/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.util.List;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceHandle;
import org.jvnet.hk2.internal.SystemDescriptor;

public interface Creator<T> {
    public List<Injectee> getInjectees();

    public T create(ServiceHandle<?> var1, SystemDescriptor<?> var2) throws MultiException;

    public void dispose(T var1) throws MultiException;
}

