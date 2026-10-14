/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.samlec.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.samlec.EncType;
import org.opensaml.saml.ext.samlec.impl.EncTypeImpl;

public class EncTypeBuilder
extends AbstractSAMLObjectBuilder<EncType> {
    @Override
    public EncType buildObject() {
        return this.buildObject("urn:ietf:params:xml:ns:samlec", "EncType", "samlec");
    }

    @Override
    public EncType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new EncTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

