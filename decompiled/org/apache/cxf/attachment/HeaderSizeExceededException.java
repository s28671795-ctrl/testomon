/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.attachment;

public class HeaderSizeExceededException
extends RuntimeException {
    private static final long serialVersionUID = -8976580055837650080L;

    public HeaderSizeExceededException() {
    }

    public HeaderSizeExceededException(String message) {
        super(message);
    }

    public HeaderSizeExceededException(String message, Throwable cause) {
        super(message, cause);
    }

    public HeaderSizeExceededException(Throwable cause) {
        super(cause);
    }
}

