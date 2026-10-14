/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.ZipLatest$;
import akka.stream.scaladsl.ZipLatestWith2;
import java.io.Serializable;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005];QAC\u0006\t\u0002I1Q\u0001F\u0006\t\u0002UAQ\u0001H\u0001\u0005\u0002uAQAH\u0001\u0005\u0002}AQAH\u0001\u0005\u0002=3A\u0001F\u0006\u0003E!I\u0001(\u0002B\u0001B\u0003%\u0011\b\u0010\u0005\u00069\u0015!\t!\u0010\u0005\u00069\u0015!\t\u0001\u0011\u0005\u0006\u0003\u0016!\tEQ\u0001\n5&\u0004H*\u0019;fgRT!\u0001D\u0007\u0002\u0011M\u001c\u0017\r\\1eg2T!AD\b\u0002\rM$(/Z1n\u0015\u0005\u0001\u0012\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002\u0014\u00035\t1BA\u0005[SBd\u0015\r^3tiN\u0011\u0011A\u0006\t\u0003/ii\u0011\u0001\u0007\u0006\u00023\u0005)1oY1mC&\u00111\u0004\u0007\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005\u0011\u0012!B1qa2LXc\u0001\u0011M\u001dR\t\u0011\u0005\u0005\u0003\u0014\u000b-kUcA\u0012*gM\u0011Q\u0001\n\t\u0006'\u0015:#'N\u0005\u0003M-\u0011aBW5q\u0019\u0006$Xm\u001d;XSRD'\u0007\u0005\u0002)S1\u0001A!\u0002\u0016\u0006\u0005\u0004Y#!A!\u0012\u00051z\u0003CA\f.\u0013\tq\u0003DA\u0004O_RD\u0017N\\4\u0011\u0005]\u0001\u0014BA\u0019\u0019\u0005\r\te.\u001f\t\u0003QM\"Q\u0001N\u0003C\u0002-\u0012\u0011A\u0011\t\u0005/Y:#'\u0003\u000281\t1A+\u001e9mKJ\nQ\"Z1hKJ\u001cu.\u001c9mKR,\u0007CA\f;\u0013\tY\u0004DA\u0004C_>dW-\u00198\n\u0005a*CC\u0001 @!\u0011\u0019Ra\n\u001a\t\u000ba:\u0001\u0019A\u001d\u0015\u0003y\n\u0001\u0002^8TiJLgn\u001a\u000b\u0002\u0007B\u0011A)S\u0007\u0002\u000b*\u0011aiR\u0001\u0005Y\u0006twMC\u0001I\u0003\u0011Q\u0017M^1\n\u0005)+%AB*ue&tw\r\u0005\u0002)\u0019\u0012)!f\u0001b\u0001WA\u0011\u0001F\u0014\u0003\u0006i\r\u0011\raK\u000b\u0004!N+FCA)W!\u0011\u0019RA\u0015+\u0011\u0005!\u001aF!\u0002\u0016\u0005\u0005\u0004Y\u0003C\u0001\u0015V\t\u0015!DA1\u0001,\u0011\u0015AD\u00011\u0001:\u0001")
public final class ZipLatest<A, B>
extends ZipLatestWith2<A, B, Tuple2<A, B>> {
    public static <A, B> ZipLatest<A, B> apply(boolean eagerComplete) {
        return ZipLatest$.MODULE$.apply(eagerComplete);
    }

    public static <A, B> ZipLatest<A, B> apply() {
        return ZipLatest$.MODULE$.apply();
    }

    @Override
    public String toString() {
        return "ZipLatest";
    }

    public ZipLatest(boolean eagerComplete) {
        super(new Serializable(){
            private static final long serialVersionUID = 0L;

            public final Tuple2<A, B> apply(A _1, B _2) {
                return new Tuple2<A, B>(_1, _2);
            }
        }, eagerComplete);
    }

    public ZipLatest() {
        this(true);
    }
}

