/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.io.IOException;
import javax.jms.JMSException;

public class ConnectionFailedException
extends JMSException {
    private static final long serialVersionUID = 2288453203492073973L;

    public ConnectionFailedException(IOException cause) {
        super("The JMS connection has failed: " + ConnectionFailedException.extractMessage(cause));
        this.initCause(cause);
        this.setLinkedException(cause);
    }

    public ConnectionFailedException() {
        super("The JMS connection has failed due to a Transport problem");
    }

    private static String extractMessage(IOException cause) {
        String m4 = cause.getMessage();
        if (m4 == null || m4.length() == 0) {
            m4 = cause.toString();
        }
        return m4;
    }
}

