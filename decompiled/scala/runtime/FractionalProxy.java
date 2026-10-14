/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.math.Fractional;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaNumberProxy;

@ScalaSignature(bytes="\u0006\u0005=2q\u0001B\u0003\u0011\u0002\u0007\u0005!\u0002C\u0003\u001d\u0001\u0011\u0005Q\u0004C\u0003\"\u0001\u0019M!\u0005C\u0003+\u0001\u0011\u00051FA\bGe\u0006\u001cG/[8oC2\u0004&o\u001c=z\u0015\t1q!A\u0004sk:$\u0018.\\3\u000b\u0003!\tQa]2bY\u0006\u001c\u0001!\u0006\u0002\f-M\u0019\u0001\u0001\u0004\t\u0011\u00055qQ\"A\u0004\n\u0005=9!aA!osB\u0019\u0011C\u0005\u000b\u000e\u0003\u0015I!aE\u0003\u0003!M\u001b\u0017\r\\1Ok6\u0014WM\u001d)s_bL\bCA\u000b\u0017\u0019\u0001!Qa\u0006\u0001C\u0002a\u0011\u0011\u0001V\t\u000331\u0001\"!\u0004\u000e\n\u0005m9!a\u0002(pi\"LgnZ\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003y\u0001\"!D\u0010\n\u0005\u0001:!\u0001B+oSR\f1A\\;n+\u0005\u0019\u0003c\u0001\u0013()9\u0011Q\"J\u0005\u0003M\u001d\tq\u0001]1dW\u0006<W-\u0003\u0002)S\tQaI]1di&|g.\u00197\u000b\u0005\u0019:\u0011aB5t/\"|G.Z\u000b\u0002YA\u0011Q\"L\u0005\u0003]\u001d\u0011qAQ8pY\u0016\fg\u000e")
public interface FractionalProxy<T>
extends ScalaNumberProxy<T> {
    @Override
    public Fractional<T> num();

    public static /* synthetic */ boolean isWhole$(FractionalProxy $this) {
        return $this.isWhole();
    }

    @Override
    default public boolean isWhole() {
        return false;
    }

    public static void $init$(FractionalProxy $this) {
    }
}

