/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.messaging.saaj.client.p2p;

import com.sun.xml.messaging.saaj.client.p2p.HttpSOAPConnection;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;

public class HttpSOAPConnectionFactory
extends SOAPConnectionFactory {
    @Override
    public SOAPConnection createConnection() throws SOAPException {
        return new HttpSOAPConnection();
    }
}

