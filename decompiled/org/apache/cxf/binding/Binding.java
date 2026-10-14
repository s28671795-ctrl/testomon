/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding;

import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.BindingInfo;

public interface Binding
extends InterceptorProvider {
    public Message createMessage();

    public Message createMessage(Message var1);

    public BindingInfo getBindingInfo();
}

