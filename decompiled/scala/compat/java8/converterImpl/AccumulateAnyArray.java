/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.converterImpl;

import scala.compat.java8.converterImpl.AccumulateAnyArray$;
import scala.jdk.AnyAccumulator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!IQ\u0006\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006]\u0001!\ta\f\u0005\u0006i\u0001!\t!\u000e\u0005\b\u0015\u0002\t\t\u0011\"\u0011L\u0011\u001dy\u0005!!A\u0005BA;qA\u0016\b\u0002\u0002#\u0005qKB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001-\t\u000b9BA\u0011\u0001/\t\u000buCAQ\u00010\t\u000f\u0019D\u0011\u0011!C\u0003O\"9Q\u000eCA\u0001\n\u000bq'AE!dGVlW\u000f\\1uK\u0006s\u00170\u0011:sCfT!a\u0004\t\u0002\u001b\r|gN^3si\u0016\u0014\u0018*\u001c9m\u0015\t\t\"#A\u0003kCZ\f\u0007H\u0003\u0002\u0014)\u000511m\\7qCRT\u0011!F\u0001\u0006g\u000e\fG.Y\u0002\u0001+\tABe\u0005\u0002\u00013A\u0011!dG\u0007\u0002)%\u0011A\u0004\u0006\u0002\u0007\u0003:Lh+\u00197\u0002\u007fM\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013d_:4XM\u001d;fe&k\u0007\u000f\u001c\u0013BG\u000e,X.\u001e7bi\u0016\fe._!se\u0006LH\u0005J;oI\u0016\u0014H._5oOV\tq\u0004E\u0002\u001bA\tJ!!\t\u000b\u0003\u000b\u0005\u0013(/Y=\u0011\u0005\r\"C\u0002\u0001\u0003\u0006K\u0001\u0011\rA\n\u0002\u0002\u0003F\u0011qE\u000b\t\u00035!J!!\u000b\u000b\u0003\u000f9{G\u000f[5oOB\u0011!dK\u0005\u0003YQ\u00111!\u00118z\u0003\u0001\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%G>tg/\u001a:uKJLU\u000e\u001d7%\u0003\u000e\u001cW/\\;mCR,\u0017I\\=BeJ\f\u0017\u0010\n\u0013v]\u0012,'\u000f\\=j]\u001e\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u00021eA\u0019\u0011\u0007\u0001\u0012\u000e\u00039AQaM\u0002A\u0002}\t!\"\u001e8eKJd\u00170\u001b8h\u0003)\t7mY;nk2\fG/Z\u000b\u0002mA\u0019qg\u0012\u0012\u000f\u0005a\"eBA\u001dC\u001d\tQ\u0014I\u0004\u0002<\u0001:\u0011AhP\u0007\u0002{)\u0011aHF\u0001\u0007yI|w\u000e\u001e \n\u0003UI!a\u0005\u000b\n\u0005E\u0011\u0012BA\"\u0011\u00039\u0019w\u000e\u001c7fGRLwN\\%na2L!!\u0012$\u0002\u000fA\f7m[1hK*\u00111\tE\u0005\u0003\u0011&\u00131\"Q2dk6,H.\u0019;pe*\u0011QIR\u0001\tQ\u0006\u001c\bnQ8eKR\tA\n\u0005\u0002\u001b\u001b&\u0011a\n\u0006\u0002\u0004\u0013:$\u0018AB3rk\u0006d7\u000f\u0006\u0002R)B\u0011!DU\u0005\u0003'R\u0011qAQ8pY\u0016\fg\u000eC\u0004V\r\u0005\u0005\t\u0019\u0001\u0016\u0002\u0007a$\u0013'\u0001\nBG\u000e,X.\u001e7bi\u0016\fe._!se\u0006L\bCA\u0019\t'\tA\u0011\f\u0005\u0002\u001b5&\u00111\f\u0006\u0002\u0007\u0003:L(+\u001a4\u0015\u0003]\u000bA#Y2dk6,H.\u0019;fI\u0015DH/\u001a8tS>tWCA0c)\t\u00017\rE\u00028\u000f\u0006\u0004\"a\t2\u0005\u000b\u0015R!\u0019\u0001\u0014\t\u000b\u0011T\u0001\u0019A3\u0002\u000b\u0011\"\b.[:\u0011\u0007E\u0002\u0011-\u0001\niCND7i\u001c3fI\u0015DH/\u001a8tS>tWC\u00015m)\tY\u0015\u000eC\u0003e\u0017\u0001\u0007!\u000eE\u00022\u0001-\u0004\"a\t7\u0005\u000b\u0015Z!\u0019\u0001\u0014\u0002!\u0015\fX/\u00197tI\u0015DH/\u001a8tS>tWCA8v)\t\u0001(\u000f\u0006\u0002Rc\"9Q\u000bDA\u0001\u0002\u0004Q\u0003\"\u00023\r\u0001\u0004\u0019\bcA\u0019\u0001iB\u00111%\u001e\u0003\u0006K1\u0011\rA\n")
public final class AccumulateAnyArray<A> {
    private final Object scala$compat$java8$converterImpl$AccumulateAnyArray$$underlying;

    public static <A> boolean equals$extension(Object object, Object object2) {
        return AccumulateAnyArray$.MODULE$.equals$extension(object, object2);
    }

    public static <A> int hashCode$extension(Object object) {
        return AccumulateAnyArray$.MODULE$.hashCode$extension(object);
    }

    public static <A> AnyAccumulator<A> accumulate$extension(Object object) {
        return AccumulateAnyArray$.MODULE$.accumulate$extension(object);
    }

    public Object scala$compat$java8$converterImpl$AccumulateAnyArray$$underlying() {
        return this.scala$compat$java8$converterImpl$AccumulateAnyArray$$underlying;
    }

    public AnyAccumulator<A> accumulate() {
        return AccumulateAnyArray$.MODULE$.accumulate$extension(this.scala$compat$java8$converterImpl$AccumulateAnyArray$$underlying());
    }

    public int hashCode() {
        return AccumulateAnyArray$.MODULE$.hashCode$extension(this.scala$compat$java8$converterImpl$AccumulateAnyArray$$underlying());
    }

    public boolean equals(Object x$1) {
        return AccumulateAnyArray$.MODULE$.equals$extension(this.scala$compat$java8$converterImpl$AccumulateAnyArray$$underlying(), x$1);
    }

    public AccumulateAnyArray(Object underlying) {
        this.scala$compat$java8$converterImpl$AccumulateAnyArray$$underlying = underlying;
    }
}

