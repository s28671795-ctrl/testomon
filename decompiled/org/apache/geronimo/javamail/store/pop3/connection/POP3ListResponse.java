/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.pop3.connection;

import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.pop3.connection.POP3Response;

public class POP3ListResponse
extends POP3Response {
    private int msgnum = 0;
    private int size = 0;
    private List multipleMsgs = null;

    POP3ListResponse(POP3Response baseRes) throws MessagingException {
        super(baseRes.getStatus(), baseRes.getFirstLine(), baseRes.getData());
        if (0 == this.getStatus()) {
            if (baseRes.getData() == null) {
                String[] args2 = this.getFirstLine().split(" ");
                try {
                    this.msgnum = Integer.parseInt(args2[0]);
                }
                catch (NumberFormatException e) {
                    throw new MessagingException("Invalid response for LIST command", e);
                }
                try {
                    this.size = Integer.parseInt(args2[1]);
                }
                catch (NumberFormatException e) {
                    throw new MessagingException("Invalid response for LIST command", e);
                }
            }
            int totalMsgs = 0;
            String[] args3 = this.getFirstLine().split(" ");
            try {
                totalMsgs = Integer.parseInt(args3[0]);
            }
            catch (NumberFormatException e) {
                throw new MessagingException("Invalid response for LIST command", e);
            }
            this.multipleMsgs = new ArrayList(totalMsgs);
        }
    }

    public int getMessageNumber() {
        return this.msgnum;
    }

    public int getSize() {
        return this.size;
    }

    public List getMultipleMessageDetails() {
        return this.multipleMsgs;
    }
}

