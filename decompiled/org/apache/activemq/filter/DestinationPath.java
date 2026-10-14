/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import java.util.ArrayList;
import javax.jms.JMSException;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;

public final class DestinationPath {
    protected static final char SEPARATOR = '.';

    private DestinationPath() {
    }

    public static String[] getDestinationPaths(String subject) {
        ArrayList<String> list = new ArrayList<String>();
        int previous = 0;
        int lastIndex = subject.length() - 1;
        while (true) {
            int idx;
            if ((idx = subject.indexOf(46, previous)) < 0) break;
            list.add(subject.substring(previous, idx));
            previous = idx + 1;
        }
        list.add(subject.substring(previous, lastIndex + 1));
        String[] answer = new String[list.size()];
        list.toArray(answer);
        return answer;
    }

    public static String[] getDestinationPaths(Message message) throws JMSException {
        return DestinationPath.getDestinationPaths(message.getDestination());
    }

    public static String[] getDestinationPaths(ActiveMQDestination destination) {
        return DestinationPath.getDestinationPaths(destination.getPhysicalName());
    }

    public static String toString(String[] paths) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < paths.length; ++i) {
            String path;
            if (i > 0) {
                buffer.append('.');
            }
            if ((path = paths[i]) == null) {
                buffer.append("*");
                continue;
            }
            buffer.append(path);
        }
        return buffer.toString();
    }
}

