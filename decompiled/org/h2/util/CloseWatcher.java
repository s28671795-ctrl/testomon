/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CloseWatcher
extends PhantomReference<Object> {
    private static final ReferenceQueue<Object> queue = new ReferenceQueue();
    private static final Set<CloseWatcher> refs = Collections.synchronizedSet(new HashSet());
    private String openStackTrace;
    private AutoCloseable closeable;

    public CloseWatcher(Object object, ReferenceQueue<Object> referenceQueue, AutoCloseable autoCloseable) {
        super(object, referenceQueue);
        this.closeable = autoCloseable;
    }

    public static CloseWatcher pollUnclosed() {
        CloseWatcher closeWatcher;
        do {
            if ((closeWatcher = (CloseWatcher)queue.poll()) == null) {
                return null;
            }
            if (refs == null) continue;
            refs.remove(closeWatcher);
        } while (closeWatcher.closeable == null);
        return closeWatcher;
    }

    public static CloseWatcher register(Object object, AutoCloseable autoCloseable, boolean bl) {
        CloseWatcher closeWatcher = new CloseWatcher(object, queue, autoCloseable);
        if (bl) {
            Exception exception = new Exception("Open Stack Trace");
            StringWriter stringWriter = new StringWriter();
            exception.printStackTrace(new PrintWriter(stringWriter));
            closeWatcher.openStackTrace = stringWriter.toString();
        }
        refs.add(closeWatcher);
        return closeWatcher;
    }

    public static void unregister(CloseWatcher closeWatcher) {
        closeWatcher.closeable = null;
        refs.remove(closeWatcher);
    }

    public String getOpenStackTrace() {
        return this.openStackTrace;
    }

    public AutoCloseable getCloseable() {
        return this.closeable;
    }
}

