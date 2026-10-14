/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.brw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.br_2.ResourceNotDestroyedFaultType;

@WebFault(name="ResourceNotDestroyedFault", targetNamespace="http://docs.oasis-open.org/wsn/br-2")
public class ResourceNotDestroyedFault
extends Exception {
    public static final long serialVersionUID = 1L;
    private ResourceNotDestroyedFaultType faultInfo;

    public ResourceNotDestroyedFault() {
    }

    public ResourceNotDestroyedFault(String message) {
        super(message);
    }

    public ResourceNotDestroyedFault(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotDestroyedFault(String message, ResourceNotDestroyedFaultType resourceNotDestroyedFault) {
        super(message);
        this.faultInfo = resourceNotDestroyedFault;
    }

    public ResourceNotDestroyedFault(String message, ResourceNotDestroyedFaultType resourceNotDestroyedFault, Throwable cause) {
        super(message, cause);
        this.faultInfo = resourceNotDestroyedFault;
    }

    public ResourceNotDestroyedFaultType getFaultInfo() {
        return this.faultInfo;
    }
}

