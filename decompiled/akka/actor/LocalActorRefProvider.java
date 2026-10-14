/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.ConfigurationException;
import akka.actor.Actor;
import akka.actor.Actor$;
import akka.actor.ActorCell;
import akka.actor.ActorContext;
import akka.actor.ActorPath;
import akka.actor.ActorPathExtractor$;
import akka.actor.ActorRef;
import akka.actor.ActorRefProvider;
import akka.actor.ActorRefWithCell;
import akka.actor.ActorSystem;
import akka.actor.ActorSystemImpl;
import akka.actor.Address;
import akka.actor.Address$;
import akka.actor.DeadLetterActorRef;
import akka.actor.Deploy;
import akka.actor.Deployer;
import akka.actor.DynamicAccess;
import akka.actor.EmptyLocalActorRef;
import akka.actor.IgnoreActorRef;
import akka.actor.InternalActorRef;
import akka.actor.InvalidMessageException$;
import akka.actor.LocalActorRef;
import akka.actor.LocalActorRefProvider$SystemGuardian$;
import akka.actor.LocalRef;
import akka.actor.MinimalActorRef;
import akka.actor.Nobody$;
import akka.actor.OneForOneStrategy;
import akka.actor.OneForOneStrategy$;
import akka.actor.Props;
import akka.actor.Props$;
import akka.actor.RepointableActorRef;
import akka.actor.RootActorPath;
import akka.actor.RootActorPath$;
import akka.actor.StopChild;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy$;
import akka.actor.SupervisorStrategy$Stop$;
import akka.actor.SupervisorStrategyConfigurator;
import akka.actor.SystemGuardian$RegisterTerminationHook$;
import akka.actor.SystemGuardian$TerminationHook$;
import akka.actor.SystemGuardian$TerminationHookDone$;
import akka.actor.Terminated;
import akka.actor.Terminated$;
import akka.actor.VirtualPathContainer;
import akka.annotation.InternalApi;
import akka.dispatch.MailboxType;
import akka.dispatch.MessageDispatcher;
import akka.dispatch.RequiresMessageQueue;
import akka.dispatch.UnboundedMessageQueueSemantics;
import akka.dispatch.sysmsg.DeathWatchNotification;
import akka.dispatch.sysmsg.Failed;
import akka.dispatch.sysmsg.Supervise;
import akka.dispatch.sysmsg.SystemMessage;
import akka.dispatch.sysmsg.Watch;
import akka.event.EventStream;
import akka.event.LogSource$;
import akka.event.Logging$;
import akka.event.MarkerLoggingAdapter;
import akka.routing.NoRouter$;
import akka.routing.RoutedActorCell;
import akka.routing.RoutedActorRef;
import akka.routing.RouterConfig;
import akka.serialization.Serialization;
import akka.util.Collections$EmptyImmutableSeq$;
import akka.util.Helpers$;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.concurrent.atomic.AtomicLong;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Some;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.package$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\rUwAB.]\u0011\u0003q\u0006M\u0002\u0004c9\"\u0005al\u0019\u0005\u0006U\u0006!\t\u0001\u001c\u0004\u0005[\u0006!a\u000e\u0003\u0005|\u0007\t\u0015\r\u0011\"\u0011}\u0011%\t\ta\u0001B\u0001B\u0003%Q\u0010\u0003\u0004k\u0007\u0011\u0005\u00111\u0001\u0005\b\u0003\u0017\u0019A\u0011AA\u0007\u0011\u001d\t\tc\u0001C!\u0003G1a!a\u0013\u0002\t\u00055\u0003\u0002C>\n\u0005\u000b\u0007I\u0011\t?\t\u0013\u0005\u0005\u0011B!A!\u0002\u0013i\bBCA(\u0013\t\u0015\r\u0011\"\u0001\u0002R!Q\u0011\u0011L\u0005\u0003\u0002\u0003\u0006I!a\u0015\t\r)LA\u0011AA.\u0011%\t\u0019'\u0003a\u0001\n\u0003\t)\u0007C\u0005\u0002x%\u0001\r\u0011\"\u0001\u0002z!A\u0011qP\u0005!B\u0013\t9\u0007C\u0004\u0002\f%!\t!!\u0004\t\u000f\u0005\u0005\u0015\u0002\"\u0001\u0002\u0004\"9\u0011QR\u0005\u0005\u0002\u0005=\u0005bBAG\u0013\u0011\u0005\u0011Q\u0013\u0005\b\u0003CIA\u0011IAL\r\u0019\u0011G\f\u00010\u0002\u001e\"Q\u0011QU\f\u0003\u0002\u0003\u0006I!a*\t\u0015\u0005]vC!b\u0001\n\u0003\nI\f\u0003\u0006\u0002J^\u0011\t\u0011)A\u0005\u0003wC!\"a3\u0018\u0005\u000b\u0007I\u0011AAg\u0011)\tYn\u0006B\u0001B\u0003%\u0011q\u001a\u0005\u000b\u0003;<\"Q1A\u0005\u0002\u0005}\u0007BCAt/\t\u0005\t\u0015!\u0003\u0002b\"Q\u0011\u0011^\f\u0003\u0006\u0004%\t%a;\t\u0015\u0005MxC!A!\u0002\u0013\ti\u000f\u0003\u0006\u0002v^\u0011\t\u0011)A\u0005\u0003oDqA[\f\u0005\u0002y\u0013Y\u0001\u0003\u0004k/\u0011\u0005!1\u0004\u0005\n\u0005K9\"\u0019!C!\u0005OA\u0001B!\u000b\u0018A\u0003%\u0011q \u0005\u000b\u0005W9\"\u0019!C\u0001=\n5\u0002\u0002\u0003B\u001b/\u0001\u0006IAa\f\t\u0015\t]rC1A\u0005\u0002y\u0013i\u0003\u0003\u0005\u0003:]\u0001\u000b\u0011\u0002B\u0018\u0011%\u0011Yd\u0006b\u0001\n\u0003\u0012i\u0004\u0003\u0005\u0003@]\u0001\u000b\u0011\u0002B\u0003\u0011%\u0011\te\u0006b\u0001\n\u0003\n\t\u0006\u0003\u0005\u0003D]\u0001\u000b\u0011BA*\u0011!\u0011)e\u0006Q\u0001\u000e\t\u001d\u0003b\u0002B-/\u0011\u0005!1\f\u0005\n\u0005G:\"\u0019!C\u0005\u0005KB\u0001B! \u0018A\u0003%!q\r\u0005\n\u0005\u007f:\"\u0019!C\u0005\u0005OA\u0001B!!\u0018A\u0003%\u0011q \u0005\b\u0005\u0007;B\u0011\tBC\u0011\u001d\u0011\u0019i\u0006C!\u0005\u000fC!B!$\u0018\u0005\u0004%\tA\u0018B\u001f\u0011!\u0011yi\u0006Q\u0001\n\t\u0015\u0001b\u0003BI/\u0001\u0007\t\u0019!C\u0005\u0005'C1Ba'\u0018\u0001\u0004\u0005\r\u0011\"\u0003\u0003\u001e\"Y!\u0011U\fA\u0002\u0003\u0005\u000b\u0015\u0002BK\u0011%\u0011Yk\u0006a\u0001\n\u0013\u0011i\u000bC\u0005\u00036^\u0001\r\u0011\"\u0003\u00038\"A!1X\f!B\u0013\u0011y\u000bC\u0004\u0003@^!\tA!1\t\u000f\t\u001dw\u0003\"\u0003\u0003J\"1!\u0011[\f\u0005\u0012qDaAa5\u0018\t#a\bB\u0002Bk/\u0011EA\u0010C\u0004\u0003X^!IA!7\t\u0015\t\u0005x\u0003#b\u0001\n\u0013\u0011\u0019\u000f\u0003\u0006\u0003l^A)\u0019!C!\u0005[DqA!>\u0018\t\u0003\u00129\u0010\u0003\u0006\u0002P]A)\u0019!C!\u0005[D!ba\u0001\u0018\u0011\u000b\u0007I\u0011\tBw\u0011)\u0019)a\u0006EC\u0002\u0013\u00051q\u0001\u0005\b\u0007\u001f9B\u0011AB\t\u0011\u001d\u0019Yb\u0006C\u0001\u0007;A\u0001b!\t\u0018\t\u0003q61\u0005\u0005\b\u0007S9B\u0011AB\u0016\u0011\u001d\u0019Ic\u0006C\u0001\u0007_A\u0001b!\u000b\u0018\t\u0003q61\u0007\u0005\b\u0007\u0007:B\u0011AB#\u0011\u001d\u00199h\u0006C\u0001\u0007sBqa!!\u0018\t\u0003\u0019\u0019\tC\u0005\u0004\u0006^\u0001\r\u0011\"\u0003\u0004\b\"I1qU\fA\u0002\u0013%1\u0011\u0016\u0005\t\u0007[;\u0002\u0015)\u0003\u0004\n\"A1qV\f\u0005By\u001b\t\fC\u0005\u0004B^\u0001\r\u0011\"\u0003\u0004D\"I1qY\fA\u0002\u0013%1\u0011\u001a\u0005\t\u0007\u001b<\u0002\u0015)\u0003\u0004F\"A1\u0011[\f\u0005By\u001b\u0019.A\u000bM_\u000e\fG.Q2u_J\u0014VM\u001a)s_ZLG-\u001a:\u000b\u0005us\u0016!B1di>\u0014(\"A0\u0002\t\u0005\\7.\u0019\t\u0003C\u0006i\u0011\u0001\u0018\u0002\u0016\u0019>\u001c\u0017\r\\!di>\u0014(+\u001a4Qe>4\u0018\u000eZ3s'\t\tA\r\u0005\u0002fQ6\taMC\u0001h\u0003\u0015\u00198-\u00197b\u0013\tIgM\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\t\u0001M\u0001\u0005Hk\u0006\u0014H-[1o'\u0011\u0019Am\u001c:\u0011\u0005\u0005\u0004\u0018BA9]\u0005\u0015\t5\r^8s!\r\u0019h\u000f_\u0007\u0002i*\u0011QOX\u0001\tI&\u001c\b/\u0019;dQ&\u0011q\u000f\u001e\u0002\u0015%\u0016\fX/\u001b:fg6+7o]1hKF+X-^3\u0011\u0005ML\u0018B\u0001>u\u0005y)fNY8v]\u0012,G-T3tg\u0006<W-U;fk\u0016\u001cV-\\1oi&\u001c7/\u0001\ntkB,'O^5t_J\u001cFO]1uK\u001eLX#A?\u0011\u0005\u0005t\u0018BA@]\u0005I\u0019V\u000f]3sm&\u001cxN]*ue\u0006$XmZ=\u0002'M,\b/\u001a:wSN|'o\u0015;sCR,w-\u001f\u0011\u0015\t\u0005\u0015\u0011\u0011\u0002\t\u0004\u0003\u000f\u0019Q\"A\u0001\t\u000bm4\u0001\u0019A?\u0002\u000fI,7-Z5wKV\u0011\u0011q\u0002\t\bK\u0006E\u0011QCA\u000e\u0013\r\t\u0019B\u001a\u0002\u0010!\u0006\u0014H/[1m\rVt7\r^5p]B\u0019Q-a\u0006\n\u0007\u0005eaMA\u0002B]f\u00042!ZA\u000f\u0013\r\tyB\u001a\u0002\u0005+:LG/\u0001\u0006qe\u0016\u0014Vm\u001d;beR$b!a\u0007\u0002&\u0005\u0005\u0003bBA\u0014\u0011\u0001\u0007\u0011\u0011F\u0001\u0006G\u0006,8/\u001a\t\u0005\u0003W\tYD\u0004\u0003\u0002.\u0005]b\u0002BA\u0018\u0003ki!!!\r\u000b\u0007\u0005M2.\u0001\u0004=e>|GOP\u0005\u0002O&\u0019\u0011\u0011\b4\u0002\u000fA\f7m[1hK&!\u0011QHA \u0005%!\u0006N]8xC\ndWMC\u0002\u0002:\u0019Dq!a\u0011\t\u0001\u0004\t)%A\u0002ng\u001e\u0004R!ZA$\u0003+I1!!\u0013g\u0005\u0019y\u0005\u000f^5p]\nq1+_:uK6<U/\u0019:eS\u0006t7\u0003B\u0005e_J\f\u0001bZ;be\u0012L\u0017M\\\u000b\u0003\u0003'\u00022!YA+\u0013\r\t9\u0006\u0018\u0002\t\u0003\u000e$xN\u001d*fM\u0006Iq-^1sI&\fg\u000e\t\u000b\u0007\u0003;\ny&!\u0019\u0011\u0007\u0005\u001d\u0011\u0002C\u0003|\u001d\u0001\u0007Q\u0010C\u0004\u0002P9\u0001\r!a\u0015\u0002!Q,'/\\5oCRLwN\u001c%p_.\u001cXCAA4!\u0019\tI'a\u001d\u0002T5\u0011\u00111\u000e\u0006\u0005\u0003[\ny'A\u0005j[6,H/\u00192mK*\u0019\u0011\u0011\u000f4\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002v\u0005-$aA*fi\u0006!B/\u001a:nS:\fG/[8o\u0011>|7n]0%KF$B!a\u0007\u0002|!I\u0011Q\u0010\t\u0002\u0002\u0003\u0007\u0011qM\u0001\u0004q\u0012\n\u0014!\u0005;fe6Lg.\u0019;j_:Dun\\6tA\u0005YA/\u001a:nS:\fG/\u001b8h+\t\t)\t\u0005\u0003\u0002\b\u0006%U\"A\u0005\n\u0007\u0005-\u0005OA\u0004SK\u000e,\u0017N^3\u0002?M$x\u000e],iK:\fE\u000e\u001c+fe6Lg.\u0019;j_:Dun\\6t\t>tW\r\u0006\u0003\u0002\u001c\u0005E\u0005bBAJ)\u0001\u0007\u00111K\u0001\u0007e\u0016lwN^3\u0015\u0005\u0005mACBA\u000e\u00033\u000bY\nC\u0004\u0002(Y\u0001\r!!\u000b\t\u000f\u0005\rc\u00031\u0001\u0002FM!q\u0003ZAP!\r\t\u0017\u0011U\u0005\u0004\u0003Gc&\u0001E!di>\u0014(+\u001a4Qe>4\u0018\u000eZ3s\u0003-y6/_:uK6t\u0015-\\3\u0011\t\u0005%\u0016\u0011\u0017\b\u0005\u0003W\u000bi\u000bE\u0002\u00020\u0019L1!a,g\u0003\u0019\u0001&/\u001a3fM&!\u00111WA[\u0005\u0019\u0019FO]5oO*\u0019\u0011q\u00164\u0002\u0011M,G\u000f^5oON,\"!a/\u0011\t\u0005u\u00161\u0019\b\u0004C\u0006}\u0016bAAa9\u0006Y\u0011i\u0019;peNK8\u000f^3n\u0013\u0011\t)-a2\u0003\u0011M+G\u000f^5oONT1!!1]\u0003%\u0019X\r\u001e;j]\u001e\u001c\b%A\u0006fm\u0016tGo\u0015;sK\u0006lWCAAh!\u0011\t\t.a6\u000e\u0005\u0005M'bAAk=\u0006)QM^3oi&!\u0011\u0011\\Aj\u0005-)e/\u001a8u'R\u0014X-Y7\u0002\u0019\u00154XM\u001c;TiJ,\u0017-\u001c\u0011\u0002\u001b\u0011Lh.Y7jG\u0006\u001b7-Z:t+\t\t\t\u000fE\u0002b\u0003GL1!!:]\u00055!\u0015P\\1nS\u000e\f5mY3tg\u0006qA-\u001f8b[&\u001c\u0017iY2fgN\u0004\u0013\u0001\u00033fa2|\u00170\u001a:\u0016\u0005\u00055\bcA1\u0002p&\u0019\u0011\u0011\u001f/\u0003\u0011\u0011+\u0007\u000f\\8zKJ\f\u0011\u0002Z3qY>LXM\u001d\u0011\u0002\u0019}#W-\u00193MKR$XM]:\u0011\u000b\u0015\f9%!?\u0011\u000f\u0015\fY0a@\u0003\u0006%\u0019\u0011Q 4\u0003\u0013\u0019+hn\u0019;j_:\f\u0004cA1\u0003\u0002%\u0019!1\u0001/\u0003\u0013\u0005\u001bGo\u001c:QCRD\u0007cA1\u0003\b%\u0019!\u0011\u0002/\u0003!%sG/\u001a:oC2\f5\r^8s%\u00164GC\u0004B\u0007\u0005\u001f\u0011\tBa\u0005\u0003\u0016\t]!\u0011\u0004\t\u0003C^Aq!!*#\u0001\u0004\t9\u000bC\u0004\u00028\n\u0002\r!a/\t\u000f\u0005-'\u00051\u0001\u0002P\"9\u0011Q\u001c\u0012A\u0002\u0005\u0005\bbBAuE\u0001\u0007\u0011Q\u001e\u0005\b\u0003k\u0014\u0003\u0019AA|))\u0011iA!\b\u0003 \t\u0005\"1\u0005\u0005\b\u0003K\u001b\u0003\u0019AAT\u0011\u001d\t9l\ta\u0001\u0003wCq!a3$\u0001\u0004\ty\rC\u0004\u0002^\u000e\u0002\r!!9\u0002\u0011I|w\u000e\u001e)bi\",\"!a@\u0002\u0013I|w\u000e\u001e)bi\"\u0004\u0013a\u00017pOV\u0011!q\u0006\t\u0005\u0003#\u0014\t$\u0003\u0003\u00034\u0005M'\u0001F'be.,'\u000fT8hO&tw-\u00113baR,'/\u0001\u0003m_\u001e\u0004\u0013\u0001\u00037pO\u0012+7/\u001a:\u0002\u00131|w\rR3tKJ\u0004\u0013a\u00033fC\u0012dU\r\u001e;feN,\"A!\u0002\u0002\u0019\u0011,\u0017\r\u001a'fiR,'o\u001d\u0011\u0002\u0013%<gn\u001c:f%\u00164\u0017AC5h]>\u0014XMU3gA\u0005\u0011B/\u001a:nS:\fG/[8o!J|W.[:f!\u0019\u0011IEa\u0014\u0003T5\u0011!1\n\u0006\u0004\u0005\u001b2\u0017AC2p]\u000e,(O]3oi&!!\u0011\u000bB&\u0005\u001d\u0001&o\\7jg\u0016\u00042!\u0019B+\u0013\r\u00119\u0006\u0018\u0002\u000b)\u0016\u0014X.\u001b8bi\u0016$\u0017!\u0005;fe6Lg.\u0019;j_:4U\u000f^;sKV\u0011!Q\f\t\u0007\u0005\u0013\u0012yFa\u0015\n\t\t\u0005$1\n\u0002\u0007\rV$XO]3\u0002\u0015Q,W\u000e\u001d(v[\n,'/\u0006\u0002\u0003hA!!\u0011\u000eB=\u001b\t\u0011YG\u0003\u0003\u0003n\t=\u0014AB1u_6L7M\u0003\u0003\u0003N\tE$\u0002\u0002B:\u0005k\nA!\u001e;jY*\u0011!qO\u0001\u0005U\u00064\u0018-\u0003\u0003\u0003|\t-$AC!u_6L7\rT8oO\u0006YA/Z7q\u001dVl'-\u001a:!\u0003!!X-\u001c9O_\u0012,\u0017!\u0003;f[Btu\u000eZ3!\u0003!!X-\u001c9QCRDGCAA\u0000)\u0011\tyP!#\t\u000f\t-U\u00071\u0001\u0002(\u00061\u0001O]3gSb\f1\u0005\u001e5f\u001f:,w\u000b[8XC2\\7\u000f\u00165f\u0005V\u0014'\r\\3t\u001f\u001a\u001c\u0006/Y2f)&lW-\u0001\u0013uQ\u0016|e.Z,i_^\u000bGn[:UQ\u0016\u0014UO\u00192mKN|em\u00159bG\u0016$\u0016.\\3!\u0003\u0019\u0019\u0018p\u001d;f[V\u0011!Q\u0013\t\u0004C\n]\u0015b\u0001BM9\ny\u0011i\u0019;peNK8\u000f^3n\u00136\u0004H.\u0001\u0006tsN$X-\\0%KF$B!a\u0007\u0003 \"I\u0011QP\u001d\u0002\u0002\u0003\u0007!QS\u0001\bgf\u001cH/Z7!Q\rQ$Q\u0015\t\u0004K\n\u001d\u0016b\u0001BUM\nAao\u001c7bi&dW-\u0001\u0006fqR\u0014\u0018MT1nKN,\"Aa,\u0011\u0011\u0005%&\u0011WAT\u0005\u000bIAAa-\u00026\n\u0019Q*\u00199\u0002\u001d\u0015DHO]1OC6,7o\u0018\u0013fcR!\u00111\u0004B]\u0011%\ti\bPA\u0001\u0002\u0004\u0011y+A\u0006fqR\u0014\u0018MT1nKN\u0004\u0003fA\u001f\u0003&\u0006\u0011\"/Z4jgR,'/\u0012=ue\u0006t\u0015-\\3t)\u0011\tYBa1\t\u000f\t\u0015g\b1\u0001\u00030\u00069q,\u001a=ue\u0006\u001c\u0018AJ4vCJ$\u0017.\u00198TkB,'O^5t_J\u001cFO]1uK\u001eL8i\u001c8gS\u001e,(/\u0019;peV\u0011!1\u001a\t\u0004C\n5\u0017b\u0001Bh9\nq2+\u001e9feZL7o\u001c:TiJ\fG/Z4z\u0007>tg-[4ve\u0006$xN]\u0001\u0015e>|GoR;be\u0012L\u0017M\\*ue\u0006$XmZ=\u0002!\u001d,\u0018M\u001d3jC:\u001cFO]1uK\u001eL\u0018AF:zgR,WnR;be\u0012L\u0017M\\*ue\u0006$XmZ=\u0002%%tG/\u001a:oC2$\u0015n\u001d9bi\u000eDWM]\u000b\u0003\u00057\u00042a\u001dBo\u0013\r\u0011y\u000e\u001e\u0002\u0012\u001b\u0016\u001c8/Y4f\t&\u001c\b/\u0019;dQ\u0016\u0014\u0018A\u00043fM\u0006,H\u000e^'bS2\u0014w\u000e_\u000b\u0003\u0005K\u00042a\u001dBt\u0013\r\u0011I\u000f\u001e\u0002\f\u001b\u0006LGNY8y)f\u0004X-\u0001\u0007s_>$x)^1sI&\fg.\u0006\u0002\u0003pB\u0019\u0011M!=\n\u0007\tMHLA\u0007M_\u000e\fG.Q2u_J\u0014VMZ\u0001\u000fe>|GoR;be\u0012L\u0017M\\!u)\u0011\t\u0019F!?\t\u000f\tmh\t1\u0001\u0003~\u00069\u0011\r\u001a3sKN\u001c\bcA1\u0003\u0000&\u00191\u0011\u0001/\u0003\u000f\u0005#GM]3tg\u0006q1/_:uK6<U/\u0019:eS\u0006t\u0017!\u0004;f[B\u001cuN\u001c;bS:,'/\u0006\u0002\u0004\nA\u0019\u0011ma\u0003\n\u0007\r5AL\u0001\u000bWSJ$X/\u00197QCRD7i\u001c8uC&tWM]\u0001\u0012e\u0016<\u0017n\u001d;feR+W\u000e]!di>\u0014HCBA\u000e\u0007'\u00199\u0002C\u0004\u0004\u0016)\u0003\rA!\u0002\u0002\u0011\u0005\u001cGo\u001c:SK\u001aDqa!\u0007K\u0001\u0004\ty0\u0001\u0003qCRD\u0017aE;oe\u0016<\u0017n\u001d;feR+W\u000e]!di>\u0014H\u0003BA\u000e\u0007?Aqa!\u0007L\u0001\u0004\ty0\u0001\u0003j]&$H\u0003BA\u000e\u0007KAqaa\nM\u0001\u0004\u0011)*A\u0004`gf\u001cH/Z7\u0002\u001fI,7o\u001c7wK\u0006\u001bGo\u001c:SK\u001a$B!a\u0015\u0004.!91\u0011D'A\u0002\u0005\u001dF\u0003BA*\u0007cAqa!\u0007O\u0001\u0004\ty\u0010\u0006\u0004\u0003\u0006\rU2\u0011\b\u0005\b\u0007oy\u0005\u0019\u0001B\u0003\u0003\r\u0011XM\u001a\u0005\b\u0007wy\u0005\u0019AB\u001f\u00031\u0001\u0018\r\u001e5FY\u0016lWM\u001c;t!\u0019\tYca\u0010\u0002(&!1\u0011IA \u0005!IE/\u001a:bE2,\u0017aB1di>\u0014xJ\u001a\u000b\u0013\u0005\u000b\u00199e!\u0013\u0004T\r]3\u0011LB2\u0007_\u001a\u0019\bC\u0004\u0003\u0012B\u0003\rA!&\t\u000f\r-\u0003\u000b1\u0001\u0004N\u0005)\u0001O]8qgB\u0019\u0011ma\u0014\n\u0007\rECLA\u0003Qe>\u00048\u000fC\u0004\u0004VA\u0003\rA!\u0002\u0002\u0015M,\b/\u001a:wSN|'\u000fC\u0004\u0004\u001aA\u0003\r!a@\t\u000f\rm\u0003\u000b1\u0001\u0004^\u0005i1/_:uK6\u001cVM\u001d<jG\u0016\u00042!ZB0\u0013\r\u0019\tG\u001a\u0002\b\u0005>|G.Z1o\u0011\u001d\u0019)\u0007\u0015a\u0001\u0007O\na\u0001Z3qY>L\b#B3\u0002H\r%\u0004cA1\u0004l%\u00191Q\u000e/\u0003\r\u0011+\u0007\u000f\\8z\u0011\u001d\u0019\t\b\u0015a\u0001\u0007;\nA\u0002\\8pWV\u0004H)\u001a9m_fDqa!\u001eQ\u0001\u0004\u0019i&A\u0003bgft7-A\u000bhKR,\u0005\u0010^3s]\u0006d\u0017\t\u001a3sKN\u001chi\u001c:\u0015\t\rm4Q\u0010\t\u0006K\u0006\u001d#Q \u0005\b\u0007\u007f\n\u0006\u0019\u0001B\u007f\u0003\u0011\tG\r\u001a:\u0002#\u001d,G\u000fR3gCVdG/\u00113ee\u0016\u001c8/\u0006\u0002\u0003~\u0006i2/\u001a:jC2L'0\u0019;j_:LeNZ8s[\u0006$\u0018n\u001c8DC\u000eDW-\u0006\u0002\u0004\nB111RBH\u0007'k!a!$\u000b\u0007\tMd,\u0003\u0003\u0004\u0012\u000e5%!C(qi&|gNV1m!\u0011\u0019)j!)\u000f\t\r]5QT\u0007\u0003\u00073S1aa'_\u00035\u0019XM]5bY&T\u0018\r^5p]&!1qTBM\u00035\u0019VM]5bY&T\u0018\r^5p]&!11UBS\u0005-IeNZ8s[\u0006$\u0018n\u001c8\u000b\t\r}5\u0011T\u0001\"g\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8J]\u001a|'/\\1uS>t7)Y2iK~#S-\u001d\u000b\u0005\u00037\u0019Y\u000bC\u0005\u0002~Q\u000b\t\u00111\u0001\u0004\n\u0006q2/\u001a:jC2L'0\u0019;j_:LeNZ8s[\u0006$\u0018n\u001c8DC\u000eDW\rI\u0001\u0019g\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8J]\u001a|'/\\1uS>tWCABJQ\r16Q\u0017\t\u0005\u0007o\u001bi,\u0004\u0002\u0004:*\u001911\u00180\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0004@\u000ee&aC%oi\u0016\u0014h.\u00197Ba&\fabX1eIJ,7o]*ue&tw-\u0006\u0002\u0004FB111RBH\u0003O\u000b!cX1eIJ,7o]*ue&twm\u0018\u0013fcR!\u00111DBf\u0011%\ti\bWA\u0001\u0002\u0004\u0019)-A\b`C\u0012$'/Z:t'R\u0014\u0018N\\4!Q\rI&QU\u0001\u000eC\u0012$'/Z:t'R\u0014\u0018N\\4\u0016\u0005\u0005\u001d\u0006")
public class LocalActorRefProvider
implements ActorRefProvider {
    private MailboxType akka$actor$LocalActorRefProvider$$defaultMailbox;
    private LocalActorRef rootGuardian;
    private LocalActorRef guardian;
    private LocalActorRef systemGuardian;
    private VirtualPathContainer tempContainer;
    private final ActorSystem.Settings settings;
    private final EventStream eventStream;
    private final DynamicAccess dynamicAccess;
    private final Deployer deployer;
    private final ActorPath rootPath;
    private final MarkerLoggingAdapter log;
    private final MarkerLoggingAdapter logDeser;
    private final InternalActorRef deadLetters;
    private final ActorRef ignoreRef;
    public final Promise<Terminated> akka$actor$LocalActorRefProvider$$terminationPromise;
    private final AtomicLong tempNumber;
    private final ActorPath tempNode;
    private final InternalActorRef theOneWhoWalksTheBubblesOfSpaceTime;
    private volatile ActorSystemImpl akka$actor$LocalActorRefProvider$$system;
    private volatile Map<String, InternalActorRef> akka$actor$LocalActorRefProvider$$extraNames;
    private Serialization.Information serializationInformationCache;
    private volatile String _addressString;
    private volatile byte bitmap$0;

    @Override
    public ActorSystem.Settings settings() {
        return this.settings;
    }

    public EventStream eventStream() {
        return this.eventStream;
    }

    public DynamicAccess dynamicAccess() {
        return this.dynamicAccess;
    }

    @Override
    public Deployer deployer() {
        return this.deployer;
    }

    @Override
    public ActorPath rootPath() {
        return this.rootPath;
    }

    public MarkerLoggingAdapter log() {
        return this.log;
    }

    public MarkerLoggingAdapter logDeser() {
        return this.logDeser;
    }

    @Override
    public InternalActorRef deadLetters() {
        return this.deadLetters;
    }

    @Override
    public ActorRef ignoreRef() {
        return this.ignoreRef;
    }

    @Override
    public Future<Terminated> terminationFuture() {
        return this.akka$actor$LocalActorRefProvider$$terminationPromise.future();
    }

    private AtomicLong tempNumber() {
        return this.tempNumber;
    }

    private ActorPath tempNode() {
        return this.tempNode;
    }

    @Override
    public ActorPath tempPath() {
        return this.tempPath("");
    }

    @Override
    public ActorPath tempPath(String prefix) {
        StringBuilder builder = new StringBuilder();
        Serializable serializable = StringOps$.MODULE$.nonEmpty$extension(Predef$.MODULE$.augmentString(prefix)) ? builder.append(prefix) : BoxedUnit.UNIT;
        builder.append("$");
        Helpers$.MODULE$.base64(this.tempNumber().getAndIncrement(), builder);
        return this.tempNode().$div(builder.toString());
    }

    public InternalActorRef theOneWhoWalksTheBubblesOfSpaceTime() {
        return this.theOneWhoWalksTheBubblesOfSpaceTime;
    }

    public ActorSystemImpl akka$actor$LocalActorRefProvider$$system() {
        return this.akka$actor$LocalActorRefProvider$$system;
    }

    private void system_$eq(ActorSystemImpl x$1) {
        this.akka$actor$LocalActorRefProvider$$system = x$1;
    }

    public Map<String, InternalActorRef> akka$actor$LocalActorRefProvider$$extraNames() {
        return this.akka$actor$LocalActorRefProvider$$extraNames;
    }

    private void extraNames_$eq(Map<String, InternalActorRef> x$1) {
        this.akka$actor$LocalActorRefProvider$$extraNames = x$1;
    }

    public void registerExtraNames(Map<String, InternalActorRef> _extras) {
        this.extraNames_$eq((Map)this.akka$actor$LocalActorRefProvider$$extraNames().$plus$plus(_extras));
    }

    private SupervisorStrategyConfigurator guardianSupervisorStrategyConfigurator() {
        return (SupervisorStrategyConfigurator)this.dynamicAccess().createInstanceFor(this.settings().SupervisorStrategyClass(), Collections$EmptyImmutableSeq$.MODULE$, ClassTag$.MODULE$.apply(SupervisorStrategyConfigurator.class)).get();
    }

    public SupervisorStrategy rootGuardianStrategy() {
        return new OneForOneStrategy(OneForOneStrategy$.MODULE$.apply$default$1(), OneForOneStrategy$.MODULE$.apply$default$2(), OneForOneStrategy$.MODULE$.apply$default$3(), (PartialFunction<Throwable, SupervisorStrategy.Directive>)((Object)new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ LocalActorRefProvider $outer;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                this.$outer.log().error(A1, "guardian failed, shutting down system");
                return (B1)SupervisorStrategy$Stop$.MODULE$;
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
            }
        }));
    }

    public SupervisorStrategy guardianStrategy() {
        return this.guardianSupervisorStrategyConfigurator().create();
    }

    public SupervisorStrategy systemGuardianStrategy() {
        return SupervisorStrategy$.MODULE$.defaultStrategy();
    }

    public MessageDispatcher akka$actor$LocalActorRefProvider$$internalDispatcher() {
        return this.akka$actor$LocalActorRefProvider$$system().dispatchers().internalDispatcher();
    }

    private MailboxType defaultMailbox$lzycompute() {
        LocalActorRefProvider localActorRefProvider = this;
        synchronized (localActorRefProvider) {
            if ((byte)(this.bitmap$0 & 1) == 0) {
                this.akka$actor$LocalActorRefProvider$$defaultMailbox = this.akka$actor$LocalActorRefProvider$$system().mailboxes().lookup("akka.actor.default-mailbox");
                this.bitmap$0 = (byte)(this.bitmap$0 | 1);
            }
        }
        return this.akka$actor$LocalActorRefProvider$$defaultMailbox;
    }

    public MailboxType akka$actor$LocalActorRefProvider$$defaultMailbox() {
        if ((byte)(this.bitmap$0 & 1) == 0) {
            return this.defaultMailbox$lzycompute();
        }
        return this.akka$actor$LocalActorRefProvider$$defaultMailbox;
    }

    private LocalActorRef rootGuardian$lzycompute() {
        LocalActorRefProvider localActorRefProvider = this;
        synchronized (localActorRefProvider) {
            if ((byte)(this.bitmap$0 & 2) == 0) {
                this.rootGuardian = new LocalActorRef(this){
                    private final /* synthetic */ LocalActorRefProvider $outer;

                    private /* synthetic */ InternalActorRef super$getSingleChild(String name) {
                        return super.getSingleChild(name);
                    }

                    public InternalActorRef getParent() {
                        return this;
                    }

                    public InternalActorRef getSingleChild(String name) {
                        String string = name;
                        switch (string == null ? 0 : string.hashCode()) {
                            case -1283521527: {
                                if (!"deadLetters".equals(string)) break;
                                return this.$outer.deadLetters();
                            }
                            case 3556308: {
                                if (!"temp".equals(string)) break;
                                return this.$outer.tempContainer();
                            }
                        }
                        return (InternalActorRef)this.$outer.akka$actor$LocalActorRefProvider$$extraNames().getOrElse(string, (Function0<InternalActorRef> & Serializable)() -> this.super$getSingleChild(string));
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        super($outer.akka$actor$LocalActorRefProvider$$system(), Props$.MODULE$.apply(Guardian.class, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{$outer.rootGuardianStrategy()})), $outer.akka$actor$LocalActorRefProvider$$internalDispatcher(), $outer.akka$actor$LocalActorRefProvider$$defaultMailbox(), $outer.theOneWhoWalksTheBubblesOfSpaceTime(), $outer.rootPath());
                    }

                    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                        return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$getSingleChild$1(akka.actor.LocalActorRefProvider$$anon$2 java.lang.String )}, serializedLambda);
                    }
                };
                this.bitmap$0 = (byte)(this.bitmap$0 | 2);
            }
        }
        return this.rootGuardian;
    }

    @Override
    public LocalActorRef rootGuardian() {
        if ((byte)(this.bitmap$0 & 2) == 0) {
            return this.rootGuardian$lzycompute();
        }
        return this.rootGuardian;
    }

    @Override
    public ActorRef rootGuardianAt(Address address) {
        Address address2 = address;
        Address address3 = this.rootPath().address();
        if (!(address2 != null ? !((Object)address2).equals(address3) : address3 != null)) {
            return this.rootGuardian();
        }
        return this.deadLetters();
    }

    private LocalActorRef guardian$lzycompute() {
        LocalActorRefProvider localActorRefProvider = this;
        synchronized (localActorRefProvider) {
            if ((byte)(this.bitmap$0 & 4) == 0) {
                MailboxType mailboxType;
                MessageDispatcher messageDispatcher;
                ActorCell cell = this.rootGuardian().underlying();
                cell.reserveChild("user");
                Option<Props> option = this.akka$actor$LocalActorRefProvider$$system().guardianProps();
                if (None$.MODULE$.equals(option)) {
                    messageDispatcher = this.akka$actor$LocalActorRefProvider$$internalDispatcher();
                } else if (option instanceof Some) {
                    Some some = (Some)option;
                    Props props = (Props)some.value();
                    String string = props.deploy().dispatcher();
                    String string2 = "..";
                    String dispatcherId = !(string != null ? !string.equals(string2) : string2 != null) ? "akka.actor.default-dispatcher" : props.dispatcher();
                    messageDispatcher = this.akka$actor$LocalActorRefProvider$$system().dispatchers().lookup(dispatcherId);
                } else {
                    throw new MatchError(option);
                }
                MessageDispatcher dispatcher = messageDispatcher;
                ActorSystemImpl actorSystemImpl = this.akka$actor$LocalActorRefProvider$$system();
                Props props = (Props)this.akka$actor$LocalActorRefProvider$$system().guardianProps().getOrElse((Function0<Props> & Serializable)() -> Props$.MODULE$.apply(Guardian.class, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.guardianStrategy()})));
                Option<Props> option2 = this.akka$actor$LocalActorRefProvider$$system().guardianProps();
                if (option2 instanceof Some) {
                    Some some = (Some)option2;
                    Props props2 = (Props)some.value();
                    mailboxType = this.akka$actor$LocalActorRefProvider$$system().mailboxes().lookup(props2.mailbox());
                } else if (None$.MODULE$.equals(option2)) {
                    mailboxType = this.akka$actor$LocalActorRefProvider$$defaultMailbox();
                } else {
                    throw new MatchError(option2);
                }
                LocalActorRef ref = new LocalActorRef(actorSystemImpl, props, dispatcher, mailboxType, this.rootGuardian(), this.rootPath().$div("user"));
                cell.initChild(ref);
                ref.start();
                this.guardian = ref;
                this.bitmap$0 = (byte)(this.bitmap$0 | 4);
            }
        }
        return this.guardian;
    }

    @Override
    public LocalActorRef guardian() {
        if ((byte)(this.bitmap$0 & 4) == 0) {
            return this.guardian$lzycompute();
        }
        return this.guardian;
    }

    /*
     * WARNING - void declaration
     */
    private LocalActorRef systemGuardian$lzycompute() {
        LocalActorRefProvider localActorRefProvider = this;
        synchronized (localActorRefProvider) {
            if ((byte)(this.bitmap$0 & 8) == 0) {
                void var3_3;
                ActorCell cell = this.rootGuardian().underlying();
                cell.reserveChild("system");
                LocalActorRef ref = new LocalActorRef(this.akka$actor$LocalActorRefProvider$$system(), Props$.MODULE$.apply(SystemGuardian.class, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.systemGuardianStrategy(), this.guardian()})), this.akka$actor$LocalActorRefProvider$$internalDispatcher(), this.akka$actor$LocalActorRefProvider$$defaultMailbox(), this.rootGuardian(), this.rootPath().$div("system"));
                cell.initChild(ref);
                ref.start();
                this.systemGuardian = var3_3;
                this.bitmap$0 = (byte)(this.bitmap$0 | 8);
            }
        }
        return this.systemGuardian;
    }

    @Override
    public LocalActorRef systemGuardian() {
        if ((byte)(this.bitmap$0 & 8) == 0) {
            return this.systemGuardian$lzycompute();
        }
        return this.systemGuardian;
    }

    private VirtualPathContainer tempContainer$lzycompute() {
        LocalActorRefProvider localActorRefProvider = this;
        synchronized (localActorRefProvider) {
            if ((byte)(this.bitmap$0 & 0x10) == 0) {
                this.tempContainer = new VirtualPathContainer(this.akka$actor$LocalActorRefProvider$$system().provider(), this.tempNode(), this.rootGuardian(), this.log());
                this.bitmap$0 = (byte)(this.bitmap$0 | 0x10);
            }
        }
        return this.tempContainer;
    }

    @Override
    public VirtualPathContainer tempContainer() {
        if ((byte)(this.bitmap$0 & 0x10) == 0) {
            return this.tempContainer$lzycompute();
        }
        return this.tempContainer;
    }

    @Override
    public void registerTempActor(InternalActorRef actorRef2, ActorPath path) {
        Predef$.MODULE$.assert(path.parent() == this.tempNode(), (Function0<Object>)(Function0<String> & Serializable)() -> "cannot registerTempActor() with anything not obtained from tempPath()");
        this.tempContainer().addChild(path.name(), actorRef2);
    }

    @Override
    public void unregisterTempActor(ActorPath path) {
        Predef$.MODULE$.assert(path.parent() == this.tempNode(), (Function0<Object>)(Function0<String> & Serializable)() -> "cannot unregisterTempActor() with anything not obtained from tempPath()");
        this.tempContainer().removeChild(path.name());
    }

    @Override
    public void init(ActorSystemImpl _system) {
        this.system_$eq(_system);
        this.rootGuardian().start();
        this.systemGuardian().sendSystemMessage(new Watch(this.guardian(), this.systemGuardian()));
        this.rootGuardian().sendSystemMessage(new Watch(this.systemGuardian(), this.rootGuardian()));
        this.eventStream().startDefaultLoggers(_system);
    }

    @Override
    public ActorRef resolveActorRef(String path) {
        Option<Tuple2<Address, scala.collection.immutable.Iterable<String>>> option;
        String string = path;
        if (string != null && !(option = ActorPathExtractor$.MODULE$.unapply(string)).isEmpty()) {
            Address address = option.get()._1();
            scala.collection.immutable.Iterable<String> elems = option.get()._2();
            Address address2 = address;
            Address address3 = this.rootPath().address();
            if (!(address2 != null ? !((Object)address2).equals(address3) : address3 != null)) {
                return this.resolveActorRef(this.rootGuardian(), elems);
            }
        }
        this.logDeser().debug("Resolve (deserialization) of unknown (invalid) path [{}], using deadLetters.", (Object)path);
        return this.deadLetters();
    }

    @Override
    public ActorRef resolveActorRef(ActorPath path) {
        RootActorPath rootActorPath = path.root();
        ActorPath actorPath = this.rootPath();
        if (!(rootActorPath != null ? !((Object)rootActorPath).equals(actorPath) : actorPath != null)) {
            return this.resolveActorRef(this.rootGuardian(), path.elements());
        }
        this.logDeser().debug("Resolve (deserialization) of foreign path [{}] doesn't match root path [{}], using deadLetters.", path, (Object)this.rootPath());
        return this.deadLetters();
    }

    public InternalActorRef resolveActorRef(InternalActorRef ref, Iterable<String> pathElements) {
        if (pathElements.isEmpty()) {
            this.logDeser().debug("Resolve (deserialization) of empty path doesn't match an active actor, using deadLetters.");
            return this.deadLetters();
        }
        InternalActorRef internalActorRef = ref.getChild(pathElements.iterator());
        if (((Object)Nobody$.MODULE$).equals(internalActorRef)) {
            if (this.log().isDebugEnabled()) {
                this.logDeser().debug("Resolve (deserialization) of path [{}] doesn't match an active actor. It has probably been stopped, using deadLetters.", (Object)pathElements.mkString("/"));
            }
            return new EmptyLocalActorRef(this.akka$actor$LocalActorRefProvider$$system().provider(), ref.path().$div(pathElements), this.eventStream());
        }
        return internalActorRef;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Override
    public InternalActorRef actorOf(ActorSystemImpl system, Props props, InternalActorRef supervisor, ActorPath path, boolean systemService, Option<Deploy> deploy, boolean lookupDeploy, boolean async) {
        block14: {
            block16: {
                block19: {
                    block18: {
                        block17: {
                            block15: {
                                var14_9 = props.deploy().routerConfig();
                                if (!NoRouter$.MODULE$.equals(var14_9)) break block14;
                                if (this.settings().DebugRouterMisconfiguration()) {
                                    this.deployer().lookup(path).foreach((Function1<Deploy, Object> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, $anonfun$actorOf$1$adapted(akka.actor.LocalActorRefProvider akka.actor.ActorPath akka.actor.Deploy ), (Lakka/actor/Deploy;)Ljava/lang/Object;)((LocalActorRefProvider)this, (ActorPath)path));
                                }
                                v0 = var16_10 = lookupDeploy != false ? this.deployer().lookup(path) : deploy;
                                if (!(var16_10 instanceof Some)) ** GOTO lbl50
                                var17_11 = (Some)var16_10;
                                d = (Deploy)var17_11.value();
                                var19_13 = new Tuple2<String, String>(d.dispatcher(), d.mailbox());
                                if (var19_13 == null) break block15;
                                var20_14 = var19_13._1();
                                var21_15 = var19_13._2();
                                if (!"".equals(var20_14) || !"".equals(var21_15)) break block15;
                                v1 = props;
                                break block16;
                            }
                            if (var19_13 == null) break block17;
                            var22_16 = var19_13._1();
                            var23_17 = var19_13._2();
                            if (!"..".equals(var22_16) || !"".equals(var23_17)) break block17;
                            v1 = props.withDispatcher(LocalActorRefProvider.parentDispatcher$1(supervisor));
                            break block16;
                        }
                        if (var19_13 == null) break block18;
                        dsp = var19_13._1();
                        var25_19 = var19_13._2();
                        if (!"".equals(var25_19)) break block18;
                        v1 = props.withDispatcher(dsp);
                        break block16;
                    }
                    if (var19_13 == null) break block19;
                    var26_20 = var19_13._1();
                    mbx = var19_13._2();
                    if (!"".equals(var26_20)) break block19;
                    v1 = props.withMailbox(mbx);
                    break block16;
                }
                if (var19_13 == null) ** GOTO lbl-1000
                var28_22 = var19_13._1();
                mbx = var19_13._2();
                if ("..".equals(var28_22)) {
                    v1 = props.withDispatcher(LocalActorRefProvider.parentDispatcher$1(supervisor)).withMailbox(mbx);
                } else if (var19_13 != null) {
                    dsp = var19_13._1();
                    mbx = var19_13._2();
                    v1 = props.withDispatcher(dsp).withMailbox(mbx);
                } else {
                    throw new MatchError(var19_13);
lbl50:
                    // 1 sources

                    v2 = props.deploy().dispatcher();
                    var32_26 = "..";
                    v1 = !(v2 != null ? v2.equals(var32_26) == false : var32_26 != null) ? props.withDispatcher(LocalActorRefProvider.parentDispatcher$1(supervisor)) : (props2 = props);
                }
            }
            if (!system.dispatchers().hasDispatcher(props2.dispatcher())) {
                throw new ConfigurationException(new StringBuilder(38).append("Dispatcher [").append(props2.dispatcher()).append("] not configured for path ").append(path).toString());
            }
            try {
                dispatcher = system.dispatchers().lookup(props2.dispatcher());
                mailboxType = system.mailboxes().getMailboxType(props2, dispatcher.configurator().config());
                v3 /* !! */  = async ? new RepointableActorRef(system, props2, dispatcher, mailboxType, supervisor, path).initialize(async) : new LocalActorRef(system, props2, dispatcher, mailboxType, supervisor, path);
            }
            catch (Throwable var35_30) {
                var36_31 = var35_30;
                if (var36_31 != null && !(var37_32 = NonFatal$.MODULE$.unapply(var36_31)).isEmpty()) {
                    e = var37_32.get();
                    throw new ConfigurationException(new StringBuilder(73).append("configuration problem while creating [").append(path).append("] with dispatcher [").append(props2.dispatcher()).append("] and mailbox [").append(props2.mailbox()).append("]").toString(), e);
                }
                throw var35_30;
            }
            return v3 /* !! */ ;
        }
        lookup = lookupDeploy != false ? this.deployer().lookup(path) : None$.MODULE$;
        var41_35 = var14_9;
        var42_36 = deploy.map((Function1<Deploy, RouterConfig> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, $anonfun$actorOf$2(akka.actor.Deploy ), (Lakka/actor/Deploy;)Lakka/routing/RouterConfig;)()).toList();
        r = (RouterConfig)lookup.map((Function1<Deploy, RouterConfig> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, $anonfun$actorOf$3(akka.actor.Deploy ), (Lakka/actor/Deploy;)Lakka/routing/RouterConfig;)()).toList().$colon$colon$colon(var42_36).$colon$colon(var41_35).reduce((Function2<RouterConfig, RouterConfig, RouterConfig> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;, $anonfun$actorOf$4(akka.routing.RouterConfig akka.routing.RouterConfig ), (Lakka/routing/RouterConfig;Lakka/routing/RouterConfig;)Lakka/routing/RouterConfig;)());
        p = props.withRouter(r);
        if (!system.dispatchers().hasDispatcher(p.dispatcher())) {
            throw new ConfigurationException(new StringBuilder(44).append("Dispatcher [").append(p.dispatcher()).append("] not configured for routees of ").append(path).toString());
        }
        if (!system.dispatchers().hasDispatcher(r.routerDispatcher())) {
            throw new ConfigurationException(new StringBuilder(43).append("Dispatcher [").append(p.dispatcher()).append("] not configured for router of ").append(path).toString());
        }
        x$1 = p.routerConfig().routerDispatcher();
        x$2 = p.deploy().copy$default$1();
        x$3 = p.deploy().copy$default$2();
        x$4 = p.deploy().copy$default$3();
        x$5 = p.deploy().copy$default$4();
        x$6 = p.deploy().copy$default$6();
        routerProps = new Props(p.deploy().copy(x$2, x$3, x$4, x$5, x$1, x$6), RoutedActorCell.RouterActorCreator.class, (Seq)package$.MODULE$.Vector().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new RouterConfig[]{p.routerConfig()})));
        routeeProps = p.withRouter(NoRouter$.MODULE$);
        try {
            routerDispatcher = system.dispatchers().lookup(p.routerConfig().routerDispatcher());
            routerMailbox = system.mailboxes().getMailboxType(routerProps, routerDispatcher.configurator().config());
            routeeDispatcher = system.dispatchers().lookup(p.dispatcher());
            system.mailboxes().getMailboxType(routeeProps, routeeDispatcher.configurator().config());
            v4 = new RoutedActorRef(system, routerProps, routerDispatcher, routerMailbox, routeeProps, supervisor, path).initialize(async);
        }
        catch (Throwable var55_50) {
            var56_51 = var55_50;
            if (var56_51 != null && !(var57_52 = NonFatal$.MODULE$.unapply(var56_51)).isEmpty()) {
                e = var57_52.get();
                throw new ConfigurationException(new StringBuilder(120).append("configuration problem while creating [").append(path).append("] with router dispatcher [").append(routerProps.dispatcher()).append("] and mailbox [").append(routerProps.mailbox()).append("] ").append("and routee dispatcher [").append(routeeProps.dispatcher()).append("] and mailbox [").append(routeeProps.mailbox()).append("]").toString(), e);
            }
            throw var55_50;
        }
        return v4;
    }

    @Override
    public Option<Address> getExternalAddressFor(Address addr) {
        Address address = addr;
        Address address2 = this.rootPath().address();
        if (!(address != null ? !((Object)address).equals(address2) : address2 != null)) {
            return new Some<Address>(addr);
        }
        return None$.MODULE$;
    }

    @Override
    public Address getDefaultAddress() {
        return this.rootPath().address();
    }

    private Serialization.Information serializationInformationCache() {
        return this.serializationInformationCache;
    }

    private void serializationInformationCache_$eq(Serialization.Information x$1) {
        this.serializationInformationCache = x$1;
    }

    @Override
    @InternalApi
    public Serialization.Information serializationInformation() {
        new Serialization.Information(this.getDefaultAddress(), this.akka$actor$LocalActorRefProvider$$system());
        Serialization.Information information = this.serializationInformationCache();
        Serialization.Information information2 = OptionVal$Some$.MODULE$.unapply(information);
        if (!OptionVal$.MODULE$.isEmpty$extension(information2)) {
            Serialization.Information info = OptionVal$.MODULE$.get$extension(information2);
            return info;
        }
        if (this.akka$actor$LocalActorRefProvider$$system() == null) {
            throw new IllegalStateException("Too early access of serializationInformation");
        }
        Serialization.Information info = new Serialization.Information(this.rootPath().address(), this.akka$actor$LocalActorRefProvider$$system());
        this.serializationInformationCache_$eq(OptionVal$Some$.MODULE$.apply(info));
        return info;
    }

    private String _addressString() {
        return this._addressString;
    }

    private void _addressString_$eq(String x$1) {
        this._addressString = x$1;
    }

    @Override
    public String addressString() {
        String string = this._addressString();
        String string2 = OptionVal$Some$.MODULE$.unapply(string);
        if (!OptionVal$.MODULE$.isEmpty$extension(string2)) {
            String addr = OptionVal$.MODULE$.get$extension(string2);
            return addr;
        }
        String addr = this.getDefaultAddress().toString();
        this._addressString_$eq(OptionVal$Some$.MODULE$.apply(addr));
        return addr;
    }

    public static final /* synthetic */ void $anonfun$actorOf$1(LocalActorRefProvider $this, ActorPath path$1, Deploy d) {
        RouterConfig routerConfig = d.routerConfig();
        NoRouter$ noRouter$ = NoRouter$.MODULE$;
        if (routerConfig == null ? noRouter$ != null : !routerConfig.equals(noRouter$)) {
            $this.log().warning("Configuration says that [{}] should be a router, but code disagrees. Remove the config or add a routerConfig to its Props.", path$1);
            return;
        }
    }

    private static final String parentDispatcher$1(InternalActorRef supervisor$1) {
        InternalActorRef internalActorRef = supervisor$1;
        if (internalActorRef instanceof ActorRefWithCell) {
            ActorRefWithCell actorRefWithCell = (ActorRefWithCell)internalActorRef;
            return actorRefWithCell.underlying().props().dispatcher();
        }
        return "";
    }

    public static final /* synthetic */ RouterConfig $anonfun$actorOf$2(Deploy x$3) {
        return x$3.routerConfig();
    }

    public static final /* synthetic */ RouterConfig $anonfun$actorOf$3(Deploy x$4) {
        return x$4.routerConfig();
    }

    public static final /* synthetic */ RouterConfig $anonfun$actorOf$4(RouterConfig a, RouterConfig b) {
        return b.withFallback(a);
    }

    public LocalActorRefProvider(String _systemName, ActorSystem.Settings settings, EventStream eventStream, DynamicAccess dynamicAccess, Deployer deployer, Option<Function1<ActorPath, InternalActorRef>> _deadLetters) {
        this.settings = settings;
        this.eventStream = eventStream;
        this.dynamicAccess = dynamicAccess;
        this.deployer = deployer;
        this.rootPath = new RootActorPath(Address$.MODULE$.apply("akka", _systemName), RootActorPath$.MODULE$.apply$default$2());
        this.log = Logging$.MODULE$.withMarker(eventStream, LocalActorRefProvider.class, LogSource$.MODULE$.fromAnyClass());
        this.logDeser = Logging$.MODULE$.withMarker(eventStream, new StringBuilder(16).append(this.getClass().getName()).append(".Deserialization").toString(), LogSource$.MODULE$.fromString());
        this.deadLetters = (InternalActorRef)((Function1)_deadLetters.getOrElse((Function0<Function1> & Serializable)() -> (Function1<ActorPath, DeadLetterActorRef> & Serializable)p -> new DeadLetterActorRef(this, (ActorPath)p, this.eventStream()))).apply(this.rootPath().$div("deadLetters"));
        this.ignoreRef = new IgnoreActorRef(this);
        this.akka$actor$LocalActorRefProvider$$terminationPromise = Promise$.MODULE$.apply();
        this.tempNumber = new AtomicLong();
        this.tempNode = this.rootPath().$div("temp");
        this.theOneWhoWalksTheBubblesOfSpaceTime = new MinimalActorRef(this){
            private final Promise<Terminated> causeOfTermination;
            private final ActorPath path;
            private final /* synthetic */ LocalActorRefProvider $outer;

            public InternalActorRef getParent() {
                return MinimalActorRef.getParent$(this);
            }

            public InternalActorRef getChild(Iterator<String> names) {
                return MinimalActorRef.getChild$(this, names);
            }

            public void start() {
                MinimalActorRef.start$(this);
            }

            public void suspend() {
                MinimalActorRef.suspend$(this);
            }

            public void resume(Throwable causedByFailure) {
                MinimalActorRef.resume$(this, causedByFailure);
            }

            public void restart(Throwable cause) {
                MinimalActorRef.restart$(this, cause);
            }

            public Object writeReplace() throws ObjectStreamException {
                return MinimalActorRef.writeReplace$(this);
            }

            public final boolean isLocal() {
                return LocalRef.isLocal$(this);
            }

            private Promise<Terminated> causeOfTermination() {
                return this.causeOfTermination;
            }

            public ActorPath path() {
                return this.path;
            }

            public ActorRefProvider provider() {
                return this.$outer;
            }

            private boolean isWalking() {
                return !this.causeOfTermination().future().isCompleted();
            }

            public void stop() {
                this.causeOfTermination().trySuccess(Terminated$.MODULE$.apply(this.provider().rootGuardian(), true, true));
                this.$outer.akka$actor$LocalActorRefProvider$$terminationPromise.completeWith(this.causeOfTermination().future());
            }

            @InternalApi
            public boolean isTerminated() {
                return !this.isWalking();
            }

            public void $bang(Object message, ActorRef sender) {
                if (this.isWalking()) {
                    Object object = message;
                    if (object == null) {
                        throw InvalidMessageException$.MODULE$.apply("Message is null");
                    }
                    this.$outer.log().error(new StringBuilder(31).append(this).append(" received unexpected message [").append(message).append("]").toString());
                    return;
                }
            }

            public void sendSystemMessage(SystemMessage message) {
                if (this.isWalking()) {
                    SystemMessage systemMessage = message;
                    if (systemMessage instanceof Failed) {
                        Failed failed = (Failed)systemMessage;
                        ActorRef child = failed.child();
                        Throwable ex = failed.cause();
                        if (child instanceof InternalActorRef) {
                            InternalActorRef internalActorRef = (InternalActorRef)child;
                            this.$outer.log().error(ex, new StringBuilder(32).append("guardian ").append(internalActorRef).append(" failed, shutting down!").toString());
                            this.causeOfTermination().tryFailure(ex);
                            internalActorRef.stop();
                            return;
                        }
                    }
                    if (systemMessage instanceof Supervise) {
                        return;
                    }
                    if (systemMessage instanceof DeathWatchNotification) {
                        this.stop();
                        return;
                    }
                    this.$outer.log().error(new StringBuilder(38).append(this).append(" received unexpected system message [").append(message).append("]").toString());
                    return;
                }
            }

            public ActorRef $bang$default$2(Object message) {
                return Actor$.MODULE$.noSender();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                LocalRef.$init$(this);
                MinimalActorRef.$init$(this);
                this.causeOfTermination = Promise$.MODULE$.apply();
                this.path = $outer.rootPath().$div("bubble-walker");
            }
        };
        this.akka$actor$LocalActorRefProvider$$extraNames = (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$);
        OptionVal$.MODULE$.None();
        this.serializationInformationCache = null;
        OptionVal$.MODULE$.None();
        this._addressString = null;
    }

    public LocalActorRefProvider(String _systemName, ActorSystem.Settings settings, EventStream eventStream, DynamicAccess dynamicAccess) {
        this(_systemName, settings, eventStream, dynamicAccess, new Deployer(settings, dynamicAccess), None$.MODULE$);
    }

    public static final /* synthetic */ Object $anonfun$actorOf$1$adapted(LocalActorRefProvider $this, ActorPath path$1, Deploy d) {
        LocalActorRefProvider.$anonfun$actorOf$1($this, path$1, d);
        return BoxedUnit.UNIT;
    }

    private static class Guardian
    implements Actor,
    RequiresMessageQueue<UnboundedMessageQueueSemantics> {
        private final SupervisorStrategy supervisorStrategy;
        private ActorContext context;
        private ActorRef self;

        @Override
        public final ActorRef sender() {
            return Actor.sender$(this);
        }

        @Override
        @InternalApi
        public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
            Actor.aroundReceive$(this, receive2, msg);
        }

        @Override
        @InternalApi
        public void aroundPreStart() {
            Actor.aroundPreStart$(this);
        }

        @Override
        @InternalApi
        public void aroundPostStop() {
            Actor.aroundPostStop$(this);
        }

        @Override
        @InternalApi
        public void aroundPreRestart(Throwable reason, Option<Object> message) {
            Actor.aroundPreRestart$(this, reason, message);
        }

        @Override
        @InternalApi
        public void aroundPostRestart(Throwable reason) {
            Actor.aroundPostRestart$(this, reason);
        }

        @Override
        public void preStart() throws Exception {
            Actor.preStart$(this);
        }

        @Override
        public void postStop() throws Exception {
            Actor.postStop$(this);
        }

        @Override
        public void postRestart(Throwable reason) throws Exception {
            Actor.postRestart$(this, reason);
        }

        @Override
        public void unhandled(Object message) {
            Actor.unhandled$(this, message);
        }

        @Override
        public ActorContext context() {
            return this.context;
        }

        @Override
        public final ActorRef self() {
            return this.self;
        }

        @Override
        public void akka$actor$Actor$_setter_$context_$eq(ActorContext x$1) {
            this.context = x$1;
        }

        @Override
        public final void akka$actor$Actor$_setter_$self_$eq(ActorRef x$1) {
            this.self = x$1;
        }

        @Override
        public SupervisorStrategy supervisorStrategy() {
            return this.supervisorStrategy;
        }

        @Override
        public PartialFunction<Object, BoxedUnit> receive() {
            return new Serializable(this){
                private static final long serialVersionUID = 0L;
                private final /* synthetic */ Guardian $outer;

                public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                    A1 A1 = x1;
                    if (A1 instanceof Terminated) {
                        this.$outer.context().stop(this.$outer.self());
                        return (B1)BoxedUnit.UNIT;
                    }
                    if (A1 instanceof StopChild) {
                        StopChild stopChild = (StopChild)A1;
                        ActorRef child = stopChild.child();
                        this.$outer.context().stop(child);
                        return (B1)BoxedUnit.UNIT;
                    }
                    return function1.apply(x1);
                }

                public final boolean isDefinedAt(Object x1) {
                    Object object = x1;
                    if (object instanceof Terminated) {
                        return true;
                    }
                    return object instanceof StopChild;
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
        public void preRestart(Throwable cause, Option<Object> msg) {
        }

        public Guardian(SupervisorStrategy supervisorStrategy) {
            this.supervisorStrategy = supervisorStrategy;
            Actor.$init$(this);
            Statics.releaseFence();
        }
    }

    private static class SystemGuardian
    implements Actor,
    RequiresMessageQueue<UnboundedMessageQueueSemantics> {
        private final SupervisorStrategy supervisorStrategy;
        private final ActorRef guardian;
        private Set<ActorRef> terminationHooks;
        private ActorContext context;
        private ActorRef self;

        @Override
        public final ActorRef sender() {
            return Actor.sender$(this);
        }

        @Override
        @InternalApi
        public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
            Actor.aroundReceive$(this, receive2, msg);
        }

        @Override
        @InternalApi
        public void aroundPreStart() {
            Actor.aroundPreStart$(this);
        }

        @Override
        @InternalApi
        public void aroundPostStop() {
            Actor.aroundPostStop$(this);
        }

        @Override
        @InternalApi
        public void aroundPreRestart(Throwable reason, Option<Object> message) {
            Actor.aroundPreRestart$(this, reason, message);
        }

        @Override
        @InternalApi
        public void aroundPostRestart(Throwable reason) {
            Actor.aroundPostRestart$(this, reason);
        }

        @Override
        public void preStart() throws Exception {
            Actor.preStart$(this);
        }

        @Override
        public void postStop() throws Exception {
            Actor.postStop$(this);
        }

        @Override
        public void postRestart(Throwable reason) throws Exception {
            Actor.postRestart$(this, reason);
        }

        @Override
        public void unhandled(Object message) {
            Actor.unhandled$(this, message);
        }

        @Override
        public ActorContext context() {
            return this.context;
        }

        @Override
        public final ActorRef self() {
            return this.self;
        }

        @Override
        public void akka$actor$Actor$_setter_$context_$eq(ActorContext x$1) {
            this.context = x$1;
        }

        @Override
        public final void akka$actor$Actor$_setter_$self_$eq(ActorRef x$1) {
            this.self = x$1;
        }

        @Override
        public SupervisorStrategy supervisorStrategy() {
            return this.supervisorStrategy;
        }

        public ActorRef guardian() {
            return this.guardian;
        }

        public Set<ActorRef> terminationHooks() {
            return this.terminationHooks;
        }

        public void terminationHooks_$eq(Set<ActorRef> x$1) {
            this.terminationHooks = x$1;
        }

        @Override
        public PartialFunction<Object, BoxedUnit> receive() {
            return new Serializable(this){
                private static final long serialVersionUID = 0L;
                private final /* synthetic */ SystemGuardian $outer;

                public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                    boolean bl = false;
                    Terminated terminated = null;
                    A1 A1 = x1;
                    if (A1 instanceof Terminated) {
                        bl = true;
                        terminated = (Terminated)A1;
                        ActorRef actorRef2 = terminated.actor();
                        ActorRef actorRef3 = this.$outer.guardian();
                        ActorRef actorRef4 = actorRef2;
                        if (!(actorRef3 != null ? !((Object)actorRef3).equals(actorRef4) : actorRef4 != null)) {
                            this.$outer.context().become(this.$outer.terminating());
                            this.$outer.terminationHooks().foreach((Function1<ActorRef, Object> & Serializable)x$1 -> {
                                SystemGuardian$$anonfun$receive$2.$anonfun$applyOrElse$1(this, x$1);
                                return BoxedUnit.UNIT;
                            });
                            this.$outer.stopWhenAllTerminationHooksDone();
                            return (B1)BoxedUnit.UNIT;
                        }
                    }
                    if (bl) {
                        ActorRef a = terminated.actor();
                        this.$outer.terminationHooks_$eq((Set)this.$outer.terminationHooks().$minus(a));
                        return (B1)BoxedUnit.UNIT;
                    }
                    if (A1 instanceof StopChild) {
                        StopChild stopChild = (StopChild)A1;
                        ActorRef child = stopChild.child();
                        this.$outer.context().stop(child);
                        return (B1)BoxedUnit.UNIT;
                    }
                    if (SystemGuardian$RegisterTerminationHook$.MODULE$.equals(A1)) {
                        ActorRef actorRef5 = this.$outer.sender();
                        ActorRef actorRef6 = this.$outer.context().system().deadLetters();
                        if (actorRef5 == null ? actorRef6 != null : !((Object)actorRef5).equals(actorRef6)) {
                            this.$outer.terminationHooks_$eq((Set)this.$outer.terminationHooks().$plus(this.$outer.sender()));
                            this.$outer.context().watch(this.$outer.sender());
                            return (B1)BoxedUnit.UNIT;
                        }
                    }
                    return function1.apply(x1);
                }

                public final boolean isDefinedAt(Object x1) {
                    boolean bl = false;
                    Terminated terminated = null;
                    Object object = x1;
                    if (object instanceof Terminated) {
                        bl = true;
                        terminated = (Terminated)object;
                        ActorRef actorRef2 = terminated.actor();
                        ActorRef actorRef3 = this.$outer.guardian();
                        ActorRef actorRef4 = actorRef2;
                        if (!(actorRef3 != null ? !((Object)actorRef3).equals(actorRef4) : actorRef4 != null)) {
                            return true;
                        }
                    }
                    if (bl) {
                        return true;
                    }
                    if (object instanceof StopChild) {
                        return true;
                    }
                    if (SystemGuardian$RegisterTerminationHook$.MODULE$.equals(object)) {
                        ActorRef actorRef5 = this.$outer.sender();
                        ActorRef actorRef6 = this.$outer.context().system().deadLetters();
                        if (actorRef5 == null ? actorRef6 != null : !((Object)actorRef5).equals(actorRef6)) {
                            return true;
                        }
                    }
                    return false;
                }

                public static final /* synthetic */ void $anonfun$applyOrElse$1(SystemGuardian$$anonfun$receive$2 $this, ActorRef x$1) {
                    x$1.$bang(SystemGuardian$TerminationHook$.MODULE$, $this.$outer.self());
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                }

                private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                    return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1$adapted(akka.actor.LocalActorRefProvider$SystemGuardian$$anonfun$receive$2 akka.actor.ActorRef )}, serializedLambda);
                }
            };
        }

        public PartialFunction<Object, BoxedUnit> terminating() {
            return new Serializable(this){
                private static final long serialVersionUID = 0L;
                private final /* synthetic */ SystemGuardian $outer;

                public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                    A1 A1 = x1;
                    if (A1 instanceof Terminated) {
                        Terminated terminated = (Terminated)A1;
                        ActorRef a = terminated.actor();
                        this.$outer.stopWhenAllTerminationHooksDone(a);
                        return (B1)BoxedUnit.UNIT;
                    }
                    if (SystemGuardian$TerminationHookDone$.MODULE$.equals(A1)) {
                        this.$outer.stopWhenAllTerminationHooksDone(this.$outer.sender());
                        return (B1)BoxedUnit.UNIT;
                    }
                    return function1.apply(x1);
                }

                public final boolean isDefinedAt(Object x1) {
                    Object object = x1;
                    if (object instanceof Terminated) {
                        return true;
                    }
                    return SystemGuardian$TerminationHookDone$.MODULE$.equals(object);
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                }
            };
        }

        public void stopWhenAllTerminationHooksDone(ActorRef remove) {
            this.terminationHooks_$eq((Set)this.terminationHooks().$minus(remove));
            this.stopWhenAllTerminationHooksDone();
        }

        public void stopWhenAllTerminationHooksDone() {
            if (this.terminationHooks().isEmpty()) {
                this.context().system().eventStream().stopDefaultLoggers(this.context().system());
                this.context().stop(this.self());
                return;
            }
        }

        @Override
        public void preRestart(Throwable cause, Option<Object> msg) {
        }

        public SystemGuardian(SupervisorStrategy supervisorStrategy, ActorRef guardian) {
            this.supervisorStrategy = supervisorStrategy;
            this.guardian = guardian;
            Actor.$init$(this);
            this.terminationHooks = Predef$.MODULE$.Set().empty();
            Statics.releaseFence();
        }
    }
}

