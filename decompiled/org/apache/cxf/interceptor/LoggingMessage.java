/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.util.concurrent.atomic.AtomicInteger;

@Deprecated
public final class LoggingMessage {
    public static final String ID_KEY = LoggingMessage.class.getName() + ".ID";
    private static final AtomicInteger ID = new AtomicInteger();
    private final String heading;
    private final StringBuilder address;
    private final StringBuilder contentType;
    private final StringBuilder encoding;
    private final StringBuilder httpMethod;
    private final StringBuilder header;
    private final StringBuilder message;
    private final StringBuilder payload;
    private final StringBuilder responseCode;
    private final String id;

    public LoggingMessage(String h2, String i) {
        this.heading = h2;
        this.id = i;
        this.contentType = new StringBuilder();
        this.address = new StringBuilder();
        this.encoding = new StringBuilder();
        this.httpMethod = new StringBuilder();
        this.header = new StringBuilder();
        this.message = new StringBuilder();
        this.payload = new StringBuilder();
        this.responseCode = new StringBuilder();
    }

    public String getId() {
        return this.id;
    }

    public static String nextId() {
        return Integer.toString(ID.incrementAndGet());
    }

    public StringBuilder getAddress() {
        return this.address;
    }

    public StringBuilder getEncoding() {
        return this.encoding;
    }

    public StringBuilder getHeader() {
        return this.header;
    }

    public StringBuilder getHttpMethod() {
        return this.httpMethod;
    }

    public StringBuilder getContentType() {
        return this.contentType;
    }

    public StringBuilder getMessage() {
        return this.message;
    }

    public StringBuilder getPayload() {
        return this.payload;
    }

    public StringBuilder getResponseCode() {
        return this.responseCode;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(128);
        buffer.append(this.heading);
        buffer.append("\nID: ").append(this.id);
        if (this.address.length() > 0) {
            buffer.append("\nAddress: ");
            buffer.append((CharSequence)this.address);
        }
        if (this.responseCode.length() > 0) {
            buffer.append("\nResponse-Code: ");
            buffer.append((CharSequence)this.responseCode);
        }
        if (this.encoding.length() > 0) {
            buffer.append("\nEncoding: ");
            buffer.append((CharSequence)this.encoding);
        }
        if (this.httpMethod.length() > 0) {
            buffer.append("\nHttp-Method: ");
            buffer.append((CharSequence)this.httpMethod);
        }
        buffer.append("\nContent-Type: ");
        buffer.append((CharSequence)this.contentType);
        buffer.append("\nHeaders: ");
        buffer.append((CharSequence)this.header);
        if (this.message.length() > 0) {
            buffer.append("\nMessages: ");
            buffer.append((CharSequence)this.message);
        }
        if (this.payload.length() > 0) {
            buffer.append("\nPayload: ");
            buffer.append((CharSequence)this.payload);
        }
        buffer.append("\n--------------------------------------");
        return buffer.toString();
    }
}

