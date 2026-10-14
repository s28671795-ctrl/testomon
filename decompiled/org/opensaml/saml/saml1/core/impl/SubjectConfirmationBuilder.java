/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.SubjectConfirmation;
import org.opensaml.saml.saml1.core.impl.SubjectConfirmationImpl;

public class SubjectConfirmationBuilder
extends AbstractSAMLObjectBuilder<SubjectConfirmation> {
    @Override
    public SubjectConfirmation buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation", "saml1");
    }

    @Override
    public SubjectConfirmation buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SubjectConfirmationImpl(namespaceURI, localName, namespacePrefix);
    }
}

