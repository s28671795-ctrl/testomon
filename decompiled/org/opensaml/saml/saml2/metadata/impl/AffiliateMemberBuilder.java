/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AffiliateMember;
import org.opensaml.saml.saml2.metadata.impl.AffiliateMemberImpl;

public class AffiliateMemberBuilder
extends AbstractSAMLObjectBuilder<AffiliateMember> {
    @Override
    public AffiliateMember buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AffiliateMember", "md");
    }

    @Override
    public AffiliateMember buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AffiliateMemberImpl(namespaceURI, localName, namespacePrefix);
    }
}

