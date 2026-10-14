/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.saml.common.SAMLObject;

public interface Endpoint
extends SAMLObject,
ElementExtensibleXMLObject,
AttributeExtensibleXMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Endpoint";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "Endpoint", "md");
    public static final String TYPE_LOCAL_NAME = "EndpointType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "EndpointType", "md");
    public static final String BINDING_ATTRIB_NAME = "Binding";
    public static final String LOCATION_ATTRIB_NAME = "Location";
    public static final String RESPONSE_LOCATION_ATTRIB_NAME = "ResponseLocation";

    public String getBinding();

    public void setBinding(String var1);

    public String getLocation();

    public void setLocation(String var1);

    public String getResponseLocation();

    public void setResponseLocation(String var1);
}

