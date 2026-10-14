/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFaultType;

@WebFault(name="NotifyMessageNotSupportedFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class NotifyMessageNotSupportedFault
extends Exception {
    private NotifyMessageNotSupportedFaultType notifyMessageNotSupportedFault;

    public NotifyMessageNotSupportedFault() {
    }

    public NotifyMessageNotSupportedFault(String message) {
        super(message);
    }

    public NotifyMessageNotSupportedFault(String message, Throwable cause) {
        super(message, cause);
    }

    public NotifyMessageNotSupportedFault(String message, NotifyMessageNotSupportedFaultType notifyMessageNotSupportedFault) {
        super(message);
        this.notifyMessageNotSupportedFault = notifyMessageNotSupportedFault;
    }

    public NotifyMessageNotSupportedFault(String message, NotifyMessageNotSupportedFaultType notifyMessageNotSupportedFault, Throwable cause) {
        super(message, cause);
        this.notifyMessageNotSupportedFault = notifyMessageNotSupportedFault;
    }

    public NotifyMessageNotSupportedFaultType getFaultInfo() {
        return this.notifyMessageNotSupportedFault;
    }
}

