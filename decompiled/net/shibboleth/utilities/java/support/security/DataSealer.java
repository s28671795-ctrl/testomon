/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyException;
import java.security.SecureRandom;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.logic.ConstraintViolationException;
import net.shibboleth.utilities.java.support.security.DataExpiredException;
import net.shibboleth.utilities.java.support.security.DataSealerException;
import net.shibboleth.utilities.java.support.security.DataSealerKeyStrategy;
import net.shibboleth.utilities.java.support.security.KeyNotFoundException;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSealer
extends AbstractInitializableComponent {
    private static final int CHUNK_SIZE = 60000;
    @Nonnull
    private Logger log = LoggerFactory.getLogger(DataSealer.class);
    @NonnullAfterInit
    private DataSealerKeyStrategy keyStrategy;
    @NonnullAfterInit
    private SecureRandom random;
    @Nonnull
    private BinaryEncoder encoder = new Base64(0, new byte[]{10});
    @Nonnull
    private BinaryDecoder decoder = (Base64)this.encoder;

    public void setKeyStrategy(@Nonnull DataSealerKeyStrategy strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.keyStrategy = Constraint.isNotNull(strategy, "DataSealerKeyStrategy cannot be null");
    }

    public void setRandom(@Nonnull SecureRandom r) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.random = Constraint.isNotNull(r, "SecureRandom cannot be null");
    }

    public void setEncoder(@Nonnull BinaryEncoder e) {
        this.encoder = Constraint.isNotNull(e, "Encoder cannot be null");
    }

    public void setDecoder(@Nonnull BinaryDecoder d) {
        this.decoder = Constraint.isNotNull(d, "Decoder cannot be null");
    }

    @Override
    public void doInitialize() throws ComponentInitializationException {
        try {
            try {
                Constraint.isNotNull(this.keyStrategy, "DataSealerKeyStrategy cannot be null");
            }
            catch (ConstraintViolationException e) {
                throw new ComponentInitializationException(e);
            }
            if (this.random == null) {
                this.random = new SecureRandom();
            }
            SecretKey initialKey = this.keyStrategy.getDefaultKey().getSecond();
            this.testEncryption(initialKey);
        }
        catch (KeyException e) {
            this.log.error(e.getMessage());
            throw new ComponentInitializationException("Exception loading the keystore", e);
        }
        catch (DataSealerException e) {
            this.log.error(e.getMessage());
            throw new ComponentInitializationException("Exception testing the encryption settings used", e);
        }
    }

    @Nonnull
    public String unwrap(@Nonnull @NotEmpty String wrapped) throws DataSealerException {
        return this.unwrap(wrapped, null);
    }

    @Nonnull
    public String unwrap(@Nonnull @NotEmpty String wrapped, @Nullable StringBuffer keyUsed) throws DataSealerException {
        try {
            byte[] in = this.decoder.decode(wrapped.getBytes(StandardCharsets.UTF_8));
            ByteArrayInputStream inputByteStream = new ByteArrayInputStream(in);
            DataInputStream inputDataStream = new DataInputStream(inputByteStream);
            String keyAlias = inputDataStream.readUTF();
            this.log.trace("Data was encrypted by key named '{}'", (Object)keyAlias);
            if (keyUsed != null) {
                keyUsed.append(keyAlias);
            }
            SecretKey key = this.keyStrategy.getKey(keyAlias);
            GCMBlockCipher cipher = new GCMBlockCipher(new AESEngine());
            int ivSize = cipher.getUnderlyingCipher().getBlockSize();
            byte[] iv = new byte[ivSize];
            inputDataStream.readFully(iv);
            AEADParameters aeadParams = new AEADParameters(new KeyParameter(key.getEncoded()), 128, iv, keyAlias.getBytes());
            cipher.init(false, aeadParams);
            byte[] data = new byte[in.length - ivSize];
            int dataSize = inputDataStream.read(data);
            byte[] plaintext = new byte[cipher.getOutputSize(dataSize)];
            int outputLen = cipher.processBytes(data, 0, dataSize, plaintext, 0);
            cipher.doFinal(plaintext, outputLen);
            return this.extractAndCheckDecryptedData(plaintext);
        }
        catch (IOException | IllegalStateException | DecoderException | InvalidCipherTextException e) {
            this.log.error("Exception unwrapping data", e);
            throw new DataSealerException("Exception unwrapping data", e);
        }
        catch (KeyNotFoundException e) {
            if (keyUsed != null) {
                this.log.info("Data was wrapped with a key ({}) no longer available", (Object)keyUsed.toString());
            } else {
                this.log.info("Data was wrapped with a key no longer available");
            }
            throw new DataExpiredException("Data wrapped with expired key");
        }
        catch (KeyException e) {
            this.log.error(e.getMessage());
            throw new DataSealerException("Exception loading key", e);
        }
    }

    @Nonnull
    private String extractAndCheckDecryptedData(@Nonnull @NotEmpty byte[] decryptedBytes) throws DataSealerException {
        try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(decryptedBytes);
            GZIPInputStream compressedData = new GZIPInputStream(byteStream);
            DataInputStream dataInputStream = new DataInputStream(compressedData);
            long decodedExpirationTime = dataInputStream.readLong();
            if (System.currentTimeMillis() > decodedExpirationTime) {
                this.log.debug("Unwrapped data has expired");
                throw new DataExpiredException("Unwrapped data has expired");
            }
            StringBuffer accumulator = new StringBuffer();
            int count = 0;
            try {
                while (true) {
                    String decodedData = dataInputStream.readUTF();
                    accumulator.append(decodedData);
                    this.log.trace("Read chunk #{} from output stream", (Object)(++count));
                }
            }
            catch (EOFException e) {
                this.log.trace("Unwrapped data verified");
                return accumulator.toString();
            }
        }
        catch (IOException e) {
            this.log.error(e.getMessage());
            throw new DataSealerException("Caught IOException unwrapping data", e);
        }
    }

    @Nonnull
    public String wrap(@Nonnull @NotEmpty String data, long exp) throws DataSealerException {
        if (data == null || data.length() == 0) {
            throw new IllegalArgumentException("Data must be supplied for the wrapping operation");
        }
        try {
            GCMBlockCipher cipher = new GCMBlockCipher(new AESEngine());
            byte[] iv = new byte[cipher.getUnderlyingCipher().getBlockSize()];
            this.random.nextBytes(iv);
            Pair<String, SecretKey> defaultKey = this.keyStrategy.getDefaultKey();
            AEADParameters aeadParams = new AEADParameters(new KeyParameter(defaultKey.getSecond().getEncoded()), 128, iv, defaultKey.getFirst().getBytes());
            cipher.init(true, aeadParams);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            GZIPOutputStream compressedStream = new GZIPOutputStream(byteStream);
            DataOutputStream dataStream = new DataOutputStream(compressedStream);
            dataStream.writeLong(exp);
            int count = 0;
            int dataLength = data.length();
            for (int start2 = 0; start2 < dataLength; start2 += Math.min(dataLength - start2, 60000)) {
                dataStream.writeUTF(data.substring(start2, start2 + Math.min(dataLength - start2, 60000)));
                this.log.trace("Wrote chunk #{} to output stream", (Object)(++count));
            }
            dataStream.flush();
            compressedStream.flush();
            compressedStream.finish();
            byteStream.flush();
            byte[] plaintext = byteStream.toByteArray();
            byte[] encryptedData = new byte[cipher.getOutputSize(plaintext.length)];
            int outputLen = cipher.processBytes(plaintext, 0, plaintext.length, encryptedData, 0);
            outputLen += cipher.doFinal(encryptedData, outputLen);
            ByteArrayOutputStream finalByteStream = new ByteArrayOutputStream();
            DataOutputStream finalDataStream = new DataOutputStream(finalByteStream);
            finalDataStream.writeUTF(defaultKey.getFirst());
            finalDataStream.write(iv);
            finalDataStream.write(encryptedData, 0, outputLen);
            finalDataStream.flush();
            finalByteStream.flush();
            return new String(this.encoder.encode(finalByteStream.toByteArray()), StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            this.log.error("Exception wrapping data", e);
            throw new DataSealerException("Exception wrapping data", e);
        }
    }

    private void testEncryption(@Nonnull SecretKey key) throws DataSealerException {
        String decrypted;
        try {
            GCMBlockCipher cipher = new GCMBlockCipher(new AESEngine());
            byte[] iv = new byte[cipher.getUnderlyingCipher().getBlockSize()];
            this.random.nextBytes(iv);
            AEADParameters aeadParams = new AEADParameters(new KeyParameter(key.getEncoded()), 128, iv, "aad".getBytes(StandardCharsets.UTF_8));
            cipher.init(true, aeadParams);
            byte[] plaintext = "test".getBytes(StandardCharsets.UTF_8);
            byte[] encryptedData = new byte[cipher.getOutputSize(plaintext.length)];
            int outputLen = cipher.processBytes(plaintext, 0, plaintext.length, encryptedData, 0);
            cipher.doFinal(encryptedData, outputLen);
            cipher.init(false, aeadParams);
            plaintext = new byte[cipher.getOutputSize(encryptedData.length)];
            outputLen = cipher.processBytes(encryptedData, 0, encryptedData.length, plaintext, 0);
            cipher.doFinal(plaintext, outputLen);
            decrypted = Strings.fromUTF8ByteArray(plaintext);
        }
        catch (IllegalStateException | InvalidCipherTextException e) {
            this.log.error("Round trip encryption/decryption test unsuccessful", e);
            throw new DataSealerException("Round trip encryption/decryption test unsuccessful", e);
        }
        if (decrypted == null || !"test".equals(decrypted)) {
            this.log.error("Round trip encryption/decryption test unsuccessful. Decrypted text did not match");
            throw new DataSealerException("Round trip encryption/decryption test unsuccessful");
        }
    }
}

