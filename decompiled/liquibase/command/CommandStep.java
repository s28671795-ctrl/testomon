/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command;

import java.util.List;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.exception.CommandValidationException;

public interface CommandStep {
    @Deprecated
    public static final int ORDER_DEFAULT = 1000;
    @Deprecated
    public static final int ORDER_NOT_APPLICABLE = -1;

    public String[][] defineCommandNames();

    @Deprecated
    public int getOrder(CommandDefinition var1);

    public void adjustCommandDefinition(CommandDefinition var1);

    public void validate(CommandScope var1) throws CommandValidationException;

    public void run(CommandResultsBuilder var1) throws Exception;

    public List<Class<?>> requiredDependencies();

    public List<Class<?>> providedDependencies();
}

