/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.pop3.connection;

import java.io.ByteArrayInputStream;
import org.apache.geronimo.javamail.store.pop3.POP3Constants;
import org.apache.geronimo.mail.util.Base64;

public class POP3Response
implements POP3Constants {
    private int status = 1;
    private String firstLine;
    private byte[] data;

    POP3Response(int status, String firstLine, byte[] data) {
        this.status = status;
        this.firstLine = firstLine;
        this.data = data;
    }

    public int getStatus() {
        return this.status;
    }

    public byte[] getData() {
        return this.data;
    }

    public ByteArrayInputStream getContentStream() {
        return new ByteArrayInputStream(this.data);
    }

    public String getFirstLine() {
        return this.firstLine;
    }

    public boolean isError() {
        return this.status == 1;
    }

    public boolean isChallenge() {
        return this.status == 2;
    }

    public byte[] decodeChallengeResponse() {
        return Base64.decode(this.firstLine.trim());
    }
}

