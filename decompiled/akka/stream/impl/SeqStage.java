/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.AbruptStageTerminationException;
import akka.stream.Attributes;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.SinkShape;
import akka.stream.SinkShape$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.InHandler;
import scala.Tuple2;
import scala.collection.Factory;
import scala.collection.mutable.Builder;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005}b!\u0002\u0006\f\u0005=\t\u0002\u0002C\u001b\u0001\u0005\u0003\u0005\u000b1\u0002\u001c\t\u000be\u0003A\u0011\u0001.\t\u000f\u0019\u0004!\u0019!C\u0001O\"11\u000e\u0001Q\u0001\n!DQ\u0001\u001c\u0001\u0005B5DqA\u001e\u0001C\u0002\u0013\u0005s\u000f\u0003\u0004y\u0001\u0001\u0006I!\u0007\u0005\u0006s\u0002!\tF\u001f\u0005\u0006}\u0002!\te \u0002\t'\u0016\f8\u000b^1hK*\u0011A\"D\u0001\u0005S6\u0004HN\u0003\u0002\u000f\u001f\u000511\u000f\u001e:fC6T\u0011\u0001E\u0001\u0005C.\\\u0017-F\u0002\u0013?M\u001a\"\u0001A\n\u0011\tQ9\u0012\u0004L\u0007\u0002+)\u0011a#D\u0001\u0006gR\fw-Z\u0005\u00031U\u0011qd\u0012:ba\"\u001cF/Y4f/&$\b.T1uKJL\u0017\r\\5{K\u00124\u0016\r\\;f!\rQ2$H\u0007\u0002\u001b%\u0011A$\u0004\u0002\n'&t7n\u00155ba\u0016\u0004\"AH\u0010\r\u0001\u0011)\u0001\u0005\u0001b\u0001E\t\tAk\u0001\u0001\u0012\u0005\rJ\u0003C\u0001\u0013(\u001b\u0005)#\"\u0001\u0014\u0002\u000bM\u001c\u0017\r\\1\n\u0005!*#a\u0002(pi\"Lgn\u001a\t\u0003I)J!aK\u0013\u0003\u0007\u0005s\u0017\u0010E\u0002.aIj\u0011A\f\u0006\u0003_\u0015\n!bY8oGV\u0014(/\u001a8u\u0013\t\tdF\u0001\u0004GkR,(/\u001a\t\u0003=M\"Q\u0001\u000e\u0001C\u0002\t\u0012A\u0001\u00165bi\u0006\u00191M\u00194\u0011\t]:UD\u0013\b\u0003q\u0011s!!O!\u000f\u0005izdBA\u001e?\u001b\u0005a$BA\u001f\"\u0003\u0019a$o\\8u}%\t\u0001#\u0003\u0002A\u001f\u0005!Q\u000f^5m\u0013\t\u00115)A\u0004dG>l\u0007/\u0019;\u000b\u0005\u0001{\u0011BA#G\u0003\u001d\u0001\u0018mY6bO\u0016T!AQ\"\n\u0005!K%a\u0002$bGR|'/\u001f\u0006\u0003\u000b\u001a\u00132a\u0013\u001aN\r\u0011a\u0005\u0001\u0001&\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e 1\u00059;\u0006cA(U-6\t\u0001K\u0003\u0002R%\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003'\u0016\n!bY8mY\u0016\u001cG/[8o\u0013\t)\u0006K\u0001\u0005Ji\u0016\u0014\u0018M\u00197f!\tqr\u000bB\u0005Y\u0003\u0005\u0005\t\u0011!B\u0001E\t\u0019q\fJ\u0019\u0002\rqJg.\u001b;?)\u0005YFC\u0001/_!\u0011i\u0006!\b\u001a\u000e\u0003-AQ!\u000e\u0002A\u0004}\u0003BaN$\u001eAJ\u0019\u0011M\r2\u0007\t1\u0003\u0001\u0001\u0019\u0019\u0003G\u0016\u00042a\u0014+e!\tqR\rB\u0005Y=\u0006\u0005\t\u0011!B\u0001E\u0005\u0011\u0011N\\\u000b\u0002QB\u0019!$[\u000f\n\u0005)l!!B%oY\u0016$\u0018aA5oA\u0005AAo\\*ue&tw\rF\u0001o!\ty7O\u0004\u0002qcB\u00111(J\u0005\u0003e\u0016\na\u0001\u0015:fI\u00164\u0017B\u0001;v\u0005\u0019\u0019FO]5oO*\u0011!/J\u0001\u0006g\"\f\u0007/Z\u000b\u00023\u000511\u000f[1qK\u0002\n\u0011#\u001b8ji&\fG.\u0011;ue&\u0014W\u000f^3t+\u0005Y\bC\u0001\u000e}\u0013\tiXB\u0001\u0006BiR\u0014\u0018NY;uKN\fqd\u0019:fCR,Gj\\4jG\u0006sG-T1uKJL\u0017\r\\5{K\u00124\u0016\r\\;f)\u0011\t\t!!\f\u0011\r\u0011\n\u0019!a\u0002-\u0013\r\t)!\n\u0002\u0007)V\u0004H.\u001a\u001a\u0013\r\u0005%\u0011qBA\u000b\r\u0019a\u00151\u0002\u0001\u0002\b!1\u0011QB\u0005\u0001\u0003\u000f\tQ\u0001\\8hS\u000e\u00042\u0001FA\t\u0013\r\t\u0019\"\u0006\u0002\u0010\u000fJ\f\u0007\u000f[*uC\u001e,Gj\\4jGB\u0019A#a\u0006\n\u0007\u0005eQCA\u0005J]\"\u000bg\u000e\u001a7fe\"Q\u0011QDA\u0005\u0005\u0004%\t!a\b\u0002\u0007\t,h-\u0006\u0002\u0002\"A1\u00111EA\u0015;)k!!!\n\u000b\u0007\u0005\u001d\"+A\u0004nkR\f'\r\\3\n\t\u0005-\u0012Q\u0005\u0002\b\u0005VLG\u000eZ3s\u0011\u0019\ty#\u0003a\u0001w\u0006\u0019\u0012N\u001c5fe&$X\rZ!uiJL'-\u001e;fg\"\u001a\u0001!a\r\u0011\t\u0005U\u00121H\u0007\u0003\u0003oQ1!!\u000f\u0010\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003{\t9DA\u0006J]R,'O\\1m\u0003BL\u0007")
@InternalApi
public final class SeqStage<T, That>
extends GraphStageWithMaterializedValue<SinkShape<T>, Future<That>> {
    public final Factory<T, That> akka$stream$impl$SeqStage$$cbf;
    private final Inlet<T> in;
    private final SinkShape<T> shape;

    public Inlet<T> in() {
        return this.in;
    }

    public String toString() {
        return "SeqStage";
    }

    @Override
    public SinkShape<T> shape() {
        return this.shape;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.seqSink();
    }

    @Override
    public Tuple2<GraphStageLogic, Future<That>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        Promise p = Promise$.MODULE$.apply();
        InHandler logic = new InHandler(this, p){
            private final Builder<T, That> buf;
            private final /* synthetic */ SeqStage $outer;
            private final Promise p$3;

            public Builder<T, That> buf() {
                return this.buf;
            }

            public void preStart() {
                this.pull(this.$outer.in());
            }

            public void onPush() {
                this.buf().$plus$eq(this.grab(this.$outer.in()));
                this.pull(this.$outer.in());
            }

            public void onUpstreamFinish() {
                That result = this.buf().result();
                this.p$3.trySuccess(result);
                this.completeStage();
            }

            public void onUpstreamFailure(Throwable ex) {
                this.p$3.tryFailure(ex);
                this.failStage(ex);
            }

            public void postStop() {
                if (!this.p$3.isCompleted()) {
                    this.p$3.failure(new AbruptStageTerminationException(this));
                    return;
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.p$3 = p$3;
                super($outer.shape());
                InHandler.$init$(this);
                this.buf = $outer.akka$stream$impl$SeqStage$$cbf.newBuilder();
                this.setHandler($outer.in(), this);
            }
        };
        return new Tuple2<GraphStageLogic, Future<That>>((GraphStageLogic)((Object)logic), p.future());
    }

    public SeqStage(Factory<T, That> cbf) {
        this.akka$stream$impl$SeqStage$$cbf = cbf;
        this.in = Inlet$.MODULE$.apply("seq.in");
        this.shape = SinkShape$.MODULE$.of(this.in());
    }
}

