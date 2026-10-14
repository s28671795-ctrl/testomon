/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface NewID
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "NewID";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "NewID", "saml2p");

    public String getNewID();

    public void setNewID(String var1);
}

