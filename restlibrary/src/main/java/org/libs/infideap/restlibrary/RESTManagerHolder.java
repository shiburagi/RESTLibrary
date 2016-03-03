package org.libs.infideap.restlibrary;

import android.util.Pair;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by InfiDeaP on 14/12/2015.
 */
public class RESTManagerHolder {

    Path path;
    Object object;
    List<Pair> list;
    Type type;

    int method = 0;

    public RESTManagerHolder(Path path, Object object, Type type) {
        this.object = object;
        this.path = path;
        this.type = type;

        method = 1;
    }

    public RESTManagerHolder(Path path, List<Pair> list, Type type) {
        this.list = list;
        this.path = path;
        this.type = type;

        method = 2;
    }

}
