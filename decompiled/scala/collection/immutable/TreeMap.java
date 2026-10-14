/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Map;
import scala.collection.SortedMapFactory;
import scala.collection.SortedMapFactoryDefaults;
import scala.collection.SortedMapOps;
import scala.collection.SortedOps;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShape$;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StrictOptimizedMapOps;
import scala.collection.convert.impl.AnyBinaryTreeStepper;
import scala.collection.convert.impl.BinaryTreeStepper$;
import scala.collection.convert.impl.DoubleBinaryTreeStepper;
import scala.collection.convert.impl.IntBinaryTreeStepper;
import scala.collection.convert.impl.LongBinaryTreeStepper;
import scala.collection.generic.DefaultSerializable;
import scala.collection.immutable.AbstractMap;
import scala.collection.immutable.LinearSeq;
import scala.collection.immutable.MapOps;
import scala.collection.immutable.RedBlackTree;
import scala.collection.immutable.RedBlackTree$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.SortedMap;
import scala.collection.immutable.SortedMapOps;
import scala.collection.immutable.StrictOptimizedSortedMapOps;
import scala.collection.immutable.TreeMap$;
import scala.collection.immutable.TreeMap$TreeMapBuilder$adder$;
import scala.collection.immutable.TreeSet;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.ReusableBuilder;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.util.Either;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@ScalaSignature(bytes="\u0006\u0005\u0011}g\u0001\u0002,X\u0005yC!\"a\u0006\u0001\u0005\u000b\u0007I\u0011BA\r\u0011)\tI\u0003\u0001B\u0001B\u0003%\u00111\u0004\u0005\u000b\u0003W\u0001!Q1A\u0005\u0004\u00055\u0002BCA\u001f\u0001\t\u0005\t\u0015!\u0003\u00020!9\u0011q\b\u0001\u0005\n\u0005\u0005\u0003bBA \u0001\u0011\u0005\u0011\u0011\n\u0005\t\u0003\u001f\u0002A\u0011A,\u0002\u001a!A\u0011\u0011\u000b\u0001!\n\u0013\t\u0019\u0006C\u0004\u0002h\u0001!\t%!\u001b\t\u000f\u0005E\u0004\u0001\"\u0001\u0002t!9\u0011\u0011\u0011\u0001\u0005\u0002\u0005\r\u0005bBAF\u0001\u0011\u0005\u0013Q\u0012\u0005\b\u0003+\u0003A\u0011AAL\u0011\u001d\tY\n\u0001C!\u0003;Cq!a)\u0001\t\u0003\n)\u000bC\u0004\u0002l\u0002!\t%!<\t\u000f\t%\u0001\u0001\"\u0011\u0003\f!9!q\u0005\u0001\u0005\u0002\t%\u0002b\u0002B\u001b\u0001\u0011\u0005#q\u0007\u0005\b\u0005\u0017\u0002A\u0011\u0001B'\u0011\u001d\u0011\t\u0006\u0001C\u0001\u0005'BqAa\u0019\u0001\t\u0003\u0012)\u0007C\u0004\u0003|\u0001!\tE! \t\u000f\t\u0015\u0005\u0001\"\u0001\u0003\b\"9!\u0011\u0016\u0001\u0005\u0002\t-\u0006b\u0002B\\\u0001\u0011\u0005#\u0011\u0018\u0005\b\u0005\u007f\u0003A\u0011\tBa\u0011\u001d\u0011)\r\u0001C!\u0005\u000fDqA!4\u0001\t\u0003\u0012y\rC\u0004\u0003j\u0002!\tEa;\t\u000f\tm\b\u0001\"\u0011\u0003~\"91Q\u0001\u0001\u0005B\tu\bbBB\u0004\u0001\u0011\u00053\u0011\u0002\u0005\b\u0007#\u0001A\u0011IB\n\u0011\u001d\u0019)\u0002\u0001C!\u0007'Aqaa\u0006\u0001\t\u0003\u001aI\u0002C\u0004\u0004\u001c\u0001!\te!\u0007\t\u000f\ru\u0001\u0001\"\u0011\u0004 !91\u0011\u0005\u0001\u0005B\r}\u0001bBB\u0012\u0001\u0011\u00053Q\u0005\u0005\b\u0007W\u0001A\u0011IB\u0017\u0011\u001d\u0019\t\u0004\u0001C!\u0007gAqa!\u000f\u0001\t\u0003\u001aY\u0004C\u0004\u0004@\u0001!\te!\u0011\t\u0011\r\u0015\u0003\u0001)C\u0005\u0007\u000fBqaa\u0014\u0001\t\u0003\u001a\t\u0006C\u0004\u0004V\u0001!\tea\u0016\t\u000f\rm\u0003\u0001\"\u0011\u0004^!911\r\u0001\u0005B\r\u0015\u0004bBB5\u0001\u0011\u000531\u000e\u0005\b\u0007_\u0002A\u0011IB9\r\u0019\u0019\t\t\u0001\u0004\u0004\u0004\"9\u0011q\b\u001b\u0005\u0002\r]\u0005\"CBOi\u0001\u0007I\u0011BBP\u0011%\u0019\u0019\u000b\u000ea\u0001\n\u0013\u0019)\u000b\u0003\u0005\u0004,R\u0002\u000b\u0015BBQ\u0011\u001d\u0019i\u000b\u000eC\u0001\u0007?Cqaa,5\t\u0003\u001a\t\fC\u0004\u00048R\"\ta!/\t\u000f\rM\u0007\u0001\"\u0011\u0004V\"A11\u001c\u0001!\n#\u001ainB\u0004\u0004p^C\ta!=\u0007\rY;\u0006\u0012ABz\u0011\u001d\tyd\u0010C\u0001\u0007wDqa!@@\t\u0003\u0019y\u0010C\u0004\u00030~\"\t\u0001b\u0005\t\u000f\u0011=r\b\"\u0001\u00052\u00191A\u0011K \u0005\t'BA\"a\u000bE\u0005\u0003\u0005\u000b1\u0002C4\tSBq!a\u0010E\t\u0003!y'\u0002\u0004\u0002&\u0011\u0003A\u0011\u0010\u0005\n\u0003/!\u0005\u0019!C\u0005\twB\u0011\u0002\"!E\u0001\u0004%I\u0001b!\t\u0011\u0005%B\t)Q\u0005\t{Bq\u0001b\"E\t\u0003!IiB\u0004\u0005\u0010\u0012CI\u0001\"%\u0007\u000f\u0011ME\t#\u0003\u0005\u0016\"9\u0011qH'\u0005\u0002\u0011\r\u0006\u0002\u0003CS\u001b\u0002\u0006K\u0001\" \t\u000f\u0011\u001dV\n\"\u0001\u0005*\"91qV'\u0005B\u0011M\u0006bBB\\\t\u0012\u0005C\u0011\u0018\u0005\b\t\u0003$E\u0011\tCb\u0011\u001d!)\r\u0012C!\t\u000fD\u0011\u0002\"3@\u0003\u0003%I\u0001b3\u0003\u000fQ\u0013X-Z'ba*\u0011\u0001,W\u0001\nS6lW\u000f^1cY\u0016T!AW.\u0002\u0015\r|G\u000e\\3di&|gNC\u0001]\u0003\u0015\u00198-\u00197b\u0007\u0001)2a\u00184r'\u001d\u0001\u0001m\u001d<|\u0003\u0017\u0001B!\u00192ea6\tq+\u0003\u0002d/\nY\u0011IY:ue\u0006\u001cG/T1q!\t)g\r\u0004\u0001\u0005\u000b\u001d\u0004!\u0019\u00015\u0003\u0003-\u000b\"![7\u0011\u0005)\\W\"A.\n\u00051\\&a\u0002(pi\"Lgn\u001a\t\u0003U:L!a\\.\u0003\u0007\u0005s\u0017\u0010\u0005\u0002fc\u00121!\u000f\u0001CC\u0002!\u0014\u0011A\u0016\t\u0005CR$\u0007/\u0003\u0002v/\nI1k\u001c:uK\u0012l\u0015\r\u001d\t\u0007C^$\u0007/\u001f>\n\u0005a<&aG*ue&\u001cGo\u00149uS6L'0\u001a3T_J$X\rZ'ba>\u00038\u000f\u0005\u0002b\u0001A!\u0011\r\u00013q!!aX\u0010\u001a9z\u007f\u0006\u0015Q\"A-\n\u0005yL&\u0001G*peR,G-T1q\r\u0006\u001cGo\u001c:z\t\u00164\u0017-\u001e7ugB\u0019\u0011-!\u0001\n\u0007\u0005\rqK\u0001\u0005Ji\u0016\u0014\u0018M\u00197f!\r\t\u0017qA\u0005\u0004\u0003\u00139&aA'baB!\u0011QBA\n\u001b\t\tyAC\u0002\u0002\u0012e\u000bqaZ3oKJL7-\u0003\u0003\u0002\u0016\u0005=!a\u0005#fM\u0006,H\u000e^*fe&\fG.\u001b>bE2,\u0017\u0001\u0002;sK\u0016,\"!a\u0007\u0011\r\u0005u\u00111\u00053q\u001d\r\t\u0017qD\u0005\u0004\u0003C9\u0016\u0001\u0004*fI\nc\u0017mY6Ue\u0016,\u0017\u0002BA\u0013\u0003O\u0011A\u0001\u0016:fK*\u0019\u0011\u0011E,\u0002\u000bQ\u0014X-\u001a\u0011\u0002\u0011=\u0014H-\u001a:j]\u001e,\"!a\f\u0011\u000b\u0005E\u0012q\u00073\u000f\u0007)\f\u0019$C\u0002\u00026m\u000bq\u0001]1dW\u0006<W-\u0003\u0003\u0002:\u0005m\"\u0001C(sI\u0016\u0014\u0018N\\4\u000b\u0007\u0005U2,A\u0005pe\u0012,'/\u001b8hA\u00051A(\u001b8jiz\"B!a\u0011\u0002HQ\u0019!0!\u0012\t\u000f\u0005-R\u0001q\u0001\u00020!9\u0011qC\u0003A\u0002\u0005mACAA&)\rQ\u0018Q\n\u0005\b\u0003W1\u00019AA\u0018\u0003\u0015!(/Z31\u00031qWm^'ba>\u00138+\u001a7g+\u0011\t)&a\u0017\u0015\t\u0005]\u0013\u0011\r\t\u0006C\u0002!\u0017\u0011\f\t\u0004K\u0006mCaBA/\u0011\t\u0007\u0011q\f\u0002\u0003-F\n\"\u0001]7\t\u000f\u0005\r\u0004\u00021\u0001\u0002f\u0005\tA\u000fE\u0004\u0002\u001e\u0005\rB-!\u0017\u0002!M|'\u000f^3e\u001b\u0006\u0004h)Y2u_JLXCAA6!\u0011a\u0018QN=\n\u0007\u0005=\u0014L\u0001\tT_J$X\rZ'ba\u001a\u000b7\r^8ss\u0006A\u0011\u000e^3sCR|'/\u0006\u0002\u0002vA)A0a\u001e\u0002|%\u0019\u0011\u0011P-\u0003\u0011%#XM]1u_J\u0004RA[A?IBL1!a \\\u0005\u0019!V\u000f\u001d7fe\u0005\u00012.Z=t\u0013R,'/\u0019;pe\u001a\u0013x.\u001c\u000b\u0005\u0003\u000b\u000b9\t\u0005\u0003}\u0003o\"\u0007BBAE\u0017\u0001\u0007A-A\u0003ti\u0006\u0014H/\u0001\u0004lKf\u001cV\r^\u000b\u0003\u0003\u001f\u0003B!YAII&\u0019\u00111S,\u0003\u000fQ\u0013X-Z*fi\u0006a\u0011\u000e^3sCR|'O\u0012:p[R!\u0011QOAM\u0011\u0019\tI)\u0004a\u0001I\u0006\u0011b/\u00197vKNLE/\u001a:bi>\u0014hI]8n)\u0011\ty*!)\u0011\tq\f9\b\u001d\u0005\u0007\u0003\u0013s\u0001\u0019\u00013\u0002\u000fM$X\r\u001d9feV!\u0011qUAY)\u0011\tI+!9\u0013\r\u0005-\u0016qVAc\r\u0019\ti\u000b\u0001\u0001\u0002*\naAH]3gS:,W.\u001a8u}A\u0019Q-!-\u0005\u000f\u0005MvB1\u0001\u00026\n\t1+E\u0002j\u0003o\u0003D!!/\u0002BB)A0a/\u0002@&\u0019\u0011QX-\u0003\u000fM#X\r\u001d9feB\u0019Q-!1\u0005\u0017\u0005\r\u0017\u0011WA\u0001\u0002\u0003\u0015\t\u0001\u001b\u0002\u0004?\u0012\n\u0004\u0003BAd\u00037tA!!3\u0002X:!\u00111ZAk\u001d\u0011\ti-a5\u000e\u0005\u0005='bAAi;\u00061AH]8pizJ\u0011\u0001X\u0005\u00035nK1!!7Z\u0003\u001d\u0019F/\u001a9qKJLA!!8\u0002`\nqQI\u001a4jG&,g\u000e^*qY&$(bAAm3\"9\u00111]\bA\u0004\u0005\u0015\u0018!B:iCB,\u0007c\u0002?\u0002h\u0006m\u0014qV\u0005\u0004\u0003SL&\u0001D*uKB\u0004XM]*iCB,\u0017AC6fsN#X\r\u001d9feV!\u0011q^A|)\u0011\t\tP!\u0002\u0013\r\u0005M\u0018Q_Ac\r\u0019\ti\u000b\u0001\u0001\u0002rB\u0019Q-a>\u0005\u000f\u0005M\u0006C1\u0001\u0002zF\u0019\u0011.a?1\t\u0005u(\u0011\u0001\t\u0006y\u0006m\u0016q \t\u0004K\n\u0005Aa\u0003B\u0002\u0003o\f\t\u0011!A\u0003\u0002!\u00141a\u0018\u00133\u0011\u001d\t\u0019\u000f\u0005a\u0002\u0005\u000f\u0001b\u0001`AtI\u0006U\u0018\u0001\u0004<bYV,7\u000b^3qa\u0016\u0014X\u0003\u0002B\u0007\u0005+!BAa\u0004\u0003$I1!\u0011\u0003B\n\u0003\u000b4a!!,\u0001\u0001\t=\u0001cA3\u0003\u0016\u00119\u00111W\tC\u0002\t]\u0011cA5\u0003\u001aA\"!1\u0004B\u0010!\u0015a\u00181\u0018B\u000f!\r)'q\u0004\u0003\f\u0005C\u0011)\"!A\u0001\u0002\u000b\u0005\u0001NA\u0002`IMBq!a9\u0012\u0001\b\u0011)\u0003\u0005\u0004}\u0003O\u0004(1C\u0001\u0004O\u0016$H\u0003\u0002B\u0016\u0005c\u0001BA\u001bB\u0017a&\u0019!qF.\u0003\r=\u0003H/[8o\u0011\u0019\u0011\u0019D\u0005a\u0001I\u0006\u00191.Z=\u0002\u0013\u001d,Go\u0014:FYN,W\u0003\u0002B\u001d\u0005{!bAa\u000f\u0003@\t\u0005\u0003cA3\u0003>\u00119\u0011QL\nC\u0002\u0005}\u0003B\u0002B\u001a'\u0001\u0007A\r\u0003\u0005\u0003DM!\t\u0019\u0001B#\u0003\u001d!WMZ1vYR\u0004RA\u001bB$\u0005wI1A!\u0013\\\u0005!a$-\u001f8b[\u0016t\u0014a\u0002:f[>4X\r\u001a\u000b\u0004u\n=\u0003B\u0002B\u001a)\u0001\u0007A-A\u0004va\u0012\fG/\u001a3\u0016\t\tU#1\f\u000b\u0007\u0005/\u0012iFa\u0018\u0011\u000b\u0005\u0004AM!\u0017\u0011\u0007\u0015\u0014Y\u0006B\u0004\u0002^U\u0011\r!a\u0018\t\r\tMR\u00031\u0001e\u0011\u001d\u0011\t'\u0006a\u0001\u00053\nQA^1mk\u0016\faaY8oG\u0006$X\u0003\u0002B4\u0005[\"BA!\u001b\u0003pA)\u0011\r\u00013\u0003lA\u0019QM!\u001c\u0005\u000f\u0005ucC1\u0001\u0002`!9!\u0011\u000f\fA\u0002\tM\u0014\u0001\u0002;iCR\u0004R\u0001 B;\u0005sJ1Aa\u001eZ\u00051IE/\u001a:bE2,wJ\\2f!\u0019Q\u0017Q\u00103\u0003l\u0005Q!/Z7pm\u0016$\u0017\t\u001c7\u0015\u0007i\u0014y\bC\u0004\u0003\u0002^\u0001\rAa!\u0002\t-,\u0017p\u001d\t\u0005y\nUD-\u0001\u0004j]N,'\u000f^\u000b\u0005\u0005\u0013\u0013y\t\u0006\u0004\u0003\f\nE%1\u0013\t\u0006C\u0002!'Q\u0012\t\u0004K\n=EaBA/1\t\u0007\u0011q\f\u0005\u0007\u0005gA\u0002\u0019\u00013\t\u000f\t\u0005\u0004\u00041\u0001\u0003\u000e\"Z\u0001Da&\u0003\u001e\n}%1\u0015BS!\rQ'\u0011T\u0005\u0004\u00057[&A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017E\u0001BQ\u0003U)6/\u001a\u0011akB$\u0017\r^3eA\u0002Jgn\u001d;fC\u0012\fQa]5oG\u0016\f#Aa*\u0002\rIr\u0013g\r\u00181\u0003%\u0011\u0018M\\4f\u00136\u0004H\u000eF\u0003{\u0005[\u0013\u0019\fC\u0004\u00030f\u0001\rA!-\u0002\t\u0019\u0014x.\u001c\t\u0005U\n5B\rC\u0004\u00036f\u0001\rA!-\u0002\u000bUtG/\u001b7\u0002\u00115Lg.\u00114uKJ$BAa/\u0003>B)!N!\f\u0002|!1!1\u0007\u000eA\u0002\u0011\f\u0011\"\\1y\u0005\u00164wN]3\u0015\t\tm&1\u0019\u0005\u0007\u0005gY\u0002\u0019\u00013\u0002\u000bI\fgnZ3\u0015\u000bi\u0014IMa3\t\r\t=F\u00041\u0001e\u0011\u0019\u0011)\f\ba\u0001I\u00069am\u001c:fC\u000eDW\u0003\u0002Bi\u0005K$BAa5\u0003ZB\u0019!N!6\n\u0007\t]7L\u0001\u0003V]&$\bb\u0002Bn;\u0001\u0007!Q\\\u0001\u0002MB9!Na8\u0002|\t\r\u0018b\u0001Bq7\nIa)\u001e8di&|g.\r\t\u0004K\n\u0015HA\u0002Bt;\t\u0007\u0001NA\u0001V\u000311wN]3bG\",e\u000e\u001e:z+\u0011\u0011iO!?\u0015\t\tM'q\u001e\u0005\b\u00057t\u0002\u0019\u0001By!\u001dQ'1\u001f3q\u0005oL1A!>\\\u0005%1UO\\2uS>t'\u0007E\u0002f\u0005s$aAa:\u001f\u0005\u0004A\u0017\u0001B:ju\u0016,\"Aa@\u0011\u0007)\u001c\t!C\u0002\u0004\u0004m\u00131!\u00138u\u0003%Ygn\\<o'&TX-A\u0004jg\u0016k\u0007\u000f^=\u0016\u0005\r-\u0001c\u00016\u0004\u000e%\u00191qB.\u0003\u000f\t{w\u000e\\3b]\u0006Aa-\u001b:ti.+\u00170F\u0001e\u0003\u001da\u0017m\u001d;LKf\fA\u0001[3bIV\u0011\u00111P\u0001\u0005Y\u0006\u001cH/\u0001\u0003uC&dW#\u0001>\u0002\t%t\u0017\u000e^\u0001\u0005IJ|\u0007\u000fF\u0002{\u0007OAqa!\u000b)\u0001\u0004\u0011y0A\u0001o\u0003\u0011!\u0018m[3\u0015\u0007i\u001cy\u0003C\u0004\u0004*%\u0002\rAa@\u0002\u000bMd\u0017nY3\u0015\u000bi\u001c)da\u000e\t\u000f\t=&\u00061\u0001\u0003\u0000\"9!Q\u0017\u0016A\u0002\t}\u0018!\u00033s_B\u0014\u0016n\u001a5u)\rQ8Q\b\u0005\b\u0007SY\u0003\u0019\u0001B\u0000\u0003%!\u0018m[3SS\u001eDG\u000fF\u0002{\u0007\u0007Bqa!\u000b-\u0001\u0004\u0011y0\u0001\u0006d_VtGo\u00165jY\u0016$BAa@\u0004J!911J\u0017A\u0002\r5\u0013!\u00019\u0011\u000f)\u0014y.a\u001f\u0004\f\u0005IAM]8q/\"LG.\u001a\u000b\u0004u\u000eM\u0003bBB&]\u0001\u00071QJ\u0001\ni\u0006\\Wm\u00165jY\u0016$2A_B-\u0011\u001d\u0019Ye\fa\u0001\u0007\u001b\nAa\u001d9b]R!1qLB1!\u0015Q\u0017Q\u0010>{\u0011\u001d\u0019Y\u0005\ra\u0001\u0007\u001b\naAZ5mi\u0016\u0014Hc\u0001>\u0004h!9!1\\\u0019A\u0002\r5\u0013!\u00039beRLG/[8o)\u0011\u0019yf!\u001c\t\u000f\r-#\u00071\u0001\u0004N\u0005IAO]1og\u001a|'/\\\u000b\u0005\u0007g\u001aI\b\u0006\u0003\u0004v\ru\u0004#B1\u0001I\u000e]\u0004cA3\u0004z\u0011111P\u001aC\u0002!\u0014\u0011a\u0016\u0005\b\u00057\u001c\u0004\u0019AB@!\u001dQ'1\u001f3q\u0007o\u0012Q!\u00113eKJ,Ba!\"\u0004\u0010N)Aga\"\u0004\u0014B9\u0011QDBEI\u000e5\u0015\u0002BBF\u0003O\u0011\u0011\"T1q\u0011\u0016d\u0007/\u001a:\u0011\u0007\u0015\u001cy\tB\u0004\u0004\u0012R\u0012\r!a\u0018\u0003\u0005\t\u000b\u0004c\u00026\u0003`\u000eU%1\u001b\t\u0007U\u0006uDm!$\u0015\u0005\re\u0005#BBNi\r5U\"\u0001\u0001\u0002%\r,(O]3oi6+H/\u00192mKR\u0013X-Z\u000b\u0003\u0007C\u0003r!!\b\u0002$\u0011\u001ci)\u0001\fdkJ\u0014XM\u001c;NkR\f'\r\\3Ue\u0016,w\fJ3r)\u0011\u0011\u0019na*\t\u0013\r%v'!AA\u0002\r\u0005\u0016a\u0001=%c\u0005\u00192-\u001e:sK:$X*\u001e;bE2,GK]3fA\u0005Ia-\u001b8bYR\u0013X-Z\u0001\u0006CB\u0004H.\u001f\u000b\u0005\u0005'\u001c\u0019\fC\u0004\u00046j\u0002\ra!&\u0002\u0005-4\u0018AB1eI\u0006cG\u000e\u0006\u0003\u0003T\u000em\u0006bBB_w\u0001\u00071qX\u0001\u0003YN\u0004R!YBa\u0007+K1aa1X\u0005%a\u0015N\\3beN+\u0017\u000fK\u0002<\u0007\u000f\u0004Ba!3\u0004P6\u001111\u001a\u0006\u0004\u0007\u001b\\\u0016AC1o]>$\u0018\r^5p]&!1\u0011[Bf\u0005\u001d!\u0018-\u001b7sK\u000e\fa!Z9vC2\u001cH\u0003BB\u0006\u0007/Daa!7=\u0001\u0004i\u0017aA8cU\u0006I1\r\\1tg:\u000bW.Z\u000b\u0003\u0007?\u0004Ba!9\u0004l6\u001111\u001d\u0006\u0005\u0007K\u001c9/\u0001\u0003mC:<'BABu\u0003\u0011Q\u0017M^1\n\t\r581\u001d\u0002\u0007'R\u0014\u0018N\\4\u0002\u000fQ\u0013X-Z'baB\u0011\u0011mP\n\u0006\u007f\rU\u00181\u000e\t\u0004U\u000e]\u0018bAB}7\n1\u0011I\\=SK\u001a$\"a!=\u0002\u000b\u0015l\u0007\u000f^=\u0016\r\u0011\u0005Aq\u0001C\u0006)\u0011!\u0019\u0001\"\u0004\u0011\r\u0005\u0004AQ\u0001C\u0005!\r)Gq\u0001\u0003\u0006O\u0006\u0013\r\u0001\u001b\t\u0004K\u0012-A!\u0002:B\u0005\u0004A\u0007\"\u0003C\b\u0003\u0006\u0005\t9\u0001C\t\u0003))g/\u001b3f]\u000e,G%\r\t\u0007\u0003c\t9\u0004\"\u0002\u0016\r\u0011UAQ\u0004C\u0011)\u0011!9\u0002b\n\u0015\t\u0011eA1\u0005\t\u0007C\u0002!Y\u0002b\b\u0011\u0007\u0015$i\u0002B\u0003h\u0005\n\u0007\u0001\u000eE\u0002f\tC!QA\u001d\"C\u0002!Dq!a\u000bC\u0001\b!)\u0003\u0005\u0004\u00022\u0005]B1\u0004\u0005\b\tS\u0011\u0005\u0019\u0001C\u0016\u0003\tIG\u000fE\u0003}\u0005k\"i\u0003E\u0004k\u0003{\"Y\u0002b\b\u0002\u00159,wOQ;jY\u0012,'/\u0006\u0004\u00054\u0011\u0015C\u0011\n\u000b\u0005\tk!i\u0005\u0005\u0005\u00058\u0011uB\u0011\tC&\u001b\t!IDC\u0002\u0005<e\u000bq!\\;uC\ndW-\u0003\u0003\u0005@\u0011e\"a\u0004*fkN\f'\r\\3Ck&dG-\u001a:\u0011\u000f)\fi\bb\u0011\u0005HA\u0019Q\r\"\u0012\u0005\u000b\u001d\u001c%\u0019\u00015\u0011\u0007\u0015$I\u0005B\u0003s\u0007\n\u0007\u0001\u000e\u0005\u0004b\u0001\u0011\rCq\t\u0005\b\u0003W\u0019\u00059\u0001C(!\u0019\t\t$a\u000e\u0005D\tqAK]3f\u001b\u0006\u0004()^5mI\u0016\u0014XC\u0002C+\t7\"yfE\u0003E\t/\"\t\u0007\u0005\u0005\u0002\u001e\r%E\u0011\fC/!\r)G1\f\u0003\u0006O\u0012\u0013\r\u0001\u001b\t\u0004K\u0012}C!\u0002:E\u0005\u0004A\u0007\u0003\u0003C\u001c\t{!\u0019\u0007\"\u001a\u0011\u000f)\fi\b\"\u0017\u0005^A1\u0011\r\u0001C-\t;\u0002b!!\r\u00028\u0011e\u0013\u0002BA\u0016\tWJA\u0001\"\u001c\u0002(\t1\u0001*\u001a7qKJ$\"\u0001\"\u001d\u0015\t\u0011MDq\u000f\t\b\tk\"E\u0011\fC/\u001b\u0005y\u0004bBA\u0016\r\u0002\u000fAq\r\t\t\u0003;\t\u0019\u0003\"\u0017\u0005^U\u0011AQ\u0010\t\u0004\t\u007f:U\"\u0001#\u0002\u0011Q\u0014X-Z0%KF$BAa5\u0005\u0006\"I1\u0011V%\u0002\u0002\u0003\u0007AQP\u0001\u0007C\u0012$wJ\\3\u0015\t\u0011}D1\u0012\u0005\b\t\u001b[\u0005\u0019\u0001C2\u0003\u0011)G.Z7\u0002\u000b\u0005$G-\u001a:\u0011\u0007\u0011}TJA\u0003bI\u0012,'oE\u0002N\t/\u0003\"\u0002\"'\u0005 \u0012eCQ\fBj\u001b\t!YJC\u0002\u0005\u001en\u000bqA];oi&lW-\u0003\u0003\u0005\"\u0012m%!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8oeQ\u0011A\u0011S\u0001\fC\u000e\u001cW/\\;mCR|'/\u0001\u0006bI\u00124uN]#bG\"$BAa5\u0005,\"9AQ\u0016)A\u0002\u0011=\u0016A\u00035bg\u001a{'/R1dQB9A\u0010\"-\u0005Z\u0011u\u0013bAA\u00053R1!1\u001bC[\toCqAa\rR\u0001\u0004!I\u0006C\u0004\u0003bE\u0003\r\u0001\"\u0018\u0015\t\u0011}D1\u0018\u0005\b\t{\u0013\u0006\u0019\u0001C`\u0003\tA8\u000fE\u0003}\u0005k\"\u0019'A\u0003dY\u0016\f'\u000f\u0006\u0002\u0003T\u00061!/Z:vYR$\"\u0001\"\u001a\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u00115\u0007\u0003BBq\t\u001fLA\u0001\"5\u0004d\n1qJ\u00196fGRDsa\u0010Ck\u0005C\"Y\u000eE\u0002k\t/L1\u0001\"7\\\u0005A\u0019VM]5bYZ+'o]5p]VKEIH\u0001\u0004Q\u001dqDQ\u001bB1\t7\u0004")
public final class TreeMap<K, V>
extends AbstractMap<K, V>
implements SortedMap<K, V>,
StrictOptimizedSortedMapOps<K, V, TreeMap, TreeMap<K, V>>,
DefaultSerializable {
    private final RedBlackTree.Tree<K, V> tree;
    private final Ordering<K> ordering;

    public static <K, V> ReusableBuilder<Tuple2<K, V>, TreeMap<K, V>> newBuilder(Ordering<K> ordering) {
        return new TreeMapBuilder(ordering);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
    }

    @Override
    public Map map(Function1 f, Ordering ordering) {
        return scala.collection.StrictOptimizedSortedMapOps.map$(this, f, ordering);
    }

    @Override
    public Map flatMap(Function1 f, Ordering ordering) {
        return scala.collection.StrictOptimizedSortedMapOps.flatMap$(this, f, ordering);
    }

    @Override
    public Map collect(PartialFunction pf, Ordering ordering) {
        return scala.collection.StrictOptimizedSortedMapOps.collect$(this, pf, ordering);
    }

    @Override
    public Map $plus(Tuple2 elem1, Tuple2 elem2, Seq elems) {
        return scala.collection.StrictOptimizedSortedMapOps.$plus$(this, elem1, elem2, elems);
    }

    @Override
    public IterableOps map(Function1 f) {
        return StrictOptimizedMapOps.map$(this, f);
    }

    @Override
    public IterableOps flatMap(Function1 f) {
        return StrictOptimizedMapOps.flatMap$(this, f);
    }

    @Override
    public IterableOps collect(PartialFunction pf) {
        return StrictOptimizedMapOps.collect$(this, pf);
    }

    @Override
    public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> unzip(Function1<Tuple2<K, V>, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>, scala.collection.immutable.Iterable<A3>> unzip3(Function1<Tuple2<K, V>, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<Tuple2<K, V>, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<Tuple2<K, V>, IterableOnce<B>> f) {
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
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<Tuple2<K, V>, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<Tuple2<K, V>, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<Tuple2<K, V>, B>, C2> b) {
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
    public Object filterNot(Function1 pred) {
        return StrictOptimizedIterableOps.filterNot$(this, pred);
    }

    @Override
    public Object filterImpl(Function1 pred, boolean isFlipped) {
        return StrictOptimizedIterableOps.filterImpl$(this, pred, isFlipped);
    }

    @Override
    public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> partitionMap(Function1<Tuple2<K, V>, Either<A1, A2>> f) {
        return StrictOptimizedIterableOps.partitionMap$(this, f);
    }

    @Override
    public Object tapEach(Function1 f) {
        return StrictOptimizedIterableOps.tapEach$(this, f);
    }

    @Override
    public scala.collection.immutable.Map<K, V> unsorted() {
        return SortedMap.unsorted$(this);
    }

    @Override
    public <V1> SortedMap<K, V1> withDefault(Function1<K, V1> d) {
        return SortedMap.withDefault$(this, d);
    }

    @Override
    public <V1> SortedMap<K, V1> withDefaultValue(V1 d) {
        return SortedMap.withDefaultValue$(this, d);
    }

    @Override
    public final scala.collection.immutable.Map $plus(Tuple2 kv) {
        return SortedMapOps.$plus$(this, kv);
    }

    @Override
    public scala.collection.immutable.Map updatedWith(Object key, Function1 remappingFunction) {
        return SortedMapOps.updatedWith$(this, key, remappingFunction);
    }

    @Override
    public /* synthetic */ boolean scala$collection$SortedMap$$super$equals(Object o) {
        return Map.equals$(this, o);
    }

    @Override
    public String stringPrefix() {
        return scala.collection.SortedMap.stringPrefix$(this);
    }

    @Override
    public scala.collection.SortedMapOps empty() {
        return SortedMapFactoryDefaults.empty$(this);
    }

    @Override
    public scala.collection.SortedMapOps fromSpecific(IterableOnce coll) {
        return SortedMapFactoryDefaults.fromSpecific$(this, coll);
    }

    @Override
    public Builder<Tuple2<K, V>, TreeMap<K, V>> newSpecificBuilder() {
        return SortedMapFactoryDefaults.newSpecificBuilder$(this);
    }

    @Override
    public SortedMapOps.WithFilter<K, V, ?, ?, ?> withFilter(Function1<Tuple2<K, V>, Object> p) {
        return SortedMapFactoryDefaults.withFilter$(this, p);
    }

    @Override
    public final Map sortedMapFromIterable(Iterable it, Ordering ordering) {
        return scala.collection.SortedMapOps.sortedMapFromIterable$(this, it, ordering);
    }

    @Override
    public scala.collection.SortedMapOps rangeTo(Object to) {
        return scala.collection.SortedMapOps.rangeTo$(this, to);
    }

    @Override
    public final Map $plus$plus(IterableOnce xs) {
        return scala.collection.SortedMapOps.$plus$plus$(this, xs);
    }

    @Override
    public int compare(K k0, K k1) {
        return SortedOps.compare$(this, k0, k1);
    }

    @Override
    public final Object from(Object from) {
        return SortedOps.from$(this, from);
    }

    @Override
    public Object rangeFrom(Object from) {
        return SortedOps.rangeFrom$(this, from);
    }

    @Override
    public final Object until(Object until) {
        return SortedOps.until$(this, until);
    }

    @Override
    public Object rangeUntil(Object until) {
        return SortedOps.rangeUntil$(this, until);
    }

    @Override
    public final Object to(Object to) {
        return SortedOps.to$(this, to);
    }

    private RedBlackTree.Tree<K, V> tree() {
        return this.tree;
    }

    @Override
    public Ordering<K> ordering() {
        return this.ordering;
    }

    public RedBlackTree.Tree<K, V> tree0() {
        return this.tree();
    }

    private <V1> TreeMap<K, V1> newMapOrSelf(RedBlackTree.Tree<K, V1> t2) {
        if (t2 == this.tree()) {
            return this;
        }
        return new TreeMap<K, V1>(t2, this.ordering());
    }

    @Override
    public SortedMapFactory<TreeMap> sortedMapFactory() {
        return TreeMap$.MODULE$;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Iterator<Tuple2<K, V>> iterator() {
        void iterator_evidence$16;
        Ordering<K> ordering = this.ordering();
        None$ iterator_start = None$.MODULE$;
        RedBlackTree.Tree<K, V> iterator_tree = this.tree();
        return new RedBlackTree.EntriesIterator<Nothing$, V>(iterator_tree, iterator_start, (Ordering<Nothing$>)iterator_evidence$16);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Iterator<K> keysIteratorFrom(K start2) {
        void keysIterator_evidence$17;
        Ordering<K> ordering = this.ordering();
        Some<K> keysIterator_start = new Some<K>(start2);
        RedBlackTree.Tree<K, V> keysIterator_tree = this.tree();
        return new RedBlackTree.KeysIterator<K, V>(keysIterator_tree, keysIterator_start, keysIterator_evidence$17);
    }

    @Override
    public TreeSet<K> keySet() {
        return new TreeSet<K>(this.tree(), this.ordering());
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Iterator<Tuple2<K, V>> iteratorFrom(K start2) {
        void iterator_evidence$16;
        Ordering<K> ordering = this.ordering();
        Some<K> iterator_start = new Some<K>(start2);
        RedBlackTree.Tree<K, V> iterator_tree = this.tree();
        return new RedBlackTree.EntriesIterator<K, V>(iterator_tree, iterator_start, iterator_evidence$16);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Iterator<V> valuesIteratorFrom(K start2) {
        void valuesIterator_evidence$18;
        Ordering<K> ordering = this.ordering();
        Some<K> valuesIterator_start = new Some<K>(start2);
        RedBlackTree.Tree<K, V> valuesIterator_tree = this.tree();
        return new RedBlackTree.ValuesIterator<K, V>(valuesIterator_tree, valuesIterator_start, valuesIterator_evidence$18);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<Tuple2<K, V>, S> shape) {
        void from_extract;
        void from_right;
        void from_left;
        Function1<RedBlackTree.Tree, Tuple2> & Serializable intersect = (Function1<RedBlackTree.Tree, Tuple2> & Serializable)x -> new Tuple2(x.scala$collection$immutable$RedBlackTree$Tree$$_key, x.scala$collection$immutable$RedBlackTree$Tree$$_value);
        Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect2 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$2 -> x$2.right();
        Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect3 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$1 -> x$1.left();
        RedBlackTree.Tree<K, V> from_root = this.tree();
        int from_maxLength = this.size();
        AnyBinaryTreeStepper from_ans = new AnyBinaryTreeStepper(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left, (Function1<Object, Object>)from_right, from_extract);
        from_ans.initialize(from_root, from_maxLength);
        Object var3_5 = null;
        intersect3 = null;
        intersect2 = null;
        intersect = null;
        Object var7_7 = null;
        return shape.parUnbox(from_ans);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <S extends Stepper<?>> S keyStepper(StepperShape<K, S> shape) {
        void from_root;
        void from_extract;
        void from_right;
        void from_left;
        int n = shape.shape();
        if (StepperShape$.MODULE$.IntShape() == n) {
            void from_root2;
            void from_extract2;
            void from_right2;
            void from_left2;
            Function1<RedBlackTree.Tree, Object> & Serializable intersect = (Function1<RedBlackTree.Tree, Object> & Serializable)x$5 -> BoxesRunTime.boxToInteger(TreeMap.$anonfun$keyStepper$3(x$5));
            Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect2 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$4 -> x$4.right();
            Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect3 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$3 -> x$3.left();
            RedBlackTree.Tree<K, V> tree = this.tree();
            int from_maxLength = this.size();
            IntBinaryTreeStepper<Object> from_ans = new IntBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left2, (Function1<Object, Object>)from_right2, (Function1<Object, Object>)from_extract2);
            from_ans.initialize(from_root2, from_maxLength);
            return (S)from_ans;
        }
        if (StepperShape$.MODULE$.LongShape() == n) {
            void from_root3;
            void from_extract3;
            void from_right3;
            void from_left3;
            Function1<RedBlackTree.Tree, Object> & Serializable intersect = (Function1<RedBlackTree.Tree, Object> & Serializable)x$8 -> BoxesRunTime.boxToLong(TreeMap.$anonfun$keyStepper$6(x$8));
            Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect4 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$7 -> x$7.right();
            Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect5 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$6 -> x$6.left();
            RedBlackTree.Tree<K, V> tree = this.tree();
            int from_maxLength = this.size();
            LongBinaryTreeStepper<Object> from_ans = new LongBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left3, (Function1<Object, Object>)from_right3, (Function1<Object, Object>)from_extract3);
            from_ans.initialize(from_root3, from_maxLength);
            return (S)from_ans;
        }
        if (StepperShape$.MODULE$.DoubleShape() == n) {
            void from_root4;
            void from_extract4;
            void from_right4;
            void from_left4;
            Function1<RedBlackTree.Tree, Object> & Serializable intersect = (Function1<RedBlackTree.Tree, Object> & Serializable)x$11 -> BoxesRunTime.boxToDouble(TreeMap.$anonfun$keyStepper$9(x$11));
            Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect6 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$10 -> x$10.right();
            Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect7 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$9 -> x$9.left();
            RedBlackTree.Tree<K, V> tree = this.tree();
            int from_maxLength = this.size();
            DoubleBinaryTreeStepper<Object> from_ans = new DoubleBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left4, (Function1<Object, Object>)from_right4, (Function1<Object, Object>)from_extract4);
            from_ans.initialize(from_root4, from_maxLength);
            return (S)from_ans;
        }
        Function1<RedBlackTree.Tree, Object> & Serializable intersect = (Function1<RedBlackTree.Tree, Object> & Serializable)x$14 -> x$14.key();
        Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect8 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$13 -> x$13.right();
        Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect9 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$12 -> x$12.left();
        RedBlackTree.Tree<K, V> tree = this.tree();
        int from_maxLength = this.size();
        AnyBinaryTreeStepper from_ans = new AnyBinaryTreeStepper(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left, (Function1<Object, Object>)from_right, from_extract);
        from_ans.initialize(from_root, from_maxLength);
        tree = null;
        intersect9 = null;
        intersect8 = null;
        intersect = null;
        Object var26_26 = null;
        return shape.parUnbox(from_ans);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <S extends Stepper<?>> S valueStepper(StepperShape<V, S> shape) {
        void from_root;
        void from_extract;
        void from_right;
        void from_left;
        int n = shape.shape();
        if (StepperShape$.MODULE$.IntShape() == n) {
            void from_root2;
            void from_extract2;
            void from_right2;
            void from_left2;
            Function1<RedBlackTree.Tree, Object> & Serializable intersect = (Function1<RedBlackTree.Tree, Object> & Serializable)x$17 -> BoxesRunTime.boxToInteger(TreeMap.$anonfun$valueStepper$3(x$17));
            Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect2 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$16 -> x$16.right();
            Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect3 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$15 -> x$15.left();
            RedBlackTree.Tree<K, V> tree = this.tree();
            int from_maxLength = this.size();
            IntBinaryTreeStepper<Object> from_ans = new IntBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left2, (Function1<Object, Object>)from_right2, (Function1<Object, Object>)from_extract2);
            from_ans.initialize(from_root2, from_maxLength);
            return (S)from_ans;
        }
        if (StepperShape$.MODULE$.LongShape() == n) {
            void from_root3;
            void from_extract3;
            void from_right3;
            void from_left3;
            Function1<RedBlackTree.Tree, Object> & Serializable intersect = (Function1<RedBlackTree.Tree, Object> & Serializable)x$20 -> BoxesRunTime.boxToLong(TreeMap.$anonfun$valueStepper$6(x$20));
            Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect4 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$19 -> x$19.right();
            Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect5 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$18 -> x$18.left();
            RedBlackTree.Tree<K, V> tree = this.tree();
            int from_maxLength = this.size();
            LongBinaryTreeStepper<Object> from_ans = new LongBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left3, (Function1<Object, Object>)from_right3, (Function1<Object, Object>)from_extract3);
            from_ans.initialize(from_root3, from_maxLength);
            return (S)from_ans;
        }
        if (StepperShape$.MODULE$.DoubleShape() == n) {
            void from_root4;
            void from_extract4;
            void from_right4;
            void from_left4;
            Function1<RedBlackTree.Tree, Object> & Serializable intersect = (Function1<RedBlackTree.Tree, Object> & Serializable)x$23 -> BoxesRunTime.boxToDouble(TreeMap.$anonfun$valueStepper$9(x$23));
            Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect6 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$22 -> x$22.right();
            Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect7 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$21 -> x$21.left();
            RedBlackTree.Tree<K, V> tree = this.tree();
            int from_maxLength = this.size();
            DoubleBinaryTreeStepper<Object> from_ans = new DoubleBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left4, (Function1<Object, Object>)from_right4, (Function1<Object, Object>)from_extract4);
            from_ans.initialize(from_root4, from_maxLength);
            return (S)from_ans;
        }
        Function1<RedBlackTree.Tree, Object> & Serializable intersect = (Function1<RedBlackTree.Tree, Object> & Serializable)x$26 -> x$26.value();
        Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect8 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$25 -> x$25.right();
        Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect9 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$24 -> x$24.left();
        RedBlackTree.Tree<K, V> tree = this.tree();
        int from_maxLength = this.size();
        AnyBinaryTreeStepper from_ans = new AnyBinaryTreeStepper(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left, (Function1<Object, Object>)from_right, from_extract);
        from_ans.initialize(from_root, from_maxLength);
        tree = null;
        intersect9 = null;
        intersect8 = null;
        intersect = null;
        Object var26_26 = null;
        return shape.parUnbox(from_ans);
    }

    @Override
    public Option<V> get(K key) {
        return RedBlackTree$.MODULE$.get(this.tree(), key, this.ordering());
    }

    @Override
    public <V1> V1 getOrElse(K key, Function0<V1> function0) {
        RedBlackTree.Tree<K, V> resultOrNull = RedBlackTree$.MODULE$.lookup(this.tree(), key, this.ordering());
        if (resultOrNull == null) {
            return function0.apply();
        }
        return (V1)resultOrNull.scala$collection$immutable$RedBlackTree$Tree$$_value;
    }

    @Override
    public TreeMap<K, V> removed(K key) {
        return this.newMapOrSelf(RedBlackTree$.MODULE$.delete(this.tree(), key, this.ordering()));
    }

    @Override
    public <V1> TreeMap<K, V1> updated(K key, V1 value) {
        return this.newMapOrSelf(RedBlackTree$.MODULE$.update(this.tree(), key, value, true, this.ordering()));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public <V1> TreeMap<K, V1> concat(IterableOnce<Tuple2<K, V1>> that) {
        RedBlackTree.Tree<K, Object> tree;
        if (that instanceof TreeMap) {
            TreeMap treeMap = (TreeMap)that;
            Ordering<K> ordering = this.ordering();
            Ordering<K> ordering2 = treeMap.ordering();
            if (!(ordering != null ? !ordering.equals(ordering2) : ordering2 != null)) {
                tree = RedBlackTree$.MODULE$.union(this.tree(), treeMap.tree(), this.ordering());
                return this.newMapOrSelf(tree);
            }
        }
        if (that instanceof LinearSeq) {
            LinearSeq linearSeq = (LinearSeq)that;
            if (linearSeq.isEmpty()) {
                tree = this.tree();
                return this.newMapOrSelf(tree);
            }
            Adder adder = new Adder(this);
            adder.addAll(linearSeq);
            tree = adder.finalTree();
            return this.newMapOrSelf(tree);
        }
        Adder<V1> adder = new Adder<V1>(this);
        Iterator<Tuple2<K, V1>> it = that.iterator();
        while (true) {
            if (!it.hasNext()) {
                tree = adder.finalTree();
                return this.newMapOrSelf(tree);
            }
            adder.apply(it.next());
        }
    }

    @Override
    public TreeMap<K, V> removedAll(IterableOnce<K> keys2) {
        if (keys2 instanceof TreeSet) {
            TreeSet treeSet = (TreeSet)keys2;
            Ordering<K> ordering = this.ordering();
            Ordering ordering2 = treeSet.ordering();
            if (!(ordering != null ? !ordering.equals(ordering2) : ordering2 != null)) {
                return this.newMapOrSelf(RedBlackTree$.MODULE$.difference(this.tree(), treeSet.tree(), this.ordering()));
            }
        }
        return (TreeMap)MapOps.removedAll$(this, keys2);
    }

    public <V1> TreeMap<K, V1> insert(K key, V1 value) {
        Predef$.MODULE$.assert(!RedBlackTree$.MODULE$.contains(this.tree(), key, this.ordering()));
        return this.updated((Object)key, (Object)value);
    }

    @Override
    public TreeMap<K, V> rangeImpl(Option<K> from, Option<K> until) {
        return this.newMapOrSelf(RedBlackTree$.MODULE$.rangeImpl(this.tree(), from, until, this.ordering()));
    }

    @Override
    public Option<Tuple2<K, V>> minAfter(K key) {
        RedBlackTree.Tree<K, V> tree = RedBlackTree$.MODULE$.minAfter(this.tree(), key, this.ordering());
        if (tree == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<K, V>>(new Tuple2(tree.scala$collection$immutable$RedBlackTree$Tree$$_key, tree.scala$collection$immutable$RedBlackTree$Tree$$_value));
    }

    @Override
    public Option<Tuple2<K, V>> maxBefore(K key) {
        RedBlackTree.Tree<K, V> tree = RedBlackTree$.MODULE$.maxBefore(this.tree(), key, this.ordering());
        if (tree == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<K, V>>(new Tuple2(tree.scala$collection$immutable$RedBlackTree$Tree$$_key, tree.scala$collection$immutable$RedBlackTree$Tree$$_value));
    }

    @Override
    public TreeMap<K, V> range(K from, K until) {
        return this.newMapOrSelf(RedBlackTree$.MODULE$.range(this.tree(), from, until, this.ordering()));
    }

    @Override
    public <U> void foreach(Function1<Tuple2<K, V>, U> f) {
        RedBlackTree$.MODULE$.foreach(this.tree(), f);
    }

    @Override
    public <U> void foreachEntry(Function2<K, V, U> f) {
        RedBlackTree$.MODULE$.foreachEntry(this.tree(), f);
    }

    @Override
    public int size() {
        return RedBlackTree$.MODULE$.count(this.tree());
    }

    @Override
    public int knownSize() {
        return this.size();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public K firstKey() {
        RedBlackTree.Tree<K, V> tree = RedBlackTree$.MODULE$.smallest(this.tree());
        if (tree == null) {
            throw null;
        }
        return (K)tree.scala$collection$immutable$RedBlackTree$Tree$$_key;
    }

    @Override
    public K lastKey() {
        RedBlackTree.Tree<K, V> tree = RedBlackTree$.MODULE$.greatest(this.tree());
        if (tree == null) {
            throw null;
        }
        return (K)tree.scala$collection$immutable$RedBlackTree$Tree$$_key;
    }

    @Override
    public Tuple2<K, V> head() {
        RedBlackTree.Tree<K, V> smallest = RedBlackTree$.MODULE$.smallest(this.tree());
        if (smallest == null) {
            throw null;
        }
        return new Tuple2(smallest.scala$collection$immutable$RedBlackTree$Tree$$_key, smallest.scala$collection$immutable$RedBlackTree$Tree$$_value);
    }

    @Override
    public Tuple2<K, V> last() {
        RedBlackTree.Tree<K, V> greatest = RedBlackTree$.MODULE$.greatest(this.tree());
        if (greatest == null) {
            throw null;
        }
        return new Tuple2(greatest.scala$collection$immutable$RedBlackTree$Tree$$_key, greatest.scala$collection$immutable$RedBlackTree$Tree$$_value);
    }

    @Override
    public TreeMap<K, V> tail() {
        return new TreeMap<K, V>(RedBlackTree$.MODULE$.tail(this.tree()), this.ordering());
    }

    @Override
    public TreeMap<K, V> init() {
        return new TreeMap<K, V>(RedBlackTree$.MODULE$.init(this.tree()), this.ordering());
    }

    @Override
    public TreeMap<K, V> drop(int n) {
        if (n <= 0) {
            return this;
        }
        if (n >= this.size()) {
            return (TreeMap)SortedMapFactoryDefaults.empty$(this);
        }
        return new TreeMap<K, V>(RedBlackTree$.MODULE$.drop(this.tree(), n, this.ordering()), this.ordering());
    }

    @Override
    public TreeMap<K, V> take(int n) {
        if (n <= 0) {
            return (TreeMap)SortedMapFactoryDefaults.empty$(this);
        }
        if (n >= this.size()) {
            return this;
        }
        return new TreeMap<K, V>(RedBlackTree$.MODULE$.take(this.tree(), n, this.ordering()), this.ordering());
    }

    @Override
    public TreeMap<K, V> slice(int from, int until) {
        if (until <= from) {
            return (TreeMap)SortedMapFactoryDefaults.empty$(this);
        }
        if (from <= 0) {
            return this.take(until);
        }
        if (until >= this.size()) {
            return this.drop(from);
        }
        return new TreeMap<K, V>(RedBlackTree$.MODULE$.slice(this.tree(), from, until, this.ordering()), this.ordering());
    }

    @Override
    public TreeMap<K, V> dropRight(int n) {
        int max_y = 0;
        return this.take(this.size() - Math.max(n, max_y));
    }

    @Override
    public TreeMap<K, V> takeRight(int n) {
        int max_y = 0;
        return this.drop(this.size() - Math.max(n, max_y));
    }

    private int countWhile(Function1<Tuple2<K, V>, Object> p) {
        int result = 0;
        Iterator<Tuple2<K, V>> it = this.iterator();
        while (it.hasNext() && BoxesRunTime.unboxToBoolean(p.apply(it.next()))) {
            ++result;
        }
        return result;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public TreeMap<K, V> dropWhile(Function1<Tuple2<K, V>, Object> p) {
        void var2_2;
        int countWhile_result = 0;
        Iterator<Tuple2<K, V>> countWhile_it = this.iterator();
        while (countWhile_it.hasNext() && BoxesRunTime.unboxToBoolean(p.apply(countWhile_it.next()))) {
            ++countWhile_result;
        }
        Object var3_3 = null;
        return this.drop((int)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public TreeMap<K, V> takeWhile(Function1<Tuple2<K, V>, Object> p) {
        void var2_2;
        int countWhile_result = 0;
        Iterator<Tuple2<K, V>> countWhile_it = this.iterator();
        while (countWhile_it.hasNext() && BoxesRunTime.unboxToBoolean(p.apply(countWhile_it.next()))) {
            ++countWhile_result;
        }
        Object var3_3 = null;
        return this.take((int)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Tuple2<TreeMap<K, V>, TreeMap<K, V>> span(Function1<Tuple2<K, V>, Object> p) {
        void var2_2;
        int countWhile_result = 0;
        Iterator<Tuple2<K, V>> countWhile_it = this.iterator();
        while (countWhile_it.hasNext() && BoxesRunTime.unboxToBoolean(p.apply(countWhile_it.next()))) {
            ++countWhile_result;
        }
        Object var3_3 = null;
        return IterableOps.splitAt$(this, (int)var2_2);
    }

    @Override
    public TreeMap<K, V> filter(Function1<Tuple2<K, V>, Object> f) {
        return this.newMapOrSelf(RedBlackTree$.MODULE$.filterEntries(this.tree(), (Function2<Object, Object, Object> & Serializable)(k, v) -> BoxesRunTime.boxToBoolean(TreeMap.$anonfun$filter$1(f, k, v))));
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Tuple2<TreeMap<K, V>, TreeMap<K, V>> partition(Function1<Tuple2<K, V>, Object> p) {
        void var4_4;
        void var3_3;
        Tuple2<RedBlackTree.Tree<K, V>, RedBlackTree.Tree<K, V>> tuple2 = RedBlackTree$.MODULE$.partitionEntries(this.tree(), (Function2<Object, Object, Object> & Serializable)(k, v) -> BoxesRunTime.boxToBoolean(TreeMap.$anonfun$partition$1(p, k, v)));
        if (tuple2 == null) {
            throw new MatchError((Object)null);
        }
        RedBlackTree.Tree<K, V> l = tuple2._1();
        RedBlackTree.Tree<K, V> r = tuple2._2();
        return new Tuple2<TreeMap<K, V>, TreeMap<K, V>>(this.newMapOrSelf((RedBlackTree.Tree)var3_3), this.newMapOrSelf((RedBlackTree.Tree)var4_4));
    }

    @Override
    public <W> TreeMap<K, W> transform(Function2<K, V, W> f) {
        RedBlackTree.Tree<K, W> t2 = RedBlackTree$.MODULE$.transform(this.tree(), f);
        if (t2 == this.tree()) {
            return this;
        }
        return new TreeMap<K, W>(t2, this.ordering());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TreeMap) {
            TreeMap treeMap = (TreeMap)obj;
            Ordering<K> ordering = this.ordering();
            Ordering<K> ordering2 = treeMap.ordering();
            if (!(ordering != null ? !ordering.equals(ordering2) : ordering2 != null)) {
                return RedBlackTree$.MODULE$.entriesEqual(this.tree(), treeMap.tree(), this.ordering());
            }
        }
        return scala.collection.SortedMap.equals$(this, obj);
    }

    @Override
    public String className() {
        return "TreeMap";
    }

    public static final /* synthetic */ int $anonfun$keyStepper$3(RedBlackTree.Tree x$5) {
        return BoxesRunTime.unboxToInt(x$5.key());
    }

    public static final /* synthetic */ long $anonfun$keyStepper$6(RedBlackTree.Tree x$8) {
        return BoxesRunTime.unboxToLong(x$8.key());
    }

    public static final /* synthetic */ double $anonfun$keyStepper$9(RedBlackTree.Tree x$11) {
        return BoxesRunTime.unboxToDouble(x$11.key());
    }

    public static final /* synthetic */ int $anonfun$valueStepper$3(RedBlackTree.Tree x$17) {
        return BoxesRunTime.unboxToInt(x$17.value());
    }

    public static final /* synthetic */ long $anonfun$valueStepper$6(RedBlackTree.Tree x$20) {
        return BoxesRunTime.unboxToLong(x$20.value());
    }

    public static final /* synthetic */ double $anonfun$valueStepper$9(RedBlackTree.Tree x$23) {
        return BoxesRunTime.unboxToDouble(x$23.value());
    }

    public static final /* synthetic */ boolean $anonfun$filter$1(Function1 f$1, Object k, Object v) {
        return BoxesRunTime.unboxToBoolean(f$1.apply(new Tuple2<Object, Object>(k, v)));
    }

    public static final /* synthetic */ boolean $anonfun$partition$1(Function1 p$1, Object k, Object v) {
        return BoxesRunTime.unboxToBoolean(p$1.apply(new Tuple2<Object, Object>(k, v)));
    }

    public TreeMap(RedBlackTree.Tree<K, V> tree, Ordering<K> ordering) {
        this.tree = tree;
        this.ordering = ordering;
    }

    public TreeMap(Ordering<K> ordering) {
        this(null, ordering);
    }

    private final class Adder<B1>
    extends RedBlackTree.MapHelper<K, B1>
    implements Function1<Tuple2<K, B1>, BoxedUnit> {
        private RedBlackTree.Tree<K, B1> currentMutableTree;

        @Override
        public boolean apply$mcZD$sp(double v1) {
            return Function1.apply$mcZD$sp$(this, v1);
        }

        @Override
        public double apply$mcDD$sp(double v1) {
            return Function1.apply$mcDD$sp$(this, v1);
        }

        @Override
        public float apply$mcFD$sp(double v1) {
            return Function1.apply$mcFD$sp$(this, v1);
        }

        @Override
        public int apply$mcID$sp(double v1) {
            return Function1.apply$mcID$sp$(this, v1);
        }

        @Override
        public long apply$mcJD$sp(double v1) {
            return Function1.apply$mcJD$sp$(this, v1);
        }

        @Override
        public void apply$mcVD$sp(double v1) {
            Function1.apply$mcVD$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZF$sp(float v1) {
            return Function1.apply$mcZF$sp$(this, v1);
        }

        @Override
        public double apply$mcDF$sp(float v1) {
            return Function1.apply$mcDF$sp$(this, v1);
        }

        @Override
        public float apply$mcFF$sp(float v1) {
            return Function1.apply$mcFF$sp$(this, v1);
        }

        @Override
        public int apply$mcIF$sp(float v1) {
            return Function1.apply$mcIF$sp$(this, v1);
        }

        @Override
        public long apply$mcJF$sp(float v1) {
            return Function1.apply$mcJF$sp$(this, v1);
        }

        @Override
        public void apply$mcVF$sp(float v1) {
            Function1.apply$mcVF$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZI$sp(int v1) {
            return Function1.apply$mcZI$sp$(this, v1);
        }

        @Override
        public double apply$mcDI$sp(int v1) {
            return Function1.apply$mcDI$sp$(this, v1);
        }

        @Override
        public float apply$mcFI$sp(int v1) {
            return Function1.apply$mcFI$sp$(this, v1);
        }

        @Override
        public int apply$mcII$sp(int v1) {
            return Function1.apply$mcII$sp$(this, v1);
        }

        @Override
        public long apply$mcJI$sp(int v1) {
            return Function1.apply$mcJI$sp$(this, v1);
        }

        @Override
        public void apply$mcVI$sp(int v1) {
            Function1.apply$mcVI$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZJ$sp(long v1) {
            return Function1.apply$mcZJ$sp$(this, v1);
        }

        @Override
        public double apply$mcDJ$sp(long v1) {
            return Function1.apply$mcDJ$sp$(this, v1);
        }

        @Override
        public float apply$mcFJ$sp(long v1) {
            return Function1.apply$mcFJ$sp$(this, v1);
        }

        @Override
        public int apply$mcIJ$sp(long v1) {
            return Function1.apply$mcIJ$sp$(this, v1);
        }

        @Override
        public long apply$mcJJ$sp(long v1) {
            return Function1.apply$mcJJ$sp$(this, v1);
        }

        @Override
        public void apply$mcVJ$sp(long v1) {
            Function1.apply$mcVJ$sp$(this, v1);
        }

        @Override
        public <A$> Function1<A$, BoxedUnit> compose(Function1<A$, Tuple2<K, B1>> g2) {
            return Function1.compose$(this, g2);
        }

        @Override
        public <A$> Function1<Tuple2<K, B1>, A$> andThen(Function1<BoxedUnit, A$> g2) {
            return Function1.andThen$(this, g2);
        }

        @Override
        public String toString() {
            return Function1.toString$(this);
        }

        private RedBlackTree.Tree<K, B1> currentMutableTree() {
            return this.currentMutableTree;
        }

        private void currentMutableTree_$eq(RedBlackTree.Tree<K, B1> x$1) {
            this.currentMutableTree = x$1;
        }

        public RedBlackTree.Tree<K, B1> finalTree() {
            return this.beforePublish(this.currentMutableTree());
        }

        @Override
        public void apply(Tuple2<K, B1> kv) {
            this.currentMutableTree_$eq(this.mutableUpd(this.currentMutableTree(), kv._1(), kv._2()));
        }

        public void addAll(LinearSeq<Tuple2<K, B1>> ls) {
            while (!ls.isEmpty()) {
                Tuple2 kv = (Tuple2)ls.head();
                this.currentMutableTree_$eq(this.mutableUpd(this.currentMutableTree(), kv._1(), kv._2()));
                ls = (LinearSeq)ls.tail();
            }
        }

        public Adder(TreeMap $outer) {
            super($outer.ordering());
            this.currentMutableTree = $outer.tree0();
        }
    }

    private static class TreeMapBuilder<K, V>
    extends RedBlackTree.MapHelper<K, V>
    implements ReusableBuilder<Tuple2<K, V>, TreeMap<K, V>> {
        private volatile TreeMap$TreeMapBuilder$adder$ adder$module;
        private RedBlackTree.Tree<K, V> scala$collection$immutable$TreeMap$TreeMapBuilder$$tree = null;

        @Override
        public void sizeHint(int size) {
            Builder.sizeHint$(this, size);
        }

        @Override
        public final void sizeHint(IterableOnce<?> coll, int delta) {
            Builder.sizeHint$(this, coll, delta);
        }

        @Override
        public final int sizeHint$default$2() {
            return Builder.sizeHint$default$2$(this);
        }

        @Override
        public final void sizeHintBounded(int size, Iterable<?> boundingColl) {
            Builder.sizeHintBounded$(this, size, boundingColl);
        }

        @Override
        public <NewTo> Builder<Tuple2<K, V>, NewTo> mapResult(Function1<TreeMap<K, V>, NewTo> f) {
            return Builder.mapResult$(this, f);
        }

        @Override
        public final Growable $plus$eq(Object elem) {
            return Growable.$plus$eq$(this, elem);
        }

        @Override
        public final Growable $plus$eq(Object elem1, Object elem2, Seq elems) {
            return Growable.$plus$eq$(this, elem1, elem2, elems);
        }

        @Override
        public final Growable<Tuple2<K, V>> $plus$plus$eq(IterableOnce<Tuple2<K, V>> xs) {
            return Growable.$plus$plus$eq$(this, xs);
        }

        @Override
        public int knownSize() {
            return Growable.knownSize$(this);
        }

        private TreeMap$TreeMapBuilder$adder$ adder() {
            if (this.adder$module == null) {
                this.adder$lzycompute$1();
            }
            return this.adder$module;
        }

        public RedBlackTree.Tree<K, V> scala$collection$immutable$TreeMap$TreeMapBuilder$$tree() {
            return this.scala$collection$immutable$TreeMap$TreeMapBuilder$$tree;
        }

        public void scala$collection$immutable$TreeMap$TreeMapBuilder$$tree_$eq(RedBlackTree.Tree<K, V> x$1) {
            this.scala$collection$immutable$TreeMap$TreeMapBuilder$$tree = x$1;
        }

        public TreeMapBuilder<K, V> addOne(Tuple2<K, V> elem) {
            this.scala$collection$immutable$TreeMap$TreeMapBuilder$$tree_$eq(this.mutableUpd(this.scala$collection$immutable$TreeMap$TreeMapBuilder$$tree(), elem._1(), elem._2()));
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public TreeMapBuilder<K, V> addAll(IterableOnce<Tuple2<K, V>> xs) {
            if (xs instanceof TreeMap) {
                TreeMap treeMap = (TreeMap)xs;
                Ordering ordering = treeMap.ordering();
                Ordering ordering2 = super.ordering();
                if (!(ordering != null ? !ordering.equals(ordering2) : ordering2 != null)) {
                    if (this.scala$collection$immutable$TreeMap$TreeMapBuilder$$tree() == null) {
                        this.scala$collection$immutable$TreeMap$TreeMapBuilder$$tree_$eq(treeMap.tree0());
                        return this;
                    }
                    this.scala$collection$immutable$TreeMap$TreeMapBuilder$$tree_$eq(RedBlackTree$.MODULE$.union(this.beforePublish(this.scala$collection$immutable$TreeMap$TreeMapBuilder$$tree()), treeMap.tree0(), super.ordering()));
                    return this;
                }
            }
            if (xs instanceof Map) {
                Map map = (Map)xs;
                this.adder().addForEach(map);
                return this;
            }
            Growable.addAll$(this, xs);
            return this;
        }

        @Override
        public void clear() {
            this.scala$collection$immutable$TreeMap$TreeMapBuilder$$tree_$eq(null);
        }

        @Override
        public TreeMap<K, V> result() {
            return new TreeMap<K, V>(this.beforePublish(this.scala$collection$immutable$TreeMap$TreeMapBuilder$$tree()), super.ordering());
        }

        private final void adder$lzycompute$1() {
            synchronized (this) {
                if (this.adder$module == null) {
                    this.adder$module = new TreeMap$TreeMapBuilder$adder$(this);
                }
                return;
            }
        }

        public TreeMapBuilder(Ordering<K> ordering) {
            super(ordering);
        }
    }
}

