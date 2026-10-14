/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

public class IMAPMailboxResponse {
    public int count;
    public String name;

    public IMAPMailboxResponse(int count, String name) {
        this.count = count;
        this.name = name;
    }
}

