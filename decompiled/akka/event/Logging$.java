/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.Actor;
import akka.actor.ActorSelectionMessage;
import akka.actor.ActorSystem;
import akka.actor.ExtendedActorSystem;
import akka.annotation.InternalApi;
import akka.event.BusLogging;
import akka.event.DiagnosticLoggingAdapter;
import akka.event.DiagnosticMarkerBusLoggingAdapter;
import akka.event.LogSource;
import akka.event.LogSource$;
import akka.event.Logging;
import akka.event.Logging$Error$NoCause$;
import akka.event.Logging$LoggerInitialized$;
import akka.event.LoggingAdapter;
import akka.event.LoggingBus;
import akka.event.MarkerLoggingAdapter;
import akka.util.Helpers$;
import java.io.PrintWriter;
import java.io.StringWriter;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.Tuple2;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.runtime.ScalaRunTime$;
import scala.util.control.NoStackTrace;

public final class Logging$ {
    public static final Logging$ MODULE$ = new Logging$();
    private static final int ErrorLevel = 1;
    private static final int WarningLevel = 2;
    private static final int InfoLevel = 3;
    private static final int DebugLevel = 4;
    @InternalApi
    private static final int OffLevel = Integer.MIN_VALUE;
    private static final Seq<Logging.LogLevel> AllLogLevels = (Seq)package$.MODULE$.Vector().apply((Seq)ScalaRunTime$.MODULE$.genericWrapArray(new Logging.LogLevel[]{new Logging.LogLevel(MODULE$.ErrorLevel()), new Logging.LogLevel(MODULE$.WarningLevel()), new Logging.LogLevel(MODULE$.InfoLevel()), new Logging.LogLevel(MODULE$.DebugLevel())}));
    private static final Logging.SerializedStandardOutLogger akka$event$Logging$$serializedStandardOutLogger = new Logging.SerializedStandardOutLogger();
    private static final Logging.StandardOutLogger StandardOutLogger = new Logging.StandardOutLogger();
    private static final Map<String, Object> emptyMDC = (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$);

    public String simpleName(Object obj) {
        return this.simpleName(obj.getClass());
    }

    public String simpleName(Class<?> clazz) {
        String n = clazz.getName();
        int i = n.lastIndexOf(46);
        return n.substring(i + 1);
    }

    public String messageClassName(Object message) {
        Object object = message;
        if (object == null) {
            return "null";
        }
        if (object instanceof ActorSelectionMessage) {
            ActorSelectionMessage actorSelectionMessage = (ActorSelectionMessage)object;
            Object m4 = actorSelectionMessage.msg();
            return new StringBuilder(23).append("ActorSelectionMessage(").append(m4.getClass().getName()).append(")").toString();
        }
        return object.getClass().getName();
    }

    public final int ErrorLevel() {
        return ErrorLevel;
    }

    public final int WarningLevel() {
        return WarningLevel;
    }

    public final int InfoLevel() {
        return InfoLevel;
    }

    public final int DebugLevel() {
        return DebugLevel;
    }

    public final int OffLevel() {
        return OffLevel;
    }

    public Option<Logging.LogLevel> levelFor(String s2) {
        String string = Helpers$.MODULE$.toRootLowerCase(s2);
        switch (string == null ? 0 : string.hashCode()) {
            case 109935: {
                if (!"off".equals(string)) break;
                return new Some<Logging.LogLevel>(new Logging.LogLevel(this.OffLevel()));
            }
            case 3237038: {
                if (!"info".equals(string)) break;
                return new Some<Logging.LogLevel>(new Logging.LogLevel(this.InfoLevel()));
            }
            case 95458899: {
                if (!"debug".equals(string)) break;
                return new Some<Logging.LogLevel>(new Logging.LogLevel(this.DebugLevel()));
            }
            case 96784904: {
                if (!"error".equals(string)) break;
                return new Some<Logging.LogLevel>(new Logging.LogLevel(this.ErrorLevel()));
            }
            case 1124446108: {
                if (!"warning".equals(string)) break;
                return new Some<Logging.LogLevel>(new Logging.LogLevel(this.WarningLevel()));
            }
        }
        return None$.MODULE$;
    }

    public int levelFor(Class<? extends Logging.LogEvent> eventClass) {
        if (Logging.Error.class.isAssignableFrom(eventClass)) {
            return this.ErrorLevel();
        }
        if (Logging.Warning.class.isAssignableFrom(eventClass)) {
            return this.WarningLevel();
        }
        if (Logging.Info.class.isAssignableFrom(eventClass)) {
            return this.InfoLevel();
        }
        if (Logging.Debug.class.isAssignableFrom(eventClass)) {
            return this.DebugLevel();
        }
        return this.DebugLevel();
    }

    public Class<? extends Logging.LogEvent> classFor(int level) {
        int n = level;
        if (this.ErrorLevel() == n) {
            return Logging.Error.class;
        }
        if (this.WarningLevel() == n) {
            return Logging.Warning.class;
        }
        if (this.InfoLevel() == n) {
            return Logging.Info.class;
        }
        if (this.DebugLevel() == n) {
            return Logging.Debug.class;
        }
        throw new IllegalArgumentException(new StringBuilder(24).append("Unsupported log level [").append(new Logging.LogLevel(n)).append("]").toString());
    }

    public Seq<Logging.LogLevel> AllLogLevels() {
        return AllLogLevels;
    }

    public <T> LoggingAdapter apply(ActorSystem system, T logSource, LogSource<T> evidence$3) {
        Tuple2<String, Class<?>> tuple2 = LogSource$.MODULE$.apply(logSource, system, evidence$3);
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        String str = tuple2._1();
        Class<?> clazz = tuple2._2();
        Tuple2 tuple22 = new Tuple2(str, clazz);
        String str2 = tuple22._1();
        Class<?> clazz2 = tuple22._2();
        return new BusLogging(system.eventStream(), str2, clazz2, ((ExtendedActorSystem)system).logFilter());
    }

    public <T> MarkerLoggingAdapter withMarker(ActorSystem system, T logSource, LogSource<T> evidence$4) {
        Tuple2<String, Class<?>> tuple2 = LogSource$.MODULE$.apply(logSource, system, evidence$4);
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        String str = tuple2._1();
        Class<?> clazz = tuple2._2();
        Tuple2 tuple22 = new Tuple2(str, clazz);
        String str2 = tuple22._1();
        Class<?> clazz2 = tuple22._2();
        return new MarkerLoggingAdapter(system.eventStream(), str2, clazz2, ((ExtendedActorSystem)system).logFilter());
    }

    public <T> LoggingAdapter apply(LoggingBus bus, T logSource, LogSource<T> evidence$5) {
        Tuple2<String, Class<?>> tuple2 = LogSource$.MODULE$.apply(logSource, evidence$5);
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        String str = tuple2._1();
        Class<?> clazz = tuple2._2();
        Tuple2 tuple22 = new Tuple2(str, clazz);
        String str2 = tuple22._1();
        Class<?> clazz2 = tuple22._2();
        return new BusLogging(bus, str2, clazz2);
    }

    public <T> MarkerLoggingAdapter withMarker(LoggingBus bus, T logSource, LogSource<T> evidence$6) {
        Tuple2<String, Class<?>> tuple2 = LogSource$.MODULE$.apply(logSource, evidence$6);
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        String str = tuple2._1();
        Class<?> clazz = tuple2._2();
        Tuple2 tuple22 = new Tuple2(str, clazz);
        String str2 = tuple22._1();
        Class<?> clazz2 = tuple22._2();
        return new MarkerLoggingAdapter(bus, str2, clazz2);
    }

    public DiagnosticLoggingAdapter apply(Actor logSource) {
        Tuple2<String, Class<?>> tuple2 = LogSource$.MODULE$.apply(logSource, LogSource$.MODULE$.fromActor());
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        String str = tuple2._1();
        Class<?> clazz = tuple2._2();
        Tuple2 tuple22 = new Tuple2(str, clazz);
        String str2 = tuple22._1();
        Class<?> clazz2 = tuple22._2();
        ExtendedActorSystem system = (ExtendedActorSystem)logSource.context().system();
        return new DiagnosticLoggingAdapter(system, str2, clazz2){
            private Map<String, Object> akka$event$DiagnosticLoggingAdapter$$_mdc;

            public Map<String, Object> mdc() {
                return DiagnosticLoggingAdapter.mdc$(this);
            }

            public void mdc(Map<String, Object> mdc) {
                DiagnosticLoggingAdapter.mdc$(this, mdc);
            }

            public java.util.Map<String, Object> getMDC() {
                return DiagnosticLoggingAdapter.getMDC$(this);
            }

            public void setMDC(java.util.Map<String, Object> jMdc) {
                DiagnosticLoggingAdapter.setMDC$(this, jMdc);
            }

            public void clearMDC() {
                DiagnosticLoggingAdapter.clearMDC$(this);
            }

            public Map<String, Object> akka$event$DiagnosticLoggingAdapter$$_mdc() {
                return this.akka$event$DiagnosticLoggingAdapter$$_mdc;
            }

            public void akka$event$DiagnosticLoggingAdapter$$_mdc_$eq(Map<String, Object> x$1) {
                this.akka$event$DiagnosticLoggingAdapter$$_mdc = x$1;
            }
            {
                DiagnosticLoggingAdapter.$init$(this);
            }
        };
    }

    public DiagnosticMarkerBusLoggingAdapter withMarker(Actor logSource) {
        Tuple2<String, Class<?>> tuple2 = LogSource$.MODULE$.apply(logSource, LogSource$.MODULE$.fromActor());
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        String str = tuple2._1();
        Class<?> clazz = tuple2._2();
        Tuple2 tuple22 = new Tuple2(str, clazz);
        String str2 = tuple22._1();
        Class<?> clazz2 = tuple22._2();
        ExtendedActorSystem system = (ExtendedActorSystem)logSource.context().system();
        return new DiagnosticMarkerBusLoggingAdapter(system.eventStream(), str2, clazz2, system.logFilter());
    }

    public LoggingAdapter getLogger(ActorSystem system, Object logSource) {
        Tuple2<String, Class<?>> tuple2 = LogSource$.MODULE$.fromAnyRef(logSource, system);
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        String str = tuple2._1();
        Class<?> clazz = tuple2._2();
        Tuple2 tuple22 = new Tuple2(str, clazz);
        String str2 = tuple22._1();
        Class<?> clazz2 = tuple22._2();
        return new BusLogging(system.eventStream(), str2, clazz2, ((ExtendedActorSystem)system).logFilter());
    }

    public LoggingAdapter getLogger(LoggingBus bus, Object logSource) {
        Tuple2<String, Class<?>> tuple2 = LogSource$.MODULE$.fromAnyRef(logSource);
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        String str = tuple2._1();
        Class<?> clazz = tuple2._2();
        Tuple2 tuple22 = new Tuple2(str, clazz);
        String str2 = tuple22._1();
        Class<?> clazz2 = tuple22._2();
        return new BusLogging(bus, str2, clazz2);
    }

    public DiagnosticLoggingAdapter getLogger(Actor logSource) {
        return this.apply(logSource);
    }

    public Logging$Error$NoCause$ noCause() {
        return Logging$Error$NoCause$.MODULE$;
    }

    public Logging$LoggerInitialized$ loggerInitialized() {
        return Logging$LoggerInitialized$.MODULE$;
    }

    public Logging.SerializedStandardOutLogger akka$event$Logging$$serializedStandardOutLogger() {
        return akka$event$Logging$$serializedStandardOutLogger;
    }

    public Logging.StandardOutLogger StandardOutLogger() {
        return StandardOutLogger;
    }

    public String stackTraceFor(Throwable e) {
        Throwable throwable = e;
        if (throwable == null ? true : Logging$Error$NoCause$.MODULE$.equals(throwable)) {
            return "";
        }
        if (throwable instanceof NoStackTrace) {
            return new StringBuilder(5).append(" (").append(e.getClass().getName()).append(": ").append(e.getMessage()).append(")").toString();
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.append('\n');
        throwable.printStackTrace(pw);
        return sw.toString();
    }

    public Map<String, Object> emptyMDC() {
        return emptyMDC;
    }

    private Logging$() {
    }
}

