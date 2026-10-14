/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.pf4j.util.DirectoryFileFilter;
import org.pf4j.util.JarFileFilter;
import org.pf4j.util.StringUtils;
import org.pf4j.util.Unzip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FileUtils {
    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static List<String> readLines(Path path, boolean ignoreComments) throws IOException {
        File file = path.toFile();
        if (!file.isFile()) {
            return new ArrayList<String>();
        }
        ArrayList<String> lines = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file));){
            String line;
            while ((line = reader.readLine()) != null) {
                if (!ignoreComments || line.startsWith("#") || lines.contains(line)) continue;
                lines.add(line);
            }
        }
        return lines;
    }

    @Deprecated
    public static void writeLines(Collection<String> lines, File file) throws IOException {
        FileUtils.writeLines(lines, file.toPath());
    }

    public static void writeLines(Collection<String> lines, Path path) throws IOException {
        Files.write(path, lines, StandardCharsets.UTF_8, new OpenOption[0]);
    }

    public static void delete(Path path) throws IOException {
        Files.walkFileTree(path, (FileVisitor<? super Path>)new SimpleFileVisitor<Path>(){

            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                if (!attrs.isSymbolicLink()) {
                    Files.delete(path);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static List<File> getJars(Path folder) {
        ArrayList<File> bucket = new ArrayList<File>();
        FileUtils.getJars(bucket, folder);
        return bucket;
    }

    private static void getJars(List<File> bucket, Path folder) {
        JarFileFilter jarFilter = new JarFileFilter();
        DirectoryFileFilter directoryFilter = new DirectoryFileFilter();
        if (Files.isDirectory(folder, new LinkOption[0])) {
            File[] jars = folder.toFile().listFiles(jarFilter);
            for (int i = 0; jars != null && i < jars.length; ++i) {
                bucket.add(jars[i]);
            }
            File[] directories = folder.toFile().listFiles(directoryFilter);
            for (int i = 0; directories != null && i < directories.length; ++i) {
                File directory = directories[i];
                FileUtils.getJars(bucket, directory.toPath());
            }
        }
    }

    public static Path findWithEnding(Path basePath, String ... endings) {
        for (String ending : endings) {
            Path newPath = basePath.resolveSibling(basePath.getFileName() + ending);
            if (!Files.exists(newPath, new LinkOption[0])) continue;
            return newPath;
        }
        return null;
    }

    public static void optimisticDelete(Path path) {
        if (path == null) {
            return;
        }
        try {
            Files.delete(path);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public static Path expandIfZip(Path filePath) throws IOException {
        if (!FileUtils.isZipFile(filePath)) {
            return filePath;
        }
        FileTime pluginZipDate = Files.getLastModifiedTime(filePath, new LinkOption[0]);
        String fileName = filePath.getFileName().toString();
        String directoryName = fileName.substring(0, fileName.lastIndexOf("."));
        Path pluginDirectory = filePath.resolveSibling(directoryName);
        if (!Files.exists(pluginDirectory, new LinkOption[0]) || pluginZipDate.compareTo(Files.getLastModifiedTime(pluginDirectory, new LinkOption[0])) > 0) {
            Unzip unzip = new Unzip();
            unzip.setSource(filePath.toFile());
            unzip.setDestination(pluginDirectory.toFile());
            unzip.extract();
            log.info("Expanded plugin zip '{}' in '{}'", (Object)filePath.getFileName(), (Object)pluginDirectory.getFileName());
        }
        return pluginDirectory;
    }

    public static boolean isZipFile(Path path) {
        return Files.isRegularFile(path, new LinkOption[0]) && path.toString().toLowerCase().endsWith(".zip");
    }

    public static boolean isJarFile(Path path) {
        return Files.isRegularFile(path, new LinkOption[0]) && path.toString().toLowerCase().endsWith(".jar");
    }

    public static boolean isZipOrJarFile(Path path) {
        return FileUtils.isZipFile(path) || FileUtils.isJarFile(path);
    }

    public static Path getPath(Path path, String first, String ... more) throws IOException {
        URI uri = path.toUri();
        if (FileUtils.isZipOrJarFile(path)) {
            String pathString = path.toAbsolutePath().toString();
            pathString = StringUtils.addStart(pathString.replace("\\", "/"), "/");
            pathString = pathString.replace(" ", "%20");
            uri = URI.create("jar:file:" + pathString);
        }
        return FileUtils.getPath(uri, first, more);
    }

    public static Path getPath(URI uri, String first, String ... more) throws IOException {
        return FileUtils.getFileSystem(uri).getPath(first, more);
    }

    public static void closePath(Path path) {
        if (path != null) {
            try {
                path.getFileSystem().close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public static Path findFile(Path directoryPath, String fileName) {
        File[] files = directoryPath.toFile().listFiles();
        if (files != null) {
            for (File file : files) {
                Path foundFile;
                if (file.isFile()) {
                    if (!file.getName().equals(fileName)) continue;
                    return file.toPath();
                }
                if (!file.isDirectory() || (foundFile = FileUtils.findFile(file.toPath(), fileName)) == null) continue;
                return foundFile;
            }
        }
        return null;
    }

    private static FileSystem getFileSystem(URI uri) throws IOException {
        try {
            return FileSystems.getFileSystem(uri);
        }
        catch (FileSystemNotFoundException e) {
            return FileSystems.newFileSystem(uri, Collections.emptyMap());
        }
    }

    private FileUtils() {
    }
}

