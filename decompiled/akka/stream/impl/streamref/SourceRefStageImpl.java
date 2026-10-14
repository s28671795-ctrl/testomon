/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.streamref;

import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.annotation.InternalApi;
import akka.event.Logging$;
import akka.event.LoggingAdapter;
import akka.stream.Attributes;
import akka.stream.InvalidPartnerActorException;
import akka.stream.InvalidSequenceNumberException;
import akka.stream.Materializer;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.RemoteStreamRefActorTerminatedException;
import akka.stream.SinkRef;
import akka.stream.SourceShape;
import akka.stream.SourceShape$;
import akka.stream.StreamRefAttributes;
import akka.stream.StreamRefSettings;
import akka.stream.StreamRefSubscriptionTimeoutException;
import akka.stream.SubscriptionWithCancelException;
import akka.stream.SubscriptionWithCancelException$NoMoreElementsNeeded$;
import akka.stream.impl.FixedSizeBuffer;
import akka.stream.impl.FixedSizeBuffer$;
import akka.stream.impl.streamref.SinkRefImpl;
import akka.stream.impl.streamref.SourceRefStageImpl$;
import akka.stream.impl.streamref.SourceRefStageImpl$AwaitingPartner$;
import akka.stream.impl.streamref.SourceRefStageImpl$WatermarkRequestStrategy$;
import akka.stream.impl.streamref.StreamRefsMaster;
import akka.stream.impl.streamref.StreamRefsMaster$;
import akka.stream.impl.streamref.StreamRefsProtocol;
import akka.stream.impl.streamref.StreamRefsProtocol$Ack$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.OutHandler;
import akka.stream.stage.StageLogging;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import akka.util.PrettyDuration$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.Predef$;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.concurrent.duration.FiniteDuration;
import scala.math.package$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0011muACA3\u0003OB\t!a\u001c\u0002x\u0019Q\u00111PA4\u0011\u0003\ty'! \t\u000f\u0005-\u0015\u0001\"\u0001\u0002\u0010\u001aI\u0011\u0011S\u0001\u0011\u0002G%\u00121\u0013\u0005\b\u0003+\u001ba\u0011AAL\u000f\u001d\t\t-\u0001E\u0001\u0003\u00074q!!2\u0002\u0011\u0003\t9\rC\u0004\u0002\f\u001a!\t!!6\t\u000f\u0005]g\u0001\"\u0001\u0002Z\"I\u0011q\u001b\u0004\u0002\u0002\u0013\u0005%1\u0012\u0005\n\u0005#3\u0011\u0011!CA\u0005'C\u0011B!*\u0007\u0003\u0003%IAa*\u0007\r\u0005\u0015\u0017AQAo\u0011)\tY\u0010\u0004BK\u0002\u0013\u0005\u0011Q \u0005\u000b\u0005\u000ba!\u0011#Q\u0001\n\u0005}\bB\u0003B\u0004\u0019\tU\r\u0011\"\u0001\u0002~\"Q!\u0011\u0002\u0007\u0003\u0012\u0003\u0006I!a@\t\u000f\u0005-E\u0002\"\u0001\u0003\f!9\u00111\u0012\u0007\u0005\u0002\tE\u0001b\u0002B\u000b\u0019\u0011\u0005!q\u0003\u0005\n\u0005;a\u0011\u0011!C\u0001\u0005?A\u0011B!\n\r#\u0003%\tAa\n\t\u0013\tuB\"%A\u0005\u0002\t\u001d\u0002\"\u0003B \u0019\u0005\u0005I\u0011\tB!\u0011%\u0011I\u0005DA\u0001\n\u0003\ti\u0010C\u0005\u0003L1\t\t\u0011\"\u0001\u0003N!I!\u0011\f\u0007\u0002\u0002\u0013\u0005#1\f\u0005\n\u0005Sb\u0011\u0011!C\u0001\u0005WB\u0011B!\u001e\r\u0003\u0003%\tEa\u001e\t\u0013\tmD\"!A\u0005B\tu\u0004\"\u0003B@\u0019\u0005\u0005I\u0011\tBA\u0011%\u0011\u0019\tDA\u0001\n\u0003\u0012)IB\u0005\u0003*\u0006\u0001\n1%\u000b\u0003,\u001aI!q]\u0001\u0011\u0002G%\"\u0011\u001e\u0005\b\u0005\u000b\fc\u0011AAL\u000f\u001d\u0019Y(\u0001EE\u0005o3qAa,\u0002\u0011\u0013\u0013\t\fC\u0004\u0002\f\u0012\"\tA!.\t\u0013\t}B%!A\u0005B\t\u0005\u0003\"\u0003B%I\u0005\u0005I\u0011AA\u007f\u0011%\u0011Y\u0005JA\u0001\n\u0003\u0011I\fC\u0005\u0003Z\u0011\n\t\u0011\"\u0011\u0003\\!I!\u0011\u000e\u0013\u0002\u0002\u0013\u0005!Q\u0018\u0005\n\u0005w\"\u0013\u0011!C!\u0005{B\u0011Ba %\u0003\u0003%\tE!!\t\u0013\t\u0015F%!A\u0005\n\t\u001dfA\u0002Bw\u0003\u0011\u0013y\u000f\u0003\u0006\u0003F:\u0012)\u001a!C\u0001\u0003/C!Ba2/\u0005#\u0005\u000b\u0011BAM\u0011\u001d\tYI\fC\u0001\u0005gD\u0011B!\b/\u0003\u0003%\tA!?\t\u0013\t\u0015b&%A\u0005\u0002\tM\u0007\"\u0003B ]\u0005\u0005I\u0011\tB!\u0011%\u0011IELA\u0001\n\u0003\ti\u0010C\u0005\u0003L9\n\t\u0011\"\u0001\u0003~\"I!\u0011\f\u0018\u0002\u0002\u0013\u0005#1\f\u0005\n\u0005Sr\u0013\u0011!C\u0001\u0007\u0003A\u0011B!\u001e/\u0003\u0003%\te!\u0002\t\u0013\tmd&!A\u0005B\tu\u0004\"\u0003B@]\u0005\u0005I\u0011\tBA\u0011%\u0011\u0019ILA\u0001\n\u0003\u001aIaB\u0005\u0004~\u0005\t\t\u0011#\u0003\u0004\u0000\u0019I!Q^\u0001\u0002\u0002#%1\u0011\u0011\u0005\b\u0003\u0017sD\u0011ABH\u0011%\u0011yHPA\u0001\n\u000b\u0012\t\tC\u0005\u0002Xz\n\t\u0011\"!\u0004\u0012\"I!\u0011\u0013 \u0002\u0002\u0013\u00055Q\u0013\u0005\n\u0005Ks\u0014\u0011!C\u0005\u0005O3aa!\u0004\u0002\r\u000e=\u0001B\u0003Bc\t\nU\r\u0011\"\u0001\u0002\u0018\"Q!q\u0019#\u0003\u0012\u0003\u0006I!!'\t\u000f\u0005-E\t\"\u0001\u0004\u0012!I!Q\u0004#\u0002\u0002\u0013\u00051q\u0003\u0005\n\u0005K!\u0015\u0013!C\u0001\u0005'D\u0011Ba\u0010E\u0003\u0003%\tE!\u0011\t\u0013\t%C)!A\u0005\u0002\u0005u\b\"\u0003B&\t\u0006\u0005I\u0011AB\u000e\u0011%\u0011I\u0006RA\u0001\n\u0003\u0012Y\u0006C\u0005\u0003j\u0011\u000b\t\u0011\"\u0001\u0004 !I!Q\u000f#\u0002\u0002\u0013\u000531\u0005\u0005\n\u0005w\"\u0015\u0011!C!\u0005{B\u0011Ba E\u0003\u0003%\tE!!\t\u0013\t\rE)!A\u0005B\r\u001dr!CBN\u0003\u0005\u0005\t\u0012BBO\r%\u0019i!AA\u0001\u0012\u0013\u0019y\nC\u0004\u0002\fR#\taa)\t\u0013\t}D+!A\u0005F\t\u0005\u0005\"CAl)\u0006\u0005I\u0011QBS\u0011%\u0011\t\nVA\u0001\n\u0003\u001bI\u000bC\u0005\u0003&R\u000b\t\u0011\"\u0003\u0003(\u001a11\u0011J\u0001G\u0007\u0017B!B!2[\u0005+\u0007I\u0011AAL\u0011)\u00119M\u0017B\tB\u0003%\u0011\u0011\u0014\u0005\u000b\u0007\u001bR&Q3A\u0005\u0002\r=\u0003BCB,5\nE\t\u0015!\u0003\u0004R!9\u00111\u0012.\u0005\u0002\re\u0003\"\u0003B\u000f5\u0006\u0005I\u0011AB1\u0011%\u0011)CWI\u0001\n\u0003\u0011\u0019\u000eC\u0005\u0003>i\u000b\n\u0011\"\u0001\u0004h!I!q\b.\u0002\u0002\u0013\u0005#\u0011\t\u0005\n\u0005\u0013R\u0016\u0011!C\u0001\u0003{D\u0011Ba\u0013[\u0003\u0003%\taa\u001b\t\u0013\te#,!A\u0005B\tm\u0003\"\u0003B55\u0006\u0005I\u0011AB8\u0011%\u0011)HWA\u0001\n\u0003\u001a\u0019\bC\u0005\u0003|i\u000b\t\u0011\"\u0011\u0003~!I!q\u0010.\u0002\u0002\u0013\u0005#\u0011\u0011\u0005\n\u0005\u0007S\u0016\u0011!C!\u0007o:\u0011b!,\u0002\u0003\u0003EIaa,\u0007\u0013\r%\u0013!!A\t\n\rE\u0006bBAF[\u0012\u00051\u0011\u0018\u0005\n\u0005\u007fj\u0017\u0011!C#\u0005\u0003C\u0011\"a6n\u0003\u0003%\tia/\t\u0013\tEU.!A\u0005\u0002\u000e\u0005\u0007\"\u0003BS[\u0006\u0005I\u0011\u0002BT\r\u0019\u0019Y#\u0001$\u0004.!Q!QY:\u0003\u0016\u0004%\t!a&\t\u0015\t\u001d7O!E!\u0002\u0013\tI\nC\u0004\u0002\fN$\taa\f\t\u0013\tu1/!A\u0005\u0002\rU\u0002\"\u0003B\u0013gF\u0005I\u0011\u0001Bj\u0011%\u0011yd]A\u0001\n\u0003\u0012\t\u0005C\u0005\u0003JM\f\t\u0011\"\u0001\u0002~\"I!1J:\u0002\u0002\u0013\u00051\u0011\b\u0005\n\u00053\u001a\u0018\u0011!C!\u00057B\u0011B!\u001bt\u0003\u0003%\ta!\u0010\t\u0013\tU4/!A\u0005B\r\u0005\u0003\"\u0003B>g\u0006\u0005I\u0011\tB?\u0011%\u0011yh]A\u0001\n\u0003\u0012\t\tC\u0005\u0003\u0004N\f\t\u0011\"\u0011\u0004F\u001dI1\u0011Z\u0001\u0002\u0002#%11\u001a\u0004\n\u0007W\t\u0011\u0011!E\u0005\u0007\u001bD\u0001\"a#\u0002\b\u0011\u00051\u0011\u001b\u0005\u000b\u0005\u007f\n9!!A\u0005F\t\u0005\u0005BCAl\u0003\u000f\t\t\u0011\"!\u0004T\"Q!\u0011SA\u0004\u0003\u0003%\tia6\t\u0015\t\u0015\u0016qAA\u0001\n\u0013\u00119K\u0002\u0004\u0003B\u00061%1\u0019\u0005\f\u0005\u000b\f\u0019B!f\u0001\n\u0003\t9\nC\u0006\u0003H\u0006M!\u0011#Q\u0001\n\u0005e\u0005\u0002CAF\u0003'!\tA!3\t\u0015\tu\u00111CA\u0001\n\u0003\u0011y\r\u0003\u0006\u0003&\u0005M\u0011\u0013!C\u0001\u0005'D!Ba\u0010\u0002\u0014\u0005\u0005I\u0011\tB!\u0011)\u0011I%a\u0005\u0002\u0002\u0013\u0005\u0011Q \u0005\u000b\u0005\u0017\n\u0019\"!A\u0005\u0002\t]\u0007B\u0003B-\u0003'\t\t\u0011\"\u0011\u0003\\!Q!\u0011NA\n\u0003\u0003%\tAa7\t\u0015\tU\u00141CA\u0001\n\u0003\u0012y\u000e\u0003\u0006\u0003|\u0005M\u0011\u0011!C!\u0005{B!Ba \u0002\u0014\u0005\u0005I\u0011\tBA\u0011)\u0011\u0019)a\u0005\u0002\u0002\u0013\u0005#1]\u0004\n\u00077\f\u0011\u0011!E\u0005\u0007;4\u0011B!1\u0002\u0003\u0003EIaa8\t\u0011\u0005-\u00151\u0007C\u0001\u0007GD!Ba \u00024\u0005\u0005IQ\tBA\u0011)\t9.a\r\u0002\u0002\u0013\u00055Q\u001d\u0005\u000b\u0005#\u000b\u0019$!A\u0005\u0002\u000e%\bB\u0003BS\u0003g\t\t\u0011\"\u0003\u0003(\"I1Q^\u0001C\u0002\u0013\u0005!\u0011\t\u0005\t\u0007_\f\u0001\u0015!\u0003\u0003D!I1\u0011_\u0001C\u0002\u0013\u0005!\u0011\t\u0005\t\u0007g\f\u0001\u0015!\u0003\u0003D!I1Q_\u0001C\u0002\u0013\u0005!\u0011\t\u0005\t\u0007o\f\u0001\u0015!\u0003\u0003D!I1\u0011`\u0001C\u0002\u0013\u0005!\u0011\t\u0005\t\u0007w\f\u0001\u0015!\u0003\u0003D\u0019I\u00111PA4\u0005\u0005=D1\u0002\u0005\f\ts\tyE!b\u0001\n\u0003!Y\u0004C\u0006\u0005J\u0005=#\u0011!Q\u0001\n\u0011u\u0002\u0002CAF\u0003\u001f\"\t\u0001b\u0013\t\u0015\u0011E\u0013q\nb\u0001\n\u0003!\u0019\u0006C\u0005\u0005\\\u0005=\u0003\u0015!\u0003\u0005V!AAQLA(\t\u0003\"y\u0006\u0003\u0005\u0005b\u0005=C\u0011\u0002C2\u0011!!\u0019(a\u0014\u0005B\u0011U\u0004B\u0003C:\u0003\u001f\"\t%a\u001d\u0005\n\"A!qPA(\t\u0003\"9*\u0001\nT_V\u00148-\u001a*fMN#\u0018mZ3J[Bd'\u0002BA5\u0003W\n\u0011b\u001d;sK\u0006l'/\u001a4\u000b\t\u00055\u0014qN\u0001\u0005S6\u0004HN\u0003\u0003\u0002r\u0005M\u0014AB:ue\u0016\fWN\u0003\u0002\u0002v\u0005!\u0011m[6b!\r\tI(A\u0007\u0003\u0003O\u0012!cU8ve\u000e,'+\u001a4Ti\u0006<W-S7qYN\u0019\u0011!a \u0011\t\u0005\u0005\u0015qQ\u0007\u0003\u0003\u0007S!!!\"\u0002\u000bM\u001c\u0017\r\\1\n\t\u0005%\u00151\u0011\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\"!a\u001e\u0003\u001b\u0005\u001bGo\u001c:SK\u001a\u001cF/Y4f'\r\u0019\u0011qP\u0001\u0004e\u00164WCAAM!\u0011\tY*!)\u000e\u0005\u0005u%\u0002BAP\u0003g\nQ!Y2u_JLA!a)\u0002\u001e\nA\u0011i\u0019;peJ+g-K\u0002\u0004\u0003O3a!!+\u0004\u0001\u0005-&!\u0004\u001fm_\u000e\fG\u000eI2iS2$gh\u0005\u0004\u0002(\u00065\u0016Q\u0018\t\u0005\u0003_\u000bI,\u0004\u0002\u00022*!\u00111WA[\u0003\u0011a\u0017M\\4\u000b\u0005\u0005]\u0016\u0001\u00026bm\u0006LA!a/\u00022\n1qJ\u00196fGR\u00042!a0\u0004\u001b\u0005\t\u0011\u0001G,bi\u0016\u0014X.\u0019:l%\u0016\fX/Z:u'R\u0014\u0018\r^3hsB\u0019\u0011q\u0018\u0004\u00031]\u000bG/\u001a:nCJ\\'+Z9vKN$8\u000b\u001e:bi\u0016<\u0017pE\u0003\u0007\u0003\u007f\nI\r\u0005\u0003\u0002L\u0006EWBAAg\u0015\u0011\ty-!.\u0002\u0005%|\u0017\u0002BAj\u0003\u001b\u0014AbU3sS\u0006d\u0017N_1cY\u0016$\"!a1\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\t\u0005m'\u0011\u0012\t\u0004\u0003\u007fc1c\u0002\u0007\u0002\u0000\u0005}\u0017Q\u001d\t\u0005\u0003\u0003\u000b\t/\u0003\u0003\u0002d\u0006\r%a\u0002)s_\u0012,8\r\u001e\t\u0005\u0003O\f9P\u0004\u0003\u0002j\u0006Mh\u0002BAv\u0003cl!!!<\u000b\t\u0005=\u0018QR\u0001\u0007yI|w\u000e\u001e \n\u0005\u0005\u0015\u0015\u0002BA{\u0003\u0007\u000bq\u0001]1dW\u0006<W-\u0003\u0003\u0002T\u0006e(\u0002BA{\u0003\u0007\u000bQ\u0002[5hQ^\u000bG/\u001a:nCJ\\WCAA\u0000!\u0011\t\tI!\u0001\n\t\t\r\u00111\u0011\u0002\u0004\u0013:$\u0018A\u00045jO\"<\u0016\r^3s[\u0006\u00148\u000eI\u0001\rY><x+\u0019;fe6\f'o[\u0001\u000eY><x+\u0019;fe6\f'o\u001b\u0011\u0015\r\u0005m'Q\u0002B\b\u0011\u001d\tY0\u0005a\u0001\u0003\u007fDqAa\u0002\u0012\u0001\u0004\ty\u0010\u0006\u0003\u0002\\\nM\u0001bBA~%\u0001\u0007\u0011q`\u0001\u000ee\u0016\fX/Z:u\t\u0016l\u0017M\u001c3\u0015\t\u0005}(\u0011\u0004\u0005\b\u00057\u0019\u0002\u0019AA\u0000\u0003I\u0011X-\\1j]&twMU3rk\u0016\u001cH/\u001a3\u0002\t\r|\u0007/\u001f\u000b\u0007\u00037\u0014\tCa\t\t\u0013\u0005mH\u0003%AA\u0002\u0005}\b\"\u0003B\u0004)A\u0005\t\u0019AA\u0000\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\"A!\u000b+\t\u0005}(1F\u0016\u0003\u0005[\u0001BAa\f\u0003:5\u0011!\u0011\u0007\u0006\u0005\u0005g\u0011)$A\u0005v]\u000eDWmY6fI*!!qGAB\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0005w\u0011\tDA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0005\u0007\u0002B!a,\u0003F%!!qIAY\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5us\u0006q\u0001O]8ek\u000e$X\t\\3nK:$H\u0003\u0002B(\u0005+\u0002B!!!\u0003R%!!1KAB\u0005\r\te.\u001f\u0005\n\u0005/J\u0012\u0011!a\u0001\u0003\u007f\f1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XC\u0001B/!\u0019\u0011yF!\u001a\u0003P5\u0011!\u0011\r\u0006\u0005\u0005G\n\u0019)\u0001\u0006d_2dWm\u0019;j_:LAAa\u001a\u0003b\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\u0011iGa\u001d\u0011\t\u0005\u0005%qN\u0005\u0005\u0005c\n\u0019IA\u0004C_>dW-\u00198\t\u0013\t]3$!AA\u0002\t=\u0013A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$BAa\u0011\u0003z!I!q\u000b\u000f\u0002\u0002\u0003\u0007\u0011q`\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011q`\u0001\ti>\u001cFO]5oOR\u0011!1I\u0001\u0007KF,\u0018\r\\:\u0015\t\t5$q\u0011\u0005\n\u0005/z\u0012\u0011!a\u0001\u0005\u001fBq!a?\t\u0001\u0004\ty\u0010\u0006\u0004\u0002\\\n5%q\u0012\u0005\b\u0003wL\u0001\u0019AA\u0000\u0011\u001d\u00119!\u0003a\u0001\u0003\u007f\fq!\u001e8baBd\u0017\u0010\u0006\u0003\u0003\u0016\n\u0005\u0006CBAA\u0005/\u0013Y*\u0003\u0003\u0003\u001a\u0006\r%AB(qi&|g\u000e\u0005\u0005\u0002\u0002\nu\u0015q`A\u0000\u0013\u0011\u0011y*a!\u0003\rQ+\b\u000f\\33\u0011%\u0011\u0019KCA\u0001\u0002\u0004\tY.A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!,\u0003\u000bM#\u0018\r^3\u0014\u0007\u0001\ny(K\u0003!I\u0005M\u0011EA\bBo\u0006LG/\u001b8h!\u0006\u0014HO\\3s'%!\u0013q\u0010BZ\u0003?\f)\u000fE\u0002\u0002@\u0002\"\"Aa.\u0011\u0007\u0005}F\u0005\u0006\u0003\u0003P\tm\u0006\"\u0003B,Q\u0005\u0005\t\u0019AA\u0000)\u0011\u0011iGa0\t\u0013\t]#&!AA\u0002\t=#AE+qgR\u0014X-Y7UKJl\u0017N\\1uK\u0012\u001c\"\"a\u0005\u0002\u0000\tM\u0016q\\As\u0003\u001d\u0001\u0018M\u001d;oKJ\f\u0001\u0002]1si:,'\u000f\t\u000b\u0005\u0005\u0017\u0014i\r\u0005\u0003\u0002@\u0006M\u0001\u0002\u0003Bc\u00033\u0001\r!!'\u0015\t\t-'\u0011\u001b\u0005\u000b\u0005\u000b\fY\u0002%AA\u0002\u0005eUC\u0001BkU\u0011\tIJa\u000b\u0015\t\t=#\u0011\u001c\u0005\u000b\u0005/\n\u0019#!AA\u0002\u0005}H\u0003\u0002B7\u0005;D!Ba\u0016\u0002(\u0005\u0005\t\u0019\u0001B()\u0011\u0011\u0019E!9\t\u0015\t]\u0013\u0011FA\u0001\u0002\u0004\ty\u0010\u0006\u0003\u0003n\t\u0015\bB\u0003B,\u0003_\t\t\u00111\u0001\u0003P\tiq+Z&o_^\u0004\u0016M\u001d;oKJ\u001cR!IA@\u0005gKS!\t\u0018Egj\u0013A#Q<bSRLgnZ*vEN\u001c'/\u001b9uS>t7#\u0003\u0018\u0002\u0000\tE\u0018q\\As!\r\ty,\t\u000b\u0005\u0005k\u00149\u0010E\u0002\u0002@:BqA!22\u0001\u0004\tI\n\u0006\u0003\u0003v\nm\b\"\u0003BceA\u0005\t\u0019AAM)\u0011\u0011yEa@\t\u0013\t]c'!AA\u0002\u0005}H\u0003\u0002B7\u0007\u0007A\u0011Ba\u00169\u0003\u0003\u0005\rAa\u0014\u0015\t\t\r3q\u0001\u0005\n\u0005/J\u0014\u0011!a\u0001\u0003\u007f$BA!\u001c\u0004\f!I!q\u000b\u001f\u0002\u0002\u0003\u0007!q\n\u0002\b%Vtg.\u001b8h'%!\u0015q\u0010By\u0003?\f)\u000f\u0006\u0003\u0004\u0014\rU\u0001cAA`\t\"9!QY$A\u0002\u0005eE\u0003BB\n\u00073A\u0011B!2I!\u0003\u0005\r!!'\u0015\t\t=3Q\u0004\u0005\n\u0005/b\u0015\u0011!a\u0001\u0003\u007f$BA!\u001c\u0004\"!I!q\u000b(\u0002\u0002\u0003\u0007!q\n\u000b\u0005\u0005\u0007\u001a)\u0003C\u0005\u0003X=\u000b\t\u00111\u0001\u0002\u0000R!!QNB\u0015\u0011%\u00119FUA\u0001\u0002\u0004\u0011yEA\tVaN$(/Z1n\u0007>l\u0007\u000f\\3uK\u0012\u001c\u0012b]A@\u0005c\fy.!:\u0015\t\rE21\u0007\t\u0004\u0003\u007f\u001b\bb\u0002Bcm\u0002\u0007\u0011\u0011\u0014\u000b\u0005\u0007c\u00199\u0004C\u0005\u0003F^\u0004\n\u00111\u0001\u0002\u001aR!!qJB\u001e\u0011%\u00119f_A\u0001\u0002\u0004\ty\u0010\u0006\u0003\u0003n\r}\u0002\"\u0003B,{\u0006\u0005\t\u0019\u0001B()\u0011\u0011\u0019ea\u0011\t\u0013\t]c0!AA\u0002\u0005}H\u0003\u0002B7\u0007\u000fB!Ba\u0016\u0002\u0004\u0005\u0005\t\u0019\u0001B(\u0005M9\u0016-\u001b;j]\u001e4uN]\"b]\u000e,G.Q2l'%Q\u0016q\u0010By\u0003?\f)/A\u0003dCV\u001cX-\u0006\u0002\u0004RA!\u0011q]B*\u0013\u0011\u0019)&!?\u0003\u0013QC'o\\<bE2,\u0017AB2bkN,\u0007\u0005\u0006\u0004\u0004\\\ru3q\f\t\u0004\u0003\u007fS\u0006b\u0002Bc?\u0002\u0007\u0011\u0011\u0014\u0005\b\u0007\u001bz\u0006\u0019AB))\u0019\u0019Yfa\u0019\u0004f!I!Q\u00191\u0011\u0002\u0003\u0007\u0011\u0011\u0014\u0005\n\u0007\u001b\u0002\u0007\u0013!a\u0001\u0007#*\"a!\u001b+\t\rE#1\u0006\u000b\u0005\u0005\u001f\u001ai\u0007C\u0005\u0003X\u0015\f\t\u00111\u0001\u0002\u0000R!!QNB9\u0011%\u00119fZA\u0001\u0002\u0004\u0011y\u0005\u0006\u0003\u0003D\rU\u0004\"\u0003B,Q\u0006\u0005\t\u0019AA\u0000)\u0011\u0011ig!\u001f\t\u0013\t]3.!AA\u0002\t=\u0013aD!xC&$\u0018N\\4QCJ$h.\u001a:\u0002)\u0005;\u0018-\u001b;j]\u001e\u001cVOY:de&\u0004H/[8o!\r\tyLP\n\u0006}\r\r\u0015\u0011\u001a\t\t\u0007\u000b\u001bY)!'\u0003v6\u00111q\u0011\u0006\u0005\u0007\u0013\u000b\u0019)A\u0004sk:$\u0018.\\3\n\t\r55q\u0011\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\fDCAB@)\u0011\u0011)pa%\t\u000f\t\u0015\u0017\t1\u0001\u0002\u001aR!1qSBM!\u0019\t\tIa&\u0002\u001a\"I!1\u0015\"\u0002\u0002\u0003\u0007!Q_\u0001\b%Vtg.\u001b8h!\r\ty\fV\n\u0006)\u000e\u0005\u0016\u0011\u001a\t\t\u0007\u000b\u001bY)!'\u0004\u0014Q\u00111Q\u0014\u000b\u0005\u0007'\u00199\u000bC\u0004\u0003F^\u0003\r!!'\u0015\t\r]51\u0016\u0005\n\u0005GC\u0016\u0011!a\u0001\u0007'\t1cV1ji&twMR8s\u0007\u0006t7-\u001a7BG.\u00042!a0n'\u0015i71WAe!)\u0019)i!.\u0002\u001a\u000eE31L\u0005\u0005\u0007o\u001b9IA\tBEN$(/Y2u\rVt7\r^5p]J\"\"aa,\u0015\r\rm3QXB`\u0011\u001d\u0011)\r\u001da\u0001\u00033Cqa!\u0014q\u0001\u0004\u0019\t\u0006\u0006\u0003\u0004D\u000e\u001d\u0007CBAA\u0005/\u001b)\r\u0005\u0005\u0002\u0002\nu\u0015\u0011TB)\u0011%\u0011\u0019+]A\u0001\u0002\u0004\u0019Y&A\tVaN$(/Z1n\u0007>l\u0007\u000f\\3uK\u0012\u0004B!a0\u0002\bM1\u0011qABh\u0003\u0013\u0004\u0002b!\"\u0004\f\u0006e5\u0011\u0007\u000b\u0003\u0007\u0017$Ba!\r\u0004V\"A!QYA\u0007\u0001\u0004\tI\n\u0006\u0003\u0004\u0018\u000ee\u0007B\u0003BR\u0003\u001f\t\t\u00111\u0001\u00042\u0005\u0011R\u000b]:ue\u0016\fW\u000eV3s[&t\u0017\r^3e!\u0011\ty,a\r\u0014\r\u0005M2\u0011]Ae!!\u0019)ia#\u0002\u001a\n-GCABo)\u0011\u0011Yma:\t\u0011\t\u0015\u0017\u0011\ba\u0001\u00033#Baa&\u0004l\"Q!1UA\u001e\u0003\u0003\u0005\rAa3\u00027M+(m]2sSB$\u0018n\u001c8US6,w.\u001e;US6,'oS3z\u0003q\u0019VOY:de&\u0004H/[8o)&lWm\\;u)&lWM]&fs\u0002\n\u0001\u0004R3nC:$'+\u001a3fY&4XM]=US6,'oS3z\u0003e!U-\\1oIJ+G-\u001a7jm\u0016\u0014\u0018\u0010V5nKJ\\U-\u001f\u0011\u00027Q+'/\\5oCRLwN\u001c#fC\u0012d\u0017N\\3US6,'oS3z\u0003q!VM]7j]\u0006$\u0018n\u001c8EK\u0006$G.\u001b8f)&lWM]&fs\u0002\nAdQ1oG\u0016dG.\u0019;j_:$U-\u00193mS:,G+[7fe.+\u00170A\u000fDC:\u001cW\r\u001c7bi&|g\u000eR3bI2Lg.\u001a+j[\u0016\u00148*Z=!Q\r\t1q \t\u0005\t\u0003!)!\u0004\u0002\u0005\u0004)!!qGA:\u0013\u0011!9\u0001b\u0001\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b\u0015\u0004\u0001\r}X\u0003\u0002C\u0007\tO\u0019B!a\u0014\u0005\u0010AAA\u0011\u0003C\f\t7!\u0019$\u0004\u0002\u0005\u0014)!AQCA8\u0003\u0015\u0019H/Y4f\u0013\u0011!I\u0002b\u0005\u0003?\u001d\u0013\u0018\r\u001d5Ti\u0006<WmV5uQ6\u000bG/\u001a:jC2L'0\u001a3WC2,X\r\u0005\u0004\u0005\u001e\u0011}A1E\u0007\u0003\u0003_JA\u0001\"\t\u0002p\tY1k\\;sG\u0016\u001c\u0006.\u00199f!\u0011!)\u0003b\n\r\u0001\u0011AA\u0011FA(\u0005\u0004!YCA\u0002PkR\fB\u0001\"\f\u0003PA!\u0011\u0011\u0011C\u0018\u0013\u0011!\t$a!\u0003\u000f9{G\u000f[5oOB1AQ\u0004C\u001b\tGIA\u0001b\u000e\u0002p\t91+\u001b8l%\u00164\u0017!E5oSRL\u0017\r\u001c)beRtWM\u001d*fMV\u0011AQ\b\t\u0007\t\u007f!)%!'\u000e\u0005\u0011\u0005#\u0002\u0002C\"\u0003g\nA!\u001e;jY&!Aq\tC!\u0005%y\u0005\u000f^5p]Z\u000bG.\u0001\nj]&$\u0018.\u00197QCJ$h.\u001a:SK\u001a\u0004C\u0003\u0002C'\t\u001f\u0002b!!\u001f\u0002P\u0011\r\u0002\u0002\u0003C\u001d\u0003+\u0002\r\u0001\"\u0010\u0002\u0007=,H/\u0006\u0002\u0005VA1AQ\u0004C,\tGIA\u0001\"\u0017\u0002p\t1q*\u001e;mKR\fAa\\;uA\u0005)1\u000f[1qKV\u0011A1D\u0001\u000fS:LG/[1m%\u00164g*Y7f+\t!)\u0007\u0005\u0003\u0005h\u0011=d\u0002\u0002C5\tW\u0002B!a;\u0002\u0004&!AQNAB\u0003\u0019\u0001&/\u001a3fM&!!q\tC9\u0015\u0011!i'a!\u0002?\r\u0014X-\u0019;f\u0019><\u0017nY!oI6\u000bG/\u001a:jC2L'0\u001a3WC2,X\r\u0006\u0003\u0005x\u0011}\u0004\u0003CAA\u0005;#I\bb\r\u0011\t\u0011EA1P\u0005\u0005\t{\"\u0019BA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011!!\t)a\u0018A\u0002\u0011\r\u0015aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\b\u0003\u0002C\u000f\t\u000bKA\u0001b\"\u0002p\tQ\u0011\t\u001e;sS\n,H/Z:\u0015\r\u0011]D1\u0012CG\u0011!!\t)!\u0019A\u0002\u0011\r\u0005\u0002\u0003CH\u0003C\u0002\r\u0001\"%\u0002#\u0015\fw-\u001a:NCR,'/[1mSj,'\u000f\u0005\u0003\u0005\u001e\u0011M\u0015\u0002\u0002CK\u0003_\u0012A\"T1uKJL\u0017\r\\5{KJ$\"\u0001\"\u001a)\t\u0005=3q ")
@InternalApi
public final class SourceRefStageImpl<Out>
extends GraphStageWithMaterializedValue<SourceShape<Out>, SinkRef<Out>> {
    private final ActorRef initialPartnerRef;
    private final Outlet<Out> out;

    public static String CancellationDeadlineTimerKey() {
        return SourceRefStageImpl$.MODULE$.CancellationDeadlineTimerKey();
    }

    public static String TerminationDeadlineTimerKey() {
        return SourceRefStageImpl$.MODULE$.TerminationDeadlineTimerKey();
    }

    public static String DemandRedeliveryTimerKey() {
        return SourceRefStageImpl$.MODULE$.DemandRedeliveryTimerKey();
    }

    public static String SubscriptionTimeoutTimerKey() {
        return SourceRefStageImpl$.MODULE$.SubscriptionTimeoutTimerKey();
    }

    public ActorRef initialPartnerRef() {
        return this.initialPartnerRef;
    }

    public Outlet<Out> out() {
        return this.out;
    }

    @Override
    public SourceShape<Out> shape() {
        return SourceShape$.MODULE$.of(this.out());
    }

    private String initialRefName() {
        ActorRef actorRef2 = this.initialPartnerRef();
        ActorRef actorRef3 = OptionVal$Some$.MODULE$.unapply(actorRef2);
        if (!OptionVal$.MODULE$.isEmpty$extension(actorRef3)) {
            ActorRef ref = OptionVal$.MODULE$.get$extension(actorRef3);
            return ref.toString();
        }
        return "<no-initial-ref>";
    }

    @Override
    public Tuple2<GraphStageLogic, SinkRef<Out>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        throw new IllegalStateException("Not supported");
    }

    @Override
    public Tuple2<GraphStageLogic, SinkRef<Out>> createLogicAndMaterializedValue(Attributes inheritedAttributes, Materializer eagerMaterializer) {
        StageLogging logic = new StageLogging(this, eagerMaterializer, inheritedAttributes){
            private final StreamRefsMaster streamRefsMaster;
            private final StreamRefSettings settings;
            private final StreamRefAttributes.SubscriptionTimeout subscriptionTimeout;
            private final int bufferCapacity;
            private final FiniteDuration demandRedeliveryInterval;
            private final FiniteDuration finalTerminationSignalDeadline;
            private final String stageActorName;
            private final GraphStageLogic.StageActor self;
            private final ActorRef ref;
            private State state;
            private long expectingSeqNr;
            private long localCumulativeDemand;
            private int localRemainingRequested;
            private final FixedSizeBuffer.FixedSizeBuffer<Out> receiveBuffer;
            private final WatermarkRequestStrategy requestStrategy;
            private LoggingAdapter akka$stream$stage$StageLogging$$_log;
            private final /* synthetic */ SourceRefStageImpl $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public LoggingAdapter log() {
                return StageLogging.log$(this);
            }

            public LoggingAdapter akka$stream$stage$StageLogging$$_log() {
                return this.akka$stream$stage$StageLogging$$_log;
            }

            public void akka$stream$stage$StageLogging$$_log_$eq(LoggingAdapter x$1) {
                this.akka$stream$stage$StageLogging$$_log = x$1;
            }

            public Class<?> logSource() {
                return SourceRefStageImpl.class;
            }

            public String stageActorName() {
                return this.stageActorName;
            }

            public ActorRef ref() {
                return this.ref;
            }

            private ActorRef selfSender() {
                return this.ref();
            }

            private State state() {
                return this.state;
            }

            private void state_$eq(State x$1) {
                this.state = x$1;
            }

            private long expectingSeqNr() {
                return this.expectingSeqNr;
            }

            private void expectingSeqNr_$eq(long x$1) {
                this.expectingSeqNr = x$1;
            }

            private long localCumulativeDemand() {
                return this.localCumulativeDemand;
            }

            private void localCumulativeDemand_$eq(long x$1) {
                this.localCumulativeDemand = x$1;
            }

            private int localRemainingRequested() {
                return this.localRemainingRequested;
            }

            private void localRemainingRequested_$eq(int x$1) {
                this.localRemainingRequested = x$1;
            }

            private FixedSizeBuffer.FixedSizeBuffer<Out> receiveBuffer() {
                return this.receiveBuffer;
            }

            private WatermarkRequestStrategy requestStrategy() {
                return this.requestStrategy;
            }

            public void preStart() {
                this.log().debug("[{}] Starting up with, self ref: {}, state: {}, subscription timeout: {}", this.stageActorName(), this.self.ref(), this.state(), PrettyDuration$.MODULE$.format(this.subscriptionTimeout.timeout()));
                this.scheduleOnce((Object)SourceRefStageImpl$.MODULE$.SubscriptionTimeoutTimerKey(), this.subscriptionTimeout.timeout());
            }

            public void onPull() {
                this.tryPush();
                this.triggerCumulativeDemand();
            }

            public Function1<Tuple2<ActorRef, Object>, BoxedUnit> receiveRemoteMessage() {
                return (Function1<Tuple2, Object> & Serializable)x0$1 -> {
                    $anon$1.$anonfun$receiveRemoteMessage$1(this, x0$1);
                    return BoxedUnit.UNIT;
                };
            }

            public void onTimer(Object timerKey) {
                Object object = timerKey;
                String string = SourceRefStageImpl$.MODULE$.SubscriptionTimeoutTimerKey();
                Object object2 = object;
                if (!(string != null ? !string.equals(object2) : object2 != null)) {
                    State state = this.state();
                    if (SourceRefStageImpl$AwaitingPartner$.MODULE$.equals(state) ? true : state instanceof AwaitingSubscription) {
                        StreamRefSubscriptionTimeoutException ex = new StreamRefSubscriptionTimeoutException(new StringBuilder(106).append("[").append(this.stageActorName()).append("] Remote side did not subscribe (materialize) handed out Sink reference [").append(this.ref()).append("],").append("within subscription timeout: ").append(PrettyDuration$.MODULE$.format(this.subscriptionTimeout.timeout())).append("!").toString());
                        throw ex;
                    }
                    this.log().debug("[{}] Ignoring subscription timeout in state [{}]", this.stageActorName(), state);
                    return;
                }
                String string2 = SourceRefStageImpl$.MODULE$.DemandRedeliveryTimerKey();
                Object object3 = object;
                if (!(string2 != null ? !string2.equals(object3) : object3 != null)) {
                    State state = this.state();
                    if (state instanceof Running) {
                        Running running = (Running)state;
                        ActorRef ref = running.partner();
                        this.log().debug("[{}] Scheduled re-delivery of demand until [{}]", this.stageActorName(), BoxesRunTime.boxToLong(this.localCumulativeDemand()));
                        ref.$bang(new StreamRefsProtocol.CumulativeDemand(this.localCumulativeDemand()), this.selfSender());
                        this.scheduleDemandRedelivery();
                    } else {
                        this.log().debug("[{}] Ignoring demand redelivery timeout in state [{}]", this.stageActorName(), state);
                    }
                    return;
                }
                String string3 = SourceRefStageImpl$.MODULE$.TerminationDeadlineTimerKey();
                Object object4 = object;
                if (!(string3 != null ? !string3.equals(object4) : object4 != null)) {
                    State state = this.state();
                    if (state instanceof UpstreamTerminated) {
                        UpstreamTerminated upstreamTerminated = (UpstreamTerminated)state;
                        ActorRef partner = upstreamTerminated.partner();
                        this.log().debug("[{}] Remote partner [{}] has terminated unexpectedly and no clean completion/failure message was received", this.stageActorName(), partner);
                        this.failStage(new RemoteStreamRefActorTerminatedException(new StringBuilder(211).append("[").append(this.stageActorName()).append("] Remote partner [").append(partner).append("] has terminated unexpectedly and no clean completion/failure message was received ").append("(possible reasons: network partition or subscription timeout triggered termination of partner). Tearing down.").toString()));
                    } else if (SourceRefStageImpl$AwaitingPartner$.MODULE$.equals(state)) {
                        this.log().debug("[{}] Downstream cancelled, but timeout hit before we saw a partner", this.stageActorName());
                        this.cancelStage(SubscriptionWithCancelException$NoMoreElementsNeeded$.MODULE$);
                    } else {
                        throw new IllegalStateException(new StringBuilder(50).append("TerminationDeadlineTimerKey can't happen in state ").append(state).toString());
                    }
                    return;
                }
                String string4 = SourceRefStageImpl$.MODULE$.CancellationDeadlineTimerKey();
                Object object5 = object;
                if (!(string4 != null ? !string4.equals(object5) : object5 != null)) {
                    State state = this.state();
                    if (!(state instanceof WaitingForCancelAck)) {
                        throw new IllegalStateException(new StringBuilder(54).append("[").append(this.stageActorName()).append("] CancellationDeadlineTimerKey can't happen in state ").append(state).toString());
                    }
                    WaitingForCancelAck waitingForCancelAck = (WaitingForCancelAck)state;
                    ActorRef partner = waitingForCancelAck.partner();
                    Throwable cause = waitingForCancelAck.cause();
                    this.log().debug("[{}] Waiting for remote ack from [{}] for downstream failure timed out, failing stage with original downstream failure", this.stageActorName(), partner);
                    this.cancelStage(cause);
                    return;
                }
                throw new IllegalArgumentException(new StringBuilder(19).append("Unknown timer key: ").append(object).toString());
            }

            public void onDownstreamFinish(Throwable cause) {
                State state = this.state();
                if (state instanceof Running) {
                    Running running = (Running)state;
                    ActorRef ref = running.partner();
                    this.triggerCancellationExchange(ref, cause);
                    return;
                }
                if (SourceRefStageImpl$AwaitingPartner$.MODULE$.equals(state)) {
                    this.scheduleOnce((Object)SourceRefStageImpl$.MODULE$.TerminationDeadlineTimerKey(), this.finalTerminationSignalDeadline);
                    return;
                }
                if (state instanceof AwaitingSubscription) {
                    AwaitingSubscription awaitingSubscription = (AwaitingSubscription)state;
                    ActorRef ref = awaitingSubscription.partner();
                    this.triggerCancellationExchange(ref, cause);
                    return;
                }
                if (state instanceof UpstreamCompleted) {
                    Throwable throwable;
                    if (this.receiveBuffer().nonEmpty()) {
                        this.log().debug("[{}] Downstream cancelled with elements [{}] in buffer, dropping elements", this.stageActorName(), BoxesRunTime.boxToInteger(this.receiveBuffer().used()));
                    }
                    if ((throwable = cause) instanceof SubscriptionWithCancelException.NonFailureCancellation) {
                        this.completeStage();
                    } else {
                        this.failStage(throwable);
                    }
                    return;
                }
                if (state instanceof WaitingForCancelAck) {
                    throw new UnsupportedOperationException(new StringBuilder(54).append("[").append(this.stageActorName()).append("] Didn't expect state ").append(this.state()).append(" when downstream finished with ").append(cause).toString());
                }
                if (state instanceof UpstreamTerminated) {
                    this.log().debug("[{}] Downstream cancelled with elements [{}] in buffer", this.stageActorName(), BoxesRunTime.boxToInteger(this.receiveBuffer().used()));
                    if (this.receiveBuffer().isEmpty()) {
                        this.failStage(new RemoteStreamRefActorTerminatedException(new StringBuilder(26).append("[").append(this.stageActorName()).append("] unexpectedly terminated").toString()));
                        return;
                    }
                    this.tryPush();
                    return;
                }
                throw new MatchError(state);
            }

            private void triggerCancellationExchange(ActorRef partner, Throwable cause) {
                Product product;
                Throwable throwable;
                if (this.receiveBuffer().nonEmpty()) {
                    this.log().debug("Downstream cancelled with elements [{}] in buffer, dropping elements", BoxesRunTime.boxToInteger(this.receiveBuffer().used()));
                }
                if ((throwable = cause) instanceof SubscriptionWithCancelException.NonFailureCancellation) {
                    this.log().debug("[{}] Deferred stop on downstream cancel", this.stageActorName());
                    product = new StreamRefsProtocol.RemoteStreamCompleted(this.expectingSeqNr());
                } else {
                    this.log().debug("[{}] Deferred stop on downstream failure: {}", this.stageActorName(), throwable);
                    product = new StreamRefsProtocol.RemoteStreamFailure("Downstream failed");
                }
                Product message = product;
                this.self.unwatch(partner);
                partner.$bang(message, this.selfSender());
                this.state_$eq(new WaitingForCancelAck(partner, cause));
                this.scheduleOnce((Object)SourceRefStageImpl$.MODULE$.CancellationDeadlineTimerKey(), this.subscriptionTimeout.timeout());
                this.setKeepGoing(true);
            }

            public void triggerCumulativeDemand() {
                int i = this.receiveBuffer().remainingCapacity() - this.localRemainingRequested();
                if (i > 0) {
                    int addDemand = this.requestStrategy().requestDemand(this.receiveBuffer().used() + this.localRemainingRequested());
                    if (addDemand > 0) {
                        State state = this.state();
                        if (state instanceof Running) {
                            Running running = (Running)state;
                            ActorRef partner = running.partner();
                            this.log().debug("[{}] Demanding until [{}] (+{})", this.stageActorName(), BoxesRunTime.boxToLong(this.localCumulativeDemand()), BoxesRunTime.boxToInteger(addDemand));
                            this.sendDemand$1(partner, addDemand);
                            return;
                        }
                        if (state instanceof AwaitingSubscription) {
                            AwaitingSubscription awaitingSubscription = (AwaitingSubscription)state;
                            ActorRef partner = awaitingSubscription.partner();
                            this.log().debug("[{}] Demanding, before subscription seen, until [{}] (+{})", this.stageActorName(), BoxesRunTime.boxToLong(this.localCumulativeDemand()), BoxesRunTime.boxToInteger(addDemand));
                            this.sendDemand$1(partner, addDemand);
                            return;
                        }
                        this.log().debug("[{}] Partner ref not set up in state {}, demanding elements deferred", this.stageActorName(), state);
                        return;
                    }
                    return;
                }
            }

            private void tryPush() {
                if (this.receiveBuffer().nonEmpty() && this.isAvailable(this.$outer.out())) {
                    Out element = this.receiveBuffer().dequeue();
                    this.push(this.$outer.out(), element);
                    return;
                }
                if (this.receiveBuffer().isEmpty()) {
                    State state = this.state();
                    if (state instanceof UpstreamCompleted) {
                        this.completeStage();
                        return;
                    }
                    return;
                }
            }

            private void onReceiveElement(Out payload) {
                this.localRemainingRequested_$eq(this.localRemainingRequested() - 1);
                if (this.receiveBuffer().isEmpty() && this.isAvailable(this.$outer.out())) {
                    this.push(this.$outer.out(), payload);
                    return;
                }
                if (this.receiveBuffer().isFull()) {
                    throw new IllegalStateException(new StringBuilder(112).append("Attempted to overflow buffer! ").append("Capacity: ").append(this.receiveBuffer().capacity()).append(", incoming element: ").append(payload).append(", ").append("localRemainingRequested: ").append(this.localRemainingRequested()).append(", localCumulativeDemand: ").append(this.localCumulativeDemand()).toString());
                }
                this.receiveBuffer().enqueue(payload);
            }

            private void verifyPartner(ActorRef sender, ActorRef partner) {
                ActorRef actorRef2 = sender;
                ActorRef actorRef3 = partner;
                if (actorRef2 == null ? actorRef3 != null : !((Object)actorRef2).equals(actorRef3)) {
                    throw new InvalidPartnerActorException(partner, sender, new StringBuilder(119).append("[").append(this.stageActorName()).append("] Received message from UNEXPECTED sender [").append(sender).append("]! ").append("This actor is NOT our trusted remote partner, which is [").append(partner).append("]. Tearing down.").toString());
                }
            }

            private void observeAndValidateSequenceNr(long seqNr, String msg) {
                if (this.isInvalidSequenceNr(seqNr)) {
                    this.log().warning("[{}] {}, expected {} but was {}", (Object)this.stageActorName(), (Object)msg, (Object)BoxesRunTime.boxToLong(this.expectingSeqNr()), (Object)BoxesRunTime.boxToLong(seqNr));
                    throw new InvalidSequenceNumberException(this.expectingSeqNr(), seqNr, msg);
                }
                this.expectingSeqNr_$eq(this.expectingSeqNr() + 1L);
            }

            private boolean isInvalidSequenceNr(long seqNr) {
                return seqNr != this.expectingSeqNr();
            }

            private void scheduleDemandRedelivery() {
                this.scheduleOnce((Object)SourceRefStageImpl$.MODULE$.DemandRedeliveryTimerKey(), this.demandRedeliveryInterval);
            }

            public static final /* synthetic */ void $anonfun$receiveRemoteMessage$1($anon$1 $this, Tuple2 x0$1) {
                T2 T2;
                Tuple2 tuple2 = x0$1;
                if (tuple2 != null) {
                    ActorRef sender = (ActorRef)tuple2._1();
                    T2 msg = tuple2._2();
                    if (msg instanceof StreamRefsProtocol.OnSubscribeHandshake) {
                        StreamRefsProtocol.OnSubscribeHandshake onSubscribeHandshake = (StreamRefsProtocol.OnSubscribeHandshake)msg;
                        ActorRef remoteRef = onSubscribeHandshake.targetRef();
                        State state = $this.state();
                        if (SourceRefStageImpl$AwaitingPartner$.MODULE$.equals(state)) {
                            $this.cancelTimer(SourceRefStageImpl$.MODULE$.SubscriptionTimeoutTimerKey());
                            $this.log().debug("[{}] Received on subscribe handshake {} while awaiting partner from {}", $this.stageActorName(), onSubscribeHandshake, remoteRef);
                            $this.state_$eq(new Running(remoteRef));
                            $this.self.watch(remoteRef);
                            $this.triggerCumulativeDemand();
                        } else if (state instanceof AwaitingSubscription) {
                            AwaitingSubscription awaitingSubscription = (AwaitingSubscription)state;
                            ActorRef partner = awaitingSubscription.partner();
                            $this.verifyPartner(sender, partner);
                            $this.cancelTimer(SourceRefStageImpl$.MODULE$.SubscriptionTimeoutTimerKey());
                            $this.log().debug("[{}] Received on subscribe handshake {} while awaiting subscription from {}", $this.stageActorName(), onSubscribeHandshake, remoteRef);
                            $this.state_$eq(new Running(remoteRef));
                            $this.triggerCumulativeDemand();
                        } else {
                            throw new IllegalStateException(new StringBuilder(28).append("[").append($this.stageActorName()).append("] Got unexpected ").append(onSubscribeHandshake).append(" in state ").append(state).toString());
                        }
                        return;
                    }
                }
                if (tuple2 != null) {
                    ActorRef sender = (ActorRef)tuple2._1();
                    T2 msg = tuple2._2();
                    if (msg instanceof StreamRefsProtocol.SequencedOnNext) {
                        StreamRefsProtocol.SequencedOnNext sequencedOnNext = (StreamRefsProtocol.SequencedOnNext)msg;
                        long seqNr = sequencedOnNext.seqNr();
                        T payload = sequencedOnNext.payload();
                        if (payload instanceof Object) {
                            T t2 = payload;
                            $this.observeAndValidateSequenceNr(seqNr, "Illegal sequence nr in SequencedOnNext");
                            State state = $this.state();
                            if (state instanceof AwaitingSubscription) {
                                AwaitingSubscription awaitingSubscription = (AwaitingSubscription)state;
                                ActorRef partner = awaitingSubscription.partner();
                                $this.verifyPartner(sender, partner);
                                $this.log().debug("[{}] Received seq {} from {}", $this.stageActorName(), sequencedOnNext, sender);
                                $this.state_$eq(new Running(partner));
                                $this.onReceiveElement(t2);
                                $this.triggerCumulativeDemand();
                            } else if (state instanceof Running) {
                                Running running = (Running)state;
                                ActorRef partner = running.partner();
                                $this.verifyPartner(sender, partner);
                                $this.onReceiveElement(t2);
                                $this.triggerCumulativeDemand();
                            } else {
                                if (SourceRefStageImpl$AwaitingPartner$.MODULE$.equals(state)) {
                                    throw new IllegalStateException(new StringBuilder(35).append("[").append($this.stageActorName()).append("] Got ").append(sequencedOnNext).append(" from ").append(sender).append(" while AwaitingPartner").toString());
                                }
                                if (state instanceof WaitingForCancelAck) {
                                    WaitingForCancelAck waitingForCancelAck = (WaitingForCancelAck)state;
                                    ActorRef partner = waitingForCancelAck.partner();
                                    $this.verifyPartner(sender, partner);
                                    $this.log().warning("[{}] Got element from remote but downstream cancelled, dropping element of type {}", (Object)$this.stageActorName(), t2.getClass());
                                } else {
                                    if (state instanceof UpstreamCompleted) {
                                        UpstreamCompleted upstreamCompleted = (UpstreamCompleted)state;
                                        ActorRef partner = upstreamCompleted.partner();
                                        $this.verifyPartner(sender, partner);
                                        throw new IllegalStateException(new StringBuilder(88).append("[").append($this.stageActorName()).append("] Got completion and then received more elements from ").append(sender).append(", this is not supposed to happen.").toString());
                                    }
                                    if (state instanceof UpstreamTerminated) {
                                        UpstreamTerminated upstreamTerminated = (UpstreamTerminated)state;
                                        ActorRef partner = upstreamTerminated.partner();
                                        $this.verifyPartner(sender, partner);
                                        $this.log().debug("[{}] Received element after partner terminated");
                                        $this.onReceiveElement(t2);
                                    } else {
                                        throw new MatchError(state);
                                    }
                                }
                            }
                            return;
                        }
                    }
                }
                if (tuple2 != null) {
                    ActorRef sender = (ActorRef)tuple2._1();
                    T2 T22 = tuple2._2();
                    if (T22 instanceof StreamRefsProtocol.RemoteStreamCompleted) {
                        StreamRefsProtocol.RemoteStreamCompleted remoteStreamCompleted = (StreamRefsProtocol.RemoteStreamCompleted)T22;
                        long seqNr = remoteStreamCompleted.seqNr();
                        $this.observeAndValidateSequenceNr(seqNr, "Illegal sequence nr in RemoteSinkCompleted");
                        State state = $this.state();
                        if (state instanceof Running) {
                            Running running = (Running)state;
                            ActorRef partner = running.partner();
                            $this.verifyPartner(sender, partner);
                            $this.log().debug("[{}] The remote stream has completed, emitting {} elements left in buffer before completing", $this.stageActorName(), BoxesRunTime.boxToInteger($this.receiveBuffer().used()));
                            $this.self.unwatch(sender);
                            $this.state_$eq(new UpstreamCompleted(partner));
                            $this.tryPush();
                        } else if (state instanceof WaitingForCancelAck) {
                            $this.log().debug("[{}] Upstream completed while waiting for cancel ack", $this.stageActorName());
                        } else {
                            throw new IllegalStateException(new StringBuilder(67).append("[").append($this.stageActorName()).append("] Saw RemoteStreamCompleted(").append(seqNr).append(") while in state ").append(state).append(", should never happen").toString());
                        }
                        return;
                    }
                }
                if (tuple2 != null) {
                    ActorRef sender = (ActorRef)tuple2._1();
                    T2 T23 = tuple2._2();
                    if (T23 instanceof StreamRefsProtocol.RemoteStreamFailure) {
                        StreamRefsProtocol.RemoteStreamFailure remoteStreamFailure = (StreamRefsProtocol.RemoteStreamFailure)T23;
                        String reason = remoteStreamFailure.msg();
                        State state = $this.state();
                        if (!(state instanceof WeKnowPartner)) {
                            throw new IllegalStateException(new StringBuilder(64).append("[").append($this.stageActorName()).append("] got RemoteStreamFailure(").append(reason).append(") when in state ").append(state).append(", should never happen").toString());
                        }
                        WeKnowPartner weKnowPartner = (WeKnowPartner)state;
                        ActorRef partner = weKnowPartner.partner();
                        $this.verifyPartner(sender, partner);
                        $this.log().debug("[{}] The remote stream has failed, failing (reason: {})", $this.stageActorName(), reason);
                        $this.failStage(new RemoteStreamRefActorTerminatedException(new StringBuilder(36).append("[").append($this.stageActorName()).append("] Remote stream (").append(sender.path()).append(") failed, reason: ").append(reason).toString()));
                        return;
                    }
                }
                if (tuple2 != null) {
                    ActorRef sender = (ActorRef)tuple2._1();
                    T2 T24 = tuple2._2();
                    if (StreamRefsProtocol$Ack$.MODULE$.equals(T24)) {
                        State state = $this.state();
                        if (!(state instanceof WaitingForCancelAck)) {
                            throw new IllegalStateException(new StringBuilder(28).append("[").append($this.stageActorName()).append("] Got an Ack when in state ").append(state).toString());
                        }
                        WaitingForCancelAck waitingForCancelAck = (WaitingForCancelAck)state;
                        ActorRef partner = waitingForCancelAck.partner();
                        Throwable cause = waitingForCancelAck.cause();
                        $this.verifyPartner(sender, partner);
                        $this.log().debug("[{}] Got cancellation ack from remote, canceling", $this.stageActorName());
                        $this.cancelStage(cause);
                        return;
                    }
                }
                if (tuple2 != null && (T2 = tuple2._2()) instanceof Terminated) {
                    WeKnowPartner weKnowPartner;
                    Terminated terminated = (Terminated)T2;
                    ActorRef p = terminated.actor();
                    State state = $this.state();
                    if (state instanceof WeKnowPartner) {
                        weKnowPartner = (WeKnowPartner)state;
                        ActorRef actorRef2 = weKnowPartner.partner();
                        ActorRef actorRef3 = p;
                        if (actorRef2 == null ? actorRef3 != null : !((Object)actorRef2).equals(actorRef3)) {
                            throw new RemoteStreamRefActorTerminatedException(new StringBuilder(118).append("[").append($this.stageActorName()).append("] Received UNEXPECTED Terminated(").append(p).append(") message! ").append("This actor was NOT our trusted remote partner, which was: ").append(weKnowPartner.partner()).append(". Tearing down.").toString());
                        }
                    } else {
                        throw new IllegalStateException(new StringBuilder(72).append("[").append($this.stageActorName()).append("] Unexpected deathwatch message for ").append(p).append(" before we knew partner ref, state ").append(state).toString());
                    }
                    $this.scheduleOnce((Object)SourceRefStageImpl$.MODULE$.TerminationDeadlineTimerKey(), $this.finalTerminationSignalDeadline);
                    $this.log().debug("[{}] Partner terminated, starting delayed shutdown, deadline: [{}]", $this.stageActorName(), $this.finalTerminationSignalDeadline);
                    $this.state_$eq(new UpstreamTerminated(weKnowPartner.partner()));
                    return;
                }
                if (tuple2 != null) {
                    ActorRef sender = (ActorRef)tuple2._1();
                    T2 msg = tuple2._2();
                    throw new IllegalStateException(new StringBuilder(39).append("[").append($this.stageActorName()).append("] Unexpected message in state ").append($this.state()).append(": ").append(msg).append(" from ").append(sender).toString());
                }
                throw new MatchError(tuple2);
            }

            private final void sendDemand$1(ActorRef partner, int addDemand$1) {
                this.localCumulativeDemand_$eq(this.localCumulativeDemand() + (long)addDemand$1);
                this.localRemainingRequested_$eq(this.localRemainingRequested() + addDemand$1);
                StreamRefsProtocol.CumulativeDemand demand = new StreamRefsProtocol.CumulativeDemand(this.localCumulativeDemand());
                partner.$bang(demand, this.selfSender());
                this.scheduleDemandRedelivery();
            }
            {
                State state;
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                StageLogging.$init$(this);
                OutHandler.$init$(this);
                this.streamRefsMaster = (StreamRefsMaster)StreamRefsMaster$.MODULE$.apply(eagerMaterializer$1.system());
                this.settings = eagerMaterializer$1.settings().streamRefSettings();
                this.subscriptionTimeout = inheritedAttributes$1.get(new StreamRefAttributes.SubscriptionTimeout(this.settings.subscriptionTimeout()), ClassTag$.MODULE$.apply(StreamRefAttributes.SubscriptionTimeout.class));
                this.bufferCapacity = inheritedAttributes$1.get(new StreamRefAttributes.BufferCapacity(this.settings.bufferCapacity()), ClassTag$.MODULE$.apply(StreamRefAttributes.BufferCapacity.class)).capacity();
                this.demandRedeliveryInterval = inheritedAttributes$1.get(new StreamRefAttributes.DemandRedeliveryInterval(this.settings.demandRedeliveryInterval()), ClassTag$.MODULE$.apply(StreamRefAttributes.DemandRedeliveryInterval.class)).timeout();
                this.finalTerminationSignalDeadline = inheritedAttributes$1.get(new StreamRefAttributes.FinalTerminationSignalDeadline(this.settings.finalTerminationSignalDeadline()), ClassTag$.MODULE$.apply(StreamRefAttributes.FinalTerminationSignalDeadline.class)).timeout();
                this.stageActorName = this.streamRefsMaster.nextSourceRefStageName();
                this.self = this.getEagerStageActor(eagerMaterializer$1, this.receiveRemoteMessage());
                this.ref = this.self.ref();
                ActorRef actorRef2 = $outer.initialPartnerRef();
                ActorRef actorRef3 = OptionVal$Some$.MODULE$.unapply(actorRef2);
                if (!OptionVal$.MODULE$.isEmpty$extension(actorRef3)) {
                    ActorRef ref = OptionVal$.MODULE$.get$extension(actorRef3);
                    this.self.watch(ref);
                    state = new AwaitingSubscription(ref);
                } else {
                    state = SourceRefStageImpl$AwaitingPartner$.MODULE$;
                }
                this.state = state;
                this.expectingSeqNr = 0L;
                this.localCumulativeDemand = 0L;
                this.localRemainingRequested = 0;
                this.receiveBuffer = FixedSizeBuffer$.MODULE$.apply(this.bufferCapacity);
                this.requestStrategy = SourceRefStageImpl$WatermarkRequestStrategy$.MODULE$.apply(this.receiveBuffer().capacity());
                this.setHandler($outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$receiveRemoteMessage$1$adapted(akka.stream.impl.streamref.SourceRefStageImpl$$anon$1 scala.Tuple2 )}, serializedLambda);
            }
        };
        return new Tuple2<GraphStageLogic, SinkRef<Out>>((GraphStageLogic)((Object)logic), new SinkRefImpl(((ActorRefStage)((Object)logic)).ref()));
    }

    public String toString() {
        return new StringBuilder(3).append(Logging$.MODULE$.simpleName(this.getClass())).append("(").append(this.initialRefName()).append(")}").toString();
    }

    public SourceRefStageImpl(ActorRef initialPartnerRef) {
        this.initialPartnerRef = initialPartnerRef;
        this.out = Outlet$.MODULE$.apply(new StringBuilder(4).append(Logging$.MODULE$.simpleName(this.getClass())).append(".out").toString());
    }

    private static interface ActorRefStage {
        public ActorRef ref();
    }

    private static class AwaitingSubscription
    implements WeKnowPartner,
    Product,
    Serializable {
        private final ActorRef partner;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public ActorRef partner() {
            return this.partner;
        }

        public AwaitingSubscription copy(ActorRef partner) {
            return new AwaitingSubscription(partner);
        }

        public ActorRef copy$default$1() {
            return this.partner();
        }

        @Override
        public String productPrefix() {
            return "AwaitingSubscription";
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
                    return this.partner();
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
            return x$1 instanceof AwaitingSubscription;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "partner";
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
            if (!(object instanceof AwaitingSubscription)) return false;
            boolean bl = true;
            if (!bl) return false;
            AwaitingSubscription awaitingSubscription = (AwaitingSubscription)x$1;
            ActorRef actorRef2 = this.partner();
            ActorRef actorRef3 = awaitingSubscription.partner();
            if (actorRef2 == null) {
                if (actorRef3 != null) {
                    return false;
                }
            } else if (!((Object)actorRef2).equals(actorRef3)) return false;
            if (!awaitingSubscription.canEqual(this)) return false;
            return true;
        }

        public AwaitingSubscription(ActorRef partner) {
            this.partner = partner;
            Product.$init$(this);
        }
    }

    private static final class Running
    implements WeKnowPartner,
    Product,
    Serializable {
        private final ActorRef partner;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public ActorRef partner() {
            return this.partner;
        }

        public Running copy(ActorRef partner) {
            return new Running(partner);
        }

        public ActorRef copy$default$1() {
            return this.partner();
        }

        @Override
        public String productPrefix() {
            return "Running";
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
                    return this.partner();
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
            return x$1 instanceof Running;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "partner";
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
            if (!(object instanceof Running)) return false;
            boolean bl = true;
            if (!bl) return false;
            Running running = (Running)x$1;
            ActorRef actorRef2 = this.partner();
            ActorRef actorRef3 = running.partner();
            if (actorRef2 != null) {
                if (!((Object)actorRef2).equals(actorRef3)) return false;
                return true;
            }
            if (actorRef3 == null) return true;
            return false;
        }

        public Running(ActorRef partner) {
            this.partner = partner;
            Product.$init$(this);
        }
    }

    private static interface State {
    }

    private static final class UpstreamCompleted
    implements WeKnowPartner,
    Product,
    Serializable {
        private final ActorRef partner;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public ActorRef partner() {
            return this.partner;
        }

        public UpstreamCompleted copy(ActorRef partner) {
            return new UpstreamCompleted(partner);
        }

        public ActorRef copy$default$1() {
            return this.partner();
        }

        @Override
        public String productPrefix() {
            return "UpstreamCompleted";
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
                    return this.partner();
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
            return x$1 instanceof UpstreamCompleted;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "partner";
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
            if (!(object instanceof UpstreamCompleted)) return false;
            boolean bl = true;
            if (!bl) return false;
            UpstreamCompleted upstreamCompleted = (UpstreamCompleted)x$1;
            ActorRef actorRef2 = this.partner();
            ActorRef actorRef3 = upstreamCompleted.partner();
            if (actorRef2 != null) {
                if (!((Object)actorRef2).equals(actorRef3)) return false;
                return true;
            }
            if (actorRef3 == null) return true;
            return false;
        }

        public UpstreamCompleted(ActorRef partner) {
            this.partner = partner;
            Product.$init$(this);
        }
    }

    private static final class UpstreamTerminated
    implements State,
    Product,
    Serializable {
        private final ActorRef partner;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ActorRef partner() {
            return this.partner;
        }

        public UpstreamTerminated copy(ActorRef partner) {
            return new UpstreamTerminated(partner);
        }

        public ActorRef copy$default$1() {
            return this.partner();
        }

        @Override
        public String productPrefix() {
            return "UpstreamTerminated";
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
                    return this.partner();
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
            return x$1 instanceof UpstreamTerminated;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "partner";
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
            if (!(object instanceof UpstreamTerminated)) return false;
            boolean bl = true;
            if (!bl) return false;
            UpstreamTerminated upstreamTerminated = (UpstreamTerminated)x$1;
            ActorRef actorRef2 = this.partner();
            ActorRef actorRef3 = upstreamTerminated.partner();
            if (actorRef2 != null) {
                if (!((Object)actorRef2).equals(actorRef3)) return false;
                return true;
            }
            if (actorRef3 == null) return true;
            return false;
        }

        public UpstreamTerminated(ActorRef partner) {
            this.partner = partner;
            Product.$init$(this);
        }
    }

    private static final class WaitingForCancelAck
    implements WeKnowPartner,
    Product,
    Serializable {
        private final ActorRef partner;
        private final Throwable cause;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public ActorRef partner() {
            return this.partner;
        }

        public Throwable cause() {
            return this.cause;
        }

        public WaitingForCancelAck copy(ActorRef partner, Throwable cause) {
            return new WaitingForCancelAck(partner, cause);
        }

        public ActorRef copy$default$1() {
            return this.partner();
        }

        public Throwable copy$default$2() {
            return this.cause();
        }

        @Override
        public String productPrefix() {
            return "WaitingForCancelAck";
        }

        @Override
        public int productArity() {
            return 2;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.partner();
                }
                case 1: {
                    return this.cause();
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
            return x$1 instanceof WaitingForCancelAck;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "partner";
                }
                case 1: {
                    return "cause";
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
            if (!(object instanceof WaitingForCancelAck)) return false;
            boolean bl = true;
            if (!bl) return false;
            WaitingForCancelAck waitingForCancelAck = (WaitingForCancelAck)x$1;
            ActorRef actorRef2 = this.partner();
            ActorRef actorRef3 = waitingForCancelAck.partner();
            if (actorRef2 == null) {
                if (actorRef3 != null) {
                    return false;
                }
            } else if (!((Object)actorRef2).equals(actorRef3)) return false;
            Throwable throwable = this.cause();
            Throwable throwable2 = waitingForCancelAck.cause();
            if (throwable == null) {
                if (throwable2 == null) return true;
                return false;
            } else {
                if (!throwable.equals(throwable2)) return false;
                return true;
            }
        }

        public WaitingForCancelAck(ActorRef partner, Throwable cause) {
            this.partner = partner;
            this.cause = cause;
            Product.$init$(this);
        }
    }

    public static final class WatermarkRequestStrategy
    implements Product,
    Serializable {
        private final int highWatermark;
        private final int lowWatermark;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public int highWatermark() {
            return this.highWatermark;
        }

        public int lowWatermark() {
            return this.lowWatermark;
        }

        public int requestDemand(int remainingRequested) {
            if (remainingRequested < this.lowWatermark()) {
                return this.highWatermark() - remainingRequested;
            }
            return 0;
        }

        public WatermarkRequestStrategy copy(int highWatermark, int lowWatermark) {
            return new WatermarkRequestStrategy(highWatermark, lowWatermark);
        }

        public int copy$default$1() {
            return this.highWatermark();
        }

        public int copy$default$2() {
            return this.lowWatermark();
        }

        @Override
        public String productPrefix() {
            return "WatermarkRequestStrategy";
        }

        @Override
        public int productArity() {
            return 2;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return BoxesRunTime.boxToInteger(this.highWatermark());
                }
                case 1: {
                    return BoxesRunTime.boxToInteger(this.lowWatermark());
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
            return x$1 instanceof WatermarkRequestStrategy;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "highWatermark";
                }
                case 1: {
                    return "lowWatermark";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.highWatermark());
            n = Statics.mix(n, this.lowWatermark());
            return Statics.finalizeHash(n, 2);
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
            if (!(object instanceof WatermarkRequestStrategy)) return false;
            boolean bl = true;
            if (!bl) return false;
            WatermarkRequestStrategy watermarkRequestStrategy = (WatermarkRequestStrategy)x$1;
            if (this.highWatermark() != watermarkRequestStrategy.highWatermark()) return false;
            if (this.lowWatermark() != watermarkRequestStrategy.lowWatermark()) return false;
            return true;
        }

        public WatermarkRequestStrategy(int highWatermark, int lowWatermark) {
            this.highWatermark = highWatermark;
            this.lowWatermark = lowWatermark;
            Product.$init$(this);
            Predef$.MODULE$.require(lowWatermark >= 0, (Function0<Object>)(Function0<String> & Serializable)() -> "lowWatermark must be >= 0");
            Predef$.MODULE$.require(highWatermark >= lowWatermark, (Function0<Object>)(Function0<String> & Serializable)() -> "highWatermark must be >= lowWatermark");
        }

        public WatermarkRequestStrategy(int highWatermark) {
            this(highWatermark, package$.MODULE$.max(1, highWatermark / 2));
        }
    }

    private static interface WeKnowPartner
    extends State {
        public ActorRef partner();
    }
}

