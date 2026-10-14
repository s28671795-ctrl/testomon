/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import com.opencsv.ResultSetHelper;
import com.opencsv.ResultSetHelperService;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public class ResultSetColumnNameHelperService
extends ResultSetHelperService
implements ResultSetHelper {
    private String[] columnNames;
    private String[] columnHeaders;
    private final Map<String, Integer> columnNamePositionMap = new HashMap<String, Integer>();
    private Locale errorLocale = Locale.getDefault();

    public void setErrorLocale(Locale errorLocale) {
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
    }

    public void setColumnNames(String[] columnNames, String[] columnHeaders) {
        if (columnHeaders.length != columnNames.length) {
            throw new UnsupportedOperationException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("column.count.mismatch"));
        }
        if (this.hasInvalidValue(columnNames)) {
            throw new UnsupportedOperationException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("column.name.bogus"));
        }
        if (this.hasInvalidValue(columnHeaders)) {
            throw new UnsupportedOperationException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("header.name.bogus"));
        }
        this.columnNames = Arrays.copyOf(columnNames, columnNames.length);
        this.columnHeaders = Arrays.copyOf(columnHeaders, columnHeaders.length);
    }

    private boolean hasInvalidValue(String[] strings) {
        return Stream.of(strings).anyMatch(s2 -> StringUtils.isBlank(s2));
    }

    @Override
    public String[] getColumnNames(ResultSet rs) throws SQLException {
        if (this.columnNamePositionMap.isEmpty()) {
            this.populateColumnData(rs);
        }
        return Arrays.copyOf(this.columnHeaders, this.columnHeaders.length);
    }

    private void populateColumnData(ResultSet rs) throws SQLException {
        Object[] realColumnNames = super.getColumnNames(rs);
        if (this.columnNames == null) {
            this.columnNames = Arrays.copyOf(realColumnNames, realColumnNames.length);
            this.columnHeaders = Arrays.copyOf(realColumnNames, realColumnNames.length);
        }
        for (String name : this.columnNames) {
            int position = ArrayUtils.indexOf(realColumnNames, name);
            if (position == -1) {
                throw new UnsupportedOperationException(String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("column.nonexistant"), name));
            }
            this.columnNamePositionMap.put(name, position);
        }
    }

    @Override
    public String[] getColumnValues(ResultSet rs) throws SQLException, IOException {
        if (this.columnNamePositionMap.isEmpty()) {
            this.populateColumnData(rs);
        }
        String[] realColumnValues = super.getColumnValues(rs, false, this.dateFormat, this.dateTimeFormat);
        return this.getColumnValueSubset(realColumnValues);
    }

    @Override
    public String[] getColumnValues(ResultSet rs, boolean trim) throws SQLException, IOException {
        if (this.columnNamePositionMap.isEmpty()) {
            this.populateColumnData(rs);
        }
        String[] realColumnValues = super.getColumnValues(rs, trim, this.dateFormat, this.dateTimeFormat);
        return this.getColumnValueSubset(realColumnValues);
    }

    @Override
    public String[] getColumnValues(ResultSet rs, boolean trim, String dateFormatString, String timeFormatString) throws SQLException, IOException {
        if (this.columnNamePositionMap.isEmpty()) {
            this.populateColumnData(rs);
        }
        String[] realColumnValues = super.getColumnValues(rs, trim, dateFormatString, timeFormatString);
        return this.getColumnValueSubset(realColumnValues);
    }

    private String[] getColumnValueSubset(String[] realColumnValues) {
        return Stream.of(this.columnNames).map(c -> realColumnValues[this.columnNamePositionMap.get(c)]).collect(Collectors.toList()).toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }
}

