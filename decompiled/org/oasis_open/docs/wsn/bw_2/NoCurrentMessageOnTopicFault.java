/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.NoCurrentMessageOnTopicFaultType;

@WebFault(name="NoCurrentMessageOnTopicFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class NoCurrentMessageOnTopicFault
extends Exception {
    public static final long serialVersionUID = 1L;
    private NoCurrentMessageOnTopicFaultType faultInfo;

    public NoCurrentMessageOnTopicFault() {
    }

    public NoCurrentMessageOnTopicFault(String message) {
        super(message);
    }

    public NoCurrentMessageOnTopicFault(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCurrentMessageOnTopicFault(String message, NoCurrentMessageOnTopicFaultType noCurrentMessageOnTopicFault) {
        super(message);
        this.faultInfo = noCurrentMessageOnTopicFault;
    }

    public NoCurrentMessageOnTopicFault(String message, NoCurrentMessageOnTopicFaultType noCurrentMessageOnTopicFault, Throwable cause) {
        super(message, cause);
        this.faultInfo = noCurrentMessageOnTopicFault;
    }

    public NoCurrentMessageOnTopicFaultType getFaultInfo() {
        return this.faultInfo;
    }
}

