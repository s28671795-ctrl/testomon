/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.Timeout$;
import java.io.Serializable;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\re\u0001B\u000e\u001d\u0001\u0006B\u0001b\u000e\u0001\u0003\u0016\u0004%\t\u0001\u000f\u0005\t\u0001\u0002\u0011\t\u0012)A\u0005s!)\u0011\t\u0001C\u0001\u0005\")\u0011\t\u0001C\u0001\r\"9a\u000bAA\u0001\n\u00039\u0006bB-\u0001#\u0003%\tA\u0017\u0005\bK\u0002\t\t\u0011\"\u0011g\u0011\u001di\u0007!!A\u0005\u00029DqA\u001d\u0001\u0002\u0002\u0013\u00051\u000fC\u0004z\u0001\u0005\u0005I\u0011\t>\t\u0013\u0005\r\u0001!!A\u0005\u0002\u0005\u0015\u0001\"CA\b\u0001\u0005\u0005I\u0011IA\t\u0011%\t)\u0002AA\u0001\n\u0003\n9\u0002C\u0005\u0002\u001a\u0001\t\t\u0011\"\u0011\u0002\u001c!I\u0011Q\u0004\u0001\u0002\u0002\u0013\u0005\u0013qD\u0004\b\u0003_a\u0002\u0012AA\u0019\r\u0019YB\u0004#\u0001\u00024!1\u0011)\u0005C\u0001\u0003\u007fA\u0011\"!\u0011\u0012\u0005\u0004%\t!a\u0011\t\u000f\u0005\u0015\u0013\u0003)A\u0005\u0007\"9\u0011qI\t\u0005\u0002\u0005%\u0003bBA(#\u0011\u0005\u0011\u0011\u000b\u0005\b\u0003C\nB1AA2\u0011%\t9%EA\u0001\n\u0003\u000b9\u0007C\u0005\u0002lE\t\t\u0011\"!\u0002n!I\u0011\u0011P\t\u0002\u0002\u0013%\u00111\u0010\u0002\b)&lWm\\;u\u0015\tib$\u0001\u0003vi&d'\"A\u0010\u0002\t\u0005\\7.Y\u0002\u0001'\u0011\u0001!\u0005K\u0016\u0011\u0005\r2S\"\u0001\u0013\u000b\u0003\u0015\nQa]2bY\u0006L!a\n\u0013\u0003\r\u0005s\u0017PU3g!\t\u0019\u0013&\u0003\u0002+I\t9\u0001K]8ek\u000e$\bC\u0001\u00175\u001d\ti#G\u0004\u0002/c5\tqF\u0003\u00021A\u00051AH]8pizJ\u0011!J\u0005\u0003g\u0011\nq\u0001]1dW\u0006<W-\u0003\u00026m\ta1+\u001a:jC2L'0\u00192mK*\u00111\u0007J\u0001\tIV\u0014\u0018\r^5p]V\t\u0011\b\u0005\u0002;}5\t1H\u0003\u00028y)\u0011Q\bJ\u0001\u000bG>t7-\u001e:sK:$\u0018BA <\u000591\u0015N\\5uK\u0012+(/\u0019;j_:\f\u0011\u0002Z;sCRLwN\u001c\u0011\u0002\rqJg.\u001b;?)\t\u0019U\t\u0005\u0002E\u00015\tA\u0004C\u00038\u0007\u0001\u0007\u0011\bF\u0002D\u000f2CQ\u0001\u0013\u0003A\u0002%\u000ba\u0001\\3oORD\u0007CA\u0012K\u0013\tYEE\u0001\u0003M_:<\u0007\"B'\u0005\u0001\u0004q\u0015\u0001B;oSR\u0004\"a\u0014+\u000e\u0003AS!!P)\u000b\u0005u\u0011&\"A*\u0002\t)\fg/Y\u0005\u0003+B\u0013\u0001\u0002V5nKVs\u0017\u000e^\u0001\u0005G>\u0004\u0018\u0010\u0006\u0002D1\"9q'\u0002I\u0001\u0002\u0004I\u0014AD2paf$C-\u001a4bk2$H%M\u000b\u00027*\u0012\u0011\bX\u0016\u0002;B\u0011alY\u0007\u0002?*\u0011\u0001-Y\u0001\nk:\u001c\u0007.Z2lK\u0012T!A\u0019\u0013\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002e?\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u00059\u0007C\u00015l\u001b\u0005I'B\u00016S\u0003\u0011a\u0017M\\4\n\u00051L'AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001p!\t\u0019\u0003/\u0003\u0002rI\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011Ao\u001e\t\u0003GUL!A\u001e\u0013\u0003\u0007\u0005s\u0017\u0010C\u0004y\u0013\u0005\u0005\t\u0019A8\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005Y\bc\u0001?\u0000i6\tQP\u0003\u0002\u007fI\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0007\u0005\u0005QP\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA\u0004\u0003\u001b\u00012aIA\u0005\u0013\r\tY\u0001\n\u0002\b\u0005>|G.Z1o\u0011\u001dA8\"!AA\u0002Q\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u0019q-a\u0005\t\u000fad\u0011\u0011!a\u0001_\u0006A\u0001.Y:i\u0007>$W\rF\u0001p\u0003!!xn\u0015;sS:<G#A4\u0002\r\u0015\fX/\u00197t)\u0011\t9!!\t\t\u000fa|\u0011\u0011!a\u0001i\":\u0001!!\n\u0002,\u00055\u0002cA\u0012\u0002(%\u0019\u0011\u0011\u0006\u0013\u0003!M+'/[1m-\u0016\u00148/[8o+&#\u0015!\u0002<bYV,g$A\u0001\u0002\u000fQKW.Z8viB\u0011A)E\n\u0005#\t\n)\u0004\u0005\u0003\u00028\u0005uRBAA\u001d\u0015\r\tYDU\u0001\u0003S>L1!NA\u001d)\t\t\t$\u0001\u0003{KJ|W#A\"\u0002\u000bi,'o\u001c\u0011\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u000b\r\u000bY%!\u0014\t\u000b!+\u0002\u0019A%\t\u000b5+\u0002\u0019\u0001(\u0002\r\r\u0014X-\u0019;f)\r\u0019\u00151\u000b\u0005\u0007oY\u0001\r!!\u0016\u0011\t\u0005]\u0013QL\u0007\u0003\u00033R1!a\u0017S\u0003\u0011!\u0018.\\3\n\t\u0005}\u0013\u0011\f\u0002\t\tV\u0014\u0018\r^5p]\u0006\tB-\u001e:bi&|g\u000eV8US6,w.\u001e;\u0015\u0007\r\u000b)\u0007C\u00038/\u0001\u0007\u0011\bF\u0002D\u0003SBQa\u000e\rA\u0002e\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002p\u0005U\u0004\u0003B\u0012\u0002reJ1!a\u001d%\u0005\u0019y\u0005\u000f^5p]\"A\u0011qO\r\u0002\u0002\u0003\u00071)A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!! \u0011\u0007!\fy(C\u0002\u0002\u0002&\u0014aa\u00142kK\u000e$\b")
public class Timeout
implements Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final FiniteDuration duration;

    public static Option<FiniteDuration> unapply(Timeout x$0) {
        return Timeout$.MODULE$.unapply(x$0);
    }

    public static Timeout apply(FiniteDuration duration) {
        return Timeout$.MODULE$.apply(duration);
    }

    public static Timeout durationToTimeout(FiniteDuration duration) {
        return Timeout$.MODULE$.durationToTimeout(duration);
    }

    public static Timeout create(Duration duration) {
        return Timeout$.MODULE$.create(duration);
    }

    public static Timeout apply(long length, TimeUnit unit) {
        return Timeout$.MODULE$.apply(length, unit);
    }

    public static Timeout zero() {
        return Timeout$.MODULE$.zero();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public FiniteDuration duration() {
        return this.duration;
    }

    public Timeout copy(FiniteDuration duration) {
        return new Timeout(duration);
    }

    public FiniteDuration copy$default$1() {
        return this.duration();
    }

    @Override
    public String productPrefix() {
        return "Timeout";
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
                return this.duration();
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
        return x$1 instanceof Timeout;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "duration";
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
        if (!(object instanceof Timeout)) return false;
        boolean bl = true;
        if (!bl) return false;
        Timeout timeout2 = (Timeout)x$1;
        FiniteDuration finiteDuration = this.duration();
        FiniteDuration finiteDuration2 = timeout2.duration();
        if (finiteDuration == null) {
            if (finiteDuration2 != null) {
                return false;
            }
        } else if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
        if (!timeout2.canEqual(this)) return false;
        return true;
    }

    public Timeout(FiniteDuration duration) {
        this.duration = duration;
        Product.$init$(this);
    }

    public Timeout(long length, TimeUnit unit) {
        this(Duration$.MODULE$.apply(length, unit));
    }
}

