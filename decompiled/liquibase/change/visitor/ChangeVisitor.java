/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.visitor;

import java.util.Set;
import liquibase.changelog.ChangeLogChild;

public interface ChangeVisitor
extends ChangeLogChild {
    public String getName();

    public String getChange();

    public Set<String> getDbms();
}

