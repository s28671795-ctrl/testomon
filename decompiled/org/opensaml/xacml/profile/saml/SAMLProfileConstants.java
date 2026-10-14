/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml;

import javax.xml.namespace.QName;

public final class SAMLProfileConstants {
    public static final String SAML20XACMLASSERTION_PREFIX = "xacml-saml";
    public static final String SAML20XACMLPROTOCOL_PREFIX = "xacml-samlp";
    public static final String SAML20XACML10P_NS = "urn:oasis:names:tc:xacml:1.0:profile:saml2.0:v2:schema:protocol";
    public static final String SAML20XACML10_NS = "urn:oasis:names:tc:xacml:1.0:profile:saml2.0:v2:schema:assertion";
    public static final String SAML20XACML1_1P_NS = "urn:oasis:names:tc:xacml:1.1:profile:saml2.0:v2:schema:protocol";
    public static final String SAML20XACML1_1_NS = "urn:oasis:names:tc:xacml:1.1:profile:saml2.0:v2:schema:assertion";
    public static final String SAML20XACML20P_NS = "urn:oasis:names:tc:xacml:2.0:profile:saml2.0:v2:schema:protocol";
    public static final String SAML20XACML20_NS = "urn:oasis:names:tc:xacml:2.0:profile:saml2.0:v2:schema:assertion";
    public static final String SAML20XACML30P_NS = "urn:oasis:names:tc:xacml:3.0:profile:saml2.0:v2:schema:protocol";
    public static final String SAML20XACML30_NS = "urn:oasis:names:tc:xacml:3.0:profile:saml2.0:v2:schema:assertion";
    public static final QName SAML_DATATYPE_ATTRIB = new QName("urn:oasis:names:tc:SAML:2.0:profiles:attribute:XACML", "DataType", "xacmlprof");

    private SAMLProfileConstants() {
    }
}

