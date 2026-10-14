/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.impl.PushAttributes$;
import akka.stream.impl.Traversal;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005%c!\u0002\f\u0018\u0005ni\u0002\u0002\u0003\u001d\u0001\u0005+\u0007I\u0011A\u001d\t\u0011y\u0002!\u0011#Q\u0001\niBQa\u0010\u0001\u0005\u0002\u0001Cqa\u0011\u0001\u0002\u0002\u0013\u0005A\tC\u0004G\u0001E\u0005I\u0011A$\t\u000fI\u0003\u0011\u0011!C!'\"9A\fAA\u0001\n\u0003i\u0006bB1\u0001\u0003\u0003%\tA\u0019\u0005\bQ\u0002\t\t\u0011\"\u0011j\u0011\u001d\u0001\b!!A\u0005\u0002EDqA\u001e\u0001\u0002\u0002\u0013\u0005s\u000fC\u0004z\u0001\u0005\u0005I\u0011\t>\t\u000fm\u0004\u0011\u0011!C!y\"9Q\u0010AA\u0001\n\u0003rxACA\u0007/\u0005\u0005\t\u0012A\u000e\u0002\u0010\u0019IacFA\u0001\u0012\u0003Y\u0012\u0011\u0003\u0005\u0007\u007fA!\t!!\u000b\t\u000fm\u0004\u0012\u0011!C#y\"I\u00111\u0006\t\u0002\u0002\u0013\u0005\u0015Q\u0006\u0005\n\u0003c\u0001\u0012\u0011!CA\u0003gA\u0011\"a\u0010\u0011\u0003\u0003%I!!\u0011\u0003\u001dA+8\u000f[!uiJL'-\u001e;fg*\u0011\u0001$G\u0001\u0005S6\u0004HN\u0003\u0002\u001b7\u000511\u000f\u001e:fC6T\u0011\u0001H\u0001\u0005C.\\\u0017mE\u0003\u0001=\u0011B3\u0006\u0005\u0002 E5\t\u0001EC\u0001\"\u0003\u0015\u00198-\u00197b\u0013\t\u0019\u0003E\u0001\u0004B]f\u0014VM\u001a\t\u0003K\u0019j\u0011aF\u0005\u0003O]\u0011\u0011\u0002\u0016:bm\u0016\u00148/\u00197\u0011\u0005}I\u0013B\u0001\u0016!\u0005\u001d\u0001&o\u001c3vGR\u0004\"\u0001L\u001b\u000f\u00055\u001adB\u0001\u00183\u001b\u0005y#B\u0001\u00192\u0003\u0019a$o\\8u}\r\u0001\u0011\"A\u0011\n\u0005Q\u0002\u0013a\u00029bG.\fw-Z\u0005\u0003m]\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!\u0001\u000e\u0011\u0002\u0015\u0005$HO]5ckR,7/F\u0001;!\tYD(D\u0001\u001a\u0013\ti\u0014D\u0001\u0006BiR\u0014\u0018NY;uKN\f1\"\u0019;ue&\u0014W\u000f^3tA\u00051A(\u001b8jiz\"\"!\u0011\"\u0011\u0005\u0015\u0002\u0001\"\u0002\u001d\u0004\u0001\u0004Q\u0014\u0001B2paf$\"!Q#\t\u000fa\"\u0001\u0013!a\u0001u\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#\u0001%+\u0005iJ5&\u0001&\u0011\u0005-\u0003V\"\u0001'\u000b\u00055s\u0015!C;oG\",7m[3e\u0015\ty\u0005%\u0001\u0006b]:|G/\u0019;j_:L!!\u0015'\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002)B\u0011QKW\u0007\u0002-*\u0011q\u000bW\u0001\u0005Y\u0006twMC\u0001Z\u0003\u0011Q\u0017M^1\n\u0005m3&AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001_!\tyr,\u0003\u0002aA\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u00111M\u001a\t\u0003?\u0011L!!\u001a\u0011\u0003\u0007\u0005s\u0017\u0010C\u0004h\u0011\u0005\u0005\t\u0019\u00010\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005Q\u0007cA6oG6\tAN\u0003\u0002nA\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005=d'\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$\"A];\u0011\u0005}\u0019\u0018B\u0001;!\u0005\u001d\u0011un\u001c7fC:Dqa\u001a\u0006\u0002\u0002\u0003\u00071-\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,GC\u0001+y\u0011\u001d97\"!AA\u0002y\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002=\u0006AAo\\*ue&tw\rF\u0001U\u0003\u0019)\u0017/^1mgR\u0011!o \u0005\bO:\t\t\u00111\u0001dQ\r\u0001\u00111\u0001\t\u0005\u0003\u000b\tI!\u0004\u0002\u0002\b)\u0011qjG\u0005\u0005\u0003\u0017\t9AA\u0006J]R,'O\\1m\u0003BL\u0017A\u0004)vg\"\fE\u000f\u001e:jEV$Xm\u001d\t\u0003KA\u0019R\u0001EA\n\u0003?\u0001b!!\u0006\u0002\u001ci\nUBAA\f\u0015\r\tI\u0002I\u0001\beVtG/[7f\u0013\u0011\ti\"a\u0006\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t\u0017\u0007\u0005\u0003\u0002\"\u0005\u001dRBAA\u0012\u0015\r\t)\u0003W\u0001\u0003S>L1ANA\u0012)\t\ty!A\u0003baBd\u0017\u0010F\u0002B\u0003_AQ\u0001O\nA\u0002i\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u00026\u0005m\u0002\u0003B\u0010\u00028iJ1!!\u000f!\u0005\u0019y\u0005\u000f^5p]\"A\u0011Q\b\u000b\u0002\u0002\u0003\u0007\u0011)A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!a\u0011\u0011\u0007U\u000b)%C\u0002\u0002HY\u0013aa\u00142kK\u000e$\b")
@InternalApi
public final class PushAttributes
implements Traversal,
Product,
Serializable {
    private final Attributes attributes;

    public static Option<Attributes> unapply(PushAttributes x$0) {
        return PushAttributes$.MODULE$.unapply(x$0);
    }

    public static PushAttributes apply(Attributes attributes) {
        return PushAttributes$.MODULE$.apply(attributes);
    }

    public static <A> Function1<Attributes, A> andThen(Function1<PushAttributes, A> g2) {
        return PushAttributes$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, PushAttributes> compose(Function1<A, Attributes> g2) {
        return PushAttributes$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public Traversal concat(Traversal that) {
        return Traversal.concat$(this, that);
    }

    @Override
    public Traversal rewireFirstTo(int relativeOffset) {
        return Traversal.rewireFirstTo$(this, relativeOffset);
    }

    public Attributes attributes() {
        return this.attributes;
    }

    public PushAttributes copy(Attributes attributes) {
        return new PushAttributes(attributes);
    }

    public Attributes copy$default$1() {
        return this.attributes();
    }

    @Override
    public String productPrefix() {
        return "PushAttributes";
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
                return this.attributes();
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
        return x$1 instanceof PushAttributes;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "attributes";
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
        if (!(object instanceof PushAttributes)) return false;
        boolean bl = true;
        if (!bl) return false;
        PushAttributes pushAttributes = (PushAttributes)x$1;
        Attributes attributes = this.attributes();
        Attributes attributes2 = pushAttributes.attributes();
        if (attributes != null) {
            if (!((Object)attributes).equals(attributes2)) return false;
            return true;
        }
        if (attributes2 == null) return true;
        return false;
    }

    public PushAttributes(Attributes attributes) {
        this.attributes = attributes;
        Traversal.$init$(this);
        Product.$init$(this);
    }
}

