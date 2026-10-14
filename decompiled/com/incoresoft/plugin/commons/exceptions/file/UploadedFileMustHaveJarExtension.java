/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.exceptions.file;

public class UploadedFileMustHaveJarExtension
extends RuntimeException {
    private final String fileName;

    public UploadedFileMustHaveJarExtension(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }
}

