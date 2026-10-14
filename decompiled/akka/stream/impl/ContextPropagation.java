/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalStableApi;
import akka.stream.impl.ContextPropagation$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y2q!\u0003\u0006\u0011\u0002G\u0005\u0011\u0003C\u0003\u0019\u0001\u0019\u0005\u0011\u0004C\u0003\u001e\u0001\u0019\u0005\u0011\u0004C\u0003\u001f\u0001\u0019\u0005q\u0004C\u0003\u001e\u0001\u0019\u0005\u0001eB\u0003+\u0015!\u00051FB\u0003\n\u0015!\u0005Q\u0006C\u0003/\r\u0011\u0005q\u0006C\u00031\r\u0011\u0005\u0011G\u0001\nD_:$X\r\u001f;Qe>\u0004\u0018mZ1uS>t'BA\u0006\r\u0003\u0011IW\u000e\u001d7\u000b\u00055q\u0011AB:ue\u0016\fWNC\u0001\u0010\u0003\u0011\t7n[1\u0004\u0001M\u0011\u0001A\u0005\t\u0003'Yi\u0011\u0001\u0006\u0006\u0002+\u0005)1oY1mC&\u0011q\u0003\u0006\u0002\u0007\u0003:L(+\u001a4\u0002\u001dM,8\u000f]3oI\u000e{g\u000e^3yiR\t!\u0004\u0005\u0002\u00147%\u0011A\u0004\u0006\u0002\u0005+:LG/A\u0007sKN,X.Z\"p]R,\u0007\u0010^\u0001\u000fGV\u0014(/\u001a8u\u0007>tG/\u001a=u)\u0005\u0011BC\u0001\u000e\"\u0011\u0015\u0011C\u00011\u0001\u0013\u0003\u001d\u0019wN\u001c;fqRD#\u0001\u0001\u0013\u0011\u0005\u0015BS\"\u0001\u0014\u000b\u0005\u001dr\u0011AC1o]>$\u0018\r^5p]&\u0011\u0011F\n\u0002\u0012\u0013:$XM\u001d8bYN#\u0018M\u00197f\u0003BL\u0017AE\"p]R,\u0007\u0010\u001e)s_B\fw-\u0019;j_:\u0004\"\u0001\f\u0004\u000e\u0003)\u0019\"A\u0002\n\u0002\rqJg.\u001b;?)\u0005Y\u0013!B1qa2LH#\u0001\u001a\u0011\u00051\u0002\u0001F\u0001\u0005%Q\t1A\u0005\u000b\u0002\u0006I\u0001")
@InternalStableApi
public interface ContextPropagation {
    @InternalStableApi
    public static ContextPropagation apply() {
        return ContextPropagation$.MODULE$.apply();
    }

    public void suspendContext();

    public void resumeContext();

    public Object currentContext();

    public void resumeContext(Object var1);
}

