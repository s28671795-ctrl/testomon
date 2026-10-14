/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Consumer;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunction1AsConsumer$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005Q4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!I\u0001\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006c\u0001!\tA\r\u0005\u0006o\u0001!\t\u0001\u000f\u0005\b\u000f\u0002\t\t\u0011\"\u0011I\u0011\u001da\u0005!!A\u0005B5;qa\u0015\b\u0002\u0002#\u0005AKB\u0004\u000e\u001d\u0005\u0005\t\u0012A+\t\u000bEBA\u0011A-\t\u000biCAQA.\t\u000f\u0011D\u0011\u0011!C\u0003K\"91\u000eCA\u0001\n\u000ba'a\u0006*jG\"4UO\\2uS>t\u0017'Q:D_:\u001cX/\\3s\u0015\ty\u0001#\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\t\t\"#A\u0003kCZ\f\u0007H\u0003\u0002\u0014)\u000511m\\7qCRT\u0011!F\u0001\u0006g\u000e\fG.Y\u0002\u0001+\tABe\u0005\u0002\u00013A\u0011!dG\u0007\u0002)%\u0011A\u0004\u0006\u0002\u0007\u0003:Lh+\u00197\u0002\u001bN\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b$v]\u000e$\u0018n\u001c82\u0003N\u001cuN\\:v[\u0016\u0014H\u0005J;oI\u0016\u0014H._5oOV\tq\u0004\u0005\u0003\u001bA\tj\u0013BA\u0011\u0015\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0002$I1\u0001A!B\u0013\u0001\u0005\u00041#!\u0001+\u0012\u0005\u001dR\u0003C\u0001\u000e)\u0013\tICCA\u0004O_RD\u0017N\\4\u0011\u0005iY\u0013B\u0001\u0017\u0015\u0005\r\te.\u001f\t\u000359J!a\f\u000b\u0003\tUs\u0017\u000e^\u0001Og\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007NR;oGRLwN\\\u0019Bg\u000e{gn];nKJ$C%\u001e8eKJd\u00170\u001b8hA\u00051A(\u001b8jiz\"\"aM\u001b\u0011\u0007Q\u0002!%D\u0001\u000f\u0011\u001514\u00011\u0001 \u0003))h\u000eZ3sYfLgnZ\u0001\u0007CNT\u0015M^1\u0016\u0003e\u00022AO!#\u001b\u0005Y$B\u0001\u001f>\u0003!1WO\\2uS>t'B\u0001 @\u0003\u0011)H/\u001b7\u000b\u0003\u0001\u000bAA[1wC&\u0011!i\u000f\u0002\t\u0007>t7/^7fe\"\u0012A\u0001\u0012\t\u00035\u0015K!A\u0012\u000b\u0003\r%tG.\u001b8f\u0003!A\u0017m\u001d5D_\u0012,G#A%\u0011\u0005iQ\u0015BA&\u0015\u0005\rIe\u000e^\u0001\u0007KF,\u0018\r\\:\u0015\u00059\u000b\u0006C\u0001\u000eP\u0013\t\u0001FCA\u0004C_>dW-\u00198\t\u000fI3\u0011\u0011!a\u0001U\u0005\u0019\u0001\u0010J\u0019\u0002/IK7\r\u001b$v]\u000e$\u0018n\u001c82\u0003N\u001cuN\\:v[\u0016\u0014\bC\u0001\u001b\t'\tAa\u000b\u0005\u0002\u001b/&\u0011\u0001\f\u0006\u0002\u0007\u0003:L(+\u001a4\u0015\u0003Q\u000b\u0001#Y:KCZ\fG%\u001a=uK:\u001c\u0018n\u001c8\u0016\u0005q{FCA/a!\rQ\u0014I\u0018\t\u0003G}#Q!\n\u0006C\u0002\u0019BQ!\u0019\u0006A\u0002\t\fQ\u0001\n;iSN\u00042\u0001\u000e\u0001_Q\tQA)\u0001\niCND7i\u001c3fI\u0015DH/\u001a8tS>tWC\u00014k)\tAu\rC\u0003b\u0017\u0001\u0007\u0001\u000eE\u00025\u0001%\u0004\"a\t6\u0005\u000b\u0015Z!\u0019\u0001\u0014\u0002!\u0015\fX/\u00197tI\u0015DH/\u001a8tS>tWCA7t)\tq\u0007\u000f\u0006\u0002O_\"9!\u000bDA\u0001\u0002\u0004Q\u0003\"B1\r\u0001\u0004\t\bc\u0001\u001b\u0001eB\u00111e\u001d\u0003\u0006K1\u0011\rA\n")
public final class RichFunction1AsConsumer<T> {
    private final Function1<T, BoxedUnit> scala$compat$java8$functionConverterImpls$RichFunction1AsConsumer$$underlying;

    public static <T> boolean equals$extension(Function1<T, BoxedUnit> function1, Object object) {
        return RichFunction1AsConsumer$.MODULE$.equals$extension(function1, object);
    }

    public static <T> int hashCode$extension(Function1<T, BoxedUnit> function1) {
        return RichFunction1AsConsumer$.MODULE$.hashCode$extension(function1);
    }

    public static <T> Consumer<T> asJava$extension(Function1<T, BoxedUnit> function1) {
        return RichFunction1AsConsumer$.MODULE$.asJava$extension(function1);
    }

    public Function1<T, BoxedUnit> scala$compat$java8$functionConverterImpls$RichFunction1AsConsumer$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction1AsConsumer$$underlying;
    }

    public Consumer<T> asJava() {
        return RichFunction1AsConsumer$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsConsumer$$underlying());
    }

    public int hashCode() {
        return RichFunction1AsConsumer$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsConsumer$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction1AsConsumer$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsConsumer$$underlying(), x$1);
    }

    public RichFunction1AsConsumer(Function1<T, BoxedUnit> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction1AsConsumer$$underlying = underlying;
    }
}

