/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.transport.nntp;

import javax.mail.Session;
import javax.mail.URLName;
import org.apache.geronimo.javamail.transport.nntp.NNTPTransport;

public class NNTPSSLTransport
extends NNTPTransport {
    public NNTPSSLTransport(Session session, URLName name) {
        super(session, name, "nntp-posts", 563, true);
    }
}

