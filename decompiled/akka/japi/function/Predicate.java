/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.function;

import java.io.Serializable;
import scala.reflect.ScalaSignature;

@FunctionalInterface
@ScalaSignature(bytes="\u0006\u0005e2qAA\u0002\u0011\u0002G\u0005!\u0002C\u0003\u001b\u0001\u0019\u00051DA\u0005Qe\u0016$\u0017nY1uK*\u0011A!B\u0001\tMVt7\r^5p]*\u0011aaB\u0001\u0005U\u0006\u0004\u0018NC\u0001\t\u0003\u0011\t7n[1\u0004\u0001U\u00111bI\n\u0004\u00011\u0011\u0002CA\u0007\u0011\u001b\u0005q!\"A\b\u0002\u000bM\u001c\u0017\r\\1\n\u0005Eq!AB!osJ+g\r\u0005\u0002\u001415\tAC\u0003\u0002\u0016-\u0005\u0011\u0011n\u001c\u0006\u0002/\u0005!!.\u0019<b\u0013\tIBC\u0001\u0007TKJL\u0017\r\\5{C\ndW-\u0001\u0003uKN$HC\u0001\u000f !\tiQ$\u0003\u0002\u001f\u001d\t9!i\\8mK\u0006t\u0007\"\u0002\u0011\u0002\u0001\u0004\t\u0013!\u00029be\u0006l\u0007C\u0001\u0012$\u0019\u0001!a\u0001\n\u0001\t\u0006\u0004)#!\u0001+\u0012\u0005\u0019J\u0003CA\u0007(\u0013\tAcBA\u0004O_RD\u0017N\\4\u0011\u00055Q\u0013BA\u0016\u000f\u0005\r\te.\u001f\u0015\u0003\u00015\u0002\"AL\u0019\u000e\u0003=R!\u0001\r\f\u0002\t1\fgnZ\u0005\u0003e=\u00121CR;oGRLwN\\1m\u0013:$XM\u001d4bG\u0016DC\u0001\u0001\u001b8qA\u0011Q\"N\u0005\u0003m9\u0011\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u0002\u000bY\fG.^3\u001f\u0003\u0005\u0001")
public interface Predicate<T>
extends Serializable {
    public boolean test(T var1);
}

