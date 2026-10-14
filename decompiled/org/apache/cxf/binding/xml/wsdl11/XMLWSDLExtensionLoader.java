/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.xml.wsdl11;

import javax.wsdl.Binding;
import javax.wsdl.BindingInput;
import javax.wsdl.BindingOutput;
import javax.xml.bind.JAXBException;
import org.apache.cxf.Bus;
import org.apache.cxf.bindings.xformat.XMLBindingMessageFormat;
import org.apache.cxf.bindings.xformat.XMLFormatBinding;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.wsdl.JAXBExtensionHelper;
import org.apache.cxf.wsdl.WSDLExtensionLoader;
import org.apache.cxf.wsdl.WSDLManager;

@NoJSR250Annotations
public final class XMLWSDLExtensionLoader
implements WSDLExtensionLoader {
    public XMLWSDLExtensionLoader(Bus b) {
        this.setupBus(b);
    }

    public void setupBus(Bus b) {
        WSDLManager manager = b.getExtension(WSDLManager.class);
        XMLWSDLExtensionLoader.registerExtensors(manager);
    }

    public static void registerExtensors(WSDLManager manager) {
        XMLWSDLExtensionLoader.createExtensor(manager, BindingInput.class, XMLBindingMessageFormat.class);
        XMLWSDLExtensionLoader.createExtensor(manager, BindingOutput.class, XMLBindingMessageFormat.class);
        XMLWSDLExtensionLoader.createExtensor(manager, Binding.class, XMLFormatBinding.class);
    }

    public static void createExtensor(WSDLManager manager, Class<?> parentType, Class<?> elementType) {
        try {
            JAXBExtensionHelper.addExtensions(manager.getExtensionRegistry(), parentType, elementType, null, XMLWSDLExtensionLoader.class.getClassLoader());
        }
        catch (JAXBException jAXBException) {
            // empty catch block
        }
    }
}

