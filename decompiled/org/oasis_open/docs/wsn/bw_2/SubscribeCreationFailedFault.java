/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFaultType;

@WebFault(name="SubscribeCreationFailedFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class SubscribeCreationFailedFault
extends Exception {
    private SubscribeCreationFailedFaultType subscribeCreationFailedFault;

    public SubscribeCreationFailedFault() {
    }

    public SubscribeCreationFailedFault(String message) {
        super(message);
    }

    public SubscribeCreationFailedFault(String message, Throwable cause) {
        super(message, cause);
    }

    public SubscribeCreationFailedFault(String message, SubscribeCreationFailedFaultType subscribeCreationFailedFault) {
        super(message);
        this.subscribeCreationFailedFault = subscribeCreationFailedFault;
    }

    public SubscribeCreationFailedFault(String message, SubscribeCreationFailedFaultType subscribeCreationFailedFault, Throwable cause) {
        super(message, cause);
        this.subscribeCreationFailedFault = subscribeCreationFailedFault;
    }

    public SubscribeCreationFailedFaultType getFaultInfo() {
        return this.subscribeCreationFailedFault;
    }
}

