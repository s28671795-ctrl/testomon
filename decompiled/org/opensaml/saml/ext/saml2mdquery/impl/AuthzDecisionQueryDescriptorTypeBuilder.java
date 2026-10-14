/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdquery.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdquery.AuthzDecisionQueryDescriptorType;
import org.opensaml.saml.ext.saml2mdquery.impl.AuthzDecisionQueryDescriptorTypeImpl;

public class AuthzDecisionQueryDescriptorTypeBuilder
extends AbstractSAMLObjectBuilder<AuthzDecisionQueryDescriptorType> {
    @Override
    public AuthzDecisionQueryDescriptorType buildObject() {
        return (AuthzDecisionQueryDescriptorType)this.buildObject("urn:oasis:names:tc:SAML:metadata:ext:query", "RoleDescriptor", "query", AuthzDecisionQueryDescriptorType.TYPE_NAME);
    }

    @Override
    public AuthzDecisionQueryDescriptorType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AuthzDecisionQueryDescriptorTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

