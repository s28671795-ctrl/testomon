/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.xacml.policy.ExpressionType;

public interface AttributeDesignatorType
extends ExpressionType {
    public static final String SUBJECT_ATTRIBUTE_DESIGNATOR_ELEMENT_LOCAL_NAME = "SubjectAttributeDesignator";
    public static final QName SUBJECT_ATTRIBUTE_DESIGNATOR_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "SubjectAttributeDesignator", "xacml");
    public static final String RESOURCE_ATTRIBUTE_DESIGNATOR_ELEMENT_LOCAL_NAME = "ResourceAttributeDesignator";
    public static final QName RESOURCE_ATTRIBUTE_DESIGNATOR_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "ResourceAttributeDesignator", "xacml");
    public static final String ACTION_ATTRIBUTE_DESIGNATOR_ELEMENT_LOCAL_NAME = "ActionAttributeDesignator";
    public static final QName ACTION_ATTRIBUTE_DESIGNATOR_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "ActionAttributeDesignator", "xacml");
    public static final String ENVIRONMENT_ATTRIBUTE_DESIGNATOR_ELEMENT_LOCAL_NAME = "EnvironmentAttributeDesignator";
    public static final QName ENVIRONMENT_ATTRIBUTE_DESIGNATOR_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "EnvironmentAttributeDesignator", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "AttributeDesignatorType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "AttributeDesignatorType", "xacml");
    public static final String ATTRIBUTE_ID_ATTRIB_NAME = "AttributeId";
    public static final String DATA_TYPE_ATTRIB_NAME = "DataType";
    public static final String ISSUER_ATTRIB_NAME = "Issuer";
    public static final String MUST_BE_PRESENT_ATTRIB_NAME = "MustBePresent";

    public String getAttributeId();

    public void setAttributeId(String var1);

    public String getDataType();

    public void setDataType(String var1);

    public String getIssuer();

    public void setIssuer(String var1);

    public XSBooleanValue getMustBePresentXSBoolean();

    public void setMustBePresentXSBoolean(XSBooleanValue var1);

    public void setMustBePresent(Boolean var1);

    public Boolean getMustBePresent();
}

