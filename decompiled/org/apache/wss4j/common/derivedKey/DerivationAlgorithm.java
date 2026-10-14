/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.derivedKey;

import org.apache.wss4j.common.ext.WSSecurityException;

public interface DerivationAlgorithm {
    public byte[] createKey(byte[] var1, byte[] var2, int var3, long var4) throws WSSecurityException;
}

