/*
 * Decompiled with CFR 0.152.
 */
package akka.event.jul;

import akka.event.jul.Logger$;
import java.util.logging.Level;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0019<Qa\u0002\u0005\t\u0002=1Q!\u0005\u0005\t\u0002IAQ!G\u0001\u0005\u0002iAQaG\u0001\u0005\u0002qAQaG\u0001\u0005\u0002MBQaR\u0001\u0005\u0002!CQ!S\u0001\u0005\u0002)\u000ba\u0001T8hO\u0016\u0014(BA\u0005\u000b\u0003\rQW\u000f\u001c\u0006\u0003\u00171\tQ!\u001a<f]RT\u0011!D\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005A\tQ\"\u0001\u0005\u0003\r1{wmZ3s'\t\t1\u0003\u0005\u0002\u0015/5\tQCC\u0001\u0017\u0003\u0015\u00198-\u00197b\u0013\tARC\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003=\tQ!\u00199qYf$\"!\b\u0014\u0011\u0005y)S\"A\u0010\u000b\u0005\u0001\n\u0013a\u00027pO\u001eLgn\u001a\u0006\u0003E\r\nA!\u001e;jY*\tA%\u0001\u0003kCZ\f\u0017BA\t \u0011\u001593\u00011\u0001)\u0003\u0019awnZ4feB\u0011\u0011\u0006\r\b\u0003U9\u0002\"aK\u000b\u000e\u00031R!!\f\b\u0002\rq\u0012xn\u001c;?\u0013\tyS#\u0001\u0004Qe\u0016$WMZ\u0005\u0003cI\u0012aa\u0015;sS:<'BA\u0018\u0016)\riB'\u0012\u0005\u0006k\u0011\u0001\rAN\u0001\tY><7\t\\1tgB\u0012q\u0007\u0010\t\u0004SaR\u0014BA\u001d3\u0005\u0015\u0019E.Y:t!\tYD\b\u0004\u0001\u0005\u0013u\"\u0014\u0011!A\u0001\u0006\u0003q$aA0%cE\u0011qH\u0011\t\u0003)\u0001K!!Q\u000b\u0003\u000f9{G\u000f[5oOB\u0011AcQ\u0005\u0003\tV\u00111!\u00118z\u0011\u00151E\u00011\u0001)\u0003%awnZ*pkJ\u001cW-\u0001\u0003s_>$X#A\u000f\u0002\u00115\f\u0007\u000fT3wK2$\"a\u0013(\u0011\u0005ya\u0015BA' \u0005\u0015aUM^3m\u0011\u0015ye\u00011\u0001Q\u0003\u0015aWM^3m!\t\t\u0006L\u0004\u0002S-:\u00111+\u0016\b\u0003WQK\u0011!D\u0005\u0003\u00171I!a\u0016\u0006\u0002\u000f1{wmZ5oO&\u0011\u0011L\u0017\u0002\t\u0019><G*\u001a<fY*\u0011qK\u0003\u0015\u0007\u0003q{\u0006MY2\u0011\u0005Qi\u0016B\u00010\u0016\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\u0005\t\u0017AL+tK\u0002\u001aFJ\u0012\u001bKA=\u0014\b\u0005Z5sK\u000e$\bE[1wC:*H/\u001b7/Y><w-\u001b8hA%t7\u000f^3bI:\nQa]5oG\u0016\f\u0013\u0001Z\u0001\u0006e92d\u0006\r\u0015\u0007\u0001q{\u0006MY2")
public final class Logger {
    public static Level mapLevel(int level) {
        return Logger$.MODULE$.mapLevel(level);
    }

    public static java.util.logging.Logger root() {
        return Logger$.MODULE$.root();
    }

    public static java.util.logging.Logger apply(Class<?> logClass, String logSource) {
        return Logger$.MODULE$.apply(logClass, logSource);
    }

    public static java.util.logging.Logger apply(String logger2) {
        return Logger$.MODULE$.apply(logger2);
    }
}

