/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.Actor$;
import akka.actor.ActorKilledException$;
import akka.actor.ActorRef;
import akka.actor.ActorRef$;
import akka.actor.ActorRefProvider;
import akka.actor.Cancellable;
import akka.actor.Scheduler;
import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.pattern.AskTimeoutException;
import akka.pattern.PromiseActorRef;
import akka.util.Timeout;
import java.io.Serializable;
import scala.Function1;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.runtime.BoxedUnit;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;
import scala.util.Failure;
import scala.util.Try;

@InternalApi
public final class PromiseActorRef$
implements Serializable {
    public static final PromiseActorRef$ MODULE$ = new PromiseActorRef$();
    private static final Failure<Nothing$> akka$pattern$PromiseActorRef$$ActorStopResult = new Failure(ActorKilledException$.MODULE$.apply("Stopped"));
    private static final Function1<String, Throwable> defaultOnTimeout = (Function1<String, AskTimeoutException> & Serializable)str -> new AskTimeoutException((String)str);

    public Failure<Nothing$> akka$pattern$PromiseActorRef$$ActorStopResult() {
        return akka$pattern$PromiseActorRef$$ActorStopResult;
    }

    private Function1<String, Throwable> defaultOnTimeout() {
        return defaultOnTimeout;
    }

    public PromiseActorRef apply(ActorRefProvider provider, Timeout timeout2, Object targetName, String messageClassName, String refPathPrefix, ActorRef sender, Function1<String, Throwable> onTimeout) {
        if (refPathPrefix.indexOf(47) > -1) {
            throw new IllegalArgumentException(new StringBuilder(43).append("refPathPrefix must not contain slash, was: ").append(refPathPrefix).toString());
        }
        Promise<Object> result = Promise$.MODULE$.apply();
        Scheduler scheduler = provider.guardian().underlying().system().scheduler();
        PromiseActorRef a = new PromiseActorRef(provider, result, messageClassName, refPathPrefix);
        ExecutionContext ec = ExecutionContexts$.MODULE$.parasitic();
        Cancellable f = scheduler.scheduleOnce(timeout2.duration(), () -> {
            ActorRef actorRef2 = sender;
            ActorRef actorRef3 = ActorRef$.MODULE$.noSender();
            String wasSentBy = !(actorRef2 != null ? !((Object)actorRef2).equals(actorRef3) : actorRef3 != null) ? "" : new StringBuilder(15).append(" was sent by [").append(sender).append("]").toString();
            String messagePart = new StringBuilder(19).append("Message of type [").append(a.messageClassName()).append("]").append(wasSentBy).append(".").toString();
            boolean timedOut = result.tryComplete(new Failure((Throwable)onTimeout.apply(new StringBuilder(125).append("Ask timed out on [").append(targetName).append("] after [").append(timeout2.duration().toMillis()).append(" ms]. ").append(messagePart).append(" A typical reason for `AskTimeoutException` is that the recipient actor didn't send a reply.").toString())));
            if (timedOut) {
                a.onTimeout(timeout2);
                return;
            }
        }, ec);
        result.future().onComplete((Function1<Try, Object> & Serializable)x$2 -> {
            PromiseActorRef$.$anonfun$apply$2(a, f, x$2);
            return BoxedUnit.UNIT;
        }, ec);
        return a;
    }

    public ActorRef apply$default$6() {
        return Actor$.MODULE$.noSender();
    }

    public Function1<String, Throwable> apply$default$7() {
        return this.defaultOnTimeout();
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(PromiseActorRef$.class);
    }

    public static final /* synthetic */ void $anonfun$apply$2(PromiseActorRef a$1, Cancellable f$1, Try x$2) {
        try {
            a$1.stop();
        }
        finally {
            f$1.cancel();
        }
    }

    private PromiseActorRef$() {
    }
}

