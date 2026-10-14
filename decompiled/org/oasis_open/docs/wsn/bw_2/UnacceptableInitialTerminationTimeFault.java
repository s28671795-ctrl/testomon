/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFaultType;

@WebFault(name="UnacceptableInitialTerminationTimeFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class UnacceptableInitialTerminationTimeFault
extends Exception {
    private UnacceptableInitialTerminationTimeFaultType unacceptableInitialTerminationTimeFault;

    public UnacceptableInitialTerminationTimeFault() {
    }

    public UnacceptableInitialTerminationTimeFault(String message) {
        super(message);
    }

    public UnacceptableInitialTerminationTimeFault(String message, Throwable cause) {
        super(message, cause);
    }

    public UnacceptableInitialTerminationTimeFault(String message, UnacceptableInitialTerminationTimeFaultType unacceptableInitialTerminationTimeFault) {
        super(message);
        this.unacceptableInitialTerminationTimeFault = unacceptableInitialTerminationTimeFault;
    }

    public UnacceptableInitialTerminationTimeFault(String message, UnacceptableInitialTerminationTimeFaultType unacceptableInitialTerminationTimeFault, Throwable cause) {
        super(message, cause);
        this.unacceptableInitialTerminationTimeFault = unacceptableInitialTerminationTimeFault;
    }

    public UnacceptableInitialTerminationTimeFaultType getFaultInfo() {
        return this.unacceptableInitialTerminationTimeFault;
    }
}

