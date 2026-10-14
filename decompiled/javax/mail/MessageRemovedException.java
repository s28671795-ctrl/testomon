/*
 * Decompiled with CFR 0.152.
 */
package javax.mail;

import javax.mail.MessagingException;

public class MessageRemovedException
extends MessagingException {
    private static final long serialVersionUID = 1951292550679528690L;

    public MessageRemovedException() {
    }

    public MessageRemovedException(String s2) {
        super(s2);
    }

    public MessageRemovedException(String s2, Exception e) {
        super(s2, e);
    }
}

