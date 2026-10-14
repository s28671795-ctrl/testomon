/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.Audience;

public class AudienceImpl
extends AbstractSAMLObject
implements Audience {
    private String audienceURI;

    protected AudienceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getAudienceURI() {
        return this.audienceURI;
    }

    @Override
    public void setAudienceURI(String newAudienceURI) {
        this.audienceURI = this.prepareForAssignment(this.audienceURI, newAudienceURI);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

