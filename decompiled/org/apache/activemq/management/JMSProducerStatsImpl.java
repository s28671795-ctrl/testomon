/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.management;

import javax.jms.Destination;
import org.apache.activemq.management.CountStatisticImpl;
import org.apache.activemq.management.JMSEndpointStatsImpl;
import org.apache.activemq.management.JMSSessionStatsImpl;
import org.apache.activemq.management.TimeStatisticImpl;
import org.apache.activemq.util.IndentPrinter;

public class JMSProducerStatsImpl
extends JMSEndpointStatsImpl {
    private String destination;

    public JMSProducerStatsImpl(JMSSessionStatsImpl sessionStats, Destination destination) {
        super(sessionStats);
        if (destination != null) {
            this.destination = destination.toString();
        }
    }

    public JMSProducerStatsImpl(CountStatisticImpl messageCount, CountStatisticImpl pendingMessageCount, CountStatisticImpl expiredMessageCount, TimeStatisticImpl messageWaitTime, TimeStatisticImpl messageRateTime, String destination) {
        super(messageCount, pendingMessageCount, expiredMessageCount, messageWaitTime, messageRateTime);
        this.destination = destination;
    }

    public String getDestination() {
        return this.destination;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("producer ");
        buffer.append(this.destination);
        buffer.append(" { ");
        buffer.append(super.toString());
        buffer.append(" }");
        return buffer.toString();
    }

    @Override
    public void dump(IndentPrinter out) {
        out.printIndent();
        out.print("producer ");
        out.print(this.destination);
        out.println(" {");
        out.incrementIndent();
        super.dump(out);
        out.decrementIndent();
        out.printIndent();
        out.println("}");
    }
}

