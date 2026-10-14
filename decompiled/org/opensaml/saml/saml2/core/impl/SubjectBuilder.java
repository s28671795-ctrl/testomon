/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.impl.SubjectImpl;

public class SubjectBuilder
extends AbstractSAMLObjectBuilder<Subject> {
    @Override
    public Subject buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Subject", "saml2");
    }

    @Override
    public Subject buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SubjectImpl(namespaceURI, localName, namespacePrefix);
    }
}

