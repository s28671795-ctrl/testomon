/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.IDPList;
import org.opensaml.saml.saml2.core.impl.IDPListImpl;

public class IDPListBuilder
extends AbstractSAMLObjectBuilder<IDPList> {
    @Override
    public IDPList buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "IDPList", "saml2p");
    }

    @Override
    public IDPList buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new IDPListImpl(namespaceURI, localName, namespacePrefix);
    }
}

