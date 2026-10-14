/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.Collect$;
import akka.stream.impl.fusing.SupervisedGraphStageLogic;
import akka.stream.stage.GraphStage;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005mxAB\u0010!\u0011\u0003!\u0003F\u0002\u0004+A!\u0005Ae\u000b\u0005\u0006u\u0005!\t\u0001\u0010\u0005\b{\u0005\u0011\r\u0011\"\u0002?\u0011\u0019)\u0015\u0001)A\u0007\u007f!9a)AA\u0001\n\u0003;\u0005\"CAk\u0003\u0005\u0005I\u0011QAl\u0011%\t\t0AA\u0001\n\u0013\t\u0019PB\u0003+A\t3#\n\u0003\u0005p\u0011\tU\r\u0011\"\u0001q\u0011!!\bB!E!\u0002\u0013\t\b\"\u0002\u001e\t\t\u0003)\bb\u0002=\t\u0005\u0004%\t!\u001f\u0005\u0007{\"\u0001\u000b\u0011\u0002>\t\u000fyD!\u0019!C\u0001\u007f\"A\u0011q\u0001\u0005!\u0002\u0013\t\t\u0001C\u0005\u0002\n!\u0011\r\u0011\"\u0011\u0002\f!9\u0011Q\u0002\u0005!\u0002\u0013\u0011\u0006bBA\b\u0011\u0011\u0005\u0013\u0011\u0003\u0005\b\u00033AA\u0011AA\u000e\u0011\u001d\tI\u0004\u0003C!\u0003wA\u0011\"!\u0013\t\u0003\u0003%\t!a\u0013\t\u0013\u0005u\u0003\"%A\u0005\u0002\u0005}\u0003\"CA>\u0011\u0005\u0005I\u0011IA?\u0011%\ty\bCA\u0001\n\u0003\t\t\tC\u0005\u0002\n\"\t\t\u0011\"\u0001\u0002\f\"I\u0011\u0011\u0013\u0005\u0002\u0002\u0013\u0005\u00131\u0013\u0005\n\u0003CC\u0011\u0011!C\u0001\u0003GC\u0011\"!,\t\u0003\u0003%\t%a,\t\u0013\u0005M\u0006\"!A\u0005B\u0005U\u0006\"CA\\\u0011\u0005\u0005I\u0011IA]\u0003\u001d\u0019u\u000e\u001c7fGRT!!\t\u0012\u0002\r\u0019,8/\u001b8h\u0015\t\u0019C%\u0001\u0003j[Bd'BA\u0013'\u0003\u0019\u0019HO]3b[*\tq%\u0001\u0003bW.\f\u0007CA\u0015\u0002\u001b\u0005\u0001#aB\"pY2,7\r^\n\u0004\u00031\u0012\u0004CA\u00171\u001b\u0005q#\"A\u0018\u0002\u000bM\u001c\u0017\r\\1\n\u0005Er#AB!osJ+g\r\u0005\u00024q5\tAG\u0003\u00026m\u0005\u0011\u0011n\u001c\u0006\u0002o\u0005!!.\u0019<b\u0013\tIDG\u0001\u0007TKJL\u0017\r\\5{C\ndW-\u0001\u0004=S:LGOP\u0002\u0001)\u0005A\u0013A\u0003(pi\u0006\u0003\b\u000f\\5fIV\tq\b\u0005\u0003.\u0001\n\u0013\u0015BA!/\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0002.\u0007&\u0011AI\f\u0002\u0004\u0003:L\u0018a\u0003(pi\u0006\u0003\b\u000f\\5fI\u0002\nQ!\u00199qYf,R\u0001SAf\u0003\u001f$2!SAi!\u0019I\u0003\"!3\u0002NV\u00191\nW0\u0014\t!a\u0015\r\u001a\t\u0004\u001bB\u0013V\"\u0001(\u000b\u0005=#\u0013!B:uC\u001e,\u0017BA)O\u0005)9%/\u00199i'R\fw-\u001a\t\u0005'R3f,D\u0001%\u0013\t)FEA\u0005GY><8\u000b[1qKB\u0011q\u000b\u0017\u0007\u0001\t\u0015I\u0006B1\u0001[\u0005\tIe.\u0005\u0002\\\u0005B\u0011Q\u0006X\u0005\u0003;:\u0012qAT8uQ&tw\r\u0005\u0002X?\u0012)\u0001\r\u0003b\u00015\n\u0019q*\u001e;\u0011\u00055\u0012\u0017BA2/\u0005\u001d\u0001&o\u001c3vGR\u0004\"!Z7\u000f\u0005\u0019\\gBA4k\u001b\u0005A'BA5<\u0003\u0019a$o\\8u}%\tq&\u0003\u0002m]\u00059\u0001/Y2lC\u001e,\u0017BA\u001do\u0015\tag&\u0001\u0002qMV\t\u0011\u000f\u0005\u0003.eZs\u0016BA:/\u0005=\u0001\u0016M\u001d;jC24UO\\2uS>t\u0017a\u00019gAQ\u0011ao\u001e\t\u0005S!1f\fC\u0003p\u0017\u0001\u0007\u0011/\u0001\u0002j]V\t!\u0010E\u0002TwZK!\u0001 \u0013\u0003\u000b%sG.\u001a;\u0002\u0007%t\u0007%A\u0002pkR,\"!!\u0001\u0011\tM\u000b\u0019AX\u0005\u0004\u0003\u000b!#AB(vi2,G/\u0001\u0003pkR\u0004\u0013!B:iCB,W#\u0001*\u0002\rMD\u0017\r]3!\u0003EIg.\u001b;jC2\fE\u000f\u001e:jEV$Xm]\u000b\u0003\u0003'\u00012aUA\u000b\u0013\r\t9\u0002\n\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018aC2sK\u0006$X\rT8hS\u000e$B!!\b\u00026IA\u0011qDA\u0012\u0003S\tyC\u0002\u0004\u0002\"M\u0001\u0011Q\u0004\u0002\ryI,g-\u001b8f[\u0016tGO\u0010\t\u0004S\u0005\u0015\u0012bAA\u0014A\tI2+\u001e9feZL7/\u001a3He\u0006\u0004\bn\u0015;bO\u0016dunZ5d!\ri\u00151F\u0005\u0004\u0003[q%!C%o\u0011\u0006tG\r\\3s!\ri\u0015\u0011G\u0005\u0004\u0003gq%AC(vi\"\u000bg\u000e\u001a7fe\"9\u0011qG\nA\u0002\u0005M\u0011aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005u\u0002\u0003BA \u0003\u000bj!!!\u0011\u000b\u0007\u0005\rc'\u0001\u0003mC:<\u0017\u0002BA$\u0003\u0003\u0012aa\u0015;sS:<\u0017\u0001B2paf,b!!\u0014\u0002T\u0005]C\u0003BA(\u00033\u0002b!\u000b\u0005\u0002R\u0005U\u0003cA,\u0002T\u0011)\u0011,\u0006b\u00015B\u0019q+a\u0016\u0005\u000b\u0001,\"\u0019\u0001.\t\u0011=,\u0002\u0013!a\u0001\u00037\u0002b!\f:\u0002R\u0005U\u0013AD2paf$C-\u001a4bk2$H%M\u000b\u0007\u0003C\n9(!\u001f\u0016\u0005\u0005\r$fA9\u0002f-\u0012\u0011q\r\t\u0005\u0003S\n\u0019(\u0004\u0002\u0002l)!\u0011QNA8\u0003%)hn\u00195fG.,GMC\u0002\u0002r9\n!\"\u00198o_R\fG/[8o\u0013\u0011\t)(a\u001b\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW\rB\u0003Z-\t\u0007!\fB\u0003a-\t\u0007!,A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003{\tA\u0002\u001d:pIV\u001cG/\u0011:jif,\"!a!\u0011\u00075\n))C\u0002\u0002\b:\u00121!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$2AQAG\u0011%\ty)GA\u0001\u0002\u0004\t\u0019)A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003+\u0003R!a&\u0002\u001e\nk!!!'\u000b\u0007\u0005me&\u0001\u0006d_2dWm\u0019;j_:LA!a(\u0002\u001a\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\t)+a+\u0011\u00075\n9+C\u0002\u0002*:\u0012qAQ8pY\u0016\fg\u000e\u0003\u0005\u0002\u0010n\t\t\u00111\u0001C\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\u0005u\u0012\u0011\u0017\u0005\n\u0003\u001fc\u0012\u0011!a\u0001\u0003\u0007\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0003\u0007\u000ba!Z9vC2\u001cH\u0003BAS\u0003wC\u0001\"a$\u001f\u0003\u0003\u0005\rA\u0011\u0015\u0004\u0011\u0005}\u0006\u0003BAa\u0003\u000bl!!a1\u000b\u0007\u0005Ed%\u0003\u0003\u0002H\u0006\r'aC%oi\u0016\u0014h.\u00197Ba&\u00042aVAf\t\u0015IVA1\u0001[!\r9\u0016q\u001a\u0003\u0006A\u0016\u0011\rA\u0017\u0005\u0007_\u0016\u0001\r!a5\u0011\r5\u0012\u0018\u0011ZAg\u0003\u001d)h.\u00199qYf,b!!7\u0002f\u0006%H\u0003BAn\u0003W\u0004R!LAo\u0003CL1!a8/\u0005\u0019y\u0005\u000f^5p]B1QF]Ar\u0003O\u00042aVAs\t\u0015IfA1\u0001[!\r9\u0016\u0011\u001e\u0003\u0006A\u001a\u0011\rA\u0017\u0005\n\u0003[4\u0011\u0011!a\u0001\u0003_\f1\u0001\u001f\u00131!\u0019I\u0003\"a9\u0002h\u0006aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011Q\u001f\t\u0005\u0003\u007f\t90\u0003\u0003\u0002z\u0006\u0005#AB(cU\u0016\u001cG\u000f")
@InternalApi
public final class Collect<In, Out>
extends GraphStage<FlowShape<In, Out>>
implements Product,
Serializable {
    private final PartialFunction<In, Out> pf;
    private final Inlet<In> in;
    private final Outlet<Out> out;
    private final FlowShape<In, Out> shape;

    public static <In, Out> Option<PartialFunction<In, Out>> unapply(Collect<In, Out> x$0) {
        return Collect$.MODULE$.unapply(x$0);
    }

    public static <In, Out> Collect<In, Out> apply(PartialFunction<In, Out> pf) {
        return Collect$.MODULE$.apply(pf);
    }

    public static Function1<Object, Object> NotApplied() {
        return Collect$.MODULE$.NotApplied();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public PartialFunction<In, Out> pf() {
        return this.pf;
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

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.collect().and(Attributes$SourceLocation$.MODULE$.forLambda(this.pf()));
    }

    @Override
    public SupervisedGraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this, inheritedAttributes){
            private final Function0<Object> wrappedPf;
            private final /* synthetic */ Collect $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFinish() throws Exception {
                InHandler.onUpstreamFinish$(this);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            private Function0<Object> wrappedPf() {
                return this.wrappedPf;
            }

            public void onPush() {
                Object object = this.withSupervision(this.wrappedPf());
                Object object2 = OptionVal$Some$.MODULE$.unapply(object);
                if (!OptionVal$.MODULE$.isEmpty$extension(object2)) {
                    Object result;
                    Object object3 = result = OptionVal$.MODULE$.get$extension(object2);
                    Function1<Object, Object> function1 = Collect$.MODULE$.NotApplied();
                    Object object4 = object3;
                    if (!(function1 != null ? !function1.equals(object4) : object4 != null)) {
                        this.pull(this.$outer.in());
                    } else if (object3 instanceof Object) {
                        Object object5 = object3;
                        this.push(this.$outer.out(), object5);
                    } else {
                        throw new RuntimeException();
                    }
                    return;
                }
            }

            public void onResume(Throwable t2) {
                if (!this.hasBeenPulled(this.$outer.in())) {
                    this.pull(this.$outer.in());
                    return;
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
                super(inheritedAttributes$5, $outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.wrappedPf = (Function0<Object> & Serializable)() -> $this.$outer.pf().applyOrElse(this.grab($this.$outer.in()), Collect$.MODULE$.NotApplied());
                this.setHandlers($outer.in(), $outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$wrappedPf$1(akka.stream.impl.fusing.Collect$$anon$6 )}, serializedLambda);
            }
        };
    }

    public String toString() {
        return "Collect";
    }

    public <In, Out> Collect<In, Out> copy(PartialFunction<In, Out> pf) {
        return new Collect<In, Out>(pf);
    }

    public <In, Out> PartialFunction<In, Out> copy$default$1() {
        return this.pf();
    }

    @Override
    public String productPrefix() {
        return "Collect";
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
                return this.pf();
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
        return x$1 instanceof Collect;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "pf";
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
        if (!(object instanceof Collect)) return false;
        boolean bl = true;
        if (!bl) return false;
        Collect collect2 = (Collect)x$1;
        PartialFunction<In, Out> partialFunction = this.pf();
        PartialFunction<In, Out> partialFunction2 = collect2.pf();
        if (partialFunction != null) {
            if (!partialFunction.equals(partialFunction2)) return false;
            return true;
        }
        if (partialFunction2 == null) return true;
        return false;
    }

    public Collect(PartialFunction<In, Out> pf) {
        this.pf = pf;
        Product.$init$(this);
        this.in = Inlet$.MODULE$.apply("Collect.in");
        this.out = Outlet$.MODULE$.apply("Collect.out");
        this.shape = new FlowShape<In, Out>(this.in(), this.out());
    }
}

