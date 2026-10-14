/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command;

import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;
import liquibase.command.CommandResultDefinition;
import liquibase.command.CommandScope;

public class CommandResults {
    private final SortedMap<String, Object> resultValues = new TreeMap<String, Object>();
    private final CommandScope commandScope;

    protected CommandResults(SortedMap<String, Object> resultValues, CommandScope commandScope) {
        this.resultValues.putAll(resultValues);
        this.commandScope = commandScope;
    }

    public CommandScope getCommandScope() {
        return this.commandScope;
    }

    public <DataType> DataType getResult(CommandResultDefinition<DataType> definition) {
        return (DataType)this.resultValues.get(definition.getName());
    }

    public Object getResult(String key) {
        return this.resultValues.get(key);
    }

    public SortedMap<String, Object> getResults() {
        return Collections.unmodifiableSortedMap(this.resultValues);
    }
}

