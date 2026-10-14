/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.ObjectStreamException;
import java.io.Serializable;
import scala.Option;
import scala.Symbol$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005=4Aa\u0004\t\u0003'!Aq\u0004\u0001BC\u0002\u0013\u0005\u0001\u0005\u0003\u0005-\u0001\t\u0005\t\u0015!\u0003\"\u0011\u0015i\u0003\u0001\"\u0003/\u0011\u0015\t\u0004\u0001\"\u00113\u0011\u0015\u0019\u0004\u0001\"\u00035\u0011\u0015)\u0005\u0001\"\u0011G\u0011\u0015Q\u0005\u0001\"\u0011L\u000f\u0015\t\u0006\u0003#\u0001S\r\u0015y\u0001\u0003#\u0001T\u0011\u0015i\u0013\u0002\"\u0001Z\u0011\u0015Q\u0016\u0002\"\u0011\\\u0011\u0015i\u0016\u0002\"\u0005_\u0011\u0015\u0001\u0017\u0002\"\u0005b\u0011\u001d9\u0017\"!A\u0005\n!\u0014aaU=nE>d'\"A\t\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001M\u0019\u0001\u0001\u0006\r\u0011\u0005U1R\"\u0001\t\n\u0005]\u0001\"AB!osJ+g\r\u0005\u0002\u001a99\u0011QCG\u0005\u00037A\tq\u0001]1dW\u0006<W-\u0003\u0002\u001e=\ta1+\u001a:jC2L'0\u00192mK*\u00111\u0004E\u0001\u0005]\u0006lW-F\u0001\"!\t\u0011\u0013F\u0004\u0002$OA\u0011A\u0005E\u0007\u0002K)\u0011aEE\u0001\u0007yI|w\u000e\u001e \n\u0005!\u0002\u0012A\u0002)sK\u0012,g-\u0003\u0002+W\t11\u000b\u001e:j]\u001eT!\u0001\u000b\t\u0002\u000b9\fW.\u001a\u0011\u0002\rqJg.\u001b;?)\ty\u0003\u0007\u0005\u0002\u0016\u0001!)qd\u0001a\u0001C\u0005AAo\\*ue&tw\rF\u0001\"\u0003-\u0011X-\u00193SKN|GN^3\u0015\u0003U\u0002\"!\u0006\u001c\n\u0005]\u0002\"aA!os\"\u001aQ!\u000f#\u0011\u0007UQD(\u0003\u0002<!\t1A\u000f\u001b:poN\u0004\"!\u0010\"\u000e\u0003yR!a\u0010!\u0002\u0005%|'\"A!\u0002\t)\fg/Y\u0005\u0003\u0007z\u0012Qc\u00142kK\u000e$8\u000b\u001e:fC6,\u0005pY3qi&|gnI\u0001=\u0003!A\u0017m\u001d5D_\u0012,G#A$\u0011\u0005UA\u0015BA%\u0011\u0005\rIe\u000e^\u0001\u0007KF,\u0018\r\\:\u0015\u00051{\u0005CA\u000bN\u0013\tq\u0005CA\u0004C_>dW-\u00198\t\u000bA;\u0001\u0019A\u001b\u0002\u000b=$\b.\u001a:\u0002\rMKXNY8m!\t)\u0012bE\u0002\n)^\u0003B!F+\"_%\u0011a\u000b\u0005\u0002\u0010+:L\u0017/^3oKN\u001c8)Y2iKB\u0011Q\bW\u0005\u0003;y\"\u0012AU\u0001\u0006CB\u0004H.\u001f\u000b\u0003_qCQaH\u0006A\u0002\u0005\nAB^1mk\u00164%o\\7LKf$\"aL0\t\u000b}a\u0001\u0019A\u0011\u0002\u0019-,\u0017P\u0012:p[Z\u000bG.^3\u0015\u0005\t,\u0007cA\u000bdC%\u0011A\r\u0005\u0002\u0007\u001fB$\u0018n\u001c8\t\u000b\u0019l\u0001\u0019A\u0018\u0002\u0007MLX.\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u0001j!\tQW.D\u0001l\u0015\ta\u0007)\u0001\u0003mC:<\u0017B\u00018l\u0005\u0019y%M[3di\u0002")
public final class Symbol
implements Serializable {
    private final String name;

    public static Symbol apply(String name) {
        return Symbol$.MODULE$.apply(name);
    }

    public static Option unapply(Object other) {
        return Symbol$.MODULE$.keyFromValue((Symbol)other);
    }

    public String name() {
        return this.name;
    }

    public String toString() {
        return new StringBuilder(8).append("Symbol(").append(this.name()).append(")").toString();
    }

    private Object readResolve() throws ObjectStreamException {
        return Symbol$.MODULE$.apply(this.name());
    }

    public int hashCode() {
        return this.name().hashCode();
    }

    public boolean equals(Object other) {
        return this == other;
    }

    public Symbol(String name) {
        this.name = name;
    }
}

