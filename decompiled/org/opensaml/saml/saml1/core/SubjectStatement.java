/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.Statement;
import org.opensaml.saml.saml1.core.Subject;

public interface SubjectStatement
extends SAMLObject,
Statement {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectStatement";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectStatement", "saml1");
    public static final String TYPE_LOCAL_NAME = "SubjectStatementAbstractType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectStatementAbstractType", "saml1");

    public Subject getSubject();

    public void setSubject(Subject var1);
}

