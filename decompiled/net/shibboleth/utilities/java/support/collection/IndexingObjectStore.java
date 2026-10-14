/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.collection;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.concurrent.ThreadSafe;
import net.shibboleth.utilities.java.support.collection.LazyMap;

@ThreadSafe
public class IndexingObjectStore<T> {
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private Map<String, StoredObjectWrapper> objectStore = new LazyMap<String, StoredObjectWrapper>();
    private Map<T, Integer> indexStore = new LazyMap<T, Integer>();
    private int lastIndex = 0;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void clear() {
        Lock writeLock = this.rwLock.writeLock();
        writeLock.lock();
        try {
            this.objectStore.clear();
            this.indexStore.clear();
        }
        finally {
            writeLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean containsIndex(String index) {
        Lock readLock = this.rwLock.readLock();
        readLock.lock();
        try {
            boolean bl = this.objectStore.containsKey(index);
            return bl;
        }
        finally {
            readLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean containsInstance(T instance) {
        Lock readLock = this.rwLock.readLock();
        readLock.lock();
        try {
            Integer index = this.indexStore.get(instance);
            if (index == null) {
                boolean bl = false;
                return bl;
            }
            boolean bl = this.objectStore.containsKey(index.toString());
            return bl;
        }
        finally {
            readLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isEmpty() {
        Lock readLock = this.rwLock.readLock();
        readLock.lock();
        try {
            boolean bl = this.objectStore.isEmpty();
            return bl;
        }
        finally {
            readLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String put(T object) {
        if (object == null) {
            return null;
        }
        Lock writeLock = this.rwLock.writeLock();
        writeLock.lock();
        try {
            String index = this.getIndex(object);
            StoredObjectWrapper objectWrapper = this.objectStore.get(index);
            if (objectWrapper == null) {
                objectWrapper = new StoredObjectWrapper(object);
                this.objectStore.put(index, objectWrapper);
            }
            objectWrapper.incremementReferenceCount();
            String string = index;
            return string;
        }
        finally {
            writeLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public T get(String index) {
        if (index == null) {
            return null;
        }
        Lock readLock = this.rwLock.readLock();
        readLock.lock();
        try {
            StoredObjectWrapper objectWrapper = this.objectStore.get(index);
            if (objectWrapper != null) {
                Object t2 = objectWrapper.getObject();
                return t2;
            }
            T t3 = null;
            return t3;
        }
        finally {
            readLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void remove(String index) {
        if (index == null) {
            return;
        }
        Lock writeLock = this.rwLock.writeLock();
        writeLock.lock();
        try {
            StoredObjectWrapper objectWrapper = this.objectStore.get(index);
            if (objectWrapper != null) {
                objectWrapper.decremementReferenceCount();
                if (objectWrapper.getReferenceCount() == 0) {
                    this.objectStore.remove(index);
                    this.removeIndex(objectWrapper.getObject());
                }
            }
        }
        finally {
            writeLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int size() {
        Lock readLock = this.rwLock.readLock();
        readLock.lock();
        try {
            int n = this.objectStore.size();
            return n;
        }
        finally {
            readLock.unlock();
        }
    }

    protected String getIndex(T object) {
        Integer index = this.indexStore.get(object);
        if (index == null) {
            index = ++this.lastIndex;
            this.indexStore.put(object, index);
        }
        return index.toString();
    }

    protected void removeIndex(T object) {
        this.indexStore.remove(object);
    }

    private class StoredObjectWrapper {
        private T object;
        private int referenceCount;

        public StoredObjectWrapper(T wrappedObject) {
            this.object = wrappedObject;
            this.referenceCount = 0;
        }

        public T getObject() {
            return this.object;
        }

        public int getReferenceCount() {
            return this.referenceCount;
        }

        public void incremementReferenceCount() {
            ++this.referenceCount;
        }

        public void decremementReferenceCount() {
            --this.referenceCount;
        }
    }
}

