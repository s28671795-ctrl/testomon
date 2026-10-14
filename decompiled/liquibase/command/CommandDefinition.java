/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandStep;
import liquibase.util.StringUtil;

public class CommandDefinition
implements Comparable<CommandDefinition> {
    private final String[] name;
    private final String concatName;
    private final SortedSet<CommandStep> pipeline;
    private final SortedMap<String, CommandArgumentDefinition<?>> arguments = new TreeMap();
    private String longDescription = null;
    private String shortDescription = null;
    private boolean internal;
    private boolean hidden;
    private String footer;
    private String groupFooter;
    private final Map<String, String> groupLongDescription = new HashMap<String, String>();
    private final Map<String, String> groupShortDescription = new HashMap<String, String>();
    private List<String[]> aliases = new ArrayList<String[]>();

    protected CommandDefinition(String[] name) {
        this.name = name;
        this.concatName = StringUtil.join(Arrays.asList(name), " ");
        this.pipeline = new TreeSet<CommandStep>((o1, o2) -> {
            int order = Integer.compare(o1.getOrder(this), o2.getOrder(this));
            if (order == 0) {
                return 1;
            }
            return order;
        });
    }

    public String[] getName() {
        return this.name;
    }

    @Override
    public int compareTo(CommandDefinition o) {
        return this.concatName.compareTo(o.concatName);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return this.concatName.equals(((CommandDefinition)o).concatName);
    }

    public int hashCode() {
        return this.concatName.hashCode();
    }

    public List<CommandStep> getPipeline() {
        return Collections.unmodifiableList(new ArrayList<CommandStep>(this.pipeline));
    }

    public CommandArgumentDefinition<?> getArgument(String argName) {
        argName = StringUtil.toCamelCase(argName);
        for (CommandArgumentDefinition<?> argumentDefinition : this.getArguments().values()) {
            if (argumentDefinition.getName().equalsIgnoreCase(argName)) {
                return argumentDefinition;
            }
            for (String alias : argumentDefinition.getAliases()) {
                if (!alias.equalsIgnoreCase(argName)) continue;
                return argumentDefinition;
            }
        }
        return null;
    }

    public SortedMap<String, CommandArgumentDefinition<?>> getArguments() {
        return Collections.unmodifiableSortedMap(this.arguments);
    }

    public <T> SortedSet<CommandArgumentDefinition<T>> getArguments(Class<T> argumentType) {
        TreeSet<CommandArgumentDefinition<T>> returnSet = new TreeSet<CommandArgumentDefinition<T>>();
        for (CommandArgumentDefinition<?> definition : this.arguments.values()) {
            if (!definition.getDataType().isAssignableFrom(argumentType)) continue;
            returnSet.add(definition);
        }
        return returnSet;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getGroupShortDescription(String[] group) {
        return this.groupShortDescription.get(StringUtil.join(group, " "));
    }

    public void setGroupShortDescription(String[] group, String shortDescription) {
        this.groupShortDescription.put(StringUtil.join(group, " "), shortDescription);
    }

    public String getGroupLongDescription(String[] group) {
        return this.groupLongDescription.get(StringUtil.join(group, " "));
    }

    public void setGroupLongDescription(String[] group, String longDescription) {
        this.groupLongDescription.put(StringUtil.join(group, " "), longDescription);
    }

    public String getLongDescription() {
        return this.longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getHelpFooter() {
        return this.footer;
    }

    public void setHelpFooter(String footer) {
        this.footer = footer;
    }

    public String getGroupHelpFooter() {
        return this.groupFooter;
    }

    public void setGroupHelpFooter(String groupFooter) {
        this.groupFooter = groupFooter;
    }

    void add(CommandStep step) {
        this.pipeline.add(step);
    }

    public void add(CommandArgumentDefinition commandArg) {
        this.arguments.put(commandArg.getName(), commandArg);
    }

    public boolean getInternal() {
        return this.internal;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    public boolean getHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean is(String ... commandName) {
        return Arrays.equals(this.getName(), commandName);
    }

    public void addAlias(String[] alias) {
        this.aliases.add(alias);
    }

    public List<String[]> getAliases() {
        return this.aliases;
    }
}

