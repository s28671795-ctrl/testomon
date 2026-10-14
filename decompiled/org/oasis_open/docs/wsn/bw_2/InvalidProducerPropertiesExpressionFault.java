/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFaultType;

@WebFault(name="InvalidProducerPropertiesExpressionFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class InvalidProducerPropertiesExpressionFault
extends Exception {
    private InvalidProducerPropertiesExpressionFaultType invalidProducerPropertiesExpressionFault;

    public InvalidProducerPropertiesExpressionFault() {
    }

    public InvalidProducerPropertiesExpressionFault(String message) {
        super(message);
    }

    public InvalidProducerPropertiesExpressionFault(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidProducerPropertiesExpressionFault(String message, InvalidProducerPropertiesExpressionFaultType invalidProducerPropertiesExpressionFault) {
        super(message);
        this.invalidProducerPropertiesExpressionFault = invalidProducerPropertiesExpressionFault;
    }

    public InvalidProducerPropertiesExpressionFault(String message, InvalidProducerPropertiesExpressionFaultType invalidProducerPropertiesExpressionFault, Throwable cause) {
        super(message, cause);
        this.invalidProducerPropertiesExpressionFault = invalidProducerPropertiesExpressionFault;
    }

    public InvalidProducerPropertiesExpressionFaultType getFaultInfo() {
        return this.invalidProducerPropertiesExpressionFault;
    }
}

