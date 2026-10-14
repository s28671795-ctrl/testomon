/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io.compression;

import akka.NotUsed;
import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.impl.io.compression.CompressionUtils$;
import akka.stream.impl.io.compression.Compressor;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Flow$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.util.ByteString;
import scala.Function0;

@InternalApi
public final class CompressionUtils$ {
    public static final CompressionUtils$ MODULE$ = new CompressionUtils$();

    public Flow<ByteString, ByteString, NotUsed> compressorFlow(Function0<Compressor> newCompressor) {
        return Flow$.MODULE$.fromGraph(new GraphStages.SimpleLinearGraphStage<ByteString>(newCompressor){
            public final Function0 newCompressor$1;

            public GraphStageLogic createLogic(Attributes inheritedAttributes) {
                return new InHandler(this){
                    private final Compressor compressor;
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

                    private Compressor compressor() {
                        return this.compressor;
                    }

                    public void onPush() {
                        ByteString data = this.compressor().compressAndFlush((ByteString)this.grab(this.$outer.in()));
                        if (data.nonEmpty()) {
                            this.push(this.$outer.out(), data);
                            return;
                        }
                        this.pull(this.$outer.in());
                    }

                    public void onPull() {
                        this.pull(this.$outer.in());
                    }

                    public void onUpstreamFinish() {
                        ByteString data = this.compressor().finish();
                        if (data.nonEmpty()) {
                            this.emit(this.$outer.out(), data);
                        }
                        this.completeStage();
                    }

                    public void postStop() {
                        this.compressor().close();
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        super($outer.shape());
                        InHandler.$init$(this);
                        OutHandler.$init$(this);
                        this.compressor = (Compressor)$outer.newCompressor$1.apply();
                        this.setHandlers($outer.in(), $outer.out(), this);
                    }
                };
            }
            {
                this.newCompressor$1 = newCompressor$1;
            }
        });
    }

    private CompressionUtils$() {
    }
}

