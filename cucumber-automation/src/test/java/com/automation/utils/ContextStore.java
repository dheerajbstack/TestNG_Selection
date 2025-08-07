package com.automation.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class ContextStore {
    private static final Map<String, Object> context = new ConcurrentHashMap<>();

    public static void put(String key, Object value) {
        context.put(key, value);
    }

    public static Object get(String key) {
        return context.get(key);
    }

    public static void clear() {
        context.clear();
    }
}