/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1;
import scala.Function3;
import scala.Tuple3;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005%2QAA\u0002\u0002\u0002!AQ!\n\u0001\u0005\u0002\u0019\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c84\u0015\t!Q!A\u0004sk:$\u0018.\\3\u000b\u0003\u0019\tQa]2bY\u0006\u001c\u0001!F\u0003\n'u\u00013eE\u0002\u0001\u00159\u0001\"a\u0003\u0007\u000e\u0003\u0015I!!D\u0003\u0003\r\u0005s\u0017PU3g!\u0019Yq\"\u0005\u000f E%\u0011\u0001#\u0002\u0002\n\rVt7\r^5p]N\u0002\"AE\n\r\u0001\u00111A\u0003\u0001EC\u0002U\u0011!\u0001V\u0019\u0012\u0005YI\u0002CA\u0006\u0018\u0013\tARAA\u0004O_RD\u0017N\\4\u0011\u0005-Q\u0012BA\u000e\u0006\u0005\r\te.\u001f\t\u0003%u!aA\b\u0001\t\u0006\u0004)\"A\u0001+3!\t\u0011\u0002\u0005\u0002\u0004\"\u0001!\u0015\r!\u0006\u0002\u0003)N\u0002\"AE\u0012\u0005\r\u0011\u0002AQ1\u0001\u0016\u0005\u0005\u0011\u0016A\u0002\u001fj]&$h\bF\u0001(!\u0019A\u0003!\u0005\u000f E5\t1\u0001")
public abstract class AbstractFunction3<T1, T2, T3, R>
implements Function3<T1, T2, T3, R> {
    @Override
    public Function1<T1, Function1<T2, Function1<T3, R>>> curried() {
        return Function3.curried$(this);
    }

    @Override
    public Function1<Tuple3<T1, T2, T3>, R> tupled() {
        return Function3.tupled$(this);
    }

    @Override
    public String toString() {
        return Function3.toString$(this);
    }
}

