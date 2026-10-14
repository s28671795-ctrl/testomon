/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdquery.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdquery.ActionNamespace;

public class ActionNamespaceImpl
extends AbstractSAMLObject
implements ActionNamespace {
    private String value;

    protected ActionNamespaceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void setValue(String newValue) {
        this.value = this.prepareForAssignment(this.value, newValue);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

