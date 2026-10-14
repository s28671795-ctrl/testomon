/*
 * Decompiled with CFR 0.152.
 */
package javax.mail;

import javax.mail.Address;
import javax.mail.MessagingException;

public class SendFailedException
extends MessagingException {
    protected transient Address[] invalid;
    protected transient Address[] validSent;
    protected transient Address[] validUnsent;
    private static final long serialVersionUID = -6457531621682372913L;

    public SendFailedException() {
    }

    public SendFailedException(String s2) {
        super(s2);
    }

    public SendFailedException(String s2, Exception e) {
        super(s2, e);
    }

    public SendFailedException(String msg, Exception ex, Address[] validSent, Address[] validUnsent, Address[] invalid) {
        super(msg, ex);
        this.validSent = validSent;
        this.validUnsent = validUnsent;
        this.invalid = invalid;
    }

    public Address[] getValidSentAddresses() {
        return this.validSent;
    }

    public Address[] getValidUnsentAddresses() {
        return this.validUnsent;
    }

    public Address[] getInvalidAddresses() {
        return this.invalid;
    }
}

