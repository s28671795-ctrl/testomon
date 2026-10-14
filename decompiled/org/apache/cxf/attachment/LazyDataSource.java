/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.attachment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.activation.DataSource;
import org.apache.cxf.message.Attachment;

public class LazyDataSource
implements DataSource {
    private DataSource dataSource;
    private Collection<Attachment> attachments;
    private String id;

    public LazyDataSource(String id, Collection<Attachment> attachments) {
        this.id = id;
        this.attachments = attachments;
    }

    private synchronized void load() {
        if (this.dataSource == null) {
            for (Attachment a : this.attachments) {
                if (!a.getId().equals(this.id)) continue;
                this.dataSource = a.getDataHandler().getDataSource();
                if (this.dataSource != null) break;
                throw new IllegalStateException("Could not get DataSource for attachment of id " + this.id);
            }
        }
        if (this.dataSource == null) {
            throw new IllegalStateException("No attachment for id " + this.id + " found in " + this.attachments.stream().map(Attachment::getId).collect(Collectors.toList()));
        }
    }

    @Override
    public String getContentType() {
        this.load();
        return this.dataSource.getContentType();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        this.load();
        return this.dataSource.getInputStream();
    }

    @Override
    public String getName() {
        this.load();
        return this.dataSource.getName();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        this.load();
        return this.dataSource.getOutputStream();
    }

    public DataSource getDataSource() {
        this.load();
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}

