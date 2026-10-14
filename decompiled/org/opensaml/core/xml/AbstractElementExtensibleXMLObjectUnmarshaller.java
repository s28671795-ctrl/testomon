/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.w3c.dom.Attr;

public abstract class AbstractElementExtensibleXMLObjectUnmarshaller
extends AbstractXMLObjectUnmarshaller {
    @Override
    protected void processChildElement(@Nonnull XMLObject parentXMLObject, @Nonnull XMLObject childXMLObject) throws UnmarshallingException {
        ElementExtensibleXMLObject any = (ElementExtensibleXMLObject)parentXMLObject;
        any.getUnknownXMLObjects().add(childXMLObject);
    }

    @Override
    protected void processAttribute(@Nonnull XMLObject xmlObject, @Nonnull Attr attribute) throws UnmarshallingException {
    }

    @Override
    protected void processElementContent(@Nonnull XMLObject xmlObject, @Nonnull String elementContent) {
    }
}

