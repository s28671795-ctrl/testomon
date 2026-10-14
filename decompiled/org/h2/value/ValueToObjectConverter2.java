/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import java.util.UUID;
import org.h2.api.IntervalQualifier;
import org.h2.engine.CastDataProvider;
import org.h2.engine.Session;
import org.h2.jdbc.JdbcResultSet;
import org.h2.message.DbException;
import org.h2.message.TraceObject;
import org.h2.util.IntervalUtils;
import org.h2.util.JSR310Utils;
import org.h2.util.JdbcUtils;
import org.h2.util.LegacyDateTimeUtils;
import org.h2.util.Utils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueArray;
import org.h2.value.ValueBigint;
import org.h2.value.ValueBinary;
import org.h2.value.ValueBlob;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueChar;
import org.h2.value.ValueClob;
import org.h2.value.ValueDecfloat;
import org.h2.value.ValueDouble;
import org.h2.value.ValueGeometry;
import org.h2.value.ValueInteger;
import org.h2.value.ValueJavaObject;
import org.h2.value.ValueJson;
import org.h2.value.ValueNull;
import org.h2.value.ValueNumeric;
import org.h2.value.ValueReal;
import org.h2.value.ValueRow;
import org.h2.value.ValueSmallint;
import org.h2.value.ValueTimeTimeZone;
import org.h2.value.ValueTimestampTimeZone;
import org.h2.value.ValueTinyint;
import org.h2.value.ValueToObjectConverter;
import org.h2.value.ValueUuid;
import org.h2.value.ValueVarbinary;
import org.h2.value.ValueVarchar;
import org.h2.value.ValueVarcharIgnoreCase;

public final class ValueToObjectConverter2
extends TraceObject {
    public static TypeInfo classToType(Class<?> clazz) {
        if (clazz == null) {
            return TypeInfo.TYPE_NULL;
        }
        if (clazz.isPrimitive()) {
            clazz = Utils.getNonPrimitiveClass(clazz);
        }
        if (clazz == Void.class) {
            return TypeInfo.TYPE_NULL;
        }
        if (clazz == String.class || clazz == Character.class) {
            return TypeInfo.TYPE_VARCHAR;
        }
        if (clazz == byte[].class) {
            return TypeInfo.TYPE_VARBINARY;
        }
        if (clazz == Boolean.class) {
            return TypeInfo.TYPE_BOOLEAN;
        }
        if (clazz == Byte.class) {
            return TypeInfo.TYPE_TINYINT;
        }
        if (clazz == Short.class) {
            return TypeInfo.TYPE_SMALLINT;
        }
        if (clazz == Integer.class) {
            return TypeInfo.TYPE_INTEGER;
        }
        if (clazz == Long.class) {
            return TypeInfo.TYPE_BIGINT;
        }
        if (clazz == Float.class) {
            return TypeInfo.TYPE_REAL;
        }
        if (clazz == Double.class) {
            return TypeInfo.TYPE_DOUBLE;
        }
        if (clazz == LocalDate.class) {
            return TypeInfo.TYPE_DATE;
        }
        if (clazz == LocalTime.class) {
            return TypeInfo.TYPE_TIME;
        }
        if (clazz == OffsetTime.class) {
            return TypeInfo.TYPE_TIME_TZ;
        }
        if (clazz == LocalDateTime.class) {
            return TypeInfo.TYPE_TIMESTAMP;
        }
        if (clazz == OffsetDateTime.class || clazz == ZonedDateTime.class || clazz == Instant.class) {
            return TypeInfo.TYPE_TIMESTAMP_TZ;
        }
        if (clazz == Period.class) {
            return TypeInfo.TYPE_INTERVAL_YEAR_TO_MONTH;
        }
        if (clazz == Duration.class) {
            return TypeInfo.TYPE_INTERVAL_DAY_TO_SECOND;
        }
        if (UUID.class == clazz) {
            return TypeInfo.TYPE_UUID;
        }
        if (clazz.isArray()) {
            return TypeInfo.getTypeInfo(40, Integer.MAX_VALUE, 0, ValueToObjectConverter2.classToType(clazz.getComponentType()));
        }
        if (Clob.class.isAssignableFrom(clazz) || Reader.class.isAssignableFrom(clazz)) {
            return TypeInfo.TYPE_CLOB;
        }
        if (Blob.class.isAssignableFrom(clazz) || InputStream.class.isAssignableFrom(clazz)) {
            return TypeInfo.TYPE_BLOB;
        }
        if (BigDecimal.class.isAssignableFrom(clazz)) {
            return TypeInfo.TYPE_NUMERIC_FLOATING_POINT;
        }
        if (ValueToObjectConverter.GEOMETRY_CLASS != null && ValueToObjectConverter.GEOMETRY_CLASS.isAssignableFrom(clazz)) {
            return TypeInfo.TYPE_GEOMETRY;
        }
        if (Array.class.isAssignableFrom(clazz)) {
            return TypeInfo.TYPE_ARRAY_UNKNOWN;
        }
        if (ResultSet.class.isAssignableFrom(clazz)) {
            return TypeInfo.TYPE_ROW_EMPTY;
        }
        TypeInfo typeInfo = LegacyDateTimeUtils.legacyClassToType(clazz);
        if (typeInfo != null) {
            return typeInfo;
        }
        return TypeInfo.TYPE_JAVA_OBJECT;
    }

    public static Value readValue(Session session, ResultSet resultSet, int n, int n2) {
        Value value;
        if (resultSet instanceof JdbcResultSet) {
            value = ValueToObjectConverter.readValue(session, (JdbcResultSet)resultSet, n);
        } else {
            try {
                value = ValueToObjectConverter2.readValueOther(session, resultSet, n, n2);
            }
            catch (SQLException sQLException) {
                throw DbException.convert(sQLException);
            }
        }
        return value;
    }

    private static Value readValueOther(Session session, ResultSet resultSet, int n, int n2) throws SQLException {
        Value value;
        switch (n2) {
            case 0: {
                value = ValueNull.INSTANCE;
                break;
            }
            case 1: {
                String string = resultSet.getString(n);
                value = string == null ? ValueNull.INSTANCE : ValueChar.get(string);
                break;
            }
            case 2: {
                String string = resultSet.getString(n);
                value = string == null ? ValueNull.INSTANCE : ValueVarchar.get(string, session);
                break;
            }
            case 3: {
                if (session == null) {
                    String string = resultSet.getString(n);
                    value = string == null ? ValueNull.INSTANCE : ValueClob.createSmall(string);
                    break;
                }
                Reader reader = resultSet.getCharacterStream(n);
                value = reader == null ? ValueNull.INSTANCE : session.addTemporaryLob(session.getDataHandler().getLobStorage().createClob(new BufferedReader(reader), -1L));
                break;
            }
            case 4: {
                String string = resultSet.getString(n);
                value = string == null ? ValueNull.INSTANCE : ValueVarcharIgnoreCase.get(string);
                break;
            }
            case 5: {
                byte[] byArray = resultSet.getBytes(n);
                value = byArray == null ? ValueNull.INSTANCE : ValueBinary.getNoCopy(byArray);
                break;
            }
            case 6: {
                byte[] byArray = resultSet.getBytes(n);
                value = byArray == null ? ValueNull.INSTANCE : ValueVarbinary.getNoCopy(byArray);
                break;
            }
            case 7: {
                if (session == null) {
                    byte[] byArray = resultSet.getBytes(n);
                    value = byArray == null ? ValueNull.INSTANCE : ValueBlob.createSmall(byArray);
                    break;
                }
                InputStream inputStream2 = resultSet.getBinaryStream(n);
                value = inputStream2 == null ? ValueNull.INSTANCE : session.addTemporaryLob(session.getDataHandler().getLobStorage().createBlob(inputStream2, -1L));
                break;
            }
            case 8: {
                boolean bl = resultSet.getBoolean(n);
                value = resultSet.wasNull() ? ValueNull.INSTANCE : ValueBoolean.get(bl);
                break;
            }
            case 9: {
                byte by = resultSet.getByte(n);
                value = resultSet.wasNull() ? ValueNull.INSTANCE : ValueTinyint.get(by);
                break;
            }
            case 10: {
                short s2 = resultSet.getShort(n);
                value = resultSet.wasNull() ? ValueNull.INSTANCE : ValueSmallint.get(s2);
                break;
            }
            case 11: {
                int n3 = resultSet.getInt(n);
                value = resultSet.wasNull() ? ValueNull.INSTANCE : ValueInteger.get(n3);
                break;
            }
            case 12: {
                long l = resultSet.getLong(n);
                value = resultSet.wasNull() ? ValueNull.INSTANCE : ValueBigint.get(l);
                break;
            }
            case 13: {
                BigDecimal bigDecimal = resultSet.getBigDecimal(n);
                value = bigDecimal == null ? ValueNull.INSTANCE : ValueNumeric.getAnyScale(bigDecimal);
                break;
            }
            case 14: {
                float f = resultSet.getFloat(n);
                value = resultSet.wasNull() ? ValueNull.INSTANCE : ValueReal.get(f);
                break;
            }
            case 15: {
                double d = resultSet.getDouble(n);
                value = resultSet.wasNull() ? ValueNull.INSTANCE : ValueDouble.get(d);
                break;
            }
            case 16: {
                BigDecimal bigDecimal = resultSet.getBigDecimal(n);
                value = bigDecimal == null ? ValueNull.INSTANCE : ValueDecfloat.get(bigDecimal);
                break;
            }
            case 17: {
                try {
                    LocalDate localDate = resultSet.getObject(n, LocalDate.class);
                    value = localDate == null ? ValueNull.INSTANCE : JSR310Utils.localDateToValue(localDate);
                }
                catch (SQLException sQLException) {
                    Date date = resultSet.getDate(n);
                    value = date == null ? ValueNull.INSTANCE : LegacyDateTimeUtils.fromDate(session, null, date);
                }
                break;
            }
            case 18: {
                try {
                    LocalTime localTime = resultSet.getObject(n, LocalTime.class);
                    value = localTime == null ? ValueNull.INSTANCE : JSR310Utils.localTimeToValue(localTime);
                }
                catch (SQLException sQLException) {
                    Time time = resultSet.getTime(n);
                    value = time == null ? ValueNull.INSTANCE : LegacyDateTimeUtils.fromTime(session, null, time);
                }
                break;
            }
            case 19: {
                try {
                    OffsetTime offsetTime = resultSet.getObject(n, OffsetTime.class);
                    value = offsetTime == null ? ValueNull.INSTANCE : JSR310Utils.offsetTimeToValue(offsetTime);
                }
                catch (SQLException sQLException) {
                    Object object = resultSet.getObject(n);
                    if (object == null) {
                        value = ValueNull.INSTANCE;
                        break;
                    }
                    value = ValueTimeTimeZone.parse(object.toString(), session);
                }
                break;
            }
            case 20: {
                try {
                    LocalDateTime localDateTime = resultSet.getObject(n, LocalDateTime.class);
                    value = localDateTime == null ? ValueNull.INSTANCE : JSR310Utils.localDateTimeToValue(localDateTime);
                }
                catch (SQLException sQLException) {
                    Timestamp timestamp = resultSet.getTimestamp(n);
                    value = timestamp == null ? ValueNull.INSTANCE : LegacyDateTimeUtils.fromTimestamp((CastDataProvider)session, null, timestamp);
                }
                break;
            }
            case 21: {
                try {
                    OffsetDateTime offsetDateTime = resultSet.getObject(n, OffsetDateTime.class);
                    value = offsetDateTime == null ? ValueNull.INSTANCE : JSR310Utils.offsetDateTimeToValue(offsetDateTime);
                }
                catch (SQLException sQLException) {
                    Object object = resultSet.getObject(n);
                    if (object == null) {
                        value = ValueNull.INSTANCE;
                        break;
                    }
                    if (object instanceof ZonedDateTime) {
                        value = JSR310Utils.zonedDateTimeToValue((ZonedDateTime)object);
                        break;
                    }
                    value = ValueTimestampTimeZone.parse(object.toString(), session);
                }
                break;
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
                String string = resultSet.getString(n);
                value = string == null ? ValueNull.INSTANCE : IntervalUtils.parseFormattedInterval(IntervalQualifier.valueOf(n2 - 22), string);
                break;
            }
            case 35: {
                byte[] byArray;
                try {
                    byArray = resultSet.getBytes(n);
                }
                catch (SQLException sQLException) {
                    try {
                        Object object = resultSet.getObject(n);
                        byArray = object != null ? JdbcUtils.serialize(object, session.getJavaObjectSerializer()) : null;
                    }
                    catch (Exception exception) {
                        throw DbException.convert(exception);
                    }
                }
                value = byArray == null ? ValueNull.INSTANCE : ValueJavaObject.getNoCopy(byArray);
                break;
            }
            case 36: {
                int n4 = resultSet.getInt(n);
                value = resultSet.wasNull() ? ValueNull.INSTANCE : ValueInteger.get(n4);
                break;
            }
            case 37: {
                Object object = resultSet.getObject(n);
                value = object == null ? ValueNull.INSTANCE : ValueGeometry.getFromGeometry(object);
                break;
            }
            case 38: {
                Object object = resultSet.getObject(n);
                if (object == null) {
                    value = ValueNull.INSTANCE;
                    break;
                }
                Class<?> clazz = object.getClass();
                if (clazz == byte[].class) {
                    value = ValueJson.fromJson((byte[])object);
                    break;
                }
                if (clazz == String.class) {
                    value = ValueJson.fromJson((String)object);
                    break;
                }
                value = ValueJson.fromJson(object.toString());
                break;
            }
            case 39: {
                Object object = resultSet.getObject(n);
                if (object == null) {
                    value = ValueNull.INSTANCE;
                    break;
                }
                if (object instanceof UUID) {
                    value = ValueUuid.get((UUID)object);
                    break;
                }
                if (object instanceof byte[]) {
                    value = ValueUuid.get((byte[])object);
                    break;
                }
                value = ValueUuid.get((String)object);
                break;
            }
            case 40: {
                Array array = resultSet.getArray(n);
                if (array == null) {
                    value = ValueNull.INSTANCE;
                    break;
                }
                Object[] objectArray = (Object[])array.getArray();
                if (objectArray == null) {
                    value = ValueNull.INSTANCE;
                    break;
                }
                int n5 = objectArray.length;
                Value[] valueArray = new Value[n5];
                for (int i = 0; i < n5; ++i) {
                    valueArray[i] = ValueToObjectConverter.objectToValue(session, objectArray[i], 0);
                }
                value = ValueArray.get(valueArray, session);
                break;
            }
            case 41: {
                Object object = resultSet.getObject(n);
                if (object == null) {
                    value = ValueNull.INSTANCE;
                    break;
                }
                if (object instanceof ResultSet) {
                    value = ValueToObjectConverter.resultSetToValue(session, (ResultSet)object);
                    break;
                }
                Object[] objectArray = (Object[])object;
                int n6 = objectArray.length;
                Value[] valueArray = new Value[n6];
                for (int i = 0; i < n6; ++i) {
                    valueArray[i] = ValueToObjectConverter.objectToValue(session, objectArray[i], 0);
                }
                value = ValueRow.get(valueArray);
                break;
            }
            default: {
                throw DbException.getInternalError("data type " + n2);
            }
        }
        return value;
    }

    private ValueToObjectConverter2() {
    }
}

