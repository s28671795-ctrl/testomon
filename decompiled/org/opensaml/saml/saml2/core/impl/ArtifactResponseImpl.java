/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.ArtifactResponse;
import org.opensaml.saml.saml2.core.impl.StatusResponseTypeImpl;

public class ArtifactResponseImpl
extends StatusResponseTypeImpl
implements ArtifactResponse {
    private SAMLObject protocolMessage;

    protected ArtifactResponseImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public SAMLObject getMessage() {
        return this.protocolMessage;
    }

    @Override
    public void setMessage(SAMLObject message) {
        this.protocolMessage = this.prepareForAssignment(this.protocolMessage, message);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(super.getOrderedChildren());
        children.add(this.protocolMessage);
        return Collections.unmodifiableList(children);
    }
}

