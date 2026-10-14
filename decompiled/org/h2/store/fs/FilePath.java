/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.h2.store.fs.disk.FilePathDisk;
import org.h2.util.MathUtils;

public abstract class FilePath {
    private static final FilePath defaultProvider;
    private static final ConcurrentHashMap<String, FilePath> providers;
    private static String tempRandom;
    private static long tempSequence;
    public String name;

    public static FilePath get(String string) {
        int n = (string = string.replace('\\', '/')).indexOf(58);
        if (n < 2) {
            return defaultProvider.getPath(string);
        }
        String string2 = string.substring(0, n);
        FilePath filePath = providers.get(string2);
        if (filePath == null) {
            filePath = defaultProvider;
        }
        return filePath.getPath(string);
    }

    public static void register(FilePath filePath) {
        providers.put(filePath.getScheme(), filePath);
    }

    public static void unregister(FilePath filePath) {
        providers.remove(filePath.getScheme());
    }

    public abstract long size();

    public abstract void moveTo(FilePath var1, boolean var2);

    public abstract boolean createFile();

    public abstract boolean exists();

    public abstract void delete();

    public abstract List<FilePath> newDirectoryStream();

    public abstract FilePath toRealPath();

    public abstract FilePath getParent();

    public abstract boolean isDirectory();

    public abstract boolean isRegularFile();

    public abstract boolean isAbsolute();

    public abstract long lastModified();

    public abstract boolean canWrite();

    public abstract void createDirectory();

    public String getName() {
        int n = Math.max(this.name.indexOf(58), this.name.lastIndexOf(47));
        return n < 0 ? this.name : this.name.substring(n + 1);
    }

    public OutputStream newOutputStream(boolean bl) throws IOException {
        return FilePath.newFileChannelOutputStream(this.open("rw"), bl);
    }

    public static OutputStream newFileChannelOutputStream(FileChannel fileChannel, boolean bl) throws IOException {
        if (bl) {
            fileChannel.position(fileChannel.size());
        } else {
            fileChannel.position(0L);
            fileChannel.truncate(0L);
        }
        return Channels.newOutputStream(fileChannel);
    }

    public abstract FileChannel open(String var1) throws IOException;

    public InputStream newInputStream() throws IOException {
        return Channels.newInputStream(this.open("r"));
    }

    public abstract boolean setReadOnly();

    public FilePath createTempFile(String string, boolean bl) throws IOException {
        FilePath filePath;
        while ((filePath = this.getPath(this.name + FilePath.getNextTempFileNamePart(false) + string)).exists() || !filePath.createFile()) {
            FilePath.getNextTempFileNamePart(true);
        }
        filePath.open("rw").close();
        return filePath;
    }

    private static synchronized String getNextTempFileNamePart(boolean bl) {
        if (bl || tempRandom == null) {
            tempRandom = MathUtils.randomInt(Integer.MAX_VALUE) + ".";
        }
        return tempRandom + tempSequence++;
    }

    public String toString() {
        return this.name;
    }

    public abstract String getScheme();

    public abstract FilePath getPath(String var1);

    public FilePath unwrap() {
        return this;
    }

    static {
        ConcurrentHashMap<String, FilePath> concurrentHashMap = new ConcurrentHashMap<String, FilePath>();
        FilePath filePath = new FilePathDisk();
        concurrentHashMap.put(filePath.getScheme(), filePath);
        concurrentHashMap.put("nio", filePath);
        defaultProvider = filePath;
        for (String string : new String[]{"org.h2.store.fs.mem.FilePathMem", "org.h2.store.fs.mem.FilePathMemLZF", "org.h2.store.fs.niomem.FilePathNioMem", "org.h2.store.fs.niomem.FilePathNioMemLZF", "org.h2.store.fs.split.FilePathSplit", "org.h2.store.fs.niomapped.FilePathNioMapped", "org.h2.store.fs.async.FilePathAsync", "org.h2.store.fs.zip.FilePathZip", "org.h2.store.fs.retry.FilePathRetryOnInterrupt"}) {
            try {
                filePath = (FilePath)Class.forName(string).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                concurrentHashMap.put(filePath.getScheme(), filePath);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        providers = concurrentHashMap;
    }
}

