/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.xml.wsdl11;

import java.util.Map;
import javax.wsdl.Port;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.http.HTTPAddress;
import org.apache.cxf.wsdl.AbstractWSDLPlugin;
import org.apache.cxf.wsdl.WSDLConstants;
import org.apache.cxf.wsdl.http.AddressType;

public final class HttpAddressPlugin
extends AbstractWSDLPlugin {
    @Override
    public ExtensibilityElement createExtension(Map<String, Object> args2) throws WSDLException {
        String address = this.getOption(args2, "address");
        ExtensibilityElement addr = this.registry.createExtension(Port.class, WSDLConstants.QNAME_XMLHTTP_BINDING_ADDRESS);
        if (addr instanceof AddressType) {
            ((AddressType)((Object)addr)).setLocation(address);
        }
        if (addr instanceof HTTPAddress) {
            ((HTTPAddress)addr).setLocationURI(address);
        }
        return addr;
    }
}

