/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.model;

import net.lingala.zip4j.headers.HeaderSignature;
import net.lingala.zip4j.model.ZipHeader;

public class EndOfCentralDirectoryRecord
extends ZipHeader {
    private int numberOfThisDisk;
    private int numberOfThisDiskStartOfCentralDir;
    private int totalNumberOfEntriesInCentralDirectoryOnThisDisk;
    private int totalNumberOfEntriesInCentralDirectory;
    private int sizeOfCentralDirectory;
    private long offsetOfStartOfCentralDirectory;
    private long offsetOfEndOfCentralDirectory;
    private String comment = "";

    public EndOfCentralDirectoryRecord() {
        this.setSignature(HeaderSignature.END_OF_CENTRAL_DIRECTORY);
    }

    public int getNumberOfThisDisk() {
        return this.numberOfThisDisk;
    }

    public void setNumberOfThisDisk(int numberOfThisDisk) {
        this.numberOfThisDisk = numberOfThisDisk;
    }

    public int getNumberOfThisDiskStartOfCentralDir() {
        return this.numberOfThisDiskStartOfCentralDir;
    }

    public void setNumberOfThisDiskStartOfCentralDir(int numberOfThisDiskStartOfCentralDir) {
        this.numberOfThisDiskStartOfCentralDir = numberOfThisDiskStartOfCentralDir;
    }

    public int getTotalNumberOfEntriesInCentralDirectoryOnThisDisk() {
        return this.totalNumberOfEntriesInCentralDirectoryOnThisDisk;
    }

    public void setTotalNumberOfEntriesInCentralDirectoryOnThisDisk(int totalNumberOfEntriesInCentralDirectoryOnThisDisk) {
        this.totalNumberOfEntriesInCentralDirectoryOnThisDisk = totalNumberOfEntriesInCentralDirectoryOnThisDisk;
    }

    public int getTotalNumberOfEntriesInCentralDirectory() {
        return this.totalNumberOfEntriesInCentralDirectory;
    }

    public void setTotalNumberOfEntriesInCentralDirectory(int totNoOfEntrisInCentralDir) {
        this.totalNumberOfEntriesInCentralDirectory = totNoOfEntrisInCentralDir;
    }

    public int getSizeOfCentralDirectory() {
        return this.sizeOfCentralDirectory;
    }

    public void setSizeOfCentralDirectory(int sizeOfCentralDirectory) {
        this.sizeOfCentralDirectory = sizeOfCentralDirectory;
    }

    public long getOffsetOfStartOfCentralDirectory() {
        return this.offsetOfStartOfCentralDirectory;
    }

    public void setOffsetOfStartOfCentralDirectory(long offSetOfStartOfCentralDir) {
        this.offsetOfStartOfCentralDirectory = offSetOfStartOfCentralDir;
    }

    public long getOffsetOfEndOfCentralDirectory() {
        return this.offsetOfEndOfCentralDirectory;
    }

    public void setOffsetOfEndOfCentralDirectory(long offsetOfEndOfCentralDirectory) {
        this.offsetOfEndOfCentralDirectory = offsetOfEndOfCentralDirectory;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        if (comment != null) {
            this.comment = comment;
        }
    }
}

