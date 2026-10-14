/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.hqc;

import org.bouncycastle.pqc.crypto.KEMParameters;
import org.bouncycastle.pqc.crypto.hqc.HQCEngine;

public class HQCParameters
implements KEMParameters {
    public static final HQCParameters hqc128 = new HQCParameters("hqc-128", 17669, 46, 384, 16, 31, 15, 66, 75, 75, 16767881, 4, new int[]{89, 69, 153, 116, 176, 117, 111, 75, 73, 233, 242, 233, 65, 210, 21, 139, 103, 173, 67, 118, 105, 210, 174, 110, 74, 69, 228, 82, 255, 181, 1});
    public static final HQCParameters hqc192 = new HQCParameters("hqc-192", 35851, 56, 640, 24, 33, 16, 100, 114, 114, 16742417, 5, new int[]{45, 216, 239, 24, 253, 104, 27, 40, 107, 50, 163, 210, 227, 134, 224, 158, 119, 13, 158, 1, 238, 164, 82, 43, 15, 232, 246, 142, 50, 189, 29, 232, 1});
    public static final HQCParameters hqc256 = new HQCParameters("hqc-256", 57637, 90, 640, 32, 59, 29, 131, 149, 149, 16772367, 5, new int[]{49, 167, 49, 39, 200, 121, 124, 91, 240, 63, 148, 71, 150, 123, 87, 101, 32, 215, 159, 71, 201, 115, 97, 210, 186, 183, 141, 217, 123, 12, 31, 243, 180, 219, 152, 239, 99, 141, 4, 246, 191, 144, 8, 232, 47, 27, 141, 178, 130, 64, 124, 47, 39, 188, 216, 48, 199, 187, 1});
    private final String name;
    private int n;
    private int n1;
    private int n2;
    private int k;
    private int g;
    private int delta;
    private int w;
    private int wr;
    private int we;
    private int utilRejectionThreshold;
    private int fft;
    private int[] generatorPoly;
    static final int PARAM_M = 8;
    static final int GF_MUL_ORDER = 255;
    private HQCEngine hqcEngine;

    private HQCParameters(String string, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int[] nArray) {
        this.name = string;
        this.n = n;
        this.n1 = n2;
        this.n2 = n3;
        this.k = n4;
        this.delta = n6;
        this.w = n7;
        this.wr = n8;
        this.we = n9;
        this.generatorPoly = nArray;
        this.g = n5;
        this.utilRejectionThreshold = n10;
        this.fft = n11;
        this.hqcEngine = new HQCEngine(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, nArray);
    }

    int getN() {
        return this.n;
    }

    int getK() {
        return this.k;
    }

    int getDelta() {
        return this.delta;
    }

    int getW() {
        return this.w;
    }

    int getWr() {
        return this.wr;
    }

    int getWe() {
        return this.we;
    }

    int getN1() {
        return this.n1;
    }

    int getN2() {
        return this.n2;
    }

    int getSHA512_BYTES() {
        return 64;
    }

    int getSALT_SIZE_BYTES() {
        return 16;
    }

    int getN_BYTES() {
        return (this.n + 7) / 8;
    }

    int getN1N2_BYTES() {
        return (this.n1 * this.n2 + 7) / 8;
    }

    HQCEngine getEngine() {
        return this.hqcEngine;
    }

    public int getSessionKeySize() {
        return this.k * 8;
    }

    public String getName() {
        return this.name;
    }
}

