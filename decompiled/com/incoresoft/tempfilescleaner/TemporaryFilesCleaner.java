/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.tempfilescleaner;

import com.google.inject.Inject;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemporaryFilesCleaner {
    private static final Logger log = LoggerFactory.getLogger(TemporaryFilesCleaner.class);
    private static final Pattern MULTIPART_FILE_NAME_PATTERN = Pattern.compile("^(?i)multipart.*");
    private static final List<String> TEMP_DIRECTORIES = List.of(System.getProperty("java.io.tmpdir"));
    private static final int TEMP_FILE_RETENTION_MINUTES = 10;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(Thread.ofVirtual().factory());

    @Inject
    public void init() {
        this.executorService.scheduleAtFixedRate(this::clean, 10L, 10L, TimeUnit.MINUTES);
    }

    public void clean() {
        Instant now = Instant.now();
        for (String tempDirectory : TEMP_DIRECTORIES) {
            TemporaryFilesCleaner.cleanStrayMultipartCacheFiles(tempDirectory, now);
            TemporaryFilesCleaner.cleanConscryptJniTempFiles(tempDirectory);
        }
    }

    private static void cleanStrayMultipartCacheFiles(String tempDirectory, Instant now) {
        try (Stream<Path> stream = Files.walk(Paths.get(tempDirectory, new String[0]), new FileVisitOption[0]);){
            stream.forEach(file -> {
                try {
                    if (!Files.isRegularFile(file, new LinkOption[0])) {
                        return;
                    }
                    if (!MULTIPART_FILE_NAME_PATTERN.matcher(file.getFileName().toString()).matches()) {
                        return;
                    }
                    BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class, new LinkOption[0]);
                    Instant creationTime = attributes.creationTime().toInstant();
                    if (creationTime.plus(10L, TimeUnit.MINUTES.toChronoUnit()).isAfter(now)) {
                        return;
                    }
                    Files.deleteIfExists(file);
                }
                catch (Exception e) {
                    log.error("Error while deleting file {}", file, (Object)e);
                }
            });
        }
        catch (IOException e) {
            log.error("Error while walking through directory {}", (Object)tempDirectory, (Object)e);
        }
    }

    private static void cleanConscryptJniTempFiles(String tempDirectory) {
        HashSet filesToDelete = new HashSet();
        AtomicReference<Instant> newestFileCreationTime = new AtomicReference<Instant>(Instant.EPOCH);
        AtomicReference<Object> newestFile = new AtomicReference<Object>(null);
        try (Stream<Path> stream = Files.walk(Paths.get(tempDirectory, new String[0]), new FileVisitOption[0]);){
            stream.forEach(file -> {
                try {
                    if (!Files.isRegularFile(file, new LinkOption[0])) {
                        return;
                    }
                    String filename = file.getFileName().toString();
                    if (!filename.startsWith("conscrypt_") && !filename.endsWith(".dll")) {
                        return;
                    }
                    BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class, new LinkOption[0]);
                    Instant creationTime = attributes.creationTime().toInstant();
                    if (creationTime.isAfter((Instant)newestFileCreationTime.get())) {
                        newestFile.set(file);
                        newestFileCreationTime.set(creationTime);
                    }
                    filesToDelete.add(file);
                }
                catch (Exception e) {
                    log.error("Error while deleting file {}", file, (Object)e);
                }
            });
        }
        catch (IOException e) {
            log.error("Error while walking through directory {}", (Object)tempDirectory, (Object)e);
            return;
        }
        if (newestFile.get() != null) {
            filesToDelete.remove(newestFile.get());
            for (Path file2 : filesToDelete) {
                try {
                    Files.deleteIfExists(file2);
                }
                catch (Exception e) {
                    log.error("Error while deleting file {}", (Object)file2, (Object)e);
                }
            }
        }
    }
}

