/*
 * Decompiled with CFR 0.152.
 */
package akka;

import akka.AkkaVersion$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U:Q!\u0002\u0004\t\u0002%1Qa\u0003\u0004\t\u00021AQaE\u0001\u0005\u0002QAQ!F\u0001\u0005\u0002YAa!F\u0001\u0005\u0002\u0019I\u0013aC!lW\u00064VM]:j_:T\u0011aB\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005)\tQ\"\u0001\u0004\u0003\u0017\u0005[7.\u0019,feNLwN\\\n\u0003\u00035\u0001\"AD\t\u000e\u0003=Q\u0011\u0001E\u0001\u0006g\u000e\fG.Y\u0005\u0003%=\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\n\u0003\u001d\u0011X-];je\u0016$2a\u0006\u000e(!\tq\u0001$\u0003\u0002\u001a\u001f\t!QK\\5u\u0011\u0015Y2\u00011\u0001\u001d\u0003-a\u0017N\u0019:beft\u0015-\\3\u0011\u0005u!cB\u0001\u0010#!\tyr\"D\u0001!\u0015\t\t\u0003\"\u0001\u0004=e>|GOP\u0005\u0003G=\ta\u0001\u0015:fI\u00164\u0017BA\u0013'\u0005\u0019\u0019FO]5oO*\u00111e\u0004\u0005\u0006Q\r\u0001\r\u0001H\u0001\u0010e\u0016\fX/\u001b:fIZ+'o]5p]R!qCK\u0016-\u0011\u0015YB\u00011\u0001\u001d\u0011\u0015AC\u00011\u0001\u001d\u0011\u0015iC\u00011\u0001\u001d\u00039\u0019WO\u001d:f]R4VM]:j_:D#\u0001B\u0018\u0011\u0005A\u001aT\"A\u0019\u000b\u0005I2\u0011AC1o]>$\u0018\r^5p]&\u0011A'\r\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e")
public final class AkkaVersion {
    public static void require(String libraryName, String requiredVersion) {
        AkkaVersion$.MODULE$.require(libraryName, requiredVersion);
    }
}

