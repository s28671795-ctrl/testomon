/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.management;

import java.util.List;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.management.JMSSessionStatsImpl;
import org.apache.activemq.management.StatsImpl;
import org.apache.activemq.util.IndentPrinter;

public class JMSConnectionStatsImpl
extends StatsImpl {
    private List sessions;
    private boolean transactional;

    public JMSConnectionStatsImpl(List sessions, boolean transactional) {
        this.sessions = sessions;
        this.transactional = transactional;
    }

    public JMSSessionStatsImpl[] getSessions() {
        Object[] sessionArray = this.sessions.toArray();
        int size = sessionArray.length;
        JMSSessionStatsImpl[] answer = new JMSSessionStatsImpl[size];
        for (int i = 0; i < size; ++i) {
            ActiveMQSession session = (ActiveMQSession)sessionArray[i];
            answer[i] = session.getSessionStats();
        }
        return answer;
    }

    @Override
    public void reset() {
        super.reset();
        JMSSessionStatsImpl[] stats = this.getSessions();
        int size = stats.length;
        for (int i = 0; i < size; ++i) {
            stats[i].reset();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        JMSSessionStatsImpl[] stats = this.getSessions();
        int size = stats.length;
        for (int i = 0; i < size; ++i) {
            stats[i].setEnabled(enabled);
        }
    }

    public boolean isTransactional() {
        return this.transactional;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("connection{ ");
        JMSSessionStatsImpl[] array = this.getSessions();
        for (int i = 0; i < array.length; ++i) {
            if (i > 0) {
                buffer.append(", ");
            }
            buffer.append(Integer.toString(i));
            buffer.append(" = ");
            buffer.append(array[i]);
        }
        buffer.append(" }");
        return buffer.toString();
    }

    public void dump(IndentPrinter out) {
        out.printIndent();
        out.println("connection {");
        out.incrementIndent();
        JMSSessionStatsImpl[] array = this.getSessions();
        for (int i = 0; i < array.length; ++i) {
            JMSSessionStatsImpl sessionStat = array[i];
            out.printIndent();
            out.println("session {");
            out.incrementIndent();
            sessionStat.dump(out);
            out.decrementIndent();
            out.printIndent();
            out.println("}");
        }
        out.decrementIndent();
        out.printIndent();
        out.println("}");
        out.flush();
    }
}

