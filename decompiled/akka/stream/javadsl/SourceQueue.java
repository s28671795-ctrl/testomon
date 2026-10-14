/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.Done;
import akka.stream.QueueOfferResult;
import java.util.concurrent.CompletionStage;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005]2qa\u0001\u0003\u0011\u0002G\u00051\u0002C\u0003\u0014\u0001\u0019\u0005A\u0003C\u00031\u0001\u0019\u0005\u0011GA\u0006T_V\u00148-Z)vKV,'BA\u0003\u0007\u0003\u001dQ\u0017M^1eg2T!a\u0002\u0005\u0002\rM$(/Z1n\u0015\u0005I\u0011\u0001B1lW\u0006\u001c\u0001!\u0006\u0002\rOM\u0011\u0001!\u0004\t\u0003\u001dEi\u0011a\u0004\u0006\u0002!\u0005)1oY1mC&\u0011!c\u0004\u0002\u0007\u0003:L(+\u001a4\u0002\u000b=4g-\u001a:\u0015\u0005U\u0019\u0003c\u0001\f\u001e?5\tqC\u0003\u0002\u00193\u0005Q1m\u001c8dkJ\u0014XM\u001c;\u000b\u0005iY\u0012\u0001B;uS2T\u0011\u0001H\u0001\u0005U\u00064\u0018-\u0003\u0002\u001f/\ty1i\\7qY\u0016$\u0018n\u001c8Ti\u0006<W\r\u0005\u0002!C5\ta!\u0003\u0002#\r\t\u0001\u0012+^3vK>3g-\u001a:SKN,H\u000e\u001e\u0005\u0006I\u0005\u0001\r!J\u0001\u0005K2,W\u000e\u0005\u0002'O1\u0001A!\u0002\u0015\u0001\u0005\u0004I#!\u0001+\u0012\u0005)j\u0003C\u0001\b,\u0013\tasBA\u0004O_RD\u0017N\\4\u0011\u00059q\u0013BA\u0018\u0010\u0005\r\te._\u0001\u0010o\u0006$8\r[\"p[BdW\r^5p]R\t!\u0007E\u0002\u0017;M\u0002\"\u0001N\u001b\u000e\u0003!I!A\u000e\u0005\u0003\t\u0011{g.\u001a")
public interface SourceQueue<T> {
    public CompletionStage<QueueOfferResult> offer(T var1);

    public CompletionStage<Done> watchCompletion();
}

