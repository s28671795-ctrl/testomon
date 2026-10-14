/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.util.List;
import javax.mail.Flags;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFlagsResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPOkResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPPermanentFlagsResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;
import org.apache.geronimo.javamail.store.imap.connection.IMAPSizeResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPStatusResponse;

public class IMAPMailboxStatus {
    public Flags availableFlags = null;
    public Flags permanentFlags = null;
    public int mode = 2;
    public int messages = -1;
    public int recentMessages = -1;
    public int unseenMessages = -1;
    public long uidNext = -1L;
    public long uidValidity = -1L;

    public void mergeStatus(IMAPStatusResponse source2) throws MessagingException {
        if (source2.messages != -1) {
            this.messages = source2.messages;
        }
        if (source2.uidNext != -1L) {
            this.uidNext = source2.uidNext;
        }
        if (source2.uidValidity != -1L) {
            this.uidValidity = source2.uidValidity;
        }
        if (source2.recentMessages != -1) {
            this.recentMessages = source2.recentMessages;
        }
        if (source2.unseenMessages != -1) {
            this.unseenMessages = source2.unseenMessages;
        }
    }

    public void mergeFlags(IMAPFlagsResponse response) throws MessagingException {
        if (response != null) {
            this.availableFlags = response.getFlags();
        }
    }

    public void mergeSizeResponses(List responses) throws MessagingException {
        for (int i = 0; i < responses.size(); ++i) {
            this.mergeStatus((IMAPSizeResponse)responses.get(i));
        }
    }

    public void mergeOkResponses(List responses) throws MessagingException {
        for (int i = 0; i < responses.size(); ++i) {
            this.mergeStatus((IMAPOkResponse)responses.get(i));
        }
    }

    public void mergeStatus(IMAPSizeResponse source2) throws MessagingException {
        if (source2 != null) {
            String name = source2.getKeyword();
            if (source2.isKeyword("EXISTS")) {
                this.messages = source2.getSize();
            } else if (source2.isKeyword("RECENT")) {
                this.recentMessages = source2.getSize();
            }
        }
    }

    public void mergeStatus(IMAPOkResponse source2) throws MessagingException {
        if (source2 != null) {
            List arguments;
            String name = source2.getKeyword();
            if (source2.isKeyword("UIDVALIDITY")) {
                arguments = source2.getStatus();
                this.uidValidity = ((IMAPResponseTokenizer.Token)arguments.get(0)).getLong();
            }
            if (source2.isKeyword("UIDNEXT")) {
                arguments = source2.getStatus();
                this.uidNext = ((IMAPResponseTokenizer.Token)arguments.get(0)).getLong();
            } else if (source2.isKeyword("UNSEEN")) {
                arguments = source2.getStatus();
                this.uidValidity = ((IMAPResponseTokenizer.Token)arguments.get(0)).getInteger();
            }
        }
    }

    public void mergeStatus(IMAPPermanentFlagsResponse source2) throws MessagingException {
        if (source2 != null) {
            this.permanentFlags = source2.flags;
        }
    }
}

