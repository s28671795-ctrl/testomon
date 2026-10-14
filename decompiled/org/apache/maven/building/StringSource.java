/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.building;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.maven.building.Source;

public class StringSource
implements Source {
    private String content;
    private String location;

    public StringSource(CharSequence content) {
        this(content, null);
    }

    public StringSource(CharSequence content, String location) {
        this.content = content != null ? content.toString() : "";
        this.location = location != null ? location : "(memory)";
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.content.getBytes("UTF-8"));
    }

    @Override
    public String getLocation() {
        return this.location;
    }

    public String getContent() {
        return this.content;
    }

    public String toString() {
        return this.getLocation();
    }
}

