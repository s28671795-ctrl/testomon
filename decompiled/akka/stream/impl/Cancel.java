/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.DeadLetterSuppression;
import akka.actor.NoSerializationVerificationNeeded;
import akka.annotation.InternalApi;
import akka.stream.impl.ActorSubscription;
import akka.stream.impl.Cancel$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005md!\u0002\f\u0018\u0005ni\u0002\u0002\u0003 \u0001\u0005+\u0007I\u0011A \t\u0011=\u0003!\u0011#Q\u0001\n\u0001CQ\u0001\u0015\u0001\u0005\u0002ECq\u0001\u0016\u0001\u0002\u0002\u0013\u0005Q\u000bC\u0004]\u0001E\u0005I\u0011A/\t\u000f)\u0004\u0011\u0011!C!W\"9A\u000fAA\u0001\n\u0003)\bbB=\u0001\u0003\u0003%\tA\u001f\u0005\b{\u0002\t\t\u0011\"\u0011\u007f\u0011%\tY\u0001AA\u0001\n\u0003\ti\u0001C\u0005\u0002\u0018\u0001\t\t\u0011\"\u0011\u0002\u001a!I\u0011Q\u0004\u0001\u0002\u0002\u0013\u0005\u0013q\u0004\u0005\n\u0003C\u0001\u0011\u0011!C!\u0003GA\u0011\"!\n\u0001\u0003\u0003%\t%a\n\b\u0015\u0005]r#!A\t\u0002m\tIDB\u0005\u0017/\u0005\u0005\t\u0012A\u000e\u0002<!1\u0001\u000b\u0005C\u0001\u0003\u000fB\u0011\"!\t\u0011\u0003\u0003%)%a\t\t\u0013\u0005%\u0003#!A\u0005\u0002\u0006-\u0003\"CA-!\u0005\u0005I\u0011QA.\u0011%\t\t\bEA\u0001\n\u0013\t\u0019H\u0001\u0004DC:\u001cW\r\u001c\u0006\u00031e\tA![7qY*\u0011!dG\u0001\u0007gR\u0014X-Y7\u000b\u0003q\tA!Y6lCV\u0011aDR\n\u0007\u0001})3FL\u0019\u0011\u0005\u0001\u001aS\"A\u0011\u000b\u0003\t\nQa]2bY\u0006L!\u0001J\u0011\u0003\r\u0005s\u0017PU3g!\t1\u0013&D\u0001(\u0015\tA3$A\u0003bGR|'/\u0003\u0002+O\t)B)Z1e\u0019\u0016$H/\u001a:TkB\u0004(/Z:tS>t\u0007C\u0001\u0014-\u0013\tisEA\u0011O_N+'/[1mSj\fG/[8o-\u0016\u0014\u0018NZ5dCRLwN\u001c(fK\u0012,G\r\u0005\u0002!_%\u0011\u0001'\t\u0002\b!J|G-^2u!\t\u00114H\u0004\u00024s9\u0011A\u0007O\u0007\u0002k)\u0011agN\u0001\u0007yI|w\u000e\u001e \u0004\u0001%\t!%\u0003\u0002;C\u00059\u0001/Y2lC\u001e,\u0017B\u0001\u001f>\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\tQ\u0014%\u0001\u0007tk\n\u001c8M]5qi&|g.F\u0001A!\r\t%\tR\u0007\u0002/%\u00111i\u0006\u0002\u0012\u0003\u000e$xN]*vEN\u001c'/\u001b9uS>t\u0007CA#G\u0019\u0001!Qa\u0012\u0001C\u0002!\u0013\u0011\u0001V\t\u0003\u00132\u0003\"\u0001\t&\n\u0005-\u000b#a\u0002(pi\"Lgn\u001a\t\u0003A5K!AT\u0011\u0003\u0007\u0005s\u00170A\u0007tk\n\u001c8M]5qi&|g\u000eI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005I\u001b\u0006cA!\u0001\t\")ah\u0001a\u0001\u0001\u0006!1m\u001c9z+\t1\u0016\f\u0006\u0002X5B\u0019\u0011\t\u0001-\u0011\u0005\u0015KF!B$\u0005\u0005\u0004A\u0005b\u0002 \u0005!\u0003\u0005\ra\u0017\t\u0004\u0003\nC\u0016AD2paf$C-\u001a4bk2$H%M\u000b\u0003=&,\u0012a\u0018\u0016\u0003\u0001\u0002\\\u0013!\u0019\t\u0003E\u001el\u0011a\u0019\u0006\u0003I\u0016\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005\u0019\f\u0013AC1o]>$\u0018\r^5p]&\u0011\u0001n\u0019\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,G!B$\u0006\u0005\u0004A\u0015!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001m!\ti'/D\u0001o\u0015\ty\u0007/\u0001\u0003mC:<'\"A9\u0002\t)\fg/Y\u0005\u0003g:\u0014aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRLX#\u0001<\u0011\u0005\u0001:\u0018B\u0001=\"\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\ta5\u0010C\u0004}\u0011\u0005\u0005\t\u0019\u0001<\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005y\b#BA\u0001\u0003\u000faUBAA\u0002\u0015\r\t)!I\u0001\u000bG>dG.Z2uS>t\u0017\u0002BA\u0005\u0003\u0007\u0011\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u0011qBA\u000b!\r\u0001\u0013\u0011C\u0005\u0004\u0003'\t#a\u0002\"p_2,\u0017M\u001c\u0005\by*\t\t\u00111\u0001M\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u00071\fY\u0002C\u0004}\u0017\u0005\u0005\t\u0019\u0001<\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012A^\u0001\ti>\u001cFO]5oOR\tA.\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003\u001f\tI\u0003C\u0004}\u001d\u0005\u0005\t\u0019\u0001')\u0007\u0001\ti\u0003\u0005\u0003\u00020\u0005MRBAA\u0019\u0015\t17$\u0003\u0003\u00026\u0005E\"aC%oi\u0016\u0014h.\u00197Ba&\faaQ1oG\u0016d\u0007CA!\u0011'\u0011\u0001r$!\u0010\u0011\t\u0005}\u0012QI\u0007\u0003\u0003\u0003R1!a\u0011q\u0003\tIw.C\u0002=\u0003\u0003\"\"!!\u000f\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\t\u00055\u00131\u000b\u000b\u0005\u0003\u001f\n)\u0006\u0005\u0003B\u0001\u0005E\u0003cA#\u0002T\u0011)qi\u0005b\u0001\u0011\"1ah\u0005a\u0001\u0003/\u0002B!\u0011\"\u0002R\u00059QO\\1qa2LX\u0003BA/\u0003S\"B!a\u0018\u0002lA)\u0001%!\u0019\u0002f%\u0019\u00111M\u0011\u0003\r=\u0003H/[8o!\u0011\t%)a\u001a\u0011\u0007\u0015\u000bI\u0007B\u0003H)\t\u0007\u0001\nC\u0005\u0002nQ\t\t\u00111\u0001\u0002p\u0005\u0019\u0001\u0010\n\u0019\u0011\t\u0005\u0003\u0011qM\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003k\u00022!\\A<\u0013\r\tIH\u001c\u0002\u0007\u001f\nTWm\u0019;")
@InternalApi
public final class Cancel<T>
implements DeadLetterSuppression,
NoSerializationVerificationNeeded,
Product,
Serializable {
    private final ActorSubscription<T> subscription;

    public static <T> Option<ActorSubscription<T>> unapply(Cancel<T> x$0) {
        return Cancel$.MODULE$.unapply(x$0);
    }

    public static <T> Cancel<T> apply(ActorSubscription<T> subscription) {
        return Cancel$.MODULE$.apply(subscription);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public ActorSubscription<T> subscription() {
        return this.subscription;
    }

    public <T> Cancel<T> copy(ActorSubscription<T> subscription) {
        return new Cancel<T>(subscription);
    }

    public <T> ActorSubscription<T> copy$default$1() {
        return this.subscription();
    }

    @Override
    public String productPrefix() {
        return "Cancel";
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
                return this.subscription();
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
        return x$1 instanceof Cancel;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "subscription";
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
        if (!(object instanceof Cancel)) return false;
        boolean bl = true;
        if (!bl) return false;
        Cancel cancel = (Cancel)x$1;
        ActorSubscription<T> actorSubscription = this.subscription();
        ActorSubscription<T> actorSubscription2 = cancel.subscription();
        if (actorSubscription != null) {
            if (!actorSubscription.equals(actorSubscription2)) return false;
            return true;
        }
        if (actorSubscription2 == null) return true;
        return false;
    }

    public Cancel(ActorSubscription<T> subscription) {
        this.subscription = subscription;
        Product.$init$(this);
    }
}

