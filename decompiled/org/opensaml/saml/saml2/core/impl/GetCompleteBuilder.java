/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.GetComplete;
import org.opensaml.saml.saml2.core.impl.GetCompleteImpl;

public class GetCompleteBuilder
extends AbstractSAMLObjectBuilder<GetComplete> {
    @Override
    public GetComplete buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "GetComplete", "saml2p");
    }

    @Override
    public GetComplete buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new GetCompleteImpl(namespaceURI, localName, namespacePrefix);
    }
}

