/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.IOResult;
import akka.stream.scaladsl.Sink;
import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.StreamConverters$;
import akka.util.ByteString;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.stream.BaseStream;
import java.util.stream.Collector;
import java.util.stream.Stream;
import scala.Function0;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Ev!B\b\u0011\u0011\u00039b!B\r\u0011\u0011\u0003Q\u0002\"B\u0011\u0002\t\u0003\u0011\u0003\"B\u0012\u0002\t\u0003!\u0003b\u0002&\u0002#\u0003%\ta\u0013\u0005\u0006-\u0006!\ta\u0016\u0005\bI\u0006\t\n\u0011\"\u0001f\u0011\u00159\u0017\u0001\"\u0001i\u0011\u001d!\u0018!%A\u0005\u0002UDQa^\u0001\u0005\u0002aDq\u0001`\u0001\u0012\u0002\u0013\u0005Q\rC\u0003~\u0003\u0011\u0005a\u0010C\u0004\u0002H\u0005!\t!!\u0013\t\u000f\u0005e\u0014\u0001\"\u0001\u0002|!9\u00111R\u0001\u0005\u0002\u00055\u0015\u0001E*ue\u0016\fWnQ8om\u0016\u0014H/\u001a:t\u0015\t\t\"#\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\t\u0019B#\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002+\u0005!\u0011m[6b\u0007\u0001\u0001\"\u0001G\u0001\u000e\u0003A\u0011\u0001c\u0015;sK\u0006l7i\u001c8wKJ$XM]:\u0014\u0005\u0005Y\u0002C\u0001\u000f \u001b\u0005i\"\"\u0001\u0010\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0001j\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002/\u0005yaM]8n\u0013:\u0004X\u000f^*ue\u0016\fW\u000eF\u0002&q\u0015\u0003B\u0001\u0007\u0014)]%\u0011q\u0005\u0005\u0002\u0007'>,(oY3\u0011\u0005%bS\"\u0001\u0016\u000b\u0005-\"\u0012\u0001B;uS2L!!\f\u0016\u0003\u0015\tKH/Z*ue&tw\rE\u00020eQj\u0011\u0001\r\u0006\u0003cu\t!bY8oGV\u0014(/\u001a8u\u0013\t\u0019\u0004G\u0001\u0004GkR,(/\u001a\t\u0003kYj\u0011AE\u0005\u0003oI\u0011\u0001\"S(SKN,H\u000e\u001e\u0005\u0006s\r\u0001\rAO\u0001\u0003S:\u00042\u0001H\u001e>\u0013\taTDA\u0005Gk:\u001cG/[8oaA\u0011ahQ\u0007\u0002\u007f)\u0011\u0001)Q\u0001\u0003S>T\u0011AQ\u0001\u0005U\u00064\u0018-\u0003\u0002E\u007f\tY\u0011J\u001c9viN#(/Z1n\u0011\u001d15\u0001%AA\u0002\u001d\u000b\u0011b\u00195v].\u001c\u0016N_3\u0011\u0005qA\u0015BA%\u001e\u0005\rIe\u000e^\u0001\u001aMJ|W.\u00138qkR\u001cFO]3b[\u0012\"WMZ1vYR$#'F\u0001MU\t9UjK\u0001O!\tyE+D\u0001Q\u0015\t\t&+A\u0005v]\u000eDWmY6fI*\u00111+H\u0001\u000bC:tw\u000e^1uS>t\u0017BA+Q\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fCN|U\u000f\u001e9viN#(/Z1n)\tAF\f\u0005\u0003\u0019M!J\u0006C\u0001 [\u0013\tYvH\u0001\u0007PkR\u0004X\u000f^*ue\u0016\fW\u000eC\u0004^\u000bA\u0005\t\u0019\u00010\u0002\u0019]\u0014\u0018\u000e^3US6,w.\u001e;\u0011\u0005}\u0013W\"\u00011\u000b\u0005\u0005\u0004\u0014\u0001\u00033ve\u0006$\u0018n\u001c8\n\u0005\r\u0004'A\u0004$j]&$X\rR;sCRLwN\\\u0001\u0019CN|U\u000f\u001e9viN#(/Z1nI\u0011,g-Y;mi\u0012\nT#\u00014+\u0005yk\u0015\u0001\u00054s_6|U\u000f\u001e9viN#(/Z1n)\rIGn\u001c\t\u00051)Dc&\u0003\u0002l!\t!1+\u001b8l\u0011\u0015iw\u00011\u0001o\u0003\ryW\u000f\u001e\t\u00049mJ\u0006b\u00029\b!\u0003\u0005\r!]\u0001\nCV$xN\u00127vg\"\u0004\"\u0001\b:\n\u0005Ml\"a\u0002\"p_2,\u0017M\\\u0001\u001bMJ|WnT;uaV$8\u000b\u001e:fC6$C-\u001a4bk2$HEM\u000b\u0002m*\u0012\u0011/T\u0001\u000eCNLe\u000e];u'R\u0014X-Y7\u0015\u0005eT\b\u0003\u0002\rkQuBqa_\u0005\u0011\u0002\u0003\u0007a,A\u0006sK\u0006$G+[7f_V$\u0018aF1t\u0013:\u0004X\u000f^*ue\u0016\fW\u000e\n3fM\u0006,H\u000e\u001e\u00132\u00035Q\u0017M^1D_2dWm\u0019;peV)q0a\u0002\u0002\u001eQ!\u0011\u0011AA\u0011!\u0019A\".a\u0001\u0002\u001aA!\u0011QAA\u0004\u0019\u0001!q!!\u0003\f\u0005\u0004\tYAA\u0001U#\u0011\ti!a\u0005\u0011\u0007q\ty!C\u0002\u0002\u0012u\u0011qAT8uQ&tw\rE\u0002\u001d\u0003+I1!a\u0006\u001e\u0005\r\te.\u001f\t\u0005_I\nY\u0002\u0005\u0003\u0002\u0006\u0005uAaBA\u0010\u0017\t\u0007\u00111\u0002\u0002\u0002%\"9\u00111E\u0006A\u0002\u0005\u0015\u0012\u0001E2pY2,7\r^8s\r\u0006\u001cGo\u001c:z!\u0011a2(a\n1\t\u0005%\u0012q\u0007\t\u000b\u0003W\t\t$a\u0001\u00026\u0005mQBAA\u0017\u0015\r\u0019\u0012q\u0006\u0006\u0003W\u0005KA!a\r\u0002.\tI1i\u001c7mK\u000e$xN\u001d\t\u0005\u0003\u000b\t9\u0004\u0002\u0007\u0002:\u0005m\u0012\u0011!A\u0001\u0006\u0003\tYAA\u0002`IEBq!a\t\f\u0001\u0004\ti\u0004\u0005\u0003\u001dw\u0005}\u0002\u0007BA!\u0003o\u0001\"\"a\u000b\u00022\u0005\r\u0013QGA#!\u0011\t)!a\u0002\u0011\t\u0005\u0015\u0011QD\u0001\u001fU\u00064\u0018mQ8mY\u0016\u001cGo\u001c:QCJ\fG\u000e\\3m+:|'\u000fZ3sK\u0012,b!a\u0013\u0002T\u0005eC\u0003BA'\u0003k\"B!a\u0014\u0002\\A1\u0001D[A)\u0003+\u0002B!!\u0002\u0002T\u00119\u0011\u0011\u0002\u0007C\u0002\u0005-\u0001\u0003B\u00183\u0003/\u0002B!!\u0002\u0002Z\u00119\u0011q\u0004\u0007C\u0002\u0005-\u0001bBA\u0012\u0019\u0001\u0007\u0011Q\f\t\u00059m\ny\u0006\r\u0003\u0002b\u0005\u0015\u0004CCA\u0016\u0003c\t\t&a\u0019\u0002XA!\u0011QAA3\t1\t9'!\u001b\u0002\u0002\u0003\u0005)\u0011AA\u0006\u0005\ryFE\r\u0005\b\u0003Ga\u0001\u0019AA6!\u0011a2(!\u001c1\t\u0005=\u0014Q\r\t\u000b\u0003W\t\t$!\u001d\u0002d\u0005M\u0004\u0003BA\u0003\u0003'\u0002B!!\u0002\u0002Z!1\u0011q\u000f\u0007A\u0002\u001d\u000b1\u0002]1sC2dW\r\\5t[\u0006a\u0011m\u001d&bm\u0006\u001cFO]3b[V!\u0011QPAB)\t\ty\b\u0005\u0004\u0019U\u0006\u0005\u0015Q\u0011\t\u0005\u0003\u000b\t\u0019\tB\u0004\u0002\n5\u0011\r!a\u0003\u0011\r\u0005-\u0012qQAA\u0013\u0011\tI)!\f\u0003\rM#(/Z1n\u000391'o\\7KCZ\f7\u000b\u001e:fC6,b!a$\u0002\u0016\u0006-F\u0003BAI\u0003?\u0003b\u0001\u0007\u0014\u0002\u0014\u0006]\u0005\u0003BA\u0003\u0003+#q!!\u0003\u000f\u0005\u0004\tY\u0001\u0005\u0003\u0002\u001a\u0006mU\"\u0001\u000b\n\u0007\u0005uECA\u0004O_R,6/\u001a3\t\rMq\u0001\u0019AAQ!\u0011a2(a)\u0011\u0011\u0005-\u0012QUAJ\u0003SKA!a*\u0002.\tQ!)Y:f'R\u0014X-Y7\u0011\t\u0005\u0015\u00111\u0016\u0003\b\u0003[s!\u0019AAX\u0005\u0005\u0019\u0016\u0003BA\u0007\u0003G\u0003")
public final class StreamConverters {
    public static <T, S extends BaseStream<T, S>> Source<T, NotUsed> fromJavaStream(Function0<BaseStream<T, S>> stream) {
        return StreamConverters$.MODULE$.fromJavaStream(stream);
    }

    public static <T> Sink<T, Stream<T>> asJavaStream() {
        return StreamConverters$.MODULE$.asJavaStream();
    }

    public static <T, R> Sink<T, Future<R>> javaCollectorParallelUnordered(int parallelism, Function0<Collector<T, ?, R>> collectorFactory) {
        return StreamConverters$.MODULE$.javaCollectorParallelUnordered(parallelism, collectorFactory);
    }

    public static <T, R> Sink<T, Future<R>> javaCollector(Function0<Collector<T, ?, R>> collectorFactory) {
        return StreamConverters$.MODULE$.javaCollector(collectorFactory);
    }

    public static FiniteDuration asInputStream$default$1() {
        return StreamConverters$.MODULE$.asInputStream$default$1();
    }

    public static Sink<ByteString, InputStream> asInputStream(FiniteDuration readTimeout) {
        return StreamConverters$.MODULE$.asInputStream(readTimeout);
    }

    public static boolean fromOutputStream$default$2() {
        return StreamConverters$.MODULE$.fromOutputStream$default$2();
    }

    public static Sink<ByteString, Future<IOResult>> fromOutputStream(Function0<OutputStream> out, boolean autoFlush) {
        return StreamConverters$.MODULE$.fromOutputStream(out, autoFlush);
    }

    public static FiniteDuration asOutputStream$default$1() {
        return StreamConverters$.MODULE$.asOutputStream$default$1();
    }

    public static Source<ByteString, OutputStream> asOutputStream(FiniteDuration writeTimeout) {
        return StreamConverters$.MODULE$.asOutputStream(writeTimeout);
    }

    public static int fromInputStream$default$2() {
        return StreamConverters$.MODULE$.fromInputStream$default$2();
    }

    public static Source<ByteString, Future<IOResult>> fromInputStream(Function0<InputStream> in, int chunkSize) {
        return StreamConverters$.MODULE$.fromInputStream(in, chunkSize);
    }
}

