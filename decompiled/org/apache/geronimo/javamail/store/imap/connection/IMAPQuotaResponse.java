/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.util.ArrayList;
import javax.mail.MessagingException;
import javax.mail.Quota;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;

public class IMAPQuotaResponse
extends IMAPUntaggedResponse {
    public Quota quota;

    public IMAPQuotaResponse(byte[] data, IMAPResponseTokenizer source2) throws MessagingException {
        super("QUOTA", data);
        String tokenName = source2.readString();
        this.quota = new Quota(tokenName);
        source2.checkLeftParen();
        ArrayList<Quota.Resource> resources = new ArrayList<Quota.Resource>();
        while (source2.notListEnd()) {
            Quota.Resource resource = new Quota.Resource(source2.readAtom(), source2.readLong(), source2.readLong());
            resources.add(resource);
        }
        this.quota.resources = resources.toArray(new Quota.Resource[resources.size()]);
    }
}

