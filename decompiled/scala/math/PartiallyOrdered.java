/*
 * Decompiled with CFR 0.152.
 */
package scala.math;

import scala.Function1;
import scala.Option;
import scala.Some;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005%4q\u0001C\u0005\u0011\u0002\u0007\u0005a\u0002C\u0003\u0015\u0001\u0011\u0005Q#\u0002\u0003\u001a\u0001\u0001Q\u0002\"\u0002\u0015\u0001\r\u0003I\u0003\"\u0002 \u0001\t\u0003y\u0004\"B&\u0001\t\u0003a\u0005\"B+\u0001\t\u00031\u0006\"B0\u0001\t\u0003\u0001'\u0001\u0005)beRL\u0017\r\u001c7z\u001fJ$WM]3e\u0015\tQ1\"\u0001\u0003nCRD'\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011qBO\n\u0003\u0001A\u0001\"!\u0005\n\u000e\u0003-I!aE\u0006\u0003\u0007\u0005s\u00170\u0001\u0004%S:LG\u000f\n\u000b\u0002-A\u0011\u0011cF\u0005\u00031-\u0011A!\u00168ji\n\u0011\u0012i\u001d)beRL\u0017\r\u001c7z\u001fJ$WM]3e+\tY\u0002\u0005\u0005\u0003\u00129y1\u0013BA\u000f\f\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0002 A1\u0001A!B\u0011\u0003\u0005\u0004\u0011#!\u0001\"\u0012\u0005\r\u0002\u0002CA\t%\u0013\t)3BA\u0004O_RD\u0017N\\4\u0011\u0007\u001d\u0002a$D\u0001\n\u00031!(/_\"p[B\f'/\u001a+p+\tQs\u0007\u0006\u0002,yQ\u0011AF\r\t\u0004#5z\u0013B\u0001\u0018\f\u0005\u0019y\u0005\u000f^5p]B\u0011\u0011\u0003M\u0005\u0003c-\u00111!\u00138u\u0011\u001d\u00194!!AA\u0004Q\n!\"\u001a<jI\u0016t7-\u001a\u00132!\r)$AN\u0007\u0002\u0001A\u0011qd\u000e\u0003\u0006C\r\u0011\r\u0001O\t\u0003sA\u0001\"a\b\u001e\u0005\rm\u0002AQ1\u0001#\u0005\u0005\t\u0005\"B\u001f\u0004\u0001\u00041\u0014\u0001\u0002;iCR\fQ\u0001\n7fgN,\"\u0001Q%\u0015\u0005\u0005SEC\u0001\"F!\t\t2)\u0003\u0002E\u0017\t9!i\\8mK\u0006t\u0007b\u0002$\u0005\u0003\u0003\u0005\u001daR\u0001\u000bKZLG-\u001a8dK\u0012\u0012\u0004cA\u001b\u0003\u0011B\u0011q$\u0013\u0003\u0006C\u0011\u0011\r\u0001\u000f\u0005\u0006{\u0011\u0001\r\u0001S\u0001\tI\u001d\u0014X-\u0019;feV\u0011Qj\u0015\u000b\u0003\u001dR#\"AQ(\t\u000fA+\u0011\u0011!a\u0002#\u0006QQM^5eK:\u001cW\rJ\u001a\u0011\u0007U\u0012!\u000b\u0005\u0002 '\u0012)\u0011%\u0002b\u0001q!)Q(\u0002a\u0001%\u0006AA\u0005\\3tg\u0012*\u0017/\u0006\u0002X;R\u0011\u0001L\u0018\u000b\u0003\u0005fCqA\u0017\u0004\u0002\u0002\u0003\u000f1,\u0001\u0006fm&$WM\\2fIQ\u00022!\u000e\u0002]!\tyR\fB\u0003\"\r\t\u0007\u0001\bC\u0003>\r\u0001\u0007A,A\u0006%OJ,\u0017\r^3sI\u0015\fXCA1h)\t\u0011\u0007\u000e\u0006\u0002CG\"9AmBA\u0001\u0002\b)\u0017AC3wS\u0012,gnY3%kA\u0019QG\u00014\u0011\u0005}9G!B\u0011\b\u0005\u0004A\u0004\"B\u001f\b\u0001\u00041\u0007")
public interface PartiallyOrdered<A> {
    public <B> Option<Object> tryCompareTo(B var1, Function1<B, PartiallyOrdered<B>> var2);

    public static /* synthetic */ boolean $less$(PartiallyOrdered $this, Object that, Function1 evidence$2) {
        return $this.$less(that, evidence$2);
    }

    default public <B> boolean $less(B that, Function1<B, PartiallyOrdered<B>> evidence$2) {
        Option<Object> option = this.tryCompareTo(that, evidence$2);
        return option instanceof Some && BoxesRunTime.unboxToInt(((Some)option).value()) < 0;
    }

    public static /* synthetic */ boolean $greater$(PartiallyOrdered $this, Object that, Function1 evidence$3) {
        return $this.$greater(that, evidence$3);
    }

    default public <B> boolean $greater(B that, Function1<B, PartiallyOrdered<B>> evidence$3) {
        Option<Object> option = this.tryCompareTo(that, evidence$3);
        return option instanceof Some && BoxesRunTime.unboxToInt(((Some)option).value()) > 0;
    }

    public static /* synthetic */ boolean $less$eq$(PartiallyOrdered $this, Object that, Function1 evidence$4) {
        return $this.$less$eq(that, evidence$4);
    }

    default public <B> boolean $less$eq(B that, Function1<B, PartiallyOrdered<B>> evidence$4) {
        Option<Object> option = this.tryCompareTo(that, evidence$4);
        return option instanceof Some && BoxesRunTime.unboxToInt(((Some)option).value()) <= 0;
    }

    public static /* synthetic */ boolean $greater$eq$(PartiallyOrdered $this, Object that, Function1 evidence$5) {
        return $this.$greater$eq(that, evidence$5);
    }

    default public <B> boolean $greater$eq(B that, Function1<B, PartiallyOrdered<B>> evidence$5) {
        Option<Object> option = this.tryCompareTo(that, evidence$5);
        return option instanceof Some && BoxesRunTime.unboxToInt(((Some)option).value()) >= 0;
    }

    public static void $init$(PartiallyOrdered $this) {
    }
}

