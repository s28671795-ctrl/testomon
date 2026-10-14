/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.security.bc;

import com.hierynomus.protocol.commons.Factory;
import com.hierynomus.security.DerivationFunction;
import com.hierynomus.security.jce.derivationfunction.CounterDerivationParameters;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.KDFCounterBytesGenerator;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KDFCounterParameters;

public class BCDerivationFunctionFactory {
    private static final Map<String, Factory<DerivationFunction>> lookup = new HashMap<String, Factory<DerivationFunction>>();

    public static DerivationFunction create(String name) {
        Factory<DerivationFunction> derivationFunctionFactory = lookup.get(name);
        if (derivationFunctionFactory == null) {
            throw new IllegalArgumentException("Unknown DerivationFunction " + name);
        }
        return derivationFunctionFactory.create();
    }

    static {
        lookup.put("KDF/Counter/HMACSHA256", new Factory<DerivationFunction>(){

            @Override
            public DerivationFunction create() {
                return new BCDerivationFunction(new KDFCounterBytesGenerator(new HMac(new SHA256Digest()))){

                    @Override
                    protected DerivationParameters createParams(com.hierynomus.security.jce.derivationfunction.DerivationParameters in) {
                        if (!(in instanceof CounterDerivationParameters)) {
                            throw new IllegalArgumentException("Parameters should be a CounterDerivationParameters");
                        }
                        CounterDerivationParameters p = (CounterDerivationParameters)in;
                        return new KDFCounterParameters(p.getSeed(), p.getFixedCounterSuffix(), p.getCounterLength());
                    }
                };
            }
        });
    }

    static abstract class BCDerivationFunction
    implements DerivationFunction {
        private final org.bouncycastle.crypto.DerivationFunction function;

        public BCDerivationFunction(org.bouncycastle.crypto.DerivationFunction function) {
            this.function = function;
        }

        @Override
        public void init(com.hierynomus.security.jce.derivationfunction.DerivationParameters parameters) {
            this.function.init(this.createParams(parameters));
        }

        @Override
        public int generateBytes(byte[] out, int outOff, int len) {
            return this.function.generateBytes(out, outOff, len);
        }

        protected abstract DerivationParameters createParams(com.hierynomus.security.jce.derivationfunction.DerivationParameters var1);
    }
}

