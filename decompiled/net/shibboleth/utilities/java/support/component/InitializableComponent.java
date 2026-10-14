/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.component;

import net.shibboleth.utilities.java.support.component.Component;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;

public interface InitializableComponent
extends Component {
    public boolean isInitialized();

    public void initialize() throws ComponentInitializationException;
}

