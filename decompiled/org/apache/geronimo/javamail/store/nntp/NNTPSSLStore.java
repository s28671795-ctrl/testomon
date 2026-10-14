/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.nntp;

import javax.mail.Session;
import javax.mail.URLName;
import org.apache.geronimo.javamail.store.nntp.NNTPStore;

public class NNTPSSLStore
extends NNTPStore {
    public NNTPSSLStore(Session session, URLName urlName) {
        super(session, urlName, "nntps", 563, true);
    }
}

