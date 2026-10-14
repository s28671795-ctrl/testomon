/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.headers;

import java.nio.charset.Charset;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderSignature;
import net.lingala.zip4j.headers.HeaderUtil;
import net.lingala.zip4j.model.AESExtraDataRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import net.lingala.zip4j.util.BitUtils;
import net.lingala.zip4j.util.FileUtils;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.RawIO;
import net.lingala.zip4j.util.Zip4jUtil;
import net.lingala.zip4j.util.ZipVersionUtils;

public class FileHeaderFactory {
    public FileHeader generateFileHeader(ZipParameters zipParameters, boolean isSplitZip, int currentDiskNumberStart, Charset charset, RawIO rawIO) throws ZipException {
        FileHeader fileHeader = new FileHeader();
        fileHeader.setSignature(HeaderSignature.CENTRAL_DIRECTORY);
        fileHeader.setVersionMadeBy(ZipVersionUtils.determineVersionMadeBy(zipParameters, rawIO));
        fileHeader.setVersionNeededToExtract(ZipVersionUtils.determineVersionNeededToExtract(zipParameters).getCode());
        if (zipParameters.isEncryptFiles() && zipParameters.getEncryptionMethod() == EncryptionMethod.AES) {
            fileHeader.setCompressionMethod(CompressionMethod.AES_INTERNAL_ONLY);
            fileHeader.setAesExtraDataRecord(this.generateAESExtraDataRecord(zipParameters));
            fileHeader.setExtraFieldLength(fileHeader.getExtraFieldLength() + 11);
        } else {
            fileHeader.setCompressionMethod(zipParameters.getCompressionMethod());
        }
        if (zipParameters.isEncryptFiles()) {
            if (zipParameters.getEncryptionMethod() == null || zipParameters.getEncryptionMethod() == EncryptionMethod.NONE) {
                throw new ZipException("Encryption method has to be set when encryptFiles flag is set in zip parameters");
            }
            fileHeader.setEncrypted(true);
            fileHeader.setEncryptionMethod(zipParameters.getEncryptionMethod());
        }
        String fileName = this.validateAndGetFileName(zipParameters.getFileNameInZip());
        fileHeader.setFileName(fileName);
        fileHeader.setFileNameLength(this.determineFileNameLength(fileName, charset));
        fileHeader.setDiskNumberStart(isSplitZip ? currentDiskNumberStart : 0);
        fileHeader.setLastModifiedTime(Zip4jUtil.epochToExtendedDosTime(zipParameters.getLastModifiedFileTime()));
        boolean isDirectory = FileUtils.isZipEntryDirectory(fileName);
        fileHeader.setDirectory(isDirectory);
        fileHeader.setExternalFileAttributes(FileUtils.getDefaultFileAttributes(isDirectory));
        if (zipParameters.isWriteExtendedLocalFileHeader() && zipParameters.getEntrySize() == -1L) {
            fileHeader.setUncompressedSize(0L);
        } else {
            fileHeader.setUncompressedSize(zipParameters.getEntrySize());
        }
        if (zipParameters.isEncryptFiles() && zipParameters.getEncryptionMethod() == EncryptionMethod.ZIP_STANDARD) {
            fileHeader.setCrc(zipParameters.getEntryCRC());
        }
        fileHeader.setGeneralPurposeFlag(this.determineGeneralPurposeBitFlag(fileHeader.isEncrypted(), zipParameters, charset));
        fileHeader.setDataDescriptorExists(zipParameters.isWriteExtendedLocalFileHeader());
        fileHeader.setFileComment(zipParameters.getFileComment());
        return fileHeader;
    }

    public LocalFileHeader generateLocalFileHeader(FileHeader fileHeader) {
        LocalFileHeader localFileHeader = new LocalFileHeader();
        localFileHeader.setSignature(HeaderSignature.LOCAL_FILE_HEADER);
        localFileHeader.setVersionNeededToExtract(fileHeader.getVersionNeededToExtract());
        localFileHeader.setCompressionMethod(fileHeader.getCompressionMethod());
        localFileHeader.setLastModifiedTime(fileHeader.getLastModifiedTime());
        localFileHeader.setUncompressedSize(fileHeader.getUncompressedSize());
        localFileHeader.setFileNameLength(fileHeader.getFileNameLength());
        localFileHeader.setFileName(fileHeader.getFileName());
        localFileHeader.setEncrypted(fileHeader.isEncrypted());
        localFileHeader.setEncryptionMethod(fileHeader.getEncryptionMethod());
        localFileHeader.setAesExtraDataRecord(fileHeader.getAesExtraDataRecord());
        localFileHeader.setCrc(fileHeader.getCrc());
        localFileHeader.setCompressedSize(fileHeader.getCompressedSize());
        localFileHeader.setGeneralPurposeFlag((byte[])fileHeader.getGeneralPurposeFlag().clone());
        localFileHeader.setDataDescriptorExists(fileHeader.isDataDescriptorExists());
        localFileHeader.setExtraFieldLength(fileHeader.getExtraFieldLength());
        return localFileHeader;
    }

    private byte[] determineGeneralPurposeBitFlag(boolean isEncrypted, ZipParameters zipParameters, Charset charset) {
        byte[] generalPurposeBitFlag = new byte[2];
        generalPurposeBitFlag[0] = this.generateFirstGeneralPurposeByte(isEncrypted, zipParameters);
        if (charset == null || InternalZipConstants.CHARSET_UTF_8.equals(charset)) {
            generalPurposeBitFlag[1] = BitUtils.setBit(generalPurposeBitFlag[1], 3);
        }
        return generalPurposeBitFlag;
    }

    private byte generateFirstGeneralPurposeByte(boolean isEncrypted, ZipParameters zipParameters) {
        byte firstByte = 0;
        if (isEncrypted) {
            firstByte = BitUtils.setBit(firstByte, 0);
        }
        if (CompressionMethod.DEFLATE.equals((Object)zipParameters.getCompressionMethod())) {
            if (CompressionLevel.NORMAL.equals((Object)zipParameters.getCompressionLevel())) {
                firstByte = BitUtils.unsetBit(firstByte, 1);
                firstByte = BitUtils.unsetBit(firstByte, 2);
            } else if (CompressionLevel.MAXIMUM.equals((Object)zipParameters.getCompressionLevel())) {
                firstByte = BitUtils.setBit(firstByte, 1);
                firstByte = BitUtils.unsetBit(firstByte, 2);
            } else if (CompressionLevel.FAST.equals((Object)zipParameters.getCompressionLevel())) {
                firstByte = BitUtils.unsetBit(firstByte, 1);
                firstByte = BitUtils.setBit(firstByte, 2);
            } else if (CompressionLevel.FASTEST.equals((Object)zipParameters.getCompressionLevel()) || CompressionLevel.ULTRA.equals((Object)zipParameters.getCompressionLevel())) {
                firstByte = BitUtils.setBit(firstByte, 1);
                firstByte = BitUtils.setBit(firstByte, 2);
            }
        }
        if (zipParameters.isWriteExtendedLocalFileHeader()) {
            firstByte = BitUtils.setBit(firstByte, 3);
        }
        return firstByte;
    }

    private String validateAndGetFileName(String fileNameInZip) throws ZipException {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(fileNameInZip)) {
            throw new ZipException("fileNameInZip is null or empty");
        }
        return fileNameInZip;
    }

    private AESExtraDataRecord generateAESExtraDataRecord(ZipParameters parameters) throws ZipException {
        AESExtraDataRecord aesExtraDataRecord = new AESExtraDataRecord();
        if (parameters.getAesVersion() != null) {
            aesExtraDataRecord.setAesVersion(parameters.getAesVersion());
        }
        if (parameters.getAesKeyStrength() == AesKeyStrength.KEY_STRENGTH_128) {
            aesExtraDataRecord.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_128);
        } else if (parameters.getAesKeyStrength() == AesKeyStrength.KEY_STRENGTH_192) {
            aesExtraDataRecord.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_192);
        } else if (parameters.getAesKeyStrength() == AesKeyStrength.KEY_STRENGTH_256) {
            aesExtraDataRecord.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
        } else {
            throw new ZipException("invalid AES key strength");
        }
        aesExtraDataRecord.setCompressionMethod(parameters.getCompressionMethod());
        return aesExtraDataRecord;
    }

    private int determineFileNameLength(String fileName, Charset charset) {
        return HeaderUtil.getBytesFromString(fileName, charset).length;
    }
}

