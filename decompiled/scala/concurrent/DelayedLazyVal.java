/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent;

import scala.Function0;
import scala.concurrent.ExecutionContext;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005=3A!\u0003\u0006\u0001\u001f!AQ\u0003\u0001B\u0001B\u0003%a\u0003\u0003\u0005%\u0001\t\u0005I\u0015!\u0003&\u0011!Y\u0003A!A!\u0002\u0017a\u0003\"\u0002\u0019\u0001\t\u0003\t\u0004BB\u001c\u0001A\u0003&\u0001\b\u0003\u0005@\u0001!\u0015\r\u0015\"\u0003A\u0011\u0015\t\u0005\u0001\"\u0001C\u0011\u0015\u0019\u0005\u0001\"\u0001E\u00059!U\r\\1zK\u0012d\u0015M_=WC2T!a\u0003\u0007\u0002\u0015\r|gnY;se\u0016tGOC\u0001\u000e\u0003\u0015\u00198-\u00197b\u0007\u0001)\"\u0001E\u000e\u0014\u0005\u0001\t\u0002C\u0001\n\u0014\u001b\u0005a\u0011B\u0001\u000b\r\u0005\u0019\te.\u001f*fM\u0006\ta\rE\u0002\u0013/eI!\u0001\u0007\u0007\u0003\u0013\u0019+hn\u0019;j_:\u0004\u0004C\u0001\u000e\u001c\u0019\u0001!Q\u0001\b\u0001C\u0002u\u0011\u0011\u0001V\t\u0003=\u0005\u0002\"AE\u0010\n\u0005\u0001b!a\u0002(pi\"Lgn\u001a\t\u0003%\tJ!a\t\u0007\u0003\u0007\u0005s\u00170\u0001\u0003c_\u0012L\bc\u0001\n'Q%\u0011q\u0005\u0004\u0002\ty\tLh.Y7f}A\u0011!#K\u0005\u0003U1\u0011A!\u00168ji\u0006!Q\r_3d!\tic&D\u0001\u000b\u0013\ty#B\u0001\tFq\u0016\u001cW\u000f^5p]\u000e{g\u000e^3yi\u00061A(\u001b8jiz\"2AM\u001b7)\t\u0019D\u0007E\u0002.\u0001eAQa\u000b\u0003A\u00041BQ!\u0006\u0003A\u0002YAa\u0001\n\u0003\u0005\u0002\u0004)\u0013aB0jg\u0012{g.\u001a\t\u0003%eJ!A\u000f\u0007\u0003\u000f\t{w\u000e\\3b]\"\u0012Q\u0001\u0010\t\u0003%uJ!A\u0010\u0007\u0003\u0011Y|G.\u0019;jY\u0016\f\u0001bY8na2,G/Z\u000b\u00023\u00051\u0011n\u001d#p]\u0016,\u0012\u0001O\u0001\u0006CB\u0004H.\u001f\u000b\u00023!2\u0001AR%K\u00196\u0003\"AE$\n\u0005!c!A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017%A&\u0002_\u0001$U\r\\1zK\u0012d\u0015M_=WC2\u0004\u0007eV5mY\u0002\u0012W\r\t:f[>4X\r\u001a\u0011j]\u0002\"\b.\u001a\u0011gkR,(/\u001a\u0018\u0002\u000bMLgnY3\"\u00039\u000baA\r\u00182g9\u0002\u0004")
public class DelayedLazyVal<T> {
    private T complete;
    private final Function0<T> f;
    private final Function0<BoxedUnit> body;
    private volatile boolean _isDone;
    private volatile boolean bitmap$0;

    private T complete$lzycompute() {
        synchronized (this) {
            if (!this.bitmap$0) {
                this.complete = this.f.apply();
                this.bitmap$0 = true;
            }
        }
        return this.complete;
    }

    private T complete() {
        if (!this.bitmap$0) {
            return this.complete$lzycompute();
        }
        return this.complete;
    }

    public boolean isDone() {
        return this._isDone;
    }

    public T apply() {
        if (this.isDone()) {
            return this.complete();
        }
        return this.f.apply();
    }

    public DelayedLazyVal(Function0<T> f, Function0<BoxedUnit> body2, ExecutionContext exec) {
        this.f = f;
        this.body = body2;
        this._isDone = false;
        exec.execute(() -> {
            $this.body.apply$mcV$sp();
            $this._isDone = true;
        });
    }
}

