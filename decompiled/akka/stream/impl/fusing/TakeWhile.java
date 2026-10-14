/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.Supervision;
import akka.stream.Supervision$Stop$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.impl.fusing.TakeWhile$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0005Eh!B\u000f\u001f\u0005\u00122\u0003\u0002C+\u0001\u0005+\u0007I\u0011\u0001,\t\u0011u\u0003!\u0011#Q\u0001\n]C\u0001B\u0018\u0001\u0003\u0016\u0004%\ta\u0018\u0005\tA\u0002\u0011\t\u0012)A\u00055\")\u0011\r\u0001C\u0001E\")q\r\u0001C!Q\")Q\u000e\u0001C!]\")q\u000f\u0001C!q\"I\u00111\u0001\u0001\u0002\u0002\u0013\u0005\u0011Q\u0001\u0005\n\u0003+\u0001\u0011\u0013!C\u0001\u0003/A\u0011\"!\r\u0001#\u0003%\t!a\r\t\u0013\u0005m\u0002!!A\u0005B\u0005u\u0002\"CA'\u0001\u0005\u0005I\u0011AA(\u0011%\t9\u0006AA\u0001\n\u0003\tI\u0006C\u0005\u0002`\u0001\t\t\u0011\"\u0011\u0002b!I\u0011q\u000e\u0001\u0002\u0002\u0013\u0005\u0011\u0011\u000f\u0005\n\u0003k\u0002\u0011\u0011!C!\u0003oB\u0011\"a\u001f\u0001\u0003\u0003%\t%! \t\u0013\u0005}\u0004!!A\u0005B\u0005\u0005uACAI=\u0005\u0005\t\u0012\u0001\u0013\u0002\u0014\u001aIQDHA\u0001\u0012\u0003!\u0013Q\u0013\u0005\u0007CV!\t!a*\t\u00115,\u0012\u0011!C#\u0003SC\u0011\"a+\u0016\u0003\u0003%\t)!,\t\u0013\u0005uV#%A\u0005\u0002\u0005}\u0006\"CAb+\u0005\u0005I\u0011QAc\u0011%\t\t/FI\u0001\n\u0003\t\u0019\u000fC\u0005\u0002hV\t\t\u0011\"\u0003\u0002j\nIA+Y6f/\"LG.\u001a\u0006\u0003?\u0001\naAZ;tS:<'BA\u0011#\u0003\u0011IW\u000e\u001d7\u000b\u0005\r\"\u0013AB:ue\u0016\fWNC\u0001&\u0003\u0011\t7n[1\u0016\u0005\u001dj4\u0003\u0002\u0001)\u00132\u00032!\u000b\u001d<\u001d\tQcG\u0004\u0002,k9\u0011A\u0006\u000e\b\u0003[Mr!A\f\u001a\u000e\u0003=R!\u0001M\u0019\u0002\rq\u0012xn\u001c;?\u0007\u0001I\u0011!J\u0005\u0003G\u0011J!!\t\u0012\n\u0005}\u0001\u0013BA\u001c\u001f\u0003-9%/\u00199i'R\fw-Z:\n\u0005eR$AF*j[BdW\rT5oK\u0006\u0014xI]1qQN#\u0018mZ3\u000b\u0005]r\u0002C\u0001\u001f>\u0019\u0001!QA\u0010\u0001C\u0002}\u0012\u0011\u0001V\t\u0003\u0001\u001a\u0003\"!\u0011#\u000e\u0003\tS\u0011aQ\u0001\u0006g\u000e\fG.Y\u0005\u0003\u000b\n\u0013qAT8uQ&tw\r\u0005\u0002B\u000f&\u0011\u0001J\u0011\u0002\u0004\u0003:L\bCA!K\u0013\tY%IA\u0004Qe>$Wo\u0019;\u0011\u00055\u0013fB\u0001(Q\u001d\tqs*C\u0001D\u0013\t\t&)A\u0004qC\u000e\\\u0017mZ3\n\u0005M#&\u0001D*fe&\fG.\u001b>bE2,'BA)C\u0003\u0005\u0001X#A,\u0011\t\u0005C6HW\u0005\u00033\n\u0013\u0011BR;oGRLwN\\\u0019\u0011\u0005\u0005[\u0016B\u0001/C\u0005\u001d\u0011un\u001c7fC:\f!\u0001\u001d\u0011\u0002\u0013%t7\r\\;tSZ,W#\u0001.\u0002\u0015%t7\r\\;tSZ,\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0004G\u00164\u0007c\u00013\u0001w5\ta\u0004C\u0003V\u000b\u0001\u0007q\u000bC\u0004_\u000bA\u0005\t\u0019\u0001.\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/F\u0001j!\tQ7.D\u0001#\u0013\ta'E\u0001\u0006BiR\u0014\u0018NY;uKN\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002_B\u0011\u0001\u000f\u001e\b\u0003cJ\u0004\"A\f\"\n\u0005M\u0014\u0015A\u0002)sK\u0012,g-\u0003\u0002vm\n11\u000b\u001e:j]\u001eT!a\u001d\"\u0002\u0017\r\u0014X-\u0019;f\u0019><\u0017n\u0019\u000b\u0003s~\u0004\"A_?\u000e\u0003mT!\u0001 \u0012\u0002\u000bM$\u0018mZ3\n\u0005y\\(aD$sCBD7\u000b^1hK2{w-[2\t\r\u0005\u0005\u0001\u00021\u0001j\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t\u0003\u0011\u0019w\u000e]=\u0016\t\u0005\u001d\u0011Q\u0002\u000b\u0007\u0003\u0013\ty!a\u0005\u0011\t\u0011\u0004\u00111\u0002\t\u0004y\u00055A!\u0002 \n\u0005\u0004y\u0004\u0002C+\n!\u0003\u0005\r!!\u0005\u0011\u000b\u0005C\u00161\u0002.\t\u000fyK\u0001\u0013!a\u00015\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nT\u0003BA\r\u0003_)\"!a\u0007+\u0007]\u000bib\u000b\u0002\u0002 A!\u0011\u0011EA\u0016\u001b\t\t\u0019C\u0003\u0003\u0002&\u0005\u001d\u0012!C;oG\",7m[3e\u0015\r\tICQ\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA\u0017\u0003G\u0011\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\t\u0015q$B1\u0001@\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*B!!\u000e\u0002:U\u0011\u0011q\u0007\u0016\u00045\u0006uA!\u0002 \f\u0005\u0004y\u0014!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002@A!\u0011\u0011IA&\u001b\t\t\u0019E\u0003\u0003\u0002F\u0005\u001d\u0013\u0001\u00027b]\u001eT!!!\u0013\u0002\t)\fg/Y\u0005\u0004k\u0006\r\u0013\u0001\u00049s_\u0012,8\r^!sSRLXCAA)!\r\t\u00151K\u0005\u0004\u0003+\u0012%aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$Hc\u0001$\u0002\\!I\u0011Q\f\b\u0002\u0002\u0003\u0007\u0011\u0011K\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u0005\r\u0004#BA3\u0003W2UBAA4\u0015\r\tIGQ\u0001\u000bG>dG.Z2uS>t\u0017\u0002BA7\u0003O\u0012\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR\u0019!,a\u001d\t\u0011\u0005u\u0003#!AA\u0002\u0019\u000b!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011qHA=\u0011%\ti&EA\u0001\u0002\u0004\t\t&\u0001\u0005iCND7i\u001c3f)\t\t\t&\u0001\u0004fcV\fGn\u001d\u000b\u00045\u0006\r\u0005\u0002CA/'\u0005\u0005\t\u0019\u0001$)\u0007\u0001\t9\t\u0005\u0003\u0002\n\u00065UBAAF\u0015\r\tI\u0003J\u0005\u0005\u0003\u001f\u000bYIA\u0006J]R,'O\\1m\u0003BL\u0017!\u0003+bW\u0016<\u0006.\u001b7f!\t!WcE\u0003\u0016\u0003/\u000bi\nE\u0002B\u00033K1!a'C\u0005\u0019\te.\u001f*fMB!\u0011qTAS\u001b\t\t\tK\u0003\u0003\u0002$\u0006\u001d\u0013AA5p\u0013\r\u0019\u0016\u0011\u0015\u000b\u0003\u0003'#\"!a\u0010\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\t\u0005=\u0016Q\u0017\u000b\u0007\u0003c\u000b9,a/\u0011\t\u0011\u0004\u00111\u0017\t\u0004y\u0005UF!\u0002 \u0019\u0005\u0004y\u0004BB+\u0019\u0001\u0004\tI\fE\u0003B1\u0006M&\fC\u0004_1A\u0005\t\u0019\u0001.\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uII*B!!\u000e\u0002B\u0012)a(\u0007b\u0001\u007f\u00059QO\\1qa2LX\u0003BAd\u00033$B!!3\u0002\\B)\u0011)a3\u0002P&\u0019\u0011Q\u001a\"\u0003\r=\u0003H/[8o!\u0019\t\u0015\u0011[Ak5&\u0019\u00111\u001b\"\u0003\rQ+\b\u000f\\33!\u0015\t\u0005,a6[!\ra\u0014\u0011\u001c\u0003\u0006}i\u0011\ra\u0010\u0005\n\u0003;T\u0012\u0011!a\u0001\u0003?\f1\u0001\u001f\u00131!\u0011!\u0007!a6\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00133+\u0011\t)$!:\u0005\u000byZ\"\u0019A \u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005-\b\u0003BA!\u0003[LA!a<\u0002D\t1qJ\u00196fGR\u0004")
@InternalApi
public final class TakeWhile<T>
extends GraphStages.SimpleLinearGraphStage<T>
implements Product,
Serializable {
    private final Function1<T, Object> p;
    private final boolean inclusive;

    public static <T> boolean $lessinit$greater$default$2() {
        return TakeWhile$.MODULE$.$lessinit$greater$default$2();
    }

    public static <T> Option<Tuple2<Function1<T, Object>, Object>> unapply(TakeWhile<T> x$0) {
        return TakeWhile$.MODULE$.unapply(x$0);
    }

    public static <T> boolean apply$default$2() {
        return TakeWhile$.MODULE$.apply$default$2();
    }

    public static <T> TakeWhile<T> apply(Function1<T, Object> p, boolean inclusive) {
        return TakeWhile$.MODULE$.apply(p, inclusive);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Function1<T, Object> p() {
        return this.p;
    }

    public boolean inclusive() {
        return this.inclusive;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.takeWhile().and(Attributes$SourceLocation$.MODULE$.forLambda(this.p()));
    }

    public String toString() {
        return "TakeWhile";
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this, inheritedAttributes){
            private final /* synthetic */ TakeWhile $outer;
            private final Attributes inheritedAttributes$3;

            public void onUpstreamFinish() throws Exception {
                InHandler.onUpstreamFinish$(this);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public String toString() {
                return "TakeWhileLogic";
            }

            private Function1<Throwable, Supervision.Directive> decider() {
                return ((ActorAttributes.SupervisionStrategy)this.inheritedAttributes$3.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
            }

            public void onPush() {
                try {
                    T elem = this.grab(this.$outer.in());
                    if (BoxesRunTime.unboxToBoolean(this.$outer.p().apply(elem))) {
                        this.push(this.$outer.out(), elem);
                    } else {
                        if (this.$outer.inclusive()) {
                            this.push(this.$outer.out(), elem);
                        }
                        this.completeStage();
                    }
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable ex = option.get();
                        Supervision.Directive directive = this.decider().apply(ex);
                        if (Supervision$Stop$.MODULE$.equals(directive)) {
                            this.failStage(ex);
                        } else {
                            this.pull(this.$outer.in());
                        }
                    }
                    throw throwable;
                }
            }

            public void onPull() {
                this.pull(this.$outer.in());
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$3 = inheritedAttributes$3;
                super($outer.shape());
                OutHandler.$init$(this);
                InHandler.$init$(this);
                this.setHandlers($outer.in(), $outer.out(), this);
            }
        };
    }

    public <T> TakeWhile<T> copy(Function1<T, Object> p, boolean inclusive) {
        return new TakeWhile<T>(p, inclusive);
    }

    public <T> Function1<T, Object> copy$default$1() {
        return this.p();
    }

    public <T> boolean copy$default$2() {
        return this.inclusive();
    }

    @Override
    public String productPrefix() {
        return "TakeWhile";
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
                return this.p();
            }
            case 1: {
                return BoxesRunTime.boxToBoolean(this.inclusive());
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
        return x$1 instanceof TakeWhile;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "p";
            }
            case 1: {
                return "inclusive";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.p()));
        n = Statics.mix(n, this.inclusive() ? 1231 : 1237);
        return Statics.finalizeHash(n, 2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof TakeWhile)) return false;
        boolean bl = true;
        if (!bl) return false;
        TakeWhile takeWhile = (TakeWhile)x$1;
        if (this.inclusive() != takeWhile.inclusive()) return false;
        Function1<T, Object> function1 = this.p();
        Function1<T, Object> function12 = takeWhile.p();
        if (function1 != null) {
            if (!function1.equals(function12)) return false;
            return true;
        }
        if (function12 == null) return true;
        return false;
    }

    public TakeWhile(Function1<T, Object> p, boolean inclusive) {
        this.p = p;
        this.inclusive = inclusive;
        Product.$init$(this);
    }
}

