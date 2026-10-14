/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.NewID;

public class NewIDImpl
extends AbstractSAMLObject
implements NewID {
    private String newID;

    protected NewIDImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getNewID() {
        return this.newID;
    }

    @Override
    public void setNewID(String newNewID) {
        this.newID = this.prepareForAssignment(this.newID, newNewID);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

