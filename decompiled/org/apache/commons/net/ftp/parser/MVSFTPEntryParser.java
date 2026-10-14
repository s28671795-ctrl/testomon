/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import java.util.List;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.parser.ConfigurableFTPFileEntryParserImpl;
import org.apache.commons.net.ftp.parser.UnixFTPEntryParser;

public class MVSFTPEntryParser
extends ConfigurableFTPFileEntryParserImpl {
    static final int UNKNOWN_LIST_TYPE = -1;
    static final int FILE_LIST_TYPE = 0;
    static final int MEMBER_LIST_TYPE = 1;
    static final int UNIX_LIST_TYPE = 2;
    static final int JES_LEVEL_1_LIST_TYPE = 3;
    static final int JES_LEVEL_2_LIST_TYPE = 4;
    private int isType = -1;
    private UnixFTPEntryParser unixFTPEntryParser;
    static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm";
    static final String FILE_LIST_REGEX = "\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+(?:\\S+\\s+)?(?:F|FB|V|VB|U)\\s+\\S+\\s+\\S+\\s+(PS|PO|PO-E)\\s+(\\S+)\\s*";
    static final String MEMBER_LIST_REGEX = "(\\S+)\\s+\\S+\\s+\\S+\\s+(\\S+)\\s+(\\S+)\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s*";
    static final String JES_LEVEL_1_LIST_REGEX = "(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*";
    static final String JES_LEVEL_2_LIST_REGEX = "(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+).*";

    public MVSFTPEntryParser() {
        super("");
        super.configure(null);
    }

    @Override
    public FTPFile parseFTPEntry(String entry) {
        if (this.isType == 0) {
            return this.parseFileList(entry);
        }
        if (this.isType == 1) {
            return this.parseMemberList(entry);
        }
        if (this.isType == 2) {
            return this.unixFTPEntryParser.parseFTPEntry(entry);
        }
        if (this.isType == 3) {
            return this.parseJeslevel1List(entry);
        }
        if (this.isType == 4) {
            return this.parseJeslevel2List(entry);
        }
        return null;
    }

    private FTPFile parseFileList(String entry) {
        if (this.matches(entry)) {
            FTPFile file = new FTPFile();
            file.setRawListing(entry);
            String name = this.group(2);
            String dsorg = this.group(1);
            file.setName(name);
            if ("PS".equals(dsorg)) {
                file.setType(0);
            } else if ("PO".equals(dsorg) || "PO-E".equals(dsorg)) {
                file.setType(1);
            } else {
                return null;
            }
            return file;
        }
        return null;
    }

    private FTPFile parseMemberList(String entry) {
        FTPFile file = new FTPFile();
        if (this.matches(entry)) {
            file.setRawListing(entry);
            String name = this.group(1);
            String datestr = this.group(2) + " " + this.group(3);
            file.setName(name);
            file.setType(0);
            try {
                file.setTimestamp(super.parseTimestamp(datestr));
            }
            catch (ParseException e) {
                // empty catch block
            }
            return file;
        }
        if (entry != null && entry.trim().length() > 0) {
            file.setRawListing(entry);
            String name = entry.split(" ")[0];
            file.setName(name);
            file.setType(0);
            return file;
        }
        return null;
    }

    private FTPFile parseJeslevel1List(String entry) {
        if (this.matches(entry)) {
            FTPFile file = new FTPFile();
            if (this.group(3).equalsIgnoreCase("OUTPUT")) {
                file.setRawListing(entry);
                String name = this.group(2);
                file.setName(name);
                file.setType(0);
                return file;
            }
        }
        return null;
    }

    private FTPFile parseJeslevel2List(String entry) {
        if (this.matches(entry)) {
            FTPFile file = new FTPFile();
            if (this.group(4).equalsIgnoreCase("OUTPUT")) {
                file.setRawListing(entry);
                String name = this.group(2);
                file.setName(name);
                file.setType(0);
                return file;
            }
        }
        return null;
    }

    @Override
    public List<String> preParse(List<String> orig) {
        if (orig != null && orig.size() > 0) {
            String header = orig.get(0);
            if (header.indexOf("Volume") >= 0 && header.indexOf("Dsname") >= 0) {
                this.setType(0);
                super.setRegex(FILE_LIST_REGEX);
            } else if (header.indexOf("Name") >= 0 && header.indexOf("Id") >= 0) {
                this.setType(1);
                super.setRegex(MEMBER_LIST_REGEX);
            } else if (header.indexOf("total") == 0) {
                this.setType(2);
                this.unixFTPEntryParser = new UnixFTPEntryParser();
            } else if (header.indexOf("Spool Files") >= 30) {
                this.setType(3);
                super.setRegex(JES_LEVEL_1_LIST_REGEX);
            } else if (header.indexOf("JOBNAME") == 0 && header.indexOf("JOBID") > 8) {
                this.setType(4);
                super.setRegex(JES_LEVEL_2_LIST_REGEX);
            } else {
                this.setType(-1);
            }
            if (this.isType != 3) {
                orig.remove(0);
            }
        }
        return orig;
    }

    void setType(int type) {
        this.isType = type;
    }

    @Override
    protected FTPClientConfig getDefaultConfiguration() {
        return new FTPClientConfig("MVS", DEFAULT_DATE_FORMAT, null);
    }
}

