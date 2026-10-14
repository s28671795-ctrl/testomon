/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.Attribute;
import org.opensaml.saml.saml1.core.SubjectStatement;

public interface AttributeStatement
extends SAMLObject,
SubjectStatement {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeStatement";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeStatement", "saml1");
    public static final String TYPE_LOCAL_NAME = "AttributeStatementType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeStatementType", "saml1");

    public List<Attribute> getAttributes();
}

