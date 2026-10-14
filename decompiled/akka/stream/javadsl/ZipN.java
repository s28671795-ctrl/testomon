/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.UniformFanInShape;
import akka.stream.javadsl.ZipN$;
import java.util.List;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y:Q\u0001B\u0003\t\u000211QAD\u0003\t\u0002=AQAF\u0001\u0005\u0002]AQ\u0001G\u0001\u0005\u0002e\tAAW5q\u001d*\u0011aaB\u0001\bU\u00064\u0018\rZ:m\u0015\tA\u0011\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u0015\u0005!\u0011m[6b\u0007\u0001\u0001\"!D\u0001\u000e\u0003\u0015\u0011AAW5q\u001dN\u0011\u0011\u0001\u0005\t\u0003#Qi\u0011A\u0005\u0006\u0002'\u0005)1oY1mC&\u0011QC\u0005\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005a\u0011AB2sK\u0006$X-\u0006\u0002\u001bIQ\u00111$\u000f\t\u00059uyR'D\u0001\b\u0013\tqrAA\u0003He\u0006\u0004\b\u000e\u0005\u0003\u001dA\tj\u0013BA\u0011\b\u0005E)f.\u001b4pe64\u0015M\\%o'\"\f\u0007/\u001a\t\u0003G\u0011b\u0001\u0001B\u0003&\u0007\t\u0007aEA\u0001B#\t9#\u0006\u0005\u0002\u0012Q%\u0011\u0011F\u0005\u0002\b\u001d>$\b.\u001b8h!\t\t2&\u0003\u0002-%\t\u0019\u0011I\\=\u0011\u00079\u001a$%D\u00010\u0015\t\u0001\u0014'\u0001\u0003vi&d'\"\u0001\u001a\u0002\t)\fg/Y\u0005\u0003i=\u0012A\u0001T5tiB\u0011agN\u0007\u0002\u0013%\u0011\u0001(\u0003\u0002\b\u001d>$Xk]3e\u0011\u0015Q4\u00011\u0001<\u0003\u0005q\u0007CA\t=\u0013\ti$CA\u0002J]R\u0004")
public final class ZipN {
    public static <A> Graph<UniformFanInShape<A, List<A>>, NotUsed> create(int n) {
        return ZipN$.MODULE$.create(n);
    }
}

