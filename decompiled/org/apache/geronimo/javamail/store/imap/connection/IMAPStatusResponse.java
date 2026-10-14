/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.util.List;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;

public class IMAPStatusResponse
extends IMAPUntaggedResponse {
    public String mailbox;
    public int messages = -1;
    public int recentMessages = -1;
    public int unseenMessages = -1;
    public long uidNext = -1L;
    public long uidValidity = -1L;

    public IMAPStatusResponse(byte[] data, IMAPResponseTokenizer source2) throws MessagingException {
        super("STATUS", data);
        this.mailbox = source2.readEncodedString();
        List flags = source2.readStringList();
        if (flags == null) {
            return;
        }
        for (int i = 0; i < flags.size(); i += 2) {
            long value;
            String field2 = ((String)flags.get(i)).toUpperCase();
            String stringValue = (String)flags.get(i + 1);
            try {
                value = Long.parseLong(stringValue);
            }
            catch (NumberFormatException e) {
                throw new MessagingException("Invalid IMAP Status response", e);
            }
            if (field2.equals("MESSAGES")) {
                this.messages = (int)value;
                continue;
            }
            if (field2.equals("RECENT")) {
                this.recentMessages = (int)value;
                continue;
            }
            if (field2.equals("UIDNEXT")) {
                this.uidNext = value;
                continue;
            }
            if (field2.equals("UIDVALIDITY")) {
                this.uidValidity = value;
                continue;
            }
            if (!field2.equals("UNSEEN")) continue;
            this.unseenMessages = (int)value;
        }
    }
}

