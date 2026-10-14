/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;

public interface Interceptor<T extends Message> {
    public void handleMessage(T var1) throws Fault;

    public void handleFault(T var1);
}

