/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.HashMap;
import org.h2.engine.SysProperties;
import org.h2.message.DbException;
import org.h2.store.fs.FileUtils;
import org.h2.util.IOUtils;

public class TempFileDeleter {
    private final ReferenceQueue<Object> queue = new ReferenceQueue();
    private final HashMap<PhantomReference<?>, Object> refMap = new HashMap();

    private TempFileDeleter() {
    }

    public static TempFileDeleter getInstance() {
        return new TempFileDeleter();
    }

    public synchronized Reference<?> addFile(Object object, Object object2) {
        if (!(object instanceof String) && !(object instanceof AutoCloseable)) {
            throw DbException.getUnsupportedException("Unsupported resource " + object);
        }
        IOUtils.trace("TempFileDeleter.addFile", object instanceof String ? (String)object : "-", object2);
        PhantomReference<Object> phantomReference = new PhantomReference<Object>(object2, this.queue);
        this.refMap.put(phantomReference, object);
        this.deleteUnused();
        return phantomReference;
    }

    public synchronized void deleteFile(Reference<?> reference, Object object) {
        Object object2;
        if (reference != null && (object2 = this.refMap.remove(reference)) != null) {
            if (SysProperties.CHECK && object != null && !object2.equals(object)) {
                throw DbException.getInternalError("f2:" + object2 + " f:" + object);
            }
            object = object2;
        }
        if (object instanceof String) {
            object2 = (String)object;
            if (FileUtils.exists((String)object2)) {
                try {
                    IOUtils.trace("TempFileDeleter.deleteFile", (String)object2, null);
                    FileUtils.tryDelete((String)object2);
                }
                catch (Exception exception) {}
            }
        } else if (object instanceof AutoCloseable) {
            object2 = (AutoCloseable)object;
            try {
                IOUtils.trace("TempFileDeleter.deleteCloseable", "-", null);
                object2.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public void deleteAll() {
        for (Object object : new ArrayList<Object>(this.refMap.values())) {
            this.deleteFile(null, object);
        }
        this.deleteUnused();
    }

    public void deleteUnused() {
        Reference<Object> reference;
        while ((reference = this.queue.poll()) != null) {
            this.deleteFile(reference, null);
        }
    }

    public void stopAutoDelete(Reference<?> reference, Object object) {
        IOUtils.trace("TempFileDeleter.stopAutoDelete", object instanceof String ? (String)object : "-", reference);
        if (reference != null) {
            Object object2 = this.refMap.remove(reference);
            if (SysProperties.CHECK && (object2 == null || !object2.equals(object))) {
                throw DbException.getInternalError("f2:" + object2 + ' ' + (object2 == null ? "" : object2) + " f:" + object);
            }
        }
        this.deleteUnused();
    }
}

