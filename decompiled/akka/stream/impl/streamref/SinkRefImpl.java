/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.streamref;

import akka.NotUsed;
import akka.NotUsed$;
import akka.actor.ActorRef;
import akka.annotation.InternalApi;
import akka.stream.SinkRef;
import akka.stream.SourceRef;
import akka.stream.impl.streamref.SinkRefImpl$;
import akka.stream.impl.streamref.SinkRefStageImpl;
import akka.stream.javadsl.Sink;
import akka.stream.scaladsl.Sink$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005=e!B\f\u0019\u0005r\u0001\u0003\u0002C$\u0001\u0005+\u0007I\u0011\u0001%\t\u0011=\u0003!\u0011#Q\u0001\n%CQ\u0001\u0015\u0001\u0005\u0002ECQ!\u0016\u0001\u0005BYCq!\u0019\u0001\u0002\u0002\u0013\u0005!\rC\u0004i\u0001E\u0005I\u0011A5\t\u000fY\u0004\u0011\u0011!C!o\"I\u0011\u0011\u0001\u0001\u0002\u0002\u0013\u0005\u00111\u0001\u0005\n\u0003\u0017\u0001\u0011\u0011!C\u0001\u0003\u001bA\u0011\"a\u0005\u0001\u0003\u0003%\t%!\u0006\t\u0013\u0005\r\u0002!!A\u0005\u0002\u0005\u0015\u0002\"CA\u0018\u0001\u0005\u0005I\u0011IA\u0019\u0011%\t)\u0004AA\u0001\n\u0003\n9\u0004C\u0005\u0002:\u0001\t\t\u0011\"\u0011\u0002<!I\u0011Q\b\u0001\u0002\u0002\u0013\u0005\u0013qH\u0004\u000b\u0003\u001fB\u0012\u0011!E\u00019\u0005Ec!C\f\u0019\u0003\u0003E\t\u0001HA*\u0011\u0019\u0001\u0016\u0003\"\u0001\u0002`!I\u0011\u0011H\t\u0002\u0002\u0013\u0015\u00131\b\u0005\n\u0003C\n\u0012\u0011!CA\u0003GB\u0011\"a\u001c\u0012\u0003\u0003%\t)!\u001d\t\u0013\u0005\u0015\u0015#!A\u0005\n\u0005\u001d%aC*j].\u0014VMZ%na2T!!\u0007\u000e\u0002\u0013M$(/Z1ne\u00164'BA\u000e\u001d\u0003\u0011IW\u000e\u001d7\u000b\u0005uq\u0012AB:ue\u0016\fWNC\u0001 \u0003\u0011\t7n[1\u0016\u0005\u0005r3#\u0002\u0001#QaZ\u0004CA\u0012'\u001b\u0005!#\"A\u0013\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u001d\"#AB!osJ+g\rE\u0002*U1j\u0011\u0001H\u0005\u0003Wq\u0011qaU5oWJ+g\r\u0005\u0002.]1\u0001A!B\u0018\u0001\u0005\u0004\t$AA%o\u0007\u0001\t\"AM\u001b\u0011\u0005\r\u001a\u0014B\u0001\u001b%\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"a\t\u001c\n\u0005]\"#aA!osB\u00111%O\u0005\u0003u\u0011\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002=\t:\u0011QH\u0011\b\u0003}\u0005k\u0011a\u0010\u0006\u0003\u0001B\na\u0001\u0010:p_Rt\u0014\"A\u0013\n\u0005\r#\u0013a\u00029bG.\fw-Z\u0005\u0003\u000b\u001a\u0013AbU3sS\u0006d\u0017N_1cY\u0016T!a\u0011\u0013\u0002#%t\u0017\u000e^5bYB\u000b'\u000f\u001e8feJ+g-F\u0001J!\tQU*D\u0001L\u0015\tae$A\u0003bGR|'/\u0003\u0002O\u0017\nA\u0011i\u0019;peJ+g-\u0001\nj]&$\u0018.\u00197QCJ$h.\u001a:SK\u001a\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002S)B\u00191\u000b\u0001\u0017\u000e\u0003aAQaR\u0002A\u0002%\u000bAa]5oWR\tq\u000b\u0005\u0003Y72jV\"A-\u000b\u0005ic\u0012\u0001C:dC2\fGm\u001d7\n\u0005qK&\u0001B*j].\u0004\"AX0\u000e\u0003yI!\u0001\u0019\u0010\u0003\u000f9{G/V:fI\u0006!1m\u001c9z+\t\u0019g\r\u0006\u0002eOB\u00191\u000bA3\u0011\u000552G!B\u0018\u0006\u0005\u0004\t\u0004bB$\u0006!\u0003\u0005\r!S\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\tQW/F\u0001lU\tIEnK\u0001n!\tq7/D\u0001p\u0015\t\u0001\u0018/A\u0005v]\u000eDWmY6fI*\u0011!\u000fJ\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001;p\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\u0006_\u0019\u0011\r!M\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003a\u0004\"!\u001f@\u000e\u0003iT!a\u001f?\u0002\t1\fgn\u001a\u0006\u0002{\u0006!!.\u0019<b\u0013\ty(P\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003\u000b\u00012aIA\u0004\u0013\r\tI\u0001\n\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004k\u0005=\u0001\"CA\t\u0013\u0005\u0005\t\u0019AA\u0003\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011q\u0003\t\u0006\u00033\ty\"N\u0007\u0003\u00037Q1!!\b%\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003C\tYB\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA\u0014\u0003[\u00012aIA\u0015\u0013\r\tY\u0003\n\u0002\b\u0005>|G.Z1o\u0011!\t\tbCA\u0001\u0002\u0004)\u0014A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$2\u0001_A\u001a\u0011%\t\t\u0002DA\u0001\u0002\u0004\t)!\u0001\u0005iCND7i\u001c3f)\t\t)!\u0001\u0005u_N#(/\u001b8h)\u0005A\u0018AB3rk\u0006d7\u000f\u0006\u0003\u0002(\u0005\u0005\u0003\u0002CA\t\u001f\u0005\u0005\t\u0019A\u001b)\u0007\u0001\t)\u0005\u0005\u0003\u0002H\u0005-SBAA%\u0015\t\u0011h$\u0003\u0003\u0002N\u0005%#aC%oi\u0016\u0014h.\u00197Ba&\f1bU5oWJ+g-S7qYB\u00111+E\n\u0005#\t\n)\u0006\u0005\u0003\u0002X\u0005uSBAA-\u0015\r\tY\u0006`\u0001\u0003S>L1!RA-)\t\t\t&A\u0003baBd\u00170\u0006\u0003\u0002f\u0005-D\u0003BA4\u0003[\u0002Ba\u0015\u0001\u0002jA\u0019Q&a\u001b\u0005\u000b=\"\"\u0019A\u0019\t\u000b\u001d#\u0002\u0019A%\u0002\u000fUt\u0017\r\u001d9msV!\u00111OAB)\u0011\t)(a\u001f\u0011\t\r\n9(S\u0005\u0004\u0003s\"#AB(qi&|g\u000eC\u0005\u0002~U\t\t\u00111\u0001\u0002\u0000\u0005\u0019\u0001\u0010\n\u0019\u0011\tM\u0003\u0011\u0011\u0011\t\u0004[\u0005\rE!B\u0018\u0016\u0005\u0004\t\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAAE!\rI\u00181R\u0005\u0004\u0003\u001bS(AB(cU\u0016\u001cG\u000f")
@InternalApi
public final class SinkRefImpl<In>
implements SinkRef<In>,
Product,
Serializable {
    private final ActorRef initialPartnerRef;

    public static <In> Option<ActorRef> unapply(SinkRefImpl<In> x$0) {
        return SinkRefImpl$.MODULE$.unapply(x$0);
    }

    public static <In> SinkRefImpl<In> apply(ActorRef initialPartnerRef) {
        return SinkRefImpl$.MODULE$.apply(initialPartnerRef);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public final Sink<In, NotUsed> getSink() {
        return SinkRef.getSink$(this);
    }

    public ActorRef initialPartnerRef() {
        return this.initialPartnerRef;
    }

    @Override
    public akka.stream.scaladsl.Sink<In, NotUsed> sink() {
        return Sink$.MODULE$.fromGraph(new SinkRefStageImpl(OptionVal$Some$.MODULE$.apply(this.initialPartnerRef()))).mapMaterializedValue((Function1<SourceRef, NotUsed$> & Serializable)x$1 -> NotUsed$.MODULE$);
    }

    public <In> SinkRefImpl<In> copy(ActorRef initialPartnerRef) {
        return new SinkRefImpl<In>(initialPartnerRef);
    }

    public <In> ActorRef copy$default$1() {
        return this.initialPartnerRef();
    }

    @Override
    public String productPrefix() {
        return "SinkRefImpl";
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
                return this.initialPartnerRef();
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
        return x$1 instanceof SinkRefImpl;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "initialPartnerRef";
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
        if (!(object instanceof SinkRefImpl)) return false;
        boolean bl = true;
        if (!bl) return false;
        SinkRefImpl sinkRefImpl = (SinkRefImpl)x$1;
        ActorRef actorRef2 = this.initialPartnerRef();
        ActorRef actorRef3 = sinkRefImpl.initialPartnerRef();
        if (actorRef2 != null) {
            if (!((Object)actorRef2).equals(actorRef3)) return false;
            return true;
        }
        if (actorRef3 == null) return true;
        return false;
    }

    public SinkRefImpl(ActorRef initialPartnerRef) {
        this.initialPartnerRef = initialPartnerRef;
        SinkRef.$init$(this);
        Product.$init$(this);
    }
}

