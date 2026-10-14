/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.file;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NotDirectoryException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.io.file.AccumulatorPathVisitor;
import org.apache.commons.io.file.CleaningPathVisitor;
import org.apache.commons.io.file.CopyDirectoryVisitor;
import org.apache.commons.io.file.Counters;
import org.apache.commons.io.file.CountingPathVisitor;
import org.apache.commons.io.file.DeletingPathVisitor;

public final class PathUtils {
    public static final FileVisitOption[] EMPTY_FILE_VISIT_OPTION_ARRAY = new FileVisitOption[0];
    public static final LinkOption[] EMPTY_LINK_OPTION_ARRAY = new LinkOption[0];
    public static final OpenOption[] EMPTY_OPEN_OPTION_ARRAY = new OpenOption[0];

    private static AccumulatorPathVisitor accumulate(Path directory, int maxDepth, LinkOption[] linkOptions, FileVisitOption[] fileVisitOptions) throws IOException {
        return PathUtils.visitFileTree(AccumulatorPathVisitor.withLongCounters(), directory, PathUtils.toFileVisitOptionSet(fileVisitOptions), maxDepth);
    }

    public static Counters.PathCounters cleanDirectory(Path directory) throws IOException {
        return PathUtils.visitFileTree(CleaningPathVisitor.withLongCounters(), directory).getPathCounters();
    }

    public static Counters.PathCounters copyDirectory(Path sourceDirectory, Path targetDirectory, CopyOption ... copyOptions) throws IOException {
        return PathUtils.visitFileTree(new CopyDirectoryVisitor(Counters.longPathCounters(), sourceDirectory, targetDirectory, copyOptions), sourceDirectory).getPathCounters();
    }

    public static Path copyFile(URL sourceFile, Path targetFile, CopyOption ... copyOptions) throws IOException {
        try (InputStream inputStream2 = sourceFile.openStream();){
            Files.copy(inputStream2, targetFile, copyOptions);
            Path path = targetFile;
            return path;
        }
    }

    public static Path copyFileToDirectory(Path sourceFile, Path targetDirectory, CopyOption ... copyOptions) throws IOException {
        return Files.copy(sourceFile, targetDirectory.resolve(sourceFile.getFileName()), copyOptions);
    }

    public static Path copyFileToDirectory(URL sourceFile, Path targetDirectory, CopyOption ... copyOptions) throws IOException {
        try (InputStream inputStream2 = sourceFile.openStream();){
            Files.copy(inputStream2, targetDirectory.resolve(sourceFile.getFile()), copyOptions);
            Path path = targetDirectory;
            return path;
        }
    }

    public static Counters.PathCounters countDirectory(Path directory) throws IOException {
        return PathUtils.visitFileTree(new CountingPathVisitor(Counters.longPathCounters()), directory).getPathCounters();
    }

    public static Counters.PathCounters delete(Path path) throws IOException {
        return Files.isDirectory(path, new LinkOption[0]) ? PathUtils.deleteDirectory(path) : PathUtils.deleteFile(path);
    }

    public static Counters.PathCounters deleteDirectory(Path directory) throws IOException {
        return PathUtils.visitFileTree(DeletingPathVisitor.withLongCounters(), directory).getPathCounters();
    }

    public static Counters.PathCounters deleteFile(Path file) throws IOException {
        long size;
        if (Files.isDirectory(file, new LinkOption[0])) {
            throw new NotDirectoryException(file.toString());
        }
        Counters.PathCounters pathCounts = Counters.longPathCounters();
        long l = size = Files.exists(file, new LinkOption[0]) ? Files.size(file) : 0L;
        if (Files.deleteIfExists(file)) {
            pathCounts.getFileCounter().increment();
            pathCounts.getByteCounter().add(size);
        }
        return pathCounts;
    }

    public static boolean directoryAndFileContentEquals(Path path1, Path path2) throws IOException {
        return PathUtils.directoryAndFileContentEquals(path1, path2, EMPTY_LINK_OPTION_ARRAY, EMPTY_OPEN_OPTION_ARRAY, EMPTY_FILE_VISIT_OPTION_ARRAY);
    }

    public static boolean directoryAndFileContentEquals(Path path1, Path path2, LinkOption[] linkOptions, OpenOption[] openOptions, FileVisitOption[] fileVisitOption) throws IOException {
        if (path1 == null && path2 == null) {
            return true;
        }
        if (path1 == null ^ path2 == null) {
            return false;
        }
        if (!Files.exists(path1, new LinkOption[0]) && !Files.exists(path2, new LinkOption[0])) {
            return true;
        }
        RelativeSortedPaths relativeSortedPaths = new RelativeSortedPaths(path1, path2, Integer.MAX_VALUE, linkOptions, fileVisitOption);
        if (!relativeSortedPaths.equals) {
            return false;
        }
        List<Path> fileList1 = relativeSortedPaths.relativeFileList1;
        List<Path> fileList2 = relativeSortedPaths.relativeFileList2;
        for (Path path : fileList1) {
            int binarySearch = Collections.binarySearch(fileList2, path);
            if (binarySearch > -1) {
                if (PathUtils.fileContentEquals(path1.resolve(path), path2.resolve(path), linkOptions, openOptions)) continue;
                return false;
            }
            throw new IllegalStateException(String.format("Unexpected mismatch.", new Object[0]));
        }
        return true;
    }

    public static boolean directoryContentEquals(Path path1, Path path2) throws IOException {
        return PathUtils.directoryContentEquals(path1, path2, Integer.MAX_VALUE, EMPTY_LINK_OPTION_ARRAY, EMPTY_FILE_VISIT_OPTION_ARRAY);
    }

    public static boolean directoryContentEquals(Path path1, Path path2, int maxDepth, LinkOption[] linkOptions, FileVisitOption[] fileVisitOptions) throws IOException {
        return new RelativeSortedPaths((Path)path1, (Path)path2, (int)maxDepth, (LinkOption[])linkOptions, (FileVisitOption[])fileVisitOptions).equals;
    }

    public static boolean fileContentEquals(Path path1, Path path2) throws IOException {
        return PathUtils.fileContentEquals(path1, path2, EMPTY_LINK_OPTION_ARRAY, EMPTY_OPEN_OPTION_ARRAY);
    }

    /*
     * Exception decompiling
     */
    public static boolean fileContentEquals(Path path1, Path path2, LinkOption[] linkOptions, OpenOption[] openOptions) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static boolean isEmpty(Path path) throws IOException {
        return Files.isDirectory(path, new LinkOption[0]) ? PathUtils.isEmptyDirectory(path) : PathUtils.isEmptyFile(path);
    }

    public static boolean isEmptyDirectory(Path directory) throws IOException {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory);){
            if (directoryStream.iterator().hasNext()) {
                boolean bl = false;
                return bl;
            }
        }
        return true;
    }

    public static boolean isEmptyFile(Path file) throws IOException {
        return Files.size(file) <= 0L;
    }

    static List<Path> relativize(Collection<Path> collection, Path parent, boolean sort, Comparator<? super Path> comparator) {
        Stream<Path> stream = collection.stream().map(e -> parent.relativize((Path)e));
        if (sort) {
            stream = comparator == null ? stream.sorted() : stream.sorted(comparator);
        }
        return stream.collect(Collectors.toList());
    }

    static Set<FileVisitOption> toFileVisitOptionSet(FileVisitOption ... fileVisitOptions) {
        return fileVisitOptions == null ? EnumSet.noneOf(FileVisitOption.class) : Arrays.stream(fileVisitOptions).collect(Collectors.toSet());
    }

    public static <T extends FileVisitor<? super Path>> T visitFileTree(T visitor, Path directory) throws IOException {
        Files.walkFileTree(directory, visitor);
        return visitor;
    }

    public static <T extends FileVisitor<? super Path>> T visitFileTree(T visitor, Path start2, Set<FileVisitOption> options, int maxDepth) throws IOException {
        Files.walkFileTree(start2, options, maxDepth, visitor);
        return visitor;
    }

    public static <T extends FileVisitor<? super Path>> T visitFileTree(T visitor, String first, String ... more) throws IOException {
        return PathUtils.visitFileTree(visitor, Paths.get(first, more));
    }

    public static <T extends FileVisitor<? super Path>> T visitFileTree(T visitor, URI uri) throws IOException {
        return PathUtils.visitFileTree(visitor, Paths.get(uri));
    }

    private PathUtils() {
    }

    private static class RelativeSortedPaths {
        final boolean equals;
        final List<Path> relativeDirList1;
        final List<Path> relativeDirList2;
        final List<Path> relativeFileList1;
        final List<Path> relativeFileList2;

        private RelativeSortedPaths(Path dir1, Path dir2, int maxDepth, LinkOption[] linkOptions, FileVisitOption[] fileVisitOptions) throws IOException {
            List<Path> tmpRelativeDirList1 = null;
            List<Path> tmpRelativeDirList2 = null;
            List<Path> tmpRelativeFileList1 = null;
            List<Path> tmpRelativeFileList2 = null;
            if (dir1 == null && dir2 == null) {
                this.equals = true;
            } else if (dir1 == null ^ dir2 == null) {
                this.equals = false;
            } else {
                boolean parentDirExists1 = Files.exists(dir1, linkOptions);
                boolean parentDirExists2 = Files.exists(dir2, linkOptions);
                if (!parentDirExists1 || !parentDirExists2) {
                    this.equals = !parentDirExists1 && !parentDirExists2;
                } else {
                    AccumulatorPathVisitor visitor1 = PathUtils.accumulate(dir1, maxDepth, linkOptions, fileVisitOptions);
                    AccumulatorPathVisitor visitor2 = PathUtils.accumulate(dir2, maxDepth, linkOptions, fileVisitOptions);
                    if (visitor1.getDirList().size() != visitor2.getDirList().size() || visitor1.getFileList().size() != visitor2.getFileList().size()) {
                        this.equals = false;
                    } else {
                        tmpRelativeDirList1 = visitor1.relativizeDirectories(dir1, true, null);
                        if (!tmpRelativeDirList1.equals(tmpRelativeDirList2 = visitor2.relativizeDirectories(dir2, true, null))) {
                            this.equals = false;
                        } else {
                            tmpRelativeFileList1 = visitor1.relativizeFiles(dir1, true, null);
                            tmpRelativeFileList2 = visitor2.relativizeFiles(dir2, true, null);
                            this.equals = tmpRelativeFileList1.equals(tmpRelativeFileList2);
                        }
                    }
                }
            }
            this.relativeDirList1 = tmpRelativeDirList1;
            this.relativeDirList2 = tmpRelativeDirList2;
            this.relativeFileList1 = tmpRelativeFileList1;
            this.relativeFileList2 = tmpRelativeFileList2;
        }
    }
}

