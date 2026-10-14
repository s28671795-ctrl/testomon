/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.Subject;
import org.opensaml.saml.saml1.core.impl.SubjectImpl;

public class SubjectBuilder
extends AbstractSAMLObjectBuilder<Subject> {
    @Override
    public Subject buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "Subject", "saml1");
    }

    @Override
    public Subject buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SubjectImpl(namespaceURI, localName, namespacePrefix);
    }
}

