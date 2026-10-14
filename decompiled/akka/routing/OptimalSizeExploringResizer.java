/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.OptimalSizeExploringResizer$;
import akka.routing.Resizer;
import akka.routing.Routee;
import com.typesafe.config.Config;
import java.io.Serializable;
import java.time.LocalDateTime;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.collection.immutable.IndexedSeq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t%ga\u0002)R!\u0003\r\nA\u0016\u0005\u0006C\u00021\tAY\u0004\u0006qFC\t)\u001f\u0004\u0006!FC\tI\u001f\u0005\b\u0003+\u0019A\u0011AA\f\u000b\u001d\tIb\u0001\u0001R\u000371q!!\t\u0004\u0001F\u000b\u0019\u0003\u0003\u0006\u0002&\u0019\u0011)\u001a!C\u0001\u0003OA!\"!\u000f\u0007\u0005#\u0005\u000b\u0011BA\u0015\u0011)\tYD\u0002BK\u0002\u0013\u0005\u0011Q\b\u0005\u000b\u0003\u007f1!\u0011#Q\u0001\n\u0005m\u0001bBA\u000b\r\u0011\u0005\u0011\u0011\t\u0005\n\u0003\u00172\u0011\u0011!C\u0001\u0003\u001bB\u0011\"a\u0015\u0007#\u0003%\t!!\u0016\t\u0013\u0005-d!%A\u0005\u0002\u00055\u0004\"CA9\r\u0005\u0005I\u0011IA:\u0011%\t\tIBA\u0001\n\u0003\ti\u0004C\u0005\u0002\u0004\u001a\t\t\u0011\"\u0001\u0002\u0006\"I\u0011\u0011\u0013\u0004\u0002\u0002\u0013\u0005\u00131\u0013\u0005\n\u0003;3\u0011\u0011!C\u0001\u0003?C\u0011\"!+\u0007\u0003\u0003%\t%a+\t\u0013\u0005=f!!A\u0005B\u0005E\u0006\"CAZ\r\u0005\u0005I\u0011IA[\u0011%\t9LBA\u0001\n\u0003\nIl\u0002\u0006\u0002>\u000e\t\t\u0011#\u0001R\u0003\u007f3!\"!\t\u0004\u0003\u0003E\t!UAa\u0011\u001d\t)\"\u0007C\u0001\u00033D\u0011\"a-\u001a\u0003\u0003%)%!.\t\u0013\u0005m\u0017$!A\u0005\u0002\u0006u\u0007\"CAr3\u0005\u0005I\u0011QAs\u0011%\t90GA\u0001\n\u0013\tIPB\u0004\u0003\u0002\r\u0001\u0015Ka\u0001\t\u0015\t\u0015qD!f\u0001\n\u0003\u00119\u0001\u0003\u0006\u0003\f}\u0011\t\u0012)A\u0005\u0005\u0013A!B!\u0004 \u0005+\u0007I\u0011\u0001B\b\u0011%\u0011\tb\bB\tB\u0003%Q\u000f\u0003\u0006\u0003\u0014}\u0011)\u001a!C\u0001\u0003{A!B!\u0006 \u0005#\u0005\u000b\u0011BA\u000e\u0011)\u00119b\bBK\u0002\u0013\u0005!q\u0002\u0005\n\u00053y\"\u0011#Q\u0001\nUDq!!\u0006 \t\u0003\u0011Y\u0002C\u0005\u0002L}\t\t\u0011\"\u0001\u0003(!I\u00111K\u0010\u0012\u0002\u0013\u0005!\u0011\u0007\u0005\n\u0003Wz\u0012\u0013!C\u0001\u0005kA\u0011B!\u000f #\u0003%\t!!\u001c\t\u0013\tmr$%A\u0005\u0002\tU\u0002\"CA9?\u0005\u0005I\u0011IA:\u0011%\t\tiHA\u0001\n\u0003\ti\u0004C\u0005\u0002\u0004~\t\t\u0011\"\u0001\u0003>!I\u0011\u0011S\u0010\u0002\u0002\u0013\u0005\u00131\u0013\u0005\n\u0003;{\u0012\u0011!C\u0001\u0005\u0003B\u0011\"!+ \u0003\u0003%\tE!\u0012\t\u0013\u0005=v$!A\u0005B\u0005E\u0006\"CAZ?\u0005\u0005I\u0011IA[\u0011%\t9lHA\u0001\n\u0003\u0012Ie\u0002\u0006\u0003N\r\t\t\u0011#\u0001R\u0005\u001f2!B!\u0001\u0004\u0003\u0003E\t!\u0015B)\u0011\u001d\t)\u0002\u000fC\u0001\u00053B\u0011\"a-9\u0003\u0003%)%!.\t\u0013\u0005m\u0007(!A\u0005\u0002\nm\u0003\"\u0003B3qE\u0005I\u0011\u0001B\u0019\u0011%\u00119\u0007OI\u0001\n\u0003\u0011)\u0004C\u0005\u0003ja\n\n\u0011\"\u0001\u0002n!I!1\u000e\u001d\u0012\u0002\u0013\u0005!Q\u0007\u0005\n\u0003GD\u0014\u0011!CA\u0005[B\u0011B!\u001f9#\u0003%\tA!\r\t\u0013\tm\u0004(%A\u0005\u0002\tU\u0002\"\u0003B?qE\u0005I\u0011AA7\u0011%\u0011y\bOI\u0001\n\u0003\u0011)\u0004C\u0005\u0002xb\n\t\u0011\"\u0003\u0002z\u00169!\u0011Q\u0002\u0001#\n\r\u0005bBAn\u0007\u0011\u0005!Q\u0015\u0005\n\u0003c\u001a\u0011\u0011!C!\u0003gB\u0011\"!!\u0004\u0003\u0003%\t!!\u0010\t\u0013\u0005\r5!!A\u0005\u0002\t\u0005\u0007\"CAI\u0007\u0005\u0005I\u0011IAJ\u0011%\tijAA\u0001\n\u0003\u0011)\rC\u0005\u00020\u000e\t\t\u0011\"\u0011\u00022\"I\u00111W\u0002\u0002\u0002\u0013\u0005\u0013Q\u0017\u0005\n\u0003o\u001c\u0011\u0011!C\u0005\u0003s\u00141d\u00149uS6\fGnU5{K\u0016C\b\u000f\\8sS:<'+Z:ju\u0016\u0014(B\u0001*T\u0003\u001d\u0011x.\u001e;j]\u001eT\u0011\u0001V\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0007\u00019V\f\u0005\u0002Y76\t\u0011LC\u0001[\u0003\u0015\u00198-\u00197b\u0013\ta\u0016L\u0001\u0004B]f\u0014VM\u001a\t\u0003=~k\u0011!U\u0005\u0003AF\u0013qAU3tSj,'/\u0001\nsKB|'\u000f^'fgN\fw-Z\"pk:$HcA2ggB\u0011\u0001\fZ\u0005\u0003Kf\u0013A!\u00168ji\")q-\u0001a\u0001Q\u0006q1-\u001e:sK:$(k\\;uK\u0016\u001c\bcA5oa6\t!N\u0003\u0002lY\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003[f\u000b!bY8mY\u0016\u001cG/[8o\u0013\ty'N\u0001\u0006J]\u0012,\u00070\u001a3TKF\u0004\"AX9\n\u0005I\f&A\u0002*pkR,W\rC\u0003u\u0003\u0001\u0007Q/\u0001\bnKN\u001c\u0018mZ3D_VtG/\u001a:\u0011\u0005a3\u0018BA<Z\u0005\u0011auN\\4\u00027=\u0003H/[7bYNK'0Z#ya2|'/\u001b8h%\u0016\u001c\u0018N_3s!\tq6a\u0005\u0003\u0004/nt\bC\u0001-}\u0013\ti\u0018LA\u0004Qe>$Wo\u0019;\u0011\u0007}\fyA\u0004\u0003\u0002\u0002\u0005-a\u0002BA\u0002\u0003\u0013i!!!\u0002\u000b\u0007\u0005\u001dQ+\u0001\u0004=e>|GOP\u0005\u00025&\u0019\u0011QB-\u0002\u000fA\f7m[1hK&!\u0011\u0011CA\n\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\r\ti!W\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003e\u0014\u0001\u0002U8pYNK'0\u001a\t\u00041\u0006u\u0011bAA\u00103\n\u0019\u0011J\u001c;\u0003-UsG-\u001a:Vi&d\u0017N_1uS>t7\u000b\u001e:fC.\u001cBAB,|}\u0006)1\u000f^1siV\u0011\u0011\u0011\u0006\t\u0005\u0003W\t)$\u0004\u0002\u0002.)!\u0011qFA\u0019\u0003\u0011!\u0018.\\3\u000b\u0005\u0005M\u0012\u0001\u00026bm\u0006LA!a\u000e\u0002.\tiAj\\2bY\u0012\u000bG/\u001a+j[\u0016\faa\u001d;beR\u0004\u0013A\u00055jO\",7\u000f^+uS2L'0\u0019;j_:,\"!a\u0007\u0002'!Lw\r[3tiV#\u0018\u000e\\5{CRLwN\u001c\u0011\u0015\r\u0005\r\u0013qIA%!\r\t)EB\u0007\u0002\u0007!9\u0011QE\u0006A\u0002\u0005%\u0002bBA\u001e\u0017\u0001\u0007\u00111D\u0001\u0005G>\u0004\u0018\u0010\u0006\u0004\u0002D\u0005=\u0013\u0011\u000b\u0005\n\u0003Ka\u0001\u0013!a\u0001\u0003SA\u0011\"a\u000f\r!\u0003\u0005\r!a\u0007\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011\u0011q\u000b\u0016\u0005\u0003S\tIf\u000b\u0002\u0002\\A!\u0011QLA4\u001b\t\tyF\u0003\u0003\u0002b\u0005\r\u0014!C;oG\",7m[3e\u0015\r\t)'W\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA5\u0003?\u0012\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\"!a\u001c+\t\u0005m\u0011\u0011L\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005U\u0004\u0003BA<\u0003{j!!!\u001f\u000b\t\u0005m\u0014\u0011G\u0001\u0005Y\u0006tw-\u0003\u0003\u0002\u0000\u0005e$AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\u0005\u001d\u0015Q\u0012\t\u00041\u0006%\u0015bAAF3\n\u0019\u0011I\\=\t\u0013\u0005=\u0015#!AA\u0002\u0005m\u0011a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002\u0016B1\u0011qSAM\u0003\u000fk\u0011\u0001\\\u0005\u0004\u00037c'\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!!)\u0002(B\u0019\u0001,a)\n\u0007\u0005\u0015\u0016LA\u0004C_>dW-\u00198\t\u0013\u0005=5#!AA\u0002\u0005\u001d\u0015A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!!\u001e\u0002.\"I\u0011q\u0012\u000b\u0002\u0002\u0003\u0007\u00111D\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u00111D\u0001\ti>\u001cFO]5oOR\u0011\u0011QO\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005\u0005\u00161\u0018\u0005\n\u0003\u001f;\u0012\u0011!a\u0001\u0003\u000f\u000ba#\u00168eKJ,F/\u001b7ju\u0006$\u0018n\u001c8TiJ,\u0017m\u001b\t\u0004\u0003\u000bJ2#B\r\u0002D\u0006=\u0007CCAc\u0003\u0017\fI#a\u0007\u0002D5\u0011\u0011q\u0019\u0006\u0004\u0003\u0013L\u0016a\u0002:v]RLW.Z\u0005\u0005\u0003\u001b\f9MA\tBEN$(/Y2u\rVt7\r^5p]J\u0002B!!5\u0002X6\u0011\u00111\u001b\u0006\u0005\u0003+\f\t$\u0001\u0002j_&!\u0011\u0011CAj)\t\ty,A\u0003baBd\u0017\u0010\u0006\u0004\u0002D\u0005}\u0017\u0011\u001d\u0005\b\u0003Ka\u0002\u0019AA\u0015\u0011\u001d\tY\u0004\ba\u0001\u00037\tq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002h\u0006M\b#\u0002-\u0002j\u00065\u0018bAAv3\n1q\n\u001d;j_:\u0004r\u0001WAx\u0003S\tY\"C\u0002\u0002rf\u0013a\u0001V;qY\u0016\u0014\u0004\"CA{;\u0005\u0005\t\u0019AA\"\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003w\u0004B!a\u001e\u0002~&!\u0011q`A=\u0005\u0019y%M[3di\na!+Z:ju\u0016\u0014VmY8sIN!qdV>\u007f\u0003Y)h\u000eZ3skRLG.\u001b>bi&|gn\u0015;sK\u0006\\WC\u0001B\u0005!\u0015A\u0016\u0011^A\"\u0003])h\u000eZ3skRLG.\u001b>bi&|gn\u0015;sK\u0006\\\u0007%\u0001\u0007nKN\u001c\u0018mZ3D_VtG/F\u0001v\u00035iWm]:bO\u0016\u001cu.\u001e8uA\u0005\u0001Bo\u001c;bYF+X-^3MK:<G\u000f[\u0001\u0012i>$\u0018\r\\)vKV,G*\u001a8hi\"\u0004\u0013!C2iK\u000e\\G+[7f\u0003)\u0019\u0007.Z2l)&lW\r\t\u000b\u000b\u0005;\u0011yB!\t\u0003$\t\u0015\u0002cAA#?!I!Q\u0001\u0015\u0011\u0002\u0003\u0007!\u0011\u0002\u0005\t\u0005\u001bA\u0003\u0013!a\u0001k\"I!1\u0003\u0015\u0011\u0002\u0003\u0007\u00111\u0004\u0005\t\u0005/A\u0003\u0013!a\u0001kRQ!Q\u0004B\u0015\u0005W\u0011iCa\f\t\u0013\t\u0015\u0011\u0006%AA\u0002\t%\u0001\u0002\u0003B\u0007SA\u0005\t\u0019A;\t\u0013\tM\u0011\u0006%AA\u0002\u0005m\u0001\u0002\u0003B\fSA\u0005\t\u0019A;\u0016\u0005\tM\"\u0006\u0002B\u0005\u00033*\"Aa\u000e+\u0007U\fI&\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iQ!\u0011q\u0011B \u0011%\ty\tMA\u0001\u0002\u0004\tY\u0002\u0006\u0003\u0002\"\n\r\u0003\"CAHe\u0005\u0005\t\u0019AAD)\u0011\t)Ha\u0012\t\u0013\u0005=5'!AA\u0002\u0005mA\u0003BAQ\u0005\u0017B\u0011\"a$7\u0003\u0003\u0005\r!a\"\u0002\u0019I+7/\u001b>f%\u0016\u001cwN\u001d3\u0011\u0007\u0005\u0015\u0003hE\u00039\u0005'\ny\r\u0005\u0007\u0002F\nU#\u0011B;\u0002\u001cU\u0014i\"\u0003\u0003\u0003X\u0005\u001d'!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8oiQ\u0011!q\n\u000b\u000b\u0005;\u0011iFa\u0018\u0003b\t\r\u0004\"\u0003B\u0003wA\u0005\t\u0019\u0001B\u0005\u0011!\u0011ia\u000fI\u0001\u0002\u0004)\b\"\u0003B\nwA\u0005\t\u0019AA\u000e\u0011!\u00119b\u000fI\u0001\u0002\u0004)\u0018aD1qa2LH\u0005Z3gCVdG\u000fJ\u0019\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uII\nq\"\u00199qYf$C-\u001a4bk2$HeM\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%iQ!!q\u000eB<!\u0015A\u0016\u0011\u001eB9!%A&1\u000fB\u0005k\u0006mQ/C\u0002\u0003ve\u0013a\u0001V;qY\u0016$\u0004\"CA{\u0001\u0006\u0005\t\u0019\u0001B\u000f\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%c\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uII\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u001a\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$CG\u0001\bQKJ4wN]7b]\u000e,Gj\\4\u0011\u0011\t\u0015%Q\u0012BJ\u0005+sAAa\"\u0003\nB\u0019\u00111A-\n\u0007\t-\u0015,\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0005\u001f\u0013\tJA\u0002NCBT1Aa#Z!\r\t)%\u0002\t\u0005\u0005/\u0013\t+\u0004\u0002\u0003\u001a*!!1\u0014BO\u0003!!WO]1uS>t'b\u0001BP3\u0006Q1m\u001c8dkJ\u0014XM\u001c;\n\t\t\r&\u0011\u0014\u0002\t\tV\u0014\u0018\r^5p]R!!q\u0015BU!\tq\u0006\u0001C\u0004\u0003,\u001e\u0003\rA!,\u0002\u0015I,7/\u001b>fe\u000e3w\r\u0005\u0003\u00030\nuVB\u0001BY\u0015\u0011\u0011\u0019L!.\u0002\r\r|gNZ5h\u0015\u0011\u00119L!/\u0002\u0011QL\b/Z:bM\u0016T!Aa/\u0002\u0007\r|W.\u0003\u0003\u0003@\nE&AB\"p]\u001aLw\r\u0006\u0003\u0002\b\n\r\u0007\"CAH\u0015\u0006\u0005\t\u0019AA\u000e)\u0011\t\tKa2\t\u0013\u0005=E*!AA\u0002\u0005\u001d\u0005")
public interface OptimalSizeExploringResizer
extends Resizer {
    public static boolean canEqual(Object x$1) {
        return OptimalSizeExploringResizer$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return OptimalSizeExploringResizer$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return OptimalSizeExploringResizer$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return OptimalSizeExploringResizer$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return OptimalSizeExploringResizer$.MODULE$.productPrefix();
    }

    public static OptimalSizeExploringResizer apply(Config resizerCfg) {
        return OptimalSizeExploringResizer$.MODULE$.apply(resizerCfg);
    }

    public static Iterator<String> productElementNames() {
        return OptimalSizeExploringResizer$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return OptimalSizeExploringResizer$.MODULE$.productElementName(n);
    }

    public void reportMessageCount(IndexedSeq<Routee> var1, long var2);

    public static class ResizeRecord
    implements Product,
    Serializable {
        private final Option<UnderUtilizationStreak> underutilizationStreak;
        private final long messageCount;
        private final int totalQueueLength;
        private final long checkTime;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Option<UnderUtilizationStreak> underutilizationStreak() {
            return this.underutilizationStreak;
        }

        public long messageCount() {
            return this.messageCount;
        }

        public int totalQueueLength() {
            return this.totalQueueLength;
        }

        public long checkTime() {
            return this.checkTime;
        }

        public ResizeRecord copy(Option<UnderUtilizationStreak> underutilizationStreak, long messageCount, int totalQueueLength, long checkTime) {
            return new ResizeRecord(underutilizationStreak, messageCount, totalQueueLength, checkTime);
        }

        public Option<UnderUtilizationStreak> copy$default$1() {
            return this.underutilizationStreak();
        }

        public long copy$default$2() {
            return this.messageCount();
        }

        public int copy$default$3() {
            return this.totalQueueLength();
        }

        public long copy$default$4() {
            return this.checkTime();
        }

        @Override
        public String productPrefix() {
            return "ResizeRecord";
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
                    return this.underutilizationStreak();
                }
                case 1: {
                    return BoxesRunTime.boxToLong(this.messageCount());
                }
                case 2: {
                    return BoxesRunTime.boxToInteger(this.totalQueueLength());
                }
                case 3: {
                    return BoxesRunTime.boxToLong(this.checkTime());
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
            return x$1 instanceof ResizeRecord;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "underutilizationStreak";
                }
                case 1: {
                    return "messageCount";
                }
                case 2: {
                    return "totalQueueLength";
                }
                case 3: {
                    return "checkTime";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.anyHash(this.underutilizationStreak()));
            n = Statics.mix(n, Statics.longHash(this.messageCount()));
            n = Statics.mix(n, this.totalQueueLength());
            n = Statics.mix(n, Statics.longHash(this.checkTime()));
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
            if (!(object instanceof ResizeRecord)) return false;
            boolean bl = true;
            if (!bl) return false;
            ResizeRecord resizeRecord = (ResizeRecord)x$1;
            if (this.messageCount() != resizeRecord.messageCount()) return false;
            if (this.totalQueueLength() != resizeRecord.totalQueueLength()) return false;
            if (this.checkTime() != resizeRecord.checkTime()) return false;
            Option<UnderUtilizationStreak> option = this.underutilizationStreak();
            Option<UnderUtilizationStreak> option2 = resizeRecord.underutilizationStreak();
            if (option == null) {
                if (option2 != null) {
                    return false;
                }
            } else if (!option.equals(option2)) return false;
            if (!resizeRecord.canEqual(this)) return false;
            return true;
        }

        public ResizeRecord(Option<UnderUtilizationStreak> underutilizationStreak, long messageCount, int totalQueueLength, long checkTime) {
            this.underutilizationStreak = underutilizationStreak;
            this.messageCount = messageCount;
            this.totalQueueLength = totalQueueLength;
            this.checkTime = checkTime;
            Product.$init$(this);
        }
    }

    public static class UnderUtilizationStreak
    implements Product,
    Serializable {
        private final LocalDateTime start;
        private final int highestUtilization;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public LocalDateTime start() {
            return this.start;
        }

        public int highestUtilization() {
            return this.highestUtilization;
        }

        public UnderUtilizationStreak copy(LocalDateTime start2, int highestUtilization) {
            return new UnderUtilizationStreak(start2, highestUtilization);
        }

        public LocalDateTime copy$default$1() {
            return this.start();
        }

        public int copy$default$2() {
            return this.highestUtilization();
        }

        @Override
        public String productPrefix() {
            return "UnderUtilizationStreak";
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
                    return this.start();
                }
                case 1: {
                    return BoxesRunTime.boxToInteger(this.highestUtilization());
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
            return x$1 instanceof UnderUtilizationStreak;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "start";
                }
                case 1: {
                    return "highestUtilization";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.anyHash(this.start()));
            n = Statics.mix(n, this.highestUtilization());
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
            if (!(object instanceof UnderUtilizationStreak)) return false;
            boolean bl = true;
            if (!bl) return false;
            UnderUtilizationStreak underUtilizationStreak = (UnderUtilizationStreak)x$1;
            if (this.highestUtilization() != underUtilizationStreak.highestUtilization()) return false;
            LocalDateTime localDateTime = this.start();
            LocalDateTime localDateTime2 = underUtilizationStreak.start();
            if (localDateTime == null) {
                if (localDateTime2 != null) {
                    return false;
                }
            } else if (!((Object)localDateTime).equals(localDateTime2)) return false;
            if (!underUtilizationStreak.canEqual(this)) return false;
            return true;
        }

        public UnderUtilizationStreak(LocalDateTime start2, int highestUtilization) {
            this.start = start2;
            this.highestUtilization = highestUtilization;
            Product.$init$(this);
        }
    }
}

