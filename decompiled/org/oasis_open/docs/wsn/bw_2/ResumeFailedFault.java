/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.ResumeFailedFaultType;

@WebFault(name="ResumeFailedFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class ResumeFailedFault
extends Exception {
    public static final long serialVersionUID = 1L;
    private ResumeFailedFaultType faultInfo;

    public ResumeFailedFault() {
    }

    public ResumeFailedFault(String message) {
        super(message);
    }

    public ResumeFailedFault(String message, Throwable cause) {
        super(message, cause);
    }

    public ResumeFailedFault(String message, ResumeFailedFaultType resumeFailedFault) {
        super(message);
        this.faultInfo = resumeFailedFault;
    }

    public ResumeFailedFault(String message, ResumeFailedFaultType resumeFailedFault, Throwable cause) {
        super(message, cause);
        this.faultInfo = resumeFailedFault;
    }

    public ResumeFailedFaultType getFaultInfo() {
        return this.faultInfo;
    }
}

