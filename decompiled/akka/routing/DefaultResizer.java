/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorCell;
import akka.actor.ActorRef;
import akka.actor.ActorRefWithCell;
import akka.actor.Cell;
import akka.routing.ActorRefRoutee;
import akka.routing.DefaultResizer$;
import akka.routing.Resizer;
import akka.routing.Routee;
import com.typesafe.config.Config;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Tuple7;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.collection.immutable.IndexedSeq;
import scala.math.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tut!\u0002$H\u0011\u0003ce!\u0002(H\u0011\u0003{\u0005\"B3\u0002\t\u00031\u0007\"B4\u0002\t\u0003A\u0007b\u0002B\u0012\u0003\u0011\u0005!Q\u0005\u0005\tO\u0006\t\t\u0011\"!\u00030!I!qH\u0001\u0012\u0002\u0013\u0005\u0011q\u0012\u0005\n\u0005\u0003\n\u0011\u0013!C\u0001\u0003\u001fC\u0011Ba\u0011\u0002#\u0003%\t!a$\t\u0013\t\u0015\u0013!%A\u0005\u0002\u0005-\u0006\"\u0003B$\u0003E\u0005I\u0011AAV\u0011%\u0011I%AI\u0001\n\u0003\tY\u000bC\u0005\u0003L\u0005\t\n\u0011\"\u0001\u0002\u0010\"I!QJ\u0001\u0002\u0002\u0013\u0005%q\n\u0005\n\u0005;\n\u0011\u0013!C\u0001\u0003\u001fC\u0011Ba\u0018\u0002#\u0003%\t!a$\t\u0013\t\u0005\u0014!%A\u0005\u0002\u0005=\u0005\"\u0003B2\u0003E\u0005I\u0011AAV\u0011%\u0011)'AI\u0001\n\u0003\tY\u000bC\u0005\u0003h\u0005\t\n\u0011\"\u0001\u0002,\"I!\u0011N\u0001\u0012\u0002\u0013\u0005\u0011q\u0012\u0005\n\u0003k\u000b\u0011\u0011!C!\u0003oC\u0001\"!3\u0002\u0003\u0003%\ta\u001c\u0005\n\u0003\u0017\f\u0011\u0011!C\u0001\u0005WB\u0011\"!7\u0002\u0003\u0003%\t%a7\t\u0013\u0005\u0015\u0018!!A\u0005\u0002\t=\u0004\"CAy\u0003\u0005\u0005I\u0011IAz\u0011%\t)0AA\u0001\n\u0003\n9\u0010C\u0005\u0003t\u0005\t\t\u0011\"\u0003\u0003v\u0019!aj\u0012!k\u0011!qWD!f\u0001\n\u0003y\u0007\u0002C:\u001e\u0005#\u0005\u000b\u0011\u00029\t\u0011Ql\"Q3A\u0005\u0002=D\u0001\"^\u000f\u0003\u0012\u0003\u0006I\u0001\u001d\u0005\tmv\u0011)\u001a!C\u0001_\"Aq/\bB\tB\u0003%\u0001\u000f\u0003\u0005y;\tU\r\u0011\"\u0001z\u0011!iXD!E!\u0002\u0013Q\b\u0002\u0003@\u001e\u0005+\u0007I\u0011A=\t\u0011}l\"\u0011#Q\u0001\niD\u0011\"!\u0001\u001e\u0005+\u0007I\u0011A=\t\u0013\u0005\rQD!E!\u0002\u0013Q\b\"CA\u0003;\tU\r\u0011\"\u0001p\u0011%\t9!\bB\tB\u0003%\u0001\u000f\u0003\u0004f;\u0011\u0005\u0011\u0011\u0002\u0005\u0007Kv!\t!!\u0007\t\u000f\u0005\rR\u0004\"\u0001\u0002&!9\u0011qG\u000f\u0005B\u0005e\u0002bBA+;\u0011\u0005\u0011q\u000b\u0005\b\u0003;jB\u0011AA0\u0011\u001d\t\u0019'\bC\u0001\u0003KBq!a\u001b\u001e\t\u0003\ti\u0007C\u0004\u0002tu!\t!!\u001e\t\u0013\u0005mT$!A\u0005\u0002\u0005u\u0004\"CAG;E\u0005I\u0011AAH\u0011%\t)+HI\u0001\n\u0003\ty\tC\u0005\u0002(v\t\n\u0011\"\u0001\u0002\u0010\"I\u0011\u0011V\u000f\u0012\u0002\u0013\u0005\u00111\u0016\u0005\n\u0003_k\u0012\u0013!C\u0001\u0003WC\u0011\"!-\u001e#\u0003%\t!a+\t\u0013\u0005MV$%A\u0005\u0002\u0005=\u0005\"CA[;\u0005\u0005I\u0011IA\\\u0011!\tI-HA\u0001\n\u0003y\u0007\"CAf;\u0005\u0005I\u0011AAg\u0011%\tI.HA\u0001\n\u0003\nY\u000eC\u0005\u0002fv\t\t\u0011\"\u0001\u0002h\"I\u00111^\u000f\u0002\u0002\u0013\u0005\u0013Q\u001e\u0005\n\u0003cl\u0012\u0011!C!\u0003gD\u0011\"!>\u001e\u0003\u0003%\t%a>\t\u0013\u0005eX$!A\u0005B\u0005m\u0018A\u0004#fM\u0006,H\u000e\u001e*fg&TXM\u001d\u0006\u0003\u0011&\u000bqA]8vi&twMC\u0001K\u0003\u0011\t7n[1\u0004\u0001A\u0011Q*A\u0007\u0002\u000f\nqA)\u001a4bk2$(+Z:ju\u0016\u00148\u0003B\u0001Q-f\u0003\"!\u0015+\u000e\u0003IS\u0011aU\u0001\u0006g\u000e\fG.Y\u0005\u0003+J\u0013a!\u00118z%\u00164\u0007CA)X\u0013\tA&KA\u0004Qe>$Wo\u0019;\u0011\u0005i\u0013gBA.a\u001d\tav,D\u0001^\u0015\tq6*\u0001\u0004=e>|GOP\u0005\u0002'&\u0011\u0011MU\u0001\ba\u0006\u001c7.Y4f\u0013\t\u0019GM\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002b%\u00061A(\u001b8jiz\"\u0012\u0001T\u0001\u0006CB\u0004H.\u001f\u000b\u0004S\n-\u0001CA'\u001e'\u0015i\u0002k\u001b,Z!\tiE.\u0003\u0002n\u000f\n9!+Z:ju\u0016\u0014\u0018A\u00037po\u0016\u0014(i\\;oIV\t\u0001\u000f\u0005\u0002Rc&\u0011!O\u0015\u0002\u0004\u0013:$\u0018a\u00037po\u0016\u0014(i\\;oI\u0002\n!\"\u001e9qKJ\u0014u.\u001e8e\u0003-)\b\u000f]3s\u0005>,h\u000e\u001a\u0011\u0002#A\u0014Xm]:ve\u0016$\u0006N]3tQ>dG-\u0001\nqe\u0016\u001c8/\u001e:f)\"\u0014Xm\u001d5pY\u0012\u0004\u0013A\u0003:b[B,\bOU1uKV\t!\u0010\u0005\u0002Rw&\u0011AP\u0015\u0002\u0007\t>,(\r\\3\u0002\u0017I\fW\u000e];q%\u0006$X\rI\u0001\u0011E\u0006\u001c7n\u001c4g)\"\u0014Xm\u001d5pY\u0012\f\u0011CY1dW>4g\r\u00165sKNDw\u000e\u001c3!\u0003-\u0011\u0017mY6pM\u001a\u0014\u0016\r^3\u0002\u0019\t\f7m[8gMJ\u000bG/\u001a\u0011\u0002#5,7o]1hKN\u0004VM\u001d*fg&TX-\u0001\nnKN\u001c\u0018mZ3t!\u0016\u0014(+Z:ju\u0016\u0004CcD5\u0002\f\u00055\u0011qBA\t\u0003'\t)\"a\u0006\t\u000f9d\u0003\u0013!a\u0001a\"9A\u000f\fI\u0001\u0002\u0004\u0001\bb\u0002<-!\u0003\u0005\r\u0001\u001d\u0005\bq2\u0002\n\u00111\u0001{\u0011\u001dqH\u0006%AA\u0002iD\u0001\"!\u0001-!\u0003\u0005\rA\u001f\u0005\t\u0003\u000ba\u0003\u0013!a\u0001aR)\u0011.a\u0007\u0002 !1\u0011QD\u0017A\u0002A\fQ\u0001\\8xKJDa!!\t.\u0001\u0004\u0001\u0018!B;qa\u0016\u0014\u0018aD5t)&lWMR8s%\u0016\u001c\u0018N_3\u0015\t\u0005\u001d\u0012Q\u0006\t\u0004#\u0006%\u0012bAA\u0016%\n9!i\\8mK\u0006t\u0007bBA\u0018]\u0001\u0007\u0011\u0011G\u0001\u000f[\u0016\u001c8/Y4f\u0007>,h\u000e^3s!\r\t\u00161G\u0005\u0004\u0003k\u0011&\u0001\u0002'p]\u001e\faA]3tSj,Gc\u00019\u0002<!9\u0011QH\u0018A\u0002\u0005}\u0012AD2veJ,g\u000e\u001e*pkR,Wm\u001d\t\u0007\u0003\u0003\nY%a\u0014\u000e\u0005\u0005\r#\u0002BA#\u0003\u000f\n\u0011\"[7nkR\f'\r\\3\u000b\u0007\u0005%#+\u0001\u0006d_2dWm\u0019;j_:LA!!\u0014\u0002D\tQ\u0011J\u001c3fq\u0016$7+Z9\u0011\u00075\u000b\t&C\u0002\u0002T\u001d\u0013aAU8vi\u0016,\u0017\u0001C2ba\u0006\u001c\u0017\u000e^=\u0015\u0007A\fI\u0006C\u0004\u0002\\A\u0002\r!a\u0010\u0002\u000fI|W\u000f^3fg\u0006A\u0001O]3tgV\u0014X\rF\u0002q\u0003CBq!a\u00172\u0001\u0004\ty$\u0001\u0004gS2$XM\u001d\u000b\u0006a\u0006\u001d\u0014\u0011\u000e\u0005\u0007\u0003;\u0012\u0004\u0019\u00019\t\r\u0005U#\u00071\u0001q\u0003\u0019\u0011\u0018-\u001c9vaR)\u0001/a\u001c\u0002r!1\u0011QL\u001aA\u0002ADa!!\u00164\u0001\u0004\u0001\u0018a\u00022bG.|gM\u001a\u000b\u0006a\u0006]\u0014\u0011\u0010\u0005\u0007\u0003;\"\u0004\u0019\u00019\t\r\u0005UC\u00071\u0001q\u0003\u0011\u0019w\u000e]=\u0015\u001f%\fy(!!\u0002\u0004\u0006\u0015\u0015qQAE\u0003\u0017CqA\\\u001b\u0011\u0002\u0003\u0007\u0001\u000fC\u0004ukA\u0005\t\u0019\u00019\t\u000fY,\u0004\u0013!a\u0001a\"9\u00010\u000eI\u0001\u0002\u0004Q\bb\u0002@6!\u0003\u0005\rA\u001f\u0005\t\u0003\u0003)\u0004\u0013!a\u0001u\"A\u0011QA\u001b\u0011\u0002\u0003\u0007\u0001/\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\u0005E%f\u00019\u0002\u0014.\u0012\u0011Q\u0013\t\u0005\u0003/\u000b\t+\u0004\u0002\u0002\u001a*!\u00111TAO\u0003%)hn\u00195fG.,GMC\u0002\u0002 J\u000b!\"\u00198o_R\fG/[8o\u0013\u0011\t\u0019+!'\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%g\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\"TCAAWU\rQ\u00181S\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00136\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIY\nabY8qs\u0012\"WMZ1vYR$s'A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003s\u0003B!a/\u0002F6\u0011\u0011Q\u0018\u0006\u0005\u0003\u007f\u000b\t-\u0001\u0003mC:<'BAAb\u0003\u0011Q\u0017M^1\n\t\u0005\u001d\u0017Q\u0018\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!\u0011qZAk!\r\t\u0016\u0011[\u0005\u0004\u0003'\u0014&aA!os\"A\u0011q[ \u0002\u0002\u0003\u0007\u0001/A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003;\u0004b!a8\u0002b\u0006=WBAA$\u0013\u0011\t\u0019/a\u0012\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003O\tI\u000fC\u0005\u0002X\u0006\u000b\t\u00111\u0001\u0002P\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\tI,a<\t\u0011\u0005]')!AA\u0002A\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002a\u0006AAo\\*ue&tw\r\u0006\u0002\u0002:\u00061Q-];bYN$B!a\n\u0002~\"I\u0011q[#\u0002\u0002\u0003\u0007\u0011q\u001a\u0015\b;\t\u0005!q\u0001B\u0005!\r\t&1A\u0005\u0004\u0005\u000b\u0011&\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\t\u0001b\u0002B\u0007\u0007\u0001\u0007!qB\u0001\u000ee\u0016\u001c\u0018N_3s\u0007>tg-[4\u0011\t\tE!qD\u0007\u0003\u0005'QAA!\u0006\u0003\u0018\u000511m\u001c8gS\u001eTAA!\u0007\u0003\u001c\u0005AA/\u001f9fg\u00064WM\u0003\u0002\u0003\u001e\u0005\u00191m\\7\n\t\t\u0005\"1\u0003\u0002\u0007\u0007>tg-[4\u0002\u0015\u0019\u0014x.\\\"p]\u001aLw\r\u0006\u0003\u0003(\t5\u0002\u0003B)\u0003*%L1Aa\u000bS\u0005\u0019y\u0005\u000f^5p]\"9!Q\u0002\u0003A\u0002\t=AcD5\u00032\tM\"Q\u0007B\u001c\u0005s\u0011YD!\u0010\t\u000f9,\u0001\u0013!a\u0001a\"9A/\u0002I\u0001\u0002\u0004\u0001\bb\u0002<\u0006!\u0003\u0005\r\u0001\u001d\u0005\bq\u0016\u0001\n\u00111\u0001{\u0011\u001dqX\u0001%AA\u0002iD\u0001\"!\u0001\u0006!\u0003\u0005\rA\u001f\u0005\t\u0003\u000b)\u0001\u0013!a\u0001a\u0006y\u0011\r\u001d9ms\u0012\"WMZ1vYR$\u0013'A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00133\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\u001a\u0014aD1qa2LH\u0005Z3gCVdG\u000f\n\u001b\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIU\nq\"\u00199qYf$C-\u001a4bk2$HEN\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%o\u00059QO\\1qa2LH\u0003\u0002B)\u00053\u0002R!\u0015B\u0015\u0005'\u0002\"\"\u0015B+aB\u0004(P\u001f>q\u0013\r\u00119F\u0015\u0002\u0007)V\u0004H.Z\u001c\t\u0011\tmS\"!AA\u0002%\f1\u0001\u001f\u00131\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%c\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uII\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u001a\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$C'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%N\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000f\n\u001c\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00138)\u0011\tyM!\u001c\t\u0011\u0005]w#!AA\u0002A$B!a\n\u0003r!I\u0011q[\r\u0002\u0002\u0003\u0007\u0011qZ\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0005o\u0002B!a/\u0003z%!!1PA_\u0005\u0019y%M[3di\u0002")
public class DefaultResizer
implements Resizer,
Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final int lowerBound;
    private final int upperBound;
    private final int pressureThreshold;
    private final double rampupRate;
    private final double backoffThreshold;
    private final double backoffRate;
    private final int messagesPerResize;

    public static int $lessinit$greater$default$7() {
        return DefaultResizer$.MODULE$.$lessinit$greater$default$7();
    }

    public static double $lessinit$greater$default$6() {
        return DefaultResizer$.MODULE$.$lessinit$greater$default$6();
    }

    public static double $lessinit$greater$default$5() {
        return DefaultResizer$.MODULE$.$lessinit$greater$default$5();
    }

    public static double $lessinit$greater$default$4() {
        return DefaultResizer$.MODULE$.$lessinit$greater$default$4();
    }

    public static int $lessinit$greater$default$3() {
        return DefaultResizer$.MODULE$.$lessinit$greater$default$3();
    }

    public static int $lessinit$greater$default$2() {
        return DefaultResizer$.MODULE$.$lessinit$greater$default$2();
    }

    public static int $lessinit$greater$default$1() {
        return DefaultResizer$.MODULE$.$lessinit$greater$default$1();
    }

    public static Option<Tuple7<Object, Object, Object, Object, Object, Object, Object>> unapply(DefaultResizer x$0) {
        return DefaultResizer$.MODULE$.unapply(x$0);
    }

    public static int apply$default$7() {
        return DefaultResizer$.MODULE$.apply$default$7();
    }

    public static double apply$default$6() {
        return DefaultResizer$.MODULE$.apply$default$6();
    }

    public static double apply$default$5() {
        return DefaultResizer$.MODULE$.apply$default$5();
    }

    public static double apply$default$4() {
        return DefaultResizer$.MODULE$.apply$default$4();
    }

    public static int apply$default$3() {
        return DefaultResizer$.MODULE$.apply$default$3();
    }

    public static int apply$default$2() {
        return DefaultResizer$.MODULE$.apply$default$2();
    }

    public static int apply$default$1() {
        return DefaultResizer$.MODULE$.apply$default$1();
    }

    public static DefaultResizer apply(int lowerBound, int upperBound, int pressureThreshold, double rampupRate, double backoffThreshold, double backoffRate, int messagesPerResize) {
        return DefaultResizer$.MODULE$.apply(lowerBound, upperBound, pressureThreshold, rampupRate, backoffThreshold, backoffRate, messagesPerResize);
    }

    public static Option<DefaultResizer> fromConfig(Config resizerConfig) {
        return DefaultResizer$.MODULE$.fromConfig(resizerConfig);
    }

    public static DefaultResizer apply(Config resizerConfig) {
        return DefaultResizer$.MODULE$.apply(resizerConfig);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int lowerBound() {
        return this.lowerBound;
    }

    public int upperBound() {
        return this.upperBound;
    }

    public int pressureThreshold() {
        return this.pressureThreshold;
    }

    public double rampupRate() {
        return this.rampupRate;
    }

    public double backoffThreshold() {
        return this.backoffThreshold;
    }

    public double backoffRate() {
        return this.backoffRate;
    }

    public int messagesPerResize() {
        return this.messagesPerResize;
    }

    @Override
    public boolean isTimeForResize(long messageCounter) {
        return messageCounter % (long)this.messagesPerResize() == 0L;
    }

    @Override
    public int resize(IndexedSeq<Routee> currentRoutees) {
        return this.capacity(currentRoutees);
    }

    public int capacity(IndexedSeq<Routee> routees) {
        int press;
        int delta;
        int currentSize = routees.size();
        int proposed = currentSize + (delta = this.filter(press = this.pressure(routees), currentSize));
        if (proposed < this.lowerBound()) {
            return delta + (this.lowerBound() - proposed);
        }
        if (proposed > this.upperBound()) {
            return delta - (proposed - this.upperBound());
        }
        return delta;
    }

    public int pressure(IndexedSeq<Routee> routees) {
        return routees.count((Function1<Routee, Object> & Serializable)x0$1 -> BoxesRunTime.boxToBoolean(DefaultResizer.$anonfun$pressure$1(this, x0$1)));
    }

    public int filter(int pressure, int capacity) {
        return this.rampup(pressure, capacity) + this.backoff(pressure, capacity);
    }

    public int rampup(int pressure, int capacity) {
        if (pressure < capacity) {
            return 0;
        }
        return (int)package$.MODULE$.ceil(this.rampupRate() * (double)capacity);
    }

    public int backoff(int pressure, int capacity) {
        if (this.backoffThreshold() > 0.0 && this.backoffRate() > 0.0 && capacity > 0 && (double)pressure / (double)capacity < this.backoffThreshold()) {
            return (int)package$.MODULE$.floor(-1.0 * this.backoffRate() * (double)capacity);
        }
        return 0;
    }

    public DefaultResizer copy(int lowerBound, int upperBound, int pressureThreshold, double rampupRate, double backoffThreshold, double backoffRate, int messagesPerResize) {
        return new DefaultResizer(lowerBound, upperBound, pressureThreshold, rampupRate, backoffThreshold, backoffRate, messagesPerResize);
    }

    public int copy$default$1() {
        return this.lowerBound();
    }

    public int copy$default$2() {
        return this.upperBound();
    }

    public int copy$default$3() {
        return this.pressureThreshold();
    }

    public double copy$default$4() {
        return this.rampupRate();
    }

    public double copy$default$5() {
        return this.backoffThreshold();
    }

    public double copy$default$6() {
        return this.backoffRate();
    }

    public int copy$default$7() {
        return this.messagesPerResize();
    }

    @Override
    public String productPrefix() {
        return "DefaultResizer";
    }

    @Override
    public int productArity() {
        return 7;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return BoxesRunTime.boxToInteger(this.lowerBound());
            }
            case 1: {
                return BoxesRunTime.boxToInteger(this.upperBound());
            }
            case 2: {
                return BoxesRunTime.boxToInteger(this.pressureThreshold());
            }
            case 3: {
                return BoxesRunTime.boxToDouble(this.rampupRate());
            }
            case 4: {
                return BoxesRunTime.boxToDouble(this.backoffThreshold());
            }
            case 5: {
                return BoxesRunTime.boxToDouble(this.backoffRate());
            }
            case 6: {
                return BoxesRunTime.boxToInteger(this.messagesPerResize());
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
        return x$1 instanceof DefaultResizer;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "lowerBound";
            }
            case 1: {
                return "upperBound";
            }
            case 2: {
                return "pressureThreshold";
            }
            case 3: {
                return "rampupRate";
            }
            case 4: {
                return "backoffThreshold";
            }
            case 5: {
                return "backoffRate";
            }
            case 6: {
                return "messagesPerResize";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.lowerBound());
        n = Statics.mix(n, this.upperBound());
        n = Statics.mix(n, this.pressureThreshold());
        n = Statics.mix(n, Statics.doubleHash(this.rampupRate()));
        n = Statics.mix(n, Statics.doubleHash(this.backoffThreshold()));
        n = Statics.mix(n, Statics.doubleHash(this.backoffRate()));
        n = Statics.mix(n, this.messagesPerResize());
        return Statics.finalizeHash(n, 7);
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
        if (!(object instanceof DefaultResizer)) return false;
        boolean bl = true;
        if (!bl) return false;
        DefaultResizer defaultResizer = (DefaultResizer)x$1;
        if (this.lowerBound() != defaultResizer.lowerBound()) return false;
        if (this.upperBound() != defaultResizer.upperBound()) return false;
        if (this.pressureThreshold() != defaultResizer.pressureThreshold()) return false;
        if (this.rampupRate() != defaultResizer.rampupRate()) return false;
        if (this.backoffThreshold() != defaultResizer.backoffThreshold()) return false;
        if (this.backoffRate() != defaultResizer.backoffRate()) return false;
        if (this.messagesPerResize() != defaultResizer.messagesPerResize()) return false;
        if (!defaultResizer.canEqual(this)) return false;
        return true;
    }

    public static final /* synthetic */ boolean $anonfun$pressure$1(DefaultResizer $this, Routee x0$1) {
        ActorRefRoutee actorRefRoutee;
        ActorRef a;
        Routee routee = x0$1;
        if (routee instanceof ActorRefRoutee && (a = (actorRefRoutee = (ActorRefRoutee)routee).ref()) instanceof ActorRefWithCell) {
            ActorRefWithCell actorRefWithCell = (ActorRefWithCell)a;
            Cell cell = actorRefWithCell.underlying();
            if (cell instanceof ActorCell) {
                ActorCell actorCell = (ActorCell)cell;
                int n = $this.pressureThreshold();
                switch (n) {
                    case 1: {
                        return actorCell.mailbox().isScheduled() && actorCell.mailbox().hasMessages();
                    }
                }
                if (n < 1) {
                    return actorCell.mailbox().isScheduled() && actorCell.currentMessage() != null;
                }
                return actorCell.mailbox().numberOfMessages() >= n;
            }
            int n = $this.pressureThreshold();
            switch (n) {
                case 1: {
                    return cell.hasMessages();
                }
            }
            if (n < 1) {
                return true;
            }
            return cell.numberOfMessages() >= n;
        }
        return false;
    }

    public DefaultResizer(int lowerBound, int upperBound, int pressureThreshold, double rampupRate, double backoffThreshold, double backoffRate, int messagesPerResize) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.pressureThreshold = pressureThreshold;
        this.rampupRate = rampupRate;
        this.backoffThreshold = backoffThreshold;
        this.backoffRate = backoffRate;
        this.messagesPerResize = messagesPerResize;
        Product.$init$(this);
        if (lowerBound < 0) {
            throw new IllegalArgumentException(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("lowerBound must be >= 0, was: [%s]"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{BoxesRunTime.boxToInteger(lowerBound)})));
        }
        if (upperBound < 0) {
            throw new IllegalArgumentException(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("upperBound must be >= 0, was: [%s]"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{BoxesRunTime.boxToInteger(upperBound)})));
        }
        if (upperBound < lowerBound) {
            throw new IllegalArgumentException(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("upperBound must be >= lowerBound, was: [%s] < [%s]"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{BoxesRunTime.boxToInteger(upperBound), BoxesRunTime.boxToInteger(lowerBound)})));
        }
        if (rampupRate < 0.0) {
            throw new IllegalArgumentException(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("rampupRate must be >= 0.0, was [%s]"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{BoxesRunTime.boxToDouble(rampupRate)})));
        }
        if (backoffThreshold > 1.0) {
            throw new IllegalArgumentException(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("backoffThreshold must be <= 1.0, was [%s]"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{BoxesRunTime.boxToDouble(backoffThreshold)})));
        }
        if (backoffRate < 0.0) {
            throw new IllegalArgumentException(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("backoffRate must be >= 0.0, was [%s]"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{BoxesRunTime.boxToDouble(backoffRate)})));
        }
        if (messagesPerResize <= 0) {
            throw new IllegalArgumentException(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("messagesPerResize must be > 0, was [%s]"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{BoxesRunTime.boxToInteger(messagesPerResize)})));
        }
    }

    public DefaultResizer(int lower, int upper) {
        this(lower, upper, DefaultResizer$.MODULE$.$lessinit$greater$default$3(), DefaultResizer$.MODULE$.$lessinit$greater$default$4(), DefaultResizer$.MODULE$.$lessinit$greater$default$5(), DefaultResizer$.MODULE$.$lessinit$greater$default$6(), DefaultResizer$.MODULE$.$lessinit$greater$default$7());
    }
}

