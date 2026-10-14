/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.transport.smtp;

import javax.mail.Session;
import javax.mail.URLName;
import org.apache.geronimo.javamail.transport.smtp.SMTPTransport;

public class SMTPSTransport
extends SMTPTransport {
    public SMTPSTransport(Session session, URLName name) {
        super(session, name, "smtps", 465, true);
    }
}

