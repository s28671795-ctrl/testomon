/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.h2.store.fs.FilePath;

public class FileUtils {
    public static final Set<? extends OpenOption> R = Collections.singleton(StandardOpenOption.READ);
    public static final Set<? extends OpenOption> RW = Collections.unmodifiableSet(EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE));
    public static final Set<? extends OpenOption> RWS = Collections.unmodifiableSet(EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.SYNC));
    public static final Set<? extends OpenOption> RWD = Collections.unmodifiableSet(EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.DSYNC));
    public static final FileAttribute<?>[] NO_ATTRIBUTES = new FileAttribute[0];

    public static boolean exists(String string) {
        return FilePath.get(string).exists();
    }

    public static void createDirectory(String string) {
        FilePath.get(string).createDirectory();
    }

    public static boolean createFile(String string) {
        return FilePath.get(string).createFile();
    }

    public static void delete(String string) {
        FilePath.get(string).delete();
    }

    public static String toRealPath(String string) {
        return FilePath.get(string).toRealPath().toString();
    }

    public static String getParent(String string) {
        FilePath filePath = FilePath.get(string).getParent();
        return filePath == null ? null : filePath.toString();
    }

    public static boolean isAbsolute(String string) {
        return FilePath.get(string).isAbsolute() || string.startsWith(File.separator) || string.startsWith("/");
    }

    public static void move(String string, String string2) {
        FilePath.get(string).moveTo(FilePath.get(string2), false);
    }

    public static void moveAtomicReplace(String string, String string2) {
        FilePath.get(string).moveTo(FilePath.get(string2), true);
    }

    public static String getName(String string) {
        return FilePath.get(string).getName();
    }

    public static List<String> newDirectoryStream(String string) {
        List<FilePath> list = FilePath.get(string).newDirectoryStream();
        int n = list.size();
        ArrayList<String> arrayList = new ArrayList<String>(n);
        for (FilePath filePath : list) {
            arrayList.add(filePath.toString());
        }
        return arrayList;
    }

    public static long lastModified(String string) {
        return FilePath.get(string).lastModified();
    }

    public static long size(String string) {
        return FilePath.get(string).size();
    }

    public static boolean isDirectory(String string) {
        return FilePath.get(string).isDirectory();
    }

    public static boolean isRegularFile(String string) {
        return FilePath.get(string).isRegularFile();
    }

    public static FileChannel open(String string, String string2) throws IOException {
        return FilePath.get(string).open(string2);
    }

    public static InputStream newInputStream(String string) throws IOException {
        return FilePath.get(string).newInputStream();
    }

    public static BufferedReader newBufferedReader(String string, Charset charset) throws IOException {
        return new BufferedReader(new InputStreamReader(FileUtils.newInputStream(string), charset), 4096);
    }

    public static OutputStream newOutputStream(String string, boolean bl) throws IOException {
        return FilePath.get(string).newOutputStream(bl);
    }

    public static boolean canWrite(String string) {
        return FilePath.get(string).canWrite();
    }

    public static boolean setReadOnly(String string) {
        return FilePath.get(string).setReadOnly();
    }

    public static String unwrap(String string) {
        return FilePath.get(string).unwrap().toString();
    }

    public static void deleteRecursive(String string, boolean bl) {
        if (FileUtils.exists(string)) {
            if (FileUtils.isDirectory(string)) {
                for (String string2 : FileUtils.newDirectoryStream(string)) {
                    FileUtils.deleteRecursive(string2, bl);
                }
            }
            if (bl) {
                FileUtils.tryDelete(string);
            } else {
                FileUtils.delete(string);
            }
        }
    }

    public static void createDirectories(String string) {
        if (string != null) {
            if (FileUtils.exists(string)) {
                if (!FileUtils.isDirectory(string)) {
                    FileUtils.createDirectory(string);
                }
            } else {
                String string2 = FileUtils.getParent(string);
                FileUtils.createDirectories(string2);
                FileUtils.createDirectory(string);
            }
        }
    }

    public static boolean tryDelete(String string) {
        try {
            FilePath.get(string).delete();
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static String createTempFile(String string, String string2, boolean bl) throws IOException {
        return FilePath.get(string).createTempFile(string2, bl).toString();
    }

    public static void readFully(FileChannel fileChannel, ByteBuffer byteBuffer) throws IOException {
        do {
            int n;
            if ((n = fileChannel.read(byteBuffer)) >= 0) continue;
            throw new EOFException();
        } while (byteBuffer.remaining() > 0);
    }

    public static void writeFully(FileChannel fileChannel, ByteBuffer byteBuffer) throws IOException {
        do {
            fileChannel.write(byteBuffer);
        } while (byteBuffer.remaining() > 0);
    }

    public static Set<? extends OpenOption> modeToOptions(String string) {
        Set<? extends OpenOption> set;
        switch (string) {
            case "r": {
                set = R;
                break;
            }
            case "rw": {
                set = RW;
                break;
            }
            case "rws": {
                set = RWS;
                break;
            }
            case "rwd": {
                set = RWD;
                break;
            }
            default: {
                throw new IllegalArgumentException(string);
            }
        }
        return set;
    }
}

