/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.frames;

import com.google.inject.Inject;
import com.incoresoft.middleware.cleaner.Cleaner;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.vezha.database.StreamsRepository;
import com.incoresoft.vezha.utils.PasswordGenerator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FramesManager {
    private static final Logger log = LoggerFactory.getLogger(FramesManager.class);
    private static final Pattern FRAME_FILE_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{10}(_thumbnail)?\\.jpg$");
    private final StreamsRepository streamsRepository;
    private final Path framesDirectoryPath;

    @Inject
    public FramesManager(StreamsRepository streamsRepository) {
        this(streamsRepository, Path.of("images", new String[0]));
    }

    public static String generateOriginalImageFileName() {
        return PasswordGenerator.generateAlphaNumericPassword(10) + ".jpg";
    }

    public Path generateOriginalImageFilePath() {
        return this.framesDirectoryPath.resolve(FramesManager.generateOriginalImageFileName());
    }

    public Path getOriginalImagePath(String originalImageFileName) {
        return this.framesDirectoryPath.resolve(originalImageFileName);
    }

    public Path getThumbnailImagePathByOriginalImage(String originalImageFileName) {
        String thumbnailImageFileName = FilenameUtils.getBaseName(originalImageFileName) + "_thumbnail.jpg";
        return this.framesDirectoryPath.resolve(thumbnailImageFileName);
    }

    @Inject
    public void init(Cleaner cleaner) {
        cleaner.addCustomTask(this::cleanupOldFrames);
    }

    void cleanupOldFrames() {
        log.info("Starting cleanup of old frames at {}", (Object)LocalDateTime.now());
        try {
            Set<Path> currentFramePaths = this.getCurrentFramePaths();
            this.deleteOldFrames(currentFramePaths);
        }
        catch (Exception e) {
            log.error("Error during frames cleanup", e);
        }
    }

    private Set<Path> getCurrentFramePaths() {
        List<Stream> streams = this.streamsRepository.getAll(false);
        HashSet<Path> actualImages = new HashSet<Path>();
        for (Stream stream : streams) {
            String fileName = stream.getImage();
            if (!StringUtils.isNotEmpty(fileName)) continue;
            Path originalImagePath = this.getOriginalImagePath(fileName);
            Path thumbnailImagePath = this.getThumbnailImagePathByOriginalImage(fileName);
            actualImages.add(originalImagePath);
            actualImages.add(thumbnailImagePath);
        }
        return actualImages;
    }

    private void deleteOldFrames(Set<Path> currentFramePaths) {
        if (!Files.exists(this.framesDirectoryPath, new LinkOption[0])) {
            log.warn("Frames directory does not exist: {}", (Object)this.framesDirectoryPath);
            return;
        }
        try (java.util.stream.Stream<Path> paths = Files.list(this.framesDirectoryPath);){
            Set filesToDelete = paths.filter(it -> FRAME_FILE_NAME_PATTERN.matcher(it.getFileName().toString()).matches()).filter(x$0 -> Files.isRegularFile(x$0, new LinkOption[0])).filter(path -> !currentFramePaths.contains(path)).collect(Collectors.toSet());
            int deletedCount = 0;
            for (Path path2 : filesToDelete) {
                try {
                    Files.delete(path2);
                    ++deletedCount;
                }
                catch (IOException e) {
                    log.error("Failed to delete frame: {}", (Object)path2.getFileName(), (Object)e);
                }
            }
            log.info("Deleted {} unused frame files", (Object)deletedCount);
        }
        catch (IOException e) {
            log.error("Failed to list files in frames directory: {}", (Object)this.framesDirectoryPath, (Object)e);
        }
    }

    FramesManager(StreamsRepository streamsRepository, Path framesDirectoryPath) {
        this.streamsRepository = streamsRepository;
        this.framesDirectoryPath = framesDirectoryPath;
    }
}

