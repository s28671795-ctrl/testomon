/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml.saml2.metadata.AttributeProfile;
import org.opensaml.saml.saml2.metadata.NameIDMappingService;
import org.opensaml.saml.saml2.metadata.SSODescriptor;
import org.opensaml.saml.saml2.metadata.SingleSignOnService;

public interface IDPSSODescriptor
extends SSODescriptor {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "IDPSSODescriptor";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "IDPSSODescriptor", "md");
    public static final String TYPE_LOCAL_NAME = "IDPSSODescriptorType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "IDPSSODescriptorType", "md");
    public static final String WANT_AUTHN_REQ_SIGNED_ATTRIB_NAME = "WantAuthnRequestsSigned";

    public Boolean getWantAuthnRequestsSigned();

    public XSBooleanValue getWantAuthnRequestsSignedXSBoolean();

    public void setWantAuthnRequestsSigned(Boolean var1);

    public void setWantAuthnRequestsSigned(XSBooleanValue var1);

    public List<SingleSignOnService> getSingleSignOnServices();

    public List<NameIDMappingService> getNameIDMappingServices();

    public List<AssertionIDRequestService> getAssertionIDRequestServices();

    public List<AttributeProfile> getAttributeProfiles();

    public List<Attribute> getAttributes();
}

