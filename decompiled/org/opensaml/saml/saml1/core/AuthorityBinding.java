/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface AuthorityBinding
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AuthorityBinding";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorityBinding", "saml1");
    public static final String TYPE_LOCAL_NAME = "AuthorityBindingType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorityBindingType", "saml1");
    public static final String AUTHORITYKIND_ATTRIB_NAME = "AuthorityKind";
    public static final String LOCATION_ATTRIB_NAME = "Location";
    public static final String BINDING_ATTRIB_NAME = "Binding";

    public QName getAuthorityKind();

    public void setAuthorityKind(QName var1);

    public String getLocation();

    public void setLocation(String var1);

    public String getBinding();

    public void setBinding(String var1);
}

