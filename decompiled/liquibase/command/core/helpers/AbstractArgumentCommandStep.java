/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core.helpers;

import java.util.List;
import liquibase.command.AbstractCommandStep;
import liquibase.command.CommandDefinition;
import liquibase.util.HelpUtil;

public abstract class AbstractArgumentCommandStep
extends AbstractCommandStep {
    @Override
    public final void adjustCommandDefinition(CommandDefinition commandDefinition) {
        HelpUtil.hideCommandNameInHelpView(commandDefinition);
    }

    @Override
    public abstract List<Class<?>> providedDependencies();
}

