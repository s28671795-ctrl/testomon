/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.policy.ExpressionType;

public interface ConditionType
extends ExpressionType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Condition";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Condition", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "ConditionType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "ConditionType", "xacml");

    public ExpressionType getExpression();

    public void setExpression(ExpressionType var1);
}

