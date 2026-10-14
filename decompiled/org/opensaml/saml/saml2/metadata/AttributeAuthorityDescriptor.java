/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml.saml2.metadata.AttributeProfile;
import org.opensaml.saml.saml2.metadata.AttributeService;
import org.opensaml.saml.saml2.metadata.NameIDFormat;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;

public interface AttributeAuthorityDescriptor
extends SAMLObject,
RoleDescriptor {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeAuthorityDescriptor";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeAuthorityDescriptor", "md");
    public static final String TYPE_LOCAL_NAME = "AttributeAuthorityDescriptorType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeAuthorityDescriptorType", "md");

    public List<AttributeService> getAttributeServices();

    public List<AssertionIDRequestService> getAssertionIDRequestServices();

    public List<NameIDFormat> getNameIDFormats();

    public List<AttributeProfile> getAttributeProfiles();

    public List<Attribute> getAttributes();
}

