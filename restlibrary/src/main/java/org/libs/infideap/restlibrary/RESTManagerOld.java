package org.libs.infideap.restlibrary;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;

//import android.util.Log;


/**
 * Created by Shiburagi on 27-Mar-15.
 * Updated by Shiburagi on 14-DIS-15
 * <p/>
 * This class created to manage which location of data should application retrieve, local or backend.<br/>
 * Base on current environment state and data.
 */
public class RESTManagerOld {


    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_HEAD = "HEAD";
    public static final String METHOD_OPTIONS = "OPTIONS";
    public static final String METHOD_TRACE = "TRACE";
    private static int TIMEOUT_MILISECONDS = 10000;

    private final String TAG = "RESTManagerOld";
    protected final Context context;
    private String DATABASE_DATEFORMAT = "yy-MM-dd hh:mm:ss";
    private TreeMap map = null;


    private boolean IS_EXPERIMENTAL = true;


    protected RESTManagerOld(Context context) {
        this.context = context;
    }


    /**
     * This method use to read data from Asset Resource Directory (Local).
     *
     * @param path path of the local REST
     * @return plain text from file
     * @throws IOException
     * @throws JSONException
     */
    private StringBuilder readFromAsset(String path) throws JSONException, IOException {

        byte[] buffer;
        InputStream inputStream;
        try {
            inputStream = context.getAssets().open(path);
        } catch (IOException e) {
//            e.a(new Throwable("Local file must store in Asset Resource Folder."));
            IOException ioException = new IOException(e.toString() + "\n\r" +
                    "Local REST must store in Asset Resource Folder.");
            ioException.setStackTrace(e.getStackTrace());
            throw ioException;
//            throw new IOException();

        }
        int size = inputStream.available();

        if (size == 0) {
        }

        buffer = new byte[size];

        inputStream.read(buffer);
        inputStream.close();


        return new StringBuilder(new String(buffer));

    }

    /**
     * This method use to receive data from server-side.
     *
     * @param path link of the REST
     * @param json JSON in String
     * @return plain text receive from back end
     * @throws IOException
     * @throws JSONException
     */
    private StringBuilder receiveFromBackEnd(Path path, StringBuilder json) throws IOException, JSONException {

        if (path.backend.substring(0, 8).equalsIgnoreCase("https://"))
            return receiveFromHttpsBackEnd(path, json);
        else if (path.backend.substring(0, 7).equalsIgnoreCase("http://"))
            return receiveFromHttpBackEnd(path, json);
        else {
            path.backend = "http://".concat(path.backend);
            return receiveFromHttpBackEnd(path, json);
        }


    }

    /**
     * For HTTP url connection
     *
     * @param path link of the REST
     * @param json JSON in String
     * @return plain text receive from back end
     * @throws IOException
     * @throws JSONException
     */
    private StringBuilder receiveFromHttpBackEnd(Path path, StringBuilder json) throws IOException, JSONException {
        URL obj = new URL(path.backend);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        String USER_AGENT = "Mozilla/5.0";
        //add reuqest header
        con.setRequestMethod(path.method);
        con.setRequestProperty("User-Agent", USER_AGENT);

        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");

        con.setConnectTimeout(TIMEOUT_MILISECONDS);
        if (path.method.equals(METHOD_POST) || path.method.equals(METHOD_PUT)) {

            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            byte[] bytes = json.toString().getBytes();

            wr.write(bytes);
            wr.flush();
            wr.close();


        }

        con.connect();
        InputStream in;
        int status = con.getResponseCode();

        if (status >= 400)
            in = con.getErrorStream();
        else
            in = con.getInputStream();

        Log.d(TAG, con.getRequestMethod());
        return getResponse(in);
    }

    /**
     * For HTTPS url connection
     *
     * @param path link of the REST
     * @param json JSON in String
     * @return plain text receive from back end
     * @throws IOException
     * @throws JSONException
     */
    private StringBuilder receiveFromHttpsBackEnd(Path path, StringBuilder json) throws IOException, JSONException {
        URL obj = new URL(path.backend);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        String USER_AGENT = "Mozilla/5.0";
        //add reuqest header
        con.setRequestMethod(path.method);
        con.setRequestProperty("User-Agent", USER_AGENT);

        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");

        if (path.method.equals(METHOD_POST) || path.method.equals(METHOD_PUT)) {

            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            byte[] bytes = json.toString().getBytes();
            wr.write(bytes);
            wr.flush();
            wr.close();


        }

        con.connect();
        InputStream in;
        int status = con.getResponseCode();

        if (status >= 400)
            in = con.getErrorStream();
        else
            in = con.getInputStream();

        Log.d(TAG, con.getRequestMethod());
        return getResponse(in);
    }

    /**
     * To get response/feedback from URL/REST API
     * @param inputStream
     * @return
     * @throws IOException
     */
    private StringBuilder getResponse(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(inputStream));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }

    /**
     * This method created to help the application decide whether
     * get data from local or server side, depend on current environment.
     * This method for POST method.
     *
     * @param path path of data, contains local and backend path
     * @param json data in json string format(send to server-side)
     * @return plain text of response
     */
    public StringBuilder retrieveRawData(Path path, StringBuilder json) throws IOException, JSONException {


        if (IS_EXPERIMENTAL || path.backend == null || path.backend.length() == 0) {
            json = readFromAsset(path.local);

        } else {

            json = receiveFromBackEnd(path, json);

        }

        return json;
    }


    /**
     * This method to convert the plain text receive from response into Object/Model Representation.
     * Method type - POST
     * data send in raw input format
     *
     * @param path path of data, contains local and backend path
     * @param model data in object(send to server-side)
     * @param type  type of data will obtain
     * @return list of data in object representation
     */
    public List retriveDataList(Path path, Object model, Type type) throws IOException, JSONException {


        List list;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATABASE_DATEFORMAT);

        Gson gson = gsonBuilder.create();

        StringBuilder json = retrieveRawData(path, new StringBuilder(gson.toJson(model)));

        Object[] objects = gson.fromJson(json.toString(), type);
        list = Arrays.asList(objects);

        return list;
    }

    /**
     * This method to convert the plain text receive from response into Object/Model Representation.
     * Method type - POST
     * data send in raw input format
     *
     * @param path path of data, contains local and backend path
     * @param model data in object(send to server-side)
     * @param type  type of data will obtain
     * @return data in object representation
     */
    public Object retrieveData(Path path, Object model, Type type) throws IOException, JSONException {
//        Toast.makeText("JSON",Toast.LENGTH_LONG).show();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATABASE_DATEFORMAT);

        Gson gson = gsonBuilder.create();

//        Object objects2 = gson.fromJson(gson.toJson(jsonObject), type);
        StringBuilder appendable = new StringBuilder();
        gson.toJson(model, appendable);
        StringBuilder json = retrieveRawData(path, appendable);


        Object objects = gson.fromJson(json.toString(), type);


        return objects;
    }


    /**
     * This method to set current environment state whether experiment
     * or integration and testing.
     *
     * @param experiment TRUE : for experiment state, and <br/> FALSE : for integration state
     */
    public void setExperimental(boolean experiment) {
        IS_EXPERIMENTAL = experiment;

    }

    /**
     * This method to set date format.
     *
     * @param dateFormat Date format
     */
    public void setDateFormat(String dateFormat) {
        DATABASE_DATEFORMAT = dateFormat;

    }


    /**
     * This method to set timeout miliseconds.
     *
     * @param milisecond Milisecond
     */
    public void setTimeout(int milisecond) {
        TIMEOUT_MILISECONDS = milisecond;

    }

}
