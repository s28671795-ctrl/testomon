/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.impl.JsonObjectParser;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Flow$;
import akka.stream.scaladsl.JsonFraming;
import akka.stream.scaladsl.JsonFraming$;
import akka.stream.scaladsl.JsonFraming$PartialObjectException$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.util.ByteString;
import scala.Option;
import scala.Some;
import scala.util.control.NonFatal$;

public final class JsonFraming$ {
    public static final JsonFraming$ MODULE$ = new JsonFraming$();

    public Flow<ByteString, ByteString, NotUsed> objectScanner(int maximumObjectLength) {
        return Flow$.MODULE$.apply().via(new GraphStages.SimpleLinearGraphStage<ByteString>(maximumObjectLength){
            public final int maximumObjectLength$1;

            public Attributes initialAttributes() {
                return Attributes$.MODULE$.name("JsonFraming.objectScanner");
            }

            public GraphStageLogic createLogic(Attributes inheritedAttributes) {
                return new InHandler(this){
                    private final JsonObjectParser buffer;
                    private final /* synthetic */ anon.1 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    private JsonObjectParser buffer() {
                        return this.buffer;
                    }

                    public void onPush() {
                        this.buffer().offer((ByteString)this.grab(this.$outer.in()));
                        this.tryPopBuffer();
                    }

                    public void onPull() {
                        this.tryPopBuffer();
                    }

                    public void onUpstreamFinish() {
                        Option<ByteString> option = this.buffer().poll();
                        if (option instanceof Some) {
                            Some some = (Some)option;
                            ByteString json = (ByteString)some.value();
                            this.emit(this.$outer.out(), json);
                            return;
                        }
                        this.complete();
                    }

                    private void tryPopBuffer() {
                        try {
                            Option<ByteString> option = this.buffer().poll();
                            if (option instanceof Some) {
                                Some some = (Some)option;
                                ByteString json = (ByteString)some.value();
                                this.push(this.$outer.out(), json);
                            } else if (this.isClosed(this.$outer.in())) {
                                this.complete();
                            } else {
                                this.pull(this.$outer.in());
                            }
                        }
                        catch (Throwable throwable) {
                            Option<Throwable> option;
                            Throwable throwable2 = throwable;
                            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                                Throwable ex = option.get();
                                this.failStage(ex);
                            }
                            throw throwable;
                        }
                    }

                    private void complete() {
                        if (this.buffer().canComplete()) {
                            this.completeStage();
                            return;
                        }
                        this.failStage(new JsonFraming.PartialObjectException(JsonFraming$PartialObjectException$.MODULE$.$lessinit$greater$default$1()));
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        super($outer.shape());
                        InHandler.$init$(this);
                        OutHandler.$init$(this);
                        this.buffer = new JsonObjectParser($outer.maximumObjectLength$1);
                        this.setHandlers($outer.in(), $outer.out(), this);
                    }
                };
            }
            {
                this.maximumObjectLength$1 = maximumObjectLength$1;
            }
        });
    }

    private JsonFraming$() {
    }
}

