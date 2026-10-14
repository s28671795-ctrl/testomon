/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import scala.Function2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a2qa\u0001\u0003\u0011\u0002\u0007\u0005\u0011\u0002C\u0003\u000f\u0001\u0011\u0005q\u0002C\u0003\u0014\u0001\u0011\rACA\u000eQe&|'/\u001b;zg\u0019+hn\u0019;j_:,\u0005\u0010^3og&|gn\u001d\u0006\u0003\u000b\u0019\t1A\u001b3l\u0015\u00059\u0011!B:dC2\f7\u0001A\n\u0003\u0001)\u0001\"a\u0003\u0007\u000e\u0003\u0019I!!\u0004\u0004\u0003\r\u0005s\u0017PU3g\u0003\u0019!\u0013N\\5uIQ\t\u0001\u0003\u0005\u0002\f#%\u0011!C\u0002\u0002\u0005+:LG/\u0001\ff]JL7\r[!t\u0015\u00064\u0018MQ5Gk:\u001cG/[8o+\u0011)\u0002EK\u0017\u0015\u0005Yy\u0003#B\f\u001c=%bcB\u0001\r\u001a\u001b\u0005!\u0011B\u0001\u000e\u0005\u0003A1UO\\2uS>twK]1qa\u0016\u00148/\u0003\u0002\u001d;\tI\"+[2i\rVt7\r^5p]J\n5OQ5Gk:\u001cG/[8o\u0015\tQB\u0001\u0005\u0002 A1\u0001A!B\u0011\u0003\u0005\u0004\u0011#!\u0001+\u0012\u0005\r2\u0003CA\u0006%\u0013\t)cAA\u0004O_RD\u0017N\\4\u0011\u0005-9\u0013B\u0001\u0015\u0007\u0005\r\te.\u001f\t\u0003?)\"Qa\u000b\u0002C\u0002\t\u0012\u0011!\u0016\t\u0003?5\"QA\f\u0002C\u0002\t\u0012\u0011A\u0015\u0005\u0006a\t\u0001\r!M\u0001\u0003g\u001a\u0004Ra\u0003\u001a\u001fS1J!a\r\u0004\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004F\u0001\u00026!\tYa'\u0003\u00028\r\t1\u0011N\u001c7j]\u0016\u0004")
public interface Priority3FunctionExtensions {
    public static /* synthetic */ Function2 enrichAsJavaBiFunction$(Priority3FunctionExtensions $this, Function2 sf) {
        return $this.enrichAsJavaBiFunction(sf);
    }

    default public <T, U, R> Function2<T, U, R> enrichAsJavaBiFunction(Function2<T, U, R> sf) {
        return sf;
    }

    public static void $init$(Priority3FunctionExtensions $this) {
    }
}

