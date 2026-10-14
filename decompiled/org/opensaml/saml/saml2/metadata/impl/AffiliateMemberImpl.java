/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.AffiliateMember;

public class AffiliateMemberImpl
extends AbstractSAMLObject
implements AffiliateMember {
    private String id;

    protected AffiliateMemberImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public void setID(String newID) {
        if (newID != null && newID.length() > 1024) {
            throw new IllegalArgumentException("Member ID can not exceed 1024 characters in length");
        }
        this.id = this.prepareForAssignment(this.id, newID);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

