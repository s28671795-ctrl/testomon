/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.SubjectQuery;

public interface AttributeQuery
extends SubjectQuery {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeQuery";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "AttributeQuery", "saml2p");
    public static final String TYPE_LOCAL_NAME = "AttributeQueryType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "AttributeQueryType", "saml2p");

    public List<Attribute> getAttributes();
}

