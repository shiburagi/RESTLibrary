package com.app.infideap.restlibraryexample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.libs.infideap.restlibrary.Path;
import org.libs.infideap.restlibrary.RESTManager;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initial this line of code at the top of onCreate()
        // needed context and isExprimental state.
        RESTManager.init(this, false);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    /**
     * This method use build in background process in RESTManager
     * @param view
     */
    public void send(final View view) {

        Spinner methodSpinner = (Spinner) findViewById(R.id.spinner_method);

        TextInputEditText urlEditText = (TextInputEditText) findViewById(R.id.editText_url);
        TextInputEditText rawEditText = (TextInputEditText) findViewById(R.id.editText_raw);

        final TextView resultTextView = (TextView) findViewById(R.id.textView_result);

        RESTManager.getInstance().requestRawSync(
                new Path(
                        null, // null if doesn't have local/dummy data in app
                        urlEditText.getText().toString(), // null if the REST API not ready yet
                        methodSpinner.getSelectedItem().toString() //method of the request
                ),

                new StringBuilder(rawEditText.getText().toString()), //JSON String

                new RESTManager.OnDataRequestListener() { // Interface

                    /**
                     * The process is complete without any error.
                     * @param object
                     */
                    @Override
                    public void onSuccess(final Object object) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                resultTextView.setText(object.toString());
                            }
                        });
                    }

                    /**
                     *  if an error occurred
                     * @param e
                     */
                    @Override
                    public void onFailed(final Exception e) {

                        Snackbar.make(view, R.string.erroroccured, Snackbar.LENGTH_LONG)
                                .show();

                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                resultTextView.setText(e.toString());
                            }
                        });
                    }

                    /**
                     * if the device doesn't have internet connection
                     */
                    @Override
                    public void onNoIntenetConnection() {
                        Snackbar.make(view, R.string.noactiveinternetconnection, Snackbar.LENGTH_LONG)
                                .show();

                    }

                    /**
                     * This method always execute.
                     * @param success
                     */
                    @Override
                    public void finish(boolean success) {

                    }
                }

        );

    }

    /**
     * This method use own background process.
     * @param view
     */
    public void sendByUsingOwnThread(final View view) {

        final Spinner methodSpinner = (Spinner) findViewById(R.id.spinner_method);

        final TextInputEditText urlEditText = (TextInputEditText) findViewById(R.id.editText_url);
        final TextInputEditText rawEditText = (TextInputEditText) findViewById(R.id.editText_raw);

        final TextView resultTextView = (TextView) findViewById(R.id.textView_result);

        final String method = methodSpinner.getSelectedItem().toString();
        final String url = urlEditText.getText().toString();
        final String raw = rawEditText.getText().toString();

        if (RESTManager.getInstance().isNetworkAvailable())
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {

                    try {
                        final StringBuilder builder =
                                RESTManager.getInstance().retrieveRawData(
                                        new Path(
                                                null,
                                                url,
                                                method
                                        ),
                                        new StringBuilder(raw)
                                );
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                resultTextView.setText(builder.toString());
                            }
                        });
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        Snackbar.make(view, R.string.erroroccured, Snackbar.LENGTH_LONG)
                                .show();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                resultTextView.setText(e.toString());
                            }
                        });
                    }
                    return null;
                }
            }.execute();
        else
            Snackbar.make(view, R.string.noactiveinternetconnection, Snackbar.LENGTH_LONG)
                    .show();


    }

}
