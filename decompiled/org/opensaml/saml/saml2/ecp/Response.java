/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.ecp;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public interface Response
extends SAMLObject,
MustUnderstandBearing,
ActorBearing {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Response";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "Response", "ecp");
    public static final String TYPE_LOCAL_NAME = "ResponseType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "ResponseType", "ecp");
    public static final String ASSERTION_CONSUMER_SERVICE_URL_ATTRIB_NAME = "AssertionConsumerServiceURL";

    public String getAssertionConsumerServiceURL();

    public void setAssertionConsumerServiceURL(String var1);
}

