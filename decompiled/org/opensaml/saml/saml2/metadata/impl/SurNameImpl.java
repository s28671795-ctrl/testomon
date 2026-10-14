/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.SurName;

public class SurNameImpl
extends AbstractSAMLObject
implements SurName {
    private String name;

    protected SurNameImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String newName) {
        this.name = this.prepareForAssignment(this.name, newName);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

