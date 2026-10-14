/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.actor.ActorSystem;
import akka.annotation.InternalApi;
import akka.event.LogSource;
import akka.event.LogSource$;
import akka.event.Logging$;
import akka.event.LoggingAdapter;
import akka.stream.Attributes;
import akka.stream.Attributes$LogLevels$;
import akka.stream.Materializer;
import akka.stream.impl.fusing.Log;
import java.io.Serializable;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.ModuleSerializationProxy;

@InternalApi
public final class Log$
implements Serializable {
    public static final Log$ MODULE$ = new Log$();
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
                string = new StringBuilder(17).append("akka.stream.Log").append("(").append(t2.supervisor().path()).append(")").toString();
            }
            catch (Exception exception) {
                string = LogSource$.MODULE$.fromString().genString("akka.stream.Log");
            }
            return string;
        }
        {
            LogSource.$init$(this);
        }
    };
    private static final int akka$stream$impl$fusing$Log$$OffInt = Attributes$LogLevels$.MODULE$.Off();
    private static final Attributes.LogLevels akka$stream$impl$fusing$Log$$DefaultLogLevels = new Attributes.LogLevels(Logging$.MODULE$.DebugLevel(), Logging$.MODULE$.DebugLevel(), Logging$.MODULE$.ErrorLevel());

    public final LogSource<Materializer> fromMaterializer() {
        return fromMaterializer;
    }

    private final String DefaultLoggerName() {
        return "akka.stream.Log";
    }

    public final int akka$stream$impl$fusing$Log$$OffInt() {
        return akka$stream$impl$fusing$Log$$OffInt;
    }

    public final Attributes.LogLevels akka$stream$impl$fusing$Log$$DefaultLogLevels() {
        return akka$stream$impl$fusing$Log$$DefaultLogLevels;
    }

    public <T> Log<T> apply(String name, Function1<T, Object> extract, Option<LoggingAdapter> logAdapter) {
        return new Log<T>(name, extract, logAdapter);
    }

    public <T> Option<Tuple3<String, Function1<T, Object>, Option<LoggingAdapter>>> unapply(Log<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<String, Function1<T, Object>, Option<LoggingAdapter>>>(new Tuple3<String, Function1<T, Object>, Option<LoggingAdapter>>(x$0.name(), x$0.extract(), x$0.logAdapter()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Log$.class);
    }

    private Log$() {
    }
}

