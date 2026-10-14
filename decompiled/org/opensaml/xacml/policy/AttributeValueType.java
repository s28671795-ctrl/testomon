/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.xacml.policy.ExpressionType;

public interface AttributeValueType
extends ExpressionType,
AttributeExtensibleXMLObject,
ElementExtensibleXMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeValue";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "AttributeValue", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "AttributeValueType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "AttributeValueType", "xacml");
    public static final String DATA_TYPE_ATTRIB_NAME = "DataType";

    public String getDataType();

    public void setDataType(String var1);

    public String getValue();

    public void setValue(String var1);
}

