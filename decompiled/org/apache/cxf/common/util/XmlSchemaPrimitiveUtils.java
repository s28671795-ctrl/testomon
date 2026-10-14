/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class XmlSchemaPrimitiveUtils {
    private static final String SCHEMA_NS_PREFIX = "xs";
    private static final Map<Class<?>, String> XML_SCHEMA_PRIMITIVE_MAP = new HashMap();

    private XmlSchemaPrimitiveUtils() {
    }

    private static void initializeMap() {
        XmlSchemaPrimitiveUtils.registerPrimitiveClasses("int", Integer.class, Integer.TYPE);
        XmlSchemaPrimitiveUtils.registerPrimitiveClasses("byte", Byte.class, Byte.TYPE);
        XmlSchemaPrimitiveUtils.registerPrimitiveClasses("boolean", Boolean.class, Boolean.TYPE);
        XmlSchemaPrimitiveUtils.registerPrimitiveClasses("long", Long.class, Long.TYPE);
        XmlSchemaPrimitiveUtils.registerPrimitiveClasses("float", Float.class, Float.TYPE);
        XmlSchemaPrimitiveUtils.registerPrimitiveClasses("double", Double.class, Double.TYPE);
        XmlSchemaPrimitiveUtils.registerPrimitiveClasses("string", String.class);
        XmlSchemaPrimitiveUtils.registerPrimitiveClasses("dateTime", java.sql.Date.class, Date.class, Calendar.class, Timestamp.class);
        XmlSchemaPrimitiveUtils.registerPrimitiveClasses("time", Time.class);
    }

    private static void registerPrimitiveClasses(String value, Class<?> ... classes) {
        for (Class<?> cls : classes) {
            XML_SCHEMA_PRIMITIVE_MAP.put(cls, value);
        }
    }

    public static String getSchemaRepresentation(Class<?> type) {
        return XmlSchemaPrimitiveUtils.getSchemaRepresentation(type, SCHEMA_NS_PREFIX);
    }

    public static String getSchemaRepresentation(Class<?> type, String xsdPrefix) {
        String value = XML_SCHEMA_PRIMITIVE_MAP.get(type);
        return value == null ? value : xsdPrefix + ":" + value;
    }

    static {
        XmlSchemaPrimitiveUtils.initializeMap();
    }
}

