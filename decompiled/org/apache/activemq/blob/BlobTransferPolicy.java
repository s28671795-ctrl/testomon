/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.blob;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.activemq.blob.BlobDownloadStrategy;
import org.apache.activemq.blob.BlobUploadStrategy;
import org.apache.activemq.blob.DefaultBlobDownloadStrategy;
import org.apache.activemq.blob.DefaultBlobUploadStrategy;
import org.apache.activemq.blob.FTPBlobDownloadStrategy;
import org.apache.activemq.blob.FTPBlobUploadStrategy;
import org.apache.activemq.blob.FileSystemBlobStrategy;

public class BlobTransferPolicy {
    private String defaultUploadUrl = "http://localhost:8080/uploads/";
    private String brokerUploadUrl;
    private String uploadUrl;
    private int bufferSize = 131072;
    private BlobUploadStrategy uploadStrategy;
    private BlobDownloadStrategy downloadStrategy;

    public BlobTransferPolicy copy() {
        BlobTransferPolicy that = new BlobTransferPolicy();
        that.defaultUploadUrl = this.defaultUploadUrl;
        that.brokerUploadUrl = this.brokerUploadUrl;
        that.uploadUrl = this.uploadUrl;
        that.bufferSize = this.bufferSize;
        that.uploadStrategy = this.uploadStrategy;
        that.downloadStrategy = this.downloadStrategy;
        return that;
    }

    public String getUploadUrl() {
        if (this.uploadUrl == null) {
            this.uploadUrl = this.getBrokerUploadUrl();
            if (this.uploadUrl == null) {
                this.uploadUrl = this.getDefaultUploadUrl();
            }
        }
        return this.uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getBrokerUploadUrl() {
        return this.brokerUploadUrl;
    }

    public void setBrokerUploadUrl(String brokerUploadUrl) {
        this.brokerUploadUrl = brokerUploadUrl;
    }

    public String getDefaultUploadUrl() {
        return this.defaultUploadUrl;
    }

    public void setDefaultUploadUrl(String defaultUploadUrl) {
        this.defaultUploadUrl = defaultUploadUrl;
    }

    public BlobUploadStrategy getUploadStrategy() {
        if (this.uploadStrategy == null) {
            this.uploadStrategy = this.createUploadStrategy();
        }
        return this.uploadStrategy;
    }

    public BlobDownloadStrategy getDownloadStrategy() {
        if (this.downloadStrategy == null) {
            this.downloadStrategy = this.createDownloadStrategy();
        }
        return this.downloadStrategy;
    }

    public void setUploadStrategy(BlobUploadStrategy uploadStrategy) {
        this.uploadStrategy = uploadStrategy;
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    protected BlobUploadStrategy createUploadStrategy() {
        BlobUploadStrategy strategy;
        try {
            URL url2 = new URL(this.getUploadUrl());
            strategy = url2.getProtocol().equalsIgnoreCase("FTP") ? new FTPBlobUploadStrategy(this) : (url2.getProtocol().equalsIgnoreCase("FILE") ? new FileSystemBlobStrategy(this) : new DefaultBlobUploadStrategy(this));
        }
        catch (MalformedURLException e) {
            strategy = new DefaultBlobUploadStrategy(this);
        }
        catch (URISyntaxException e) {
            strategy = new DefaultBlobUploadStrategy(this);
        }
        return strategy;
    }

    protected BlobDownloadStrategy createDownloadStrategy() {
        BlobDownloadStrategy strategy;
        try {
            URL url2 = new URL(this.getUploadUrl());
            strategy = url2.getProtocol().equalsIgnoreCase("FTP") ? new FTPBlobDownloadStrategy(this) : (url2.getProtocol().equalsIgnoreCase("FILE") ? new FileSystemBlobStrategy(this) : new DefaultBlobDownloadStrategy(this));
        }
        catch (MalformedURLException e) {
            strategy = new DefaultBlobDownloadStrategy(this);
        }
        catch (URISyntaxException e) {
            strategy = new DefaultBlobDownloadStrategy(this);
        }
        return strategy;
    }
}

