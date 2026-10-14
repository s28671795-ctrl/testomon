/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.actor.NoSerializationVerificationNeeded;
import akka.actor.Props$;
import akka.actor.Stash;
import akka.actor.StashSupport;
import akka.actor.Status;
import akka.actor.SupervisorStrategy;
import akka.actor.UnrestrictedStash;
import akka.annotation.InternalApi;
import akka.annotation.InternalStableApi;
import akka.dispatch.DequeBasedMessageQueueSemantics;
import akka.dispatch.Envelope;
import akka.event.LoggingAdapter;
import akka.io.IO$;
import akka.io.Tcp$;
import akka.io.Udp;
import akka.io.Udp$;
import akka.io.Udp$Bind$;
import akka.io.Udp$Send$;
import akka.io.Udp$Unbind$;
import akka.io.Udp$Unbound$;
import akka.io.dns.RecordClass$;
import akka.io.dns.RecordType;
import akka.io.dns.RecordType$;
import akka.io.dns.ResourceRecord;
import akka.io.dns.internal.DnsClient$;
import akka.io.dns.internal.Message;
import akka.io.dns.internal.Message$;
import akka.io.dns.internal.MessageFlags$;
import akka.io.dns.internal.Question;
import akka.io.dns.internal.ResponseCode$;
import akka.io.dns.internal.TcpDnsClient;
import akka.pattern.BackoffOpts$;
import akka.pattern.BackoffSupervisor$;
import akka.util.ByteString;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import scala.Array$;
import scala.Enumeration;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Vector;
import scala.concurrent.duration.package;
import scala.concurrent.duration.package$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Try$;

@ScalaSignature(bytes="\u0006\u0005\u0011\rrACA\u0015\u0003WA\t!a\u000e\u0002<\u0019Q\u0011qHA\u0016\u0011\u0003\t9$!\u0011\t\u000f\u0005=\u0013\u0001\"\u0001\u0002T\u0019I\u0011QK\u0001\u0011\u0002G\u0005\u0012q\u000b\u0005\b\u00033\u001aa\u0011AA.\r\u0019\u0011i%\u0001\"\u0003P!Q\u0011\u0011L\u0003\u0003\u0016\u0004%\t!a\u0017\t\u0015\u0005-UA!E!\u0002\u0013\ti\u0006\u0003\u0006\u0002\u000e\u0016\u0011)\u001a!C\u0001\u0003\u001fC!\"!)\u0006\u0005#\u0005\u000b\u0011BAI\u0011\u001d\ty%\u0002C\u0001\u0005#B\u0011\"a+\u0006\u0003\u0003%\tA!\u0017\t\u0013\u0005MV!%A\u0005\u0002\u0005U\u0006\"CAf\u000bE\u0005I\u0011AAg\u0011%\t\t.BA\u0001\n\u0003\n\u0019\u000eC\u0005\u0002d\u0016\t\t\u0011\"\u0001\u0002f\"I\u0011Q^\u0003\u0002\u0002\u0013\u0005!q\f\u0005\n\u0003w,\u0011\u0011!C!\u0003{D\u0011Ba\u0003\u0006\u0003\u0003%\tAa\u0019\t\u0013\t]Q!!A\u0005B\t\u001d\u0004\"\u0003B\u000f\u000b\u0005\u0005I\u0011\tB\u0010\u0011%\u0011\t#BA\u0001\n\u0003\u0012\u0019\u0003C\u0005\u0003&\u0015\t\t\u0011\"\u0011\u0003l\u001dI!qN\u0001\u0002\u0002#\u0005!\u0011\u000f\u0004\n\u0005\u001b\n\u0011\u0011!E\u0001\u0005gBq!a\u0014\u0019\t\u0003\u0011I\tC\u0005\u0003\"a\t\t\u0011\"\u0012\u0003$!I!1\u0012\r\u0002\u0002\u0013\u0005%Q\u0012\u0005\n\u0005'C\u0012\u0011!CA\u0005+C\u0011Ba*\u0019\u0003\u0003%IA!+\u0007\r\u0005\u0015\u0014AQA4\u0011)\tIF\bBK\u0002\u0013\u0005\u00111\f\u0005\u000b\u0003\u0017s\"\u0011#Q\u0001\n\u0005u\u0003BCAG=\tU\r\u0011\"\u0001\u0002\u0010\"Q\u0011\u0011\u0015\u0010\u0003\u0012\u0003\u0006I!!%\t\u000f\u0005=c\u0004\"\u0001\u0002$\"I\u00111\u0016\u0010\u0002\u0002\u0013\u0005\u0011Q\u0016\u0005\n\u0003gs\u0012\u0013!C\u0001\u0003kC\u0011\"a3\u001f#\u0003%\t!!4\t\u0013\u0005Eg$!A\u0005B\u0005M\u0007\"CAr=\u0005\u0005I\u0011AAs\u0011%\tiOHA\u0001\n\u0003\ty\u000fC\u0005\u0002|z\t\t\u0011\"\u0011\u0002~\"I!1\u0002\u0010\u0002\u0002\u0013\u0005!Q\u0002\u0005\n\u0005/q\u0012\u0011!C!\u00053A\u0011B!\b\u001f\u0003\u0003%\tEa\b\t\u0013\t\u0005b$!A\u0005B\t\r\u0002\"\u0003B\u0013=\u0005\u0005I\u0011\tB\u0014\u000f%\u0011\t,AA\u0001\u0012\u0003\u0011\u0019LB\u0005\u0002f\u0005\t\t\u0011#\u0001\u00036\"9\u0011qJ\u0019\u0005\u0002\te\u0006\"\u0003B\u0011c\u0005\u0005IQ\tB\u0012\u0011%\u0011Y)MA\u0001\n\u0003\u0013Y\fC\u0005\u0003\u0014F\n\t\u0011\"!\u0003B\"I!qU\u0019\u0002\u0002\u0013%!\u0011\u0016\u0004\u0007\u0005W\t!I!\f\t\u0015\u0005esG!f\u0001\n\u0003\tY\u0006\u0003\u0006\u0002\f^\u0012\t\u0012)A\u0005\u0003;B!\"!$8\u0005+\u0007I\u0011AAH\u0011)\t\tk\u000eB\tB\u0003%\u0011\u0011\u0013\u0005\b\u0003\u001f:D\u0011\u0001B\u0018\u0011%\tYkNA\u0001\n\u0003\u00119\u0004C\u0005\u00024^\n\n\u0011\"\u0001\u00026\"I\u00111Z\u001c\u0012\u0002\u0013\u0005\u0011Q\u001a\u0005\n\u0003#<\u0014\u0011!C!\u0003'D\u0011\"a98\u0003\u0003%\t!!:\t\u0013\u00055x'!A\u0005\u0002\tu\u0002\"CA~o\u0005\u0005I\u0011IA\u007f\u0011%\u0011YaNA\u0001\n\u0003\u0011\t\u0005C\u0005\u0003\u0018]\n\t\u0011\"\u0011\u0003F!I!QD\u001c\u0002\u0002\u0013\u0005#q\u0004\u0005\n\u0005C9\u0014\u0011!C!\u0005GA\u0011B!\n8\u0003\u0003%\tE!\u0013\b\u0013\t\u0015\u0017!!A\t\u0002\t\u001dg!\u0003B\u0016\u0003\u0005\u0005\t\u0012\u0001Be\u0011\u001d\tyE\u0013C\u0001\u0005\u001bD\u0011B!\tK\u0003\u0003%)Ea\t\t\u0013\t-%*!A\u0005\u0002\n=\u0007\"\u0003BJ\u0015\u0006\u0005I\u0011\u0011Bk\u0011%\u00119KSA\u0001\n\u0013\u0011IK\u0002\u0004\u0003Z\u0006\u0011%1\u001c\u0005\u000b\u00033\u0002&Q3A\u0005\u0002\u0005m\u0003BCAF!\nE\t\u0015!\u0003\u0002^!Q!\u0011\u001e)\u0003\u0016\u0004%\tAa;\t\u0015\r\u0005\u0001K!E!\u0002\u0013\u0011i\u000f\u0003\u0006\u0004\u0004A\u0013)\u001a!C\u0001\u0005WD!b!\u0002Q\u0005#\u0005\u000b\u0011\u0002Bw\u0011\u001d\ty\u0005\u0015C\u0001\u0007\u000fA\u0011\"a+Q\u0003\u0003%\ta!\u0005\t\u0013\u0005M\u0006+%A\u0005\u0002\u0005U\u0006\"CAf!F\u0005I\u0011AB\r\u0011%\u0019i\u0002UI\u0001\n\u0003\u0019I\u0002C\u0005\u0002RB\u000b\t\u0011\"\u0011\u0002T\"I\u00111\u001d)\u0002\u0002\u0013\u0005\u0011Q\u001d\u0005\n\u0003[\u0004\u0016\u0011!C\u0001\u0007?A\u0011\"a?Q\u0003\u0003%\t%!@\t\u0013\t-\u0001+!A\u0005\u0002\r\r\u0002\"\u0003B\f!\u0006\u0005I\u0011IB\u0014\u0011%\u0011i\u0002UA\u0001\n\u0003\u0012y\u0002C\u0005\u0003\"A\u000b\t\u0011\"\u0011\u0003$!I!Q\u0005)\u0002\u0002\u0013\u000531F\u0004\n\u0007_\t\u0011\u0011!E\u0001\u0007c1\u0011B!7\u0002\u0003\u0003E\taa\r\t\u000f\u0005=c\r\"\u0001\u0004<!I!\u0011\u00054\u0002\u0002\u0013\u0015#1\u0005\u0005\n\u0005\u00173\u0017\u0011!CA\u0007{A\u0011b!\u0012g#\u0003%\ta!\u0007\t\u0013\tMe-!A\u0005\u0002\u000e\u001d\u0003\"CB*MF\u0005I\u0011AB\r\u0011%\u00119KZA\u0001\n\u0013\u0011IK\u0002\u0004\u0004V\u0005\u00115q\u000b\u0005\u000b\u00033r'Q3A\u0005\u0002\u0005m\u0003BCAF]\nE\t\u0015!\u0003\u0002^!9\u0011q\n8\u0005\u0002\re\u0003\"CAV]\u0006\u0005I\u0011AB0\u0011%\t\u0019L\\I\u0001\n\u0003\t)\fC\u0005\u0002R:\f\t\u0011\"\u0011\u0002T\"I\u00111\u001d8\u0002\u0002\u0013\u0005\u0011Q\u001d\u0005\n\u0003[t\u0017\u0011!C\u0001\u0007GB\u0011\"a?o\u0003\u0003%\t%!@\t\u0013\t-a.!A\u0005\u0002\r\u001d\u0004\"\u0003B\f]\u0006\u0005I\u0011IB6\u0011%\u0011iB\\A\u0001\n\u0003\u0012y\u0002C\u0005\u0003\"9\f\t\u0011\"\u0011\u0003$!I!Q\u00058\u0002\u0002\u0013\u00053qN\u0004\n\u0007g\n\u0011\u0011!E\u0001\u0007k2\u0011b!\u0016\u0002\u0003\u0003E\taa\u001e\t\u000f\u0005=c\u0010\"\u0001\u0004\u0000!I!\u0011\u0005@\u0002\u0002\u0013\u0015#1\u0005\u0005\n\u0005\u0017s\u0018\u0011!CA\u0007\u0003C\u0011Ba%\u007f\u0003\u0003%\ti!\"\t\u0013\t\u001df0!A\u0005\n\t%f!CA \u0003W\u0001\u0011qGBM\u0011-\u0019i+!\u0003\u0003\u0002\u0003\u0006Iaa,\t\u0011\u0005=\u0013\u0011\u0002C\u0001\u0007wC!b!1\u0002\n\t\u0007I\u0011ABb\u0011%\u0019Y-!\u0003!\u0002\u0013\u0019)\r\u0003\u0006\u0004N\u0006%!\u0019!C\u0001\u0007\u0007D\u0011ba4\u0002\n\u0001\u0006Ia!2\t\u0019\rE\u0017\u0011\u0002a\u0001\n\u0003\tYca5\t\u0019\r\r\u0018\u0011\u0002a\u0001\n\u0003\tYc!:\t\u0013\r=\u0018\u0011\u0002Q!\n\rU\u0007bCBy\u0003\u0013A)\u0019!C\u0001\u0007\u0007D\u0001ba=\u0002\n\u0011\u00053Q\u001f\u0005\t\u0007o\fI\u0001\"\u0001\u0004z\"AA1AA\u0005\t\u0013!)\u0001\u0003\u0005\u0005\u0016\u0005%A\u0011\u0001C\f\u0011!!i\"!\u0003\u0005\u0002\u0011}\u0011!\u0003#og\u000ec\u0017.\u001a8u\u0015\u0011\ti#a\f\u0002\u0011%tG/\u001a:oC2TA!!\r\u00024\u0005\u0019AM\\:\u000b\t\u0005U\u0012qG\u0001\u0003S>T!!!\u000f\u0002\t\u0005\\7.\u0019\t\u0004\u0003{\tQBAA\u0016\u0005%!en]\"mS\u0016tGoE\u0002\u0002\u0003\u0007\u0002B!!\u0012\u0002L5\u0011\u0011q\t\u0006\u0003\u0003\u0013\nQa]2bY\u0006LA!!\u0014\u0002H\t1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u0003\u0003w\u00111\u0002\u00128t#V,7\u000f^5p]N\u00191!a\u0011\u0002\u0005%$WCAA/!\u0011\t)%a\u0018\n\t\u0005\u0005\u0014q\t\u0002\u0006'\"|'\u000f^\u0015\u0005\u0007y9TAA\u0005Rk\u0016\u001cH/[8oiMIa$a\u0011\u0002j\u00055\u00141\u000f\t\u0004\u0003W\u001aQ\"A\u0001\u0011\t\u0005\u0015\u0013qN\u0005\u0005\u0003c\n9EA\u0004Qe>$Wo\u0019;\u0011\t\u0005U\u0014Q\u0011\b\u0005\u0003o\n\tI\u0004\u0003\u0002z\u0005}TBAA>\u0015\u0011\ti(!\u0015\u0002\rq\u0012xn\u001c;?\u0013\t\tI%\u0003\u0003\u0002\u0004\u0006\u001d\u0013a\u00029bG.\fw-Z\u0005\u0005\u0003\u000f\u000bII\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0003\u0002\u0004\u0006\u001d\u0013aA5eA\u0005!a.Y7f+\t\t\t\n\u0005\u0003\u0002\u0014\u0006me\u0002BAK\u0003/\u0003B!!\u001f\u0002H%!\u0011\u0011TA$\u0003\u0019\u0001&/\u001a3fM&!\u0011QTAP\u0005\u0019\u0019FO]5oO*!\u0011\u0011TA$\u0003\u0015q\u0017-\\3!)\u0019\t)+a*\u0002*B\u0019\u00111\u000e\u0010\t\u000f\u0005e3\u00051\u0001\u0002^!9\u0011QR\u0012A\u0002\u0005E\u0015\u0001B2paf$b!!*\u00020\u0006E\u0006\"CA-IA\u0005\t\u0019AA/\u0011%\ti\t\nI\u0001\u0002\u0004\t\t*\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\u0005]&\u0006BA/\u0003s[#!a/\u0011\t\u0005u\u0016qY\u0007\u0003\u0003\u007fSA!!1\u0002D\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0005\u0003\u000b\f9%\u0001\u0006b]:|G/\u0019;j_:LA!!3\u0002@\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\u0011\u0011q\u001a\u0016\u0005\u0003#\u000bI,A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003+\u0004B!a6\u0002b6\u0011\u0011\u0011\u001c\u0006\u0005\u00037\fi.\u0001\u0003mC:<'BAAp\u0003\u0011Q\u0017M^1\n\t\u0005u\u0015\u0011\\\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003O\u0004B!!\u0012\u0002j&!\u00111^A$\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\u0011\t\t0a>\u0011\t\u0005\u0015\u00131_\u0005\u0005\u0003k\f9EA\u0002B]fD\u0011\"!?*\u0003\u0003\u0005\r!a:\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\ty\u0010\u0005\u0004\u0003\u0002\t\u001d\u0011\u0011_\u0007\u0003\u0005\u0007QAA!\u0002\u0002H\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\t%!1\u0001\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0003\u0010\tU\u0001\u0003BA#\u0005#IAAa\u0005\u0002H\t9!i\\8mK\u0006t\u0007\"CA}W\u0005\u0005\t\u0019AAy\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\u0005U'1\u0004\u0005\n\u0003sd\u0013\u0011!a\u0001\u0003O\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0003O\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003+\fa!Z9vC2\u001cH\u0003\u0002B\b\u0005SA\u0011\"!?0\u0003\u0003\u0005\r!!=\u0003\u0013E+Xm\u001d;j_:44#C\u001c\u0002D\u0005%\u0014QNA:)\u0019\u0011\tDa\r\u00036A\u0019\u00111N\u001c\t\u000f\u0005eC\b1\u0001\u0002^!9\u0011Q\u0012\u001fA\u0002\u0005EEC\u0002B\u0019\u0005s\u0011Y\u0004C\u0005\u0002Zu\u0002\n\u00111\u0001\u0002^!I\u0011QR\u001f\u0011\u0002\u0003\u0007\u0011\u0011\u0013\u000b\u0005\u0003c\u0014y\u0004C\u0005\u0002z\n\u000b\t\u00111\u0001\u0002hR!!q\u0002B\"\u0011%\tI\u0010RA\u0001\u0002\u0004\t\t\u0010\u0006\u0003\u0002V\n\u001d\u0003\"CA}\u000b\u0006\u0005\t\u0019AAt)\u0011\u0011yAa\u0013\t\u0013\u0005e\b*!AA\u0002\u0005E(aC*smF+Xm\u001d;j_:\u001c\u0012\"BA\"\u0003S\ni'a\u001d\u0015\r\tM#Q\u000bB,!\r\tY'\u0002\u0005\b\u00033R\u0001\u0019AA/\u0011\u001d\tiI\u0003a\u0001\u0003##bAa\u0015\u0003\\\tu\u0003\"CA-\u0017A\u0005\t\u0019AA/\u0011%\tii\u0003I\u0001\u0002\u0004\t\t\n\u0006\u0003\u0002r\n\u0005\u0004\"CA}!\u0005\u0005\t\u0019AAt)\u0011\u0011yA!\u001a\t\u0013\u0005e(#!AA\u0002\u0005EH\u0003BAk\u0005SB\u0011\"!?\u0014\u0003\u0003\u0005\r!a:\u0015\t\t=!Q\u000e\u0005\n\u0003s4\u0012\u0011!a\u0001\u0003c\f1b\u0015:w#V,7\u000f^5p]B\u0019\u00111\u000e\r\u0014\u000ba\u0011)H!!\u0011\u0015\t]$QPA/\u0003#\u0013\u0019&\u0004\u0002\u0003z)!!1PA$\u0003\u001d\u0011XO\u001c;j[\u0016LAAa \u0003z\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\u001c\u001a\u0011\t\t\r%qQ\u0007\u0003\u0005\u000bSA!!\u000e\u0002^&!\u0011q\u0011BC)\t\u0011\t(A\u0003baBd\u0017\u0010\u0006\u0004\u0003T\t=%\u0011\u0013\u0005\b\u00033Z\u0002\u0019AA/\u0011\u001d\tii\u0007a\u0001\u0003#\u000bq!\u001e8baBd\u0017\u0010\u0006\u0003\u0003\u0018\n\r\u0006CBA#\u00053\u0013i*\u0003\u0003\u0003\u001c\u0006\u001d#AB(qi&|g\u000e\u0005\u0005\u0002F\t}\u0015QLAI\u0013\u0011\u0011\t+a\u0012\u0003\rQ+\b\u000f\\33\u0011%\u0011)\u000bHA\u0001\u0002\u0004\u0011\u0019&A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"Aa+\u0011\t\u0005]'QV\u0005\u0005\u0005_\u000bIN\u0001\u0004PE*,7\r^\u0001\n#V,7\u000f^5p]R\u00022!a\u001b2'\u0015\t$q\u0017BA!)\u00119H! \u0002^\u0005E\u0015Q\u0015\u000b\u0003\u0005g#b!!*\u0003>\n}\u0006bBA-i\u0001\u0007\u0011Q\f\u0005\b\u0003\u001b#\u0004\u0019AAI)\u0011\u00119Ja1\t\u0013\t\u0015V'!AA\u0002\u0005\u0015\u0016!C)vKN$\u0018n\u001c87!\r\tYGS\n\u0006\u0015\n-'\u0011\u0011\t\u000b\u0005o\u0012i(!\u0018\u0002\u0012\nEBC\u0001Bd)\u0019\u0011\tD!5\u0003T\"9\u0011\u0011L'A\u0002\u0005u\u0003bBAG\u001b\u0002\u0007\u0011\u0011\u0013\u000b\u0005\u0005/\u00139\u000eC\u0005\u0003&:\u000b\t\u00111\u0001\u00032\t1\u0011I\\:xKJ\u001c\u0012\u0002UA\"\u0005;\fi'a\u001d\u0011\t\t}'Q]\u0007\u0003\u0005CTAAa9\u00028\u0005)\u0011m\u0019;pe&!!q\u001dBq\u0005\u0005runU3sS\u0006d\u0017N_1uS>tg+\u001a:jM&\u001c\u0017\r^5p]:+W\rZ3e\u0003\r\u0011(o]\u000b\u0003\u0005[\u0004bAa<\u0003v\neXB\u0001By\u0015\u0011\u0011\u0019Pa\u0001\u0002\u0013%lW.\u001e;bE2,\u0017\u0002\u0002B|\u0005c\u00141aU3r!\u0011\u0011YP!@\u000e\u0005\u0005=\u0012\u0002\u0002B\u0000\u0003_\u0011aBU3t_V\u00148-\u001a*fG>\u0014H-\u0001\u0003seN\u0004\u0013AD1eI&$\u0018n\u001c8bYJ+7m]\u0001\u0010C\u0012$\u0017\u000e^5p]\u0006d'+Z2tAQA1\u0011BB\u0006\u0007\u001b\u0019y\u0001E\u0002\u0002lACq!!\u0017X\u0001\u0004\ti\u0006C\u0004\u0003j^\u0003\rA!<\t\u0013\r\rq\u000b%AA\u0002\t5H\u0003CB\u0005\u0007'\u0019)ba\u0006\t\u0013\u0005e\u0003\f%AA\u0002\u0005u\u0003\"\u0003Bu1B\u0005\t\u0019\u0001Bw\u0011%\u0019\u0019\u0001\u0017I\u0001\u0002\u0004\u0011i/\u0006\u0002\u0004\u001c)\"!Q^A]\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM\"B!!=\u0004\"!I\u0011\u0011 0\u0002\u0002\u0003\u0007\u0011q\u001d\u000b\u0005\u0005\u001f\u0019)\u0003C\u0005\u0002z\u0002\f\t\u00111\u0001\u0002rR!\u0011Q[B\u0015\u0011%\tI0YA\u0001\u0002\u0004\t9\u000f\u0006\u0003\u0003\u0010\r5\u0002\"CA}I\u0006\u0005\t\u0019AAy\u0003\u0019\ten]<feB\u0019\u00111\u000e4\u0014\u000b\u0019\u001c)D!!\u0011\u0019\t]4qGA/\u0005[\u0014io!\u0003\n\t\re\"\u0011\u0010\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\u001cDCAB\u0019)!\u0019Iaa\u0010\u0004B\r\r\u0003bBA-S\u0002\u0007\u0011Q\f\u0005\b\u0005SL\u0007\u0019\u0001Bw\u0011%\u0019\u0019!\u001bI\u0001\u0002\u0004\u0011i/A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00134)\u0011\u0019Ie!\u0015\u0011\r\u0005\u0015#\u0011TB&!)\t)e!\u0014\u0002^\t5(Q^\u0005\u0005\u0007\u001f\n9E\u0001\u0004UkBdWm\r\u0005\n\u0005K[\u0017\u0011!a\u0001\u0007\u0013\t1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u001a$a\u0003#s_B\u0014V-];fgR\u001crA\\A\"\u0003[\n\u0019\b\u0006\u0003\u0004\\\ru\u0003cAA6]\"9\u0011\u0011L9A\u0002\u0005uC\u0003BB.\u0007CB\u0011\"!\u0017s!\u0003\u0005\r!!\u0018\u0015\t\u0005E8Q\r\u0005\n\u0003s4\u0018\u0011!a\u0001\u0003O$BAa\u0004\u0004j!I\u0011\u0011 =\u0002\u0002\u0003\u0007\u0011\u0011\u001f\u000b\u0005\u0003+\u001ci\u0007C\u0005\u0002zf\f\t\u00111\u0001\u0002hR!!qBB9\u0011%\tI\u0010`A\u0001\u0002\u0004\t\t0A\u0006Ee>\u0004(+Z9vKN$\bcAA6}N)ap!\u001f\u0003\u0002BA!qOB>\u0003;\u001aY&\u0003\u0003\u0004~\te$!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8ocQ\u00111Q\u000f\u000b\u0005\u00077\u001a\u0019\t\u0003\u0005\u0002Z\u0005\r\u0001\u0019AA/)\u0011\u00199i!#\u0011\r\u0005\u0015#\u0011TA/\u0011)\u0011)+!\u0002\u0002\u0002\u0003\u000711\f\u0015\u0004\u0003\r5\u0005\u0003BBH\u0007'k!a!%\u000b\t\u0005\u0015\u0017qG\u0005\u0005\u0007+\u001b\tJA\u0006J]R,'O\\1m\u0003BL\u0007f\u0001\u0001\u0004\u000eNQ\u0011\u0011BA\"\u00077\u001b\tka*\u0011\t\t}7QT\u0005\u0005\u0007?\u0013\tOA\u0003BGR|'\u000f\u0005\u0003\u0003`\u000e\r\u0016\u0002BBS\u0005C\u0014A\"Q2u_JdunZ4j]\u001e\u0004BAa8\u0004*&!11\u0016Bq\u0005\u0015\u0019F/Y:i\u0003\tq7\u000f\u0005\u0003\u00042\u000e]VBABZ\u0015\u0011\u0019),!8\u0002\u00079,G/\u0003\u0003\u0004:\u000eM&!E%oKR\u001cvnY6fi\u0006#GM]3tgR!1QXB`!\u0011\ti$!\u0003\t\u0011\r5\u0016Q\u0002a\u0001\u0007_\u000b1!\u001e3q+\t\u0019)\r\u0005\u0003\u0003`\u000e\u001d\u0017\u0002BBe\u0005C\u0014\u0001\"Q2u_J\u0014VMZ\u0001\u0005k\u0012\u0004\b%A\u0002uGB\fA\u0001^2qA\u0005\u0001\u0012N\u001c4mS\u001eDGOU3rk\u0016\u001cHo]\u000b\u0003\u0007+\u0004\u0002\"a%\u0004X\u0006u31\\\u0005\u0005\u00073\fyJA\u0002NCB\u0004\u0002\"!\u0012\u0003 \u000e\u00157Q\u001c\t\u0005\u0003{\u0019y.\u0003\u0003\u0004b\u0006-\"aB'fgN\fw-Z\u0001\u0015S:4G.[4iiJ+\u0017/^3tiN|F%Z9\u0015\t\r\u001d8Q\u001e\t\u0005\u0003\u000b\u001aI/\u0003\u0003\u0004l\u0006\u001d#\u0001B+oSRD!\"!?\u0002\u001a\u0005\u0005\t\u0019ABk\u0003EIgN\u001a7jO\"$(+Z9vKN$8\u000fI\u0001\ri\u000e\u0004HI\\:DY&,g\u000e^\u0001\taJ,7\u000b^1siR\u00111q]\u0001\be\u0016\u001cW-\u001b<f+\t\u0019Y\u0010\u0005\u0003\u0004~\u000e}XBAA\u0005\u0013\u0011!\ta!(\u0003\u000fI+7-Z5wK\u00069Q.Z:tC\u001e,G\u0003CBo\t\u000f!I\u0001b\u0003\t\u0011\u00055\u00151\u0005a\u0001\u0003#C\u0001\"!\u0017\u0002$\u0001\u0007\u0011Q\f\u0005\t\t\u001b\t\u0019\u00031\u0001\u0005\u0010\u0005Q!/Z2pe\u0012$\u0016\u0010]3\u0011\t\tmH\u0011C\u0005\u0005\t'\tyC\u0001\u0006SK\u000e|'\u000f\u001a+za\u0016\fQA]3bIf$Baa?\u0005\u001a!AA1DA\u0013\u0001\u0004\u0019)-\u0001\u0004t_\u000e\\W\r^\u0001\u0010GJ,\u0017\r^3UGB\u001cE.[3oiR\u00111Q\u0019\u0015\u0005\u0003\u0013\u0019i\t")
@InternalApi
public class DnsClient
implements ActorLogging,
Stash {
    private ActorRef tcpDnsClient;
    public final InetSocketAddress akka$io$dns$internal$DnsClient$$ns;
    private final ActorRef udp;
    private final ActorRef tcp;
    private Map<Object, Tuple2<ActorRef, Message>> inflightRequests;
    private Vector<Envelope> akka$actor$StashSupport$$theStash;
    private int akka$actor$StashSupport$$capacity;
    private DequeBasedMessageQueueSemantics mailbox;
    private LoggingAdapter akka$actor$ActorLogging$$_log;
    private ActorContext context;
    private ActorRef self;
    private volatile boolean bitmap$0;

    @Override
    public /* synthetic */ void akka$actor$UnrestrictedStash$$super$preRestart(Throwable reason, Option message) {
        Actor.preRestart$(this, reason, message);
    }

    @Override
    public /* synthetic */ void akka$actor$UnrestrictedStash$$super$postStop() {
        Actor.postStop$(this);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        UnrestrictedStash.preRestart$(this, reason, message);
    }

    @Override
    public void postStop() throws Exception {
        UnrestrictedStash.postStop$(this);
    }

    @Override
    public void stash() {
        StashSupport.stash$(this);
    }

    @Override
    public void prepend(Seq<Envelope> others) {
        StashSupport.prepend$(this, others);
    }

    @Override
    public void unstash() {
        StashSupport.unstash$(this);
    }

    @Override
    public void unstashAll() {
        StashSupport.unstashAll$(this);
    }

    @Override
    @InternalStableApi
    public void unstashAll(Function1<Object, Object> filterPredicate) {
        StashSupport.unstashAll$(this, filterPredicate);
    }

    @Override
    @InternalStableApi
    public Vector<Envelope> clearStash() {
        return StashSupport.clearStash$(this);
    }

    @Override
    public LoggingAdapter log() {
        return ActorLogging.log$(this);
    }

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
    public SupervisorStrategy supervisorStrategy() {
        return Actor.supervisorStrategy$(this);
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
    public Vector<Envelope> akka$actor$StashSupport$$theStash() {
        return this.akka$actor$StashSupport$$theStash;
    }

    @Override
    public void akka$actor$StashSupport$$theStash_$eq(Vector<Envelope> x$1) {
        this.akka$actor$StashSupport$$theStash = x$1;
    }

    @Override
    public int akka$actor$StashSupport$$capacity() {
        return this.akka$actor$StashSupport$$capacity;
    }

    @Override
    public DequeBasedMessageQueueSemantics mailbox() {
        return this.mailbox;
    }

    @Override
    public final void akka$actor$StashSupport$_setter_$akka$actor$StashSupport$$capacity_$eq(int x$1) {
        this.akka$actor$StashSupport$$capacity = x$1;
    }

    @Override
    public void akka$actor$StashSupport$_setter_$mailbox_$eq(DequeBasedMessageQueueSemantics x$1) {
        this.mailbox = x$1;
    }

    @Override
    public LoggingAdapter akka$actor$ActorLogging$$_log() {
        return this.akka$actor$ActorLogging$$_log;
    }

    @Override
    public void akka$actor$ActorLogging$$_log_$eq(LoggingAdapter x$1) {
        this.akka$actor$ActorLogging$$_log = x$1;
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

    public ActorRef udp() {
        return this.udp;
    }

    public ActorRef tcp() {
        return this.tcp;
    }

    public Map<Object, Tuple2<ActorRef, Message>> inflightRequests() {
        return this.inflightRequests;
    }

    public void inflightRequests_$eq(Map<Object, Tuple2<ActorRef, Message>> x$1) {
        this.inflightRequests = x$1;
    }

    private ActorRef tcpDnsClient$lzycompute() {
        DnsClient dnsClient = this;
        synchronized (dnsClient) {
            if (!this.bitmap$0) {
                this.tcpDnsClient = this.createTcpClient();
                this.bitmap$0 = true;
            }
        }
        return this.tcpDnsClient;
    }

    public ActorRef tcpDnsClient() {
        if (!this.bitmap$0) {
            return this.tcpDnsClient$lzycompute();
        }
        return this.tcpDnsClient;
    }

    @Override
    public void preStart() {
        this.udp().$bang(new Udp.Bind(this.self(), new InetSocketAddress(InetAddress.getByAddress((byte[])Array$.MODULE$.ofDim(4, ClassTag$.MODULE$.Byte())), 0), Udp$Bind$.MODULE$.apply$default$3()), this.self());
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ DnsClient $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Udp.Bound) {
                    Udp.Bound bound2 = (Udp.Bound)A1;
                    InetSocketAddress local = bound2.localAddress();
                    this.$outer.log().debug("Bound to UDP address [{}]", local);
                    this.$outer.context().become(this.$outer.ready(this.$outer.sender()));
                    this.$outer.unstashAll();
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Question4) {
                    this.$outer.stash();
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Question6) {
                    this.$outer.stash();
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof SrvQuestion) {
                    this.$outer.stash();
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof Udp.Bound) {
                    return true;
                }
                if (object instanceof Question4) {
                    return true;
                }
                if (object instanceof Question6) {
                    return true;
                }
                return object instanceof SrvQuestion;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public Message akka$io$dns$internal$DnsClient$$message(String name, short id, RecordType recordType) {
        return new Message(id, MessageFlags$.MODULE$.apply(MessageFlags$.MODULE$.apply$default$1(), MessageFlags$.MODULE$.apply$default$2(), MessageFlags$.MODULE$.apply$default$3(), MessageFlags$.MODULE$.apply$default$4(), MessageFlags$.MODULE$.apply$default$5(), MessageFlags$.MODULE$.apply$default$6(), MessageFlags$.MODULE$.apply$default$7()), new $colon$colon<Nothing$>((Nothing$)((Object)new Question(name, recordType, RecordClass$.MODULE$.IN())), Nil$.MODULE$), Message$.MODULE$.apply$default$4(), Message$.MODULE$.apply$default$5(), Message$.MODULE$.apply$default$6());
    }

    public PartialFunction<Object, BoxedUnit> ready(ActorRef socket) {
        return new Serializable(this, socket){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ DnsClient $outer;
            private final ActorRef socket$1;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof DropRequest) {
                    DropRequest dropRequest = (DropRequest)A1;
                    short id = dropRequest.id();
                    this.$outer.log().debug("Dropping request [{}]", BoxesRunTime.boxToShort(id));
                    this.$outer.inflightRequests_$eq((Map)this.$outer.inflightRequests().$minus(BoxesRunTime.boxToShort(id)));
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Question4) {
                    Question4 question4 = (Question4)A1;
                    short id = question4.id();
                    String name = question4.name();
                    this.$outer.log().debug("Resolving [{}] (A)", name);
                    Message msg = this.$outer.akka$io$dns$internal$DnsClient$$message(name, id, RecordType$.MODULE$.A());
                    this.$outer.inflightRequests_$eq((Map)this.$outer.inflightRequests().$plus(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(BoxesRunTime.boxToShort(id)), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(this.$outer.sender()), msg))));
                    this.$outer.log().debug("Message [{}] to [{}]: [{}]", BoxesRunTime.boxToShort(id), this.$outer.akka$io$dns$internal$DnsClient$$ns, msg);
                    this.socket$1.$bang(Udp$Send$.MODULE$.apply(msg.write(), this.$outer.akka$io$dns$internal$DnsClient$$ns), this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Question6) {
                    Question6 question6 = (Question6)A1;
                    short id = question6.id();
                    String name = question6.name();
                    this.$outer.log().debug("Resolving [{}] (AAAA)", name);
                    Message msg = this.$outer.akka$io$dns$internal$DnsClient$$message(name, id, RecordType$.MODULE$.AAAA());
                    this.$outer.inflightRequests_$eq((Map)this.$outer.inflightRequests().$plus(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(BoxesRunTime.boxToShort(id)), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(this.$outer.sender()), msg))));
                    this.$outer.log().debug("Message to [{}]: [{}]", this.$outer.akka$io$dns$internal$DnsClient$$ns, msg);
                    this.socket$1.$bang(Udp$Send$.MODULE$.apply(msg.write(), this.$outer.akka$io$dns$internal$DnsClient$$ns), this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof SrvQuestion) {
                    SrvQuestion srvQuestion = (SrvQuestion)A1;
                    short id = srvQuestion.id();
                    String name = srvQuestion.name();
                    this.$outer.log().debug("Resolving [{}] (SRV)", name);
                    Message msg = this.$outer.akka$io$dns$internal$DnsClient$$message(name, id, RecordType$.MODULE$.SRV());
                    this.$outer.inflightRequests_$eq((Map)this.$outer.inflightRequests().$plus(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(BoxesRunTime.boxToShort(id)), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(this.$outer.sender()), msg))));
                    this.$outer.log().debug("Message to [{}]: [{}]", this.$outer.akka$io$dns$internal$DnsClient$$ns, msg);
                    this.socket$1.$bang(Udp$Send$.MODULE$.apply(msg.write(), this.$outer.akka$io$dns$internal$DnsClient$$ns), this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Udp.CommandFailed) {
                    Udp.CommandFailed commandFailed = (Udp.CommandFailed)A1;
                    Udp.Command cmd = commandFailed.cmd();
                    this.$outer.log().debug("Command failed [{}]", cmd);
                    Udp.Command command = cmd;
                    if (command instanceof Udp.Send) {
                        Udp.Send send = (Udp.Send)command;
                        Try$.MODULE$.apply(() -> {
                            Message msg = Message$.MODULE$.parse(send.payload());
                            $this.$outer.inflightRequests().get(BoxesRunTime.boxToShort(msg.id())).foreach((Function1<Tuple2, Object> & Serializable)x0$1 -> {
                                $anonfun$ready$1.$anonfun$applyOrElse$2(this, msg, x0$1);
                                return BoxedUnit.UNIT;
                            });
                        });
                    } else {
                        this.$outer.log().warning("Dns client failed to send {}", cmd);
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Udp.Received) {
                    Tuple2<Seq<ResourceRecord>, Seq<ResourceRecord>> tuple2;
                    Udp.Received received = (Udp.Received)A1;
                    ByteString data = received.data();
                    InetSocketAddress remote = received.sender();
                    this.$outer.log().debug("Received message from [{}]: [{}]", remote, data);
                    Message msg = Message$.MODULE$.parse(data);
                    this.$outer.log().debug("Decoded UDP DNS response [{}]", msg);
                    if (MessageFlags$.MODULE$.isTruncated$extension(msg.flags())) {
                        Some some;
                        Tuple2 tuple22;
                        this.$outer.log().debug("DNS response truncated, falling back to TCP");
                        Option<V> option = this.$outer.inflightRequests().get(BoxesRunTime.boxToShort(msg.id()));
                        if (option instanceof Some && (tuple22 = (Tuple2)(some = (Some)option).value()) != null) {
                            Message msg2 = (Message)tuple22._2();
                            this.$outer.tcpDnsClient().$bang(msg2, this.$outer.self());
                        } else {
                            this.$outer.log().debug("Client for id {} not found. Discarding unsuccessful response.", BoxesRunTime.boxToShort(msg.id()));
                        }
                        return (B1)BoxedUnit.UNIT;
                    }
                    Enumeration.Value value = MessageFlags$.MODULE$.responseCode$extension(msg.flags());
                    Enumeration.Value value2 = ResponseCode$.MODULE$.SUCCESS();
                    Tuple2<Seq<ResourceRecord>, Seq<ResourceRecord>> tuple23 = !(value != null ? !((Object)value).equals(value2) : value2 != null) ? new Tuple2<Seq<ResourceRecord>, Seq<ResourceRecord>>(msg.answerRecs(), msg.additionalRecs()) : (tuple2 = new Tuple2<Nil$, Nil$>(Nil$.MODULE$, Nil$.MODULE$));
                    if (tuple2 == null) {
                        throw new MatchError(tuple2);
                    }
                    Seq<ResourceRecord> recs = tuple2._1();
                    Seq<ResourceRecord> additionalRecs = tuple2._2();
                    Tuple2<Seq<ResourceRecord>, Seq<ResourceRecord>> tuple24 = new Tuple2<Seq<ResourceRecord>, Seq<ResourceRecord>>(recs, additionalRecs);
                    Seq<ResourceRecord> recs2 = tuple24._1();
                    Seq<ResourceRecord> additionalRecs2 = tuple24._2();
                    this.$outer.self().$bang(new Answer(msg.id(), recs2, additionalRecs2), this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Answer) {
                    Some some;
                    Tuple2 tuple2;
                    Answer answer = (Answer)A1;
                    Option<V> option = this.$outer.inflightRequests().get(BoxesRunTime.boxToShort(answer.id()));
                    if (option instanceof Some && (tuple2 = (Tuple2)(some = (Some)option).value()) != null) {
                        ActorRef reply = (ActorRef)tuple2._1();
                        reply.$bang(answer, this.$outer.self());
                        this.$outer.inflightRequests_$eq((Map)this.$outer.inflightRequests().$minus(BoxesRunTime.boxToShort(answer.id())));
                    } else if (None$.MODULE$.equals(option)) {
                        this.$outer.log().debug("Client for id {} not found. Discarding response.", BoxesRunTime.boxToShort(answer.id()));
                    } else {
                        throw new MatchError(option);
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                if (Udp$Unbind$.MODULE$.equals(A1)) {
                    this.socket$1.$bang(Udp$Unbind$.MODULE$, this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                if (Udp$Unbound$.MODULE$.equals(A1)) {
                    this.$outer.context().stop(this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof DropRequest) {
                    return true;
                }
                if (object instanceof Question4) {
                    return true;
                }
                if (object instanceof Question6) {
                    return true;
                }
                if (object instanceof SrvQuestion) {
                    return true;
                }
                if (object instanceof Udp.CommandFailed) {
                    return true;
                }
                if (object instanceof Udp.Received) {
                    return true;
                }
                if (object instanceof Answer) {
                    return true;
                }
                if (Udp$Unbind$.MODULE$.equals(object)) {
                    return true;
                }
                return Udp$Unbound$.MODULE$.equals(object);
            }

            public static final /* synthetic */ void $anonfun$applyOrElse$2($anonfun$ready$1 $this, Message msg$1, Tuple2 x0$1) {
                Tuple2 tuple2 = x0$1;
                if (tuple2 != null) {
                    ActorRef s2 = (ActorRef)tuple2._1();
                    s2.$bang(new Status.Failure(new RuntimeException("Send failed to nameserver")), $this.$outer.self());
                    $this.$outer.inflightRequests_$eq((Map)$this.$outer.inflightRequests().$minus(BoxesRunTime.boxToShort(msg$1.id())));
                    return;
                }
                throw new MatchError(tuple2);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.socket$1 = socket$1;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1(akka.io.dns.internal.DnsClient$$anonfun$ready$1 akka.io.Udp$Send ), $anonfun$applyOrElse$2$adapted(akka.io.dns.internal.DnsClient$$anonfun$ready$1 akka.io.dns.internal.Message scala.Tuple2 )}, serializedLambda);
            }
        };
    }

    public ActorRef createTcpClient() {
        return this.context().actorOf(BackoffSupervisor$.MODULE$.props(BackoffOpts$.MODULE$.onFailure(Props$.MODULE$.apply(TcpDnsClient.class, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.tcp(), this.akka$io$dns$internal$DnsClient$$ns, this.self()})), "tcpDnsClient", new package.DurationInt(package$.MODULE$.DurationInt(10)).millis(), new package.DurationInt(package$.MODULE$.DurationInt(20)).seconds(), 0.1)), "tcpDnsClientSupervisor");
    }

    public DnsClient(InetSocketAddress ns) {
        this.akka$io$dns$internal$DnsClient$$ns = ns;
        Actor.$init$(this);
        ActorLogging.$init$(this);
        StashSupport.$init$(this);
        UnrestrictedStash.$init$(this);
        this.udp = IO$.MODULE$.apply(Udp$.MODULE$, this.context().system());
        this.tcp = IO$.MODULE$.apply(Tcp$.MODULE$, this.context().system());
        this.inflightRequests = Predef$.MODULE$.Map().empty();
        Statics.releaseFence();
    }

    public static final class Answer
    implements NoSerializationVerificationNeeded,
    Product,
    Serializable {
        private final short id;
        private final Seq<ResourceRecord> rrs;
        private final Seq<ResourceRecord> additionalRecs;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public short id() {
            return this.id;
        }

        public Seq<ResourceRecord> rrs() {
            return this.rrs;
        }

        public Seq<ResourceRecord> additionalRecs() {
            return this.additionalRecs;
        }

        public Answer copy(short id, Seq<ResourceRecord> rrs, Seq<ResourceRecord> additionalRecs) {
            return new Answer(id, rrs, additionalRecs);
        }

        public short copy$default$1() {
            return this.id();
        }

        public Seq<ResourceRecord> copy$default$2() {
            return this.rrs();
        }

        public Seq<ResourceRecord> copy$default$3() {
            return this.additionalRecs();
        }

        @Override
        public String productPrefix() {
            return "Answer";
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
                    return BoxesRunTime.boxToShort(this.id());
                }
                case 1: {
                    return this.rrs();
                }
                case 2: {
                    return this.additionalRecs();
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
            return x$1 instanceof Answer;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "id";
                }
                case 1: {
                    return "rrs";
                }
                case 2: {
                    return "additionalRecs";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.id());
            n = Statics.mix(n, Statics.anyHash(this.rrs()));
            n = Statics.mix(n, Statics.anyHash(this.additionalRecs()));
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
            if (!(object instanceof Answer)) return false;
            boolean bl = true;
            if (!bl) return false;
            Answer answer = (Answer)x$1;
            if (this.id() != answer.id()) return false;
            Seq<ResourceRecord> seq = this.rrs();
            Seq<ResourceRecord> seq2 = answer.rrs();
            if (seq == null) {
                if (seq2 != null) {
                    return false;
                }
            } else if (!seq.equals(seq2)) return false;
            Seq<ResourceRecord> seq3 = this.additionalRecs();
            Seq<ResourceRecord> seq4 = answer.additionalRecs();
            if (seq3 == null) {
                if (seq4 == null) return true;
                return false;
            } else {
                if (!seq3.equals(seq4)) return false;
                return true;
            }
        }

        public Answer(short id, Seq<ResourceRecord> rrs, Seq<ResourceRecord> additionalRecs) {
            this.id = id;
            this.rrs = rrs;
            this.additionalRecs = additionalRecs;
            Product.$init$(this);
        }
    }

    public static interface DnsQuestion {
        public short id();
    }

    public static final class DropRequest
    implements Product,
    Serializable {
        private final short id;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public short id() {
            return this.id;
        }

        public DropRequest copy(short id) {
            return new DropRequest(id);
        }

        public short copy$default$1() {
            return this.id();
        }

        @Override
        public String productPrefix() {
            return "DropRequest";
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
                    return BoxesRunTime.boxToShort(this.id());
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
            return x$1 instanceof DropRequest;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "id";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.id());
            return Statics.finalizeHash(n, 1);
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
            if (!(object instanceof DropRequest)) return false;
            boolean bl = true;
            if (!bl) return false;
            DropRequest dropRequest = (DropRequest)x$1;
            if (this.id() != dropRequest.id()) return false;
            return true;
        }

        public DropRequest(short id) {
            this.id = id;
            Product.$init$(this);
        }
    }

    public static final class Question4
    implements DnsQuestion,
    Product,
    Serializable {
        private final short id;
        private final String name;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public short id() {
            return this.id;
        }

        public String name() {
            return this.name;
        }

        public Question4 copy(short id, String name) {
            return new Question4(id, name);
        }

        public short copy$default$1() {
            return this.id();
        }

        public String copy$default$2() {
            return this.name();
        }

        @Override
        public String productPrefix() {
            return "Question4";
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
                    return BoxesRunTime.boxToShort(this.id());
                }
                case 1: {
                    return this.name();
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
            return x$1 instanceof Question4;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "id";
                }
                case 1: {
                    return "name";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.id());
            n = Statics.mix(n, Statics.anyHash(this.name()));
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
            if (!(object instanceof Question4)) return false;
            boolean bl = true;
            if (!bl) return false;
            Question4 question4 = (Question4)x$1;
            if (this.id() != question4.id()) return false;
            String string = this.name();
            String string2 = question4.name();
            if (string != null) {
                if (!string.equals(string2)) return false;
                return true;
            }
            if (string2 == null) return true;
            return false;
        }

        public Question4(short id, String name) {
            this.id = id;
            this.name = name;
            Product.$init$(this);
        }
    }

    public static final class Question6
    implements DnsQuestion,
    Product,
    Serializable {
        private final short id;
        private final String name;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public short id() {
            return this.id;
        }

        public String name() {
            return this.name;
        }

        public Question6 copy(short id, String name) {
            return new Question6(id, name);
        }

        public short copy$default$1() {
            return this.id();
        }

        public String copy$default$2() {
            return this.name();
        }

        @Override
        public String productPrefix() {
            return "Question6";
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
                    return BoxesRunTime.boxToShort(this.id());
                }
                case 1: {
                    return this.name();
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
            return x$1 instanceof Question6;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "id";
                }
                case 1: {
                    return "name";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.id());
            n = Statics.mix(n, Statics.anyHash(this.name()));
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
            if (!(object instanceof Question6)) return false;
            boolean bl = true;
            if (!bl) return false;
            Question6 question6 = (Question6)x$1;
            if (this.id() != question6.id()) return false;
            String string = this.name();
            String string2 = question6.name();
            if (string != null) {
                if (!string.equals(string2)) return false;
                return true;
            }
            if (string2 == null) return true;
            return false;
        }

        public Question6(short id, String name) {
            this.id = id;
            this.name = name;
            Product.$init$(this);
        }
    }

    public static final class SrvQuestion
    implements DnsQuestion,
    Product,
    Serializable {
        private final short id;
        private final String name;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public short id() {
            return this.id;
        }

        public String name() {
            return this.name;
        }

        public SrvQuestion copy(short id, String name) {
            return new SrvQuestion(id, name);
        }

        public short copy$default$1() {
            return this.id();
        }

        public String copy$default$2() {
            return this.name();
        }

        @Override
        public String productPrefix() {
            return "SrvQuestion";
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
                    return BoxesRunTime.boxToShort(this.id());
                }
                case 1: {
                    return this.name();
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
            return x$1 instanceof SrvQuestion;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "id";
                }
                case 1: {
                    return "name";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.id());
            n = Statics.mix(n, Statics.anyHash(this.name()));
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
            if (!(object instanceof SrvQuestion)) return false;
            boolean bl = true;
            if (!bl) return false;
            SrvQuestion srvQuestion = (SrvQuestion)x$1;
            if (this.id() != srvQuestion.id()) return false;
            String string = this.name();
            String string2 = srvQuestion.name();
            if (string != null) {
                if (!string.equals(string2)) return false;
                return true;
            }
            if (string2 == null) return true;
            return false;
        }

        public SrvQuestion(short id, String name) {
            this.id = id;
            this.name = name;
            Product.$init$(this);
        }
    }
}

