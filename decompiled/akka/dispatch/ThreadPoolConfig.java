/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.ExecutorServiceFactory;
import akka.dispatch.ExecutorServiceFactoryProvider;
import akka.dispatch.LoadMetrics;
import akka.dispatch.MonitorableThreadFactory;
import akka.dispatch.ThreadPoolConfig$;
import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import scala.Function0;
import scala.Option;
import scala.Product;
import scala.Tuple6;
import scala.collection.Iterator;
import scala.concurrent.duration.Duration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t\rw!\u0002%J\u0011\u0003qe!\u0002)J\u0011\u0003\t\u0006\"\u00021\u0002\t\u0003\tW\u0001\u00022\u0002\u0001\rDq\u0001^\u0001C\u0002\u0013\u0005Q\u000f\u0003\u0004z\u0003\u0001\u0006IA\u001e\u0005\bu\u0006\u0011\r\u0011\"\u0001|\u0011\u0019y\u0018\u0001)A\u0005y\"A\u0011\u0011A\u0001C\u0002\u0013\u00051\u0010C\u0004\u0002\u0004\u0005\u0001\u000b\u0011\u0002?\t\u0013\u0005\u0015\u0011A1A\u0005\u0002\u0005\u001d\u0001\u0002CA\f\u0003\u0001\u0006I!!\u0003\t\u0013\u0005e\u0011A1A\u0005\u0002\u0005m\u0001\u0002CA\u0012\u0003\u0001\u0006I!!\b\t\u000f\u0005\u0015\u0012\u0001\"\u0001\u0002(!9\u00111H\u0001\u0005\u0002\u0005u\u0002bBA&\u0003\u0011\u0005\u0011Q\n\u0005\b\u0003#\nA\u0011AA*\u0011\u001d\t\t&\u0001C\u0001\u0003+Bq!!\u0017\u0002\t\u0003\tY\u0006C\u0004\u0002Z\u0005!\t!!\u0019\t\u0013\u0005\u001d\u0014!!A\u0005\u0002\u0006%\u0004\"\u0003BG\u0003E\u0005I\u0011\u0001B\b\u0011%\u0011y)AI\u0001\n\u0003\u00119\u0003C\u0005\u0003\u0012\u0006\t\n\u0011\"\u0001\u0003(!I!1S\u0001\u0012\u0002\u0013\u0005!q\u0006\u0005\n\u0005+\u000b\u0011\u0013!C\u0001\u0005kA\u0011Ba&\u0002#\u0003%\tAa\u000f\t\u0013\te\u0015!!A\u0005\u0002\nm\u0005\"\u0003BW\u0003E\u0005I\u0011\u0001B\b\u0011%\u0011y+AI\u0001\n\u0003\u00119\u0003C\u0005\u00032\u0006\t\n\u0011\"\u0001\u0003(!I!1W\u0001\u0012\u0002\u0013\u0005!q\u0006\u0005\n\u0005k\u000b\u0011\u0013!C\u0001\u0005kA\u0011Ba.\u0002#\u0003%\tAa\u000f\t\u0013\te\u0016!!A\u0005\n\tmf!\u0002)J\u0005\u00065\u0004\"CAII\tU\r\u0011\"\u0001v\u0011%\t\u0019\n\nB\tB\u0003%a\u000fC\u0005\u0002\u0016\u0012\u0012)\u001a!C\u0001w\"I\u0011q\u0013\u0013\u0003\u0012\u0003\u0006I\u0001 \u0005\n\u00033##Q3A\u0005\u0002mD\u0011\"a'%\u0005#\u0005\u000b\u0011\u0002?\t\u0015\u0005uEE!f\u0001\n\u0003\t9\u0001\u0003\u0006\u0002 \u0012\u0012\t\u0012)A\u0005\u0003\u0013A!\"!\u001a%\u0005+\u0007I\u0011AAQ\u0011)\t9\u000b\nB\tB\u0003%\u00111\u0015\u0005\u000b\u0003S##Q3A\u0005\u0002\u0005m\u0001BCAVI\tE\t\u0015!\u0003\u0002\u001e!1\u0001\r\nC\u0001\u0003[3a!a/%\u0001\u0005u\u0006BCAce\t\u0015\r\u0011\"\u0001\u0002H\"Q\u0011q\u001a\u001a\u0003\u0002\u0003\u0006I!!3\t\r\u0001\u0014D\u0011AAi\u0011\u001d\tIN\rC\u0001\u00037Dq!a9%\t\u0003\t)\u000fC\u0005\u0002~\u0012\n\t\u0011\"\u0001\u0002\u0000\"I!Q\u0002\u0013\u0012\u0002\u0013\u0005!q\u0002\u0005\n\u0005K!\u0013\u0013!C\u0001\u0005OA\u0011Ba\u000b%#\u0003%\tAa\n\t\u0013\t5B%%A\u0005\u0002\t=\u0002\"\u0003B\u001aIE\u0005I\u0011\u0001B\u001b\u0011%\u0011I\u0004JI\u0001\n\u0003\u0011Y\u0004C\u0005\u0003@\u0011\n\t\u0011\"\u0011\u0003B!A!q\t\u0013\u0002\u0002\u0013\u00051\u0010C\u0005\u0003J\u0011\n\t\u0011\"\u0001\u0003L!I!q\u000b\u0013\u0002\u0002\u0013\u0005#\u0011\f\u0005\n\u0005O\"\u0013\u0011!C\u0001\u0005SB\u0011B!\u001c%\u0003\u0003%\tEa\u001c\t\u0013\tMD%!A\u0005B\tU\u0004\"\u0003B<I\u0005\u0005I\u0011\tB=\u0011%\u0011Y\bJA\u0001\n\u0003\u0012i(\u0001\tUQJ,\u0017\r\u001a)p_2\u001cuN\u001c4jO*\u0011!jS\u0001\tI&\u001c\b/\u0019;dQ*\tA*\u0001\u0003bW.\f7\u0001\u0001\t\u0003\u001f\u0006i\u0011!\u0013\u0002\u0011)\"\u0014X-\u00193Q_>d7i\u001c8gS\u001e\u001c2!\u0001*Y!\t\u0019f+D\u0001U\u0015\u0005)\u0016!B:dC2\f\u0017BA,U\u0005\u0019\te.\u001f*fMB\u0011\u0011LX\u0007\u00025*\u00111\fX\u0001\u0003S>T\u0011!X\u0001\u0005U\u00064\u0018-\u0003\u0002`5\na1+\u001a:jC2L'0\u00192mK\u00061A(\u001b8jiz\"\u0012A\u0014\u0002\r#V,W/\u001a$bGR|'/\u001f\t\u0004'\u00124\u0017BA3U\u0005%1UO\\2uS>t\u0007\u0007E\u0002hY:l\u0011\u0001\u001b\u0006\u0003S*\f!bY8oGV\u0014(/\u001a8u\u0015\tYG,\u0001\u0003vi&d\u0017BA7i\u00055\u0011En\\2lS:<\u0017+^3vKB\u0011qN]\u0007\u0002a*\u0011\u0011\u000fX\u0001\u0005Y\u0006tw-\u0003\u0002ta\nA!+\u001e8oC\ndW-A\u000feK\u001a\fW\u000f\u001c;BY2|woQ8sKRC'/Z1e)&lWm\\;u+\u00051\bCA*x\u0013\tAHKA\u0004C_>dW-\u00198\u0002=\u0011,g-Y;mi\u0006cGn\\<D_J,G\u000b\u001b:fC\u0012$\u0016.\\3pkR\u0004\u0013a\u00053fM\u0006,H\u000e^\"pe\u0016\u0004vn\u001c7TSj,W#\u0001?\u0011\u0005Mk\u0018B\u0001@U\u0005\rIe\u000e^\u0001\u0015I\u00164\u0017-\u001e7u\u0007>\u0014X\rU8pYNK'0\u001a\u0011\u0002%\u0011,g-Y;mi6\u000b\u0007\u0010U8pYNK'0Z\u0001\u0014I\u00164\u0017-\u001e7u\u001b\u0006D\bk\\8m'&TX\rI\u0001\u000fI\u00164\u0017-\u001e7u)&lWm\\;u+\t\tI\u0001\u0005\u0003\u0002\f\u0005MQBAA\u0007\u0015\u0011\ty!!\u0005\u0002\u0011\u0011,(/\u0019;j_:T!!\u001b+\n\t\u0005U\u0011Q\u0002\u0002\t\tV\u0014\u0018\r^5p]\u0006yA-\u001a4bk2$H+[7f_V$\b%\u0001\feK\u001a\fW\u000f\u001c;SK*,7\r^5p]B{G.[2z+\t\ti\u0002E\u0002h\u0003?I1!!\ti\u0005a\u0011VM[3di\u0016$W\t_3dkRLwN\u001c%b]\u0012dWM]\u0001\u0018I\u00164\u0017-\u001e7u%\u0016TWm\u0019;j_:\u0004v\u000e\\5ds\u0002\nab]2bY\u0016$\u0007k\\8m'&TX\rF\u0004}\u0003S\ti#a\u000e\t\r\u0005-b\u00021\u0001}\u0003\u00151Gn\\8s\u0011\u001d\tyC\u0004a\u0001\u0003c\t!\"\\;mi&\u0004H.[3s!\r\u0019\u00161G\u0005\u0004\u0003k!&A\u0002#pk\ndW\r\u0003\u0004\u0002:9\u0001\r\u0001`\u0001\bG\u0016LG.\u001b8h\u0003I\t'O]1z\u00052|7m[5oOF+X-^3\u0015\r\u0005}\u00121IA$!\r\t\teA\u0007\u0002\u0003!1\u0011QI\bA\u0002q\f\u0001bY1qC\u000eLG/\u001f\u0005\u0007\u0003\u0013z\u0001\u0019\u0001<\u0002\t\u0019\f\u0017N]\u0001\u0011gft7\r\u001b:p]>,8/U;fk\u0016$B!a\u0010\u0002P!1\u0011\u0011\n\tA\u0002Y\f1\u0003\\5oW\u0016$'\t\\8dW&tw-U;fk\u0016$\"!a\u0010\u0015\t\u0005}\u0012q\u000b\u0005\u0007\u0003\u000b\u0012\u0002\u0019\u0001?\u0002\u001bI,Wo]1cY\u0016\fV/Z;f)\u0011\ty$!\u0018\t\r\u0005}3\u00031\u0001g\u0003\u0015\tX/Z;f)\u0011\ty$a\u0019\t\u000f\u0005\u0015D\u00031\u0001\u0002@\u0005a\u0011/^3vK\u001a\u000b7\r^8ss\u0006)\u0011\r\u001d9msRq\u00111\u000eBA\u0005\u0007\u0013)Ia\"\u0003\n\n-\u0005CA(%'!!#+a\u001c\u0002v\u0005m\u0004cA(\u0002r%\u0019\u00111O%\u0003=\u0015CXmY;u_J\u001cVM\u001d<jG\u00164\u0015m\u0019;pef\u0004&o\u001c<jI\u0016\u0014\bcA*\u0002x%\u0019\u0011\u0011\u0010+\u0003\u000fA\u0013x\u000eZ;diB!\u0011QPAG\u001d\u0011\ty(!#\u000f\t\u0005\u0005\u0015qQ\u0007\u0003\u0003\u0007S1!!\"N\u0003\u0019a$o\\8u}%\tQ+C\u0002\u0002\fR\u000bq\u0001]1dW\u0006<W-C\u0002`\u0003\u001fS1!a#U\u0003Q\tG\u000e\\8x\u0007>\u0014X\rU8pYRKW.Z8vi\u0006)\u0012\r\u001c7po\u000e{'/\u001a)p_2$\u0016.\\3pkR\u0004\u0013\u0001D2pe\u0016\u0004vn\u001c7TSj,\u0017!D2pe\u0016\u0004vn\u001c7TSj,\u0007%A\u0006nCb\u0004vn\u001c7TSj,\u0017\u0001D7bqB{w\u000e\\*ju\u0016\u0004\u0013!\u0004;ie\u0016\fG\rV5nK>,H/\u0001\buQJ,\u0017\r\u001a+j[\u0016|W\u000f\u001e\u0011\u0016\u0005\u0005\r\u0006cAAS\u00079\u0011q\nA\u0001\u000ecV,W/\u001a$bGR|'/\u001f\u0011\u0002\u001fI,'.Z2uS>t\u0007k\u001c7jGf\f\u0001C]3kK\u000e$\u0018n\u001c8Q_2L7-\u001f\u0011\u0015\u001d\u0005-\u0014qVAY\u0003g\u000b),a.\u0002:\"A\u0011\u0011S\u0019\u0011\u0002\u0003\u0007a\u000f\u0003\u0005\u0002\u0016F\u0002\n\u00111\u0001}\u0011!\tI*\rI\u0001\u0002\u0004a\b\"CAOcA\u0005\t\u0019AA\u0005\u0011%\t)'\rI\u0001\u0002\u0004\t\u0019\u000bC\u0005\u0002*F\u0002\n\u00111\u0001\u0002\u001e\t\u0001C\u000b\u001b:fC\u0012\u0004vn\u001c7Fq\u0016\u001cW\u000f^8s'\u0016\u0014h/[2f\r\u0006\u001cGo\u001c:z'\u0011\u0011$+a0\u0011\u0007=\u000b\t-C\u0002\u0002D&\u0013a#\u0012=fGV$xN]*feZL7-\u001a$bGR|'/_\u0001\u000ei\"\u0014X-\u00193GC\u000e$xN]=\u0016\u0005\u0005%\u0007cA4\u0002L&\u0019\u0011Q\u001a5\u0003\u001bQC'/Z1e\r\u0006\u001cGo\u001c:z\u00039!\bN]3bI\u001a\u000b7\r^8ss\u0002\"B!a5\u0002XB\u0019\u0011Q\u001b\u001a\u000e\u0003\u0011Bq!!26\u0001\u0004\tI-A\u000bde\u0016\fG/Z#yK\u000e,Ho\u001c:TKJ4\u0018nY3\u0016\u0005\u0005u\u0007cA4\u0002`&\u0019\u0011\u0011\u001d5\u0003\u001f\u0015CXmY;u_J\u001cVM\u001d<jG\u0016\fAd\u0019:fCR,W\t_3dkR|'oU3sm&\u001cWMR1di>\u0014\u0018\u0010\u0006\u0004\u0002@\u0006\u001d\u00181 \u0005\b\u0003S<\u0004\u0019AAv\u0003\tIG\r\u0005\u0003\u0002n\u0006Uh\u0002BAx\u0003c\u00042!!!U\u0013\r\t\u0019\u0010V\u0001\u0007!J,G-\u001a4\n\t\u0005]\u0018\u0011 \u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005MH\u000bC\u0004\u0002F^\u0002\r!!3\u0002\t\r|\u0007/\u001f\u000b\u000f\u0003W\u0012\tAa\u0001\u0003\u0006\t\u001d!\u0011\u0002B\u0006\u0011!\t\t\n\u000fI\u0001\u0002\u00041\b\u0002CAKqA\u0005\t\u0019\u0001?\t\u0011\u0005e\u0005\b%AA\u0002qD\u0011\"!(9!\u0003\u0005\r!!\u0003\t\u0013\u0005\u0015\u0004\b%AA\u0002\u0005\r\u0006\"CAUqA\u0005\t\u0019AA\u000f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\"A!\u0005+\u0007Y\u0014\u0019b\u000b\u0002\u0003\u0016A!!q\u0003B\u0011\u001b\t\u0011IB\u0003\u0003\u0003\u001c\tu\u0011!C;oG\",7m[3e\u0015\r\u0011y\u0002V\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002\u0002B\u0012\u00053\u0011\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\"A!\u000b+\u0007q\u0014\u0019\"\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iU\u0011!\u0011\u0007\u0016\u0005\u0003\u0013\u0011\u0019\"\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001b\u0016\u0005\t]\"\u0006BAR\u0005'\tabY8qs\u0012\"WMZ1vYR$c'\u0006\u0002\u0003>)\"\u0011Q\u0004B\n\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011!1\t\t\u0004_\n\u0015\u0013bAA|a\u0006a\u0001O]8ek\u000e$\u0018I]5us\u0006q\u0001O]8ek\u000e$X\t\\3nK:$H\u0003\u0002B'\u0005'\u00022a\u0015B(\u0013\r\u0011\t\u0006\u0016\u0002\u0004\u0003:L\b\u0002\u0003B+\u0003\u0006\u0005\t\u0019\u0001?\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\u0011Y\u0006\u0005\u0004\u0003^\t\r$QJ\u0007\u0003\u0005?R1A!\u0019U\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0005K\u0012yF\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dGc\u0001<\u0003l!I!QK\"\u0002\u0002\u0003\u0007!QJ\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0003D\tE\u0004\u0002\u0003B+\t\u0006\u0005\t\u0019\u0001?\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012\u0001`\u0001\ti>\u001cFO]5oOR\u0011!1I\u0001\u0007KF,\u0018\r\\:\u0015\u0007Y\u0014y\bC\u0005\u0003V\u001d\u000b\t\u00111\u0001\u0003N!A\u0011\u0011S\u000b\u0011\u0002\u0003\u0007a\u000f\u0003\u0005\u0002\u0016V\u0001\n\u00111\u0001}\u0011!\tI*\u0006I\u0001\u0002\u0004a\b\"CAO+A\u0005\t\u0019AA\u0005\u0011%\t)'\u0006I\u0001\u0002\u0004\t\u0019\u000bC\u0005\u0002*V\u0001\n\u00111\u0001\u0002\u001e\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$\u0013'A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00133\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\u001a\u0014aD1qa2LH\u0005Z3gCVdG\u000f\n\u001b\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIU\nq\"\u00199qYf$C-\u001a4bk2$HEN\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\u0011iJ!+\u0011\u000bM\u0013yJa)\n\u0007\t\u0005FK\u0001\u0004PaRLwN\u001c\t\r'\n\u0015f\u000f ?\u0002\n\u0005\r\u0016QD\u0005\u0004\u0005O#&A\u0002+va2,g\u0007C\u0005\u0003,r\t\t\u00111\u0001\u0002l\u0005\u0019\u0001\u0010\n\u0019\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00132\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%e\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIM\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\"\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$S'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HEN\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0005{\u00032a\u001cB`\u0013\r\u0011\t\r\u001d\u0002\u0007\u001f\nTWm\u0019;")
public final class ThreadPoolConfig
implements ExecutorServiceFactoryProvider,
Product,
Serializable {
    private final boolean allowCorePoolTimeout;
    private final int corePoolSize;
    private final int maxPoolSize;
    private final Duration threadTimeout;
    private final Function0<BlockingQueue<Runnable>> queueFactory;
    private final RejectedExecutionHandler rejectionPolicy;

    public static RejectedExecutionHandler $lessinit$greater$default$6() {
        return ThreadPoolConfig$.MODULE$.$lessinit$greater$default$6();
    }

    public static Function0<BlockingQueue<Runnable>> $lessinit$greater$default$5() {
        return ThreadPoolConfig$.MODULE$.$lessinit$greater$default$5();
    }

    public static Duration $lessinit$greater$default$4() {
        return ThreadPoolConfig$.MODULE$.$lessinit$greater$default$4();
    }

    public static int $lessinit$greater$default$3() {
        return ThreadPoolConfig$.MODULE$.$lessinit$greater$default$3();
    }

    public static int $lessinit$greater$default$2() {
        return ThreadPoolConfig$.MODULE$.$lessinit$greater$default$2();
    }

    public static boolean $lessinit$greater$default$1() {
        return ThreadPoolConfig$.MODULE$.$lessinit$greater$default$1();
    }

    public static Option<Tuple6<Object, Object, Object, Duration, Function0<BlockingQueue<Runnable>>, RejectedExecutionHandler>> unapply(ThreadPoolConfig x$0) {
        return ThreadPoolConfig$.MODULE$.unapply(x$0);
    }

    public static RejectedExecutionHandler apply$default$6() {
        return ThreadPoolConfig$.MODULE$.apply$default$6();
    }

    public static Function0<BlockingQueue<Runnable>> apply$default$5() {
        return ThreadPoolConfig$.MODULE$.apply$default$5();
    }

    public static Duration apply$default$4() {
        return ThreadPoolConfig$.MODULE$.apply$default$4();
    }

    public static int apply$default$3() {
        return ThreadPoolConfig$.MODULE$.apply$default$3();
    }

    public static int apply$default$2() {
        return ThreadPoolConfig$.MODULE$.apply$default$2();
    }

    public static boolean apply$default$1() {
        return ThreadPoolConfig$.MODULE$.apply$default$1();
    }

    public static ThreadPoolConfig apply(boolean allowCorePoolTimeout, int corePoolSize, int maxPoolSize, Duration threadTimeout, Function0<BlockingQueue<Runnable>> queueFactory, RejectedExecutionHandler rejectionPolicy) {
        return ThreadPoolConfig$.MODULE$.apply(allowCorePoolTimeout, corePoolSize, maxPoolSize, threadTimeout, queueFactory, rejectionPolicy);
    }

    public static Function0<BlockingQueue<Runnable>> reusableQueue(Function0<BlockingQueue<Runnable>> queueFactory) {
        return ThreadPoolConfig$.MODULE$.reusableQueue(queueFactory);
    }

    public static Function0<BlockingQueue<Runnable>> reusableQueue(BlockingQueue<Runnable> queue) {
        return ThreadPoolConfig$.MODULE$.reusableQueue(queue);
    }

    public static Function0<BlockingQueue<Runnable>> linkedBlockingQueue(int capacity) {
        return ThreadPoolConfig$.MODULE$.linkedBlockingQueue(capacity);
    }

    public static Function0<BlockingQueue<Runnable>> linkedBlockingQueue() {
        return ThreadPoolConfig$.MODULE$.linkedBlockingQueue();
    }

    public static Function0<BlockingQueue<Runnable>> synchronousQueue(boolean fair) {
        return ThreadPoolConfig$.MODULE$.synchronousQueue(fair);
    }

    public static Function0<BlockingQueue<Runnable>> arrayBlockingQueue(int capacity, boolean fair) {
        return ThreadPoolConfig$.MODULE$.arrayBlockingQueue(capacity, fair);
    }

    public static int scaledPoolSize(int floor, double multiplier, int ceiling) {
        return ThreadPoolConfig$.MODULE$.scaledPoolSize(floor, multiplier, ceiling);
    }

    public static RejectedExecutionHandler defaultRejectionPolicy() {
        return ThreadPoolConfig$.MODULE$.defaultRejectionPolicy();
    }

    public static Duration defaultTimeout() {
        return ThreadPoolConfig$.MODULE$.defaultTimeout();
    }

    public static int defaultMaxPoolSize() {
        return ThreadPoolConfig$.MODULE$.defaultMaxPoolSize();
    }

    public static int defaultCorePoolSize() {
        return ThreadPoolConfig$.MODULE$.defaultCorePoolSize();
    }

    public static boolean defaultAllowCoreThreadTimeout() {
        return ThreadPoolConfig$.MODULE$.defaultAllowCoreThreadTimeout();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public boolean allowCorePoolTimeout() {
        return this.allowCorePoolTimeout;
    }

    public int corePoolSize() {
        return this.corePoolSize;
    }

    public int maxPoolSize() {
        return this.maxPoolSize;
    }

    public Duration threadTimeout() {
        return this.threadTimeout;
    }

    public Function0<BlockingQueue<Runnable>> queueFactory() {
        return this.queueFactory;
    }

    public RejectedExecutionHandler rejectionPolicy() {
        return this.rejectionPolicy;
    }

    @Override
    public ExecutorServiceFactory createExecutorServiceFactory(String id, ThreadFactory threadFactory) {
        ThreadFactory threadFactory2;
        ThreadFactory threadFactory3 = threadFactory;
        if (threadFactory3 instanceof MonitorableThreadFactory) {
            MonitorableThreadFactory monitorableThreadFactory = (MonitorableThreadFactory)threadFactory3;
            threadFactory2 = monitorableThreadFactory.withName(new StringBuilder(1).append(monitorableThreadFactory.name()).append("-").append(id).toString());
        } else {
            threadFactory2 = threadFactory3;
        }
        ThreadFactory tf = threadFactory2;
        return new ThreadPoolExecutorServiceFactory(this, tf);
    }

    public ThreadPoolConfig copy(boolean allowCorePoolTimeout, int corePoolSize, int maxPoolSize, Duration threadTimeout, Function0<BlockingQueue<Runnable>> queueFactory, RejectedExecutionHandler rejectionPolicy) {
        return new ThreadPoolConfig(allowCorePoolTimeout, corePoolSize, maxPoolSize, threadTimeout, queueFactory, rejectionPolicy);
    }

    public boolean copy$default$1() {
        return this.allowCorePoolTimeout();
    }

    public int copy$default$2() {
        return this.corePoolSize();
    }

    public int copy$default$3() {
        return this.maxPoolSize();
    }

    public Duration copy$default$4() {
        return this.threadTimeout();
    }

    public Function0<BlockingQueue<Runnable>> copy$default$5() {
        return this.queueFactory();
    }

    public RejectedExecutionHandler copy$default$6() {
        return this.rejectionPolicy();
    }

    @Override
    public String productPrefix() {
        return "ThreadPoolConfig";
    }

    @Override
    public int productArity() {
        return 6;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return BoxesRunTime.boxToBoolean(this.allowCorePoolTimeout());
            }
            case 1: {
                return BoxesRunTime.boxToInteger(this.corePoolSize());
            }
            case 2: {
                return BoxesRunTime.boxToInteger(this.maxPoolSize());
            }
            case 3: {
                return this.threadTimeout();
            }
            case 4: {
                return this.queueFactory();
            }
            case 5: {
                return this.rejectionPolicy();
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
        return x$1 instanceof ThreadPoolConfig;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "allowCorePoolTimeout";
            }
            case 1: {
                return "corePoolSize";
            }
            case 2: {
                return "maxPoolSize";
            }
            case 3: {
                return "threadTimeout";
            }
            case 4: {
                return "queueFactory";
            }
            case 5: {
                return "rejectionPolicy";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.allowCorePoolTimeout() ? 1231 : 1237);
        n = Statics.mix(n, this.corePoolSize());
        n = Statics.mix(n, this.maxPoolSize());
        n = Statics.mix(n, Statics.anyHash(this.threadTimeout()));
        n = Statics.mix(n, Statics.anyHash(this.queueFactory()));
        n = Statics.mix(n, Statics.anyHash(this.rejectionPolicy()));
        return Statics.finalizeHash(n, 6);
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
        if (!(object instanceof ThreadPoolConfig)) return false;
        boolean bl = true;
        if (!bl) return false;
        ThreadPoolConfig threadPoolConfig = (ThreadPoolConfig)x$1;
        if (this.allowCorePoolTimeout() != threadPoolConfig.allowCorePoolTimeout()) return false;
        if (this.corePoolSize() != threadPoolConfig.corePoolSize()) return false;
        if (this.maxPoolSize() != threadPoolConfig.maxPoolSize()) return false;
        Duration duration = this.threadTimeout();
        Duration duration2 = threadPoolConfig.threadTimeout();
        if (duration == null) {
            if (duration2 != null) {
                return false;
            }
        } else if (!duration.equals(duration2)) return false;
        Function0<BlockingQueue<Runnable>> function0 = this.queueFactory();
        Function0<BlockingQueue<Runnable>> function02 = threadPoolConfig.queueFactory();
        if (function0 == null) {
            if (function02 != null) {
                return false;
            }
        } else if (!function0.equals(function02)) return false;
        RejectedExecutionHandler rejectedExecutionHandler = this.rejectionPolicy();
        RejectedExecutionHandler rejectedExecutionHandler2 = threadPoolConfig.rejectionPolicy();
        if (rejectedExecutionHandler == null) {
            if (rejectedExecutionHandler2 == null) return true;
            return false;
        } else {
            if (!rejectedExecutionHandler.equals(rejectedExecutionHandler2)) return false;
            return true;
        }
    }

    public ThreadPoolConfig(boolean allowCorePoolTimeout, int corePoolSize, int maxPoolSize, Duration threadTimeout, Function0<BlockingQueue<Runnable>> queueFactory, RejectedExecutionHandler rejectionPolicy) {
        this.allowCorePoolTimeout = allowCorePoolTimeout;
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.threadTimeout = threadTimeout;
        this.queueFactory = queueFactory;
        this.rejectionPolicy = rejectionPolicy;
        Product.$init$(this);
    }

    public class ThreadPoolExecutorServiceFactory
    implements ExecutorServiceFactory {
        private final ThreadFactory threadFactory;
        public final /* synthetic */ ThreadPoolConfig $outer;

        public ThreadFactory threadFactory() {
            return this.threadFactory;
        }

        @Override
        public ExecutorService createExecutorService() {
            LoadMetrics service2 = new LoadMetrics(this){

                public boolean atFullThrottle() {
                    return this.getActiveCount() >= this.getPoolSize();
                }
            };
            ((ThreadPoolExecutor)((Object)service2)).allowCoreThreadTimeOut(this.akka$dispatch$ThreadPoolConfig$ThreadPoolExecutorServiceFactory$$$outer().allowCorePoolTimeout());
            return service2;
        }

        public /* synthetic */ ThreadPoolConfig akka$dispatch$ThreadPoolConfig$ThreadPoolExecutorServiceFactory$$$outer() {
            return this.$outer;
        }

        public ThreadPoolExecutorServiceFactory(ThreadPoolConfig $outer, ThreadFactory threadFactory) {
            this.threadFactory = threadFactory;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }
}

