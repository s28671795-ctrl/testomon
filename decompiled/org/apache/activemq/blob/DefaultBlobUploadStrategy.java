/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.blob;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.jms.JMSException;
import org.apache.activemq.blob.BlobTransferPolicy;
import org.apache.activemq.blob.BlobUploadStrategy;
import org.apache.activemq.blob.DefaultStrategy;
import org.apache.activemq.command.ActiveMQBlobMessage;

public class DefaultBlobUploadStrategy
extends DefaultStrategy
implements BlobUploadStrategy {
    public DefaultBlobUploadStrategy(BlobTransferPolicy transferPolicy) {
        super(transferPolicy);
    }

    @Override
    public URL uploadFile(ActiveMQBlobMessage message, File file) throws JMSException, IOException {
        try (FileInputStream fis = new FileInputStream(file);){
            URL uRL = this.uploadStream(message, fis);
            return uRL;
        }
    }

    @Override
    public URL uploadStream(ActiveMQBlobMessage message, InputStream fis) throws JMSException, IOException {
        URL url2 = this.createMessageURL(message);
        HttpURLConnection connection = (HttpURLConnection)url2.openConnection();
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        connection.setChunkedStreamingMode(this.transferPolicy.getBufferSize());
        try (OutputStream os = connection.getOutputStream();){
            byte[] buf = new byte[this.transferPolicy.getBufferSize()];
            int c = fis.read(buf);
            while (c != -1) {
                os.write(buf, 0, c);
                os.flush();
                c = fis.read(buf);
            }
        }
        catch (IOException error) {
            throw new IOException("PUT failed to: " + url2, error);
        }
        if (!this.isSuccessfulCode(connection.getResponseCode())) {
            throw new IOException("PUT to " + url2 + " was not successful: " + connection.getResponseCode() + " " + connection.getResponseMessage());
        }
        return url2;
    }
}

