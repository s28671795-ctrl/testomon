/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.util.ArrayList;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;

public class IMAPSearchResponse
extends IMAPUntaggedResponse {
    public int[] messageNumbers;

    public IMAPSearchResponse(byte[] data, IMAPResponseTokenizer source2) throws MessagingException {
        super("SEARCH", data);
        IMAPResponseTokenizer.Token token = source2.next();
        ArrayList<IMAPResponseTokenizer.Token> tokens = new ArrayList<IMAPResponseTokenizer.Token>();
        while (token.getType() != -5) {
            tokens.add(token);
            token = source2.next();
        }
        this.messageNumbers = new int[tokens.size()];
        for (int i = 0; i < this.messageNumbers.length; ++i) {
            token = (IMAPResponseTokenizer.Token)tokens.get(i);
            this.messageNumbers[i] = token.getInteger();
        }
    }
}

