/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1;
import scala.Function9;
import scala.Tuple9;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m2QAA\u0002\u0002\u0002!AQa\u000e\u0001\u0005\u0002a\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c8:\u0015\t!Q!A\u0004sk:$\u0018.\\3\u000b\u0003\u0019\tQa]2bY\u0006\u001c\u0001!F\u0006\n'u\u00013EJ\u0015-_I*4c\u0001\u0001\u000b\u001dA\u00111\u0002D\u0007\u0002\u000b%\u0011Q\"\u0002\u0002\u0007\u0003:L(+\u001a4\u0011\u0019-y\u0011\u0003H\u0010#K!Zc&\r\u001b\n\u0005A)!!\u0003$v]\u000e$\u0018n\u001c8:!\t\u00112\u0003\u0004\u0001\u0005\rQ\u0001\u0001R1\u0001\u0016\u0005\t!\u0016'\u0005\u0002\u00173A\u00111bF\u0005\u00031\u0015\u0011qAT8uQ&tw\r\u0005\u0002\f5%\u00111$\u0002\u0002\u0004\u0003:L\bC\u0001\n\u001e\t\u0019q\u0002\u0001#b\u0001+\t\u0011AK\r\t\u0003%\u0001\"a!\t\u0001\t\u0006\u0004)\"A\u0001+4!\t\u00112\u0005\u0002\u0004%\u0001!\u0015\r!\u0006\u0002\u0003)R\u0002\"A\u0005\u0014\u0005\r\u001d\u0002\u0001R1\u0001\u0016\u0005\t!V\u0007\u0005\u0002\u0013S\u00111!\u0006\u0001EC\u0002U\u0011!\u0001\u0016\u001c\u0011\u0005IaCAB\u0017\u0001\u0011\u000b\u0007QC\u0001\u0002UoA\u0011!c\f\u0003\u0007a\u0001A)\u0019A\u000b\u0003\u0005QC\u0004C\u0001\n3\t\u0019\u0019\u0004\u0001#b\u0001+\t\u0011A+\u000f\t\u0003%U\"aA\u000e\u0001\u0005\u0006\u0004)\"!\u0001*\u0002\rqJg.\u001b;?)\u0005I\u0004\u0003\u0004\u001e\u0001#qy\"%\n\u0015,]E\"T\"A\u0002")
public abstract class AbstractFunction9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R>
implements Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> {
    @Override
    public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, R>>>>>>>>> curried() {
        return Function9.curried$(this);
    }

    @Override
    public Function1<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, R> tupled() {
        return Function9.tupled$(this);
    }

    @Override
    public String toString() {
        return Function9.toString$(this);
    }
}

