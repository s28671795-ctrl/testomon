/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.inputstream;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.CRC32;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderReader;
import net.lingala.zip4j.headers.HeaderSignature;
import net.lingala.zip4j.io.inputstream.AesCipherInputStream;
import net.lingala.zip4j.io.inputstream.CipherInputStream;
import net.lingala.zip4j.io.inputstream.DecompressedInputStream;
import net.lingala.zip4j.io.inputstream.InflaterInputStream;
import net.lingala.zip4j.io.inputstream.NoCipherInputStream;
import net.lingala.zip4j.io.inputstream.StoreInputStream;
import net.lingala.zip4j.io.inputstream.ZipEntryInputStream;
import net.lingala.zip4j.io.inputstream.ZipStandardCipherInputStream;
import net.lingala.zip4j.model.AESExtraDataRecord;
import net.lingala.zip4j.model.DataDescriptor;
import net.lingala.zip4j.model.ExtraDataRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.enums.AesVersion;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import net.lingala.zip4j.util.PasswordCallback;
import net.lingala.zip4j.util.Zip4jUtil;

public class ZipInputStream
extends InputStream {
    private PushbackInputStream inputStream;
    private DecompressedInputStream decompressedInputStream;
    private HeaderReader headerReader = new HeaderReader();
    private char[] password;
    private PasswordCallback passwordCallback;
    private LocalFileHeader localFileHeader;
    private CRC32 crc32 = new CRC32();
    private byte[] endOfEntryBuffer;
    private boolean canSkipExtendedLocalFileHeader = false;
    private Zip4jConfig zip4jConfig;
    private boolean streamClosed = false;
    private boolean entryEOFReached = false;

    public ZipInputStream(InputStream inputStream2) {
        this(inputStream2, (char[])null, (Charset)null);
    }

    public ZipInputStream(InputStream inputStream2, Charset charset) {
        this(inputStream2, (char[])null, charset);
    }

    public ZipInputStream(InputStream inputStream2, char[] password) {
        this(inputStream2, password, (Charset)null);
    }

    public ZipInputStream(InputStream inputStream2, PasswordCallback passwordCallback) {
        this(inputStream2, passwordCallback, (Charset)null);
    }

    public ZipInputStream(InputStream inputStream2, char[] password, Charset charset) {
        this(inputStream2, password, new Zip4jConfig(charset, 4096, true));
    }

    public ZipInputStream(InputStream inputStream2, PasswordCallback passwordCallback, Charset charset) {
        this(inputStream2, passwordCallback, new Zip4jConfig(charset, 4096, true));
    }

    public ZipInputStream(InputStream inputStream2, char[] password, Zip4jConfig zip4jConfig) {
        this(inputStream2, password, null, zip4jConfig);
    }

    public ZipInputStream(InputStream inputStream2, PasswordCallback passwordCallback, Zip4jConfig zip4jConfig) {
        this(inputStream2, null, passwordCallback, zip4jConfig);
    }

    private ZipInputStream(InputStream inputStream2, char[] password, PasswordCallback passwordCallback, Zip4jConfig zip4jConfig) {
        if (zip4jConfig.getBufferSize() < 512) {
            throw new IllegalArgumentException("Buffer size cannot be less than 512 bytes");
        }
        this.inputStream = new PushbackInputStream(inputStream2, zip4jConfig.getBufferSize());
        this.password = password;
        this.passwordCallback = passwordCallback;
        this.zip4jConfig = zip4jConfig;
    }

    public LocalFileHeader getNextEntry() throws IOException {
        return this.getNextEntry(null, true);
    }

    public LocalFileHeader getNextEntry(FileHeader fileHeader, boolean readUntilEndOfCurrentEntryIfOpen) throws IOException {
        if (this.localFileHeader != null && readUntilEndOfCurrentEntryIfOpen) {
            this.readUntilEndOfEntry();
        }
        this.localFileHeader = this.headerReader.readLocalFileHeader(this.inputStream, this.zip4jConfig.getCharset());
        if (this.localFileHeader == null) {
            return null;
        }
        if (this.localFileHeader.isEncrypted() && this.password == null && this.passwordCallback != null) {
            this.setPassword(this.passwordCallback.getPassword());
        }
        this.verifyLocalFileHeader(this.localFileHeader);
        this.crc32.reset();
        if (fileHeader != null) {
            this.localFileHeader.setCrc(fileHeader.getCrc());
            this.localFileHeader.setCompressedSize(fileHeader.getCompressedSize());
            this.localFileHeader.setUncompressedSize(fileHeader.getUncompressedSize());
            this.localFileHeader.setDirectory(fileHeader.isDirectory());
            this.canSkipExtendedLocalFileHeader = true;
        } else {
            this.canSkipExtendedLocalFileHeader = false;
        }
        this.decompressedInputStream = this.initializeEntryInputStream(this.localFileHeader);
        this.entryEOFReached = false;
        return this.localFileHeader;
    }

    @Override
    public int read() throws IOException {
        byte[] b = new byte[1];
        int readLen = this.read(b);
        if (readLen == -1) {
            return -1;
        }
        return b[0] & 0xFF;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (this.streamClosed) {
            throw new IOException("Stream closed");
        }
        if (len < 0) {
            throw new IllegalArgumentException("Negative read length");
        }
        if (len == 0) {
            return 0;
        }
        if (this.localFileHeader == null) {
            return -1;
        }
        try {
            int readLen = this.decompressedInputStream.read(b, off, len);
            if (readLen == -1) {
                this.endOfCompressedDataReached();
            } else {
                this.crc32.update(b, off, readLen);
            }
            return readLen;
        }
        catch (IOException e) {
            if (this.isEncryptionMethodZipStandard(this.localFileHeader)) {
                throw new ZipException(e.getMessage(), e.getCause(), ZipException.Type.WRONG_PASSWORD);
            }
            throw e;
        }
    }

    @Override
    public void close() throws IOException {
        if (this.streamClosed) {
            return;
        }
        if (this.decompressedInputStream != null) {
            this.decompressedInputStream.close();
        }
        this.streamClosed = true;
    }

    @Override
    public int available() throws IOException {
        this.assertStreamOpen();
        return this.entryEOFReached ? 0 : 1;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    private void endOfCompressedDataReached() throws IOException {
        int numberOfBytesPushedBack = this.decompressedInputStream.pushBackInputStreamIfNecessary(this.inputStream);
        this.decompressedInputStream.endOfEntryReached(this.inputStream, numberOfBytesPushedBack);
        this.readExtendedLocalFileHeaderIfPresent();
        this.verifyCrc();
        this.resetFields();
        this.entryEOFReached = true;
    }

    private DecompressedInputStream initializeEntryInputStream(LocalFileHeader localFileHeader) throws IOException {
        ZipEntryInputStream zipEntryInputStream = new ZipEntryInputStream(this.inputStream, this.getCompressedSize(localFileHeader));
        CipherInputStream<?> cipherInputStream = this.initializeCipherInputStream(zipEntryInputStream, localFileHeader);
        return this.initializeDecompressorForThisEntry(cipherInputStream, localFileHeader);
    }

    private CipherInputStream<?> initializeCipherInputStream(ZipEntryInputStream zipEntryInputStream, LocalFileHeader localFileHeader) throws IOException {
        if (!localFileHeader.isEncrypted()) {
            return new NoCipherInputStream(zipEntryInputStream, localFileHeader, this.password, this.zip4jConfig.getBufferSize());
        }
        if (localFileHeader.getEncryptionMethod() == EncryptionMethod.AES) {
            return new AesCipherInputStream(zipEntryInputStream, localFileHeader, this.password, this.zip4jConfig.getBufferSize(), this.zip4jConfig.isUseUtf8CharsetForPasswords());
        }
        if (localFileHeader.getEncryptionMethod() == EncryptionMethod.ZIP_STANDARD) {
            return new ZipStandardCipherInputStream(zipEntryInputStream, localFileHeader, this.password, this.zip4jConfig.getBufferSize(), this.zip4jConfig.isUseUtf8CharsetForPasswords());
        }
        String message = String.format("Entry [%s] Strong Encryption not supported", localFileHeader.getFileName());
        throw new ZipException(message, ZipException.Type.UNSUPPORTED_ENCRYPTION);
    }

    private DecompressedInputStream initializeDecompressorForThisEntry(CipherInputStream<?> cipherInputStream, LocalFileHeader localFileHeader) throws ZipException {
        CompressionMethod compressionMethod = Zip4jUtil.getCompressionMethod(localFileHeader);
        if (compressionMethod == CompressionMethod.DEFLATE) {
            return new InflaterInputStream(cipherInputStream, this.zip4jConfig.getBufferSize());
        }
        return new StoreInputStream(cipherInputStream);
    }

    private void readExtendedLocalFileHeaderIfPresent() throws IOException {
        if (!this.localFileHeader.isDataDescriptorExists() || this.canSkipExtendedLocalFileHeader) {
            return;
        }
        DataDescriptor dataDescriptor = this.headerReader.readDataDescriptor(this.inputStream, this.checkIfZip64ExtraDataRecordPresentInLFH(this.localFileHeader.getExtraDataRecords()));
        this.localFileHeader.setCompressedSize(dataDescriptor.getCompressedSize());
        this.localFileHeader.setUncompressedSize(dataDescriptor.getUncompressedSize());
        this.localFileHeader.setCrc(dataDescriptor.getCrc());
    }

    private void verifyLocalFileHeader(LocalFileHeader localFileHeader) throws IOException {
        if (!this.isEntryDirectory(localFileHeader.getFileName()) && localFileHeader.getCompressionMethod() == CompressionMethod.STORE && localFileHeader.getUncompressedSize() < 0L) {
            throw new IOException("Invalid local file header for: " + localFileHeader.getFileName() + ". Uncompressed size has to be set for entry of compression type store which is not a directory");
        }
    }

    private boolean checkIfZip64ExtraDataRecordPresentInLFH(List<ExtraDataRecord> extraDataRecords) {
        if (extraDataRecords == null) {
            return false;
        }
        for (ExtraDataRecord extraDataRecord : extraDataRecords) {
            if (extraDataRecord.getHeader() != HeaderSignature.ZIP64_EXTRA_FIELD_SIGNATURE.getValue()) continue;
            return true;
        }
        return false;
    }

    private void verifyCrc() throws IOException {
        if (this.localFileHeader.getEncryptionMethod() == EncryptionMethod.AES && this.localFileHeader.getAesExtraDataRecord().getAesVersion().equals((Object)AesVersion.TWO)) {
            return;
        }
        if (this.localFileHeader.getCrc() != this.crc32.getValue()) {
            ZipException.Type exceptionType = ZipException.Type.CHECKSUM_MISMATCH;
            if (this.isEncryptionMethodZipStandard(this.localFileHeader)) {
                exceptionType = ZipException.Type.WRONG_PASSWORD;
            }
            throw new ZipException("Reached end of entry, but crc verification failed for " + this.localFileHeader.getFileName(), exceptionType);
        }
    }

    private void resetFields() {
        this.localFileHeader = null;
        this.crc32.reset();
    }

    private boolean isEntryDirectory(String entryName) {
        return entryName.endsWith("/") || entryName.endsWith("\\");
    }

    private long getCompressedSize(LocalFileHeader localFileHeader) throws ZipException {
        if (Zip4jUtil.getCompressionMethod(localFileHeader).equals((Object)CompressionMethod.STORE)) {
            return localFileHeader.getUncompressedSize();
        }
        if (localFileHeader.isDataDescriptorExists() && !this.canSkipExtendedLocalFileHeader) {
            return -1L;
        }
        return localFileHeader.getCompressedSize() - (long)this.getEncryptionHeaderSize(localFileHeader);
    }

    private int getEncryptionHeaderSize(LocalFileHeader localFileHeader) throws ZipException {
        if (!localFileHeader.isEncrypted()) {
            return 0;
        }
        if (localFileHeader.getEncryptionMethod().equals((Object)EncryptionMethod.AES)) {
            return this.getAesEncryptionHeaderSize(localFileHeader.getAesExtraDataRecord());
        }
        if (localFileHeader.getEncryptionMethod().equals((Object)EncryptionMethod.ZIP_STANDARD)) {
            return 12;
        }
        return 0;
    }

    private void readUntilEndOfEntry() throws IOException {
        if (this.endOfEntryBuffer == null) {
            this.endOfEntryBuffer = new byte[512];
        }
        while (this.read(this.endOfEntryBuffer) != -1) {
        }
        this.entryEOFReached = true;
    }

    private int getAesEncryptionHeaderSize(AESExtraDataRecord aesExtraDataRecord) throws ZipException {
        if (aesExtraDataRecord == null || aesExtraDataRecord.getAesKeyStrength() == null) {
            throw new ZipException("AesExtraDataRecord not found or invalid for Aes encrypted entry");
        }
        return 12 + aesExtraDataRecord.getAesKeyStrength().getSaltLength();
    }

    private boolean isEncryptionMethodZipStandard(LocalFileHeader localFileHeader) {
        return localFileHeader.isEncrypted() && EncryptionMethod.ZIP_STANDARD.equals((Object)localFileHeader.getEncryptionMethod());
    }

    private void assertStreamOpen() throws IOException {
        if (this.streamClosed) {
            throw new IOException("Stream closed");
        }
    }
}

