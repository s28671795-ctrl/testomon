/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.util.List;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;

public class IMAPListResponse
extends IMAPUntaggedResponse {
    public boolean noinferiors = false;
    public boolean noselect = false;
    public boolean marked = false;
    public boolean unmarked = false;
    public char separator;
    public String mailboxName;
    public String[] attributes;

    public IMAPListResponse(String type, byte[] data, IMAPResponseTokenizer source2) throws MessagingException {
        super(type, data);
        List flags = source2.readSystemNameList();
        this.attributes = new String[flags.size()];
        this.attributes = flags.toArray(this.attributes);
        for (int i = 0; i < flags.size(); ++i) {
            String flag = (String)flags.get(i);
            if (flag.equalsIgnoreCase("\\Marked")) {
                this.marked = true;
                continue;
            }
            if (flag.equalsIgnoreCase("\\Unmarked")) {
                this.unmarked = true;
                continue;
            }
            if (flag.equalsIgnoreCase("\\Noselect")) {
                this.noselect = true;
                continue;
            }
            if (!flag.equalsIgnoreCase("\\Noinferiors")) continue;
            this.noinferiors = true;
        }
        this.separator = '\u0000';
        String separatorString = source2.readQuotedStringOrNil();
        if (separatorString != null && separatorString.length() == 1) {
            this.separator = separatorString.charAt(0);
        }
        this.mailboxName = source2.readEncodedString();
    }
}

