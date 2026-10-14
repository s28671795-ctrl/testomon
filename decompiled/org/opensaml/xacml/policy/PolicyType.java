/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.policy.CombinerParametersType;
import org.opensaml.xacml.policy.DefaultsType;
import org.opensaml.xacml.policy.DescriptionType;
import org.opensaml.xacml.policy.ObligationsType;
import org.opensaml.xacml.policy.RuleCombinerParametersType;
import org.opensaml.xacml.policy.RuleType;
import org.opensaml.xacml.policy.TargetType;
import org.opensaml.xacml.policy.VariableDefinitionType;

public interface PolicyType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Policy";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Policy", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "PolicyType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicyType", "xacml");
    public static final String POLICY_ID_ATTRIB_NAME = "PolicyId";
    public static final String VERSION_ATTRIB_NAME = "Version";
    public static final String VERSION_DEFAULT_VALUE = "1.0";
    public static final String RULE_COMBINING_ALG_ID_ATTRIB_NAME = "RuleCombiningAlgId";

    public DescriptionType getDescription();

    public void setDescription(DescriptionType var1);

    public DefaultsType getPolicyDefaults();

    public void setPolicyDefaults(DefaultsType var1);

    public TargetType getTarget();

    public void setTarget(TargetType var1);

    public List<CombinerParametersType> getCombinerParameters();

    public List<RuleCombinerParametersType> getRuleCombinerParameters();

    public List<VariableDefinitionType> getVariableDefinitions();

    public List<RuleType> getRules();

    public ObligationsType getObligations();

    public void setObligations(ObligationsType var1);

    public String getPolicyId();

    public void setPolicyId(String var1);

    public String getVersion();

    public void setVersion(String var1);

    public String getRuleCombiningAlgoId();

    public void setRuleCombiningAlgoId(String var1);
}

