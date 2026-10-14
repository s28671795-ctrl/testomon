/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.UnableToGetMessagesFaultType;

@WebFault(name="UnableToGetMessagesFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class UnableToGetMessagesFault
extends Exception {
    public static final long serialVersionUID = 1L;
    private UnableToGetMessagesFaultType faultInfo;

    public UnableToGetMessagesFault() {
    }

    public UnableToGetMessagesFault(String message) {
        super(message);
    }

    public UnableToGetMessagesFault(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToGetMessagesFault(String message, UnableToGetMessagesFaultType unableToGetMessagesFault) {
        super(message);
        this.faultInfo = unableToGetMessagesFault;
    }

    public UnableToGetMessagesFault(String message, UnableToGetMessagesFaultType unableToGetMessagesFault, Throwable cause) {
        super(message, cause);
        this.faultInfo = unableToGetMessagesFault;
    }

    public UnableToGetMessagesFaultType getFaultInfo() {
        return this.faultInfo;
    }
}

