/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.h2.api.Interval;
import org.h2.engine.Mode;
import org.h2.engine.Session;
import org.h2.jdbc.JdbcArray;
import org.h2.jdbc.JdbcBlob;
import org.h2.jdbc.JdbcClob;
import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcLob;
import org.h2.jdbc.JdbcResultSet;
import org.h2.jdbc.JdbcSQLXML;
import org.h2.message.DbException;
import org.h2.message.TraceObject;
import org.h2.result.ResultInterface;
import org.h2.util.JSR310Utils;
import org.h2.util.JdbcUtils;
import org.h2.util.LegacyDateTimeUtils;
import org.h2.value.DataType;
import org.h2.value.ExtTypeInfoRow;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueArray;
import org.h2.value.ValueBigint;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueChar;
import org.h2.value.ValueCollectionBase;
import org.h2.value.ValueDouble;
import org.h2.value.ValueGeometry;
import org.h2.value.ValueInteger;
import org.h2.value.ValueInterval;
import org.h2.value.ValueJavaObject;
import org.h2.value.ValueLob;
import org.h2.value.ValueNull;
import org.h2.value.ValueNumeric;
import org.h2.value.ValueReal;
import org.h2.value.ValueRow;
import org.h2.value.ValueSmallint;
import org.h2.value.ValueTinyint;
import org.h2.value.ValueUuid;
import org.h2.value.ValueVarbinary;
import org.h2.value.ValueVarchar;

public final class ValueToObjectConverter
extends TraceObject {
    public static final Class<?> GEOMETRY_CLASS;
    private static final String GEOMETRY_CLASS_NAME = "org.locationtech.jts.geom.Geometry";

    public static Value objectToValue(Session session, Object object, int n) {
        if (object == null) {
            return ValueNull.INSTANCE;
        }
        if (n == 35) {
            return ValueJavaObject.getNoCopy(JdbcUtils.serialize(object, session.getJavaObjectSerializer()));
        }
        if (object instanceof Value) {
            Value value = (Value)object;
            if (value instanceof ValueLob) {
                session.addTemporaryLob((ValueLob)value);
            }
            return value;
        }
        Class<?> clazz = object.getClass();
        if (clazz == String.class) {
            return ValueVarchar.get((String)object, session);
        }
        if (clazz == Long.class) {
            return ValueBigint.get((Long)object);
        }
        if (clazz == Integer.class) {
            return ValueInteger.get((Integer)object);
        }
        if (clazz == Boolean.class) {
            return ValueBoolean.get((Boolean)object);
        }
        if (clazz == Byte.class) {
            return ValueTinyint.get((Byte)object);
        }
        if (clazz == Short.class) {
            return ValueSmallint.get((Short)object);
        }
        if (clazz == Float.class) {
            return ValueReal.get(((Float)object).floatValue());
        }
        if (clazz == Double.class) {
            return ValueDouble.get((Double)object);
        }
        if (clazz == byte[].class) {
            return ValueVarbinary.get((byte[])object);
        }
        if (clazz == UUID.class) {
            return ValueUuid.get((UUID)object);
        }
        if (clazz == Character.class) {
            return ValueChar.get(((Character)object).toString());
        }
        if (clazz == LocalDate.class) {
            return JSR310Utils.localDateToValue((LocalDate)object);
        }
        if (clazz == LocalTime.class) {
            return JSR310Utils.localTimeToValue((LocalTime)object);
        }
        if (clazz == LocalDateTime.class) {
            return JSR310Utils.localDateTimeToValue((LocalDateTime)object);
        }
        if (clazz == Instant.class) {
            return JSR310Utils.instantToValue((Instant)object);
        }
        if (clazz == OffsetTime.class) {
            return JSR310Utils.offsetTimeToValue((OffsetTime)object);
        }
        if (clazz == OffsetDateTime.class) {
            return JSR310Utils.offsetDateTimeToValue((OffsetDateTime)object);
        }
        if (clazz == ZonedDateTime.class) {
            return JSR310Utils.zonedDateTimeToValue((ZonedDateTime)object);
        }
        if (clazz == Interval.class) {
            Interval interval = (Interval)object;
            return ValueInterval.from(interval.getQualifier(), interval.isNegative(), interval.getLeading(), interval.getRemaining());
        }
        if (clazz == Period.class) {
            return JSR310Utils.periodToValue((Period)object);
        }
        if (clazz == Duration.class) {
            return JSR310Utils.durationToValue((Duration)object);
        }
        if (object instanceof Object[]) {
            return ValueToObjectConverter.arrayToValue(session, object);
        }
        if (GEOMETRY_CLASS != null && GEOMETRY_CLASS.isAssignableFrom(clazz)) {
            return ValueGeometry.getFromGeometry(object);
        }
        if (object instanceof BigInteger) {
            return ValueNumeric.get((BigInteger)object);
        }
        if (object instanceof BigDecimal) {
            return ValueNumeric.getAnyScale((BigDecimal)object);
        }
        return ValueToObjectConverter.otherToValue(session, object);
    }

    private static Value otherToValue(Session session, Object object) {
        ValueLob valueLob;
        if (object instanceof java.sql.Array) {
            java.sql.Array array = (java.sql.Array)object;
            try {
                return ValueToObjectConverter.arrayToValue(session, array.getArray());
            }
            catch (SQLException sQLException) {
                throw DbException.convert(sQLException);
            }
        }
        if (object instanceof ResultSet) {
            return ValueToObjectConverter.resultSetToValue(session, (ResultSet)object);
        }
        if (object instanceof Reader) {
            Reader reader = (Reader)object;
            if (!(reader instanceof BufferedReader)) {
                reader = new BufferedReader(reader);
            }
            valueLob = session.getDataHandler().getLobStorage().createClob(reader, -1L);
        } else if (object instanceof Clob) {
            try {
                Clob clob = (Clob)object;
                BufferedReader bufferedReader = new BufferedReader(clob.getCharacterStream());
                valueLob = session.getDataHandler().getLobStorage().createClob(bufferedReader, clob.length());
            }
            catch (SQLException sQLException) {
                throw DbException.convert(sQLException);
            }
        } else if (object instanceof InputStream) {
            valueLob = session.getDataHandler().getLobStorage().createBlob((InputStream)object, -1L);
        } else if (object instanceof Blob) {
            try {
                Blob blob = (Blob)object;
                valueLob = session.getDataHandler().getLobStorage().createBlob(blob.getBinaryStream(), blob.length());
            }
            catch (SQLException sQLException) {
                throw DbException.convert(sQLException);
            }
        } else if (object instanceof SQLXML) {
            try {
                valueLob = session.getDataHandler().getLobStorage().createClob(new BufferedReader(((SQLXML)object).getCharacterStream()), -1L);
            }
            catch (SQLException sQLException) {
                throw DbException.convert(sQLException);
            }
        } else {
            Value value = LegacyDateTimeUtils.legacyObjectToValue(session, object);
            if (value != null) {
                return value;
            }
            return ValueJavaObject.getNoCopy(JdbcUtils.serialize(object, session.getJavaObjectSerializer()));
        }
        return session.addTemporaryLob(valueLob);
    }

    private static Value arrayToValue(Session session, Object object) {
        Object[] objectArray = (Object[])object;
        int n = objectArray.length;
        Value[] valueArray = new Value[n];
        for (int i = 0; i < n; ++i) {
            valueArray[i] = ValueToObjectConverter.objectToValue(session, objectArray[i], -1);
        }
        return ValueArray.get(valueArray, session);
    }

    static Value resultSetToValue(Session session, ResultSet resultSet) {
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int n = resultSetMetaData.getColumnCount();
            LinkedHashMap<String, TypeInfo> linkedHashMap = ValueToObjectConverter.readResultSetMeta(session, resultSetMetaData, n);
            if (!resultSet.next()) {
                throw DbException.get(22018, "Empty ResultSet to ROW value");
            }
            Value[] valueArray = new Value[n];
            Iterator<Map.Entry<String, TypeInfo>> iterator2 = linkedHashMap.entrySet().iterator();
            for (int i = 0; i < n; ++i) {
                valueArray[i] = ValueToObjectConverter.objectToValue(session, resultSet.getObject(i + 1), iterator2.next().getValue().getValueType());
            }
            if (resultSet.next()) {
                throw DbException.get(22018, "Multi-row ResultSet to ROW value");
            }
            return ValueRow.get(new ExtTypeInfoRow(linkedHashMap), valueArray);
        }
        catch (SQLException sQLException) {
            throw DbException.convert(sQLException);
        }
    }

    private static LinkedHashMap<String, TypeInfo> readResultSetMeta(Session session, ResultSetMetaData resultSetMetaData, int n) throws SQLException {
        LinkedHashMap<String, TypeInfo> linkedHashMap = new LinkedHashMap<String, TypeInfo>();
        for (int i = 0; i < n; ++i) {
            String string = resultSetMetaData.getColumnLabel(i + 1);
            String string2 = resultSetMetaData.getColumnTypeName(i + 1);
            int n2 = DataType.convertSQLTypeToValueType(resultSetMetaData.getColumnType(i + 1), string2);
            int n3 = resultSetMetaData.getPrecision(i + 1);
            int n4 = resultSetMetaData.getScale(i + 1);
            TypeInfo typeInfo = n2 == 40 && string2.endsWith(" ARRAY") ? TypeInfo.getTypeInfo(40, -1L, 0, TypeInfo.getTypeInfo(DataType.getTypeByName((String)string2.substring((int)0, (int)(string2.length() - 6)), (Mode)session.getMode()).type)) : TypeInfo.getTypeInfo(n2, n3, n4, null);
            linkedHashMap.put(string, typeInfo);
        }
        return linkedHashMap;
    }

    public static <T> T valueToObject(Class<T> clazz, Value value, JdbcConnection jdbcConnection) {
        if (value == ValueNull.INSTANCE) {
            return null;
        }
        if (clazz == BigDecimal.class) {
            return (T)value.getBigDecimal();
        }
        if (clazz == BigInteger.class) {
            return (T)value.getBigDecimal().toBigInteger();
        }
        if (clazz == String.class) {
            return (T)value.getString();
        }
        if (clazz == Boolean.class) {
            return (T)Boolean.valueOf(value.getBoolean());
        }
        if (clazz == Byte.class) {
            return (T)Byte.valueOf(value.getByte());
        }
        if (clazz == Short.class) {
            return (T)Short.valueOf(value.getShort());
        }
        if (clazz == Integer.class) {
            return (T)Integer.valueOf(value.getInt());
        }
        if (clazz == Long.class) {
            return (T)Long.valueOf(value.getLong());
        }
        if (clazz == Float.class) {
            return (T)Float.valueOf(value.getFloat());
        }
        if (clazz == Double.class) {
            return (T)Double.valueOf(value.getDouble());
        }
        if (clazz == UUID.class) {
            return (T)value.convertToUuid().getUuid();
        }
        if (clazz == byte[].class) {
            return (T)value.getBytes();
        }
        if (clazz == Character.class) {
            String string = value.getString();
            return (T)Character.valueOf(string.isEmpty() ? (char)' ' : string.charAt(0));
        }
        if (clazz == Interval.class) {
            if (!(value instanceof ValueInterval)) {
                value = value.convertTo(TypeInfo.TYPE_INTERVAL_DAY_TO_SECOND);
            }
            ValueInterval valueInterval = (ValueInterval)value;
            return (T)new Interval(valueInterval.getQualifier(), false, valueInterval.getLeading(), valueInterval.getRemaining());
        }
        if (clazz == LocalDate.class) {
            return (T)JSR310Utils.valueToLocalDate(value, jdbcConnection);
        }
        if (clazz == LocalTime.class) {
            return (T)JSR310Utils.valueToLocalTime(value, jdbcConnection);
        }
        if (clazz == LocalDateTime.class) {
            return (T)JSR310Utils.valueToLocalDateTime(value, jdbcConnection);
        }
        if (clazz == OffsetTime.class) {
            return (T)JSR310Utils.valueToOffsetTime(value, jdbcConnection);
        }
        if (clazz == OffsetDateTime.class) {
            return (T)JSR310Utils.valueToOffsetDateTime(value, jdbcConnection);
        }
        if (clazz == ZonedDateTime.class) {
            return (T)JSR310Utils.valueToZonedDateTime(value, jdbcConnection);
        }
        if (clazz == Instant.class) {
            return (T)JSR310Utils.valueToInstant(value, jdbcConnection);
        }
        if (clazz == Period.class) {
            return (T)JSR310Utils.valueToPeriod(value);
        }
        if (clazz == Duration.class) {
            return (T)JSR310Utils.valueToDuration(value);
        }
        if (clazz.isArray()) {
            return (T)ValueToObjectConverter.valueToArray(clazz, value, jdbcConnection);
        }
        if (GEOMETRY_CLASS != null && GEOMETRY_CLASS.isAssignableFrom(clazz)) {
            return (T)value.convertToGeometry(null).getGeometry();
        }
        return (T)ValueToObjectConverter.valueToOther(clazz, value, jdbcConnection);
    }

    private static Object valueToArray(Class<?> clazz, Value value, JdbcConnection jdbcConnection) {
        Value[] valueArray = ((ValueArray)value).getList();
        Class<?> clazz2 = clazz.getComponentType();
        int n = valueArray.length;
        Object[] objectArray = (Object[])Array.newInstance(clazz2, n);
        for (int i = 0; i < n; ++i) {
            objectArray[i] = ValueToObjectConverter.valueToObject(clazz2, valueArray[i], jdbcConnection);
        }
        return objectArray;
    }

    private static Object valueToOther(Class<?> clazz, Value value, JdbcConnection jdbcConnection) {
        if (clazz == Object.class) {
            return JdbcUtils.deserialize(value.convertToJavaObject(TypeInfo.TYPE_JAVA_OBJECT, 0, null).getBytesNoCopy(), jdbcConnection.getJavaObjectSerializer());
        }
        if (clazz == InputStream.class) {
            return value.getInputStream();
        }
        if (clazz == Reader.class) {
            return value.getReader();
        }
        if (clazz == java.sql.Array.class) {
            return new JdbcArray(jdbcConnection, value, ValueToObjectConverter.getNextId(16));
        }
        if (clazz == Blob.class) {
            return new JdbcBlob(jdbcConnection, value, JdbcLob.State.WITH_VALUE, ValueToObjectConverter.getNextId(9));
        }
        if (clazz == Clob.class) {
            return new JdbcClob(jdbcConnection, value, JdbcLob.State.WITH_VALUE, ValueToObjectConverter.getNextId(10));
        }
        if (clazz == SQLXML.class) {
            return new JdbcSQLXML(jdbcConnection, value, JdbcLob.State.WITH_VALUE, ValueToObjectConverter.getNextId(17));
        }
        if (clazz == ResultSet.class) {
            return new JdbcResultSet(jdbcConnection, null, null, (ResultInterface)value.convertToAnyRow().getResult(), ValueToObjectConverter.getNextId(4), true, false, false);
        }
        Object object = LegacyDateTimeUtils.valueToLegacyType(clazz, value, jdbcConnection);
        if (object != null) {
            return object;
        }
        if (value.getValueType() == 35 && clazz.isAssignableFrom((object = JdbcUtils.deserialize(value.getBytesNoCopy(), jdbcConnection.getJavaObjectSerializer())).getClass())) {
            return object;
        }
        throw DbException.getUnsupportedException("converting to class " + clazz.getName());
    }

    public static Class<?> getDefaultClass(int n, boolean bl) {
        switch (n) {
            case 0: {
                return Void.class;
            }
            case 1: 
            case 2: 
            case 4: 
            case 36: {
                return String.class;
            }
            case 3: {
                return Clob.class;
            }
            case 5: 
            case 6: 
            case 38: {
                return byte[].class;
            }
            case 7: {
                return Blob.class;
            }
            case 8: {
                return Boolean.class;
            }
            case 9: {
                if (bl) {
                    return Integer.class;
                }
                return Byte.class;
            }
            case 10: {
                if (bl) {
                    return Integer.class;
                }
                return Short.class;
            }
            case 11: {
                return Integer.class;
            }
            case 12: {
                return Long.class;
            }
            case 13: 
            case 16: {
                return BigDecimal.class;
            }
            case 14: {
                return Float.class;
            }
            case 15: {
                return Double.class;
            }
            case 17: {
                return bl ? Date.class : LocalDate.class;
            }
            case 18: {
                return bl ? Time.class : LocalTime.class;
            }
            case 19: {
                return OffsetTime.class;
            }
            case 20: {
                return bl ? Timestamp.class : LocalDateTime.class;
            }
            case 21: {
                return OffsetDateTime.class;
            }
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: {
                return Interval.class;
            }
            case 35: {
                return bl ? Object.class : byte[].class;
            }
            case 37: {
                Class<?> clazz = GEOMETRY_CLASS;
                return clazz != null ? clazz : String.class;
            }
            case 39: {
                return UUID.class;
            }
            case 40: {
                if (bl) {
                    return java.sql.Array.class;
                }
                return Object[].class;
            }
            case 41: {
                if (bl) {
                    return ResultSet.class;
                }
                return Object[].class;
            }
        }
        throw DbException.getUnsupportedException("data type " + n);
    }

    public static Object valueToDefaultObject(Value value, JdbcConnection jdbcConnection, boolean bl) {
        switch (value.getValueType()) {
            case 0: {
                return null;
            }
            case 1: 
            case 2: 
            case 4: 
            case 36: {
                return value.getString();
            }
            case 3: {
                return new JdbcClob(jdbcConnection, value, JdbcLob.State.WITH_VALUE, ValueToObjectConverter.getNextId(10));
            }
            case 5: 
            case 6: 
            case 38: {
                return value.getBytes();
            }
            case 7: {
                return new JdbcBlob(jdbcConnection, value, JdbcLob.State.WITH_VALUE, ValueToObjectConverter.getNextId(9));
            }
            case 8: {
                return value.getBoolean();
            }
            case 9: {
                if (bl) {
                    return value.getInt();
                }
                return value.getByte();
            }
            case 10: {
                if (bl) {
                    return value.getInt();
                }
                return value.getShort();
            }
            case 11: {
                return value.getInt();
            }
            case 12: {
                return value.getLong();
            }
            case 13: 
            case 16: {
                return value.getBigDecimal();
            }
            case 14: {
                return Float.valueOf(value.getFloat());
            }
            case 15: {
                return value.getDouble();
            }
            case 17: {
                return bl ? LegacyDateTimeUtils.toDate(jdbcConnection, null, value) : JSR310Utils.valueToLocalDate(value, null);
            }
            case 18: {
                return bl ? LegacyDateTimeUtils.toTime(jdbcConnection, null, value) : JSR310Utils.valueToLocalTime(value, null);
            }
            case 19: {
                return JSR310Utils.valueToOffsetTime(value, null);
            }
            case 20: {
                return bl ? LegacyDateTimeUtils.toTimestamp(jdbcConnection, null, value) : JSR310Utils.valueToLocalDateTime(value, null);
            }
            case 21: {
                return JSR310Utils.valueToOffsetDateTime(value, null);
            }
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: {
                return ((ValueInterval)value).getInterval();
            }
            case 35: {
                return bl ? (Object)JdbcUtils.deserialize(value.getBytesNoCopy(), jdbcConnection.getJavaObjectSerializer()) : value.getBytes();
            }
            case 37: {
                return GEOMETRY_CLASS != null ? ((ValueGeometry)value).getGeometry() : value.getString();
            }
            case 39: {
                return ((ValueUuid)value).getUuid();
            }
            case 40: {
                if (bl) {
                    return new JdbcArray(jdbcConnection, value, ValueToObjectConverter.getNextId(16));
                }
                return ValueToObjectConverter.valueToDefaultArray(value, jdbcConnection, bl);
            }
            case 41: {
                if (bl) {
                    return new JdbcResultSet(jdbcConnection, null, null, (ResultInterface)((ValueRow)value).getResult(), ValueToObjectConverter.getNextId(4), true, false, false);
                }
                return ValueToObjectConverter.valueToDefaultArray(value, jdbcConnection, bl);
            }
        }
        throw DbException.getUnsupportedException("data type " + value.getValueType());
    }

    public static Object valueToDefaultArray(Value value, JdbcConnection jdbcConnection, boolean bl) {
        Value[] valueArray = ((ValueCollectionBase)value).getList();
        int n = valueArray.length;
        Object[] objectArray = new Object[n];
        for (int i = 0; i < n; ++i) {
            objectArray[i] = ValueToObjectConverter.valueToDefaultObject(valueArray[i], jdbcConnection, bl);
        }
        return objectArray;
    }

    public static Value readValue(Session session, JdbcResultSet jdbcResultSet, int n) {
        Value value = jdbcResultSet.getInternal(n);
        switch (value.getValueType()) {
            case 3: {
                value = session.addTemporaryLob(session.getDataHandler().getLobStorage().createClob(new BufferedReader(value.getReader()), -1L));
                break;
            }
            case 7: {
                value = session.addTemporaryLob(session.getDataHandler().getLobStorage().createBlob(value.getInputStream(), -1L));
            }
        }
        return value;
    }

    private ValueToObjectConverter() {
    }

    static {
        Class clazz;
        try {
            clazz = JdbcUtils.loadUserClass(GEOMETRY_CLASS_NAME);
        }
        catch (Exception exception) {
            clazz = null;
        }
        GEOMETRY_CLASS = clazz;
    }
}

