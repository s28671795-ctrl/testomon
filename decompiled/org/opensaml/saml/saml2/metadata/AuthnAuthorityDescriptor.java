/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml.saml2.metadata.AuthnQueryService;
import org.opensaml.saml.saml2.metadata.NameIDFormat;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;

public interface AuthnAuthorityDescriptor
extends SAMLObject,
RoleDescriptor {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AuthnAuthorityDescriptor";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AuthnAuthorityDescriptor", "md");
    public static final String TYPE_LOCAL_NAME = "AuthnAuthorityDescriptorType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AuthnAuthorityDescriptorType", "md");

    public List<AuthnQueryService> getAuthnQueryServices();

    public List<AssertionIDRequestService> getAssertionIDRequestServices();

    public List<NameIDFormat> getNameIDFormats();
}

