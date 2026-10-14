/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.core.exceptions;

public class CoreExtensionNotFoundException
extends RuntimeException {
    public CoreExtensionNotFoundException(String className) {
        super(String.format("Extension implementing %s not found", className));
    }
}

