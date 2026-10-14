/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.Supervision;
import akka.stream.Supervision$Restart$;
import akka.stream.Supervision$Stop$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.Fold$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import scala.Function1;
import scala.Function2;
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

@ScalaSignature(bytes="\u0006\u0005\tMa!\u0002\u0012$\u0005&Z\u0003\u0002\u0003-\u0001\u0005+\u0007I\u0011A-\t\u0011i\u0003!\u0011#Q\u0001\n\u0019C\u0001b\u0017\u0001\u0003\u0016\u0004%\t\u0001\u0018\u0005\tA\u0002\u0011\t\u0012)A\u0005;\")\u0011\r\u0001C\u0001E\"9q\r\u0001b\u0001\n\u0003A\u0007B\u00027\u0001A\u0003%\u0011\u000eC\u0004n\u0001\t\u0007I\u0011\u00018\t\rI\u0004\u0001\u0015!\u0003p\u0011\u001d\u0019\bA1A\u0005BQDa!\u001e\u0001!\u0002\u0013\u0019\u0004\"\u0002<\u0001\t\u0003:\b\"CA\u0001\u0001\t\u0007I\u0011IA\u0002\u0011!\tY\u0001\u0001Q\u0001\n\u0005\u0015\u0001bBA\u0007\u0001\u0011\u0005\u0013q\u0002\u0005\n\u00037\u0001\u0011\u0011!C\u0001\u0003;A\u0011\"!\r\u0001#\u0003%\t!a\r\t\u0013\u0005=\u0003!%A\u0005\u0002\u0005E\u0003\"CA.\u0001\u0005\u0005I\u0011IA/\u0011%\ti\u0007AA\u0001\n\u0003\ty\u0007C\u0005\u0002x\u0001\t\t\u0011\"\u0001\u0002z!I\u0011q\u0010\u0001\u0002\u0002\u0013\u0005\u0013\u0011\u0011\u0005\n\u0003\u001f\u0003\u0011\u0011!C\u0001\u0003#C\u0011\"a'\u0001\u0003\u0003%\t%!(\t\u0013\u0005\u0005\u0006!!A\u0005B\u0005\r\u0006\"CAS\u0001\u0005\u0005I\u0011IAT\u000f)\t9lIA\u0001\u0012\u0003I\u0013\u0011\u0018\u0004\nE\r\n\t\u0011#\u0001*\u0003wCa!\u0019\u000f\u0005\u0002\u00055\u0007\u0002\u0003<\u001d\u0003\u0003%)%a4\t\u0013\u0005EG$!A\u0005\u0002\u0006M\u0007\"CAt9\u0005\u0005I\u0011QAu\u0011%\u0011I\u0001HA\u0001\n\u0013\u0011YA\u0001\u0003G_2$'B\u0001\u0013&\u0003\u00191Wo]5oO*\u0011aeJ\u0001\u0005S6\u0004HN\u0003\u0002)S\u000511\u000f\u001e:fC6T\u0011AK\u0001\u0005C.\\\u0017-F\u0002-s\u001d\u001bB\u0001A\u0017J\u0019B\u0019a&M\u001a\u000e\u0003=R!\u0001M\u0014\u0002\u000bM$\u0018mZ3\n\u0005Iz#AC$sCBD7\u000b^1hKB!A'N\u001cG\u001b\u00059\u0013B\u0001\u001c(\u0005%1En\\<TQ\u0006\u0004X\r\u0005\u00029s1\u0001A!\u0002\u001e\u0001\u0005\u0004a$AA%o\u0007\u0001\t\"!P\"\u0011\u0005y\nU\"A \u000b\u0003\u0001\u000bQa]2bY\u0006L!AQ \u0003\u000f9{G\u000f[5oOB\u0011a\bR\u0005\u0003\u000b~\u00121!\u00118z!\tAt\tB\u0003I\u0001\t\u0007AHA\u0002PkR\u0004\"A\u0010&\n\u0005-{$a\u0002)s_\u0012,8\r\u001e\t\u0003\u001bVs!AT*\u000f\u0005=\u0013V\"\u0001)\u000b\u0005E[\u0014A\u0002\u001fs_>$h(C\u0001A\u0013\t!v(A\u0004qC\u000e\\\u0017mZ3\n\u0005Y;&\u0001D*fe&\fG.\u001b>bE2,'B\u0001+@\u0003\u0011QXM]8\u0016\u0003\u0019\u000bQA_3s_\u0002\n\u0011AZ\u000b\u0002;B)aH\u0018$8\r&\u0011ql\u0010\u0002\n\rVt7\r^5p]J\n!A\u001a\u0011\u0002\rqJg.\u001b;?)\r\u0019WM\u001a\t\u0005I\u00029d)D\u0001$\u0011\u0015AV\u00011\u0001G\u0011\u0015YV\u00011\u0001^\u0003\tIg.F\u0001j!\r!$nN\u0005\u0003W\u001e\u0012Q!\u00138mKR\f1!\u001b8!\u0003\ryW\u000f^\u000b\u0002_B\u0019A\u0007\u001d$\n\u0005E<#AB(vi2,G/\u0001\u0003pkR\u0004\u0013!B:iCB,W#A\u001a\u0002\rMD\u0017\r]3!\u0003!!xn\u0015;sS:<G#\u0001=\u0011\u0005elhB\u0001>|!\tyu(\u0003\u0002}\u007f\u00051\u0001K]3eK\u001aL!A`@\u0003\rM#(/\u001b8h\u0015\tax(A\tj]&$\u0018.\u00197BiR\u0014\u0018NY;uKN,\"!!\u0002\u0011\u0007Q\n9!C\u0002\u0002\n\u001d\u0012!\"\u0011;ue&\u0014W\u000f^3t\u0003IIg.\u001b;jC2\fE\u000f\u001e:jEV$Xm\u001d\u0011\u0002\u0017\r\u0014X-\u0019;f\u0019><\u0017n\u0019\u000b\u0005\u0003#\t9\u0002E\u0002/\u0003'I1!!\u00060\u0005=9%/\u00199i'R\fw-\u001a'pO&\u001c\u0007bBA\r\u001f\u0001\u0007\u0011QA\u0001\u0014S:DWM]5uK\u0012\fE\u000f\u001e:jEV$Xm]\u0001\u0005G>\u0004\u00180\u0006\u0004\u0002 \u0005\u0015\u0012\u0011\u0006\u000b\u0007\u0003C\tY#!\f\u0011\r\u0011\u0004\u00111EA\u0014!\rA\u0014Q\u0005\u0003\u0006uA\u0011\r\u0001\u0010\t\u0004q\u0005%B!\u0002%\u0011\u0005\u0004a\u0004\u0002\u0003-\u0011!\u0003\u0005\r!a\n\t\u0011m\u0003\u0002\u0013!a\u0001\u0003_\u0001\u0002B\u00100\u0002(\u0005\r\u0012qE\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0019\t)$a\u0013\u0002NU\u0011\u0011q\u0007\u0016\u0004\r\u0006e2FAA\u001e!\u0011\ti$a\u0012\u000e\u0005\u0005}\"\u0002BA!\u0003\u0007\n\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005\u0015s(\u0001\u0006b]:|G/\u0019;j_:LA!!\u0013\u0002@\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0005\u000bi\n\"\u0019\u0001\u001f\u0005\u000b!\u000b\"\u0019\u0001\u001f\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU1\u00111KA,\u00033*\"!!\u0016+\u0007u\u000bI\u0004B\u0003;%\t\u0007A\bB\u0003I%\t\u0007A(A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003?\u0002B!!\u0019\u0002l5\u0011\u00111\r\u0006\u0005\u0003K\n9'\u0001\u0003mC:<'BAA5\u0003\u0011Q\u0017M^1\n\u0007y\f\u0019'\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0006\u0002\u0002rA\u0019a(a\u001d\n\u0007\u0005UtHA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000fF\u0002D\u0003wB\u0011\"! \u0016\u0003\u0003\u0005\r!!\u001d\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\t\u0019\tE\u0003\u0002\u0006\u0006-5)\u0004\u0002\u0002\b*\u0019\u0011\u0011R \u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002\u000e\u0006\u001d%\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!a%\u0002\u001aB\u0019a(!&\n\u0007\u0005]uHA\u0004C_>dW-\u00198\t\u0011\u0005ut#!AA\u0002\r\u000b!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011qLAP\u0011%\ti\bGA\u0001\u0002\u0004\t\t(\u0001\u0005iCND7i\u001c3f)\t\t\t(\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003'\u000bI\u000b\u0003\u0005\u0002~i\t\t\u00111\u0001DQ\r\u0001\u0011Q\u0016\t\u0005\u0003_\u000b\u0019,\u0004\u0002\u00022*\u0019\u0011QI\u0015\n\t\u0005U\u0016\u0011\u0017\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018.\u0001\u0003G_2$\u0007C\u00013\u001d'\u0015a\u0012QXAb!\rq\u0014qX\u0005\u0004\u0003\u0003|$AB!osJ+g\r\u0005\u0003\u0002F\u0006-WBAAd\u0015\u0011\tI-a\u001a\u0002\u0005%|\u0017b\u0001,\u0002HR\u0011\u0011\u0011\u0018\u000b\u0003\u0003?\nQ!\u00199qYf,b!!6\u0002\\\u0006}GCBAl\u0003C\f\u0019\u000f\u0005\u0004e\u0001\u0005e\u0017Q\u001c\t\u0004q\u0005mG!\u0002\u001e \u0005\u0004a\u0004c\u0001\u001d\u0002`\u0012)\u0001j\bb\u0001y!1\u0001l\ba\u0001\u0003;DaaW\u0010A\u0002\u0005\u0015\b\u0003\u0003 _\u0003;\fI.!8\u0002\u000fUt\u0017\r\u001d9msV1\u00111\u001eB\u0001\u0003w$B!!<\u0003\u0004A)a(a<\u0002t&\u0019\u0011\u0011_ \u0003\r=\u0003H/[8o!\u001dq\u0014Q_A}\u0003{L1!a>@\u0005\u0019!V\u000f\u001d7feA\u0019\u0001(a?\u0005\u000b!\u0003#\u0019\u0001\u001f\u0011\u0011yr\u0016\u0011`A\u0000\u0003s\u00042\u0001\u000fB\u0001\t\u0015Q\u0004E1\u0001=\u0011%\u0011)\u0001IA\u0001\u0002\u0004\u00119!A\u0002yIA\u0002b\u0001\u001a\u0001\u0002\u0000\u0006e\u0018\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001B\u0007!\u0011\t\tGa\u0004\n\t\tE\u00111\r\u0002\u0007\u001f\nTWm\u0019;")
@InternalApi
public final class Fold<In, Out>
extends GraphStage<FlowShape<In, Out>>
implements Product,
Serializable {
    private final Out zero;
    private final Function2<Out, In, Out> f;
    private final Inlet<In> in;
    private final Outlet<Out> out;
    private final FlowShape<In, Out> shape;
    private final Attributes initialAttributes;

    public static <In, Out> Option<Tuple2<Out, Function2<Out, In, Out>>> unapply(Fold<In, Out> x$0) {
        return Fold$.MODULE$.unapply(x$0);
    }

    public static <In, Out> Fold<In, Out> apply(Out zero, Function2<Out, In, Out> f) {
        return Fold$.MODULE$.apply(zero, f);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Out zero() {
        return this.zero;
    }

    public Function2<Out, In, Out> f() {
        return this.f;
    }

    public Inlet<In> in() {
        return this.in;
    }

    public Outlet<Out> out() {
        return this.out;
    }

    @Override
    public FlowShape<In, Out> shape() {
        return this.shape;
    }

    public String toString() {
        return "Fold";
    }

    @Override
    public Attributes initialAttributes() {
        return this.initialAttributes;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this, inheritedAttributes){
            private Out aggregator;
            private final /* synthetic */ Fold $outer;
            private final Attributes inheritedAttributes$8;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            private Out aggregator() {
                return this.aggregator;
            }

            private void aggregator_$eq(Out x$1) {
                this.aggregator = x$1;
            }

            private Function1<Throwable, Supervision.Directive> decider() {
                return ((ActorAttributes.SupervisionStrategy)this.inheritedAttributes$8.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
            }

            public void onPush() {
                block10: {
                    In elem = this.grab(this.$outer.in());
                    try {
                        try {
                            this.aggregator_$eq(this.$outer.f().apply(this.aggregator(), elem));
                        }
                        catch (Throwable throwable) {
                            Option<Throwable> option;
                            Throwable throwable2 = throwable;
                            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                                Throwable ex = option.get();
                                Supervision.Directive directive = this.decider().apply(ex);
                                if (Supervision$Stop$.MODULE$.equals(directive)) {
                                    this.failStage(ex);
                                } else if (Supervision$Restart$.MODULE$.equals(directive)) {
                                    this.aggregator_$eq(this.$outer.zero());
                                }
                                break block10;
                            }
                            throw throwable;
                        }
                    }
                    finally {
                        if (!this.isClosed(this.$outer.in())) {
                            this.pull(this.$outer.in());
                        }
                    }
                }
            }

            public void onPull() {
                if (this.isClosed(this.$outer.in())) {
                    this.push(this.$outer.out(), this.aggregator());
                    this.completeStage();
                    return;
                }
                this.pull(this.$outer.in());
            }

            public void onUpstreamFinish() {
                if (this.isAvailable(this.$outer.out())) {
                    this.push(this.$outer.out(), this.aggregator());
                    this.completeStage();
                    return;
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$8 = inheritedAttributes$8;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.aggregator = $outer.zero();
                this.setHandlers($outer.in(), $outer.out(), this);
            }
        };
    }

    public <In, Out> Fold<In, Out> copy(Out zero, Function2<Out, In, Out> f) {
        return new Fold<In, Out>(zero, f);
    }

    public <In, Out> Out copy$default$1() {
        return this.zero();
    }

    public <In, Out> Function2<Out, In, Out> copy$default$2() {
        return this.f();
    }

    @Override
    public String productPrefix() {
        return "Fold";
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
                return this.zero();
            }
            case 1: {
                return this.f();
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
        return x$1 instanceof Fold;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "zero";
            }
            case 1: {
                return "f";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof Fold)) return false;
        boolean bl = true;
        if (!bl) return false;
        Fold fold = (Fold)x$1;
        if (!BoxesRunTime.equals(this.zero(), fold.zero())) return false;
        Function2<Out, In, Out> function2 = this.f();
        Function2<Out, In, Out> function22 = fold.f();
        if (function2 != null) {
            if (!function2.equals(function22)) return false;
            return true;
        }
        if (function22 == null) return true;
        return false;
    }

    public Fold(Out zero, Function2<Out, In, Out> f) {
        this.zero = zero;
        this.f = f;
        Product.$init$(this);
        this.in = Inlet$.MODULE$.apply("Fold.in");
        this.out = Outlet$.MODULE$.apply("Fold.out");
        this.shape = new FlowShape<In, Out>(this.in(), this.out());
        this.initialAttributes = Stages$DefaultAttributes$.MODULE$.fold().and(Attributes$SourceLocation$.MODULE$.forLambda(f));
    }
}

