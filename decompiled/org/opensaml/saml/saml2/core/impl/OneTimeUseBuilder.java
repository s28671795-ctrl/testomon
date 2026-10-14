/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.OneTimeUse;
import org.opensaml.saml.saml2.core.impl.OneTimeUseImpl;

public class OneTimeUseBuilder
extends AbstractSAMLObjectBuilder<OneTimeUse> {
    @Override
    public OneTimeUse buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "OneTimeUse", "saml2");
    }

    @Override
    public OneTimeUse buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new OneTimeUseImpl(namespaceURI, localName, namespacePrefix);
    }
}

