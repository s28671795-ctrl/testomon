/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import scala.Option;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00054aAC\u0006\u0002\u0002-i\u0001\"B\n\u0001\t\u0003)\u0002BB\u0015\u0001A\u0003%!\u0006\u0003\u00047\u0001\u0001\u0006Ia\u000e\u0005\u0007{\u0001\u0001\u000b\u0011\u0002 \t\r\u0005\u0003\u0001\u0015!\u0003C\u0011\u0015q\u0005A\"\u0005P\u0011\u0015\u0011\u0006A\"\u0005T\u0011\u0015I\u0006\u0001\"\u0001[\u0011\u0015i\u0006\u0001\"\u0001_\u0005=)f.[9vK:,7o]\"bG\",'\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\u0016\u00079I2e\u0005\u0002\u0001\u001fA\u0011\u0001#E\u0007\u0002\u0017%\u0011!c\u0003\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\u0012A\u0006\t\u0005!\u00019\"\u0005\u0005\u0002\u001931\u0001A!\u0002\u000e\u0001\u0005\u0004Y\"!A&\u0012\u0005qy\u0002C\u0001\t\u001e\u0013\tq2BA\u0004O_RD\u0017N\\4\u0011\u0005A\u0001\u0013BA\u0011\f\u0005\r\te.\u001f\t\u00031\r\"Q\u0001\n\u0001C\u0002\u0015\u0012\u0011AV\t\u0003M}\u0001\"\u0001E\u0014\n\u0005!Z!\u0001\u0002(vY2\f1A]<m!\tYC'D\u0001-\u0015\tic&A\u0003m_\u000e\\7O\u0003\u00020a\u0005Q1m\u001c8dkJ\u0014XM\u001c;\u000b\u0005E\u0012\u0014\u0001B;uS2T\u0011aM\u0001\u0005U\u00064\u0018-\u0003\u00026Y\t1\"+Z3oiJ\fg\u000e\u001e*fC\u0012<&/\u001b;f\u0019>\u001c7.A\u0003sY>\u001c7\u000e\u0005\u00029w5\t\u0011H\u0003\u0002;Y\u00051\"+Z3oiJ\fg\u000e\u001e*fC\u0012<&/\u001b;f\u0019>\u001c7.\u0003\u0002=s\tA!+Z1e\u0019>\u001c7.A\u0003xY>\u001c7\u000e\u0005\u00029\u007f%\u0011\u0001)\u000f\u0002\n/JLG/\u001a'pG.\f1!\\1q!\u0011\u0019Ei\u0006$\u000e\u0003AJ!!\u0012\u0019\u0003\u0017]+\u0017m\u001b%bg\"l\u0015\r\u001d\t\u0004\u000f2\u0013S\"\u0001%\u000b\u0005%S\u0015a\u0001:fM*\u00111JM\u0001\u0005Y\u0006tw-\u0003\u0002N\u0011\niq+Z1l%\u00164WM]3oG\u0016\fAB^1mk\u00164%o\\7LKf$\"A\t)\t\u000bE3\u0001\u0019A\f\u0002\u0003-\fAb[3z\rJ|WNV1mk\u0016$\"\u0001V,\u0011\u0007A)v#\u0003\u0002W\u0017\t1q\n\u001d;j_:DQ\u0001W\u0004A\u0002\t\n\u0011A^\u0001\u0006CB\u0004H.\u001f\u000b\u0003EmCQ\u0001\u0018\u0005A\u0002]\tAA\\1nK\u00069QO\\1qa2LHC\u0001+`\u0011\u0015\u0001\u0017\u00021\u0001#\u0003\u0015yG\u000f[3s\u0001")
public abstract class UniquenessCache<K, V> {
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock rlock = this.rwl.readLock();
    private final ReentrantReadWriteLock.WriteLock wlock = this.rwl.writeLock();
    private final WeakHashMap<K, WeakReference<V>> map = new WeakHashMap();

    public abstract V valueFromKey(K var1);

    public abstract Option<K> keyFromValue(V var1);

    public V apply(K name) {
        Object object = this.cached$1(name);
        if (object == null) {
            return (V)this.updateCache$1(name);
        }
        return (V)object;
    }

    public Option<K> unapply(V other) {
        return this.keyFromValue(other);
    }

    private final Object cached$1(Object name$1) {
        Object v0;
        this.rlock.lock();
        try {
            WeakReference<V> reference = this.map.get(name$1);
            v0 = reference == null ? null : reference.get();
        }
        finally {
            this.rlock.unlock();
        }
        return v0;
    }

    /*
     * WARNING - void declaration
     */
    private final Object updateCache$1(Object name$1) {
        Object object;
        this.wlock.lock();
        try {
            Object res = this.cached$1(name$1);
            if (res != null) {
                object = res;
            } else {
                void var3_3;
                this.map.remove(name$1);
                V sym = this.valueFromKey(name$1);
                this.map.put(name$1, new WeakReference<V>(sym));
                object = var3_3;
            }
        }
        finally {
            this.wlock.unlock();
        }
        return object;
    }
}

