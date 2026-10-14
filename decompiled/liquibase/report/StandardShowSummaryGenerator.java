/*
 * Decompiled with CFR 0.152.
 */
package liquibase.report;

import java.util.Collections;
import java.util.List;
import liquibase.changelog.ChangeLogIterator;
import liquibase.changelog.ChangeSetStatus;
import liquibase.report.ShowSummaryGenerator;

public class StandardShowSummaryGenerator
implements ShowSummaryGenerator {
    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public List<ChangeSetStatus> getAllAdditionalChangeSetStatus(ChangeLogIterator runChangeLogIterator) {
        return Collections.emptyList();
    }

    @Override
    public void appendAdditionalSummaryMessages(StringBuilder builder, ChangeLogIterator runChangeLogIterator) {
    }
}

