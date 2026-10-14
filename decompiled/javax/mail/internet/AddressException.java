/*
 * Decompiled with CFR 0.152.
 */
package javax.mail.internet;

import javax.mail.internet.ParseException;

public class AddressException
extends ParseException {
    protected String ref = null;
    protected int pos = -1;
    private static final long serialVersionUID = 9134583443539323120L;

    public AddressException() {
    }

    public AddressException(String s2) {
        super(s2);
    }

    public AddressException(String s2, String ref) {
        super(s2);
        this.ref = ref;
    }

    public AddressException(String s2, String ref, int pos) {
        super(s2);
        this.ref = ref;
        this.pos = pos;
    }

    public String getRef() {
        return this.ref;
    }

    public int getPos() {
        return this.pos;
    }

    public String toString() {
        String s2 = super.toString();
        if (this.ref == null) {
            return s2;
        }
        s2 = s2 + " in string ``" + this.ref + "''";
        if (this.pos < 0) {
            return s2;
        }
        return s2 + " at position " + this.pos;
    }
}

