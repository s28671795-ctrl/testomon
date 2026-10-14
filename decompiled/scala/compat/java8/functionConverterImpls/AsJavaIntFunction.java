/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntFunction;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005q2A\u0001B\u0003\u0001\u001d!AA\u0006\u0001B\u0001B\u0003%Q\u0006C\u00034\u0001\u0011\u0005A\u0007C\u00039\u0001\u0011\u0005\u0011HA\tBg*\u000bg/Y%oi\u001a+hn\u0019;j_:T!AB\u0004\u0002-\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYNT!\u0001C\u0005\u0002\u000b)\fg/\u0019\u001d\u000b\u0005)Y\u0011AB2p[B\fGOC\u0001\r\u0003\u0015\u00198-\u00197b\u0007\u0001)\"a\u0004\u0012\u0014\u0007\u0001\u0001\u0002\u0004\u0005\u0002\u0012-5\t!C\u0003\u0002\u0014)\u0005!A.\u00198h\u0015\u0005)\u0012\u0001\u00026bm\u0006L!a\u0006\n\u0003\r=\u0013'.Z2u!\rIb\u0004I\u0007\u00025)\u00111\u0004H\u0001\tMVt7\r^5p]*\u0011Q\u0004F\u0001\u0005kRLG.\u0003\u0002 5\tY\u0011J\u001c;Gk:\u001cG/[8o!\t\t#\u0005\u0004\u0001\u0005\u000b\r\u0002!\u0019\u0001\u0013\u0003\u0003I\u000b\"!J\u0015\u0011\u0005\u0019:S\"A\u0006\n\u0005!Z!a\u0002(pi\"Lgn\u001a\t\u0003M)J!aK\u0006\u0003\u0007\u0005s\u00170\u0001\u0002tMB!aE\f\u0019!\u0013\ty3BA\u0005Gk:\u001cG/[8ocA\u0011a%M\u0005\u0003e-\u00111!\u00138u\u0003\u0019a\u0014N\\5u}Q\u0011Qg\u000e\t\u0004m\u0001\u0001S\"A\u0003\t\u000b1\u0012\u0001\u0019A\u0017\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0005\u0001R\u0004\"B\u001e\u0004\u0001\u0004\u0001\u0014A\u0001=2\u0001")
public class AsJavaIntFunction<R>
implements IntFunction<R> {
    private final Function1<Object, R> sf;

    @Override
    public R apply(int x1) {
        return this.sf.apply(BoxesRunTime.boxToInteger(x1));
    }

    public AsJavaIntFunction(Function1<Object, R> sf) {
        this.sf = sf;
    }
}

