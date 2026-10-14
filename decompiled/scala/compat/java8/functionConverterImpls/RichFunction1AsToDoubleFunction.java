/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToDoubleFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunction1AsToDoubleFunction$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!I\u0001\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006c\u0001!\tA\r\u0005\u0006o\u0001!\t\u0001\u000f\u0005\b\u000f\u0002\t\t\u0011\"\u0011I\u0011\u001da\u0005!!A\u0005B5;qa\u0015\b\u0002\u0002#\u0005AKB\u0004\u000e\u001d\u0005\u0005\t\u0012A+\t\u000bEBA\u0011A-\t\u000biCAQA.\t\u000f\u0011D\u0011\u0011!C\u0003K\"91\u000eCA\u0001\n\u000ba'a\b*jG\"4UO\\2uS>t\u0017'Q:U_\u0012{WO\u00197f\rVt7\r^5p]*\u0011q\u0002E\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0011CE\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003'Q\taaY8na\u0006$(\"A\u000b\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011\u0001\u0004J\n\u0003\u0001e\u0001\"AG\u000e\u000e\u0003QI!\u0001\b\u000b\u0003\r\u0005s\u0017PV1m\u0003U\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195Gk:\u001cG/[8oc\u0005\u001bHk\u001c#pk\ndWMR;oGRLwN\u001c\u0013%k:$WM\u001d7zS:<W#A\u0010\u0011\ti\u0001#%L\u0005\u0003CQ\u0011\u0011BR;oGRLwN\\\u0019\u0011\u0005\r\"C\u0002\u0001\u0003\u0006K\u0001\u0011\rA\n\u0002\u0002)F\u0011qE\u000b\t\u00035!J!!\u000b\u000b\u0003\u000f9{G\u000f[5oOB\u0011!dK\u0005\u0003YQ\u00111!\u00118z!\tQb&\u0003\u00020)\t1Ai\\;cY\u0016\fak]2bY\u0006$3m\\7qCR$#.\u0019<bq\u00112WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001cHEU5dQ\u001a+hn\u0019;j_:\f\u0014i\u001d+p\t>,(\r\\3Gk:\u001cG/[8oI\u0011*h\u000eZ3sYfLgn\u001a\u0011\u0002\rqJg.\u001b;?)\t\u0019T\u0007E\u00025\u0001\tj\u0011A\u0004\u0005\u0006m\r\u0001\raH\u0001\u000bk:$WM\u001d7zS:<\u0017AB1t\u0015\u00064\u0018-F\u0001:!\rQ\u0014II\u0007\u0002w)\u0011A(P\u0001\tMVt7\r^5p]*\u0011ahP\u0001\u0005kRLGNC\u0001A\u0003\u0011Q\u0017M^1\n\u0005\t[$\u0001\u0005+p\t>,(\r\\3Gk:\u001cG/[8oQ\t!A\t\u0005\u0002\u001b\u000b&\u0011a\t\u0006\u0002\u0007S:d\u0017N\\3\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012!\u0013\t\u00035)K!a\u0013\u000b\u0003\u0007%sG/\u0001\u0004fcV\fGn\u001d\u000b\u0003\u001dF\u0003\"AG(\n\u0005A#\"a\u0002\"p_2,\u0017M\u001c\u0005\b%\u001a\t\t\u00111\u0001+\u0003\rAH%M\u0001 %&\u001c\u0007NR;oGRLwN\\\u0019BgR{Gi\\;cY\u00164UO\\2uS>t\u0007C\u0001\u001b\t'\tAa\u000b\u0005\u0002\u001b/&\u0011\u0001\f\u0006\u0002\u0007\u0003:L(+\u001a4\u0015\u0003Q\u000b\u0001#Y:KCZ\fG%\u001a=uK:\u001c\u0018n\u001c8\u0016\u0005q{FCA/a!\rQ\u0014I\u0018\t\u0003G}#Q!\n\u0006C\u0002\u0019BQ!\u0019\u0006A\u0002\t\fQ\u0001\n;iSN\u00042\u0001\u000e\u0001_Q\tQA)\u0001\niCND7i\u001c3fI\u0015DH/\u001a8tS>tWC\u00014k)\tAu\rC\u0003b\u0017\u0001\u0007\u0001\u000eE\u00025\u0001%\u0004\"a\t6\u0005\u000b\u0015Z!\u0019\u0001\u0014\u0002!\u0015\fX/\u00197tI\u0015DH/\u001a8tS>tWCA7t)\tq\u0007\u000f\u0006\u0002O_\"9!\u000bDA\u0001\u0002\u0004Q\u0003\"B1\r\u0001\u0004\t\bc\u0001\u001b\u0001eB\u00111e\u001d\u0003\u0006K1\u0011\rA\n")
public final class RichFunction1AsToDoubleFunction<T> {
    private final Function1<T, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsToDoubleFunction$$underlying;

    public static <T> boolean equals$extension(Function1<T, Object> function1, Object object) {
        return RichFunction1AsToDoubleFunction$.MODULE$.equals$extension(function1, object);
    }

    public static <T> int hashCode$extension(Function1<T, Object> function1) {
        return RichFunction1AsToDoubleFunction$.MODULE$.hashCode$extension(function1);
    }

    public static <T> ToDoubleFunction<T> asJava$extension(Function1<T, Object> function1) {
        return RichFunction1AsToDoubleFunction$.MODULE$.asJava$extension(function1);
    }

    public Function1<T, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsToDoubleFunction$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction1AsToDoubleFunction$$underlying;
    }

    public ToDoubleFunction<T> asJava() {
        return RichFunction1AsToDoubleFunction$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsToDoubleFunction$$underlying());
    }

    public int hashCode() {
        return RichFunction1AsToDoubleFunction$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsToDoubleFunction$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction1AsToDoubleFunction$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsToDoubleFunction$$underlying(), x$1);
    }

    public RichFunction1AsToDoubleFunction(Function1<T, Object> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction1AsToDoubleFunction$$underlying = underlying;
    }
}

