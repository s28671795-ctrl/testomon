/*
 * Decompiled with CFR 0.152.
 */
package akka.event.jul;

import akka.actor.ActorSystem;
import akka.event.EventStream;
import akka.event.Logging$;
import akka.event.Logging$LogLevel$;
import akka.event.LoggingFilter;
import akka.event.jul.Logger$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005a\u0001\u0002\u0005\n\u0001AA\u0001b\u0007\u0001\u0003\u0002\u0003\u0006I\u0001\b\u0005\t[\u0001\u0011\t\u0011)A\u0005]!)\u0011\u0007\u0001C\u0001e!)\u0001\b\u0001C\u0001s!)1\f\u0001C\u00019\")A\r\u0001C\u0001K\")Q\u000e\u0001C\u0001]\n\t\"*\u0019<b\u0019><w-\u001b8h\r&dG/\u001a:\u000b\u0005)Y\u0011a\u00016vY*\u0011A\"D\u0001\u0006KZ,g\u000e\u001e\u0006\u0002\u001d\u0005!\u0011m[6b\u0007\u0001\u00192\u0001A\t\u0018!\t\u0011R#D\u0001\u0014\u0015\u0005!\u0012!B:dC2\f\u0017B\u0001\f\u0014\u0005\u0019\te.\u001f*fMB\u0011\u0001$G\u0007\u0002\u0017%\u0011!d\u0003\u0002\u000e\u0019><w-\u001b8h\r&dG/\u001a:\u0002\u0011M,G\u000f^5oON\u0004\"!H\u0012\u000f\u0005y\tS\"A\u0010\u000b\u0005\u0001j\u0011!B1di>\u0014\u0018B\u0001\u0012 \u0003-\t5\r^8s'f\u001cH/Z7\n\u0005\u0011*#\u0001C*fiRLgnZ:\u000b\u0005\tz\u0002FA\u0001(!\tA3&D\u0001*\u0015\tQS\"\u0001\u0003vi&d\u0017B\u0001\u0017*\u0005\u0019)h.^:fI\u0006YQM^3oiN#(/Z1n!\tAr&\u0003\u00021\u0017\tYQI^3oiN#(/Z1n\u0003\u0019a\u0014N\\5u}Q\u00191'N\u001c\u0011\u0005Q\u0002Q\"A\u0005\t\u000bm\u0019\u0001\u0019\u0001\u000f)\u0005U:\u0003\"B\u0017\u0004\u0001\u0004q\u0013AD5t\u000bJ\u0014xN]#oC\ndW\r\u001a\u000b\u0004uu2\u0006C\u0001\n<\u0013\ta4CA\u0004C_>dW-\u00198\t\u000by\"\u0001\u0019A \u0002\u00111|wm\u00117bgN\u0004$\u0001Q'\u0011\u0007\u0005C5J\u0004\u0002C\rB\u00111iE\u0007\u0002\t*\u0011QiD\u0001\u0007yI|w\u000e\u001e \n\u0005\u001d\u001b\u0012A\u0002)sK\u0012,g-\u0003\u0002J\u0015\n)1\t\\1tg*\u0011qi\u0005\t\u0003\u00196c\u0001\u0001B\u0005O{\u0005\u0005\t\u0011!B\u0001\u001f\n\u0019q\f\n\u001a\u0012\u0005A\u001b\u0006C\u0001\nR\u0013\t\u00116CA\u0004O_RD\u0017N\\4\u0011\u0005I!\u0016BA+\u0014\u0005\r\te.\u001f\u0005\u0006/\u0012\u0001\r\u0001W\u0001\nY><7k\\;sG\u0016\u0004\"!Q-\n\u0005iS%AB*ue&tw-\u0001\tjg^\u000b'O\\5oO\u0016s\u0017M\u00197fIR\u0019!(X2\t\u000by*\u0001\u0019\u000101\u0005}\u000b\u0007cA!IAB\u0011A*\u0019\u0003\nEv\u000b\t\u0011!A\u0003\u0002=\u00131a\u0018\u00134\u0011\u00159V\u00011\u0001Y\u00035I7/\u00138g_\u0016s\u0017M\u00197fIR\u0019!H\u001a7\t\u000by2\u0001\u0019A41\u0005!T\u0007cA!ISB\u0011AJ\u001b\u0003\nW\u001a\f\t\u0011!A\u0003\u0002=\u00131a\u0018\u00135\u0011\u00159f\u00011\u0001Y\u00039I7\u000fR3ck\u001e,e.\u00192mK\u0012$2AO8v\u0011\u0015qt\u00011\u0001qa\t\t8\u000fE\u0002B\u0011J\u0004\"\u0001T:\u0005\u0013Q|\u0017\u0011!A\u0001\u0006\u0003y%aA0%k!)qk\u0002a\u00011\"2\u0001a\u001e>|{z\u0004\"A\u0005=\n\u0005e\u001c\"A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017%\u0001?\u0002?U\u001bX\rI*mMRRGj\\4hS:<g)\u001b7uKJ\u0004\u0013N\\:uK\u0006$g&A\u0003tS:\u001cW-I\u0001\u0000\u0003\u0015\u0011dF\u000e\u00181\u0001")
public class JavaLoggingFilter
implements LoggingFilter {
    private final EventStream eventStream;

    @Override
    public boolean isErrorEnabled(Class<?> logClass, String logSource) {
        return Logging$LogLevel$.MODULE$.$greater$eq$extension(this.eventStream.logLevel(), Logging$.MODULE$.ErrorLevel()) && Logger$.MODULE$.apply(logClass, logSource).isLoggable(Logger$.MODULE$.mapLevel(Logging$.MODULE$.ErrorLevel()));
    }

    @Override
    public boolean isWarningEnabled(Class<?> logClass, String logSource) {
        return Logging$LogLevel$.MODULE$.$greater$eq$extension(this.eventStream.logLevel(), Logging$.MODULE$.WarningLevel()) && Logger$.MODULE$.apply(logClass, logSource).isLoggable(Logger$.MODULE$.mapLevel(Logging$.MODULE$.WarningLevel()));
    }

    @Override
    public boolean isInfoEnabled(Class<?> logClass, String logSource) {
        return Logging$LogLevel$.MODULE$.$greater$eq$extension(this.eventStream.logLevel(), Logging$.MODULE$.InfoLevel()) && Logger$.MODULE$.apply(logClass, logSource).isLoggable(Logger$.MODULE$.mapLevel(Logging$.MODULE$.InfoLevel()));
    }

    @Override
    public boolean isDebugEnabled(Class<?> logClass, String logSource) {
        return Logging$LogLevel$.MODULE$.$greater$eq$extension(this.eventStream.logLevel(), Logging$.MODULE$.DebugLevel()) && Logger$.MODULE$.apply(logClass, logSource).isLoggable(Logger$.MODULE$.mapLevel(Logging$.MODULE$.DebugLevel()));
    }

    public JavaLoggingFilter(ActorSystem.Settings settings, EventStream eventStream) {
        this.eventStream = eventStream;
    }
}

