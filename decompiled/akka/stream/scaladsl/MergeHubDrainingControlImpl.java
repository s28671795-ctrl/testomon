/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.annotation.InternalApi;
import akka.stream.scaladsl.MergeHub;
import scala.Function0;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005=2Q\u0001B\u0003\u0003\u0013-A\u0001B\u0007\u0001\u0003\u0002\u0003\u0006I\u0001\b\u0005\u0006E\u0001!\ta\t\u0005\u0006M\u0001!\te\n\u0002\u001c\u001b\u0016\u0014x-\u001a%vE\u0012\u0013\u0018-\u001b8j]\u001e\u001cuN\u001c;s_2LU\u000e\u001d7\u000b\u0005\u00199\u0011\u0001C:dC2\fGm\u001d7\u000b\u0005!I\u0011AB:ue\u0016\fWNC\u0001\u000b\u0003\u0011\t7n[1\u0014\u0007\u0001a!\u0003\u0005\u0002\u000e!5\taBC\u0001\u0010\u0003\u0015\u00198-\u00197b\u0013\t\tbB\u0001\u0004B]f\u0014VM\u001a\t\u0003']q!\u0001F\u000b\u000e\u0003\u0015I!AF\u0003\u0002\u00115+'oZ3Ik\nL!\u0001G\r\u0003\u001f\u0011\u0013\u0018-\u001b8j]\u001e\u001cuN\u001c;s_2T!AF\u0003\u0002\u0017\u0011\u0014\u0018-\u001b8BGRLwN\\\u0002\u0001!\riQdH\u0005\u0003=9\u0011\u0011BR;oGRLwN\u001c\u0019\u0011\u00055\u0001\u0013BA\u0011\u000f\u0005\u0011)f.\u001b;\u0002\rqJg.\u001b;?)\t!S\u0005\u0005\u0002\u0015\u0001!)!D\u0001a\u00019\u0005\u0001BM]1j]\u0006sGmQ8na2,G/\u001a\u000b\u0002?!\u0012\u0001!\u000b\t\u0003U5j\u0011a\u000b\u0006\u0003Y%\t!\"\u00198o_R\fG/[8o\u0013\tq3FA\u0006J]R,'O\\1m\u0003BL\u0007")
@InternalApi
public final class MergeHubDrainingControlImpl
implements MergeHub.DrainingControl {
    private final Function0<BoxedUnit> drainAction;

    @Override
    public void drainAndComplete() {
        this.drainAction.apply$mcV$sp();
    }

    public MergeHubDrainingControlImpl(Function0<BoxedUnit> drainAction) {
        this.drainAction = drainAction;
    }
}

