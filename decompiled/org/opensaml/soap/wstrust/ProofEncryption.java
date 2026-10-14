/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface ProofEncryption
extends WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "ProofEncryption";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ProofEncryption", "wst");
    public static final String TYPE_LOCAL_NAME = "ProofEncryptionType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ProofEncryptionType", "wst");

    public XMLObject getUnknownXMLObject();

    public void setUnknownXMLObject(XMLObject var1);
}

