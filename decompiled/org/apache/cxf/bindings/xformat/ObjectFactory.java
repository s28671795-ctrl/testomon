/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bindings.xformat;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.apache.cxf.bindings.xformat.XMLBindingMessageFormat;
import org.apache.cxf.bindings.xformat.XMLFormatBinding;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Body_QNAME = new QName("http://cxf.apache.org/bindings/xformat", "body");
    private static final QName _Binding_QNAME = new QName("http://cxf.apache.org/bindings/xformat", "binding");

    public XMLBindingMessageFormat createXMLBindingMessageFormat() {
        return new XMLBindingMessageFormat();
    }

    public XMLFormatBinding createXMLFormatBinding() {
        return new XMLFormatBinding();
    }

    @XmlElementDecl(namespace="http://cxf.apache.org/bindings/xformat", name="body")
    public JAXBElement<XMLBindingMessageFormat> createBody(XMLBindingMessageFormat value) {
        return new JAXBElement<XMLBindingMessageFormat>(_Body_QNAME, XMLBindingMessageFormat.class, null, value);
    }

    @XmlElementDecl(namespace="http://cxf.apache.org/bindings/xformat", name="binding")
    public JAXBElement<XMLFormatBinding> createBinding(XMLFormatBinding value) {
        return new JAXBElement<XMLFormatBinding>(_Binding_QNAME, XMLFormatBinding.class, null, value);
    }
}

