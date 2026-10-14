/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.blob;

import java.io.IOException;
import java.io.InputStream;
import javax.jms.JMSException;
import org.apache.activemq.command.ActiveMQBlobMessage;

public interface BlobDownloadStrategy {
    public InputStream getInputStream(ActiveMQBlobMessage var1) throws IOException, JMSException;

    public void deleteFile(ActiveMQBlobMessage var1) throws IOException, JMSException;
}

