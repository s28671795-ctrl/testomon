/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import com.opencsv.ResultSetHelper;
import java.io.IOException;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;
import org.apache.commons.text.TextStringBuilder;

public class ResultSetHelperService
implements ResultSetHelper {
    protected static final int CLOBBUFFERSIZE = 2048;
    static final String DEFAULT_DATE_FORMAT = "dd-MMM-yyyy";
    static final String DEFAULT_TIMESTAMP_FORMAT = "dd-MMM-yyyy HH:mm:ss";
    private static final String DEFAULT_VALUE = "";
    protected String dateFormat = "dd-MMM-yyyy";
    protected String dateTimeFormat = "dd-MMM-yyyy HH:mm:ss";
    protected NumberFormat integerFormat;
    protected NumberFormat floatingPointFormat;

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public void setIntegerFormat(NumberFormat format) {
        this.integerFormat = format;
    }

    public void setFloatingPointFormat(NumberFormat format) {
        this.floatingPointFormat = format;
    }

    @Override
    public String[] getColumnNames(ResultSet rs) throws SQLException {
        ResultSetMetaData metadata = rs.getMetaData();
        String[] nameArray = new String[metadata.getColumnCount()];
        for (int i = 0; i < metadata.getColumnCount(); ++i) {
            nameArray[i] = metadata.getColumnLabel(i + 1);
        }
        return nameArray;
    }

    @Override
    public String[] getColumnValues(ResultSet rs) throws SQLException, IOException {
        return this.getColumnValues(rs, false, this.dateFormat, this.dateTimeFormat);
    }

    @Override
    public String[] getColumnValues(ResultSet rs, boolean trim) throws SQLException, IOException {
        return this.getColumnValues(rs, trim, this.dateFormat, this.dateTimeFormat);
    }

    @Override
    public String[] getColumnValues(ResultSet rs, boolean trim, String dateFormatString, String timeFormatString) throws SQLException, IOException {
        ResultSetMetaData metadata = rs.getMetaData();
        String[] valueArray = new String[metadata.getColumnCount()];
        for (int i = 1; i <= metadata.getColumnCount(); ++i) {
            valueArray[i - 1] = this.getColumnValue(rs, metadata.getColumnType(i), i, trim, dateFormatString, timeFormatString);
        }
        return valueArray;
    }

    protected String handleTimestamp(Timestamp timestamp, String timestampFormatString) {
        SimpleDateFormat timeFormat = new SimpleDateFormat(timestampFormatString);
        return timestamp == null ? null : timeFormat.format(timestamp);
    }

    private String getColumnValue(ResultSet rs, int colType, int colIndex, boolean trim, String dateFormatString, String timestampFormatString) throws SQLException, IOException {
        String value;
        switch (colType) {
            case 16: {
                value = Objects.toString(rs.getBoolean(colIndex));
                break;
            }
            case 2011: {
                value = this.handleNClob(rs, colIndex);
                break;
            }
            case 2005: {
                value = this.handleClob(rs, colIndex);
                break;
            }
            case -5: {
                value = this.applyFormatter(this.integerFormat, rs.getBigDecimal(colIndex));
                break;
            }
            case 2: 
            case 3: 
            case 7: {
                value = this.applyFormatter(this.floatingPointFormat, rs.getBigDecimal(colIndex));
                break;
            }
            case 8: {
                value = this.applyFormatter(this.floatingPointFormat, rs.getDouble(colIndex));
                break;
            }
            case 6: {
                value = this.applyFormatter(this.floatingPointFormat, Float.valueOf(rs.getFloat(colIndex)));
                break;
            }
            case -6: 
            case 4: 
            case 5: {
                value = this.applyFormatter(this.integerFormat, rs.getInt(colIndex));
                break;
            }
            case 91: {
                value = this.handleDate(rs, colIndex, dateFormatString);
                break;
            }
            case 92: {
                value = Objects.toString(rs.getTime(colIndex), DEFAULT_VALUE);
                break;
            }
            case 93: {
                value = this.handleTimestamp(rs.getTimestamp(colIndex), timestampFormatString);
                break;
            }
            case -16: 
            case -15: 
            case -9: {
                value = this.handleNVarChar(rs, colIndex, trim);
                break;
            }
            case -1: 
            case 1: 
            case 12: {
                value = this.handleVarChar(rs, colIndex, trim);
                break;
            }
            default: {
                value = Objects.toString(rs.getObject(colIndex), DEFAULT_VALUE);
            }
        }
        if (rs.wasNull() || value == null) {
            value = DEFAULT_VALUE;
        }
        return value;
    }

    private String applyFormatter(NumberFormat formatter, Number value) {
        if (formatter != null && value != null) {
            return formatter.format(value);
        }
        return Objects.toString(value, DEFAULT_VALUE);
    }

    protected String handleVarChar(ResultSet rs, int colIndex, boolean trim) throws SQLException {
        String columnValue = rs.getString(colIndex);
        String value = trim && columnValue != null ? columnValue.trim() : columnValue;
        return value;
    }

    protected String handleNVarChar(ResultSet rs, int colIndex, boolean trim) throws SQLException {
        String nColumnValue = rs.getNString(colIndex);
        String value = trim && nColumnValue != null ? nColumnValue.trim() : nColumnValue;
        return value;
    }

    protected String handleDate(ResultSet rs, int colIndex, String dateFormatString) throws SQLException {
        String value = DEFAULT_VALUE;
        Date date = rs.getDate(colIndex);
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(dateFormatString);
            value = df.format(date);
        }
        return value;
    }

    protected String handleClob(ResultSet rs, int colIndex) throws SQLException, IOException {
        String value = DEFAULT_VALUE;
        Clob c = rs.getClob(colIndex);
        if (c != null) {
            TextStringBuilder sb = new TextStringBuilder();
            sb.readFrom(c.getCharacterStream());
            value = sb.toString();
        }
        return value;
    }

    protected String handleNClob(ResultSet rs, int colIndex) throws SQLException, IOException {
        String value = DEFAULT_VALUE;
        NClob nc = rs.getNClob(colIndex);
        if (nc != null) {
            TextStringBuilder sb = new TextStringBuilder();
            sb.readFrom(nc.getCharacterStream());
            value = sb.toString();
        }
        return value;
    }
}

