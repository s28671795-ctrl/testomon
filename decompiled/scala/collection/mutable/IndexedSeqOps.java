/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import java.util.Arrays;
import scala.Function1;
import scala.Function2;
import scala.Some;
import scala.collection.mutable.SeqOps;
import scala.math.Ordering;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;

@ScalaSignature(bytes="\u0006\u0005\u001d4qAB\u0004\u0011\u0002\u0007\u0005a\u0002C\u00031\u0001\u0011\u0005\u0011\u0007C\u00036\u0001\u0011\u0005a\u0007C\u0003>\u0001\u0011\u0005a\bC\u0003T\u0001\u0011\u0005A\u000bC\u0003^\u0001\u0011\u0005aLA\u0007J]\u0012,\u00070\u001a3TKF|\u0005o\u001d\u0006\u0003\u0011%\tq!\\;uC\ndWM\u0003\u0002\u000b\u0017\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u00031\tQa]2bY\u0006\u001c\u0001!\u0006\u0003\u00103\rJ3\u0003\u0002\u0001\u0011)1\u0002\"!\u0005\n\u000e\u0003-I!aE\u0006\u0003\r\u0005s\u0017PU3g!\u0015)bc\u0006\u0012)\u001b\u0005I\u0011B\u0001\u0004\n!\tA\u0012\u0004\u0004\u0001\u0005\u000bi\u0001!\u0019A\u000e\u0003\u0003\u0005\u000b\"\u0001H\u0010\u0011\u0005Ei\u0012B\u0001\u0010\f\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!\u0005\u0011\n\u0005\u0005Z!aA!osB\u0011\u0001d\t\u0003\u0007I\u0001!)\u0019A\u0013\u0003\u0005\r\u001bUCA\u000e'\t\u001593E1\u0001\u001c\u0005\u0011yF\u0005J\u0019\u0011\u0005aICA\u0002\u0016\u0001\t\u000b\u00071FA\u0001D#\ta\u0002\u0003E\u0003.]]\u0011\u0003&D\u0001\b\u0013\tysA\u0001\u0004TKF|\u0005o]\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003I\u0002\"!E\u001a\n\u0005QZ!\u0001B+oSR\f!\"\\1q\u0013:\u0004F.Y2f)\t9\u0004(D\u0001\u0001\u0011\u0015I$\u00011\u0001;\u0003\u00051\u0007\u0003B\t</]I!\u0001P\u0006\u0003\u0013\u0019+hn\u0019;j_:\f\u0014aC:peRLe\u000e\u00157bG\u0016,\"a\u0010)\u0015\u0003\u0001#\"aN!\t\u000b\t\u001b\u00019A\"\u0002\u0007=\u0014H\rE\u0002E\u0019>s!!\u0012&\u000f\u0005\u0019KU\"A$\u000b\u0005!k\u0011A\u0002\u001fs_>$h(C\u0001\r\u0013\tY5\"A\u0004qC\u000e\\\u0017mZ3\n\u00055s%\u0001C(sI\u0016\u0014\u0018N\\4\u000b\u0005-[\u0001C\u0001\rQ\t\u0015\t6A1\u0001S\u0005\u0005\u0011\u0015CA\f \u0003=\u0019xN\u001d;J]Bc\u0017mY3XSRDGCA\u001cV\u0011\u00151F\u00011\u0001X\u0003\taG\u000fE\u0003\u00121^9\",\u0003\u0002Z\u0017\tIa)\u001e8di&|gN\r\t\u0003#mK!\u0001X\u0006\u0003\u000f\t{w\u000e\\3b]\u0006i1o\u001c:u\u0013:\u0004F.Y2f\u0005f,\"a\u00183\u0015\u0005\u0001,GCA\u001cb\u0011\u0015\u0011U\u0001q\u0001c!\r!Ej\u0019\t\u00031\u0011$Q!U\u0003C\u0002mAQ!O\u0003A\u0002\u0019\u0004B!E\u001e\u0018G\u0002")
public interface IndexedSeqOps<A, CC, C>
extends scala.collection.IndexedSeqOps<A, CC, C>,
SeqOps<A, CC, C> {
    public static /* synthetic */ IndexedSeqOps mapInPlace$(IndexedSeqOps $this, Function1 f) {
        return $this.mapInPlace(f);
    }

    default public IndexedSeqOps<A, CC, C> mapInPlace(Function1<A, A> f) {
        int siz = this.length();
        for (int i = 0; i < siz; ++i) {
            this.update(i, f.apply(this.apply(i)));
        }
        return this;
    }

    public static /* synthetic */ IndexedSeqOps sortInPlace$(IndexedSeqOps $this, Ordering ord) {
        return $this.sortInPlace(ord);
    }

    default public <B> IndexedSeqOps<A, CC, C> sortInPlace(Ordering<B> ord) {
        int len = this.length();
        if (len > 1) {
            Object[] arr = new Object[len];
            int create_e = 0;
            IntRef i = new IntRef(create_e);
            this.foreach((Function1<Object, Object> & Serializable)x -> {
                IndexedSeqOps.$anonfun$sortInPlace$1(arr, i, x);
                return BoxedUnit.UNIT;
            });
            Arrays.sort(arr, ord);
            i.elem = 0;
            while (i.elem < arr.length) {
                this.update(i.elem, arr[i.elem]);
                ++i.elem;
            }
        }
        return this;
    }

    public static /* synthetic */ IndexedSeqOps sortInPlaceWith$(IndexedSeqOps $this, Function2 lt) {
        return $this.sortInPlaceWith(lt);
    }

    default public IndexedSeqOps<A, CC, C> sortInPlaceWith(Function2<A, A, Object> lt) {
        if (package$.MODULE$.Ordering() == null) {
            throw null;
        }
        return this.sortInPlace(new Ordering<T>(lt){
            private final Function2 cmp$2;

            public Some<Object> tryCompare(T x, T y) {
                return Ordering.tryCompare$(this, x, y);
            }

            public boolean equiv(T x, T y) {
                return Ordering.equiv$(this, x, y);
            }

            public <U extends T> U max(U x, U y) {
                return (U)Ordering.max$(this, x, y);
            }

            public <U extends T> U min(U x, U y) {
                return (U)Ordering.min$(this, x, y);
            }

            public Ordering<T> reverse() {
                return Ordering.reverse$(this);
            }

            public boolean isReverseOf(Ordering<?> other) {
                return Ordering.isReverseOf$(this, other);
            }

            public <U> Ordering<U> on(Function1<U, T> f) {
                return Ordering.on$(this, f);
            }

            public Ordering<T> orElse(Ordering<T> other) {
                return Ordering.orElse$(this, other);
            }

            public <S> Ordering<T> orElseBy(Function1<T, S> f, Ordering<S> ord) {
                return Ordering.orElseBy$(this, f, ord);
            }

            public Ordering.OrderingOps mkOrderingOps(T lhs) {
                return Ordering.mkOrderingOps$(this, lhs);
            }

            public int compare(T x, T y) {
                if (BoxesRunTime.unboxToBoolean(this.cmp$2.apply(x, y))) {
                    return -1;
                }
                if (BoxesRunTime.unboxToBoolean(this.cmp$2.apply(y, x))) {
                    return 1;
                }
                return 0;
            }

            public boolean lt(T x, T y) {
                return BoxesRunTime.unboxToBoolean(this.cmp$2.apply(x, y));
            }

            public boolean gt(T x, T y) {
                return BoxesRunTime.unboxToBoolean(this.cmp$2.apply(y, x));
            }

            public boolean gteq(T x, T y) {
                return !BoxesRunTime.unboxToBoolean(this.cmp$2.apply(x, y));
            }

            public boolean lteq(T x, T y) {
                return !BoxesRunTime.unboxToBoolean(this.cmp$2.apply(y, x));
            }
            {
                this.cmp$2 = cmp$2;
            }
        });
    }

    public static /* synthetic */ IndexedSeqOps sortInPlaceBy$(IndexedSeqOps $this, Function1 f, Ordering ord) {
        return $this.sortInPlaceBy(f, ord);
    }

    default public <B> IndexedSeqOps<A, CC, C> sortInPlaceBy(Function1<A, B> f, Ordering<B> ord) {
        return this.sortInPlace(ord.on(f));
    }

    public static /* synthetic */ void $anonfun$sortInPlace$1(Object[] arr$1, IntRef i$1, Object x) {
        arr$1[i$1.elem] = x;
        ++i$1.elem;
    }

    public static void $init$(IndexedSeqOps $this) {
    }
}

