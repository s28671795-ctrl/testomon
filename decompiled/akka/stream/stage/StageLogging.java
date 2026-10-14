/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.stage;

import akka.event.LoggingAdapter;
import akka.event.NoLogging$;
import akka.stream.Materializer;
import akka.stream.MaterializerLoggingProvider;
import akka.stream.stage.GraphStageLogic;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005!3\u0001BB\u0004\u0011\u0002\u0007\u0005a\u0002\u0011\u0005\u0006+\u0001!\tA\u0006\u0005\n5\u0001\u0001\r\u00111Q\u0005\nmA\u0011B\t\u0001A\u0002\u0003\u0007K\u0011B\u0012\t\u000b\u0019\u0002A\u0011C\u0014\t\u000b}\u0002A\u0011A\u000e\u0003\u0019M#\u0018mZ3M_\u001e<\u0017N\\4\u000b\u0005!I\u0011!B:uC\u001e,'B\u0001\u0006\f\u0003\u0019\u0019HO]3b[*\tA\"\u0001\u0003bW.\f7\u0001A\n\u0003\u0001=\u0001\"\u0001E\n\u000e\u0003EQ\u0011AE\u0001\u0006g\u000e\fG.Y\u0005\u0003)E\u0011a!\u00118z%\u00164\u0017A\u0002\u0013j]&$H\u0005F\u0001\u0018!\t\u0001\u0002$\u0003\u0002\u001a#\t!QK\\5u\u0003\u0011yFn\\4\u0016\u0003q\u0001\"!\b\u0011\u000e\u0003yQ!aH\u0006\u0002\u000b\u00154XM\u001c;\n\u0005\u0005r\"A\u0004'pO\u001eLgnZ!eCB$XM]\u0001\t?2|wm\u0018\u0013fcR\u0011q\u0003\n\u0005\bK\r\t\t\u00111\u0001\u001d\u0003\rAH%M\u0001\nY><7k\\;sG\u0016,\u0012\u0001\u000b\u0019\u0003SY\u00022AK\u00195\u001d\tYs\u0006\u0005\u0002-#5\tQF\u0003\u0002/\u001b\u00051AH]8pizJ!\u0001M\t\u0002\rA\u0013X\rZ3g\u0013\t\u00114GA\u0003DY\u0006\u001c8O\u0003\u00021#A\u0011QG\u000e\u0007\u0001\t%9D!!A\u0001\u0002\u000b\u0005\u0001HA\u0002`IE\n\"!\u000f\u001f\u0011\u0005AQ\u0014BA\u001e\u0012\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"\u0001E\u001f\n\u0005y\n\"aA!os\u0006\u0019An\\4\u0013\u0007\u0005\u001bUI\u0002\u0003C\u0001\u0001\u0001%\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004C\u0001#\u0001\u001b\u00059\u0001C\u0001#G\u0013\t9uAA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0001")
public interface StageLogging {
    public LoggingAdapter akka$stream$stage$StageLogging$$_log();

    public void akka$stream$stage$StageLogging$$_log_$eq(LoggingAdapter var1);

    public static /* synthetic */ Class logSource$(StageLogging $this) {
        return $this.logSource();
    }

    default public Class<?> logSource() {
        return this.getClass();
    }

    public static /* synthetic */ LoggingAdapter log$(StageLogging $this) {
        return $this.log();
    }

    default public LoggingAdapter log() {
        block2: {
            if (this.akka$stream$stage$StageLogging$$_log() != null) break block2;
            Materializer materializer = ((GraphStageLogic)((Object)this)).materializer();
            if (materializer instanceof MaterializerLoggingProvider) {
                Materializer materializer2 = materializer;
                this.akka$stream$stage$StageLogging$$_log_$eq(((MaterializerLoggingProvider)((Object)materializer2)).makeLogger(this.logSource()));
            } else {
                this.akka$stream$stage$StageLogging$$_log_$eq(NoLogging$.MODULE$);
            }
        }
        return this.akka$stream$stage$StageLogging$$_log();
    }

    public static void $init$(StageLogging $this) {
    }
}

