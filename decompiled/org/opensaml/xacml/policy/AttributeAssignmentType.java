/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.policy.AttributeValueType;

public interface AttributeAssignmentType
extends AttributeValueType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeAssignment";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "AttributeAssignment", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "AttributeAssignmentType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "AttributeAssignmentType", "xacml");
    public static final String ATTR_ID_ATTRIB_NAME = "AttributeId";

    public String getAttributeId();

    public void setAttributeId(String var1);
}

