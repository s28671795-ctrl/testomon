/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.util;

import java.io.IOException;
import org.apache.activemq.broker.BrokerPluginSupport;
import org.apache.activemq.command.MessageDispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TraceBrokerPathPlugin
extends BrokerPluginSupport {
    private String stampProperty = "BrokerPath";
    private static final Logger LOG = LoggerFactory.getLogger(TraceBrokerPathPlugin.class);

    public String getStampProperty() {
        return this.stampProperty;
    }

    public void setStampProperty(String stampProperty) {
        if (stampProperty != null && !stampProperty.isEmpty()) {
            this.stampProperty = stampProperty;
        }
    }

    @Override
    public void preProcessDispatch(MessageDispatch messageDispatch) {
        try {
            if (messageDispatch != null && messageDispatch.getMessage() != null) {
                String brokerStamp = (String)messageDispatch.getMessage().getProperty(this.getStampProperty());
                brokerStamp = brokerStamp == null ? this.getBrokerName() : brokerStamp + "," + this.getBrokerName();
                messageDispatch.getMessage().setProperty(this.getStampProperty(), brokerStamp);
                messageDispatch.getMessage().setMarshalledProperties(null);
            }
        }
        catch (IOException ioe) {
            LOG.warn("Setting broker property failed", ioe);
        }
        super.preProcessDispatch(messageDispatch);
    }
}

