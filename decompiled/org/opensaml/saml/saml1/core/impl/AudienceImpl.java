/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.Audience;

public class AudienceImpl
extends AbstractSAMLObject
implements Audience {
    private String uri;

    protected AudienceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getUri() {
        return this.uri;
    }

    @Override
    public void setUri(String s2) {
        this.uri = this.prepareForAssignment(this.uri, s2);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

