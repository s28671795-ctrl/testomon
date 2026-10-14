/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot;

import java.util.Map;

public class CachedRow {
    private final Map row;

    public CachedRow(Map row) {
        this.row = row;
    }

    public Object get(String columnName) {
        return this.row.get(columnName);
    }

    public void set(String columnName, Object value) {
        this.row.put(columnName, value);
    }

    public boolean containsColumn(String columnName) {
        return this.row.containsKey(columnName);
    }

    public String getString(String columnName) {
        return (String)this.row.get(columnName);
    }

    public Integer getInt(String columnName) {
        Object o = this.row.get(columnName);
        if (o instanceof Number) {
            return ((Number)o).intValue();
        }
        if (o instanceof String) {
            return Integer.valueOf((String)o);
        }
        if (o instanceof byte[]) {
            return Integer.valueOf(new String((byte[])o));
        }
        return (Integer)o;
    }

    public Short getShort(String columnName) {
        Object o = this.row.get(columnName);
        if (o instanceof Number) {
            return ((Number)o).shortValue();
        }
        if (o instanceof String) {
            return Short.valueOf((String)o);
        }
        return (Short)o;
    }

    public Boolean getBoolean(String columnName) {
        Object o = this.row.get(columnName);
        if (o instanceof Number) {
            return ((Number)o).longValue() != 0L;
        }
        if (o instanceof String) {
            String s2 = (String)o;
            if ("T".equalsIgnoreCase(s2)) {
                s2 = "TRUE";
            }
            return Boolean.valueOf(s2);
        }
        return (Boolean)o;
    }

    public Boolean yesNoToBoolean(String columnName) {
        Object o = this.row.get(columnName);
        if (o instanceof String && "YES".equalsIgnoreCase((String)o)) {
            return Boolean.TRUE;
        }
        return this.getBoolean(columnName);
    }
}

