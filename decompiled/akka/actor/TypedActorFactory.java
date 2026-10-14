/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.ActorRefFactory;
import akka.actor.InternalActorRef;
import akka.actor.PoisonPill$;
import akka.actor.Props;
import akka.actor.Props$;
import akka.actor.TypedActor;
import akka.actor.TypedActorExtension;
import akka.actor.TypedProps;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;
import scala.Function0;
import scala.collection.immutable.Seq;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m4qa\u0003\u0007\u0011\u0002\u0007\u0005\u0011\u0003C\u0003\u0019\u0001\u0011\u0005\u0011\u0004C\u0003\u001e\u0001\u0019Ea\u0004C\u0003$\u0001\u0019EA\u0005C\u0003)\u0001\u0011\u0005\u0011\u0006C\u00030\u0001\u0011\u0005\u0001\u0007C\u00033\u0001\u0019\u00051\u0007C\u00037\u0001\u0019\u0005q\u0007C\u0003=\u0001\u0011\u0005Q\bC\u0003=\u0001\u0011\u0005\u0001\u000bC\u0003=\u0001\u0011\u0005aMA\tUsB,G-Q2u_J4\u0015m\u0019;pefT!!\u0004\b\u0002\u000b\u0005\u001cGo\u001c:\u000b\u0003=\tA!Y6lC\u000e\u00011C\u0001\u0001\u0013!\t\u0019b#D\u0001\u0015\u0015\u0005)\u0012!B:dC2\f\u0017BA\f\u0015\u0005\u0019\te.\u001f*fM\u00061A%\u001b8ji\u0012\"\u0012A\u0007\t\u0003'mI!\u0001\b\u000b\u0003\tUs\u0017\u000e^\u0001\rC\u000e$xN\u001d$bGR|'/_\u000b\u0002?A\u0011\u0001%I\u0007\u0002\u0019%\u0011!\u0005\u0004\u0002\u0010\u0003\u000e$xN\u001d*fM\u001a\u000b7\r^8ss\u0006QA/\u001f9fI\u0006\u001bGo\u001c:\u0016\u0003\u0015\u0002\"\u0001\t\u0014\n\u0005\u001db!a\u0005+za\u0016$\u0017i\u0019;pe\u0016CH/\u001a8tS>t\u0017\u0001B:u_B$\"AK\u0017\u0011\u0005MY\u0013B\u0001\u0017\u0015\u0005\u001d\u0011un\u001c7fC:DQA\f\u0003A\u0002I\tQ\u0001\u001d:pqf\f!\u0002]8jg>t\u0007+\u001b7m)\tQ\u0013\u0007C\u0003/\u000b\u0001\u0007!#\u0001\u0007jgRK\b/\u001a3BGR|'\u000f\u0006\u0002+i!)QG\u0002a\u0001%\u0005Q\u0001O]8ys>\u0013hj\u001c;\u0002\u001d\u001d,G/Q2u_J\u0014VM\u001a$peR\u0011\u0001h\u000f\t\u0003AeJ!A\u000f\u0007\u0003\u0011\u0005\u001bGo\u001c:SK\u001aDQAL\u0004A\u0002I\tA\u0002^=qK\u0012\f5\r^8s\u001f\u001a,2AP!N)\tyt\t\u0005\u0002A\u00032\u0001A!\u0002\"\t\u0005\u0004\u0019%!\u0001*\u0012\u0005\u0011\u0013\u0002CA\nF\u0013\t1ECA\u0004O_RD\u0017N\\4\t\u000b!C\u0001\u0019A%\u0002\u000bA\u0014x\u000e]:\u0011\u0007\u0001RE*\u0003\u0002L\u0019\tQA+\u001f9fIB\u0013x\u000e]:\u0011\u0005\u0001kE!\u0002(\t\u0005\u0004y%!\u0001+\u0012\u0005\u0011{TcA)T/R\u0019!\u000bV-\u0011\u0005\u0001\u001bF!\u0002\"\n\u0005\u0004\u0019\u0005\"\u0002%\n\u0001\u0004)\u0006c\u0001\u0011K-B\u0011\u0001i\u0016\u0003\u0006\u001d&\u0011\r\u0001W\t\u0003\tJCQAW\u0005A\u0002m\u000bAA\\1nKB\u0011Al\u0019\b\u0003;\u0006\u0004\"A\u0018\u000b\u000e\u0003}S!\u0001\u0019\t\u0002\rq\u0012xn\u001c;?\u0013\t\u0011G#\u0001\u0004Qe\u0016$WMZ\u0005\u0003I\u0016\u0014aa\u0015;sS:<'B\u00012\u0015+\r9\u0017.\u001c\u000b\u0004Q*|\u0007C\u0001!j\t\u0015\u0011%B1\u0001D\u0011\u0015A%\u00021\u0001l!\r\u0001#\n\u001c\t\u0003\u00016$QA\u0014\u0006C\u00029\f\"\u0001\u00125\t\u000bAT\u0001\u0019\u0001\u001d\u0002\u0011\u0005\u001cGo\u001c:SK\u001aDc\u0001\u0001:vmbL\bCA\nt\u0013\t!HC\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-I\u0001x\u0003m)6/\u001a\u0011(C.\\\u0017ML1di>\u0014h\u0006^=qK\u0012<\u0003%\u0011)J]\u0005)1/\u001b8dK\u0006\n!0A\u00033]Yr\u0003\u0007")
public interface TypedActorFactory {
    public ActorRefFactory actorFactory();

    public TypedActorExtension typedActor();

    public static /* synthetic */ boolean stop$(TypedActorFactory $this, Object proxy) {
        return $this.stop(proxy);
    }

    default public boolean stop(Object proxy) {
        ActorRef actorRef2 = this.getActorRefFor(proxy);
        if (actorRef2 == null) {
            return false;
        }
        ((InternalActorRef)actorRef2).stop();
        return true;
    }

    public static /* synthetic */ boolean poisonPill$(TypedActorFactory $this, Object proxy) {
        return $this.poisonPill(proxy);
    }

    default public boolean poisonPill(Object proxy) {
        ActorRef actorRef2 = this.getActorRefFor(proxy);
        if (actorRef2 == null) {
            return false;
        }
        PoisonPill$ x$1 = PoisonPill$.MODULE$;
        ActorRef x$2 = actorRef2.$bang$default$2(x$1);
        actorRef2.$bang(x$1, x$2);
        return true;
    }

    public boolean isTypedActor(Object var1);

    public ActorRef getActorRefFor(Object var1);

    public static /* synthetic */ Object typedActorOf$(TypedActorFactory $this, TypedProps props) {
        return $this.typedActorOf(props);
    }

    default public <R, T extends R> R typedActorOf(TypedProps<T> props) {
        AtomicReference proxyVar = new AtomicReference();
        Function0 c = props.creator();
        Seq<Class<?>> i = props.interfaces();
        Props ap = Props$.MODULE$.apply((Function0<TypedActor.TypedActor> & Serializable)() -> new TypedActor.TypedActor(proxyVar, c, i), ClassTag$.MODULE$.apply(TypedActor.TypedActor.class)).withDeploy(props.actorProps().deploy());
        return (R)this.typedActor().createActorRefProxy(props, proxyVar, (Function0<ActorRef> & Serializable)() -> this.actorFactory().actorOf(ap));
    }

    public static /* synthetic */ Object typedActorOf$(TypedActorFactory $this, TypedProps props, String name) {
        return $this.typedActorOf(props, name);
    }

    default public <R, T extends R> R typedActorOf(TypedProps<T> props, String name) {
        AtomicReference proxyVar = new AtomicReference();
        Function0 c = props.creator();
        Seq<Class<?>> i = props.interfaces();
        Props ap = Props$.MODULE$.apply((Function0<TypedActor.TypedActor> & Serializable)() -> new TypedActor.TypedActor(proxyVar, c, i), ClassTag$.MODULE$.apply(TypedActor.TypedActor.class)).withDeploy(props.actorProps().deploy());
        return (R)this.typedActor().createActorRefProxy(props, proxyVar, (Function0<ActorRef> & Serializable)() -> this.actorFactory().actorOf(ap, name));
    }

    public static /* synthetic */ Object typedActorOf$(TypedActorFactory $this, TypedProps props, ActorRef actorRef2) {
        return $this.typedActorOf(props, actorRef2);
    }

    default public <R, T extends R> R typedActorOf(TypedProps<T> props, ActorRef actorRef2) {
        return this.typedActor().createActorRefProxy(props, null, (Function0<ActorRef> & Serializable)() -> actorRef2);
    }

    public static void $init$(TypedActorFactory $this) {
    }
}

