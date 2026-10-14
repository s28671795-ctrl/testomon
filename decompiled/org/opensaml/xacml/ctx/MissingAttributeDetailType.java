/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.ctx.AttributeValueType;

public interface MissingAttributeDetailType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "MissingAttributeDetail";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "MissingAttributeDetail", "xacml-context");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "MissingAttributeDetailType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "MissingAttributeDetailType", "xacml");
    public static final String ATTRIBUTE_ID_ATTRIB_NAME = "AttributeId";
    public static final String DATA_TYPE_ATTRIB_NAME = "DataType";
    public static final String ISSUER_ATTRIB_NAME = "Issuer";

    public List<AttributeValueType> getAttributeValues();

    public String getAttributeId();

    public void setAttributeId(String var1);

    public String getDataType();

    public void setDataType(String var1);

    public String getIssuer();

    public void setIssuer(String var1);
}

