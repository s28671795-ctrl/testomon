/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Preconditions;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.LogExceptionRunnable;
import java.util.IdentityHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class SharedResourceHolder {
    static final long DESTROY_DELAY_SECONDS = 1L;
    private static final SharedResourceHolder holder = new SharedResourceHolder(new ScheduledExecutorFactory(){

        @Override
        public ScheduledExecutorService createScheduledExecutor() {
            return Executors.newSingleThreadScheduledExecutor(GrpcUtil.getThreadFactory("grpc-shared-destroyer-%d", true));
        }
    });
    private final IdentityHashMap<Resource<?>, Instance> instances = new IdentityHashMap();
    private final ScheduledExecutorFactory destroyerFactory;
    private ScheduledExecutorService destroyer;

    SharedResourceHolder(ScheduledExecutorFactory destroyerFactory) {
        this.destroyerFactory = destroyerFactory;
    }

    public static <T> T get(Resource<T> resource) {
        return holder.getInternal(resource);
    }

    public static <T> T release(Resource<T> resource, T instance) {
        return holder.releaseInternal(resource, instance);
    }

    synchronized <T> T getInternal(Resource<T> resource) {
        Instance instance = this.instances.get(resource);
        if (instance == null) {
            instance = new Instance(resource.create());
            this.instances.put(resource, instance);
        }
        if (instance.destroyTask != null) {
            instance.destroyTask.cancel(false);
            instance.destroyTask = null;
        }
        ++instance.refcount;
        return (T)instance.payload;
    }

    synchronized <T> T releaseInternal(final Resource<T> resource, final T instance) {
        final Instance cached = this.instances.get(resource);
        if (cached == null) {
            throw new IllegalArgumentException("No cached instance found for " + resource);
        }
        Preconditions.checkArgument(instance == cached.payload, "Releasing the wrong instance");
        Preconditions.checkState(cached.refcount > 0, "Refcount has already reached zero");
        --cached.refcount;
        if (cached.refcount == 0) {
            Preconditions.checkState(cached.destroyTask == null, "Destroy task already scheduled");
            if (this.destroyer == null) {
                this.destroyer = this.destroyerFactory.createScheduledExecutor();
            }
            cached.destroyTask = this.destroyer.schedule(new LogExceptionRunnable(new Runnable(){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                @Override
                public void run() {
                    SharedResourceHolder sharedResourceHolder = SharedResourceHolder.this;
                    synchronized (sharedResourceHolder) {
                        if (cached.refcount == 0) {
                            try {
                                resource.close(instance);
                            }
                            finally {
                                SharedResourceHolder.this.instances.remove(resource);
                                if (SharedResourceHolder.this.instances.isEmpty()) {
                                    SharedResourceHolder.this.destroyer.shutdown();
                                    SharedResourceHolder.this.destroyer = null;
                                }
                            }
                        }
                    }
                }
            }), 1L, TimeUnit.SECONDS);
        }
        return null;
    }

    private static class Instance {
        final Object payload;
        int refcount;
        ScheduledFuture<?> destroyTask;

        Instance(Object payload) {
            this.payload = payload;
        }
    }

    static interface ScheduledExecutorFactory {
        public ScheduledExecutorService createScheduledExecutor();
    }

    public static interface Resource<T> {
        public T create();

        public void close(T var1);
    }
}

