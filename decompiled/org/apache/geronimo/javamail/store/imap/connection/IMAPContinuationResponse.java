/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import org.apache.geronimo.javamail.store.imap.connection.IMAPTaggedResponse;

public class IMAPContinuationResponse
extends IMAPTaggedResponse {
    protected IMAPContinuationResponse(byte[] response) {
        super(response);
    }
}

