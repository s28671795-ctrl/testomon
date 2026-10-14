/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.schema.XSBase64Binary;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface BinarySecret
extends XSBase64Binary,
AttributeExtensibleXMLObject,
WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "BinarySecret";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "BinarySecret", "wst");
    public static final String TYPE_LOCAL_NAME = "BinarySecretType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "BinarySecretType", "wst");
    public static final String TYPE_ATTRIB_NAME = "Type";
    public static final String TYPE_ASYMMETRIC_KEY = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/AsymmetricKey";
    public static final String TYPE_SYMMETRIC_KEY = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/SymmetricKey";
    public static final String TYPE_NONCE = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Nonce";

    public String getType();

    public void setType(String var1);
}

