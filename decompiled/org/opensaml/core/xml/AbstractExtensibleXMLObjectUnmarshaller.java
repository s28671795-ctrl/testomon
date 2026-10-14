/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml;

import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.AbstractElementExtensibleXMLObjectUnmarshaller;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.w3c.dom.Attr;

public abstract class AbstractExtensibleXMLObjectUnmarshaller
extends AbstractElementExtensibleXMLObjectUnmarshaller {
    @Override
    protected void processAttribute(@Nonnull XMLObject xmlObject, @Nonnull Attr attribute) throws UnmarshallingException {
        AttributeExtensibleXMLObject anyAttribute = (AttributeExtensibleXMLObject)xmlObject;
        QName attribQName = QNameSupport.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
        if (attribute.isId()) {
            anyAttribute.getUnknownAttributes().registerID(attribQName);
        }
        anyAttribute.getUnknownAttributes().put(attribQName, attribute.getValue());
    }
}

