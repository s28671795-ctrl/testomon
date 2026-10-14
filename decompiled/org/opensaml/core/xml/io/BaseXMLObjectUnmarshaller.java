/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.io;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.w3c.dom.Attr;

public abstract class BaseXMLObjectUnmarshaller
extends AbstractXMLObjectUnmarshaller {
    @Override
    protected void processAttribute(@Nonnull XMLObject xmlObject, @Nonnull Attr attribute) throws UnmarshallingException {
    }

    @Override
    protected void processChildElement(@Nonnull XMLObject parentXMLObject, @Nonnull XMLObject childXMLObject) throws UnmarshallingException {
    }

    @Override
    protected void processElementContent(@Nonnull XMLObject xmlObject, @Nonnull String elementContent) {
    }
}

