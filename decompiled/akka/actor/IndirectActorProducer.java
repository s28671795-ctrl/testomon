/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor;
import akka.actor.CreatorConsumer;
import akka.actor.CreatorFunctionConsumer;
import akka.actor.IndirectActorProducer$;
import akka.actor.TypedCreatorFunctionConsumer;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000594q!\u0004\b\u0011\u0002G\u00051\u0003C\u0003\u001b\u0001\u0019\u00051\u0004C\u0003!\u0001\u0019\u0005\u0011e\u0002\u00047\u001d!\u0005\u0001c\u000e\u0004\u0007\u001b9A\t\u0001\u0005\u001d\t\u000be\"A\u0011\u0001\u001e\t\u000fm\"!\u0019!C\u0001y!1q\t\u0002Q\u0001\nuBq\u0001\u0013\u0003C\u0002\u0013\u0005\u0011\n\u0003\u0004O\t\u0001\u0006IA\u0013\u0005\b\u001f\u0012\u0011\r\u0011\"\u0001Q\u0011\u0019)F\u0001)A\u0005#\")a\u000b\u0002C\u0001/\n)\u0012J\u001c3je\u0016\u001cG/Q2u_J\u0004&o\u001c3vG\u0016\u0014(BA\b\u0011\u0003\u0015\t7\r^8s\u0015\u0005\t\u0012\u0001B1lW\u0006\u001c\u0001a\u0005\u0002\u0001)A\u0011Q\u0003G\u0007\u0002-)\tq#A\u0003tG\u0006d\u0017-\u0003\u0002\u001a-\t1\u0011I\\=SK\u001a\fq\u0001\u001d:pIV\u001cW\rF\u0001\u001d!\tib$D\u0001\u000f\u0013\tybBA\u0003BGR|'/\u0001\u0006bGR|'o\u00117bgN,\u0012A\t\u0019\u0003GA\u00022\u0001J\u0016/\u001d\t)\u0013\u0006\u0005\u0002'-5\tqE\u0003\u0002)%\u00051AH]8pizJ!A\u000b\f\u0002\rA\u0013X\rZ3g\u0013\taSFA\u0003DY\u0006\u001c8O\u0003\u0002+-A\u0011q\u0006\r\u0007\u0001\t%\t$!!A\u0001\u0002\u000b\u0005!GA\u0002`IE\n\"a\r\u000f\u0011\u0005U!\u0014BA\u001b\u0017\u0005\u001dqu\u000e\u001e5j]\u001e\fQ#\u00138eSJ,7\r^!di>\u0014\bK]8ek\u000e,'\u000f\u0005\u0002\u001e\tM\u0011A\u0001F\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003]\nAd\u0011:fCR|'OR;oGRLwN\\\"p]N,X.\u001a:DY\u0006\u001c8/F\u0001>!\rq4\tR\u0007\u0002\u007f)\u0011\u0001)Q\u0001\u0005Y\u0006twMC\u0001C\u0003\u0011Q\u0017M^1\n\u00051z\u0004CA\u000fF\u0013\t1eBA\fDe\u0016\fGo\u001c:Gk:\u001cG/[8o\u0007>t7/^7fe\u0006i2I]3bi>\u0014h)\u001e8di&|gnQ8ogVlWM]\"mCN\u001c\b%\u0001\u000bDe\u0016\fGo\u001c:D_:\u001cX/\\3s\u00072\f7o]\u000b\u0002\u0015B\u0019ahQ&\u0011\u0005ua\u0015BA'\u000f\u0005=\u0019%/Z1u_J\u001cuN\\:v[\u0016\u0014\u0018!F\"sK\u0006$xN]\"p]N,X.\u001a:DY\u0006\u001c8\u000fI\u0001\")f\u0004X\rZ\"sK\u0006$xN\u001d$v]\u000e$\u0018n\u001c8D_:\u001cX/\\3s\u00072\f7o]\u000b\u0002#B\u0019ah\u0011*\u0011\u0005u\u0019\u0016B\u0001+\u000f\u0005q!\u0016\u0010]3e\u0007J,\u0017\r^8s\rVt7\r^5p]\u000e{gn];nKJ\f!\u0005V=qK\u0012\u001c%/Z1u_J4UO\\2uS>t7i\u001c8tk6,'o\u00117bgN\u0004\u0013!B1qa2LHc\u0001-ZIB\u0011Q\u0004\u0001\u0005\u000652\u0001\raW\u0001\u0006G2\f'P\u001f\u0019\u00039z\u00032\u0001J\u0016^!\tyc\fB\u0005`3\u0006\u0005\t\u0011!B\u0001A\n\u0019q\f\n\u001a\u0012\u0005M\n\u0007CA\u000bc\u0013\t\u0019gCA\u0002B]fDQ!\u001a\u0007A\u0002\u0019\fA!\u0019:hgB\u0019q\r\\1\u000e\u0003!T!!\u001b6\u0002\u0013%lW.\u001e;bE2,'BA6\u0017\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003[\"\u00141aU3r\u0001")
public interface IndirectActorProducer {
    public static IndirectActorProducer apply(Class<?> clazz, Seq<Object> args2) {
        return IndirectActorProducer$.MODULE$.apply(clazz, args2);
    }

    public static Class<TypedCreatorFunctionConsumer> TypedCreatorFunctionConsumerClass() {
        return IndirectActorProducer$.MODULE$.TypedCreatorFunctionConsumerClass();
    }

    public static Class<CreatorConsumer> CreatorConsumerClass() {
        return IndirectActorProducer$.MODULE$.CreatorConsumerClass();
    }

    public static Class<CreatorFunctionConsumer> CreatorFunctionConsumerClass() {
        return IndirectActorProducer$.MODULE$.CreatorFunctionConsumerClass();
    }

    public Actor produce();

    public Class<? extends Actor> actorClass();
}

