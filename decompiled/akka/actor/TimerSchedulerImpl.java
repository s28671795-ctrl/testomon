/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.NoSerializationVerificationNeeded;
import akka.actor.NotInfluenceReceiveTimeout;
import akka.actor.Scheduler;
import akka.actor.TimerScheduler;
import akka.actor.TimerSchedulerImpl$SingleMode$;
import akka.annotation.InternalApi;
import akka.event.LogSource$;
import akka.event.Logging$;
import akka.event.LoggingAdapter;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.collection.Iterator;
import scala.collection.immutable.Map;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0011\u001dxACA6\u0003[B\t!!\u001d\u0002v\u0019Q\u0011\u0011PA7\u0011\u0003\t\t(a\u001f\t\u000f\u0005%\u0015\u0001\"\u0001\u0002\u000e\u001aI\u0011qR\u0001\u0011\u0002G\u0005\u0012\u0011\u0013\u0005\b\u0003'\u001ba\u0011AAK\u0011\u001d\tij\u0001D\u0001\u0003?Cq!a*\u0004\r\u0003\tIK\u0002\u0004\u0002r\u0006\u0011\u00151\u001f\u0005\u000b\u0003';!Q3A\u0005\u0002\u0005U\u0005B\u0003B\u0007\u000f\tE\t\u0015!\u0003\u0002\u0018\"Q!qB\u0004\u0003\u0016\u0004%\t!!&\t\u0015\tEqA!E!\u0002\u0013\t9\n\u0003\u0006\u0003\u0014\u001d\u0011)\u001a!C\u0001\u0005+A!B!\b\b\u0005#\u0005\u000b\u0011\u0002B\f\u0011)\tij\u0002BK\u0002\u0013\u0005\u0011q\u0014\u0005\u000b\u0005?9!\u0011#Q\u0001\n\u0005\u0005\u0006B\u0003B\u0011\u000f\tU\r\u0011\"\u0001\u0003$!Q!1F\u0004\u0003\u0012\u0003\u0006IA!\n\t\u000f\u0005%u\u0001\"\u0001\u0003.!I!QH\u0004\u0002\u0002\u0013\u0005!q\b\u0005\n\u0005\u0017:\u0011\u0013!C\u0001\u0005\u001bB\u0011Ba\u0019\b#\u0003%\tA!\u0014\t\u0013\t\u0015t!%A\u0005\u0002\t\u001d\u0004\"\u0003B6\u000fE\u0005I\u0011\u0001B7\u0011%\u0011\thBI\u0001\n\u0003\u0011\u0019\bC\u0005\u0003x\u001d\t\t\u0011\"\u0011\u0003z!I!1R\u0004\u0002\u0002\u0013\u0005\u0011q\u0014\u0005\n\u0005\u001b;\u0011\u0011!C\u0001\u0005\u001fC\u0011B!&\b\u0003\u0003%\tEa&\t\u0013\t\u0015v!!A\u0005\u0002\t\u001d\u0006\"\u0003BV\u000f\u0005\u0005I\u0011\tBW\u0011%\u0011\tlBA\u0001\n\u0003\u0012\u0019\fC\u0005\u00036\u001e\t\t\u0011\"\u0011\u00038\"I!\u0011X\u0004\u0002\u0002\u0013\u0005#1X\u0004\n\t\u007f\t\u0011\u0011!E\u0001\t\u00032\u0011\"!=\u0002\u0003\u0003E\t\u0001b\u0011\t\u000f\u0005%5\u0005\"\u0001\u0005\\!I!QW\u0012\u0002\u0002\u0013\u0015#q\u0017\u0005\n\t;\u001a\u0013\u0011!CA\t?B\u0011\u0002b\u001b$\u0003\u0003%\t\t\"\u001c\t\u0013\re5%!A\u0005\n\rmeABBp\u0003\t\u001b\t\u000f\u0003\u0006\u0002\u0014&\u0012)\u001a!C\u0001\u0003+C!B!\u0004*\u0005#\u0005\u000b\u0011BAL\u0011)\ti*\u000bBK\u0002\u0013\u0005\u0011q\u0014\u0005\u000b\u0005?I#\u0011#Q\u0001\n\u0005\u0005\u0006BCATS\tU\r\u0011\"\u0001\u0002*\"Q11^\u0015\u0003\u0012\u0003\u0006I!a+\t\u000f\u0005%\u0015\u0006\"\u0001\u0004n\"I!QH\u0015\u0002\u0002\u0013\u00051q\u001f\u0005\n\u0005\u0017J\u0013\u0013!C\u0001\u0005\u001bB\u0011Ba\u0019*#\u0003%\tA!\u001c\t\u0013\t\u0015\u0014&%A\u0005\u0002\r}\b\"\u0003B<S\u0005\u0005I\u0011\tB=\u0011%\u0011Y)KA\u0001\n\u0003\ty\nC\u0005\u0003\u000e&\n\t\u0011\"\u0001\u0005\u0004!I!QS\u0015\u0002\u0002\u0013\u0005#q\u0013\u0005\n\u0005KK\u0013\u0011!C\u0001\t\u000fA\u0011Ba+*\u0003\u0003%\t\u0005b\u0003\t\u0013\tE\u0016&!A\u0005B\tM\u0006\"\u0003B[S\u0005\u0005I\u0011\tB\\\u0011%\u0011I,KA\u0001\n\u0003\"yaB\u0005\u0005\u0000\u0005\t\t\u0011#\u0001\u0005\u0002\u001aI1q\\\u0001\u0002\u0002#\u0005A1\u0011\u0005\b\u0003\u0013{D\u0011\u0001CF\u0011%\u0011)lPA\u0001\n\u000b\u00129\fC\u0005\u0005^}\n\t\u0011\"!\u0005\u000e\"IA1N \u0002\u0002\u0013\u0005EQ\u0013\u0005\n\u00073{\u0014\u0011!C\u0005\u000773a\u0001b\u0005\u0002\u0005\u0012U\u0001BCAJ\u000b\nU\r\u0011\"\u0001\u0002\u0016\"Q!QB#\u0003\u0012\u0003\u0006I!a&\t\u0015\u0005uUI!f\u0001\n\u0003\ty\n\u0003\u0006\u0003 \u0015\u0013\t\u0012)A\u0005\u0003CC!\"a*F\u0005+\u0007I\u0011AAU\u0011)\u0019Y/\u0012B\tB\u0003%\u00111\u0016\u0005\b\u0003\u0013+E\u0011\u0001C\u000f\u0011%\u0011i$RA\u0001\n\u0003!9\u0003C\u0005\u0003L\u0015\u000b\n\u0011\"\u0001\u0003N!I!1M#\u0012\u0002\u0013\u0005!Q\u000e\u0005\n\u0005K*\u0015\u0013!C\u0001\u0007\u007fD\u0011Ba\u001eF\u0003\u0003%\tE!\u001f\t\u0013\t-U)!A\u0005\u0002\u0005}\u0005\"\u0003BG\u000b\u0006\u0005I\u0011\u0001C\u0018\u0011%\u0011)*RA\u0001\n\u0003\u00129\nC\u0005\u0003&\u0016\u000b\t\u0011\"\u0001\u00054!I!1V#\u0002\u0002\u0013\u0005Cq\u0007\u0005\n\u0005c+\u0015\u0011!C!\u0005gC\u0011B!.F\u0003\u0003%\tEa.\t\u0013\teV)!A\u0005B\u0011mr!\u0003CQ\u0003\u0005\u0005\t\u0012\u0001CR\r%!\u0019\"AA\u0001\u0012\u0003!)\u000bC\u0004\u0002\nn#\t\u0001\"+\t\u0013\tU6,!A\u0005F\t]\u0006\"\u0003C/7\u0006\u0005I\u0011\u0011CV\u0011%!YgWA\u0001\n\u0003#\u0019\fC\u0005\u0004\u001an\u000b\t\u0011\"\u0003\u0004\u001c\u001aI1QH\u0001\u0011\u0002G%2q\b\u0005\b\u0005'\tg\u0011\u0001B\u000b\r\u0019\u0019Y'\u0001#\u0004n!Q!Q`2\u0003\u0016\u0004%\ta!\u0013\t\u0015\r-3M!E!\u0002\u0013\u0011)\u000fC\u0004\u0002\n\u000e$\taa\u001c\t\u000f\tM1\r\"\u0011\u0003\u0016!I!QH2\u0002\u0002\u0013\u00051Q\u000f\u0005\n\u0005\u0017\u001a\u0017\u0013!C\u0001\u0007/B\u0011Ba\u001ed\u0003\u0003%\tE!\u001f\t\u0013\t-5-!A\u0005\u0002\u0005}\u0005\"\u0003BGG\u0006\u0005I\u0011AB=\u0011%\u0011)jYA\u0001\n\u0003\u00129\nC\u0005\u0003&\u000e\f\t\u0011\"\u0001\u0004~!I!1V2\u0002\u0002\u0013\u00053\u0011\u0011\u0005\n\u0005c\u001b\u0017\u0011!C!\u0005gC\u0011B!.d\u0003\u0003%\tEa.\t\u0013\te6-!A\u0005B\r\u0015u!\u0003C\\\u0003\u0005\u0005\t\u0012\u0002C]\r%\u0019Y'AA\u0001\u0012\u0013!Y\fC\u0004\u0002\nR$\t\u0001b1\t\u0013\tUF/!A\u0005F\t]\u0006\"\u0003C/i\u0006\u0005I\u0011\u0011Cc\u0011%!Y\u0007^A\u0001\n\u0003#I\rC\u0005\u0004\u001aR\f\t\u0011\"\u0003\u0004\u001c\u001a111I\u0001E\u0007\u000bB!B!@{\u0005+\u0007I\u0011AB%\u0011)\u0019YE\u001fB\tB\u0003%!Q\u001d\u0005\b\u0003\u0013SH\u0011AB'\u0011\u001d\u0011\u0019B\u001fC!\u0005+A\u0011B!\u0010{\u0003\u0003%\taa\u0015\t\u0013\t-#0%A\u0005\u0002\r]\u0003\"\u0003B<u\u0006\u0005I\u0011\tB=\u0011%\u0011YI_A\u0001\n\u0003\ty\nC\u0005\u0003\u000ej\f\t\u0011\"\u0001\u0004\\!I!Q\u0013>\u0002\u0002\u0013\u0005#q\u0013\u0005\n\u0005KS\u0018\u0011!C\u0001\u0007?B\u0011Ba+{\u0003\u0003%\tea\u0019\t\u0013\tE&0!A\u0005B\tM\u0006\"\u0003B[u\u0006\u0005I\u0011\tB\\\u0011%\u0011IL_A\u0001\n\u0003\u001a9gB\u0005\u0005P\u0006\t\t\u0011#\u0003\u0005R\u001aI11I\u0001\u0002\u0002#%A1\u001b\u0005\t\u0003\u0013\u000b9\u0002\"\u0001\u0005X\"Q!QWA\f\u0003\u0003%)Ea.\t\u0015\u0011u\u0013qCA\u0001\n\u0003#I\u000e\u0003\u0006\u0005l\u0005]\u0011\u0011!CA\t;D!b!'\u0002\u0018\u0005\u0005I\u0011BBN\u000f\u001d!\t/\u0001EE\u0007\u001f3qa!#\u0002\u0011\u0013\u001bY\t\u0003\u0005\u0002\n\u0006\u0015B\u0011ABG\u0011!\u0011\u0019\"!\n\u0005B\tU\u0001B\u0003B<\u0003K\t\t\u0011\"\u0011\u0003z!Q!1RA\u0013\u0003\u0003%\t!a(\t\u0015\t5\u0015QEA\u0001\n\u0003\u0019\t\n\u0003\u0006\u0003\u0016\u0006\u0015\u0012\u0011!C!\u0005/C!B!*\u0002&\u0005\u0005I\u0011ABK\u0011)\u0011\t,!\n\u0002\u0002\u0013\u0005#1\u0017\u0005\u000b\u0005k\u000b)#!A\u0005B\t]\u0006BCBM\u0003K\t\t\u0011\"\u0003\u0004\u001c\u001aI\u0011\u0011PA7\u0001\u0005E\u0014Q\u0016\u0005\f\u0003k\u000bYD!A!\u0002\u0013\t9\f\u0003\u0005\u0002\n\u0006mB\u0011AA_\u0011)\t\t-a\u000fC\u0002\u0013%\u00111\u0019\u0005\n\u0003#\fY\u0004)A\u0005\u0003\u000bD!\"a5\u0002<\u0001\u0007I\u0011BAk\u0011)\u0011y,a\u000fA\u0002\u0013%!\u0011\u0019\u0005\n\u0005\u0017\fY\u0004)Q\u0005\u0003/D!B!4\u0002<\u0001\u0007I\u0011BAP\u0011)\u0011y-a\u000fA\u0002\u0013%!\u0011\u001b\u0005\n\u0005+\fY\u0004)Q\u0005\u0003CC\u0001Ba6\u0002<\u0011%!1\u0017\u0005\t\u00053\fY\u0004\"\u0011\u0003\\\"A!\u0011\\A\u001e\t\u0003\u0012)\u0010\u0003\u0005\u0004\u0002\u0005mB\u0011IB\u0002\u0011!\u0019\t!a\u000f\u0005B\r5\u0001\u0002CB\f\u0003w!\te!\u0007\t\u0011\r\u0005\u00121\bC!\u0007GA\u0001b!\f\u0002<\u0011%1q\u0006\u0005\t\u0007G\u000bY\u0004\"\u0011\u0004&\"A1\u0011VA\u001e\t\u0003\u001aY\u000b\u0003\u0005\u00040\u0006mB\u0011BBY\u0011!\u00199,a\u000f\u0005B\re\u0006\u0002CB^\u0003w!\ta!0\u0002%QKW.\u001a:TG\",G-\u001e7fe&k\u0007\u000f\u001c\u0006\u0005\u0003_\n\t(A\u0003bGR|'O\u0003\u0002\u0002t\u0005!\u0011m[6b!\r\t9(A\u0007\u0003\u0003[\u0012!\u0003V5nKJ\u001c6\r[3ek2,'/S7qYN\u0019\u0011!! \u0011\t\u0005}\u0014QQ\u0007\u0003\u0003\u0003S!!a!\u0002\u000bM\u001c\u0017\r\\1\n\t\u0005\u001d\u0015\u0011\u0011\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\"!!\u001e\u0003\u0011QKW.\u001a:Ng\u001e\u001c2aAA?\u0003\rYW-_\u000b\u0003\u0003/\u0003B!a \u0002\u001a&!\u00111TAA\u0005\r\te._\u0001\u000bO\u0016tWM]1uS>tWCAAQ!\u0011\ty(a)\n\t\u0005\u0015\u0016\u0011\u0011\u0002\u0004\u0013:$\u0018!B8x]\u0016\u0014XCAAV!\u0011\t9(a\u000f\u0014\t\u0005m\u0012q\u0016\t\u0005\u0003o\n\t,\u0003\u0003\u00024\u00065$A\u0004+j[\u0016\u00148k\u00195fIVdWM]\u0001\u0004GRD\b\u0003BA<\u0003sKA!a/\u0002n\ta\u0011i\u0019;pe\u000e{g\u000e^3yiR!\u00111VA`\u0011!\t),a\u0010A\u0002\u0005]\u0016a\u00017pOV\u0011\u0011Q\u0019\t\u0005\u0003\u000f\fi-\u0004\u0002\u0002J*!\u00111ZA9\u0003\u0015)g/\u001a8u\u0013\u0011\ty-!3\u0003\u001d1{wmZ5oO\u0006#\u0017\r\u001d;fe\u0006!An\\4!\u0003\u0019!\u0018.\\3sgV\u0011\u0011q\u001b\t\t\u00033\f9/a&\u0002n:!\u00111\\Ar!\u0011\ti.!!\u000e\u0005\u0005}'\u0002BAq\u0003\u0017\u000ba\u0001\u0010:p_Rt\u0014\u0002BAs\u0003\u0003\u000ba\u0001\u0015:fI\u00164\u0017\u0002BAu\u0003W\u00141!T1q\u0015\u0011\t)/!!\u0011\u0007\u0005=xAD\u0002\u0002x\u0001\u0011Q\u0001V5nKJ\u001craBA?\u0003k\fY\u0010\u0005\u0003\u0002\u0000\u0005]\u0018\u0002BA}\u0003\u0003\u0013q\u0001\u0015:pIV\u001cG\u000f\u0005\u0003\u0002~\n\u001da\u0002BA\u0000\u0005\u0007qA!!8\u0003\u0002%\u0011\u00111Q\u0005\u0005\u0005\u000b\t\t)A\u0004qC\u000e\\\u0017mZ3\n\t\t%!1\u0002\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0005\u0005\u000b\t\t)\u0001\u0003lKf\u0004\u0013aA7tO\u0006!Qn]4!\u0003\u0019\u0011X\r]3biV\u0011!q\u0003\t\u0005\u0003\u007f\u0012I\"\u0003\u0003\u0003\u001c\u0005\u0005%a\u0002\"p_2,\u0017M\\\u0001\be\u0016\u0004X-\u0019;!\u0003-9WM\\3sCRLwN\u001c\u0011\u0002\tQ\f7o[\u000b\u0003\u0005K\u0001B!a\u001e\u0003(%!!\u0011FA7\u0005-\u0019\u0015M\\2fY2\f'\r\\3\u0002\u000bQ\f7o\u001b\u0011\u0015\u0019\t=\"1\u0007B\u001b\u0005o\u0011IDa\u000f\u0011\u0007\tEr!D\u0001\u0002\u0011\u001d\t\u0019J\u0005a\u0001\u0003/CqAa\u0004\u0013\u0001\u0004\t9\nC\u0004\u0003\u0014I\u0001\rAa\u0006\t\u000f\u0005u%\u00031\u0001\u0002\"\"9!\u0011\u0005\nA\u0002\t\u0015\u0012\u0001B2paf$BBa\f\u0003B\t\r#Q\tB$\u0005\u0013B\u0011\"a%\u0014!\u0003\u0005\r!a&\t\u0013\t=1\u0003%AA\u0002\u0005]\u0005\"\u0003B\n'A\u0005\t\u0019\u0001B\f\u0011%\tij\u0005I\u0001\u0002\u0004\t\t\u000bC\u0005\u0003\"M\u0001\n\u00111\u0001\u0003&\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nTC\u0001B(U\u0011\t9J!\u0015,\u0005\tM\u0003\u0003\u0002B+\u0005?j!Aa\u0016\u000b\t\te#1L\u0001\nk:\u001c\u0007.Z2lK\u0012TAA!\u0018\u0002\u0002\u0006Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\t\u0005$q\u000b\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00134+\t\u0011IG\u000b\u0003\u0003\u0018\tE\u0013AD2paf$C-\u001a4bk2$H\u0005N\u000b\u0003\u0005_RC!!)\u0003R\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012*TC\u0001B;U\u0011\u0011)C!\u0015\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\u0011Y\b\u0005\u0003\u0003~\t\u001dUB\u0001B@\u0015\u0011\u0011\tIa!\u0002\t1\fgn\u001a\u0006\u0003\u0005\u000b\u000bAA[1wC&!!\u0011\u0012B@\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5us\u0006q\u0001O]8ek\u000e$X\t\\3nK:$H\u0003BAL\u0005#C\u0011Ba%\u001c\u0003\u0003\u0005\r!!)\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\u0011I\n\u0005\u0004\u0003\u001c\n\u0005\u0016qS\u0007\u0003\u0005;SAAa(\u0002\u0002\u0006Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\t\r&Q\u0014\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0003\u0018\t%\u0006\"\u0003BJ;\u0005\u0005\t\u0019AAL\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\tm$q\u0016\u0005\n\u0005's\u0012\u0011!a\u0001\u0003C\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0003C\u000b\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0005w\na!Z9vC2\u001cH\u0003\u0002B\f\u0005{C\u0011Ba%\"\u0003\u0003\u0005\r!a&\u0002\u0015QLW.\u001a:t?\u0012*\u0017\u000f\u0006\u0003\u0003D\n%\u0007\u0003BA@\u0005\u000bLAAa2\u0002\u0002\n!QK\\5u\u0011)\u0011\u0019*a\u0012\u0002\u0002\u0003\u0007\u0011q[\u0001\bi&lWM]:!\u0003!!\u0018.\\3s\u000f\u0016t\u0017\u0001\u0004;j[\u0016\u0014x)\u001a8`I\u0015\fH\u0003\u0002Bb\u0005'D!Ba%\u0002N\u0005\u0005\t\u0019AAQ\u0003%!\u0018.\\3s\u000f\u0016t\u0007%\u0001\u0007oKb$H+[7fe\u001e+g.A\u000bti\u0006\u0014H\u000fV5nKJ\fEOR5yK\u0012\u0014\u0016\r^3\u0015\u0011\t\r'Q\u001cBp\u0005CD\u0001\"a%\u0002T\u0001\u0007\u0011q\u0013\u0005\t\u0005\u001f\t\u0019\u00061\u0001\u0002\u0018\"A!1]A*\u0001\u0004\u0011)/\u0001\u0005j]R,'O^1m!\u0011\u00119O!=\u000e\u0005\t%(\u0002\u0002Bv\u0005[\f\u0001\u0002Z;sCRLwN\u001c\u0006\u0005\u0005_\f\t)\u0001\u0006d_:\u001cWO\u001d:f]RLAAa=\u0003j\nqa)\u001b8ji\u0016$UO]1uS>tGC\u0003Bb\u0005o\u0014IPa?\u0003\u0000\"A\u00111SA+\u0001\u0004\t9\n\u0003\u0005\u0003\u0010\u0005U\u0003\u0019AAL\u0011!\u0011i0!\u0016A\u0002\t\u0015\u0018\u0001D5oSRL\u0017\r\u001c#fY\u0006L\b\u0002\u0003Br\u0003+\u0002\rA!:\u00021M$\u0018M\u001d;US6,'oV5uQ\u001aK\u00070\u001a3EK2\f\u0017\u0010\u0006\u0005\u0003D\u000e\u00151qAB\u0005\u0011!\t\u0019*a\u0016A\u0002\u0005]\u0005\u0002\u0003B\b\u0003/\u0002\r!a&\t\u0011\r-\u0011q\u000ba\u0001\u0005K\fQ\u0001Z3mCf$\"Ba1\u0004\u0010\rE11CB\u000b\u0011!\t\u0019*!\u0017A\u0002\u0005]\u0005\u0002\u0003B\b\u00033\u0002\r!a&\t\u0011\tu\u0018\u0011\fa\u0001\u0005KD\u0001ba\u0003\u0002Z\u0001\u0007!Q]\u0001\u0013gR\f'\u000f\u001e)fe&|G-[2US6,'\u000f\u0006\u0005\u0003D\u000em1QDB\u0010\u0011!\t\u0019*a\u0017A\u0002\u0005]\u0005\u0002\u0003B\b\u00037\u0002\r!a&\t\u0011\t\r\u00181\fa\u0001\u0005K\f\u0001c\u001d;beR\u001c\u0016N\\4mKRKW.\u001a:\u0015\u0011\t\r7QEB\u0014\u0007SA\u0001\"a%\u0002^\u0001\u0007\u0011q\u0013\u0005\t\u0005\u001f\ti\u00061\u0001\u0002\u0018\"A11FA/\u0001\u0004\u0011)/A\u0004uS6,w.\u001e;\u0002\u0015M$\u0018M\u001d;US6,'\u000f\u0006\u0006\u0003D\u000eE21GB\u001b\u0007oA\u0001\"a%\u0002`\u0001\u0007\u0011q\u0013\u0005\t\u0005\u001f\ty\u00061\u0001\u0002\u0018\"A11FA0\u0001\u0004\u0011)\u000f\u0003\u0005\u0004:\u0005}\u0003\u0019AB\u001e\u0003\u0011iw\u000eZ3\u0011\u0007\u0005=\u0018MA\u0005US6,'/T8eKN\u0019\u0011-! *\u000b\u0005T8-!\n\u0003\u001d\u0019K\u00070\u001a3EK2\f\u00170T8eKNI!0! \u0004H\u0005U\u00181 \t\u0004\u0005c\tWC\u0001Bs\u00035Ig.\u001b;jC2$U\r\\1zAQ!1qJB)!\r\u0011\tD\u001f\u0005\b\u0005{l\b\u0019\u0001Bs)\u0011\u0019ye!\u0016\t\u0013\tux\u0010%AA\u0002\t\u0015XCAB-U\u0011\u0011)O!\u0015\u0015\t\u0005]5Q\f\u0005\u000b\u0005'\u000b9!!AA\u0002\u0005\u0005F\u0003\u0002B\f\u0007CB!Ba%\u0002\f\u0005\u0005\t\u0019AAL)\u0011\u0011Yh!\u001a\t\u0015\tM\u0015QBA\u0001\u0002\u0004\t\t\u000b\u0006\u0003\u0003\u0018\r%\u0004B\u0003BJ\u0003'\t\t\u00111\u0001\u0002\u0018\nia)\u001b=fIJ\u000bG/Z'pI\u0016\u001c\u0012bYA?\u0007\u000f\n)0a?\u0015\t\rE41\u000f\t\u0004\u0005c\u0019\u0007b\u0002B\u007fM\u0002\u0007!Q\u001d\u000b\u0005\u0007c\u001a9\bC\u0005\u0003~\"\u0004\n\u00111\u0001\u0003fR!\u0011qSB>\u0011%\u0011\u0019\n\\A\u0001\u0002\u0004\t\t\u000b\u0006\u0003\u0003\u0018\r}\u0004\"\u0003BJ]\u0006\u0005\t\u0019AAL)\u0011\u0011Yha!\t\u0013\tMu.!AA\u0002\u0005\u0005F\u0003\u0002B\f\u0007\u000fC\u0011Ba%s\u0003\u0003\u0005\r!a&\u0003\u0015MKgn\u001a7f\u001b>$Wm\u0005\u0006\u0002&\u0005u4qIA{\u0003w$\"aa$\u0011\t\tE\u0012Q\u0005\u000b\u0005\u0003/\u001b\u0019\n\u0003\u0006\u0003\u0014\u0006=\u0012\u0011!a\u0001\u0003C#BAa\u0006\u0004\u0018\"Q!1SA\u001a\u0003\u0003\u0005\r!a&\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\ru\u0005\u0003\u0002B?\u0007?KAa!)\u0003\u0000\t1qJ\u00196fGR\fQ\"[:US6,'/Q2uSZ,G\u0003\u0002B\f\u0007OC\u0001\"a%\u0002b\u0001\u0007\u0011qS\u0001\u0007G\u0006t7-\u001a7\u0015\t\t\r7Q\u0016\u0005\t\u0003'\u000b\u0019\u00071\u0001\u0002\u0018\u0006Y1-\u00198dK2$\u0016.\\3s)\u0011\u0011\u0019ma-\t\u0011\rU\u0016Q\ra\u0001\u0003[\fQ\u0001^5nKJ\f\u0011bY1oG\u0016d\u0017\t\u001c7\u0015\u0005\t\r\u0017!E5oi\u0016\u00148-\u001a9u)&lWM]'tOR!1qXBf!\u0019\u0019\tma2\u0002~5\u001111\u0019\u0006\u0005\u0007\u000b\f\t(\u0001\u0003vi&d\u0017\u0002BBe\u0007\u0007\u0014\u0011b\u00149uS>tg+\u00197\t\u0011\r5\u0017\u0011\u000ea\u0001\u0007\u001f\f\u0001\u0002^5nKJl5o\u001a\t\u0004\u0003_\u001c\u0001\u0006BA\u001e\u0007'\u0004Ba!6\u0004Z6\u00111q\u001b\u0006\u0005\u0005;\n\t(\u0003\u0003\u0004\\\u000e]'aC%oi\u0016\u0014h.\u00197Ba&L3aA\u0015F\u0005}IeN\u001a7vK:\u001cWMU3dK&4X\rV5nK>,H\u000fV5nKJl5oZ\n\fS\u0005u41]Bs\u0003k\fY\u0010E\u0002\u00032\r\u0001B!a\u001e\u0004h&!1\u0011^A7\u0005\u0005runU3sS\u0006d\u0017N_1uS>tg+\u001a:jM&\u001c\u0017\r^5p]:+W\rZ3e\u0003\u0019ywO\\3sAQA1q^By\u0007g\u001c)\u0010E\u0002\u00032%Bq!a%1\u0001\u0004\t9\nC\u0004\u0002\u001eB\u0002\r!!)\t\u000f\u0005\u001d\u0006\u00071\u0001\u0002,RA1q^B}\u0007w\u001ci\u0010C\u0005\u0002\u0014F\u0002\n\u00111\u0001\u0002\u0018\"I\u0011QT\u0019\u0011\u0002\u0003\u0007\u0011\u0011\u0015\u0005\n\u0003O\u000b\u0004\u0013!a\u0001\u0003W+\"\u0001\"\u0001+\t\u0005-&\u0011\u000b\u000b\u0005\u0003/#)\u0001C\u0005\u0003\u0014^\n\t\u00111\u0001\u0002\"R!!q\u0003C\u0005\u0011%\u0011\u0019*OA\u0001\u0002\u0004\t9\n\u0006\u0003\u0003|\u00115\u0001\"\u0003BJu\u0005\u0005\t\u0019AAQ)\u0011\u00119\u0002\"\u0005\t\u0013\tMU(!AA\u0002\u0005]%A\t(pi&sg\r\\;f]\u000e,'+Z2fSZ,G+[7f_V$H+[7fe6\u001bxmE\u0007F\u0003{\u001a\u0019o!:\u0005\u0018\u0005U\u00181 \t\u0005\u0003o\"I\"\u0003\u0003\u0005\u001c\u00055$A\u0007(pi&sg\r\\;f]\u000e,'+Z2fSZ,G+[7f_V$H\u0003\u0003C\u0010\tC!\u0019\u0003\"\n\u0011\u0007\tER\tC\u0004\u0002\u00142\u0003\r!a&\t\u000f\u0005uE\n1\u0001\u0002\"\"9\u0011q\u0015'A\u0002\u0005-F\u0003\u0003C\u0010\tS!Y\u0003\"\f\t\u0013\u0005MU\n%AA\u0002\u0005]\u0005\"CAO\u001bB\u0005\t\u0019AAQ\u0011%\t9+\u0014I\u0001\u0002\u0004\tY\u000b\u0006\u0003\u0002\u0018\u0012E\u0002\"\u0003BJ'\u0006\u0005\t\u0019AAQ)\u0011\u00119\u0002\"\u000e\t\u0013\tMU+!AA\u0002\u0005]E\u0003\u0002B>\tsA\u0011Ba%W\u0003\u0003\u0005\r!!)\u0015\t\t]AQ\b\u0005\n\u0005'K\u0016\u0011!a\u0001\u0003/\u000bQ\u0001V5nKJ\u00042A!\r$'\u0015\u0019CQ\tC)!A!9\u0005\"\u0014\u0002\u0018\u0006]%qCAQ\u0005K\u0011y#\u0004\u0002\u0005J)!A1JAA\u0003\u001d\u0011XO\u001c;j[\u0016LA\u0001b\u0014\u0005J\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u001b\u0011\t\u0011MC\u0011L\u0007\u0003\t+RA\u0001b\u0016\u0003\u0004\u0006\u0011\u0011n\\\u0005\u0005\u0005\u0013!)\u0006\u0006\u0002\u0005B\u0005)\u0011\r\u001d9msRa!q\u0006C1\tG\")\u0007b\u001a\u0005j!9\u00111\u0013\u0014A\u0002\u0005]\u0005b\u0002B\bM\u0001\u0007\u0011q\u0013\u0005\b\u0005'1\u0003\u0019\u0001B\f\u0011\u001d\tiJ\na\u0001\u0003CCqA!\t'\u0001\u0004\u0011)#A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0011=D1\u0010\t\u0007\u0003\u007f\"\t\b\"\u001e\n\t\u0011M\u0014\u0011\u0011\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u001d\u0005}DqOAL\u0003/\u00139\"!)\u0003&%!A\u0011PAA\u0005\u0019!V\u000f\u001d7fk!IAQP\u0014\u0002\u0002\u0003\u0007!qF\u0001\u0004q\u0012\u0002\u0014aH%oM2,XM\\2f%\u0016\u001cW-\u001b<f)&lWm\\;u)&lWM]'tOB\u0019!\u0011G \u0014\u000b}\")\t\"\u0015\u0011\u0019\u0011\u001dCqQAL\u0003C\u000bYka<\n\t\u0011%E\u0011\n\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\u001cDC\u0001CA)!\u0019y\u000fb$\u0005\u0012\u0012M\u0005bBAJ\u0005\u0002\u0007\u0011q\u0013\u0005\b\u0003;\u0013\u0005\u0019AAQ\u0011\u001d\t9K\u0011a\u0001\u0003W#B\u0001b&\u0005 B1\u0011q\u0010C9\t3\u0003\"\"a \u0005\u001c\u0006]\u0015\u0011UAV\u0013\u0011!i*!!\u0003\rQ+\b\u000f\\34\u0011%!ihQA\u0001\u0002\u0004\u0019y/\u0001\u0012O_RLeN\u001a7vK:\u001cWMU3dK&4X\rV5nK>,H\u000fV5nKJl5o\u001a\t\u0004\u0005cY6#B.\u0005(\u0012E\u0003\u0003\u0004C$\t\u000f\u000b9*!)\u0002,\u0012}AC\u0001CR)!!y\u0002\",\u00050\u0012E\u0006bBAJ=\u0002\u0007\u0011q\u0013\u0005\b\u0003;s\u0006\u0019AAQ\u0011\u001d\t9K\u0018a\u0001\u0003W#B\u0001b&\u00056\"IAQP0\u0002\u0002\u0003\u0007AqD\u0001\u000e\r&DX\r\u001a*bi\u0016lu\u000eZ3\u0011\u0007\tEBoE\u0003u\t{#\t\u0006\u0005\u0005\u0005H\u0011}&Q]B9\u0013\u0011!\t\r\"\u0013\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t\u0017\u0007\u0006\u0002\u0005:R!1\u0011\u000fCd\u0011\u001d\u0011ip\u001ea\u0001\u0005K$B\u0001b3\u0005NB1\u0011q\u0010C9\u0005KD\u0011\u0002\" y\u0003\u0003\u0005\ra!\u001d\u0002\u001d\u0019K\u00070\u001a3EK2\f\u00170T8eKB!!\u0011GA\f'\u0019\t9\u0002\"6\u0005RAAAq\tC`\u0005K\u001cy\u0005\u0006\u0002\u0005RR!1q\nCn\u0011!\u0011i0!\bA\u0002\t\u0015H\u0003\u0002Cf\t?D!\u0002\" \u0002 \u0005\u0005\t\u0019AB(\u0003)\u0019\u0016N\\4mK6{G-\u001a\u0015\u0004\u0003\rM\u0007f\u0001\u0001\u0004T\u0002")
@InternalApi
public class TimerSchedulerImpl
extends TimerScheduler {
    private final ActorContext ctx;
    private final LoggingAdapter log;
    private Map<Object, Timer> timers;
    private int timerGen;

    private LoggingAdapter log() {
        return this.log;
    }

    private Map<Object, Timer> timers() {
        return this.timers;
    }

    private void timers_$eq(Map<Object, Timer> x$1) {
        this.timers = x$1;
    }

    private int timerGen() {
        return this.timerGen;
    }

    private void timerGen_$eq(int x$1) {
        this.timerGen = x$1;
    }

    private int nextTimerGen() {
        this.timerGen_$eq(this.timerGen() + 1);
        return this.timerGen();
    }

    @Override
    public void startTimerAtFixedRate(Object key, Object msg, FiniteDuration interval) {
        this.startTimer(key, msg, interval, new FixedRateMode(interval));
    }

    @Override
    public void startTimerAtFixedRate(Object key, Object msg, FiniteDuration initialDelay, FiniteDuration interval) {
        this.startTimer(key, msg, interval, new FixedRateMode(initialDelay));
    }

    @Override
    public void startTimerWithFixedDelay(Object key, Object msg, FiniteDuration delay) {
        this.startTimer(key, msg, delay, new FixedDelayMode(delay));
    }

    @Override
    public void startTimerWithFixedDelay(Object key, Object msg, FiniteDuration initialDelay, FiniteDuration delay) {
        this.startTimer(key, msg, delay, new FixedDelayMode(initialDelay));
    }

    @Override
    public void startPeriodicTimer(Object key, Object msg, FiniteDuration interval) {
        this.startTimerAtFixedRate(key, msg, interval);
    }

    @Override
    public void startSingleTimer(Object key, Object msg, FiniteDuration timeout2) {
        this.startTimer(key, msg, timeout2, TimerSchedulerImpl$SingleMode$.MODULE$);
    }

    private void startTimer(Object key, Object msg, FiniteDuration timeout2, TimerMode mode) {
        Cancellable cancellable;
        Option option = this.timers().get(key);
        if (option instanceof Some) {
            Some some = (Some)option;
            Timer t2 = (Timer)some.value();
            this.cancelTimer(t2);
        } else if (None$.MODULE$.equals(option)) {
        } else {
            throw new MatchError(option);
        }
        int nextGen = this.nextTimerGen();
        NoSerializationVerificationNeeded timerMsg = msg instanceof NotInfluenceReceiveTimeout ? new NotInfluenceReceiveTimeoutTimerMsg(key, nextGen, this) : new InfluenceReceiveTimeoutTimerMsg(key, nextGen, this);
        TimerMode timerMode = mode;
        if (TimerSchedulerImpl$SingleMode$.MODULE$.equals(timerMode)) {
            Scheduler qual$1 = this.ctx.system().scheduler();
            FiniteDuration x$1 = timeout2;
            ActorRef x$2 = this.ctx.self();
            NoSerializationVerificationNeeded x$3 = timerMsg;
            ExecutionContextExecutor x$4 = this.ctx.dispatcher();
            ActorRef x$5 = qual$1.scheduleOnce$default$5(x$1, x$2, x$3);
            cancellable = qual$1.scheduleOnce(x$1, x$2, (Object)x$3, (ExecutionContext)x$4, x$5);
        } else if (timerMode instanceof FixedDelayMode) {
            FixedDelayMode fixedDelayMode = (FixedDelayMode)timerMode;
            Scheduler qual$2 = this.ctx.system().scheduler();
            FiniteDuration x$6 = fixedDelayMode.initialDelay();
            FiniteDuration x$7 = timeout2;
            ActorRef x$8 = this.ctx.self();
            NoSerializationVerificationNeeded x$9 = timerMsg;
            ExecutionContextExecutor x$10 = this.ctx.dispatcher();
            ActorRef x$11 = qual$2.scheduleWithFixedDelay$default$6(x$6, x$7, x$8, x$9);
            cancellable = qual$2.scheduleWithFixedDelay(x$6, x$7, x$8, (Object)x$9, (ExecutionContext)x$10, x$11);
        } else if (timerMode instanceof FixedRateMode) {
            FixedRateMode fixedRateMode = (FixedRateMode)timerMode;
            Scheduler qual$3 = this.ctx.system().scheduler();
            FiniteDuration x$12 = fixedRateMode.initialDelay();
            FiniteDuration x$13 = timeout2;
            ActorRef x$14 = this.ctx.self();
            NoSerializationVerificationNeeded x$15 = timerMsg;
            ExecutionContextExecutor x$16 = this.ctx.dispatcher();
            ActorRef x$17 = qual$3.scheduleAtFixedRate$default$6(x$12, x$13, x$14, x$15);
            cancellable = qual$3.scheduleAtFixedRate(x$12, x$13, x$14, (Object)x$15, (ExecutionContext)x$16, x$17);
        } else {
            throw new MatchError(timerMode);
        }
        Cancellable task = cancellable;
        Timer nextTimer = new Timer(key, msg, mode.repeat(), nextGen, task);
        this.timers_$eq((Map)this.timers().updated(key, nextTimer));
    }

    @Override
    public boolean isTimerActive(Object key) {
        return this.timers().contains(key);
    }

    @Override
    public void cancel(Object key) {
        Option option = this.timers().get(key);
        if (None$.MODULE$.equals(option)) {
            return;
        }
        if (option instanceof Some) {
            Some some = (Some)option;
            Timer t2 = (Timer)some.value();
            this.cancelTimer(t2);
            return;
        }
        throw new MatchError(option);
    }

    private void cancelTimer(Timer timer) {
        timer.task().cancel();
        this.timers_$eq((Map)this.timers().$minus(timer.key()));
    }

    @Override
    public void cancelAll() {
        this.timers().valuesIterator().foreach((Function1<Timer, Object> & Serializable)timer -> BoxesRunTime.boxToBoolean(TimerSchedulerImpl.$anonfun$cancelAll$1(timer)));
        this.timers_$eq((Map<Object, Timer>)Predef$.MODULE$.Map().empty());
    }

    public Object interceptTimerMsg(TimerMsg timerMsg) {
        Option option = this.timers().get(timerMsg.key());
        if (None$.MODULE$.equals(option)) {
            this.log().debug("Received timer [{}] that has been removed, discarding", timerMsg.key());
            OptionVal$.MODULE$.None();
            return null;
        }
        if (option instanceof Some) {
            Some some = (Some)option;
            Timer t2 = (Timer)some.value();
            if (timerMsg.owner() != this) {
                this.log().debug("Received timer [{}] from old restarted instance, discarding", timerMsg.key());
                OptionVal$.MODULE$.None();
                return null;
            }
            if (timerMsg.generation() == t2.generation()) {
                if (!t2.repeat()) {
                    this.timers_$eq((Map)this.timers().$minus(t2.key()));
                }
                return OptionVal$Some$.MODULE$.apply(t2.msg());
            }
            this.log().debug("Received timer [{}] from from old generation [{}], expected generation [{}], discarding", timerMsg.key(), BoxesRunTime.boxToInteger(timerMsg.generation()), BoxesRunTime.boxToInteger(t2.generation()));
            OptionVal$.MODULE$.None();
            return null;
        }
        throw new MatchError(option);
    }

    public static final /* synthetic */ boolean $anonfun$cancelAll$1(Timer timer) {
        return timer.task().cancel();
    }

    public TimerSchedulerImpl(ActorContext ctx) {
        this.ctx = ctx;
        this.log = Logging$.MODULE$.apply(ctx.system(), TimerScheduler.class, LogSource$.MODULE$.fromAnyClass());
        this.timers = Predef$.MODULE$.Map().empty();
        this.timerGen = 0;
    }

    private static class FixedDelayMode
    implements TimerMode,
    Product,
    Serializable {
        private final FiniteDuration initialDelay;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public FiniteDuration initialDelay() {
            return this.initialDelay;
        }

        @Override
        public boolean repeat() {
            return true;
        }

        public FixedDelayMode copy(FiniteDuration initialDelay) {
            return new FixedDelayMode(initialDelay);
        }

        public FiniteDuration copy$default$1() {
            return this.initialDelay();
        }

        @Override
        public String productPrefix() {
            return "FixedDelayMode";
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
                    return this.initialDelay();
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
            return x$1 instanceof FixedDelayMode;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "initialDelay";
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
            if (!(object instanceof FixedDelayMode)) return false;
            boolean bl = true;
            if (!bl) return false;
            FixedDelayMode fixedDelayMode = (FixedDelayMode)x$1;
            FiniteDuration finiteDuration = this.initialDelay();
            FiniteDuration finiteDuration2 = fixedDelayMode.initialDelay();
            if (finiteDuration == null) {
                if (finiteDuration2 != null) {
                    return false;
                }
            } else if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
            if (!fixedDelayMode.canEqual(this)) return false;
            return true;
        }

        public FixedDelayMode(FiniteDuration initialDelay) {
            this.initialDelay = initialDelay;
            Product.$init$(this);
        }
    }

    private static class FixedRateMode
    implements TimerMode,
    Product,
    Serializable {
        private final FiniteDuration initialDelay;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public FiniteDuration initialDelay() {
            return this.initialDelay;
        }

        @Override
        public boolean repeat() {
            return true;
        }

        public FixedRateMode copy(FiniteDuration initialDelay) {
            return new FixedRateMode(initialDelay);
        }

        public FiniteDuration copy$default$1() {
            return this.initialDelay();
        }

        @Override
        public String productPrefix() {
            return "FixedRateMode";
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
                    return this.initialDelay();
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
            return x$1 instanceof FixedRateMode;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "initialDelay";
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
            if (!(object instanceof FixedRateMode)) return false;
            boolean bl = true;
            if (!bl) return false;
            FixedRateMode fixedRateMode = (FixedRateMode)x$1;
            FiniteDuration finiteDuration = this.initialDelay();
            FiniteDuration finiteDuration2 = fixedRateMode.initialDelay();
            if (finiteDuration == null) {
                if (finiteDuration2 != null) {
                    return false;
                }
            } else if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
            if (!fixedRateMode.canEqual(this)) return false;
            return true;
        }

        public FixedRateMode(FiniteDuration initialDelay) {
            this.initialDelay = initialDelay;
            Product.$init$(this);
        }
    }

    public static final class InfluenceReceiveTimeoutTimerMsg
    implements TimerMsg,
    NoSerializationVerificationNeeded,
    Product,
    Serializable {
        private final Object key;
        private final int generation;
        private final TimerSchedulerImpl owner;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public Object key() {
            return this.key;
        }

        @Override
        public int generation() {
            return this.generation;
        }

        @Override
        public TimerSchedulerImpl owner() {
            return this.owner;
        }

        public InfluenceReceiveTimeoutTimerMsg copy(Object key, int generation, TimerSchedulerImpl owner) {
            return new InfluenceReceiveTimeoutTimerMsg(key, generation, owner);
        }

        public Object copy$default$1() {
            return this.key();
        }

        public int copy$default$2() {
            return this.generation();
        }

        public TimerSchedulerImpl copy$default$3() {
            return this.owner();
        }

        @Override
        public String productPrefix() {
            return "InfluenceReceiveTimeoutTimerMsg";
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
                    return this.key();
                }
                case 1: {
                    return BoxesRunTime.boxToInteger(this.generation());
                }
                case 2: {
                    return this.owner();
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
            return x$1 instanceof InfluenceReceiveTimeoutTimerMsg;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "key";
                }
                case 1: {
                    return "generation";
                }
                case 2: {
                    return "owner";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.anyHash(this.key()));
            n = Statics.mix(n, this.generation());
            n = Statics.mix(n, Statics.anyHash(this.owner()));
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
            if (!(object instanceof InfluenceReceiveTimeoutTimerMsg)) return false;
            boolean bl = true;
            if (!bl) return false;
            InfluenceReceiveTimeoutTimerMsg influenceReceiveTimeoutTimerMsg = (InfluenceReceiveTimeoutTimerMsg)x$1;
            if (this.generation() != influenceReceiveTimeoutTimerMsg.generation()) return false;
            if (!BoxesRunTime.equals(this.key(), influenceReceiveTimeoutTimerMsg.key())) return false;
            TimerSchedulerImpl timerSchedulerImpl = this.owner();
            TimerSchedulerImpl timerSchedulerImpl2 = influenceReceiveTimeoutTimerMsg.owner();
            if (timerSchedulerImpl != null) {
                if (!timerSchedulerImpl.equals(timerSchedulerImpl2)) return false;
                return true;
            }
            if (timerSchedulerImpl2 == null) return true;
            return false;
        }

        public InfluenceReceiveTimeoutTimerMsg(Object key, int generation, TimerSchedulerImpl owner) {
            this.key = key;
            this.generation = generation;
            this.owner = owner;
            Product.$init$(this);
        }
    }

    public static final class NotInfluenceReceiveTimeoutTimerMsg
    implements TimerMsg,
    NoSerializationVerificationNeeded,
    NotInfluenceReceiveTimeout,
    Product,
    Serializable {
        private final Object key;
        private final int generation;
        private final TimerSchedulerImpl owner;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public Object key() {
            return this.key;
        }

        @Override
        public int generation() {
            return this.generation;
        }

        @Override
        public TimerSchedulerImpl owner() {
            return this.owner;
        }

        public NotInfluenceReceiveTimeoutTimerMsg copy(Object key, int generation, TimerSchedulerImpl owner) {
            return new NotInfluenceReceiveTimeoutTimerMsg(key, generation, owner);
        }

        public Object copy$default$1() {
            return this.key();
        }

        public int copy$default$2() {
            return this.generation();
        }

        public TimerSchedulerImpl copy$default$3() {
            return this.owner();
        }

        @Override
        public String productPrefix() {
            return "NotInfluenceReceiveTimeoutTimerMsg";
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
                    return this.key();
                }
                case 1: {
                    return BoxesRunTime.boxToInteger(this.generation());
                }
                case 2: {
                    return this.owner();
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
            return x$1 instanceof NotInfluenceReceiveTimeoutTimerMsg;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "key";
                }
                case 1: {
                    return "generation";
                }
                case 2: {
                    return "owner";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.anyHash(this.key()));
            n = Statics.mix(n, this.generation());
            n = Statics.mix(n, Statics.anyHash(this.owner()));
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
            if (!(object instanceof NotInfluenceReceiveTimeoutTimerMsg)) return false;
            boolean bl = true;
            if (!bl) return false;
            NotInfluenceReceiveTimeoutTimerMsg notInfluenceReceiveTimeoutTimerMsg = (NotInfluenceReceiveTimeoutTimerMsg)x$1;
            if (this.generation() != notInfluenceReceiveTimeoutTimerMsg.generation()) return false;
            if (!BoxesRunTime.equals(this.key(), notInfluenceReceiveTimeoutTimerMsg.key())) return false;
            TimerSchedulerImpl timerSchedulerImpl = this.owner();
            TimerSchedulerImpl timerSchedulerImpl2 = notInfluenceReceiveTimeoutTimerMsg.owner();
            if (timerSchedulerImpl != null) {
                if (!timerSchedulerImpl.equals(timerSchedulerImpl2)) return false;
                return true;
            }
            if (timerSchedulerImpl2 == null) return true;
            return false;
        }

        public NotInfluenceReceiveTimeoutTimerMsg(Object key, int generation, TimerSchedulerImpl owner) {
            this.key = key;
            this.generation = generation;
            this.owner = owner;
            Product.$init$(this);
        }
    }

    public static final class Timer
    implements Product,
    Serializable {
        private final Object key;
        private final Object msg;
        private final boolean repeat;
        private final int generation;
        private final Cancellable task;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Object key() {
            return this.key;
        }

        public Object msg() {
            return this.msg;
        }

        public boolean repeat() {
            return this.repeat;
        }

        public int generation() {
            return this.generation;
        }

        public Cancellable task() {
            return this.task;
        }

        public Timer copy(Object key, Object msg, boolean repeat, int generation, Cancellable task) {
            return new Timer(key, msg, repeat, generation, task);
        }

        public Object copy$default$1() {
            return this.key();
        }

        public Object copy$default$2() {
            return this.msg();
        }

        public boolean copy$default$3() {
            return this.repeat();
        }

        public int copy$default$4() {
            return this.generation();
        }

        public Cancellable copy$default$5() {
            return this.task();
        }

        @Override
        public String productPrefix() {
            return "Timer";
        }

        @Override
        public int productArity() {
            return 5;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.key();
                }
                case 1: {
                    return this.msg();
                }
                case 2: {
                    return BoxesRunTime.boxToBoolean(this.repeat());
                }
                case 3: {
                    return BoxesRunTime.boxToInteger(this.generation());
                }
                case 4: {
                    return this.task();
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
            return x$1 instanceof Timer;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "key";
                }
                case 1: {
                    return "msg";
                }
                case 2: {
                    return "repeat";
                }
                case 3: {
                    return "generation";
                }
                case 4: {
                    return "task";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.anyHash(this.key()));
            n = Statics.mix(n, Statics.anyHash(this.msg()));
            n = Statics.mix(n, this.repeat() ? 1231 : 1237);
            n = Statics.mix(n, this.generation());
            n = Statics.mix(n, Statics.anyHash(this.task()));
            return Statics.finalizeHash(n, 5);
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
            if (!(object instanceof Timer)) return false;
            boolean bl = true;
            if (!bl) return false;
            Timer timer = (Timer)x$1;
            if (this.repeat() != timer.repeat()) return false;
            if (this.generation() != timer.generation()) return false;
            if (!BoxesRunTime.equals(this.key(), timer.key())) return false;
            if (!BoxesRunTime.equals(this.msg(), timer.msg())) return false;
            Cancellable cancellable = this.task();
            Cancellable cancellable2 = timer.task();
            if (cancellable != null) {
                if (!cancellable.equals(cancellable2)) return false;
                return true;
            }
            if (cancellable2 == null) return true;
            return false;
        }

        public Timer(Object key, Object msg, boolean repeat, int generation, Cancellable task) {
            this.key = key;
            this.msg = msg;
            this.repeat = repeat;
            this.generation = generation;
            this.task = task;
            Product.$init$(this);
        }
    }

    private static interface TimerMode {
        public boolean repeat();
    }

    public static interface TimerMsg {
        public Object key();

        public int generation();

        public TimerSchedulerImpl owner();
    }
}

