/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface AdditionalMetadataLocation
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AdditionalMetadataLocation";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AdditionalMetadataLocation", "md");
    public static final String TYPE_LOCAL_NAME = "AdditionalMetadataLocationType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AdditionalMetadataLocationType", "md");
    public static final String NAMESPACE_ATTRIB_NAME = "namespace";

    public String getLocationURI();

    public void setLocationURI(String var1);

    public String getNamespaceURI();

    public void setNamespaceURI(String var1);
}

