/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.blob;

import java.io.IOException;
import java.io.InputStream;
import javax.jms.JMSException;
import org.apache.activemq.blob.BlobDownloadStrategy;
import org.apache.activemq.blob.BlobTransferPolicy;
import org.apache.activemq.command.ActiveMQBlobMessage;

public class BlobDownloader {
    private final BlobTransferPolicy blobTransferPolicy;

    public BlobDownloader(BlobTransferPolicy transferPolicy) {
        this.blobTransferPolicy = transferPolicy.copy();
    }

    public InputStream getInputStream(ActiveMQBlobMessage message) throws IOException, JMSException {
        return this.getStrategy().getInputStream(message);
    }

    public void deleteFile(ActiveMQBlobMessage message) throws IOException, JMSException {
        this.getStrategy().deleteFile(message);
    }

    public BlobTransferPolicy getBlobTransferPolicy() {
        return this.blobTransferPolicy;
    }

    public BlobDownloadStrategy getStrategy() {
        return this.getBlobTransferPolicy().getDownloadStrategy();
    }
}

