/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding;

import org.apache.cxf.BusException;
import org.apache.cxf.binding.BindingFactory;

public interface BindingFactoryManager {
    public void registerBindingFactory(String var1, BindingFactory var2);

    public void unregisterBindingFactory(String var1);

    public BindingFactory getBindingFactory(String var1) throws BusException;
}

