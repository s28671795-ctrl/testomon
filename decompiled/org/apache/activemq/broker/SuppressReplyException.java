/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.io.IOException;

public class SuppressReplyException
extends RuntimeException {
    public SuppressReplyException(Throwable cause) {
        super(cause);
    }

    public SuppressReplyException(String reason) {
        super(reason);
    }

    public SuppressReplyException(String reason, IOException cause) {
        super(reason, cause);
    }
}

