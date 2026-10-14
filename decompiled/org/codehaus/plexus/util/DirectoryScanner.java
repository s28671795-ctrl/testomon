/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.codehaus.plexus.util.AbstractScanner;
import org.codehaus.plexus.util.MatchPattern;
import org.codehaus.plexus.util.NioFiles;

public class DirectoryScanner
extends AbstractScanner {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    protected File basedir;
    protected ArrayList<String> filesIncluded;
    protected ArrayList<String> filesNotIncluded;
    protected ArrayList<String> filesExcluded;
    protected ArrayList<String> dirsIncluded;
    protected ArrayList<String> dirsNotIncluded;
    protected ArrayList<String> dirsExcluded;
    protected ArrayList<String> filesDeselected;
    protected ArrayList<String> dirsDeselected;
    protected boolean haveSlowResults = false;
    private boolean followSymlinks = true;
    protected boolean everythingIncluded = true;
    private final char[][] tokenizedEmpty = MatchPattern.tokenizePathToCharArray("", File.separator);

    public void setBasedir(String basedir) {
        this.setBasedir(new File(basedir.replace('/', File.separatorChar).replace('\\', File.separatorChar)));
    }

    public void setBasedir(File basedir) {
        this.basedir = basedir;
    }

    @Override
    public File getBasedir() {
        return this.basedir;
    }

    public void setFollowSymlinks(boolean followSymlinks) {
        this.followSymlinks = followSymlinks;
    }

    public boolean isEverythingIncluded() {
        return this.everythingIncluded;
    }

    @Override
    public void scan() throws IllegalStateException {
        if (this.basedir == null) {
            throw new IllegalStateException("No basedir set");
        }
        if (!this.basedir.exists()) {
            throw new IllegalStateException("basedir " + this.basedir + " does not exist");
        }
        if (!this.basedir.isDirectory()) {
            throw new IllegalStateException("basedir " + this.basedir + " is not a directory");
        }
        this.setupDefaultFilters();
        this.setupMatchPatterns();
        this.filesIncluded = new ArrayList();
        this.filesNotIncluded = new ArrayList();
        this.filesExcluded = new ArrayList();
        this.filesDeselected = new ArrayList();
        this.dirsIncluded = new ArrayList();
        this.dirsNotIncluded = new ArrayList();
        this.dirsExcluded = new ArrayList();
        this.dirsDeselected = new ArrayList();
        if (this.isIncluded("", this.tokenizedEmpty)) {
            if (!this.isExcluded("", this.tokenizedEmpty)) {
                if (this.isSelected("", this.basedir)) {
                    this.dirsIncluded.add("");
                } else {
                    this.dirsDeselected.add("");
                }
            } else {
                this.dirsExcluded.add("");
            }
        } else {
            this.dirsNotIncluded.add("");
        }
        this.scandir(this.basedir, "", true);
    }

    protected void slowScan() {
        if (this.haveSlowResults) {
            return;
        }
        String[] excl = this.dirsExcluded.toArray(EMPTY_STRING_ARRAY);
        String[] notIncl = this.dirsNotIncluded.toArray(EMPTY_STRING_ARRAY);
        for (String anExcl : excl) {
            if (this.couldHoldIncluded(anExcl)) continue;
            this.scandir(new File(this.basedir, anExcl), anExcl + File.separator, false);
        }
        for (String aNotIncl : notIncl) {
            if (this.couldHoldIncluded(aNotIncl)) continue;
            this.scandir(new File(this.basedir, aNotIncl), aNotIncl + File.separator, false);
        }
        this.haveSlowResults = true;
    }

    protected void scandir(File dir, String vpath, boolean fast) {
        String[] newfiles = dir.list();
        if (newfiles == null) {
            newfiles = EMPTY_STRING_ARRAY;
        }
        if (!this.followSymlinks) {
            try {
                if (this.isParentSymbolicLink(dir, null)) {
                    for (String newfile : newfiles) {
                        String name = vpath + newfile;
                        File file = new File(dir, newfile);
                        if (file.isDirectory()) {
                            this.dirsExcluded.add(name);
                            continue;
                        }
                        this.filesExcluded.add(name);
                    }
                    return;
                }
            }
            catch (IOException ioe) {
                String msg = "IOException caught while checking for links!";
                System.err.println(msg);
            }
        }
        if (this.filenameComparator != null) {
            Arrays.sort(newfiles, this.filenameComparator);
        }
        for (String newfile : newfiles) {
            String name = vpath + newfile;
            char[][] tokenizedName = MatchPattern.tokenizePathToCharArray(name, File.separator);
            File file = new File(dir, newfile);
            if (file.isDirectory()) {
                if (this.isIncluded(name, tokenizedName)) {
                    if (!this.isExcluded(name, tokenizedName)) {
                        if (this.isSelected(name, file)) {
                            this.dirsIncluded.add(name);
                            if (fast) {
                                this.scandir(file, name + File.separator, fast);
                            }
                        } else {
                            this.everythingIncluded = false;
                            this.dirsDeselected.add(name);
                            if (fast && this.couldHoldIncluded(name)) {
                                this.scandir(file, name + File.separator, fast);
                            }
                        }
                    } else {
                        this.everythingIncluded = false;
                        this.dirsExcluded.add(name);
                        if (fast && this.couldHoldIncluded(name)) {
                            this.scandir(file, name + File.separator, fast);
                        }
                    }
                } else {
                    this.everythingIncluded = false;
                    this.dirsNotIncluded.add(name);
                    if (fast && this.couldHoldIncluded(name)) {
                        this.scandir(file, name + File.separator, fast);
                    }
                }
                if (fast) continue;
                this.scandir(file, name + File.separator, fast);
                continue;
            }
            if (!file.isFile()) continue;
            if (this.isIncluded(name, tokenizedName)) {
                if (!this.isExcluded(name, tokenizedName)) {
                    if (this.isSelected(name, file)) {
                        this.filesIncluded.add(name);
                        continue;
                    }
                    this.everythingIncluded = false;
                    this.filesDeselected.add(name);
                    continue;
                }
                this.everythingIncluded = false;
                this.filesExcluded.add(name);
                continue;
            }
            this.everythingIncluded = false;
            this.filesNotIncluded.add(name);
        }
    }

    protected boolean isSelected(String name, File file) {
        return true;
    }

    @Override
    public String[] getIncludedFiles() {
        return this.filesIncluded.toArray(EMPTY_STRING_ARRAY);
    }

    public String[] getNotIncludedFiles() {
        this.slowScan();
        return this.filesNotIncluded.toArray(EMPTY_STRING_ARRAY);
    }

    public String[] getExcludedFiles() {
        this.slowScan();
        return this.filesExcluded.toArray(EMPTY_STRING_ARRAY);
    }

    public String[] getDeselectedFiles() {
        this.slowScan();
        return this.filesDeselected.toArray(EMPTY_STRING_ARRAY);
    }

    @Override
    public String[] getIncludedDirectories() {
        return this.dirsIncluded.toArray(EMPTY_STRING_ARRAY);
    }

    public String[] getNotIncludedDirectories() {
        this.slowScan();
        return this.dirsNotIncluded.toArray(EMPTY_STRING_ARRAY);
    }

    public String[] getExcludedDirectories() {
        this.slowScan();
        return this.dirsExcluded.toArray(EMPTY_STRING_ARRAY);
    }

    public String[] getDeselectedDirectories() {
        this.slowScan();
        return this.dirsDeselected.toArray(EMPTY_STRING_ARRAY);
    }

    public boolean isSymbolicLink(File parent, String name) throws IOException {
        return NioFiles.isSymbolicLink(new File(parent, name));
    }

    public boolean isParentSymbolicLink(File parent, String name) throws IOException {
        return NioFiles.isSymbolicLink(parent);
    }
}

