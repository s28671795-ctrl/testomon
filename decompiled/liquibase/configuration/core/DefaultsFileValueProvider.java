/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandFactory;
import liquibase.command.CommandScope;
import liquibase.configuration.AbstractMapConfigurationValueProvider;
import liquibase.configuration.LiquibaseConfiguration;
import liquibase.servicelocator.LiquibaseService;
import liquibase.util.StringUtil;

@LiquibaseService(skip=true)
public class DefaultsFileValueProvider
extends AbstractMapConfigurationValueProvider {
    private final Properties properties;
    private final String sourceDescription;

    protected DefaultsFileValueProvider(Properties properties) {
        this.properties = properties;
        this.sourceDescription = "Passed default properties";
    }

    public DefaultsFileValueProvider(InputStream stream, String sourceDescription) throws IOException {
        this.sourceDescription = sourceDescription;
        this.properties = new Properties();
        this.properties.load(stream);
        this.trimAllProperties();
    }

    public DefaultsFileValueProvider(File path) throws IOException {
        this.sourceDescription = "File " + path.getAbsolutePath();
        try (InputStream stream = Files.newInputStream(path.toPath(), new OpenOption[0]);){
            this.properties = new Properties();
            this.properties.load(stream);
            this.trimAllProperties();
        }
    }

    @Override
    public void validate(CommandScope commandScope) throws IllegalArgumentException {
        boolean strict = GlobalConfiguration.STRICT.getCurrentValue();
        TreeSet<String> invalidKeys = new TreeSet<String>();
        for (Map.Entry<Object, Object> entry : this.properties.entrySet()) {
            String keyAsArg;
            String key = (String)entry.getKey();
            String originalKey = key = StringUtil.toCamelCase(key);
            if (key.equalsIgnoreCase("strict") || key.startsWith("parameter.")) continue;
            String genericCommandPrefix = "liquibase.command.";
            String targetedCommandPrefix = "liquibase.command." + StringUtil.join(commandScope.getCommand().getName(), ".") + ".";
            if (!key.contains(".")) {
                if (commandScope.getCommand().getArgument(key) != null) continue;
                if (!key.startsWith("liquibase")) {
                    key = "liquibase." + key;
                }
                if (Scope.getCurrentScope().getSingleton(LiquibaseConfiguration.class).getRegisteredDefinition(key) != null) continue;
                invalidKeys.add(" - '" + originalKey + "'");
                continue;
            }
            if (key.startsWith(targetedCommandPrefix)) {
                keyAsArg = key.replace(targetedCommandPrefix, "");
                if (commandScope.getCommand().getArgument(keyAsArg) != null) continue;
                invalidKeys.add(" - '" + originalKey + "'");
                continue;
            }
            if (key.startsWith("liquibase.command.")) {
                keyAsArg = key.replace("liquibase.command.", "");
                boolean foundMatch = false;
                for (CommandDefinition definition : Scope.getCurrentScope().getSingleton(CommandFactory.class).getCommands(true)) {
                    if (definition.getArgument(keyAsArg) == null) continue;
                    foundMatch = true;
                    break;
                }
                if (foundMatch) continue;
                invalidKeys.add(" - '" + originalKey + "'");
                continue;
            }
            if (Scope.getCurrentScope().getSingleton(LiquibaseConfiguration.class).getRegisteredDefinition(key) != null) continue;
            invalidKeys.add(" - '" + originalKey + "'");
        }
        if (invalidKeys.size() > 0) {
            if (strict) {
                String message = "Strict check failed due to undefined key(s) for '" + StringUtil.join(commandScope.getCommand().getName(), " ") + "' command in " + StringUtil.lowerCaseFirst(this.sourceDescription) + "':\n" + StringUtil.join(invalidKeys, "\n") + "\nTo define keys that could apply to any command, prefix it with 'liquibase.command.'\nTo disable strict checking, remove 'strict' from the file.";
                throw new IllegalArgumentException(message);
            }
            Scope.getCurrentScope().getLog(this.getClass()).warning("Potentially ignored key(s) in " + StringUtil.lowerCaseFirst(this.sourceDescription) + "\n" + StringUtil.join(invalidKeys, "\n"));
        }
    }

    private void trimAllProperties() {
        this.properties.forEach((BiConsumer<? super Object, ? super Object>)((BiConsumer<Object, Object>)(key, value) -> {
            if (value == null) {
                return;
            }
            if (!(value instanceof String)) {
                return;
            }
            this.properties.put(key, StringUtil.trimToEmpty((String)value));
        }));
    }

    @Override
    public int getPrecedence() {
        return 50;
    }

    @Override
    public Map<?, ?> getMap() {
        return this.properties;
    }

    @Override
    protected boolean keyMatches(String wantedKey, String storedKey) {
        if (super.keyMatches(wantedKey, storedKey)) {
            return true;
        }
        return wantedKey.replaceFirst("^liquibase\\.", "").equalsIgnoreCase(StringUtil.toCamelCase(storedKey)) || wantedKey.replaceFirst("^liquibase\\.command\\.", "").equalsIgnoreCase(StringUtil.toCamelCase(storedKey));
    }

    @Override
    protected String getSourceDescription() {
        return this.sourceDescription;
    }
}

