/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFaultType;

@WebFault(name="InvalidTopicExpressionFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class InvalidTopicExpressionFault
extends Exception {
    private InvalidTopicExpressionFaultType invalidTopicExpressionFault;

    public InvalidTopicExpressionFault() {
    }

    public InvalidTopicExpressionFault(String message) {
        super(message);
    }

    public InvalidTopicExpressionFault(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTopicExpressionFault(String message, InvalidTopicExpressionFaultType invalidTopicExpressionFault) {
        super(message);
        this.invalidTopicExpressionFault = invalidTopicExpressionFault;
    }

    public InvalidTopicExpressionFault(String message, InvalidTopicExpressionFaultType invalidTopicExpressionFault, Throwable cause) {
        super(message, cause);
        this.invalidTopicExpressionFault = invalidTopicExpressionFault;
    }

    public InvalidTopicExpressionFaultType getFaultInfo() {
        return this.invalidTopicExpressionFault;
    }
}

