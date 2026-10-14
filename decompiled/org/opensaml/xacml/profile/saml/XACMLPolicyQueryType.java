/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.policy.IdReferenceType;

public interface XACMLPolicyQueryType
extends RequestAbstractType,
XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "XACMLPolicyQuery";
    public static final QName DEFAULT_ELEMENT_NAME_XACML10 = new QName("urn:oasis:names:tc:xacml:1.0:profile:saml2.0:v2:schema:protocol", "XACMLPolicyQuery", "xacml-samlp");
    public static final QName DEFAULT_ELEMENT_NAME_XACML11 = new QName("urn:oasis:names:tc:xacml:1.1:profile:saml2.0:v2:schema:protocol", "XACMLPolicyQuery", "xacml-samlp");
    public static final QName DEFAULT_ELEMENT_NAME_XACML20 = new QName("urn:oasis:names:tc:xacml:2.0:profile:saml2.0:v2:schema:protocol", "XACMLPolicyQuery", "xacml-samlp");
    public static final QName DEFAULT_ELEMENT_NAME_XACML30 = new QName("urn:oasis:names:tc:xacml:3.0:profile:saml2.0:v2:schema:protocol", "XACMLPolicyQuery", "xacml-samlp");
    public static final String TYPE_LOCAL_NAME = "XACMLPolicyQueryType";
    public static final QName TYPE_NAME_XACML10 = new QName("urn:oasis:names:tc:xacml:1.0:profile:saml2.0:v2:schema:protocol", "XACMLPolicyQueryType", "xacml-samlp");
    public static final QName TYPE_NAME_XACML11 = new QName("urn:oasis:names:tc:xacml:1.1:profile:saml2.0:v2:schema:protocol", "XACMLPolicyQueryType", "xacml-samlp");
    public static final QName TYPE_NAME_XACML20 = new QName("urn:oasis:names:tc:xacml:2.0:profile:saml2.0:v2:schema:protocol", "XACMLPolicyQueryType", "xacml-samlp");
    public static final QName TYPE_NAME_XACML30 = new QName("urn:oasis:names:tc:xacml:3.0:profile:saml2.0:v2:schema:protocol", "XACMLPolicyQueryType", "xacml-samlp");

    public List<RequestType> getRequests();

    public List<IdReferenceType> getPolicySetIdReferences();

    public List<IdReferenceType> getPolicyIdReferences();
}

