/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.metadata.Endpoint;

public interface IndexedEndpoint
extends Endpoint {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "IndexedEndpoint";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "IndexedEndpoint", "md");
    public static final String TYPE_LOCAL_NAME = "IndexedEndpointType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "IndexedEndpointType", "md");
    public static final String INDEX_ATTRIB_NAME = "index";
    public static final String IS_DEFAULT_ATTRIB_NAME = "isDefault";

    public Integer getIndex();

    public void setIndex(Integer var1);

    public Boolean isDefault();

    public XSBooleanValue isDefaultXSBoolean();

    public void setIsDefault(Boolean var1);

    public void setIsDefault(XSBooleanValue var1);
}

