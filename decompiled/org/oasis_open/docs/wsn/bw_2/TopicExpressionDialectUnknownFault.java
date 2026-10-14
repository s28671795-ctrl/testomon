/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;
import org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFaultType;

@WebFault(name="TopicExpressionDialectUnknownFault", targetNamespace="http://docs.oasis-open.org/wsn/b-2")
public class TopicExpressionDialectUnknownFault
extends Exception {
    private TopicExpressionDialectUnknownFaultType topicExpressionDialectUnknownFault;

    public TopicExpressionDialectUnknownFault() {
    }

    public TopicExpressionDialectUnknownFault(String message) {
        super(message);
    }

    public TopicExpressionDialectUnknownFault(String message, Throwable cause) {
        super(message, cause);
    }

    public TopicExpressionDialectUnknownFault(String message, TopicExpressionDialectUnknownFaultType topicExpressionDialectUnknownFault) {
        super(message);
        this.topicExpressionDialectUnknownFault = topicExpressionDialectUnknownFault;
    }

    public TopicExpressionDialectUnknownFault(String message, TopicExpressionDialectUnknownFaultType topicExpressionDialectUnknownFault, Throwable cause) {
        super(message, cause);
        this.topicExpressionDialectUnknownFault = topicExpressionDialectUnknownFault;
    }

    public TopicExpressionDialectUnknownFaultType getFaultInfo() {
        return this.topicExpressionDialectUnknownFault;
    }
}

