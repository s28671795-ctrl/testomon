/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.scaladsl.ZipN$;
import akka.stream.scaladsl.ZipWithN;
import akka.util.ConstantFun$;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M;Q!\u0003\u0006\t\u0002E1Qa\u0005\u0006\t\u0002QAQaG\u0001\u0005\u0002qAQ!H\u0001\u0005\u0002y1Aa\u0005\u0006\u0003C!A\u0011\b\u0002B\u0001B\u0003%!\bC\u0003\u001c\t\u0011\u0005Q\bC\u0003A\t\u0011\u0005\u0013\tC\u0003G\t\u0011\u0005s)\u0001\u0003[SBt%BA\u0006\r\u0003!\u00198-\u00197bINd'BA\u0007\u000f\u0003\u0019\u0019HO]3b[*\tq\"\u0001\u0003bW.\f7\u0001\u0001\t\u0003%\u0005i\u0011A\u0003\u0002\u00055&\u0004hj\u0005\u0002\u0002+A\u0011a#G\u0007\u0002/)\t\u0001$A\u0003tG\u0006d\u0017-\u0003\u0002\u001b/\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#A\t\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\u0005}\tFC\u0001\u0011S!\r\u0011B\u0001U\u000b\u0003E!\u001a\"\u0001B\u0012\u0011\tI!c%M\u0005\u0003K)\u0011\u0001BW5q/&$\bN\u0014\t\u0003O!b\u0001\u0001B\u0003*\t\t\u0007!FA\u0001B#\tYc\u0006\u0005\u0002\u0017Y%\u0011Qf\u0006\u0002\b\u001d>$\b.\u001b8h!\t1r&\u0003\u00021/\t\u0019\u0011I\\=\u0011\u0007I:d%D\u00014\u0015\t!T'A\u0005j[6,H/\u00192mK*\u0011agF\u0001\u000bG>dG.Z2uS>t\u0017B\u0001\u001d4\u0005\r\u0019V-]\u0001\u0002]B\u0011acO\u0005\u0003y]\u00111!\u00138u)\tqt\bE\u0002\u0013\t\u0019BQ!\u000f\u0004A\u0002i\n\u0011#\u001b8ji&\fG.\u0011;ue&\u0014W\u000f^3t+\u0005\u0011\u0005CA\"E\u001b\u0005a\u0011BA#\r\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\ti>\u001cFO]5oOR\t\u0001\n\u0005\u0002J\u001d6\t!J\u0003\u0002L\u0019\u0006!A.\u00198h\u0015\u0005i\u0015\u0001\u00026bm\u0006L!a\u0014&\u0003\rM#(/\u001b8h!\t9\u0013\u000bB\u0003*\u0007\t\u0007!\u0006C\u0003:\u0007\u0001\u0007!\b")
public final class ZipN<A>
extends ZipWithN<A, Seq<A>> {
    public static <A> ZipN<A> apply(int n) {
        return ZipN$.MODULE$.apply(n);
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.zipN();
    }

    @Override
    public String toString() {
        return "ZipN";
    }

    public ZipN(int n) {
        super(ConstantFun$.MODULE$.scalaIdentityFunction(), n);
    }
}

