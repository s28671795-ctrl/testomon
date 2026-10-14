/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.xacml.policy.ExpressionType;

public interface AttributeSelectorType
extends ExpressionType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeSelector";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "AttributeSelector", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "AttributeSelectorType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "AttributeSelectorType", "xacml");
    public static final String REQUEST_CONTEXT_PATH_ATTRIB_NAME = "RequestContextPath";
    public static final String DATA_TYPE_ATTRIB_NAME = "DataType";
    public static final String MUST_BE_PRESENT_ATTRIB_NAME = "MustBePresent";

    public String getRequestContextPath();

    public void setRequestContextPath(String var1);

    public String getDataType();

    public void setDataType(String var1);

    public Boolean getMustBePresent();

    public XSBooleanValue getMustBePresentXSBoolean();

    public void setMustBePresent(Boolean var1);

    public void setMustBePresentXSBoolean(XSBooleanValue var1);
}

