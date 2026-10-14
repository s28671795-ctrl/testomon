/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface SessionIndex
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "SessionIndex";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "SessionIndex", "saml2p");

    public String getSessionIndex();

    public void setSessionIndex(String var1);
}

