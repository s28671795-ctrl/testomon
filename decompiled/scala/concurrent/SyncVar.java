/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent;

import java.util.concurrent.TimeUnit;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005]3A!\u0004\b\u0001'!)\u0011\u0004\u0001C\u00015!1\u0001\u0006\u0001Q!\n%B\u0011\u0002\f\u0001A\u0002\u0003\u0005\u000b\u0015B\u000f\t\u000b5\u0002A\u0011\u0001\u0018\t\u000b=\u0002A\u0011\u0002\u0019\t\u000b5\u0002A\u0011\u0001\u001c\t\u000bm\u0002A\u0011\u0001\u001f\t\u000bm\u0002A\u0011A\u001f\t\u000b}\u0002A\u0011\u0001!\t\u000b\u0019\u0003A\u0011A$\t\u000b!\u0003A\u0011B%\t\u000b-\u0003A\u0011\u0002'\u0003\u000fMKhn\u0019,be*\u0011q\u0002E\u0001\u000bG>t7-\u001e:sK:$(\"A\t\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011AcH\n\u0003\u0001U\u0001\"AF\f\u000e\u0003AI!\u0001\u0007\t\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t1\u0004E\u0002\u001d\u0001ui\u0011A\u0004\t\u0003=}a\u0001\u0001B\u0003!\u0001\t\u0007\u0011EA\u0001B#\t\u0011S\u0005\u0005\u0002\u0017G%\u0011A\u0005\u0005\u0002\b\u001d>$\b.\u001b8h!\t1b%\u0003\u0002(!\t\u0019\u0011I\\=\u0002\u0013%\u001cH)\u001a4j]\u0016$\u0007C\u0001\f+\u0013\tY\u0003CA\u0004C_>dW-\u00198\u0002\u000bY\fG.^3\u0002\u0007\u001d,G/F\u0001\u001e\u0003Q9\u0018-\u001b;NK\u0006\u001cXO]5oO\u0016c\u0017\r]:fIR\u0011\u0011\u0007\u000e\t\u0003-IJ!a\r\t\u0003\t1{gn\u001a\u0005\u0006k\u0015\u0001\r!M\u0001\bi&lWm\\;u)\t9$\bE\u0002\u0017quI!!\u000f\t\u0003\r=\u0003H/[8o\u0011\u0015)d\u00011\u00012\u0003\u0011!\u0018m[3\u0015\u0003u!\"!\b \t\u000bUB\u0001\u0019A\u0019\u0002\u0007A,H\u000f\u0006\u0002B\tB\u0011aCQ\u0005\u0003\u0007B\u0011A!\u00168ji\")Q)\u0003a\u0001;\u0005\t\u00010A\u0003jgN+G/F\u0001*\u0003\u0019\u0019X\r\u001e,bYR\u0011\u0011I\u0013\u0005\u0006\u000b.\u0001\r!H\u0001\tk:\u001cX\r\u001e,bYR\t\u0011\t\u000b\u0004\u0001\u001dF\u0013F+\u0016\t\u0003-=K!\u0001\u0015\t\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u0003M\u000bq)V:fA\u0001T\u0017M^1/kRLGNL2p]\u000e,(O]3oi:b\u0015N\\6fI\ncwnY6j]\u001e\fV/Z;fA]LG\u000f\u001b\u0011dCB\f7-\u001b;zAE\u0002\u0007%\u001b8ti\u0016\fGML\u0001\u0006g&t7-Z\u0011\u0002-\u00061!GL\u00194]A\u0002")
public class SyncVar<A> {
    private boolean isDefined = false;
    private A value;

    public synchronized A get() {
        while (!this.isDefined) {
            this.wait();
        }
        return this.value;
    }

    private long waitMeasuringElapsed(long timeout2) {
        if (timeout2 <= 0L) {
            return 0L;
        }
        long start2 = System.nanoTime();
        this.wait(timeout2);
        long elapsed = System.nanoTime() - start2;
        if (elapsed < 0L) {
            return 0L;
        }
        return TimeUnit.NANOSECONDS.toMillis(elapsed);
    }

    public synchronized Option<A> get(long timeout2) {
        long elapsed;
        for (long rest = timeout2; !this.isDefined && rest > 0L; rest -= elapsed) {
            elapsed = this.waitMeasuringElapsed(rest);
        }
        if (this.isDefined) {
            return new Some<A>(this.value);
        }
        return None$.MODULE$;
    }

    public synchronized A take() {
        A a;
        try {
            a = this.get();
        }
        finally {
            this.unsetVal();
        }
        return a;
    }

    public synchronized A take(long timeout2) {
        A a;
        try {
            a = this.get(timeout2).get();
        }
        finally {
            this.unsetVal();
        }
        return a;
    }

    public synchronized void put(A x) {
        while (this.isDefined) {
            this.wait();
        }
        this.setVal(x);
    }

    public synchronized boolean isSet() {
        return this.isDefined;
    }

    private synchronized void setVal(A x) {
        this.isDefined = true;
        this.value = x;
        this.notifyAll();
    }

    private synchronized void unsetVal() {
        this.isDefined = false;
        this.value = null;
        this.notifyAll();
    }
}

