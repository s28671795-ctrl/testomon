/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import java.util.NoSuchElementException;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterator;
import scala.collection.IndexedSeq;
import scala.collection.IndexedSeqView;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.Searching;
import scala.collection.Seq;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShape$;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StrictOptimizedSeqOps;
import scala.collection.convert.impl.AnyNumericRangeStepper;
import scala.collection.convert.impl.IntNumericRangeStepper;
import scala.collection.convert.impl.LongNumericRangeStepper;
import scala.collection.immutable.AbstractSeq;
import scala.collection.immutable.IndexedSeqOps;
import scala.collection.immutable.NumericRange$;
import scala.collection.mutable.Builder;
import scala.math.Integral;
import scala.math.Numeric;
import scala.math.Numeric$BigDecimalAsIfIntegral$;
import scala.math.Numeric$BigIntIsIntegral$;
import scala.math.Numeric$ByteIsIntegral$;
import scala.math.Numeric$CharIsIntegral$;
import scala.math.Numeric$IntIsIntegral$;
import scala.math.Numeric$LongIsIntegral$;
import scala.math.Numeric$ShortIsIntegral$;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.util.Either;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\u0011]a\u0001\u0002'N!QC!\"!\u0001\u0001\u0005\u000b\u0007I\u0011AA\u0002\u0011%\t)\u0001\u0001B\u0001B\u0003%!\f\u0003\u0006\u0002\b\u0001\u0011)\u0019!C\u0001\u0003\u0007A\u0011\"!\u0003\u0001\u0005\u0003\u0005\u000b\u0011\u0002.\t\u0015\u0005-\u0001A!b\u0001\n\u0003\t\u0019\u0001C\u0005\u0002\u000e\u0001\u0011\t\u0011)A\u00055\"Q\u0011q\u0002\u0001\u0003\u0006\u0004%\t!!\u0005\t\u0015\u0005e\u0001A!A!\u0002\u0013\t\u0019\u0002\u0003\u0006\u0002\u001c\u0001\u0011\t\u0011)A\u0006\u0003;Aq!a\t\u0001\t\u0003\t)\u0003C\u0004\u00026\u0001!\t%a\u000e\t\u000f\u0005}\u0002\u0001\"\u0011\u0002B!Q\u0011Q\u0010\u0001\t\u0006\u0004%\t%a \t\u0015\u0005\u001d\u0005\u0001#b\u0001\n\u0003\n\t\u0002C\u0004\u0002\n\u0002!\t%a\u0001\t\u000f\u0005-\u0005\u0001\"\u0011\u0002\u000e\"9\u0011q\u0012\u0001\u0005B\u0005\r\u0001bBAI\u0001\u0011\u0005\u0013Q\u0012\u0005\b\u0003'\u0003A\u0011AAK\u0011\u001d\tY\n\u0001C\u0001\u0003;Cq!!*\u0001\t\u0003\t9\u000bC\u0004\u0002t\u0002!\t%!>\t\u000f\tU\u0002\u0001\"\u0003\u00038!9!Q\b\u0001\u0005\n\t}\u0002b\u0002B#\u0001\u0011%!q\t\u0005\b\u0007+\u0003A\u0011IBL\u0011\u001d\u0019Y\n\u0001C!\u0007;Cqa!)\u0001\t\u0003\u001a\u0019\u000bC\u0004\u0004.\u0002!\t%!$\t\u000f\r=\u0006\u0001\"\u0011\u00042\"91Q\u0019\u0001\u0005B\r\u001d\u0007bBBj\u0001\u0011\u00051Q\u001b\u0005\b\u00077\u0004A\u0011IBo\u0011\u001d\u0019I\u000f\u0001C!\u0007WD!ba?\u0001\u0011\u000b\u0007I\u0011IA@\u0011\u001d\u0019i\u0010\u0001C+\u0003\u007fBqaa@\u0001\t\u0003\"\t\u0001C\u0004\u0005\b\u0001!\t\u0005\"\u0003\t\u0011\u0011-\u0001\u0001)C)\t\u001b9qA!\u0014N\u0011\u0003\u0011yE\u0002\u0004M\u001b\"\u0005!\u0011\u000b\u0005\b\u0003GIC\u0011\u0001B4\u0011\u001d\u0011I'\u000bC\u0005\u0005WBqAa *\t\u0003\u0011\tI\u0002\u0004\u0003\u0018&\u0002!\u0011\u0014\u0005\f\u0003\u0003i#\u0011!Q\u0001\n\t}\u0015\u0001C\u0006\u0002\b5\u0012\t\u0011)A\u0005\u0005?\u001b\u0001bCA\u0006[\t\u0005\t\u0015!\u0003\u0003 \u0016A!\"a\u0007.\u0005\u0003\u0005\u000b1\u0002BR\u0011\u001d\t\u0019#\fC\u0001\u0005KCq!a'.\t\u0003\u0012)\fC\u0004\u0003>6\"\tAa0\u0007\r\t\r\u0017\u0006\u0001Bc\u0011-\t\t!\u000eB\u0001B\u0003%!1Z\u0001\t\u0017\u0005\u001dQG!A!\u0002\u0013\u0011Ym\u0001\u0005\f\u0003\u0017)$\u0011!Q\u0001\n\t-W\u0001\u0003\u0006\u0002\u001cU\u0012\t\u0011)A\u0006\u0005\u001fDq!a\t6\t\u0003\u0011\t\u000eC\u0004\u0002\u001cV\"\tEa8\t\u000f\t\u001dX\u0007\"\u0001\u0003j\"9\u0011QU\u0015\u0005\u0002\tm\bb\u0002BtS\u0011\u00051\u0011\u0003\u0005\u000b\u0007OI#\u0019!C\u0001\u001f\u000e%\u0002\u0002CB#S\u0001\u0006Iaa\u000b\u0007\r\rU\u0013FBB,\u0011)\u0019)'\u0011B\u0001B\u0003%1q\r\u0005\u000b\u00037\t%\u0011!Q\u0001\n\r%\u0004bBA\u0012\u0003\u0012\u000511\u000e\u0005\t\u0007g\n\u0005\u0015)\u0003\u0002\u0014!A1QO!!B\u0013\u0019\t\u0007\u0003\u0005\u0004x\u0005\u0003\u000b\u0011BB1\u0011\u001d\u0019I(\u0011C!\u0003\u007fBqaa\u001fB\t\u0003\t\t\u0002C\u0004\u0004~\u0005#\taa \t\u0013\r\r\u0015&!A\u0005\n\r\u0015%\u0001\u0004(v[\u0016\u0014\u0018n\u0019*b]\u001e,'B\u0001(P\u0003%IW.\\;uC\ndWM\u0003\u0002Q#\u0006Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003I\u000bQa]2bY\u0006\u001c\u0001!\u0006\u0002V9N9\u0001A\u00164j[B$\bcA,Y56\tQ*\u0003\u0002Z\u001b\nY\u0011IY:ue\u0006\u001cGoU3r!\tYF\f\u0004\u0001\u0005\u000bu\u0003!\u0019\u00010\u0003\u0003Q\u000b\"aX2\u0011\u0005\u0001\fW\"A)\n\u0005\t\f&a\u0002(pi\"Lgn\u001a\t\u0003A\u0012L!!Z)\u0003\u0007\u0005s\u0017\u0010E\u0002XOjK!\u0001['\u0003\u0015%sG-\u001a=fIN+\u0017\u000fE\u0003XUjcg-\u0003\u0002l\u001b\ni\u0011J\u001c3fq\u0016$7+Z9PaN\u0004\"aV4\u0011\u000b]s'\f\u001c4\n\u0005=l%!F*ue&\u001cGo\u00149uS6L'0\u001a3TKF|\u0005o\u001d\t\u0005cJTF.D\u0001P\u0013\t\u0019xJA\fJi\u0016\u0014\u0018M\u00197f\r\u0006\u001cGo\u001c:z\t\u00164\u0017-\u001e7ugB\u0011Q/ \b\u0003mnt!a\u001e>\u000e\u0003aT!!_*\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0011\u0016B\u0001?R\u0003\u001d\u0001\u0018mY6bO\u0016L!A`@\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005q\f\u0016!B:uCJ$X#\u0001.\u0002\rM$\u0018M\u001d;!\u0003\r)g\u000eZ\u0001\u0005K:$\u0007%\u0001\u0003ti\u0016\u0004\u0018!B:uKB\u0004\u0013aC5t\u0013:\u001cG.^:jm\u0016,\"!a\u0005\u0011\u0007\u0001\f)\"C\u0002\u0002\u0018E\u0013qAQ8pY\u0016\fg.\u0001\u0007jg&s7\r\\;tSZ,\u0007%A\u0002ok6\u0004B!^A\u00105&\u0019\u0011\u0011E@\u0003\u0011%sG/Z4sC2\fa\u0001P5oSRtDCCA\u0014\u0003[\ty#!\r\u00024Q!\u0011\u0011FA\u0016!\r9\u0006A\u0017\u0005\b\u00037Q\u00019AA\u000f\u0011\u0019\t\tA\u0003a\u00015\"1\u0011q\u0001\u0006A\u0002iCa!a\u0003\u000b\u0001\u0004Q\u0006bBA\b\u0015\u0001\u0007\u00111C\u0001\tSR,'/\u0019;peV\u0011\u0011\u0011\b\t\u0005c\u0006m\",C\u0002\u0002>=\u0013\u0001\"\u0013;fe\u0006$xN]\u0001\bgR,\u0007\u000f]3s+\u0011\t\u0019%!\u0014\u0015\t\u0005\u0015\u00131\u000f\n\u0007\u0003\u000f\nY%!\u0019\u0007\r\u0005%\u0003\u0001AA#\u00051a$/\u001a4j]\u0016lWM\u001c;?!\rY\u0016Q\n\u0003\b\u0003\u001fb!\u0019AA)\u0005\u0005\u0019\u0016cA0\u0002TA\"\u0011QKA/!\u0015\t\u0018qKA.\u0013\r\tIf\u0014\u0002\b'R,\u0007\u000f]3s!\rY\u0016Q\f\u0003\f\u0003?\ni%!A\u0001\u0002\u000b\u0005aLA\u0002`IE\u0002B!a\u0019\u0002n9!\u0011QMA5\u001d\r1\u0018qM\u0005\u0003!FK1!a\u001bP\u0003\u001d\u0019F/\u001a9qKJLA!a\u001c\u0002r\tqQI\u001a4jG&,g\u000e^*qY&$(bAA6\u001f\"9\u0011Q\u000f\u0007A\u0004\u0005]\u0014!B:iCB,\u0007CB9\u0002zi\u000bY%C\u0002\u0002|=\u0013Ab\u0015;faB,'o\u00155ba\u0016\fa\u0001\\3oORDWCAAA!\r\u0001\u00171Q\u0005\u0004\u0003\u000b\u000b&aA%oi\u00069\u0011n]#naRL\u0018\u0001\u00027bgR\fA!\u001b8jiV\u0011\u0011\u0011F\u0001\u0005Q\u0016\fG-\u0001\u0003uC&d\u0017A\u00012z)\u0011\tI#a&\t\r\u0005e5\u00031\u0001[\u0003\u001dqWm^*uKB\fAaY8qsRA\u0011\u0011FAP\u0003C\u000b\u0019\u000b\u0003\u0004\u0002\u0002Q\u0001\rA\u0017\u0005\u0007\u0003\u000f!\u0002\u0019\u0001.\t\r\u0005-A\u00031\u0001[\u0003\u0015\t\u0007\u000f\u001d7z)\rQ\u0016\u0011\u0016\u0005\b\u0003W+\u0002\u0019AAA\u0003\rIG\r\u001f\u0015\u0006+\u0005=\u00161\u0018\t\u0006A\u0006E\u0016QW\u0005\u0004\u0003g\u000b&A\u0002;ie><8\u000fE\u0002v\u0003oK1!!/\u0000\u0005eIe\u000eZ3y\u001fV$xJ\u001a\"pk:$7/\u0012=dKB$\u0018n\u001c82\u000fy\ti,!4\u0002rB!\u0011qXAd\u001d\u0011\t\t-a1\u0011\u0005]\f\u0016bAAc#\u00061\u0001K]3eK\u001aLA!!3\u0002L\n11\u000b\u001e:j]\u001eT1!!2Rc%\u0019\u0013qZAk\u0003O\f9.\u0006\u0003\u0002R\u0006MWCAA_\t\u0019i6K1\u0001\u0002^&!\u0011q[Am\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%c)\u0019\u00111\\)\u0002\rQD'o\\<t#\ry\u0016q\u001c\t\u0005\u0003C\f\u0019O\u0004\u0002aw&\u0019\u0011Q]@\u0003\u0013QC'o\\<bE2,\u0017'C\u0012\u0002j\u0006-\u0018Q^An\u001d\r\u0001\u00171^\u0005\u0004\u00037\f\u0016'\u0002\u0012a#\u0006=(!B:dC2\f\u0017g\u0001\u0014\u00026\u00069am\u001c:fC\u000eDW\u0003BA|\u0005\u0017!B!!?\u0002\u0000B\u0019\u0001-a?\n\u0007\u0005u\u0018K\u0001\u0003V]&$\bb\u0002B\u0001-\u0001\u0007!1A\u0001\u0002MB1\u0001M!\u0002[\u0005\u0013I1Aa\u0002R\u0005%1UO\\2uS>t\u0017\u0007E\u0002\\\u0005\u0017!!B!\u0004\u0017A\u0003\u0005\tQ1\u0001_\u0005\u0005)\u0006F\u0002B\u0006\u0005#\u00119\u0002E\u0002a\u0005'I1A!\u0006R\u0005-\u0019\b/Z2jC2L'0\u001a32\u0013\r\u0012IB!\f\u00032\t=\u0002C\u0002B\u000e\u0005C\u00119CD\u0002a\u0005;I1Aa\bR\u00035\u0019\u0006/Z2jC2L'0\u00192mK&!!1\u0005B\u0013\u0005\u00159%o\\;q\u0015\r\u0011y\"\u0015\t\u0006A\n%\u0012\u0011`\u0005\u0004\u0005W\t&A\u0002+va2,\u0017'\u0003\u0003\u00030\t\u0015\u0012\u0001B+oSR\f\u0014b\tB\u000e\u0005;\u0011\u0019Da\b2\t\u00112(PU\u0001\u0013SN<\u0016\u000e\u001e5j]\n{WO\u001c3be&,7\u000f\u0006\u0003\u0002\u0014\te\u0002B\u0002B\u001e/\u0001\u0007!,\u0001\u0003fY\u0016l\u0017A\u00047pG\u0006$\u0018n\u001c8BMR,'O\u0014\u000b\u00045\n\u0005\u0003b\u0002B\"1\u0001\u0007\u0011\u0011Q\u0001\u0002]\u0006ia.Z<F[B$\u0018PU1oO\u0016$BA!\u0013\u0004\u0014B!!1J\u001b[\u001d\t9\u0006&\u0001\u0007Ok6,'/[2SC:<W\r\u0005\u0002XSM)\u0011Fa\u0015\u0003ZA\u0019\u0001M!\u0016\n\u0007\t]\u0013K\u0001\u0004B]f\u0014VM\u001a\t\u0005\u00057\u0012)'\u0004\u0002\u0003^)!!q\fB1\u0003\tIwN\u0003\u0002\u0003d\u0005!!.\u0019<b\u0013\rq(Q\f\u000b\u0003\u0005\u001f\n\u0001DY5h\t\u0016\u001c\u0017.\\1m\u0007\",7m[+oI\u0016\u0014h\r\\8x+\u0011\u0011iGa\u001e\u0015\u0011\t=$\u0011\u0010B>\u0005{\"B!!?\u0003r!9\u00111D\u0016A\u0004\tM\u0004#B;\u0002 \tU\u0004cA.\u0003x\u0011)Ql\u000bb\u0001=\"9\u0011\u0011A\u0016A\u0002\tU\u0004bBA\u0004W\u0001\u0007!Q\u000f\u0005\b\u0003\u0017Y\u0003\u0019\u0001B;\u0003\u0015\u0019w.\u001e8u+\u0011\u0011\u0019I!$\u0015\u0015\t\u0015%q\u0012BI\u0005'\u0013)\n\u0006\u0003\u0002\u0002\n\u001d\u0005bBA\u000eY\u0001\u000f!\u0011\u0012\t\u0006k\u0006}!1\u0012\t\u00047\n5E!B/-\u0005\u0004q\u0006bBA\u0001Y\u0001\u0007!1\u0012\u0005\b\u0003\u000fa\u0003\u0019\u0001BF\u0011\u001d\tY\u0001\fa\u0001\u0005\u0017Cq!a\u0004-\u0001\u0004\t\u0019BA\u0005J]\u000edWo]5wKV!!1\u0014BQ'\ri#Q\u0014\t\u0005/\u0002\u0011y\nE\u0002\\\u0005C#Q!X\u0017C\u0002y\u0003R!^A\u0010\u0005?#\u0002Ba*\u00030\nE&1\u0017\u000b\u0005\u0005S\u0013i\u000bE\u0003\u0003,6\u0012y*D\u0001*\u0011\u001d\tYB\ra\u0002\u0005GCq!!\u00013\u0001\u0004\u0011y\nC\u0004\u0002\bI\u0002\rAa(\t\u000f\u0005-!\u00071\u0001\u0003 RA!\u0011\u0016B\\\u0005s\u0013Y\fC\u0004\u0002\u0002M\u0002\rAa(\t\u000f\u0005\u001d1\u00071\u0001\u0003 \"9\u00111B\u001aA\u0002\t}\u0015!C3yG2,8/\u001b<f+\t\u0011\t\rE\u0003\u0003,V\u0012yJA\u0005Fq\u000edWo]5wKV!!q\u0019Bg'\r)$\u0011\u001a\t\u0005/\u0002\u0011Y\rE\u0002\\\u0005\u001b$Q!X\u001bC\u0002y\u0003R!^A\u0010\u0005\u0017$\u0002Ba5\u0003Z\nm'Q\u001c\u000b\u0005\u0005+\u00149\u000eE\u0003\u0003,V\u0012Y\rC\u0004\u0002\u001ci\u0002\u001dAa4\t\u000f\u0005\u0005!\b1\u0001\u0003L\"9\u0011q\u0001\u001eA\u0002\t-\u0007bBA\u0006u\u0001\u0007!1\u001a\u000b\t\u0005+\u0014\tOa9\u0003f\"9\u0011\u0011A\u001eA\u0002\t-\u0007bBA\u0004w\u0001\u0007!1\u001a\u0005\b\u0003\u0017Y\u0004\u0019\u0001Bf\u0003%Ign\u00197vg&4X-\u0006\u0002\u0003lB)!1V\u0017\u0003L\":QGa<\u0003v\n]\bc\u00011\u0003r&\u0019!1_)\u0003!M+'/[1m-\u0016\u00148/[8o+&#\u0015!\u0002<bYV,g$A\u0002)\u000f5\u0012yO!>\u0003xV!!Q`B\u0003)!\u0011ypa\u0003\u0004\u000e\r=A\u0003BB\u0001\u0007\u000f\u0001RAa+6\u0007\u0007\u00012aWB\u0003\t\u0015iVH1\u0001_\u0011\u001d\tY\"\u0010a\u0002\u0007\u0013\u0001R!^A\u0010\u0007\u0007Aq!!\u0001>\u0001\u0004\u0019\u0019\u0001C\u0004\u0002\bu\u0002\raa\u0001\t\u000f\u0005-Q\b1\u0001\u0004\u0004U!11CB\u000e)!\u0019)b!\t\u0004$\r\u0015B\u0003BB\f\u0007;\u0001RAa+.\u00073\u00012aWB\u000e\t\u0015ifH1\u0001_\u0011\u001d\tYB\u0010a\u0002\u0007?\u0001R!^A\u0010\u00073Aq!!\u0001?\u0001\u0004\u0019I\u0002C\u0004\u0002\by\u0002\ra!\u0007\t\u000f\u0005-a\b1\u0001\u0004\u001a\u0005yA-\u001a4bk2$xJ\u001d3fe&tw-\u0006\u0002\u0004,A9qk!\f\u00042\r\u001d\u0013bAB\u0018\u001b\n\u0019Q*\u001991\t\rM2\u0011\t\t\u0007\u0007k\u0019Yda\u0010\u000e\u0005\r]\"bAB\u001d#\u0006!Q.\u0019;i\u0013\u0011\u0019ida\u000e\u0003\u000f9+X.\u001a:jGB\u00191l!\u0011\u0005\u0015\r\r\u0003)!A\u0001\u0002\u000b\u0005aLA\u0002`II\n\u0001\u0003Z3gCVdGo\u0014:eKJLgn\u001a\u00111\t\r%3\u0011\u000b\t\u0007\u0007k\u0019Yea\u0014\n\t\r53q\u0007\u0002\t\u001fJ$WM]5oOB\u00191l!\u0015\u0005\u0015\rM\u0003)!A\u0001\u0002\u000b\u0005aLA\u0002`IM\u0012ACT;nKJL7MU1oO\u0016LE/\u001a:bi>\u0014X\u0003BB-\u0007G\u001aB!QB.iB)\u0011o!\u0018\u0004b%\u00191qL(\u0003!\u0005\u00137\u000f\u001e:bGRLE/\u001a:bi>\u0014\bcA.\u0004d\u0011)Q,\u0011b\u0001=\u0006!1/\u001a7g!\u00119\u0006a!\u0019\u0011\u000bU\fyb!\u0019\u0015\r\r54qNB9!\u0015\u0011Y+QB1\u0011\u001d\u0019)\u0007\u0012a\u0001\u0007OBq!a\u0007E\u0001\u0004\u0019I'\u0001\u0005`Q\u0006\u001ch*\u001a=u\u0003\u0015yf.\u001a=u\u0003-a\u0017m\u001d;FY\u0016lWM\u001c;\u0002\u0013-twn\u001e8TSj,\u0017a\u00025bg:+\u0007\u0010^\u0001\u0005]\u0016DH\u000f\u0006\u0002\u0004b!:\u0011Ia<\u0003v\n]\u0018\u0001D<sSR,'+\u001a9mC\u000e,GCABD!\u0011\u0019Iia$\u000e\u0005\r-%\u0002BBG\u0005C\nA\u0001\\1oO&!1\u0011SBF\u0005\u0019y%M[3di\"1!Q_\rA\u0002i\u000bA\u0001^1lKR!\u0011\u0011FBM\u0011\u001d\u0011\u0019E\u0007a\u0001\u0003\u0003\u000bA\u0001\u001a:paR!\u0011\u0011FBP\u0011\u001d\u0011\u0019e\u0007a\u0001\u0003\u0003\u000bqa\u001d9mSR\fE\u000f\u0006\u0003\u0004&\u000e-\u0006c\u00021\u0004(\u0006%\u0012\u0011F\u0005\u0004\u0007S\u000b&A\u0002+va2,'\u0007C\u0004\u0003Dq\u0001\r!!!\u0002\u000fI,g/\u001a:tK\u0006\u0019Q.\u001b8\u0016\t\rM6q\u0018\u000b\u00045\u000eU\u0006bBB\\=\u0001\u000f1\u0011X\u0001\u0004_J$\u0007#B;\u0004<\u000eu\u0016bAB'\u007fB\u00191la0\u0005\u000f\r\u0005gD1\u0001\u0004D\n\u0011A+M\t\u00035\u000e\f1!\\1y+\u0011\u0019Im!5\u0015\u0007i\u001bY\rC\u0004\u00048~\u0001\u001da!4\u0011\u000bU\u001cYla4\u0011\u0007m\u001b\t\u000eB\u0004\u0004B~\u0011\raa1\u0002\u001b\r|g\u000e^1j]N$\u0016\u0010]3e)\u0011\t\u0019ba6\t\r\re\u0007\u00051\u0001[\u0003\u0005A\u0018\u0001C2p]R\f\u0017N\\:\u0016\t\r}7Q\u001d\u000b\u0005\u0003'\u0019\t\u000fC\u0004\u0004Z\u0006\u0002\raa9\u0011\u0007m\u001b)\u000fB\u0004\u0004h\u0006\u0012\raa1\u0003\u0005\u0005\u000b\u0014aA:v[V!1Q^By)\u0011\u0019yo!>\u0011\u0007m\u001b\t\u0010B\u0004\u0004t\n\u0012\raa1\u0003\u0003\tCq!a\u0007#\u0001\b\u00199\u0010E\u0003v\u0007s\u001cy/C\u0002\u0004>}\f\u0001\u0002[1tQ\u000e{G-Z\u0001\u0018CB\u0004H.\u001f)sK\u001a,'O]3e\u001b\u0006DH*\u001a8hi\"\fa!Z9vC2\u001cH\u0003BA\n\t\u0007Aa\u0001\"\u0002&\u0001\u0004\u0019\u0017!B8uQ\u0016\u0014\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005u\u0016!C2mCN\u001ch*Y7f+\t!y\u0001\u0005\u0003\u0004\n\u0012E\u0011\u0002BAe\u0007\u0017K3\u0001A\u001b.Q\u001d\u0001!q\u001eB{\u0005o\u0004")
public class NumericRange<T>
extends AbstractSeq<T>
implements scala.collection.immutable.IndexedSeq<T>,
scala.collection.immutable.StrictOptimizedSeqOps<T, scala.collection.immutable.IndexedSeq, scala.collection.immutable.IndexedSeq<T>>,
Serializable {
    private static final long serialVersionUID = 3L;
    private int length;
    private boolean isEmpty;
    private int hashCode;
    private final T start;
    private final T end;
    private final T step;
    private final boolean isInclusive;
    public final Integral<T> scala$collection$immutable$NumericRange$$num;
    private volatile byte bitmap$0;

    public static <T> Inclusive<T> inclusive(T start2, T end, T step, Integral<T> num) {
        return new Inclusive<T>(start2, end, step, num);
    }

    @Override
    public /* synthetic */ Object scala$collection$immutable$StrictOptimizedSeqOps$$super$sorted(Ordering ord) {
        return SeqOps.sorted$(this, ord);
    }

    @Override
    public Object distinctBy(Function1 f) {
        return scala.collection.immutable.StrictOptimizedSeqOps.distinctBy$(this, f);
    }

    @Override
    public Object updated(int index, Object elem) {
        return scala.collection.immutable.StrictOptimizedSeqOps.updated$(this, index, elem);
    }

    @Override
    public Object patch(int from, IterableOnce other, int replaced) {
        return scala.collection.immutable.StrictOptimizedSeqOps.patch$(this, from, other, replaced);
    }

    @Override
    public Object sorted(Ordering ord) {
        return scala.collection.immutable.StrictOptimizedSeqOps.sorted$(this, ord);
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
    public Tuple2<scala.collection.immutable.IndexedSeq<T>, scala.collection.immutable.IndexedSeq<T>> partition(Function1<T, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<scala.collection.immutable.IndexedSeq<T>, scala.collection.immutable.IndexedSeq<T>> span(Function1<T, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<scala.collection.immutable.IndexedSeq<A1>, scala.collection.immutable.IndexedSeq<A2>> unzip(Function1<T, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<scala.collection.immutable.IndexedSeq<A1>, scala.collection.immutable.IndexedSeq<A2>, scala.collection.immutable.IndexedSeq<A3>> unzip3(Function1<T, Tuple3<A1, A2, A3>> asTriple) {
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
    public <A1, A2> Tuple2<scala.collection.immutable.IndexedSeq<A1>, scala.collection.immutable.IndexedSeq<A2>> partitionMap(Function1<T, Either<A1, A2>> f) {
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
    public /* synthetic */ boolean scala$collection$immutable$IndexedSeq$$super$canEqual(Object that) {
        return Seq.canEqual$(this, that);
    }

    @Override
    public /* synthetic */ boolean scala$collection$immutable$IndexedSeq$$super$sameElements(IterableOnce that) {
        return SeqOps.sameElements$(this, that);
    }

    @Override
    public final scala.collection.immutable.IndexedSeq<T> toIndexedSeq() {
        return scala.collection.immutable.IndexedSeq.toIndexedSeq$(this);
    }

    @Override
    public boolean canEqual(Object that) {
        return scala.collection.immutable.IndexedSeq.canEqual$(this, that);
    }

    @Override
    public <B> boolean sameElements(IterableOnce<B> o) {
        return scala.collection.immutable.IndexedSeq.sameElements$(this, o);
    }

    @Override
    public SeqFactory<scala.collection.immutable.IndexedSeq> iterableFactory() {
        return scala.collection.immutable.IndexedSeq.iterableFactory$(this);
    }

    @Override
    public /* synthetic */ Object scala$collection$immutable$IndexedSeqOps$$super$slice(int from, int until) {
        return scala.collection.IndexedSeqOps.slice$(this, from, until);
    }

    @Override
    public Object slice(int from, int until) {
        return IndexedSeqOps.slice$(this, from, until);
    }

    @Override
    public String stringPrefix() {
        return IndexedSeq.stringPrefix$(this);
    }

    @Override
    public Iterator<T> reverseIterator() {
        return scala.collection.IndexedSeqOps.reverseIterator$(this);
    }

    @Override
    public <B$> B$ foldRight(B$ z, Function2<T, B$, B$> op) {
        return (B$)scala.collection.IndexedSeqOps.foldRight$(this, z, op);
    }

    @Override
    public IndexedSeqView<T> view() {
        return scala.collection.IndexedSeqOps.view$(this);
    }

    @Override
    public IndexedSeqView<T> view(int from, int until) {
        return scala.collection.IndexedSeqOps.view$(this, from, until);
    }

    @Override
    public Iterable<T> reversed() {
        return scala.collection.IndexedSeqOps.reversed$(this);
    }

    @Override
    public Option<T> headOption() {
        return scala.collection.IndexedSeqOps.headOption$(this);
    }

    @Override
    public final int lengthCompare(int len) {
        return scala.collection.IndexedSeqOps.lengthCompare$((scala.collection.IndexedSeqOps)this, len);
    }

    @Override
    public int knownSize() {
        return scala.collection.IndexedSeqOps.knownSize$(this);
    }

    @Override
    public final int lengthCompare(Iterable<?> that) {
        return scala.collection.IndexedSeqOps.lengthCompare$((scala.collection.IndexedSeqOps)this, that);
    }

    @Override
    public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
        return scala.collection.IndexedSeqOps.search$(this, elem, ord);
    }

    @Override
    public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
        return scala.collection.IndexedSeqOps.search$(this, elem, from, to, ord);
    }

    public T start() {
        return this.start;
    }

    public T end() {
        return this.end;
    }

    public T step() {
        return this.step;
    }

    public boolean isInclusive() {
        return this.isInclusive;
    }

    @Override
    public Iterator<T> iterator() {
        return new NumericRangeIterator<T>(this, this.scala$collection$immutable$NumericRange$$num);
    }

    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<T, S> shape) {
        int n = shape.shape();
        if (StepperShape$.MODULE$.IntShape() == n) {
            return (S)new IntNumericRangeStepper(this, 0, this.length());
        }
        if (StepperShape$.MODULE$.LongShape() == n) {
            return (S)new LongNumericRangeStepper(this, 0, this.length());
        }
        return shape.parUnbox(new AnyNumericRangeStepper(this, 0, this.length()));
    }

    private int length$lzycompute() {
        synchronized (this) {
            if ((byte)(this.bitmap$0 & 1) == 0) {
                this.length = NumericRange$.MODULE$.count(this.start(), this.end(), this.step(), this.isInclusive(), this.scala$collection$immutable$NumericRange$$num);
                this.bitmap$0 = (byte)(this.bitmap$0 | 1);
            }
        }
        return this.length;
    }

    @Override
    public int length() {
        if ((byte)(this.bitmap$0 & 1) == 0) {
            return this.length$lzycompute();
        }
        return this.length;
    }

    private boolean isEmpty$lzycompute() {
        synchronized (this) {
            if ((byte)(this.bitmap$0 & 2) == 0) {
                this.isEmpty = this.scala$collection$immutable$NumericRange$$num.gt(this.start(), this.end()) && this.scala$collection$immutable$NumericRange$$num.gt(this.step(), this.scala$collection$immutable$NumericRange$$num.zero()) || this.scala$collection$immutable$NumericRange$$num.lt(this.start(), this.end()) && this.scala$collection$immutable$NumericRange$$num.lt(this.step(), this.scala$collection$immutable$NumericRange$$num.zero()) || this.scala$collection$immutable$NumericRange$$num.equiv(this.start(), this.end()) && !this.isInclusive();
                this.bitmap$0 = (byte)(this.bitmap$0 | 2);
            }
        }
        return this.isEmpty;
    }

    @Override
    public boolean isEmpty() {
        if ((byte)(this.bitmap$0 & 2) == 0) {
            return this.isEmpty$lzycompute();
        }
        return this.isEmpty;
    }

    @Override
    public T last() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("head of empty list");
        }
        return this.locationAfterN(this.length() - 1);
    }

    @Override
    public NumericRange<T> init() {
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("init of empty list");
        }
        return new NumericRange<T>(this.start(), this.scala$collection$immutable$NumericRange$$num.mkNumericOps(this.end()).$minus(this.step()), this.step(), this.isInclusive(), this.scala$collection$immutable$NumericRange$$num);
    }

    @Override
    public T head() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("head of empty list");
        }
        return this.start();
    }

    @Override
    public NumericRange<T> tail() {
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("tail of empty list");
        }
        if (this.isInclusive()) {
            return new Inclusive(this.scala$collection$immutable$NumericRange$$num.mkNumericOps(this.start()).$plus(this.step()), this.end(), this.step(), this.scala$collection$immutable$NumericRange$$num);
        }
        return new Exclusive(this.scala$collection$immutable$NumericRange$$num.mkNumericOps(this.start()).$plus(this.step()), this.end(), this.step(), this.scala$collection$immutable$NumericRange$$num);
    }

    public NumericRange<T> by(T newStep) {
        return this.copy(this.start(), this.end(), newStep);
    }

    public NumericRange<T> copy(T start2, T end, T step) {
        return new NumericRange<T>(start2, end, step, this.isInclusive(), this.scala$collection$immutable$NumericRange$$num);
    }

    @Override
    public T apply(int idx) throws IndexOutOfBoundsException {
        if (idx < 0 || idx >= this.length()) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(idx).append(" is out of bounds (min 0, max ").append(this.length() - 1).append(")").toString());
        }
        return this.locationAfterN(idx);
    }

    @Override
    public <U> void foreach(Function1<T, U> f) {
        T current = this.start();
        for (int count = 0; count < this.length(); ++count) {
            f.apply(current);
            current = this.scala$collection$immutable$NumericRange$$num.mkNumericOps(current).$plus(this.step());
        }
    }

    private boolean isWithinBoundaries(T elem) {
        return !this.isEmpty() && (this.scala$collection$immutable$NumericRange$$num.mkOrderingOps(this.step()).$greater(this.scala$collection$immutable$NumericRange$$num.zero()) && this.scala$collection$immutable$NumericRange$$num.mkOrderingOps(this.start()).$less$eq(elem) && this.scala$collection$immutable$NumericRange$$num.mkOrderingOps(elem).$less$eq(this.last()) || this.scala$collection$immutable$NumericRange$$num.mkOrderingOps(this.step()).$less(this.scala$collection$immutable$NumericRange$$num.zero()) && this.scala$collection$immutable$NumericRange$$num.mkOrderingOps(this.last()).$less$eq(elem) && this.scala$collection$immutable$NumericRange$$num.mkOrderingOps(elem).$less$eq(this.start()));
    }

    private T locationAfterN(int n) {
        return this.scala$collection$immutable$NumericRange$$num.mkNumericOps(this.start()).$plus(this.scala$collection$immutable$NumericRange$$num.mkNumericOps(this.step()).$times(this.scala$collection$immutable$NumericRange$$num.fromInt(n)));
    }

    private Exclusive<T> newEmptyRange(T value) {
        Integral<T> apply_num = this.scala$collection$immutable$NumericRange$$num;
        T apply_step = this.step();
        return new Exclusive<T>(value, value, apply_step, apply_num);
    }

    @Override
    public NumericRange<T> take(int n) {
        if (n <= 0 || this.isEmpty()) {
            return this.newEmptyRange(this.start());
        }
        if (n >= this.length()) {
            return this;
        }
        return new Inclusive<T>(this.start(), this.locationAfterN(n - 1), this.step(), this.scala$collection$immutable$NumericRange$$num);
    }

    @Override
    public NumericRange<T> drop(int n) {
        if (n <= 0 || this.isEmpty()) {
            return this;
        }
        if (n >= this.length()) {
            return this.newEmptyRange(this.end());
        }
        return this.copy(this.locationAfterN(n), this.end(), this.step());
    }

    @Override
    public Tuple2<NumericRange<T>, NumericRange<T>> splitAt(int n) {
        return new Tuple2<Object, Object>(this.take(n), this.drop(n));
    }

    @Override
    public NumericRange<T> reverse() {
        if (this.isEmpty()) {
            return this;
        }
        Object newStep = this.scala$collection$immutable$NumericRange$$num.mkNumericOps(this.step()).unary_$minus();
        if (BoxesRunTime.equals(this.scala$collection$immutable$NumericRange$$num.sign(newStep), this.scala$collection$immutable$NumericRange$$num.sign(this.step()))) {
            throw new ArithmeticException("number type is unsigned, and .reverse requires a negative step");
        }
        return new Inclusive<T>(this.last(), this.start(), newStep, this.scala$collection$immutable$NumericRange$$num);
    }

    @Override
    public <T1> T min(Ordering<T1> ord) {
        block6: {
            block5: {
                Ordering ordering;
                if (ord == this.scala$collection$immutable$NumericRange$$num) break block5;
                Option option = NumericRange$.MODULE$.defaultOrdering().get(this.scala$collection$immutable$NumericRange$$num);
                if (option == null) {
                    throw null;
                }
                Option exists_this = option;
                boolean bl = !exists_this.isEmpty() && NumericRange.$anonfun$min$1(ord, ordering = (Ordering)exists_this.get());
                Object var2_2 = null;
                if (!bl) break block6;
            }
            if (this.scala$collection$immutable$NumericRange$$num.mkOrderingOps(this.scala$collection$immutable$NumericRange$$num.sign(this.step())).$greater(this.scala$collection$immutable$NumericRange$$num.zero())) {
                return this.head();
            }
            return this.last();
        }
        return (T)IterableOnceOps.min$(this, ord);
    }

    @Override
    public <T1> T max(Ordering<T1> ord) {
        block6: {
            block5: {
                Ordering ordering;
                if (ord == this.scala$collection$immutable$NumericRange$$num) break block5;
                Option option = NumericRange$.MODULE$.defaultOrdering().get(this.scala$collection$immutable$NumericRange$$num);
                if (option == null) {
                    throw null;
                }
                Option exists_this = option;
                boolean bl = !exists_this.isEmpty() && NumericRange.$anonfun$max$1(ord, ordering = (Ordering)exists_this.get());
                Object var2_2 = null;
                if (!bl) break block6;
            }
            if (this.scala$collection$immutable$NumericRange$$num.mkOrderingOps(this.scala$collection$immutable$NumericRange$$num.sign(this.step())).$greater(this.scala$collection$immutable$NumericRange$$num.zero())) {
                return this.last();
            }
            return this.head();
        }
        return (T)IterableOnceOps.max$(this, ord);
    }

    public boolean containsTyped(T x) {
        return this.isWithinBoundaries(x) && BoxesRunTime.equals(this.scala$collection$immutable$NumericRange$$num.mkNumericOps(this.scala$collection$immutable$NumericRange$$num.mkNumericOps(x).$minus(this.start())).$percent(this.step()), this.scala$collection$immutable$NumericRange$$num.zero());
    }

    @Override
    public <A1> boolean contains(A1 x) {
        try {
            return this.containsTyped(x);
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    @Override
    public <B> B sum(Numeric<B> num) {
        if (this.isEmpty()) {
            return num.zero();
        }
        if (this.length() == 1) {
            return (B)this.head();
        }
        if (num == Numeric$IntIsIntegral$.MODULE$ || num == Numeric$ShortIsIntegral$.MODULE$ || num == Numeric$ByteIsIntegral$.MODULE$ || num == Numeric$CharIsIntegral$.MODULE$) {
            long exact = (long)this.length() * (num.toLong(this.head()) + (long)num.toInt(this.last())) / 2L;
            return num.fromInt((int)exact);
        }
        if (num == Numeric$LongIsIntegral$.MODULE$) {
            long l;
            long a = this.scala$collection$immutable$NumericRange$$num.mkNumericOps(this.head()).toLong();
            long b = this.scala$collection$immutable$NumericRange$$num.mkNumericOps(this.last()).toLong();
            if ((this.length() & 1) == 0) {
                l = (long)(this.length() / 2) * (a + b);
            } else {
                long ha = a / 2L;
                long hb = b / 2L;
                l = (long)this.length() * (ha + hb + (a - 2L * ha + (b - 2L * hb)) / 2L);
            }
            return (B)Long.valueOf(l);
        }
        if (num == Numeric$BigIntIsIntegral$.MODULE$ || num == Numeric$BigDecimalAsIfIntegral$.MODULE$) {
            Integral numAsIntegral = (Integral)num;
            return (B)numAsIntegral.mkNumericOps(numAsIntegral.mkNumericOps(num.fromInt(this.length())).$times(numAsIntegral.mkNumericOps(this.head()).$plus(this.last()))).$div(num.fromInt(2));
        }
        if (this.isEmpty()) {
            return num.zero();
        }
        B acc = num.zero();
        T i = this.head();
        for (int idx = 0; idx < this.length(); ++idx) {
            acc = num.plus(acc, i);
            i = this.scala$collection$immutable$NumericRange$$num.mkNumericOps(i).$plus(this.step());
        }
        return acc;
    }

    private int hashCode$lzycompute() {
        synchronized (this) {
            if ((byte)(this.bitmap$0 & 4) == 0) {
                this.hashCode = MurmurHash3$.MODULE$.seqHash(this);
                this.bitmap$0 = (byte)(this.bitmap$0 | 4);
            }
        }
        return this.hashCode;
    }

    @Override
    public int hashCode() {
        if ((byte)(this.bitmap$0 & 4) == 0) {
            return this.hashCode$lzycompute();
        }
        return this.hashCode;
    }

    @Override
    public final int applyPreferredMaxLength() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof NumericRange) {
            NumericRange numericRange = (NumericRange)other;
            return numericRange.canEqual(this) && this.length() == numericRange.length() && (this.isEmpty() || BoxesRunTime.equals(this.start(), numericRange.start()) && BoxesRunTime.equals(this.last(), numericRange.last()));
        }
        return Seq.equals$(this, other);
    }

    @Override
    public String toString() {
        String empty = this.isEmpty() ? "empty " : "";
        String preposition = this.isInclusive() ? "to" : "until";
        String stepped = BoxesRunTime.equals(this.step(), 1) ? "" : new StringBuilder(4).append(" by ").append(this.step()).toString();
        return new StringBuilder(15).append(empty).append("NumericRange ").append(this.start()).append(" ").append(preposition).append(" ").append(this.end()).append(stepped).toString();
    }

    @Override
    public String className() {
        return "NumericRange";
    }

    public void foreach$mVc$sp(Function1<T, BoxedUnit> f) {
        T current = this.start();
        for (int count = 0; count < this.length(); ++count) {
            f.apply(current);
            current = this.scala$collection$immutable$NumericRange$$num.mkNumericOps(current).$plus(this.step());
        }
    }

    public static final /* synthetic */ boolean $anonfun$min$1(Ordering ord$1, Ordering x$1) {
        return ord$1 == x$1;
    }

    public static final /* synthetic */ boolean $anonfun$max$1(Ordering ord$2, Ordering x$2) {
        return ord$2 == x$2;
    }

    public NumericRange(T start2, T end, T step, boolean isInclusive, Integral<T> num) {
        this.start = start2;
        this.end = end;
        this.step = step;
        this.isInclusive = isInclusive;
        this.scala$collection$immutable$NumericRange$$num = num;
    }

    public static final /* synthetic */ Object $anonfun$min$1$adapted(Ordering ord$1, Ordering x$1) {
        return BoxesRunTime.boxToBoolean(NumericRange.$anonfun$min$1(ord$1, x$1));
    }

    public static final /* synthetic */ Object $anonfun$max$1$adapted(Ordering ord$2, Ordering x$2) {
        return BoxesRunTime.boxToBoolean(NumericRange.$anonfun$max$1(ord$2, x$2));
    }

    public static class Exclusive<T>
    extends NumericRange<T> {
        private static final long serialVersionUID = 3L;
        private final Integral<T> num;

        @Override
        public Exclusive<T> copy(T start2, T end, T step) {
            Integral<T> apply_num = this.num;
            return new Exclusive<T>(start2, end, step, apply_num);
        }

        /*
         * WARNING - void declaration
         */
        public Inclusive<T> inclusive() {
            void inclusive_num;
            void inclusive_step;
            Integral<T> integral = this.num;
            Object t2 = super.step();
            Object inclusive_end = super.end();
            Object inclusive_start = super.start();
            return new Inclusive<void>((void)inclusive_start, (void)inclusive_end, inclusive_step, (Integral<void>)inclusive_num);
        }

        public Exclusive(T start2, T end, T step, Integral<T> num) {
            this.num = num;
            super(start2, end, step, false, num);
        }
    }

    public static class Inclusive<T>
    extends NumericRange<T> {
        private static final long serialVersionUID = 3L;
        private final Integral<T> num;

        @Override
        public Inclusive<T> copy(T start2, T end, T step) {
            Integral<T> inclusive_num = this.num;
            return new Inclusive<T>(start2, end, step, inclusive_num);
        }

        /*
         * WARNING - void declaration
         */
        public Exclusive<T> exclusive() {
            void apply_num;
            void apply_step;
            Integral<T> integral = this.num;
            Object t2 = super.step();
            Object apply_end = super.end();
            Object apply_start = super.start();
            return new Exclusive<void>((void)apply_start, (void)apply_end, apply_step, (Integral<void>)apply_num);
        }

        public Inclusive(T start2, T end, T step, Integral<T> num) {
            this.num = num;
            super(start2, end, step, true, num);
        }
    }

    private static final class NumericRangeIterator<T>
    extends AbstractIterator<T>
    implements Serializable {
        private static final long serialVersionUID = 3L;
        private final NumericRange<T> self;
        private final Integral<T> num;
        private boolean _hasNext;
        private T _next;
        private final T lastElement;

        @Override
        public int knownSize() {
            if (this._hasNext) {
                return this.num.toInt(this.num.mkNumericOps(this.num.mkNumericOps(this.lastElement).$minus(this._next)).$div(this.self.step())) + 1;
            }
            return 0;
        }

        @Override
        public boolean hasNext() {
            return this._hasNext;
        }

        @Override
        public T next() {
            T value;
            if (!this._hasNext) {
                Iterator$.scala$collection$Iterator$$_empty.next();
            }
            this._hasNext = !BoxesRunTime.equals(value = this._next, this.lastElement);
            this._next = this.num.plus(value, this.self.step());
            return value;
        }

        public NumericRangeIterator(NumericRange<T> self, Integral<T> num) {
            this.self = self;
            this.num = num;
            this._hasNext = !self.isEmpty();
            this._next = self.start();
            this.lastElement = this._hasNext ? self.last() : self.start();
        }
    }
}

