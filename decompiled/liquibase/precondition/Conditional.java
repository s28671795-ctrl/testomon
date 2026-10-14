/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition;

import liquibase.precondition.core.PreconditionContainer;

public interface Conditional {
    public PreconditionContainer getPreconditions();

    public void setPreconditions(PreconditionContainer var1);
}

