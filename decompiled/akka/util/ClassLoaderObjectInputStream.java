/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00193A!\u0002\u0004\u0001\u0017!AA\u0003\u0001B\u0001B\u0003%Q\u0003\u0003\u0005\u001c\u0001\t\u0005\t\u0015!\u0003\u001d\u0011\u0015y\u0002\u0001\"\u0001!\u0011\u0015)\u0003\u0001\"\u0015'\u0005q\u0019E.Y:t\u0019>\fG-\u001a:PE*,7\r^%oaV$8\u000b\u001e:fC6T!a\u0002\u0005\u0002\tU$\u0018\u000e\u001c\u0006\u0002\u0013\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001\u0001\u0007\u0011\u00055\u0011R\"\u0001\b\u000b\u0005=\u0001\u0012AA5p\u0015\u0005\t\u0012\u0001\u00026bm\u0006L!a\u0005\b\u0003#=\u0013'.Z2u\u0013:\u0004X\u000f^*ue\u0016\fW.A\u0006dY\u0006\u001c8\u000fT8bI\u0016\u0014\bC\u0001\f\u001a\u001b\u00059\"B\u0001\r\u0011\u0003\u0011a\u0017M\\4\n\u0005i9\"aC\"mCN\u001cHj\\1eKJ\f!![:\u0011\u00055i\u0012B\u0001\u0010\u000f\u0005-Ie\u000e];u'R\u0014X-Y7\u0002\rqJg.\u001b;?)\r\t3\u0005\n\t\u0003E\u0001i\u0011A\u0002\u0005\u0006)\r\u0001\r!\u0006\u0005\u00067\r\u0001\r\u0001H\u0001\re\u0016\u001cx\u000e\u001c<f\u00072\f7o\u001d\u000b\u0003O\u0005\u0003$\u0001K\u001c\u0011\u0007%\u0012TG\u0004\u0002+aA\u00111FL\u0007\u0002Y)\u0011QFC\u0001\u0007yI|w\u000e\u001e \u000b\u0003=\nQa]2bY\u0006L!!\r\u0018\u0002\rA\u0013X\rZ3g\u0013\t\u0019DGA\u0003DY\u0006\u001c8O\u0003\u00022]A\u0011ag\u000e\u0007\u0001\t%AD!!A\u0001\u0002\u000b\u0005\u0011HA\u0002`IE\n\"A\u000f \u0011\u0005mbT\"\u0001\u0018\n\u0005ur#a\u0002(pi\"Lgn\u001a\t\u0003w}J!\u0001\u0011\u0018\u0003\u0007\u0005s\u0017\u0010C\u0003C\t\u0001\u00071)A\tpE*,7\r^*ue\u0016\fWn\u00117bgN\u0004\"!\u0004#\n\u0005\u0015s!!E(cU\u0016\u001cGo\u0015;sK\u0006l7\t\\1tg\u0002")
public class ClassLoaderObjectInputStream
extends ObjectInputStream {
    private final ClassLoader classLoader;

    @Override
    public Class<?> resolveClass(ObjectStreamClass objectStreamClass) {
        Class<?> clazz;
        try {
            clazz = Class.forName(objectStreamClass.getName(), false, this.classLoader);
        }
        catch (ClassNotFoundException classNotFoundException) {
            clazz = super.resolveClass(objectStreamClass);
        }
        return clazz;
    }

    public ClassLoaderObjectInputStream(ClassLoader classLoader, InputStream is) {
        this.classLoader = classLoader;
        super(is);
    }
}

