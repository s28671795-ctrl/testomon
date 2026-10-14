/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.schema.impl;

import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSAny;
import org.w3c.dom.Attr;

public class XSAnyUnmarshaller
extends AbstractXMLObjectUnmarshaller {
    @Override
    protected void processChildElement(@Nonnull XMLObject parentXMLObject, @Nonnull XMLObject childXMLObject) throws UnmarshallingException {
        XSAny xsAny = (XSAny)parentXMLObject;
        xsAny.getUnknownXMLObjects().add(childXMLObject);
    }

    @Override
    protected void processAttribute(@Nonnull XMLObject xmlObject, @Nonnull Attr attribute) throws UnmarshallingException {
        XSAny xsAny = (XSAny)xmlObject;
        QName attribQName = QNameSupport.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
        if (attribute.isId()) {
            xsAny.getUnknownAttributes().registerID(attribQName);
        }
        xsAny.getUnknownAttributes().put(attribQName, attribute.getValue());
    }

    @Override
    protected void processElementContent(@Nonnull XMLObject xmlObject, @Nonnull String elementContent) {
        XSAny xsAny = (XSAny)xmlObject;
        xsAny.setTextContent(elementContent);
    }
}

