/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.ContentType;
import org.jooq.File;

class FileImpl
implements File {
    private final String path;
    private final String name;
    private final String content;
    private final ContentType type;

    FileImpl(String path, String content, ContentType type) {
        this.path = path;
        this.content = content;
        this.type = type;
        this.name = path.substring(path.lastIndexOf(47) + 1);
    }

    @Override
    public final String path() {
        return this.path;
    }

    @Override
    public final String name() {
        return this.name;
    }

    @Override
    public final String content() {
        return this.content;
    }

    @Override
    public final ContentType type() {
        return this.type;
    }

    public String toString() {
        return "-- " + String.valueOf((Object)this.type) + ": " + this.path + "\n" + this.content;
    }
}

