/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.xhtml;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.xhtml.XhtmlElementType;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.xhtml.XhtmlValueType;

public class Elements
extends JAXBElement<XhtmlElementType> {
    private static final long serialVersionUID = 1L;

    public static Elements el(String elementName) {
        return Elements.createElement(elementName);
    }

    public static Object val(String elementName, String value) {
        return Elements.createElement(elementName, value);
    }

    public Elements(QName name, Class<XhtmlElementType> clazz, XhtmlElementType element) {
        super(name, clazz, element);
    }

    public Elements add(Object ... childNodes) {
        if (childNodes != null) {
            for (Object childNode : childNodes) {
                ((XhtmlElementType)this.getValue()).getChildNodes().add(childNode);
            }
        }
        return this;
    }

    public Elements addChild(Object child) {
        ((XhtmlElementType)this.getValue()).getChildNodes().add(child);
        return this;
    }

    private static Elements createElement(String elementName) {
        try {
            XhtmlElementType element = new XhtmlElementType();
            Elements jaxbElement = new Elements(new QName("http://www.w3.org/1999/xhtml", elementName), XhtmlElementType.class, element);
            return jaxbElement;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static JAXBElement<XhtmlValueType> createElement(String elementName, String value) {
        try {
            XhtmlValueType element = new XhtmlValueType();
            element.value = value;
            JAXBElement<XhtmlValueType> jaxbElement = new JAXBElement<XhtmlValueType>(new QName("http://www.w3.org/1999/xhtml", elementName), XhtmlValueType.class, element);
            return jaxbElement;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

