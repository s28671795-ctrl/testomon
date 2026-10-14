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
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Locale;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.conscrypt.EmptyArray;

public abstract class OpenSSLCipher
extends CipherSpi {
    Mode mode = Mode.ECB;
    private Padding padding = Padding.PKCS5PADDING;
    byte[] encodedKey;
    byte[] iv;
    private boolean encrypting;
    private int blockSize;

    OpenSSLCipher() {
    }

    OpenSSLCipher(Mode mode, Padding padding) {
        this.mode = mode;
        this.padding = padding;
        this.blockSize = this.getCipherBlockSize();
    }

    abstract void engineInitInternal(byte[] var1, AlgorithmParameterSpec var2, SecureRandom var3) throws InvalidKeyException, InvalidAlgorithmParameterException;

    abstract int updateInternal(byte[] var1, int var2, int var3, byte[] var4, int var5, int var6) throws ShortBufferException;

    abstract int doFinalInternal(byte[] var1, int var2, int var3) throws IllegalBlockSizeException, BadPaddingException, ShortBufferException;

    abstract String getBaseCipherName();

    abstract void checkSupportedKeySize(int var1) throws InvalidKeyException;

    abstract void checkSupportedMode(Mode var1) throws NoSuchAlgorithmException;

    abstract void checkSupportedPadding(Padding var1) throws NoSuchPaddingException;

    abstract int getCipherBlockSize();

    boolean supportsVariableSizeKey() {
        return false;
    }

    boolean supportsVariableSizeIv() {
        return false;
    }

    @Override
    protected void engineSetMode(String modeStr) throws NoSuchAlgorithmException {
        Mode mode;
        try {
            mode = Mode.getNormalized(modeStr);
        }
        catch (IllegalArgumentException e) {
            NoSuchAlgorithmException newE = new NoSuchAlgorithmException("No such mode: " + modeStr);
            newE.initCause(e);
            throw newE;
        }
        this.checkSupportedMode(mode);
        this.mode = mode;
    }

    @Override
    protected void engineSetPadding(String paddingStr) throws NoSuchPaddingException {
        Padding padding;
        try {
            padding = Padding.getNormalized(paddingStr);
        }
        catch (IllegalArgumentException e) {
            NoSuchPaddingException newE = new NoSuchPaddingException("No such padding: " + paddingStr);
            newE.initCause(e);
            throw newE;
        }
        this.checkSupportedPadding(padding);
        this.padding = padding;
    }

    Padding getPadding() {
        return this.padding;
    }

    @Override
    protected int engineGetBlockSize() {
        return this.blockSize;
    }

    abstract int getOutputSizeForFinal(int var1);

    abstract int getOutputSizeForUpdate(int var1);

    @Override
    protected int engineGetOutputSize(int inputLen) {
        return Math.max(this.getOutputSizeForUpdate(inputLen), this.getOutputSizeForFinal(inputLen));
    }

    @Override
    protected byte[] engineGetIV() {
        return this.iv;
    }

    @Override
    protected AlgorithmParameters engineGetParameters() {
        if (this.iv != null && this.iv.length > 0) {
            try {
                AlgorithmParameters params = AlgorithmParameters.getInstance(this.getBaseCipherName());
                params.init(new IvParameterSpec(this.iv));
                return params;
            }
            catch (NoSuchAlgorithmException e) {
                return null;
            }
            catch (InvalidParameterSpecException e) {
                return null;
            }
        }
        return null;
    }

    protected AlgorithmParameterSpec getParameterSpec(AlgorithmParameters params) throws InvalidAlgorithmParameterException {
        if (params != null) {
            try {
                return params.getParameterSpec(IvParameterSpec.class);
            }
            catch (InvalidParameterSpecException e) {
                throw new InvalidAlgorithmParameterException("Params must be convertible to IvParameterSpec", e);
            }
        }
        return null;
    }

    @Override
    protected void engineInit(int opmode, Key key, SecureRandom random) throws InvalidKeyException {
        this.checkAndSetEncodedKey(opmode, key);
        try {
            this.engineInitInternal(this.encodedKey, null, random);
        }
        catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void engineInit(int opmode, Key key, AlgorithmParameterSpec params, SecureRandom random) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.checkAndSetEncodedKey(opmode, key);
        this.engineInitInternal(this.encodedKey, params, random);
    }

    @Override
    protected void engineInit(int opmode, Key key, AlgorithmParameters params, SecureRandom random) throws InvalidKeyException, InvalidAlgorithmParameterException {
        AlgorithmParameterSpec spec = this.getParameterSpec(params);
        this.engineInit(opmode, key, spec, random);
    }

    @Override
    protected byte[] engineUpdate(byte[] input, int inputOffset, int inputLen) {
        int bytesWritten;
        int maximumLen = this.getOutputSizeForUpdate(inputLen);
        byte[] output = maximumLen > 0 ? new byte[maximumLen] : EmptyArray.BYTE;
        try {
            bytesWritten = this.updateInternal(input, inputOffset, inputLen, output, 0, maximumLen);
        }
        catch (ShortBufferException e) {
            throw new RuntimeException("calculated buffer size was wrong: " + maximumLen);
        }
        if (output.length == bytesWritten) {
            return output;
        }
        if (bytesWritten == 0) {
            return EmptyArray.BYTE;
        }
        return Arrays.copyOfRange(output, 0, bytesWritten);
    }

    @Override
    protected int engineUpdate(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) throws ShortBufferException {
        int maximumLen = this.getOutputSizeForUpdate(inputLen);
        return this.updateInternal(input, inputOffset, inputLen, output, outputOffset, maximumLen);
    }

    @Override
    protected byte[] engineDoFinal(byte[] input, int inputOffset, int inputLen) throws IllegalBlockSizeException, BadPaddingException {
        int bytesWritten;
        int maximumLen = this.getOutputSizeForFinal(inputLen);
        byte[] output = new byte[maximumLen];
        if (inputLen > 0) {
            try {
                bytesWritten = this.updateInternal(input, inputOffset, inputLen, output, 0, maximumLen);
            }
            catch (ShortBufferException e) {
                throw new RuntimeException("our calculated buffer was too small", e);
            }
        } else {
            bytesWritten = 0;
        }
        try {
            bytesWritten += this.doFinalInternal(output, bytesWritten, maximumLen - bytesWritten);
        }
        catch (ShortBufferException e) {
            throw new RuntimeException("our calculated buffer was too small", e);
        }
        if (bytesWritten == output.length) {
            return output;
        }
        if (bytesWritten == 0) {
            return EmptyArray.BYTE;
        }
        return Arrays.copyOfRange(output, 0, bytesWritten);
    }

    @Override
    protected int engineDoFinal(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        int bytesWritten;
        if (output == null) {
            throw new NullPointerException("output == null");
        }
        int maximumLen = this.getOutputSizeForFinal(inputLen);
        if (inputLen > 0) {
            bytesWritten = this.updateInternal(input, inputOffset, inputLen, output, outputOffset, maximumLen);
            outputOffset += bytesWritten;
            maximumLen -= bytesWritten;
        } else {
            bytesWritten = 0;
        }
        return bytesWritten + this.doFinalInternal(output, outputOffset, maximumLen);
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

    @Override
    protected int engineGetKeySize(Key key) throws InvalidKeyException {
        if (!(key instanceof SecretKey)) {
            throw new InvalidKeyException("Only SecretKey is supported");
        }
        byte[] encodedKey = key.getEncoded();
        if (encodedKey == null) {
            throw new InvalidKeyException("key.getEncoded() == null");
        }
        this.checkSupportedKeySize(encodedKey.length);
        return encodedKey.length * 8;
    }

    private byte[] checkAndSetEncodedKey(int opmode, Key key) throws InvalidKeyException {
        if (opmode == 1 || opmode == 3) {
            this.encrypting = true;
        } else if (opmode == 2 || opmode == 4) {
            this.encrypting = false;
        } else {
            throw new InvalidParameterException("Unsupported opmode " + opmode);
        }
        if (!(key instanceof SecretKey)) {
            throw new InvalidKeyException("Only SecretKey is supported");
        }
        byte[] encodedKey = key.getEncoded();
        if (encodedKey == null) {
            throw new InvalidKeyException("key.getEncoded() == null");
        }
        this.checkSupportedKeySize(encodedKey.length);
        this.encodedKey = encodedKey;
        return encodedKey;
    }

    boolean isEncrypting() {
        return this.encrypting;
    }

    static enum Padding {
        NOPADDING,
        PKCS5PADDING,
        PKCS7PADDING;


        public static Padding getNormalized(String value) {
            Padding p = Padding.valueOf(value.toUpperCase(Locale.US));
            if (p == PKCS7PADDING) {
                return PKCS5PADDING;
            }
            return p;
        }
    }

    static enum Mode {
        NONE,
        CBC,
        CTR,
        ECB,
        GCM,
        GCM_SIV,
        POLY1305;


        public static Mode getNormalized(String modeString) {
            if ((modeString = modeString.toUpperCase(Locale.US)).equals("GCM-SIV")) {
                return GCM_SIV;
            }
            if (modeString.equals("GCM_SIV")) {
                throw new IllegalArgumentException("Invalid mode");
            }
            return Mode.valueOf(modeString);
        }
    }
}

