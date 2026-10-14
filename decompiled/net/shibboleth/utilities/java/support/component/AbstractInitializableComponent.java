/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.component;

import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.component.DestructableComponent;
import net.shibboleth.utilities.java.support.component.InitializableComponent;

public abstract class AbstractInitializableComponent
implements DestructableComponent,
InitializableComponent {
    private boolean isDestroyed;
    private boolean isInitialized;

    @Override
    public final boolean isDestroyed() {
        return this.isDestroyed;
    }

    @Override
    public boolean isInitialized() {
        return this.isInitialized;
    }

    @Override
    public final synchronized void destroy() {
        if (this.isDestroyed) {
            return;
        }
        this.doDestroy();
        this.isDestroyed = true;
    }

    @Override
    public final synchronized void initialize() throws ComponentInitializationException {
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        if (this.isInitialized()) {
            return;
        }
        this.doInitialize();
        this.isInitialized = true;
    }

    protected void doDestroy() {
    }

    protected void doInitialize() throws ComponentInitializationException {
    }
}

