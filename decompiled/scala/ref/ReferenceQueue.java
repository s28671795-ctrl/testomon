/*
 * Decompiled with CFR 0.152.
 */
package scala.ref;

import scala.None$;
import scala.Option;
import scala.Some;
import scala.ref.Reference;
import scala.ref.ReferenceWithWrapper;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m3A!\u0003\u0006\u0001\u001f!)Q\u0003\u0001C\u0001-!A\u0011\u0005\u0001b\u0001\n\u0003Q!\u0005\u0003\u00040\u0001\u0001\u0006Ia\t\u0005\u0006c\u0001!\tE\r\u0005\u0006}\u0001!\tb\u0010\u0005\u0006%\u0002!\ta\u0015\u0005\u0006)\u0002!\ta\u0015\u0005\u0006)\u0002!\t!\u0016\u0002\u000f%\u00164WM]3oG\u0016\fV/Z;f\u0015\tYA\"A\u0002sK\u001aT\u0011!D\u0001\u0006g\u000e\fG.Y\u0002\u0001+\t\u00012d\u0005\u0002\u0001#A\u0011!cE\u0007\u0002\u0019%\u0011A\u0003\u0004\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u00059\u0002c\u0001\r\u000135\t!\u0002\u0005\u0002\u001b71\u0001AA\u0002\u000f\u0001\t\u000b\u0007QDA\u0001U#\tq\u0012\u0003\u0005\u0002\u0013?%\u0011\u0001\u0005\u0004\u0002\b\u001d>$\b.\u001b8h\u0003))h\u000eZ3sYfLgnZ\u000b\u0002GA\u0012A%\f\t\u0004K-bS\"\u0001\u0014\u000b\u0005-9#B\u0001\u0015*\u0003\u0011a\u0017M\\4\u000b\u0003)\nAA[1wC&\u0011\u0011B\n\t\u000355\"\u0011BL\u0002\u0002\u0002\u0003\u0005)\u0011\u0001\u0019\u0003\u0007}#\u0013'A\u0006v]\u0012,'\u000f\\=j]\u001e\u0004\u0013C\u0001\u0010\u001a\u0003!!xn\u0015;sS:<G#A\u001a\u0011\u0005QZdBA\u001b:!\t1D\"D\u00018\u0015\tAd\"\u0001\u0004=e>|GOP\u0005\u0003u1\ta\u0001\u0015:fI\u00164\u0017B\u0001\u001f>\u0005\u0019\u0019FO]5oO*\u0011!\bD\u0001\b/J\f\u0007\u000f]3s)\t\u0001e\tE\u0002\u0013\u0003\u000eK!A\u0011\u0007\u0003\r=\u0003H/[8o!\rAB)G\u0005\u0003\u000b*\u0011\u0011BU3gKJ,gnY3\t\u000b\u001d+\u0001\u0019\u0001%\u0002\t)\u0014XM\u001a\u0019\u0003\u00132\u00032!\n&L\u0013\t)e\u0005\u0005\u0002\u001b\u0019\u0012IQJRA\u0001\u0002\u0003\u0015\tA\u0014\u0002\u0004?\u0012\u0012\u0014C\u0001\u0010P!\t\u0011\u0002+\u0003\u0002R\u0019\t\u0019\u0011I\\=\u0002\tA|G\u000e\\\u000b\u0002\u0001\u00061!/Z7pm\u0016$\"\u0001\u0011,\t\u000b]C\u0001\u0019\u0001-\u0002\u000fQLW.Z8viB\u0011!#W\u0005\u000352\u0011A\u0001T8oO\u0002")
public class ReferenceQueue<T> {
    private final java.lang.ref.ReferenceQueue<? extends T> underlying = new java.lang.ref.ReferenceQueue();

    public java.lang.ref.ReferenceQueue<? extends T> underlying() {
        return this.underlying;
    }

    public String toString() {
        return this.underlying().toString();
    }

    public Option<Reference<T>> Wrapper(java.lang.ref.Reference<?> jref) {
        if (jref == null) {
            return None$.MODULE$;
        }
        return new Some<Reference<T>>(((ReferenceWithWrapper)((Object)jref)).wrapper());
    }

    public Option<Reference<T>> poll() {
        return this.Wrapper(this.underlying().poll());
    }

    public Option<Reference<T>> remove() {
        return this.Wrapper(this.underlying().remove());
    }

    public Option<Reference<T>> remove(long timeout2) {
        return this.Wrapper(this.underlying().remove(timeout2));
    }
}

