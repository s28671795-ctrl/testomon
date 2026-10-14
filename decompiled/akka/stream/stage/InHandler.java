/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.stage;

import akka.stream.impl.fusing.GraphInterpreter$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U2q!\u0002\u0004\u0011\u0002\u0007\u0005Q\u0002C\u0003\u0015\u0001\u0011\u0005Q\u0003C\u0003\u001a\u0001\u0019\u0005Q\u0003C\u0003,\u0001\u0011\u0005Q\u0003C\u0003.\u0001\u0011\u0005aFA\u0005J]\"\u000bg\u000e\u001a7fe*\u0011q\u0001C\u0001\u0006gR\fw-\u001a\u0006\u0003\u0013)\taa\u001d;sK\u0006l'\"A\u0006\u0002\t\u0005\\7.Y\u0002\u0001'\t\u0001a\u0002\u0005\u0002\u0010%5\t\u0001CC\u0001\u0012\u0003\u0015\u00198-\u00197b\u0013\t\u0019\u0002C\u0001\u0004B]f\u0014VMZ\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003Y\u0001\"aD\f\n\u0005a\u0001\"\u0001B+oSR\faa\u001c8QkND\u0007f\u0001\u0002\u001cUA\u0019q\u0002\b\u0010\n\u0005u\u0001\"A\u0002;ie><8\u000f\u0005\u0002 O9\u0011\u0001%\n\b\u0003C\u0011j\u0011A\t\u0006\u0003G1\ta\u0001\u0010:p_Rt\u0014\"A\t\n\u0005\u0019\u0002\u0012a\u00029bG.\fw-Z\u0005\u0003Q%\u0012\u0011\"\u0012=dKB$\u0018n\u001c8\u000b\u0005\u0019\u00022%\u0001\u0010\u0002!=tW\u000b]:ue\u0016\fWNR5oSND\u0007fA\u0002\u001cU\u0005\trN\\+qgR\u0014X-Y7GC&dWO]3\u0015\u0005Yy\u0003\"\u0002\u0019\u0005\u0001\u0004\t\u0014AA3y!\ty\"'\u0003\u00024S\tIA\u000b\u001b:po\u0006\u0014G.\u001a\u0015\u0004\tmQ\u0003")
public interface InHandler {
    public void onPush() throws Exception;

    public static /* synthetic */ void onUpstreamFinish$(InHandler $this) {
        $this.onUpstreamFinish();
    }

    default public void onUpstreamFinish() throws Exception {
        GraphInterpreter$.MODULE$.currentInterpreter().activeStage().completeStage();
    }

    public static /* synthetic */ void onUpstreamFailure$(InHandler $this, Throwable ex) {
        $this.onUpstreamFailure(ex);
    }

    default public void onUpstreamFailure(Throwable ex) throws Exception {
        GraphInterpreter$.MODULE$.currentInterpreter().activeStage().failStage(ex);
    }

    public static void $init$(InHandler $this) {
    }
}

