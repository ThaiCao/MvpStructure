package com.mvp.structure.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by thai.cao on 8/1/2019.
 */

public class IOUtils {

    public static void close(Closeable closeable){
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
            //close error
        }
    }
}
