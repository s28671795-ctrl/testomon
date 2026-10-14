/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import liquibase.Scope;
import liquibase.SingletonObject;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandExecutionException;
import liquibase.command.CommandOverride;
import liquibase.command.CommandResult;
import liquibase.command.CommandStep;
import liquibase.command.LiquibaseCommand;
import liquibase.command.LiquibaseCommandFactory;
import liquibase.servicelocator.ServiceLocator;
import liquibase.util.DependencyUtil;
import liquibase.util.StringUtil;

public class CommandFactory
implements SingletonObject {
    private Collection<CommandStep> allInstances;
    private final Map<String[], CommandDefinition> commandDefinitions = new ConcurrentHashMap<String[], CommandDefinition>();
    private Map<Class<? extends CommandStep>, CommandStep> commandOverrides;
    private final Map<String, Set<CommandArgumentDefinition<?>>> commandArgumentDefinitions = new HashMap();

    public static CommandFactory getInstance() {
        return Scope.getCurrentScope().getSingleton(CommandFactory.class);
    }

    protected CommandFactory() {
    }

    public CommandDefinition getCommandDefinition(String ... commandName) throws IllegalArgumentException {
        CommandDefinition commandDefinition = this.commandDefinitions.get(commandName);
        if (commandDefinition == null) {
            commandDefinition = new CommandDefinition(commandName);
            this.computePipelineForCommandDefinition(commandDefinition);
            this.consolidateCommandArgumentsForCommand(commandDefinition);
            this.adjustCommandDefinitionForSteps(commandDefinition);
            this.commandDefinitions.put(commandName, commandDefinition);
        }
        return commandDefinition;
    }

    private void computePipelineForCommandDefinition(CommandDefinition commandDefinition) {
        LinkedHashSet pipeline = new LinkedHashSet();
        DependencyUtil.DependencyGraph<CommandStep> pipelineGraph = new DependencyUtil.DependencyGraph<CommandStep>(p -> {
            if (p != null) {
                pipeline.add(p);
            }
        });
        Collection<CommandStep> allCommandStepInstances = this.findAllInstances();
        Map<Class<? extends CommandStep>, CommandStep> overrides = this.findAllOverrides(allCommandStepInstances);
        for (CommandStep step : allCommandStepInstances) {
            if (step.getOrder(commandDefinition) <= 0) continue;
            Optional<CommandStep> overrideStep = this.getOverride(overrides, step);
            this.findDependenciesForCommand(pipelineGraph, allCommandStepInstances, overrideStep.orElse(step), overrides);
        }
        pipelineGraph.computeDependencies();
        if (pipeline.isEmpty()) {
            throw new IllegalArgumentException("Unknown command '" + StringUtil.join(commandDefinition.getName(), " ") + "'");
        }
        pipeline.forEach(p -> {
            try {
                commandDefinition.add((CommandStep)p.getClass().getConstructor(new Class[0]).newInstance(new Object[0]));
            }
            catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            }
        });
    }

    private void findDependenciesForCommand(DependencyUtil.DependencyGraph<CommandStep> pipelineGraph, Collection<CommandStep> allCommandStepInstances, CommandStep step, Map<Class<? extends CommandStep>, CommandStep> overrides) {
        if (step.requiredDependencies() == null || step.requiredDependencies().isEmpty()) {
            pipelineGraph.add(null, step);
        } else {
            for (Class<?> d : step.requiredDependencies()) {
                CommandStep provider = this.whoProvidesClass(d, allCommandStepInstances, overrides);
                Optional<CommandStep> override = this.getOverride(overrides, provider);
                pipelineGraph.add(override.orElse(provider), step);
                this.findDependenciesForCommand(pipelineGraph, allCommandStepInstances, override.orElse(provider), overrides);
            }
        }
    }

    private CommandStep whoProvidesClass(Class<?> dependency, Collection<CommandStep> allCommandStepInstances, Map<Class<? extends CommandStep>, CommandStep> overrides) {
        return allCommandStepInstances.stream().filter(cs -> cs.providedDependencies() != null && cs.providedDependencies().contains(dependency) && !overrides.containsValue(cs)).reduce((a, b) -> {
            throw new IllegalStateException(String.format("More than one CommandStep provides class %s. Steps: %s, %s", dependency.getName(), a.getClass().getName(), b.getClass().getName()));
        }).orElseThrow(() -> new IllegalStateException("Unable to find CommandStep provider for class " + dependency.getName()));
    }

    private void consolidateCommandArgumentsForCommand(CommandDefinition commandDefinition) {
        HashSet<CommandArgumentDefinition> stepArguments = new HashSet<CommandArgumentDefinition>();
        for (CommandStep step : commandDefinition.getPipeline()) {
            String[][] names = step.defineCommandNames();
            if (names == null) continue;
            for (String[] name : names) {
                for (CommandArgumentDefinition command : (Set)this.commandArgumentDefinitions.getOrDefault(StringUtil.join(name, " "), new HashSet())) {
                    stepArguments.stream().filter(cad -> cad.getName().equals(command.getName())).findAny().ifPresent(stepArguments::remove);
                    stepArguments.add(command);
                }
            }
        }
        if (!stepArguments.isEmpty()) {
            for (CommandArgumentDefinition commandArg : stepArguments) {
                commandDefinition.add(commandArg);
            }
        }
    }

    private void adjustCommandDefinitionForSteps(CommandDefinition commandDefinition) {
        for (CommandStep step : commandDefinition.getPipeline()) {
            step.adjustCommandDefinition(commandDefinition);
        }
    }

    public SortedSet<CommandDefinition> getCommands(boolean includeInternal) {
        HashMap<String, String[]> commandNames = new HashMap<String, String[]>();
        for (CommandStep step : this.findAllInstances()) {
            String[][] names = step.defineCommandNames();
            if (names == null) continue;
            for (String[] name : names) {
                commandNames.put(StringUtil.join(name, " "), name);
            }
        }
        TreeSet<CommandDefinition> commands = new TreeSet<CommandDefinition>();
        for (String[] commandName : commandNames.values()) {
            try {
                CommandDefinition definition = this.getCommandDefinition(commandName);
                if (!includeInternal && definition.getInternal()) continue;
                commands.add(definition);
            }
            catch (IllegalArgumentException illegalArgumentException) {}
        }
        return Collections.unmodifiableSortedSet(commands);
    }

    protected void register(String[] commandName, CommandArgumentDefinition<?> definition) {
        String commandNameKey = StringUtil.join(commandName, " ");
        if (!this.commandArgumentDefinitions.containsKey(commandNameKey)) {
            this.commandArgumentDefinitions.put(commandNameKey, new TreeSet());
        }
        if (this.commandArgumentDefinitions.get(commandNameKey).contains(definition)) {
            throw new IllegalArgumentException("Duplicate argument '" + definition.getName() + "' found for command '" + commandNameKey + "'");
        }
        if (definition.isRequired() && definition.getDefaultValue() != null) {
            throw new IllegalArgumentException("Argument '" + definition.getName() + "' for command '" + commandNameKey + "' has both a default value and the isRequired flag set to true. Arguments with default values cannot be marked as required.");
        }
        this.commandArgumentDefinitions.get(commandNameKey).add(definition);
    }

    protected void unregister(String[] commandName) {
        this.commandArgumentDefinitions.remove(StringUtil.join(commandName, " "));
    }

    public LiquibaseCommand getCommand(String commandName) {
        return Scope.getCurrentScope().getSingleton(LiquibaseCommandFactory.class).getCommand(commandName);
    }

    public <T extends CommandResult> T execute(LiquibaseCommand<T> command) throws CommandExecutionException {
        command.validate();
        try {
            return command.run();
        }
        catch (Exception e) {
            if (e instanceof CommandExecutionException) {
                throw (CommandExecutionException)e;
            }
            throw new CommandExecutionException(e);
        }
    }

    private synchronized Collection<CommandStep> findAllInstances() {
        if (this.allInstances == null) {
            this.allInstances = new ArrayList<CommandStep>();
            ServiceLocator serviceLocator = Scope.getCurrentScope().getServiceLocator();
            this.allInstances.addAll(serviceLocator.findInstances(CommandStep.class));
        }
        return this.allInstances;
    }

    private Map<Class<? extends CommandStep>, CommandStep> findAllOverrides(Collection<CommandStep> allCommandSteps) throws RuntimeException {
        if (this.commandOverrides == null) {
            HashMap<Class<? extends CommandStep>, List<CommandStep>> overrides = new HashMap<Class<? extends CommandStep>, List<CommandStep>>();
            allCommandSteps.stream().filter(commandStep -> commandStep.getClass().isAnnotationPresent(CommandOverride.class)).forEach(overrideStep -> {
                Class<? extends CommandStep> classToOverride = overrideStep.getClass().getAnnotation(CommandOverride.class).override();
                overrides.computeIfAbsent(classToOverride, val -> new ArrayList()).add(overrideStep);
            });
            this.validateOverrides(overrides);
            HashMap<Class<? extends CommandStep>, CommandStep> validOverrides = new HashMap<Class<? extends CommandStep>, CommandStep>();
            overrides.forEach((overriddenClass, validOverride) -> validOverride.stream().findFirst().ifPresent(valid -> validOverrides.put((Class<? extends CommandStep>)overriddenClass, (CommandStep)valid)));
            this.commandOverrides = validOverrides;
        }
        return this.commandOverrides;
    }

    private void validateOverrides(Map<Class<? extends CommandStep>, List<CommandStep>> overrides) throws RuntimeException {
        HashMap<Class, List> invalidOverrides = new HashMap<Class, List>();
        overrides.forEach((step, overrideSteps) -> {
            if (overrideSteps.size() > 1) {
                invalidOverrides.put((Class)step, (List)overrideSteps);
            }
        });
        invalidOverrides.forEach((step, overrideSteps) -> Scope.getCurrentScope().getLog(this.getClass()).severe(String.format("Found multiple command steps overriding %s! A command may have at most one override. Invalid overrides include: %s", step.getSimpleName(), overrideSteps.stream().map(ovrr -> ovrr.getClass().getSimpleName()).collect(Collectors.joining(", ")))));
        if (!invalidOverrides.isEmpty()) {
            throw new RuntimeException(String.format("Found more than one CommandOverride for CommandStep(s): %s! A command may have at most one override.", invalidOverrides.keySet().stream().map(Class::getSimpleName).collect(Collectors.joining(", "))));
        }
    }

    private Optional<CommandStep> getOverride(Map<Class<? extends CommandStep>, CommandStep> overrides, CommandStep step) {
        CommandStep overrideStep = overrides.get(step.getClass());
        if (overrideStep != null && overrideStep.getClass() != step.getClass()) {
            Scope.getCurrentScope().getLog(this.getClass()).fine(String.format("Found %s override for %s! Using %s in pipeline.", overrideStep.getClass().getSimpleName(), step.getClass().getSimpleName(), overrideStep.getClass().getSimpleName()));
            return Optional.of(overrideStep);
        }
        return Optional.empty();
    }
}

