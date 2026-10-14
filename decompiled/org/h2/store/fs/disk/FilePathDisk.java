/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.disk;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.AccessDeniedException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;
import org.h2.engine.SysProperties;
import org.h2.message.DbException;
import org.h2.store.fs.FilePath;
import org.h2.store.fs.FileUtils;
import org.h2.util.IOUtils;

public class FilePathDisk
extends FilePath {
    private static final String CLASSPATH_PREFIX = "classpath:";

    @Override
    public FilePathDisk getPath(String string) {
        FilePathDisk filePathDisk = new FilePathDisk();
        filePathDisk.name = FilePathDisk.translateFileName(string);
        return filePathDisk;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public long size() {
        if (this.name.startsWith(CLASSPATH_PREFIX)) {
            URL uRL;
            String string = this.name.substring(CLASSPATH_PREFIX.length());
            if (!string.startsWith("/")) {
                string = "/" + string;
            }
            if ((uRL = this.getClass().getResource(string)) == null) {
                return 0L;
            }
            try {
                URI uRI = uRL.toURI();
                if ("file".equals(uRL.getProtocol())) {
                    return Files.size(Paths.get(uRI));
                }
                try {
                    FileSystems.getFileSystem(uRI);
                    return Files.size(Paths.get(uRI));
                }
                catch (FileSystemNotFoundException fileSystemNotFoundException) {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("create", "true");
                    try (FileSystem fileSystem = FileSystems.newFileSystem(uRI, hashMap);){
                        long l = Files.size(Paths.get(uRI));
                        return l;
                    }
                }
            }
            catch (Exception exception) {
                return 0L;
            }
        }
        try {
            return Files.size(Paths.get(this.name, new String[0]));
        }
        catch (IOException iOException) {
            return 0L;
        }
    }

    protected static String translateFileName(String string) {
        if ((string = string.replace('\\', '/')).startsWith("file:")) {
            string = string.substring(5);
        } else if (string.startsWith("nio:")) {
            string = string.substring(4);
        }
        return FilePathDisk.expandUserHomeDirectory(string);
    }

    public static String expandUserHomeDirectory(String string) {
        if (string.startsWith("~") && (string.length() == 1 || string.startsWith("~/"))) {
            String string2 = SysProperties.USER_HOME;
            string = string2 + string.substring(1);
        }
        return string;
    }

    @Override
    public void moveTo(FilePath filePath, boolean bl) {
        CopyOption[] copyOptionArray;
        Path path = Paths.get(this.name, new String[0]);
        Path path2 = Paths.get(filePath.name, new String[0]);
        if (!Files.exists(path, new LinkOption[0])) {
            throw DbException.get(90024, this.name + " (not found)", filePath.name);
        }
        if (bl) {
            try {
                Files.move(path, path2, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
                return;
            }
            catch (AtomicMoveNotSupportedException atomicMoveNotSupportedException) {
            }
            catch (IOException iOException) {
                throw DbException.get(90024, iOException, this.name, filePath.name);
            }
        }
        if (bl) {
            CopyOption[] copyOptionArray2 = new CopyOption[1];
            copyOptionArray = copyOptionArray2;
            copyOptionArray2[0] = StandardCopyOption.REPLACE_EXISTING;
        } else {
            copyOptionArray = new CopyOption[]{};
        }
        CopyOption[] copyOptionArray3 = copyOptionArray;
        try {
            Files.move(path, path2, copyOptionArray3);
        }
        catch (FileAlreadyExistsException fileAlreadyExistsException) {
            throw DbException.get(90024, this.name, filePath + " (exists)");
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            for (int i = 0; i < SysProperties.MAX_FILE_RETRY; ++i) {
                IOUtils.trace("rename", this.name + " >" + filePath, null);
                try {
                    Files.move(path, path2, copyOptionArray3);
                    return;
                }
                catch (FileAlreadyExistsException fileAlreadyExistsException) {
                    throw DbException.get(90024, this.name, filePath + " (exists)");
                }
                catch (IOException iOException3) {
                    iOException2 = iOException;
                    FilePathDisk.wait(i);
                    continue;
                }
            }
            throw DbException.get(90024, iOException2, this.name, filePath.name);
        }
    }

    private static void wait(int n) {
        if (n == 8) {
            System.gc();
        }
        try {
            long l = Math.min(256, n * n);
            Thread.sleep(l);
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    @Override
    public boolean createFile() {
        Path path = Paths.get(this.name, new String[0]);
        for (int i = 0; i < SysProperties.MAX_FILE_RETRY; ++i) {
            try {
                Files.createFile(path, new FileAttribute[0]);
                return true;
            }
            catch (FileAlreadyExistsException fileAlreadyExistsException) {
                return false;
            }
            catch (IOException iOException) {
                FilePathDisk.wait(i);
                continue;
            }
        }
        return false;
    }

    @Override
    public boolean exists() {
        return Files.exists(Paths.get(this.name, new String[0]), new LinkOption[0]);
    }

    @Override
    public void delete() {
        Path path = Paths.get(this.name, new String[0]);
        IOException iOException = null;
        for (int i = 0; i < SysProperties.MAX_FILE_RETRY; ++i) {
            IOUtils.trace("delete", this.name, null);
            try {
                Files.deleteIfExists(path);
                return;
            }
            catch (DirectoryNotEmptyException directoryNotEmptyException) {
                throw DbException.get(90025, directoryNotEmptyException, this.name);
            }
            catch (AccessDeniedException accessDeniedException) {
                try {
                    FileStore fileStore = Files.getFileStore(path);
                    if (!fileStore.supportsFileAttributeView(PosixFileAttributeView.class) && fileStore.supportsFileAttributeView(DosFileAttributeView.class)) {
                        Files.setAttribute(path, "dos:readonly", false, new LinkOption[0]);
                        Files.delete(path);
                    }
                }
                catch (IOException iOException2) {
                    iOException = iOException2;
                }
            }
            catch (IOException iOException3) {
                iOException = iOException3;
            }
            FilePathDisk.wait(i);
        }
        throw DbException.get(90025, iOException, this.name);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<FilePath> newDirectoryStream() {
        try (Stream<Path> stream = Files.list(FilePathDisk.toRealPath(Paths.get(this.name, new String[0])));){
            List list = stream.collect(ArrayList::new, (arrayList, path) -> arrayList.add(this.getPath(path.toString())), ArrayList::addAll);
            return list;
        }
        catch (NoSuchFileException noSuchFileException) {
            return Collections.emptyList();
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, this.name);
        }
    }

    @Override
    public boolean canWrite() {
        try {
            return Files.isWritable(Paths.get(this.name, new String[0]));
        }
        catch (Exception exception) {
            return false;
        }
    }

    @Override
    public boolean setReadOnly() {
        Path path = Paths.get(this.name, new String[0]);
        try {
            FileStore fileStore = Files.getFileStore(path);
            if (fileStore.supportsFileAttributeView(PosixFileAttributeView.class)) {
                HashSet<PosixFilePermission> hashSet = new HashSet<PosixFilePermission>();
                block5: for (PosixFilePermission posixFilePermission : Files.getPosixFilePermissions(path, new LinkOption[0])) {
                    switch (posixFilePermission) {
                        case OWNER_WRITE: 
                        case GROUP_WRITE: 
                        case OTHERS_WRITE: {
                            continue block5;
                        }
                    }
                    hashSet.add(posixFilePermission);
                }
                Files.setPosixFilePermissions(path, hashSet);
            } else if (fileStore.supportsFileAttributeView(DosFileAttributeView.class)) {
                Files.setAttribute(path, "dos:readonly", true, new LinkOption[0]);
            } else {
                return false;
            }
            return true;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    @Override
    public FilePathDisk toRealPath() {
        return this.getPath(FilePathDisk.toRealPath(Paths.get(this.name, new String[0])).toString());
    }

    private static Path toRealPath(Path path) {
        try {
            path = path.toRealPath(new LinkOption[0]);
        }
        catch (IOException iOException) {
            path = FilePathDisk.parentToRealPath(path.toAbsolutePath().normalize());
        }
        return path;
    }

    private static Path parentToRealPath(Path path) {
        Path path2 = path.getParent();
        if (path2 == null) {
            return path;
        }
        try {
            path2 = path2.toRealPath(new LinkOption[0]);
        }
        catch (IOException iOException) {
            path2 = FilePathDisk.parentToRealPath(path2);
        }
        return path2.resolve(path.getFileName());
    }

    @Override
    public FilePath getParent() {
        Path path = Paths.get(this.name, new String[0]).getParent();
        return path == null ? null : this.getPath(path.toString());
    }

    @Override
    public boolean isDirectory() {
        return Files.isDirectory(Paths.get(this.name, new String[0]), new LinkOption[0]);
    }

    @Override
    public boolean isRegularFile() {
        return Files.isRegularFile(Paths.get(this.name, new String[0]), new LinkOption[0]);
    }

    @Override
    public boolean isAbsolute() {
        return Paths.get(this.name, new String[0]).isAbsolute();
    }

    @Override
    public long lastModified() {
        try {
            return Files.getLastModifiedTime(Paths.get(this.name, new String[0]), new LinkOption[0]).toMillis();
        }
        catch (IOException iOException) {
            return 0L;
        }
    }

    @Override
    public void createDirectory() {
        Path path = Paths.get(this.name, new String[0]);
        try {
            Files.createDirectory(path, new FileAttribute[0]);
        }
        catch (FileAlreadyExistsException fileAlreadyExistsException) {
            throw DbException.get(90062, this.name + " (a file with this name already exists)");
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            for (int i = 0; i < SysProperties.MAX_FILE_RETRY; ++i) {
                if (Files.isDirectory(path, new LinkOption[0])) {
                    return;
                }
                try {
                    Files.createDirectory(path, new FileAttribute[0]);
                }
                catch (FileAlreadyExistsException fileAlreadyExistsException) {
                    throw DbException.get(90062, this.name + " (a file with this name already exists)");
                }
                catch (IOException iOException3) {
                    iOException2 = iOException3;
                }
                FilePathDisk.wait(i);
            }
            throw DbException.get(90062, iOException2, this.name);
        }
    }

    @Override
    public OutputStream newOutputStream(boolean bl) throws IOException {
        OpenOption[] openOptionArray;
        Path path = Paths.get(this.name, new String[0]);
        if (bl) {
            OpenOption[] openOptionArray2 = new OpenOption[2];
            openOptionArray2[0] = StandardOpenOption.CREATE;
            openOptionArray = openOptionArray2;
            openOptionArray2[1] = StandardOpenOption.APPEND;
        } else {
            openOptionArray = new OpenOption[]{};
        }
        OpenOption[] openOptionArray3 = openOptionArray;
        try {
            Path path2 = path.getParent();
            if (path2 != null) {
                Files.createDirectories(path2, new FileAttribute[0]);
            }
            OutputStream outputStream2 = Files.newOutputStream(path, openOptionArray3);
            IOUtils.trace("openFileOutputStream", this.name, outputStream2);
            return outputStream2;
        }
        catch (IOException iOException) {
            FilePathDisk.freeMemoryAndFinalize();
            return Files.newOutputStream(path, openOptionArray3);
        }
    }

    @Override
    public InputStream newInputStream() throws IOException {
        if (this.name.matches("[a-zA-Z]{2,19}:.*")) {
            if (this.name.startsWith(CLASSPATH_PREFIX)) {
                InputStream inputStream2;
                String string = this.name.substring(CLASSPATH_PREFIX.length());
                if (!string.startsWith("/")) {
                    string = "/" + string;
                }
                if ((inputStream2 = this.getClass().getResourceAsStream(string)) == null) {
                    inputStream2 = Thread.currentThread().getContextClassLoader().getResourceAsStream(string.substring(1));
                }
                if (inputStream2 == null) {
                    throw new FileNotFoundException("resource " + string);
                }
                return inputStream2;
            }
            URL uRL = new URL(this.name);
            return uRL.openStream();
        }
        InputStream inputStream3 = Files.newInputStream(Paths.get(this.name, new String[0]), new OpenOption[0]);
        IOUtils.trace("openFileInputStream", this.name, inputStream3);
        return inputStream3;
    }

    static void freeMemoryAndFinalize() {
        IOUtils.trace("freeMemoryAndFinalize", null, null);
        Runtime runtime = Runtime.getRuntime();
        long l = runtime.freeMemory();
        for (int i = 0; i < 16; ++i) {
            runtime.gc();
            long l2 = runtime.freeMemory();
            runtime.runFinalization();
            if (l2 == l) break;
            l = l2;
        }
    }

    @Override
    public FileChannel open(String string) throws IOException {
        FileChannel fileChannel = FileChannel.open(Paths.get(this.name, new String[0]), FileUtils.modeToOptions(string), FileUtils.NO_ATTRIBUTES);
        IOUtils.trace("open", this.name, fileChannel);
        return fileChannel;
    }

    @Override
    public String getScheme() {
        return "file";
    }

    @Override
    public FilePath createTempFile(String string, boolean bl) throws IOException {
        Path path = Paths.get(this.name + '.', new String[0]).toAbsolutePath();
        String string2 = path.getFileName().toString();
        if (bl) {
            Path path2 = Paths.get(System.getProperty("java.io.tmpdir", "."), new String[0]);
            if (!Files.isDirectory(path2, new LinkOption[0])) {
                Files.createDirectories(path2, new FileAttribute[0]);
            }
            path = Files.createTempFile(string2, string, new FileAttribute[0]);
        } else {
            Path path3 = path.getParent();
            Files.createDirectories(path3, new FileAttribute[0]);
            path = Files.createTempFile(path3, string2, string, new FileAttribute[0]);
        }
        return FilePathDisk.get(path.toString());
    }
}

