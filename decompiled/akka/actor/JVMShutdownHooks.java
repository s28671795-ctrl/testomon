/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.annotation.InternalApi;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y2\u0001\u0002C\u0005\u0011\u0002G\u00051\"\u0004\u0005\u0006)\u00011\tA\u0006\u0005\u0006I\u00011\t!J\u0004\u0007c%A\ta\u0003\u001a\u0007\r!I\u0001\u0012A\u00065\u0011\u00151D\u0001\"\u00018\u0011\u0015!B\u0001\"\u00119\u0011\u0015!C\u0001\"\u0011;\u0005AQe+T*ikR$wn\u001e8I_>\\7O\u0003\u0002\u000b\u0017\u0005)\u0011m\u0019;pe*\tA\"\u0001\u0003bW.\f7C\u0001\u0001\u000f!\ty!#D\u0001\u0011\u0015\u0005\t\u0012!B:dC2\f\u0017BA\n\u0011\u0005\u0019\te.\u001f*fM\u00069\u0011\r\u001a3I_>\\7\u0001\u0001\u000b\u0003/i\u0001\"a\u0004\r\n\u0005e\u0001\"\u0001B+oSRDQaG\u0001A\u0002q\t\u0011\u0001\u001e\t\u0003;\tj\u0011A\b\u0006\u0003?\u0001\nA\u0001\\1oO*\t\u0011%\u0001\u0003kCZ\f\u0017BA\u0012\u001f\u0005\u0019!\u0006N]3bI\u0006Q!/Z7pm\u0016Dun\\6\u0015\u0005\u0019J\u0003CA\b(\u0013\tA\u0003CA\u0004C_>dW-\u00198\t\u000bm\u0011\u0001\u0019\u0001\u000f)\u0005\u0001Y\u0003C\u0001\u00170\u001b\u0005i#B\u0001\u0018\f\u0003)\tgN\\8uCRLwN\\\u0005\u0003a5\u00121\"\u00138uKJt\u0017\r\\!qS\u0006\u0001\"JV'TQV$Hm\\<o\u0011>|7n\u001d\t\u0003g\u0011i\u0011!C\n\u0004\t9)\u0004CA\u001a\u0001\u0003\u0019a\u0014N\\5u}Q\t!\u0007\u0006\u0002\u0018s!)1D\u0002a\u00019Q\u0011ae\u000f\u0005\u00067\u001d\u0001\r\u0001\b\u0015\u0003\t-B#aA\u0016")
@InternalApi
public interface JVMShutdownHooks {
    public void addHook(Thread var1);

    public boolean removeHook(Thread var1);
}

