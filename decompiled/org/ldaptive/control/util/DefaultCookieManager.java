/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control.util;

import org.ldaptive.control.util.CookieManager;

public class DefaultCookieManager
implements CookieManager {
    private byte[] cookie;

    public DefaultCookieManager() {
    }

    public DefaultCookieManager(byte[] b) {
        this.cookie = b;
    }

    @Override
    public byte[] readCookie() {
        return this.cookie;
    }

    @Override
    public void writeCookie(byte[] b) {
        this.cookie = b;
    }
}

