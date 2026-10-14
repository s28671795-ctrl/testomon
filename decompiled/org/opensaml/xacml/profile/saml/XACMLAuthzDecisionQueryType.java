/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.policy.PolicySetType;
import org.opensaml.xacml.policy.PolicyType;
import org.opensaml.xacml.profile.saml.ReferencedPoliciesType;

public interface XACMLAuthzDecisionQueryType
extends RequestAbstractType,
XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "XACMLAuthzDecisionQuery";
    public static final QName DEFAULT_ELEMENT_NAME_XACML10 = new QName("urn:oasis:names:tc:xacml:1.0:profile:saml2.0:v2:schema:protocol", "XACMLAuthzDecisionQuery", "xacml-samlp");
    public static final QName DEFAULT_ELEMENT_NAME_XACML11 = new QName("urn:oasis:names:tc:xacml:1.1:profile:saml2.0:v2:schema:protocol", "XACMLAuthzDecisionQuery", "xacml-samlp");
    public static final QName DEFAULT_ELEMENT_NAME_XACML20 = new QName("urn:oasis:names:tc:xacml:2.0:profile:saml2.0:v2:schema:protocol", "XACMLAuthzDecisionQuery", "xacml-samlp");
    public static final QName DEFAULT_ELEMENT_NAME_XACML30 = new QName("urn:oasis:names:tc:xacml:3.0:profile:saml2.0:v2:schema:protocol", "XACMLAuthzDecisionQuery", "xacml-samlp");
    public static final String TYPE_LOCAL_NAME = "XACMLAuthzDecisionQueryType";
    public static final QName TYPE_NAME_XACML10 = new QName("urn:oasis:names:tc:xacml:1.0:profile:saml2.0:v2:schema:protocol", "XACMLAuthzDecisionQueryType", "xacml-samlp");
    public static final QName TYPE_NAME_XACML11 = new QName("urn:oasis:names:tc:xacml:1.1:profile:saml2.0:v2:schema:protocol", "XACMLAuthzDecisionQueryType", "xacml-samlp");
    public static final QName TYPE_NAME_XACML20 = new QName("urn:oasis:names:tc:xacml:2.0:profile:saml2.0:v2:schema:protocol", "XACMLAuthzDecisionQueryType", "xacml-samlp");
    public static final QName TYPE_NAME_XACML30 = new QName("urn:oasis:names:tc:xacml:3.0:profile:saml2.0:v2:schema:protocol", "XACMLAuthzDecisionQueryType", "xacml-samlp");
    public static final String INPUTCONTEXTONLY_ATTRIB_NAME = "InputContextOnly";
    public static final String RETURNCONTEXT_ATTRIB_NAME = "ReturnContext";
    public static final String COMBINEPOLICIES_ATTRIB_NAME = "CombinePolicies";

    public XSBooleanValue getCombinePoliciesXSBooleanValue();

    public XSBooleanValue getInputContextOnlyXSBooleanValue();

    public List<PolicyType> getPolicies();

    public List<PolicySetType> getPolicySets();

    public ReferencedPoliciesType getReferencedPolicies();

    public void setReferencedPolicies(ReferencedPoliciesType var1);

    public RequestType getRequest();

    public XSBooleanValue getReturnContextXSBooleanValue();

    public Boolean isCombinePolicies();

    public Boolean isInputContextOnly();

    public Boolean isReturnContext();

    public void setCombinePolicies(Boolean var1);

    public void setCombinePolicies(XSBooleanValue var1);

    public void setInputContextOnly(Boolean var1);

    public void setInputContextOnly(XSBooleanValue var1);

    public void setRequest(RequestType var1);

    public void setReturnContext(Boolean var1);

    public void setReturnContext(XSBooleanValue var1);
}

