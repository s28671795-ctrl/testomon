/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.xmlsec.signature.SignableXMLObject;
import org.opensaml.xmlsec.signature.Signature;
import org.w3c.dom.Element;

public abstract class AbstractSignableXMLObject
extends AbstractXMLObject
implements SignableXMLObject {
    private Signature signature;

    protected AbstractSignableXMLObject(@Nullable String namespaceURI, @Nonnull String elementLocalName, @Nullable String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public boolean isSigned() {
        Element child = ElementSupport.getFirstChildElement(this.getDOM());
        while (child != null && !ElementSupport.isElementNamed(child, "http://www.w3.org/2000/09/xmldsig#", "Signature")) {
            child = ElementSupport.getNextSiblingElement(child);
        }
        return child != null;
    }

    @Override
    @Nullable
    public Signature getSignature() {
        return this.signature;
    }

    @Override
    public void setSignature(@Nullable Signature newSignature) {
        this.signature = this.prepareForAssignment(this.signature, newSignature);
    }
}

