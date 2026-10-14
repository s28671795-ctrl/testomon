/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;

public class IMAPNamespace {
    public String prefix;
    public char separator = '\u0000';

    public IMAPNamespace(IMAPResponseTokenizer source2) throws MessagingException {
        source2.checkLeftParen();
        this.prefix = source2.readString();
        String delim = source2.readString();
        if (delim.length() != 0) {
            this.separator = delim.charAt(0);
        }
        source2.checkRightParen();
    }
}

