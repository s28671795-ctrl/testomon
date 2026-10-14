/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.wsdl11;

import java.util.Map;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import org.apache.cxf.binding.soap.SOAPBindingUtil;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapAddress;
import org.apache.cxf.wsdl.AbstractWSDLPlugin;

public final class SoapAddressPlugin
extends AbstractWSDLPlugin {
    public static final String CFG_ADDRESS = "address";
    public static final String CFG_SOAP12 = "soap12";

    @Override
    public ExtensibilityElement createExtension(Map<String, Object> args2) throws WSDLException {
        return this.createExtension(this.optionSet(args2, CFG_SOAP12), this.getOption(args2, CFG_ADDRESS));
    }

    public ExtensibilityElement createExtension(boolean isSOAP12, String address) throws WSDLException {
        SoapAddress soapAddress = null;
        soapAddress = SOAPBindingUtil.createSoapAddress(this.registry, isSOAP12);
        soapAddress.setLocationURI(address);
        return soapAddress;
    }
}

