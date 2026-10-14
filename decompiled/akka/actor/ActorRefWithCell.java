/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.Cell;
import akka.actor.InternalActorRef;
import scala.collection.immutable.Iterable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q2q!\u0002\u0004\u0002\u0002!Qa\u0007C\u0003\u0010\u0001\u0011\u0005\u0011\u0003C\u0003\u0014\u0001\u0019\u0005A\u0003C\u0003\u0019\u0001\u0019\u0005\u0011\u0004C\u0003(\u0001\u0019\u0005\u0001F\u0001\tBGR|'OU3g/&$\bnQ3mY*\u0011q\u0001C\u0001\u0006C\u000e$xN\u001d\u0006\u0002\u0013\u0005!\u0011m[6b'\t\u00011\u0002\u0005\u0002\r\u001b5\ta!\u0003\u0002\u000f\r\t\u0001\u0012J\u001c;fe:\fG.Q2u_J\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\t!\u0003\u0005\u0002\r\u0001\u0005QQO\u001c3fe2L\u0018N\\4\u0016\u0003U\u0001\"\u0001\u0004\f\n\u0005]1!\u0001B\"fY2\f\u0001b\u00195jY\u0012\u0014XM\\\u000b\u00025A\u00191D\t\u0013\u000e\u0003qQ!!\b\u0010\u0002\u0013%lW.\u001e;bE2,'BA\u0010!\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002C\u0005)1oY1mC&\u00111\u0005\b\u0002\t\u0013R,'/\u00192mKB\u0011A\"J\u0005\u0003M\u0019\u0011\u0001\"Q2u_J\u0014VMZ\u0001\u000fO\u0016$8+\u001b8hY\u0016\u001c\u0005.\u001b7e)\tY\u0011\u0006C\u0003+\t\u0001\u00071&\u0001\u0003oC6,\u0007C\u0001\u00174\u001d\ti\u0013\u0007\u0005\u0002/A5\tqF\u0003\u00021!\u00051AH]8pizJ!A\r\u0011\u0002\rA\u0013X\rZ3g\u0013\t!TG\u0001\u0004TiJLgn\u001a\u0006\u0003e\u0001\u00122a\u000e\n:\r\u0011A\u0004\u0001\u0001\u001c\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \u0011\u00051Q\u0014BA\u001e\u0007\u00055\t5\r^8s%\u001647kY8qK\u0002")
public abstract class ActorRefWithCell
extends InternalActorRef {
    public abstract Cell underlying();

    public abstract Iterable<ActorRef> children();

    public abstract InternalActorRef getSingleChild(String var1);
}

