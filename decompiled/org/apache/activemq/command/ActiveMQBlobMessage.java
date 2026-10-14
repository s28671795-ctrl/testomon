/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.jms.JMSException;
import org.apache.activemq.BlobMessage;
import org.apache.activemq.blob.BlobDownloader;
import org.apache.activemq.blob.BlobUploader;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.Message;
import org.apache.activemq.util.JMSExceptionSupport;

public class ActiveMQBlobMessage
extends ActiveMQMessage
implements BlobMessage {
    public static final byte DATA_STRUCTURE_TYPE = 29;
    public static final String BINARY_MIME_TYPE = "application/octet-stream";
    private String remoteBlobUrl;
    private String mimeType;
    private String name;
    private boolean deletedByBroker;
    private transient BlobUploader blobUploader;
    private transient BlobDownloader blobDownloader;
    private transient URL url;

    @Override
    public Message copy() {
        ActiveMQBlobMessage copy = new ActiveMQBlobMessage();
        this.copy(copy);
        return copy;
    }

    private void copy(ActiveMQBlobMessage copy) {
        super.copy(copy);
        copy.setRemoteBlobUrl(this.getRemoteBlobUrl());
        copy.setMimeType(this.getMimeType());
        copy.setDeletedByBroker(this.isDeletedByBroker());
        copy.setBlobUploader(this.getBlobUploader());
        copy.setName(this.getName());
    }

    @Override
    public byte getDataStructureType() {
        return 29;
    }

    public String getRemoteBlobUrl() {
        return this.remoteBlobUrl;
    }

    public void setRemoteBlobUrl(String remoteBlobUrl) {
        this.remoteBlobUrl = remoteBlobUrl;
        this.url = null;
    }

    @Override
    public String getMimeType() {
        if (this.mimeType == null) {
            return BINARY_MIME_TYPE;
        }
        return this.mimeType;
    }

    @Override
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeletedByBroker() {
        return this.deletedByBroker;
    }

    public void setDeletedByBroker(boolean deletedByBroker) {
        this.deletedByBroker = deletedByBroker;
    }

    @Override
    public String getJMSXMimeType() {
        return this.getMimeType();
    }

    @Override
    public InputStream getInputStream() throws IOException, JMSException {
        if (this.blobDownloader == null) {
            return null;
        }
        return this.blobDownloader.getInputStream(this);
    }

    @Override
    public URL getURL() throws JMSException {
        if (this.url == null && this.remoteBlobUrl != null) {
            try {
                this.url = new URL(this.remoteBlobUrl);
            }
            catch (MalformedURLException e) {
                throw JMSExceptionSupport.create(e);
            }
        }
        return this.url;
    }

    public void setURL(URL url2) {
        this.url = url2;
        this.remoteBlobUrl = url2 != null ? url2.toExternalForm() : null;
    }

    public BlobUploader getBlobUploader() {
        return this.blobUploader;
    }

    public void setBlobUploader(BlobUploader blobUploader) {
        this.blobUploader = blobUploader;
    }

    public BlobDownloader getBlobDownloader() {
        return this.blobDownloader;
    }

    public void setBlobDownloader(BlobDownloader blobDownloader) {
        this.blobDownloader = blobDownloader;
    }

    @Override
    public void onSend() throws JMSException {
        super.onSend();
        if (this.blobUploader != null) {
            try {
                URL value = this.blobUploader.upload(this);
                this.setURL(value);
            }
            catch (IOException e) {
                throw JMSExceptionSupport.create(e);
            }
        }
    }

    public void deleteFile() throws IOException, JMSException {
        this.blobDownloader.deleteFile(this);
    }
}

