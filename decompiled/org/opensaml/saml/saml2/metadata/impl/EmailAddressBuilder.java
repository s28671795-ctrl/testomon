/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.EmailAddress;
import org.opensaml.saml.saml2.metadata.impl.EmailAddressImpl;

public class EmailAddressBuilder
extends AbstractSAMLObjectBuilder<EmailAddress> {
    @Override
    public EmailAddress buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "EmailAddress", "md");
    }

    @Override
    public EmailAddress buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new EmailAddressImpl(namespaceURI, localName, namespacePrefix);
    }
}

