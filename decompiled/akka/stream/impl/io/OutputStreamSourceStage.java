/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.stream.Attributes;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.SourceShape$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.io.OutputStreamAdapter;
import akka.stream.impl.io.OutputStreamSourceStage;
import akka.stream.impl.io.OutputStreamSourceStage$Close$;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageLogic$EagerTerminateOutput$;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.util.ByteString;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.concurrent.Semaphore;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.Predef$;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005]xA\u0002\u00180\u0011\u0003\u0019tG\u0002\u0004:_!\u00051G\u000f\u0005\u0006\u0003\u0006!\ta\u0011\u0004\b\t\u0006\u0001\n1%\tF\r\u0019\t\u0019\"\u0001!\u0002\u0016!Q\u0011q\u0003\u0003\u0003\u0016\u0004%\t!!\u0007\t\u0015\u0005\u001dBA!E!\u0002\u0013\tY\u0002\u0003\u0004B\t\u0011\u0005\u0011\u0011\u0006\u0005\n\u0003_!\u0011\u0011!C\u0001\u0003cA\u0011\"!\u000e\u0005#\u0003%\t!a\u000e\t\u000fq#\u0011\u0011!C!;\"9a\rBA\u0001\n\u00039\u0007\u0002C6\u0005\u0003\u0003%\t!!\u0014\t\u000fI$\u0011\u0011!C!g\"A!\u0010BA\u0001\n\u0003\t\t\u0006C\u0005\u0002V\u0011\t\t\u0011\"\u0011\u0002X!I\u0011\u0011\u0001\u0003\u0002\u0002\u0013\u0005\u00131\u0001\u0005\n\u0003\u000b!\u0011\u0011!C!\u0003\u000fA\u0011\"a\u0017\u0005\u0003\u0003%\t%!\u0018\b\u0013\u0005\u0005\u0014!!A\t\u0002\u0005\rd!CA\n\u0003\u0005\u0005\t\u0012AA3\u0011\u0019\tE\u0003\"\u0001\u0002|!I\u0011Q\u0001\u000b\u0002\u0002\u0013\u0015\u0013q\u0001\u0005\n\u0003{\"\u0012\u0011!CA\u0003\u007fB\u0011\"a!\u0015\u0003\u0003%\t)!\"\t\u0013\u0005%A#!A\u0005\n\u0005-qABAI\u0003!\u00055LB\u0003H\u0003!\u0005\u0005\nC\u0003B7\u0011\u0005!\fC\u0004]7\u0005\u0005I\u0011I/\t\u000f\u0019\\\u0012\u0011!C\u0001O\"91nGA\u0001\n\u0003a\u0007b\u0002:\u001c\u0003\u0003%\te\u001d\u0005\bun\t\t\u0011\"\u0001|\u0011%\t\taGA\u0001\n\u0003\n\u0019\u0001C\u0005\u0002\u0006m\t\t\u0011\"\u0011\u0002\b!I\u0011\u0011B\u000e\u0002\u0002\u0013%\u00111\u0002\u0004\u0007s=\u00121'a%\t\u0015\u0005=VE!A!\u0002\u0013\t\t\f\u0003\u0004BK\u0011\u0005\u0011\u0011\u0019\u0005\n\u0003\u000f,#\u0019!C\u0001\u0003\u0013D\u0001\"!5&A\u0003%\u00111\u001a\u0005\b\u0003',C\u0011IAk\u0011%\ti.\nb\u0001\n\u0003\ny\u000e\u0003\u0005\u0002b\u0016\u0002\u000b\u0011BAQ\u0011\u001d\t\u0019/\nC!\u0003K\fqcT;uaV$8\u000b\u001e:fC6\u001cv.\u001e:dKN#\u0018mZ3\u000b\u0005A\n\u0014AA5p\u0015\t\u00114'\u0001\u0003j[Bd'B\u0001\u001b6\u0003\u0019\u0019HO]3b[*\ta'\u0001\u0003bW.\f\u0007C\u0001\u001d\u0002\u001b\u0005y#aF(viB,Ho\u0015;sK\u0006l7k\\;sG\u0016\u001cF/Y4f'\t\t1\b\u0005\u0002=\u007f5\tQHC\u0001?\u0003\u0015\u00198-\u00197b\u0013\t\u0001UH\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\tqGA\u000bBI\u0006\u0004H/\u001a:U_N#\u0018mZ3NKN\u001c\u0018mZ3\u0014\u0005\rY\u0014fA\u0002\u001c\t\t)1\t\\8tKN)1dO%L\u001dB\u0011!jA\u0007\u0002\u0003A\u0011A\bT\u0005\u0003\u001bv\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002P/:\u0011\u0001+\u0016\b\u0003#Rk\u0011A\u0015\u0006\u0003'\n\u000ba\u0001\u0010:p_Rt\u0014\"\u0001 \n\u0005Yk\u0014a\u00029bG.\fw-Z\u0005\u00031f\u0013AbU3sS\u0006d\u0017N_1cY\u0016T!AV\u001f\u0015\u0003m\u0003\"AS\u000e\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005q\u0006CA0e\u001b\u0005\u0001'BA1c\u0003\u0011a\u0017M\\4\u000b\u0003\r\fAA[1wC&\u0011Q\r\u0019\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003!\u0004\"\u0001P5\n\u0005)l$aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HCA7q!\tad.\u0003\u0002p{\t\u0019\u0011I\\=\t\u000fE|\u0012\u0011!a\u0001Q\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\u0012\u0001\u001e\t\u0004kblW\"\u0001<\u000b\u0005]l\u0014AC2pY2,7\r^5p]&\u0011\u0011P\u001e\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0002}\u007fB\u0011A(`\u0005\u0003}v\u0012qAQ8pY\u0016\fg\u000eC\u0004rC\u0005\u0005\t\u0019A7\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012\u0001[\u0001\ti>\u001cFO]5oOR\ta,\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002\u000eA\u0019q,a\u0004\n\u0007\u0005E\u0001M\u0001\u0004PE*,7\r\u001e\u0002\u0005'\u0016tGmE\u0003\u0005w%[e*\u0001\u0003eCR\fWCAA\u000e!\u0011\ti\"a\t\u000e\u0005\u0005}!bAA\u0011k\u0005!Q\u000f^5m\u0013\u0011\t)#a\b\u0003\u0015\tKH/Z*ue&tw-A\u0003eCR\f\u0007\u0005\u0006\u0003\u0002,\u00055\u0002C\u0001&\u0005\u0011\u001d\t9b\u0002a\u0001\u00037\tAaY8qsR!\u00111FA\u001a\u0011%\t9\u0002\u0003I\u0001\u0002\u0004\tY\"\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\u0005e\"\u0006BA\u000e\u0003wY#!!\u0010\u0011\t\u0005}\u0012\u0011J\u0007\u0003\u0003\u0003RA!a\u0011\u0002F\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003\u000fj\u0014AC1o]>$\u0018\r^5p]&!\u00111JA!\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u000b\u0004[\u0006=\u0003bB9\r\u0003\u0003\u0005\r\u0001\u001b\u000b\u0004y\u0006M\u0003bB9\u000f\u0003\u0003\u0005\r!\\\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\rF\u0002_\u00033Bq!]\b\u0002\u0002\u0003\u0007\u0001.\u0001\u0004fcV\fGn\u001d\u000b\u0004y\u0006}\u0003bB9\u0013\u0003\u0003\u0005\r!\\\u0001\u0005'\u0016tG\r\u0005\u0002K)M)A#a\u001a\u0002tAA\u0011\u0011NA8\u00037\tY#\u0004\u0002\u0002l)\u0019\u0011QN\u001f\u0002\u000fI,h\u000e^5nK&!\u0011\u0011OA6\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\t\u0005\u0003k\nI(\u0004\u0002\u0002x)\u0011\u0001GY\u0005\u00041\u0006]DCAA2\u0003\u0015\t\u0007\u000f\u001d7z)\u0011\tY#!!\t\u000f\u0005]q\u00031\u0001\u0002\u001c\u00059QO\\1qa2LH\u0003BAD\u0003\u001b\u0003R\u0001PAE\u00037I1!a#>\u0005\u0019y\u0005\u000f^5p]\"I\u0011q\u0012\r\u0002\u0002\u0003\u0007\u00111F\u0001\u0004q\u0012\u0002\u0014!B\"m_N,7cA\u0013\u0002\u0016BA\u0011qSAO\u0003C\u000bI+\u0004\u0002\u0002\u001a*\u0019\u00111T\u001a\u0002\u000bM$\u0018mZ3\n\t\u0005}\u0015\u0011\u0014\u0002 \u000fJ\f\u0007\u000f[*uC\u001e,w+\u001b;i\u001b\u0006$XM]5bY&TX\r\u001a,bYV,\u0007CBAR\u0003K\u000bY\"D\u00014\u0013\r\t9k\r\u0002\f'>,(oY3TQ\u0006\u0004X\r\u0005\u0003\u0002v\u0005-\u0016\u0002BAW\u0003o\u0012AbT;uaV$8\u000b\u001e:fC6\fAb\u001e:ji\u0016$\u0016.\\3pkR\u0004B!a-\u0002>6\u0011\u0011Q\u0017\u0006\u0005\u0003o\u000bI,\u0001\u0005ekJ\fG/[8o\u0015\r\tY,P\u0001\u000bG>t7-\u001e:sK:$\u0018\u0002BA`\u0003k\u0013aBR5oSR,G)\u001e:bi&|g\u000e\u0006\u0003\u0002D\u0006\u0015\u0007C\u0001\u001d&\u0011\u001d\tyk\na\u0001\u0003c\u000b1a\\;u+\t\tY\r\u0005\u0004\u0002$\u00065\u00171D\u0005\u0004\u0003\u001f\u001c$AB(vi2,G/\u0001\u0003pkR\u0004\u0013!E5oSRL\u0017\r\\!uiJL'-\u001e;fgV\u0011\u0011q\u001b\t\u0005\u0003G\u000bI.C\u0002\u0002\\N\u0012!\"\u0011;ue&\u0014W\u000f^3t\u0003\u0015\u0019\b.\u00199f+\t\t\t+\u0001\u0004tQ\u0006\u0004X\rI\u0001 GJ,\u0017\r^3M_\u001eL7-\u00118e\u001b\u0006$XM]5bY&TX\r\u001a,bYV,G\u0003BAt\u0003g\u0004r\u0001PAu\u0003[\fI+C\u0002\u0002lv\u0012a\u0001V;qY\u0016\u0014\u0004\u0003BAL\u0003_LA!!=\u0002\u001a\nyqI]1qQN#\u0018mZ3M_\u001eL7\rC\u0004\u0002v6\u0002\r!a6\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:")
public final class OutputStreamSourceStage
extends GraphStageWithMaterializedValue<SourceShape<ByteString>, OutputStream> {
    private final FiniteDuration writeTimeout;
    private final Outlet<ByteString> out;
    private final SourceShape<ByteString> shape;

    public Outlet<ByteString> out() {
        return this.out;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.outputStreamSource();
    }

    @Override
    public SourceShape<ByteString> shape() {
        return this.shape;
    }

    @Override
    public Tuple2<GraphStageLogic, OutputStream> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        int maxBuffer = inheritedAttributes.get(new Attributes.InputBuffer(16, 16), ClassTag$.MODULE$.apply(Attributes.InputBuffer.class)).max();
        Predef$.MODULE$.require(maxBuffer > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "Buffer size must be greater than 0");
        Semaphore semaphore = new Semaphore(maxBuffer, true);
        public final class Akka_stream_impl_io_OutputStreamSourceStage$OutputStreamSourceLogic$1
        extends GraphStageLogic {
            private final AsyncCallback<AdapterToStageMessage> upstreamCallback;
            private final /* synthetic */ OutputStreamSourceStage $outer;
            private final Semaphore semaphore$1;

            public AsyncCallback<AdapterToStageMessage> upstreamCallback() {
                return this.upstreamCallback;
            }

            private void onAsyncMessage(AdapterToStageMessage event) {
                AdapterToStageMessage adapterToStageMessage = event;
                if (adapterToStageMessage instanceof Send) {
                    Send send = (Send)adapterToStageMessage;
                    ByteString data = send.data();
                    this.emit(this.$outer.out(), data, () -> $this.semaphore$1.release());
                    return;
                }
                if (OutputStreamSourceStage$Close$.MODULE$.equals(adapterToStageMessage)) {
                    this.completeStage();
                    return;
                }
                throw new MatchError(adapterToStageMessage);
            }

            public static final /* synthetic */ void $anonfun$upstreamCallback$1(Akka_stream_impl_io_OutputStreamSourceStage$OutputStreamSourceLogic$1 $this, AdapterToStageMessage event) {
                $this.onAsyncMessage(event);
            }

            public Akka_stream_impl_io_OutputStreamSourceStage$OutputStreamSourceLogic$1(OutputStreamSourceStage $outer, Semaphore semaphore$1) {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.semaphore$1 = semaphore$1;
                super($outer.shape());
                this.upstreamCallback = this.getAsyncCallback((Function1<AdapterToStageMessage, Object> & Serializable)event -> {
                    Akka_stream_impl_io_OutputStreamSourceStage$OutputStreamSourceLogic$1.$anonfun$upstreamCallback$1(this, event);
                    return BoxedUnit.UNIT;
                });
                this.setHandler($outer.out(), GraphStageLogic$EagerTerminateOutput$.MODULE$);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$onAsyncMessage$1(akka.stream.impl.io.OutputStreamSourceStage$OutputStreamSourceLogic$1 ), $anonfun$upstreamCallback$1$adapted(akka.stream.impl.io.OutputStreamSourceStage$OutputStreamSourceLogic$1 akka.stream.impl.io.OutputStreamSourceStage$AdapterToStageMessage )}, serializedLambda);
            }
        }
        Akka_stream_impl_io_OutputStreamSourceStage$OutputStreamSourceLogic$1 logic = new Akka_stream_impl_io_OutputStreamSourceStage$OutputStreamSourceLogic$1(this, semaphore);
        return new Tuple2<GraphStageLogic, OutputStream>(logic, new OutputStreamAdapter(semaphore, logic.upstreamCallback(), this.writeTimeout));
    }

    public OutputStreamSourceStage(FiniteDuration writeTimeout) {
        this.writeTimeout = writeTimeout;
        this.out = Outlet$.MODULE$.apply("OutputStreamSource.out");
        this.shape = SourceShape$.MODULE$.of(this.out());
    }

    public static interface AdapterToStageMessage {
    }

    public static class Send
    implements AdapterToStageMessage,
    Product,
    Serializable {
        private final ByteString data;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ByteString data() {
            return this.data;
        }

        public Send copy(ByteString data) {
            return new Send(data);
        }

        public ByteString copy$default$1() {
            return this.data();
        }

        @Override
        public String productPrefix() {
            return "Send";
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
                    return this.data();
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
            return x$1 instanceof Send;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "data";
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
            if (!(object instanceof Send)) return false;
            boolean bl = true;
            if (!bl) return false;
            Send send = (Send)x$1;
            ByteString byteString = this.data();
            ByteString byteString2 = send.data();
            if (byteString == null) {
                if (byteString2 != null) {
                    return false;
                }
            } else if (!((Object)byteString).equals(byteString2)) return false;
            if (!send.canEqual(this)) return false;
            return true;
        }

        public Send(ByteString data) {
            this.data = data;
            Product.$init$(this);
        }
    }
}

