/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.ConfirmationMethod;

public class ConfirmationMethodImpl
extends AbstractSAMLObject
implements ConfirmationMethod {
    private String confirmationMethod;

    protected ConfirmationMethodImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getConfirmationMethod() {
        return this.confirmationMethod;
    }

    @Override
    public void setConfirmationMethod(String method2) {
        this.confirmationMethod = this.prepareForAssignment(this.confirmationMethod, method2);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

