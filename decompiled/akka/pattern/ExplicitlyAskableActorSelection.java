/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.pattern.ExplicitlyAskableActorSelection$;
import akka.util.Timeout;
import scala.Function1;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005=c\u0001B\u000b\u0017\u0005mA\u0001B\t\u0001\u0003\u0006\u0004%\ta\t\u0005\tU\u0001\u0011\t\u0011)A\u0005I!)1\u0006\u0001C\u0001Y!)\u0001\u0007\u0001C\u0001c!9a\nAI\u0001\n\u0003y\u0005\"B.\u0001\t\u0003a\u0006bB1\u0001#\u0003%\tA\u0019\u0005\u0007I\u0002!\tAF3\t\u000f)\u0004\u0011\u0011!C!W\"9q\u000eAA\u0001\n\u0003\u0002xa\u0002<\u0017\u0003\u0003E\ta\u001e\u0004\b+Y\t\t\u0011#\u0001y\u0011\u0015YC\u0002\"\u0001}\u0011\u0015iH\u0002\"\u0002\u007f\u0011%\ti\u0001DI\u0001\n\u000b\ty\u0001C\u0004\u0002\u00181!)!!\u0007\t\u000f\u0005\u001dB\u0002\"\u0002\u0002*!I\u0011Q\u0007\u0007\u0012\u0002\u0013\u0015\u0011q\u0007\u0005\n\u0003\u007fa\u0011\u0011!C\u0003\u0003\u0003B\u0011\"!\u0012\r\u0003\u0003%)!a\u0012\u0003?\u0015C\b\u000f\\5dSRd\u00170Q:lC\ndW-Q2u_J\u001cV\r\\3di&|gN\u0003\u0002\u00181\u00059\u0001/\u0019;uKJt'\"A\r\u0002\t\u0005\\7.Y\u0002\u0001'\t\u0001A\u0004\u0005\u0002\u001eA5\taDC\u0001 \u0003\u0015\u00198-\u00197b\u0013\t\tcD\u0001\u0004B]f4\u0016\r\\\u0001\tC\u000e$xN]*fYV\tA\u0005\u0005\u0002&Q5\taE\u0003\u0002(1\u0005)\u0011m\u0019;pe&\u0011\u0011F\n\u0002\u000f\u0003\u000e$xN]*fY\u0016\u001cG/[8o\u0003%\t7\r^8s'\u0016d\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0003[=\u0002\"A\f\u0001\u000e\u0003YAQAI\u0002A\u0002\u0011\n1!Y:l)\t\u0011\u0014\nF\u00024y\u0011\u00032\u0001N\u001c:\u001b\u0005)$B\u0001\u001c\u001f\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0003qU\u0012aAR;ukJ,\u0007CA\u000f;\u0013\tYdDA\u0002B]fDQ!\u0010\u0003A\u0004y\nq\u0001^5nK>,H\u000f\u0005\u0002@\u00056\t\u0001I\u0003\u0002B1\u0005!Q\u000f^5m\u0013\t\u0019\u0005IA\u0004US6,w.\u001e;\t\u000f\u0015#\u0001\u0013!a\u0002\r\u000611/\u001a8eKJ\u0004\"!J$\n\u0005!3#\u0001C!di>\u0014(+\u001a4\t\u000b)#\u0001\u0019A&\u0002\u000f5,7o]1hKB!Q\u0004\u0014$:\u0013\tieDA\u0005Gk:\u001cG/[8oc\u0005i\u0011m]6%I\u00164\u0017-\u001e7uIM\"\"\u0001\u0015.+\u0005\u0019\u000b6&\u0001*\u0011\u0005MCV\"\u0001+\u000b\u0005U3\u0016!C;oG\",7m[3e\u0015\t9f$\u0001\u0006b]:|G/\u0019;j_:L!!\u0017+\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW\rC\u0003K\u000b\u0001\u00071*\u0001\u0004%c6\f'o\u001b\u000b\u0003;\u0002$2a\r0`\u0011\u0015id\u0001q\u0001?\u0011\u001d)e\u0001%AA\u0004\u0019CQA\u0013\u0004A\u0002-\u000b\u0001\u0003J9nCJ\\G\u0005Z3gCVdG\u000fJ\u001a\u0015\u0005A\u001b\u0007\"\u0002&\b\u0001\u0004Y\u0015aC5oi\u0016\u0014h.\u00197Bg.$Ba\r4iS\")q\r\u0003a\u0001\u0017\u0006qQ.Z:tC\u001e,g)Y2u_JL\b\"B\u001f\t\u0001\u0004q\u0004\"B#\t\u0001\u00041\u0015\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u00031\u0004\"!H7\n\u00059t\"aA%oi\u00061Q-];bYN$\"!\u001d;\u0011\u0005u\u0011\u0018BA:\u001f\u0005\u001d\u0011un\u001c7fC:Dq!\u001e\u0006\u0002\u0002\u0003\u0007\u0011(A\u0002yIE\nq$\u0012=qY&\u001c\u0017\u000e\u001e7z\u0003N\\\u0017M\u00197f\u0003\u000e$xN]*fY\u0016\u001cG/[8o!\tqCb\u0005\u0002\rsB\u0011QD_\u0005\u0003wz\u0011a!\u00118z%\u00164G#A<\u0002\u001b\u0005\u001c8\u000eJ3yi\u0016t7/[8o)\ry\u0018\u0011\u0002\u000b\u0005\u0003\u0003\t9\u0001F\u00034\u0003\u0007\t)\u0001C\u0003>\u001d\u0001\u000fa\bC\u0004F\u001dA\u0005\t9\u0001$\t\u000b)s\u0001\u0019A&\t\r\u0005-a\u00021\u0001.\u0003\u0015!C\u000f[5t\u0003]\t7o\u001b\u0013eK\u001a\fW\u000f\u001c;%g\u0011*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002\u0012\u0005UAc\u0001)\u0002\u0014!)!j\u0004a\u0001\u0017\"1\u00111B\bA\u00025\n\u0001\u0003J9nCJ\\G%\u001a=uK:\u001c\u0018n\u001c8\u0015\t\u0005m\u0011Q\u0005\u000b\u0005\u0003;\t\u0019\u0003F\u00034\u0003?\t\t\u0003C\u0003>!\u0001\u000fa\bC\u0004F!A\u0005\t9\u0001$\t\u000b)\u0003\u0002\u0019A&\t\r\u0005-\u0001\u00031\u0001.\u0003UIg\u000e^3s]\u0006d\u0017i]6%Kb$XM\\:j_:$B!a\u000b\u00024Q91'!\f\u00020\u0005E\u0002\"B4\u0012\u0001\u0004Y\u0005\"B\u001f\u0012\u0001\u0004q\u0004\"B#\u0012\u0001\u00041\u0005BBA\u0006#\u0001\u0007Q&\u0001\u000e%c6\f'o\u001b\u0013eK\u001a\fW\u000f\u001c;%g\u0011*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002:\u0005uBc\u0001)\u0002<!)!J\u0005a\u0001\u0017\"1\u00111\u0002\nA\u00025\n!\u0003[1tQ\u000e{G-\u001a\u0013fqR,gn]5p]R\u00191.a\u0011\t\r\u0005-1\u00031\u0001.\u0003A)\u0017/^1mg\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002J\u00055CcA9\u0002L!9Q\u000fFA\u0001\u0002\u0004I\u0004BBA\u0006)\u0001\u0007Q\u0006")
public final class ExplicitlyAskableActorSelection {
    private final ActorSelection actorSel;

    public static boolean equals$extension(ActorSelection $this, Object x$1) {
        return ExplicitlyAskableActorSelection$.MODULE$.equals$extension($this, x$1);
    }

    public static int hashCode$extension(ActorSelection $this) {
        return ExplicitlyAskableActorSelection$.MODULE$.hashCode$extension($this);
    }

    public static ActorRef $qmark$default$3$extension(ActorSelection $this, Function1<ActorRef, Object> message) {
        return ExplicitlyAskableActorSelection$.MODULE$.$qmark$default$3$extension($this, message);
    }

    public static Future<Object> internalAsk$extension(ActorSelection $this, Function1<ActorRef, Object> messageFactory, Timeout timeout2, ActorRef sender) {
        return ExplicitlyAskableActorSelection$.MODULE$.internalAsk$extension($this, messageFactory, timeout2, sender);
    }

    public static Future<Object> $qmark$extension(ActorSelection $this, Function1<ActorRef, Object> message, Timeout timeout2, ActorRef sender) {
        return ExplicitlyAskableActorSelection$.MODULE$.$qmark$extension($this, message, timeout2, sender);
    }

    public static ActorRef ask$default$3$extension(ActorSelection $this, Function1<ActorRef, Object> message) {
        return ExplicitlyAskableActorSelection$.MODULE$.ask$default$3$extension($this, message);
    }

    public static Future<Object> ask$extension(ActorSelection $this, Function1<ActorRef, Object> message, Timeout timeout2, ActorRef sender) {
        return ExplicitlyAskableActorSelection$.MODULE$.ask$extension($this, message, timeout2, sender);
    }

    public ActorSelection actorSel() {
        return this.actorSel;
    }

    public Future<Object> ask(Function1<ActorRef, Object> message, Timeout timeout2, ActorRef sender) {
        return ExplicitlyAskableActorSelection$.MODULE$.ask$extension(this.actorSel(), message, timeout2, sender);
    }

    public ActorRef ask$default$3(Function1<ActorRef, Object> message) {
        return ExplicitlyAskableActorSelection$.MODULE$.ask$default$3$extension(this.actorSel(), message);
    }

    public Future<Object> $qmark(Function1<ActorRef, Object> message, Timeout timeout2, ActorRef sender) {
        return ExplicitlyAskableActorSelection$.MODULE$.$qmark$extension(this.actorSel(), message, timeout2, sender);
    }

    public Future<Object> internalAsk(Function1<ActorRef, Object> messageFactory, Timeout timeout2, ActorRef sender) {
        return ExplicitlyAskableActorSelection$.MODULE$.internalAsk$extension(this.actorSel(), messageFactory, timeout2, sender);
    }

    public ActorRef $qmark$default$3(Function1<ActorRef, Object> message) {
        return ExplicitlyAskableActorSelection$.MODULE$.$qmark$default$3$extension(this.actorSel(), message);
    }

    public int hashCode() {
        return ExplicitlyAskableActorSelection$.MODULE$.hashCode$extension(this.actorSel());
    }

    public boolean equals(Object x$1) {
        return ExplicitlyAskableActorSelection$.MODULE$.equals$extension(this.actorSel(), x$1);
    }

    public ExplicitlyAskableActorSelection(ActorSelection actorSel) {
        this.actorSel = actorSel;
    }
}

