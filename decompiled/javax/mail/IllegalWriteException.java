/*
 * Decompiled with CFR 0.152.
 */
package javax.mail;

import javax.mail.MessagingException;

public class IllegalWriteException
extends MessagingException {
    private static final long serialVersionUID = 3974370223328268013L;

    public IllegalWriteException() {
    }

    public IllegalWriteException(String s2) {
        super(s2);
    }

    public IllegalWriteException(String s2, Exception e) {
        super(s2, e);
    }
}

