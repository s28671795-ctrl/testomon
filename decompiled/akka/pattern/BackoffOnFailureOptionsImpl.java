/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.Props$;
import akka.pattern.AutoReset;
import akka.pattern.BackoffOnFailureOptions;
import akka.pattern.BackoffOnFailureOptionsImpl$;
import akka.pattern.BackoffReset;
import akka.pattern.ForwardTo;
import akka.pattern.HandlingWhileStopped;
import akka.pattern.ManualReset$;
import akka.pattern.ReplyWith;
import akka.pattern.internal.BackoffOnRestartSupervisor;
import java.io.Serializable;
import scala.Function0;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.Tuple8;
import scala.collection.Iterator;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t]g\u0001\u0002\u001e<\r\u0002C\u0001b\u0017\u0001\u0003\u0016\u0004%\t\u0001\u0018\u0005\tG\u0002\u0011\t\u0012)A\u0005;\"AA\r\u0001BK\u0002\u0013\u0005Q\r\u0003\u0005o\u0001\tE\t\u0015!\u0003g\u0011!y\u0007A!f\u0001\n\u0003\u0001\b\u0002C=\u0001\u0005#\u0005\u000b\u0011B9\t\u0011i\u0004!Q3A\u0005\u0002AD\u0001b\u001f\u0001\u0003\u0012\u0003\u0006I!\u001d\u0005\ty\u0002\u0011)\u001a!C\u0001{\"I\u00111\u0001\u0001\u0003\u0012\u0003\u0006IA \u0005\u000b\u0003\u000b\u0001!Q3A\u0005\u0002\u0005\u001d\u0001BCA\u000b\u0001\tE\t\u0015!\u0003\u0002\n!Q\u0011q\u0003\u0001\u0003\u0016\u0004%\t!!\u0007\t\u0015\u0005\u0005\u0002A!E!\u0002\u0013\tY\u0002\u0003\u0006\u0002$\u0001\u0011)\u001a!C\u0001\u0003KA!\"!\f\u0001\u0005#\u0005\u000b\u0011BA\u0014\u0011\u001d\ty\u0003\u0001C\u0001\u0003cA\u0011\"a\u0017\u0001\u0005\u0004%I!!\u0018\t\u0011\u0005}\u0003\u0001)A\u0005\u0003\u001fAq!!\u0019\u0001\t\u0003\t\u0019\u0007C\u0004\u0002l\u0001!\t!!\u001c\t\u000f\u0005=\u0004\u0001\"\u0001\u0002r!9\u0011Q\u000f\u0001\u0005\u0002\u0005]\u0004bBA?\u0001\u0011\u0005\u0011q\u0010\u0005\b\u0003\u0017\u0003A\u0011AAG\u0011\u0019\tI\n\u0001C\u00019\"I\u00111\u0014\u0001\u0002\u0002\u0013\u0005\u0011Q\u0014\u0005\n\u0003o\u0003\u0011\u0013!C\u0001\u0003sC\u0011\"a5\u0001#\u0003%\t!!6\t\u0013\u0005u\u0007!%A\u0005\u0002\u0005}\u0007\"CAt\u0001E\u0005I\u0011AAu\u0011%\ti\u000fAI\u0001\n\u0003\ty\u000fC\u0005\u0002x\u0002\t\n\u0011\"\u0001\u0002z\"I!\u0011\u0001\u0001\u0012\u0002\u0013\u0005!1\u0001\u0005\n\u0005\u0017\u0001\u0011\u0013!C\u0001\u0005\u001bA\u0011B!\u0006\u0001\u0003\u0003%\tEa\u0006\t\u0013\t\u001d\u0002!!A\u0005\u0002\t%\u0002\"\u0003B\u0016\u0001\u0005\u0005I\u0011\u0001B\u0017\u0011%\u0011\u0019\u0004AA\u0001\n\u0003\u0012)\u0004C\u0005\u0003D\u0001\t\t\u0011\"\u0001\u0003F!I!q\n\u0001\u0002\u0002\u0013\u0005#\u0011\u000b\u0005\n\u0005+\u0002\u0011\u0011!C!\u0005/B\u0011B!\u0017\u0001\u0003\u0003%\tEa\u0017\t\u0013\tu\u0003!!A\u0005B\t}s!\u0003B2w\u0005\u0005\t\u0012\u0002B3\r!Q4(!A\t\n\t\u001d\u0004bBA\u0018]\u0011\u0005!1\u000f\u0005\n\u00053r\u0013\u0011!C#\u00057B\u0011B!\u001e/\u0003\u0003%\tIa\u001e\t\u0013\tEe&%A\u0005\u0002\tM\u0005\"\u0003BL]E\u0005I\u0011\u0001BM\u0011%\u0011iJLI\u0001\n\u0003\u0011y\nC\u0005\u0003$:\n\t\u0011\"!\u0003&\"I!1\u0018\u0018\u0012\u0002\u0013\u0005!Q\u0018\u0005\n\u0005\u0003t\u0013\u0013!C\u0001\u0005\u0007D\u0011Ba2/#\u0003%\tA!3\t\u0013\t5g&!A\u0005\n\t='a\u0007\"bG.|gMZ(o\r\u0006LG.\u001e:f\u001fB$\u0018n\u001c8t\u00136\u0004HN\u0003\u0002={\u00059\u0001/\u0019;uKJt'\"\u0001 \u0002\t\u0005\\7.Y\u0002\u0001+\r\t\u0015\u0011H\n\u0006\u0001\tCEj\u0014\t\u0003\u0007\u001ak\u0011\u0001\u0012\u0006\u0002\u000b\u0006)1oY1mC&\u0011q\t\u0012\u0002\u0007\u0003:L(+\u001a4\u0011\u0005%SU\"A\u001e\n\u0005-[$a\u0006\"bG.|gMZ(o\r\u0006LG.\u001e:f\u001fB$\u0018n\u001c8t!\t\u0019U*\u0003\u0002O\t\n9\u0001K]8ek\u000e$\bC\u0001)Y\u001d\t\tfK\u0004\u0002S+6\t1K\u0003\u0002U\u007f\u00051AH]8pizJ\u0011!R\u0005\u0003/\u0012\u000bq\u0001]1dW\u0006<W-\u0003\u0002Z5\na1+\u001a:jC2L'0\u00192mK*\u0011q\u000bR\u0001\u000bG\"LG\u000e\u001a)s_B\u001cX#A/\u0011\u0005y\u000bW\"A0\u000b\u0005\u0001l\u0014!B1di>\u0014\u0018B\u00012`\u0005\u0015\u0001&o\u001c9t\u0003-\u0019\u0007.\u001b7e!J|\u0007o\u001d\u0011\u0002\u0013\rD\u0017\u000e\u001c3OC6,W#\u00014\u0011\u0005\u001d\\gB\u00015j!\t\u0011F)\u0003\u0002k\t\u00061\u0001K]3eK\u001aL!\u0001\\7\u0003\rM#(/\u001b8h\u0015\tQG)\u0001\u0006dQ&dGMT1nK\u0002\n!\"\\5o\u0005\u0006\u001c7n\u001c4g+\u0005\t\bC\u0001:x\u001b\u0005\u0019(B\u0001;v\u0003!!WO]1uS>t'B\u0001<E\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0003qN\u0014aBR5oSR,G)\u001e:bi&|g.A\u0006nS:\u0014\u0015mY6pM\u001a\u0004\u0013AC7bq\n\u000b7m[8gM\u0006YQ.\u0019=CC\u000e\\wN\u001a4!\u00031\u0011\u0018M\u001c3p[\u001a\u000b7\r^8s+\u0005q\bCA\"\u0000\u0013\r\t\t\u0001\u0012\u0002\u0007\t>,(\r\\3\u0002\u001bI\fg\u000eZ8n\r\u0006\u001cGo\u001c:!\u0003\u0015\u0011Xm]3u+\t\tI\u0001E\u0003D\u0003\u0017\ty!C\u0002\u0002\u000e\u0011\u0013aa\u00149uS>t\u0007cA%\u0002\u0012%\u0019\u00111C\u001e\u0003\u0019\t\u000b7m[8gMJ+7/\u001a;\u0002\rI,7/\u001a;!\u0003I\u0019X\u000f]3sm&\u001cxN]*ue\u0006$XmZ=\u0016\u0005\u0005m\u0001c\u00010\u0002\u001e%\u0019\u0011qD0\u0003#=sWMR8s\u001f:,7\u000b\u001e:bi\u0016<\u00170A\ntkB,'O^5t_J\u001cFO]1uK\u001eL\b%\u0001\u000biC:$G.\u001b8h/\"LG.Z*u_B\u0004X\rZ\u000b\u0003\u0003O\u00012!SA\u0015\u0013\r\tYc\u000f\u0002\u0015\u0011\u0006tG\r\\5oO^C\u0017\u000e\\3Ti>\u0004\b/\u001a3\u0002+!\fg\u000e\u001a7j]\u001e<\u0006.\u001b7f'R|\u0007\u000f]3eA\u00051A(\u001b8jiz\"\"#a\r\u0002L\u00055\u0013qJA)\u0003'\n)&a\u0016\u0002ZA!\u0011\nAA\u001b!\u0011\t9$!\u000f\r\u0001\u00119\u00111\b\u0001C\u0002\u0005u\"!\u0001+\u0012\t\u0005}\u0012Q\t\t\u0004\u0007\u0006\u0005\u0013bAA\"\t\n9aj\u001c;iS:<\u0007cA\"\u0002H%\u0019\u0011\u0011\n#\u0003\u0007\u0005s\u0017\u0010C\u0003\\#\u0001\u0007Q\fC\u0003e#\u0001\u0007a\rC\u0003p#\u0001\u0007\u0011\u000fC\u0003{#\u0001\u0007\u0011\u000fC\u0003}#\u0001\u0007a\u0010C\u0005\u0002\u0006E\u0001\n\u00111\u0001\u0002\n!I\u0011qC\t\u0011\u0002\u0003\u0007\u00111\u0004\u0005\n\u0003G\t\u0002\u0013!a\u0001\u0003O\tABY1dW>4gMU3tKR,\"!a\u0004\u0002\u001b\t\f7m[8gMJ+7/\u001a;!\u000359\u0018\u000e\u001e5BkR|'+Z:fiR!\u0011QMA4!\u0011I\u0005!a\u0010\t\r\u0005%D\u00031\u0001r\u00031\u0011Xm]3u\u0005\u0006\u001c7n\u001c4g\u0003=9\u0018\u000e\u001e5NC:,\u0018\r\u001c*fg\u0016$XCAA3\u0003Y9\u0018\u000e\u001e5TkB,'O^5t_J\u001cFO]1uK\u001eLH\u0003BA3\u0003gBq!a\u0006\u0017\u0001\u0004\tY\"A\u000bxSRD'+\u001a9ms^C\u0017\u000e\\3Ti>\u0004\b/\u001a3\u0015\t\u0005\u0015\u0014\u0011\u0010\u0005\b\u0003w:\u0002\u0019AA#\u0003E\u0011X\r\u001d7z/\"LG.Z*u_B\u0004X\rZ\u0001\u0018o&$\b\u000eS1oI2,'o\u00165jY\u0016\u001cFo\u001c9qK\u0012$B!!\u001a\u0002\u0002\"9\u00111\u0011\rA\u0002\u0005\u0015\u0015a\u00055b]\u0012dWM],iS2,7\u000b^8qa\u0016$\u0007c\u00010\u0002\b&\u0019\u0011\u0011R0\u0003\u0011\u0005\u001bGo\u001c:SK\u001a\f!c^5uQ6\u000b\u0007P\u0014:PMJ+GO]5fgR!\u0011QMAH\u0011\u001d\t\t*\u0007a\u0001\u0003'\u000ba\"\\1y\u001dJ|eMU3ue&,7\u000fE\u0002D\u0003+K1!a&E\u0005\rIe\u000e^\u0001\u0006aJ|\u0007o]\u0001\u0005G>\u0004\u00180\u0006\u0003\u0002 \u0006\u0015FCEAQ\u0003O\u000bI+a+\u0002.\u0006=\u0016\u0011WAZ\u0003k\u0003B!\u0013\u0001\u0002$B!\u0011qGAS\t\u001d\tYd\u0007b\u0001\u0003{AqaW\u000e\u0011\u0002\u0003\u0007Q\fC\u0004e7A\u0005\t\u0019\u00014\t\u000f=\\\u0002\u0013!a\u0001c\"9!p\u0007I\u0001\u0002\u0004\t\bb\u0002?\u001c!\u0003\u0005\rA \u0005\n\u0003\u000bY\u0002\u0013!a\u0001\u0003\u0013A\u0011\"a\u0006\u001c!\u0003\u0005\r!a\u0007\t\u0013\u0005\r2\u0004%AA\u0002\u0005\u001d\u0012AD2paf$C-\u001a4bk2$H%M\u000b\u0005\u0003w\u000b\t.\u0006\u0002\u0002>*\u001aQ,a0,\u0005\u0005\u0005\u0007\u0003BAb\u0003\u001bl!!!2\u000b\t\u0005\u001d\u0017\u0011Z\u0001\nk:\u001c\u0007.Z2lK\u0012T1!a3E\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003\u001f\f)MA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$q!a\u000f\u001d\u0005\u0004\ti$\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\t\u0005]\u00171\\\u000b\u0003\u00033T3AZA`\t\u001d\tY$\bb\u0001\u0003{\tabY8qs\u0012\"WMZ1vYR$3'\u0006\u0003\u0002b\u0006\u0015XCAArU\r\t\u0018q\u0018\u0003\b\u0003wq\"\u0019AA\u001f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIQ*B!!9\u0002l\u00129\u00111H\u0010C\u0002\u0005u\u0012AD2paf$C-\u001a4bk2$H%N\u000b\u0005\u0003c\f)0\u0006\u0002\u0002t*\u001aa0a0\u0005\u000f\u0005m\u0002E1\u0001\u0002>\u0005q1m\u001c9zI\u0011,g-Y;mi\u00122T\u0003BA~\u0003\u007f,\"!!@+\t\u0005%\u0011q\u0018\u0003\b\u0003w\t#\u0019AA\u001f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uI]*BA!\u0002\u0003\nU\u0011!q\u0001\u0016\u0005\u00037\ty\fB\u0004\u0002<\t\u0012\r!!\u0010\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%qU!!q\u0002B\n+\t\u0011\tB\u000b\u0003\u0002(\u0005}FaBA\u001eG\t\u0007\u0011QH\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\te\u0001\u0003\u0002B\u000e\u0005Ki!A!\b\u000b\t\t}!\u0011E\u0001\u0005Y\u0006twM\u0003\u0002\u0003$\u0005!!.\u0019<b\u0013\ra'QD\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003'\u000ba\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0003\u0002F\t=\u0002\"\u0003B\u0019M\u0005\u0005\t\u0019AAJ\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011!q\u0007\t\u0007\u0005s\u0011y$!\u0012\u000e\u0005\tm\"b\u0001B\u001f\t\u0006Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\t\u0005#1\b\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0003H\t5\u0003cA\"\u0003J%\u0019!1\n#\u0003\u000f\t{w\u000e\\3b]\"I!\u0011\u0007\u0015\u0002\u0002\u0003\u0007\u0011QI\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0003\u001a\tM\u0003\"\u0003B\u0019S\u0005\u0005\t\u0019AAJ\u0003!A\u0017m\u001d5D_\u0012,GCAAJ\u0003!!xn\u0015;sS:<GC\u0001B\r\u0003\u0019)\u0017/^1mgR!!q\tB1\u0011%\u0011\t\u0004LA\u0001\u0002\u0004\t)%A\u000eCC\u000e\\wN\u001a4P]\u001a\u000b\u0017\u000e\\;sK>\u0003H/[8og&k\u0007\u000f\u001c\t\u0003\u0013:\u001aBA\f\"\u0003jA!!1\u000eB9\u001b\t\u0011iG\u0003\u0003\u0003p\t\u0005\u0012AA5p\u0013\rI&Q\u000e\u000b\u0003\u0005K\nQ!\u00199qYf,BA!\u001f\u0003\u0000Q\u0011\"1\u0010BA\u0005\u0007\u0013)Ia\"\u0003\n\n-%Q\u0012BH!\u0011I\u0005A! \u0011\t\u0005]\"q\u0010\u0003\b\u0003w\t$\u0019AA\u001f\u0011\u0015Y\u0016\u00071\u0001^\u0011\u0015!\u0017\u00071\u0001g\u0011\u0015y\u0017\u00071\u0001r\u0011\u0015Q\u0018\u00071\u0001r\u0011\u0015a\u0018\u00071\u0001\u007f\u0011%\t)!\rI\u0001\u0002\u0004\tI\u0001C\u0005\u0002\u0018E\u0002\n\u00111\u0001\u0002\u001c!I\u00111E\u0019\u0011\u0002\u0003\u0007\u0011qE\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%mU!\u00111 BK\t\u001d\tYD\rb\u0001\u0003{\tq\"\u00199qYf$C-\u001a4bk2$HeN\u000b\u0005\u0005\u000b\u0011Y\nB\u0004\u0002<M\u0012\r!!\u0010\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIa*BAa\u0004\u0003\"\u00129\u00111\b\u001bC\u0002\u0005u\u0012aB;oCB\u0004H._\u000b\u0005\u0005O\u0013I\f\u0006\u0003\u0003*\nE\u0006#B\"\u0002\f\t-\u0006CD\"\u0003.v3\u0017/\u001d@\u0002\n\u0005m\u0011qE\u0005\u0004\u0005_#%A\u0002+va2,\u0007\bC\u0005\u00034V\n\t\u00111\u0001\u00036\u0006\u0019\u0001\u0010\n\u0019\u0011\t%\u0003!q\u0017\t\u0005\u0003o\u0011I\fB\u0004\u0002<U\u0012\r!!\u0010\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00137+\u0011\tYPa0\u0005\u000f\u0005mbG1\u0001\u0002>\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uI]*BA!\u0002\u0003F\u00129\u00111H\u001cC\u0002\u0005u\u0012a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0003(\u0006\u0003\u0003\u0010\t-GaBA\u001eq\t\u0007\u0011QH\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0005#\u0004BAa\u0007\u0003T&!!Q\u001bB\u000f\u0005\u0019y%M[3di\u0002")
public final class BackoffOnFailureOptionsImpl<T>
implements BackoffOnFailureOptions,
Product,
Serializable {
    private final Props childProps;
    private final String childName;
    private final FiniteDuration minBackoff;
    private final FiniteDuration maxBackoff;
    private final double randomFactor;
    private final Option<BackoffReset> reset;
    private final OneForOneStrategy supervisorStrategy;
    private final HandlingWhileStopped handlingWhileStopped;
    private final BackoffReset backoffReset;

    public static <T> HandlingWhileStopped $lessinit$greater$default$8() {
        return BackoffOnFailureOptionsImpl$.MODULE$.$lessinit$greater$default$8();
    }

    public static <T> OneForOneStrategy $lessinit$greater$default$7() {
        return BackoffOnFailureOptionsImpl$.MODULE$.$lessinit$greater$default$7();
    }

    public static <T> Option<BackoffReset> $lessinit$greater$default$6() {
        return BackoffOnFailureOptionsImpl$.MODULE$.$lessinit$greater$default$6();
    }

    public static <T> Option<Tuple8<Props, String, FiniteDuration, FiniteDuration, Object, Option<BackoffReset>, OneForOneStrategy, HandlingWhileStopped>> unapply(BackoffOnFailureOptionsImpl<T> x$0) {
        return BackoffOnFailureOptionsImpl$.MODULE$.unapply(x$0);
    }

    public static <T> HandlingWhileStopped apply$default$8() {
        return BackoffOnFailureOptionsImpl$.MODULE$.apply$default$8();
    }

    public static <T> OneForOneStrategy apply$default$7() {
        return BackoffOnFailureOptionsImpl$.MODULE$.apply$default$7();
    }

    public static <T> Option<BackoffReset> apply$default$6() {
        return BackoffOnFailureOptionsImpl$.MODULE$.apply$default$6();
    }

    public static <T> BackoffOnFailureOptionsImpl<T> apply(Props childProps, String childName, FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, Option<BackoffReset> reset, OneForOneStrategy supervisorStrategy, HandlingWhileStopped handlingWhileStopped) {
        return BackoffOnFailureOptionsImpl$.MODULE$.apply(childProps, childName, minBackoff, maxBackoff, randomFactor, reset, supervisorStrategy, handlingWhileStopped);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Props childProps() {
        return this.childProps;
    }

    public String childName() {
        return this.childName;
    }

    public FiniteDuration minBackoff() {
        return this.minBackoff;
    }

    public FiniteDuration maxBackoff() {
        return this.maxBackoff;
    }

    public double randomFactor() {
        return this.randomFactor;
    }

    public Option<BackoffReset> reset() {
        return this.reset;
    }

    public OneForOneStrategy supervisorStrategy() {
        return this.supervisorStrategy;
    }

    public HandlingWhileStopped handlingWhileStopped() {
        return this.handlingWhileStopped;
    }

    private BackoffReset backoffReset() {
        return this.backoffReset;
    }

    @Override
    public BackoffOnFailureOptionsImpl<Nothing$> withAutoReset(FiniteDuration resetBackoff) {
        Some<BackoffReset> x$1 = new Some<BackoffReset>(new AutoReset(resetBackoff));
        Props x$2 = this.copy$default$1();
        String x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        FiniteDuration x$5 = this.copy$default$4();
        double x$6 = this.copy$default$5();
        OneForOneStrategy x$7 = this.copy$default$7();
        HandlingWhileStopped x$8 = this.copy$default$8();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$1, x$7, x$8);
    }

    @Override
    public BackoffOnFailureOptionsImpl<Nothing$> withManualReset() {
        Some<BackoffReset> x$1 = new Some<BackoffReset>(ManualReset$.MODULE$);
        Props x$2 = this.copy$default$1();
        String x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        FiniteDuration x$5 = this.copy$default$4();
        double x$6 = this.copy$default$5();
        OneForOneStrategy x$7 = this.copy$default$7();
        HandlingWhileStopped x$8 = this.copy$default$8();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$1, x$7, x$8);
    }

    @Override
    public BackoffOnFailureOptionsImpl<Nothing$> withSupervisorStrategy(OneForOneStrategy supervisorStrategy) {
        OneForOneStrategy x$1 = supervisorStrategy;
        Props x$2 = this.copy$default$1();
        String x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        FiniteDuration x$5 = this.copy$default$4();
        double x$6 = this.copy$default$5();
        Option<BackoffReset> x$7 = this.copy$default$6();
        HandlingWhileStopped x$8 = this.copy$default$8();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$1, x$8);
    }

    @Override
    public BackoffOnFailureOptionsImpl<Nothing$> withReplyWhileStopped(Object replyWhileStopped) {
        ReplyWith x$1 = new ReplyWith(replyWhileStopped);
        Props x$2 = this.copy$default$1();
        String x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        FiniteDuration x$5 = this.copy$default$4();
        double x$6 = this.copy$default$5();
        Option<BackoffReset> x$7 = this.copy$default$6();
        OneForOneStrategy x$8 = this.copy$default$7();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$1);
    }

    @Override
    public BackoffOnFailureOptionsImpl<Nothing$> withHandlerWhileStopped(ActorRef handlerWhileStopped) {
        ForwardTo x$1 = new ForwardTo(handlerWhileStopped);
        Props x$2 = this.copy$default$1();
        String x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        FiniteDuration x$5 = this.copy$default$4();
        double x$6 = this.copy$default$5();
        Option<BackoffReset> x$7 = this.copy$default$6();
        OneForOneStrategy x$8 = this.copy$default$7();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$1);
    }

    @Override
    public BackoffOnFailureOptionsImpl<Nothing$> withMaxNrOfRetries(int maxNrOfRetries) {
        OneForOneStrategy x$1 = this.supervisorStrategy().withMaxNrOfRetries(maxNrOfRetries);
        Props x$2 = this.copy$default$1();
        String x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        FiniteDuration x$5 = this.copy$default$4();
        double x$6 = this.copy$default$5();
        Option<BackoffReset> x$7 = this.copy$default$6();
        HandlingWhileStopped x$8 = this.copy$default$8();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$1, x$8);
    }

    @Override
    public Props props() {
        block0: {
            Predef$.MODULE$.require(this.minBackoff().$greater(Duration$.MODULE$.Zero()), (Function0<Object>)(Function0<String> & Serializable)() -> "minBackoff must be > 0");
            Predef$.MODULE$.require(this.maxBackoff().$greater$eq(this.minBackoff()), (Function0<Object>)(Function0<String> & Serializable)() -> "maxBackoff must be >= minBackoff");
            Predef$.MODULE$.require(0.0 <= this.randomFactor() && this.randomFactor() <= 1.0, (Function0<Object>)(Function0<String> & Serializable)() -> "randomFactor must be between 0.0 and 1.0");
            BackoffReset backoffReset = this.backoffReset();
            if (!(backoffReset instanceof AutoReset)) break block0;
            AutoReset autoReset = (AutoReset)backoffReset;
            FiniteDuration resetBackoff = autoReset.resetBackoff();
            Predef$.MODULE$.require(this.minBackoff().$less$eq(resetBackoff) && resetBackoff.$less$eq(this.maxBackoff()));
        }
        return Props$.MODULE$.apply((Function0<BackoffOnRestartSupervisor> & Serializable)() -> new BackoffOnRestartSupervisor(this.childProps(), this.childName(), this.minBackoff(), this.maxBackoff(), this.backoffReset(), this.randomFactor(), this.supervisorStrategy(), this.handlingWhileStopped()), ClassTag$.MODULE$.apply(BackoffOnRestartSupervisor.class));
    }

    public <T> BackoffOnFailureOptionsImpl<T> copy(Props childProps, String childName, FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, Option<BackoffReset> reset, OneForOneStrategy supervisorStrategy, HandlingWhileStopped handlingWhileStopped) {
        return new BackoffOnFailureOptionsImpl<T>(childProps, childName, minBackoff, maxBackoff, randomFactor, reset, supervisorStrategy, handlingWhileStopped);
    }

    public <T> Props copy$default$1() {
        return this.childProps();
    }

    public <T> String copy$default$2() {
        return this.childName();
    }

    public <T> FiniteDuration copy$default$3() {
        return this.minBackoff();
    }

    public <T> FiniteDuration copy$default$4() {
        return this.maxBackoff();
    }

    public <T> double copy$default$5() {
        return this.randomFactor();
    }

    public <T> Option<BackoffReset> copy$default$6() {
        return this.reset();
    }

    public <T> OneForOneStrategy copy$default$7() {
        return this.supervisorStrategy();
    }

    public <T> HandlingWhileStopped copy$default$8() {
        return this.handlingWhileStopped();
    }

    @Override
    public String productPrefix() {
        return "BackoffOnFailureOptionsImpl";
    }

    @Override
    public int productArity() {
        return 8;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.childProps();
            }
            case 1: {
                return this.childName();
            }
            case 2: {
                return this.minBackoff();
            }
            case 3: {
                return this.maxBackoff();
            }
            case 4: {
                return BoxesRunTime.boxToDouble(this.randomFactor());
            }
            case 5: {
                return this.reset();
            }
            case 6: {
                return this.supervisorStrategy();
            }
            case 7: {
                return this.handlingWhileStopped();
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
        return x$1 instanceof BackoffOnFailureOptionsImpl;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "childProps";
            }
            case 1: {
                return "childName";
            }
            case 2: {
                return "minBackoff";
            }
            case 3: {
                return "maxBackoff";
            }
            case 4: {
                return "randomFactor";
            }
            case 5: {
                return "reset";
            }
            case 6: {
                return "supervisorStrategy";
            }
            case 7: {
                return "handlingWhileStopped";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.childProps()));
        n = Statics.mix(n, Statics.anyHash(this.childName()));
        n = Statics.mix(n, Statics.anyHash(this.minBackoff()));
        n = Statics.mix(n, Statics.anyHash(this.maxBackoff()));
        n = Statics.mix(n, Statics.doubleHash(this.randomFactor()));
        n = Statics.mix(n, Statics.anyHash(this.reset()));
        n = Statics.mix(n, Statics.anyHash(this.supervisorStrategy()));
        n = Statics.mix(n, Statics.anyHash(this.handlingWhileStopped()));
        return Statics.finalizeHash(n, 8);
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
        if (!(object instanceof BackoffOnFailureOptionsImpl)) return false;
        boolean bl = true;
        if (!bl) return false;
        BackoffOnFailureOptionsImpl backoffOnFailureOptionsImpl = (BackoffOnFailureOptionsImpl)x$1;
        if (this.randomFactor() != backoffOnFailureOptionsImpl.randomFactor()) return false;
        Props props = this.childProps();
        Props props2 = backoffOnFailureOptionsImpl.childProps();
        if (props == null) {
            if (props2 != null) {
                return false;
            }
        } else if (!((Object)props).equals(props2)) return false;
        String string = this.childName();
        String string2 = backoffOnFailureOptionsImpl.childName();
        if (string == null) {
            if (string2 != null) {
                return false;
            }
        } else if (!string.equals(string2)) return false;
        FiniteDuration finiteDuration = this.minBackoff();
        FiniteDuration finiteDuration2 = backoffOnFailureOptionsImpl.minBackoff();
        if (finiteDuration == null) {
            if (finiteDuration2 != null) {
                return false;
            }
        } else if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
        FiniteDuration finiteDuration3 = this.maxBackoff();
        FiniteDuration finiteDuration4 = backoffOnFailureOptionsImpl.maxBackoff();
        if (finiteDuration3 == null) {
            if (finiteDuration4 != null) {
                return false;
            }
        } else if (!((Object)finiteDuration3).equals(finiteDuration4)) return false;
        Option<BackoffReset> option = this.reset();
        Option<BackoffReset> option2 = backoffOnFailureOptionsImpl.reset();
        if (option == null) {
            if (option2 != null) {
                return false;
            }
        } else if (!option.equals(option2)) return false;
        OneForOneStrategy oneForOneStrategy = this.supervisorStrategy();
        OneForOneStrategy oneForOneStrategy2 = backoffOnFailureOptionsImpl.supervisorStrategy();
        if (oneForOneStrategy == null) {
            if (oneForOneStrategy2 != null) {
                return false;
            }
        } else if (!((Object)oneForOneStrategy).equals(oneForOneStrategy2)) return false;
        HandlingWhileStopped handlingWhileStopped = this.handlingWhileStopped();
        HandlingWhileStopped handlingWhileStopped2 = backoffOnFailureOptionsImpl.handlingWhileStopped();
        if (handlingWhileStopped == null) {
            if (handlingWhileStopped2 == null) return true;
            return false;
        } else {
            if (!handlingWhileStopped.equals(handlingWhileStopped2)) return false;
            return true;
        }
    }

    public BackoffOnFailureOptionsImpl(Props childProps, String childName, FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, Option<BackoffReset> reset, OneForOneStrategy supervisorStrategy, HandlingWhileStopped handlingWhileStopped) {
        this.childProps = childProps;
        this.childName = childName;
        this.minBackoff = minBackoff;
        this.maxBackoff = maxBackoff;
        this.randomFactor = randomFactor;
        this.reset = reset;
        this.supervisorStrategy = supervisorStrategy;
        this.handlingWhileStopped = handlingWhileStopped;
        Product.$init$(this);
        this.backoffReset = (BackoffReset)reset.getOrElse((Function0<AutoReset> & Serializable)() -> new AutoReset(this.minBackoff()));
    }
}

