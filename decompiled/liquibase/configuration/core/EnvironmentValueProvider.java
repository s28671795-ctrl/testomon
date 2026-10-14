/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration.core;

import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicBoolean;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandFactory;
import liquibase.command.CommandScope;
import liquibase.configuration.AbstractMapConfigurationValueProvider;
import liquibase.configuration.ConfigurationDefinition;
import liquibase.configuration.LiquibaseConfiguration;
import liquibase.util.StringUtil;

public class EnvironmentValueProvider
extends AbstractMapConfigurationValueProvider {
    private final Map<String, String> environment = System.getenv();
    private static final AtomicBoolean printedInvalidEnvironmentVariablesMessage = new AtomicBoolean(false);

    @Override
    public int getPrecedence() {
        return 150;
    }

    @Override
    protected Map<?, ?> getMap() {
        return this.environment;
    }

    @Override
    public void validate(CommandScope commandScope) throws IllegalArgumentException {
        ArrayList<String> unknownVariables = new ArrayList<String>();
        LiquibaseConfiguration liquibaseConfiguration = Scope.getCurrentScope().getSingleton(LiquibaseConfiguration.class);
        for (Map.Entry<?, ?> entry : this.getMap().entrySet()) {
            String originalKey = (String)entry.getKey();
            String key = ((String)entry.getKey()).toLowerCase();
            if (!key.startsWith("liquibase_") || key.equals("liquibase_home") || key.startsWith("liquibase_launcher_")) continue;
            String editedKey = StringUtil.toCamelCase(key);
            ConfigurationDefinition<?> def = liquibaseConfiguration.getRegisteredDefinition(editedKey);
            if (def == null) {
                boolean found = false;
                SortedSet<CommandDefinition> commands = Scope.getCurrentScope().getSingleton(CommandFactory.class).getCommands(false);
                for (CommandDefinition commandDef : commands) {
                    SortedMap<String, CommandArgumentDefinition<?>> arguments = commandDef.getArguments();
                    StringBuilder fullName = new StringBuilder();
                    for (String name : commandDef.getName()) {
                        fullName.append(StringUtil.upperCaseFirst(name));
                    }
                    String simplifiedKey = StringUtil.lowerCaseFirst(editedKey.replace("liquibaseCommand", "").replaceFirst(fullName.toString(), ""));
                    boolean bl = found = arguments.get(simplifiedKey) != null;
                    if (!found) {
                        found = arguments.values().stream().anyMatch(argDef -> argDef.getAliases().contains(simplifiedKey));
                    }
                    if (!found) continue;
                    break;
                }
                if (found) continue;
                unknownVariables.add("- " + originalKey);
                continue;
            }
            if (def.getCurrentValue() != null && !def.getCurrentConfiguredValue().wasDefaultValueUsed()) continue;
            unknownVariables.add("- " + originalKey);
        }
        Boolean strict = GlobalConfiguration.STRICT.getCurrentValue();
        if (!unknownVariables.isEmpty()) {
            String message = System.lineSeparator() + System.lineSeparator() + "Liquibase detected the following invalid LIQUIBASE_* environment variables:" + System.lineSeparator() + System.lineSeparator() + StringUtil.join(unknownVariables, System.lineSeparator(), true) + System.lineSeparator() + System.lineSeparator();
            if (strict.booleanValue()) {
                message = message + "Please rename them and run your command again, or set liquibase.strict=FALSE or LIQUIBASE_STRICT=FALSE." + System.lineSeparator();
            }
            message = message + "Find the list of valid environment variables at https://docs.liquibase.com/environment-variables" + System.lineSeparator();
            if (!printedInvalidEnvironmentVariablesMessage.getAndSet(true)) {
                Scope.getCurrentScope().getUI().sendMessage("WARNING: " + message);
                Scope.getCurrentScope().getLog(EnvironmentValueProvider.class).warning(message);
            }
            if (strict.booleanValue()) {
                throw new IllegalArgumentException(message);
            }
        }
    }

    @Override
    protected String getSourceDescription() {
        return "Environment variable";
    }

    @Override
    protected boolean keyMatches(String wantedKey, String storedKey) {
        if (super.keyMatches(wantedKey, storedKey = StringUtil.trimToNull(storedKey))) {
            return true;
        }
        wantedKey = wantedKey.replace(".", "-");
        if ((wantedKey = StringUtil.toKabobCase(wantedKey)).equalsIgnoreCase(storedKey)) {
            return true;
        }
        wantedKey = wantedKey.replace("-", "_");
        return wantedKey.equalsIgnoreCase(storedKey);
    }
}

