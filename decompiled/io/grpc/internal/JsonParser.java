/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Preconditions;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class JsonParser {
    private static final Logger logger = Logger.getLogger(JsonParser.class.getName());

    private JsonParser() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Object parse(String raw) throws IOException {
        JsonReader jr = new JsonReader(new StringReader(raw));
        try {
            Object object = JsonParser.parseRecursive(jr);
            return object;
        }
        finally {
            try {
                jr.close();
            }
            catch (IOException e) {
                logger.log(Level.WARNING, "Failed to close", e);
            }
        }
    }

    private static Object parseRecursive(JsonReader jr) throws IOException {
        Preconditions.checkState(jr.hasNext(), "unexpected end of JSON");
        switch (jr.peek()) {
            case BEGIN_ARRAY: {
                return JsonParser.parseJsonArray(jr);
            }
            case BEGIN_OBJECT: {
                return JsonParser.parseJsonObject(jr);
            }
            case STRING: {
                return jr.nextString();
            }
            case NUMBER: {
                return jr.nextDouble();
            }
            case BOOLEAN: {
                return jr.nextBoolean();
            }
            case NULL: {
                return JsonParser.parseJsonNull(jr);
            }
        }
        throw new IllegalStateException("Bad token: " + jr.getPath());
    }

    private static Map<String, ?> parseJsonObject(JsonReader jr) throws IOException {
        jr.beginObject();
        LinkedHashMap<String, Object> obj = new LinkedHashMap<String, Object>();
        while (jr.hasNext()) {
            String name = jr.nextName();
            Object value = JsonParser.parseRecursive(jr);
            obj.put(name, value);
        }
        Preconditions.checkState(jr.peek() == JsonToken.END_OBJECT, "Bad token: " + jr.getPath());
        jr.endObject();
        return Collections.unmodifiableMap(obj);
    }

    private static List<?> parseJsonArray(JsonReader jr) throws IOException {
        jr.beginArray();
        ArrayList<Object> array = new ArrayList<Object>();
        while (jr.hasNext()) {
            Object value = JsonParser.parseRecursive(jr);
            array.add(value);
        }
        Preconditions.checkState(jr.peek() == JsonToken.END_ARRAY, "Bad token: " + jr.getPath());
        jr.endArray();
        return Collections.unmodifiableList(array);
    }

    private static Void parseJsonNull(JsonReader jr) throws IOException {
        jr.nextNull();
        return null;
    }
}

