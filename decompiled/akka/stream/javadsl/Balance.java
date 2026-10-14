/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.UniformFanOutShape;
import akka.stream.javadsl.Balance$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dq!B\u0005\u000b\u0011\u0003\tb!B\n\u000b\u0011\u0003!\u0002\"B\u000e\u0002\t\u0003a\u0002\"B\u000f\u0002\t\u0003q\u0002\"B\u000f\u0002\t\u0003\u0001\u0005\"B\u000f\u0002\t\u0003Q\u0005\"B\u000f\u0002\t\u0003\t\u0006\"B\u000f\u0002\t\u0003a\u0007\"B\u000f\u0002\t\u00039\u0018a\u0002\"bY\u0006t7-\u001a\u0006\u0003\u00171\tqA[1wC\u0012\u001cHN\u0003\u0002\u000e\u001d\u000511\u000f\u001e:fC6T\u0011aD\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005I\tQ\"\u0001\u0006\u0003\u000f\t\u000bG.\u00198dKN\u0011\u0011!\u0006\t\u0003-ei\u0011a\u0006\u0006\u00021\u0005)1oY1mC&\u0011!d\u0006\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005\t\u0012AB2sK\u0006$X-\u0006\u0002 SQ\u0019\u0001EN\u001e\u0011\t\u0005\u0012CEM\u0007\u0002\u0019%\u00111\u0005\u0004\u0002\u0006\u000fJ\f\u0007\u000f\u001b\t\u0005C\u0015:s%\u0003\u0002'\u0019\t\u0011RK\\5g_Jlg)\u00198PkR\u001c\u0006.\u00199f!\tA\u0013\u0006\u0004\u0001\u0005\u000b)\u001a!\u0019A\u0016\u0003\u0003Q\u000b\"\u0001L\u0018\u0011\u0005Yi\u0013B\u0001\u0018\u0018\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u0006\u0019\n\u0005E:\"aA!osB\u00111\u0007N\u0007\u0002\u001d%\u0011QG\u0004\u0002\b\u001d>$Xk]3e\u0011\u001594\u00011\u00019\u0003-yW\u000f\u001e9vi\u000e{WO\u001c;\u0011\u0005YI\u0014B\u0001\u001e\u0018\u0005\rIe\u000e\u001e\u0005\u0006y\r\u0001\r!P\u0001\u0016o\u0006LGOR8s\u00032dGi\\<ogR\u0014X-Y7t!\t1b(\u0003\u0002@/\t9!i\\8mK\u0006tWCA!F)\u0011\u0011ei\u0012%\u0011\t\u0005\u00123I\r\t\u0005C\u0015\"E\t\u0005\u0002)\u000b\u0012)!\u0006\u0002b\u0001W!)q\u0007\u0002a\u0001q!)A\b\u0002a\u0001{!)\u0011\n\u0002a\u0001{\u0005YQ-Y4fe\u000e\u000bgnY3m+\tYu\n\u0006\u0002M!B!\u0011EI'3!\u0011\tSE\u0014(\u0011\u0005!zE!\u0002\u0016\u0006\u0005\u0004Y\u0003\"B\u001c\u0006\u0001\u0004ATC\u0001*W)\r\u0019vk\u001b\t\u0005C\t\"&\u0007\u0005\u0003\"KU+\u0006C\u0001\u0015W\t\u0015QcA1\u0001,\u0011\u0015Af\u00011\u0001Z\u0003\u0015\u0019G.\u0019>{!\rQ\u0016-\u0016\b\u00037~\u0003\"\u0001X\f\u000e\u0003uS!A\u0018\t\u0002\rq\u0012xn\u001c;?\u0013\t\u0001w#\u0001\u0004Qe\u0016$WMZ\u0005\u0003E\u000e\u0014Qa\u00117bgNT!\u0001Y\f)\u0005]+\u0007C\u00014j\u001b\u00059'B\u00015\u000f\u0003\u0011)H/\u001b7\n\u0005)<'AB;okN,G\rC\u00038\r\u0001\u0007\u0001(\u0006\u0002ncR!aN];w!\u0011\t#e\u001c\u001a\u0011\t\u0005*\u0003\u000f\u001d\t\u0003QE$QAK\u0004C\u0002-BQ\u0001W\u0004A\u0002M\u00042AW1qQ\t\u0011X\rC\u00038\u000f\u0001\u0007\u0001\bC\u0003=\u000f\u0001\u0007Q(\u0006\u0002yyRA\u00110`A\u0001\u0003\u0007\t)\u0001\u0005\u0003\"Ei\u0014\u0004\u0003B\u0011&wn\u0004\"\u0001\u000b?\u0005\u000b)B!\u0019A\u0016\t\u000baC\u0001\u0019\u0001@\u0011\u0007i\u000b7\u0010\u000b\u0002~K\")q\u0007\u0003a\u0001q!)A\b\u0003a\u0001{!)\u0011\n\u0003a\u0001{\u0001")
public final class Balance {
    public static <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(Class<T> clazz, int outputCount, boolean waitForAllDownstreams, boolean eagerCancel) {
        return Balance$.MODULE$.create(clazz, outputCount, waitForAllDownstreams, eagerCancel);
    }

    public static <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(Class<T> clazz, int outputCount, boolean waitForAllDownstreams) {
        return Balance$.MODULE$.create(clazz, outputCount, waitForAllDownstreams);
    }

    public static <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(Class<T> clazz, int outputCount) {
        return Balance$.MODULE$.create(clazz, outputCount);
    }

    public static <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(int outputCount) {
        return Balance$.MODULE$.create(outputCount);
    }

    public static <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(int outputCount, boolean waitForAllDownstreams, boolean eagerCancel) {
        return Balance$.MODULE$.create(outputCount, waitForAllDownstreams, eagerCancel);
    }

    public static <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(int outputCount, boolean waitForAllDownstreams) {
        return Balance$.MODULE$.create(outputCount, waitForAllDownstreams);
    }
}

