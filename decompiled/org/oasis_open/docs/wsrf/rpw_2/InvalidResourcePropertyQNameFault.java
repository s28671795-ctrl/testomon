/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.rpw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsrf.rp_2.InvalidResourcePropertyQNameFaultType;

@WebFault(name="InvalidResourcePropertyQNameFault", targetNamespace="http://docs.oasis-open.org/wsrf/rp-2")
public class InvalidResourcePropertyQNameFault
extends Exception {
    public static final long serialVersionUID = 1L;
    private InvalidResourcePropertyQNameFaultType faultInfo;

    public InvalidResourcePropertyQNameFault() {
    }

    public InvalidResourcePropertyQNameFault(String message) {
        super(message);
    }

    public InvalidResourcePropertyQNameFault(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidResourcePropertyQNameFault(String message, InvalidResourcePropertyQNameFaultType invalidResourcePropertyQNameFault) {
        super(message);
        this.faultInfo = invalidResourcePropertyQNameFault;
    }

    public InvalidResourcePropertyQNameFault(String message, InvalidResourcePropertyQNameFaultType invalidResourcePropertyQNameFault, Throwable cause) {
        super(message, cause);
        this.faultInfo = invalidResourcePropertyQNameFault;
    }

    public InvalidResourcePropertyQNameFaultType getFaultInfo() {
        return this.faultInfo;
    }
}

