/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.BaseID;

public abstract class BaseIDImpl
extends AbstractSAMLObject
implements BaseID {
    private String nameQualifier;
    private String spNameQualfier;

    protected BaseIDImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getNameQualifier() {
        return this.nameQualifier;
    }

    @Override
    public void setNameQualifier(String newNameQualifier) {
        this.nameQualifier = this.prepareForAssignment(this.nameQualifier, newNameQualifier);
    }

    @Override
    public String getSPNameQualifier() {
        return this.spNameQualfier;
    }

    @Override
    public void setSPNameQualifier(String newSPNameQualifier) {
        this.spNameQualfier = this.prepareForAssignment(this.spNameQualfier, newSPNameQualifier);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

