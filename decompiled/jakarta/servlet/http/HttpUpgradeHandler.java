/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet.http;

import jakarta.servlet.http.WebConnection;

public interface HttpUpgradeHandler {
    public void init(WebConnection var1);

    public void destroy();
}

