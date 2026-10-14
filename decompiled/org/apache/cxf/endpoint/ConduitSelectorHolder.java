/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import org.apache.cxf.endpoint.ConduitSelector;

public interface ConduitSelectorHolder {
    public ConduitSelector getConduitSelector();

    public void setConduitSelector(ConduitSelector var1);
}

