/*
 * Decompiled with CFR 0.152.
 */
package scala.ref;

import java.lang.ref.Reference;
import scala.Function0;
import scala.Option;
import scala.Proxy;
import scala.ref.PhantomReferenceWithWrapper;
import scala.ref.ReferenceQueue;
import scala.ref.ReferenceWrapper;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a2AAB\u0004\u0001\u0019!Aa\u0004\u0001B\u0001B\u0003%a\u0003\u0003\u0005 \u0001\t\u0005\t\u0015!\u0003!\u0011\u0015\u0019\u0003\u0001\"\u0001%\u0011\u001dA\u0003A1A\u0005\u0002%BaA\u000e\u0001!\u0002\u0013Q#\u0001\u0005)iC:$x.\u001c*fM\u0016\u0014XM\\2f\u0015\tA\u0011\"A\u0002sK\u001aT\u0011AC\u0001\u0006g\u000e\fG.Y\u0002\u0001+\ti\u0001dE\u0002\u0001\u001dI\u0001\"a\u0004\t\u000e\u0003%I!!E\u0005\u0003\r\u0005s\u0017PU3g!\r\u0019BCF\u0007\u0002\u000f%\u0011Qc\u0002\u0002\u0011%\u00164WM]3oG\u0016<&/\u00199qKJ\u0004\"a\u0006\r\r\u0001\u00111\u0011\u0004\u0001CC\u0002i\u0011\u0011\u0001V\t\u000379\u0001\"a\u0004\u000f\n\u0005uI!a\u0002(pi\"LgnZ\u0001\u0006m\u0006dW/Z\u0001\u0006cV,W/\u001a\t\u0004'\u00052\u0012B\u0001\u0012\b\u00059\u0011VMZ3sK:\u001cW-U;fk\u0016\fa\u0001P5oSRtDcA\u0013'OA\u00191\u0003\u0001\f\t\u000by\u0019\u0001\u0019\u0001\f\t\u000b}\u0019\u0001\u0019\u0001\u0011\u0002\u0015UtG-\u001a:ms&tw-F\u0001+a\tYC\u0007E\u0002-eMj\u0011!\f\u0006\u0003\u00119R!a\f\u0019\u0002\t1\fgn\u001a\u0006\u0002c\u0005!!.\u0019<b\u0013\t1Q\u0006\u0005\u0002\u0018i\u0011IQ'BA\u0001\u0002\u0003\u0015\ta\u000e\u0002\u0004?\u0012\n\u0014aC;oI\u0016\u0014H._5oO\u0002\n\"a\u0007\f")
public class PhantomReference<T>
implements ReferenceWrapper<T> {
    private final java.lang.ref.PhantomReference<? extends T> underlying;

    @Override
    public Option<T> get() {
        return ReferenceWrapper.get$(this);
    }

    @Override
    public T apply() {
        return (T)ReferenceWrapper.apply$(this);
    }

    @Override
    public void clear() {
        ReferenceWrapper.clear$(this);
    }

    @Override
    public boolean enqueue() {
        return ReferenceWrapper.enqueue$(this);
    }

    @Override
    public boolean isEnqueued() {
        return ReferenceWrapper.isEnqueued$(this);
    }

    @Override
    public Reference<? extends T> self() {
        return ReferenceWrapper.self$(this);
    }

    @Override
    public int hashCode() {
        return Proxy.hashCode$(this);
    }

    @Override
    public boolean equals(Object that) {
        return Proxy.equals$(this, that);
    }

    @Override
    public String toString() {
        return Proxy.toString$(this);
    }

    @Override
    public boolean apply$mcZ$sp() {
        return Function0.apply$mcZ$sp$(this);
    }

    @Override
    public byte apply$mcB$sp() {
        return Function0.apply$mcB$sp$(this);
    }

    @Override
    public char apply$mcC$sp() {
        return Function0.apply$mcC$sp$(this);
    }

    @Override
    public double apply$mcD$sp() {
        return Function0.apply$mcD$sp$(this);
    }

    @Override
    public float apply$mcF$sp() {
        return Function0.apply$mcF$sp$(this);
    }

    @Override
    public int apply$mcI$sp() {
        return Function0.apply$mcI$sp$(this);
    }

    @Override
    public long apply$mcJ$sp() {
        return Function0.apply$mcJ$sp$(this);
    }

    @Override
    public short apply$mcS$sp() {
        return Function0.apply$mcS$sp$(this);
    }

    @Override
    public void apply$mcV$sp() {
        Function0.apply$mcV$sp$(this);
    }

    @Override
    public java.lang.ref.PhantomReference<? extends T> underlying() {
        return this.underlying;
    }

    public PhantomReference(T value, ReferenceQueue<T> queue) {
        this.underlying = new PhantomReferenceWithWrapper<T>(value, queue, this);
    }
}

