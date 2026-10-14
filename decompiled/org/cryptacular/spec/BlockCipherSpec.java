/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.spec;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.engines.BlowfishEngine;
import org.bouncycastle.crypto.engines.CAST5Engine;
import org.bouncycastle.crypto.engines.CAST6Engine;
import org.bouncycastle.crypto.engines.CamelliaEngine;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.engines.GOST28147Engine;
import org.bouncycastle.crypto.engines.NoekeonEngine;
import org.bouncycastle.crypto.engines.RC2Engine;
import org.bouncycastle.crypto.engines.RC564Engine;
import org.bouncycastle.crypto.engines.RC6Engine;
import org.bouncycastle.crypto.engines.SEEDEngine;
import org.bouncycastle.crypto.engines.SerpentEngine;
import org.bouncycastle.crypto.engines.SkipjackEngine;
import org.bouncycastle.crypto.engines.TEAEngine;
import org.bouncycastle.crypto.engines.TwofishEngine;
import org.bouncycastle.crypto.engines.XTEAEngine;
import org.cryptacular.spec.Spec;

public class BlockCipherSpec
implements Spec<BlockCipher> {
    private final String algorithm;

    public BlockCipherSpec(String algName) {
        this.algorithm = algName;
    }

    @Override
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override
    public BlockCipher newInstance() {
        BlockCipher cipher;
        if ("AES".equalsIgnoreCase(this.algorithm)) {
            cipher = new AESEngine();
        } else if ("Blowfish".equalsIgnoreCase(this.algorithm)) {
            cipher = new BlowfishEngine();
        } else if ("Camellia".equalsIgnoreCase(this.algorithm)) {
            cipher = new CamelliaEngine();
        } else if ("CAST5".equalsIgnoreCase(this.algorithm)) {
            cipher = new CAST5Engine();
        } else if ("CAST6".equalsIgnoreCase(this.algorithm)) {
            cipher = new CAST6Engine();
        } else if ("DES".equalsIgnoreCase(this.algorithm)) {
            cipher = new DESEngine();
        } else if ("DESede".equalsIgnoreCase(this.algorithm) || "DES3".equalsIgnoreCase(this.algorithm)) {
            cipher = new DESedeEngine();
        } else if ("GOST".equalsIgnoreCase(this.algorithm) || "GOST28147".equals(this.algorithm)) {
            cipher = new GOST28147Engine();
        } else if ("Noekeon".equalsIgnoreCase(this.algorithm)) {
            cipher = new NoekeonEngine();
        } else if ("RC2".equalsIgnoreCase(this.algorithm)) {
            cipher = new RC2Engine();
        } else if ("RC5".equalsIgnoreCase(this.algorithm)) {
            cipher = new RC564Engine();
        } else if ("RC6".equalsIgnoreCase(this.algorithm)) {
            cipher = new RC6Engine();
        } else if ("SEED".equalsIgnoreCase(this.algorithm)) {
            cipher = new SEEDEngine();
        } else if ("Serpent".equalsIgnoreCase(this.algorithm)) {
            cipher = new SerpentEngine();
        } else if ("Skipjack".equalsIgnoreCase(this.algorithm)) {
            cipher = new SkipjackEngine();
        } else if ("TEA".equalsIgnoreCase(this.algorithm)) {
            cipher = new TEAEngine();
        } else if ("Twofish".equalsIgnoreCase(this.algorithm)) {
            cipher = new TwofishEngine();
        } else if ("XTEA".equalsIgnoreCase(this.algorithm)) {
            cipher = new XTEAEngine();
        } else {
            throw new IllegalStateException("Unsupported cipher algorithm " + this.algorithm);
        }
        return cipher;
    }

    public String toString() {
        return this.algorithm;
    }
}

