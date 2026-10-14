/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Consumer;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichConsumerAsFunction1$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005Q4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!IA\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006k\u0001!\tA\u000e\u0005\u0006w\u0001!\t\u0001\u0010\u0005\b\u000f\u0002\t\t\u0011\"\u0011I\u0011\u001da\u0005!!A\u0005B5;qa\u0015\b\u0002\u0002#\u0005AKB\u0004\u000e\u001d\u0005\u0005\t\u0012A+\t\u000bUBA\u0011A-\t\u000biCAQA.\t\u000f\u0011D\u0011\u0011!C\u0003K\"91\u000eCA\u0001\n\u000ba'a\u0006*jG\"\u001cuN\\:v[\u0016\u0014\u0018i\u001d$v]\u000e$\u0018n\u001c82\u0015\ty\u0001#\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\t\t\"#A\u0003kCZ\f\u0007H\u0003\u0002\u0014)\u000511m\\7qCRT\u0011!F\u0001\u0006g\u000e\fG.Y\u0002\u0001+\tA2f\u0005\u0002\u00013A\u0011!dG\u0007\u0002)%\u0011A\u0004\u0006\u0002\u0007\u0003:Lh+\u00197\u0002\u001bN\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r[\"p]N,X.\u001a:Bg\u001a+hn\u0019;j_:\fD\u0005J;oI\u0016\u0014H._5oOV\tq\u0004E\u0002!O%j\u0011!\t\u0006\u0003E\r\n\u0001BZ;oGRLwN\u001c\u0006\u0003I\u0015\nA!\u001e;jY*\ta%\u0001\u0003kCZ\f\u0017B\u0001\u0015\"\u0005!\u0019uN\\:v[\u0016\u0014\bC\u0001\u0016,\u0019\u0001!Q\u0001\f\u0001C\u00025\u0012\u0011\u0001V\t\u0003]E\u0002\"AG\u0018\n\u0005A\"\"a\u0002(pi\"Lgn\u001a\t\u00035IJ!a\r\u000b\u0003\u0007\u0005s\u00170\u0001(tG\u0006d\u0017\rJ2p[B\fG\u000f\n6bm\u0006DDEZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0013SS\u000eD7i\u001c8tk6,'/Q:Gk:\u001cG/[8oc\u0011\"SO\u001c3fe2L\u0018N\\4!\u0003\u0019a\u0014N\\5u}Q\u0011q'\u000f\t\u0004q\u0001IS\"\u0001\b\t\u000bi\u001a\u0001\u0019A\u0010\u0002\u0015UtG-\u001a:ms&tw-A\u0004bgN\u001b\u0017\r\\1\u0016\u0003u\u0002BA\u0007 *\u0001&\u0011q\b\u0006\u0002\n\rVt7\r^5p]F\u0002\"AG!\n\u0005\t#\"\u0001B+oSRD#\u0001\u0002#\u0011\u0005i)\u0015B\u0001$\u0015\u0005\u0019Ig\u000e\\5oK\u0006A\u0001.Y:i\u0007>$W\rF\u0001J!\tQ\"*\u0003\u0002L)\t\u0019\u0011J\u001c;\u0002\r\u0015\fX/\u00197t)\tq\u0015\u000b\u0005\u0002\u001b\u001f&\u0011\u0001\u000b\u0006\u0002\b\u0005>|G.Z1o\u0011\u001d\u0011f!!AA\u0002E\n1\u0001\u001f\u00132\u0003]\u0011\u0016n\u00195D_:\u001cX/\\3s\u0003N4UO\\2uS>t\u0017\u0007\u0005\u00029\u0011M\u0011\u0001B\u0016\t\u00035]K!\u0001\u0017\u000b\u0003\r\u0005s\u0017PU3g)\u0005!\u0016!E1t'\u000e\fG.\u0019\u0013fqR,gn]5p]V\u0011Al\u0018\u000b\u0003;\u0002\u0004BA\u0007 _\u0001B\u0011!f\u0018\u0003\u0006Y)\u0011\r!\f\u0005\u0006C*\u0001\rAY\u0001\u0006IQD\u0017n\u001d\t\u0004q\u0001q\u0006F\u0001\u0006E\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0016\u0005\u0019TGC\u0001%h\u0011\u0015\t7\u00021\u0001i!\rA\u0004!\u001b\t\u0003U)$Q\u0001L\u0006C\u00025\n\u0001#Z9vC2\u001cH%\u001a=uK:\u001c\u0018n\u001c8\u0016\u00055\u001cHC\u00018q)\tqu\u000eC\u0004S\u0019\u0005\u0005\t\u0019A\u0019\t\u000b\u0005d\u0001\u0019A9\u0011\u0007a\u0002!\u000f\u0005\u0002+g\u0012)A\u0006\u0004b\u0001[\u0001")
public final class RichConsumerAsFunction1<T> {
    private final Consumer<T> scala$compat$java8$functionConverterImpls$RichConsumerAsFunction1$$underlying;

    public static <T> boolean equals$extension(Consumer<T> consumer, Object object) {
        return RichConsumerAsFunction1$.MODULE$.equals$extension(consumer, object);
    }

    public static <T> int hashCode$extension(Consumer<T> consumer) {
        return RichConsumerAsFunction1$.MODULE$.hashCode$extension(consumer);
    }

    public static <T> Function1<T, BoxedUnit> asScala$extension(Consumer<T> consumer) {
        return RichConsumerAsFunction1$.MODULE$.asScala$extension(consumer);
    }

    public Consumer<T> scala$compat$java8$functionConverterImpls$RichConsumerAsFunction1$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichConsumerAsFunction1$$underlying;
    }

    public Function1<T, BoxedUnit> asScala() {
        return RichConsumerAsFunction1$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichConsumerAsFunction1$$underlying());
    }

    public int hashCode() {
        return RichConsumerAsFunction1$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichConsumerAsFunction1$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichConsumerAsFunction1$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichConsumerAsFunction1$$underlying(), x$1);
    }

    public RichConsumerAsFunction1(Consumer<T> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichConsumerAsFunction1$$underlying = underlying;
    }
}

