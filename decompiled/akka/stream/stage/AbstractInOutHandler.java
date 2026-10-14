/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.stage;

import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m1QAA\u0002\u0002\u0002)AQ\u0001\u0007\u0001\u0005\u0002e\u0011A#\u00112tiJ\f7\r^%o\u001fV$\b*\u00198eY\u0016\u0014(B\u0001\u0003\u0006\u0003\u0015\u0019H/Y4f\u0015\t1q!\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u0011\u0005!\u0011m[6b\u0007\u0001\u0019B\u0001A\u0006\u0012+A\u0011AbD\u0007\u0002\u001b)\ta\"A\u0003tG\u0006d\u0017-\u0003\u0002\u0011\u001b\t1\u0011I\\=SK\u001a\u0004\"AE\n\u000e\u0003\rI!\u0001F\u0002\u0003\u0013%s\u0007*\u00198eY\u0016\u0014\bC\u0001\n\u0017\u0013\t92A\u0001\u0006PkRD\u0015M\u001c3mKJ\fa\u0001P5oSRtD#\u0001\u000e\u0011\u0005I\u0001\u0001")
public abstract class AbstractInOutHandler
implements InHandler,
OutHandler {
    @Override
    public void onDownstreamFinish() throws Exception {
        OutHandler.onDownstreamFinish$(this);
    }

    @Override
    public void onDownstreamFinish(Throwable cause) throws Exception {
        OutHandler.onDownstreamFinish$(this, cause);
    }

    @Override
    public void onUpstreamFinish() throws Exception {
        InHandler.onUpstreamFinish$(this);
    }

    @Override
    public void onUpstreamFailure(Throwable ex) throws Exception {
        InHandler.onUpstreamFailure$(this, ex);
    }

    public AbstractInOutHandler() {
        InHandler.$init$(this);
        OutHandler.$init$(this);
    }
}

