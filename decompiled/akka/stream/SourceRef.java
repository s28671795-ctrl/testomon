/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.NotUsed;
import akka.annotation.DoNotInherit;
import akka.stream.SourceRef$;
import akka.stream.javadsl.Source;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005=;Q\u0001C\u0005\t\u000291Q\u0001E\u0005\t\u0002EAQ\u0001G\u0001\u0005\u0002eAQAG\u0001\u0005\u0004m1q\u0001E\u0005\u0011\u0002\u0007\u0005Q\u0007C\u00038\t\u0011\u0005\u0001\bC\u0003=\t\u0019\u0005Q\bC\u0003B\t\u0011\u0015!)A\u0005T_V\u00148-\u001a*fM*\u0011!bC\u0001\u0007gR\u0014X-Y7\u000b\u00031\tA!Y6lC\u000e\u0001\u0001CA\b\u0002\u001b\u0005I!!C*pkJ\u001cWMU3g'\t\t!\u0003\u0005\u0002\u0014-5\tACC\u0001\u0016\u0003\u0015\u00198-\u00197b\u0013\t9BC\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u00039\t!cY8om\u0016\u0014HOU3g)>\u001cv.\u001e:dKV\u0011A$\n\u000b\u0003;I\u0002BAH\u0011$]5\tqD\u0003\u0002!\u0013\u0005A1oY1mC\u0012\u001cH.\u0003\u0002#?\t11k\\;sG\u0016\u0004\"\u0001J\u0013\r\u0001\u0011)ae\u0001b\u0001O\t\tA+\u0005\u0002)WA\u00111#K\u0005\u0003UQ\u0011qAT8uQ&tw\r\u0005\u0002\u0014Y%\u0011Q\u0006\u0006\u0002\u0004\u0003:L\bCA\u00181\u001b\u0005Y\u0011BA\u0019\f\u0005\u001dqu\u000e^+tK\u0012DQaM\u0002A\u0002Q\n1A]3g!\ryAaI\u000b\u0003m\u0001\u001b\"\u0001\u0002\n\u0002\r\u0011Jg.\u001b;%)\u0005I\u0004CA\n;\u0013\tYDC\u0001\u0003V]&$\u0018AB:pkJ\u001cW-F\u0001?!\u0011q\u0012e\u0010\u0018\u0011\u0005\u0011\u0002E!\u0002\u0014\u0005\u0005\u00049\u0013!C4fiN{WO]2f+\u0005\u0019\u0005\u0003\u0002#H\u007f9j\u0011!\u0012\u0006\u0003\r&\tqA[1wC\u0012\u001cH.\u0003\u0002#\u000b\"\u0012A!\u0013\t\u0003\u00156k\u0011a\u0013\u0006\u0003\u0019.\t!\"\u00198o_R\fG/[8o\u0013\tq5J\u0001\u0007E_:{G/\u00138iKJLG\u000f")
@DoNotInherit
public interface SourceRef<T> {
    public static <T> akka.stream.scaladsl.Source<T, NotUsed> convertRefToSource(SourceRef<T> ref) {
        return SourceRef$.MODULE$.convertRefToSource(ref);
    }

    public akka.stream.scaladsl.Source<T, NotUsed> source();

    public static /* synthetic */ Source getSource$(SourceRef $this) {
        return $this.getSource();
    }

    default public Source<T, NotUsed> getSource() {
        return this.source().asJava();
    }

    public static void $init$(SourceRef $this) {
    }
}

