/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdquery.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdquery.AuthnQueryDescriptorType;
import org.opensaml.saml.ext.saml2mdquery.impl.AuthnQueryDescriptorTypeImpl;

public class AuthnQueryDescriptorTypeBuilder
extends AbstractSAMLObjectBuilder<AuthnQueryDescriptorType> {
    @Override
    public AuthnQueryDescriptorType buildObject() {
        return (AuthnQueryDescriptorType)this.buildObject("urn:oasis:names:tc:SAML:metadata:ext:query", "RoleDescriptor", "query", AuthnQueryDescriptorType.TYPE_NAME);
    }

    @Override
    public AuthnQueryDescriptorType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AuthnQueryDescriptorTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

