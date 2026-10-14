/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityEvent;

import javax.xml.namespace.QName;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;

public class OperationSecurityEvent
extends SecurityEvent {
    private QName operation;

    public OperationSecurityEvent() {
        super(WSSecurityEventConstants.OPERATION);
    }

    public QName getOperation() {
        return this.operation;
    }

    public void setOperation(QName operation) {
        this.operation = operation;
    }
}

