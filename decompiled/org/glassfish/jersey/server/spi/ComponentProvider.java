/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.spi;

import java.util.Set;
import org.glassfish.jersey.internal.inject.InjectionManager;

public interface ComponentProvider {
    public void initialize(InjectionManager var1);

    public boolean bind(Class<?> var1, Set<Class<?>> var2);

    public void done();
}

