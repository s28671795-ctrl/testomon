/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.ext;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Attachment {
    private String id;
    private String mimeType;
    private final Map<String, String> headers = new HashMap<String, String>();
    private InputStream sourceStream;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public void addHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public InputStream getSourceStream() {
        return this.sourceStream;
    }

    public void setSourceStream(InputStream sourceStream) {
        this.sourceStream = sourceStream;
    }
}

