/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.generic;

import scala.collection.generic.IsIterable;
import scala.collection.generic.IsIterableOnce;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q2qa\u0001\u0003\u0011\u0002\u0007\u00051\u0002C\u0003\u0011\u0001\u0011\u0005\u0011\u0003C\u0003\u0016\u0001\u0011\raCA\rJg&#XM]1cY\u0016|enY3M_^\u0004&/[8sSRL(BA\u0003\u0007\u0003\u001d9WM\\3sS\u000eT!a\u0002\u0005\u0002\u0015\r|G\u000e\\3di&|gNC\u0001\n\u0003\u0015\u00198-\u00197b\u0007\u0001\u0019\"\u0001\u0001\u0007\u0011\u00055qQ\"\u0001\u0005\n\u0005=A!AB!osJ+g-\u0001\u0004%S:LG\u000f\n\u000b\u0002%A\u0011QbE\u0005\u0003)!\u0011A!\u00168ji\u0006a\u0012n]%uKJ\f'\r\\3MS.,\u0017j]%uKJ\f'\r\\3P]\u000e,WCA\f\")\tAbF\u0005\u0002\u001a7\u0019!!\u0004\u0001\u0001\u0019\u00051a$/\u001a4j]\u0016lWM\u001c;?!\raRdH\u0007\u0002\t%\u0011a\u0004\u0002\u0002\u000f\u0013NLE/\u001a:bE2,wJ\\2f!\t\u0001\u0013\u0005\u0004\u0001\u0005\u000b\t\u0012!\u0019A\u0012\u0003\tI+\u0007O]\t\u0003I\u001d\u0002\"!D\u0013\n\u0005\u0019B!a\u0002(pi\"Lgn\u001a\t\u0003\u001b!J!!\u000b\u0005\u0003\u0007\u0005s\u00170\u0002\u0003,3\u0001a#!A!\u0011\u00055\u001adB\u0001\u0011/\u0011\u0015y#\u0001q\u00011\u00039I7/\u0013;fe\u0006\u0014G.\u001a'jW\u0016\u00042\u0001H\u0019 \u0013\t\u0011DA\u0001\u0006Jg&#XM]1cY\u0016L!aK\u000f")
public interface IsIterableOnceLowPriority {
    public static /* synthetic */ IsIterableOnce isIterableLikeIsIterableOnce$(IsIterableOnceLowPriority $this, IsIterable isIterableLike) {
        return $this.isIterableLikeIsIterableOnce(isIterableLike);
    }

    default public <Repr> IsIterableOnce<Repr> isIterableLikeIsIterableOnce(IsIterable<Repr> isIterableLike) {
        return isIterableLike;
    }

    public static void $init$(IsIterableOnceLowPriority $this) {
    }
}

