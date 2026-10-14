/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.binding;

import org.apache.cxf.jaxws.binding.AbstractBindingImpl;
import org.apache.cxf.jaxws.support.JaxWsEndpointImpl;

public class DefaultBindingImpl
extends AbstractBindingImpl {
    public DefaultBindingImpl(JaxWsEndpointImpl endpoint) {
        super(endpoint);
    }

    @Override
    public String getBindingID() {
        return "";
    }
}

