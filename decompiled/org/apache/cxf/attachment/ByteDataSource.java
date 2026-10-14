/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.attachment;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.DataSource;

public class ByteDataSource
implements DataSource {
    private String contentType;
    private String name;
    private byte[] data;
    private int offset;
    private int length;

    public ByteDataSource(byte[] dataParam) {
        this(dataParam, 0, dataParam.length);
    }

    public ByteDataSource(byte[] dataParam, String ct) {
        this(dataParam, 0, dataParam.length);
        this.contentType = ct;
    }

    public ByteDataSource(byte[] dataParam, int offsetParam, int lengthParam) {
        this.data = dataParam;
        this.offset = offsetParam;
        this.length = lengthParam;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] dataParam) {
        this.data = dataParam;
    }

    public void setContentType(String contentTypeParam) {
        this.contentType = contentTypeParam;
    }

    public void setName(String nameParam) {
        this.name = nameParam;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.data, this.offset, this.length);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return null;
    }
}

