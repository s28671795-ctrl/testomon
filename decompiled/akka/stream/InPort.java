/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.Inlet;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q3aa\u0003\u0007\u0002\"Eq\u0005\"\u0002\r\u0001\t\u0003I\u0002\"\u0002\u000f\u0001\t\u000bj\u0002\"B\u0011\u0001\t\u000b\u0012\u0003\u0002C\u0016\u0001\u0001\u0004%\t\u0001\u0004\u0017\t\u00115\u0002\u0001\u0019!C\u0001\u00199Ba\u0001\u000e\u0001!B\u0013q\u0002\u0002C\u001d\u0001\u0001\u0004%\t\u0001\u0004\u001e\t\u0011m\u0002\u0001\u0019!C\u0001\u0019qBaA\u0010\u0001!B\u0013Q\u0002B\u0002!\u0001\t\u0003a\u0011I\u0001\u0004J]B{'\u000f\u001e\u0006\u0003\u001b9\taa\u001d;sK\u0006l'\"A\b\u0002\t\u0005\\7.Y\u0002\u0001'\t\u0001!\u0003\u0005\u0002\u0014-5\tACC\u0001\u0016\u0003\u0015\u00198-\u00197b\u0013\t9BC\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003i\u0001\"a\u0007\u0001\u000e\u00031\t\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002=A\u00111cH\u0005\u0003AQ\u00111!\u00138u\u0003\u0019)\u0017/^1mgR\u00111E\n\t\u0003'\u0011J!!\n\u000b\u0003\u000f\t{w\u000e\\3b]\")qe\u0001a\u0001Q\u0005!A\u000f[1u!\t\u0019\u0012&\u0003\u0002+)\t\u0019\u0011I\\=\u0002\u0005%$W#\u0001\u0010\u0002\r%$w\fJ3r)\ty#\u0007\u0005\u0002\u0014a%\u0011\u0011\u0007\u0006\u0002\u0005+:LG\u000fC\u00044\u000b\u0005\u0005\t\u0019\u0001\u0010\u0002\u0007a$\u0013'A\u0002jI\u0002B#A\u0002\u001c\u0011\u0005M9\u0014B\u0001\u001d\u0015\u0005!1x\u000e\\1uS2,\u0017\u0001C7baB,G\rV8\u0016\u0003i\tA\"\\1qa\u0016$Gk\\0%KF$\"aL\u001f\t\u000fMB\u0011\u0011!a\u00015\u0005IQ.\u00199qK\u0012$v\u000e\t\u0015\u0003\u0013Y\nQ!\u001b8mKR,\u0012A\u0011\u0019\u0003\u0007\"\u00032a\u0007#G\u0013\t)EBA\u0003J]2,G\u000f\u0005\u0002H\u00112\u0001A!C%\u000b\u0003\u0003\u0005\tQ!\u0001K\u0005\ryFEM\t\u0003\u0017\"\u0002\"a\u0005'\n\u00055#\"a\u0002(pi\"Lgn\u001a\u0019\u0003\u001fF\u00032a\u0007#Q!\t9\u0015\u000bB\u0005S\u0001\u0005\u0005\t\u0011!B\u0001\u0015\n\u0019q\fJ\u0019*\u0005\u0001!\u0005")
public abstract class InPort {
    private volatile int id = -1;
    private volatile InPort mappedTo = this;

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

    public InPort mappedTo() {
        return this.mappedTo;
    }

    public void mappedTo_$eq(InPort x$1) {
        this.mappedTo = x$1;
    }

    public Inlet<?> inlet() {
        return (Inlet)this;
    }
}

