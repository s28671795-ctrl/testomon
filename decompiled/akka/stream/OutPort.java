/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.Outlet;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q3aa\u0003\u0007\u0002\"Eq\u0005\"\u0002\r\u0001\t\u0003I\u0002\"\u0002\u000f\u0001\t\u000bj\u0002\"B\u0011\u0001\t\u000b\u0012\u0003\u0002C\u0016\u0001\u0001\u0004%\t\u0001\u0004\u0017\t\u00115\u0002\u0001\u0019!C\u0001\u00199Ba\u0001\u000e\u0001!B\u0013q\u0002\u0002C\u001d\u0001\u0001\u0004%\t\u0001\u0004\u001e\t\u0011m\u0002\u0001\u0019!C\u0001\u0019qBaA\u0010\u0001!B\u0013Q\u0002B\u0002!\u0001\t\u0003a\u0011IA\u0004PkR\u0004vN\u001d;\u000b\u00055q\u0011AB:ue\u0016\fWNC\u0001\u0010\u0003\u0011\t7n[1\u0004\u0001M\u0011\u0001A\u0005\t\u0003'Yi\u0011\u0001\u0006\u0006\u0002+\u0005)1oY1mC&\u0011q\u0003\u0006\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005Q\u0002CA\u000e\u0001\u001b\u0005a\u0011\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003y\u0001\"aE\u0010\n\u0005\u0001\"\"aA%oi\u00061Q-];bYN$\"a\t\u0014\u0011\u0005M!\u0013BA\u0013\u0015\u0005\u001d\u0011un\u001c7fC:DQaJ\u0002A\u0002!\nA\u0001\u001e5biB\u00111#K\u0005\u0003UQ\u00111!\u00118z\u0003\tIG-F\u0001\u001f\u0003\u0019IGm\u0018\u0013fcR\u0011qF\r\t\u0003'AJ!!\r\u000b\u0003\tUs\u0017\u000e\u001e\u0005\bg\u0015\t\t\u00111\u0001\u001f\u0003\rAH%M\u0001\u0004S\u0012\u0004\u0003F\u0001\u00047!\t\u0019r'\u0003\u00029)\tAao\u001c7bi&dW-\u0001\u0005nCB\u0004X\r\u001a+p+\u0005Q\u0012\u0001D7baB,G\rV8`I\u0015\fHCA\u0018>\u0011\u001d\u0019\u0004\"!AA\u0002i\t\u0011\"\\1qa\u0016$Gk\u001c\u0011)\u0005%1\u0014AB8vi2,G/F\u0001Ca\t\u0019\u0005\nE\u0002\u001c\t\u001aK!!\u0012\u0007\u0003\r=+H\u000f\\3u!\t9\u0005\n\u0004\u0001\u0005\u0013%S\u0011\u0011!A\u0001\u0006\u0003Q%aA0%iE\u00111\n\u000b\t\u0003'1K!!\u0014\u000b\u0003\u000f9{G\u000f[5oOB\u0012q*\u0015\t\u00047\u0011\u0003\u0006CA$R\t%\u0011\u0006!!A\u0001\u0002\u000b\u0005!JA\u0002`IMJ#\u0001\u0001#")
public abstract class OutPort {
    private volatile int id = -1;
    private volatile OutPort mappedTo = this;

    public final int hashCode() {
        return super.hashCode();
    }

    public final boolean equals(Object that) {
        return this == that;
    }

    public int id() {
        return this.id;
    }

    public void id_$eq(int x$1) {
        this.id = x$1;
    }

    public OutPort mappedTo() {
        return this.mappedTo;
    }

    public void mappedTo_$eq(OutPort x$1) {
        this.mappedTo = x$1;
    }

    public Outlet<?> outlet() {
        return (Outlet)this;
    }
}

