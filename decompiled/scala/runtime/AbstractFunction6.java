/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1;
import scala.Function6;
import scala.Tuple6;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005I2QAA\u0002\u0002\u0002!AQA\f\u0001\u0005\u0002=\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c87\u0015\t!Q!A\u0004sk:$\u0018.\\3\u000b\u0003\u0019\tQa]2bY\u0006\u001c\u0001!\u0006\u0005\n'u\u00013EJ\u0015-'\r\u0001!B\u0004\t\u0003\u00171i\u0011!B\u0005\u0003\u001b\u0015\u0011a!\u00118z%\u00164\u0007#C\u0006\u0010#qy\"%\n\u0015,\u0013\t\u0001RAA\u0005Gk:\u001cG/[8omA\u0011!c\u0005\u0007\u0001\t\u0019!\u0002\u0001#b\u0001+\t\u0011A+M\t\u0003-e\u0001\"aC\f\n\u0005a)!a\u0002(pi\"Lgn\u001a\t\u0003\u0017iI!aG\u0003\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u0013;\u00111a\u0004\u0001EC\u0002U\u0011!\u0001\u0016\u001a\u0011\u0005I\u0001CAB\u0011\u0001\u0011\u000b\u0007QC\u0001\u0002UgA\u0011!c\t\u0003\u0007I\u0001A)\u0019A\u000b\u0003\u0005Q#\u0004C\u0001\n'\t\u00199\u0003\u0001#b\u0001+\t\u0011A+\u000e\t\u0003%%\"aA\u000b\u0001\t\u0006\u0004)\"A\u0001+7!\t\u0011B\u0006\u0002\u0004.\u0001\u0011\u0015\r!\u0006\u0002\u0002%\u00061A(\u001b8jiz\"\u0012\u0001\r\t\nc\u0001\tBd\b\u0012&Q-j\u0011a\u0001")
public abstract class AbstractFunction6<T1, T2, T3, T4, T5, T6, R>
implements Function6<T1, T2, T3, T4, T5, T6, R> {
    @Override
    public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, R>>>>>> curried() {
        return Function6.curried$(this);
    }

    @Override
    public Function1<Tuple6<T1, T2, T3, T4, T5, T6>, R> tupled() {
        return Function6.tupled$(this);
    }

    @Override
    public String toString() {
        return Function6.toString$(this);
    }
}

