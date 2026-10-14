/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap;

import javax.mail.Session;
import javax.mail.URLName;
import org.apache.geronimo.javamail.store.imap.IMAPStore;

public class IMAPSSLStore
extends IMAPStore {
    public IMAPSSLStore(Session session, URLName urlName) {
        super(session, urlName, "imaps", true, 993);
    }
}

