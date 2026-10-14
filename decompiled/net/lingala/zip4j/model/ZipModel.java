/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.lingala.zip4j.model.ArchiveExtraDataRecord;
import net.lingala.zip4j.model.CentralDirectory;
import net.lingala.zip4j.model.DataDescriptor;
import net.lingala.zip4j.model.EndOfCentralDirectoryRecord;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator;
import net.lingala.zip4j.model.Zip64EndOfCentralDirectoryRecord;

public class ZipModel
implements Cloneable {
    private List<LocalFileHeader> localFileHeaders = new ArrayList<LocalFileHeader>();
    private List<DataDescriptor> dataDescriptors = new ArrayList<DataDescriptor>();
    private ArchiveExtraDataRecord archiveExtraDataRecord = new ArchiveExtraDataRecord();
    private CentralDirectory centralDirectory = new CentralDirectory();
    private EndOfCentralDirectoryRecord endOfCentralDirectoryRecord = new EndOfCentralDirectoryRecord();
    private Zip64EndOfCentralDirectoryLocator zip64EndOfCentralDirectoryLocator = new Zip64EndOfCentralDirectoryLocator();
    private Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecord = new Zip64EndOfCentralDirectoryRecord();
    private boolean splitArchive;
    private long splitLength = -1L;
    private File zipFile;
    private boolean isZip64Format = false;
    private boolean isNestedZipFile;
    private long start;
    private long end;

    public List<LocalFileHeader> getLocalFileHeaders() {
        return this.localFileHeaders;
    }

    public void setLocalFileHeaders(List<LocalFileHeader> localFileHeaderList) {
        this.localFileHeaders = localFileHeaderList;
    }

    public List<DataDescriptor> getDataDescriptors() {
        return this.dataDescriptors;
    }

    public void setDataDescriptors(List<DataDescriptor> dataDescriptors) {
        this.dataDescriptors = dataDescriptors;
    }

    public CentralDirectory getCentralDirectory() {
        return this.centralDirectory;
    }

    public void setCentralDirectory(CentralDirectory centralDirectory) {
        this.centralDirectory = centralDirectory;
    }

    public EndOfCentralDirectoryRecord getEndOfCentralDirectoryRecord() {
        return this.endOfCentralDirectoryRecord;
    }

    public void setEndOfCentralDirectoryRecord(EndOfCentralDirectoryRecord endOfCentralDirectoryRecord) {
        this.endOfCentralDirectoryRecord = endOfCentralDirectoryRecord;
    }

    public ArchiveExtraDataRecord getArchiveExtraDataRecord() {
        return this.archiveExtraDataRecord;
    }

    public void setArchiveExtraDataRecord(ArchiveExtraDataRecord archiveExtraDataRecord) {
        this.archiveExtraDataRecord = archiveExtraDataRecord;
    }

    public boolean isSplitArchive() {
        return this.splitArchive;
    }

    public void setSplitArchive(boolean splitArchive) {
        this.splitArchive = splitArchive;
    }

    public File getZipFile() {
        return this.zipFile;
    }

    public void setZipFile(File zipFile) {
        this.zipFile = zipFile;
    }

    public Zip64EndOfCentralDirectoryLocator getZip64EndOfCentralDirectoryLocator() {
        return this.zip64EndOfCentralDirectoryLocator;
    }

    public void setZip64EndOfCentralDirectoryLocator(Zip64EndOfCentralDirectoryLocator zip64EndOfCentralDirectoryLocator) {
        this.zip64EndOfCentralDirectoryLocator = zip64EndOfCentralDirectoryLocator;
    }

    public Zip64EndOfCentralDirectoryRecord getZip64EndOfCentralDirectoryRecord() {
        return this.zip64EndOfCentralDirectoryRecord;
    }

    public void setZip64EndOfCentralDirectoryRecord(Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecord) {
        this.zip64EndOfCentralDirectoryRecord = zip64EndOfCentralDirectoryRecord;
    }

    public boolean isZip64Format() {
        return this.isZip64Format;
    }

    public void setZip64Format(boolean isZip64Format) {
        this.isZip64Format = isZip64Format;
    }

    public boolean isNestedZipFile() {
        return this.isNestedZipFile;
    }

    public void setNestedZipFile(boolean isNestedZipFile) {
        this.isNestedZipFile = isNestedZipFile;
    }

    public long getStart() {
        return this.start;
    }

    public void setStart(long start2) {
        this.start = start2;
    }

    public long getEnd() {
        return this.end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getSplitLength() {
        return this.splitLength;
    }

    public void setSplitLength(long splitLength) {
        this.splitLength = splitLength;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

