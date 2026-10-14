/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.crypto.aes128;

import com.incoresoft.middleware.analytics.crypto.aes128.AES128;
import java.nio.charset.StandardCharsets;

public class AES128Wrapper {
    public String encode(String key, String input) throws Exception {
        AES128 aes128 = new AES128();
        aes128.SetKey(key.getBytes(StandardCharsets.UTF_8));
        return aes128.CryptPacket(input.getBytes(StandardCharsets.UTF_8));
    }

    public String decode(String key, String input) throws Exception {
        AES128 AES2 = new AES128();
        AES2.SetKey(key.getBytes(StandardCharsets.UTF_8));
        return new String(AES2.DecryptPacket(input));
    }
}

