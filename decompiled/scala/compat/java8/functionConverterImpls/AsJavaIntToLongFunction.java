/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntToLongFunction;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M2A\u0001B\u0003\u0001\u001d!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003+\u0001\u0011\u00051\u0006C\u00030\u0001\u0011\u0005\u0001GA\fBg*\u000bg/Y%oiR{Gj\u001c8h\rVt7\r^5p]*\u0011aaB\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0001\"C\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003\u0015-\taaY8na\u0006$(\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001M\u0019\u0001aD\f\u0011\u0005A)R\"A\t\u000b\u0005I\u0019\u0012\u0001\u00027b]\u001eT\u0011\u0001F\u0001\u0005U\u00064\u0018-\u0003\u0002\u0017#\t1qJ\u00196fGR\u0004\"\u0001G\u000f\u000e\u0003eQ!AG\u000e\u0002\u0011\u0019,hn\u0019;j_:T!\u0001H\n\u0002\tU$\u0018\u000e\\\u0005\u0003=e\u0011\u0011#\u00138u)>duN\\4Gk:\u001cG/[8o\u0003\t\u0019h\r\u0005\u0003\"E\u0011:S\"A\u0006\n\u0005\rZ!!\u0003$v]\u000e$\u0018n\u001c82!\t\tS%\u0003\u0002'\u0017\t\u0019\u0011J\u001c;\u0011\u0005\u0005B\u0013BA\u0015\f\u0005\u0011auN\\4\u0002\rqJg.\u001b;?)\tac\u0006\u0005\u0002.\u00015\tQ\u0001C\u0003 \u0005\u0001\u0007\u0001%A\u0006baBd\u00170Q:M_:<GCA\u00142\u0011\u0015\u00114\u00011\u0001%\u0003\tA\u0018\u0007")
public class AsJavaIntToLongFunction
implements IntToLongFunction {
    private final Function1<Object, Object> sf;

    @Override
    public long applyAsLong(int x1) {
        return this.sf.apply$mcJI$sp(x1);
    }

    public AsJavaIntToLongFunction(Function1<Object, Object> sf) {
        this.sf = sf;
    }
}

