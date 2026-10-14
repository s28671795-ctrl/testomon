/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.actor.DeadLetterSuppression;
import akka.dispatch.sysmsg.SystemMessage;
import akka.dispatch.sysmsg.Terminate$;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005ea!B\n\u0015\u0005bQ\u0002\"B\u001e\u0001\t\u0003a\u0004b\u0002 \u0001\u0003\u0003%\t\u0001\u0010\u0005\b\u007f\u0001\t\t\u0011\"\u0011A\u0011\u001dI\u0005!!A\u0005\u0002)CqA\u0014\u0001\u0002\u0002\u0013\u0005q\nC\u0004V\u0001\u0005\u0005I\u0011\t,\t\u000fu\u0003\u0011\u0011!C\u0001=\"91\rAA\u0001\n\u0003\"\u0007b\u00024\u0001\u0003\u0003%\te\u001a\u0005\bQ\u0002\t\t\u0011\"\u0011j\u0011\u001dQ\u0007!!A\u0005B-<\u0001b\u001d\u000b\u0002\u0002#\u0005\u0001\u0004\u001e\u0004\t'Q\t\t\u0011#\u0001\u0019k\"11(\u0004C\u0001\u0003\u0007Aq\u0001[\u0007\u0002\u0002\u0013\u0015\u0013\u000e\u0003\u0005\u0002\u00065\t\t\u0011\"!=\u0011%\t9!DA\u0001\n\u0003\u000bI\u0001C\u0005\u0002\u00105\t\t\u0011\"\u0003\u0002\u0012\tIA+\u001a:nS:\fG/\u001a\u0006\u0003+Y\taa]=t[N<'BA\f\u0019\u0003!!\u0017n\u001d9bi\u000eD'\"A\r\u0002\t\u0005\\7.Y\n\u0007\u0001m\tSe\u000b\u0018\u0011\u0005qyR\"A\u000f\u000b\u0003y\tQa]2bY\u0006L!\u0001I\u000f\u0003\r\u0005s\u0017PU3g!\t\u00113%D\u0001\u0015\u0013\t!CCA\u0007TsN$X-\\'fgN\fw-\u001a\t\u0003M%j\u0011a\n\u0006\u0003Qa\tQ!Y2u_JL!AK\u0014\u0003+\u0011+\u0017\r\u001a'fiR,'oU;qaJ,7o]5p]B\u0011A\u0004L\u0005\u0003[u\u0011q\u0001\u0015:pIV\u001cG\u000f\u0005\u00020q9\u0011\u0001G\u000e\b\u0003cUj\u0011A\r\u0006\u0003gQ\na\u0001\u0010:p_Rt4\u0001A\u0005\u0002=%\u0011q'H\u0001\ba\u0006\u001c7.Y4f\u0013\tI$H\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u00028;\u00051A(\u001b8jiz\"\u0012!\u0010\t\u0003E\u0001\tAaY8qs\u0006i\u0001O]8ek\u000e$\bK]3gSb,\u0012!\u0011\t\u0003\u0005\u001ek\u0011a\u0011\u0006\u0003\t\u0016\u000bA\u0001\\1oO*\ta)\u0001\u0003kCZ\f\u0017B\u0001%D\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\t1\n\u0005\u0002\u001d\u0019&\u0011Q*\b\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003!N\u0003\"\u0001H)\n\u0005Ik\"aA!os\"9A+BA\u0001\u0002\u0004Y\u0015a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001X!\rA6\fU\u0007\u00023*\u0011!,H\u0001\u000bG>dG.Z2uS>t\u0017B\u0001/Z\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0005}\u0013\u0007C\u0001\u000fa\u0013\t\tWDA\u0004C_>dW-\u00198\t\u000fQ;\u0011\u0011!a\u0001!\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\t\tU\rC\u0004U\u0011\u0005\u0005\t\u0019A&\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012aS\u0001\ti>\u001cFO]5oOR\t\u0011)\u0001\u0004fcV\fGn\u001d\u000b\u0003?2Dq\u0001V\u0006\u0002\u0002\u0003\u0007\u0001\u000b\u000b\u0003\u0001]F\u0014\bC\u0001\u000fp\u0013\t\u0001XD\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t\u0011!A\u0005UKJl\u0017N\\1uKB\u0011!%D\n\u0004\u001bYd\bcA<{{5\t\u0001P\u0003\u0002z;\u00059!/\u001e8uS6,\u0017BA>y\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g\u000e\r\t\u0004{\u0006\u0005Q\"\u0001@\u000b\u0005},\u0015AA5p\u0013\tId\u0010F\u0001u\u0003\u0015\t\u0007\u000f\u001d7z\u0003\u001d)h.\u00199qYf$2aXA\u0006\u0011!\ti!EA\u0001\u0002\u0004i\u0014a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u00111\u0003\t\u0004\u0005\u0006U\u0011bAA\f\u0007\n1qJ\u00196fGR\u0004")
public final class Terminate
implements SystemMessage,
DeadLetterSuppression,
Product {
    private static final long serialVersionUID = 1L;
    private transient SystemMessage next;

    public static boolean unapply(Terminate x$0) {
        return Terminate$.MODULE$.unapply(x$0);
    }

    public static Terminate apply() {
        return Terminate$.MODULE$.apply();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public void unlink() {
        SystemMessage.unlink$(this);
    }

    @Override
    public boolean unlinked() {
        return SystemMessage.unlinked$(this);
    }

    @Override
    public SystemMessage next() {
        return this.next;
    }

    @Override
    public void next_$eq(SystemMessage x$1) {
        this.next = x$1;
    }

    public Terminate copy() {
        return new Terminate();
    }

    @Override
    public String productPrefix() {
        return "Terminate";
    }

    @Override
    public int productArity() {
        return 0;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof Terminate;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    @Override
    public boolean equals(Object x$1) {
        Object object = x$1;
        return object instanceof Terminate;
    }

    public Terminate() {
        SystemMessage.$init$(this);
        Product.$init$(this);
    }
}

