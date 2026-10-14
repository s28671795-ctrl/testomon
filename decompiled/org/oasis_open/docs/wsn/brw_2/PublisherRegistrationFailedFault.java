/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.brw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFaultType;

@WebFault(name="PublisherRegistrationFailedFault", targetNamespace="http://docs.oasis-open.org/wsn/br-2")
public class PublisherRegistrationFailedFault
extends Exception {
    public static final long serialVersionUID = 1L;
    private PublisherRegistrationFailedFaultType faultInfo;

    public PublisherRegistrationFailedFault() {
    }

    public PublisherRegistrationFailedFault(String message) {
        super(message);
    }

    public PublisherRegistrationFailedFault(String message, Throwable cause) {
        super(message, cause);
    }

    public PublisherRegistrationFailedFault(String message, PublisherRegistrationFailedFaultType publisherRegistrationFailedFault) {
        super(message);
        this.faultInfo = publisherRegistrationFailedFault;
    }

    public PublisherRegistrationFailedFault(String message, PublisherRegistrationFailedFaultType publisherRegistrationFailedFault, Throwable cause) {
        super(message, cause);
        this.faultInfo = publisherRegistrationFailedFault;
    }

    public PublisherRegistrationFailedFaultType getFaultInfo() {
        return this.faultInfo;
    }
}

