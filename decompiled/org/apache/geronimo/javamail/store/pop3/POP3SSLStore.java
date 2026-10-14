/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.pop3;

import javax.mail.Session;
import javax.mail.URLName;
import org.apache.geronimo.javamail.store.pop3.POP3Store;

public class POP3SSLStore
extends POP3Store {
    public POP3SSLStore(Session session, URLName urlName) {
        super(session, urlName, "pop3s", 995, true);
    }
}

