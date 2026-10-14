/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorRef;
import akka.actor.LocalActorRef;
import akka.actor.Props;
import akka.annotation.DoNotInherit;
import akka.annotation.InternalApi;
import akka.event.LoggingAdapter;
import akka.stream.ActorAttributes;
import akka.stream.ActorMaterializer;
import akka.stream.Attributes;
import akka.stream.ClosedShape;
import akka.stream.Graph;
import akka.stream.MaterializationContext;
import akka.stream.impl.IslandTag;
import akka.stream.impl.Phase;
import akka.stream.impl.PhasedFusingActorMaterializer$;
import scala.collection.immutable.Map;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dbA\u0002\u0006\f\u0003\u0003y\u0011\u0003C\u0003\u0017\u0001\u0011\u0005\u0001\u0004C\u0003\u001c\u0001\u0019\u0005C\u0004C\u0003-\u0001\u0019\u0005Q\u0006C\u0003-\u0001\u0019\u0005!\n\u0003\u0004-\u0001\u0019\u0005qB\u0016\u0005\u0007Y\u0002!\teD7\t\r1\u0004A\u0011A\b\u0000\u0011!\t9\u0001\u0001D!\u001f\u0005%\u0001\u0002CA\r\u0001\u0019\u0005s\"a\u0007\u00033\u0015CH/\u001a8eK\u0012\f5\r^8s\u001b\u0006$XM]5bY&TXM\u001d\u0006\u0003\u00195\tA![7qY*\u0011abD\u0001\u0007gR\u0014X-Y7\u000b\u0003A\tA!Y6lCN\u0011\u0001A\u0005\t\u0003'Qi\u0011!D\u0005\u0003+5\u0011\u0011#Q2u_Jl\u0015\r^3sS\u0006d\u0017N_3s\u0003\u0019a\u0014N\\5u}\r\u0001A#A\r\u0011\u0005i\u0001Q\"A\u0006\u0002\u001d]LG\u000f\u001b(b[\u0016\u0004&/\u001a4jqR\u0011\u0011$\b\u0005\u0006=\t\u0001\raH\u0001\u0005]\u0006lW\r\u0005\u0002!S9\u0011\u0011e\n\t\u0003E\u0015j\u0011a\t\u0006\u0003I]\ta\u0001\u0010:p_Rt$\"\u0001\u0014\u0002\u000bM\u001c\u0017\r\\1\n\u0005!*\u0013A\u0002)sK\u0012,g-\u0003\u0002+W\t11\u000b\u001e:j]\u001eT!\u0001K\u0013\u0002\u00175\fG/\u001a:jC2L'0Z\u000b\u0003]E\"\"aL\u001e\u0011\u0005A\nD\u0002\u0001\u0003\u0006e\r\u0011\ra\r\u0002\u0004\u001b\u0006$\u0018C\u0001\u001b9!\t)d'D\u0001&\u0013\t9TEA\u0004O_RD\u0017N\\4\u0011\u0005UJ\u0014B\u0001\u001e&\u0005\r\te.\u001f\u0005\u0006y\r\u0001\r!P\u0001\u000f?J,hN\\1cY\u0016<%/\u00199i!\u0011\u0019b\bQ\u0018\n\u0005}j!!B$sCBD\u0007CA\nB\u0013\t\u0011UBA\u0006DY>\u001cX\rZ*iCB,\u0007FA\u0002E!\t)\u0005*D\u0001G\u0015\t9u\"\u0001\u0006b]:|G/\u0019;j_:L!!\u0013$\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u000b\u0003\u00176#2\u0001\u0014(Q!\t\u0001T\nB\u00033\t\t\u00071\u0007C\u0003=\t\u0001\u0007q\n\u0005\u0003\u0014}\u0001c\u0005\"B)\u0005\u0001\u0004\u0011\u0016!\u00053fM\u0006,H\u000e^!uiJL'-\u001e;fgB\u00111cU\u0005\u0003)6\u0011!\"\u0011;ue&\u0014W\u000f^3tQ\t!A)\u0006\u0002X3R)\u0001LW/_GB\u0011\u0001'\u0017\u0003\u0006e\u0015\u0011\ra\r\u0005\u00067\u0016\u0001\r\u0001X\u0001\u0006OJ\f\u0007\u000f\u001b\t\u0005'y\u0002\u0005\fC\u0003R\u000b\u0001\u0007!\u000bC\u0003`\u000b\u0001\u0007\u0001-\u0001\u0007eK\u001a\fW\u000f\u001c;QQ\u0006\u001cX\rE\u0002\u001bCbJ!AY\u0006\u0003\u000bAC\u0017m]3\t\u000b\u0011,\u0001\u0019A3\u0002\rAD\u0017m]3t!\u0011\u0001c\r\u001b1\n\u0005\u001d\\#aA'baB\u0011!$[\u0005\u0003U.\u0011\u0011\"S:mC:$G+Y4)\u0005\u0015!\u0015aB1di>\u0014xJ\u001a\u000b\u0004]RL\bCA8s\u001b\u0005\u0001(BA9\u0010\u0003\u0015\t7\r^8s\u0013\t\u0019\bO\u0001\u0005BGR|'OU3g\u0011\u0015)h\u00011\u0001w\u0003\u001d\u0019wN\u001c;fqR\u0004\"aE<\n\u0005al!AF'bi\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8D_:$X\r\u001f;\t\u000bi4\u0001\u0019A>\u0002\u000bA\u0014x\u000e]:\u0011\u0005=d\u0018BA?q\u0005\u0015\u0001&o\u001c9tQ\t1A\tF\u0003o\u0003\u0003\t\u0019\u0001C\u0003{\u000f\u0001\u00071\u0010C\u0003\u001f\u000f\u0001\u0007q\u0004\u000b\u0002\b\t\u00061An\\4hKJ,\"!a\u0003\u0011\t\u00055\u00111C\u0007\u0003\u0003\u001fQ1!!\u0005\u0010\u0003\u0015)g/\u001a8u\u0013\u0011\t)\"a\u0004\u0003\u001d1{wmZ5oO\u0006#\u0017\r\u001d;fe\"\u0012\u0001\u0002R\u0001\u000bgV\u0004XM\u001d<jg>\u0014X#\u00018)\u0005%!\u0005f\u0001\u0001\u0002\"A\u0019Q)a\t\n\u0007\u0005\u0015bI\u0001\u0007E_:{G/\u00138iKJLG\u000f")
@DoNotInherit
public abstract class ExtendedActorMaterializer
extends ActorMaterializer {
    @Override
    public abstract ExtendedActorMaterializer withNamePrefix(String var1);

    @Override
    @InternalApi
    public abstract <Mat> Mat materialize(Graph<ClosedShape, Mat> var1);

    @Override
    @InternalApi
    public abstract <Mat> Mat materialize(Graph<ClosedShape, Mat> var1, Attributes var2);

    @InternalApi
    public abstract <Mat> Mat materialize(Graph<ClosedShape, Mat> var1, Attributes var2, Phase<Object> var3, Map<IslandTag, Phase<Object>> var4);

    @Override
    @InternalApi
    public ActorRef actorOf(MaterializationContext context, Props props) {
        Props props2;
        block3: {
            String string = props.dispatcher();
            switch (string == null ? 0 : string.hashCode()) {
                case 759061882: {
                    if (!"akka.actor.default-dispatcher".equals(string)) break;
                    props2 = props.withDispatcher(((ActorAttributes.Dispatcher)context.effectiveAttributes().mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.Dispatcher.class))).dispatcher()).withMailbox(PhasedFusingActorMaterializer$.MODULE$.Mailbox());
                    break block3;
                }
            }
            props2 = props;
        }
        Props effectiveProps = props2;
        return this.actorOf(effectiveProps, context.islandName());
    }

    @InternalApi
    public ActorRef actorOf(Props props, String name) {
        ActorRef actorRef2 = this.supervisor();
        if (actorRef2 instanceof LocalActorRef) {
            LocalActorRef localActorRef = (LocalActorRef)actorRef2;
            return localActorRef.underlying().attachChild(props, name, false);
        }
        throw new IllegalStateException(new StringBuilder(47).append("Stream supervisor must be a local actor, was [").append(actorRef2.getClass().getName()).append("]").toString());
    }

    @Override
    @InternalApi
    public abstract LoggingAdapter logger();

    @Override
    @InternalApi
    public abstract ActorRef supervisor();
}

