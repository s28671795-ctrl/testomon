/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import org.conscrypt.EvpMdRef;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeRef;
import org.conscrypt.OpenSSLKey;

public class OpenSSLSignature
extends SignatureSpi {
    private NativeRef.EVP_MD_CTX ctx;
    private OpenSSLKey key;
    private final EngineType engineType;
    private final long evpMdRef;
    private final byte[] singleByte = new byte[1];
    private boolean signing;
    private long evpPkeyCtx;

    private OpenSSLSignature(long evpMdRef, EngineType engineType) {
        this.engineType = engineType;
        this.evpMdRef = evpMdRef;
    }

    private void resetContext() throws InvalidAlgorithmParameterException {
        NativeRef.EVP_MD_CTX ctxLocal = new NativeRef.EVP_MD_CTX(NativeCrypto.EVP_MD_CTX_create());
        this.evpPkeyCtx = this.signing ? NativeCrypto.EVP_DigestSignInit(ctxLocal, this.evpMdRef, this.key.getNativeRef()) : NativeCrypto.EVP_DigestVerifyInit(ctxLocal, this.evpMdRef, this.key.getNativeRef());
        this.configureEVP_PKEY_CTX(this.evpPkeyCtx);
        this.ctx = ctxLocal;
    }

    protected void configureEVP_PKEY_CTX(long ctx) throws InvalidAlgorithmParameterException {
    }

    @Override
    protected void engineUpdate(byte input) {
        this.singleByte[0] = input;
        this.engineUpdate(this.singleByte, 0, 1);
    }

    @Override
    protected void engineUpdate(byte[] input, int offset, int len) {
        NativeRef.EVP_MD_CTX ctxLocal = this.ctx;
        if (this.signing) {
            NativeCrypto.EVP_DigestSignUpdate(ctxLocal, input, offset, len);
        } else {
            NativeCrypto.EVP_DigestVerifyUpdate(ctxLocal, input, offset, len);
        }
    }

    @Override
    protected void engineUpdate(ByteBuffer input) {
        if (!input.hasRemaining()) {
            return;
        }
        if (!input.isDirect()) {
            super.engineUpdate(input);
            return;
        }
        long baseAddress = NativeCrypto.getDirectBufferAddress(input);
        if (baseAddress == 0L) {
            super.engineUpdate(input);
            return;
        }
        int position = input.position();
        if (position < 0) {
            throw new RuntimeException("Negative position");
        }
        long ptr = baseAddress + (long)position;
        int len = input.remaining();
        if (len < 0) {
            throw new RuntimeException("Negative remaining amount");
        }
        NativeRef.EVP_MD_CTX ctxLocal = this.ctx;
        if (this.signing) {
            NativeCrypto.EVP_DigestSignUpdateDirect(ctxLocal, ptr, len);
        } else {
            NativeCrypto.EVP_DigestVerifyUpdateDirect(ctxLocal, ptr, len);
        }
        input.position(position + len);
    }

    @Override
    @Deprecated
    protected Object engineGetParameter(String param2) throws InvalidParameterException {
        return null;
    }

    private void checkEngineType(OpenSSLKey pkey) throws InvalidKeyException {
        int pkeyType = NativeCrypto.EVP_PKEY_type(pkey.getNativeRef());
        switch (this.engineType) {
            case RSA: {
                if (pkeyType == 6) break;
                throw new InvalidKeyException("Signature initialized as " + (Object)((Object)this.engineType) + " (not RSA)");
            }
            case EC: {
                if (pkeyType == 408) break;
                throw new InvalidKeyException("Signature initialized as " + (Object)((Object)this.engineType) + " (not EC)");
            }
            default: {
                throw new InvalidKeyException("Key must be of type " + (Object)((Object)this.engineType));
            }
        }
    }

    private void initInternal(OpenSSLKey newKey, boolean signing) throws InvalidKeyException {
        this.checkEngineType(newKey);
        this.key = newKey;
        this.signing = signing;
        try {
            this.resetContext();
        }
        catch (InvalidAlgorithmParameterException e) {
            throw new InvalidKeyException(e);
        }
    }

    @Override
    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        this.initInternal(OpenSSLKey.fromPrivateKey(privateKey), true);
    }

    @Override
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        this.initInternal(OpenSSLKey.fromPublicKey(publicKey), false);
    }

    @Override
    @Deprecated
    protected void engineSetParameter(String param2, Object value) throws InvalidParameterException {
    }

    @Override
    protected byte[] engineSign() throws SignatureException {
        NativeRef.EVP_MD_CTX ctxLocal = this.ctx;
        try {
            byte[] byArray = NativeCrypto.EVP_DigestSignFinal(ctxLocal);
            return byArray;
        }
        catch (Exception ex) {
            throw new SignatureException(ex);
        }
        finally {
            try {
                this.resetContext();
            }
            catch (InvalidAlgorithmParameterException e) {
                throw new AssertionError((Object)"Reset of context failed after it was successful once");
            }
        }
    }

    @Override
    protected boolean engineVerify(byte[] sigBytes) throws SignatureException {
        NativeRef.EVP_MD_CTX ctxLocal = this.ctx;
        try {
            boolean bl = NativeCrypto.EVP_DigestVerifyFinal(ctxLocal, sigBytes, 0, sigBytes.length);
            return bl;
        }
        catch (Exception ex) {
            throw new SignatureException(ex);
        }
        finally {
            try {
                this.resetContext();
            }
            catch (InvalidAlgorithmParameterException e) {
                throw new AssertionError((Object)"Reset of context failed after it was successful once");
            }
        }
    }

    protected final long getEVP_PKEY_CTX() {
        return this.evpPkeyCtx;
    }

    public static final class SHA512RSAPSS
    extends RSAPSSPadding {
        public SHA512RSAPSS() {
            super(EvpMdRef.SHA512.EVP_MD, "SHA-512", EvpMdRef.SHA512.SIZE_BYTES);
        }
    }

    public static final class SHA384RSAPSS
    extends RSAPSSPadding {
        public SHA384RSAPSS() {
            super(EvpMdRef.SHA384.EVP_MD, "SHA-384", EvpMdRef.SHA384.SIZE_BYTES);
        }
    }

    public static final class SHA256RSAPSS
    extends RSAPSSPadding {
        public SHA256RSAPSS() {
            super(EvpMdRef.SHA256.EVP_MD, "SHA-256", EvpMdRef.SHA256.SIZE_BYTES);
        }
    }

    public static final class SHA224RSAPSS
    extends RSAPSSPadding {
        public SHA224RSAPSS() {
            super(EvpMdRef.SHA224.EVP_MD, "SHA-224", EvpMdRef.SHA224.SIZE_BYTES);
        }
    }

    public static final class SHA1RSAPSS
    extends RSAPSSPadding {
        public SHA1RSAPSS() {
            super(EvpMdRef.SHA1.EVP_MD, "SHA-1", EvpMdRef.SHA1.SIZE_BYTES);
        }
    }

    static abstract class RSAPSSPadding
    extends OpenSSLSignature {
        private static final int TRAILER_FIELD_BC_ID = 1;
        private final String contentDigestAlgorithm;
        private String mgf1DigestAlgorithm;
        private long mgf1EvpMdRef;
        private int saltSizeBytes;

        RSAPSSPadding(long contentDigestEvpMdRef, String contentDigestAlgorithm, int saltSizeBytes) {
            super(contentDigestEvpMdRef, EngineType.RSA);
            this.contentDigestAlgorithm = contentDigestAlgorithm;
            this.mgf1DigestAlgorithm = contentDigestAlgorithm;
            this.mgf1EvpMdRef = contentDigestEvpMdRef;
            this.saltSizeBytes = saltSizeBytes;
        }

        @Override
        protected final void configureEVP_PKEY_CTX(long ctx) throws InvalidAlgorithmParameterException {
            NativeCrypto.EVP_PKEY_CTX_set_rsa_padding(ctx, 6);
            NativeCrypto.EVP_PKEY_CTX_set_rsa_mgf1_md(ctx, this.mgf1EvpMdRef);
            NativeCrypto.EVP_PKEY_CTX_set_rsa_pss_saltlen(ctx, this.saltSizeBytes);
        }

        @Override
        protected final void engineSetParameter(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            long specMgf1EvpMdRef;
            if (!(params instanceof PSSParameterSpec)) {
                throw new InvalidAlgorithmParameterException("Unsupported parameter: " + params + ". Only " + PSSParameterSpec.class.getName() + " supported");
            }
            PSSParameterSpec spec = (PSSParameterSpec)params;
            String specContentDigest = EvpMdRef.getJcaDigestAlgorithmStandardName(spec.getDigestAlgorithm());
            if (specContentDigest == null) {
                throw new InvalidAlgorithmParameterException("Unsupported content digest algorithm: " + spec.getDigestAlgorithm());
            }
            if (!this.contentDigestAlgorithm.equalsIgnoreCase(specContentDigest)) {
                throw new InvalidAlgorithmParameterException("Changing content digest algorithm not supported");
            }
            String specMgfAlgorithm = spec.getMGFAlgorithm();
            if (!"MGF1".equalsIgnoreCase(specMgfAlgorithm) && !"1.2.840.113549.1.1.8".equals(specMgfAlgorithm)) {
                throw new InvalidAlgorithmParameterException("Unsupported MGF algorithm: " + specMgfAlgorithm + ". Only " + "MGF1" + " supported");
            }
            AlgorithmParameterSpec mgfSpec = spec.getMGFParameters();
            if (!(mgfSpec instanceof MGF1ParameterSpec)) {
                throw new InvalidAlgorithmParameterException("Unsupported MGF parameters: " + mgfSpec + ". Only " + MGF1ParameterSpec.class.getName() + " supported");
            }
            MGF1ParameterSpec specMgf1Spec = (MGF1ParameterSpec)spec.getMGFParameters();
            String specMgf1Digest = EvpMdRef.getJcaDigestAlgorithmStandardName(specMgf1Spec.getDigestAlgorithm());
            if (specMgf1Digest == null) {
                throw new InvalidAlgorithmParameterException("Unsupported MGF1 digest algorithm: " + specMgf1Spec.getDigestAlgorithm());
            }
            try {
                specMgf1EvpMdRef = EvpMdRef.getEVP_MDByJcaDigestAlgorithmStandardName(specMgf1Digest);
            }
            catch (NoSuchAlgorithmException e) {
                throw new ProviderException("Failed to obtain EVP_MD for " + specMgf1Digest, e);
            }
            int specSaltSizeBytes = spec.getSaltLength();
            if (specSaltSizeBytes < 0) {
                throw new InvalidAlgorithmParameterException("Salt length must be non-negative: " + specSaltSizeBytes);
            }
            int specTrailer = spec.getTrailerField();
            if (specTrailer != 1) {
                throw new InvalidAlgorithmParameterException("Unsupported trailer field: " + specTrailer + ". Only " + 1 + " supported");
            }
            this.mgf1DigestAlgorithm = specMgf1Digest;
            this.mgf1EvpMdRef = specMgf1EvpMdRef;
            this.saltSizeBytes = specSaltSizeBytes;
            long ctx = this.getEVP_PKEY_CTX();
            if (ctx != 0L) {
                this.configureEVP_PKEY_CTX(ctx);
            }
        }

        @Override
        protected final AlgorithmParameters engineGetParameters() {
            try {
                AlgorithmParameters result = AlgorithmParameters.getInstance("PSS");
                result.init(new PSSParameterSpec(this.contentDigestAlgorithm, "MGF1", new MGF1ParameterSpec(this.mgf1DigestAlgorithm), this.saltSizeBytes, 1));
                return result;
            }
            catch (NoSuchAlgorithmException e) {
                throw new ProviderException("Failed to create PSS AlgorithmParameters", e);
            }
            catch (InvalidParameterSpecException e) {
                throw new ProviderException("Failed to create PSS AlgorithmParameters", e);
            }
        }
    }

    public static final class SHA512ECDSA
    extends OpenSSLSignature {
        public SHA512ECDSA() {
            super(EvpMdRef.SHA512.EVP_MD, EngineType.EC);
        }
    }

    public static final class SHA384ECDSA
    extends OpenSSLSignature {
        public SHA384ECDSA() {
            super(EvpMdRef.SHA384.EVP_MD, EngineType.EC);
        }
    }

    public static final class SHA256ECDSA
    extends OpenSSLSignature {
        public SHA256ECDSA() {
            super(EvpMdRef.SHA256.EVP_MD, EngineType.EC);
        }
    }

    public static final class SHA224ECDSA
    extends OpenSSLSignature {
        public SHA224ECDSA() {
            super(EvpMdRef.SHA224.EVP_MD, EngineType.EC);
        }
    }

    public static final class SHA1ECDSA
    extends OpenSSLSignature {
        public SHA1ECDSA() {
            super(EvpMdRef.SHA1.EVP_MD, EngineType.EC);
        }
    }

    public static final class SHA512RSA
    extends RSAPKCS1Padding {
        public SHA512RSA() {
            super(EvpMdRef.SHA512.EVP_MD);
        }
    }

    public static final class SHA384RSA
    extends RSAPKCS1Padding {
        public SHA384RSA() {
            super(EvpMdRef.SHA384.EVP_MD);
        }
    }

    public static final class SHA256RSA
    extends RSAPKCS1Padding {
        public SHA256RSA() {
            super(EvpMdRef.SHA256.EVP_MD);
        }
    }

    public static final class SHA224RSA
    extends RSAPKCS1Padding {
        public SHA224RSA() {
            super(EvpMdRef.SHA224.EVP_MD);
        }
    }

    public static final class SHA1RSA
    extends RSAPKCS1Padding {
        public SHA1RSA() {
            super(EvpMdRef.SHA1.EVP_MD);
        }
    }

    public static final class MD5RSA
    extends RSAPKCS1Padding {
        public MD5RSA() {
            super(EvpMdRef.MD5.EVP_MD);
        }
    }

    static abstract class RSAPKCS1Padding
    extends OpenSSLSignature {
        RSAPKCS1Padding(long evpMdRef) {
            super(evpMdRef, EngineType.RSA);
        }

        @Override
        protected final void configureEVP_PKEY_CTX(long ctx) throws InvalidAlgorithmParameterException {
            NativeCrypto.EVP_PKEY_CTX_set_rsa_padding(ctx, 1);
        }
    }

    private static enum EngineType {
        RSA,
        EC;

    }
}

