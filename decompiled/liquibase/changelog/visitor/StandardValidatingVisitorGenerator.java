/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.visitor;

import java.util.List;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.visitor.ValidatingVisitor;
import liquibase.changelog.visitor.ValidatingVisitorGenerator;

public class StandardValidatingVisitorGenerator
implements ValidatingVisitorGenerator {
    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public ValidatingVisitor generateValidatingVisitor(List<RanChangeSet> ranChangeSetList) {
        return new ValidatingVisitor(ranChangeSetList);
    }
}

