/*
 * Decompiled with CFR 0.152.
 */
package org.terracotta.context;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeakIdentityHashMap<K, V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeakIdentityHashMap.class);
    private final ReferenceQueue<K> referenceQueue = new ReferenceQueue();
    private final ConcurrentHashMap<Reference<K>, V> backing = new ConcurrentHashMap();

    public V get(K key) {
        this.clean();
        return this.backing.get(this.createReference(key, null));
    }

    public V putIfAbsent(K key, V value) {
        this.clean();
        return this.backing.putIfAbsent(this.createReference(key, this.referenceQueue), value);
    }

    private void clean() {
        Reference<K> ref;
        while ((ref = this.referenceQueue.poll()) != null) {
            V dead = this.backing.remove(ref);
            if (!(dead instanceof Cleanable)) continue;
            try {
                ((Cleanable)dead).clean();
            }
            catch (Throwable t2) {
                LOGGER.warn("Cleaning failed with : {}", t2);
            }
        }
    }

    protected Reference<K> createReference(K key, ReferenceQueue<? super K> queue) {
        return new IdentityWeakReference<K>(key, queue);
    }

    public static interface Cleanable {
        public void clean();
    }

    static class IdentityWeakReference<T>
    extends WeakReference<T> {
        private final int hashCode;

        public IdentityWeakReference(T t2) {
            this(t2, null);
        }

        public IdentityWeakReference(T t2, ReferenceQueue<? super T> rq) {
            super(t2, rq);
            this.hashCode = System.identityHashCode(t2);
        }

        public int hashCode() {
            return this.hashCode;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof IdentityWeakReference) {
                Object ourReferent = this.get();
                return ourReferent != null && ourReferent == ((IdentityWeakReference)o).get();
            }
            return false;
        }
    }
}

