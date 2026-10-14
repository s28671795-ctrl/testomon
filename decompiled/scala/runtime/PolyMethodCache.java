/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.lang.reflect.Method;
import scala.reflect.ScalaSignature;
import scala.runtime.MegaMethodCache;
import scala.runtime.MethodCache;

@ScalaSignature(bytes="\u0006\u0005)4Qa\u0003\u0007\u0003\u001dAA\u0001\"\u0006\u0001\u0003\u0002\u0003\u0006I!\u0005\u0005\t/\u0001\u0011\t\u0011)A\u00051!AQ\u0006\u0001B\u0001B\u0003%a\u0006\u0003\u00055\u0001\t\u0005\t\u0015!\u00036\u0011\u0015A\u0004\u0001\"\u0001:\u0011\u0015\u0019\u0005\u0001\"\u0003E\u0011\u0015\u0019\u0006\u0001\"\u0001U\u0011\u001dY\u0006A1A\u0005\u000eqCaa\u0018\u0001!\u0002\u001bi\u0006\"\u00021\u0001\t\u0003\t'a\u0004)pYflU\r\u001e5pI\u000e\u000b7\r[3\u000b\u00055q\u0011a\u0002:v]RLW.\u001a\u0006\u0002\u001f\u0005)1oY1mCN\u0011\u0001!\u0005\t\u0003%Mi\u0011\u0001D\u0005\u0003)1\u00111\"T3uQ>$7)Y2iK\u0006!a.\u001a=u\u0007\u0001\t\u0001B]3dK&4XM\u001d\u0019\u00033\r\u00022AG\u0010\"\u001b\u0005Y\"B\u0001\u000f\u001e\u0003\u0011a\u0017M\\4\u000b\u0003y\tAA[1wC&\u0011\u0001e\u0007\u0002\u0006\u00072\f7o\u001d\t\u0003E\rb\u0001\u0001B\u0005%\u0005\u0005\u0005\t\u0011!B\u0001K\t\u0019q\f\n\u001d\u0012\u0005\u0019R\u0003CA\u0014)\u001b\u0005q\u0011BA\u0015\u000f\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"aJ\u0016\n\u00051r!aA!os\u00061Q.\u001a;i_\u0012\u0004\"a\f\u001a\u000e\u0003AR!!M\u000e\u0002\u000fI,g\r\\3di&\u00111\u0007\r\u0002\u0007\u001b\u0016$\bn\u001c3\u0002\u0015\r|W\u000e\u001d7fq&$\u0018\u0010\u0005\u0002(m%\u0011qG\u0004\u0002\u0004\u0013:$\u0018A\u0002\u001fj]&$h\bF\u0003;wq\n%\t\u0005\u0002\u0013\u0001!)Q#\u0002a\u0001#!)q#\u0002a\u0001{A\u0012a\b\u0011\t\u00045}y\u0004C\u0001\u0012A\t%!C(!A\u0001\u0002\u000b\u0005Q\u0005C\u0003.\u000b\u0001\u0007a\u0006C\u00035\u000b\u0001\u0007Q'\u0001\u0007gS:$\u0017J\u001c;fe:\fG\u000e\u0006\u0002/\u000b\")aI\u0002a\u0001\u000f\u0006Yam\u001c:SK\u000e,\u0017N^3sa\tA%\nE\u0002\u001b?%\u0003\"A\t&\u0005\u0013-+\u0015\u0011!A\u0001\u0006\u0003)#aA0%s!\u0012a!\u0014\t\u0003\u001dFk\u0011a\u0014\u0006\u0003!:\t!\"\u00198o_R\fG/[8o\u0013\t\u0011vJA\u0004uC&d'/Z2\u0002\t\u0019Lg\u000e\u001a\u000b\u0003]UCQAR\u0004A\u0002Y\u0003$aV-\u0011\u0007iy\u0002\f\u0005\u0002#3\u0012I!,VA\u0001\u0002\u0003\u0015\t!\n\u0002\u0005?\u0012\n\u0004'A\u0007NCb\u001cu.\u001c9mKbLG/_\u000b\u0002;>\ta,\b\u0002\u0001A\u0007qQ*\u0019=D_6\u0004H.\u001a=jif\u0004\u0013aA1eIR\u0019\u0011C\u00195\t\u000b\u0019S\u0001\u0019A21\u0005\u00114\u0007c\u0001\u000e KB\u0011!E\u001a\u0003\nO\n\f\t\u0011!A\u0003\u0002\u0015\u0012Aa\u0018\u00132c!)\u0011N\u0003a\u0001]\u0005Iam\u001c:NKRDw\u000e\u001a")
public final class PolyMethodCache
extends MethodCache {
    private final MethodCache next;
    private final Class<?> receiver;
    private final Method method;
    private final int complexity;

    private Method findInternal(Class<?> forReceiver) {
        while (true) {
            if (forReceiver == this_.receiver) {
                return this_.method;
            }
            MethodCache methodCache = this_.next;
            if (!(methodCache instanceof PolyMethodCache)) break;
            PolyMethodCache this_ = (PolyMethodCache)methodCache;
        }
        return this_.next.find(forReceiver);
    }

    @Override
    public Method find(Class<?> forReceiver) {
        return this.findInternal(forReceiver);
    }

    private final int MaxComplexity() {
        return 160;
    }

    @Override
    public MethodCache add(Class<?> forReceiver, Method forMethod) {
        if (this.complexity < 160) {
            return new PolyMethodCache(this, forReceiver, forMethod, this.complexity + 1);
        }
        return new MegaMethodCache(forMethod.getName(), forMethod.getParameterTypes());
    }

    public PolyMethodCache(MethodCache next, Class<?> receiver, Method method2, int complexity) {
        this.next = next;
        this.receiver = receiver;
        this.method = method2;
        this.complexity = complexity;
    }
}

