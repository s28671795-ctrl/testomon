/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.StreamRefSubscriptionTimeoutException$;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005]b\u0001B\u000b\u0017\u0005nA\u0001B\u000e\u0001\u0003\u0016\u0004%\ta\u000e\u0005\t\u0001\u0002\u0011\t\u0012)A\u0005q!)\u0011\t\u0001C\u0001\u0005\"9a\tAA\u0001\n\u00039\u0005bB%\u0001#\u0003%\tA\u0013\u0005\b+\u0002\t\t\u0011\"\u0011W\u0011\u001dI\u0006!!A\u0005\u0002iCqA\u0018\u0001\u0002\u0002\u0013\u0005q\fC\u0004f\u0001\u0005\u0005I\u0011\t4\t\u000f5\u0004\u0011\u0011!C\u0001]\"91\u000fAA\u0001\n\u0003\"\bb\u0002<\u0001\u0003\u0003%\te\u001e\u0005\bq\u0002\t\t\u0011\"\u0011z\u000f\u001dYh#!A\t\u0002q4q!\u0006\f\u0002\u0002#\u0005Q\u0010\u0003\u0004B\u001f\u0011\u0005\u00111\u0003\u0005\n\u0003+y\u0011\u0011!C#\u0003/A\u0011\"!\u0007\u0010\u0003\u0003%\t)a\u0007\t\u0013\u0005}q\"!A\u0005\u0002\u0006\u0005\u0002\"CA\u0017\u001f\u0005\u0005I\u0011BA\u0018\u0005\u0015\u001aFO]3b[J+gmU;cg\u000e\u0014\u0018\u000e\u001d;j_:$\u0016.\\3pkR,\u0005pY3qi&|gN\u0003\u0002\u00181\u000511\u000f\u001e:fC6T\u0011!G\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\t\u0001aBE\u000b\t\u0003;\tj\u0011A\b\u0006\u0003?\u0001\nA\u0001\\1oO*\t\u0011%\u0001\u0003kCZ\f\u0017BA\u0012\u001f\u0005UIE\u000e\\3hC2\u001cF/\u0019;f\u000bb\u001cW\r\u001d;j_:\u0004\"!\n\u0015\u000e\u0003\u0019R\u0011aJ\u0001\u0006g\u000e\fG.Y\u0005\u0003S\u0019\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002,g9\u0011A&\r\b\u0003[Aj\u0011A\f\u0006\u0003_i\ta\u0001\u0010:p_Rt\u0014\"A\u0014\n\u0005I2\u0013a\u00029bG.\fw-Z\u0005\u0003iU\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!A\r\u0014\u0002\u00075\u001cx-F\u00019!\tITH\u0004\u0002;wA\u0011QFJ\u0005\u0003y\u0019\na\u0001\u0015:fI\u00164\u0017B\u0001 @\u0005\u0019\u0019FO]5oO*\u0011AHJ\u0001\u0005[N<\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0003\u0007\u0016\u0003\"\u0001\u0012\u0001\u000e\u0003YAQAN\u0002A\u0002a\nAaY8qsR\u00111\t\u0013\u0005\bm\u0011\u0001\n\u00111\u00019\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012a\u0013\u0016\u0003q1[\u0013!\u0014\t\u0003\u001dNk\u0011a\u0014\u0006\u0003!F\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005I3\u0013AC1o]>$\u0018\r^5p]&\u0011Ak\u0014\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001X!\ti\u0002,\u0003\u0002?=\u0005a\u0001O]8ek\u000e$\u0018I]5usV\t1\f\u0005\u0002&9&\u0011QL\n\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003A\u000e\u0004\"!J1\n\u0005\t4#aA!os\"9A\rCA\u0001\u0002\u0004Y\u0016a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001h!\rA7\u000eY\u0007\u0002S*\u0011!NJ\u0001\u000bG>dG.Z2uS>t\u0017B\u00017j\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0005=\u0014\bCA\u0013q\u0013\t\thEA\u0004C_>dW-\u00198\t\u000f\u0011T\u0011\u0011!a\u0001A\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\t9V\u000fC\u0004e\u0017\u0005\u0005\t\u0019A.\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012aW\u0001\u0007KF,\u0018\r\\:\u0015\u0005=T\bb\u00023\u000e\u0003\u0003\u0005\r\u0001Y\u0001&'R\u0014X-Y7SK\u001a\u001cVOY:de&\u0004H/[8o)&lWm\\;u\u000bb\u001cW\r\u001d;j_:\u0004\"\u0001R\b\u0014\t=q\u0018\u0011\u0002\t\u0006\u007f\u0006\u0015\u0001hQ\u0007\u0003\u0003\u0003Q1!a\u0001'\u0003\u001d\u0011XO\u001c;j[\u0016LA!a\u0002\u0002\u0002\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u0019\u0011\t\u0005-\u0011\u0011C\u0007\u0003\u0003\u001bQ1!a\u0004!\u0003\tIw.C\u00025\u0003\u001b!\u0012\u0001`\u0001\ti>\u001cFO]5oOR\tq+A\u0003baBd\u0017\u0010F\u0002D\u0003;AQA\u000e\nA\u0002a\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002$\u0005%\u0002\u0003B\u0013\u0002&aJ1!a\n'\u0005\u0019y\u0005\u000f^5p]\"A\u00111F\n\u0002\u0002\u0003\u00071)A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!\r\u0011\u0007u\t\u0019$C\u0002\u00026y\u0011aa\u00142kK\u000e$\b")
public final class StreamRefSubscriptionTimeoutException
extends IllegalStateException
implements Product {
    private final String msg;

    public static Option<String> unapply(StreamRefSubscriptionTimeoutException x$0) {
        return StreamRefSubscriptionTimeoutException$.MODULE$.unapply(x$0);
    }

    public static StreamRefSubscriptionTimeoutException apply(String msg) {
        return StreamRefSubscriptionTimeoutException$.MODULE$.apply(msg);
    }

    public static <A> Function1<String, A> andThen(Function1<StreamRefSubscriptionTimeoutException, A> g2) {
        return StreamRefSubscriptionTimeoutException$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, StreamRefSubscriptionTimeoutException> compose(Function1<A, String> g2) {
        return StreamRefSubscriptionTimeoutException$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String msg() {
        return this.msg;
    }

    public StreamRefSubscriptionTimeoutException copy(String msg) {
        return new StreamRefSubscriptionTimeoutException(msg);
    }

    public String copy$default$1() {
        return this.msg();
    }

    @Override
    public String productPrefix() {
        return "StreamRefSubscriptionTimeoutException";
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
        return x$1 instanceof StreamRefSubscriptionTimeoutException;
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
        if (!(object instanceof StreamRefSubscriptionTimeoutException)) return false;
        boolean bl = true;
        if (!bl) return false;
        StreamRefSubscriptionTimeoutException streamRefSubscriptionTimeoutException = (StreamRefSubscriptionTimeoutException)x$1;
        String string = this.msg();
        String string2 = streamRefSubscriptionTimeoutException.msg();
        if (string != null) {
            if (!string.equals(string2)) return false;
            return true;
        }
        if (string2 == null) return true;
        return false;
    }

    public StreamRefSubscriptionTimeoutException(String msg) {
        this.msg = msg;
        super(msg);
        Product.$init$(this);
    }
}

