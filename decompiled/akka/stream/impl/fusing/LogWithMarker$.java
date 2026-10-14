/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.actor.ActorSystem;
import akka.annotation.InternalApi;
import akka.event.LogMarker;
import akka.event.LogSource;
import akka.event.LogSource$;
import akka.event.Logging$;
import akka.event.MarkerLoggingAdapter;
import akka.stream.Attributes;
import akka.stream.Attributes$LogLevels$;
import akka.stream.Materializer;
import akka.stream.impl.fusing.LogWithMarker;
import java.io.Serializable;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple4;
import scala.runtime.ModuleSerializationProxy;

@InternalApi
public final class LogWithMarker$
implements Serializable {
    public static final LogWithMarker$ MODULE$ = new LogWithMarker$();
    private static final LogSource<Materializer> fromMaterializer = new LogSource<Materializer>(){

        public String genString(Object t2, ActorSystem system) {
            return LogSource.genString$(this, t2, system);
        }

        public Class<?> getClazz(Materializer t2) {
            return Materializer.class;
        }

        public String genString(Materializer t2) {
            String string;
            try {
                string = new StringBuilder(27).append("akka.stream.LogWithMarker").append("(").append(t2.supervisor().path()).append(")").toString();
            }
            catch (Exception exception) {
                string = LogSource$.MODULE$.fromString().genString("akka.stream.LogWithMarker");
            }
            return string;
        }
        {
            LogSource.$init$(this);
        }
    };
    private static final int akka$stream$impl$fusing$LogWithMarker$$OffInt = Attributes$LogLevels$.MODULE$.Off();
    private static final Attributes.LogLevels akka$stream$impl$fusing$LogWithMarker$$DefaultLogLevels = new Attributes.LogLevels(Logging$.MODULE$.DebugLevel(), Logging$.MODULE$.DebugLevel(), Logging$.MODULE$.ErrorLevel());

    public final LogSource<Materializer> fromMaterializer() {
        return fromMaterializer;
    }

    private final String DefaultLoggerName() {
        return "akka.stream.LogWithMarker";
    }

    public final int akka$stream$impl$fusing$LogWithMarker$$OffInt() {
        return akka$stream$impl$fusing$LogWithMarker$$OffInt;
    }

    public final Attributes.LogLevels akka$stream$impl$fusing$LogWithMarker$$DefaultLogLevels() {
        return akka$stream$impl$fusing$LogWithMarker$$DefaultLogLevels;
    }

    public <T> LogWithMarker<T> apply(String name, Function1<T, LogMarker> marker, Function1<T, Object> extract, Option<MarkerLoggingAdapter> logAdapter) {
        return new LogWithMarker<T>(name, marker, extract, logAdapter);
    }

    public <T> Option<Tuple4<String, Function1<T, LogMarker>, Function1<T, Object>, Option<MarkerLoggingAdapter>>> unapply(LogWithMarker<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple4<String, Function1<T, LogMarker>, Function1<T, Object>, Option<MarkerLoggingAdapter>>>(new Tuple4<String, Function1<T, LogMarker>, Function1<T, Object>, Option<MarkerLoggingAdapter>>(x$0.name(), x$0.marker(), x$0.extract(), x$0.logAdapter()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(LogWithMarker$.class);
    }

    private LogWithMarker$() {
    }
}

