/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface StatusCode
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "StatusCode";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "StatusCode", "saml1p");
    public static final String TYPE_LOCAL_NAME = "StatusCodeType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "StatusCodeType", "saml1p");
    public static final QName SUCCESS = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "Success", "saml1p");
    public static final QName VERSION_MISMATCH = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "VersionMismatch", "saml1p");
    public static final QName REQUESTER = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "Requester", "saml1p");
    public static final QName RESPONDER = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "Responder", "saml1p");
    public static final QName REQUEST_VERSION_TOO_HIGH = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "RequestVersionTooHigh", "saml1p");
    public static final QName REQUEST_VERSION_TOO_LOW = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "RequestVersionTooLow", "saml1p");
    public static final QName REQUEST_VERSION_DEPRECATED = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "RequestVersionDeprecated", "saml1p");
    public static final QName TOO_MANY_RESPONSES = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "TooManyResponses", "saml1p");
    public static final QName REQUEST_DENIED = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "RequestDenied", "saml1p");
    public static final QName RESOURCE_NOT_RECOGNIZED = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "ResourceNotRecognized", "saml1p");
    public static final String VALUE_ATTRIB_NAME = "Value";

    public QName getValue();

    public void setValue(QName var1);

    public StatusCode getStatusCode();

    public void setStatusCode(StatusCode var1);
}

