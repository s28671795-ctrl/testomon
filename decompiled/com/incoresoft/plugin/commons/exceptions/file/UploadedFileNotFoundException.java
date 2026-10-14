/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.exceptions.file;

public class UploadedFileNotFoundException
extends RuntimeException {
    private final String fileName;

    public UploadedFileNotFoundException(String fileName) {
        super(String.format("File with name %s not found", fileName));
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }
}

