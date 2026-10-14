/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00114QAC\u0006\u0003\u001fEA\u0001\u0002\f\u0001\u0003\u0002\u0003\u0006I!\f\u0005\u0006s\u0001!\tA\u000f\u0005\b}\u0001\u0011\r\u0011\"\u0001@\u0011\u0019\u0019\u0005\u0001)A\u0005\u0001\"9A\t\u0001b\u0001\n\u0003*\u0005B\u0002$\u0001A\u0003%\u0011\u0004C\u0003H\u0001\u0011E\u0003\nC\u0003M\u0001\u0011\u0005S\nC\u0003T\u0001\u0011\u0005CK\u0001\u0007GC&dW\rZ*pkJ\u001cWM\u0003\u0002\r\u001b\u0005!\u0011.\u001c9m\u0015\tqq\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002!\u0005!\u0011m[6b+\t\u0011rd\u0005\u0002\u0001'A\u0019AcF\r\u000e\u0003UQ!AF\u0007\u0002\u000bM$\u0018mZ3\n\u0005a)\"AC$sCBD7\u000b^1hKB\u0019!dG\u000f\u000e\u00035I!\u0001H\u0007\u0003\u0017M{WO]2f'\"\f\u0007/\u001a\t\u0003=}a\u0001\u0001B\u0003!\u0001\t\u0007!EA\u0001U\u0007\u0001\t\"aI\u0015\u0011\u0005\u0011:S\"A\u0013\u000b\u0003\u0019\nQa]2bY\u0006L!\u0001K\u0013\u0003\u000f9{G\u000f[5oOB\u0011AEK\u0005\u0003W\u0015\u00121!\u00118z\u0003\u001d1\u0017-\u001b7ve\u0016\u0004\"A\f\u001c\u000f\u0005=\"dB\u0001\u00194\u001b\u0005\t$B\u0001\u001a\"\u0003\u0019a$o\\8u}%\ta%\u0003\u00026K\u00059\u0001/Y2lC\u001e,\u0017BA\u001c9\u0005%!\u0006N]8xC\ndWM\u0003\u00026K\u00051A(\u001b8jiz\"\"aO\u001f\u0011\u0007q\u0002Q$D\u0001\f\u0011\u0015a#\u00011\u0001.\u0003\ryW\u000f^\u000b\u0002\u0001B\u0019!$Q\u000f\n\u0005\tk!AB(vi2,G/\u0001\u0003pkR\u0004\u0013!B:iCB,W#A\r\u0002\rMD\u0017\r]3!\u0003EIg.\u001b;jC2\fE\u000f\u001e:jEV$Xm]\u000b\u0002\u0013B\u0011!DS\u0005\u0003\u00176\u0011!\"\u0011;ue&\u0014W\u000f^3t\u0003-\u0019'/Z1uK2{w-[2\u0015\u00059\u000b\u0006C\u0001\u000bP\u0013\t\u0001VCA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011\u0015\u0011\u0006\u00021\u0001J\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t\u0003!!xn\u0015;sS:<G#A+\u0011\u0005Y[V\"A,\u000b\u0005aK\u0016\u0001\u00027b]\u001eT\u0011AW\u0001\u0005U\u00064\u0018-\u0003\u0002]/\n11\u000b\u001e:j]\u001eD#\u0001\u00010\u0011\u0005}\u0013W\"\u00011\u000b\u0005\u0005|\u0011AC1o]>$\u0018\r^5p]&\u00111\r\u0019\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e")
@InternalApi
public final class FailedSource<T>
extends GraphStage<SourceShape<T>> {
    public final Throwable akka$stream$impl$FailedSource$$failure;
    private final Outlet<T> out;
    private final SourceShape<T> shape;

    public Outlet<T> out() {
        return this.out;
    }

    @Override
    public SourceShape<T> shape() {
        return this.shape;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.failedSource();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this){
            private final /* synthetic */ FailedSource $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onPull() {
            }

            public void preStart() {
                this.failStage(this.$outer.akka$stream$impl$FailedSource$$failure);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                OutHandler.$init$(this);
                this.setHandler($outer.out(), this);
            }
        };
    }

    public String toString() {
        return new StringBuilder(14).append("FailedSource(").append(this.akka$stream$impl$FailedSource$$failure.getClass().getName()).append(")").toString();
    }

    public FailedSource(Throwable failure) {
        this.akka$stream$impl$FailedSource$$failure = failure;
        this.out = Outlet$.MODULE$.apply("FailedSource.out");
        this.shape = new SourceShape<T>(this.out());
    }
}

