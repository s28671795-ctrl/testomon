/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.ContextualTypedActorFactory$;
import akka.actor.TypedActorExtension;
import akka.actor.TypedActorFactory;
import akka.actor.TypedProps;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Ud\u0001B\u000e\u001d\u0005\u0006B\u0001b\u000f\u0001\u0003\u0016\u0004%\t\u0001\u0010\u0005\t\u0001\u0002\u0011\t\u0012)A\u0005{!A\u0011\t\u0001BK\u0002\u0013\u0005!\t\u0003\u0005G\u0001\tE\t\u0015!\u0003D\u0011\u00159\u0005\u0001\"\u0001I\u0011\u0015a\u0005\u0001\"\u0011N\u0011\u0015\u0019\u0006\u0001\"\u0011U\u0011\u001dQ\u0006!!A\u0005\u0002mCqA\u0018\u0001\u0012\u0002\u0013\u0005q\fC\u0004k\u0001E\u0005I\u0011A6\t\u000f5\u0004\u0011\u0011!C!]\"9q\u000fAA\u0001\n\u0003A\bb\u0002?\u0001\u0003\u0003%\t! \u0005\n\u0003\u000f\u0001\u0011\u0011!C!\u0003\u0013A\u0011\"a\u0006\u0001\u0003\u0003%\t!!\u0007\t\u0013\u0005u\u0001!!A\u0005B\u0005}\u0001\"CA\u0012\u0001\u0005\u0005I\u0011IA\u0013\u0011%\t9\u0003AA\u0001\n\u0003\nI\u0003C\u0005\u0002,\u0001\t\t\u0011\"\u0011\u0002.\u001dI\u0011\u0011\u0007\u000f\u0002\u0002#\u0005\u00111\u0007\u0004\t7q\t\t\u0011#\u0001\u00026!1q)\u0006C\u0001\u0003\u001bB\u0011\"a\n\u0016\u0003\u0003%)%!\u000b\t\u0013\u0005=S#!A\u0005\u0002\u0006E\u0003\"CA,+\u0005\u0005I\u0011QA-\u0011%\tY'FA\u0001\n\u0013\tiGA\u000eD_:$X\r\u001f;vC2$\u0016\u0010]3e\u0003\u000e$xN\u001d$bGR|'/\u001f\u0006\u0003;y\tQ!Y2u_JT\u0011aH\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u000b\u0001\u0011\u0003\u0006L\u0018\u0011\u0005\r2S\"\u0001\u0013\u000b\u0003\u0015\nQa]2bY\u0006L!a\n\u0013\u0003\r\u0005s\u0017PU3g!\tI#&D\u0001\u001d\u0013\tYCDA\tUsB,G-Q2u_J4\u0015m\u0019;pef\u0004\"aI\u0017\n\u00059\"#a\u0002)s_\u0012,8\r\u001e\t\u0003aar!!\r\u001c\u000f\u0005I*T\"A\u001a\u000b\u0005Q\u0002\u0013A\u0002\u001fs_>$h(C\u0001&\u0013\t9D%A\u0004qC\u000e\\\u0017mZ3\n\u0005eR$\u0001D*fe&\fG.\u001b>bE2,'BA\u001c%\u0003)!\u0018\u0010]3e\u0003\u000e$xN]\u000b\u0002{A\u0011\u0011FP\u0005\u0003\u007fq\u00111\u0003V=qK\u0012\f5\r^8s\u000bb$XM\\:j_:\f1\u0002^=qK\u0012\f5\r^8sA\u0005a\u0011m\u0019;pe\u001a\u000b7\r^8ssV\t1\t\u0005\u0002*\t&\u0011Q\t\b\u0002\r\u0003\u000e$xN]\"p]R,\u0007\u0010^\u0001\u000eC\u000e$xN\u001d$bGR|'/\u001f\u0011\u0002\rqJg.\u001b;?)\rI%j\u0013\t\u0003S\u0001AQaO\u0003A\u0002uBQ!Q\u0003A\u0002\r\u000babZ3u\u0003\u000e$xN\u001d*fM\u001a{'\u000f\u0006\u0002O#B\u0011\u0011fT\u0005\u0003!r\u0011\u0001\"Q2u_J\u0014VM\u001a\u0005\u0006%\u001a\u0001\rAI\u0001\u0006aJ|\u00070_\u0001\rSN$\u0016\u0010]3e\u0003\u000e$xN\u001d\u000b\u0003+b\u0003\"a\t,\n\u0005]##a\u0002\"p_2,\u0017M\u001c\u0005\u00063\u001e\u0001\rAI\u0001\u000baJ|\u00070_(s\u001d>$\u0018\u0001B2paf$2!\u0013/^\u0011\u001dY\u0004\u0002%AA\u0002uBq!\u0011\u0005\u0011\u0002\u0003\u00071)\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003\u0001T#!P1,\u0003\t\u0004\"a\u00195\u000e\u0003\u0011T!!\u001a4\u0002\u0013Ut7\r[3dW\u0016$'BA4%\u0003)\tgN\\8uCRLwN\\\u0005\u0003S\u0012\u0014\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\u0012\u0001\u001c\u0016\u0003\u0007\u0006\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#A8\u0011\u0005A,X\"A9\u000b\u0005I\u001c\u0018\u0001\u00027b]\u001eT\u0011\u0001^\u0001\u0005U\u00064\u0018-\u0003\u0002wc\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012!\u001f\t\u0003GiL!a\u001f\u0013\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0007y\f\u0019\u0001\u0005\u0002$\u007f&\u0019\u0011\u0011\u0001\u0013\u0003\u0007\u0005s\u0017\u0010\u0003\u0005\u0002\u00065\t\t\u00111\u0001z\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u00111\u0002\t\u0006\u0003\u001b\t\u0019B`\u0007\u0003\u0003\u001fQ1!!\u0005%\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003+\tyA\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dGcA+\u0002\u001c!A\u0011QA\b\u0002\u0002\u0003\u0007a0\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,GcA8\u0002\"!A\u0011Q\u0001\t\u0002\u0002\u0003\u0007\u00110\u0001\u0005iCND7i\u001c3f)\u0005I\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003=\fa!Z9vC2\u001cHcA+\u00020!A\u0011QA\n\u0002\u0002\u0003\u0007a0A\u000eD_:$X\r\u001f;vC2$\u0016\u0010]3e\u0003\u000e$xN\u001d$bGR|'/\u001f\t\u0003SU\u0019R!FA\u001c\u0003\u0007\u0002r!!\u000f\u0002@u\u001a\u0015*\u0004\u0002\u0002<)\u0019\u0011Q\b\u0013\u0002\u000fI,h\u000e^5nK&!\u0011\u0011IA\u001e\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|gN\r\t\u0005\u0003\u000b\nY%\u0004\u0002\u0002H)\u0019\u0011\u0011J:\u0002\u0005%|\u0017bA\u001d\u0002HQ\u0011\u00111G\u0001\u0006CB\u0004H.\u001f\u000b\u0006\u0013\u0006M\u0013Q\u000b\u0005\u0006wa\u0001\r!\u0010\u0005\u0006\u0003b\u0001\raQ\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\tY&a\u001a\u0011\u000b\r\ni&!\u0019\n\u0007\u0005}CE\u0001\u0004PaRLwN\u001c\t\u0006G\u0005\rThQ\u0005\u0004\u0003K\"#A\u0002+va2,'\u0007\u0003\u0005\u0002je\t\t\u00111\u0001J\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003_\u00022\u0001]A9\u0013\r\t\u0019(\u001d\u0002\u0007\u001f\nTWm\u0019;")
public final class ContextualTypedActorFactory
implements TypedActorFactory,
Product,
Serializable {
    private final TypedActorExtension typedActor;
    private final ActorContext actorFactory;

    public static Option<Tuple2<TypedActorExtension, ActorContext>> unapply(ContextualTypedActorFactory x$0) {
        return ContextualTypedActorFactory$.MODULE$.unapply(x$0);
    }

    public static ContextualTypedActorFactory apply(TypedActorExtension typedActor, ActorContext actorFactory) {
        return ContextualTypedActorFactory$.MODULE$.apply(typedActor, actorFactory);
    }

    public static Function1<Tuple2<TypedActorExtension, ActorContext>, ContextualTypedActorFactory> tupled() {
        return ContextualTypedActorFactory$.MODULE$.tupled();
    }

    public static Function1<TypedActorExtension, Function1<ActorContext, ContextualTypedActorFactory>> curried() {
        return ContextualTypedActorFactory$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public boolean stop(Object proxy) {
        return TypedActorFactory.stop$(this, proxy);
    }

    @Override
    public boolean poisonPill(Object proxy) {
        return TypedActorFactory.poisonPill$(this, proxy);
    }

    @Override
    public <R, T extends R> R typedActorOf(TypedProps<T> props) {
        return (R)TypedActorFactory.typedActorOf$(this, props);
    }

    @Override
    public <R, T extends R> R typedActorOf(TypedProps<T> props, String name) {
        return (R)TypedActorFactory.typedActorOf$((TypedActorFactory)this, props, name);
    }

    @Override
    public <R, T extends R> R typedActorOf(TypedProps<T> props, ActorRef actorRef2) {
        return (R)TypedActorFactory.typedActorOf$((TypedActorFactory)this, props, actorRef2);
    }

    @Override
    public TypedActorExtension typedActor() {
        return this.typedActor;
    }

    @Override
    public ActorContext actorFactory() {
        return this.actorFactory;
    }

    @Override
    public ActorRef getActorRefFor(Object proxy) {
        return this.typedActor().getActorRefFor(proxy);
    }

    @Override
    public boolean isTypedActor(Object proxyOrNot) {
        return this.typedActor().isTypedActor(proxyOrNot);
    }

    public ContextualTypedActorFactory copy(TypedActorExtension typedActor, ActorContext actorFactory) {
        return new ContextualTypedActorFactory(typedActor, actorFactory);
    }

    public TypedActorExtension copy$default$1() {
        return this.typedActor();
    }

    public ActorContext copy$default$2() {
        return this.actorFactory();
    }

    @Override
    public String productPrefix() {
        return "ContextualTypedActorFactory";
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
                return this.typedActor();
            }
            case 1: {
                return this.actorFactory();
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
        return x$1 instanceof ContextualTypedActorFactory;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "typedActor";
            }
            case 1: {
                return "actorFactory";
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
        if (!(object instanceof ContextualTypedActorFactory)) return false;
        boolean bl = true;
        if (!bl) return false;
        ContextualTypedActorFactory contextualTypedActorFactory = (ContextualTypedActorFactory)x$1;
        TypedActorExtension typedActorExtension = this.typedActor();
        TypedActorExtension typedActorExtension2 = contextualTypedActorFactory.typedActor();
        if (typedActorExtension == null) {
            if (typedActorExtension2 != null) {
                return false;
            }
        } else if (!typedActorExtension.equals(typedActorExtension2)) return false;
        ActorContext actorContext = this.actorFactory();
        ActorContext actorContext2 = contextualTypedActorFactory.actorFactory();
        if (actorContext == null) {
            if (actorContext2 == null) return true;
            return false;
        } else {
            if (!actorContext.equals(actorContext2)) return false;
            return true;
        }
    }

    public ContextualTypedActorFactory(TypedActorExtension typedActor, ActorContext actorFactory) {
        this.typedActor = typedActor;
        this.actorFactory = actorFactory;
        TypedActorFactory.$init$(this);
        Product.$init$(this);
    }
}

