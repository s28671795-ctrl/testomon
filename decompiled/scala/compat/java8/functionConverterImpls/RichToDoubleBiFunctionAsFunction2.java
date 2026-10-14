/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToDoubleBiFunction;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichToDoubleBiFunctionAsFunction2$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!Iq\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006q\u0001!\t!\u000f\u0005\u0006}\u0001!\ta\u0010\u0005\b\u0015\u0002\t\t\u0011\"\u0011L\u0011\u001dy\u0005!!A\u0005BA;qA\u0016\b\u0002\u0002#\u0005qKB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001-\t\u000baBA\u0011\u0001/\t\u000buCAQ\u00010\t\u000f%D\u0011\u0011!C\u0003U\"9!\u000fCA\u0001\n\u000b\u0019(!\t*jG\"$v\u000eR8vE2,')\u001b$v]\u000e$\u0018n\u001c8Bg\u001a+hn\u0019;j_:\u0014$BA\b\u0011\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(BA\t\u0013\u0003\u0015Q\u0017M^19\u0015\t\u0019B#\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002+\u0005)1oY1mC\u000e\u0001Qc\u0001\r,kM\u0011\u0001!\u0007\t\u00035mi\u0011\u0001F\u0005\u00039Q\u0011a!\u00118z-\u0006d\u0017aV:dC2\fGeY8na\u0006$HE[1wCb\"c-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7\u000f\n*jG\"$v\u000eR8vE2,')\u001b$v]\u000e$\u0018n\u001c8Bg\u001a+hn\u0019;j_:\u0014D\u0005J;oI\u0016\u0014H._5oOV\tq\u0004\u0005\u0003!O%\"T\"A\u0011\u000b\u0005\t\u001a\u0013\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005\u0011*\u0013\u0001B;uS2T\u0011AJ\u0001\u0005U\u00064\u0018-\u0003\u0002)C\t\u0011Bk\u001c#pk\ndWMQ5Gk:\u001cG/[8o!\tQ3\u0006\u0004\u0001\u0005\u000b1\u0002!\u0019A\u0017\u0003\u0003Q\u000b\"AL\u0019\u0011\u0005iy\u0013B\u0001\u0019\u0015\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u0007\u001a\n\u0005M\"\"aA!osB\u0011!&\u000e\u0003\u0006m\u0001\u0011\r!\f\u0002\u0002+\u0006A6oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYN$#+[2i)>$u.\u001e2mK\nKg)\u001e8di&|g.Q:Gk:\u001cG/[8oe\u0011\"SO\u001c3fe2L\u0018N\\4!\u0003\u0019a\u0014N\\5u}Q\u0011!\b\u0010\t\u0005w\u0001IC'D\u0001\u000f\u0011\u0015i4\u00011\u0001 \u0003))h\u000eZ3sYfLgnZ\u0001\bCN\u001c6-\u00197b+\u0005\u0001\u0005#\u0002\u000eBSQ\u001a\u0015B\u0001\"\u0015\u0005%1UO\\2uS>t'\u0007\u0005\u0002\u001b\t&\u0011Q\t\u0006\u0002\u0007\t>,(\r\\3)\u0005\u00119\u0005C\u0001\u000eI\u0013\tIEC\u0001\u0004j]2Lg.Z\u0001\tQ\u0006\u001c\bnQ8eKR\tA\n\u0005\u0002\u001b\u001b&\u0011a\n\u0006\u0002\u0004\u0013:$\u0018AB3rk\u0006d7\u000f\u0006\u0002R)B\u0011!DU\u0005\u0003'R\u0011qAQ8pY\u0016\fg\u000eC\u0004V\r\u0005\u0005\t\u0019A\u0019\u0002\u0007a$\u0013'A\u0011SS\u000eDGk\u001c#pk\ndWMQ5Gk:\u001cG/[8o\u0003N4UO\\2uS>t'\u0007\u0005\u0002<\u0011M\u0011\u0001\"\u0017\t\u00035iK!a\u0017\u000b\u0003\r\u0005s\u0017PU3g)\u00059\u0016!E1t'\u000e\fG.\u0019\u0013fqR,gn]5p]V\u0019qL\u00193\u0015\u0005\u0001,\u0007#\u0002\u000eBC\u000e\u001c\u0005C\u0001\u0016c\t\u0015a#B1\u0001.!\tQC\rB\u00037\u0015\t\u0007Q\u0006C\u0003g\u0015\u0001\u0007q-A\u0003%i\"L7\u000f\u0005\u0003<\u0001\u0005\u001c\u0007F\u0001\u0006H\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0016\u0007-|\u0017\u000f\u0006\u0002LY\")am\u0003a\u0001[B!1\b\u00018q!\tQs\u000eB\u0003-\u0017\t\u0007Q\u0006\u0005\u0002+c\u0012)ag\u0003b\u0001[\u0005\u0001R-];bYN$S\r\u001f;f]NLwN\\\u000b\u0004ijdHCA;x)\t\tf\u000fC\u0004V\u0019\u0005\u0005\t\u0019A\u0019\t\u000b\u0019d\u0001\u0019\u0001=\u0011\tm\u0002\u0011p\u001f\t\u0003Ui$Q\u0001\f\u0007C\u00025\u0002\"A\u000b?\u0005\u000bYb!\u0019A\u0017")
public final class RichToDoubleBiFunctionAsFunction2<T, U> {
    private final ToDoubleBiFunction<T, U> scala$compat$java8$functionConverterImpls$RichToDoubleBiFunctionAsFunction2$$underlying;

    public static <T, U> boolean equals$extension(ToDoubleBiFunction<T, U> toDoubleBiFunction, Object object) {
        return RichToDoubleBiFunctionAsFunction2$.MODULE$.equals$extension(toDoubleBiFunction, object);
    }

    public static <T, U> int hashCode$extension(ToDoubleBiFunction<T, U> toDoubleBiFunction) {
        return RichToDoubleBiFunctionAsFunction2$.MODULE$.hashCode$extension(toDoubleBiFunction);
    }

    public static <T, U> Function2<T, U, Object> asScala$extension(ToDoubleBiFunction<T, U> toDoubleBiFunction) {
        return RichToDoubleBiFunctionAsFunction2$.MODULE$.asScala$extension(toDoubleBiFunction);
    }

    public ToDoubleBiFunction<T, U> scala$compat$java8$functionConverterImpls$RichToDoubleBiFunctionAsFunction2$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichToDoubleBiFunctionAsFunction2$$underlying;
    }

    public Function2<T, U, Object> asScala() {
        return RichToDoubleBiFunctionAsFunction2$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichToDoubleBiFunctionAsFunction2$$underlying());
    }

    public int hashCode() {
        return RichToDoubleBiFunctionAsFunction2$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichToDoubleBiFunctionAsFunction2$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichToDoubleBiFunctionAsFunction2$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichToDoubleBiFunctionAsFunction2$$underlying(), x$1);
    }

    public RichToDoubleBiFunctionAsFunction2(ToDoubleBiFunction<T, U> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichToDoubleBiFunctionAsFunction2$$underlying = underlying;
    }
}

