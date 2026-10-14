/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.InvalidFilterFaultType;

@WebFault(name="InvalidFilterFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class InvalidFilterFault
extends Exception {
    private InvalidFilterFaultType invalidFilterFault;

    public InvalidFilterFault() {
    }

    public InvalidFilterFault(String message) {
        super(message);
    }

    public InvalidFilterFault(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFilterFault(String message, InvalidFilterFaultType invalidFilterFault) {
        super(message);
        this.invalidFilterFault = invalidFilterFault;
    }

    public InvalidFilterFault(String message, InvalidFilterFaultType invalidFilterFault, Throwable cause) {
        super(message, cause);
        this.invalidFilterFault = invalidFilterFault;
    }

    public InvalidFilterFaultType getFaultInfo() {
        return this.invalidFilterFault;
    }
}

