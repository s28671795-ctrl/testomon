/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.SubjectLocality;
import org.opensaml.saml.saml2.core.impl.SubjectLocalityImpl;

public class SubjectLocalityBuilder
extends AbstractSAMLObjectBuilder<SubjectLocality> {
    @Override
    public SubjectLocality buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectLocality", "saml2");
    }

    @Override
    public SubjectLocality buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SubjectLocalityImpl(namespaceURI, localName, namespacePrefix);
    }
}

