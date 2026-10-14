/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.UnableToCreatePullPointFaultType;

@WebFault(name="UnableToCreatePullPointFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class UnableToCreatePullPointFault
extends Exception {
    public static final long serialVersionUID = 1L;
    private UnableToCreatePullPointFaultType faultInfo;

    public UnableToCreatePullPointFault() {
    }

    public UnableToCreatePullPointFault(String message) {
        super(message);
    }

    public UnableToCreatePullPointFault(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToCreatePullPointFault(String message, UnableToCreatePullPointFaultType unableToCreatePullPointFault) {
        super(message);
        this.faultInfo = unableToCreatePullPointFault;
    }

    public UnableToCreatePullPointFault(String message, UnableToCreatePullPointFaultType unableToCreatePullPointFault, Throwable cause) {
        super(message, cause);
        this.faultInfo = unableToCreatePullPointFault;
    }

    public UnableToCreatePullPointFaultType getFaultInfo() {
        return this.faultInfo;
    }
}

