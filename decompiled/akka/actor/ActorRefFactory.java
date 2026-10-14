/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorPath;
import akka.actor.ActorPathExtractor$;
import akka.actor.ActorRef;
import akka.actor.ActorRefProvider;
import akka.actor.ActorSelection;
import akka.actor.ActorSelection$;
import akka.actor.ActorSystemImpl;
import akka.actor.Address;
import akka.actor.InternalActorRef;
import akka.actor.Props;
import akka.actor.RelativeActorPath$;
import scala.Option;
import scala.Tuple2;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.Seq;
import scala.concurrent.ExecutionContextExecutor;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005%4q\u0001D\u0007\u0011\u0002\u0007\u0005!\u0003C\u0003\u001a\u0001\u0011\u0005!\u0004C\u0003\u001f\u0001\u0019Eq\u0004C\u0003%\u0001\u0019EQ\u0005C\u0003*\u0001\u0019\r!\u0006C\u00032\u0001\u0019E!\u0007C\u00037\u0001\u0019E!\u0007C\u00038\u0001\u0019\u0005\u0001\bC\u00038\u0001\u0019\u0005\u0011\tC\u0003Q\u0001\u0011\u0005\u0011\u000bC\u0003Q\u0001\u0011\u0005q\u000bC\u0003]\u0001\u0019\u0005QLA\bBGR|'OU3g\r\u0006\u001cGo\u001c:z\u0015\tqq\"A\u0003bGR|'OC\u0001\u0011\u0003\u0011\t7n[1\u0004\u0001M\u0011\u0001a\u0005\t\u0003)]i\u0011!\u0006\u0006\u0002-\u0005)1oY1mC&\u0011\u0001$\u0006\u0002\u0007\u0003:L(+\u001a4\u0002\r\u0011Jg.\u001b;%)\u0005Y\u0002C\u0001\u000b\u001d\u0013\tiRC\u0001\u0003V]&$\u0018AC:zgR,W.S7qYV\t\u0001\u0005\u0005\u0002\"E5\tQ\"\u0003\u0002$\u001b\ty\u0011i\u0019;peNK8\u000f^3n\u00136\u0004H.\u0001\u0005qe>4\u0018\u000eZ3s+\u00051\u0003CA\u0011(\u0013\tASB\u0001\tBGR|'OU3g!J|g/\u001b3fe\u0006QA-[:qCR\u001c\u0007.\u001a:\u0016\u0003-\u0002\"\u0001L\u0018\u000e\u00035R!AL\u000b\u0002\u0015\r|gnY;se\u0016tG/\u0003\u00021[\tAR\t_3dkRLwN\\\"p]R,\u0007\u0010^#yK\u000e,Ho\u001c:\u0002\u0011\u001d,\u0018M\u001d3jC:,\u0012a\r\t\u0003CQJ!!N\u0007\u0003!%sG/\u001a:oC2\f5\r^8s%\u00164\u0017A\u00037p_.,\bOU8pi\u00069\u0011m\u0019;pe>3GCA\u001d=!\t\t#(\u0003\u0002<\u001b\tA\u0011i\u0019;peJ+g\rC\u0003>\u000f\u0001\u0007a(A\u0003qe>\u00048\u000f\u0005\u0002\"\u007f%\u0011\u0001)\u0004\u0002\u0006!J|\u0007o\u001d\u000b\u0004s\t\u001b\u0005\"B\u001f\t\u0001\u0004q\u0004\"\u0002#\t\u0001\u0004)\u0015\u0001\u00028b[\u0016\u0004\"AR'\u000f\u0005\u001d[\u0005C\u0001%\u0016\u001b\u0005I%B\u0001&\u0012\u0003\u0019a$o\\8u}%\u0011A*F\u0001\u0007!J,G-\u001a4\n\u00059{%AB*ue&twM\u0003\u0002M+\u0005q\u0011m\u0019;peN+G.Z2uS>tGC\u0001*V!\t\t3+\u0003\u0002U\u001b\tq\u0011i\u0019;peN+G.Z2uS>t\u0007\"\u0002,\n\u0001\u0004)\u0015\u0001\u00029bi\"$\"A\u0015-\t\u000bYS\u0001\u0019A-\u0011\u0005\u0005R\u0016BA.\u000e\u0005%\t5\r^8s!\u0006$\b.\u0001\u0003ti>\u0004HCA\u000e_\u0011\u0015q1\u00021\u0001:Q\u0011\u0001\u0001MZ4\u0011\u0005\u0005$W\"\u00012\u000b\u0005\r,\u0012AC1o]>$\u0018\r^5p]&\u0011QM\u0019\u0002\u0011S6\u0004H.[2ji:{GOR8v]\u0012\f1!\\:hC\u0005A\u0017!a\fj[Bd\u0017nY5uA\u0005\u001bGo\u001c:SK\u001a4\u0015m\u0019;pef\u0004#/Z9vSJ,GM\u000f\u0011jM\u0002zW\u000f^:jI\u0016\u0004sN\u001a\u0011b]\u0002\n5\r^8sAe|W\u000f\t8fK\u0012\u0004\u0013M\u001c\u0011j[Bd\u0017nY5uA\u0005\u001bGo\u001c:TsN$X-\u001c\u0017!S:\u001c\u0018\u000eZ3!_\u001a\u0004\u0013M\u001c\u0011bGR|'\u000f\t;iSN\u00043\u000f[8vY\u0012\u0004#-\u001a\u0011uQ\u0016\u0004\u0013.\u001c9mS\u000eLG\u000fI!di>\u00148i\u001c8uKb$\b")
public interface ActorRefFactory {
    public ActorSystemImpl systemImpl();

    public ActorRefProvider provider();

    public ExecutionContextExecutor dispatcher();

    public InternalActorRef guardian();

    public InternalActorRef lookupRoot();

    public ActorRef actorOf(Props var1);

    public ActorRef actorOf(Props var1, String var2);

    public static /* synthetic */ ActorSelection actorSelection$(ActorRefFactory $this, String path) {
        return $this.actorSelection(path);
    }

    default public ActorSelection actorSelection(String path) {
        Option<Tuple2<Address, Iterable<String>>> option;
        Option<Seq<String>> option2;
        String string = path;
        if (string != null && !(option2 = RelativeActorPath$.MODULE$.unapply(string)).isEmpty()) {
            Seq<String> elems = option2.get();
            if (elems.isEmpty()) {
                return ActorSelection$.MODULE$.apply(this.provider().deadLetters(), "");
            }
            if (((String)elems.head()).isEmpty()) {
                return ActorSelection$.MODULE$.apply((ActorRef)this.provider().rootGuardian(), (scala.collection.Iterable)elems.tail());
            }
            return ActorSelection$.MODULE$.apply((ActorRef)this.lookupRoot(), elems);
        }
        if (string != null && !(option = ActorPathExtractor$.MODULE$.unapply(string)).isEmpty()) {
            Address address = option.get()._1();
            Iterable<String> elems = option.get()._2();
            return ActorSelection$.MODULE$.apply(this.provider().rootGuardianAt(address), elems);
        }
        return ActorSelection$.MODULE$.apply(this.provider().deadLetters(), "");
    }

    public static /* synthetic */ ActorSelection actorSelection$(ActorRefFactory $this, ActorPath path) {
        return $this.actorSelection(path);
    }

    default public ActorSelection actorSelection(ActorPath path) {
        return ActorSelection$.MODULE$.apply(this.provider().rootGuardianAt(path.address()), path.elements());
    }

    public void stop(ActorRef var1);

    public static void $init$(ActorRefFactory $this) {
    }
}

