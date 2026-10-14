/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import javax.wsdl.Port;
import javax.xml.bind.JAXBException;
import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.transports.http.configuration.HTTPServerPolicy;
import org.apache.cxf.wsdl.JAXBExtensionHelper;
import org.apache.cxf.wsdl.WSDLExtensionLoader;
import org.apache.cxf.wsdl.WSDLManager;
import org.apache.cxf.wsdl.http.AddressType;

@NoJSR250Annotations
public final class HTTPWSDLExtensionLoader
implements WSDLExtensionLoader {
    public HTTPWSDLExtensionLoader(Bus b) {
        WSDLManager manager = b.getExtension(WSDLManager.class);
        this.createExtensor(manager, Port.class, HTTPClientPolicy.class);
        this.createExtensor(manager, Port.class, HTTPServerPolicy.class);
        this.createExtensor(manager, Port.class, AddressType.class);
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

