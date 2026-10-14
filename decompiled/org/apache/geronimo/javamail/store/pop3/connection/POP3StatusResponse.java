/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.pop3.connection;

import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.pop3.connection.POP3Response;

public class POP3StatusResponse
extends POP3Response {
    private int numMessages = 0;
    private int size = 0;

    POP3StatusResponse(POP3Response baseRes) throws MessagingException {
        super(baseRes.getStatus(), baseRes.getFirstLine(), baseRes.getData());
        if (0 == this.getStatus()) {
            String[] args2 = this.getFirstLine().split(" ");
            try {
                this.numMessages = Integer.parseInt(args2[0]);
            }
            catch (NumberFormatException e) {
                throw new MessagingException("Invalid response for STAT command", e);
            }
            try {
                this.size = Integer.parseInt(args2[1]);
            }
            catch (NumberFormatException e) {
                throw new MessagingException("Invalid response for STAT command", e);
            }
        }
    }

    public int getNumMessages() {
        return this.numMessages;
    }

    public int getSize() {
        return this.size;
    }
}

