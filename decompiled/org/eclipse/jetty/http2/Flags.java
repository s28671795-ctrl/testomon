/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2;

public interface Flags {
    public static final int NONE = 0;
    public static final int END_STREAM = 1;
    public static final int ACK = 1;
    public static final int END_HEADERS = 4;
    public static final int PADDING = 8;
    public static final int PRIORITY = 32;
}

