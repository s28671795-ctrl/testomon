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
import akka.stream.Supervision$Stop$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.Map$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0005mg!\u0002\u0010 \u0005\u0016:\u0003\u0002\u0003+\u0001\u0005+\u0007I\u0011A+\t\u0011e\u0003!\u0011#Q\u0001\nYCQA\u0017\u0001\u0005\u0002mCqa\u0018\u0001C\u0002\u0013\u0005\u0001\r\u0003\u0004e\u0001\u0001\u0006I!\u0019\u0005\bK\u0002\u0011\r\u0011\"\u0001g\u0011\u0019Q\u0007\u0001)A\u0005O\"91\u000e\u0001b\u0001\n\u0003b\u0007BB7\u0001A\u0003%q\u0006C\u0003o\u0001\u0011\u0005s\u000eC\u0003t\u0001\u0011\u0005C\u000fC\u0004{\u0001\u0005\u0005I\u0011A>\t\u0013\u0005%\u0001!%A\u0005\u0002\u0005-\u0001\"CA\u0014\u0001\u0005\u0005I\u0011IA\u0015\u0011%\tY\u0004AA\u0001\n\u0003\ti\u0004C\u0005\u0002F\u0001\t\t\u0011\"\u0001\u0002H!I\u0011Q\n\u0001\u0002\u0002\u0013\u0005\u0013q\n\u0005\n\u0003;\u0002\u0011\u0011!C\u0001\u0003?B\u0011\"!\u001b\u0001\u0003\u0003%\t%a\u001b\t\u0013\u0005=\u0004!!A\u0005B\u0005E\u0004\"CA:\u0001\u0005\u0005I\u0011IA;\u0011%\t9\bAA\u0001\n\u0003\nIh\u0002\u0006\u0002\n~\t\t\u0011#\u0001&\u0003\u00173\u0011BH\u0010\u0002\u0002#\u0005Q%!$\t\riCB\u0011AAP\u0011%\t\u0019\bGA\u0001\n\u000b\n)\bC\u0005\u0002\"b\t\t\u0011\"!\u0002$\"I\u0011Q\u0017\r\u0002\u0002\u0013\u0005\u0015q\u0017\u0005\n\u0003#D\u0012\u0011!C\u0005\u0003'\u00141!T1q\u0015\t\u0001\u0013%\u0001\u0004gkNLgn\u001a\u0006\u0003E\r\nA![7qY*\u0011A%J\u0001\u0007gR\u0014X-Y7\u000b\u0003\u0019\nA!Y6lCV\u0019\u0001&N\"\u0014\t\u0001IS\t\u0013\t\u0004U5zS\"A\u0016\u000b\u00051\u001a\u0013!B:uC\u001e,\u0017B\u0001\u0018,\u0005)9%/\u00199i'R\fw-\u001a\t\u0005aE\u001a$)D\u0001$\u0013\t\u00114EA\u0005GY><8\u000b[1qKB\u0011A'\u000e\u0007\u0001\t\u00151\u0004A1\u00019\u0005\tIen\u0001\u0001\u0012\u0005ez\u0004C\u0001\u001e>\u001b\u0005Y$\"\u0001\u001f\u0002\u000bM\u001c\u0017\r\\1\n\u0005yZ$a\u0002(pi\"Lgn\u001a\t\u0003u\u0001K!!Q\u001e\u0003\u0007\u0005s\u0017\u0010\u0005\u00025\u0007\u0012)A\t\u0001b\u0001q\t\u0019q*\u001e;\u0011\u0005i2\u0015BA$<\u0005\u001d\u0001&o\u001c3vGR\u0004\"!S)\u000f\u0005){eBA&O\u001b\u0005a%BA'8\u0003\u0019a$o\\8u}%\tA(\u0003\u0002Qw\u00059\u0001/Y2lC\u001e,\u0017B\u0001*T\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t\u00016(A\u0001g+\u00051\u0006\u0003\u0002\u001eXg\tK!\u0001W\u001e\u0003\u0013\u0019+hn\u0019;j_:\f\u0014A\u00014!\u0003\u0019a\u0014N\\5u}Q\u0011AL\u0018\t\u0005;\u0002\u0019$)D\u0001 \u0011\u0015!6\u00011\u0001W\u0003\tIg.F\u0001b!\r\u0001$mM\u0005\u0003G\u000e\u0012Q!\u00138mKR\f1!\u001b8!\u0003\ryW\u000f^\u000b\u0002OB\u0019\u0001\u0007\u001b\"\n\u0005%\u001c#AB(vi2,G/\u0001\u0003pkR\u0004\u0013!B:iCB,W#A\u0018\u0002\rMD\u0017\r]3!\u0003EIg.\u001b;jC2\fE\u000f\u001e:jEV$Xm]\u000b\u0002aB\u0011\u0001']\u0005\u0003e\u000e\u0012!\"\u0011;ue&\u0014W\u000f^3t\u0003-\u0019'/Z1uK2{w-[2\u0015\u0005UD\bC\u0001\u0016w\u0013\t98FA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011\u0015I8\u00021\u0001q\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t\u0003\u0011\u0019w\u000e]=\u0016\tq|\u00181\u0001\u000b\u0004{\u0006\u0015\u0001#B/\u0001}\u0006\u0005\u0001C\u0001\u001b\u0000\t\u00151DB1\u00019!\r!\u00141\u0001\u0003\u0006\t2\u0011\r\u0001\u000f\u0005\t)2\u0001\n\u00111\u0001\u0002\bA)!h\u0016@\u0002\u0002\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nTCBA\u0007\u0003G\t)#\u0006\u0002\u0002\u0010)\u001aa+!\u0005,\u0005\u0005M\u0001\u0003BA\u000b\u0003?i!!a\u0006\u000b\t\u0005e\u00111D\u0001\nk:\u001c\u0007.Z2lK\u0012T1!!\b<\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003C\t9BA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$QAN\u0007C\u0002a\"Q\u0001R\u0007C\u0002a\nQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAA\u0016!\u0011\ti#a\u000e\u000e\u0005\u0005=\"\u0002BA\u0019\u0003g\tA\u0001\\1oO*\u0011\u0011QG\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002:\u0005=\"AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0006\u0002\u0002@A\u0019!(!\u0011\n\u0007\u0005\r3HA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000fF\u0002@\u0003\u0013B\u0011\"a\u0013\u0011\u0003\u0003\u0005\r!a\u0010\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\t\t\u0006E\u0003\u0002T\u0005es(\u0004\u0002\u0002V)\u0019\u0011qK\u001e\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002\\\u0005U#\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!!\u0019\u0002hA\u0019!(a\u0019\n\u0007\u0005\u00154HA\u0004C_>dW-\u00198\t\u0011\u0005-##!AA\u0002}\n!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u00111FA7\u0011%\tYeEA\u0001\u0002\u0004\ty$\u0001\u0005iCND7i\u001c3f)\t\ty$\u0001\u0005u_N#(/\u001b8h)\t\tY#\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003C\nY\b\u0003\u0005\u0002LY\t\t\u00111\u0001@Q\r\u0001\u0011q\u0010\t\u0005\u0003\u0003\u000b))\u0004\u0002\u0002\u0004*\u0019\u0011QD\u0013\n\t\u0005\u001d\u00151\u0011\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018.A\u0002NCB\u0004\"!\u0018\r\u0014\u000ba\ty)!&\u0011\u0007i\n\t*C\u0002\u0002\u0014n\u0012a!\u00118z%\u00164\u0007\u0003BAL\u0003;k!!!'\u000b\t\u0005m\u00151G\u0001\u0003S>L1AUAM)\t\tY)A\u0003baBd\u00170\u0006\u0004\u0002&\u0006-\u0016q\u0016\u000b\u0005\u0003O\u000b\t\f\u0005\u0004^\u0001\u0005%\u0016Q\u0016\t\u0004i\u0005-F!\u0002\u001c\u001c\u0005\u0004A\u0004c\u0001\u001b\u00020\u0012)Ai\u0007b\u0001q!1Ak\u0007a\u0001\u0003g\u0003bAO,\u0002*\u00065\u0016aB;oCB\u0004H._\u000b\u0007\u0003s\u000b)-!3\u0015\t\u0005m\u00161\u001a\t\u0006u\u0005u\u0016\u0011Y\u0005\u0004\u0003\u007f[$AB(qi&|g\u000e\u0005\u0004;/\u0006\r\u0017q\u0019\t\u0004i\u0005\u0015G!\u0002\u001c\u001d\u0005\u0004A\u0004c\u0001\u001b\u0002J\u0012)A\t\bb\u0001q!I\u0011Q\u001a\u000f\u0002\u0002\u0003\u0007\u0011qZ\u0001\u0004q\u0012\u0002\u0004CB/\u0001\u0003\u0007\f9-\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002VB!\u0011QFAl\u0013\u0011\tI.a\f\u0003\r=\u0013'.Z2u\u0001")
@InternalApi
public final class Map<In, Out>
extends GraphStage<FlowShape<In, Out>>
implements Product,
Serializable {
    private final Function1<In, Out> f;
    private final Inlet<In> in;
    private final Outlet<Out> out;
    private final FlowShape<In, Out> shape;

    public static <In, Out> Option<Function1<In, Out>> unapply(Map<In, Out> x$0) {
        return Map$.MODULE$.unapply(x$0);
    }

    public static <In, Out> Map<In, Out> apply(Function1<In, Out> f) {
        return Map$.MODULE$.apply(f);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Function1<In, Out> f() {
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

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.map().and(Attributes$SourceLocation$.MODULE$.forLambda(this.f()));
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this, inheritedAttributes){
            private final /* synthetic */ Map $outer;
            private final Attributes inheritedAttributes$1;

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

            private Function1<Throwable, Supervision.Directive> decider() {
                return ((ActorAttributes.SupervisionStrategy)this.inheritedAttributes$1.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
            }

            public void onPush() {
                try {
                    this.push(this.$outer.out(), this.$outer.f().apply(this.grab(this.$outer.in())));
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
                this.inheritedAttributes$1 = inheritedAttributes$1;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.setHandlers($outer.in(), $outer.out(), this);
            }
        };
    }

    public <In, Out> Map<In, Out> copy(Function1<In, Out> f) {
        return new Map<In, Out>(f);
    }

    public <In, Out> Function1<In, Out> copy$default$1() {
        return this.f();
    }

    @Override
    public String productPrefix() {
        return "Map";
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
        return x$1 instanceof Map;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "f";
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
        if (!(object instanceof Map)) return false;
        boolean bl = true;
        if (!bl) return false;
        Map map = (Map)x$1;
        Function1<In, Out> function1 = this.f();
        Function1<In, Out> function12 = map.f();
        if (function1 != null) {
            if (!function1.equals(function12)) return false;
            return true;
        }
        if (function12 == null) return true;
        return false;
    }

    public Map(Function1<In, Out> f) {
        this.f = f;
        Product.$init$(this);
        this.in = Inlet$.MODULE$.apply("Map.in");
        this.out = Outlet$.MODULE$.apply("Map.out");
        this.shape = new FlowShape<In, Out>(this.in(), this.out());
    }
}

