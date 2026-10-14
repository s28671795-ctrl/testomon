/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl.binding;

import javax.wsdl.Binding;
import org.apache.cxf.binding.BindingFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.ServiceInfo;

public interface WSDLBindingFactory
extends BindingFactory {
    public BindingInfo createBindingInfo(ServiceInfo var1, Binding var2, String var3);
}

