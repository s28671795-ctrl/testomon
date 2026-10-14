/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.hpack;

import org.eclipse.jetty.http.HostPortHttpField;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http2.hpack.HpackContext;

public class AuthorityHttpField
extends HostPortHttpField {
    public static final String AUTHORITY = HpackContext.STATIC_TABLE[1][0];

    public AuthorityHttpField(String authority) {
        super(HttpHeader.C_AUTHORITY, AUTHORITY, authority);
    }

    @Override
    public String toString() {
        return String.format("%s(preparsed h=%s p=%d)", super.toString(), this.getHost(), this.getPort());
    }
}

