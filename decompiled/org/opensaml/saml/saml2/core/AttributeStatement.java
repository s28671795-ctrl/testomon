/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.EncryptedAttribute;
import org.opensaml.saml.saml2.core.Statement;

public interface AttributeStatement
extends Statement {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeStatement";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AttributeStatement", "saml2");
    public static final String TYPE_LOCAL_NAME = "AttributeStatementType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AttributeStatementType", "saml2");

    public List<Attribute> getAttributes();

    public List<EncryptedAttribute> getEncryptedAttributes();
}

