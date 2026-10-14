/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface SubjectLocality
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectLocality";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectLocality", "saml2");
    public static final String TYPE_LOCAL_NAME = "SubjectLocalityType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectLocalityType", "saml2");
    public static final String ADDRESS_ATTRIB_NAME = "Address";
    public static final String DNS_NAME_ATTRIB_NAME = "DNSName";

    public String getAddress();

    public void setAddress(String var1);

    public String getDNSName();

    public void setDNSName(String var1);
}

