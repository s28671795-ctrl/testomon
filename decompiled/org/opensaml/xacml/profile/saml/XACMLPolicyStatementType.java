/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.Statement;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.policy.PolicySetType;
import org.opensaml.xacml.policy.PolicyType;
import org.opensaml.xacml.profile.saml.ReferencedPoliciesType;

public interface XACMLPolicyStatementType
extends Statement,
XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "XACMLPolicyStatement";
    public static final QName DEFAULT_ELEMENT_NAME_XACML10 = new QName("urn:oasis:names:tc:xacml:1.0:profile:saml2.0:v2:schema:assertion", "XACMLPolicyStatement", "xacml-saml");
    public static final QName DEFAULT_ELEMENT_NAME_XACML11 = new QName("urn:oasis:names:tc:xacml:1.1:profile:saml2.0:v2:schema:assertion", "XACMLPolicyStatement", "xacml-saml");
    public static final QName DEFAULT_ELEMENT_NAME_XACML20 = new QName("urn:oasis:names:tc:xacml:2.0:profile:saml2.0:v2:schema:assertion", "XACMLPolicyStatement", "xacml-saml");
    public static final QName DEFAULT_ELEMENT_NAME_XACML30 = new QName("urn:oasis:names:tc:xacml:3.0:profile:saml2.0:v2:schema:assertion", "XACMLPolicyStatement", "xacml-saml");
    public static final String TYPE_LOCAL_NAME = "XACMLPolicyStatementType";
    public static final QName TYPE_NAME_XACML10 = new QName("urn:oasis:names:tc:xacml:1.0:profile:saml2.0:v2:schema:assertion", "XACMLPolicyStatementType", "xacml-saml");
    public static final QName TYPE_NAME_XACML11 = new QName("urn:oasis:names:tc:xacml:1.1:profile:saml2.0:v2:schema:assertion", "XACMLPolicyStatementType", "xacml-saml");
    public static final QName TYPE_NAME_XACML20 = new QName("urn:oasis:names:tc:xacml:2.0:profile:saml2.0:v2:schema:assertion", "XACMLPolicyStatementType", "xacml-saml");
    public static final QName TYPE_NAME_XACML30 = new QName("urn:oasis:names:tc:xacml:3.0:profile:saml2.0:v2:schema:assertion", "XACMLPolicyStatementType", "xacml-saml");

    public List<PolicyType> getPolicies();

    public List<PolicySetType> getPolicySets();

    public ReferencedPoliciesType getReferencedPolicies();

    public void setReferencedPolicies(ReferencedPoliciesType var1);
}

