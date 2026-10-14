/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.InPort;
import akka.stream.MaterializationContext;
import akka.stream.OutPort;
import akka.stream.Shape;
import akka.stream.impl.PhaseIsland;
import akka.stream.impl.PhasedFusingActorMaterializer;
import akka.stream.impl.SinkModule;
import akka.stream.impl.StreamLayout;
import akka.stream.impl.VirtualPublisher;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001db!\u0002\b\u0010\u0005M)\u0002\u0002\u0003\u0011\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0012\t\u0011\u0015\u0002!\u0011!Q\u0001\n\u0019BQ!\r\u0001\u0005\u0002IBQA\u000e\u0001\u0005B]B\u0011\u0002\u000f\u0001A\u0002\u0003\u0007I\u0011A\u001d\t\u0013i\u0002\u0001\u0019!a\u0001\n\u0003Y\u0004\"C!\u0001\u0001\u0004\u0005\t\u0015)\u0003\u0017\u0011\u0015\u0011\u0005\u0001\"\u0011D\u0011\u0015\u0011\u0007\u0001\"\u0011d\u0011\u0015\u0011\u0007\u0001\"\u0011q\u0011\u0015A\b\u0001\"\u0011z\u0011\u001d\tI\u0001\u0001C!\u0003\u0017Aq!!\u0006\u0001\t\u0003\n9BA\bTS:\\Wj\u001c3vY\u0016\u0004\u0006.Y:f\u0015\t\u0001\u0012#\u0001\u0003j[Bd'B\u0001\n\u0014\u0003\u0019\u0019HO]3b[*\tA#\u0001\u0003bW.\f7c\u0001\u0001\u00179A\u0011qCG\u0007\u00021)\t\u0011$A\u0003tG\u0006d\u0017-\u0003\u0002\u001c1\t1\u0011I\\=SK\u001a\u00042!\b\u0010\u0017\u001b\u0005y\u0011BA\u0010\u0010\u0005-\u0001\u0006.Y:f\u0013Nd\u0017M\u001c3\u0002\u00195\fG/\u001a:jC2L'0\u001a:\u0004\u0001A\u0011QdI\u0005\u0003I=\u0011Q\u0004\u00155bg\u0016$g)^:j]\u001e\f5\r^8s\u001b\u0006$XM]5bY&TXM]\u0001\u000bSNd\u0017M\u001c3OC6,\u0007CA\u0014/\u001d\tAC\u0006\u0005\u0002*15\t!F\u0003\u0002,C\u00051AH]8pizJ!!\f\r\u0002\rA\u0013X\rZ3g\u0013\ty\u0003G\u0001\u0004TiJLgn\u001a\u0006\u0003[a\ta\u0001P5oSRtDcA\u001a5kA\u0011Q\u0004\u0001\u0005\u0006A\r\u0001\rA\t\u0005\u0006K\r\u0001\rAJ\u0001\u0005]\u0006lW-F\u0001'\u0003q\u0019XOY:de&\u0014WM](s-&\u0014H/^1m!V\u0014G.[:iKJ,\u0012AF\u0001!gV\u00147o\u0019:jE\u0016\u0014xJ\u001d,jeR,\u0018\r\u001c)vE2L7\u000f[3s?\u0012*\u0017\u000f\u0006\u0002=\u007fA\u0011q#P\u0005\u0003}a\u0011A!\u00168ji\"9\u0001IBA\u0001\u0002\u00041\u0012a\u0001=%c\u0005i2/\u001e2tGJL'-\u001a:PeZK'\u000f^;bYB+(\r\\5tQ\u0016\u0014\b%A\tnCR,'/[1mSj,\u0017\t^8nS\u000e$2\u0001\u0012&^!\u00119RIF$\n\u0005\u0019C\"A\u0002+va2,'\u0007\u0005\u0002\u0018\u0011&\u0011\u0011\n\u0007\u0002\u0004\u0003:L\b\"B&\t\u0001\u0004a\u0015aA7pIB!QJV-H\u001d\tqEK\u0004\u0002P':\u0011\u0001K\u0015\b\u0003SEK\u0011\u0001F\u0005\u0003%MI!\u0001E\t\n\u0005U{\u0011\u0001D*ue\u0016\fW\u000eT1z_V$\u0018BA,Y\u00051\tEo\\7jG6{G-\u001e7f\u0015\t)v\u0002\u0005\u0002[76\t\u0011#\u0003\u0002]#\t)1\u000b[1qK\")a\f\u0003a\u0001?\u0006Q\u0011\r\u001e;sS\n,H/Z:\u0011\u0005i\u0003\u0017BA1\u0012\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\u000bCN\u001c\u0018n\u001a8Q_J$H\u0003\u0002\u001feS:DQ!Z\u0005A\u0002\u0019\f!!\u001b8\u0011\u0005i;\u0017B\u00015\u0012\u0005\u0019Ie\u000eU8si\")!.\u0003a\u0001W\u0006!1\u000f\\8u!\t9B.\u0003\u0002n1\t\u0019\u0011J\u001c;\t\u000b=L\u0001\u0019\u0001\f\u0002\u000b1|w-[2\u0015\tq\nho\u001e\u0005\u0006e*\u0001\ra]\u0001\u0004_V$\bC\u0001.u\u0013\t)\u0018CA\u0004PkR\u0004vN\u001d;\t\u000b)T\u0001\u0019A6\t\u000b=T\u0001\u0019\u0001\f\u0002\u001f\r\u0014X-\u0019;f!V\u0014G.[:iKJ$RA_A\u0003\u0003\u000f\u0001Ba_A\u0001\u000f6\tAP\u0003\u0002~}\u0006y!/Z1di&4Xm\u001d;sK\u0006l7OC\u0001\u0000\u0003\ry'oZ\u0005\u0004\u0003\u0007a(!\u0003)vE2L7\u000f[3s\u0011\u0015\u00118\u00021\u0001t\u0011\u0015y7\u00021\u0001\u0017\u00035!\u0018m[3Qk\nd\u0017n\u001d5feR9A(!\u0004\u0002\u0010\u0005M\u0001\"\u00026\r\u0001\u0004Y\u0007BBA\t\u0019\u0001\u0007!0A\u0005qk\nd\u0017n\u001d5fe\")a\f\u0004a\u0001?\u0006iqN\\%tY\u0006tGMU3bIf$\u0012\u0001\u0010\u0015\u0004\u0001\u0005m\u0001\u0003BA\u000f\u0003Gi!!a\b\u000b\u0007\u0005\u00052#\u0001\u0006b]:|G/\u0019;j_:LA!!\n\u0002 \tY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
public final class SinkModulePhase
implements PhaseIsland<Object> {
    private final PhasedFusingActorMaterializer materializer;
    private final String islandName;
    private Object subscriberOrVirtualPublisher;

    @Override
    public String name() {
        return "SinkModule phase";
    }

    public Object subscriberOrVirtualPublisher() {
        return this.subscriberOrVirtualPublisher;
    }

    public void subscriberOrVirtualPublisher_$eq(Object x$1) {
        this.subscriberOrVirtualPublisher = x$1;
    }

    @Override
    public Tuple2<Object, Object> materializeAtomic(StreamLayout.AtomicModule<Shape, Object> mod, Attributes attributes) {
        Tuple2 subAndMat = ((SinkModule)mod).create(new MaterializationContext(this.materializer, attributes, new StringBuilder(1).append(this.islandName).append("-").append(attributes.nameOrDefault(attributes.nameOrDefault$default$1())).toString()));
        this.subscriberOrVirtualPublisher_$eq(subAndMat._1());
        return new Tuple2<Object, Object>(this.subscriberOrVirtualPublisher(), subAndMat._2());
    }

    @Override
    public void assignPort(InPort in, int slot, Object logic) {
    }

    @Override
    public void assignPort(OutPort out, int slot, Object logic) {
    }

    @Override
    public Publisher<Object> createPublisher(OutPort out, Object logic) {
        throw new UnsupportedOperationException("A Sink cannot create a Publisher");
    }

    @Override
    public void takePublisher(int slot, Publisher<Object> publisher, Attributes attributes) {
        Object object = this.subscriberOrVirtualPublisher();
        if (object instanceof VirtualPublisher) {
            VirtualPublisher virtualPublisher = (VirtualPublisher)object;
            virtualPublisher.registerPublisher(publisher);
            return;
        }
        if (object instanceof Subscriber) {
            Subscriber subscriber = (Subscriber)object;
            publisher.subscribe(subscriber);
            return;
        }
        throw new IllegalStateException();
    }

    @Override
    public void onIslandReady() {
    }

    public SinkModulePhase(PhasedFusingActorMaterializer materializer, String islandName) {
        this.materializer = materializer;
        this.islandName = islandName;
    }
}

