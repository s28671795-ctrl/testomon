/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.Company;

public class CompanyImpl
extends AbstractSAMLObject
implements Company {
    private String companyName;

    protected CompanyImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getName() {
        return this.companyName;
    }

    @Override
    public void setName(String newName) {
        this.companyName = this.prepareForAssignment(this.companyName, newName);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

