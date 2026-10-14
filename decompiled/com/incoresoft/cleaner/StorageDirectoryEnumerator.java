/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.cleaner;

import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StorageDirectoryEnumerator {
    private static final Logger log = LoggerFactory.getLogger(StorageDirectoryEnumerator.class);
    private static final String folderRegex = "(.*)storage[\\\\/](.*)[\\\\/](\\d+)[\\\\/](20\\d{2})[\\\\/](\\d{1,2})[\\\\/](\\d{1,2})$";
    private static final Pattern folderPattern = Pattern.compile("(.*)storage[\\\\/](.*)[\\\\/](\\d+)[\\\\/](20\\d{2})[\\\\/](\\d{1,2})[\\\\/](\\d{1,2})$");
    public static final int SCANNING_DEPTH = 5;

    public Collection<Path> enumerateSubDirsEligibleForRemoval(String dir, Duration duration) {
        List<Path> list;
        block8: {
            LocalDate earliestAllowedDate = LocalDateTime.now().minus(duration).toLocalDate();
            AtomicInteger eligibleDirectoriesCount = new AtomicInteger();
            Stream<Path> walk = Files.walk(Path.of(dir, new String[0]), 5, new FileVisitOption[0]);
            try {
                list = walk.filter(x$0 -> Files.isDirectory(x$0, new LinkOption[0])).filter(item -> {
                    Matcher matcher = folderPattern.matcher(item.toString());
                    if (!matcher.find()) {
                        return false;
                    }
                    int year = Integer.parseInt(matcher.group(4));
                    int month = Integer.parseInt(matcher.group(5));
                    int day = Integer.parseInt(matcher.group(6));
                    LocalDate folderDate = LocalDate.of(year, month, day);
                    return folderDate.isBefore(earliestAllowedDate);
                }).peek(item -> log.info("#{} Directory {} is eligible for removal.", (Object)eligibleDirectoriesCount.incrementAndGet(), item)).toList();
                if (walk == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (walk != null) {
                        try {
                            walk.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            walk.close();
        }
        return list;
    }
}

