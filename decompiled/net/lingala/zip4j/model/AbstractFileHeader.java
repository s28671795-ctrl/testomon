/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.model;

import java.util.List;
import net.lingala.zip4j.model.AESExtraDataRecord;
import net.lingala.zip4j.model.ExtraDataRecord;
import net.lingala.zip4j.model.Zip64ExtendedInfo;
import net.lingala.zip4j.model.ZipHeader;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import net.lingala.zip4j.util.Zip4jUtil;

public abstract class AbstractFileHeader
extends ZipHeader {
    private int versionNeededToExtract;
    private byte[] generalPurposeFlag;
    private CompressionMethod compressionMethod;
    private long lastModifiedTime;
    private long crc = 0L;
    private long compressedSize = 0L;
    private long uncompressedSize = 0L;
    private int fileNameLength;
    private int extraFieldLength;
    private String fileName;
    private boolean isEncrypted;
    private EncryptionMethod encryptionMethod = EncryptionMethod.NONE;
    private boolean dataDescriptorExists;
    private Zip64ExtendedInfo zip64ExtendedInfo;
    private AESExtraDataRecord aesExtraDataRecord;
    private boolean fileNameUTF8Encoded;
    private List<ExtraDataRecord> extraDataRecords;
    private boolean isDirectory;

    public int getVersionNeededToExtract() {
        return this.versionNeededToExtract;
    }

    public void setVersionNeededToExtract(int versionNeededToExtract) {
        this.versionNeededToExtract = versionNeededToExtract;
    }

    public byte[] getGeneralPurposeFlag() {
        return this.generalPurposeFlag;
    }

    public void setGeneralPurposeFlag(byte[] generalPurposeFlag) {
        this.generalPurposeFlag = generalPurposeFlag;
    }

    public CompressionMethod getCompressionMethod() {
        return this.compressionMethod;
    }

    public void setCompressionMethod(CompressionMethod compressionMethod) {
        this.compressionMethod = compressionMethod;
    }

    public long getLastModifiedTime() {
        return this.lastModifiedTime;
    }

    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public long getLastModifiedTimeEpoch() {
        return Zip4jUtil.dosToExtendedEpochTme(this.lastModifiedTime);
    }

    public long getCrc() {
        return this.crc;
    }

    public void setCrc(long crc) {
        this.crc = crc;
    }

    public long getCompressedSize() {
        return this.compressedSize;
    }

    public void setCompressedSize(long compressedSize) {
        this.compressedSize = compressedSize;
    }

    public long getUncompressedSize() {
        return this.uncompressedSize;
    }

    public void setUncompressedSize(long uncompressedSize) {
        this.uncompressedSize = uncompressedSize;
    }

    public int getFileNameLength() {
        return this.fileNameLength;
    }

    public void setFileNameLength(int fileNameLength) {
        this.fileNameLength = fileNameLength;
    }

    public int getExtraFieldLength() {
        return this.extraFieldLength;
    }

    public void setExtraFieldLength(int extraFieldLength) {
        this.extraFieldLength = extraFieldLength;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isEncrypted() {
        return this.isEncrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.isEncrypted = encrypted;
    }

    public EncryptionMethod getEncryptionMethod() {
        return this.encryptionMethod;
    }

    public void setEncryptionMethod(EncryptionMethod encryptionMethod) {
        this.encryptionMethod = encryptionMethod;
    }

    public boolean isDataDescriptorExists() {
        return this.dataDescriptorExists;
    }

    public void setDataDescriptorExists(boolean dataDescriptorExists) {
        this.dataDescriptorExists = dataDescriptorExists;
    }

    public Zip64ExtendedInfo getZip64ExtendedInfo() {
        return this.zip64ExtendedInfo;
    }

    public void setZip64ExtendedInfo(Zip64ExtendedInfo zip64ExtendedInfo) {
        this.zip64ExtendedInfo = zip64ExtendedInfo;
    }

    public AESExtraDataRecord getAesExtraDataRecord() {
        return this.aesExtraDataRecord;
    }

    public void setAesExtraDataRecord(AESExtraDataRecord aesExtraDataRecord) {
        this.aesExtraDataRecord = aesExtraDataRecord;
    }

    public boolean isFileNameUTF8Encoded() {
        return this.fileNameUTF8Encoded;
    }

    public void setFileNameUTF8Encoded(boolean fileNameUTF8Encoded) {
        this.fileNameUTF8Encoded = fileNameUTF8Encoded;
    }

    public List<ExtraDataRecord> getExtraDataRecords() {
        return this.extraDataRecords;
    }

    public void setExtraDataRecords(List<ExtraDataRecord> extraDataRecords) {
        this.extraDataRecords = extraDataRecords;
    }

    public boolean isDirectory() {
        return this.isDirectory;
    }

    public void setDirectory(boolean directory) {
        this.isDirectory = directory;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractFileHeader)) {
            return false;
        }
        return this.getFileName().equals(((AbstractFileHeader)obj).getFileName());
    }
}

