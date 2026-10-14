/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import scala.concurrent.duration.Duration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e1qAA\u0002\u0011\u0002G\u0005\u0001\u0002C\u0003\u0010\u0001\u0019\u0005\u0001C\u0001\u000fC_VtG-\u001a3NKN\u001c\u0018mZ3Rk\u0016,XmU3nC:$\u0018nY:\u000b\u0005\u0011)\u0011\u0001\u00033jgB\fGo\u00195\u000b\u0003\u0019\tA!Y6lC\u000e\u00011C\u0001\u0001\n!\tQQ\"D\u0001\f\u0015\u0005a\u0011!B:dC2\f\u0017B\u0001\b\f\u0005\u0019\te.\u001f*fM\u0006Y\u0001/^:i)&lWmT;u+\u0005\t\u0002C\u0001\n\u0018\u001b\u0005\u0019\"B\u0001\u000b\u0016\u0003!!WO]1uS>t'B\u0001\f\f\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u00031M\u0011\u0001\u0002R;sCRLwN\u001c")
public interface BoundedMessageQueueSemantics {
    public Duration pushTimeOut();
}

