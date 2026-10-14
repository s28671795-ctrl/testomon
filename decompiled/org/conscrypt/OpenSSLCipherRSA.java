/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Locale;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;
import org.conscrypt.EmptyArray;
import org.conscrypt.EvpMdRef;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeRef;
import org.conscrypt.OpenSSLKey;
import org.conscrypt.OpenSSLRSAPrivateCrtKey;
import org.conscrypt.OpenSSLRSAPrivateKey;
import org.conscrypt.OpenSSLRSAPublicKey;
import org.conscrypt.ShortBufferWithoutStackTraceException;

public abstract class OpenSSLCipherRSA
extends CipherSpi {
    OpenSSLKey key;
    boolean usingPrivateKey;
    boolean encrypting;
    private byte[] buffer;
    private int bufferOffset;
    private boolean inputTooLarge;
    int padding = 1;

    OpenSSLCipherRSA(int padding) {
        this.padding = padding;
    }

    @Override
    protected void engineSetMode(String mode) throws NoSuchAlgorithmException {
        String modeUpper = mode.toUpperCase(Locale.ROOT);
        if ("NONE".equals(modeUpper) || "ECB".equals(modeUpper)) {
            return;
        }
        throw new NoSuchAlgorithmException("mode not supported: " + mode);
    }

    @Override
    protected void engineSetPadding(String padding) throws NoSuchPaddingException {
        String paddingUpper = padding.toUpperCase(Locale.ROOT);
        if ("PKCS1PADDING".equals(paddingUpper)) {
            this.padding = 1;
            return;
        }
        if ("NOPADDING".equals(paddingUpper)) {
            this.padding = 3;
            return;
        }
        throw new NoSuchPaddingException("padding not supported: " + padding);
    }

    @Override
    protected int engineGetBlockSize() {
        if (this.encrypting) {
            return this.paddedBlockSizeBytes();
        }
        return this.keySizeBytes();
    }

    @Override
    protected int engineGetOutputSize(int inputLen) {
        if (this.encrypting) {
            return this.keySizeBytes();
        }
        return this.paddedBlockSizeBytes();
    }

    int paddedBlockSizeBytes() {
        int paddedBlockSizeBytes = this.keySizeBytes();
        if (this.padding == 1) {
            --paddedBlockSizeBytes;
            paddedBlockSizeBytes -= 10;
        }
        return paddedBlockSizeBytes;
    }

    int keySizeBytes() {
        if (!this.isInitialized()) {
            throw new IllegalStateException("cipher is not initialized");
        }
        return NativeCrypto.RSA_size(this.key.getNativeRef());
    }

    boolean isInitialized() {
        return this.key != null;
    }

    @Override
    protected byte[] engineGetIV() {
        return null;
    }

    @Override
    protected AlgorithmParameters engineGetParameters() {
        return null;
    }

    void doCryptoInit(AlgorithmParameterSpec spec) throws InvalidAlgorithmParameterException, InvalidKeyException {
    }

    void engineInitInternal(int opmode, Key key, AlgorithmParameterSpec spec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (opmode == 1 || opmode == 3) {
            this.encrypting = true;
        } else if (opmode == 2 || opmode == 4) {
            this.encrypting = false;
        } else {
            throw new InvalidParameterException("Unsupported opmode " + opmode);
        }
        if (key instanceof OpenSSLRSAPrivateKey) {
            OpenSSLRSAPrivateKey rsaPrivateKey = (OpenSSLRSAPrivateKey)key;
            this.usingPrivateKey = true;
            this.key = rsaPrivateKey.getOpenSSLKey();
        } else if (key instanceof RSAPrivateCrtKey) {
            RSAPrivateCrtKey rsaPrivateKey = (RSAPrivateCrtKey)key;
            this.usingPrivateKey = true;
            this.key = OpenSSLRSAPrivateCrtKey.getInstance(rsaPrivateKey);
        } else if (key instanceof RSAPrivateKey) {
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)key;
            this.usingPrivateKey = true;
            this.key = OpenSSLRSAPrivateKey.getInstance(rsaPrivateKey);
        } else if (key instanceof OpenSSLRSAPublicKey) {
            OpenSSLRSAPublicKey rsaPublicKey = (OpenSSLRSAPublicKey)key;
            this.usingPrivateKey = false;
            this.key = rsaPublicKey.getOpenSSLKey();
        } else if (key instanceof RSAPublicKey) {
            RSAPublicKey rsaPublicKey = (RSAPublicKey)key;
            this.usingPrivateKey = false;
            this.key = OpenSSLRSAPublicKey.getInstance(rsaPublicKey);
        } else {
            if (null == key) {
                throw new InvalidKeyException("RSA private or public key is null");
            }
            throw new InvalidKeyException("Need RSA private or public key");
        }
        this.buffer = new byte[NativeCrypto.RSA_size(this.key.getNativeRef())];
        this.bufferOffset = 0;
        this.inputTooLarge = false;
        this.doCryptoInit(spec);
    }

    @Override
    protected int engineGetKeySize(Key key) throws InvalidKeyException {
        if (key instanceof OpenSSLRSAPrivateKey) {
            return ((OpenSSLRSAPrivateKey)key).getModulus().bitLength();
        }
        if (key instanceof RSAPrivateCrtKey) {
            return ((RSAPrivateCrtKey)key).getModulus().bitLength();
        }
        if (key instanceof RSAPrivateKey) {
            return ((RSAPrivateKey)key).getModulus().bitLength();
        }
        if (key instanceof OpenSSLRSAPublicKey) {
            return ((OpenSSLRSAPublicKey)key).getModulus().bitLength();
        }
        if (key instanceof RSAPublicKey) {
            return ((RSAPublicKey)key).getModulus().bitLength();
        }
        if (null == key) {
            throw new InvalidKeyException("RSA private or public key is null");
        }
        throw new InvalidKeyException("Need RSA private or public key");
    }

    @Override
    protected void engineInit(int opmode, Key key, SecureRandom random) throws InvalidKeyException {
        try {
            this.engineInitInternal(opmode, key, null);
        }
        catch (InvalidAlgorithmParameterException e) {
            throw new InvalidKeyException("Algorithm parameters rejected when none supplied", e);
        }
    }

    @Override
    protected void engineInit(int opmode, Key key, AlgorithmParameterSpec params, SecureRandom random) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (params != null) {
            throw new InvalidAlgorithmParameterException("unknown param type: " + params.getClass().getName());
        }
        this.engineInitInternal(opmode, key, params);
    }

    @Override
    protected void engineInit(int opmode, Key key, AlgorithmParameters params, SecureRandom random) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (params != null) {
            throw new InvalidAlgorithmParameterException("unknown param type: " + params.getClass().getName());
        }
        this.engineInitInternal(opmode, key, null);
    }

    @Override
    protected byte[] engineUpdate(byte[] input, int inputOffset, int inputLen) {
        if (this.bufferOffset + inputLen > this.buffer.length) {
            this.inputTooLarge = true;
            return EmptyArray.BYTE;
        }
        System.arraycopy(input, inputOffset, this.buffer, this.bufferOffset, inputLen);
        this.bufferOffset += inputLen;
        return EmptyArray.BYTE;
    }

    @Override
    protected int engineUpdate(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) throws ShortBufferException {
        this.engineUpdate(input, inputOffset, inputLen);
        return 0;
    }

    @Override
    protected byte[] engineDoFinal(byte[] input, int inputOffset, int inputLen) throws IllegalBlockSizeException, BadPaddingException {
        byte[] tmpBuf;
        if (input != null) {
            this.engineUpdate(input, inputOffset, inputLen);
        }
        if (this.inputTooLarge) {
            throw new IllegalBlockSizeException("input must be under " + this.buffer.length + " bytes");
        }
        if (this.bufferOffset != this.buffer.length) {
            if (this.padding == 3) {
                tmpBuf = new byte[this.buffer.length];
                System.arraycopy(this.buffer, 0, tmpBuf, this.buffer.length - this.bufferOffset, this.bufferOffset);
            } else {
                tmpBuf = Arrays.copyOf(this.buffer, this.bufferOffset);
            }
        } else {
            tmpBuf = this.buffer;
        }
        byte[] output = new byte[this.buffer.length];
        int resultSize = this.doCryptoOperation(tmpBuf, output);
        if (!this.encrypting && resultSize != output.length) {
            output = Arrays.copyOf(output, resultSize);
        }
        this.bufferOffset = 0;
        return output;
    }

    abstract int doCryptoOperation(byte[] var1, byte[] var2) throws BadPaddingException, IllegalBlockSizeException;

    @Override
    protected int engineDoFinal(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        byte[] b = this.engineDoFinal(input, inputOffset, inputLen);
        int lastOffset = outputOffset + b.length;
        if (lastOffset > output.length) {
            throw new ShortBufferWithoutStackTraceException("output buffer is too small " + output.length + " < " + lastOffset);
        }
        System.arraycopy(b, 0, output, outputOffset, b.length);
        return b.length;
    }

    @Override
    protected byte[] engineWrap(Key key) throws IllegalBlockSizeException, InvalidKeyException {
        try {
            byte[] encoded = key.getEncoded();
            return this.engineDoFinal(encoded, 0, encoded.length);
        }
        catch (BadPaddingException e) {
            IllegalBlockSizeException newE = new IllegalBlockSizeException();
            newE.initCause(e);
            throw newE;
        }
    }

    @Override
    protected Key engineUnwrap(byte[] wrappedKey, String wrappedKeyAlgorithm, int wrappedKeyType) throws InvalidKeyException, NoSuchAlgorithmException {
        try {
            byte[] encoded = this.engineDoFinal(wrappedKey, 0, wrappedKey.length);
            if (wrappedKeyType == 1) {
                KeyFactory keyFactory = KeyFactory.getInstance(wrappedKeyAlgorithm);
                return keyFactory.generatePublic(new X509EncodedKeySpec(encoded));
            }
            if (wrappedKeyType == 2) {
                KeyFactory keyFactory = KeyFactory.getInstance(wrappedKeyAlgorithm);
                return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encoded));
            }
            if (wrappedKeyType == 3) {
                return new SecretKeySpec(encoded, wrappedKeyAlgorithm);
            }
            throw new UnsupportedOperationException("wrappedKeyType == " + wrappedKeyType);
        }
        catch (IllegalBlockSizeException e) {
            throw new InvalidKeyException(e);
        }
        catch (BadPaddingException e) {
            throw new InvalidKeyException(e);
        }
        catch (InvalidKeySpecException e) {
            throw new InvalidKeyException(e);
        }
    }

    public static class OAEP
    extends OpenSSLCipherRSA {
        private long oaepMd;
        private int oaepMdSizeBytes;
        private long mgf1Md;
        private byte[] label;
        private NativeRef.EVP_PKEY_CTX pkeyCtx;

        public OAEP(long defaultMd, int defaultMdSizeBytes) {
            super(4);
            this.oaepMd = this.mgf1Md = defaultMd;
            this.oaepMdSizeBytes = defaultMdSizeBytes;
        }

        @Override
        protected AlgorithmParameters engineGetParameters() {
            if (!this.isInitialized()) {
                return null;
            }
            try {
                AlgorithmParameters params = AlgorithmParameters.getInstance("OAEP");
                PSource.PSpecified pSrc = this.label == null ? PSource.PSpecified.DEFAULT : new PSource.PSpecified(this.label);
                params.init(new OAEPParameterSpec(EvpMdRef.getJcaDigestAlgorithmStandardNameFromEVP_MD(this.oaepMd), "MGF1", new MGF1ParameterSpec(EvpMdRef.getJcaDigestAlgorithmStandardNameFromEVP_MD(this.mgf1Md)), pSrc));
                return params;
            }
            catch (NoSuchAlgorithmException e) {
                throw (Error)((Throwable)((Object)new AssertionError((Object)"OAEP not supported"))).initCause(e);
            }
            catch (InvalidParameterSpecException e) {
                throw new RuntimeException("No providers of AlgorithmParameters.OAEP available");
            }
        }

        @Override
        protected void engineSetPadding(String padding) throws NoSuchPaddingException {
            String paddingUpper = padding.toUpperCase(Locale.US);
            if (paddingUpper.equals("OAEPPADDING")) {
                this.padding = 4;
                return;
            }
            throw new NoSuchPaddingException("Only OAEP padding is supported");
        }

        @Override
        protected void engineInit(int opmode, Key key, AlgorithmParameterSpec spec, SecureRandom random) throws InvalidKeyException, InvalidAlgorithmParameterException {
            if (spec != null && !(spec instanceof OAEPParameterSpec)) {
                throw new InvalidAlgorithmParameterException("Only OAEPParameterSpec accepted in OAEP mode");
            }
            this.engineInitInternal(opmode, key, spec);
        }

        @Override
        protected void engineInit(int opmode, Key key, AlgorithmParameters params, SecureRandom random) throws InvalidKeyException, InvalidAlgorithmParameterException {
            OAEPParameterSpec spec = null;
            if (params != null) {
                try {
                    spec = params.getParameterSpec(OAEPParameterSpec.class);
                }
                catch (InvalidParameterSpecException e) {
                    throw new InvalidAlgorithmParameterException("Only OAEP parameters are supported", e);
                }
            }
            this.engineInitInternal(opmode, key, spec);
        }

        @Override
        void engineInitInternal(int opmode, Key key, AlgorithmParameterSpec spec) throws InvalidKeyException, InvalidAlgorithmParameterException {
            if (opmode == 1 || opmode == 3) {
                if (!(key instanceof PublicKey)) {
                    throw new InvalidKeyException("Only public keys may be used to encrypt");
                }
            } else if (!(opmode != 2 && opmode != 4 || key instanceof PrivateKey)) {
                throw new InvalidKeyException("Only private keys may be used to decrypt");
            }
            super.engineInitInternal(opmode, key, spec);
        }

        @Override
        void doCryptoInit(AlgorithmParameterSpec spec) throws InvalidAlgorithmParameterException, InvalidKeyException {
            this.pkeyCtx = new NativeRef.EVP_PKEY_CTX(this.encrypting ? NativeCrypto.EVP_PKEY_encrypt_init(this.key.getNativeRef()) : NativeCrypto.EVP_PKEY_decrypt_init(this.key.getNativeRef()));
            if (spec instanceof OAEPParameterSpec) {
                this.readOAEPParameters((OAEPParameterSpec)spec);
            }
            NativeCrypto.EVP_PKEY_CTX_set_rsa_padding(this.pkeyCtx.address, 4);
            NativeCrypto.EVP_PKEY_CTX_set_rsa_oaep_md(this.pkeyCtx.address, this.oaepMd);
            NativeCrypto.EVP_PKEY_CTX_set_rsa_mgf1_md(this.pkeyCtx.address, this.mgf1Md);
            if (this.label != null && this.label.length > 0) {
                NativeCrypto.EVP_PKEY_CTX_set_rsa_oaep_label(this.pkeyCtx.address, this.label);
            }
        }

        @Override
        int paddedBlockSizeBytes() {
            int paddedBlockSizeBytes = this.keySizeBytes();
            return paddedBlockSizeBytes - (2 * this.oaepMdSizeBytes + 2);
        }

        private void readOAEPParameters(OAEPParameterSpec spec) throws InvalidAlgorithmParameterException {
            String mgfAlgUpper = spec.getMGFAlgorithm().toUpperCase(Locale.US);
            AlgorithmParameterSpec mgfSpec = spec.getMGFParameters();
            if (!"MGF1".equals(mgfAlgUpper) && !"1.2.840.113549.1.1.8".equals(mgfAlgUpper) || !(mgfSpec instanceof MGF1ParameterSpec)) {
                throw new InvalidAlgorithmParameterException("Only MGF1 supported as mask generation function");
            }
            MGF1ParameterSpec mgf1spec = (MGF1ParameterSpec)mgfSpec;
            String oaepAlgUpper = spec.getDigestAlgorithm().toUpperCase(Locale.US);
            try {
                this.oaepMd = EvpMdRef.getEVP_MDByJcaDigestAlgorithmStandardName(oaepAlgUpper);
                this.oaepMdSizeBytes = EvpMdRef.getDigestSizeBytesByJcaDigestAlgorithmStandardName(oaepAlgUpper);
                this.mgf1Md = EvpMdRef.getEVP_MDByJcaDigestAlgorithmStandardName(mgf1spec.getDigestAlgorithm());
            }
            catch (NoSuchAlgorithmException e) {
                throw new InvalidAlgorithmParameterException(e);
            }
            PSource pSource = spec.getPSource();
            if (!"PSpecified".equals(pSource.getAlgorithm()) || !(pSource instanceof PSource.PSpecified)) {
                throw new InvalidAlgorithmParameterException("Only PSpecified accepted for PSource");
            }
            this.label = ((PSource.PSpecified)pSource).getValue();
        }

        @Override
        int doCryptoOperation(byte[] tmpBuf, byte[] output) throws BadPaddingException, IllegalBlockSizeException {
            if (this.encrypting) {
                return NativeCrypto.EVP_PKEY_encrypt(this.pkeyCtx, output, 0, tmpBuf, 0, tmpBuf.length);
            }
            return NativeCrypto.EVP_PKEY_decrypt(this.pkeyCtx, output, 0, tmpBuf, 0, tmpBuf.length);
        }

        public static final class SHA512
        extends OAEP {
            public SHA512() {
                super(EvpMdRef.SHA512.EVP_MD, EvpMdRef.SHA512.SIZE_BYTES);
            }
        }

        public static final class SHA384
        extends OAEP {
            public SHA384() {
                super(EvpMdRef.SHA384.EVP_MD, EvpMdRef.SHA384.SIZE_BYTES);
            }
        }

        public static final class SHA256
        extends OAEP {
            public SHA256() {
                super(EvpMdRef.SHA256.EVP_MD, EvpMdRef.SHA256.SIZE_BYTES);
            }
        }

        public static final class SHA224
        extends OAEP {
            public SHA224() {
                super(EvpMdRef.SHA224.EVP_MD, EvpMdRef.SHA224.SIZE_BYTES);
            }
        }

        public static final class SHA1
        extends OAEP {
            public SHA1() {
                super(EvpMdRef.SHA1.EVP_MD, EvpMdRef.SHA1.SIZE_BYTES);
            }
        }
    }

    public static final class Raw
    extends DirectRSA {
        public Raw() {
            super(3);
        }
    }

    public static final class PKCS1
    extends DirectRSA {
        public PKCS1() {
            super(1);
        }
    }

    public static abstract class DirectRSA
    extends OpenSSLCipherRSA {
        public DirectRSA(int padding) {
            super(padding);
        }

        @Override
        int doCryptoOperation(byte[] tmpBuf, byte[] output) throws BadPaddingException, IllegalBlockSizeException {
            int resultSize;
            if (this.encrypting) {
                resultSize = this.usingPrivateKey ? NativeCrypto.RSA_private_encrypt(tmpBuf.length, tmpBuf, output, this.key.getNativeRef(), this.padding) : NativeCrypto.RSA_public_encrypt(tmpBuf.length, tmpBuf, output, this.key.getNativeRef(), this.padding);
            } else {
                try {
                    resultSize = this.usingPrivateKey ? NativeCrypto.RSA_private_decrypt(tmpBuf.length, tmpBuf, output, this.key.getNativeRef(), this.padding) : NativeCrypto.RSA_public_decrypt(tmpBuf.length, tmpBuf, output, this.key.getNativeRef(), this.padding);
                }
                catch (SignatureException e) {
                    IllegalBlockSizeException newE = new IllegalBlockSizeException();
                    newE.initCause(e);
                    throw newE;
                }
            }
            return resultSize;
        }
    }
}

