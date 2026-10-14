/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.outputstream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.zip.CRC32;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.FileHeaderFactory;
import net.lingala.zip4j.headers.HeaderSignature;
import net.lingala.zip4j.headers.HeaderWriter;
import net.lingala.zip4j.io.outputstream.AesCipherOutputStream;
import net.lingala.zip4j.io.outputstream.CipherOutputStream;
import net.lingala.zip4j.io.outputstream.CompressedOutputStream;
import net.lingala.zip4j.io.outputstream.CountingOutputStream;
import net.lingala.zip4j.io.outputstream.DeflaterOutputStream;
import net.lingala.zip4j.io.outputstream.NoCipherOutputStream;
import net.lingala.zip4j.io.outputstream.StoreOutputStream;
import net.lingala.zip4j.io.outputstream.ZipEntryOutputStream;
import net.lingala.zip4j.io.outputstream.ZipStandardCipherOutputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesVersion;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import net.lingala.zip4j.util.FileUtils;
import net.lingala.zip4j.util.RawIO;
import net.lingala.zip4j.util.Zip4jUtil;

public class ZipOutputStream
extends OutputStream {
    private CountingOutputStream countingOutputStream;
    private char[] password;
    private ZipModel zipModel;
    private CompressedOutputStream compressedOutputStream;
    private FileHeader fileHeader;
    private LocalFileHeader localFileHeader;
    private FileHeaderFactory fileHeaderFactory = new FileHeaderFactory();
    private HeaderWriter headerWriter = new HeaderWriter();
    private CRC32 crc32 = new CRC32();
    private RawIO rawIO = new RawIO();
    private long uncompressedSizeForThisEntry = 0L;
    private Zip4jConfig zip4jConfig;
    private boolean streamClosed;
    private boolean entryClosed = true;

    public ZipOutputStream(OutputStream outputStream2) throws IOException {
        this(outputStream2, null, null);
    }

    public ZipOutputStream(OutputStream outputStream2, Charset charset) throws IOException {
        this(outputStream2, null, charset);
    }

    public ZipOutputStream(OutputStream outputStream2, char[] password) throws IOException {
        this(outputStream2, password, null);
    }

    public ZipOutputStream(OutputStream outputStream2, char[] password, Charset charset) throws IOException {
        this(outputStream2, password, new Zip4jConfig(charset, 4096, true), new ZipModel());
    }

    public ZipOutputStream(OutputStream outputStream2, char[] password, Zip4jConfig zip4jConfig, ZipModel zipModel) throws IOException {
        if (zip4jConfig.getBufferSize() < 512) {
            throw new IllegalArgumentException("Buffer size cannot be less than 512 bytes");
        }
        this.countingOutputStream = new CountingOutputStream(outputStream2);
        this.password = password;
        this.zip4jConfig = zip4jConfig;
        this.zipModel = this.initializeZipModel(zipModel, this.countingOutputStream);
        this.streamClosed = false;
        this.writeSplitZipHeaderIfApplicable();
    }

    public void putNextEntry(ZipParameters zipParameters) throws IOException {
        this.verifyZipParameters(zipParameters);
        ZipParameters clonedZipParameters = this.cloneAndPrepareZipParameters(zipParameters);
        this.initializeAndWriteFileHeader(clonedZipParameters);
        this.compressedOutputStream = this.initializeCompressedOutputStream(clonedZipParameters);
        this.entryClosed = false;
    }

    @Override
    public void write(int b) throws IOException {
        this.write(new byte[]{(byte)b});
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        this.ensureStreamOpen();
        this.crc32.update(b, off, len);
        this.compressedOutputStream.write(b, off, len);
        this.uncompressedSizeForThisEntry += (long)len;
    }

    public FileHeader closeEntry() throws IOException {
        this.compressedOutputStream.closeEntry();
        long compressedSize = this.compressedOutputStream.getCompressedSize();
        this.fileHeader.setCompressedSize(compressedSize);
        this.localFileHeader.setCompressedSize(compressedSize);
        this.fileHeader.setUncompressedSize(this.uncompressedSizeForThisEntry);
        this.localFileHeader.setUncompressedSize(this.uncompressedSizeForThisEntry);
        if (this.writeCrc(this.fileHeader)) {
            this.fileHeader.setCrc(this.crc32.getValue());
            this.localFileHeader.setCrc(this.crc32.getValue());
        }
        this.zipModel.getLocalFileHeaders().add(this.localFileHeader);
        this.zipModel.getCentralDirectory().getFileHeaders().add(this.fileHeader);
        if (this.localFileHeader.isDataDescriptorExists()) {
            this.headerWriter.writeExtendedLocalHeader(this.localFileHeader, this.countingOutputStream);
        }
        this.reset();
        this.entryClosed = true;
        return this.fileHeader;
    }

    @Override
    public void close() throws IOException {
        if (!this.entryClosed) {
            this.closeEntry();
        }
        this.zipModel.getEndOfCentralDirectoryRecord().setOffsetOfStartOfCentralDirectory(this.countingOutputStream.getNumberOfBytesWritten());
        this.headerWriter.finalizeZipFile(this.zipModel, this.countingOutputStream, this.zip4jConfig.getCharset());
        this.countingOutputStream.close();
        this.streamClosed = true;
    }

    public void setComment(String comment) throws IOException {
        this.ensureStreamOpen();
        this.zipModel.getEndOfCentralDirectoryRecord().setComment(comment);
    }

    private void ensureStreamOpen() throws IOException {
        if (this.streamClosed) {
            throw new IOException("Stream is closed");
        }
    }

    private ZipModel initializeZipModel(ZipModel zipModel, CountingOutputStream countingOutputStream) {
        if (zipModel == null) {
            zipModel = new ZipModel();
        }
        if (countingOutputStream.isSplitZipFile()) {
            zipModel.setSplitArchive(true);
            zipModel.setSplitLength(countingOutputStream.getSplitLength());
        }
        return zipModel;
    }

    private void initializeAndWriteFileHeader(ZipParameters zipParameters) throws IOException {
        this.fileHeader = this.fileHeaderFactory.generateFileHeader(zipParameters, this.countingOutputStream.isSplitZipFile(), this.countingOutputStream.getCurrentSplitFileCounter(), this.zip4jConfig.getCharset(), this.rawIO);
        this.fileHeader.setOffsetLocalHeader(this.countingOutputStream.getOffsetForNextEntry());
        this.localFileHeader = this.fileHeaderFactory.generateLocalFileHeader(this.fileHeader);
        this.headerWriter.writeLocalFileHeader(this.zipModel, this.localFileHeader, this.countingOutputStream, this.zip4jConfig.getCharset());
    }

    private void reset() throws IOException {
        this.uncompressedSizeForThisEntry = 0L;
        this.crc32.reset();
        this.compressedOutputStream.close();
    }

    private void writeSplitZipHeaderIfApplicable() throws IOException {
        if (!this.countingOutputStream.isSplitZipFile()) {
            return;
        }
        this.rawIO.writeIntLittleEndian(this.countingOutputStream, (int)HeaderSignature.SPLIT_ZIP.getValue());
    }

    private CompressedOutputStream initializeCompressedOutputStream(ZipParameters zipParameters) throws IOException {
        ZipEntryOutputStream zipEntryOutputStream = new ZipEntryOutputStream(this.countingOutputStream);
        CipherOutputStream<?> cipherOutputStream = this.initializeCipherOutputStream(zipEntryOutputStream, zipParameters);
        return this.initializeCompressedOutputStream(cipherOutputStream, zipParameters);
    }

    private CipherOutputStream<?> initializeCipherOutputStream(ZipEntryOutputStream zipEntryOutputStream, ZipParameters zipParameters) throws IOException {
        if (!zipParameters.isEncryptFiles()) {
            return new NoCipherOutputStream(zipEntryOutputStream, zipParameters, null);
        }
        if (this.password == null || this.password.length == 0) {
            throw new ZipException("password not set");
        }
        if (zipParameters.getEncryptionMethod() == EncryptionMethod.AES) {
            return new AesCipherOutputStream(zipEntryOutputStream, zipParameters, this.password, this.zip4jConfig.isUseUtf8CharsetForPasswords());
        }
        if (zipParameters.getEncryptionMethod() == EncryptionMethod.ZIP_STANDARD) {
            return new ZipStandardCipherOutputStream(zipEntryOutputStream, zipParameters, this.password, this.zip4jConfig.isUseUtf8CharsetForPasswords());
        }
        if (zipParameters.getEncryptionMethod() == EncryptionMethod.ZIP_STANDARD_VARIANT_STRONG) {
            throw new ZipException((Object)((Object)EncryptionMethod.ZIP_STANDARD_VARIANT_STRONG) + " encryption method is not supported");
        }
        throw new ZipException("Invalid encryption method");
    }

    private CompressedOutputStream initializeCompressedOutputStream(CipherOutputStream<?> cipherOutputStream, ZipParameters zipParameters) {
        if (zipParameters.getCompressionMethod() == CompressionMethod.DEFLATE) {
            return new DeflaterOutputStream(cipherOutputStream, zipParameters.getCompressionLevel(), this.zip4jConfig.getBufferSize());
        }
        return new StoreOutputStream(cipherOutputStream);
    }

    private void verifyZipParameters(ZipParameters zipParameters) {
        if (Zip4jUtil.isStringNullOrEmpty(zipParameters.getFileNameInZip())) {
            throw new IllegalArgumentException("fileNameInZip is null or empty");
        }
        if (zipParameters.getCompressionMethod() == CompressionMethod.STORE && zipParameters.getEntrySize() < 0L && !FileUtils.isZipEntryDirectory(zipParameters.getFileNameInZip()) && zipParameters.isWriteExtendedLocalFileHeader()) {
            throw new IllegalArgumentException("uncompressed size should be set for zip entries of compression type store");
        }
    }

    private boolean writeCrc(FileHeader fileHeader) {
        boolean isAesEncrypted;
        boolean bl = isAesEncrypted = fileHeader.isEncrypted() && fileHeader.getEncryptionMethod().equals((Object)EncryptionMethod.AES);
        if (!isAesEncrypted) {
            return true;
        }
        return fileHeader.getAesExtraDataRecord().getAesVersion().equals((Object)AesVersion.ONE);
    }

    private ZipParameters cloneAndPrepareZipParameters(ZipParameters zipParameters) {
        ZipParameters clonedZipParameters = new ZipParameters(zipParameters);
        if (FileUtils.isZipEntryDirectory(zipParameters.getFileNameInZip())) {
            clonedZipParameters.setWriteExtendedLocalFileHeader(false);
            clonedZipParameters.setCompressionMethod(CompressionMethod.STORE);
            clonedZipParameters.setEncryptFiles(false);
            clonedZipParameters.setEntrySize(0L);
        }
        if (zipParameters.getLastModifiedFileTime() <= 0L) {
            clonedZipParameters.setLastModifiedFileTime(System.currentTimeMillis());
        }
        return clonedZipParameters;
    }
}

