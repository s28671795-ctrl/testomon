/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.affinity;

import akka.annotation.ApiMayChange;
import akka.annotation.InternalApi;
import akka.dispatch.AbstractBoundedNodeQueue;
import akka.dispatch.affinity.AffinityPool$;
import akka.dispatch.affinity.OnSpinWait;
import akka.dispatch.affinity.QueueSelector;
import akka.dispatch.affinity.RejectionHandler;
import akka.event.Logging$;
import akka.util.ReentrantGuard;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.collection.immutable.Nil$;
import scala.collection.mutable.Set;
import scala.collection.mutable.Set$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\t5xAB+W\u0011\u00031FL\u0002\u0004_-\"\u0005ak\u0018\u0005\u0006M\u0006!\t\u0001[\u0003\u0005S\u0006\u0001!\u000eC\u0004n\u0003\t\u0007IQ\u00018\t\rE\f\u0001\u0015!\u0004p\u0011\u001d\u0011\u0018A1A\u0005\u0006MDaA^\u0001!\u0002\u001b!\bbB<\u0002\u0005\u0004%)\u0001\u001f\u0005\u0007w\u0006\u0001\u000bQB=\t\u000fq\f!\u0019!C\u0003{\"9\u0011\u0011A\u0001!\u0002\u001bq\b\"CA\u0002\u0003\t\u0007IQAA\u0003\u0011!\tY!\u0001Q\u0001\u000e\u0005\u001d\u0001\"CA\u0007\u0003\t\u0007IQAA\b\u0011!\t)\"\u0001Q\u0001\u000e\u0005EQ!BA\f\u0003\u0001Q\u0007\u0002CA\r\u0003\t\u0007IQ\u00018\t\u000f\u0005m\u0011\u0001)A\u0007_\"A\u0011QD\u0001C\u0002\u0013\u00151\u000fC\u0004\u0002 \u0005\u0001\u000bQ\u0002;\t\u0011\u0005\u0005\u0012A1A\u0005\u0006aDq!a\t\u0002A\u00035\u0011\u0010\u0003\u0005\u0002&\u0005\u0011\r\u0011\"\u0002~\u0011\u001d\t9#\u0001Q\u0001\u000ey4a!!\u000b\u0002\r\u0005-\u0002\"CA\u00173\t\u0005\t\u0015!\u0003k\u0011\u00191\u0017\u0004\"\u0001\u00020!9\u0011qG\r!\u0002\u0013Q\u0007bBA\u001d3\u0001\u0006IA\u001b\u0005\b\u0003wI\u0002\u0015!\u0003k\u0011!\ti$\u0007Q\u0001\n\u0005}\u0002BDA#3\u0011\u0005\tQ!A\u0001B\u0003&\u0011q\t\u0005\u000f\u0003\u0013JB\u0011!A\u0003\u0002\u0003\u0005\u000b\u0015BA \u0011!\tY%\u0007Q!\n\u0005}\u0002\u0002CA'3\u0001\u0006K!a\u0014\t\u0011\u0005u\u0013\u0004)C\u0005\u0003?Bq!a\u001d\u001a\t\u0003\t)\bC\u0004\u0002xe!\t!!\u001f\t\u000f\u0005m\u0014\u0004\"\u0001\u0002z\u00191\u0011QP\u0001\u0007\u0003\u007fB\u0011\"!')\u0005\u0003\u0005\u000b\u0011\u00026\t\r\u0019DC\u0011AAN\r\u0019qf\u000b\u0001.\u0002<\"Q\u0011QZ\u0016\u0003\u0002\u0003\u0006I!a4\t\u0013\u0005\u00158F!A!\u0002\u0013Q\u0007\"CAtW\t\u0005\t\u0015!\u0003k\u0011)\tIo\u000bB\u0001B\u0003%\u00111\u001e\u0005\n\u0003[Y#\u0011!Q\u0001\n)D!\"!=,\u0005\u000b\u0007IQAAz\u0011)\tYp\u000bB\u0001B\u00035\u0011Q\u001f\u0005\u000b\u0003{\\#\u0011!Q\u0001\n\u0005}\bB\u00024,\t\u0003\u0011)\u0001C\u0005\u0003\u0018-\u0012\r\u0011\"\u0003\u0003\u001a!A!QE\u0016!\u0002\u0013\u0011Y\u0002C\u0005\u0003(-\u0012\r\u0011\"\u0003\u0003*!A!qG\u0016!\u0002\u0013\u0011Y\u0003C\u0005\u0003:-\u0002\r\u0011\"\u0004\u0003<!I!\u0011I\u0016A\u0002\u00135!1\t\u0005\t\u0005\u0013Z\u0003\u0015)\u0004\u0003>!A!QJ\u0016!\u0002\u001b\u0011y\u0005\u0003\u0005\u0003X-\u0002\u000bQ\u0002B-\u0011\u001d\u00119j\u000bC\u0001\u0005?CqA!),\t\u0013\u0011\u0019\u000bC\u0004\u0003*.\"IAa+\t\u000f\tU6\u0006\"\u0011\u00038\"9!QX\u0016\u0005B\t}\u0006b\u0002BhW\u0011%\u0011\u0011\u0010\u0005\b\u0005#\\C\u0011\tBj\u0011\u001d\u0011in\u000bC!\u0003sBqAa8,\t\u0003\u0012\t\u000fC\u0004\u0003d.\"\tE!9\t\u000f\t\u00158\u0006\"\u0011\u0003h\u001aA!QN\u0016!\u0002\u001b\u0011y\u0007\u0003\u0006\u0003x%\u0013)\u0019!C\u0001\u0005sB!Ba\u001fJ\u0005\u0003\u0005\u000b\u0011\u0002B+\u0011)\u0011i(\u0013BC\u0002\u0013\u0005!q\u0010\u0005\u000b\u0005\u0007K%\u0011!Q\u0001\n\t\u0005\u0005B\u00024J\t\u0003\u0011)\tC\u0005\u0003\f&\u0013\r\u0011\"\u0001\u0003\u000e\"A!QS%!\u0002\u0013\u0011y\tC\u0004\u0003\u0018&#\t!!\u001f\t\u000f\te\u0015\n\"\u0011\u0002z!9!1T%\u0005\u0002\u0005e\u0004b\u0002BO\u0013\u0012\u0005\u0011\u0011P\u0001\r\u0003\u001a4\u0017N\\5usB{w\u000e\u001c\u0006\u0003/b\u000b\u0001\"\u00194gS:LG/\u001f\u0006\u00033j\u000b\u0001\u0002Z5ta\u0006$8\r\u001b\u0006\u00027\u0006!\u0011m[6b!\ti\u0016!D\u0001W\u00051\teMZ5oSRL\bk\\8m'\t\t\u0001\r\u0005\u0002bI6\t!MC\u0001d\u0003\u0015\u00198-\u00197b\u0013\t)'M\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\tALA\u0005Q_>d7\u000b^1uKB\u0011\u0011m[\u0005\u0003Y\n\u00141!\u00138u\u00035)f.\u001b8ji&\fG.\u001b>fIV\tqnD\u0001q;\u0005\u0001\u0011AD+oS:LG/[1mSj,G\rI\u0001\r\u0013:LG/[1mSjLgnZ\u000b\u0002i>\tQ/H\u0001\u0002\u00035Ie.\u001b;jC2L'0\u001b8hA\u00059!+\u001e8oS:<W#A=\u0010\u0003il\u0012AA\u0001\t%Vtg.\u001b8hA\u0005a1\u000b[;ui&tw\rR8x]V\tapD\u0001\u0000;\u0005\u0019\u0011!D*ikR$\u0018N\\4E_^t\u0007%\u0001\u0005TQV$Hi\\<o+\t\t9a\u0004\u0002\u0002\nu\tA!A\u0005TQV$Hi\\<oA\u0005QA+\u001a:nS:\fG/\u001a3\u0016\u0005\u0005EqBAA\n;\u0005)\u0011a\u0003+fe6Lg.\u0019;fI\u0002\u0012\u0011\"\u00133mKN#\u0018\r^3\u0002\u000f%s\u0017\u000e^5bY\u0006A\u0011J\\5uS\u0006d\u0007%\u0001\u0005Ta&tg.\u001b8h\u0003%\u0019\u0006/\u001b8oS:<\u0007%\u0001\u0005ZS\u0016dG-\u001b8h\u0003%I\u0016.\u001a7eS:<\u0007%A\u0004QCJ\\\u0017N\\4\u0002\u0011A\u000b'o[5oO\u0002\u0012A\"\u00133mKN#(/\u0019;fOf\u001c\"!\u00071\u0002\u0019%$G.Z\"qk2+g/\u001a7\u0015\t\u0005E\u0012Q\u0007\t\u0004\u0003gIR\"A\u0001\t\r\u000552\u00041\u0001k\u0003!i\u0017\r_*qS:\u001c\u0018!C7bqfKW\r\u001c3t\u0003=i\u0017N\u001c)be.\u0004VM]5pI:\u001b\u0018aD7bqB\u000b'o\u001b)fe&|GMT:\u0011\u0007\u0005\f\t%C\u0002\u0002D\t\u0014A\u0001T8oO\u00069\u0014m[6bI\u0011L7\u000f]1uG\"$\u0013M\u001a4j]&$\u0018\u0010J!gM&t\u0017\u000e^=Q_>dG%\u00133mKN#(/\u0019;fOf$Ce\u001d;bi\u0016\u00042!a\r\u0011\u0003]\n7n[1%I&\u001c\b/\u0019;dQ\u0012\ngMZ5oSRLH%\u00114gS:LG/\u001f)p_2$\u0013\n\u001a7f'R\u0014\u0018\r^3hs\u0012\"C/\u001e:og\u0006a\u0001/\u0019:l!\u0016\u0014\u0018n\u001c3Og\u00061\u0011\u000e\u001a7j]\u001e\u00042!YA)\u0013\r\t\u0019F\u0019\u0002\b\u0005>|G.Z1oQ\r\u0019\u0013q\u000b\t\u0004C\u0006e\u0013bAA.E\nAao\u001c7bi&dW-\u0001\u0007ue\u0006t7/\u001b;j_:$v\u000e\u0006\u0003\u0002b\u0005\u001d\u0004cA1\u0002d%\u0019\u0011Q\r2\u0003\tUs\u0017\u000e\u001e\u0005\b\u0003S\"\u0003\u0019AA$\u0003!qWm^*uCR,\u0007f\u0001\u0013\u0002nA\u0019\u0011-a\u001c\n\u0007\u0005E$M\u0001\u0004j]2Lg.Z\u0001\tSNLE\r\\5oOV\u0011\u0011qJ\u0001\u0005S\u0012dW\r\u0006\u0002\u0002b\u0005)!/Z:fi\nA\"i\\;oI\u0016$\u0017I\u001a4j]&$\u0018\u0010V1tWF+X-^3\u0014\u0007!\n\t\t\u0005\u0004\u0002\u0004\u0006\u0015\u0015\u0011R\u0007\u00021&\u0019\u0011q\u0011-\u00031\u0005\u00137\u000f\u001e:bGR\u0014u.\u001e8eK\u0012tu\u000eZ3Rk\u0016,X\r\u0005\u0003\u0002\f\u0006UUBAAG\u0015\u0011\ty)!%\u0002\t1\fgn\u001a\u0006\u0003\u0003'\u000bAA[1wC&!\u0011qSAG\u0005!\u0011VO\u001c8bE2,\u0017\u0001C2ba\u0006\u001c\u0017\u000e^=\u0015\t\u0005u\u0015q\u0014\t\u0004\u0003gA\u0003BBAMU\u0001\u0007!\u000eK\u0002\u0002\u0003G\u0003B!!*\u0002,6\u0011\u0011q\u0015\u0006\u0004\u0003SS\u0016AC1o]>$\u0018\r^5p]&!\u0011QVAT\u00051\t\u0005/['bs\u000eC\u0017M\\4fQ\r\t\u0011\u0011\u0017\t\u0005\u0003K\u000b\u0019,\u0003\u0003\u00026\u0006\u001d&aC%oi\u0016\u0014h.\u00197Ba&D3\u0001AARQ\r\u0001\u0011\u0011W\n\u0004W\u0005u\u0006\u0003BA`\u0003\u0013l!!!1\u000b\t\u0005\r\u0017QY\u0001\u000bG>t7-\u001e:sK:$(\u0002BAd\u0003#\u000bA!\u001e;jY&!\u00111ZAa\u0005]\t%m\u001d;sC\u000e$X\t_3dkR|'oU3sm&\u001cW-\u0001\u0002jIB!\u0011\u0011[Ap\u001d\u0011\t\u0019.a7\u0011\u0007\u0005U'-\u0004\u0002\u0002X*\u0019\u0011\u0011\\4\u0002\rq\u0012xn\u001c;?\u0013\r\tiNY\u0001\u0007!J,G-\u001a4\n\t\u0005\u0005\u00181\u001d\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005u'-A\u0006qCJ\fG\u000e\\3mSNl\u0017!E1gM&t\u0017\u000e^=He>,\boU5{K\u0006iA\u000f\u001b:fC\u00124\u0015m\u0019;pef\u0004B!a0\u0002n&!\u0011q^Aa\u00055!\u0006N]3bI\u001a\u000b7\r^8ss\u0006i\u0011/^3vKN+G.Z2u_J,\"!!>\u0011\u0007u\u000b90C\u0002\u0002zZ\u0013Q\"U;fk\u0016\u001cV\r\\3di>\u0014\u0018AD9vKV,7+\u001a7fGR|'\u000fI\u0001\u0011e\u0016TWm\u0019;j_:D\u0015M\u001c3mKJ\u00042!\u0018B\u0001\u0013\r\u0011\u0019A\u0016\u0002\u0011%\u0016TWm\u0019;j_:D\u0015M\u001c3mKJ$\u0002Ca\u0002\u0003\n\t-!Q\u0002B\b\u0005#\u0011\u0019B!\u0006\u0011\u0005u[\u0003bBAgi\u0001\u0007\u0011q\u001a\u0005\u0007\u0003K$\u0004\u0019\u00016\t\r\u0005\u001dH\u00071\u0001k\u0011\u001d\tI\u000f\u000ea\u0001\u0003WDa!!\f5\u0001\u0004Q\u0007bBAyi\u0001\u0007\u0011Q\u001f\u0005\b\u0003{$\u0004\u0019AA\u0000\u0003=\u0011wn\\6LK\u0016\u0004\u0018N\\4M_\u000e\\WC\u0001B\u000e!\u0011\u0011iB!\t\u000e\u0005\t}!bAAd5&!!1\u0005B\u0010\u00059\u0011V-\u001a8ue\u0006tGoR;be\u0012\f\u0001CY8pW.+W\r]5oO2{7m\u001b\u0011\u0002)Q,'/\\5oCRLwN\\\"p]\u0012LG/[8o+\t\u0011Y\u0003\u0005\u0003\u0003.\tMRB\u0001B\u0018\u0015\u0011\u0011\t$!1\u0002\u000b1|7m[:\n\t\tU\"q\u0006\u0002\n\u0007>tG-\u001b;j_:\fQ\u0003^3s[&t\u0017\r^5p]\u000e{g\u000eZ5uS>t\u0007%A\u0005q_>d7\u000b^1uKV\u0011!Q\b\t\u0004\u0005\u007f\u0019aBA/\u0001\u00035\u0001xn\u001c7Ti\u0006$Xm\u0018\u0013fcR!\u0011\u0011\rB#\u0011%\u00119EOA\u0001\u0002\u0004\u0011i$A\u0002yIE\n!\u0002]8pYN#\u0018\r^3!Q\rY\u0014qK\u0001\u000bo>\u00148.U;fk\u0016\u001c\b#B1\u0003R\tU\u0013b\u0001B*E\n)\u0011I\u001d:bsB\u0019!q\b\u0015\u0002\u000f]|'o[3sgB1!1\fB3\u0005Sj!A!\u0018\u000b\t\t}#\u0011M\u0001\b[V$\u0018M\u00197f\u0015\r\u0011\u0019GY\u0001\u000bG>dG.Z2uS>t\u0017\u0002\u0002B4\u0005;\u00121aU3u!\r\u0011Y'S\u0007\u0002W\t\u0011\u0012I\u001a4j]&$\u0018\u0010U8pY^{'o[3s'\u0015I%\u0011OAE!\u0011\tYIa\u001d\n\t\tU\u0014Q\u0012\u0002\u0007\u001f\nTWm\u0019;\u0002\u0003E,\"A!\u0016\u0002\u0005E\u0004\u0013\u0001D5eY\u0016\u001cFO]1uK\u001eLXC\u0001BA!\r\u0011y$G\u0001\u000eS\u0012dWm\u0015;sCR,w-\u001f\u0011\u0015\r\t%$q\u0011BE\u0011\u001d\u00119H\u0014a\u0001\u0005+BqA! O\u0001\u0004\u0011\t)\u0001\u0004uQJ,\u0017\rZ\u000b\u0003\u0005\u001f\u0003B!a#\u0003\u0012&!!1SAG\u0005\u0019!\u0006N]3bI\u00069A\u000f\u001b:fC\u0012\u0004\u0013!B:uCJ$\u0018a\u0001:v]\u0006!1\u000f^8q\u0003)\u0019Ho\u001c9JM&#G.\u001a\u000b\u0003\u0005W\n\u0011\"\u00193e/>\u00148.\u001a:\u0015\r\u0005\u0005$Q\u0015BT\u0011\u001d\u00119f\u0010a\u0001\u00053BqAa\u001e@\u0001\u0004\u0011)&\u0001\u0007p]^{'o[3s\u000bbLG\u000f\u0006\u0004\u0002b\t5&\u0011\u0017\u0005\b\u0005_\u0003\u0005\u0019\u0001B5\u0003\u00059\bb\u0002BZ\u0001\u0002\u0007\u0011qJ\u0001\u0012C\n\u0014X\u000f\u001d;UKJl\u0017N\\1uS>t\u0017aB3yK\u000e,H/\u001a\u000b\u0005\u0003C\u0012I\fC\u0004\u0003<\u0006\u0003\r!!#\u0002\u000f\r|W.\\1oI\u0006\u0001\u0012m^1jiR+'/\\5oCRLwN\u001c\u000b\u0007\u0003\u001f\u0012\tM!2\t\u000f\t\r'\t1\u0001\u0002@\u00059A/[7f_V$\bb\u0002Bd\u0005\u0002\u0007!\u0011Z\u0001\u0005k:LG\u000f\u0005\u0003\u0002@\n-\u0017\u0002\u0002Bg\u0003\u0003\u0014\u0001\u0002V5nKVs\u0017\u000e^\u0001\u0017CR$X-\u001c9u!>|G\u000eV3s[&t\u0017\r^5p]\u0006Y1\u000f[;uI><hNT8x)\t\u0011)\u000e\u0005\u0004\u0003X\ne\u0017\u0011R\u0007\u0003\u0003\u000bLAAa7\u0002F\n!A*[:u\u0003!\u0019\b.\u001e;e_^t\u0017AC5t'\",H\u000fZ8x]R\u0011\u0011qJ\u0001\rSN$VM]7j]\u0006$X\rZ\u0001\ti>\u001cFO]5oOR\u0011\u0011q\u001a\u0015\u0004W\u0005\r\u0006fA\u0016\u00022\u0002")
@InternalApi
@ApiMayChange
public class AffinityPool
extends AbstractExecutorService {
    private final String id;
    private final int parallelism;
    private final int affinityGroupSize;
    public final ThreadFactory akka$dispatch$affinity$AffinityPool$$threadFactory;
    private final int idleCpuLevel;
    private final QueueSelector queueSelector;
    private final RejectionHandler rejectionHandler;
    private final ReentrantGuard bookKeepingLock;
    private final Condition terminationCondition;
    private volatile int akka$dispatch$affinity$AffinityPool$$poolState;
    private final BoundedAffinityTaskQueue[] workQueues;
    private final Set<AffinityPoolWorker> workers;

    public static int Parking() {
        return AffinityPool$.MODULE$.Parking();
    }

    public static int Yielding() {
        return AffinityPool$.MODULE$.Yielding();
    }

    public static int Spinning() {
        return AffinityPool$.MODULE$.Spinning();
    }

    public static int Initial() {
        return AffinityPool$.MODULE$.Initial();
    }

    public static int Terminated() {
        return AffinityPool$.MODULE$.Terminated();
    }

    public static int ShutDown() {
        return AffinityPool$.MODULE$.ShutDown();
    }

    public static int ShuttingDown() {
        return AffinityPool$.MODULE$.ShuttingDown();
    }

    public static int Running() {
        return AffinityPool$.MODULE$.Running();
    }

    public static int Initializing() {
        return AffinityPool$.MODULE$.Initializing();
    }

    public static int Uninitialized() {
        return AffinityPool$.MODULE$.Uninitialized();
    }

    public final QueueSelector queueSelector() {
        return this.queueSelector;
    }

    private ReentrantGuard bookKeepingLock() {
        return this.bookKeepingLock;
    }

    private Condition terminationCondition() {
        return this.terminationCondition;
    }

    public final int akka$dispatch$affinity$AffinityPool$$poolState() {
        return this.akka$dispatch$affinity$AffinityPool$$poolState;
    }

    private final void poolState_$eq(int x$1) {
        this.akka$dispatch$affinity$AffinityPool$$poolState = x$1;
    }

    public AffinityPool start() {
        return (AffinityPool)this.bookKeepingLock().withGuard((Function0<AffinityPool> & Serializable)() -> {
            block0: {
                if (this.akka$dispatch$affinity$AffinityPool$$poolState() != 0) break block0;
                this.poolState_$eq(1);
                ArrayOps$.MODULE$.foreach$extension(Predef$.MODULE$.refArrayOps($this.workQueues), (Function1<BoundedAffinityTaskQueue, Object> & Serializable)q -> {
                    this.addWorker(this.workers, q);
                    return BoxedUnit.UNIT;
                });
                this.poolState_$eq(2);
            }
            return this;
        });
    }

    private void addWorker(Set<AffinityPoolWorker> workers, BoundedAffinityTaskQueue q) {
        AffinityPoolWorker worker = new AffinityPoolWorker(this, q, new IdleStrategy(this.idleCpuLevel));
        workers.add(worker);
        worker.start();
    }

    public void akka$dispatch$affinity$AffinityPool$$onWorkerExit(AffinityPoolWorker w, boolean abruptTermination) {
        this.bookKeepingLock().withGuard(() -> {
            $this.workers.remove(w);
            if (abruptTermination && this.akka$dispatch$affinity$AffinityPool$$poolState() == 2) {
                this.addWorker($this.workers, w.q());
                return;
            }
            if ($this.workers.isEmpty() && !abruptTermination && this.akka$dispatch$affinity$AffinityPool$$poolState() >= 3) {
                this.poolState_$eq(4);
                this.attemptPoolTermination();
                return;
            }
        });
    }

    @Override
    public void execute(Runnable command) {
        BoundedAffinityTaskQueue queue = this.workQueues[this.queueSelector().getQueue(command, this.parallelism)];
        if (this.akka$dispatch$affinity$AffinityPool$$poolState() >= 3 || !queue.add(command)) {
            this.rejectionHandler.reject(command, this);
            return;
        }
    }

    @Override
    public boolean awaitTermination(long timeout2, TimeUnit unit) {
        return BoxesRunTime.unboxToBoolean(this.bookKeepingLock().withGuard(() -> this.awaitTermination$1(unit.toNanos(timeout2))));
    }

    private void attemptPoolTermination() {
        if (this.workers.isEmpty() && this.akka$dispatch$affinity$AffinityPool$$poolState() == 4) {
            this.poolState_$eq(5);
            this.terminationCondition().signalAll();
            return;
        }
    }

    @Override
    public List<Runnable> shutdownNow() {
        return (List)this.bookKeepingLock().withGuard((Function0<List> & Serializable)() -> {
            this.poolState_$eq(4);
            $this.workers.foreach((Function1<AffinityPoolWorker, Object> & Serializable)x$1 -> {
                x$1.stop();
                return BoxedUnit.UNIT;
            });
            this.attemptPoolTermination();
            return Collections.emptyList();
        });
    }

    @Override
    public void shutdown() {
        this.bookKeepingLock().withGuard(() -> {
            this.poolState_$eq(3);
            $this.workers.foreach((Function1<AffinityPoolWorker, Object> & Serializable)x$2 -> {
                x$2.stopIfIdle();
                return BoxedUnit.UNIT;
            });
            this.attemptPoolTermination();
        });
    }

    @Override
    public boolean isShutdown() {
        return this.akka$dispatch$affinity$AffinityPool$$poolState() >= 4;
    }

    @Override
    public boolean isTerminated() {
        return this.akka$dispatch$affinity$AffinityPool$$poolState() == 5;
    }

    public String toString() {
        return new StringBuilder(119).append(Logging$.MODULE$.simpleName(this)).append("(id = ").append(this.id).append(", parallelism = ").append(this.parallelism).append(", affinityGroupSize = ").append(this.affinityGroupSize).append(", threadFactory = ").append(this.akka$dispatch$affinity$AffinityPool$$threadFactory).append(", idleCpuLevel = ").append(this.idleCpuLevel).append(", queueSelector = ").append(this.queueSelector()).append(", rejectionHandler = ").append(this.rejectionHandler).append(")").toString();
    }

    private final boolean awaitTermination$1(long nanos) {
        while (this.akka$dispatch$affinity$AffinityPool$$poolState() != 5) {
            if (nanos <= 0L) {
                return false;
            }
            nanos = this.terminationCondition().awaitNanos(nanos);
        }
        return true;
    }

    public AffinityPool(String id, int parallelism, int affinityGroupSize, ThreadFactory threadFactory, int idleCpuLevel, QueueSelector queueSelector, RejectionHandler rejectionHandler) {
        this.id = id;
        this.parallelism = parallelism;
        this.affinityGroupSize = affinityGroupSize;
        this.akka$dispatch$affinity$AffinityPool$$threadFactory = threadFactory;
        this.idleCpuLevel = idleCpuLevel;
        this.queueSelector = queueSelector;
        this.rejectionHandler = rejectionHandler;
        if (parallelism <= 0) {
            throw new IllegalArgumentException("Size of pool cannot be less or equal to 0");
        }
        this.bookKeepingLock = new ReentrantGuard();
        this.terminationCondition = this.bookKeepingLock().newCondition();
        this.akka$dispatch$affinity$AffinityPool$$poolState = 0;
        this.workQueues = (BoundedAffinityTaskQueue[])Array$.MODULE$.fill(parallelism, (Function0<BoundedAffinityTaskQueue> & Serializable)() -> new BoundedAffinityTaskQueue($this.affinityGroupSize), ClassTag$.MODULE$.apply(BoundedAffinityTaskQueue.class));
        this.workers = (Set)Set$.MODULE$.apply(Nil$.MODULE$);
    }

    private final class AffinityPoolWorker
    implements Runnable {
        private final BoundedAffinityTaskQueue q;
        private final IdleStrategy idleStrategy;
        private final Thread thread;
        private final /* synthetic */ AffinityPool $outer;

        public BoundedAffinityTaskQueue q() {
            return this.q;
        }

        public IdleStrategy idleStrategy() {
            return this.idleStrategy;
        }

        public Thread thread() {
            return this.thread;
        }

        public void start() {
            if (this.thread() == null) {
                throw new IllegalStateException(new StringBuilder(43).append("Was not able to allocate worker thread for ").append(this.$outer).toString());
            }
            this.thread().start();
        }

        @Override
        public void run() {
            boolean abruptTermination = true;
            try {
                this.runLoop$1();
                abruptTermination = false;
            }
            finally {
                this.$outer.akka$dispatch$affinity$AffinityPool$$onWorkerExit(this, abruptTermination);
            }
        }

        public void stop() {
            if (!this.thread().isInterrupted()) {
                this.thread().interrupt();
                return;
            }
        }

        public void stopIfIdle() {
            if (this.idleStrategy().isIdling()) {
                this.stop();
                return;
            }
        }

        private final boolean executeNext$1() {
            boolean next;
            Runnable c = (Runnable)this.q().poll();
            boolean bl = next = c != null;
            if (next) {
                c.run();
                this.idleStrategy().reset();
            } else {
                this.idleStrategy().idle();
            }
            return next;
        }

        private final void runLoop$1() {
            block7: {
                BoxedUnit boxedUnit;
                block6: while (!Thread.interrupted()) {
                    int n = this.$outer.akka$dispatch$affinity$AffinityPool$$poolState();
                    switch (n) {
                        case 0: {
                            boxedUnit = BoxedUnit.UNIT;
                            break block7;
                        }
                        case 1: 
                        case 2: {
                            this.executeNext$1();
                            continue block6;
                        }
                        case 3: {
                            if (this.executeNext$1()) continue block6;
                            boxedUnit = BoxedUnit.UNIT;
                            break block7;
                        }
                        case 4: 
                        case 5: {
                            boxedUnit = BoxedUnit.UNIT;
                            break block7;
                        }
                        default: {
                            throw new MatchError(BoxesRunTime.boxToInteger(n));
                        }
                    }
                }
                boxedUnit = BoxedUnit.UNIT;
            }
        }

        public AffinityPoolWorker(AffinityPool $outer, BoundedAffinityTaskQueue q, IdleStrategy idleStrategy) {
            this.q = q;
            this.idleStrategy = idleStrategy;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            this.thread = $outer.akka$dispatch$affinity$AffinityPool$$threadFactory.newThread(this);
        }
    }

    private static final class BoundedAffinityTaskQueue
    extends AbstractBoundedNodeQueue<Runnable> {
        public BoundedAffinityTaskQueue(int capacity) {
            super(capacity);
        }
    }

    private static final class IdleStrategy {
        private final int maxSpins;
        private final int maxYields;
        private final int minParkPeriodNs;
        private final long maxParkPeriodNs;
        public int akka$dispatch$affinity$AffinityPool$IdleStrategy$$state;
        public long akka$dispatch$affinity$AffinityPool$IdleStrategy$$turns;
        private long parkPeriodNs;
        private volatile boolean idling;

        private void transitionTo(int newState) {
            this.akka$dispatch$affinity$AffinityPool$IdleStrategy$$state = newState;
            this.akka$dispatch$affinity$AffinityPool$IdleStrategy$$turns = 0L;
        }

        public boolean isIdling() {
            return this.idling;
        }

        public void idle() {
            int n = this.akka$dispatch$affinity$AffinityPool$IdleStrategy$$state;
            switch (n) {
                case 0: {
                    this.idling = true;
                    this.transitionTo(1);
                    return;
                }
                case 1: {
                    OnSpinWait.spinWait();
                    ++this.akka$dispatch$affinity$AffinityPool$IdleStrategy$$turns;
                    if (this.akka$dispatch$affinity$AffinityPool$IdleStrategy$$turns > (long)this.maxSpins) {
                        this.transitionTo(2);
                        return;
                    }
                    return;
                }
                case 2: {
                    ++this.akka$dispatch$affinity$AffinityPool$IdleStrategy$$turns;
                    if (this.akka$dispatch$affinity$AffinityPool$IdleStrategy$$turns > (long)this.maxYields) {
                        this.parkPeriodNs = this.minParkPeriodNs;
                        this.transitionTo(3);
                        return;
                    }
                    Thread.yield();
                    return;
                }
                case 3: {
                    LockSupport.parkNanos(this.parkPeriodNs);
                    this.parkPeriodNs = Math.min(this.parkPeriodNs << 1, this.maxParkPeriodNs);
                    return;
                }
            }
            throw new MatchError(BoxesRunTime.boxToInteger(n));
        }

        public void reset() {
            this.idling = false;
            this.transitionTo(0);
        }

        public IdleStrategy(int idleCpuLevel) {
            this.maxSpins = 1100 * idleCpuLevel - 1000;
            this.maxYields = 5 * idleCpuLevel;
            this.minParkPeriodNs = 1;
            this.maxParkPeriodNs = TimeUnit.MICROSECONDS.toNanos(250 - 80 * (idleCpuLevel - 1) / 3);
            this.akka$dispatch$affinity$AffinityPool$IdleStrategy$$state = 0;
            this.akka$dispatch$affinity$AffinityPool$IdleStrategy$$turns = 0L;
            this.parkPeriodNs = 0L;
            this.idling = false;
        }
    }
}

