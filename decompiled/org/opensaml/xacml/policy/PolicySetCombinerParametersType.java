/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.policy.CombinerParametersType;

public interface PolicySetCombinerParametersType
extends CombinerParametersType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "PolicySetCombinerParameters";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicySetCombinerParameters", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "PolicySetCombinerParametersType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicySetCombinerParametersType", "xacml");
    public static final String POLICY_SET_ID_REF_ATTRIB_NAME = "PolicySetIdRef";

    public String getPolicySetIdRef();

    public void setPolicySetIdRef(String var1);
}

