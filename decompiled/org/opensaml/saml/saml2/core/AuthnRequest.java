/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.NameIDPolicy;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml.saml2.core.Scoping;
import org.opensaml.saml.saml2.core.Subject;

public interface AuthnRequest
extends RequestAbstractType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AuthnRequest";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "AuthnRequest", "saml2p");
    public static final String TYPE_LOCAL_NAME = "AuthnRequestType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "AuthnRequestType", "saml2p");
    public static final String FORCE_AUTHN_ATTRIB_NAME = "ForceAuthn";
    public static final String IS_PASSIVE_ATTRIB_NAME = "IsPassive";
    public static final String PROTOCOL_BINDING_ATTRIB_NAME = "ProtocolBinding";
    public static final String ASSERTION_CONSUMER_SERVICE_INDEX_ATTRIB_NAME = "AssertionConsumerServiceIndex";
    public static final String ASSERTION_CONSUMER_SERVICE_URL_ATTRIB_NAME = "AssertionConsumerServiceURL";
    public static final String ATTRIBUTE_CONSUMING_SERVICE_INDEX_ATTRIB_NAME = "AttributeConsumingServiceIndex";
    public static final String PROVIDER_NAME_ATTRIB_NAME = "ProviderName";

    public Boolean isForceAuthn();

    public XSBooleanValue isForceAuthnXSBoolean();

    public void setForceAuthn(Boolean var1);

    public void setForceAuthn(XSBooleanValue var1);

    public Boolean isPassive();

    public XSBooleanValue isPassiveXSBoolean();

    public void setIsPassive(Boolean var1);

    public void setIsPassive(XSBooleanValue var1);

    public String getProtocolBinding();

    public void setProtocolBinding(String var1);

    public Integer getAssertionConsumerServiceIndex();

    public void setAssertionConsumerServiceIndex(Integer var1);

    public String getAssertionConsumerServiceURL();

    public void setAssertionConsumerServiceURL(String var1);

    public Integer getAttributeConsumingServiceIndex();

    public void setAttributeConsumingServiceIndex(Integer var1);

    public String getProviderName();

    public void setProviderName(String var1);

    public Subject getSubject();

    public void setSubject(Subject var1);

    public NameIDPolicy getNameIDPolicy();

    public void setNameIDPolicy(NameIDPolicy var1);

    public Conditions getConditions();

    public void setConditions(Conditions var1);

    public RequestedAuthnContext getRequestedAuthnContext();

    public void setRequestedAuthnContext(RequestedAuthnContext var1);

    public Scoping getScoping();

    public void setScoping(Scoping var1);
}

