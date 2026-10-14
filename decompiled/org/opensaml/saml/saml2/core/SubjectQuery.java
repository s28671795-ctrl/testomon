/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.Subject;

public interface SubjectQuery
extends RequestAbstractType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectQuery";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "SubjectQuery", "saml2p");
    public static final String TYPE_LOCAL_NAME = "SubjectQueryAbstractType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "SubjectQueryAbstractType", "saml2p");

    public Subject getSubject();

    public void setSubject(Subject var1);
}

