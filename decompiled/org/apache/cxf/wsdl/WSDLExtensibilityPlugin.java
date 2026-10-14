/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl;

import java.util.Map;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.ExtensionRegistry;

public interface WSDLExtensibilityPlugin {
    public void setExtensionRegistry(ExtensionRegistry var1);

    public ExtensibilityElement createExtension(Map<String, Object> var1) throws WSDLException;
}

