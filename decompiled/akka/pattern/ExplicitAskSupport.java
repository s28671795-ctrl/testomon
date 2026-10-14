/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.actor.ActorRef$;
import akka.actor.ActorSelection;
import akka.pattern.ExplicitlyAskableActorRef$;
import akka.pattern.ExplicitlyAskableActorSelection$;
import akka.util.Timeout;
import scala.Function1;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q3q\u0001C\u0005\u0011\u0002\u0007\u0005a\u0002C\u0003\u0016\u0001\u0011\u0005a\u0003C\u0003\u001b\u0001\u0011\r1\u0004C\u0003\u001b\u0001\u0011\u0005\u0001\u0006C\u0003\u001b\u0001\u0011\u0005\u0011\tC\u0003\u001b\u0001\u0011\r\u0001\nC\u0003\u001b\u0001\u0011\u0005\u0011\u000bC\u0003\u001b\u0001\u0011\u0005aK\u0001\nFqBd\u0017nY5u\u0003N\\7+\u001e9q_J$(B\u0001\u0006\f\u0003\u001d\u0001\u0018\r\u001e;fe:T\u0011\u0001D\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0005\u0001y\u0001C\u0001\t\u0014\u001b\u0005\t\"\"\u0001\n\u0002\u000bM\u001c\u0017\r\\1\n\u0005Q\t\"AB!osJ+g-\u0001\u0004%S:LG\u000f\n\u000b\u0002/A\u0011\u0001\u0003G\u0005\u00033E\u0011A!\u00168ji\u0006\u0019\u0011m]6\u0015\u0005q\u0001\u0003CA\u000f\u001f\u001b\u0005I\u0011BA\u0010\n\u0005e)\u0005\u0010\u001d7jG&$H._!tW\u0006\u0014G.Z!di>\u0014(+\u001a4\t\u000b\u0005\u0012\u0001\u0019\u0001\u0012\u0002\u0011\u0005\u001cGo\u001c:SK\u001a\u0004\"a\t\u0014\u000e\u0003\u0011R!!J\u0006\u0002\u000b\u0005\u001cGo\u001c:\n\u0005\u001d\"#\u0001C!di>\u0014(+\u001a4\u0015\u0007%ZD\b\u0006\u0002+gA\u00191F\f\u0019\u000e\u00031R!!L\t\u0002\u0015\r|gnY;se\u0016tG/\u0003\u00020Y\t1a)\u001e;ve\u0016\u0004\"\u0001E\u0019\n\u0005I\n\"aA!os\")Ag\u0001a\u0002k\u00059A/[7f_V$\bC\u0001\u001c:\u001b\u00059$B\u0001\u001d\f\u0003\u0011)H/\u001b7\n\u0005i:$a\u0002+j[\u0016|W\u000f\u001e\u0005\u0006C\r\u0001\rA\t\u0005\u0006{\r\u0001\rAP\u0001\u000f[\u0016\u001c8/Y4f\r\u0006\u001cGo\u001c:z!\u0011\u0001rH\t\u0019\n\u0005\u0001\u000b\"!\u0003$v]\u000e$\u0018n\u001c82)\u0011\u0011E)\u0012$\u0015\u0005)\u001a\u0005\"\u0002\u001b\u0005\u0001\b)\u0004\"B\u0011\u0005\u0001\u0004\u0011\u0003\"B\u001f\u0005\u0001\u0004q\u0004\"B$\u0005\u0001\u0004\u0011\u0013AB:f]\u0012,'\u000f\u0006\u0002J\u0019B\u0011QDS\u0005\u0003\u0017&\u0011q$\u0012=qY&\u001c\u0017\u000e\u001e7z\u0003N\\\u0017M\u00197f\u0003\u000e$xN]*fY\u0016\u001cG/[8o\u0011\u0015iU\u00011\u0001O\u00039\t7\r^8s'\u0016dWm\u0019;j_:\u0004\"aI(\n\u0005A##AD!di>\u00148+\u001a7fGRLwN\u001c\u000b\u0004%R+FC\u0001\u0016T\u0011\u0015!d\u0001q\u00016\u0011\u0015ie\u00011\u0001O\u0011\u0015id\u00011\u0001?)\u00119\u0016LW.\u0015\u0005)B\u0006\"\u0002\u001b\b\u0001\b)\u0004\"B'\b\u0001\u0004q\u0005\"B\u001f\b\u0001\u0004q\u0004\"B$\b\u0001\u0004\u0011\u0003")
public interface ExplicitAskSupport {
    public static /* synthetic */ ActorRef ask$(ExplicitAskSupport $this, ActorRef actorRef2) {
        return $this.ask(actorRef2);
    }

    default public ActorRef ask(ActorRef actorRef2) {
        return actorRef2;
    }

    public static /* synthetic */ Future ask$(ExplicitAskSupport $this, ActorRef actorRef2, Function1 messageFactory, Timeout timeout2) {
        return $this.ask(actorRef2, (Function1<ActorRef, Object>)messageFactory, timeout2);
    }

    default public Future<Object> ask(ActorRef actorRef2, Function1<ActorRef, Object> messageFactory, Timeout timeout2) {
        return ExplicitlyAskableActorRef$.MODULE$.internalAsk$extension(this.ask(actorRef2), messageFactory, timeout2, ActorRef$.MODULE$.noSender());
    }

    public static /* synthetic */ Future ask$(ExplicitAskSupport $this, ActorRef actorRef2, Function1 messageFactory, ActorRef sender, Timeout timeout2) {
        return $this.ask(actorRef2, (Function1<ActorRef, Object>)messageFactory, sender, timeout2);
    }

    default public Future<Object> ask(ActorRef actorRef2, Function1<ActorRef, Object> messageFactory, ActorRef sender, Timeout timeout2) {
        return ExplicitlyAskableActorRef$.MODULE$.internalAsk$extension(this.ask(actorRef2), messageFactory, timeout2, sender);
    }

    public static /* synthetic */ ActorSelection ask$(ExplicitAskSupport $this, ActorSelection actorSelection) {
        return $this.ask(actorSelection);
    }

    default public ActorSelection ask(ActorSelection actorSelection) {
        return actorSelection;
    }

    public static /* synthetic */ Future ask$(ExplicitAskSupport $this, ActorSelection actorSelection, Function1 messageFactory, Timeout timeout2) {
        return $this.ask(actorSelection, (Function1<ActorRef, Object>)messageFactory, timeout2);
    }

    default public Future<Object> ask(ActorSelection actorSelection, Function1<ActorRef, Object> messageFactory, Timeout timeout2) {
        return ExplicitlyAskableActorSelection$.MODULE$.internalAsk$extension(this.ask(actorSelection), messageFactory, timeout2, ActorRef$.MODULE$.noSender());
    }

    public static /* synthetic */ Future ask$(ExplicitAskSupport $this, ActorSelection actorSelection, Function1 messageFactory, ActorRef sender, Timeout timeout2) {
        return $this.ask(actorSelection, (Function1<ActorRef, Object>)messageFactory, sender, timeout2);
    }

    default public Future<Object> ask(ActorSelection actorSelection, Function1<ActorRef, Object> messageFactory, ActorRef sender, Timeout timeout2) {
        return ExplicitlyAskableActorSelection$.MODULE$.internalAsk$extension(this.ask(actorSelection), messageFactory, timeout2, sender);
    }

    public static void $init$(ExplicitAskSupport $this) {
    }
}

