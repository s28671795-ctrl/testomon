/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.TargetRefNotInitializedYetException$;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005i4AAE\nC1!)1\u0007\u0001C\u0001i!9q\u0007AA\u0001\n\u0003!\u0004b\u0002\u001d\u0001\u0003\u0003%\t%\u000f\u0005\b{\u0001\t\t\u0011\"\u0001?\u0011\u001d\u0011\u0005!!A\u0005\u0002\rCq!\u0013\u0001\u0002\u0002\u0013\u0005#\nC\u0004R\u0001\u0005\u0005I\u0011\u0001*\t\u000f]\u0003\u0011\u0011!C!1\"9!\fAA\u0001\n\u0003Z\u0006b\u0002/\u0001\u0003\u0003%\t%X\u0004\b?N\t\t\u0011#\u0001a\r\u001d\u00112#!A\t\u0002\u0005DQa\r\u0007\u0005\u00025DqA\u001c\u0007\u0002\u0002\u0013\u0015s\u000eC\u0004q\u0019\u0005\u0005I\u0011\u0011\u001b\t\u000fEd\u0011\u0011!CAe\"9Q\u000fDA\u0001\n\u00131(a\t+be\u001e,GOU3g\u001d>$\u0018J\\5uS\u0006d\u0017N_3e3\u0016$X\t_2faRLwN\u001c\u0006\u0003)U\taa\u001d;sK\u0006l'\"\u0001\f\u0002\t\u0005\\7.Y\u0002\u0001'\u0011\u0001\u0011$I\u0014\u0011\u0005iyR\"A\u000e\u000b\u0005qi\u0012\u0001\u00027b]\u001eT\u0011AH\u0001\u0005U\u00064\u0018-\u0003\u0002!7\t)\u0012\n\u001c7fO\u0006d7\u000b^1uK\u0016C8-\u001a9uS>t\u0007C\u0001\u0012&\u001b\u0005\u0019#\"\u0001\u0013\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0019\u001a#a\u0002)s_\u0012,8\r\u001e\t\u0003QAr!!\u000b\u0018\u000f\u0005)jS\"A\u0016\u000b\u00051:\u0012A\u0002\u001fs_>$h(C\u0001%\u0013\ty3%A\u0004qC\u000e\\\u0017mZ3\n\u0005E\u0012$\u0001D*fe&\fG.\u001b>bE2,'BA\u0018$\u0003\u0019a\u0014N\\5u}Q\tQ\u0007\u0005\u00027\u00015\t1#\u0001\u0003d_BL\u0018!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001;!\tQ2(\u0003\u0002=7\t11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012a\u0010\t\u0003E\u0001K!!Q\u0012\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0005\u0011;\u0005C\u0001\u0012F\u0013\t15EA\u0002B]fDq\u0001S\u0003\u0002\u0002\u0003\u0007q(A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0002\u0017B\u0019Aj\u0014#\u000e\u00035S!AT\u0012\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002Q\u001b\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\t\u0019f\u000b\u0005\u0002#)&\u0011Qk\t\u0002\b\u0005>|G.Z1o\u0011\u001dAu!!AA\u0002\u0011\u000b!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u0011!(\u0017\u0005\b\u0011\"\t\t\u00111\u0001@\u0003!A\u0017m\u001d5D_\u0012,G#A \u0002\r\u0015\fX/\u00197t)\t\u0019f\fC\u0004I\u0015\u0005\u0005\t\u0019\u0001#\u0002GQ\u000b'oZ3u%\u00164gj\u001c;J]&$\u0018.\u00197ju\u0016$\u0017,\u001a;Fq\u000e,\u0007\u000f^5p]B\u0011a\u0007D\n\u0004\u0019\tD\u0007cA2gk5\tAM\u0003\u0002fG\u00059!/\u001e8uS6,\u0017BA4e\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g\u000e\r\t\u0003S2l\u0011A\u001b\u0006\u0003Wv\t!![8\n\u0005ERG#\u00011\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012AO\u0001\u0006CB\u0004H._\u0001\bk:\f\u0007\u000f\u001d7z)\t\u00196\u000fC\u0004u!\u0005\u0005\t\u0019A\u001b\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u0001x!\tQ\u00020\u0003\u0002z7\t1qJ\u00196fGR\u0004")
public final class TargetRefNotInitializedYetException
extends IllegalStateException
implements Product {
    public static boolean unapply(TargetRefNotInitializedYetException x$0) {
        return TargetRefNotInitializedYetException$.MODULE$.unapply(x$0);
    }

    public static TargetRefNotInitializedYetException apply() {
        return TargetRefNotInitializedYetException$.MODULE$.apply();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public TargetRefNotInitializedYetException copy() {
        return new TargetRefNotInitializedYetException();
    }

    @Override
    public String productPrefix() {
        return "TargetRefNotInitializedYetException";
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
        return x$1 instanceof TargetRefNotInitializedYetException;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    @Override
    public boolean equals(Object x$1) {
        Object object = x$1;
        return object instanceof TargetRefNotInitializedYetException;
    }

    public TargetRefNotInitializedYetException() {
        super("Internal remote target actor ref not yet resolved, yet attempted to send messages to it. This should not happen due to proper flow-control, please open a ticket on the issue tracker: https://github.com/akka/akka");
        Product.$init$(this);
    }
}

