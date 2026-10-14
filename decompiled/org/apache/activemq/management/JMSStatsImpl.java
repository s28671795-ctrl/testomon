/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.management;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.management.JMSConnectionStatsImpl;
import org.apache.activemq.management.StatsImpl;
import org.apache.activemq.util.IndentPrinter;

public class JMSStatsImpl
extends StatsImpl {
    private List<ActiveMQConnection> connections = new CopyOnWriteArrayList<ActiveMQConnection>();

    public JMSConnectionStatsImpl[] getConnections() {
        Object[] connectionArray = this.connections.toArray();
        int size = connectionArray.length;
        JMSConnectionStatsImpl[] answer = new JMSConnectionStatsImpl[size];
        for (int i = 0; i < size; ++i) {
            ActiveMQConnection connection = (ActiveMQConnection)connectionArray[i];
            answer[i] = connection.getConnectionStats();
        }
        return answer;
    }

    public void addConnection(ActiveMQConnection connection) {
        this.connections.add(connection);
    }

    public void removeConnection(ActiveMQConnection connection) {
        this.connections.remove(connection);
    }

    public void dump(IndentPrinter out) {
        out.printIndent();
        out.println("factory {");
        out.incrementIndent();
        JMSConnectionStatsImpl[] array = this.getConnections();
        for (int i = 0; i < array.length; ++i) {
            JMSConnectionStatsImpl connectionStat = array[i];
            connectionStat.dump(out);
        }
        out.decrementIndent();
        out.printIndent();
        out.println("}");
        out.flush();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        JMSConnectionStatsImpl[] stats = this.getConnections();
        int size = stats.length;
        for (int i = 0; i < size; ++i) {
            stats[i].setEnabled(enabled);
        }
    }
}

