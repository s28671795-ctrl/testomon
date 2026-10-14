/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.japi;
import scala.MatchError;
import scala.reflect.ScalaSignature;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005%3Q\u0001B\u0003\u0002\u0002)AQ\u0001\u000b\u0001\u0005\u0002%BQa\u000b\u0001\u0005V1BQA\r\u0001\u0007\u0002M\u0012!b\u00148D_6\u0004H.\u001a;f\u0015\t1q!\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005A\u0011\u0001B1lW\u0006\u001c\u0001!\u0006\u0002\f=M\u0011\u0001\u0001\u0004\t\u0004\u001bE!bB\u0001\b\u0010\u001b\u0005)\u0011B\u0001\t\u0006\u0003\u0011Q\u0017\r]5\n\u0005I\u0019\"AD\"bY2\u0014\u0017mY6Ce&$w-\u001a\u0006\u0003!\u0015\u00012!\u0006\u000e\u001d\u001b\u00051\"BA\f\u0019\u0003\u0011)H/\u001b7\u000b\u0003e\tQa]2bY\u0006L!a\u0007\f\u0003\u0007Q\u0013\u0018\u0010\u0005\u0002\u001e=1\u0001AAB\u0010\u0001\u0011\u000b\u0007\u0001EA\u0001U#\t\tS\u0005\u0005\u0002#G5\t\u0001$\u0003\u0002%1\t9aj\u001c;iS:<\u0007C\u0001\u0012'\u0013\t9\u0003DA\u0002B]f\fa\u0001P5oSRtD#\u0001\u0016\u0011\u00079\u0001A$\u0001\u0005j]R,'O\\1m)\ti\u0003\u0007\u0005\u0002#]%\u0011q\u0006\u0007\u0002\u0005+:LG\u000fC\u00032\u0005\u0001\u0007A#A\u0003wC2,X-\u0001\u0006p]\u000e{W\u000e\u001d7fi\u0016$2!\f\u001bC\u0011\u0015)4\u00011\u00017\u0003\u001d1\u0017-\u001b7ve\u0016\u0004\"aN \u000f\u0005ajdBA\u001d=\u001b\u0005Q$BA\u001e\n\u0003\u0019a$o\\8u}%\t\u0011$\u0003\u0002?1\u00059\u0001/Y2lC\u001e,\u0017B\u0001!B\u0005%!\u0006N]8xC\ndWM\u0003\u0002?1!)1i\u0001a\u00019\u000591/^2dKN\u001c\bfA\u0002F\u0011B\u0019!E\u0012\u001c\n\u0005\u001dC\"A\u0002;ie><8oI\u00017\u0001")
public abstract class OnComplete<T>
extends japi.CallbackBridge<Try<T>> {
    @Override
    public final void internal(Try<T> value) {
        Try<T> try_ = value;
        if (try_ instanceof Failure) {
            Failure failure = (Failure)try_;
            Throwable t2 = failure.exception();
            this.onComplete(t2, null);
            return;
        }
        if (try_ instanceof Success) {
            Success success = (Success)try_;
            Object r = success.value();
            this.onComplete(null, r);
            return;
        }
        throw new MatchError(try_);
    }

    public abstract void onComplete(Throwable var1, T var2) throws Throwable;
}

