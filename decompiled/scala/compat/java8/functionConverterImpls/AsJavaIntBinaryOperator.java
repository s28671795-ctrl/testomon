/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntBinaryOperator;
import scala.Function2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005I2A\u0001B\u0003\u0001\u001d!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003-\u0001\u0011\u0005QFA\fBg*\u000bg/Y%oi\nKg.\u0019:z\u001fB,'/\u0019;pe*\u0011aaB\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0001\"C\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003\u0015-\taaY8na\u0006$(\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001M\u0019\u0001aD\f\u0011\u0005A)R\"A\t\u000b\u0005I\u0019\u0012\u0001\u00027b]\u001eT\u0011\u0001F\u0001\u0005U\u00064\u0018-\u0003\u0002\u0017#\t1qJ\u00196fGR\u0004\"\u0001G\u000f\u000e\u0003eQ!AG\u000e\u0002\u0011\u0019,hn\u0019;j_:T!\u0001H\n\u0002\tU$\u0018\u000e\\\u0005\u0003=e\u0011\u0011#\u00138u\u0005&t\u0017M]=Pa\u0016\u0014\u0018\r^8s\u0003\t\u0019h\rE\u0003\"E\u0011\"C%D\u0001\f\u0013\t\u00193BA\u0005Gk:\u001cG/[8oeA\u0011\u0011%J\u0005\u0003M-\u00111!\u00138u\u0003\u0019a\u0014N\\5u}Q\u0011\u0011f\u000b\t\u0003U\u0001i\u0011!\u0002\u0005\u0006?\t\u0001\r\u0001I\u0001\u000bCB\u0004H._!t\u0013:$Hc\u0001\u0013/a!)qf\u0001a\u0001I\u0005\u0011\u00010\r\u0005\u0006c\r\u0001\r\u0001J\u0001\u0003qJ\u0002")
public class AsJavaIntBinaryOperator
implements IntBinaryOperator {
    private final Function2<Object, Object, Object> sf;

    @Override
    public int applyAsInt(int x1, int x2) {
        return this.sf.apply$mcIII$sp(x1, x2);
    }

    public AsJavaIntBinaryOperator(Function2<Object, Object, Object> sf) {
        this.sf = sf;
    }
}

