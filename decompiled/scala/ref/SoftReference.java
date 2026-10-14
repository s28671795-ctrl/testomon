/*
 * Decompiled with CFR 0.152.
 */
package scala.ref;

import java.lang.ref.Reference;
import scala.Function0;
import scala.Option;
import scala.Proxy;
import scala.ref.ReferenceQueue;
import scala.ref.ReferenceWrapper;
import scala.ref.SoftReference$;
import scala.ref.SoftReferenceWithWrapper;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y3A\u0001D\u0007\u0001%!AA\u0005\u0001B\u0001B\u0003%A\u0004\u0003\u0005&\u0001\t\u0005\t\u0015!\u0003'\u0011\u0015I\u0003\u0001\"\u0001+\u0011\u0015I\u0003\u0001\"\u0001/\u0011\u001d\u0001\u0004A1A\u0005\u0002EBaA\u0010\u0001!\u0002\u0013\u0011t!\u0002!\u000e\u0011\u0003\te!\u0002\u0007\u000e\u0011\u0003\u0011\u0005\"B\u0015\t\t\u0003\u0019\u0005\"\u0002#\t\t\u0003)\u0005\"B&\t\t\u0003a%!D*pMR\u0014VMZ3sK:\u001cWM\u0003\u0002\u000f\u001f\u0005\u0019!/\u001a4\u000b\u0003A\tQa]2bY\u0006\u001c\u0001!\u0006\u0002\u0014=M\u0019\u0001\u0001\u0006\r\u0011\u0005U1R\"A\b\n\u0005]y!AB!osJ+g\rE\u0002\u001a5qi\u0011!D\u0005\u000375\u0011\u0001CU3gKJ,gnY3Xe\u0006\u0004\b/\u001a:\u0011\u0005uqB\u0002\u0001\u0003\u0007?\u0001!)\u0019\u0001\u0011\u0003\u0003Q\u000b\"!\t\u000b\u0011\u0005U\u0011\u0013BA\u0012\u0010\u0005\u001dqu\u000e\u001e5j]\u001e\fQA^1mk\u0016\fQ!];fk\u0016\u00042!G\u0014\u001d\u0013\tASB\u0001\bSK\u001a,'/\u001a8dKF+X-^3\u0002\rqJg.\u001b;?)\rYC&\f\t\u00043\u0001a\u0002\"\u0002\u0013\u0004\u0001\u0004a\u0002\"B\u0013\u0004\u0001\u00041CCA\u00160\u0011\u0015!C\u00011\u0001\u001d\u0003))h\u000eZ3sYfLgnZ\u000b\u0002eA\u00121\u0007\u0010\t\u0004iiZT\"A\u001b\u000b\u000591$BA\u001c9\u0003\u0011a\u0017M\\4\u000b\u0003e\nAA[1wC&\u0011A\"\u000e\t\u0003;q\"\u0011\"\u0010\u0004\u0002\u0002\u0003\u0005)\u0011A \u0003\u0007}#\u0013'A\u0006v]\u0012,'\u000f\\=j]\u001e\u0004\u0013CA\u0011\u001d\u00035\u0019vN\u001a;SK\u001a,'/\u001a8dKB\u0011\u0011\u0004C\n\u0003\u0011Q!\u0012!Q\u0001\u0006CB\u0004H._\u000b\u0003\r&#\"a\u0012&\u0011\u0007e\u0001\u0001\n\u0005\u0002\u001e\u0013\u0012)qD\u0003b\u0001A!)AE\u0003a\u0001\u0011\u00069QO\\1qa2LXCA'S)\tq5\u000bE\u0002\u0016\u001fFK!\u0001U\b\u0003\r=\u0003H/[8o!\ti\"\u000bB\u0003 \u0017\t\u0007\u0001\u0005C\u0003U\u0017\u0001\u0007Q+\u0001\u0002teB\u0019\u0011\u0004A)")
public class SoftReference<T>
implements ReferenceWrapper<T> {
    private final java.lang.ref.SoftReference<? extends T> underlying;

    public static <T> Option<T> unapply(SoftReference<T> sr) {
        return SoftReference$.MODULE$.unapply(sr);
    }

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
    public java.lang.ref.SoftReference<? extends T> underlying() {
        return this.underlying;
    }

    public SoftReference(T value, ReferenceQueue<T> queue) {
        this.underlying = new SoftReferenceWithWrapper<T>(value, queue, this);
    }

    public SoftReference(T value) {
        this(value, null);
    }
}

