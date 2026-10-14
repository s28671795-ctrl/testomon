/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.stage;

import akka.stream.impl.fusing.GraphInterpreter$;
import akka.stream.stage.GraphStageLogic;
import java.io.Serializable;
import scala.Function0;
import scala.Predef$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00113q!\u0002\u0004\u0011\u0002\u0007\u0005Q\u0002C\u0003\u0015\u0001\u0011\u0005Q\u0003C\u0003\u001a\u0001\u0019\u0005Q\u0003C\u0003,\u0001\u0011\u0005Q\u0003C\u0003,\u0001\u0011\u0005QH\u0001\u0006PkRD\u0015M\u001c3mKJT!a\u0002\u0005\u0002\u000bM$\u0018mZ3\u000b\u0005%Q\u0011AB:ue\u0016\fWNC\u0001\f\u0003\u0011\t7n[1\u0004\u0001M\u0011\u0001A\u0004\t\u0003\u001fIi\u0011\u0001\u0005\u0006\u0002#\u0005)1oY1mC&\u00111\u0003\u0005\u0002\u0007\u0003:L(+\u001a4\u0002\r\u0011Jg.\u001b;%)\u00051\u0002CA\b\u0018\u0013\tA\u0002C\u0001\u0003V]&$\u0018AB8o!VdG\u000eK\u0002\u00037)\u00022a\u0004\u000f\u001f\u0013\ti\u0002C\u0001\u0004uQJ|wo\u001d\t\u0003?\u001dr!\u0001I\u0013\u000f\u0005\u0005\"S\"\u0001\u0012\u000b\u0005\rb\u0011A\u0002\u001fs_>$h(C\u0001\u0012\u0013\t1\u0003#A\u0004qC\u000e\\\u0017mZ3\n\u0005!J#!C#yG\u0016\u0004H/[8o\u0015\t1\u0003cI\u0001\u001f\u0003Iyg\u000eR8x]N$(/Z1n\r&t\u0017n\u001d5)\r\ri\u0003'M\u001a5!\tya&\u0003\u00020!\tQA-\u001a9sK\u000e\fG/\u001a3\u0002\u000f5,7o]1hK\u0006\n!'\u0001\u001aDC2d\u0007e\u001c8E_^t7\u000f\u001e:fC64\u0015N\\5tQ\u0002:\u0018\u000e\u001e5!C\u0002\u001a\u0017M\\2fY2\fG/[8oA\r\fWo]3/\u0003\u0015\u0019\u0018N\\2fC\u0005)\u0014!\u0002\u001a/m9\u0002\u0004FB\u00028ai\u001aD\u0007\u0005\u0002\u0010q%\u0011\u0011\b\u0005\u0002\u0015I\u0016\u0004(/Z2bi\u0016$wJ^3se&$\u0017N\\4\"\u0003m\nQh\u0014<feJLG-\u001a\u0011aI\u00164\u0007e\u001c8E_^t7\u000f\u001e:fC64\u0015N\\5tQ\"\u001a\u0017-^:fu\u0001\"\u0006N]8xC\ndW-\u000b1-A%t7\u000f^3bI:B3aA\u000e+)\t1b\bC\u0003@\t\u0001\u0007\u0001)A\u0003dCV\u001cX\r\u0005\u0002 \u0003&\u0011!)\u000b\u0002\n)\"\u0014xn^1cY\u0016D3\u0001B\u000e+\u0001")
public interface OutHandler {
    public void onPull() throws Exception;

    public static /* synthetic */ void onDownstreamFinish$(OutHandler $this) {
        $this.onDownstreamFinish();
    }

    default public void onDownstreamFinish() throws Exception {
        GraphStageLogic thisStage = GraphInterpreter$.MODULE$.currentInterpreter().activeStage();
        Predef$.MODULE$.require(thisStage.lastCancellationCause() != null, (Function0<Object>)(Function0<String> & Serializable)() -> "onDownstreamFinish() must not be called without a cancellation cause");
        thisStage.cancelStage(thisStage.lastCancellationCause());
    }

    public static /* synthetic */ void onDownstreamFinish$(OutHandler $this, Throwable cause) {
        $this.onDownstreamFinish(cause);
    }

    default public void onDownstreamFinish(Throwable cause) throws Exception {
        GraphStageLogic thisStage = GraphInterpreter$.MODULE$.currentInterpreter().activeStage();
        try {
            Predef$.MODULE$.require(cause != null, (Function0<Object>)(Function0<String> & Serializable)() -> "Cancellation cause must not be null");
            Predef$.MODULE$.require(thisStage.lastCancellationCause() == null, (Function0<Object>)(Function0<String> & Serializable)() -> "onDownstreamFinish(cause) must not be called recursively");
            thisStage.lastCancellationCause_$eq(cause);
            this.onDownstreamFinish();
        }
        finally {
            thisStage.lastCancellationCause_$eq(null);
        }
    }

    public static void $init$(OutHandler $this) {
    }
}

