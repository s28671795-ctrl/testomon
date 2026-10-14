/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent.impl;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u000513Qa\u0002\u0005\u0003\u00119AQa\r\u0001\u0005\u0002QBaa\u000e\u0001!B\u0013y\u0002\"\u0002\u001d\u0001\t\u000bI\u0004\"\u0002\u001e\u0001\t#Z\u0004\"B!\u0001\t#\u0012\u0005\"\u0002%\u0001\t\u0003J%aD\"p[BdW\r^5p]2\u000bGo\u00195\u000b\u0005%Q\u0011\u0001B5na2T!a\u0003\u0007\u0002\u0015\r|gnY;se\u0016tGOC\u0001\u000e\u0003\u0015\u00198-\u00197b+\tyaeE\u0002\u0001!m\u0001\"!E\r\u000e\u0003IQ!a\u0005\u000b\u0002\u000b1|7m[:\u000b\u0005-)\"B\u0001\f\u0018\u0003\u0011)H/\u001b7\u000b\u0003a\tAA[1wC&\u0011!D\u0005\u0002\u001b\u0003\n\u001cHO]1diF+X-^3e'ft7\r\u001b:p]&TXM\u001d\t\u00059uy\u0002'D\u0001\r\u0013\tqBBA\u0005Gk:\u001cG/[8ocA\u0019\u0001E\t\u0013\u000e\u0003\u0005R!A\u0006\u0007\n\u0005\r\n#a\u0001+ssB\u0011QE\n\u0007\u0001\t\u00159\u0003A1\u0001*\u0005\u0005!6\u0001A\t\u0003U5\u0002\"\u0001H\u0016\n\u00051b!a\u0002(pi\"Lgn\u001a\t\u000399J!a\f\u0007\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u001dc%\u0011!\u0007\u0004\u0002\u0005+:LG/\u0001\u0004=S:LGO\u0010\u000b\u0002kA\u0019a\u0007\u0001\u0013\u000e\u0003!\tqa\u0018:fgVdG/\u0001\u0004sKN,H\u000e^\u000b\u0002?\u0005\u0001BO]=BGF,\u0018N]3TQ\u0006\u0014X\r\u001a\u000b\u0003y}\u0002\"\u0001H\u001f\n\u0005yb!aA%oi\")\u0001\t\u0002a\u0001y\u00059\u0011n\u001a8pe\u0016$\u0017\u0001\u0005;ssJ+G.Z1tKNC\u0017M]3e)\t\u0019e\t\u0005\u0002\u001d\t&\u0011Q\t\u0004\u0002\b\u0005>|G.Z1o\u0011\u00159U\u00011\u0001=\u0003\u0019IwM\\8sK\u0006)\u0011\r\u001d9msR\u0011\u0001G\u0013\u0005\u0006\u0017\u001a\u0001\raH\u0001\u0006m\u0006dW/\u001a")
public final class CompletionLatch<T>
extends AbstractQueuedSynchronizer
implements Function1<Try<T>, BoxedUnit> {
    private Try<T> _result = null;

    @Override
    public boolean apply$mcZD$sp(double v1) {
        return Function1.apply$mcZD$sp$(this, v1);
    }

    @Override
    public double apply$mcDD$sp(double v1) {
        return Function1.apply$mcDD$sp$(this, v1);
    }

    @Override
    public float apply$mcFD$sp(double v1) {
        return Function1.apply$mcFD$sp$(this, v1);
    }

    @Override
    public int apply$mcID$sp(double v1) {
        return Function1.apply$mcID$sp$(this, v1);
    }

    @Override
    public long apply$mcJD$sp(double v1) {
        return Function1.apply$mcJD$sp$(this, v1);
    }

    @Override
    public void apply$mcVD$sp(double v1) {
        Function1.apply$mcVD$sp$(this, v1);
    }

    @Override
    public boolean apply$mcZF$sp(float v1) {
        return Function1.apply$mcZF$sp$(this, v1);
    }

    @Override
    public double apply$mcDF$sp(float v1) {
        return Function1.apply$mcDF$sp$(this, v1);
    }

    @Override
    public float apply$mcFF$sp(float v1) {
        return Function1.apply$mcFF$sp$(this, v1);
    }

    @Override
    public int apply$mcIF$sp(float v1) {
        return Function1.apply$mcIF$sp$(this, v1);
    }

    @Override
    public long apply$mcJF$sp(float v1) {
        return Function1.apply$mcJF$sp$(this, v1);
    }

    @Override
    public void apply$mcVF$sp(float v1) {
        Function1.apply$mcVF$sp$(this, v1);
    }

    @Override
    public boolean apply$mcZI$sp(int v1) {
        return Function1.apply$mcZI$sp$(this, v1);
    }

    @Override
    public double apply$mcDI$sp(int v1) {
        return Function1.apply$mcDI$sp$(this, v1);
    }

    @Override
    public float apply$mcFI$sp(int v1) {
        return Function1.apply$mcFI$sp$(this, v1);
    }

    @Override
    public int apply$mcII$sp(int v1) {
        return Function1.apply$mcII$sp$(this, v1);
    }

    @Override
    public long apply$mcJI$sp(int v1) {
        return Function1.apply$mcJI$sp$(this, v1);
    }

    @Override
    public void apply$mcVI$sp(int v1) {
        Function1.apply$mcVI$sp$(this, v1);
    }

    @Override
    public boolean apply$mcZJ$sp(long v1) {
        return Function1.apply$mcZJ$sp$(this, v1);
    }

    @Override
    public double apply$mcDJ$sp(long v1) {
        return Function1.apply$mcDJ$sp$(this, v1);
    }

    @Override
    public float apply$mcFJ$sp(long v1) {
        return Function1.apply$mcFJ$sp$(this, v1);
    }

    @Override
    public int apply$mcIJ$sp(long v1) {
        return Function1.apply$mcIJ$sp$(this, v1);
    }

    @Override
    public long apply$mcJJ$sp(long v1) {
        return Function1.apply$mcJJ$sp$(this, v1);
    }

    @Override
    public void apply$mcVJ$sp(long v1) {
        Function1.apply$mcVJ$sp$(this, v1);
    }

    @Override
    public <A$> Function1<A$, BoxedUnit> compose(Function1<A$, Try<T>> g2) {
        return Function1.compose$(this, g2);
    }

    @Override
    public <A$> Function1<Try<T>, A$> andThen(Function1<BoxedUnit, A$> g2) {
        return Function1.andThen$(this, g2);
    }

    @Override
    public String toString() {
        return Function1.toString$(this);
    }

    public final Try<T> result() {
        return this._result;
    }

    @Override
    public int tryAcquireShared(int ignored) {
        if (this.getState() != 0) {
            return 1;
        }
        return -1;
    }

    @Override
    public boolean tryReleaseShared(int ignore) {
        this.setState(1);
        return true;
    }

    @Override
    public void apply(Try<T> value) {
        this._result = value;
        this.releaseShared(1);
    }
}

