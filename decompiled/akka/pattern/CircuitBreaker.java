/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.Cancellable;
import akka.actor.ExtendedActorSystem;
import akka.actor.Scheduler;
import akka.dispatch.ExecutionContexts$;
import akka.pattern.AbstractCircuitBreaker;
import akka.pattern.CircuitBreaker$;
import akka.pattern.CircuitBreaker$Closed$;
import akka.pattern.CircuitBreaker$HalfOpen$;
import akka.pattern.CircuitBreaker$Open$;
import akka.pattern.internal.CircuitBreakerNoopTelemetry$;
import akka.pattern.internal.CircuitBreakerTelemetry;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import akka.util.Unsafe;
import java.io.Serializable;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeoutException;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.Option;
import scala.Predef$;
import scala.collection.immutable.Set;
import scala.compat.java8.FutureConverters$;
import scala.concurrent.Await$;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.Future$;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.duration.package;
import scala.concurrent.duration.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.NoStackTrace;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u00155vaB@\u0002\u0002!\u0005\u00111\u0002\u0004\t\u0003\u001f\t\t\u0001#\u0001\u0002\u0012!9\u0011qD\u0001\u0005\u0002\u0005\u0005\u0002bBA\u0012\u0003\u0011\u0005\u0011Q\u0005\u0005\b\u0003G\tA\u0011AC9\u0011\u001d)\u0019)\u0001C\u0001\u000b\u000bCq!b$\u0002\t\u0003)\t\nC\u0004\u0006\u0018\u0006!\t\"\"'\u0007\u000f\u0005=\u0011\u0011\u0001\u0001\u0002*!Q\u0011\u0011\u0007\u0005\u0003\u0002\u0003\u0006I!a\r\t\u0015\u0005}\u0002B!A!\u0002\u0013\t\t\u0005\u0003\u0006\u0002H!\u0011\t\u0011)A\u0005\u0003\u0013B!\"!\u0017\t\u0005\u000b\u0007I\u0011AA.\u0011)\ti\u0006\u0003B\u0001B\u0003%\u0011\u0011\n\u0005\u000b\u0003?B!\u0011!Q\u0001\n\u0005%\u0003BCA1\u0011\t\u0005\t\u0015!\u0003\u0002d!Q\u0011\u0011\u000e\u0005\u0003\u0002\u0003\u0006I!a\u0019\t\u0015\u0005-\u0004B!b\u0001\n\u0003\ti\u0007\u0003\u0006\u0002\f\"\u0011\t\u0011)A\u0005\u0003_B!\"!$\t\u0005\u000b\u0007I\u0011AAH\u0011)\ti\n\u0003B\u0001B\u0003%\u0011\u0011\u0013\u0005\u000b\u0003?C!\u0011!Q\u0001\f\u0005\u0005\u0006bBA\u0010\u0011\u0011\u0005\u0011\u0011\u0016\u0005\b\u0003?AA\u0011AAa\u0011\u001d\ty\u0002\u0003C\u0001\u0003+Dq!a\b\t\t\u0003\t\t\u0010C\u0004\u0002 !!\t!a@\t\u000f\tE\u0001\u0002\"\u0001\u0003\u0014!9!\u0011\u0003\u0005\u0005\u0002\t]\u0001b\u0002B\u000e\u0011\u0011\u0005!Q\u0004\u0005\t\u0005CA\u0001\u0015)\u0003\u0003$!A1q\f\u0005!B\u0013\tI\u0005C\u0004\u0004d!!I!a\u0017\t\u0011\r\u0015\u0004\u0002)C\u0005\u0007OB\u0001b!\u001f\tA\u0013%11\u0010\u0005\t\u0007\u007fB\u0001\u0015\"\u0003\u0004\u0002\"A1Q\u0012\u0005!\n\u0013\tY\u0006C\u0004\u0004\u0012\"!\taa%\t\u000f\rE\u0005\u0002\"\u0001\u0004(\"91Q\u0017\u0005\u0005\u0002\r]\u0006bBB[\u0011\u0011\u00051\u0011\u001a\u0005\b\t\u000bAA\u0011\u0001C\u0004\u0011\u001d!)\u0001\u0003C\u0001\t3Aq\u0001\"\f\t\t\u0003!y\u0003C\u0004\u0005.!!\t\u0001b\u000f\t\u000f\u00115\u0003\u0002\"\u0001\u0005P!9AQ\n\u0005\u0005\u0002\u0011m\u0003b\u0002C7\u0011\u0011\u0005!Q\u0006\u0005\b\t_BA\u0011\u0001B\u0017\u0011\u001d!\t\b\u0003C\u0001\u0005;Bq\u0001b\u001d\t\t\u0003\u0011i\u0006C\u0004\u0005v!!\tA!\u0018\t\u000f\u0011]\u0004\u0002\"\u0001\u0005z!9A\u0011\u0011\u0005\u0005\u0002\u0011\r\u0005b\u0002CD\u0011\u0011\u0005A\u0011\u0012\u0005\b\t\u001bCA\u0011\u0001CH\u0011\u001d!\u0019\n\u0003C\u0001\t+Cq\u0001\"'\t\t\u0003!Y\nC\u0004\u0005 \"!\t\u0001\")\t\u000f\u00115\u0006\u0002\"\u0001\u00050\"9A\u0011\u0018\u0005\u0005\u0002\u0011m\u0006b\u0002C`\u0011\u0011\u0005A\u0011\u0019\u0005\b\t\u000bDA\u0011\u0001Cd\u0011\u001d!Y\r\u0003C\u0001\t\u001bDq\u0001\"5\t\t\u0003!\u0019\u000eC\u0004\u0005X\"!\t\u0001\"7\t\u0013\u0011u\u0007\u0002\"\u0001\u0002\u0006\u0011}\u0007b\u0002Cq\u0011\u0011%A1\u001d\u0005\b\t[DA\u0011\u0002Cx\u0011\u001d!\u0019\u0010\u0003C\u0005\u0005[Aq\u0001\">\t\t\u0013!9\u0010C\u0004\u0005~\"!I\u0001b@\t\u000f\u0015\r\u0001\u0002\"\u0003\u0006\u0006!9Q\u0011\u0002\u0005\u0005\n\t5\u0002bBC\u0006\u0011\u0011%!Q\u0006\u0005\n\u000b\u001bA!\u0019!C\u0005\u000b\u001fA\u0001\"b\u0006\tA\u0003%Q\u0011\u0003\u0005\n\u000bkA!\u0019!C\u0005\u000boA\u0001\"b\u000f\tA\u0003%Q\u0011\b\u0005\n\u000b{A!\u0019!C\u0005\u000boA\u0001\"b\u0010\tA\u0003%Q\u0011\b\u0005\n\u000b\u0003B!\u0019!C\u0005\u0005oA\u0001\"b\u0011\tA\u0003%!\u0011\b\u0005\n\u000b\u000bB!\u0019!C\u0005\u000boA\u0001\"b\u0012\tA\u0003%Q\u0011\b\u0005\b\u000b\u0013BA\u0011BC&\u0011%)\t\u0006\u0003b\u0001\n\u0013)\u0019\u0006\u0003\u0005\u0006b!\u0001\u000b\u0011BC+\r%\u00119\u0003\u0003I\u0001\u0004S\u0011I\u0003C\u0004\u0003,a#\tA!\f\t\u0013\tU\u0002L1A\u0005\n\t]\u0002b\u0002B*1\u0012\u0005!Q\u000b\u0005\b\u00057BF\u0011\u0002B/\u0011\u001d\u0011)\u0007\u0017C\t\u0005[AqAa\u001aY\t\u0003\u0011I\u0007C\u0004\u0003ha#\tAa*\t\u000f\tU\u0006L\"\u0001\u00038\"9!Q\u0017-\u0005\u0002\t-\u0007b\u0002Bm1\u001a\u0005!Q\u0006\u0005\b\u00057Df\u0011\u0001B\u0017\u0011\u001d\u0011i\u000e\u0017C\u0003\u0005[AqAa8Y\r\u0003\u0011icB\u0004\u0006d!AIA!>\u0007\u000f\t\r\b\u0002#\u0003\u0003f\"9\u0011qD4\u0005\u0002\tM\bb\u0002B[O\u0012\u0005#q\u001f\u0005\b\u00053<G\u0011\tB\u0017\u0011\u001d\u0011Yn\u001aC!\u0005[AqAa8h\t\u0003\u0012i\u0003C\u0004\u0004\f\u001d$\te!\u0004\b\u000f\u0015\u0015\u0004\u0002#\u0003\u0004\u001c\u001991q\u0002\u0005\t\n\rE\u0001bBA\u0010_\u0012\u00051\u0011\u0004\u0005\b\u0005k{G\u0011IB\u000f\u0011\u001d\u0011In\u001cC!\u0005[AqAa7p\t\u0003\u0012i\u0003C\u0004\u0003`>$\tE!\f\t\u000f\r-q\u000e\"\u0011\u0004\u000e\u001d9Qq\r\u0005\t\n\rubaBB\u0019\u0011!%11\u0007\u0005\b\u0003?9H\u0011AB\u001e\u0011\u001d\u0011)l\u001eC!\u0007\u007fAqaa\u0015x\t\u0013\u0019)\u0006C\u0004\u0003Z^$\tE!\f\t\u000f\tmw\u000f\"\u0011\u0003.!9!q\\<\u0005B\t5\u0002bBB\u0006o\u0012\u00053QB\u0001\u000f\u0007&\u00148-^5u\u0005J,\u0017m[3s\u0015\u0011\t\u0019!!\u0002\u0002\u000fA\fG\u000f^3s]*\u0011\u0011qA\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0007\u00055\u0011!\u0004\u0002\u0002\u0002\tq1)\u001b:dk&$(I]3bW\u0016\u00148cA\u0001\u0002\u0014A!\u0011QCA\u000e\u001b\t\t9B\u0003\u0002\u0002\u001a\u0005)1oY1mC&!\u0011QDA\f\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\"!a\u0003\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0015\u0005\u001dR\u0011NC6\u000b[*y\u0007E\u0002\u0002\u000e!\u00192\u0001CA\u0016!\u0011\ti!!\f\n\t\u0005=\u0012\u0011\u0001\u0002\u0017\u0003\n\u001cHO]1di\u000eK'oY;ji\n\u0013X-Y6fe\u0006I1o\u00195fIVdWM\u001d\t\u0005\u0003k\tY$\u0004\u0002\u00028)!\u0011\u0011HA\u0003\u0003\u0015\t7\r^8s\u0013\u0011\ti$a\u000e\u0003\u0013M\u001b\u0007.\u001a3vY\u0016\u0014\u0018aC7bq\u001a\u000b\u0017\u000e\\;sKN\u0004B!!\u0006\u0002D%!\u0011QIA\f\u0005\rIe\u000e^\u0001\fG\u0006dG\u000eV5nK>,H\u000f\u0005\u0003\u0002L\u0005USBAA'\u0015\u0011\ty%!\u0015\u0002\u0011\u0011,(/\u0019;j_:TA!a\u0015\u0002\u0018\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\t\u0005]\u0013Q\n\u0002\u000f\r&t\u0017\u000e^3EkJ\fG/[8o\u00031\u0011Xm]3u)&lWm\\;u+\t\tI%A\u0007sKN,G\u000fV5nK>,H\u000fI\u0001\u0010[\u0006D(+Z:fiRKW.Z8vi\u0006AR\r\u001f9p]\u0016tG/[1m\u0005\u0006\u001c7n\u001c4g\r\u0006\u001cGo\u001c:\u0011\t\u0005U\u0011QM\u0005\u0005\u0003O\n9B\u0001\u0004E_V\u0014G.Z\u0001\re\u0006tGm\\7GC\u000e$xN]\u0001\u0010C2dwn^#yG\u0016\u0004H/[8ogV\u0011\u0011q\u000e\t\u0007\u0003c\ny(!\"\u000f\t\u0005M\u00141\u0010\t\u0005\u0003k\n9\"\u0004\u0002\u0002x)!\u0011\u0011PA\u0005\u0003\u0019a$o\\8u}%!\u0011QPA\f\u0003\u0019\u0001&/\u001a3fM&!\u0011\u0011QAB\u0005\r\u0019V\r\u001e\u0006\u0005\u0003{\n9\u0002\u0005\u0003\u0002r\u0005\u001d\u0015\u0002BAE\u0003\u0007\u0013aa\u0015;sS:<\u0017\u0001E1mY><X\t_2faRLwN\\:!\u0003%!X\r\\3nKR\u0014\u00180\u0006\u0002\u0002\u0012B!\u00111SAM\u001b\t\t)J\u0003\u0003\u0002\u0018\u0006\u0005\u0011\u0001C5oi\u0016\u0014h.\u00197\n\t\u0005m\u0015Q\u0013\u0002\u0018\u0007&\u00148-^5u\u0005J,\u0017m[3s)\u0016dW-\\3uef\f!\u0002^3mK6,GO]=!\u0003!)\u00070Z2vi>\u0014\b\u0003BAR\u0003Kk!!!\u0015\n\t\u0005\u001d\u0016\u0011\u000b\u0002\u0011\u000bb,7-\u001e;j_:\u001cuN\u001c;fqR$B#a+\u00020\u0006E\u00161WA[\u0003o\u000bI,a/\u0002>\u0006}F\u0003BA\u0014\u0003[Cq!a(\u0017\u0001\b\t\t\u000bC\u0004\u00022Y\u0001\r!a\r\t\u000f\u0005}b\u00031\u0001\u0002B!9\u0011q\t\fA\u0002\u0005%\u0003bBA--\u0001\u0007\u0011\u0011\n\u0005\b\u0003?2\u0002\u0019AA%\u0011\u001d\t\tG\u0006a\u0001\u0003GBq!!\u001b\u0017\u0001\u0004\t\u0019\u0007C\u0004\u0002lY\u0001\r!a\u001c\t\u000f\u00055e\u00031\u0001\u0002\u0012R\u0001\u00121YAd\u0003\u0013\fY-!4\u0002P\u0006E\u00171\u001b\u000b\u0005\u0003O\t)\rC\u0004\u0002 ^\u0001\u001d!!)\t\u000f\u0005Er\u00031\u0001\u00024!9\u0011qH\fA\u0002\u0005\u0005\u0003bBA$/\u0001\u0007\u0011\u0011\n\u0005\b\u00033:\u0002\u0019AA%\u0011\u001d\tyf\u0006a\u0001\u0003\u0013Bq!!\u0019\u0018\u0001\u0004\t\u0019\u0007C\u0004\u0002j]\u0001\r!a\u0019\u0015\u0019\u0005\u001d\u0012q[Am\u00037\fi.a<\t\u000f\u0005}\u0005\u00041\u0001\u0002\"\"9\u0011\u0011\u0007\rA\u0002\u0005M\u0002bBA 1\u0001\u0007\u0011\u0011\t\u0005\b\u0003\u000fB\u0002\u0019AAp!\u0011\t\t/a;\u000e\u0005\u0005\r(\u0002BAs\u0003O\fA\u0001^5nK*\u0011\u0011\u0011^\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002n\u0006\r(\u0001\u0003#ve\u0006$\u0018n\u001c8\t\u000f\u0005e\u0003\u00041\u0001\u0002`RQ\u00111_A|\u0003s\fY0!@\u0015\t\u0005\u001d\u0012Q\u001f\u0005\b\u0003?K\u00029AAQ\u0011\u001d\t\t$\u0007a\u0001\u0003gAq!a\u0010\u001a\u0001\u0004\t\t\u0005C\u0004\u0002He\u0001\r!!\u0013\t\u000f\u0005e\u0013\u00041\u0001\u0002JQq!\u0011\u0001B\u0003\u0005\u000f\u0011IAa\u0003\u0003\u000e\t=A\u0003BA\u0014\u0005\u0007Aq!a(\u001b\u0001\b\t\t\u000bC\u0004\u00022i\u0001\r!a\r\t\u000f\u0005}\"\u00041\u0001\u0002B!9\u0011q\t\u000eA\u0002\u0005%\u0003bBA-5\u0001\u0007\u0011\u0011\n\u0005\b\u0003?R\u0002\u0019AA%\u0011\u001d\t\tG\u0007a\u0001\u0003G\nac^5uQ\u0016C\bo\u001c8f]RL\u0017\r\u001c\"bG.|gM\u001a\u000b\u0005\u0003O\u0011)\u0002C\u0004\u0002`m\u0001\r!!\u0013\u0015\t\u0005\u001d\"\u0011\u0004\u0005\b\u0003?b\u0002\u0019AAp\u0003A9\u0018\u000e\u001e5SC:$w.\u001c$bGR|'\u000f\u0006\u0003\u0002(\t}\u0001bBA5;\u0001\u0007\u00111M\u0001!?\u000e,(O]3oiN#\u0018\r^3E_:{GoQ1mY6+G)\u001b:fGRd\u0017\u0010E\u0002\u0003&ak\u0011\u0001\u0003\u0002\u0006'R\fG/Z\n\u00041\u0006M\u0011A\u0002\u0013j]&$H\u0005\u0006\u0002\u00030A!\u0011Q\u0003B\u0019\u0013\u0011\u0011\u0019$a\u0006\u0003\tUs\u0017\u000e^\u0001\nY&\u001cH/\u001a8feN,\"A!\u000f\u0011\r\tm\"1\tB$\u001b\t\u0011iD\u0003\u0003\u0002T\t}\"\u0002\u0002B!\u0003O\fA!\u001e;jY&!!Q\tB\u001f\u0005Q\u0019u\u000e]=P]^\u0013\u0018\u000e^3BeJ\f\u0017\u0010T5tiB!!\u0011\nB(\u001b\t\u0011YE\u0003\u0003\u0003N\u0005\u001d\u0018\u0001\u00027b]\u001eLAA!\u0015\u0003L\tA!+\u001e8oC\ndW-A\u0006bI\u0012d\u0015n\u001d;f]\u0016\u0014H\u0003\u0002B\u0018\u0005/BqA!\u0017\\\u0001\u0004\u00119%\u0001\u0005mSN$XM\\3s\u00031A\u0017m\u001d'jgR,g.\u001a:t+\t\u0011y\u0006\u0005\u0003\u0002\u0016\t\u0005\u0014\u0002\u0002B2\u0003/\u0011qAQ8pY\u0016\fg.A\ro_RLg-\u001f+sC:\u001c\u0018\u000e^5p]2K7\u000f^3oKJ\u001c\u0018aC2bY2$\u0006N]8vO\",BAa\u001b\u0003xQ1!Q\u000eBE\u0005'\u0003b!a)\u0003p\tM\u0014\u0002\u0002B9\u0003#\u0012aAR;ukJ,\u0007\u0003\u0002B;\u0005ob\u0001\u0001B\u0004\u0003zy\u0013\rAa\u001f\u0003\u0003Q\u000bBA! \u0003\u0004B!\u0011Q\u0003B@\u0013\u0011\u0011\t)a\u0006\u0003\u000f9{G\u000f[5oOB!\u0011Q\u0003BC\u0013\u0011\u00119)a\u0006\u0003\u0007\u0005s\u0017\u0010\u0003\u0005\u0003\fz#\t\u0019\u0001BG\u0003\u0011\u0011w\u000eZ=\u0011\r\u0005U!q\u0012B7\u0013\u0011\u0011\t*a\u0006\u0003\u0011q\u0012\u0017P\\1nKzBqA!&_\u0001\u0004\u00119*A\beK\u001aLg.\u001a$bS2,(/\u001a$o!!\t)B!'\u0003\u001e\n}\u0013\u0002\u0002BN\u0003/\u0011\u0011BR;oGRLwN\\\u0019\u0011\r\t}%1\u0015B:\u001b\t\u0011\tK\u0003\u0003\u0003B\u0005]\u0011\u0002\u0002BS\u0005C\u00131\u0001\u0016:z+\u0011\u0011IKa,\u0015\t\t-&\u0011\u0017\t\u0007\u0003G\u0013yG!,\u0011\t\tU$q\u0016\u0003\b\u0005sz&\u0019\u0001B>\u0011!\u0011Yi\u0018CA\u0002\tM\u0006CBA\u000b\u0005\u001f\u0013Y+\u0001\u0004j]Z|7.Z\u000b\u0005\u0005s\u0013y\f\u0006\u0004\u0003<\n\u0005'Q\u0019\t\u0007\u0003G\u0013yG!0\u0011\t\tU$q\u0018\u0003\b\u0005s\u0002'\u0019\u0001B>\u0011!\u0011Y\t\u0019CA\u0002\t\r\u0007CBA\u000b\u0005\u001f\u0013Y\fC\u0004\u0003\u0016\u0002\u0004\rAa2\u0011\u0011\u0005U!\u0011\u0014Be\u0005?\u0002bAa(\u0003$\nuV\u0003\u0002Bg\u0005'$BAa4\u0003VB1\u00111\u0015B8\u0005#\u0004BA!\u001e\u0003T\u00129!\u0011P1C\u0002\tm\u0004\u0002\u0003BFC\u0012\u0005\rAa6\u0011\r\u0005U!q\u0012Bh\u00031\u0019\u0017\r\u001c7Tk\u000e\u001cW-\u001a3t\u0003%\u0019\u0017\r\u001c7GC&d7/A\u0003f]R,'/\u0001\u0004`K:$XM]\u0015\u00051\u001e|wO\u0001\u0004DY>\u001cX\rZ\n\u0006O\n\u001d(1\u0005\t\u0005\u0005S\u0014y/\u0004\u0002\u0003l*!!Q\u001eB\u001f\u0003\u0019\tGo\\7jG&!!\u0011\u001fBv\u00055\tEo\\7jG&sG/Z4feR\u0011!Q\u001f\t\u0004\u0005K9W\u0003\u0002B}\u0005\u007f$bAa?\u0004\u0002\r\u0015\u0001CBAR\u0005_\u0012i\u0010\u0005\u0003\u0003v\t}Ha\u0002B=S\n\u0007!1\u0010\u0005\t\u0005\u0017KG\u00111\u0001\u0004\u0004A1\u0011Q\u0003BH\u0005wDqA!&j\u0001\u0004\u00199\u0001\u0005\u0005\u0002\u0016\te5\u0011\u0002B0!\u0019\u0011yJa)\u0003~\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\u0006\nA\u0001*\u00197g\u001fB,gnE\u0003p\u0007'\u0011\u0019\u0003\u0005\u0003\u0003j\u000eU\u0011\u0002BB\f\u0005W\u0014Q\"\u0011;p[&\u001c'i\\8mK\u0006tGCAB\u000e!\r\u0011)c\\\u000b\u0005\u0007?\u0019)\u0003\u0006\u0004\u0004\"\r\u001d21\u0006\t\u0007\u0003G\u0013yga\t\u0011\t\tU4Q\u0005\u0003\b\u0005s\n(\u0019\u0001B>\u0011!\u0011Y)\u001dCA\u0002\r%\u0002CBA\u000b\u0005\u001f\u001b\t\u0003C\u0004\u0003\u0016F\u0004\ra!\f\u0011\u0011\u0005U!\u0011TB\u0018\u0005?\u0002bAa(\u0003$\u000e\r\"\u0001B(qK:\u001cRa^B\u001b\u0005G\u0001BA!;\u00048%!1\u0011\bBv\u0005)\tEo\\7jG2{gn\u001a\u000b\u0003\u0007{\u00012A!\nx+\u0011\u0019\tea\u0012\u0015\r\r\r3\u0011JB'!\u0019\t\u0019Ka\u001c\u0004FA!!QOB$\t\u001d\u0011I(\u001fb\u0001\u0005wB\u0001Ba#z\t\u0003\u000711\n\t\u0007\u0003+\u0011yia\u0011\t\u000f\tU\u0015\u00101\u0001\u0004PAA\u0011Q\u0003BM\u0007#\u0012y\u0006\u0005\u0004\u0003 \n\r6QI\u0001\u0012e\u0016l\u0017-\u001b8j]\u001e$UO]1uS>tGCAA%Q\rq2\u0011\f\t\u0005\u0003+\u0019Y&\u0003\u0003\u0004^\u0005]!\u0001\u0003<pY\u0006$\u0018\u000e\\3\u0002O}\u001bWO\u001d:f]R\u0014Vm]3u)&lWm\\;u\t>tu\u000e^\"bY2lU\rR5sK\u000e$H.\u001f\u0015\u0004?\re\u0013\u0001H0qe\u00164XM\u001c;Qe&4\u0018\r^3V]V\u001cX\rZ#sCN,(/Z\u0001\ng^\f\u0007o\u0015;bi\u0016$bAa\u0018\u0004j\r5\u0004bBB6C\u0001\u0007!1E\u0001\t_2$7\u000b^1uK\"91qN\u0011A\u0002\t\r\u0012\u0001\u00038foN#\u0018\r^3)\u0007\u0005\u001a\u0019\b\u0005\u0003\u0002\u0016\rU\u0014\u0002BB<\u0003/\u0011a!\u001b8mS:,\u0017\u0001D2veJ,g\u000e^*uCR,WC\u0001B\u0012Q\r\u001131O\u0001\u0011g^\f\u0007OU3tKR$\u0016.\\3pkR$bAa\u0018\u0004\u0004\u000e\u001d\u0005bBBCG\u0001\u0007\u0011\u0011J\u0001\u0010_2$'+Z:fiRKW.Z8vi\"91\u0011R\u0012A\u0002\u0005%\u0013a\u00048foJ+7/\u001a;US6,w.\u001e;)\u0007\r\u001a\u0019(A\ndkJ\u0014XM\u001c;SKN,G\u000fV5nK>,H\u000fK\u0002%\u0007g\n!c^5uQ\u000eK'oY;ji\n\u0013X-Y6feV!1QSBN)\u0019\u00199j!(\u0004\"B1\u00111\u0015B8\u00073\u0003BA!\u001e\u0004\u001c\u00129!\u0011P\u0013C\u0002\tm\u0004\u0002\u0003BFK\u0011\u0005\raa(\u0011\r\u0005U!qRBL\u0011\u001d\u0011)*\na\u0001\u0007G\u0003\u0002\"!\u0006\u0003\u001a\u000e\u0015&q\f\t\u0007\u0005?\u0013\u0019k!'\u0016\t\r%6q\u0016\u000b\u0005\u0007W\u001b\t\f\u0005\u0004\u0002$\n=4Q\u0016\t\u0005\u0005k\u001ay\u000bB\u0004\u0003z\u0019\u0012\rAa\u001f\t\u0011\t-e\u0005\"a\u0001\u0007g\u0003b!!\u0006\u0003\u0010\u000e-\u0016AF2bY2<\u0016\u000e\u001e5DSJ\u001cW/\u001b;Ce\u0016\f7.\u001a:\u0016\t\re6q\u0018\u000b\u0005\u0007w\u001b\t\r\u0005\u0004\u0002$\n=4Q\u0018\t\u0005\u0005k\u001ay\fB\u0004\u0003z\u001d\u0012\rAa\u001f\t\u000f\t-u\u00051\u0001\u0004DB1!1HBc\u0007wKAaa2\u0003>\tA1)\u00197mC\ndW-\u0006\u0003\u0004L\u000eEGCBBg\u0007'\u001c9\u000e\u0005\u0004\u0002$\n=4q\u001a\t\u0005\u0005k\u001a\t\u000eB\u0004\u0003z!\u0012\rAa\u001f\t\u000f\t-\u0005\u00061\u0001\u0004VB1!1HBc\u0007\u001bDqA!&)\u0001\u0004\u0019I\u000e\u0005\u0006\u0004\\\u000e\u00058Q]Bw\t\u0003i!a!8\u000b\t\r}'qH\u0001\tMVt7\r^5p]&!11]Bo\u0005)\u0011\u0015NR;oGRLwN\u001c\t\u0007\u0007O\u001cIoa4\u000e\u0005\t}\u0012\u0002BBv\u0005\u007f\u0011\u0001b\u00149uS>t\u0017\r\u001c\t\u0007\u0007O\u001cIoa<\u0011\t\rE81 \b\u0005\u0007g\u001c9P\u0004\u0003\u0002v\rU\u0018BAA\r\u0013\u0011\u0019I0a\u0006\u0002\u000fA\f7m[1hK&!1Q`B\u0000\u0005%!\u0006N]8xC\ndWM\u0003\u0003\u0004z\u0006]\u0001\u0003\u0002B%\t\u0007IAAa\u0019\u0003L\u0005A2-\u00197m/&$\bnQ5sGVLGO\u0011:fC.,'oQ*\u0016\t\u0011%A1\u0003\u000b\u0005\t\u0017!)\u0002\u0005\u0004\u0003<\u00115A\u0011C\u0005\u0005\t\u001f\u0011iDA\bD_6\u0004H.\u001a;j_:\u001cF/Y4f!\u0011\u0011)\bb\u0005\u0005\u000f\te\u0014F1\u0001\u0003|!9!1R\u0015A\u0002\u0011]\u0001C\u0002B\u001e\u0007\u000b$Y!\u0006\u0003\u0005\u001c\u0011\u0005BC\u0002C\u000f\tG!9\u0003\u0005\u0004\u0003<\u00115Aq\u0004\t\u0005\u0005k\"\t\u0003B\u0004\u0003z)\u0012\rAa\u001f\t\u000f\t-%\u00061\u0001\u0005&A1!1HBc\t;AqA!&+\u0001\u0004!I\u0003\u0005\u0006\u0004\\\u000e\u0005H1FBw\t\u0003\u0001baa:\u0004j\u0012}\u0011AF<ji\"\u001c\u0016P\\2DSJ\u001cW/\u001b;Ce\u0016\f7.\u001a:\u0016\t\u0011EBQ\u0007\u000b\u0005\tg!9\u0004\u0005\u0003\u0003v\u0011UBa\u0002B=W\t\u0007!1\u0010\u0005\t\u0005\u0017[C\u00111\u0001\u0005:A1\u0011Q\u0003BH\tg)B\u0001\"\u0010\u0005BQ1Aq\bC\"\t\u000f\u0002BA!\u001e\u0005B\u00119!\u0011\u0010\u0017C\u0002\tm\u0004\u0002\u0003BFY\u0011\u0005\r\u0001\"\u0012\u0011\r\u0005U!q\u0012C \u0011\u001d\u0011)\n\fa\u0001\t\u0013\u0002\u0002\"!\u0006\u0003\u001a\u0012-#q\f\t\u0007\u0005?\u0013\u0019\u000bb\u0010\u00025\r\fG\u000e\\,ji\"\u001c\u0016P\\2DSJ\u001cW/\u001b;Ce\u0016\f7.\u001a:\u0016\t\u0011ECQ\u000b\u000b\u0005\t'\"9\u0006\u0005\u0003\u0003v\u0011UCa\u0002B=[\t\u0007!1\u0010\u0005\b\u0005\u0017k\u0003\u0019\u0001C-!\u0019\u0011Yd!2\u0005TU!AQ\fC1)\u0019!y\u0006b\u0019\u0005hA!!Q\u000fC1\t\u001d\u0011IH\fb\u0001\u0005wBqAa#/\u0001\u0004!)\u0007\u0005\u0004\u0003<\r\u0015Gq\f\u0005\b\u0005+s\u0003\u0019\u0001C5!)\u0019Yn!9\u0005l\r5H\u0011\u0001\t\u0007\u0007O\u001cI\u000fb\u0018\u0002\u000fM,8mY3fI\u0006!a-Y5m\u0003!I7o\u00117pg\u0016$\u0017AB5t\u001fB,g.\u0001\u0006jg\"\u000bGNZ(qK:\faa\u001c8Pa\u0016tG\u0003BA\u0014\twB\u0001\u0002\" 5\t\u0003\u0007AqP\u0001\tG\u0006dGNY1dWB1\u0011Q\u0003BH\u0005_\t\u0011#\u00193e\u001f:|\u0005/\u001a8MSN$XM\\3s)\u0011\t9\u0003\"\"\t\u000f\u0011uT\u00071\u0001\u0003H\u0005QqN\u001c%bY\u001a|\u0005/\u001a8\u0015\t\u0005\u001dB1\u0012\u0005\t\t{2D\u00111\u0001\u0005\u0000\u0005)\u0012\r\u001a3P]\"\u000bGNZ(qK:d\u0015n\u001d;f]\u0016\u0014H\u0003BA\u0014\t#Cq\u0001\" 8\u0001\u0004\u00119%A\u0004p]\u000ecwn]3\u0015\t\u0005\u001dBq\u0013\u0005\t\t{BD\u00111\u0001\u0005\u0000\u0005\u0011\u0012\r\u001a3P]\u000ecwn]3MSN$XM\\3s)\u0011\t9\u0003\"(\t\u000f\u0011u\u0014\b1\u0001\u0003H\u0005iqN\\\"bY2\u001cVoY2fgN$B!a\n\u0005$\"9AQ\u0010\u001eA\u0002\u0011\u0015\u0006\u0003CA\u000b\u00053#9Ka\f\u0011\t\u0005UA\u0011V\u0005\u0005\tW\u000b9B\u0001\u0003M_:<\u0017\u0001G1eI>s7)\u00197m'V\u001c7-Z:t\u0019&\u001cH/\u001a8feR!\u0011q\u0005CY\u0011\u001d!ih\u000fa\u0001\tg\u0003baa7\u00056\u0012\u001d\u0016\u0002\u0002C\\\u0007;\u0014\u0001bQ8ogVlWM]\u0001\u000e_:\u001c\u0015\r\u001c7GC&dWO]3\u0015\t\u0005\u001dBQ\u0018\u0005\b\t{b\u0004\u0019\u0001CS\u0003a\tG\rZ(o\u0007\u0006dGNR1jYV\u0014X\rT5ti\u0016tWM\u001d\u000b\u0005\u0003O!\u0019\rC\u0004\u0005~u\u0002\r\u0001b-\u0002\u001b=t7)\u00197m)&lWm\\;u)\u0011\t9\u0003\"3\t\u000f\u0011ud\b1\u0001\u0005&\u0006A\u0012\r\u001a3P]\u000e\u000bG\u000e\u001c+j[\u0016|W\u000f\u001e'jgR,g.\u001a:\u0015\t\u0005\u001dBq\u001a\u0005\b\t{z\u0004\u0019\u0001CZ\u0003EygnQ1mY\n\u0013X-Y6fe>\u0003XM\u001c\u000b\u0005\u0003O!)\u000e\u0003\u0005\u0005~\u0001#\t\u0019\u0001C@\u0003q\tG\rZ(o\u0007\u0006dGN\u0011:fC.,'o\u00149f]2K7\u000f^3oKJ$B!a\n\u0005\\\"9AQP!A\u0002\t\u001d\u0013aE2veJ,g\u000e\u001e$bS2,(/Z\"pk:$XCAA!\u0003)!(/\u00198tSRLwN\u001c\u000b\u0007\u0005_!)\u000f\";\t\u000f\u0011\u001d8\t1\u0001\u0003$\u0005IaM]8n'R\fG/\u001a\u0005\b\tW\u001c\u0005\u0019\u0001B\u0012\u0003\u001d!xn\u0015;bi\u0016\f1\u0002\u001e:ja\n\u0013X-Y6feR!!q\u0006Cy\u0011\u001d!9\u000f\u0012a\u0001\u0005G\tAB]3tKR\u0014%/Z1lKJ\f!D\\8uS\u001aL8)\u00197m'V\u001c7-Z:t\u0019&\u001cH/\u001a8feN$BAa\f\u0005z\"9A1 $A\u0002\u0011\u001d\u0016!B:uCJ$\u0018A\u00078pi&4\u0017pQ1mY\u001a\u000b\u0017\u000e\\;sK2K7\u000f^3oKJ\u001cH\u0003\u0002B\u0018\u000b\u0003Aq\u0001b?H\u0001\u0004!9+\u0001\u000eo_RLg-_\"bY2$\u0016.\\3pkRd\u0015n\u001d;f]\u0016\u00148\u000f\u0006\u0003\u00030\u0015\u001d\u0001b\u0002C~\u0011\u0002\u0007AqU\u0001\u001f]>$\u0018NZ=DC2d'I]3bW\u0016\u0014x\n]3o\u0019&\u001cH/\u001a8feN\fA\"\u0019;uK6\u0004HOU3tKR\f\u0011\u0002^5nK>,H/\u0012=\u0016\u0005\u0015E!CBC\n\u000b3)IC\u0002\u0004\u0006\u00161\u0003Q\u0011\u0003\u0002\ryI,g-\u001b8f[\u0016tGOP\u0001\u000bi&lWm\\;u\u000bb\u0004\u0003\u0003BC\u000e\u000bGqA!\"\b\u0006\"9!11_C\u0010\u0013\u0011\t\u0019&a\u0006\n\t\re\u0018\u0011K\u0005\u0005\u000bK)9C\u0001\tUS6,w.\u001e;Fq\u000e,\u0007\u000f^5p]*!1\u0011`A)!\u0011)Y#\"\r\u000e\u0005\u00155\"\u0002BC\u0018\u0005C\u000bqaY8oiJ|G.\u0003\u0003\u00064\u00155\"\u0001\u0004(p'R\f7m\u001b+sC\u000e,\u0017\u0001F2bY24\u0015-\u001b7ve\u0016d\u0015n\u001d;f]\u0016\u00148/\u0006\u0002\u0006:A1!1\bB\"\tg\u000bQcY1mY\u001a\u000b\u0017\u000e\\;sK2K7\u000f^3oKJ\u001c\b%\u0001\u000bdC2dG+[7f_V$H*[:uK:,'o]\u0001\u0016G\u0006dG\u000eV5nK>,H\u000fT5ti\u0016tWM]:!\u0003a\u0019\u0017\r\u001c7Ce\u0016\f7.\u001a:Pa\u0016tG*[:uK:,'o]\u0001\u001aG\u0006dGN\u0011:fC.,'o\u00149f]2K7\u000f^3oKJ\u001c\b%\u0001\ttk\u000e\u001cWm]:MSN$XM\\3sg\u0006\t2/^2dKN\u001cH*[:uK:,'o\u001d\u0011\u0002%%\u001c\u0018j\u001a8pe\u0016$W\t_2faRLwN\u001c\u000b\u0005\u0005?*i\u0005C\u0004\u0006PU\u0003\rAa!\u0002\u0005\u0015D\u0018!\u00034bS2,(/\u001a$o+\t))\u0006\u0005\u0005\u0002\u0016\teUq\u000bB0a\u0011)I&\"\u0018\u0011\r\t}%1UC.!\u0011\u0011)(\"\u0018\u0005\u0017\u0015}s+!A\u0001\u0002\u000b\u0005!1\u0010\u0002\u0004?\u0012\n\u0014A\u00034bS2,(/\u001a$oA\u000511\t\\8tK\u0012\f\u0001\u0002S1mM>\u0003XM\\\u0001\u0005\u001fB,g\u000eC\u0004\u00022\r\u0001\r!a\r\t\u000f\u0005}2\u00011\u0001\u0002B!9\u0011qI\u0002A\u0002\u0005%\u0003bBA-\u0007\u0001\u0007\u0011\u0011\n\u000b\u0005\u000bg*y\b\u0006\u0003\u0002(\u0015U\u0004bBC<\t\u0001\u000fQ\u0011P\u0001\u0007gf\u001cH/Z7\u0011\t\u0005UR1P\u0005\u0005\u000b{\n9DA\nFqR,g\u000eZ3e\u0003\u000e$xN]*zgR,W\u000eC\u0004\u0006\u0002\u0012\u0001\r!!\"\u0002\u0005%$\u0017AB2sK\u0006$X\r\u0006\u0006\u0002(\u0015\u001dU\u0011RCF\u000b\u001bCq!!\r\u0006\u0001\u0004\t\u0019\u0004C\u0004\u0002@\u0015\u0001\r!!\u0011\t\u000f\u0005\u001dS\u00011\u0001\u0002`\"9\u0011\u0011L\u0003A\u0002\u0005}\u0017A\u00027p_.,\b\u000f\u0006\u0004\u0002(\u0015MUQ\u0013\u0005\b\u000b\u00033\u0001\u0019AAC\u0011\u001d)9H\u0002a\u0001\u000bs\n1dY8om\u0016\u0014HOS1wC\u001a\u000b\u0017\u000e\\;sK\u001asGk\\*dC2\fW\u0003BCN\u000bG#B!\"(\u0006&BA\u0011Q\u0003BM\u000b?\u0013y\u0006\u0005\u0004\u0003 \n\rV\u0011\u0015\t\u0005\u0005k*\u0019\u000bB\u0004\u0003z\u001d\u0011\rAa\u001f\t\u000f\u0015\u001dv\u00011\u0001\u0006*\u00061!.\u0019<b\r:\u0004\"ba7\u0004b\u0016-6Q\u001eC\u0001!\u0019\u00199o!;\u0006\"\u0002")
public class CircuitBreaker
extends AbstractCircuitBreaker {
    private volatile CircuitBreaker$Closed$ Closed$module;
    private volatile CircuitBreaker$HalfOpen$ HalfOpen$module;
    private volatile CircuitBreaker$Open$ Open$module;
    public final Scheduler akka$pattern$CircuitBreaker$$scheduler;
    public final int akka$pattern$CircuitBreaker$$maxFailures;
    public final FiniteDuration akka$pattern$CircuitBreaker$$callTimeout;
    private final FiniteDuration resetTimeout;
    public final FiniteDuration akka$pattern$CircuitBreaker$$maxResetTimeout;
    public final double akka$pattern$CircuitBreaker$$exponentialBackoffFactor;
    public final double akka$pattern$CircuitBreaker$$randomFactor;
    private final Set<String> allowExceptions;
    private final CircuitBreakerTelemetry telemetry;
    public final ExecutionContext akka$pattern$CircuitBreaker$$executor;
    private volatile State _currentStateDoNotCallMeDirectly;
    private volatile FiniteDuration _currentResetTimeoutDoNotCallMeDirectly;
    private final TimeoutException akka$pattern$CircuitBreaker$$timeoutEx;
    private final CopyOnWriteArrayList<Consumer<Object>> callFailureListeners;
    private final CopyOnWriteArrayList<Consumer<Object>> callTimeoutListeners;
    private final CopyOnWriteArrayList<Runnable> callBreakerOpenListeners;
    private final CopyOnWriteArrayList<Consumer<Object>> successListeners;
    private final Function1<Try<?>, Object> akka$pattern$CircuitBreaker$$failureFn;

    public static CircuitBreaker lookup(String id, ExtendedActorSystem system) {
        return CircuitBreaker$.MODULE$.lookup(id, system);
    }

    public static CircuitBreaker create(Scheduler scheduler, int maxFailures, Duration callTimeout, Duration resetTimeout) {
        return CircuitBreaker$.MODULE$.create(scheduler, maxFailures, callTimeout, resetTimeout);
    }

    public static CircuitBreaker apply(String id, ExtendedActorSystem system) {
        return CircuitBreaker$.MODULE$.apply(id, system);
    }

    public static CircuitBreaker apply(Scheduler scheduler, int maxFailures, FiniteDuration callTimeout, FiniteDuration resetTimeout) {
        return CircuitBreaker$.MODULE$.apply(scheduler, maxFailures, callTimeout, resetTimeout);
    }

    public CircuitBreaker$Closed$ akka$pattern$CircuitBreaker$$Closed() {
        if (this.Closed$module == null) {
            this.Closed$lzycompute$1();
        }
        return this.Closed$module;
    }

    public CircuitBreaker$HalfOpen$ akka$pattern$CircuitBreaker$$HalfOpen() {
        if (this.HalfOpen$module == null) {
            this.HalfOpen$lzycompute$1();
        }
        return this.HalfOpen$module;
    }

    private CircuitBreaker$Open$ Open() {
        if (this.Open$module == null) {
            this.Open$lzycompute$1();
        }
        return this.Open$module;
    }

    public FiniteDuration resetTimeout() {
        return this.resetTimeout;
    }

    public Set<String> allowExceptions() {
        return this.allowExceptions;
    }

    public CircuitBreakerTelemetry telemetry() {
        return this.telemetry;
    }

    public CircuitBreaker withExponentialBackoff(FiniteDuration maxResetTimeout) {
        return new CircuitBreaker(this.akka$pattern$CircuitBreaker$$scheduler, this.akka$pattern$CircuitBreaker$$maxFailures, this.akka$pattern$CircuitBreaker$$callTimeout, this.resetTimeout(), maxResetTimeout, 2.0, this.akka$pattern$CircuitBreaker$$randomFactor, this.allowExceptions(), this.telemetry(), this.akka$pattern$CircuitBreaker$$executor);
    }

    public CircuitBreaker withExponentialBackoff(Duration maxResetTimeout) {
        return this.withExponentialBackoff(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(maxResetTimeout)));
    }

    public CircuitBreaker withRandomFactor(double randomFactor) {
        return new CircuitBreaker(this.akka$pattern$CircuitBreaker$$scheduler, this.akka$pattern$CircuitBreaker$$maxFailures, this.akka$pattern$CircuitBreaker$$callTimeout, this.resetTimeout(), this.akka$pattern$CircuitBreaker$$maxResetTimeout, this.akka$pattern$CircuitBreaker$$exponentialBackoffFactor, randomFactor, this.allowExceptions(), this.telemetry(), this.akka$pattern$CircuitBreaker$$executor);
    }

    private FiniteDuration _preventPrivateUnusedErasure() {
        return this._currentResetTimeoutDoNotCallMeDirectly;
    }

    private boolean swapState(State oldState, State newState) {
        return Unsafe.instance.compareAndSwapObject(this, AbstractCircuitBreaker.stateOffset, oldState, newState);
    }

    private State currentState() {
        return (State)Unsafe.instance.getObjectVolatile(this, AbstractCircuitBreaker.stateOffset);
    }

    public boolean akka$pattern$CircuitBreaker$$swapResetTimeout(FiniteDuration oldResetTimeout, FiniteDuration newResetTimeout) {
        return Unsafe.instance.compareAndSwapObject(this, AbstractCircuitBreaker.resetTimeoutOffset, oldResetTimeout, newResetTimeout);
    }

    public FiniteDuration akka$pattern$CircuitBreaker$$currentResetTimeout() {
        return (FiniteDuration)Unsafe.instance.getObjectVolatile(this, AbstractCircuitBreaker.resetTimeoutOffset);
    }

    public <T> Future<T> withCircuitBreaker(Function0<Future<T>> body2, Function1<Try<T>, Object> defineFailureFn) {
        return this.currentState().invoke(body2, defineFailureFn);
    }

    public <T> Future<T> withCircuitBreaker(Function0<Future<T>> body2) {
        return this.currentState().invoke(body2, this.akka$pattern$CircuitBreaker$$failureFn());
    }

    public <T> Future<T> callWithCircuitBreaker(Callable<Future<T>> body2) {
        return this.withCircuitBreaker((Function0<Future> & Serializable)() -> (Future)body2.call());
    }

    public <T> Future<T> callWithCircuitBreaker(Callable<Future<T>> body2, BiFunction<Optional<T>, Optional<Throwable>, Boolean> defineFailureFn) {
        Function1<Try<T>, Object> failureFnInScala = CircuitBreaker$.MODULE$.convertJavaFailureFnToScala(defineFailureFn);
        return this.withCircuitBreaker((Function0<Future> & Serializable)() -> (Future)body2.call(), failureFnInScala);
    }

    public <T> CompletionStage<T> callWithCircuitBreakerCS(Callable<CompletionStage<T>> body2) {
        return FutureConverters$.MODULE$.toJava(this.callWithCircuitBreaker(new Callable<Future<T>>(null, body2){
            private final Callable body$3;

            public Future<T> call() {
                return FutureConverters$.MODULE$.toScala((CompletionStage)this.body$3.call());
            }
            {
                this.body$3 = body$3;
            }
        }));
    }

    public <T> CompletionStage<T> callWithCircuitBreakerCS(Callable<CompletionStage<T>> body2, BiFunction<Optional<T>, Optional<Throwable>, Boolean> defineFailureFn) {
        return FutureConverters$.MODULE$.toJava(this.callWithCircuitBreaker(new Callable<Future<T>>(null, body2){
            private final Callable body$4;

            public Future<T> call() {
                return FutureConverters$.MODULE$.toScala((CompletionStage)this.body$4.call());
            }
            {
                this.body$4 = body$4;
            }
        }, defineFailureFn));
    }

    public <T> T withSyncCircuitBreaker(Function0<T> body2) {
        return this.withSyncCircuitBreaker(body2, this.akka$pattern$CircuitBreaker$$failureFn());
    }

    public <T> T withSyncCircuitBreaker(Function0<T> body2, Function1<Try<T>, Object> defineFailureFn) {
        return Await$.MODULE$.result(this.withCircuitBreaker((Function0<Future> & Serializable)() -> {
            Future<Object> future;
            try {
                future = Future$.MODULE$.successful(body2.apply());
            }
            catch (Throwable throwable) {
                Option<Throwable> option;
                Throwable throwable2 = throwable;
                if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                    Throwable t2 = option.get();
                    future = Future$.MODULE$.failed(t2);
                }
                throw throwable;
            }
            return future;
        }, defineFailureFn), this.akka$pattern$CircuitBreaker$$callTimeout);
    }

    public <T> T callWithSyncCircuitBreaker(Callable<T> body2) {
        return this.withSyncCircuitBreaker((Function0<Object> & Serializable)() -> body2.call());
    }

    public <T> T callWithSyncCircuitBreaker(Callable<T> body2, BiFunction<Optional<T>, Optional<Throwable>, Boolean> defineFailureFn) {
        Function1<Try<T>, Object> failureFnInScala = CircuitBreaker$.MODULE$.convertJavaFailureFnToScala(defineFailureFn);
        return this.withSyncCircuitBreaker((Function0<Object> & Serializable)() -> body2.call(), failureFnInScala);
    }

    public void succeed() {
        this.currentState().callSucceeds();
    }

    public void fail() {
        this.currentState().callFails();
    }

    public boolean isClosed() {
        State state = this.currentState();
        CircuitBreaker$Closed$ circuitBreaker$Closed$ = this.akka$pattern$CircuitBreaker$$Closed();
        return !(state != null ? !state.equals(circuitBreaker$Closed$) : circuitBreaker$Closed$ != null);
    }

    public boolean isOpen() {
        State state = this.currentState();
        CircuitBreaker$Open$ circuitBreaker$Open$ = this.Open();
        return !(state != null ? !state.equals(circuitBreaker$Open$) : circuitBreaker$Open$ != null);
    }

    public boolean isHalfOpen() {
        State state = this.currentState();
        CircuitBreaker$HalfOpen$ circuitBreaker$HalfOpen$ = this.akka$pattern$CircuitBreaker$$HalfOpen();
        return !(state != null ? !state.equals(circuitBreaker$HalfOpen$) : circuitBreaker$HalfOpen$ != null);
    }

    public CircuitBreaker onOpen(Function0<BoxedUnit> callback) {
        return this.addOnOpenListener(new Runnable(null, callback){
            private final Function0 callback$1;

            public void run() {
                this.callback$1.apply$mcV$sp();
            }
            {
                this.callback$1 = callback$1;
            }
        });
    }

    public CircuitBreaker addOnOpenListener(Runnable callback) {
        this.Open().addListener(callback);
        return this;
    }

    public CircuitBreaker onHalfOpen(Function0<BoxedUnit> callback) {
        return this.addOnHalfOpenListener(new Runnable(null, callback){
            private final Function0 callback$2;

            public void run() {
                this.callback$2.apply$mcV$sp();
            }
            {
                this.callback$2 = callback$2;
            }
        });
    }

    public CircuitBreaker addOnHalfOpenListener(Runnable callback) {
        this.akka$pattern$CircuitBreaker$$HalfOpen().addListener(callback);
        return this;
    }

    public CircuitBreaker onClose(Function0<BoxedUnit> callback) {
        return this.addOnCloseListener(new Runnable(null, callback){
            private final Function0 callback$3;

            public void run() {
                this.callback$3.apply$mcV$sp();
            }
            {
                this.callback$3 = callback$3;
            }
        });
    }

    public CircuitBreaker addOnCloseListener(Runnable callback) {
        this.akka$pattern$CircuitBreaker$$Closed().addListener(callback);
        return this;
    }

    public CircuitBreaker onCallSuccess(Function1<Object, BoxedUnit> callback) {
        return this.addOnCallSuccessListener(new Consumer<Object>(null, callback){
            private final Function1 callback$4;

            public Consumer<Object> andThen(Consumer<? super Object> x$1) {
                return Consumer.super.andThen(x$1);
            }

            public void accept(long result) {
                this.callback$4.apply$mcVJ$sp(result);
            }
            {
                this.callback$4 = callback$4;
            }
        });
    }

    public CircuitBreaker addOnCallSuccessListener(Consumer<Object> callback) {
        this.successListeners().add(callback);
        return this;
    }

    public CircuitBreaker onCallFailure(Function1<Object, BoxedUnit> callback) {
        return this.addOnCallFailureListener(new Consumer<Object>(null, callback){
            private final Function1 callback$5;

            public Consumer<Object> andThen(Consumer<? super Object> x$1) {
                return Consumer.super.andThen(x$1);
            }

            public void accept(long result) {
                this.callback$5.apply$mcVJ$sp(result);
            }
            {
                this.callback$5 = callback$5;
            }
        });
    }

    public CircuitBreaker addOnCallFailureListener(Consumer<Object> callback) {
        this.callFailureListeners().add(callback);
        return this;
    }

    public CircuitBreaker onCallTimeout(Function1<Object, BoxedUnit> callback) {
        return this.addOnCallTimeoutListener(new Consumer<Object>(null, callback){
            private final Function1 callback$6;

            public Consumer<Object> andThen(Consumer<? super Object> x$1) {
                return Consumer.super.andThen(x$1);
            }

            public void accept(long result) {
                this.callback$6.apply$mcVJ$sp(result);
            }
            {
                this.callback$6 = callback$6;
            }
        });
    }

    public CircuitBreaker addOnCallTimeoutListener(Consumer<Object> callback) {
        this.callTimeoutListeners().add(callback);
        return this;
    }

    public CircuitBreaker onCallBreakerOpen(Function0<BoxedUnit> callback) {
        return this.addOnCallBreakerOpenListener(new Runnable(null, callback){
            private final Function0 callback$7;

            public void run() {
                this.callback$7.apply$mcV$sp();
            }
            {
                this.callback$7 = callback$7;
            }
        });
    }

    public CircuitBreaker addOnCallBreakerOpenListener(Runnable callback) {
        this.callBreakerOpenListeners().add(callback);
        return this;
    }

    public int currentFailureCount() {
        return this.akka$pattern$CircuitBreaker$$Closed().get();
    }

    private void transition(State fromState, State toState) {
        if (this.swapState(fromState, toState)) {
            toState.enter();
            return;
        }
    }

    public void akka$pattern$CircuitBreaker$$tripBreaker(State fromState) {
        this.transition(fromState, this.Open());
    }

    public void akka$pattern$CircuitBreaker$$resetBreaker() {
        this.transition(this.akka$pattern$CircuitBreaker$$HalfOpen(), this.akka$pattern$CircuitBreaker$$Closed());
    }

    public void akka$pattern$CircuitBreaker$$notifyCallSuccessListeners(long start2) {
        if (!this.successListeners().isEmpty()) {
            long elapsed = System.nanoTime() - start2;
            for (Consumer<Object> listener : this.successListeners()) {
                this.akka$pattern$CircuitBreaker$$executor.execute(new Runnable(null, listener, elapsed){
                    private final Consumer listener$1;
                    private final long elapsed$1;

                    public void run() {
                        this.listener$1.accept(BoxesRunTime.boxToLong(this.elapsed$1));
                    }
                    {
                        this.listener$1 = listener$1;
                        this.elapsed$1 = elapsed$1;
                    }
                });
            }
            return;
        }
    }

    public void akka$pattern$CircuitBreaker$$notifyCallFailureListeners(long start2) {
        if (!this.callFailureListeners().isEmpty()) {
            long elapsed = System.nanoTime() - start2;
            for (Consumer<Object> listener : this.callFailureListeners()) {
                this.akka$pattern$CircuitBreaker$$executor.execute(new Runnable(null, listener, elapsed){
                    private final Consumer listener$2;
                    private final long elapsed$2;

                    public void run() {
                        this.listener$2.accept(BoxesRunTime.boxToLong(this.elapsed$2));
                    }
                    {
                        this.listener$2 = listener$2;
                        this.elapsed$2 = elapsed$2;
                    }
                });
            }
            return;
        }
    }

    public void akka$pattern$CircuitBreaker$$notifyCallTimeoutListeners(long start2) {
        if (!this.callTimeoutListeners().isEmpty()) {
            long elapsed = System.nanoTime() - start2;
            for (Consumer<Object> listener : this.callTimeoutListeners()) {
                this.akka$pattern$CircuitBreaker$$executor.execute(new Runnable(null, listener, elapsed){
                    private final Consumer listener$3;
                    private final long elapsed$3;

                    public void run() {
                        this.listener$3.accept(BoxesRunTime.boxToLong(this.elapsed$3));
                    }
                    {
                        this.listener$3 = listener$3;
                        this.elapsed$3 = elapsed$3;
                    }
                });
            }
            return;
        }
    }

    public void akka$pattern$CircuitBreaker$$notifyCallBreakerOpenListeners() {
        if (!this.callBreakerOpenListeners().isEmpty()) {
            for (Runnable listener : this.callBreakerOpenListeners()) {
                this.akka$pattern$CircuitBreaker$$executor.execute(listener);
            }
            return;
        }
    }

    public void akka$pattern$CircuitBreaker$$attemptReset() {
        this.transition(this.Open(), this.akka$pattern$CircuitBreaker$$HalfOpen());
    }

    public TimeoutException akka$pattern$CircuitBreaker$$timeoutEx() {
        return this.akka$pattern$CircuitBreaker$$timeoutEx;
    }

    private CopyOnWriteArrayList<Consumer<Object>> callFailureListeners() {
        return this.callFailureListeners;
    }

    private CopyOnWriteArrayList<Consumer<Object>> callTimeoutListeners() {
        return this.callTimeoutListeners;
    }

    private CopyOnWriteArrayList<Runnable> callBreakerOpenListeners() {
        return this.callBreakerOpenListeners;
    }

    private CopyOnWriteArrayList<Consumer<Object>> successListeners() {
        return this.successListeners;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean akka$pattern$CircuitBreaker$$isIgnoredException(Object ex) {
        if (!this.allowExceptions().nonEmpty()) return false;
        Object object = ex;
        if (object instanceof CompletionException) {
            CompletionException completionException = (CompletionException)object;
            if (completionException.getCause() == null) return false;
            if (!this.allowExceptions().contains(completionException.getCause().getClass().getName())) return false;
            return true;
        }
        boolean bl = this.allowExceptions().contains(ex.getClass().getName());
        if (!bl) return false;
        return true;
    }

    public Function1<Try<?>, Object> akka$pattern$CircuitBreaker$$failureFn() {
        return this.akka$pattern$CircuitBreaker$$failureFn;
    }

    private final void Closed$lzycompute$1() {
        CircuitBreaker circuitBreaker = this;
        synchronized (circuitBreaker) {
            if (this.Closed$module == null) {
                this.Closed$module = new CircuitBreaker$Closed$(this);
            }
        }
    }

    private final void HalfOpen$lzycompute$1() {
        CircuitBreaker circuitBreaker = this;
        synchronized (circuitBreaker) {
            if (this.HalfOpen$module == null) {
                this.HalfOpen$module = new CircuitBreaker$HalfOpen$(this);
            }
        }
    }

    private final void Open$lzycompute$1() {
        CircuitBreaker circuitBreaker = this;
        synchronized (circuitBreaker) {
            if (this.Open$module == null) {
                this.Open$module = new CircuitBreaker$Open$(this);
            }
        }
    }

    public static final /* synthetic */ boolean $anonfun$failureFn$1(CircuitBreaker $this, Try x0$1) {
        Failure failure;
        Throwable t2;
        Try try_ = x0$1;
        if (try_ instanceof Success) {
            return false;
        }
        return !(try_ instanceof Failure) || !$this.akka$pattern$CircuitBreaker$$isIgnoredException(t2 = (failure = (Failure)try_).exception());
    }

    public CircuitBreaker(Scheduler scheduler, int maxFailures, FiniteDuration callTimeout, FiniteDuration resetTimeout, FiniteDuration maxResetTimeout, double exponentialBackoffFactor, double randomFactor, Set<String> allowExceptions, CircuitBreakerTelemetry telemetry, ExecutionContext executor2) {
        Object object;
        this.akka$pattern$CircuitBreaker$$scheduler = scheduler;
        this.akka$pattern$CircuitBreaker$$maxFailures = maxFailures;
        this.akka$pattern$CircuitBreaker$$callTimeout = callTimeout;
        this.resetTimeout = resetTimeout;
        this.akka$pattern$CircuitBreaker$$maxResetTimeout = maxResetTimeout;
        this.akka$pattern$CircuitBreaker$$exponentialBackoffFactor = exponentialBackoffFactor;
        this.akka$pattern$CircuitBreaker$$randomFactor = randomFactor;
        this.allowExceptions = allowExceptions;
        this.telemetry = telemetry;
        this.akka$pattern$CircuitBreaker$$executor = executor2;
        Predef$.MODULE$.require(exponentialBackoffFactor >= 1.0, (Function0<Object>)(Function0<String> & Serializable)() -> "exponentialBackoffFactor must be >= 1.0");
        Predef$.MODULE$.require(0.0 <= randomFactor && randomFactor <= 1.0, (Function0<Object>)(Function0<String> & Serializable)() -> "randomFactor must be between 0.0 and 1.0");
        this._currentStateDoNotCallMeDirectly = this.akka$pattern$CircuitBreaker$$Closed();
        this._currentResetTimeoutDoNotCallMeDirectly = resetTimeout;
        this.akka$pattern$CircuitBreaker$$timeoutEx = new NoStackTrace(null){

            public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
                return super.fillInStackTrace();
            }

            public Throwable fillInStackTrace() {
                return NoStackTrace.fillInStackTrace$(this);
            }
            {
                NoStackTrace.$init$(this);
            }
        };
        this.callFailureListeners = new CopyOnWriteArrayList();
        this.callTimeoutListeners = new CopyOnWriteArrayList();
        this.callBreakerOpenListeners = new CopyOnWriteArrayList();
        this.successListeners = new CopyOnWriteArrayList();
        CircuitBreakerTelemetry circuitBreakerTelemetry = telemetry;
        CircuitBreakerNoopTelemetry$ circuitBreakerNoopTelemetry$ = CircuitBreakerNoopTelemetry$.MODULE$;
        if (circuitBreakerTelemetry == null ? circuitBreakerNoopTelemetry$ != null : !circuitBreakerTelemetry.equals(circuitBreakerNoopTelemetry$)) {
            this.onOpen(() -> this.telemetry().onOpen());
            this.onHalfOpen(() -> this.telemetry().onHalfOpen());
            this.onClose(() -> this.telemetry().onClose());
            this.onCallBreakerOpen(() -> this.telemetry().onCallBreakerOpenFailure());
            this.onCallTimeout(elapsedNanos -> this.telemetry().onCallTimeoutFailure(elapsedNanos));
            this.onCallFailure(elapsedNanos -> this.telemetry().onCallFailure(elapsedNanos));
            object = this.onCallSuccess(elapsedNanos -> this.telemetry().onCallSuccess(elapsedNanos));
        } else {
            object = BoxedUnit.UNIT;
        }
        this.akka$pattern$CircuitBreaker$$failureFn = (Function1<Try, Object> & Serializable)x0$1 -> BoxesRunTime.boxToBoolean(CircuitBreaker.$anonfun$failureFn$1(this, x0$1));
    }

    public CircuitBreaker(Scheduler scheduler, int maxFailures, FiniteDuration callTimeout, FiniteDuration resetTimeout, FiniteDuration maxResetTimeout, double exponentialBackoffFactor, double randomFactor, ExecutionContext executor2) {
        this(scheduler, maxFailures, callTimeout, resetTimeout, maxResetTimeout, exponentialBackoffFactor, randomFactor, (Set<String>)Predef$.MODULE$.Set().empty(), CircuitBreakerNoopTelemetry$.MODULE$, executor2);
    }

    public CircuitBreaker(ExecutionContext executor2, Scheduler scheduler, int maxFailures, Duration callTimeout, Duration resetTimeout) {
        this(scheduler, maxFailures, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(callTimeout)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(resetTimeout)), new package.DurationInt(package$.MODULE$.DurationInt(36500)).days(), 1.0, 0.0, executor2);
    }

    public CircuitBreaker(Scheduler scheduler, int maxFailures, FiniteDuration callTimeout, FiniteDuration resetTimeout, ExecutionContext executor2) {
        this(scheduler, maxFailures, callTimeout, resetTimeout, new package.DurationInt(package$.MODULE$.DurationInt(36500)).days(), 1.0, 0.0, executor2);
    }

    public CircuitBreaker(Scheduler scheduler, int maxFailures, FiniteDuration callTimeout, FiniteDuration resetTimeout, FiniteDuration maxResetTimeout, double exponentialBackoffFactor, ExecutionContext executor2) {
        this(scheduler, maxFailures, callTimeout, resetTimeout, maxResetTimeout, exponentialBackoffFactor, 0.0, executor2);
    }

    private interface State {
        public void akka$pattern$CircuitBreaker$State$_setter_$akka$pattern$CircuitBreaker$State$$listeners_$eq(CopyOnWriteArrayList<Runnable> var1);

        public CopyOnWriteArrayList<Runnable> akka$pattern$CircuitBreaker$State$$listeners();

        public static /* synthetic */ void addListener$(State $this, Runnable listener) {
            $this.addListener(listener);
        }

        default public void addListener(Runnable listener) {
            this.akka$pattern$CircuitBreaker$State$$listeners().add(listener);
        }

        private boolean hasListeners() {
            return !this.akka$pattern$CircuitBreaker$State$$listeners().isEmpty();
        }

        public static /* synthetic */ void notifyTransitionListeners$(State $this) {
            $this.notifyTransitionListeners();
        }

        default public void notifyTransitionListeners() {
            if (this.hasListeners()) {
                for (Runnable listener : this.akka$pattern$CircuitBreaker$State$$listeners()) {
                    this.akka$pattern$CircuitBreaker$State$$$outer().akka$pattern$CircuitBreaker$$executor.execute(listener);
                }
                return;
            }
        }

        public static /* synthetic */ Future callThrough$(State $this, Function0 body2, Function1 defineFailureFn) {
            return $this.callThrough(body2, defineFailureFn);
        }

        default public <T> Future<T> callThrough(Function0<Future<T>> body2, Function1<Try<T>, Object> defineFailureFn) {
            FiniteDuration finiteDuration = this.akka$pattern$CircuitBreaker$State$$$outer().akka$pattern$CircuitBreaker$$callTimeout;
            FiniteDuration finiteDuration2 = Duration$.MODULE$.Zero();
            if (!(finiteDuration != null ? !((Object)finiteDuration).equals(finiteDuration2) : finiteDuration2 != null)) {
                long start2 = System.nanoTime();
                Future f = State.materialize$1(body2);
                f.onComplete((Function1<Try, Object> & Serializable)x0$1 -> {
                    State.$anonfun$callThrough$1(this, start2, x0$1);
                    return BoxedUnit.UNIT;
                }, this.akka$pattern$CircuitBreaker$State$$$outer().akka$pattern$CircuitBreaker$$executor);
                return f;
            }
            long start3 = System.nanoTime();
            Promise p = Promise$.MODULE$.apply();
            p.future().onComplete((Function1<Try, Object> & Serializable)fResult -> {
                State.$anonfun$callThrough$2(this, defineFailureFn, start3, fResult);
                return BoxedUnit.UNIT;
            }, ExecutionContexts$.MODULE$.parasitic());
            Cancellable timeout2 = this.akka$pattern$CircuitBreaker$State$$$outer().akka$pattern$CircuitBreaker$$scheduler.scheduleOnce(this.akka$pattern$CircuitBreaker$State$$$outer().akka$pattern$CircuitBreaker$$callTimeout, () -> {
                if (p.tryFailure(this.akka$pattern$CircuitBreaker$State$$$outer().akka$pattern$CircuitBreaker$$timeoutEx())) {
                    this.akka$pattern$CircuitBreaker$State$$$outer().akka$pattern$CircuitBreaker$$notifyCallTimeoutListeners(start3);
                    return;
                }
            }, ExecutionContexts$.MODULE$.parasitic());
            State.materialize$1(body2).onComplete((Function1<Try, Object> & Serializable)x0$2 -> BoxesRunTime.boxToBoolean(State.$anonfun$callThrough$4(this, p, timeout2, start3, x0$2)), ExecutionContexts$.MODULE$.parasitic());
            return p.future();
        }

        public static /* synthetic */ Future callThrough$(State $this, Function0 body2) {
            return $this.callThrough(body2);
        }

        default public <T> Future<T> callThrough(Function0<Future<T>> body2) {
            return this.callThrough(body2, this.akka$pattern$CircuitBreaker$State$$$outer().akka$pattern$CircuitBreaker$$failureFn());
        }

        public <T> Future<T> invoke(Function0<Future<T>> var1, Function1<Try<T>, Object> var2);

        public static /* synthetic */ Future invoke$(State $this, Function0 body2) {
            return $this.invoke(body2);
        }

        default public <T> Future<T> invoke(Function0<Future<T>> body2) {
            return this.invoke(body2, this.akka$pattern$CircuitBreaker$State$$$outer().akka$pattern$CircuitBreaker$$failureFn());
        }

        public void callSucceeds();

        public void callFails();

        public static /* synthetic */ void enter$(State $this) {
            $this.enter();
        }

        default public void enter() {
            this._enter();
            this.notifyTransitionListeners();
        }

        public void _enter();

        public /* synthetic */ CircuitBreaker akka$pattern$CircuitBreaker$State$$$outer();

        private static Future materialize$1(Function0 value) {
            Future future;
            try {
                future = (Future)value.apply();
            }
            catch (Throwable throwable) {
                Option<Throwable> option;
                Throwable throwable2 = throwable;
                if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                    Throwable t2 = option.get();
                    future = Future$.MODULE$.failed(t2);
                }
                throw throwable;
            }
            return future;
        }

        public static /* synthetic */ void $anonfun$callThrough$1(State $this, long start$1, Try x0$1) {
            Try try_ = x0$1;
            if (try_ instanceof Success) {
                $this.akka$pattern$CircuitBreaker$State$$$outer().akka$pattern$CircuitBreaker$$notifyCallSuccessListeners(start$1);
                $this.callSucceeds();
                return;
            }
            if (try_ instanceof Failure) {
                $this.akka$pattern$CircuitBreaker$State$$$outer().akka$pattern$CircuitBreaker$$notifyCallFailureListeners(start$1);
                $this.callFails();
                return;
            }
            throw new MatchError(try_);
        }

        public static /* synthetic */ void $anonfun$callThrough$2(State $this, Function1 defineFailureFn$1, long start$2, Try fResult) {
            if (BoxesRunTime.unboxToBoolean(defineFailureFn$1.apply(fResult))) {
                $this.callFails();
                return;
            }
            $this.akka$pattern$CircuitBreaker$State$$$outer().akka$pattern$CircuitBreaker$$notifyCallSuccessListeners(start$2);
            $this.callSucceeds();
        }

        public static /* synthetic */ boolean $anonfun$callThrough$4(State $this, Promise p$1, Cancellable timeout$1, long start$2, Try x0$2) {
            Try try_ = x0$2;
            if (try_ instanceof Success) {
                Success success = (Success)try_;
                Object result = success.value();
                p$1.trySuccess(result);
                return timeout$1.cancel();
            }
            if (try_ instanceof Failure) {
                Failure failure = (Failure)try_;
                Throwable ex = failure.exception();
                if (p$1.tryFailure(ex) && !$this.akka$pattern$CircuitBreaker$State$$$outer().akka$pattern$CircuitBreaker$$isIgnoredException(ex)) {
                    $this.akka$pattern$CircuitBreaker$State$$$outer().akka$pattern$CircuitBreaker$$notifyCallFailureListeners(start$2);
                }
                return timeout$1.cancel();
            }
            throw new MatchError(try_);
        }

        public static void $init$(State $this) {
            $this.akka$pattern$CircuitBreaker$State$_setter_$akka$pattern$CircuitBreaker$State$$listeners_$eq(new CopyOnWriteArrayList<Runnable>());
        }
    }
}

