/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.SessionIndex;

public class SessionIndexImpl
extends AbstractSAMLObject
implements SessionIndex {
    private String sessionIndex;

    protected SessionIndexImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getSessionIndex() {
        return this.sessionIndex;
    }

    @Override
    public void setSessionIndex(String newSessionIndex) {
        this.sessionIndex = this.prepareForAssignment(this.sessionIndex, newSessionIndex);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

