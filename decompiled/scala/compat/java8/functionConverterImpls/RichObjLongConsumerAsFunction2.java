/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ObjLongConsumer;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichObjLongConsumerAsFunction2$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005]4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!IA\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006k\u0001!\tA\u000e\u0005\u0006w\u0001!\t\u0001\u0010\u0005\b\u0015\u0002\t\t\u0011\"\u0011L\u0011\u001dy\u0005!!A\u0005BA;qA\u0016\b\u0002\u0002#\u0005qKB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001-\t\u000bUBA\u0011\u0001/\t\u000buCAQ\u00010\t\u000f\u001dD\u0011\u0011!C\u0003Q\"9a\u000eCA\u0001\n\u000by'A\b*jG\"|%M\u001b'p]\u001e\u001cuN\\:v[\u0016\u0014\u0018i\u001d$v]\u000e$\u0018n\u001c83\u0015\ty\u0001#\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\t\t\"#A\u0003kCZ\f\u0007H\u0003\u0002\u0014)\u000511m\\7qCRT\u0011!F\u0001\u0006g\u000e\fG.Y\u0002\u0001+\tA2f\u0005\u0002\u00013A\u0011!dG\u0007\u0002)%\u0011A\u0004\u0006\u0002\u0007\u0003:Lh+\u00197\u0002)N\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r[(cU2{gnZ\"p]N,X.\u001a:Bg\u001a+hn\u0019;j_:\u0014D\u0005J;oI\u0016\u0014H._5oOV\tq\u0004E\u0002!O%j\u0011!\t\u0006\u0003E\r\n\u0001BZ;oGRLwN\u001c\u0006\u0003I\u0015\nA!\u001e;jY*\ta%\u0001\u0003kCZ\f\u0017B\u0001\u0015\"\u0005=y%M\u001b'p]\u001e\u001cuN\\:v[\u0016\u0014\bC\u0001\u0016,\u0019\u0001!Q\u0001\f\u0001C\u00025\u0012\u0011\u0001V\t\u0003]E\u0002\"AG\u0018\n\u0005A\"\"a\u0002(pi\"Lgn\u001a\t\u00035IJ!a\r\u000b\u0003\u0007\u0005s\u00170A+tG\u0006d\u0017\rJ2p[B\fG\u000f\n6bm\u0006DDEZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0013SS\u000eDwJ\u00196M_:<7i\u001c8tk6,'/Q:Gk:\u001cG/[8oe\u0011\"SO\u001c3fe2L\u0018N\\4!\u0003\u0019a\u0014N\\5u}Q\u0011q'\u000f\t\u0004q\u0001IS\"\u0001\b\t\u000bi\u001a\u0001\u0019A\u0010\u0002\u0015UtG-\u001a:ms&tw-A\u0004bgN\u001b\u0017\r\\1\u0016\u0003u\u0002RA\u0007 *\u0001\u000eK!a\u0010\u000b\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004C\u0001\u000eB\u0013\t\u0011EC\u0001\u0003M_:<\u0007C\u0001\u000eE\u0013\t)EC\u0001\u0003V]&$\bF\u0001\u0003H!\tQ\u0002*\u0003\u0002J)\t1\u0011N\u001c7j]\u0016\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002\u0019B\u0011!$T\u0005\u0003\u001dR\u00111!\u00138u\u0003\u0019)\u0017/^1mgR\u0011\u0011\u000b\u0016\t\u00035IK!a\u0015\u000b\u0003\u000f\t{w\u000e\\3b]\"9QKBA\u0001\u0002\u0004\t\u0014a\u0001=%c\u0005q\"+[2i\u001f\nTGj\u001c8h\u0007>t7/^7fe\u0006\u001bh)\u001e8di&|gN\r\t\u0003q!\u0019\"\u0001C-\u0011\u0005iQ\u0016BA.\u0015\u0005\u0019\te.\u001f*fMR\tq+A\tbgN\u001b\u0017\r\\1%Kb$XM\\:j_:,\"a\u00182\u0015\u0005\u0001\u001c\u0007#\u0002\u000e?C\u0002\u001b\u0005C\u0001\u0016c\t\u0015a#B1\u0001.\u0011\u0015!'\u00021\u0001f\u0003\u0015!C\u000f[5t!\rA\u0004!\u0019\u0015\u0003\u0015\u001d\u000b!\u0003[1tQ\u000e{G-\u001a\u0013fqR,gn]5p]V\u0011\u0011.\u001c\u000b\u0003\u0017*DQ\u0001Z\u0006A\u0002-\u00042\u0001\u000f\u0001m!\tQS\u000eB\u0003-\u0017\t\u0007Q&\u0001\tfcV\fGn\u001d\u0013fqR,gn]5p]V\u0011\u0001O\u001e\u000b\u0003cN$\"!\u0015:\t\u000fUc\u0011\u0011!a\u0001c!)A\r\u0004a\u0001iB\u0019\u0001\bA;\u0011\u0005)2H!\u0002\u0017\r\u0005\u0004i\u0003")
public final class RichObjLongConsumerAsFunction2<T> {
    private final ObjLongConsumer<T> scala$compat$java8$functionConverterImpls$RichObjLongConsumerAsFunction2$$underlying;

    public static <T> boolean equals$extension(ObjLongConsumer<T> objLongConsumer, Object object) {
        return RichObjLongConsumerAsFunction2$.MODULE$.equals$extension(objLongConsumer, object);
    }

    public static <T> int hashCode$extension(ObjLongConsumer<T> objLongConsumer) {
        return RichObjLongConsumerAsFunction2$.MODULE$.hashCode$extension(objLongConsumer);
    }

    public static <T> Function2<T, Object, BoxedUnit> asScala$extension(ObjLongConsumer<T> objLongConsumer) {
        return RichObjLongConsumerAsFunction2$.MODULE$.asScala$extension(objLongConsumer);
    }

    public ObjLongConsumer<T> scala$compat$java8$functionConverterImpls$RichObjLongConsumerAsFunction2$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichObjLongConsumerAsFunction2$$underlying;
    }

    public Function2<T, Object, BoxedUnit> asScala() {
        return RichObjLongConsumerAsFunction2$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichObjLongConsumerAsFunction2$$underlying());
    }

    public int hashCode() {
        return RichObjLongConsumerAsFunction2$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichObjLongConsumerAsFunction2$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichObjLongConsumerAsFunction2$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichObjLongConsumerAsFunction2$$underlying(), x$1);
    }

    public RichObjLongConsumerAsFunction2(ObjLongConsumer<T> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichObjLongConsumerAsFunction2$$underlying = underlying;
    }
}

