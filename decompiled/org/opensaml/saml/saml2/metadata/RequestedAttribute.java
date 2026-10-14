/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.core.Attribute;

public interface RequestedAttribute
extends Attribute {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "RequestedAttribute";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "RequestedAttribute", "md");
    public static final String TYPE_LOCAL_NAME = "RequestedAttributeType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "RequestedAttributeType", "md");
    public static final String IS_REQUIRED_ATTRIB_NAME = "isRequired";

    public Boolean isRequired();

    public XSBooleanValue isRequiredXSBoolean();

    public void setIsRequired(Boolean var1);

    public void setIsRequired(XSBooleanValue var1);
}

