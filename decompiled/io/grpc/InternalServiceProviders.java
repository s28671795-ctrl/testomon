/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.annotations.VisibleForTesting;
import io.grpc.Internal;
import io.grpc.ServiceProviders;
import java.util.List;

@Internal
public final class InternalServiceProviders {
    private InternalServiceProviders() {
    }

    public static <T> T load(Class<T> klass, Iterable<Class<?>> hardcoded, ClassLoader classLoader, PriorityAccessor<T> priorityAccessor) {
        return ServiceProviders.load(klass, hardcoded, classLoader, priorityAccessor);
    }

    public static <T> List<T> loadAll(Class<T> klass, Iterable<Class<?>> hardCodedClasses, ClassLoader classLoader, PriorityAccessor<T> priorityAccessor) {
        return ServiceProviders.loadAll(klass, hardCodedClasses, classLoader, priorityAccessor);
    }

    @VisibleForTesting
    public static <T> Iterable<T> getCandidatesViaServiceLoader(Class<T> klass, ClassLoader cl) {
        return ServiceProviders.getCandidatesViaServiceLoader(klass, cl);
    }

    @VisibleForTesting
    public static <T> Iterable<T> getCandidatesViaHardCoded(Class<T> klass, Iterable<Class<?>> hardcoded) {
        return ServiceProviders.getCandidatesViaHardCoded(klass, hardcoded);
    }

    public static boolean isAndroid(ClassLoader cl) {
        return ServiceProviders.isAndroid(cl);
    }

    public static interface PriorityAccessor<T>
    extends ServiceProviders.PriorityAccessor<T> {
    }
}

