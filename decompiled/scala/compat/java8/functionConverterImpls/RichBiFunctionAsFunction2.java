/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BiFunction;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichBiFunctionAsFunction2$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001da\u0001B\u0007\u000f\u0005]AA\"\b\u0001\u0005\u0002\u0003\u0015)Q1A\u0005\nyA\u0011B\u000f\u0001\u0003\u0006\u0003\u0005\u000b\u0011B\u0010\t\u000bm\u0002A\u0011\u0001\u001f\t\u000b\u0005\u0003A\u0011\u0001\"\t\u000f)\u0003\u0011\u0011!C!\u0017\"9q\nAA\u0001\n\u0003\u0002va\u0002,\u000f\u0003\u0003E\ta\u0016\u0004\b\u001b9\t\t\u0011#\u0001Y\u0011\u0015Y\u0004\u0002\"\u0001]\u0011\u0015i\u0006\u0002\"\u0002_\u0011\u001dY\u0007\"!A\u0005\u00061DqA\u001e\u0005\u0002\u0002\u0013\u0015qOA\rSS\u000eD')\u001b$v]\u000e$\u0018n\u001c8Bg\u001a+hn\u0019;j_:\u0014$BA\b\u0011\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(BA\t\u0013\u0003\u0015Q\u0017M^19\u0015\t\u0019B#\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002+\u0005)1oY1mC\u000e\u0001Q\u0003\u0002\r,ka\u001a\"\u0001A\r\u0011\u0005iYR\"\u0001\u000b\n\u0005q!\"AB!osZ\u000bG.A(tG\u0006d\u0017\rJ2p[B\fG\u000f\n6bm\u0006DDEZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0013SS\u000eD')\u001b$v]\u000e$\u0018n\u001c8Bg\u001a+hn\u0019;j_:\u0014D\u0005J;oI\u0016\u0014H._5oOV\tq\u0004E\u0003!O%\"t'D\u0001\"\u0015\t\u00113%\u0001\u0005gk:\u001cG/[8o\u0015\t!S%\u0001\u0003vi&d'\"\u0001\u0014\u0002\t)\fg/Y\u0005\u0003Q\u0005\u0012!BQ5Gk:\u001cG/[8o!\tQ3\u0006\u0004\u0001\u0005\u000b1\u0002!\u0019A\u0017\u0003\u0003Q\u000b\"AL\u0019\u0011\u0005iy\u0013B\u0001\u0019\u0015\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u0007\u001a\n\u0005M\"\"aA!osB\u0011!&\u000e\u0003\u0006m\u0001\u0011\r!\f\u0002\u0002+B\u0011!\u0006\u000f\u0003\u0006s\u0001\u0011\r!\f\u0002\u0002%\u0006\u00016oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYN$#+[2i\u0005&4UO\\2uS>t\u0017i\u001d$v]\u000e$\u0018n\u001c83I\u0011*h\u000eZ3sYfLgn\u001a\u0011\u0002\rqJg.\u001b;?)\tit\bE\u0003?\u0001%\"t'D\u0001\u000f\u0011\u0015\u00015\u00011\u0001 \u0003))h\u000eZ3sYfLgnZ\u0001\bCN\u001c6-\u00197b+\u0005\u0019\u0005#\u0002\u000eESQ:\u0014BA#\u0015\u0005%1UO\\2uS>t'\u0007\u000b\u0002\u0005\u000fB\u0011!\u0004S\u0005\u0003\u0013R\u0011a!\u001b8mS:,\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u00031\u0003\"AG'\n\u00059#\"aA%oi\u00061Q-];bYN$\"!\u0015+\u0011\u0005i\u0011\u0016BA*\u0015\u0005\u001d\u0011un\u001c7fC:Dq!\u0016\u0004\u0002\u0002\u0003\u0007\u0011'A\u0002yIE\n\u0011DU5dQ\nKg)\u001e8di&|g.Q:Gk:\u001cG/[8oeA\u0011a\bC\n\u0003\u0011e\u0003\"A\u0007.\n\u0005m#\"AB!osJ+g\rF\u0001X\u0003E\t7oU2bY\u0006$S\r\u001f;f]NLwN\\\u000b\u0005?\n$g\r\u0006\u0002aOB)!\u0004R1dKB\u0011!F\u0019\u0003\u0006Y)\u0011\r!\f\t\u0003U\u0011$QA\u000e\u0006C\u00025\u0002\"A\u000b4\u0005\u000beR!\u0019A\u0017\t\u000b!T\u0001\u0019A5\u0002\u000b\u0011\"\b.[:\u0011\u000by\u0002\u0011mY3)\u0005)9\u0015A\u00055bg\"\u001cu\u000eZ3%Kb$XM\\:j_:,B!\\9tkR\u00111J\u001c\u0005\u0006Q.\u0001\ra\u001c\t\u0006}\u0001\u0001(\u000f\u001e\t\u0003UE$Q\u0001L\u0006C\u00025\u0002\"AK:\u0005\u000bYZ!\u0019A\u0017\u0011\u0005)*H!B\u001d\f\u0005\u0004i\u0013\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o+\u0019Ah0!\u0001\u0002\u0006Q\u0011\u0011p\u001f\u000b\u0003#jDq!\u0016\u0007\u0002\u0002\u0003\u0007\u0011\u0007C\u0003i\u0019\u0001\u0007A\u0010\u0005\u0004?\u0001u|\u00181\u0001\t\u0003Uy$Q\u0001\f\u0007C\u00025\u00022AKA\u0001\t\u00151DB1\u0001.!\rQ\u0013Q\u0001\u0003\u0006s1\u0011\r!\f")
public final class RichBiFunctionAsFunction2<T, U, R> {
    private final BiFunction<T, U, R> scala$compat$java8$functionConverterImpls$RichBiFunctionAsFunction2$$underlying;

    public static <T, U, R> boolean equals$extension(BiFunction<T, U, R> biFunction, Object object) {
        return RichBiFunctionAsFunction2$.MODULE$.equals$extension(biFunction, object);
    }

    public static <T, U, R> int hashCode$extension(BiFunction<T, U, R> biFunction) {
        return RichBiFunctionAsFunction2$.MODULE$.hashCode$extension(biFunction);
    }

    public static <T, U, R> Function2<T, U, R> asScala$extension(BiFunction<T, U, R> biFunction) {
        return RichBiFunctionAsFunction2$.MODULE$.asScala$extension(biFunction);
    }

    public BiFunction<T, U, R> scala$compat$java8$functionConverterImpls$RichBiFunctionAsFunction2$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichBiFunctionAsFunction2$$underlying;
    }

    public Function2<T, U, R> asScala() {
        return RichBiFunctionAsFunction2$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichBiFunctionAsFunction2$$underlying());
    }

    public int hashCode() {
        return RichBiFunctionAsFunction2$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichBiFunctionAsFunction2$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichBiFunctionAsFunction2$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichBiFunctionAsFunction2$$underlying(), x$1);
    }

    public RichBiFunctionAsFunction2(BiFunction<T, U, R> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichBiFunctionAsFunction2$$underlying = underlying;
    }
}

