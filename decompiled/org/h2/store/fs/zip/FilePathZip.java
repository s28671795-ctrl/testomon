/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.zip;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.h2.message.DbException;
import org.h2.store.fs.FilePath;
import org.h2.store.fs.disk.FilePathDisk;
import org.h2.store.fs.zip.FileZip;

public class FilePathZip
extends FilePath {
    @Override
    public FilePathZip getPath(String string) {
        FilePathZip filePathZip = new FilePathZip();
        filePathZip.name = string;
        return filePathZip;
    }

    @Override
    public void createDirectory() {
    }

    @Override
    public boolean createFile() {
        throw DbException.getUnsupportedException("write");
    }

    @Override
    public void delete() {
        throw DbException.getUnsupportedException("write");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean exists() {
        try {
            String string = this.getEntryName();
            if (string.isEmpty()) {
                return true;
            }
            try (ZipFile zipFile = this.openZipFile();){
                boolean bl = zipFile.getEntry(string) != null;
                return bl;
            }
        }
        catch (IOException iOException) {
            return false;
        }
    }

    @Override
    public long lastModified() {
        return 0L;
    }

    @Override
    public FilePath getParent() {
        int n = this.name.lastIndexOf(47);
        return n < 0 ? null : this.getPath(this.name.substring(0, n));
    }

    @Override
    public boolean isAbsolute() {
        String string = FilePathZip.translateFileName(this.name);
        return FilePath.get(string).isAbsolute();
    }

    @Override
    public FilePath unwrap() {
        return FilePath.get(this.name.substring(this.getScheme().length() + 1));
    }

    @Override
    public boolean isDirectory() {
        return this.isRegularOrDirectory(true);
    }

    @Override
    public boolean isRegularFile() {
        return this.isRegularOrDirectory(false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean isRegularOrDirectory(boolean bl) {
        try {
            String string = this.getEntryName();
            if (string.isEmpty()) {
                return bl;
            }
            try (ZipFile zipFile = this.openZipFile();){
                String string2;
                Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
                do {
                    if (!enumeration.hasMoreElements()) return false;
                    ZipEntry zipEntry = enumeration.nextElement();
                    string2 = zipEntry.getName();
                    if (!string2.equals(string)) continue;
                    boolean bl2 = zipEntry.isDirectory() == bl;
                    return bl2;
                } while (!string2.startsWith(string) || string2.length() != string.length() + 1 || !string2.equals(string + "/"));
                boolean bl3 = bl;
                return bl3;
            }
        }
        catch (IOException iOException) {
            return false;
        }
    }

    @Override
    public boolean canWrite() {
        return false;
    }

    @Override
    public boolean setReadOnly() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public long size() {
        try (ZipFile zipFile = this.openZipFile();){
            ZipEntry zipEntry = zipFile.getEntry(this.getEntryName());
            long l = zipEntry == null ? 0L : zipEntry.getSize();
            return l;
        }
        catch (IOException iOException) {
            return 0L;
        }
    }

    public ArrayList<FilePath> newDirectoryStream() {
        String string = this.name;
        ArrayList<FilePath> arrayList = new ArrayList<FilePath>();
        try {
            if (string.indexOf(33) < 0) {
                string = string + "!";
            }
            if (!string.endsWith("/")) {
                string = string + "/";
            }
            try (ZipFile zipFile = this.openZipFile();){
                String string2 = this.getEntryName();
                String string3 = string.substring(0, string.length() - string2.length());
                Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
                while (enumeration.hasMoreElements()) {
                    int n;
                    ZipEntry zipEntry = enumeration.nextElement();
                    String string4 = zipEntry.getName();
                    if (!string4.startsWith(string2) || string4.length() <= string2.length() || (n = string4.indexOf(47, string2.length())) >= 0 && n < string4.length() - 1) continue;
                    arrayList.add(this.getPath(string3 + string4));
                }
            }
            return arrayList;
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, "listFiles " + string);
        }
    }

    @Override
    public FileChannel open(String string) throws IOException {
        ZipFile zipFile = this.openZipFile();
        ZipEntry zipEntry = zipFile.getEntry(this.getEntryName());
        if (zipEntry == null) {
            zipFile.close();
            throw new FileNotFoundException(this.name);
        }
        return new FileZip(zipFile, zipEntry);
    }

    @Override
    public OutputStream newOutputStream(boolean bl) throws IOException {
        throw new IOException("write");
    }

    @Override
    public void moveTo(FilePath filePath, boolean bl) {
        throw DbException.getUnsupportedException("write");
    }

    private static String translateFileName(String string) {
        int n;
        if (string.startsWith("zip:")) {
            string = string.substring("zip:".length());
        }
        if ((n = string.indexOf(33)) >= 0) {
            string = string.substring(0, n);
        }
        return FilePathDisk.expandUserHomeDirectory(string);
    }

    @Override
    public FilePath toRealPath() {
        return this;
    }

    private String getEntryName() {
        int n = this.name.indexOf(33);
        String string = n <= 0 ? "" : this.name.substring(n + 1);
        if ((string = string.replace('\\', '/')).startsWith("/")) {
            string = string.substring(1);
        }
        return string;
    }

    private ZipFile openZipFile() throws IOException {
        String string = FilePathZip.translateFileName(this.name);
        return new ZipFile(string);
    }

    @Override
    public FilePath createTempFile(String string, boolean bl) throws IOException {
        if (!bl) {
            throw new IOException("File system is read-only");
        }
        return new FilePathDisk().getPath(this.name).createTempFile(string, true);
    }

    @Override
    public String getScheme() {
        return "zip";
    }
}

