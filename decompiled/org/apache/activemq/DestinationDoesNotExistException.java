/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import javax.jms.JMSException;

public class DestinationDoesNotExistException
extends JMSException {
    public DestinationDoesNotExistException(String destination) {
        super(destination);
    }

    public boolean isTemporary() {
        return this.getMessage().startsWith("temp-");
    }

    @Override
    public String getLocalizedMessage() {
        return "The destination " + this.getMessage() + " does not exist.";
    }
}

