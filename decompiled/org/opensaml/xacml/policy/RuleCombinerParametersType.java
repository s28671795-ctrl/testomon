/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.policy.CombinerParametersType;

public interface RuleCombinerParametersType
extends CombinerParametersType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "RuleCombinerParameters";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "RuleCombinerParameters", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "RuleCombinerParametersType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "RuleCombinerParametersType", "xacml");
    public static final String RULE_ID_REF_ATTRIB_NAME = "RuleIdRef";

    public String getRuleIdRef();

    public void setRuleIdRef(String var1);
}

