/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.io.Inet;
import akka.io.SelectionHandler;
import akka.io.SelectionHandlerSettings;
import akka.io.Udp$;
import akka.io.UdpExt;
import akka.util.ByteString;
import akka.util.Helpers$;
import akka.util.Helpers$Requiring$;
import com.typesafe.config.Config;
import java.io.Serializable;
import java.net.InetSocketAddress;
import scala.Function0;
import scala.Predef$;
import scala.Product;
import scala.collection.Iterator;
import scala.collection.immutable.Iterable;
import scala.math.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0019\u001dw\u0001\u0003B\u001c\u0005sA\tAa\u0011\u0007\u0011\t\u001d#\u0011\bE\u0001\u0005\u0013BqAa\u001c\u0002\t\u0003\u0011\t\bC\u0004\u0003t\u0005!\tE!\u001e\t\u000f\t]\u0014\u0001\"\u0011\u0003z!9!QQ\u0001\u0005B\t\u001d\u0005b\u0002BC\u0003\u0011\u0005#\u0011\u0013\u0004\n\u00057\u000b\u0001\u0013aI\u0011\u0005;3\u0011B!)\u0002!\u0003\r\tAa)\t\u000f\t]\u0006\u0002\"\u0001\u0003:\"9!\u0011\u0019\u0005\u0005\u0002\t\rgABB;\u0003\u0001\u001b9\b\u0003\u0006\u0004z-\u0011)\u001a!C\u0001\u0007wB!b! \f\u0005#\u0005\u000b\u0011BB\u001e\u0011\u001d\u0011yg\u0003C\u0001\u0007\u007fB\u0011Ba?\f\u0003\u0003%\ta!\"\t\u0013\r\u00051\"%A\u0005\u0002\r%\u0005\"CB\r\u0017\u0005\u0005I\u0011IB\u000e\u0011%\u0019icCA\u0001\n\u0003\u0019y\u0003C\u0005\u00048-\t\t\u0011\"\u0001\u0004\u000e\"I1QI\u0006\u0002\u0002\u0013\u00053q\t\u0005\n\u0007+Z\u0011\u0011!C\u0001\u0007#C\u0011b!\u0019\f\u0003\u0003%\te!&\t\u0013\r\u001d4\"!A\u0005B\r%\u0004\"CB6\u0017\u0005\u0005I\u0011IB7\u0011%\u0019ygCA\u0001\n\u0003\u001aIjB\u0004\u0004\u001e\u0006A\taa(\u0007\u000f\rU\u0014\u0001#\u0001\u0004\"\"9!qN\u000e\u0005\u0002\r\r\u0006\"CBS7\u0005\u0005I\u0011QBT\u0011%\u0019YkGA\u0001\n\u0003\u001bi\u000bC\u0005\u0004:n\t\t\u0011\"\u0003\u0004<\u001a111Y\u0001C\u0007\u000bD!ba2!\u0005+\u0007I\u0011ABe\u0011)\u00199\u000e\tB\tB\u0003%11\u001a\u0005\u000b\u00073\u0004#Q3A\u0005\u0002\rm\u0007BCBuA\tE\t\u0015!\u0003\u0004^\"Q11\u001e\u0011\u0003\u0016\u0004%\ta!<\t\u0015\r=\bE!E!\u0002\u0013\u0011Y\rC\u0004\u0003p\u0001\"\ta!=\t\u000f\rm\b\u0005\"\u0001\u0004~\"I!1 \u0011\u0002\u0002\u0013\u00051q \u0005\n\u0007\u0003\u0001\u0013\u0013!C\u0001\t\u000fA\u0011\u0002b\u0003!#\u0003%\t\u0001\"\u0004\t\u0013\u0011E\u0001%%A\u0005\u0002\u0011M\u0001\"CB\rA\u0005\u0005I\u0011IB\u000e\u0011%\u0019i\u0003IA\u0001\n\u0003\u0019y\u0003C\u0005\u00048\u0001\n\t\u0011\"\u0001\u0005\u0018!I1Q\t\u0011\u0002\u0002\u0013\u00053q\t\u0005\n\u0007+\u0002\u0013\u0011!C\u0001\t7A\u0011b!\u0019!\u0003\u0003%\t\u0005b\b\t\u0013\r\u001d\u0004%!A\u0005B\r%\u0004\"CB6A\u0005\u0005I\u0011IB7\u0011%\u0019y\u0007IA\u0001\n\u0003\"\u0019cB\u0004\u0005(\u0005A\t\u0001\"\u000b\u0007\u000f\r\r\u0017\u0001#\u0001\u0005,!9!qN\u001c\u0005\u0002\u0011U\u0002bBBSo\u0011\u0005Aq\u0007\u0005\n\u0007K;\u0014\u0011!CA\t\u007fA\u0011ba+8\u0003\u0003%\t\tb\u0012\t\u0013\rev'!A\u0005\n\rmfA\u0002C*\u0003\t#)\u0006\u0003\u0006\u0005Xu\u0012)\u001a!C\u0001\t3B!\u0002\"\u0019>\u0005#\u0005\u000b\u0011\u0002C.\u0011)!\u0019'\u0010BK\u0002\u0013\u000511\u001c\u0005\u000b\tKj$\u0011#Q\u0001\n\ru\u0007B\u0003C4{\tU\r\u0011\"\u0001\u0005j!QAQS\u001f\u0003\u0012\u0003\u0006I\u0001b\u001b\t\u000f\t=T\b\"\u0001\u0005\u0018\"I!1`\u001f\u0002\u0002\u0013\u0005A\u0011\u0015\u0005\n\u0007\u0003i\u0014\u0013!C\u0001\tSC\u0011\u0002b\u0003>#\u0003%\t\u0001\"\u0004\t\u0013\u0011EQ(%A\u0005\u0002\u00115\u0006\"CB\r{\u0005\u0005I\u0011IB\u000e\u0011%\u0019i#PA\u0001\n\u0003\u0019y\u0003C\u0005\u00048u\n\t\u0011\"\u0001\u00052\"I1QI\u001f\u0002\u0002\u0013\u00053q\t\u0005\n\u0007+j\u0014\u0011!C\u0001\tkC\u0011b!\u0019>\u0003\u0003%\t\u0005\"/\t\u0013\r\u001dT(!A\u0005B\r%\u0004\"CB6{\u0005\u0005I\u0011IB7\u0011%\u0019y'PA\u0001\n\u0003\"ilB\u0005\u0005B\u0006\t\t\u0011#\u0001\u0005D\u001aIA1K\u0001\u0002\u0002#\u0005AQ\u0019\u0005\b\u0005_\u001aF\u0011\u0001Cj\u0011%\u0019YgUA\u0001\n\u000b\u001ai\u0007C\u0005\u0004&N\u000b\t\u0011\"!\u0005V\"IAQ\\*\u0012\u0002\u0013\u0005AQ\u0016\u0005\n\u0007W\u001b\u0016\u0011!CA\t?D\u0011\u0002b:T#\u0003%\t\u0001\",\t\u0013\re6+!A\u0005\n\rmva\u0002Cu\u0003!\u0005E1\u001e\u0004\b\t[\f\u0001\u0012\u0011Cx\u0011\u001d\u0011y\u0007\u0018C\u0001\tcD\u0011b!\u0007]\u0003\u0003%\tea\u0007\t\u0013\r5B,!A\u0005\u0002\r=\u0002\"CB\u001c9\u0006\u0005I\u0011\u0001Cz\u0011%\u0019)\u0005XA\u0001\n\u0003\u001a9\u0005C\u0005\u0004Vq\u000b\t\u0011\"\u0001\u0005x\"I1q\r/\u0002\u0002\u0013\u00053\u0011\u000e\u0005\n\u0007Wb\u0016\u0011!C!\u0007[B\u0011b!/]\u0003\u0003%Iaa/\u0007\r\u0011m\u0018\u0001\u0011C\u007f\u0011)!9G\u001aBK\u0002\u0013\u0005A\u0011\u000e\u0005\u000b\t+3'\u0011#Q\u0001\n\u0011-\u0004b\u0002B8M\u0012\u0005Aq \u0005\n\u0005w4\u0017\u0011!C\u0001\u000b\u000bA\u0011b!\u0001g#\u0003%\t\u0001\",\t\u0013\rea-!A\u0005B\rm\u0001\"CB\u0017M\u0006\u0005I\u0011AB\u0018\u0011%\u00199DZA\u0001\n\u0003)I\u0001C\u0005\u0004F\u0019\f\t\u0011\"\u0011\u0004H!I1Q\u000b4\u0002\u0002\u0013\u0005QQ\u0002\u0005\n\u0007C2\u0017\u0011!C!\u000b#A\u0011ba\u001ag\u0003\u0003%\te!\u001b\t\u0013\r-d-!A\u0005B\r5\u0004\"CB8M\u0006\u0005I\u0011IC\u000b\u000f\u001d)I\"\u0001E\u0001\u000b71q\u0001b?\u0002\u0011\u0003)i\u0002C\u0004\u0003pY$\t!b\b\t\u0013\r\u0015f/!A\u0005\u0002\u0016\u0005\u0002\"CC\u0013mF\u0005I\u0011\u0001CW\u0011%\u0019YK^A\u0001\n\u0003+9\u0003C\u0005\u0006.Y\f\n\u0011\"\u0001\u0005.\"I1\u0011\u0018<\u0002\u0002\u0013%11X\u0004\b\u000b_\t\u0001\u0012QC\u0019\r\u001d)\u0019$\u0001EA\u000bkAqAa\u001c\u007f\t\u0003)9\u0004C\u0005\u0004\u001ay\f\t\u0011\"\u0011\u0004\u001c!I1Q\u0006@\u0002\u0002\u0013\u00051q\u0006\u0005\n\u0007oq\u0018\u0011!C\u0001\u000bsA\u0011b!\u0012\u007f\u0003\u0003%\tea\u0012\t\u0013\rUc0!A\u0005\u0002\u0015u\u0002\"CB4}\u0006\u0005I\u0011IB5\u0011%\u0019YG`A\u0001\n\u0003\u001ai\u0007C\u0005\u0004:z\f\t\u0011\"\u0003\u0004<\u001e9Q\u0011I\u0001\t\u0002\u0016\rcaBC#\u0003!\u0005Uq\t\u0005\t\u0005_\n\u0019\u0002\"\u0001\u0006J!Q1\u0011DA\n\u0003\u0003%\tea\u0007\t\u0015\r5\u00121CA\u0001\n\u0003\u0019y\u0003\u0003\u0006\u00048\u0005M\u0011\u0011!C\u0001\u000b\u0017B!b!\u0012\u0002\u0014\u0005\u0005I\u0011IB$\u0011)\u0019)&a\u0005\u0002\u0002\u0013\u0005Qq\n\u0005\u000b\u0007O\n\u0019\"!A\u0005B\r%\u0004BCB6\u0003'\t\t\u0011\"\u0011\u0004n!Q1\u0011XA\n\u0003\u0003%Iaa/\u0007\u0013\t5\u0017\u0001%A\u0012\u0002\t=gABC*\u0003\t+)\u0006C\u0006\u0005<\u0005%\"Q3A\u0005\u0002\r%\u0007bCC,\u0003S\u0011\t\u0012)A\u0005\u0007\u0017D1\"\"\u0017\u0002*\tU\r\u0011\"\u0001\u0004\\\"YQ1LA\u0015\u0005#\u0005\u000b\u0011BBo\u0011!\u0011y'!\u000b\u0005\u0002\u0015u\u0003B\u0003B~\u0003S\t\t\u0011\"\u0001\u0006f!Q1\u0011AA\u0015#\u0003%\t\u0001b\u0002\t\u0015\u0011-\u0011\u0011FI\u0001\n\u0003!i\u0001\u0003\u0006\u0004\u001a\u0005%\u0012\u0011!C!\u00077A!b!\f\u0002*\u0005\u0005I\u0011AB\u0018\u0011)\u00199$!\u000b\u0002\u0002\u0013\u0005Q1\u000e\u0005\u000b\u0007\u000b\nI#!A\u0005B\r\u001d\u0003BCB+\u0003S\t\t\u0011\"\u0001\u0006p!Q1\u0011MA\u0015\u0003\u0003%\t%b\u001d\t\u0015\r\u001d\u0014\u0011FA\u0001\n\u0003\u001aI\u0007\u0003\u0006\u0004l\u0005%\u0012\u0011!C!\u0007[B!ba\u001c\u0002*\u0005\u0005I\u0011IC<\u000f%)Y(AA\u0001\u0012\u0003)iHB\u0005\u0006T\u0005\t\t\u0011#\u0001\u0006\u0000!A!qNA(\t\u0003)9\t\u0003\u0006\u0004l\u0005=\u0013\u0011!C#\u0007[B!b!*\u0002P\u0005\u0005I\u0011QCE\u0011)\u0019Y+a\u0014\u0002\u0002\u0013\u0005Uq\u0012\u0005\u000b\u0007s\u000by%!A\u0005\n\rmfA\u0002Bd\u0003\t\u0013I\rC\u0006\u0003p\u0006m#Q3A\u0005\u0002\tE\bb\u0003B{\u00037\u0012\t\u0012)A\u0005\u0005gD\u0001Ba\u001c\u0002\\\u0011\u0005!q\u001f\u0005\u000b\u0005w\fY&!A\u0005\u0002\tu\bBCB\u0001\u00037\n\n\u0011\"\u0001\u0004\u0004!Q1\u0011DA.\u0003\u0003%\tea\u0007\t\u0015\r5\u00121LA\u0001\n\u0003\u0019y\u0003\u0003\u0006\u00048\u0005m\u0013\u0011!C\u0001\u0007sA!b!\u0012\u0002\\\u0005\u0005I\u0011IB$\u0011)\u0019)&a\u0017\u0002\u0002\u0013\u00051q\u000b\u0005\u000b\u0007C\nY&!A\u0005B\r\r\u0004BCB4\u00037\n\t\u0011\"\u0011\u0004j!Q11NA.\u0003\u0003%\te!\u001c\t\u0015\r=\u00141LA\u0001\n\u0003\u001a\thB\u0005\u0006\u001c\u0006\t\t\u0011#\u0001\u0006\u001e\u001aI!qY\u0001\u0002\u0002#\u0005Qq\u0014\u0005\t\u0005_\nY\b\"\u0001\u0006(\"Q11NA>\u0003\u0003%)e!\u001c\t\u0015\r\u0015\u00161PA\u0001\n\u0003+I\u000b\u0003\u0006\u0004,\u0006m\u0014\u0011!CA\u000b[C!b!/\u0002|\u0005\u0005I\u0011BB^\r\u0019)\u0019,\u0001\"\u00066\"YA1MAD\u0005+\u0007I\u0011ABn\u0011-!)'a\"\u0003\u0012\u0003\u0006Ia!8\t\u0011\t=\u0014q\u0011C\u0001\u000boC!Ba?\u0002\b\u0006\u0005I\u0011AC_\u0011)\u0019\t!a\"\u0012\u0002\u0013\u0005AQ\u0002\u0005\u000b\u00073\t9)!A\u0005B\rm\u0001BCB\u0017\u0003\u000f\u000b\t\u0011\"\u0001\u00040!Q1qGAD\u0003\u0003%\t!\"1\t\u0015\r\u0015\u0013qQA\u0001\n\u0003\u001a9\u0005\u0003\u0006\u0004V\u0005\u001d\u0015\u0011!C\u0001\u000b\u000bD!b!\u0019\u0002\b\u0006\u0005I\u0011ICe\u0011)\u00199'a\"\u0002\u0002\u0013\u00053\u0011\u000e\u0005\u000b\u0007W\n9)!A\u0005B\r5\u0004BCB8\u0003\u000f\u000b\t\u0011\"\u0011\u0006N\u001eIQ\u0011[\u0001\u0002\u0002#\u0005Q1\u001b\u0004\n\u000bg\u000b\u0011\u0011!E\u0001\u000b+D\u0001Ba\u001c\u0002(\u0012\u0005Q\u0011\u001c\u0005\u000b\u0007W\n9+!A\u0005F\r5\u0004BCBS\u0003O\u000b\t\u0011\"!\u0006\\\"Q11VAT\u0003\u0003%\t)b8\t\u0015\re\u0016qUA\u0001\n\u0013\u0019YLB\u0005\u0006f\u0006\u0001\n1%\t\u0006h\u001e9Q1`\u0001\t\u0002\u0016EhaBCs\u0003!\u0005U1\u001e\u0005\t\u0005_\n9\f\"\u0001\u0006p\"Q1\u0011DA\\\u0003\u0003%\tea\u0007\t\u0015\r5\u0012qWA\u0001\n\u0003\u0019y\u0003\u0003\u0006\u00048\u0005]\u0016\u0011!C\u0001\u000bgD!b!\u0012\u00028\u0006\u0005I\u0011IB$\u0011)\u0019)&a.\u0002\u0002\u0013\u0005Qq\u001f\u0005\u000b\u0007O\n9,!A\u0005B\r%\u0004BCB6\u0003o\u000b\t\u0011\"\u0011\u0004n!Q1\u0011XA\\\u0003\u0003%Iaa/\u0007\u0013\u0015u\u0018\u0001%A\u0012\"\u0015}xa\u0002D\n\u0003!\u0005e\u0011\u0002\u0004\b\u000b{\f\u0001\u0012\u0011D\u0002\u0011!\u0011y'a4\u0005\u0002\u0019\u001d\u0001BCB\r\u0003\u001f\f\t\u0011\"\u0011\u0004\u001c!Q1QFAh\u0003\u0003%\taa\f\t\u0015\r]\u0012qZA\u0001\n\u00031Y\u0001\u0003\u0006\u0004F\u0005=\u0017\u0011!C!\u0007\u000fB!b!\u0016\u0002P\u0006\u0005I\u0011\u0001D\b\u0011)\u00199'a4\u0002\u0002\u0013\u00053\u0011\u000e\u0005\u000b\u0007W\ny-!A\u0005B\r5\u0004BCB]\u0003\u001f\f\t\u0011\"\u0003\u0004<\u001e9aQC\u0001\t\u0002\u0019]aa\u0002D\r\u0003!\u0005a1\u0004\u0005\t\u0005_\n)\u000f\"\u0001\u0007&\u00199aqEAs\u0005\u001a%\u0002b\u0003D\u0016\u0003S\u0014)\u001a!C\u0001\u0007{D1B\"\f\u0002j\nE\t\u0015!\u0003\u0004Z!A!qNAu\t\u00031y\u0003\u0003\u0005\u00078\u0005%H\u0011\tD\u001d\u0011)\u0011Y0!;\u0002\u0002\u0013\u0005aQ\t\u0005\u000b\u0007\u0003\tI/%A\u0005\u0002\u0019%\u0003BCB\r\u0003S\f\t\u0011\"\u0011\u0004\u001c!Q1QFAu\u0003\u0003%\taa\f\t\u0015\r]\u0012\u0011^A\u0001\n\u00031i\u0005\u0003\u0006\u0004F\u0005%\u0018\u0011!C!\u0007\u000fB!b!\u0016\u0002j\u0006\u0005I\u0011\u0001D)\u0011)\u0019\t'!;\u0002\u0002\u0013\u0005cQ\u000b\u0005\u000b\u0007O\nI/!A\u0005B\r%\u0004BCB6\u0003S\f\t\u0011\"\u0011\u0004n!Q1qNAu\u0003\u0003%\tE\"\u0017\b\u0015\u0019u\u0013Q]A\u0001\u0012\u00031yF\u0002\u0006\u0007(\u0005\u0015\u0018\u0011!E\u0001\rCB\u0001Ba\u001c\u0003\f\u0011\u0005aQ\r\u0005\u000b\u0007W\u0012Y!!A\u0005F\r5\u0004BCBS\u0005\u0017\t\t\u0011\"!\u0007h!Q11\u0016B\u0006\u0003\u0003%\tIb\u001b\t\u0015\re&1BA\u0001\n\u0013\u0019YL\u0002\u0005\u0007r\u0005\u0001!\u0011\bD:\u0011-1YHa\u0006\u0003\u0002\u0003\u0006IA\" \t\u0011\t=$q\u0003C\u0001\r#C!Bb&\u0003\u0018\t\u0007I\u0011AB\u0018\u0011%1IJa\u0006!\u0002\u0013\u0019\t\u0004\u0003\u0006\u0007\u001c\n]!\u0019!C\u0001\u0007_A\u0011B\"(\u0003\u0018\u0001\u0006Ia!\r\t\u0015\u0019}%q\u0003b\u0001\n\u0003\u0019y\u0003C\u0005\u0007\"\n]\u0001\u0015!\u0003\u00042!Qa1\u0015B\f\u0005\u0004%\taa\f\t\u0013\u0019\u0015&q\u0003Q\u0001\n\rE\u0002B\u0003DT\u0005/\u0011\r\u0011\"\u0001\u0007*\"Ia\u0011\u0018B\fA\u0003%a1\u0016\u0005\u000b\rw\u00139B1A\u0005B\r=\u0002\"\u0003D_\u0005/\u0001\u000b\u0011BB\u0019\u0011%1yLa\u0006!\n\u00131\t-A\u0002VIBTAAa\u000f\u0003>\u0005\u0011\u0011n\u001c\u0006\u0003\u0005\u007f\tA!Y6lC\u000e\u0001\u0001c\u0001B#\u00035\u0011!\u0011\b\u0002\u0004+\u0012\u00048cB\u0001\u0003L\t]#\u0011\u000e\t\u0005\u0005\u001b\u0012\u0019&\u0004\u0002\u0003P)\u0011!\u0011K\u0001\u0006g\u000e\fG.Y\u0005\u0005\u0005+\u0012yE\u0001\u0004B]f\u0014VM\u001a\t\u0007\u00053\u0012yFa\u0019\u000e\u0005\tm#\u0002\u0002B/\u0005{\tQ!Y2u_JLAA!\u0019\u0003\\\tYQ\t\u001f;f]NLwN\\%e!\u0011\u0011)E!\u001a\n\t\t\u001d$\u0011\b\u0002\u0007+\u0012\u0004X\t\u001f;\u0011\t\te#1N\u0005\u0005\u0005[\u0012YFA\nFqR,gn]5p]&#\u0007K]8wS\u0012,'/\u0001\u0004=S:LGO\u0010\u000b\u0003\u0005\u0007\na\u0001\\8pWV\u0004XC\u0001B\"\u0003=\u0019'/Z1uK\u0016CH/\u001a8tS>tG\u0003\u0002B2\u0005wBqA! \u0005\u0001\u0004\u0011y(\u0001\u0004tsN$X-\u001c\t\u0005\u00053\u0012\t)\u0003\u0003\u0003\u0004\nm#aE#yi\u0016tG-\u001a3BGR|'oU=ti\u0016l\u0017aA4fiR!!1\rBE\u0011\u001d\u0011i(\u0002a\u0001\u0005\u0017\u0003BA!\u0017\u0003\u000e&!!q\u0012B.\u0005-\t5\r^8s'f\u001cH/Z7\u0015\t\t\r$1\u0013\u0005\b\u0005{2\u0001\u0019\u0001BK!\u0011\u0011IFa&\n\t\te%1\f\u0002\u001b\u00072\f7o]5d\u0003\u000e$xN]*zgR,W\u000e\u0015:pm&$WM\u001d\u0002\b\u001b\u0016\u001c8/Y4f'\r9!1J\u0015\u0005\u000f!\t9CA\u0004D_6l\u0017M\u001c3\u0014\u000f!\u0011YE!*\u00034B!!q\u0015BW\u001d\u0011\u0011)E!+\n\t\t-&\u0011H\u0001\u0011'\u0016dWm\u0019;j_:D\u0015M\u001c3mKJLAAa,\u00032\n\t\u0002*Y:GC&dWO]3NKN\u001c\u0018mZ3\u000b\t\t-&\u0011\b\t\u0004\u0005k;Q\"A\u0001\u0002\r\u0011Jg.\u001b;%)\t\u0011Y\f\u0005\u0003\u0003N\tu\u0016\u0002\u0002B`\u0005\u001f\u0012A!\u00168ji\u0006qa-Y5mkJ,W*Z:tC\u001e,WC\u0001Bc!\u0011\u0011),a\u0017\u0003\u001b\r{W.\\1oI\u001a\u000b\u0017\u000e\\3e')\tYFa\u0013\u0003L\nE'q\u001b\t\u0005\u0005k\u000b9CA\u0003Fm\u0016tGo\u0005\u0004\u0002(\t-#1\u0017\t\u0005\u0005\u001b\u0012\u0019.\u0003\u0003\u0003V\n=#a\u0002)s_\u0012,8\r\u001e\t\u0005\u00053\u0014IO\u0004\u0003\u0003\\\n\u0015h\u0002\u0002Bo\u0005Gl!Aa8\u000b\t\t\u0005(\u0011I\u0001\u0007yI|w\u000e\u001e \n\u0005\tE\u0013\u0002\u0002Bt\u0005\u001f\nq\u0001]1dW\u0006<W-\u0003\u0003\u0003l\n5(\u0001D*fe&\fG.\u001b>bE2,'\u0002\u0002Bt\u0005\u001f\n1aY7e+\t\u0011\u0019\u0010E\u0002\u00036\"\tAaY7eAQ!!Q\u0019B}\u0011!\u0011y/!\u0019A\u0002\tM\u0018\u0001B2paf$BA!2\u0003\u0000\"Q!q^A2!\u0003\u0005\rAa=\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u00111Q\u0001\u0016\u0005\u0005g\u001c9a\u000b\u0002\u0004\nA!11BB\u000b\u001b\t\u0019iA\u0003\u0003\u0004\u0010\rE\u0011!C;oG\",7m[3e\u0015\u0011\u0019\u0019Ba\u0014\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0004\u0018\r5!!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006i\u0001O]8ek\u000e$\bK]3gSb,\"a!\b\u0011\t\r}1\u0011F\u0007\u0003\u0007CQAaa\t\u0004&\u0005!A.\u00198h\u0015\t\u00199#\u0001\u0003kCZ\f\u0017\u0002BB\u0016\u0007C\u0011aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRLXCAB\u0019!\u0011\u0011iea\r\n\t\rU\"q\n\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0007w\u0019\t\u0005\u0005\u0003\u0003N\ru\u0012\u0002BB \u0005\u001f\u00121!\u00118z\u0011)\u0019\u0019%a\u001b\u0002\u0002\u0003\u00071\u0011G\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\r%\u0003CBB&\u0007#\u001aY$\u0004\u0002\u0004N)!1q\nB(\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0007'\u001aiE\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BB-\u0007?\u0002BA!\u0014\u0004\\%!1Q\fB(\u0005\u001d\u0011un\u001c7fC:D!ba\u0011\u0002p\u0005\u0005\t\u0019AB\u001e\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\ru1Q\r\u0005\u000b\u0007\u0007\n\t(!AA\u0002\rE\u0012\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\rE\u0012\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\ru\u0011AB3rk\u0006d7\u000f\u0006\u0003\u0004Z\rM\u0004BCB\"\u0003o\n\t\u00111\u0001\u0004<\t)aj\\!dWNI1Ba\u0013\u0003L\nE'q[\u0001\u0006i>\\WM\\\u000b\u0003\u0007w\ta\u0001^8lK:\u0004C\u0003BBA\u0007\u0007\u00032A!.\f\u0011\u001d\u0019IH\u0004a\u0001\u0007w!Ba!!\u0004\b\"I1\u0011P\b\u0011\u0002\u0003\u000711H\u000b\u0003\u0007\u0017SCaa\u000f\u0004\bQ!11HBH\u0011%\u0019\u0019eEA\u0001\u0002\u0004\u0019\t\u0004\u0006\u0003\u0004Z\rM\u0005\"CB\"+\u0005\u0005\t\u0019AB\u001e)\u0011\u0019iba&\t\u0013\r\rc#!AA\u0002\rEB\u0003BB-\u00077C\u0011ba\u0011\u001a\u0003\u0003\u0005\raa\u000f\u0002\u000b9{\u0017iY6\u0011\u0007\tU6dE\u0002\u001c\u0007\u0003#\"aa(\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\t\r\u00055\u0011\u0016\u0005\b\u0007sj\u0002\u0019AB\u001e\u0003\u001d)h.\u00199qYf$Baa,\u00046B1!QJBY\u0007wIAaa-\u0003P\t1q\n\u001d;j_:D\u0011ba.\u001f\u0003\u0003\u0005\ra!!\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0004>B!1qDB`\u0013\u0011\u0019\tm!\t\u0003\r=\u0013'.Z2u\u0005\u0011\u0019VM\u001c3\u0014\u0013\u0001\u0012YEa=\u0003R\n]\u0017a\u00029bs2|\u0017\rZ\u000b\u0003\u0007\u0017\u0004Ba!4\u0004T6\u00111q\u001a\u0006\u0005\u0007#\u0014i$\u0001\u0003vi&d\u0017\u0002BBk\u0007\u001f\u0014!BQ=uKN#(/\u001b8h\u0003!\u0001\u0018-\u001f7pC\u0012\u0004\u0013A\u0002;be\u001e,G/\u0006\u0002\u0004^B!1q\\Bs\u001b\t\u0019\tO\u0003\u0003\u0004d\u000e\u0015\u0012a\u00018fi&!1q]Bq\u0005EIe.\u001a;T_\u000e\\W\r^!eIJ,7o]\u0001\bi\u0006\u0014x-\u001a;!\u0003\r\t7m[\u000b\u0003\u0005\u0017\fA!Y2lAQA11_B{\u0007o\u001cI\u0010E\u0002\u00036\u0002Bqaa2(\u0001\u0004\u0019Y\rC\u0004\u0004Z\u001e\u0002\ra!8\t\u000f\r-x\u00051\u0001\u0003L\u0006Aq/\u00198ug\u0006\u001b7.\u0006\u0002\u0004ZQA11\u001fC\u0001\t\u0007!)\u0001C\u0005\u0004H&\u0002\n\u00111\u0001\u0004L\"I1\u0011\\\u0015\u0011\u0002\u0003\u00071Q\u001c\u0005\n\u0007WL\u0003\u0013!a\u0001\u0005\u0017,\"\u0001\"\u0003+\t\r-7qA\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\t!yA\u000b\u0003\u0004^\u000e\u001d\u0011AD2paf$C-\u001a4bk2$HeM\u000b\u0003\t+QCAa3\u0004\bQ!11\bC\r\u0011%\u0019\u0019eLA\u0001\u0002\u0004\u0019\t\u0004\u0006\u0003\u0004Z\u0011u\u0001\"CB\"c\u0005\u0005\t\u0019AB\u001e)\u0011\u0019i\u0002\"\t\t\u0013\r\r#'!AA\u0002\rEB\u0003BB-\tKA\u0011ba\u00116\u0003\u0003\u0005\raa\u000f\u0002\tM+g\u000e\u001a\t\u0004\u0005k;4#B\u001c\u0003L\u00115\u0002\u0003\u0002C\u0018\tgi!\u0001\"\r\u000b\t\tm2QE\u0005\u0005\u0005W$\t\u0004\u0006\u0002\u0005*Q111\u001fC\u001d\t{Aq\u0001b\u000f:\u0001\u0004\u0019Y-\u0001\u0003eCR\f\u0007bBBms\u0001\u00071Q\u001c\u000b\t\u0007g$\t\u0005b\u0011\u0005F!91q\u0019\u001eA\u0002\r-\u0007bBBmu\u0001\u00071Q\u001c\u0005\b\u0007WT\u0004\u0019\u0001Bf)\u0011!I\u0005\"\u0015\u0011\r\t53\u0011\u0017C&!)\u0011i\u0005\"\u0014\u0004L\u000eu'1Z\u0005\u0005\t\u001f\u0012yE\u0001\u0004UkBdWm\r\u0005\n\u0007o[\u0014\u0011!a\u0001\u0007g\u0014AAQ5oINIQHa\u0013\u0003t\nE'q[\u0001\bQ\u0006tG\r\\3s+\t!Y\u0006\u0005\u0003\u0003Z\u0011u\u0013\u0002\u0002C0\u00057\u0012\u0001\"Q2u_J\u0014VMZ\u0001\tQ\u0006tG\r\\3sA\u0005aAn\\2bY\u0006#GM]3tg\u0006iAn\\2bY\u0006#GM]3tg\u0002\nqa\u001c9uS>t7/\u0006\u0002\u0005lA1AQ\u000eC=\t\u007frA\u0001b\u001c\u0005v9!11\nC9\u0013\u0011!\u0019h!\u0014\u0002\u0013%lW.\u001e;bE2,\u0017\u0002\u0002Bt\toRA\u0001b\u001d\u0004N%!A1\u0010C?\u0005-!&/\u0019<feN\f'\r\\3\u000b\t\t\u001dHq\u000f\t\u0005\t\u0003#yI\u0004\u0003\u0005\u0004\u0012-e\u0002\u0002CC\t\u0013sAA!8\u0005\b&\u0011!qH\u0005\u0005\u0005w\u0011i$\u0003\u0003\u0005\u000e\ne\u0012\u0001B%oKRLA\u0001\"%\u0005\u0014\na1k\\2lKR|\u0005\u000f^5p]*!AQ\u0012B\u001d\u0003!y\u0007\u000f^5p]N\u0004C\u0003\u0003CM\t7#i\nb(\u0011\u0007\tUV\bC\u0004\u0005X\u0011\u0003\r\u0001b\u0017\t\u000f\u0011\rD\t1\u0001\u0004^\"IAq\r#\u0011\u0002\u0003\u0007A1\u000e\u000b\t\t3#\u0019\u000b\"*\u0005(\"IAqK#\u0011\u0002\u0003\u0007A1\f\u0005\n\tG*\u0005\u0013!a\u0001\u0007;D\u0011\u0002b\u001aF!\u0003\u0005\r\u0001b\u001b\u0016\u0005\u0011-&\u0006\u0002C.\u0007\u000f)\"\u0001b,+\t\u0011-4q\u0001\u000b\u0005\u0007w!\u0019\fC\u0005\u0004D-\u000b\t\u00111\u0001\u00042Q!1\u0011\fC\\\u0011%\u0019\u0019%TA\u0001\u0002\u0004\u0019Y\u0004\u0006\u0003\u0004\u001e\u0011m\u0006\"CB\"\u001d\u0006\u0005\t\u0019AB\u0019)\u0011\u0019I\u0006b0\t\u0013\r\r\u0013+!AA\u0002\rm\u0012\u0001\u0002\"j]\u0012\u00042A!.T'\u0015\u0019Fq\u0019C\u0017!1!I\rb4\u0005\\\ruG1\u000eCM\u001b\t!YM\u0003\u0003\u0005N\n=\u0013a\u0002:v]RLW.Z\u0005\u0005\t#$YMA\tBEN$(/Y2u\rVt7\r^5p]N\"\"\u0001b1\u0015\u0011\u0011eEq\u001bCm\t7Dq\u0001b\u0016W\u0001\u0004!Y\u0006C\u0004\u0005dY\u0003\ra!8\t\u0013\u0011\u001dd\u000b%AA\u0002\u0011-\u0014aD1qa2LH\u0005Z3gCVdG\u000fJ\u001a\u0015\t\u0011\u0005HQ\u001d\t\u0007\u0005\u001b\u001a\t\fb9\u0011\u0015\t5CQ\nC.\u0007;$Y\u0007C\u0005\u00048b\u000b\t\u00111\u0001\u0005\u001a\u0006YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIM\na!\u00168cS:$\u0007c\u0001B[9\n1QK\u001c2j]\u0012\u001c\u0012\u0002\u0018B&\u0005g\u0014\tNa6\u0015\u0005\u0011-H\u0003BB\u001e\tkD\u0011ba\u0011a\u0003\u0003\u0005\ra!\r\u0015\t\reC\u0011 \u0005\n\u0007\u0007\u0012\u0017\u0011!a\u0001\u0007w\u0011AbU5na2,7+\u001a8eKJ\u001c\u0012B\u001aB&\u0005g\u0014\tNa6\u0015\t\u0015\u0005Q1\u0001\t\u0004\u0005k3\u0007\"\u0003C4SB\u0005\t\u0019\u0001C6)\u0011)\t!b\u0002\t\u0013\u0011\u001d$\u000e%AA\u0002\u0011-D\u0003BB\u001e\u000b\u0017A\u0011ba\u0011o\u0003\u0003\u0005\ra!\r\u0015\t\reSq\u0002\u0005\n\u0007\u0007\u0002\u0018\u0011!a\u0001\u0007w!Ba!\b\u0006\u0014!I11I9\u0002\u0002\u0003\u00071\u0011\u0007\u000b\u0005\u00073*9\u0002C\u0005\u0004DQ\f\t\u00111\u0001\u0004<\u0005a1+[7qY\u0016\u001cVM\u001c3feB\u0019!Q\u0017<\u0014\u0007Y,\t\u0001\u0006\u0002\u0006\u001cQ!Q\u0011AC\u0012\u0011%!9\u0007\u001fI\u0001\u0002\u0004!Y'A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00132)\u0011)I#b\u000b\u0011\r\t53\u0011\u0017C6\u0011%\u00199L_A\u0001\u0002\u0004)\t!A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%M\u0001\u000f'V\u001c\b/\u001a8e%\u0016\fG-\u001b8h!\r\u0011)L \u0002\u000f'V\u001c\b/\u001a8e%\u0016\fG-\u001b8h'%q(1\nBz\u0005#\u00149\u000e\u0006\u0002\u00062Q!11HC\u001e\u0011)\u0019\u0019%!\u0002\u0002\u0002\u0003\u00071\u0011\u0007\u000b\u0005\u00073*y\u0004\u0003\u0006\u0004D\u0005%\u0011\u0011!a\u0001\u0007w\tQBU3tk6,'+Z1eS:<\u0007\u0003\u0002B[\u0003'\u0011QBU3tk6,'+Z1eS:<7CCA\n\u0005\u0017\u0012\u0019P!5\u0003XR\u0011Q1\t\u000b\u0005\u0007w)i\u0005\u0003\u0006\u0004D\u0005m\u0011\u0011!a\u0001\u0007c!Ba!\u0017\u0006R!Q11IA\u0010\u0003\u0003\u0005\raa\u000f\u0003\u0011I+7-Z5wK\u0012\u001c\"\"!\u000b\u0003L\t-'\u0011\u001bBl\u0003\u0015!\u0017\r^1!\u0003\u0019\u0019XM\u001c3fe\u000691/\u001a8eKJ\u0004CCBC0\u000bC*\u0019\u0007\u0005\u0003\u00036\u0006%\u0002\u0002\u0003C\u001e\u0003g\u0001\raa3\t\u0011\u0015e\u00131\u0007a\u0001\u0007;$b!b\u0018\u0006h\u0015%\u0004B\u0003C\u001e\u0003k\u0001\n\u00111\u0001\u0004L\"QQ\u0011LA\u001b!\u0003\u0005\ra!8\u0015\t\rmRQ\u000e\u0005\u000b\u0007\u0007\ny$!AA\u0002\rEB\u0003BB-\u000bcB!ba\u0011\u0002D\u0005\u0005\t\u0019AB\u001e)\u0011\u0019i\"\"\u001e\t\u0015\r\r\u0013QIA\u0001\u0002\u0004\u0019\t\u0004\u0006\u0003\u0004Z\u0015e\u0004BCB\"\u0003\u0017\n\t\u00111\u0001\u0004<\u0005A!+Z2fSZ,G\r\u0005\u0003\u00036\u0006=3CBA(\u000b\u0003#i\u0003\u0005\u0006\u0005J\u0016\r51ZBo\u000b?JA!\"\"\u0005L\n\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\u001c\u001a\u0015\u0005\u0015uDCBC0\u000b\u0017+i\t\u0003\u0005\u0005<\u0005U\u0003\u0019ABf\u0011!)I&!\u0016A\u0002\ruG\u0003BCI\u000b3\u0003bA!\u0014\u00042\u0016M\u0005\u0003\u0003B'\u000b+\u001bYm!8\n\t\u0015]%q\n\u0002\u0007)V\u0004H.\u001a\u001a\t\u0015\r]\u0016qKA\u0001\u0002\u0004)y&A\u0007D_6l\u0017M\u001c3GC&dW\r\u001a\t\u0005\u0005k\u000bYh\u0005\u0004\u0002|\u0015\u0005FQ\u0006\t\t\t\u0013,\u0019Ka=\u0003F&!QQ\u0015Cf\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\u000b\u0003\u000b;#BA!2\u0006,\"A!q^AA\u0001\u0004\u0011\u0019\u0010\u0006\u0003\u00060\u0016E\u0006C\u0002B'\u0007c\u0013\u0019\u0010\u0003\u0006\u00048\u0006\r\u0015\u0011!a\u0001\u0005\u000b\u0014QAQ8v]\u0012\u001c\"\"a\"\u0003L\t-'\u0011\u001bBl)\u0011)I,b/\u0011\t\tU\u0016q\u0011\u0005\t\tG\ni\t1\u0001\u0004^R!Q\u0011XC`\u0011)!\u0019'a$\u0011\u0002\u0003\u00071Q\u001c\u000b\u0005\u0007w)\u0019\r\u0003\u0006\u0004D\u0005]\u0015\u0011!a\u0001\u0007c!Ba!\u0017\u0006H\"Q11IAN\u0003\u0003\u0005\raa\u000f\u0015\t\ruQ1\u001a\u0005\u000b\u0007\u0007\ni*!AA\u0002\rEB\u0003BB-\u000b\u001fD!ba\u0011\u0002$\u0006\u0005\t\u0019AB\u001e\u0003\u0015\u0011u.\u001e8e!\u0011\u0011),a*\u0014\r\u0005\u001dVq\u001bC\u0017!!!I-b)\u0004^\u0016eFCACj)\u0011)I,\"8\t\u0011\u0011\r\u0014Q\u0016a\u0001\u0007;$B!\"9\u0006dB1!QJBY\u0007;D!ba.\u00020\u0006\u0005\t\u0019AC]\u0005E\u0019\u0016.\u001c9mKN+g\u000eZ3s%\u0016\fG-_\n\u0007\u0003g\u0013YEa3*\t\u0005M\u0016qW\n\u000b\u0003o\u0013Y%\"<\u0003R\n]\u0007\u0003\u0002B[\u0003g#\"!\"=\u0011\t\tU\u0016q\u0017\u000b\u0005\u0007w))\u0010\u0003\u0006\u0004D\u0005}\u0016\u0011!a\u0001\u0007c!Ba!\u0017\u0006z\"Q11IAb\u0003\u0003\u0005\raa\u000f\u0002#MKW\u000e\u001d7f'\u0016tG-\u001a:SK\u0006$\u0017PA\u0004V]\n|WO\u001c3\u0014\t\u0005-'1J\u0015\u0005\u0003\u0017\fym\u0005\u0006\u0002P\n-cQ\u0001Bi\u0005/\u0004BA!.\u0002LR\u0011a\u0011\u0002\t\u0005\u0005k\u000by\r\u0006\u0003\u0004<\u00195\u0001BCB\"\u0003/\f\t\u00111\u0001\u00042Q!1\u0011\fD\t\u0011)\u0019\u0019%a7\u0002\u0002\u0003\u000711H\u0001\b+:\u0014w.\u001e8e\u0003\t\u0019v\n\u0005\u0003\u00036\u0006\u0015(AA*P'\u0019\t)Oa\u0013\u0007\u001eA!aq\u0004D\u0011\u001d\u0011\u0011)\u0005b#\n\t\u0019\rB1\u0013\u0002\r'>4uN]<be\u0012,'o\u001d\u000b\u0003\r/\u0011\u0011B\u0011:pC\u0012\u001c\u0017m\u001d;\u0014\u0015\u0005%(1\nC@\u0005#\u00149.\u0001\u0002p]\u0006\u0019qN\u001c\u0011\u0015\t\u0019EbQ\u0007\t\u0005\rg\tI/\u0004\u0002\u0002f\"Aa1FAx\u0001\u0004\u0019I&\u0001\ncK\u001a|'/\u001a#bi\u0006<'/Y7CS:$G\u0003\u0002B^\rwA\u0001B\"\u0010\u0002r\u0002\u0007aqH\u0001\u0002gB!1q\u001cD!\u0013\u00111\u0019e!9\u0003\u001d\u0011\u000bG/Y4sC6\u001cvnY6fiR!a\u0011\u0007D$\u0011)1Y#a=\u0011\u0002\u0003\u00071\u0011L\u000b\u0003\r\u0017RCa!\u0017\u0004\bQ!11\bD(\u0011)\u0019\u0019%a?\u0002\u0002\u0003\u00071\u0011\u0007\u000b\u0005\u000732\u0019\u0006\u0003\u0006\u0004D\u0005}\u0018\u0011!a\u0001\u0007w!Ba!\b\u0007X!Q11\tB\u0001\u0003\u0003\u0005\ra!\r\u0015\t\rec1\f\u0005\u000b\u0007\u0007\u00129!!AA\u0002\rm\u0012!\u0003\"s_\u0006$7-Y:u!\u00111\u0019Da\u0003\u0014\r\t-a1\rC\u0017!!!I-b)\u0004Z\u0019EBC\u0001D0)\u00111\tD\"\u001b\t\u0011\u0019-\"\u0011\u0003a\u0001\u00073\"BA\"\u001c\u0007pA1!QJBY\u00073B!ba.\u0003\u0014\u0005\u0005\t\u0019\u0001D\u0019\u0005-)F\r]*fiRLgnZ:\u0014\t\t]aQ\u000f\t\u0005\u0005\u000b29(\u0003\u0003\u0007z\te\"\u0001G*fY\u0016\u001cG/[8o\u0011\u0006tG\r\\3s'\u0016$H/\u001b8hg\u00069qlY8oM&<\u0007\u0003\u0002D@\r\u001bk!A\"!\u000b\t\u0019\reQQ\u0001\u0007G>tg-[4\u000b\t\u0019\u001de\u0011R\u0001\tif\u0004Xm]1gK*\u0011a1R\u0001\u0004G>l\u0017\u0002\u0002DH\r\u0003\u0013aaQ8oM&<G\u0003\u0002DJ\r+\u0003BA!.\u0003\u0018!Aa1\u0010B\u000e\u0001\u00041i(A\u0007Oe>37+\u001a7fGR|'o]\u0001\u000f\u001dJ|emU3mK\u000e$xN]:!\u0003A!\u0015N]3di\n+hMZ3s'&TX-A\tESJ,7\r\u001e\"vM\u001a,'oU5{K\u0002\nq#T1y\t&\u0014Xm\u0019;Ck\u001a4WM\u001d)p_2\u001c\u0016N_3\u000215\u000b\u0007\u0010R5sK\u000e$()\u001e4gKJ\u0004vn\u001c7TSj,\u0007%A\tCCR\u001c\u0007NU3dK&4X\rT5nSR\f!CQ1uG\"\u0014VmY3jm\u0016d\u0015.\\5uA\u0005!R*\u00198bO\u0016lWM\u001c;ESN\u0004\u0018\r^2iKJ,\"Ab+\u0011\t\u00195fQ\u0017\b\u0005\r_3\t\f\u0005\u0003\u0003^\n=\u0013\u0002\u0002DZ\u0005\u001f\na\u0001\u0015:fI\u00164\u0017\u0002BB\u0016\roSAAb-\u0003P\u0005)R*\u00198bO\u0016lWM\u001c;ESN\u0004\u0018\r^2iKJ\u0004\u0013AF'bq\u000eC\u0017M\u001c8fYN\u0004VM]*fY\u0016\u001cGo\u001c:\u0002/5\u000b\u0007p\u00115b]:,Gn\u001d)feN+G.Z2u_J\u0004\u0013aC4fi&sGOQ=uKN$Ba!\r\u0007D\"AaQ\u0019B\u001b\u0001\u00041Y+\u0001\u0003qCRD\u0007")
public final class Udp {
    public static UdpExt get(ClassicActorSystemProvider system) {
        return Udp$.MODULE$.get(system);
    }

    public static UdpExt get(ActorSystem system) {
        return Udp$.MODULE$.get(system);
    }

    public static UdpExt createExtension(ExtendedActorSystem system) {
        return Udp$.MODULE$.createExtension(system);
    }

    public static Udp$ lookup() {
        return Udp$.MODULE$.lookup();
    }

    public static boolean equals(Object other) {
        return Udp$.MODULE$.equals(other);
    }

    public static int hashCode() {
        return Udp$.MODULE$.hashCode();
    }

    public static Extension apply(ClassicActorSystemProvider system) {
        return Udp$.MODULE$.apply(system);
    }

    public static Extension apply(ActorSystem system) {
        return Udp$.MODULE$.apply(system);
    }

    public static final class Bind
    implements Command,
    Product,
    Serializable {
        private final ActorRef handler;
        private final InetSocketAddress localAddress;
        private final Iterable<Inet.SocketOption> options;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public CommandFailed failureMessage() {
            return ((Command)this).failureMessage();
        }

        public ActorRef handler() {
            return this.handler;
        }

        public InetSocketAddress localAddress() {
            return this.localAddress;
        }

        public Iterable<Inet.SocketOption> options() {
            return this.options;
        }

        public Bind copy(ActorRef handler, InetSocketAddress localAddress, Iterable<Inet.SocketOption> options) {
            return new Bind(handler, localAddress, options);
        }

        public ActorRef copy$default$1() {
            return this.handler();
        }

        public InetSocketAddress copy$default$2() {
            return this.localAddress();
        }

        public Iterable<Inet.SocketOption> copy$default$3() {
            return this.options();
        }

        @Override
        public String productPrefix() {
            return "Bind";
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
                    return this.handler();
                }
                case 1: {
                    return this.localAddress();
                }
                case 2: {
                    return this.options();
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
            return x$1 instanceof Bind;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "handler";
                }
                case 1: {
                    return "localAddress";
                }
                case 2: {
                    return "options";
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
            if (!(object instanceof Bind)) return false;
            boolean bl = true;
            if (!bl) return false;
            Bind bind = (Bind)x$1;
            ActorRef actorRef2 = this.handler();
            ActorRef actorRef3 = bind.handler();
            if (actorRef2 == null) {
                if (actorRef3 != null) {
                    return false;
                }
            } else if (!((Object)actorRef2).equals(actorRef3)) return false;
            InetSocketAddress inetSocketAddress = this.localAddress();
            InetSocketAddress inetSocketAddress2 = bind.localAddress();
            if (inetSocketAddress == null) {
                if (inetSocketAddress2 != null) {
                    return false;
                }
            } else if (!((Object)inetSocketAddress).equals(inetSocketAddress2)) return false;
            Iterable<Inet.SocketOption> iterable = this.options();
            Iterable<Inet.SocketOption> iterable2 = bind.options();
            if (iterable == null) {
                if (iterable2 == null) return true;
                return false;
            } else {
                if (!iterable.equals(iterable2)) return false;
                return true;
            }
        }

        public Bind(ActorRef handler, InetSocketAddress localAddress, Iterable<Inet.SocketOption> options) {
            this.handler = handler;
            this.localAddress = localAddress;
            this.options = options;
            Command.$init$(this);
            Product.$init$(this);
        }
    }

    public static final class Bound
    implements Event,
    Product,
    Serializable {
        private final InetSocketAddress localAddress;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public InetSocketAddress localAddress() {
            return this.localAddress;
        }

        public Bound copy(InetSocketAddress localAddress) {
            return new Bound(localAddress);
        }

        public InetSocketAddress copy$default$1() {
            return this.localAddress();
        }

        @Override
        public String productPrefix() {
            return "Bound";
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
                    return this.localAddress();
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
            return x$1 instanceof Bound;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "localAddress";
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
            if (!(object instanceof Bound)) return false;
            boolean bl = true;
            if (!bl) return false;
            Bound bound2 = (Bound)x$1;
            InetSocketAddress inetSocketAddress = this.localAddress();
            InetSocketAddress inetSocketAddress2 = bound2.localAddress();
            if (inetSocketAddress != null) {
                if (!((Object)inetSocketAddress).equals(inetSocketAddress2)) return false;
                return true;
            }
            if (inetSocketAddress2 == null) return true;
            return false;
        }

        public Bound(InetSocketAddress localAddress) {
            this.localAddress = localAddress;
            Product.$init$(this);
        }
    }

    public static interface Command
    extends SelectionHandler.HasFailureMessage,
    Message {
        @Override
        default public CommandFailed failureMessage() {
            return new CommandFailed(this);
        }

        public static void $init$(Command $this) {
        }
    }

    public static final class CommandFailed
    implements Event,
    Product,
    Serializable {
        private final Command cmd;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Command cmd() {
            return this.cmd;
        }

        public CommandFailed copy(Command cmd) {
            return new CommandFailed(cmd);
        }

        public Command copy$default$1() {
            return this.cmd();
        }

        @Override
        public String productPrefix() {
            return "CommandFailed";
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
                    return this.cmd();
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
            return x$1 instanceof CommandFailed;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "cmd";
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
            if (!(object instanceof CommandFailed)) return false;
            boolean bl = true;
            if (!bl) return false;
            CommandFailed commandFailed = (CommandFailed)x$1;
            Command command = this.cmd();
            Command command2 = commandFailed.cmd();
            if (command != null) {
                if (!command.equals(command2)) return false;
                return true;
            }
            if (command2 == null) return true;
            return false;
        }

        public CommandFailed(Command cmd) {
            this.cmd = cmd;
            Product.$init$(this);
        }
    }

    public static interface Event
    extends Message {
    }

    public static interface Message {
    }

    public static class NoAck
    implements Event,
    Product,
    Serializable {
        private final Object token;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Object token() {
            return this.token;
        }

        public NoAck copy(Object token) {
            return new NoAck(token);
        }

        public Object copy$default$1() {
            return this.token();
        }

        @Override
        public String productPrefix() {
            return "NoAck";
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
                    return this.token();
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
            return x$1 instanceof NoAck;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "token";
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
            if (!(object instanceof NoAck)) return false;
            boolean bl = true;
            if (!bl) return false;
            NoAck noAck = (NoAck)x$1;
            if (!BoxesRunTime.equals(this.token(), noAck.token())) return false;
            if (!noAck.canEqual(this)) return false;
            return true;
        }

        public NoAck(Object token) {
            this.token = token;
            Product.$init$(this);
        }
    }

    public static final class Received
    implements Event,
    Product,
    Serializable {
        private final ByteString data;
        private final InetSocketAddress sender;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ByteString data() {
            return this.data;
        }

        public InetSocketAddress sender() {
            return this.sender;
        }

        public Received copy(ByteString data, InetSocketAddress sender) {
            return new Received(data, sender);
        }

        public ByteString copy$default$1() {
            return this.data();
        }

        public InetSocketAddress copy$default$2() {
            return this.sender();
        }

        @Override
        public String productPrefix() {
            return "Received";
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
                    return this.data();
                }
                case 1: {
                    return this.sender();
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
            return x$1 instanceof Received;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "data";
                }
                case 1: {
                    return "sender";
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
            if (!(object instanceof Received)) return false;
            boolean bl = true;
            if (!bl) return false;
            Received received = (Received)x$1;
            ByteString byteString = this.data();
            ByteString byteString2 = received.data();
            if (byteString == null) {
                if (byteString2 != null) {
                    return false;
                }
            } else if (!((Object)byteString).equals(byteString2)) return false;
            InetSocketAddress inetSocketAddress = this.sender();
            InetSocketAddress inetSocketAddress2 = received.sender();
            if (inetSocketAddress == null) {
                if (inetSocketAddress2 == null) return true;
                return false;
            } else {
                if (!((Object)inetSocketAddress).equals(inetSocketAddress2)) return false;
                return true;
            }
        }

        public Received(ByteString data, InetSocketAddress sender) {
            this.data = data;
            this.sender = sender;
            Product.$init$(this);
        }
    }

    public static final class Send
    implements Command,
    Product,
    Serializable {
        private final ByteString payload;
        private final InetSocketAddress target;
        private final Event ack;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public CommandFailed failureMessage() {
            return ((Command)this).failureMessage();
        }

        public ByteString payload() {
            return this.payload;
        }

        public InetSocketAddress target() {
            return this.target;
        }

        public Event ack() {
            return this.ack;
        }

        public boolean wantsAck() {
            return !(this.ack() instanceof NoAck);
        }

        public Send copy(ByteString payload, InetSocketAddress target, Event ack) {
            return new Send(payload, target, ack);
        }

        public ByteString copy$default$1() {
            return this.payload();
        }

        public InetSocketAddress copy$default$2() {
            return this.target();
        }

        public Event copy$default$3() {
            return this.ack();
        }

        @Override
        public String productPrefix() {
            return "Send";
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
                    return this.payload();
                }
                case 1: {
                    return this.target();
                }
                case 2: {
                    return this.ack();
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
            return x$1 instanceof Send;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "payload";
                }
                case 1: {
                    return "target";
                }
                case 2: {
                    return "ack";
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
            if (!(object instanceof Send)) return false;
            boolean bl = true;
            if (!bl) return false;
            Send send = (Send)x$1;
            ByteString byteString = this.payload();
            ByteString byteString2 = send.payload();
            if (byteString == null) {
                if (byteString2 != null) {
                    return false;
                }
            } else if (!((Object)byteString).equals(byteString2)) return false;
            InetSocketAddress inetSocketAddress = this.target();
            InetSocketAddress inetSocketAddress2 = send.target();
            if (inetSocketAddress == null) {
                if (inetSocketAddress2 != null) {
                    return false;
                }
            } else if (!((Object)inetSocketAddress).equals(inetSocketAddress2)) return false;
            Event event = this.ack();
            Event event2 = send.ack();
            if (event == null) {
                if (event2 == null) return true;
                return false;
            } else {
                if (!event.equals(event2)) return false;
                return true;
            }
        }

        public Send(ByteString payload, InetSocketAddress target, Event ack) {
            this.payload = payload;
            this.target = target;
            this.ack = ack;
            Command.$init$(this);
            Product.$init$(this);
            Predef$.MODULE$.require(ack != null, (Function0<Object>)(Function0<String> & Serializable)() -> "ack must be non-null. Use NoAck if you don't want acks.");
        }
    }

    public static class SimpleSender
    implements Command,
    Product,
    Serializable {
        private final Iterable<Inet.SocketOption> options;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public CommandFailed failureMessage() {
            return ((Command)this).failureMessage();
        }

        public Iterable<Inet.SocketOption> options() {
            return this.options;
        }

        public SimpleSender copy(Iterable<Inet.SocketOption> options) {
            return new SimpleSender(options);
        }

        public Iterable<Inet.SocketOption> copy$default$1() {
            return this.options();
        }

        @Override
        public String productPrefix() {
            return "SimpleSender";
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
                    return this.options();
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
            return x$1 instanceof SimpleSender;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "options";
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
            if (!(object instanceof SimpleSender)) return false;
            boolean bl = true;
            if (!bl) return false;
            SimpleSender simpleSender = (SimpleSender)x$1;
            Iterable<Inet.SocketOption> iterable = this.options();
            Iterable<Inet.SocketOption> iterable2 = simpleSender.options();
            if (iterable == null) {
                if (iterable2 != null) {
                    return false;
                }
            } else if (!iterable.equals(iterable2)) return false;
            if (!simpleSender.canEqual(this)) return false;
            return true;
        }

        public SimpleSender(Iterable<Inet.SocketOption> options) {
            this.options = options;
            Command.$init$(this);
            Product.$init$(this);
        }
    }

    public static interface SimpleSenderReady
    extends Event {
    }

    public static class UdpSettings
    extends SelectionHandlerSettings {
        private final Config _config;
        private final int NrOfSelectors;
        private final int DirectBufferSize;
        private final int MaxDirectBufferPoolSize;
        private final int BatchReceiveLimit;
        private final String ManagementDispatcher;
        private final int MaxChannelsPerSelector;

        public int NrOfSelectors() {
            return this.NrOfSelectors;
        }

        public int DirectBufferSize() {
            return this.DirectBufferSize;
        }

        public int MaxDirectBufferPoolSize() {
            return this.MaxDirectBufferPoolSize;
        }

        public int BatchReceiveLimit() {
            return this.BatchReceiveLimit;
        }

        public String ManagementDispatcher() {
            return this.ManagementDispatcher;
        }

        @Override
        public int MaxChannelsPerSelector() {
            return this.MaxChannelsPerSelector;
        }

        private int getIntBytes(String path) {
            Long size = this._config.getBytes(path);
            Predef$.MODULE$.require(Predef$.MODULE$.Long2long(size) < Integer.MAX_VALUE, (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(16).append(path).append(" must be < 2 GiB").toString());
            return (int)Predef$.MODULE$.Long2long(size);
        }

        public UdpSettings(Config _config) {
            this._config = _config;
            super(_config);
            this.NrOfSelectors = BoxesRunTime.unboxToInt(Helpers$Requiring$.MODULE$.requiring$extension(Helpers$.MODULE$.Requiring(BoxesRunTime.boxToInteger(_config.getInt("nr-of-selectors"))), x$1 -> x$1 > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "nr-of-selectors must be > 0"));
            this.DirectBufferSize = this.getIntBytes("direct-buffer-size");
            this.MaxDirectBufferPoolSize = _config.getInt("direct-buffer-pool-limit");
            this.BatchReceiveLimit = _config.getInt("receive-throughput");
            this.ManagementDispatcher = _config.getString("management-dispatcher");
            this.MaxChannelsPerSelector = this.MaxChannels() == -1 ? -1 : package$.MODULE$.max(this.MaxChannels() / this.NrOfSelectors(), 1);
        }
    }

    public static interface Unbound {
    }
}

