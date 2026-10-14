/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.CancelledSubscription$;
import akka.stream.impl.ErrorPublisher$;
import akka.stream.impl.ReactiveStreamsCompliance;
import akka.stream.impl.ReactiveStreamsCompliance$;
import java.io.Serializable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005=f!B\u000e\u001d\u0005\u0002\u0012\u0003\u0002C%\u0001\u0005+\u0007I\u0011\u0001&\t\u00119\u0003!\u0011#Q\u0001\n-C\u0001b\u0014\u0001\u0003\u0016\u0004%\t\u0001\u0015\u0005\t3\u0002\u0011\t\u0012)A\u0005#\")!\f\u0001C\u00017\")\u0001\r\u0001C!C\")1\u000f\u0001C\u0001i\")!\u0010\u0001C!w\"9A\u0010AA\u0001\n\u0003i\b\"CA\u0001\u0001E\u0005I\u0011AA\u0002\u0011%\tI\u0002AI\u0001\n\u0003\tY\u0002C\u0005\u0002 \u0001\t\t\u0011\"\u0011\u0002\"!I\u0011q\u0005\u0001\u0002\u0002\u0013\u0005\u0011\u0011\u0006\u0005\n\u0003c\u0001\u0011\u0011!C\u0001\u0003gA\u0011\"!\u000f\u0001\u0003\u0003%\t%a\u000f\t\u0013\u0005%\u0003!!A\u0005\u0002\u0005-\u0003\"CA+\u0001\u0005\u0005I\u0011IA,\u0011%\tY\u0006AA\u0001\n\u0003\ni\u0006C\u0005\u0002`\u0001\t\t\u0011\"\u0011\u0002b\u001dQ\u0011\u0011\u000f\u000f\u0002\u0002#\u0005\u0001%a\u001d\u0007\u0013ma\u0012\u0011!E\u0001A\u0005U\u0004B\u0002.\u0016\t\u0003\ti\t\u0003\u0005{+\u0005\u0005IQIAH\u0011!\u0019X#!A\u0005\u0002\u0006E\u0005\"CAL+\u0005\u0005I\u0011QAM\u0011%\tY+FA\u0001\n\u0013\tiK\u0001\bFeJ|'\u000fU;cY&\u001c\b.\u001a:\u000b\u0005uq\u0012\u0001B5na2T!a\b\u0011\u0002\rM$(/Z1n\u0015\u0005\t\u0013\u0001B1lW\u0006\u001cR\u0001A\u0012,sq\u0002\"\u0001J\u0015\u000e\u0003\u0015R!AJ\u0014\u0002\t1\fgn\u001a\u0006\u0002Q\u0005!!.\u0019<b\u0013\tQSE\u0001\u0004PE*,7\r\u001e\t\u0004YE\u001aT\"A\u0017\u000b\u00059z\u0013a\u0004:fC\u000e$\u0018N^3tiJ,\u0017-\\:\u000b\u0003A\n1a\u001c:h\u0013\t\u0011TFA\u0005Qk\nd\u0017n\u001d5feB\u0011AgN\u0007\u0002k)\ta'A\u0003tG\u0006d\u0017-\u0003\u00029k\t9aj\u001c;iS:<\u0007C\u0001\u001b;\u0013\tYTGA\u0004Qe>$Wo\u0019;\u0011\u0005u2eB\u0001 E\u001d\ty4)D\u0001A\u0015\t\t%)\u0001\u0004=e>|GOP\u0002\u0001\u0013\u00051\u0014BA#6\u0003\u001d\u0001\u0018mY6bO\u0016L!a\u0012%\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005\u0015+\u0014!\u0001;\u0016\u0003-\u0003\"!\u0010'\n\u00055C%!\u0003+ie><\u0018M\u00197f\u0003\t!\b%\u0001\u0003oC6,W#A)\u0011\u0005I3fBA*U!\tyT'\u0003\u0002Vk\u00051\u0001K]3eK\u001aL!a\u0016-\u0003\rM#(/\u001b8h\u0015\t)V'A\u0003oC6,\u0007%\u0001\u0004=S:LGO\u0010\u000b\u00049z{\u0006CA/\u0001\u001b\u0005a\u0002\"B%\u0006\u0001\u0004Y\u0005\"B(\u0006\u0001\u0004\t\u0016!C:vEN\u001c'/\u001b2f)\t\u0011W\r\u0005\u00025G&\u0011A-\u000e\u0002\u0005+:LG\u000fC\u0003g\r\u0001\u0007q-\u0001\u0006tk\n\u001c8M]5cKJ\u0004$\u0001[7\u0011\u00071J7.\u0003\u0002k[\tQ1+\u001e2tGJL'-\u001a:\u0011\u00051lG\u0002\u0001\u0003\n]\u0016\f\t\u0011!A\u0003\u0002=\u00141a\u0018\u00133#\t\u0019\u0004\u000f\u0005\u00025c&\u0011!/\u000e\u0002\u0004\u0003:L\u0018!B1qa2LXCA;y+\u00051\bc\u0001\u00172oB\u0011A\u000e\u001f\u0003\u0006s\u001e\u0011\ra\u001c\u0002\u0002)\u0006AAo\\*ue&tw\rF\u0001R\u0003\u0011\u0019w\u000e]=\u0015\u0007qsx\u0010C\u0004J\u0013A\u0005\t\u0019A&\t\u000f=K\u0001\u0013!a\u0001#\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nTCAA\u0003U\rY\u0015qA\u0016\u0003\u0003\u0013\u0001B!a\u0003\u0002\u00165\u0011\u0011Q\u0002\u0006\u0005\u0003\u001f\t\t\"A\u0005v]\u000eDWmY6fI*\u0019\u00111C\u001b\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002\u0018\u00055!!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012TCAA\u000fU\r\t\u0016qA\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005\r\u0002c\u0001\u0013\u0002&%\u0011q+J\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003W\u00012\u0001NA\u0017\u0013\r\ty#\u000e\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004a\u0006U\u0002\"CA\u001c\u001d\u0005\u0005\t\u0019AA\u0016\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011Q\b\t\u0006\u0003\u007f\t)\u0005]\u0007\u0003\u0003\u0003R1!a\u00116\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003\u000f\n\tE\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA'\u0003'\u00022\u0001NA(\u0013\r\t\t&\u000e\u0002\b\u0005>|G.Z1o\u0011!\t9\u0004EA\u0001\u0002\u0004\u0001\u0018A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!a\t\u0002Z!I\u0011qG\t\u0002\u0002\u0003\u0007\u00111F\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u00111F\u0001\u0007KF,\u0018\r\\:\u0015\t\u00055\u00131\r\u0005\t\u0003o\u0019\u0012\u0011!a\u0001a\"\u001a\u0001!a\u001a\u0011\t\u0005%\u0014QN\u0007\u0003\u0003WR1!a\u0005!\u0013\u0011\ty'a\u001b\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\u000f\u000bJ\u0014xN\u001d)vE2L7\u000f[3s!\tiVcE\u0003\u0016\u0003o\n\u0019\tE\u0004\u0002z\u0005}4*\u0015/\u000e\u0005\u0005m$bAA?k\u00059!/\u001e8uS6,\u0017\u0002BAA\u0003w\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c83!\u0011\t))a#\u000e\u0005\u0005\u001d%bAAEO\u0005\u0011\u0011n\\\u0005\u0004\u000f\u0006\u001dECAA:)\t\t\u0019\u0003F\u0003]\u0003'\u000b)\nC\u0003J1\u0001\u00071\nC\u0003P1\u0001\u0007\u0011+A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005m\u0015q\u0015\t\u0006i\u0005u\u0015\u0011U\u0005\u0004\u0003?+$AB(qi&|g\u000eE\u00035\u0003G[\u0015+C\u0002\u0002&V\u0012a\u0001V;qY\u0016\u0014\u0004\u0002CAU3\u0005\u0005\t\u0019\u0001/\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u0001$\u0001")
@InternalApi
public final class ErrorPublisher
implements Publisher<Nothing$>,
Product,
Serializable {
    private final Throwable t;
    private final String name;

    public static Option<Tuple2<Throwable, String>> unapply(ErrorPublisher x$0) {
        return ErrorPublisher$.MODULE$.unapply(x$0);
    }

    public static Function1<Tuple2<Throwable, String>, ErrorPublisher> tupled() {
        return ErrorPublisher$.MODULE$.tupled();
    }

    public static Function1<Throwable, Function1<String, ErrorPublisher>> curried() {
        return ErrorPublisher$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Throwable t() {
        return this.t;
    }

    public String name() {
        return this.name;
    }

    @Override
    public void subscribe(Subscriber<?> subscriber) {
        try {
            ReactiveStreamsCompliance$.MODULE$.requireNonNullSubscriber(subscriber);
            ReactiveStreamsCompliance$.MODULE$.tryOnSubscribe(subscriber, CancelledSubscription$.MODULE$);
            ReactiveStreamsCompliance$.MODULE$.tryOnError(subscriber, this.t());
        }
        catch (Throwable throwable) {
            Throwable throwable2 = throwable;
            if (throwable2 instanceof ReactiveStreamsCompliance.SpecViolation) {
            }
            throw throwable;
        }
    }

    public <T> Publisher<T> apply() {
        return this;
    }

    public String toString() {
        return this.name();
    }

    public ErrorPublisher copy(Throwable t2, String name) {
        return new ErrorPublisher(t2, name);
    }

    public Throwable copy$default$1() {
        return this.t();
    }

    public String copy$default$2() {
        return this.name();
    }

    @Override
    public String productPrefix() {
        return "ErrorPublisher";
    }

    @Override
    public int productArity() {
        return 2;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.t();
            }
            case 1: {
                return this.name();
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
        return x$1 instanceof ErrorPublisher;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "t";
            }
            case 1: {
                return "name";
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
        if (!(object instanceof ErrorPublisher)) return false;
        boolean bl = true;
        if (!bl) return false;
        ErrorPublisher errorPublisher = (ErrorPublisher)x$1;
        Throwable throwable = this.t();
        Throwable throwable2 = errorPublisher.t();
        if (throwable == null) {
            if (throwable2 != null) {
                return false;
            }
        } else if (!throwable.equals(throwable2)) return false;
        String string = this.name();
        String string2 = errorPublisher.name();
        if (string == null) {
            if (string2 == null) return true;
            return false;
        } else {
            if (!string.equals(string2)) return false;
            return true;
        }
    }

    public ErrorPublisher(Throwable t2, String name) {
        this.t = t2;
        this.name = name;
        Product.$init$(this);
        ReactiveStreamsCompliance$.MODULE$.requireNonNullElement(t2);
    }
}

