/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import java.util.Arrays;
import scala.$less$colon$less;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractSeqView;
import scala.collection.Factory;
import scala.collection.IndexedSeq;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableFactoryDefaults;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.LazyZip2;
import scala.collection.Searching;
import scala.collection.Seq;
import scala.collection.SeqOps;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.View;
import scala.collection.WithFilter;
import scala.collection.immutable.ArraySeq;
import scala.collection.immutable.ArraySeq$;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Range;
import scala.collection.immutable.Set;
import scala.collection.immutable.Stream;
import scala.collection.immutable.Vector;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.StringBuilder;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.package$;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\u0011=gaB<y!\u0003\r\t! \u0005\b\u0003[\u0001A\u0011AA\u0018\u0011\u001d\t9\u0004\u0001C!\u0003sAq!!\u0010\u0001\t\u0003\ny\u0004C\u0004\u0002V\u0001!\t%a\u0016\t\u000f\u0005\u001d\u0004\u0001\"\u0011\u0002j!9\u0011Q\u000f\u0001\u0005B\u0005e\u0002bBA<\u0001\u0011\u0005\u0013\u0011\u0010\u0005\b\u0003\u000b\u0003A\u0011IAD\u0011\u001d\tY\t\u0001C!\u0003\u001bCq!!%\u0001\t\u0003\n\u0019\nC\u0004\u0002\u0018\u0002!\t%!'\t\u000f\u0005\u001d\u0006\u0001\"\u0001\u0002*\"9A\u0011\u0015\u0001\u0005\u0002\u0011\r\u0006b\u0002CY\u0001\u0011\u0005A1\u0017\u0005\b\t\u000f\u0002A\u0011\tCa\u0011!!i\r\u0001Q\u0005R\r}saBA^q\"\u0005\u0011Q\u0018\u0004\u0007obD\t!a0\t\u000f\u0005E'\u0003\"\u0001\u0002T\u00161\u0011Q\u001b\n\u0005\u0003/4a!!>\u0013\u0001\u0005]\bB\u0003B\u0003+\t\u0005\t\u0015!\u0003\u0003\b!9\u0011\u0011[\u000b\u0005\u0002\t-\u0001b\u0002B\t+\u0011\u0005!1\u0003\u0005\b\u00053)B\u0011\u0001B\u000e\u0011\u001d\u0011i\"\u0006C\u0001\u0005?AqAa\n\u0016\t\u0003\u0012Y\u0002C\u0004\u0003*U!\tEa\u000b\u0007\r\t}\"\u0003\u0001B!\u0011)\u0011)!\bB\u0001B\u0003%!1\f\u0005\u000b\u0003\u001bj\"\u0011!Q\u0001\n\tu\u0003bBAi;\u0011\u0005!q\f\u0005\b\u0005#iB\u0011\u0001B4\u0011\u001d\u0011I\"\bC\u0001\u000571aA!\u001c\u0013\u0001\t=\u0004B\u0003B\u0003G\t\u0005\t\u0015!\u0003\u0003~!Q\u0011QM\u0012\u0003\u0002\u0003\u0006IAa\u001e\t\u000f\u0005E7\u0005\"\u0001\u0003\u0000!9!\u0011C\u0012\u0005\u0002\t\u001d\u0005b\u0002B\rG\u0011\u0005!1\u0004\u0004\u0007\u0005\u001b\u0013\u0002Aa$\t\u0015\u0005\u0015\u0014F!A!\u0002\u0013\u00119\n\u0003\u0006\u0003\u0006%\u0012\t\u0011)A\u0005\u0005;Cq!!5*\t\u0003\u0011y\nC\u0004\u0003\u0012%\"\tAa*\t\u000f\te\u0011\u0006\"\u0001\u0003\u001c\u00191!Q\u0016\n\u0001\u0005_C!B!00\u0005\u0003\u0005\u000b\u0011\u0002B`\u0011)\t)l\fB\u0001B\u0003%!q\u0018\u0005\b\u0003#|C\u0011\u0001Ba\u0011\u001d\u0011\tb\fC\u0001\u0005\u0013DqA!\u00070\t\u0003\u0011YB\u0002\u0004\u0003PJ\u0001!\u0011\u001b\u0005\u000b\u0005\u000b)$\u0011!Q\u0001\n\tm\u0007bBAik\u0011\u0005!Q\u001c\u0005\b\u0005#)D\u0011\u0001Br\u0011\u001d\u0011I\"\u000eC\u0001\u00057AqA!\b6\t\u0003\u0011I\u000fC\u0004\u0003(U\"\tEa\u0007\t\u000f\t%R\u0007\"\u0011\u0003,\u00191!q\u001e\n\u0001\u0005cD!B!\u0002>\u0005\u0003\u0005\u000b\u0011\u0002B\u0000\u0011)\ti(\u0010B\u0001B\u0003%\u0011q\u0010\u0005\b\u0003#lD\u0011AB\u0001\u0011\u001d\u0011\t\"\u0010C\u0001\u0007\u0013AqA!\u0007>\t\u0003\u0011YB\u0002\u0004\u0004\u0010I\u00011\u0011\u0003\u0005\u000b\u0005\u000b\u0019%\u0011!Q\u0001\n\r}\u0001BCA?\u0007\n\u0005\t\u0015!\u0003\u0002\u0000!9\u0011\u0011[\"\u0005\u0002\r\u0005\u0002\u0002CB\u0015\u0007\u0002\u0006I!a \t\u000f\te1\t\"\u0001\u0003\u001c!9!\u0011C\"\u0005\u0002\r-bABBB%\u0001\u0019)\t\u0003\u0006\u0003\u0006)\u0013\t\u0011)A\u0005\u0007'C!\"! K\u0005\u0003\u0005\u000b\u0011BA@\u0011\u001d\t\tN\u0013C\u0001\u0007+CqA!\u0007K\t\u0003\u0011Y\u0002C\u0004\u0003\u0012)#\ta!(\t\u000f\u0005\u0015%\n\"\u0011\u0004.\u001a111\u0017\n\u0001\u0007kC!B!\u0002R\u0005\u0003\u0005\u000b\u0011BBb\u0011)\ti(\u0015B\u0001B\u0003%\u0011q\u0010\u0005\b\u0003#\fF\u0011ABc\u0011!\u0019i-\u0015Q\u0001\n\u0005}\u0004b\u0002B\r#\u0012\u0005!1\u0004\u0005\b\u0005#\tF\u0011ABh\r\u0019\u0019\tO\u0005\u0001\u0004d\"Q!Q\u0001-\u0003\u0002\u0003\u0006Ka!<\t\u0015\r5\u0007L!A!\u0002\u0013\ty\b\u0003\u0006\u0004pb\u0013\t\u0011)A\u0005\u0007cDq!!5Y\t\u0013\u0019i\u0010C\u0004\u0002Rb#\t\u0001b\u0002\u0007\u0011\u00115\u0001\f)A\u0005\t\u001fAq!!5_\t\u0003!\t\u0002\u0003\u0006\u0005\u0018yC)\u0019)C\u0005\t3AqA!\u0005_\t\u0003!i\u0002C\u0004\u0003\u001ay#\tAa\u0007\t\u000f\tua\f\"\u0001\u0005\"!9!q\u00050\u0005B\tm\u0001b\u0002B\u0015=\u0012\u0005#1\u0006\u0005\b\tKqF\u0011\tC\u0014\u0011\u001d\t)H\u0018C!\twAq\u0001\"\u0010_\t#\"y\u0004C\u0004\u0005Hy#\t\u0005\"\u0013\t\u0011\u0011m\u0003\f)Q\u0005\u0005[A!\u0002\"\u001aY\u0011\u000b\u0007K\u0011\u0002C4\u0011!!y\u0007\u0017Q\u0005\n\u0011E\u0004b\u0002B\t1\u0012\u0005A1\u000f\u0005\b\u00053AF\u0011\u0001B\u000e\u0011\u001d\u0011i\u0002\u0017C\u0001\tCAqAa\nY\t\u0003\u0012Y\u0002C\u0004\u0003*a#\tEa\u000b\t\u000f\u0011\u0015\u0002\f\"\u0011\u0005x!9\u0011Q\u000f-\u0005B\u0011m\u0002b\u0002C\u001f1\u0012ECq\b\u0005\b\t\u000fBF\u0011\tCB\u0011%!\tJEA\u0001\n\u0013!\u0019JA\u0004TKF4\u0016.Z<\u000b\u0005eT\u0018AC2pY2,7\r^5p]*\t10A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0007y\f\u0019b\u0005\u0004\u0001\u007f\u0006\u001d\u00111\u0006\t\u0005\u0003\u0003\t\u0019!D\u0001{\u0013\r\t)A\u001f\u0002\u0007\u0003:L(+\u001a4\u0011\u0015\u0005%\u00111BA\b\u0003K\tY#D\u0001y\u0013\r\ti\u0001\u001f\u0002\u0007'\u0016\fx\n]:\u0011\t\u0005E\u00111\u0003\u0007\u0001\t!\t)\u0002\u0001CC\u0002\u0005]!!A!\u0012\t\u0005e\u0011q\u0004\t\u0005\u0003\u0003\tY\"C\u0002\u0002\u001ei\u0014qAT8uQ&tw\r\u0005\u0003\u0002\u0002\u0005\u0005\u0012bAA\u0012u\n\u0019\u0011I\\=\u0011\t\u0005%\u0011qE\u0005\u0004\u0003SA(\u0001\u0002,jK^\u0004b!!\u0003\u0002(\u0005=\u0011A\u0002\u0013j]&$H\u0005\u0006\u0002\u00022A!\u0011\u0011AA\u001a\u0013\r\t)D\u001f\u0002\u0005+:LG/\u0001\u0003wS\u0016<XCAA\u001e!\u0015\tI\u0001AA\b\u0003\ri\u0017\r]\u000b\u0005\u0003\u0003\n9\u0005\u0006\u0003\u0002D\u0005-\u0003#BA\u0005\u0001\u0005\u0015\u0003\u0003BA\t\u0003\u000f\"q!!\u0013\u0004\u0005\u0004\t9BA\u0001C\u0011\u001d\tie\u0001a\u0001\u0003\u001f\n\u0011A\u001a\t\t\u0003\u0003\t\t&a\u0004\u0002F%\u0019\u00111\u000b>\u0003\u0013\u0019+hn\u0019;j_:\f\u0014\u0001C1qa\u0016tG-\u001a3\u0016\t\u0005e\u0013q\f\u000b\u0005\u00037\n\u0019\u0007E\u0003\u0002\n\u0001\ti\u0006\u0005\u0003\u0002\u0012\u0005}CaBA%\t\t\u0007\u0011\u0011M\t\u0005\u0003\u001f\ty\u0002C\u0004\u0002f\u0011\u0001\r!!\u0018\u0002\t\u0015dW-\\\u0001\naJ,\u0007/\u001a8eK\u0012,B!a\u001b\u0002rQ!\u0011QNA:!\u0015\tI\u0001AA8!\u0011\t\t\"!\u001d\u0005\u000f\u0005%SA1\u0001\u0002b!9\u0011QM\u0003A\u0002\u0005=\u0014a\u0002:fm\u0016\u00148/Z\u0001\u0005i\u0006\\W\r\u0006\u0003\u0002<\u0005m\u0004bBA?\u000f\u0001\u0007\u0011qP\u0001\u0002]B!\u0011\u0011AAA\u0013\r\t\u0019I\u001f\u0002\u0004\u0013:$\u0018\u0001\u00023s_B$B!a\u000f\u0002\n\"9\u0011Q\u0010\u0005A\u0002\u0005}\u0014!\u0003;bW\u0016\u0014\u0016n\u001a5u)\u0011\tY$a$\t\u000f\u0005u\u0014\u00021\u0001\u0002\u0000\u0005IAM]8q%&<\u0007\u000e\u001e\u000b\u0005\u0003w\t)\nC\u0004\u0002~)\u0001\r!a \u0002\u000fQ\f\u0007/R1dQV!\u00111TAR)\u0011\tY$!(\t\u000f\u000553\u00021\u0001\u0002 BA\u0011\u0011AA)\u0003\u001f\t\t\u000b\u0005\u0003\u0002\u0012\u0005\rFaBAS\u0017\t\u0007\u0011q\u0003\u0002\u0002+\u000611m\u001c8dCR,B!a+\u00022R!\u0011QVAZ!\u0015\tI\u0001AAX!\u0011\t\t\"!-\u0005\u000f\u0005%CB1\u0001\u0002b!9\u0011Q\u0017\u0007A\u0002\u0005]\u0016AB:vM\u001aL\u0007\u0010E\u0003\u0002:R\tyKD\u0002\u0002\nE\tqaU3r-&,w\u000fE\u0002\u0002\nI\u0019BAE@\u0002BB!\u00111YAg\u001b\t\t)M\u0003\u0003\u0002H\u0006%\u0017AA5p\u0015\t\tY-\u0001\u0003kCZ\f\u0017\u0002BAh\u0003\u000b\u0014AbU3sS\u0006d\u0017N_1cY\u0016\fa\u0001P5oSRtDCAA_\u0005)\u0019v.\\3TKF|\u0005o]\u000b\u0005\u00033\fy\u000e\r\u0003\u0002\\\u0006E\bCCA\u0005\u0003\u0017\ti.!9\u0002pB!\u0011\u0011CAp\t!\t)\u0002\u0006CC\u0002\u0005]\u0001\u0003BAr\u0003StA!!\u0003\u0002f&\u0019\u0011q\u001d=\u0002\u000fA\f7m[1hK&!\u00111^Aw\u0005%\te._\"p]N$(OC\u0002\u0002hb\u0004B!!\u0005\u0002r\u0012Y\u00111\u001f\u000b\u0002\u0002\u0003\u0005)\u0011AA\f\u0005\ryF%\r\u0002\u0003\u0013\u0012,B!!?\u0003\u0004M\u0019Q#a?\u0011\r\u0005%\u0011Q B\u0001\u0013\r\ty\u0010\u001f\u0002\u0010\u0003\n\u001cHO]1diN+\u0017OV5foB!\u0011\u0011\u0003B\u0002\t!\t)\"\u0006CC\u0002\u0005]\u0011AC;oI\u0016\u0014H._5oOB)!\u0011\u0002\u000b\u0003\u00025\t!\u0003\u0006\u0003\u0003\u000e\t=\u0001#\u0002B\u0005+\t\u0005\u0001b\u0002B\u0003/\u0001\u0007!qA\u0001\u0006CB\u0004H.\u001f\u000b\u0005\u0005\u0003\u0011)\u0002C\u0004\u0003\u0018a\u0001\r!a \u0002\u0007%$\u00070\u0001\u0004mK:<G\u000f[\u000b\u0003\u0003\u007f\n\u0001\"\u001b;fe\u0006$xN]\u000b\u0003\u0005C\u0001b!!\u0003\u0003$\t\u0005\u0011b\u0001B\u0013q\nA\u0011\n^3sCR|'/A\u0005l]><hnU5{K\u00069\u0011n]#naRLXC\u0001B\u0017!\u0011\t\tAa\f\n\u0007\tE\"PA\u0004C_>dW-\u00198)\u000fU\u0011)Da\u000f\u0003>A!\u0011\u0011\u0001B\u001c\u0013\r\u0011ID\u001f\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012a\u0001\u0002\u0004\u001b\u0006\u0004XC\u0002B\"\u0005'\u00129fE\u0003\u001e\u0005\u000b\u0012I\u0006\u0005\u0005\u0003H\t5#\u0011\u000bB+\u001d\u0011\tIA!\u0013\n\u0007\t-\u00030\u0001\u0003WS\u0016<\u0018\u0002\u0002B \u0005\u001fR1Aa\u0013y!\u0011\t\tBa\u0015\u0005\u0011\u0005UQ\u0004\"b\u0001\u0003/\u0001B!!\u0005\u0003X\u0011A\u0011\u0011J\u000f\u0005\u0006\u0004\t9\u0002E\u0003\u0002\n\u0001\u0011)\u0006E\u0003\u0003\nQ\u0011\t\u0006\u0005\u0005\u0002\u0002\u0005E#\u0011\u000bB+)\u0019\u0011\tGa\u0019\u0003fA9!\u0011B\u000f\u0003R\tU\u0003b\u0002B\u0003A\u0001\u0007!1\f\u0005\b\u0003\u001b\u0002\u0003\u0019\u0001B/)\u0011\u0011)F!\u001b\t\u000f\t]\u0011\u00051\u0001\u0002\u0000!:QD!\u000e\u0003<\tu\"\u0001C!qa\u0016tG-\u001a3\u0016\t\tE$\u0011P\n\u0006G\tM$1\u0010\t\u0007\u0005\u000f\u0012)Ha\u001e\n\t\t5$q\n\t\u0005\u0003#\u0011I\b\u0002\u0005\u0002\u0016\r\")\u0019AA\f!\u0015\tI\u0001\u0001B<!\u0015\u0011I\u0001\u0006B<)\u0019\u0011\tIa!\u0003\u0006B)!\u0011B\u0012\u0003x!9!Q\u0001\u0014A\u0002\tu\u0004bBA3M\u0001\u0007!q\u000f\u000b\u0005\u0005o\u0012I\tC\u0004\u0003\u0018\u001d\u0002\r!a )\u000f\r\u0012)Da\u000f\u0003>\tI\u0001K]3qK:$W\rZ\u000b\u0005\u0005#\u0013IjE\u0003*\u0005'\u0013Y\n\u0005\u0004\u0003H\tU%qS\u0005\u0005\u0005\u001b\u0013y\u0005\u0005\u0003\u0002\u0012\teE\u0001CA\u000bS\u0011\u0015\r!a\u0006\u0011\u000b\u0005%\u0001Aa&\u0011\u000b\t%ACa&\u0015\r\t\u0005&1\u0015BS!\u0015\u0011I!\u000bBL\u0011\u001d\t)\u0007\fa\u0001\u0005/CqA!\u0002-\u0001\u0004\u0011i\n\u0006\u0003\u0003\u0018\n%\u0006b\u0002B\f[\u0001\u0007\u0011q\u0010\u0015\bS\tU\"1\bB\u001f\u0005\u0019\u0019uN\\2biV!!\u0011\u0017B]'\u0015y#1\u0017B^!\u0019\u00119E!.\u00038&!!Q\u0016B(!\u0011\t\tB!/\u0005\u000f\u0005UqF1\u0001\u0002\u0018A)\u0011\u0011\u0002\u0001\u00038\u00061\u0001O]3gSb\u0004RA!\u0003\u0015\u0005o#bAa1\u0003F\n\u001d\u0007#\u0002B\u0005_\t]\u0006b\u0002B_e\u0001\u0007!q\u0018\u0005\b\u0003k\u0013\u0004\u0019\u0001B`)\u0011\u00119La3\t\u000f\t]1\u00071\u0001\u0002\u0000!:qF!\u000e\u0003<\tu\"a\u0002*fm\u0016\u00148/Z\u000b\u0005\u0005'\u0014InE\u00026\u0005+\u0004b!!\u0003\u0002~\n]\u0007\u0003BA\t\u00053$q!!\u00066\u0005\u0004\t9\u0002E\u0003\u0003\nQ\u00119\u000e\u0006\u0003\u0003`\n\u0005\b#\u0002B\u0005k\t]\u0007b\u0002B\u0003o\u0001\u0007!1\u001c\u000b\u0005\u0005/\u0014)\u000fC\u0004\u0003hb\u0002\r!a \u0002\u0003%,\"Aa;\u0011\r\u0005%!1\u0005BlQ\u001d)$Q\u0007B\u001e\u0005{\u0011A\u0001V1lKV!!1\u001fB~'\u0015i$Q\u001fB\u007f!\u0019\u00119Ea>\u0003z&!!q\u001eB(!\u0011\t\tBa?\u0005\u0011\u0005UQ\b\"b\u0001\u0003/\u0001R!!\u0003\u0001\u0005s\u0004RA!\u0003\u0015\u0005s$baa\u0001\u0004\u0006\r\u001d\u0001#\u0002B\u0005{\te\bb\u0002B\u0003\u0001\u0002\u0007!q \u0005\b\u0003{\u0002\u0005\u0019AA@)\u0011\u0011Ipa\u0003\t\u000f\t]\u0011\t1\u0001\u0002\u0000!:QH!\u000e\u0003<\tu\"!\u0003+bW\u0016\u0014\u0016n\u001a5u+\u0011\u0019\u0019ba\u0007\u0014\u000b\r\u001b)b!\b\u0011\r\t\u001d3qCB\r\u0013\u0011\u0019yAa\u0014\u0011\t\u0005E11\u0004\u0003\t\u0003+\u0019EQ1\u0001\u0002\u0018A)\u0011\u0011\u0002\u0001\u0004\u001aA)!\u0011\u0002\u000b\u0004\u001aQ111EB\u0013\u0007O\u0001RA!\u0003D\u00073AqA!\u0002G\u0001\u0004\u0019y\u0002C\u0004\u0002~\u0019\u0003\r!a \u0002\u000b\u0011,G\u000e^1\u0015\t\re1Q\u0006\u0005\b\u0005OL\u0005\u0019AA@Q\u0015I5\u0011GB\"!\u0019\t\taa\r\u00048%\u00191Q\u0007>\u0003\rQD'o\\<t!\u0011\u0019Id!\u0010\u000f\t\u0005\u000511H\u0005\u0004\u0003OT\u0018\u0002BB \u0007\u0003\u0012\u0011$\u00138eKb|U\u000f^(g\u0005>,h\u000eZ:Fq\u000e,\u0007\u000f^5p]*\u0019\u0011q\u001d>2\u000fy\u0019)ea\u0017\u0004\u0000A!1qIB+\u001d\u0011\u0019Ie!\u0015\u0011\u0007\r-#0\u0004\u0002\u0004N)\u00191q\n?\u0002\rq\u0012xn\u001c;?\u0013\r\u0019\u0019F_\u0001\u0007!J,G-\u001a4\n\t\r]3\u0011\f\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\rM#0M\u0005$\u0007;\u001a)g!\u001e\u0004hU!1qLB1+\t\u0019)\u0005B\u0004\u0004dq\u0014\ra!\u001c\u0003\u0003QKAaa\u001a\u0004j\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIER1aa\u001b{\u0003\u0019!\bN]8xgF!\u0011\u0011DB8!\u0011\u0019Id!\u001d\n\t\rM4\u0011\t\u0002\n)\"\u0014xn^1cY\u0016\f\u0014bIB<\u0007s\u001aYha\u001b\u000f\t\u0005\u00051\u0011P\u0005\u0004\u0007WR\u0018G\u0002\u0012\u0002\u0002i\u001ciHA\u0003tG\u0006d\u0017-M\u0002'\u0007oAsa\u0011B\u001b\u0005w\u0011iD\u0001\u0003Ee>\u0004X\u0003BBD\u0007\u001f\u001bRASBE\u0007#\u0003bAa\u0012\u0004\f\u000e5\u0015\u0002BBB\u0005\u001f\u0002B!!\u0005\u0004\u0010\u00129\u0011Q\u0003&C\u0002\u0005]\u0001#BA\u0005\u0001\r5\u0005#\u0002B\u0005)\r5ECBBL\u00073\u001bY\nE\u0003\u0003\n)\u001bi\tC\u0004\u0003\u00065\u0003\raa%\t\u000f\u0005uT\n1\u0001\u0002\u0000Q!1QRBP\u0011\u001d\u00119o\u0014a\u0001\u0003\u007fBSaTB\u0019\u0007G\u000btAHB#\u0007K\u001bY+M\u0005$\u0007;\u001a)ga*\u0004hEJ1ea\u001e\u0004z\r%61N\u0019\u0007E\u0005\u0005!p! 2\u0007\u0019\u001a9\u0004\u0006\u0003\u0004\u0012\u000e=\u0006bBA?!\u0002\u0007\u0011q\u0010\u0015\b\u0015\nU\"1\bB\u001f\u0005%!%o\u001c9SS\u001eDG/\u0006\u0003\u00048\u000e}6#B)\u0004:\u000e\u0005\u0007C\u0002B$\u0007w\u001bi,\u0003\u0003\u00044\n=\u0003\u0003BA\t\u0007\u007f#q!!\u0006R\u0005\u0004\t9\u0002E\u0003\u0002\n\u0001\u0019i\fE\u0003\u0003\nQ\u0019i\f\u0006\u0004\u0004H\u000e%71\u001a\t\u0006\u0005\u0013\t6Q\u0018\u0005\b\u0005\u000b!\u0006\u0019ABb\u0011\u001d\ti\b\u0016a\u0001\u0003\u007f\n1\u0001\\3o)\u0011\u0019il!5\t\u000f\t\u001dx\u000b1\u0001\u0002\u0000!*qk!\r\u0004VF:ad!\u0012\u0004X\u000eu\u0017'C\u0012\u0004^\r\u00154\u0011\\B4c%\u00193qOB=\u00077\u001cY'\r\u0004#\u0003\u0003Q8QP\u0019\u0004M\r]\u0002fB)\u00036\tm\"Q\b\u0002\u0007'>\u0014H/\u001a3\u0016\r\r\u001581^B}'\u0011Avpa:\u0011\u000b\u0005%\u0001a!;\u0011\t\u0005E11\u001e\u0003\b\u0003+A&\u0019AA\f!\u0015\u0011I\u0001FBu\u0003\ry'\u000f\u001a\t\u0007\u0007s\u0019\u0019pa>\n\t\rU8\u0011\t\u0002\t\u001fJ$WM]5oOB!\u0011\u0011CB}\t\u001d\tI\u0005\u0017b\u0001\u0007w\fBa!;\u0002 QA1q C\u0001\t\u0007!)\u0001E\u0004\u0003\na\u001bIoa>\t\u000f\t\u0015A\f1\u0001\u0004n\"91Q\u001a/A\u0002\u0005}\u0004bBBx9\u0002\u00071\u0011\u001f\u000b\u0007\u0007\u007f$I\u0001b\u0003\t\u000f\t\u0015Q\f1\u0001\u0004n\"91q^/A\u0002\rE(!\u0004*fm\u0016\u00148/Z*peR,Gm\u0005\u0003_\u007f\u000e\u001dHC\u0001C\n!\r!)BX\u0007\u00021\u0006IqL]3wKJ\u001cX\rZ\u000b\u0003\t7\u0001R!!/6\u0007S$Ba!;\u0005 !9!q]1A\u0002\u0005}TC\u0001C\u0012!\u0019\tIAa\t\u0004j\u0006\u0011Ao\\\u000b\u0005\tS!i\u0003\u0006\u0003\u0005,\u0011E\u0002\u0003BA\t\t[!q\u0001b\fg\u0005\u0004\t9B\u0001\u0002Dc!9A1\u00074A\u0002\u0011U\u0012a\u00024bGR|'/\u001f\t\t\u0003\u0013!9d!;\u0005,%\u0019A\u0011\b=\u0003\u000f\u0019\u000b7\r^8ssV\u00111q]\u0001\te\u00164XM]:fIV\u0011A\u0011\t\t\u0007\u0003\u0013!\u0019e!;\n\u0007\u0011\u0015\u0003P\u0001\u0005Ji\u0016\u0014\u0018M\u00197f\u0003\u0019\u0019xN\u001d;fIV!A1\nC+)\u0011\u00199\u000f\"\u0014\t\u000f\u0011=\u0013\u000eq\u0001\u0005R\u0005!qN\u001d32!\u0019\u0019Ida=\u0005TA!\u0011\u0011\u0003C+\t\u001d!9&\u001bb\u0001\u0007w\u0014!AQ\u0019)\u000fy\u0013)Da\u000f\u0003>\u0005IQM^1mk\u0006$X\r\u001a\u0015\u0004U\u0012}\u0003\u0003BA\u0001\tCJ1\u0001b\u0019{\u0005!1x\u000e\\1uS2,\u0017aB0t_J$X\rZ\u000b\u0003\tS\u0002b!!\u0003\u0005l\r%\u0018b\u0001C7q\n\u00191+Z9\u0002\u000b\u0015dW-\\:\u0016\u0005\r5H\u0003BBu\tkBqAa:n\u0001\u0004\ty(\u0006\u0003\u0005z\u0011uD\u0003\u0002C>\t\u007f\u0002B!!\u0005\u0005~\u00119Aq\u0006:C\u0002\u0005]\u0001b\u0002C\u001ae\u0002\u0007A\u0011\u0011\t\t\u0003\u0013!9d!;\u0005|U!AQ\u0011CG)\u0011\u00199\u000fb\"\t\u000f\u0011=S\u000fq\u0001\u0005\nB11\u0011HBz\t\u0017\u0003B!!\u0005\u0005\u000e\u00129AqK;C\u0002\rm\bf\u0002-\u00036\tm\"QH\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\t+\u0003B\u0001b&\u0005\u001e6\u0011A\u0011\u0014\u0006\u0005\t7\u000bI-\u0001\u0003mC:<\u0017\u0002\u0002CP\t3\u0013aa\u00142kK\u000e$\u0018aC1qa\u0016tG-\u001a3BY2,B\u0001\"*\u0005,R!Aq\u0015CW!\u0015\tI\u0001\u0001CU!\u0011\t\t\u0002b+\u0005\u000f\u0005%SB1\u0001\u0002b!9\u0011QW\u0007A\u0002\u0011=\u0006#BA])\u0011%\u0016\u0001\u00049sKB,g\u000eZ3e\u00032dW\u0003\u0002C[\tw#B\u0001b.\u0005>B)\u0011\u0011\u0002\u0001\u0005:B!\u0011\u0011\u0003C^\t\u001d\tIE\u0004b\u0001\u0003CBqA!0\u000f\u0001\u0004!y\fE\u0003\u0002:R!I,\u0006\u0003\u0005D\u0012-G\u0003BA\u001e\t\u000bDqaa<\u0010\u0001\b!9\r\u0005\u0004\u0004:\rMH\u0011\u001a\t\u0005\u0003#!Y\rB\u0004\u0002J=\u0011\r!!\u0019\u0002\u0019M$(/\u001b8h!J,g-\u001b=")
public interface SeqView<A>
extends SeqOps<A, View, View<A>>,
View<A> {
    @Override
    default public SeqView<A> view() {
        return this;
    }

    @Override
    default public <B$> SeqView<B$> map(Function1<A, B$> f) {
        return new Map<A, B$>(this, f);
    }

    @Override
    default public <B> SeqView<B> appended(B elem) {
        return new Appended<B>(this, elem);
    }

    @Override
    default public <B> SeqView<B> prepended(B elem) {
        return new Prepended<B>(elem, this);
    }

    @Override
    default public SeqView<A> reverse() {
        return new Reverse(this);
    }

    @Override
    default public SeqView<A> take(int n) {
        return new Take(this, n);
    }

    @Override
    default public SeqView<A> drop(int n) {
        return new Drop(this, n);
    }

    @Override
    default public SeqView<A> takeRight(int n) {
        return new TakeRight(this, n);
    }

    @Override
    default public SeqView<A> dropRight(int n) {
        return new DropRight(this, n);
    }

    @Override
    default public <U> SeqView<A> tapEach(Function1<A, U> f) {
        return new Map(this, (Function1<Object, Object> & Serializable)a -> {
            f.apply(a);
            return a;
        });
    }

    default public <B> SeqView<B> concat(SeqOps<B, ?, Object> suffix) {
        return new Concat<B>(this, suffix);
    }

    default public <B> SeqView<B> appendedAll(SeqOps<B, ?, Object> suffix) {
        return new Concat<B>(this, suffix);
    }

    default public <B> SeqView<B> prependedAll(SeqOps<B, ?, Object> prefix) {
        return new Concat<B>(prefix, this);
    }

    @Override
    default public <B> SeqView<A> sorted(Ordering<B> ord) {
        return new Sorted(this, ord);
    }

    @Override
    default public String stringPrefix() {
        return "SeqView";
    }

    public static void $init$(SeqView $this) {
    }

    public static class Appended<A>
    extends View.Appended<A>
    implements SeqView<A> {
        private static final long serialVersionUID = 3L;
        private final SeqOps<A, ?, Object> underlying;
        private final A elem;

        @Override
        public SeqView<A> view() {
            return ((SeqView)this).view();
        }

        @Override
        public <B$> SeqView<B$> map(Function1<A, B$> f) {
            return ((SeqView)this).map(f);
        }

        @Override
        public <B> SeqView<B> appended(B elem) {
            return ((SeqView)this).appended(elem);
        }

        @Override
        public <B> SeqView<B> prepended(B elem) {
            return ((SeqView)this).prepended(elem);
        }

        @Override
        public SeqView<A> reverse() {
            return ((SeqView)this).reverse();
        }

        @Override
        public SeqView<A> take(int n) {
            return ((SeqView)this).take(n);
        }

        @Override
        public SeqView<A> drop(int n) {
            return ((SeqView)this).drop(n);
        }

        @Override
        public SeqView<A> takeRight(int n) {
            return ((SeqView)this).takeRight(n);
        }

        @Override
        public SeqView<A> dropRight(int n) {
            return ((SeqView)this).dropRight(n);
        }

        @Override
        public <U> SeqView<A> tapEach(Function1<A, U> f) {
            return ((SeqView)this).tapEach(f);
        }

        @Override
        public <B> SeqView<B> concat(SeqOps<B, ?, Object> suffix) {
            return ((SeqView)this).concat(suffix);
        }

        @Override
        public <B> SeqView<B> appendedAll(SeqOps<B, ?, Object> suffix) {
            return ((SeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> SeqView<B> prependedAll(SeqOps<B, ?, Object> prefix) {
            return ((SeqView)this).prependedAll(prefix);
        }

        @Override
        public <B> SeqView<A> sorted(Ordering<B> ord) {
            return ((SeqView)this).sorted(ord);
        }

        @Override
        public String stringPrefix() {
            return ((SeqView)this).stringPrefix();
        }

        @Override
        public /* synthetic */ Object scala$collection$SeqOps$$super$concat(IterableOnce suffix) {
            return IterableOps.concat$(this, suffix);
        }

        @Override
        public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(int otherSize) {
            return IterableOps.sizeCompare$((IterableOps)this, otherSize);
        }

        @Override
        public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(Iterable that) {
            return IterableOps.sizeCompare$((IterableOps)this, that);
        }

        @Override
        public final Object $plus$colon(Object elem) {
            return SeqOps.$plus$colon$(this, elem);
        }

        @Override
        public final Object $colon$plus(Object elem) {
            return SeqOps.$colon$plus$(this, elem);
        }

        @Override
        public Object prependedAll(IterableOnce prefix) {
            return SeqOps.prependedAll$(this, prefix);
        }

        @Override
        public final Object $plus$plus$colon(IterableOnce prefix) {
            return SeqOps.$plus$plus$colon$(this, prefix);
        }

        @Override
        public Object appendedAll(IterableOnce suffix) {
            return SeqOps.appendedAll$(this, suffix);
        }

        @Override
        public final Object $colon$plus$plus(IterableOnce suffix) {
            return SeqOps.$colon$plus$plus$(this, suffix);
        }

        @Override
        public final Object concat(IterableOnce suffix) {
            return SeqOps.concat$(this, suffix);
        }

        @Override
        public final Object union(Seq that) {
            return SeqOps.union$(this, that);
        }

        @Override
        public final int size() {
            return SeqOps.size$(this);
        }

        @Override
        public Object distinct() {
            return SeqOps.distinct$(this);
        }

        @Override
        public Object distinctBy(Function1 f) {
            return SeqOps.distinctBy$(this, f);
        }

        @Override
        public Iterator<A> reverseIterator() {
            return SeqOps.reverseIterator$(this);
        }

        @Override
        public <B> boolean startsWith(IterableOnce<B> that, int offset) {
            return SeqOps.startsWith$(this, that, offset);
        }

        @Override
        public <B> int startsWith$default$2() {
            return SeqOps.startsWith$default$2$(this);
        }

        @Override
        public <B> boolean endsWith(Iterable<B> that) {
            return SeqOps.endsWith$(this, that);
        }

        @Override
        public boolean isDefinedAt(int idx) {
            return SeqOps.isDefinedAt$(this, idx);
        }

        @Override
        public Object padTo(int len, Object elem) {
            return SeqOps.padTo$(this, len, elem);
        }

        @Override
        public final int segmentLength(Function1<A, Object> p) {
            return SeqOps.segmentLength$(this, p);
        }

        @Override
        public int segmentLength(Function1<A, Object> p, int from) {
            return SeqOps.segmentLength$(this, p, from);
        }

        @Override
        public final int prefixLength(Function1<A, Object> p) {
            return SeqOps.prefixLength$(this, p);
        }

        @Override
        public int indexWhere(Function1<A, Object> p, int from) {
            return SeqOps.indexWhere$(this, p, from);
        }

        @Override
        public int indexWhere(Function1<A, Object> p) {
            return SeqOps.indexWhere$(this, p);
        }

        @Override
        public <B> int indexOf(B elem, int from) {
            return SeqOps.indexOf$(this, elem, from);
        }

        @Override
        public <B> int indexOf(B elem) {
            return SeqOps.indexOf$(this, elem);
        }

        @Override
        public <B> int lastIndexOf(B elem, int end) {
            return SeqOps.lastIndexOf$(this, elem, end);
        }

        @Override
        public <B> int lastIndexOf$default$2() {
            return SeqOps.lastIndexOf$default$2$(this);
        }

        @Override
        public int lastIndexWhere(Function1<A, Object> p, int end) {
            return SeqOps.lastIndexWhere$(this, p, end);
        }

        @Override
        public int lastIndexWhere(Function1<A, Object> p) {
            return SeqOps.lastIndexWhere$(this, p);
        }

        @Override
        public <B> int indexOfSlice(Seq<B> that, int from) {
            return SeqOps.indexOfSlice$(this, that, from);
        }

        @Override
        public <B> int indexOfSlice(Seq<B> that) {
            return SeqOps.indexOfSlice$(this, that);
        }

        @Override
        public <B> int lastIndexOfSlice(Seq<B> that, int end) {
            return SeqOps.lastIndexOfSlice$(this, that, end);
        }

        @Override
        public <B> int lastIndexOfSlice(Seq<B> that) {
            return SeqOps.lastIndexOfSlice$(this, that);
        }

        @Override
        public Option<A> findLast(Function1<A, Object> p) {
            return SeqOps.findLast$(this, p);
        }

        @Override
        public <B> boolean containsSlice(Seq<B> that) {
            return SeqOps.containsSlice$(this, that);
        }

        @Override
        public <A1> boolean contains(A1 elem) {
            return SeqOps.contains$(this, elem);
        }

        @Override
        public Object reverseMap(Function1 f) {
            return SeqOps.reverseMap$(this, f);
        }

        @Override
        public Iterator<View<A>> permutations() {
            return SeqOps.permutations$(this);
        }

        @Override
        public Iterator<View<A>> combinations(int n) {
            return SeqOps.combinations$(this, n);
        }

        @Override
        public Object sortWith(Function2 lt) {
            return SeqOps.sortWith$(this, lt);
        }

        @Override
        public Object sortBy(Function1 f, Ordering ord) {
            return SeqOps.sortBy$(this, f, ord);
        }

        @Override
        public Range indices() {
            return SeqOps.indices$(this);
        }

        @Override
        public final int sizeCompare(int otherSize) {
            return SeqOps.sizeCompare$((SeqOps)this, otherSize);
        }

        @Override
        public int lengthCompare(int len) {
            return SeqOps.lengthCompare$((SeqOps)this, len);
        }

        @Override
        public final int sizeCompare(Iterable<?> that) {
            return SeqOps.sizeCompare$((SeqOps)this, that);
        }

        @Override
        public int lengthCompare(Iterable<?> that) {
            return SeqOps.lengthCompare$((SeqOps)this, that);
        }

        @Override
        public final IterableOps lengthIs() {
            return SeqOps.lengthIs$(this);
        }

        @Override
        public boolean isEmpty() {
            return SeqOps.isEmpty$(this);
        }

        @Override
        public <B> boolean sameElements(IterableOnce<B> that) {
            return SeqOps.sameElements$(this, that);
        }

        @Override
        public <B$> boolean corresponds(Seq<B$> that, Function2<A, B$, Object> p) {
            return SeqOps.corresponds$(this, that, p);
        }

        @Override
        public Object diff(Seq that) {
            return SeqOps.diff$(this, that);
        }

        @Override
        public Object intersect(Seq that) {
            return SeqOps.intersect$(this, that);
        }

        @Override
        public Object patch(int from, IterableOnce other, int replaced) {
            return SeqOps.patch$(this, from, other, replaced);
        }

        @Override
        public Object updated(int index, Object elem) {
            return SeqOps.updated$(this, index, elem);
        }

        @Override
        public <B> scala.collection.mutable.Map<B, Object> occCounts(Seq<B> sq) {
            return SeqOps.occCounts$(this, sq);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
            return SeqOps.search$(this, elem, ord);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
            return SeqOps.search$(this, elem, from, to, ord);
        }

        @Override
        public A apply(int idx) {
            if (idx == this.underlying.length()) {
                return this.elem;
            }
            return this.underlying.apply(idx);
        }

        @Override
        public int length() {
            return this.underlying.length() + 1;
        }

        public Appended(SeqOps<A, ?, Object> underlying, A elem) {
            this.underlying = underlying;
            this.elem = elem;
            super(underlying, elem);
        }
    }

    public static class Concat<A>
    extends View.Concat<A>
    implements SeqView<A> {
        private static final long serialVersionUID = 3L;
        private final SeqOps<A, ?, Object> prefix;
        private final SeqOps<A, ?, Object> suffix;

        @Override
        public SeqView<A> view() {
            return ((SeqView)this).view();
        }

        @Override
        public <B$> SeqView<B$> map(Function1<A, B$> f) {
            return ((SeqView)this).map(f);
        }

        @Override
        public <B> SeqView<B> appended(B elem) {
            return ((SeqView)this).appended(elem);
        }

        @Override
        public <B> SeqView<B> prepended(B elem) {
            return ((SeqView)this).prepended(elem);
        }

        @Override
        public SeqView<A> reverse() {
            return ((SeqView)this).reverse();
        }

        @Override
        public SeqView<A> take(int n) {
            return ((SeqView)this).take(n);
        }

        @Override
        public SeqView<A> drop(int n) {
            return ((SeqView)this).drop(n);
        }

        @Override
        public SeqView<A> takeRight(int n) {
            return ((SeqView)this).takeRight(n);
        }

        @Override
        public SeqView<A> dropRight(int n) {
            return ((SeqView)this).dropRight(n);
        }

        @Override
        public <U> SeqView<A> tapEach(Function1<A, U> f) {
            return ((SeqView)this).tapEach(f);
        }

        @Override
        public <B> SeqView<B> concat(SeqOps<B, ?, Object> suffix) {
            return ((SeqView)this).concat(suffix);
        }

        @Override
        public <B> SeqView<B> appendedAll(SeqOps<B, ?, Object> suffix) {
            return ((SeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> SeqView<B> prependedAll(SeqOps<B, ?, Object> prefix) {
            return ((SeqView)this).prependedAll(prefix);
        }

        @Override
        public <B> SeqView<A> sorted(Ordering<B> ord) {
            return ((SeqView)this).sorted(ord);
        }

        @Override
        public String stringPrefix() {
            return ((SeqView)this).stringPrefix();
        }

        @Override
        public /* synthetic */ Object scala$collection$SeqOps$$super$concat(IterableOnce suffix) {
            return IterableOps.concat$(this, suffix);
        }

        @Override
        public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(int otherSize) {
            return IterableOps.sizeCompare$((IterableOps)this, otherSize);
        }

        @Override
        public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(Iterable that) {
            return IterableOps.sizeCompare$((IterableOps)this, that);
        }

        @Override
        public final Object $plus$colon(Object elem) {
            return SeqOps.$plus$colon$(this, elem);
        }

        @Override
        public final Object $colon$plus(Object elem) {
            return SeqOps.$colon$plus$(this, elem);
        }

        @Override
        public Object prependedAll(IterableOnce prefix) {
            return SeqOps.prependedAll$(this, prefix);
        }

        @Override
        public final Object $plus$plus$colon(IterableOnce prefix) {
            return SeqOps.$plus$plus$colon$(this, prefix);
        }

        @Override
        public Object appendedAll(IterableOnce suffix) {
            return SeqOps.appendedAll$(this, suffix);
        }

        @Override
        public final Object $colon$plus$plus(IterableOnce suffix) {
            return SeqOps.$colon$plus$plus$(this, suffix);
        }

        @Override
        public final Object concat(IterableOnce suffix) {
            return SeqOps.concat$(this, suffix);
        }

        @Override
        public final Object union(Seq that) {
            return SeqOps.union$(this, that);
        }

        @Override
        public final int size() {
            return SeqOps.size$(this);
        }

        @Override
        public Object distinct() {
            return SeqOps.distinct$(this);
        }

        @Override
        public Object distinctBy(Function1 f) {
            return SeqOps.distinctBy$(this, f);
        }

        @Override
        public Iterator<A> reverseIterator() {
            return SeqOps.reverseIterator$(this);
        }

        @Override
        public <B> boolean startsWith(IterableOnce<B> that, int offset) {
            return SeqOps.startsWith$(this, that, offset);
        }

        @Override
        public <B> int startsWith$default$2() {
            return SeqOps.startsWith$default$2$(this);
        }

        @Override
        public <B> boolean endsWith(Iterable<B> that) {
            return SeqOps.endsWith$(this, that);
        }

        @Override
        public boolean isDefinedAt(int idx) {
            return SeqOps.isDefinedAt$(this, idx);
        }

        @Override
        public Object padTo(int len, Object elem) {
            return SeqOps.padTo$(this, len, elem);
        }

        @Override
        public final int segmentLength(Function1<A, Object> p) {
            return SeqOps.segmentLength$(this, p);
        }

        @Override
        public int segmentLength(Function1<A, Object> p, int from) {
            return SeqOps.segmentLength$(this, p, from);
        }

        @Override
        public final int prefixLength(Function1<A, Object> p) {
            return SeqOps.prefixLength$(this, p);
        }

        @Override
        public int indexWhere(Function1<A, Object> p, int from) {
            return SeqOps.indexWhere$(this, p, from);
        }

        @Override
        public int indexWhere(Function1<A, Object> p) {
            return SeqOps.indexWhere$(this, p);
        }

        @Override
        public <B> int indexOf(B elem, int from) {
            return SeqOps.indexOf$(this, elem, from);
        }

        @Override
        public <B> int indexOf(B elem) {
            return SeqOps.indexOf$(this, elem);
        }

        @Override
        public <B> int lastIndexOf(B elem, int end) {
            return SeqOps.lastIndexOf$(this, elem, end);
        }

        @Override
        public <B> int lastIndexOf$default$2() {
            return SeqOps.lastIndexOf$default$2$(this);
        }

        @Override
        public int lastIndexWhere(Function1<A, Object> p, int end) {
            return SeqOps.lastIndexWhere$(this, p, end);
        }

        @Override
        public int lastIndexWhere(Function1<A, Object> p) {
            return SeqOps.lastIndexWhere$(this, p);
        }

        @Override
        public <B> int indexOfSlice(Seq<B> that, int from) {
            return SeqOps.indexOfSlice$(this, that, from);
        }

        @Override
        public <B> int indexOfSlice(Seq<B> that) {
            return SeqOps.indexOfSlice$(this, that);
        }

        @Override
        public <B> int lastIndexOfSlice(Seq<B> that, int end) {
            return SeqOps.lastIndexOfSlice$(this, that, end);
        }

        @Override
        public <B> int lastIndexOfSlice(Seq<B> that) {
            return SeqOps.lastIndexOfSlice$(this, that);
        }

        @Override
        public Option<A> findLast(Function1<A, Object> p) {
            return SeqOps.findLast$(this, p);
        }

        @Override
        public <B> boolean containsSlice(Seq<B> that) {
            return SeqOps.containsSlice$(this, that);
        }

        @Override
        public <A1> boolean contains(A1 elem) {
            return SeqOps.contains$(this, elem);
        }

        @Override
        public Object reverseMap(Function1 f) {
            return SeqOps.reverseMap$(this, f);
        }

        @Override
        public Iterator<View<A>> permutations() {
            return SeqOps.permutations$(this);
        }

        @Override
        public Iterator<View<A>> combinations(int n) {
            return SeqOps.combinations$(this, n);
        }

        @Override
        public Object sortWith(Function2 lt) {
            return SeqOps.sortWith$(this, lt);
        }

        @Override
        public Object sortBy(Function1 f, Ordering ord) {
            return SeqOps.sortBy$(this, f, ord);
        }

        @Override
        public Range indices() {
            return SeqOps.indices$(this);
        }

        @Override
        public final int sizeCompare(int otherSize) {
            return SeqOps.sizeCompare$((SeqOps)this, otherSize);
        }

        @Override
        public int lengthCompare(int len) {
            return SeqOps.lengthCompare$((SeqOps)this, len);
        }

        @Override
        public final int sizeCompare(Iterable<?> that) {
            return SeqOps.sizeCompare$((SeqOps)this, that);
        }

        @Override
        public int lengthCompare(Iterable<?> that) {
            return SeqOps.lengthCompare$((SeqOps)this, that);
        }

        @Override
        public final IterableOps lengthIs() {
            return SeqOps.lengthIs$(this);
        }

        @Override
        public boolean isEmpty() {
            return SeqOps.isEmpty$(this);
        }

        @Override
        public <B> boolean sameElements(IterableOnce<B> that) {
            return SeqOps.sameElements$(this, that);
        }

        @Override
        public <B$> boolean corresponds(Seq<B$> that, Function2<A, B$, Object> p) {
            return SeqOps.corresponds$(this, that, p);
        }

        @Override
        public Object diff(Seq that) {
            return SeqOps.diff$(this, that);
        }

        @Override
        public Object intersect(Seq that) {
            return SeqOps.intersect$(this, that);
        }

        @Override
        public Object patch(int from, IterableOnce other, int replaced) {
            return SeqOps.patch$(this, from, other, replaced);
        }

        @Override
        public Object updated(int index, Object elem) {
            return SeqOps.updated$(this, index, elem);
        }

        @Override
        public <B> scala.collection.mutable.Map<B, Object> occCounts(Seq<B> sq) {
            return SeqOps.occCounts$(this, sq);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
            return SeqOps.search$(this, elem, ord);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
            return SeqOps.search$(this, elem, from, to, ord);
        }

        @Override
        public A apply(int idx) {
            int l = this.prefix.length();
            if (idx < l) {
                return this.prefix.apply(idx);
            }
            return this.suffix.apply(idx - l);
        }

        @Override
        public int length() {
            return this.prefix.length() + this.suffix.length();
        }

        public Concat(SeqOps<A, ?, Object> prefix, SeqOps<A, ?, Object> suffix) {
            this.prefix = prefix;
            this.suffix = suffix;
            super(prefix, suffix);
        }
    }

    public static class Drop<A>
    extends View.Drop<A>
    implements SeqView<A> {
        private static final long serialVersionUID = 3L;
        private final SeqOps<A, ?, Object> underlying;
        private final int n;

        @Override
        public SeqView<A> view() {
            return ((SeqView)this).view();
        }

        @Override
        public <B$> SeqView<B$> map(Function1<A, B$> f) {
            return ((SeqView)this).map(f);
        }

        @Override
        public <B> SeqView<B> appended(B elem) {
            return ((SeqView)this).appended(elem);
        }

        @Override
        public <B> SeqView<B> prepended(B elem) {
            return ((SeqView)this).prepended(elem);
        }

        @Override
        public SeqView<A> reverse() {
            return ((SeqView)this).reverse();
        }

        @Override
        public SeqView<A> take(int n) {
            return ((SeqView)this).take(n);
        }

        @Override
        public SeqView<A> takeRight(int n) {
            return ((SeqView)this).takeRight(n);
        }

        @Override
        public SeqView<A> dropRight(int n) {
            return ((SeqView)this).dropRight(n);
        }

        @Override
        public <U> SeqView<A> tapEach(Function1<A, U> f) {
            return ((SeqView)this).tapEach(f);
        }

        @Override
        public <B> SeqView<B> concat(SeqOps<B, ?, Object> suffix) {
            return ((SeqView)this).concat(suffix);
        }

        @Override
        public <B> SeqView<B> appendedAll(SeqOps<B, ?, Object> suffix) {
            return ((SeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> SeqView<B> prependedAll(SeqOps<B, ?, Object> prefix) {
            return ((SeqView)this).prependedAll(prefix);
        }

        @Override
        public <B> SeqView<A> sorted(Ordering<B> ord) {
            return ((SeqView)this).sorted(ord);
        }

        @Override
        public String stringPrefix() {
            return ((SeqView)this).stringPrefix();
        }

        @Override
        public /* synthetic */ Object scala$collection$SeqOps$$super$concat(IterableOnce suffix) {
            return IterableOps.concat$(this, suffix);
        }

        @Override
        public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(int otherSize) {
            return IterableOps.sizeCompare$((IterableOps)this, otherSize);
        }

        @Override
        public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(Iterable that) {
            return IterableOps.sizeCompare$((IterableOps)this, that);
        }

        @Override
        public final Object $plus$colon(Object elem) {
            return SeqOps.$plus$colon$(this, elem);
        }

        @Override
        public final Object $colon$plus(Object elem) {
            return SeqOps.$colon$plus$(this, elem);
        }

        @Override
        public Object prependedAll(IterableOnce prefix) {
            return SeqOps.prependedAll$(this, prefix);
        }

        @Override
        public final Object $plus$plus$colon(IterableOnce prefix) {
            return SeqOps.$plus$plus$colon$(this, prefix);
        }

        @Override
        public Object appendedAll(IterableOnce suffix) {
            return SeqOps.appendedAll$(this, suffix);
        }

        @Override
        public final Object $colon$plus$plus(IterableOnce suffix) {
            return SeqOps.$colon$plus$plus$(this, suffix);
        }

        @Override
        public final Object concat(IterableOnce suffix) {
            return SeqOps.concat$(this, suffix);
        }

        @Override
        public final Object union(Seq that) {
            return SeqOps.union$(this, that);
        }

        @Override
        public final int size() {
            return SeqOps.size$(this);
        }

        @Override
        public Object distinct() {
            return SeqOps.distinct$(this);
        }

        @Override
        public Object distinctBy(Function1 f) {
            return SeqOps.distinctBy$(this, f);
        }

        @Override
        public Iterator<A> reverseIterator() {
            return SeqOps.reverseIterator$(this);
        }

        @Override
        public <B> boolean startsWith(IterableOnce<B> that, int offset) {
            return SeqOps.startsWith$(this, that, offset);
        }

        @Override
        public <B> int startsWith$default$2() {
            return SeqOps.startsWith$default$2$(this);
        }

        @Override
        public <B> boolean endsWith(Iterable<B> that) {
            return SeqOps.endsWith$(this, that);
        }

        @Override
        public boolean isDefinedAt(int idx) {
            return SeqOps.isDefinedAt$(this, idx);
        }

        @Override
        public Object padTo(int len, Object elem) {
            return SeqOps.padTo$(this, len, elem);
        }

        @Override
        public final int segmentLength(Function1<A, Object> p) {
            return SeqOps.segmentLength$(this, p);
        }

        @Override
        public int segmentLength(Function1<A, Object> p, int from) {
            return SeqOps.segmentLength$(this, p, from);
        }

        @Override
        public final int prefixLength(Function1<A, Object> p) {
            return SeqOps.prefixLength$(this, p);
        }

        @Override
        public int indexWhere(Function1<A, Object> p, int from) {
            return SeqOps.indexWhere$(this, p, from);
        }

        @Override
        public int indexWhere(Function1<A, Object> p) {
            return SeqOps.indexWhere$(this, p);
        }

        @Override
        public <B> int indexOf(B elem, int from) {
            return SeqOps.indexOf$(this, elem, from);
        }

        @Override
        public <B> int indexOf(B elem) {
            return SeqOps.indexOf$(this, elem);
        }

        @Override
        public <B> int lastIndexOf(B elem, int end) {
            return SeqOps.lastIndexOf$(this, elem, end);
        }

        @Override
        public <B> int lastIndexOf$default$2() {
            return SeqOps.lastIndexOf$default$2$(this);
        }

        @Override
        public int lastIndexWhere(Function1<A, Object> p, int end) {
            return SeqOps.lastIndexWhere$(this, p, end);
        }

        @Override
        public int lastIndexWhere(Function1<A, Object> p) {
            return SeqOps.lastIndexWhere$(this, p);
        }

        @Override
        public <B> int indexOfSlice(Seq<B> that, int from) {
            return SeqOps.indexOfSlice$(this, that, from);
        }

        @Override
        public <B> int indexOfSlice(Seq<B> that) {
            return SeqOps.indexOfSlice$(this, that);
        }

        @Override
        public <B> int lastIndexOfSlice(Seq<B> that, int end) {
            return SeqOps.lastIndexOfSlice$(this, that, end);
        }

        @Override
        public <B> int lastIndexOfSlice(Seq<B> that) {
            return SeqOps.lastIndexOfSlice$(this, that);
        }

        @Override
        public Option<A> findLast(Function1<A, Object> p) {
            return SeqOps.findLast$(this, p);
        }

        @Override
        public <B> boolean containsSlice(Seq<B> that) {
            return SeqOps.containsSlice$(this, that);
        }

        @Override
        public <A1> boolean contains(A1 elem) {
            return SeqOps.contains$(this, elem);
        }

        @Override
        public Object reverseMap(Function1 f) {
            return SeqOps.reverseMap$(this, f);
        }

        @Override
        public Iterator<View<A>> permutations() {
            return SeqOps.permutations$(this);
        }

        @Override
        public Iterator<View<A>> combinations(int n) {
            return SeqOps.combinations$(this, n);
        }

        @Override
        public Object sortWith(Function2 lt) {
            return SeqOps.sortWith$(this, lt);
        }

        @Override
        public Object sortBy(Function1 f, Ordering ord) {
            return SeqOps.sortBy$(this, f, ord);
        }

        @Override
        public Range indices() {
            return SeqOps.indices$(this);
        }

        @Override
        public final int sizeCompare(int otherSize) {
            return SeqOps.sizeCompare$((SeqOps)this, otherSize);
        }

        @Override
        public int lengthCompare(int len) {
            return SeqOps.lengthCompare$((SeqOps)this, len);
        }

        @Override
        public final int sizeCompare(Iterable<?> that) {
            return SeqOps.sizeCompare$((SeqOps)this, that);
        }

        @Override
        public int lengthCompare(Iterable<?> that) {
            return SeqOps.lengthCompare$((SeqOps)this, that);
        }

        @Override
        public final IterableOps lengthIs() {
            return SeqOps.lengthIs$(this);
        }

        @Override
        public boolean isEmpty() {
            return SeqOps.isEmpty$(this);
        }

        @Override
        public <B> boolean sameElements(IterableOnce<B> that) {
            return SeqOps.sameElements$(this, that);
        }

        @Override
        public <B$> boolean corresponds(Seq<B$> that, Function2<A, B$, Object> p) {
            return SeqOps.corresponds$(this, that, p);
        }

        @Override
        public Object diff(Seq that) {
            return SeqOps.diff$(this, that);
        }

        @Override
        public Object intersect(Seq that) {
            return SeqOps.intersect$(this, that);
        }

        @Override
        public Object patch(int from, IterableOnce other, int replaced) {
            return SeqOps.patch$(this, from, other, replaced);
        }

        @Override
        public Object updated(int index, Object elem) {
            return SeqOps.updated$(this, index, elem);
        }

        @Override
        public <B> scala.collection.mutable.Map<B, Object> occCounts(Seq<B> sq) {
            return SeqOps.occCounts$(this, sq);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
            return SeqOps.search$(this, elem, ord);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
            return SeqOps.search$(this, elem, from, to, ord);
        }

        @Override
        public int length() {
            SeqOps<A, ?, Object> seqOps = this.underlying;
            if (seqOps == null) {
                throw null;
            }
            int n = seqOps.length() - this.normN();
            int max$extension_that = 0;
            return Math.max(n, max$extension_that);
        }

        @Override
        public A apply(int i) throws IndexOutOfBoundsException {
            return this.underlying.apply(i + this.normN());
        }

        @Override
        public SeqView<A> drop(int n) {
            return new Drop<A>(this.underlying, this.n + n);
        }

        public Drop(SeqOps<A, ?, Object> underlying, int n) {
            this.underlying = underlying;
            this.n = n;
            super(underlying, n);
        }
    }

    public static class DropRight<A>
    extends View.DropRight<A>
    implements SeqView<A> {
        private static final long serialVersionUID = 3L;
        private final SeqOps<A, ?, Object> underlying;
        private final int len;

        @Override
        public SeqView<A> view() {
            return ((SeqView)this).view();
        }

        @Override
        public <B$> SeqView<B$> map(Function1<A, B$> f) {
            return ((SeqView)this).map(f);
        }

        @Override
        public <B> SeqView<B> appended(B elem) {
            return ((SeqView)this).appended(elem);
        }

        @Override
        public <B> SeqView<B> prepended(B elem) {
            return ((SeqView)this).prepended(elem);
        }

        @Override
        public SeqView<A> reverse() {
            return ((SeqView)this).reverse();
        }

        @Override
        public SeqView<A> take(int n) {
            return ((SeqView)this).take(n);
        }

        @Override
        public SeqView<A> drop(int n) {
            return ((SeqView)this).drop(n);
        }

        @Override
        public SeqView<A> takeRight(int n) {
            return ((SeqView)this).takeRight(n);
        }

        @Override
        public SeqView<A> dropRight(int n) {
            return ((SeqView)this).dropRight(n);
        }

        @Override
        public <U> SeqView<A> tapEach(Function1<A, U> f) {
            return ((SeqView)this).tapEach(f);
        }

        @Override
        public <B> SeqView<B> concat(SeqOps<B, ?, Object> suffix) {
            return ((SeqView)this).concat(suffix);
        }

        @Override
        public <B> SeqView<B> appendedAll(SeqOps<B, ?, Object> suffix) {
            return ((SeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> SeqView<B> prependedAll(SeqOps<B, ?, Object> prefix) {
            return ((SeqView)this).prependedAll(prefix);
        }

        @Override
        public <B> SeqView<A> sorted(Ordering<B> ord) {
            return ((SeqView)this).sorted(ord);
        }

        @Override
        public String stringPrefix() {
            return ((SeqView)this).stringPrefix();
        }

        @Override
        public /* synthetic */ Object scala$collection$SeqOps$$super$concat(IterableOnce suffix) {
            return IterableOps.concat$(this, suffix);
        }

        @Override
        public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(int otherSize) {
            return IterableOps.sizeCompare$((IterableOps)this, otherSize);
        }

        @Override
        public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(Iterable that) {
            return IterableOps.sizeCompare$((IterableOps)this, that);
        }

        @Override
        public final Object $plus$colon(Object elem) {
            return SeqOps.$plus$colon$(this, elem);
        }

        @Override
        public final Object $colon$plus(Object elem) {
            return SeqOps.$colon$plus$(this, elem);
        }

        @Override
        public Object prependedAll(IterableOnce prefix) {
            return SeqOps.prependedAll$(this, prefix);
        }

        @Override
        public final Object $plus$plus$colon(IterableOnce prefix) {
            return SeqOps.$plus$plus$colon$(this, prefix);
        }

        @Override
        public Object appendedAll(IterableOnce suffix) {
            return SeqOps.appendedAll$(this, suffix);
        }

        @Override
        public final Object $colon$plus$plus(IterableOnce suffix) {
            return SeqOps.$colon$plus$plus$(this, suffix);
        }

        @Override
        public final Object concat(IterableOnce suffix) {
            return SeqOps.concat$(this, suffix);
        }

        @Override
        public final Object union(Seq that) {
            return SeqOps.union$(this, that);
        }

        @Override
        public final int size() {
            return SeqOps.size$(this);
        }

        @Override
        public Object distinct() {
            return SeqOps.distinct$(this);
        }

        @Override
        public Object distinctBy(Function1 f) {
            return SeqOps.distinctBy$(this, f);
        }

        @Override
        public Iterator<A> reverseIterator() {
            return SeqOps.reverseIterator$(this);
        }

        @Override
        public <B> boolean startsWith(IterableOnce<B> that, int offset) {
            return SeqOps.startsWith$(this, that, offset);
        }

        @Override
        public <B> int startsWith$default$2() {
            return SeqOps.startsWith$default$2$(this);
        }

        @Override
        public <B> boolean endsWith(Iterable<B> that) {
            return SeqOps.endsWith$(this, that);
        }

        @Override
        public boolean isDefinedAt(int idx) {
            return SeqOps.isDefinedAt$(this, idx);
        }

        @Override
        public Object padTo(int len, Object elem) {
            return SeqOps.padTo$(this, len, elem);
        }

        @Override
        public final int segmentLength(Function1<A, Object> p) {
            return SeqOps.segmentLength$(this, p);
        }

        @Override
        public int segmentLength(Function1<A, Object> p, int from) {
            return SeqOps.segmentLength$(this, p, from);
        }

        @Override
        public final int prefixLength(Function1<A, Object> p) {
            return SeqOps.prefixLength$(this, p);
        }

        @Override
        public int indexWhere(Function1<A, Object> p, int from) {
            return SeqOps.indexWhere$(this, p, from);
        }

        @Override
        public int indexWhere(Function1<A, Object> p) {
            return SeqOps.indexWhere$(this, p);
        }

        @Override
        public <B> int indexOf(B elem, int from) {
            return SeqOps.indexOf$(this, elem, from);
        }

        @Override
        public <B> int indexOf(B elem) {
            return SeqOps.indexOf$(this, elem);
        }

        @Override
        public <B> int lastIndexOf(B elem, int end) {
            return SeqOps.lastIndexOf$(this, elem, end);
        }

        @Override
        public <B> int lastIndexOf$default$2() {
            return SeqOps.lastIndexOf$default$2$(this);
        }

        @Override
        public int lastIndexWhere(Function1<A, Object> p, int end) {
            return SeqOps.lastIndexWhere$(this, p, end);
        }

        @Override
        public int lastIndexWhere(Function1<A, Object> p) {
            return SeqOps.lastIndexWhere$(this, p);
        }

        @Override
        public <B> int indexOfSlice(Seq<B> that, int from) {
            return SeqOps.indexOfSlice$(this, that, from);
        }

        @Override
        public <B> int indexOfSlice(Seq<B> that) {
            return SeqOps.indexOfSlice$(this, that);
        }

        @Override
        public <B> int lastIndexOfSlice(Seq<B> that, int end) {
            return SeqOps.lastIndexOfSlice$(this, that, end);
        }

        @Override
        public <B> int lastIndexOfSlice(Seq<B> that) {
            return SeqOps.lastIndexOfSlice$(this, that);
        }

        @Override
        public Option<A> findLast(Function1<A, Object> p) {
            return SeqOps.findLast$(this, p);
        }

        @Override
        public <B> boolean containsSlice(Seq<B> that) {
            return SeqOps.containsSlice$(this, that);
        }

        @Override
        public <A1> boolean contains(A1 elem) {
            return SeqOps.contains$(this, elem);
        }

        @Override
        public Object reverseMap(Function1 f) {
            return SeqOps.reverseMap$(this, f);
        }

        @Override
        public Iterator<View<A>> permutations() {
            return SeqOps.permutations$(this);
        }

        @Override
        public Iterator<View<A>> combinations(int n) {
            return SeqOps.combinations$(this, n);
        }

        @Override
        public Object sortWith(Function2 lt) {
            return SeqOps.sortWith$(this, lt);
        }

        @Override
        public Object sortBy(Function1 f, Ordering ord) {
            return SeqOps.sortBy$(this, f, ord);
        }

        @Override
        public Range indices() {
            return SeqOps.indices$(this);
        }

        @Override
        public final int sizeCompare(int otherSize) {
            return SeqOps.sizeCompare$((SeqOps)this, otherSize);
        }

        @Override
        public int lengthCompare(int len) {
            return SeqOps.lengthCompare$((SeqOps)this, len);
        }

        @Override
        public final int sizeCompare(Iterable<?> that) {
            return SeqOps.sizeCompare$((SeqOps)this, that);
        }

        @Override
        public int lengthCompare(Iterable<?> that) {
            return SeqOps.lengthCompare$((SeqOps)this, that);
        }

        @Override
        public final IterableOps lengthIs() {
            return SeqOps.lengthIs$(this);
        }

        @Override
        public boolean isEmpty() {
            return SeqOps.isEmpty$(this);
        }

        @Override
        public <B> boolean sameElements(IterableOnce<B> that) {
            return SeqOps.sameElements$(this, that);
        }

        @Override
        public <B$> boolean corresponds(Seq<B$> that, Function2<A, B$, Object> p) {
            return SeqOps.corresponds$(this, that, p);
        }

        @Override
        public Object diff(Seq that) {
            return SeqOps.diff$(this, that);
        }

        @Override
        public Object intersect(Seq that) {
            return SeqOps.intersect$(this, that);
        }

        @Override
        public Object patch(int from, IterableOnce other, int replaced) {
            return SeqOps.patch$(this, from, other, replaced);
        }

        @Override
        public Object updated(int index, Object elem) {
            return SeqOps.updated$(this, index, elem);
        }

        @Override
        public <B> scala.collection.mutable.Map<B, Object> occCounts(Seq<B> sq) {
            return SeqOps.occCounts$(this, sq);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
            return SeqOps.search$(this, elem, ord);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
            return SeqOps.search$(this, elem, from, to, ord);
        }

        @Override
        public int length() {
            return this.len;
        }

        @Override
        public A apply(int i) throws IndexOutOfBoundsException {
            return this.underlying.apply(i);
        }

        public DropRight(SeqOps<A, ?, Object> underlying, int n) {
            this.underlying = underlying;
            super(underlying, n);
            if (underlying == null) {
                throw null;
            }
            int max$extension_that = 0;
            int n2 = underlying.length() - Math.max(n, max$extension_that);
            int max$extension_that2 = 0;
            this.len = Math.max(n2, max$extension_that2);
        }
    }

    public static class Id<A>
    extends AbstractSeqView<A> {
        private static final long serialVersionUID = 3L;
        private final SeqOps<A, ?, Object> underlying;

        @Override
        public A apply(int idx) {
            return this.underlying.apply(idx);
        }

        @Override
        public int length() {
            return this.underlying.length();
        }

        @Override
        public Iterator<A> iterator() {
            return this.underlying.iterator();
        }

        @Override
        public int knownSize() {
            return this.underlying.knownSize();
        }

        @Override
        public boolean isEmpty() {
            return this.underlying.isEmpty();
        }

        public Id(SeqOps<A, ?, Object> underlying) {
            this.underlying = underlying;
        }
    }

    public static class Map<A, B>
    extends View.Map<A, B>
    implements SeqView<B> {
        private static final long serialVersionUID = 3L;
        private final SeqOps<A, ?, Object> underlying;
        private final Function1<A, B> f;

        @Override
        public SeqView<B> view() {
            return ((SeqView)this).view();
        }

        @Override
        public <B$> SeqView<B$> map(Function1<B, B$> f) {
            return ((SeqView)this).map(f);
        }

        @Override
        public <B$> SeqView<B$> appended(B$ elem) {
            return ((SeqView)this).appended(elem);
        }

        @Override
        public <B$> SeqView<B$> prepended(B$ elem) {
            return ((SeqView)this).prepended(elem);
        }

        @Override
        public SeqView<B> reverse() {
            return ((SeqView)this).reverse();
        }

        @Override
        public SeqView<B> take(int n) {
            return ((SeqView)this).take(n);
        }

        @Override
        public SeqView<B> drop(int n) {
            return ((SeqView)this).drop(n);
        }

        @Override
        public SeqView<B> takeRight(int n) {
            return ((SeqView)this).takeRight(n);
        }

        @Override
        public SeqView<B> dropRight(int n) {
            return ((SeqView)this).dropRight(n);
        }

        @Override
        public <U> SeqView<B> tapEach(Function1<B, U> f) {
            return ((SeqView)this).tapEach(f);
        }

        @Override
        public <B$> SeqView<B$> concat(SeqOps<B$, ?, Object> suffix) {
            return ((SeqView)this).concat(suffix);
        }

        @Override
        public <B$> SeqView<B$> appendedAll(SeqOps<B$, ?, Object> suffix) {
            return ((SeqView)this).appendedAll(suffix);
        }

        @Override
        public <B$> SeqView<B$> prependedAll(SeqOps<B$, ?, Object> prefix) {
            return ((SeqView)this).prependedAll(prefix);
        }

        @Override
        public <B$> SeqView<B> sorted(Ordering<B$> ord) {
            return ((SeqView)this).sorted(ord);
        }

        @Override
        public String stringPrefix() {
            return ((SeqView)this).stringPrefix();
        }

        @Override
        public /* synthetic */ Object scala$collection$SeqOps$$super$concat(IterableOnce suffix) {
            return IterableOps.concat$(this, suffix);
        }

        @Override
        public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(int otherSize) {
            return IterableOps.sizeCompare$((IterableOps)this, otherSize);
        }

        @Override
        public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(Iterable that) {
            return IterableOps.sizeCompare$((IterableOps)this, that);
        }

        @Override
        public final Object $plus$colon(Object elem) {
            return SeqOps.$plus$colon$(this, elem);
        }

        @Override
        public final Object $colon$plus(Object elem) {
            return SeqOps.$colon$plus$(this, elem);
        }

        @Override
        public Object prependedAll(IterableOnce prefix) {
            return SeqOps.prependedAll$(this, prefix);
        }

        @Override
        public final Object $plus$plus$colon(IterableOnce prefix) {
            return SeqOps.$plus$plus$colon$(this, prefix);
        }

        @Override
        public Object appendedAll(IterableOnce suffix) {
            return SeqOps.appendedAll$(this, suffix);
        }

        @Override
        public final Object $colon$plus$plus(IterableOnce suffix) {
            return SeqOps.$colon$plus$plus$(this, suffix);
        }

        @Override
        public final Object concat(IterableOnce suffix) {
            return SeqOps.concat$(this, suffix);
        }

        @Override
        public final Object union(Seq that) {
            return SeqOps.union$(this, that);
        }

        @Override
        public final int size() {
            return SeqOps.size$(this);
        }

        @Override
        public Object distinct() {
            return SeqOps.distinct$(this);
        }

        @Override
        public Object distinctBy(Function1 f) {
            return SeqOps.distinctBy$(this, f);
        }

        @Override
        public Iterator<B> reverseIterator() {
            return SeqOps.reverseIterator$(this);
        }

        @Override
        public <B$> boolean startsWith(IterableOnce<B$> that, int offset) {
            return SeqOps.startsWith$(this, that, offset);
        }

        @Override
        public <B$> int startsWith$default$2() {
            return SeqOps.startsWith$default$2$(this);
        }

        @Override
        public <B$> boolean endsWith(Iterable<B$> that) {
            return SeqOps.endsWith$(this, that);
        }

        @Override
        public boolean isDefinedAt(int idx) {
            return SeqOps.isDefinedAt$(this, idx);
        }

        @Override
        public Object padTo(int len, Object elem) {
            return SeqOps.padTo$(this, len, elem);
        }

        @Override
        public final int segmentLength(Function1<B, Object> p) {
            return SeqOps.segmentLength$(this, p);
        }

        @Override
        public int segmentLength(Function1<B, Object> p, int from) {
            return SeqOps.segmentLength$(this, p, from);
        }

        @Override
        public final int prefixLength(Function1<B, Object> p) {
            return SeqOps.prefixLength$(this, p);
        }

        @Override
        public int indexWhere(Function1<B, Object> p, int from) {
            return SeqOps.indexWhere$(this, p, from);
        }

        @Override
        public int indexWhere(Function1<B, Object> p) {
            return SeqOps.indexWhere$(this, p);
        }

        @Override
        public <B$> int indexOf(B$ elem, int from) {
            return SeqOps.indexOf$(this, elem, from);
        }

        @Override
        public <B$> int indexOf(B$ elem) {
            return SeqOps.indexOf$(this, elem);
        }

        @Override
        public <B$> int lastIndexOf(B$ elem, int end) {
            return SeqOps.lastIndexOf$(this, elem, end);
        }

        @Override
        public <B$> int lastIndexOf$default$2() {
            return SeqOps.lastIndexOf$default$2$(this);
        }

        @Override
        public int lastIndexWhere(Function1<B, Object> p, int end) {
            return SeqOps.lastIndexWhere$(this, p, end);
        }

        @Override
        public int lastIndexWhere(Function1<B, Object> p) {
            return SeqOps.lastIndexWhere$(this, p);
        }

        @Override
        public <B$> int indexOfSlice(Seq<B$> that, int from) {
            return SeqOps.indexOfSlice$(this, that, from);
        }

        @Override
        public <B$> int indexOfSlice(Seq<B$> that) {
            return SeqOps.indexOfSlice$(this, that);
        }

        @Override
        public <B$> int lastIndexOfSlice(Seq<B$> that, int end) {
            return SeqOps.lastIndexOfSlice$(this, that, end);
        }

        @Override
        public <B$> int lastIndexOfSlice(Seq<B$> that) {
            return SeqOps.lastIndexOfSlice$(this, that);
        }

        @Override
        public Option<B> findLast(Function1<B, Object> p) {
            return SeqOps.findLast$(this, p);
        }

        @Override
        public <B$> boolean containsSlice(Seq<B$> that) {
            return SeqOps.containsSlice$(this, that);
        }

        @Override
        public <A1> boolean contains(A1 elem) {
            return SeqOps.contains$(this, elem);
        }

        @Override
        public Object reverseMap(Function1 f) {
            return SeqOps.reverseMap$(this, f);
        }

        @Override
        public Iterator<View<B>> permutations() {
            return SeqOps.permutations$(this);
        }

        @Override
        public Iterator<View<B>> combinations(int n) {
            return SeqOps.combinations$(this, n);
        }

        @Override
        public Object sortWith(Function2 lt) {
            return SeqOps.sortWith$(this, lt);
        }

        @Override
        public Object sortBy(Function1 f, Ordering ord) {
            return SeqOps.sortBy$(this, f, ord);
        }

        @Override
        public Range indices() {
            return SeqOps.indices$(this);
        }

        @Override
        public final int sizeCompare(int otherSize) {
            return SeqOps.sizeCompare$((SeqOps)this, otherSize);
        }

        @Override
        public int lengthCompare(int len) {
            return SeqOps.lengthCompare$((SeqOps)this, len);
        }

        @Override
        public final int sizeCompare(Iterable<?> that) {
            return SeqOps.sizeCompare$((SeqOps)this, that);
        }

        @Override
        public int lengthCompare(Iterable<?> that) {
            return SeqOps.lengthCompare$((SeqOps)this, that);
        }

        @Override
        public final IterableOps lengthIs() {
            return SeqOps.lengthIs$(this);
        }

        @Override
        public boolean isEmpty() {
            return SeqOps.isEmpty$(this);
        }

        @Override
        public <B$> boolean sameElements(IterableOnce<B$> that) {
            return SeqOps.sameElements$(this, that);
        }

        @Override
        public <B$> boolean corresponds(Seq<B$> that, Function2<B, B$, Object> p) {
            return SeqOps.corresponds$(this, that, p);
        }

        @Override
        public Object diff(Seq that) {
            return SeqOps.diff$(this, that);
        }

        @Override
        public Object intersect(Seq that) {
            return SeqOps.intersect$(this, that);
        }

        @Override
        public Object patch(int from, IterableOnce other, int replaced) {
            return SeqOps.patch$(this, from, other, replaced);
        }

        @Override
        public Object updated(int index, Object elem) {
            return SeqOps.updated$(this, index, elem);
        }

        @Override
        public <B> scala.collection.mutable.Map<B, Object> occCounts(Seq<B> sq) {
            return SeqOps.occCounts$(this, sq);
        }

        @Override
        public <B$> Searching.SearchResult search(B$ elem, Ordering<B$> ord) {
            return SeqOps.search$(this, elem, ord);
        }

        @Override
        public <B$> Searching.SearchResult search(B$ elem, int from, int to, Ordering<B$> ord) {
            return SeqOps.search$(this, elem, from, to, ord);
        }

        @Override
        public B apply(int idx) {
            return this.f.apply(this.underlying.apply(idx));
        }

        @Override
        public int length() {
            return this.underlying.length();
        }

        public Map(SeqOps<A, ?, Object> underlying, Function1<A, B> f) {
            this.underlying = underlying;
            this.f = f;
            super(underlying, f);
        }
    }

    public static class Prepended<A>
    extends View.Prepended<A>
    implements SeqView<A> {
        private static final long serialVersionUID = 3L;
        private final A elem;
        private final SeqOps<A, ?, Object> underlying;

        @Override
        public SeqView<A> view() {
            return ((SeqView)this).view();
        }

        @Override
        public <B$> SeqView<B$> map(Function1<A, B$> f) {
            return ((SeqView)this).map(f);
        }

        @Override
        public <B> SeqView<B> appended(B elem) {
            return ((SeqView)this).appended(elem);
        }

        @Override
        public <B> SeqView<B> prepended(B elem) {
            return ((SeqView)this).prepended(elem);
        }

        @Override
        public SeqView<A> reverse() {
            return ((SeqView)this).reverse();
        }

        @Override
        public SeqView<A> take(int n) {
            return ((SeqView)this).take(n);
        }

        @Override
        public SeqView<A> drop(int n) {
            return ((SeqView)this).drop(n);
        }

        @Override
        public SeqView<A> takeRight(int n) {
            return ((SeqView)this).takeRight(n);
        }

        @Override
        public SeqView<A> dropRight(int n) {
            return ((SeqView)this).dropRight(n);
        }

        @Override
        public <U> SeqView<A> tapEach(Function1<A, U> f) {
            return ((SeqView)this).tapEach(f);
        }

        @Override
        public <B> SeqView<B> concat(SeqOps<B, ?, Object> suffix) {
            return ((SeqView)this).concat(suffix);
        }

        @Override
        public <B> SeqView<B> appendedAll(SeqOps<B, ?, Object> suffix) {
            return ((SeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> SeqView<B> prependedAll(SeqOps<B, ?, Object> prefix) {
            return ((SeqView)this).prependedAll(prefix);
        }

        @Override
        public <B> SeqView<A> sorted(Ordering<B> ord) {
            return ((SeqView)this).sorted(ord);
        }

        @Override
        public String stringPrefix() {
            return ((SeqView)this).stringPrefix();
        }

        @Override
        public /* synthetic */ Object scala$collection$SeqOps$$super$concat(IterableOnce suffix) {
            return IterableOps.concat$(this, suffix);
        }

        @Override
        public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(int otherSize) {
            return IterableOps.sizeCompare$((IterableOps)this, otherSize);
        }

        @Override
        public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(Iterable that) {
            return IterableOps.sizeCompare$((IterableOps)this, that);
        }

        @Override
        public final Object $plus$colon(Object elem) {
            return SeqOps.$plus$colon$(this, elem);
        }

        @Override
        public final Object $colon$plus(Object elem) {
            return SeqOps.$colon$plus$(this, elem);
        }

        @Override
        public Object prependedAll(IterableOnce prefix) {
            return SeqOps.prependedAll$(this, prefix);
        }

        @Override
        public final Object $plus$plus$colon(IterableOnce prefix) {
            return SeqOps.$plus$plus$colon$(this, prefix);
        }

        @Override
        public Object appendedAll(IterableOnce suffix) {
            return SeqOps.appendedAll$(this, suffix);
        }

        @Override
        public final Object $colon$plus$plus(IterableOnce suffix) {
            return SeqOps.$colon$plus$plus$(this, suffix);
        }

        @Override
        public final Object concat(IterableOnce suffix) {
            return SeqOps.concat$(this, suffix);
        }

        @Override
        public final Object union(Seq that) {
            return SeqOps.union$(this, that);
        }

        @Override
        public final int size() {
            return SeqOps.size$(this);
        }

        @Override
        public Object distinct() {
            return SeqOps.distinct$(this);
        }

        @Override
        public Object distinctBy(Function1 f) {
            return SeqOps.distinctBy$(this, f);
        }

        @Override
        public Iterator<A> reverseIterator() {
            return SeqOps.reverseIterator$(this);
        }

        @Override
        public <B> boolean startsWith(IterableOnce<B> that, int offset) {
            return SeqOps.startsWith$(this, that, offset);
        }

        @Override
        public <B> int startsWith$default$2() {
            return SeqOps.startsWith$default$2$(this);
        }

        @Override
        public <B> boolean endsWith(Iterable<B> that) {
            return SeqOps.endsWith$(this, that);
        }

        @Override
        public boolean isDefinedAt(int idx) {
            return SeqOps.isDefinedAt$(this, idx);
        }

        @Override
        public Object padTo(int len, Object elem) {
            return SeqOps.padTo$(this, len, elem);
        }

        @Override
        public final int segmentLength(Function1<A, Object> p) {
            return SeqOps.segmentLength$(this, p);
        }

        @Override
        public int segmentLength(Function1<A, Object> p, int from) {
            return SeqOps.segmentLength$(this, p, from);
        }

        @Override
        public final int prefixLength(Function1<A, Object> p) {
            return SeqOps.prefixLength$(this, p);
        }

        @Override
        public int indexWhere(Function1<A, Object> p, int from) {
            return SeqOps.indexWhere$(this, p, from);
        }

        @Override
        public int indexWhere(Function1<A, Object> p) {
            return SeqOps.indexWhere$(this, p);
        }

        @Override
        public <B> int indexOf(B elem, int from) {
            return SeqOps.indexOf$(this, elem, from);
        }

        @Override
        public <B> int indexOf(B elem) {
            return SeqOps.indexOf$(this, elem);
        }

        @Override
        public <B> int lastIndexOf(B elem, int end) {
            return SeqOps.lastIndexOf$(this, elem, end);
        }

        @Override
        public <B> int lastIndexOf$default$2() {
            return SeqOps.lastIndexOf$default$2$(this);
        }

        @Override
        public int lastIndexWhere(Function1<A, Object> p, int end) {
            return SeqOps.lastIndexWhere$(this, p, end);
        }

        @Override
        public int lastIndexWhere(Function1<A, Object> p) {
            return SeqOps.lastIndexWhere$(this, p);
        }

        @Override
        public <B> int indexOfSlice(Seq<B> that, int from) {
            return SeqOps.indexOfSlice$(this, that, from);
        }

        @Override
        public <B> int indexOfSlice(Seq<B> that) {
            return SeqOps.indexOfSlice$(this, that);
        }

        @Override
        public <B> int lastIndexOfSlice(Seq<B> that, int end) {
            return SeqOps.lastIndexOfSlice$(this, that, end);
        }

        @Override
        public <B> int lastIndexOfSlice(Seq<B> that) {
            return SeqOps.lastIndexOfSlice$(this, that);
        }

        @Override
        public Option<A> findLast(Function1<A, Object> p) {
            return SeqOps.findLast$(this, p);
        }

        @Override
        public <B> boolean containsSlice(Seq<B> that) {
            return SeqOps.containsSlice$(this, that);
        }

        @Override
        public <A1> boolean contains(A1 elem) {
            return SeqOps.contains$(this, elem);
        }

        @Override
        public Object reverseMap(Function1 f) {
            return SeqOps.reverseMap$(this, f);
        }

        @Override
        public Iterator<View<A>> permutations() {
            return SeqOps.permutations$(this);
        }

        @Override
        public Iterator<View<A>> combinations(int n) {
            return SeqOps.combinations$(this, n);
        }

        @Override
        public Object sortWith(Function2 lt) {
            return SeqOps.sortWith$(this, lt);
        }

        @Override
        public Object sortBy(Function1 f, Ordering ord) {
            return SeqOps.sortBy$(this, f, ord);
        }

        @Override
        public Range indices() {
            return SeqOps.indices$(this);
        }

        @Override
        public final int sizeCompare(int otherSize) {
            return SeqOps.sizeCompare$((SeqOps)this, otherSize);
        }

        @Override
        public int lengthCompare(int len) {
            return SeqOps.lengthCompare$((SeqOps)this, len);
        }

        @Override
        public final int sizeCompare(Iterable<?> that) {
            return SeqOps.sizeCompare$((SeqOps)this, that);
        }

        @Override
        public int lengthCompare(Iterable<?> that) {
            return SeqOps.lengthCompare$((SeqOps)this, that);
        }

        @Override
        public final IterableOps lengthIs() {
            return SeqOps.lengthIs$(this);
        }

        @Override
        public boolean isEmpty() {
            return SeqOps.isEmpty$(this);
        }

        @Override
        public <B> boolean sameElements(IterableOnce<B> that) {
            return SeqOps.sameElements$(this, that);
        }

        @Override
        public <B$> boolean corresponds(Seq<B$> that, Function2<A, B$, Object> p) {
            return SeqOps.corresponds$(this, that, p);
        }

        @Override
        public Object diff(Seq that) {
            return SeqOps.diff$(this, that);
        }

        @Override
        public Object intersect(Seq that) {
            return SeqOps.intersect$(this, that);
        }

        @Override
        public Object patch(int from, IterableOnce other, int replaced) {
            return SeqOps.patch$(this, from, other, replaced);
        }

        @Override
        public Object updated(int index, Object elem) {
            return SeqOps.updated$(this, index, elem);
        }

        @Override
        public <B> scala.collection.mutable.Map<B, Object> occCounts(Seq<B> sq) {
            return SeqOps.occCounts$(this, sq);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
            return SeqOps.search$(this, elem, ord);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
            return SeqOps.search$(this, elem, from, to, ord);
        }

        @Override
        public A apply(int idx) {
            if (idx == 0) {
                return this.elem;
            }
            return this.underlying.apply(idx - 1);
        }

        @Override
        public int length() {
            return this.underlying.length() + 1;
        }

        public Prepended(A elem, SeqOps<A, ?, Object> underlying) {
            this.elem = elem;
            this.underlying = underlying;
            super(elem, underlying);
        }
    }

    public static class Reverse<A>
    extends AbstractSeqView<A> {
        private static final long serialVersionUID = 3L;
        private final SeqOps<A, ?, Object> underlying;

        @Override
        public A apply(int i) {
            return this.underlying.apply(this.length() - 1 - i);
        }

        @Override
        public int length() {
            SeqOps<A, ?, Object> seqOps = this.underlying;
            if (seqOps == null) {
                throw null;
            }
            return seqOps.length();
        }

        @Override
        public Iterator<A> iterator() {
            return this.underlying.reverseIterator();
        }

        @Override
        public int knownSize() {
            return this.underlying.knownSize();
        }

        @Override
        public boolean isEmpty() {
            return this.underlying.isEmpty();
        }

        public Reverse(SeqOps<A, ?, Object> underlying) {
            this.underlying = underlying;
        }
    }

    public static class Sorted<A, B>
    implements SeqView<A> {
        private static final long serialVersionUID = 3L;
        private Seq<A> scala$collection$SeqView$Sorted$$_sorted;
        private SeqOps<A, ?, Object> underlying;
        public final int scala$collection$SeqView$Sorted$$len;
        public final Ordering<B> scala$collection$SeqView$Sorted$$ord;
        private volatile boolean evaluated;
        private volatile boolean bitmap$0;

        @Override
        public SeqView<A> view() {
            return ((SeqView)this).view();
        }

        @Override
        public <B$> SeqView<B$> map(Function1<A, B$> f) {
            return ((SeqView)this).map(f);
        }

        @Override
        public <B> SeqView<B> appended(B elem) {
            return ((SeqView)this).appended(elem);
        }

        @Override
        public <B> SeqView<B> prepended(B elem) {
            return ((SeqView)this).prepended(elem);
        }

        @Override
        public SeqView<A> take(int n) {
            return ((SeqView)this).take(n);
        }

        @Override
        public SeqView<A> drop(int n) {
            return ((SeqView)this).drop(n);
        }

        @Override
        public SeqView<A> takeRight(int n) {
            return ((SeqView)this).takeRight(n);
        }

        @Override
        public SeqView<A> dropRight(int n) {
            return ((SeqView)this).dropRight(n);
        }

        @Override
        public <U> SeqView<A> tapEach(Function1<A, U> f) {
            return ((SeqView)this).tapEach(f);
        }

        @Override
        public <B> SeqView<B> concat(SeqOps<B, ?, Object> suffix) {
            return ((SeqView)this).concat(suffix);
        }

        @Override
        public <B> SeqView<B> appendedAll(SeqOps<B, ?, Object> suffix) {
            return ((SeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> SeqView<B> prependedAll(SeqOps<B, ?, Object> prefix) {
            return ((SeqView)this).prependedAll(prefix);
        }

        @Override
        public String stringPrefix() {
            return ((SeqView)this).stringPrefix();
        }

        @Override
        public IterableFactory<View> iterableFactory() {
            return View.iterableFactory$(this);
        }

        @Override
        public View<A> empty() {
            return View.empty$(this);
        }

        @Override
        public String toString() {
            return View.toString$(this);
        }

        @Override
        public IndexedSeq<A> force() {
            return View.force$(this);
        }

        @Override
        public final Iterable<A> toIterable() {
            return Iterable.toIterable$(this);
        }

        @Override
        public final Iterable<A> coll() {
            return Iterable.coll$(this);
        }

        @Override
        public Iterable<A> seq() {
            return Iterable.seq$(this);
        }

        @Override
        public String className() {
            return Iterable.className$(this);
        }

        @Override
        public final String collectionClassName() {
            return Iterable.collectionClassName$(this);
        }

        @Override
        public <B> LazyZip2<A, B, Iterable> lazyZip(Iterable<B> that) {
            return Iterable.lazyZip$(this, that);
        }

        @Override
        public IterableOps fromSpecific(IterableOnce coll) {
            return IterableFactoryDefaults.fromSpecific$(this, coll);
        }

        @Override
        public Builder<A, View<A>> newSpecificBuilder() {
            return IterableFactoryDefaults.newSpecificBuilder$(this);
        }

        @Override
        public /* synthetic */ Object scala$collection$SeqOps$$super$concat(IterableOnce suffix) {
            return IterableOps.concat$(this, suffix);
        }

        @Override
        public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(int otherSize) {
            return IterableOps.sizeCompare$((IterableOps)this, otherSize);
        }

        @Override
        public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(Iterable that) {
            return IterableOps.sizeCompare$((IterableOps)this, that);
        }

        @Override
        public final Object $plus$colon(Object elem) {
            return SeqOps.$plus$colon$(this, elem);
        }

        @Override
        public final Object $colon$plus(Object elem) {
            return SeqOps.$colon$plus$(this, elem);
        }

        @Override
        public Object prependedAll(IterableOnce prefix) {
            return SeqOps.prependedAll$(this, prefix);
        }

        @Override
        public final Object $plus$plus$colon(IterableOnce prefix) {
            return SeqOps.$plus$plus$colon$(this, prefix);
        }

        @Override
        public Object appendedAll(IterableOnce suffix) {
            return SeqOps.appendedAll$(this, suffix);
        }

        @Override
        public final Object $colon$plus$plus(IterableOnce suffix) {
            return SeqOps.$colon$plus$plus$(this, suffix);
        }

        @Override
        public final Object concat(IterableOnce suffix) {
            return SeqOps.concat$(this, suffix);
        }

        @Override
        public final Object union(Seq that) {
            return SeqOps.union$(this, that);
        }

        @Override
        public final int size() {
            return SeqOps.size$(this);
        }

        @Override
        public Object distinct() {
            return SeqOps.distinct$(this);
        }

        @Override
        public Object distinctBy(Function1 f) {
            return SeqOps.distinctBy$(this, f);
        }

        @Override
        public Iterator<A> reverseIterator() {
            return SeqOps.reverseIterator$(this);
        }

        @Override
        public <B> boolean startsWith(IterableOnce<B> that, int offset) {
            return SeqOps.startsWith$(this, that, offset);
        }

        @Override
        public <B> int startsWith$default$2() {
            return SeqOps.startsWith$default$2$(this);
        }

        @Override
        public <B> boolean endsWith(Iterable<B> that) {
            return SeqOps.endsWith$(this, that);
        }

        @Override
        public boolean isDefinedAt(int idx) {
            return SeqOps.isDefinedAt$(this, idx);
        }

        @Override
        public Object padTo(int len, Object elem) {
            return SeqOps.padTo$(this, len, elem);
        }

        @Override
        public final int segmentLength(Function1<A, Object> p) {
            return SeqOps.segmentLength$(this, p);
        }

        @Override
        public int segmentLength(Function1<A, Object> p, int from) {
            return SeqOps.segmentLength$(this, p, from);
        }

        @Override
        public final int prefixLength(Function1<A, Object> p) {
            return SeqOps.prefixLength$(this, p);
        }

        @Override
        public int indexWhere(Function1<A, Object> p, int from) {
            return SeqOps.indexWhere$(this, p, from);
        }

        @Override
        public int indexWhere(Function1<A, Object> p) {
            return SeqOps.indexWhere$(this, p);
        }

        @Override
        public <B> int indexOf(B elem, int from) {
            return SeqOps.indexOf$(this, elem, from);
        }

        @Override
        public <B> int indexOf(B elem) {
            return SeqOps.indexOf$(this, elem);
        }

        @Override
        public <B> int lastIndexOf(B elem, int end) {
            return SeqOps.lastIndexOf$(this, elem, end);
        }

        @Override
        public <B> int lastIndexOf$default$2() {
            return SeqOps.lastIndexOf$default$2$(this);
        }

        @Override
        public int lastIndexWhere(Function1<A, Object> p, int end) {
            return SeqOps.lastIndexWhere$(this, p, end);
        }

        @Override
        public int lastIndexWhere(Function1<A, Object> p) {
            return SeqOps.lastIndexWhere$(this, p);
        }

        @Override
        public <B> int indexOfSlice(Seq<B> that, int from) {
            return SeqOps.indexOfSlice$(this, that, from);
        }

        @Override
        public <B> int indexOfSlice(Seq<B> that) {
            return SeqOps.indexOfSlice$(this, that);
        }

        @Override
        public <B> int lastIndexOfSlice(Seq<B> that, int end) {
            return SeqOps.lastIndexOfSlice$(this, that, end);
        }

        @Override
        public <B> int lastIndexOfSlice(Seq<B> that) {
            return SeqOps.lastIndexOfSlice$(this, that);
        }

        @Override
        public Option<A> findLast(Function1<A, Object> p) {
            return SeqOps.findLast$(this, p);
        }

        @Override
        public <B> boolean containsSlice(Seq<B> that) {
            return SeqOps.containsSlice$(this, that);
        }

        @Override
        public <A1> boolean contains(A1 elem) {
            return SeqOps.contains$(this, elem);
        }

        @Override
        public Object reverseMap(Function1 f) {
            return SeqOps.reverseMap$(this, f);
        }

        @Override
        public Iterator<View<A>> permutations() {
            return SeqOps.permutations$(this);
        }

        @Override
        public Iterator<View<A>> combinations(int n) {
            return SeqOps.combinations$(this, n);
        }

        @Override
        public Object sortWith(Function2 lt) {
            return SeqOps.sortWith$(this, lt);
        }

        @Override
        public Object sortBy(Function1 f, Ordering ord) {
            return SeqOps.sortBy$(this, f, ord);
        }

        @Override
        public Range indices() {
            return SeqOps.indices$(this);
        }

        @Override
        public final int sizeCompare(int otherSize) {
            return SeqOps.sizeCompare$((SeqOps)this, otherSize);
        }

        @Override
        public int lengthCompare(int len) {
            return SeqOps.lengthCompare$((SeqOps)this, len);
        }

        @Override
        public final int sizeCompare(Iterable<?> that) {
            return SeqOps.sizeCompare$((SeqOps)this, that);
        }

        @Override
        public int lengthCompare(Iterable<?> that) {
            return SeqOps.lengthCompare$((SeqOps)this, that);
        }

        @Override
        public final IterableOps lengthIs() {
            return SeqOps.lengthIs$(this);
        }

        @Override
        public <B> boolean sameElements(IterableOnce<B> that) {
            return SeqOps.sameElements$(this, that);
        }

        @Override
        public <B$> boolean corresponds(Seq<B$> that, Function2<A, B$, Object> p) {
            return SeqOps.corresponds$(this, that, p);
        }

        @Override
        public Object diff(Seq that) {
            return SeqOps.diff$(this, that);
        }

        @Override
        public Object intersect(Seq that) {
            return SeqOps.intersect$(this, that);
        }

        @Override
        public Object patch(int from, IterableOnce other, int replaced) {
            return SeqOps.patch$(this, from, other, replaced);
        }

        @Override
        public Object updated(int index, Object elem) {
            return SeqOps.updated$(this, index, elem);
        }

        @Override
        public <B> scala.collection.mutable.Map<B, Object> occCounts(Seq<B> sq) {
            return SeqOps.occCounts$(this, sq);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
            return SeqOps.search$(this, elem, ord);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
            return SeqOps.search$(this, elem, from, to, ord);
        }

        @Override
        public final Iterable<A> toTraversable() {
            return IterableOps.toTraversable$(this);
        }

        @Override
        public boolean isTraversableAgain() {
            return IterableOps.isTraversableAgain$(this);
        }

        @Override
        public final Object repr() {
            return IterableOps.repr$(this);
        }

        @Override
        public IterableFactory<?> companion() {
            return IterableOps.companion$(this);
        }

        @Override
        public A head() {
            return (A)IterableOps.head$(this);
        }

        @Override
        public Option<A> headOption() {
            return IterableOps.headOption$(this);
        }

        @Override
        public A last() {
            return (A)IterableOps.last$(this);
        }

        @Override
        public Option<A> lastOption() {
            return IterableOps.lastOption$(this);
        }

        @Override
        public final IterableOps sizeIs() {
            return IterableOps.sizeIs$(this);
        }

        @Override
        public View<A> view(int from, int until) {
            return IterableOps.view$(this, from, until);
        }

        @Override
        public Object transpose(Function1 asIterable) {
            return IterableOps.transpose$(this, asIterable);
        }

        @Override
        public Object filter(Function1 pred) {
            return IterableOps.filter$(this, pred);
        }

        @Override
        public Object filterNot(Function1 pred) {
            return IterableOps.filterNot$(this, pred);
        }

        @Override
        public WithFilter<A, ?> withFilter(Function1<A, Object> p) {
            return IterableOps.withFilter$(this, p);
        }

        @Override
        public Tuple2<View<A>, View<A>> partition(Function1<A, Object> p) {
            return IterableOps.partition$(this, p);
        }

        @Override
        public Tuple2<View<A>, View<A>> splitAt(int n) {
            return IterableOps.splitAt$(this, n);
        }

        @Override
        public Object takeWhile(Function1 p) {
            return IterableOps.takeWhile$(this, p);
        }

        @Override
        public Tuple2<View<A>, View<A>> span(Function1<A, Object> p) {
            return IterableOps.span$(this, p);
        }

        @Override
        public Object dropWhile(Function1 p) {
            return IterableOps.dropWhile$(this, p);
        }

        @Override
        public Iterator<View<A>> grouped(int size) {
            return IterableOps.grouped$(this, size);
        }

        @Override
        public Iterator<View<A>> sliding(int size) {
            return IterableOps.sliding$(this, size);
        }

        @Override
        public Iterator<View<A>> sliding(int size, int step) {
            return IterableOps.sliding$(this, size, step);
        }

        @Override
        public Object tail() {
            return IterableOps.tail$(this);
        }

        @Override
        public Object init() {
            return IterableOps.init$(this);
        }

        @Override
        public Object slice(int from, int until) {
            return IterableOps.slice$(this, from, until);
        }

        @Override
        public <K$> scala.collection.immutable.Map<K$, View<A>> groupBy(Function1<A, K$> f) {
            return IterableOps.groupBy$(this, f);
        }

        @Override
        public <K$, B> scala.collection.immutable.Map<K$, View<B>> groupMap(Function1<A, K$> key, Function1<A, B> f) {
            return IterableOps.groupMap$(this, key, f);
        }

        @Override
        public <K$, B> scala.collection.immutable.Map<K$, B> groupMapReduce(Function1<A, K$> key, Function1<A, B> f, Function2<B, B, B> reduce) {
            return IterableOps.groupMapReduce$(this, key, f, reduce);
        }

        @Override
        public Object scan(Object z, Function2 op) {
            return IterableOps.scan$(this, z, op);
        }

        @Override
        public Object scanLeft(Object z, Function2 op) {
            return IterableOps.scanLeft$(this, z, op);
        }

        @Override
        public Object scanRight(Object z, Function2 op) {
            return IterableOps.scanRight$(this, z, op);
        }

        @Override
        public Object flatMap(Function1 f) {
            return IterableOps.flatMap$(this, f);
        }

        @Override
        public Object flatten(Function1 asIterable) {
            return IterableOps.flatten$(this, asIterable);
        }

        @Override
        public Object collect(PartialFunction pf) {
            return IterableOps.collect$(this, pf);
        }

        @Override
        public <A1, A2> Tuple2<View<A1>, View<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
            return IterableOps.partitionMap$(this, f);
        }

        @Override
        public final Object $plus$plus(IterableOnce suffix) {
            return IterableOps.$plus$plus$(this, suffix);
        }

        @Override
        public Object zip(IterableOnce that) {
            return IterableOps.zip$(this, that);
        }

        @Override
        public Object zipWithIndex() {
            return IterableOps.zipWithIndex$(this);
        }

        @Override
        public Object zipAll(Iterable that, Object thisElem, Object thatElem) {
            return IterableOps.zipAll$(this, that, thisElem, thatElem);
        }

        @Override
        public <A1, A2> Tuple2<View<A1>, View<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
            return IterableOps.unzip$(this, asPair);
        }

        @Override
        public <A1, A2, A3> Tuple3<View<A1>, View<A2>, View<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
            return IterableOps.unzip3$(this, asTriple);
        }

        @Override
        public Iterator<View<A>> tails() {
            return IterableOps.tails$(this);
        }

        @Override
        public Iterator<View<A>> inits() {
            return IterableOps.inits$(this);
        }

        @Override
        public boolean hasDefiniteSize() {
            return IterableOnceOps.hasDefiniteSize$(this);
        }

        @Override
        public <U> void foreach(Function1<A, U> f) {
            IterableOnceOps.foreach$(this, f);
        }

        @Override
        public boolean forall(Function1<A, Object> p) {
            return IterableOnceOps.forall$(this, p);
        }

        @Override
        public boolean exists(Function1<A, Object> p) {
            return IterableOnceOps.exists$(this, p);
        }

        @Override
        public int count(Function1<A, Object> p) {
            return IterableOnceOps.count$(this, p);
        }

        @Override
        public Option<A> find(Function1<A, Object> p) {
            return IterableOnceOps.find$(this, p);
        }

        @Override
        public <B> B foldLeft(B z, Function2<B, A, B> op) {
            return (B)IterableOnceOps.foldLeft$(this, z, op);
        }

        @Override
        public <B> B foldRight(B z, Function2<A, B, B> op) {
            return (B)IterableOnceOps.foldRight$(this, z, op);
        }

        @Override
        public final <B> B $div$colon(B z, Function2<B, A, B> op) {
            return (B)IterableOnceOps.$div$colon$(this, z, op);
        }

        @Override
        public final <B> B $colon$bslash(B z, Function2<A, B, B> op) {
            return (B)IterableOnceOps.$colon$bslash$(this, z, op);
        }

        @Override
        public <A1> A1 fold(A1 z, Function2<A1, A1, A1> op) {
            return (A1)IterableOnceOps.fold$(this, z, op);
        }

        @Override
        public <B> B reduce(Function2<B, B, B> op) {
            return (B)IterableOnceOps.reduce$(this, op);
        }

        @Override
        public <B> Option<B> reduceOption(Function2<B, B, B> op) {
            return IterableOnceOps.reduceOption$(this, op);
        }

        @Override
        public <B> B reduceLeft(Function2<B, A, B> op) {
            return (B)IterableOnceOps.reduceLeft$(this, op);
        }

        @Override
        public <B> B reduceRight(Function2<A, B, B> op) {
            return (B)IterableOnceOps.reduceRight$(this, op);
        }

        @Override
        public <B> Option<B> reduceLeftOption(Function2<B, A, B> op) {
            return IterableOnceOps.reduceLeftOption$(this, op);
        }

        @Override
        public <B> Option<B> reduceRightOption(Function2<A, B, B> op) {
            return IterableOnceOps.reduceRightOption$(this, op);
        }

        @Override
        public boolean nonEmpty() {
            return IterableOnceOps.nonEmpty$(this);
        }

        @Override
        public final <B> void copyToBuffer(Buffer<B> dest) {
            IterableOnceOps.copyToBuffer$(this, dest);
        }

        @Override
        public <B> int copyToArray(Object xs) {
            return IterableOnceOps.copyToArray$(this, xs);
        }

        @Override
        public <B> int copyToArray(Object xs, int start2) {
            return IterableOnceOps.copyToArray$(this, xs, start2);
        }

        @Override
        public <B> int copyToArray(Object xs, int start2, int len) {
            return IterableOnceOps.copyToArray$(this, xs, start2, len);
        }

        @Override
        public <B> B sum(Numeric<B> num) {
            return (B)IterableOnceOps.sum$(this, num);
        }

        @Override
        public <B> B product(Numeric<B> num) {
            return (B)IterableOnceOps.product$(this, num);
        }

        @Override
        public <B> A min(Ordering<B> ord) {
            return (A)IterableOnceOps.min$(this, ord);
        }

        @Override
        public <B> Option<A> minOption(Ordering<B> ord) {
            return IterableOnceOps.minOption$(this, ord);
        }

        @Override
        public <B> A max(Ordering<B> ord) {
            return (A)IterableOnceOps.max$(this, ord);
        }

        @Override
        public <B> Option<A> maxOption(Ordering<B> ord) {
            return IterableOnceOps.maxOption$(this, ord);
        }

        @Override
        public <B> A maxBy(Function1<A, B> f, Ordering<B> cmp) {
            return (A)IterableOnceOps.maxBy$(this, f, cmp);
        }

        @Override
        public <B> Option<A> maxByOption(Function1<A, B> f, Ordering<B> cmp) {
            return IterableOnceOps.maxByOption$(this, f, cmp);
        }

        @Override
        public <B> A minBy(Function1<A, B> f, Ordering<B> cmp) {
            return (A)IterableOnceOps.minBy$(this, f, cmp);
        }

        @Override
        public <B> Option<A> minByOption(Function1<A, B> f, Ordering<B> cmp) {
            return IterableOnceOps.minByOption$(this, f, cmp);
        }

        @Override
        public <B> Option<B> collectFirst(PartialFunction<A, B> pf) {
            return IterableOnceOps.collectFirst$(this, pf);
        }

        @Override
        public <B> B aggregate(Function0<B> z, Function2<B, A, B> seqop, Function2<B, B, B> combop) {
            return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
        }

        @Override
        public <B> boolean corresponds(IterableOnce<B> that, Function2<A, B, Object> p) {
            return IterableOnceOps.corresponds$(this, that, p);
        }

        @Override
        public final String mkString(String start2, String sep, String end) {
            return IterableOnceOps.mkString$(this, start2, sep, end);
        }

        @Override
        public final String mkString(String sep) {
            return IterableOnceOps.mkString$(this, sep);
        }

        @Override
        public final String mkString() {
            return IterableOnceOps.mkString$(this);
        }

        @Override
        public StringBuilder addString(StringBuilder b, String start2, String sep, String end) {
            return IterableOnceOps.addString$(this, b, start2, sep, end);
        }

        @Override
        public final StringBuilder addString(StringBuilder b, String sep) {
            return IterableOnceOps.addString$(this, b, sep);
        }

        @Override
        public final StringBuilder addString(StringBuilder b) {
            return IterableOnceOps.addString$(this, b);
        }

        @Override
        public final Iterator<A> toIterator() {
            return IterableOnceOps.toIterator$(this);
        }

        @Override
        public List<A> toList() {
            return IterableOnceOps.toList$(this);
        }

        @Override
        public Vector<A> toVector() {
            return IterableOnceOps.toVector$(this);
        }

        @Override
        public <K$, V$> scala.collection.immutable.Map<K$, V$> toMap($less$colon$less<A, Tuple2<K$, V$>> ev) {
            return IterableOnceOps.toMap$(this, ev);
        }

        @Override
        public <B> Set<B> toSet() {
            return IterableOnceOps.toSet$(this);
        }

        @Override
        public scala.collection.immutable.Seq<A> toSeq() {
            return IterableOnceOps.toSeq$(this);
        }

        @Override
        public scala.collection.immutable.IndexedSeq<A> toIndexedSeq() {
            return IterableOnceOps.toIndexedSeq$(this);
        }

        @Override
        public final Stream<A> toStream() {
            return IterableOnceOps.toStream$(this);
        }

        @Override
        public final <B> Buffer<B> toBuffer() {
            return IterableOnceOps.toBuffer$(this);
        }

        @Override
        public <B> Object toArray(ClassTag<B> evidence$2) {
            return IterableOnceOps.toArray$(this, evidence$2);
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
            return (S)IterableOnce.stepper$(this, shape);
        }

        /*
         * WARNING - void declaration
         */
        private Seq<A> _sorted$lzycompute() {
            synchronized (this) {
                if (!this.bitmap$0) {
                    void var1_4;
                    Object object;
                    int len = this.scala$collection$SeqView$Sorted$$len;
                    if (len == 0) {
                        object = Nil$.MODULE$;
                    } else if (len == 1) {
                        void apply_elems;
                        ArraySeq arraySeq = ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.underlying.head()});
                        if (package$.MODULE$.List() == null) {
                            throw null;
                        }
                        object = Nil$.MODULE$.prependedAll((IterableOnce)apply_elems);
                        arraySeq = null;
                    } else {
                        Object[] arr = new Object[len];
                        this.underlying.copyToArray(arr);
                        Arrays.sort(arr, this.scala$collection$SeqView$Sorted$$ord);
                        object = ArraySeq$.MODULE$.unsafeWrapArray(arr);
                    }
                    Nil$ res = object;
                    this.evaluated = true;
                    this.underlying = null;
                    this.scala$collection$SeqView$Sorted$$_sorted = var1_4;
                    this.bitmap$0 = true;
                }
            }
            return this.scala$collection$SeqView$Sorted$$_sorted;
        }

        public Seq<A> scala$collection$SeqView$Sorted$$_sorted() {
            if (!this.bitmap$0) {
                return this._sorted$lzycompute();
            }
            return this.scala$collection$SeqView$Sorted$$_sorted;
        }

        public SeqOps<A, ?, Object> scala$collection$SeqView$Sorted$$elems() {
            SeqOps<A, ?, Object> orig = this.underlying;
            if (this.evaluated) {
                return this.scala$collection$SeqView$Sorted$$_sorted();
            }
            return orig;
        }

        @Override
        public A apply(int i) {
            return (A)this.scala$collection$SeqView$Sorted$$_sorted().apply(i);
        }

        @Override
        public int length() {
            return this.scala$collection$SeqView$Sorted$$len;
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public Iterator<A> iterator() {
            void $plus$plus_xs;
            Iterator<Nothing$> iterator2 = Iterator$.scala$collection$Iterator$$_empty;
            Function0<Iterator> & Serializable intersect = (Function0<Iterator> & Serializable)() -> this.scala$collection$SeqView$Sorted$$_sorted().iterator();
            if (iterator2 == null) {
                throw null;
            }
            return iterator2.concat($plus$plus_xs);
        }

        @Override
        public int knownSize() {
            return this.scala$collection$SeqView$Sorted$$len;
        }

        @Override
        public boolean isEmpty() {
            return this.scala$collection$SeqView$Sorted$$len == 0;
        }

        @Override
        public <C1> C1 to(Factory<A, C1> factory2) {
            return this.scala$collection$SeqView$Sorted$$_sorted().to(factory2);
        }

        @Override
        public SeqView<A> reverse() {
            return new ReverseSorted();
        }

        @Override
        public Iterable<A> reversed() {
            return new ReverseSorted();
        }

        @Override
        public <B1> SeqView<A> sorted(Ordering<B1> ord1) {
            Ordering<B1> ordering = ord1;
            Ordering<B> ordering2 = this.scala$collection$SeqView$Sorted$$ord;
            if (!(ordering != null ? !ordering.equals(ordering2) : ordering2 != null)) {
                return this;
            }
            if (ord1.isReverseOf(this.scala$collection$SeqView$Sorted$$ord)) {
                return this.reverse();
            }
            return new Sorted<A, B1>(this.scala$collection$SeqView$Sorted$$elems(), this.scala$collection$SeqView$Sorted$$len, ord1);
        }

        public Sorted(SeqOps<A, ?, Object> underlying, int len, Ordering<B> ord) {
            this.underlying = underlying;
            this.scala$collection$SeqView$Sorted$$len = len;
            this.scala$collection$SeqView$Sorted$$ord = ord;
            this.evaluated = false;
        }

        public Sorted(SeqOps<A, ?, Object> underlying, Ordering<B> ord) {
            this(underlying, underlying.length(), ord);
        }

        private class ReverseSorted
        implements SeqView<A> {
            private static final long serialVersionUID = 3L;
            private Reverse<A> _reversed;
            private volatile boolean bitmap$0;

            @Override
            public SeqView<A> view() {
                return ((SeqView)this).view();
            }

            @Override
            public <B$> SeqView<B$> map(Function1<A, B$> f) {
                return ((SeqView)this).map(f);
            }

            @Override
            public <B> SeqView<B> appended(B elem) {
                return ((SeqView)this).appended(elem);
            }

            @Override
            public <B> SeqView<B> prepended(B elem) {
                return ((SeqView)this).prepended(elem);
            }

            @Override
            public SeqView<A> take(int n) {
                return ((SeqView)this).take(n);
            }

            @Override
            public SeqView<A> drop(int n) {
                return ((SeqView)this).drop(n);
            }

            @Override
            public SeqView<A> takeRight(int n) {
                return ((SeqView)this).takeRight(n);
            }

            @Override
            public SeqView<A> dropRight(int n) {
                return ((SeqView)this).dropRight(n);
            }

            @Override
            public <U> SeqView<A> tapEach(Function1<A, U> f) {
                return ((SeqView)this).tapEach(f);
            }

            @Override
            public <B> SeqView<B> concat(SeqOps<B, ?, Object> suffix) {
                return ((SeqView)this).concat(suffix);
            }

            @Override
            public <B> SeqView<B> appendedAll(SeqOps<B, ?, Object> suffix) {
                return ((SeqView)this).appendedAll(suffix);
            }

            @Override
            public <B> SeqView<B> prependedAll(SeqOps<B, ?, Object> prefix) {
                return ((SeqView)this).prependedAll(prefix);
            }

            @Override
            public String stringPrefix() {
                return ((SeqView)this).stringPrefix();
            }

            @Override
            public IterableFactory<View> iterableFactory() {
                return View.iterableFactory$(this);
            }

            @Override
            public View<A> empty() {
                return View.empty$(this);
            }

            @Override
            public String toString() {
                return View.toString$(this);
            }

            @Override
            public IndexedSeq<A> force() {
                return View.force$(this);
            }

            @Override
            public final Iterable<A> toIterable() {
                return Iterable.toIterable$(this);
            }

            @Override
            public final Iterable<A> coll() {
                return Iterable.coll$(this);
            }

            @Override
            public Iterable<A> seq() {
                return Iterable.seq$(this);
            }

            @Override
            public String className() {
                return Iterable.className$(this);
            }

            @Override
            public final String collectionClassName() {
                return Iterable.collectionClassName$(this);
            }

            @Override
            public <B> LazyZip2<A, B, Iterable> lazyZip(Iterable<B> that) {
                return Iterable.lazyZip$(this, that);
            }

            @Override
            public IterableOps fromSpecific(IterableOnce coll) {
                return IterableFactoryDefaults.fromSpecific$(this, coll);
            }

            @Override
            public Builder<A, View<A>> newSpecificBuilder() {
                return IterableFactoryDefaults.newSpecificBuilder$(this);
            }

            @Override
            public /* synthetic */ Object scala$collection$SeqOps$$super$concat(IterableOnce suffix) {
                return IterableOps.concat$(this, suffix);
            }

            @Override
            public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(int otherSize) {
                return IterableOps.sizeCompare$((IterableOps)this, otherSize);
            }

            @Override
            public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(Iterable that) {
                return IterableOps.sizeCompare$((IterableOps)this, that);
            }

            @Override
            public final Object $plus$colon(Object elem) {
                return SeqOps.$plus$colon$(this, elem);
            }

            @Override
            public final Object $colon$plus(Object elem) {
                return SeqOps.$colon$plus$(this, elem);
            }

            @Override
            public Object prependedAll(IterableOnce prefix) {
                return SeqOps.prependedAll$(this, prefix);
            }

            @Override
            public final Object $plus$plus$colon(IterableOnce prefix) {
                return SeqOps.$plus$plus$colon$(this, prefix);
            }

            @Override
            public Object appendedAll(IterableOnce suffix) {
                return SeqOps.appendedAll$(this, suffix);
            }

            @Override
            public final Object $colon$plus$plus(IterableOnce suffix) {
                return SeqOps.$colon$plus$plus$(this, suffix);
            }

            @Override
            public final Object concat(IterableOnce suffix) {
                return SeqOps.concat$(this, suffix);
            }

            @Override
            public final Object union(Seq that) {
                return SeqOps.union$(this, that);
            }

            @Override
            public final int size() {
                return SeqOps.size$(this);
            }

            @Override
            public Object distinct() {
                return SeqOps.distinct$(this);
            }

            @Override
            public Object distinctBy(Function1 f) {
                return SeqOps.distinctBy$(this, f);
            }

            @Override
            public Iterator<A> reverseIterator() {
                return SeqOps.reverseIterator$(this);
            }

            @Override
            public <B> boolean startsWith(IterableOnce<B> that, int offset) {
                return SeqOps.startsWith$(this, that, offset);
            }

            @Override
            public <B> int startsWith$default$2() {
                return SeqOps.startsWith$default$2$(this);
            }

            @Override
            public <B> boolean endsWith(Iterable<B> that) {
                return SeqOps.endsWith$(this, that);
            }

            @Override
            public boolean isDefinedAt(int idx) {
                return SeqOps.isDefinedAt$(this, idx);
            }

            @Override
            public Object padTo(int len, Object elem) {
                return SeqOps.padTo$(this, len, elem);
            }

            @Override
            public final int segmentLength(Function1<A, Object> p) {
                return SeqOps.segmentLength$(this, p);
            }

            @Override
            public int segmentLength(Function1<A, Object> p, int from) {
                return SeqOps.segmentLength$(this, p, from);
            }

            @Override
            public final int prefixLength(Function1<A, Object> p) {
                return SeqOps.prefixLength$(this, p);
            }

            @Override
            public int indexWhere(Function1<A, Object> p, int from) {
                return SeqOps.indexWhere$(this, p, from);
            }

            @Override
            public int indexWhere(Function1<A, Object> p) {
                return SeqOps.indexWhere$(this, p);
            }

            @Override
            public <B> int indexOf(B elem, int from) {
                return SeqOps.indexOf$(this, elem, from);
            }

            @Override
            public <B> int indexOf(B elem) {
                return SeqOps.indexOf$(this, elem);
            }

            @Override
            public <B> int lastIndexOf(B elem, int end) {
                return SeqOps.lastIndexOf$(this, elem, end);
            }

            @Override
            public <B> int lastIndexOf$default$2() {
                return SeqOps.lastIndexOf$default$2$(this);
            }

            @Override
            public int lastIndexWhere(Function1<A, Object> p, int end) {
                return SeqOps.lastIndexWhere$(this, p, end);
            }

            @Override
            public int lastIndexWhere(Function1<A, Object> p) {
                return SeqOps.lastIndexWhere$(this, p);
            }

            @Override
            public <B> int indexOfSlice(Seq<B> that, int from) {
                return SeqOps.indexOfSlice$(this, that, from);
            }

            @Override
            public <B> int indexOfSlice(Seq<B> that) {
                return SeqOps.indexOfSlice$(this, that);
            }

            @Override
            public <B> int lastIndexOfSlice(Seq<B> that, int end) {
                return SeqOps.lastIndexOfSlice$(this, that, end);
            }

            @Override
            public <B> int lastIndexOfSlice(Seq<B> that) {
                return SeqOps.lastIndexOfSlice$(this, that);
            }

            @Override
            public Option<A> findLast(Function1<A, Object> p) {
                return SeqOps.findLast$(this, p);
            }

            @Override
            public <B> boolean containsSlice(Seq<B> that) {
                return SeqOps.containsSlice$(this, that);
            }

            @Override
            public <A1> boolean contains(A1 elem) {
                return SeqOps.contains$(this, elem);
            }

            @Override
            public Object reverseMap(Function1 f) {
                return SeqOps.reverseMap$(this, f);
            }

            @Override
            public Iterator<View<A>> permutations() {
                return SeqOps.permutations$(this);
            }

            @Override
            public Iterator<View<A>> combinations(int n) {
                return SeqOps.combinations$(this, n);
            }

            @Override
            public Object sortWith(Function2 lt) {
                return SeqOps.sortWith$(this, lt);
            }

            @Override
            public Object sortBy(Function1 f, Ordering ord) {
                return SeqOps.sortBy$(this, f, ord);
            }

            @Override
            public Range indices() {
                return SeqOps.indices$(this);
            }

            @Override
            public final int sizeCompare(int otherSize) {
                return SeqOps.sizeCompare$((SeqOps)this, otherSize);
            }

            @Override
            public int lengthCompare(int len) {
                return SeqOps.lengthCompare$((SeqOps)this, len);
            }

            @Override
            public final int sizeCompare(Iterable<?> that) {
                return SeqOps.sizeCompare$((SeqOps)this, that);
            }

            @Override
            public int lengthCompare(Iterable<?> that) {
                return SeqOps.lengthCompare$((SeqOps)this, that);
            }

            @Override
            public final IterableOps lengthIs() {
                return SeqOps.lengthIs$(this);
            }

            @Override
            public <B> boolean sameElements(IterableOnce<B> that) {
                return SeqOps.sameElements$(this, that);
            }

            @Override
            public <B$> boolean corresponds(Seq<B$> that, Function2<A, B$, Object> p) {
                return SeqOps.corresponds$(this, that, p);
            }

            @Override
            public Object diff(Seq that) {
                return SeqOps.diff$(this, that);
            }

            @Override
            public Object intersect(Seq that) {
                return SeqOps.intersect$(this, that);
            }

            @Override
            public Object patch(int from, IterableOnce other, int replaced) {
                return SeqOps.patch$(this, from, other, replaced);
            }

            @Override
            public Object updated(int index, Object elem) {
                return SeqOps.updated$(this, index, elem);
            }

            @Override
            public <B> scala.collection.mutable.Map<B, Object> occCounts(Seq<B> sq) {
                return SeqOps.occCounts$(this, sq);
            }

            @Override
            public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
                return SeqOps.search$(this, elem, ord);
            }

            @Override
            public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
                return SeqOps.search$(this, elem, from, to, ord);
            }

            @Override
            public final Iterable<A> toTraversable() {
                return IterableOps.toTraversable$(this);
            }

            @Override
            public boolean isTraversableAgain() {
                return IterableOps.isTraversableAgain$(this);
            }

            @Override
            public final Object repr() {
                return IterableOps.repr$(this);
            }

            @Override
            public IterableFactory<?> companion() {
                return IterableOps.companion$(this);
            }

            @Override
            public A head() {
                return IterableOps.head$(this);
            }

            @Override
            public Option<A> headOption() {
                return IterableOps.headOption$(this);
            }

            @Override
            public A last() {
                return IterableOps.last$(this);
            }

            @Override
            public Option<A> lastOption() {
                return IterableOps.lastOption$(this);
            }

            @Override
            public final IterableOps sizeIs() {
                return IterableOps.sizeIs$(this);
            }

            @Override
            public View<A> view(int from, int until) {
                return IterableOps.view$(this, from, until);
            }

            @Override
            public Object transpose(Function1 asIterable) {
                return IterableOps.transpose$(this, asIterable);
            }

            @Override
            public Object filter(Function1 pred) {
                return IterableOps.filter$(this, pred);
            }

            @Override
            public Object filterNot(Function1 pred) {
                return IterableOps.filterNot$(this, pred);
            }

            @Override
            public WithFilter<A, ?> withFilter(Function1<A, Object> p) {
                return IterableOps.withFilter$(this, p);
            }

            @Override
            public Tuple2<View<A>, View<A>> partition(Function1<A, Object> p) {
                return IterableOps.partition$(this, p);
            }

            @Override
            public Tuple2<View<A>, View<A>> splitAt(int n) {
                return IterableOps.splitAt$(this, n);
            }

            @Override
            public Object takeWhile(Function1 p) {
                return IterableOps.takeWhile$(this, p);
            }

            @Override
            public Tuple2<View<A>, View<A>> span(Function1<A, Object> p) {
                return IterableOps.span$(this, p);
            }

            @Override
            public Object dropWhile(Function1 p) {
                return IterableOps.dropWhile$(this, p);
            }

            @Override
            public Iterator<View<A>> grouped(int size) {
                return IterableOps.grouped$(this, size);
            }

            @Override
            public Iterator<View<A>> sliding(int size) {
                return IterableOps.sliding$(this, size);
            }

            @Override
            public Iterator<View<A>> sliding(int size, int step) {
                return IterableOps.sliding$(this, size, step);
            }

            @Override
            public Object tail() {
                return IterableOps.tail$(this);
            }

            @Override
            public Object init() {
                return IterableOps.init$(this);
            }

            @Override
            public Object slice(int from, int until) {
                return IterableOps.slice$(this, from, until);
            }

            @Override
            public <K$> scala.collection.immutable.Map<K$, View<A>> groupBy(Function1<A, K$> f) {
                return IterableOps.groupBy$(this, f);
            }

            @Override
            public <K$, B> scala.collection.immutable.Map<K$, View<B>> groupMap(Function1<A, K$> key, Function1<A, B> f) {
                return IterableOps.groupMap$(this, key, f);
            }

            @Override
            public <K$, B> scala.collection.immutable.Map<K$, B> groupMapReduce(Function1<A, K$> key, Function1<A, B> f, Function2<B, B, B> reduce) {
                return IterableOps.groupMapReduce$(this, key, f, reduce);
            }

            @Override
            public Object scan(Object z, Function2 op) {
                return IterableOps.scan$(this, z, op);
            }

            @Override
            public Object scanLeft(Object z, Function2 op) {
                return IterableOps.scanLeft$(this, z, op);
            }

            @Override
            public Object scanRight(Object z, Function2 op) {
                return IterableOps.scanRight$(this, z, op);
            }

            @Override
            public Object flatMap(Function1 f) {
                return IterableOps.flatMap$(this, f);
            }

            @Override
            public Object flatten(Function1 asIterable) {
                return IterableOps.flatten$(this, asIterable);
            }

            @Override
            public Object collect(PartialFunction pf) {
                return IterableOps.collect$(this, pf);
            }

            @Override
            public <A1, A2> Tuple2<View<A1>, View<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
                return IterableOps.partitionMap$(this, f);
            }

            @Override
            public final Object $plus$plus(IterableOnce suffix) {
                return IterableOps.$plus$plus$(this, suffix);
            }

            @Override
            public Object zip(IterableOnce that) {
                return IterableOps.zip$(this, that);
            }

            @Override
            public Object zipWithIndex() {
                return IterableOps.zipWithIndex$(this);
            }

            @Override
            public Object zipAll(Iterable that, Object thisElem, Object thatElem) {
                return IterableOps.zipAll$(this, that, thisElem, thatElem);
            }

            @Override
            public <A1, A2> Tuple2<View<A1>, View<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
                return IterableOps.unzip$(this, asPair);
            }

            @Override
            public <A1, A2, A3> Tuple3<View<A1>, View<A2>, View<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
                return IterableOps.unzip3$(this, asTriple);
            }

            @Override
            public Iterator<View<A>> tails() {
                return IterableOps.tails$(this);
            }

            @Override
            public Iterator<View<A>> inits() {
                return IterableOps.inits$(this);
            }

            @Override
            public boolean hasDefiniteSize() {
                return IterableOnceOps.hasDefiniteSize$(this);
            }

            @Override
            public <U> void foreach(Function1<A, U> f) {
                IterableOnceOps.foreach$(this, f);
            }

            @Override
            public boolean forall(Function1<A, Object> p) {
                return IterableOnceOps.forall$(this, p);
            }

            @Override
            public boolean exists(Function1<A, Object> p) {
                return IterableOnceOps.exists$(this, p);
            }

            @Override
            public int count(Function1<A, Object> p) {
                return IterableOnceOps.count$(this, p);
            }

            @Override
            public Option<A> find(Function1<A, Object> p) {
                return IterableOnceOps.find$(this, p);
            }

            @Override
            public <B> B foldLeft(B z, Function2<B, A, B> op) {
                return (B)IterableOnceOps.foldLeft$(this, z, op);
            }

            @Override
            public <B> B foldRight(B z, Function2<A, B, B> op) {
                return (B)IterableOnceOps.foldRight$(this, z, op);
            }

            @Override
            public final <B> B $div$colon(B z, Function2<B, A, B> op) {
                return (B)IterableOnceOps.$div$colon$(this, z, op);
            }

            @Override
            public final <B> B $colon$bslash(B z, Function2<A, B, B> op) {
                return (B)IterableOnceOps.$colon$bslash$(this, z, op);
            }

            @Override
            public <A1> A1 fold(A1 z, Function2<A1, A1, A1> op) {
                return (A1)IterableOnceOps.fold$(this, z, op);
            }

            @Override
            public <B> B reduce(Function2<B, B, B> op) {
                return (B)IterableOnceOps.reduce$(this, op);
            }

            @Override
            public <B> Option<B> reduceOption(Function2<B, B, B> op) {
                return IterableOnceOps.reduceOption$(this, op);
            }

            @Override
            public <B> B reduceLeft(Function2<B, A, B> op) {
                return (B)IterableOnceOps.reduceLeft$(this, op);
            }

            @Override
            public <B> B reduceRight(Function2<A, B, B> op) {
                return (B)IterableOnceOps.reduceRight$(this, op);
            }

            @Override
            public <B> Option<B> reduceLeftOption(Function2<B, A, B> op) {
                return IterableOnceOps.reduceLeftOption$(this, op);
            }

            @Override
            public <B> Option<B> reduceRightOption(Function2<A, B, B> op) {
                return IterableOnceOps.reduceRightOption$(this, op);
            }

            @Override
            public boolean nonEmpty() {
                return IterableOnceOps.nonEmpty$(this);
            }

            @Override
            public final <B> void copyToBuffer(Buffer<B> dest) {
                IterableOnceOps.copyToBuffer$(this, dest);
            }

            @Override
            public <B> int copyToArray(Object xs) {
                return IterableOnceOps.copyToArray$(this, xs);
            }

            @Override
            public <B> int copyToArray(Object xs, int start2) {
                return IterableOnceOps.copyToArray$(this, xs, start2);
            }

            @Override
            public <B> int copyToArray(Object xs, int start2, int len) {
                return IterableOnceOps.copyToArray$(this, xs, start2, len);
            }

            @Override
            public <B> B sum(Numeric<B> num) {
                return (B)IterableOnceOps.sum$(this, num);
            }

            @Override
            public <B> B product(Numeric<B> num) {
                return (B)IterableOnceOps.product$(this, num);
            }

            @Override
            public <B> A min(Ordering<B> ord) {
                return IterableOnceOps.min$(this, ord);
            }

            @Override
            public <B> Option<A> minOption(Ordering<B> ord) {
                return IterableOnceOps.minOption$(this, ord);
            }

            @Override
            public <B> A max(Ordering<B> ord) {
                return IterableOnceOps.max$(this, ord);
            }

            @Override
            public <B> Option<A> maxOption(Ordering<B> ord) {
                return IterableOnceOps.maxOption$(this, ord);
            }

            @Override
            public <B> A maxBy(Function1<A, B> f, Ordering<B> cmp) {
                return IterableOnceOps.maxBy$(this, f, cmp);
            }

            @Override
            public <B> Option<A> maxByOption(Function1<A, B> f, Ordering<B> cmp) {
                return IterableOnceOps.maxByOption$(this, f, cmp);
            }

            @Override
            public <B> A minBy(Function1<A, B> f, Ordering<B> cmp) {
                return IterableOnceOps.minBy$(this, f, cmp);
            }

            @Override
            public <B> Option<A> minByOption(Function1<A, B> f, Ordering<B> cmp) {
                return IterableOnceOps.minByOption$(this, f, cmp);
            }

            @Override
            public <B> Option<B> collectFirst(PartialFunction<A, B> pf) {
                return IterableOnceOps.collectFirst$(this, pf);
            }

            @Override
            public <B> B aggregate(Function0<B> z, Function2<B, A, B> seqop, Function2<B, B, B> combop) {
                return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
            }

            @Override
            public <B> boolean corresponds(IterableOnce<B> that, Function2<A, B, Object> p) {
                return IterableOnceOps.corresponds$(this, that, p);
            }

            @Override
            public final String mkString(String start2, String sep, String end) {
                return IterableOnceOps.mkString$(this, start2, sep, end);
            }

            @Override
            public final String mkString(String sep) {
                return IterableOnceOps.mkString$(this, sep);
            }

            @Override
            public final String mkString() {
                return IterableOnceOps.mkString$(this);
            }

            @Override
            public StringBuilder addString(StringBuilder b, String start2, String sep, String end) {
                return IterableOnceOps.addString$(this, b, start2, sep, end);
            }

            @Override
            public final StringBuilder addString(StringBuilder b, String sep) {
                return IterableOnceOps.addString$(this, b, sep);
            }

            @Override
            public final StringBuilder addString(StringBuilder b) {
                return IterableOnceOps.addString$(this, b);
            }

            @Override
            public final Iterator<A> toIterator() {
                return IterableOnceOps.toIterator$(this);
            }

            @Override
            public List<A> toList() {
                return IterableOnceOps.toList$(this);
            }

            @Override
            public Vector<A> toVector() {
                return IterableOnceOps.toVector$(this);
            }

            @Override
            public <K$, V$> scala.collection.immutable.Map<K$, V$> toMap($less$colon$less<A, Tuple2<K$, V$>> ev) {
                return IterableOnceOps.toMap$(this, ev);
            }

            @Override
            public <B> Set<B> toSet() {
                return IterableOnceOps.toSet$(this);
            }

            @Override
            public scala.collection.immutable.Seq<A> toSeq() {
                return IterableOnceOps.toSeq$(this);
            }

            @Override
            public scala.collection.immutable.IndexedSeq<A> toIndexedSeq() {
                return IterableOnceOps.toIndexedSeq$(this);
            }

            @Override
            public final Stream<A> toStream() {
                return IterableOnceOps.toStream$(this);
            }

            @Override
            public final <B> Buffer<B> toBuffer() {
                return IterableOnceOps.toBuffer$(this);
            }

            @Override
            public <B> Object toArray(ClassTag<B> evidence$2) {
                return IterableOnceOps.toArray$(this, evidence$2);
            }

            @Override
            public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
                return (S)IterableOnce.stepper$(this, shape);
            }

            private Reverse<A> _reversed$lzycompute() {
                synchronized (this) {
                    if (!this.bitmap$0) {
                        this._reversed = new Reverse(this.scala$collection$SeqView$Sorted$ReverseSorted$$$outer().scala$collection$SeqView$Sorted$$_sorted());
                        this.bitmap$0 = true;
                    }
                }
                return this._reversed;
            }

            private Reverse<A> _reversed() {
                if (!this.bitmap$0) {
                    return this._reversed$lzycompute();
                }
                return this._reversed;
            }

            @Override
            public A apply(int i) {
                return this._reversed().apply(i);
            }

            @Override
            public int length() {
                return this.scala$collection$SeqView$Sorted$ReverseSorted$$$outer().scala$collection$SeqView$Sorted$$len;
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public Iterator<A> iterator() {
                void $plus$plus_xs;
                Iterator<Nothing$> iterator2 = Iterator$.scala$collection$Iterator$$_empty;
                Function0<Iterator> & Serializable intersect = (Function0<Iterator> & Serializable)() -> this._reversed().iterator();
                if (iterator2 == null) {
                    throw null;
                }
                return iterator2.concat($plus$plus_xs);
            }

            @Override
            public int knownSize() {
                return this.scala$collection$SeqView$Sorted$ReverseSorted$$$outer().scala$collection$SeqView$Sorted$$len;
            }

            @Override
            public boolean isEmpty() {
                return this.scala$collection$SeqView$Sorted$ReverseSorted$$$outer().scala$collection$SeqView$Sorted$$len == 0;
            }

            @Override
            public <C1> C1 to(Factory<A, C1> factory2) {
                return this._reversed().to(factory2);
            }

            @Override
            public SeqView<A> reverse() {
                return this.scala$collection$SeqView$Sorted$ReverseSorted$$$outer();
            }

            @Override
            public Iterable<A> reversed() {
                return this.scala$collection$SeqView$Sorted$ReverseSorted$$$outer();
            }

            @Override
            public <B1> SeqView<A> sorted(Ordering<B1> ord1) {
                Ordering<B1> ordering = ord1;
                Ordering ordering2 = this.scala$collection$SeqView$Sorted$ReverseSorted$$$outer().scala$collection$SeqView$Sorted$$ord;
                if (!(ordering != null ? !ordering.equals(ordering2) : ordering2 != null)) {
                    return this.scala$collection$SeqView$Sorted$ReverseSorted$$$outer();
                }
                if (ord1.isReverseOf(this.scala$collection$SeqView$Sorted$ReverseSorted$$$outer().scala$collection$SeqView$Sorted$$ord)) {
                    return this;
                }
                return new Sorted(this.scala$collection$SeqView$Sorted$ReverseSorted$$$outer().scala$collection$SeqView$Sorted$$elems(), this.scala$collection$SeqView$Sorted$ReverseSorted$$$outer().scala$collection$SeqView$Sorted$$len, ord1);
            }

            public /* synthetic */ Sorted scala$collection$SeqView$Sorted$ReverseSorted$$$outer() {
                return Sorted.this;
            }

            public ReverseSorted() {
                if (Sorted.this == null) {
                    throw null;
                }
            }
        }
    }

    public static class Take<A>
    extends View.Take<A>
    implements SeqView<A> {
        private static final long serialVersionUID = 3L;
        private final SeqOps<A, ?, Object> underlying;
        private final int n;

        @Override
        public SeqView<A> view() {
            return ((SeqView)this).view();
        }

        @Override
        public <B$> SeqView<B$> map(Function1<A, B$> f) {
            return ((SeqView)this).map(f);
        }

        @Override
        public <B> SeqView<B> appended(B elem) {
            return ((SeqView)this).appended(elem);
        }

        @Override
        public <B> SeqView<B> prepended(B elem) {
            return ((SeqView)this).prepended(elem);
        }

        @Override
        public SeqView<A> reverse() {
            return ((SeqView)this).reverse();
        }

        @Override
        public SeqView<A> take(int n) {
            return ((SeqView)this).take(n);
        }

        @Override
        public SeqView<A> drop(int n) {
            return ((SeqView)this).drop(n);
        }

        @Override
        public SeqView<A> takeRight(int n) {
            return ((SeqView)this).takeRight(n);
        }

        @Override
        public SeqView<A> dropRight(int n) {
            return ((SeqView)this).dropRight(n);
        }

        @Override
        public <U> SeqView<A> tapEach(Function1<A, U> f) {
            return ((SeqView)this).tapEach(f);
        }

        @Override
        public <B> SeqView<B> concat(SeqOps<B, ?, Object> suffix) {
            return ((SeqView)this).concat(suffix);
        }

        @Override
        public <B> SeqView<B> appendedAll(SeqOps<B, ?, Object> suffix) {
            return ((SeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> SeqView<B> prependedAll(SeqOps<B, ?, Object> prefix) {
            return ((SeqView)this).prependedAll(prefix);
        }

        @Override
        public <B> SeqView<A> sorted(Ordering<B> ord) {
            return ((SeqView)this).sorted(ord);
        }

        @Override
        public String stringPrefix() {
            return ((SeqView)this).stringPrefix();
        }

        @Override
        public /* synthetic */ Object scala$collection$SeqOps$$super$concat(IterableOnce suffix) {
            return IterableOps.concat$(this, suffix);
        }

        @Override
        public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(int otherSize) {
            return IterableOps.sizeCompare$((IterableOps)this, otherSize);
        }

        @Override
        public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(Iterable that) {
            return IterableOps.sizeCompare$((IterableOps)this, that);
        }

        @Override
        public final Object $plus$colon(Object elem) {
            return SeqOps.$plus$colon$(this, elem);
        }

        @Override
        public final Object $colon$plus(Object elem) {
            return SeqOps.$colon$plus$(this, elem);
        }

        @Override
        public Object prependedAll(IterableOnce prefix) {
            return SeqOps.prependedAll$(this, prefix);
        }

        @Override
        public final Object $plus$plus$colon(IterableOnce prefix) {
            return SeqOps.$plus$plus$colon$(this, prefix);
        }

        @Override
        public Object appendedAll(IterableOnce suffix) {
            return SeqOps.appendedAll$(this, suffix);
        }

        @Override
        public final Object $colon$plus$plus(IterableOnce suffix) {
            return SeqOps.$colon$plus$plus$(this, suffix);
        }

        @Override
        public final Object concat(IterableOnce suffix) {
            return SeqOps.concat$(this, suffix);
        }

        @Override
        public final Object union(Seq that) {
            return SeqOps.union$(this, that);
        }

        @Override
        public final int size() {
            return SeqOps.size$(this);
        }

        @Override
        public Object distinct() {
            return SeqOps.distinct$(this);
        }

        @Override
        public Object distinctBy(Function1 f) {
            return SeqOps.distinctBy$(this, f);
        }

        @Override
        public Iterator<A> reverseIterator() {
            return SeqOps.reverseIterator$(this);
        }

        @Override
        public <B> boolean startsWith(IterableOnce<B> that, int offset) {
            return SeqOps.startsWith$(this, that, offset);
        }

        @Override
        public <B> int startsWith$default$2() {
            return SeqOps.startsWith$default$2$(this);
        }

        @Override
        public <B> boolean endsWith(Iterable<B> that) {
            return SeqOps.endsWith$(this, that);
        }

        @Override
        public boolean isDefinedAt(int idx) {
            return SeqOps.isDefinedAt$(this, idx);
        }

        @Override
        public Object padTo(int len, Object elem) {
            return SeqOps.padTo$(this, len, elem);
        }

        @Override
        public final int segmentLength(Function1<A, Object> p) {
            return SeqOps.segmentLength$(this, p);
        }

        @Override
        public int segmentLength(Function1<A, Object> p, int from) {
            return SeqOps.segmentLength$(this, p, from);
        }

        @Override
        public final int prefixLength(Function1<A, Object> p) {
            return SeqOps.prefixLength$(this, p);
        }

        @Override
        public int indexWhere(Function1<A, Object> p, int from) {
            return SeqOps.indexWhere$(this, p, from);
        }

        @Override
        public int indexWhere(Function1<A, Object> p) {
            return SeqOps.indexWhere$(this, p);
        }

        @Override
        public <B> int indexOf(B elem, int from) {
            return SeqOps.indexOf$(this, elem, from);
        }

        @Override
        public <B> int indexOf(B elem) {
            return SeqOps.indexOf$(this, elem);
        }

        @Override
        public <B> int lastIndexOf(B elem, int end) {
            return SeqOps.lastIndexOf$(this, elem, end);
        }

        @Override
        public <B> int lastIndexOf$default$2() {
            return SeqOps.lastIndexOf$default$2$(this);
        }

        @Override
        public int lastIndexWhere(Function1<A, Object> p, int end) {
            return SeqOps.lastIndexWhere$(this, p, end);
        }

        @Override
        public int lastIndexWhere(Function1<A, Object> p) {
            return SeqOps.lastIndexWhere$(this, p);
        }

        @Override
        public <B> int indexOfSlice(Seq<B> that, int from) {
            return SeqOps.indexOfSlice$(this, that, from);
        }

        @Override
        public <B> int indexOfSlice(Seq<B> that) {
            return SeqOps.indexOfSlice$(this, that);
        }

        @Override
        public <B> int lastIndexOfSlice(Seq<B> that, int end) {
            return SeqOps.lastIndexOfSlice$(this, that, end);
        }

        @Override
        public <B> int lastIndexOfSlice(Seq<B> that) {
            return SeqOps.lastIndexOfSlice$(this, that);
        }

        @Override
        public Option<A> findLast(Function1<A, Object> p) {
            return SeqOps.findLast$(this, p);
        }

        @Override
        public <B> boolean containsSlice(Seq<B> that) {
            return SeqOps.containsSlice$(this, that);
        }

        @Override
        public <A1> boolean contains(A1 elem) {
            return SeqOps.contains$(this, elem);
        }

        @Override
        public Object reverseMap(Function1 f) {
            return SeqOps.reverseMap$(this, f);
        }

        @Override
        public Iterator<View<A>> permutations() {
            return SeqOps.permutations$(this);
        }

        @Override
        public Iterator<View<A>> combinations(int n) {
            return SeqOps.combinations$(this, n);
        }

        @Override
        public Object sortWith(Function2 lt) {
            return SeqOps.sortWith$(this, lt);
        }

        @Override
        public Object sortBy(Function1 f, Ordering ord) {
            return SeqOps.sortBy$(this, f, ord);
        }

        @Override
        public Range indices() {
            return SeqOps.indices$(this);
        }

        @Override
        public final int sizeCompare(int otherSize) {
            return SeqOps.sizeCompare$((SeqOps)this, otherSize);
        }

        @Override
        public int lengthCompare(int len) {
            return SeqOps.lengthCompare$((SeqOps)this, len);
        }

        @Override
        public final int sizeCompare(Iterable<?> that) {
            return SeqOps.sizeCompare$((SeqOps)this, that);
        }

        @Override
        public int lengthCompare(Iterable<?> that) {
            return SeqOps.lengthCompare$((SeqOps)this, that);
        }

        @Override
        public final IterableOps lengthIs() {
            return SeqOps.lengthIs$(this);
        }

        @Override
        public boolean isEmpty() {
            return SeqOps.isEmpty$(this);
        }

        @Override
        public <B> boolean sameElements(IterableOnce<B> that) {
            return SeqOps.sameElements$(this, that);
        }

        @Override
        public <B$> boolean corresponds(Seq<B$> that, Function2<A, B$, Object> p) {
            return SeqOps.corresponds$(this, that, p);
        }

        @Override
        public Object diff(Seq that) {
            return SeqOps.diff$(this, that);
        }

        @Override
        public Object intersect(Seq that) {
            return SeqOps.intersect$(this, that);
        }

        @Override
        public Object patch(int from, IterableOnce other, int replaced) {
            return SeqOps.patch$(this, from, other, replaced);
        }

        @Override
        public Object updated(int index, Object elem) {
            return SeqOps.updated$(this, index, elem);
        }

        @Override
        public <B> scala.collection.mutable.Map<B, Object> occCounts(Seq<B> sq) {
            return SeqOps.occCounts$(this, sq);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
            return SeqOps.search$(this, elem, ord);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
            return SeqOps.search$(this, elem, from, to, ord);
        }

        @Override
        public A apply(int idx) {
            if (idx < this.n) {
                return this.underlying.apply(idx);
            }
            throw new IndexOutOfBoundsException(new java.lang.StringBuilder(31).append(idx).append(" is out of bounds (min 0, max ").append(this.underlying.knownSize() >= 0 ? Integer.valueOf(this.knownSize() - 1) : "unknown").append(")").toString());
        }

        @Override
        public int length() {
            int n = this.underlying.length();
            int min$extension_that = this.normN();
            return Math.min(n, min$extension_that);
        }

        public Take(SeqOps<A, ?, Object> underlying, int n) {
            this.underlying = underlying;
            this.n = n;
            super(underlying, n);
        }
    }

    public static class TakeRight<A>
    extends View.TakeRight<A>
    implements SeqView<A> {
        private static final long serialVersionUID = 3L;
        private final SeqOps<A, ?, Object> underlying;
        private final int delta;

        @Override
        public SeqView<A> view() {
            return ((SeqView)this).view();
        }

        @Override
        public <B$> SeqView<B$> map(Function1<A, B$> f) {
            return ((SeqView)this).map(f);
        }

        @Override
        public <B> SeqView<B> appended(B elem) {
            return ((SeqView)this).appended(elem);
        }

        @Override
        public <B> SeqView<B> prepended(B elem) {
            return ((SeqView)this).prepended(elem);
        }

        @Override
        public SeqView<A> reverse() {
            return ((SeqView)this).reverse();
        }

        @Override
        public SeqView<A> take(int n) {
            return ((SeqView)this).take(n);
        }

        @Override
        public SeqView<A> drop(int n) {
            return ((SeqView)this).drop(n);
        }

        @Override
        public SeqView<A> takeRight(int n) {
            return ((SeqView)this).takeRight(n);
        }

        @Override
        public SeqView<A> dropRight(int n) {
            return ((SeqView)this).dropRight(n);
        }

        @Override
        public <U> SeqView<A> tapEach(Function1<A, U> f) {
            return ((SeqView)this).tapEach(f);
        }

        @Override
        public <B> SeqView<B> concat(SeqOps<B, ?, Object> suffix) {
            return ((SeqView)this).concat(suffix);
        }

        @Override
        public <B> SeqView<B> appendedAll(SeqOps<B, ?, Object> suffix) {
            return ((SeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> SeqView<B> prependedAll(SeqOps<B, ?, Object> prefix) {
            return ((SeqView)this).prependedAll(prefix);
        }

        @Override
        public <B> SeqView<A> sorted(Ordering<B> ord) {
            return ((SeqView)this).sorted(ord);
        }

        @Override
        public String stringPrefix() {
            return ((SeqView)this).stringPrefix();
        }

        @Override
        public /* synthetic */ Object scala$collection$SeqOps$$super$concat(IterableOnce suffix) {
            return IterableOps.concat$(this, suffix);
        }

        @Override
        public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(int otherSize) {
            return IterableOps.sizeCompare$((IterableOps)this, otherSize);
        }

        @Override
        public /* synthetic */ int scala$collection$SeqOps$$super$sizeCompare(Iterable that) {
            return IterableOps.sizeCompare$((IterableOps)this, that);
        }

        @Override
        public final Object $plus$colon(Object elem) {
            return SeqOps.$plus$colon$(this, elem);
        }

        @Override
        public final Object $colon$plus(Object elem) {
            return SeqOps.$colon$plus$(this, elem);
        }

        @Override
        public Object prependedAll(IterableOnce prefix) {
            return SeqOps.prependedAll$(this, prefix);
        }

        @Override
        public final Object $plus$plus$colon(IterableOnce prefix) {
            return SeqOps.$plus$plus$colon$(this, prefix);
        }

        @Override
        public Object appendedAll(IterableOnce suffix) {
            return SeqOps.appendedAll$(this, suffix);
        }

        @Override
        public final Object $colon$plus$plus(IterableOnce suffix) {
            return SeqOps.$colon$plus$plus$(this, suffix);
        }

        @Override
        public final Object concat(IterableOnce suffix) {
            return SeqOps.concat$(this, suffix);
        }

        @Override
        public final Object union(Seq that) {
            return SeqOps.union$(this, that);
        }

        @Override
        public final int size() {
            return SeqOps.size$(this);
        }

        @Override
        public Object distinct() {
            return SeqOps.distinct$(this);
        }

        @Override
        public Object distinctBy(Function1 f) {
            return SeqOps.distinctBy$(this, f);
        }

        @Override
        public Iterator<A> reverseIterator() {
            return SeqOps.reverseIterator$(this);
        }

        @Override
        public <B> boolean startsWith(IterableOnce<B> that, int offset) {
            return SeqOps.startsWith$(this, that, offset);
        }

        @Override
        public <B> int startsWith$default$2() {
            return SeqOps.startsWith$default$2$(this);
        }

        @Override
        public <B> boolean endsWith(Iterable<B> that) {
            return SeqOps.endsWith$(this, that);
        }

        @Override
        public boolean isDefinedAt(int idx) {
            return SeqOps.isDefinedAt$(this, idx);
        }

        @Override
        public Object padTo(int len, Object elem) {
            return SeqOps.padTo$(this, len, elem);
        }

        @Override
        public final int segmentLength(Function1<A, Object> p) {
            return SeqOps.segmentLength$(this, p);
        }

        @Override
        public int segmentLength(Function1<A, Object> p, int from) {
            return SeqOps.segmentLength$(this, p, from);
        }

        @Override
        public final int prefixLength(Function1<A, Object> p) {
            return SeqOps.prefixLength$(this, p);
        }

        @Override
        public int indexWhere(Function1<A, Object> p, int from) {
            return SeqOps.indexWhere$(this, p, from);
        }

        @Override
        public int indexWhere(Function1<A, Object> p) {
            return SeqOps.indexWhere$(this, p);
        }

        @Override
        public <B> int indexOf(B elem, int from) {
            return SeqOps.indexOf$(this, elem, from);
        }

        @Override
        public <B> int indexOf(B elem) {
            return SeqOps.indexOf$(this, elem);
        }

        @Override
        public <B> int lastIndexOf(B elem, int end) {
            return SeqOps.lastIndexOf$(this, elem, end);
        }

        @Override
        public <B> int lastIndexOf$default$2() {
            return SeqOps.lastIndexOf$default$2$(this);
        }

        @Override
        public int lastIndexWhere(Function1<A, Object> p, int end) {
            return SeqOps.lastIndexWhere$(this, p, end);
        }

        @Override
        public int lastIndexWhere(Function1<A, Object> p) {
            return SeqOps.lastIndexWhere$(this, p);
        }

        @Override
        public <B> int indexOfSlice(Seq<B> that, int from) {
            return SeqOps.indexOfSlice$(this, that, from);
        }

        @Override
        public <B> int indexOfSlice(Seq<B> that) {
            return SeqOps.indexOfSlice$(this, that);
        }

        @Override
        public <B> int lastIndexOfSlice(Seq<B> that, int end) {
            return SeqOps.lastIndexOfSlice$(this, that, end);
        }

        @Override
        public <B> int lastIndexOfSlice(Seq<B> that) {
            return SeqOps.lastIndexOfSlice$(this, that);
        }

        @Override
        public Option<A> findLast(Function1<A, Object> p) {
            return SeqOps.findLast$(this, p);
        }

        @Override
        public <B> boolean containsSlice(Seq<B> that) {
            return SeqOps.containsSlice$(this, that);
        }

        @Override
        public <A1> boolean contains(A1 elem) {
            return SeqOps.contains$(this, elem);
        }

        @Override
        public Object reverseMap(Function1 f) {
            return SeqOps.reverseMap$(this, f);
        }

        @Override
        public Iterator<View<A>> permutations() {
            return SeqOps.permutations$(this);
        }

        @Override
        public Iterator<View<A>> combinations(int n) {
            return SeqOps.combinations$(this, n);
        }

        @Override
        public Object sortWith(Function2 lt) {
            return SeqOps.sortWith$(this, lt);
        }

        @Override
        public Object sortBy(Function1 f, Ordering ord) {
            return SeqOps.sortBy$(this, f, ord);
        }

        @Override
        public Range indices() {
            return SeqOps.indices$(this);
        }

        @Override
        public final int sizeCompare(int otherSize) {
            return SeqOps.sizeCompare$((SeqOps)this, otherSize);
        }

        @Override
        public int lengthCompare(int len) {
            return SeqOps.lengthCompare$((SeqOps)this, len);
        }

        @Override
        public final int sizeCompare(Iterable<?> that) {
            return SeqOps.sizeCompare$((SeqOps)this, that);
        }

        @Override
        public int lengthCompare(Iterable<?> that) {
            return SeqOps.lengthCompare$((SeqOps)this, that);
        }

        @Override
        public final IterableOps lengthIs() {
            return SeqOps.lengthIs$(this);
        }

        @Override
        public boolean isEmpty() {
            return SeqOps.isEmpty$(this);
        }

        @Override
        public <B> boolean sameElements(IterableOnce<B> that) {
            return SeqOps.sameElements$(this, that);
        }

        @Override
        public <B$> boolean corresponds(Seq<B$> that, Function2<A, B$, Object> p) {
            return SeqOps.corresponds$(this, that, p);
        }

        @Override
        public Object diff(Seq that) {
            return SeqOps.diff$(this, that);
        }

        @Override
        public Object intersect(Seq that) {
            return SeqOps.intersect$(this, that);
        }

        @Override
        public Object patch(int from, IterableOnce other, int replaced) {
            return SeqOps.patch$(this, from, other, replaced);
        }

        @Override
        public Object updated(int index, Object elem) {
            return SeqOps.updated$(this, index, elem);
        }

        @Override
        public <B> scala.collection.mutable.Map<B, Object> occCounts(Seq<B> sq) {
            return SeqOps.occCounts$(this, sq);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
            return SeqOps.search$(this, elem, ord);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
            return SeqOps.search$(this, elem, from, to, ord);
        }

        @Override
        public int length() {
            SeqOps<A, ?, Object> seqOps = this.underlying;
            if (seqOps == null) {
                throw null;
            }
            return seqOps.length() - this.delta;
        }

        @Override
        public A apply(int i) throws IndexOutOfBoundsException {
            return this.underlying.apply(i + this.delta);
        }

        public TakeRight(SeqOps<A, ?, Object> underlying, int n) {
            this.underlying = underlying;
            super(underlying, n);
            if (underlying == null) {
                throw null;
            }
            int max$extension_that = 0;
            int n2 = underlying.length() - Math.max(n, max$extension_that);
            int max$extension_that2 = 0;
            this.delta = Math.max(n2, max$extension_that2);
        }
    }
}

