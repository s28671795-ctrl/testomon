/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.headers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderSignature;
import net.lingala.zip4j.headers.HeaderUtil;
import net.lingala.zip4j.io.outputstream.CountingOutputStream;
import net.lingala.zip4j.io.outputstream.OutputStreamWithSplitZipSupport;
import net.lingala.zip4j.io.outputstream.SplitOutputStream;
import net.lingala.zip4j.model.AESExtraDataRecord;
import net.lingala.zip4j.model.ExtraDataRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator;
import net.lingala.zip4j.model.Zip64EndOfCentralDirectoryRecord;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.util.FileUtils;
import net.lingala.zip4j.util.RawIO;
import net.lingala.zip4j.util.Zip4jUtil;

public class HeaderWriter {
    private static final short ZIP64_EXTRA_DATA_RECORD_SIZE_LFH = 16;
    private static final short ZIP64_EXTRA_DATA_RECORD_SIZE_FH = 28;
    private static final short AES_EXTRA_DATA_RECORD_SIZE = 11;
    private final RawIO rawIO = new RawIO();
    private final byte[] longBuff = new byte[8];
    private final byte[] intBuff = new byte[4];

    public void writeLocalFileHeader(ZipModel zipModel, LocalFileHeader localFileHeader, OutputStream outputStream2, Charset charset) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();){
            boolean writeZip64Header;
            this.rawIO.writeIntLittleEndian(byteArrayOutputStream, (int)localFileHeader.getSignature().getValue());
            this.rawIO.writeShortLittleEndian(byteArrayOutputStream, localFileHeader.getVersionNeededToExtract());
            byteArrayOutputStream.write(localFileHeader.getGeneralPurposeFlag());
            this.rawIO.writeShortLittleEndian(byteArrayOutputStream, localFileHeader.getCompressionMethod().getCode());
            this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getLastModifiedTime());
            byteArrayOutputStream.write(this.longBuff, 0, 4);
            this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getCrc());
            byteArrayOutputStream.write(this.longBuff, 0, 4);
            boolean bl = writeZip64Header = localFileHeader.getCompressedSize() >= 0xFFFFFFFFL || localFileHeader.getUncompressedSize() >= 0xFFFFFFFFL;
            if (writeZip64Header) {
                this.rawIO.writeLongLittleEndian(this.longBuff, 0, 0xFFFFFFFFL);
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                zipModel.setZip64Format(true);
                localFileHeader.setWriteCompressedSizeInZip64ExtraRecord(true);
            } else {
                this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getCompressedSize());
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getUncompressedSize());
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                localFileHeader.setWriteCompressedSizeInZip64ExtraRecord(false);
            }
            byte[] fileNameBytes = new byte[]{};
            if (Zip4jUtil.isStringNotNullAndNotEmpty(localFileHeader.getFileName())) {
                fileNameBytes = HeaderUtil.getBytesFromString(localFileHeader.getFileName(), charset);
            }
            this.rawIO.writeShortLittleEndian(byteArrayOutputStream, fileNameBytes.length);
            int extraFieldLength = 0;
            if (writeZip64Header) {
                extraFieldLength += 20;
            }
            if (localFileHeader.getAesExtraDataRecord() != null) {
                extraFieldLength += 11;
            }
            this.rawIO.writeShortLittleEndian(byteArrayOutputStream, extraFieldLength);
            if (fileNameBytes.length > 0) {
                byteArrayOutputStream.write(fileNameBytes);
            }
            if (writeZip64Header) {
                this.rawIO.writeShortLittleEndian(byteArrayOutputStream, (int)HeaderSignature.ZIP64_EXTRA_FIELD_SIGNATURE.getValue());
                this.rawIO.writeShortLittleEndian(byteArrayOutputStream, 16);
                this.rawIO.writeLongLittleEndian(byteArrayOutputStream, localFileHeader.getUncompressedSize());
                this.rawIO.writeLongLittleEndian(byteArrayOutputStream, localFileHeader.getCompressedSize());
            }
            if (localFileHeader.getAesExtraDataRecord() != null) {
                AESExtraDataRecord aesExtraDataRecord = localFileHeader.getAesExtraDataRecord();
                this.rawIO.writeShortLittleEndian(byteArrayOutputStream, (int)aesExtraDataRecord.getSignature().getValue());
                this.rawIO.writeShortLittleEndian(byteArrayOutputStream, aesExtraDataRecord.getDataSize());
                this.rawIO.writeShortLittleEndian(byteArrayOutputStream, aesExtraDataRecord.getAesVersion().getVersionNumber());
                byteArrayOutputStream.write(HeaderUtil.getBytesFromString(aesExtraDataRecord.getVendorID(), charset));
                byte[] aesStrengthBytes = new byte[]{(byte)aesExtraDataRecord.getAesKeyStrength().getRawCode()};
                byteArrayOutputStream.write(aesStrengthBytes);
                this.rawIO.writeShortLittleEndian(byteArrayOutputStream, aesExtraDataRecord.getCompressionMethod().getCode());
            }
            outputStream2.write(byteArrayOutputStream.toByteArray());
        }
    }

    public void writeExtendedLocalHeader(LocalFileHeader localFileHeader, OutputStream outputStream2) throws IOException {
        if (localFileHeader == null || outputStream2 == null) {
            throw new ZipException("input parameters is null, cannot write extended local header");
        }
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();){
            this.rawIO.writeIntLittleEndian(byteArrayOutputStream, (int)HeaderSignature.EXTRA_DATA_RECORD.getValue());
            this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getCrc());
            byteArrayOutputStream.write(this.longBuff, 0, 4);
            if (localFileHeader.isWriteCompressedSizeInZip64ExtraRecord()) {
                this.rawIO.writeLongLittleEndian(byteArrayOutputStream, localFileHeader.getCompressedSize());
                this.rawIO.writeLongLittleEndian(byteArrayOutputStream, localFileHeader.getUncompressedSize());
            } else {
                this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getCompressedSize());
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getUncompressedSize());
                byteArrayOutputStream.write(this.longBuff, 0, 4);
            }
            outputStream2.write(byteArrayOutputStream.toByteArray());
        }
    }

    public void finalizeZipFile(ZipModel zipModel, OutputStream outputStream2, Charset charset) throws IOException {
        if (zipModel == null || outputStream2 == null) {
            throw new ZipException("input parameters is null, cannot finalize zip file");
        }
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();){
            this.processHeaderData(zipModel, outputStream2);
            long offsetCentralDir = this.getOffsetOfCentralDirectory(zipModel);
            this.writeCentralDirectory(zipModel, byteArrayOutputStream, this.rawIO, charset);
            int sizeOfCentralDir = byteArrayOutputStream.size();
            if (zipModel.isZip64Format() || offsetCentralDir >= 0xFFFFFFFFL || zipModel.getCentralDirectory().getFileHeaders().size() >= 65535) {
                if (zipModel.getZip64EndOfCentralDirectoryRecord() == null) {
                    zipModel.setZip64EndOfCentralDirectoryRecord(new Zip64EndOfCentralDirectoryRecord());
                }
                if (zipModel.getZip64EndOfCentralDirectoryLocator() == null) {
                    zipModel.setZip64EndOfCentralDirectoryLocator(new Zip64EndOfCentralDirectoryLocator());
                }
                zipModel.getZip64EndOfCentralDirectoryLocator().setOffsetZip64EndOfCentralDirectoryRecord(offsetCentralDir + (long)sizeOfCentralDir);
                if (this.isSplitZipFile(outputStream2)) {
                    int currentSplitFileCounter = this.getCurrentSplitFileCounter(outputStream2);
                    zipModel.getZip64EndOfCentralDirectoryLocator().setNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord(currentSplitFileCounter);
                    zipModel.getZip64EndOfCentralDirectoryLocator().setTotalNumberOfDiscs(currentSplitFileCounter + 1);
                } else {
                    zipModel.getZip64EndOfCentralDirectoryLocator().setNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord(0);
                    zipModel.getZip64EndOfCentralDirectoryLocator().setTotalNumberOfDiscs(1);
                }
                Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecord = this.buildZip64EndOfCentralDirectoryRecord(zipModel, sizeOfCentralDir, offsetCentralDir);
                zipModel.setZip64EndOfCentralDirectoryRecord(zip64EndOfCentralDirectoryRecord);
                this.writeZip64EndOfCentralDirectoryRecord(zip64EndOfCentralDirectoryRecord, byteArrayOutputStream, this.rawIO);
                this.writeZip64EndOfCentralDirectoryLocator(zipModel.getZip64EndOfCentralDirectoryLocator(), byteArrayOutputStream, this.rawIO);
            }
            this.writeEndOfCentralDirectoryRecord(zipModel, sizeOfCentralDir, offsetCentralDir, byteArrayOutputStream, this.rawIO, charset);
            this.writeZipHeaderBytes(zipModel, outputStream2, byteArrayOutputStream.toByteArray(), charset);
        }
    }

    public void finalizeZipFileWithoutValidations(ZipModel zipModel, OutputStream outputStream2, Charset charset) throws IOException {
        if (zipModel == null || outputStream2 == null) {
            throw new ZipException("input parameters is null, cannot finalize zip file without validations");
        }
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();){
            long offsetCentralDir = this.getOffsetOfCentralDirectory(zipModel);
            this.writeCentralDirectory(zipModel, byteArrayOutputStream, this.rawIO, charset);
            int sizeOfCentralDir = byteArrayOutputStream.size();
            if (zipModel.isZip64Format() || offsetCentralDir >= 0xFFFFFFFFL || zipModel.getCentralDirectory().getFileHeaders().size() >= 65535) {
                if (zipModel.getZip64EndOfCentralDirectoryRecord() == null) {
                    zipModel.setZip64EndOfCentralDirectoryRecord(new Zip64EndOfCentralDirectoryRecord());
                }
                if (zipModel.getZip64EndOfCentralDirectoryLocator() == null) {
                    zipModel.setZip64EndOfCentralDirectoryLocator(new Zip64EndOfCentralDirectoryLocator());
                }
                zipModel.getZip64EndOfCentralDirectoryLocator().setOffsetZip64EndOfCentralDirectoryRecord(offsetCentralDir + (long)sizeOfCentralDir);
                Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecord = this.buildZip64EndOfCentralDirectoryRecord(zipModel, sizeOfCentralDir, offsetCentralDir);
                zipModel.setZip64EndOfCentralDirectoryRecord(zip64EndOfCentralDirectoryRecord);
                this.writeZip64EndOfCentralDirectoryRecord(zip64EndOfCentralDirectoryRecord, byteArrayOutputStream, this.rawIO);
                this.writeZip64EndOfCentralDirectoryLocator(zipModel.getZip64EndOfCentralDirectoryLocator(), byteArrayOutputStream, this.rawIO);
            }
            this.writeEndOfCentralDirectoryRecord(zipModel, sizeOfCentralDir, offsetCentralDir, byteArrayOutputStream, this.rawIO, charset);
            this.writeZipHeaderBytes(zipModel, outputStream2, byteArrayOutputStream.toByteArray(), charset);
        }
    }

    public void updateLocalFileHeader(FileHeader fileHeader, ZipModel zipModel, SplitOutputStream outputStream2) throws IOException {
        SplitOutputStream currOutputStream;
        if (fileHeader == null || zipModel == null) {
            throw new ZipException("invalid input parameters, cannot update local file header");
        }
        boolean closeFlag = false;
        if (fileHeader.getDiskNumberStart() != outputStream2.getCurrentSplitFileCounter()) {
            String parentFile = zipModel.getZipFile().getParent();
            String fileNameWithoutExt = FileUtils.getZipFileNameWithoutExtension(zipModel.getZipFile().getName());
            String fileName = "";
            if (parentFile != null) {
                fileName = parentFile + System.getProperty("file.separator");
            }
            fileName = fileHeader.getDiskNumberStart() < 9 ? fileName + fileNameWithoutExt + ".z0" + (fileHeader.getDiskNumberStart() + 1) : fileName + fileNameWithoutExt + ".z" + (fileHeader.getDiskNumberStart() + 1);
            currOutputStream = new SplitOutputStream(new File(fileName));
            closeFlag = true;
        } else {
            currOutputStream = outputStream2;
        }
        long currOffset = currOutputStream.getFilePointer();
        currOutputStream.seek(fileHeader.getOffsetLocalHeader() + 14L);
        this.rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getCrc());
        currOutputStream.write(this.longBuff, 0, 4);
        this.updateFileSizesInLocalFileHeader(currOutputStream, fileHeader);
        if (closeFlag) {
            currOutputStream.close();
        } else {
            outputStream2.seek(currOffset);
        }
    }

    private void updateFileSizesInLocalFileHeader(SplitOutputStream outputStream2, FileHeader fileHeader) throws IOException {
        if (fileHeader.getUncompressedSize() >= 0xFFFFFFFFL) {
            this.rawIO.writeLongLittleEndian(this.longBuff, 0, 0xFFFFFFFFL);
            outputStream2.write(this.longBuff, 0, 4);
            outputStream2.write(this.longBuff, 0, 4);
            int zip64CompressedSizeOffset = 4 + fileHeader.getFileNameLength() + 2 + 2;
            if (outputStream2.skipBytes(zip64CompressedSizeOffset) != zip64CompressedSizeOffset) {
                throw new ZipException("Unable to skip " + zip64CompressedSizeOffset + " bytes to update LFH");
            }
            this.rawIO.writeLongLittleEndian(outputStream2, fileHeader.getUncompressedSize());
            this.rawIO.writeLongLittleEndian(outputStream2, fileHeader.getCompressedSize());
        } else {
            this.rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getCompressedSize());
            outputStream2.write(this.longBuff, 0, 4);
            this.rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getUncompressedSize());
            outputStream2.write(this.longBuff, 0, 4);
        }
    }

    private boolean isSplitZipFile(OutputStream outputStream2) {
        if (outputStream2 instanceof SplitOutputStream) {
            return ((SplitOutputStream)outputStream2).isSplitZipFile();
        }
        if (outputStream2 instanceof CountingOutputStream) {
            return ((CountingOutputStream)outputStream2).isSplitZipFile();
        }
        return false;
    }

    private int getCurrentSplitFileCounter(OutputStream outputStream2) {
        if (outputStream2 instanceof SplitOutputStream) {
            return ((SplitOutputStream)outputStream2).getCurrentSplitFileCounter();
        }
        return ((CountingOutputStream)outputStream2).getCurrentSplitFileCounter();
    }

    private void writeZipHeaderBytes(ZipModel zipModel, OutputStream outputStream2, byte[] buff, Charset charset) throws IOException {
        if (buff == null) {
            throw new ZipException("invalid buff to write as zip headers");
        }
        if (outputStream2 instanceof CountingOutputStream && ((CountingOutputStream)outputStream2).checkBuffSizeAndStartNextSplitFile(buff.length)) {
            this.finalizeZipFile(zipModel, outputStream2, charset);
            return;
        }
        outputStream2.write(buff);
    }

    private void processHeaderData(ZipModel zipModel, OutputStream outputStream2) throws IOException {
        int currentSplitFileCounter = 0;
        if (outputStream2 instanceof OutputStreamWithSplitZipSupport) {
            zipModel.getEndOfCentralDirectoryRecord().setOffsetOfStartOfCentralDirectory(((OutputStreamWithSplitZipSupport)((Object)outputStream2)).getFilePointer());
            currentSplitFileCounter = ((OutputStreamWithSplitZipSupport)((Object)outputStream2)).getCurrentSplitFileCounter();
        }
        if (zipModel.isZip64Format()) {
            if (zipModel.getZip64EndOfCentralDirectoryRecord() == null) {
                zipModel.setZip64EndOfCentralDirectoryRecord(new Zip64EndOfCentralDirectoryRecord());
            }
            if (zipModel.getZip64EndOfCentralDirectoryLocator() == null) {
                zipModel.setZip64EndOfCentralDirectoryLocator(new Zip64EndOfCentralDirectoryLocator());
            }
            zipModel.getZip64EndOfCentralDirectoryRecord().setOffsetStartCentralDirectoryWRTStartDiskNumber(zipModel.getEndOfCentralDirectoryRecord().getOffsetOfStartOfCentralDirectory());
            zipModel.getZip64EndOfCentralDirectoryLocator().setNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord(currentSplitFileCounter);
            zipModel.getZip64EndOfCentralDirectoryLocator().setTotalNumberOfDiscs(currentSplitFileCounter + 1);
        }
        zipModel.getEndOfCentralDirectoryRecord().setNumberOfThisDisk(currentSplitFileCounter);
        zipModel.getEndOfCentralDirectoryRecord().setNumberOfThisDiskStartOfCentralDir(currentSplitFileCounter);
    }

    private void writeCentralDirectory(ZipModel zipModel, ByteArrayOutputStream byteArrayOutputStream, RawIO rawIO, Charset charset) throws ZipException {
        if (zipModel.getCentralDirectory() == null || zipModel.getCentralDirectory().getFileHeaders() == null || zipModel.getCentralDirectory().getFileHeaders().size() <= 0) {
            return;
        }
        for (FileHeader fileHeader : zipModel.getCentralDirectory().getFileHeaders()) {
            this.writeFileHeader(zipModel, fileHeader, byteArrayOutputStream, rawIO, charset);
        }
    }

    private void writeFileHeader(ZipModel zipModel, FileHeader fileHeader, ByteArrayOutputStream byteArrayOutputStream, RawIO rawIO, Charset charset) throws ZipException {
        if (fileHeader == null) {
            throw new ZipException("input parameters is null, cannot write local file header");
        }
        try {
            byte[] emptyShortByte = new byte[]{0, 0};
            boolean writeZip64ExtendedInfo = this.isZip64Entry(fileHeader);
            rawIO.writeIntLittleEndian(byteArrayOutputStream, (int)fileHeader.getSignature().getValue());
            rawIO.writeShortLittleEndian(byteArrayOutputStream, fileHeader.getVersionMadeBy());
            rawIO.writeShortLittleEndian(byteArrayOutputStream, fileHeader.getVersionNeededToExtract());
            byteArrayOutputStream.write(fileHeader.getGeneralPurposeFlag());
            rawIO.writeShortLittleEndian(byteArrayOutputStream, fileHeader.getCompressionMethod().getCode());
            rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getLastModifiedTime());
            byteArrayOutputStream.write(this.longBuff, 0, 4);
            rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getCrc());
            byteArrayOutputStream.write(this.longBuff, 0, 4);
            if (writeZip64ExtendedInfo) {
                rawIO.writeLongLittleEndian(this.longBuff, 0, 0xFFFFFFFFL);
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                zipModel.setZip64Format(true);
            } else {
                rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getCompressedSize());
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getUncompressedSize());
                byteArrayOutputStream.write(this.longBuff, 0, 4);
            }
            byte[] fileNameBytes = new byte[]{};
            if (Zip4jUtil.isStringNotNullAndNotEmpty(fileHeader.getFileName())) {
                fileNameBytes = HeaderUtil.getBytesFromString(fileHeader.getFileName(), charset);
            }
            rawIO.writeShortLittleEndian(byteArrayOutputStream, fileNameBytes.length);
            byte[] offsetLocalHeaderBytes = new byte[4];
            if (writeZip64ExtendedInfo) {
                rawIO.writeLongLittleEndian(this.longBuff, 0, 0xFFFFFFFFL);
                System.arraycopy(this.longBuff, 0, offsetLocalHeaderBytes, 0, 4);
            } else {
                rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getOffsetLocalHeader());
                System.arraycopy(this.longBuff, 0, offsetLocalHeaderBytes, 0, 4);
            }
            int extraFieldLength = this.calculateExtraDataRecordsSize(fileHeader, writeZip64ExtendedInfo);
            rawIO.writeShortLittleEndian(byteArrayOutputStream, extraFieldLength);
            String fileComment = fileHeader.getFileComment();
            byte[] fileCommentBytes = new byte[]{};
            if (Zip4jUtil.isStringNotNullAndNotEmpty(fileComment)) {
                fileCommentBytes = HeaderUtil.getBytesFromString(fileComment, charset);
            }
            rawIO.writeShortLittleEndian(byteArrayOutputStream, fileCommentBytes.length);
            if (writeZip64ExtendedInfo) {
                rawIO.writeIntLittleEndian(this.intBuff, 0, 65535);
                byteArrayOutputStream.write(this.intBuff, 0, 2);
            } else {
                rawIO.writeShortLittleEndian(byteArrayOutputStream, fileHeader.getDiskNumberStart());
            }
            byteArrayOutputStream.write(emptyShortByte);
            byteArrayOutputStream.write(fileHeader.getExternalFileAttributes());
            byteArrayOutputStream.write(offsetLocalHeaderBytes);
            if (fileNameBytes.length > 0) {
                byteArrayOutputStream.write(fileNameBytes);
            }
            if (writeZip64ExtendedInfo) {
                zipModel.setZip64Format(true);
                rawIO.writeShortLittleEndian(byteArrayOutputStream, (int)HeaderSignature.ZIP64_EXTRA_FIELD_SIGNATURE.getValue());
                rawIO.writeShortLittleEndian(byteArrayOutputStream, 28);
                rawIO.writeLongLittleEndian(byteArrayOutputStream, fileHeader.getUncompressedSize());
                rawIO.writeLongLittleEndian(byteArrayOutputStream, fileHeader.getCompressedSize());
                rawIO.writeLongLittleEndian(byteArrayOutputStream, fileHeader.getOffsetLocalHeader());
                rawIO.writeIntLittleEndian(byteArrayOutputStream, fileHeader.getDiskNumberStart());
            }
            if (fileHeader.getAesExtraDataRecord() != null) {
                AESExtraDataRecord aesExtraDataRecord = fileHeader.getAesExtraDataRecord();
                rawIO.writeShortLittleEndian(byteArrayOutputStream, (int)aesExtraDataRecord.getSignature().getValue());
                rawIO.writeShortLittleEndian(byteArrayOutputStream, aesExtraDataRecord.getDataSize());
                rawIO.writeShortLittleEndian(byteArrayOutputStream, aesExtraDataRecord.getAesVersion().getVersionNumber());
                byteArrayOutputStream.write(HeaderUtil.getBytesFromString(aesExtraDataRecord.getVendorID(), charset));
                byte[] aesStrengthBytes = new byte[]{(byte)aesExtraDataRecord.getAesKeyStrength().getRawCode()};
                byteArrayOutputStream.write(aesStrengthBytes);
                rawIO.writeShortLittleEndian(byteArrayOutputStream, aesExtraDataRecord.getCompressionMethod().getCode());
            }
            this.writeRemainingExtraDataRecordsIfPresent(fileHeader, byteArrayOutputStream);
            if (fileCommentBytes.length > 0) {
                byteArrayOutputStream.write(fileCommentBytes);
            }
        }
        catch (Exception e) {
            throw new ZipException(e);
        }
    }

    private int calculateExtraDataRecordsSize(FileHeader fileHeader, boolean writeZip64ExtendedInfo) {
        int extraFieldLength = 0;
        if (writeZip64ExtendedInfo) {
            extraFieldLength += 32;
        }
        if (fileHeader.getAesExtraDataRecord() != null) {
            extraFieldLength += 11;
        }
        if (fileHeader.getExtraDataRecords() != null) {
            for (ExtraDataRecord extraDataRecord : fileHeader.getExtraDataRecords()) {
                if (extraDataRecord.getHeader() == HeaderSignature.AES_EXTRA_DATA_RECORD.getValue() || extraDataRecord.getHeader() == HeaderSignature.ZIP64_EXTRA_FIELD_SIGNATURE.getValue()) continue;
                extraFieldLength += 4 + extraDataRecord.getSizeOfData();
            }
        }
        return extraFieldLength;
    }

    private void writeRemainingExtraDataRecordsIfPresent(FileHeader fileHeader, OutputStream outputStream2) throws IOException {
        if (fileHeader.getExtraDataRecords() == null || fileHeader.getExtraDataRecords().size() == 0) {
            return;
        }
        for (ExtraDataRecord extraDataRecord : fileHeader.getExtraDataRecords()) {
            if (extraDataRecord.getHeader() == HeaderSignature.AES_EXTRA_DATA_RECORD.getValue() || extraDataRecord.getHeader() == HeaderSignature.ZIP64_EXTRA_FIELD_SIGNATURE.getValue()) continue;
            this.rawIO.writeShortLittleEndian(outputStream2, (int)extraDataRecord.getHeader());
            this.rawIO.writeShortLittleEndian(outputStream2, extraDataRecord.getSizeOfData());
            if (extraDataRecord.getSizeOfData() <= 0 || extraDataRecord.getData() == null) continue;
            outputStream2.write(extraDataRecord.getData());
        }
    }

    private void writeZip64EndOfCentralDirectoryRecord(Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecord, ByteArrayOutputStream byteArrayOutputStream, RawIO rawIO) throws IOException {
        rawIO.writeIntLittleEndian(byteArrayOutputStream, (int)zip64EndOfCentralDirectoryRecord.getSignature().getValue());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getSizeOfZip64EndCentralDirectoryRecord());
        rawIO.writeShortLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getVersionMadeBy());
        rawIO.writeShortLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getVersionNeededToExtract());
        rawIO.writeIntLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getNumberOfThisDisk());
        rawIO.writeIntLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getNumberOfThisDiskStartOfCentralDirectory());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getTotalNumberOfEntriesInCentralDirectoryOnThisDisk());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getTotalNumberOfEntriesInCentralDirectory());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getSizeOfCentralDirectory());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getOffsetStartCentralDirectoryWRTStartDiskNumber());
    }

    private void writeZip64EndOfCentralDirectoryLocator(Zip64EndOfCentralDirectoryLocator zip64EndOfCentralDirectoryLocator, ByteArrayOutputStream byteArrayOutputStream, RawIO rawIO) throws IOException {
        rawIO.writeIntLittleEndian(byteArrayOutputStream, (int)HeaderSignature.ZIP64_END_CENTRAL_DIRECTORY_LOCATOR.getValue());
        rawIO.writeIntLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryLocator.getNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryLocator.getOffsetZip64EndOfCentralDirectoryRecord());
        rawIO.writeIntLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryLocator.getTotalNumberOfDiscs());
    }

    private void writeEndOfCentralDirectoryRecord(ZipModel zipModel, int sizeOfCentralDir, long offsetCentralDir, ByteArrayOutputStream byteArrayOutputStream, RawIO rawIO, Charset charset) throws IOException {
        long numEntries;
        byte[] longByte = new byte[8];
        rawIO.writeIntLittleEndian(byteArrayOutputStream, (int)HeaderSignature.END_OF_CENTRAL_DIRECTORY.getValue());
        rawIO.writeShortLittleEndian(byteArrayOutputStream, zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk());
        rawIO.writeShortLittleEndian(byteArrayOutputStream, zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDiskStartOfCentralDir());
        long numEntriesOnThisDisk = numEntries = (long)zipModel.getCentralDirectory().getFileHeaders().size();
        if (zipModel.isSplitArchive()) {
            numEntriesOnThisDisk = this.countNumberOfFileHeaderEntriesOnDisk(zipModel.getCentralDirectory().getFileHeaders(), zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk());
        }
        if (numEntriesOnThisDisk > 65535L) {
            numEntriesOnThisDisk = 65535L;
        }
        rawIO.writeShortLittleEndian(byteArrayOutputStream, (int)numEntriesOnThisDisk);
        if (numEntries > 65535L) {
            numEntries = 65535L;
        }
        rawIO.writeShortLittleEndian(byteArrayOutputStream, (int)numEntries);
        rawIO.writeIntLittleEndian(byteArrayOutputStream, sizeOfCentralDir);
        if (offsetCentralDir > 0xFFFFFFFFL) {
            rawIO.writeLongLittleEndian(longByte, 0, 0xFFFFFFFFL);
            byteArrayOutputStream.write(longByte, 0, 4);
        } else {
            rawIO.writeLongLittleEndian(longByte, 0, offsetCentralDir);
            byteArrayOutputStream.write(longByte, 0, 4);
        }
        String comment = zipModel.getEndOfCentralDirectoryRecord().getComment();
        if (Zip4jUtil.isStringNotNullAndNotEmpty(comment)) {
            byte[] commentBytes = HeaderUtil.getBytesFromString(comment, charset);
            rawIO.writeShortLittleEndian(byteArrayOutputStream, commentBytes.length);
            byteArrayOutputStream.write(commentBytes);
        } else {
            rawIO.writeShortLittleEndian(byteArrayOutputStream, 0);
        }
    }

    private long countNumberOfFileHeaderEntriesOnDisk(List<FileHeader> fileHeaders, int numOfDisk) throws ZipException {
        if (fileHeaders == null) {
            throw new ZipException("file headers are null, cannot calculate number of entries on this disk");
        }
        int noEntries = 0;
        for (FileHeader fileHeader : fileHeaders) {
            if (fileHeader.getDiskNumberStart() != numOfDisk) continue;
            ++noEntries;
        }
        return noEntries;
    }

    private boolean isZip64Entry(FileHeader fileHeader) {
        return fileHeader.getCompressedSize() >= 0xFFFFFFFFL || fileHeader.getUncompressedSize() >= 0xFFFFFFFFL || fileHeader.getOffsetLocalHeader() >= 0xFFFFFFFFL || fileHeader.getDiskNumberStart() >= 65535;
    }

    private long getOffsetOfCentralDirectory(ZipModel zipModel) {
        if (zipModel.isZip64Format() && zipModel.getZip64EndOfCentralDirectoryRecord() != null && zipModel.getZip64EndOfCentralDirectoryRecord().getOffsetStartCentralDirectoryWRTStartDiskNumber() != -1L) {
            return zipModel.getZip64EndOfCentralDirectoryRecord().getOffsetStartCentralDirectoryWRTStartDiskNumber();
        }
        return zipModel.getEndOfCentralDirectoryRecord().getOffsetOfStartOfCentralDirectory();
    }

    private Zip64EndOfCentralDirectoryRecord buildZip64EndOfCentralDirectoryRecord(ZipModel zipModel, int sizeOfCentralDir, long offsetCentralDir) throws ZipException {
        long numEntries;
        Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecord = new Zip64EndOfCentralDirectoryRecord();
        zip64EndOfCentralDirectoryRecord.setSignature(HeaderSignature.ZIP64_END_CENTRAL_DIRECTORY_RECORD);
        zip64EndOfCentralDirectoryRecord.setSizeOfZip64EndCentralDirectoryRecord(44L);
        if (zipModel.getCentralDirectory() != null && zipModel.getCentralDirectory().getFileHeaders() != null && zipModel.getCentralDirectory().getFileHeaders().size() > 0) {
            FileHeader firstFileHeader = zipModel.getCentralDirectory().getFileHeaders().get(0);
            zip64EndOfCentralDirectoryRecord.setVersionMadeBy(firstFileHeader.getVersionMadeBy());
            zip64EndOfCentralDirectoryRecord.setVersionNeededToExtract(firstFileHeader.getVersionNeededToExtract());
        }
        zip64EndOfCentralDirectoryRecord.setNumberOfThisDisk(zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk());
        zip64EndOfCentralDirectoryRecord.setNumberOfThisDiskStartOfCentralDirectory(zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDiskStartOfCentralDir());
        long numEntriesOnThisDisk = numEntries = (long)zipModel.getCentralDirectory().getFileHeaders().size();
        if (zipModel.isSplitArchive()) {
            numEntriesOnThisDisk = this.countNumberOfFileHeaderEntriesOnDisk(zipModel.getCentralDirectory().getFileHeaders(), zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk());
        }
        zip64EndOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectoryOnThisDisk(numEntriesOnThisDisk);
        zip64EndOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectory(numEntries);
        zip64EndOfCentralDirectoryRecord.setSizeOfCentralDirectory(sizeOfCentralDir);
        zip64EndOfCentralDirectoryRecord.setOffsetStartCentralDirectoryWRTStartDiskNumber(offsetCentralDir);
        return zip64EndOfCentralDirectoryRecord;
    }
}

