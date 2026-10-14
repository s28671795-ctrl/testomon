/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import liquibase.command.CommandDefinition;

public class HelpUtil {
    public static final String HISTORY_URL = "https://docs.liquibase.com/DATABASECHANGELOGHISTORY";

    public static void hideCommandNameInHelpView(CommandDefinition commandDefinition) {
        if (commandDefinition.getPipeline().size() == 1) {
            commandDefinition.setInternal(true);
        }
    }
}

