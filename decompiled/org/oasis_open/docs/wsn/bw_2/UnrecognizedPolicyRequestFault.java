/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.UnrecognizedPolicyRequestFaultType;

@WebFault(name="UnrecognizedPolicyRequestFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class UnrecognizedPolicyRequestFault
extends Exception {
    private UnrecognizedPolicyRequestFaultType unrecognizedPolicyRequestFault;

    public UnrecognizedPolicyRequestFault() {
    }

    public UnrecognizedPolicyRequestFault(String message) {
        super(message);
    }

    public UnrecognizedPolicyRequestFault(String message, Throwable cause) {
        super(message, cause);
    }

    public UnrecognizedPolicyRequestFault(String message, UnrecognizedPolicyRequestFaultType unrecognizedPolicyRequestFault) {
        super(message);
        this.unrecognizedPolicyRequestFault = unrecognizedPolicyRequestFault;
    }

    public UnrecognizedPolicyRequestFault(String message, UnrecognizedPolicyRequestFaultType unrecognizedPolicyRequestFault, Throwable cause) {
        super(message, cause);
        this.unrecognizedPolicyRequestFault = unrecognizedPolicyRequestFault;
    }

    public UnrecognizedPolicyRequestFaultType getFaultInfo() {
        return this.unrecognizedPolicyRequestFault;
    }
}

