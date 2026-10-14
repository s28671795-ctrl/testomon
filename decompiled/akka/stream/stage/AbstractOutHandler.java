/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.stage;

import akka.stream.stage.OutHandler;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a1QAA\u0002\u0002\u0002)AQ!\u0006\u0001\u0005\u0002Y\u0011!#\u00112tiJ\f7\r^(vi\"\u000bg\u000e\u001a7fe*\u0011A!B\u0001\u0006gR\fw-\u001a\u0006\u0003\r\u001d\taa\u001d;sK\u0006l'\"\u0001\u0005\u0002\t\u0005\\7.Y\u0002\u0001'\r\u00011\"\u0005\t\u0003\u0019=i\u0011!\u0004\u0006\u0002\u001d\u0005)1oY1mC&\u0011\u0001#\u0004\u0002\u0007\u0003:L(+\u001a4\u0011\u0005I\u0019R\"A\u0002\n\u0005Q\u0019!AC(vi\"\u000bg\u000e\u001a7fe\u00061A(\u001b8jiz\"\u0012a\u0006\t\u0003%\u0001\u0001")
public abstract class AbstractOutHandler
implements OutHandler {
    @Override
    public void onDownstreamFinish() throws Exception {
        OutHandler.onDownstreamFinish$(this);
    }

    @Override
    public void onDownstreamFinish(Throwable cause) throws Exception {
        OutHandler.onDownstreamFinish$(this, cause);
    }

    public AbstractOutHandler() {
        OutHandler.$init$(this);
    }
}

