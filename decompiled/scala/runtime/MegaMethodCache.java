/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.lang.reflect.Method;
import scala.reflect.ScalaSignature;
import scala.runtime.MethodCache;

@ScalaSignature(bytes="\u0006\u0005e3QAB\u0004\u0003\u0013-A\u0001\u0002\u0005\u0001\u0003\u0002\u0003\u0006IA\u0005\u0005\t;\u0001\u0011\t\u0011)A\u0005=!)a\u0007\u0001C\u0001o!)\u0001\t\u0001C\u0001\u0003\")q\n\u0001C\u0001!\nyQ*Z4b\u001b\u0016$\bn\u001c3DC\u000eDWM\u0003\u0002\t\u0013\u00059!/\u001e8uS6,'\"\u0001\u0006\u0002\u000bM\u001c\u0017\r\\1\u0014\u0005\u0001a\u0001CA\u0007\u000f\u001b\u00059\u0011BA\b\b\u0005-iU\r\u001e5pI\u000e\u000b7\r[3\u0002\u000f\u0019|'OT1nK\u000e\u0001\u0001CA\n\u001b\u001d\t!\u0002\u0004\u0005\u0002\u0016\u00135\taC\u0003\u0002\u0018#\u00051AH]8pizJ!!G\u0005\u0002\rA\u0013X\rZ3g\u0013\tYBD\u0001\u0004TiJLgn\u001a\u0006\u00033%\t\u0011CZ8s!\u0006\u0014\u0018-\\3uKJ$\u0016\u0010]3t!\ry\u0002EI\u0007\u0002\u0013%\u0011\u0011%\u0003\u0002\u0006\u0003J\u0014\u0018-\u001f\u0019\u0003G5\u00022\u0001J\u0015,\u001b\u0005)#B\u0001\u0014(\u0003\u0011a\u0017M\\4\u000b\u0003!\nAA[1wC&\u0011!&\n\u0002\u0006\u00072\f7o\u001d\t\u0003Y5b\u0001\u0001B\u0005/\u0005\u0005\u0005\t\u0011!B\u0001_\t\u0019q\fJ\u001b\u0012\u0005A\u001a\u0004CA\u00102\u0013\t\u0011\u0014BA\u0004O_RD\u0017N\\4\u0011\u0005}!\u0014BA\u001b\n\u0005\r\te._\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007aJ$\b\u0005\u0002\u000e\u0001!)\u0001c\u0001a\u0001%!)Qd\u0001a\u0001wA\u0019q\u0004\t\u001f1\u0005uz\u0004c\u0001\u0013*}A\u0011Af\u0010\u0003\n]i\n\t\u0011!A\u0003\u0002=\nAAZ5oIR\u0011!\t\u0013\t\u0003\u0007\u001ak\u0011\u0001\u0012\u0006\u0003\u000b\u0016\nqA]3gY\u0016\u001cG/\u0003\u0002H\t\n1Q*\u001a;i_\u0012DQ!\u0013\u0003A\u0002)\u000b1BZ8s%\u0016\u001cW-\u001b<feB\u00121*\u0014\t\u0004I%b\u0005C\u0001\u0017N\t%q\u0005*!A\u0001\u0002\u000b\u0005qFA\u0002`IY\n1!\u00193e)\ra\u0011k\u0016\u0005\u0006\u0013\u0016\u0001\rA\u0015\u0019\u0003'V\u00032\u0001J\u0015U!\taS\u000bB\u0005W#\u0006\u0005\t\u0011!B\u0001_\t\u0019q\fJ\u001c\t\u000ba+\u0001\u0019\u0001\"\u0002\u0013\u0019|'/T3uQ>$\u0007")
public final class MegaMethodCache
extends MethodCache {
    private final String forName;
    private final Class<?>[] forParameterTypes;

    @Override
    public Method find(Class<?> forReceiver) {
        return forReceiver.getMethod(this.forName, this.forParameterTypes);
    }

    @Override
    public MethodCache add(Class<?> forReceiver, Method forMethod) {
        return this;
    }

    public MegaMethodCache(String forName, Class<?>[] forParameterTypes) {
        this.forName = forName;
        this.forParameterTypes = forParameterTypes;
    }
}

