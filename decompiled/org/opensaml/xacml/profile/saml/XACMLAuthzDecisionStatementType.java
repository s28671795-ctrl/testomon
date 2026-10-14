/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.Statement;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.ctx.ResponseType;

public interface XACMLAuthzDecisionStatementType
extends Statement,
XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "XACMLAuthzDecisionStatement";
    public static final QName DEFAULT_ELEMENT_NAME_XACML10 = new QName("urn:oasis:names:tc:xacml:1.0:profile:saml2.0:v2:schema:assertion", "XACMLAuthzDecisionStatement", "xacml-saml");
    public static final QName DEFAULT_ELEMENT_NAME_XACML11 = new QName("urn:oasis:names:tc:xacml:1.1:profile:saml2.0:v2:schema:assertion", "XACMLAuthzDecisionStatement", "xacml-saml");
    public static final QName DEFAULT_ELEMENT_NAME_XACML20 = new QName("urn:oasis:names:tc:xacml:2.0:profile:saml2.0:v2:schema:assertion", "XACMLAuthzDecisionStatement", "xacml-saml");
    public static final QName DEFAULT_ELEMENT_NAME_XACML30 = new QName("urn:oasis:names:tc:xacml:3.0:profile:saml2.0:v2:schema:assertion", "XACMLAuthzDecisionStatement", "xacml-saml");
    public static final String TYPE_LOCAL_NAME = "XACMLAuthzDecisionStatementType";
    public static final QName TYPE_NAME_XACML10 = new QName("urn:oasis:names:tc:xacml:1.0:profile:saml2.0:v2:schema:assertion", "XACMLAuthzDecisionStatementType", "xacml-saml");
    public static final QName TYPE_NAME_XACML11 = new QName("urn:oasis:names:tc:xacml:1.1:profile:saml2.0:v2:schema:assertion", "XACMLAuthzDecisionStatementType", "xacml-saml");
    public static final QName TYPE_NAME_XACML20 = new QName("urn:oasis:names:tc:xacml:2.0:profile:saml2.0:v2:schema:assertion", "XACMLAuthzDecisionStatementType", "xacml-saml");
    public static final QName TYPE_NAME_XACML30 = new QName("urn:oasis:names:tc:xacml:3.0:profile:saml2.0:v2:schema:assertion", "XACMLAuthzDecisionStatementType", "xacml-saml");

    public RequestType getRequest();

    public ResponseType getResponse();

    public void setRequest(RequestType var1);

    public void setResponse(ResponseType var1);
}

