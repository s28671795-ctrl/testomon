/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.impl.fusing.TakeWithin$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005I<aa\u0003\u0007\t\u0002I!bA\u0002\f\r\u0011\u0003\u0011r\u0003C\u0003\u001f\u0003\u0011\u0005\u0001\u0005C\u0004\"\u0003\t\u0007I\u0011\u0001\u0012\t\r-\n\u0001\u0015!\u0003$\r\u00151BB\u0001\n5\u0011!\u0019VA!b\u0001\n\u0003!\u0006\u0002C/\u0006\u0005\u0003\u0005\u000b\u0011B+\t\u000by)A\u0011\u00010\t\u000b\u0005,A\u0011\t2\t\u000b=,A\u0011\t9\u0002\u0015Q\u000b7.Z,ji\"LgN\u0003\u0002\u000e\u001d\u00051a-^:j]\u001eT!a\u0004\t\u0002\t%l\u0007\u000f\u001c\u0006\u0003#I\taa\u001d;sK\u0006l'\"A\n\u0002\t\u0005\\7.\u0019\t\u0003+\u0005i\u0011\u0001\u0004\u0002\u000b)\u0006\\WmV5uQ&t7CA\u0001\u0019!\tIB$D\u0001\u001b\u0015\u0005Y\u0012!B:dC2\f\u0017BA\u000f\u001b\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\u001a\u0001\u0001F\u0001\u0015\u0003=!\u0018m[3XSRD\u0017N\u001c+j[\u0016\u0014X#A\u0012\u0011\u0005\u0011JS\"A\u0013\u000b\u0005\u0019:\u0013\u0001\u00027b]\u001eT\u0011\u0001K\u0001\u0005U\u00064\u0018-\u0003\u0002+K\t11\u000b\u001e:j]\u001e\f\u0001\u0003^1lK^KG\u000f[5o)&lWM\u001d\u0011)\u0005\u0005i\u0003C\u0001\u00182\u001b\u0005y#B\u0001\u0019\u0013\u0003)\tgN\\8uCRLwN\\\u0005\u0003e=\u00121\"\u00138uKJt\u0017\r\\!qS\"\u0012\u0001!L\u000b\u0003k)\u001b\"!\u0002\u001c\u0011\u0007]*\u0005J\u0004\u00029\u0007:\u0011\u0011H\u0011\b\u0003u\u0005s!a\u000f!\u000f\u0005qzT\"A\u001f\u000b\u0005yz\u0012A\u0002\u001fs_>$h(C\u0001\u0014\u0013\t\t\"#\u0003\u0002\u0010!%\u0011QBD\u0005\u0003\t2\t1b\u0012:ba\"\u001cF/Y4fg&\u0011ai\u0012\u0002\u0017'&l\u0007\u000f\\3MS:,\u0017M]$sCBD7\u000b^1hK*\u0011A\t\u0004\t\u0003\u0013*c\u0001\u0001B\u0003L\u000b\t\u0007AJA\u0001U#\ti\u0005\u000b\u0005\u0002\u001a\u001d&\u0011qJ\u0007\u0002\b\u001d>$\b.\u001b8h!\tI\u0012+\u0003\u0002S5\t\u0019\u0011I\\=\u0002\u000fQLW.Z8viV\tQ\u000b\u0005\u0002W76\tqK\u0003\u0002Y3\u0006AA-\u001e:bi&|gN\u0003\u0002[5\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u0005q;&A\u0004$j]&$X\rR;sCRLwN\\\u0001\ti&lWm\\;uAQ\u0011q\f\u0019\t\u0004+\u0015A\u0005\"B*\t\u0001\u0004)\u0016aC2sK\u0006$X\rT8hS\u000e$\"aY5\u0011\u0005\u0011<W\"A3\u000b\u0005\u0019\u0004\u0012!B:uC\u001e,\u0017B\u00015f\u0005=9%/\u00199i'R\fw-\u001a'pO&\u001c\u0007\"\u00026\n\u0001\u0004Y\u0017aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\bC\u00017n\u001b\u0005\u0001\u0012B\u00018\u0011\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\ti>\u001cFO]5oOR\t1\u0005\u000b\u0002\u0006[\u0001")
@InternalApi
public final class TakeWithin<T>
extends GraphStages.SimpleLinearGraphStage<T> {
    private final FiniteDuration timeout;

    public static String takeWithinTimer() {
        return TakeWithin$.MODULE$.takeWithinTimer();
    }

    public FiniteDuration timeout() {
        return this.timeout;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this){
            private final /* synthetic */ TakeWithin $outer;

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

            public void onPush() {
                this.push(this.$outer.out(), this.grab(this.$outer.in()));
            }

            public void onPull() {
                this.pull(this.$outer.in());
            }

            public final void onTimer(Object key) {
                this.completeStage();
            }

            public void preStart() {
                this.scheduleOnce((Object)TakeWithin$.MODULE$.takeWithinTimer(), this.$outer.timeout());
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.setHandlers($outer.in(), $outer.out(), this);
            }
        };
    }

    public String toString() {
        return "TakeWithin";
    }

    public TakeWithin(FiniteDuration timeout2) {
        this.timeout = timeout2;
    }
}

