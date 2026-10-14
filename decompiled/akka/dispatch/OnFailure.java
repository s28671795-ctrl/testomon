/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.japi;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q2Q\u0001B\u0003\u0002\u0002)AQ!\t\u0001\u0005\u0002\tBQ\u0001\n\u0001\u0005V\u0015BQ\u0001\f\u0001\u0007\u00025\u0012\u0011b\u00148GC&dWO]3\u000b\u0005\u00199\u0011\u0001\u00033jgB\fGo\u00195\u000b\u0003!\tA!Y6lC\u000e\u00011C\u0001\u0001\f!\ra\u0001c\u0005\b\u0003\u001b9i\u0011!B\u0005\u0003\u001f\u0015\tAA[1qS&\u0011\u0011C\u0005\u0002\u000f\u0007\u0006dGNY1dW\n\u0013\u0018\u000eZ4f\u0015\tyQ\u0001\u0005\u0002\u0015=9\u0011Qc\u0007\b\u0003-ei\u0011a\u0006\u0006\u00031%\ta\u0001\u0010:p_Rt\u0014\"\u0001\u000e\u0002\u000bM\u001c\u0017\r\\1\n\u0005qi\u0012a\u00029bG.\fw-\u001a\u0006\u00025%\u0011q\u0004\t\u0002\n)\"\u0014xn^1cY\u0016T!\u0001H\u000f\u0002\rqJg.\u001b;?)\u0005\u0019\u0003CA\u0007\u0001\u0003!Ig\u000e^3s]\u0006dGC\u0001\u0014+!\t9\u0003&D\u0001\u001e\u0013\tISD\u0001\u0003V]&$\b\"B\u0016\u0003\u0001\u0004\u0019\u0012a\u00024bS2,(/Z\u0001\n_:4\u0015-\u001b7ve\u0016$\"A\n\u0018\t\u000b-\u001a\u0001\u0019A\n)\u0007\r\u00014\u0007E\u0002(cMI!AM\u000f\u0003\rQD'o\\<tG\u0005\u0019\u0002")
public abstract class OnFailure
extends japi.CallbackBridge<Throwable> {
    @Override
    public final void internal(Throwable failure) {
        this.onFailure(failure);
    }

    public abstract void onFailure(Throwable var1) throws Throwable;
}

