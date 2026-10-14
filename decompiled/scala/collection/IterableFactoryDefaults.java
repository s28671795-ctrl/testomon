/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.mutable.Builder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-3q!\u0002\u0004\u0011\u0002\u0007\u00051\u0002C\u00036\u0001\u0011\u0005a\u0007C\u0003;\u0001\u0011E1\bC\u0003B\u0001\u0011E!\tC\u0003J\u0001\u0011\u0005#JA\fJi\u0016\u0014\u0018M\u00197f\r\u0006\u001cGo\u001c:z\t\u00164\u0017-\u001e7ug*\u0011q\u0001C\u0001\u000bG>dG.Z2uS>t'\"A\u0005\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0019AbF\u0011\u0014\u0007\u0001i\u0011\u0003\u0005\u0002\u000f\u001f5\t\u0001\"\u0003\u0002\u0011\u0011\t1\u0011I\\=SK\u001a\u0004RAE\n\u0016A)j\u0011AB\u0005\u0003)\u0019\u00111\"\u0013;fe\u0006\u0014G.Z(qgB\u0011ac\u0006\u0007\u0001\t\u0019A\u0002\u0001\"b\u00013\t\t\u0011)\u0005\u0002\u001b;A\u0011abG\u0005\u00039!\u0011qAT8uQ&tw\r\u0005\u0002\u000f=%\u0011q\u0004\u0003\u0002\u0004\u0003:L\bC\u0001\f\"\t\u0019\u0011\u0003\u0001\"b\u0001G\t\u00111iQ\u000b\u0003I\u001d\n\"AG\u0013\u0011\u000bI\u0019b\u0005I\u0015\u0011\u0005Y9C!\u0002\u0015\"\u0005\u0004I\"!\u0001=\u0011\u0007Y\tc\u0005E\u0002\u0017C-R#!\u0006\u0017,\u00035\u0002\"AL\u001a\u000e\u0003=R!\u0001M\u0019\u0002\u0013Ut7\r[3dW\u0016$'B\u0001\u001a\t\u0003)\tgN\\8uCRLwN\\\u0005\u0003i=\u0012\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u0003\u0019!\u0013N\\5uIQ\tq\u0007\u0005\u0002\u000fq%\u0011\u0011\b\u0003\u0002\u0005+:LG/\u0001\u0007ge>l7\u000b]3dS\u001aL7\r\u0006\u0002+y!)QH\u0001a\u0001}\u0005!1m\u001c7m!\r\u0011rhK\u0005\u0003\u0001\u001a\u0011A\"\u0013;fe\u0006\u0014G.Z(oG\u0016\f!C\\3x'B,7-\u001b4jG\n+\u0018\u000e\u001c3feV\t1\t\u0005\u0003E\u000f.RS\"A#\u000b\u0005\u00193\u0011aB7vi\u0006\u0014G.Z\u0005\u0003\u0011\u0016\u0013qAQ;jY\u0012,'/A\u0003f[B$\u00180F\u0001+\u0001")
public interface IterableFactoryDefaults<A, CC extends IterableOps<Object, CC, CC>>
extends IterableOps<A, CC, CC> {
    public static /* synthetic */ IterableOps fromSpecific$(IterableFactoryDefaults $this, IterableOnce coll) {
        return $this.fromSpecific(coll);
    }

    @Override
    default public CC fromSpecific(IterableOnce<A> coll) {
        return (CC)((IterableOps)this.iterableFactory().from(coll));
    }

    public static /* synthetic */ Builder newSpecificBuilder$(IterableFactoryDefaults $this) {
        return $this.newSpecificBuilder();
    }

    @Override
    default public Builder<A, CC> newSpecificBuilder() {
        return this.iterableFactory().newBuilder();
    }

    public static /* synthetic */ IterableOps empty$(IterableFactoryDefaults $this) {
        return $this.empty();
    }

    @Override
    default public CC empty() {
        return (CC)((IterableOps)this.iterableFactory().empty());
    }

    public static void $init$(IterableFactoryDefaults $this) {
    }
}

