/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.policy.AttributeDesignatorType;

public interface SubjectAttributeDesignatorType
extends AttributeDesignatorType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectAttributeDesignator";
    public static final QName DEFAULT_ELEMENT_QNAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "SubjectAttributeDesignator", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "SubjectAttributeDesignatorType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "SubjectAttributeDesignatorType", "xacml");
    public static final String SUBJECT_CATEGORY_ATTRIB_NAME = "SubjectCategory";

    public String getSubjectCategory();

    public void setSubjectCategory(String var1);
}

