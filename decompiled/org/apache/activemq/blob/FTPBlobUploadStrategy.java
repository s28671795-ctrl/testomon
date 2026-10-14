/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.blob;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.jms.JMSException;
import org.apache.activemq.blob.BlobTransferPolicy;
import org.apache.activemq.blob.BlobUploadStrategy;
import org.apache.activemq.blob.FTPStrategy;
import org.apache.activemq.command.ActiveMQBlobMessage;
import org.apache.commons.net.ftp.FTPClient;

public class FTPBlobUploadStrategy
extends FTPStrategy
implements BlobUploadStrategy {
    public FTPBlobUploadStrategy(BlobTransferPolicy transferPolicy) throws MalformedURLException {
        super(transferPolicy);
    }

    @Override
    public URL uploadFile(ActiveMQBlobMessage message, File file) throws JMSException, IOException {
        try (FileInputStream fis = new FileInputStream(file);){
            URL uRL = this.uploadStream(message, fis);
            return uRL;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public URL uploadStream(ActiveMQBlobMessage message, InputStream in) throws JMSException, IOException {
        FTPClient ftp = this.createFTP();
        try {
            String path = this.url.getPath();
            String workingDir = path.substring(0, path.lastIndexOf("/"));
            String filename = message.getMessageId().toString().replaceAll(":", "_");
            ftp.setFileType(2);
            String url2 = !ftp.changeWorkingDirectory(workingDir) ? this.url.toString().replaceFirst(this.url.getPath(), "") + "/" : this.url.toString();
            if (!ftp.storeFile(filename, in)) {
                throw new JMSException("FTP store failed: " + ftp.getReplyString());
            }
            URL uRL = new URL(url2 + filename);
            return uRL;
        }
        finally {
            ftp.quit();
            ftp.disconnect();
        }
    }
}

