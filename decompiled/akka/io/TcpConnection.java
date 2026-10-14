/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.actor.NoSerializationVerificationNeeded;
import akka.actor.ReceiveTimeout$;
import akka.actor.SupervisorStrategy;
import akka.annotation.InternalApi;
import akka.dispatch.RequiresMessageQueue;
import akka.dispatch.UnboundedMessageQueueSemantics;
import akka.event.LoggingAdapter;
import akka.io.ChannelRegistration;
import akka.io.Inet;
import akka.io.SelectionHandler$ChannelReadable$;
import akka.io.SelectionHandler$ChannelWritable$;
import akka.io.Tcp;
import akka.io.Tcp$Abort$;
import akka.io.Tcp$Aborted$;
import akka.io.Tcp$Close$;
import akka.io.Tcp$ConfirmedClosed$;
import akka.io.Tcp$PeerClosed$;
import akka.io.Tcp$ResumeReading$;
import akka.io.Tcp$ResumeWriting$;
import akka.io.Tcp$SuspendReading$;
import akka.io.Tcp$Write$;
import akka.io.Tcp$WritingResumed$;
import akka.io.TcpConnection$;
import akka.io.TcpConnection$AllRead$;
import akka.io.TcpConnection$EmptyPendingWrite$;
import akka.io.TcpConnection$EndOfStream$;
import akka.io.TcpConnection$MoreDataWaiting$;
import akka.io.TcpConnection$Unregistered$;
import akka.io.TcpExt;
import akka.util.ByteString;
import akka.util.ByteString$;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.collection.AbstractIterable;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.SetOps;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.concurrent.duration.Duration$;
import scala.math.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0019\u0005gACAY\u0003g\u000b\t!a-\u0002<\"Q\u0011Q\u001e\u0001\u0003\u0006\u0004%\t!!=\t\u0015\u0005m\bA!A!\u0002\u0013\t\u0019\u0010\u0003\u0006\u0002~\u0002\u0011)\u0019!C\u0001\u0003\u007fD!B!\u0006\u0001\u0005\u0003\u0005\u000b\u0011\u0002B\u0001\u0011)\u00119\u0002\u0001BC\u0002\u0013\u0005!\u0011\u0004\u0005\u000b\u0005C\u0001!\u0011!Q\u0001\n\tm\u0001b\u0002B\u0012\u0001\u0011\u0005!Q\u0005\u0005\t\u0005_\u0001\u0001\u0015)\u0003\u00032!AQq\u000e\u0001!B\u0013\u0011Y\u0002\u0003\u0005\u0006r\u0001\u0001\u000b\u0015\u0002B\u000e\u0011!)\u0019\b\u0001Q!\n\tm\u0001\u0002CC;\u0001\u0001\u0006K!b\u001e\t\u0011\u0015e\u0004\u0001)Q\u0005\u000bwB\u0011\"b \u0001\u0001\u0004%Iaa%\t\u0013\u0015\u0005\u0005\u00011A\u0005\n\u0015\r\u0005\u0002CCD\u0001\u0001\u0006KAa'\t\u0013\r\u0015\u0005\u00011A\u0005\n\u0015%\u0005\"CCG\u0001\u0001\u0007I\u0011BCH\u0011!\u0019y\t\u0001Q!\n\u0015-\u0005bBCJ\u0001\u0011\u0005QQ\u0013\u0005\b\u000b3\u0003A\u0011CCN\u0011\u001d)y\n\u0001C\t\t7Aq!\")\u0001\t\u0003\u0011I\u0002C\u0004\u0006$\u0002!\t!\"*\t\u000f\u0015E\u0006\u0001\"\u0001\u00064\"9Qq\u0017\u0001\u0005\u0002\u0015e\u0006bBC_\u0001\u0011\u0005Qq\u0018\u0005\b\u000b\u001f\u0004A\u0011ACi\u0011\u001d)9\u000e\u0001C\u0001\u000b3Dq!\"8\u0001\t\u0003)y\u000eC\u0004\u0006b\u0002!\t!b9\t\u000f\u0019=\u0001\u0001\"\u0001\u0007\u0012!9aQ\u0003\u0001\u0005\u0002\u0019]\u0001b\u0002D\u000f\u0001\u0011\u0005aq\u0004\u0005\b\t#\u0001A\u0011\u0001D\u0013\u0011\u001d1I\u0003\u0001C\u0001\rWAqA\"\r\u0001\t\u00031\u0019\u0004C\u0004\u0007<\u0001!\tA\"\u0010\t\u000f\u0019\u0015\u0003\u0001\"\u0001\u0007H!9aq\n\u0001\u0005\u0002\u0019E\u0003\u0002\u0003D*\u0001\u0001&IA\"\u0016\t\u000f\u0019=\u0004\u0001\"\u0001\u0005\u001c!9a\u0011\u000f\u0001\u0005\u0002\u0019M\u0004\"\u0003D?\u0001E\u0005I\u0011AB`\u0011\u001d1y\b\u0001C!\t7AqA\"!\u0001\t\u00032\u0019\tC\u0004\u0007\n\u0002!\tAb#\t\u000f\u0019M\u0005\u0001\"\u0001\u0007\u0016\u001a1A\u0011\u0007\u0001\u0001\tgA!\u0002b\u00042\u0005\u000b\u0007I\u0011ABJ\u0011)!)$\rB\u0001B\u0003%!1\u0014\u0005\u000b\to\t$\u0011!Q\u0001\n\u0011e\u0002B\u0003C#c\t\u0005\t\u0015!\u0003\u0004\f!QAqI\u0019\u0003\u0002\u0003\u0006I\u0001\"\u0013\t\u0015\u0011E\u0013G!A!\u0002\u0013!\u0019\u0006C\u0004\u0003$E\"\t\u0001\"\u0017\t\u000f\u0011E\u0011\u0007\"\u0001\u0005j!9A\u0011D\u0019\u0005\u0002\u0011m\u0001b\u0002DQ\u0001\u0011\u0005a1\u0015\u0004\u0007\t_\u0002\u0001\u0001\"\u001d\t\u0015\u0011=AH!b\u0001\n\u0003\u0019\u0019\n\u0003\u0006\u00056q\u0012\t\u0011)A\u0005\u00057C!\u0002\"\u001f=\u0005\u0003\u0005\u000b\u0011\u0002C>\u0011)!\t\t\u0010B\u0001B\u0003%A1\u0011\u0005\u000b\t\u0013c$\u0011!Q\u0001\n\u0011\r\u0005B\u0003C#y\t\u0005\t\u0015!\u0003\u0003(\"QA\u0011\u000b\u001f\u0003\u0002\u0003\u0006I\u0001b\u0015\t\u000f\t\rB\b\"\u0001\u0005\f\"9A\u0011\u0003\u001f\u0005\u0002\u0011m\u0005b\u0002C\ry\u0011\u0005A1\u0004\u0005\b\t?cD\u0011\u0001C\u000e\u000f)\u0011)$a-\t\u0002\u0005M&q\u0007\u0004\u000b\u0003c\u000b\u0019\f#\u0001\u00024\ne\u0002b\u0002B\u0012\u0013\u0012\u0005!1\b\u0004\n\u0005{I\u0005\u0013aI\u0011\u0005\u007f9qAa\u0018J\u0011\u0003\u0011)FB\u0004\u0003P%C\tA!\u0015\t\u000f\t\rR\n\"\u0001\u0003T\u001d9!\u0011M%\t\u0002\t5ca\u0002B\"\u0013\"\u0005!Q\t\u0005\b\u0005G\u0001F\u0011\u0001B&\u000f\u001d\u0011\u0019'\u0013E\u0001\u0005;2qAa\u0016J\u0011\u0003\u0011I\u0006C\u0004\u0003$M#\tAa\u0017\u0007\r\t\u0015\u0014J\u0011B4\u0011)\u00119)\u0016BK\u0002\u0013\u0005!\u0011\u0012\u0005\u000b\u0005C+&\u0011#Q\u0001\n\t-\u0005B\u0003BR+\nU\r\u0011\"\u0001\u0003&\"Q!QX+\u0003\u0012\u0003\u0006IAa*\t\u000f\t\rR\u000b\"\u0001\u0003@\"I!qY+\u0002\u0002\u0013\u0005!\u0011\u001a\u0005\n\u0005\u001f,\u0016\u0013!C\u0001\u0005#D\u0011Ba:V#\u0003%\tA!;\t\u0013\t5X+!A\u0005B\t=\b\"\u0003B\u007f+\u0006\u0005I\u0011\u0001B\u0000\u0011%\u00199!VA\u0001\n\u0003\u0019I\u0001C\u0005\u0004\u0016U\u000b\t\u0011\"\u0011\u0004\u0018!I1QE+\u0002\u0002\u0013\u00051q\u0005\u0005\n\u0007W)\u0016\u0011!C!\u0007[A\u0011b!\rV\u0003\u0003%\tea\r\t\u0013\rUR+!A\u0005B\r]\u0002\"CB\u001d+\u0006\u0005I\u0011IB\u001e\u000f%\u0019y$SA\u0001\u0012\u0003\u0019\tEB\u0005\u0003f%\u000b\t\u0011#\u0001\u0004D!9!1\u00055\u0005\u0002\re\u0003\"CB\u001bQ\u0006\u0005IQIB\u001c\u0011%\u0019Y\u0006[A\u0001\n\u0003\u001bi\u0006C\u0005\u0004d!\f\t\u0011\"!\u0004f!I1q\u000f5\u0002\u0002\u0013%1\u0011\u0010\u0004\u0007\u0007\u0003K%ia!\t\u0015\r\u0015eN!f\u0001\n\u0003\u00199\t\u0003\u0006\u0004\u0010:\u0014\t\u0012)A\u0005\u0007\u0013C!b!%o\u0005+\u0007I\u0011ABJ\u0011)\u0019)J\u001cB\tB\u0003%!1\u0014\u0005\u000b\u0007/s'Q3A\u0005\u0002\te\u0001BCBM]\nE\t\u0015!\u0003\u0003\u001c!Q11\u00148\u0003\u0016\u0004%\tA!\u0007\t\u0015\rueN!E!\u0002\u0013\u0011Y\u0002C\u0004\u0003$9$\taa(\t\u0013\t\u001dg.!A\u0005\u0002\r-\u0006\"\u0003Bh]F\u0005I\u0011AB[\u0011%\u00119O\\I\u0001\n\u0003\u0019I\fC\u0005\u0004>:\f\n\u0011\"\u0001\u0004@\"I11\u00198\u0012\u0002\u0013\u00051q\u0018\u0005\n\u0005[t\u0017\u0011!C!\u0005_D\u0011B!@o\u0003\u0003%\tAa@\t\u0013\r\u001da.!A\u0005\u0002\r\u0015\u0007\"CB\u000b]\u0006\u0005I\u0011IB\f\u0011%\u0019)C\\A\u0001\n\u0003\u0019I\rC\u0005\u0004,9\f\t\u0011\"\u0011\u0004N\"I1\u0011\u00078\u0002\u0002\u0013\u000531\u0007\u0005\n\u0007kq\u0017\u0011!C!\u0007oA\u0011b!\u000fo\u0003\u0003%\te!5\b\u0013\rU\u0017*!A\t\u0002\r]g!CBA\u0013\u0006\u0005\t\u0012ABm\u0011!\u0011\u0019#a\u0004\u0005\u0002\r\u0005\bBCB\u001b\u0003\u001f\t\t\u0011\"\u0012\u00048!Q11LA\b\u0003\u0003%\tia9\t\u0015\r\r\u0014qBA\u0001\n\u0003\u001bi\u000f\u0003\u0006\u0004x\u0005=\u0011\u0011!C\u0005\u0007s2aa!?J\u0005\u000em\bb\u0003C\u0002\u00037\u0011)\u001a!C\u0001\t\u000bA1\u0002\")\u0002\u001c\tE\t\u0015!\u0003\u0005\b!YA1UA\u000e\u0005+\u0007I\u0011\u0001CS\u0011-!i+a\u0007\u0003\u0012\u0003\u0006I\u0001b*\t\u0011\t\r\u00121\u0004C\u0001\t_C!Ba2\u0002\u001c\u0005\u0005I\u0011\u0001C\\\u0011)\u0011y-a\u0007\u0012\u0002\u0013\u0005AQ\u0018\u0005\u000b\u0005O\fY\"%A\u0005\u0002\u0011\u0005\u0007B\u0003Bw\u00037\t\t\u0011\"\u0011\u0003p\"Q!Q`A\u000e\u0003\u0003%\tAa@\t\u0015\r\u001d\u00111DA\u0001\n\u0003!)\r\u0003\u0006\u0004\u0016\u0005m\u0011\u0011!C!\u0007/A!b!\n\u0002\u001c\u0005\u0005I\u0011\u0001Ce\u0011)\u0019Y#a\u0007\u0002\u0002\u0013\u0005CQ\u001a\u0005\u000b\u0007c\tY\"!A\u0005B\rM\u0002BCB\u001b\u00037\t\t\u0011\"\u0011\u00048!Q1\u0011HA\u000e\u0003\u0003%\t\u0005\"5\b\u0013\u0011U\u0017*!A\t\u0002\u0011]g!CB}\u0013\u0006\u0005\t\u0012\u0001Cm\u0011!\u0011\u0019#!\u0011\u0005\u0002\u0011u\u0007BCB\u001b\u0003\u0003\n\t\u0011\"\u0012\u00048!Q11LA!\u0003\u0003%\t\tb8\t\u0015\r\r\u0014\u0011IA\u0001\n\u0003#)\u000f\u0003\u0006\u0004x\u0005\u0005\u0013\u0011!C\u0005\u0007s2a\u0001\"<J\u0005\u0012=\bb\u0003Cy\u0003\u001b\u0012)\u001a!C\u0001\tgD1\u0002b?\u0002N\tE\t\u0015!\u0003\u0005v\"A!1EA'\t\u0003!i\u0010\u0003\u0006\u0003H\u00065\u0013\u0011!C\u0001\u000b\u0007A!Ba4\u0002NE\u0005I\u0011AC\u0004\u0011)\u0011i/!\u0014\u0002\u0002\u0013\u0005#q\u001e\u0005\u000b\u0005{\fi%!A\u0005\u0002\t}\bBCB\u0004\u0003\u001b\n\t\u0011\"\u0001\u0006\f!Q1QCA'\u0003\u0003%\tea\u0006\t\u0015\r\u0015\u0012QJA\u0001\n\u0003)y\u0001\u0003\u0006\u0004,\u00055\u0013\u0011!C!\u000b'A!b!\r\u0002N\u0005\u0005I\u0011IB\u001a\u0011)\u0019)$!\u0014\u0002\u0002\u0013\u00053q\u0007\u0005\u000b\u0007s\ti%!A\u0005B\u0015]q!CC\u000e\u0013\u0006\u0005\t\u0012AC\u000f\r%!i/SA\u0001\u0012\u0003)y\u0002\u0003\u0005\u0003$\u00055D\u0011AC\u0014\u0011)\u0019)$!\u001c\u0002\u0002\u0013\u00153q\u0007\u0005\u000b\u00077\ni'!A\u0005\u0002\u0016%\u0002BCB2\u0003[\n\t\u0011\"!\u0006.!Q1qOA7\u0003\u0003%Ia!\u001f\b\u000f\u0015M\u0012\n#!\u00066\u00199QqG%\t\u0002\u0016e\u0002\u0002\u0003B\u0012\u0003w\"\t!b\u000f\t\u0015\t5\u00181PA\u0001\n\u0003\u0012y\u000f\u0003\u0006\u0003~\u0006m\u0014\u0011!C\u0001\u0005\u007fD!ba\u0002\u0002|\u0005\u0005I\u0011AC\u001f\u0011)\u0019)\"a\u001f\u0002\u0002\u0013\u00053q\u0003\u0005\u000b\u0007K\tY(!A\u0005\u0002\u0015\u0005\u0003BCB\u0019\u0003w\n\t\u0011\"\u0011\u00044!Q1QGA>\u0003\u0003%\tea\u000e\t\u0015\r]\u00141PA\u0001\n\u0013\u0019IHB\u0004\u0005\n%\u000b\t\u0003b\u0003\t\u0011\t\r\u0012q\u0012C\u0001\t\u001bA\u0001\u0002b\u0004\u0002\u0010\u001a\u000511\u0013\u0005\t\t#\tyI\"\u0001\u0005\u0014!AA\u0011DAH\r\u0003!YbB\u0004\u0006F%C\t\u0001b\u000b\u0007\u000f\u0011\u0015\u0012\n#\u0001\u0005(!A!1EAN\t\u0003!I\u0003\u0003\u0005\u0005\u0010\u0005mE\u0011ABJ\u0011!!\t\"a'\u0005\u0002\u00115\u0002\u0002\u0003C\r\u00037#\t\u0001b\u0007\t\u0013\u0015\u001d\u0013J1A\u0005\u0002\u0011\u0015\u0006\u0002CC%\u0013\u0002\u0006I\u0001b*\t\u0013\u0015-\u0013J1A\u0005\u0002\u00155\u0003\u0002CC+\u0013\u0002\u0006I!b\u0014\t\u0013\u0015\u0015\u0014J1A\u0005\u0002\u0015\u001d\u0004\u0002CC7\u0013\u0002\u0006I!\"\u001b\u0003\u001bQ\u001b\u0007oQ8o]\u0016\u001cG/[8o\u0015\u0011\t),a.\u0002\u0005%|'BAA]\u0003\u0011\t7n[1\u0014\u0013\u0001\ti,!3\u0002V\u0006m\u0007\u0003BA`\u0003\u000bl!!!1\u000b\u0005\u0005\r\u0017!B:dC2\f\u0017\u0002BAd\u0003\u0003\u0014a!\u00118z%\u00164\u0007\u0003BAf\u0003#l!!!4\u000b\t\u0005=\u0017qW\u0001\u0006C\u000e$xN]\u0005\u0005\u0003'\fiMA\u0003BGR|'\u000f\u0005\u0003\u0002L\u0006]\u0017\u0002BAm\u0003\u001b\u0014A\"Q2u_JdunZ4j]\u001e\u0004b!!8\u0002d\u0006\u001dXBAAp\u0015\u0011\t\t/a.\u0002\u0011\u0011L7\u000f]1uG\"LA!!:\u0002`\n!\"+Z9vSJ,7/T3tg\u0006<W-U;fk\u0016\u0004B!!8\u0002j&!\u00111^Ap\u0005y)fNY8v]\u0012,G-T3tg\u0006<W-U;fk\u0016\u001cV-\\1oi&\u001c7/A\u0002uGB\u001c\u0001!\u0006\u0002\u0002tB!\u0011Q_A|\u001b\t\t\u0019,\u0003\u0003\u0002z\u0006M&A\u0002+da\u0016CH/\u0001\u0003uGB\u0004\u0013aB2iC:tW\r\\\u000b\u0003\u0005\u0003\u0001BAa\u0001\u0003\u00125\u0011!Q\u0001\u0006\u0005\u0005\u000f\u0011I!\u0001\u0005dQ\u0006tg.\u001a7t\u0015\u0011\u0011YA!\u0004\u0002\u00079LwN\u0003\u0002\u0003\u0010\u0005!!.\u0019<b\u0013\u0011\u0011\u0019B!\u0002\u0003\u001bM{7m[3u\u0007\"\fgN\\3m\u0003!\u0019\u0007.\u00198oK2\u0004\u0013\u0001\u00039vY2lu\u000eZ3\u0016\u0005\tm\u0001\u0003BA`\u0005;IAAa\b\u0002B\n9!i\\8mK\u0006t\u0017!\u00039vY2lu\u000eZ3!\u0003\u0019a\u0014N\\5u}QA!q\u0005B\u0015\u0005W\u0011i\u0003E\u0002\u0002v\u0002Aq!!<\b\u0001\u0004\t\u0019\u0010C\u0004\u0002~\u001e\u0001\rA!\u0001\t\u000f\t]q\u00011\u0001\u0003\u001c\u0005a\u0001/\u001a8eS:<wK]5uKB!!1GAH\u001d\r\t)\u0010S\u0001\u000e)\u000e\u00048i\u001c8oK\u000e$\u0018n\u001c8\u0011\u0007\u0005U\u0018jE\u0002J\u0003{#\"Aa\u000e\u0003\u0015I+\u0017\r\u001a*fgVdGoE\u0002L\u0003{KCa\u0013)N'\n9\u0011\t\u001c7SK\u0006$7#\u0002)\u0002>\n\u001d\u0003c\u0001B%\u00176\t\u0011\n\u0006\u0002\u0003NA\u0019!\u0011\n)\u0003\u0017\u0015sGm\u00144TiJ,\u0017-\\\n\u0006\u001b\u0006u&q\t\u000b\u0003\u0005+\u00022A!\u0013N\u0005=iuN]3ECR\fw+Y5uS:<7#B*\u0002>\n\u001dCC\u0001B/!\r\u0011IeU\u0001\f\u000b:$wJZ*ue\u0016\fW.A\u0004BY2\u0014V-\u00193\u0002\u001f5{'/\u001a#bi\u0006<\u0016-\u001b;j]\u001e\u0014\u0001c\u00117pg\u0016LeNZ8s[\u0006$\u0018n\u001c8\u0014\u000fU\u000biL!\u001b\u0003pA!\u0011q\u0018B6\u0013\u0011\u0011i'!1\u0003\u000fA\u0013x\u000eZ;diB!!\u0011\u000fBA\u001d\u0011\u0011\u0019H! \u000f\t\tU$1P\u0007\u0003\u0005oRAA!\u001f\u0002p\u00061AH]8pizJ!!a1\n\t\t}\u0014\u0011Y\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\u0011\u0019I!\"\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\t\t}\u0014\u0011Y\u0001\u0010]>$\u0018NZ5dCRLwN\\:U_V\u0011!1\u0012\t\u0007\u0005\u001b\u0013)Ja'\u000f\t\t=%\u0011\u0013\t\u0005\u0005k\n\t-\u0003\u0003\u0003\u0014\u0006\u0005\u0017A\u0002)sK\u0012,g-\u0003\u0003\u0003\u0018\ne%aA*fi*!!1SAa!\u0011\tYM!(\n\t\t}\u0015Q\u001a\u0002\t\u0003\u000e$xN\u001d*fM\u0006\u0001bn\u001c;jM&\u001c\u0017\r^5p]N$v\u000eI\u0001\fG2|7/\u001a3Fm\u0016tG/\u0006\u0002\u0003(B!!\u0011\u0016B\\\u001d\u0011\u0011YKa-\u000f\t\t5&\u0011\u0017\b\u0005\u0005k\u0012y+\u0003\u0002\u0002:&!\u0011QWA\\\u0013\u0011\u0011),a-\u0002\u0007Q\u001b\u0007/\u0003\u0003\u0003:\nm&!B#wK:$(\u0002\u0002B[\u0003g\u000bAb\u00197pg\u0016$WI^3oi\u0002\"bA!1\u0003D\n\u0015\u0007c\u0001B%+\"9!q\u0011.A\u0002\t-\u0005b\u0002BR5\u0002\u0007!qU\u0001\u0005G>\u0004\u0018\u0010\u0006\u0004\u0003B\n-'Q\u001a\u0005\n\u0005\u000f[\u0006\u0013!a\u0001\u0005\u0017C\u0011Ba)\\!\u0003\u0005\rAa*\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011!1\u001b\u0016\u0005\u0005\u0017\u0013)n\u000b\u0002\u0003XB!!\u0011\u001cBr\u001b\t\u0011YN\u0003\u0003\u0003^\n}\u0017!C;oG\",7m[3e\u0015\u0011\u0011\t/!1\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0003f\nm'!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012TC\u0001BvU\u0011\u00119K!6\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\u0011\t\u0010\u0005\u0003\u0003t\neXB\u0001B{\u0015\u0011\u00119P!\u0004\u0002\t1\fgnZ\u0005\u0005\u0005w\u0014)P\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0007\u0003\u0001B!a0\u0004\u0004%!1QAAa\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\u0011\u0019Ya!\u0005\u0011\t\u0005}6QB\u0005\u0005\u0007\u001f\t\tMA\u0002B]fD\u0011ba\u0005a\u0003\u0003\u0005\ra!\u0001\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\u0019I\u0002\u0005\u0004\u0004\u001c\r\u000521B\u0007\u0003\u0007;QAaa\b\u0002B\u0006Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\r\r2Q\u0004\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0003\u001c\r%\u0002\"CB\nE\u0006\u0005\t\u0019AB\u0006\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\tE8q\u0006\u0005\n\u0007'\u0019\u0017\u0011!a\u0001\u0007\u0003\t\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0007\u0003\t\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0005c\fa!Z9vC2\u001cH\u0003\u0002B\u000e\u0007{A\u0011ba\u0005g\u0003\u0003\u0005\raa\u0003\u0002!\rcwn]3J]\u001a|'/\\1uS>t\u0007c\u0001B%QN)\u0001n!\u0012\u0004RAQ1qIB'\u0005\u0017\u00139K!1\u000e\u0005\r%#\u0002BB&\u0003\u0003\fqA];oi&lW-\u0003\u0003\u0004P\r%#!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8oeA!11KB,\u001b\t\u0019)F\u0003\u0003\u00026\n5\u0011\u0002\u0002BB\u0007+\"\"a!\u0011\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\r\t\u00057qLB1\u0011\u001d\u00119i\u001ba\u0001\u0005\u0017CqAa)l\u0001\u0004\u00119+A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\r\u001d41\u000f\t\u0007\u0003\u007f\u001bIg!\u001c\n\t\r-\u0014\u0011\u0019\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u0011\u0005}6q\u000eBF\u0005OKAa!\u001d\u0002B\n1A+\u001e9mKJB\u0011b!\u001em\u0003\u0003\u0005\rA!1\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0004|A!!1_B?\u0013\u0011\u0019yH!>\u0003\r=\u0013'.Z2u\u00059\u0019uN\u001c8fGRLwN\\%oM>\u001crA\\A_\u0005S\u0012y'\u0001\u0007sK\u001eL7\u000f\u001e:bi&|g.\u0006\u0002\u0004\nB!\u0011Q_BF\u0013\u0011\u0019i)a-\u0003'\rC\u0017M\u001c8fYJ+w-[:ue\u0006$\u0018n\u001c8\u0002\u001bI,w-[:ue\u0006$\u0018n\u001c8!\u0003\u001dA\u0017M\u001c3mKJ,\"Aa'\u0002\u0011!\fg\u000e\u001a7fe\u0002\nAc[3fa>\u0003XM\\(o!\u0016,'o\u00117pg\u0016$\u0017!F6fKB|\u0005/\u001a8P]B+WM]\"m_N,G\rI\u0001\u0011kN,'+Z:v[\u0016<&/\u001b;j]\u001e\f\u0011#^:f%\u0016\u001cX/\\3Xe&$\u0018N\\4!))\u0019\tka)\u0004&\u000e\u001d6\u0011\u0016\t\u0004\u0005\u0013r\u0007bBBCo\u0002\u00071\u0011\u0012\u0005\b\u0007#;\b\u0019\u0001BN\u0011\u001d\u00199j\u001ea\u0001\u00057Aqaa'x\u0001\u0004\u0011Y\u0002\u0006\u0006\u0004\"\u000e56qVBY\u0007gC\u0011b!\"y!\u0003\u0005\ra!#\t\u0013\rE\u0005\u0010%AA\u0002\tm\u0005\"CBLqB\u0005\t\u0019\u0001B\u000e\u0011%\u0019Y\n\u001fI\u0001\u0002\u0004\u0011Y\"\u0006\u0002\u00048*\"1\u0011\u0012Bk+\t\u0019YL\u000b\u0003\u0003\u001c\nU\u0017AD2paf$C-\u001a4bk2$HeM\u000b\u0003\u0007\u0003TCAa\u0007\u0003V\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\"D\u0003BB\u0006\u0007\u000fD\u0011ba\u0005\u0000\u0003\u0003\u0005\ra!\u0001\u0015\t\tm11\u001a\u0005\u000b\u0007'\t\u0019!!AA\u0002\r-A\u0003\u0002By\u0007\u001fD!ba\u0005\u0002\u0006\u0005\u0005\t\u0019AB\u0001)\u0011\u0011Yba5\t\u0015\rM\u00111BA\u0001\u0002\u0004\u0019Y!\u0001\bD_:tWm\u0019;j_:LeNZ8\u0011\t\t%\u0013qB\n\u0007\u0003\u001f\u0019Yn!\u0015\u0011\u001d\r\u001d3Q\\BE\u00057\u0013YBa\u0007\u0004\"&!1q\\B%\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g\u000e\u000e\u000b\u0003\u0007/$\"b!)\u0004f\u000e\u001d8\u0011^Bv\u0011!\u0019))!\u0006A\u0002\r%\u0005\u0002CBI\u0003+\u0001\rAa'\t\u0011\r]\u0015Q\u0003a\u0001\u00057A\u0001ba'\u0002\u0016\u0001\u0007!1\u0004\u000b\u0005\u0007_\u001c9\u0010\u0005\u0004\u0002@\u000e%4\u0011\u001f\t\r\u0003\u007f\u001b\u0019p!#\u0003\u001c\nm!1D\u0005\u0005\u0007k\f\tM\u0001\u0004UkBdW\r\u000e\u0005\u000b\u0007k\n9\"!AA\u0002\r\u0005&!G+qI\u0006$X\rU3oI&twm\u0016:ji\u0016\fe\u000e\u001a+iK:\u001c\"\"a\u0007\u0002>\u000eu(\u0011\u000eB8!\u0011\tYma@\n\t\u0011\u0005\u0011Q\u001a\u0002\"\u001d>\u001cVM]5bY&T\u0018\r^5p]Z+'/\u001b4jG\u0006$\u0018n\u001c8OK\u0016$W\rZ\u0001\u000fe\u0016l\u0017-\u001b8j]\u001e<&/\u001b;f+\t!9\u0001\u0005\u0003\u0003J\u0005=%\u0001\u0004)f]\u0012LgnZ,sSR,7\u0003BAH\u0003{#\"\u0001b\u0002\u0002\u0013\r|W.\\1oI\u0016\u0014\u0018a\u00023p/JLG/\u001a\u000b\u0005\t\u000f!)\u0002\u0003\u0005\u0005\u0018\u0005U\u0005\u0019ABQ\u0003\u0011IgNZ8\u0002\u000fI,G.Z1tKR\u0011AQ\u0004\t\u0005\u0003\u007f#y\"\u0003\u0003\u0005\"\u0005\u0005'\u0001B+oSRLc!a$\u0002\u001cFb$!E#naRL\b+\u001a8eS:<wK]5uKN!\u00111\u0014C\u0004)\t!Y\u0003\u0005\u0003\u0003J\u0005mE\u0003\u0002C\u0004\t_A\u0001\u0002b\u0006\u0002\"\u0002\u00071\u0011\u0015\u0002\u0013!\u0016tG-\u001b8h\u0005V4g-\u001a:Xe&$XmE\u00022\u0005c\t!bY8n[\u0006tG-\u001a:!\u00035\u0011X-\\1j]&tw\rR1uCB!A1\bC!\u001b\t!iD\u0003\u0003\u0005@\u0005]\u0016\u0001B;uS2LA\u0001b\u0011\u0005>\tQ!)\u001f;f'R\u0014\u0018N\\4\u0002\u0007\u0005\u001c7.\u0001\u0004ck\u001a4WM\u001d\t\u0005\t\u0017\"i%\u0004\u0002\u0003\n%!Aq\nB\u0005\u0005)\u0011\u0015\u0010^3Ck\u001a4WM]\u0001\u0005i\u0006LG\u000e\u0005\u0003\u0003*\u0012U\u0013\u0002\u0002C,\u0005w\u0013Ab\u0016:ji\u0016\u001cu.\\7b]\u0012$B\u0002b\u0017\u0005`\u0011\u0005D1\rC3\tO\u00022\u0001\"\u00182\u001b\u0005\u0001\u0001b\u0002C\bq\u0001\u0007!1\u0014\u0005\b\toA\u0004\u0019\u0001C\u001d\u0011\u001d!)\u0005\u000fa\u0001\u0007\u0017Aq\u0001b\u00129\u0001\u0004!I\u0005C\u0004\u0005Ra\u0002\r\u0001b\u0015\u0015\t\tEB1\u000e\u0005\b\t/I\u0004\u0019\u0001C7!\r\u0011\u0019D\u001c\u0002\u0011!\u0016tG-\u001b8h/JLG/\u001a$jY\u0016\u001cR\u0001\u0010B\u0019\tg\u0002BAa=\u0005v%!Aq\u000fB{\u0005!\u0011VO\u001c8bE2,\u0017a\u00034jY\u0016\u001c\u0005.\u00198oK2\u0004BAa\u0001\u0005~%!Aq\u0010B\u0003\u0005-1\u0015\u000e\\3DQ\u0006tg.\u001a7\u0002\r=4gm]3u!\u0011\ty\f\"\"\n\t\u0011\u001d\u0015\u0011\u0019\u0002\u0005\u0019>tw-A\u0005sK6\f\u0017N\\5oORqAQ\u0012CH\t##\u0019\n\"&\u0005\u0018\u0012e\u0005c\u0001C/y!9Aq\u0002#A\u0002\tm\u0005b\u0002C=\t\u0002\u0007A1\u0010\u0005\b\t\u0003#\u0005\u0019\u0001CB\u0011\u001d!I\t\u0012a\u0001\t\u0007Cq\u0001\"\u0012E\u0001\u0004\u00119\u000bC\u0004\u0005R\u0011\u0003\r\u0001b\u0015\u0015\t\tEBQ\u0014\u0005\b\t/)\u0005\u0019\u0001C7\u0003\r\u0011XO\\\u0001\u0010e\u0016l\u0017-\u001b8j]\u001e<&/\u001b;fA\u0005!qo\u001c:l+\t!9\u000b\u0005\u0004\u0002@\u0012%FQD\u0005\u0005\tW\u000b\tMA\u0005Gk:\u001cG/[8oa\u0005)qo\u001c:lAQ1A\u0011\u0017CZ\tk\u0003BA!\u0013\u0002\u001c!AA1AA\u0013\u0001\u0004!9\u0001\u0003\u0005\u0005$\u0006\u0015\u0002\u0019\u0001CT)\u0019!\t\f\"/\u0005<\"QA1AA\u0014!\u0003\u0005\r\u0001b\u0002\t\u0015\u0011\r\u0016q\u0005I\u0001\u0002\u0004!9+\u0006\u0002\u0005@*\"Aq\u0001Bk+\t!\u0019M\u000b\u0003\u0005(\nUG\u0003BB\u0006\t\u000fD!ba\u0005\u00022\u0005\u0005\t\u0019AB\u0001)\u0011\u0011Y\u0002b3\t\u0015\rM\u0011QGA\u0001\u0002\u0004\u0019Y\u0001\u0006\u0003\u0003r\u0012=\u0007BCB\n\u0003o\t\t\u00111\u0001\u0004\u0002Q!!1\u0004Cj\u0011)\u0019\u0019\"!\u0010\u0002\u0002\u0003\u000711B\u0001\u001a+B$\u0017\r^3QK:$\u0017N\\4Xe&$X-\u00118e)\",g\u000e\u0005\u0003\u0003J\u0005\u00053CBA!\t7\u001c\t\u0006\u0005\u0006\u0004H\r5Cq\u0001CT\tc#\"\u0001b6\u0015\r\u0011EF\u0011\u001dCr\u0011!!\u0019!a\u0012A\u0002\u0011\u001d\u0001\u0002\u0003CR\u0003\u000f\u0002\r\u0001b*\u0015\t\u0011\u001dH1\u001e\t\u0007\u0003\u007f\u001bI\u0007\";\u0011\u0011\u0005}6q\u000eC\u0004\tOC!b!\u001e\u0002J\u0005\u0005\t\u0019\u0001CY\u0005=9&/\u001b;f\r&dWMR1jY\u0016$7\u0003CA'\u0003{\u0013IGa\u001c\u0002\u0003\u0015,\"\u0001\">\u0011\t\rMCq_\u0005\u0005\ts\u001c)FA\u0006J\u001f\u0016C8-\u001a9uS>t\u0017AA3!)\u0011!y0\"\u0001\u0011\t\t%\u0013Q\n\u0005\t\tc\f\u0019\u00061\u0001\u0005vR!Aq`C\u0003\u0011)!\t0!\u0016\u0011\u0002\u0003\u0007AQ_\u000b\u0003\u000b\u0013QC\u0001\">\u0003VR!11BC\u0007\u0011)\u0019\u0019\"!\u0018\u0002\u0002\u0003\u00071\u0011\u0001\u000b\u0005\u00057)\t\u0002\u0003\u0006\u0004\u0014\u0005\u0005\u0014\u0011!a\u0001\u0007\u0017!BA!=\u0006\u0016!Q11CA2\u0003\u0003\u0005\ra!\u0001\u0015\t\tmQ\u0011\u0004\u0005\u000b\u0007'\tI'!AA\u0002\r-\u0011aD,sSR,g)\u001b7f\r\u0006LG.\u001a3\u0011\t\t%\u0013QN\n\u0007\u0003[*\tc!\u0015\u0011\u0011\r\u001dS1\u0005C{\t\u007fLA!\"\n\u0004J\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u0019\u0015\u0005\u0015uA\u0003\u0002C\u0000\u000bWA\u0001\u0002\"=\u0002t\u0001\u0007AQ\u001f\u000b\u0005\u000b_)\t\u0004\u0005\u0004\u0002@\u000e%DQ\u001f\u0005\u000b\u0007k\n)(!AA\u0002\u0011}\u0018\u0001D+oe\u0016<\u0017n\u001d;fe\u0016$\u0007\u0003\u0002B%\u0003w\u0012A\"\u00168sK\u001eL7\u000f^3sK\u0012\u001c\u0002\"a\u001f\u0002>\n%$q\u000e\u000b\u0003\u000bk!Baa\u0003\u0006@!Q11CAB\u0003\u0003\u0005\ra!\u0001\u0015\t\tmQ1\t\u0005\u000b\u0007'\t9)!AA\u0002\r-\u0011!E#naRL\b+\u001a8eS:<wK]5uK\u0006IAm\u001c(pi\"LgnZ\u0001\u000bI>tu\u000e\u001e5j]\u001e\u0004\u0013a\f#s_B\u0004\u0018N\\4Xe&$XMQ3dCV\u001cXm\u0016:ji&tw-S:TkN\u0004XM\u001c3fI\u0016C8-\u001a9uS>tWCAC(%\u0019)\t\u0006\">\u0006X\u00199Q1KAV\u0001\u0015=#\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0014\u0001\r#s_B\u0004\u0018N\\4Xe&$XMQ3dCV\u001cXm\u0016:ji&tw-S:TkN\u0004XM\u001c3fI\u0016C8-\u001a9uS>t\u0007\u0005\u0005\u0003\u0006Z\u0015\u0005TBAC.\u0015\u0011)i&b\u0018\u0002\u000f\r|g\u000e\u001e:pY*!AqHAa\u0013\u0011)\u0019'b\u0017\u0003\u00199{7\u000b^1dWR\u0013\u0018mY3\u0002Q\u0011\u0013x\u000e\u001d9j]\u001e<&/\u001b;f\u0005\u0016\u001c\u0017-^:f#V,W/Z%t\rVdG.\u0012=dKB$\u0018n\u001c8\u0016\u0005\u0015%$CBC6\tk,9FB\u0004\u0006T\u0005=\u0006!\"\u001b\u0002S\u0011\u0013x\u000e\u001d9j]\u001e<&/\u001b;f\u0005\u0016\u001c\u0017-^:f#V,W/Z%t\rVdG.\u0012=dKB$\u0018n\u001c8!\u0003)\u0001X-\u001a:DY>\u001cX\rZ\u0001\u0011oJLG/\u001b8h'V\u001c\b/\u001a8eK\u0012\f\u0001C]3bI&twmU;ta\u0016tG-\u001a3\u0002%%tG/\u001a:fgR,G-\u00138SKN,X.\u001a\t\u0007\u0003\u007f\u001bIGa'\u0002\u001b\rdwn]3e\u001b\u0016\u001c8/Y4f!\u0019\tyl!\u001b\u0006~A\u0019!1G+\u0002\u0019]\fGo\u00195fI\u0006\u001bGo\u001c:\u0002!]\fGo\u00195fI\u0006\u001bGo\u001c:`I\u0015\fH\u0003\u0002C\u000f\u000b\u000bC\u0011ba\u0005\u0010\u0003\u0003\u0005\rAa'\u0002\u001b]\fGo\u00195fI\u0006\u001bGo\u001c:!+\t)Y\t\u0005\u0004\u0002@\u000e%4\u0011R\u0001\u0011e\u0016<\u0017n\u001d;sCRLwN\\0%KF$B\u0001\"\b\u0006\u0012\"I11\u0003\n\u0002\u0002\u0003\u0007Q1R\u0001\u0010g\u0016$(+Z4jgR\u0014\u0018\r^5p]R!AQDCL\u0011\u001d\u0019)\t\u0006a\u0001\u0007\u0013\u000bQb]5h]\u0012+\u0017\r\u001e5QC\u000e$H\u0003\u0002C\u000f\u000b;Cq!a4\u0016\u0001\u0004\u0011Y*A\bv]NLwM\u001c#fCRD\u0007+Y2u\u000319(/\u001b;f!\u0016tG-\u001b8h\u0003Y9\u0018-\u001b;j]\u001e4uN\u001d*fO&\u001cHO]1uS>tGCBCT\u000b[+y\u000b\u0005\u0003\u0005^\u0015%\u0016\u0002BCV\u0003#\u0014qAU3dK&4X\rC\u0004\u0004\u0006b\u0001\ra!#\t\u000f\u0011=\u0001\u00041\u0001\u0003\u001c\u0006I1m\u001c8oK\u000e$X\r\u001a\u000b\u0005\u000bO+)\fC\u0004\u0005\u0018e\u0001\r\u0001\"\u001c\u0002\u0017A,WM]*f]R,uJ\u0012\u000b\u0005\u000bO+Y\fC\u0004\u0005\u0018i\u0001\r\u0001\"\u001c\u0002/\rdwn]5oO^KG\u000f\u001b)f]\u0012LgnZ,sSR,G\u0003CCT\u000b\u0003,\u0019-b2\t\u000f\u0011]1\u00041\u0001\u0005n!9QQY\u000eA\u0002\u0015]\u0014AD2m_N,7i\\7nC:$WM\u001d\u0005\b\u0005G[\u0002\u0019ACe!\u0011\u0011I+b3\n\t\u00155'1\u0018\u0002\u0011\u0007>tg.Z2uS>t7\t\\8tK\u0012\fqa\u00197pg&tw\r\u0006\u0004\u0006(\u0016MWQ\u001b\u0005\b\t/a\u0002\u0019\u0001C7\u0011\u001d))\r\ba\u0001\u000bo\n1\u0003[1oI2,wK]5uK6+7o]1hKN$B!b*\u0006\\\"9AqC\u000fA\u0002\u00115\u0014!D;oe\u0016<\u0017n\u001d;fe&tw-\u0006\u0002\u0006(\u0006y1m\\7qY\u0016$XmQ8o]\u0016\u001cG\u000f\u0006\u0005\u0005\u001e\u0015\u0015Xq]Cu\u0011\u001d\u0019)i\ba\u0001\u0007\u0013Cq\u0001b\u0004 \u0001\u0004\u0011Y\nC\u0004\u0006l~\u0001\r!\"<\u0002\u000f=\u0004H/[8ogB1Qq^C~\r\u0003qA!\"=\u0006x:!11DCz\u0013\u0011))p!\b\u0002\u0013%lW.\u001e;bE2,\u0017\u0002\u0002B@\u000bsTA!\">\u0004\u001e%!QQ`C\u0000\u0005-!&/\u0019<feN\f'\r\\3\u000b\t\t}T\u0011 \t\u0005\r\u00071IA\u0004\u0003\u0003,\u001a\u0015\u0011\u0002\u0002D\u0004\u0003g\u000bA!\u00138fi&!a1\u0002D\u0007\u00051\u0019vnY6fi>\u0003H/[8o\u0015\u001119!a-\u0002\u001dM,8\u000f]3oIJ+\u0017\rZ5oOR!AQ\u0004D\n\u0011\u001d!9\u0002\ta\u0001\t[\nQB]3tk6,'+Z1eS:<GC\u0002C\u000f\r31Y\u0002C\u0004\u0005\u0018\u0005\u0002\r\u0001\"\u001c\t\u000f\u0015\u0015\u0017\u00051\u0001\u0006x\u00051Am\u001c*fC\u0012$b\u0001\"\b\u0007\"\u0019\r\u0002b\u0002C\fE\u0001\u0007AQ\u000e\u0005\b\u000b\u000b\u0014\u0003\u0019AC<)\u0011!iBb\n\t\u000f\u0011]1\u00051\u0001\u0005n\u0005Y1\r\\8tKJ+\u0017m]8o+\t1iC\u0005\u0005\u00070\u0015%'\u0011NB)\r\u0019)\u0019\u0006\u0001\u0001\u0007.\u0005Y\u0001.\u00198eY\u0016\u001cEn\\:f)!!iB\"\u000e\u00078\u0019e\u0002b\u0002C\fK\u0001\u0007AQ\u000e\u0005\b\u000b\u000b,\u0003\u0019AC<\u0011\u001d\u0011\u0019+\na\u0001\u000b\u0013\f\u0011\u0003Z8DY>\u001cXmQ8o]\u0016\u001cG/[8o)!!iBb\u0010\u0007B\u0019\r\u0003bBBIM\u0001\u0007!1\u0014\u0005\b\u000b\u000b4\u0003\u0019AC<\u0011\u001d\u0011\u0019K\na\u0001\u000b\u0013\f1\u0002[1oI2,WI\u001d:peR1AQ\u0004D%\r\u0017Bqa!%(\u0001\u0004\u0011Y\nC\u0004\u0007N\u001d\u0002\r\u0001\">\u0002\u0013\u0015D8-\u001a9uS>t\u0017AE:bM\u0016\u001c\u0006.\u001e;e_^tw*\u001e;qkR$\"Aa\u0007\u0002\u0015\u0015DHO]1di6\u001bx\r\u0006\u0003\u0007X\u0019m\u0003\u0003\u0002BG\r3JAAa?\u0003\u001a\"9aQL\u0015A\u0002\u0019}\u0013!\u0001;\u0011\t\tEd\u0011M\u0005\u0005\rG\u0012)IA\u0005UQJ|w/\u00192mK\"\u001a\u0011Fb\u001a\u0011\t\u0019%d1N\u0007\u0003\u0005?LAA\"\u001c\u0003`\n9A/Y5me\u0016\u001c\u0017\u0001\u00049sKB\f'/Z!c_J$\u0018\u0001C:u_B<\u0016\u000e\u001e5\u0015\r\u0011uaQ\u000fD=\u0011\u001d19h\u000ba\u0001\u000b{\n\u0011b\u00197pg\u0016LeNZ8\t\u0013\u0019m4\u0006%AA\u0002\tm\u0011aC:i_VdG-\u00112peR\f!c\u001d;pa^KG\u000f\u001b\u0013eK\u001a\fW\u000f\u001c;%e\u0005A\u0001o\\:u'R|\u0007/A\u0006q_N$(+Z:uCJ$H\u0003\u0002C\u000f\r\u000bCqAb\"/\u0001\u00041y&\u0001\u0004sK\u0006\u001cxN\\\u0001\r!\u0016tG-\u001b8h/JLG/\u001a\u000b\u0007\u0005c1iIb$\t\u000f\u0011=q\u00061\u0001\u0003\u001c\"9a\u0011S\u0018A\u0002\u0011M\u0013!B<sSR,\u0017A\u0005)f]\u0012Lgn\u001a\"vM\u001a,'o\u0016:ji\u0016$\"\u0002b\u0017\u0007\u0018\u001aeeQ\u0014DP\u0011\u001d!y\u0001\ra\u0001\u00057CqAb'1\u0001\u0004!I$\u0001\u0003eCR\f\u0007b\u0002C#a\u0001\u0007!q\u0015\u0005\b\t#\u0002\u0004\u0019\u0001C*\u0003A\u0001VM\u001c3j]\u001e<&/\u001b;f\r&dW\r\u0006\b\u0005\u000e\u001a\u0015fq\u0015D\\\rs3iLb0\t\u000f\u0011=1\b1\u0001\u0003\u001c\"9a\u0011V\u001eA\u0002\u0019-\u0016\u0001\u00034jY\u0016\u0004\u0016\r\u001e5\u0011\t\u00195f1W\u0007\u0003\r_SAA\"-\u0003\n\u0005!a-\u001b7f\u0013\u00111)Lb,\u0003\tA\u000bG\u000f\u001b\u0005\b\t\u0003[\u0004\u0019\u0001CB\u0011\u001d1Yl\u000fa\u0001\t\u0007\u000bQaY8v]RDq\u0001\"\u0012<\u0001\u0004\u00119\u000bC\u0004\u0005Rm\u0002\r\u0001b\u0015")
public abstract class TcpConnection
implements Actor,
ActorLogging,
RequiresMessageQueue<UnboundedMessageQueueSemantics> {
    private final TcpExt tcp;
    private final SocketChannel channel;
    private final boolean pullMode;
    public PendingWrite akka$io$TcpConnection$$pendingWrite;
    private boolean peerClosed;
    public boolean akka$io$TcpConnection$$writingSuspended;
    public boolean akka$io$TcpConnection$$readingSuspended;
    public Option<ActorRef> akka$io$TcpConnection$$interestedInResume;
    private Option<CloseInformation> closedMessage;
    private ActorRef watchedActor;
    private Option<ChannelRegistration> registration;
    private LoggingAdapter akka$actor$ActorLogging$$_log;
    private ActorContext context;
    private ActorRef self;

    public static IOException DroppingWriteBecauseQueueIsFullException() {
        return TcpConnection$.MODULE$.DroppingWriteBecauseQueueIsFullException();
    }

    public static IOException DroppingWriteBecauseWritingIsSuspendedException() {
        return TcpConnection$.MODULE$.DroppingWriteBecauseWritingIsSuspendedException();
    }

    public static Function0<BoxedUnit> doNothing() {
        return TcpConnection$.MODULE$.doNothing();
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
    public void preStart() throws Exception {
        Actor.preStart$(this);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        Actor.preRestart$(this, reason, message);
    }

    @Override
    public void unhandled(Object message) {
        Actor.unhandled$(this, message);
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

    public TcpExt tcp() {
        return this.tcp;
    }

    public SocketChannel channel() {
        return this.channel;
    }

    public boolean pullMode() {
        return this.pullMode;
    }

    private ActorRef watchedActor() {
        return this.watchedActor;
    }

    private void watchedActor_$eq(ActorRef x$1) {
        this.watchedActor = x$1;
    }

    private Option<ChannelRegistration> registration() {
        return this.registration;
    }

    private void registration_$eq(Option<ChannelRegistration> x$1) {
        this.registration = x$1;
    }

    public void setRegistration(ChannelRegistration registration) {
        this.registration_$eq(new Some<ChannelRegistration>(registration));
    }

    public void signDeathPact(ActorRef actor) {
        this.unsignDeathPact();
        this.watchedActor_$eq(actor);
        this.context().watch(this.watchedActor());
    }

    public void unsignDeathPact() {
        if (this.watchedActor() != this.context().system().deadLetters()) {
            this.context().unwatch(this.watchedActor());
            return;
        }
    }

    public boolean writePending() {
        return this.akka$io$TcpConnection$$pendingWrite != TcpConnection$EmptyPendingWrite$.MODULE$;
    }

    public PartialFunction<Object, BoxedUnit> waitingForRegistration(ChannelRegistration registration, ActorRef commander) {
        return new Serializable(this, commander, registration){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ TcpConnection $outer;
            private final ActorRef commander$1;
            private final ChannelRegistration registration$1;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Tcp.Register) {
                    Tcp.Register register = (Tcp.Register)A1;
                    ActorRef handler = register.handler();
                    boolean keepOpenOnPeerClosed = register.keepOpenOnPeerClosed();
                    boolean useResumeWriting = register.useResumeWriting();
                    ActorRef actorRef2 = handler;
                    ActorRef actorRef3 = this.commander$1;
                    if (actorRef2 == null ? actorRef3 != null : !((Object)actorRef2).equals(actorRef3)) {
                        this.$outer.signDeathPact(handler);
                    }
                    if (this.$outer.tcp().Settings().TraceLogging()) {
                        this.$outer.log().debug("[{}] registered as connection handler", handler);
                    }
                    ConnectionInfo info = new ConnectionInfo(this.registration$1, handler, keepOpenOnPeerClosed, useResumeWriting);
                    this.$outer.context().setReceiveTimeout(Duration$.MODULE$.Undefined());
                    this.$outer.context().become(this.$outer.connected(info));
                    if (!this.$outer.pullMode() || !this.$outer.akka$io$TcpConnection$$readingSuspended) {
                        this.$outer.resumeReading(info, None$.MODULE$);
                        return (B1)BoxedUnit.UNIT;
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                if (Tcp$ResumeReading$.MODULE$.equals(A1)) {
                    this.$outer.akka$io$TcpConnection$$readingSuspended = false;
                    return (B1)BoxedUnit.UNIT;
                }
                if (Tcp$SuspendReading$.MODULE$.equals(A1)) {
                    this.$outer.akka$io$TcpConnection$$readingSuspended = true;
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Tcp.CloseCommand) {
                    Tcp.CloseCommand closeCommand = (Tcp.CloseCommand)A1;
                    ConnectionInfo info = new ConnectionInfo(this.registration$1, this.commander$1, false, false);
                    this.$outer.handleClose(info, new Some<ActorRef>(this.$outer.sender()), closeCommand.event());
                    return (B1)BoxedUnit.UNIT;
                }
                if (ReceiveTimeout$.MODULE$.equals(A1)) {
                    this.$outer.log().debug("Configured registration timeout of [{}] expired, stopping", this.$outer.tcp().Settings().RegisterTimeout());
                    this.$outer.context().stop(this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof Tcp.Register) {
                    return true;
                }
                if (Tcp$ResumeReading$.MODULE$.equals(object)) {
                    return true;
                }
                if (Tcp$SuspendReading$.MODULE$.equals(object)) {
                    return true;
                }
                if (object instanceof Tcp.CloseCommand) {
                    return true;
                }
                return ReceiveTimeout$.MODULE$.equals(object);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.commander$1 = commander$1;
                this.registration$1 = registration$1;
            }
        };
    }

    public PartialFunction<Object, BoxedUnit> connected(ConnectionInfo info) {
        return this.handleWriteMessages(info).orElse(new Serializable(this, info){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ TcpConnection $outer;
            private final ConnectionInfo info$1;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (Tcp$SuspendReading$.MODULE$.equals(A1)) {
                    this.$outer.suspendReading(this.info$1);
                    return (B1)BoxedUnit.UNIT;
                }
                if (Tcp$ResumeReading$.MODULE$.equals(A1)) {
                    this.$outer.resumeReading(this.info$1, None$.MODULE$);
                    return (B1)BoxedUnit.UNIT;
                }
                if (SelectionHandler$ChannelReadable$.MODULE$.equals(A1)) {
                    this.$outer.doRead(this.info$1, None$.MODULE$);
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Tcp.CloseCommand) {
                    Tcp.CloseCommand closeCommand = (Tcp.CloseCommand)A1;
                    this.$outer.handleClose(this.info$1, new Some<ActorRef>(this.$outer.sender()), closeCommand.event());
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (Tcp$SuspendReading$.MODULE$.equals(object)) {
                    return true;
                }
                if (Tcp$ResumeReading$.MODULE$.equals(object)) {
                    return true;
                }
                if (SelectionHandler$ChannelReadable$.MODULE$.equals(object)) {
                    return true;
                }
                return object instanceof Tcp.CloseCommand;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.info$1 = info$1;
            }
        });
    }

    public PartialFunction<Object, BoxedUnit> peerSentEOF(ConnectionInfo info) {
        return this.handleWriteMessages(info).orElse(new Serializable(this, info){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ TcpConnection $outer;
            private final ConnectionInfo info$2;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Tcp.CloseCommand) {
                    Tcp.CloseCommand closeCommand = (Tcp.CloseCommand)A1;
                    this.$outer.handleClose(this.info$2, new Some<ActorRef>(this.$outer.sender()), closeCommand.event());
                    return (B1)BoxedUnit.UNIT;
                }
                if (Tcp$ResumeReading$.MODULE$.equals(A1) ? true : SelectionHandler$ChannelReadable$.MODULE$.equals(A1)) {
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof Tcp.CloseCommand) {
                    return true;
                }
                return Tcp$ResumeReading$.MODULE$.equals(object) ? true : SelectionHandler$ChannelReadable$.MODULE$.equals(object);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.info$2 = info$2;
            }
        });
    }

    public PartialFunction<Object, BoxedUnit> closingWithPendingWrite(ConnectionInfo info, Option<ActorRef> closeCommander, Tcp.ConnectionClosed closedEvent) {
        return new Serializable(this, info, closeCommander, closedEvent){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ TcpConnection $outer;
            private final ConnectionInfo info$3;
            private final Option closeCommander$1;
            private final Tcp.ConnectionClosed closedEvent$1;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (Tcp$SuspendReading$.MODULE$.equals(A1)) {
                    this.$outer.suspendReading(this.info$3);
                    return (B1)BoxedUnit.UNIT;
                }
                if (Tcp$ResumeReading$.MODULE$.equals(A1)) {
                    this.$outer.resumeReading(this.info$3, this.closeCommander$1);
                    return (B1)BoxedUnit.UNIT;
                }
                if (SelectionHandler$ChannelReadable$.MODULE$.equals(A1)) {
                    this.$outer.doRead(this.info$3, this.closeCommander$1);
                    return (B1)BoxedUnit.UNIT;
                }
                if (SelectionHandler$ChannelWritable$.MODULE$.equals(A1)) {
                    this.$outer.doWrite(this.info$3);
                    if (!this.$outer.writePending()) {
                        this.$outer.handleClose(this.info$3, this.closeCommander$1, this.closedEvent$1);
                        return (B1)BoxedUnit.UNIT;
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof UpdatePendingWriteAndThen) {
                    UpdatePendingWriteAndThen updatePendingWriteAndThen = (UpdatePendingWriteAndThen)A1;
                    PendingWrite remaining = updatePendingWriteAndThen.remainingWrite();
                    Function0<BoxedUnit> work = updatePendingWriteAndThen.work();
                    this.$outer.akka$io$TcpConnection$$pendingWrite = remaining;
                    work.apply$mcV$sp();
                    if (this.$outer.writePending()) {
                        this.info$3.registration().enableInterest(4);
                        return (B1)BoxedUnit.UNIT;
                    }
                    this.$outer.handleClose(this.info$3, this.closeCommander$1, this.closedEvent$1);
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof WriteFileFailed) {
                    WriteFileFailed writeFileFailed = (WriteFileFailed)A1;
                    IOException e = writeFileFailed.e();
                    this.$outer.handleError(this.info$3.handler(), e);
                    return (B1)BoxedUnit.UNIT;
                }
                if (Tcp$Abort$.MODULE$.equals(A1)) {
                    this.$outer.handleClose(this.info$3, new Some<ActorRef>(this.$outer.sender()), Tcp$Aborted$.MODULE$);
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (Tcp$SuspendReading$.MODULE$.equals(object)) {
                    return true;
                }
                if (Tcp$ResumeReading$.MODULE$.equals(object)) {
                    return true;
                }
                if (SelectionHandler$ChannelReadable$.MODULE$.equals(object)) {
                    return true;
                }
                if (SelectionHandler$ChannelWritable$.MODULE$.equals(object)) {
                    return true;
                }
                if (object instanceof UpdatePendingWriteAndThen) {
                    return true;
                }
                if (object instanceof WriteFileFailed) {
                    return true;
                }
                return Tcp$Abort$.MODULE$.equals(object);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.info$3 = info$3;
                this.closeCommander$1 = closeCommander$1;
                this.closedEvent$1 = closedEvent$1;
            }
        };
    }

    public PartialFunction<Object, BoxedUnit> closing(ConnectionInfo info, Option<ActorRef> closeCommander) {
        return new Serializable(this, info, closeCommander){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ TcpConnection $outer;
            private final ConnectionInfo info$4;
            private final Option closeCommander$2;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (Tcp$SuspendReading$.MODULE$.equals(A1)) {
                    this.$outer.suspendReading(this.info$4);
                    return (B1)BoxedUnit.UNIT;
                }
                if (Tcp$ResumeReading$.MODULE$.equals(A1)) {
                    this.$outer.resumeReading(this.info$4, this.closeCommander$2);
                    return (B1)BoxedUnit.UNIT;
                }
                if (SelectionHandler$ChannelReadable$.MODULE$.equals(A1)) {
                    this.$outer.doRead(this.info$4, this.closeCommander$2);
                    return (B1)BoxedUnit.UNIT;
                }
                if (Tcp$Close$.MODULE$.equals(A1)) {
                    this.$outer.doCloseConnection(this.info$4.handler(), this.closeCommander$2, Tcp$Close$.MODULE$.event());
                    return (B1)BoxedUnit.UNIT;
                }
                if (Tcp$Abort$.MODULE$.equals(A1)) {
                    this.$outer.handleClose(this.info$4, new Some<ActorRef>(this.$outer.sender()), Tcp$Aborted$.MODULE$);
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (Tcp$SuspendReading$.MODULE$.equals(object)) {
                    return true;
                }
                if (Tcp$ResumeReading$.MODULE$.equals(object)) {
                    return true;
                }
                if (SelectionHandler$ChannelReadable$.MODULE$.equals(object)) {
                    return true;
                }
                if (Tcp$Close$.MODULE$.equals(object)) {
                    return true;
                }
                return Tcp$Abort$.MODULE$.equals(object);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.info$4 = info$4;
                this.closeCommander$2 = closeCommander$2;
            }
        };
    }

    public PartialFunction<Object, BoxedUnit> handleWriteMessages(ConnectionInfo info) {
        return new Serializable(this, info){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ TcpConnection $outer;
            private final ConnectionInfo info$5;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (SelectionHandler$ChannelWritable$.MODULE$.equals(A1)) {
                    if (this.$outer.writePending()) {
                        this.$outer.doWrite(this.info$5);
                        if (!this.$outer.writePending() && this.$outer.akka$io$TcpConnection$$interestedInResume.nonEmpty()) {
                            this.$outer.akka$io$TcpConnection$$interestedInResume.get().$bang(Tcp$WritingResumed$.MODULE$, this.$outer.self());
                            this.$outer.akka$io$TcpConnection$$interestedInResume = None$.MODULE$;
                            return (B1)BoxedUnit.UNIT;
                        }
                        return (B1)BoxedUnit.UNIT;
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Tcp.WriteCommand) {
                    Tcp.WriteCommand writeCommand = (Tcp.WriteCommand)A1;
                    if (this.$outer.akka$io$TcpConnection$$writingSuspended) {
                        if (this.$outer.tcp().Settings().TraceLogging()) {
                            this.$outer.log().debug("Dropping write because writing is suspended");
                        }
                        this.$outer.sender().$bang(new Tcp.CommandFailed(writeCommand).withCause(TcpConnection$.MODULE$.DroppingWriteBecauseWritingIsSuspendedException()), this.$outer.self());
                        return (B1)BoxedUnit.UNIT;
                    }
                    if (this.$outer.writePending()) {
                        if (this.$outer.tcp().Settings().TraceLogging()) {
                            this.$outer.log().debug("Dropping write because queue is full");
                        }
                        this.$outer.sender().$bang(new Tcp.CommandFailed(writeCommand).withCause(TcpConnection$.MODULE$.DroppingWriteBecauseQueueIsFullException()), this.$outer.self());
                        if (this.info$5.useResumeWriting()) {
                            this.$outer.akka$io$TcpConnection$$writingSuspended = true;
                            return (B1)BoxedUnit.UNIT;
                        }
                        return (B1)BoxedUnit.UNIT;
                    }
                    this.$outer.akka$io$TcpConnection$$pendingWrite = this.$outer.PendingWrite(this.$outer.sender(), writeCommand);
                    if (this.$outer.writePending()) {
                        this.$outer.doWrite(this.info$5);
                        return (B1)BoxedUnit.UNIT;
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                if (Tcp$ResumeWriting$.MODULE$.equals(A1)) {
                    this.$outer.akka$io$TcpConnection$$writingSuspended = false;
                    if (this.$outer.writePending()) {
                        if (this.$outer.akka$io$TcpConnection$$interestedInResume.isEmpty()) {
                            this.$outer.akka$io$TcpConnection$$interestedInResume = new Some<ActorRef>(this.$outer.sender());
                            return (B1)BoxedUnit.UNIT;
                        }
                        this.$outer.sender().$bang(new Tcp.CommandFailed(Tcp$ResumeWriting$.MODULE$), this.$outer.self());
                        return (B1)BoxedUnit.UNIT;
                    }
                    this.$outer.sender().$bang(Tcp$WritingResumed$.MODULE$, this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof UpdatePendingWriteAndThen) {
                    UpdatePendingWriteAndThen updatePendingWriteAndThen = (UpdatePendingWriteAndThen)A1;
                    PendingWrite remaining = updatePendingWriteAndThen.remainingWrite();
                    Function0<BoxedUnit> work = updatePendingWriteAndThen.work();
                    this.$outer.akka$io$TcpConnection$$pendingWrite = remaining;
                    work.apply$mcV$sp();
                    if (this.$outer.writePending()) {
                        this.info$5.registration().enableInterest(4);
                        return (B1)BoxedUnit.UNIT;
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof WriteFileFailed) {
                    WriteFileFailed writeFileFailed = (WriteFileFailed)A1;
                    IOException e = writeFileFailed.e();
                    this.$outer.handleError(this.info$5.handler(), e);
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (SelectionHandler$ChannelWritable$.MODULE$.equals(object)) {
                    return true;
                }
                if (object instanceof Tcp.WriteCommand) {
                    return true;
                }
                if (Tcp$ResumeWriting$.MODULE$.equals(object)) {
                    return true;
                }
                if (object instanceof UpdatePendingWriteAndThen) {
                    return true;
                }
                return object instanceof WriteFileFailed;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.info$5 = info$5;
            }
        };
    }

    public PartialFunction<Object, BoxedUnit> unregistering() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ TcpConnection $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (TcpConnection$Unregistered$.MODULE$.equals(A1)) {
                    this.$outer.context().stop(this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                return (B1)BoxedUnit.UNIT;
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (TcpConnection$Unregistered$.MODULE$.equals(object)) {
                    return true;
                }
                return true;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public void completeConnect(ChannelRegistration registration, ActorRef commander, Iterable<Inet.SocketOption> options) {
        this.registration_$eq(new Some<ChannelRegistration>(registration));
        try {
            this.channel().socket().setTcpNoDelay(true);
        }
        catch (SocketException e) {
            this.log().debug("Could not enable TcpNoDelay: {}", e.getMessage());
        }
        options.foreach((Function1<Inet.SocketOption, Object> & Serializable)x$1 -> {
            x$1.afterConnect(this.channel().socket());
            return BoxedUnit.UNIT;
        });
        commander.$bang(new Tcp.Connected((InetSocketAddress)this.channel().socket().getRemoteSocketAddress(), (InetSocketAddress)this.channel().socket().getLocalSocketAddress()), this.self());
        this.context().setReceiveTimeout(this.tcp().Settings().RegisterTimeout());
        if (this.tcp().Settings().WindowsConnectionAbortWorkaroundEnabled()) {
            registration.enableInterest(8);
        }
        this.context().become(this.waitingForRegistration(registration, commander));
    }

    public void suspendReading(ConnectionInfo info) {
        this.akka$io$TcpConnection$$readingSuspended = true;
        info.registration().disableInterest(1);
    }

    public void resumeReading(ConnectionInfo info, Option<ActorRef> closeCommander) {
        this.akka$io$TcpConnection$$readingSuspended = false;
        this.doRead(info, closeCommander);
    }

    public void doRead(ConnectionInfo info, Option<ActorRef> closeCommander) {
        if (!this.akka$io$TcpConnection$$readingSuspended) {
            block14: {
                ByteBuffer buffer = this.tcp().bufferPool().acquire();
                try {
                    try {
                        boolean bl = false;
                        ReadResult readResult = null;
                        ReadResult readResult2 = this.innerRead$1(buffer, this.tcp().Settings().ReceivedMessageSizeLimit(), info);
                        if (TcpConnection$AllRead$.MODULE$.equals(readResult2)) {
                            break block14;
                        }
                        if (TcpConnection$MoreDataWaiting$.MODULE$.equals(readResult2)) {
                            if (!this.pullMode()) {
                                this.self().$bang(SelectionHandler$ChannelReadable$.MODULE$, this.self());
                            }
                            break block14;
                        }
                        if (TcpConnection$EndOfStream$.MODULE$.equals(readResult2)) {
                            bl = true;
                            readResult = readResult2;
                            if (this.channel().socket().isOutputShutdown()) {
                                if (this.tcp().Settings().TraceLogging()) {
                                    this.log().debug("Read returned end-of-stream, our side already closed");
                                }
                                this.doCloseConnection(info.handler(), closeCommander, Tcp$ConfirmedClosed$.MODULE$);
                                break block14;
                            }
                        }
                        if (bl) {
                            if (this.tcp().Settings().TraceLogging()) {
                                this.log().debug("Read returned end-of-stream, our side not yet closed");
                            }
                            this.handleClose(info, closeCommander, Tcp$PeerClosed$.MODULE$);
                            break block14;
                        }
                        throw new MatchError(readResult2);
                    }
                    catch (IOException e) {
                        this.handleError(info.handler(), e);
                    }
                }
                finally {
                    this.tcp().bufferPool().release(buffer);
                }
            }
            return;
        }
    }

    public void doWrite(ConnectionInfo info) {
        this.akka$io$TcpConnection$$pendingWrite = this.akka$io$TcpConnection$$pendingWrite.doWrite(info);
    }

    public Tcp.ConnectionClosed closeReason() {
        if (this.channel().socket().isOutputShutdown()) {
            return Tcp$ConfirmedClosed$.MODULE$;
        }
        return Tcp$PeerClosed$.MODULE$;
    }

    public void handleClose(ConnectionInfo info, Option<ActorRef> closeCommander, Tcp.ConnectionClosed closedEvent) {
        Tcp.ConnectionClosed connectionClosed = closedEvent;
        if (Tcp$Aborted$.MODULE$.equals(connectionClosed)) {
            if (this.tcp().Settings().TraceLogging()) {
                this.log().debug("Got Abort command. RESETing connection.");
            }
            this.doCloseConnection(info.handler(), closeCommander, closedEvent);
            return;
        }
        if (Tcp$PeerClosed$.MODULE$.equals(connectionClosed) && info.keepOpenOnPeerClosed()) {
            info.handler().$bang(Tcp$PeerClosed$.MODULE$, this.self());
            this.peerClosed = true;
            this.context().become(this.peerSentEOF(info));
            return;
        }
        if (this.writePending()) {
            this.unsignDeathPact();
            if (this.tcp().Settings().TraceLogging()) {
                this.log().debug("Got Close command but write is still pending.");
            }
            this.context().become(this.closingWithPendingWrite(info, closeCommander, closedEvent));
            return;
        }
        if (Tcp$ConfirmedClosed$.MODULE$.equals(connectionClosed)) {
            if (this.tcp().Settings().TraceLogging()) {
                this.log().debug("Got ConfirmedClose command, sending FIN.");
            }
            if (this.peerClosed || !this.safeShutdownOutput()) {
                this.doCloseConnection(info.handler(), closeCommander, closedEvent);
                return;
            }
            this.context().become(this.closing(info, closeCommander));
            return;
        }
        if (this.tcp().Settings().TraceLogging()) {
            this.log().debug("Got Close command, closing connection.");
        }
        this.doCloseConnection(info.handler(), closeCommander, closedEvent);
    }

    public void doCloseConnection(ActorRef handler, Option<ActorRef> closeCommander, Tcp.ConnectionClosed closedEvent) {
        this.stopWith(new CloseInformation((Set)((SetOps)Predef$.MODULE$.Set().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new ActorRef[]{handler}))).$plus$plus(closeCommander), closedEvent), this.stopWith$default$2());
    }

    public void handleError(ActorRef handler, IOException exception) {
        this.log().debug("Closing connection due to IO error {}", exception);
        this.stopWith(new CloseInformation((Set)Predef$.MODULE$.Set().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new ActorRef[]{handler})), new Tcp.ErrorClosed(this.extractMsg(exception))), this.stopWith$default$2());
    }

    public boolean safeShutdownOutput() {
        boolean bl;
        try {
            this.channel().socket().shutdownOutput();
            bl = true;
        }
        catch (SocketException socketException) {
            bl = false;
        }
        return bl;
    }

    private String extractMsg(Throwable t2) {
        String string;
        block3: while (true) {
            if (t2 == null) {
                return "unknown";
            }
            string = t2.getMessage();
            switch (string == null ? 0 : string.hashCode()) {
                case 0: {
                    if (string != null && !"".equals(string)) break block3;
                    break;
                }
                default: {
                    break block3;
                }
            }
            t2 = t2.getCause();
        }
        return string;
    }

    public void prepareAbort() {
        try {
            this.channel().socket().setSoLinger(true, 0);
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable e = option.get();
                if (this.tcp().Settings().TraceLogging()) {
                    this.log().debug("setSoLinger(true, 0) failed with [{}]", e);
                }
            }
            throw throwable;
        }
    }

    public void stopWith(CloseInformation closeInfo, boolean shouldAbort) {
        Option<ChannelRegistration> option;
        this.closedMessage = new Some<CloseInformation>(closeInfo);
        this.unsignDeathPact();
        Tcp.Event event = closeInfo.closedEvent();
        Tcp$Aborted$ tcp$Aborted$ = Tcp$Aborted$.MODULE$;
        if (!(event == null ? tcp$Aborted$ != null : !event.equals(tcp$Aborted$)) || shouldAbort) {
            this.prepareAbort();
        }
        if (None$.MODULE$.equals(option = this.registration())) {
            this.context().stop(this.self());
            return;
        }
        if (option instanceof Some) {
            Some some = (Some)option;
            ChannelRegistration reg = (ChannelRegistration)some.value();
            this.context().become(this.unregistering());
            reg.cancelAndClose(() -> this.self().$bang(TcpConnection$Unregistered$.MODULE$, this.self()));
            return;
        }
        throw new MatchError(option);
    }

    public boolean stopWith$default$2() {
        return false;
    }

    @Override
    public void postStop() {
        if (this.writePending()) {
            this.akka$io$TcpConnection$$pendingWrite.release();
        }
        Set interestedInClose = (Set)((IterableOps)(this.writePending() ? Predef$.MODULE$.Set().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new ActorRef[]{this.akka$io$TcpConnection$$pendingWrite.commander()})) : Predef$.MODULE$.Set().empty())).$plus$plus(((AbstractIterable)this.closedMessage.toList().flatMap((Function1<CloseInformation, Set> & Serializable)x$2 -> x$2.notificationsTo())).toSet());
        if (this.channel().isOpen()) {
            this.prepareAbort();
        }
        if (!this.channel().isOpen() || this.isCommandFailed$1() || this.registration().isEmpty()) {
            this.notifyInterested$1(interestedInClose);
            return;
        }
        this.registration().foreach((Function1<ChannelRegistration, Object> & Serializable)x$4 -> {
            x$4.cancelAndClose(() -> this.notifyInterested$1(interestedInClose));
            return BoxedUnit.UNIT;
        });
    }

    @Override
    public void postRestart(Throwable reason) {
        throw new IllegalStateException("Restarting not supported for connection actors.");
    }

    public PendingWrite PendingWrite(ActorRef commander, Tcp.WriteCommand write) {
        return this.create$1(write, Tcp$Write$.MODULE$.empty(), commander);
    }

    public PendingBufferWrite PendingBufferWrite(ActorRef commander, ByteString data, Tcp.Event ack, Tcp.WriteCommand tail) {
        PendingBufferWrite pendingBufferWrite;
        ByteBuffer buffer = this.tcp().bufferPool().acquire();
        try {
            int copied = data.copyToBuffer(buffer);
            buffer.flip();
            pendingBufferWrite = new PendingBufferWrite(this, commander, data.drop(copied), ack, buffer, tail);
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable e = option.get();
                this.tcp().bufferPool().release(buffer);
                throw e;
            }
            throw throwable;
        }
        return pendingBufferWrite;
    }

    public PendingWriteFile PendingWriteFile(ActorRef commander, Path filePath, long offset, long count, Tcp.Event ack, Tcp.WriteCommand tail) {
        return new PendingWriteFile(this, commander, FileChannel.open(filePath, new OpenOption[0]), offset, count, ack, tail);
    }

    private final ReadResult innerRead$1(ByteBuffer buffer, int remainingLimit, ConnectionInfo info$6) {
        while (remainingLimit > 0) {
            int n;
            buffer.clear();
            int maxBufferSpace = package$.MODULE$.min(this.tcp().Settings().DirectBufferSize(), remainingLimit);
            buffer.limit(maxBufferSpace);
            int readBytes = this.channel().read(buffer);
            buffer.flip();
            if (this.tcp().Settings().TraceLogging()) {
                this.log().debug("Read [{}] bytes.", BoxesRunTime.boxToInteger(readBytes));
            }
            if (readBytes > 0) {
                info$6.handler().$bang(new Tcp.Received(ByteString$.MODULE$.apply(buffer)), this.self());
            }
            if (maxBufferSpace == (n = readBytes)) {
                if (this.pullMode()) {
                    return TcpConnection$MoreDataWaiting$.MODULE$;
                }
                remainingLimit -= maxBufferSpace;
                continue;
            }
            if (n >= 0) {
                if (!this.pullMode() || n == 0) {
                    info$6.registration().enableInterest(1);
                }
                return TcpConnection$AllRead$.MODULE$;
            }
            if (-1 == n) {
                return TcpConnection$EndOfStream$.MODULE$;
            }
            throw new IllegalStateException(new StringBuilder(37).append("Unexpected value returned from read: ").append(readBytes).toString());
        }
        return TcpConnection$MoreDataWaiting$.MODULE$;
    }

    public static final /* synthetic */ boolean $anonfun$postStop$2(CloseInformation x$3) {
        return x$3.closedEvent() instanceof Tcp.CommandFailed;
    }

    private final boolean isCommandFailed$1() {
        return this.closedMessage.exists((Function1<CloseInformation, Object> & Serializable)x$3 -> BoxesRunTime.boxToBoolean(TcpConnection.$anonfun$postStop$2(x$3)));
    }

    private final void notifyInterested$1(Set interestedInClose$1) {
        this.closedMessage.foreach((Function1<CloseInformation, Object> & Serializable)msg -> {
            interestedInClose$1.foreach((Function1<ActorRef, Object> & Serializable)ref -> {
                ref.$bang(msg.closedEvent(), $this.self());
                return BoxedUnit.UNIT;
            });
            return BoxedUnit.UNIT;
        });
    }

    private final PendingWrite create$1(Tcp.WriteCommand head, Tcp.WriteCommand tail, ActorRef commander$2) {
        Tcp.WriteCommand writeCommand;
        while (true) {
            boolean bl = false;
            Tcp.Write write = null;
            writeCommand = head;
            Tcp.Write write2 = Tcp$Write$.MODULE$.empty();
            Tcp.WriteCommand writeCommand2 = writeCommand;
            if (!(write2 != null ? !((Object)write2).equals(writeCommand2) : writeCommand2 != null)) {
                if (tail == Tcp$Write$.MODULE$.empty()) {
                    return TcpConnection$EmptyPendingWrite$.MODULE$;
                }
                Tcp.WriteCommand writeCommand3 = tail;
                tail = Tcp$Write$.MODULE$.empty();
                head = writeCommand3;
                continue;
            }
            if (writeCommand instanceof Tcp.Write) {
                bl = true;
                write = (Tcp.Write)writeCommand;
                ByteString data = write.data();
                Tcp.Event ack = write.ack();
                if (data.nonEmpty()) {
                    return this.PendingBufferWrite(commander$2, data, ack, tail);
                }
            }
            if (writeCommand instanceof Tcp.WriteFile) {
                Tcp.WriteFile writeFile = (Tcp.WriteFile)writeCommand;
                String path = writeFile.filePath();
                long offset = writeFile.position();
                long count = writeFile.count();
                Tcp.Event ack = writeFile.ack();
                return this.PendingWriteFile(commander$2, Paths.get(path, new String[0]), offset, count, ack, tail);
            }
            if (writeCommand instanceof Tcp.WritePath) {
                Tcp.WritePath writePath = (Tcp.WritePath)writeCommand;
                Path path = writePath.path();
                long offset = writePath.position();
                long count = writePath.count();
                Tcp.Event ack = writePath.ack();
                return this.PendingWriteFile(commander$2, path, offset, count, ack, tail);
            }
            if (writeCommand instanceof Tcp.CompoundWrite) {
                Tcp.WriteCommand t2;
                Tcp.CompoundWrite compoundWrite = (Tcp.CompoundWrite)writeCommand;
                Tcp.SimpleWriteCommand h2 = compoundWrite.head();
                tail = t2 = compoundWrite.tailCommand();
                head = h2;
                continue;
            }
            if (!bl) break;
            Tcp.Event ack = write.ack();
            if (write.wantsAck()) {
                commander$2.$bang(ack, this.self());
            }
            Tcp.WriteCommand writeCommand4 = tail;
            tail = Tcp$Write$.MODULE$.empty();
            head = writeCommand4;
        }
        throw new MatchError(writeCommand);
    }

    public TcpConnection(TcpExt tcp, SocketChannel channel, boolean pullMode) {
        this.tcp = tcp;
        this.channel = channel;
        this.pullMode = pullMode;
        Actor.$init$(this);
        ActorLogging.$init$(this);
        this.akka$io$TcpConnection$$pendingWrite = TcpConnection$EmptyPendingWrite$.MODULE$;
        this.peerClosed = false;
        this.akka$io$TcpConnection$$writingSuspended = false;
        this.akka$io$TcpConnection$$readingSuspended = pullMode;
        this.akka$io$TcpConnection$$interestedInResume = None$.MODULE$;
        this.closedMessage = None$.MODULE$;
        this.watchedActor = this.context().system().deadLetters();
        this.registration = None$.MODULE$;
        Statics.releaseFence();
    }

    public static final class CloseInformation
    implements Product,
    Serializable {
        private final Set<ActorRef> notificationsTo;
        private final Tcp.Event closedEvent;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Set<ActorRef> notificationsTo() {
            return this.notificationsTo;
        }

        public Tcp.Event closedEvent() {
            return this.closedEvent;
        }

        public CloseInformation copy(Set<ActorRef> notificationsTo, Tcp.Event closedEvent) {
            return new CloseInformation(notificationsTo, closedEvent);
        }

        public Set<ActorRef> copy$default$1() {
            return this.notificationsTo();
        }

        public Tcp.Event copy$default$2() {
            return this.closedEvent();
        }

        @Override
        public String productPrefix() {
            return "CloseInformation";
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
                    return this.notificationsTo();
                }
                case 1: {
                    return this.closedEvent();
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
            return x$1 instanceof CloseInformation;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "notificationsTo";
                }
                case 1: {
                    return "closedEvent";
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
            if (!(object instanceof CloseInformation)) return false;
            boolean bl = true;
            if (!bl) return false;
            CloseInformation closeInformation = (CloseInformation)x$1;
            Set<ActorRef> set = this.notificationsTo();
            Set<ActorRef> set2 = closeInformation.notificationsTo();
            if (set == null) {
                if (set2 != null) {
                    return false;
                }
            } else if (!set.equals(set2)) return false;
            Tcp.Event event = this.closedEvent();
            Tcp.Event event2 = closeInformation.closedEvent();
            if (event == null) {
                if (event2 == null) return true;
                return false;
            } else {
                if (!event.equals(event2)) return false;
                return true;
            }
        }

        public CloseInformation(Set<ActorRef> notificationsTo, Tcp.Event closedEvent) {
            this.notificationsTo = notificationsTo;
            this.closedEvent = closedEvent;
            Product.$init$(this);
        }
    }

    public static final class ConnectionInfo
    implements Product,
    Serializable {
        private final ChannelRegistration registration;
        private final ActorRef handler;
        private final boolean keepOpenOnPeerClosed;
        private final boolean useResumeWriting;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ChannelRegistration registration() {
            return this.registration;
        }

        public ActorRef handler() {
            return this.handler;
        }

        public boolean keepOpenOnPeerClosed() {
            return this.keepOpenOnPeerClosed;
        }

        public boolean useResumeWriting() {
            return this.useResumeWriting;
        }

        public ConnectionInfo copy(ChannelRegistration registration, ActorRef handler, boolean keepOpenOnPeerClosed, boolean useResumeWriting) {
            return new ConnectionInfo(registration, handler, keepOpenOnPeerClosed, useResumeWriting);
        }

        public ChannelRegistration copy$default$1() {
            return this.registration();
        }

        public ActorRef copy$default$2() {
            return this.handler();
        }

        public boolean copy$default$3() {
            return this.keepOpenOnPeerClosed();
        }

        public boolean copy$default$4() {
            return this.useResumeWriting();
        }

        @Override
        public String productPrefix() {
            return "ConnectionInfo";
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
                    return this.registration();
                }
                case 1: {
                    return this.handler();
                }
                case 2: {
                    return BoxesRunTime.boxToBoolean(this.keepOpenOnPeerClosed());
                }
                case 3: {
                    return BoxesRunTime.boxToBoolean(this.useResumeWriting());
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
            return x$1 instanceof ConnectionInfo;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "registration";
                }
                case 1: {
                    return "handler";
                }
                case 2: {
                    return "keepOpenOnPeerClosed";
                }
                case 3: {
                    return "useResumeWriting";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.anyHash(this.registration()));
            n = Statics.mix(n, Statics.anyHash(this.handler()));
            n = Statics.mix(n, this.keepOpenOnPeerClosed() ? 1231 : 1237);
            n = Statics.mix(n, this.useResumeWriting() ? 1231 : 1237);
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
            if (!(object instanceof ConnectionInfo)) return false;
            boolean bl = true;
            if (!bl) return false;
            ConnectionInfo connectionInfo = (ConnectionInfo)x$1;
            if (this.keepOpenOnPeerClosed() != connectionInfo.keepOpenOnPeerClosed()) return false;
            if (this.useResumeWriting() != connectionInfo.useResumeWriting()) return false;
            ChannelRegistration channelRegistration = this.registration();
            ChannelRegistration channelRegistration2 = connectionInfo.registration();
            if (channelRegistration == null) {
                if (channelRegistration2 != null) {
                    return false;
                }
            } else if (!channelRegistration.equals(channelRegistration2)) return false;
            ActorRef actorRef2 = this.handler();
            ActorRef actorRef3 = connectionInfo.handler();
            if (actorRef2 == null) {
                if (actorRef3 == null) return true;
                return false;
            } else {
                if (!((Object)actorRef2).equals(actorRef3)) return false;
                return true;
            }
        }

        public ConnectionInfo(ChannelRegistration registration, ActorRef handler, boolean keepOpenOnPeerClosed, boolean useResumeWriting) {
            this.registration = registration;
            this.handler = handler;
            this.keepOpenOnPeerClosed = keepOpenOnPeerClosed;
            this.useResumeWriting = useResumeWriting;
            Product.$init$(this);
        }
    }

    public class PendingBufferWrite
    extends PendingWrite {
        private final ActorRef commander;
        private final ByteString remainingData;
        private final Object ack;
        private final ByteBuffer buffer;
        private final Tcp.WriteCommand tail;
        public final /* synthetic */ TcpConnection $outer;

        @Override
        public ActorRef commander() {
            return this.commander;
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public PendingWrite doWrite(ConnectionInfo info) {
            PendingBufferWrite pendingBufferWrite;
            try {
                void var2_2;
                PendingWrite next = this.writeToChannel$1(this.remainingData);
                if (next != TcpConnection$EmptyPendingWrite$.MODULE$) {
                    info.registration().enableInterest(4);
                }
                pendingBufferWrite = var2_2;
            }
            catch (IOException e) {
                this.akka$io$TcpConnection$PendingBufferWrite$$$outer().handleError(info.handler(), e);
                pendingBufferWrite = this;
            }
            return pendingBufferWrite;
        }

        @Override
        public void release() {
            this.akka$io$TcpConnection$PendingBufferWrite$$$outer().tcp().bufferPool().release(this.buffer);
        }

        public /* synthetic */ TcpConnection akka$io$TcpConnection$PendingBufferWrite$$$outer() {
            return this.$outer;
        }

        private final PendingWrite writeToChannel$1(ByteString data) {
            while (true) {
                int writtenBytes = this.akka$io$TcpConnection$PendingBufferWrite$$$outer().channel().write(this.buffer);
                if (this.akka$io$TcpConnection$PendingBufferWrite$$$outer().tcp().Settings().TraceLogging()) {
                    this.akka$io$TcpConnection$PendingBufferWrite$$$outer().log().debug("Wrote [{}] bytes to channel", BoxesRunTime.boxToInteger(writtenBytes));
                }
                if (this.buffer.hasRemaining()) {
                    if (data == this.remainingData) {
                        return this;
                    }
                    return new PendingBufferWrite(this.akka$io$TcpConnection$PendingBufferWrite$$$outer(), this.commander(), data, this.ack, this.buffer, this.tail);
                }
                if (!data.nonEmpty()) break;
                this.buffer.clear();
                int copied = data.copyToBuffer(this.buffer);
                this.buffer.flip();
                data = data.drop(copied);
            }
            if (!(this.ack instanceof Tcp.NoAck)) {
                this.commander().$bang(this.ack, this.akka$io$TcpConnection$PendingBufferWrite$$$outer().self());
            }
            this.release();
            return this.akka$io$TcpConnection$PendingBufferWrite$$$outer().PendingWrite(this.commander(), this.tail);
        }

        public PendingBufferWrite(TcpConnection $outer, ActorRef commander, ByteString remainingData, Object ack, ByteBuffer buffer, Tcp.WriteCommand tail) {
            this.commander = commander;
            this.remainingData = remainingData;
            this.ack = ack;
            this.buffer = buffer;
            this.tail = tail;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }

    public static abstract class PendingWrite {
        public abstract ActorRef commander();

        public abstract PendingWrite doWrite(ConnectionInfo var1);

        public abstract void release();
    }

    public class PendingWriteFile
    extends PendingWrite
    implements Runnable {
        private final ActorRef commander;
        private final FileChannel fileChannel;
        private final long offset;
        private final long remaining;
        private final Tcp.Event ack;
        private final Tcp.WriteCommand tail;
        public final /* synthetic */ TcpConnection $outer;

        @Override
        public ActorRef commander() {
            return this.commander;
        }

        @Override
        public PendingWrite doWrite(ConnectionInfo info) {
            this.akka$io$TcpConnection$PendingWriteFile$$$outer().tcp().fileIoDispatcher().execute(this);
            return this;
        }

        @Override
        public void release() {
            this.fileChannel.close();
        }

        @Override
        public void run() {
            block3: {
                try {
                    long toWrite = package$.MODULE$.min(this.remaining, (long)this.akka$io$TcpConnection$PendingWriteFile$$$outer().tcp().Settings().TransferToLimit());
                    long written = this.fileChannel.transferTo(this.offset, toWrite, this.akka$io$TcpConnection$PendingWriteFile$$$outer().channel());
                    if (written < this.remaining) {
                        PendingWriteFile updated = new PendingWriteFile(this.akka$io$TcpConnection$PendingWriteFile$$$outer(), this.commander(), this.fileChannel, this.offset + written, this.remaining - written, this.ack, this.tail);
                        this.akka$io$TcpConnection$PendingWriteFile$$$outer().self().$bang(new UpdatePendingWriteAndThen(updated, TcpConnection$.MODULE$.doNothing()), this.akka$io$TcpConnection$PendingWriteFile$$$outer().self());
                        break block3;
                    }
                    this.release();
                    Function0<Object> andThen = !(this.ack instanceof Tcp.NoAck) ? () -> this.commander().$bang($this.ack, this.akka$io$TcpConnection$PendingWriteFile$$$outer().self()) : TcpConnection$.MODULE$.doNothing();
                    this.akka$io$TcpConnection$PendingWriteFile$$$outer().self().$bang(new UpdatePendingWriteAndThen(this.akka$io$TcpConnection$PendingWriteFile$$$outer().PendingWrite(this.commander(), this.tail), andThen), this.akka$io$TcpConnection$PendingWriteFile$$$outer().self());
                }
                catch (IOException e) {
                    this.akka$io$TcpConnection$PendingWriteFile$$$outer().self().$bang(new WriteFileFailed(e), this.akka$io$TcpConnection$PendingWriteFile$$$outer().self());
                }
            }
        }

        public /* synthetic */ TcpConnection akka$io$TcpConnection$PendingWriteFile$$$outer() {
            return this.$outer;
        }

        public PendingWriteFile(TcpConnection $outer, ActorRef commander, FileChannel fileChannel, long offset, long remaining, Tcp.Event ack, Tcp.WriteCommand tail) {
            this.commander = commander;
            this.fileChannel = fileChannel;
            this.offset = offset;
            this.remaining = remaining;
            this.ack = ack;
            this.tail = tail;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }

    public static interface ReadResult {
    }

    public static final class UpdatePendingWriteAndThen
    implements NoSerializationVerificationNeeded,
    Product,
    Serializable {
        private final PendingWrite remainingWrite;
        private final Function0<BoxedUnit> work;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public PendingWrite remainingWrite() {
            return this.remainingWrite;
        }

        public Function0<BoxedUnit> work() {
            return this.work;
        }

        public UpdatePendingWriteAndThen copy(PendingWrite remainingWrite, Function0<BoxedUnit> work) {
            return new UpdatePendingWriteAndThen(remainingWrite, work);
        }

        public PendingWrite copy$default$1() {
            return this.remainingWrite();
        }

        public Function0<BoxedUnit> copy$default$2() {
            return this.work();
        }

        @Override
        public String productPrefix() {
            return "UpdatePendingWriteAndThen";
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
                    return this.remainingWrite();
                }
                case 1: {
                    return this.work();
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
            return x$1 instanceof UpdatePendingWriteAndThen;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "remainingWrite";
                }
                case 1: {
                    return "work";
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
            if (!(object instanceof UpdatePendingWriteAndThen)) return false;
            boolean bl = true;
            if (!bl) return false;
            UpdatePendingWriteAndThen updatePendingWriteAndThen = (UpdatePendingWriteAndThen)x$1;
            PendingWrite pendingWrite = this.remainingWrite();
            PendingWrite pendingWrite2 = updatePendingWriteAndThen.remainingWrite();
            if (pendingWrite == null) {
                if (pendingWrite2 != null) {
                    return false;
                }
            } else if (!pendingWrite.equals(pendingWrite2)) return false;
            Function0<BoxedUnit> function0 = this.work();
            Function0<BoxedUnit> function02 = updatePendingWriteAndThen.work();
            if (function0 == null) {
                if (function02 == null) return true;
                return false;
            } else {
                if (!function0.equals(function02)) return false;
                return true;
            }
        }

        public UpdatePendingWriteAndThen(PendingWrite remainingWrite, Function0<BoxedUnit> work) {
            this.remainingWrite = remainingWrite;
            this.work = work;
            Product.$init$(this);
        }
    }

    public static final class WriteFileFailed
    implements Product,
    Serializable {
        private final IOException e;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public IOException e() {
            return this.e;
        }

        public WriteFileFailed copy(IOException e) {
            return new WriteFileFailed(e);
        }

        public IOException copy$default$1() {
            return this.e();
        }

        @Override
        public String productPrefix() {
            return "WriteFileFailed";
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
                    return this.e();
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
            return x$1 instanceof WriteFileFailed;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "e";
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
            if (!(object instanceof WriteFileFailed)) return false;
            boolean bl = true;
            if (!bl) return false;
            WriteFileFailed writeFileFailed = (WriteFileFailed)x$1;
            IOException iOException = this.e();
            IOException iOException2 = writeFileFailed.e();
            if (iOException != null) {
                if (!iOException.equals(iOException2)) return false;
                return true;
            }
            if (iOException2 == null) return true;
            return false;
        }

        public WriteFileFailed(IOException e) {
            this.e = e;
            Product.$init$(this);
        }
    }
}

