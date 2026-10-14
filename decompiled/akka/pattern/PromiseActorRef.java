/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.Actor$;
import akka.actor.ActorCell$;
import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorRefProvider;
import akka.actor.ActorSelection;
import akka.actor.InternalActorRef;
import akka.actor.InvalidMessageException$;
import akka.actor.LocalRef;
import akka.actor.MinimalActorRef;
import akka.actor.Status;
import akka.actor.Terminated;
import akka.actor.Terminated$;
import akka.annotation.InternalStableApi;
import akka.dispatch.sysmsg.DeathWatchNotification;
import akka.dispatch.sysmsg.SystemMessage;
import akka.dispatch.sysmsg.Terminate;
import akka.dispatch.sysmsg.Unwatch;
import akka.dispatch.sysmsg.Watch;
import akka.pattern.AbstractPromiseActorRef;
import akka.pattern.PromiseActorRef$;
import akka.pattern.PromiseActorRef$Registering$;
import akka.pattern.PromiseActorRef$Stopped$;
import akka.util.Timeout;
import akka.util.Unsafe;
import java.io.ObjectStreamException;
import java.io.Serializable;
import scala.Function1;
import scala.Predef$;
import scala.Product;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.collection.immutable.Set;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\r\u001df!\u0002-Z\u0005mk\u0006\u0002C4\u0001\u0005\u000b\u0007I\u0011A5\t\u00115\u0004!\u0011!Q\u0001\n)D\u0001B\u001c\u0001\u0003\u0006\u0004%\ta\u001c\u0005\ty\u0002\u0011\t\u0011)A\u0005a\"AQ\u0010\u0001B\u0001B\u0003%a\u0010C\u0005\u0002\u0014\u0001\u0011\t\u0011)A\u0005}\"9\u0011Q\u0003\u0001\u0005\u0002\u0005]\u0001\"CA\u0013\u0001\u0001\u0007I\u0011AA\u0014\u0011%\tI\u0003\u0001a\u0001\n\u0003\tY\u0003C\u0004\u00028\u0001\u0001\u000b\u0015\u0002@\t\u0017\u0005\u0005\u0003\u00011A\u0001B\u0003&\u00111\t\u0005\t\u0003\u0017\u0002\u0001\u0015)\u0003\u0002N!9\u00111\f\u0001\u0005\n\u0005u\u0003\u0002CA0\u0001\u0001&I!!\u0018\t\u0011\u0005%\u0004\u0001)C\u0005\u0003WB\u0001\"! \u0001A\u00135\u0011q\u0010\u0005\t\u0003'\u0003\u0001\u0015\"\u0004\u0002\u0016\"A\u00111\u0014\u0001!\n\u001b\ti\n\u0003\u0005\u0002\"\u0002\u0001K\u0011BAR\u0011!\t9\u000b\u0001Q\u0005\n\u0005%\u0006\u0002CA[\u0001\u0001&I!a.\t\u000f\u0005u\u0006\u0001\"\u0011\u0002@\"9\u0011\u0011\u0019\u0001\u0005\u0002\u0005\r\u0007bBAg\u0001\u0011\u0005\u0013q\u001a\u0005\n\u00037\u0004\u0011\u0013!C\u0001\u0003;Dq!!=\u0001\t\u0003\n\u0019\u0010\u0003\u0005\u0003\b\u0001!\te\u0017B\u0005\u0011\u001d\u0011Y\u0001\u0001C!\u0005\u001bA\u0001B!\u0005\u0001\t\u0003Y&1\u0003\u0005\t\u0005#\u0001A\u0011A.\u0003L!A!\u0011\f\u0001\u0005\u0002m\u0013Y\u0006\u0003\u0005\u0003j\u0001!\ta\u0017B6\u000f!\u0011\u0019(\u0017E\u00017\nUda\u0002-Z\u0011\u0003Y&q\u000f\u0005\b\u0003+\u0011C\u0011\u0001BE\u000f\u001d\u0011YI\tEE\u0005\u001b3qA!%#\u0011\u0013\u0013\u0019\nC\u0004\u0002\u0016\u0015\"\tAa+\t\u0013\t5V%!A\u0005B\t=\u0006\"\u0003B^K\u0005\u0005I\u0011\u0001B_\u0011%\u0011)-JA\u0001\n\u0003\u00119\rC\u0005\u0003L\u0016\n\t\u0011\"\u0011\u0003N\"I!1\\\u0013\u0002\u0002\u0013\u0005!Q\u001c\u0005\n\u0005C,\u0013\u0011!C!\u0005GD\u0011B!:&\u0003\u0003%\tEa:\t\u0013\t%X%!A\u0005\n\t-xa\u0002BzE!%%Q\u001f\u0004\b\u0005o\u0014\u0003\u0012\u0012B}\u0011\u001d\t)\u0002\rC\u0001\u0005wD\u0011B!,1\u0003\u0003%\tEa,\t\u0013\tm\u0006'!A\u0005\u0002\tu\u0006\"\u0003Bca\u0005\u0005I\u0011\u0001B\u007f\u0011%\u0011Y\rMA\u0001\n\u0003\u0012i\rC\u0005\u0003\\B\n\t\u0011\"\u0001\u0004\u0002!I!\u0011\u001d\u0019\u0002\u0002\u0013\u0005#1\u001d\u0005\n\u0005K\u0004\u0014\u0011!C!\u0005OD\u0011B!;1\u0003\u0003%IAa;\u0007\r\r\u0015!ERB\u0004\u0011)\t\tM\u000fBK\u0002\u0013\u0005\u00111\u0019\u0005\u000b\u0007\u0013Q$\u0011#Q\u0001\n\u0005\u0015\u0007bBA\u000bu\u0011\u000511\u0002\u0005\n\u0007#Q\u0014\u0011!C\u0001\u0007'A\u0011ba\u0006;#\u0003%\ta!\u0007\t\u0013\t5&(!A\u0005B\t=\u0006\"\u0003B^u\u0005\u0005I\u0011\u0001B_\u0011%\u0011)MOA\u0001\n\u0003\u0019i\u0002C\u0005\u0003Lj\n\t\u0011\"\u0011\u0003N\"I!1\u001c\u001e\u0002\u0002\u0013\u00051\u0011\u0005\u0005\n\u0007KQ\u0014\u0011!C!\u0007OA\u0011B!9;\u0003\u0003%\tEa9\t\u0013\t\u0015((!A\u0005B\t\u001d\b\"CB\u0016u\u0005\u0005I\u0011IB\u0017\u000f%\u0019\tDIA\u0001\u0012\u0013\u0019\u0019DB\u0005\u0004\u0006\t\n\t\u0011#\u0003\u00046!9\u0011Q\u0003&\u0005\u0002\r\r\u0003\"\u0003Bs\u0015\u0006\u0005IQ\tBt\u0011%\u0019)ESA\u0001\n\u0003\u001b9\u0005C\u0005\u0004L)\u000b\t\u0011\"!\u0004N!I!\u0011\u001e&\u0002\u0002\u0013%!1\u001e\u0005\n\u00073\u0012#\u0019!C\u0005\u00077B\u0001b!\u001c#A\u0003%1Q\f\u0005\n\u0007_\u0012#\u0019!C\u0005\u0007cB\u0001ba #A\u0003%11\u000f\u0005\b\u0007\u000b\u0012C\u0011ABA\u0011%\u0019\u0019JII\u0001\n\u0003\u0019)\nC\u0005\u0004\u0018\n\n\n\u0011\"\u0001\u0004\u001a\"I!\u0011\u001e\u0012\u0002\u0002\u0013%!1\u001e\u0002\u0010!J|W.[:f\u0003\u000e$xN\u001d*fM*\u0011!lW\u0001\ba\u0006$H/\u001a:o\u0015\u0005a\u0016\u0001B1lW\u0006\u001c2\u0001\u00010e!\ty&-D\u0001a\u0015\t\t7,A\u0003bGR|'/\u0003\u0002dA\n\u0001\u0012J\u001c;fe:\fG.Q2u_J\u0014VM\u001a\t\u0003?\u0016L!A\u001a1\u0003\u001f5Kg.[7bY\u0006\u001bGo\u001c:SK\u001a\f\u0001\u0002\u001d:pm&$WM]\u0002\u0001+\u0005Q\u0007CA0l\u0013\ta\u0007M\u0001\tBGR|'OU3g!J|g/\u001b3fe\u0006I\u0001O]8wS\u0012,'\u000fI\u0001\u0007e\u0016\u001cX\u000f\u001c;\u0016\u0003A\u00042!\u001d<y\u001b\u0005\u0011(BA:u\u0003)\u0019wN\\2veJ,g\u000e\u001e\u0006\u0002k\u0006)1oY1mC&\u0011qO\u001d\u0002\b!J|W.[:f!\tI(0D\u0001u\u0013\tYHOA\u0002B]f\fqA]3tk2$\b%\u0001\u0003`[\u000et\u0007cA@\u0002\u000e9!\u0011\u0011AA\u0005!\r\t\u0019\u0001^\u0007\u0003\u0003\u000bQ1!a\u0002i\u0003\u0019a$o\\8u}%\u0019\u00111\u0002;\u0002\rA\u0013X\rZ3g\u0013\u0011\ty!!\u0005\u0003\rM#(/\u001b8h\u0015\r\tY\u0001^\u0001\u000ee\u00164\u0007+\u0019;i!J,g-\u001b=\u0002\rqJg.\u001b;?))\tI\"!\b\u0002 \u0005\u0005\u00121\u0005\t\u0004\u00037\u0001Q\"A-\t\u000b\u001d<\u0001\u0019\u00016\t\u000b9<\u0001\u0019\u00019\t\u000bu<\u0001\u0019\u0001@\t\r\u0005Mq\u00011\u0001\u007f\u0003AiWm]:bO\u0016\u001cE.Y:t\u001d\u0006lW-F\u0001\u007f\u0003QiWm]:bO\u0016\u001cE.Y:t\u001d\u0006lWm\u0018\u0013fcR!\u0011QFA\u001a!\rI\u0018qF\u0005\u0004\u0003c!(\u0001B+oSRD\u0001\"!\u000e\n\u0003\u0003\u0005\rA`\u0001\u0004q\u0012\n\u0014!E7fgN\fw-Z\"mCN\u001ch*Y7fA!\u001a!\"a\u000f\u0011\u0007e\fi$C\u0002\u0002@Q\u0014\u0001B^8mCRLG.Z\u0001\u001a?N$\u0018\r^3E_:{GoQ1mY6+G)\u001b:fGRd\u0017\u0010E\u0002z\u0003\u000bJ1!a\u0012u\u0005\u0019\te.\u001f*fM\"\u001a1\"a\u000f\u0002;};\u0018\r^2iK\u0012\u0014\u0015\u0010R8O_R\u001c\u0015\r\u001c7NK\u0012K'/Z2uYf\u0004Ra`A(\u0003'JA!!\u0015\u0002\u0012\t\u00191+\u001a;\u0011\u0007}\u000b)&C\u0002\u0002X\u0001\u0014\u0001\"Q2u_J\u0014VM\u001a\u0015\u0004\u0019\u0005m\u0012\u0001H0qe\u00164XM\u001c;Qe&4\u0018\r^3V]V\u001cX\rZ#sCN,(/Z\u000b\u0003\u0003\u001b\n\u0011b^1uG\",GMQ=)\u00079\t\u0019\u0007E\u0002z\u0003KJ1!a\u001au\u0005\u0019Ig\u000e\\5oK\u0006yQ\u000f\u001d3bi\u0016<\u0016\r^2iK\u0012\u0014\u0015\u0010\u0006\u0004\u0002n\u0005M\u0014q\u000f\t\u0004s\u0006=\u0014bAA9i\n9!i\\8mK\u0006t\u0007bBA;\u001f\u0001\u0007\u0011QJ\u0001\r_2$w+\u0019;dQ\u0016$')\u001f\u0005\b\u0003sz\u0001\u0019AA'\u00031qWm^,bi\u000eDW\r\u001a\"zQ\ry\u00111M\u0001\u000bC\u0012$w+\u0019;dQ\u0016\u0014H\u0003BA7\u0003\u0003Cq!a!\u0011\u0001\u0004\t\u0019&A\u0004xCR\u001c\u0007.\u001a:)\u0007A\t9\t\u0005\u0003\u0002\n\u0006=UBAAF\u0015\r\ti\t^\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BAI\u0003\u0017\u0013q\u0001^1jYJ,7-\u0001\u0006sK6<\u0016\r^2iKJ$B!!\f\u0002\u0018\"9\u00111Q\tA\u0002\u0005M\u0003fA\t\u0002\b\u0006i1\r\\3be^\u000bGo\u00195feN$\"!!\u0014)\u0007I\t9)A\u0003ti\u0006$X-\u0006\u0002\u0002D!\u001a1#a\u0019\u0002\u0017U\u0004H-\u0019;f'R\fG/\u001a\u000b\u0007\u0003[\nY+a,\t\u000f\u00055F\u00031\u0001\u0002D\u0005Aq\u000e\u001c3Ti\u0006$X\rC\u0004\u00022R\u0001\r!a\u0011\u0002\u00119,wo\u0015;bi\u0016D3\u0001FA2\u0003!\u0019X\r^*uCR,G\u0003BA\u0017\u0003sCq!!-\u0016\u0001\u0004\t\u0019\u0005K\u0002\u0016\u0003G\n\u0011bZ3u!\u0006\u0014XM\u001c;\u0016\u0003y\u000bA\u0001]1uQV\u0011\u0011Q\u0019\t\u0004?\u0006\u001d\u0017bAAeA\nI\u0011i\u0019;peB\u000bG\u000f\u001b\u0015\u0004/\u0005\u001d\u0015!\u0002\u0013cC:<G\u0003BAi\u0003/$B!!\f\u0002T\"I\u0011Q\u001b\r\u0011\u0002\u0003\u000f\u00111K\u0001\u0007g\u0016tG-\u001a:\t\r\u0005e\u0007\u00041\u0001y\u0003\u001diWm]:bO\u0016\fq\u0002\n2b]\u001e$C-\u001a4bk2$HE\r\u000b\u0005\u0003?\fyO\u000b\u0003\u0002T\u0005\u00058FAAr!\u0011\t)/a;\u000e\u0005\u0005\u001d(\u0002BAu\u0003\u0017\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\n\t\u00055\u0018q\u001d\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0007BBAm3\u0001\u0007\u00010A\ttK:$7+_:uK6lUm]:bO\u0016$B!!\f\u0002v\"9\u0011\u0011\u001c\u000eA\u0002\u0005]\b\u0003BA}\u0005\u0007i!!a?\u000b\t\u0005u\u0018q`\u0001\u0007gf\u001cXn]4\u000b\u0007\t\u00051,\u0001\u0005eSN\u0004\u0018\r^2i\u0013\u0011\u0011)!a?\u0003\u001bMK8\u000f^3n\u001b\u0016\u001c8/Y4f\u00031I7\u000fV3s[&t\u0017\r^3e+\t\ti'\u0001\u0003ti>\u0004HCAA\u0017Q\ra\u0012qQ\u0001\u0004CN\\G\u0003\u0003B\u000b\u00057\u0011)Ca\n\u0011\tE\u00149\u0002_\u0005\u0004\u00053\u0011(A\u0002$viV\u0014X\rC\u0004\u0003\u001eu\u0001\rAa\b\u0002\u0011\u0005\u001cGo\u001c:TK2\u00042a\u0018B\u0011\u0013\r\u0011\u0019\u0003\u0019\u0002\u000f\u0003\u000e$xN]*fY\u0016\u001cG/[8o\u0011\u0019\tI.\ba\u0001q\"9!\u0011F\u000fA\u0002\t-\u0012a\u0002;j[\u0016|W\u000f\u001e\t\u0005\u0005[\u0011\u0019$\u0004\u0002\u00030)\u0019!\u0011G.\u0002\tU$\u0018\u000e\\\u0005\u0005\u0005k\u0011yCA\u0004US6,w.\u001e;)\t\t\u001d\"\u0011\b\t\u0005\u0005[\u0011Y$\u0003\u0003\u0003>\t=\"AB;okN,G\rK\u0002\u001e\u0005\u0003\u0002BAa\u0011\u0003H5\u0011!Q\t\u0006\u0004\u0003\u001b[\u0016\u0002\u0002B%\u0005\u000b\u0012\u0011#\u00138uKJt\u0017\r\\*uC\ndW-\u00119j)!\u0011)B!\u0014\u0003R\tM\u0003b\u0002B(=\u0001\u0007\u00111K\u0001\tC\u000e$xN\u001d*fM\"1\u0011\u0011\u001c\u0010A\u0002aDqA!\u000b\u001f\u0001\u0004\u0011Y\u0003\u000b\u0003\u0003T\te\u0002f\u0001\u0010\u0003B\u0005QqN\\\"p[BdW\r^3\u0015\r\u00055\"Q\fB1\u0011\u0019\tIn\ba\u0001q\"\"!Q\fB\u001d\u0011\u001d\u0011\u0019g\ba\u0001\u0003[\n\u0001#\u00197sK\u0006$\u0017pQ8na2,G/\u001a3)\t\t\u0005$\u0011\b\u0015\u0004?\t\u0005\u0013!C8o)&lWm\\;u)\u0011\tiC!\u001c\t\u000f\t%\u0002\u00051\u0001\u0003,!\"!Q\u000eB\u001dQ\r\u0001#\u0011I\u0001\u0010!J|W.[:f\u0003\u000e$xN\u001d*fMB\u0019\u00111\u0004\u0012\u0014\u000b\t\n\u0019E!\u001f\u0011\t\tm$QQ\u0007\u0003\u0005{RAAa \u0003\u0002\u0006\u0011\u0011n\u001c\u0006\u0003\u0005\u0007\u000bAA[1wC&!!q\u0011B?\u00051\u0019VM]5bY&T\u0018M\u00197f)\t\u0011)(A\u0006SK\u001eL7\u000f^3sS:<\u0007c\u0001BHK5\t!EA\u0006SK\u001eL7\u000f^3sS:<7cB\u0013\u0002D\tU%1\u0014\t\u0004s\n]\u0015b\u0001BMi\n9\u0001K]8ek\u000e$\b\u0003\u0002BO\u0005OsAAa(\u0003$:!\u00111\u0001BQ\u0013\u0005)\u0018b\u0001BSi\u00069\u0001/Y2lC\u001e,\u0017\u0002\u0002BD\u0005SS1A!*u)\t\u0011i)A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0005c\u0003BAa-\u0003:6\u0011!Q\u0017\u0006\u0005\u0005o\u0013\t)\u0001\u0003mC:<\u0017\u0002BA\b\u0005k\u000bA\u0002\u001d:pIV\u001cG/\u0011:jif,\"Aa0\u0011\u0007e\u0014\t-C\u0002\u0003DR\u00141!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$2\u0001\u001fBe\u0011%\t)$KA\u0001\u0002\u0004\u0011y,A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\u0011y\rE\u0003\u0003R\n]\u00070\u0004\u0002\u0003T*\u0019!Q\u001b;\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0003Z\nM'\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!!\u001c\u0003`\"A\u0011QG\u0016\u0002\u0002\u0003\u0007\u00010\u0001\u0005iCND7i\u001c3f)\t\u0011y,\u0001\u0005u_N#(/\u001b8h)\t\u0011\t,\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0003nB!!1\u0017Bx\u0013\u0011\u0011\tP!.\u0003\r=\u0013'.Z2u\u0003\u001d\u0019Fo\u001c9qK\u0012\u00042Aa$1\u0005\u001d\u0019Fo\u001c9qK\u0012\u001cr\u0001MA\"\u0005+\u0013Y\n\u0006\u0002\u0003vR\u0019\u0001Pa@\t\u0013\u0005UB'!AA\u0002\t}F\u0003BA7\u0007\u0007A\u0001\"!\u000e7\u0003\u0003\u0005\r\u0001\u001f\u0002\u0010'R|\u0007\u000f]3e/&$\b\u000eU1uQN9!(a\u0011\u0003\u0016\nm\u0015!\u00029bi\"\u0004C\u0003BB\u0007\u0007\u001f\u00012Aa$;\u0011\u001d\t\t-\u0010a\u0001\u0003\u000b\fAaY8qsR!1QBB\u000b\u0011%\t\tM\u0010I\u0001\u0002\u0004\t)-\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\rm!\u0006BAc\u0003C$2\u0001_B\u0010\u0011%\t)DQA\u0001\u0002\u0004\u0011y\f\u0006\u0003\u0002n\r\r\u0002\u0002CA\u001b\t\u0006\u0005\t\u0019\u0001=\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0005c\u001bI\u0003C\u0005\u00026\u0015\u000b\t\u00111\u0001\u0003@\u00061Q-];bYN$B!!\u001c\u00040!A\u0011Q\u0007%\u0002\u0002\u0003\u0007\u00010A\bTi>\u0004\b/\u001a3XSRD\u0007+\u0019;i!\r\u0011yIS\n\u0006\u0015\u000e]\"\u0011\u0010\t\t\u0007s\u0019y$!2\u0004\u000e5\u001111\b\u0006\u0004\u0007{!\u0018a\u0002:v]RLW.Z\u0005\u0005\u0007\u0003\u001aYDA\tBEN$(/Y2u\rVt7\r^5p]F\"\"aa\r\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\t\r51\u0011\n\u0005\b\u0003\u0003l\u0005\u0019AAc\u0003\u001d)h.\u00199qYf$Baa\u0014\u0004VA)\u0011p!\u0015\u0002F&\u001911\u000b;\u0003\r=\u0003H/[8o\u0011%\u00199FTA\u0001\u0002\u0004\u0019i!A\u0002yIA\nq\"Q2u_J\u001cFo\u001c9SKN,H\u000e^\u000b\u0003\u0007;\u0002baa\u0018\u0004d\r\u001dTBAB1\u0015\r\u0011\t\u0004^\u0005\u0005\u0007K\u001a\tGA\u0004GC&dWO]3\u0011\u0007e\u001cI'C\u0002\u0004lQ\u0014qAT8uQ&tw-\u0001\tBGR|'o\u0015;paJ+7/\u001e7uA\u0005\u0001B-\u001a4bk2$xJ\u001c+j[\u0016|W\u000f^\u000b\u0003\u0007g\u0002b!_B;}\u000ee\u0014bAB<i\nIa)\u001e8di&|g.\r\t\u0005\u0005;\u001bY(\u0003\u0003\u0004~\t%&!\u0003+ie><\u0018M\u00197f\u0003E!WMZ1vYR|e\u000eV5nK>,H\u000f\t\u000b\u0011\u00033\u0019\u0019i!\"\u0004\b\u000e-5QRBH\u0007#CQa\u001a+A\u0002)DqA!\u000bU\u0001\u0004\u0011Y\u0003\u0003\u0004\u0004\nR\u0003\r\u0001_\u0001\u000bi\u0006\u0014x-\u001a;OC6,\u0007BBA\u0013)\u0002\u0007a\u0010\u0003\u0004\u0002\u0014Q\u0003\rA \u0005\n\u0003+$\u0006\u0013!a\u0001\u0003'B\u0011B!\u001bU!\u0003\u0005\raa\u001d\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIY*\"!a8\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uI]*\"aa'+\t\rM\u0014\u0011\u001d\u0015\u0004E\r}\u0005\u0003\u0002B\"\u0007CKAaa)\u0003F\tY\u0011J\u001c;fe:\fG.\u00119jQ\r\t3q\u0014")
public final class PromiseActorRef
extends InternalActorRef
implements MinimalActorRef {
    private final ActorRefProvider provider;
    private final Promise<Object> result;
    private final String refPathPrefix;
    private volatile String messageClassName;
    private volatile Object _stateDoNotCallMeDirectly;
    private volatile Set<ActorRef> _watchedByDoNotCallMeDirectly;

    public static Function1<String, Throwable> apply$default$7() {
        return PromiseActorRef$.MODULE$.apply$default$7();
    }

    public static ActorRef apply$default$6() {
        return PromiseActorRef$.MODULE$.apply$default$6();
    }

    public static PromiseActorRef apply(ActorRefProvider provider, Timeout timeout2, Object targetName, String messageClassName, String refPathPrefix, ActorRef sender, Function1<String, Throwable> onTimeout) {
        return PromiseActorRef$.MODULE$.apply(provider, timeout2, targetName, messageClassName, refPathPrefix, sender, onTimeout);
    }

    @Override
    public InternalActorRef getChild(Iterator<String> names) {
        return MinimalActorRef.getChild$(this, names);
    }

    @Override
    public void start() {
        MinimalActorRef.start$(this);
    }

    @Override
    public void suspend() {
        MinimalActorRef.suspend$(this);
    }

    @Override
    public void resume(Throwable causedByFailure) {
        MinimalActorRef.resume$(this, causedByFailure);
    }

    @Override
    public void restart(Throwable cause) {
        MinimalActorRef.restart$(this, cause);
    }

    @Override
    public Object writeReplace() throws ObjectStreamException {
        return MinimalActorRef.writeReplace$(this);
    }

    @Override
    public final boolean isLocal() {
        return LocalRef.isLocal$(this);
    }

    @Override
    public ActorRefProvider provider() {
        return this.provider;
    }

    public Promise<Object> result() {
        return this.result;
    }

    public String messageClassName() {
        return this.messageClassName;
    }

    public void messageClassName_$eq(String x$1) {
        this.messageClassName = x$1;
    }

    private Set<ActorRef> _preventPrivateUnusedErasure() {
        return this._watchedByDoNotCallMeDirectly;
    }

    private Set<ActorRef> watchedBy() {
        return (Set)Unsafe.instance.getObjectVolatile(this, AbstractPromiseActorRef.watchedByOffset);
    }

    private boolean updateWatchedBy(Set<ActorRef> oldWatchedBy, Set<ActorRef> newWatchedBy) {
        return Unsafe.instance.compareAndSwapObject(this, AbstractPromiseActorRef.watchedByOffset, oldWatchedBy, newWatchedBy);
    }

    private final boolean addWatcher(ActorRef watcher) {
        Set<ActorRef> set;
        do {
            if ((set = this.watchedBy()) != null) continue;
            return false;
        } while (!this.updateWatchedBy(set, (Set)set.$plus(watcher)));
        return true;
    }

    private final void remWatcher(ActorRef watcher) {
        Set<ActorRef> set;
        do {
            if ((set = this.watchedBy()) != null) continue;
            break;
        } while (!this.updateWatchedBy(set, (Set)set.$minus(watcher)));
    }

    private final Set<ActorRef> clearWatchers() {
        Set<ActorRef> set;
        do {
            if ((set = this.watchedBy()) != null) continue;
            return ActorCell$.MODULE$.emptyActorRefSet();
        } while (!this.updateWatchedBy(set, null));
        return set;
    }

    private Object state() {
        return Unsafe.instance.getObjectVolatile(this, AbstractPromiseActorRef.stateOffset);
    }

    private boolean updateState(Object oldState, Object newState) {
        return Unsafe.instance.compareAndSwapObject(this, AbstractPromiseActorRef.stateOffset, oldState, newState);
    }

    private void setState(Object newState) {
        Unsafe.instance.putObjectVolatile(this, AbstractPromiseActorRef.stateOffset, newState);
    }

    @Override
    public InternalActorRef getParent() {
        return this.provider().tempContainer();
    }

    @Override
    public ActorPath path() {
        Object object;
        while (true) {
            if ((object = this.state()) == null) {
                ActorPath actorPath;
                if (!this.updateState(null, PromiseActorRef$Registering$.MODULE$)) continue;
                ActorPath p = null;
                try {
                    p = this.provider().tempPath(this.refPathPrefix);
                    this.provider().registerTempActor(this, p);
                    actorPath = p;
                }
                catch (Throwable throwable) {
                    this.setState(p);
                    throw throwable;
                }
                this.setState(p);
                return actorPath;
            }
            if (object instanceof ActorPath) {
                ActorPath actorPath = (ActorPath)object;
                return actorPath;
            }
            if (object instanceof StoppedWithPath) {
                StoppedWithPath stoppedWithPath = (StoppedWithPath)object;
                ActorPath p = stoppedWithPath.path();
                return p;
            }
            if (PromiseActorRef$Stopped$.MODULE$.equals(object)) {
                this.updateState(PromiseActorRef$Stopped$.MODULE$, new StoppedWithPath(this.provider().tempPath()));
                continue;
            }
            if (!PromiseActorRef$Registering$.MODULE$.equals(object)) break;
        }
        throw new IllegalStateException(new StringBuilder(18).append("Unexpected state: ").append(object).toString());
    }

    @Override
    public void $bang(Object message, ActorRef sender) {
        boolean alreadyCompleted;
        Try try_;
        Object object = this.state();
        if (PromiseActorRef$Stopped$.MODULE$.equals(object) ? true : object instanceof StoppedWithPath) {
            this.provider().deadLetters().$bang(message, sender);
            this.onComplete(message, true);
            return;
        }
        if (message == null) {
            throw InvalidMessageException$.MODULE$.apply("Message is null");
        }
        Object object2 = message;
        if (object2 instanceof Status.Success) {
            Status.Success success = (Status.Success)object2;
            Object r = success.status();
            try_ = new Success<Object>(r);
        } else if (object2 instanceof Status.Failure) {
            Status.Failure failure = (Status.Failure)object2;
            Throwable f = failure.cause();
            try_ = new Failure(f);
        } else {
            try_ = new Success<Object>(object2);
        }
        Success<Object> promiseResult = try_;
        boolean bl = alreadyCompleted = !this.result().tryComplete(promiseResult);
        if (alreadyCompleted) {
            this.provider().deadLetters().$bang(message, sender);
        }
        this.onComplete(message, alreadyCompleted);
    }

    @Override
    public void sendSystemMessage(SystemMessage message) {
        SystemMessage systemMessage = message;
        if (systemMessage instanceof Terminate) {
            this.stop();
            return;
        }
        if (systemMessage instanceof DeathWatchNotification) {
            DeathWatchNotification deathWatchNotification = (DeathWatchNotification)systemMessage;
            ActorRef a = deathWatchNotification.actor();
            boolean ec = deathWatchNotification.existenceConfirmed();
            boolean at = deathWatchNotification.addressTerminated();
            Terminated x$1 = Terminated$.MODULE$.apply(a, ec, at);
            ActorRef x$2 = this.$bang$default$2(x$1);
            this.$bang(x$1, x$2);
            return;
        }
        if (systemMessage instanceof Watch) {
            Watch watch = (Watch)systemMessage;
            InternalActorRef watchee = watch.watchee();
            InternalActorRef watcher = watch.watcher();
            InternalActorRef internalActorRef = watchee;
            PromiseActorRef promiseActorRef = this;
            if (!(internalActorRef != null ? !((Object)internalActorRef).equals(promiseActorRef) : promiseActorRef != null)) {
                InternalActorRef internalActorRef2 = watcher;
                PromiseActorRef promiseActorRef2 = this;
                if (internalActorRef2 == null ? promiseActorRef2 != null : !((Object)internalActorRef2).equals(promiseActorRef2)) {
                    if (!this.addWatcher(watcher)) {
                        watcher.sendSystemMessage(new DeathWatchNotification(watchee, true, false));
                        return;
                    }
                    return;
                }
            }
            System.err.println(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("BUG: illegal Watch(%s,%s) for %s"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{watchee, watcher, this})));
            return;
        }
        if (systemMessage instanceof Unwatch) {
            Unwatch unwatch = (Unwatch)systemMessage;
            ActorRef watchee = unwatch.watchee();
            ActorRef watcher = unwatch.watcher();
            ActorRef actorRef2 = watchee;
            PromiseActorRef promiseActorRef = this;
            if (!(actorRef2 != null ? !((Object)actorRef2).equals(promiseActorRef) : promiseActorRef != null)) {
                ActorRef actorRef3 = watcher;
                PromiseActorRef promiseActorRef3 = this;
                if (actorRef3 == null ? promiseActorRef3 != null : !((Object)actorRef3).equals(promiseActorRef3)) {
                    this.remWatcher(watcher);
                    return;
                }
            }
            System.err.println(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("BUG: illegal Unwatch(%s,%s) for %s"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{watchee, watcher, this})));
            return;
        }
    }

    @Override
    public boolean isTerminated() {
        Object object = this.state();
        return PromiseActorRef$Stopped$.MODULE$.equals(object) ? true : object instanceof StoppedWithPath;
    }

    @Override
    public void stop() {
        block7: {
            Object object;
            while (true) {
                if ((object = this.state()) == null) {
                    if (!this.updateState(null, PromiseActorRef$Stopped$.MODULE$)) continue;
                    this.ensureCompleted$1();
                    break block7;
                }
                if (object instanceof ActorPath) {
                    ActorPath actorPath = (ActorPath)object;
                    if (!this.updateState(actorPath, new StoppedWithPath(actorPath))) continue;
                    try {
                        this.ensureCompleted$1();
                        break block7;
                    }
                    finally {
                        this.provider().unregisterTempActor(actorPath);
                    }
                }
                if (PromiseActorRef$Stopped$.MODULE$.equals(object) ? true : object instanceof StoppedWithPath) {
                    break block7;
                }
                if (!PromiseActorRef$Registering$.MODULE$.equals(object)) break;
            }
            throw new IllegalStateException(new StringBuilder(18).append("Unexpected state: ").append(object).toString());
        }
    }

    @InternalStableApi
    public Future<Object> ask(ActorSelection actorSel, Object message, Timeout timeout2) {
        actorSel.tell(message, this);
        return this.result().future();
    }

    @InternalStableApi
    public Future<Object> ask(ActorRef actorRef2, Object message, Timeout timeout2) {
        actorRef2.tell(message, this);
        return this.result().future();
    }

    @InternalStableApi
    public void onComplete(Object message, boolean alreadyCompleted) {
    }

    @InternalStableApi
    public void onTimeout(Timeout timeout2) {
    }

    @Override
    public ActorRef $bang$default$2(Object message) {
        return Actor$.MODULE$.noSender();
    }

    public static final /* synthetic */ void $anonfun$stop$1(PromiseActorRef $this, ActorRef watcher) {
        ((InternalActorRef)watcher).sendSystemMessage(new DeathWatchNotification($this, true, false));
    }

    private final void ensureCompleted$1() {
        this.result().tryComplete(PromiseActorRef$.MODULE$.akka$pattern$PromiseActorRef$$ActorStopResult());
        Set<ActorRef> watchers = this.clearWatchers();
        if (watchers.nonEmpty()) {
            watchers.foreach((Function1<ActorRef, Object> & Serializable)watcher -> {
                PromiseActorRef.$anonfun$stop$1(this, watcher);
                return BoxedUnit.UNIT;
            });
            return;
        }
    }

    public PromiseActorRef(ActorRefProvider provider, Promise<Object> result, String _mcn, String refPathPrefix) {
        this.provider = provider;
        this.result = result;
        this.refPathPrefix = refPathPrefix;
        LocalRef.$init$(this);
        MinimalActorRef.$init$(this);
        this.messageClassName = _mcn;
        this._watchedByDoNotCallMeDirectly = ActorCell$.MODULE$.emptyActorRefSet();
    }

    private static final class StoppedWithPath
    implements Product,
    Serializable {
        private final ActorPath path;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ActorPath path() {
            return this.path;
        }

        public StoppedWithPath copy(ActorPath path) {
            return new StoppedWithPath(path);
        }

        public ActorPath copy$default$1() {
            return this.path();
        }

        @Override
        public String productPrefix() {
            return "StoppedWithPath";
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
                    return this.path();
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
            return x$1 instanceof StoppedWithPath;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "path";
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
            if (!(object instanceof StoppedWithPath)) return false;
            boolean bl = true;
            if (!bl) return false;
            StoppedWithPath stoppedWithPath = (StoppedWithPath)x$1;
            ActorPath actorPath = this.path();
            ActorPath actorPath2 = stoppedWithPath.path();
            if (actorPath != null) {
                if (!actorPath.equals(actorPath2)) return false;
                return true;
            }
            if (actorPath2 == null) return true;
            return false;
        }

        public StoppedWithPath(ActorPath path) {
            this.path = path;
            Product.$init$(this);
        }
    }
}

