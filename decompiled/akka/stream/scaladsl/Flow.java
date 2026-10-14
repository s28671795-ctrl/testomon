/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorRef;
import akka.annotation.ApiMayChange;
import akka.event.LogMarker;
import akka.event.LoggingAdapter;
import akka.event.MarkerLoggingAdapter;
import akka.stream.ActorMaterializer;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.BidiShape;
import akka.stream.DelayOverflowStrategy;
import akka.stream.FlowMonitor;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.Materializer;
import akka.stream.OverflowStrategy;
import akka.stream.Shape;
import akka.stream.SinkShape;
import akka.stream.SourceShape;
import akka.stream.SubstreamCancelStrategy;
import akka.stream.ThrottleMode;
import akka.stream.impl.LinearTraversalBuilder;
import akka.stream.impl.LinearTraversalBuilder$;
import akka.stream.impl.TraversalBuilder;
import akka.stream.impl.TraversalBuilder$;
import akka.stream.scaladsl.DelayStrategy;
import akka.stream.scaladsl.Flow$;
import akka.stream.scaladsl.FlowOps;
import akka.stream.scaladsl.FlowOpsMat;
import akka.stream.scaladsl.FlowWithContext;
import akka.stream.scaladsl.Keep$;
import akka.stream.scaladsl.RunnableGraph;
import akka.stream.scaladsl.Sink;
import akka.stream.scaladsl.Sink$;
import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.Source$;
import akka.stream.scaladsl.SubFlow;
import akka.util.Timeout;
import java.io.Serializable;
import org.reactivestreams.Processor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005\u0015Md\u0001\u0002\u001c8\u0005yB\u0001B\u0019\u0001\u0003\u0006\u0004%\te\u0019\u0005\tU\u0002\u0011\t\u0011)A\u0005I\"A1\u000e\u0001BC\u0002\u0013\u0005C\u000e\u0003\u0005n\u0001\t\u0005\t\u0015!\u0003]\u0011\u0015q\u0007\u0001\"\u0001p\u0011\u0015\u0019\b\u0001\"\u0011u\u000b\u0019\t\t\u0001\u0001\u0011\u0002\u0004\u00151\u00111\u0005\u0001!\u0003K)a!a\r\u0001A\u0005URABA\u001e\u0001\u0001\ni\u0004\u0003\u0005\u0002F\u0001!\t!OA$\u0011\u001d\ty\u0005\u0001C!\u0003#Bq!!\u001c\u0001\t\u0003\ny\u0007C\u0004\u0002\u0016\u0002!\t!a&\t\u000f\u00055\u0006\u0001\"\u0001\u00020\"9\u0011q\u0019\u0001\u0005B\u0005%\u0007bBAo\u0001\u0011\u0005\u0011q\u001c\u0005\b\u0003{\u0004A\u0011AA\u0000\u0011\u001d\u0011\u0019\u0002\u0001C\u0001\u0005+Aq!!@\u0001\t\u0003\u0011i\u0003C\u0004\u0003\u0014\u0001!\tAa\u0014\t\u000f\tE\u0004\u0001\"\u0011\u0003t!9!\u0011\u0011\u0001\u0005B\t\r\u0005b\u0002BD\u0001\u0011\u0005#\u0011\u0012\u0005\b\u0005\u001f\u0003A\u0011\tBI\u0011\u001d\u0011y\t\u0001C!\u0005'CqAa$\u0001\t\u0003\u0012I\nC\u0004\u0003(\u0002!\tA!+\t\u000f\t5\u0007\u0001\"\u0001\u0003P\"9!Q\u001d\u0001\u0005\u0002\t\u001d\bbBB\t\u0001\u0011\u000511C\u0004\b\u0007S9\u0004\u0012AB\u0016\r\u00191t\u0007#\u0001\u0004.!1a.\tC\u0001\u0007_A\u0011b!\r\"\u0005\u0004%\t!O2\t\u000f\rM\u0012\u0005)A\u0005I\"A1QG\u0011!\u0002\u0013\u00199\u0004C\u0004\u0004:\u0005\"\taa\u000f\t\u000f\r]\u0013\u0005\"\u0001\u0004Z!911O\u0011\u0005\u0002\rU\u0004bBB@C\u0011\u00051\u0011\u0011\u0005\b\u0007/\u000bC\u0011ABM\u0011\u001d\u0019\u0019,\tC\u0001\u0007kCqaa7\"\t\u0003\u0019i\u000eC\u0004\u0005\u0012\u0005\"\t\u0001b\u0005\t\u000f\u0011u\u0012\u0005\"\u0001\u0005@!9AqN\u0011\u0005\u0002\u0011E\u0004b\u0002CNC\u0011\u0005AQ\u0014\u0005\b\t\u0013\fC\u0011\u0001Cf\u0011\u001d!\t0\tC\u0001\tgDq!b\u0007\"\t\u0003)i\u0002C\u0004\u00068\u0005\"\t!\"\u000f\t\u000f\u0015U\u0013\u0005\"\u0001\u0006X\t!a\t\\8x\u0015\tA\u0014(\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\tQ4(\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002y\u0005!\u0011m[6b\u0007\u0001)Ba\u00101M-N!\u0001\u0001\u0011$Y!\t\tE)D\u0001C\u0015\u0005\u0019\u0015!B:dC2\f\u0017BA#C\u0005\u0019\te.\u001f*fMB!q\t\u0013&V\u001b\u00059\u0014BA%8\u0005)1En\\<PaNl\u0015\r\u001e\t\u0003\u00172c\u0001\u0001\u0002\u0004N\u0001\u0011\u0015\rA\u0014\u0002\u0004\u001fV$\u0018CA(S!\t\t\u0005+\u0003\u0002R\u0005\n9aj\u001c;iS:<\u0007CA!T\u0013\t!&IA\u0002B]f\u0004\"a\u0013,\u0005\r]\u0003AQ1\u0001O\u0005\ri\u0015\r\u001e\t\u00053jcV+D\u0001:\u0013\tY\u0016HA\u0003He\u0006\u0004\b\u000e\u0005\u0003Z;~S\u0015B\u00010:\u0005%1En\\<TQ\u0006\u0004X\r\u0005\u0002LA\u00121\u0011\r\u0001EC\u00029\u0013!!\u00138\u0002!Q\u0014\u0018M^3sg\u0006d')^5mI\u0016\u0014X#\u00013\u0011\u0005\u0015DW\"\u00014\u000b\u0005\u001dL\u0014\u0001B5na2L!!\u001b4\u0003-1Kg.Z1s)J\fg/\u001a:tC2\u0014U/\u001b7eKJ\f\u0011\u0003\u001e:bm\u0016\u00148/\u00197Ck&dG-\u001a:!\u0003\u0015\u0019\b.\u00199f+\u0005a\u0016AB:iCB,\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0004aF\u0014\b#B$\u0001?*+\u0006\"\u00022\u0006\u0001\u0004!\u0007\"B6\u0006\u0001\u0004a\u0016\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003U\u0004\"A^?\u000f\u0005]\\\bC\u0001=C\u001b\u0005I(B\u0001>>\u0003\u0019a$o\\8u}%\u0011APQ\u0001\u0007!J,G-\u001a4\n\u0005y|(AB*ue&twM\u0003\u0002}\u0005\n!!+\u001a9s+\u0011\t)!!\b\u0011\u0011\u001d\u0003\u0011qAA\u000e\u0003CQ3aXA\u0005W\t\tY\u0001\u0005\u0003\u0002\u000e\u0005]QBAA\b\u0015\u0011\t\t\"a\u0005\u0002\u0013Ut7\r[3dW\u0016$'bAA\u000b\u0005\u0006Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005e\u0011q\u0002\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0007cA&\u0002\u001e\u00119\u0011qD\u0004\u0005\u0006\u0004q%!A(+\u0007U\u000bIAA\u0004SKB\u0014X*\u0019;\u0016\r\u0005\u001d\u00121FA\u0018!!9\u0005!a\u0002\u0002*\u00055\u0002cA&\u0002,\u00119\u0011q\u0004\u0005\u0005\u0006\u0004q\u0005cA&\u00020\u00119\u0011\u0011\u0007\u0005\u0005\u0006\u0004q%!A'\u0003\r\rcwn]3e!\u001d9\u0015qGA\u0004\u0003CI1!!\u000f8\u0005\u0011\u0019\u0016N\\6\u0003\u0013\rcwn]3e\u001b\u0006$X\u0003BA \u0003\u0007\u0002raRA\u001c\u0003\u000f\t\t\u0005E\u0002L\u0003\u0007\"q!!\r\u000b\t\u000b\u0007a*\u0001\u0006jg&#WM\u001c;jif,\"!!\u0013\u0011\u0007\u0005\u000bY%C\u0002\u0002N\t\u0013qAQ8pY\u0016\fg.A\u0002wS\u0006,b!a\u0015\u0002\\\u0005%D\u0003BA+\u0003?\u0002R!a\u0016\b\u00033j\u0011\u0001\u0001\t\u0004\u0017\u0006mCABA/\u0019\t\u0007aJA\u0001U\u0011\u001d\t\t\u0007\u0004a\u0001\u0003G\nAA\u001a7poB1\u0011LWA3\u0003O\u0002R!W/K\u00033\u00022aSA5\t\u0019\tY\u0007\u0004b\u0001\u001d\n!Q*\u0019;3\u0003\u00191\u0018.Y'biVA\u0011\u0011OA=\u0003\u001b\u000bi\b\u0006\u0003\u0002t\u0005=E\u0003BA;\u0003\u0003\u0003ra\u0012\u0001`\u0003o\nY\bE\u0002L\u0003s\"a!!\u0018\u000e\u0005\u0004q\u0005cA&\u0002~\u00111\u0011qP\u0007C\u00029\u0013A!T1ug!9\u00111Q\u0007A\u0002\u0005\u0015\u0015aB2p[\nLg.\u001a\t\t\u0003\u0006\u001dU+a#\u0002|%\u0019\u0011\u0011\u0012\"\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004cA&\u0002\u000e\u00121\u00111N\u0007C\u00029Cq!!\u0019\u000e\u0001\u0004\t\t\n\u0005\u0004Z5\u0006M\u00151\u0012\t\u00063vS\u0015qO\u0001\u0003i>,B!!'\u0002,R!\u00111TAO!\u00159\u0015qG0V\u0011\u001d\tyJ\u0004a\u0001\u0003C\u000bAa]5oWB1\u0011LWAR\u0003S\u0003B!WAS\u0015&\u0019\u0011qU\u001d\u0003\u0013MKgn[*iCB,\u0007cA&\u0002,\u00121\u00111\u000e\bC\u00029\u000bQ\u0001^8NCR,b!!-\u0002B\u0006eF\u0003BAZ\u0003\u0007$B!!.\u0002<B1q)a\u000e`\u0003o\u00032aSA]\t\u0019\tyh\u0004b\u0001\u001d\"9\u00111Q\bA\u0002\u0005u\u0006\u0003C!\u0002\bV\u000by,a.\u0011\u0007-\u000b\t\r\u0002\u0004\u0002l=\u0011\rA\u0014\u0005\b\u0003?{\u0001\u0019AAc!\u0019I&,a)\u0002@\u0006!R.\u00199NCR,'/[1mSj,GMV1mk\u0016,B!a3\u0002RR!\u0011QZAj!\u0019\t9\u0006\u0003&\u0002PB\u00191*!5\u0005\r\u0005-\u0004C1\u0001O\u0011\u001d\t)\u000e\u0005a\u0001\u0003/\f\u0011A\u001a\t\u0007\u0003\u0006eW+a4\n\u0007\u0005m'IA\u0005Gk:\u001cG/[8oc\u0005q\u0001O]3NCR,'/[1mSj,GCAAq)\u0011\t\u0019/a=\u0011\r\u0005\u000b)/VAu\u0013\r\t9O\u0011\u0002\u0007)V\u0004H.\u001a\u001a\u0011\r\u0005]\u0003BSAv!\u0011\ti/a<\u000e\u0003mJ1!!=<\u0005\u001dqu\u000e^+tK\u0012Dq!!>\u0012\u0001\b\t90\u0001\u0007nCR,'/[1mSj,'\u000fE\u0002Z\u0003sL1!a?:\u00051i\u0015\r^3sS\u0006d\u0017N_3s\u0003\u0011Qw.\u001b8\u0016\t\t\u0005!\u0011\u0003\u000b\u0005\u0005\u0007\u0011I\u0001\u0005\u0003H\u0005\u000b)\u0016b\u0001B\u0004o\ti!+\u001e8oC\ndWm\u0012:ba\"Dq!!\u0019\u0013\u0001\u0004\u0011Y\u0001\u0005\u0004Z5\n5!q\u0002\t\u00053vSu\fE\u0002L\u0005#!a!a\u001b\u0013\u0005\u0004q\u0015a\u00026pS:l\u0015\r^\u000b\u0007\u0005/\u00119Ca\b\u0015\t\te!\u0011\u0006\u000b\u0005\u00057\u0011\t\u0003E\u0003H\u0005\u000b\u0011i\u0002E\u0002L\u0005?!a!a \u0014\u0005\u0004q\u0005bBAB'\u0001\u0007!1\u0005\t\t\u0003\u0006\u001dUK!\n\u0003\u001eA\u00191Ja\n\u0005\r\u0005-4C1\u0001O\u0011\u001d\t\tg\u0005a\u0001\u0005W\u0001b!\u0017.\u0003\u000e\t\u0015R\u0003\u0003B\u0018\u0005k\u0011YD!\u0014\u0015\t\tE\"q\b\t\b\u000f\u0002\u0011\u0019D!\u000fV!\rY%Q\u0007\u0003\u0007\u0005o!\"\u0019\u0001(\u0003\u0005%\u0013\u0004cA&\u0003<\u00111!Q\b\u000bC\u00029\u0013!aT\u0019\t\u000f\t\u0005C\u00031\u0001\u0003D\u0005!!-\u001b3j!\u0019I&L!\u0012\u0003LAI\u0011La\u0012K\u0005s\u0011\u0019dX\u0005\u0004\u0005\u0013J$!\u0003\"jI&\u001c\u0006.\u00199f!\rY%Q\n\u0003\u0007\u0003W\"\"\u0019\u0001(\u0016\u0015\tE#\u0011\fB/\u0005S\u0012\t\u0007\u0006\u0003\u0003T\t-D\u0003\u0002B+\u0005G\u0002\u0002b\u0012\u0001\u0003X\tm#q\f\t\u0004\u0017\neCA\u0002B\u001c+\t\u0007a\nE\u0002L\u0005;\"aA!\u0010\u0016\u0005\u0004q\u0005cA&\u0003b\u00111\u0011\u0011G\u000bC\u00029Cq!a!\u0016\u0001\u0004\u0011)\u0007\u0005\u0005B\u0003\u000f+&q\rB0!\rY%\u0011\u000e\u0003\u0007\u0003W*\"\u0019\u0001(\t\u000f\t\u0005S\u00031\u0001\u0003nA1\u0011L\u0017B8\u0005O\u0002\u0012\"\u0017B$\u0015\nm#qK0\u0002\u001d]LG\u000f[!uiJL'-\u001e;fgR!!Q\u000fB<!\u0011\t9f\u0002&\t\u000f\ted\u00031\u0001\u0003|\u0005!\u0011\r\u001e;s!\rI&QP\u0005\u0004\u0005\u007fJ$AC!uiJL'-\u001e;fg\u0006i\u0011\r\u001a3BiR\u0014\u0018NY;uKN$BA!\u001e\u0003\u0006\"9!\u0011P\fA\u0002\tm\u0014!\u00028b[\u0016$G\u0003\u0002B;\u0005\u0017CaA!$\u0019\u0001\u0004)\u0018\u0001\u00028b[\u0016\fQ!Y:z]\u000e,\"A!\u001e\u0015\t\tU$Q\u0013\u0005\u0007\u0005/S\u0002\u0019A;\u0002\u0015\u0011L7\u000f]1uG\",'\u000f\u0006\u0004\u0003v\tm%Q\u0014\u0005\u0007\u0005/[\u0002\u0019A;\t\u000f\t}5\u00041\u0001\u0003\"\u0006y\u0011N\u001c9vi\n+hMZ3s'&TX\rE\u0002B\u0005GK1A!*C\u0005\rIe\u000e^\u0001\beVtw+\u001b;i+\u0019\u0011YKa-\u0003:R1!Q\u0016B_\u0005\u0013$BAa,\u0003<B9\u0011)!:\u00032\n]\u0006cA&\u00034\u00121!Q\u0017\u000fC\u00029\u0013A!T1ucA\u00191J!/\u0005\r\u0005-DD1\u0001O\u0011\u001d\t)\u0010\ba\u0002\u0003oDqAa0\u001d\u0001\u0004\u0011\t-\u0001\u0004t_V\u00148-\u001a\t\u00073j\u0013\u0019M!-\u0011\te\u0013)mX\u0005\u0004\u0005\u000fL$aC*pkJ\u001cWm\u00155ba\u0016Dq!a(\u001d\u0001\u0004\u0011Y\r\u0005\u0004Z5\u0006\r&qW\u0001\fi>\u0004&o\\2fgN|'/\u0006\u0002\u0003RB)qI!\u0002\u0003TBA!Q\u001bBp\u0003\u000f\u0011\u0019/\u0004\u0002\u0003X*!!\u0011\u001cBn\u0003=\u0011X-Y2uSZ,7\u000f\u001e:fC6\u001c(B\u0001Bo\u0003\ry'oZ\u0005\u0005\u0005C\u00149NA\u0005Qe>\u001cWm]:pe*\u001a!*!\u0003\u0002#\u0005\u001ch\t\\8x/&$\bnQ8oi\u0016DH/\u0006\u0005\u0003j\nU(1`B\u0001)\u0011\u0011Yoa\u0003\u0015\t\t58Q\u0001\t\f\u000f\n=(1\u001fB}\u0015\n}X+C\u0002\u0003r^\u0012qB\u00127po^KG\u000f[\"p]R,\u0007\u0010\u001e\t\u0004\u0017\nUHA\u0002B|=\t\u0007aJA\u0001V!\rY%1 \u0003\u0007\u0005{t\"\u0019\u0001(\u0003\t\r#\b0\u0016\t\u0004\u0017\u000e\u0005AABB\u0002=\t\u0007aJ\u0001\u0004Dib|U\u000f\u001e\u0005\b\u0007\u000fq\u0002\u0019AB\u0005\u00039)\u0007\u0010\u001e:bGR\u001cuN\u001c;fqR\u0004b!QAm\u0015\n}\bbBB\u0007=\u0001\u00071qB\u0001\u0010G>dG.\u00199tK\u000e{g\u000e^3yiBA\u0011)a\"\u0003t\nex,\u0001\u0004bg*\u000bg/Y\u000b\u0005\u0007+\u0019\u0019#\u0006\u0002\u0004\u0018AQ1\u0011DB\u0010\u0007C\u0011\u0019/!\t\u000e\u0005\rm!bAB\u000fs\u00059!.\u0019<bINd\u0017b\u0001\u001c\u0004\u001cA\u00191ja\t\u0005\u000f\r\u0015rD1\u0001\u0004(\t\u0019!*\u00138\u0012\u0005={\u0016\u0001\u0002$m_^\u0004\"aR\u0011\u0014\u0005\u0005\u0002ECAB\u0016\u0003aIG-\u001a8uSRLHK]1wKJ\u001c\u0018\r\u001c\"vS2$WM]\u0001\u001aS\u0012,g\u000e^5usR\u0013\u0018M^3sg\u0006d')^5mI\u0016\u0014\b%\u0001\u0005jI\u0016tG/\u001b;z!\u00199\u0005A\u0015*\u0002l\u0006iaM]8n!J|7-Z:t_J,ba!\u0010\u0004D\r%C\u0003BB \u0007\u0017\u0002\u0002b\u0012\u0001\u0004B\r\u001d\u00131\u001e\t\u0004\u0017\u000e\rCABB#M\t\u0007aJA\u0001J!\rY5\u0011\n\u0003\u0007\u0003?1#\u0019\u0001(\t\u000f\r5c\u00051\u0001\u0004P\u0005\u0001\u0002O]8dKN\u001cxN\u001d$bGR|'/\u001f\t\u0006\u0003\u000eE3QK\u0005\u0004\u0007'\u0012%!\u0003$v]\u000e$\u0018n\u001c81!!\u0011)Na8\u0004B\r\u001d\u0013\u0001\u00054s_6\u0004&o\\2fgN|'/T1u+!\u0019Yf!\u0019\u0004f\r%D\u0003BB/\u0007W\u0002\u0002b\u0012\u0001\u0004`\r\r4q\r\t\u0004\u0017\u000e\u0005DABB#O\t\u0007a\nE\u0002L\u0007K\"a!a\b(\u0005\u0004q\u0005cA&\u0004j\u00111\u0011\u0011G\u0014C\u00029Cqa!\u0014(\u0001\u0004\u0019i\u0007E\u0003B\u0007#\u001ay\u0007E\u0004B\u0003K\u001c\tha\u001a\u0011\u0011\tU'q\\B0\u0007G\nQ!\u00199qYf,Baa\u001e\u0004~U\u00111\u0011\u0010\t\t\u000f\u0002\u0019Yha\u001f\u0002lB\u00191j! \u0005\r\u0005u\u0003F1\u0001O\u000311'o\\7Gk:\u001cG/[8o+\u0019\u0019\u0019i!#\u0004\u0010R!1QQBJ!!9\u0005aa\"\u0004\u000e\u0006-\bcA&\u0004\n\u0012111R\u0015C\u00029\u0013\u0011!\u0011\t\u0004\u0017\u000e=EABBIS\t\u0007aJA\u0001C\u0011\u001d\t).\u000ba\u0001\u0007+\u0003r!QAm\u0007\u000f\u001bi)A\u0005ge>lwI]1qQVA11TBQ\u0007K\u001bI\u000b\u0006\u0003\u0004\u001e\u000e-\u0006\u0003C$\u0001\u0007?\u001b\u0019ka*\u0011\u0007-\u001b\t\u000b\u0002\u0004\u0004F)\u0012\rA\u0014\t\u0004\u0017\u000e\u0015FABA\u0010U\t\u0007a\nE\u0002L\u0007S#a!!\r+\u0005\u0004q\u0005bBBWU\u0001\u00071qV\u0001\u0002OB1\u0011LWBY\u0007O\u0003b!W/\u0004 \u000e\r\u0016\u0001\u00054s_6l\u0015\r^3sS\u0006d\u0017N_3s+!\u00199l!0\u0004B\u000eEG\u0003BB]\u0007'\u0004\u0002b\u0012\u0001\u0004<\u000e}61\u0019\t\u0004\u0017\u000euFABA/W\t\u0007a\nE\u0002L\u0007\u0003$aAa>,\u0005\u0004q\u0005CBBc\u0007\u0017\u001cy-\u0004\u0002\u0004H*\u00191\u0011\u001a\"\u0002\u0015\r|gnY;se\u0016tG/\u0003\u0003\u0004N\u000e\u001d'A\u0002$viV\u0014X\rE\u0002L\u0007#$a!!\r,\u0005\u0004q\u0005bBBkW\u0001\u00071q[\u0001\bM\u0006\u001cGo\u001c:z!%\t\u0015qQA|\u0005w\u001aI\u000e\u0005\u0005H\u0001\rm6qXBh\u0003\u0015\u0019X\r^;q+!\u0019yn!:\u0004j\u000e=H\u0003BBq\u0007c\u0004\u0002b\u0012\u0001\u0004d\u000e\u001d81\u001e\t\u0004\u0017\u000e\u0015HABA/Y\t\u0007a\nE\u0002L\u0007S$aAa>-\u0005\u0004q\u0005CBBc\u0007\u0017\u001ci\u000fE\u0002L\u0007_$a!!\r-\u0005\u0004q\u0005bBBkY\u0001\u000711\u001f\t\n\u0003\u0006\u001d5Q\u001fB>\u0007w\u00042!WB|\u0013\r\u0019I0\u000f\u0002\u0012\u0003\u000e$xN]'bi\u0016\u0014\u0018.\u00197ju\u0016\u0014\b\u0003C$\u0001\u0007G\u001c9o!<)\u00171\u001ay\u0010\"\u0002\u0005\b\u0011-AQ\u0002\t\u0004\u0003\u0012\u0005\u0011b\u0001C\u0002\u0005\nQA-\u001a9sK\u000e\fG/\u001a3\u0002\u000f5,7o]1hK\u0006\u0012A\u0011B\u0001\u001f+N,\u0007e\n4s_6l\u0015\r^3sS\u0006d\u0017N_3sO\u0001Jgn\u001d;fC\u0012\fQa]5oG\u0016\f#\u0001b\u0004\u0002\u000bIrcG\f\u0019\u0002#\u0019\u0014x.\\*j].\fe\u000eZ*pkJ\u001cW-\u0006\u0004\u0005\u0016\u0011mAq\u0004\u000b\u0007\t/!\t\u0003b\f\u0011\u0011\u001d\u0003A\u0011\u0004C\u000f\u0003W\u00042a\u0013C\u000e\t\u0019\u0019)%\fb\u0001\u001dB\u00191\nb\b\u0005\r\u0005}QF1\u0001O\u0011\u001d\ty*\fa\u0001\tG\u0001D\u0001\"\n\u0005,A1\u0011L\u0017C\u0014\tS\u0001R!WAS\t3\u00012a\u0013C\u0016\t-!i\u0003\"\t\u0002\u0002\u0003\u0005)\u0011\u0001(\u0003\u0007}##\u0007C\u0004\u0003@6\u0002\r\u0001\"\r1\t\u0011MB\u0011\b\t\u00073j#)\u0004b\u000e\u0011\u000be\u0013)\r\"\b\u0011\u0007-#I\u0004B\u0006\u0005<\u0011=\u0012\u0011!A\u0001\u0006\u0003q%aA0%g\u0005!bM]8n'&t7.\u00118e'>,(oY3NCR,B\u0002\"\u0011\u0005J\u00115C\u0011\fC0\t#\"b\u0001b\u0011\u0005d\u0011%D\u0003\u0002C#\t'\u0002\u0002b\u0012\u0001\u0005H\u0011-Cq\n\t\u0004\u0017\u0012%CABB#]\t\u0007a\nE\u0002L\t\u001b\"a!a\b/\u0005\u0004q\u0005cA&\u0005R\u00111\u0011\u0011\u0007\u0018C\u00029Cq!a!/\u0001\u0004!)\u0006E\u0005B\u0003\u000f#9\u0006\"\u0018\u0005PA\u00191\n\"\u0017\u0005\r\u0011mcF1\u0001O\u0005\ti\u0015\u0007E\u0002L\t?\"a\u0001\"\u0019/\u0005\u0004q%AA'3\u0011\u001d\tyJ\fa\u0001\tK\u0002b!\u0017.\u0005h\u0011]\u0003#B-\u0002&\u0012\u001d\u0003b\u0002B`]\u0001\u0007A1\u000e\t\u00073j#i\u0007\"\u0018\u0011\u000be\u0013)\rb\u0013\u00021\u0019\u0014x.\\*j].\fe\u000eZ*pkJ\u001cWmQ8va2,G-\u0006\u0004\u0005t\u0011eDQ\u0010\u000b\u0007\tk\"y\b\"$\u0011\u0011\u001d\u0003Aq\u000fC>\u0003W\u00042a\u0013C=\t\u0019\u0019)e\fb\u0001\u001dB\u00191\n\" \u0005\r\u0005}qF1\u0001O\u0011\u001d\tyj\fa\u0001\t\u0003\u0003D\u0001b!\u0005\nB1\u0011L\u0017CC\t\u000f\u0003R!WAS\to\u00022a\u0013CE\t-!Y\tb \u0002\u0002\u0003\u0005)\u0011\u0001(\u0003\u0007}#C\u0007C\u0004\u0003@>\u0002\r\u0001b$1\t\u0011EEq\u0013\t\u00073j#\u0019\n\"&\u0011\u000be\u0013)\rb\u001f\u0011\u0007-#9\nB\u0006\u0005\u001a\u00125\u0015\u0011!A\u0001\u0006\u0003q%aA0%k\u0005YbM]8n'&t7.\u00118e'>,(oY3D_V\u0004H.\u001a3NCR,B\u0002b(\u0005(\u0012-Fq\u0017C^\t_#b\u0001\")\u0005>\u0012\rG\u0003\u0002CR\tc\u0003\u0002b\u0012\u0001\u0005&\u0012%FQ\u0016\t\u0004\u0017\u0012\u001dFABB#a\t\u0007a\nE\u0002L\tW#a!a\b1\u0005\u0004q\u0005cA&\u00050\u00121\u0011\u0011\u0007\u0019C\u00029Cq!a!1\u0001\u0004!\u0019\fE\u0005B\u0003\u000f#)\f\"/\u0005.B\u00191\nb.\u0005\r\u0011m\u0003G1\u0001O!\rYE1\u0018\u0003\u0007\tC\u0002$\u0019\u0001(\t\u000f\u0005}\u0005\u00071\u0001\u0005@B1\u0011L\u0017Ca\tk\u0003R!WAS\tKCqAa01\u0001\u0004!)\r\u0005\u0004Z5\u0012\u001dG\u0011\u0018\t\u00063\n\u0015G\u0011V\u0001\tY\u0006T\u00180\u00138jiVAAQ\u001aCj\t/$Y\u000e\u0006\u0004\u0005P\u0012uGQ\u001d\t\t\u000f\u0002!\t\u000e\"6\u0005ZB\u00191\nb5\u0005\r\r\u0015\u0013G1\u0001O!\rYEq\u001b\u0003\u0007\u0003?\t$\u0019\u0001(\u0011\u0007-#Y\u000e\u0002\u0004\u00022E\u0012\rA\u0014\u0005\b\t?\f\u0004\u0019\u0001Cq\u0003-1Gn\\<GC\u000e$xN]=\u0011\u000f\u0005\u000bI\u000e\"5\u0005dB11QYBf\t\u001fDq\u0001b:2\u0001\u0004!I/\u0001\u0005gC2d'-Y2l!\u0015\t5\u0011\u000bCmQ-\t4q C\u0003\t[$Y\u0001\"\u0004\"\u0005\u0011=\u0018A\\+tK\u0002:c\t\\8x]\u0019,H/\u001e:f\r2|wo\n\u0011j]\u0002\u001aw.\u001c2j]\u0006$\u0018n\u001c8!o&$\b\u000e\t9sK\u001aL\u00070\u00118e)\u0006LG\u000eK\u0019*A%t7\u000f^3bI2\u00023/Z3!A\u001a,H/\u001e:f\r2|w\u000f\u0019\u0011pa\u0016\u0014\u0018\r^8sA\u0011|7m\u001d\u0011g_J\u0004C-\u001a;bS2\u001c\u0018!\u00047bufLe.\u001b;Bgft7-\u0006\u0005\u0005v\u0012mHq`C\u0006)\u0011!90\"\u0004\u0011\u0011\u001d\u0003A\u0011 C\u007f\u000b\u0003\u00012a\u0013C~\t\u0019\u0019)E\rb\u0001\u001dB\u00191\nb@\u0005\r\u0005}!G1\u0001O!\u0019\u0019)ma3\u0006\u0004A)\u0011)\"\u0002\u0006\n%\u0019Qq\u0001\"\u0003\r=\u0003H/[8o!\rYU1\u0002\u0003\u0007\u0003c\u0011$\u0019\u0001(\t\u000f\u0011}'\u00071\u0001\u0006\u0010A)\u0011i!\u0015\u0006\u0012A11QYBf\u000b'\u0001\u0002b\u0012\u0001\u0005z\u0012uX\u0011\u0002\u0015\fe\r}HQAC\f\t\u0017!i!\t\u0002\u0006\u001a\u0005\tSk]3!O\u0019cwn\u001e\u0018mCjLh)\u001e;ve\u00164En\\<(A%t7\u000f^3bI\u0006Qa-\u001e;ve\u00164En\\<\u0016\u0011\u0015}QQEC\u0015\u000b_!B!\"\t\u00062AAq\tAC\u0012\u000bO)Y\u0003E\u0002L\u000bK!aa!\u00124\u0005\u0004q\u0005cA&\u0006*\u00111\u0011qD\u001aC\u00029\u0003ba!2\u0004L\u00165\u0002cA&\u00060\u00111\u0011\u0011G\u001aC\u00029Cq!!\u00194\u0001\u0004)\u0019\u0004\u0005\u0004\u0004F\u000e-WQ\u0007\t\t\u000f\u0002)\u0019#b\n\u0006.\u0005AA.\u0019>z\r2|w/\u0006\u0005\u0006<\u0015\u0005SQIC&)\u0011)i$\"\u0014\u0011\u0011\u001d\u0003QqHC\"\u000b\u000f\u00022aSC!\t\u0019\u0019)\u0005\u000eb\u0001\u001dB\u00191*\"\u0012\u0005\r\u0005}AG1\u0001O!\u0019\u0019)ma3\u0006JA\u00191*b\u0013\u0005\r\u0005EBG1\u0001O\u0011\u001d)y\u0005\u000ea\u0001\u000b#\naa\u0019:fCR,\u0007#B!\u0004R\u0015M\u0003\u0003C$\u0001\u000b\u007f)\u0019%\"\u0013\u0002\u001d1\f'0\u001f$viV\u0014XM\u00127poVAQ\u0011LC0\u000bG*I\u0007\u0006\u0003\u0006\\\u0015-\u0004\u0003C$\u0001\u000b;*\t'\"\u001a\u0011\u0007-+y\u0006\u0002\u0004\u0004FU\u0012\rA\u0014\t\u0004\u0017\u0016\rDABA\u0010k\t\u0007a\n\u0005\u0004\u0004F\u000e-Wq\r\t\u0004\u0017\u0016%DABA\u0019k\t\u0007a\nC\u0004\u0006PU\u0002\r!\"\u001c\u0011\u000b\u0005\u001b\t&b\u001c\u0011\r\r\u001571ZC9!!9\u0005!\"\u0018\u0006b\u0015\u001d\u0004")
public final class Flow<In, Out, Mat>
implements FlowOpsMat<Out, Mat>,
Graph<FlowShape<In, Out>, Mat> {
    private final LinearTraversalBuilder traversalBuilder;
    private final FlowShape<In, Out> shape;

    public static <I, O, M> Flow<I, O, Future<M>> lazyFutureFlow(Function0<Future<Flow<I, O, M>>> create) {
        return Flow$.MODULE$.lazyFutureFlow(create);
    }

    public static <I, O, M> Flow<I, O, Future<M>> lazyFlow(Function0<Flow<I, O, M>> create) {
        return Flow$.MODULE$.lazyFlow(create);
    }

    public static <I, O, M> Flow<I, O, Future<M>> futureFlow(Future<Flow<I, O, M>> flow) {
        return Flow$.MODULE$.futureFlow(flow);
    }

    public static <I, O, M> Flow<I, O, Future<Option<M>>> lazyInitAsync(Function0<Future<Flow<I, O, M>>> flowFactory) {
        return Flow$.MODULE$.lazyInitAsync(flowFactory);
    }

    public static <I, O, M> Flow<I, O, M> lazyInit(Function1<I, Future<Flow<I, O, M>>> flowFactory, Function0<M> fallback) {
        return Flow$.MODULE$.lazyInit(flowFactory, fallback);
    }

    public static <I, O, M1, M2, M> Flow<I, O, M> fromSinkAndSourceCoupledMat(Graph<SinkShape<I>, M1> sink2, Graph<SourceShape<O>, M2> source2, Function2<M1, M2, M> combine) {
        return Flow$.MODULE$.fromSinkAndSourceCoupledMat(sink2, source2, combine);
    }

    public static <I, O> Flow<I, O, NotUsed> fromSinkAndSourceCoupled(Graph<SinkShape<I>, ?> sink2, Graph<SourceShape<O>, ?> source2) {
        return Flow$.MODULE$.fromSinkAndSourceCoupled(sink2, source2);
    }

    public static <I, O, M1, M2, M> Flow<I, O, M> fromSinkAndSourceMat(Graph<SinkShape<I>, M1> sink2, Graph<SourceShape<O>, M2> source2, Function2<M1, M2, M> combine) {
        return Flow$.MODULE$.fromSinkAndSourceMat(sink2, source2, combine);
    }

    public static <I, O> Flow<I, O, NotUsed> fromSinkAndSource(Graph<SinkShape<I>, ?> sink2, Graph<SourceShape<O>, ?> source2) {
        return Flow$.MODULE$.fromSinkAndSource(sink2, source2);
    }

    public static <T, U, M> Flow<T, U, Future<M>> setup(Function2<ActorMaterializer, Attributes, Flow<T, U, M>> factory2) {
        return Flow$.MODULE$.setup(factory2);
    }

    public static <T, U, M> Flow<T, U, Future<M>> fromMaterializer(Function2<Materializer, Attributes, Flow<T, U, M>> factory2) {
        return Flow$.MODULE$.fromMaterializer(factory2);
    }

    public static <I, O, M> Flow<I, O, M> fromGraph(Graph<FlowShape<I, O>, M> g2) {
        return Flow$.MODULE$.fromGraph(g2);
    }

    public static <A, B> Flow<A, B, NotUsed> fromFunction(Function1<A, B> f) {
        return Flow$.MODULE$.fromFunction(f);
    }

    public static <T> Flow<T, T, NotUsed> apply() {
        return Flow$.MODULE$.apply();
    }

    public static <I, O, M> Flow<I, O, M> fromProcessorMat(Function0<Tuple2<Processor<I, O>, M>> processorFactory) {
        return Flow$.MODULE$.fromProcessorMat(processorFactory);
    }

    public static <I, O> Flow<I, O, NotUsed> fromProcessor(Function0<Processor<I, O>> processorFactory) {
        return Flow$.MODULE$.fromProcessor(processorFactory);
    }

    @Override
    public Attributes getAttributes() {
        return Graph.getAttributes$(this);
    }

    @Override
    public <Out2, Mat2, Mat3> FlowOpsMat flatMapPrefixMat(int n, Function1<Seq<Out>, Flow<Out, Out2, Mat2>> f, Function2<Mat, Future<Mat2>, Mat3> matF) {
        return FlowOpsMat.flatMapPrefixMat$(this, n, f, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat zipMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.zipMat$(this, that, matF);
    }

    @Override
    public <U, Mat2, Mat3, A> FlowOpsMat zipAllMat(Graph<SourceShape<U>, Mat2> that, A thisElem, U thatElem, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.zipAllMat$(this, that, thisElem, thatElem, matF);
    }

    @Override
    public <Out2, Out3, Mat2, Mat3> FlowOpsMat zipWithMat(Graph<SourceShape<Out2>, Mat2> that, Function2<Out, Out2, Out3> combine, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.zipWithMat$(this, that, combine, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat zipLatestMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.zipLatestMat$(this, that, matF);
    }

    @Override
    public <Out2, Out3, Mat2, Mat3> FlowOpsMat zipLatestWithMat(Graph<SourceShape<Out2>, Mat2> that, Function2<Out, Out2, Out3> combine, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.zipLatestWithMat$(this, that, combine, matF);
    }

    @Override
    public <Out2, Out3, Mat2, Mat3> FlowOpsMat zipLatestWithMat(Graph<SourceShape<Out2>, Mat2> that, boolean eagerComplete, Function2<Out, Out2, Out3> combine, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.zipLatestWithMat$(this, that, eagerComplete, combine, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat mergeMat(Graph<SourceShape<U>, Mat2> that, boolean eagerComplete, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.mergeMat$(this, that, eagerComplete, matF);
    }

    @Override
    public <U, Mat2, Mat3> boolean mergeMat$default$2() {
        return FlowOpsMat.mergeMat$default$2$(this);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat interleaveMat(Graph<SourceShape<U>, Mat2> that, int segmentSize, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.interleaveMat$(this, that, segmentSize, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat interleaveMat(Graph<SourceShape<U>, Mat2> that, int segmentSize, boolean eagerClose, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.interleaveMat$(this, that, segmentSize, eagerClose, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat mergeLatestMat(Graph<SourceShape<U>, Mat2> that, boolean eagerClose, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.mergeLatestMat$(this, that, eagerClose, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat mergePreferredMat(Graph<SourceShape<U>, Mat2> that, boolean preferred, boolean eagerClose, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.mergePreferredMat$(this, that, preferred, eagerClose, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat mergePrioritizedMat(Graph<SourceShape<U>, Mat2> that, int leftPriority, int rightPriority, boolean eagerClose, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.mergePrioritizedMat$(this, that, leftPriority, rightPriority, eagerClose, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat mergeSortedMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF, Ordering<U> ord) {
        return FlowOpsMat.mergeSortedMat$(this, that, matF, ord);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat concatMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.concatMat$(this, that, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat concatLazyMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.concatLazyMat$(this, that, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat prependMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.prependMat$(this, that, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat prependLazyMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.prependLazyMat$(this, that, matF);
    }

    @Override
    public <U, Mat2, Mat3> FlowOpsMat orElseMat(Graph<SourceShape<U>, Mat2> secondary, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.orElseMat$(this, secondary, matF);
    }

    @Override
    public <Mat2, Mat3> FlowOpsMat alsoToMat(Graph<SinkShape<Out>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.alsoToMat$(this, that, matF);
    }

    @Override
    public <Mat2, Mat3> FlowOpsMat divertToMat(Graph<SinkShape<Out>, Mat2> that, Function1<Out, Object> when, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.divertToMat$(this, that, when, matF);
    }

    @Override
    public <Mat2, Mat3> FlowOpsMat wireTapMat(Graph<SinkShape<Out>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return FlowOpsMat.wireTapMat$(this, that, matF);
    }

    @Override
    public <Mat2> FlowOpsMat watchTermination(Function2<Mat, Future<Done>, Mat2> matF) {
        return FlowOpsMat.watchTermination$(this, matF);
    }

    @Override
    public <Mat2> FlowOpsMat monitorMat(Function2<Mat, FlowMonitor<Out>, Mat2> combine) {
        return FlowOpsMat.monitorMat$(this, combine);
    }

    @Override
    public FlowOpsMat monitor() {
        return FlowOpsMat.monitor$(this);
    }

    @Override
    public <T> FlowOps recover(PartialFunction<Throwable, T> pf) {
        return FlowOps.recover$(this, pf);
    }

    @Override
    public <T> FlowOps recoverWith(PartialFunction<Throwable, Graph<SourceShape<T>, NotUsed>> pf) {
        return FlowOps.recoverWith$(this, pf);
    }

    @Override
    public <T> FlowOps recoverWithRetries(int attempts, PartialFunction<Throwable, Graph<SourceShape<T>, NotUsed>> pf) {
        return FlowOps.recoverWithRetries$(this, attempts, pf);
    }

    @Override
    public FlowOps mapError(PartialFunction<Throwable, Throwable> pf) {
        return FlowOps.mapError$(this, pf);
    }

    @Override
    public <T> FlowOps map(Function1<Out, T> f) {
        return FlowOps.map$(this, f);
    }

    @Override
    public FlowOps wireTap(Function1<Out, BoxedUnit> f) {
        return FlowOps.wireTap$((FlowOps)this, f);
    }

    @Override
    public <T> FlowOps mapConcat(Function1<Out, IterableOnce<T>> f) {
        return FlowOps.mapConcat$(this, f);
    }

    @Override
    public <S, T> FlowOps statefulMap(Function0<S> create, Function2<S, Out, Tuple2<S, T>> f, Function1<S, Option<T>> onComplete) {
        return FlowOps.statefulMap$(this, create, f, onComplete);
    }

    @Override
    public <R, T> FlowOps mapWithResource(Function0<R> create, Function2<R, Out, T> f, Function1<R, Option<T>> close) {
        return FlowOps.mapWithResource$(this, create, f, close);
    }

    @Override
    public <T> FlowOps statefulMapConcat(Function0<Function1<Out, IterableOnce<T>>> f) {
        return FlowOps.statefulMapConcat$(this, f);
    }

    @Override
    public <T> FlowOps mapAsync(int parallelism, Function1<Out, Future<T>> f) {
        return FlowOps.mapAsync$(this, parallelism, f);
    }

    @Override
    public <T, P> FlowOps mapAsyncPartitioned(int parallelism, int perPartition, Function1<Out, P> partitioner, Function2<Out, P, Future<T>> f) {
        return FlowOps.mapAsyncPartitioned$(this, parallelism, perPartition, partitioner, f);
    }

    @Override
    public <T> FlowOps mapAsyncUnordered(int parallelism, Function1<Out, Future<T>> f) {
        return FlowOps.mapAsyncUnordered$(this, parallelism, f);
    }

    @Override
    public <S> FlowOps ask(ActorRef ref, Timeout timeout2, ClassTag<S> tag) {
        return FlowOps.ask$(this, ref, timeout2, tag);
    }

    @Override
    public <S> FlowOps ask(int parallelism, ActorRef ref, Timeout timeout2, ClassTag<S> tag) {
        return FlowOps.ask$(this, parallelism, ref, timeout2, tag);
    }

    @Override
    public FlowOps watch(ActorRef ref) {
        return FlowOps.watch$(this, ref);
    }

    @Override
    public FlowOps filter(Function1<Out, Object> p) {
        return FlowOps.filter$(this, p);
    }

    @Override
    public FlowOps filterNot(Function1<Out, Object> p) {
        return FlowOps.filterNot$(this, p);
    }

    @Override
    public FlowOps takeWhile(Function1<Out, Object> p) {
        return FlowOps.takeWhile$(this, p);
    }

    @Override
    public FlowOps takeWhile(Function1<Out, Object> p, boolean inclusive) {
        return FlowOps.takeWhile$(this, p, inclusive);
    }

    @Override
    public FlowOps dropWhile(Function1<Out, Object> p) {
        return FlowOps.dropWhile$(this, p);
    }

    @Override
    public <T> FlowOps collect(PartialFunction<Out, T> pf) {
        return FlowOps.collect$(this, pf);
    }

    @Override
    public <T> FlowOps collectType(ClassTag<T> tag) {
        return FlowOps.collectType$(this, tag);
    }

    @Override
    public FlowOps grouped(int n) {
        return FlowOps.grouped$(this, n);
    }

    @Override
    public FlowOps groupedWeighted(long minWeight, Function1<Out, Object> costFn) {
        return FlowOps.groupedWeighted$(this, minWeight, costFn);
    }

    @Override
    public FlowOps limit(long max) {
        return FlowOps.limit$(this, max);
    }

    @Override
    public <T> FlowOps limitWeighted(long max, Function1<Out, Object> costFn) {
        return FlowOps.limitWeighted$(this, max, costFn);
    }

    @Override
    public FlowOps sliding(int n, int step) {
        return FlowOps.sliding$(this, n, step);
    }

    @Override
    public int sliding$default$2() {
        return FlowOps.sliding$default$2$(this);
    }

    @Override
    public <T> FlowOps scan(T zero, Function2<T, Out, T> f) {
        return FlowOps.scan$(this, zero, f);
    }

    @Override
    public <T> FlowOps scanAsync(T zero, Function2<T, Out, Future<T>> f) {
        return FlowOps.scanAsync$(this, zero, f);
    }

    @Override
    public <T> FlowOps fold(T zero, Function2<T, Out, T> f) {
        return FlowOps.fold$(this, zero, f);
    }

    @Override
    public <T> FlowOps foldAsync(T zero, Function2<T, Out, Future<T>> f) {
        return FlowOps.foldAsync$(this, zero, f);
    }

    @Override
    public <T> FlowOps reduce(Function2<T, T, T> f) {
        return FlowOps.reduce$(this, f);
    }

    @Override
    public <T> FlowOps intersperse(T start2, T inject, T end) {
        return FlowOps.intersperse$(this, start2, inject, end);
    }

    @Override
    public <T> FlowOps intersperse(T inject) {
        return FlowOps.intersperse$(this, inject);
    }

    @Override
    public FlowOps groupedWithin(int n, FiniteDuration d) {
        return FlowOps.groupedWithin$(this, n, d);
    }

    @Override
    public FlowOps groupedWeightedWithin(long maxWeight, FiniteDuration d, Function1<Out, Object> costFn) {
        return FlowOps.groupedWeightedWithin$(this, maxWeight, d, costFn);
    }

    @Override
    public FlowOps groupedWeightedWithin(long maxWeight, int maxNumber, FiniteDuration d, Function1<Out, Object> costFn) {
        return FlowOps.groupedWeightedWithin$(this, maxWeight, maxNumber, d, costFn);
    }

    @Override
    public FlowOps delay(FiniteDuration of, DelayOverflowStrategy strategy) {
        return FlowOps.delay$(this, of, strategy);
    }

    @Override
    public DelayOverflowStrategy delay$default$2() {
        return FlowOps.delay$default$2$(this);
    }

    @Override
    public FlowOps delayWith(Function0<DelayStrategy<Out>> delayStrategySupplier, DelayOverflowStrategy overFlowStrategy) {
        return FlowOps.delayWith$(this, delayStrategySupplier, overFlowStrategy);
    }

    @Override
    public FlowOps drop(long n) {
        return FlowOps.drop$(this, n);
    }

    @Override
    public FlowOps dropWithin(FiniteDuration d) {
        return FlowOps.dropWithin$(this, d);
    }

    @Override
    public FlowOps take(long n) {
        return FlowOps.take$(this, n);
    }

    @Override
    public FlowOps takeWithin(FiniteDuration d) {
        return FlowOps.takeWithin$(this, d);
    }

    @Override
    public <S> FlowOps conflateWithSeed(Function1<Out, S> seed, Function2<S, Out, S> aggregate) {
        return FlowOps.conflateWithSeed$(this, seed, aggregate);
    }

    @Override
    public <O2> FlowOps conflate(Function2<O2, O2, O2> aggregate) {
        return FlowOps.conflate$(this, aggregate);
    }

    @Override
    public <S> FlowOps batch(long max, Function1<Out, S> seed, Function2<S, Out, S> aggregate) {
        return FlowOps.batch$(this, max, seed, aggregate);
    }

    @Override
    public <S> FlowOps batchWeighted(long max, Function1<Out, Object> costFn, Function1<Out, S> seed, Function2<S, Out, S> aggregate) {
        return FlowOps.batchWeighted$(this, max, costFn, seed, aggregate);
    }

    @Override
    public <U> FlowOps expand(Function1<Out, Iterator<U>> expander) {
        return FlowOps.expand$(this, expander);
    }

    @Override
    public <U> FlowOps extrapolate(Function1<U, Iterator<U>> extrapolator, Option<U> initial) {
        return FlowOps.extrapolate$(this, extrapolator, initial);
    }

    @Override
    public <U> None$ extrapolate$default$2() {
        return FlowOps.extrapolate$default$2$(this);
    }

    @Override
    public FlowOps buffer(int size, OverflowStrategy overflowStrategy) {
        return FlowOps.buffer$(this, size, overflowStrategy);
    }

    @Override
    public <U> FlowOps prefixAndTail(int n) {
        return FlowOps.prefixAndTail$(this, n);
    }

    @Override
    public <Out2, Mat2> FlowOps flatMapPrefix(int n, Function1<Seq<Out>, Flow<Out, Out2, Mat2>> f) {
        return FlowOps.flatMapPrefix$(this, n, f);
    }

    @Override
    public <K> SubFlow<Out, Mat, FlowOps, Object> groupBy(int maxSubstreams, Function1<Out, K> f, boolean allowClosedSubstreamRecreation) {
        return FlowOps.groupBy$(this, maxSubstreams, f, allowClosedSubstreamRecreation);
    }

    @Override
    public <K> SubFlow<Out, Mat, FlowOps, Object> groupBy(int maxSubstreams, Function1<Out, K> f) {
        return FlowOps.groupBy$(this, maxSubstreams, f);
    }

    @Override
    public SubFlow<Out, Mat, FlowOps, Object> splitWhen(SubstreamCancelStrategy substreamCancelStrategy, Function1<Out, Object> p) {
        return FlowOps.splitWhen$(this, substreamCancelStrategy, p);
    }

    @Override
    public SubFlow<Out, Mat, FlowOps, Object> splitWhen(Function1<Out, Object> p) {
        return FlowOps.splitWhen$(this, p);
    }

    @Override
    public SubFlow<Out, Mat, FlowOps, Object> splitAfter(SubstreamCancelStrategy substreamCancelStrategy, Function1<Out, Object> p) {
        return FlowOps.splitAfter$(this, substreamCancelStrategy, p);
    }

    @Override
    public SubFlow<Out, Mat, FlowOps, Object> splitAfter(Function1<Out, Object> p) {
        return FlowOps.splitAfter$(this, p);
    }

    @Override
    public <T, M> FlowOps flatMapConcat(Function1<Out, Graph<SourceShape<T>, M>> f) {
        return FlowOps.flatMapConcat$(this, f);
    }

    @Override
    public <T, M> FlowOps flatMapMerge(int breadth, Function1<Out, Graph<SourceShape<T>, M>> f) {
        return FlowOps.flatMapMerge$(this, breadth, f);
    }

    @Override
    public FlowOps initialTimeout(FiniteDuration timeout2) {
        return FlowOps.initialTimeout$(this, timeout2);
    }

    @Override
    public FlowOps completionTimeout(FiniteDuration timeout2) {
        return FlowOps.completionTimeout$(this, timeout2);
    }

    @Override
    public FlowOps idleTimeout(FiniteDuration timeout2) {
        return FlowOps.idleTimeout$(this, timeout2);
    }

    @Override
    public FlowOps backpressureTimeout(FiniteDuration timeout2) {
        return FlowOps.backpressureTimeout$(this, timeout2);
    }

    @Override
    public <U> FlowOps keepAlive(FiniteDuration maxIdle, Function0<U> injectedElem) {
        return FlowOps.keepAlive$(this, maxIdle, injectedElem);
    }

    @Override
    public FlowOps throttle(int elements, FiniteDuration per) {
        return FlowOps.throttle$(this, elements, per);
    }

    @Override
    public FlowOps throttle(int elements, FiniteDuration per, int maximumBurst, ThrottleMode mode) {
        return FlowOps.throttle$(this, elements, per, maximumBurst, mode);
    }

    @Override
    public FlowOps throttle(int cost, FiniteDuration per, Function1<Out, Object> costCalculation) {
        return FlowOps.throttle$(this, cost, per, costCalculation);
    }

    @Override
    public FlowOps throttle(int cost, FiniteDuration per, int maximumBurst, Function1<Out, Object> costCalculation, ThrottleMode mode) {
        return FlowOps.throttle$(this, cost, per, maximumBurst, costCalculation, mode);
    }

    @Override
    public FlowOps detach() {
        return FlowOps.detach$(this);
    }

    @Override
    public FlowOps initialDelay(FiniteDuration delay) {
        return FlowOps.initialDelay$(this, delay);
    }

    @Override
    public FlowOps log(String name, Function1<Out, Object> extract, LoggingAdapter log) {
        return FlowOps.log$(this, name, extract, log);
    }

    @Override
    public Function1<Out, Object> log$default$2() {
        return FlowOps.log$default$2$(this);
    }

    @Override
    public LoggingAdapter log$default$3(String name, Function1<Out, Object> extract) {
        return FlowOps.log$default$3$(this, name, extract);
    }

    @Override
    public FlowOps logWithMarker(String name, Function1<Out, LogMarker> marker, Function1<Out, Object> extract, MarkerLoggingAdapter log) {
        return FlowOps.logWithMarker$(this, name, marker, extract, log);
    }

    @Override
    public Function1<Out, Object> logWithMarker$default$3() {
        return FlowOps.logWithMarker$default$3$(this);
    }

    @Override
    public MarkerLoggingAdapter logWithMarker$default$4(String name, Function1<Out, LogMarker> marker, Function1<Out, Object> extract) {
        return FlowOps.logWithMarker$default$4$(this, name, marker, extract);
    }

    @Override
    public <U> FlowOps zip(Graph<SourceShape<U>, ?> that) {
        return FlowOps.zip$(this, that);
    }

    @Override
    public <U, A> FlowOps zipAll(Graph<SourceShape<U>, ?> that, A thisElem, U thatElem) {
        return FlowOps.zipAll$(this, that, thisElem, thatElem);
    }

    @Override
    public <U, A, Mat2> Flow<Out, Tuple2<A, U>, Mat2> zipAllFlow(Graph<SourceShape<U>, Mat2> that, A thisElem, U thatElem) {
        return FlowOps.zipAllFlow$(this, that, thisElem, thatElem);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, Tuple2<Out, U>>, M> zipGraph(Graph<SourceShape<U>, M> that) {
        return FlowOps.zipGraph$(this, that);
    }

    @Override
    public <U> FlowOps zipLatest(Graph<SourceShape<U>, ?> that) {
        return FlowOps.zipLatest$(this, that);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, Tuple2<Out, U>>, M> zipLatestGraph(Graph<SourceShape<U>, M> that) {
        return FlowOps.zipLatestGraph$(this, that);
    }

    @Override
    public <Out2, Out3> FlowOps zipWith(Graph<SourceShape<Out2>, ?> that, Function2<Out, Out2, Out3> combine) {
        return FlowOps.zipWith$(this, that, combine);
    }

    @Override
    public <Out2, Out3, M> Graph<FlowShape<Out, Out3>, M> zipWithGraph(Graph<SourceShape<Out2>, M> that, Function2<Out, Out2, Out3> combine) {
        return FlowOps.zipWithGraph$(this, that, combine);
    }

    @Override
    public <Out2, Out3> FlowOps zipLatestWith(Graph<SourceShape<Out2>, ?> that, Function2<Out, Out2, Out3> combine) {
        return FlowOps.zipLatestWith$(this, that, combine);
    }

    @Override
    public <Out2, Out3> FlowOps zipLatestWith(Graph<SourceShape<Out2>, ?> that, boolean eagerComplete, Function2<Out, Out2, Out3> combine) {
        return FlowOps.zipLatestWith$(this, that, eagerComplete, combine);
    }

    @Override
    public <Out2, Out3, M> Graph<FlowShape<Out, Out3>, M> zipLatestWithGraph(Graph<SourceShape<Out2>, M> that, Function2<Out, Out2, Out3> combine) {
        return FlowOps.zipLatestWithGraph$(this, that, combine);
    }

    @Override
    public <Out2, Out3, M> Graph<FlowShape<Out, Out3>, M> zipLatestWithGraph(Graph<SourceShape<Out2>, M> that, boolean eagerComplete, Function2<Out, Out2, Out3> combine) {
        return FlowOps.zipLatestWithGraph$(this, that, eagerComplete, combine);
    }

    @Override
    public FlowOps zipWithIndex() {
        return FlowOps.zipWithIndex$(this);
    }

    @Override
    public <U> FlowOps interleave(Graph<SourceShape<U>, ?> that, int segmentSize) {
        return FlowOps.interleave$(this, that, segmentSize);
    }

    @Override
    public <U> FlowOps interleave(Graph<SourceShape<U>, ?> that, int segmentSize, boolean eagerClose) {
        return FlowOps.interleave$(this, that, segmentSize, eagerClose);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, U>, M> interleaveGraph(Graph<SourceShape<U>, M> that, int segmentSize, boolean eagerClose) {
        return FlowOps.interleaveGraph$(this, that, segmentSize, eagerClose);
    }

    @Override
    public <U, M> boolean interleaveGraph$default$3() {
        return FlowOps.interleaveGraph$default$3$(this);
    }

    @Override
    public <U> FlowOps interleaveAll(Seq<Graph<SourceShape<U>, ?>> those, int segmentSize, boolean eagerClose) {
        return FlowOps.interleaveAll$(this, those, segmentSize, eagerClose);
    }

    @Override
    public <U, M> FlowOps merge(Graph<SourceShape<U>, M> that, boolean eagerComplete) {
        return FlowOps.merge$(this, that, eagerComplete);
    }

    @Override
    public <U, M> boolean merge$default$2() {
        return FlowOps.merge$default$2$(this);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, U>, M> mergeGraph(Graph<SourceShape<U>, M> that, boolean eagerComplete) {
        return FlowOps.mergeGraph$(this, that, eagerComplete);
    }

    @Override
    public <U> FlowOps mergeAll(Seq<Graph<SourceShape<U>, ?>> those, boolean eagerComplete) {
        return FlowOps.mergeAll$(this, those, eagerComplete);
    }

    @Override
    public <U, M> FlowOps mergeLatest(Graph<SourceShape<U>, M> that, boolean eagerComplete) {
        return FlowOps.mergeLatest$(this, that, eagerComplete);
    }

    @Override
    public <U, M> boolean mergeLatest$default$2() {
        return FlowOps.mergeLatest$default$2$(this);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, Seq<U>>, M> mergeLatestGraph(Graph<SourceShape<U>, M> that, boolean eagerComplete) {
        return FlowOps.mergeLatestGraph$(this, that, eagerComplete);
    }

    @Override
    public <U, M> FlowOps mergePreferred(Graph<SourceShape<U>, M> that, boolean preferred, boolean eagerComplete) {
        return FlowOps.mergePreferred$(this, that, preferred, eagerComplete);
    }

    @Override
    public <U, M> boolean mergePreferred$default$3() {
        return FlowOps.mergePreferred$default$3$(this);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, U>, M> mergePreferredGraph(Graph<SourceShape<U>, M> that, boolean preferred, boolean eagerComplete) {
        return FlowOps.mergePreferredGraph$(this, that, preferred, eagerComplete);
    }

    @Override
    public <U, M> FlowOps mergePrioritized(Graph<SourceShape<U>, M> that, int leftPriority, int rightPriority, boolean eagerComplete) {
        return FlowOps.mergePrioritized$(this, that, leftPriority, rightPriority, eagerComplete);
    }

    @Override
    public <U, M> boolean mergePrioritized$default$4() {
        return FlowOps.mergePrioritized$default$4$(this);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, U>, M> mergePrioritizedGraph(Graph<SourceShape<U>, M> that, int leftPriority, int rightPriority, boolean eagerComplete) {
        return FlowOps.mergePrioritizedGraph$(this, that, leftPriority, rightPriority, eagerComplete);
    }

    @Override
    public <U, M> FlowOps mergeSorted(Graph<SourceShape<U>, M> that, Ordering<U> ord) {
        return FlowOps.mergeSorted$(this, that, ord);
    }

    @Override
    public <U, M> Graph<FlowShape<Out, U>, M> mergeSortedGraph(Graph<SourceShape<U>, M> that, Ordering<U> ord) {
        return FlowOps.mergeSortedGraph$(this, that, ord);
    }

    @Override
    public <U, Mat2> FlowOps concat(Graph<SourceShape<U>, Mat2> that) {
        return FlowOps.concat$(this, that);
    }

    @Override
    public <U, Mat2> Graph<FlowShape<Out, U>, Mat2> concatGraph(Graph<SourceShape<U>, Mat2> that, boolean detached) {
        return FlowOps.concatGraph$(this, that, detached);
    }

    @Override
    public <U, Mat2> FlowOps concatLazy(Graph<SourceShape<U>, Mat2> that) {
        return FlowOps.concatLazy$(this, that);
    }

    @Override
    public <U> FlowOps concatAllLazy(Seq<Graph<SourceShape<U>, ?>> those) {
        return FlowOps.concatAllLazy$(this, those);
    }

    @Override
    public <U, Mat2> FlowOps prepend(Graph<SourceShape<U>, Mat2> that) {
        return FlowOps.prepend$(this, that);
    }

    @Override
    public <U, Mat2> Graph<FlowShape<Out, U>, Mat2> prependGraph(Graph<SourceShape<U>, Mat2> that, boolean detached) {
        return FlowOps.prependGraph$(this, that, detached);
    }

    @Override
    public <U, Mat2> FlowOps prependLazy(Graph<SourceShape<U>, Mat2> that) {
        return FlowOps.prependLazy$(this, that);
    }

    @Override
    public <U, Mat2> FlowOps orElse(Graph<SourceShape<U>, Mat2> secondary) {
        return FlowOps.orElse$(this, secondary);
    }

    @Override
    public <U, Mat2> Graph<FlowShape<Out, U>, Mat2> orElseGraph(Graph<SourceShape<U>, Mat2> secondary) {
        return FlowOps.orElseGraph$(this, secondary);
    }

    @Override
    public <U, M> FlowOps $plus$plus(Graph<SourceShape<U>, M> that) {
        return FlowOps.$plus$plus$(this, that);
    }

    @Override
    public FlowOps alsoTo(Graph<SinkShape<Out>, ?> that) {
        return FlowOps.alsoTo$(this, that);
    }

    @Override
    public <M> Graph<FlowShape<Out, Out>, M> alsoToGraph(Graph<SinkShape<Out>, M> that) {
        return FlowOps.alsoToGraph$(this, that);
    }

    @Override
    public FlowOps alsoToAll(Seq<Graph<SinkShape<Out>, ?>> those) {
        return FlowOps.alsoToAll$(this, those);
    }

    @Override
    public FlowOps divertTo(Graph<SinkShape<Out>, ?> that, Function1<Out, Object> when) {
        return FlowOps.divertTo$(this, that, when);
    }

    @Override
    public <M> Graph<FlowShape<Out, Out>, M> divertToGraph(Graph<SinkShape<Out>, M> that, Function1<Out, Object> when) {
        return FlowOps.divertToGraph$(this, that, when);
    }

    @Override
    public FlowOps wireTap(Graph<SinkShape<Out>, ?> that) {
        return FlowOps.wireTap$((FlowOps)this, that);
    }

    @Override
    public <M> Graph<FlowShape<Out, Out>, M> wireTapGraph(Graph<SinkShape<Out>, M> that) {
        return FlowOps.wireTapGraph$(this, that);
    }

    @Override
    @ApiMayChange
    public <Agg, Emit> FlowOps aggregateWithBoundary(Function0<Agg> allocate, Function2<Agg, Out, Tuple2<Agg, Object>> aggregate, Function1<Agg, Emit> harvest, Option<Tuple2<Function1<Agg, Object>, FiniteDuration>> emitOnTimer) {
        return FlowOps.aggregateWithBoundary$(this, allocate, aggregate, harvest, emitOnTimer);
    }

    @Override
    public LinearTraversalBuilder traversalBuilder() {
        return this.traversalBuilder;
    }

    @Override
    public FlowShape<In, Out> shape() {
        return this.shape;
    }

    public String toString() {
        return new StringBuilder(6).append("Flow(").append(this.shape()).append(")").toString();
    }

    public boolean isIdentity() {
        return this.traversalBuilder() == Flow$.MODULE$.identityTraversalBuilder();
    }

    @Override
    public <T, Mat2> Flow<In, T, Mat> via(Graph<FlowShape<Out, T>, Mat2> flow) {
        return this.viaMat((Graph)flow, Keep$.MODULE$.left());
    }

    @Override
    public <T, Mat2, Mat3> Flow<In, T, Mat3> viaMat(Graph<FlowShape<Out, T>, Mat2> flow, Function2<Mat, Mat2, Mat3> combine) {
        if (this.isIdentity()) {
            Function2<Nothing$, Nothing$, Object> function2 = combine;
            Function2 function22 = Keep$.MODULE$.right();
            if (!(function2 != null ? !function2.equals(function22) : function22 != null)) {
                return Flow$.MODULE$.fromGraph(flow);
            }
            Function2<Nothing$, Nothing$, Object> function23 = combine;
            Function2 function24 = Keep$.MODULE$.left();
            Function2<Nothing$, Nothing$, Object> useCombine = !(function23 != null ? !function23.equals(function24) : function24 != null) ? Keep$.MODULE$.none() : combine;
            return new Flow<Out, T, Mat>(LinearTraversalBuilder$.MODULE$.empty(LinearTraversalBuilder$.MODULE$.empty$default$1()).append(flow.traversalBuilder(), flow.shape(), useCombine), flow.shape());
        }
        if (flow.traversalBuilder() == Flow$.MODULE$.identityTraversalBuilder()) {
            Function2<Nothing$, Nothing$, Object> function2 = combine;
            Function2 function25 = Keep$.MODULE$.left();
            if (!(function2 != null ? !function2.equals(function25) : function25 != null)) {
                return this;
            }
            Function2<Nothing$, Nothing$, Object> function26 = combine;
            Function2 function27 = Keep$.MODULE$.right();
            Function2<Nothing$, Nothing$, Object> useCombine = !(function26 != null ? !function26.equals(function27) : function27 != null) ? Keep$.MODULE$.none() : combine;
            return new Flow(this.traversalBuilder().append(LinearTraversalBuilder$.MODULE$.empty(LinearTraversalBuilder$.MODULE$.empty$default$1()), this.shape(), useCombine), new FlowShape(((FlowShape)this.shape()).in(), flow.shape().out()));
        }
        return new Flow(this.traversalBuilder().append(flow.traversalBuilder(), flow.shape(), combine), new FlowShape(((FlowShape)this.shape()).in(), flow.shape().out()));
    }

    @Override
    public <Mat2> Sink<In, Mat> to(Graph<SinkShape<Out>, Mat2> sink2) {
        return this.toMat((Graph)sink2, Keep$.MODULE$.left());
    }

    @Override
    public <Mat2, Mat3> Sink<In, Mat3> toMat(Graph<SinkShape<Out>, Mat2> sink2, Function2<Mat, Mat2, Mat3> combine) {
        if (this.isIdentity()) {
            return new Sink(LinearTraversalBuilder$.MODULE$.fromBuilder(sink2.traversalBuilder(), sink2.shape(), combine), new SinkShape<Out>(sink2.shape().in()));
        }
        return new Sink(this.traversalBuilder().append(sink2.traversalBuilder(), sink2.shape(), combine), new SinkShape(((FlowShape)this.shape()).in()));
    }

    @Override
    public <Mat2> Flow<In, Out, Mat2> mapMaterializedValue(Function1<Mat, Mat2> f) {
        return new Flow<In, Out, Mat>((LinearTraversalBuilder)this.traversalBuilder().transformMat(f), this.shape());
    }

    public Tuple2<Mat, Flow<In, Out, NotUsed>> preMaterialize(Materializer materializer) {
        Publisher pub;
        Tuple2 tuple2;
        block3: {
            Tuple2 tuple22;
            block2: {
                tuple22 = (Tuple2)((RunnableGraph)((Source)Source$.MODULE$.asSubscriber().viaMat((Graph)this, Keep$.MODULE$.both())).toMat((Graph)Sink$.MODULE$.asPublisher(false), Keep$.MODULE$.both())).run(materializer);
                if (tuple22 == null) break block2;
                tuple2 = (Tuple2)tuple22._1();
                pub = (Publisher)tuple22._2();
                if (tuple2 != null) break block3;
            }
            throw new MatchError(tuple22);
        }
        Subscriber sub = (Subscriber)tuple2._1();
        Object mat = tuple2._2();
        Tuple3 tuple3 = new Tuple3(sub, mat, pub);
        Subscriber sub2 = tuple3._1();
        Object mat2 = tuple3._2();
        Publisher pub2 = tuple3._3();
        return new Tuple2(mat2, Flow$.MODULE$.fromSinkAndSource(Sink$.MODULE$.fromSubscriber(sub2), Source$.MODULE$.fromPublisher(pub2)));
    }

    public <Mat2> RunnableGraph<Mat> join(Graph<FlowShape<Out, In>, Mat2> flow) {
        return this.joinMat(flow, Keep$.MODULE$.left());
    }

    public <Mat2, Mat3> RunnableGraph<Mat3> joinMat(Graph<FlowShape<Out, In>, Mat2> flow, Function2<Mat, Mat2, Mat3> combine) {
        TraversalBuilder resultBuilder = this.traversalBuilder().append(flow.traversalBuilder(), flow.shape(), combine).wire(flow.shape().out(), ((FlowShape)this.shape()).in());
        return new RunnableGraph(resultBuilder);
    }

    public <I2, O1, Mat2> Flow<I2, O1, Mat> join(Graph<BidiShape<Out, O1, I2, In>, Mat2> bidi) {
        return this.joinMat(bidi, Keep$.MODULE$.left());
    }

    public <I2, O1, Mat2, M> Flow<I2, O1, M> joinMat(Graph<BidiShape<Out, O1, I2, In>, Mat2> bidi, Function2<Mat, Mat2, M> combine) {
        Shape newBidiShape = bidi.shape().deepCopy();
        Shape newFlowShape = ((FlowShape)this.shape()).deepCopy();
        TraversalBuilder resultBuilder = TraversalBuilder$.MODULE$.empty(TraversalBuilder$.MODULE$.empty$default$1()).add(this.traversalBuilder(), newFlowShape, Keep$.MODULE$.right()).add(bidi.traversalBuilder(), newBidiShape, combine).wire(((FlowShape)newFlowShape).out(), ((BidiShape)newBidiShape).in1()).wire(((BidiShape)newBidiShape).out2(), ((FlowShape)newFlowShape).in());
        FlowShape newShape = new FlowShape(((BidiShape)newBidiShape).in2(), ((BidiShape)newBidiShape).out1());
        return new Flow(LinearTraversalBuilder$.MODULE$.fromBuilder(resultBuilder, newShape, Keep$.MODULE$.right()), newShape);
    }

    @Override
    public Flow<In, Out, Mat> withAttributes(Attributes attr) {
        return new Flow<In, Out, Mat>(this.traversalBuilder().setAttributes(attr), this.shape());
    }

    @Override
    public Flow<In, Out, Mat> addAttributes(Attributes attr) {
        return this.withAttributes(this.traversalBuilder().attributes().and(attr));
    }

    @Override
    public Flow<In, Out, Mat> named(String name) {
        return this.addAttributes(Attributes$.MODULE$.name(name));
    }

    @Override
    public Flow<In, Out, Mat> async() {
        return (Flow)Graph.async$(this);
    }

    public Flow<In, Out, Mat> async(String dispatcher) {
        return (Flow)Graph.async$(this, dispatcher);
    }

    public Flow<In, Out, Mat> async(String dispatcher, int inputBufferSize) {
        return (Flow)Graph.async$(this, dispatcher, inputBufferSize);
    }

    public <Mat1, Mat2> Tuple2<Mat1, Mat2> runWith(Graph<SourceShape<In>, Mat1> source2, Graph<SinkShape<Out>, Mat2> sink2, Materializer materializer) {
        return (Tuple2)((RunnableGraph)((Source)Source$.MODULE$.fromGraph(source2).via((Graph)this)).toMat((Graph)sink2, Keep$.MODULE$.both())).run(materializer);
    }

    public RunnableGraph<Processor<In, Out>> toProcessor() {
        return ((RunnableGraph)((Source)Source$.MODULE$.asSubscriber().via((Graph)this)).toMat((Graph)Sink$.MODULE$.asPublisher(false), Keep$.MODULE$.both())).mapMaterializedValue((Function1<Tuple2, Processor> & Serializable)(Mat x0$1) -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                Subscriber sub = (Subscriber)tuple2._1();
                Publisher pub = (Publisher)tuple2._2();
                return new Processor<In, Out>(null, sub, pub){
                    private final Subscriber sub$1;
                    private final Publisher pub$1;

                    public void onError(Throwable t2) {
                        this.sub$1.onError(t2);
                    }

                    public void onSubscribe(Subscription s2) {
                        this.sub$1.onSubscribe(s2);
                    }

                    public void onComplete() {
                        this.sub$1.onComplete();
                    }

                    public void onNext(In t2) {
                        this.sub$1.onNext(t2);
                    }

                    public void subscribe(Subscriber<? super Out> s2) {
                        this.pub$1.subscribe(s2);
                    }
                    {
                        this.sub$1 = sub$1;
                        this.pub$1 = pub$1;
                    }
                };
            }
            throw new MatchError(tuple2);
        });
    }

    public <U, CtxU, CtxOut> FlowWithContext<U, CtxU, Out, CtxOut, Mat> asFlowWithContext(Function2<U, CtxU, In> collapseContext, Function1<Out, CtxOut> extractContext) {
        return new FlowWithContext((Flow)((Flow)((Flow)Flow$.MODULE$.apply().map((Function1<Tuple2, Object> & Serializable)x0$1 -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                Object e = tuple2._1();
                Object ctx = tuple2._2();
                return collapseContext.apply(e, ctx);
            }
            throw new MatchError(tuple2);
        })).viaMat((Graph)this, Keep$.MODULE$.right())).map((Function1<Object, Tuple2> & Serializable)e -> new Tuple2(e, extractContext.apply(e))));
    }

    public <JIn extends In> akka.stream.javadsl.Flow<JIn, Out, Mat> asJava() {
        return new akka.stream.javadsl.Flow(this);
    }

    public Flow(LinearTraversalBuilder traversalBuilder, FlowShape<In, Out> shape) {
        this.traversalBuilder = traversalBuilder;
        this.shape = shape;
        FlowOps.$init$(this);
        FlowOpsMat.$init$(this);
        Graph.$init$(this);
    }
}

