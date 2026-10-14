/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdrpi;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.metadata.LocalizedURI;

public interface RegistrationPolicy
extends LocalizedURI {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "RegistrationPolicy";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:rpi", "RegistrationPolicy", "mdrpi");
}

