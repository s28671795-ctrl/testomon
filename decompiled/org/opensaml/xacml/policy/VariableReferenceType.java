/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.policy.ExpressionType;

public interface VariableReferenceType
extends ExpressionType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "VariableReference";
    public static final QName DEFAULT_ELEMENT_NAME_XACML20 = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "VariableReference", "xacml");
    public static final String TYPE_LOCAL_NAME = "VariableReferenceType";
    public static final QName TYPE_NAME_XACML20 = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "VariableReferenceType", "xacml");
    public static final String VARIABLE_ID_ATTRIB_NAME = "VariableId";

    public List<ExpressionType> getExpressions();

    public String getVariableId();

    public void setVariableId(String var1);
}

