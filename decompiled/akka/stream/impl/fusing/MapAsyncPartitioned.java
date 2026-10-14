/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.impl.BoundedBuffer;
import akka.stream.impl.Buffer;
import akka.stream.impl.Buffer$;
import akka.stream.impl.ChainedBuffer;
import akka.stream.impl.PartitionedBuffer;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.MapAsyncPartitioned$;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.Tuple4;
import scala.collection.Iterator;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.Nothing$;
import scala.runtime.RichInt$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\r\u0005rA\u0002\u001d:\u0011\u0003y\u0014I\u0002\u0004Ds!\u0005q\b\u0012\u0005\u0006'\u0006!\t!\u0016\u0004\u0005-\u0006\u0011q\u000b\u0003\u0005q\u0007\t\u0005\r\u0011\"\u0001r\u0011!)8A!a\u0001\n\u00031\b\u0002C=\u0004\u0005\u0003\u0005\u000b\u0015\u0002:\t\u0011i\u001c!\u00111A\u0005\u0002mD\u0001\u0002`\u0002\u0003\u0002\u0004%\t! \u0005\t\u007f\u000e\u0011\t\u0011)Q\u00059\"Q\u0011\u0011A\u0002\u0003\u0006\u0004%\t!a\u0001\t\u0015\u0005-1A!A!\u0002\u0013\t)\u0001\u0003\u0006\u0002\u000e\r\u0011)\u0019!C\u0001\u0003\u001fA!\"!\t\u0004\u0005\u0003\u0005\u000b\u0011BA\t\u0011\u0019\u00196\u0001\"\u0001\u0002$!9\u0011QF\u0002\u0005\u0002\u0005=\u0002bBA\u0019\u0007\u0011\u0005\u00111\u0007\u0005\b\u0003s\u0019A\u0011IA\u001e\u0011\u001d\tyd\u0001C!\u0003\u0003B\u0011\"!\u0017\u0002\u0005\u0004%\t!a\u0017\t\u0011\u0005\r\u0014\u0001)A\u0005\u0003;B\u0011\"!\u000f\u0002\u0003\u0003%\t)!\u001a\t\u0013\t%\u0018!!A\u0005\u0002\n-\b\"CB\n\u0003\u0005\u0005I\u0011BB\u000b\r\u0019\u0019\u0015HQ \u0002l!Q\u0011q\u0014\r\u0003\u0016\u0004%\t!!)\t\u0015\u0005%\u0006D!E!\u0002\u0013\t\u0019\u000b\u0003\u0006\u0002,b\u0011)\u001a!C\u0001\u0003CC!\"!,\u0019\u0005#\u0005\u000b\u0011BAR\u0011)\ty\u000b\u0007BK\u0002\u0013\u0005\u0011\u0011\u0017\u0005\u000b\u0003wC\"\u0011#Q\u0001\n\u0005M\u0006BCA_1\tU\r\u0011\"\u0001\u0002@\"Q\u00111\u001b\r\u0003\u0012\u0003\u0006I!!1\t\rMCB\u0011AAk\u0011%\t\t\u000f\u0007b\u0001\n\u0013\t\u0019\u000f\u0003\u0005\u0002lb\u0001\u000b\u0011BAs\u0011%\ti\u000f\u0007b\u0001\n\u0013\ty\u000f\u0003\u0005\u0002xb\u0001\u000b\u0011BAy\u0011\u001d\tI\u0010\u0007C!\u0003wD\u0011Ba\u0001\u0019\u0005\u0004%\tE!\u0002\t\u0011\t\u001d\u0001\u0004)A\u0005\u0003kBqA!\u0003\u0019\t\u0003\u0012Y\u0001C\u0005\u0003\u0018a\t\t\u0011\"\u0001\u0003\u001a!I!\u0011\b\r\u0012\u0002\u0013\u0005!1\b\u0005\n\u00053B\u0012\u0013!C\u0001\u00057B\u0011Ba\u0019\u0019#\u0003%\tA!\u001a\t\u0013\tE\u0004$%A\u0005\u0002\tM\u0004\"\u0003B@1\u0005\u0005I\u0011\tBA\u0011%\u0011i\tGA\u0001\n\u0003\t\t\u000bC\u0005\u0003\u0010b\t\t\u0011\"\u0001\u0003\u0012\"I!Q\u0013\r\u0002\u0002\u0013\u0005#q\u0013\u0005\n\u0005KC\u0012\u0011!C\u0001\u0005OC\u0011B!-\u0019\u0003\u0003%\tEa-\t\u0013\t]\u0006$!A\u0005B\te\u0006\"CA 1\u0005\u0005I\u0011\tB^\u0011%\u0011i\fGA\u0001\n\u0003\u0012y,A\nNCB\f5/\u001f8d!\u0006\u0014H/\u001b;j_:,GM\u0003\u0002;w\u00051a-^:j]\u001eT!\u0001P\u001f\u0002\t%l\u0007\u000f\u001c\u0006\u0003}}\naa\u001d;sK\u0006l'\"\u0001!\u0002\t\u0005\\7.\u0019\t\u0003\u0005\u0006i\u0011!\u000f\u0002\u0014\u001b\u0006\u0004\u0018i]=oGB\u000b'\u000f^5uS>tW\rZ\n\u0004\u0003\u0015[\u0005C\u0001$J\u001b\u00059%\"\u0001%\u0002\u000bM\u001c\u0017\r\\1\n\u0005);%AB!osJ+g\r\u0005\u0002M#6\tQJ\u0003\u0002O\u001f\u0006\u0011\u0011n\u001c\u0006\u0002!\u0006!!.\u0019<b\u0013\t\u0011VJ\u0001\u0007TKJL\u0017\r\\5{C\ndW-\u0001\u0004=S:LGOP\u0002\u0001)\u0005\t%A\u0002%pY\u0012,'/F\u0003Y\u0003\u000f\u0019HmE\u0002\u0004\u000bf\u0003BA\u0012.][&\u00111l\u0012\u0002\n\rVt7\r^5p]F\u00022!\u00181c\u001b\u0005q&BA0H\u0003\u0011)H/\u001b7\n\u0005\u0005t&a\u0001+ssB\u00111\r\u001a\u0007\u0001\t\u0015)7A1\u0001g\u0005\u0005y\u0015CA4k!\t1\u0005.\u0003\u0002j\u000f\n9aj\u001c;iS:<\u0007C\u0001$l\u0013\tawIA\u0002B]f\u0004\"A\u00128\n\u0005=<%\u0001B+oSR\f\u0001\"\u001b8d_6LgnZ\u000b\u0002eB\u00111m\u001d\u0003\u0006i\u000e\u0011\rA\u001a\u0002\u0002\u0013\u0006a\u0011N\\2p[&twm\u0018\u0013fcR\u0011Qn\u001e\u0005\bq\u0016\t\t\u00111\u0001s\u0003\rAH%M\u0001\nS:\u001cw.\\5oO\u0002\n\u0001b\\;uO>LgnZ\u000b\u00029\u0006aq.\u001e;h_&twm\u0018\u0013fcR\u0011QN \u0005\bq\"\t\t\u00111\u0001]\u0003%yW\u000f^4pS:<\u0007%A\u0005qCJ$\u0018\u000e^5p]V\u0011\u0011Q\u0001\t\u0004G\u0006\u001dAABA\u0005\u0007\t\u0007aMA\u0001Q\u0003)\u0001\u0018M\u001d;ji&|g\u000eI\u0001\u0003G\n,\"!!\u0005\u0011\r\u0005M\u0011\u0011DA\u000f\u001b\t\t)BC\u0002\u0002\u0018u\nQa\u001d;bO\u0016LA!a\u0007\u0002\u0016\ti\u0011i]=oG\u000e\u000bG\u000e\u001c2bG.\u0004r!a\b\u0004\u0003\u000b\u0011(-D\u0001\u0002\u0003\r\u0019'\r\t\u000b\u000b\u0003;\t)#a\n\u0002*\u0005-\u0002\"\u00029\u000f\u0001\u0004\u0011\b\"\u0002>\u000f\u0001\u0004a\u0006bBA\u0001\u001d\u0001\u0007\u0011Q\u0001\u0005\b\u0003\u001bq\u0001\u0019AA\t\u00035\u0019G.Z1s\u0013:\u001cw.\\5oOR\tQ.A\u0006tKR|U\u000f^4pS:<GcA7\u00026!1\u0011q\u0007\tA\u0002q\u000b\u0011\u0001^\u0001\u0006CB\u0004H.\u001f\u000b\u0004[\u0006u\u0002BBA\u001c#\u0001\u0007A,\u0001\u0005u_N#(/\u001b8h)\t\t\u0019\u0005\u0005\u0003\u0002F\u0005Mc\u0002BA$\u0003\u001f\u00022!!\u0013H\u001b\t\tYEC\u0002\u0002NQ\u000ba\u0001\u0010:p_Rt\u0014bAA)\u000f\u00061\u0001K]3eK\u001aLA!!\u0016\u0002X\t11\u000b\u001e:j]\u001eT1!!\u0015H\u0003-qu\u000e^-fiRCWM]3\u0016\u0005\u0005u\u0003\u0003B/\u0002`\u001dL1!!\u0019_\u0005\u001d1\u0015-\u001b7ve\u0016\fABT8u3\u0016$H\u000b[3sK\u0002*\u0002\"a\u001a\u0003R\nU'\u0011\u001c\u000b\u000b\u0003S\u0012YN!8\u0003`\n\r\b\u0003\u0003\"\u0019\u0005\u001f\u0014\u0019Na6\u0016\u0011\u00055\u0014qPAC\u0003o\u001br\u0001GA8\u0003\u0013\u000by\t\u0005\u0004\u0002\u0014\u0005E\u0014QO\u0005\u0005\u0003g\n)B\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u0004\u0002\"a\u001e\u0002z\u0005u\u00141Q\u0007\u0002{%\u0019\u00111P\u001f\u0003\u0013\u0019cwn^*iCB,\u0007cA2\u0002\u0000\u00111\u0011\u0011\u0011\rC\u0002\u0019\u0014!!\u00138\u0011\u0007\r\f)\t\u0002\u0004\u0002\bb\u0011\rA\u001a\u0002\u0004\u001fV$\bc\u0001$\u0002\f&\u0019\u0011QR$\u0003\u000fA\u0013x\u000eZ;diB!\u0011\u0011SAN\u001d\u0011\t\u0019*a&\u000f\t\u0005%\u0013QS\u0005\u0002\u0011&\u0019\u0011\u0011T$\u0002\u000fA\f7m[1hK&\u0019!+!(\u000b\u0007\u0005eu)A\u0006qCJ\fG\u000e\\3mSNlWCAAR!\r1\u0015QU\u0005\u0004\u0003O;%aA%oi\u0006a\u0001/\u0019:bY2,G.[:nA\u0005a\u0001/\u001a:QCJ$\u0018\u000e^5p]\u0006i\u0001/\u001a:QCJ$\u0018\u000e^5p]\u0002\n1\u0002]1si&$\u0018n\u001c8feV\u0011\u00111\u0017\t\u0007\rj\u000bi(!.\u0011\u0007\r\f9\f\u0002\u0004\u0002:b\u0011\rA\u001a\u0002\n!\u0006\u0014H/\u001b;j_:\fA\u0002]1si&$\u0018n\u001c8fe\u0002\n\u0011AZ\u000b\u0003\u0003\u0003\u0004\u0012BRAb\u0003{\n),a2\n\u0007\u0005\u0015wIA\u0005Gk:\u001cG/[8oeA1\u0011\u0011ZAh\u0003\u0007k!!a3\u000b\u0007\u00055w)\u0001\u0006d_:\u001cWO\u001d:f]RLA!!5\u0002L\n1a)\u001e;ve\u0016\f!A\u001a\u0011\u0015\u0015\u0005]\u0017\u0011\\An\u0003;\fy\u000e\u0005\u0005C1\u0005u\u00141QA[\u0011\u001d\ty*\ta\u0001\u0003GCq!a+\"\u0001\u0004\t\u0019\u000bC\u0004\u00020\u0006\u0002\r!a-\t\u000f\u0005u\u0016\u00051\u0001\u0002B\u0006\u0011\u0011N\\\u000b\u0003\u0003K\u0004b!a\u001e\u0002h\u0006u\u0014bAAu{\t)\u0011J\u001c7fi\u0006\u0019\u0011N\u001c\u0011\u0002\u0007=,H/\u0006\u0002\u0002rB1\u0011qOAz\u0003\u0007K1!!>>\u0005\u0019yU\u000f\u001e7fi\u0006!q.\u001e;!\u0003EIg.\u001b;jC2\fE\u000f\u001e:jEV$Xm]\u000b\u0003\u0003{\u0004B!a\u001e\u0002\u0000&\u0019!\u0011A\u001f\u0003\u0015\u0005#HO]5ckR,7/A\u0003tQ\u0006\u0004X-\u0006\u0002\u0002v\u000511\u000f[1qK\u0002\n1b\u0019:fCR,Gj\\4jGR!!Q\u0002B\n!\u0011\t\u0019Ba\u0004\n\t\tE\u0011Q\u0003\u0002\u0010\u000fJ\f\u0007\u000f[*uC\u001e,Gj\\4jG\"9!QC\u0015A\u0002\u0005u\u0018aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001B2paf,\u0002Ba\u0007\u0003\"\t\u0015\"\u0011\u0006\u000b\u000b\u0005;\u0011YC!\f\u00030\tM\u0002\u0003\u0003\"\u0019\u0005?\u0011\u0019Ca\n\u0011\u0007\r\u0014\t\u0003\u0002\u0004\u0002\u0002*\u0012\rA\u001a\t\u0004G\n\u0015BABADU\t\u0007a\rE\u0002d\u0005S!a!!/+\u0005\u00041\u0007\"CAPUA\u0005\t\u0019AAR\u0011%\tYK\u000bI\u0001\u0002\u0004\t\u0019\u000bC\u0005\u00020*\u0002\n\u00111\u0001\u00032A1aI\u0017B\u0010\u0005OA\u0011\"!0+!\u0003\u0005\rA!\u000e\u0011\u0013\u0019\u000b\u0019Ma\b\u0003(\t]\u0002CBAe\u0003\u001f\u0014\u0019#\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0011\tu\"1\u000bB+\u0005/*\"Aa\u0010+\t\u0005\r&\u0011I\u0016\u0003\u0005\u0007\u0002BA!\u0012\u0003P5\u0011!q\t\u0006\u0005\u0005\u0013\u0012Y%A\u0005v]\u000eDWmY6fI*\u0019!QJ$\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0003R\t\u001d#!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u00121\u0011\u0011Q\u0016C\u0002\u0019$a!a\",\u0005\u00041GABA]W\t\u0007a-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0011\tu\"Q\fB0\u0005C\"a!!!-\u0005\u00041GABADY\t\u0007a\r\u0002\u0004\u0002:2\u0012\rAZ\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00134+!\u00119Ga\u001b\u0003n\t=TC\u0001B5U\u0011\t\u0019L!\u0011\u0005\r\u0005\u0005UF1\u0001g\t\u0019\t9)\fb\u0001M\u00121\u0011\u0011X\u0017C\u0002\u0019\fabY8qs\u0012\"WMZ1vYR$C'\u0006\u0005\u0003v\te$1\u0010B?+\t\u00119H\u000b\u0003\u0002B\n\u0005CABAA]\t\u0007a\r\u0002\u0004\u0002\b:\u0012\rA\u001a\u0003\u0007\u0003ss#\u0019\u00014\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\u0011\u0019\t\u0005\u0003\u0003\u0006\n-UB\u0001BD\u0015\r\u0011IiT\u0001\u0005Y\u0006tw-\u0003\u0003\u0002V\t\u001d\u0015\u0001\u00049s_\u0012,8\r^!sSRL\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004U\nM\u0005\u0002\u0003=2\u0003\u0003\u0005\r!a)\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"A!'\u0011\u000b\tm%\u0011\u00156\u000e\u0005\tu%b\u0001BP\u000f\u0006Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\t\r&Q\u0014\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0003*\n=\u0006c\u0001$\u0003,&\u0019!QV$\u0003\u000f\t{w\u000e\\3b]\"9\u0001pMA\u0001\u0002\u0004Q\u0017A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$BAa!\u00036\"A\u0001\u0010NA\u0001\u0002\u0004\t\u0019+\u0001\u0005iCND7i\u001c3f)\t\t\u0019\u000b\u0006\u0002\u0003\u0004\u00061Q-];bYN$BA!+\u0003B\"9\u0001pNA\u0001\u0002\u0004Q\u0007f\u0001\r\u0003FB!!q\u0019Bf\u001b\t\u0011IMC\u0002\u0003N}JAA!4\u0003J\nY\u0011J\u001c;fe:\fG.\u00119j!\r\u0019'\u0011\u001b\u0003\u0007\u0003\u0003+\"\u0019\u00014\u0011\u0007\r\u0014)\u000e\u0002\u0004\u0002\bV\u0011\rA\u001a\t\u0004G\neGABA]+\t\u0007a\rC\u0004\u0002 V\u0001\r!a)\t\u000f\u0005-V\u00031\u0001\u0002$\"9\u0011qV\u000bA\u0002\t\u0005\bC\u0002$[\u0005\u001f\u00149\u000eC\u0004\u0002>V\u0001\rA!:\u0011\u0013\u0019\u000b\u0019Ma4\u0003X\n\u001d\bCBAe\u0003\u001f\u0014\u0019.A\u0004v]\u0006\u0004\b\u000f\\=\u0016\u0011\t5(q`B\u0006\u0007\u0007!BAa<\u0004\u000eA)aI!=\u0003v&\u0019!1_$\u0003\r=\u0003H/[8o!-1%q_AR\u0003G\u0013Yp!\u0002\n\u0007\texI\u0001\u0004UkBdW\r\u000e\t\u0007\rj\u0013ip!\u0001\u0011\u0007\r\u0014y\u0010\u0002\u0004\u0002\u0002Z\u0011\rA\u001a\t\u0004G\u000e\rAABA]-\t\u0007a\rE\u0005G\u0003\u0007\u0014ip!\u0001\u0004\bA1\u0011\u0011ZAh\u0007\u0013\u00012aYB\u0006\t\u0019\t9I\u0006b\u0001M\"I1q\u0002\f\u0002\u0002\u0003\u00071\u0011C\u0001\u0004q\u0012\u0002\u0004\u0003\u0003\"\u0019\u0005{\u001cIa!\u0001\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\r]\u0001\u0003\u0002BC\u00073IAaa\u0007\u0003\b\n1qJ\u00196fGRD3!\u0001BcQ\r\u0001!Q\u0019")
@InternalApi
public final class MapAsyncPartitioned<In, Out, Partition>
extends GraphStage<FlowShape<In, Out>>
implements Product,
Serializable {
    private final int parallelism;
    private final int perPartition;
    private final Function1<In, Partition> partitioner;
    private final Function2<In, Partition, Future<Out>> f;
    private final Inlet<In> akka$stream$impl$fusing$MapAsyncPartitioned$$in;
    private final Outlet<Out> akka$stream$impl$fusing$MapAsyncPartitioned$$out;
    private final FlowShape<In, Out> shape;

    public static <In, Out, Partition> Option<Tuple4<Object, Object, Function1<In, Partition>, Function2<In, Partition, Future<Out>>>> unapply(MapAsyncPartitioned<In, Out, Partition> x$0) {
        return MapAsyncPartitioned$.MODULE$.unapply(x$0);
    }

    public static <In, Out, Partition> MapAsyncPartitioned<In, Out, Partition> apply(int parallelism, int perPartition, Function1<In, Partition> partitioner, Function2<In, Partition, Future<Out>> f) {
        return MapAsyncPartitioned$.MODULE$.apply(parallelism, perPartition, partitioner, f);
    }

    public static Failure<Nothing$> NotYetThere() {
        return MapAsyncPartitioned$.MODULE$.NotYetThere();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int parallelism() {
        return this.parallelism;
    }

    public int perPartition() {
        return this.perPartition;
    }

    public Function1<In, Partition> partitioner() {
        return this.partitioner;
    }

    public Function2<In, Partition, Future<Out>> f() {
        return this.f;
    }

    public Inlet<In> akka$stream$impl$fusing$MapAsyncPartitioned$$in() {
        return this.akka$stream$impl$fusing$MapAsyncPartitioned$$in;
    }

    public Outlet<Out> akka$stream$impl$fusing$MapAsyncPartitioned$$out() {
        return this.akka$stream$impl$fusing$MapAsyncPartitioned$$out;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.mapAsync().and(Attributes$SourceLocation$.MODULE$.forLambda(this.f()));
    }

    @Override
    public FlowShape<In, Out> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this, inheritedAttributes){
            private final PartitionedBuffer<Partition, Holder<Partition, In, Out>> buffer;
            private final AsyncCallback<Holder<Partition, In, Out>> futureCB;
            private final /* synthetic */ MapAsyncPartitioned $outer;
            private final Attributes inheritedAttributes$1;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            private PartitionedBuffer<Partition, Holder<Partition, In, Out>> buffer() {
                return this.buffer;
            }

            private AsyncCallback<Holder<Partition, In, Out>> futureCB() {
                return this.futureCB;
            }

            public void onPull() {
                this.pushNextIfPossible();
            }

            public void onPush() {
                In elem = this.grab(this.$outer.akka$stream$impl$fusing$MapAsyncPartitioned$$in());
                Partition partition = this.$outer.partitioner().apply(elem);
                Holder<Partition, In, Nothing$> holder = new Holder<Partition, In, Nothing$>(elem, MapAsyncPartitioned$.MODULE$.NotYetThere(), partition, this.futureCB());
                if (!this.buffer().containsPartition(partition)) {
                    if (this.buffer().capacity() == 0) {
                        throw new IllegalStateException(new StringBuilder(45).append("Saw new partition [").append(partition).append("] but no buffer space left").toString());
                    }
                    ChainedBuffer<Holder<Partition, In, Out>> partitionBuffer = this.createNewPartitionBuffer(partition);
                    this.buffer().addPartition(partition, partitionBuffer);
                }
                this.buffer().enqueue(partition, holder);
                this.pullIfNeeded();
            }

            public void onUpstreamFinish() {
                if (this.buffer().isEmpty()) {
                    this.completeStage();
                    return;
                }
            }

            private void dropCompletedThenPushIfPossible(Partition partition) {
                block3: {
                    Option<Holder<Partition, In, Out>> option;
                    block4: {
                        block5: {
                            Try<O> try_;
                            while (true) {
                                Option<Holder<Partition, In, Out>> holderOpt;
                                if (None$.MODULE$.equals(option = (holderOpt = this.buffer().peekPartition(partition)))) {
                                    this.pushNextIfPossible();
                                    break block3;
                                }
                                if (!(option instanceof Some)) break block4;
                                Some some = (Some)option;
                                Holder holder = (Holder)some.value();
                                try_ = holder.outgoing();
                                Failure<Nothing$> failure = MapAsyncPartitioned$.MODULE$.NotYetThere();
                                Try<O> try_2 = try_;
                                if (!(failure != null ? !((Object)failure).equals(try_2) : try_2 != null)) break block5;
                                if (!(try_ instanceof Success)) break;
                                this.buffer().dropOnlyPartitionHead(partition);
                            }
                            if (try_ instanceof Failure) {
                                Failure failure = (Failure)try_;
                                Throwable ex = failure.exception();
                                throw ex;
                            }
                            throw new MatchError(try_);
                        }
                        this.pushNextIfPossible();
                        break block3;
                    }
                    throw new MatchError(option);
                }
            }

            private void pushNextIfPossible() {
                block4: {
                    BoxedUnit boxedUnit;
                    while (true) {
                        if (this.buffer().isEmpty()) {
                            this.pullIfNeeded();
                            boxedUnit = BoxedUnit.UNIT;
                            break block4;
                        }
                        if (this.buffer().peek().outgoing() == MapAsyncPartitioned$.MODULE$.NotYetThere()) {
                            this.pullIfNeeded();
                            boxedUnit = BoxedUnit.UNIT;
                            break block4;
                        }
                        if (!this.isAvailable(this.$outer.akka$stream$impl$fusing$MapAsyncPartitioned$$out())) break;
                        Holder<Partition, In, Out> holder = this.buffer().peek();
                        Out elem = holder.outgoing().get();
                        if (elem != null) {
                            this.push(this.$outer.akka$stream$impl$fusing$MapAsyncPartitioned$$out(), elem);
                            this.buffer().dropHead();
                            this.pullIfNeeded();
                            boxedUnit = BoxedUnit.UNIT;
                            break block4;
                        }
                        this.buffer().dropHead();
                        this.pullIfNeeded();
                    }
                    boxedUnit = BoxedUnit.UNIT;
                }
            }

            private void pullIfNeeded() {
                if (this.isClosed(this.$outer.akka$stream$impl$fusing$MapAsyncPartitioned$$in()) && this.buffer().isEmpty()) {
                    this.completeStage();
                    return;
                }
                if (this.buffer().used() < this.$outer.parallelism() && !this.hasBeenPulled(this.$outer.akka$stream$impl$fusing$MapAsyncPartitioned$$in())) {
                    this.tryPull(this.$outer.akka$stream$impl$fusing$MapAsyncPartitioned$$in());
                    return;
                }
            }

            private ChainedBuffer<Holder<Partition, In, Out>> createNewPartitionBuffer(Partition partition) {
                int tailCapacity = RichInt$.MODULE$.max$extension(Predef$.MODULE$.intWrapper(this.$outer.parallelism() - this.$outer.perPartition()), 1);
                Buffer<T> headBuffer = Buffer$.MODULE$.apply(this.$outer.perPartition(), this.inheritedAttributes$1);
                BoundedBuffer.DynamicQueue<T> tailBuffer = new BoundedBuffer.DynamicQueue<T>(tailCapacity);
                return new ChainedBuffer<Holder<Partition, In, Out>>(headBuffer, tailBuffer, (Function1<Holder, Object> & Serializable)x$1 -> {
                    $anon$1.$anonfun$createNewPartitionBuffer$1(this, partition, x$1);
                    return BoxedUnit.UNIT;
                });
            }

            private void executeFutureTask(Holder<Partition, In, Out> holder, Partition partition) {
                Future<Out> future = this.$outer.f().apply(holder.incoming(), partition);
                holder.clearIncoming();
                boolean bl = false;
                Some some = null;
                Option<Try<Out>> option = future.value();
                if (None$.MODULE$.equals(option)) {
                    future.onComplete(holder, ExecutionContexts$.MODULE$.parasitic());
                    return;
                }
                if (option instanceof Some) {
                    bl = true;
                    some = (Some)option;
                    Try try_ = (Try)some.value();
                    if (try_ instanceof Failure) {
                        Failure failure = (Failure)try_;
                        Throwable ex = failure.exception();
                        throw ex;
                    }
                }
                if (bl) {
                    Try v = (Try)some.value();
                    holder.setOutgoing(v);
                    this.dropCompletedThenPushIfPossible(partition);
                    return;
                }
                throw new MatchError(option);
            }

            public static final /* synthetic */ void $anonfun$futureCB$1($anon$1 $this, Holder holder) {
                Try<O> try_ = holder.outgoing();
                if (try_ instanceof Failure) {
                    Failure failure = (Failure)try_;
                    Throwable ex = failure.exception();
                    $this.failStage(ex);
                    return;
                }
                $this.dropCompletedThenPushIfPossible(holder.partition());
            }

            public static final /* synthetic */ void $anonfun$createNewPartitionBuffer$1($anon$1 $this, Object partition$1, Holder x$1) {
                $this.executeFutureTask(x$1, partition$1);
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
                this.buffer = new PartitionedBuffer<K, V>($outer.parallelism());
                this.futureCB = this.getAsyncCallback((Function1<Holder, Object> & Serializable)holder -> {
                    $anon$1.$anonfun$futureCB$1(this, holder);
                    return BoxedUnit.UNIT;
                });
                this.setHandlers($outer.akka$stream$impl$fusing$MapAsyncPartitioned$$in(), $outer.akka$stream$impl$fusing$MapAsyncPartitioned$$out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$createNewPartitionBuffer$1$adapted(akka.stream.impl.fusing.MapAsyncPartitioned$$anon$1 java.lang.Object akka.stream.impl.fusing.MapAsyncPartitioned$Holder ), $anonfun$futureCB$1$adapted(akka.stream.impl.fusing.MapAsyncPartitioned$$anon$1 akka.stream.impl.fusing.MapAsyncPartitioned$Holder )}, serializedLambda);
            }
        };
    }

    public <In, Out, Partition> MapAsyncPartitioned<In, Out, Partition> copy(int parallelism, int perPartition, Function1<In, Partition> partitioner, Function2<In, Partition, Future<Out>> f) {
        return new MapAsyncPartitioned<In, Out, Partition>(parallelism, perPartition, partitioner, f);
    }

    public <In, Out, Partition> int copy$default$1() {
        return this.parallelism();
    }

    public <In, Out, Partition> int copy$default$2() {
        return this.perPartition();
    }

    public <In, Out, Partition> Function1<In, Partition> copy$default$3() {
        return this.partitioner();
    }

    public <In, Out, Partition> Function2<In, Partition, Future<Out>> copy$default$4() {
        return this.f();
    }

    @Override
    public String productPrefix() {
        return "MapAsyncPartitioned";
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
                return BoxesRunTime.boxToInteger(this.parallelism());
            }
            case 1: {
                return BoxesRunTime.boxToInteger(this.perPartition());
            }
            case 2: {
                return this.partitioner();
            }
            case 3: {
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
        return x$1 instanceof MapAsyncPartitioned;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "parallelism";
            }
            case 1: {
                return "perPartition";
            }
            case 2: {
                return "partitioner";
            }
            case 3: {
                return "f";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.parallelism());
        n = Statics.mix(n, this.perPartition());
        n = Statics.mix(n, Statics.anyHash(this.partitioner()));
        n = Statics.mix(n, Statics.anyHash(this.f()));
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
        if (!(object instanceof MapAsyncPartitioned)) return false;
        boolean bl = true;
        if (!bl) return false;
        MapAsyncPartitioned mapAsyncPartitioned = (MapAsyncPartitioned)x$1;
        if (this.parallelism() != mapAsyncPartitioned.parallelism()) return false;
        if (this.perPartition() != mapAsyncPartitioned.perPartition()) return false;
        Function1<In, Partition> function1 = this.partitioner();
        Function1<In, Partition> function12 = mapAsyncPartitioned.partitioner();
        if (function1 == null) {
            if (function12 != null) {
                return false;
            }
        } else if (!function1.equals(function12)) return false;
        Function2<In, Partition, Future<Out>> function2 = this.f();
        Function2<In, Partition, Future<Out>> function22 = mapAsyncPartitioned.f();
        if (function2 == null) {
            if (function22 == null) return true;
            return false;
        } else {
            if (!function2.equals(function22)) return false;
            return true;
        }
    }

    public MapAsyncPartitioned(int parallelism, int perPartition, Function1<In, Partition> partitioner, Function2<In, Partition, Future<Out>> f) {
        this.parallelism = parallelism;
        this.perPartition = perPartition;
        this.partitioner = partitioner;
        this.f = f;
        Product.$init$(this);
        if (parallelism < 1) {
            throw new IllegalArgumentException("parallelism must be at least 1");
        }
        if (perPartition < 1) {
            throw new IllegalArgumentException("perPartition must be at least 1");
        }
        if (perPartition >= parallelism) {
            throw new IllegalArgumentException("perPartition must be less than parallelism: consider mapAsync instead");
        }
        this.akka$stream$impl$fusing$MapAsyncPartitioned$$in = Inlet$.MODULE$.apply("MapAsyncPartitioned.in");
        this.akka$stream$impl$fusing$MapAsyncPartitioned$$out = Outlet$.MODULE$.apply("MapAsyncPartitioned.out");
        this.shape = new FlowShape<In, Out>(this.akka$stream$impl$fusing$MapAsyncPartitioned$$in(), this.akka$stream$impl$fusing$MapAsyncPartitioned$$out());
    }

    public static final class Holder<P, I, O>
    implements Function1<Try<O>, BoxedUnit> {
        private I incoming;
        private Try<O> outgoing;
        private final P partition;
        private final AsyncCallback<Holder<P, I, O>> cb;

        @Override
        public boolean apply$mcZD$sp(double v1) {
            return Function1.apply$mcZD$sp$(this, v1);
        }

        @Override
        public double apply$mcDD$sp(double v1) {
            return Function1.apply$mcDD$sp$(this, v1);
        }

        @Override
        public float apply$mcFD$sp(double v1) {
            return Function1.apply$mcFD$sp$(this, v1);
        }

        @Override
        public int apply$mcID$sp(double v1) {
            return Function1.apply$mcID$sp$(this, v1);
        }

        @Override
        public long apply$mcJD$sp(double v1) {
            return Function1.apply$mcJD$sp$(this, v1);
        }

        @Override
        public void apply$mcVD$sp(double v1) {
            Function1.apply$mcVD$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZF$sp(float v1) {
            return Function1.apply$mcZF$sp$(this, v1);
        }

        @Override
        public double apply$mcDF$sp(float v1) {
            return Function1.apply$mcDF$sp$(this, v1);
        }

        @Override
        public float apply$mcFF$sp(float v1) {
            return Function1.apply$mcFF$sp$(this, v1);
        }

        @Override
        public int apply$mcIF$sp(float v1) {
            return Function1.apply$mcIF$sp$(this, v1);
        }

        @Override
        public long apply$mcJF$sp(float v1) {
            return Function1.apply$mcJF$sp$(this, v1);
        }

        @Override
        public void apply$mcVF$sp(float v1) {
            Function1.apply$mcVF$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZI$sp(int v1) {
            return Function1.apply$mcZI$sp$(this, v1);
        }

        @Override
        public double apply$mcDI$sp(int v1) {
            return Function1.apply$mcDI$sp$(this, v1);
        }

        @Override
        public float apply$mcFI$sp(int v1) {
            return Function1.apply$mcFI$sp$(this, v1);
        }

        @Override
        public int apply$mcII$sp(int v1) {
            return Function1.apply$mcII$sp$(this, v1);
        }

        @Override
        public long apply$mcJI$sp(int v1) {
            return Function1.apply$mcJI$sp$(this, v1);
        }

        @Override
        public void apply$mcVI$sp(int v1) {
            Function1.apply$mcVI$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZJ$sp(long v1) {
            return Function1.apply$mcZJ$sp$(this, v1);
        }

        @Override
        public double apply$mcDJ$sp(long v1) {
            return Function1.apply$mcDJ$sp$(this, v1);
        }

        @Override
        public float apply$mcFJ$sp(long v1) {
            return Function1.apply$mcFJ$sp$(this, v1);
        }

        @Override
        public int apply$mcIJ$sp(long v1) {
            return Function1.apply$mcIJ$sp$(this, v1);
        }

        @Override
        public long apply$mcJJ$sp(long v1) {
            return Function1.apply$mcJJ$sp$(this, v1);
        }

        @Override
        public void apply$mcVJ$sp(long v1) {
            Function1.apply$mcVJ$sp$(this, v1);
        }

        @Override
        public <A> Function1<A, BoxedUnit> compose(Function1<A, Try<O>> g2) {
            return Function1.compose$(this, g2);
        }

        @Override
        public <A> Function1<Try<O>, A> andThen(Function1<BoxedUnit, A> g2) {
            return Function1.andThen$(this, g2);
        }

        public I incoming() {
            return this.incoming;
        }

        public void incoming_$eq(I x$1) {
            this.incoming = x$1;
        }

        public Try<O> outgoing() {
            return this.outgoing;
        }

        public void outgoing_$eq(Try<O> x$1) {
            this.outgoing = x$1;
        }

        public P partition() {
            return this.partition;
        }

        public AsyncCallback<Holder<P, I, O>> cb() {
            return this.cb;
        }

        public void clearIncoming() {
            this.incoming_$eq(null);
        }

        public void setOutgoing(Try<O> t2) {
            this.outgoing_$eq(t2);
        }

        @Override
        public void apply(Try<O> t2) {
            this.setOutgoing(t2);
            this.cb().invoke(this);
        }

        @Override
        public String toString() {
            return new StringBuilder(12).append("Holder(").append(this.incoming()).append(", ").append(this.outgoing()).append(", ").append(this.partition()).append(")").toString();
        }

        public Holder(I incoming, Try<O> outgoing, P partition, AsyncCallback<Holder<P, I, O>> cb) {
            this.incoming = incoming;
            this.outgoing = outgoing;
            this.partition = partition;
            this.cb = cb;
            Function1.$init$(this);
        }
    }
}

