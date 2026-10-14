/*
 * Decompiled with CFR 0.152.
 */
package javax.mail;

import javax.mail.MessagingException;

public class MethodNotSupportedException
extends MessagingException {
    private static final long serialVersionUID = -3757386618726131322L;

    public MethodNotSupportedException() {
    }

    public MethodNotSupportedException(String s2) {
        super(s2);
    }

    public MethodNotSupportedException(String s2, Exception e) {
        super(s2, e);
    }
}

