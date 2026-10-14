/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk.javaapi;

import java.time.Duration;
import scala.concurrent.duration.FiniteDuration;
import scala.jdk.javaapi.DurationConverters$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u00055:Q!\u0002\u0004\t\u000251Qa\u0004\u0004\t\u0002AAQ!F\u0001\u0005\u0002YAQaF\u0001\u0005\u0002aAQAK\u0001\u0005\u0002-\n!\u0003R;sCRLwN\\\"p]Z,'\u000f^3sg*\u0011q\u0001C\u0001\bU\u00064\u0018-\u00199j\u0015\tI!\"A\u0002kI.T\u0011aC\u0001\u0006g\u000e\fG.Y\u0002\u0001!\tq\u0011!D\u0001\u0007\u0005I!UO]1uS>t7i\u001c8wKJ$XM]:\u0014\u0005\u0005\t\u0002C\u0001\n\u0014\u001b\u0005Q\u0011B\u0001\u000b\u000b\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012!D\u0001\bi>\u001c6-\u00197b)\tI\u0012\u0005\u0005\u0002\u001b?5\t1D\u0003\u0002\u001d;\u0005AA-\u001e:bi&|gN\u0003\u0002\u001f\u0015\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u0005\u0001Z\"A\u0004$j]&$X\rR;sCRLwN\u001c\u0005\u00069\r\u0001\rA\t\t\u0003G!j\u0011\u0001\n\u0006\u0003K\u0019\nA\u0001^5nK*\tq%\u0001\u0003kCZ\f\u0017BA\u0015%\u0005!!UO]1uS>t\u0017A\u0002;p\u0015\u00064\u0018\r\u0006\u0002#Y!)A\u0004\u0002a\u00013\u0001")
public final class DurationConverters {
    public static Duration toJava(FiniteDuration duration) {
        return DurationConverters$.MODULE$.toJava(duration);
    }

    public static FiniteDuration toScala(Duration duration) {
        return DurationConverters$.MODULE$.toScala(duration);
    }
}

