/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.SerializedLambda;
import java.util.Map;
import scala.reflect.ScalaSignature;
import scala.runtime.LambdaDeserializer$;

@ScalaSignature(bytes="\u0006\u0005);Q!\u0002\u0004\t\u0002-1Q!\u0004\u0004\t\u00029AQaE\u0001\u0005\u0002QAQ!F\u0001\u0005\u0002YAQ\u0001R\u0001\u0005\u0002\u0015\u000b!\u0003T1nE\u0012\fG)Z:fe&\fG.\u001b>fe*\u0011q\u0001C\u0001\beVtG/[7f\u0015\u0005I\u0011!B:dC2\f7\u0001\u0001\t\u0003\u0019\u0005i\u0011A\u0002\u0002\u0013\u0019\u0006l'\rZ1EKN,'/[1mSj,'o\u0005\u0002\u0002\u001fA\u0011\u0001#E\u0007\u0002\u0011%\u0011!\u0003\u0003\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005Y\u0011!\u00053fg\u0016\u0014\u0018.\u00197ju\u0016d\u0015-\u001c2eCR)qbF\u0014>\u007f!)\u0001d\u0001a\u00013\u00051An\\8lkB\u0004\"A\u0007\u0013\u000f\u0005m\u0011S\"\u0001\u000f\u000b\u0005uq\u0012AB5om>\\WM\u0003\u0002 A\u0005!A.\u00198h\u0015\u0005\t\u0013\u0001\u00026bm\u0006L!a\t\u000f\u0002\u001b5+G\u000f[8e\u0011\u0006tG\r\\3t\u0013\t)cE\u0001\u0004M_>\\W\u000f\u001d\u0006\u0003GqAQ\u0001K\u0002A\u0002%\nQaY1dQ\u0016\u0004BAK\u00170u5\t1F\u0003\u0002-A\u0005!Q\u000f^5m\u0013\tq3FA\u0002NCB\u0004\"\u0001M\u001c\u000f\u0005E*\u0004C\u0001\u001a\t\u001b\u0005\u0019$B\u0001\u001b\u000b\u0003\u0019a$o\\8u}%\u0011a\u0007C\u0001\u0007!J,G-\u001a4\n\u0005aJ$AB*ue&twM\u0003\u00027\u0011A\u00111dO\u0005\u0003yq\u0011A\"T3uQ>$\u0007*\u00198eY\u0016DQAP\u0002A\u0002%\nq\u0002^1sO\u0016$X*\u001a;i_\u0012l\u0015\r\u001d\u0005\u0006\u0001\u000e\u0001\r!Q\u0001\u000bg\u0016\u0014\u0018.\u00197ju\u0016$\u0007CA\u000eC\u0013\t\u0019ED\u0001\tTKJL\u0017\r\\5{K\u0012d\u0015-\u001c2eC\u00069B-Z:fe&\fG.\u001b>f\u0019\u0006l'\rZ1Pe:+H\u000e\u001c\u000b\u0006\u001f\u0019;\u0005*\u0013\u0005\u00061\u0011\u0001\r!\u0007\u0005\u0006Q\u0011\u0001\r!\u000b\u0005\u0006}\u0011\u0001\r!\u000b\u0005\u0006\u0001\u0012\u0001\r!\u0011")
public final class LambdaDeserializer {
    public static Object deserializeLambdaOrNull(MethodHandles.Lookup lookup, Map<String, MethodHandle> cache, Map<String, MethodHandle> targetMethodMap, SerializedLambda serialized) {
        return LambdaDeserializer$.MODULE$.deserializeLambdaOrNull(lookup, cache, targetMethodMap, serialized);
    }

    public static Object deserializeLambda(MethodHandles.Lookup lookup, Map<String, MethodHandle> cache, Map<String, MethodHandle> targetMethodMap, SerializedLambda serialized) {
        return LambdaDeserializer$.MODULE$.deserializeLambda(lookup, cache, targetMethodMap, serialized);
    }
}

