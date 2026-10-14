/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.util.ArrayList;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.ACL;
import org.apache.geronimo.javamail.store.imap.Rights;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;

public class IMAPACLResponse
extends IMAPUntaggedResponse {
    public String mailbox;
    public ACL[] acls;

    public IMAPACLResponse(byte[] data, IMAPResponseTokenizer source2) throws MessagingException {
        super("ACL", data);
        this.mailbox = source2.readEncodedString();
        ArrayList<ACL> temp = new ArrayList<ACL>();
        while (source2.hasMore()) {
            String name = source2.readString();
            String rights = source2.readString();
            temp.add(new ACL(name, new Rights(rights)));
        }
        this.acls = new ACL[temp.size()];
        this.acls = temp.toArray(this.acls);
    }
}

