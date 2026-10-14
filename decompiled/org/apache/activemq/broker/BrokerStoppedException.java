/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

public class BrokerStoppedException
extends IllegalStateException {
    private static final long serialVersionUID = -3435230276850902220L;

    public BrokerStoppedException() {
    }

    public BrokerStoppedException(String message, Throwable cause) {
        super(message);
        this.initCause(cause);
    }

    public BrokerStoppedException(String s2) {
        super(s2);
    }

    public BrokerStoppedException(Throwable cause) {
        this.initCause(cause);
    }
}

