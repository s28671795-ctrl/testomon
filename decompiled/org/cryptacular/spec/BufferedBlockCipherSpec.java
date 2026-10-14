/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.spec;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.modes.OFBBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.ISO10126d2Padding;
import org.bouncycastle.crypto.paddings.ISO7816d4Padding;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.TBCPadding;
import org.bouncycastle.crypto.paddings.X923Padding;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.cryptacular.spec.BlockCipherSpec;
import org.cryptacular.spec.Spec;

public class BufferedBlockCipherSpec
implements Spec<BufferedBlockCipher> {
    public static final Pattern FORMAT = Pattern.compile("(?<alg>[A-Za-z0-9_-]+)/(?<mode>\\w+)/(?<padding>\\w+)");
    private final String algorithm;
    private final String mode;
    private final String padding;

    public BufferedBlockCipherSpec(String algName) {
        this(algName, null, null);
    }

    public BufferedBlockCipherSpec(String algName, String cipherMode) {
        this(algName, cipherMode, null);
    }

    public BufferedBlockCipherSpec(String algName, String cipherMode, String cipherPadding) {
        this.algorithm = algName;
        this.mode = cipherMode;
        this.padding = cipherPadding;
    }

    @Override
    public String getAlgorithm() {
        return this.algorithm;
    }

    public String getMode() {
        return this.mode;
    }

    public String getPadding() {
        return this.padding;
    }

    public BlockCipherSpec getBlockCipherSpec() {
        return new BlockCipherSpec(this.algorithm);
    }

    @Override
    public BufferedBlockCipher newInstance() {
        BlockCipher cipher = this.getBlockCipherSpec().newInstance();
        switch (this.mode) {
            case "CBC": {
                cipher = new CBCBlockCipher(cipher);
                break;
            }
            case "OFB": {
                cipher = new OFBBlockCipher(cipher, cipher.getBlockSize());
                break;
            }
            case "CFB": {
                cipher = new CFBBlockCipher(cipher, cipher.getBlockSize());
                break;
            }
        }
        if (this.padding != null) {
            return new PaddedBufferedBlockCipher(cipher, BufferedBlockCipherSpec.getPadding(this.padding));
        }
        return new BufferedBlockCipher(cipher);
    }

    public String toString() {
        return this.algorithm + '/' + this.mode + '/' + this.padding;
    }

    public static BufferedBlockCipherSpec parse(String specification) {
        Matcher m4 = FORMAT.matcher(specification);
        if (!m4.matches()) {
            throw new IllegalArgumentException("Invalid specification " + specification);
        }
        return new BufferedBlockCipherSpec(m4.group("alg"), m4.group("mode"), m4.group("padding"));
    }

    private static BlockCipherPadding getPadding(String padding) {
        BlockCipherPadding blockCipherPadding;
        int pIndex = padding.indexOf("Padding");
        String name = pIndex > -1 ? padding.substring(0, pIndex) : padding;
        if ("ISO7816d4".equalsIgnoreCase(name) | "ISO7816".equalsIgnoreCase(name)) {
            blockCipherPadding = new ISO7816d4Padding();
        } else if ("ISO10126".equalsIgnoreCase(name) || "ISO10126-2".equalsIgnoreCase(name)) {
            blockCipherPadding = new ISO10126d2Padding();
        } else if ("PKCS7".equalsIgnoreCase(name) || "PKCS5".equalsIgnoreCase(name)) {
            blockCipherPadding = new PKCS7Padding();
        } else if ("TBC".equalsIgnoreCase(name)) {
            blockCipherPadding = new TBCPadding();
        } else if ("X923".equalsIgnoreCase(name)) {
            blockCipherPadding = new X923Padding();
        } else if ("NULL".equalsIgnoreCase(name) || "Zero".equalsIgnoreCase(name) || "None".equalsIgnoreCase(name)) {
            blockCipherPadding = new ZeroBytePadding();
        } else {
            throw new IllegalArgumentException("Invalid padding " + padding);
        }
        return blockCipherPadding;
    }
}

