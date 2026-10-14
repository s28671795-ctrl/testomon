/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.policy.AttributeValueType;

public interface CombinerParameterType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "CombinerParameter";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "CombinerParameter", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "CombinerParameterType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "CombinerParameterType", "xacml");
    public static final String PARAMETER_NAMEATTRIB_NAME = "ParameterName";

    public AttributeValueType getAttributeValue();

    public void setAttributeValue(AttributeValueType var1);

    public String getParameterName();

    public void setParameterName(String var1);
}

