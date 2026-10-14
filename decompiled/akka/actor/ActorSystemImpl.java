/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.Version$;
import akka.actor.Actor;
import akka.actor.ActorCell;
import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorRefProvider;
import akka.actor.ActorRefWithCell;
import akka.actor.ActorSystem;
import akka.actor.ActorSystem$Settings$;
import akka.actor.Cell;
import akka.actor.CoordinatedShutdown;
import akka.actor.CoordinatedShutdown$;
import akka.actor.CoordinatedShutdown$ActorSystemTerminateReason$;
import akka.actor.DynamicAccess;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import akka.actor.ExtensionIdProvider;
import akka.actor.InternalActorRef;
import akka.actor.LocalActorRef;
import akka.actor.Props;
import akka.actor.Props$;
import akka.actor.ReflectiveDynamicAccess;
import akka.actor.Scheduler;
import akka.actor.StopChild;
import akka.actor.Terminated;
import akka.actor.dungeon.ChildrenContainer;
import akka.actor.dungeon.ChildrenContainer$EmptyChildrenContainer$;
import akka.actor.dungeon.ChildrenContainer$TerminatedChildrenContainer$;
import akka.actor.setup.ActorSystemSetup;
import akka.annotation.InternalApi;
import akka.dispatch.DefaultDispatcherPrerequisites;
import akka.dispatch.Dispatchers;
import akka.dispatch.Mailboxes;
import akka.dispatch.MonitorableThreadFactory;
import akka.dispatch.MonitorableThreadFactory$;
import akka.event.DeadLetterListener;
import akka.event.EventStream;
import akka.event.LogMarker$;
import akka.event.Logging$;
import akka.event.LoggingAdapter;
import akka.event.LoggingFilter;
import akka.event.MarkerLoggingAdapter;
import akka.japi.Util$;
import akka.serialization.SerializationExtension$;
import akka.util.ManifestInfo;
import akka.util.ManifestInfo$;
import akka.util.OptionVal$;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.Closeable;
import java.io.Serializable;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.NotImplementedError;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Some;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.StringOps$;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.collection.immutable.Vector;
import scala.compat.java8.FutureConverters$;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.concurrent.package$;
import scala.math.Ordering$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.Try$;
import scala.util.control.ControlThrowable;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0011\rc!B2e\u0001\u0019D\u0007\u0002C7\u0001\u0005\u000b\u0007I\u0011A8\t\u0011u\u0004!\u0011!Q\u0001\nAD\u0001B \u0001\u0003\u0002\u0003\u0006Ia \u0005\u000b\u0003'\u0001!\u0011!Q\u0001\n\u0005U\u0001BCA\u0013\u0001\t\u0005\t\u0015!\u0003\u0002(!Q\u00111\b\u0001\u0003\u0006\u0004%\t!!\u0010\t\u0015\u0005\u001d\u0003A!A!\u0002\u0013\ty\u0004\u0003\u0006\u0002J\u0001\u0011\t\u0011)A\u0005\u0003\u0017Bq!!\u0016\u0001\t\u0003\t9\u0006C\u0005\u0002h\u0001\u0011\r\u0011\"\u0001\u0002j!A\u0011\u0011\u000f\u0001!\u0002\u0013\tY\u0007C\u0005\u0002t\u0001\u0001\r\u0011\"\u0003\u0002v!I\u0011q\u0010\u0001A\u0002\u0013%\u0011\u0011\u0011\u0005\t\u0003\u001b\u0003\u0001\u0015)\u0003\u0002x!I\u0011q\u0013\u0001C\u0002\u0013%\u0011\u0011\u0014\u0005\t\u0003C\u0003\u0001\u0015!\u0003\u0002\u001c\"Q\u00111\u0015\u0001A\u0002\u0013\u0005a-!*\t\u0015\u0005e\u0006\u00011A\u0005\u0002\u0019\fY\f\u0003\u0005\u0002@\u0002\u0001\u000b\u0015BAT\u0011%\t\t\r\u0001b\u0001\n\u000b\t\u0019\r\u0003\u0005\u0002T\u0002\u0001\u000bQBAc\u0011\u001d\t)\u000e\u0001C\t\u0003/D\u0011\"a:\u0001\u0005\u0004%)!!;\t\u0011\u0005]\b\u0001)A\u0007\u0003WDq!!?\u0001\t#\tY\u0010C\u0004\u0002~\u0002!\t!!'\t\u000f\u0005}\b\u0001\"\u0001\u0003\u0002!9!1\u0001\u0001\u0005\u0012\t\u0015\u0001b\u0002B\u0004\u0001\u0011\u0005!\u0011\u0002\u0005\b\u0005#\u0001A\u0011\u0001B\n\u0011\u001d\u0011\t\u0002\u0001C\u0001\u00053AqA!\b\u0001\t\u0003\u0011y\u0002C\u0005\u0003$\u0001\u0011\r\u0011\"\u0001\u0003&!A!1\u0007\u0001!\u0002\u0013\u00119\u0003C\u0005\u00036\u0001\u0011\r\u0011\"\u0001\u00038!A!q\b\u0001!\u0002\u0013\u0011I\u0004\u0003\b\u0003B\u0001!\t\u0011!B\u0001\u0002\u0003\u0006IAa\u0011\t\u0013\t%\u0003A1A\u0005\u0002\t-\u0003\u0002\u0003B*\u0001\u0001\u0006IA!\u0014\t\u0013\tU\u0003A1A\u0005\u0002\t]\u0003\u0002\u0003B0\u0001\u0001\u0006IA!\u0017\t\u0013\t\u0005\u0004A1A\u0005\u0002\t\r\u0004\u0002\u0003B6\u0001\u0001\u0006IA!\u001a\t\u000f\t5\u0004\u0001\"\u0001\u0003p!I!\u0011\u000f\u0001C\u0002\u0013\u0005!1\u000f\u0005\t\u0005w\u0002\u0001\u0015!\u0003\u0003v!I!Q\u0010\u0001C\u0002\u0013\u0005!q\u0010\u0005\t\u0005\u000f\u0003\u0001\u0015!\u0003\u0003\u0002\"I!\u0011\u0012\u0001C\u0002\u0013\u0005!1\u0012\u0005\t\u0005'\u0003\u0001\u0015!\u0003\u0003\u000e\"A!Q\u0013\u0001!\u0002\u001b\u00119\nC\u0004\u0003~\u0002!\tEa@\t\u000f\r\r\u0001\u0001\"\u0011\u0004\u0006!91q\u0002\u0001\u0005\u0002\rE\u0001bBB\r\u0001\u0011\u000511\u0004\u0005\b\u0007G\u0001A\u0011AB\u000e\u0011\u001d\u0019)\u0003\u0001C\u0001\u0007OAqa!\n\u0001\t\u0003\u0019\u0019\u0004C\u0004\u0004L\u0001!\te!\u0014\t\u000f\rU\u0003\u0001\"\u0003\u0004X!I1q\f\u0001A\u0002\u0013%1\u0011\r\u0005\n\u0007S\u0002\u0001\u0019!C\u0005\u0007WB\u0001ba\u001c\u0001A\u0003&11\r\u0005\b\u0007g\u0002A\u0011\u0001B\u0001\u0011)\u0019)\b\u0001EC\u0002\u0013%1q\u000f\u0005\b\u0007s\u0002A\u0011AB>\u0011\u001d\u0019i\b\u0001C\u0001\u0007\u007fBqa! \u0001\t\u0003\u0019\t\nC\u0005\u0004\u0016\u0002\u0001\r\u0011\"\u0003\u0004b!I1q\u0013\u0001A\u0002\u0013%1\u0011\u0014\u0005\t\u0007;\u0003\u0001\u0015)\u0003\u0004d!91\u0011\u0015\u0001\u0005B\r\r\u0006\u0002CBS\u0001\u0011\u0005cM!\u0001\t\u0011\r\u001d\u0006\u0001\"\u0011g\u0007SC\u0011ba+\u0001\u0001\u0004%\ta!\u0019\t\u0013\r5\u0006\u00011A\u0005\u0002\r=\u0006\u0002CBZ\u0001\u0001\u0006Kaa\u0019\t\u000f\r]\u0006\u0001\"\u0001\u0003\u0002!91\u0011\u0018\u0001\u0005\u0012\rm\u0006bBB_\u0001\u0011E!\u0011\u0001\u0005\n\u0007\u007f\u0003!\u0019!C\u0005\u0007\u0003D\u0001ba6\u0001A\u0003%11\u0019\u0005\b\u00073\u0004A\u0011BBn\u0011\u001d\u0019y\u0010\u0001C\u0003\t\u0003Aq\u0001b\u0004\u0001\t\u0003!\t\u0002C\u0004\u0005\u001e\u0001!\t\u0001b\b\t\u000f\u00115\u0002\u0001\"\u0003\u0003\u0002!9Aq\u0006\u0001\u0005B\u0011E\u0002B\u0002C\u001a\u0001\u0011\u0005sN\u0002\u0004\u0003\u001c\u0002\u0011!Q\u0014\u0005\u000b\u0005CS&\u0011!Q\u0001\n\t\r\u0006B\u0003B`5\n\u0005\t\u0015a\u0003\u00020!9\u0011Q\u000b.\u0005\u0002\t\u0005\u0007\u0002\u0003Bf5\u0002\u0006iA!4\t\u0011\tM'\f)A\u0007\u0005+DqA!:[\t\u000b\u00119\u000fC\u0004\u0003tj#\tA!>\t\u000f\u0011U\u0002\u0001\"\u0003\u0004b\ty\u0011i\u0019;peNK8\u000f^3n\u00136\u0004HN\u0003\u0002fM\u0006)\u0011m\u0019;pe*\tq-\u0001\u0003bW.\f7C\u0001\u0001j!\tQ7.D\u0001e\u0013\taGMA\nFqR,g\u000eZ3e\u0003\u000e$xN]*zgR,W.\u0001\u0003oC6,7\u0001A\u000b\u0002aB\u0011\u0011O\u001f\b\u0003eb\u0004\"a\u001d<\u000e\u0003QT!!\u001e8\u0002\rq\u0012xn\u001c;?\u0015\u00059\u0018!B:dC2\f\u0017BA=w\u0003\u0019\u0001&/\u001a3fM&\u00111\u0010 \u0002\u0007'R\u0014\u0018N\\4\u000b\u0005e4\u0018!\u00028b[\u0016\u0004\u0013!E1qa2L7-\u0019;j_:\u001cuN\u001c4jOB!\u0011\u0011AA\b\u001b\t\t\u0019A\u0003\u0003\u0002\u0006\u0005\u001d\u0011AB2p]\u001aLwM\u0003\u0003\u0002\n\u0005-\u0011\u0001\u0003;za\u0016\u001c\u0018MZ3\u000b\u0005\u00055\u0011aA2p[&!\u0011\u0011CA\u0002\u0005\u0019\u0019uN\u001c4jO\u0006Y1\r\\1tg2{\u0017\rZ3s!\u0011\t9\"!\t\u000e\u0005\u0005e!\u0002BA\u000e\u0003;\tA\u0001\\1oO*\u0011\u0011qD\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002$\u0005e!aC\"mCN\u001cHj\\1eKJ\fq\u0003Z3gCVdG/\u0012=fGV$\u0018n\u001c8D_:$X\r\u001f;\u0011\r\u0005%\u00121FA\u0018\u001b\u00051\u0018bAA\u0017m\n1q\n\u001d;j_:\u0004B!!\r\u000285\u0011\u00111\u0007\u0006\u0004\u0003k1\u0018AC2p]\u000e,(O]3oi&!\u0011\u0011HA\u001a\u0005A)\u00050Z2vi&|gnQ8oi\u0016DH/A\u0007hk\u0006\u0014H-[1o!J|\u0007o]\u000b\u0003\u0003\u007f\u0001b!!\u000b\u0002,\u0005\u0005\u0003c\u00016\u0002D%\u0019\u0011Q\t3\u0003\u000bA\u0013x\u000e]:\u0002\u001d\u001d,\u0018M\u001d3jC:\u0004&o\u001c9tA\u0005)1/\u001a;vaB!\u0011QJA)\u001b\t\tyEC\u0002\u0002J\u0011LA!a\u0015\u0002P\t\u0001\u0012i\u0019;peNK8\u000f^3n'\u0016$X\u000f]\u0001\u0007y%t\u0017\u000e\u001e \u0015\u001d\u0005e\u00131LA/\u0003?\n\t'a\u0019\u0002fA\u0011!\u000e\u0001\u0005\u0006[&\u0001\r\u0001\u001d\u0005\u0006}&\u0001\ra \u0005\b\u0003'I\u0001\u0019AA\u000b\u0011\u001d\t)#\u0003a\u0001\u0003OAq!a\u000f\n\u0001\u0004\ty\u0004C\u0004\u0002J%\u0001\r!a\u0013\u0002\u0007ULG-\u0006\u0002\u0002lA!\u0011\u0011FA7\u0013\r\tyG\u001e\u0002\u0005\u0019>tw-\u0001\u0003vS\u0012\u0004\u0013!\u00067pO\u0012+\u0017\r\u001a'fiR,'\u000fT5ti\u0016tWM]\u000b\u0003\u0003o\u0002b!!\u000b\u0002,\u0005e\u0004c\u00016\u0002|%\u0019\u0011Q\u00103\u0003\u0011\u0005\u001bGo\u001c:SK\u001a\f\u0011\u0004\\8h\t\u0016\fG\rT3ui\u0016\u0014H*[:uK:,'o\u0018\u0013fcR!\u00111QAE!\u0011\tI#!\"\n\u0007\u0005\u001deO\u0001\u0003V]&$\b\"CAF\u001b\u0005\u0005\t\u0019AA<\u0003\rAH%M\u0001\u0017Y><G)Z1e\u0019\u0016$H/\u001a:MSN$XM\\3sA!\u001aa\"!%\u0011\t\u0005%\u00121S\u0005\u0004\u0003+3(\u0001\u0003<pY\u0006$\u0018\u000e\\3\u0002\u001d}#\u0017P\\1nS\u000e\f5mY3tgV\u0011\u00111\u0014\t\u0004U\u0006u\u0015bAAPI\niA)\u001f8b[&\u001c\u0017iY2fgN\fqb\u00183z]\u0006l\u0017nY!dG\u0016\u001c8\u000fI\u0001\fif\u0004X\rZ*zgR,W.\u0006\u0002\u0002(B1\u0011\u0011VAX\u0003gk!!a+\u000b\u0007\u00055f-\u0001\u0003vi&d\u0017\u0002BAY\u0003W\u0013\u0011b\u00149uS>tg+\u00197\u0011\t\u0005%\u0012QW\u0005\u0004\u0003o3(AB!osJ+g-A\busB,GmU=ti\u0016lw\fJ3r)\u0011\t\u0019)!0\t\u0013\u0005-%#!AA\u0002\u0005\u001d\u0016\u0001\u0004;za\u0016$7+_:uK6\u0004\u0013\u0001C:fiRLgnZ:\u0016\u0005\u0005\u0015\u0007\u0003BAd\u0003\u001bt1A[Ae\u0013\r\tY\rZ\u0001\f\u0003\u000e$xN]*zgR,W.\u0003\u0003\u0002P\u0006E'\u0001C*fiRLgnZ:\u000b\u0007\u0005-G-A\u0005tKR$\u0018N\\4tA\u0005ARO\\2bk\u001eDG/\u0012=dKB$\u0018n\u001c8IC:$G.\u001a:\u0016\u0005\u0005e\u0007\u0003BAn\u0003CtA!a\u0006\u0002^&!\u0011q\\A\r\u0003\u0019!\u0006N]3bI&!\u00111]As\u0005a)fnY1vO\"$X\t_2faRLwN\u001c%b]\u0012dWM\u001d\u0006\u0005\u0003?\fI\"A\u0007uQJ,\u0017\r\u001a$bGR|'/_\u000b\u0003\u0003W\u0004B!!<\u0002t6\u0011\u0011q\u001e\u0006\u0004\u0003c4\u0017\u0001\u00033jgB\fGo\u00195\n\t\u0005U\u0018q\u001e\u0002\u0019\u001b>t\u0017\u000e^8sC\ndW\r\u00165sK\u0006$g)Y2u_JL\u0018A\u0004;ie\u0016\fGMR1di>\u0014\u0018\u0010I\u0001\u0014GJ,\u0017\r^3Es:\fW.[2BG\u000e,7o\u001d\u000b\u0003\u00037\u000bQ\u0002Z=oC6L7-Q2dKN\u001c\u0018\u0001\u00057pO\u000e{gNZ5hkJ\fG/[8o)\t\t\u0019)\u0001\u0006tsN$X-\\%na2,\"!!\u0017\u0002\u001bML8\u000f^3n\u0003\u000e$xN](g)\u0019\tIHa\u0003\u0003\u0010!9!QB\u000fA\u0002\u0005\u0005\u0013!\u00029s_B\u001c\b\"B7\u001e\u0001\u0004\u0001\u0018aB1di>\u0014xJ\u001a\u000b\u0007\u0003s\u0012)Ba\u0006\t\u000f\t5a\u00041\u0001\u0002B!)QN\ba\u0001aR!\u0011\u0011\u0010B\u000e\u0011\u001d\u0011ia\ba\u0001\u0003\u0003\nAa\u001d;paR!\u00111\u0011B\u0011\u0011\u0019)\u0007\u00051\u0001\u0002z\u0005YQM^3oiN#(/Z1n+\t\u00119\u0003\u0005\u0003\u0003*\t=RB\u0001B\u0016\u0015\r\u0011iCZ\u0001\u0006KZ,g\u000e^\u0005\u0005\u0005c\u0011YCA\u0006Fm\u0016tGo\u0015;sK\u0006l\u0017\u0001D3wK:$8\u000b\u001e:fC6\u0004\u0013!\u00037pO\u001aKG\u000e^3s+\t\u0011I\u0004\u0005\u0003\u0003*\tm\u0012\u0002\u0002B\u001f\u0005W\u0011Q\u0002T8hO&twMR5mi\u0016\u0014\u0018A\u00037pO\u001aKG\u000e^3sA\u0005I\u0013m[6bI\u0005\u001cGo\u001c:%\u0003\u000e$xN]*zgR,W.S7qY\u0012\"S.\u0019:lKJdunZ4j]\u001e\u0004BA!\u000b\u0003F%!!q\tB\u0016\u0005Qi\u0015M]6fe2{wmZ5oO\u0006#\u0017\r\u001d;fe\u0006\u0019An\\4\u0016\u0005\t5\u0003\u0003\u0002B\u0015\u0005\u001fJAA!\u0015\u0003,\tqAj\\4hS:<\u0017\tZ1qi\u0016\u0014\u0018\u0001\u00027pO\u0002\n\u0011b]2iK\u0012,H.\u001a:\u0016\u0005\te\u0003c\u00016\u0003\\%\u0019!Q\f3\u0003\u0013M\u001b\u0007.\u001a3vY\u0016\u0014\u0018AC:dQ\u0016$W\u000f\\3sA\u0005A\u0001O]8wS\u0012,'/\u0006\u0002\u0003fA\u0019!Na\u001a\n\u0007\t%DM\u0001\tBGR|'OU3g!J|g/\u001b3fe\u0006I\u0001O]8wS\u0012,'\u000fI\u0001\fI\u0016\fG\rT3ui\u0016\u00148/\u0006\u0002\u0002z\u0005IQ.Y5mE>DXm]\u000b\u0003\u0005k\u0002B!!<\u0003x%!!\u0011PAx\u0005%i\u0015-\u001b7c_b,7/\u0001\u0006nC&d'm\u001c=fg\u0002\n1\u0002Z5ta\u0006$8\r[3sgV\u0011!\u0011\u0011\t\u0005\u0003[\u0014\u0019)\u0003\u0003\u0003\u0006\u0006=(a\u0003#jgB\fGo\u00195feN\fA\u0002Z5ta\u0006$8\r[3sg\u0002\n!\u0002Z5ta\u0006$8\r[3s+\t\u0011i\t\u0005\u0003\u00022\t=\u0015\u0002\u0002BI\u0003g\u0011\u0001$\u0012=fGV$\u0018n\u001c8D_:$X\r\u001f;Fq\u0016\u001cW\u000f^8s\u0003-!\u0017n\u001d9bi\u000eDWM\u001d\u0011\u0002)Q,'/\\5oCRLwN\\\"bY2\u0014\u0017mY6t!\u0015\u0011IJ\u0017B|\u001b\u0005\u0001!\u0001\u0006+fe6Lg.\u0019;j_:\u001c\u0015\r\u001c7cC\u000e\\7/\u0006\u0003\u0003 \n56c\u0001.\u00024\u0006\u0011R\u000f]*ue\u0016\fW\u000eV3s[&t\u0017\r^3e!\u0019\t\tD!*\u0003*&!!qUA\u001a\u0005\u00191U\u000f^;sKB!!1\u0016BW\u0019\u0001!qAa,[\u0005\u0004\u0011\tLA\u0001U#\u0011\u0011\u0019L!/\u0011\t\u0005%\"QW\u0005\u0004\u0005o3(a\u0002(pi\"Lgn\u001a\t\u0005\u0003S\u0011Y,C\u0002\u0003>Z\u00141!\u00118z\u0003\t)7\r\u0006\u0003\u0003D\n%G\u0003\u0002Bc\u0005\u000f\u0004RA!'[\u0005SCqAa0^\u0001\b\ty\u0003C\u0004\u0003\"v\u0003\rAa)\u0002\t\u0011|g.\u001a\t\u0007\u0003c\u0011yM!+\n\t\tE\u00171\u0007\u0002\b!J|W.[:f\u0003\r\u0011XM\u001a\t\u0007\u0005/\u0014\tO!4\u000e\u0005\te'\u0002\u0002Bn\u0005;\fa!\u0019;p[&\u001c'\u0002BA\u001b\u0005?TA!!,\u0002\u001e%!!1\u001dBm\u0005=\tEo\\7jGJ+g-\u001a:f]\u000e,\u0017aA1eIR!\u00111\u0011Bu\u0011\u001d\u0011Y\u000f\u0019a\u0001\u0005[\f\u0011A\u001d\t\u0005\u0003/\u0011y/\u0003\u0003\u0003r\u0006e!\u0001\u0003*v]:\f'\r\\3\u0002#Q,'/\\5oCRLwN\u001c$viV\u0014X-\u0006\u0002\u0003$B\u0019!N!?\n\u0007\tmHM\u0001\u0006UKJl\u0017N\\1uK\u0012\fab\u001e5f]R+'/\\5oCR,G-\u0006\u0002\u0004\u0002A1\u0011\u0011\u0007BS\u0005o\f\u0011cZ3u/\",g\u000eV3s[&t\u0017\r^3e+\t\u00199\u0001\u0005\u0004\u0004\n\r-!q_\u0007\u0003\u0005;LAa!\u0004\u0003^\ny1i\\7qY\u0016$\u0018n\u001c8Ti\u0006<W-\u0001\u0006m_>\\W\u000f\u001d*p_R,\"aa\u0005\u0011\u0007)\u001c)\"C\u0002\u0004\u0018\u0011\u0014\u0001#\u00138uKJt\u0017\r\\!di>\u0014(+\u001a4\u0002\u0011\u001d,\u0018M\u001d3jC:,\"a!\b\u0011\u0007)\u001cy\"C\u0002\u0004\"\u0011\u0014Q\u0002T8dC2\f5\r^8s%\u00164\u0017AD:zgR,WnR;be\u0012L\u0017M\\\u0001\u0005I\u0011Lg\u000f\u0006\u0003\u0004*\r=\u0002c\u00016\u0004,%\u00191Q\u00063\u0003\u0013\u0005\u001bGo\u001c:QCRD\u0007BBB\u0019s\u0001\u0007\u0001/A\u0005bGR|'OT1nKR!1\u0011FB\u001b\u0011\u001d\u00199D\u000fa\u0001\u0007s\tA\u0001]1uQB)11HB#a:!1QHB!\u001d\r\u00198qH\u0005\u0002o&\u001911\t<\u0002\u000fA\f7m[1hK&!1qIB%\u0005!IE/\u001a:bE2,'bAB\"m\u0006i1\r\\1tg&\u001c7+_:uK6,\"aa\u0014\u0011\u0007)\u001c\t&C\u0002\u0004T\u0011\u00141\"Q2u_J\u001c\u0016p\u001d;f[\u0006Q\u0011\r\u001c7N_\u0012,H.Z:\u0016\u0005\re\u0003#BB\u001e\u00077\u0002\u0018\u0002BB/\u0007\u0013\u0012A\u0001T5ti\u0006aq,\u001b8ji&\fG.\u001b>fIV\u001111\r\t\u0005\u0003S\u0019)'C\u0002\u0004hY\u0014qAQ8pY\u0016\fg.\u0001\t`S:LG/[1mSj,Gm\u0018\u0013fcR!\u00111QB7\u0011%\tYIPA\u0001\u0002\u0004\u0019\u0019'A\u0007`S:LG/[1mSj,G\r\t\u0015\u0004\u007f\u0005E\u0015!E1tg\u0016\u0014H/\u00138ji&\fG.\u001b>fI\u00061ql\u001d;beR,\"A!'\u0002\u000bM$\u0018M\u001d;\u0015\u0005\te\u0015!\u0006:fO&\u001cH/\u001a:P]R+'/\\5oCRLwN\\\u000b\u0005\u0007\u0003\u001by\t\u0006\u0003\u0002\u0004\u000e\r\u0005\u0002CBC\u0007\u0012\u0005\raa\"\u0002\t\r|G-\u001a\t\u0007\u0003S\u0019Ii!$\n\u0007\r-eO\u0001\u0005=Eft\u0017-\\3?!\u0011\u0011Yka$\u0005\u000f\t=6I1\u0001\u00032R!\u00111QBJ\u0011\u001d\u0019)\t\u0012a\u0001\u0005[\f1\u0002^3s[&t\u0017\r^5oO\u0006yA/\u001a:nS:\fG/\u001b8h?\u0012*\u0017\u000f\u0006\u0003\u0002\u0004\u000em\u0005\"CAF\r\u0006\u0005\t\u0019AB2\u00031!XM]7j]\u0006$\u0018N\\4!Q\r9\u0015\u0011S\u0001\ni\u0016\u0014X.\u001b8bi\u0016$\"a!\u0001\u0002\u001d\u0019Lg.\u00197UKJl\u0017N\\1uK\u0006i\u0011n\u001d+fe6Lg.\u0019;j]\u001e$\"aa\u0019\u0002\u0011\u0005\u0014wN\u001d;j]\u001e\fA\"\u00192peRLgnZ0%KF$B!a!\u00042\"I\u00111\u0012'\u0002\u0002\u0003\u000711M\u0001\nC\n|'\u000f^5oO\u0002B3!TAI\u0003\u0015\t'm\u001c:u\u0003=\u0019'/Z1uKN\u001b\u0007.\u001a3vY\u0016\u0014HC\u0001B-\u00035\u0019Ho\u001c9TG\",G-\u001e7fe\u0006QQ\r\u001f;f]NLwN\\:\u0016\u0005\r\r\u0007\u0003CB\u0005\u0007\u000b\u001cI-a-\n\t\r\u001d'Q\u001c\u0002\u0012\u0007>t7-\u001e:sK:$\b*Y:i\u001b\u0006\u0004\b\u0007BBf\u0007'\u0004RA[Bg\u0007#L1aa4e\u0005-)\u0005\u0010^3og&|g.\u00133\u0011\t\t-61\u001b\u0003\f\u0007+\u0014\u0016\u0011!A\u0001\u0006\u0003\u0011\tLA\u0002`II\n1\"\u001a=uK:\u001c\u0018n\u001c8tA\u0005ia-\u001b8e\u000bb$XM\\:j_:,Ba!8\u0004bR!1q\\Bv!\u0011\u0011Yk!9\u0005\u000f\t=6K1\u0001\u0004dF!!1WBs!\rQ7q]\u0005\u0004\u0007S$'!C#yi\u0016t7/[8o\u0011\u001d\u0019io\u0015a\u0001\u0007_\f1!\u001a=u!\u0015Q7QZBpQ\r\u001961\u001f\t\u0005\u0007k\u001cY0\u0004\u0002\u0004x*\u00191\u0011 <\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0004~\u000e](a\u0002;bS2\u0014XmY\u0001\u0012e\u0016<\u0017n\u001d;fe\u0016CH/\u001a8tS>tW\u0003\u0002C\u0002\t\u000f!B\u0001\"\u0002\u0005\nA!!1\u0016C\u0004\t\u001d\u0011y\u000b\u0016b\u0001\u0007GDqa!<U\u0001\u0004!Y\u0001E\u0003k\u0007\u001b$)\u0001K\u0002U\u0007g\f\u0011\"\u001a=uK:\u001c\u0018n\u001c8\u0016\t\u0011MAq\u0003\u000b\u0005\t+!I\u0002\u0005\u0003\u0003,\u0012]Aa\u0002BX+\n\u000711\u001d\u0005\b\u0007[,\u0006\u0019\u0001C\u000e!\u0015Q7Q\u001aC\u000b\u00031A\u0017m]#yi\u0016t7/[8o)\u0011\u0019\u0019\u0007\"\t\t\u000f\r5h\u000b1\u0001\u0005$A\"AQ\u0005C\u0015!\u0015Q7Q\u001aC\u0014!\u0011\u0011Y\u000b\"\u000b\u0005\u0019\u0011-B\u0011EA\u0001\u0002\u0003\u0015\taa9\u0003\u0007}#3'\u0001\bm_\u0006$W\t\u001f;f]NLwN\\:\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012\u0001]\u0001\naJLg\u000e\u001e+sK\u0016\fq\"[:UsB,GmR;be\u0012L\u0017M\u001c\u0015\u0004\u0001\u0011e\u0002\u0003\u0002C\u001e\t\u007fi!\u0001\"\u0010\u000b\u0007\reh-\u0003\u0003\u0005B\u0011u\"aC%oi\u0016\u0014h.\u00197Ba&\u0004")
@InternalApi
public class ActorSystemImpl
extends ExtendedActorSystem {
    private ActorSystemImpl _start;
    private final String name;
    private final ClassLoader classLoader;
    private final Option<Props> guardianProps;
    private final long uid;
    private volatile Option<ActorRef> logDeadLetterListener;
    private final DynamicAccess _dynamicAccess;
    private Object typedSystem;
    private final ActorSystem.Settings settings;
    private final MonitorableThreadFactory threadFactory;
    private final EventStream eventStream;
    private final LoggingFilter logFilter;
    public final MarkerLoggingAdapter akka$actor$ActorSystemImpl$$markerLogging;
    private final LoggingAdapter log;
    private final Scheduler scheduler;
    private final ActorRefProvider provider;
    private final Mailboxes mailboxes;
    private final Dispatchers dispatchers;
    private final ExecutionContextExecutor dispatcher;
    private final TerminationCallbacks<Terminated> terminationCallbacks;
    private volatile boolean _initialized;
    private volatile boolean terminating;
    private volatile boolean aborting;
    private final ConcurrentHashMap<ExtensionId<?>, Object> extensions;
    private volatile boolean bitmap$0;

    @Override
    public String name() {
        return this.name;
    }

    public Option<Props> guardianProps() {
        return this.guardianProps;
    }

    @Override
    public long uid() {
        return this.uid;
    }

    private Option<ActorRef> logDeadLetterListener() {
        return this.logDeadLetterListener;
    }

    private void logDeadLetterListener_$eq(Option<ActorRef> x$1) {
        this.logDeadLetterListener = x$1;
    }

    private DynamicAccess _dynamicAccess() {
        return this._dynamicAccess;
    }

    public Object typedSystem() {
        return this.typedSystem;
    }

    public void typedSystem_$eq(Object x$1) {
        this.typedSystem = x$1;
    }

    @Override
    public final ActorSystem.Settings settings() {
        return this.settings;
    }

    public Thread.UncaughtExceptionHandler uncaughtExceptionHandler() {
        return new Thread.UncaughtExceptionHandler(this){
            private final /* synthetic */ ActorSystemImpl $outer;

            public void uncaughtException(Thread thread2, Throwable cause) {
                Option<Throwable> option;
                Throwable throwable = cause;
                if (throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty() ? true : (throwable instanceof InterruptedException ? true : (throwable instanceof NotImplementedError ? true : throwable instanceof ControlThrowable))) {
                    this.$outer.log().error(cause, "Uncaught error from thread [{}]", (Object)thread2.getName());
                    return;
                }
                if (cause instanceof IncompatibleClassChangeError && cause.getMessage().startsWith("akka")) {
                    System.err.println(StringOps$.MODULE$.stripMargin$extension(Predef$.MODULE$.augmentString(new StringBuilder(569).append("Detected ").append(cause.getClass().getName()).append(" error, which MAY be caused by incompatible Akka versions on the classpath.\n                  | Please note that a given Akka version MUST be the same across all modules of Akka that you are using,\n                  | e.g. if you use akka-actor [").append(Version$.MODULE$.current()).append(" (resolved from current classpath)] all other core\n                  | Akka modules MUST be of the same version. External projects like Alpakka, Persistence plugins or Akka\n                  | HTTP etc. have their own version numbers - please make sure you're using a compatible set of libraries.\n                 ").toString())).replaceAll("[\r\n]", ""));
                }
                if (this.$outer.settings().JvmExitOnFatalError()) {
                    try {
                        this.logFatalError("shutting down JVM since 'akka.jvm-exit-on-fatal-error' is enabled for", cause, thread2);
                    }
                    finally {
                        System.exit(-1);
                    }
                    return;
                }
                try {
                    this.logFatalError("shutting down", cause, thread2);
                }
                finally {
                    this.$outer.terminate();
                }
            }

            private void logFatalError(String message, Throwable cause, Thread thread2) {
                System.err.print("Uncaught error from thread [");
                System.err.print(thread2.getName());
                System.err.print("]: ");
                System.err.print(cause.getMessage());
                System.err.print(", ");
                System.err.print(message);
                System.err.print(" ActorSystem[");
                System.err.print(this.$outer.name());
                System.err.println("]");
                System.err.flush();
                cause.printStackTrace(System.err);
                System.err.flush();
                this.$outer.akka$actor$ActorSystemImpl$$markerLogging.error(LogMarker$.MODULE$.Security(), cause, new StringBuilder(51).append("Uncaught error from thread [{}]: ").append(cause.getMessage()).append(", ").append(message).append(" ActorSystem[{}]").toString(), (Object)thread2.getName(), (Object)this.$outer.name());
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    @Override
    public final MonitorableThreadFactory threadFactory() {
        return this.threadFactory;
    }

    public DynamicAccess createDynamicAccess() {
        return new ReflectiveDynamicAccess(this.classLoader);
    }

    @Override
    public DynamicAccess dynamicAccess() {
        return this._dynamicAccess();
    }

    @Override
    public void logConfiguration() {
        this.log().info(this.settings().toString());
    }

    @Override
    public ActorSystemImpl systemImpl() {
        return this;
    }

    @Override
    public ActorRef systemActorOf(Props props, String name) {
        return this.systemGuardian().underlying().attachChild(props, name, true);
    }

    @Override
    public ActorRef actorOf(Props props, String name) {
        if (this.guardianProps().isEmpty()) {
            return this.guardian().underlying().attachChild(props, name, false);
        }
        String message = this.isTypedGuardian() ? new StringBuilder(313).append("cannot create top-level actor [").append(name).append("] from the outside on a typed ActorSystem.  In a typed ActorSystem, ").append("top-level actors should be spawned as children of the guardian behavior; if this is not possible (e.g. this ").append("actorOf call is in a library), a classic ActorSystem should be created and used to spawn top-level actors.").toString() : new StringBuilder(90).append("cannot create top-level actor [").append(name).append("] from the outside on ActorSystem with custom user guardian").toString();
        throw new UnsupportedOperationException(message);
    }

    @Override
    public ActorRef actorOf(Props props) {
        if (this.guardianProps().isEmpty()) {
            return this.guardian().underlying().attachChild(props, false);
        }
        String message = this.isTypedGuardian() ? "cannot create top-level actor from the outside on a typed ActorSystem.  In a typed ActorSystem, top-level actors should be spawned as children of the guardian behavior; if this is not possible (e.g. this actorOf call is in a library), a classic ActorSystem should be created and used to spawn top-level actors." : "cannot create top-level actor from the outside on ActorSystem with custom user guardian";
        throw new UnsupportedOperationException(message);
    }

    @Override
    public void stop(ActorRef actor) {
        ActorPath path = actor.path();
        ActorPath guard = this.guardian().path();
        ActorPath sys = this.systemGuardian().path();
        ActorPath actorPath = path.parent();
        ActorPath actorPath2 = guard;
        ActorPath actorPath3 = actorPath;
        if (!(actorPath2 != null ? !actorPath2.equals(actorPath3) : actorPath3 != null)) {
            LocalActorRef qual$1 = this.guardian();
            StopChild x$1 = new StopChild(actor);
            ActorRef x$2 = qual$1.$bang$default$2(x$1);
            qual$1.$bang(x$1, x$2);
            return;
        }
        ActorPath actorPath4 = sys;
        ActorPath actorPath5 = actorPath;
        if (!(actorPath4 != null ? !actorPath4.equals(actorPath5) : actorPath5 != null)) {
            LocalActorRef qual$2 = this.systemGuardian();
            StopChild x$3 = new StopChild(actor);
            ActorRef x$4 = qual$2.$bang$default$2(x$3);
            qual$2.$bang(x$3, x$4);
            return;
        }
        ((InternalActorRef)actor).stop();
    }

    @Override
    public EventStream eventStream() {
        return this.eventStream;
    }

    @Override
    public LoggingFilter logFilter() {
        return this.logFilter;
    }

    @Override
    public LoggingAdapter log() {
        return this.log;
    }

    @Override
    public Scheduler scheduler() {
        return this.scheduler;
    }

    @Override
    public ActorRefProvider provider() {
        return this.provider;
    }

    @Override
    public ActorRef deadLetters() {
        return this.provider().deadLetters();
    }

    @Override
    public Mailboxes mailboxes() {
        return this.mailboxes;
    }

    @Override
    public Dispatchers dispatchers() {
        return this.dispatchers;
    }

    @Override
    public ExecutionContextExecutor dispatcher() {
        return this.dispatcher;
    }

    @Override
    public Future<Terminated> whenTerminated() {
        return this.terminationCallbacks.terminationFuture();
    }

    @Override
    public CompletionStage<Terminated> getWhenTerminated() {
        return FutureConverters$.MODULE$.toJava(this.whenTerminated());
    }

    @Override
    public InternalActorRef lookupRoot() {
        return this.provider().rootGuardian();
    }

    @Override
    public LocalActorRef guardian() {
        return this.provider().guardian();
    }

    @Override
    public LocalActorRef systemGuardian() {
        return this.provider().systemGuardian();
    }

    @Override
    public ActorPath $div(String actorName) {
        return this.guardian().path().$div(actorName);
    }

    @Override
    public ActorPath $div(Iterable<String> path) {
        return this.guardian().path().$div(path);
    }

    @Override
    public ActorSystem classicSystem() {
        return this;
    }

    private List<String> allModules() {
        return (List)scala.package$.MODULE$.List().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new String[]{"akka-actor", "akka-actor-testkit-typed", "akka-actor-typed", "akka-cluster", "akka-cluster-metrics", "akka-cluster-sharding", "akka-cluster-sharding-typed", "akka-cluster-tools", "akka-cluster-typed", "akka-coordination", "akka-discovery", "akka-distributed-data", "akka-testkit", "akka-multi-node-testkit", "akka-persistence", "akka-persistence-query", "akka-persistence-shared", "akka-persistence-testkit", "akka-persistence-typed", "akka-pki", "akka-protobuf", "akka-protobuf-v3", "akka-remote", "akka-serialization-jackson", "akka-slf4j", "akka-stream", "akka-stream-testkit", "akka-stream-typed", "akka-stream-testkit"}));
    }

    private boolean _initialized() {
        return this._initialized;
    }

    private void _initialized_$eq(boolean x$1) {
        this._initialized = x$1;
    }

    public void assertInitialized() {
        if (!this._initialized()) {
            throw new IllegalStateException("The calling code expected that the ActorSystem was initialized but it wasn't yet. This is probably a bug in the ActorSystem initialization sequence often related to initialization of extensions. Please report at https://github.com/akka/akka/issues.");
        }
    }

    private ActorSystemImpl _start$lzycompute() {
        ActorSystemImpl actorSystemImpl = this;
        synchronized (actorSystemImpl) {
            if (!this.bitmap$0) {
                this._start = this.liftedTree2$1();
                this.bitmap$0 = true;
            }
        }
        return this._start;
    }

    private ActorSystemImpl _start() {
        if (!this.bitmap$0) {
            return this._start$lzycompute();
        }
        return this._start;
    }

    public ActorSystemImpl start() {
        return this._start();
    }

    @Override
    public <T> void registerOnTermination(Function0<T> code) {
        this.registerOnTermination(new Runnable(null, code){
            private final Function0 code$1;

            public void run() {
                this.code$1.apply();
            }
            {
                this.code$1 = code$1;
            }
        });
    }

    @Override
    public void registerOnTermination(Runnable code) {
        this.terminationCallbacks.add(code);
    }

    private boolean terminating() {
        return this.terminating;
    }

    private void terminating_$eq(boolean x$1) {
        this.terminating = x$1;
    }

    @Override
    public Future<Terminated> terminate() {
        Object object;
        this.terminating_$eq(true);
        if (this.settings().CoordinatedShutdownRunByActorSystemTerminate() && !this.aborting()) {
            object = ((CoordinatedShutdown)CoordinatedShutdown$.MODULE$.apply(this)).run(CoordinatedShutdown$ActorSystemTerminateReason$.MODULE$);
        } else {
            this.finalTerminate();
            object = BoxedUnit.UNIT;
        }
        return this.whenTerminated();
    }

    @Override
    public void finalTerminate() {
        this.terminating_$eq(true);
        if (!this.settings().LogDeadLettersDuringShutdown()) {
            this.logDeadLetterListener().foreach((Function1<ActorRef, Object> & Serializable)actor -> {
                this.stop(actor);
                return BoxedUnit.UNIT;
            });
        }
        this.guardian().stop();
    }

    @Override
    public boolean isTerminating() {
        return this.terminating() || this.aborting() || ((CoordinatedShutdown)CoordinatedShutdown$.MODULE$.apply(this)).shutdownReason().isDefined();
    }

    public boolean aborting() {
        return this.aborting;
    }

    public void aborting_$eq(boolean x$1) {
        this.aborting = x$1;
    }

    public void abort() {
        this.aborting_$eq(true);
        this.terminate();
    }

    public Scheduler createScheduler() {
        return (Scheduler)this.dynamicAccess().createInstanceFor(this.settings().SchedulerClass(), new $colon$colon<Nothing$>((Nothing$)((Object)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(Config.class), this.settings().config())), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(LoggingAdapter.class), this.log())), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(ThreadFactory.class), this.threadFactory().withName(new StringBuilder(10).append(this.threadFactory().name()).append("-scheduler").toString()))), Nil$.MODULE$))), ClassTag$.MODULE$.apply(Scheduler.class)).get();
    }

    public void stopScheduler() {
        Scheduler scheduler = this.scheduler();
        if (scheduler instanceof Closeable) {
            Scheduler scheduler2 = scheduler;
            ((Closeable)((Object)scheduler2)).close();
            return;
        }
    }

    private ConcurrentHashMap<ExtensionId<?>, Object> extensions() {
        return this.extensions;
    }

    private <T extends Extension> T findExtension(ExtensionId<T> ext) {
        Object object;
        while ((object = this.extensions().get(ext)) instanceof CountDownLatch) {
            CountDownLatch countDownLatch = (CountDownLatch)object;
            package$.MODULE$.blocking(() -> {
                long awaitMillis = this.settings().CreationTimeout().duration().toMillis();
                if (!countDownLatch.await(awaitMillis, TimeUnit.MILLISECONDS)) {
                    ExtensionId extensionId = ext;
                    SerializationExtension$ serializationExtension$ = SerializationExtension$.MODULE$;
                    throw new IllegalStateException(new StringBuilder(43).append("Initialization of [").append(ext).append("] took more than [").append(awaitMillis).append(" ms]. ").append((Object)(!(extensionId != null ? !((Object)extensionId).equals(serializationExtension$) : serializationExtension$ != null) ? "A serializer must not access the SerializationExtension from its constructor. Use lazy init." : "Could be deadlock due to cyclic initialization of extensions.")).toString());
                }
            });
        }
        if (object instanceof Throwable) {
            Throwable throwable = (Throwable)object;
            throw throwable;
        }
        return (T)((Extension)object);
    }

    @Override
    public final <T extends Extension> T registerExtension(ExtensionId<T> ext) {
        T t2;
        while ((t2 = this.findExtension(ext)) == null) {
            T t3;
            CountDownLatch inProcessOfRegistration = new CountDownLatch(1);
            Object object = this.extensions().putIfAbsent(ext, inProcessOfRegistration);
            if (object != null) continue;
            try {
                try {
                    T t4 = ext.createExtension(this);
                    if (t4 == null) {
                        throw new IllegalStateException(new StringBuilder(53).append("Extension instance created as 'null' for extension [").append(ext).append("]").toString());
                    }
                    this.extensions().replace(ext, inProcessOfRegistration, t4);
                    t3 = t4;
                }
                catch (Throwable t5) {
                    this.extensions().replace(ext, inProcessOfRegistration, t5);
                    throw t5;
                }
            }
            finally {
                inProcessOfRegistration.countDown();
            }
            return t3;
        }
        return t2;
    }

    @Override
    public <T extends Extension> T extension(ExtensionId<T> ext) {
        T t2 = this.findExtension(ext);
        if (t2 == null) {
            throw new IllegalArgumentException(new StringBuilder(41).append("Trying to get non-registered extension [").append(ext).append("]").toString());
        }
        return t2;
    }

    @Override
    public boolean hasExtension(ExtensionId<? extends Extension> ext) {
        return this.findExtension(ext) != null;
    }

    private void loadExtensions() {
        this.loadExtensions$1("akka.library-extensions", true);
        this.loadExtensions$1("akka.extensions", false);
    }

    public String toString() {
        return this.lookupRoot().path().root().address().toString();
    }

    @Override
    public String printTree() {
        return ActorSystemImpl.printNode$1(this.lookupRoot(), "");
    }

    private boolean isTypedGuardian() {
        return this.guardianProps().exists((Function1<Props, Object> & Serializable)x$9 -> BoxesRunTime.boxToBoolean(ActorSystemImpl.$anonfun$isTypedGuardian$1(x$9)));
    }

    private final /* synthetic */ ActorRefProvider liftedTree1$1() {
        ActorRefProvider actorRefProvider;
        try {
            Vector arguments = (Vector)scala.package$.MODULE$.Vector().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(String.class), this.name()), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(ActorSystem.Settings.class), this.settings()), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(EventStream.class), this.eventStream()), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(DynamicAccess.class), this.dynamicAccess())}));
            actorRefProvider = (ActorRefProvider)this.dynamicAccess().createInstanceFor(this.settings().ProviderClass(), arguments, ClassTag$.MODULE$.apply(ActorRefProvider.class)).get();
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable e = option.get();
                Try$.MODULE$.apply(() -> this.stopScheduler());
                throw e;
            }
            throw throwable;
        }
        return actorRefProvider;
    }

    private final /* synthetic */ ActorSystemImpl liftedTree2$1() {
        ActorSystemImpl actorSystemImpl;
        try {
            this.registerOnTermination(() -> this.stopScheduler());
            this.provider().init(this);
            this._initialized_$eq(true);
            if (this.settings().LogDeadLetters() > 0) {
                this.logDeadLetterListener_$eq(new Some<ActorRef>(this.systemActorOf(Props$.MODULE$.apply(ClassTag$.MODULE$.apply(DeadLetterListener.class)), "deadLetterListener")));
            }
            this.eventStream().startUnsubscriber();
            ((ManifestInfo)ManifestInfo$.MODULE$.apply(this)).checkSameVersion("Akka", this.allModules(), true);
            if (!this.terminating()) {
                this.loadExtensions();
            }
            if (this.settings().LogConfigOnStart()) {
                this.logConfiguration();
            }
            actorSystemImpl = this;
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable e;
                block9: {
                    Object object;
                    e = option.get();
                    try {
                        object = this.terminate();
                    }
                    catch (Throwable throwable3) {
                        Option<Throwable> option2;
                        Throwable throwable4 = throwable3;
                        if (throwable4 != null && !(option2 = NonFatal$.MODULE$.unapply(throwable4)).isEmpty()) {
                            object = Try$.MODULE$.apply(() -> this.stopScheduler());
                            break block9;
                        }
                        throw throwable3;
                    }
                }
                throw e;
            }
            throw throwable;
        }
        return actorSystemImpl;
    }

    private final void loadExtensions$1(String key, boolean throwOnLoadFail) {
        Util$.MODULE$.immutableSeq(this.settings().config().getStringList(key)).foreach((Function1<String, Object> & Serializable)fqcn -> {
            Object p;
            boolean bl = false;
            Success success = null;
            Try<Object> try_ = this.dynamicAccess().getObjectFor((String)fqcn, ClassTag$.MODULE$.AnyRef()).recoverWith(new Serializable(this, (String)fqcn){
                private static final long serialVersionUID = 0L;
                private final /* synthetic */ ActorSystemImpl $outer;
                private final String fqcn$1;

                public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                    A1 A1 = x1;
                    return (B1)this.$outer.dynamicAccess().createInstanceFor(this.fqcn$1, Nil$.MODULE$, ClassTag$.MODULE$.AnyRef()).recoverWith(new Serializable(null, A1){
                        private static final long serialVersionUID = 0L;
                        private final Throwable x1$1;

                        public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                            A1 A1 = x1;
                            return (B1)new Failure<T>(this.x1$1);
                        }

                        public final boolean isDefinedAt(Throwable x1) {
                            Throwable throwable = x1;
                            return true;
                        }
                        {
                            this.x1$1 = x1$1;
                        }
                    });
                }

                public final boolean isDefinedAt(Throwable x1) {
                    Throwable throwable = x1;
                    return true;
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    this.fqcn$1 = fqcn$1;
                }
            });
            if (try_ instanceof Success) {
                bl = true;
                success = (Success)try_;
                Object p2 = success.value();
                if (p2 instanceof ExtensionIdProvider) {
                    ExtensionIdProvider extensionIdProvider = (ExtensionIdProvider)p2;
                    return this.registerExtension(extensionIdProvider.lookup());
                }
            }
            if (bl && (p = success.value()) instanceof ExtensionId) {
                ExtensionId extensionId = (ExtensionId)p;
                return this.registerExtension(extensionId);
            }
            if (bl) {
                if (!throwOnLoadFail) {
                    this.log().error("[{}] is not an 'ExtensionIdProvider' or 'ExtensionId', skipping...", fqcn);
                    return BoxedUnit.UNIT;
                }
                throw new RuntimeException(new StringBuilder(51).append("[").append((String)fqcn).append("] is not an 'ExtensionIdProvider' or 'ExtensionId'").toString());
            }
            if (try_ instanceof Failure) {
                Failure failure = (Failure)try_;
                Throwable problem = failure.exception();
                if (!throwOnLoadFail) {
                    this.log().error(problem, "While trying to load extension [{}], skipping...", fqcn);
                    return BoxedUnit.UNIT;
                }
                throw new RuntimeException(new StringBuilder(33).append("While trying to load extension [").append((String)fqcn).append("]").toString(), problem);
            }
            throw new MatchError(try_);
        });
    }

    private static final String printNode$1(ActorRef node, String indent) {
        ActorRef actorRef2 = node;
        if (actorRef2 instanceof ActorRefWithCell) {
            String string;
            String string2;
            ActorCell actorCell;
            Actor realActor;
            Cell cell;
            ActorRefWithCell actorRefWithCell = (ActorRefWithCell)actorRef2;
            Cell cell2 = actorRefWithCell.underlying();
            StringBuilder stringBuilder = new StringBuilder(3).append(indent.isEmpty() ? "-> " : new StringBuilder(4).append(StringOps$.MODULE$.dropRight$extension(Predef$.MODULE$.augmentString(indent), 1)).append("\u230a-> ").toString()).append(node.path().name()).append(" ").append(Logging$.MODULE$.simpleName(node)).append(" ").append((cell = cell2) instanceof ActorCell ? ((realActor = (actorCell = (ActorCell)cell).actor()) != null ? realActor.getClass() : "null") : Logging$.MODULE$.simpleName(cell2));
            Cell cell3 = cell2;
            if (cell3 instanceof ActorCell) {
                ActorCell actorCell2 = (ActorCell)cell3;
                string2 = new StringBuilder(8).append(" status=").append(actorCell2.mailbox().currentStatus()).toString();
            } else {
                string2 = "";
            }
            StringBuilder stringBuilder2 = stringBuilder.append((Object)string2).append(" ");
            ChildrenContainer childrenContainer = cell2.childrenRefs();
            if (childrenContainer instanceof ChildrenContainer.TerminatingChildrenContainer) {
                ChildrenContainer.TerminatingChildrenContainer terminatingChildrenContainer = (ChildrenContainer.TerminatingChildrenContainer)childrenContainer;
                Set<ActorRef> toDie = terminatingChildrenContainer.toDie();
                ChildrenContainer.SuspendReason reason = terminatingChildrenContainer.reason();
                string = new StringBuilder(13).append("Terminating(").append(reason).append(")").append(((IterableOnceOps)toDie.toSeq().sorted(Ordering$.MODULE$.ordered(Predef$.MODULE$.$conforms()))).mkString(new StringBuilder(16).append("\n").append(indent).append("   |    toDie: ").toString(), new StringBuilder(16).append("\n").append(indent).append("   |           ").toString(), "")).toString();
            } else if (ChildrenContainer$TerminatedChildrenContainer$.MODULE$.equals(childrenContainer) ? true : ChildrenContainer$EmptyChildrenContainer$.MODULE$.equals(childrenContainer)) {
                string = childrenContainer.toString();
            } else if (childrenContainer instanceof ChildrenContainer.NormalChildrenContainer) {
                ChildrenContainer.NormalChildrenContainer normalChildrenContainer = (ChildrenContainer.NormalChildrenContainer)childrenContainer;
                string = new StringBuilder(9).append(Integer.toString(normalChildrenContainer.c().size())).append(" children").toString();
            } else {
                string = Logging$.MODULE$.simpleName(childrenContainer);
            }
            Seq children = (Seq)cell2.childrenRefs().children().toSeq().sorted(Ordering$.MODULE$.ordered(Predef$.MODULE$.$conforms()));
            Seq bulk = (Seq)((IterableOps)children.dropRight(1)).map((Function1<ActorRef, String> & Serializable)x$7 -> ActorSystemImpl.printNode$1(x$7, new StringBuilder(4).append(indent).append("   |").toString()));
            return stringBuilder2.append((Object)string).append((Object)(cell2.childrenRefs().children().isEmpty() ? "" : "\n")).append(((IterableOnceOps)bulk.$plus$plus(children.lastOption().map((Function1<ActorRef, String> & Serializable)x$8 -> ActorSystemImpl.printNode$1(x$8, new StringBuilder(4).append(indent).append("    ").toString())))).mkString("\n")).toString();
        }
        return new StringBuilder(1).append(indent).append(node.path().name()).append(" ").append(Logging$.MODULE$.simpleName(node)).toString();
    }

    public static final /* synthetic */ boolean $anonfun$isTypedGuardian$1(Props x$9) {
        return x$9.clazz().getName().startsWith("akka.actor.typed");
    }

    public ActorSystemImpl(String name, Config applicationConfig, ClassLoader classLoader, Option<ExecutionContext> defaultExecutionContext, Option<Props> guardianProps, ActorSystemSetup setup) {
        this.name = name;
        this.classLoader = classLoader;
        this.guardianProps = guardianProps;
        this.uid = ThreadLocalRandom.current().nextLong();
        if (!name.matches("^[a-zA-Z0-9][a-zA-Z0-9-_]*$")) {
            throw new IllegalArgumentException(new StringBuilder(109).append("invalid ActorSystem name [").append(name).append("], must contain only word characters (i.e. [a-zA-Z0-9] plus non-leading '-' or '_')").toString());
        }
        this.logDeadLetterListener = None$.MODULE$;
        this._dynamicAccess = this.createDynamicAccess();
        OptionVal$.MODULE$.None();
        this.typedSystem = null;
        Config config = ActorSystem$Settings$.MODULE$.amendSlf4jConfig(applicationConfig.withFallback(ConfigFactory.defaultReference(classLoader)), this._dynamicAccess());
        this.settings = new ActorSystem.Settings(classLoader, config, name, setup);
        this.threadFactory = new MonitorableThreadFactory(name, this.settings().Daemonicity(), Option$.MODULE$.apply(classLoader), this.uncaughtExceptionHandler(), MonitorableThreadFactory$.MODULE$.apply$default$5());
        this.eventStream = new EventStream(this, this.settings().DebugEventStream());
        this.eventStream().startStdoutLogger(this.settings());
        Vector arguments = (Vector)scala.package$.MODULE$.Vector().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(ActorSystem.Settings.class), this.settings()), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(EventStream.class), this.eventStream())}));
        this.logFilter = (LoggingFilter)this.dynamicAccess().createInstanceFor(this.settings().LoggingFilter(), arguments, ClassTag$.MODULE$.apply(LoggingFilter.class)).get();
        this.akka$actor$ActorSystemImpl$$markerLogging = new MarkerLoggingAdapter(this.eventStream(), new StringBuilder(2).append(this.getClass().getName()).append("(").append(name).append(")").toString(), this.getClass(), this.logFilter());
        this.log = this.akka$actor$ActorSystemImpl$$markerLogging;
        this.scheduler = this.createScheduler();
        this.provider = this.liftedTree1$1();
        this.mailboxes = new Mailboxes(this.settings(), this.eventStream(), this.dynamicAccess(), this.deadLetters());
        this.dispatchers = new Dispatchers(this.settings(), new DefaultDispatcherPrerequisites(this.threadFactory(), this.eventStream(), this.scheduler(), this.dynamicAccess(), this.settings(), this.mailboxes(), defaultExecutionContext), this.log());
        this.dispatcher = this.dispatchers().defaultGlobalDispatcher();
        this.terminationCallbacks = new TerminationCallbacks<Terminated>(this, this.provider().terminationFuture(), this.dispatcher());
        this._initialized = false;
        this.terminating = false;
        this.aborting = false;
        this.extensions = new ConcurrentHashMap();
    }

    public final class TerminationCallbacks<T> {
        private final ExecutionContext ec;
        private final Promise<T> done;
        private final AtomicReference<Promise<T>> ref;

        public final void add(Runnable r) {
            this.addRec$1(r, Promise$.MODULE$.apply());
        }

        public Future<T> terminationFuture() {
            return this.done.future();
        }

        private final void addRec$1(Runnable r, Promise p) {
            Promise promise;
            while (true) {
                if ((promise = this.ref.get()) == null) {
                    throw new RejectedExecutionException("ActorSystem already terminated.");
                }
                if (this.ref.compareAndSet(promise, p)) break;
            }
            promise.completeWith(p.future().andThen(new Serializable(null, r){
                private static final long serialVersionUID = 0L;
                private final Runnable r$1;

                public final <A1 extends Try<T>, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                    A1 A1 = x1;
                    this.r$1.run();
                    return (B1)BoxedUnit.UNIT;
                }

                public final boolean isDefinedAt(Try<T> x1) {
                    Try<T> try_ = x1;
                    return true;
                }
                {
                    this.r$1 = r$1;
                }
            }, this.ec));
        }

        public TerminationCallbacks(ActorSystemImpl $outer, Future<T> upStreamTerminated, ExecutionContext ec) {
            this.ec = ec;
            this.done = Promise$.MODULE$.apply();
            this.ref = new AtomicReference<Promise<T>>(this.done);
            upStreamTerminated.onComplete((Function1<Try, Promise> & Serializable)t2 -> ((Promise)$this.ref.getAndSet(null)).complete(t2), ec);
        }
    }
}

