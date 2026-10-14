/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.security.bc;

import com.hierynomus.protocol.commons.Factory;
import com.hierynomus.security.AEADBlockCipher;
import com.hierynomus.security.Cipher;
import com.hierynomus.security.SecurityException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.spec.GCMParameterSpec;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CCMBlockCipher;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;

public class BCAEADCipherFactory {
    private static final Map<String, Factory<AEADBlockCipher>> lookup = new HashMap<String, Factory<AEADBlockCipher>>();

    public static AEADBlockCipher create(String name) {
        Factory<AEADBlockCipher> cipherFactory = lookup.get(name);
        if (cipherFactory == null) {
            throw new IllegalArgumentException("Unknown AEADCipher " + name);
        }
        return cipherFactory.create();
    }

    static {
        lookup.put("AES/CCM/NoPadding", new Factory<AEADBlockCipher>(){

            @Override
            public AEADBlockCipher create() {
                return new BCAEADBlockCipher(new CCMBlockCipher(new AESEngine())){

                    @Override
                    protected CipherParameters createParams(byte[] key, GCMParameterSpec gcmParameterSpec) {
                        return new AEADParameters(new KeyParameter(key), gcmParameterSpec.getTLen(), gcmParameterSpec.getIV());
                    }
                };
            }
        });
        lookup.put("AES/GCM/NoPadding", new Factory<AEADBlockCipher>(){

            @Override
            public AEADBlockCipher create() {
                return new BCAEADBlockCipher(new GCMBlockCipher(new AESEngine())){

                    @Override
                    protected CipherParameters createParams(byte[] key, GCMParameterSpec gcmParameterSpec) {
                        return new AEADParameters(new KeyParameter(key), gcmParameterSpec.getTLen(), gcmParameterSpec.getIV());
                    }
                };
            }
        });
    }

    private static abstract class BCAEADBlockCipher
    implements AEADBlockCipher {
        private org.bouncycastle.crypto.modes.AEADBlockCipher wrappedCipher;

        BCAEADBlockCipher(org.bouncycastle.crypto.modes.AEADBlockCipher aeadBlockCipher) {
            this.wrappedCipher = aeadBlockCipher;
        }

        @Override
        public void init(Cipher.CryptMode cryptMode, byte[] bytes, GCMParameterSpec gcmParameterSpec) throws SecurityException {
            this.wrappedCipher.init(cryptMode == Cipher.CryptMode.ENCRYPT, this.createParams(bytes, gcmParameterSpec));
        }

        @Override
        public void updateAAD(byte[] aad, int aadOffset, int aadLength) throws SecurityException {
            this.wrappedCipher.processAADBytes(aad, aadOffset, aadLength);
        }

        @Override
        public byte[] update(byte[] in, int inOffset, int inLength) throws SecurityException {
            int outputSize = this.wrappedCipher.getUpdateOutputSize(inLength);
            byte[] out = new byte[outputSize];
            this.wrappedCipher.processBytes(in, inOffset, inLength, out, 0);
            return out;
        }

        @Override
        public byte[] doFinal(byte[] in, int inOffset, int inLength) throws SecurityException {
            int outOff = 0;
            int outputSizeWithFinal = this.wrappedCipher.getOutputSize(inLength);
            byte[] out = new byte[outputSizeWithFinal];
            outOff += this.wrappedCipher.processBytes(in, inOffset, inLength, out, outOff);
            try {
                outOff += this.wrappedCipher.doFinal(out, outOff);
            }
            catch (InvalidCipherTextException e) {
                throw new SecurityException(e);
            }
            return out;
        }

        @Override
        public void reset() {
            this.wrappedCipher.reset();
        }

        protected abstract CipherParameters createParams(byte[] var1, GCMParameterSpec var2);
    }
}

