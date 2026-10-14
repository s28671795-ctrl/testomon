/*
 * Decompiled with CFR 0.152.
 */
package akka;

import akka.Main$;
import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.actor.SupervisorStrategy;
import akka.actor.Terminated;
import akka.annotation.InternalApi;
import akka.event.LoggingAdapter;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005M;Q\u0001C\u0005\t\u000211QAD\u0005\t\u0002=AQAF\u0001\u0005\u0002]AQ\u0001G\u0001\u0005\u0002e1A!L\u0001\u0001]!A\u0001\b\u0002B\u0001B\u0003%\u0011\bC\u0003\u0017\t\u0011\u0005A\bC\u0003A\t\u0011\u0005\u0011)\u0001\u0003NC&t'\"\u0001\u0006\u0002\t\u0005\\7.Y\u0002\u0001!\ti\u0011!D\u0001\n\u0005\u0011i\u0015-\u001b8\u0014\u0005\u0005\u0001\u0002CA\t\u0015\u001b\u0005\u0011\"\"A\n\u0002\u000bM\u001c\u0017\r\\1\n\u0005U\u0011\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002\u0019\u0005!Q.Y5o)\tQR\u0004\u0005\u0002\u00127%\u0011AD\u0005\u0002\u0005+:LG\u000fC\u0003\u001f\u0007\u0001\u0007q$\u0001\u0003be\u001e\u001c\bcA\t!E%\u0011\u0011E\u0005\u0002\u0006\u0003J\u0014\u0018-\u001f\t\u0003G)r!\u0001\n\u0015\u0011\u0005\u0015\u0012R\"\u0001\u0014\u000b\u0005\u001dZ\u0011A\u0002\u001fs_>$h(\u0003\u0002*%\u00051\u0001K]3eK\u001aL!a\u000b\u0017\u0003\rM#(/\u001b8h\u0015\tI#C\u0001\u0006UKJl\u0017N\\1u_J\u001cB\u0001\u0002\t0kA\u0011\u0001gM\u0007\u0002c)\u0011!'C\u0001\u0006C\u000e$xN]\u0005\u0003iE\u0012Q!Q2u_J\u0004\"\u0001\r\u001c\n\u0005]\n$\u0001D!di>\u0014Hj\\4hS:<\u0017aA1qaB\u0011\u0001GO\u0005\u0003wE\u0012\u0001\"Q2u_J\u0014VM\u001a\u000b\u0003{}\u0002\"A\u0010\u0003\u000e\u0003\u0005AQ\u0001\u000f\u0004A\u0002e\nqA]3dK&4X-F\u0001C!\u0011\t2)\u0012\u000e\n\u0005\u0011\u0013\"a\u0004)beRL\u0017\r\u001c$v]\u000e$\u0018n\u001c8\u0011\u0005E1\u0015BA$\u0013\u0005\r\te.\u001f\u0015\u0007\u0003%cUj\u0014)\u0011\u0005EQ\u0015BA&\u0013\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\u0005q\u0015aV%na2,W.\u001a8uAe|WO\u001d\u0011po:\u0004S.Y5oA\rd\u0017m]:!S:\u001cH/Z1eY\u00012'o\\7!o\"L7\r\u001b\u0011z_V\u00043\u000f^1si\u0002\"\b.\u001a\u0011BGR|'oU=ti\u0016l\u0007%\u00198eA\u0005\u001cGo\u001c:t]\u0005)1/\u001b8dK\u0006\n\u0011+A\u00033]Yr\u0003\u0007\u000b\u0004\u0001\u00132ku\n\u0015")
public final class Main {
    public static void main(String[] args2) {
        Main$.MODULE$.main(args2);
    }

    public static class Terminator
    implements Actor,
    ActorLogging {
        private LoggingAdapter akka$actor$ActorLogging$$_log;
        private ActorContext context;
        private ActorRef self;

        @Override
        public LoggingAdapter log() {
            return ActorLogging.log$(this);
        }

        @Override
        public final ActorRef sender() {
            return Actor.sender$(this);
        }

        @Override
        @InternalApi
        public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
            Actor.aroundReceive$(this, receive2, msg);
        }

        @Override
        @InternalApi
        public void aroundPreStart() {
            Actor.aroundPreStart$(this);
        }

        @Override
        @InternalApi
        public void aroundPostStop() {
            Actor.aroundPostStop$(this);
        }

        @Override
        @InternalApi
        public void aroundPreRestart(Throwable reason, Option<Object> message) {
            Actor.aroundPreRestart$(this, reason, message);
        }

        @Override
        @InternalApi
        public void aroundPostRestart(Throwable reason) {
            Actor.aroundPostRestart$(this, reason);
        }

        @Override
        public SupervisorStrategy supervisorStrategy() {
            return Actor.supervisorStrategy$(this);
        }

        @Override
        public void preStart() throws Exception {
            Actor.preStart$(this);
        }

        @Override
        public void postStop() throws Exception {
            Actor.postStop$(this);
        }

        @Override
        public void preRestart(Throwable reason, Option<Object> message) throws Exception {
            Actor.preRestart$(this, reason, message);
        }

        @Override
        public void postRestart(Throwable reason) throws Exception {
            Actor.postRestart$(this, reason);
        }

        @Override
        public void unhandled(Object message) {
            Actor.unhandled$(this, message);
        }

        @Override
        public LoggingAdapter akka$actor$ActorLogging$$_log() {
            return this.akka$actor$ActorLogging$$_log;
        }

        @Override
        public void akka$actor$ActorLogging$$_log_$eq(LoggingAdapter x$1) {
            this.akka$actor$ActorLogging$$_log = x$1;
        }

        @Override
        public ActorContext context() {
            return this.context;
        }

        @Override
        public final ActorRef self() {
            return this.self;
        }

        @Override
        public void akka$actor$Actor$_setter_$context_$eq(ActorContext x$1) {
            this.context = x$1;
        }

        @Override
        public final void akka$actor$Actor$_setter_$self_$eq(ActorRef x$1) {
            this.self = x$1;
        }

        @Override
        public PartialFunction<Object, BoxedUnit> receive() {
            return new Serializable(this){
                private static final long serialVersionUID = 0L;
                private final /* synthetic */ Terminator $outer;

                public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                    A1 A1 = x1;
                    if (A1 instanceof Terminated) {
                        this.$outer.log().info("application supervisor has terminated, shutting down");
                        this.$outer.context().system().terminate();
                        return (B1)BoxedUnit.UNIT;
                    }
                    return function1.apply(x1);
                }

                public final boolean isDefinedAt(Object x1) {
                    Object object = x1;
                    return object instanceof Terminated;
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                }
            };
        }

        public Terminator(ActorRef app) {
            Actor.$init$(this);
            ActorLogging.$init$(this);
            this.context().watch(app);
            Statics.releaseFence();
        }
    }
}

