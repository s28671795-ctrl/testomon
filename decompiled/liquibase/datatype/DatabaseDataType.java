/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype;

import java.util.Locale;
import liquibase.util.StringUtil;

public class DatabaseDataType {
    private String type;

    public DatabaseDataType(String type) {
        this.type = type;
    }

    public DatabaseDataType(String name, Object ... parameters) {
        if (parameters == null) {
            parameters = new Object[]{};
        }
        this.type = name;
        String[] stringParams = new String[parameters.length];
        if (parameters.length > 0) {
            for (int i = 0; i < parameters.length; ++i) {
                stringParams[i] = parameters[i] == null ? "NULL" : parameters[i].toString();
            }
            this.type = this.type + "(" + StringUtil.join(stringParams, ", ") + ")";
        }
    }

    public void addAdditionalInformation(String additionalInformation) {
        if (additionalInformation != null) {
            this.type = this.type + " " + additionalInformation;
        }
    }

    public boolean isAutoIncrement() {
        return "serial".equals(this.type.toLowerCase(Locale.US)) || "bigserial".equals(this.type.toLowerCase(Locale.US)) || "smallserial".equals(this.type.toLowerCase(Locale.US));
    }

    public String toSql() {
        return this.toString();
    }

    public String toString() {
        return this.type;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

