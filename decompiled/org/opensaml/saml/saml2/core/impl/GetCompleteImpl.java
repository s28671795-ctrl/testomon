/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.GetComplete;

public class GetCompleteImpl
extends AbstractSAMLObject
implements GetComplete {
    private String getComplete;

    protected GetCompleteImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getGetComplete() {
        return this.getComplete;
    }

    @Override
    public void setGetComplete(String newGetComplete) {
        this.getComplete = this.prepareForAssignment(this.getComplete, newGetComplete);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

