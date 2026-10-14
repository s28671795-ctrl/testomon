/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.ecp.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.ecp.SubjectConfirmation;
import org.opensaml.saml.saml2.ecp.impl.SubjectConfirmationImpl;

public class SubjectConfirmationBuilder
extends AbstractSAMLObjectBuilder<SubjectConfirmation> {
    @Override
    public SubjectConfirmation buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "SubjectConfirmation", "ecp");
    }

    @Override
    public SubjectConfirmation buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SubjectConfirmationImpl(namespaceURI, localName, namespacePrefix);
    }
}

