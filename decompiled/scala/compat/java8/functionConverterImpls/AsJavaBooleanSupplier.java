/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BooleanSupplier;
import scala.Function0;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000592A\u0001B\u0003\u0001\u001d!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003-\u0001\u0011\u0005QFA\u000bBg*\u000bg/\u0019\"p_2,\u0017M\\*vaBd\u0017.\u001a:\u000b\u0005\u00199\u0011A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005!I\u0011!\u00026bm\u0006D$B\u0001\u0006\f\u0003\u0019\u0019w.\u001c9bi*\tA\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0007\u0001yq\u0003\u0005\u0002\u0011+5\t\u0011C\u0003\u0002\u0013'\u0005!A.\u00198h\u0015\u0005!\u0012\u0001\u00026bm\u0006L!AF\t\u0003\r=\u0013'.Z2u!\tAR$D\u0001\u001a\u0015\tQ2$\u0001\u0005gk:\u001cG/[8o\u0015\ta2#\u0001\u0003vi&d\u0017B\u0001\u0010\u001a\u0005=\u0011un\u001c7fC:\u001cV\u000f\u001d9mS\u0016\u0014\u0018AA:g!\r\t#\u0005J\u0007\u0002\u0017%\u00111e\u0003\u0002\n\rVt7\r^5p]B\u0002\"!I\u0013\n\u0005\u0019Z!a\u0002\"p_2,\u0017M\\\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005%Z\u0003C\u0001\u0016\u0001\u001b\u0005)\u0001\"B\u0010\u0003\u0001\u0004\u0001\u0013\u0001D4fi\u0006\u001b(i\\8mK\u0006tG#\u0001\u0013")
public class AsJavaBooleanSupplier
implements BooleanSupplier {
    private final Function0<Object> sf;

    @Override
    public boolean getAsBoolean() {
        return this.sf.apply$mcZ$sp();
    }

    public AsJavaBooleanSupplier(Function0<Object> sf) {
        this.sf = sf;
    }
}

