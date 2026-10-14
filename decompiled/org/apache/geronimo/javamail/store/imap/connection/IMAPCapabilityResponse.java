/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;

public class IMAPCapabilityResponse
extends IMAPUntaggedResponse {
    protected Map capabilities = new HashMap();
    protected List authentications = new ArrayList();

    public IMAPCapabilityResponse(IMAPResponseTokenizer source2, byte[] response) throws MessagingException {
        super("CAPABILITY", response);
        while (source2.hasMore()) {
            String value = source2.readAtom().toUpperCase();
            if (value.startsWith("AUTH=")) {
                String mechanism = value.substring(5);
                this.authentications.add(mechanism);
                continue;
            }
            this.capabilities.put(value, value);
        }
    }

    public Map getCapabilities() {
        return this.capabilities;
    }

    public List getAuthentications() {
        return this.authentications;
    }
}

