/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.brw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFaultType;

@WebFault(name="PublisherRegistrationRejectedFault", targetNamespace="http://docs.oasis-open.org/wsn/br-2")
public class PublisherRegistrationRejectedFault
extends Exception {
    public static final long serialVersionUID = 1L;
    private PublisherRegistrationRejectedFaultType faultInfo;

    public PublisherRegistrationRejectedFault() {
    }

    public PublisherRegistrationRejectedFault(String message) {
        super(message);
    }

    public PublisherRegistrationRejectedFault(String message, Throwable cause) {
        super(message, cause);
    }

    public PublisherRegistrationRejectedFault(String message, PublisherRegistrationRejectedFaultType publisherRegistrationRejectedFault) {
        super(message);
        this.faultInfo = publisherRegistrationRejectedFault;
    }

    public PublisherRegistrationRejectedFault(String message, PublisherRegistrationRejectedFaultType publisherRegistrationRejectedFault, Throwable cause) {
        super(message, cause);
        this.faultInfo = publisherRegistrationRejectedFault;
    }

    public PublisherRegistrationRejectedFaultType getFaultInfo() {
        return this.faultInfo;
    }
}

