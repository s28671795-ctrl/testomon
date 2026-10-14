/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.JAXB
 */
package org.jooq.impl;

import jakarta.xml.bind.JAXB;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import org.jooq.ArrayRecord;
import org.jooq.ContextConverter;
import org.jooq.Converter;
import org.jooq.ConverterContext;
import org.jooq.EnumType;
import org.jooq.Field;
import org.jooq.JSON;
import org.jooq.JSONB;
import org.jooq.JSONFormat;
import org.jooq.Param;
import org.jooq.QualifiedRecord;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.XML;
import org.jooq.XMLFormat;
import org.jooq.exception.DataTypeException;
import org.jooq.impl.AbstractContextConverter;
import org.jooq.impl.IdentityConverter;
import org.jooq.impl.Internal;
import org.jooq.impl.Tools;
import org.jooq.tools.Ints;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.Longs;
import org.jooq.tools.jdbc.MockArray;
import org.jooq.tools.jdbc.MockResultSet;
import org.jooq.tools.json.ContainerFactory;
import org.jooq.tools.json.JSONArray;
import org.jooq.tools.json.JSONObject;
import org.jooq.tools.json.JSONParser;
import org.jooq.tools.json.ParseException;
import org.jooq.tools.reflect.Reflect;
import org.jooq.types.DayToSecond;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;
import org.jooq.types.Unsigned;
import org.jooq.types.YearToMonth;
import org.jooq.types.YearToSecond;
import org.jooq.util.postgres.PostgresUtils;
import org.jooq.util.xml.jaxb.InformationSchema;

final class Convert {
    private static final JooqLogger log = JooqLogger.getLogger(Convert.class);
    static final Set<String> TRUE_VALUES;
    static final Set<String> FALSE_VALUES;
    private static final Pattern UUID_PATTERN;
    static final Pattern P_FRACTIONAL_SECONDS;

    static final Object[] convert(Object[] values2, Field<?>[] fields) {
        if (values2 == null) {
            return null;
        }
        Object[] result = new Object[values2.length];
        for (int i = 0; i < values2.length; ++i) {
            result[i] = values2[i] instanceof Field ? values2[i] : Convert.convert(values2[i], fields[i].getType());
        }
        return result;
    }

    static final Object[] convert(Object[] values2, Class<?>[] types) {
        if (values2 == null) {
            return null;
        }
        Object[] result = new Object[values2.length];
        for (int i = 0; i < values2.length; ++i) {
            result[i] = values2[i] instanceof Field ? values2[i] : Convert.convert(values2[i], types[i]);
        }
        return result;
    }

    static final <U> U[] convertArray(Object[] from, Converter<?, ? extends U> converter) throws DataTypeException {
        if (from == null) {
            return null;
        }
        Object[] arrayOfT = Convert.convertArray(from, converter.fromType());
        Object[] arrayOfU = (Object[])Array.newInstance(converter.toType(), from.length);
        for (int i = 0; i < arrayOfT.length; ++i) {
            arrayOfU[i] = Convert.convert(arrayOfT[i], converter);
        }
        return arrayOfU;
    }

    static final Object[] convertArray(Object[] from, Class<?> toClass) throws DataTypeException {
        if (from == null) {
            return null;
        }
        if (!toClass.isArray()) {
            return Convert.convertArray(from, Internal.arrayType(toClass));
        }
        if (toClass == from.getClass()) {
            return from;
        }
        Class<?> toComponentType = toClass.getComponentType();
        if (from.length == 0) {
            return Arrays.copyOf(from, from.length, toClass);
        }
        if (from[0] != null && from[0].getClass() == toComponentType) {
            return Arrays.copyOf(from, from.length, toClass);
        }
        Object[] result = (Object[])Array.newInstance(toComponentType, from.length);
        for (int i = 0; i < from.length; ++i) {
            result[i] = Convert.convert(from[i], toComponentType);
        }
        return result;
    }

    static final <U> U[] convertCollection(Collection from, Class<? extends U[]> to) {
        return new ConvertAll<U[]>(to).from(from, Internal.converterContext());
    }

    static final <U> U convert(Object from, Converter<?, ? extends U> converter) throws DataTypeException {
        if (converter instanceof IdentityConverter) {
            return (U)from;
        }
        return Convert.convert0(from, converter);
    }

    private static final <T, U> U convert0(Object from, Converter<T, ? extends U> converter) throws DataTypeException {
        Class<T> fromType = converter.fromType();
        if (fromType == Object.class) {
            return ContextConverter.scoped(converter).from(from, Internal.converterContext());
        }
        ConvertAll<T> convertAll = new ConvertAll<T>(fromType);
        return ContextConverter.scoped(converter).from(convertAll.from(from, Internal.converterContext()), Internal.converterContext());
    }

    static final <T> T convert(Object from, Class<? extends T> toClass) throws DataTypeException {
        if (from != null && from.getClass() == toClass) {
            return (T)from;
        }
        return (T)Convert.convert0(from, new ConvertAll<T>(toClass));
    }

    static final <T> List<T> convert(Collection<?> collection, Class<? extends T> type) throws DataTypeException {
        return Convert.convert(collection, new ConvertAll<T>(type));
    }

    static final <U> List<U> convert(Collection<?> collection, Converter<?, ? extends U> converter) throws DataTypeException {
        return Convert.convert0(collection, converter);
    }

    private static final <T, U> List<U> convert0(Collection<?> collection, Converter<T, ? extends U> converter) throws DataTypeException {
        ConvertAll<T> all = new ConvertAll<T>(converter.fromType());
        ArrayList<U> result = new ArrayList<U>(collection.size());
        for (Object o : collection) {
            result.add(Convert.convert(all.from(o, Internal.converterContext()), converter));
        }
        return result;
    }

    private Convert() {
    }

    static final String patchFractionalSeconds(String string) {
        return string.length() > 8 ? P_FRACTIONAL_SECONDS.matcher(string).replaceFirst("$1") : string;
    }

    static final String patchIso8601Time(String string) {
        return string.length() == 8 ? string.replace('.', ':') : string;
    }

    static final String patchIso8601Timestamp(String string, boolean t2) {
        if (string.length() > 11) {
            if (t2 && string.charAt(10) == ' ') {
                return string.substring(0, 10) + "T" + string.substring(11);
            }
            if (!t2 && string.charAt(10) == 'T') {
                return string.substring(0, 10) + " " + string.substring(11);
            }
        }
        return string;
    }

    static {
        UUID_PATTERN = Pattern.compile("(\\p{XDigit}{8})-?(\\p{XDigit}{4})-?(\\p{XDigit}{4})-?(\\p{XDigit}{4})-?(\\p{XDigit}{12})");
        HashSet<String> trueValues = new HashSet<String>();
        HashSet<String> falseValues = new HashSet<String>();
        trueValues.add("1");
        trueValues.add("1.0");
        trueValues.add("y");
        trueValues.add("Y");
        trueValues.add("yes");
        trueValues.add("YES");
        trueValues.add("true");
        trueValues.add("TRUE");
        trueValues.add("t");
        trueValues.add("T");
        trueValues.add("on");
        trueValues.add("ON");
        trueValues.add("enabled");
        trueValues.add("ENABLED");
        falseValues.add("0");
        falseValues.add("0.0");
        falseValues.add("n");
        falseValues.add("N");
        falseValues.add("no");
        falseValues.add("NO");
        falseValues.add("false");
        falseValues.add("FALSE");
        falseValues.add("f");
        falseValues.add("F");
        falseValues.add("off");
        falseValues.add("OFF");
        falseValues.add("disabled");
        falseValues.add("DISABLED");
        TRUE_VALUES = Collections.unmodifiableSet(trueValues);
        FALSE_VALUES = Collections.unmodifiableSet(falseValues);
        P_FRACTIONAL_SECONDS = Pattern.compile("^(\\d+:\\d+:\\d+)\\.\\d+$");
    }

    private static final class ConvertAll<U>
    extends AbstractContextConverter<Object, U> {
        private final Class<? extends U> toClass;

        ConvertAll(Class<? extends U> toClass) {
            super(Object.class, toClass);
            this.toClass = toClass;
        }

        @Override
        public U from(Object from, ConverterContext scope) {
            Class<?>[] types;
            Class<?> wrapperFrom;
            if (from == null) {
                if (this.toClass.isPrimitive()) {
                    if (this.toClass == Character.TYPE) {
                        return (U)Character.valueOf('\u0000');
                    }
                    return Convert.convert((Object)0, this.toClass);
                }
                if (this.toClass == Optional.class) {
                    return (U)Optional.empty();
                }
                return null;
            }
            Class<?> fromClass = from.getClass();
            if (this.toClass == fromClass) {
                return (U)from;
            }
            if (this.toClass.isAssignableFrom(fromClass)) {
                return (U)from;
            }
            Class<? extends U> wrapperTo = Reflect.wrapper(this.toClass);
            if (wrapperTo == (wrapperFrom = Reflect.wrapper(fromClass))) {
                return (U)from;
            }
            if (fromClass == Optional.class) {
                return this.from((Object)((Optional)from).orElse(null), scope);
            }
            if (fromClass == byte[].class) {
                if (this.toClass == UUID.class) {
                    ByteBuffer b = ByteBuffer.wrap((byte[])from);
                    long mostSigBits = b.getLong();
                    long leastSigBits = b.getLong();
                    return (U)new UUID(mostSigBits, leastSigBits);
                }
                if (this.toClass == ByteBuffer.class) {
                    return (U)ByteBuffer.wrap((byte[])from);
                }
                return Convert.convert((Object)new String((byte[])from), this.toClass);
            }
            if (fromClass.isArray()) {
                Object[] fromArray = (Object[])from;
                if (Collection.class.isAssignableFrom(this.toClass) && this.toClass.isAssignableFrom(ArrayList.class)) {
                    return (U)new ArrayList<Object>(Arrays.asList(fromArray));
                }
                if (Collection.class.isAssignableFrom(this.toClass) && this.toClass.isAssignableFrom(LinkedHashSet.class)) {
                    return (U)new LinkedHashSet<Object>(Arrays.asList(fromArray));
                }
                if (this.toClass == java.sql.Array.class) {
                    return (U)new MockArray<Object>(null, fromArray, fromClass);
                }
                return (U)Convert.convertArray(fromArray, this.toClass);
            }
            if (java.sql.Array.class.isAssignableFrom(fromClass)) {
                try {
                    return (U)Convert.convertArray((Object[])((java.sql.Array)from).getArray(), this.toClass);
                }
                catch (SQLException e) {
                    throw new DataTypeException("Cannot convert from JDBC array: ", e);
                }
            }
            if (Result.class.isAssignableFrom(fromClass) && this.toClass == String.class) {
                switch (Tools.emulateMultiset(Tools.configuration((Result)from))) {
                    case XML: {
                        return (U)((Result)from).formatXML(XMLFormat.DEFAULT_FOR_RECORDS);
                    }
                    case JSON: 
                    case JSONB: {
                        return (U)((Result)from).formatJSON(JSONFormat.DEFAULT_FOR_RECORDS);
                    }
                }
            } else {
                if (Result.class.isAssignableFrom(fromClass) && this.toClass == byte[].class) {
                    return (U)Convert.convert((Object)Convert.convert(from, String.class), byte[].class);
                }
                if (Result.class.isAssignableFrom(fromClass) && this.toClass == ResultSet.class) {
                    return (U)new MockResultSet((Result)from);
                }
                if (Collection.class.isAssignableFrom(fromClass) && !ArrayRecord.class.isAssignableFrom(fromClass) && (this.toClass == java.sql.Array.class || this.toClass.isArray())) {
                    Object[] fromArray = ((Collection)from).toArray();
                    if (this.toClass == java.sql.Array.class) {
                        return (U)new MockArray<Object>(null, fromArray, fromClass);
                    }
                    return (U)Convert.convertArray(fromArray, this.toClass);
                }
                if (this.toClass == Optional.class) {
                    return (U)Optional.of(from);
                }
                if (this.toClass == String.class) {
                    if (from instanceof EnumType) {
                        EnumType e = (EnumType)from;
                        return (U)e.getLiteral();
                    }
                    return (U)from.toString();
                }
                if (this.toClass == byte[].class) {
                    if (from instanceof UUID) {
                        UUID u = (UUID)from;
                        ByteBuffer b = ByteBuffer.wrap(new byte[16]);
                        b.putLong(u.getMostSignificantBits());
                        b.putLong(u.getLeastSignificantBits());
                        return (U)b.array();
                    }
                    if (from instanceof ByteBuffer) {
                        ByteBuffer b = (ByteBuffer)from;
                        return (U)b.array();
                    }
                    return (U)from.toString().getBytes();
                }
                if (wrapperTo == Byte.class) {
                    if (Number.class.isAssignableFrom(fromClass)) {
                        return (U)Byte.valueOf(((Number)from).byteValue());
                    }
                    if (wrapperFrom == Boolean.class) {
                        return (U)((Boolean)from != false ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0));
                    }
                    try {
                        String fromString = from.toString().trim();
                        Integer asInt = Ints.tryParse(fromString);
                        return (U)Byte.valueOf(asInt != null ? asInt.byteValue() : new BigDecimal(fromString).byteValue());
                    }
                    catch (NumberFormatException e) {
                        return Reflect.initValue(this.toClass);
                    }
                }
                if (wrapperTo == Short.class) {
                    if (Number.class.isAssignableFrom(fromClass)) {
                        return (U)Short.valueOf(((Number)from).shortValue());
                    }
                    if (wrapperFrom == Boolean.class) {
                        return (U)((Boolean)from != false ? Short.valueOf((short)1) : Short.valueOf((short)0));
                    }
                    try {
                        String fromString = from.toString().trim();
                        Integer asInt = Ints.tryParse(fromString);
                        return (U)Short.valueOf(asInt != null ? asInt.shortValue() : new BigDecimal(fromString).shortValue());
                    }
                    catch (NumberFormatException e) {
                        return Reflect.initValue(this.toClass);
                    }
                }
                if (wrapperTo == Integer.class) {
                    if (Number.class.isAssignableFrom(fromClass)) {
                        return (U)Integer.valueOf(((Number)from).intValue());
                    }
                    if (wrapperFrom == Boolean.class) {
                        return (U)((Boolean)from != false ? Integer.valueOf(1) : Integer.valueOf(0));
                    }
                    try {
                        String fromString = from.toString().trim();
                        Integer asInt = Ints.tryParse(fromString);
                        return (U)Integer.valueOf(asInt != null ? asInt.intValue() : new BigDecimal(fromString).intValue());
                    }
                    catch (NumberFormatException e) {
                        return Reflect.initValue(this.toClass);
                    }
                }
                if (wrapperTo == Long.class) {
                    if (Number.class.isAssignableFrom(fromClass)) {
                        return (U)Long.valueOf(((Number)from).longValue());
                    }
                    if (wrapperFrom == Boolean.class) {
                        return (U)((Boolean)from != false ? Long.valueOf(1L) : Long.valueOf(0L));
                    }
                    if (wrapperFrom == Year.class) {
                        return (U)Long.valueOf(((Year)from).getValue());
                    }
                    if (Date.class.isAssignableFrom(fromClass)) {
                        return (U)Long.valueOf(((Date)from).getTime());
                    }
                    if (Temporal.class.isAssignableFrom(fromClass)) {
                        return (U)Long.valueOf(ConvertAll.millis((Temporal)from));
                    }
                    try {
                        String fromString = from.toString().trim();
                        Long asLong = Longs.tryParse(fromString);
                        return (U)Long.valueOf(asLong != null ? asLong.longValue() : new BigDecimal(fromString).longValue());
                    }
                    catch (NumberFormatException e) {
                        return Reflect.initValue(this.toClass);
                    }
                }
                if (this.toClass == UByte.class) {
                    try {
                        if (Number.class.isAssignableFrom(fromClass)) {
                            return (U)Unsigned.ubyte(((Number)from).shortValue());
                        }
                        if (wrapperFrom == Boolean.class) {
                            return (U)((Boolean)from != false ? Unsigned.ubyte(1) : Unsigned.ubyte(0));
                        }
                        String fromString = from.toString().trim();
                        Integer asInt = Ints.tryParse(fromString);
                        return (U)Unsigned.ubyte(asInt != null ? asInt.shortValue() : new BigDecimal(fromString).shortValue());
                    }
                    catch (NumberFormatException e) {
                        return null;
                    }
                }
                if (this.toClass == UShort.class) {
                    try {
                        if (Number.class.isAssignableFrom(fromClass)) {
                            return (U)Unsigned.ushort(((Number)from).intValue());
                        }
                        if (wrapperFrom == Boolean.class) {
                            return (U)((Boolean)from != false ? Unsigned.ushort(1) : Unsigned.ushort(0));
                        }
                        String fromString = from.toString().trim();
                        Integer asInt = Ints.tryParse(fromString);
                        return (U)Unsigned.ushort(asInt != null ? asInt.intValue() : new BigDecimal(fromString).intValue());
                    }
                    catch (NumberFormatException e) {
                        return null;
                    }
                }
                if (this.toClass == UInteger.class) {
                    try {
                        if (Number.class.isAssignableFrom(fromClass)) {
                            return (U)Unsigned.uint(((Number)from).longValue());
                        }
                        if (wrapperFrom == Boolean.class) {
                            return (U)((Boolean)from != false ? Unsigned.uint(1) : Unsigned.uint(0));
                        }
                        String fromString = from.toString().trim();
                        Long asLong = Longs.tryParse(fromString);
                        return (U)Unsigned.uint(asLong != null ? asLong.longValue() : new BigDecimal(fromString).longValue());
                    }
                    catch (NumberFormatException e) {
                        return null;
                    }
                }
                if (this.toClass == ULong.class) {
                    if (wrapperFrom == Boolean.class) {
                        return (U)((Boolean)from != false ? Unsigned.ulong(1L) : Unsigned.ulong(0L));
                    }
                    if (Date.class.isAssignableFrom(fromClass)) {
                        return (U)Unsigned.ulong(((Date)from).getTime());
                    }
                    if (Temporal.class.isAssignableFrom(fromClass)) {
                        return (U)Unsigned.ulong(ConvertAll.millis((Temporal)from));
                    }
                    try {
                        String fromString = from.toString().trim();
                        Long asLong = Longs.tryParse(fromString);
                        return (U)(asLong != null ? Unsigned.ulong(asLong) : Unsigned.ulong(new BigDecimal(fromString).toBigInteger()));
                    }
                    catch (NumberFormatException e) {
                        return null;
                    }
                }
                if (wrapperTo == Float.class) {
                    if (Number.class.isAssignableFrom(fromClass)) {
                        return (U)Float.valueOf(((Number)from).floatValue());
                    }
                    if (wrapperFrom == Boolean.class) {
                        return (U)((Boolean)from != false ? Float.valueOf(1.0f) : Float.valueOf(0.0f));
                    }
                    try {
                        return (U)Float.valueOf(from.toString().trim());
                    }
                    catch (NumberFormatException e) {
                        return Reflect.initValue(this.toClass);
                    }
                }
                if (wrapperTo == Double.class) {
                    if (Number.class.isAssignableFrom(fromClass)) {
                        return (U)Double.valueOf(((Number)from).doubleValue());
                    }
                    if (wrapperFrom == Boolean.class) {
                        return (U)((Boolean)from != false ? Double.valueOf(1.0) : Double.valueOf(0.0));
                    }
                    try {
                        return (U)Double.valueOf(from.toString().trim());
                    }
                    catch (NumberFormatException e) {
                        return Reflect.initValue(this.toClass);
                    }
                }
                if (this.toClass == BigDecimal.class) {
                    if (wrapperFrom == Boolean.class) {
                        return (U)((Boolean)from != false ? BigDecimal.ONE : BigDecimal.ZERO);
                    }
                    try {
                        return (U)new BigDecimal(from.toString().trim());
                    }
                    catch (NumberFormatException e) {
                        return null;
                    }
                }
                if (this.toClass == BigInteger.class) {
                    if (wrapperFrom == Boolean.class) {
                        return (U)((Boolean)from != false ? BigInteger.ONE : BigInteger.ZERO);
                    }
                    try {
                        return (U)new BigDecimal(from.toString().trim()).toBigInteger();
                    }
                    catch (NumberFormatException e) {
                        return null;
                    }
                }
                if (this.toClass == Year.class) {
                    if (Number.class.isAssignableFrom(wrapperFrom)) {
                        return (U)Year.of(((Number)from).intValue());
                    }
                    try {
                        return (U)Year.parse(from.toString().trim());
                    }
                    catch (DateTimeParseException e) {
                        return null;
                    }
                }
                if (wrapperTo == Boolean.class) {
                    String s2 = from.toString().toLowerCase().trim();
                    if (TRUE_VALUES.contains(s2)) {
                        return (U)Boolean.TRUE;
                    }
                    if (FALSE_VALUES.contains(s2)) {
                        return (U)Boolean.FALSE;
                    }
                    return (U)(this.toClass == Boolean.class ? null : Boolean.valueOf(false));
                }
                if (wrapperTo == Character.class) {
                    if (wrapperFrom == Boolean.class) {
                        return (U)((Boolean)from != false ? Character.valueOf('1') : Character.valueOf('0'));
                    }
                    if (from.toString().length() < 1) {
                        return Reflect.initValue(this.toClass);
                    }
                    return (U)Character.valueOf(from.toString().charAt(0));
                }
                if (fromClass == String.class && this.toClass == URL.class) {
                    try {
                        return (U)new URI(from.toString()).toURL();
                    }
                    catch (Exception e) {
                        return null;
                    }
                }
                if (Date.class.isAssignableFrom(fromClass)) {
                    if (Timestamp.class == fromClass) {
                        if (LocalDateTime.class == this.toClass) {
                            return (U)((Timestamp)from).toLocalDateTime();
                        }
                        return ConvertAll.toDate(((Timestamp)from).getTime(), ((Timestamp)from).getNanos(), this.toClass);
                    }
                    if (java.sql.Date.class == fromClass && LocalDate.class == this.toClass) {
                        return (U)((java.sql.Date)from).toLocalDate();
                    }
                    if (Time.class == fromClass && LocalTime.class == this.toClass) {
                        return (U)((Time)from).toLocalTime();
                    }
                    return ConvertAll.toDate(((Date)from).getTime(), this.toClass);
                }
                if (Temporal.class.isAssignableFrom(fromClass)) {
                    if (LocalDateTime.class == fromClass && Timestamp.class == this.toClass) {
                        return (U)Timestamp.valueOf((LocalDateTime)from);
                    }
                    if (LocalDateTime.class == fromClass && Temporal.class.isAssignableFrom(this.toClass)) {
                        return ConvertAll.toDate(((LocalDateTime)from).toInstant(OffsetTime.now().getOffset()).toEpochMilli(), ((LocalDateTime)from).getNano(), this.toClass);
                    }
                    if (LocalDate.class == fromClass && java.sql.Date.class == this.toClass) {
                        return (U)java.sql.Date.valueOf((LocalDate)from);
                    }
                    if (LocalTime.class == fromClass && Time.class == this.toClass) {
                        return (U)Time.valueOf((LocalTime)from);
                    }
                    if (OffsetDateTime.class == fromClass && (Timestamp.class == this.toClass || Temporal.class.isAssignableFrom(this.toClass))) {
                        return ConvertAll.toDate(((OffsetDateTime)from).toInstant().toEpochMilli(), ((OffsetDateTime)from).getNano(), this.toClass);
                    }
                    if (Instant.class == fromClass && (Timestamp.class == this.toClass || Temporal.class.isAssignableFrom(this.toClass))) {
                        return ConvertAll.toDate(((Instant)from).toEpochMilli(), ((Instant)from).getNano(), this.toClass);
                    }
                    return ConvertAll.toDate(Convert.convert(from, Long.class), this.toClass);
                }
                if (wrapperFrom == Long.class && Date.class.isAssignableFrom(this.toClass)) {
                    return ConvertAll.toDate((Long)from, this.toClass);
                }
                if (wrapperFrom == Long.class && Temporal.class.isAssignableFrom(this.toClass)) {
                    return ConvertAll.toDate((Long)from, this.toClass);
                }
                if (fromClass == String.class && this.toClass == java.sql.Date.class) {
                    try {
                        return (U)java.sql.Date.valueOf((String)from);
                    }
                    catch (IllegalArgumentException e) {
                        return null;
                    }
                }
                if (fromClass == String.class && this.toClass == Time.class) {
                    try {
                        return (U)Time.valueOf(Convert.patchFractionalSeconds(Convert.patchIso8601Time((String)from)));
                    }
                    catch (IllegalArgumentException e) {
                        return null;
                    }
                }
                if (fromClass == String.class && this.toClass == Timestamp.class) {
                    try {
                        return (U)Timestamp.valueOf(Convert.patchIso8601Timestamp((String)from, false));
                    }
                    catch (IllegalArgumentException e) {
                        return null;
                    }
                }
                if (fromClass == String.class && this.toClass == LocalDate.class) {
                    try {
                        return (U)java.sql.Date.valueOf((String)from).toLocalDate();
                    }
                    catch (IllegalArgumentException e1) {
                        try {
                            return (U)LocalDate.parse((String)from);
                        }
                        catch (DateTimeParseException e2) {
                            return null;
                        }
                    }
                }
                if (fromClass == String.class && this.toClass == LocalTime.class) {
                    try {
                        return (U)LocalTime.parse(Convert.patchIso8601Time((String)from));
                    }
                    catch (DateTimeParseException e2) {
                        return null;
                    }
                }
                if (fromClass == String.class && this.toClass == OffsetTime.class) {
                    try {
                        return (U)Time.valueOf((String)from).toLocalTime().atOffset(OffsetTime.now().getOffset());
                    }
                    catch (IllegalArgumentException e1) {
                        try {
                            return (U)OffsetTime.parse((String)from);
                        }
                        catch (DateTimeParseException e2) {
                            return null;
                        }
                    }
                }
                if (fromClass == String.class && this.toClass == LocalDateTime.class) {
                    try {
                        return (U)LocalDateTime.parse(Convert.patchIso8601Timestamp((String)from, true));
                    }
                    catch (DateTimeParseException e2) {
                        return null;
                    }
                }
                if (fromClass == String.class && this.toClass == OffsetDateTime.class) {
                    try {
                        return (U)Timestamp.valueOf((String)from).toLocalDateTime().atOffset(OffsetDateTime.now().getOffset());
                    }
                    catch (IllegalArgumentException e1) {
                        try {
                            return (U)OffsetDateTime.parse(Convert.patchIso8601Timestamp((String)from, true));
                        }
                        catch (DateTimeParseException e2) {
                            return null;
                        }
                    }
                }
                if (fromClass == String.class && this.toClass == Instant.class) {
                    try {
                        return (U)Timestamp.valueOf((String)from).toLocalDateTime().atOffset(OffsetDateTime.now().getOffset()).toInstant();
                    }
                    catch (IllegalArgumentException e1) {
                        try {
                            return (U)OffsetDateTime.parse(Convert.patchIso8601Timestamp((String)from, true)).toInstant();
                        }
                        catch (DateTimeParseException e2) {
                            return null;
                        }
                    }
                }
                if (fromClass == String.class && this.toClass == YearToMonth.class) {
                    YearToMonth r = YearToMonth.valueOf((String)from);
                    if (r != null) {
                        return (U)r;
                    }
                    if (((String)from).startsWith("INTERVAL")) {
                        try {
                            r = (YearToMonth)((Param)scope.dsl().parser().parseField((String)from)).getValue();
                            return (U)r;
                        }
                        catch (Exception e2) {
                            // empty catch block
                        }
                    }
                    try {
                        return (U)PostgresUtils.toYearToMonth(from);
                    }
                    catch (Exception e) {
                        return null;
                    }
                }
                if (fromClass == String.class && this.toClass == DayToSecond.class) {
                    DayToSecond r = DayToSecond.valueOf((String)from);
                    if (r != null) {
                        return (U)r;
                    }
                    if (((String)from).startsWith("INTERVAL")) {
                        try {
                            r = (DayToSecond)((Param)scope.dsl().parser().parseField((String)from)).getValue();
                            return (U)r;
                        }
                        catch (Exception e) {
                            // empty catch block
                        }
                    }
                    try {
                        return (U)PostgresUtils.toDayToSecond(from);
                    }
                    catch (Exception e) {
                        return null;
                    }
                }
                if (fromClass == String.class && this.toClass == YearToSecond.class) {
                    YearToSecond r = YearToSecond.valueOf((String)from);
                    if (r != null) {
                        return (U)r;
                    }
                    try {
                        return (U)PostgresUtils.toYearToSecond(from);
                    }
                    catch (Exception e) {
                        return null;
                    }
                }
                if (Enum.class.isAssignableFrom(this.toClass) && (fromClass == String.class || from instanceof Enum || from instanceof EnumType)) {
                    try {
                        String fromString;
                        if (fromClass == String.class) {
                            v0 = (String)from;
                        } else if (from instanceof EnumType) {
                            U[] e = (U[])from;
                            v0 = e.getLiteral();
                        } else {
                            v0 = fromString = ((Enum)from).name();
                        }
                        if (fromString == null) {
                            return null;
                        }
                        if (EnumType.class.isAssignableFrom(this.toClass)) {
                            for (U value : this.toClass.getEnumConstants()) {
                                if (!fromString.equals(((EnumType)value).getLiteral())) continue;
                                return value;
                            }
                            return null;
                        }
                        return Enum.valueOf(this.toClass, fromString);
                    }
                    catch (IllegalArgumentException e) {
                        return null;
                    }
                }
                if (fromClass == String.class && this.toClass == UUID.class) {
                    try {
                        return (U)ConvertAll.parseUUID((String)from);
                    }
                    catch (IllegalArgumentException e) {
                        return null;
                    }
                }
                if (fromClass == String.class && this.toClass == JSON.class) {
                    return (U)JSON.valueOf((String)from);
                }
                if (fromClass == String.class && this.toClass == JSONB.class) {
                    return (U)JSONB.valueOf((String)from);
                }
                if (Map.class.isAssignableFrom(fromClass) && this.toClass == JSON.class) {
                    return (U)JSON.valueOf(JSONObject.toJSONString((Map)from));
                }
                if (Map.class.isAssignableFrom(fromClass) && this.toClass == JSONB.class) {
                    return (U)JSONB.valueOf(JSONObject.toJSONString((Map)from));
                }
                if (List.class.isAssignableFrom(fromClass) && this.toClass == JSON.class) {
                    return (U)JSON.valueOf(JSONArray.toJSONString((List)from));
                }
                if (List.class.isAssignableFrom(fromClass) && this.toClass == JSONB.class) {
                    return (U)JSONB.valueOf(JSONArray.toJSONString((List)from));
                }
                if (fromClass == JSON.class && Map.class.isAssignableFrom(this.toClass)) {
                    try {
                        return ConvertAll.require(this.toClass, new JSONParser().parse(((JSON)from).data(), ConvertAll.containerFactoryForMaps(this.toClass)));
                    }
                    catch (ParseException e) {
                        throw new DataTypeException("Error while mapping JSON to Map", e);
                    }
                }
                if (fromClass == JSONB.class && Map.class.isAssignableFrom(this.toClass)) {
                    try {
                        return ConvertAll.require(this.toClass, new JSONParser().parse(((JSONB)from).data(), ConvertAll.containerFactoryForMaps(this.toClass)));
                    }
                    catch (ParseException e) {
                        throw new DataTypeException("Error while mapping JSONB to Map", e);
                    }
                }
                if (fromClass == JSON.class && List.class.isAssignableFrom(this.toClass)) {
                    try {
                        return ConvertAll.require(this.toClass, new JSONParser().parse(((JSON)from).data(), ConvertAll.containerFactoryForLists(this.toClass)));
                    }
                    catch (ParseException e) {
                        throw new DataTypeException("Error while mapping JSON to List", e);
                    }
                }
                if (fromClass == JSONB.class && List.class.isAssignableFrom(this.toClass)) {
                    try {
                        return ConvertAll.require(this.toClass, new JSONParser().parse(((JSONB)from).data(), ConvertAll.containerFactoryForLists(this.toClass)));
                    }
                    catch (ParseException e) {
                        throw new DataTypeException("Error while mapping JSONB to List", e);
                    }
                }
                if (fromClass == JSON.class && _JSON.JSON_MAPPER != null) {
                    try {
                        return (U)_JSON.JSON_READ_METHOD.invoke(_JSON.JSON_MAPPER, ((JSON)from).data(), this.toClass);
                    }
                    catch (Exception e) {
                        throw new DataTypeException("Error while mapping JSON to POJO using Jackson", e);
                    }
                }
                if (fromClass == JSONB.class && _JSON.JSON_MAPPER != null) {
                    try {
                        return (U)_JSON.JSON_READ_METHOD.invoke(_JSON.JSON_MAPPER, ((JSONB)from).data(), this.toClass);
                    }
                    catch (Exception e) {
                        throw new DataTypeException("Error while mapping JSON to POJO using Jackson", e);
                    }
                }
                if (Map.class.isAssignableFrom(fromClass) && _JSON.JSON_MAPPER != null) {
                    try {
                        return (U)_JSON.JSON_READ_METHOD.invoke(_JSON.JSON_MAPPER, _JSON.JSON_WRITE_METHOD.invoke(_JSON.JSON_MAPPER, from), this.toClass);
                    }
                    catch (Exception e) {
                        throw new DataTypeException("Error while mapping JSON to POJO using Jackson", e);
                    }
                }
                if (fromClass == XML.class && _XML.JAXB_AVAILABLE) {
                    try {
                        return (U)JAXB.unmarshal((Reader)new StringReader(((XML)from).data()), this.toClass);
                    }
                    catch (Exception e) {
                        throw new DataTypeException("Error while mapping XML to POJO using JAXB", e);
                    }
                }
                if (Record.class.isAssignableFrom(fromClass)) {
                    Record record = (Record)from;
                    return (U)record.into(this.toClass);
                }
                if (Struct.class.isAssignableFrom(fromClass)) {
                    Struct struct = (Struct)from;
                    if (QualifiedRecord.class.isAssignableFrom(this.toClass)) {
                        try {
                            QualifiedRecord record = (QualifiedRecord)this.toClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                            record.from(struct.getAttributes());
                            return (U)record;
                        }
                        catch (Exception e) {
                            throw new DataTypeException("Cannot convert from " + String.valueOf(fromClass) + " to " + String.valueOf(this.toClass), e);
                        }
                    }
                } else if (ArrayRecord.class.isAssignableFrom(fromClass)) {
                    ArrayRecord record = (ArrayRecord)from;
                    if (Collection.class.isAssignableFrom(this.toClass)) {
                        try {
                            AbstractCollection c = !this.toClass.isInterface() ? (LinkedHashSet)this.toClass.newInstance() : (Set.class.isAssignableFrom(this.toClass) ? new LinkedHashSet() : new ArrayList());
                            c.addAll(record);
                            return (U)c;
                        }
                        catch (Exception e) {
                            throw new DataTypeException("Cannot convert from " + String.valueOf(fromClass) + " to " + String.valueOf(this.toClass), e);
                        }
                    }
                    if (this.toClass == java.sql.Array.class) {
                        return (U)new MockArray<Object>(null, record.toArray((Object[])Array.newInstance(record.getDataType().getType(), record.size())), record.getArrayType().getType());
                    }
                    return (U)Convert.convertArray(record.toArray((Object[])Array.newInstance(record.getDataType().getType(), record.size())), this.toClass);
                }
            }
            for (Constructor<?> constructor : this.toClass.getConstructors()) {
                types = constructor.getParameterTypes();
                if (types.length != 1 || types[0] == this.toClass) continue;
                try {
                    return (U)constructor.newInstance(Convert.convert(from, types[0]));
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            for (Constructor<?> constructor : this.toClass.getDeclaredConstructors()) {
                types = constructor.getParameterTypes();
                if (types.length != 1 || types[0] == this.toClass) continue;
                try {
                    return (U)Reflect.accessible(constructor).newInstance(Convert.convert(from, types[0]));
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            throw ConvertAll.fail(from, this.toClass);
        }

        private static final <T> T require(Class<? extends T> type, Object o) {
            if (o == null || type.isInstance(o)) {
                return (T)o;
            }
            throw new DataTypeException("Type " + String.valueOf(type) + " expected. Got: " + String.valueOf(o.getClass()));
        }

        private static final ContainerFactory containerFactoryForMaps(final Class<?> mapClass) {
            return new ContainerFactory(){

                @Override
                public Map createObjectContainer() {
                    try {
                        if (mapClass == Map.class) {
                            return new LinkedHashMap();
                        }
                        return (Map)mapClass.getConstructor(new Class[0]).newInstance(new Object[0]);
                    }
                    catch (Exception e) {
                        throw new DataTypeException("Error while mapping JSON to Map", e);
                    }
                }

                @Override
                public List createArrayContainer() {
                    return new ArrayList();
                }
            };
        }

        private static final ContainerFactory containerFactoryForLists(final Class<?> listClass) {
            return new ContainerFactory(){

                @Override
                public Map createObjectContainer() {
                    return new LinkedHashMap();
                }

                @Override
                public List createArrayContainer() {
                    try {
                        if (listClass == List.class) {
                            return new ArrayList();
                        }
                        return (List)listClass.getConstructor(new Class[0]).newInstance(new Object[0]);
                    }
                    catch (Exception e) {
                        throw new DataTypeException("Error while mapping JSON to List", e);
                    }
                }
            };
        }

        @Override
        public Object to(U to, ConverterContext scope) {
            return to;
        }

        private static <X> X toDate(long time, Class<X> toClass) {
            return ConvertAll.toDate(time, 0, toClass);
        }

        private static <X> X toDate(long time, int nanos, Class<X> toClass) {
            if (toClass == java.sql.Date.class) {
                return (X)new java.sql.Date(time);
            }
            if (toClass == Time.class) {
                return (X)new Time(time);
            }
            if (toClass == Timestamp.class) {
                return (X)ConvertAll.toTimestamp(time, nanos);
            }
            if (toClass == Date.class) {
                return (X)new Date(time);
            }
            if (toClass == Calendar.class) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(time);
                return (X)calendar;
            }
            if (toClass == LocalDate.class) {
                return (X)new java.sql.Date(time).toLocalDate();
            }
            if (toClass == LocalTime.class) {
                return (X)new Time(time).toLocalTime();
            }
            if (toClass == OffsetTime.class) {
                return (X)new Time(time).toLocalTime().atOffset(OffsetTime.now().getOffset());
            }
            if (toClass == LocalDateTime.class) {
                return (X)ConvertAll.toTimestamp(time, nanos).toLocalDateTime();
            }
            if (toClass == OffsetDateTime.class) {
                return (X)ConvertAll.toTimestamp(time, nanos).toLocalDateTime().atOffset(OffsetDateTime.now().getOffset());
            }
            if (toClass == Instant.class) {
                if ((long)nanos == 0L) {
                    return (X)Instant.ofEpochMilli(time);
                }
                return (X)Instant.ofEpochSecond(time / 1000L, nanos);
            }
            throw ConvertAll.fail(time, toClass);
        }

        private static Timestamp toTimestamp(long time, int nanos) {
            if ((long)nanos == 0L) {
                return new Timestamp(time);
            }
            Timestamp ts = new Timestamp(time / 1000L * 1000L);
            ts.setNanos(nanos);
            return ts;
        }

        private static final long millis(Temporal temporal) {
            if (temporal instanceof LocalDate) {
                LocalDate ld = (LocalDate)temporal;
                return java.sql.Date.valueOf(ld).getTime();
            }
            if (temporal instanceof LocalTime) {
                LocalTime lt = (LocalTime)temporal;
                return Time.valueOf(lt).getTime();
            }
            if (temporal instanceof LocalDateTime) {
                LocalDateTime ldt = (LocalDateTime)temporal;
                return Timestamp.valueOf(ldt).getTime();
            }
            if (temporal.isSupported(ChronoField.INSTANT_SECONDS)) {
                return 1000L * temporal.getLong(ChronoField.INSTANT_SECONDS) + temporal.getLong(ChronoField.MILLI_OF_SECOND);
            }
            if (temporal.isSupported(ChronoField.MILLI_OF_DAY)) {
                return temporal.getLong(ChronoField.MILLI_OF_DAY);
            }
            throw ConvertAll.fail(temporal, Long.class);
        }

        private static final UUID parseUUID(String string) {
            if (string == null) {
                return null;
            }
            if (string.contains("-")) {
                return UUID.fromString(string);
            }
            return UUID.fromString(UUID_PATTERN.matcher(string).replaceAll("$1-$2-$3-$4-$5"));
        }

        private static final DataTypeException fail(Object from, Class<?> toClass) {
            String message = "Cannot convert from " + String.valueOf(from) + " (" + String.valueOf(from.getClass()) + ") to " + String.valueOf(toClass);
            if ((from instanceof JSON || from instanceof JSONB) && _JSON.JSON_MAPPER == null) {
                return new DataTypeException(message + ". Check your classpath to see if Jackson or Gson is available to jOOQ.");
            }
            if (from instanceof XML && !_XML.JAXB_AVAILABLE) {
                return new DataTypeException(message + ". Check your classpath to see if JAXB is available to jOOQ.");
            }
            return new DataTypeException(message);
        }
    }

    private static final class _XML {
        private static final boolean JAXB_AVAILABLE;

        private _XML() {
        }

        static {
            boolean jaxbAvailable = false;
            try {
                JAXB.marshal((Object)new InformationSchema(), (Writer)new StringWriter());
                jaxbAvailable = true;
                log.debug("JAXB is available");
            }
            catch (Throwable t2) {
                log.debug((Object)"JAXB not available", t2.getMessage());
            }
            JAXB_AVAILABLE = jaxbAvailable;
        }
    }

    private static final class _JSON {
        private static final Object JSON_MAPPER;
        private static final Method JSON_READ_METHOD;
        private static final Method JSON_WRITE_METHOD;

        private _JSON() {
        }

        static {
            Object jsonMapper = null;
            Method jsonReadMethod = null;
            Method jsonWriteMethod = null;
            try {
                Class<?> klass = Class.forName("com.fasterxml.jackson.databind.ObjectMapper");
                try {
                    Class<?> kotlin = Class.forName("com.fasterxml.jackson.module.kotlin.ExtensionsKt");
                    jsonMapper = kotlin.getMethod("jacksonObjectMapper", new Class[0]).invoke(kotlin, new Object[0]);
                    log.debug("Jackson kotlin module is available");
                }
                catch (Exception e) {
                    jsonMapper = klass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    log.debug("Jackson kotlin module is not available");
                }
                jsonReadMethod = klass.getMethod("readValue", String.class, Class.class);
                jsonWriteMethod = klass.getMethod("writeValueAsString", Object.class);
                log.debug("Jackson is available");
            }
            catch (Exception e1) {
                log.debug((Object)"Jackson not available", e1.getMessage());
                try {
                    Class<?> klass = Class.forName("com.google.gson.Gson");
                    jsonMapper = klass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    jsonReadMethod = klass.getMethod("fromJson", String.class, Class.class);
                    jsonWriteMethod = klass.getMethod("toJson", Object.class);
                    log.debug("Gson is available");
                }
                catch (Exception e2) {
                    log.debug((Object)"Gson not available", e2.getMessage());
                }
            }
            JSON_MAPPER = jsonMapper;
            JSON_READ_METHOD = jsonReadMethod;
            JSON_WRITE_METHOD = jsonWriteMethod;
        }
    }
}

