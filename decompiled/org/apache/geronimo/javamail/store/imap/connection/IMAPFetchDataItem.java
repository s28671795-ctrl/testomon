/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

public class IMAPFetchDataItem {
    public static final int FETCH = 0;
    public static final int ENVELOPE = 1;
    public static final int BODY = 2;
    public static final int BODYSTRUCTURE = 3;
    public static final int INTERNALDATE = 4;
    public static final int SIZE = 5;
    public static final int UID = 6;
    public static final int TEXT = 7;
    public static final int HEADER = 8;
    public static final int FLAGS = 9;
    protected int type;

    public IMAPFetchDataItem(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public boolean isType(int t2) {
        return this.type == t2;
    }
}

