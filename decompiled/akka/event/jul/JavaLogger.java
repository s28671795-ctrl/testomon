/*
 * Decompiled with CFR 0.152.
 */
package akka.event.jul;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.SupervisorStrategy;
import akka.annotation.InternalApi;
import akka.dispatch.RequiresMessageQueue;
import akka.event.LoggerMessageQueueSemantics;
import akka.event.Logging;
import akka.event.Logging$LoggerInitialized$;
import akka.event.jul.Logger$;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u00114A\u0001B\u0003\u0001\u0019!)1\u0005\u0001C\u0001I!)q\u0005\u0001C\u0001Q!)!\u0007\u0001C\u0001g\tQ!*\u0019<b\u0019><w-\u001a:\u000b\u0005\u00199\u0011a\u00016vY*\u0011\u0001\"C\u0001\u0006KZ,g\u000e\u001e\u0006\u0002\u0015\u0005!\u0011m[6b\u0007\u0001\u0019B\u0001A\u0007\u00143A\u0011a\"E\u0007\u0002\u001f)\t\u0001#A\u0003tG\u0006d\u0017-\u0003\u0002\u0013\u001f\t1\u0011I\\=SK\u001a\u0004\"\u0001F\f\u000e\u0003UQ!AF\u0005\u0002\u000b\u0005\u001cGo\u001c:\n\u0005a)\"!B!di>\u0014\bc\u0001\u000e\u001e?5\t1D\u0003\u0002\u001d\u0013\u0005AA-[:qCR\u001c\u0007.\u0003\u0002\u001f7\t!\"+Z9vSJ,7/T3tg\u0006<W-U;fk\u0016\u0004\"\u0001I\u0011\u000e\u0003\u001dI!AI\u0004\u000371{wmZ3s\u001b\u0016\u001c8/Y4f#V,W/Z*f[\u0006tG/[2t\u0003\u0019a\u0014N\\5u}Q\tQ\u0005\u0005\u0002'\u00015\tQ!A\u0004sK\u000e,\u0017N^3\u0016\u0003%\u0002BA\u0004\u0016-_%\u00111f\u0004\u0002\u0010!\u0006\u0014H/[1m\rVt7\r^5p]B\u0011a\"L\u0005\u0003]=\u00111!\u00118z!\tq\u0001'\u0003\u00022\u001f\t!QK\\5u\u0003\rawn\u001a\u000b\u0005_Q\u0002e\nC\u00036\u0007\u0001\u0007a'A\u0003mKZ,G\u000e\u0005\u00028}5\t\u0001H\u0003\u0002:u\u00059An\\4hS:<'BA\u001e=\u0003\u0011)H/\u001b7\u000b\u0003u\nAA[1wC&\u0011q\b\u000f\u0002\u0006\u0019\u00164X\r\u001c\u0005\u0006\u0003\u000e\u0001\rAQ\u0001\u0006G\u0006,8/\u001a\t\u0003\u0007.s!\u0001R%\u000f\u0005\u0015CU\"\u0001$\u000b\u0005\u001d[\u0011A\u0002\u001fs_>$h(C\u0001\u0011\u0013\tQu\"A\u0004qC\u000e\\\u0017mZ3\n\u00051k%!\u0003+ie><\u0018M\u00197f\u0015\tQu\u0002C\u0003\t\u0007\u0001\u0007q\n\u0005\u0002Q/:\u0011\u0011+\u0016\b\u0003%Rs!!R*\n\u0003)I!\u0001C\u0005\n\u0005Y;\u0011a\u0002'pO\u001eLgnZ\u0005\u00031f\u0013\u0001\u0002T8h\u000bZ,g\u000e\u001e\u0006\u0003-\u001eAc\u0001A._?\u0006\u0014\u0007C\u0001\b]\u0013\tivB\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-I\u0001a\u0003a)6/\u001a\u0011TY\u001a$$\u000eT8hO\u0016\u0014\b%\u001b8ti\u0016\fGML\u0001\u0006g&t7-Z\u0011\u0002G\u0006)!G\f\u001c/a\u0001")
public class JavaLogger
implements Actor,
RequiresMessageQueue<LoggerMessageQueueSemantics> {
    private ActorContext context;
    private ActorRef self;

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
            private final /* synthetic */ JavaLogger $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Logging.Error) {
                    Logging.Error error = (Logging.Error)A1;
                    Throwable cause = error.cause();
                    this.$outer.log(Logger$.MODULE$.mapLevel(error.level()), cause, error);
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Logging.Warning) {
                    Logging.Warning warning = (Logging.Warning)A1;
                    this.$outer.log(Logger$.MODULE$.mapLevel(warning.level()), null, warning);
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Logging.Info) {
                    Logging.Info info = (Logging.Info)A1;
                    this.$outer.log(Logger$.MODULE$.mapLevel(info.level()), null, info);
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Logging.Debug) {
                    Logging.Debug debug = (Logging.Debug)A1;
                    this.$outer.log(Logger$.MODULE$.mapLevel(debug.level()), null, debug);
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Logging.InitializeLogger) {
                    Logger$.MODULE$.apply(this.$outer.getClass().getName()).warning(new StringBuilder(57).append(this.$outer.getClass().getName()).append(" has been deprecated since Akka 2.6.0. Use SLF4J instead.").toString());
                    this.$outer.sender().$bang(Logging$LoggerInitialized$.MODULE$, this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof Logging.Error) {
                    return true;
                }
                if (object instanceof Logging.Warning) {
                    return true;
                }
                if (object instanceof Logging.Info) {
                    return true;
                }
                if (object instanceof Logging.Debug) {
                    return true;
                }
                return object instanceof Logging.InitializeLogger;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public void log(Level level, Throwable cause, Logging.LogEvent event) {
        Logger logger2 = Logger$.MODULE$.apply(event.logClass(), event.logSource());
        LogRecord record = new LogRecord(level, String.valueOf(event.message()));
        record.setLoggerName(logger2.getName());
        record.setThrown(cause);
        record.setThreadID((int)event.thread().getId());
        record.setSourceClassName(event.logClass().getName());
        record.setSourceMethodName(null);
        logger2.log(record);
    }

    public JavaLogger() {
        Actor.$init$(this);
        Statics.releaseFence();
    }
}

