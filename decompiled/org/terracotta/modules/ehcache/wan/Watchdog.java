/*
 * Decompiled with CFR 0.152.
 */
package org.terracotta.modules.ehcache.wan;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terracotta.modules.ehcache.wan.Watchable;

public class Watchdog {
    private static final Logger LOGGER = LoggerFactory.getLogger(Watchdog.class);
    private static final long WATCHDOG_INTERVAL = 5000L;
    private static final ThreadFactory DEFAULT_THREAD_FACTORY = new ThreadFactory(){

        @Override
        public Thread newThread(Runnable runnable2) {
            Thread thread2 = new Thread(runnable2);
            thread2.setName("wan-watchdog");
            thread2.setDaemon(true);
            return thread2;
        }
    };
    private final Set<Watchable> registry = new CopyOnWriteArraySet<Watchable>();
    private final ScheduledExecutorService scheduler;

    public static Watchdog create() {
        return Watchdog.create(Executors.newSingleThreadScheduledExecutor(DEFAULT_THREAD_FACTORY));
    }

    static Watchdog create(ScheduledExecutorService scheduler) {
        Watchdog dog = new Watchdog(scheduler);
        dog.init();
        return dog;
    }

    private Watchdog(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
    }

    public void watch(Watchable watchable) {
        this.registry.add(watchable);
        LOGGER.debug("Watchable cache '{}' registered", (Object)watchable.name());
    }

    public void unwatch(Watchable watchable) {
        this.registry.remove(watchable);
        LOGGER.debug("Watchable cache '{}' unregistered", (Object)watchable.name());
    }

    public void init() {
        this.scheduler.scheduleWithFixedDelay(new Runnable(){

            @Override
            public void run() {
                for (Watchable watchable : Watchdog.this.registry) {
                    watchable.probeLiveness();
                }
            }
        }, 0L, 5000L, TimeUnit.MILLISECONDS);
        LOGGER.debug("WAN watchdog started");
    }
}

