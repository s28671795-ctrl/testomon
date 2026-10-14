/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function1;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.collection.LinearSeq;
import scala.collection.LinearSeqOps;
import scala.collection.StrictOptimizedSeqOps;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005=3q!\u0002\u0004\u0011\u0002\u0007\u00051\u0002C\u00035\u0001\u0011\u0005Q\u0007C\u0003:\u0001\u0011\u0005#\bC\u0003?\u0001\u0011\u0005s\bC\u0003F\u0001\u0011\u0005cIA\u000eTiJL7\r^(qi&l\u0017N_3e\u0019&tW-\u0019:TKF|\u0005o\u001d\u0006\u0003\u000f!\t!bY8mY\u0016\u001cG/[8o\u0015\u0005I\u0011!B:dC2\f7\u0001A\u000b\u0005\u0019]q\u0012f\u0005\u0003\u0001\u001bE\t\u0004C\u0001\b\u0010\u001b\u0005A\u0011B\u0001\t\t\u0005\r\te.\u001f\t\u0006%M)R\u0004K\u0007\u0002\r%\u0011AC\u0002\u0002\r\u0019&tW-\u0019:TKF|\u0005o\u001d\t\u0003-]a\u0001\u0001\u0002\u0004\u0019\u0001\u0011\u0015\r!\u0007\u0002\u0002\u0003F\u0011!$\u0004\t\u0003\u001dmI!\u0001\b\u0005\u0003\u000f9{G\u000f[5oOB\u0011aC\b\u0003\u0007?\u0001!)\u0019\u0001\u0011\u0003\u0005\r\u001bUCA\u0011'#\tQ\"\u0005E\u0002\u0013G\u0015J!\u0001\n\u0004\u0003\u00131Kg.Z1s'\u0016\f\bC\u0001\f'\t\u00159cD1\u0001\u001a\u0005\u0005A\u0006C\u0001\f*\t\u0019Q\u0003\u0001\"b\u0001W\t\t1)\u0005\u0002\u001bYI\u0019Qf\f\u0019\u0007\t9\u0002\u0001\u0001\f\u0002\ryI,g-\u001b8f[\u0016tGO\u0010\t\u0004%\r*\u0002#\u0002\n\u0001+uA\u0003#\u0002\n3+uA\u0013BA\u001a\u0007\u0005U\u0019FO]5di>\u0003H/[7ju\u0016$7+Z9PaN\fa\u0001J5oSR$C#\u0001\u001c\u0011\u000599\u0014B\u0001\u001d\t\u0005\u0011)f.\u001b;\u0002\u0011%$XM]1u_J,\u0012a\u000f\t\u0004%q*\u0012BA\u001f\u0007\u0005!IE/\u001a:bi>\u0014\u0018\u0001\u00023s_B$\"\u0001\u000b!\t\u000b\u0005\u001b\u0001\u0019\u0001\"\u0002\u00039\u0004\"AD\"\n\u0005\u0011C!aA%oi\u0006IAM]8q/\"LG.\u001a\u000b\u0003Q\u001dCQ\u0001\u0013\u0003A\u0002%\u000b\u0011\u0001\u001d\t\u0005\u001d)+B*\u0003\u0002L\u0011\tIa)\u001e8di&|g.\r\t\u0003\u001d5K!A\u0014\u0005\u0003\u000f\t{w\u000e\\3b]\u0002")
public interface StrictOptimizedLinearSeqOps<A, CC extends LinearSeq<Object>, C extends LinearSeq<A> & StrictOptimizedLinearSeqOps<A, CC, C>>
extends LinearSeqOps<A, CC, C>,
StrictOptimizedSeqOps<A, CC, C> {
    public static /* synthetic */ Iterator iterator$(StrictOptimizedLinearSeqOps $this) {
        return $this.iterator();
    }

    @Override
    default public Iterator<A> iterator() {
        return new AbstractIterator<A>(this){
            private StrictOptimizedLinearSeqOps<A, CC, C> current;

            public boolean hasNext() {
                return !this.current.isEmpty();
            }

            public A next() {
                A r = this.current.head();
                this.current = (StrictOptimizedLinearSeqOps)this.current.tail();
                return r;
            }
            {
                this.current = $outer;
            }
        };
    }

    public static /* synthetic */ LinearSeq drop$(StrictOptimizedLinearSeqOps $this, int n) {
        return $this.drop(n);
    }

    @Override
    default public C drop(int n) {
        return (C)this.loop$2(n, (LinearSeq)this.coll());
    }

    public static /* synthetic */ LinearSeq dropWhile$(StrictOptimizedLinearSeqOps $this, Function1 p) {
        return $this.dropWhile(p);
    }

    @Override
    default public C dropWhile(Function1<A, Object> p) {
        return (C)this.loop$3((LinearSeq)this.coll(), p);
    }

    private LinearSeq loop$2(int n, LinearSeq s2) {
        while (n > 0 && !s2.isEmpty()) {
            s2 = (LinearSeq)s2.tail();
            --n;
        }
        return s2;
    }

    private LinearSeq loop$3(LinearSeq s2, Function1 p$1) {
        while (s2.nonEmpty() && BoxesRunTime.unboxToBoolean(p$1.apply(s2.head()))) {
            s2 = (LinearSeq)s2.tail();
        }
        return s2;
    }

    public static void $init$(StrictOptimizedLinearSeqOps $this) {
    }
}

