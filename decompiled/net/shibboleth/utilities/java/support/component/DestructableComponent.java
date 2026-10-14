/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.component;

import net.shibboleth.utilities.java.support.component.Component;

public interface DestructableComponent
extends Component {
    public boolean isDestroyed();

    public void destroy();
}

