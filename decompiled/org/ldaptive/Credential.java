/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.LdapUtils;

public class Credential {
    private final byte[] bytes;

    public Credential(String password) {
        if (password == null) {
            throw new NullPointerException("Password cannot be null");
        }
        this.bytes = LdapUtils.utf8Encode(password, false);
    }

    public Credential(char[] password) {
        if (password == null) {
            throw new NullPointerException("Password cannot be null");
        }
        this.bytes = LdapUtils.utf8Encode(new String(password), false);
    }

    public Credential(byte[] password) {
        if (password == null) {
            throw new NullPointerException("Password cannot be null");
        }
        this.bytes = password;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public String getString() {
        return LdapUtils.utf8Encode(this.bytes, false);
    }

    public char[] getChars() {
        return this.getString().toCharArray();
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "bytes=" + LdapUtils.utf8Encode(this.bytes) + "]";
    }
}

