/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.invoker;

import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.service.invoker.AbstractInvoker;

@NoJSR250Annotations
public class BeanInvoker
extends AbstractInvoker {
    private Object proxy;

    public BeanInvoker(Object proxy) {
        this.proxy = proxy;
    }

    @Override
    public Object getServiceObject(Exchange ex) {
        return this.proxy;
    }
}

