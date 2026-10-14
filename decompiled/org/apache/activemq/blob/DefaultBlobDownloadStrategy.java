/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.blob;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.jms.JMSException;
import org.apache.activemq.blob.BlobDownloadStrategy;
import org.apache.activemq.blob.BlobTransferPolicy;
import org.apache.activemq.blob.DefaultStrategy;
import org.apache.activemq.command.ActiveMQBlobMessage;

public class DefaultBlobDownloadStrategy
extends DefaultStrategy
implements BlobDownloadStrategy {
    public DefaultBlobDownloadStrategy(BlobTransferPolicy transferPolicy) {
        super(transferPolicy);
    }

    @Override
    public InputStream getInputStream(ActiveMQBlobMessage message) throws IOException, JMSException {
        URL value = message.getURL();
        if (value == null) {
            return null;
        }
        return value.openStream();
    }

    @Override
    public void deleteFile(ActiveMQBlobMessage message) throws IOException, JMSException {
        URL url2 = this.createMessageURL(message);
        HttpURLConnection connection = (HttpURLConnection)url2.openConnection();
        connection.setRequestMethod("DELETE");
        try {
            connection.connect();
        }
        catch (IOException e) {
            throw new IOException("DELETE failed on: " + url2, e);
        }
        finally {
            connection.disconnect();
        }
        if (!this.isSuccessfulCode(connection.getResponseCode())) {
            throw new IOException("DELETE was not successful: " + connection.getResponseCode() + " " + connection.getResponseMessage());
        }
    }
}

