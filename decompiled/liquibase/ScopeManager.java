/*
 * Decompiled with CFR 0.152.
 */
package liquibase;

import liquibase.Scope;

public abstract class ScopeManager {
    public abstract Scope getCurrentScope();

    protected abstract void setCurrentScope(Scope var1);

    protected abstract Scope init(Scope var1) throws Exception;
}

