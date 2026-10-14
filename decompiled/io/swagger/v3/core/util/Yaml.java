/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.util;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.swagger.v3.core.util.ObjectMapperFactory;

public class Yaml {
    static ObjectMapper mapper;

    public static ObjectMapper mapper() {
        if (mapper == null) {
            mapper = ObjectMapperFactory.createYaml();
        }
        return mapper;
    }

    public static ObjectWriter pretty() {
        return Yaml.mapper().writer(new DefaultPrettyPrinter());
    }

    public static String pretty(Object o) {
        try {
            return Yaml.pretty().writeValueAsString(o);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void prettyPrint(Object o) {
        try {
            System.out.println(Yaml.pretty().writeValueAsString(o));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

