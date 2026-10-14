/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement;

public class DatabaseFunction {
    public static final String CURRENT_DATE_TIME_PLACE_HOLDER = "current_datetime";
    private String schemaName;
    private final String value;

    public DatabaseFunction(String value) {
        this.value = value;
    }

    public DatabaseFunction(String schemaName, String value) {
        this.schemaName = schemaName;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return (this.getSchemaName() == null ? "" : this.getSchemaName() + ".") + this.getValue();
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public boolean equals(Object obj) {
        if (obj instanceof DatabaseFunction) {
            return this.toString().equals(obj.toString());
        }
        return super.equals(obj);
    }

    public int hashCode() {
        return this.toString().hashCode();
    }
}

