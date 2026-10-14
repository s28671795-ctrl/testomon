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
import java.util.Arrays;
import java.util.Objects;
import org.apache.commons.io.file.Counters;
import org.apache.commons.io.file.CountingPathVisitor;

public class CleaningPathVisitor
extends CountingPathVisitor {
    private final String[] skip;

    public static CountingPathVisitor withBigIntegerCounters() {
        return new CleaningPathVisitor(Counters.bigIntegerPathCounters(), new String[0]);
    }

    public static CountingPathVisitor withLongCounters() {
        return new CleaningPathVisitor(Counters.longPathCounters(), new String[0]);
    }

    public CleaningPathVisitor(Counters.PathCounters pathCounter, String ... skip) {
        super(pathCounter);
        Object[] temp = skip != null ? (String[])skip.clone() : EMPTY_STRING_ARRAY;
        Arrays.sort(temp);
        this.skip = temp;
    }

    private boolean accept(Path path) {
        return Arrays.binarySearch(this.skip, Objects.toString(path.getFileName(), null)) < 0;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attributes) throws IOException {
        super.preVisitDirectory(dir, attributes);
        return this.accept(dir) ? FileVisitResult.CONTINUE : FileVisitResult.SKIP_SUBTREE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
        if (this.accept(file) && Files.exists(file, new LinkOption[0])) {
            Files.deleteIfExists(file);
        }
        this.updateFileCounters(file, attributes);
        return FileVisitResult.CONTINUE;
    }
}

