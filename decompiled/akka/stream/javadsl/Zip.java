/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.Pair;
import akka.stream.FanInShape2;
import akka.stream.Graph;
import akka.stream.javadsl.Zip$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\r;Q!\u0002\u0004\t\u000251Qa\u0004\u0004\t\u0002AAQaF\u0001\u0005\u0002aAQ!G\u0001\u0005\u0002iAaaO\u0001!\u0002\u001ba\u0014a\u0001.ja*\u0011q\u0001C\u0001\bU\u00064\u0018\rZ:m\u0015\tI!\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u0017\u0005!\u0011m[6b\u0007\u0001\u0001\"AD\u0001\u000e\u0003\u0019\u00111AW5q'\t\t\u0011\u0003\u0005\u0002\u0013+5\t1CC\u0001\u0015\u0003\u0015\u00198-\u00197b\u0013\t12C\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u00035\taa\u0019:fCR,WcA\u000e&_U\tA\u0004\u0005\u0003\u001e=\u0001:T\"\u0001\u0005\n\u0005}A!!B$sCBD\u0007#B\u000f\"G9\n\u0014B\u0001\u0012\t\u0005-1\u0015M\\%o'\"\f\u0007/\u001a\u001a\u0011\u0005\u0011*C\u0002\u0001\u0003\u0006M\r\u0011\ra\n\u0002\u0002\u0003F\u0011\u0001f\u000b\t\u0003%%J!AK\n\u0003\u000f9{G\u000f[5oOB\u0011!\u0003L\u0005\u0003[M\u00111!\u00118z!\t!s\u0006B\u00031\u0007\t\u0007qEA\u0001C!\u0011\u0011Tg\t\u0018\u000e\u0003MR!\u0001\u000e\u0006\u0002\t)\f\u0007/[\u0005\u0003mM\u0012A\u0001U1jeB\u0011\u0001(O\u0007\u0002\u0015%\u0011!H\u0003\u0002\b\u001d>$Xk]3e\u0003\u001dyFo\u001c)bSJ\u0004R!\u0010!,W\tk\u0011A\u0010\u0006\u0003\u007fM\n\u0001BZ;oGRLwN\\\u0005\u0003\u0003z\u0012\u0011BR;oGRLwN\u001c\u001a\u0011\tI*4f\u000b")
public final class Zip {
    public static <A, B> Graph<FanInShape2<A, B, Pair<A, B>>, NotUsed> create() {
        return Zip$.MODULE$.create();
    }
}

