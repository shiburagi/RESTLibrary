package org.libs.infideap.restlibrary;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by InfiDeaP on 15/12/2015.
 */
public class RESTManager extends RESTManagerOld {

    private static RESTManager instance;

    /**
     * To initialize RESTManager object and instance and set the state of current developemnt,<br/>
     * - true : to use dummy data in app.
     * - false : to get data from the server or API.
     *
     * @param context
     * @param isExprimental use to set the state of current developement.
     * @return
     */
    public static RESTManager init(Context context, boolean isExprimental) {
        instance = new RESTManager(context);
        instance.setExperimental(isExprimental);

        return instance;
    }

    /**
     * To initialize RESTManager object and instance
     * @param context
     * @return
     */
    public static RESTManager init(Context context) {
        instance = new RESTManager(context);
        instance.setExperimental(true);

        return instance;
    }

    /**
     * to get RESTManager instance.
     * @return
     */
    public static RESTManager getInstance() {

        return instance;
    }

    /**
     * Constructor
     * @param context
     */
    private RESTManager(Context context) {
        super(context);
    }

    /**
     * This method use send the request with background process.
     *
     * @param path path of data, contains local and backend path
     * @param model data in object(send to server-side)
     * @param type type of data will obtain
     * @param listener an interface to use when the process is complete or terminate
     */
    public void requestSync(final Path path, final Object model, final Class<?> type, OnDataRequestListener listener) {
        if (listener == null)
            listener = getDefaultDataListener();

        final OnDataRequestListener _Listener = listener;
        if (isNetworkAvailable())
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    boolean success = true;
                    Object object;

                    try {
                        object = retrieveData(path, model, type);
                        _Listener.onSuccess(object);

                    } catch (Exception e) {
                        success = false;
                        _Listener.onFailed(e);
                    }

                    _Listener.finish(success);
                    return null;
                }
            }.execute();
        else {
            _Listener.onNoIntenetConnection();
            _Listener.finish(false);
        }
    }

    /**
     * This method use send the request with background process.
     * This method use if need send json in plain string and get the response also in string format
     *
     * @param path path of data, contains local and backend path
     * @param raw data in json string format(send to server-side)
     * @param listener an interface to use when the process is complete or terminate
     */
    public void requestRawSync(final Path path, final StringBuilder raw, OnDataRequestListener listener) {
        if (listener == null)
            listener = getDefaultDataListener();

        final OnDataRequestListener _Listener = listener;
        if (isNetworkAvailable())
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    boolean success = true;

                    try {
                        StringBuilder object = retrieveRawData(path, raw);
                        _Listener.onSuccess(object);

                    } catch (Exception e) {
                        success = false;
                        _Listener.onFailed(e);
                    }

                    _Listener.finish(success);
                    return null;
                }
            }.execute();
        else {
            _Listener.onNoIntenetConnection();
            _Listener.finish(false);
        }
    }

    /**
     * This method use send the request with background process,
     * and the result in List representation.
     *
     * @param path path of data, contains local and backend path
     * @param model data in object(send to server-side)
     * @param type type of data will obtain
     * @param listener an interface to use when the process is complete or terminate
     */
    public void requestListSync(final Path path, final Object model, final Class<?> type, OnDataListRequestListener listener) {


        if (listener == null)
            listener = getDefaultDataListListener();

        final OnDataListRequestListener _Listener = listener;

        if (isNetworkAvailable())
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    boolean success = true;
                    List list = null;
                    try {
                        list = retriveDataList(path, model, type);
                        _Listener.onSuccess(list);
                    } catch (Exception e) {
                        _Listener.onFailed(e);
                        success = false;
                    }

                    _Listener.finish(success);
                    return null;
                }
            }.execute();
        else {
            _Listener.onNoIntenetConnection();
            _Listener.finish(false);
        }
    }


    /**
     * to check if there are exist an active network connection.
     * @return
     */
    public boolean isNetworkAvailable() {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private OnDataRequestListener getDefaultDataListener() {
        return new OnDataRequestListener() {
            @Override
            public void onSuccess(Object object) {

            }

            @Override
            public void onFailed(Exception e) {

            }

            @Override
            public void onNoIntenetConnection() {

            }

            @Override
            public void finish(boolean success) {

            }
        };
    }

    public OnDataListRequestListener getDefaultDataListListener() {
        return new OnDataListRequestListener() {
            @Override
            public void onSuccess(List list) {

            }

            @Override
            public void onFailed(Exception e) {

            }

            @Override
            public void onNoIntenetConnection() {

            }

            @Override
            public void finish(boolean success) {

            }
        };
    }

    private interface OnRequestListener {
        void onFailed(Exception e);

        void onNoIntenetConnection();

        void finish(boolean success);
    }

    /**
     * Interface for requesSync and requestRawSync
     * @param <T>
     */
    public interface OnDataRequestListener<T> extends OnRequestListener {

        void onSuccess(T object);

    }

    /**
     * Interface for requestListSync
     * @param <T>
     */
    public interface OnDataListRequestListener<T> extends OnRequestListener {

        void onSuccess(List<T> list);

    }

}
