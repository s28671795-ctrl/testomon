/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BiFunction;
import java.util.function.Function;
import scala.Function2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00053A\u0001B\u0003\u0001\u001d!A!\u0007\u0001B\u0001B\u0003%1\u0007C\u00037\u0001\u0011\u0005q\u0007C\u0003<\u0001\u0011\u0005AH\u0001\tBg*\u000bg/\u0019\"j\rVt7\r^5p]*\u0011aaB\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0001\"C\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003\u0015-\taaY8na\u0006$(\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U!qBI\u00171'\r\u0001\u0001\u0003\u0007\t\u0003#Yi\u0011A\u0005\u0006\u0003'Q\tA\u0001\\1oO*\tQ#\u0001\u0003kCZ\f\u0017BA\f\u0013\u0005\u0019y%M[3diB)\u0011D\b\u0011-_5\t!D\u0003\u0002\u001c9\u0005Aa-\u001e8di&|gN\u0003\u0002\u001e)\u0005!Q\u000f^5m\u0013\ty\"D\u0001\u0006CS\u001a+hn\u0019;j_:\u0004\"!\t\u0012\r\u0001\u0011)1\u0005\u0001b\u0001I\t\tA+\u0005\u0002&SA\u0011aeJ\u0007\u0002\u0017%\u0011\u0001f\u0003\u0002\b\u001d>$\b.\u001b8h!\t1#&\u0003\u0002,\u0017\t\u0019\u0011I\\=\u0011\u0005\u0005jC!\u0002\u0018\u0001\u0005\u0004!#!A+\u0011\u0005\u0005\u0002D!B\u0019\u0001\u0005\u0004!#!\u0001*\u0002\u0005M4\u0007#\u0002\u00145A1z\u0013BA\u001b\f\u0005%1UO\\2uS>t''\u0001\u0004=S:LGO\u0010\u000b\u0003qi\u0002R!\u000f\u0001!Y=j\u0011!\u0002\u0005\u0006e\t\u0001\raM\u0001\u0006CB\u0004H.\u001f\u000b\u0004_uz\u0004\"\u0002 \u0004\u0001\u0004\u0001\u0013A\u0001=2\u0011\u0015\u00015\u00011\u0001-\u0003\tA(\u0007")
public class AsJavaBiFunction<T, U, R>
implements BiFunction<T, U, R> {
    private final Function2<T, U, R> sf;

    @Override
    public <V> BiFunction<T, U, V> andThen(Function<? super R, ? extends V> x$1) {
        return BiFunction.super.andThen(x$1);
    }

    @Override
    public R apply(T x1, U x2) {
        return this.sf.apply(x1, x2);
    }

    public AsJavaBiFunction(Function2<T, U, R> sf) {
        this.sf = sf;
    }
}

