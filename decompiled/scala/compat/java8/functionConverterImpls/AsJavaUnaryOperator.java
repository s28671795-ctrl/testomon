/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.UnaryOperator;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e2A\u0001B\u0003\u0001\u001d!AA\u0006\u0001B\u0001B\u0003%Q\u0006C\u00031\u0001\u0011\u0005\u0011\u0007C\u00036\u0001\u0011\u0005aGA\nBg*\u000bg/Y+oCJLx\n]3sCR|'O\u0003\u0002\u0007\u000f\u00051b-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7O\u0003\u0002\t\u0013\u0005)!.\u0019<bq)\u0011!bC\u0001\u0007G>l\u0007/\u0019;\u000b\u00031\tQa]2bY\u0006\u001c\u0001!\u0006\u0002\u0010EM\u0019\u0001\u0001\u0005\r\u0011\u0005E1R\"\u0001\n\u000b\u0005M!\u0012\u0001\u00027b]\u001eT\u0011!F\u0001\u0005U\u00064\u0018-\u0003\u0002\u0018%\t1qJ\u00196fGR\u00042!\u0007\u0010!\u001b\u0005Q\"BA\u000e\u001d\u0003!1WO\\2uS>t'BA\u000f\u0015\u0003\u0011)H/\u001b7\n\u0005}Q\"!D+oCJLx\n]3sCR|'\u000f\u0005\u0002\"E1\u0001A!B\u0012\u0001\u0005\u0004!#!\u0001+\u0012\u0005\u0015J\u0003C\u0001\u0014(\u001b\u0005Y\u0011B\u0001\u0015\f\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\n\u0016\n\u0005-Z!aA!os\u0006\u00111O\u001a\t\u0005M9\u0002\u0003%\u0003\u00020\u0017\tIa)\u001e8di&|g.M\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005I\"\u0004cA\u001a\u0001A5\tQ\u0001C\u0003-\u0005\u0001\u0007Q&A\u0003baBd\u0017\u0010\u0006\u0002!o!)\u0001h\u0001a\u0001A\u0005\u0011\u00010\r")
public class AsJavaUnaryOperator<T>
implements UnaryOperator<T> {
    private final Function1<T, T> sf;

    @Override
    public T apply(T x1) {
        return this.sf.apply(x1);
    }

    public AsJavaUnaryOperator(Function1<T, T> sf) {
        this.sf = sf;
    }
}

