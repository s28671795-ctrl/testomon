/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.ctx.AttributeValueType;

public interface AttributeType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Attribute";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "Attribute", "xacml-context");
    public static final String TYPE_LOCAL_NAME = "AttributeType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "AttributeType", "xacml-context");
    public static final String ATTRIBUTEID_ATTTRIB_NAME = "AttributeId";
    public static final String DATATYPE_ATTRIB_NAME = "DataType";
    public static final String ISSUER_ATTRIB_NAME = "Issuer";

    public String getAttributeId();

    public List<AttributeValueType> getAttributeValues();

    public String getDataType();

    public String getIssuer();

    public void setAttributeID(String var1);

    public void setDataType(String var1);

    public void setIssuer(String var1);
}

