/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.AkkaException;
import akka.actor.SchedulerException$;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u0005\u0005%c\u0001B\u000b\u0017\rnA\u0001B\u000f\u0001\u0003\u0016\u0004%\ta\u000f\u0005\t\t\u0002\u0011\t\u0012)A\u0005y!)Q\t\u0001C\u0001\r\"9!\nAA\u0001\n\u0003Y\u0005bB'\u0001#\u0003%\tA\u0014\u0005\b3\u0002\t\t\u0011\"\u0011[\u0011\u001d\u0011\u0007!!A\u0005\u0002\rDqa\u001a\u0001\u0002\u0002\u0013\u0005\u0001\u000eC\u0004o\u0001\u0005\u0005I\u0011I8\t\u000fY\u0004\u0011\u0011!C\u0001o\"9A\u0010AA\u0001\n\u0003j\b\u0002C@\u0001\u0003\u0003%\t%!\u0001\t\u0013\u0005\r\u0001!!A\u0005B\u0005\u0015q!CA\u0005-\u0005\u0005\t\u0012BA\u0006\r!)b#!A\t\n\u00055\u0001BB#\u0010\t\u0003\t)\u0003C\u0005\u0002(=\t\t\u0011\"\u0012\u0002*!I\u00111F\b\u0002\u0002\u0013\u0005\u0015Q\u0006\u0005\n\u0003cy\u0011\u0011!CA\u0003gA\u0011\"a\u0010\u0010\u0003\u0003%I!!\u0011\u0003%M\u001b\u0007.\u001a3vY\u0016\u0014X\t_2faRLwN\u001c\u0006\u0003/a\tQ!Y2u_JT\u0011!G\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u000b\u0001a\u0002E\u000b\u0018\u0011\u0005uqR\"\u0001\r\n\u0005}A\"!D!lW\u0006,\u0005pY3qi&|g\u000e\u0005\u0002\"Q5\t!E\u0003\u0002$I\u000591m\u001c8ue>d'BA\u0013'\u0003\u0011)H/\u001b7\u000b\u0003\u001d\nQa]2bY\u0006L!!\u000b\u0012\u0003\u00199{7\u000b^1dWR\u0013\u0018mY3\u0011\u0005-bS\"\u0001\u0014\n\u000552#a\u0002)s_\u0012,8\r\u001e\t\u0003_]r!\u0001M\u001b\u000f\u0005E\"T\"\u0001\u001a\u000b\u0005MR\u0012A\u0002\u001fs_>$h(C\u0001(\u0013\t1d%A\u0004qC\u000e\\\u0017mZ3\n\u0005aJ$\u0001D*fe&\fG.\u001b>bE2,'B\u0001\u001c'\u0003\ri7oZ\u000b\u0002yA\u0011Q(\u0011\b\u0003}}\u0002\"!\r\u0014\n\u0005\u00013\u0013A\u0002)sK\u0012,g-\u0003\u0002C\u0007\n11\u000b\u001e:j]\u001eT!\u0001\u0011\u0014\u0002\t5\u001cx\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\u001dK\u0005C\u0001%\u0001\u001b\u00051\u0002\"\u0002\u001e\u0004\u0001\u0004a\u0014\u0001B2paf$\"a\u0012'\t\u000fi\"\u0001\u0013!a\u0001y\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#A(+\u0005q\u00026&A)\u0011\u0005I;V\"A*\u000b\u0005Q+\u0016!C;oG\",7m[3e\u0015\t1f%\u0001\u0006b]:|G/\u0019;j_:L!\u0001W*\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u00027B\u0011A,Y\u0007\u0002;*\u0011alX\u0001\u0005Y\u0006twMC\u0001a\u0003\u0011Q\u0017M^1\n\u0005\tk\u0016\u0001\u00049s_\u0012,8\r^!sSRLX#\u00013\u0011\u0005-*\u0017B\u00014'\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\tIG\u000e\u0005\u0002,U&\u00111N\n\u0002\u0004\u0003:L\bbB7\t\u0003\u0003\u0005\r\u0001Z\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0003A\u00042!\u001d;j\u001b\u0005\u0011(BA:'\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003kJ\u0014\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR\u0011\u0001p\u001f\t\u0003WeL!A\u001f\u0014\u0003\u000f\t{w\u000e\\3b]\"9QNCA\u0001\u0002\u0004I\u0017A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$\"a\u0017@\t\u000f5\\\u0011\u0011!a\u0001I\u0006A\u0001.Y:i\u0007>$W\rF\u0001e\u0003\u0019)\u0017/^1mgR\u0019\u00010a\u0002\t\u000f5l\u0011\u0011!a\u0001S\u0006\u00112k\u00195fIVdWM]#yG\u0016\u0004H/[8o!\tAubE\u0003\u0010\u0003\u001f\tY\u0002\u0005\u0004\u0002\u0012\u0005]AhR\u0007\u0003\u0003'Q1!!\u0006'\u0003\u001d\u0011XO\u001c;j[\u0016LA!!\u0007\u0002\u0014\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u0019\u0011\t\u0005u\u00111E\u0007\u0003\u0003?Q1!!\t`\u0003\tIw.C\u00029\u0003?!\"!a\u0003\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012aW\u0001\u0006CB\u0004H.\u001f\u000b\u0004\u000f\u0006=\u0002\"\u0002\u001e\u0013\u0001\u0004a\u0014aB;oCB\u0004H.\u001f\u000b\u0005\u0003k\tY\u0004\u0005\u0003,\u0003oa\u0014bAA\u001dM\t1q\n\u001d;j_:D\u0001\"!\u0010\u0014\u0003\u0003\u0005\raR\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAA\"!\ra\u0016QI\u0005\u0004\u0003\u000fj&AB(cU\u0016\u001cG\u000f")
public final class SchedulerException
extends AkkaException
implements NoStackTrace,
Product {
    private final String msg;

    public static Option<String> unapply(SchedulerException x$0) {
        return SchedulerException$.MODULE$.unapply(x$0);
    }

    public static SchedulerException apply(String msg) {
        return SchedulerException$.MODULE$.apply(msg);
    }

    public static <A> Function1<String, A> andThen(Function1<SchedulerException, A> g2) {
        return SchedulerException$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, SchedulerException> compose(Function1<A, String> g2) {
        return SchedulerException$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
        return super.fillInStackTrace();
    }

    @Override
    public Throwable fillInStackTrace() {
        return NoStackTrace.fillInStackTrace$(this);
    }

    public String msg() {
        return this.msg;
    }

    public SchedulerException copy(String msg) {
        return new SchedulerException(msg);
    }

    public String copy$default$1() {
        return this.msg();
    }

    @Override
    public String productPrefix() {
        return "SchedulerException";
    }

    @Override
    public int productArity() {
        return 1;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.msg();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof SchedulerException;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "msg";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof SchedulerException)) return false;
        boolean bl = true;
        if (!bl) return false;
        SchedulerException schedulerException = (SchedulerException)x$1;
        String string = this.msg();
        String string2 = schedulerException.msg();
        if (string != null) {
            if (!string.equals(string2)) return false;
            return true;
        }
        if (string2 == null) return true;
        return false;
    }

    public SchedulerException(String msg) {
        this.msg = msg;
        super(msg);
        NoStackTrace.$init$(this);
        Product.$init$(this);
    }
}

