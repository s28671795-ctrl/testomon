/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor.jvm;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import liquibase.executor.jvm.RowMapper;
import liquibase.util.JdbcUtil;
import liquibase.util.NumberUtil;
import liquibase.util.ObjectUtil;

class SingleColumnRowMapper
implements RowMapper {
    private Class requiredType;

    public SingleColumnRowMapper() {
    }

    public SingleColumnRowMapper(Class requiredType) {
        this.requiredType = requiredType;
    }

    public void setRequiredType(Class requiredType) {
        this.requiredType = requiredType;
    }

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int nrOfColumns = rsmd.getColumnCount();
        if (nrOfColumns == 0) {
            throw new SQLException("Returned no columns!");
        }
        if (nrOfColumns != 1) {
            throw new SQLException("Returned too many columns: " + nrOfColumns);
        }
        Object result = this.getColumnValue(rs, 1, this.requiredType);
        if (result != null && this.requiredType != null && !this.requiredType.isInstance(result)) {
            try {
                return this.convertValueToRequiredType(result, this.requiredType);
            }
            catch (IllegalArgumentException ex) {
                throw new SQLException("Type mismatch affecting row number " + rowNum + " and column type '" + rsmd.getColumnTypeName(1) + "': " + ex.getMessage());
            }
        }
        return result;
    }

    protected Object getColumnValue(ResultSet rs, int index, Class requiredType) throws SQLException {
        if (requiredType != null) {
            Object value;
            boolean wasNullCheck = false;
            if (String.class.equals((Object)requiredType)) {
                value = rs.getString(index);
            } else if (Boolean.class.equals((Object)requiredType)) {
                value = rs.getBoolean(index) ? Boolean.TRUE : Boolean.FALSE;
                wasNullCheck = true;
            } else if (Byte.class.equals((Object)requiredType)) {
                value = rs.getByte(index);
                wasNullCheck = true;
            } else if (Short.class.equals((Object)requiredType)) {
                value = rs.getShort(index);
                wasNullCheck = true;
            } else if (Integer.class.equals((Object)requiredType)) {
                value = rs.getInt(index);
                wasNullCheck = true;
            } else if (Long.class.equals((Object)requiredType)) {
                value = rs.getLong(index);
                wasNullCheck = true;
            } else if (Float.class.equals((Object)requiredType)) {
                value = Float.valueOf(rs.getFloat(index));
                wasNullCheck = true;
            } else if (Double.class.equals((Object)requiredType) || Number.class.equals((Object)requiredType)) {
                value = rs.getDouble(index);
                wasNullCheck = true;
            } else {
                value = byte[].class.equals((Object)requiredType) ? (Object)rs.getBytes(index) : (Date.class.equals((Object)requiredType) ? rs.getDate(index) : (Time.class.equals((Object)requiredType) ? rs.getTime(index) : (Timestamp.class.equals((Object)requiredType) || java.util.Date.class.equals((Object)requiredType) ? rs.getTimestamp(index) : (BigDecimal.class.equals((Object)requiredType) ? rs.getBigDecimal(index) : (Blob.class.equals((Object)requiredType) ? rs.getBlob(index) : (Clob.class.equals((Object)requiredType) ? rs.getClob(index) : rs.getObject(index)))))));
            }
            if (wasNullCheck && value != null && rs.wasNull()) {
                value = null;
            }
            return value;
        }
        return this.getColumnValue(rs, index);
    }

    protected Object getColumnValue(ResultSet rs, int index) throws SQLException {
        return JdbcUtil.getResultSetValue(rs, index);
    }

    protected Object convertValueToRequiredType(Object value, Class requiredType) {
        if (String.class.equals((Object)this.requiredType)) {
            return value.toString();
        }
        if (Number.class.isAssignableFrom(this.requiredType)) {
            if (value instanceof Number) {
                return ObjectUtil.convert(value, this.requiredType);
            }
            return NumberUtil.parseNumber(value.toString(), this.requiredType);
        }
        throw new IllegalArgumentException("Value [" + value + "] is of type [" + value.getClass().getName() + "] and cannot be converted to required type [" + this.requiredType.getName() + "]");
    }
}

