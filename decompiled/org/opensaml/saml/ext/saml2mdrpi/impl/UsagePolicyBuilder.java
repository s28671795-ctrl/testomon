/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdrpi.UsagePolicy;
import org.opensaml.saml.ext.saml2mdrpi.impl.UsagePolicyImpl;

public class UsagePolicyBuilder
extends AbstractSAMLObjectBuilder<UsagePolicy> {
    @Override
    public UsagePolicy buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:rpi", "UsagePolicy", "mdrpi");
    }

    @Override
    public UsagePolicy buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new UsagePolicyImpl(namespaceURI, localName, namespacePrefix);
    }
}

