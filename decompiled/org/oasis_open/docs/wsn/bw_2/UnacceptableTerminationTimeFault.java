/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.UnacceptableTerminationTimeFaultType;

@WebFault(name="UnacceptableTerminationTimeFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class UnacceptableTerminationTimeFault
extends Exception {
    public static final long serialVersionUID = 1L;
    private UnacceptableTerminationTimeFaultType faultInfo;

    public UnacceptableTerminationTimeFault() {
    }

    public UnacceptableTerminationTimeFault(String message) {
        super(message);
    }

    public UnacceptableTerminationTimeFault(String message, Throwable cause) {
        super(message, cause);
    }

    public UnacceptableTerminationTimeFault(String message, UnacceptableTerminationTimeFaultType unacceptableTerminationTimeFault) {
        super(message);
        this.faultInfo = unacceptableTerminationTimeFault;
    }

    public UnacceptableTerminationTimeFault(String message, UnacceptableTerminationTimeFaultType unacceptableTerminationTimeFault, Throwable cause) {
        super(message, cause);
        this.faultInfo = unacceptableTerminationTimeFault;
    }

    public UnacceptableTerminationTimeFaultType getFaultInfo() {
        return this.faultInfo;
    }
}

