/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.api;

public enum BatchMode {
    AUTO,
    ON,
    OFF;


    public static BatchMode max(BatchMode one, BatchMode two) {
        return one.ordinal() < two.ordinal() ? two : one;
    }
}

