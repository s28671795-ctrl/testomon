/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongUnaryOperator;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A2A\u0001B\u0003\u0001\u001d!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003-\u0001\u0011\u0005QFA\fBg*\u000bg/\u0019'p]\u001e,f.\u0019:z\u001fB,'/\u0019;pe*\u0011aaB\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0001\"C\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003\u0015-\taaY8na\u0006$(\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001M\u0019\u0001aD\f\u0011\u0005A)R\"A\t\u000b\u0005I\u0019\u0012\u0001\u00027b]\u001eT\u0011\u0001F\u0001\u0005U\u00064\u0018-\u0003\u0002\u0017#\t1qJ\u00196fGR\u0004\"\u0001G\u000f\u000e\u0003eQ!AG\u000e\u0002\u0011\u0019,hn\u0019;j_:T!\u0001H\n\u0002\tU$\u0018\u000e\\\u0005\u0003=e\u0011\u0011\u0003T8oOVs\u0017M]=Pa\u0016\u0014\u0018\r^8s\u0003\t\u0019h\r\u0005\u0003\"E\u0011\"S\"A\u0006\n\u0005\rZ!!\u0003$v]\u000e$\u0018n\u001c82!\t\tS%\u0003\u0002'\u0017\t!Aj\u001c8h\u0003\u0019a\u0014N\\5u}Q\u0011\u0011f\u000b\t\u0003U\u0001i\u0011!\u0002\u0005\u0006?\t\u0001\r\u0001I\u0001\fCB\u0004H._!t\u0019>tw\r\u0006\u0002%]!)qf\u0001a\u0001I\u0005\u0011\u00010\r")
public class AsJavaLongUnaryOperator
implements LongUnaryOperator {
    private final Function1<Object, Object> sf;

    @Override
    public LongUnaryOperator compose(LongUnaryOperator x$1) {
        return LongUnaryOperator.super.compose(x$1);
    }

    @Override
    public LongUnaryOperator andThen(LongUnaryOperator x$1) {
        return LongUnaryOperator.super.andThen(x$1);
    }

    @Override
    public long applyAsLong(long x1) {
        return this.sf.apply$mcJJ$sp(x1);
    }

    public AsJavaLongUnaryOperator(Function1<Object, Object> sf) {
        this.sf = sf;
    }
}

