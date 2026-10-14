/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.policy.ConditionType;
import org.opensaml.xacml.policy.DescriptionType;
import org.opensaml.xacml.policy.EffectType;
import org.opensaml.xacml.policy.TargetType;

public interface RuleType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Rule";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Rule", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "RuleType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "RuleType", "xacml");
    public static final String RULE_ID_ATTRIB_NAME = "RuleId";
    public static final String EFFECT_ATTRIB_NAME = "Effect";

    public DescriptionType getDescription();

    public void setDescription(DescriptionType var1);

    public TargetType getTarget();

    public void setTarget(TargetType var1);

    public ConditionType getCondition();

    public void setCondition(ConditionType var1);

    public String getRuleId();

    public void setRuleId(String var1);

    public EffectType getEffect();

    public void setEffect(EffectType var1);
}

