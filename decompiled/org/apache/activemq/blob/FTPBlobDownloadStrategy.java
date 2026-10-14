/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.blob;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import javax.jms.JMSException;
import org.apache.activemq.blob.BlobDownloadStrategy;
import org.apache.activemq.blob.BlobTransferPolicy;
import org.apache.activemq.blob.FTPStrategy;
import org.apache.activemq.command.ActiveMQBlobMessage;
import org.apache.commons.net.ftp.FTPClient;

public class FTPBlobDownloadStrategy
extends FTPStrategy
implements BlobDownloadStrategy {
    public FTPBlobDownloadStrategy(BlobTransferPolicy transferPolicy) throws MalformedURLException {
        super(transferPolicy);
    }

    @Override
    public InputStream getInputStream(ActiveMQBlobMessage message) throws IOException, JMSException {
        this.url = message.getURL();
        final FTPClient ftp = this.createFTP();
        String path = this.url.getPath();
        String workingDir = path.substring(0, path.lastIndexOf("/"));
        String file = path.substring(path.lastIndexOf("/") + 1);
        ftp.changeWorkingDirectory(workingDir);
        ftp.setFileType(2);
        FilterInputStream input = new FilterInputStream(ftp.retrieveFileStream(file)){

            @Override
            public void close() throws IOException {
                this.in.close();
                ftp.quit();
                ftp.disconnect();
            }
        };
        return input;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deleteFile(ActiveMQBlobMessage message) throws IOException, JMSException {
        this.url = message.getURL();
        FTPClient ftp = this.createFTP();
        String path = this.url.getPath();
        try {
            if (!ftp.deleteFile(path)) {
                throw new JMSException("Delete file failed: " + ftp.getReplyString());
            }
        }
        finally {
            ftp.quit();
            ftp.disconnect();
        }
    }
}

