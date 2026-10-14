/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl11;

import javax.wsdl.Binding;
import javax.wsdl.extensions.ExtensibilityElement;
import org.apache.cxf.Bus;
import org.apache.cxf.BusException;
import org.apache.cxf.binding.BindingFactory;
import org.apache.cxf.binding.BindingFactoryManager;

public final class WSDLServiceUtils {
    private WSDLServiceUtils() {
    }

    public static BindingFactory getBindingFactory(Binding binding, Bus bus, StringBuilder sb) {
        BindingFactory factory2 = null;
        for (Object obj : binding.getExtensibilityElements()) {
            if (!(obj instanceof ExtensibilityElement)) continue;
            ExtensibilityElement ext = (ExtensibilityElement)obj;
            sb.delete(0, sb.length());
            sb.append(ext.getElementType().getNamespaceURI());
            try {
                BindingFactoryManager manager = bus.getExtension(BindingFactoryManager.class);
                if (manager != null) {
                    factory2 = manager.getBindingFactory(sb.toString());
                }
            }
            catch (BusException busException) {
                // empty catch block
            }
            if (factory2 == null) continue;
            break;
        }
        return factory2;
    }
}

