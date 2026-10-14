/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.None$;
import scala.Option;
import scala.Some;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}4qAD\b\u0011\u0002\u0007\u0005A\u0003C\u0003\u001b\u0001\u0011\u00051\u0004C\u0003 \u0001\u0019\u0005\u0001\u0005C\u00039\u0001\u0019\u0005\u0011\bC\u0003;\u0001\u0019\u0005\u0011\bC\u0003<\u0001\u0011\u0005A\bC\u0003W\u0001\u0019\u0005q\u000bC\u0003c\u0001\u0011\u00051\rC\u0003]\u0001\u0011\u0015a\rC\u0003l\u0001\u0011\u0005A\u000eC\u0003b\u0001\u0011\u0015a\u000eC\u0003t\u0001\u0011\u0005A\u000fC\u0003w\u0001\u0011\u0015q\u000fC\u0003}\u0001\u0019\u0005QPA\u0005T_J$X\rZ(qg*\u0011\u0001#E\u0001\u000bG>dG.Z2uS>t'\"\u0001\n\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0019QcL-\u0014\u0005\u00011\u0002CA\f\u0019\u001b\u0005\t\u0012BA\r\u0012\u0005\u0019\te.\u001f*fM\u00061A%\u001b8ji\u0012\"\u0012\u0001\b\t\u0003/uI!AH\t\u0003\tUs\u0017\u000e^\u0001\t_J$WM]5oOV\t\u0011\u0005E\u0002#U5r!a\t\u0015\u000f\u0005\u0011:S\"A\u0013\u000b\u0005\u0019\u001a\u0012A\u0002\u001fs_>$h(C\u0001\u0013\u0013\tI\u0013#A\u0004qC\u000e\\\u0017mZ3\n\u0005-b#\u0001C(sI\u0016\u0014\u0018N\\4\u000b\u0005%\n\u0002C\u0001\u00180\u0019\u0001!Q\u0001\r\u0001C\u0002E\u0012\u0011!Q\t\u0003eU\u0002\"aF\u001a\n\u0005Q\n\"a\u0002(pi\"Lgn\u001a\t\u0003/YJ!aN\t\u0003\u0007\u0005s\u00170\u0001\u0005gSJ\u001cHoS3z+\u0005i\u0013a\u00027bgR\\U-_\u0001\bG>l\u0007/\u0019:f)\ri\u0004I\u0011\t\u0003/yJ!aP\t\u0003\u0007%sG\u000fC\u0003B\u000b\u0001\u0007Q&\u0001\u0002la!)1)\u0002a\u0001[\u0005\u00111.\r\u0015\u0003\u000b\u0015\u0003\"a\u0006$\n\u0005\u001d\u000b\"AB5oY&tW\r\u000b\u0004\u0006\u00132ku\n\u0015\t\u0003/)K!aS\t\u0003)\u0011,\u0007O]3dCR,Gm\u0014<feJLG-\u001b8h\u0003\u001diWm]:bO\u0016\f\u0013AT\u0001\u001d+N,\u0007e\u001c:eKJLgn\u001a\u0018d_6\u0004\u0018M]3!S:\u001cH/Z1e\u0003\u0015\u0019\u0018N\\2fC\u0005\t\u0016A\u0002\u001a/cMr\u0003\u0007\u000b\u0004\u0006'2ku\n\u0015\t\u0003/QK!!V\t\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0005sC:<W-S7qYR\u0019\u0001l\u00171\u0011\u00059JFA\u0002.\u0001\t\u000b\u0007\u0011GA\u0001D\u0011\u0015af\u00011\u0001^\u0003\u00111'o\\7\u0011\u0007]qV&\u0003\u0002`#\t1q\n\u001d;j_:DQ!\u0019\u0004A\u0002u\u000bQ!\u001e8uS2\fQA]1oO\u0016$2\u0001\u00173f\u0011\u0015av\u00011\u0001.\u0011\u0015\tw\u00011\u0001.)\tAv\rC\u0003]\u0011\u0001\u0007Q\u0006\u000b\u0004\t'2Kw\nU\u0011\u0002U\u0006iQk]3!e\u0006tw-\u001a$s_6\f\u0011B]1oO\u00164%o\\7\u0015\u0005ak\u0007\"\u0002/\n\u0001\u0004iCC\u0001-p\u0011\u0015\t'\u00021\u0001.Q\u0019Q1\u000bT9P!\u0006\n!/\u0001\bVg\u0016\u0004#/\u00198hKVsG/\u001b7\u0002\u0015I\fgnZ3V]RLG\u000e\u0006\u0002Yk\")\u0011m\u0003a\u0001[\u0005\u0011Ao\u001c\u000b\u00031bDQA\u001e\u0007A\u00025Bc\u0001D*Mu>\u0003\u0016%A>\u0002\u0017U\u001bX\r\t:b]\u001e,Gk\\\u0001\be\u0006tw-\u001a+p)\tAf\u0010C\u0003w\u001b\u0001\u0007Q\u0006")
public interface SortedOps<A, C> {
    public Ordering<A> ordering();

    public A firstKey();

    public A lastKey();

    public static /* synthetic */ int compare$(SortedOps $this, Object k0, Object k1) {
        return $this.compare(k0, k1);
    }

    default public int compare(A k0, A k1) {
        return this.ordering().compare(k0, k1);
    }

    public C rangeImpl(Option<A> var1, Option<A> var2);

    public static /* synthetic */ Object range$(SortedOps $this, Object from, Object until) {
        return $this.range(from, until);
    }

    default public C range(A from, A until) {
        return this.rangeImpl(new Some<A>(from), new Some<A>(until));
    }

    public static /* synthetic */ Object from$(SortedOps $this, Object from) {
        return $this.from(from);
    }

    default public C from(A from) {
        return this.rangeFrom(from);
    }

    public static /* synthetic */ Object rangeFrom$(SortedOps $this, Object from) {
        return $this.rangeFrom(from);
    }

    default public C rangeFrom(A from) {
        return this.rangeImpl(new Some<A>(from), None$.MODULE$);
    }

    public static /* synthetic */ Object until$(SortedOps $this, Object until) {
        return $this.until(until);
    }

    default public C until(A until) {
        return this.rangeUntil(until);
    }

    public static /* synthetic */ Object rangeUntil$(SortedOps $this, Object until) {
        return $this.rangeUntil(until);
    }

    default public C rangeUntil(A until) {
        return this.rangeImpl(None$.MODULE$, new Some<A>(until));
    }

    public static /* synthetic */ Object to$(SortedOps $this, Object to) {
        return $this.to(to);
    }

    default public C to(A to) {
        return this.rangeTo(to);
    }

    public C rangeTo(A var1);

    public static void $init$(SortedOps $this) {
    }
}

