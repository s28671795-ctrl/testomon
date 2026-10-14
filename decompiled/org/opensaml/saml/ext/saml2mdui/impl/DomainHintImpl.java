/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdui.DomainHint;

public class DomainHintImpl
extends AbstractSAMLObject
implements DomainHint {
    private String hint;

    protected DomainHintImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getHint() {
        return this.hint;
    }

    @Override
    public void setHint(String newHint) {
        this.hint = this.prepareForAssignment(this.hint, newHint);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

