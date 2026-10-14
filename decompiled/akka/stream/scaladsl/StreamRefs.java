/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.SinkRef;
import akka.stream.SourceRef;
import akka.stream.scaladsl.Sink;
import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.StreamRefs$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e:Q!\u0002\u0004\t\u000251Qa\u0004\u0004\t\u0002AAQaF\u0001\u0005\u0002aAQ!G\u0001\u0005\u0002iAQAL\u0001\u0005\u0002=\n!b\u0015;sK\u0006l'+\u001a4t\u0015\t9\u0001\"\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\tI!\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u0017\u0005!\u0011m[6b\u0007\u0001\u0001\"AD\u0001\u000e\u0003\u0019\u0011!b\u0015;sK\u0006l'+\u001a4t'\t\t\u0011\u0003\u0005\u0002\u0013+5\t1CC\u0001\u0015\u0003\u0015\u00198-\u00197b\u0013\t12C\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u00035\t\u0011b]8ve\u000e,'+\u001a4\u0016\u0005m\tC#\u0001\u000f\u0011\t9irDK\u0005\u0003=\u0019\u0011AaU5oWB\u0011\u0001%\t\u0007\u0001\t\u0015\u00113A1\u0001$\u0005\u0005!\u0016C\u0001\u0013(!\t\u0011R%\u0003\u0002''\t9aj\u001c;iS:<\u0007C\u0001\n)\u0013\tI3CA\u0002B]f\u00042a\u000b\u0017 \u001b\u0005A\u0011BA\u0017\t\u0005%\u0019v.\u001e:dKJ+g-A\u0004tS:\\'+\u001a4\u0016\u0005A*D#A\u0019\u0011\t9\u0011DGN\u0005\u0003g\u0019\u0011aaU8ve\u000e,\u0007C\u0001\u00116\t\u0015\u0011CA1\u0001$!\rYs\u0007N\u0005\u0003q!\u0011qaU5oWJ+g\r")
public final class StreamRefs {
    public static <T> Source<T, SinkRef<T>> sinkRef() {
        return StreamRefs$.MODULE$.sinkRef();
    }

    public static <T> Sink<T, SourceRef<T>> sourceRef() {
        return StreamRefs$.MODULE$.sourceRef();
    }
}

