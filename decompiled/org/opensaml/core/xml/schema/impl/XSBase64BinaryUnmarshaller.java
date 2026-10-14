/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.schema.impl;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBase64Binary;
import org.w3c.dom.Attr;

public class XSBase64BinaryUnmarshaller
extends AbstractXMLObjectUnmarshaller {
    @Override
    protected void processChildElement(@Nonnull XMLObject parentXMLObject, @Nonnull XMLObject childXMLObject) throws UnmarshallingException {
    }

    @Override
    protected void processAttribute(@Nonnull XMLObject xmlObject, @Nonnull Attr attribute) throws UnmarshallingException {
    }

    @Override
    protected void processElementContent(@Nonnull XMLObject xmlObject, @Nonnull String elementContent) {
        XSBase64Binary xsBase64Binary = (XSBase64Binary)xmlObject;
        xsBase64Binary.setValue(StringSupport.trimOrNull(elementContent));
    }
}

