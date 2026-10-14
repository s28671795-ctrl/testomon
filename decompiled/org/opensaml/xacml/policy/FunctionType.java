/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.policy.ExpressionType;

public interface FunctionType
extends ExpressionType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Function";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Function", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "FunctionType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "FunctionType", "xacml");
    public static final String FUNCTION_ID_ATTRIB_NAME = "FunctionId";

    public String getFunctionId();

    public void setFunctionId(String var1);
}

