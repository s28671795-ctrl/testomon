/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdquery;

import javax.xml.namespace.QName;
import org.opensaml.saml.ext.saml2mdquery.QueryDescriptorType;

public interface AuthnQueryDescriptorType
extends QueryDescriptorType {
    public static final String TYPE_LOCAL_NAME = "AuthnQueryDescriptorType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ext:query", "AuthnQueryDescriptorType", "query");
}

