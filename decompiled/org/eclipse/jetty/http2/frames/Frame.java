/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.frames;

import org.eclipse.jetty.http2.frames.FrameType;

public abstract class Frame {
    public static final int HEADER_LENGTH = 9;
    public static final int DEFAULT_MAX_LENGTH = 16384;
    public static final int MAX_MAX_LENGTH = 0xFFFFFF;
    public static final Frame[] EMPTY_ARRAY = new Frame[0];
    private final FrameType type;

    protected Frame(FrameType type) {
        this.type = type;
    }

    public FrameType getType() {
        return this.type;
    }

    public String toString() {
        return String.format("%s@%x", this.getClass().getSimpleName(), this.hashCode());
    }
}

