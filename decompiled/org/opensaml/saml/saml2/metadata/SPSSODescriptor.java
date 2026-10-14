/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml.saml2.metadata.SSODescriptor;

public interface SPSSODescriptor
extends SSODescriptor {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "SPSSODescriptor";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "SPSSODescriptor", "md");
    public static final String TYPE_LOCAL_NAME = "SPSSODescriptorType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "SPSSODescriptorType", "md");
    public static final String AUTH_REQUESTS_SIGNED_ATTRIB_NAME = "AuthnRequestsSigned";
    public static final String WANT_ASSERTIONS_SIGNED_ATTRIB_NAME = "WantAssertionsSigned";

    public Boolean isAuthnRequestsSigned();

    public XSBooleanValue isAuthnRequestsSignedXSBoolean();

    public void setAuthnRequestsSigned(Boolean var1);

    public void setAuthnRequestsSigned(XSBooleanValue var1);

    public Boolean getWantAssertionsSigned();

    public XSBooleanValue getWantAssertionsSignedXSBoolean();

    public void setWantAssertionsSigned(Boolean var1);

    public void setWantAssertionsSigned(XSBooleanValue var1);

    public List<AssertionConsumerService> getAssertionConsumerServices();

    public AssertionConsumerService getDefaultAssertionConsumerService();

    public List<AttributeConsumingService> getAttributeConsumingServices();

    public AttributeConsumingService getDefaultAttributeConsumingService();
}

