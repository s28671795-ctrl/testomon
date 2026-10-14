/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.AuthorityBinding;

public class AuthorityBindingImpl
extends AbstractSAMLObject
implements AuthorityBinding {
    private QName authorityKind;
    private String location;
    private String binding;

    protected AuthorityBindingImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public QName getAuthorityKind() {
        return this.authorityKind;
    }

    @Override
    public void setAuthorityKind(QName kind) {
        this.authorityKind = this.prepareAttributeValueForAssignment("AuthorityKind", this.authorityKind, kind);
    }

    @Override
    public String getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(String loc) {
        this.location = this.prepareForAssignment(this.location, loc);
    }

    @Override
    public String getBinding() {
        return this.binding;
    }

    @Override
    public void setBinding(String newBinding) {
        this.binding = this.prepareForAssignment(this.binding, newBinding);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

