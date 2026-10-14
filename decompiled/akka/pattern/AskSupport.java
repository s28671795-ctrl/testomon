/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.Actor$;
import akka.actor.ActorRef;
import akka.actor.ActorRef$;
import akka.actor.ActorSelection;
import akka.pattern.AskableActorRef$;
import akka.pattern.AskableActorSelection$;
import akka.util.Timeout;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u001d4qAC\u0006\u0011\u0002\u0007\u0005\u0001\u0003C\u0003\u0018\u0001\u0011\u0005\u0001\u0004C\u0003\u001d\u0001\u0011\rQ\u0004C\u0003\u001d\u0001\u0011\u0005!\u0006C\u0003\u001d\u0001\u0011\u0005\u0001\tC\u0003H\u0001\u0011\u0005\u0001\nC\u0003H\u0001\u0011\u0005Q\nC\u0003\u001d\u0001\u0011\r1\u000bC\u0003\u001d\u0001\u0011\u0005A\fC\u0003\u001d\u0001\u0011\u0005\u0011M\u0001\u0006Bg.\u001cV\u000f\u001d9peRT!\u0001D\u0007\u0002\u000fA\fG\u000f^3s]*\ta\"\u0001\u0003bW.\f7\u0001A\n\u0003\u0001E\u0001\"AE\u000b\u000e\u0003MQ\u0011\u0001F\u0001\u0006g\u000e\fG.Y\u0005\u0003-M\u0011a!\u00118z%\u00164\u0017A\u0002\u0013j]&$H\u0005F\u0001\u001a!\t\u0011\"$\u0003\u0002\u001c'\t!QK\\5u\u0003\r\t7o\u001b\u000b\u0003=\t\u0002\"a\b\u0011\u000e\u0003-I!!I\u0006\u0003\u001f\u0005\u001b8.\u00192mK\u0006\u001bGo\u001c:SK\u001aDQa\t\u0002A\u0002\u0011\n\u0001\"Y2u_J\u0014VM\u001a\t\u0003K!j\u0011A\n\u0006\u0003O5\tQ!Y2u_JL!!\u000b\u0014\u0003\u0011\u0005\u001bGo\u001c:SK\u001a$2aK\u001f?)\taS\u0007E\u0002.aIj\u0011A\f\u0006\u0003_M\t!bY8oGV\u0014(/\u001a8u\u0013\t\tdF\u0001\u0004GkR,(/\u001a\t\u0003%MJ!\u0001N\n\u0003\u0007\u0005s\u0017\u0010C\u00037\u0007\u0001\u000fq'A\u0004uS6,w.\u001e;\u0011\u0005aZT\"A\u001d\u000b\u0005ij\u0011\u0001B;uS2L!\u0001P\u001d\u0003\u000fQKW.Z8vi\")1e\u0001a\u0001I!)qh\u0001a\u0001e\u00059Q.Z:tC\u001e,G\u0003B!D\t\u0016#\"\u0001\f\"\t\u000bY\"\u00019A\u001c\t\u000b\r\"\u0001\u0019\u0001\u0013\t\u000b}\"\u0001\u0019\u0001\u001a\t\u000b\u0019#\u0001\u0019\u0001\u0013\u0002\rM,g\u000eZ3s\u00035\t7o[,ji\"\u001cF/\u0019;vgR\u0019\u0011j\u0013'\u0015\u00051R\u0005\"\u0002\u001c\u0006\u0001\b9\u0004\"B\u0012\u0006\u0001\u0004!\u0003\"B \u0006\u0001\u0004\u0011D\u0003\u0002(Q#J#\"\u0001L(\t\u000bY2\u00019A\u001c\t\u000b\r2\u0001\u0019\u0001\u0013\t\u000b}2\u0001\u0019\u0001\u001a\t\u000b\u00193\u0001\u0019\u0001\u0013\u0015\u0005Q;\u0006CA\u0010V\u0013\t16BA\u000bBg.\f'\r\\3BGR|'oU3mK\u000e$\u0018n\u001c8\t\u000ba;\u0001\u0019A-\u0002\u001d\u0005\u001cGo\u001c:TK2,7\r^5p]B\u0011QEW\u0005\u00037\u001a\u0012a\"Q2u_J\u001cV\r\\3di&|g\u000eF\u0002^?\u0002$\"\u0001\f0\t\u000bYB\u00019A\u001c\t\u000baC\u0001\u0019A-\t\u000b}B\u0001\u0019\u0001\u001a\u0015\t\t$WM\u001a\u000b\u0003Y\rDQAN\u0005A\u0004]BQ\u0001W\u0005A\u0002eCQaP\u0005A\u0002IBQAR\u0005A\u0002\u0011\u0002")
public interface AskSupport {
    public static /* synthetic */ ActorRef ask$(AskSupport $this, ActorRef actorRef2) {
        return $this.ask(actorRef2);
    }

    default public ActorRef ask(ActorRef actorRef2) {
        return actorRef2;
    }

    public static /* synthetic */ Future ask$(AskSupport $this, ActorRef actorRef2, Object message, Timeout timeout2) {
        return $this.ask(actorRef2, message, timeout2);
    }

    default public Future<Object> ask(ActorRef actorRef2, Object message, Timeout timeout2) {
        return AskableActorRef$.MODULE$.internalAsk$extension(this.ask(actorRef2), message, timeout2, ActorRef$.MODULE$.noSender());
    }

    public static /* synthetic */ Future ask$(AskSupport $this, ActorRef actorRef2, Object message, ActorRef sender, Timeout timeout2) {
        return $this.ask(actorRef2, message, sender, timeout2);
    }

    default public Future<Object> ask(ActorRef actorRef2, Object message, ActorRef sender, Timeout timeout2) {
        return AskableActorRef$.MODULE$.internalAsk$extension(this.ask(actorRef2), message, timeout2, sender);
    }

    public static /* synthetic */ Future askWithStatus$(AskSupport $this, ActorRef actorRef2, Object message, Timeout timeout2) {
        return $this.askWithStatus(actorRef2, message, timeout2);
    }

    default public Future<Object> askWithStatus(ActorRef actorRef2, Object message, Timeout timeout2) {
        return AskableActorRef$.MODULE$.internalAskWithStatus$extension(this.ask(actorRef2), message, timeout2, Actor$.MODULE$.noSender());
    }

    public static /* synthetic */ Future askWithStatus$(AskSupport $this, ActorRef actorRef2, Object message, ActorRef sender, Timeout timeout2) {
        return $this.askWithStatus(actorRef2, message, sender, timeout2);
    }

    default public Future<Object> askWithStatus(ActorRef actorRef2, Object message, ActorRef sender, Timeout timeout2) {
        return AskableActorRef$.MODULE$.internalAskWithStatus$extension(this.ask(actorRef2), message, timeout2, sender);
    }

    public static /* synthetic */ ActorSelection ask$(AskSupport $this, ActorSelection actorSelection) {
        return $this.ask(actorSelection);
    }

    default public ActorSelection ask(ActorSelection actorSelection) {
        return actorSelection;
    }

    public static /* synthetic */ Future ask$(AskSupport $this, ActorSelection actorSelection, Object message, Timeout timeout2) {
        return $this.ask(actorSelection, message, timeout2);
    }

    default public Future<Object> ask(ActorSelection actorSelection, Object message, Timeout timeout2) {
        return AskableActorSelection$.MODULE$.internalAsk$extension(this.ask(actorSelection), message, timeout2, ActorRef$.MODULE$.noSender());
    }

    public static /* synthetic */ Future ask$(AskSupport $this, ActorSelection actorSelection, Object message, ActorRef sender, Timeout timeout2) {
        return $this.ask(actorSelection, message, sender, timeout2);
    }

    default public Future<Object> ask(ActorSelection actorSelection, Object message, ActorRef sender, Timeout timeout2) {
        return AskableActorSelection$.MODULE$.internalAsk$extension(this.ask(actorSelection), message, timeout2, sender);
    }

    public static void $init$(AskSupport $this) {
    }
}

