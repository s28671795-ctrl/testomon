/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.util.ArrayList;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.Rights;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;

public class IMAPListRightsResponse
extends IMAPUntaggedResponse {
    public String mailbox;
    public String name;
    public Rights[] rights;

    public IMAPListRightsResponse(byte[] data, IMAPResponseTokenizer source2) throws MessagingException {
        super("LISTRIGHTS", data);
        this.mailbox = source2.readEncodedString();
        this.name = source2.readString();
        ArrayList<Rights> acls = new ArrayList<Rights>();
        while (source2.hasMore()) {
            acls.add(new Rights(source2.readString()));
        }
        this.rights = new Rights[acls.size()];
        acls.toArray(this.rights);
    }
}

