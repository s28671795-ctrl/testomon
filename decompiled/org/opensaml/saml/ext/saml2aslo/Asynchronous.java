/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2aslo;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Asynchronous
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Asynchronous";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol:ext:async-slo", "Asynchronous", "aslo");
    public static final String TYPE_LOCAL_NAME = "AsynchronousType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol:ext:async-slo", "AsynchronousType", "aslo");
}

