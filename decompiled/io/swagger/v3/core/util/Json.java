/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.util;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.swagger.v3.core.util.ObjectMapperFactory;

public class Json {
    private static ObjectMapper mapper;

    public static ObjectMapper mapper() {
        if (mapper == null) {
            mapper = ObjectMapperFactory.createJson();
        }
        return mapper;
    }

    public static ObjectWriter pretty() {
        return Json.mapper().writer(new DefaultPrettyPrinter());
    }

    public static String pretty(Object o) {
        try {
            return Json.pretty().writeValueAsString(o);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void prettyPrint(Object o) {
        try {
            System.out.println(Json.pretty().writeValueAsString(o).replace("\r", ""));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

