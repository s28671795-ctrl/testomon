/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.AutoReceivedMessage;
import akka.actor.DeadLetterSuppression;
import akka.actor.NoSerializationVerificationNeeded;
import akka.actor.PossiblyHarmful;
import akka.actor.Terminated$;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Ue\u0001B\u000f\u001f\u0005\u000eB\u0001b\b\u0001\u0003\u0016\u0004%\tA\u0012\u0005\t\u0015\u0002\u0011\t\u0012)A\u0005\u000f\")!\u000b\u0001C\u0001'\"Aa\u000b\u0001BC\u0002\u0013\u0005q\u000b\u0003\u0005\\\u0001\t\u0005\t\u0015!\u0003Y\u0011\u0015i\u0006\u0001\"\u0001_\u0011!\u0001\u0007A!b\u0001\n\u00039\u0006\u0002C1\u0001\u0005\u0003\u0005\u000b\u0011\u0002-\t\u000b\r\u0004A\u0011\u00010\t\r\u0015\u0004A\u0011\u0001\u0011g\u0011\u001da\u0007!!A\u0005\u00025DqA\u001d\u0001\u0012\u0002\u0013\u00051\u000fC\u0004\u007f\u0001\u0005\u0005I\u0011I@\t\u0013\u0005E\u0001!!A\u0005\u0002\u0005M\u0001\"CA\u000e\u0001\u0005\u0005I\u0011AA\u000f\u0011%\tI\u0003AA\u0001\n\u0003\nY\u0003C\u0005\u0002:\u0001\t\t\u0011\"\u0001\u0002<!I\u0011q\b\u0001\u0002\u0002\u0013\u0005\u0013\u0011\t\u0005\n\u0003\u000b\u0002\u0011\u0011!C!\u0003\u000fB\u0011\"!\u0013\u0001\u0003\u0003%\t%a\u0013\t\u0013\u00055\u0003!!A\u0005B\u0005=s!CA0=\u0005\u0005\t\u0012AA1\r!ib$!A\t\u0002\u0005\r\u0004BB3\u0018\t\u0003\ty\u0007C\u0005\u0002J]\t\t\u0011\"\u0012\u0002L!I\u0011\u0011O\f\u0002\u0002\u0013\u0005\u00151\u000f\u0005\n\u0003{:\u0012\u0011!CA\u0003\u007fB\u0011\"a#\u0018\u0003\u0003%I!!$\u0003\u0015Q+'/\\5oCR,GM\u0003\u0002 A\u0005)\u0011m\u0019;pe*\t\u0011%\u0001\u0003bW.\f7\u0001A\n\t\u0001\u0011Rc&\r\u001b8uA\u0011Q\u0005K\u0007\u0002M)\tq%A\u0003tG\u0006d\u0017-\u0003\u0002*M\t1\u0011I\\=SK\u001a\u0004\"a\u000b\u0017\u000e\u0003yI!!\f\u0010\u0003'\u0005+Ho\u001c*fG\u0016Lg/\u001a3NKN\u001c\u0018mZ3\u0011\u0005-z\u0013B\u0001\u0019\u001f\u0005=\u0001vn]:jE2L\b*\u0019:nMVd\u0007CA\u00163\u0013\t\u0019dDA\u000bEK\u0006$G*\u001a;uKJ\u001cV\u000f\u001d9sKN\u001c\u0018n\u001c8\u0011\u0005-*\u0014B\u0001\u001c\u001f\u0005\u0005runU3sS\u0006d\u0017N_1uS>tg+\u001a:jM&\u001c\u0017\r^5p]:+W\rZ3e!\t)\u0003(\u0003\u0002:M\t9\u0001K]8ek\u000e$\bCA\u001eD\u001d\ta\u0014I\u0004\u0002>\u00016\taH\u0003\u0002@E\u00051AH]8pizJ\u0011aJ\u0005\u0003\u0005\u001a\nq\u0001]1dW\u0006<W-\u0003\u0002E\u000b\na1+\u001a:jC2L'0\u00192mK*\u0011!IJ\u000b\u0002\u000fB\u00111\u0006S\u0005\u0003\u0013z\u0011\u0001\"Q2u_J\u0014VMZ\u0001\u0007C\u000e$xN\u001d\u0011)\u0005\ta\u0005CA'Q\u001b\u0005q%BA('\u0003\u0015\u0011W-\u00198t\u0013\t\tfJ\u0001\u0007CK\u0006t\u0007K]8qKJ$\u00180\u0001\u0005hKR\f5\r^8s)\u00059\u0005FA\u0002V\u0017\u0001\t!#\u001a=jgR,gnY3D_:4\u0017N]7fIV\t\u0001\f\u0005\u0002&3&\u0011!L\n\u0002\b\u0005>|G.Z1o\u0003M)\u00070[:uK:\u001cWmQ8oM&\u0014X.\u001a3!Q\t)A*A\u000bhKR,\u00050[:uK:\u001cWmQ8oM&\u0014X.\u001a3\u0015\u0003aC#AB+\u0002#\u0005$GM]3tgR+'/\\5oCR,G-\u0001\nbI\u0012\u0014Xm]:UKJl\u0017N\\1uK\u0012\u0004\u0003F\u0001\u0005M\u0003Q9W\r^!eIJ,7o\u001d+fe6Lg.\u0019;fI\"\u0012\u0011\"V\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\u001d\\Gc\u00015jUB\u00111\u0006\u0001\u0005\u0006-*\u0001\r\u0001\u0017\u0005\u0006A*\u0001\r\u0001\u0017\u0005\u0006?)\u0001\raR\u0001\u0005G>\u0004\u0018\u0010\u0006\u0002ocR\u0019\u0001n\u001c9\t\u000bY[\u0001\u0019\u0001-\t\u000b\u0001\\\u0001\u0019\u0001-\t\u000f}Y\u0001\u0013!a\u0001\u000f\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#\u0001;+\u0005\u001d+8&\u0001<\u0011\u0005]dX\"\u0001=\u000b\u0005eT\u0018!C;oG\",7m[3e\u0015\tYh%\u0001\u0006b]:|G/\u0019;j_:L!! =\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003\u0003\u0001B!a\u0001\u0002\u000e5\u0011\u0011Q\u0001\u0006\u0005\u0003\u000f\tI!\u0001\u0003mC:<'BAA\u0006\u0003\u0011Q\u0017M^1\n\t\u0005=\u0011Q\u0001\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0005\u0005U\u0001cA\u0013\u0002\u0018%\u0019\u0011\u0011\u0004\u0014\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\u0005}\u0011Q\u0005\t\u0004K\u0005\u0005\u0012bAA\u0012M\t\u0019\u0011I\\=\t\u0013\u0005\u001dr\"!AA\u0002\u0005U\u0011a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002.A1\u0011qFA\u001b\u0003?i!!!\r\u000b\u0007\u0005Mb%\u0001\u0006d_2dWm\u0019;j_:LA!a\u000e\u00022\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\rA\u0016Q\b\u0005\n\u0003O\t\u0012\u0011!a\u0001\u0003?\t!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011\u0011AA\"\u0011%\t9CEA\u0001\u0002\u0004\t)\"\u0001\u0005iCND7i\u001c3f)\t\t)\"\u0001\u0005u_N#(/\u001b8h)\t\t\t!\u0001\u0004fcV\fGn\u001d\u000b\u00041\u0006E\u0003\"CA\u0014+\u0005\u0005\t\u0019AA\u0010Q\u001d\u0001\u0011QKA.\u0003;\u00022!JA,\u0013\r\tIF\n\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!A\u0001\u000b)\u0016\u0014X.\u001b8bi\u0016$\u0007CA\u0016\u0018'\u00119B%!\u001a\u0011\t\u0005\u001d\u0014QN\u0007\u0003\u0003SRA!a\u001b\u0002\n\u0005\u0011\u0011n\\\u0005\u0004\t\u0006%DCAA1\u0003\u0015\t\u0007\u000f\u001d7z)\u0011\t)(a\u001f\u0015\u000b!\f9(!\u001f\t\u000bYS\u0002\u0019\u0001-\t\u000b\u0001T\u0002\u0019\u0001-\t\u000b}Q\u0002\u0019A$\u0002\u000fUt\u0017\r\u001d9msR!\u0011\u0011QAD!\u0011)\u00131Q$\n\u0007\u0005\u0015eE\u0001\u0004PaRLwN\u001c\u0005\t\u0003\u0013[\u0012\u0011!a\u0001Q\u0006\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005=\u0005\u0003BA\u0002\u0003#KA!a%\u0002\u0006\t1qJ\u00196fGR\u0004")
public final class Terminated
implements AutoReceivedMessage,
PossiblyHarmful,
DeadLetterSuppression,
NoSerializationVerificationNeeded,
Product {
    private static final long serialVersionUID = 1L;
    private final ActorRef actor;
    private final boolean existenceConfirmed;
    private final boolean addressTerminated;

    public static Option<ActorRef> unapply(Terminated x$0) {
        return Terminated$.MODULE$.unapply(x$0);
    }

    public static Terminated apply(ActorRef actor, boolean existenceConfirmed, boolean addressTerminated) {
        return Terminated$.MODULE$.apply(actor, existenceConfirmed, addressTerminated);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public ActorRef actor() {
        return this.actor;
    }

    public boolean existenceConfirmed() {
        return this.existenceConfirmed;
    }

    public boolean addressTerminated() {
        return this.addressTerminated;
    }

    public Terminated copy(ActorRef actor, boolean existenceConfirmed, boolean addressTerminated) {
        return new Terminated(actor, existenceConfirmed, addressTerminated);
    }

    public ActorRef copy$default$1() {
        return this.actor();
    }

    public ActorRef getActor() {
        return this.actor();
    }

    public boolean getAddressTerminated() {
        return this.addressTerminated();
    }

    public boolean getExistenceConfirmed() {
        return this.existenceConfirmed();
    }

    @Override
    public String productPrefix() {
        return "Terminated";
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
                return this.actor();
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
        return x$1 instanceof Terminated;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "actor";
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
        if (!(object instanceof Terminated)) return false;
        boolean bl = true;
        if (!bl) return false;
        Terminated terminated = (Terminated)x$1;
        ActorRef actorRef2 = this.actor();
        ActorRef actorRef3 = terminated.actor();
        if (actorRef2 != null) {
            if (!((Object)actorRef2).equals(actorRef3)) return false;
            return true;
        }
        if (actorRef3 == null) return true;
        return false;
    }

    public Terminated(ActorRef actor, boolean existenceConfirmed, boolean addressTerminated) {
        this.actor = actor;
        this.existenceConfirmed = existenceConfirmed;
        this.addressTerminated = addressTerminated;
        Product.$init$(this);
    }
}

