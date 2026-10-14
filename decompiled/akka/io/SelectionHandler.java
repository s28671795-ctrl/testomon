/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorInitializationException;
import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.actor.Deploy$;
import akka.actor.NoSerializationVerificationNeeded;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.Props$;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy$Stop$;
import akka.actor.Terminated;
import akka.annotation.InternalApi;
import akka.dispatch.MessageDispatcher;
import akka.dispatch.RequiresMessageQueue;
import akka.dispatch.UnboundedMessageQueueSemantics;
import akka.event.Logging;
import akka.event.Logging$;
import akka.event.LoggingAdapter;
import akka.io.ChannelRegistration;
import akka.io.ChannelRegistry;
import akka.io.SelectionHandler$;
import akka.io.SelectionHandler$ChannelAcceptable$;
import akka.io.SelectionHandler$ChannelConnectable$;
import akka.io.SelectionHandler$ChannelReadable$;
import akka.io.SelectionHandler$ChannelRegistryImpl$;
import akka.io.SelectionHandler$ChannelWritable$;
import akka.io.SelectionHandlerSettings;
import akka.routing.RandomPool;
import akka.routing.RandomPool$;
import akka.routing.Resizer;
import akka.util.SerializedSuspendableExecutionContext$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Product;
import scala.collection.Iterator;
import scala.concurrent.ExecutionContext;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Try$;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u00115uACA\u0012\u0003KA\t!!\n\u0002.\u0019Q\u0011\u0011GA\u0013\u0011\u0003\t)#a\r\t\u000f\u0005\u0005\u0013\u0001\"\u0001\u0002F!I\u0011qI\u0001C\u0002\u0013\u0005\u0011\u0011\n\u0005\t\u0003#\n\u0001\u0015!\u0003\u0002L\u0019I\u00111K\u0001\u0011\u0002G\u0005\u0011Q\u000b\u0005\b\u0003/*a\u0011AA-\r\u0019\t\t'\u0001\"\u0002d!Q\u0011qR\u0004\u0003\u0016\u0004%\t!!%\t\u0015\u0005]uA!E!\u0002\u0013\t\u0019\n\u0003\u0006\u0002\u001a\u001e\u0011)\u001a!C\u0001\u00037C!\"a)\b\u0005#\u0005\u000b\u0011BAO\u0011)\t)k\u0002BK\u0002\u0013\u0005\u0011q\u0015\u0005\u000b\u0003w;!\u0011#Q\u0001\n\u0005%\u0006bBA!\u000f\u0011\u0005\u0011Q\u0018\u0005\n\u0003\u000f<\u0011\u0011!C\u0001\u0003\u0013D\u0011\"!5\b#\u0003%\t!a5\t\u0013\u0005%x!%A\u0005\u0002\u0005-\b\"CAx\u000fE\u0005I\u0011AAy\u0011%\t)pBA\u0001\n\u0003\n9\u0010C\u0005\u0003\n\u001d\t\t\u0011\"\u0001\u0002J!I!1B\u0004\u0002\u0002\u0013\u0005!Q\u0002\u0005\n\u0005'9\u0011\u0011!C!\u0005+A\u0011Ba\t\b\u0003\u0003%\tA!\n\t\u0013\t=r!!A\u0005B\tE\u0002\"\u0003B\u001b\u000f\u0005\u0005I\u0011\tB\u001c\u0011%\u0011IdBA\u0001\n\u0003\u0012Y\u0004C\u0005\u0003>\u001d\t\t\u0011\"\u0011\u0003@\u001dI!1I\u0001\u0002\u0002#\u0005!Q\t\u0004\n\u0003C\n\u0011\u0011!E\u0001\u0005\u000fBq!!\u0011\u001e\t\u0003\u0011i\u0006C\u0005\u0003:u\t\t\u0011\"\u0012\u0003<!I!qL\u000f\u0002\u0002\u0013\u0005%\u0011\r\u0005\n\u0005Sj\u0012\u0011!CA\u0005WB\u0011B! \u001e\u0003\u0003%IAa \u0007\r\t\u001d\u0015A\u0011BE\u0011)\u0011Yi\tBK\u0002\u0013\u0005!Q\u0012\u0005\u000b\u0005\u001f\u001b#\u0011#Q\u0001\n\u0005}\u0006B\u0003BIG\tU\r\u0011\"\u0001\u0002J!Q!1S\u0012\u0003\u0012\u0003\u0006I!a\u0013\t\u000f\u0005\u00053\u0005\"\u0001\u0003\u0016\"I\u0011qY\u0012\u0002\u0002\u0013\u0005!Q\u0014\u0005\n\u0003#\u001c\u0013\u0013!C\u0001\u0005GC\u0011\"!;$#\u0003%\tAa*\t\u0013\u0005U8%!A\u0005B\u0005]\b\"\u0003B\u0005G\u0005\u0005I\u0011AA%\u0011%\u0011YaIA\u0001\n\u0003\u0011Y\u000bC\u0005\u0003\u0014\r\n\t\u0011\"\u0011\u0003\u0016!I!1E\u0012\u0002\u0002\u0013\u0005!q\u0016\u0005\n\u0005_\u0019\u0013\u0011!C!\u0005gC\u0011B!\u000e$\u0003\u0003%\tEa\u000e\t\u0013\te2%!A\u0005B\tm\u0002\"\u0003B\u001fG\u0005\u0005I\u0011\tB\\\u000f%\u0011Y,AA\u0001\u0012\u0003\u0011iLB\u0005\u0003\b\u0006\t\t\u0011#\u0001\u0003@\"9\u0011\u0011\t\u001c\u0005\u0002\t\u001d\u0007\"\u0003B\u001dm\u0005\u0005IQ\tB\u001e\u0011%\u0011yFNA\u0001\n\u0003\u0013I\rC\u0005\u0003jY\n\t\u0011\"!\u0003P\"I!Q\u0010\u001c\u0002\u0002\u0013%!qP\u0004\b\u00057\f\u0001\u0012\u0011Bo\r\u001d\u0011y.\u0001EA\u0005CDq!!\u0011>\t\u0003\u0011\u0019\u000fC\u0005\u0002vv\n\t\u0011\"\u0011\u0002x\"I!\u0011B\u001f\u0002\u0002\u0013\u0005\u0011\u0011\n\u0005\n\u0005\u0017i\u0014\u0011!C\u0001\u0005KD\u0011Ba\u0005>\u0003\u0003%\tE!\u0006\t\u0013\t\rR(!A\u0005\u0002\t%\b\"\u0003B\u001b{\u0005\u0005I\u0011\tB\u001c\u0011%\u0011I$PA\u0001\n\u0003\u0012Y\u0004C\u0005\u0003~u\n\t\u0011\"\u0003\u0003\u0000\u001d9!Q^\u0001\t\u0002\n=ha\u0002By\u0003!\u0005%1\u001f\u0005\b\u0003\u0003BE\u0011\u0001B{\u0011%\t)\u0010SA\u0001\n\u0003\n9\u0010C\u0005\u0003\n!\u000b\t\u0011\"\u0001\u0002J!I!1\u0002%\u0002\u0002\u0013\u0005!q\u001f\u0005\n\u0005'A\u0015\u0011!C!\u0005+A\u0011Ba\tI\u0003\u0003%\tAa?\t\u0013\tU\u0002*!A\u0005B\t]\u0002\"\u0003B\u001d\u0011\u0006\u0005I\u0011\tB\u001e\u0011%\u0011i\bSA\u0001\n\u0013\u0011yhB\u0004\u0003\u0000\u0006A\ti!\u0001\u0007\u000f\r\r\u0011\u0001#!\u0004\u0006!9\u0011\u0011I*\u0005\u0002\r5\u0001\"CA{'\u0006\u0005I\u0011IA|\u0011%\u0011IaUA\u0001\n\u0003\tI\u0005C\u0005\u0003\fM\u000b\t\u0011\"\u0001\u0004\u0010!I!1C*\u0002\u0002\u0013\u0005#Q\u0003\u0005\n\u0005G\u0019\u0016\u0011!C\u0001\u0007'A\u0011B!\u000eT\u0003\u0003%\tEa\u000e\t\u0013\te2+!A\u0005B\tm\u0002\"\u0003B?'\u0006\u0005I\u0011\u0002B@\u000f\u001d\u00199\"\u0001EA\u000731qaa\u0007\u0002\u0011\u0003\u001bi\u0002C\u0004\u0002By#\taa\b\t\u0013\u0005Uh,!A\u0005B\u0005]\b\"\u0003B\u0005=\u0006\u0005I\u0011AA%\u0011%\u0011YAXA\u0001\n\u0003\u0019\t\u0003C\u0005\u0003\u0014y\u000b\t\u0011\"\u0011\u0003\u0016!I!1\u00050\u0002\u0002\u0013\u00051Q\u0005\u0005\n\u0005kq\u0016\u0011!C!\u0005oA\u0011B!\u000f_\u0003\u0003%\tEa\u000f\t\u0013\tud,!A\u0005\n\t}d!CB\u0015\u0003\u0005\u0005\u0011QEB\u0016\u0011)\u0019\u0019\u0004\u001bB\u0001B\u0003%1Q\u0007\u0005\u000b\u0007wA'\u0011!Q\u0001\n\u0005-\u0003bBA!Q\u0012\u00051Q\b\u0005\b\u0007\u000bBG\u0011IB$\u0011%\u0019y\u0005\u001bb\u0001\n\u0003\tY\n\u0003\u0005\u0004R!\u0004\u000b\u0011BAO\u0011\u001d\u0019\u0019\u0006\u001bC\u0003\u0007+B1b!\u001b\u0002\u0005\u0004%)!!\n\u0004H!A11N\u0001!\u0002\u001b\u0019IE\u0002\u0004\u0004n\u0005!1q\u000e\u0005\u000b\u0007c\u0012(\u0011!Q\u0001\n\rM\u0004BCB@e\n\u0005\t\u0015!\u0003\u00046!Q1\u0011\u0011:\u0003\u0002\u0003\u0006Iaa!\t\u000f\u0005\u0005#\u000f\"\u0001\u0004\u0010\"A1\u0011\u0014:!\u0002\u0013\u0019Y\n\u0003\u0005\u00040J\u0004\u000b\u0011BBY\u0011%\u0019\u0019M\u001db\u0001\n\u000b\u0019)\r\u0003\u0005\u0004LJ\u0004\u000bQBBd\u0011!\u0019iM\u001dQ\u0001\n\r=\u0007bBBve\u0012\u00051Q\u001e\u0005\b\t\u000b\u0011H\u0011ABq\u0011\u001d!9A\u001dC\u0005\t\u0013Aq\u0001\"\u0007s\t\u0013!Y\u0002C\u0004\u0005*I$I\u0001b\u000b\t\u000f\u0011=\"\u000f\"\u0003\u00052!9Aq\u0007:\u0005\n\u0011ebaBBje\u0006%1Q\u001b\u0005\t\u0003\u0003\n9\u0001\"\u0001\u0004^\"A1q\\A\u0004\r\u0003\u0019\t\u000f\u0003\u0005\u0004j\u0006\u001dA\u0011ABq\r%\t\t$!\n\u0001\u0003K!y\u0004C\u0006\u0004\u0000\u0005=!\u0011!Q\u0001\n\rU\u0002\u0002CA!\u0003\u001f!\t\u0001\"\u0017\t\u0013\u0011}\u0013q\u0002Q!\n\u0011\u0005\u0004\"\u0003C4\u0003\u001f\u0001\u000b\u0015BA&\u0011%!I'a\u0004!\u0002\u0013!Y\u0007\u0003\u0005\u0005p\u0005=A\u0011\u0001C9\u0011!!9(a\u0004\u0005B\r\u0005\b\u0002CB#\u0003\u001f!\t\u0005\"\u001f\t\u0011\u0011\u0005\u0015q\u0002C\u0001\t\u0007\u000b\u0001cU3mK\u000e$\u0018n\u001c8IC:$G.\u001a:\u000b\t\u0005\u001d\u0012\u0011F\u0001\u0003S>T!!a\u000b\u0002\t\u0005\\7.\u0019\t\u0004\u0003_\tQBAA\u0013\u0005A\u0019V\r\\3di&|g\u000eS1oI2,'oE\u0002\u0002\u0003k\u0001B!a\u000e\u0002>5\u0011\u0011\u0011\b\u0006\u0003\u0003w\tQa]2bY\u0006LA!a\u0010\u0002:\t1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u0003\u0003[\tq\"T1y'\u0016dWm\u0019;NS2d\u0017n]\u000b\u0003\u0003\u0017\u0002B!a\u000e\u0002N%!\u0011qJA\u001d\u0005\rIe\u000e^\u0001\u0011\u001b\u0006D8+\u001a7fGRl\u0015\u000e\u001c7jg\u0002\u0012\u0011\u0003S1t\r\u0006LG.\u001e:f\u001b\u0016\u001c8/Y4f'\r)\u0011QG\u0001\u000fM\u0006LG.\u001e:f\u001b\u0016\u001c8/Y4f+\t\tY\u0006\u0005\u0003\u00028\u0005u\u0013\u0002BA0\u0003s\u00111!\u00118z\u0005A9vN]6fe\u001a{'oQ8n[\u0006tGmE\u0005\b\u0003k\t)'!\u001d\u0002xA!\u0011qMA7\u001b\t\tIG\u0003\u0003\u0002l\u0005%\u0012!B1di>\u0014\u0018\u0002BA8\u0003S\u0012\u0011ET8TKJL\u0017\r\\5{CRLwN\u001c,fe&4\u0017nY1uS>tg*Z3eK\u0012\u0004B!a\u000e\u0002t%!\u0011QOA\u001d\u0005\u001d\u0001&o\u001c3vGR\u0004B!!\u001f\u0002\n:!\u00111PAC\u001d\u0011\ti(a!\u000e\u0005\u0005}$\u0002BAA\u0003\u0007\na\u0001\u0010:p_Rt\u0014BAA\u001e\u0013\u0011\t9)!\u000f\u0002\u000fA\f7m[1hK&!\u00111RAG\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\u0011\t9)!\u000f\u0002\u0015\u0005\u0004\u0018nQ8n[\u0006tG-\u0006\u0002\u0002\u0014B\u0019\u0011QS\u0003\u000e\u0003\u0005\t1\"\u00199j\u0007>lW.\u00198eA\u0005I1m\\7nC:$WM]\u000b\u0003\u0003;\u0003B!a\u001a\u0002 &!\u0011\u0011UA5\u0005!\t5\r^8s%\u00164\u0017AC2p[6\fg\u000eZ3sA\u0005Q1\r[5mIB\u0013x\u000e]:\u0016\u0005\u0005%\u0006\u0003CA\u001c\u0003W\u000by+!.\n\t\u00055\u0016\u0011\b\u0002\n\rVt7\r^5p]F\u0002B!a\f\u00022&!\u00111WA\u0013\u0005=\u0019\u0005.\u00198oK2\u0014VmZ5tiJL\b\u0003BA4\u0003oKA!!/\u0002j\t)\u0001K]8qg\u0006Y1\r[5mIB\u0013x\u000e]:!)!\ty,!1\u0002D\u0006\u0015\u0007cAAK\u000f!9\u0011q\u0012\bA\u0002\u0005M\u0005bBAM\u001d\u0001\u0007\u0011Q\u0014\u0005\b\u0003Ks\u0001\u0019AAU\u0003\u0011\u0019w\u000e]=\u0015\u0011\u0005}\u00161ZAg\u0003\u001fD\u0011\"a$\u0010!\u0003\u0005\r!a%\t\u0013\u0005eu\u0002%AA\u0002\u0005u\u0005\"CAS\u001fA\u0005\t\u0019AAU\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\"!!6+\t\u0005M\u0015q[\u0016\u0003\u00033\u0004B!a7\u0002f6\u0011\u0011Q\u001c\u0006\u0005\u0003?\f\t/A\u0005v]\u000eDWmY6fI*!\u00111]A\u001d\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003O\fiNA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'\u0006\u0002\u0002n*\"\u0011QTAl\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*\"!a=+\t\u0005%\u0016q[\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005e\b\u0003BA~\u0005\u000bi!!!@\u000b\t\u0005}(\u0011A\u0001\u0005Y\u0006twM\u0003\u0002\u0003\u0004\u0005!!.\u0019<b\u0013\u0011\u00119!!@\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z\u00039\u0001(o\u001c3vGR,E.Z7f]R$B!a\u0017\u0003\u0010!I!\u0011C\u000b\u0002\u0002\u0003\u0007\u00111J\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\t]\u0001C\u0002B\r\u0005?\tY&\u0004\u0002\u0003\u001c)!!QDA\u001d\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0005C\u0011YB\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003\u0002B\u0014\u0005[\u0001B!a\u000e\u0003*%!!1FA\u001d\u0005\u001d\u0011un\u001c7fC:D\u0011B!\u0005\u0018\u0003\u0003\u0005\r!a\u0017\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0003s\u0014\u0019\u0004C\u0005\u0003\u0012a\t\t\u00111\u0001\u0002L\u0005A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002L\u0005AAo\\*ue&tw\r\u0006\u0002\u0002z\u00061Q-];bYN$BAa\n\u0003B!I!\u0011C\u000e\u0002\u0002\u0003\u0007\u00111L\u0001\u0011/>\u00148.\u001a:G_J\u001cu.\\7b]\u0012\u00042!!&\u001e'\u0015i\"\u0011\nB+!1\u0011YE!\u0015\u0002\u0014\u0006u\u0015\u0011VA`\u001b\t\u0011iE\u0003\u0003\u0003P\u0005e\u0012a\u0002:v]RLW.Z\u0005\u0005\u0005'\u0012iEA\tBEN$(/Y2u\rVt7\r^5p]N\u0002BAa\u0016\u0003\\5\u0011!\u0011\f\u0006\u0005\u0003O\u0011\t!\u0003\u0003\u0002\f\neCC\u0001B#\u0003\u0015\t\u0007\u000f\u001d7z)!\tyLa\u0019\u0003f\t\u001d\u0004bBAHA\u0001\u0007\u00111\u0013\u0005\b\u00033\u0003\u0003\u0019AAO\u0011\u001d\t)\u000b\ta\u0001\u0003S\u000bq!\u001e8baBd\u0017\u0010\u0006\u0003\u0003n\te\u0004CBA\u001c\u0005_\u0012\u0019(\u0003\u0003\u0003r\u0005e\"AB(qi&|g\u000e\u0005\u0006\u00028\tU\u00141SAO\u0003SKAAa\u001e\u0002:\t1A+\u001e9mKNB\u0011Ba\u001f\"\u0003\u0003\u0005\r!a0\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0003\u0002B!\u00111 BB\u0013\u0011\u0011))!@\u0003\r=\u0013'.Z2u\u0005\u0015\u0011V\r\u001e:z'%\u0019\u0013QGA3\u0003c\n9(A\u0004d_6l\u0017M\u001c3\u0016\u0005\u0005}\u0016\u0001C2p[6\fg\u000e\u001a\u0011\u0002\u0017I,GO]5fg2+g\r^\u0001\re\u0016$(/[3t\u0019\u00164G\u000f\t\u000b\u0007\u0005/\u0013IJa'\u0011\u0007\u0005U5\u0005C\u0004\u0003\f\"\u0002\r!a0\t\u000f\tE\u0005\u00061\u0001\u0002LQ1!q\u0013BP\u0005CC\u0011Ba#*!\u0003\u0005\r!a0\t\u0013\tE\u0015\u0006%AA\u0002\u0005-SC\u0001BSU\u0011\ty,a6\u0016\u0005\t%&\u0006BA&\u0003/$B!a\u0017\u0003.\"I!\u0011\u0003\u0018\u0002\u0002\u0003\u0007\u00111\n\u000b\u0005\u0005O\u0011\t\fC\u0005\u0003\u0012A\n\t\u00111\u0001\u0002\\Q!\u0011\u0011 B[\u0011%\u0011\t\"MA\u0001\u0002\u0004\tY\u0005\u0006\u0003\u0003(\te\u0006\"\u0003B\ti\u0005\u0005\t\u0019AA.\u0003\u0015\u0011V\r\u001e:z!\r\t)JN\n\u0006m\t\u0005'Q\u000b\t\u000b\u0005\u0017\u0012\u0019-a0\u0002L\t]\u0015\u0002\u0002Bc\u0005\u001b\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c83)\t\u0011i\f\u0006\u0004\u0003\u0018\n-'Q\u001a\u0005\b\u0005\u0017K\u0004\u0019AA`\u0011\u001d\u0011\t*\u000fa\u0001\u0003\u0017\"BA!5\u0003ZB1\u0011q\u0007B8\u0005'\u0004\u0002\"a\u000e\u0003V\u0006}\u00161J\u0005\u0005\u0005/\fID\u0001\u0004UkBdWM\r\u0005\n\u0005wR\u0014\u0011!a\u0001\u0005/\u000b!c\u00115b]:,GnQ8o]\u0016\u001cG/\u00192mKB\u0019\u0011QS\u001f\u0003%\rC\u0017M\u001c8fY\u000e{gN\\3di\u0006\u0014G.Z\n\b{\u0005U\u0012\u0011OA<)\t\u0011i\u000e\u0006\u0003\u0002\\\t\u001d\b\"\u0003B\t\u0003\u0006\u0005\t\u0019AA&)\u0011\u00119Ca;\t\u0013\tE1)!AA\u0002\u0005m\u0013!E\"iC:tW\r\\!dG\u0016\u0004H/\u00192mKB\u0019\u0011Q\u0013%\u0003#\rC\u0017M\u001c8fY\u0006\u001b7-\u001a9uC\ndWmE\u0004I\u0003k\t\t(a\u001e\u0015\u0005\t=H\u0003BA.\u0005sD\u0011B!\u0005M\u0003\u0003\u0005\r!a\u0013\u0015\t\t\u001d\"Q \u0005\n\u0005#q\u0015\u0011!a\u0001\u00037\nqb\u00115b]:,GNU3bI\u0006\u0014G.\u001a\t\u0004\u0003+\u001b&aD\"iC:tW\r\u001c*fC\u0012\f'\r\\3\u0014\u0013M\u000b)da\u0002\u0002r\u0005]\u0004\u0003BA4\u0007\u0013IAaa\u0003\u0002j\t)B)Z1e\u0019\u0016$H/\u001a:TkB\u0004(/Z:tS>tGCAB\u0001)\u0011\tYf!\u0005\t\u0013\tEq+!AA\u0002\u0005-C\u0003\u0002B\u0014\u0007+A\u0011B!\u0005Z\u0003\u0003\u0005\r!a\u0017\u0002\u001f\rC\u0017M\u001c8fY^\u0013\u0018\u000e^1cY\u0016\u00042!!&_\u0005=\u0019\u0005.\u00198oK2<&/\u001b;bE2,7#\u00030\u00026\r\u001d\u0011\u0011OA<)\t\u0019I\u0002\u0006\u0003\u0002\\\r\r\u0002\"\u0003B\tE\u0006\u0005\t\u0019AA&)\u0011\u00119ca\n\t\u0013\tEA-!AA\u0002\u0005m#\u0001F*fY\u0016\u001cGo\u001c:CCN,G-T1oC\u001e,'oE\u0003i\u0003k\u0019i\u0003\u0005\u0003\u0002h\r=\u0012\u0002BB\u0019\u0003S\u0012Q!Q2u_J\f\u0001c]3mK\u000e$xN]*fiRLgnZ:\u0011\t\u0005=2qG\u0005\u0005\u0007s\t)C\u0001\rTK2,7\r^5p]\"\u000bg\u000e\u001a7feN+G\u000f^5oON\fQB\u001c:PMN+G.Z2u_J\u001cHCBB \u0007\u0003\u001a\u0019\u0005E\u0002\u0002\u0016\"Dqaa\rl\u0001\u0004\u0019)\u0004C\u0004\u0004<-\u0004\r!a\u0013\u0002%M,\b/\u001a:wSN|'o\u0015;sCR,w-_\u000b\u0003\u0007\u0013\u0002B!a\u001a\u0004L%!1QJA5\u0005I\u0019V\u000f]3sm&\u001cxN]*ue\u0006$XmZ=\u0002\u0019M,G.Z2u_J\u0004vn\u001c7\u0002\u001bM,G.Z2u_J\u0004vn\u001c7!\u0003]9xN]6fe\u001a{'oQ8n[\u0006tG\rS1oI2,'\u000f\u0006\u0003\u0004X\r}\u0003\u0003BB-\u00077j\u0011\u0001[\u0005\u0005\u0007;\u001ayCA\u0004SK\u000e,\u0017N^3\t\u000f\r\u0005t\u000e1\u0001\u0004d\u0005\u0011\u0001O\u001a\t\t\u0003o\u0019)'a%\u0002*&!1qMA\u001d\u0005=\u0001\u0016M\u001d;jC24UO\\2uS>t\u0017\u0001H2p]:,7\r^5p]N+\b/\u001a:wSN|'o\u0015;sCR,w-_\u0001\u001eG>tg.Z2uS>t7+\u001e9feZL7o\u001c:TiJ\fG/Z4zA\t\u00192\t[1o]\u0016d'+Z4jgR\u0014\u00180S7qYN)!/!\u000e\u00020\u0006\u0001R\r_3dkRLwN\\\"p]R,\u0007\u0010\u001e\t\u0005\u0007k\u001aY(\u0004\u0002\u0004x)!1\u0011PA\u001d\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0005\u0007{\u001a9H\u0001\tFq\u0016\u001cW\u000f^5p]\u000e{g\u000e^3yi\u0006A1/\u001a;uS:<7/A\u0002m_\u001e\u0004Ba!\"\u0004\f6\u00111q\u0011\u0006\u0005\u0007\u0013\u000bI#A\u0003fm\u0016tG/\u0003\u0003\u0004\u000e\u000e\u001d%A\u0004'pO\u001eLgnZ!eCB$XM\u001d\u000b\t\u0007#\u001b\u0019j!&\u0004\u0018B\u0019\u0011Q\u0013:\t\u000f\rEd\u000f1\u0001\u0004t!91q\u0010<A\u0002\rU\u0002bBBAm\u0002\u000711Q\u0001\tg\u0016dWm\u0019;peB!1QTBV\u001b\t\u0019yJ\u0003\u0003\u0004\"\u000e\r\u0016aA:qS*!1QUBT\u0003!\u0019\u0007.\u00198oK2\u001c(\u0002BBU\u0005\u0003\t1A\\5p\u0013\u0011\u0019ika(\u0003!\u0005\u00137\u000f\u001e:bGR\u001cV\r\\3di>\u0014\u0018AB<bW\u0016,\u0006\u000f\u0005\u0003\u00044\u000e}VBAB[\u0015\u0011\u00199l!/\u0002\r\u0005$x.\\5d\u0015\u0011\u0019Iha/\u000b\t\ru&\u0011A\u0001\u0005kRLG.\u0003\u0003\u0004B\u000eU&!D!u_6L7MQ8pY\u0016\fg.A\tP!~\u0013V)\u0011#`\u0003:#ul\u0016*J)\u0016+\"aa2\u0010\u0005\r%W$A\u0003\u0002%=\u0003vLU#B\t~\u000be\nR0X%&#V\tI\u0001\u0007g\u0016dWm\u0019;\u0011\t\rE\u0017qA\u0007\u0002e\n!A+Y:l'\u0019\t9A!!\u0004XB!\u00111`Bm\u0013\u0011\u0019Y.!@\u0003\u0011I+hN\\1cY\u0016$\"aa4\u0002\rQ\u0014\u0018PU;o)\t\u0019\u0019\u000f\u0005\u0003\u00028\r\u0015\u0018\u0002BBt\u0003s\u0011A!\u00168ji\u0006\u0019!/\u001e8\u0002\u0011I,w-[:uKJ$baa<\u0004v\u0012\u0005A\u0003BBr\u0007cDqaa=}\u0001\b\ti*\u0001\u0007dQ\u0006tg.\u001a7BGR|'\u000fC\u0004\u0004xr\u0004\ra!?\u0002\u000f\rD\u0017M\u001c8fYB!11`B\u007f\u001b\t\u0019\u0019+\u0003\u0003\u0004\u0000\u000e\r&!E*fY\u0016\u001cG/\u00192mK\u000eC\u0017M\u001c8fY\"9A1\u0001?A\u0002\u0005-\u0013AC5oSRL\u0017\r\\(qg\u0006A1\u000f[;uI><h.A\tf]\u0006\u0014G.Z%oi\u0016\u0014Xm\u001d;PaN$baa9\u0005\f\u0011U\u0001b\u0002C\u0007}\u0002\u0007AqB\u0001\u0004W\u0016L\b\u0003BB~\t#IA\u0001b\u0005\u0004$\na1+\u001a7fGRLwN\\&fs\"9Aq\u0003@A\u0002\u0005-\u0013aA8qg\u0006\t2-\u00198dK2\\U-_!oI\u000ecwn]3\u0015\r\r\rHQ\u0004C\u0010\u0011\u001d!ia a\u0001\t\u001fAq\u0001\"\t\u0000\u0001\u0004!\u0019#A\u0004b]\u0012$\u0006.\u001a8\u0011\r\u0005]BQEBr\u0013\u0011!9#!\u000f\u0003\u0013\u0019+hn\u0019;j_:\u0004\u0014\u0001\u0003:v]RCWO\\6\u0015\t\r\rHQ\u0006\u0005\t\tC\t\t\u00011\u0001\u0005$\u0005\u0011B-[:bE2,\u0017J\u001c;fe\u0016\u001cHo\u00149t)\u0019\u0019\u0019\u000fb\r\u00056!AAQBA\u0002\u0001\u0004!y\u0001\u0003\u0005\u0005\u0018\u0005\r\u0001\u0019AA&\u0003\u001d)\u00070Z2vi\u0016$Baa9\u0005<!AAQHA\u0003\u0001\u0004\u0019y-\u0001\u0003uCN\\7CCA\b\u0003k\u0019i\u0003\"\u0011\u0005HA!\u0011q\rC\"\u0013\u0011!)%!\u001b\u0003\u0019\u0005\u001bGo\u001c:M_\u001e<\u0017N\\4\u0011\r\u0011%Cq\nC*\u001b\t!YE\u0003\u0003\u0005N\u0005%\u0012\u0001\u00033jgB\fGo\u00195\n\t\u0011EC1\n\u0002\u0015%\u0016\fX/\u001b:fg6+7o]1hKF+X-^3\u0011\t\u0011%CQK\u0005\u0005\t/\"YE\u0001\u0010V]\n|WO\u001c3fI6+7o]1hKF+X-^3TK6\fg\u000e^5dgR!A1\fC/!\u0011\ty#a\u0004\t\u0011\r}\u00141\u0003a\u0001\u0007k\tab]3rk\u0016t7-\u001a(v[\n,'\u000f\u0005\u0003\u00028\u0011\r\u0014\u0002\u0002C3\u0003s\u0011A\u0001T8oO\u0006Q1\r[5mI\u000e{WO\u001c;\u0002\u0011I,w-[:uef\u00042\u0001\"\u001cs\u001d\r\ty\u0003A\u0001\be\u0016\u001cW-\u001b<f+\t!\u0019\b\u0005\u0003\u0005v\rmSBAA\b\u0003!\u0001xn\u001d;Ti>\u0004XC\u0001C>!\u0011\t9\u0007\" \n\t\u0011}\u0014\u0011\u000e\u0002\u0012\u001f:,gi\u001c:P]\u0016\u001cFO]1uK\u001eL\u0018\u0001I:qC^t7\t[5mI^KG\u000f[\"ba\u0006\u001c\u0017\u000e^=Qe>$Xm\u0019;j_:$baa9\u0005\u0006\u0012-\u0005\u0002\u0003CD\u0003C\u0001\r\u0001\"#\u0002\u0007\rlG\rE\u0002\u0005n\u001dA\u0001B!%\u0002\"\u0001\u0007\u00111\n")
public class SelectionHandler
implements Actor,
ActorLogging,
RequiresMessageQueue<UnboundedMessageQueueSemantics> {
    public final SelectionHandlerSettings akka$io$SelectionHandler$$settings;
    private long sequenceNumber;
    public int akka$io$SelectionHandler$$childCount;
    private final ChannelRegistryImpl registry;
    private LoggingAdapter akka$actor$ActorLogging$$_log;
    private ActorContext context;
    private ActorRef self;

    public static int MaxSelectMillis() {
        return SelectionHandler$.MODULE$.MaxSelectMillis();
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

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ SelectionHandler $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof WorkerForCommand) {
                    WorkerForCommand workerForCommand = (WorkerForCommand)A1;
                    this.$outer.spawnChildWithCapacityProtection(workerForCommand, this.$outer.akka$io$SelectionHandler$$settings.SelectorAssociationRetries());
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Retry) {
                    Retry retry2 = (Retry)A1;
                    WorkerForCommand cmd = retry2.command();
                    int retriesLeft = retry2.retriesLeft();
                    this.$outer.spawnChildWithCapacityProtection(cmd, retriesLeft);
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Terminated) {
                    --this.$outer.akka$io$SelectionHandler$$childCount;
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof WorkerForCommand) {
                    return true;
                }
                if (object instanceof Retry) {
                    return true;
                }
                return object instanceof Terminated;
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
    public void postStop() {
        this.registry.shutdown();
    }

    @Override
    public OneForOneStrategy supervisorStrategy() {
        return new OneForOneStrategy(this){

            /*
             * Unable to fully structure code
             */
            public void logFailure(ActorContext context, ActorRef child, Throwable cause, SupervisorStrategy.Directive decision) {
                try {
                    block8: {
                        block7: {
                            var9_5 = false;
                            var10_6 = null;
                            var11_7 = cause;
                            if (!(var11_7 instanceof ActorInitializationException)) break block7;
                            var9_5 = true;
                            var10_6 = (ActorInitializationException)var11_7;
                            if (var10_6.getCause() == null || var10_6.getCause().getMessage() == null) break block7;
                            v0 = var10_6.getCause().getMessage();
                            break block8;
                        }
                        if (!var9_5 || var10_6.getCause() == null) ** GOTO lbl23
                        var12_8 = var10_6.getCause();
                        if (var12_8 instanceof InvocationTargetException) {
                            var13_9 = (InvocationTargetException)var12_8;
                            v0 = var13_9.getTargetException().toString();
                        } else if (var12_8 != null) {
                            var14_10 = var12_8;
                            v0 = Logging$.MODULE$.simpleName(var14_10);
                        } else {
                            throw new MatchError((Object)var12_8);
lbl23:
                            // 1 sources

                            v0 = var11_7.getMessage();
                        }
                    }
                    logMessage = v0;
                    context.system().eventStream().publish(new Logging.Debug(child.path().toString(), SelectionHandler.class, logMessage));
                }
                catch (Throwable var15_12) {
                    var16_13 = var15_12;
                    if (var16_13 != null && !(var17_14 = NonFatal$.MODULE$.unapply(var16_13)).isEmpty()) {
                    }
                    throw var15_12;
                }
            }
        };
    }

    public void spawnChildWithCapacityProtection(WorkerForCommand cmd, int retriesLeft) {
        if (this.akka$io$SelectionHandler$$settings.TraceLogging()) {
            this.log().debug("Executing [{}]", cmd);
        }
        if (this.akka$io$SelectionHandler$$settings.MaxChannelsPerSelector() == -1 || this.akka$io$SelectionHandler$$childCount < this.akka$io$SelectionHandler$$settings.MaxChannelsPerSelector()) {
            String newName = Long.toString(this.sequenceNumber);
            ++this.sequenceNumber;
            ActorRef child = this.context().actorOf(cmd.childProps().apply(this.registry).withDispatcher(this.akka$io$SelectionHandler$$settings.WorkerDispatcher()).withDeploy(Deploy$.MODULE$.local()), newName);
            ++this.akka$io$SelectionHandler$$childCount;
            if (this.akka$io$SelectionHandler$$settings.MaxChannelsPerSelector() > 0) {
                this.context().watch(child);
                return;
            }
            return;
        }
        if (retriesLeft >= 1) {
            this.log().debug("Rejecting [{}] with [{}] retries left, retrying...", cmd, BoxesRunTime.boxToInteger(retriesLeft));
            this.context().parent().forward(new Retry(cmd, retriesLeft - 1), this.context());
            return;
        }
        this.log().warning("Rejecting [{}] with no retries left, aborting...", cmd);
        cmd.commander().$bang(cmd.apiCommand().failureMessage(), this.self());
    }

    public static final PartialFunction akka$io$SelectionHandler$$stoppingDecider$1() {
        return new Serializable(null){
            private static final long serialVersionUID = 0L;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Exception) {
                    return (B1)SupervisorStrategy$Stop$.MODULE$;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                Throwable throwable = x1;
                return throwable instanceof Exception;
            }
        };
    }

    public SelectionHandler(SelectionHandlerSettings settings) {
        this.akka$io$SelectionHandler$$settings = settings;
        Actor.$init$(this);
        ActorLogging.$init$(this);
        this.sequenceNumber = 0L;
        this.akka$io$SelectionHandler$$childCount = 0;
        MessageDispatcher dispatcher = this.context().system().dispatchers().lookup(settings.SelectorDispatcher());
        this.registry = new ChannelRegistryImpl(SerializedSuspendableExecutionContext$.MODULE$.apply(dispatcher.throughput(), dispatcher), settings, this.log());
        Statics.releaseFence();
    }

    private static class ChannelRegistryImpl
    implements ChannelRegistry {
        public final ExecutionContext akka$io$SelectionHandler$ChannelRegistryImpl$$executionContext;
        public final SelectionHandlerSettings akka$io$SelectionHandler$ChannelRegistryImpl$$settings;
        public final LoggingAdapter akka$io$SelectionHandler$ChannelRegistryImpl$$log;
        public final AbstractSelector akka$io$SelectionHandler$ChannelRegistryImpl$$selector;
        public final AtomicBoolean akka$io$SelectionHandler$ChannelRegistryImpl$$wakeUp;
        private final Task select;

        public final int OP_READ_AND_WRITE() {
            return 5;
        }

        @Override
        public void register(SelectableChannel channel, int initialOps, ActorRef channelActor) {
            if (this.akka$io$SelectionHandler$ChannelRegistryImpl$$settings.TraceLogging()) {
                this.akka$io$SelectionHandler$ChannelRegistryImpl$$log.debug(new StringBuilder(48).append("Scheduling Registering channel ").append(channel).append(" with initialOps ").append(initialOps).toString());
            }
            this.execute(new Task(this, channel, initialOps, channelActor){
                private final /* synthetic */ ChannelRegistryImpl $outer;
                private final SelectableChannel channel$1;
                private final int initialOps$1;
                private final ActorRef channelActor$1;

                public void tryRun() {
                    try {
                        if (this.$outer.akka$io$SelectionHandler$ChannelRegistryImpl$$settings.TraceLogging()) {
                            this.$outer.akka$io$SelectionHandler$ChannelRegistryImpl$$log.debug(new StringBuilder(37).append("Registering channel ").append(this.channel$1).append(" with initialOps ").append(this.initialOps$1).toString());
                        }
                        SelectionKey key = this.channel$1.register(this.$outer.akka$io$SelectionHandler$ChannelRegistryImpl$$selector, this.initialOps$1, this.channelActor$1);
                        this.channelActor$1.$bang(new ChannelRegistration(this, key){
                            private final /* synthetic */ ChannelRegistryImpl$$anon$3 $outer;
                            private final SelectionKey key$1;

                            public void enableInterest(int ops) {
                                this.$outer.akka$io$SelectionHandler$ChannelRegistryImpl$$anon$$$outer().akka$io$SelectionHandler$ChannelRegistryImpl$$enableInterestOps(this.key$1, ops);
                            }

                            public void disableInterest(int ops) {
                                this.$outer.akka$io$SelectionHandler$ChannelRegistryImpl$$anon$$$outer().akka$io$SelectionHandler$ChannelRegistryImpl$$disableInterestOps(this.key$1, ops);
                            }

                            public void cancelAndClose(Function0<BoxedUnit> andThen) {
                                this.$outer.akka$io$SelectionHandler$ChannelRegistryImpl$$anon$$$outer().akka$io$SelectionHandler$ChannelRegistryImpl$$cancelKeyAndClose(this.key$1, andThen);
                            }
                            {
                                if ($outer == null) {
                                    throw null;
                                }
                                this.$outer = $outer;
                                this.key$1 = key$1;
                            }
                        }, this.channelActor$1);
                    }
                    catch (ClosedChannelException closedChannelException) {}
                }

                public /* synthetic */ ChannelRegistryImpl akka$io$SelectionHandler$ChannelRegistryImpl$$anon$$$outer() {
                    return this.$outer;
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    this.channel$1 = channel$1;
                    this.initialOps$1 = initialOps$1;
                    this.channelActor$1 = channelActor$1;
                }
            });
        }

        public void shutdown() {
            this.execute(new Task(this){
                private final /* synthetic */ ChannelRegistryImpl $outer;

                public void tryRun() {
                    try {
                        this.closeNextChannel$1(this.$outer.akka$io$SelectionHandler$ChannelRegistryImpl$$selector.keys().iterator());
                    }
                    finally {
                        this.$outer.akka$io$SelectionHandler$ChannelRegistryImpl$$selector.close();
                    }
                }

                private final void closeNextChannel$1(java.util.Iterator it) {
                    while (it.hasNext()) {
                        try {
                            ((SelectionKey)it.next()).channel().close();
                        }
                        catch (Throwable throwable) {
                            Option<Throwable> option;
                            Throwable throwable2 = throwable;
                            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                                Throwable e = option.get();
                                this.$outer.akka$io$SelectionHandler$ChannelRegistryImpl$$log.debug("Error closing channel: {}", e);
                                continue;
                            }
                            throw throwable;
                        }
                    }
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                }
            });
        }

        public void akka$io$SelectionHandler$ChannelRegistryImpl$$enableInterestOps(SelectionKey key, int ops) {
            this.execute(new Task(this, ops, key){
                private final /* synthetic */ ChannelRegistryImpl $outer;
                private final int ops$1;
                private final SelectionKey key$2;

                public void tryRun() {
                    int currentOps;
                    int newOps;
                    if (this.$outer.akka$io$SelectionHandler$ChannelRegistryImpl$$settings.TraceLogging()) {
                        this.$outer.akka$io$SelectionHandler$ChannelRegistryImpl$$log.debug(new StringBuilder(13).append("Enabling ").append(this.ops$1).append(" on ").append(this.key$2).toString());
                    }
                    if ((newOps = (currentOps = this.key$2.interestOps()) | this.ops$1) != currentOps) {
                        this.key$2.interestOps(newOps);
                        return;
                    }
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    this.ops$1 = ops$1;
                    this.key$2 = key$2;
                }
            });
        }

        public void akka$io$SelectionHandler$ChannelRegistryImpl$$cancelKeyAndClose(SelectionKey key, Function0<BoxedUnit> andThen) {
            this.execute(new Task(this, key, andThen){
                private final /* synthetic */ ChannelRegistryImpl $outer;
                private final SelectionKey key$3;
                private final Function0 andThen$1;

                public void tryRun() {
                    Try$.MODULE$.apply(() -> $this.key$3.cancel());
                    Try$.MODULE$.apply(() -> $this.key$3.channel().close());
                    this.$outer.akka$io$SelectionHandler$ChannelRegistryImpl$$runThunk(this.andThen$1);
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    this.key$3 = key$3;
                    this.andThen$1 = andThen$1;
                }

                private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                    return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$tryRun$1(akka.io.SelectionHandler$ChannelRegistryImpl$$anon$7 ), $anonfun$tryRun$2(akka.io.SelectionHandler$ChannelRegistryImpl$$anon$7 )}, serializedLambda);
                }
            });
        }

        public void akka$io$SelectionHandler$ChannelRegistryImpl$$runThunk(Function0<BoxedUnit> andThen) {
            this.execute(new Task(this, andThen){
                private final Function0 andThen$2;

                public void tryRun() {
                    this.andThen$2.apply$mcV$sp();
                }
                {
                    this.andThen$2 = andThen$2;
                }
            });
        }

        public void akka$io$SelectionHandler$ChannelRegistryImpl$$disableInterestOps(SelectionKey key, int ops) {
            this.execute(new Task(this, key, ops){
                private final SelectionKey key$4;
                private final int ops$2;

                public void tryRun() {
                    int currentOps = this.key$4.interestOps();
                    int newOps = currentOps & ~this.ops$2;
                    if (newOps != currentOps) {
                        this.key$4.interestOps(newOps);
                        return;
                    }
                }
                {
                    this.key$4 = key$4;
                    this.ops$2 = ops$2;
                }
            });
        }

        private void execute(Task task) {
            this.akka$io$SelectionHandler$ChannelRegistryImpl$$executionContext.execute(task);
            if (this.akka$io$SelectionHandler$ChannelRegistryImpl$$wakeUp.compareAndSet(false, true)) {
                this.akka$io$SelectionHandler$ChannelRegistryImpl$$selector.wakeup();
                return;
            }
        }

        public ChannelRegistryImpl(ExecutionContext executionContext, SelectionHandlerSettings settings, LoggingAdapter log) {
            this.akka$io$SelectionHandler$ChannelRegistryImpl$$executionContext = executionContext;
            this.akka$io$SelectionHandler$ChannelRegistryImpl$$settings = settings;
            this.akka$io$SelectionHandler$ChannelRegistryImpl$$log = log;
            this.akka$io$SelectionHandler$ChannelRegistryImpl$$selector = SelectorProvider.provider().openSelector();
            this.akka$io$SelectionHandler$ChannelRegistryImpl$$wakeUp = new AtomicBoolean(false);
            this.select = new Task(this){
                private final /* synthetic */ ChannelRegistryImpl $outer;

                public void tryRun() {
                    if (this.$outer.akka$io$SelectionHandler$ChannelRegistryImpl$$selector.select(SelectionHandler$.MODULE$.MaxSelectMillis()) > 0) {
                        Set<SelectionKey> keys2 = this.$outer.akka$io$SelectionHandler$ChannelRegistryImpl$$selector.selectedKeys();
                        for (SelectionKey key : keys2) {
                            if (!key.isValid()) continue;
                            try {
                                int readyOps = key.readyOps();
                                key.interestOps(key.interestOps() & ~readyOps);
                                ActorRef connection = (ActorRef)key.attachment();
                                int n = readyOps;
                                switch (n) {
                                    case 1: {
                                        SelectionHandler$ChannelReadable$ x$1 = SelectionHandler$ChannelReadable$.MODULE$;
                                        ActorRef x$2 = connection.$bang$default$2(x$1);
                                        connection.$bang(x$1, x$2);
                                        break;
                                    }
                                    case 4: {
                                        SelectionHandler$ChannelWritable$ x$3 = SelectionHandler$ChannelWritable$.MODULE$;
                                        ActorRef x$4 = connection.$bang$default$2(x$3);
                                        connection.$bang(x$3, x$4);
                                        break;
                                    }
                                    case 5: {
                                        SelectionHandler$ChannelWritable$ x$5 = SelectionHandler$ChannelWritable$.MODULE$;
                                        ActorRef x$6 = connection.$bang$default$2(x$5);
                                        connection.$bang(x$5, x$6);
                                        SelectionHandler$ChannelReadable$ x$7 = SelectionHandler$ChannelReadable$.MODULE$;
                                        ActorRef x$8 = connection.$bang$default$2(x$7);
                                        connection.$bang(x$7, x$8);
                                        break;
                                    }
                                    default: {
                                        if ((n & 0x10) > 0) {
                                            SelectionHandler$ChannelAcceptable$ x$9 = SelectionHandler$ChannelAcceptable$.MODULE$;
                                            ActorRef x$10 = connection.$bang$default$2(x$9);
                                            connection.$bang(x$9, x$10);
                                            break;
                                        }
                                        if ((n & 8) > 0) {
                                            SelectionHandler$ChannelConnectable$ x$11 = SelectionHandler$ChannelConnectable$.MODULE$;
                                            ActorRef x$12 = connection.$bang$default$2(x$11);
                                            connection.$bang(x$11, x$12);
                                            break;
                                        }
                                        this.$outer.akka$io$SelectionHandler$ChannelRegistryImpl$$log.warning("Invalid readyOps: [{}]", BoxesRunTime.boxToInteger(n));
                                        break;
                                    }
                                }
                            }
                            catch (CancelledKeyException cancelledKeyException) {}
                        }
                        keys2.clear();
                    }
                    this.$outer.akka$io$SelectionHandler$ChannelRegistryImpl$$wakeUp.set(false);
                }

                public void run() {
                    if (this.$outer.akka$io$SelectionHandler$ChannelRegistryImpl$$selector.isOpen()) {
                        try {
                            super.run();
                        }
                        finally {
                            this.$outer.akka$io$SelectionHandler$ChannelRegistryImpl$$executionContext.execute(this);
                        }
                        return;
                    }
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                }
            };
            executionContext.execute(this.select);
        }

        private abstract class Task
        implements Runnable {
            public abstract void tryRun();

            @Override
            public void run() {
                try {
                    this.tryRun();
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 instanceof CancelledKeyException) {
                    }
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable e = option.get();
                        this.akka$io$SelectionHandler$ChannelRegistryImpl$Task$$$outer().akka$io$SelectionHandler$ChannelRegistryImpl$$log.error(e, "Error during selector management task: [{}]", (Object)e);
                    }
                    throw throwable;
                }
            }

            public /* synthetic */ ChannelRegistryImpl akka$io$SelectionHandler$ChannelRegistryImpl$Task$$$outer() {
                return ChannelRegistryImpl.this;
            }

            public Task() {
                if (ChannelRegistryImpl.this == null) {
                    throw null;
                }
            }
        }
    }

    public static interface HasFailureMessage {
        public Object failureMessage();
    }

    public static final class Retry
    implements NoSerializationVerificationNeeded,
    Product,
    Serializable {
        private final WorkerForCommand command;
        private final int retriesLeft;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public WorkerForCommand command() {
            return this.command;
        }

        public int retriesLeft() {
            return this.retriesLeft;
        }

        public Retry copy(WorkerForCommand command, int retriesLeft) {
            return new Retry(command, retriesLeft);
        }

        public WorkerForCommand copy$default$1() {
            return this.command();
        }

        public int copy$default$2() {
            return this.retriesLeft();
        }

        @Override
        public String productPrefix() {
            return "Retry";
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
                    return this.command();
                }
                case 1: {
                    return BoxesRunTime.boxToInteger(this.retriesLeft());
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
            return x$1 instanceof Retry;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "command";
                }
                case 1: {
                    return "retriesLeft";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.anyHash(this.command()));
            n = Statics.mix(n, this.retriesLeft());
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
            if (!(object instanceof Retry)) return false;
            boolean bl = true;
            if (!bl) return false;
            Retry retry2 = (Retry)x$1;
            if (this.retriesLeft() != retry2.retriesLeft()) return false;
            WorkerForCommand workerForCommand = this.command();
            WorkerForCommand workerForCommand2 = retry2.command();
            if (workerForCommand != null) {
                if (!((Object)workerForCommand).equals(workerForCommand2)) return false;
                return true;
            }
            if (workerForCommand2 == null) return true;
            return false;
        }

        public Retry(WorkerForCommand command, int retriesLeft) {
            this.command = command;
            this.retriesLeft = retriesLeft;
            Product.$init$(this);
            Predef$.MODULE$.require(retriesLeft >= 0);
        }
    }

    public static abstract class SelectorBasedManager
    implements Actor {
        private final ActorRef selectorPool;
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

        @Override
        public SupervisorStrategy supervisorStrategy() {
            return SelectionHandler$.MODULE$.connectionSupervisorStrategy();
        }

        public ActorRef selectorPool() {
            return this.selectorPool;
        }

        public final PartialFunction<Object, BoxedUnit> workerForCommandHandler(PartialFunction<HasFailureMessage, Function1<ChannelRegistry, Props>> pf) {
            return new Serializable(this, pf){
                private static final long serialVersionUID = 0L;
                private final /* synthetic */ SelectorBasedManager $outer;
                private final PartialFunction pf$1;

                public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                    HasFailureMessage hasFailureMessage;
                    A1 A1 = x1;
                    if (A1 instanceof HasFailureMessage && this.pf$1.isDefinedAt(hasFailureMessage = (HasFailureMessage)A1)) {
                        this.$outer.selectorPool().$bang(new WorkerForCommand(hasFailureMessage, this.$outer.sender(), (Function1)this.pf$1.apply(hasFailureMessage)), this.$outer.self());
                        return (B1)BoxedUnit.UNIT;
                    }
                    return function1.apply(x1);
                }

                public final boolean isDefinedAt(Object x1) {
                    HasFailureMessage hasFailureMessage;
                    Object object = x1;
                    return object instanceof HasFailureMessage && this.pf$1.isDefinedAt(hasFailureMessage = (HasFailureMessage)object);
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    this.pf$1 = pf$1;
                }
            };
        }

        public SelectorBasedManager(SelectionHandlerSettings selectorSettings, int nrOfSelectors) {
            Actor.$init$(this);
            Props routeeProps = Props$.MODULE$.apply(SelectionHandler.class, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{selectorSettings})).withDispatcher(this.context().props().dispatcher()).withDeploy(Deploy$.MODULE$.local());
            int x$1 = nrOfSelectors;
            String x$2 = this.context().props().dispatcher();
            Option<Resizer> x$3 = RandomPool$.MODULE$.apply$default$2();
            SupervisorStrategy x$4 = RandomPool$.MODULE$.apply$default$3();
            boolean x$5 = RandomPool$.MODULE$.apply$default$5();
            this.selectorPool = this.context().actorOf(new RandomPool(x$1, x$3, x$4, x$2, x$5).props(routeeProps).withDeploy(Deploy$.MODULE$.local()), "selectors");
            Statics.releaseFence();
        }
    }

    public static final class WorkerForCommand
    implements NoSerializationVerificationNeeded,
    Product,
    Serializable {
        private final HasFailureMessage apiCommand;
        private final ActorRef commander;
        private final Function1<ChannelRegistry, Props> childProps;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public HasFailureMessage apiCommand() {
            return this.apiCommand;
        }

        public ActorRef commander() {
            return this.commander;
        }

        public Function1<ChannelRegistry, Props> childProps() {
            return this.childProps;
        }

        public WorkerForCommand copy(HasFailureMessage apiCommand, ActorRef commander, Function1<ChannelRegistry, Props> childProps) {
            return new WorkerForCommand(apiCommand, commander, childProps);
        }

        public HasFailureMessage copy$default$1() {
            return this.apiCommand();
        }

        public ActorRef copy$default$2() {
            return this.commander();
        }

        public Function1<ChannelRegistry, Props> copy$default$3() {
            return this.childProps();
        }

        @Override
        public String productPrefix() {
            return "WorkerForCommand";
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
                    return this.apiCommand();
                }
                case 1: {
                    return this.commander();
                }
                case 2: {
                    return this.childProps();
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
            return x$1 instanceof WorkerForCommand;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "apiCommand";
                }
                case 1: {
                    return "commander";
                }
                case 2: {
                    return "childProps";
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
            if (!(object instanceof WorkerForCommand)) return false;
            boolean bl = true;
            if (!bl) return false;
            WorkerForCommand workerForCommand = (WorkerForCommand)x$1;
            HasFailureMessage hasFailureMessage = this.apiCommand();
            HasFailureMessage hasFailureMessage2 = workerForCommand.apiCommand();
            if (hasFailureMessage == null) {
                if (hasFailureMessage2 != null) {
                    return false;
                }
            } else if (!hasFailureMessage.equals(hasFailureMessage2)) return false;
            ActorRef actorRef2 = this.commander();
            ActorRef actorRef3 = workerForCommand.commander();
            if (actorRef2 == null) {
                if (actorRef3 != null) {
                    return false;
                }
            } else if (!((Object)actorRef2).equals(actorRef3)) return false;
            Function1<ChannelRegistry, Props> function1 = this.childProps();
            Function1<ChannelRegistry, Props> function12 = workerForCommand.childProps();
            if (function1 == null) {
                if (function12 == null) return true;
                return false;
            } else {
                if (!function1.equals(function12)) return false;
                return true;
            }
        }

        public WorkerForCommand(HasFailureMessage apiCommand, ActorRef commander, Function1<ChannelRegistry, Props> childProps) {
            this.apiCommand = apiCommand;
            this.commander = commander;
            this.childProps = childProps;
            Product.$init$(this);
        }
    }
}

