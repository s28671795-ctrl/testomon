/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SnowballObject {
    private String name;
    private InputStream stream;
    private long size;
    private ZonedDateTime modificationTime;
    private String filename;

    public SnowballObject(@Nonnull String name, @Nonnull InputStream stream, long size, @Nullable ZonedDateTime modificationTime) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name must be provided");
        }
        this.name = name.startsWith("/") ? name.substring(1) : name;
        this.stream = Objects.requireNonNull(stream, "stream must not be null");
        if (size < 0L) {
            throw new IllegalArgumentException("size cannot be negative value");
        }
        this.size = size;
        this.modificationTime = modificationTime;
    }

    public SnowballObject(@Nonnull String name, @Nonnull String filename) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name must be provided");
        }
        String string = this.name = name.startsWith("/") ? name.substring(1) : name;
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("filename must be provided");
        }
        this.filename = filename;
    }

    public String name() {
        return this.name;
    }

    public InputStream stream() {
        return this.stream;
    }

    public long size() {
        return this.size;
    }

    public String filename() {
        return this.filename;
    }

    public ZonedDateTime modificationTime() {
        return this.modificationTime;
    }
}

