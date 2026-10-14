/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor;
import akka.event.LogSource$;
import akka.event.Logging$;
import akka.event.LoggingAdapter;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000512\u0001\"\u0002\u0004\u0011\u0002\u0007\u00051\u0002\n\u0005\u0006%\u0001!\ta\u0005\u0005\n/\u0001\u0001\r\u00111A\u0005\naA\u0011b\b\u0001A\u0002\u0003\u0007I\u0011\u0002\u0011\t\u000b\r\u0002A\u0011\u0001\r\u0003\u0019\u0005\u001bGo\u001c:M_\u001e<\u0017N\\4\u000b\u0005\u001dA\u0011!B1di>\u0014(\"A\u0005\u0002\t\u0005\\7.Y\u0002\u0001'\t\u0001A\u0002\u0005\u0002\u000e!5\taBC\u0001\u0010\u0003\u0015\u00198-\u00197b\u0013\t\tbB\u0001\u0004B]f\u0014VMZ\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003Q\u0001\"!D\u000b\n\u0005Yq!\u0001B+oSR\fAa\u00187pOV\t\u0011\u0004\u0005\u0002\u001b;5\t1D\u0003\u0002\u001d\u0011\u0005)QM^3oi&\u0011ad\u0007\u0002\u000f\u0019><w-\u001b8h\u0003\u0012\f\u0007\u000f^3s\u0003!yFn\\4`I\u0015\fHC\u0001\u000b\"\u0011\u001d\u00113!!AA\u0002e\t1\u0001\u001f\u00132\u0003\rawn\u001a\n\u0004K\u001dJc\u0001\u0002\u0014\u0001\u0001\u0011\u0012A\u0002\u0010:fM&tW-\\3oiz\u0002\"\u0001\u000b\u0001\u000e\u0003\u0019\u0001\"\u0001\u000b\u0016\n\u0005-2!!B!di>\u0014\b")
public interface ActorLogging {
    public LoggingAdapter akka$actor$ActorLogging$$_log();

    public void akka$actor$ActorLogging$$_log_$eq(LoggingAdapter var1);

    public static /* synthetic */ LoggingAdapter log$(ActorLogging $this) {
        return $this.log();
    }

    default public LoggingAdapter log() {
        block0: {
            if (this.akka$actor$ActorLogging$$_log() != null) break block0;
            this.akka$actor$ActorLogging$$_log_$eq(Logging$.MODULE$.apply(((Actor)((Object)this)).context().system(), this, LogSource$.MODULE$.fromActor()));
        }
        return this.akka$actor$ActorLogging$$_log();
    }

    public static void $init$(ActorLogging $this) {
    }
}

