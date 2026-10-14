/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.annotation.DoNotInherit;
import akka.stream.Graph;
import akka.stream.SinkShape;
import akka.stream.scaladsl.FlowOps;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-4q\u0001C\u0005\u0011\u0002\u0007\u0005\u0001\u0003C\u0003+\u0001\u0011\u00051&\u0002\u00030\u0001\u0001\u0002T\u0001\u0002&\u0001A\u001dCQa\u0013\u0001\u0007\u00021CQA\u0017\u0001\u0005\u0002mCQ!\u0018\u0001\u0007\u0002yCQ\u0001\u001a\u0001\u0005\u0002m\u0013qaU;c\r2|wO\u0003\u0002\u000b\u0017\u0005A1oY1mC\u0012\u001cHN\u0003\u0002\r\u001b\u000511\u000f\u001e:fC6T\u0011AD\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\u000bEq\u0002&\u0011%\u0014\u0007\u0001\u0011\u0002\u0004\u0005\u0002\u0014-5\tACC\u0001\u0016\u0003\u0015\u00198-\u00197b\u0013\t9BC\u0001\u0004B]f\u0014VM\u001a\t\u00053iar%D\u0001\n\u0013\tY\u0012BA\u0004GY><x\n]:\u0011\u0005uqB\u0002\u0001\u0003\u0007?\u0001!)\u0019\u0001\u0011\u0003\u0007=+H/\u0005\u0002\"IA\u00111CI\u0005\u0003GQ\u0011qAT8uQ&tw\r\u0005\u0002\u0014K%\u0011a\u0005\u0006\u0002\u0004\u0003:L\bCA\u000f)\t\u0019I\u0003\u0001\"b\u0001A\t\u0019Q*\u0019;\u0002\r\u0011Jg.\u001b;%)\u0005a\u0003CA\n.\u0013\tqCC\u0001\u0003V]&$(\u0001\u0002*faJ,\"!M\u001a\u0011\re\u0001!'N G!\ti2\u0007\u0002\u00045\u0005\u0011\u0015\r\u0001\t\u0002\u0002)*\u0012qEN\u0016\u0002oA\u0011\u0001(P\u0007\u0002s)\u0011!hO\u0001\nk:\u001c\u0007.Z2lK\u0012T!\u0001\u0010\u000b\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002?s\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3+\u0005\u00013\u0004CA\u000fB\t\u0019\u0011\u0005\u0001\"b\u0001\u0007\n\ta)\u0006\u0002!\t\u00121Q)\u0011CC\u0002\u0001\u0012Aa\u0018\u0013%c)\u0012qI\u000e\t\u0003;!#Q!\u0013\u0001C\u0002\u0001\u0012\u0011a\u0011\u0002\u0007\u00072|7/\u001a3\u0002\u0005Q|WCA'Y)\t9e\nC\u0003P\t\u0001\u0007\u0001+\u0001\u0003tS:\\\u0007\u0003B)S)^k\u0011aC\u0005\u0003'.\u0011Qa\u0012:ba\"\u00042!U+\u001d\u0013\t16BA\u0005TS:\\7\u000b[1qKB\u0011Q\u0004\u0017\u0003\u00063\u0012\u0011\r\u0001\t\u0002\u0002\u001b\u0006yQ.\u001a:hKN+(m\u001d;sK\u0006l7/F\u0001]!\ri\u0012\tH\u0001\u001f[\u0016\u0014x-Z*vEN$(/Z1ng^KG\u000f\u001b)be\u0006dG.\u001a7jg6$\"\u0001X0\t\u000b\u00014\u0001\u0019A1\u0002\u0017A\f'/\u00197mK2L7/\u001c\t\u0003'\tL!a\u0019\u000b\u0003\u0007%sG/\u0001\td_:\u001c\u0017\r^*vEN$(/Z1ng\"\u0012\u0001A\u001a\t\u0003O&l\u0011\u0001\u001b\u0006\u0003y5I!A\u001b5\u0003\u0019\u0011{gj\u001c;J]\",'/\u001b;")
@DoNotInherit
public interface SubFlow<Out, Mat, F, C>
extends FlowOps<Out, Mat> {
    @Override
    public <M> C to(Graph<SinkShape<Out>, M> var1);

    public static /* synthetic */ Object mergeSubstreams$(SubFlow $this) {
        return $this.mergeSubstreams();
    }

    default public F mergeSubstreams() {
        return this.mergeSubstreamsWithParallelism(Integer.MAX_VALUE);
    }

    public F mergeSubstreamsWithParallelism(int var1);

    public static /* synthetic */ Object concatSubstreams$(SubFlow $this) {
        return $this.concatSubstreams();
    }

    default public F concatSubstreams() {
        return this.mergeSubstreamsWithParallelism(1);
    }

    public static void $init$(SubFlow $this) {
    }
}

