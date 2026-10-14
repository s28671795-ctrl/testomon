/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.model;

import net.lingala.zip4j.model.ZipHeader;

public class Zip64EndOfCentralDirectoryLocator
extends ZipHeader {
    private int numberOfDiskStartOfZip64EndOfCentralDirectoryRecord;
    private long offsetZip64EndOfCentralDirectoryRecord;
    private int totalNumberOfDiscs;

    public int getNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord() {
        return this.numberOfDiskStartOfZip64EndOfCentralDirectoryRecord;
    }

    public void setNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord(int noOfDiskStartOfZip64EndOfCentralDirRec) {
        this.numberOfDiskStartOfZip64EndOfCentralDirectoryRecord = noOfDiskStartOfZip64EndOfCentralDirRec;
    }

    public long getOffsetZip64EndOfCentralDirectoryRecord() {
        return this.offsetZip64EndOfCentralDirectoryRecord;
    }

    public void setOffsetZip64EndOfCentralDirectoryRecord(long offsetZip64EndOfCentralDirectoryRecord) {
        this.offsetZip64EndOfCentralDirectoryRecord = offsetZip64EndOfCentralDirectoryRecord;
    }

    public int getTotalNumberOfDiscs() {
        return this.totalNumberOfDiscs;
    }

    public void setTotalNumberOfDiscs(int totNumberOfDiscs) {
        this.totalNumberOfDiscs = totNumberOfDiscs;
    }
}

