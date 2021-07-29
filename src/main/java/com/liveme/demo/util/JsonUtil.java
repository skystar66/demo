package com.liveme.demo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.Reader;
import java.lang.reflect.Type;

@SuppressWarnings("unused")
public class JsonUtil {

    private static final Gson GSON = new Gson();

    private static final Gson GSON_NULL = new GsonBuilder().serializeNulls().create();

    private static final Gson GSON_PRETTY = new GsonBuilder().setPrettyPrinting().create();

    public static JsonObject fromJson( String json) {
        return GSON.fromJson(json, JsonObject.class);
    }

    public static <T> T fromJson(String json, Class<T> targetType) {
        return GSON.fromJson(json, targetType);
    }

    public static <T> T fromJson(Reader json, Class<T> targetType) {
        return GSON.fromJson(json, targetType);
    }

    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    public static <T> T fromJson(JsonElement json, Type type) {
        return GSON.fromJson(json, type);
    }

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static String toJsonNullable(Object object) {
        return GSON_NULL.toJson(object);
    }

    public static String toJsonPretty(Object object) { return GSON_PRETTY.toJson(object); }

}
