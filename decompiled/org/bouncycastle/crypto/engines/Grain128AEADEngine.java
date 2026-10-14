/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.crypto.engines;

import java.io.ByteArrayOutputStream;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServiceProperties;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.constraints.DefaultServiceProperties;
import org.bouncycastle.crypto.engines.Utils;
import org.bouncycastle.crypto.modes.AEADCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Pack;

public class Grain128AEADEngine
implements AEADCipher {
    private static final int STATE_SIZE = 4;
    private byte[] workingKey;
    private byte[] workingIV;
    private int[] lfsr;
    private int[] nfsr;
    private int[] authAcc;
    private int[] authSr;
    private boolean initialised = false;
    private boolean aadFinished = false;
    private ErasableOutputStream aadData = new ErasableOutputStream();
    private byte[] mac;

    @Override
    public String getAlgorithmName() {
        return "Grain-128AEAD";
    }

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) throws IllegalArgumentException {
        if (!(cipherParameters instanceof ParametersWithIV)) {
            throw new IllegalArgumentException("Grain-128AEAD init parameters must include an IV");
        }
        ParametersWithIV parametersWithIV = (ParametersWithIV)cipherParameters;
        byte[] byArray = parametersWithIV.getIV();
        if (byArray == null || byArray.length != 12) {
            throw new IllegalArgumentException("Grain-128AEAD requires exactly 12 bytes of IV");
        }
        if (!(parametersWithIV.getParameters() instanceof KeyParameter)) {
            throw new IllegalArgumentException("Grain-128AEAD init parameters must include a key");
        }
        KeyParameter keyParameter = (KeyParameter)parametersWithIV.getParameters();
        byte[] byArray2 = keyParameter.getKey();
        if (byArray2.length != 16) {
            throw new IllegalArgumentException("Grain-128AEAD key must be 128 bits long");
        }
        CryptoServicesRegistrar.checkConstraints((CryptoServiceProperties)new DefaultServiceProperties(this.getAlgorithmName(), 128, cipherParameters, Utils.getPurpose(bl)));
        this.workingIV = new byte[16];
        this.workingKey = new byte[16];
        this.lfsr = new int[4];
        this.nfsr = new int[4];
        this.authAcc = new int[2];
        this.authSr = new int[2];
        System.arraycopy(byArray, 0, this.workingIV, 0, byArray.length);
        System.arraycopy(byArray2, 0, this.workingKey, 0, byArray2.length);
        this.reset();
    }

    private void initGrain() {
        int n;
        int n2;
        int n3;
        for (n3 = 0; n3 < 320; ++n3) {
            n2 = this.getOutput();
            this.nfsr = this.shift(this.nfsr, (this.getOutputNFSR() ^ this.lfsr[0] ^ n2) & 1);
            this.lfsr = this.shift(this.lfsr, (this.getOutputLFSR() ^ n2) & 1);
        }
        for (n3 = 0; n3 < 8; ++n3) {
            for (n2 = 0; n2 < 8; ++n2) {
                n = this.getOutput();
                this.nfsr = this.shift(this.nfsr, (this.getOutputNFSR() ^ this.lfsr[0] ^ n ^ this.workingKey[n3] >> n2) & 1);
                this.lfsr = this.shift(this.lfsr, (this.getOutputLFSR() ^ n ^ this.workingKey[n3 + 8] >> n2) & 1);
            }
        }
        for (n3 = 0; n3 < 2; ++n3) {
            for (n2 = 0; n2 < 32; ++n2) {
                n = this.getOutput();
                this.nfsr = this.shift(this.nfsr, (this.getOutputNFSR() ^ this.lfsr[0]) & 1);
                this.lfsr = this.shift(this.lfsr, this.getOutputLFSR() & 1);
                int n4 = n3;
                this.authAcc[n4] = this.authAcc[n4] | n << n2;
            }
        }
        for (n3 = 0; n3 < 2; ++n3) {
            for (n2 = 0; n2 < 32; ++n2) {
                n = this.getOutput();
                this.nfsr = this.shift(this.nfsr, (this.getOutputNFSR() ^ this.lfsr[0]) & 1);
                this.lfsr = this.shift(this.lfsr, this.getOutputLFSR() & 1);
                int n5 = n3;
                this.authSr[n5] = this.authSr[n5] | n << n2;
            }
        }
        this.initialised = true;
    }

    private int getOutputNFSR() {
        int n = this.nfsr[0];
        int n2 = this.nfsr[0] >>> 3;
        int n3 = this.nfsr[0] >>> 11;
        int n4 = this.nfsr[0] >>> 13;
        int n5 = this.nfsr[0] >>> 17;
        int n6 = this.nfsr[0] >>> 18;
        int n7 = this.nfsr[0] >>> 22;
        int n8 = this.nfsr[0] >>> 24;
        int n9 = this.nfsr[0] >>> 25;
        int n10 = this.nfsr[0] >>> 26;
        int n11 = this.nfsr[0] >>> 27;
        int n12 = this.nfsr[1] >>> 8;
        int n13 = this.nfsr[1] >>> 16;
        int n14 = this.nfsr[1] >>> 24;
        int n15 = this.nfsr[1] >>> 27;
        int n16 = this.nfsr[1] >>> 29;
        int n17 = this.nfsr[2] >>> 1;
        int n18 = this.nfsr[2] >>> 3;
        int n19 = this.nfsr[2] >>> 4;
        int n20 = this.nfsr[2] >>> 6;
        int n21 = this.nfsr[2] >>> 14;
        int n22 = this.nfsr[2] >>> 18;
        int n23 = this.nfsr[2] >>> 20;
        int n24 = this.nfsr[2] >>> 24;
        int n25 = this.nfsr[2] >>> 27;
        int n26 = this.nfsr[2] >>> 28;
        int n27 = this.nfsr[2] >>> 29;
        int n28 = this.nfsr[2] >>> 31;
        int n29 = this.nfsr[3];
        return (n ^ n10 ^ n14 ^ n25 ^ n29 ^ n2 & n18 ^ n3 & n4 ^ n5 & n6 ^ n11 & n15 ^ n12 & n13 ^ n16 & n17 ^ n19 & n23 ^ n7 & n8 & n9 ^ n20 & n21 & n22 ^ n24 & n26 & n27 & n28) & 1;
    }

    private int getOutputLFSR() {
        int n = this.lfsr[0];
        int n2 = this.lfsr[0] >>> 7;
        int n3 = this.lfsr[1] >>> 6;
        int n4 = this.lfsr[2] >>> 6;
        int n5 = this.lfsr[2] >>> 17;
        int n6 = this.lfsr[3];
        return (n ^ n2 ^ n3 ^ n4 ^ n5 ^ n6) & 1;
    }

    private int getOutput() {
        int n = this.nfsr[0] >>> 2;
        int n2 = this.nfsr[0] >>> 12;
        int n3 = this.nfsr[0] >>> 15;
        int n4 = this.nfsr[1] >>> 4;
        int n5 = this.nfsr[1] >>> 13;
        int n6 = this.nfsr[2];
        int n7 = this.nfsr[2] >>> 9;
        int n8 = this.nfsr[2] >>> 25;
        int n9 = this.nfsr[2] >>> 31;
        int n10 = this.lfsr[0] >>> 8;
        int n11 = this.lfsr[0] >>> 13;
        int n12 = this.lfsr[0] >>> 20;
        int n13 = this.lfsr[1] >>> 10;
        int n14 = this.lfsr[1] >>> 28;
        int n15 = this.lfsr[2] >>> 15;
        int n16 = this.lfsr[2] >>> 29;
        int n17 = this.lfsr[2] >>> 30;
        return (n2 & n10 ^ n11 & n12 ^ n9 & n13 ^ n14 & n15 ^ n2 & n9 & n17 ^ n16 ^ n ^ n3 ^ n4 ^ n5 ^ n6 ^ n7 ^ n8) & 1;
    }

    private int[] shift(int[] nArray, int n) {
        nArray[0] = nArray[0] >>> 1 | nArray[1] << 31;
        nArray[1] = nArray[1] >>> 1 | nArray[2] << 31;
        nArray[2] = nArray[2] >>> 1 | nArray[3] << 31;
        nArray[3] = nArray[3] >>> 1 | n << 31;
        return nArray;
    }

    private void setKey(byte[] byArray, byte[] byArray2) {
        byArray2[12] = -1;
        byArray2[13] = -1;
        byArray2[14] = -1;
        byArray2[15] = 127;
        this.workingKey = byArray;
        this.workingIV = byArray2;
        Pack.littleEndianToInt(this.workingKey, 0, this.nfsr);
        Pack.littleEndianToInt(this.workingIV, 0, this.lfsr);
    }

    @Override
    public int processBytes(byte[] byArray, int n, int n2, byte[] byArray2, int n3) throws DataLengthException {
        if (!this.initialised) {
            throw new IllegalStateException(this.getAlgorithmName() + " not initialised");
        }
        if (!this.aadFinished) {
            this.doProcessAADBytes(this.aadData.getBuf(), 0, this.aadData.size());
            this.aadFinished = true;
        }
        if (n + n2 > byArray.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (n3 + n2 > byArray2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        this.getKeyStream(byArray, n, n2, byArray2, n3);
        return n2;
    }

    @Override
    public void reset() {
        this.reset(true);
    }

    private void reset(boolean bl) {
        if (bl) {
            this.mac = null;
        }
        this.aadData.reset();
        this.aadFinished = false;
        this.setKey(this.workingKey, this.workingIV);
        this.initGrain();
    }

    private byte[] getKeyStream(byte[] byArray, int n, int n2, byte[] byArray2, int n3) {
        for (int i = 0; i < n2; ++i) {
            byte by = 0;
            byte by2 = byArray[n + i];
            for (int j = 0; j < 8; ++j) {
                int n4 = this.getOutput();
                this.nfsr = this.shift(this.nfsr, (this.getOutputNFSR() ^ this.lfsr[0]) & 1);
                this.lfsr = this.shift(this.lfsr, this.getOutputLFSR() & 1);
                int n5 = by2 >> j & 1;
                by = (byte)(by | (n5 ^ n4) << j);
                int n6 = -n5;
                this.authAcc[0] = this.authAcc[0] ^ this.authSr[0] & n6;
                this.authAcc[1] = this.authAcc[1] ^ this.authSr[1] & n6;
                this.authShift(this.getOutput());
                this.nfsr = this.shift(this.nfsr, (this.getOutputNFSR() ^ this.lfsr[0]) & 1);
                this.lfsr = this.shift(this.lfsr, this.getOutputLFSR() & 1);
            }
            byArray2[n3 + i] = by;
        }
        return byArray2;
    }

    @Override
    public void processAADByte(byte by) {
        if (this.aadFinished) {
            throw new IllegalStateException("associated data must be added before plaintext/ciphertext");
        }
        this.aadData.write(by);
    }

    @Override
    public void processAADBytes(byte[] byArray, int n, int n2) {
        if (this.aadFinished) {
            throw new IllegalStateException("associated data must be added before plaintext/ciphertext");
        }
        this.aadData.write(byArray, n, n2);
    }

    private void doProcessAADBytes(byte[] byArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        byte[] byArray2;
        if (n2 < 128) {
            byArray2 = new byte[1 + n2];
            byArray2[0] = (byte)n2;
            n5 = 0;
        } else {
            n5 = Grain128AEADEngine.len_length(n2);
            byArray2 = new byte[1 + n5 + n2];
            byArray2[0] = (byte)(0x80 | n5);
            n4 = n2;
            for (n3 = 0; n3 < n5; ++n3) {
                byArray2[1 + n3] = (byte)n4;
                n4 >>>= 8;
            }
        }
        for (n4 = 0; n4 < n2; ++n4) {
            byArray2[1 + n5 + n4] = byArray[n + n4];
        }
        for (n4 = 0; n4 < byArray2.length; ++n4) {
            n3 = byArray2[n4];
            for (int i = 0; i < 8; ++i) {
                this.nfsr = this.shift(this.nfsr, (this.getOutputNFSR() ^ this.lfsr[0]) & 1);
                this.lfsr = this.shift(this.lfsr, this.getOutputLFSR() & 1);
                int n6 = n3 >> i & 1;
                int n7 = -n6;
                this.authAcc[0] = this.authAcc[0] ^ this.authSr[0] & n7;
                this.authAcc[1] = this.authAcc[1] ^ this.authSr[1] & n7;
                this.authShift(this.getOutput());
                this.nfsr = this.shift(this.nfsr, (this.getOutputNFSR() ^ this.lfsr[0]) & 1);
                this.lfsr = this.shift(this.lfsr, this.getOutputLFSR() & 1);
            }
        }
    }

    private void accumulate() {
        this.authAcc[0] = this.authAcc[0] ^ this.authSr[0];
        this.authAcc[1] = this.authAcc[1] ^ this.authSr[1];
    }

    private void authShift(int n) {
        this.authSr[0] = this.authSr[0] >>> 1 | this.authSr[1] << 31;
        this.authSr[1] = this.authSr[1] >>> 1 | n << 31;
    }

    @Override
    public int processByte(byte by, byte[] byArray, int n) throws DataLengthException {
        return this.processBytes(new byte[]{by}, 0, 1, byArray, n);
    }

    @Override
    public int doFinal(byte[] byArray, int n) throws IllegalStateException, InvalidCipherTextException {
        if (!this.aadFinished) {
            this.doProcessAADBytes(this.aadData.getBuf(), 0, this.aadData.size());
            this.aadFinished = true;
        }
        this.accumulate();
        this.mac = Pack.intToLittleEndian(this.authAcc);
        System.arraycopy(this.mac, 0, byArray, n, this.mac.length);
        this.reset(false);
        return this.mac.length;
    }

    @Override
    public byte[] getMac() {
        return this.mac;
    }

    @Override
    public int getUpdateOutputSize(int n) {
        return n;
    }

    @Override
    public int getOutputSize(int n) {
        return n + 8;
    }

    private static int len_length(int n) {
        if ((n & 0xFF) == n) {
            return 1;
        }
        if ((n & 0xFFFF) == n) {
            return 2;
        }
        if ((n & 0xFFFFFF) == n) {
            return 3;
        }
        return 4;
    }

    private static final class ErasableOutputStream
    extends ByteArrayOutputStream {
        public byte[] getBuf() {
            return this.buf;
        }
    }
}

