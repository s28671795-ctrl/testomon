/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml1.core.Query;
import org.opensaml.saml.saml1.core.Subject;

public interface SubjectQuery
extends Query {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectQuery";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "SubjectQuery", "saml1p");
    public static final String TYPE_LOCAL_NAME = "SubjectQueryAbstractType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "SubjectQueryAbstractType", "saml1p");

    public Subject getSubject();

    public void setSubject(Subject var1);
}

