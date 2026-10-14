/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.niomem;

import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import org.h2.message.DbException;
import org.h2.store.fs.FilePath;
import org.h2.store.fs.niomem.FileNioMem;
import org.h2.store.fs.niomem.FileNioMemData;

public class FilePathNioMem
extends FilePath {
    private static final TreeMap<String, FileNioMemData> MEMORY_FILES = new TreeMap();
    float compressLaterCachePercent = 1.0f;

    @Override
    public FilePathNioMem getPath(String string) {
        FilePathNioMem filePathNioMem = new FilePathNioMem();
        filePathNioMem.name = FilePathNioMem.getCanonicalPath(string);
        return filePathNioMem;
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
        TreeMap<String, FileNioMemData> treeMap = MEMORY_FILES;
        synchronized (treeMap) {
            if (!bl && !this.name.equals(filePath.name) && MEMORY_FILES.containsKey(filePath.name)) {
                throw DbException.get(90024, this.name, filePath + " (exists)");
            }
            FileNioMemData fileNioMemData = this.getMemoryFile();
            fileNioMemData.setName(filePath.name);
            MEMORY_FILES.remove(this.name);
            MEMORY_FILES.put(filePath.name, fileNioMemData);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean createFile() {
        TreeMap<String, FileNioMemData> treeMap = MEMORY_FILES;
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
        TreeMap<String, FileNioMemData> treeMap = MEMORY_FILES;
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
        TreeMap<String, FileNioMemData> treeMap = MEMORY_FILES;
        synchronized (treeMap) {
            MEMORY_FILES.remove(this.name);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<FilePath> newDirectoryStream() {
        ArrayList<FilePath> arrayList = new ArrayList<FilePath>();
        TreeMap<String, FileNioMemData> treeMap = MEMORY_FILES;
        synchronized (treeMap) {
            for (String string : MEMORY_FILES.tailMap(this.name).keySet()) {
                if (!string.startsWith(this.name)) break;
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
    public FilePathNioMem getParent() {
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
        TreeMap<String, FileNioMemData> treeMap = MEMORY_FILES;
        synchronized (treeMap) {
            return MEMORY_FILES.get(this.name) == null;
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
        TreeMap<String, FileNioMemData> treeMap = MEMORY_FILES;
        synchronized (treeMap) {
            return MEMORY_FILES.get(this.name) != null;
        }
    }

    @Override
    public boolean isAbsolute() {
        return true;
    }

    @Override
    public FilePathNioMem toRealPath() {
        return this;
    }

    @Override
    public long lastModified() {
        return this.getMemoryFile().getLastModified();
    }

    @Override
    public void createDirectory() {
        if (this.exists() && this.isDirectory()) {
            throw DbException.get(90062, this.name + " (a file with this name already exists)");
        }
    }

    @Override
    public FileChannel open(String string) {
        FileNioMemData fileNioMemData = this.getMemoryFile();
        return new FileNioMem(fileNioMemData, "r".equals(string));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private FileNioMemData getMemoryFile() {
        TreeMap<String, FileNioMemData> treeMap = MEMORY_FILES;
        synchronized (treeMap) {
            FileNioMemData fileNioMemData = MEMORY_FILES.get(this.name);
            if (fileNioMemData == null) {
                fileNioMemData = new FileNioMemData(this.name, this.compressed(), this.compressLaterCachePercent);
                MEMORY_FILES.put(this.name, fileNioMemData);
            }
            return fileNioMemData;
        }
    }

    protected boolean isRoot() {
        return this.name.equals(this.getScheme() + ":");
    }

    protected static String getCanonicalPath(String string) {
        string = string.replace('\\', '/');
        int n = string.lastIndexOf(58) + 1;
        if (string.length() > n && string.charAt(n) != '/') {
            string = string.substring(0, n) + "/" + string.substring(n);
        }
        return string;
    }

    @Override
    public String getScheme() {
        return "nioMemFS";
    }

    boolean compressed() {
        return false;
    }
}

