/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.Attributes;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.scaladsl.BidiFlow;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Framing$;
import akka.stream.scaladsl.Framing$DelimiterFramingStage$;
import akka.stream.scaladsl.Framing$DelimiterFramingStage$$anon$2$FrameIterator$;
import akka.stream.scaladsl.Framing$DelimiterFramingStage$$anon$2$LightArray$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.util.ByteIterator;
import akka.util.ByteString;
import akka.util.ByteString$;
import akka.util.CompactByteString;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.nio.ByteOrder;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Some;
import scala.Tuple2;
import scala.math.Numeric$IntIsIntegral$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\t]u!B A\u0011\u00039e!B%A\u0011\u0003Q\u0005\"B)\u0002\t\u0003\u0011\u0006\"B*\u0002\t\u0003!\u0006bB7\u0002#\u0003%\tA\u001c\u0005\u0006s\u0006!\tA\u001f\u0005\n\u0003+\t\u0011\u0013!C\u0001\u0003/A\u0011\"a\u0007\u0002#\u0003%\t!!\b\t\re\fA\u0011AA\u0011\u0011\u001d\t\t%\u0001C\u0001\u0003\u0007Bq!a\u0014\u0002\t\u0003\t\t\u0006C\u0004\u0002V\u0005!\t!a\u0016\u0007\r\u0005m\u0013\u0001AA/\u0011)\t9\b\u0004B\u0001B\u0003%\u0011\u0011\u0010\u0005\u0007#2!\t!!#\t\u0013\u0005E\u0015A1A\u0005\u000e\u0005M\u0005\u0002CAO\u0003\u0001\u0006i!!&\t\u0013\u0005}\u0015A1A\u0005\u000e\u0005M\u0005\u0002CAQ\u0003\u0001\u0006i!!&\u0007\r\u0005\r\u0016\u0001BAS\u0011)\tie\u0005B\u0001B\u0003%\u0011Q\u001a\u0005\u0007#N!\t!a5\t\u000f\u0005e7\u0003\"\u0011\u0002\\\u001a1!qA\u0001\u0005\u0005\u0013A!Ba\u0006\u0018\u0005\u000b\u0007I\u0011\u0001B\r\u0011%\u0011Yb\u0006B\u0001B\u0003%\u0001\f\u0003\u0006\u0003\u001e]\u0011)\u0019!C\u0001\u0005?A\u0011B!\t\u0018\u0005\u0003\u0005\u000b\u0011B3\t\u0013%<\"Q1A\u0005\u0002\t\r\u0002\"\u0003B\u0013/\t\u0005\t\u0015!\u0003k\u0011\u0019\tv\u0003\"\u0001\u0003(!I!\u0011G\fC\u0002\u0013\u0005!1\u0007\u0005\t\u0005w9\u0002\u0015!\u0003\u00036!I!QH\fC\u0002\u0013\u0005!q\b\u0005\t\u0005\u000f:\u0002\u0015!\u0003\u0003B!I!\u0011J\fC\u0002\u0013\u0005#1\n\u0005\t\u0005\u001b:\u0002\u0015!\u0003\u0003\u0012!9!qJ\f\u0005B\tE\u0003b\u0002B*/\u0011\u0005#Q\u000b\u0005\b\u00033<B\u0011\tB,\r\u0019\u0011Y&\u0001\u0004\u0003^!Q!q\f\u0015\u0003\u0006\u0004%\tAa\b\t\u0013\t\u0005\u0004F!A!\u0002\u0013)\u0007B\u0003B2Q\t\u0015\r\u0011\"\u0001\u0003 !I!Q\r\u0015\u0003\u0002\u0003\u0006I!\u001a\u0005\nI\"\u0012)\u0019!C\u0001\u0005?A\u0011Ba\u001a)\u0005\u0003\u0005\u000b\u0011B3\t\u0015\u0005\r\u0001F!b\u0001\n\u0003\u0011I\u0007\u0003\u0006\u0003l!\u0012\t\u0011)A\u0005\u0003\u000bA!\"!\f)\u0005\u0003\u0005\u000b\u0011\u0002B7\u0011\u0019\t\u0006\u0006\"\u0001\u0003t!1\u0011\u000b\u000bC\u0001\u0005\u0003C\u0011Ba#)\u0005\u0004%IAa\b\t\u000f\t5\u0005\u0006)A\u0005K\"I!q\u0012\u0015C\u0002\u0013%\u00111\u0013\u0005\t\u0005#C\u0003\u0015!\u0003\u0002\u0016\"I!\u0011\u0007\u0015C\u0002\u0013\u0005!1\u0007\u0005\t\u0005wA\u0003\u0015!\u0003\u00036!I!Q\b\u0015C\u0002\u0013\u0005!q\b\u0005\t\u0005\u000fB\u0003\u0015!\u0003\u0003B!I!\u0011\n\u0015C\u0002\u0013\u0005#1\n\u0005\t\u0005\u001bB\u0003\u0015!\u0003\u0003\u0012!9\u0011\u0011\u001c\u0015\u0005B\tM\u0015a\u0002$sC6Lgn\u001a\u0006\u0003\u0003\n\u000b\u0001b]2bY\u0006$7\u000f\u001c\u0006\u0003\u0007\u0012\u000baa\u001d;sK\u0006l'\"A#\u0002\t\u0005\\7.Y\u0002\u0001!\tA\u0015!D\u0001A\u0005\u001d1%/Y7j]\u001e\u001c\"!A&\u0011\u00051{U\"A'\u000b\u00039\u000bQa]2bY\u0006L!\u0001U'\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\tq)A\u0005eK2LW.\u001b;feR!QKY2i!\u0015Ae\u000b\u0017-_\u0013\t9\u0006I\u0001\u0003GY><\bCA-]\u001b\u0005Q&BA.E\u0003\u0011)H/\u001b7\n\u0005uS&A\u0003\"zi\u0016\u001cFO]5oOB\u0011q\fY\u0007\u0002\t&\u0011\u0011\r\u0012\u0002\b\u001d>$Xk]3e\u0011\u0015\u00196\u00011\u0001Y\u0011\u0015!7\u00011\u0001f\u0003Ii\u0017\r_5nk64%/Y7f\u0019\u0016tw\r\u001e5\u0011\u000513\u0017BA4N\u0005\rIe\u000e\u001e\u0005\bS\u000e\u0001\n\u00111\u0001k\u0003=\tG\u000e\\8x)J,hnY1uS>t\u0007C\u0001'l\u0013\taWJA\u0004C_>dW-\u00198\u0002'\u0011,G.[7ji\u0016\u0014H\u0005Z3gCVdG\u000fJ\u001a\u0016\u0003=T#A\u001b9,\u0003E\u0004\"A]<\u000e\u0003MT!\u0001^;\u0002\u0013Ut7\r[3dW\u0016$'B\u0001<N\u0003)\tgN\\8uCRLwN\\\u0005\u0003qN\u0014\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u0003-aWM\\4uQ\u001aKW\r\u001c3\u0015\rU[Xp`A\u0001\u0011\u0015aX\u00011\u0001f\u0003-1\u0017.\u001a7e\u0019\u0016tw\r\u001e5\t\u000fy,\u0001\u0013!a\u0001K\u0006Ya-[3mI>3gm]3u\u0011\u0015!W\u00011\u0001f\u0011%\t\u0019!\u0002I\u0001\u0002\u0004\t)!A\u0005csR,wJ\u001d3feB!\u0011qAA\t\u001b\t\tIA\u0003\u0003\u0002\f\u00055\u0011a\u00018j_*\u0011\u0011qB\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002\u0014\u0005%!!\u0003\"zi\u0016|%\u000fZ3s\u0003UaWM\\4uQ\u001aKW\r\u001c3%I\u00164\u0017-\u001e7uII*\"!!\u0007+\u0005\u0015\u0004\u0018!\u00067f]\u001e$\bNR5fY\u0012$C-\u001a4bk2$H\u0005N\u000b\u0003\u0003?Q3!!\u0002q)-)\u00161EA\u0013\u0003O\tI#a\u000b\t\u000bqD\u0001\u0019A3\t\u000byD\u0001\u0019A3\t\u000b\u0011D\u0001\u0019A3\t\u000f\u0005\r\u0001\u00021\u0001\u0002\u0006!9\u0011Q\u0006\u0005A\u0002\u0005=\u0012\u0001E2p[B,H/\u001a$sC6,7+\u001b>f!\u001da\u0015\u0011GA\u001bK\u0016L1!a\rN\u0005%1UO\\2uS>t'\u0007E\u0003M\u0003o\tY$C\u0002\u0002:5\u0013Q!\u0011:sCf\u00042\u0001TA\u001f\u0013\r\ty$\u0014\u0002\u0005\u0005f$X-A\u000btS6\u0004H.\u001a$sC6Lgn\u001a)s_R|7m\u001c7\u0015\t\u0005\u0015\u00131\n\t\t\u0011\u0006\u001d\u0003\f\u0017-Y=&\u0019\u0011\u0011\n!\u0003\u0011\tKG-\u001b$m_^Da!!\u0014\n\u0001\u0004)\u0017\u0001F7bq&lW/\\'fgN\fw-\u001a'f]\u001e$\b.\u0001\u000ftS6\u0004H.\u001a$sC6Lgn\u001a)s_R|7m\u001c7EK\u000e|G-\u001a:\u0015\u0007U\u000b\u0019\u0006\u0003\u0004\u0002N)\u0001\r!Z\u0001\u001dg&l\u0007\u000f\\3Ge\u0006l\u0017N\\4Qe>$xnY8m\u000b:\u001cw\u000eZ3s)\r)\u0016\u0011\f\u0005\u0007\u0003\u001bZ\u0001\u0019A3\u0003!\u0019\u0013\u0018-\\5oO\u0016C8-\u001a9uS>t7c\u0001\u0007\u0002`A!\u0011\u0011MA9\u001d\u0011\t\u0019'!\u001c\u000f\t\u0005\u0015\u00141N\u0007\u0003\u0003OR1!!\u001bG\u0003\u0019a$o\\8u}%\ta*C\u0002\u0002p5\u000bq\u0001]1dW\u0006<W-\u0003\u0003\u0002t\u0005U$\u0001\u0005*v]RLW.Z#yG\u0016\u0004H/[8o\u0015\r\ty'T\u0001\u0004[N<\u0007\u0003BA>\u0003\u0007sA!! \u0002\u0000A\u0019\u0011QM'\n\u0007\u0005\u0005U*\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003\u000b\u000b9I\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003\u0003kE\u0003BAF\u0003\u001f\u00032!!$\r\u001b\u0005\t\u0001bBA<\u001d\u0001\u0007\u0011\u0011P\u0001\u0011E&<WI\u001c3jC:$UmY8eKJ,\"!!&\u0011\u000f1\u000b\t$a&fKB\u0019\u0011,!'\n\u0007\u0005m%L\u0001\u0007CsR,\u0017\n^3sCR|'/A\tcS\u001e,e\u000eZ5b]\u0012+7m\u001c3fe\u0002\n1\u0003\\5ui2,WI\u001c3jC:$UmY8eKJ\fA\u0003\\5ui2,WI\u001c3jC:$UmY8eKJ\u0004#\u0001H*j[BdWM\u0012:b[&tw\r\u0015:pi>\u001cw\u000e\\#oG>$WM]\n\u0004'\u0005\u001d\u0006#BAU\u0003\u000fDf\u0002BAV\u0003\u0003tA!!,\u0002<:!\u0011qVA\\\u001d\u0011\t\t,!.\u000f\t\u0005\u0015\u00141W\u0005\u0002\u000b&\u00111\tR\u0005\u0004\u0003s\u0013\u0015\u0001B5na2LA!!0\u0002@\u00061a-^:j]\u001eT1!!/C\u0013\u0011\t\u0019-!2\u0002\u0017\u001d\u0013\u0018\r\u001d5Ti\u0006<Wm\u001d\u0006\u0005\u0003{\u000by,\u0003\u0003\u0002J\u0006-'AF*j[BdW\rT5oK\u0006\u0014xI]1qQN#\u0018mZ3\u000b\t\u0005\r\u0017Q\u0019\t\u0004\u0019\u0006=\u0017bAAi\u001b\n!Aj\u001c8h)\u0011\t).a6\u0011\u0007\u000555\u0003C\u0004\u0002NU\u0001\r!!4\u0002\u0017\r\u0014X-\u0019;f\u0019><\u0017n\u0019\u000b\u0005\u0003;\fYP\u0005\u0005\u0002`\u0006\r\u0018q^A{\r\u0019\t\tO\u0006\u0001\u0002^\naAH]3gS:,W.\u001a8u}A!\u0011Q]Av\u001b\t\t9OC\u0002\u0002j\n\u000bQa\u001d;bO\u0016LA!!<\u0002h\nyqI]1qQN#\u0018mZ3M_\u001eL7\r\u0005\u0003\u0002f\u0006E\u0018\u0002BAz\u0003O\u0014\u0011\"\u00138IC:$G.\u001a:\u0011\t\u0005\u0015\u0018q_\u0005\u0005\u0003s\f9O\u0001\u0006PkRD\u0015M\u001c3mKJDq!!@\u0017\u0001\u0004\ty0A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7\u000f\u0005\u0003\u0003\u0002\t\rQ\"\u0001\"\n\u0007\t\u0015!I\u0001\u0006BiR\u0014\u0018NY;uKN\u0014Q\u0003R3mS6LG/\u001a:Ge\u0006l\u0017N\\4Ti\u0006<WmE\u0002\u0018\u0005\u0017\u0001b!!:\u0003\u000e\tE\u0011\u0002\u0002B\b\u0003O\u0014!b\u0012:ba\"\u001cF/Y4f!\u0019\u0011\tAa\u0005Y1&\u0019!Q\u0003\"\u0003\u0013\u0019cwn^*iCB,\u0017AD:fa\u0006\u0014\u0018\r^8s\u0005f$Xm]\u000b\u00021\u0006y1/\u001a9be\u0006$xN\u001d\"zi\u0016\u001c\b%\u0001\tnCbLW.^7MS:,')\u001f;fgV\tQ-A\tnCbLW.^7MS:,')\u001f;fg\u0002*\u0012A[\u0001\u0011C2dwn\u001e+sk:\u001c\u0017\r^5p]\u0002\"\u0002B!\u000b\u0003,\t5\"q\u0006\t\u0004\u0003\u001b;\u0002B\u0002B\f=\u0001\u0007\u0001\f\u0003\u0004\u0003\u001ey\u0001\r!\u001a\u0005\u0006Sz\u0001\rA[\u0001\u0003S:,\"A!\u000e\u0011\u000b\t\u0005!q\u0007-\n\u0007\te\"IA\u0003J]2,G/A\u0002j]\u0002\n1a\\;u+\t\u0011\t\u0005E\u0003\u0003\u0002\t\r\u0003,C\u0002\u0003F\t\u0013aaT;uY\u0016$\u0018\u0001B8vi\u0002\nQa\u001d5ba\u0016,\"A!\u0005\u0002\rMD\u0017\r]3!\u0003EIg.\u001b;jC2\fE\u000f\u001e:jEV$Xm]\u000b\u0003\u0003\u007f\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003s\"B!a9\u0003Z!9\u0011Q`\u0014A\u0002\u0005}(a\u0006'f]\u001e$\bNR5fY\u00124%/Y7j]\u001e\u001cF/Y4f'\rA#1B\u0001\u0012Y\u0016tw\r\u001e5GS\u0016dG\rT3oORD\u0017A\u00057f]\u001e$\bNR5fY\u0012dUM\\4uQ\u0002\n\u0011\u0003\\3oORDg)[3mI>3gm]3u\u0003IaWM\\4uQ\u001aKW\r\u001c3PM\u001a\u001cX\r\u001e\u0011\u0002'5\f\u00070[7v[\u001a\u0013\u0018-\\3MK:<G\u000f\u001b\u0011\u0016\u0005\u0005\u0015\u0011A\u00032zi\u0016|%\u000fZ3sAA)AJa\u001c\u00020%\u0019!\u0011O'\u0003\r=\u0003H/[8o)1\u0011)Ha\u001e\u0003z\tm$Q\u0010B@!\r\ti\t\u000b\u0005\u0007\u0005?\u0012\u0004\u0019A3\t\r\t\r$\u00071\u0001f\u0011\u0015!'\u00071\u0001f\u0011\u001d\t\u0019A\ra\u0001\u0003\u000bAq!!\f3\u0001\u0004\u0011i\u0007\u0006\u0006\u0003v\t\r%Q\u0011BD\u0005\u0013CaAa\u00184\u0001\u0004)\u0007B\u0002B2g\u0001\u0007Q\rC\u0003eg\u0001\u0007Q\rC\u0004\u0002\u0004M\u0002\r!!\u0002\u0002!5Lg.[7v[\u000eCWO\\6TSj,\u0017!E7j]&lW/\\\"ik:\\7+\u001b>fA\u0005Q\u0011N\u001c;EK\u000e|G-\u001a:\u0002\u0017%tG\u000fR3d_\u0012,'\u000f\t\u000b\u0005\u0003G\u0014)\nC\u0004\u0002~z\u0002\r!a@")
public final class Framing {
    public static Flow<ByteString, ByteString, NotUsed> simpleFramingProtocolEncoder(int maximumMessageLength) {
        return Framing$.MODULE$.simpleFramingProtocolEncoder(maximumMessageLength);
    }

    public static Flow<ByteString, ByteString, NotUsed> simpleFramingProtocolDecoder(int maximumMessageLength) {
        return Framing$.MODULE$.simpleFramingProtocolDecoder(maximumMessageLength);
    }

    public static BidiFlow<ByteString, ByteString, ByteString, ByteString, NotUsed> simpleFramingProtocol(int maximumMessageLength) {
        return Framing$.MODULE$.simpleFramingProtocol(maximumMessageLength);
    }

    public static Flow<ByteString, ByteString, NotUsed> lengthField(int fieldLength, int fieldOffset, int maximumFrameLength, ByteOrder byteOrder, Function2<byte[], Object, Object> computeFrameSize) {
        return Framing$.MODULE$.lengthField(fieldLength, fieldOffset, maximumFrameLength, byteOrder, computeFrameSize);
    }

    public static ByteOrder lengthField$default$4() {
        return Framing$.MODULE$.lengthField$default$4();
    }

    public static int lengthField$default$2() {
        return Framing$.MODULE$.lengthField$default$2();
    }

    public static Flow<ByteString, ByteString, NotUsed> lengthField(int fieldLength, int fieldOffset, int maximumFrameLength, ByteOrder byteOrder) {
        return Framing$.MODULE$.lengthField(fieldLength, fieldOffset, maximumFrameLength, byteOrder);
    }

    public static boolean delimiter$default$3() {
        return Framing$.MODULE$.delimiter$default$3();
    }

    public static Flow<ByteString, ByteString, NotUsed> delimiter(ByteString delimiter, int maximumFrameLength, boolean allowTruncation) {
        return Framing$.MODULE$.delimiter(delimiter, maximumFrameLength, allowTruncation);
    }

    private static class DelimiterFramingStage
    extends GraphStage<FlowShape<ByteString, ByteString>> {
        private final ByteString separatorBytes;
        private final int maximumLineBytes;
        private final boolean allowTruncation;
        private final Inlet<ByteString> in;
        private final Outlet<ByteString> out;
        private final FlowShape<ByteString, ByteString> shape;

        public ByteString separatorBytes() {
            return this.separatorBytes;
        }

        public int maximumLineBytes() {
            return this.maximumLineBytes;
        }

        public boolean allowTruncation() {
            return this.allowTruncation;
        }

        public Inlet<ByteString> in() {
            return this.in;
        }

        public Outlet<ByteString> out() {
            return this.out;
        }

        @Override
        public FlowShape<ByteString, ByteString> shape() {
            return this.shape;
        }

        @Override
        public Attributes initialAttributes() {
            return Stages$DefaultAttributes$.MODULE$.delimiterFraming();
        }

        public String toString() {
            return "DelimiterFraming";
        }

        @Override
        public GraphStageLogic createLogic(Attributes inheritedAttributes) {
            return new InHandler(this){
                private volatile Framing$DelimiterFramingStage$$anon$2$FrameIterator$ FrameIterator$module;
                private volatile Framing$DelimiterFramingStage$$anon$2$LightArray$ LightArray$module;
                private final byte firstSeparatorByte;
                private ByteString akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$buffer;
                private int nextPossibleMatch;
                private final DelimiterFramingStage$$anon$2$LightArray<Tuple2<Object, Object>> akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$indices;
                private final /* synthetic */ DelimiterFramingStage $outer;

                public void onDownstreamFinish() throws Exception {
                    OutHandler.onDownstreamFinish$(this);
                }

                public void onDownstreamFinish(Throwable cause) throws Exception {
                    OutHandler.onDownstreamFinish$(this, cause);
                }

                public void onUpstreamFailure(Throwable ex) throws Exception {
                    InHandler.onUpstreamFailure$(this, ex);
                }

                private Framing$DelimiterFramingStage$$anon$2$FrameIterator$ FrameIterator() {
                    if (this.FrameIterator$module == null) {
                        this.FrameIterator$lzycompute$1();
                    }
                    return this.FrameIterator$module;
                }

                private Framing$DelimiterFramingStage$$anon$2$LightArray$ LightArray() {
                    if (this.LightArray$module == null) {
                        this.LightArray$lzycompute$1();
                    }
                    return this.LightArray$module;
                }

                private byte firstSeparatorByte() {
                    return this.firstSeparatorByte;
                }

                public ByteString akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$buffer() {
                    return this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$buffer;
                }

                private void buffer_$eq(ByteString x$1) {
                    this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$buffer = x$1;
                }

                private int nextPossibleMatch() {
                    return this.nextPossibleMatch;
                }

                private void nextPossibleMatch_$eq(int x$1) {
                    this.nextPossibleMatch = x$1;
                }

                public DelimiterFramingStage$$anon$2$LightArray<Tuple2<Object, Object>> akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$indices() {
                    return this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$indices;
                }

                public void onPush() {
                    this.buffer_$eq(this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$buffer().$plus$plus(this.grab(this.$outer.in())));
                    this.searchIndices();
                }

                public void onPull() {
                    this.searchIndices();
                }

                public void onUpstreamFinish() {
                    if (this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$buffer().isEmpty()) {
                        this.completeStage();
                        return;
                    }
                    if (this.isAvailable(this.$outer.out())) {
                        this.searchIndices();
                        return;
                    }
                }

                private void tryPull() {
                    if (this.isClosed(this.$outer.in())) {
                        if (this.$outer.allowTruncation()) {
                            this.push(this.$outer.out(), this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$buffer());
                            this.completeStage();
                            return;
                        }
                        this.failStage(new FramingException("Stream finished but there was a truncated final frame in the buffer"));
                        return;
                    }
                    this.pull(this.$outer.in());
                }

                private void searchIndices() {
                    while (true) {
                        BoxedUnit boxedUnit;
                        int previous;
                        int n;
                        Tuple2<Object, Object> tuple2;
                        int possibleMatchPos = this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$buffer().indexOf(BoxesRunTime.boxToByte(this.firstSeparatorByte()), this.nextPossibleMatch());
                        Tuple2<Object, Object> tuple22 = this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$indices().lastOption();
                        Tuple2<Object, Object> tuple23 = OptionVal$Some$.MODULE$.unapply(tuple22);
                        if (!OptionVal$.MODULE$.isEmpty$extension(tuple23) && (tuple2 = OptionVal$.MODULE$.get$extension(tuple23)) != null) {
                            int i = tuple2._2$mcI$sp();
                            n = i + this.$outer.separatorBytes().size();
                        } else {
                            n = previous = 0;
                        }
                        if (possibleMatchPos - previous > this.$outer.maximumLineBytes()) {
                            this.failStage(new FramingException(new StringBuilder(64).append("Read ").append(possibleMatchPos - previous).append(" bytes ").append("which is more than ").append(this.$outer.maximumLineBytes()).append(" without seeing a line terminator").toString()));
                            boxedUnit = BoxedUnit.UNIT;
                            break;
                        }
                        if (possibleMatchPos == -1) {
                            if (this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$buffer().length() - previous > this.$outer.maximumLineBytes()) {
                                this.failStage(new FramingException(new StringBuilder(64).append("Read ").append(this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$buffer().length() - previous).append(" bytes ").append("which is more than ").append(this.$outer.maximumLineBytes()).append(" without seeing a line terminator").toString()));
                                boxedUnit = BoxedUnit.UNIT;
                                break;
                            }
                            this.nextPossibleMatch_$eq(this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$buffer().length());
                            this.doParse();
                            boxedUnit = BoxedUnit.UNIT;
                            break;
                        }
                        if (possibleMatchPos + this.$outer.separatorBytes().length() > this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$buffer().length()) {
                            this.nextPossibleMatch_$eq(possibleMatchPos);
                            this.doParse();
                            boxedUnit = BoxedUnit.UNIT;
                            break;
                        }
                        ByteString byteString = this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$buffer().slice(possibleMatchPos, possibleMatchPos + this.$outer.separatorBytes().length());
                        ByteString byteString2 = this.$outer.separatorBytes();
                        if (!(byteString != null ? !((Object)byteString).equals(byteString2) : byteString2 != null)) {
                            this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$indices().$plus$eq(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(BoxesRunTime.boxToInteger(previous)), BoxesRunTime.boxToInteger(possibleMatchPos)));
                            this.nextPossibleMatch_$eq(possibleMatchPos + this.$outer.separatorBytes().length());
                            if (this.nextPossibleMatch() != this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$buffer().length() && !this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$indices().isFull()) continue;
                            this.doParse();
                            boxedUnit = BoxedUnit.UNIT;
                            break;
                        }
                        this.nextPossibleMatch_$eq(this.nextPossibleMatch() + 1);
                    }
                }

                private void doParse() {
                    if (this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$indices().isEmpty()) {
                        this.tryPull();
                        return;
                    }
                    if (this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$indices().length() == 1) {
                        Tuple2<Object, Object> indice = this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$indices().apply(0);
                        this.push(this.$outer.out(), this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$buffer().slice(indice._1$mcI$sp(), indice._2$mcI$sp()).compact());
                        this.reset();
                        if (this.isClosed(this.$outer.in()) && this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$buffer().isEmpty()) {
                            this.completeStage();
                            return;
                        }
                        return;
                    }
                    this.emitMultiple(this.$outer.out(), new DelimiterFramingStage$$anon$2$FrameIterator(this, this.FrameIterator().$lessinit$greater$default$1()), () -> {
                        this.reset();
                        if (this.isClosed($this.$outer.in()) && this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$buffer().isEmpty()) {
                            this.completeStage();
                            return;
                        }
                    });
                }

                private void reset() {
                    int n;
                    Tuple2<Object, Object> tuple2;
                    Tuple2<Object, Object> tuple22 = this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$indices().lastOption();
                    Tuple2<Object, Object> tuple23 = OptionVal$Some$.MODULE$.unapply(tuple22);
                    if (!OptionVal$.MODULE$.isEmpty$extension(tuple23) && (tuple2 = OptionVal$.MODULE$.get$extension(tuple23)) != null) {
                        int i = tuple2._2$mcI$sp();
                        n = i + this.$outer.separatorBytes().size();
                    } else {
                        n = 0;
                    }
                    int previous = n;
                    this.buffer_$eq(this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$buffer().drop(previous).compact());
                    this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$indices().setLength(0);
                    this.nextPossibleMatch_$eq(0);
                }

                private final void FrameIterator$lzycompute$1() {
                    DelimiterFramingStage$$anon$2 var1_1 = this;
                    synchronized (var1_1) {
                        if (this.FrameIterator$module == null) {
                            this.FrameIterator$module = new Framing$DelimiterFramingStage$$anon$2$FrameIterator$(this);
                        }
                    }
                }

                private final void LightArray$lzycompute$1() {
                    DelimiterFramingStage$$anon$2 var1_1 = this;
                    synchronized (var1_1) {
                        if (this.LightArray$module == null) {
                            this.LightArray$module = new Framing$DelimiterFramingStage$$anon$2$LightArray$(this);
                        }
                    }
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    super($outer.shape());
                    InHandler.$init$(this);
                    OutHandler.$init$(this);
                    this.firstSeparatorByte = $outer.separatorBytes().head();
                    this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$buffer = ByteString$.MODULE$.empty();
                    this.nextPossibleMatch = 0;
                    this.akka$stream$scaladsl$Framing$DelimiterFramingStage$$anon$$indices = new DelimiterFramingStage$$anon$2$LightArray<T>(this, 256, this.LightArray().$lessinit$greater$default$2(), ClassTag$.MODULE$.apply(Tuple2.class));
                    this.setHandlers($outer.in(), $outer.out(), this);
                }

                private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                    return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$doParse$1(akka.stream.scaladsl.Framing$DelimiterFramingStage$$anon$2 )}, serializedLambda);
                }
            };
        }

        public DelimiterFramingStage(ByteString separatorBytes, int maximumLineBytes, boolean allowTruncation) {
            this.separatorBytes = separatorBytes;
            this.maximumLineBytes = maximumLineBytes;
            this.allowTruncation = allowTruncation;
            this.in = Inlet$.MODULE$.apply("DelimiterFramingStage.in");
            this.out = Outlet$.MODULE$.apply("DelimiterFramingStage.out");
            this.shape = new FlowShape<ByteString, ByteString>(this.in(), this.out());
        }
    }

    public static class FramingException
    extends RuntimeException {
        public FramingException(String msg) {
            super(msg);
        }
    }

    private static final class LengthFieldFramingStage
    extends GraphStage<FlowShape<ByteString, ByteString>> {
        private final int lengthFieldLength;
        private final int lengthFieldOffset;
        private final int maximumFrameLength;
        private final ByteOrder byteOrder;
        public final Option<Function2<byte[], Object, Object>> akka$stream$scaladsl$Framing$LengthFieldFramingStage$$computeFrameSize;
        private final int akka$stream$scaladsl$Framing$LengthFieldFramingStage$$minimumChunkSize;
        private final Function2<ByteIterator, Object, Object> akka$stream$scaladsl$Framing$LengthFieldFramingStage$$intDecoder;
        private final Inlet<ByteString> in;
        private final Outlet<ByteString> out;
        private final FlowShape<ByteString, ByteString> shape;

        public int lengthFieldLength() {
            return this.lengthFieldLength;
        }

        public int lengthFieldOffset() {
            return this.lengthFieldOffset;
        }

        public int maximumFrameLength() {
            return this.maximumFrameLength;
        }

        public ByteOrder byteOrder() {
            return this.byteOrder;
        }

        public int akka$stream$scaladsl$Framing$LengthFieldFramingStage$$minimumChunkSize() {
            return this.akka$stream$scaladsl$Framing$LengthFieldFramingStage$$minimumChunkSize;
        }

        public Function2<ByteIterator, Object, Object> akka$stream$scaladsl$Framing$LengthFieldFramingStage$$intDecoder() {
            return this.akka$stream$scaladsl$Framing$LengthFieldFramingStage$$intDecoder;
        }

        public Inlet<ByteString> in() {
            return this.in;
        }

        public Outlet<ByteString> out() {
            return this.out;
        }

        @Override
        public FlowShape<ByteString, ByteString> shape() {
            return this.shape;
        }

        @Override
        public GraphStageLogic createLogic(Attributes inheritedAttributes) {
            return new InHandler(this){
                private ByteString buffer;
                private int frameSize;
                private final /* synthetic */ LengthFieldFramingStage $outer;

                public void onDownstreamFinish() throws Exception {
                    OutHandler.onDownstreamFinish$(this);
                }

                public void onDownstreamFinish(Throwable cause) throws Exception {
                    OutHandler.onDownstreamFinish$(this, cause);
                }

                public void onUpstreamFailure(Throwable ex) throws Exception {
                    InHandler.onUpstreamFailure$(this, ex);
                }

                private ByteString buffer() {
                    return this.buffer;
                }

                private void buffer_$eq(ByteString x$1) {
                    this.buffer = x$1;
                }

                private int frameSize() {
                    return this.frameSize;
                }

                private void frameSize_$eq(int x$1) {
                    this.frameSize = x$1;
                }

                private void pushFrame() {
                    CompactByteString emit = this.buffer().take(this.frameSize()).compact();
                    this.buffer_$eq(this.buffer().drop(this.frameSize()));
                    this.frameSize_$eq(Integer.MAX_VALUE);
                    this.push(this.$outer.out(), emit);
                    if (this.buffer().isEmpty() && this.isClosed(this.$outer.in())) {
                        this.completeStage();
                        return;
                    }
                }

                private void tryPushFrame() {
                    int buffSize = this.buffer().size();
                    if (buffSize >= this.frameSize()) {
                        this.pushFrame();
                        return;
                    }
                    if (buffSize >= this.$outer.akka$stream$scaladsl$Framing$LengthFieldFramingStage$$minimumChunkSize()) {
                        int n;
                        int parsedLength = BoxesRunTime.unboxToInt(this.$outer.akka$stream$scaladsl$Framing$LengthFieldFramingStage$$intDecoder().apply(this.buffer().iterator().drop(this.$outer.lengthFieldOffset()), BoxesRunTime.boxToInteger(this.$outer.lengthFieldLength())));
                        Option<Function2<byte[], Object, Object>> option = this.$outer.akka$stream$scaladsl$Framing$LengthFieldFramingStage$$computeFrameSize;
                        if (option instanceof Some) {
                            Some some = (Some)option;
                            Function2 f = (Function2)some.value();
                            n = BoxesRunTime.unboxToInt(f.apply(this.buffer().take(this.$outer.lengthFieldOffset()).toArrayUnsafe(), BoxesRunTime.boxToInteger(parsedLength)));
                        } else if (None$.MODULE$.equals(option)) {
                            n = parsedLength + this.$outer.akka$stream$scaladsl$Framing$LengthFieldFramingStage$$minimumChunkSize();
                        } else {
                            throw new MatchError(option);
                        }
                        this.frameSize_$eq(n);
                        if (this.frameSize() > this.$outer.maximumFrameLength()) {
                            this.failStage(new FramingException(new StringBuilder(70).append("Maximum allowed frame size is ").append(this.$outer.maximumFrameLength()).append(" but decoded frame header reported size ").append(this.frameSize()).toString()));
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$Framing$LengthFieldFramingStage$$computeFrameSize.isEmpty() && parsedLength < 0) {
                            this.failStage(new FramingException(new StringBuilder(44).append("Decoded frame header reported negative size ").append(parsedLength).toString()));
                            return;
                        }
                        if (this.frameSize() < this.$outer.akka$stream$scaladsl$Framing$LengthFieldFramingStage$$minimumChunkSize()) {
                            this.failStage(new FramingException(new StringBuilder(53).append("Computed frame size ").append(this.frameSize()).append(" is less than minimum chunk size ").append(this.$outer.akka$stream$scaladsl$Framing$LengthFieldFramingStage$$minimumChunkSize()).toString()));
                            return;
                        }
                        if (buffSize >= this.frameSize()) {
                            this.pushFrame();
                            return;
                        }
                        this.tryPull();
                        return;
                    }
                    this.tryPull();
                }

                private void tryPull() {
                    if (this.isClosed(this.$outer.in())) {
                        this.failStage(new FramingException("Stream finished but there was a truncated final frame in the buffer"));
                        return;
                    }
                    this.pull(this.$outer.in());
                }

                public void onPush() {
                    this.buffer_$eq(this.buffer().$plus$plus(this.grab(this.$outer.in())));
                    this.tryPushFrame();
                }

                public void onPull() {
                    this.tryPushFrame();
                }

                public void onUpstreamFinish() {
                    if (this.buffer().isEmpty()) {
                        this.completeStage();
                        return;
                    }
                    if (this.isAvailable(this.$outer.out())) {
                        this.tryPushFrame();
                        return;
                    }
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    super($outer.shape());
                    InHandler.$init$(this);
                    OutHandler.$init$(this);
                    this.buffer = ByteString$.MODULE$.empty();
                    this.frameSize = Integer.MAX_VALUE;
                    this.setHandlers($outer.in(), $outer.out(), this);
                }
            };
        }

        public LengthFieldFramingStage(int lengthFieldLength, int lengthFieldOffset, int maximumFrameLength, ByteOrder byteOrder, Option<Function2<byte[], Object, Object>> computeFrameSize) {
            Function2<ByteIterator, Object, Object> function2;
            this.lengthFieldLength = lengthFieldLength;
            this.lengthFieldOffset = lengthFieldOffset;
            this.maximumFrameLength = maximumFrameLength;
            this.byteOrder = byteOrder;
            this.akka$stream$scaladsl$Framing$LengthFieldFramingStage$$computeFrameSize = computeFrameSize;
            this.akka$stream$scaladsl$Framing$LengthFieldFramingStage$$minimumChunkSize = lengthFieldOffset + lengthFieldLength;
            ByteOrder byteOrder2 = byteOrder;
            ByteOrder byteOrder3 = ByteOrder.BIG_ENDIAN;
            ByteOrder byteOrder4 = byteOrder2;
            if (!(byteOrder3 != null ? !byteOrder3.equals(byteOrder4) : byteOrder4 != null)) {
                function2 = Framing$.MODULE$.akka$stream$scaladsl$Framing$$bigEndianDecoder();
            } else {
                ByteOrder byteOrder5 = ByteOrder.LITTLE_ENDIAN;
                ByteOrder byteOrder6 = byteOrder2;
                if (!(byteOrder5 != null ? !byteOrder5.equals(byteOrder6) : byteOrder6 != null)) {
                    function2 = Framing$.MODULE$.akka$stream$scaladsl$Framing$$littleEndianDecoder();
                } else {
                    throw new RuntimeException();
                }
            }
            this.akka$stream$scaladsl$Framing$LengthFieldFramingStage$$intDecoder = function2;
            this.in = Inlet$.MODULE$.apply("LengthFieldFramingStage.in");
            this.out = Outlet$.MODULE$.apply("LengthFieldFramingStage.out");
            this.shape = new FlowShape<ByteString, ByteString>(this.in(), this.out());
        }

        public LengthFieldFramingStage(int lengthFieldLength, int lengthFieldOffset, int maximumFrameLength, ByteOrder byteOrder) {
            this(lengthFieldLength, lengthFieldOffset, maximumFrameLength, byteOrder, None$.MODULE$);
        }
    }

    private static class SimpleFramingProtocolEncoder
    extends GraphStages.SimpleLinearGraphStage<ByteString> {
        public final long akka$stream$scaladsl$Framing$SimpleFramingProtocolEncoder$$maximumMessageLength;

        @Override
        public GraphStageLogic createLogic(Attributes inheritedAttributes) {
            return new InHandler(this){
                private final /* synthetic */ SimpleFramingProtocolEncoder $outer;

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
                    ByteString message = (ByteString)this.grab(this.$outer.in());
                    int msgSize = message.size();
                    if ((long)msgSize > this.$outer.akka$stream$scaladsl$Framing$SimpleFramingProtocolEncoder$$maximumMessageLength) {
                        this.failStage(new FramingException(new StringBuilder(57).append("Maximum allowed message size is ").append(this.$outer.akka$stream$scaladsl$Framing$SimpleFramingProtocolEncoder$$maximumMessageLength).append(" but tried to send ").append(msgSize).append(" bytes").toString()));
                        return;
                    }
                    ByteString header = ByteString$.MODULE$.apply(ScalaRunTime$.MODULE$.wrapIntArray(new int[]{msgSize >> 24 & 0xFF, msgSize >> 16 & 0xFF, msgSize >> 8 & 0xFF, msgSize & 0xFF}), Numeric$IntIsIntegral$.MODULE$);
                    this.push(this.$outer.out(), header.$plus$plus(message));
                }

                public void onPull() {
                    this.pull(this.$outer.in());
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

        public SimpleFramingProtocolEncoder(long maximumMessageLength) {
            this.akka$stream$scaladsl$Framing$SimpleFramingProtocolEncoder$$maximumMessageLength = maximumMessageLength;
        }
    }
}

