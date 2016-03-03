package org.libs.infideap.restlibrary;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Shiburagi on 5/20/15.
 */
public class Path {

    public final String method;
    private String[] params;
    public String local;
    public String backend;

    public Path(String local, String backend, String method) {
        this.local = local;
        this.backend = backend;

        if (method != null)
            this.method = method.toUpperCase();
        else
            this.method = null;
    }


    public Path(String local, String backend, String method, String[] params) {
        this(local, backend, method);

        this.params = params;

        this.backend = format(backend, params);

    }

    private String format(String url, String[] params) {
        StringBuilder stringBuilder = new StringBuilder(url);
        stringBuilder.append('?');
        for (int i = 0; i < params.length; i++) {
            stringBuilder.append(params[i]).append('&');
        }
        try {
            return URLEncoder.encode(stringBuilder.toString().substring(0, stringBuilder.length() - 1), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return stringBuilder.toString().substring(0, stringBuilder.length() - 1);
        }
    }

}
