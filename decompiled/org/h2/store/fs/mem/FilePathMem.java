/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.mem;

import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import org.h2.message.DbException;
import org.h2.store.fs.FilePath;
import org.h2.store.fs.mem.FileMem;
import org.h2.store.fs.mem.FileMemData;

public class FilePathMem
extends FilePath {
    private static final TreeMap<String, FileMemData> MEMORY_FILES = new TreeMap();
    private static final FileMemData DIRECTORY = new FileMemData("", false);

    @Override
    public FilePathMem getPath(String string) {
        FilePathMem filePathMem = new FilePathMem();
        filePathMem.name = FilePathMem.getCanonicalPath(string);
        return filePathMem;
    }

    @Override
    public long size() {
        return this.getMemoryFile().length();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void moveTo(FilePath filePath, boolean bl) {
        TreeMap<String, FileMemData> treeMap = MEMORY_FILES;
        synchronized (treeMap) {
            if (!bl && !filePath.name.equals(this.name) && MEMORY_FILES.containsKey(filePath.name)) {
                throw DbException.get(90024, this.name, filePath + " (exists)");
            }
            FileMemData fileMemData = this.getMemoryFile();
            fileMemData.setName(filePath.name);
            MEMORY_FILES.remove(this.name);
            MEMORY_FILES.put(filePath.name, fileMemData);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean createFile() {
        TreeMap<String, FileMemData> treeMap = MEMORY_FILES;
        synchronized (treeMap) {
            if (this.exists()) {
                return false;
            }
            this.getMemoryFile();
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean exists() {
        if (this.isRoot()) {
            return true;
        }
        TreeMap<String, FileMemData> treeMap = MEMORY_FILES;
        synchronized (treeMap) {
            return MEMORY_FILES.get(this.name) != null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void delete() {
        if (this.isRoot()) {
            return;
        }
        TreeMap<String, FileMemData> treeMap = MEMORY_FILES;
        synchronized (treeMap) {
            FileMemData fileMemData = MEMORY_FILES.remove(this.name);
            if (fileMemData != null) {
                fileMemData.truncate(0L);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<FilePath> newDirectoryStream() {
        ArrayList<FilePath> arrayList = new ArrayList<FilePath>();
        TreeMap<String, FileMemData> treeMap = MEMORY_FILES;
        synchronized (treeMap) {
            for (String string : MEMORY_FILES.tailMap(this.name).keySet()) {
                if (!string.startsWith(this.name)) break;
                if (string.equals(this.name) || string.indexOf(47, this.name.length() + 1) >= 0) continue;
                arrayList.add(this.getPath(string));
            }
            return arrayList;
        }
    }

    @Override
    public boolean setReadOnly() {
        return this.getMemoryFile().setReadOnly();
    }

    @Override
    public boolean canWrite() {
        return this.getMemoryFile().canWrite();
    }

    @Override
    public FilePathMem getParent() {
        int n = this.name.lastIndexOf(47);
        return n < 0 ? null : this.getPath(this.name.substring(0, n));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isDirectory() {
        if (this.isRoot()) {
            return true;
        }
        TreeMap<String, FileMemData> treeMap = MEMORY_FILES;
        synchronized (treeMap) {
            FileMemData fileMemData = MEMORY_FILES.get(this.name);
            return fileMemData == DIRECTORY;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isRegularFile() {
        if (this.isRoot()) {
            return false;
        }
        TreeMap<String, FileMemData> treeMap = MEMORY_FILES;
        synchronized (treeMap) {
            FileMemData fileMemData = MEMORY_FILES.get(this.name);
            return fileMemData != null && fileMemData != DIRECTORY;
        }
    }

    @Override
    public boolean isAbsolute() {
        return true;
    }

    @Override
    public FilePathMem toRealPath() {
        return this;
    }

    @Override
    public long lastModified() {
        return this.getMemoryFile().getLastModified();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void createDirectory() {
        if (this.exists()) {
            throw DbException.get(90062, this.name + " (a file with this name already exists)");
        }
        TreeMap<String, FileMemData> treeMap = MEMORY_FILES;
        synchronized (treeMap) {
            MEMORY_FILES.put(this.name, DIRECTORY);
        }
    }

    @Override
    public FileChannel open(String string) {
        FileMemData fileMemData = this.getMemoryFile();
        return new FileMem(fileMemData, "r".equals(string));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private FileMemData getMemoryFile() {
        TreeMap<String, FileMemData> treeMap = MEMORY_FILES;
        synchronized (treeMap) {
            FileMemData fileMemData = MEMORY_FILES.get(this.name);
            if (fileMemData == DIRECTORY) {
                throw DbException.get(90062, this.name + " (a directory with this name already exists)");
            }
            if (fileMemData == null) {
                fileMemData = new FileMemData(this.name, this.compressed());
                MEMORY_FILES.put(this.name, fileMemData);
            }
            return fileMemData;
        }
    }

    private boolean isRoot() {
        return this.name.equals(this.getScheme() + ":");
    }

    protected static String getCanonicalPath(String string) {
        string = string.replace('\\', '/');
        int n = string.indexOf(58) + 1;
        if (string.length() > n && string.charAt(n) != '/') {
            string = string.substring(0, n) + "/" + string.substring(n);
        }
        return string;
    }

    @Override
    public String getScheme() {
        return "memFS";
    }

    boolean compressed() {
        return false;
    }
}

