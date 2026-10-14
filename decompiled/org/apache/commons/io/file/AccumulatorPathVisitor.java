/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.file;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.apache.commons.io.file.Counters;
import org.apache.commons.io.file.CountingPathVisitor;
import org.apache.commons.io.file.PathUtils;

public class AccumulatorPathVisitor
extends CountingPathVisitor {
    private final List<Path> dirList = new ArrayList<Path>();
    private final List<Path> fileList = new ArrayList<Path>();

    public static AccumulatorPathVisitor withBigIntegerCounters() {
        return new AccumulatorPathVisitor(Counters.bigIntegerPathCounters());
    }

    public static AccumulatorPathVisitor withLongCounters() {
        return new AccumulatorPathVisitor(Counters.longPathCounters());
    }

    public AccumulatorPathVisitor(Counters.PathCounters pathCounter) {
        super(pathCounter);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof AccumulatorPathVisitor)) {
            return false;
        }
        AccumulatorPathVisitor other = (AccumulatorPathVisitor)obj;
        return Objects.equals(this.dirList, other.dirList) && Objects.equals(this.fileList, other.fileList);
    }

    public List<Path> getDirList() {
        return this.dirList;
    }

    public List<Path> getFileList() {
        return this.fileList;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = super.hashCode();
        result = 31 * result + Objects.hash(this.dirList, this.fileList);
        return result;
    }

    public List<Path> relativizeDirectories(Path parent, boolean sort, Comparator<? super Path> comparator) {
        return PathUtils.relativize(this.getDirList(), parent, sort, comparator);
    }

    public List<Path> relativizeFiles(Path parent, boolean sort, Comparator<? super Path> comparator) {
        return PathUtils.relativize(this.getFileList(), parent, sort, comparator);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
        (Files.isDirectory(file, new LinkOption[0]) ? this.dirList : this.fileList).add(file.normalize());
        return super.visitFile(file, attributes);
    }
}

