/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.impl;

import javax.wsdl.Binding;
import javax.xml.bind.JAXBException;
import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.ws.addressing.wsdl.UsingAddressing;
import org.apache.cxf.wsdl.JAXBExtensionHelper;
import org.apache.cxf.wsdl.WSDLExtensionLoader;
import org.apache.cxf.wsdl.WSDLManager;

@NoJSR250Annotations
public final class AddressingWSDLExtensionLoader
implements WSDLExtensionLoader {
    public AddressingWSDLExtensionLoader(Bus b) {
        WSDLManager manager = b.getExtension(WSDLManager.class);
        this.createExtensor(manager, Binding.class, UsingAddressing.class);
    }

    public void createExtensor(WSDLManager manager, Class<?> parentType, Class<?> elementType) {
        try {
            JAXBExtensionHelper.addExtensions(manager.getExtensionRegistry(), parentType, elementType, null, this.getClass().getClassLoader());
        }
        catch (JAXBException jAXBException) {
            // empty catch block
        }
    }
}

