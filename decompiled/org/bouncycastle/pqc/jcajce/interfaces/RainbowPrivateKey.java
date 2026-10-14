/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.jcajce.interfaces;

import java.security.PrivateKey;
import org.bouncycastle.pqc.jcajce.interfaces.RainbowKey;
import org.bouncycastle.pqc.jcajce.interfaces.RainbowPublicKey;

public interface RainbowPrivateKey
extends PrivateKey,
RainbowKey {
    public RainbowPublicKey getPublicKey();
}

