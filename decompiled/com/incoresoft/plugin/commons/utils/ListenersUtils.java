/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.utils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListenersUtils {
    private static final Logger log = LoggerFactory.getLogger(ListenersUtils.class);
    private static final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    public static <T> void triggerListeners(Consumer<T> action, Collection<T> listeners) {
        for (T listener : listeners) {
            try {
                action.accept(listener);
            }
            catch (Exception e) {
                log.error(ListenersUtils.getErrorMessage(listener), e);
            }
        }
    }

    public static <T> void triggerListenersInParallel(Consumer<T> action, Collection<T> listeners) throws InterruptedException {
        List<Callable> callables = listeners.stream().map(listener -> () -> {
            try {
                action.accept(listener);
            }
            catch (Exception e) {
                log.error(ListenersUtils.getErrorMessage(listener), e);
            }
            return null;
        }).toList();
        executorService.invokeAll(callables);
    }

    private static <T> String getErrorMessage(T failedListener) {
        return String.format("Error occurred during triggering listener %s", failedListener.getClass().getName());
    }
}

