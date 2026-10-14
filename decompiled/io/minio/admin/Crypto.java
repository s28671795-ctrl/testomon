/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.modes.AEADCipher;
import org.bouncycastle.crypto.modes.ChaCha20Poly1305;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.bouncycastle.crypto.params.KeyParameter;

public class Crypto {
    private static final int TAG_LENGTH = 16;
    private static final int CHUNK_SIZE = 16384;
    private static final int MAX_CHUNK_SIZE = 16400;
    private static final int SALT_LENGTH = 32;
    private static final int NONCE_LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    private static byte[] random(int length) {
        byte[] data = new byte[length];
        RANDOM.nextBytes(data);
        return data;
    }

    private static byte[] appendBytes(byte[] ... args2) {
        if (args2.length == 1) {
            return args2[0];
        }
        int length = 0;
        for (byte[] arg : args2) {
            length += arg.length;
        }
        ByteBuffer buf = ByteBuffer.allocate(length);
        for (byte[] arg : args2) {
            buf.put(arg);
        }
        return buf.array();
    }

    private static int[] readFully(InputStream inputStream2, byte[] buf, boolean raiseEof) throws EOFException, IOException {
        int totalBytesRead = 0;
        int eof = 0;
        int offset = 0;
        while (totalBytesRead < buf.length) {
            int bytesToRead = buf.length - totalBytesRead;
            int bytesRead = inputStream2.read(buf, offset, bytesToRead);
            if (bytesRead < 0) {
                if (raiseEof) {
                    throw new EOFException("EOF occurred");
                }
                eof = 1;
                break;
            }
            totalBytesRead += bytesRead;
            offset += bytesRead;
        }
        return new int[]{totalBytesRead, eof};
    }

    private static AEADCipher getEncryptDecryptCipher(boolean encryptFlag, int aeadId, byte[] key, byte[] paddedNonce) {
        AEADCipher cipher = null;
        switch (aeadId) {
            case 0: {
                cipher = GCMBlockCipher.newInstance((BlockCipher)AESEngine.newInstance());
                break;
            }
            case 1: {
                cipher = new ChaCha20Poly1305();
                break;
            }
            default: {
                throw new IllegalArgumentException("unknown AEAD ID " + aeadId);
            }
        }
        cipher.init(encryptFlag, new AEADParameters(new KeyParameter(key), 128, paddedNonce));
        return cipher;
    }

    private static AEADCipher getEncryptCipher(int aeadId, byte[] key, byte[] paddedNonce) {
        return Crypto.getEncryptDecryptCipher(true, aeadId, key, paddedNonce);
    }

    private static AEADCipher getDecryptCipher(int aeadId, byte[] key, byte[] paddedNonce) {
        return Crypto.getEncryptDecryptCipher(false, aeadId, key, paddedNonce);
    }

    private static byte[] generateKey(byte[] secret, byte[] salt) {
        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        generator.init(new Argon2Parameters.Builder(2).withVersion(19).withSalt(salt).withMemoryAsKB(65536).withParallelism(4).withIterations(1).build());
        byte[] key = new byte[32];
        generator.generateBytes(secret, key);
        return key;
    }

    private static byte[] generateEncryptDecryptAdditionalData(boolean encryptFlag, int aeadId, byte[] key, byte[] paddedNonce) throws InvalidCipherTextException {
        AEADCipher cipher = Crypto.getEncryptCipher(aeadId, key, paddedNonce);
        int outputLength = cipher.getMac().length;
        byte[] additionalData = new byte[outputLength];
        cipher.doFinal(additionalData, 0);
        return Crypto.appendBytes({0}, additionalData);
    }

    private static byte[] generateEncryptAdditionalData(int aeadId, byte[] key, byte[] paddedNonce) throws InvalidCipherTextException {
        return Crypto.generateEncryptDecryptAdditionalData(true, aeadId, key, paddedNonce);
    }

    private static byte[] generateDecryptAdditionalData(int aeadId, byte[] key, byte[] paddedNonce) throws InvalidCipherTextException {
        return Crypto.generateEncryptDecryptAdditionalData(false, aeadId, key, paddedNonce);
    }

    private static byte[] markAsLast(byte[] additionalData) {
        additionalData[0] = -128;
        return additionalData;
    }

    private static byte[] updateNonceId(byte[] nonce, int idx) {
        byte[] idxLittleEndian = new byte[]{(byte)(idx & 0xFF), (byte)(idx >> 8 & 0xFF), (byte)(idx >> 16 & 0xFF), (byte)(idx >> 24 & 0xFF)};
        return Crypto.appendBytes(nonce, idxLittleEndian);
    }

    public static byte[] encrypt(byte[] payload, String password) throws InvalidCipherTextException {
        byte[] nonce = Crypto.random(8);
        byte[] salt = Crypto.random(32);
        byte[] key = Crypto.generateKey(password.getBytes(StandardCharsets.UTF_8), salt);
        byte[] aeadId = new byte[]{0};
        byte[] paddedNonce = Crypto.appendBytes(nonce, {0, 0, 0, 0});
        byte[] additionalData = Crypto.generateEncryptAdditionalData(aeadId[0], key, paddedNonce);
        byte[] result = Crypto.appendBytes(salt, aeadId, nonce);
        int from = 0;
        boolean done = false;
        int nonceId = 1;
        while (!done) {
            int to = from + 16384;
            if (to > payload.length) {
                additionalData = Crypto.markAsLast(additionalData);
                to = payload.length;
                done = true;
            }
            byte[] chunk = Arrays.copyOfRange(payload, from, to);
            paddedNonce = Crypto.updateNonceId(nonce, nonceId);
            AEADCipher cipher = Crypto.getEncryptCipher(aeadId[0], key, paddedNonce);
            cipher.processAADBytes(additionalData, 0, additionalData.length);
            int outputLength = cipher.getOutputSize(chunk.length);
            byte[] encryptedData = new byte[outputLength];
            int outputOffset = cipher.processBytes(chunk, 0, chunk.length, encryptedData, 0);
            cipher.doFinal(encryptedData, outputOffset);
            result = Crypto.appendBytes(result, encryptedData);
            from = to;
            ++nonceId;
        }
        return result;
    }

    public static byte[] decrypt(InputStream inputStream2, String password) throws EOFException, IOException, InvalidCipherTextException {
        DecryptReader reader = new DecryptReader(inputStream2, password.getBytes(StandardCharsets.UTF_8));
        return reader.readAllBytes();
    }

    public static class DecryptReader {
        private InputStream inputStream;
        private byte[] secret;
        private byte[] salt = new byte[32];
        private byte[] aeadId = new byte[1];
        private byte[] nonce = new byte[8];
        private byte[] key = null;
        private byte[] additionalData = null;
        private int count = 0;
        private byte[] chunk = new byte[16400];
        private byte[] oneByte = null;
        private boolean eof = false;

        public DecryptReader(InputStream inputStream2, byte[] secret) throws EOFException, IOException, InvalidCipherTextException {
            this.inputStream = inputStream2;
            this.secret = secret;
            Crypto.readFully(this.inputStream, this.salt, true);
            Crypto.readFully(this.inputStream, this.aeadId, true);
            Crypto.readFully(this.inputStream, this.nonce, true);
            this.key = Crypto.generateKey(this.secret, this.salt);
            byte[] paddedNonce = Crypto.appendBytes(new byte[][]{this.nonce, {0, 0, 0, 0}});
            this.additionalData = Crypto.generateDecryptAdditionalData(this.aeadId[0], this.key, paddedNonce);
        }

        private byte[] decrypt(byte[] encryptedData, boolean lastChunk) throws InvalidCipherTextException {
            ++this.count;
            if (lastChunk) {
                this.additionalData = Crypto.markAsLast(this.additionalData);
            }
            byte[] paddedNonce = Crypto.updateNonceId(this.nonce, this.count);
            AEADCipher cipher = Crypto.getDecryptCipher(this.aeadId[0], this.key, paddedNonce);
            cipher.processAADBytes(this.additionalData, 0, this.additionalData.length);
            int outputLength = cipher.getOutputSize(encryptedData.length);
            byte[] decryptedData = new byte[outputLength];
            int outputOffset = cipher.processBytes(encryptedData, 0, encryptedData.length, decryptedData, 0);
            cipher.doFinal(decryptedData, outputOffset);
            return decryptedData;
        }

        private byte[] readChunk() throws IOException {
            if (this.eof) {
                return new byte[0];
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (this.oneByte != null) {
                baos.write(this.oneByte);
            }
            int[] result = Crypto.readFully(this.inputStream, this.chunk, false);
            int bytesRead = result[0];
            boolean bl = this.eof = result[1] == 1;
            if (bytesRead == this.chunk.length) {
                if (this.oneByte != null) {
                    this.oneByte[0] = this.chunk[--bytesRead];
                } else if (!this.eof) {
                    this.oneByte = new byte[]{0};
                    result = Crypto.readFully(this.inputStream, this.oneByte, false);
                    boolean bl2 = this.eof = result[1] == 1;
                    if (this.eof) {
                        this.oneByte = null;
                    }
                }
            }
            baos.write(this.chunk, 0, bytesRead);
            return baos.toByteArray();
        }

        public byte[] readAllBytes() throws IOException, InvalidCipherTextException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while (!this.eof) {
                byte[] payload = this.readChunk();
                baos.write(this.decrypt(payload, this.eof));
            }
            return baos.toByteArray();
        }
    }
}

