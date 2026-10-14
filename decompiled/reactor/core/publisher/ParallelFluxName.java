/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.ParallelFlux;
import reactor.util.annotation.Nullable;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

final class ParallelFluxName<T>
extends ParallelFlux<T>
implements Scannable {
    final ParallelFlux<T> source;
    final String name;
    final List<Tuple2<String, String>> tagsWithDuplicates;

    static <T> ParallelFlux<T> createOrAppend(ParallelFlux<T> source2, String name) {
        Objects.requireNonNull(name, "name");
        if (source2 instanceof ParallelFluxName) {
            ParallelFluxName s2 = (ParallelFluxName)source2;
            return new ParallelFluxName<T>(s2.source, name, s2.tagsWithDuplicates);
        }
        return new ParallelFluxName<T>(source2, name, null);
    }

    static <T> ParallelFlux<T> createOrAppend(ParallelFlux<T> source2, String tagName, String tagValue) {
        Objects.requireNonNull(tagName, "tagName");
        Objects.requireNonNull(tagValue, "tagValue");
        Tuple2<String, String> newTag = Tuples.of(tagName, tagValue);
        if (source2 instanceof ParallelFluxName) {
            List<Tuple2<String, String>> tags;
            ParallelFluxName s2 = (ParallelFluxName)source2;
            if (s2.tagsWithDuplicates != null) {
                tags = new LinkedList<Tuple2<String, String>>(s2.tagsWithDuplicates);
                tags.add(newTag);
            } else {
                tags = Collections.singletonList(newTag);
            }
            return new ParallelFluxName<T>(s2.source, s2.name, tags);
        }
        return new ParallelFluxName<T>(source2, null, Collections.singletonList(newTag));
    }

    ParallelFluxName(ParallelFlux<T> source2, @Nullable String name, @Nullable List<Tuple2<String, String>> tags) {
        this.source = source2;
        this.name = name;
        this.tagsWithDuplicates = tags;
    }

    @Override
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override
    public int parallelism() {
        return this.source.parallelism();
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.NAME) {
            return this.name;
        }
        if (key == Scannable.Attr.TAGS && this.tagsWithDuplicates != null) {
            return this.tagsWithDuplicates.stream();
        }
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.PREFETCH) {
            return this.getPrefetch();
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T>[] subscribers) {
        this.source.subscribe(subscribers);
    }
}

