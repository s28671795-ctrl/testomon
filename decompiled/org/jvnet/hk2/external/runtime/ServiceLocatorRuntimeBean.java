/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.external.runtime;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ServiceLocatorRuntimeBean {
    public int getNumberOfDescriptors();

    public int getNumberOfChildren();

    public int getServiceCacheSize();

    public int getServiceCacheMaximumSize();

    public void clearServiceCache();

    public int getReflectionCacheSize();

    public void clearReflectionCache();
}

