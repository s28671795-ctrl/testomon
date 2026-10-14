/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.ntru;

import org.bouncycastle.crypto.EncapsulatedSecretExtractor;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.pqc.crypto.ntru.NTRUOWCPA;
import org.bouncycastle.pqc.crypto.ntru.NTRUParameters;
import org.bouncycastle.pqc.crypto.ntru.NTRUPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.ntru.OWCPADecryptResult;
import org.bouncycastle.pqc.math.ntru.parameters.NTRUParameterSet;
import org.bouncycastle.util.Arrays;

public class NTRUKEMExtractor
implements EncapsulatedSecretExtractor {
    private final NTRUParameters params;
    private final NTRUPrivateKeyParameters ntruPrivateKey;

    public NTRUKEMExtractor(NTRUPrivateKeyParameters nTRUPrivateKeyParameters) {
        this.params = nTRUPrivateKeyParameters.getParameters();
        this.ntruPrivateKey = nTRUPrivateKeyParameters;
    }

    @Override
    public byte[] extractSecret(byte[] byArray) {
        int n;
        NTRUParameterSet nTRUParameterSet = this.params.parameterSet;
        byte[] byArray2 = this.ntruPrivateKey.privateKey;
        byte[] byArray3 = new byte[nTRUParameterSet.prfKeyBytes() + nTRUParameterSet.ntruCiphertextBytes()];
        NTRUOWCPA nTRUOWCPA = new NTRUOWCPA(nTRUParameterSet);
        OWCPADecryptResult oWCPADecryptResult = nTRUOWCPA.decrypt(byArray, this.ntruPrivateKey.privateKey);
        byte[] byArray4 = oWCPADecryptResult.rm;
        int n2 = oWCPADecryptResult.fail;
        SHA3Digest sHA3Digest = new SHA3Digest(256);
        byte[] byArray5 = new byte[sHA3Digest.getDigestSize()];
        sHA3Digest.update(byArray4, 0, byArray4.length);
        sHA3Digest.doFinal(byArray5, 0);
        for (n = 0; n < nTRUParameterSet.prfKeyBytes(); ++n) {
            byArray3[n] = byArray2[n + nTRUParameterSet.owcpaSecretKeyBytes()];
        }
        for (n = 0; n < nTRUParameterSet.ntruCiphertextBytes(); ++n) {
            byArray3[nTRUParameterSet.prfKeyBytes() + n] = byArray[n];
        }
        sHA3Digest.reset();
        sHA3Digest.update(byArray3, 0, byArray3.length);
        sHA3Digest.doFinal(byArray4, 0);
        this.cmov(byArray5, byArray4, (byte)n2);
        byte[] byArray6 = Arrays.copyOfRange(byArray5, 0, nTRUParameterSet.sharedKeyBytes());
        Arrays.clear(byArray5);
        return byArray6;
    }

    private void cmov(byte[] byArray, byte[] byArray2, byte by) {
        by = (byte)(~by + 1);
        for (int i = 0; i < byArray.length; ++i) {
            int n = i;
            byArray[n] = (byte)(byArray[n] ^ by & (byArray2[i] ^ byArray[i]));
        }
    }

    @Override
    public int getEncapsulationLength() {
        return this.params.parameterSet.ntruCiphertextBytes();
    }
}

