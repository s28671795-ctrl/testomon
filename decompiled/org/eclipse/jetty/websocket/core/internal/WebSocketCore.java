/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import org.eclipse.jetty.websocket.core.WebSocketConstants;

public final class WebSocketCore {
    public static String hashKey(String key) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(key.getBytes(StandardCharsets.UTF_8));
            md.update(WebSocketConstants.MAGIC);
            return Base64.getEncoder().encodeToString(md.digest());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

