/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFaultType;

@WebFault(name="UnableToDestroySubscriptionFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class UnableToDestroySubscriptionFault
extends Exception {
    public static final long serialVersionUID = 1L;
    private UnableToDestroySubscriptionFaultType faultInfo;

    public UnableToDestroySubscriptionFault() {
    }

    public UnableToDestroySubscriptionFault(String message) {
        super(message);
    }

    public UnableToDestroySubscriptionFault(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToDestroySubscriptionFault(String message, UnableToDestroySubscriptionFaultType unableToDestroySubscriptionFault) {
        super(message);
        this.faultInfo = unableToDestroySubscriptionFault;
    }

    public UnableToDestroySubscriptionFault(String message, UnableToDestroySubscriptionFaultType unableToDestroySubscriptionFault, Throwable cause) {
        super(message, cause);
        this.faultInfo = unableToDestroySubscriptionFault;
    }

    public UnableToDestroySubscriptionFaultType getFaultInfo() {
        return this.faultInfo;
    }
}

