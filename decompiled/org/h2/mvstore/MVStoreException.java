/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

public class MVStoreException
extends RuntimeException {
    private static final long serialVersionUID = 2847042930249663807L;
    private final int errorCode;

    public MVStoreException(int n, String string) {
        super(string);
        this.errorCode = n;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}

