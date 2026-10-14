/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.model;

import com.google.inject.Inject;
import com.incoresoft.media.grubbing.jni.FrameData;
import com.incoresoft.media.grubbing.jni.FrameGrabber;
import com.incoresoft.middleware.model.stream.Codec;
import com.incoresoft.middleware.model.stream.Resolution;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamType;
import com.incoresoft.middleware.storage.Storage;
import com.incoresoft.vezha.frames.FramesManager;
import com.incoresoft.vezha.models.streams.StreamImpl;
import com.incoresoft.vezha.models.streams.exceptions.StreamFrameGrabException;
import com.incoresoft.vezha.models.streams.exceptions.UnsupportedCodecException;
import com.incoresoft.vezha.models.streams.model.FrameGrabResultImpl;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrameGrabManager {
    private static final Logger log = LoggerFactory.getLogger(FrameGrabManager.class);
    private final FrameGrabber grabber;
    private final Storage storage;
    private final FramesManager framesManager;

    public FrameGrabResultImpl grabFrame(Stream stream) {
        FramePaths framePaths = this.generateFramePaths();
        return this.grabFrame(stream, framePaths);
    }

    private FrameGrabResultImpl grabFrame(Stream stream, FramePaths framePaths) {
        FrameData frameData = this.grabFrameDataOrThrow(stream, framePaths);
        Codec codec = this.getCodecOrThrow(frameData);
        Duration duration = StreamImpl.isFile(stream) ? Duration.ofMillis(frameData.duration) : null;
        BufferedImage originalImage = this.readImage(framePaths.original());
        return new FrameGrabResultImpl(new Resolution(originalImage.getWidth(), originalImage.getHeight()), codec, framePaths.original().getFileName().toString(), framePaths.thumbnail().getFileName().toString(), duration);
    }

    private void deleteFrameFiles(FramePaths paths) {
        try {
            Files.deleteIfExists(paths.original());
            Files.deleteIfExists(paths.thumbnail());
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to delete frame files", e);
        }
    }

    private BufferedImage readImage(Path path) {
        try {
            return ImageIO.read(path.toFile());
        }
        catch (IOException e) {
            throw new StreamFrameGrabException("Failed to read grabbed image", e);
        }
    }

    private Codec getCodecOrThrow(FrameData frameData) {
        Codec codec = Codec.getCodec(frameData.codec);
        if (codec == null) {
            throw new UnsupportedCodecException();
        }
        return codec;
    }

    private FrameData grabFrameDataOrThrow(Stream stream, FramePaths framePaths) {
        String path = this.getPath(stream);
        FrameData frameData = this.grabber.process(path, framePaths.original().toString(), framePaths.thumbnail().toString(), StreamImpl.isFile(stream));
        if (StringUtils.isNotEmpty(frameData.error_text)) {
            log.error("Failed to grab frame from stream (id: {}, uri: {}): {}", stream.getId(), stream.getUri(), frameData.error_text);
            throw new StreamFrameGrabException(frameData.error_text);
        }
        return frameData;
    }

    private String getPath(Stream stream) {
        return stream.getType() == StreamType.file ? this.storage.get(stream.getUri().toString()).getPath() : stream.getUri().toString();
    }

    private FramePaths generateFramePaths() {
        String originalImageName = FramesManager.generateOriginalImageFileName();
        Path originalFramePath = this.framesManager.getOriginalImagePath(originalImageName);
        Path thumbnailFramePath = this.framesManager.getThumbnailImagePathByOriginalImage(originalImageName);
        return new FramePaths(originalFramePath, thumbnailFramePath);
    }

    @Inject
    public FrameGrabManager(FrameGrabber grabber, Storage storage, FramesManager framesManager) {
        this.grabber = grabber;
        this.storage = storage;
        this.framesManager = framesManager;
    }

    private record FramePaths(Path original, Path thumbnail) {
    }
}

