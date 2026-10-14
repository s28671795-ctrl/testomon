/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.PrivacyStatementURL;
import org.opensaml.saml.ext.saml2mdui.impl.PrivacyStatementURLImpl;

public class PrivacyStatementURLBuilder
extends AbstractSAMLObjectBuilder<PrivacyStatementURL> {
    @Override
    public PrivacyStatementURL buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "PrivacyStatementURL", "mdui");
    }

    @Override
    public PrivacyStatementURL buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new PrivacyStatementURLImpl(namespaceURI, localName, namespacePrefix);
    }
}

