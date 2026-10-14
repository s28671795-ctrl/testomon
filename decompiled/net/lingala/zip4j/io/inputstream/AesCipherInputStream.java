/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.inputstream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import net.lingala.zip4j.crypto.AESDecrypter;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.inputstream.CipherInputStream;
import net.lingala.zip4j.io.inputstream.ZipEntryInputStream;
import net.lingala.zip4j.model.AESExtraDataRecord;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.util.Zip4jUtil;

class AesCipherInputStream
extends CipherInputStream<AESDecrypter> {
    private byte[] singleByteBuffer = new byte[1];
    private byte[] aes16ByteBlock = new byte[16];
    private int aes16ByteBlockPointer = 0;
    private int remainingAes16ByteBlockLength = 0;
    private int lengthToRead = 0;
    private int offsetWithAesBlock = 0;
    private int bytesCopiedInThisIteration = 0;
    private int lengthToCopyInThisIteration = 0;
    private int aes16ByteBlockReadLength = 0;

    public AesCipherInputStream(ZipEntryInputStream zipEntryInputStream, LocalFileHeader localFileHeader, char[] password, int bufferSize, boolean useUtf8ForPassword) throws IOException {
        super(zipEntryInputStream, localFileHeader, password, bufferSize, useUtf8ForPassword);
    }

    @Override
    protected AESDecrypter initializeDecrypter(LocalFileHeader localFileHeader, char[] password, boolean useUtf8ForPassword) throws IOException {
        return new AESDecrypter(localFileHeader.getAesExtraDataRecord(), password, this.getSalt(localFileHeader), this.getPasswordVerifier(), useUtf8ForPassword);
    }

    @Override
    public int read() throws IOException {
        int readLen = this.read(this.singleByteBuffer);
        if (readLen == -1) {
            return -1;
        }
        return this.singleByteBuffer[0];
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int readLen;
        this.lengthToRead = len;
        this.offsetWithAesBlock = off;
        this.bytesCopiedInThisIteration = 0;
        if (this.remainingAes16ByteBlockLength != 0) {
            this.copyBytesFromBuffer(b, this.offsetWithAesBlock);
            if (this.bytesCopiedInThisIteration == len) {
                return this.bytesCopiedInThisIteration;
            }
        }
        if (this.lengthToRead < 16) {
            this.aes16ByteBlockReadLength = super.read(this.aes16ByteBlock, 0, this.aes16ByteBlock.length);
            this.aes16ByteBlockPointer = 0;
            if (this.aes16ByteBlockReadLength == -1) {
                this.remainingAes16ByteBlockLength = 0;
                if (this.bytesCopiedInThisIteration > 0) {
                    return this.bytesCopiedInThisIteration;
                }
                return -1;
            }
            this.remainingAes16ByteBlockLength = this.aes16ByteBlockReadLength;
            this.copyBytesFromBuffer(b, this.offsetWithAesBlock);
            if (this.bytesCopiedInThisIteration == len) {
                return this.bytesCopiedInThisIteration;
            }
        }
        if ((readLen = super.read(b, this.offsetWithAesBlock, this.lengthToRead - this.lengthToRead % 16)) == -1) {
            if (this.bytesCopiedInThisIteration > 0) {
                return this.bytesCopiedInThisIteration;
            }
            return -1;
        }
        return readLen + this.bytesCopiedInThisIteration;
    }

    private void copyBytesFromBuffer(byte[] b, int off) {
        this.lengthToCopyInThisIteration = this.lengthToRead < this.remainingAes16ByteBlockLength ? this.lengthToRead : this.remainingAes16ByteBlockLength;
        System.arraycopy(this.aes16ByteBlock, this.aes16ByteBlockPointer, b, off, this.lengthToCopyInThisIteration);
        this.incrementAesByteBlockPointer(this.lengthToCopyInThisIteration);
        this.decrementRemainingAesBytesLength(this.lengthToCopyInThisIteration);
        this.bytesCopiedInThisIteration += this.lengthToCopyInThisIteration;
        this.lengthToRead -= this.lengthToCopyInThisIteration;
        this.offsetWithAesBlock += this.lengthToCopyInThisIteration;
    }

    @Override
    protected void endOfEntryReached(InputStream inputStream2, int numberOfBytesPushedBack) throws IOException {
        this.verifyContent(this.readStoredMac(inputStream2), numberOfBytesPushedBack);
    }

    private void verifyContent(byte[] storedMac, int numberOfBytesPushedBack) throws IOException {
        byte[] calculatedMac = ((AESDecrypter)this.getDecrypter()).getCalculatedAuthenticationBytes(numberOfBytesPushedBack);
        byte[] first10BytesOfCalculatedMac = new byte[10];
        System.arraycopy(calculatedMac, 0, first10BytesOfCalculatedMac, 0, 10);
        if (!Arrays.equals(storedMac, first10BytesOfCalculatedMac)) {
            throw new IOException("Reached end of data for this entry, but aes verification failed");
        }
    }

    protected byte[] readStoredMac(InputStream inputStream2) throws IOException {
        byte[] storedMac = new byte[10];
        int readLen = Zip4jUtil.readFully(inputStream2, storedMac);
        if (readLen != 10) {
            throw new ZipException("Invalid AES Mac bytes. Could not read sufficient data");
        }
        return storedMac;
    }

    private byte[] getSalt(LocalFileHeader localFileHeader) throws IOException {
        if (localFileHeader.getAesExtraDataRecord() == null) {
            throw new IOException("invalid aes extra data record");
        }
        AESExtraDataRecord aesExtraDataRecord = localFileHeader.getAesExtraDataRecord();
        if (aesExtraDataRecord.getAesKeyStrength() == null) {
            throw new IOException("Invalid aes key strength in aes extra data record");
        }
        byte[] saltBytes = new byte[aesExtraDataRecord.getAesKeyStrength().getSaltLength()];
        this.readRaw(saltBytes);
        return saltBytes;
    }

    private byte[] getPasswordVerifier() throws IOException {
        byte[] pvBytes = new byte[2];
        this.readRaw(pvBytes);
        return pvBytes;
    }

    private void incrementAesByteBlockPointer(int incrementBy) {
        this.aes16ByteBlockPointer += incrementBy;
        if (this.aes16ByteBlockPointer >= 15) {
            this.aes16ByteBlockPointer = 15;
        }
    }

    private void decrementRemainingAesBytesLength(int decrementBy) {
        this.remainingAes16ByteBlockLength -= decrementBy;
        if (this.remainingAes16ByteBlockLength <= 0) {
            this.remainingAes16ByteBlockLength = 0;
        }
    }
}

