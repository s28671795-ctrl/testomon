/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.policy.CombinerParametersType;
import org.opensaml.xacml.policy.DefaultsType;
import org.opensaml.xacml.policy.DescriptionType;
import org.opensaml.xacml.policy.IdReferenceType;
import org.opensaml.xacml.policy.ObligationsType;
import org.opensaml.xacml.policy.PolicyCombinerParametersType;
import org.opensaml.xacml.policy.PolicySetCombinerParametersType;
import org.opensaml.xacml.policy.PolicyType;
import org.opensaml.xacml.policy.TargetType;

public interface PolicySetType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "PolicySet";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicySet", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "PolicySetType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicySetType", "xacml");
    public static final String POLICY_SET_ID_ATTRIB_NAME = "PolicySetId";
    public static final String VERSION_ATTRIB_NAME = "Version";
    public static final String DEFAULT_VERSION = "1.0";
    public static final String POLICY_COMBINING_ALG_ID_ATTRIB_NAME = "PolicyCombiningAlgId";

    public DescriptionType getDescription();

    public void setDescription(DescriptionType var1);

    public IndexedXMLObjectChildrenList<XACMLObject> getPolicyChoiceGroup();

    public DefaultsType getPolicySetDefaults();

    public void setPolicySetDefaults(DefaultsType var1);

    public TargetType getTarget();

    public void setTarget(TargetType var1);

    public List<PolicySetType> getPolicySets();

    public List<PolicyType> getPolicies();

    public List<IdReferenceType> getPolicySetIdReferences();

    public List<IdReferenceType> getPolicyIdReferences();

    public List<CombinerParametersType> getCombinerParameters();

    public List<PolicyCombinerParametersType> getPolicyCombinerParameters();

    public List<PolicySetCombinerParametersType> getPolicySetCombinerParameters();

    public ObligationsType getObligations();

    public void setObligations(ObligationsType var1);

    public String getPolicySetId();

    public void setPolicySetId(String var1);

    public String getVersion();

    public void setVersion(String var1);

    public String getPolicyCombiningAlgoId();

    public void setPolicyCombiningAlgoId(String var1);
}

