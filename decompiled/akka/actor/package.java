/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.ScalaActorRef;
import akka.actor.package$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q:Q!\u0002\u0004\t\u0002-1Q!\u0004\u0004\t\u00029AQ!F\u0001\u0005\u0002YAQaF\u0001\u0005\baAQaL\u0001\u0005\bA\nq\u0001]1dW\u0006<WM\u0003\u0002\b\u0011\u0005)\u0011m\u0019;pe*\t\u0011\"\u0001\u0003bW.\f7\u0001\u0001\t\u0003\u0019\u0005i\u0011A\u0002\u0002\ba\u0006\u001c7.Y4f'\t\tq\u0002\u0005\u0002\u0011'5\t\u0011CC\u0001\u0013\u0003\u0015\u00198-\u00197b\u0013\t!\u0012C\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003-\ta\"Y2u_J\u0014VM\u001a\u001aTG\u0006d\u0017\r\u0006\u0002\u001a9A\u0011ABG\u0005\u00037\u0019\u0011QbU2bY\u0006\f5\r^8s%\u00164\u0007\"B\u000f\u0004\u0001\u0004q\u0012a\u0001:fMB\u0011AbH\u0005\u0003A\u0019\u0011\u0001\"Q2u_J\u0014VM\u001a\u0015\u0003\u0007\t\u0002\"\u0001E\u0012\n\u0005\u0011\n\"AB5oY&tW\r\u000b\u0004\u0004M%RC&\f\t\u0003!\u001dJ!\u0001K\t\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u0003-\nq$[7qY&\u001c\u0017\u000e\u001e\u0011d_:4XM]:j_:\u0004\u0013n\u001d\u0011pEN|G.\u001a;f\u0003\u0015\u0019\u0018N\\2fC\u0005q\u0013A\u0002\u001a/m9\n4'\u0001\btG\u0006d\u0017MM!di>\u0014(+\u001a4\u0015\u0005y\t\u0004\"B\u000f\u0005\u0001\u0004I\u0002F\u0001\u0003#Q\u0019!a%\u000b\u0016-[\u0001")
public final class package {
    public static ActorRef scala2ActorRef(ScalaActorRef ref) {
        return package$.MODULE$.scala2ActorRef(ref);
    }

    public static ScalaActorRef actorRef2Scala(ActorRef ref) {
        return package$.MODULE$.actorRef2Scala(ref);
    }
}

