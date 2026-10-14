/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.OutPort;
import akka.stream.Outlet$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e;Q\u0001D\u0007\t\u0002I1Q\u0001F\u0007\t\u0002UAQ\u0001H\u0001\u0005\u0002uAQAH\u0001\u0005\u0002}AQAU\u0001\u0005\u0002M3A\u0001F\u0007\u0003E!Aq%\u0002BC\u0002\u0013\u0005\u0001\u0006\u0003\u00055\u000b\t\u0005\t\u0015!\u0003*\u0011\u0015aR\u0001\"\u00036\u0011\u0015\u0019U\u0001\"\u0001E\u0011\u0015)U\u0001\"\u0001G\u0011\u0015aU\u0001\"\u0011N\u0003\u0019yU\u000f\u001e7fi*\u0011abD\u0001\u0007gR\u0014X-Y7\u000b\u0003A\tA!Y6lC\u000e\u0001\u0001CA\n\u0002\u001b\u0005i!AB(vi2,Go\u0005\u0002\u0002-A\u0011qCG\u0007\u00021)\t\u0011$A\u0003tG\u0006d\u0017-\u0003\u0002\u001c1\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#\u0001\n\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\u0005\u0001zECA\u0011Q!\r\u0019RAT\u000b\u0003Ge\u001a\"!\u0002\u0013\u0011\u0005M)\u0013B\u0001\u0014\u000e\u0005\u001dyU\u000f\u001e)peR\f\u0011a]\u000b\u0002SA\u0011!&\r\b\u0003W=\u0002\"\u0001\f\r\u000e\u00035R!AL\t\u0002\rq\u0012xn\u001c;?\u0013\t\u0001\u0004$\u0001\u0004Qe\u0016$WMZ\u0005\u0003eM\u0012aa\u0015;sS:<'B\u0001\u0019\u0019\u0003\t\u0019\b\u0005\u0006\u00027\u0005B\u00191#B\u001c\u0011\u0005aJD\u0002\u0001\u0003\u0006u\u0015\u0011\ra\u000f\u0002\u0002)F\u0011Ah\u0010\t\u0003/uJ!A\u0010\r\u0003\u000f9{G\u000f[5oOB\u0011q\u0003Q\u0005\u0003\u0003b\u00111!\u00118z\u0011\u00159\u0003\u00021\u0001*\u0003)\u0019\u0017M\u001d2p]\u000e{\u0007/\u001f\u000b\u0002m\u0005\u0011\u0011m]\u000b\u0003\u000f*+\u0012\u0001\u0013\t\u0004'\u0015I\u0005C\u0001\u001dK\t\u0015Y%B1\u0001<\u0005\u0005)\u0016\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003%\u0002\"\u0001O(\u0005\u000bi\u001a!\u0019A\u001e\t\u000bE\u001b\u0001\u0019A\u0015\u0002\t9\fW.Z\u0001\u0007GJ,\u0017\r^3\u0016\u0005Q;FCA+Y!\r\u0019RA\u0016\t\u0003q]#QA\u000f\u0003C\u0002mBQ!\u0015\u0003A\u0002%\u0002")
public final class Outlet<T>
extends OutPort {
    private final String s;

    public static <T> Outlet<T> create(String name) {
        return Outlet$.MODULE$.create(name);
    }

    public static <T> Outlet<T> apply(String name) {
        return Outlet$.MODULE$.apply(name);
    }

    public String s() {
        return this.s;
    }

    public Outlet<T> carbonCopy() {
        Outlet out = Outlet$.MODULE$.apply(this.s());
        out.mappedTo_$eq(this);
        return out;
    }

    public <U> Outlet<U> as() {
        return this;
    }

    public String toString() {
        return new StringBuilder(2).append(this.s()).append("(").append(this.hashCode()).append(")").append((Object)(this.mappedTo() == this ? "" : new StringBuilder(11).append(" mapped to ").append(this.mappedTo()).toString())).toString();
    }

    public Outlet(String s2) {
        this.s = s2;
    }
}

