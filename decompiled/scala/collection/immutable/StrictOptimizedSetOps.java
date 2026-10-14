/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.immutable.SetOps;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q2qa\u0001\u0003\u0011\u0002\u0007\u00051\u0002C\u00031\u0001\u0011\u0005\u0011\u0007C\u00036\u0001\u0011\u0005cGA\u000bTiJL7\r^(qi&l\u0017N_3e'\u0016$x\n]:\u000b\u0005\u00151\u0011!C5n[V$\u0018M\u00197f\u0015\t9\u0001\"\u0001\u0006d_2dWm\u0019;j_:T\u0011!C\u0001\u0006g\u000e\fG.Y\u0002\u0001+\u0011aq#I\u0014\u0014\u000b\u0001i\u0011CK\u0017\u0011\u00059yQ\"\u0001\u0005\n\u0005AA!AB!osJ+g\rE\u0003\u0013'U\u0001c%D\u0001\u0005\u0013\t!BA\u0001\u0004TKR|\u0005o\u001d\t\u0003-]a\u0001\u0001B\u0003\u0019\u0001\t\u0007\u0011DA\u0001B#\tQR\u0004\u0005\u0002\u000f7%\u0011A\u0004\u0003\u0002\b\u001d>$\b.\u001b8h!\tqa$\u0003\u0002 \u0011\t\u0019\u0011I\\=\u0011\u0005Y\tCA\u0002\u0012\u0001\t\u000b\u00071E\u0001\u0002D\u0007V\u0011\u0011\u0004\n\u0003\u0006K\u0005\u0012\r!\u0007\u0002\u00021B\u0011ac\n\u0003\u0007Q\u0001!)\u0019A\u0015\u0003\u0003\r\u000b\"AG\t\u0011\u000b-bS\u0003\t\u0014\u000e\u0003\u0019I!a\u0001\u0004\u0011\u000b-rS\u0003\t\u0014\n\u0005=2!AG*ue&\u001cGo\u00149uS6L'0\u001a3Ji\u0016\u0014\u0018M\u00197f\u001fB\u001c\u0018A\u0002\u0013j]&$H\u0005F\u00013!\tq1'\u0003\u00025\u0011\t!QK\\5u\u0003\u0019\u0019wN\\2biR\u0011ae\u000e\u0005\u0006q\t\u0001\r!O\u0001\u0005i\"\fG\u000fE\u0002,uUI!a\u000f\u0004\u0003\u0019%#XM]1cY\u0016|enY3")
public interface StrictOptimizedSetOps<A, CC, C extends SetOps<A, CC, C>>
extends SetOps<A, CC, C>,
scala.collection.StrictOptimizedSetOps<A, CC, C> {
    public static /* synthetic */ SetOps concat$(StrictOptimizedSetOps $this, IterableOnce that) {
        return $this.concat((IterableOnce<A>)that);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    default public C concat(IterableOnce<A> that) {
        SetOps result = (SetOps)this.coll();
        Iterator<A> it = that.iterator();
        while (it.hasNext()) {
            void $plus_elem;
            A a = it.next();
            if (result == null) {
                throw null;
            }
            a = null;
            result = result.incl($plus_elem);
        }
        return (C)result;
    }

    public static void $init$(StrictOptimizedSetOps $this) {
    }
}

