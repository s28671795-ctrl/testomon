/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.UnableToDestroyPullPointFaultType;

@WebFault(name="UnableToDestroyPullPointFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class UnableToDestroyPullPointFault
extends Exception {
    public static final long serialVersionUID = 1L;
    private UnableToDestroyPullPointFaultType faultInfo;

    public UnableToDestroyPullPointFault() {
    }

    public UnableToDestroyPullPointFault(String message) {
        super(message);
    }

    public UnableToDestroyPullPointFault(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToDestroyPullPointFault(String message, UnableToDestroyPullPointFaultType unableToDestroyPullPointFault) {
        super(message);
        this.faultInfo = unableToDestroyPullPointFault;
    }

    public UnableToDestroyPullPointFault(String message, UnableToDestroyPullPointFaultType unableToDestroyPullPointFault, Throwable cause) {
        super(message, cause);
        this.faultInfo = unableToDestroyPullPointFault;
    }

    public UnableToDestroyPullPointFaultType getFaultInfo() {
        return this.faultInfo;
    }
}

