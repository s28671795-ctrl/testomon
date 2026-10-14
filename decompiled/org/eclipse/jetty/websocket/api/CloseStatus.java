/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.api;

public class CloseStatus {
    private static final int MAX_CONTROL_PAYLOAD = 125;
    public static final int MAX_REASON_PHRASE = 123;
    private final int code;
    private final String phrase;

    public CloseStatus(int closeCode, String reasonPhrase) {
        this.code = closeCode;
        this.phrase = reasonPhrase;
        if (reasonPhrase.length() > 123) {
            throw new IllegalArgumentException("Phrase exceeds maximum length of 123");
        }
    }

    public int getCode() {
        return this.code;
    }

    public String getPhrase() {
        return this.phrase;
    }
}

