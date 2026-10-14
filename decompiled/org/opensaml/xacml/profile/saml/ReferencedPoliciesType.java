/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.policy.PolicySetType;
import org.opensaml.xacml.policy.PolicyType;

public interface ReferencedPoliciesType
extends SAMLObject,
XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "ReferencedPolicies";
    public static final QName DEFAULT_ELEMENT_NAME_XACML10 = new QName("urn:oasis:names:tc:xacml:1.0:profile:saml2.0:v2:schema:assertion", "ReferencedPolicies", "xacml-saml");
    public static final QName DEFAULT_ELEMENT_NAME_XACML11 = new QName("urn:oasis:names:tc:xacml:1.1:profile:saml2.0:v2:schema:assertion", "ReferencedPolicies", "xacml-saml");
    public static final QName DEFAULT_ELEMENT_NAME_XACML20 = new QName("urn:oasis:names:tc:xacml:2.0:profile:saml2.0:v2:schema:assertion", "ReferencedPolicies", "xacml-saml");
    public static final QName DEFAULT_ELEMENT_NAME_XACML30 = new QName("urn:oasis:names:tc:xacml:3.0:profile:saml2.0:v2:schema:assertion", "ReferencedPolicies", "xacml-saml");
    public static final String TYPE_LOCAL_NAME = "ReferencedPoliciesType";
    public static final QName TYPE_NAME_XACML10 = new QName("urn:oasis:names:tc:xacml:1.0:profile:saml2.0:v2:schema:assertion", "ReferencedPoliciesType", "xacml-saml");
    public static final QName TYPE_NAME_XACML11 = new QName("urn:oasis:names:tc:xacml:1.1:profile:saml2.0:v2:schema:assertion", "ReferencedPoliciesType", "xacml-saml");
    public static final QName TYPE_NAME_XACML20 = new QName("urn:oasis:names:tc:xacml:2.0:profile:saml2.0:v2:schema:assertion", "ReferencedPoliciesType", "xacml-saml");
    public static final QName TYPE_NAME_XACML30 = new QName("urn:oasis:names:tc:xacml:3.0:profile:saml2.0:v2:schema:assertion", "ReferencedPoliciesType", "xacml-saml");

    public List<PolicySetType> getPolicySets();

    public List<PolicyType> getPolicies();
}

