/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.NotUsed;
import akka.NotUsed$;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.annotation.InternalApi;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.InPort;
import akka.stream.OutPort;
import akka.stream.Shape;
import akka.stream.impl.ActorPublisher;
import akka.stream.impl.FanIn;
import akka.stream.impl.FanOut;
import akka.stream.impl.PhaseIsland;
import akka.stream.impl.PhasedFusingActorMaterializer;
import akka.stream.impl.PhasedFusingActorMaterializer$;
import akka.stream.impl.StreamLayout;
import akka.stream.impl.io.TLSActor$;
import akka.stream.impl.io.TlsModule;
import java.io.Serializable;
import org.reactivestreams.Publisher;
import scala.Function1;
import scala.Tuple2;
import scala.collection.immutable.Vector;
import scala.package$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015d!B\t\u0013\u0005YA\u0002\u0002C\u0014\u0001\u0005\u0003\u0005\u000b\u0011B\u0015\t\u00111\u0002!\u0011!Q\u0001\n5BQ\u0001\u000f\u0001\u0005\u0002eBQ!\u0010\u0001\u0005\u0002yB\u0011b\u0010\u0001A\u0002\u0003\u0007I\u0011\u0001!\t\u0013\u001d\u0003\u0001\u0019!a\u0001\n\u0003A\u0005\"\u0003(\u0001\u0001\u0004\u0005\t\u0015)\u0003B\u0011%y\u0005\u00011AA\u0002\u0013\u0005\u0001\u000bC\u0005a\u0001\u0001\u0007\t\u0019!C\u0001C\"I1\r\u0001a\u0001\u0002\u0003\u0006K!\u0015\u0005\u0006I\u0002!\t!\u001a\u0005\b\u0003\u0007\u0001A\u0011AA\u0003\u0011\u001d\t\u0019\u0001\u0001C\u0001\u0003?Aq!a\f\u0001\t\u0003\t\t\u0004C\u0004\u0002H\u0001!\t%!\u0013\t\u000f\u0005M\u0003\u0001\"\u0001\u0002V\tqA\u000b\\:N_\u0012,H.\u001a)iCN,'BA\n\u0015\u0003\u0011IW\u000e\u001d7\u000b\u0005U1\u0012AB:ue\u0016\fWNC\u0001\u0018\u0003\u0011\t7n[1\u0014\u0007\u0001Ir\u0004\u0005\u0002\u001b;5\t1DC\u0001\u001d\u0003\u0015\u00198-\u00197b\u0013\tq2D\u0001\u0004B]f\u0014VM\u001a\t\u0004A\u0005\u001aS\"\u0001\n\n\u0005\t\u0012\"a\u0003)iCN,\u0017j\u001d7b]\u0012\u0004\"\u0001J\u0013\u000e\u0003YI!A\n\f\u0003\u000f9{G/V:fI\u0006aQ.\u0019;fe&\fG.\u001b>fe\u000e\u0001\u0001C\u0001\u0011+\u0013\tY#CA\u000fQQ\u0006\u001cX\r\u001a$vg&tw-Q2u_Jl\u0015\r^3sS\u0006d\u0017N_3s\u0003)I7\u000f\\1oI:\u000bW.\u001a\t\u0003]Ur!aL\u001a\u0011\u0005AZR\"A\u0019\u000b\u0005IB\u0013A\u0002\u001fs_>$h(\u0003\u000257\u00051\u0001K]3eK\u001aL!AN\u001c\u0003\rM#(/\u001b8h\u0015\t!4$\u0001\u0004=S:LGO\u0010\u000b\u0004umb\u0004C\u0001\u0011\u0001\u0011\u001593\u00011\u0001*\u0011\u0015a3\u00011\u0001.\u0003\u0011q\u0017-\\3\u0016\u00035\n\u0001\u0002\u001e7t\u0003\u000e$xN]\u000b\u0002\u0003B\u0011!)R\u0007\u0002\u0007*\u0011AIF\u0001\u0006C\u000e$xN]\u0005\u0003\r\u000e\u0013\u0001\"Q2u_J\u0014VMZ\u0001\ri2\u001c\u0018i\u0019;pe~#S-\u001d\u000b\u0003\u00132\u0003\"A\u0007&\n\u0005-[\"\u0001B+oSRDq!\u0014\u0004\u0002\u0002\u0003\u0007\u0011)A\u0002yIE\n\u0011\u0002\u001e7t\u0003\u000e$xN\u001d\u0011\u0002\u0015A,(\r\\5tQ\u0016\u00148/F\u0001R!\r\u0011vK\u0017\b\u0003'Vs!\u0001\r+\n\u0003qI!AV\u000e\u0002\u000fA\f7m[1hK&\u0011\u0001,\u0017\u0002\u0007-\u0016\u001cGo\u001c:\u000b\u0005Y[\u0002c\u0001\u0011\\;&\u0011AL\u0005\u0002\u000f\u0003\u000e$xN\u001d)vE2L7\u000f[3s!\tQb,\u0003\u0002`7\t\u0019\u0011I\\=\u0002\u001dA,(\r\\5tQ\u0016\u00148o\u0018\u0013fcR\u0011\u0011J\u0019\u0005\b\u001b&\t\t\u00111\u0001R\u0003-\u0001XO\u00197jg\",'o\u001d\u0011\u0002#5\fG/\u001a:jC2L'0Z!u_6L7\rF\u0002gSr\u0004BAG4$;&\u0011\u0001n\u0007\u0002\u0007)V\u0004H.\u001a\u001a\t\u000b)\\\u0001\u0019A6\u0002\u00075|G\r\u0005\u0003mkblfBA7t\u001d\tq'O\u0004\u0002pc:\u0011\u0001\u0007]\u0005\u0002/%\u0011QCF\u0005\u0003'QI!\u0001\u001e\n\u0002\u0019M#(/Z1n\u0019\u0006Lx.\u001e;\n\u0005Y<(\u0001D!u_6L7-T8ek2,'B\u0001;\u0013!\tI(0D\u0001\u0015\u0013\tYHCA\u0003TQ\u0006\u0004X\rC\u0003~\u0017\u0001\u0007a0\u0001\u0006biR\u0014\u0018NY;uKN\u0004\"!_@\n\u0007\u0005\u0005AC\u0001\u0006BiR\u0014\u0018NY;uKN\f!\"Y:tS\u001et\u0007k\u001c:u)\u001dI\u0015qAA\t\u00037Aq!!\u0003\r\u0001\u0004\tY!\u0001\u0002j]B\u0019\u00110!\u0004\n\u0007\u0005=AC\u0001\u0004J]B{'\u000f\u001e\u0005\b\u0003'a\u0001\u0019AA\u000b\u0003\u0011\u0019Hn\u001c;\u0011\u0007i\t9\"C\u0002\u0002\u001am\u00111!\u00138u\u0011\u0019\ti\u0002\u0004a\u0001G\u0005)An\\4jGR9\u0011*!\t\u0002,\u00055\u0002bBA\u0012\u001b\u0001\u0007\u0011QE\u0001\u0004_V$\bcA=\u0002(%\u0019\u0011\u0011\u0006\u000b\u0003\u000f=+H\u000fU8si\"9\u00111C\u0007A\u0002\u0005U\u0001BBA\u000f\u001b\u0001\u00071%A\bde\u0016\fG/\u001a)vE2L7\u000f[3s)\u0019\t\u0019$a\u0011\u0002FA)\u0011QGA ;6\u0011\u0011q\u0007\u0006\u0005\u0003s\tY$A\bsK\u0006\u001cG/\u001b<fgR\u0014X-Y7t\u0015\t\ti$A\u0002pe\u001eLA!!\u0011\u00028\tI\u0001+\u001e2mSNDWM\u001d\u0005\b\u0003Gq\u0001\u0019AA\u0013\u0011\u0019\tiB\u0004a\u0001G\u0005iA/Y6f!V\u0014G.[:iKJ$r!SA&\u0003\u001b\n\t\u0006C\u0004\u0002\u0014=\u0001\r!!\u0006\t\u000f\u0005=s\u00021\u0001\u00024\u0005I\u0001/\u001e2mSNDWM\u001d\u0005\u0006{>\u0001\rA`\u0001\u000e_:L5\u000f\\1oIJ+\u0017\rZ=\u0015\u0003%C3\u0001AA-!\u0011\tY&!\u0019\u000e\u0005\u0005u#bAA0-\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005\r\u0014Q\f\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e")
@InternalApi
public final class TlsModulePhase
implements PhaseIsland<NotUsed> {
    private final PhasedFusingActorMaterializer materializer;
    private final String islandName;
    private ActorRef tlsActor;
    private Vector<ActorPublisher<Object>> publishers;

    @Override
    public String name() {
        return "TlsModulePhase";
    }

    public ActorRef tlsActor() {
        return this.tlsActor;
    }

    public void tlsActor_$eq(ActorRef x$1) {
        this.tlsActor = x$1;
    }

    public Vector<ActorPublisher<Object>> publishers() {
        return this.publishers;
    }

    public void publishers_$eq(Vector<ActorPublisher<Object>> x$1) {
        this.publishers = x$1;
    }

    @Override
    public Tuple2<NotUsed, Object> materializeAtomic(StreamLayout.AtomicModule<Shape, Object> mod, Attributes attributes) {
        TlsModule tls = (TlsModule)mod;
        String dispatcher = ((ActorAttributes.Dispatcher)attributes.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.Dispatcher.class))).dispatcher();
        int maxInputBuffer = ((Attributes.InputBuffer)attributes.mandatoryAttribute(ClassTag$.MODULE$.apply(Attributes.InputBuffer.class))).max();
        Props props = TLSActor$.MODULE$.props(maxInputBuffer, tls.createSSLEngine(), tls.verifySession(), tls.closing(), TLSActor$.MODULE$.props$default$5()).withDispatcher(dispatcher).withMailbox(PhasedFusingActorMaterializer$.MODULE$.Mailbox());
        this.tlsActor_$eq(this.materializer.actorOf(props, new StringBuilder(8).append("TLS-for-").append(this.islandName).toString()));
        this.publishers_$eq((Vector)package$.MODULE$.Vector().tabulate(2, (Function1<Object, ActorPublisher> & Serializable)id -> this.factory$1(BoxesRunTime.unboxToInt(id))));
        ActorRef qual$1 = this.tlsActor();
        FanOut.ExposedPublishers x$1 = new FanOut.ExposedPublishers(this.publishers());
        ActorRef x$2 = qual$1.$bang$default$2(x$1);
        qual$1.$bang(x$1, x$2);
        return new Tuple2<NotUsed, Object>(NotUsed$.MODULE$, NotUsed$.MODULE$);
    }

    @Override
    public void assignPort(InPort in, int slot, NotUsed logic) {
    }

    @Override
    public void assignPort(OutPort out, int slot, NotUsed logic) {
    }

    @Override
    public Publisher<Object> createPublisher(OutPort out, NotUsed logic) {
        return (Publisher)this.publishers().apply(out.id());
    }

    @Override
    public void takePublisher(int slot, Publisher<Object> publisher, Attributes attributes) {
        publisher.subscribe(new FanIn.SubInput(this.tlsActor(), 1 - slot));
    }

    @Override
    public void onIslandReady() {
    }

    private final ActorPublisher factory$1(int id) {
        return new ActorPublisher<Object>(this, id){
            private final FanOut.SubstreamSubscribePending wakeUpMsg;

            public FanOut.SubstreamSubscribePending wakeUpMsg() {
                return this.wakeUpMsg;
            }
            {
                this.wakeUpMsg = new FanOut.SubstreamSubscribePending(id$1);
            }
        };
    }

    public TlsModulePhase(PhasedFusingActorMaterializer materializer, String islandName) {
        this.materializer = materializer;
        this.islandName = islandName;
    }
}

