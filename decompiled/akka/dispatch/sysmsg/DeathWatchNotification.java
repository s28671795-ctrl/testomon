/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.actor.ActorRef;
import akka.actor.DeadLetterSuppression;
import akka.dispatch.sysmsg.DeathWatchNotification$;
import akka.dispatch.sysmsg.SystemMessage;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005e!\u0002\u000f\u001e\u0005\u0006\u001a\u0003\u0002C\u0019\u0001\u0005+\u0007I\u0011\u0001#\t\u0011!\u0003!\u0011#Q\u0001\n\u0015C\u0001\"\u0013\u0001\u0003\u0016\u0004%\tA\u0013\u0005\t\u001d\u0002\u0011\t\u0012)A\u0005\u0017\"Aq\n\u0001BK\u0002\u0013\u0005!\n\u0003\u0005Q\u0001\tE\t\u0015!\u0003L\u0011\u0015\t\u0006\u0001\"\u0001S\u0011\u001d9\u0006!!A\u0005\u0002aCq\u0001\u0018\u0001\u0012\u0002\u0013\u0005Q\fC\u0004i\u0001E\u0005I\u0011A5\t\u000f-\u0004\u0011\u0013!C\u0001S\"9A\u000eAA\u0001\n\u0003j\u0007b\u0002<\u0001\u0003\u0003%\ta\u001e\u0005\bw\u0002\t\t\u0011\"\u0001}\u0011%\t)\u0001AA\u0001\n\u0003\n9\u0001C\u0005\u0002\u0016\u0001\t\t\u0011\"\u0001\u0002\u0018!I\u00111\u0004\u0001\u0002\u0002\u0013\u0005\u0013Q\u0004\u0005\n\u0003C\u0001\u0011\u0011!C!\u0003GA\u0011\"!\n\u0001\u0003\u0003%\t%a\n\t\u0013\u0005%\u0002!!A\u0005B\u0005-rACA\u001e;\u0005\u0005\t\u0012A\u0011\u0002>\u0019IA$HA\u0001\u0012\u0003\t\u0013q\b\u0005\u0007#Z!\t!a\u0016\t\u0013\u0005\u0015b#!A\u0005F\u0005\u001d\u0002\"CA--\u0005\u0005I\u0011QA.\u0011%\t\u0019GFA\u0001\n\u0003\u000b)\u0007C\u0005\u0002xY\t\t\u0011\"\u0003\u0002z\t1B)Z1uQ^\u000bGo\u00195O_RLg-[2bi&|gN\u0003\u0002\u001f?\u000511/_:ng\u001eT!\u0001I\u0011\u0002\u0011\u0011L7\u000f]1uG\"T\u0011AI\u0001\u0005C.\\\u0017m\u0005\u0004\u0001I)rCg\u000e\t\u0003K!j\u0011A\n\u0006\u0002O\u0005)1oY1mC&\u0011\u0011F\n\u0002\u0007\u0003:L(+\u001a4\u0011\u0005-bS\"A\u000f\n\u00055j\"!D*zgR,W.T3tg\u0006<W\r\u0005\u00020e5\t\u0001G\u0003\u00022C\u0005)\u0011m\u0019;pe&\u00111\u0007\r\u0002\u0016\t\u0016\fG\rT3ui\u0016\u00148+\u001e9qe\u0016\u001c8/[8o!\t)S'\u0003\u00027M\t9\u0001K]8ek\u000e$\bC\u0001\u001dB\u001d\tItH\u0004\u0002;}5\t1H\u0003\u0002={\u00051AH]8piz\u001a\u0001!C\u0001(\u0013\t\u0001e%A\u0004qC\u000e\\\u0017mZ3\n\u0005\t\u001b%\u0001D*fe&\fG.\u001b>bE2,'B\u0001!'+\u0005)\u0005CA\u0018G\u0013\t9\u0005G\u0001\u0005BGR|'OU3g\u0003\u0019\t7\r^8sA\u0005\u0011R\r_5ti\u0016t7-Z\"p]\u001aL'/\\3e+\u0005Y\u0005CA\u0013M\u0013\tieEA\u0004C_>dW-\u00198\u0002'\u0015D\u0018n\u001d;f]\u000e,7i\u001c8gSJlW\r\u001a\u0011\u0002#\u0005$GM]3tgR+'/\\5oCR,G-\u0001\nbI\u0012\u0014Xm]:UKJl\u0017N\\1uK\u0012\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0003T)V3\u0006CA\u0016\u0001\u0011\u0015\tt\u00011\u0001F\u0011\u0015Iu\u00011\u0001L\u0011\u0015yu\u00011\u0001L\u0003\u0011\u0019w\u000e]=\u0015\tMK&l\u0017\u0005\bc!\u0001\n\u00111\u0001F\u0011\u001dI\u0005\u0002%AA\u0002-Cqa\u0014\u0005\u0011\u0002\u0003\u00071*\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003yS#!R0,\u0003\u0001\u0004\"!\u00194\u000e\u0003\tT!a\u00193\u0002\u0013Ut7\r[3dW\u0016$'BA3'\u0003)\tgN\\8uCRLwN\\\u0005\u0003O\n\u0014\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\u0012A\u001b\u0016\u0003\u0017~\u000babY8qs\u0012\"WMZ1vYR$3'A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002]B\u0011q\u000e^\u0007\u0002a*\u0011\u0011O]\u0001\u0005Y\u0006twMC\u0001t\u0003\u0011Q\u0017M^1\n\u0005U\u0004(AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001y!\t)\u00130\u0003\u0002{M\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0019Q0!\u0001\u0011\u0005\u0015r\u0018BA@'\u0005\r\te.\u001f\u0005\t\u0003\u0007q\u0011\u0011!a\u0001q\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!!\u0003\u0011\u000b\u0005-\u0011\u0011C?\u000e\u0005\u00055!bAA\bM\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0005M\u0011Q\u0002\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000eF\u0002L\u00033A\u0001\"a\u0001\u0011\u0003\u0003\u0005\r!`\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\rF\u0002o\u0003?A\u0001\"a\u0001\u0012\u0003\u0003\u0005\r\u0001_\u0001\tQ\u0006\u001c\bnQ8eKR\t\u00010\u0001\u0005u_N#(/\u001b8h)\u0005q\u0017AB3rk\u0006d7\u000fF\u0002L\u0003[A\u0001\"a\u0001\u0015\u0003\u0003\u0005\r! \u0015\b\u0001\u0005E\u0012qGA\u001d!\r)\u00131G\u0005\u0004\u0003k1#\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\t\u0011A\u0006#fCRDw+\u0019;dQ:{G/\u001b4jG\u0006$\u0018n\u001c8\u0011\u0005-22#\u0002\f\u0002B\u00055\u0003\u0003CA\"\u0003\u0013*5jS*\u000e\u0005\u0005\u0015#bAA$M\u00059!/\u001e8uS6,\u0017\u0002BA&\u0003\u000b\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c84!\u0011\ty%!\u0016\u000e\u0005\u0005E#bAA*e\u0006\u0011\u0011n\\\u0005\u0004\u0005\u0006ECCAA\u001f\u0003\u0015\t\u0007\u000f\u001d7z)\u001d\u0019\u0016QLA0\u0003CBQ!M\rA\u0002\u0015CQ!S\rA\u0002-CQaT\rA\u0002-\u000bq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002h\u0005M\u0004#B\u0013\u0002j\u00055\u0014bAA6M\t1q\n\u001d;j_:\u0004b!JA8\u000b.[\u0015bAA9M\t1A+\u001e9mKNB\u0001\"!\u001e\u001b\u0003\u0003\u0005\raU\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAA>!\ry\u0017QP\u0005\u0004\u0003\u007f\u0002(AB(cU\u0016\u001cG\u000f")
public final class DeathWatchNotification
implements SystemMessage,
DeadLetterSuppression,
Product {
    private static final long serialVersionUID = 1L;
    private final ActorRef actor;
    private final boolean existenceConfirmed;
    private final boolean addressTerminated;
    private transient SystemMessage next;

    public static Option<Tuple3<ActorRef, Object, Object>> unapply(DeathWatchNotification x$0) {
        return DeathWatchNotification$.MODULE$.unapply(x$0);
    }

    public static DeathWatchNotification apply(ActorRef actor, boolean existenceConfirmed, boolean addressTerminated) {
        return DeathWatchNotification$.MODULE$.apply(actor, existenceConfirmed, addressTerminated);
    }

    public static Function1<Tuple3<ActorRef, Object, Object>, DeathWatchNotification> tupled() {
        return DeathWatchNotification$.MODULE$.tupled();
    }

    public static Function1<ActorRef, Function1<Object, Function1<Object, DeathWatchNotification>>> curried() {
        return DeathWatchNotification$.MODULE$.curried();
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

    public ActorRef actor() {
        return this.actor;
    }

    public boolean existenceConfirmed() {
        return this.existenceConfirmed;
    }

    public boolean addressTerminated() {
        return this.addressTerminated;
    }

    public DeathWatchNotification copy(ActorRef actor, boolean existenceConfirmed, boolean addressTerminated) {
        return new DeathWatchNotification(actor, existenceConfirmed, addressTerminated);
    }

    public ActorRef copy$default$1() {
        return this.actor();
    }

    public boolean copy$default$2() {
        return this.existenceConfirmed();
    }

    public boolean copy$default$3() {
        return this.addressTerminated();
    }

    @Override
    public String productPrefix() {
        return "DeathWatchNotification";
    }

    @Override
    public int productArity() {
        return 3;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.actor();
            }
            case 1: {
                return BoxesRunTime.boxToBoolean(this.existenceConfirmed());
            }
            case 2: {
                return BoxesRunTime.boxToBoolean(this.addressTerminated());
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
        return x$1 instanceof DeathWatchNotification;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "actor";
            }
            case 1: {
                return "existenceConfirmed";
            }
            case 2: {
                return "addressTerminated";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.actor()));
        n = Statics.mix(n, this.existenceConfirmed() ? 1231 : 1237);
        n = Statics.mix(n, this.addressTerminated() ? 1231 : 1237);
        return Statics.finalizeHash(n, 3);
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
        if (!(object instanceof DeathWatchNotification)) return false;
        boolean bl = true;
        if (!bl) return false;
        DeathWatchNotification deathWatchNotification = (DeathWatchNotification)x$1;
        if (this.existenceConfirmed() != deathWatchNotification.existenceConfirmed()) return false;
        if (this.addressTerminated() != deathWatchNotification.addressTerminated()) return false;
        ActorRef actorRef2 = this.actor();
        ActorRef actorRef3 = deathWatchNotification.actor();
        if (actorRef2 != null) {
            if (!((Object)actorRef2).equals(actorRef3)) return false;
            return true;
        }
        if (actorRef3 == null) return true;
        return false;
    }

    public DeathWatchNotification(ActorRef actor, boolean existenceConfirmed, boolean addressTerminated) {
        this.actor = actor;
        this.existenceConfirmed = existenceConfirmed;
        this.addressTerminated = addressTerminated;
        SystemMessage.$init$(this);
        Product.$init$(this);
    }
}

