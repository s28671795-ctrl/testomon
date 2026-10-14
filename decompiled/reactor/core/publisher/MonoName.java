/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoNameFuseable;
import reactor.util.annotation.Nullable;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

final class MonoName<T>
extends InternalMonoOperator<T, T> {
    final String name;
    final List<Tuple2<String, String>> tagsWithDuplicates;

    static <T> Mono<T> createOrAppend(Mono<T> source2, String name) {
        Objects.requireNonNull(name, "name");
        if (source2 instanceof MonoName) {
            MonoName s2 = (MonoName)source2;
            return new MonoName<T>(s2.source, name, s2.tagsWithDuplicates);
        }
        if (source2 instanceof MonoNameFuseable) {
            MonoNameFuseable s3 = (MonoNameFuseable)source2;
            return new MonoNameFuseable(s3.source, name, s3.tagsWithDuplicates);
        }
        if (source2 instanceof Fuseable) {
            return new MonoNameFuseable<T>(source2, name, null);
        }
        return new MonoName<T>(source2, name, null);
    }

    static <T> Mono<T> createOrAppend(Mono<T> source2, String tagName, String tagValue) {
        Objects.requireNonNull(tagName, "tagName");
        Objects.requireNonNull(tagValue, "tagValue");
        Tuple2<String, String> newTag = Tuples.of(tagName, tagValue);
        if (source2 instanceof MonoName) {
            List<Tuple2<String, String>> tags;
            MonoName s2 = (MonoName)source2;
            if (s2.tagsWithDuplicates != null) {
                tags = new LinkedList<Tuple2<String, String>>(s2.tagsWithDuplicates);
                tags.add(newTag);
            } else {
                tags = Collections.singletonList(newTag);
            }
            return new MonoName<T>(s2.source, s2.name, tags);
        }
        if (source2 instanceof MonoNameFuseable) {
            List<Tuple2<String, String>> tags;
            MonoNameFuseable s3 = (MonoNameFuseable)source2;
            if (s3.tagsWithDuplicates != null) {
                tags = new LinkedList<Tuple2<String, String>>(s3.tagsWithDuplicates);
                tags.add(newTag);
            } else {
                tags = Collections.singletonList(newTag);
            }
            return new MonoNameFuseable(s3.source, s3.name, tags);
        }
        if (source2 instanceof Fuseable) {
            return new MonoNameFuseable<T>(source2, null, Collections.singletonList(newTag));
        }
        return new MonoName<T>(source2, null, Collections.singletonList(newTag));
    }

    MonoName(Mono<? extends T> source2, @Nullable String name, @Nullable List<Tuple2<String, String>> tags) {
        super(source2);
        this.name = name;
        this.tagsWithDuplicates = tags;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return actual;
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
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}

