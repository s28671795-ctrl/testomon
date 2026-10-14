/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.DeadLetterSuppression;
import akka.actor.NoSerializationVerificationNeeded;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy$;
import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.impl.SnapshotCollector$;
import akka.stream.impl.StreamSupervisor$;
import akka.stream.impl.StreamSupervisor$GetChildren$;
import akka.stream.impl.StreamSupervisor$StopChildren$;
import akka.stream.impl.StreamSupervisor$StoppedChildren$;
import akka.stream.snapshot.StreamSnapshot;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.concurrent.atomic.AtomicBoolean;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Product;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0011\u0015uACA'\u0003\u001fB\t!a\u0016\u0002\\\u0019Q\u0011qLA(\u0011\u0003\t9&!\u0019\t\u000f\u0005=\u0014\u0001\"\u0001\u0002t!9\u0011QO\u0001\u0005\u0002\u0005]\u0004bCAW\u0003\t\u0007I\u0011AA*\u0003_C\u0001\"!0\u0002A\u0003%\u0011\u0011\u0017\u0005\n\u0003\u007f\u000b!\u0019!C\u0005\u0003\u0003D\u0001\"!3\u0002A\u0003%\u00111\u0019\u0005\b\u0003\u0017\fA\u0011AAg\r\u0019\t\u0019/\u0001\"\u0002f\"Q\u0011QO\u0005\u0003\u0016\u0004%\tAa\u0003\t\u0015\t5\u0011B!E!\u0002\u0013\tI\b\u0003\u0006\u0003\u0010%\u0011)\u001a!C\u0001\u0005#A!Ba\u0005\n\u0005#\u0005\u000b\u0011BAh\u0011\u001d\ty'\u0003C\u0001\u0005+A\u0011Ba\b\n\u0003\u0003%\tA!\t\t\u0013\t\u001d\u0012\"%A\u0005\u0002\t%\u0002\"\u0003B \u0013E\u0005I\u0011\u0001B!\u0011%\u0011)%CA\u0001\n\u0003\ny\u000bC\u0005\u0003H%\t\t\u0011\"\u0001\u0003J!I!\u0011K\u0005\u0002\u0002\u0013\u0005!1\u000b\u0005\n\u0005?J\u0011\u0011!C!\u0005CB\u0011Ba\u001c\n\u0003\u0003%\tA!\u001d\t\u0013\tm\u0014\"!A\u0005B\tu\u0004\"\u0003BA\u0013\u0005\u0005I\u0011\tBB\u0011%\u0011))CA\u0001\n\u0003\u00129\tC\u0005\u0003\n&\t\t\u0011\"\u0011\u0003\f\u001eI!qR\u0001\u0002\u0002#\u0005!\u0011\u0013\u0004\n\u0003G\f\u0011\u0011!E\u0001\u0005'Cq!a\u001c\u001d\t\u0003\u0011Y\u000bC\u0005\u0003\u0006r\t\t\u0011\"\u0012\u0003\b\"I!Q\u0016\u000f\u0002\u0002\u0013\u0005%q\u0016\u0005\n\u0005kc\u0012\u0011!CA\u0005oC\u0011B!3\u001d\u0003\u0003%IAa3\u0007\r\tM\u0017A\u0011Bk\u0011)\u00119N\tBK\u0002\u0013\u0005!\u0011\u001c\u0005\u000b\u0005S\u0014#\u0011#Q\u0001\n\tm\u0007bBA8E\u0011\u0005!1\u001e\u0005\n\u0005?\u0011\u0013\u0011!C\u0001\u0005cD\u0011Ba\n##\u0003%\tA!>\t\u0013\t\u0015#%!A\u0005B\u0005=\u0006\"\u0003B$E\u0005\u0005I\u0011\u0001B%\u0011%\u0011\tFIA\u0001\n\u0003\u0011I\u0010C\u0005\u0003`\t\n\t\u0011\"\u0011\u0003b!I!q\u000e\u0012\u0002\u0002\u0013\u0005!Q \u0005\n\u0005w\u0012\u0013\u0011!C!\u0007\u0003A\u0011B!!#\u0003\u0003%\tEa!\t\u0013\t\u0015%%!A\u0005B\t\u001d\u0005\"\u0003BEE\u0005\u0005I\u0011IB\u0003\u000f%\u0019I!AA\u0001\u0012\u0003\u0019YAB\u0005\u0003T\u0006\t\t\u0011#\u0001\u0004\u000e!9\u0011q\u000e\u001a\u0005\u0002\rU\u0001\"\u0003BCe\u0005\u0005IQ\tBD\u0011%\u0011iKMA\u0001\n\u0003\u001b9\u0002C\u0005\u00036J\n\t\u0011\"!\u0004\u001c!I!\u0011\u001a\u001a\u0002\u0002\u0013%!1\u001a\u0004\u0007\u0007C\t!ia\t\t\u0015\r\u0015\u0002H!f\u0001\n\u0003\u00199\u0003\u0003\u0006\u0004Ba\u0012\t\u0012)A\u0005\u0007SAq!a\u001c9\t\u0003\u0019\u0019\u0005C\u0005\u0003 a\n\t\u0011\"\u0001\u0004J!I!q\u0005\u001d\u0012\u0002\u0013\u00051Q\n\u0005\n\u0005\u000bB\u0014\u0011!C!\u0003_C\u0011Ba\u00129\u0003\u0003%\tA!\u0013\t\u0013\tE\u0003(!A\u0005\u0002\rE\u0003\"\u0003B0q\u0005\u0005I\u0011\tB1\u0011%\u0011y\u0007OA\u0001\n\u0003\u0019)\u0006C\u0005\u0003|a\n\t\u0011\"\u0011\u0004Z!I!\u0011\u0011\u001d\u0002\u0002\u0013\u0005#1\u0011\u0005\n\u0005\u000bC\u0014\u0011!C!\u0005\u000fC\u0011B!#9\u0003\u0003%\te!\u0018\b\u0013\r\u0005\u0014!!A\t\u0002\r\rd!CB\u0011\u0003\u0005\u0005\t\u0012AB3\u0011\u001d\ty\u0007\u0013C\u0001\u0007SB\u0011B!\"I\u0003\u0003%)Ea\"\t\u0013\t5\u0006*!A\u0005\u0002\u000e-\u0004\"\u0003B[\u0011\u0006\u0005I\u0011QB8\u0011%\u0011I\rSA\u0001\n\u0013\u0011YM\u0002\u0004\u0004v\u000515q\u000f\u0005\u000b\u0007sr%Q3A\u0005\u0002\rm\u0004BCBB\u001d\nE\t\u0015!\u0003\u0004~!9\u0011q\u000e(\u0005\u0002\r\u0015\u0005\"\u0003B\u0010\u001d\u0006\u0005I\u0011ABF\u0011%\u00119CTI\u0001\n\u0003\u0019y\tC\u0005\u0003F9\u000b\t\u0011\"\u0011\u00020\"I!q\t(\u0002\u0002\u0013\u0005!\u0011\n\u0005\n\u0005#r\u0015\u0011!C\u0001\u0007'C\u0011Ba\u0018O\u0003\u0003%\tE!\u0019\t\u0013\t=d*!A\u0005\u0002\r]\u0005\"\u0003B>\u001d\u0006\u0005I\u0011IBN\u0011%\u0011\tITA\u0001\n\u0003\u0012\u0019\tC\u0005\u0003\u0006:\u000b\t\u0011\"\u0011\u0003\b\"I!\u0011\u0012(\u0002\u0002\u0013\u00053qT\u0004\n\u0007G\u000b\u0011\u0011!E\u0005\u0007K3\u0011b!\u001e\u0002\u0003\u0003EIaa*\t\u000f\u0005=d\f\"\u0001\u0004,\"I!Q\u00110\u0002\u0002\u0013\u0015#q\u0011\u0005\n\u0005[s\u0016\u0011!CA\u0007[C\u0011B!._\u0003\u0003%\ti!-\t\u0013\t%g,!A\u0005\n\t-waBB\\\u0003!\u00055\u0011\u0018\u0004\b\u0007w\u000b\u0001\u0012QB_\u0011\u001d\ty'\u001aC\u0001\u0007\u007fC\u0011B!\u0012f\u0003\u0003%\t%a,\t\u0013\t\u001dS-!A\u0005\u0002\t%\u0003\"\u0003B)K\u0006\u0005I\u0011ABa\u0011%\u0011y&ZA\u0001\n\u0003\u0012\t\u0007C\u0005\u0003p\u0015\f\t\u0011\"\u0001\u0004F\"I!\u0011Q3\u0002\u0002\u0013\u0005#1\u0011\u0005\n\u0005\u000b+\u0017\u0011!C!\u0005\u000fC\u0011B!3f\u0003\u0003%IAa3\u0007\r\r%\u0017AQBf\u0011)\u0019im\u001cBK\u0002\u0013\u00051q\u001a\u0005\u000b\u0007/|'\u0011#Q\u0001\n\rE\u0007bBA8_\u0012\u00051\u0011\u001c\u0005\n\u0005?y\u0017\u0011!C\u0001\u0007?D\u0011Ba\np#\u0003%\taa9\t\u0013\t\u0015s.!A\u0005B\u0005=\u0006\"\u0003B$_\u0006\u0005I\u0011\u0001B%\u0011%\u0011\tf\\A\u0001\n\u0003\u00199\u000fC\u0005\u0003`=\f\t\u0011\"\u0011\u0003b!I!qN8\u0002\u0002\u0013\u000511\u001e\u0005\n\u0005wz\u0017\u0011!C!\u0007_D\u0011B!!p\u0003\u0003%\tEa!\t\u0013\t\u0015u.!A\u0005B\t\u001d\u0005\"\u0003BE_\u0006\u0005I\u0011IBz\u000f%\u001990AA\u0001\u0012\u0003\u0019IPB\u0005\u0004J\u0006\t\t\u0011#\u0001\u0004|\"9\u0011qN@\u0005\u0002\r}\b\"\u0003BC\u007f\u0006\u0005IQ\tBD\u0011%\u0011ik`A\u0001\n\u0003#\t\u0001C\u0005\u00036~\f\t\u0011\"!\u0005\u0006!I!\u0011Z@\u0002\u0002\u0013%!1Z\u0004\b\t\u0017\t\u0001\u0012\u0011C\u0007\r\u001d!y!\u0001EA\t#A\u0001\"a\u001c\u0002\u000e\u0011\u0005A1\u0003\u0005\u000b\u0005\u000b\ni!!A\u0005B\u0005=\u0006B\u0003B$\u0003\u001b\t\t\u0011\"\u0001\u0003J!Q!\u0011KA\u0007\u0003\u0003%\t\u0001\"\u0006\t\u0015\t}\u0013QBA\u0001\n\u0003\u0012\t\u0007\u0003\u0006\u0003p\u00055\u0011\u0011!C\u0001\t3A!B!!\u0002\u000e\u0005\u0005I\u0011\tBB\u0011)\u0011))!\u0004\u0002\u0002\u0013\u0005#q\u0011\u0005\u000b\u0005\u0013\fi!!A\u0005\n\t-wa\u0002C\u000f\u0003!\u0005Eq\u0004\u0004\b\tC\t\u0001\u0012\u0011C\u0012\u0011!\ty'a\t\u0005\u0002\u0011\u0015\u0002B\u0003B#\u0003G\t\t\u0011\"\u0011\u00020\"Q!qIA\u0012\u0003\u0003%\tA!\u0013\t\u0015\tE\u00131EA\u0001\n\u0003!9\u0003\u0003\u0006\u0003`\u0005\r\u0012\u0011!C!\u0005CB!Ba\u001c\u0002$\u0005\u0005I\u0011\u0001C\u0016\u0011)\u0011\t)a\t\u0002\u0002\u0013\u0005#1\u0011\u0005\u000b\u0005\u000b\u000b\u0019#!A\u0005B\t\u001d\u0005B\u0003Be\u0003G\t\t\u0011\"\u0003\u0003L\u001aI\u0011qLA(\u0001\u0005]CQ\b\u0005\f\u0003'\u000b9D!A!\u0002\u0013\t)\n\u0003\u0005\u0002p\u0005]B\u0011\u0001C#\u0011)!Y%a\u000eC\u0002\u0013\rAQ\n\u0005\n\t/\n9\u0004)A\u0005\t\u001fB\u0001\u0002\"\u0017\u00028\u0011\u0005C1\f\u0005\u000b\tG\n9\u00041A\u0005\n\r=\u0007B\u0003C3\u0003o\u0001\r\u0011\"\u0003\u0005h!IA\u0011OA\u001cA\u0003&1\u0011\u001b\u0005\t\tg\n9\u0004\"\u0001\u0005v!AAqPA\u001c\t\u0003\"\t)\u0001\tTiJ,\u0017-\\*va\u0016\u0014h/[:pe*!\u0011\u0011KA*\u0003\u0011IW\u000e\u001d7\u000b\t\u0005U\u0013qK\u0001\u0007gR\u0014X-Y7\u000b\u0005\u0005e\u0013\u0001B1lW\u0006\u00042!!\u0018\u0002\u001b\t\tyE\u0001\tTiJ,\u0017-\\*va\u0016\u0014h/[:peN\u0019\u0011!a\u0019\u0011\t\u0005\u0015\u00141N\u0007\u0003\u0003OR!!!\u001b\u0002\u000bM\u001c\u0017\r\\1\n\t\u00055\u0014q\r\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\"!a\u0017\u0002\u000bA\u0014x\u000e]:\u0015\r\u0005e\u0014QQAI!\u0011\tY(!!\u000e\u0005\u0005u$\u0002BA@\u0003/\nQ!Y2u_JLA!a!\u0002~\t)\u0001K]8qg\"9\u0011qQ\u0002A\u0002\u0005%\u0015AC1uiJL'-\u001e;fgB!\u00111RAG\u001b\t\t\u0019&\u0003\u0003\u0002\u0010\u0006M#AC!uiJL'-\u001e;fg\"9\u00111S\u0002A\u0002\u0005U\u0015\u0001\u00045bm\u0016\u001c\u0006.\u001e;E_^t\u0007\u0003BAL\u0003Sk!!!'\u000b\t\u0005m\u0015QT\u0001\u0007CR|W.[2\u000b\t\u0005}\u0015\u0011U\u0001\u000bG>t7-\u001e:sK:$(\u0002BAR\u0003K\u000bA!\u001e;jY*\u0011\u0011qU\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002,\u0006e%!D!u_6L7MQ8pY\u0016\fg.\u0001\u0005cCN,g*Y7f+\t\t\t\f\u0005\u0003\u00024\u0006eVBAA[\u0015\u0011\t9,!*\u0002\t1\fgnZ\u0005\u0005\u0003w\u000b)L\u0001\u0004TiJLgnZ\u0001\nE\u0006\u001cXMT1nK\u0002\n\u0011\"Y2u_Jt\u0015-\\3\u0016\u0005\u0005\r\u0007\u0003BA/\u0003\u000bLA!a2\u0002P\t\u00012+Z9BGR|'OT1nK&k\u0007\u000f\\\u0001\u000bC\u000e$xN\u001d(b[\u0016\u0004\u0013\u0001\u00038fqRt\u0015-\\3\u0015\u0005\u0005=\u0007\u0003BAi\u0003?tA!a5\u0002\\B!\u0011Q[A4\u001b\t\t9N\u0003\u0003\u0002Z\u0006E\u0014A\u0002\u001fs_>$h(\u0003\u0003\u0002^\u0006\u001d\u0014A\u0002)sK\u0012,g-\u0003\u0003\u0002<\u0006\u0005(\u0002BAo\u0003O\u00121\"T1uKJL\u0017\r\\5{KNY\u0011\"a\u0019\u0002h\u00065\u00181_A}!\u0011\tY(!;\n\t\u0005-\u0018Q\u0010\u0002\u0016\t\u0016\fG\rT3ui\u0016\u00148+\u001e9qe\u0016\u001c8/[8o!\u0011\tY(a<\n\t\u0005E\u0018Q\u0010\u0002\"\u001d>\u001cVM]5bY&T\u0018\r^5p]Z+'/\u001b4jG\u0006$\u0018n\u001c8OK\u0016$W\r\u001a\t\u0005\u0003K\n)0\u0003\u0003\u0002x\u0006\u001d$a\u0002)s_\u0012,8\r\u001e\t\u0005\u0003w\u0014)A\u0004\u0003\u0002~\n\u0005a\u0002BAk\u0003\u007fL!!!\u001b\n\t\t\r\u0011qM\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\u00119A!\u0003\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\t\t\r\u0011qM\u000b\u0003\u0003s\na\u0001\u001d:paN\u0004\u0013\u0001\u00028b[\u0016,\"!a4\u0002\u000b9\fW.\u001a\u0011\u0015\r\t]!1\u0004B\u000f!\r\u0011I\"C\u0007\u0002\u0003!9\u0011Q\u000f\bA\u0002\u0005e\u0004b\u0002B\b\u001d\u0001\u0007\u0011qZ\u0001\u0005G>\u0004\u0018\u0010\u0006\u0004\u0003\u0018\t\r\"Q\u0005\u0005\n\u0003kz\u0001\u0013!a\u0001\u0003sB\u0011Ba\u0004\u0010!\u0003\u0005\r!a4\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011!1\u0006\u0016\u0005\u0003s\u0012ic\u000b\u0002\u00030A!!\u0011\u0007B\u001e\u001b\t\u0011\u0019D\u0003\u0003\u00036\t]\u0012!C;oG\",7m[3e\u0015\u0011\u0011I$a\u001a\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0003>\tM\"!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012TC\u0001B\"U\u0011\tyM!\f\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\t\u0011Y\u0005\u0005\u0003\u0002f\t5\u0013\u0002\u0002B(\u0003O\u00121!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$BA!\u0016\u0003\\A!\u0011Q\rB,\u0013\u0011\u0011I&a\u001a\u0003\u0007\u0005s\u0017\u0010C\u0005\u0003^Q\t\t\u00111\u0001\u0003L\u0005\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"Aa\u0019\u0011\r\t\u0015$1\u000eB+\u001b\t\u00119G\u0003\u0003\u0003j\u0005\u001d\u0014AC2pY2,7\r^5p]&!!Q\u000eB4\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\tM$\u0011\u0010\t\u0005\u0003K\u0012)(\u0003\u0003\u0003x\u0005\u001d$a\u0002\"p_2,\u0017M\u001c\u0005\n\u0005;2\u0012\u0011!a\u0001\u0005+\n!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011\u0011\u0017B@\u0011%\u0011ifFA\u0001\u0002\u0004\u0011Y%\u0001\u0005iCND7i\u001c3f)\t\u0011Y%\u0001\u0005u_N#(/\u001b8h)\t\t\t,\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0005g\u0012i\tC\u0005\u0003^i\t\t\u00111\u0001\u0003V\u0005YQ*\u0019;fe&\fG.\u001b>f!\r\u0011I\u0002H\n\u00069\tU%\u0011\u0015\t\u000b\u0005/\u0013i*!\u001f\u0002P\n]QB\u0001BM\u0015\u0011\u0011Y*a\u001a\u0002\u000fI,h\u000e^5nK&!!q\u0014BM\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|gN\r\t\u0005\u0005G\u0013I+\u0004\u0002\u0003&*!!qUAS\u0003\tIw.\u0003\u0003\u0003\b\t\u0015FC\u0001BI\u0003\u0015\t\u0007\u000f\u001d7z)\u0019\u00119B!-\u00034\"9\u0011QO\u0010A\u0002\u0005e\u0004b\u0002B\b?\u0001\u0007\u0011qZ\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\u0011IL!2\u0011\r\u0005\u0015$1\u0018B`\u0013\u0011\u0011i,a\u001a\u0003\r=\u0003H/[8o!!\t)G!1\u0002z\u0005=\u0017\u0002\u0002Bb\u0003O\u0012a\u0001V;qY\u0016\u0014\u0004\"\u0003BdA\u0005\u0005\t\u0019\u0001B\f\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0005\u001b\u0004B!a-\u0003P&!!\u0011[A[\u0005\u0019y%M[3di\n!r)\u001a;DQ&dGM]3o':\f\u0007o\u001d5piN\u001crAIA2\u0003g\fI0A\u0004uS6,w.\u001e;\u0016\u0005\tm\u0007\u0003\u0002Bo\u0005Kl!Aa8\u000b\t\t\u0005(1]\u0001\tIV\u0014\u0018\r^5p]*!\u0011qTA4\u0013\u0011\u00119Oa8\u0003\u001d\u0019Kg.\u001b;f\tV\u0014\u0018\r^5p]\u0006AA/[7f_V$\b\u0005\u0006\u0003\u0003n\n=\bc\u0001B\rE!9!q[\u0013A\u0002\tmG\u0003\u0002Bw\u0005gD\u0011Ba6'!\u0003\u0005\rAa7\u0016\u0005\t](\u0006\u0002Bn\u0005[!BA!\u0016\u0003|\"I!Q\f\u0016\u0002\u0002\u0003\u0007!1\n\u000b\u0005\u0005g\u0012y\u0010C\u0005\u0003^1\n\t\u00111\u0001\u0003VQ!\u0011\u0011WB\u0002\u0011%\u0011i&LA\u0001\u0002\u0004\u0011Y\u0005\u0006\u0003\u0003t\r\u001d\u0001\"\u0003B/a\u0005\u0005\t\u0019\u0001B+\u0003Q9U\r^\"iS2$'/\u001a8T]\u0006\u00048\u000f[8ugB\u0019!\u0011\u0004\u001a\u0014\u000bI\u001ayA!)\u0011\u0011\t]5\u0011\u0003Bn\u0005[LAaa\u0005\u0003\u001a\n\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u0019\u0015\u0005\r-A\u0003\u0002Bw\u00073AqAa66\u0001\u0004\u0011Y\u000e\u0006\u0003\u0004\u001e\r}\u0001CBA3\u0005w\u0013Y\u000eC\u0005\u0003HZ\n\t\u00111\u0001\u0003n\n\t2\t[5mIJ,gn\u00158baNDw\u000e^:\u0014\u0017a\n\u0019'a:\u0002n\u0006M\u0018\u0011`\u0001\u0004g\u0016\fXCAB\u0015!\u0019\u0019Yc!\r\u000465\u00111Q\u0006\u0006\u0005\u0007_\u00119'A\u0005j[6,H/\u00192mK&!11GB\u0017\u0005\r\u0019V-\u001d\t\u0005\u0007o\u0019i$\u0004\u0002\u0004:)!11HA*\u0003!\u0019h.\u00199tQ>$\u0018\u0002BB \u0007s\u0011ab\u0015;sK\u0006l7K\\1qg\"|G/\u0001\u0003tKF\u0004C\u0003BB#\u0007\u000f\u00022A!\u00079\u0011\u001d\u0019)c\u000fa\u0001\u0007S!Ba!\u0012\u0004L!I1Q\u0005\u001f\u0011\u0002\u0003\u00071\u0011F\u000b\u0003\u0007\u001fRCa!\u000b\u0003.Q!!QKB*\u0011%\u0011i\u0006QA\u0001\u0002\u0004\u0011Y\u0005\u0006\u0003\u0003t\r]\u0003\"\u0003B/\u0005\u0006\u0005\t\u0019\u0001B+)\u0011\t\tla\u0017\t\u0013\tu3)!AA\u0002\t-C\u0003\u0002B:\u0007?B\u0011B!\u0018G\u0003\u0003\u0005\rA!\u0016\u0002#\rC\u0017\u000e\u001c3sK:\u001cf.\u00199tQ>$8\u000fE\u0002\u0003\u001a!\u001bR\u0001SB4\u0005C\u0003\u0002Ba&\u0004\u0012\r%2Q\t\u000b\u0003\u0007G\"Ba!\u0012\u0004n!91QE&A\u0002\r%B\u0003BB9\u0007g\u0002b!!\u001a\u0003<\u000e%\u0002\"\u0003Bd\u0019\u0006\u0005\t\u0019AB#\u0005I\u0019u\u000e\u001c7fGR|'oQ8na2,G/\u001a3\u0014\u000f9\u000b\u0019'a=\u0002z\u0006\u0019!/\u001a4\u0016\u0005\ru\u0004\u0003BA>\u0007\u007fJAa!!\u0002~\tA\u0011i\u0019;peJ+g-\u0001\u0003sK\u001a\u0004C\u0003BBD\u0007\u0013\u00032A!\u0007O\u0011\u001d\u0019I(\u0015a\u0001\u0007{\"Baa\"\u0004\u000e\"I1\u0011\u0010*\u0011\u0002\u0003\u00071QP\u000b\u0003\u0007#SCa! \u0003.Q!!QKBK\u0011%\u0011iFVA\u0001\u0002\u0004\u0011Y\u0005\u0006\u0003\u0003t\re\u0005\"\u0003B/1\u0006\u0005\t\u0019\u0001B+)\u0011\t\tl!(\t\u0013\tu\u0013,!AA\u0002\t-C\u0003\u0002B:\u0007CC\u0011B!\u0018]\u0003\u0003\u0005\rA!\u0016\u0002%\r{G\u000e\\3di>\u00148i\\7qY\u0016$X\r\u001a\t\u0004\u00053q6#\u00020\u0004*\n\u0005\u0006\u0003\u0003BL\u0007#\u0019iha\"\u0015\u0005\r\u0015F\u0003BBD\u0007_Cqa!\u001fb\u0001\u0004\u0019i\b\u0006\u0003\u00044\u000eU\u0006CBA3\u0005w\u001bi\bC\u0005\u0003H\n\f\t\u00111\u0001\u0004\b\u0006Yq)\u001a;DQ&dGM]3o!\r\u0011I\"\u001a\u0002\f\u000f\u0016$8\t[5mIJ,gnE\u0004f\u0003G\n\u00190!?\u0015\u0005\reF\u0003\u0002B+\u0007\u0007D\u0011B!\u0018j\u0003\u0003\u0005\rAa\u0013\u0015\t\tM4q\u0019\u0005\n\u0005;Z\u0017\u0011!a\u0001\u0005+\u0012\u0001b\u00115jY\u0012\u0014XM\\\n\b_\u0006\r\u00141_A}\u0003!\u0019\u0007.\u001b7ee\u0016tWCABi!\u0019\t\tna5\u0004~%!1Q[Aq\u0005\r\u0019V\r^\u0001\nG\"LG\u000e\u001a:f]\u0002\"Baa7\u0004^B\u0019!\u0011D8\t\u000f\r5'\u000f1\u0001\u0004RR!11\\Bq\u0011%\u0019im\u001dI\u0001\u0002\u0004\u0019\t.\u0006\u0002\u0004f*\"1\u0011\u001bB\u0017)\u0011\u0011)f!;\t\u0013\tus/!AA\u0002\t-C\u0003\u0002B:\u0007[D\u0011B!\u0018z\u0003\u0003\u0005\rA!\u0016\u0015\t\u0005E6\u0011\u001f\u0005\n\u0005;R\u0018\u0011!a\u0001\u0005\u0017\"BAa\u001d\u0004v\"I!QL?\u0002\u0002\u0003\u0007!QK\u0001\t\u0007\"LG\u000e\u001a:f]B\u0019!\u0011D@\u0014\u000b}\u001ciP!)\u0011\u0011\t]5\u0011CBi\u00077$\"a!?\u0015\t\rmG1\u0001\u0005\t\u0007\u001b\f)\u00011\u0001\u0004RR!Aq\u0001C\u0005!\u0019\t)Ga/\u0004R\"Q!qYA\u0004\u0003\u0003\u0005\raa7\u0002\u0019M#x\u000e]\"iS2$'/\u001a8\u0011\t\te\u0011Q\u0002\u0002\r'R|\u0007o\u00115jY\u0012\u0014XM\\\n\t\u0003\u001b\t\u0019'a=\u0002zR\u0011AQ\u0002\u000b\u0005\u0005+\"9\u0002\u0003\u0006\u0003^\u0005U\u0011\u0011!a\u0001\u0005\u0017\"BAa\u001d\u0005\u001c!Q!QLA\r\u0003\u0003\u0005\rA!\u0016\u0002\u001fM#x\u000e\u001d9fI\u000eC\u0017\u000e\u001c3sK:\u0004BA!\u0007\u0002$\ty1\u000b^8qa\u0016$7\t[5mIJ,gn\u0005\u0005\u0002$\u0005\r\u00141_A})\t!y\u0002\u0006\u0003\u0003V\u0011%\u0002B\u0003B/\u0003W\t\t\u00111\u0001\u0003LQ!!1\u000fC\u0017\u0011)\u0011i&a\f\u0002\u0002\u0003\u0007!Q\u000b\u0015\u0004\u0003\u0011E\u0002\u0003\u0002C\u001a\toi!\u0001\"\u000e\u000b\t\te\u0012qK\u0005\u0005\ts!)DA\u0006J]R,'O\\1m\u0003BL\u0007f\u0001\u0001\u00052M1\u0011qGA2\t\u007f\u0001B!a\u001f\u0005B%!A1IA?\u0005\u0015\t5\r^8s)\u0011!9\u0005\"\u0013\u0011\t\u0005u\u0013q\u0007\u0005\t\u0003'\u000bY\u00041\u0001\u0002\u0016\u0006\u0011QmY\u000b\u0003\t\u001f\u0002B\u0001\"\u0015\u0005T5\u0011!1]\u0005\u0005\t+\u0012\u0019O\u0001\rFq\u0016\u001cW\u000f^5p]\u000e{g\u000e^3yi\u0016CXmY;u_J\f1!Z2!\u0003I\u0019X\u000f]3sm&\u001cxN]*ue\u0006$XmZ=\u0016\u0005\u0011u\u0003\u0003BA>\t?JA\u0001\"\u0019\u0002~\t\u00112+\u001e9feZL7o\u001c:TiJ\fG/Z4z\u0003I\u0019h.\u00199tQ>$8i\u001c7mK\u000e$xN]:\u0002-Mt\u0017\r]:i_R\u001cu\u000e\u001c7fGR|'o]0%KF$B\u0001\"\u001b\u0005pA!\u0011Q\rC6\u0013\u0011!i'a\u001a\u0003\tUs\u0017\u000e\u001e\u0005\u000b\u0005;\n)%!AA\u0002\rE\u0017aE:oCB\u001c\bn\u001c;D_2dWm\u0019;peN\u0004\u0013a\u0002:fG\u0016Lg/Z\u000b\u0003\to\u0002B\u0001\"\u001f\u0005|5\u0011\u0011qG\u0005\u0005\t{\"\tEA\u0004SK\u000e,\u0017N^3\u0002\u0011A|7\u000f^*u_B$\"\u0001\"\u001b)\t\u0005]B\u0011\u0007")
@InternalApi
public class StreamSupervisor
implements Actor {
    private final AtomicBoolean haveShutDown;
    private final ExecutionContextExecutor ec;
    private Set<ActorRef> akka$stream$impl$StreamSupervisor$$snapshotCollectors;
    private ActorContext context;
    private ActorRef self;

    public static String nextName() {
        return StreamSupervisor$.MODULE$.nextName();
    }

    public static Props props(Attributes attributes, AtomicBoolean haveShutDown) {
        return StreamSupervisor$.MODULE$.props(attributes, haveShutDown);
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
    public void preStart() throws Exception {
        Actor.preStart$(this);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        Actor.preRestart$(this, reason, message);
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

    public ExecutionContextExecutor ec() {
        return this.ec;
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return SupervisorStrategy$.MODULE$.stoppingStrategy();
    }

    public Set<ActorRef> akka$stream$impl$StreamSupervisor$$snapshotCollectors() {
        return this.akka$stream$impl$StreamSupervisor$$snapshotCollectors;
    }

    public void akka$stream$impl$StreamSupervisor$$snapshotCollectors_$eq(Set<ActorRef> x$1) {
        this.akka$stream$impl$StreamSupervisor$$snapshotCollectors = x$1;
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ StreamSupervisor $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Materialize) {
                    Materialize materialize = (Materialize)A1;
                    Props props = materialize.props();
                    String name = materialize.name();
                    ActorRef impl = this.$outer.context().actorOf(props, name);
                    this.$outer.sender().$bang(impl, this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                if (StreamSupervisor$GetChildren$.MODULE$.equals(A1)) {
                    this.$outer.sender().$bang(new Children(this.$outer.context().children().toSet()), this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof GetChildrenSnapshots) {
                    GetChildrenSnapshots getChildrenSnapshots = (GetChildrenSnapshots)A1;
                    FiniteDuration timeout2 = getChildrenSnapshots.timeout();
                    ActorRef collector = this.$outer.context().actorOf(SnapshotCollector$.MODULE$.props((Set)this.$outer.context().children().toSet().$minus$minus(this.$outer.akka$stream$impl$StreamSupervisor$$snapshotCollectors()), timeout2, this.$outer.sender()).withDispatcher(this.$outer.context().props().dispatcher()));
                    this.$outer.context().watchWith(collector, new CollectorCompleted(collector));
                    this.$outer.akka$stream$impl$StreamSupervisor$$snapshotCollectors_$eq((Set)this.$outer.akka$stream$impl$StreamSupervisor$$snapshotCollectors().$plus(collector));
                    return (B1)BoxedUnit.UNIT;
                }
                if (StreamSupervisor$StopChildren$.MODULE$.equals(A1)) {
                    this.$outer.context().children().foreach((Function1<ActorRef, Object> & Serializable)actor -> {
                        $anonfun$receive$1.$anonfun$applyOrElse$1(this, actor);
                        return BoxedUnit.UNIT;
                    });
                    this.$outer.sender().$bang(StreamSupervisor$StoppedChildren$.MODULE$, this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof CollectorCompleted) {
                    CollectorCompleted collectorCompleted = (CollectorCompleted)A1;
                    ActorRef collector = collectorCompleted.ref();
                    this.$outer.akka$stream$impl$StreamSupervisor$$snapshotCollectors_$eq((Set)this.$outer.akka$stream$impl$StreamSupervisor$$snapshotCollectors().$minus(collector));
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof Materialize) {
                    return true;
                }
                if (StreamSupervisor$GetChildren$.MODULE$.equals(object)) {
                    return true;
                }
                if (object instanceof GetChildrenSnapshots) {
                    return true;
                }
                if (StreamSupervisor$StopChildren$.MODULE$.equals(object)) {
                    return true;
                }
                return object instanceof CollectorCompleted;
            }

            public static final /* synthetic */ void $anonfun$applyOrElse$1($anonfun$receive$1 $this, ActorRef actor) {
                $this.$outer.context().stop(actor);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1$adapted(akka.stream.impl.StreamSupervisor$$anonfun$receive$1 akka.actor.ActorRef )}, serializedLambda);
            }
        };
    }

    @Override
    public void postStop() {
        this.haveShutDown.set(true);
    }

    public StreamSupervisor(AtomicBoolean haveShutDown) {
        this.haveShutDown = haveShutDown;
        Actor.$init$(this);
        this.ec = this.context().dispatcher();
        this.akka$stream$impl$StreamSupervisor$$snapshotCollectors = Predef$.MODULE$.Set().empty();
        Statics.releaseFence();
    }

    public static final class Children
    implements Product,
    Serializable {
        private final Set<ActorRef> children;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Set<ActorRef> children() {
            return this.children;
        }

        public Children copy(Set<ActorRef> children) {
            return new Children(children);
        }

        public Set<ActorRef> copy$default$1() {
            return this.children();
        }

        @Override
        public String productPrefix() {
            return "Children";
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
                    return this.children();
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
            return x$1 instanceof Children;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "children";
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
            if (!(object instanceof Children)) return false;
            boolean bl = true;
            if (!bl) return false;
            Children children = (Children)x$1;
            Set<ActorRef> set = this.children();
            Set<ActorRef> set2 = children.children();
            if (set != null) {
                if (!set.equals(set2)) return false;
                return true;
            }
            if (set2 == null) return true;
            return false;
        }

        public Children(Set<ActorRef> children) {
            this.children = children;
            Product.$init$(this);
        }
    }

    public static final class ChildrenSnapshots
    implements DeadLetterSuppression,
    NoSerializationVerificationNeeded,
    Product,
    Serializable {
        private final Seq<StreamSnapshot> seq;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Seq<StreamSnapshot> seq() {
            return this.seq;
        }

        public ChildrenSnapshots copy(Seq<StreamSnapshot> seq) {
            return new ChildrenSnapshots(seq);
        }

        public Seq<StreamSnapshot> copy$default$1() {
            return this.seq();
        }

        @Override
        public String productPrefix() {
            return "ChildrenSnapshots";
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
                    return this.seq();
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
            return x$1 instanceof ChildrenSnapshots;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "seq";
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
            if (!(object instanceof ChildrenSnapshots)) return false;
            boolean bl = true;
            if (!bl) return false;
            ChildrenSnapshots childrenSnapshots = (ChildrenSnapshots)x$1;
            Seq<StreamSnapshot> seq = this.seq();
            Seq<StreamSnapshot> seq2 = childrenSnapshots.seq();
            if (seq != null) {
                if (!seq.equals(seq2)) return false;
                return true;
            }
            if (seq2 == null) return true;
            return false;
        }

        public ChildrenSnapshots(Seq<StreamSnapshot> seq) {
            this.seq = seq;
            Product.$init$(this);
        }
    }

    private static final class CollectorCompleted
    implements Product,
    Serializable {
        private final ActorRef ref;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ActorRef ref() {
            return this.ref;
        }

        public CollectorCompleted copy(ActorRef ref) {
            return new CollectorCompleted(ref);
        }

        public ActorRef copy$default$1() {
            return this.ref();
        }

        @Override
        public String productPrefix() {
            return "CollectorCompleted";
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
                    return this.ref();
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
            return x$1 instanceof CollectorCompleted;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "ref";
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
            if (!(object instanceof CollectorCompleted)) return false;
            boolean bl = true;
            if (!bl) return false;
            CollectorCompleted collectorCompleted = (CollectorCompleted)x$1;
            ActorRef actorRef2 = this.ref();
            ActorRef actorRef3 = collectorCompleted.ref();
            if (actorRef2 != null) {
                if (!((Object)actorRef2).equals(actorRef3)) return false;
                return true;
            }
            if (actorRef3 == null) return true;
            return false;
        }

        public CollectorCompleted(ActorRef ref) {
            this.ref = ref;
            Product.$init$(this);
        }
    }

    public static final class GetChildrenSnapshots
    implements Product,
    Serializable {
        private final FiniteDuration timeout;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public FiniteDuration timeout() {
            return this.timeout;
        }

        public GetChildrenSnapshots copy(FiniteDuration timeout2) {
            return new GetChildrenSnapshots(timeout2);
        }

        public FiniteDuration copy$default$1() {
            return this.timeout();
        }

        @Override
        public String productPrefix() {
            return "GetChildrenSnapshots";
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
                    return this.timeout();
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
            return x$1 instanceof GetChildrenSnapshots;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "timeout";
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
            if (!(object instanceof GetChildrenSnapshots)) return false;
            boolean bl = true;
            if (!bl) return false;
            GetChildrenSnapshots getChildrenSnapshots = (GetChildrenSnapshots)x$1;
            FiniteDuration finiteDuration = this.timeout();
            FiniteDuration finiteDuration2 = getChildrenSnapshots.timeout();
            if (finiteDuration != null) {
                if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
                return true;
            }
            if (finiteDuration2 == null) return true;
            return false;
        }

        public GetChildrenSnapshots(FiniteDuration timeout2) {
            this.timeout = timeout2;
            Product.$init$(this);
        }
    }

    public static final class Materialize
    implements DeadLetterSuppression,
    NoSerializationVerificationNeeded,
    Product,
    Serializable {
        private final Props props;
        private final String name;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Props props() {
            return this.props;
        }

        public String name() {
            return this.name;
        }

        public Materialize copy(Props props, String name) {
            return new Materialize(props, name);
        }

        public Props copy$default$1() {
            return this.props();
        }

        public String copy$default$2() {
            return this.name();
        }

        @Override
        public String productPrefix() {
            return "Materialize";
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
                    return this.props();
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
            return x$1 instanceof Materialize;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "props";
                }
                case 1: {
                    return "name";
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
            if (!(object instanceof Materialize)) return false;
            boolean bl = true;
            if (!bl) return false;
            Materialize materialize = (Materialize)x$1;
            Props props = this.props();
            Props props2 = materialize.props();
            if (props == null) {
                if (props2 != null) {
                    return false;
                }
            } else if (!((Object)props).equals(props2)) return false;
            String string = this.name();
            String string2 = materialize.name();
            if (string == null) {
                if (string2 == null) return true;
                return false;
            } else {
                if (!string.equals(string2)) return false;
                return true;
            }
        }

        public Materialize(Props props, String name) {
            this.props = props;
            this.name = name;
            Product.$init$(this);
        }
    }
}

