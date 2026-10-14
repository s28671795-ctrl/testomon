/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface ExpressionType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Expression";
    public static final QName DEFAULT_ELEMENT_NAME_XACML20 = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Expression", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "ExpressionType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "ExpressionType", "xacml");
}

