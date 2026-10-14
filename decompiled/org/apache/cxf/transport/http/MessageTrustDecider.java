/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.URLConnectionInfo;
import org.apache.cxf.transport.http.UntrustedURLConnectionIOException;

public abstract class MessageTrustDecider {
    protected String logicalName;

    protected MessageTrustDecider() {
        this.logicalName = this.getClass().getName();
    }

    protected MessageTrustDecider(String name) {
        this.logicalName = name;
    }

    public String getLogicalName() {
        return this.logicalName;
    }

    public abstract void establishTrust(String var1, URLConnectionInfo var2, Message var3) throws UntrustedURLConnectionIOException;
}

