/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.lang.reflect.Method;
import scala.reflect.ScalaSignature;
import scala.runtime.MethodCache;
import scala.runtime.PolyMethodCache;

@ScalaSignature(bytes="\u0006\u0005m2Q\u0001B\u0003\u0003\u000f%AQA\u0004\u0001\u0005\u0002AAQA\u0005\u0001\u0005\u0002MAQ!\r\u0001\u0005\u0002I\u0012\u0001#R7qiflU\r\u001e5pI\u000e\u000b7\r[3\u000b\u0005\u00199\u0011a\u0002:v]RLW.\u001a\u0006\u0002\u0011\u0005)1oY1mCN\u0011\u0001A\u0003\t\u0003\u00171i\u0011!B\u0005\u0003\u001b\u0015\u00111\"T3uQ>$7)Y2iK\u00061A(\u001b8jiz\u001a\u0001\u0001F\u0001\u0012!\tY\u0001!\u0001\u0003gS:$GC\u0001\u000b\u001f!\t)B$D\u0001\u0017\u0015\t9\u0002$A\u0004sK\u001adWm\u0019;\u000b\u0005eQ\u0012\u0001\u00027b]\u001eT\u0011aG\u0001\u0005U\u00064\u0018-\u0003\u0002\u001e-\t1Q*\u001a;i_\u0012DQa\b\u0002A\u0002\u0001\n1BZ8s%\u0016\u001cW-\u001b<feB\u0012\u0011e\n\t\u0004E\r*S\"\u0001\r\n\u0005\u0011B\"!B\"mCN\u001c\bC\u0001\u0014(\u0019\u0001!\u0011\u0002\u000b\u0010\u0002\u0002\u0003\u0005)\u0011A\u0015\u0003\u0007}#3'\u0005\u0002+]A\u00111\u0006L\u0007\u0002\u000f%\u0011Qf\u0002\u0002\b\u001d>$\b.\u001b8h!\tYs&\u0003\u00021\u000f\t\u0019\u0011I\\=\u0002\u0007\u0005$G\rF\u0002\u000bgeBQaH\u0002A\u0002Q\u0002$!N\u001c\u0011\u0007\t\u001ac\u0007\u0005\u0002'o\u0011I\u0001hMA\u0001\u0002\u0003\u0015\t!\u000b\u0002\u0004?\u0012\"\u0004\"\u0002\u001e\u0004\u0001\u0004!\u0012!\u00034pe6+G\u000f[8e\u0001")
public final class EmptyMethodCache
extends MethodCache {
    @Override
    public Method find(Class<?> forReceiver) {
        return null;
    }

    @Override
    public MethodCache add(Class<?> forReceiver, Method forMethod) {
        return new PolyMethodCache(this, forReceiver, forMethod, 1);
    }
}

