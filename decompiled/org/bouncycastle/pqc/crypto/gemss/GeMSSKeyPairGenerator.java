/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.gemss;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.pqc.crypto.gemss.GeMSSEngine;
import org.bouncycastle.pqc.crypto.gemss.GeMSSKeyGenerationParameters;
import org.bouncycastle.pqc.crypto.gemss.GeMSSParameters;
import org.bouncycastle.pqc.crypto.gemss.GeMSSPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.gemss.GeMSSPublicKeyParameters;
import org.bouncycastle.pqc.crypto.gemss.Pointer;
import org.bouncycastle.pqc.crypto.gemss.PointerUnion;

public class GeMSSKeyPairGenerator
implements AsymmetricCipherKeyPairGenerator {
    private SecureRandom random;
    private GeMSSParameters parameters;

    @Override
    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.random = keyGenerationParameters.getRandom();
        this.parameters = ((GeMSSKeyGenerationParameters)keyGenerationParameters).getParameters();
    }

    @Override
    public AsymmetricCipherKeyPair generateKeyPair() {
        GeMSSEngine geMSSEngine = this.parameters.getEngine();
        byte[] byArray = this.sec_rand(geMSSEngine.SIZE_SEED_SK);
        int n = 2 + geMSSEngine.HFEDegJ + (geMSSEngine.HFEDegI * (geMSSEngine.HFEDegI + 1) >>> 1);
        int n2 = n + (geMSSEngine.NB_MONOMIAL_VINEGAR - 1) + (geMSSEngine.HFEDegI + 1) * geMSSEngine.HFEv;
        int n3 = n2 * geMSSEngine.NB_WORD_GFqn;
        int n4 = n3 + (geMSSEngine.LTRIANGULAR_NV_SIZE << 1) + (geMSSEngine.LTRIANGULAR_N_SIZE << 1) << 3;
        Pointer pointer = new Pointer(n4 >>> 3);
        byte[] byArray2 = new byte[n4];
        SHAKEDigest sHAKEDigest = new SHAKEDigest(geMSSEngine.ShakeBitStrength);
        sHAKEDigest.update(byArray, 0, geMSSEngine.SIZE_SEED_SK);
        sHAKEDigest.doFinal(byArray2, 0, n4);
        byte[] byArray3 = new byte[geMSSEngine.SIZE_SEED_SK];
        int n5 = geMSSEngine.NB_MONOMIAL_PK * geMSSEngine.HFEm + 7 >> 3;
        byte[] byArray4 = new byte[n5];
        System.arraycopy(byArray, 0, byArray3, 0, byArray3.length);
        pointer.fill(0, byArray2, 0, byArray2.length);
        geMSSEngine.cleanMonicHFEv_gf2nx(pointer);
        Pointer pointer2 = new Pointer(geMSSEngine.NB_MONOMIAL_PK * geMSSEngine.NB_WORD_GFqn);
        if (geMSSEngine.HFEDeg > 34) {
            geMSSEngine.genSecretMQS_gf2_opt(pointer2, pointer);
        }
        Pointer pointer3 = new Pointer(geMSSEngine.MATRIXnv_SIZE);
        Pointer pointer4 = new Pointer(pointer3);
        Pointer pointer5 = new Pointer(pointer, n3);
        Pointer pointer6 = new Pointer(pointer5, geMSSEngine.LTRIANGULAR_NV_SIZE);
        geMSSEngine.cleanLowerMatrix(pointer5, GeMSSEngine.FunctionParams.NV);
        geMSSEngine.cleanLowerMatrix(pointer6, GeMSSEngine.FunctionParams.NV);
        geMSSEngine.invMatrixLU_gf2(pointer3, pointer5, pointer6, GeMSSEngine.FunctionParams.NV);
        if (geMSSEngine.HFEDeg <= 34) {
            int n6 = geMSSEngine.interpolateHFE_FS_ref(pointer2, pointer, pointer3);
            if (n6 != 0) {
                throw new IllegalArgumentException("Error");
            }
        } else {
            geMSSEngine.changeVariablesMQS64_gf2(pointer2, pointer3);
        }
        pointer5.move(geMSSEngine.LTRIANGULAR_NV_SIZE << 1);
        pointer6.changeIndex(pointer5.getIndex() + geMSSEngine.LTRIANGULAR_N_SIZE);
        geMSSEngine.cleanLowerMatrix(pointer5, GeMSSEngine.FunctionParams.N);
        geMSSEngine.cleanLowerMatrix(pointer6, GeMSSEngine.FunctionParams.N);
        geMSSEngine.invMatrixLU_gf2(pointer4, pointer5, pointer6, GeMSSEngine.FunctionParams.N);
        if (geMSSEngine.HFEmr8 != 0) {
            Object object;
            int n7;
            int n8 = geMSSEngine.NB_MONOMIAL_PK * geMSSEngine.NB_BYTES_GFqm + (8 - (geMSSEngine.NB_BYTES_GFqm & 7) & 7);
            PointerUnion pointerUnion = new PointerUnion(n8);
            int n9 = n7 = (geMSSEngine.NB_BYTES_GFqm & 7) != 0 ? 1 : 0;
            while (n7 < geMSSEngine.NB_MONOMIAL_PK) {
                geMSSEngine.vecMatProduct(pointerUnion, pointer2, pointer4, GeMSSEngine.FunctionParams.M);
                pointer2.move(geMSSEngine.NB_WORD_GFqn);
                pointerUnion.moveNextBytes(geMSSEngine.NB_BYTES_GFqm);
                ++n7;
            }
            if ((geMSSEngine.NB_BYTES_GFqm & 7) != 0) {
                object = new Pointer(geMSSEngine.NB_WORD_GF2m);
                geMSSEngine.vecMatProduct((Pointer)object, pointer2, pointer4, GeMSSEngine.FunctionParams.M);
                for (n7 = 0; n7 < geMSSEngine.NB_WORD_GF2m; ++n7) {
                    pointerUnion.set(n7, ((Pointer)object).get(n7));
                }
            }
            pointerUnion.indexReset();
            object = new byte[geMSSEngine.HFEmr8 * geMSSEngine.NB_BYTES_EQUATION];
            geMSSEngine.convMQS_one_to_last_mr8_equations_gf2((byte[])object, pointerUnion);
            pointerUnion.indexReset();
            if (geMSSEngine.HFENr8 != 0 && geMSSEngine.HFEmr8 > 1) {
                geMSSEngine.convMQS_one_eq_to_hybrid_rep8_uncomp_gf2(byArray4, pointerUnion, (byte[])object);
            } else {
                geMSSEngine.convMQS_one_eq_to_hybrid_rep8_comp_gf2(byArray4, pointerUnion, (byte[])object);
            }
        } else {
            PointerUnion pointerUnion = new PointerUnion(geMSSEngine.NB_WORD_GF2m << 3);
            int n10 = 0;
            for (int i = 0; i < geMSSEngine.NB_MONOMIAL_PK; ++i) {
                geMSSEngine.vecMatProduct(pointerUnion, pointer2, pointer4, GeMSSEngine.FunctionParams.M);
                n10 = pointerUnion.toBytesMove(byArray4, n10, geMSSEngine.NB_BYTES_GFqm);
                pointerUnion.indexReset();
                pointer2.move(geMSSEngine.NB_WORD_GFqn);
            }
        }
        return new AsymmetricCipherKeyPair(new GeMSSPublicKeyParameters(this.parameters, byArray4), new GeMSSPrivateKeyParameters(this.parameters, byArray3));
    }

    private byte[] sec_rand(int n) {
        byte[] byArray = new byte[n];
        this.random.nextBytes(byArray);
        return byArray;
    }
}

