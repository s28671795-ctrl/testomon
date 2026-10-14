/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.japi;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00013Q\u0001B\u0003\u0002\u0002)AQA\t\u0001\u0005\u0002\rBQ!\n\u0001\u0005V\u0019BQ\u0001\f\u0001\u0007\u00025\u0012\u0011b\u00148Tk\u000e\u001cWm]:\u000b\u0005\u00199\u0011\u0001\u00033jgB\fGo\u00195\u000b\u0003!\tA!Y6lC\u000e\u0001QCA\u0006\u0017'\t\u0001A\u0002E\u0002\u000e#Qq!AD\b\u000e\u0003\u0015I!\u0001E\u0003\u0002\t)\f\u0007/[\u0005\u0003%M\u0011abQ1mY\n\f7m\u001b\"sS\u0012<WM\u0003\u0002\u0011\u000bA\u0011QC\u0006\u0007\u0001\t\u00199\u0002\u0001#b\u00011\t\tA+\u0005\u0002\u001a?A\u0011!$H\u0007\u00027)\tA$A\u0003tG\u0006d\u0017-\u0003\u0002\u001f7\t9aj\u001c;iS:<\u0007C\u0001\u000e!\u0013\t\t3DA\u0002B]f\fa\u0001P5oSRtD#\u0001\u0013\u0011\u00079\u0001A#\u0001\u0005j]R,'O\\1m)\t9#\u0006\u0005\u0002\u001bQ%\u0011\u0011f\u0007\u0002\u0005+:LG\u000fC\u0003,\u0005\u0001\u0007A#\u0001\u0004sKN,H\u000e^\u0001\n_:\u001cVoY2fgN$\"a\n\u0018\t\u000b-\u001a\u0001\u0019\u0001\u000b)\u0007\r\u0001t\bE\u0002\u001bcMJ!AM\u000e\u0003\rQD'o\\<t!\t!DH\u0004\u00026u9\u0011a'O\u0007\u0002o)\u0011\u0001(C\u0001\u0007yI|w\u000e\u001e \n\u0003qI!aO\u000e\u0002\u000fA\f7m[1hK&\u0011QH\u0010\u0002\n)\"\u0014xn^1cY\u0016T!aO\u000e$\u0003M\u0002")
public abstract class OnSuccess<T>
extends japi.CallbackBridge<T> {
    @Override
    public final void internal(T result) {
        this.onSuccess(result);
    }

    public abstract void onSuccess(T var1) throws Throwable;
}

