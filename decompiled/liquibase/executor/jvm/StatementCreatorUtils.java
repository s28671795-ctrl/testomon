/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor.jvm;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import liquibase.executor.jvm.SqlParameter;
import liquibase.executor.jvm.SqlTypeValue;

abstract class StatementCreatorUtils {
    StatementCreatorUtils() {
    }

    public static void setParameterValue(PreparedStatement ps, int paramIndex, SqlParameter param2, Object inValue) throws SQLException {
        StatementCreatorUtils.setParameterValueInternal(ps, paramIndex, param2.getSqlType(), param2.getTypeName(), param2.getScale(), inValue);
    }

    public static void setParameterValue(PreparedStatement ps, int paramIndex, int sqlType, Object inValue) throws SQLException {
        StatementCreatorUtils.setParameterValueInternal(ps, paramIndex, sqlType, null, null, inValue);
    }

    private static void setParameterValueInternal(PreparedStatement ps, int paramIndex, int sqlType, String typeName, Integer scale, Object inValue) throws SQLException {
        if (inValue == null) {
            if (sqlType == Integer.MIN_VALUE) {
                boolean useSetObject = false;
                try {
                    useSetObject = ps.getConnection().getMetaData().getDatabaseProductName().contains("Informix");
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
                if (useSetObject) {
                    ps.setObject(paramIndex, null);
                } else {
                    ps.setNull(paramIndex, 0);
                }
            } else if (typeName != null) {
                ps.setNull(paramIndex, sqlType, typeName);
            } else {
                ps.setNull(paramIndex, sqlType);
            }
        } else if (inValue instanceof SqlTypeValue) {
            ((SqlTypeValue)inValue).setTypeValue(ps, paramIndex, sqlType, typeName);
        } else if (sqlType == 12 || sqlType == -9) {
            ps.setString(paramIndex, inValue.toString());
        } else if (sqlType == 3 || sqlType == 2) {
            if (inValue instanceof BigDecimal) {
                ps.setBigDecimal(paramIndex, (BigDecimal)inValue);
            } else if (scale != null) {
                ps.setObject(paramIndex, inValue, sqlType, (int)scale);
            } else {
                ps.setObject(paramIndex, inValue, sqlType);
            }
        } else if (sqlType == 91) {
            if (inValue instanceof Date) {
                if (inValue instanceof java.sql.Date) {
                    ps.setDate(paramIndex, (java.sql.Date)inValue);
                } else {
                    ps.setDate(paramIndex, new java.sql.Date(((Date)inValue).getTime()));
                }
            } else if (inValue instanceof Calendar) {
                Calendar cal = (Calendar)inValue;
                ps.setDate(paramIndex, new java.sql.Date(cal.getTime().getTime()), cal);
            } else {
                ps.setObject(paramIndex, inValue, 91);
            }
        } else if (sqlType == 92) {
            if (inValue instanceof Date) {
                if (inValue instanceof Time) {
                    ps.setTime(paramIndex, (Time)inValue);
                } else {
                    ps.setTime(paramIndex, new Time(((Date)inValue).getTime()));
                }
            } else if (inValue instanceof Calendar) {
                Calendar cal = (Calendar)inValue;
                ps.setTime(paramIndex, new Time(cal.getTime().getTime()), cal);
            } else {
                ps.setObject(paramIndex, inValue, 92);
            }
        } else if (sqlType == 93) {
            if (inValue instanceof Date) {
                if (inValue instanceof Timestamp) {
                    ps.setTimestamp(paramIndex, (Timestamp)inValue);
                } else {
                    ps.setTimestamp(paramIndex, new Timestamp(((Date)inValue).getTime()));
                }
            } else if (inValue instanceof Calendar) {
                Calendar cal = (Calendar)inValue;
                ps.setTimestamp(paramIndex, new Timestamp(cal.getTime().getTime()), cal);
            } else {
                ps.setObject(paramIndex, inValue, 93);
            }
        } else if (sqlType == Integer.MIN_VALUE) {
            if (StatementCreatorUtils.isStringValue(inValue)) {
                ps.setString(paramIndex, inValue.toString());
            } else if (StatementCreatorUtils.isDateValue(inValue)) {
                ps.setTimestamp(paramIndex, new Timestamp(((Date)inValue).getTime()));
            } else if (inValue instanceof Calendar) {
                Calendar cal = (Calendar)inValue;
                ps.setTimestamp(paramIndex, new Timestamp(cal.getTime().getTime()));
            } else {
                ps.setObject(paramIndex, inValue);
            }
        } else {
            ps.setObject(paramIndex, inValue, sqlType);
        }
    }

    private static boolean isStringValue(Object inValue) {
        return inValue instanceof CharSequence || inValue instanceof StringWriter;
    }

    private static boolean isDateValue(Object inValue) {
        return inValue instanceof Date && !(inValue instanceof java.sql.Date) && !(inValue instanceof Time) && !(inValue instanceof Timestamp);
    }
}

