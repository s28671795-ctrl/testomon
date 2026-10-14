/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.attachment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.DataSource;
import org.apache.cxf.attachment.AttachmentUtil;
import org.apache.cxf.attachment.DelegatingInputStream;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CacheSizeExceededException;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;

public class AttachmentDataSource
implements DataSource {
    private final String ct;
    private CachedOutputStream cache;
    private InputStream ins;
    private DelegatingInputStream delegate;
    private String name;

    public AttachmentDataSource(String ctParam, InputStream inParam) throws IOException {
        this.ct = ctParam;
        this.ins = inParam;
    }

    public boolean isCached() {
        return this.cache != null;
    }

    public void cache(Message message) throws IOException {
        if (this.cache == null) {
            this.cache = new CachedOutputStream();
            AttachmentUtil.setStreamedAttachmentProperties(message, this.cache);
            try {
                IOUtils.copyAndCloseInput(this.ins, this.cache);
                this.cache.lockOutputStream();
                if (this.delegate != null) {
                    this.delegate.setInputStream(this.cache.getInputStream());
                }
            }
            catch (IOException | CacheSizeExceededException cee) {
                this.cache.close();
                this.cache = null;
                throw cee;
            }
            finally {
                this.ins = null;
            }
        }
    }

    public void hold(Message message) throws IOException {
        this.cache(message);
        this.cache.holdTempFile();
    }

    public void release() {
        if (this.cache != null) {
            this.cache.releaseTempFileHold();
        }
    }

    @Override
    public String getContentType() {
        return this.ct;
    }

    @Override
    public InputStream getInputStream() {
        try {
            if (this.cache != null) {
                return this.cache.getInputStream();
            }
            if (this.delegate == null) {
                this.delegate = new DelegatingInputStream(this.ins);
            }
            return this.delegate;
        }
        catch (IOException e) {
            return null;
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException();
    }
}

