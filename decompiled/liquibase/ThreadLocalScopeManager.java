/*
 * Decompiled with CFR 0.152.
 */
package liquibase;

import liquibase.Scope;
import liquibase.ScopeManager;

@Deprecated
public class ThreadLocalScopeManager
extends ScopeManager {
    private final Scope rootScope;
    private final ThreadLocal<Scope> threadLocalScopes = new ThreadLocal();

    public ThreadLocalScopeManager() {
        this(Scope.getCurrentScope());
    }

    public ThreadLocalScopeManager(Scope rootScope) {
        this.rootScope = rootScope;
    }

    @Override
    public synchronized Scope getCurrentScope() {
        Scope current = this.threadLocalScopes.get();
        if (current == null) {
            this.threadLocalScopes.set(this.rootScope);
            current = this.rootScope;
        }
        return current;
    }

    @Override
    protected synchronized void setCurrentScope(Scope scope) {
        this.threadLocalScopes.set(scope);
    }

    @Override
    protected Scope init(Scope scope) throws Exception {
        return this.rootScope;
    }
}

