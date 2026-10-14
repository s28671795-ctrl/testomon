/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;

public interface IMAPUntaggedResponseHandler {
    public boolean handleResponse(IMAPUntaggedResponse var1);
}

