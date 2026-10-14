/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  picocli.CommandLine$Model$OptionSpec
 *  picocli.CommandLine$ParseResult
 */
package liquibase.integration.commandline;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import liquibase.configuration.AbstractMapConfigurationValueProvider;
import picocli.CommandLine;

public class CommandLineArgumentValueProvider
extends AbstractMapConfigurationValueProvider {
    private final SortedMap<String, Object> argumentValues = new TreeMap<String, Object>();

    public CommandLineArgumentValueProvider(CommandLine.ParseResult parseResult) {
        while (parseResult != null) {
            for (CommandLine.Model.OptionSpec option : parseResult.matchedOptions()) {
                this.argumentValues.put(option.names()[0], option.getValue());
            }
            parseResult = parseResult.subcommand();
        }
    }

    @Override
    public int getPrecedence() {
        return 250;
    }

    @Override
    protected Map<?, ?> getMap() {
        return this.argumentValues;
    }

    @Override
    protected boolean keyMatches(String wantedKey, String storedKey) {
        if (super.keyMatches(wantedKey, storedKey = String.valueOf(storedKey).replaceFirst("^--", ""))) {
            return true;
        }
        if (wantedKey.startsWith("liquibase.command.")) {
            return super.keyMatches(wantedKey.replaceFirst("^liquibase\\.command\\.", ""), storedKey);
        }
        return super.keyMatches(wantedKey.replaceFirst("^liquibase\\.", ""), storedKey);
    }

    @Override
    protected String getSourceDescription() {
        return "Command argument";
    }
}

