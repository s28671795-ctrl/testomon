/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.legacy.crypto.ntru;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.legacy.crypto.ntru.NTRUEncryptionParameters;

public class NTRUEncryptionKeyParameters
extends AsymmetricKeyParameter {
    protected final NTRUEncryptionParameters params;

    public NTRUEncryptionKeyParameters(boolean bl, NTRUEncryptionParameters nTRUEncryptionParameters) {
        super(bl);
        this.params = nTRUEncryptionParameters;
    }

    public NTRUEncryptionParameters getParameters() {
        return this.params;
    }
}

