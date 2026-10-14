/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.invoker;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.service.invoker.Factory;
import org.apache.cxf.service.invoker.PerRequestFactory;

public class PooledFactory
implements Factory {
    BlockingQueue<Object> pool;
    Factory factory;
    int count;
    int max;
    boolean createMore;

    public PooledFactory(Class<?> svcClass, int max) {
        this(new PerRequestFactory(svcClass), max, false);
    }

    public PooledFactory(Factory factory2, int max) {
        this(factory2, max, false);
    }

    public PooledFactory(Factory factory2, int max, boolean createMore) {
        this.factory = factory2;
        if (max < 1) {
            max = 16;
        }
        this.pool = new ArrayBlockingQueue<Object>(max, true);
        this.max = max;
        this.count = 0;
        this.createMore = createMore;
    }

    public PooledFactory(Collection<Object> objs) {
        this.pool = new ArrayBlockingQueue<Object>(objs.size(), true);
        this.pool.addAll(objs);
    }

    @Override
    public Object create(Exchange ex) throws Throwable {
        if (this.factory == null || this.count >= this.max && !this.createMore) {
            return this.pool.take();
        }
        Object o = this.pool.poll();
        if (o == null) {
            return this.createObject(ex);
        }
        return o;
    }

    protected synchronized Object createObject(Exchange e) throws Throwable {
        if (this.factory == null || this.count >= this.max && !this.createMore) {
            return this.pool.take();
        }
        ++this.count;
        return this.factory.create(e);
    }

    @Override
    public void release(Exchange ex, Object o) {
        this.pool.offer(o);
    }
}

