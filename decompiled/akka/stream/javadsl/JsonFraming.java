/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.JsonFraming$;
import akka.util.ByteString;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u00051:Q\u0001B\u0003\t\u000211QAD\u0003\t\u0002=AQAF\u0001\u0005\u0002]AQ\u0001G\u0001\u0005\u0002e\t1BS:p]\u001a\u0013\u0018-\\5oO*\u0011aaB\u0001\bU\u00064\u0018\rZ:m\u0015\tA\u0011\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u0015\u0005!\u0011m[6b\u0007\u0001\u0001\"!D\u0001\u000e\u0003\u0015\u00111BS:p]\u001a\u0013\u0018-\\5oON\u0011\u0011\u0001\u0005\t\u0003#Qi\u0011A\u0005\u0006\u0002'\u0005)1oY1mC&\u0011QC\u0005\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005a\u0011!D8cU\u0016\u001cGoU2b]:,'\u000f\u0006\u0002\u001bOA)QbG\u000f\u001eG%\u0011A$\u0002\u0002\u0005\r2|w\u000f\u0005\u0002\u001fC5\tqD\u0003\u0002!\u0013\u0005!Q\u000f^5m\u0013\t\u0011sD\u0001\u0006CsR,7\u000b\u001e:j]\u001e\u0004\"\u0001J\u0013\u000e\u0003%I!AJ\u0005\u0003\u000f9{G/V:fI\")\u0001f\u0001a\u0001S\u0005\u0019R.\u0019=j[VlwJ\u00196fGRdUM\\4uQB\u0011\u0011CK\u0005\u0003WI\u00111!\u00138u\u0001")
public final class JsonFraming {
    public static Flow<ByteString, ByteString, NotUsed> objectScanner(int maximumObjectLength) {
        return JsonFraming$.MODULE$.objectScanner(maximumObjectLength);
    }
}

