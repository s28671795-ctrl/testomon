/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.rw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsrf.r_2.ResourceUnavailableFaultType;

@WebFault(name="ResourceUnavailableFault", targetNamespace="http://docs.oasis-open.org/wsrf/r-2")
public class ResourceUnavailableFault
extends Exception {
    public static final long serialVersionUID = 1L;
    private ResourceUnavailableFaultType faultInfo;

    public ResourceUnavailableFault() {
    }

    public ResourceUnavailableFault(String message) {
        super(message);
    }

    public ResourceUnavailableFault(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceUnavailableFault(String message, ResourceUnavailableFaultType resourceUnavailableFault) {
        super(message);
        this.faultInfo = resourceUnavailableFault;
    }

    public ResourceUnavailableFault(String message, ResourceUnavailableFaultType resourceUnavailableFault, Throwable cause) {
        super(message, cause);
        this.faultInfo = resourceUnavailableFault;
    }

    public ResourceUnavailableFaultType getFaultInfo() {
        return this.faultInfo;
    }
}

