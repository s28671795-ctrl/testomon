/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.ArrayOps;
import scala.collection.ArrayOps$;
import scala.collection.ArrayOps$ArrayIterator$mcB$sp;
import scala.collection.ArrayOps$ArrayIterator$mcC$sp;
import scala.collection.ArrayOps$ArrayIterator$mcD$sp;
import scala.collection.ArrayOps$ArrayIterator$mcF$sp;
import scala.collection.ArrayOps$ArrayIterator$mcI$sp;
import scala.collection.ArrayOps$ArrayIterator$mcJ$sp;
import scala.collection.ArrayOps$ArrayIterator$mcS$sp;
import scala.collection.ArrayOps$ArrayIterator$mcV$sp;
import scala.collection.ArrayOps$ArrayIterator$mcZ$sp;
import scala.collection.ClassTagIterableFactory;
import scala.collection.EvidenceIterableFactory;
import scala.collection.Factory;
import scala.collection.IndexedSeq;
import scala.collection.IndexedSeqOps;
import scala.collection.IndexedSeqView;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.Searching;
import scala.collection.Seq;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShape$;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StrictOptimizedSeqOps;
import scala.collection.View;
import scala.collection.convert.impl.BoxedBooleanArrayStepper;
import scala.collection.convert.impl.DoubleArrayStepper;
import scala.collection.convert.impl.IntArrayStepper;
import scala.collection.convert.impl.LongArrayStepper;
import scala.collection.convert.impl.ObjectArrayStepper;
import scala.collection.convert.impl.WidenedByteArrayStepper;
import scala.collection.convert.impl.WidenedCharArrayStepper;
import scala.collection.convert.impl.WidenedFloatArrayStepper;
import scala.collection.convert.impl.WidenedShortArrayStepper;
import scala.collection.mutable.AbstractSeq;
import scala.collection.mutable.ArrayBuilder;
import scala.collection.mutable.ArraySeq$;
import scala.collection.mutable.Builder;
import scala.collection.mutable.StringBuilder;
import scala.math.Integral;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;
import scala.reflect.ManifestFactory;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.ScalaRunTime$;
import scala.util.Either;
import scala.util.Sorting$;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\u0019Ud\u0001CA\u0015\u0003W\t\t#!\u000f\t\u000f\u00055\u0005\u0001\"\u0001\u0002\u0010\"9\u0011\u0011\u0013\u0001\u0005B\u0005M\u0005bBAN\u0001\u0011E\u0013Q\u0014\u0005\b\u0003S\u0003A\u0011KAV\u0011\u001d\t\u0019\f\u0001C!\u0003kCq!a.\u0001\r\u0003\tI\fC\u0004\u0002P\u00021\t!!5\t\u000f\u0005]\bA\"\u0001\u0002z\"9!\u0011\u0002\u0001\u0007B\t-\u0001\u0002\u0003B$\u0001\u0001&\tF!\u0013\t\u000f\tm\u0003\u0001\"\u0011\u0002\u0010\"9!Q\f\u0001\u0005B\t}\u0003b\u0002B=\u0001\u0011\u0005#1\u0010\u0005\b\u0005\u000f\u0003A\u0011\tBE\u0011\u001d\u0011Y\n\u0001C!\u0005;;\u0001B\"\u001d\u0002,!\u0005!\u0011\u0019\u0004\t\u0003S\tY\u0003#\u0001\u00032\"9\u0011QR\t\u0005\u0002\t}\u0006\"\u0003Bb#\t\u0007I\u0011AAJ\u0011!\u0011)-\u0005Q\u0001\n\u0005U\u0005\u0002\u0003Bd#\u0001\u0006IA!3\t\u000f\u0005M\u0016\u0003\"\u0001\u00048!91qI\t\u0005\u0002\r%\u0003bBB2#\u0011\u00051Q\r\u0005\b\u0007o\nB\u0011AB=\r\u0019\u0011i-\u0005\u0002\u0003P\"Q\u0011q\u001f\u000e\u0003\u0006\u0004%\tAa7\t\u0015\t}'D!A!\u0002\u0013\u0011i\u000eC\u0004\u0002\u000ej!\tA!9\t\u000f\u0005]&\u0004\"\u0001\u0003h\"9!1\u001e\u000e\u0005\u0002\t5\bb\u0002Bx5\u0011\u0005!\u0011\u001f\u0005\b\u0003\u001fTB\u0011\u0001B{\u0011\u001d\u0011YP\u0007C!\u0005{DqA!\u001f\u001b\t\u0003\u0012y\u0010C\u0004\u0004\u0006i!\tea\u0002\t\u000f\t%!\u0004\"\u0011\u0004\u0010\u001911\u0011R\t\u0003\u0007\u0017C!\"a>'\u0005\u000b\u0007I\u0011ABK\u0011)\u0011yN\nB\u0001B\u0003%1q\u0013\u0005\b\u0003\u001b3C\u0011ABM\u0011\u001d\t9L\nC\u0001\u0007?CqAa;'\t\u0003\u0011i\u000fC\u0004\u0003p\u001a\"\taa,\t\u000f\u0005=g\u0005\"\u0001\u00044\"9!1 \u0014\u0005B\tu\bb\u0002B=M\u0011\u00053\u0011\u0018\u0005\b\u0007\u000b1C\u0011IB_\u0011\u001d\u0011IA\nC!\u0007\u00034aaa8\u0012\u0005\r\u0005\bBCA|e\t\u0015\r\u0011\"\u0001\u0004l\"Q!q\u001c\u001a\u0003\u0002\u0003\u0006Ia!<\t\u000f\u00055%\u0007\"\u0001\u0004p\"9\u0011q\u0017\u001a\u0005\u0002\rU\bb\u0002Bve\u0011\u0005!Q\u001e\u0005\b\u0005_\u0014D\u0011AB\u007f\u0011\u001d\tyM\rC\u0001\t\u0003AqAa?3\t\u0003\u0012i\u0010C\u0004\u0003zI\"\t\u0005b\u0002\t\u000f\r\u0015!\u0007\"\u0011\u0005\f!9!\u0011\u0002\u001a\u0005B\u0011=aA\u0002C\u0017#\t!y\u0003\u0003\u0006\u0002xz\u0012)\u0019!C\u0001\tsA!Ba8?\u0005\u0003\u0005\u000b\u0011\u0002C\u001e\u0011\u001d\tiI\u0010C\u0001\t{Aq!a.?\t\u0003!\u0019\u0005C\u0004\u0003lz\"\tA!<\t\u000f\t=h\b\"\u0001\u0005L!9\u0011q\u001a \u0005\u0002\u0011=\u0003b\u0002B~}\u0011\u0005#Q \u0005\b\u0005srD\u0011\tC+\u0011\u001d\u0019)A\u0010C!\t3BqA!\u0003?\t\u0003\"i\u0006C\u0004\u0005zy\"\t\u0005b\u001f\u0007\r\u0011\r\u0016C\u0001CS\u0011)\t9p\u0013BC\u0002\u0013\u0005A\u0011\u0016\u0005\u000b\u0005?\\%\u0011!Q\u0001\n\u0011-\u0006bBAG\u0017\u0012\u0005AQ\u0016\u0005\b\u0003o[E\u0011\u0001CZ\u0011\u001d\u0011Yo\u0013C\u0001\u0005[DqAa<L\t\u0003!Y\fC\u0004\u0002P.#\t\u0001b0\t\u000f\tm8\n\"\u0011\u0003~\"9!\u0011P&\u0005B\u0011\u0015\u0007bBB\u0003\u0017\u0012\u0005C\u0011\u001a\u0005\b\u0005\u0013YE\u0011\tCg\r\u0019!Y/\u0005\u0002\u0005n\"Q\u0011q_,\u0003\u0006\u0004%\t\u0001b>\t\u0015\t}wK!A!\u0002\u0013!I\u0010C\u0004\u0002\u000e^#\t\u0001b?\t\u000f\u0005]v\u000b\"\u0001\u0006\u0002!9!1^,\u0005\u0002\t5\bb\u0002Bx/\u0012\u0005Q\u0011\u0002\u0005\b\u0003\u001f<F\u0011AC\u0007\u0011\u001d\u0011Yp\u0016C!\u0005{DqA!\u001fX\t\u0003*\u0019\u0002C\u0004\u0004\u0006]#\t%b\u0006\t\u000f\t%q\u000b\"\u0011\u0006\u001c\u00191Q\u0011H\t\u0003\u000bwA!\"a>d\u0005\u000b\u0007I\u0011AC#\u0011)\u0011yn\u0019B\u0001B\u0003%Qq\t\u0005\b\u0003\u001b\u001bG\u0011AC%\u0011\u001d\t9l\u0019C\u0001\u000b\u001fBqAa;d\t\u0003\u0011i\u000fC\u0004\u0003p\u000e$\t!b\u0016\t\u000f\u0005=7\r\"\u0001\u0006\\!9!1`2\u0005B\tu\bb\u0002B=G\u0012\u0005S\u0011\r\u0005\b\u0007\u000b\u0019G\u0011IC3\u0011\u001d\u0011Ia\u0019C!\u000bS2a!b\"\u0012\u0005\u0015%\u0005BCA|_\n\u0015\r\u0011\"\u0001\u0006\u0014\"Q!q\\8\u0003\u0002\u0003\u0006I!\"&\t\u000f\u00055u\u000e\"\u0001\u0006\u0018\"9\u0011qW8\u0005\u0002\u0015u\u0005b\u0002Bv_\u0012\u0005!Q\u001e\u0005\b\u0005_|G\u0011ACS\u0011\u001d\tym\u001cC\u0001\u000bSCqAa?p\t\u0003\u0012i\u0010C\u0004\u0003z=$\t%b,\t\u000f\r\u0015q\u000e\"\u0011\u00064\"9!\u0011B8\u0005B\u0015]fA\u0002BX#\t1I\u0003\u0003\u0006\u0002xn\u0014)\u0019!C\u0001\r[A!Ba8|\u0005\u0003\u0005\u000b\u0011\u0002D\u0018\u0011\u001d\tii\u001fC\u0001\rcAq!a.|\t\u000319\u0004C\u0004\u0003ln$\tA!<\t\u000f\t=8\u0010\"\u0001\u0007@!9\u0011qZ>\u0005\u0002\u0019\r\u0003b\u0002B~w\u0012\u0005#Q \u0005\b\u0005sZH\u0011\tD%\u0011\u001d\u0019)a\u001fC!\r\u001bBqA!\u0003|\t\u00032\tF\u0002\u0004\u0006VF\u0011Qq\u001b\u0005\f\u0003o\fyA!b\u0001\n\u0003)Y\u000eC\u0006\u0003`\u0006=!\u0011!Q\u0001\n\u0015u\u0007\u0002CAG\u0003\u001f!\t!b8\t\u0011\u0005]\u0016q\u0002C\u0001\u000bKD\u0001Ba;\u0002\u0010\u0011\u0005!Q\u001e\u0005\t\u0005_\fy\u0001\"\u0001\u0006n\"A\u0011qZA\b\t\u0003)\t\u0010\u0003\u0005\u0003|\u0006=A\u0011\tB\u007f\u0011!\u0011I(a\u0004\u0005B\u0015]\b\u0002CB\u0003\u0003\u001f!\t%b?\t\u0011\t%\u0011q\u0002C!\u000b\u007fD\u0011B\"\b\u0012\u0003\u0003%IAb\b\u0003\u0011\u0005\u0013(/Y=TKFTA!!\f\u00020\u00059Q.\u001e;bE2,'\u0002BA\u0019\u0003g\t!bY8mY\u0016\u001cG/[8o\u0015\t\t)$A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\t\u0005m\u0012\u0011J\n\f\u0001\u0005u\u0012QLA2\u0003[\n)\b\u0005\u0004\u0002@\u0005\u0005\u0013QI\u0007\u0003\u0003WIA!a\u0011\u0002,\tY\u0011IY:ue\u0006\u001cGoU3r!\u0011\t9%!\u0013\r\u0001\u00119\u00111\n\u0001C\u0002\u00055#!\u0001+\u0012\t\u0005=\u0013q\u000b\t\u0005\u0003#\n\u0019&\u0004\u0002\u00024%!\u0011QKA\u001a\u0005\u001dqu\u000e\u001e5j]\u001e\u0004B!!\u0015\u0002Z%!\u00111LA\u001a\u0005\r\te.\u001f\t\u0007\u0003\u007f\ty&!\u0012\n\t\u0005\u0005\u00141\u0006\u0002\u000b\u0013:$W\r_3e'\u0016\f\bCCA \u0003K\n)%!\u001b\u0002l%!\u0011qMA\u0016\u00055Ie\u000eZ3yK\u0012\u001cV-](qgB\u0019\u0011q\b\u0001\u0011\u000b\u0005}\u0002!!\u0012\u0011\u0015\u0005=\u0014\u0011OA#\u0003S\nY'\u0004\u0002\u00020%!\u00111OA\u0018\u0005U\u0019FO]5di>\u0003H/[7ju\u0016$7+Z9PaN\u0004B!a\u001e\u0002\b:!\u0011\u0011PAB\u001d\u0011\tY(!!\u000e\u0005\u0005u$\u0002BA@\u0003o\ta\u0001\u0010:p_Rt\u0014BAA\u001b\u0013\u0011\t))a\r\u0002\u000fA\f7m[1hK&!\u0011\u0011RAF\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\u0011\t))a\r\u0002\rqJg.\u001b;?)\t\tY'A\bji\u0016\u0014\u0018M\u00197f\r\u0006\u001cGo\u001c:z+\t\t)\n\u0005\u0004\u0002p\u0005]\u0015\u0011N\u0005\u0005\u00033\u000byC\u0001\u0006TKF4\u0015m\u0019;pef\fAB\u001a:p[N\u0003XmY5gS\u000e$B!a\u001b\u0002 \"9\u0011\u0011U\u0002A\u0002\u0005\r\u0016\u0001B2pY2\u0004b!a\u001c\u0002&\u0006\u0015\u0013\u0002BAT\u0003_\u0011A\"\u0013;fe\u0006\u0014G.Z(oG\u0016\f!C\\3x'B,7-\u001b4jG\n+\u0018\u000e\u001c3feV\u0011\u0011Q\u0016\t\t\u0003\u007f\ty+!\u0012\u0002l%!\u0011\u0011WA\u0016\u0005\u001d\u0011U/\u001b7eKJ\fQ!Z7qif,\"!a\u001b\u0002\u000f\u0015dW-\u001c+bOV\u0011\u00111\u0018\u0019\u0005\u0003{\u000bY\r\u0005\u0004\u0002@\u0006\u0015\u0017\u0011Z\u0007\u0003\u0003\u0003TA!a1\u00024\u00059!/\u001a4mK\u000e$\u0018\u0002BAd\u0003\u0003\u0014\u0001b\u00117bgN$\u0016m\u001a\t\u0005\u0003\u000f\nY\rB\u0006\u0002N\u001a\t\t\u0011!A\u0003\u0002\u00055#aA0%c\u00051Q\u000f\u001d3bi\u0016$b!a5\u0002Z\u0006M\b\u0003BA)\u0003+LA!a6\u00024\t!QK\\5u\u0011\u001d\tYn\u0002a\u0001\u0003;\fQ!\u001b8eKb\u0004B!!\u0015\u0002`&!\u0011\u0011]A\u001a\u0005\rIe\u000e\u001e\u0015\t\u00033\f)/a;\u0002pB!\u0011\u0011KAt\u0013\u0011\tI/a\r\u0003\u001d\u0011,\u0007O]3dCR,GMT1nK\u0006\u0012\u0011Q^\u0001\u0004S\u0012D\u0018EAAy\u0003\u0019\u0011d&M\u001a/a!9\u0011Q_\u0004A\u0002\u0005\u0015\u0013\u0001B3mK6\fQ!\u0019:sCf,\"!a?1\t\u0005u(Q\u0001\t\u0007\u0003#\nyPa\u0001\n\t\t\u0005\u00111\u0007\u0002\u0006\u0003J\u0014\u0018-\u001f\t\u0005\u0003\u000f\u0012)\u0001B\u0006\u0003\b!\t\t\u0011!A\u0003\u0002\u00055#aA0%e\u000591\u000f^3qa\u0016\u0014X\u0003\u0002B\u0007\u0005/!BAa\u0004\u0003>I1!\u0011\u0003B\u000b\u0005W1aAa\u0005\u0001\u0001\t=!\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004\u0003BA$\u0005/!qA!\u0007\n\u0005\u0004\u0011YBA\u0001T#\u0011\tyE!\b1\t\t}!q\u0005\t\u0007\u0003_\u0012\tC!\n\n\t\t\r\u0012q\u0006\u0002\b'R,\u0007\u000f]3s!\u0011\t9Ea\n\u0005\u0019\t%\"qCA\u0001\u0002\u0003\u0015\t!!\u0014\u0003\u0007}#3\u0007\u0005\u0003\u0003.\t]b\u0002\u0002B\u0018\u0005gqA!!\u001f\u00032%!\u0011\u0011GA\u001a\u0013\u0011\u0011)$a\f\u0002\u000fM#X\r\u001d9fe&!!\u0011\bB\u001e\u00059)eMZ5dS\u0016tGo\u00159mSRTAA!\u000e\u00020!9!qH\u0005A\u0004\t\u0005\u0013!B:iCB,\u0007\u0003CA8\u0005\u0007\n)E!\u0006\n\t\t\u0015\u0013q\u0006\u0002\r'R,\u0007\u000f]3s'\"\f\u0007/Z\u0001\nG2\f7o\u001d(b[\u0016,\"Aa\u0013\u0011\t\t5#qK\u0007\u0003\u0005\u001fRAA!\u0015\u0003T\u0005!A.\u00198h\u0015\t\u0011)&\u0001\u0003kCZ\f\u0017\u0002\u0002B-\u0005\u001f\u0012aa\u0015;sS:<\u0017!B2m_:,\u0017aC2paf$v.\u0011:sCf,BA!\u0019\u0003lQA\u0011Q\u001cB2\u0005c\u0012)\bC\u0004\u0003f1\u0001\rAa\u001a\u0002\u0005a\u001c\bCBA)\u0003\u007f\u0014I\u0007\u0005\u0003\u0002H\t-Da\u0002B7\u0019\t\u0007!q\u000e\u0002\u0002\u0005F!\u0011QIA,\u0011\u001d\u0011\u0019\b\u0004a\u0001\u0003;\fQa\u001d;beRDqAa\u001e\r\u0001\u0004\ti.A\u0002mK:\fa!Z9vC2\u001cH\u0003\u0002B?\u0005\u0007\u0003B!!\u0015\u0003\u0000%!!\u0011QA\u001a\u0005\u001d\u0011un\u001c7fC:DqA!\"\u000e\u0001\u0004\t9&A\u0003pi\",'/\u0001\u0004t_J$X\rZ\u000b\u0005\u0005\u0017\u0013I\n\u0006\u0003\u0002l\t5\u0005b\u0002BH\u001d\u0001\u000f!\u0011S\u0001\u0004_J$\u0007CBA<\u0005'\u00139*\u0003\u0003\u0003\u0016\u0006-%\u0001C(sI\u0016\u0014\u0018N\\4\u0011\t\u0005\u001d#\u0011\u0014\u0003\b\u0005[r!\u0019\u0001B8\u0003-\u0019xN\u001d;J]Bc\u0017mY3\u0016\t\t}%1\u0016\u000b\u0003\u0005C#BAa)\u0003&6\t\u0001\u0001C\u0004\u0003\u0010>\u0001\u001dAa*\u0011\r\u0005]$1\u0013BU!\u0011\t9Ea+\u0005\u000f\t5tB1\u0001\u0003p%b\u0001a\u001f\u0014?_\u000e\\uK\u0007\u001a\u0002\u0010\tIqN\u001a\"p_2,\u0017M\\\n\u0006#\tM&\u0011\u0018\t\u0005\u0003#\u0012),\u0003\u0003\u00038\u0006M\"AB!osJ+g\r\u0005\u0004\u0002p\tm\u0016\u0011N\u0005\u0005\u0005{\u000byCA\u0011TiJL7\r^(qi&l\u0017N_3e\u00072\f7o\u001d+bON+\u0017OR1di>\u0014\u0018\u0010\u0006\u0002\u0003BB\u0019\u0011qH\t\u0002\u0011UtG/Y4hK\u0012\f\u0011\"\u001e8uC\u001e<W\r\u001a\u0011\u0002\u001b\u0015k\u0007\u000f^=BeJ\f\u0017pU3r!\u0015\u0011YM\u0007BZ\u001b\u0005\t\"!B8g%\u00164W\u0003\u0002Bi\u0005/\u001c2A\u0007Bj!\u0015\ty\u0004\u0001Bk!\u0011\t9Ea6\u0005\u000f\u0005-#D1\u0001\u0003ZF!\u0011q\nBZ+\t\u0011i\u000e\u0005\u0004\u0002R\u0005}(Q[\u0001\u0007CJ\u0014\u0018-\u001f\u0011\u0015\t\t\r(Q\u001d\t\u0006\u0005\u0017T\"Q\u001b\u0005\b\u0003ol\u0002\u0019\u0001Bo+\t\u0011I\u000f\u0005\u0004\u0002@\u0006\u0015'Q[\u0001\u0007Y\u0016tw\r\u001e5\u0016\u0005\u0005u\u0017!B1qa2LH\u0003\u0002Bk\u0005gDq!a7!\u0001\u0004\ti\u000e\u0006\u0004\u0002T\n](\u0011 \u0005\b\u00037\f\u0003\u0019AAo\u0011\u001d\t)0\ta\u0001\u0005+\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0003;$BA! \u0004\u0002!911A\u0012A\u0002\u0005]\u0013\u0001\u0002;iCR\f\u0001\"\u001b;fe\u0006$xN]\u000b\u0003\u0007\u0013\u0001b!a\u001c\u0004\f\tU\u0017\u0002BB\u0007\u0003_\u0011\u0001\"\u0013;fe\u0006$xN]\u000b\u0005\u0007#\u0019I\u0002\u0006\u0003\u0004\u0014\r\u001d\"CBB\u000b\u0007/\u0011YC\u0002\u0004\u0003\u0014i\u000111\u0003\t\u0005\u0003\u000f\u001aI\u0002B\u0004\u0003\u001a\u0015\u0012\raa\u0007\u0012\t\u0005=3Q\u0004\u0019\u0005\u0007?\u0019\u0019\u0003\u0005\u0004\u0002p\t\u00052\u0011\u0005\t\u0005\u0003\u000f\u001a\u0019\u0003\u0002\u0007\u0004&\re\u0011\u0011!A\u0001\u0006\u0003\tiEA\u0002`IUBqAa\u0010&\u0001\b\u0019I\u0003\u0005\u0005\u0002p\t\r#Q[B\fQ\u001dQ2QFB\u001a\u0007k\u0001B!!\u0015\u00040%!1\u0011GA\u001a\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0004+\u0011\u0019Ida\u0010\u0015\t\rm2\u0011\t\t\u0006\u0003\u007f\u00011Q\b\t\u0005\u0003\u000f\u001ay\u0004B\u0004\u0002LY\u0011\r!!\u0014\t\u0013\r\rc#!AA\u0004\r\u0015\u0013AC3wS\u0012,gnY3%cA1\u0011qXAc\u0007{\tAA\u001a:p[V!11JB*)\u0011\u0019ie!\u0018\u0015\t\r=3q\u000b\t\u0006\u0003\u007f\u00011\u0011\u000b\t\u0005\u0003\u000f\u001a\u0019\u0006B\u0004\u0004V]\u0011\r!!\u0014\u0003\u0003\u0005C\u0011b!\u0017\u0018\u0003\u0003\u0005\u001daa\u0017\u0002\u0015\u00154\u0018\u000eZ3oG\u0016$#\u0007\u0005\u0004\u0002@\u0006\u00157\u0011\u000b\u0005\b\u0007?:\u0002\u0019AB1\u0003\tIG\u000f\u0005\u0004\u0002p\u0005\u00156\u0011K\u0001\u000b]\u0016<()^5mI\u0016\u0014X\u0003BB4\u0007[\"Ba!\u001b\u0004rAA\u0011qHAX\u0007W\u001ay\u0007\u0005\u0003\u0002H\r5DaBB+1\t\u0007\u0011Q\n\t\u0006\u0003\u007f\u000111\u000e\u0005\n\u0007gB\u0012\u0011!a\u0002\u0007k\n!\"\u001a<jI\u0016t7-\u001a\u00134!\u0019\ty,!2\u0004l\u0005!Q.Y6f+\u0011\u0019Yh!!\u0015\t\ru41\u0011\t\u0006\u0003\u007f\u00011q\u0010\t\u0005\u0003\u000f\u001a\t\tB\u0004\u0002Le\u0011\r!!\u0014\t\u000f\r\u0015\u0015\u00041\u0001\u0004\b\u0006\t\u0001\u0010\u0005\u0004\u0002R\u0005}8q\u0010\u0002\u0007_\u001a\u0014\u0015\u0010^3\u0014\u0007\u0019\u001ai\tE\u0003\u0002@\u0001\u0019y\t\u0005\u0003\u0002R\rE\u0015\u0002BBJ\u0003g\u0011AAQ=uKV\u00111q\u0013\t\u0007\u0003#\nypa$\u0015\t\rm5Q\u0014\t\u0004\u0005\u00174\u0003bBA|S\u0001\u00071qS\u000b\u0003\u0007C\u0003Baa)\u0004*:!\u0011qXBS\u0013\u0011\u00199+!1\u0002\u001f5\u000bg.\u001b4fgR4\u0015m\u0019;pefLAaa+\u0004.\na!)\u001f;f\u001b\u0006t\u0017NZ3ti*!1qUAa)\u0011\u0019yi!-\t\u000f\u0005mG\u00061\u0001\u0002^R1\u00111[B[\u0007oCq!a7.\u0001\u0004\ti\u000eC\u0004\u0002v6\u0002\raa$\u0015\t\tu41\u0018\u0005\b\u0007\u0007y\u0003\u0019AA,+\t\u0019y\f\u0005\u0004\u0002p\r-1qR\u000b\u0005\u0007\u0007\u001cY\r\u0006\u0003\u0004F\u000ee'CBBd\u0007\u0013\u0014YC\u0002\u0004\u0003\u0014\u0019\u00021Q\u0019\t\u0005\u0003\u000f\u001aY\rB\u0004\u0003\u001aE\u0012\ra!4\u0012\t\u0005=3q\u001a\u0019\u0005\u0007#\u001c)\u000e\u0005\u0004\u0002p\t\u000521\u001b\t\u0005\u0003\u000f\u001a)\u000e\u0002\u0007\u0004X\u000e-\u0017\u0011!A\u0001\u0006\u0003\tiEA\u0002`IYBqAa\u00102\u0001\b\u0019Y\u000e\u0005\u0005\u0002p\t\r3qRBeQ\u001d13QFB\u001a\u0007k\u0011qa\u001c4TQ>\u0014HoE\u00023\u0007G\u0004R!a\u0010\u0001\u0007K\u0004B!!\u0015\u0004h&!1\u0011^A\u001a\u0005\u0015\u0019\u0006n\u001c:u+\t\u0019i\u000f\u0005\u0004\u0002R\u0005}8Q\u001d\u000b\u0005\u0007c\u001c\u0019\u0010E\u0002\u0003LJBq!a>6\u0001\u0004\u0019i/\u0006\u0002\u0004xB!11UB}\u0013\u0011\u0019Yp!,\u0003\u001bMCwN\u001d;NC:Lg-Z:u)\u0011\u0019)oa@\t\u000f\u0005m\u0007\b1\u0001\u0002^R1\u00111\u001bC\u0002\t\u000bAq!a7:\u0001\u0004\ti\u000eC\u0004\u0002vf\u0002\ra!:\u0015\t\tuD\u0011\u0002\u0005\b\u0007\u0007Y\u0004\u0019AA,+\t!i\u0001\u0005\u0004\u0002p\r-1Q]\u000b\u0005\t#!I\u0002\u0006\u0003\u0005\u0014\u0011\u001d\"C\u0002C\u000b\t/\u0011YC\u0002\u0004\u0003\u0014I\u0002A1\u0003\t\u0005\u0003\u000f\"I\u0002B\u0004\u0003\u001au\u0012\r\u0001b\u0007\u0012\t\u0005=CQ\u0004\u0019\u0005\t?!\u0019\u0003\u0005\u0004\u0002p\t\u0005B\u0011\u0005\t\u0005\u0003\u000f\"\u0019\u0003\u0002\u0007\u0005&\u0011e\u0011\u0011!A\u0001\u0006\u0003\tiEA\u0002`I]BqAa\u0010>\u0001\b!I\u0003\u0005\u0005\u0002p\t\r3Q\u001dC\fQ\u001d\u00114QFB\u001a\u0007k\u0011aa\u001c4DQ\u0006\u00148c\u0001 \u00052A)\u0011q\b\u0001\u00054A!\u0011\u0011\u000bC\u001b\u0013\u0011!9$a\r\u0003\t\rC\u0017M]\u000b\u0003\tw\u0001b!!\u0015\u0002\u0000\u0012MB\u0003\u0002C \t\u0003\u00022Aa3?\u0011\u001d\t90\u0011a\u0001\tw)\"\u0001\"\u0012\u0011\t\r\rFqI\u0005\u0005\t\u0013\u001aiK\u0001\u0007DQ\u0006\u0014X*\u00198jM\u0016\u001cH\u000f\u0006\u0003\u00054\u00115\u0003bBAn\t\u0002\u0007\u0011Q\u001c\u000b\u0007\u0003'$\t\u0006b\u0015\t\u000f\u0005mW\t1\u0001\u0002^\"9\u0011Q_#A\u0002\u0011MB\u0003\u0002B?\t/Bqaa\u0001H\u0001\u0004\t9&\u0006\u0002\u0005\\A1\u0011qNB\u0006\tg)B\u0001b\u0018\u0005hQ!A\u0011\rC;%\u0019!\u0019\u0007\"\u001a\u0003,\u00191!1\u0003 \u0001\tC\u0002B!a\u0012\u0005h\u00119!\u0011D%C\u0002\u0011%\u0014\u0003BA(\tW\u0002D\u0001\"\u001c\u0005rA1\u0011q\u000eB\u0011\t_\u0002B!a\u0012\u0005r\u0011aA1\u000fC4\u0003\u0003\u0005\tQ!\u0001\u0002N\t\u0019q\f\n\u001d\t\u000f\t}\u0012\nq\u0001\u0005xAA\u0011q\u000eB\"\tg!)'A\u0005bI\u0012\u001cFO]5oORQAQ\u0010C@\t\u0013#I\n\"(\u000f\t\u0005\u001dCq\u0010\u0005\b\t\u0003S\u0005\u0019\u0001CB\u0003\t\u0019(\r\u0005\u0003\u0002@\u0011\u0015\u0015\u0002\u0002CD\u0003W\u0011Qb\u0015;sS:<')^5mI\u0016\u0014\bb\u0002B:\u0015\u0002\u0007A1\u0012\t\u0005\t\u001b#)J\u0004\u0003\u0005\u0010\u0012E\u0005\u0003BA>\u0003gIA\u0001b%\u00024\u00051\u0001K]3eK\u001aLAA!\u0017\u0005\u0018*!A1SA\u001a\u0011\u001d!YJ\u0013a\u0001\t\u0017\u000b1a]3q\u0011\u001d!yJ\u0013a\u0001\t\u0017\u000b1!\u001a8eQ\u001dq4QFB\u001a\u0007k\u0011Qa\u001c4J]R\u001c2a\u0013CT!\u0015\ty\u0004AAo+\t!Y\u000b\u0005\u0004\u0002R\u0005}\u0018Q\u001c\u000b\u0005\t_#\t\fE\u0002\u0003L.Cq!a>O\u0001\u0004!Y+\u0006\u0002\u00056B!11\u0015C\\\u0013\u0011!Il!,\u0003\u0017%sG/T1oS\u001a,7\u000f\u001e\u000b\u0005\u0003;$i\fC\u0004\u0002\\F\u0003\r!!8\u0015\r\u0005MG\u0011\u0019Cb\u0011\u001d\tYN\u0015a\u0001\u0003;Dq!!>S\u0001\u0004\ti\u000e\u0006\u0003\u0003~\u0011\u001d\u0007bBB\u0002)\u0002\u0007\u0011qK\u000b\u0003\t\u0017\u0004b!a\u001c\u0004\f\u0005uW\u0003\u0002Ch\t/$B\u0001\"5\u0005fJ1A1\u001bCk\u0005W1aAa\u0005L\u0001\u0011E\u0007\u0003BA$\t/$qA!\u0007W\u0005\u0004!I.\u0005\u0003\u0002P\u0011m\u0007\u0007\u0002Co\tC\u0004b!a\u001c\u0003\"\u0011}\u0007\u0003BA$\tC$A\u0002b9\u0005X\u0006\u0005\t\u0011!B\u0001\u0003\u001b\u00121a\u0018\u0013:\u0011\u001d\u0011yD\u0016a\u0002\tO\u0004\u0002\"a\u001c\u0003D\u0005uGQ\u001b\u0015\b\u0017\u000e521GB\u001b\u0005\u0019yg\rT8oON\u0019q\u000bb<\u0011\u000b\u0005}\u0002\u0001\"=\u0011\t\u0005EC1_\u0005\u0005\tk\f\u0019D\u0001\u0003M_:<WC\u0001C}!\u0019\t\t&a@\u0005rR!AQ C\u0000!\r\u0011Ym\u0016\u0005\b\u0003oT\u0006\u0019\u0001C}+\t)\u0019\u0001\u0005\u0003\u0004$\u0016\u0015\u0011\u0002BC\u0004\u0007[\u0013A\u0002T8oO6\u000bg.\u001b4fgR$B\u0001\"=\u0006\f!9\u00111\\/A\u0002\u0005uGCBAj\u000b\u001f)\t\u0002C\u0004\u0002\\z\u0003\r!!8\t\u000f\u0005Uh\f1\u0001\u0005rR!!QPC\u000b\u0011\u001d\u0019\u0019\u0001\u0019a\u0001\u0003/*\"!\"\u0007\u0011\r\u0005=41\u0002Cy+\u0011)i\"\"\n\u0015\t\u0015}Q1\u0007\n\u0007\u000bC)\u0019Ca\u000b\u0007\r\tMq\u000bAC\u0010!\u0011\t9%\"\n\u0005\u000f\te!M1\u0001\u0006(E!\u0011qJC\u0015a\u0011)Y#b\f\u0011\r\u0005=$\u0011EC\u0017!\u0011\t9%b\f\u0005\u0019\u0015ERQEA\u0001\u0002\u0003\u0015\t!!\u0014\u0003\t}#\u0013\u0007\r\u0005\b\u0005\u007f\u0011\u00079AC\u001b!!\tyGa\u0011\u0005r\u0016\r\u0002fB,\u0004.\rM2Q\u0007\u0002\b_\u001a4En\\1u'\r\u0019WQ\b\t\u0006\u0003\u007f\u0001Qq\b\t\u0005\u0003#*\t%\u0003\u0003\u0006D\u0005M\"!\u0002$m_\u0006$XCAC$!\u0019\t\t&a@\u0006@Q!Q1JC'!\r\u0011Ym\u0019\u0005\b\u0003o4\u0007\u0019AC$+\t)\t\u0006\u0005\u0003\u0004$\u0016M\u0013\u0002BC+\u0007[\u0013QB\u00127pCRl\u0015M\\5gKN$H\u0003BC \u000b3Bq!a7j\u0001\u0004\ti\u000e\u0006\u0004\u0002T\u0016uSq\f\u0005\b\u00037T\u0007\u0019AAo\u0011\u001d\t)P\u001ba\u0001\u000b\u007f!BA! \u0006d!911\u00017A\u0002\u0005]SCAC4!\u0019\tyga\u0003\u0006@U!Q1NC:)\u0011)i'\"!\u0013\r\u0015=T\u0011\u000fB\u0016\r\u0019\u0011\u0019b\u0019\u0001\u0006nA!\u0011qIC:\t\u001d\u0011IB\u001cb\u0001\u000bk\nB!a\u0014\u0006xA\"Q\u0011PC?!\u0019\tyG!\t\u0006|A!\u0011qIC?\t1)y(b\u001d\u0002\u0002\u0003\u0005)\u0011AA'\u0005\u0011yF%M\u0019\t\u000f\t}b\u000eq\u0001\u0006\u0004BA\u0011q\u000eB\"\u000b\u007f)\t\bK\u0004d\u0007[\u0019\u0019d!\u000e\u0003\u0011=4Gi\\;cY\u0016\u001c2a\\CF!\u0015\ty\u0004ACG!\u0011\t\t&b$\n\t\u0015E\u00151\u0007\u0002\u0007\t>,(\r\\3\u0016\u0005\u0015U\u0005CBA)\u0003\u007f,i\t\u0006\u0003\u0006\u001a\u0016m\u0005c\u0001Bf_\"9\u0011q\u001f:A\u0002\u0015UUCACP!\u0011\u0019\u0019+\")\n\t\u0015\r6Q\u0016\u0002\u000f\t>,(\r\\3NC:Lg-Z:u)\u0011)i)b*\t\u000f\u0005mW\u000f1\u0001\u0002^R1\u00111[CV\u000b[Cq!a7w\u0001\u0004\ti\u000eC\u0004\u0002vZ\u0004\r!\"$\u0015\t\tuT\u0011\u0017\u0005\b\u0007\u0007A\b\u0019AA,+\t))\f\u0005\u0004\u0002p\r-QQR\u000b\u0005\u000bs+\t\r\u0006\u0003\u0006<\u0016='CBC_\u000b\u007f\u0013YC\u0002\u0004\u0003\u0014=\u0004Q1\u0018\t\u0005\u0003\u000f*\t\rB\u0004\u0003\u001ai\u0014\r!b1\u0012\t\u0005=SQ\u0019\u0019\u0005\u000b\u000f,Y\r\u0005\u0004\u0002p\t\u0005R\u0011\u001a\t\u0005\u0003\u000f*Y\r\u0002\u0007\u0006N\u0016\u0005\u0017\u0011!A\u0001\u0006\u0003\tiE\u0001\u0003`IE\u0012\u0004b\u0002B u\u0002\u000fQ\u0011\u001b\t\t\u0003_\u0012\u0019%\"$\u0006@\":qn!\f\u00044\rU\"AB8g+:LGo\u0005\u0003\u0002\u0010\u0015e\u0007#BA \u0001\u0005MWCACo!\u0019\t\t&a@\u0002TR!Q\u0011]Cr!\u0011\u0011Y-a\u0004\t\u0011\u0005]\u0018Q\u0003a\u0001\u000b;,\"!b:\u0011\t\r\rV\u0011^\u0005\u0005\u000bW\u001ciK\u0001\u0007V]&$X*\u00198jM\u0016\u001cH\u000f\u0006\u0003\u0002T\u0016=\b\u0002CAn\u00037\u0001\r!!8\u0015\r\u0005MW1_C{\u0011!\tY.!\bA\u0002\u0005u\u0007\u0002CA{\u0003;\u0001\r!a5\u0015\t\tuT\u0011 \u0005\t\u0007\u0007\t\t\u00031\u0001\u0002XU\u0011QQ \t\u0007\u0003_\u001aY!a5\u0016\t\u0019\u0005a\u0011\u0002\u000b\u0005\r\u000719B\u0005\u0004\u0007\u0006\u0019\u001d!1\u0006\u0004\b\u0005'\ty\u0001\u0001D\u0002!\u0011\t9E\"\u0003\u0005\u0011\te\u0011Q\u0005b\u0001\r\u0017\tB!a\u0014\u0007\u000eA\"aq\u0002D\n!\u0019\tyG!\t\u0007\u0012A!\u0011q\tD\n\t11)B\"\u0003\u0002\u0002\u0003\u0005)\u0011AA'\u0005\u0011yF%\r\u001b\t\u0011\t}\u0012Q\u0005a\u0002\r3\u0001\u0002\"a\u001c\u0003D\u0005Mgq\u0001\u0015\t\u0003\u001f\u0019ica\r\u00046\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011a\u0011\u0005\t\u0005\u0005\u001b2\u0019#\u0003\u0003\u0007&\t=#AB(cU\u0016\u001cG\u000fK\u0004\u0012\u0007[\u0019\u0019d!\u000e\u0014\u0007m4Y\u0003E\u0003\u0002@\u0001\u0011i(\u0006\u0002\u00070A1\u0011\u0011KA\u0000\u0005{\"BAb\r\u00076A\u0019!1Z>\t\u000f\u0005]h\u00101\u0001\u00070U\u0011a\u0011\b\t\u0005\u0007G3Y$\u0003\u0003\u0007>\r5&a\u0004\"p_2,\u0017M\\'b]&4Wm\u001d;\u0015\t\tud\u0011\t\u0005\t\u00037\f\u0019\u00011\u0001\u0002^R1\u00111\u001bD#\r\u000fB\u0001\"a7\u0002\u0006\u0001\u0007\u0011Q\u001c\u0005\t\u0003k\f)\u00011\u0001\u0003~Q!!Q\u0010D&\u0011!\u0019\u0019!!\u0003A\u0002\u0005]SC\u0001D(!\u0019\tyga\u0003\u0003~U!a1\u000bD.)\u00111)F\"\u001b\u0013\r\u0019]c\u0011\fB\u0016\r\u0019\u0011\u0019b\u001f\u0001\u0007VA!\u0011q\tD.\t!\u0011I\"!\u0004C\u0002\u0019u\u0013\u0003BA(\r?\u0002DA\"\u0019\u0007fA1\u0011q\u000eB\u0011\rG\u0002B!a\u0012\u0007f\u0011aaq\rD.\u0003\u0003\u0005\tQ!\u0001\u0002N\t!q\fJ\u00194\u0011!\u0011y$!\u0004A\u0004\u0019-\u0004\u0003CA8\u0005\u0007\u0012iH\"\u0017)\u000fm\u001cica\r\u00046!:\u0001a!\f\u00044\rU\u0012\u0001C!se\u0006L8+Z9)\u000fA\u0019ica\r\u00046\u0001")
public abstract class ArraySeq<T>
extends AbstractSeq<T>
implements scala.collection.mutable.IndexedSeq<T>,
StrictOptimizedSeqOps<T, ArraySeq, ArraySeq<T>>,
Serializable {
    private static final long serialVersionUID = 3L;

    public static <T> ArraySeq<T> make(Object x) {
        return ArraySeq$.MODULE$.make(x);
    }

    public static <A> Builder<A, ArraySeq<A>> newBuilder(ClassTag<A> evidence$3) {
        return ArraySeq$.MODULE$.newBuilder(evidence$3);
    }

    public static <A> ArraySeq<A> from(IterableOnce<A> it, ClassTag<A> evidence$2) {
        return ArraySeq$.MODULE$.from(it, evidence$2);
    }

    public static SeqFactory<ArraySeq> untagged() {
        return ArraySeq$.MODULE$.untagged();
    }

    public static SeqOps tabulate(int n, Function1 f, ClassTag evidence$35) {
        Builder tabulate_b = ArraySeq$.MODULE$.newBuilder(evidence$35);
        tabulate_b.sizeHint(n);
        for (int tabulate_i = 0; tabulate_i < n; ++tabulate_i) {
            Object tabulate_$plus$eq_elem = f.apply(tabulate_i);
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var5_5 = null;
        }
        return tabulate_b.result();
    }

    public static SeqOps fill(int n, Function0 elem, ClassTag evidence$34) {
        Builder fill_b = ArraySeq$.MODULE$.newBuilder(evidence$34);
        fill_b.sizeHint(n);
        for (int fill_i = 0; fill_i < n; ++fill_i) {
            Object fill_$plus$eq_elem = elem.apply();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var5_5 = null;
        }
        return fill_b.result();
    }

    public static SeqOps unapplySeq(SeqOps x) {
        return x;
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f, ClassTag evidence$33) {
        ArraySeq$ tabulate_this = ArraySeq$.MODULE$;
        ClassTag<Object> tabulate_evidence$9 = ClassTag$.MODULE$.AnyRef();
        Builder<Object, ArraySeq<Object>> tabulate_b = tabulate_this.newBuilder(tabulate_evidence$9);
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var15_15;
            ClassTag<Object> tabulate_evidence$92 = ClassTag$.MODULE$.AnyRef();
            Builder<Object, ArraySeq<Object>> tabulate_b2 = tabulate_this.newBuilder(tabulate_evidence$92);
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var19_19;
                ClassTag<Object> tabulate_evidence$93 = ClassTag$.MODULE$.AnyRef();
                Builder<Object, ArraySeq<Object>> tabulate_b3 = tabulate_this.newBuilder(tabulate_evidence$93);
                tabulate_b3.sizeHint(n3);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object var23_23;
                    ClassTag<Object> tabulate_evidence$94 = ClassTag$.MODULE$.AnyRef();
                    Builder<Object, ArraySeq<Object>> tabulate_b4 = tabulate_this.newBuilder(tabulate_evidence$94);
                    tabulate_b4.sizeHint(n4);
                    for (int tabulate_i4 = 0; tabulate_i4 < n4; ++tabulate_i4) {
                        Object var26_26;
                        Builder tabulate_b5 = tabulate_this.newBuilder(evidence$33);
                        tabulate_b5.sizeHint(n5);
                        for (int tabulate_i5 = 0; tabulate_i5 < n5; ++tabulate_i5) {
                            Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3, tabulate_i4, tabulate_i5);
                            tabulate_b5.addOne(tabulate_$plus$eq_elem);
                            var26_26 = null;
                        }
                        Object var24_24 = null;
                        var26_26 = null;
                        SeqOps tabulate_$plus$eq_elem = tabulate_b5.result();
                        tabulate_b4.addOne(tabulate_$plus$eq_elem);
                        var23_23 = null;
                    }
                    Object var21_21 = null;
                    var23_23 = null;
                    Object var20_20 = null;
                    SeqOps tabulate_$plus$eq_elem = tabulate_b4.result();
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var19_19 = null;
                }
                Object var17_17 = null;
                var19_19 = null;
                Object var16_16 = null;
                SeqOps tabulate_$plus$eq_elem = tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var15_15 = null;
            }
            Object var13_13 = null;
            var15_15 = null;
            Object var12_12 = null;
            SeqOps tabulate_$plus$eq_elem = tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var11_11 = null;
        }
        return tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f, ClassTag evidence$32) {
        ArraySeq$ tabulate_this = ArraySeq$.MODULE$;
        ClassTag<Object> tabulate_evidence$9 = ClassTag$.MODULE$.AnyRef();
        Builder<Object, ArraySeq<Object>> tabulate_b = tabulate_this.newBuilder(tabulate_evidence$9);
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var14_14;
            ClassTag<Object> tabulate_evidence$92 = ClassTag$.MODULE$.AnyRef();
            Builder<Object, ArraySeq<Object>> tabulate_b2 = tabulate_this.newBuilder(tabulate_evidence$92);
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var18_18;
                ClassTag<Object> tabulate_evidence$93 = ClassTag$.MODULE$.AnyRef();
                Builder<Object, ArraySeq<Object>> tabulate_b3 = tabulate_this.newBuilder(tabulate_evidence$93);
                tabulate_b3.sizeHint(n3);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object var21_21;
                    Builder tabulate_b4 = tabulate_this.newBuilder(evidence$32);
                    tabulate_b4.sizeHint(n4);
                    for (int tabulate_i4 = 0; tabulate_i4 < n4; ++tabulate_i4) {
                        Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3, tabulate_i4);
                        tabulate_b4.addOne(tabulate_$plus$eq_elem);
                        var21_21 = null;
                    }
                    Object var19_19 = null;
                    var21_21 = null;
                    SeqOps tabulate_$plus$eq_elem = tabulate_b4.result();
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var18_18 = null;
                }
                Object var16_16 = null;
                var18_18 = null;
                Object var15_15 = null;
                SeqOps tabulate_$plus$eq_elem = tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var14_14 = null;
            }
            Object var12_12 = null;
            var14_14 = null;
            Object var11_11 = null;
            SeqOps tabulate_$plus$eq_elem = tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var10_10 = null;
        }
        return tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f, ClassTag evidence$31) {
        ArraySeq$ tabulate_this = ArraySeq$.MODULE$;
        ClassTag<Object> tabulate_evidence$9 = ClassTag$.MODULE$.AnyRef();
        Builder<Object, ArraySeq<Object>> tabulate_b = tabulate_this.newBuilder(tabulate_evidence$9);
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var13_13;
            ClassTag<Object> tabulate_evidence$92 = ClassTag$.MODULE$.AnyRef();
            Builder<Object, ArraySeq<Object>> tabulate_b2 = tabulate_this.newBuilder(tabulate_evidence$92);
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var16_16;
                Builder tabulate_b3 = tabulate_this.newBuilder(evidence$31);
                tabulate_b3.sizeHint(n3);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3);
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var16_16 = null;
                }
                Object var14_14 = null;
                var16_16 = null;
                SeqOps tabulate_$plus$eq_elem = tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var13_13 = null;
            }
            Object var11_11 = null;
            var13_13 = null;
            Object var10_10 = null;
            SeqOps tabulate_$plus$eq_elem = tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var9_9 = null;
        }
        return tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, Function2 f, ClassTag evidence$30) {
        ArraySeq$ tabulate_this = ArraySeq$.MODULE$;
        ClassTag<Object> tabulate_evidence$9 = ClassTag$.MODULE$.AnyRef();
        Builder<Object, ArraySeq<Object>> tabulate_b = tabulate_this.newBuilder(tabulate_evidence$9);
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var11_11;
            Builder tabulate_b2 = tabulate_this.newBuilder(evidence$30);
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2);
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var11_11 = null;
            }
            Object var9_9 = null;
            var11_11 = null;
            SeqOps tabulate_$plus$eq_elem = tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var8_8 = null;
        }
        return tabulate_b.result();
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem, ClassTag evidence$29) {
        ArraySeq$ fill_this = ArraySeq$.MODULE$;
        ClassTag<Object> fill_evidence$8 = ClassTag$.MODULE$.AnyRef();
        Builder<Object, ArraySeq<Object>> fill_b = fill_this.newBuilder(fill_evidence$8);
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var15_15;
            ClassTag<Object> fill_evidence$82 = ClassTag$.MODULE$.AnyRef();
            Builder<Object, ArraySeq<Object>> fill_b2 = fill_this.newBuilder(fill_evidence$82);
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var19_19;
                ClassTag<Object> fill_evidence$83 = ClassTag$.MODULE$.AnyRef();
                Builder<Object, ArraySeq<Object>> fill_b3 = fill_this.newBuilder(fill_evidence$83);
                fill_b3.sizeHint(n3);
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object var23_23;
                    ClassTag<Object> fill_evidence$84 = ClassTag$.MODULE$.AnyRef();
                    Builder<Object, ArraySeq<Object>> fill_b4 = fill_this.newBuilder(fill_evidence$84);
                    fill_b4.sizeHint(n4);
                    for (int fill_i4 = 0; fill_i4 < n4; ++fill_i4) {
                        Object var26_26;
                        Builder fill_b5 = fill_this.newBuilder(evidence$29);
                        fill_b5.sizeHint(n5);
                        for (int fill_i5 = 0; fill_i5 < n5; ++fill_i5) {
                            Object fill_$plus$eq_elem = elem.apply();
                            fill_b5.addOne(fill_$plus$eq_elem);
                            var26_26 = null;
                        }
                        Object var24_24 = null;
                        var26_26 = null;
                        SeqOps fill_$plus$eq_elem = fill_b5.result();
                        fill_b4.addOne(fill_$plus$eq_elem);
                        var23_23 = null;
                    }
                    Object var21_21 = null;
                    var23_23 = null;
                    Object var20_20 = null;
                    SeqOps fill_$plus$eq_elem = fill_b4.result();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var19_19 = null;
                }
                Object var17_17 = null;
                var19_19 = null;
                Object var16_16 = null;
                SeqOps fill_$plus$eq_elem = fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var15_15 = null;
            }
            Object var13_13 = null;
            var15_15 = null;
            Object var12_12 = null;
            SeqOps fill_$plus$eq_elem = fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var11_11 = null;
        }
        return fill_b.result();
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem, ClassTag evidence$28) {
        ArraySeq$ fill_this = ArraySeq$.MODULE$;
        ClassTag<Object> fill_evidence$8 = ClassTag$.MODULE$.AnyRef();
        Builder<Object, ArraySeq<Object>> fill_b = fill_this.newBuilder(fill_evidence$8);
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var14_14;
            ClassTag<Object> fill_evidence$82 = ClassTag$.MODULE$.AnyRef();
            Builder<Object, ArraySeq<Object>> fill_b2 = fill_this.newBuilder(fill_evidence$82);
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var18_18;
                ClassTag<Object> fill_evidence$83 = ClassTag$.MODULE$.AnyRef();
                Builder<Object, ArraySeq<Object>> fill_b3 = fill_this.newBuilder(fill_evidence$83);
                fill_b3.sizeHint(n3);
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object var21_21;
                    Builder fill_b4 = fill_this.newBuilder(evidence$28);
                    fill_b4.sizeHint(n4);
                    for (int fill_i4 = 0; fill_i4 < n4; ++fill_i4) {
                        Object fill_$plus$eq_elem = elem.apply();
                        fill_b4.addOne(fill_$plus$eq_elem);
                        var21_21 = null;
                    }
                    Object var19_19 = null;
                    var21_21 = null;
                    SeqOps fill_$plus$eq_elem = fill_b4.result();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var18_18 = null;
                }
                Object var16_16 = null;
                var18_18 = null;
                Object var15_15 = null;
                SeqOps fill_$plus$eq_elem = fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var14_14 = null;
            }
            Object var12_12 = null;
            var14_14 = null;
            Object var11_11 = null;
            SeqOps fill_$plus$eq_elem = fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var10_10 = null;
        }
        return fill_b.result();
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem, ClassTag evidence$27) {
        ArraySeq$ fill_this = ArraySeq$.MODULE$;
        ClassTag<Object> fill_evidence$8 = ClassTag$.MODULE$.AnyRef();
        Builder<Object, ArraySeq<Object>> fill_b = fill_this.newBuilder(fill_evidence$8);
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var13_13;
            ClassTag<Object> fill_evidence$82 = ClassTag$.MODULE$.AnyRef();
            Builder<Object, ArraySeq<Object>> fill_b2 = fill_this.newBuilder(fill_evidence$82);
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var16_16;
                Builder fill_b3 = fill_this.newBuilder(evidence$27);
                fill_b3.sizeHint(n3);
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object fill_$plus$eq_elem = elem.apply();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var16_16 = null;
                }
                Object var14_14 = null;
                var16_16 = null;
                SeqOps fill_$plus$eq_elem = fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var13_13 = null;
            }
            Object var11_11 = null;
            var13_13 = null;
            Object var10_10 = null;
            SeqOps fill_$plus$eq_elem = fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var9_9 = null;
        }
        return fill_b.result();
    }

    public static Object fill(int n1, int n2, Function0 elem, ClassTag evidence$26) {
        ArraySeq$ fill_this = ArraySeq$.MODULE$;
        ClassTag<Object> fill_evidence$8 = ClassTag$.MODULE$.AnyRef();
        Builder<Object, ArraySeq<Object>> fill_b = fill_this.newBuilder(fill_evidence$8);
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var11_11;
            Builder fill_b2 = fill_this.newBuilder(evidence$26);
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object fill_$plus$eq_elem = elem.apply();
                fill_b2.addOne(fill_$plus$eq_elem);
                var11_11 = null;
            }
            Object var9_9 = null;
            var11_11 = null;
            SeqOps fill_$plus$eq_elem = fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var8_8 = null;
        }
        return fill_b.result();
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$24, ClassTag evidence$25) {
        return ClassTagIterableFactory.range$(ArraySeq$.MODULE$, start2, end, step, evidence$24, evidence$25);
    }

    public static Object range(Object start2, Object end, Integral evidence$22, ClassTag evidence$23) {
        return ClassTagIterableFactory.range$(ArraySeq$.MODULE$, start2, end, evidence$22, evidence$23);
    }

    public static Factory evidenceIterableFactory(Object evidence$13) {
        return EvidenceIterableFactory.evidenceIterableFactory$(ArraySeq$.MODULE$, evidence$13);
    }

    public static Object unfold(Object init2, Function1 f, Object evidence$11) {
        ArraySeq$ unfold_this = ArraySeq$.MODULE$;
        View.Unfold from_it = new View.Unfold(init2, f);
        return unfold_this.from(from_it, (ClassTag)evidence$11);
    }

    public static Object iterate(Object start2, int len, Function1 f, Object evidence$10) {
        ArraySeq$ iterate_this = ArraySeq$.MODULE$;
        View.Iterate<Object> from_it = new View.Iterate<Object>(start2, len, f);
        return iterate_this.from(from_it, (ClassTag)evidence$10);
    }

    @Override
    public Object distinctBy(Function1 f) {
        return StrictOptimizedSeqOps.distinctBy$(this, f);
    }

    @Override
    public Object prepended(Object elem) {
        return StrictOptimizedSeqOps.prepended$(this, elem);
    }

    @Override
    public Object appended(Object elem) {
        return StrictOptimizedSeqOps.appended$(this, elem);
    }

    @Override
    public Object appendedAll(IterableOnce suffix) {
        return StrictOptimizedSeqOps.appendedAll$(this, suffix);
    }

    @Override
    public Object prependedAll(IterableOnce prefix) {
        return StrictOptimizedSeqOps.prependedAll$(this, prefix);
    }

    @Override
    public Object padTo(int len, Object elem) {
        return StrictOptimizedSeqOps.padTo$(this, len, elem);
    }

    @Override
    public Object diff(Seq that) {
        return StrictOptimizedSeqOps.diff$(this, that);
    }

    @Override
    public Object intersect(Seq that) {
        return StrictOptimizedSeqOps.intersect$(this, that);
    }

    @Override
    public Tuple2<ArraySeq<T>, ArraySeq<T>> partition(Function1<T, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<ArraySeq<T>, ArraySeq<T>> span(Function1<T, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<ArraySeq<A1>, ArraySeq<A2>> unzip(Function1<T, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<ArraySeq<A1>, ArraySeq<A2>, ArraySeq<A3>> unzip3(Function1<T, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<T, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<T, IterableOnce<B>> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatMap$(this, b, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedConcat(IterableOnce<B> that, Builder<B, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedConcat$(this, that, b);
    }

    @Override
    public Object collect(PartialFunction pf) {
        return StrictOptimizedIterableOps.collect$(this, pf);
    }

    @Override
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<T, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<T, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<T, B>, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedZip$(this, that, b);
    }

    @Override
    public Object zipWithIndex() {
        return StrictOptimizedIterableOps.zipWithIndex$(this);
    }

    @Override
    public Object scanLeft(Object z, Function2 op) {
        return StrictOptimizedIterableOps.scanLeft$(this, z, op);
    }

    @Override
    public Object filter(Function1 pred) {
        return StrictOptimizedIterableOps.filter$(this, pred);
    }

    @Override
    public Object filterNot(Function1 pred) {
        return StrictOptimizedIterableOps.filterNot$(this, pred);
    }

    @Override
    public Object filterImpl(Function1 pred, boolean isFlipped) {
        return StrictOptimizedIterableOps.filterImpl$(this, pred, isFlipped);
    }

    @Override
    public <A1, A2> Tuple2<ArraySeq<A1>, ArraySeq<A2>> partitionMap(Function1<T, Either<A1, A2>> f) {
        return StrictOptimizedIterableOps.partitionMap$(this, f);
    }

    @Override
    public Object tapEach(Function1 f) {
        return StrictOptimizedIterableOps.tapEach$(this, f);
    }

    @Override
    public Object takeRight(int n) {
        return StrictOptimizedIterableOps.takeRight$(this, n);
    }

    @Override
    public Object dropRight(int n) {
        return StrictOptimizedIterableOps.dropRight$(this, n);
    }

    @Override
    public scala.collection.mutable.IndexedSeqOps<T, ?, ArraySeq<T>> mapInPlace(Function1<T, T> f) {
        return scala.collection.mutable.IndexedSeqOps.mapInPlace$(this, f);
    }

    @Override
    public scala.collection.mutable.IndexedSeqOps<T, ?, ArraySeq<T>> sortInPlaceWith(Function2<T, T, Object> lt) {
        return scala.collection.mutable.IndexedSeqOps.sortInPlaceWith$(this, lt);
    }

    @Override
    public <B> scala.collection.mutable.IndexedSeqOps<T, ?, ArraySeq<T>> sortInPlaceBy(Function1<T, B> f, Ordering<B> ord) {
        return scala.collection.mutable.IndexedSeqOps.sortInPlaceBy$(this, f, ord);
    }

    @Override
    public String stringPrefix() {
        return IndexedSeq.stringPrefix$(this);
    }

    @Override
    public Iterator<T> iterator() {
        return IndexedSeqOps.iterator$(this);
    }

    @Override
    public Iterator<T> reverseIterator() {
        return IndexedSeqOps.reverseIterator$(this);
    }

    @Override
    public <B$> B$ foldRight(B$ z, Function2<T, B$, B$> op) {
        return (B$)IndexedSeqOps.foldRight$(this, z, op);
    }

    @Override
    public IndexedSeqView<T> view() {
        return IndexedSeqOps.view$(this);
    }

    @Override
    public IndexedSeqView<T> view(int from, int until) {
        return IndexedSeqOps.view$(this, from, until);
    }

    @Override
    public Iterable<T> reversed() {
        return IndexedSeqOps.reversed$(this);
    }

    @Override
    public Object take(int n) {
        return IndexedSeqOps.take$(this, n);
    }

    @Override
    public Object drop(int n) {
        return IndexedSeqOps.drop$(this, n);
    }

    @Override
    public Object reverse() {
        return IndexedSeqOps.reverse$(this);
    }

    @Override
    public Object slice(int from, int until) {
        return IndexedSeqOps.slice$(this, from, until);
    }

    @Override
    public T head() {
        return (T)IndexedSeqOps.head$(this);
    }

    @Override
    public Option<T> headOption() {
        return IndexedSeqOps.headOption$(this);
    }

    @Override
    public T last() {
        return (T)IndexedSeqOps.last$(this);
    }

    @Override
    public final int lengthCompare(int len) {
        return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, len);
    }

    @Override
    public int knownSize() {
        return IndexedSeqOps.knownSize$(this);
    }

    @Override
    public final int lengthCompare(Iterable<?> that) {
        return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, that);
    }

    @Override
    public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
        return IndexedSeqOps.search$(this, elem, ord);
    }

    @Override
    public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
        return IndexedSeqOps.search$(this, elem, from, to, ord);
    }

    @Override
    public SeqFactory<ArraySeq> iterableFactory() {
        return ArraySeq$.MODULE$.untagged();
    }

    @Override
    public ArraySeq<T> fromSpecific(IterableOnce<T> coll) {
        ArrayBuilder arrayBuilder;
        ClassTag<?> make_evidence$1 = this.elemTag();
        Class<?> clazz = make_evidence$1.runtimeClass();
        Class<Byte> clazz2 = Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            arrayBuilder = new ArrayBuilder.ofByte();
        } else {
            Class<Short> clazz3 = Short.TYPE;
            if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
                arrayBuilder = new ArrayBuilder.ofShort();
            } else {
                Class<Character> clazz4 = Character.TYPE;
                if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
                    arrayBuilder = new ArrayBuilder.ofChar();
                } else {
                    Class<Integer> clazz5 = Integer.TYPE;
                    if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
                        arrayBuilder = new ArrayBuilder.ofInt();
                    } else {
                        Class<Long> clazz6 = Long.TYPE;
                        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
                            arrayBuilder = new ArrayBuilder.ofLong();
                        } else {
                            Class<Float> clazz7 = Float.TYPE;
                            if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
                                arrayBuilder = new ArrayBuilder.ofFloat();
                            } else {
                                Class<Double> clazz8 = Double.TYPE;
                                if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
                                    arrayBuilder = new ArrayBuilder.ofDouble();
                                } else {
                                    Class<Boolean> clazz9 = Boolean.TYPE;
                                    if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
                                        arrayBuilder = new ArrayBuilder.ofBoolean();
                                    } else {
                                        Class<Void> clazz10 = Void.TYPE;
                                        arrayBuilder = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef(make_evidence$1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Object var4_2 = null;
        clazz = null;
        ArrayBuilder b = arrayBuilder;
        int s2 = coll.knownSize();
        if (s2 > 0) {
            b.sizeHint(s2);
        }
        b.addAll(coll);
        return ArraySeq$.MODULE$.make(b.result());
    }

    @Override
    public Builder<T, ArraySeq<T>> newSpecificBuilder() {
        return ArraySeq$.MODULE$.newBuilder(this.elemTag());
    }

    @Override
    public ArraySeq<T> empty() {
        return ArraySeq$.MODULE$.empty(this.elemTag());
    }

    public abstract ClassTag<?> elemTag();

    @Override
    public abstract void update(int var1, T var2);

    public abstract Object array();

    @Override
    public abstract <S extends Stepper<?>> S stepper(StepperShape<T, S> var1);

    @Override
    public String className() {
        return "ArraySeq";
    }

    @Override
    public ArraySeq<T> clone() {
        return ArraySeq$.MODULE$.make(ScalaRunTime$.MODULE$.array_clone(this.array()));
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> int copyToArray(Object xs, int start2, int len) {
        void elemsToCopyToArray_destLen;
        int n = Array.getLength(xs);
        int elemsToCopyToArray_srcLen = this.length();
        int copied = Math.max(Math.min(Math.min(len, elemsToCopyToArray_srcLen), (int)(elemsToCopyToArray_destLen - start2)), 0);
        if (copied > 0) {
            Array$.MODULE$.copy(this.array(), 0, xs, start2, copied);
        }
        return copied;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ArraySeq) {
            ArraySeq arraySeq = (ArraySeq)other;
            if (Array.getLength(this.array()) != Array.getLength(arraySeq.array())) {
                return false;
            }
        }
        return Seq.equals$(this, other);
    }

    @Override
    public <B> ArraySeq<T> sorted(Ordering<B> ord) {
        return ArraySeq$.MODULE$.make(ArrayOps$.MODULE$.sorted$extension(this.array(), ord));
    }

    public <B> ArraySeq<T> sortInPlace(Ordering<B> ord) {
        if (this.length() > 1) {
            Object stableSort_a = this.array();
            Sorting$.MODULE$.stableSort(stableSort_a, 0, Array.getLength(stableSort_a), ord);
        }
        return this;
    }

    public static final class ofBoolean
    extends ArraySeq<Object> {
        private static final long serialVersionUID = 3L;
        private final boolean[] array;

        public boolean[] array() {
            return this.array;
        }

        public ManifestFactory.BooleanManifest elemTag() {
            return ClassTag$.MODULE$.Boolean();
        }

        @Override
        public int length() {
            return this.array().length;
        }

        @Override
        public boolean apply(int index) {
            return this.array()[index];
        }

        @Override
        public void update(int index, boolean elem) {
            this.array()[index] = elem;
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash$mZc$sp(this.array());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofBoolean) {
                ofBoolean ofBoolean2 = (ofBoolean)that;
                return Arrays.equals(this.array(), ofBoolean2.array());
            }
            return super.equals(that);
        }

        @Override
        public Iterator<Object> iterator() {
            return new ArrayOps$ArrayIterator$mcZ$sp(this.array());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
            return (S)new BoxedBooleanArrayStepper(this.array(), 0, this.array().length);
        }

        @Override
        public boolean apply$mcZI$sp(int index) {
            return this.array()[index];
        }

        public ofBoolean(boolean[] array) {
            this.array = array;
        }
    }

    public static final class ofByte
    extends ArraySeq<Object> {
        private static final long serialVersionUID = 3L;
        private final byte[] array;

        public byte[] array() {
            return this.array;
        }

        public ManifestFactory.ByteManifest elemTag() {
            return ClassTag$.MODULE$.Byte();
        }

        @Override
        public int length() {
            return this.array().length;
        }

        @Override
        public byte apply(int index) {
            return this.array()[index];
        }

        @Override
        public void update(int index, byte elem) {
            this.array()[index] = elem;
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash$mBc$sp(this.array());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofByte) {
                ofByte ofByte2 = (ofByte)that;
                return Arrays.equals(this.array(), ofByte2.array());
            }
            return super.equals(that);
        }

        @Override
        public Iterator<Object> iterator() {
            return new ArrayOps$ArrayIterator$mcB$sp(this.array());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
            Stepper.EfficientSplit efficientSplit;
            if (shape.shape() == StepperShape$.MODULE$.ReferenceShape()) {
                WidenedByteArrayStepper ofParIntStepper_st = new WidenedByteArrayStepper(this.array(), 0, this.array().length);
                efficientSplit = new Stepper.EfficientSplit(ofParIntStepper_st){};
                Object var2_2 = null;
            } else {
                efficientSplit = new WidenedByteArrayStepper(this.array(), 0, this.array().length);
            }
            return (S)efficientSplit;
        }

        public ofByte(byte[] array) {
            this.array = array;
        }
    }

    public static final class ofChar
    extends ArraySeq<Object> {
        private static final long serialVersionUID = 3L;
        private final char[] array;

        public char[] array() {
            return this.array;
        }

        public ManifestFactory.CharManifest elemTag() {
            return ClassTag$.MODULE$.Char();
        }

        @Override
        public int length() {
            return this.array().length;
        }

        @Override
        public char apply(int index) {
            return this.array()[index];
        }

        @Override
        public void update(int index, char elem) {
            this.array()[index] = elem;
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash$mCc$sp(this.array());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofChar) {
                ofChar ofChar2 = (ofChar)that;
                return Arrays.equals(this.array(), ofChar2.array());
            }
            return super.equals(that);
        }

        @Override
        public Iterator<Object> iterator() {
            return new ArrayOps$ArrayIterator$mcC$sp(this.array());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
            Stepper.EfficientSplit efficientSplit;
            if (shape.shape() == StepperShape$.MODULE$.ReferenceShape()) {
                WidenedCharArrayStepper ofParIntStepper_st = new WidenedCharArrayStepper(this.array(), 0, this.array().length);
                efficientSplit = new /* invalid duplicate definition of identical inner class */;
                Object var2_2 = null;
            } else {
                efficientSplit = new WidenedCharArrayStepper(this.array(), 0, this.array().length);
            }
            return (S)efficientSplit;
        }

        @Override
        public StringBuilder addString(StringBuilder sb, String start2, String sep, String end) {
            int len;
            java.lang.StringBuilder jsb = sb.underlying();
            if (start2.length() != 0) {
                jsb.append(start2);
            }
            if ((len = this.array().length) != 0) {
                if (sep.isEmpty()) {
                    jsb.append(this.array());
                } else {
                    jsb.ensureCapacity(jsb.length() + len + end.length() + (len - 1) * sep.length());
                    jsb.append(this.array()[0]);
                    for (int i = 1; i < len; ++i) {
                        jsb.append(sep);
                        jsb.append(this.array()[i]);
                    }
                }
            }
            if (end.length() != 0) {
                jsb.append(end);
            }
            return sb;
        }

        public ofChar(char[] array) {
            this.array = array;
        }
    }

    public static final class ofDouble
    extends ArraySeq<Object> {
        private static final long serialVersionUID = 3L;
        private final double[] array;

        public double[] array() {
            return this.array;
        }

        public ManifestFactory.DoubleManifest elemTag() {
            return ClassTag$.MODULE$.Double();
        }

        @Override
        public int length() {
            return this.array().length;
        }

        @Override
        public double apply(int index) {
            return this.array()[index];
        }

        @Override
        public void update(int index, double elem) {
            this.array()[index] = elem;
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash$mDc$sp(this.array());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofDouble) {
                ofDouble ofDouble2 = (ofDouble)that;
                return Arrays.equals(this.array(), ofDouble2.array());
            }
            return super.equals(that);
        }

        @Override
        public Iterator<Object> iterator() {
            return new ArrayOps$ArrayIterator$mcD$sp(this.array());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
            Stepper.EfficientSplit efficientSplit;
            if (shape.shape() == StepperShape$.MODULE$.ReferenceShape()) {
                DoubleArrayStepper ofParDoubleStepper_st = new DoubleArrayStepper(this.array(), 0, this.array().length);
                efficientSplit = new Stepper.EfficientSplit(ofParDoubleStepper_st){};
                Object var2_2 = null;
            } else {
                efficientSplit = new DoubleArrayStepper(this.array(), 0, this.array().length);
            }
            return (S)efficientSplit;
        }

        @Override
        public double apply$mcDI$sp(int index) {
            return this.array()[index];
        }

        public ofDouble(double[] array) {
            this.array = array;
        }
    }

    public static final class ofFloat
    extends ArraySeq<Object> {
        private static final long serialVersionUID = 3L;
        private final float[] array;

        public float[] array() {
            return this.array;
        }

        public ManifestFactory.FloatManifest elemTag() {
            return ClassTag$.MODULE$.Float();
        }

        @Override
        public int length() {
            return this.array().length;
        }

        @Override
        public float apply(int index) {
            return this.array()[index];
        }

        @Override
        public void update(int index, float elem) {
            this.array()[index] = elem;
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash$mFc$sp(this.array());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofFloat) {
                ofFloat ofFloat2 = (ofFloat)that;
                return Arrays.equals(this.array(), ofFloat2.array());
            }
            return super.equals(that);
        }

        @Override
        public Iterator<Object> iterator() {
            return new ArrayOps$ArrayIterator$mcF$sp(this.array());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
            Stepper.EfficientSplit efficientSplit;
            if (shape.shape() == StepperShape$.MODULE$.ReferenceShape()) {
                WidenedFloatArrayStepper ofParDoubleStepper_st = new WidenedFloatArrayStepper(this.array(), 0, this.array().length);
                efficientSplit = new /* invalid duplicate definition of identical inner class */;
                Object var2_2 = null;
            } else {
                efficientSplit = new WidenedFloatArrayStepper(this.array(), 0, this.array().length);
            }
            return (S)efficientSplit;
        }

        @Override
        public float apply$mcFI$sp(int index) {
            return this.array()[index];
        }

        public ofFloat(float[] array) {
            this.array = array;
        }
    }

    public static final class ofInt
    extends ArraySeq<Object> {
        private static final long serialVersionUID = 3L;
        private final int[] array;

        public int[] array() {
            return this.array;
        }

        public ManifestFactory.IntManifest elemTag() {
            return ClassTag$.MODULE$.Int();
        }

        @Override
        public int length() {
            return this.array().length;
        }

        @Override
        public int apply(int index) {
            return this.array()[index];
        }

        @Override
        public void update(int index, int elem) {
            this.array()[index] = elem;
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash$mIc$sp(this.array());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofInt) {
                ofInt ofInt2 = (ofInt)that;
                return Arrays.equals(this.array(), ofInt2.array());
            }
            return super.equals(that);
        }

        @Override
        public Iterator<Object> iterator() {
            return new ArrayOps$ArrayIterator$mcI$sp(this.array());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
            Stepper.EfficientSplit efficientSplit;
            if (shape.shape() == StepperShape$.MODULE$.ReferenceShape()) {
                IntArrayStepper ofParIntStepper_st = new IntArrayStepper(this.array(), 0, this.array().length);
                efficientSplit = new /* invalid duplicate definition of identical inner class */;
                Object var2_2 = null;
            } else {
                efficientSplit = new IntArrayStepper(this.array(), 0, this.array().length);
            }
            return (S)efficientSplit;
        }

        @Override
        public int apply$mcII$sp(int index) {
            return this.array()[index];
        }

        public ofInt(int[] array) {
            this.array = array;
        }
    }

    public static final class ofLong
    extends ArraySeq<Object> {
        private static final long serialVersionUID = 3L;
        private final long[] array;

        public long[] array() {
            return this.array;
        }

        public ManifestFactory.LongManifest elemTag() {
            return ClassTag$.MODULE$.Long();
        }

        @Override
        public int length() {
            return this.array().length;
        }

        @Override
        public long apply(int index) {
            return this.array()[index];
        }

        @Override
        public void update(int index, long elem) {
            this.array()[index] = elem;
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash$mJc$sp(this.array());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofLong) {
                ofLong ofLong2 = (ofLong)that;
                return Arrays.equals(this.array(), ofLong2.array());
            }
            return super.equals(that);
        }

        @Override
        public Iterator<Object> iterator() {
            return new ArrayOps$ArrayIterator$mcJ$sp(this.array());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
            Stepper.EfficientSplit efficientSplit;
            if (shape.shape() == StepperShape$.MODULE$.ReferenceShape()) {
                LongArrayStepper ofParLongStepper_st = new LongArrayStepper(this.array(), 0, this.array().length);
                efficientSplit = new Stepper.EfficientSplit(ofParLongStepper_st){};
                Object var2_2 = null;
            } else {
                efficientSplit = new LongArrayStepper(this.array(), 0, this.array().length);
            }
            return (S)efficientSplit;
        }

        @Override
        public long apply$mcJI$sp(int index) {
            return this.array()[index];
        }

        public ofLong(long[] array) {
            this.array = array;
        }
    }

    public static final class ofRef<T>
    extends ArraySeq<T> {
        private static final long serialVersionUID = 3L;
        private final T[] array;

        public T[] array() {
            return this.array;
        }

        @Override
        public ClassTag<T> elemTag() {
            return ClassTag$.MODULE$.apply(this.array().getClass().getComponentType());
        }

        @Override
        public int length() {
            return this.array().length;
        }

        @Override
        public T apply(int index) {
            return this.array()[index];
        }

        @Override
        public void update(int index, T elem) {
            this.array()[index] = elem;
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash(this.array());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofRef) {
                ofRef ofRef2 = (ofRef)that;
                return Array$.MODULE$.equals(this.array(), ofRef2.array());
            }
            return super.equals(that);
        }

        @Override
        public Iterator<T> iterator() {
            return new ArrayOps.ArrayIterator(this.array());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<T, S> shape) {
            return (S)(shape.shape() == StepperShape$.MODULE$.ReferenceShape() ? new ObjectArrayStepper<T>(this.array(), 0, this.array().length) : shape.parUnbox(new ObjectArrayStepper<T>(this.array(), 0, this.array().length)));
        }

        public ofRef(T[] array) {
            this.array = array;
        }
    }

    public static final class ofShort
    extends ArraySeq<Object> {
        private static final long serialVersionUID = 3L;
        private final short[] array;

        public short[] array() {
            return this.array;
        }

        public ManifestFactory.ShortManifest elemTag() {
            return ClassTag$.MODULE$.Short();
        }

        @Override
        public int length() {
            return this.array().length;
        }

        @Override
        public short apply(int index) {
            return this.array()[index];
        }

        @Override
        public void update(int index, short elem) {
            this.array()[index] = elem;
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash$mSc$sp(this.array());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofShort) {
                ofShort ofShort2 = (ofShort)that;
                return Arrays.equals(this.array(), ofShort2.array());
            }
            return super.equals(that);
        }

        @Override
        public Iterator<Object> iterator() {
            return new ArrayOps$ArrayIterator$mcS$sp(this.array());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
            Stepper.EfficientSplit efficientSplit;
            if (shape.shape() == StepperShape$.MODULE$.ReferenceShape()) {
                WidenedShortArrayStepper ofParIntStepper_st = new WidenedShortArrayStepper(this.array(), 0, this.array().length);
                efficientSplit = new /* invalid duplicate definition of identical inner class */;
                Object var2_2 = null;
            } else {
                efficientSplit = new WidenedShortArrayStepper(this.array(), 0, this.array().length);
            }
            return (S)efficientSplit;
        }

        public ofShort(short[] array) {
            this.array = array;
        }
    }

    public static final class ofUnit
    extends ArraySeq<BoxedUnit> {
        private static final long serialVersionUID = 3L;
        private final BoxedUnit[] array;

        public BoxedUnit[] array() {
            return this.array;
        }

        public ManifestFactory.UnitManifest elemTag() {
            return ClassTag$.MODULE$.Unit();
        }

        @Override
        public int length() {
            return this.array().length;
        }

        @Override
        public void apply(int index) {
            BoxedUnit cfr_ignored_0 = this.array()[index];
        }

        @Override
        public void update(int index, BoxedUnit elem) {
            this.array()[index] = elem;
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash$mVc$sp(this.array());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofUnit) {
                ofUnit ofUnit2 = (ofUnit)that;
                return this.array().length == ofUnit2.array().length;
            }
            return super.equals(that);
        }

        @Override
        public Iterator<BoxedUnit> iterator() {
            return new ArrayOps$ArrayIterator$mcV$sp(this.array());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<BoxedUnit, S> shape) {
            return (S)new ObjectArrayStepper<BoxedUnit>(this.array(), 0, this.array().length);
        }

        @Override
        public void apply$mcVI$sp(int index) {
            BoxedUnit cfr_ignored_0 = this.array()[index];
        }

        public ofUnit(BoxedUnit[] array) {
            this.array = array;
        }
    }
}

