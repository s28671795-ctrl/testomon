/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl;

import java.util.Map;
import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.factory.WSDLFactory;
import org.apache.cxf.service.model.ServiceSchemaInfo;
import org.w3c.dom.Element;

public interface WSDLManager {
    public ExtensionRegistry getExtensionRegistry();

    public WSDLFactory getWSDLFactory();

    public Definition getDefinition(String var1) throws WSDLException;

    public Definition getDefinition(Element var1) throws WSDLException;

    public void addDefinition(Object var1, Definition var2);

    public Map<Object, Definition> getDefinitions();

    public ServiceSchemaInfo getSchemasForDefinition(Definition var1);

    public void putSchemasForDefinition(Definition var1, ServiceSchemaInfo var2);

    public void removeDefinition(Definition var1);
}

