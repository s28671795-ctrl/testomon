/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.cxf.tools.common.ToolConstants
 */
package org.apache.cxf.binding.xml.wsdl11;

import java.util.Map;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.xml.namespace.QName;
import org.apache.cxf.bindings.xformat.XMLBindingMessageFormat;
import org.apache.cxf.tools.common.ToolConstants;
import org.apache.cxf.wsdl.AbstractWSDLPlugin;
import org.apache.cxf.wsdl.JAXBExtensibilityElement;

public final class XmlIoPlugin
extends AbstractWSDLPlugin {
    @Override
    public ExtensibilityElement createExtension(Map<String, Object> args2) throws WSDLException {
        XMLBindingMessageFormat xmlFormat = null;
        Class clz = this.getOption(args2, Class.class);
        QName qname = this.getOption(args2, QName.class);
        ExtensibilityElement ext = this.registry.createExtension(clz, ToolConstants.XML_FORMAT);
        xmlFormat = ext instanceof JAXBExtensibilityElement ? (XMLBindingMessageFormat)((JAXBExtensibilityElement)ext).getValue() : (XMLBindingMessageFormat)((Object)ext);
        xmlFormat.setRootNode(qname);
        return ext;
    }
}

