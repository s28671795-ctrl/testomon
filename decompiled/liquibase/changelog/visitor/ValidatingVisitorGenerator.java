/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.visitor;

import java.util.List;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.visitor.ValidatingVisitor;
import liquibase.plugin.Plugin;

public interface ValidatingVisitorGenerator
extends Plugin {
    public int getPriority();

    public ValidatingVisitor generateValidatingVisitor(List<RanChangeSet> var1);
}

