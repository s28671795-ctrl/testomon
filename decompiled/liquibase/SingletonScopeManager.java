/*
 * Decompiled with CFR 0.152.
 */
package liquibase;

import liquibase.Scope;
import liquibase.ScopeManager;

public class SingletonScopeManager
extends ScopeManager {
    private Scope currentScope;

    @Override
    public synchronized Scope getCurrentScope() {
        return this.currentScope;
    }

    @Override
    protected Scope init(Scope scope) throws Exception {
        return scope;
    }

    @Override
    protected synchronized void setCurrentScope(Scope scope) {
        this.currentScope = scope;
    }
}

