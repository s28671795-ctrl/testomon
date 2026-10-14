/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.samlec.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.samlec.GeneratedKey;
import org.opensaml.saml.ext.samlec.impl.GeneratedKeyImpl;

public class GeneratedKeyBuilder
extends AbstractSAMLObjectBuilder<GeneratedKey> {
    @Override
    public GeneratedKey buildObject() {
        return this.buildObject("urn:ietf:params:xml:ns:samlec", "GeneratedKey", "samlec");
    }

    @Override
    public GeneratedKey buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new GeneratedKeyImpl(namespaceURI, localName, namespacePrefix);
    }
}

