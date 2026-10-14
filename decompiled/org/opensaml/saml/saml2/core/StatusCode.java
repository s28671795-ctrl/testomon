/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface StatusCode
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "StatusCode";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "StatusCode", "saml2p");
    public static final String TYPE_LOCAL_NAME = "StatusCodeType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "StatusCodeType", "saml2p");
    public static final String VALUE_ATTRIB_NAME = "Value";
    public static final String SUCCESS = "urn:oasis:names:tc:SAML:2.0:status:Success";
    public static final String REQUESTER = "urn:oasis:names:tc:SAML:2.0:status:Requester";
    public static final String RESPONDER = "urn:oasis:names:tc:SAML:2.0:status:Responder";
    public static final String VERSION_MISMATCH = "urn:oasis:names:tc:SAML:2.0:status:VersionMismatch";
    public static final String AUTHN_FAILED = "urn:oasis:names:tc:SAML:2.0:status:AuthnFailed";
    public static final String INVALID_ATTR_NAME_OR_VALUE = "urn:oasis:names:tc:SAML:2.0:status:InvalidAttrNameOrValue";
    public static final String INVALID_NAMEID_POLICY = "urn:oasis:names:tc:SAML:2.0:status:InvalidNameIDPolicy";
    public static final String NO_AUTHN_CONTEXT = "urn:oasis:names:tc:SAML:2.0:status:NoAuthnContext";
    public static final String NO_AVAILABLE_IDP = "urn:oasis:names:tc:SAML:2.0:status:NoAvailableIDP";
    public static final String NO_PASSIVE = "urn:oasis:names:tc:SAML:2.0:status:NoPassive";
    public static final String NO_SUPPORTED_IDP = "urn:oasis:names:tc:SAML:2.0:status:NoSupportedIDP";
    public static final String PARTIAL_LOGOUT = "urn:oasis:names:tc:SAML:2.0:status:PartialLogout";
    public static final String PROXY_COUNT_EXCEEDED = "urn:oasis:names:tc:SAML:2.0:status:ProxyCountExceeded";
    public static final String REQUEST_DENIED = "urn:oasis:names:tc:SAML:2.0:status:RequestDenied";
    public static final String REQUEST_UNSUPPORTED = "urn:oasis:names:tc:SAML:2.0:status:RequestUnsupported";
    public static final String REQUEST_VERSION_DEPRECATED = "urn:oasis:names:tc:SAML:2.0:status:RequestVersionDeprecated";
    public static final String REQUEST_VERSION_TOO_HIGH = "urn:oasis:names:tc:SAML:2.0:status:RequestVersionTooHigh";
    public static final String REQUEST_VERSION_TOO_LOW = "urn:oasis:names:tc:SAML:2.0:status:RequestVersionTooLow";
    public static final String RESOURCE_NOT_RECOGNIZED = "urn:oasis:names:tc:SAML:2.0:status:ResourceNotRecognized";
    public static final String TOO_MANY_RESPONSES = "urn:oasis:names:tc:SAML:2.0:status:TooManyResponses";
    public static final String UNKNOWN_ATTR_PROFILE = "urn:oasis:names:tc:SAML:2.0:status:UnknownAttrProfile";
    public static final String UNKNOWN_PRINCIPAL = "urn:oasis:names:tc:SAML:2.0:status:UnknownPrincipal";
    public static final String UNSUPPORTED_BINDING = "urn:oasis:names:tc:SAML:2.0:status:UnsupportedBinding";

    public StatusCode getStatusCode();

    public void setStatusCode(StatusCode var1);

    public String getValue();

    public void setValue(String var1);
}

