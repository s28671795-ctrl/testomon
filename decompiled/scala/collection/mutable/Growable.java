/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Buffer$;
import scala.collection.mutable.Clearable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E4q\u0001D\u0007\u0011\u0002\u0007\u0005A\u0003C\u0003\u001f\u0001\u0011\u0005q\u0004C\u0003$\u0001\u0019\u0005A\u0005C\u00034\u0001\u0011\u0015A\u0007C\u00034\u0001\u0011\u0015!\bC\u0003P\u0001\u0011\u0005\u0001\u000bC\u0003X\u0001\u0011\u0015\u0001\fC\u0003\\\u0001\u0011\u0005AlB\u0003a\u001b!\u0005\u0011MB\u0003\r\u001b!\u0005!\rC\u0003d\u0013\u0011\u0005A\rC\u0003f\u0013\u0011\u0005aM\u0001\u0005He><\u0018M\u00197f\u0015\tqq\"A\u0004nkR\f'\r\\3\u000b\u0005A\t\u0012AC2pY2,7\r^5p]*\t!#A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005UQ3c\u0001\u0001\u00175A\u0011q\u0003G\u0007\u0002#%\u0011\u0011$\u0005\u0002\u0007\u0003:L(+\u001a4\u0011\u0005maR\"A\u0007\n\u0005ui!!C\"mK\u0006\u0014\u0018M\u00197f\u0003\u0019!\u0013N\\5uIQ\t\u0001\u0005\u0005\u0002\u0018C%\u0011!%\u0005\u0002\u0005+:LG/\u0001\u0004bI\u0012|e.\u001a\u000b\u0003K\u0019j\u0011\u0001\u0001\u0005\u0006O\t\u0001\r\u0001K\u0001\u0005K2,W\u000e\u0005\u0002*U1\u0001AAB\u0016\u0001\u0011\u000b\u0007AFA\u0001B#\ti\u0003\u0007\u0005\u0002\u0018]%\u0011q&\u0005\u0002\b\u001d>$\b.\u001b8h!\t9\u0012'\u0003\u00023#\t\u0019\u0011I\\=\u0002\u0011\u0011\u0002H.^:%KF$\"!J\u001b\t\u000b\u001d\u001a\u0001\u0019\u0001\u0015)\u0005\r9\u0004CA\f9\u0013\tI\u0014C\u0001\u0004j]2Lg.\u001a\u000b\u0005Kmjt\bC\u0003=\t\u0001\u0007\u0001&A\u0003fY\u0016l\u0017\u0007C\u0003?\t\u0001\u0007\u0001&A\u0003fY\u0016l'\u0007C\u0003A\t\u0001\u0007\u0011)A\u0003fY\u0016l7\u000fE\u0002\u0018\u0005\"J!aQ\t\u0003\u0015q\u0012X\r]3bi\u0016$g\b\u000b\u0002\u0005o!2AAR%K\u00196\u0003\"aF$\n\u0005!\u000b\"A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017%A&\u0002iV\u001bX\r\t1,Wu\u0002\u0007%Y6bA\u0001\fG\rZ!mY\u0002\u0004\u0013N\\:uK\u0006$\u0007e\u001c4!m\u0006\u0014\u0018M]4tA\u0001\\S\bY\u001e!S:4\u0017\u000e\u001f\u0011pa\u0016\u0014\u0018\r^5p]N\u0004s/\u001b;iA\u0005t\u0007e\u001c9fe\u0006tG\rI8gA5,H\u000e^5qY\u0016\u0004\u0013M]4tA]LG\u000e\u001c\u0011cK\u0002\"W\r\u001d:fG\u0006$X\rZ\u0001\u0006g&t7-Z\u0011\u0002\u001d\u00061!GL\u00194]A\na!\u00193e\u00032dGCA\u0013R\u0011\u0015\u0011V\u00011\u0001T\u0003\tA8\u000fE\u0002U+\"j\u0011aD\u0005\u0003->\u0011A\"\u0013;fe\u0006\u0014G.Z(oG\u0016\fQ\u0002\n9mkN$\u0003\u000f\\;tI\u0015\fHCA\u0013Z\u0011\u0015\u0011f\u00011\u0001TQ\t1q'A\u0005l]><hnU5{KV\tQ\f\u0005\u0002\u0018=&\u0011q,\u0005\u0002\u0004\u0013:$\u0018\u0001C$s_^\f'\r\\3\u0011\u0005mI1CA\u0005\u0017\u0003\u0019a\u0014N\\5u}Q\t\u0011-\u0001\u0003ge>lWCA4n)\rA\u0017N\u001c\b\u0003S%DQA[\u0006A\u0002-\fQ!Z7qif\u00042a\u0007\u0001m!\tIS\u000eB\u0003,\u0017\t\u0007A\u0006C\u0003p\u0017\u0001\u0007\u0001/\u0001\u0002jiB\u0019A+\u00167")
public interface Growable<A>
extends Clearable {
    public static <A> Growable<A> from(Growable<A> empty, IterableOnce<A> it) {
        if (empty == null) {
            throw null;
        }
        return empty.addAll(it);
    }

    public Growable<A> addOne(A var1);

    public static /* synthetic */ Growable $plus$eq$(Growable $this, Object elem) {
        return $this.$plus$eq(elem);
    }

    default public Growable<A> $plus$eq(A elem) {
        return this.addOne(elem);
    }

    public static /* synthetic */ Growable $plus$eq$(Growable $this, Object elem1, Object elem2, Seq elems) {
        return $this.$plus$eq(elem1, elem2, elems);
    }

    default public Growable<A> $plus$eq(A elem1, A elem2, Seq<A> elems) {
        Growable<A> growable = this.addOne(elem1);
        if (growable == null) {
            throw null;
        }
        Growable<A> growable2 = growable.addOne(elem2);
        if (growable2 == null) {
            throw null;
        }
        return growable2.addAll(elems);
    }

    public static /* synthetic */ Growable addAll$(Growable $this, IterableOnce xs) {
        return $this.addAll(xs);
    }

    default public Growable<A> addAll(IterableOnce<A> xs) {
        if (xs == this) {
            this.addAll((IterableOnce<A>)Buffer$.MODULE$.from((IterableOnce)xs));
        } else {
            Iterator<A> it = xs.iterator();
            while (it.hasNext()) {
                this.addOne(it.next());
            }
        }
        return this;
    }

    public static /* synthetic */ Growable $plus$plus$eq$(Growable $this, IterableOnce xs) {
        return $this.$plus$plus$eq(xs);
    }

    default public Growable<A> $plus$plus$eq(IterableOnce<A> xs) {
        return this.addAll(xs);
    }

    public static /* synthetic */ int knownSize$(Growable $this) {
        return $this.knownSize();
    }

    default public int knownSize() {
        return -1;
    }

    public static void $init$(Growable $this) {
    }
}

