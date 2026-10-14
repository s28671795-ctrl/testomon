/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor;
import akka.event.DiagnosticLoggingAdapter;
import akka.event.Logging$;
import scala.PartialFunction;
import scala.collection.immutable.Map;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005I3qAB\u0004\u0011\u0002\u0007\u0005A\u0002C\u0003\u0018\u0001\u0011\u0005\u0001\u0004C\u0004\u001d\u0001\t\u0007I\u0011A\u000f\t\u000b\u0011\u0002A\u0011A\u0013\t\r\u0001\u0003A\u0011K\u0005B\u0011-i\u0005\u0001%A\u0002\u0002\u0003%IAT)\u0003-\u0011K\u0017m\u001a8pgRL7-Q2u_JdunZ4j]\u001eT!\u0001C\u0005\u0002\u000b\u0005\u001cGo\u001c:\u000b\u0003)\tA!Y6lC\u000e\u00011c\u0001\u0001\u000e'A\u0011a\"E\u0007\u0002\u001f)\t\u0001#A\u0003tG\u0006d\u0017-\u0003\u0002\u0013\u001f\t1\u0011I\\=SK\u001a\u0004\"\u0001F\u000b\u000e\u0003\u001dI!AF\u0004\u0003\u000b\u0005\u001bGo\u001c:\u0002\r\u0011Jg.\u001b;%)\u0005I\u0002C\u0001\b\u001b\u0013\tYrB\u0001\u0003V]&$\u0018a\u00017pOV\ta\u0004\u0005\u0002 E5\t\u0001E\u0003\u0002\"\u0013\u0005)QM^3oi&\u00111\u0005\t\u0002\u0019\t&\fwM\\8ti&\u001cGj\\4hS:<\u0017\tZ1qi\u0016\u0014\u0018aA7eGR\u0011a\u0005\u000e\t\u0003OEr!\u0001K\u0018\u000f\u0005%rcB\u0001\u0016.\u001b\u0005Y#B\u0001\u0017\f\u0003\u0019a$o\\8u}%\t!\"\u0003\u0002\"\u0013%\u0011\u0001\u0007I\u0001\b\u0019><w-\u001b8h\u0013\t\u00114GA\u0002N\t\u000eS!\u0001\r\u0011\t\u000bU\u001a\u0001\u0019\u0001\u001c\u0002\u001d\r,(O]3oi6+7o]1hKB\u0011abN\u0005\u0003q=\u00111!\u00118zQ\t!$\b\u0005\u0002<}5\tAH\u0003\u0002>\u0013\u0005!Q\u000f^5m\u0013\tyDH\u0001\u0004v]V\u001cX\rZ\u0001\u000eCJ|WO\u001c3SK\u000e,\u0017N^3\u0015\u0007e\u00115\nC\u0003D\t\u0001\u0007A)A\u0004sK\u000e,\u0017N^3\u0011\u0005\u0015CeB\u0001\u000bG\u0013\t9u!A\u0003BGR|'/\u0003\u0002J\u0015\n9!+Z2fSZ,'BA$\b\u0011\u0015aE\u00011\u00017\u0003\ri7oZ\u0001\u0014gV\u0004XM\u001d\u0013be>,h\u000e\u001a*fG\u0016Lg/\u001a\u000b\u00043=\u0003\u0006\"B\"\u0006\u0001\u0004!\u0005\"\u0002'\u0006\u0001\u00041\u0014B\u0001!\u0016\u0001")
public interface DiagnosticActorLogging
extends Actor {
    public void akka$actor$DiagnosticActorLogging$_setter_$log_$eq(DiagnosticLoggingAdapter var1);

    public /* synthetic */ void akka$actor$DiagnosticActorLogging$$super$aroundReceive(PartialFunction var1, Object var2);

    public DiagnosticLoggingAdapter log();

    public static /* synthetic */ Map mdc$(DiagnosticActorLogging $this, Object currentMessage) {
        return $this.mdc(currentMessage);
    }

    default public Map<String, Object> mdc(Object currentMessage) {
        return Logging$.MODULE$.emptyMDC();
    }

    public static /* synthetic */ void aroundReceive$(DiagnosticActorLogging $this, PartialFunction receive2, Object msg) {
        $this.aroundReceive(receive2, msg);
    }

    @Override
    default public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        try {
            this.log().mdc(this.mdc(msg));
            this.akka$actor$DiagnosticActorLogging$$super$aroundReceive(receive2, msg);
        }
        finally {
            this.log().clearMDC();
        }
    }

    public static void $init$(DiagnosticActorLogging $this) {
        $this.akka$actor$DiagnosticActorLogging$_setter_$log_$eq(Logging$.MODULE$.apply($this));
    }
}

