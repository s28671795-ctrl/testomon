/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command;

import java.util.Objects;

public class CommandResultDefinition<DataType>
implements Comparable<CommandResultDefinition<?>> {
    private final String name;
    private String description;
    private final Class<DataType> dataType;
    private DataType defaultValue;

    protected CommandResultDefinition(String name, Class<DataType> type) {
        this.name = name;
        this.dataType = type;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Class<DataType> getDataType() {
        return this.dataType;
    }

    public DataType getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public int compareTo(CommandResultDefinition<?> o) {
        return this.getName().compareTo(o.getName());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        CommandResultDefinition that = (CommandResultDefinition)o;
        return Objects.equals(this.name, that.name);
    }

    public int hashCode() {
        return Objects.hash(this.name);
    }

    public String toString() {
        return this.getName();
    }

    public static class Building<DataType> {
        private final CommandResultDefinition<DataType> newCommandResult;

        Building(CommandResultDefinition<DataType> newCommandResult) {
            this.newCommandResult = newCommandResult;
        }

        public Building<DataType> description(String description) {
            ((CommandResultDefinition)this.newCommandResult).description = description;
            return this;
        }

        public Building<DataType> defaultValue(DataType defaultValue) {
            ((CommandResultDefinition)this.newCommandResult).defaultValue = defaultValue;
            return this;
        }

        public CommandResultDefinition<DataType> build() {
            return this.newCommandResult;
        }
    }
}

