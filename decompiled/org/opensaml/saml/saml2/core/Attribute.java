/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.SAMLObject;

public interface Attribute
extends SAMLObject,
AttributeExtensibleXMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Attribute";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Attribute", "saml2");
    public static final String TYPE_LOCAL_NAME = "AttributeType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AttributeType", "saml2");
    public static final String NAME_ATTTRIB_NAME = "Name";
    public static final String NAME_FORMAT_ATTRIB_NAME = "NameFormat";
    public static final String FRIENDLY_NAME_ATTRIB_NAME = "FriendlyName";
    public static final String UNSPECIFIED = "urn:oasis:names:tc:SAML:2.0:attrname-format:unspecified";
    public static final String URI_REFERENCE = "urn:oasis:names:tc:SAML:2.0:attrname-format:uri";
    public static final String BASIC = "urn:oasis:names:tc:SAML:2.0:attrname-format:basic";

    public String getName();

    public void setName(String var1);

    public String getNameFormat();

    public void setNameFormat(String var1);

    public String getFriendlyName();

    public void setFriendlyName(String var1);

    public List<XMLObject> getAttributeValues();
}

