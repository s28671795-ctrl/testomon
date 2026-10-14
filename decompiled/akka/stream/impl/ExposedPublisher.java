/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.DeadLetterSuppression;
import akka.actor.NoSerializationVerificationNeeded;
import akka.annotation.InternalApi;
import akka.stream.impl.ActorPublisher;
import akka.stream.impl.ExposedPublisher$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Mc!\u0002\f\u0018\u0005ni\u0002\u0002C\u001f\u0001\u0005+\u0007I\u0011\u0001 \t\u0011\u0019\u0003!\u0011#Q\u0001\n}BQa\u0012\u0001\u0005\u0002!Cqa\u0013\u0001\u0002\u0002\u0013\u0005A\nC\u0004O\u0001E\u0005I\u0011A(\t\u000fi\u0003\u0011\u0011!C!7\"9A\rAA\u0001\n\u0003)\u0007bB5\u0001\u0003\u0003%\tA\u001b\u0005\b[\u0002\t\t\u0011\"\u0011o\u0011\u001d)\b!!A\u0005\u0002YDqa\u001f\u0001\u0002\u0002\u0013\u0005C\u0010C\u0004\u007f\u0001\u0005\u0005I\u0011I@\t\u0013\u0005\u0005\u0001!!A\u0005B\u0005\r\u0001\"CA\u0003\u0001\u0005\u0005I\u0011IA\u0004\u000f)\t9bFA\u0001\u0012\u0003Y\u0012\u0011\u0004\u0004\n-]\t\t\u0011#\u0001\u001c\u00037Aaa\u0012\t\u0005\u0002\u0005M\u0002\"CA\u0001!\u0005\u0005IQIA\u0002\u0011%\t)\u0004EA\u0001\n\u0003\u000b9\u0004C\u0005\u0002<A\t\t\u0011\"!\u0002>!I\u0011\u0011\n\t\u0002\u0002\u0013%\u00111\n\u0002\u0011\u000bb\u0004xn]3e!V\u0014G.[:iKJT!\u0001G\r\u0002\t%l\u0007\u000f\u001c\u0006\u00035m\taa\u001d;sK\u0006l'\"\u0001\u000f\u0002\t\u0005\\7.Y\n\u0007\u0001y!#&\f\u0019\u0011\u0005}\u0011S\"\u0001\u0011\u000b\u0003\u0005\nQa]2bY\u0006L!a\t\u0011\u0003\r\u0005s\u0017PU3g!\t)\u0003&D\u0001'\u0015\t93$A\u0003bGR|'/\u0003\u0002*M\t)B)Z1e\u0019\u0016$H/\u001a:TkB\u0004(/Z:tS>t\u0007CA\u0013,\u0013\tacEA\u0011O_N+'/[1mSj\fG/[8o-\u0016\u0014\u0018NZ5dCRLwN\u001c(fK\u0012,G\r\u0005\u0002 ]%\u0011q\u0006\t\u0002\b!J|G-^2u!\t\t$H\u0004\u00023q9\u00111gN\u0007\u0002i)\u0011QGN\u0001\u0007yI|w\u000e\u001e \u0004\u0001%\t\u0011%\u0003\u0002:A\u00059\u0001/Y2lC\u001e,\u0017BA\u001e=\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\tI\u0004%A\u0005qk\nd\u0017n\u001d5feV\tq\bE\u0002A\u0003\u000ek\u0011aF\u0005\u0003\u0005^\u0011a\"Q2u_J\u0004VO\u00197jg\",'\u000f\u0005\u0002 \t&\u0011Q\t\t\u0002\u0004\u0003:L\u0018A\u00039vE2L7\u000f[3sA\u00051A(\u001b8jiz\"\"!\u0013&\u0011\u0005\u0001\u0003\u0001\"B\u001f\u0004\u0001\u0004y\u0014\u0001B2paf$\"!S'\t\u000fu\"\u0001\u0013!a\u0001\u007f\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#\u0001)+\u0005}\n6&\u0001*\u0011\u0005MCV\"\u0001+\u000b\u0005U3\u0016!C;oG\",7m[3e\u0015\t9\u0006%\u0001\u0006b]:|G/\u0019;j_:L!!\u0017+\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u00029B\u0011QLY\u0007\u0002=*\u0011q\fY\u0001\u0005Y\u0006twMC\u0001b\u0003\u0011Q\u0017M^1\n\u0005\rt&AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001g!\tyr-\u0003\u0002iA\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u00111i\u001b\u0005\bY\"\t\t\u00111\u0001g\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\tq\u000eE\u0002qg\u000ek\u0011!\u001d\u0006\u0003e\u0002\n!bY8mY\u0016\u001cG/[8o\u0013\t!\u0018O\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dGCA<{!\ty\u00020\u0003\u0002zA\t9!i\\8mK\u0006t\u0007b\u00027\u000b\u0003\u0003\u0005\raQ\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0002]{\"9AnCA\u0001\u0002\u00041\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003\u0019\f\u0001\u0002^8TiJLgn\u001a\u000b\u00029\u00061Q-];bYN$2a^A\u0005\u0011\u001dag\"!AA\u0002\rC3\u0001AA\u0007!\u0011\ty!a\u0005\u000e\u0005\u0005E!BA,\u001c\u0013\u0011\t)\"!\u0005\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\u0011\u000bb\u0004xn]3e!V\u0014G.[:iKJ\u0004\"\u0001\u0011\t\u0014\u000bA\ti\"!\u000b\u0011\r\u0005}\u0011QE J\u001b\t\t\tCC\u0002\u0002$\u0001\nqA];oi&lW-\u0003\u0003\u0002(\u0005\u0005\"!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8ocA!\u00111FA\u0019\u001b\t\tiCC\u0002\u00020\u0001\f!![8\n\u0007m\ni\u0003\u0006\u0002\u0002\u001a\u0005)\u0011\r\u001d9msR\u0019\u0011*!\u000f\t\u000bu\u001a\u0002\u0019A \u0002\u000fUt\u0017\r\u001d9msR!\u0011qHA#!\u0011y\u0012\u0011I \n\u0007\u0005\r\u0003E\u0001\u0004PaRLwN\u001c\u0005\t\u0003\u000f\"\u0012\u0011!a\u0001\u0013\u0006\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u00055\u0003cA/\u0002P%\u0019\u0011\u0011\u000b0\u0003\r=\u0013'.Z2u\u0001")
@InternalApi
public final class ExposedPublisher
implements DeadLetterSuppression,
NoSerializationVerificationNeeded,
Product,
Serializable {
    private final ActorPublisher<Object> publisher;

    public static Option<ActorPublisher<Object>> unapply(ExposedPublisher x$0) {
        return ExposedPublisher$.MODULE$.unapply(x$0);
    }

    public static ExposedPublisher apply(ActorPublisher<Object> publisher) {
        return ExposedPublisher$.MODULE$.apply(publisher);
    }

    public static <A> Function1<ActorPublisher<Object>, A> andThen(Function1<ExposedPublisher, A> g2) {
        return ExposedPublisher$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, ExposedPublisher> compose(Function1<A, ActorPublisher<Object>> g2) {
        return ExposedPublisher$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public ActorPublisher<Object> publisher() {
        return this.publisher;
    }

    public ExposedPublisher copy(ActorPublisher<Object> publisher) {
        return new ExposedPublisher(publisher);
    }

    public ActorPublisher<Object> copy$default$1() {
        return this.publisher();
    }

    @Override
    public String productPrefix() {
        return "ExposedPublisher";
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
                return this.publisher();
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
        return x$1 instanceof ExposedPublisher;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "publisher";
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
        if (!(object instanceof ExposedPublisher)) return false;
        boolean bl = true;
        if (!bl) return false;
        ExposedPublisher exposedPublisher = (ExposedPublisher)x$1;
        ActorPublisher<Object> actorPublisher = this.publisher();
        ActorPublisher<Object> actorPublisher2 = exposedPublisher.publisher();
        if (actorPublisher != null) {
            if (!actorPublisher.equals(actorPublisher2)) return false;
            return true;
        }
        if (actorPublisher2 == null) return true;
        return false;
    }

    public ExposedPublisher(ActorPublisher<Object> publisher) {
        this.publisher = publisher;
        Product.$init$(this);
    }
}

