/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.crypto.ntru;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Arrays;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.util.DigestFactory;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUEncryptionParameters;

public class NTRUEncryptionKeyGenerationParameters
extends KeyGenerationParameters
implements Cloneable {
    public static final NTRUEncryptionKeyGenerationParameters EES1087EP2 = new NTRUEncryptionKeyGenerationParameters(1087, 2048, 120, 120, 256, 13, 25, 14, true, new byte[]{0, 6, 3}, true, false, new SHA512Digest());
    public static final NTRUEncryptionKeyGenerationParameters EES1171EP1 = new NTRUEncryptionKeyGenerationParameters(1171, 2048, 106, 106, 256, 13, 20, 15, true, new byte[]{0, 6, 4}, true, false, new SHA512Digest());
    public static final NTRUEncryptionKeyGenerationParameters EES1499EP1 = new NTRUEncryptionKeyGenerationParameters(1499, 2048, 79, 79, 256, 13, 17, 19, true, new byte[]{0, 6, 5}, true, false, new SHA512Digest());
    public static final NTRUEncryptionKeyGenerationParameters APR2011_439 = new NTRUEncryptionKeyGenerationParameters(439, 2048, 146, 130, 128, 9, 32, 9, true, new byte[]{0, 7, 101}, true, false, new SHA256Digest());
    public static final NTRUEncryptionKeyGenerationParameters APR2011_439_FAST = new NTRUEncryptionKeyGenerationParameters(439, 2048, 9, 8, 5, 130, 128, 9, 32, 9, true, new byte[]{0, 7, 101}, true, true, new SHA256Digest());
    public static final NTRUEncryptionKeyGenerationParameters APR2011_743 = new NTRUEncryptionKeyGenerationParameters(743, 2048, 248, 220, 256, 10, 27, 14, true, new byte[]{0, 7, 105}, false, false, new SHA512Digest());
    public static final NTRUEncryptionKeyGenerationParameters APR2011_743_FAST = new NTRUEncryptionKeyGenerationParameters(743, 2048, 11, 11, 15, 220, 256, 10, 27, 14, true, new byte[]{0, 7, 105}, false, true, new SHA512Digest());
    public int N;
    public int q;
    public int df;
    public int df1;
    public int df2;
    public int df3;
    public int dr;
    public int dr1;
    public int dr2;
    public int dr3;
    public int dg;
    int llen;
    public int maxMsgLenBytes;
    public int db;
    public int bufferLenBits;
    int bufferLenTrits;
    public int dm0;
    public int pkLen;
    public int c;
    public int minCallsR;
    public int minCallsMask;
    public boolean hashSeed;
    public byte[] oid;
    public boolean sparse;
    public boolean fastFp;
    public int polyType;
    public Digest hashAlg;

    public NTRUEncryptionKeyGenerationParameters(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, boolean bl, byte[] byArray, boolean bl2, boolean bl3, Digest digest, SecureRandom secureRandom) {
        super(null != secureRandom ? secureRandom : CryptoServicesRegistrar.getSecureRandom(), n5);
        this.N = n;
        this.q = n2;
        this.df = n3;
        this.db = n5;
        this.dm0 = n4;
        this.c = n6;
        this.minCallsR = n7;
        this.minCallsMask = n8;
        this.hashSeed = bl;
        this.oid = byArray;
        this.sparse = bl2;
        this.fastFp = bl3;
        this.polyType = 0;
        this.hashAlg = digest;
        this.init();
    }

    public NTRUEncryptionKeyGenerationParameters(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, boolean bl, byte[] byArray, boolean bl2, boolean bl3, Digest digest) {
        this(n, n2, n3, n4, n5, n6, n7, n8, bl, byArray, bl2, bl3, digest, null);
    }

    public NTRUEncryptionKeyGenerationParameters(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, boolean bl, byte[] byArray, boolean bl2, boolean bl3, Digest digest, SecureRandom secureRandom) {
        super(null != secureRandom ? secureRandom : CryptoServicesRegistrar.getSecureRandom(), n7);
        this.N = n;
        this.q = n2;
        this.df1 = n3;
        this.df2 = n4;
        this.df3 = n5;
        this.db = n7;
        this.dm0 = n6;
        this.c = n8;
        this.minCallsR = n9;
        this.minCallsMask = n10;
        this.hashSeed = bl;
        this.oid = byArray;
        this.sparse = bl2;
        this.fastFp = bl3;
        this.polyType = 1;
        this.hashAlg = digest;
        this.init();
    }

    public NTRUEncryptionKeyGenerationParameters(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, boolean bl, byte[] byArray, boolean bl2, boolean bl3, Digest digest) {
        this(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, bl, byArray, bl2, bl3, digest, null);
    }

    private void init() {
        this.dr = this.df;
        this.dr1 = this.df1;
        this.dr2 = this.df2;
        this.dr3 = this.df3;
        this.dg = this.N / 3;
        this.llen = 1;
        this.maxMsgLenBytes = this.N * 3 / 2 / 8 - this.llen - this.db / 8 - 1;
        this.bufferLenBits = (this.N * 3 / 2 + 7) / 8 * 8 + 1;
        this.bufferLenTrits = this.N - 1;
        this.pkLen = this.db;
    }

    public NTRUEncryptionKeyGenerationParameters(InputStream inputStream2) throws IOException {
        super(CryptoServicesRegistrar.getSecureRandom(), -1);
        DataInputStream dataInputStream = new DataInputStream(inputStream2);
        this.N = dataInputStream.readInt();
        this.q = dataInputStream.readInt();
        this.df = dataInputStream.readInt();
        this.df1 = dataInputStream.readInt();
        this.df2 = dataInputStream.readInt();
        this.df3 = dataInputStream.readInt();
        this.db = dataInputStream.readInt();
        this.dm0 = dataInputStream.readInt();
        this.c = dataInputStream.readInt();
        this.minCallsR = dataInputStream.readInt();
        this.minCallsMask = dataInputStream.readInt();
        this.hashSeed = dataInputStream.readBoolean();
        this.oid = new byte[3];
        dataInputStream.readFully(this.oid);
        this.sparse = dataInputStream.readBoolean();
        this.fastFp = dataInputStream.readBoolean();
        this.polyType = dataInputStream.read();
        String string = dataInputStream.readUTF();
        if ("SHA-512".equals(string)) {
            this.hashAlg = new SHA512Digest();
        } else if ("SHA-256".equals(string)) {
            this.hashAlg = new SHA256Digest();
        }
        this.init();
    }

    public NTRUEncryptionParameters getEncryptionParameters() {
        if (this.polyType == 0) {
            return new NTRUEncryptionParameters(this.N, this.q, this.df, this.dm0, this.db, this.c, this.minCallsR, this.minCallsMask, this.hashSeed, this.oid, this.sparse, this.fastFp, DigestFactory.cloneDigest(this.hashAlg));
        }
        return new NTRUEncryptionParameters(this.N, this.q, this.df1, this.df2, this.df3, this.dm0, this.db, this.c, this.minCallsR, this.minCallsMask, this.hashSeed, this.oid, this.sparse, this.fastFp, DigestFactory.cloneDigest(this.hashAlg));
    }

    public NTRUEncryptionKeyGenerationParameters clone() {
        if (this.polyType == 0) {
            return new NTRUEncryptionKeyGenerationParameters(this.N, this.q, this.df, this.dm0, this.db, this.c, this.minCallsR, this.minCallsMask, this.hashSeed, this.oid, this.sparse, this.fastFp, DigestFactory.cloneDigest(this.hashAlg));
        }
        return new NTRUEncryptionKeyGenerationParameters(this.N, this.q, this.df1, this.df2, this.df3, this.dm0, this.db, this.c, this.minCallsR, this.minCallsMask, this.hashSeed, this.oid, this.sparse, this.fastFp, DigestFactory.cloneDigest(this.hashAlg));
    }

    public int getMaxMessageLength() {
        return this.maxMsgLenBytes;
    }

    public void writeTo(OutputStream outputStream2) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream2);
        dataOutputStream.writeInt(this.N);
        dataOutputStream.writeInt(this.q);
        dataOutputStream.writeInt(this.df);
        dataOutputStream.writeInt(this.df1);
        dataOutputStream.writeInt(this.df2);
        dataOutputStream.writeInt(this.df3);
        dataOutputStream.writeInt(this.db);
        dataOutputStream.writeInt(this.dm0);
        dataOutputStream.writeInt(this.c);
        dataOutputStream.writeInt(this.minCallsR);
        dataOutputStream.writeInt(this.minCallsMask);
        dataOutputStream.writeBoolean(this.hashSeed);
        dataOutputStream.write(this.oid);
        dataOutputStream.writeBoolean(this.sparse);
        dataOutputStream.writeBoolean(this.fastFp);
        dataOutputStream.write(this.polyType);
        dataOutputStream.writeUTF(this.hashAlg.getAlgorithmName());
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n + this.N;
        n = 31 * n + this.bufferLenBits;
        n = 31 * n + this.bufferLenTrits;
        n = 31 * n + this.c;
        n = 31 * n + this.db;
        n = 31 * n + this.df;
        n = 31 * n + this.df1;
        n = 31 * n + this.df2;
        n = 31 * n + this.df3;
        n = 31 * n + this.dg;
        n = 31 * n + this.dm0;
        n = 31 * n + this.dr;
        n = 31 * n + this.dr1;
        n = 31 * n + this.dr2;
        n = 31 * n + this.dr3;
        n = 31 * n + (this.fastFp ? 1231 : 1237);
        n = 31 * n + (this.hashAlg == null ? 0 : this.hashAlg.getAlgorithmName().hashCode());
        n = 31 * n + (this.hashSeed ? 1231 : 1237);
        n = 31 * n + this.llen;
        n = 31 * n + this.maxMsgLenBytes;
        n = 31 * n + this.minCallsMask;
        n = 31 * n + this.minCallsR;
        n = 31 * n + Arrays.hashCode(this.oid);
        n = 31 * n + this.pkLen;
        n = 31 * n + this.polyType;
        n = 31 * n + this.q;
        n = 31 * n + (this.sparse ? 1231 : 1237);
        return n;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        NTRUEncryptionKeyGenerationParameters nTRUEncryptionKeyGenerationParameters = (NTRUEncryptionKeyGenerationParameters)object;
        if (this.N != nTRUEncryptionKeyGenerationParameters.N) {
            return false;
        }
        if (this.bufferLenBits != nTRUEncryptionKeyGenerationParameters.bufferLenBits) {
            return false;
        }
        if (this.bufferLenTrits != nTRUEncryptionKeyGenerationParameters.bufferLenTrits) {
            return false;
        }
        if (this.c != nTRUEncryptionKeyGenerationParameters.c) {
            return false;
        }
        if (this.db != nTRUEncryptionKeyGenerationParameters.db) {
            return false;
        }
        if (this.df != nTRUEncryptionKeyGenerationParameters.df) {
            return false;
        }
        if (this.df1 != nTRUEncryptionKeyGenerationParameters.df1) {
            return false;
        }
        if (this.df2 != nTRUEncryptionKeyGenerationParameters.df2) {
            return false;
        }
        if (this.df3 != nTRUEncryptionKeyGenerationParameters.df3) {
            return false;
        }
        if (this.dg != nTRUEncryptionKeyGenerationParameters.dg) {
            return false;
        }
        if (this.dm0 != nTRUEncryptionKeyGenerationParameters.dm0) {
            return false;
        }
        if (this.dr != nTRUEncryptionKeyGenerationParameters.dr) {
            return false;
        }
        if (this.dr1 != nTRUEncryptionKeyGenerationParameters.dr1) {
            return false;
        }
        if (this.dr2 != nTRUEncryptionKeyGenerationParameters.dr2) {
            return false;
        }
        if (this.dr3 != nTRUEncryptionKeyGenerationParameters.dr3) {
            return false;
        }
        if (this.fastFp != nTRUEncryptionKeyGenerationParameters.fastFp) {
            return false;
        }
        if (this.hashAlg == null ? nTRUEncryptionKeyGenerationParameters.hashAlg != null : !this.hashAlg.getAlgorithmName().equals(nTRUEncryptionKeyGenerationParameters.hashAlg.getAlgorithmName())) {
            return false;
        }
        if (this.hashSeed != nTRUEncryptionKeyGenerationParameters.hashSeed) {
            return false;
        }
        if (this.llen != nTRUEncryptionKeyGenerationParameters.llen) {
            return false;
        }
        if (this.maxMsgLenBytes != nTRUEncryptionKeyGenerationParameters.maxMsgLenBytes) {
            return false;
        }
        if (this.minCallsMask != nTRUEncryptionKeyGenerationParameters.minCallsMask) {
            return false;
        }
        if (this.minCallsR != nTRUEncryptionKeyGenerationParameters.minCallsR) {
            return false;
        }
        if (!Arrays.equals(this.oid, nTRUEncryptionKeyGenerationParameters.oid)) {
            return false;
        }
        if (this.pkLen != nTRUEncryptionKeyGenerationParameters.pkLen) {
            return false;
        }
        if (this.polyType != nTRUEncryptionKeyGenerationParameters.polyType) {
            return false;
        }
        if (this.q != nTRUEncryptionKeyGenerationParameters.q) {
            return false;
        }
        return this.sparse == nTRUEncryptionKeyGenerationParameters.sparse;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("EncryptionParameters(N=" + this.N + " q=" + this.q);
        if (this.polyType == 0) {
            stringBuilder.append(" polyType=SIMPLE df=" + this.df);
        } else {
            stringBuilder.append(" polyType=PRODUCT df1=" + this.df1 + " df2=" + this.df2 + " df3=" + this.df3);
        }
        stringBuilder.append(" dm0=" + this.dm0 + " db=" + this.db + " c=" + this.c + " minCallsR=" + this.minCallsR + " minCallsMask=" + this.minCallsMask + " hashSeed=" + this.hashSeed + " hashAlg=" + this.hashAlg + " oid=" + Arrays.toString(this.oid) + " sparse=" + this.sparse + ")");
        return stringBuilder.toString();
    }
}

