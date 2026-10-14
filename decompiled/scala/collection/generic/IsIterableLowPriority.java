/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.generic;

import scala.collection.generic.IsIterable;
import scala.collection.generic.IsMap;
import scala.collection.generic.IsSeq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000593q\u0001B\u0003\u0011\u0002\u0007\u0005A\u0002C\u0003\u0012\u0001\u0011\u0005!\u0003C\u0003\u0017\u0001\u0011\rq\u0003C\u0003<\u0001\u0011\rAHA\u000bJg&#XM]1cY\u0016dun\u001e)sS>\u0014\u0018\u000e^=\u000b\u0005\u00199\u0011aB4f]\u0016\u0014\u0018n\u0019\u0006\u0003\u0011%\t!bY8mY\u0016\u001cG/[8o\u0015\u0005Q\u0011!B:dC2\f7\u0001A\n\u0003\u00015\u0001\"AD\b\u000e\u0003%I!\u0001E\u0005\u0003\r\u0005s\u0017PU3g\u0003\u0019!\u0013N\\5uIQ\t1\u0003\u0005\u0002\u000f)%\u0011Q#\u0003\u0002\u0005+:LG/A\njgN+\u0017\u000fT5lK&\u001b\u0018\n^3sC\ndW-\u0006\u0002\u0019EQ\u0011\u0011d\f\n\u00035q1Aa\u0007\u0001\u00013\taAH]3gS:,W.\u001a8u}A\u0019QD\b\u0011\u000e\u0003\u0015I!aH\u0003\u0003\u0015%\u001b\u0018\n^3sC\ndW\r\u0005\u0002\"E1\u0001A!B\u0012\u0003\u0005\u0004!#\u0001\u0002*faJ\f\"!\n\u0015\u0011\u000591\u0013BA\u0014\n\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"AD\u0015\n\u0005)J!aA!os\u0016!AF\u0007\u0001.\u0005\u0005\t\u0005C\u0001\u00185\u001d\t\ts\u0006C\u00031\u0005\u0001\u000f\u0011'A\u0005jgN+\u0017\u000fT5lKB\u0019QD\r\u0011\n\u0005M*!!B%t'\u0016\f\u0018B\u0001\u00176\u0013\t1TA\u0001\bJg&#XM]1cY\u0016|enY3\u0006\taR\u0002!\u000f\u0002\u0002\u0007B\u0011aFO\u0005\u0003qy\t1#[:NCBd\u0015n[3Jg&#XM]1cY\u0016,\"!\u0010\"\u0015\u0005y2%CA A\r\u0011Y\u0002\u0001\u0001 \u0011\u0007uq\u0012\t\u0005\u0002\"\u0005\u0012)1e\u0001b\u0001I\u0015!Af\u0010\u0001E!\t)5J\u0004\u0002\"\r\")qi\u0001a\u0002\u0011\u0006I\u0011n]'ba2K7.\u001a\t\u0004;%\u000b\u0015B\u0001&\u0006\u0005\u0015I5/T1q\u0013\ta\u0013*\u0002\u00039\u007f\u0001i\u0005CA#;\u0001")
public interface IsIterableLowPriority {
    public static /* synthetic */ IsIterable isSeqLikeIsIterable$(IsIterableLowPriority $this, IsSeq isSeqLike) {
        return $this.isSeqLikeIsIterable(isSeqLike);
    }

    default public <Repr> IsIterable<Repr> isSeqLikeIsIterable(IsSeq<Repr> isSeqLike) {
        return isSeqLike;
    }

    public static /* synthetic */ IsIterable isMapLikeIsIterable$(IsIterableLowPriority $this, IsMap isMapLike) {
        return $this.isMapLikeIsIterable(isMapLike);
    }

    default public <Repr> IsIterable<Repr> isMapLikeIsIterable(IsMap<Repr> isMapLike) {
        return isMapLike;
    }

    public static void $init$(IsIterableLowPriority $this) {
    }
}

