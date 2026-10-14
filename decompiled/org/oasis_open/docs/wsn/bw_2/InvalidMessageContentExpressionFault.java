/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.InvalidMessageContentExpressionFaultType;

@WebFault(name="InvalidMessageContentExpressionFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class InvalidMessageContentExpressionFault
extends Exception {
    private InvalidMessageContentExpressionFaultType invalidMessageContentExpressionFault;

    public InvalidMessageContentExpressionFault() {
    }

    public InvalidMessageContentExpressionFault(String message) {
        super(message);
    }

    public InvalidMessageContentExpressionFault(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMessageContentExpressionFault(String message, InvalidMessageContentExpressionFaultType invalidMessageContentExpressionFault) {
        super(message);
        this.invalidMessageContentExpressionFault = invalidMessageContentExpressionFault;
    }

    public InvalidMessageContentExpressionFault(String message, InvalidMessageContentExpressionFaultType invalidMessageContentExpressionFault, Throwable cause) {
        super(message, cause);
        this.invalidMessageContentExpressionFault = invalidMessageContentExpressionFault;
    }

    public InvalidMessageContentExpressionFaultType getFaultInfo() {
        return this.invalidMessageContentExpressionFault;
    }
}

