/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.stage;

import akka.event.LoggingAdapter;
import akka.stream.Shape;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.StageLogging;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q1Qa\u0001\u0003\u0002\u0002-A\u0001b\u0005\u0001\u0003\u0002\u0003\u0006I\u0001\u0006\u0005\u00061\u0001!\t!\u0007\u0002\u001b\u000fJ\f\u0007\u000f[*uC\u001e,Gj\\4jG^KG\u000f\u001b'pO\u001eLgn\u001a\u0006\u0003\u000b\u0019\tQa\u001d;bO\u0016T!a\u0002\u0005\u0002\rM$(/Z1n\u0015\u0005I\u0011\u0001B1lW\u0006\u001c\u0001aE\u0002\u0001\u0019A\u0001\"!\u0004\b\u000e\u0003\u0011I!a\u0004\u0003\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000e\u0004\"!D\t\n\u0005I!!\u0001D*uC\u001e,Gj\\4hS:<\u0017AB0tQ\u0006\u0004X\r\u0005\u0002\u0016-5\ta!\u0003\u0002\u0018\r\t)1\u000b[1qK\u00061A(\u001b8jiz\"\"AG\u000e\u0011\u00055\u0001\u0001\"B\n\u0003\u0001\u0004!\u0002")
public abstract class GraphStageLogicWithLogging
extends GraphStageLogic
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

    public GraphStageLogicWithLogging(Shape _shape) {
        super(_shape);
        StageLogging.$init$(this);
    }
}

