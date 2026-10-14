/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.collection.mutable.Set;
import scala.collection.mutable.SetOps;
import scala.collection.mutable.SortedSet;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a2qAA\u0002\u0011\u0002G\u0005!\u0002C\u00036\u0001\u0019\u0005aG\u0001\u0007T_J$X\rZ*fi>\u00038O\u0003\u0002\u0005\u000b\u00059Q.\u001e;bE2,'B\u0001\u0004\b\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u0011\u0005)1oY1mC\u000e\u0001Q\u0003B\u0006\u0017Q\r\u001aB\u0001\u0001\u0007\u0011eA\u0011QBD\u0007\u0002\u000f%\u0011qb\u0002\u0002\u0007\u0003:L(+\u001a4\u0011\u000bE\u0011Bc\b\u0012\u000e\u0003\rI!aE\u0002\u0003\rM+Go\u00149t!\t)b\u0003\u0004\u0001\u0005\u000b]\u0001!\u0019\u0001\r\u0003\u0003\u0005\u000b\"!\u0007\u000f\u0011\u00055Q\u0012BA\u000e\b\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!D\u000f\n\u0005y9!aA!osB\u0011\u0011\u0003I\u0005\u0003C\r\u00111aU3u!\t)2\u0005\u0002\u0004%\u0001\u0011\u0015\r!\n\u0002\u0002\u0007F\u0011\u0011D\n\t\u0006#\u0001!rE\t\t\u0003+!\"a!\u000b\u0001\u0005\u0006\u0004Q#AA\"D+\tY\u0003'\u0005\u0002\u001aYA\u0019\u0011#L\u0018\n\u00059\u001a!!C*peR,GmU3u!\t)\u0002\u0007B\u00032Q\t\u0007\u0001DA\u0001Y!\u0015\u0019D\u0007F\u0014#\u001b\u0005)\u0011B\u0001\u0002\u0006\u0003!)hn]8si\u0016$W#A\u001c\u0011\u0007E\u0001C\u0003")
public interface SortedSetOps<A, CC extends SortedSet<Object>, C extends SortedSetOps<A, CC, C>>
extends SetOps<A, Set, C>,
scala.collection.SortedSetOps<A, CC, C> {
    @Override
    public Set<A> unsorted();
}

