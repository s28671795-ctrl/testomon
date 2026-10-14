/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.invoker;

import org.apache.cxf.message.Exchange;

public interface Factory {
    public Object create(Exchange var1) throws Throwable;

    public void release(Exchange var1, Object var2);
}

