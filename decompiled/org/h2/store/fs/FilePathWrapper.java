/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.List;
import org.h2.store.fs.FilePath;

public abstract class FilePathWrapper
extends FilePath {
    private FilePath base;

    @Override
    public FilePathWrapper getPath(String string) {
        return this.create(string, this.unwrap(string));
    }

    public FilePathWrapper wrap(FilePath filePath) {
        return filePath == null ? null : this.create(this.getPrefix() + filePath.name, filePath);
    }

    @Override
    public FilePath unwrap() {
        return this.unwrap(this.name);
    }

    private FilePathWrapper create(String string, FilePath filePath) {
        try {
            FilePathWrapper filePathWrapper = (FilePathWrapper)this.getClass().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            filePathWrapper.name = string;
            filePathWrapper.base = filePath;
            return filePathWrapper;
        }
        catch (Exception exception) {
            throw new IllegalArgumentException("Path: " + string, exception);
        }
    }

    protected String getPrefix() {
        return this.getScheme() + ":";
    }

    protected FilePath unwrap(String string) {
        return FilePath.get(string.substring(this.getScheme().length() + 1));
    }

    protected FilePath getBase() {
        return this.base;
    }

    @Override
    public boolean canWrite() {
        return this.base.canWrite();
    }

    @Override
    public void createDirectory() {
        this.base.createDirectory();
    }

    @Override
    public boolean createFile() {
        return this.base.createFile();
    }

    @Override
    public void delete() {
        this.base.delete();
    }

    @Override
    public boolean exists() {
        return this.base.exists();
    }

    @Override
    public FilePath getParent() {
        return this.wrap(this.base.getParent());
    }

    @Override
    public boolean isAbsolute() {
        return this.base.isAbsolute();
    }

    @Override
    public boolean isDirectory() {
        return this.base.isDirectory();
    }

    @Override
    public boolean isRegularFile() {
        return this.base.isRegularFile();
    }

    @Override
    public long lastModified() {
        return this.base.lastModified();
    }

    @Override
    public FilePath toRealPath() {
        return this.wrap(this.base.toRealPath());
    }

    @Override
    public List<FilePath> newDirectoryStream() {
        List<FilePath> list = this.base.newDirectoryStream();
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            list.set(i, this.wrap(list.get(i)));
        }
        return list;
    }

    @Override
    public void moveTo(FilePath filePath, boolean bl) {
        this.base.moveTo(((FilePathWrapper)filePath).base, bl);
    }

    @Override
    public InputStream newInputStream() throws IOException {
        return this.base.newInputStream();
    }

    @Override
    public OutputStream newOutputStream(boolean bl) throws IOException {
        return this.base.newOutputStream(bl);
    }

    @Override
    public FileChannel open(String string) throws IOException {
        return this.base.open(string);
    }

    @Override
    public boolean setReadOnly() {
        return this.base.setReadOnly();
    }

    @Override
    public long size() {
        return this.base.size();
    }

    @Override
    public FilePath createTempFile(String string, boolean bl) throws IOException {
        return this.wrap(this.base.createTempFile(string, bl));
    }
}

