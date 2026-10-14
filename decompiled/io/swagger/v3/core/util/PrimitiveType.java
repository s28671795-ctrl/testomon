/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.util;

import com.fasterxml.jackson.databind.type.TypeFactory;
import io.swagger.v3.oas.models.media.BinarySchema;
import io.swagger.v3.oas.models.media.BooleanSchema;
import io.swagger.v3.oas.models.media.ByteArraySchema;
import io.swagger.v3.oas.models.media.DateSchema;
import io.swagger.v3.oas.models.media.DateTimeSchema;
import io.swagger.v3.oas.models.media.FileSchema;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.NumberSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.media.UUIDSchema;
import java.io.File;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;

public enum PrimitiveType {
    STRING((Class)String.class, "string"){

        @Override
        public Schema createProperty() {
            return new StringSchema();
        }
    }
    ,
    BOOLEAN((Class)Boolean.class, "boolean"){

        @Override
        public Schema createProperty() {
            return new BooleanSchema();
        }
    }
    ,
    BYTE((Class)Byte.class, "byte"){

        @Override
        public ByteArraySchema createProperty() {
            return new ByteArraySchema();
        }
    }
    ,
    BINARY((Class)Byte.class, "binary"){

        @Override
        public BinarySchema createProperty() {
            return new BinarySchema();
        }
    }
    ,
    URI((Class)URI.class, "uri"){

        @Override
        public Schema createProperty() {
            return new StringSchema().format("uri");
        }
    }
    ,
    URL((Class)URL.class, "url"){

        @Override
        public Schema createProperty() {
            return new StringSchema().format("url");
        }
    }
    ,
    EMAIL((Class)String.class, "email"){

        @Override
        public Schema createProperty() {
            return new StringSchema().format("email");
        }
    }
    ,
    UUID((Class)UUID.class, "uuid"){

        @Override
        public UUIDSchema createProperty() {
            return new UUIDSchema();
        }
    }
    ,
    INT((Class)Integer.class, "integer"){

        @Override
        public IntegerSchema createProperty() {
            return new IntegerSchema();
        }
    }
    ,
    LONG((Class)Long.class, "long"){

        @Override
        public Schema createProperty() {
            return new IntegerSchema().format("int64");
        }
    }
    ,
    FLOAT((Class)Float.class, "float"){

        @Override
        public Schema createProperty() {
            return new NumberSchema().format("float");
        }
    }
    ,
    DOUBLE((Class)Double.class, "double"){

        @Override
        public Schema createProperty() {
            return new NumberSchema().format("double");
        }
    }
    ,
    INTEGER((Class)BigInteger.class){

        @Override
        public Schema createProperty() {
            return new IntegerSchema().format(null);
        }
    }
    ,
    DECIMAL((Class)BigDecimal.class, "number"){

        @Override
        public Schema createProperty() {
            return new NumberSchema();
        }
    }
    ,
    NUMBER((Class)Number.class, "number"){

        @Override
        public Schema createProperty() {
            return new NumberSchema();
        }
    }
    ,
    DATE((Class)DateStub.class, "date"){

        @Override
        public DateSchema createProperty() {
            return new DateSchema();
        }
    }
    ,
    DATE_TIME((Class)Date.class, "date-time"){

        @Override
        public DateTimeSchema createProperty() {
            return new DateTimeSchema();
        }
    }
    ,
    PARTIAL_TIME((Class)LocalTime.class, "partial-time"){

        @Override
        public Schema createProperty() {
            return new StringSchema().format("partial-time");
        }
    }
    ,
    FILE((Class)File.class, "file"){

        @Override
        public FileSchema createProperty() {
            return new FileSchema();
        }
    }
    ,
    OBJECT((Class)Object.class){

        @Override
        public Schema createProperty() {
            return new Schema().type("object");
        }
    };

    private static final Map<Class<?>, PrimitiveType> KEY_CLASSES;
    private static final Map<Class<?>, PrimitiveType> BASE_CLASSES;
    private static final Map<String, PrimitiveType> EXTERNAL_CLASSES;
    private static Set<String> customExcludedClasses;
    private static Set<String> customExcludedExternalClasses;
    private static Map<String, PrimitiveType> customClasses;
    private static Set<String> systemPrefixes;
    private static Set<String> nonSystemTypes;
    private static Set<String> nonSystemTypePackages;
    private static final Map<String, PrimitiveType> NAMES;
    private final Class<?> keyClass;
    private final String commonName;
    public static final Map<String, String> datatypeMappings;

    private PrimitiveType(Class<?> keyClass) {
        this(keyClass, null);
    }

    private PrimitiveType(Class<?> keyClass, String commonName) {
        this.keyClass = keyClass;
        this.commonName = commonName;
    }

    public static Set<String> customExcludedClasses() {
        return customExcludedClasses;
    }

    public static Set<String> customExcludedExternalClasses() {
        return customExcludedExternalClasses;
    }

    public static Map<String, PrimitiveType> customClasses() {
        return customClasses;
    }

    public static Set<String> systemPrefixes() {
        return systemPrefixes;
    }

    public static Set<String> nonSystemTypes() {
        return nonSystemTypes;
    }

    public static Set<String> nonSystemTypePackages() {
        return nonSystemTypePackages;
    }

    public static PrimitiveType fromType(Type type) {
        Class<?> raw = TypeFactory.defaultInstance().constructType(type).getRawClass();
        PrimitiveType key = KEY_CLASSES.get(raw);
        if (key != null && !customExcludedClasses.contains(raw.getName())) {
            return key;
        }
        PrimitiveType custom = customClasses.get(raw.getName());
        if (custom != null) {
            return custom;
        }
        PrimitiveType external = EXTERNAL_CLASSES.get(raw.getName());
        if (external != null && !PrimitiveType.customExcludedExternalClasses().contains(raw.getName())) {
            return external;
        }
        for (Map.Entry<Class<?>, PrimitiveType> entry : BASE_CLASSES.entrySet()) {
            if (!entry.getKey().isAssignableFrom(raw)) continue;
            return entry.getValue();
        }
        return null;
    }

    public static PrimitiveType fromName(String name) {
        if (name == null) {
            return null;
        }
        PrimitiveType fromName = NAMES.get(name);
        if (fromName == null && !PrimitiveType.customExcludedExternalClasses().contains(name)) {
            fromName = EXTERNAL_CLASSES.get(name);
        }
        return fromName;
    }

    public static PrimitiveType fromTypeAndFormat(String type, String format) {
        if (StringUtils.isNotBlank(type) && type.equals("object")) {
            return null;
        }
        return PrimitiveType.fromName(datatypeMappings.get(String.format("%s_%s", StringUtils.isBlank(type) ? "" : type, StringUtils.isBlank(format) ? "" : format)));
    }

    public static Schema createProperty(Type type) {
        PrimitiveType item = PrimitiveType.fromType(type);
        return item == null ? null : item.createProperty();
    }

    public static Schema createProperty(String name) {
        PrimitiveType item = PrimitiveType.fromName(name);
        return item == null ? null : item.createProperty();
    }

    public static String getCommonName(Type type) {
        PrimitiveType item = PrimitiveType.fromType(type);
        return item == null ? null : item.getCommonName();
    }

    public Class<?> getKeyClass() {
        return this.keyClass;
    }

    public String getCommonName() {
        return this.commonName;
    }

    public abstract Schema createProperty();

    private static <K> void addKeys(Map<K, PrimitiveType> map, PrimitiveType type, K ... keys2) {
        for (K key : keys2) {
            map.put(key, type);
        }
    }

    public static void enablePartialTime() {
        PrimitiveType.customClasses().put("org.joda.time.LocalTime", PARTIAL_TIME);
        PrimitiveType.customClasses().put("java.time.LocalTime", PARTIAL_TIME);
    }

    static {
        customExcludedClasses = ConcurrentHashMap.newKeySet();
        customExcludedExternalClasses = ConcurrentHashMap.newKeySet();
        customClasses = new ConcurrentHashMap<String, PrimitiveType>();
        systemPrefixes = ConcurrentHashMap.newKeySet();
        nonSystemTypes = ConcurrentHashMap.newKeySet();
        nonSystemTypePackages = ConcurrentHashMap.newKeySet();
        systemPrefixes.add("java.");
        systemPrefixes.add("javax.");
        nonSystemTypes.add("java.time.LocalTime");
        HashMap<String, String> dms = new HashMap<String, String>();
        dms.put("integer_int32", "integer");
        dms.put("integer_", "integer");
        dms.put("integer_int64", "long");
        dms.put("number_", "number");
        dms.put("number_float", "float");
        dms.put("number_double", "double");
        dms.put("string_", "string");
        dms.put("string_byte", "byte");
        dms.put("string_email", "email");
        dms.put("string_binary", "binary");
        dms.put("string_uri", "uri");
        dms.put("string_url", "url");
        dms.put("string_uuid", "uuid");
        dms.put("string_date", "date");
        dms.put("string_date-time", "date-time");
        dms.put("string_partial-time", "partial-time");
        dms.put("string_password", "password");
        dms.put("boolean_", "boolean");
        dms.put("object_", "object");
        datatypeMappings = Collections.unmodifiableMap(dms);
        HashMap keyClasses = new HashMap();
        PrimitiveType.addKeys(keyClasses, BOOLEAN, Boolean.class, Boolean.TYPE);
        PrimitiveType.addKeys(keyClasses, STRING, String.class, Character.class, Character.TYPE);
        PrimitiveType.addKeys(keyClasses, BYTE, Byte.class, Byte.TYPE);
        PrimitiveType.addKeys(keyClasses, URL, URL.class);
        PrimitiveType.addKeys(keyClasses, URI, URI.class);
        PrimitiveType.addKeys(keyClasses, UUID, UUID.class);
        PrimitiveType.addKeys(keyClasses, INT, Integer.class, Integer.TYPE, Short.class, Short.TYPE);
        PrimitiveType.addKeys(keyClasses, LONG, Long.class, Long.TYPE);
        PrimitiveType.addKeys(keyClasses, FLOAT, Float.class, Float.TYPE);
        PrimitiveType.addKeys(keyClasses, DOUBLE, Double.class, Double.TYPE);
        PrimitiveType.addKeys(keyClasses, INTEGER, BigInteger.class);
        PrimitiveType.addKeys(keyClasses, DECIMAL, BigDecimal.class);
        PrimitiveType.addKeys(keyClasses, NUMBER, Number.class);
        PrimitiveType.addKeys(keyClasses, DATE, DateStub.class);
        PrimitiveType.addKeys(keyClasses, DATE_TIME, Date.class);
        PrimitiveType.addKeys(keyClasses, FILE, File.class);
        PrimitiveType.addKeys(keyClasses, OBJECT, Object.class);
        KEY_CLASSES = Collections.unmodifiableMap(keyClasses);
        HashMap baseClasses = new HashMap();
        PrimitiveType.addKeys(baseClasses, DATE_TIME, Date.class, Calendar.class);
        BASE_CLASSES = Collections.unmodifiableMap(baseClasses);
        HashMap externalClasses = new HashMap();
        PrimitiveType.addKeys(externalClasses, DATE, "org.joda.time.LocalDate", "java.time.LocalDate");
        PrimitiveType.addKeys(externalClasses, DATE_TIME, "java.time.LocalDateTime", "java.time.ZonedDateTime", "java.time.OffsetDateTime", "javax.xml.datatype.XMLGregorianCalendar", "org.joda.time.LocalDateTime", "org.joda.time.ReadableDateTime", "org.joda.time.DateTime", "java.time.Instant");
        EXTERNAL_CLASSES = Collections.unmodifiableMap(externalClasses);
        TreeMap names = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        for (PrimitiveType item : PrimitiveType.values()) {
            String name = item.getCommonName();
            if (name == null) continue;
            PrimitiveType.addKeys(names, item, name);
        }
        PrimitiveType.addKeys(names, INT, "int");
        PrimitiveType.addKeys(names, OBJECT, "object");
        NAMES = Collections.unmodifiableMap(names);
    }

    private static class DateStub {
        private DateStub() {
        }
    }
}

