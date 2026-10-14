/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command;

import java.util.Objects;

public class CommandArgument
implements Comparable {
    private String name;
    private String description;
    private Class dataType;
    private boolean required;

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Class getDataType() {
        return this.dataType;
    }

    public boolean isRequired() {
        return this.required;
    }

    public int compareTo(Object o) {
        return this.getName().compareTo(((CommandArgument)o).getName());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        CommandArgument that = (CommandArgument)o;
        return Objects.equals(this.name, that.name);
    }

    public int hashCode() {
        return Objects.hash(this.name);
    }
}

