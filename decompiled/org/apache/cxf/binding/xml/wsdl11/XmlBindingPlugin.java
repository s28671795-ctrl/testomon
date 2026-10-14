/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.cxf.tools.common.ToolConstants
 */
package org.apache.cxf.binding.xml.wsdl11;

import java.util.Map;
import javax.wsdl.Binding;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import org.apache.cxf.tools.common.ToolConstants;
import org.apache.cxf.wsdl.AbstractWSDLPlugin;

public final class XmlBindingPlugin
extends AbstractWSDLPlugin {
    @Override
    public ExtensibilityElement createExtension(Map<String, Object> args2) throws WSDLException {
        return this.registry.createExtension(Binding.class, ToolConstants.XML_BINDING_FORMAT);
    }
}

