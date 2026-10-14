/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.UnsupportedPolicyRequestFaultType;

@WebFault(name="UnsupportedPolicyRequestFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class UnsupportedPolicyRequestFault
extends Exception {
    private UnsupportedPolicyRequestFaultType unsupportedPolicyRequestFault;

    public UnsupportedPolicyRequestFault() {
    }

    public UnsupportedPolicyRequestFault(String message) {
        super(message);
    }

    public UnsupportedPolicyRequestFault(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedPolicyRequestFault(String message, UnsupportedPolicyRequestFaultType unsupportedPolicyRequestFault) {
        super(message);
        this.unsupportedPolicyRequestFault = unsupportedPolicyRequestFault;
    }

    public UnsupportedPolicyRequestFault(String message, UnsupportedPolicyRequestFaultType unsupportedPolicyRequestFault, Throwable cause) {
        super(message, cause);
        this.unsupportedPolicyRequestFault = unsupportedPolicyRequestFault;
    }

    public UnsupportedPolicyRequestFaultType getFaultInfo() {
        return this.unsupportedPolicyRequestFault;
    }
}

