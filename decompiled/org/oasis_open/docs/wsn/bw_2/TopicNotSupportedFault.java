/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.TopicNotSupportedFaultType;

@WebFault(name="TopicNotSupportedFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class TopicNotSupportedFault
extends Exception {
    private TopicNotSupportedFaultType topicNotSupportedFault;

    public TopicNotSupportedFault() {
    }

    public TopicNotSupportedFault(String message) {
        super(message);
    }

    public TopicNotSupportedFault(String message, Throwable cause) {
        super(message, cause);
    }

    public TopicNotSupportedFault(String message, TopicNotSupportedFaultType topicNotSupportedFault) {
        super(message);
        this.topicNotSupportedFault = topicNotSupportedFault;
    }

    public TopicNotSupportedFault(String message, TopicNotSupportedFaultType topicNotSupportedFault, Throwable cause) {
        super(message, cause);
        this.topicNotSupportedFault = topicNotSupportedFault;
    }

    public TopicNotSupportedFaultType getFaultInfo() {
        return this.topicNotSupportedFault;
    }
}

