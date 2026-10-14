/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface SubjectLocality
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectLocality";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectLocality", "saml1");
    public static final String TYPE_LOCAL_NAME = "SubjectLocalityType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectLocalityType", "saml1");
    public static final String IPADDRESS_ATTRIB_NAME = "IPAddress";
    public static final String DNSADDRESS_ATTRIB_NAME = "DNSAddress";

    public String getIPAddress();

    public void setIPAddress(String var1);

    public String getDNSAddress();

    public void setDNSAddress(String var1);
}

