/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.MultipleTopicsSpecifiedFaultType;

@WebFault(name="MultipleTopicsSpecifiedFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class MultipleTopicsSpecifiedFault
extends Exception {
    public static final long serialVersionUID = 1L;
    private MultipleTopicsSpecifiedFaultType faultInfo;

    public MultipleTopicsSpecifiedFault() {
    }

    public MultipleTopicsSpecifiedFault(String message) {
        super(message);
    }

    public MultipleTopicsSpecifiedFault(String message, Throwable cause) {
        super(message, cause);
    }

    public MultipleTopicsSpecifiedFault(String message, MultipleTopicsSpecifiedFaultType multipleTopicsSpecifiedFault) {
        super(message);
        this.faultInfo = multipleTopicsSpecifiedFault;
    }

    public MultipleTopicsSpecifiedFault(String message, MultipleTopicsSpecifiedFaultType multipleTopicsSpecifiedFault, Throwable cause) {
        super(message, cause);
        this.faultInfo = multipleTopicsSpecifiedFault;
    }

    public MultipleTopicsSpecifiedFaultType getFaultInfo() {
        return this.faultInfo;
    }
}

