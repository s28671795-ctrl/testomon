/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams;

import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamArchive;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import javax.validation.constraints.NotNull;

public class StreamArchiveImpl
implements StreamArchive {
    private final Map<Integer, Stream> archivedStreams;

    public StreamArchiveImpl(@NotNull Map<Integer, Stream> archivedStreams) {
        this.archivedStreams = Objects.requireNonNull(archivedStreams);
    }

    @Override
    public Collection<Stream> getAll(Predicate<Stream> filter2) {
        return this.archivedStreams.values().stream().filter(it -> filter2 == null || filter2.test((Stream)it)).toList();
    }

    @Override
    public Stream get(Integer id) {
        return this.archivedStreams.values().stream().filter(it -> it.getId() == id.intValue()).findFirst().orElse(null);
    }

    @Override
    public Collection<? extends Stream> getAll() {
        return this.getAll(null);
    }

    @Override
    public Stream add(Stream stream) {
        throw new UnsupportedOperationException("Cannot explicitly add analytics to archive");
    }

    @Override
    public Stream update(Stream stream) {
        throw new UnsupportedOperationException("Cannot update analytics in archive");
    }

    @Override
    public Stream delete(Integer l) {
        throw new UnsupportedOperationException("Cannot explicitly remove analytics from archive");
    }

    @Override
    public Stream delete(Stream stream) {
        throw new UnsupportedOperationException("Cannot explicitly remove analytics from archive");
    }
}

