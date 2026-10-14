/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl11;

import java.io.PrintWriter;
import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.extensions.ExtensionSerializer;
import javax.wsdl.extensions.schema.Schema;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import org.apache.cxf.staxutils.PrettyPrintXMLStreamWriter;
import org.apache.cxf.staxutils.StaxUtils;
import org.w3c.dom.Node;

public class SchemaSerializer
implements ExtensionSerializer {
    @Override
    public void marshall(Class parentType, QName elementType, ExtensibilityElement extension, PrintWriter pw, Definition def, ExtensionRegistry extReg) throws WSDLException {
        try {
            this.writeXml(((Schema)extension).getElement(), pw);
        }
        catch (XMLStreamException e) {
            throw new WSDLException("", "Could not write schema.", e);
        }
    }

    private void writeXml(Node n, PrintWriter pw) throws XMLStreamException {
        XMLStreamWriter writer = StaxUtils.createXMLStreamWriter(pw);
        writer = new PrettyPrintXMLStreamWriter(writer, 2);
        StaxUtils.copy((Source)new DOMSource(n), writer);
        writer.close();
    }
}

