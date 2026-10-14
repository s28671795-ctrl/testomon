/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.blob;

import java.net.MalformedURLException;
import java.net.URL;
import javax.jms.JMSException;
import org.apache.activemq.blob.BlobTransferPolicy;
import org.apache.activemq.command.ActiveMQBlobMessage;

public class DefaultStrategy {
    protected BlobTransferPolicy transferPolicy;

    public DefaultStrategy(BlobTransferPolicy transferPolicy) {
        this.transferPolicy = transferPolicy;
    }

    protected boolean isSuccessfulCode(int responseCode) {
        return responseCode >= 200 && responseCode < 300;
    }

    protected URL createMessageURL(ActiveMQBlobMessage message) throws JMSException, MalformedURLException {
        return new URL(this.transferPolicy.getUploadUrl() + message.getMessageId().toString());
    }
}

