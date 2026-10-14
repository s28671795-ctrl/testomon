/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.converterImpl;

import scala.collection.IterableOnce;
import scala.compat.java8.converterImpl.Priority3AccumulatorConverters;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u3qAB\u0004\u0011\u0002\u0007\u0005\u0001\u0003C\u0003\u001a\u0001\u0011\u0005!\u0004C\u0003\u001f\u0001\u0011\rq\u0004C\u00035\u0001\u0011\rQ\u0007C\u0003?\u0001\u0011\rq\bC\u0003I\u0001\u0011\r\u0011J\u0001\u0010Qe&|'/\u001b;ze\u0005\u001b7-^7vY\u0006$xN]\"p]Z,'\u000f^3sg*\u0011\u0001\"C\u0001\u000eG>tg/\u001a:uKJLU\u000e\u001d7\u000b\u0005)Y\u0011!\u00026bm\u0006D$B\u0001\u0007\u000e\u0003\u0019\u0019w.\u001c9bi*\ta\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0007\u0001\tR\u0003\u0005\u0002\u0013'5\tQ\"\u0003\u0002\u0015\u001b\t1\u0011I\\=SK\u001a\u0004\"AF\f\u000e\u0003\u001dI!\u0001G\u0004\u0003=A\u0013\u0018n\u001c:jif\u001c\u0014iY2v[Vd\u0017\r^8s\u0007>tg/\u001a:uKJ\u001c\u0018A\u0002\u0013j]&$H\u0005F\u0001\u001c!\t\u0011B$\u0003\u0002\u001e\u001b\t!QK\\5u\u0003i\t7mY;nk2\fG/\u001a#pk\ndWmQ8mY\u0016\u001cG/[8o)\t\u00013\u0005\u0005\u0002\u0017C%\u0011!e\u0002\u0002\u001b\u0003\u000e\u001cW/\\;mCR,Gi\\;cY\u0016\u001cu\u000e\u001c7fGRLwN\u001c\u0005\u0006I\t\u0001\r!J\u0001\u000bk:$WM\u001d7zS:<\u0007c\u0001\u0014/c9\u0011q\u0005\f\b\u0003Q-j\u0011!\u000b\u0006\u0003U=\ta\u0001\u0010:p_Rt\u0014\"\u0001\b\n\u00055j\u0011a\u00029bG.\fw-Z\u0005\u0003_A\u0012A\"\u0013;fe\u0006\u0014G.Z(oG\u0016T!!L\u0007\u0011\u0005I\u0011\u0014BA\u001a\u000e\u0005\u0019!u.\u001e2mK\u00069\u0012mY2v[Vd\u0017\r^3J]R\u001cu\u000e\u001c7fGRLwN\u001c\u000b\u0003me\u0002\"AF\u001c\n\u0005a:!aF!dGVlW\u000f\\1uK&sGoQ8mY\u0016\u001cG/[8o\u0011\u0015!3\u00011\u0001;!\r1cf\u000f\t\u0003%qJ!!P\u0007\u0003\u0007%sG/\u0001\rbG\u000e,X.\u001e7bi\u0016duN\\4D_2dWm\u0019;j_:$\"\u0001Q\"\u0011\u0005Y\t\u0015B\u0001\"\b\u0005a\t5mY;nk2\fG/\u001a'p]\u001e\u001cu\u000e\u001c7fGRLwN\u001c\u0005\u0006I\u0011\u0001\r\u0001\u0012\t\u0004M9*\u0005C\u0001\nG\u0013\t9UB\u0001\u0003M_:<\u0017AE1dGVlW\u000f\\1uK\u0006s\u00170\u0011:sCf,\"A\u0013)\u0015\u0005-K\u0006c\u0001\fM\u001d&\u0011Qj\u0002\u0002\u0013\u0003\u000e\u001cW/\\;mCR,\u0017I\\=BeJ\f\u0017\u0010\u0005\u0002P!2\u0001A!B)\u0006\u0005\u0004\u0011&!A!\u0012\u0005M3\u0006C\u0001\nU\u0013\t)VBA\u0004O_RD\u0017N\\4\u0011\u0005I9\u0016B\u0001-\u000e\u0005\r\te.\u001f\u0005\u0006I\u0015\u0001\rA\u0017\t\u0004%ms\u0015B\u0001/\u000e\u0005\u0015\t%O]1z\u0001")
public interface Priority2AccumulatorConverters
extends Priority3AccumulatorConverters {
    public static /* synthetic */ IterableOnce accumulateDoubleCollection$(Priority2AccumulatorConverters $this, IterableOnce underlying) {
        return $this.accumulateDoubleCollection(underlying);
    }

    default public IterableOnce<Object> accumulateDoubleCollection(IterableOnce<Object> underlying) {
        return underlying;
    }

    public static /* synthetic */ IterableOnce accumulateIntCollection$(Priority2AccumulatorConverters $this, IterableOnce underlying) {
        return $this.accumulateIntCollection(underlying);
    }

    default public IterableOnce<Object> accumulateIntCollection(IterableOnce<Object> underlying) {
        return underlying;
    }

    public static /* synthetic */ IterableOnce accumulateLongCollection$(Priority2AccumulatorConverters $this, IterableOnce underlying) {
        return $this.accumulateLongCollection(underlying);
    }

    default public IterableOnce<Object> accumulateLongCollection(IterableOnce<Object> underlying) {
        return underlying;
    }

    public static /* synthetic */ Object accumulateAnyArray$(Priority2AccumulatorConverters $this, Object underlying) {
        return $this.accumulateAnyArray(underlying);
    }

    default public <A> Object accumulateAnyArray(Object underlying) {
        return underlying;
    }

    public static void $init$(Priority2AccumulatorConverters $this) {
    }
}

