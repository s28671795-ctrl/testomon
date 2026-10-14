/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern.extended;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.pattern.extended.package$;
import akka.util.Timeout;
import scala.Function1;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m9Qa\u0001\u0003\t\u0002-1Q!\u0004\u0003\t\u00029AQ!G\u0001\u0005\u0002i\tq\u0001]1dW\u0006<WM\u0003\u0002\u0006\r\u0005AQ\r\u001f;f]\u0012,GM\u0003\u0002\b\u0011\u00059\u0001/\u0019;uKJt'\"A\u0005\u0002\t\u0005\\7.Y\u0002\u0001!\ta\u0011!D\u0001\u0005\u0005\u001d\u0001\u0018mY6bO\u0016\u001c2!A\b\u0016!\t\u00012#D\u0001\u0012\u0015\u0005\u0011\u0012!B:dC2\f\u0017B\u0001\u000b\u0012\u0005\u0019\te.\u001f*fMB\u0011acF\u0007\u0002\r%\u0011\u0001D\u0002\u0002\u0013\u000bb\u0004H.[2ji\u0006\u001b8nU;qa>\u0014H/\u0001\u0004=S:LGO\u0010\u000b\u0002\u0017\u0001")
public final class package {
    public static Future<Object> ask(ActorSelection actorSelection, Function1<ActorRef, Object> messageFactory, ActorRef sender, Timeout timeout2) {
        return package$.MODULE$.ask(actorSelection, messageFactory, sender, timeout2);
    }

    public static Future<Object> ask(ActorSelection actorSelection, Function1<ActorRef, Object> messageFactory, Timeout timeout2) {
        return package$.MODULE$.ask(actorSelection, messageFactory, timeout2);
    }

    public static ActorSelection ask(ActorSelection actorSelection) {
        return package$.MODULE$.ask(actorSelection);
    }

    public static Future<Object> ask(ActorRef actorRef2, Function1<ActorRef, Object> messageFactory, ActorRef sender, Timeout timeout2) {
        return package$.MODULE$.ask(actorRef2, messageFactory, sender, timeout2);
    }

    public static Future<Object> ask(ActorRef actorRef2, Function1<ActorRef, Object> messageFactory, Timeout timeout2) {
        return package$.MODULE$.ask(actorRef2, messageFactory, timeout2);
    }

    public static ActorRef ask(ActorRef actorRef2) {
        return package$.MODULE$.ask(actorRef2);
    }
}

