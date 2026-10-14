/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Terminate
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Terminate";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "Terminate", "saml2p");
    public static final String TYPE_LOCAL_NAME = "TerminateType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "TerminateType", "saml2p");
}

