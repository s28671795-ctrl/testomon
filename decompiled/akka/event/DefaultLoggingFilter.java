/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.ActorSystem;
import akka.event.EventStream;
import akka.event.LogMarker;
import akka.event.Logging;
import akka.event.Logging$;
import akka.event.Logging$LogLevel$;
import akka.event.LoggingFilterWithMarker;
import java.io.Serializable;
import scala.Function0;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m4A\u0001C\u0005\u0001\u001d!A\u0011\u0004\u0001B\u0001B\u0003%!\u0004C\u0003%\u0001\u0011\u0005Q\u0005C\u0003%\u0001\u0011\u0005\u0001\u0006C\u0003A\u0001\u0011\u0005\u0011\tC\u0003a\u0001\u0011\u0005\u0011\rC\u0003j\u0001\u0011\u0005!\u000eC\u0003s\u0001\u0011\u00051O\u0001\u000bEK\u001a\fW\u000f\u001c;M_\u001e<\u0017N\\4GS2$XM\u001d\u0006\u0003\u0015-\tQ!\u001a<f]RT\u0011\u0001D\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0007\u0001yQ\u0003\u0005\u0002\u0011'5\t\u0011CC\u0001\u0013\u0003\u0015\u00198-\u00197b\u0013\t!\u0012C\u0001\u0004B]f\u0014VM\u001a\t\u0003-]i\u0011!C\u0005\u00031%\u0011q\u0003T8hO&twMR5mi\u0016\u0014x+\u001b;i\u001b\u0006\u00148.\u001a:\u0002\u00111|w\rT3wK2\u00042\u0001E\u000e\u001e\u0013\ta\u0012CA\u0005Gk:\u001cG/[8oaA\u0011a$\t\b\u0003-}I!\u0001I\u0005\u0002\u000f1{wmZ5oO&\u0011!e\t\u0002\t\u0019><G*\u001a<fY*\u0011\u0001%C\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\u0019:\u0003C\u0001\f\u0001\u0011\u0015I\"\u00011\u0001\u001b)\r1\u0013f\u000f\u0005\u0006U\r\u0001\raK\u0001\tg\u0016$H/\u001b8hgB\u0011A\u0006\u000f\b\u0003[Ur!AL\u001a\u000f\u0005=\u0012T\"\u0001\u0019\u000b\u0005Ej\u0011A\u0002\u001fs_>$h(C\u0001\r\u0013\t!4\"A\u0003bGR|'/\u0003\u00027o\u0005Y\u0011i\u0019;peNK8\u000f^3n\u0015\t!4\"\u0003\u0002:u\tA1+\u001a;uS:<7O\u0003\u00027o!)Ah\u0001a\u0001{\u0005YQM^3oiN#(/Z1n!\t1b(\u0003\u0002@\u0013\tYQI^3oiN#(/Z1n\u00039I7/\u0012:s_J,e.\u00192mK\u0012$2AQ#\\!\t\u00012)\u0003\u0002E#\t9!i\\8mK\u0006t\u0007\"\u0002$\u0005\u0001\u00049\u0015\u0001\u00037pO\u000ec\u0017m]:1\u0005!\u0013\u0006cA%N!:\u0011!j\u0013\t\u0003_EI!\u0001T\t\u0002\rA\u0013X\rZ3g\u0013\tquJA\u0003DY\u0006\u001c8O\u0003\u0002M#A\u0011\u0011K\u0015\u0007\u0001\t%\u0019V)!A\u0001\u0002\u000b\u0005AK\u0001\u0003`IY*\u0014CA+Y!\t\u0001b+\u0003\u0002X#\t9aj\u001c;iS:<\u0007C\u0001\tZ\u0013\tQ\u0016CA\u0002B]fDQ\u0001\u0018\u0003A\u0002u\u000b\u0011\u0002\\8h'>,(oY3\u0011\u0005%s\u0016BA0P\u0005\u0019\u0019FO]5oO\u0006\u0001\u0012n],be:LgnZ#oC\ndW\r\u001a\u000b\u0004\u0005\nD\u0007\"\u0002$\u0006\u0001\u0004\u0019\u0007G\u00013g!\rIU*\u001a\t\u0003#\u001a$\u0011b\u001a2\u0002\u0002\u0003\u0005)\u0011\u0001+\u0003\t}#cG\u000e\u0005\u00069\u0016\u0001\r!X\u0001\u000eSNLeNZ8F]\u0006\u0014G.\u001a3\u0015\u0007\t[\u0017\u000fC\u0003G\r\u0001\u0007A\u000e\r\u0002n_B\u0019\u0011*\u00148\u0011\u0005E{G!\u00039l\u0003\u0003\u0005\tQ!\u0001U\u0005\u0011yFEN\u001c\t\u000bq3\u0001\u0019A/\u0002\u001d%\u001cH)\u001a2vO\u0016s\u0017M\u00197fIR\u0019!\t\u001e>\t\u000b\u0019;\u0001\u0019A;1\u0005YD\bcA%NoB\u0011\u0011\u000b\u001f\u0003\nsR\f\t\u0011!A\u0003\u0002Q\u0013Aa\u0018\u00137q!)Al\u0002a\u0001;\u0002")
public class DefaultLoggingFilter
implements LoggingFilterWithMarker {
    private final Function0<Logging.LogLevel> logLevel;

    @Override
    public boolean isErrorEnabled(Class<?> logClass, String logSource, LogMarker marker) {
        return LoggingFilterWithMarker.isErrorEnabled$(this, logClass, logSource, marker);
    }

    @Override
    public boolean isWarningEnabled(Class<?> logClass, String logSource, LogMarker marker) {
        return LoggingFilterWithMarker.isWarningEnabled$(this, logClass, logSource, marker);
    }

    @Override
    public boolean isInfoEnabled(Class<?> logClass, String logSource, LogMarker marker) {
        return LoggingFilterWithMarker.isInfoEnabled$(this, logClass, logSource, marker);
    }

    @Override
    public boolean isDebugEnabled(Class<?> logClass, String logSource, LogMarker marker) {
        return LoggingFilterWithMarker.isDebugEnabled$(this, logClass, logSource, marker);
    }

    @Override
    public boolean isErrorEnabled(Class<?> logClass, String logSource) {
        return Logging$LogLevel$.MODULE$.$greater$eq$extension(this.logLevel.apply().asInt(), Logging$.MODULE$.ErrorLevel());
    }

    @Override
    public boolean isWarningEnabled(Class<?> logClass, String logSource) {
        return Logging$LogLevel$.MODULE$.$greater$eq$extension(this.logLevel.apply().asInt(), Logging$.MODULE$.WarningLevel());
    }

    @Override
    public boolean isInfoEnabled(Class<?> logClass, String logSource) {
        return Logging$LogLevel$.MODULE$.$greater$eq$extension(this.logLevel.apply().asInt(), Logging$.MODULE$.InfoLevel());
    }

    @Override
    public boolean isDebugEnabled(Class<?> logClass, String logSource) {
        return Logging$LogLevel$.MODULE$.$greater$eq$extension(this.logLevel.apply().asInt(), Logging$.MODULE$.DebugLevel());
    }

    public DefaultLoggingFilter(Function0<Logging.LogLevel> logLevel) {
        this.logLevel = logLevel;
        LoggingFilterWithMarker.$init$(this);
    }

    public DefaultLoggingFilter(ActorSystem.Settings settings, EventStream eventStream) {
        this((Function0<Logging.LogLevel>)((Object)new Serializable(eventStream){
            private static final long serialVersionUID = 0L;
            private final EventStream eventStream$1;

            public final int apply() {
                return this.eventStream$1.logLevel();
            }
            {
                this.eventStream$1 = eventStream$1;
            }
        }));
    }
}

