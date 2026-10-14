/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.ConfigurationException;
import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ActorSystemImpl;
import akka.actor.InternalActorRef;
import akka.actor.InvalidActorNameException;
import akka.actor.Props$;
import akka.actor.SupervisorStrategy;
import akka.actor.UnhandledMessage;
import akka.annotation.InternalApi;
import akka.event.ActorEventBus;
import akka.event.Logging;
import akka.event.Logging$;
import akka.event.Logging$LogExt$;
import akka.event.Logging$LogLevel$;
import akka.event.Logging$LoggerInitialized$;
import akka.event.LoggingBus$;
import akka.pattern.AskableActorRef$;
import akka.util.ReentrantGuard;
import akka.util.Timeout;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.collection.IterableOnceOps;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.concurrent.Await$;
import scala.package$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Eca\u0002\t\u0012!\u0003\r\tA\u0006\u0005\u0006C\u0001!\tA\t\u0003\u0006M\u0001\u0011\ta\n\u0003\u0006e\u0001\u0011\ta\r\u0005\b\u0011\u0002\u0011\r\u0011\"\u0003J\u0011\u001d\u0001\u0006\u00011A\u0005\nECq\u0001\u0019\u0001A\u0002\u0013%\u0011\rC\u0005e\u0001\u0001\u0007\t\u0019!C\u0005K\"IQ\u000e\u0001a\u0001\u0002\u0004%IA\u001c\u0005\u0006a\u0002!\t!\u001a\u0005\u0006c\u0002!\tA\u001d\u0005\u0006k\u0002!IA\u001e\u0005\t\u0003\u0013\u0001A\u0011A\n\u0002\f!A\u0011q\u0002\u0001\u0005\u0002M\t\t\u0002\u0003\u0005\u0002\u001e\u0001!\taEA\u0010\u0011\u001d\tI\u0003\u0001C\u0005\u0003W\u0011!\u0002T8hO&twMQ;t\u0015\t\u00112#A\u0003fm\u0016tGOC\u0001\u0015\u0003\u0011\t7n[1\u0004\u0001M\u0019\u0001aF\u000f\u0011\u0005aYR\"A\r\u000b\u0003i\tQa]2bY\u0006L!\u0001H\r\u0003\r\u0005s\u0017PU3g!\tqr$D\u0001\u0012\u0013\t\u0001\u0013CA\u0007BGR|'/\u0012<f]R\u0014Uo]\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003\r\u0002\"\u0001\u0007\u0013\n\u0005\u0015J\"\u0001B+oSR\u0014Q!\u0012<f]R\f\"\u0001K\u0018\u0011\u0005%bcB\u0001\u0010+\u0013\tY\u0013#A\u0004M_\u001e<\u0017N\\4\n\u00055r#\u0001\u0003'pO\u00163XM\u001c;\u000b\u0005-\n\u0002C\u0001\r1\u0013\t\t\u0014DA\u0002B]f\u0014!b\u00117bgNLg-[3s#\t!t\u0006\r\u00026\u0005B\u0019a'\u0010!\u000f\u0005]Z\u0004C\u0001\u001d\u001a\u001b\u0005I$B\u0001\u001e\u0016\u0003\u0019a$o\\8u}%\u0011A(G\u0001\u0007!J,G-\u001a4\n\u0005yz$!B\"mCN\u001c(B\u0001\u001f\u001a!\t\t%\t\u0004\u0001\u0005\u0013\r\u001b\u0011\u0011!A\u0001\u0006\u0003!%aA0%cE\u0011Qi\f\t\u00031\u0019K!aR\r\u0003\u000f9{G\u000f[5oO\u0006)q-^1sIV\t!\n\u0005\u0002L\u001d6\tAJ\u0003\u0002N'\u0005!Q\u000f^5m\u0013\tyEJ\u0001\bSK\u0016tGO]1oi\u001e+\u0018M\u001d3\u0002\u000f1|wmZ3sgV\t!\u000bE\u0002T1jk\u0011\u0001\u0016\u0006\u0003+Z\u000b\u0011\"[7nkR\f'\r\\3\u000b\u0005]K\u0012AC2pY2,7\r^5p]&\u0011\u0011\f\u0016\u0002\u0004'\u0016\f\bCA._\u001b\u0005a&BA/\u0014\u0003\u0015\t7\r^8s\u0013\tyFL\u0001\u0005BGR|'OU3g\u0003-awnZ4feN|F%Z9\u0015\u0005\r\u0012\u0007bB2\u0007\u0003\u0003\u0005\rAU\u0001\u0004q\u0012\n\u0014!C0m_\u001edUM^3m+\u00051\u0007CA\u0015h\u0013\tAgF\u0001\u0005M_\u001edUM^3mQ\t9!\u000e\u0005\u0002\u0019W&\u0011A.\u0007\u0002\tm>d\u0017\r^5mK\u0006iq\f\\8h\u0019\u00164X\r\\0%KF$\"aI8\t\u000f\rD\u0011\u0011!a\u0001M\u0006AAn\\4MKZ,G.A\u0006tKRdun\u001a'fm\u0016dGCA\u0012t\u0011\u0015!(\u00021\u0001g\u0003\u0015aWM^3m\u0003E\u0019X\r^+q'R$w.\u001e;M_\u001e<WM\u001d\u000b\u0003G]DQ\u0001_\u0006A\u0002e\faaY8oM&<\u0007c\u0001>\u0002\u00049\u00111p \b\u0003yzt!\u0001O?\n\u0003QI!!X\n\n\u0007\u0005\u0005A,A\u0006BGR|'oU=ti\u0016l\u0017\u0002BA\u0003\u0003\u000f\u0011\u0001bU3ui&twm\u001d\u0006\u0004\u0003\u0003a\u0016!E:uCJ$8\u000b\u001e3pkRdunZ4feR\u00191%!\u0004\t\u000bad\u0001\u0019A=\u0002'M$\u0018M\u001d;EK\u001a\fW\u000f\u001c;M_\u001e<WM]:\u0015\u0007\r\n\u0019\u0002C\u0004\u0002\u00165\u0001\r!a\u0006\u0002\rML8\u000f^3n!\rY\u0016\u0011D\u0005\u0004\u00037a&aD!di>\u00148+_:uK6LU\u000e\u001d7\u0002%M$x\u000e\u001d#fM\u0006,H\u000e\u001e'pO\u001e,'o\u001d\u000b\u0004G\u0005\u0005\u0002bBA\u000b\u001d\u0001\u0007\u00111\u0005\t\u00047\u0006\u0015\u0012bAA\u00149\nY\u0011i\u0019;peNK8\u000f^3n\u0003%\tG\r\u001a'pO\u001e,'\u000fF\u0005[\u0003[\ty#!\u0012\u0002H!9\u0011QC\bA\u0002\u0005]\u0001bBA\u0019\u001f\u0001\u0007\u00111G\u0001\u0006G2\f'P\u001f\u0019\u0005\u0003k\tI\u0004\u0005\u00037{\u0005]\u0002cA!\u0002:\u0011a\u00111HA\u0018\u0003\u0003\u0005\tQ!\u0001\u0002>\t\u0019q\f\n\u001a\u0012\u0007\u0015\u000by\u0004E\u0002\\\u0003\u0003J1!a\u0011]\u0005\u0015\t5\r^8s\u0011\u0015!x\u00021\u0001g\u0011\u001d\tIe\u0004a\u0001\u0003\u0017\nq\u0001\\8h\u001d\u0006lW\rE\u00027\u0003\u001bJ1!a\u0014@\u0005\u0019\u0019FO]5oO\u0002")
public interface LoggingBus
extends ActorEventBus {
    public void akka$event$LoggingBus$_setter_$akka$event$LoggingBus$$guard_$eq(ReentrantGuard var1);

    public ReentrantGuard akka$event$LoggingBus$$guard();

    public Seq<ActorRef> akka$event$LoggingBus$$loggers();

    public void akka$event$LoggingBus$$loggers_$eq(Seq<ActorRef> var1);

    public int akka$event$LoggingBus$$_logLevel();

    public void akka$event$LoggingBus$$_logLevel_$eq(int var1);

    public static /* synthetic */ int logLevel$(LoggingBus $this) {
        return $this.logLevel();
    }

    default public int logLevel() {
        return this.akka$event$LoggingBus$$_logLevel();
    }

    public static /* synthetic */ void setLogLevel$(LoggingBus $this, int level) {
        $this.setLogLevel(level);
    }

    default public void setLogLevel(int level) {
        this.akka$event$LoggingBus$$guard().withGuard(() -> {
            int logLvl = this.akka$event$LoggingBus$$_logLevel();
            Logging$.MODULE$.AllLogLevels().withFilter((Function1<Object, Object> & Serializable)l -> BoxesRunTime.boxToBoolean(LoggingBus.$anonfun$setLogLevel$2(logLvl, level, ((Logging.LogLevel)l).asInt()))).foreach((Function1<Object, Object> & Serializable)l -> {
                LoggingBus.$anonfun$setLogLevel$3(this, ((Logging.LogLevel)l).asInt());
                return BoxedUnit.UNIT;
            });
            Logging$.MODULE$.AllLogLevels().withFilter((Function1<Object, Object> & Serializable)l -> BoxesRunTime.boxToBoolean(LoggingBus.$anonfun$setLogLevel$5(logLvl, level, ((Logging.LogLevel)l).asInt()))).foreach((Function1<Object, Object> & Serializable)l -> {
                LoggingBus.$anonfun$setLogLevel$6(this, ((Logging.LogLevel)l).asInt());
                return BoxedUnit.UNIT;
            });
            this.akka$event$LoggingBus$$_logLevel_$eq(level);
        });
    }

    private void setUpStdoutLogger(ActorSystem.Settings config) {
        int level = ((Logging.LogLevel)Logging$.MODULE$.levelFor(config.StdoutLogLevel()).getOrElse((Function0<Object> & Serializable)() -> new Logging.LogLevel(LoggingBus.$anonfun$setUpStdoutLogger$1(this, config)))).asInt();
        ((IterableOnceOps)Logging$.MODULE$.AllLogLevels().filter((Function1<Object, Object> & Serializable)x$2 -> BoxesRunTime.boxToBoolean(Logging$LogLevel$.MODULE$.$greater$eq$extension(level, ((Logging.LogLevel)x$2).asInt())))).foreach((Function1<Object, Object> & Serializable)l -> BoxesRunTime.boxToBoolean(this.subscribe(Logging$.MODULE$.StandardOutLogger(), Logging$.MODULE$.classFor(((Logging.LogLevel)l).asInt()))));
        this.akka$event$LoggingBus$$guard().withGuard(() -> {
            this.akka$event$LoggingBus$$loggers_$eq((Seq)this.akka$event$LoggingBus$$loggers().$colon$plus(Logging$.MODULE$.StandardOutLogger()));
            this.akka$event$LoggingBus$$_logLevel_$eq(level);
        });
    }

    public static /* synthetic */ void startStdoutLogger$(LoggingBus $this, ActorSystem.Settings config) {
        $this.startStdoutLogger(config);
    }

    default public void startStdoutLogger(ActorSystem.Settings config) {
        this.setUpStdoutLogger(config);
        this.publish(new Logging.Debug(Logging$.MODULE$.simpleName(this), this.getClass(), "StandardOutLogger started"));
    }

    public static /* synthetic */ void startDefaultLoggers$(LoggingBus $this, ActorSystemImpl system) {
        $this.startDefaultLoggers(system);
    }

    default public void startDefaultLoggers(ActorSystemImpl system) {
        String logName = new StringBuilder(2).append(Logging$.MODULE$.simpleName(this)).append("(").append(system).append(")").toString();
        int level = ((Logging.LogLevel)Logging$.MODULE$.levelFor(system.settings().LogLevel()).getOrElse((Function0<Object> & Serializable)() -> new Logging.LogLevel(LoggingBus.$anonfun$startDefaultLoggers$1(this, logName, system)))).asInt();
        try {
            Serializable serializable;
            Seq<String> seq;
            Seq<String> seq2 = system.settings().Loggers();
            if (((Object)Nil$.MODULE$).equals(seq2)) {
                String string = Logging.DefaultLogger.class.getName();
                seq = Nil$.MODULE$.$colon$colon(string);
            } else {
                seq = seq2;
            }
            Seq<String> defaultLoggers = seq;
            Seq myloggers = (Seq)defaultLoggers.withFilter((Function1<String, Object> & Serializable)loggerName -> BoxesRunTime.boxToBoolean(LoggingBus.$anonfun$startDefaultLoggers$2(loggerName))).map((Function1<String, ActorRef> & Serializable)loggerName -> (ActorRef)((Object)system.dynamicAccess().getClassFor((String)loggerName, ClassTag$.MODULE$.apply(Actor.class)).map((Function1<Class, ActorRef> & Serializable)actorClass -> this.addLogger(system, (Class<? extends Actor>)actorClass, level, logName)).recover(new Serializable(null, (String)loggerName){
                private static final long serialVersionUID = 0L;
                private final String loggerName$1;

                public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                    A1 A1 = x1;
                    throw new ConfigurationException(new StringBuilder(55).append("Logger specified in config can't be loaded [").append(this.loggerName$1).append("] due to [").append(A1.toString()).append("]").toString(), A1);
                }

                public final boolean isDefinedAt(Throwable x1) {
                    Throwable throwable = x1;
                    return true;
                }
                {
                    this.loggerName$1 = loggerName$1;
                }
            }).get()));
            this.akka$event$LoggingBus$$guard().withGuard(() -> {
                this.akka$event$LoggingBus$$loggers_$eq(myloggers);
                this.akka$event$LoggingBus$$_logLevel_$eq(level);
            });
            try {
                serializable = system.settings().DebugUnhandledMessage() ? BoxesRunTime.boxToBoolean(this.subscribe(system.systemActorOf(Props$.MODULE$.apply((Function0<$anon$1> & Serializable)() -> new Actor(this){
                    private ActorContext context;
                    private ActorRef self;
                    private final /* synthetic */ LoggingBus $outer;

                    public final ActorRef sender() {
                        return Actor.sender$(this);
                    }

                    @InternalApi
                    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
                        Actor.aroundReceive$(this, receive2, msg);
                    }

                    @InternalApi
                    public void aroundPreStart() {
                        Actor.aroundPreStart$(this);
                    }

                    @InternalApi
                    public void aroundPostStop() {
                        Actor.aroundPostStop$(this);
                    }

                    @InternalApi
                    public void aroundPreRestart(Throwable reason, Option<Object> message) {
                        Actor.aroundPreRestart$(this, reason, message);
                    }

                    @InternalApi
                    public void aroundPostRestart(Throwable reason) {
                        Actor.aroundPostRestart$(this, reason);
                    }

                    public SupervisorStrategy supervisorStrategy() {
                        return Actor.supervisorStrategy$(this);
                    }

                    public void preStart() throws Exception {
                        Actor.preStart$(this);
                    }

                    public void postStop() throws Exception {
                        Actor.postStop$(this);
                    }

                    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
                        Actor.preRestart$(this, reason, message);
                    }

                    public void postRestart(Throwable reason) throws Exception {
                        Actor.postRestart$(this, reason);
                    }

                    public void unhandled(Object message) {
                        Actor.unhandled$(this, message);
                    }

                    public ActorContext context() {
                        return this.context;
                    }

                    public final ActorRef self() {
                        return this.self;
                    }

                    public void akka$actor$Actor$_setter_$context_$eq(ActorContext x$1) {
                        this.context = x$1;
                    }

                    public final void akka$actor$Actor$_setter_$self_$eq(ActorRef x$1) {
                        this.self = x$1;
                    }

                    public PartialFunction<Object, BoxedUnit> receive() {
                        return new Serializable(this){
                            private static final long serialVersionUID = 0L;
                            private final /* synthetic */ $anon$1 $outer;

                            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                                A1 A1 = x1;
                                if (A1 instanceof UnhandledMessage) {
                                    UnhandledMessage unhandledMessage = (UnhandledMessage)A1;
                                    Object msg = unhandledMessage.message();
                                    ActorRef sender = unhandledMessage.sender();
                                    ActorRef rcp = unhandledMessage.recipient();
                                    this.$outer.akka$event$LoggingBus$$anon$$$outer().publish(new Logging.Debug(rcp.path().toString(), rcp.getClass(), new StringBuilder(25).append("unhandled message from ").append(sender).append(": ").append(msg).toString()));
                                    return (B1)BoxedUnit.UNIT;
                                }
                                return function1.apply(x1);
                            }

                            public final boolean isDefinedAt(Object x1) {
                                Object object = x1;
                                return object instanceof UnhandledMessage;
                            }
                            {
                                if ($outer == null) {
                                    throw null;
                                }
                                this.$outer = $outer;
                            }
                        };
                    }

                    public /* synthetic */ LoggingBus akka$event$LoggingBus$$anon$$$outer() {
                        return this.$outer;
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        Actor.$init$(this);
                        Statics.releaseFence();
                    }
                }, ClassTag$.MODULE$.apply(Actor.class)), "UnhandledMessageForwarder"), UnhandledMessage.class)) : BoxedUnit.UNIT;
            }
            catch (InvalidActorNameException invalidActorNameException) {
                serializable = BoxedUnit.UNIT;
            }
            this.publish(new Logging.Debug(logName, this.getClass(), "Default Loggers started"));
            if (!defaultLoggers.contains(Logging$.MODULE$.StandardOutLogger().getClass().getName())) {
                this.unsubscribe(Logging$.MODULE$.StandardOutLogger());
            }
        }
        catch (Exception e) {
            System.err.println("error while starting up loggers");
            e.printStackTrace();
            throw new ConfigurationException(new StringBuilder(32).append("Could not start logger due to [").append(e.toString()).append("]").toString());
        }
    }

    public static /* synthetic */ void stopDefaultLoggers$(LoggingBus $this, ActorSystem system) {
        $this.stopDefaultLoggers(system);
    }

    default public void stopDefaultLoggers(ActorSystem system) {
        int level = this.akka$event$LoggingBus$$_logLevel();
        if (!this.akka$event$LoggingBus$$loggers().contains(Logging$.MODULE$.StandardOutLogger())) {
            this.setUpStdoutLogger(system.settings());
            this.publish(new Logging.Debug(Logging$.MODULE$.simpleName(this), this.getClass(), "shutting down: StandardOutLogger"));
        }
        this.akka$event$LoggingBus$$loggers().withFilter((Function1<ActorRef, Object> & Serializable)logger2 -> BoxesRunTime.boxToBoolean(LoggingBus.$anonfun$stopDefaultLoggers$1(logger2))).foreach((Function1<ActorRef, Object> & Serializable)logger2 -> {
            LoggingBus.$anonfun$stopDefaultLoggers$2(this, logger2);
            return BoxedUnit.UNIT;
        });
        this.publish(new Logging.Debug(Logging$.MODULE$.simpleName(this), this.getClass(), "all default loggers stopped"));
    }

    private ActorRef addLogger(ActorSystemImpl system, Class<? extends Actor> clazz, int level, String logName) {
        Object response;
        Object object;
        String name = new StringBuilder(4).append("log").append(((Logging.LogExt)Logging$LogExt$.MODULE$.apply(system)).id()).append("-").append(Logging$.MODULE$.simpleName(clazz)).toString();
        ActorRef actor = system.systemActorOf(Props$.MODULE$.apply(clazz, Nil$.MODULE$).withDispatcher(system.settings().LoggersDispatcher()), name);
        try {
            ActorRef qual$1 = akka.pattern.package$.MODULE$.ask(actor);
            Logging.InitializeLogger x$1 = new Logging.InitializeLogger(this);
            Timeout x$2 = LoggingBus.timeout$1(system);
            ActorRef x$32 = AskableActorRef$.MODULE$.$qmark$default$3$extension(qual$1, x$1);
            object = Await$.MODULE$.result(AskableActorRef$.MODULE$.$qmark$extension(qual$1, x$1, x$2, x$32), LoggingBus.timeout$1(system).duration());
        }
        catch (TimeoutException timeoutException) {
            this.publish(new Logging.Warning(logName, this.getClass(), new StringBuilder(56).append("Logger ").append(name).append(" did not respond within ").append(LoggingBus.timeout$1(system)).append(" to InitializeLogger(bus)").toString()));
            object = "[TIMEOUT]";
        }
        Object object2 = response = object;
        Logging$LoggerInitialized$ logging$LoggerInitialized$ = Logging$LoggerInitialized$.MODULE$;
        if (object2 == null ? logging$LoggerInitialized$ != null : !object2.equals(logging$LoggerInitialized$)) {
            throw new Logging.LoggerInitializationException(new StringBuilder(61).append("Logger ").append(name).append(" did not respond with LoggerInitialized, sent instead ").append(response).toString());
        }
        ((IterableOnceOps)Logging$.MODULE$.AllLogLevels().filter((Function1<Object, Object> & Serializable)x$3 -> BoxesRunTime.boxToBoolean(Logging$LogLevel$.MODULE$.$greater$eq$extension(level, ((Logging.LogLevel)x$3).asInt())))).foreach((Function1<Object, Object> & Serializable)l -> BoxesRunTime.boxToBoolean(this.subscribe(actor, Logging$.MODULE$.classFor(((Logging.LogLevel)l).asInt()))));
        this.publish(new Logging.Debug(logName, this.getClass(), new StringBuilder(15).append("logger ").append(name).append(" started").toString()));
        return actor;
    }

    public static /* synthetic */ boolean $anonfun$setLogLevel$2(int logLvl$1, int level$1, int l) {
        return Logging$LogLevel$.MODULE$.$greater$extension(l, logLvl$1) && Logging$LogLevel$.MODULE$.$less$eq$extension(l, level$1);
    }

    public static /* synthetic */ void $anonfun$setLogLevel$3(LoggingBus $this, int l) {
        $this.akka$event$LoggingBus$$loggers().foreach((Function1<ActorRef, Object> & Serializable)log -> BoxesRunTime.boxToBoolean($this.subscribe(log, Logging$.MODULE$.classFor(l))));
    }

    public static /* synthetic */ boolean $anonfun$setLogLevel$5(int logLvl$1, int level$1, int l) {
        return Logging$LogLevel$.MODULE$.$less$eq$extension(l, logLvl$1) && Logging$LogLevel$.MODULE$.$greater$extension(l, level$1);
    }

    public static /* synthetic */ void $anonfun$setLogLevel$6(LoggingBus $this, int l) {
        $this.akka$event$LoggingBus$$loggers().foreach((Function1<ActorRef, Object> & Serializable)log -> BoxesRunTime.boxToBoolean($this.unsubscribe(log, Logging$.MODULE$.classFor(l))));
    }

    public static /* synthetic */ int $anonfun$setUpStdoutLogger$1(LoggingBus $this, ActorSystem.Settings config$1) {
        Logging$.MODULE$.StandardOutLogger().print(new Logging.Error(new Logging.LoggerException(), Logging$.MODULE$.simpleName($this), $this.getClass(), new StringBuilder(29).append("unknown akka.stdout-loglevel ").append(config$1.StdoutLogLevel()).toString()));
        return Logging$.MODULE$.ErrorLevel();
    }

    public static /* synthetic */ int $anonfun$startDefaultLoggers$1(LoggingBus $this, String logName$1, ActorSystemImpl system$1) {
        Logging$.MODULE$.StandardOutLogger().print(new Logging.Error(new Logging.LoggerException(), logName$1, $this.getClass(), new StringBuilder(22).append("unknown akka.loglevel ").append(system$1.settings().LogLevel()).toString()));
        return Logging$.MODULE$.ErrorLevel();
    }

    public static /* synthetic */ boolean $anonfun$startDefaultLoggers$2(String loggerName) {
        String string = loggerName;
        String string2 = Logging$.MODULE$.StandardOutLogger().getClass().getName();
        return string == null ? string2 != null : !string.equals(string2);
    }

    public static /* synthetic */ boolean $anonfun$stopDefaultLoggers$1(ActorRef logger2) {
        ActorRef actorRef2 = logger2;
        Logging.StandardOutLogger standardOutLogger = Logging$.MODULE$.StandardOutLogger();
        return actorRef2 == null ? standardOutLogger != null : !((Object)actorRef2).equals(standardOutLogger);
    }

    public static /* synthetic */ void $anonfun$stopDefaultLoggers$2(LoggingBus $this, ActorRef logger2) {
        $this.unsubscribe(logger2);
        ActorRef actorRef2 = logger2;
        if (actorRef2 instanceof InternalActorRef) {
            InternalActorRef internalActorRef = (InternalActorRef)actorRef2;
            internalActorRef.stop();
            return;
        }
    }

    private static Timeout timeout$1(ActorSystemImpl system$2) {
        return system$2.settings().LoggerStartTimeout();
    }

    public static void $init$(LoggingBus $this) {
        $this.akka$event$LoggingBus$_setter_$akka$event$LoggingBus$$guard_$eq(new ReentrantGuard());
        $this.akka$event$LoggingBus$$loggers_$eq((Seq)package$.MODULE$.Seq().empty());
    }
}

