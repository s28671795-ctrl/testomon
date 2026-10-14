/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.Utils.Messaging;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class MyMessage
extends MimeMessage {
    public MyMessage(Session session) {
        super(session);
        try {
            this.updateMessageID();
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateMessageID() throws MessagingException {
        this.removeHeader("Message-Id");
    }
}

