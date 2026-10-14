/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.xmlsec.signature.PublicKey;
import org.opensaml.xmlsec.signature.impl.ECPointTypeImpl;

public class PublicKeyImpl
extends ECPointTypeImpl
implements PublicKey {
    protected PublicKeyImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}

