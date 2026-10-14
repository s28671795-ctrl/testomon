/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.connection.IMAPNamespace;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;
import org.apache.geronimo.javamail.util.ResponseFormatException;

public class IMAPNamespaceResponse
extends IMAPUntaggedResponse {
    public List personalNamespaces;
    public List otherUserNamespaces;
    public List sharedNamespaces;

    public IMAPNamespaceResponse() {
        super("NAMESPACE", null);
        this.personalNamespaces = Collections.EMPTY_LIST;
        this.otherUserNamespaces = Collections.EMPTY_LIST;
        this.sharedNamespaces = Collections.EMPTY_LIST;
    }

    public IMAPNamespaceResponse(byte[] data, IMAPResponseTokenizer source2) throws MessagingException {
        super("NAMESPACE", data);
        this.personalNamespaces = this.parseNamespace(source2);
        this.otherUserNamespaces = this.parseNamespace(source2);
        this.sharedNamespaces = this.parseNamespace(source2);
    }

    private List parseNamespace(IMAPResponseTokenizer source2) throws MessagingException {
        IMAPResponseTokenizer.Token token = source2.next(true);
        if (token.getType() == -6) {
            return null;
        }
        if (token.getType() != 40) {
            throw new ResponseFormatException("Missing '(' in response");
        }
        ArrayList<IMAPNamespace> namespaces = new ArrayList<IMAPNamespace>();
        while (source2.notListEnd()) {
            namespaces.add(new IMAPNamespace(source2));
        }
        source2.checkRightParen();
        return namespaces;
    }
}

