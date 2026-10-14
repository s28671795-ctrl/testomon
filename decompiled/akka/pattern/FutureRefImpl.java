/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.pattern.FutureRef;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005i2QAB\u0004\u0003\u0013-A\u0001b\t\u0001\u0003\u0006\u0004%\t\u0001\n\u0005\tW\u0001\u0011\t\u0011)A\u0005K!AA\u0006\u0001BC\u0002\u0013\u0005Q\u0006\u0003\u00055\u0001\t\u0005\t\u0015!\u0003/\u0011\u0015)\u0004\u0001\"\u00017\u000551U\u000f^;sKJ+g-S7qY*\u0011\u0001\"C\u0001\ba\u0006$H/\u001a:o\u0015\u0005Q\u0011\u0001B1lW\u0006,\"\u0001D\r\u0014\u0007\u0001i1\u0003\u0005\u0002\u000f#5\tqBC\u0001\u0011\u0003\u0015\u00198-\u00197b\u0013\t\u0011rB\u0001\u0004B]f\u0014VM\u001a\t\u0004)U9R\"A\u0004\n\u0005Y9!!\u0003$viV\u0014XMU3g!\tA\u0012\u0004\u0004\u0001\u0005\u000bi\u0001!\u0019\u0001\u000f\u0003\u0003Q\u001b\u0001!\u0005\u0002\u001eAA\u0011aBH\u0005\u0003?=\u0011qAT8uQ&tw\r\u0005\u0002\u000fC%\u0011!e\u0004\u0002\u0004\u0003:L\u0018a\u0001:fMV\tQ\u0005\u0005\u0002'S5\tqE\u0003\u0002)\u0013\u0005)\u0011m\u0019;pe&\u0011!f\n\u0002\t\u0003\u000e$xN\u001d*fM\u0006!!/\u001a4!\u0003\u00191W\u000f^;sKV\ta\u0006E\u00020e]i\u0011\u0001\r\u0006\u0003c=\t!bY8oGV\u0014(/\u001a8u\u0013\t\u0019\u0004G\u0001\u0004GkR,(/Z\u0001\bMV$XO]3!\u0003\u0019a\u0014N\\5u}Q\u0019q\u0007O\u001d\u0011\u0007Q\u0001q\u0003C\u0003$\u000b\u0001\u0007Q\u0005C\u0003-\u000b\u0001\u0007a\u0006")
public final class FutureRefImpl<T>
implements FutureRef<T> {
    private final ActorRef ref;
    private final Future<T> future;

    @Override
    public ActorRef ref() {
        return this.ref;
    }

    @Override
    public Future<T> future() {
        return this.future;
    }

    public FutureRefImpl(ActorRef ref, Future<T> future) {
        this.ref = ref;
        this.future = future;
    }
}

