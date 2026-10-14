/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.gemss;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.gemss.GeMSSParameters;

public class GeMSSKeyParameters
extends AsymmetricKeyParameter {
    final GeMSSParameters parameters;

    protected GeMSSKeyParameters(boolean bl, GeMSSParameters geMSSParameters) {
        super(bl);
        this.parameters = geMSSParameters;
    }

    public GeMSSParameters getParameters() {
        return this.parameters;
    }
}

