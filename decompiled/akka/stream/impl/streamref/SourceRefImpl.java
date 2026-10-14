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
import akka.stream.impl.streamref.SourceRefImpl$;
import akka.stream.impl.streamref.SourceRefStageImpl;
import akka.stream.javadsl.Source;
import akka.stream.scaladsl.Source$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005=e!B\f\u0019\u0005r\u0001\u0003\u0002C$\u0001\u0005+\u0007I\u0011\u0001%\t\u0011=\u0003!\u0011#Q\u0001\n%CQ\u0001\u0015\u0001\u0005\u0002ECQ!\u0016\u0001\u0005\u0002YCq!\u0019\u0001\u0002\u0002\u0013\u0005!\rC\u0004i\u0001E\u0005I\u0011A5\t\u000fY\u0004\u0011\u0011!C!o\"I\u0011\u0011\u0001\u0001\u0002\u0002\u0013\u0005\u00111\u0001\u0005\n\u0003\u0017\u0001\u0011\u0011!C\u0001\u0003\u001bA\u0011\"a\u0005\u0001\u0003\u0003%\t%!\u0006\t\u0013\u0005\r\u0002!!A\u0005\u0002\u0005\u0015\u0002\"CA\u0018\u0001\u0005\u0005I\u0011IA\u0019\u0011%\t)\u0004AA\u0001\n\u0003\n9\u0004C\u0005\u0002:\u0001\t\t\u0011\"\u0011\u0002<!I\u0011Q\b\u0001\u0002\u0002\u0013\u0005\u0013qH\u0004\u000b\u0003\u001fB\u0012\u0011!E\u00019\u0005Ec!C\f\u0019\u0003\u0003E\t\u0001HA*\u0011\u0019\u0001\u0016\u0003\"\u0001\u0002`!I\u0011\u0011H\t\u0002\u0002\u0013\u0015\u00131\b\u0005\n\u0003C\n\u0012\u0011!CA\u0003GB\u0011\"a\u001c\u0012\u0003\u0003%\t)!\u001d\t\u0013\u0005\u0015\u0015#!A\u0005\n\u0005\u001d%!D*pkJ\u001cWMU3g\u00136\u0004HN\u0003\u0002\u001a5\u0005I1\u000f\u001e:fC6\u0014XM\u001a\u0006\u00037q\tA![7qY*\u0011QDH\u0001\u0007gR\u0014X-Y7\u000b\u0003}\tA!Y6lCV\u0011\u0011EL\n\u0006\u0001\tB\u0003h\u000f\t\u0003G\u0019j\u0011\u0001\n\u0006\u0002K\u0005)1oY1mC&\u0011q\u0005\n\u0002\u0007\u0003:L(+\u001a4\u0011\u0007%RC&D\u0001\u001d\u0013\tYCDA\u0005T_V\u00148-\u001a*fMB\u0011QF\f\u0007\u0001\t\u0015y\u0003A1\u00012\u0005\u0005!6\u0001A\t\u0003eU\u0002\"aI\u001a\n\u0005Q\"#a\u0002(pi\"Lgn\u001a\t\u0003GYJ!a\u000e\u0013\u0003\u0007\u0005s\u0017\u0010\u0005\u0002$s%\u0011!\b\n\u0002\b!J|G-^2u!\taDI\u0004\u0002>\u0005:\u0011a(Q\u0007\u0002\u007f)\u0011\u0001\tM\u0001\u0007yI|w\u000e\u001e \n\u0003\u0015J!a\u0011\u0013\u0002\u000fA\f7m[1hK&\u0011QI\u0012\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003\u0007\u0012\n\u0011#\u001b8ji&\fG\u000eU1si:,'OU3g+\u0005I\u0005C\u0001&N\u001b\u0005Y%B\u0001'\u001f\u0003\u0015\t7\r^8s\u0013\tq5J\u0001\u0005BGR|'OU3g\u0003IIg.\u001b;jC2\u0004\u0016M\u001d;oKJ\u0014VM\u001a\u0011\u0002\rqJg.\u001b;?)\t\u0011F\u000bE\u0002T\u00011j\u0011\u0001\u0007\u0005\u0006\u000f\u000e\u0001\r!S\u0001\u0007g>,(oY3\u0016\u0003]\u0003B\u0001W.-;6\t\u0011L\u0003\u0002[9\u0005A1oY1mC\u0012\u001cH.\u0003\u0002]3\n11k\\;sG\u0016\u0004\"AX0\u000e\u0003yI!\u0001\u0019\u0010\u0003\u000f9{G/V:fI\u0006!1m\u001c9z+\t\u0019g\r\u0006\u0002eOB\u00191\u000bA3\u0011\u000552G!B\u0018\u0006\u0005\u0004\t\u0004bB$\u0006!\u0003\u0005\r!S\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\tQW/F\u0001lU\tIEnK\u0001n!\tq7/D\u0001p\u0015\t\u0001\u0018/A\u0005v]\u000eDWmY6fI*\u0011!\u000fJ\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001;p\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\u0006_\u0019\u0011\r!M\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003a\u0004\"!\u001f@\u000e\u0003iT!a\u001f?\u0002\t1\fgn\u001a\u0006\u0002{\u0006!!.\u0019<b\u0013\ty(P\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003\u000b\u00012aIA\u0004\u0013\r\tI\u0001\n\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004k\u0005=\u0001\"CA\t\u0013\u0005\u0005\t\u0019AA\u0003\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011q\u0003\t\u0006\u00033\ty\"N\u0007\u0003\u00037Q1!!\b%\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003C\tYB\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA\u0014\u0003[\u00012aIA\u0015\u0013\r\tY\u0003\n\u0002\b\u0005>|G.Z1o\u0011!\t\tbCA\u0001\u0002\u0004)\u0014A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$2\u0001_A\u001a\u0011%\t\t\u0002DA\u0001\u0002\u0004\t)!\u0001\u0005iCND7i\u001c3f)\t\t)!\u0001\u0005u_N#(/\u001b8h)\u0005A\u0018AB3rk\u0006d7\u000f\u0006\u0003\u0002(\u0005\u0005\u0003\u0002CA\t\u001f\u0005\u0005\t\u0019A\u001b)\u0007\u0001\t)\u0005\u0005\u0003\u0002H\u0005-SBAA%\u0015\t\u0011h$\u0003\u0003\u0002N\u0005%#aC%oi\u0016\u0014h.\u00197Ba&\fQbU8ve\u000e,'+\u001a4J[Bd\u0007CA*\u0012'\u0011\t\"%!\u0016\u0011\t\u0005]\u0013QL\u0007\u0003\u00033R1!a\u0017}\u0003\tIw.C\u0002F\u00033\"\"!!\u0015\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\t\u0005\u0015\u00141\u000e\u000b\u0005\u0003O\ni\u0007\u0005\u0003T\u0001\u0005%\u0004cA\u0017\u0002l\u0011)q\u0006\u0006b\u0001c!)q\t\u0006a\u0001\u0013\u00069QO\\1qa2LX\u0003BA:\u0003\u0007#B!!\u001e\u0002|A!1%a\u001eJ\u0013\r\tI\b\n\u0002\u0007\u001fB$\u0018n\u001c8\t\u0013\u0005uT#!AA\u0002\u0005}\u0014a\u0001=%aA!1\u000bAAA!\ri\u00131\u0011\u0003\u0006_U\u0011\r!M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003\u0013\u00032!_AF\u0013\r\tiI\u001f\u0002\u0007\u001f\nTWm\u0019;")
@InternalApi
public final class SourceRefImpl<T>
implements SourceRef<T>,
Product,
Serializable {
    private final ActorRef initialPartnerRef;

    public static <T> Option<ActorRef> unapply(SourceRefImpl<T> x$0) {
        return SourceRefImpl$.MODULE$.unapply(x$0);
    }

    public static <T> SourceRefImpl<T> apply(ActorRef initialPartnerRef) {
        return SourceRefImpl$.MODULE$.apply(initialPartnerRef);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public final Source<T, NotUsed> getSource() {
        return SourceRef.getSource$(this);
    }

    public ActorRef initialPartnerRef() {
        return this.initialPartnerRef;
    }

    @Override
    public akka.stream.scaladsl.Source<T, NotUsed> source() {
        return Source$.MODULE$.fromGraph(new SourceRefStageImpl(OptionVal$Some$.MODULE$.apply(this.initialPartnerRef()))).mapMaterializedValue((Function1<SinkRef, NotUsed$> & Serializable)x$1 -> NotUsed$.MODULE$);
    }

    public <T> SourceRefImpl<T> copy(ActorRef initialPartnerRef) {
        return new SourceRefImpl<T>(initialPartnerRef);
    }

    public <T> ActorRef copy$default$1() {
        return this.initialPartnerRef();
    }

    @Override
    public String productPrefix() {
        return "SourceRefImpl";
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
        return x$1 instanceof SourceRefImpl;
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
        if (!(object instanceof SourceRefImpl)) return false;
        boolean bl = true;
        if (!bl) return false;
        SourceRefImpl sourceRefImpl = (SourceRefImpl)x$1;
        ActorRef actorRef2 = this.initialPartnerRef();
        ActorRef actorRef3 = sourceRefImpl.initialPartnerRef();
        if (actorRef2 != null) {
            if (!((Object)actorRef2).equals(actorRef3)) return false;
            return true;
        }
        if (actorRef3 == null) return true;
        return false;
    }

    public SourceRefImpl(ActorRef initialPartnerRef) {
        this.initialPartnerRef = initialPartnerRef;
        SourceRef.$init$(this);
        Product.$init$(this);
    }
}

