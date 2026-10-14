/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.annotation.InternalApi;
import akka.event.LoggingAdapter;
import akka.io.Tcp;
import akka.io.Tcp$Abort$;
import akka.io.Tcp$Aborted$;
import akka.io.Tcp$Close$;
import akka.io.Tcp$Closed$;
import akka.io.Tcp$ConfirmedClose$;
import akka.io.Tcp$ConfirmedClosed$;
import akka.io.Tcp$PeerClosed$;
import akka.io.Tcp$ResumeReading$;
import akka.stream.Attributes;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Materializer;
import akka.stream.Outlet;
import akka.stream.StreamTcpException;
import akka.stream.SubscriptionWithCancelException;
import akka.stream.impl.ReactiveStreamsCompliance$;
import akka.stream.impl.io.TcpConnectionStage$;
import akka.stream.impl.io.TcpConnectionStage$WriteAck$;
import akka.stream.impl.io.TcpConnectionStage$WriteDelayAck$;
import akka.stream.scaladsl.TcpAttributes;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageLogic$EagerTerminateOutput$;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.stream.stage.StageLogging;
import akka.util.ByteString;
import akka.util.ByteString$;
import java.io.Serializable;
import java.net.InetSocketAddress;
import scala.$less$colon$less$;
import scala.Function0;
import scala.Function1;
import scala.Predef$;
import scala.Product;
import scala.Tuple2;
import scala.collection.ArrayOps$;
import scala.collection.Iterator;
import scala.concurrent.Promise;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0011\u0015sACA\b\u0003#A\t!!\u0007\u0002\"\u0019Q\u0011QEA\t\u0011\u0003\tI\"a\n\t\u000f\u0005U\u0012\u0001\"\u0001\u0002:\u001d9\u00111H\u0001\t\u0002\u0006ubaBA!\u0003!\u0005\u00151\t\u0005\b\u0003k!A\u0011AA;\u0011%\t9\bBA\u0001\n\u0003\nI\bC\u0005\u0002\f\u0012\t\t\u0011\"\u0001\u0002\u000e\"I\u0011Q\u0013\u0003\u0002\u0002\u0013\u0005\u0011q\u0013\u0005\n\u0003G#\u0011\u0011!C!\u0003KC\u0011\"a-\u0005\u0003\u0003%\t!!.\t\u0013\u0005}F!!A\u0005B\u0005\u0005\u0007\"CAb\t\u0005\u0005I\u0011IAc\u0011%\t9\rBA\u0001\n\u0013\tImB\u0004\u0002R\u0006AI)a5\u0007\u000f\u0005U\u0017\u0001##\u0002X\"9\u0011QG\b\u0005\u0002\u0005e\u0007\"CA<\u001f\u0005\u0005I\u0011IA=\u0011%\tYiDA\u0001\n\u0003\ti\tC\u0005\u0002\u0016>\t\t\u0011\"\u0001\u0002\\\"I\u00111U\b\u0002\u0002\u0013\u0005\u0013Q\u0015\u0005\n\u0003g{\u0011\u0011!C\u0001\u0003?D\u0011\"a0\u0010\u0003\u0003%\t%!1\t\u0013\u0005\rw\"!A\u0005B\u0005\u0015\u0007\"CAd\u001f\u0005\u0005I\u0011BAe\u0011%\t\u0019/\u0001b\u0001\n\u0013\t)\u000f\u0003\u0005\u0002x\u0006\u0001\u000b\u0011BAt\r%\tI0\u0001I\u0001$\u0003\tY\u0010C\u0004\u0002~n1\t!a@\u0007\r\t\u0005\u0011\u0001\u0011B\u0002\u0011)\u00119!\bBK\u0002\u0013\u0005!\u0011\u0002\u0005\u000b\u0005/i\"\u0011#Q\u0001\n\t-\u0001B\u0003B\r;\tU\r\u0011\"\u0001\u0003\u001c!Q!1E\u000f\u0003\u0012\u0003\u0006IA!\b\t\u0015\t\u0015RD!f\u0001\n\u0003\u00119\u0003\u0003\u0006\u0003Bu\u0011\t\u0012)A\u0005\u0005SA!\"!@\u001e\u0005+\u0007I\u0011AA\u0000\u0011)\u0011\u0019%\bB\tB\u0003%\u0011q\u0017\u0005\b\u0003kiB\u0011\u0001B#\u0011%\u0011\t&HA\u0001\n\u0003\u0011\u0019\u0006C\u0005\u0003^u\t\n\u0011\"\u0001\u0003`!I!QO\u000f\u0012\u0002\u0013\u0005!q\u000f\u0005\n\u0005wj\u0012\u0013!C\u0001\u0005{B\u0011B!!\u001e#\u0003%\tAa!\t\u0013\u0005]T$!A\u0005B\u0005e\u0004\"CAF;\u0005\u0005I\u0011AAG\u0011%\t)*HA\u0001\n\u0003\u00119\tC\u0005\u0002$v\t\t\u0011\"\u0011\u0002&\"I\u00111W\u000f\u0002\u0002\u0013\u0005!1\u0012\u0005\n\u0005\u001fk\u0012\u0011!C!\u0005#C\u0011\"a0\u001e\u0003\u0003%\t%!1\t\u0013\u0005\rW$!A\u0005B\u0005\u0015\u0007\"\u0003BK;\u0005\u0005I\u0011\tBL\u000f%\u0011Y*AA\u0001\u0012\u0003\u0011iJB\u0005\u0003\u0002\u0005\t\t\u0011#\u0001\u0003 \"9\u0011Q\u0007\u001c\u0005\u0002\tU\u0006\"CAbm\u0005\u0005IQIAc\u0011%\u00119LNA\u0001\n\u0003\u0013I\fC\u0005\u0003DZ\n\t\u0011\"!\u0003F\"I\u0011q\u0019\u001c\u0002\u0002\u0013%\u0011\u0011\u001a\u0004\u0007\u0005/\f\u0001I!7\t\u0015\tmGH!f\u0001\n\u0003\u0011I\u0001\u0003\u0006\u0003^r\u0012\t\u0012)A\u0005\u0005\u0017A!\"!@=\u0005+\u0007I\u0011AA\u0000\u0011)\u0011\u0019\u0005\u0010B\tB\u0003%\u0011q\u0017\u0005\u000b\u0005?d$Q3A\u0005\u0002\t\u0005\bB\u0003Bxy\tE\t\u0015!\u0003\u0003d\"9\u0011Q\u0007\u001f\u0005\u0002\tE\b\"\u0003B)y\u0005\u0005I\u0011\u0001B~\u0011%\u0011i\u0006PI\u0001\n\u0003\u0011y\u0006C\u0005\u0003vq\n\n\u0011\"\u0001\u0003\u0004\"I!1\u0010\u001f\u0012\u0002\u0013\u000511\u0001\u0005\n\u0003ob\u0014\u0011!C!\u0003sB\u0011\"a#=\u0003\u0003%\t!!$\t\u0013\u0005UE(!A\u0005\u0002\r\u001d\u0001\"CARy\u0005\u0005I\u0011IAS\u0011%\t\u0019\fPA\u0001\n\u0003\u0019Y\u0001C\u0005\u0003\u0010r\n\t\u0011\"\u0011\u0004\u0010!I\u0011q\u0018\u001f\u0002\u0002\u0013\u0005\u0013\u0011\u0019\u0005\n\u0003\u0007d\u0014\u0011!C!\u0003\u000bD\u0011B!&=\u0003\u0003%\tea\u0005\b\u0013\r]\u0011!!A\t\u0002\rea!\u0003Bl\u0003\u0005\u0005\t\u0012AB\u000e\u0011\u001d\t)D\u0015C\u0001\u0007GA\u0011\"a1S\u0003\u0003%)%!2\t\u0013\t]&+!A\u0005\u0002\u000e\u0015\u0002\"\u0003Bb%\u0006\u0005I\u0011QB\u0017\u0011%\t9MUA\u0001\n\u0013\tIM\u0002\u0004\u0004:\u0005\u000111\b\u0005\u000b\u0007\u001fB&Q1A\u0005\u0002\rE\u0003BCB41\n\u0005\t\u0015!\u0003\u0004T!Q1\u0011\u000e-\u0003\u0006\u0004%\taa\u001b\t\u0015\r5\u0004L!A!\u0002\u0013\u0011)\u0001\u0003\u0006\u0004pa\u0013\t\u0011)A\u0005\u0007cB!ba\u001eY\u0005\u0003\u0005\u000b\u0011\u0002B\u001b\u0011)\u0019I\b\u0017B\u0001B\u0003%11\u0010\u0005\b\u0003kAF\u0011ABA\u0011\u001d\u0019y\t\u0017C\u0002\u0005\u0013Aqa!%Y\t\u0013\u0019\u0019\nC\u0004\u0004\u001cb#Ia!(\t\u0017\tm\u0007\f1AA\u0002\u0013%!\u0011\u0002\u0005\f\u0007KC\u0006\u0019!a\u0001\n\u0013\u00199\u000bC\u0006\u0003^b\u0003\r\u0011!Q!\n\t-\u0001\"CBV1\n\u0007I\u0011BAG\u0011!\u0019i\u000b\u0017Q\u0001\n\u0005=\u0005\"CBX1\u0002\u0007I\u0011BBY\u0011%\u0019\u0019\f\u0017a\u0001\n\u0013\u0019)\f\u0003\u0005\u0004:b\u0003\u000b\u0015BB.\u0011%\u0019Y\f\u0017a\u0001\n\u0013\ty\u0010C\u0005\u0004>b\u0003\r\u0011\"\u0003\u0004@\"A11\u0019-!B\u0013\t9\fC\u0005\u0004Fb\u0003\r\u0011\"\u0003\u0002\u0000\"I1q\u0019-A\u0002\u0013%1\u0011\u001a\u0005\t\u0007\u001bD\u0006\u0015)\u0003\u00028\"I1q\u001a-C\u0002\u0013%\u0011Q\u0012\u0005\t\u0007#D\u0006\u0015!\u0003\u0002\u0010\"911\u001b-\u0005\n\u0005}\b\"CBk1\u0002\u0007I\u0011BAG\u0011%\u00199\u000e\u0017a\u0001\n\u0013\u0019I\u000e\u0003\u0005\u0004^b\u0003\u000b\u0015BAH\u0011%\u0019y\u000e\u0017a\u0001\n\u0013\ti\tC\u0005\u0004bb\u0003\r\u0011\"\u0003\u0004d\"A1q\u001d-!B\u0013\ty\tC\u0004\u0004jb#\tea;\t\u000f\r5\b\f\"\u0003\u0004p\"9A\u0011\u0001-\u0005\n\r-\bb\u0002C\u00021\u0012%11\u001e\u0005\b\t\u000bAF\u0011\u0002C\u0004\u0011\u001d!Y\u0001\u0017C\u0005\u0007WDq\u0001\"\u0004Y\t\u0013\u0019Y\u000fC\u0005\u0005\u0010a\u0013\r\u0011\"\u0001\u0005\u0012!AA\u0011\u0004-!\u0002\u0013!\u0019\u0002C\u0004\u0005\"a#I\u0001b\t\t\u000f\u0011=\u0002\f\"\u0003\u00052!9AQ\u0007-\u0005B\r-\u0018A\u0005+da\u000e{gN\\3di&|gn\u0015;bO\u0016TA!a\u0005\u0002\u0016\u0005\u0011\u0011n\u001c\u0006\u0005\u0003/\tI\"\u0001\u0003j[Bd'\u0002BA\u000e\u0003;\taa\u001d;sK\u0006l'BAA\u0010\u0003\u0011\t7n[1\u0011\u0007\u0005\r\u0012!\u0004\u0002\u0002\u0012\t\u0011Bk\u00199D_:tWm\u0019;j_:\u001cF/Y4f'\r\t\u0011\u0011\u0006\t\u0005\u0003W\t\t$\u0004\u0002\u0002.)\u0011\u0011qF\u0001\u0006g\u000e\fG.Y\u0005\u0005\u0003g\tiC\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\u0011\u0011\u0011E\u0001\t/JLG/Z!dWB\u0019\u0011q\b\u0003\u000e\u0003\u0005\u0011\u0001b\u0016:ji\u0016\f5m[\n\n\t\u0005%\u0012QIA,\u0003;\u0002B!a\u0012\u0002R9!\u0011\u0011JA'\u001b\t\tYE\u0003\u0003\u0002\u0014\u0005u\u0011\u0002BA(\u0003\u0017\n1\u0001V2q\u0013\u0011\t\u0019&!\u0016\u0003\u000b\u00153XM\u001c;\u000b\t\u0005=\u00131\n\t\u0005\u0003W\tI&\u0003\u0003\u0002\\\u00055\"a\u0002)s_\u0012,8\r\u001e\t\u0005\u0003?\nyG\u0004\u0003\u0002b\u0005-d\u0002BA2\u0003Sj!!!\u001a\u000b\t\u0005\u001d\u0014qG\u0001\u0007yI|w\u000e\u001e \n\u0005\u0005=\u0012\u0002BA7\u0003[\tq\u0001]1dW\u0006<W-\u0003\u0003\u0002r\u0005M$\u0001D*fe&\fG.\u001b>bE2,'\u0002BA7\u0003[!\"!!\u0010\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\tY\b\u0005\u0003\u0002~\u0005\u001dUBAA@\u0015\u0011\t\t)a!\u0002\t1\fgn\u001a\u0006\u0003\u0003\u000b\u000bAA[1wC&!\u0011\u0011RA@\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u0011q\u0012\t\u0005\u0003W\t\t*\u0003\u0003\u0002\u0014\u00065\"aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$H\u0003BAM\u0003?\u0003B!a\u000b\u0002\u001c&!\u0011QTA\u0017\u0005\r\te.\u001f\u0005\n\u0003CC\u0011\u0011!a\u0001\u0003\u001f\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAAT!\u0019\tI+a,\u0002\u001a6\u0011\u00111\u0016\u0006\u0005\u0003[\u000bi#\u0001\u0006d_2dWm\u0019;j_:LA!!-\u0002,\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\t9,!0\u0011\t\u0005-\u0012\u0011X\u0005\u0005\u0003w\u000biCA\u0004C_>dW-\u00198\t\u0013\u0005\u0005&\"!AA\u0002\u0005e\u0015\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005=\u0015\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005m\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAAf!\u0011\ti(!4\n\t\u0005=\u0017q\u0010\u0002\u0007\u001f\nTWm\u0019;\u0002\u001b]\u0013\u0018\u000e^3EK2\f\u00170Q2l!\r\tyd\u0004\u0002\u000e/JLG/\u001a#fY\u0006L\u0018iY6\u0014\u0013=\tI#!\u0012\u0002X\u0005uCCAAj)\u0011\tI*!8\t\u0013\u0005\u00056#!AA\u0002\u0005=E\u0003BA\\\u0003CD\u0011\"!)\u0016\u0003\u0003\u0005\r!!'\u0002#]\u0013\u0018\u000e^3EK2\f\u00170T3tg\u0006<W-\u0006\u0002\u0002hB!\u0011\u0011^Az\u001d\u0011\tY/!\u0014\u000f\t\u00055\u0018\u0011\u001f\b\u0005\u0003G\ny/\u0003\u0002\u0002 %!\u00111CA\u000f\u0013\u0011\t)0!\u0016\u0003\u000b]\u0013\u0018\u000e^3\u0002%]\u0013\u0018\u000e^3EK2\f\u00170T3tg\u0006<W\r\t\u0002\b)\u000e\u0004(k\u001c7f'\rY\u0012\u0011F\u0001\nQ\u0006dgm\u00117pg\u0016,\"!a.\u0003\u0011=+HOY8v]\u0012\u001c\u0012\"HA\u0015\u0005\u000b\t9&!\u0018\u0011\u0007\u0005}2$A\u0004nC:\fw-\u001a:\u0016\u0005\t-\u0001\u0003\u0002B\u0007\u0005'i!Aa\u0004\u000b\t\tE\u0011QD\u0001\u0006C\u000e$xN]\u0005\u0005\u0005+\u0011yA\u0001\u0005BGR|'OU3g\u0003!i\u0017M\\1hKJ\u0004\u0013AC2p]:,7\r^\"nIV\u0011!Q\u0004\t\u0005\u0003S\u0014y\"\u0003\u0003\u0003\"\u0005U#aB\"p]:,7\r^\u0001\fG>tg.Z2u\u00076$\u0007%A\nm_\u000e\fG.\u00113ee\u0016\u001c8\u000f\u0015:p[&\u001cX-\u0006\u0002\u0003*A1!1\u0006B\u0019\u0005ki!A!\f\u000b\t\t=\u0012QF\u0001\u000bG>t7-\u001e:sK:$\u0018\u0002\u0002B\u001a\u0005[\u0011q\u0001\u0015:p[&\u001cX\r\u0005\u0003\u00038\tuRB\u0001B\u001d\u0015\u0011\u0011Y$a!\u0002\u00079,G/\u0003\u0003\u0003@\te\"!E%oKR\u001cvnY6fi\u0006#GM]3tg\u0006!Bn\\2bY\u0006#GM]3tgB\u0013x.\\5tK\u0002\n!\u0002[1mM\u000ecwn]3!))\u00119E!\u0013\u0003L\t5#q\n\t\u0004\u0003\u007fi\u0002b\u0002B\u0004M\u0001\u0007!1\u0002\u0005\b\u000531\u0003\u0019\u0001B\u000f\u0011\u001d\u0011)C\na\u0001\u0005SAq!!@'\u0001\u0004\t9,\u0001\u0003d_BLHC\u0003B$\u0005+\u00129F!\u0017\u0003\\!I!qA\u0014\u0011\u0002\u0003\u0007!1\u0002\u0005\n\u000539\u0003\u0013!a\u0001\u0005;A\u0011B!\n(!\u0003\u0005\rA!\u000b\t\u0013\u0005ux\u0005%AA\u0002\u0005]\u0016AD2paf$C-\u001a4bk2$H%M\u000b\u0003\u0005CRCAa\u0003\u0003d-\u0012!Q\r\t\u0005\u0005O\u0012\t(\u0004\u0002\u0003j)!!1\u000eB7\u0003%)hn\u00195fG.,GM\u0003\u0003\u0003p\u00055\u0012AC1o]>$\u0018\r^5p]&!!1\u000fB5\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\t\u0011IH\u000b\u0003\u0003\u001e\t\r\u0014AD2paf$C-\u001a4bk2$HeM\u000b\u0003\u0005\u007fRCA!\u000b\u0003d\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\"TC\u0001BCU\u0011\t9La\u0019\u0015\t\u0005e%\u0011\u0012\u0005\n\u0003Cs\u0013\u0011!a\u0001\u0003\u001f#B!a.\u0003\u000e\"I\u0011\u0011\u0015\u0019\u0002\u0002\u0003\u0007\u0011\u0011T\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002|\tM\u0005\"CAQc\u0005\u0005\t\u0019AAH\u0003\u0019)\u0017/^1mgR!\u0011q\u0017BM\u0011%\t\t\u000bNA\u0001\u0002\u0004\tI*\u0001\u0005PkR\u0014w.\u001e8e!\r\tyDN\n\u0006m\t\u0005&Q\u0016\t\u000f\u0005G\u0013IKa\u0003\u0003\u001e\t%\u0012q\u0017B$\u001b\t\u0011)K\u0003\u0003\u0003(\u00065\u0012a\u0002:v]RLW.Z\u0005\u0005\u0005W\u0013)KA\tBEN$(/Y2u\rVt7\r^5p]R\u0002BAa,\u000346\u0011!\u0011\u0017\u0006\u0005\u0003'\t\u0019)\u0003\u0003\u0002r\tEFC\u0001BO\u0003\u0015\t\u0007\u000f\u001d7z))\u00119Ea/\u0003>\n}&\u0011\u0019\u0005\b\u0005\u000fI\u0004\u0019\u0001B\u0006\u0011\u001d\u0011I\"\u000fa\u0001\u0005;AqA!\n:\u0001\u0004\u0011I\u0003C\u0004\u0002~f\u0002\r!a.\u0002\u000fUt\u0017\r\u001d9msR!!q\u0019Bj!\u0019\tYC!3\u0003N&!!1ZA\u0017\u0005\u0019y\u0005\u000f^5p]Ba\u00111\u0006Bh\u0005\u0017\u0011iB!\u000b\u00028&!!\u0011[A\u0017\u0005\u0019!V\u000f\u001d7fi!I!Q\u001b\u001e\u0002\u0002\u0003\u0007!qI\u0001\u0004q\u0012\u0002$aB%oE>,h\u000eZ\n\ny\u0005%\"QAA,\u0003;\n!bY8o]\u0016\u001cG/[8o\u0003-\u0019wN\u001c8fGRLwN\u001c\u0011\u0002!I,w-[:uKJ\u001c\u0015\r\u001c7cC\u000e\\WC\u0001Br!\u0019\tYC!:\u0003j&!!q]A\u0017\u0005%1UO\\2uS>t\u0007\u0007\u0005\u0003\u0002,\t-\u0018\u0002\u0002Bw\u0003[\u0011A!\u00168ji\u0006\t\"/Z4jgR,'oQ1mY\n\f7m\u001b\u0011\u0015\u0011\tM(Q\u001fB|\u0005s\u00042!a\u0010=\u0011\u001d\u0011Yn\u0011a\u0001\u0005\u0017Aq!!@D\u0001\u0004\t9\fC\u0004\u0003`\u000e\u0003\rAa9\u0015\u0011\tM(Q B\u0000\u0007\u0003A\u0011Ba7E!\u0003\u0005\rAa\u0003\t\u0013\u0005uH\t%AA\u0002\u0005]\u0006\"\u0003Bp\tB\u0005\t\u0019\u0001Br+\t\u0019)A\u000b\u0003\u0003d\n\rD\u0003BAM\u0007\u0013A\u0011\"!)K\u0003\u0003\u0005\r!a$\u0015\t\u0005]6Q\u0002\u0005\n\u0003Cc\u0015\u0011!a\u0001\u00033#B!a\u001f\u0004\u0012!I\u0011\u0011U'\u0002\u0002\u0003\u0007\u0011q\u0012\u000b\u0005\u0003o\u001b)\u0002C\u0005\u0002\"B\u000b\t\u00111\u0001\u0002\u001a\u00069\u0011J\u001c2pk:$\u0007cAA %N)!k!\b\u0003.Ba!1UB\u0010\u0005\u0017\t9La9\u0003t&!1\u0011\u0005BS\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|gn\r\u000b\u0003\u00073!\u0002Ba=\u0004(\r%21\u0006\u0005\b\u00057,\u0006\u0019\u0001B\u0006\u0011\u001d\ti0\u0016a\u0001\u0003oCqAa8V\u0001\u0004\u0011\u0019\u000f\u0006\u0003\u00040\r]\u0002CBA\u0016\u0005\u0013\u001c\t\u0004\u0005\u0006\u0002,\rM\"1BA\\\u0005GLAa!\u000e\u0002.\t1A+\u001e9mKNB\u0011B!6W\u0003\u0003\u0005\rAa=\u0003\u001dQ\u001b\u0007o\u0015;sK\u0006lGj\\4jGN)\u0001l!\u0010\u0004JA!1qHB#\u001b\t\u0019\tE\u0003\u0003\u0004D\u0005e\u0011!B:uC\u001e,\u0017\u0002BB$\u0007\u0003\u0012qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\t\u0005\u0007\u007f\u0019Y%\u0003\u0003\u0004N\r\u0005#\u0001D*uC\u001e,Gj\\4hS:<\u0017!B:iCB,WCAB*!!\u0019)fa\u0016\u0004\\\rmSBAA\r\u0013\u0011\u0019I&!\u0007\u0003\u0013\u0019cwn^*iCB,\u0007\u0003BB/\u0007Gj!aa\u0018\u000b\t\r\u0005\u0014QD\u0001\u0005kRLG.\u0003\u0003\u0004f\r}#A\u0003\"zi\u0016\u001cFO]5oO\u000611\u000f[1qK\u0002\nAA]8mKV\u0011!QA\u0001\u0006e>dW\rI\u0001\u0014S:DWM]5uK\u0012\fE\u000f\u001e:jEV$Xm\u001d\t\u0005\u0007+\u001a\u0019(\u0003\u0003\u0004v\u0005e!AC!uiJL'-\u001e;fg\u0006i!/Z7pi\u0016\fE\r\u001a:fgN\f\u0011#Z1hKJl\u0015\r^3sS\u0006d\u0017N_3s!\u0011\u0019)f! \n\t\r}\u0014\u0011\u0004\u0002\r\u001b\u0006$XM]5bY&TXM\u001d\u000b\r\u0007\u0007\u001b)ia\"\u0004\n\u000e-5Q\u0012\t\u0004\u0003\u007fA\u0006bBB(A\u0002\u000711\u000b\u0005\b\u0007S\u0002\u0007\u0019\u0001B\u0003\u0011\u001d\u0019y\u0007\u0019a\u0001\u0007cBqaa\u001ea\u0001\u0004\u0011)\u0004C\u0004\u0004z\u0001\u0004\raa\u001f\u0002\tM,GNZ\u0001\bEf$Xm]%o+\t\u0019)\n\u0005\u0004\u0004V\r]51L\u0005\u0005\u00073\u000bIBA\u0003J]2,G/\u0001\u0005csR,7oT;u+\t\u0019y\n\u0005\u0004\u0004V\r\u000561L\u0005\u0005\u0007G\u000bIB\u0001\u0004PkRdW\r^\u0001\u000fG>tg.Z2uS>tw\fJ3r)\u0011\u0011Io!+\t\u0013\u0005\u0005V-!AA\u0002\t-\u0011aD<sSR,')\u001e4gKJ\u001c\u0016N_3\u0002!]\u0014\u0018\u000e^3Ck\u001a4WM]*ju\u0016\u0004\u0013aC<sSR,')\u001e4gKJ,\"aa\u0017\u0002\u001f]\u0014\u0018\u000e^3Ck\u001a4WM]0%KF$BA!;\u00048\"I\u0011\u0011\u00156\u0002\u0002\u0003\u000711L\u0001\roJLG/\u001a\"vM\u001a,'\u000fI\u0001\u0010oJLG/Z%o!J|wM]3tg\u0006\u0019rO]5uK&s\u0007K]8he\u0016\u001c8o\u0018\u0013fcR!!\u0011^Ba\u0011%\t\t+\\A\u0001\u0002\u0004\t9,\u0001\txe&$X-\u00138Qe><'/Z:tA\u000512m\u001c8oK\u000e$\u0018n\u001c8DY>\u001cX\rU3oI&tw-\u0001\u000ed_:tWm\u0019;j_:\u001cEn\\:f!\u0016tG-\u001b8h?\u0012*\u0017\u000f\u0006\u0003\u0003j\u000e-\u0007\"CAQa\u0006\u0005\t\u0019AA\\\u0003]\u0019wN\u001c8fGRLwN\\\"m_N,\u0007+\u001a8eS:<\u0007%\u0001\bd_\u0006dWm]2f/JLG/Z:\u0002\u001f\r|\u0017\r\\3tG\u0016<&/\u001b;fg\u0002\nacY8bY\u0016\u001c8-Z,sSR,7\u000fR5tC\ndW\rZ\u0001\u0014oJLG/\u001a#fY\u0006L8i\\;oi\u0012{wO\\\u0001\u0018oJLG/\u001a#fY\u0006L8i\\;oi\u0012{wO\\0%KF$BA!;\u0004\\\"I\u0011\u0011\u0015<\u0002\u0002\u0003\u0007\u0011qR\u0001\u0015oJLG/\u001a#fY\u0006L8i\\;oi\u0012{wO\u001c\u0011\u0002/A\u0014XM^5pkN<&/\u001b;f\u0005V4g-\u001a:TSj,\u0017a\u00079sKZLw.^:Xe&$XMQ;gM\u0016\u00148+\u001b>f?\u0012*\u0017\u000f\u0006\u0003\u0003j\u000e\u0015\b\"CAQs\u0006\u0005\t\u0019AAH\u0003a\u0001(/\u001a<j_V\u001cxK]5uK\n+hMZ3s'&TX\rI\u0001\taJ,7\u000b^1siR\u0011!\u0011^\u0001\u000bG>tg.Z2uS:<G\u0003BBy\u0007{$BA!;\u0004t\"91Q\u001f?A\u0002\r]\u0018aA3wiBA\u00111FB}\u0005\u0017\tI*\u0003\u0003\u0004|\u00065\"A\u0002+va2,'\u0007C\u0004\u0004\u0000r\u0004\rAa\u0012\u0002\u0005=\u0014\u0017aD:f]\u0012<&/\u001b;f\u0005V4g-\u001a:\u0002\u001dM,g\u000eZ,sSR,G)\u001a7bs\u0006I1m\u001c8oK\u000e$X\r\u001a\u000b\u0005\u0005S$I\u0001C\u0004\u0004v~\u0004\raa>\u0002?\rdwn]3D_:tWm\u0019;j_:,\u0006o\u001d;sK\u0006lg)\u001b8jg\",G-A\u0011dY>\u001cXmQ8o]\u0016\u001cG/[8o\t><hn\u001d;sK\u0006lg)\u001b8jg\",G-A\u0006sK\u0006$\u0007*\u00198eY\u0016\u0014XC\u0001C\n%\u0019!)\"!\u000b\u0005\u001c\u00199AqCA\u0004\u0001\u0011M!\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0014\u0001\u0004:fC\u0012D\u0015M\u001c3mKJ\u0004\u0003\u0003BB \t;IA\u0001b\b\u0004B\tQq*\u001e;IC:$G.\u001a:\u0002\t\u0019\f\u0017\u000e\u001c\u000b\u0005\u0005S$)\u0003\u0003\u0005\u0005(\u0005%\u0001\u0019\u0001C\u0015\u0003\t)\u0007\u0010\u0005\u0003\u0002`\u0011-\u0012\u0002\u0002C\u0017\u0003g\u0012\u0011\u0002\u00165s_^\f'\r\\3\u00021I,\u0007o\u001c:u\u000bb\u001cW\r\u001d;j_:$v\u000e\u0015:p[&\u001cX\r\u0006\u0003\u0003j\u0012M\u0002\u0002\u0003C\u0014\u0003\u0017\u0001\r\u0001\"\u000b\u0002\u0011A|7\u000f^*u_BD3!\u0001C\u001d!\u0011!Y\u0004b\u0010\u000e\u0005\u0011u\"\u0002\u0002B8\u0003;IA\u0001\"\u0011\u0005>\tY\u0011J\u001c;fe:\fG.\u00119jQ\r\u0001A\u0011\b")
@InternalApi
public final class TcpConnectionStage {

    public static class Inbound
    implements TcpRole,
    Product,
    Serializable {
        private final ActorRef connection;
        private final boolean halfClose;
        private final Function0<BoxedUnit> registerCallback;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ActorRef connection() {
            return this.connection;
        }

        @Override
        public boolean halfClose() {
            return this.halfClose;
        }

        public Function0<BoxedUnit> registerCallback() {
            return this.registerCallback;
        }

        public Inbound copy(ActorRef connection, boolean halfClose, Function0<BoxedUnit> registerCallback) {
            return new Inbound(connection, halfClose, registerCallback);
        }

        public ActorRef copy$default$1() {
            return this.connection();
        }

        public boolean copy$default$2() {
            return this.halfClose();
        }

        public Function0<BoxedUnit> copy$default$3() {
            return this.registerCallback();
        }

        @Override
        public String productPrefix() {
            return "Inbound";
        }

        @Override
        public int productArity() {
            return 3;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.connection();
                }
                case 1: {
                    return BoxesRunTime.boxToBoolean(this.halfClose());
                }
                case 2: {
                    return this.registerCallback();
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
            return x$1 instanceof Inbound;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "connection";
                }
                case 1: {
                    return "halfClose";
                }
                case 2: {
                    return "registerCallback";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.anyHash(this.connection()));
            n = Statics.mix(n, this.halfClose() ? 1231 : 1237);
            n = Statics.mix(n, Statics.anyHash(this.registerCallback()));
            return Statics.finalizeHash(n, 3);
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
            if (!(object instanceof Inbound)) return false;
            boolean bl = true;
            if (!bl) return false;
            Inbound inbound = (Inbound)x$1;
            if (this.halfClose() != inbound.halfClose()) return false;
            ActorRef actorRef2 = this.connection();
            ActorRef actorRef3 = inbound.connection();
            if (actorRef2 == null) {
                if (actorRef3 != null) {
                    return false;
                }
            } else if (!((Object)actorRef2).equals(actorRef3)) return false;
            Function0<BoxedUnit> function0 = this.registerCallback();
            Function0<BoxedUnit> function02 = inbound.registerCallback();
            if (function0 == null) {
                if (function02 != null) {
                    return false;
                }
            } else if (!function0.equals(function02)) return false;
            if (!inbound.canEqual(this)) return false;
            return true;
        }

        public Inbound(ActorRef connection, boolean halfClose, Function0<BoxedUnit> registerCallback) {
            this.connection = connection;
            this.halfClose = halfClose;
            this.registerCallback = registerCallback;
            Product.$init$(this);
        }
    }

    public static class Outbound
    implements TcpRole,
    Product,
    Serializable {
        private final ActorRef manager;
        private final Tcp.Connect connectCmd;
        private final Promise<InetSocketAddress> localAddressPromise;
        private final boolean halfClose;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ActorRef manager() {
            return this.manager;
        }

        public Tcp.Connect connectCmd() {
            return this.connectCmd;
        }

        public Promise<InetSocketAddress> localAddressPromise() {
            return this.localAddressPromise;
        }

        @Override
        public boolean halfClose() {
            return this.halfClose;
        }

        public Outbound copy(ActorRef manager, Tcp.Connect connectCmd, Promise<InetSocketAddress> localAddressPromise, boolean halfClose) {
            return new Outbound(manager, connectCmd, localAddressPromise, halfClose);
        }

        public ActorRef copy$default$1() {
            return this.manager();
        }

        public Tcp.Connect copy$default$2() {
            return this.connectCmd();
        }

        public Promise<InetSocketAddress> copy$default$3() {
            return this.localAddressPromise();
        }

        public boolean copy$default$4() {
            return this.halfClose();
        }

        @Override
        public String productPrefix() {
            return "Outbound";
        }

        @Override
        public int productArity() {
            return 4;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.manager();
                }
                case 1: {
                    return this.connectCmd();
                }
                case 2: {
                    return this.localAddressPromise();
                }
                case 3: {
                    return BoxesRunTime.boxToBoolean(this.halfClose());
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
            return x$1 instanceof Outbound;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "manager";
                }
                case 1: {
                    return "connectCmd";
                }
                case 2: {
                    return "localAddressPromise";
                }
                case 3: {
                    return "halfClose";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.anyHash(this.manager()));
            n = Statics.mix(n, Statics.anyHash(this.connectCmd()));
            n = Statics.mix(n, Statics.anyHash(this.localAddressPromise()));
            n = Statics.mix(n, this.halfClose() ? 1231 : 1237);
            return Statics.finalizeHash(n, 4);
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
            if (!(object instanceof Outbound)) return false;
            boolean bl = true;
            if (!bl) return false;
            Outbound outbound = (Outbound)x$1;
            if (this.halfClose() != outbound.halfClose()) return false;
            ActorRef actorRef2 = this.manager();
            ActorRef actorRef3 = outbound.manager();
            if (actorRef2 == null) {
                if (actorRef3 != null) {
                    return false;
                }
            } else if (!((Object)actorRef2).equals(actorRef3)) return false;
            Tcp.Connect connect2 = this.connectCmd();
            Tcp.Connect connect3 = outbound.connectCmd();
            if (connect2 == null) {
                if (connect3 != null) {
                    return false;
                }
            } else if (!((Object)connect2).equals(connect3)) return false;
            Promise<InetSocketAddress> promise = this.localAddressPromise();
            Promise<InetSocketAddress> promise2 = outbound.localAddressPromise();
            if (promise == null) {
                if (promise2 != null) {
                    return false;
                }
            } else if (!promise.equals(promise2)) return false;
            if (!outbound.canEqual(this)) return false;
            return true;
        }

        public Outbound(ActorRef manager, Tcp.Connect connectCmd, Promise<InetSocketAddress> localAddressPromise, boolean halfClose) {
            this.manager = manager;
            this.connectCmd = connectCmd;
            this.localAddressPromise = localAddressPromise;
            this.halfClose = halfClose;
            Product.$init$(this);
        }
    }

    public static interface TcpRole {
        public boolean halfClose();
    }

    public static class TcpStreamLogic
    extends GraphStageLogic
    implements StageLogging {
        private final FlowShape<ByteString, ByteString> shape;
        private final TcpRole role;
        public final InetSocketAddress akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$remoteAddress;
        private ActorRef akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection;
        private final int akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBufferSize;
        private ByteString akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer;
        private boolean akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeInProgress;
        private boolean connectionClosePending;
        private final int akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$coalesceWrites;
        private int akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeDelayCountDown;
        private int previousWriteBufferSize;
        private final OutHandler readHandler;
        private LoggingAdapter akka$stream$stage$StageLogging$$_log;

        @Override
        public Class<?> logSource() {
            return StageLogging.logSource$(this);
        }

        @Override
        public LoggingAdapter log() {
            return StageLogging.log$(this);
        }

        @Override
        public LoggingAdapter akka$stream$stage$StageLogging$$_log() {
            return this.akka$stream$stage$StageLogging$$_log;
        }

        @Override
        public void akka$stream$stage$StageLogging$$_log_$eq(LoggingAdapter x$1) {
            this.akka$stream$stage$StageLogging$$_log = x$1;
        }

        public FlowShape<ByteString, ByteString> shape() {
            return this.shape;
        }

        public TcpRole role() {
            return this.role;
        }

        public ActorRef self() {
            return this.stageActor().ref();
        }

        public Inlet<ByteString> akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$bytesIn() {
            return this.shape().in();
        }

        private Outlet<ByteString> bytesOut() {
            return this.shape().out();
        }

        public ActorRef akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection() {
            return this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection;
        }

        private void connection_$eq(ActorRef x$1) {
            this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection = x$1;
        }

        public int akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBufferSize() {
            return this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBufferSize;
        }

        public ByteString akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer() {
            return this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer;
        }

        public void akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer_$eq(ByteString x$1) {
            this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer = x$1;
        }

        public boolean akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeInProgress() {
            return this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeInProgress;
        }

        private void writeInProgress_$eq(boolean x$1) {
            this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeInProgress = x$1;
        }

        private boolean connectionClosePending() {
            return this.connectionClosePending;
        }

        private void connectionClosePending_$eq(boolean x$1) {
            this.connectionClosePending = x$1;
        }

        public int akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$coalesceWrites() {
            return this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$coalesceWrites;
        }

        public boolean akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$coalesceWritesDisabled() {
            return this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$coalesceWrites() == 0;
        }

        private int writeDelayCountDown() {
            return this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeDelayCountDown;
        }

        public void akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeDelayCountDown_$eq(int x$1) {
            this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeDelayCountDown = x$1;
        }

        private int previousWriteBufferSize() {
            return this.previousWriteBufferSize;
        }

        private void previousWriteBufferSize_$eq(int x$1) {
            this.previousWriteBufferSize = x$1;
        }

        @Override
        public void preStart() {
            this.setKeepGoing(true);
            TcpRole tcpRole = this.role();
            if (tcpRole instanceof Inbound) {
                Inbound inbound = (Inbound)tcpRole;
                ActorRef conn = inbound.connection();
                Function0<BoxedUnit> registerCallback = inbound.registerCallback();
                this.setHandler(this.bytesOut(), this.readHandler());
                this.connection_$eq(conn);
                this.getStageActor((Function1<Tuple2<ActorRef, Object>, BoxedUnit>)(Function1<Tuple2, Object> & Serializable)evt -> {
                    this.connected(evt);
                    return BoxedUnit.UNIT;
                }).watch(this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection());
                this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection().$bang(new Tcp.Register(this.self(), true, false), this.self());
                registerCallback.apply$mcV$sp();
                this.pull(this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$bytesIn());
                return;
            }
            if (tcpRole instanceof Outbound) {
                Outbound outbound = (Outbound)tcpRole;
                ActorRef manager = outbound.manager();
                Tcp.Connect cmd = outbound.connectCmd();
                this.getStageActor((Function1<Tuple2<ActorRef, Object>, BoxedUnit>)(Function1<Tuple2, Object> & Serializable)evt -> {
                    this.connecting(outbound, evt);
                    return BoxedUnit.UNIT;
                }).watch(manager);
                manager.$bang(cmd, this.self());
                return;
            }
            throw new IllegalArgumentException(new StringBuilder(22).append("Unsupported TCP role: ").append(tcpRole).toString());
        }

        private void connecting(Outbound ob, Tuple2<ActorRef, Object> evt2) {
            ActorRef sender = evt2._1();
            Object msg = evt2._2();
            Object object = msg;
            if (object instanceof Terminated) {
                this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$fail(new StreamTcpException("The IO manager actor (TCP) has terminated. Stopping now."));
                return;
            }
            if (object instanceof Tcp.CommandFailed) {
                Tcp.CommandFailed commandFailed = (Tcp.CommandFailed)object;
                Tcp.Command cmd = commandFailed.cmd();
                this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$fail(new StreamTcpException(new StringBuilder(21).append("Tcp command [").append(cmd).append("] failed").append(commandFailed.causedByString()).toString()).initCause((Throwable)commandFailed.cause().orNull($less$colon$less$.MODULE$.refl())));
                return;
            }
            if (object instanceof Tcp.Connected) {
                Tcp.Connected connected2 = (Tcp.Connected)object;
                ((Outbound)this.role()).localAddressPromise().success(connected2.localAddress());
                this.connection_$eq(sender);
                this.setHandler(this.bytesOut(), this.readHandler());
                this.stageActor().unwatch(ob.manager());
                this.stageActor().become((Function1<Tuple2<ActorRef, Object>, BoxedUnit>)(Function1<Tuple2, Object> & Serializable)evt -> {
                    this.connected(evt);
                    return BoxedUnit.UNIT;
                });
                this.stageActor().watch(this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection());
                this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection().$bang(new Tcp.Register(this.self(), true, false), this.self());
                if (this.isAvailable(this.bytesOut())) {
                    this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection().$bang(Tcp$ResumeReading$.MODULE$, this.self());
                }
                if (this.isClosed(this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$bytesIn())) {
                    this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection().$bang(Tcp$ConfirmedClose$.MODULE$, this.self());
                    return;
                }
                this.pull(this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$bytesIn());
                return;
            }
            this.log().warning("Unexpected message to connecting TcpStage: [{}]", object.getClass());
        }

        public void akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$sendWriteBuffer() {
            this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection().$bang(new Tcp.Write(this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer(), TcpConnectionStage$WriteAck$.MODULE$), this.self());
            this.writeInProgress_$eq(true);
            this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer_$eq(ByteString$.MODULE$.empty());
        }

        public void akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$sendWriteDelay() {
            this.previousWriteBufferSize_$eq(this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer().length());
            this.writeInProgress_$eq(true);
            this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection().$bang(TcpConnectionStage$.MODULE$.akka$stream$impl$io$TcpConnectionStage$$WriteDelayMessage(), this.self());
        }

        private void connected(Tuple2<ActorRef, Object> evt) {
            Object msg = evt._2();
            Object object = msg;
            if (object instanceof Tcp.Received) {
                Tcp.Received received = (Tcp.Received)object;
                ByteString data = received.data();
                if (this.isClosed(this.bytesOut())) {
                    this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection().$bang(Tcp$ResumeReading$.MODULE$, this.self());
                    return;
                }
                this.push(this.bytesOut(), data);
                return;
            }
            if (TcpConnectionStage$WriteDelayAck$.MODULE$.equals(object)) {
                this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeDelayCountDown_$eq(this.writeDelayCountDown() - 1);
                if (this.writeDelayCountDown() == 0 || this.previousWriteBufferSize() == this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer().length() || this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer().length() >= this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBufferSize()) {
                    this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$sendWriteBuffer();
                    return;
                }
                this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$sendWriteDelay();
                return;
            }
            if (TcpConnectionStage$WriteAck$.MODULE$.equals(object)) {
                if (this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer().isEmpty()) {
                    this.writeInProgress_$eq(false);
                } else if (this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$coalesceWritesDisabled() || this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer().length() >= this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBufferSize()) {
                    this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$sendWriteBuffer();
                } else {
                    this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeDelayCountDown_$eq(this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$coalesceWrites());
                    this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$sendWriteDelay();
                }
                if (!this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeInProgress() && this.connectionClosePending()) {
                    this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$closeConnectionUpstreamFinished();
                }
                if (!this.isClosed(this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$bytesIn()) && !this.hasBeenPulled(this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$bytesIn())) {
                    this.pull(this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$bytesIn());
                    return;
                }
                return;
            }
            if (object instanceof Terminated) {
                this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$fail(new StreamTcpException("The connection actor has terminated. Stopping now."));
                return;
            }
            if (object instanceof Tcp.CommandFailed) {
                Tcp.CommandFailed commandFailed = (Tcp.CommandFailed)object;
                Tcp.Command cmd = commandFailed.cmd();
                this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$fail(new StreamTcpException(new StringBuilder(21).append("Tcp command [").append(cmd).append("] failed").append(commandFailed.causedByString()).toString()).initCause((Throwable)commandFailed.cause().orNull($less$colon$less$.MODULE$.refl())));
                return;
            }
            if (object instanceof Tcp.ErrorClosed) {
                Tcp.ErrorClosed errorClosed = (Tcp.ErrorClosed)object;
                String cause = errorClosed.cause();
                this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$fail(new StreamTcpException(new StringBuilder(34).append("The connection closed with error: ").append(cause).toString()));
                return;
            }
            if (Tcp$Aborted$.MODULE$.equals(object)) {
                this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$fail(new StreamTcpException("The connection has been aborted"));
                return;
            }
            if (Tcp$Closed$.MODULE$.equals(object)) {
                this.completeStage();
                return;
            }
            if (Tcp$ConfirmedClosed$.MODULE$.equals(object)) {
                this.completeStage();
                return;
            }
            if (Tcp$PeerClosed$.MODULE$.equals(object)) {
                this.complete(this.bytesOut());
                return;
            }
            this.log().warning("Unexpected message to connected TcpStage: [{}]", object.getClass());
        }

        public void akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$closeConnectionUpstreamFinished() {
            if (this.isClosed(this.bytesOut()) || !this.role().halfClose()) {
                if (this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeInProgress()) {
                    this.connectionClosePending_$eq(true);
                    return;
                }
                this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection().$bang(Tcp$Close$.MODULE$, this.self());
                return;
            }
            if (this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection() != null) {
                if (this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeInProgress()) {
                    this.connectionClosePending_$eq(true);
                    return;
                }
                this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection().$bang(Tcp$ConfirmedClose$.MODULE$, this.self());
                return;
            }
        }

        public void akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$closeConnectionDownstreamFinished() {
            if (this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection() == null) {
                return;
            }
            if (this.role().halfClose()) {
                if (this.isClosed(this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$bytesIn()) && !this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeInProgress()) {
                    this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection().$bang(Tcp$Close$.MODULE$, this.self());
                    return;
                }
                this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection().$bang(Tcp$ResumeReading$.MODULE$, this.self());
                return;
            }
            if (!this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeInProgress()) {
                this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection().$bang(Tcp$Close$.MODULE$, this.self());
                return;
            }
        }

        public OutHandler readHandler() {
            return this.readHandler;
        }

        public void akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$fail(Throwable ex) {
            this.reportExceptionToPromise(ex);
            this.failStage(ex);
        }

        private void reportExceptionToPromise(Throwable ex) {
            TcpRole tcpRole = this.role();
            if (tcpRole instanceof Outbound) {
                Outbound outbound = (Outbound)tcpRole;
                Promise<InetSocketAddress> localAddressPromise = outbound.localAddressPromise();
                localAddressPromise.tryFailure(ex);
                return;
            }
        }

        @Override
        public void postStop() {
            this.reportExceptionToPromise(new StreamTcpException("Connection failed."));
        }

        public TcpStreamLogic(FlowShape<ByteString, ByteString> shape, TcpRole role, Attributes inheritedAttributes, InetSocketAddress remoteAddress, Materializer eagerMaterializer) {
            this.shape = shape;
            this.role = role;
            this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$remoteAddress = remoteAddress;
            super(shape);
            StageLogging.$init$(this);
            this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBufferSize = inheritedAttributes.get(new TcpAttributes.TcpWriteBufferSize(eagerMaterializer.settings().ioSettings().tcpWriteBufferSize()), ClassTag$.MODULE$.apply(TcpAttributes.TcpWriteBufferSize.class)).size();
            this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer = ByteString$.MODULE$.empty();
            this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeInProgress = false;
            this.connectionClosePending = false;
            this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$coalesceWrites = eagerMaterializer.settings().ioSettings().coalesceWrites();
            this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeDelayCountDown = 0;
            this.previousWriteBufferSize = 0;
            this.setHandler(this.bytesOut(), GraphStageLogic$EagerTerminateOutput$.MODULE$);
            this.readHandler = new OutHandler(this){
                private final /* synthetic */ TcpStreamLogic $outer;

                public void onDownstreamFinish() throws Exception {
                    OutHandler.onDownstreamFinish$(this);
                }

                public void onPull() {
                    this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection().$bang(Tcp$ResumeReading$.MODULE$, this.$outer.self());
                }

                public void onDownstreamFinish(Throwable cause) {
                    Throwable throwable = cause;
                    if (throwable instanceof SubscriptionWithCancelException.NonFailureCancellation) {
                        this.$outer.log().debug("Closing connection from {}:{} because downstream cancelled stream without failure", this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$remoteAddress.getHostString(), BoxesRunTime.boxToInteger(this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$remoteAddress.getPort()));
                        this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$closeConnectionDownstreamFinished();
                        return;
                    }
                    this.$outer.log().debug("Aborting connection from {}:{} because of downstream failure: {}", this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$remoteAddress.getHostString(), BoxesRunTime.boxToInteger(this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$remoteAddress.getPort()), throwable);
                    this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection().$bang(Tcp$Abort$.MODULE$, this.$outer.self());
                    this.$outer.failStage(cause);
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    OutHandler.$init$(this);
                }
            };
            this.setHandler(this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$bytesIn(), new InHandler(this){
                private final /* synthetic */ TcpStreamLogic $outer;

                public void onPush() {
                    ByteString elem = this.$outer.grab(this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$bytesIn());
                    ReactiveStreamsCompliance$.MODULE$.requireNonNullElement(elem);
                    if (this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeInProgress()) {
                        this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer_$eq(this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer().$plus$plus(elem));
                    } else if (this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$coalesceWritesDisabled() || this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer().length() >= this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBufferSize()) {
                        this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer_$eq(this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer().$plus$plus(elem));
                        this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$sendWriteBuffer();
                    } else {
                        this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer_$eq(this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer().$plus$plus(elem));
                        this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeDelayCountDown_$eq(this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$coalesceWrites());
                        this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$sendWriteDelay();
                    }
                    if (this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer().length() < this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBufferSize()) {
                        this.$outer.pull(this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$bytesIn());
                        return;
                    }
                }

                public void onUpstreamFinish() {
                    this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$closeConnectionUpstreamFinished();
                }

                public void onUpstreamFailure(Throwable ex) {
                    if (this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection() != null) {
                        if (this.$outer.interpreter().log().isDebugEnabled()) {
                            String msg = "Aborting tcp connection to {} because of upstream failure: {}";
                            if (ArrayOps$.MODULE$.isEmpty$extension(Predef$.MODULE$.refArrayOps(ex.getStackTrace()))) {
                                this.$outer.interpreter().log().debug(msg, this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$remoteAddress, ex);
                            } else {
                                this.$outer.interpreter().log().debug(new StringBuilder(3).append(msg).append("\n{}").toString(), this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$remoteAddress, ex, Predef$.MODULE$.wrapRefArray((Object[])ex.getStackTrace()).mkString("\n"));
                            }
                        }
                        this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$connection().$bang(Tcp$Abort$.MODULE$, this.$outer.self());
                        return;
                    }
                    this.$outer.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$fail(ex);
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    InHandler.$init$(this);
                }
            });
            this.akka$stream$impl$io$TcpConnectionStage$TcpStreamLogic$$writeBuffer_$eq(ByteString$.MODULE$.empty());
        }
    }
}

