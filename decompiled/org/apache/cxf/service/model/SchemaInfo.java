/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.model;

import java.lang.ref.SoftReference;
import javax.xml.namespace.QName;
import org.apache.cxf.service.model.AbstractPropertiesHolder;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.apache.ws.commons.schema.XmlSchemaForm;
import org.apache.ws.commons.schema.XmlSchemaSerializer;
import org.apache.ws.commons.schema.utils.NamespaceMap;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class SchemaInfo
extends AbstractPropertiesHolder {
    private String namespaceUri;
    private boolean isElementQualified;
    private boolean isAttributeQualified;
    private XmlSchema schema;
    private String systemId;
    private SoftReference<Element> cachedElement;

    public SchemaInfo(String namespaceUri) {
        this(namespaceUri, false, false);
    }

    public SchemaInfo(String namespaceUri, boolean qElement, boolean qAttribute) {
        this.namespaceUri = namespaceUri;
        this.isElementQualified = qElement;
        this.isAttributeQualified = qAttribute;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(64).append(this.getClass().getName());
        buffer.append(" [namespaceURI: ");
        buffer.append(this.namespaceUri);
        buffer.append("] [systemId: ");
        buffer.append(this.systemId);
        buffer.append(']');
        return buffer.toString();
    }

    public String getNamespaceURI() {
        return this.namespaceUri;
    }

    public void setNamespaceURI(String nsUri) {
        this.namespaceUri = nsUri;
    }

    public synchronized void setElement(Element el) {
        this.cachedElement = new SoftReference<Element>(el);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized Element getElement() {
        XmlSchema sch;
        Element element;
        Element element2 = element = this.cachedElement == null ? null : this.cachedElement.get();
        if (element != null) {
            return element;
        }
        if (this.getSchema() == null) {
            throw new RuntimeException("No XmlSchema in SchemaInfo");
        }
        XmlSchema xmlSchema = sch = this.getSchema();
        synchronized (xmlSchema) {
            Document serializedSchema;
            XmlSchema schAgain = this.getSchema();
            if (schAgain.getNamespaceContext() == null) {
                NamespaceMap nsMap = new NamespaceMap();
                nsMap.add("xsd", "http://www.w3.org/2001/XMLSchema");
                nsMap.add("tns", schAgain.getTargetNamespace());
                schAgain.setNamespaceContext(nsMap);
            }
            try {
                serializedSchema = schAgain.getSchemaDocument();
            }
            catch (XmlSchemaSerializer.XmlSchemaSerializerException e) {
                throw new RuntimeException("Error serializing Xml Schema", e);
            }
            element = serializedSchema.getDocumentElement();
            this.cachedElement = new SoftReference<Element>(element);
        }
        if (element.getPrefix() == null && !"http://www.w3.org/2001/XMLSchema".equals(element.getAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns"))) {
            Attr attr = element.getOwnerDocument().createAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns");
            attr.setValue("http://www.w3.org/2001/XMLSchema");
            element.setAttributeNodeNS(attr);
        }
        return element;
    }

    public boolean isElementFormQualified() {
        return this.isElementQualified;
    }

    public boolean isAttributeFormQualified() {
        return this.isAttributeQualified;
    }

    public XmlSchema getSchema() {
        return this.schema;
    }

    public void setSchema(XmlSchema schema) {
        this.schema = schema;
        this.isElementQualified = schema.getElementFormDefault().equals((Object)XmlSchemaForm.QUALIFIED);
        this.isAttributeQualified = schema.getAttributeFormDefault().equals((Object)XmlSchemaForm.QUALIFIED);
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public XmlSchemaElement getElementByQName(QName qname) {
        String uri = qname.getNamespaceURI();
        if (this.schema != null && this.schema.getTargetNamespace() != null && this.schema.getTargetNamespace().equals(uri)) {
            return this.schema.getElementByName(qname);
        }
        return null;
    }

    String getNamespaceUri() {
        return this.namespaceUri;
    }

    boolean isElementQualified() {
        return this.isElementQualified;
    }

    boolean isAttributeQualified() {
        return this.isAttributeQualified;
    }
}

