/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandScope;
import liquibase.command.CommandStep;
import liquibase.exception.CommandValidationException;
import liquibase.util.StringUtil;

public abstract class AbstractCommandStep
implements CommandStep {
    protected static ResourceBundle coreBundle = ResourceBundle.getBundle("liquibase/i18n/liquibase-core");

    @Override
    public List<Class<?>> requiredDependencies() {
        return Collections.emptyList();
    }

    @Override
    public List<Class<?>> providedDependencies() {
        return Collections.emptyList();
    }

    @Override
    public int getOrder(CommandDefinition commandDefinition) {
        String[][] definedCommandNames = this.defineCommandNames();
        if (definedCommandNames != null) {
            for (String[] thisCommandName : definedCommandNames) {
                if (thisCommandName == null || !StringUtil.join(Arrays.asList(thisCommandName), " ").equalsIgnoreCase(StringUtil.join(Arrays.asList(commandDefinition.getName()), " "))) continue;
                return 1000;
            }
        }
        return -1;
    }

    @Override
    public void validate(CommandScope commandScope) throws CommandValidationException {
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
    }
}

