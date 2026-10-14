/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.PauseFailedFaultType;

@WebFault(name="PauseFailedFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class PauseFailedFault
extends Exception {
    public static final long serialVersionUID = 1L;
    private PauseFailedFaultType faultInfo;

    public PauseFailedFault() {
    }

    public PauseFailedFault(String message) {
        super(message);
    }

    public PauseFailedFault(String message, Throwable cause) {
        super(message, cause);
    }

    public PauseFailedFault(String message, PauseFailedFaultType pauseFailedFault) {
        super(message);
        this.faultInfo = pauseFailedFault;
    }

    public PauseFailedFault(String message, PauseFailedFaultType pauseFailedFault, Throwable cause) {
        super(message, cause);
        this.faultInfo = pauseFailedFault;
    }

    public PauseFailedFaultType getFaultInfo() {
        return this.faultInfo;
    }
}

