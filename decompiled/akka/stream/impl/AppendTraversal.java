/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.AppendTraversal$;
import akka.stream.impl.Traversal;
import akka.stream.impl.TraversalBuildStep;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dc!\u0002\f\u0018\u0005ni\u0002\u0002\u0003\u001d\u0001\u0005+\u0007I\u0011A\u001d\t\u0011u\u0002!\u0011#Q\u0001\niBQA\u0010\u0001\u0005\u0002}BqA\u0011\u0001\u0002\u0002\u0013\u00051\tC\u0004F\u0001E\u0005I\u0011\u0001$\t\u000fE\u0003\u0011\u0011!C!%\"91\fAA\u0001\n\u0003a\u0006b\u00021\u0001\u0003\u0003%\t!\u0019\u0005\bO\u0002\t\t\u0011\"\u0011i\u0011\u001dy\u0007!!A\u0005\u0002ADq!\u001e\u0001\u0002\u0002\u0013\u0005c\u000fC\u0004y\u0001\u0005\u0005I\u0011I=\t\u000fi\u0004\u0011\u0011!C!w\"9A\u0010AA\u0001\n\u0003jxACA\u0006/\u0005\u0005\t\u0012A\u000e\u0002\u000e\u0019IacFA\u0001\u0012\u0003Y\u0012q\u0002\u0005\u0007}A!\t!a\n\t\u000fi\u0004\u0012\u0011!C#w\"I\u0011\u0011\u0006\t\u0002\u0002\u0013\u0005\u00151\u0006\u0005\n\u0003_\u0001\u0012\u0011!CA\u0003cA\u0011\"!\u0010\u0011\u0003\u0003%I!a\u0010\u0003\u001f\u0005\u0003\b/\u001a8e)J\fg/\u001a:tC2T!\u0001G\r\u0002\t%l\u0007\u000f\u001c\u0006\u00035m\taa\u001d;sK\u0006l'\"\u0001\u000f\u0002\t\u0005\\7.Y\n\u0006\u0001y!\u0003f\u000b\t\u0003?\tj\u0011\u0001\t\u0006\u0002C\u0005)1oY1mC&\u00111\u0005\t\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\u00152S\"A\f\n\u0005\u001d:\"A\u0005+sCZ,'o]1m\u0005VLG\u000eZ*uKB\u0004\"aH\u0015\n\u0005)\u0002#a\u0002)s_\u0012,8\r\u001e\t\u0003YUr!!L\u001a\u000f\u00059\u0012T\"A\u0018\u000b\u0005A\n\u0014A\u0002\u001fs_>$hh\u0001\u0001\n\u0003\u0005J!\u0001\u000e\u0011\u0002\u000fA\f7m[1hK&\u0011ag\u000e\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003i\u0001\n\u0011\u0002\u001e:bm\u0016\u00148/\u00197\u0016\u0003i\u0002\"!J\u001e\n\u0005q:\"!\u0003+sCZ,'o]1m\u0003)!(/\u0019<feN\fG\u000eI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\u0001\u000b\u0005CA\u0013\u0001\u0011\u0015A4\u00011\u0001;\u0003\u0011\u0019w\u000e]=\u0015\u0005\u0001#\u0005b\u0002\u001d\u0005!\u0003\u0005\rAO\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u00059%F\u0001\u001eIW\u0005I\u0005C\u0001&P\u001b\u0005Y%B\u0001'N\u0003%)hn\u00195fG.,GM\u0003\u0002OA\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005A[%!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006i\u0001O]8ek\u000e$\bK]3gSb,\u0012a\u0015\t\u0003)fk\u0011!\u0016\u0006\u0003-^\u000bA\u0001\\1oO*\t\u0001,\u0001\u0003kCZ\f\u0017B\u0001.V\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\tQ\f\u0005\u0002 =&\u0011q\f\t\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003E\u0016\u0004\"aH2\n\u0005\u0011\u0004#aA!os\"9a\rCA\u0001\u0002\u0004i\u0016a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001j!\rQWNY\u0007\u0002W*\u0011A\u000eI\u0001\u000bG>dG.Z2uS>t\u0017B\u00018l\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0005E$\bCA\u0010s\u0013\t\u0019\bEA\u0004C_>dW-\u00198\t\u000f\u0019T\u0011\u0011!a\u0001E\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\t\u0019v\u000fC\u0004g\u0017\u0005\u0005\t\u0019A/\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012!X\u0001\ti>\u001cFO]5oOR\t1+\u0001\u0004fcV\fGn\u001d\u000b\u0003czDqA\u001a\b\u0002\u0002\u0003\u0007!\rK\u0002\u0001\u0003\u0003\u0001B!a\u0001\u0002\b5\u0011\u0011Q\u0001\u0006\u0003\u001dnIA!!\u0003\u0002\u0006\tY\u0011J\u001c;fe:\fG.\u00119j\u0003=\t\u0005\u000f]3oIR\u0013\u0018M^3sg\u0006d\u0007CA\u0013\u0011'\u0015\u0001\u0012\u0011CA\u000f!\u0019\t\u0019\"!\u0007;\u00016\u0011\u0011Q\u0003\u0006\u0004\u0003/\u0001\u0013a\u0002:v]RLW.Z\u0005\u0005\u00037\t)BA\tBEN$(/Y2u\rVt7\r^5p]F\u0002B!a\b\u0002&5\u0011\u0011\u0011\u0005\u0006\u0004\u0003G9\u0016AA5p\u0013\r1\u0014\u0011\u0005\u000b\u0003\u0003\u001b\tQ!\u00199qYf$2\u0001QA\u0017\u0011\u0015A4\u00031\u0001;\u0003\u001d)h.\u00199qYf$B!a\r\u0002:A!q$!\u000e;\u0013\r\t9\u0004\t\u0002\u0007\u001fB$\u0018n\u001c8\t\u0011\u0005mB#!AA\u0002\u0001\u000b1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\t\t\u0005E\u0002U\u0003\u0007J1!!\u0012V\u0005\u0019y%M[3di\u0002")
@InternalApi
public final class AppendTraversal
implements TraversalBuildStep,
Product,
Serializable {
    private final Traversal traversal;

    public static Option<Traversal> unapply(AppendTraversal x$0) {
        return AppendTraversal$.MODULE$.unapply(x$0);
    }

    public static AppendTraversal apply(Traversal traversal) {
        return AppendTraversal$.MODULE$.apply(traversal);
    }

    public static <A> Function1<Traversal, A> andThen(Function1<AppendTraversal, A> g2) {
        return AppendTraversal$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, AppendTraversal> compose(Function1<A, Traversal> g2) {
        return AppendTraversal$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Traversal traversal() {
        return this.traversal;
    }

    public AppendTraversal copy(Traversal traversal) {
        return new AppendTraversal(traversal);
    }

    public Traversal copy$default$1() {
        return this.traversal();
    }

    @Override
    public String productPrefix() {
        return "AppendTraversal";
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
                return this.traversal();
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
        return x$1 instanceof AppendTraversal;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "traversal";
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
        if (!(object instanceof AppendTraversal)) return false;
        boolean bl = true;
        if (!bl) return false;
        AppendTraversal appendTraversal = (AppendTraversal)x$1;
        Traversal traversal = this.traversal();
        Traversal traversal2 = appendTraversal.traversal();
        if (traversal != null) {
            if (!traversal.equals(traversal2)) return false;
            return true;
        }
        if (traversal2 == null) return true;
        return false;
    }

    public AppendTraversal(Traversal traversal) {
        this.traversal = traversal;
        Product.$init$(this);
    }
}

