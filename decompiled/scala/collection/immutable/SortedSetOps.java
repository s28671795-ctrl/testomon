/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.immutable.Set;
import scala.collection.immutable.SetOps;
import scala.collection.immutable.SortedSet;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a2qAA\u0002\u0011\u0002G\u0005!\u0002C\u00036\u0001\u0019\u0005aG\u0001\u0007T_J$X\rZ*fi>\u00038O\u0003\u0002\u0005\u000b\u0005I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003\r\u001d\t!bY8mY\u0016\u001cG/[8o\u0015\u0005A\u0011!B:dC2\f7\u0001A\u000b\u0005\u0017YA3e\u0005\u0003\u0001\u0019A\u0011\u0004CA\u0007\u000f\u001b\u00059\u0011BA\b\b\u0005\u0019\te.\u001f*fMB)\u0011C\u0005\u000b E5\t1!\u0003\u0002\u0014\u0007\t11+\u001a;PaN\u0004\"!\u0006\f\r\u0001\u0011)q\u0003\u0001b\u00011\t\t\u0011)\u0005\u0002\u001a9A\u0011QBG\u0005\u00037\u001d\u0011qAT8uQ&tw\r\u0005\u0002\u000e;%\u0011ad\u0002\u0002\u0004\u0003:L\bCA\t!\u0013\t\t3AA\u0002TKR\u0004\"!F\u0012\u0005\r\u0011\u0002AQ1\u0001&\u0005\u0005\u0019\u0015CA\r'!\u0015\t\u0002\u0001F\u0014#!\t)\u0002\u0006\u0002\u0004*\u0001\u0011\u0015\rA\u000b\u0002\u0003\u0007\u000e+\"a\u000b\u0019\u0012\u0005ea\u0003cA\t._%\u0011af\u0001\u0002\n'>\u0014H/\u001a3TKR\u0004\"!\u0006\u0019\u0005\u000bEB#\u0019\u0001\r\u0003\u0003a\u0003Ra\r\u001b\u0015O\tj\u0011!B\u0005\u0003\u0005\u0015\t\u0001\"\u001e8t_J$X\rZ\u000b\u0002oA\u0019\u0011\u0003\t\u000b")
public interface SortedSetOps<A, CC extends SortedSet<Object>, C extends SortedSetOps<A, CC, C>>
extends SetOps<A, Set, C>,
scala.collection.SortedSetOps<A, CC, C> {
    @Override
    public Set<A> unsorted();
}

