/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.stage;

import akka.stream.stage.InHandler;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a1QAA\u0002\u0002\u0002)AQ!\u0006\u0001\u0005\u0002Y\u0011\u0011#\u00112tiJ\f7\r^%o\u0011\u0006tG\r\\3s\u0015\t!Q!A\u0003ti\u0006<WM\u0003\u0002\u0007\u000f\u000511\u000f\u001e:fC6T\u0011\u0001C\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0007\u0001Y\u0011\u0003\u0005\u0002\r\u001f5\tQBC\u0001\u000f\u0003\u0015\u00198-\u00197b\u0013\t\u0001RB\u0001\u0004B]f\u0014VM\u001a\t\u0003%Mi\u0011aA\u0005\u0003)\r\u0011\u0011\"\u00138IC:$G.\u001a:\u0002\rqJg.\u001b;?)\u00059\u0002C\u0001\n\u0001\u0001")
public abstract class AbstractInHandler
implements InHandler {
    @Override
    public void onUpstreamFinish() throws Exception {
        InHandler.onUpstreamFinish$(this);
    }

    @Override
    public void onUpstreamFailure(Throwable ex) throws Exception {
        InHandler.onUpstreamFailure$(this, ex);
    }

    public AbstractInHandler() {
        InHandler.$init$(this);
    }
}

