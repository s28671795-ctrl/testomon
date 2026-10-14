/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.model;

import net.lingala.zip4j.model.ExcludeFileFilter;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.AesVersion;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;

public class ZipParameters {
    private CompressionMethod compressionMethod = CompressionMethod.DEFLATE;
    private CompressionLevel compressionLevel = CompressionLevel.NORMAL;
    private boolean encryptFiles = false;
    private EncryptionMethod encryptionMethod = EncryptionMethod.NONE;
    private boolean readHiddenFiles = true;
    private boolean readHiddenFolders = true;
    private AesKeyStrength aesKeyStrength = AesKeyStrength.KEY_STRENGTH_256;
    private AesVersion aesVersion = AesVersion.TWO;
    private boolean includeRootFolder = true;
    private long entryCRC;
    private String defaultFolderPath;
    private String fileNameInZip;
    private long lastModifiedFileTime = 0L;
    private long entrySize = -1L;
    private boolean writeExtendedLocalFileHeader = true;
    private boolean overrideExistingFilesInZip = true;
    private String rootFolderNameInZip;
    private String fileComment;
    private SymbolicLinkAction symbolicLinkAction = SymbolicLinkAction.INCLUDE_LINKED_FILE_ONLY;
    private ExcludeFileFilter excludeFileFilter;
    private boolean unixMode;

    public ZipParameters() {
    }

    public ZipParameters(ZipParameters zipParameters) {
        this.compressionMethod = zipParameters.getCompressionMethod();
        this.compressionLevel = zipParameters.getCompressionLevel();
        this.encryptFiles = zipParameters.isEncryptFiles();
        this.encryptionMethod = zipParameters.getEncryptionMethod();
        this.readHiddenFiles = zipParameters.isReadHiddenFiles();
        this.readHiddenFolders = zipParameters.isReadHiddenFolders();
        this.aesKeyStrength = zipParameters.getAesKeyStrength();
        this.aesVersion = zipParameters.getAesVersion();
        this.includeRootFolder = zipParameters.isIncludeRootFolder();
        this.entryCRC = zipParameters.getEntryCRC();
        this.defaultFolderPath = zipParameters.getDefaultFolderPath();
        this.fileNameInZip = zipParameters.getFileNameInZip();
        this.lastModifiedFileTime = zipParameters.getLastModifiedFileTime();
        this.entrySize = zipParameters.getEntrySize();
        this.writeExtendedLocalFileHeader = zipParameters.isWriteExtendedLocalFileHeader();
        this.overrideExistingFilesInZip = zipParameters.isOverrideExistingFilesInZip();
        this.rootFolderNameInZip = zipParameters.getRootFolderNameInZip();
        this.fileComment = zipParameters.getFileComment();
        this.symbolicLinkAction = zipParameters.getSymbolicLinkAction();
        this.excludeFileFilter = zipParameters.getExcludeFileFilter();
        this.unixMode = zipParameters.isUnixMode();
    }

    public CompressionMethod getCompressionMethod() {
        return this.compressionMethod;
    }

    public void setCompressionMethod(CompressionMethod compressionMethod) {
        this.compressionMethod = compressionMethod;
    }

    public boolean isEncryptFiles() {
        return this.encryptFiles;
    }

    public void setEncryptFiles(boolean encryptFiles) {
        this.encryptFiles = encryptFiles;
    }

    public EncryptionMethod getEncryptionMethod() {
        return this.encryptionMethod;
    }

    public void setEncryptionMethod(EncryptionMethod encryptionMethod) {
        this.encryptionMethod = encryptionMethod;
    }

    public CompressionLevel getCompressionLevel() {
        return this.compressionLevel;
    }

    public void setCompressionLevel(CompressionLevel compressionLevel) {
        this.compressionLevel = compressionLevel;
    }

    public boolean isReadHiddenFiles() {
        return this.readHiddenFiles;
    }

    public void setReadHiddenFiles(boolean readHiddenFiles) {
        this.readHiddenFiles = readHiddenFiles;
    }

    public boolean isReadHiddenFolders() {
        return this.readHiddenFolders;
    }

    public void setReadHiddenFolders(boolean readHiddenFolders) {
        this.readHiddenFolders = readHiddenFolders;
    }

    public AesKeyStrength getAesKeyStrength() {
        return this.aesKeyStrength;
    }

    public void setAesKeyStrength(AesKeyStrength aesKeyStrength) {
        this.aesKeyStrength = aesKeyStrength;
    }

    public AesVersion getAesVersion() {
        return this.aesVersion;
    }

    public void setAesVersion(AesVersion aesVersion) {
        this.aesVersion = aesVersion;
    }

    public boolean isIncludeRootFolder() {
        return this.includeRootFolder;
    }

    public void setIncludeRootFolder(boolean includeRootFolder) {
        this.includeRootFolder = includeRootFolder;
    }

    public long getEntryCRC() {
        return this.entryCRC;
    }

    public void setEntryCRC(long entryCRC) {
        this.entryCRC = entryCRC;
    }

    public String getDefaultFolderPath() {
        return this.defaultFolderPath;
    }

    public void setDefaultFolderPath(String defaultFolderPath) {
        this.defaultFolderPath = defaultFolderPath;
    }

    public String getFileNameInZip() {
        return this.fileNameInZip;
    }

    public void setFileNameInZip(String fileNameInZip) {
        this.fileNameInZip = fileNameInZip;
    }

    public long getLastModifiedFileTime() {
        return this.lastModifiedFileTime;
    }

    public void setLastModifiedFileTime(long lastModifiedFileTime) {
        if (lastModifiedFileTime < 0L) {
            this.lastModifiedFileTime = 0L;
            return;
        }
        this.lastModifiedFileTime = lastModifiedFileTime;
    }

    public long getEntrySize() {
        return this.entrySize;
    }

    public void setEntrySize(long entrySize) {
        this.entrySize = entrySize;
    }

    public boolean isWriteExtendedLocalFileHeader() {
        return this.writeExtendedLocalFileHeader;
    }

    public void setWriteExtendedLocalFileHeader(boolean writeExtendedLocalFileHeader) {
        this.writeExtendedLocalFileHeader = writeExtendedLocalFileHeader;
    }

    public boolean isOverrideExistingFilesInZip() {
        return this.overrideExistingFilesInZip;
    }

    public void setOverrideExistingFilesInZip(boolean overrideExistingFilesInZip) {
        this.overrideExistingFilesInZip = overrideExistingFilesInZip;
    }

    public String getRootFolderNameInZip() {
        return this.rootFolderNameInZip;
    }

    public void setRootFolderNameInZip(String rootFolderNameInZip) {
        this.rootFolderNameInZip = rootFolderNameInZip;
    }

    public String getFileComment() {
        return this.fileComment;
    }

    public void setFileComment(String fileComment) {
        this.fileComment = fileComment;
    }

    public SymbolicLinkAction getSymbolicLinkAction() {
        return this.symbolicLinkAction;
    }

    public void setSymbolicLinkAction(SymbolicLinkAction symbolicLinkAction) {
        this.symbolicLinkAction = symbolicLinkAction;
    }

    public ExcludeFileFilter getExcludeFileFilter() {
        return this.excludeFileFilter;
    }

    public void setExcludeFileFilter(ExcludeFileFilter excludeFileFilter) {
        this.excludeFileFilter = excludeFileFilter;
    }

    public boolean isUnixMode() {
        return this.unixMode;
    }

    public void setUnixMode(boolean unixMode) {
        this.unixMode = unixMode;
    }

    public static enum SymbolicLinkAction {
        INCLUDE_LINK_ONLY,
        INCLUDE_LINKED_FILE_ONLY,
        INCLUDE_LINK_AND_LINKED_FILE;

    }
}

