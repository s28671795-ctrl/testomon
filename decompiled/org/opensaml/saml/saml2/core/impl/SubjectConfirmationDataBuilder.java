/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;
import org.opensaml.saml.saml2.core.impl.SubjectConfirmationDataImpl;

public class SubjectConfirmationDataBuilder
extends AbstractSAMLObjectBuilder<SubjectConfirmationData> {
    @Override
    public SubjectConfirmationData buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectConfirmationData", "saml2");
    }

    @Override
    public SubjectConfirmationData buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SubjectConfirmationDataImpl(namespaceURI, localName, namespacePrefix);
    }
}

