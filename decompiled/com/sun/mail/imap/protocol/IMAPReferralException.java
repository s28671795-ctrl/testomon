/*
 * Decompiled with CFR 0.152.
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ProtocolException;

public class IMAPReferralException
extends ProtocolException {
    private String url;
    private static final long serialVersionUID = 2578770669364251968L;

    public IMAPReferralException(String s2, String url2) {
        super(s2);
        this.url = url2;
    }

    public String getUrl() {
        return this.url;
    }
}

