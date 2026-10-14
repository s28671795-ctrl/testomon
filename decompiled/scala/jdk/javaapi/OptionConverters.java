/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk.javaapi;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import scala.Option;
import scala.jdk.javaapi.OptionConverters$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u001d<Qa\u0003\u0007\t\u0002M1Q!\u0006\u0007\t\u0002YAQaG\u0001\u0005\u0002qAQ!H\u0001\u0005\u0002yAQ\u0001O\u0001\u0005\u0002eBQ!R\u0001\u0005\u0002\u0019CQaT\u0001\u0005\u0002ACQ!W\u0001\u0005\u0002iCQ!W\u0001\u0005\u0002\u0005DQ!W\u0001\u0005\u0002\rDQ!W\u0001\u0005\u0002\u0015\f\u0001c\u00149uS>t7i\u001c8wKJ$XM]:\u000b\u00055q\u0011a\u00026bm\u0006\f\u0007/\u001b\u0006\u0003\u001fA\t1A\u001b3l\u0015\u0005\t\u0012!B:dC2\f7\u0001\u0001\t\u0003)\u0005i\u0011\u0001\u0004\u0002\u0011\u001fB$\u0018n\u001c8D_:4XM\u001d;feN\u001c\"!A\f\u0011\u0005aIR\"\u0001\t\n\u0005i\u0001\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002'\u00051Ao\u001c&bm\u0006,\"a\b\u0016\u0015\u0005\u0001\u001a\u0004cA\u0011'Q5\t!E\u0003\u0002$I\u0005!Q\u000f^5m\u0015\u0005)\u0013\u0001\u00026bm\u0006L!a\n\u0012\u0003\u0011=\u0003H/[8oC2\u0004\"!\u000b\u0016\r\u0001\u0011)1f\u0001b\u0001Y\t\t\u0011)\u0005\u0002.aA\u0011\u0001DL\u0005\u0003_A\u0011qAT8uQ&tw\r\u0005\u0002\u0019c%\u0011!\u0007\u0005\u0002\u0004\u0003:L\b\"\u0002\u001b\u0004\u0001\u0004)\u0014!A8\u0011\u0007a1\u0004&\u0003\u00028!\t1q\n\u001d;j_:\fA\u0003^8KCZ\fw\n\u001d;j_:\fG\u000eR8vE2,GC\u0001\u001e>!\t\t3(\u0003\u0002=E\tqq\n\u001d;j_:\fG\u000eR8vE2,\u0007\"\u0002\u001b\u0005\u0001\u0004q\u0004c\u0001\r7\u007fA\u0011\u0001iQ\u0007\u0002\u0003*\u0011!\tJ\u0001\u0005Y\u0006tw-\u0003\u0002E\u0003\n1Ai\\;cY\u0016\f\u0011\u0003^8KCZ\fw\n\u001d;j_:\fG.\u00138u)\t9%\n\u0005\u0002\"\u0011&\u0011\u0011J\t\u0002\f\u001fB$\u0018n\u001c8bY&sG\u000fC\u00035\u000b\u0001\u00071\nE\u0002\u0019m1\u0003\"\u0001Q'\n\u00059\u000b%aB%oi\u0016<WM]\u0001\u0013i>T\u0015M^1PaRLwN\\1m\u0019>tw\r\u0006\u0002R)B\u0011\u0011EU\u0005\u0003'\n\u0012Ab\u00149uS>t\u0017\r\u001c'p]\u001eDQ\u0001\u000e\u0004A\u0002U\u00032\u0001\u0007\u001cW!\t\u0001u+\u0003\u0002Y\u0003\n!Aj\u001c8h\u0003\u001d!xnU2bY\u0006,\"a\u00170\u0015\u0005q{\u0006c\u0001\r7;B\u0011\u0011F\u0018\u0003\u0006W\u001d\u0011\r\u0001\f\u0005\u0006i\u001d\u0001\r\u0001\u0019\t\u0004C\u0019jFC\u0001 c\u0011\u0015!\u0004\u00021\u0001;)\tYE\rC\u00035\u0013\u0001\u0007q\t\u0006\u0002VM\")AG\u0003a\u0001#\u0002")
public final class OptionConverters {
    public static Option<Long> toScala(OptionalLong o) {
        return OptionConverters$.MODULE$.toScala(o);
    }

    public static Option<Integer> toScala(OptionalInt o) {
        return OptionConverters$.MODULE$.toScala(o);
    }

    public static Option<Double> toScala(OptionalDouble o) {
        return OptionConverters$.MODULE$.toScala(o);
    }

    public static <A> Option<A> toScala(Optional<A> o) {
        return OptionConverters$.MODULE$.toScala(o);
    }

    public static OptionalLong toJavaOptionalLong(Option<Long> o) {
        return OptionConverters$.MODULE$.toJavaOptionalLong(o);
    }

    public static OptionalInt toJavaOptionalInt(Option<Integer> o) {
        return OptionConverters$.MODULE$.toJavaOptionalInt(o);
    }

    public static OptionalDouble toJavaOptionalDouble(Option<Double> o) {
        return OptionConverters$.MODULE$.toJavaOptionalDouble(o);
    }

    public static <A> Optional<A> toJava(Option<A> o) {
        return OptionConverters$.MODULE$.toJava(o);
    }
}

