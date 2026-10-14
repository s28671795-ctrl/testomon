/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.messaging.saaj.soap.dynamic;

import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
import com.sun.xml.messaging.saaj.soap.SOAPFactoryImpl;
import javax.xml.soap.Detail;
import javax.xml.soap.SOAPException;

public class SOAPFactoryDynamicImpl
extends SOAPFactoryImpl {
    @Override
    protected SOAPDocumentImpl createDocument() {
        return null;
    }

    @Override
    public Detail createDetail() throws SOAPException {
        throw new UnsupportedOperationException("createDetail() not supported for Dynamic Protocol");
    }
}

