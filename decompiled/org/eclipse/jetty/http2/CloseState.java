/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2;

public enum CloseState {
    NOT_CLOSED,
    LOCALLY_CLOSING,
    LOCALLY_CLOSED,
    REMOTELY_CLOSED,
    CLOSING,
    CLOSED;


    public static enum Event {
        RECEIVED,
        BEFORE_SEND,
        AFTER_SEND;

    }
}

