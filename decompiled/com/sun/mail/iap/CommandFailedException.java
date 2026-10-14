/*
 * Decompiled with CFR 0.152.
 */
package com.sun.mail.iap;

import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;

public class CommandFailedException
extends ProtocolException {
    private static final long serialVersionUID = 793932807880443631L;

    public CommandFailedException() {
    }

    public CommandFailedException(String s2) {
        super(s2);
    }

    public CommandFailedException(Response r) {
        super(r);
    }
}

