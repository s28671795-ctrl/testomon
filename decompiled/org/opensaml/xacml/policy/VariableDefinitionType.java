/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.policy.ExpressionType;

public interface VariableDefinitionType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "VariableDefinition";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "VariableDefinition", "xacml");
    public static final String TYPE_LOCAL_NAME = "VariableDefinitionType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "VariableDefinitionType", "xacml");
    public static final String VARIABLE_ID_ATTRIB_NAME = "VariableId";

    public ExpressionType getExpression();

    public void setExpression(ExpressionType var1);

    public String getVariableId();

    public void setVariableId(String var1);
}

