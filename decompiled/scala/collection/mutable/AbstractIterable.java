/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.collection.IterableFactory;
import scala.collection.mutable.Iterable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t2QAA\u0002\u0002\u0002)AQa\b\u0001\u0005\u0002\u0001\u0012\u0001#\u00112tiJ\f7\r^%uKJ\f'\r\\3\u000b\u0005\u0011)\u0011aB7vi\u0006\u0014G.\u001a\u0006\u0003\r\u001d\t!bY8mY\u0016\u001cG/[8o\u0015\u0005A\u0011!B:dC2\f7\u0001A\u000b\u0003\u0017E\u00192\u0001\u0001\u0007\u001c!\riabD\u0007\u0002\u000b%\u0011!!\u0002\t\u0003!Ea\u0001\u0001B\u0003\u0013\u0001\t\u00071CA\u0001B#\t!\u0002\u0004\u0005\u0002\u0016-5\tq!\u0003\u0002\u0018\u000f\t9aj\u001c;iS:<\u0007CA\u000b\u001a\u0013\tQrAA\u0002B]f\u00042\u0001H\u000f\u0010\u001b\u0005\u0019\u0011B\u0001\u0010\u0004\u0005!IE/\u001a:bE2,\u0017A\u0002\u001fj]&$h\bF\u0001\"!\ra\u0002a\u0004")
public abstract class AbstractIterable<A>
extends scala.collection.AbstractIterable<A>
implements Iterable<A> {
    @Override
    public IterableFactory<Iterable> iterableFactory() {
        return Iterable.iterableFactory$(this);
    }
}

