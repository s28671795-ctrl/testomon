/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.TLSRole$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0011:Qa\u0002\u0005\t\u000251Qa\u0004\u0005\t\u0002AAQaF\u0001\u0005\u0002aAQ!G\u0001\u0005\u0002iAQaI\u0001\u0005\u0002i1Qa\u0004\u0005\u0002\"qAQaF\u0003\u0005\u0002u\tq\u0001\u0016'T%>dWM\u0003\u0002\n\u0015\u000511\u000f\u001e:fC6T\u0011aC\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u00059\tQ\"\u0001\u0005\u0003\u000fQc5KU8mKN\u0011\u0011!\u0005\t\u0003%Ui\u0011a\u0005\u0006\u0002)\u0005)1oY1mC&\u0011ac\u0005\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005i\u0011AB2mS\u0016tG/F\u0001\u001c!\tqQa\u0005\u0002\u0006#Q\t1$K\u0002\u0006?\u0005J!\u0001\t\u0005\u0003\r\rc\u0017.\u001a8u\u0013\t\u0011\u0003B\u0001\u0004TKJ4XM]\u0001\u0007g\u0016\u0014h/\u001a:")
public abstract class TLSRole {
    public static TLSRole server() {
        return TLSRole$.MODULE$.server();
    }

    public static TLSRole client() {
        return TLSRole$.MODULE$.client();
    }
}

