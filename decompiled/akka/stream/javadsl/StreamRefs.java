/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.stream.SinkRef;
import akka.stream.SourceRef;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.stream.javadsl.StreamRefs$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e:Q!\u0002\u0004\t\u000251Qa\u0004\u0004\t\u0002AAQaF\u0001\u0005\u0002aAQ!G\u0001\u0005\u0002iAQAL\u0001\u0005\u0002=\n!b\u0015;sK\u0006l'+\u001a4t\u0015\t9\u0001\"A\u0004kCZ\fGm\u001d7\u000b\u0005%Q\u0011AB:ue\u0016\fWNC\u0001\f\u0003\u0011\t7n[1\u0004\u0001A\u0011a\"A\u0007\u0002\r\tQ1\u000b\u001e:fC6\u0014VMZ:\u0014\u0005\u0005\t\u0002C\u0001\n\u0016\u001b\u0005\u0019\"\"\u0001\u000b\u0002\u000bM\u001c\u0017\r\\1\n\u0005Y\u0019\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002\u001b\u0005I1o\\;sG\u0016\u0014VMZ\u000b\u00037\u0005\"\u0012\u0001\b\t\u0005\u001duy\"&\u0003\u0002\u001f\r\t!1+\u001b8l!\t\u0001\u0013\u0005\u0004\u0001\u0005\u000b\t\u001a!\u0019A\u0012\u0003\u0003Q\u000b\"\u0001J\u0014\u0011\u0005I)\u0013B\u0001\u0014\u0014\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u0005\u0015\n\u0005%\u001a\"aA!osB\u00191\u0006L\u0010\u000e\u0003!I!!\f\u0005\u0003\u0013M{WO]2f%\u00164\u0017aB:j].\u0014VMZ\u000b\u0003aU\"\u0012!\r\t\u0005\u001dI\"d'\u0003\u00024\r\t11k\\;sG\u0016\u0004\"\u0001I\u001b\u0005\u000b\t\"!\u0019A\u0012\u0011\u0007-:D'\u0003\u00029\u0011\t91+\u001b8l%\u00164\u0007")
public final class StreamRefs {
    public static <T> Source<T, SinkRef<T>> sinkRef() {
        return StreamRefs$.MODULE$.sinkRef();
    }

    public static <T> Sink<T, SourceRef<T>> sourceRef() {
        return StreamRefs$.MODULE$.sourceRef();
    }
}

