/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command;

import java.util.Collections;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;
import liquibase.Scope;
import liquibase.command.CommandFactory;
import liquibase.command.CommandScope;
import liquibase.command.CommonArgumentNames;
import liquibase.configuration.ConfigurationValueConverter;
import liquibase.configuration.ConfigurationValueObfuscator;
import liquibase.exception.CommandValidationException;
import liquibase.exception.MissingRequiredArgumentException;
import liquibase.integration.commandline.LiquibaseCommandLineConfiguration;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;

public class CommandArgumentDefinition<DataType>
implements Comparable<CommandArgumentDefinition<?>> {
    private static final String ALLOWED_ARGUMENT_REGEX = "[a-zA-Z0-9]+";
    private static final Pattern ALLOWED_ARGUMENT_PATTERN = Pattern.compile("[a-zA-Z0-9]+");
    private final String name;
    private final SortedSet<String> aliases = new TreeSet<String>();
    private final Class<DataType> dataType;
    private String description;
    private boolean required;
    private boolean hidden;
    private DataType defaultValue;
    private String defaultValueDescription;
    private ConfigurationValueConverter<DataType> valueConverter;
    private ConfigurationValueObfuscator<DataType> valueObfuscator;
    private CommandArgumentDefinition<?> supersededBy;

    protected CommandArgumentDefinition(String name, Class<DataType> type) {
        this.name = name;
        this.dataType = type;
        this.valueConverter = value -> ObjectUtil.convert(value, type, name);
    }

    public String getName() {
        return this.name;
    }

    public SortedSet<String> getAliases() {
        return Collections.unmodifiableSortedSet(this.aliases);
    }

    public String getDescription() {
        return this.description;
    }

    public Class<DataType> getDataType() {
        return this.dataType;
    }

    public boolean isRequired() {
        return this.required;
    }

    public CommandArgumentDefinition<?> getSupersededBy() {
        return this.supersededBy;
    }

    public void setSupersededBy(CommandArgumentDefinition<?> supersededBy) {
        this.supersededBy = supersededBy;
    }

    public boolean getHidden() {
        return this.hidden;
    }

    public DataType getDefaultValue() {
        return this.defaultValue;
    }

    public String getDefaultValueDescription() {
        return this.defaultValueDescription;
    }

    public ConfigurationValueConverter<DataType> getValueConverter() {
        return this.valueConverter;
    }

    public ConfigurationValueObfuscator<DataType> getValueObfuscator() {
        return this.valueObfuscator;
    }

    public void validate(CommandScope commandScope) throws CommandValidationException {
        Object currentValue = commandScope.getArgumentValue(this);
        if (this.isRequired() && currentValue == null && (this.getSupersededBy() == null || commandScope.getArgumentValue(this.getSupersededBy()) == null)) {
            throw new CommandValidationException(LiquibaseCommandLineConfiguration.ARGUMENT_CONVERTER.getCurrentValue().convert(this.getName()), "missing required argument", new MissingRequiredArgumentException(this.getName()));
        }
    }

    @Override
    public int compareTo(CommandArgumentDefinition<?> o) {
        return this.getName().compareTo(o.getName());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        CommandArgumentDefinition that = (CommandArgumentDefinition)o;
        return Objects.equals(this.name, that.name);
    }

    public int hashCode() {
        return Objects.hash(this.name);
    }

    public String toString() {
        String returnString = this.getName();
        if (this.required) {
            returnString = returnString + " (required)";
        }
        return returnString;
    }

    public static class Building<DataType> {
        private final String[][] commandNames;
        private final CommandArgumentDefinition<DataType> newCommandArgument;

        Building(String[][] commandNames, CommandArgumentDefinition<DataType> newCommandArgument) {
            this.commandNames = commandNames;
            this.newCommandArgument = newCommandArgument;
            if (newCommandArgument.getName().equalsIgnoreCase(CommonArgumentNames.URL.getArgumentName()) || newCommandArgument.getName().equalsIgnoreCase(CommonArgumentNames.REFERENCE_URL.getArgumentName())) {
                this.setValueObfuscator(ConfigurationValueObfuscator.URL_OBFUSCATOR);
            }
        }

        public Building<DataType> required() {
            ((CommandArgumentDefinition)this.newCommandArgument).required = true;
            return this;
        }

        public Building<DataType> supersededBy(CommandArgumentDefinition<?> commandArgumentDefinition) {
            ((CommandArgumentDefinition)this.newCommandArgument).supersededBy = (CommandArgumentDefinition)commandArgumentDefinition;
            return this;
        }

        public Building<DataType> optional() {
            ((CommandArgumentDefinition)this.newCommandArgument).required = false;
            return this;
        }

        public Building<DataType> hidden() {
            ((CommandArgumentDefinition)this.newCommandArgument).hidden = true;
            return this;
        }

        public Building<DataType> description(String description) {
            ((CommandArgumentDefinition)this.newCommandArgument).description = description;
            return this;
        }

        public Building<DataType> defaultValue(DataType defaultValue, String description) {
            ((CommandArgumentDefinition)this.newCommandArgument).defaultValue = defaultValue;
            ((CommandArgumentDefinition)this.newCommandArgument).defaultValueDescription = description;
            return this;
        }

        public Building<DataType> defaultValue(DataType defaultValue) {
            String description = null;
            if (defaultValue != null) {
                description = String.valueOf(defaultValue);
            }
            return this.defaultValue(defaultValue, description);
        }

        public Building<DataType> setValueHandler(ConfigurationValueConverter<DataType> valueHandler) {
            ((CommandArgumentDefinition)this.newCommandArgument).valueConverter = valueHandler;
            return this;
        }

        public Building<DataType> setValueObfuscator(ConfigurationValueObfuscator<DataType> valueObfuscator) {
            ((CommandArgumentDefinition)this.newCommandArgument).valueObfuscator = valueObfuscator;
            return this;
        }

        public Building<DataType> addAlias(String alias) {
            ((CommandArgumentDefinition)this.newCommandArgument).aliases.add(alias);
            return this;
        }

        public CommandArgumentDefinition<DataType> build() throws IllegalArgumentException {
            if (!ALLOWED_ARGUMENT_PATTERN.matcher(((CommandArgumentDefinition)this.newCommandArgument).name).matches()) {
                throw new IllegalArgumentException("Invalid argument format: " + ((CommandArgumentDefinition)this.newCommandArgument).name);
            }
            for (String[] commandName : this.commandNames) {
                try {
                    Scope.getCurrentScope().getSingleton(CommandFactory.class).register(commandName, this.newCommandArgument);
                }
                catch (IllegalArgumentException iae) {
                    Scope.getCurrentScope().getLog(CommandArgumentDefinition.class).warning("Unable to register command '" + StringUtil.join(commandName, " ") + "' argument '" + this.newCommandArgument.getName() + "': " + iae.getMessage());
                    throw iae;
                }
            }
            return this.newCommandArgument;
        }
    }
}

