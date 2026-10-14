/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.base.internal;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;

public class Finalizer
implements Runnable {
    private static final Logger logger = Logger.getLogger(Finalizer.class.getName());
    private static final String FINALIZABLE_REFERENCE = "com.google.common.base.FinalizableReference";
    private final WeakReference<Class<?>> finalizableReferenceClassReference;
    private final PhantomReference<Object> frqReference;
    private final ReferenceQueue<Object> queue;
    @CheckForNull
    private static final Constructor<Thread> bigThreadConstructor = Finalizer.getBigThreadConstructor();
    @CheckForNull
    private static final Field inheritableThreadLocals = bigThreadConstructor == null ? Finalizer.getInheritableThreadLocalsField() : null;

    public static void startFinalizer(Class<?> finalizableReferenceClass, ReferenceQueue<Object> queue, PhantomReference<Object> frqReference) {
        if (!finalizableReferenceClass.getName().equals(FINALIZABLE_REFERENCE)) {
            throw new IllegalArgumentException("Expected com.google.common.base.FinalizableReference.");
        }
        Finalizer finalizer = new Finalizer(finalizableReferenceClass, queue, frqReference);
        String threadName = Finalizer.class.getName();
        Thread thread2 = null;
        if (bigThreadConstructor != null) {
            try {
                boolean inheritThreadLocals = false;
                long defaultStackSize = 0L;
                thread2 = bigThreadConstructor.newInstance(null, finalizer, threadName, defaultStackSize, inheritThreadLocals);
            }
            catch (Throwable t2) {
                logger.log(Level.INFO, "Failed to create a thread without inherited thread-local values", t2);
            }
        }
        if (thread2 == null) {
            thread2 = new Thread((ThreadGroup)null, finalizer, threadName);
        }
        thread2.setDaemon(true);
        try {
            if (inheritableThreadLocals != null) {
                inheritableThreadLocals.set(thread2, null);
            }
        }
        catch (Throwable t3) {
            logger.log(Level.INFO, "Failed to clear thread local values inherited by reference finalizer thread.", t3);
        }
        thread2.start();
    }

    private Finalizer(Class<?> finalizableReferenceClass, ReferenceQueue<Object> queue, PhantomReference<Object> frqReference) {
        this.queue = queue;
        this.finalizableReferenceClassReference = new WeakReference(finalizableReferenceClass);
        this.frqReference = frqReference;
    }

    @Override
    public void run() {
        while (true) {
            try {
                while (this.cleanUp(this.queue.remove())) {
                }
            }
            catch (InterruptedException interruptedException) {
                continue;
            }
            break;
        }
    }

    private boolean cleanUp(Reference<?> firstReference) {
        Reference<Object> furtherReference;
        Method finalizeReferentMethod = this.getFinalizeReferentMethod();
        if (finalizeReferentMethod == null) {
            return false;
        }
        if (!this.finalizeReference(firstReference, finalizeReferentMethod)) {
            return false;
        }
        do {
            if ((furtherReference = this.queue.poll()) != null) continue;
            return true;
        } while (this.finalizeReference(furtherReference, finalizeReferentMethod));
        return false;
    }

    private boolean finalizeReference(Reference<?> reference, Method finalizeReferentMethod) {
        reference.clear();
        if (reference == this.frqReference) {
            return false;
        }
        try {
            finalizeReferentMethod.invoke(reference, new Object[0]);
        }
        catch (Throwable t2) {
            logger.log(Level.SEVERE, "Error cleaning up after reference.", t2);
        }
        return true;
    }

    @CheckForNull
    private Method getFinalizeReferentMethod() {
        Class finalizableReferenceClass = (Class)this.finalizableReferenceClassReference.get();
        if (finalizableReferenceClass == null) {
            return null;
        }
        try {
            return finalizableReferenceClass.getMethod("finalizeReferent", new Class[0]);
        }
        catch (NoSuchMethodException e) {
            throw new AssertionError((Object)e);
        }
    }

    @CheckForNull
    private static Field getInheritableThreadLocalsField() {
        try {
            Field inheritableThreadLocals = Thread.class.getDeclaredField("inheritableThreadLocals");
            inheritableThreadLocals.setAccessible(true);
            return inheritableThreadLocals;
        }
        catch (Throwable t2) {
            logger.log(Level.INFO, "Couldn't access Thread.inheritableThreadLocals. Reference finalizer threads will inherit thread local values.");
            return null;
        }
    }

    @CheckForNull
    private static Constructor<Thread> getBigThreadConstructor() {
        try {
            return Thread.class.getConstructor(ThreadGroup.class, Runnable.class, String.class, Long.TYPE, Boolean.TYPE);
        }
        catch (Throwable t2) {
            return null;
        }
    }
}

