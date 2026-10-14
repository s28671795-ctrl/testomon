/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.metadata.LocalizedName;

public interface ServiceDescription
extends LocalizedName {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "ServiceDescription";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "ServiceDescription", "md");
    public static final String TYPE_LOCAL_NAME = "localizedNameType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "localizedNameType", "md");
}

