/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.utils;

import com.incoresoft.middleware.analytics.models.DeletedStream;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamsManager;
import java.util.Optional;

public class StreamUtils {
    public static Stream getStream(StreamsManager streamManager, int streamId) {
        Stream stream = Optional.ofNullable((Stream)streamManager.get(streamId)).orElse((Stream)streamManager.getStreamsArchive().get(streamId));
        return Optional.ofNullable(stream).orElse(new DeletedStream(streamId));
    }
}

