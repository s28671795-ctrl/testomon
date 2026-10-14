/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSURI;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface EncryptionAlgorithm
extends XSURI,
WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "EncryptionAlgorithm";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "EncryptionAlgorithm", "wst");
}

