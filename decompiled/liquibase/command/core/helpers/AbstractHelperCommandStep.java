/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core.helpers;

import liquibase.command.AbstractCommandStep;
import liquibase.command.CommandDefinition;

public abstract class AbstractHelperCommandStep
extends AbstractCommandStep {
    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        boolean allHelpers = commandDefinition.getPipeline().stream().allMatch(cs -> cs instanceof AbstractHelperCommandStep);
        if (allHelpers) {
            commandDefinition.setHidden(true);
        }
    }
}

