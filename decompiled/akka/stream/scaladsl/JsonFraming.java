/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Framing;
import akka.stream.scaladsl.JsonFraming$;
import akka.util.ByteString;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M<Q\u0001D\u0007\t\u0002Q1QAF\u0007\t\u0002]AQAH\u0001\u0005\u0002}1A\u0001I\u0001\u0001C!A!g\u0001B\u0001B\u0003%1\u0007C\u0003\u001f\u0007\u0011\u00051hB\u0004@\u0003\u0005\u0005\t\u0012\u0001!\u0007\u000f\u0001\n\u0011\u0011!E\u0001\u0003\")ad\u0002C\u0001\u0015\"91jBI\u0001\n\u0003a\u0005bB,\b\u0003\u0003%I\u0001\u0017\u0005\u0006?\u0006!\t\u0001Y\u0001\f\u0015N|gN\u0012:b[&twM\u0003\u0002\u000f\u001f\u0005A1oY1mC\u0012\u001cHN\u0003\u0002\u0011#\u000511\u000f\u001e:fC6T\u0011AE\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005U\tQ\"A\u0007\u0003\u0017)\u001bxN\u001c$sC6LgnZ\n\u0003\u0003a\u0001\"!\u0007\u000f\u000e\u0003iQ\u0011aG\u0001\u0006g\u000e\fG.Y\u0005\u0003;i\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\u0015\u0005Y\u0001\u0016M\u001d;jC2|%M[3di\u0016C8-\u001a9uS>t7CA\u0002#!\t\u0019sF\u0004\u0002%[9\u0011Q\u0005\f\b\u0003M-r!a\n\u0016\u000e\u0003!R!!K\n\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0011\u0012B\u0001\t\u0012\u0013\tqq\"\u0003\u0002/\u001b\u00059aI]1nS:<\u0017B\u0001\u00192\u0005A1%/Y7j]\u001e,\u0005pY3qi&|gN\u0003\u0002/\u001b\u0005\u0019Qn]4\u0011\u0005QBdBA\u001b7!\t9#$\u0003\u000285\u00051\u0001K]3eK\u001aL!!\u000f\u001e\u0003\rM#(/\u001b8h\u0015\t9$\u0004\u0006\u0002=}A\u0011QhA\u0007\u0002\u0003!9!'\u0002I\u0001\u0002\u0004\u0019\u0014A\u0006)beRL\u0017\r\\(cU\u0016\u001cG/\u0012=dKB$\u0018n\u001c8\u0011\u0005u:1cA\u0004\u0019\u0005B\u00111\tS\u0007\u0002\t*\u0011QIR\u0001\u0003S>T\u0011aR\u0001\u0005U\u00064\u0018-\u0003\u0002J\t\na1+\u001a:jC2L'0\u00192mKR\t\u0001)A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%M\u000b\u0002\u001b*\u00121GT\u0016\u0002\u001fB\u0011\u0001+V\u0007\u0002#*\u0011!kU\u0001\nk:\u001c\u0007.Z2lK\u0012T!\u0001\u0016\u000e\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002W#\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0003e\u0003\"AW/\u000e\u0003mS!\u0001\u0018$\u0002\t1\fgnZ\u0005\u0003=n\u0013aa\u00142kK\u000e$\u0018!D8cU\u0016\u001cGoU2b]:,'\u000f\u0006\u0002b]B)QC\u00193eU&\u00111-\u0004\u0002\u0005\r2|w\u000f\u0005\u0002fQ6\taM\u0003\u0002h#\u0005!Q\u000f^5m\u0013\tIgM\u0001\u0006CsR,7\u000b\u001e:j]\u001e\u0004\"a\u001b7\u000e\u0003EI!!\\\t\u0003\u000f9{G/V:fI\")qn\u0003a\u0001a\u0006\u0019R.\u0019=j[VlwJ\u00196fGRdUM\\4uQB\u0011\u0011$]\u0005\u0003ej\u00111!\u00138u\u0001")
public final class JsonFraming {
    public static Flow<ByteString, ByteString, NotUsed> objectScanner(int maximumObjectLength) {
        return JsonFraming$.MODULE$.objectScanner(maximumObjectLength);
    }

    public static class PartialObjectException
    extends Framing.FramingException {
        public PartialObjectException(String msg) {
            super(msg);
        }
    }
}

