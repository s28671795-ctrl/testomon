/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.pattern.ExplicitlyAskableActorRef$;
import akka.util.Timeout;
import scala.Function1;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005%c\u0001B\u000b\u0017\u0005mA\u0001B\t\u0001\u0003\u0006\u0004%\ta\t\u0005\tU\u0001\u0011\t\u0011)A\u0005I!)1\u0006\u0001C\u0001Y!)\u0001\u0007\u0001C\u0001c!91\nAI\u0001\n\u0003a\u0005\"\u0002-\u0001\t\u0003I\u0006b\u00020\u0001#\u0003%\ta\u0018\u0005\u0007C\u0002!\tA\u00062\t\u000f\u001d\u0004\u0011\u0011!C!Q\"9A\u000eAA\u0001\n\u0003jwaB:\u0017\u0003\u0003E\t\u0001\u001e\u0004\b+Y\t\t\u0011#\u0001v\u0011\u0015YC\u0002\"\u0001z\u0011\u0015QH\u0002\"\u0002|\u0011%\t9\u0001DI\u0001\n\u000b\tI\u0001C\u0004\u0002\u00121!)!a\u0005\t\u000f\u0005\u0005B\u0002\"\u0002\u0002$!I\u0011q\u0006\u0007\u0012\u0002\u0013\u0015\u0011\u0011\u0007\u0005\n\u0003sa\u0011\u0011!C\u0003\u0003wA\u0011\"a\u0010\r\u0003\u0003%)!!\u0011\u00033\u0015C\b\u000f\\5dSRd\u00170Q:lC\ndW-Q2u_J\u0014VM\u001a\u0006\u0003/a\tq\u0001]1ui\u0016\u0014hNC\u0001\u001a\u0003\u0011\t7n[1\u0004\u0001M\u0011\u0001\u0001\b\t\u0003;\u0001j\u0011A\b\u0006\u0002?\u0005)1oY1mC&\u0011\u0011E\b\u0002\u0007\u0003:Lh+\u00197\u0002\u0011\u0005\u001cGo\u001c:SK\u001a,\u0012\u0001\n\t\u0003K!j\u0011A\n\u0006\u0003Oa\tQ!Y2u_JL!!\u000b\u0014\u0003\u0011\u0005\u001bGo\u001c:SK\u001a\f\u0011\"Y2u_J\u0014VM\u001a\u0011\u0002\rqJg.\u001b;?)\tis\u0006\u0005\u0002/\u00015\ta\u0003C\u0003#\u0007\u0001\u0007A%A\u0002bg.$\"A\r$\u0015\u0007MbD\tE\u00025oej\u0011!\u000e\u0006\u0003my\t!bY8oGV\u0014(/\u001a8u\u0013\tATG\u0001\u0004GkR,(/\u001a\t\u0003;iJ!a\u000f\u0010\u0003\u0007\u0005s\u0017\u0010C\u0003>\t\u0001\u000fa(A\u0004uS6,w.\u001e;\u0011\u0005}\u0012U\"\u0001!\u000b\u0005\u0005C\u0012\u0001B;uS2L!a\u0011!\u0003\u000fQKW.Z8vi\"9Q\t\u0002I\u0001\u0002\b!\u0013AB:f]\u0012,'\u000fC\u0003H\t\u0001\u0007\u0001*A\u0004nKN\u001c\u0018mZ3\u0011\tuIE%O\u0005\u0003\u0015z\u0011\u0011BR;oGRLwN\\\u0019\u0002\u001b\u0005\u001c8\u000e\n3fM\u0006,H\u000e\u001e\u00134)\tiuK\u000b\u0002%\u001d.\nq\n\u0005\u0002Q+6\t\u0011K\u0003\u0002S'\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003)z\t!\"\u00198o_R\fG/[8o\u0013\t1\u0016KA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016DQaR\u0003A\u0002!\u000ba\u0001J9nCJ\\GC\u0001.^)\r\u00194\f\u0018\u0005\u0006{\u0019\u0001\u001dA\u0010\u0005\b\u000b\u001a\u0001\n\u0011q\u0001%\u0011\u00159e\u00011\u0001I\u0003A!\u0013/\\1sW\u0012\"WMZ1vYR$3\u0007\u0006\u0002NA\")qi\u0002a\u0001\u0011\u0006Y\u0011N\u001c;fe:\fG.Q:l)\u0011\u00194-\u001a4\t\u000b\u0011D\u0001\u0019\u0001%\u0002\u001d5,7o]1hK\u001a\u000b7\r^8ss\")Q\b\u0003a\u0001}!)Q\t\u0003a\u0001I\u0005A\u0001.Y:i\u0007>$W\rF\u0001j!\ti\".\u0003\u0002l=\t\u0019\u0011J\u001c;\u0002\r\u0015\fX/\u00197t)\tq\u0017\u000f\u0005\u0002\u001e_&\u0011\u0001O\b\u0002\b\u0005>|G.Z1o\u0011\u001d\u0011(\"!AA\u0002e\n1\u0001\u001f\u00132\u0003e)\u0005\u0010\u001d7jG&$H._!tW\u0006\u0014G.Z!di>\u0014(+\u001a4\u0011\u00059b1C\u0001\u0007w!\tir/\u0003\u0002y=\t1\u0011I\\=SK\u001a$\u0012\u0001^\u0001\u000eCN\\G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007q\f\u0019\u0001F\u0002~\u0003\u0003!2a\r@\u0000\u0011\u0015id\u0002q\u0001?\u0011\u001d)e\u0002%AA\u0004\u0011BQa\u0012\bA\u0002!Ca!!\u0002\u000f\u0001\u0004i\u0013!\u0002\u0013uQ&\u001c\u0018aF1tW\u0012\"WMZ1vYR$3\u0007J3yi\u0016t7/[8o)\u0011\tY!a\u0004\u0015\u00075\u000bi\u0001C\u0003H\u001f\u0001\u0007\u0001\n\u0003\u0004\u0002\u0006=\u0001\r!L\u0001\u0011IEl\u0017M]6%Kb$XM\\:j_:$B!!\u0006\u0002 Q!\u0011qCA\u000f)\u0015\u0019\u0014\u0011DA\u000e\u0011\u0015i\u0004\u0003q\u0001?\u0011\u001d)\u0005\u0003%AA\u0004\u0011BQa\u0012\tA\u0002!Ca!!\u0002\u0011\u0001\u0004i\u0013!F5oi\u0016\u0014h.\u00197Bg.$S\r\u001f;f]NLwN\u001c\u000b\u0005\u0003K\ti\u0003F\u00044\u0003O\tI#a\u000b\t\u000b\u0011\f\u0002\u0019\u0001%\t\u000bu\n\u0002\u0019\u0001 \t\u000b\u0015\u000b\u0002\u0019\u0001\u0013\t\r\u0005\u0015\u0011\u00031\u0001.\u0003i!\u0013/\\1sW\u0012\"WMZ1vYR$3\u0007J3yi\u0016t7/[8o)\u0011\t\u0019$a\u000e\u0015\u00075\u000b)\u0004C\u0003H%\u0001\u0007\u0001\n\u0003\u0004\u0002\u0006I\u0001\r!L\u0001\u0013Q\u0006\u001c\bnQ8eK\u0012*\u0007\u0010^3og&|g\u000eF\u0002i\u0003{Aa!!\u0002\u0014\u0001\u0004i\u0013\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o)\u0011\t\u0019%a\u0012\u0015\u00079\f)\u0005C\u0004s)\u0005\u0005\t\u0019A\u001d\t\r\u0005\u0015A\u00031\u0001.\u0001")
public final class ExplicitlyAskableActorRef {
    private final ActorRef actorRef;

    public static boolean equals$extension(ActorRef $this, Object x$1) {
        return ExplicitlyAskableActorRef$.MODULE$.equals$extension($this, x$1);
    }

    public static int hashCode$extension(ActorRef $this) {
        return ExplicitlyAskableActorRef$.MODULE$.hashCode$extension($this);
    }

    public static ActorRef $qmark$default$3$extension(ActorRef $this, Function1<ActorRef, Object> message) {
        return ExplicitlyAskableActorRef$.MODULE$.$qmark$default$3$extension($this, message);
    }

    public static Future<Object> internalAsk$extension(ActorRef $this, Function1<ActorRef, Object> messageFactory, Timeout timeout2, ActorRef sender) {
        return ExplicitlyAskableActorRef$.MODULE$.internalAsk$extension($this, messageFactory, timeout2, sender);
    }

    public static Future<Object> $qmark$extension(ActorRef $this, Function1<ActorRef, Object> message, Timeout timeout2, ActorRef sender) {
        return ExplicitlyAskableActorRef$.MODULE$.$qmark$extension($this, message, timeout2, sender);
    }

    public static ActorRef ask$default$3$extension(ActorRef $this, Function1<ActorRef, Object> message) {
        return ExplicitlyAskableActorRef$.MODULE$.ask$default$3$extension($this, message);
    }

    public static Future<Object> ask$extension(ActorRef $this, Function1<ActorRef, Object> message, Timeout timeout2, ActorRef sender) {
        return ExplicitlyAskableActorRef$.MODULE$.ask$extension($this, message, timeout2, sender);
    }

    public ActorRef actorRef() {
        return this.actorRef;
    }

    public Future<Object> ask(Function1<ActorRef, Object> message, Timeout timeout2, ActorRef sender) {
        return ExplicitlyAskableActorRef$.MODULE$.ask$extension(this.actorRef(), message, timeout2, sender);
    }

    public ActorRef ask$default$3(Function1<ActorRef, Object> message) {
        return ExplicitlyAskableActorRef$.MODULE$.ask$default$3$extension(this.actorRef(), message);
    }

    public Future<Object> $qmark(Function1<ActorRef, Object> message, Timeout timeout2, ActorRef sender) {
        return ExplicitlyAskableActorRef$.MODULE$.$qmark$extension(this.actorRef(), message, timeout2, sender);
    }

    public Future<Object> internalAsk(Function1<ActorRef, Object> messageFactory, Timeout timeout2, ActorRef sender) {
        return ExplicitlyAskableActorRef$.MODULE$.internalAsk$extension(this.actorRef(), messageFactory, timeout2, sender);
    }

    public ActorRef $qmark$default$3(Function1<ActorRef, Object> message) {
        return ExplicitlyAskableActorRef$.MODULE$.$qmark$default$3$extension(this.actorRef(), message);
    }

    public int hashCode() {
        return ExplicitlyAskableActorRef$.MODULE$.hashCode$extension(this.actorRef());
    }

    public boolean equals(Object x$1) {
        return ExplicitlyAskableActorRef$.MODULE$.equals$extension(this.actorRef(), x$1);
    }

    public ExplicitlyAskableActorRef(ActorRef actorRef2) {
        this.actorRef = actorRef2;
    }
}

