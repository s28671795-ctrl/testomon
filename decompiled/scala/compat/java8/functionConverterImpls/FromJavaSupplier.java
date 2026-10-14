/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Supplier;
import scala.Function0;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q2A\u0001B\u0003\u0001\u001d!A!\u0005\u0001B\u0001B\u0003%1\u0005C\u0003.\u0001\u0011\u0005a\u0006C\u00033\u0001\u0011\u00051G\u0001\tGe>l'*\u0019<b'V\u0004\b\u000f\\5fe*\u0011aaB\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0001\"C\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003\u0015-\taaY8na\u0006$(\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011q\"G\n\u0004\u0001A!\u0002CA\t\u0013\u001b\u0005Y\u0011BA\n\f\u0005\u0019\te.\u001f*fMB\u0019\u0011#F\f\n\u0005YY!!\u0003$v]\u000e$\u0018n\u001c81!\tA\u0012\u0004\u0004\u0001\u0005\u000bi\u0001!\u0019A\u000e\u0003\u0003Q\u000b\"\u0001H\u0010\u0011\u0005Ei\u0012B\u0001\u0010\f\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!\u0005\u0011\n\u0005\u0005Z!aA!os\u0006\u0011!N\u001a\t\u0004I-:R\"A\u0013\u000b\u0005\u0019:\u0013\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005!J\u0013\u0001B;uS2T\u0011AK\u0001\u0005U\u00064\u0018-\u0003\u0002-K\tA1+\u001e9qY&,'/\u0001\u0004=S:LGO\u0010\u000b\u0003_E\u00022\u0001\r\u0001\u0018\u001b\u0005)\u0001\"\u0002\u0012\u0003\u0001\u0004\u0019\u0013!B1qa2LH#A\f")
public class FromJavaSupplier<T>
implements Function0<T> {
    private final Supplier<T> jf;

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
    public String toString() {
        return Function0.toString$(this);
    }

    @Override
    public T apply() {
        return this.jf.get();
    }

    public FromJavaSupplier(Supplier<T> jf) {
        this.jf = jf;
        Function0.$init$(this);
    }
}

