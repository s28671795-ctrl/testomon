/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.split;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import org.h2.engine.SysProperties;
import org.h2.message.DbException;
import org.h2.store.fs.FilePath;
import org.h2.store.fs.FilePathWrapper;
import org.h2.store.fs.split.FileSplit;

public class FilePathSplit
extends FilePathWrapper {
    private static final String PART_SUFFIX = ".part";

    @Override
    protected String getPrefix() {
        return this.getScheme() + ":" + this.parse(this.name)[0] + ":";
    }

    @Override
    public FilePath unwrap(String string) {
        return FilePath.get(this.parse(string)[1]);
    }

    @Override
    public boolean setReadOnly() {
        FilePath filePath;
        boolean bl = false;
        int n = 0;
        while ((filePath = this.getBase(n)).exists()) {
            bl = filePath.setReadOnly();
            ++n;
        }
        return bl;
    }

    @Override
    public void delete() {
        FilePath filePath;
        int n = 0;
        while ((filePath = this.getBase(n)).exists()) {
            filePath.delete();
            ++n;
        }
    }

    @Override
    public long lastModified() {
        FilePath filePath;
        long l = 0L;
        int n = 0;
        while ((filePath = this.getBase(n)).exists()) {
            long l2 = filePath.lastModified();
            l = Math.max(l, l2);
            ++n;
        }
        return l;
    }

    @Override
    public long size() {
        FilePath filePath;
        long l = 0L;
        int n = 0;
        while ((filePath = this.getBase(n)).exists()) {
            l += filePath.size();
            ++n;
        }
        return l;
    }

    public ArrayList<FilePath> newDirectoryStream() {
        List<FilePath> list = this.getBase().newDirectoryStream();
        ArrayList<FilePath> arrayList = new ArrayList<FilePath>();
        for (FilePath filePath : list) {
            if (filePath.getName().endsWith(PART_SUFFIX)) continue;
            arrayList.add(this.wrap(filePath));
        }
        return arrayList;
    }

    @Override
    public InputStream newInputStream() throws IOException {
        FilePath filePath;
        InputStream inputStream2 = this.getBase().newInputStream();
        int n = 1;
        while ((filePath = this.getBase(n)).exists()) {
            InputStream inputStream3 = filePath.newInputStream();
            inputStream2 = new SequenceInputStream(inputStream2, inputStream3);
            ++n;
        }
        return inputStream2;
    }

    @Override
    public FileChannel open(String string) throws IOException {
        long l;
        FilePath filePath;
        ArrayList<FileChannel> arrayList = new ArrayList<FileChannel>();
        arrayList.add(this.getBase().open(string));
        int n = 1;
        while ((filePath = this.getBase(n)).exists()) {
            arrayList.add(filePath.open(string));
            ++n;
        }
        FileChannel[] fileChannelArray = arrayList.toArray(new FileChannel[0]);
        long l2 = l = fileChannelArray[0].size();
        if (fileChannelArray.length == 1) {
            long l3 = this.getDefaultMaxLength();
            if (l < l3) {
                l = l3;
            }
        } else {
            if (l == 0L) {
                this.closeAndThrow(0, fileChannelArray, fileChannelArray[0], l);
            }
            for (int i = 1; i < fileChannelArray.length - 1; ++i) {
                FileChannel fileChannel = fileChannelArray[i];
                long l4 = fileChannel.size();
                l2 += l4;
                if (l4 == l) continue;
                this.closeAndThrow(i, fileChannelArray, fileChannel, l);
            }
            FileChannel fileChannel = fileChannelArray[fileChannelArray.length - 1];
            long l5 = fileChannel.size();
            l2 += l5;
            if (l5 > l) {
                this.closeAndThrow(fileChannelArray.length - 1, fileChannelArray, fileChannel, l);
            }
        }
        return new FileSplit(this, string, fileChannelArray, l2, l);
    }

    private long getDefaultMaxLength() {
        return 1L << Integer.decode(this.parse(this.name)[0]);
    }

    private void closeAndThrow(int n, FileChannel[] fileChannelArray, FileChannel fileChannel, long l) throws IOException {
        String string = "Expected file length: " + l + " got: " + fileChannel.size() + " for " + this.getName(n);
        for (FileChannel fileChannel2 : fileChannelArray) {
            fileChannel2.close();
        }
        throw new IOException(string);
    }

    @Override
    public OutputStream newOutputStream(boolean bl) throws IOException {
        return FilePathSplit.newFileChannelOutputStream(this.open("rw"), bl);
    }

    @Override
    public void moveTo(FilePath filePath, boolean bl) {
        FilePathSplit filePathSplit = (FilePathSplit)filePath;
        int n = 0;
        while (true) {
            FilePath filePath2;
            if ((filePath2 = this.getBase(n)).exists()) {
                filePath2.moveTo(filePathSplit.getBase(n), bl);
            } else {
                if (!filePathSplit.getBase(n).exists()) break;
                filePathSplit.getBase(n).delete();
            }
            ++n;
        }
    }

    private String[] parse(String string) {
        String string2;
        if (!string.startsWith(this.getScheme())) {
            throw DbException.getInternalError(string + " doesn't start with " + this.getScheme());
        }
        if ((string = string.substring(this.getScheme().length() + 1)).length() > 0 && Character.isDigit(string.charAt(0))) {
            int n = string.indexOf(58);
            string2 = string.substring(0, n);
            try {
                string = string.substring(n + 1);
            }
            catch (NumberFormatException numberFormatException) {}
        } else {
            string2 = Long.toString(SysProperties.SPLIT_FILE_SIZE_SHIFT);
        }
        return new String[]{string2, string};
    }

    FilePath getBase(int n) {
        return FilePath.get(this.getName(n));
    }

    private String getName(int n) {
        return n > 0 ? this.getBase().name + "." + n + PART_SUFFIX : this.getBase().name;
    }

    @Override
    public String getScheme() {
        return "split";
    }
}

