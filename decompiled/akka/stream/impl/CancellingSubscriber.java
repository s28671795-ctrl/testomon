/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a3QAB\u0004\u0003\u00175AQA\f\u0001\u0005\u0002=BQA\r\u0001\u0005BMBQ!\u0012\u0001\u0005B\u0019CQ\u0001\u0014\u0001\u0005B5CQA\u0014\u0001\u0005B=\u0013AcQ1oG\u0016dG.\u001b8h'V\u00147o\u0019:jE\u0016\u0014(B\u0001\u0005\n\u0003\u0011IW\u000e\u001d7\u000b\u0005)Y\u0011AB:ue\u0016\fWNC\u0001\r\u0003\u0011\t7n[1\u0016\u00059\t3c\u0001\u0001\u0010/A\u0011\u0001#F\u0007\u0002#)\u0011!cE\u0001\u0005Y\u0006twMC\u0001\u0015\u0003\u0011Q\u0017M^1\n\u0005Y\t\"AB(cU\u0016\u001cG\u000fE\u0002\u0019;}i\u0011!\u0007\u0006\u00035m\tqB]3bGRLg/Z:ue\u0016\fWn\u001d\u0006\u00029\u0005\u0019qN]4\n\u0005yI\"AC*vEN\u001c'/\u001b2feB\u0011\u0001%\t\u0007\u0001\t\u0015\u0011\u0003A1\u0001%\u0005\u0005!6\u0001A\t\u0003K-\u0002\"AJ\u0015\u000e\u0003\u001dR\u0011\u0001K\u0001\u0006g\u000e\fG.Y\u0005\u0003U\u001d\u0012qAT8uQ&tw\r\u0005\u0002'Y%\u0011Qf\n\u0002\u0004\u0003:L\u0018A\u0002\u001fj]&$h\bF\u00011!\r\t\u0004aH\u0007\u0002\u000f\u00059qN\\#se>\u0014HC\u0001\u001b8!\t1S'\u0003\u00027O\t!QK\\5u\u0011\u0015A$\u00011\u0001:\u0003\u0005!\bC\u0001\u001eC\u001d\tY\u0004I\u0004\u0002=\u007f5\tQH\u0003\u0002?G\u00051AH]8pizJ\u0011\u0001K\u0005\u0003\u0003\u001e\nq\u0001]1dW\u0006<W-\u0003\u0002D\t\nIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0003\u0003\u001e\n1b\u001c8Tk\n\u001c8M]5cKR\u0011Ag\u0012\u0005\u0006\u0011\u000e\u0001\r!S\u0001\u0002gB\u0011\u0001DS\u0005\u0003\u0017f\u0011AbU;cg\u000e\u0014\u0018\u000e\u001d;j_:\f!b\u001c8D_6\u0004H.\u001a;f)\u0005!\u0014AB8o\u001d\u0016DH\u000f\u0006\u00025!\")\u0001(\u0002a\u0001?!\u0012\u0001A\u0015\t\u0003'Zk\u0011\u0001\u0016\u0006\u0003+.\t!\"\u00198o_R\fG/[8o\u0013\t9FKA\u0006J]R,'O\\1m\u0003BL\u0007")
@InternalApi
public final class CancellingSubscriber<T>
implements Subscriber<T> {
    @Override
    public void onError(Throwable t2) {
    }

    @Override
    public void onSubscribe(Subscription s2) {
        s2.cancel();
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onNext(T t2) {
    }
}

