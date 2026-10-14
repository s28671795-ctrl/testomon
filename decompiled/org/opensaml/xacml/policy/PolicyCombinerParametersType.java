/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.policy.CombinerParametersType;

public interface PolicyCombinerParametersType
extends CombinerParametersType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "PolicyCombinerParameters";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicyCombinerParameters", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "PolicyCombinerParametersType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicyCombinerParametersType", "xacml");
    public static final String POLICY_ID_REF_ATTRIB_NAME = "PolicyIdRef";

    public String getPolicyIdRef();

    public void setPolicyIdRef(String var1);
}

