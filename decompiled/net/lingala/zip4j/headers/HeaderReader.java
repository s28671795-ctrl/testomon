/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.headers;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderSignature;
import net.lingala.zip4j.headers.HeaderUtil;
import net.lingala.zip4j.io.inputstream.NumberedSplitRandomAccessFile;
import net.lingala.zip4j.model.AESExtraDataRecord;
import net.lingala.zip4j.model.AbstractFileHeader;
import net.lingala.zip4j.model.CentralDirectory;
import net.lingala.zip4j.model.DataDescriptor;
import net.lingala.zip4j.model.DigitalSignature;
import net.lingala.zip4j.model.EndOfCentralDirectoryRecord;
import net.lingala.zip4j.model.ExtraDataRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator;
import net.lingala.zip4j.model.Zip64EndOfCentralDirectoryRecord;
import net.lingala.zip4j.model.Zip64ExtendedInfo;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.AesVersion;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import net.lingala.zip4j.util.BitUtils;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.RawIO;
import net.lingala.zip4j.util.Zip4jUtil;

public class HeaderReader {
    private ZipModel zipModel;
    private final RawIO rawIO = new RawIO();
    private final byte[] intBuff = new byte[4];

    public ZipModel readAllHeaders(RandomAccessFile zip4jRaf, Zip4jConfig zip4jConfig) throws IOException {
        if (zip4jRaf.length() == 0L) {
            return new ZipModel();
        }
        if (zip4jRaf.length() < 22L) {
            throw new ZipException("Zip file size less than minimum expected zip file size. Probably not a zip file or a corrupted zip file");
        }
        this.zipModel = new ZipModel();
        try {
            this.zipModel.setEndOfCentralDirectoryRecord(this.readEndOfCentralDirectoryRecord(zip4jRaf, this.rawIO, zip4jConfig));
        }
        catch (ZipException e) {
            throw e;
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new ZipException("Zip headers not found. Probably not a zip file or a corrupted zip file", e);
        }
        if (this.zipModel.getEndOfCentralDirectoryRecord().getTotalNumberOfEntriesInCentralDirectory() == 0) {
            return this.zipModel;
        }
        this.zipModel.setZip64EndOfCentralDirectoryLocator(this.readZip64EndOfCentralDirectoryLocator(zip4jRaf, this.rawIO, this.zipModel.getEndOfCentralDirectoryRecord().getOffsetOfEndOfCentralDirectory()));
        if (this.zipModel.isZip64Format()) {
            this.zipModel.setZip64EndOfCentralDirectoryRecord(this.readZip64EndCentralDirRec(zip4jRaf, this.rawIO));
            if (this.zipModel.getZip64EndOfCentralDirectoryRecord() != null && this.zipModel.getZip64EndOfCentralDirectoryRecord().getNumberOfThisDisk() > 0) {
                this.zipModel.setSplitArchive(true);
            } else {
                this.zipModel.setSplitArchive(false);
            }
        }
        this.zipModel.setCentralDirectory(this.readCentralDirectory(zip4jRaf, this.rawIO, zip4jConfig.getCharset()));
        return this.zipModel;
    }

    private EndOfCentralDirectoryRecord readEndOfCentralDirectoryRecord(RandomAccessFile zip4jRaf, RawIO rawIO, Zip4jConfig zip4jConfig) throws IOException {
        long offsetEndOfCentralDirectory = this.locateOffsetOfEndOfCentralDirectory(zip4jRaf);
        this.seekInCurrentPart(zip4jRaf, offsetEndOfCentralDirectory + 4L);
        EndOfCentralDirectoryRecord endOfCentralDirectoryRecord = new EndOfCentralDirectoryRecord();
        endOfCentralDirectoryRecord.setSignature(HeaderSignature.END_OF_CENTRAL_DIRECTORY);
        endOfCentralDirectoryRecord.setNumberOfThisDisk(rawIO.readShortLittleEndian(zip4jRaf));
        endOfCentralDirectoryRecord.setNumberOfThisDiskStartOfCentralDir(rawIO.readShortLittleEndian(zip4jRaf));
        endOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectoryOnThisDisk(rawIO.readShortLittleEndian(zip4jRaf));
        endOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectory(rawIO.readShortLittleEndian(zip4jRaf));
        endOfCentralDirectoryRecord.setSizeOfCentralDirectory(rawIO.readIntLittleEndian(zip4jRaf));
        endOfCentralDirectoryRecord.setOffsetOfEndOfCentralDirectory(offsetEndOfCentralDirectory);
        zip4jRaf.readFully(this.intBuff);
        endOfCentralDirectoryRecord.setOffsetOfStartOfCentralDirectory(rawIO.readLongLittleEndian(this.intBuff, 0));
        int commentLength = rawIO.readShortLittleEndian(zip4jRaf);
        endOfCentralDirectoryRecord.setComment(this.readZipComment(zip4jRaf, commentLength, zip4jConfig.getCharset()));
        this.zipModel.setSplitArchive(endOfCentralDirectoryRecord.getNumberOfThisDisk() > 0);
        return endOfCentralDirectoryRecord;
    }

    private CentralDirectory readCentralDirectory(RandomAccessFile zip4jRaf, RawIO rawIO, Charset charset) throws IOException {
        CentralDirectory centralDirectory = new CentralDirectory();
        ArrayList<FileHeader> fileHeaders = new ArrayList<FileHeader>();
        long offSetStartCentralDir = HeaderUtil.getOffsetStartOfCentralDirectory(this.zipModel);
        long centralDirEntryCount = this.getNumberOfEntriesInCentralDirectory(this.zipModel);
        zip4jRaf.seek(offSetStartCentralDir);
        byte[] shortBuff = new byte[2];
        byte[] intBuff = new byte[4];
        int i = 0;
        while ((long)i < centralDirEntryCount) {
            FileHeader fileHeader = new FileHeader();
            if ((long)rawIO.readIntLittleEndian(zip4jRaf) != HeaderSignature.CENTRAL_DIRECTORY.getValue()) {
                throw new ZipException("Expected central directory entry not found (#" + (i + 1) + ")");
            }
            fileHeader.setSignature(HeaderSignature.CENTRAL_DIRECTORY);
            fileHeader.setVersionMadeBy(rawIO.readShortLittleEndian(zip4jRaf));
            fileHeader.setVersionNeededToExtract(rawIO.readShortLittleEndian(zip4jRaf));
            byte[] generalPurposeFlags = new byte[2];
            zip4jRaf.readFully(generalPurposeFlags);
            fileHeader.setEncrypted(BitUtils.isBitSet(generalPurposeFlags[0], 0));
            fileHeader.setDataDescriptorExists(BitUtils.isBitSet(generalPurposeFlags[0], 3));
            fileHeader.setFileNameUTF8Encoded(BitUtils.isBitSet(generalPurposeFlags[1], 3));
            fileHeader.setGeneralPurposeFlag((byte[])generalPurposeFlags.clone());
            fileHeader.setCompressionMethod(CompressionMethod.getCompressionMethodFromCode(rawIO.readShortLittleEndian(zip4jRaf)));
            fileHeader.setLastModifiedTime(rawIO.readIntLittleEndian(zip4jRaf));
            zip4jRaf.readFully(intBuff);
            fileHeader.setCrc(rawIO.readLongLittleEndian(intBuff, 0));
            fileHeader.setCompressedSize(rawIO.readLongLittleEndian(zip4jRaf, 4));
            fileHeader.setUncompressedSize(rawIO.readLongLittleEndian(zip4jRaf, 4));
            int fileNameLength = rawIO.readShortLittleEndian(zip4jRaf);
            fileHeader.setFileNameLength(fileNameLength);
            fileHeader.setExtraFieldLength(rawIO.readShortLittleEndian(zip4jRaf));
            int fileCommentLength = rawIO.readShortLittleEndian(zip4jRaf);
            fileHeader.setFileCommentLength(fileCommentLength);
            fileHeader.setDiskNumberStart(rawIO.readShortLittleEndian(zip4jRaf));
            zip4jRaf.readFully(shortBuff);
            fileHeader.setInternalFileAttributes((byte[])shortBuff.clone());
            zip4jRaf.readFully(intBuff);
            fileHeader.setExternalFileAttributes((byte[])intBuff.clone());
            zip4jRaf.readFully(intBuff);
            fileHeader.setOffsetLocalHeader(rawIO.readLongLittleEndian(intBuff, 0));
            if (fileNameLength <= 0) {
                throw new ZipException("Invalid entry name in file header");
            }
            byte[] fileNameBuff = new byte[fileNameLength];
            zip4jRaf.readFully(fileNameBuff);
            String fileName = HeaderUtil.decodeStringWithCharset(fileNameBuff, fileHeader.isFileNameUTF8Encoded(), charset);
            fileHeader.setFileName(fileName);
            fileHeader.setDirectory(this.isDirectory(fileHeader.getExternalFileAttributes(), fileHeader.getFileName()));
            this.readExtraDataRecords(zip4jRaf, fileHeader);
            this.readZip64ExtendedInfo(fileHeader, rawIO);
            this.readAesExtraDataRecord(fileHeader, rawIO);
            if (fileCommentLength > 0) {
                byte[] fileCommentBuff = new byte[fileCommentLength];
                zip4jRaf.readFully(fileCommentBuff);
                fileHeader.setFileComment(HeaderUtil.decodeStringWithCharset(fileCommentBuff, fileHeader.isFileNameUTF8Encoded(), charset));
            }
            if (fileHeader.isEncrypted()) {
                if (fileHeader.getAesExtraDataRecord() != null) {
                    fileHeader.setEncryptionMethod(EncryptionMethod.AES);
                } else {
                    fileHeader.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
                }
            }
            fileHeaders.add(fileHeader);
            ++i;
        }
        centralDirectory.setFileHeaders(fileHeaders);
        DigitalSignature digitalSignature = new DigitalSignature();
        if ((long)rawIO.readIntLittleEndian(zip4jRaf) == HeaderSignature.DIGITAL_SIGNATURE.getValue()) {
            digitalSignature.setSignature(HeaderSignature.DIGITAL_SIGNATURE);
            digitalSignature.setSizeOfData(rawIO.readShortLittleEndian(zip4jRaf));
            if (digitalSignature.getSizeOfData() > 0) {
                byte[] signatureDataBuff = new byte[digitalSignature.getSizeOfData()];
                zip4jRaf.readFully(signatureDataBuff);
                digitalSignature.setSignatureData(new String(signatureDataBuff));
            }
        }
        return centralDirectory;
    }

    private void readExtraDataRecords(RandomAccessFile zip4jRaf, FileHeader fileHeader) throws IOException {
        int extraFieldLength = fileHeader.getExtraFieldLength();
        if (extraFieldLength <= 0) {
            return;
        }
        fileHeader.setExtraDataRecords(this.readExtraDataRecords(zip4jRaf, extraFieldLength));
    }

    private void readExtraDataRecords(InputStream inputStream2, LocalFileHeader localFileHeader) throws IOException {
        int extraFieldLength = localFileHeader.getExtraFieldLength();
        if (extraFieldLength <= 0) {
            return;
        }
        localFileHeader.setExtraDataRecords(this.readExtraDataRecords(inputStream2, extraFieldLength));
    }

    private List<ExtraDataRecord> readExtraDataRecords(RandomAccessFile zip4jRaf, int extraFieldLength) throws IOException {
        if (extraFieldLength < 4) {
            if (extraFieldLength > 0) {
                zip4jRaf.skipBytes(extraFieldLength);
            }
            return null;
        }
        byte[] extraFieldBuf = new byte[extraFieldLength];
        zip4jRaf.read(extraFieldBuf);
        try {
            return this.parseExtraDataRecords(extraFieldBuf, extraFieldLength);
        }
        catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private List<ExtraDataRecord> readExtraDataRecords(InputStream inputStream2, int extraFieldLength) throws IOException {
        if (extraFieldLength < 4) {
            if (extraFieldLength > 0) {
                inputStream2.skip(extraFieldLength);
            }
            return null;
        }
        byte[] extraFieldBuf = new byte[extraFieldLength];
        Zip4jUtil.readFully(inputStream2, extraFieldBuf);
        try {
            return this.parseExtraDataRecords(extraFieldBuf, extraFieldLength);
        }
        catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private List<ExtraDataRecord> parseExtraDataRecords(byte[] extraFieldBuf, int extraFieldLength) {
        int sizeOfRec;
        ArrayList<ExtraDataRecord> extraDataRecords = new ArrayList<ExtraDataRecord>();
        for (int counter = 0; counter < extraFieldLength; counter += sizeOfRec) {
            ExtraDataRecord extraDataRecord = new ExtraDataRecord();
            int header = this.rawIO.readShortLittleEndian(extraFieldBuf, counter);
            extraDataRecord.setHeader(header);
            sizeOfRec = this.rawIO.readShortLittleEndian(extraFieldBuf, counter += 2);
            extraDataRecord.setSizeOfData(sizeOfRec);
            counter += 2;
            if (sizeOfRec > 0) {
                byte[] data = new byte[sizeOfRec];
                System.arraycopy(extraFieldBuf, counter, data, 0, sizeOfRec);
                extraDataRecord.setData(data);
            }
            extraDataRecords.add(extraDataRecord);
        }
        return extraDataRecords.size() > 0 ? extraDataRecords : null;
    }

    private Zip64EndOfCentralDirectoryLocator readZip64EndOfCentralDirectoryLocator(RandomAccessFile zip4jRaf, RawIO rawIO, long offsetEndOfCentralDirectoryRecord) throws IOException {
        Zip64EndOfCentralDirectoryLocator zip64EndOfCentralDirectoryLocator = new Zip64EndOfCentralDirectoryLocator();
        this.setFilePointerToReadZip64EndCentralDirLoc(zip4jRaf, offsetEndOfCentralDirectoryRecord);
        int signature = rawIO.readIntLittleEndian(zip4jRaf);
        if ((long)signature != HeaderSignature.ZIP64_END_CENTRAL_DIRECTORY_LOCATOR.getValue()) {
            this.zipModel.setZip64Format(false);
            return null;
        }
        this.zipModel.setZip64Format(true);
        zip64EndOfCentralDirectoryLocator.setSignature(HeaderSignature.ZIP64_END_CENTRAL_DIRECTORY_LOCATOR);
        zip64EndOfCentralDirectoryLocator.setNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord(rawIO.readIntLittleEndian(zip4jRaf));
        zip64EndOfCentralDirectoryLocator.setOffsetZip64EndOfCentralDirectoryRecord(rawIO.readLongLittleEndian(zip4jRaf));
        zip64EndOfCentralDirectoryLocator.setTotalNumberOfDiscs(rawIO.readIntLittleEndian(zip4jRaf));
        return zip64EndOfCentralDirectoryLocator;
    }

    private Zip64EndOfCentralDirectoryRecord readZip64EndCentralDirRec(RandomAccessFile zip4jRaf, RawIO rawIO) throws IOException {
        if (this.zipModel.getZip64EndOfCentralDirectoryLocator() == null) {
            throw new ZipException("invalid zip64 end of central directory locator");
        }
        long offSetStartOfZip64CentralDir = this.zipModel.getZip64EndOfCentralDirectoryLocator().getOffsetZip64EndOfCentralDirectoryRecord();
        if (offSetStartOfZip64CentralDir < 0L) {
            throw new ZipException("invalid offset for start of end of central directory record");
        }
        zip4jRaf.seek(offSetStartOfZip64CentralDir);
        Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecord = new Zip64EndOfCentralDirectoryRecord();
        int signature = rawIO.readIntLittleEndian(zip4jRaf);
        if ((long)signature != HeaderSignature.ZIP64_END_CENTRAL_DIRECTORY_RECORD.getValue()) {
            throw new ZipException("invalid signature for zip64 end of central directory record");
        }
        zip64EndOfCentralDirectoryRecord.setSignature(HeaderSignature.ZIP64_END_CENTRAL_DIRECTORY_RECORD);
        zip64EndOfCentralDirectoryRecord.setSizeOfZip64EndCentralDirectoryRecord(rawIO.readLongLittleEndian(zip4jRaf));
        zip64EndOfCentralDirectoryRecord.setVersionMadeBy(rawIO.readShortLittleEndian(zip4jRaf));
        zip64EndOfCentralDirectoryRecord.setVersionNeededToExtract(rawIO.readShortLittleEndian(zip4jRaf));
        zip64EndOfCentralDirectoryRecord.setNumberOfThisDisk(rawIO.readIntLittleEndian(zip4jRaf));
        zip64EndOfCentralDirectoryRecord.setNumberOfThisDiskStartOfCentralDirectory(rawIO.readIntLittleEndian(zip4jRaf));
        zip64EndOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectoryOnThisDisk(rawIO.readLongLittleEndian(zip4jRaf));
        zip64EndOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectory(rawIO.readLongLittleEndian(zip4jRaf));
        zip64EndOfCentralDirectoryRecord.setSizeOfCentralDirectory(rawIO.readLongLittleEndian(zip4jRaf));
        zip64EndOfCentralDirectoryRecord.setOffsetStartCentralDirectoryWRTStartDiskNumber(rawIO.readLongLittleEndian(zip4jRaf));
        long extDataSecSize = zip64EndOfCentralDirectoryRecord.getSizeOfZip64EndCentralDirectoryRecord() - 44L;
        if (extDataSecSize > 0L) {
            byte[] extDataSecRecBuf = new byte[(int)extDataSecSize];
            zip4jRaf.readFully(extDataSecRecBuf);
            zip64EndOfCentralDirectoryRecord.setExtensibleDataSector(extDataSecRecBuf);
        }
        return zip64EndOfCentralDirectoryRecord;
    }

    private void readZip64ExtendedInfo(FileHeader fileHeader, RawIO rawIO) {
        if (fileHeader.getExtraDataRecords() == null || fileHeader.getExtraDataRecords().size() <= 0) {
            return;
        }
        Zip64ExtendedInfo zip64ExtendedInfo = this.readZip64ExtendedInfo(fileHeader.getExtraDataRecords(), rawIO, fileHeader.getUncompressedSize(), fileHeader.getCompressedSize(), fileHeader.getOffsetLocalHeader(), fileHeader.getDiskNumberStart());
        if (zip64ExtendedInfo == null) {
            return;
        }
        fileHeader.setZip64ExtendedInfo(zip64ExtendedInfo);
        if (zip64ExtendedInfo.getUncompressedSize() != -1L) {
            fileHeader.setUncompressedSize(zip64ExtendedInfo.getUncompressedSize());
        }
        if (zip64ExtendedInfo.getCompressedSize() != -1L) {
            fileHeader.setCompressedSize(zip64ExtendedInfo.getCompressedSize());
        }
        if (zip64ExtendedInfo.getOffsetLocalHeader() != -1L) {
            fileHeader.setOffsetLocalHeader(zip64ExtendedInfo.getOffsetLocalHeader());
        }
        if (zip64ExtendedInfo.getDiskNumberStart() != -1) {
            fileHeader.setDiskNumberStart(zip64ExtendedInfo.getDiskNumberStart());
        }
    }

    private void readZip64ExtendedInfo(LocalFileHeader localFileHeader, RawIO rawIO) throws ZipException {
        if (localFileHeader == null) {
            throw new ZipException("file header is null in reading Zip64 Extended Info");
        }
        if (localFileHeader.getExtraDataRecords() == null || localFileHeader.getExtraDataRecords().size() <= 0) {
            return;
        }
        Zip64ExtendedInfo zip64ExtendedInfo = this.readZip64ExtendedInfo(localFileHeader.getExtraDataRecords(), rawIO, localFileHeader.getUncompressedSize(), localFileHeader.getCompressedSize(), 0L, 0);
        if (zip64ExtendedInfo == null) {
            return;
        }
        localFileHeader.setZip64ExtendedInfo(zip64ExtendedInfo);
        if (zip64ExtendedInfo.getUncompressedSize() != -1L) {
            localFileHeader.setUncompressedSize(zip64ExtendedInfo.getUncompressedSize());
        }
        if (zip64ExtendedInfo.getCompressedSize() != -1L) {
            localFileHeader.setCompressedSize(zip64ExtendedInfo.getCompressedSize());
        }
    }

    private Zip64ExtendedInfo readZip64ExtendedInfo(List<ExtraDataRecord> extraDataRecords, RawIO rawIO, long uncompressedSize, long compressedSize, long offsetLocalHeader, int diskNumberStart) {
        for (ExtraDataRecord extraDataRecord : extraDataRecords) {
            if (extraDataRecord == null || HeaderSignature.ZIP64_EXTRA_FIELD_SIGNATURE.getValue() != extraDataRecord.getHeader()) continue;
            Zip64ExtendedInfo zip64ExtendedInfo = new Zip64ExtendedInfo();
            byte[] extraData = extraDataRecord.getData();
            if (extraDataRecord.getSizeOfData() <= 0) {
                return null;
            }
            int counter = 0;
            if (counter < extraDataRecord.getSizeOfData() && uncompressedSize == 0xFFFFFFFFL) {
                zip64ExtendedInfo.setUncompressedSize(rawIO.readLongLittleEndian(extraData, counter));
                counter += 8;
            }
            if (counter < extraDataRecord.getSizeOfData() && compressedSize == 0xFFFFFFFFL) {
                zip64ExtendedInfo.setCompressedSize(rawIO.readLongLittleEndian(extraData, counter));
                counter += 8;
            }
            if (counter < extraDataRecord.getSizeOfData() && offsetLocalHeader == 0xFFFFFFFFL) {
                zip64ExtendedInfo.setOffsetLocalHeader(rawIO.readLongLittleEndian(extraData, counter));
                counter += 8;
            }
            if (counter < extraDataRecord.getSizeOfData() && diskNumberStart == 65535) {
                zip64ExtendedInfo.setDiskNumberStart(rawIO.readIntLittleEndian(extraData, counter));
            }
            return zip64ExtendedInfo;
        }
        return null;
    }

    private void setFilePointerToReadZip64EndCentralDirLoc(RandomAccessFile zip4jRaf, long offsetEndOfCentralDirectoryRecord) throws IOException {
        this.seekInCurrentPart(zip4jRaf, offsetEndOfCentralDirectoryRecord - 4L - 8L - 4L - 4L);
    }

    public LocalFileHeader readLocalFileHeader(InputStream inputStream2, Charset charset) throws IOException {
        LocalFileHeader localFileHeader = new LocalFileHeader();
        byte[] intBuff = new byte[4];
        int sig = this.rawIO.readIntLittleEndian(inputStream2);
        if ((long)sig == HeaderSignature.TEMPORARY_SPANNING_MARKER.getValue()) {
            sig = this.rawIO.readIntLittleEndian(inputStream2);
        }
        if ((long)sig != HeaderSignature.LOCAL_FILE_HEADER.getValue()) {
            return null;
        }
        localFileHeader.setSignature(HeaderSignature.LOCAL_FILE_HEADER);
        localFileHeader.setVersionNeededToExtract(this.rawIO.readShortLittleEndian(inputStream2));
        byte[] generalPurposeFlags = new byte[2];
        if (Zip4jUtil.readFully(inputStream2, generalPurposeFlags) != 2) {
            throw new ZipException("Could not read enough bytes for generalPurposeFlags");
        }
        localFileHeader.setEncrypted(BitUtils.isBitSet(generalPurposeFlags[0], 0));
        localFileHeader.setDataDescriptorExists(BitUtils.isBitSet(generalPurposeFlags[0], 3));
        localFileHeader.setFileNameUTF8Encoded(BitUtils.isBitSet(generalPurposeFlags[1], 3));
        localFileHeader.setGeneralPurposeFlag((byte[])generalPurposeFlags.clone());
        localFileHeader.setCompressionMethod(CompressionMethod.getCompressionMethodFromCode(this.rawIO.readShortLittleEndian(inputStream2)));
        localFileHeader.setLastModifiedTime(this.rawIO.readIntLittleEndian(inputStream2));
        Zip4jUtil.readFully(inputStream2, intBuff);
        localFileHeader.setCrc(this.rawIO.readLongLittleEndian(intBuff, 0));
        localFileHeader.setCompressedSize(this.rawIO.readLongLittleEndian(inputStream2, 4));
        localFileHeader.setUncompressedSize(this.rawIO.readLongLittleEndian(inputStream2, 4));
        int fileNameLength = this.rawIO.readShortLittleEndian(inputStream2);
        localFileHeader.setFileNameLength(fileNameLength);
        localFileHeader.setExtraFieldLength(this.rawIO.readShortLittleEndian(inputStream2));
        if (fileNameLength <= 0) {
            throw new ZipException("Invalid entry name in local file header");
        }
        byte[] fileNameBuf = new byte[fileNameLength];
        Zip4jUtil.readFully(inputStream2, fileNameBuf);
        String fileName = HeaderUtil.decodeStringWithCharset(fileNameBuf, localFileHeader.isFileNameUTF8Encoded(), charset);
        localFileHeader.setFileName(fileName);
        localFileHeader.setDirectory(fileName.endsWith("/") || fileName.endsWith("\\"));
        this.readExtraDataRecords(inputStream2, localFileHeader);
        this.readZip64ExtendedInfo(localFileHeader, this.rawIO);
        this.readAesExtraDataRecord(localFileHeader, this.rawIO);
        if (localFileHeader.isEncrypted() && localFileHeader.getEncryptionMethod() != EncryptionMethod.AES) {
            if (BitUtils.isBitSet(localFileHeader.getGeneralPurposeFlag()[0], 6)) {
                localFileHeader.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD_VARIANT_STRONG);
            } else {
                localFileHeader.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
            }
        }
        return localFileHeader;
    }

    public DataDescriptor readDataDescriptor(InputStream inputStream2, boolean isZip64Format) throws IOException {
        DataDescriptor dataDescriptor = new DataDescriptor();
        byte[] intBuff = new byte[4];
        Zip4jUtil.readFully(inputStream2, intBuff);
        long sigOrCrc = this.rawIO.readLongLittleEndian(intBuff, 0);
        if (sigOrCrc == HeaderSignature.EXTRA_DATA_RECORD.getValue()) {
            dataDescriptor.setSignature(HeaderSignature.EXTRA_DATA_RECORD);
            Zip4jUtil.readFully(inputStream2, intBuff);
            dataDescriptor.setCrc(this.rawIO.readLongLittleEndian(intBuff, 0));
        } else {
            dataDescriptor.setCrc(sigOrCrc);
        }
        if (isZip64Format) {
            dataDescriptor.setCompressedSize(this.rawIO.readLongLittleEndian(inputStream2));
            dataDescriptor.setUncompressedSize(this.rawIO.readLongLittleEndian(inputStream2));
        } else {
            dataDescriptor.setCompressedSize(this.rawIO.readIntLittleEndian(inputStream2));
            dataDescriptor.setUncompressedSize(this.rawIO.readIntLittleEndian(inputStream2));
        }
        return dataDescriptor;
    }

    private void readAesExtraDataRecord(AbstractFileHeader fileHeader, RawIO rawIO) throws ZipException {
        if (fileHeader.getExtraDataRecords() == null || fileHeader.getExtraDataRecords().size() <= 0) {
            return;
        }
        AESExtraDataRecord aesExtraDataRecord = this.readAesExtraDataRecord(fileHeader.getExtraDataRecords(), rawIO);
        if (aesExtraDataRecord != null) {
            fileHeader.setAesExtraDataRecord(aesExtraDataRecord);
            fileHeader.setEncryptionMethod(EncryptionMethod.AES);
        }
    }

    private AESExtraDataRecord readAesExtraDataRecord(List<ExtraDataRecord> extraDataRecords, RawIO rawIO) throws ZipException {
        if (extraDataRecords == null) {
            return null;
        }
        for (ExtraDataRecord extraDataRecord : extraDataRecords) {
            if (extraDataRecord == null || extraDataRecord.getHeader() != HeaderSignature.AES_EXTRA_DATA_RECORD.getValue()) continue;
            byte[] aesExtraDataRecordBytes = extraDataRecord.getData();
            if (aesExtraDataRecordBytes == null || aesExtraDataRecordBytes.length != 7) {
                throw new ZipException("corrupt AES extra data records");
            }
            AESExtraDataRecord aesExtraDataRecord = new AESExtraDataRecord();
            aesExtraDataRecord.setSignature(HeaderSignature.AES_EXTRA_DATA_RECORD);
            aesExtraDataRecord.setDataSize(extraDataRecord.getSizeOfData());
            byte[] aesData = extraDataRecord.getData();
            aesExtraDataRecord.setAesVersion(AesVersion.getFromVersionNumber(rawIO.readShortLittleEndian(aesData, 0)));
            byte[] vendorIDBytes = new byte[2];
            System.arraycopy(aesData, 2, vendorIDBytes, 0, 2);
            aesExtraDataRecord.setVendorID(new String(vendorIDBytes));
            aesExtraDataRecord.setAesKeyStrength(AesKeyStrength.getAesKeyStrengthFromRawCode(aesData[4] & 0xFF));
            aesExtraDataRecord.setCompressionMethod(CompressionMethod.getCompressionMethodFromCode(rawIO.readShortLittleEndian(aesData, 5)));
            return aesExtraDataRecord;
        }
        return null;
    }

    private long getNumberOfEntriesInCentralDirectory(ZipModel zipModel) {
        if (zipModel.isZip64Format()) {
            return zipModel.getZip64EndOfCentralDirectoryRecord().getTotalNumberOfEntriesInCentralDirectory();
        }
        return zipModel.getEndOfCentralDirectoryRecord().getTotalNumberOfEntriesInCentralDirectory();
    }

    private long locateOffsetOfEndOfCentralDirectory(RandomAccessFile randomAccessFile) throws IOException {
        long zipFileSize = randomAccessFile.length();
        if (zipFileSize < 22L) {
            throw new ZipException("Zip file size less than size of zip headers. Probably not a zip file.");
        }
        this.seekInCurrentPart(randomAccessFile, zipFileSize - 22L);
        if ((long)this.rawIO.readIntLittleEndian(randomAccessFile) == HeaderSignature.END_OF_CENTRAL_DIRECTORY.getValue()) {
            return zipFileSize - 22L;
        }
        return this.locateOffsetOfEndOfCentralDirectoryByReverseSeek(randomAccessFile);
    }

    private long locateOffsetOfEndOfCentralDirectoryByReverseSeek(RandomAccessFile randomAccessFile) throws IOException {
        long numberOfBytesToRead;
        long currentFilePointer = randomAccessFile.length() - 22L;
        long l = numberOfBytesToRead = randomAccessFile.length() < 65536L ? randomAccessFile.length() : 65536L;
        while (numberOfBytesToRead > 0L && currentFilePointer > 0L) {
            this.seekInCurrentPart(randomAccessFile, --currentFilePointer);
            if ((long)this.rawIO.readIntLittleEndian(randomAccessFile) == HeaderSignature.END_OF_CENTRAL_DIRECTORY.getValue()) {
                return currentFilePointer;
            }
            --numberOfBytesToRead;
        }
        throw new ZipException("Zip headers not found. Probably not a zip file");
    }

    private void seekInCurrentPart(RandomAccessFile randomAccessFile, long pos) throws IOException {
        if (randomAccessFile instanceof NumberedSplitRandomAccessFile) {
            ((NumberedSplitRandomAccessFile)randomAccessFile).seekInCurrentPart(pos);
        } else {
            randomAccessFile.seek(pos);
        }
    }

    private String readZipComment(RandomAccessFile raf, int commentLength, Charset charset) {
        if (commentLength <= 0) {
            return null;
        }
        try {
            byte[] commentBuf = new byte[commentLength];
            raf.readFully(commentBuf);
            return HeaderUtil.decodeStringWithCharset(commentBuf, false, charset != null ? charset : InternalZipConstants.ZIP4J_DEFAULT_CHARSET);
        }
        catch (IOException e) {
            return null;
        }
    }

    public boolean isDirectory(byte[] externalFileAttributes, String fileName) {
        if (externalFileAttributes[0] != 0 && BitUtils.isBitSet(externalFileAttributes[0], 4)) {
            return true;
        }
        if (externalFileAttributes[3] != 0 && BitUtils.isBitSet(externalFileAttributes[3], 6)) {
            return true;
        }
        return fileName != null && (fileName.endsWith("/") || fileName.endsWith("\\"));
    }
}

