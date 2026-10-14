/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.files.exceptions;

public class ZipFileException
extends RuntimeException {
    public ZipFileException(String message, Exception e) {
        super(message, e);
    }

    public ZipFileException(String message) {
        super(message);
    }
}

