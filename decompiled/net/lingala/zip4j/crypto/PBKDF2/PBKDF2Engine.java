/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.crypto.PBKDF2;

import net.lingala.zip4j.crypto.PBKDF2.MacBasedPRF;
import net.lingala.zip4j.crypto.PBKDF2.PBKDF2Parameters;
import net.lingala.zip4j.crypto.PBKDF2.PRF;
import net.lingala.zip4j.util.Zip4jUtil;

public class PBKDF2Engine {
    private PBKDF2Parameters parameters;
    private PRF prf;

    public PBKDF2Engine(PBKDF2Parameters parameters) {
        this(parameters, null);
    }

    public PBKDF2Engine(PBKDF2Parameters parameters, PRF prf) {
        this.parameters = parameters;
        this.prf = prf;
    }

    public byte[] deriveKey(char[] inputPassword, int dkLen, boolean useUtf8ForPassword) {
        if (inputPassword == null) {
            throw new NullPointerException();
        }
        byte[] p = Zip4jUtil.convertCharArrayToByteArray(inputPassword, useUtf8ForPassword);
        this.assertPRF(p);
        if (dkLen == 0) {
            dkLen = this.prf.getHLen();
        }
        return this.PBKDF2(this.prf, this.parameters.getSalt(), this.parameters.getIterationCount(), dkLen);
    }

    private void assertPRF(byte[] P2) {
        if (this.prf == null) {
            this.prf = new MacBasedPRF(this.parameters.getHashAlgorithm());
        }
        this.prf.init(P2);
    }

    private byte[] PBKDF2(PRF prf, byte[] S, int c, int dkLen) {
        if (S == null) {
            S = new byte[]{};
        }
        int hLen = prf.getHLen();
        int l = this.ceil(dkLen, hLen);
        int r = dkLen - (l - 1) * hLen;
        byte[] T = new byte[l * hLen];
        int ti_offset = 0;
        for (int i = 1; i <= l; ++i) {
            this._F(T, ti_offset, prf, S, c, i);
            ti_offset += hLen;
        }
        if (r < hLen) {
            byte[] DK = new byte[dkLen];
            System.arraycopy(T, 0, DK, 0, dkLen);
            return DK;
        }
        return T;
    }

    private int ceil(int a, int b) {
        int m4 = 0;
        if (a % b > 0) {
            m4 = 1;
        }
        return a / b + m4;
    }

    private void _F(byte[] dest, int offset, PRF prf, byte[] S, int c, int blockIndex) {
        int hLen = prf.getHLen();
        byte[] U_r = new byte[hLen];
        byte[] U_i = new byte[S.length + 4];
        System.arraycopy(S, 0, U_i, 0, S.length);
        this.INT(U_i, S.length, blockIndex);
        for (int i = 0; i < c; ++i) {
            U_i = prf.doFinal(U_i);
            this.xor(U_r, U_i);
        }
        System.arraycopy(U_r, 0, dest, offset, hLen);
    }

    private void xor(byte[] dest, byte[] src) {
        for (int i = 0; i < dest.length; ++i) {
            int n = i;
            dest[n] = (byte)(dest[n] ^ src[i]);
        }
    }

    protected void INT(byte[] dest, int offset, int i) {
        dest[offset] = (byte)(i / 0x1000000);
        dest[offset + 1] = (byte)(i / 65536);
        dest[offset + 2] = (byte)(i / 256);
        dest[offset + 3] = (byte)i;
    }

    public PBKDF2Parameters getParameters() {
        return this.parameters;
    }

    public void setParameters(PBKDF2Parameters parameters) {
        this.parameters = parameters;
    }

    public void setPseudoRandomFunction(PRF prf) {
        this.prf = prf;
    }
}

