/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import javax.jms.JMSException;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.command.ActiveMQDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ActiveMQTempDestination
extends ActiveMQDestination {
    private static final Logger LOG = LoggerFactory.getLogger(ActiveMQTempDestination.class);
    protected transient ActiveMQConnection connection;
    protected transient String connectionId;
    protected transient int sequenceId;

    public ActiveMQTempDestination() {
    }

    public ActiveMQTempDestination(String name) {
        super(name);
    }

    public ActiveMQTempDestination(String connectionId, long sequenceId) {
        super(connectionId + ":" + sequenceId);
    }

    @Override
    public boolean isTemporary() {
        return true;
    }

    public void delete() throws JMSException {
        if (this.connection != null) {
            this.connection.deleteTempDestination(this);
        }
    }

    public ActiveMQConnection getConnection() {
        return this.connection;
    }

    public void setConnection(ActiveMQConnection connection) {
        this.connection = connection;
    }

    @Override
    public void setPhysicalName(String physicalName) {
        String seqStr;
        int p;
        super.setPhysicalName(physicalName);
        if (!this.isComposite() && (p = this.physicalName.lastIndexOf(":")) >= 0 && (seqStr = this.physicalName.substring(p + 1).trim()) != null && seqStr.length() > 0) {
            try {
                this.sequenceId = Integer.parseInt(seqStr);
            }
            catch (NumberFormatException e) {
                LOG.debug("Did not parse sequence Id from " + physicalName);
            }
            this.connectionId = this.physicalName.substring(0, p);
        }
    }

    public String getConnectionId() {
        return this.connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public int getSequenceId() {
        return this.sequenceId;
    }
}

