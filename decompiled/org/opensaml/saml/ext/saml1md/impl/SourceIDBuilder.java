/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml1md.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml1md.SourceID;
import org.opensaml.saml.ext.saml1md.impl.SourceIDImpl;

public class SourceIDBuilder
extends AbstractSAMLObjectBuilder<SourceID> {
    @Override
    public SourceID buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:profiles:v1metadata", "SourceID", "saml1md");
    }

    @Override
    public SourceID buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SourceIDImpl(namespaceURI, localName, namespacePrefix);
    }
}

