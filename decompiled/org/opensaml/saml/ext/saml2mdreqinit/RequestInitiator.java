/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdreqinit;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.metadata.Endpoint;

public interface RequestInitiator
extends Endpoint {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "RequestInitiator";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:profiles:SSO:request-init", "RequestInitiator", "init");
    public static final String REQUIRED_BINDING_VALUE = "urn:oasis:names:tc:SAML:profiles:SSO:request-init";
}

