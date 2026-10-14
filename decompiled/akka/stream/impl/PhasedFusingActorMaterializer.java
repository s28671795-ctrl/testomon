/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.NotUsed$;
import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.PoisonPill$;
import akka.actor.Props;
import akka.annotation.InternalApi;
import akka.annotation.InternalStableApi;
import akka.dispatch.Dispatchers;
import akka.event.LogSource$;
import akka.event.Logging$;
import akka.event.LoggingAdapter;
import akka.stream.ActorAttributes;
import akka.stream.ActorMaterializerSettings;
import akka.stream.Attributes;
import akka.stream.ClosedShape;
import akka.stream.Graph;
import akka.stream.InPort;
import akka.stream.Inlet;
import akka.stream.MaterializationContext;
import akka.stream.Outlet;
import akka.stream.Shape;
import akka.stream.Shape$;
import akka.stream.impl.Compose;
import akka.stream.impl.Concat;
import akka.stream.impl.EmptyTraversal$;
import akka.stream.impl.EnterIsland;
import akka.stream.impl.ExitIsland$;
import akka.stream.impl.ExtendedActorMaterializer;
import akka.stream.impl.IslandTag;
import akka.stream.impl.IslandTracking;
import akka.stream.impl.MaterializeAtomic;
import akka.stream.impl.Phase;
import akka.stream.impl.PhasedFusingActorMaterializer$;
import akka.stream.impl.Pop$;
import akka.stream.impl.PopAttributes$;
import akka.stream.impl.PushAttributes;
import akka.stream.impl.PushNotUsed$;
import akka.stream.impl.SeqActorName;
import akka.stream.impl.StreamLayout;
import akka.stream.impl.Transform;
import akka.stream.impl.Traversal;
import akka.stream.impl.TraversalBuilder$;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Tuple2;
import scala.Tuple7;
import scala.collection.Iterator;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\r\rxAB#G\u0011\u0003QEJ\u0002\u0004O\r\"\u0005!j\u0014\u0005\u0006=\u0006!\t\u0001\u0019\u0005\bC\u0006\u0011\r\u0011\"\u0001c\u0011\u00191\u0017\u0001)A\u0005G\"9q-\u0001b\u0001\n\u0003A\u0007B\u0002;\u0002A\u0003%\u0011\u000eC\u0004v\u0003\t\u0007I\u0011\u0001<\t\ru\f\u0001\u0015!\u0003x\u0011\u001dq\u0018A1A\u0005\u0002}D\u0001\"a\u0006\u0002A\u0003%\u0011\u0011\u0001\u0005\b\u00033\tA\u0011AA\u000e\u0011%\tI\"AA\u0001\n\u0003\u001b\t\fC\u0005\u0004B\u0006\t\t\u0011\"!\u0004D\"I1Q[\u0001\u0002\u0002\u0013%1q\u001b\u0004\u0007\u001d\u001a\u0003%*a\b\t\u0015\u0005urB!f\u0001\n\u0003\ty\u0004\u0003\u0006\u0002N=\u0011\t\u0012)A\u0005\u0003\u0003B!\"a\u0014\u0010\u0005+\u0007I\u0011IA)\u0011)\tYf\u0004B\tB\u0003%\u00111\u000b\u0005\u000b\u0003;z!Q3A\u0005\u0002\u0005}\u0003BCA4\u001f\tE\t\u0015!\u0003\u0002b!Q\u0011\u0011N\b\u0003\u0016\u0004%\t!a\u001b\t\u0015\u0005etB!E!\u0002\u0013\ti\u0007\u0003\u0006\u0002|=\u0011)\u001a!C\u0001\u0003{B!\"!\"\u0010\u0005#\u0005\u000b\u0011BA@\u0011)\t9i\u0004BK\u0002\u0013\u0005\u0011\u0011\u0012\u0005\u000b\u0003?{!\u0011#Q\u0001\n\u0005-\u0005BCAQ\u001f\tU\r\u0011\"\u0001\u0002$\"Q\u00111V\b\u0003\u0012\u0003\u0006I!!*\t\ry{A\u0011AAW\u0011%\til\u0004b\u0001\n\u0013\ty\f\u0003\u0005\u0002N>\u0001\u000b\u0011BAa\u0011\u001d\tym\u0004C!\u0003\u007fC\u0001\"!5\u0010\u0005\u0004%IA\u0019\u0005\b\u0003'|\u0001\u0015!\u0003d\u0011\u001d\t)n\u0004C!\u0003/Da!a8\u0010\t\u0003\u0012\u0007bBAq\u001f\u0011\u0005\u00131\u001d\u0005\t\u0003S|\u0001\u0015\"\u0003\u0002l\"Q\u0011Q^\b\t\u0006\u0004%\t%a<\t\u000f\u0005mx\u0002\"\u0011\u0002~\"9!\u0011F\b\u0005B\t-\u0002b\u0002B\u001b\u001f\u0011\u0005#q\u0007\u0005\b\u0005\u007fyA\u0011\tB!\u0011\u001d\u00119e\u0004C!\u0005\u0013BqAa\u0012\u0010\t\u0003\u0012i\u0007C\u0004\u0003H=!\tE!#\t\u000f\t\u0005v\u0002\"\u0003\u0003$\"9!1Z\b\u0005\n\t5\u0007b\u0002Bu\u001f\u0011\u0005#1\u001e\u0005\t\u0005o|A\u0011\t&\u0003z\"I1qC\b\u0002\u0002\u0013\u00051\u0011\u0004\u0005\n\u0007Sy\u0011\u0013!C\u0001\u0007WA\u0011ba\u0010\u0010#\u0003%\ta!\u0011\t\u0013\r\u0015s\"%A\u0005\u0002\r\u001d\u0003\"CB&\u001fE\u0005I\u0011AB'\u0011%\u0019\tfDI\u0001\n\u0003\u0019\u0019\u0006C\u0005\u0004X=\t\n\u0011\"\u0001\u0004Z!I1QL\b\u0012\u0002\u0013\u00051q\f\u0005\n\u0007Gz\u0011\u0011!C!\u0007KB\u0011ba\u001b\u0010\u0003\u0003%\ta!\u001c\t\u0013\r=t\"!A\u0005\u0002\rE\u0004\"CB<\u001f\u0005\u0005I\u0011IB=\u0011%\u0019\u0019iDA\u0001\n\u0003\u0019)\tC\u0005\u0004\n>\t\t\u0011\"\u0011\u0004\f\"I1qR\b\u0002\u0002\u0013\u00053\u0011\u0013\u0005\n\u0007'{\u0011\u0011!C!\u0007+C\u0011ba&\u0010\u0003\u0003%\te!'\u0002;AC\u0017m]3e\rV\u001c\u0018N\\4BGR|'/T1uKJL\u0017\r\\5{KJT!a\u0012%\u0002\t%l\u0007\u000f\u001c\u0006\u0003\u0013*\u000baa\u001d;sK\u0006l'\"A&\u0002\t\u0005\\7.\u0019\t\u0003\u001b\u0006i\u0011A\u0012\u0002\u001e!\"\f7/\u001a3GkNLgnZ!di>\u0014X*\u0019;fe&\fG.\u001b>feN\u0019\u0011\u0001\u0015,\u0011\u0005E#V\"\u0001*\u000b\u0003M\u000bQa]2bY\u0006L!!\u0016*\u0003\r\u0005s\u0017PU3g!\t9F,D\u0001Y\u0015\tI&,\u0001\u0002j_*\t1,\u0001\u0003kCZ\f\u0017BA/Y\u00051\u0019VM]5bY&T\u0018M\u00197f\u0003\u0019a\u0014N\\5u}\r\u0001A#\u0001'\u0002\u000b\u0011+'-^4\u0016\u0003\r\u0004\"!\u00153\n\u0005\u0015\u0014&a\u0002\"p_2,\u0017M\\\u0001\u0007\t\u0016\u0014Wo\u001a\u0011\u0002\u000f5\u000b\u0017\u000e\u001c2pqV\t\u0011\u000e\u0005\u0002kc:\u00111n\u001c\t\u0003YJk\u0011!\u001c\u0006\u0003]~\u000ba\u0001\u0010:p_Rt\u0014B\u00019S\u0003\u0019\u0001&/\u001a3fM&\u0011!o\u001d\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005A\u0014\u0016\u0001C'bS2\u0014w\u000e\u001f\u0011\u0002\u0019\u0011+g-Y;miBC\u0017m]3\u0016\u0003]\u00042!\u0014={\u0013\tIhIA\u0003QQ\u0006\u001cX\r\u0005\u0002Rw&\u0011AP\u0015\u0002\u0004\u0003:L\u0018!\u0004#fM\u0006,H\u000e\u001e)iCN,\u0007%A\u0007EK\u001a\fW\u000f\u001c;QQ\u0006\u001cXm]\u000b\u0003\u0003\u0003\u0001r!a\u0001\u0002\u000e\u0005Eq/\u0004\u0002\u0002\u0006)!\u0011qAA\u0005\u0003%IW.\\;uC\ndWMC\u0002\u0002\fI\u000b!bY8mY\u0016\u001cG/[8o\u0013\u0011\ty!!\u0002\u0003\u00075\u000b\u0007\u000fE\u0002N\u0003'I1!!\u0006G\u0005%I5\u000f\\1oIR\u000bw-\u0001\bEK\u001a\fW\u000f\u001c;QQ\u0006\u001cXm\u001d\u0011\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0015\u0005u1qTBT\u0007W\u001bi\u000b\u0005\u0002N\u001fM9q\"!\t\u0002(\u00055\u0002cA'\u0002$%\u0019\u0011Q\u0005$\u00033\u0015CH/\u001a8eK\u0012\f5\r^8s\u001b\u0006$XM]5bY&TXM\u001d\t\u0004#\u0006%\u0012bAA\u0016%\n9\u0001K]8ek\u000e$\b\u0003BA\u0018\u0003sqA!!\r\u000269\u0019A.a\r\n\u0003MK1!a\u000eS\u0003\u001d\u0001\u0018mY6bO\u0016L1!XA\u001e\u0015\r\t9DU\u0001\u0007gf\u001cH/Z7\u0016\u0005\u0005\u0005\u0003\u0003BA\"\u0003\u0013j!!!\u0012\u000b\u0007\u0005\u001d#*A\u0003bGR|'/\u0003\u0003\u0002L\u0005\u0015#aC!di>\u00148+_:uK6\fqa]=ti\u0016l\u0007%\u0001\u0005tKR$\u0018N\\4t+\t\t\u0019\u0006\u0005\u0003\u0002V\u0005]S\"\u0001%\n\u0007\u0005e\u0003JA\rBGR|'/T1uKJL\u0017\r\\5{KJ\u001cV\r\u001e;j]\u001e\u001c\u0018!C:fiRLgnZ:!\u0003E!WMZ1vYR\fE\u000f\u001e:jEV$Xm]\u000b\u0003\u0003C\u0002B!!\u0016\u0002d%\u0019\u0011Q\r%\u0003\u0015\u0005#HO]5ckR,7/\u0001\neK\u001a\fW\u000f\u001c;BiR\u0014\u0018NY;uKN\u0004\u0013a\u00033jgB\fGo\u00195feN,\"!!\u001c\u0011\t\u0005=\u0014QO\u0007\u0003\u0003cR1!a\u001dK\u0003!!\u0017n\u001d9bi\u000eD\u0017\u0002BA<\u0003c\u00121\u0002R5ta\u0006$8\r[3sg\u0006aA-[:qCR\u001c\u0007.\u001a:tA\u0005Q1/\u001e9feZL7o\u001c:\u0016\u0005\u0005}\u0004\u0003BA\"\u0003\u0003KA!a!\u0002F\tA\u0011i\u0019;peJ+g-A\u0006tkB,'O^5t_J\u0004\u0013\u0001\u00045bm\u0016\u001c\u0006.\u001e;E_^tWCAAF!\u0011\ti)a'\u000e\u0005\u0005=%\u0002BAI\u0003'\u000ba!\u0019;p[&\u001c'\u0002BAK\u0003/\u000b!bY8oGV\u0014(/\u001a8u\u0015\r\tIJW\u0001\u0005kRLG.\u0003\u0003\u0002\u001e\u0006=%!D!u_6L7MQ8pY\u0016\fg.A\u0007iCZ,7\u000b[;u\t><h\u000eI\u0001\nM2|wOT1nKN,\"!!*\u0011\u00075\u000b9+C\u0002\u0002*\u001a\u0013AbU3r\u0003\u000e$xN\u001d(b[\u0016\f!B\u001a7po:\u000bW.Z:!)A\ti\"a,\u00022\u0006M\u0016QWA\\\u0003s\u000bY\fC\u0004\u0002>y\u0001\r!!\u0011\t\u000f\u0005=c\u00041\u0001\u0002T!9\u0011Q\f\u0010A\u0002\u0005\u0005\u0004bBA5=\u0001\u0007\u0011Q\u000e\u0005\b\u0003wr\u0002\u0019AA@\u0011\u001d\t9I\ba\u0001\u0003\u0017Cq!!)\u001f\u0001\u0004\t)+A\u0004`Y><w-\u001a:\u0016\u0005\u0005\u0005\u0007\u0003BAb\u0003\u0013l!!!2\u000b\u0007\u0005\u001d'*A\u0003fm\u0016tG/\u0003\u0003\u0002L\u0006\u0015'A\u0004'pO\u001eLgnZ!eCB$XM]\u0001\t?2|wmZ3sA\u00051An\\4hKJ\faCZ;{u&twmV1s]&tw\rR5tC\ndW\rZ\u0001\u0018MVT(0\u001b8h/\u0006\u0014h.\u001b8h\t&\u001c\u0018M\u00197fI\u0002\n\u0001b\u001d5vi\u0012|wO\u001c\u000b\u0003\u00033\u00042!UAn\u0013\r\tiN\u0015\u0002\u0005+:LG/\u0001\u0006jgNCW\u000f\u001e3po:\fab^5uQ:\u000bW.\u001a)sK\u001aL\u0007\u0010\u0006\u0003\u0002\u001e\u0005\u0015\bBBAtM\u0001\u0007\u0011.\u0001\u0003oC6,\u0017AD2sK\u0006$XM\u00127po:\u000bW.\u001a\u000b\u0002S\u0006\u0001R\r_3dkRLwN\\\"p]R,\u0007\u0010^\u000b\u0003\u0003c\u0004B!a=\u0002x6\u0011\u0011Q\u001f\u0006\u0004\u0003+\u0013\u0016\u0002BA}\u0003k\u0014\u0001$\u0012=fGV$\u0018n\u001c8D_:$X\r\u001f;Fq\u0016\u001cW\u000f^8s\u0003Y\u00198\r[3ek2,w+\u001b;i\r&DX\r\u001a#fY\u0006LH\u0003CA\u0000\u0005\u000b\u0011)B!\u0007\u0011\t\u0005\r#\u0011A\u0005\u0005\u0005\u0007\t)EA\u0006DC:\u001cW\r\u001c7bE2,\u0007b\u0002B\u0004S\u0001\u0007!\u0011B\u0001\rS:LG/[1m\t\u0016d\u0017-\u001f\t\u0005\u0005\u0017\u0011\t\"\u0004\u0002\u0003\u000e)!!qBA{\u0003!!WO]1uS>t\u0017\u0002\u0002B\n\u0005\u001b\u0011aBR5oSR,G)\u001e:bi&|g\u000eC\u0004\u0003\u0018%\u0002\rA!\u0003\u0002\u000b\u0011,G.Y=\t\u000f\tm\u0011\u00061\u0001\u0003\u001e\u0005!A/Y:l!\u0011\u0011yB!\n\u000e\u0005\t\u0005\"b\u0001B\u00125\u0006!A.\u00198h\u0013\u0011\u00119C!\t\u0003\u0011I+hN\\1cY\u0016\f1c]2iK\u0012,H.Z!u\r&DX\r\u001a*bi\u0016$\u0002\"a@\u0003.\t=\"1\u0007\u0005\b\u0005\u000fQ\u0003\u0019\u0001B\u0005\u0011\u001d\u0011\tD\u000ba\u0001\u0005\u0013\t\u0001\"\u001b8uKJ4\u0018\r\u001c\u0005\b\u00057Q\u0003\u0019\u0001B\u000f\u0003Q\u00198\r[3ek2,\u0007+\u001a:j_\u0012L7-\u00197msRA\u0011q B\u001d\u0005w\u0011i\u0004C\u0004\u0003\b-\u0002\rA!\u0003\t\u000f\tE2\u00061\u0001\u0003\n!9!1D\u0016A\u0002\tu\u0011\u0001D:dQ\u0016$W\u000f\\3P]\u000e,GCBA\u0000\u0005\u0007\u0012)\u0005C\u0004\u0003\u00181\u0002\rA!\u0003\t\u000f\tmA\u00061\u0001\u0003\u001e\u0005YQ.\u0019;fe&\fG.\u001b>f+\u0011\u0011YE!\u0015\u0015\t\t5#Q\f\t\u0005\u0005\u001f\u0012\t\u0006\u0004\u0001\u0005\u000f\tMSF1\u0001\u0003V\t\u0019Q*\u0019;\u0012\u0007\t]#\u0010E\u0002R\u00053J1Aa\u0017S\u0005\u001dqu\u000e\u001e5j]\u001eDqAa\u0018.\u0001\u0004\u0011\t'\u0001\b`eVtg.\u00192mK\u001e\u0013\u0018\r\u001d5\u0011\u0011\u0005U#1\rB4\u0005\u001bJ1A!\u001aI\u0005\u00159%/\u00199i!\u0011\t)F!\u001b\n\u0007\t-\u0004JA\u0006DY>\u001cX\rZ*iCB,W\u0003\u0002B8\u0005g\"bA!\u001d\u0003v\te\u0004\u0003\u0002B(\u0005g\"qAa\u0015/\u0005\u0004\u0011)\u0006C\u0004\u0003`9\u0002\rAa\u001e\u0011\u0011\u0005U#1\rB4\u0005cBq!!\u0018/\u0001\u0004\t\t\u0007K\u0002/\u0005{\u0002BAa \u0003\u00066\u0011!\u0011\u0011\u0006\u0004\u0005\u0007S\u0015AC1o]>$\u0018\r^5p]&!!q\u0011BA\u0005EIe\u000e^3s]\u0006d7\u000b^1cY\u0016\f\u0005/[\u000b\u0005\u0005\u0017\u0013y\t\u0006\u0006\u0003\u000e\nE%q\u0013BM\u0005;\u0003BAa\u0014\u0003\u0010\u00129!1K\u0018C\u0002\tU\u0003b\u0002BJ_\u0001\u0007!QS\u0001\u0006OJ\f\u0007\u000f\u001b\t\t\u0003+\u0012\u0019Ga\u001a\u0003\u000e\"9\u0011QL\u0018A\u0002\u0005\u0005\u0004B\u0002BN_\u0001\u0007q/\u0001\u0007eK\u001a\fW\u000f\u001c;QQ\u0006\u001cX\rC\u0004\u0003 >\u0002\r!!\u0001\u0002\rAD\u0017m]3t\u0003)9\u0018N]3J]2,Go\u001d\u000b\t\u00033\u0014)Ka,\u0003H\"9!q\u0015\u0019A\u0002\t%\u0016AD5tY\u0006tG\r\u0016:bG.Lgn\u001a\t\u0004\u001b\n-\u0016b\u0001BW\r\nq\u0011j\u001d7b]\u0012$&/Y2lS:<\u0007b\u0002BYa\u0001\u0007!1W\u0001\u0004[>$\u0007c\u0002B[\u0005w\u0013\tM\u001f\b\u0004\u001b\n]\u0016b\u0001B]\r\u0006a1\u000b\u001e:fC6d\u0015-_8vi&!!Q\u0018B`\u00051\tEo\\7jG6{G-\u001e7f\u0015\r\u0011IL\u0012\t\u0005\u0003+\u0012\u0019-C\u0002\u0003F\"\u0013Qa\u00155ba\u0016DaA!31\u0001\u0004Q\u0018!\u00027pO&\u001c\u0017aC<je\u0016|U\u000f\u001e7fiN$B\"!7\u0003P\nE'1\u001bBk\u0005?DqAa*2\u0001\u0004\u0011I\u000bC\u0004\u00032F\u0002\rAa-\t\r\t%\u0017\u00071\u0001{\u0011\u001d\u00119.\ra\u0001\u00053\f\u0011c\u001d;bO\u0016<En\u001c2bY>3gm]3u!\r\t&1\\\u0005\u0004\u0005;\u0014&aA%oi\"9!\u0011]\u0019A\u0002\t\r\u0018!C8viR{7\u000b\\8u!\u0015\t&Q\u001dBm\u0013\r\u00119O\u0015\u0002\u0006\u0003J\u0014\u0018-_\u0001\u000b[\u0006\\W\rT8hO\u0016\u0014H\u0003BAa\u0005[DqAa<3\u0001\u0004\u0011\t0A\u0005m_\u001e\u001cv.\u001e:dKB!!Na={\u0013\r\u0011)p\u001d\u0002\u0006\u00072\f7o]\u0001\bC\u000e$xN](g)\u0019\tyHa?\u0004\u0006!9!Q`\u001aA\u0002\t}\u0018aB2p]R,\u0007\u0010\u001e\t\u0005\u0003+\u001a\t!C\u0002\u0004\u0004!\u0013a#T1uKJL\u0017\r\\5{CRLwN\\\"p]R,\u0007\u0010\u001e\u0005\b\u0007\u000f\u0019\u0004\u0019AB\u0005\u0003\u0015\u0001(o\u001c9t!\u0011\t\u0019ea\u0003\n\t\r5\u0011Q\t\u0002\u0006!J|\u0007o\u001d\u0015\u0004g\rE\u0001\u0003\u0002B@\u0007'IAa!\u0006\u0003\u0002\nY\u0011J\u001c;fe:\fG.\u00119j\u0003\u0011\u0019w\u000e]=\u0015!\u0005u11DB\u000f\u0007?\u0019\tca\t\u0004&\r\u001d\u0002\"CA\u001fiA\u0005\t\u0019AA!\u0011%\ty\u0005\u000eI\u0001\u0002\u0004\t\u0019\u0006C\u0005\u0002^Q\u0002\n\u00111\u0001\u0002b!I\u0011\u0011\u000e\u001b\u0011\u0002\u0003\u0007\u0011Q\u000e\u0005\n\u0003w\"\u0004\u0013!a\u0001\u0003\u007fB\u0011\"a\"5!\u0003\u0005\r!a#\t\u0013\u0005\u0005F\u0007%AA\u0002\u0005\u0015\u0016AD2paf$C-\u001a4bk2$H%M\u000b\u0003\u0007[QC!!\u0011\u00040-\u00121\u0011\u0007\t\u0005\u0007g\u0019Y$\u0004\u0002\u00046)!1qGB\u001d\u0003%)hn\u00195fG.,GMC\u0002\u0003\u0004JKAa!\u0010\u00046\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\u001111\t\u0016\u0005\u0003'\u001ay#\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0016\u0005\r%#\u0006BA1\u0007_\tabY8qs\u0012\"WMZ1vYR$C'\u0006\u0002\u0004P)\"\u0011QNB\u0018\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIU*\"a!\u0016+\t\u0005}4qF\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00137+\t\u0019YF\u000b\u0003\u0002\f\u000e=\u0012AD2paf$C-\u001a4bk2$HeN\u000b\u0003\u0007CRC!!*\u00040\u0005i\u0001O]8ek\u000e$\bK]3gSb,\"aa\u001a\u0011\t\t}1\u0011N\u0005\u0004e\n\u0005\u0012\u0001\u00049s_\u0012,8\r^!sSRLXC\u0001Bm\u00039\u0001(o\u001c3vGR,E.Z7f]R$2A_B:\u0011%\u0019)HPA\u0001\u0002\u0004\u0011I.A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0007w\u0002Ra! \u0004\u0000il!!!\u0003\n\t\r\u0005\u0015\u0011\u0002\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000eF\u0002d\u0007\u000fC\u0001b!\u001eA\u0003\u0003\u0005\rA_\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0004h\r5\u0005\"CB;\u0003\u0006\u0005\t\u0019\u0001Bm\u0003!A\u0017m\u001d5D_\u0012,GC\u0001Bm\u0003!!xn\u0015;sS:<GCAB4\u0003\u0019)\u0017/^1mgR\u00191ma'\t\u0011\rUD)!AA\u0002iD3aDB\t\u0011\u001d\u0011ip\u0003a\u0001\u0007C\u0003B!a\u0011\u0004$&!1QUA#\u00051\t5\r^8s\u0007>tG/\u001a=u\u0011\u0019\u0019Ik\u0003a\u0001S\u0006Qa.Y7f!J,g-\u001b=\t\u000f\u0005=3\u00021\u0001\u0002T!91qV\u0006A\u0002\u0005\u0005\u0014AC1uiJL'-\u001e;fgR\u0001\u0012QDBZ\u0007k\u001b9l!/\u0004<\u000eu6q\u0018\u0005\b\u0003{a\u0001\u0019AA!\u0011\u001d\ty\u0005\u0004a\u0001\u0003'Bq!!\u0018\r\u0001\u0004\t\t\u0007C\u0004\u0002j1\u0001\r!!\u001c\t\u000f\u0005mD\u00021\u0001\u0002\u0000!9\u0011q\u0011\u0007A\u0002\u0005-\u0005bBAQ\u0019\u0001\u0007\u0011QU\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\u0019)m!5\u0011\u000bE\u001b9ma3\n\u0007\r%'K\u0001\u0004PaRLwN\u001c\t\u0012#\u000e5\u0017\u0011IA*\u0003C\ni'a \u0002\f\u0006\u0015\u0016bABh%\n1A+\u001e9mK^B\u0011ba5\u000e\u0003\u0003\u0005\r!!\b\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0004ZB!!qDBn\u0013\u0011\u0019iN!\t\u0003\r=\u0013'.Z2uQ\r\t1\u0011\u0003\u0015\u0004\u0001\rE\u0001")
@InternalApi
public class PhasedFusingActorMaterializer
extends ExtendedActorMaterializer
implements Product,
Serializable {
    private ExecutionContextExecutor executionContext;
    private final ActorSystem system;
    private final ActorMaterializerSettings settings;
    private final Attributes defaultAttributes;
    private final Dispatchers dispatchers;
    private final ActorRef supervisor;
    private final AtomicBoolean haveShutDown;
    private final SeqActorName flowNames;
    private final LoggingAdapter _logger;
    private final boolean fuzzingWarningDisabled;
    private volatile boolean bitmap$0;

    public static Option<Tuple7<ActorSystem, ActorMaterializerSettings, Attributes, Dispatchers, ActorRef, AtomicBoolean, SeqActorName>> unapply(PhasedFusingActorMaterializer x$0) {
        return PhasedFusingActorMaterializer$.MODULE$.unapply(x$0);
    }

    public static PhasedFusingActorMaterializer apply(ActorSystem system, ActorMaterializerSettings settings, Attributes defaultAttributes, Dispatchers dispatchers, ActorRef supervisor, AtomicBoolean haveShutDown, SeqActorName flowNames) {
        return PhasedFusingActorMaterializer$.MODULE$.apply(system, settings, defaultAttributes, dispatchers, supervisor, haveShutDown, flowNames);
    }

    public static PhasedFusingActorMaterializer apply(ActorContext context, String namePrefix, ActorMaterializerSettings settings, Attributes attributes) {
        return PhasedFusingActorMaterializer$.MODULE$.apply(context, namePrefix, settings, attributes);
    }

    public static Map<IslandTag, Phase<Object>> DefaultPhases() {
        return PhasedFusingActorMaterializer$.MODULE$.DefaultPhases();
    }

    public static Phase<Object> DefaultPhase() {
        return PhasedFusingActorMaterializer$.MODULE$.DefaultPhase();
    }

    public static String Mailbox() {
        return PhasedFusingActorMaterializer$.MODULE$.Mailbox();
    }

    public static boolean Debug() {
        return PhasedFusingActorMaterializer$.MODULE$.Debug();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public ActorSystem system() {
        return this.system;
    }

    @Override
    public ActorMaterializerSettings settings() {
        return this.settings;
    }

    public Attributes defaultAttributes() {
        return this.defaultAttributes;
    }

    public Dispatchers dispatchers() {
        return this.dispatchers;
    }

    @Override
    public ActorRef supervisor() {
        return this.supervisor;
    }

    public AtomicBoolean haveShutDown() {
        return this.haveShutDown;
    }

    public SeqActorName flowNames() {
        return this.flowNames;
    }

    private LoggingAdapter _logger() {
        return this._logger;
    }

    @Override
    public LoggingAdapter logger() {
        return this._logger();
    }

    private boolean fuzzingWarningDisabled() {
        return this.fuzzingWarningDisabled;
    }

    @Override
    public void shutdown() {
        if (this.haveShutDown().compareAndSet(false, true)) {
            PoisonPill$ x$1 = PoisonPill$.MODULE$;
            ActorRef x$2 = this.supervisor().$bang$default$2(x$1);
            this.supervisor().$bang(x$1, x$2);
            return;
        }
    }

    @Override
    public boolean isShutdown() {
        return this.haveShutDown().get();
    }

    @Override
    public PhasedFusingActorMaterializer withNamePrefix(String name) {
        SeqActorName x$1 = this.flowNames().copy(name);
        ActorSystem x$2 = this.copy$default$1();
        ActorMaterializerSettings x$3 = this.copy$default$2();
        Attributes x$4 = this.copy$default$3();
        Dispatchers x$5 = this.copy$default$4();
        ActorRef x$6 = this.copy$default$5();
        AtomicBoolean x$7 = this.copy$default$6();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$1);
    }

    private String createFlowName() {
        return this.flowNames().next();
    }

    private ExecutionContextExecutor executionContext$lzycompute() {
        PhasedFusingActorMaterializer phasedFusingActorMaterializer = this;
        synchronized (phasedFusingActorMaterializer) {
            if (!this.bitmap$0) {
                this.executionContext = this.dispatchers().lookup(((ActorAttributes.Dispatcher)this.defaultAttributes().mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.Dispatcher.class))).dispatcher());
                this.bitmap$0 = true;
            }
        }
        return this.executionContext;
    }

    @Override
    public ExecutionContextExecutor executionContext() {
        if (!this.bitmap$0) {
            return this.executionContext$lzycompute();
        }
        return this.executionContext;
    }

    @Override
    public Cancellable scheduleWithFixedDelay(FiniteDuration initialDelay, FiniteDuration delay, Runnable task) {
        return this.system().scheduler().scheduleWithFixedDelay(initialDelay, delay, task, (ExecutionContext)this.executionContext());
    }

    @Override
    public Cancellable scheduleAtFixedRate(FiniteDuration initialDelay, FiniteDuration interval, Runnable task) {
        return this.system().scheduler().scheduleAtFixedRate(initialDelay, interval, task, (ExecutionContext)this.executionContext());
    }

    @Override
    public Cancellable schedulePeriodically(FiniteDuration initialDelay, FiniteDuration interval, Runnable task) {
        return this.system().scheduler().scheduleAtFixedRate(initialDelay, interval, task, (ExecutionContext)this.executionContext());
    }

    @Override
    public Cancellable scheduleOnce(FiniteDuration delay, Runnable task) {
        return this.system().scheduler().scheduleOnce(delay, task, (ExecutionContext)this.executionContext());
    }

    @Override
    public <Mat> Mat materialize(Graph<ClosedShape, Mat> _runnableGraph) {
        return this.materialize(_runnableGraph, this.defaultAttributes());
    }

    @Override
    @InternalStableApi
    public <Mat> Mat materialize(Graph<ClosedShape, Mat> _runnableGraph, Attributes defaultAttributes) {
        return this.materialize(_runnableGraph, defaultAttributes, PhasedFusingActorMaterializer$.MODULE$.DefaultPhase(), PhasedFusingActorMaterializer$.MODULE$.DefaultPhases());
    }

    @Override
    public <Mat> Mat materialize(Graph<ClosedShape, Mat> graph, Attributes defaultAttributes, Phase<Object> defaultPhase, Map<IslandTag, Phase<Object>> phases) {
        Object e;
        if (this.isShutdown()) {
            throw new IllegalStateException("Trying to materialize stream after materializer has been shutdown");
        }
        Attributes defaultAndGraphAttributes = defaultAttributes.and(graph.traversalBuilder().attributes());
        if (((ActorAttributes.FuzzingMode)defaultAndGraphAttributes.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.FuzzingMode.class))).enabled() && !this.fuzzingWarningDisabled()) {
            this._logger().warning("Fuzzing mode is enabled on this system. If you see this warning on your production system then set 'akka.stream.materializer.debug.fuzzing-mode' to off.");
        }
        IslandTracking islandTracking = new IslandTracking(phases, this.settings(), defaultAndGraphAttributes, defaultPhase, this, new StringBuilder(1).append(this.createFlowName()).append("-").toString());
        Traversal current = graph.traversalBuilder().traversal();
        ArrayDeque<Attributes> attributesStack = new ArrayDeque<Attributes>(8);
        attributesStack.addLast(defaultAndGraphAttributes);
        ArrayDeque<Traversal> traversalStack = new ArrayDeque<Traversal>(16);
        traversalStack.addLast(current);
        ArrayDeque<Object> matValueStack = new ArrayDeque<Object>(8);
        if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
            Predef$.MODULE$.println("--- Materializing layout:");
            TraversalBuilder$.MODULE$.printTraversal(current, TraversalBuilder$.MODULE$.printTraversal$default$2());
            Predef$.MODULE$.println("--- Start materialization");
        }
        while (!traversalStack.isEmpty()) {
            current = (Traversal)traversalStack.removeLast();
            while (current != EmptyTraversal$.MODULE$) {
                Traversal nextStep = EmptyTraversal$.MODULE$;
                Traversal traversal = current;
                if (traversal instanceof MaterializeAtomic) {
                    MaterializeAtomic materializeAtomic = (MaterializeAtomic)traversal;
                    StreamLayout.AtomicModule<Shape, Object> mod = materializeAtomic.module();
                    int[] outToSlot = materializeAtomic.outToSlots();
                    if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                        Predef$.MODULE$.println(new StringBuilder(22).append("materializing module: ").append(mod).toString());
                    }
                    Tuple2<Object, Object> matAndStage = islandTracking.getCurrentPhase().materializeAtomic(mod, (Attributes)attributesStack.getLast());
                    Object logic = matAndStage._1();
                    Object matValue = matAndStage._2();
                    if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                        Predef$.MODULE$.println(new StringBuilder(24).append("  materialized value is ").append(matValue).toString());
                    }
                    matValueStack.addLast(matValue);
                    int stageGlobalOffset = islandTracking.getCurrentOffset();
                    this.wireInlets(islandTracking, mod, logic);
                    this.wireOutlets(islandTracking, mod, logic, stageGlobalOffset, outToSlot);
                    if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                        Predef$.MODULE$.println(new StringBuilder(10).append("PUSH: ").append(matValue).append(" => ").append(matValueStack).toString());
                    }
                } else if (traversal instanceof Concat) {
                    Concat concat = (Concat)traversal;
                    Traversal first = concat.first();
                    Traversal next = concat.next();
                    Serializable serializable = next != EmptyTraversal$.MODULE$ ? BoxesRunTime.boxToBoolean(traversalStack.add(next)) : BoxedUnit.UNIT;
                    nextStep = first;
                } else if (Pop$.MODULE$.equals(traversal)) {
                    Object popped = matValueStack.removeLast();
                    if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                        Predef$.MODULE$.println(new StringBuilder(9).append("POP: ").append(popped).append(" => ").append(matValueStack).toString());
                    }
                } else if (PushNotUsed$.MODULE$.equals(traversal)) {
                    matValueStack.addLast(NotUsed$.MODULE$);
                    if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                        Predef$.MODULE$.println(new StringBuilder(17).append("PUSH: NotUsed => ").append(matValueStack).toString());
                    }
                } else if (traversal instanceof Transform) {
                    Transform transform = (Transform)traversal;
                    Object prev = matValueStack.removeLast();
                    Object result = transform.apply(prev);
                    matValueStack.addLast(result);
                    if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                        Predef$.MODULE$.println(new StringBuilder(6).append("TRFM: ").append(matValueStack).toString());
                    }
                } else if (traversal instanceof Compose) {
                    Compose compose = (Compose)traversal;
                    Object second = matValueStack.removeLast();
                    Object first = matValueStack.removeLast();
                    Object result = compose.apply(first, second);
                    matValueStack.addLast(result);
                    if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                        Predef$.MODULE$.println(new StringBuilder(6).append("COMP: ").append(matValueStack).toString());
                    }
                } else if (traversal instanceof PushAttributes) {
                    PushAttributes pushAttributes = (PushAttributes)traversal;
                    Attributes attr = pushAttributes.attributes();
                    attributesStack.addLast(((Attributes)attributesStack.getLast()).and(attr));
                    if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                        Predef$.MODULE$.println(new StringBuilder(11).append("ATTR PUSH: ").append(attr).toString());
                    }
                } else if (PopAttributes$.MODULE$.equals(traversal)) {
                    attributesStack.removeLast();
                    if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                        Predef$.MODULE$.println("ATTR POP");
                    }
                } else if (traversal instanceof EnterIsland) {
                    EnterIsland enterIsland = (EnterIsland)traversal;
                    IslandTag tag = enterIsland.islandTag();
                    islandTracking.enterIsland(tag, (Attributes)attributesStack.getLast());
                } else if (ExitIsland$.MODULE$.equals(traversal)) {
                    islandTracking.exitIsland();
                }
                current = nextStep;
            }
        }
        try {
            islandTracking.getCurrentPhase().onIslandReady();
            islandTracking.allNestedIslandsReady();
            if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                Predef$.MODULE$.println("--- Finished materialization");
            }
            e = matValueStack.peekLast();
        }
        finally {
            if (this.isShutdown()) {
                throw PhasedFusingActorMaterializer.shutdownWhileMaterializingFailure$1();
            }
        }
        return (Mat)e;
    }

    private void wireInlets(IslandTracking islandTracking, StreamLayout.AtomicModule<Shape, Object> mod, Object logic) {
        Seq<Inlet<?>> inlets = ((Shape)mod.shape()).inlets();
        if (inlets.nonEmpty()) {
            if (Shape$.MODULE$.hasOnePort(inlets)) {
                islandTracking.wireIn((InPort)inlets.head(), logic);
                return;
            }
            Iterator ins = inlets.iterator();
            while (ins.hasNext()) {
                Inlet in = (Inlet)ins.next();
                islandTracking.wireIn(in, logic);
            }
            return;
        }
    }

    private void wireOutlets(IslandTracking islandTracking, StreamLayout.AtomicModule<Shape, Object> mod, Object logic, int stageGlobalOffset, int[] outToSlot) {
        Seq<Outlet<?>> outlets = ((Shape)mod.shape()).outlets();
        if (outlets.nonEmpty()) {
            if (Shape$.MODULE$.hasOnePort(outlets)) {
                Outlet out = (Outlet)outlets.head();
                int absoluteTargetSlot = stageGlobalOffset + outToSlot[out.id()];
                if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                    Predef$.MODULE$.println(new StringBuilder(17).append("  wiring offset: ").append(Predef$.MODULE$.wrapIntArray(outToSlot).mkString("[", ",", "]")).toString());
                }
                islandTracking.wireOut(out, absoluteTargetSlot, logic);
                return;
            }
            Iterator outs = outlets.iterator();
            while (outs.hasNext()) {
                Outlet out = (Outlet)outs.next();
                int absoluteTargetSlot = stageGlobalOffset + outToSlot[out.id()];
                if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                    Predef$.MODULE$.println(new StringBuilder(17).append("  wiring offset: ").append(Predef$.MODULE$.wrapIntArray(outToSlot).mkString("[", ",", "]")).toString());
                }
                islandTracking.wireOut(out, absoluteTargetSlot, logic);
            }
            return;
        }
    }

    @Override
    public LoggingAdapter makeLogger(Class<Object> logSource) {
        return Logging$.MODULE$.apply(this.system(), logSource, LogSource$.MODULE$.fromAnyClass());
    }

    @Override
    @InternalApi
    public ActorRef actorOf(MaterializationContext context, Props props) {
        Props props2;
        block3: {
            String string = props.dispatcher();
            switch (string == null ? 0 : string.hashCode()) {
                case 759061882: {
                    if (!"akka.actor.default-dispatcher".equals(string)) break;
                    props2 = props.withDispatcher(((ActorAttributes.Dispatcher)context.effectiveAttributes().mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.Dispatcher.class))).dispatcher()).withMailbox(PhasedFusingActorMaterializer$.MODULE$.Mailbox());
                    break block3;
                }
            }
            props2 = props;
        }
        Props effectiveProps = props2;
        return this.actorOf(effectiveProps, context.islandName());
    }

    public PhasedFusingActorMaterializer copy(ActorSystem system, ActorMaterializerSettings settings, Attributes defaultAttributes, Dispatchers dispatchers, ActorRef supervisor, AtomicBoolean haveShutDown, SeqActorName flowNames) {
        return new PhasedFusingActorMaterializer(system, settings, defaultAttributes, dispatchers, supervisor, haveShutDown, flowNames);
    }

    public ActorSystem copy$default$1() {
        return this.system();
    }

    public ActorMaterializerSettings copy$default$2() {
        return this.settings();
    }

    public Attributes copy$default$3() {
        return this.defaultAttributes();
    }

    public Dispatchers copy$default$4() {
        return this.dispatchers();
    }

    public ActorRef copy$default$5() {
        return this.supervisor();
    }

    public AtomicBoolean copy$default$6() {
        return this.haveShutDown();
    }

    public SeqActorName copy$default$7() {
        return this.flowNames();
    }

    @Override
    public String productPrefix() {
        return "PhasedFusingActorMaterializer";
    }

    @Override
    public int productArity() {
        return 7;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.system();
            }
            case 1: {
                return this.settings();
            }
            case 2: {
                return this.defaultAttributes();
            }
            case 3: {
                return this.dispatchers();
            }
            case 4: {
                return this.supervisor();
            }
            case 5: {
                return this.haveShutDown();
            }
            case 6: {
                return this.flowNames();
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
        return x$1 instanceof PhasedFusingActorMaterializer;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "system";
            }
            case 1: {
                return "settings";
            }
            case 2: {
                return "defaultAttributes";
            }
            case 3: {
                return "dispatchers";
            }
            case 4: {
                return "supervisor";
            }
            case 5: {
                return "haveShutDown";
            }
            case 6: {
                return "flowNames";
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
        if (!(object instanceof PhasedFusingActorMaterializer)) return false;
        boolean bl = true;
        if (!bl) return false;
        PhasedFusingActorMaterializer phasedFusingActorMaterializer = (PhasedFusingActorMaterializer)x$1;
        ActorSystem actorSystem = this.system();
        ActorSystem actorSystem2 = phasedFusingActorMaterializer.system();
        if (actorSystem == null) {
            if (actorSystem2 != null) {
                return false;
            }
        } else if (!actorSystem.equals(actorSystem2)) return false;
        ActorMaterializerSettings actorMaterializerSettings = this.settings();
        ActorMaterializerSettings actorMaterializerSettings2 = phasedFusingActorMaterializer.settings();
        if (actorMaterializerSettings == null) {
            if (actorMaterializerSettings2 != null) {
                return false;
            }
        } else if (!((Object)actorMaterializerSettings).equals(actorMaterializerSettings2)) return false;
        Attributes attributes = this.defaultAttributes();
        Attributes attributes2 = phasedFusingActorMaterializer.defaultAttributes();
        if (attributes == null) {
            if (attributes2 != null) {
                return false;
            }
        } else if (!((Object)attributes).equals(attributes2)) return false;
        Dispatchers dispatchers = this.dispatchers();
        Dispatchers dispatchers2 = phasedFusingActorMaterializer.dispatchers();
        if (dispatchers == null) {
            if (dispatchers2 != null) {
                return false;
            }
        } else if (!dispatchers.equals(dispatchers2)) return false;
        ActorRef actorRef2 = this.supervisor();
        ActorRef actorRef3 = phasedFusingActorMaterializer.supervisor();
        if (actorRef2 == null) {
            if (actorRef3 != null) {
                return false;
            }
        } else if (!((Object)actorRef2).equals(actorRef3)) return false;
        AtomicBoolean atomicBoolean = this.haveShutDown();
        AtomicBoolean atomicBoolean2 = phasedFusingActorMaterializer.haveShutDown();
        if (atomicBoolean == null) {
            if (atomicBoolean2 != null) {
                return false;
            }
        } else if (!atomicBoolean.equals(atomicBoolean2)) return false;
        SeqActorName seqActorName = this.flowNames();
        SeqActorName seqActorName2 = phasedFusingActorMaterializer.flowNames();
        if (seqActorName == null) {
            if (seqActorName2 != null) {
                return false;
            }
        } else if (!seqActorName.equals(seqActorName2)) return false;
        if (!phasedFusingActorMaterializer.canEqual(this)) return false;
        return true;
    }

    private static final IllegalStateException shutdownWhileMaterializingFailure$1() {
        return new IllegalStateException("Materializer shutdown while materializing stream");
    }

    public PhasedFusingActorMaterializer(ActorSystem system, ActorMaterializerSettings settings, Attributes defaultAttributes, Dispatchers dispatchers, ActorRef supervisor, AtomicBoolean haveShutDown, SeqActorName flowNames) {
        this.system = system;
        this.settings = settings;
        this.defaultAttributes = defaultAttributes;
        this.dispatchers = dispatchers;
        this.supervisor = supervisor;
        this.haveShutDown = haveShutDown;
        this.flowNames = flowNames;
        Product.$init$(this);
        this._logger = Logging$.MODULE$.getLogger(system, (Object)this);
        this.fuzzingWarningDisabled = system.settings().config().hasPath("akka.stream.secret-test-fuzzing-warning-disable");
    }
}

