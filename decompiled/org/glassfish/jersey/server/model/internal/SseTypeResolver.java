/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model.internal;

import java.security.AccessController;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.sse.SseEventSink;
import org.glassfish.jersey.internal.jsr166.Flow;
import org.glassfish.jersey.internal.util.ReflectionHelper;

public final class SseTypeResolver {
    private static final Set<Class<?>> SUPPORTED_SSE_SINK_TYPES;

    private SseTypeResolver() {
    }

    public static boolean isSseSinkParam(Class<?> type) {
        return SUPPORTED_SSE_SINK_TYPES.contains(type);
    }

    static {
        HashSet set = new HashSet(8);
        set.add(Flow.Subscriber.class);
        set.add(SseEventSink.class);
        Class clazz = AccessController.doPrivileged(ReflectionHelper.classForNamePA("java.util.concurrent.Flow$Subscriber", null));
        if (clazz != null) {
            set.add(clazz);
        }
        SUPPORTED_SSE_SINK_TYPES = Collections.unmodifiableSet(set);
    }
}

