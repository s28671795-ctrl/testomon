/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.blob;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.jms.JMSException;
import org.apache.activemq.blob.BlobDownloadStrategy;
import org.apache.activemq.blob.BlobTransferPolicy;
import org.apache.activemq.blob.BlobUploadStrategy;
import org.apache.activemq.command.ActiveMQBlobMessage;

public class FileSystemBlobStrategy
implements BlobUploadStrategy,
BlobDownloadStrategy {
    private final BlobTransferPolicy policy;
    private File rootFile;

    public FileSystemBlobStrategy(BlobTransferPolicy policy) throws MalformedURLException, URISyntaxException {
        this.policy = policy;
        this.createRootFolder();
    }

    protected void createRootFolder() throws MalformedURLException, URISyntaxException {
        this.rootFile = new File(new URL(this.policy.getUploadUrl()).toURI());
        if (!this.rootFile.exists()) {
            this.rootFile.mkdirs();
        } else if (!this.rootFile.isDirectory()) {
            throw new IllegalArgumentException("Given url is not a directory " + this.rootFile);
        }
    }

    @Override
    public URL uploadFile(ActiveMQBlobMessage message, File file) throws JMSException, IOException {
        try (FileInputStream fis = new FileInputStream(file);){
            URL uRL = this.uploadStream(message, fis);
            return uRL;
        }
    }

    @Override
    public URL uploadStream(ActiveMQBlobMessage message, InputStream in) throws JMSException, IOException {
        File f = this.getFile(message);
        try (FileOutputStream out = new FileOutputStream(f);){
            byte[] buffer = new byte[this.policy.getBufferSize()];
            int c = in.read(buffer);
            while (c != -1) {
                out.write(buffer, 0, c);
                out.flush();
                c = in.read(buffer);
            }
        }
        return f.toURI().toURL();
    }

    @Override
    public void deleteFile(ActiveMQBlobMessage message) throws IOException, JMSException {
        File f = this.getFile(message);
        if (f.exists() && !f.delete()) {
            throw new IOException("Unable to delete file " + f);
        }
    }

    @Override
    public InputStream getInputStream(ActiveMQBlobMessage message) throws IOException, JMSException {
        return new FileInputStream(this.getFile(message));
    }

    protected File getFile(ActiveMQBlobMessage message) throws JMSException, IOException {
        if (message.getURL() != null) {
            try {
                return new File(message.getURL().toURI());
            }
            catch (URISyntaxException e) {
                IOException ioe = new IOException("Unable to open file for message " + message);
                ioe.initCause(e);
            }
        }
        String fileName = message.getJMSMessageID().replaceAll(":", "_");
        return new File(this.rootFile, fileName);
    }
}

