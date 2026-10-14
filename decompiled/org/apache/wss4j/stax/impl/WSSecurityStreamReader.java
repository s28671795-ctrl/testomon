/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl;

import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.impl.XMLSecurityStreamReader;

public class WSSecurityStreamReader
extends XMLSecurityStreamReader {
    private final boolean initiator;
    private final boolean returnSecurityError;

    public WSSecurityStreamReader(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, boolean initiator, boolean returnSecurityError) {
        super(inputProcessorChain, securityProperties);
        this.initiator = initiator;
        this.returnSecurityError = returnSecurityError;
    }

    @Override
    public int next() throws XMLStreamException {
        try {
            return super.next();
        }
        catch (XMLStreamException e) {
            Throwable cause = e.getCause();
            if (this.returnSecurityError || this.initiator || cause != null && cause.getCause() instanceof WSSPolicyException) {
                throw e;
            }
            throw new XMLStreamException(new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_ERROR));
        }
    }
}

