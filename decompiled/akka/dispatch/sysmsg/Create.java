/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.actor.ActorInitializationException;
import akka.dispatch.sysmsg.Create$;
import akka.dispatch.sysmsg.SystemMessage;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005=c!\u0002\f\u0018\u0005ni\u0002\u0002\u0003\u001d\u0001\u0005+\u0007I\u0011A\u001d\t\u0011\r\u0003!\u0011#Q\u0001\niBQ\u0001\u0012\u0001\u0005\u0002\u0015Cq\u0001\u0013\u0001\u0002\u0002\u0013\u0005\u0011\nC\u0004L\u0001E\u0005I\u0011\u0001'\t\u000f]\u0003\u0011\u0011!C!1\"9\u0011\rAA\u0001\n\u0003\u0011\u0007b\u00024\u0001\u0003\u0003%\ta\u001a\u0005\b[\u0002\t\t\u0011\"\u0011o\u0011\u001d)\b!!A\u0005\u0002YDqa\u001f\u0001\u0002\u0002\u0013\u0005C\u0010C\u0004\u007f\u0001\u0005\u0005I\u0011I@\t\u0013\u0005\u0005\u0001!!A\u0005B\u0005\r\u0001\"CA\u0003\u0001\u0005\u0005I\u0011IA\u0004\u000f)\t9bFA\u0001\u0012\u0003Y\u0012\u0011\u0004\u0004\n-]\t\t\u0011#\u0001\u001c\u00037Aa\u0001\u0012\t\u0005\u0002\u0005M\u0002\"CA\u0001!\u0005\u0005IQIA\u0002\u0011%\t)\u0004EA\u0001\n\u0003\u000b9\u0004C\u0005\u0002<A\t\t\u0011\"!\u0002>!I\u0011Q\t\t\u0002\u0002\u0013%\u0011q\t\u0002\u0007\u0007J,\u0017\r^3\u000b\u0005aI\u0012AB:zg6\u001cxM\u0003\u0002\u001b7\u0005AA-[:qCR\u001c\u0007NC\u0001\u001d\u0003\u0011\t7n[1\u0014\u000b\u0001qB\u0005K\u0016\u0011\u0005}\u0011S\"\u0001\u0011\u000b\u0003\u0005\nQa]2bY\u0006L!a\t\u0011\u0003\r\u0005s\u0017PU3g!\t)c%D\u0001\u0018\u0013\t9sCA\u0007TsN$X-\\'fgN\fw-\u001a\t\u0003?%J!A\u000b\u0011\u0003\u000fA\u0013x\u000eZ;diB\u0011A&\u000e\b\u0003[Mr!A\f\u001a\u000e\u0003=R!\u0001M\u0019\u0002\rq\u0012xn\u001c;?\u0007\u0001I\u0011!I\u0005\u0003i\u0001\nq\u0001]1dW\u0006<W-\u0003\u00027o\ta1+\u001a:jC2L'0\u00192mK*\u0011A\u0007I\u0001\bM\u0006LG.\u001e:f+\u0005Q\u0004cA\u0010<{%\u0011A\b\t\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u0005y\nU\"A \u000b\u0005\u0001[\u0012!B1di>\u0014\u0018B\u0001\"@\u0005q\t5\r^8s\u0013:LG/[1mSj\fG/[8o\u000bb\u001cW\r\u001d;j_:\f\u0001BZ1jYV\u0014X\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\u0019;\u0005CA\u0013\u0001\u0011\u0015A4\u00011\u0001;\u0003\u0011\u0019w\u000e]=\u0015\u0005\u0019S\u0005b\u0002\u001d\u0005!\u0003\u0005\rAO\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005i%F\u0001\u001eOW\u0005y\u0005C\u0001)V\u001b\u0005\t&B\u0001*T\u0003%)hn\u00195fG.,GM\u0003\u0002UA\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005Y\u000b&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006i\u0001O]8ek\u000e$\bK]3gSb,\u0012!\u0017\t\u00035~k\u0011a\u0017\u0006\u00039v\u000bA\u0001\\1oO*\ta,\u0001\u0003kCZ\f\u0017B\u00011\\\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\t1\r\u0005\u0002 I&\u0011Q\r\t\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003Q.\u0004\"aH5\n\u0005)\u0004#aA!os\"9A\u000eCA\u0001\u0002\u0004\u0019\u0017a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001p!\r\u00018\u000f[\u0007\u0002c*\u0011!\u000fI\u0001\u000bG>dG.Z2uS>t\u0017B\u0001;r\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0005]T\bCA\u0010y\u0013\tI\bEA\u0004C_>dW-\u00198\t\u000f1T\u0011\u0011!a\u0001Q\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\tIV\u0010C\u0004m\u0017\u0005\u0005\t\u0019A2\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012aY\u0001\ti>\u001cFO]5oOR\t\u0011,\u0001\u0004fcV\fGn\u001d\u000b\u0004o\u0006%\u0001b\u00027\u000f\u0003\u0003\u0005\r\u0001\u001b\u0015\b\u0001\u00055\u00111CA\u000b!\ry\u0012qB\u0005\u0004\u0003#\u0001#\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\t\u0011AB\"sK\u0006$X\r\u0005\u0002&!M)\u0001#!\b\u0002*A1\u0011qDA\u0013u\u0019k!!!\t\u000b\u0007\u0005\r\u0002%A\u0004sk:$\u0018.\\3\n\t\u0005\u001d\u0012\u0011\u0005\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\f\u0004\u0003BA\u0016\u0003ci!!!\f\u000b\u0007\u0005=R,\u0001\u0002j_&\u0019a'!\f\u0015\u0005\u0005e\u0011!B1qa2LHc\u0001$\u0002:!)\u0001h\u0005a\u0001u\u00059QO\\1qa2LH\u0003BA \u0003\u0003\u00022aH\u001e;\u0011!\t\u0019\u0005FA\u0001\u0002\u00041\u0015a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011\u0011\n\t\u00045\u0006-\u0013bAA'7\n1qJ\u00196fGR\u0004")
public final class Create
implements SystemMessage,
Product {
    private static final long serialVersionUID = 1L;
    private final Option<ActorInitializationException> failure;
    private transient SystemMessage next;

    public static Option<Option<ActorInitializationException>> unapply(Create x$0) {
        return Create$.MODULE$.unapply(x$0);
    }

    public static Create apply(Option<ActorInitializationException> failure) {
        return Create$.MODULE$.apply(failure);
    }

    public static <A> Function1<Option<ActorInitializationException>, A> andThen(Function1<Create, A> g2) {
        return Create$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, Create> compose(Function1<A, Option<ActorInitializationException>> g2) {
        return Create$.MODULE$.compose(g2);
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

    public Option<ActorInitializationException> failure() {
        return this.failure;
    }

    public Create copy(Option<ActorInitializationException> failure) {
        return new Create(failure);
    }

    public Option<ActorInitializationException> copy$default$1() {
        return this.failure();
    }

    @Override
    public String productPrefix() {
        return "Create";
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
                return this.failure();
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
        return x$1 instanceof Create;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "failure";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof Create)) return false;
        boolean bl = true;
        if (!bl) return false;
        Create create = (Create)x$1;
        Option<ActorInitializationException> option = this.failure();
        Option<ActorInitializationException> option2 = create.failure();
        if (option != null) {
            if (!option.equals(option2)) return false;
            return true;
        }
        if (option2 == null) return true;
        return false;
    }

    public Create(Option<ActorInitializationException> failure) {
        this.failure = failure;
        SystemMessage.$init$(this);
        Product.$init$(this);
    }
}

