/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.service;

import javax.annotation.Nonnull;

public interface ServiceableComponent<T> {
    @Nonnull
    public T getComponent();

    public void pinComponent();

    public void unpinComponent();

    public void unloadComponent();
}

