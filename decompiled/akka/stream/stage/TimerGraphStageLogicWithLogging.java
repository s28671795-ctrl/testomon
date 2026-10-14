/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.stage;

import akka.event.LoggingAdapter;
import akka.stream.Shape;
import akka.stream.stage.StageLogging;
import akka.stream.stage.TimerGraphStageLogic;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q1Qa\u0001\u0003\u0002\u0002-A\u0001b\u0005\u0001\u0003\u0002\u0003\u0006I\u0001\u0006\u0005\u00061\u0001!\t!\u0007\u0002 )&lWM]$sCBD7\u000b^1hK2{w-[2XSRDGj\\4hS:<'BA\u0003\u0007\u0003\u0015\u0019H/Y4f\u0015\t9\u0001\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u0013\u0005!\u0011m[6b\u0007\u0001\u00192\u0001\u0001\u0007\u0011!\tia\"D\u0001\u0005\u0013\tyAA\u0001\u000bUS6,'o\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\t\u0003\u001bEI!A\u0005\u0003\u0003\u0019M#\u0018mZ3M_\u001e<\u0017N\\4\u0002\r}\u001b\b.\u00199f!\t)b#D\u0001\u0007\u0013\t9bAA\u0003TQ\u0006\u0004X-\u0001\u0004=S:LGO\u0010\u000b\u00035m\u0001\"!\u0004\u0001\t\u000bM\u0011\u0001\u0019\u0001\u000b")
public abstract class TimerGraphStageLogicWithLogging
extends TimerGraphStageLogic
implements StageLogging {
    private LoggingAdapter akka$stream$stage$StageLogging$$_log;

    @Override
    public Class<?> logSource() {
        return StageLogging.logSource$(this);
    }

    @Override
    public LoggingAdapter log() {
        return StageLogging.log$(this);
    }

    @Override
    public LoggingAdapter akka$stream$stage$StageLogging$$_log() {
        return this.akka$stream$stage$StageLogging$$_log;
    }

    @Override
    public void akka$stream$stage$StageLogging$$_log_$eq(LoggingAdapter x$1) {
        this.akka$stream$stage$StageLogging$$_log = x$1;
    }

    public TimerGraphStageLogicWithLogging(Shape _shape) {
        super(_shape);
        StageLogging.$init$(this);
    }
}

