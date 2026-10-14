/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.io;

public class CacheSizeExceededException
extends RuntimeException {
    private static final long serialVersionUID = -8976580055837650080L;

    public CacheSizeExceededException() {
    }

    public CacheSizeExceededException(String message) {
        super(message);
    }

    public CacheSizeExceededException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheSizeExceededException(Throwable cause) {
        super(cause);
    }
}

