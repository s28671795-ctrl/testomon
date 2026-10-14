/*
 * Decompiled with CFR 0.152.
 */
package liquibase.report;

import java.util.List;
import liquibase.changelog.ChangeLogIterator;
import liquibase.changelog.ChangeSetStatus;
import liquibase.plugin.Plugin;

public interface ShowSummaryGenerator
extends Plugin {
    public int getPriority();

    public List<ChangeSetStatus> getAllAdditionalChangeSetStatus(ChangeLogIterator var1);

    public void appendAdditionalSummaryMessages(StringBuilder var1, ChangeLogIterator var2);
}

