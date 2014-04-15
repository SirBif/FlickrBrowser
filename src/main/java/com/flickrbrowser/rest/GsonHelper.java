package com.flickrbrowser.rest;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by bif on 15/04/14.
 */
public abstract class GsonHelper {
    private static final Gson gsonInstance = new Gson();

    public static Gson getGsonInstance() {
        return gsonInstance;
    }

    public static <T> T fromJson(InputStream stream, java.lang.Class<T> classOfT) {
        JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(stream)));
        try {
            return gsonInstance.fromJson(reader, classOfT);
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    public static <T> T fromJson(String json, java.lang.Class<T> classOfT) {
        return gsonInstance.fromJson(json, classOfT);
    }
}
