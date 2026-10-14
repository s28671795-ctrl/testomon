/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.generic;

import java.io.Serializable;
import scala.Function2;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000513qAB\u0004\u0011\u0002\u0007\u0005a\u0002C\u0003\u0015\u0001\u0011\u0005Q\u0003C\u0003\u001a\u0001\u0019E!\u0004C\u0003-\u0001\u0019\u0005Q\u0006C\u0003-\u0001\u0011\u0005\u0001\u0007C\u0003;\u0001\u0011\u00051H\u0001\u0007Tk\n$(/Y2uC\ndWM\u0003\u0002\t\u0013\u00059q-\u001a8fe&\u001c'B\u0001\u0006\f\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u0019\u0005)1oY1mC\u000e\u0001QcA\b';M\u0011\u0001\u0001\u0005\t\u0003#Ii\u0011aC\u0005\u0003'-\u0011a!\u00118z%\u00164\u0017A\u0002\u0013j]&$H\u0005F\u0001\u0017!\t\tr#\u0003\u0002\u0019\u0017\t!QK\\5u\u0003\u0011\u0011X\r\u001d:\u0016\u0003m\u0001\"\u0001H\u000f\r\u0001\u00111a\u0004\u0001CC\u0002}\u0011AAU3qeF\u0011\u0001e\t\t\u0003#\u0005J!AI\u0006\u0003\u000f9{G\u000f[5oOB!A\u0005A\u0013\u001c\u001b\u00059\u0001C\u0001\u000f'\t\u00159\u0003A1\u0001)\u0005\u0005\t\u0015C\u0001\u0011*!\t\t\"&\u0003\u0002,\u0017\t\u0019\u0011I\\=\u0002\r\u0011j\u0017N\\;t)\tYb\u0006C\u00030\u0007\u0001\u0007Q%\u0001\u0003fY\u0016lG\u0003B\u000e2gUBQA\r\u0003A\u0002\u0015\nQ!\u001a7f[FBQ\u0001\u000e\u0003A\u0002\u0015\nQ!\u001a7f[JBQA\u000e\u0003A\u0002]\nQ!\u001a7f[N\u00042!\u0005\u001d&\u0013\tI4B\u0001\u0006=e\u0016\u0004X-\u0019;fIz\nA\u0002J7j]V\u001cH%\\5okN$\"a\u0007\u001f\t\u000bu*\u0001\u0019\u0001 \u0002\u0005a\u001c\bcA AK5\t\u0011\"\u0003\u0002B\u0013\ta\u0011\n^3sC\ndWm\u00148dK\"2\u0001a\u0011$H\u0013*\u0003\"!\u0005#\n\u0005\u0015[!A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017%\u0001%\u0002'N+(\r\u001e:bGR\f'\r\\3!SN\u0004C-\u001a9sK\u000e\fG/\u001a3/AQC\u0017n\u001d\u0011jg\u0002rwn\u001e\u0011j[BdW-\\3oi\u0016$\u0007%Y:!a\u0006\u0014H\u000fI8gAM+Go\u00149tY\u0001j\u0015\r](qg2\u0002S\r^2/\u0003\u0015\u0019\u0018N\\2fC\u0005Y\u0015A\u0002\u001a/cMr\u0003\u0007")
public interface Subtractable<A, Repr extends Subtractable<A, Repr>> {
    public Repr repr();

    public Repr $minus(A var1);

    public static /* synthetic */ Subtractable $minus$(Subtractable $this, Object elem1, Object elem2, Seq elems) {
        return $this.$minus(elem1, elem2, elems);
    }

    default public Repr $minus(A elem1, A elem2, Seq<A> elems) {
        return this.$minus(elem1).$minus(elem2).$minus$minus(elems);
    }

    public static /* synthetic */ Subtractable $minus$minus$(Subtractable $this, IterableOnce xs) {
        return $this.$minus$minus(xs);
    }

    /*
     * WARNING - void declaration
     */
    default public Repr $minus$minus(IterableOnce<A> xs) {
        void $div$colon_op;
        Repr Repr = this.repr();
        Iterator<A> iterator2 = xs.iterator();
        Function2<Subtractable, Object, Subtractable> & Serializable intersect = (Function2<Subtractable, Object, Subtractable> & Serializable)(x$1, x$2) -> x$1.$minus(x$2);
        if (iterator2 == null) {
            throw null;
        }
        return (Repr)((Subtractable)iterator2.foldLeft(Repr, $div$colon_op));
    }

    public static void $init$(Subtractable $this) {
    }
}

