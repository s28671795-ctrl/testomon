/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

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
import scala.collection.EvidenceIterableFactoryDefaults;
import scala.collection.IndexedSeq;
import scala.collection.IndexedSeqOps;
import scala.collection.IndexedSeqView;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
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
import scala.collection.immutable.AbstractSeq;
import scala.collection.immutable.ArraySeq$;
import scala.collection.mutable.ArrayBuilder;
import scala.collection.mutable.ArraySeq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.StringBuilder;
import scala.math.Integral;
import scala.math.Ordering;
import scala.math.Ordering$Boolean$;
import scala.math.Ordering$Byte$;
import scala.math.Ordering$Char$;
import scala.math.Ordering$Int$;
import scala.math.Ordering$Long$;
import scala.math.Ordering$Short$;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;
import scala.reflect.ManifestFactory;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.util.Either;
import scala.util.Sorting$;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005%Ug\u0001CA:\u0003k\n\t#a!\t\u000f\u0005%\b\u0001\"\u0001\u0002l\"9\u0011Q\u001e\u0001\u0007\u0012\u0005=\bbBA~\u0001\u0011\u0005\u0013Q \u0005\b\u0005\u000b\u0001a\u0011\u0001B\u0004\u0011\u001d\u00119\u0002\u0001C\t\u00053Aq\u0001c$\u0001\t#A\t\nC\u0004\u0003l\u00021\t\u0001#+\t\u000f\t\u001d\u0004A\"\u0001\tF\"9A1\t\u0001\u0005B!U\u0007b\u0002Es\u0001\u0011\u0005\u0003r\u001d\u0005\b\tG\u0002A\u0011\tE{\u0011\u001d!)\u0006\u0001C!\u0013\u0003Aq!#\u0004\u0001\t\u0013Iy\u0001C\u0004\n\u001c\u0001!\t%#\b\t\u000f%5\u0002\u0001\"\u0011\n0!9\u0011r\b\u0001\u0005B%\u0005\u0003bBE+\u0001\u0011\u0005\u0013r\u000b\u0005\b\u00137\u0002A\u0011IE/\u0011\u001dI\t\u0007\u0001C!\u0013GBq!c\u001a\u0001\t\u0003JI\u0007C\u0004\nn\u0001!\t%c\u001c\t\u000f%]\u0004\u0001\"\u0011\nz!9\u0011r\u0012\u0001\u0005B%E\u0005bBEQ\u0001\u0011\u0005\u00132\u0015\u0005\b\u0013K\u0003A\u0011IER\u0011!I9\u000b\u0001Q\u0005R%%\u0006bBEX\u0001\u0011\u0005\u0013\u0012\u0017\u0005\b\u0013\u000b\u0004AQ\u000bB0\u0011\u001d\u00119\r\u0001C!\u0013\u000f<\u0001B!\b\u0002v!\u0005!q\u0004\u0004\t\u0003g\n)\b#\u0001\u0003\"!9\u0011\u0011^\u0010\u0005\u0002\t=\u0002\"\u0003B\u0019?\t\u0007I\u0011AA\u007f\u0011!\u0011\u0019d\bQ\u0001\n\u0005}\bB\u0003B\u001b?!\u0015\r\u0015\"\u0003\u00038!91QG\u0010\u0005\u0002\r]\u0002bBB$?\u0011\u00051\u0011\n\u0005\b\u0007KzB\u0011AB4\u0011\u001d\u0019\u0019i\bC!\u0007\u000bCqaa* \t\u0003\u001aI\u000bC\u0004\u0004J~!\taa3\u0007\r\tmrD\u0001B\u001f\u0011)\u0011)A\u000bBC\u0002\u0013\u0005!1\n\u0005\u000b\u0005\u001fR#\u0011!Q\u0001\n\t5\u0003bBAuU\u0011\u0005!\u0011\u000b\u0005\b\u0003[TC\u0011\u0001B-\u0011\u001d\u0011iF\u000bC\u0001\u0005?BqAa\u001a+\t\u0003\u0011I\u0007C\u0004\u00036*\"\tEa.\t\u000f\te&\u0006\"\u0011\u0003<\"9!q\u0019\u0016\u0005B\t%\u0007b\u0002BqU\u0011\u0005#1\u001d\u0005\b\u0005WTC\u0011\tBw\r\u0019\u0019Yn\b\u0002\u0004^\"Q!Q\u0001\u001c\u0003\u0006\u0004%\taa:\t\u0015\t=cG!A!\u0002\u0013\u0019I\u000fC\u0004\u0002jZ\"\taa;\t\u000f\u00055h\u0007\"\u0005\u0004r\"9!Q\f\u001c\u0005\u0002\t}\u0003b\u0002B4m\u0011\u0005A\u0011\u0001\u0005\b\u0005k3D\u0011\tB\\\u0011\u001d\u0011IL\u000eC!\t#AqAa27\t\u0003\")\u0002C\u0004\u0003bZ\"\t\u0005b\t\t\u000f\t-h\u0007\"\u0011\u0005(!9A1\t\u001c\u0005B\u0011\u0015\u0003b\u0002C+m\u0011\u0005Cq\u000b\u0005\b\tG2D\u0011\tC3\r\u0019!\u0019h\b\u0002\u0005v!Q!QA#\u0003\u0006\u0004%\t\u0001b \t\u0015\t=SI!A!\u0002\u0013!\t\tC\u0004\u0002j\u0016#\t\u0001b!\t\u000f\u00055X\t\"\u0005\u0005\n\"9!QL#\u0005\u0002\t}\u0003b\u0002B4\u000b\u0012\u0005A\u0011\u0013\u0005\b\u0005k+E\u0011\tB\\\u0011\u001d\u0011I,\u0012C!\tCCqAa2F\t\u0003\")\u000bC\u0004\u0003b\u0016#\t\u0005b-\t\u000f\t-X\t\"\u0011\u00058\"9A1I#\u0005B\u0011M\u0007b\u0002C+\u000b\u0012\u0005C\u0011\u001d\u0005\b\tG*E\u0011\tCw\r\u0019!Yp\b\u0002\u0005~\"Q!Q\u0001+\u0003\u0006\u0004%\t!b\u0002\t\u0015\t=CK!A!\u0002\u0013)I\u0001C\u0004\u0002jR#\t!b\u0003\t\u000f\u00055H\u000b\"\u0005\u0006\u0012!9!Q\f+\u0005\u0002\t}\u0003b\u0002B4)\u0012\u0005Q\u0011\u0004\u0005\b\u0005k#F\u0011\tB\\\u0011\u001d\u0011I\f\u0016C!\u000bSAqAa2U\t\u0003*i\u0003C\u0004\u0003bR#\t%b\u000f\t\u000f\t-H\u000b\"\u0011\u0006@!9A1\t+\u0005B\u0015m\u0003b\u0002C+)\u0012\u0005S\u0011\u000e\u0005\b\tG\"F\u0011IC;\u0011\u001d)\t\t\u0016C!\u000b\u00073a!b( \u0005\u0015\u0005\u0006B\u0003B\u0003I\n\u0015\r\u0011\"\u0001\u0006&\"Q!q\n3\u0003\u0002\u0003\u0006I!b*\t\u000f\u0005%H\r\"\u0001\u0006*\"9\u0011Q\u001e3\u0005\u0012\u0015=\u0006b\u0002B/I\u0012\u0005!q\f\u0005\b\u0005O\"G\u0011AC\\\u0011\u001d\u0011)\f\u001aC!\u0005oCqA!/e\t\u0003*9\rC\u0004\u0003H\u0012$\t%b3\t\u000f\t\u0005H\r\"\u0011\u0006Z\"9!1\u001e3\u0005B\u0015u\u0007b\u0002C\"I\u0012\u0005S\u0011 \u0005\b\t+\"G\u0011\tD\u0004\u0011\u001d!\u0019\u0007\u001aC!\r'1aA\"\t \u0005\u0019\r\u0002B\u0003B\u0003g\n\u0015\r\u0011\"\u0001\u0007.!Q!qJ:\u0003\u0002\u0003\u0006IAb\f\t\u000f\u0005%8\u000f\"\u0001\u00072!9\u0011Q^:\u0005\u0012\u0019]\u0002b\u0002B/g\u0012\u0005!q\f\u0005\b\u0005O\u001aH\u0011\u0001D \u0011\u001d\u0011)l\u001dC!\u0005oCqA!/t\t\u00032y\u0005C\u0004\u0003HN$\tEb\u0015\t\u000f\t\u00058\u000f\"\u0011\u0007b!9!1^:\u0005B\u0019\u0015\u0004b\u0002C\"g\u0012\u0005c\u0011\u0011\u0005\b\t+\u001aH\u0011\tDH\u0011\u001d!\u0019g\u001dC!\r73aA\"+ \u0005\u0019-\u0006b\u0003B\u0003\u0003\u000b\u0011)\u0019!C\u0001\rkC1Ba\u0014\u0002\u0006\t\u0005\t\u0015!\u0003\u00078\"A\u0011\u0011^A\u0003\t\u00031I\f\u0003\u0005\u0002n\u0006\u0015A\u0011\u0003D`\u0011!\u0011i&!\u0002\u0005\u0002\t}\u0003\u0002\u0003B4\u0003\u000b!\tAb2\t\u0011\tU\u0016Q\u0001C!\u0005oC\u0001B!/\u0002\u0006\u0011\u0005cq\u001b\u0005\t\u0005C\f)\u0001\"\u0011\u0007\\\"A!1^A\u0003\t\u00032y\u000e\u0003\u0005\u0005D\u0005\u0015A\u0011\tD~\u0011!!)&!\u0002\u0005B\u001d-\u0001\u0002\u0003C2\u0003\u000b!\teb\u0006\u0007\r\u001d\u0015rDAD\u0014\u0011-\u0011)!!\t\u0003\u0006\u0004%\ta\"\r\t\u0017\t=\u0013\u0011\u0005B\u0001B\u0003%q1\u0007\u0005\t\u0003S\f\t\u0003\"\u0001\b6!A\u0011Q^A\u0011\t#9Y\u0004\u0003\u0005\u0003^\u0005\u0005B\u0011\u0001B0\u0011!\u00119'!\t\u0005\u0002\u001d\r\u0003\u0002\u0003B[\u0003C!\tEa.\t\u0011\te\u0016\u0011\u0005C!\u000f'B\u0001B!9\u0002\"\u0011\u0005sq\u000b\u0005\t\u0005W\f\t\u0003\"\u0011\b\\!AA1IA\u0011\t\u0003:9\b\u0003\u0005\u0005V\u0005\u0005B\u0011IDD\u0011!!\u0019'!\t\u0005B\u001dMeABDQ?\t9\u0019\u000bC\u0006\u0003\u0006\u0005u\"Q1A\u0005\u0002\u001d\u001d\u0006b\u0003B(\u0003{\u0011\t\u0011)A\u0005\u000fSC\u0001\"!;\u0002>\u0011\u0005q1\u0016\u0005\t\u0003[\fi\u0004\"\u0005\b2\"A!QLA\u001f\t\u0003\u0011y\u0006\u0003\u0005\u0003h\u0005uB\u0011AD]\u0011!\u0011),!\u0010\u0005B\t]\u0006\u0002\u0003B]\u0003{!\te\"3\t\u0011\t\u001d\u0017Q\bC!\u000f\u001bD\u0001B!9\u0002>\u0011\u0005s1\u001c\u0005\t\u0005W\fi\u0004\"\u0011\b`\"AA1IA\u001f\t\u0003:Y\u0010\u0003\u0005\u0005V\u0005uB\u0011\tE\u0005\u0011!!\u0019'!\u0010\u0005B!UaA\u0002E\u0012?\tA)\u0003C\u0006\u0003\u0006\u0005m#Q1A\u0005\u0002!=\u0002b\u0003B(\u00037\u0012\t\u0011)A\u0005\u0011cA\u0001\"!;\u0002\\\u0011\u0005\u00012\u0007\u0005\t\u0003[\fY\u0006\"\u0005\t:!A!QLA.\t\u0003\u0011y\u0006\u0003\u0005\u0003h\u0005mC\u0011\u0001E!\u0011!\u0011),a\u0017\u0005B\t]\u0006\u0002\u0003B]\u00037\"\t\u0005#\u0015\t\u0011\t\u0005\u00181\fC!\u0011+B\u0001Ba;\u0002\\\u0011\u0005\u0003\u0012\f\u0005\n\u0011oz\u0012\u0011!C\u0005\u0011s\u0012\u0001\"\u0011:sCf\u001cV-\u001d\u0006\u0005\u0003o\nI(A\u0005j[6,H/\u00192mK*!\u00111PA?\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0003\u0003\u007f\nQa]2bY\u0006\u001c\u0001!\u0006\u0003\u0002\u0006\u0006M5#\u0004\u0001\u0002\b\u0006\u001d\u0016QVA\\\u0003{\u000b\t\u000e\u0005\u0004\u0002\n\u0006-\u0015qR\u0007\u0003\u0003kJA!!$\u0002v\tY\u0011IY:ue\u0006\u001cGoU3r!\u0011\t\t*a%\r\u0001\u0011A\u0011Q\u0013\u0001\u0005\u0006\u0004\t9JA\u0001B#\u0011\tI*!)\u0011\t\u0005m\u0015QT\u0007\u0003\u0003{JA!a(\u0002~\t9aj\u001c;iS:<\u0007\u0003BAN\u0003GKA!!*\u0002~\t\u0019\u0011I\\=\u0011\r\u0005%\u0015\u0011VAH\u0013\u0011\tY+!\u001e\u0003\u0015%sG-\u001a=fIN+\u0017\u000f\u0005\u0006\u0002\n\u0006=\u0016qRAZ\u0003kKA!!-\u0002v\ti\u0011J\u001c3fq\u0016$7+Z9PaN\u00042!!#\u0001!\u0015\tI\tAAH!)\tI)!/\u0002\u0010\u0006M\u0016QW\u0005\u0005\u0003w\u000b)HA\u000bTiJL7\r^(qi&l\u0017N_3e'\u0016\fx\n]:\u0011\u0015\u0005}\u0016\u0011YAH\u0003g\u000b)-\u0004\u0002\u0002z%!\u00111YA=\u0005})e/\u001b3f]\u000e,\u0017\n^3sC\ndWMR1di>\u0014\u0018\u0010R3gCVdGo\u001d\t\u0005\u0003\u000f\fi-\u0004\u0002\u0002J*!\u00111ZA?\u0003\u001d\u0011XM\u001a7fGRLA!a4\u0002J\nA1\t\\1tgR\u000bw\r\u0005\u0003\u0002T\u0006\rh\u0002BAk\u0003?tA!a6\u0002^6\u0011\u0011\u0011\u001c\u0006\u0005\u00037\f\t)\u0001\u0004=e>|GOP\u0005\u0003\u0003\u007fJA!!9\u0002~\u00059\u0001/Y2lC\u001e,\u0017\u0002BAs\u0003O\u0014AbU3sS\u0006d\u0017N_1cY\u0016TA!!9\u0002~\u00051A(\u001b8jiz\"\"!!.\u0002\u000f\u0015dW-\u001c+bOV\u0011\u0011\u0011\u001f\u0019\u0005\u0003g\f9\u0010\u0005\u0004\u0002H\u00065\u0017Q\u001f\t\u0005\u0003#\u000b9\u0010B\u0006\u0002z\n\t\t\u0011!A\u0003\u0002\u0005]%aA0%c\u0005y\u0011\u000e^3sC\ndWMR1di>\u0014\u00180\u0006\u0002\u0002\u0000B1\u0011q\u0018B\u0001\u0003gKAAa\u0001\u0002z\tQ1+Z9GC\u000e$xN]=\u0002\u0017Ut7/\u00194f\u0003J\u0014\u0018-_\u000b\u0003\u0005\u0013\u0001DAa\u0003\u0003\u0014A1\u00111\u0014B\u0007\u0005#IAAa\u0004\u0002~\t)\u0011I\u001d:bsB!\u0011\u0011\u0013B\n\t-\u0011)\u0002BA\u0001\u0002\u0003\u0015\t!a&\u0003\u0007}##'A\ffm&$WM\\2f\u0013R,'/\u00192mK\u001a\u000b7\r^8ssV\u0011!1\u0004\b\u0004\u0003\u0013s\u0012\u0001C!se\u0006L8+Z9\u0011\u0007\u0005%udE\u0003 \u0005G\u0011I\u0003\u0005\u0003\u0002\u001c\n\u0015\u0012\u0002\u0002B\u0014\u0003{\u0012a!\u00118z%\u00164\u0007CBA`\u0005W\t\u0019,\u0003\u0003\u0003.\u0005e$!I*ue&\u001cGo\u00149uS6L'0\u001a3DY\u0006\u001c8\u000fV1h'\u0016\fh)Y2u_JLHC\u0001B\u0010\u0003!)h\u000e^1hO\u0016$\u0017!C;oi\u0006<w-\u001a3!\u0003%)W\u000e\u001d;z\u00136\u0004H.\u0006\u0002\u0003:A)!1\u0004\u0016\u0002\u001a\n)qN\u001a*fMV!!q\bB#'\rQ#\u0011\t\t\u0006\u0003\u0013\u0003!1\t\t\u0005\u0003#\u0013)\u0005B\u0004\u0003H)\u0012\rA!\u0013\u0003\u0003Q\u000bB!!'\u0003$U\u0011!Q\n\t\u0007\u00037\u0013iAa\u0011\u0002\u0019Ut7/\u00194f\u0003J\u0014\u0018-\u001f\u0011\u0015\t\tM#q\u000b\t\u0006\u0005+R#1I\u0007\u0002?!9!QA\u0017A\u0002\t5SC\u0001B.!\u0019\t9-!4\u0003D\u00051A.\u001a8hi\",\"A!\u0019\u0011\t\u0005m%1M\u0005\u0005\u0005K\niHA\u0002J]R\fQ!\u00199qYf$BAa\u0011\u0003l!9!Q\u000e\u0019A\u0002\t\u0005\u0014!A5)\u000bA\u0012\tH! \u0011\r\u0005m%1\u000fB<\u0013\u0011\u0011)(! \u0003\rQD'o\\<t!\u0011\t\u0019N!\u001f\n\t\tm\u0014q\u001d\u0002\u001f\u0003J\u0014\u0018-_%oI\u0016Dx*\u001e;PM\n{WO\u001c3t\u000bb\u001cW\r\u001d;j_:\ftA\bB@\u0005\u001f\u0013\u0019\f\u0005\u0003\u0003\u0002\n%e\u0002\u0002BB\u0005\u000b\u0003B!a6\u0002~%!!qQA?\u0003\u0019\u0001&/\u001a3fM&!!1\u0012BG\u0005\u0019\u0019FO]5oO*!!qQA?c%\u0019#\u0011\u0013BL\u0005S\u0013I*\u0006\u0003\u0003\u0014\nUUC\u0001B@\t!\u00119%!!C\u0002\t}\u0015\u0002\u0002BM\u00057\u000b1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\n$\u0002\u0002BO\u0003{\na\u0001\u001e5s_^\u001c\u0018\u0003BAM\u0005C\u0003BAa)\u0003&:!\u00111TAp\u0013\u0011\u00119+a:\u0003\u0013QC'o\\<bE2,\u0017'C\u0012\u0003,\n5&q\u0016BO\u001d\u0011\tYJ!,\n\t\tu\u0015QP\u0019\bE\u0005m\u0015Q\u0010BY\u0005\u0015\u00198-\u00197bc\r1#qO\u0001\tQ\u0006\u001c\bnQ8eKR\u0011!\u0011M\u0001\u0007KF,\u0018\r\\:\u0015\t\tu&1\u0019\t\u0005\u00037\u0013y,\u0003\u0003\u0003B\u0006u$a\u0002\"p_2,\u0017M\u001c\u0005\b\u0005\u000b\u0014\u0004\u0019AAQ\u0003\u0011!\b.\u0019;\u0002\rM|'\u000f^3e+\u0011\u0011YMa7\u0015\t\t5'q\u001a\t\u0006\u00057Q#1\t\u0005\b\u0005#\u001c\u00049\u0001Bj\u0003\ry'\u000f\u001a\t\u0007\u0003'\u0014)N!7\n\t\t]\u0017q\u001d\u0002\t\u001fJ$WM]5oOB!\u0011\u0011\u0013Bn\t\u001d\u0011in\rb\u0001\u0005?\u0014\u0011AQ\t\u0005\u0005\u0007\n\t+\u0001\u0005ji\u0016\u0014\u0018\r^8s+\t\u0011)\u000f\u0005\u0004\u0002@\n\u001d(1I\u0005\u0005\u0005S\fIH\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003\u001d\u0019H/\u001a9qKJ,BAa<\u0003zR!!\u0011_B\u0010%\u0019\u0011\u0019Pa>\u0004\u000e\u00191!Q\u001f\u0016\u0001\u0005c\u0014A\u0002\u0010:fM&tW-\\3oiz\u0002B!!%\u0003z\u00129!1`\u001bC\u0002\tu(!A*\u0012\t\u0005e%q \u0019\u0005\u0007\u0003\u0019I\u0001\u0005\u0004\u0002@\u000e\r1qA\u0005\u0005\u0007\u000b\tIHA\u0004Ti\u0016\u0004\b/\u001a:\u0011\t\u0005E5\u0011\u0002\u0003\r\u0007\u0017\u0011I0!A\u0001\u0002\u000b\u0005\u0011q\u0013\u0002\u0004?\u0012\"\u0004\u0003BB\b\u00073qAa!\u0005\u0004\u00169!\u0011Q[B\n\u0013\u0011\tY(! \n\t\r]\u0011\u0011P\u0001\b'R,\u0007\u000f]3s\u0013\u0011\u0019Yb!\b\u0003\u001d\u00153g-[2jK:$8\u000b\u001d7ji*!1qCA=\u0011\u001d\u0019\t#\u000ea\u0002\u0007G\tQa\u001d5ba\u0016\u0004\u0002\"a0\u0004&\t\r#q_\u0005\u0005\u0007O\tIH\u0001\u0007Ti\u0016\u0004\b/\u001a:TQ\u0006\u0004X\rK\u0004+\u0007W\u0019\tda\r\u0011\t\u0005m5QF\u0005\u0005\u0007_\tiH\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t1!A\u0003f[B$\u00180\u0006\u0003\u0004:\r}B\u0003BB\u001e\u0007\u0003\u0002R!!#\u0001\u0007{\u0001B!!%\u0004@\u00119\u0011Q\u0013\u0013C\u0002\u0005]\u0005\"CB\"I\u0005\u0005\t9AB#\u0003))g/\u001b3f]\u000e,G%\r\t\u0007\u0003\u000f\fim!\u0010\u0002\t\u0019\u0014x.\\\u000b\u0005\u0007\u0017\u001a\u0019\u0006\u0006\u0003\u0004N\rmC\u0003BB(\u0007+\u0002R!!#\u0001\u0007#\u0002B!!%\u0004T\u00119\u0011QS\u0013C\u0002\u0005]\u0005bBB,K\u0001\u000f1\u0011L\u0001\u0004i\u0006<\u0007CBAd\u0003\u001b\u001c\t\u0006C\u0004\u0004^\u0015\u0002\raa\u0018\u0002\u0005%$\bCBA`\u0007C\u001a\t&\u0003\u0003\u0004d\u0005e$\u0001D%uKJ\f'\r\\3P]\u000e,\u0017A\u00038fo\n+\u0018\u000e\u001c3feV!1\u0011NB=)\u0011\u0019Yg! \u0011\u0011\r541OB<\u0007wj!aa\u001c\u000b\t\rE\u0014\u0011P\u0001\b[V$\u0018M\u00197f\u0013\u0011\u0019)ha\u001c\u0003\u000f\t+\u0018\u000e\u001c3feB!\u0011\u0011SB=\t\u001d\t)J\nb\u0001\u0003/\u0003R!!#\u0001\u0007oB\u0011ba '\u0003\u0003\u0005\u001da!!\u0002\u0015\u00154\u0018\u000eZ3oG\u0016$#\u0007\u0005\u0004\u0002H\u000657qO\u0001\u0005M&dG.\u0006\u0003\u0004\b\u000eEE\u0003BBE\u0007G#Baa#\u0004\u001aR!1QRBJ!\u0015\tI\tABH!\u0011\t\tj!%\u0005\u000f\u0005UuE1\u0001\u0002\u0018\"I1QS\u0014\u0002\u0002\u0003\u000f1qS\u0001\u000bKZLG-\u001a8dK\u0012\u001a\u0004CBAd\u0003\u001b\u001cy\t\u0003\u0005\u0004\u001c\u001e\"\t\u0019ABO\u0003\u0011)G.Z7\u0011\r\u0005m5qTBH\u0013\u0011\u0019\t+! \u0003\u0011q\u0012\u0017P\\1nKzBqa!*(\u0001\u0004\u0011\t'A\u0001o\u0003!!\u0018MY;mCR,W\u0003BBV\u0007k#Ba!,\u0004HR!1qVB_)\u0011\u0019\tla.\u0011\u000b\u0005%\u0005aa-\u0011\t\u0005E5Q\u0017\u0003\b\u0003+C#\u0019AAL\u0011%\u0019I\fKA\u0001\u0002\b\u0019Y,\u0001\u0006fm&$WM\\2fIQ\u0002b!a2\u0002N\u000eM\u0006bBB`Q\u0001\u00071\u0011Y\u0001\u0002MBA\u00111TBb\u0005C\u001a\u0019,\u0003\u0003\u0004F\u0006u$!\u0003$v]\u000e$\u0018n\u001c82\u0011\u001d\u0019)\u000b\u000ba\u0001\u0005C\nq\"\u001e8tC\u001a,wK]1q\u0003J\u0014\u0018-_\u000b\u0005\u0007\u001b\u001c\u0019\u000e\u0006\u0003\u0004P\u000eU\u0007#BAE\u0001\rE\u0007\u0003BAI\u0007'$qAa\u0012*\u0005\u0004\t9\nC\u0004\u0004X&\u0002\ra!7\u0002\u0003a\u0004b!a'\u0003\u000e\rE'AB8g\u0005f$XmE\u00027\u0007?\u0004R!!#\u0001\u0007C\u0004B!a'\u0004d&!1Q]A?\u0005\u0011\u0011\u0015\u0010^3\u0016\u0005\r%\bCBAN\u0005\u001b\u0019\t\u000f\u0006\u0003\u0004n\u000e=\bc\u0001B+m!9!QA\u001dA\u0002\r%XCABz!\u0011\u0019)pa?\u000f\t\u0005\u001d7q_\u0005\u0005\u0007s\fI-A\bNC:Lg-Z:u\r\u0006\u001cGo\u001c:z\u0013\u0011\u0019ipa@\u0003\u0019\tKH/Z'b]&4Wm\u001d;\u000b\t\re\u0018\u0011\u001a\u000b\u0005\u0007C$\u0019\u0001C\u0004\u0003nq\u0002\rA!\u0019)\u000bq\u0012\t\bb\u00022\u000fy\u0011y\b\"\u0003\u0005\u0010EJ1E!%\u0003\u0018\u0012-!\u0011T\u0019\nG\t-&Q\u0016C\u0007\u0005;\u000btAIAN\u0003{\u0012\t,M\u0002'\u0005o\"BA!0\u0005\u0014!9!Q\u0019 A\u0002\u0005\u0005V\u0003\u0002C\f\t?!Baa8\u0005\u001a!9!\u0011[ A\u0004\u0011m\u0001CBAj\u0005+$i\u0002\u0005\u0003\u0002\u0012\u0012}Aa\u0002Bo\u007f\t\u0007A\u0011E\t\u0005\u0007C\f\t+\u0006\u0002\u0005&A1\u0011q\u0018Bt\u0007C,B\u0001\"\u000b\u00052Q!A1\u0006C %\u0019!i\u0003b\f\u0004\u000e\u00191!Q\u001f\u001c\u0001\tW\u0001B!!%\u00052\u00119!1`!C\u0002\u0011M\u0012\u0003BAM\tk\u0001D\u0001b\u000e\u0005<A1\u0011qXB\u0002\ts\u0001B!!%\u0005<\u0011aAQ\bC\u0019\u0003\u0003\u0005\tQ!\u0001\u0002\u0018\n\u0019q\fJ\u001b\t\u000f\r\u0005\u0012\tq\u0001\u0005BAA\u0011qXB\u0013\u0007C$y#A\u0004va\u0012\fG/\u001a3\u0016\t\u0011\u001dCQ\n\u000b\u0007\t\u0013\"y\u0005b\u0015\u0011\u000b\u0005%\u0005\u0001b\u0013\u0011\t\u0005EEQ\n\u0003\b\u0005;\u0014%\u0019\u0001C\u0011\u0011\u001d!\tF\u0011a\u0001\u0005C\nQ!\u001b8eKbDqaa'C\u0001\u0004!Y%\u0001\u0005baB,g\u000eZ3e+\u0011!I\u0006b\u0018\u0015\t\u0011mC\u0011\r\t\u0006\u0003\u0013\u0003AQ\f\t\u0005\u0003##y\u0006B\u0004\u0003^\u000e\u0013\r\u0001\"\t\t\u000f\rm5\t1\u0001\u0005^\u0005I\u0001O]3qK:$W\rZ\u000b\u0005\tO\"i\u0007\u0006\u0003\u0005j\u0011=\u0004#BAE\u0001\u0011-\u0004\u0003BAI\t[\"qA!8E\u0005\u0004!\t\u0003C\u0004\u0004\u001c\u0012\u0003\r\u0001b\u001b)\u000fY\u001aYc!\r\u00044\t9qNZ*i_J$8cA#\u0005xA)\u0011\u0011\u0012\u0001\u0005zA!\u00111\u0014C>\u0013\u0011!i(! \u0003\u000bMCwN\u001d;\u0016\u0005\u0011\u0005\u0005CBAN\u0005\u001b!I\b\u0006\u0003\u0005\u0006\u0012\u001d\u0005c\u0001B+\u000b\"9!Q\u0001%A\u0002\u0011\u0005UC\u0001CF!\u0011\u0019)\u0010\"$\n\t\u0011=5q \u0002\u000e'\"|'\u000f^'b]&4Wm\u001d;\u0015\t\u0011eD1\u0013\u0005\b\u0005[Z\u0005\u0019\u0001B1Q\u0015Y%\u0011\u000fCLc\u001dq\"q\u0010CM\t?\u000b\u0014b\tBI\u0005/#YJ!'2\u0013\r\u0012YK!,\u0005\u001e\nu\u0015g\u0002\u0012\u0002\u001c\u0006u$\u0011W\u0019\u0004M\t]D\u0003\u0002B_\tGCqA!2N\u0001\u0004\t\t+\u0006\u0003\u0005(\u0012=F\u0003\u0002C<\tSCqA!5O\u0001\b!Y\u000b\u0005\u0004\u0002T\nUGQ\u0016\t\u0005\u0003##y\u000bB\u0004\u0003^:\u0013\r\u0001\"-\u0012\t\u0011e\u0014\u0011U\u000b\u0003\tk\u0003b!a0\u0003h\u0012eT\u0003\u0002C]\t\u0003$B\u0001b/\u0005PJ1AQ\u0018C`\u0007\u001b1aA!>F\u0001\u0011m\u0006\u0003BAI\t\u0003$qAa?Q\u0005\u0004!\u0019-\u0005\u0003\u0002\u001a\u0012\u0015\u0007\u0007\u0002Cd\t\u0017\u0004b!a0\u0004\u0004\u0011%\u0007\u0003BAI\t\u0017$A\u0002\"4\u0005B\u0006\u0005\t\u0011!B\u0001\u0003/\u00131a\u0018\u00137\u0011\u001d\u0019\t\u0003\u0015a\u0002\t#\u0004\u0002\"a0\u0004&\u0011eDqX\u000b\u0005\t+$Y\u000e\u0006\u0004\u0005X\u0012uGq\u001c\t\u0006\u0003\u0013\u0003A\u0011\u001c\t\u0005\u0003##Y\u000eB\u0004\u0003^F\u0013\r\u0001\"-\t\u000f\u0011E\u0013\u000b1\u0001\u0003b!911T)A\u0002\u0011eW\u0003\u0002Cr\tS$B\u0001\":\u0005lB)\u0011\u0011\u0012\u0001\u0005hB!\u0011\u0011\u0013Cu\t\u001d\u0011iN\u0015b\u0001\tcCqaa'S\u0001\u0004!9/\u0006\u0003\u0005p\u0012UH\u0003\u0002Cy\to\u0004R!!#\u0001\tg\u0004B!!%\u0005v\u00129!Q\\*C\u0002\u0011E\u0006bBBN'\u0002\u0007A1\u001f\u0015\b\u000b\u000e-2\u0011GB\u001a\u0005\u0019ygm\u00115beN\u0019A\u000bb@\u0011\u000b\u0005%\u0005!\"\u0001\u0011\t\u0005mU1A\u0005\u0005\u000b\u000b\tiH\u0001\u0003DQ\u0006\u0014XCAC\u0005!\u0019\tYJ!\u0004\u0006\u0002Q!QQBC\b!\r\u0011)\u0006\u0016\u0005\b\u0005\u000b9\u0006\u0019AC\u0005+\t)\u0019\u0002\u0005\u0003\u0004v\u0016U\u0011\u0002BC\f\u0007\u007f\u0014Ab\u00115be6\u000bg.\u001b4fgR$B!\"\u0001\u0006\u001c!9!Q\u000e.A\u0002\t\u0005\u0004&\u0002.\u0003r\u0015}\u0011g\u0002\u0010\u0003\u0000\u0015\u0005RqE\u0019\nG\tE%qSC\u0012\u00053\u000b\u0014b\tBV\u0005[+)C!(2\u000f\t\nY*! \u00032F\u001aaEa\u001e\u0015\t\tuV1\u0006\u0005\b\u0005\u000bd\u0006\u0019AAQ+\u0011)y#b\u000e\u0015\t\u0011}X\u0011\u0007\u0005\b\u0005#l\u00069AC\u001a!\u0019\t\u0019N!6\u00066A!\u0011\u0011SC\u001c\t\u001d\u0011i.\u0018b\u0001\u000bs\tB!\"\u0001\u0002\"V\u0011QQ\b\t\u0007\u0003\u007f\u00139/\"\u0001\u0016\t\u0015\u0005S\u0011\n\u000b\u0005\u000b\u0007*9F\u0005\u0004\u0006F\u0015\u001d3Q\u0002\u0004\u0007\u0005k$\u0006!b\u0011\u0011\t\u0005EU\u0011\n\u0003\b\u0005w|&\u0019AC&#\u0011\tI*\"\u00141\t\u0015=S1\u000b\t\u0007\u0003\u007f\u001b\u0019!\"\u0015\u0011\t\u0005EU1\u000b\u0003\r\u000b+*I%!A\u0001\u0002\u000b\u0005\u0011q\u0013\u0002\u0004?\u0012:\u0004bBB\u0011?\u0002\u000fQ\u0011\f\t\t\u0003\u007f\u001b)#\"\u0001\u0006HU!QQLC2)\u0019)y&\"\u001a\u0006hA)\u0011\u0011\u0012\u0001\u0006bA!\u0011\u0011SC2\t\u001d\u0011i\u000e\u0019b\u0001\u000bsAq\u0001\"\u0015a\u0001\u0004\u0011\t\u0007C\u0004\u0004\u001c\u0002\u0004\r!\"\u0019\u0016\t\u0015-T\u0011\u000f\u000b\u0005\u000b[*\u0019\bE\u0003\u0002\n\u0002)y\u0007\u0005\u0003\u0002\u0012\u0016EDa\u0002BoC\n\u0007Q\u0011\b\u0005\b\u00077\u000b\u0007\u0019AC8+\u0011)9(\" \u0015\t\u0015eTq\u0010\t\u0006\u0003\u0013\u0003Q1\u0010\t\u0005\u0003#+i\bB\u0004\u0003^\n\u0014\r!\"\u000f\t\u000f\rm%\r1\u0001\u0006|\u0005I\u0011\r\u001a3TiJLgn\u001a\u000b\u000b\u000b\u000b+9)\"%\u0006\u0016\u0016ee\u0002BAI\u000b\u000fCq!\"#d\u0001\u0004)Y)\u0001\u0002tEB!\u00111[CG\u0013\u0011)y)a:\u0003\u001bM#(/\u001b8h\u0005VLG\u000eZ3s\u0011\u001d)\u0019j\u0019a\u0001\u0005\u007f\nQa\u001d;beRDq!b&d\u0001\u0004\u0011y(A\u0002tKBDq!b'd\u0001\u0004\u0011y(A\u0002f]\u0012Ds\u0001VB\u0016\u0007c\u0019\u0019DA\u0003pM&sGoE\u0002e\u000bG\u0003R!!#\u0001\u0005C*\"!b*\u0011\r\u0005m%Q\u0002B1)\u0011)Y+\",\u0011\u0007\tUC\rC\u0004\u0003\u0006\u001d\u0004\r!b*\u0016\u0005\u0015E\u0006\u0003BB{\u000bgKA!\".\u0004\u0000\nY\u0011J\u001c;NC:Lg-Z:u)\u0011\u0011\t'\"/\t\u000f\t5$\u000e1\u0001\u0003b!*!N!\u001d\u0006>F:aDa \u0006@\u0016\u0015\u0017'C\u0012\u0003\u0012\n]U\u0011\u0019BMc%\u0019#1\u0016BW\u000b\u0007\u0014i*M\u0004#\u00037\u000biH!-2\u0007\u0019\u00129\b\u0006\u0003\u0003>\u0016%\u0007b\u0002BcY\u0002\u0007\u0011\u0011U\u000b\u0005\u000b\u001b,)\u000e\u0006\u0003\u0006$\u0016=\u0007b\u0002Bi[\u0002\u000fQ\u0011\u001b\t\u0007\u0003'\u0014).b5\u0011\t\u0005EUQ\u001b\u0003\b\u0005;l'\u0019ACl#\u0011\u0011\t'!)\u0016\u0005\u0015m\u0007CBA`\u0005O\u0014\t'\u0006\u0003\u0006`\u0016\u001dH\u0003BCq\u000bk\u0014b!b9\u0006f\u000e5aA\u0002B{I\u0002)\t\u000f\u0005\u0003\u0002\u0012\u0016\u001dHa\u0002B~_\n\u0007Q\u0011^\t\u0005\u00033+Y\u000f\r\u0003\u0006n\u0016E\bCBA`\u0007\u0007)y\u000f\u0005\u0003\u0002\u0012\u0016EH\u0001DCz\u000bO\f\t\u0011!A\u0003\u0002\u0005]%aA0%q!91\u0011E8A\u0004\u0015]\b\u0003CA`\u0007K\u0011\t'\":\u0016\t\u0015mh\u0011\u0001\u000b\u0007\u000b{4\u0019A\"\u0002\u0011\u000b\u0005%\u0005!b@\u0011\t\u0005Ee\u0011\u0001\u0003\b\u0005;\u0004(\u0019ACl\u0011\u001d!\t\u0006\u001da\u0001\u0005CBqaa'q\u0001\u0004)y0\u0006\u0003\u0007\n\u0019=A\u0003\u0002D\u0006\r#\u0001R!!#\u0001\r\u001b\u0001B!!%\u0007\u0010\u00119!Q\\9C\u0002\u0015]\u0007bBBNc\u0002\u0007aQB\u000b\u0005\r+1Y\u0002\u0006\u0003\u0007\u0018\u0019u\u0001#BAE\u0001\u0019e\u0001\u0003BAI\r7!qA!8s\u0005\u0004)9\u000eC\u0004\u0004\u001cJ\u0004\rA\"\u0007)\u000f\u0011\u001cYc!\r\u00044\t1qN\u001a'p]\u001e\u001c2a\u001dD\u0013!\u0015\tI\t\u0001D\u0014!\u0011\tYJ\"\u000b\n\t\u0019-\u0012Q\u0010\u0002\u0005\u0019>tw-\u0006\u0002\u00070A1\u00111\u0014B\u0007\rO!BAb\r\u00076A\u0019!QK:\t\u000f\t\u0015a\u000f1\u0001\u00070U\u0011a\u0011\b\t\u0005\u0007k4Y$\u0003\u0003\u0007>\r}(\u0001\u0004'p]\u001el\u0015M\\5gKN$H\u0003\u0002D\u0014\r\u0003BqA!\u001cz\u0001\u0004\u0011\t\u0007K\u0003z\u0005c2)%M\u0004\u001f\u0005\u007f29E\"\u00142\u0013\r\u0012\tJa&\u0007J\te\u0015'C\u0012\u0003,\n5f1\nBOc\u001d\u0011\u00131TA?\u0005c\u000b4A\nB<)\u0011\u0011iL\"\u0015\t\u000f\t\u00157\u00101\u0001\u0002\"V!aQ\u000bD/)\u00111)Cb\u0016\t\u000f\tEG\u0010q\u0001\u0007ZA1\u00111\u001bBk\r7\u0002B!!%\u0007^\u00119!Q\u001c?C\u0002\u0019}\u0013\u0003\u0002D\u0014\u0003C+\"Ab\u0019\u0011\r\u0005}&q\u001dD\u0014+\u001119Gb\u001c\u0015\t\u0019%dQ\u0010\n\u0007\rW2ig!\u0004\u0007\r\tU8\u000f\u0001D5!\u0011\t\tJb\u001c\u0005\u000f\tmhP1\u0001\u0007rE!\u0011\u0011\u0014D:a\u00111)H\"\u001f\u0011\r\u0005}61\u0001D<!\u0011\t\tJ\"\u001f\u0005\u0019\u0019mdqNA\u0001\u0002\u0003\u0015\t!a&\u0003\u0007}#\u0013\bC\u0004\u0004\"y\u0004\u001dAb \u0011\u0011\u0005}6Q\u0005D\u0014\r[*BAb!\u0007\nR1aQ\u0011DF\r\u001b\u0003R!!#\u0001\r\u000f\u0003B!!%\u0007\n\u00129!Q\\@C\u0002\u0019}\u0003b\u0002C)\u007f\u0002\u0007!\u0011\r\u0005\b\u00077{\b\u0019\u0001DD+\u00111\tJb&\u0015\t\u0019Me\u0011\u0014\t\u0006\u0003\u0013\u0003aQ\u0013\t\u0005\u0003#39\n\u0002\u0005\u0003^\u0006\u0005!\u0019\u0001D0\u0011!\u0019Y*!\u0001A\u0002\u0019UU\u0003\u0002DO\rG#BAb(\u0007&B)\u0011\u0011\u0012\u0001\u0007\"B!\u0011\u0011\u0013DR\t!\u0011i.a\u0001C\u0002\u0019}\u0003\u0002CBN\u0003\u0007\u0001\rA\"))\u000fM\u001cYc!\r\u00044\t9qN\u001a$m_\u0006$8\u0003BA\u0003\r[\u0003R!!#\u0001\r_\u0003B!a'\u00072&!a1WA?\u0005\u00151En\\1u+\t19\f\u0005\u0004\u0002\u001c\n5aq\u0016\u000b\u0005\rw3i\f\u0005\u0003\u0003V\u0005\u0015\u0001\u0002\u0003B\u0003\u0003\u0017\u0001\rAb.\u0016\u0005\u0019\u0005\u0007\u0003BB{\r\u0007LAA\"2\u0004\u0000\nia\t\\8bi6\u000bg.\u001b4fgR$BAb,\u0007J\"A!QNA\t\u0001\u0004\u0011\t\u0007\u000b\u0004\u0002\u0012\tEdQZ\u0019\b=\t}dq\u001aDkc%\u0019#\u0011\u0013BL\r#\u0014I*M\u0005$\u0005W\u0013iKb5\u0003\u001eF:!%a'\u0002~\tE\u0016g\u0001\u0014\u0003xQ!!Q\u0018Dm\u0011!\u0011)-!\u0006A\u0002\u0005\u0005VC\u0001Do!\u0019\tyLa:\u00070V!a\u0011\u001dDu)\u00111\u0019Ob>\u0013\r\u0019\u0015hq]B\u0007\r\u001d\u0011)0!\u0002\u0001\rG\u0004B!!%\u0007j\u0012A!1`A\r\u0005\u00041Y/\u0005\u0003\u0002\u001a\u001a5\b\u0007\u0002Dx\rg\u0004b!a0\u0004\u0004\u0019E\b\u0003BAI\rg$AB\">\u0007j\u0006\u0005\t\u0011!B\u0001\u0003/\u0013Aa\u0018\u00132a!A1\u0011EA\r\u0001\b1I\u0010\u0005\u0005\u0002@\u000e\u0015bq\u0016Dt+\u00111ipb\u0001\u0015\r\u0019}xqAD\u0005!\u0015\tI\tAD\u0001!\u0011\t\tjb\u0001\u0005\u0011\tu\u00171\u0004b\u0001\u000f\u000b\tBAb,\u0002\"\"AA\u0011KA\u000e\u0001\u0004\u0011\t\u0007\u0003\u0005\u0004\u001c\u0006m\u0001\u0019AD\u0001+\u00119iab\u0005\u0015\t\u001d=qQ\u0003\t\u0006\u0003\u0013\u0003q\u0011\u0003\t\u0005\u0003#;\u0019\u0002\u0002\u0005\u0003^\u0006u!\u0019AD\u0003\u0011!\u0019Y*!\bA\u0002\u001dEQ\u0003BD\r\u000f?!Bab\u0007\b\"A)\u0011\u0011\u0012\u0001\b\u001eA!\u0011\u0011SD\u0010\t!\u0011i.a\bC\u0002\u001d\u0015\u0001\u0002CBN\u0003?\u0001\ra\"\b)\u0011\u0005\u001511FB\u0019\u0007g\u0011\u0001b\u001c4E_V\u0014G.Z\n\u0005\u0003C9I\u0003E\u0003\u0002\n\u00029Y\u0003\u0005\u0003\u0002\u001c\u001e5\u0012\u0002BD\u0018\u0003{\u0012a\u0001R8vE2,WCAD\u001a!\u0019\tYJ!\u0004\b,Q!qqGD\u001d!\u0011\u0011)&!\t\t\u0011\t\u0015\u0011q\u0005a\u0001\u000fg)\"a\"\u0010\u0011\t\rUxqH\u0005\u0005\u000f\u0003\u001ayP\u0001\bE_V\u0014G.Z'b]&4Wm\u001d;\u0015\t\u001d-rQ\t\u0005\t\u0005[\ni\u00031\u0001\u0003b!2\u0011Q\u0006B9\u000f\u0013\ntA\bB@\u000f\u0017:\t&M\u0005$\u0005#\u00139j\"\u0014\u0003\u001aFJ1Ea+\u0003.\u001e=#QT\u0019\bE\u0005m\u0015Q\u0010BYc\r1#q\u000f\u000b\u0005\u0005{;)\u0006\u0003\u0005\u0003F\u0006E\u0002\u0019AAQ+\t9I\u0006\u0005\u0004\u0002@\n\u001dx1F\u000b\u0005\u000f;:)\u0007\u0006\u0003\b`\u001dM$CBD1\u000fG\u001aiAB\u0004\u0003v\u0006\u0005\u0002ab\u0018\u0011\t\u0005EuQ\r\u0003\t\u0005w\f)D1\u0001\bhE!\u0011\u0011TD5a\u00119Ygb\u001c\u0011\r\u0005}61AD7!\u0011\t\tjb\u001c\u0005\u0019\u001dEtQMA\u0001\u0002\u0003\u0015\t!a&\u0003\t}#\u0013'\r\u0005\t\u0007C\t)\u0004q\u0001\bvAA\u0011qXB\u0013\u000fW9\u0019'\u0006\u0003\bz\u001d}DCBD>\u000f\u0007;)\tE\u0003\u0002\n\u00029i\b\u0005\u0003\u0002\u0012\u001e}D\u0001\u0003Bo\u0003o\u0011\ra\"!\u0012\t\u001d-\u0012\u0011\u0015\u0005\t\t#\n9\u00041\u0001\u0003b!A11TA\u001c\u0001\u00049i(\u0006\u0003\b\n\u001e=E\u0003BDF\u000f#\u0003R!!#\u0001\u000f\u001b\u0003B!!%\b\u0010\u0012A!Q\\A\u001d\u0005\u00049\t\t\u0003\u0005\u0004\u001c\u0006e\u0002\u0019ADG+\u00119)jb'\u0015\t\u001d]uQ\u0014\t\u0006\u0003\u0013\u0003q\u0011\u0014\t\u0005\u0003#;Y\n\u0002\u0005\u0003^\u0006m\"\u0019ADA\u0011!\u0019Y*a\u000fA\u0002\u001de\u0005\u0006CA\u0011\u0007W\u0019\tda\r\u0003\u0013=4'i\\8mK\u0006t7\u0003BA\u001f\u000fK\u0003R!!#\u0001\u0005{+\"a\"+\u0011\r\u0005m%Q\u0002B_)\u00119ikb,\u0011\t\tU\u0013Q\b\u0005\t\u0005\u000b\t\u0019\u00051\u0001\b*V\u0011q1\u0017\t\u0005\u0007k<),\u0003\u0003\b8\u000e}(a\u0004\"p_2,\u0017M\\'b]&4Wm\u001d;\u0015\t\tuv1\u0018\u0005\t\u0005[\nI\u00051\u0001\u0003b!2\u0011\u0011\nB9\u000f\u007f\u000btA\bB@\u000f\u0003<9-M\u0005$\u0005#\u00139jb1\u0003\u001aFJ1Ea+\u0003.\u001e\u0015'QT\u0019\bE\u0005m\u0015Q\u0010BYc\r1#q\u000f\u000b\u0005\u0005{;Y\r\u0003\u0005\u0003F\u00065\u0003\u0019AAQ+\u00119ymb6\u0015\t\u001d\u0015v\u0011\u001b\u0005\t\u0005#\fy\u0005q\u0001\bTB1\u00111\u001bBk\u000f+\u0004B!!%\bX\u0012A!Q\\A(\u0005\u00049I.\u0005\u0003\u0003>\u0006\u0005VCADo!\u0019\tyLa:\u0003>V!q\u0011]Du)\u00119\u0019ob>\u0013\r\u001d\u0015xq]B\u0007\r\u001d\u0011)0!\u0010\u0001\u000fG\u0004B!!%\bj\u0012A!1`A*\u0005\u00049Y/\u0005\u0003\u0002\u001a\u001e5\b\u0007BDx\u000fg\u0004b!a0\u0004\u0004\u001dE\b\u0003BAI\u000fg$Ab\">\bj\u0006\u0005\t\u0011!B\u0001\u0003/\u0013Aa\u0018\u00132e!A1\u0011EA*\u0001\b9I\u0010\u0005\u0005\u0002@\u000e\u0015\"QXDt+\u00119i\u0010c\u0001\u0015\r\u001d}\bR\u0001E\u0004!\u0015\tI\t\u0001E\u0001!\u0011\t\t\nc\u0001\u0005\u0011\tu\u0017Q\u000bb\u0001\u000f3D\u0001\u0002\"\u0015\u0002V\u0001\u0007!\u0011\r\u0005\t\u00077\u000b)\u00061\u0001\t\u0002U!\u00012\u0002E\t)\u0011Ai\u0001c\u0005\u0011\u000b\u0005%\u0005\u0001c\u0004\u0011\t\u0005E\u0005\u0012\u0003\u0003\t\u0005;\f9F1\u0001\bZ\"A11TA,\u0001\u0004Ay!\u0006\u0003\t\u0018!uA\u0003\u0002E\r\u0011?\u0001R!!#\u0001\u00117\u0001B!!%\t\u001e\u0011A!Q\\A-\u0005\u00049I\u000e\u0003\u0005\u0004\u001c\u0006e\u0003\u0019\u0001E\u000eQ!\tida\u000b\u00042\rM\"AB8g+:LGo\u0005\u0003\u0002\\!\u001d\u0002#BAE\u0001!%\u0002\u0003BAN\u0011WIA\u0001#\f\u0002~\t!QK\\5u+\tA\t\u0004\u0005\u0004\u0002\u001c\n5\u0001\u0012\u0006\u000b\u0005\u0011kA9\u0004\u0005\u0003\u0003V\u0005m\u0003\u0002\u0003B\u0003\u0003C\u0002\r\u0001#\r\u0016\u0005!m\u0002\u0003BB{\u0011{IA\u0001c\u0010\u0004\u0000\naQK\\5u\u001b\u0006t\u0017NZ3tiR!\u0001\u0012\u0006E\"\u0011!\u0011i'a\u001aA\u0002\t\u0005\u0004FBA4\u0005cB9%M\u0004\u001f\u0005\u007fBI\u0005c\u00142\u0013\r\u0012\tJa&\tL\te\u0015'C\u0012\u0003,\n5\u0006R\nBOc\u001d\u0011\u00131TA?\u0005c\u000b4A\nB<)\u0011\u0011i\fc\u0015\t\u0011\t\u0015\u00171\u000ea\u0001\u0003C+\"\u0001c\u0016\u0011\r\u0005}&q\u001dE\u0015+\u0011AY\u0006c\u0019\u0015\t!u\u0003\u0012\u000f\n\u0007\u0011?B\tg!\u0004\u0007\u000f\tU\u00181\f\u0001\t^A!\u0011\u0011\u0013E2\t!\u0011Y0a\u001cC\u0002!\u0015\u0014\u0003BAM\u0011O\u0002D\u0001#\u001b\tnA1\u0011qXB\u0002\u0011W\u0002B!!%\tn\u0011a\u0001r\u000eE2\u0003\u0003\u0005\tQ!\u0001\u0002\u0018\n!q\fJ\u00194\u0011!\u0019\t#a\u001cA\u0004!M\u0004\u0003CA`\u0007KAI\u0003#\u0019)\u0011\u0005m31FB\u0019\u0007g\tAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"\u0001c\u001f\u0011\t!u\u0004rQ\u0007\u0003\u0011\u007fRA\u0001#!\t\u0004\u0006!A.\u00198h\u0015\tA))\u0001\u0003kCZ\f\u0017\u0002\u0002EE\u0011\u007f\u0012aa\u00142kK\u000e$\bfB\u0010\u0004,\rE21\u0007\u0015\b=\r-2\u0011GB\u001a\u0003AIG/\u001a:bE2,WI^5eK:\u001cW-\u0006\u0002\t\u0014B1\u0011qYAg\u0011+SC!a$\t\u0018.\u0012\u0001\u0012\u0014\t\u0005\u00117C)+\u0004\u0002\t\u001e*!\u0001r\u0014EQ\u0003%)hn\u00195fG.,GM\u0003\u0003\t$\u0006u\u0014AC1o]>$\u0018\r^5p]&!\u0001r\u0015EO\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u000b\u0005\u0011WC\u0019\f\u0006\u0003\t.\"\u0005'C\u0002EX\u0011c\u001biA\u0002\u0004\u0003v\u0002\u0001\u0001R\u0016\t\u0005\u0003#C\u0019\fB\u0004\u0003|\u001e\u0011\r\u0001#.\u0012\t\u0005e\u0005r\u0017\u0019\u0005\u0011sCi\f\u0005\u0004\u0002@\u000e\r\u00012\u0018\t\u0005\u0003#Ci\f\u0002\u0007\t@\"M\u0016\u0011!A\u0001\u0006\u0003\t9JA\u0002`IMBqa!\t\b\u0001\bA\u0019\r\u0005\u0005\u0002@\u000e\u0015\u0012q\u0012EY)\u0011\ty\tc2\t\u000f\t5\u0004\u00021\u0001\u0003b!*\u0001B!\u001d\tLF:aDa \tN\"M\u0017'C\u0012\u0003\u0012\n]\u0005r\u001aBMc%\u0019#1\u0016BW\u0011#\u0014i*M\u0004#\u00037\u000biH!-2\u0007\u0019\u00129(\u0006\u0003\tX\"uGC\u0002Em\u0011CD\u0019\u000fE\u0003\u0002\n\u0002AY\u000e\u0005\u0003\u0002\u0012\"uGa\u0002Bo\u0013\t\u0007\u0001r\\\t\u0005\u0003\u001f\u000b\t\u000bC\u0004\u0005R%\u0001\rA!\u0019\t\u000f\rm\u0015\u00021\u0001\t\\\u0006\u0019Q.\u00199\u0016\t!%\br\u001e\u000b\u0005\u0011WD\t\u0010E\u0003\u0002\n\u0002Ai\u000f\u0005\u0003\u0002\u0012\"=Ha\u0002Bo\u0015\t\u0007\u0011q\u0013\u0005\b\u0007\u007fS\u0001\u0019\u0001Ez!!\tYja1\u0002\u0010\"5X\u0003\u0002E|\u0011{$B\u0001#?\t\u0000B)\u0011\u0011\u0012\u0001\t|B!\u0011\u0011\u0013E\u007f\t\u001d\u0011in\u0003b\u0001\u0011?Dqaa'\f\u0001\u0004AY0\u0006\u0003\n\u0004%%A\u0003BE\u0003\u0013\u0017\u0001R!!#\u0001\u0013\u000f\u0001B!!%\n\n\u00119!Q\u001c\u0007C\u0002!}\u0007bBBN\u0019\u0001\u0007\u0011rA\u0001\u0014CB\u0004XM\u001c3fI\u0006cG.\u0011:sCf\u001cV-]\u000b\u0005\u0013#I9\u0002\u0006\u0003\n\u0014%e\u0001#BAE\u0001%U\u0001\u0003BAI\u0013/!qA!8\u000e\u0005\u0004Ay\u000eC\u0004\u0003F6\u0001\r!c\u0005\u0002\u0017\u0005\u0004\b/\u001a8eK\u0012\fE\u000e\\\u000b\u0005\u0013?I)\u0003\u0006\u0003\n\"%\u001d\u0002#BAE\u0001%\r\u0002\u0003BAI\u0013K!qA!8\u000f\u0005\u0004Ay\u000eC\u0004\n*9\u0001\r!c\u000b\u0002\rM,hMZ5y!\u0019\tyl!\u0019\n$\u0005a\u0001O]3qK:$W\rZ!mYV!\u0011\u0012GE\u001c)\u0011I\u0019$#\u000f\u0011\u000b\u0005%\u0005!#\u000e\u0011\t\u0005E\u0015r\u0007\u0003\b\u0005;|!\u0019\u0001Ep\u0011\u001dIYd\u0004a\u0001\u0013{\ta\u0001\u001d:fM&D\bCBA`\u0007CJ)$A\u0002{SB,B!c\u0011\nPQ!\u0011RIE)!\u0015\tI\tAE$!!\tY*#\u0013\u0002\u0010&5\u0013\u0002BE&\u0003{\u0012a\u0001V;qY\u0016\u0014\u0004\u0003BAI\u0013\u001f\"qA!8\u0011\u0005\u0004\t9\nC\u0004\u0003FB\u0001\r!c\u0015\u0011\r\u0005}6\u0011ME'\u0003\u0011!\u0018m[3\u0015\t\u0005U\u0016\u0012\f\u0005\b\u0007K\u000b\u0002\u0019\u0001B1\u0003%!\u0018m[3SS\u001eDG\u000f\u0006\u0003\u00026&}\u0003bBBS%\u0001\u0007!\u0011M\u0001\u0005IJ|\u0007\u000f\u0006\u0003\u00026&\u0015\u0004bBBS'\u0001\u0007!\u0011M\u0001\nIJ|\u0007OU5hQR$B!!.\nl!91Q\u0015\u000bA\u0002\t\u0005\u0014!B:mS\u000e,GCBA[\u0013cJ\u0019\bC\u0004\u0004HU\u0001\rA!\u0019\t\u000f%UT\u00031\u0001\u0003b\u0005)QO\u001c;jY\u0006Aam\u001c7e\u0019\u00164G/\u0006\u0003\n|%\u0005E\u0003BE?\u0013\u0017#B!c \n\u0004B!\u0011\u0011SEA\t\u001d\u0011iN\u0006b\u0001\u0003/Cqaa0\u0017\u0001\u0004I)\t\u0005\u0006\u0002\u001c&\u001d\u0015rPAH\u0013\u007fJA!##\u0002~\tIa)\u001e8di&|gN\r\u0005\b\u0013\u001b3\u0002\u0019AE@\u0003\u0005Q\u0018!\u00034pY\u0012\u0014\u0016n\u001a5u+\u0011I\u0019*#'\u0015\t%U\u0015r\u0014\u000b\u0005\u0013/KY\n\u0005\u0003\u0002\u0012&eEa\u0002Bo/\t\u0007\u0011q\u0013\u0005\b\u0007\u007f;\u0002\u0019AEO!)\tY*c\"\u0002\u0010&]\u0015r\u0013\u0005\b\u0013\u001b;\u0002\u0019AEL\u0003\u0011!\u0018-\u001b7\u0016\u0005\u0005U\u0016a\u0002:fm\u0016\u00148/Z\u0001\nG2\f7o\u001d(b[\u0016,\"!c+\u0011\t!u\u0014RV\u0005\u0005\u0005\u0017Cy(A\u0006d_BLHk\\!se\u0006LX\u0003BEZ\u0013{#\u0002B!\u0019\n6&}\u0016\u0012\u0019\u0005\b\u0013o[\u0002\u0019AE]\u0003\tA8\u000f\u0005\u0004\u0002\u001c\n5\u00112\u0018\t\u0005\u0003#Ki\fB\u0004\u0003^n\u0011\r\u0001c8\t\u000f\u0015M5\u00041\u0001\u0003b!9\u00112Y\u000eA\u0002\t\u0005\u0014a\u00017f]\u00069\u0012\r\u001d9msB\u0013XMZ3se\u0016$W*\u0019=MK:<G\u000f[\u000b\u0005\u0013\u0013L\t\u000e\u0006\u0003\u00026&-\u0007b\u0002Bi;\u0001\u000f\u0011R\u001a\t\u0007\u0003'\u0014).c4\u0011\t\u0005E\u0015\u0012\u001b\u0003\b\u0005;l\"\u0019\u0001EpS=\u0001\u0011Q\b\u001cU\u0003C\t)\u0001Z:+\u000b\u0006m\u0003")
public abstract class ArraySeq<A>
extends AbstractSeq<A>
implements scala.collection.immutable.IndexedSeq<A>,
scala.collection.immutable.StrictOptimizedSeqOps<A, ArraySeq, ArraySeq<A>>,
EvidenceIterableFactoryDefaults<A, ArraySeq, ClassTag>,
Serializable {
    public static <T> ArraySeq<T> unsafeWrapArray(Object x) {
        return ArraySeq$.MODULE$.unsafeWrapArray(x);
    }

    public static <A> ArraySeq<A> tabulate(int n, Function1<Object, A> f, ClassTag<A> evidence$4) {
        ArraySeq$ tabulate_this = ArraySeq$.MODULE$;
        int tabulate_max_y = 0;
        int tabulate_ofDim_n1 = Math.max(n, tabulate_max_y);
        Object tabulate_elements = evidence$4.newArray(tabulate_ofDim_n1);
        for (int tabulate_i = 0; tabulate_i < n; ++tabulate_i) {
            ScalaRunTime$.MODULE$.array_update(tabulate_elements, tabulate_i, f.apply(tabulate_i));
        }
        return tabulate_this.unsafeWrapArray(tabulate_elements);
    }

    public static <A> ArraySeq<A> fill(int n, Function0<A> elem, ClassTag<A> evidence$3) {
        ArraySeq$ fill_this = ArraySeq$.MODULE$;
        int fill_tabulate_max_y = 0;
        int fill_tabulate_ofDim_n1 = Math.max(n, fill_tabulate_max_y);
        Object fill_tabulate_elements = evidence$3.newArray(fill_tabulate_ofDim_n1);
        for (int fill_tabulate_i = 0; fill_tabulate_i < n; ++fill_tabulate_i) {
            ScalaRunTime$.MODULE$.array_update(fill_tabulate_elements, fill_tabulate_i, elem.apply());
        }
        return fill_this.unsafeWrapArray(fill_tabulate_elements);
    }

    public static <A> Builder<A, ArraySeq<A>> newBuilder(ClassTag<A> evidence$2) {
        return ArraySeq$.MODULE$.newBuilder(evidence$2);
    }

    public static <A> ArraySeq<A> from(IterableOnce<A> it, ClassTag<A> tag) {
        return ArraySeq$.MODULE$.from(it, tag);
    }

    public static SeqFactory<ArraySeq> untagged() {
        return ArraySeq$.MODULE$.untagged();
    }

    public static SeqOps unapplySeq(SeqOps x) {
        return x;
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f, ClassTag evidence$33) {
        ArraySeq$ tabulate_this = ArraySeq$.MODULE$;
        ClassTag<Object> tabulate_evidence$9 = ClassTag$.MODULE$.AnyRef();
        int tabulate_max_y = 0;
        int tabulate_ofDim_n1 = Math.max(n1, tabulate_max_y);
        Object tabulate_elements = tabulate_evidence$9.newArray(tabulate_ofDim_n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            ClassTag<Object> tabulate_evidence$92 = ClassTag$.MODULE$.AnyRef();
            int tabulate_max_y2 = 0;
            int tabulate_ofDim_n12 = Math.max(n2, tabulate_max_y2);
            Object tabulate_elements2 = tabulate_evidence$92.newArray(tabulate_ofDim_n12);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                ClassTag<Object> tabulate_evidence$93 = ClassTag$.MODULE$.AnyRef();
                int tabulate_max_y3 = 0;
                int tabulate_ofDim_n13 = Math.max(n3, tabulate_max_y3);
                Object tabulate_elements3 = tabulate_evidence$93.newArray(tabulate_ofDim_n13);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    ClassTag<Object> tabulate_evidence$94 = ClassTag$.MODULE$.AnyRef();
                    int tabulate_max_y4 = 0;
                    int tabulate_ofDim_n14 = Math.max(n4, tabulate_max_y4);
                    Object tabulate_elements4 = tabulate_evidence$94.newArray(tabulate_ofDim_n14);
                    for (int tabulate_i4 = 0; tabulate_i4 < n4; ++tabulate_i4) {
                        int tabulate_max_y5 = 0;
                        int tabulate_ofDim_n15 = Math.max(n5, tabulate_max_y5);
                        Object tabulate_elements5 = evidence$33.newArray(tabulate_ofDim_n15);
                        for (int tabulate_i5 = 0; tabulate_i5 < n5; ++tabulate_i5) {
                            ScalaRunTime$.MODULE$.array_update(tabulate_elements5, tabulate_i5, f.apply(tabulate_i, tabulate_i2, tabulate_i3, tabulate_i4, tabulate_i5));
                        }
                        Object var28_28 = null;
                        ScalaRunTime$.MODULE$.array_update(tabulate_elements4, tabulate_i4, tabulate_this.unsafeWrapArray(tabulate_elements5));
                    }
                    Object var24_24 = null;
                    Object var23_23 = null;
                    ScalaRunTime$.MODULE$.array_update(tabulate_elements3, tabulate_i3, tabulate_this.unsafeWrapArray(tabulate_elements4));
                }
                Object var19_19 = null;
                Object var18_18 = null;
                ScalaRunTime$.MODULE$.array_update(tabulate_elements2, tabulate_i2, tabulate_this.unsafeWrapArray(tabulate_elements3));
            }
            Object var14_14 = null;
            Object var13_13 = null;
            ScalaRunTime$.MODULE$.array_update(tabulate_elements, tabulate_i, tabulate_this.unsafeWrapArray(tabulate_elements2));
        }
        return tabulate_this.unsafeWrapArray(tabulate_elements);
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f, ClassTag evidence$32) {
        ArraySeq$ tabulate_this = ArraySeq$.MODULE$;
        ClassTag<Object> tabulate_evidence$9 = ClassTag$.MODULE$.AnyRef();
        int tabulate_max_y = 0;
        int tabulate_ofDim_n1 = Math.max(n1, tabulate_max_y);
        Object tabulate_elements = tabulate_evidence$9.newArray(tabulate_ofDim_n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            ClassTag<Object> tabulate_evidence$92 = ClassTag$.MODULE$.AnyRef();
            int tabulate_max_y2 = 0;
            int tabulate_ofDim_n12 = Math.max(n2, tabulate_max_y2);
            Object tabulate_elements2 = tabulate_evidence$92.newArray(tabulate_ofDim_n12);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                ClassTag<Object> tabulate_evidence$93 = ClassTag$.MODULE$.AnyRef();
                int tabulate_max_y3 = 0;
                int tabulate_ofDim_n13 = Math.max(n3, tabulate_max_y3);
                Object tabulate_elements3 = tabulate_evidence$93.newArray(tabulate_ofDim_n13);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    int tabulate_max_y4 = 0;
                    int tabulate_ofDim_n14 = Math.max(n4, tabulate_max_y4);
                    Object tabulate_elements4 = evidence$32.newArray(tabulate_ofDim_n14);
                    for (int tabulate_i4 = 0; tabulate_i4 < n4; ++tabulate_i4) {
                        ScalaRunTime$.MODULE$.array_update(tabulate_elements4, tabulate_i4, f.apply(tabulate_i, tabulate_i2, tabulate_i3, tabulate_i4));
                    }
                    Object var22_22 = null;
                    ScalaRunTime$.MODULE$.array_update(tabulate_elements3, tabulate_i3, tabulate_this.unsafeWrapArray(tabulate_elements4));
                }
                Object var18_18 = null;
                Object var17_17 = null;
                ScalaRunTime$.MODULE$.array_update(tabulate_elements2, tabulate_i2, tabulate_this.unsafeWrapArray(tabulate_elements3));
            }
            Object var13_13 = null;
            Object var12_12 = null;
            ScalaRunTime$.MODULE$.array_update(tabulate_elements, tabulate_i, tabulate_this.unsafeWrapArray(tabulate_elements2));
        }
        return tabulate_this.unsafeWrapArray(tabulate_elements);
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f, ClassTag evidence$31) {
        ArraySeq$ tabulate_this = ArraySeq$.MODULE$;
        ClassTag<Object> tabulate_evidence$9 = ClassTag$.MODULE$.AnyRef();
        int tabulate_max_y = 0;
        int tabulate_ofDim_n1 = Math.max(n1, tabulate_max_y);
        Object tabulate_elements = tabulate_evidence$9.newArray(tabulate_ofDim_n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            ClassTag<Object> tabulate_evidence$92 = ClassTag$.MODULE$.AnyRef();
            int tabulate_max_y2 = 0;
            int tabulate_ofDim_n12 = Math.max(n2, tabulate_max_y2);
            Object tabulate_elements2 = tabulate_evidence$92.newArray(tabulate_ofDim_n12);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                int tabulate_max_y3 = 0;
                int tabulate_ofDim_n13 = Math.max(n3, tabulate_max_y3);
                Object tabulate_elements3 = evidence$31.newArray(tabulate_ofDim_n13);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    ScalaRunTime$.MODULE$.array_update(tabulate_elements3, tabulate_i3, f.apply(tabulate_i, tabulate_i2, tabulate_i3));
                }
                Object var16_16 = null;
                ScalaRunTime$.MODULE$.array_update(tabulate_elements2, tabulate_i2, tabulate_this.unsafeWrapArray(tabulate_elements3));
            }
            Object var12_12 = null;
            Object var11_11 = null;
            ScalaRunTime$.MODULE$.array_update(tabulate_elements, tabulate_i, tabulate_this.unsafeWrapArray(tabulate_elements2));
        }
        return tabulate_this.unsafeWrapArray(tabulate_elements);
    }

    public static Object tabulate(int n1, int n2, Function2 f, ClassTag evidence$30) {
        ArraySeq$ tabulate_this = ArraySeq$.MODULE$;
        ClassTag<Object> tabulate_evidence$9 = ClassTag$.MODULE$.AnyRef();
        int tabulate_max_y = 0;
        int tabulate_ofDim_n1 = Math.max(n1, tabulate_max_y);
        Object tabulate_elements = tabulate_evidence$9.newArray(tabulate_ofDim_n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            int tabulate_max_y2 = 0;
            int tabulate_ofDim_n12 = Math.max(n2, tabulate_max_y2);
            Object tabulate_elements2 = evidence$30.newArray(tabulate_ofDim_n12);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                ScalaRunTime$.MODULE$.array_update(tabulate_elements2, tabulate_i2, f.apply(tabulate_i, tabulate_i2));
            }
            Object var10_10 = null;
            ScalaRunTime$.MODULE$.array_update(tabulate_elements, tabulate_i, tabulate_this.unsafeWrapArray(tabulate_elements2));
        }
        return tabulate_this.unsafeWrapArray(tabulate_elements);
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem, ClassTag evidence$29) {
        ArraySeq$ fill_this = ArraySeq$.MODULE$;
        ClassTag<Object> fill_evidence$8 = ClassTag$.MODULE$.AnyRef();
        int fill_tabulate_max_y = 0;
        int fill_tabulate_ofDim_n1 = Math.max(n1, fill_tabulate_max_y);
        Object fill_tabulate_elements = fill_evidence$8.newArray(fill_tabulate_ofDim_n1);
        for (int fill_tabulate_i = 0; fill_tabulate_i < n1; ++fill_tabulate_i) {
            ClassTag<Object> fill_evidence$82 = ClassTag$.MODULE$.AnyRef();
            int fill_tabulate_max_y2 = 0;
            int fill_tabulate_ofDim_n12 = Math.max(n2, fill_tabulate_max_y2);
            Object fill_tabulate_elements2 = fill_evidence$82.newArray(fill_tabulate_ofDim_n12);
            for (int fill_tabulate_i2 = 0; fill_tabulate_i2 < n2; ++fill_tabulate_i2) {
                ClassTag<Object> fill_evidence$83 = ClassTag$.MODULE$.AnyRef();
                int fill_tabulate_max_y3 = 0;
                int fill_tabulate_ofDim_n13 = Math.max(n3, fill_tabulate_max_y3);
                Object fill_tabulate_elements3 = fill_evidence$83.newArray(fill_tabulate_ofDim_n13);
                for (int fill_tabulate_i3 = 0; fill_tabulate_i3 < n3; ++fill_tabulate_i3) {
                    ClassTag<Object> fill_evidence$84 = ClassTag$.MODULE$.AnyRef();
                    int fill_tabulate_max_y4 = 0;
                    int fill_tabulate_ofDim_n14 = Math.max(n4, fill_tabulate_max_y4);
                    Object fill_tabulate_elements4 = fill_evidence$84.newArray(fill_tabulate_ofDim_n14);
                    for (int fill_tabulate_i4 = 0; fill_tabulate_i4 < n4; ++fill_tabulate_i4) {
                        int fill_tabulate_max_y5 = 0;
                        int fill_tabulate_ofDim_n15 = Math.max(n5, fill_tabulate_max_y5);
                        Object fill_tabulate_elements5 = evidence$29.newArray(fill_tabulate_ofDim_n15);
                        for (int fill_tabulate_i5 = 0; fill_tabulate_i5 < n5; ++fill_tabulate_i5) {
                            ScalaRunTime$.MODULE$.array_update(fill_tabulate_elements5, fill_tabulate_i5, elem.apply());
                        }
                        Object var28_28 = null;
                        ScalaRunTime$.MODULE$.array_update(fill_tabulate_elements4, fill_tabulate_i4, fill_this.unsafeWrapArray(fill_tabulate_elements5));
                    }
                    Object var24_24 = null;
                    Object var23_23 = null;
                    ScalaRunTime$.MODULE$.array_update(fill_tabulate_elements3, fill_tabulate_i3, fill_this.unsafeWrapArray(fill_tabulate_elements4));
                }
                Object var19_19 = null;
                Object var18_18 = null;
                ScalaRunTime$.MODULE$.array_update(fill_tabulate_elements2, fill_tabulate_i2, fill_this.unsafeWrapArray(fill_tabulate_elements3));
            }
            Object var14_14 = null;
            Object var13_13 = null;
            ScalaRunTime$.MODULE$.array_update(fill_tabulate_elements, fill_tabulate_i, fill_this.unsafeWrapArray(fill_tabulate_elements2));
        }
        return fill_this.unsafeWrapArray(fill_tabulate_elements);
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem, ClassTag evidence$28) {
        ArraySeq$ fill_this = ArraySeq$.MODULE$;
        ClassTag<Object> fill_evidence$8 = ClassTag$.MODULE$.AnyRef();
        int fill_tabulate_max_y = 0;
        int fill_tabulate_ofDim_n1 = Math.max(n1, fill_tabulate_max_y);
        Object fill_tabulate_elements = fill_evidence$8.newArray(fill_tabulate_ofDim_n1);
        for (int fill_tabulate_i = 0; fill_tabulate_i < n1; ++fill_tabulate_i) {
            ClassTag<Object> fill_evidence$82 = ClassTag$.MODULE$.AnyRef();
            int fill_tabulate_max_y2 = 0;
            int fill_tabulate_ofDim_n12 = Math.max(n2, fill_tabulate_max_y2);
            Object fill_tabulate_elements2 = fill_evidence$82.newArray(fill_tabulate_ofDim_n12);
            for (int fill_tabulate_i2 = 0; fill_tabulate_i2 < n2; ++fill_tabulate_i2) {
                ClassTag<Object> fill_evidence$83 = ClassTag$.MODULE$.AnyRef();
                int fill_tabulate_max_y3 = 0;
                int fill_tabulate_ofDim_n13 = Math.max(n3, fill_tabulate_max_y3);
                Object fill_tabulate_elements3 = fill_evidence$83.newArray(fill_tabulate_ofDim_n13);
                for (int fill_tabulate_i3 = 0; fill_tabulate_i3 < n3; ++fill_tabulate_i3) {
                    int fill_tabulate_max_y4 = 0;
                    int fill_tabulate_ofDim_n14 = Math.max(n4, fill_tabulate_max_y4);
                    Object fill_tabulate_elements4 = evidence$28.newArray(fill_tabulate_ofDim_n14);
                    for (int fill_tabulate_i4 = 0; fill_tabulate_i4 < n4; ++fill_tabulate_i4) {
                        ScalaRunTime$.MODULE$.array_update(fill_tabulate_elements4, fill_tabulate_i4, elem.apply());
                    }
                    Object var22_22 = null;
                    ScalaRunTime$.MODULE$.array_update(fill_tabulate_elements3, fill_tabulate_i3, fill_this.unsafeWrapArray(fill_tabulate_elements4));
                }
                Object var18_18 = null;
                Object var17_17 = null;
                ScalaRunTime$.MODULE$.array_update(fill_tabulate_elements2, fill_tabulate_i2, fill_this.unsafeWrapArray(fill_tabulate_elements3));
            }
            Object var13_13 = null;
            Object var12_12 = null;
            ScalaRunTime$.MODULE$.array_update(fill_tabulate_elements, fill_tabulate_i, fill_this.unsafeWrapArray(fill_tabulate_elements2));
        }
        return fill_this.unsafeWrapArray(fill_tabulate_elements);
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem, ClassTag evidence$27) {
        ArraySeq$ fill_this = ArraySeq$.MODULE$;
        ClassTag<Object> fill_evidence$8 = ClassTag$.MODULE$.AnyRef();
        int fill_tabulate_max_y = 0;
        int fill_tabulate_ofDim_n1 = Math.max(n1, fill_tabulate_max_y);
        Object fill_tabulate_elements = fill_evidence$8.newArray(fill_tabulate_ofDim_n1);
        for (int fill_tabulate_i = 0; fill_tabulate_i < n1; ++fill_tabulate_i) {
            ClassTag<Object> fill_evidence$82 = ClassTag$.MODULE$.AnyRef();
            int fill_tabulate_max_y2 = 0;
            int fill_tabulate_ofDim_n12 = Math.max(n2, fill_tabulate_max_y2);
            Object fill_tabulate_elements2 = fill_evidence$82.newArray(fill_tabulate_ofDim_n12);
            for (int fill_tabulate_i2 = 0; fill_tabulate_i2 < n2; ++fill_tabulate_i2) {
                int fill_tabulate_max_y3 = 0;
                int fill_tabulate_ofDim_n13 = Math.max(n3, fill_tabulate_max_y3);
                Object fill_tabulate_elements3 = evidence$27.newArray(fill_tabulate_ofDim_n13);
                for (int fill_tabulate_i3 = 0; fill_tabulate_i3 < n3; ++fill_tabulate_i3) {
                    ScalaRunTime$.MODULE$.array_update(fill_tabulate_elements3, fill_tabulate_i3, elem.apply());
                }
                Object var16_16 = null;
                ScalaRunTime$.MODULE$.array_update(fill_tabulate_elements2, fill_tabulate_i2, fill_this.unsafeWrapArray(fill_tabulate_elements3));
            }
            Object var12_12 = null;
            Object var11_11 = null;
            ScalaRunTime$.MODULE$.array_update(fill_tabulate_elements, fill_tabulate_i, fill_this.unsafeWrapArray(fill_tabulate_elements2));
        }
        return fill_this.unsafeWrapArray(fill_tabulate_elements);
    }

    public static Object fill(int n1, int n2, Function0 elem, ClassTag evidence$26) {
        ArraySeq$ fill_this = ArraySeq$.MODULE$;
        ClassTag<Object> fill_evidence$8 = ClassTag$.MODULE$.AnyRef();
        int fill_tabulate_max_y = 0;
        int fill_tabulate_ofDim_n1 = Math.max(n1, fill_tabulate_max_y);
        Object fill_tabulate_elements = fill_evidence$8.newArray(fill_tabulate_ofDim_n1);
        for (int fill_tabulate_i = 0; fill_tabulate_i < n1; ++fill_tabulate_i) {
            int fill_tabulate_max_y2 = 0;
            int fill_tabulate_ofDim_n12 = Math.max(n2, fill_tabulate_max_y2);
            Object fill_tabulate_elements2 = evidence$26.newArray(fill_tabulate_ofDim_n12);
            for (int fill_tabulate_i2 = 0; fill_tabulate_i2 < n2; ++fill_tabulate_i2) {
                ScalaRunTime$.MODULE$.array_update(fill_tabulate_elements2, fill_tabulate_i2, elem.apply());
            }
            Object var10_10 = null;
            ScalaRunTime$.MODULE$.array_update(fill_tabulate_elements, fill_tabulate_i, fill_this.unsafeWrapArray(fill_tabulate_elements2));
        }
        return fill_this.unsafeWrapArray(fill_tabulate_elements);
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$24, ClassTag evidence$25) {
        return ClassTagIterableFactory.range$(ArraySeq$.MODULE$, start2, end, step, evidence$24, evidence$25);
    }

    public static Object range(Object start2, Object end, Integral evidence$22, ClassTag evidence$23) {
        return ClassTagIterableFactory.range$(ArraySeq$.MODULE$, start2, end, evidence$22, evidence$23);
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
    public IterableOps fromSpecific(IterableOnce coll) {
        return EvidenceIterableFactoryDefaults.fromSpecific$(this, coll);
    }

    @Override
    public Builder<A, ArraySeq<A>> newSpecificBuilder() {
        return EvidenceIterableFactoryDefaults.newSpecificBuilder$(this);
    }

    @Override
    public IterableOps empty() {
        return EvidenceIterableFactoryDefaults.empty$(this);
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
    public Object patch(int from, IterableOnce other, int replaced) {
        return scala.collection.immutable.StrictOptimizedSeqOps.patch$(this, from, other, replaced);
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
    public Tuple2<ArraySeq<A>, ArraySeq<A>> partition(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<ArraySeq<A>, ArraySeq<A>> span(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<ArraySeq<A1>, ArraySeq<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<ArraySeq<A1>, ArraySeq<A2>, ArraySeq<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<A, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<A, IterableOnce<B>> f) {
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
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<A, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<A, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<A, B>, C2> b) {
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
    public <A1, A2> Tuple2<ArraySeq<A1>, ArraySeq<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
        return StrictOptimizedIterableOps.partitionMap$(this, f);
    }

    @Override
    public Object tapEach(Function1 f) {
        return StrictOptimizedIterableOps.tapEach$(this, f);
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
    public final scala.collection.immutable.IndexedSeq<A> toIndexedSeq() {
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
    public /* synthetic */ Object scala$collection$immutable$IndexedSeqOps$$super$slice(int from, int until) {
        return IndexedSeqOps.slice$(this, from, until);
    }

    @Override
    public String stringPrefix() {
        return IndexedSeq.stringPrefix$(this);
    }

    @Override
    public Iterator<A> iterator() {
        return IndexedSeqOps.iterator$(this);
    }

    @Override
    public Iterator<A> reverseIterator() {
        return IndexedSeqOps.reverseIterator$(this);
    }

    @Override
    public IndexedSeqView<A> view() {
        return IndexedSeqOps.view$(this);
    }

    @Override
    public IndexedSeqView<A> view(int from, int until) {
        return IndexedSeqOps.view$(this, from, until);
    }

    @Override
    public Iterable<A> reversed() {
        return IndexedSeqOps.reversed$(this);
    }

    @Override
    public A head() {
        return (A)IndexedSeqOps.head$(this);
    }

    @Override
    public Option<A> headOption() {
        return IndexedSeqOps.headOption$(this);
    }

    @Override
    public A last() {
        return (A)IndexedSeqOps.last$(this);
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

    public abstract ClassTag<?> elemTag();

    @Override
    public SeqFactory<ArraySeq> iterableFactory() {
        return ArraySeq$.MODULE$.untagged();
    }

    public abstract Object unsafeArray();

    public ArraySeq$ evidenceIterableFactory() {
        return ArraySeq$.MODULE$;
    }

    @Override
    public ClassTag<A> iterableEvidence() {
        return this.elemTag();
    }

    @Override
    public abstract <S extends Stepper<?>> S stepper(StepperShape<A, S> var1);

    @Override
    public abstract A apply(int var1) throws ArrayIndexOutOfBoundsException;

    @Override
    public <B> ArraySeq<B> updated(int index, B elem) {
        Object[] dest = new Object[this.length()];
        Array$.MODULE$.copy(this.unsafeArray(), 0, dest, 0, this.length());
        dest[index] = elem;
        return ArraySeq$.MODULE$.unsafeWrapArray(dest);
    }

    @Override
    public <B> ArraySeq<B> map(Function1<A, B> f) {
        Object[] a = new Object[this.length()];
        for (int i = 0; i < a.length; ++i) {
            a[i] = f.apply(this.apply(i));
        }
        return ArraySeq$.MODULE$.unsafeWrapArray(a);
    }

    @Override
    public <B> ArraySeq<B> prepended(B elem) {
        return ArraySeq$.MODULE$.unsafeWrapArray(ArrayOps$.MODULE$.prepended$extension(this.unsafeArray(), elem, ClassTag$.MODULE$.Any()));
    }

    @Override
    public <B> ArraySeq<B> appended(B elem) {
        return ArraySeq$.MODULE$.unsafeWrapArray(ArrayOps$.MODULE$.appended$extension(this.unsafeArray(), elem, ClassTag$.MODULE$.Any()));
    }

    private <B> ArraySeq<B> appendedAllArraySeq(ArraySeq<B> that) {
        boolean thatIsObj;
        if (this.isEmpty()) {
            return that;
        }
        if (that.isEmpty()) {
            return this;
        }
        boolean thisIsObj = this.unsafeArray() instanceof Object[];
        if (thisIsObj != (thatIsObj = that.unsafeArray() instanceof Object[])) {
            return null;
        }
        if (thisIsObj) {
            Object ax = this.unsafeArray();
            Object ay = that.unsafeArray();
            Object[] a = new Object[Array.getLength(ax) + Array.getLength(ay)];
            System.arraycopy(ax, 0, a, 0, Array.getLength(ax));
            System.arraycopy(ay, 0, a, Array.getLength(ax), Array.getLength(ay));
            return ArraySeq$.MODULE$.unsafeWrapArray(a);
        }
        Object ax = this.unsafeArray();
        Object ay = that.unsafeArray();
        int len = Array.getLength(ax) + Array.getLength(ay);
        Object a = this.elemTag().newArray(len);
        System.arraycopy(ax, 0, a, 0, Array.getLength(ax));
        System.arraycopy(ay, 0, a, Array.getLength(ax), Array.getLength(ay));
        return ArraySeq$.MODULE$.unsafeWrapArray(a);
    }

    @Override
    public <B> ArraySeq<B> appendedAll(IterableOnce<B> suffix) {
        if (suffix instanceof ArraySeq) {
            ArraySeq arraySeq = (ArraySeq)suffix;
            ArraySeq<B> result = this.appendedAllArraySeq(arraySeq);
            if (result == null) {
                return this.genericResult$1(suffix);
            }
            return result;
        }
        return this.genericResult$1(suffix);
    }

    @Override
    public <B> ArraySeq<B> prependedAll(IterableOnce<B> prefix) {
        if (prefix instanceof ArraySeq) {
            ArraySeq<B> result = ((ArraySeq)prefix).appendedAllArraySeq(this);
            if (result == null) {
                return this.genericResult$2(prefix);
            }
            return result;
        }
        return this.genericResult$2(prefix);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> ArraySeq<Tuple2<A, B>> zip(IterableOnce<B> that) {
        if (that instanceof ArraySeq) {
            int tabulate_n;
            ArraySeq arraySeq = (ArraySeq)that;
            int n = this.length();
            int min$extension_that = arraySeq.length();
            int n2 = Math.min(n, min$extension_that);
            ArraySeq$ tabulate_this = ArraySeq$.MODULE$;
            int tabulate_max_y = 0;
            Tuple2[] tabulate_elements = new Tuple2[Math.max(tabulate_n, tabulate_max_y)];
            for (int tabulate_i = 0; tabulate_i < tabulate_n; ++tabulate_i) {
                Tuple2 array_update_value;
                tabulate_elements[tabulate_i] = array_update_value = ArraySeq.$anonfun$zip$1(this, arraySeq, tabulate_i);
                Object var14_10 = null;
            }
            return tabulate_this.unsafeWrapArray(tabulate_elements);
        }
        Builder strictOptimizedZip_b = ArraySeq$.MODULE$.untagged().newBuilder();
        Iterator strictOptimizedZip_it1 = this.iterator();
        Iterator<B> strictOptimizedZip_it2 = that.iterator();
        while (strictOptimizedZip_it1.hasNext() && strictOptimizedZip_it2.hasNext()) {
            void strictOptimizedZip_$plus$eq_elem;
            Tuple2 tuple2 = new Tuple2(strictOptimizedZip_it1.next(), strictOptimizedZip_it2.next());
            if (strictOptimizedZip_b == null) {
                throw null;
            }
            strictOptimizedZip_b.addOne(strictOptimizedZip_$plus$eq_elem);
            tuple2 = null;
        }
        return (ArraySeq)strictOptimizedZip_b.result();
    }

    @Override
    public ArraySeq<A> take(int n) {
        if (Array.getLength(this.unsafeArray()) <= n) {
            return this;
        }
        return ArraySeq$.MODULE$.unsafeWrapArray(ArrayOps$.MODULE$.slice$extension(this.unsafeArray(), 0, n));
    }

    @Override
    public ArraySeq<A> takeRight(int n) {
        if (Array.getLength(this.unsafeArray()) <= n) {
            return this;
        }
        return ArraySeq$.MODULE$.unsafeWrapArray(ArrayOps$.MODULE$.takeRight$extension(this.unsafeArray(), n));
    }

    @Override
    public ArraySeq<A> drop(int n) {
        if (n <= 0) {
            return this;
        }
        return ArraySeq$.MODULE$.unsafeWrapArray(ArrayOps$.MODULE$.drop$extension(this.unsafeArray(), n));
    }

    @Override
    public ArraySeq<A> dropRight(int n) {
        if (n <= 0) {
            return this;
        }
        return ArraySeq$.MODULE$.unsafeWrapArray(ArrayOps$.MODULE$.dropRight$extension(this.unsafeArray(), n));
    }

    @Override
    public ArraySeq<A> slice(int from, int until) {
        if (from <= 0 && Array.getLength(this.unsafeArray()) <= until) {
            return this;
        }
        return ArraySeq$.MODULE$.unsafeWrapArray(ArrayOps$.MODULE$.slice$extension(this.unsafeArray(), from, until));
    }

    @Override
    public <B> B foldLeft(B z, Function2<B, A, B> f) {
        Object array = this.unsafeArray();
        B b = z;
        for (int i = 0; i < Array.getLength(array); ++i) {
            Object a = ScalaRunTime$.MODULE$.array_apply(array, i);
            b = f.apply(b, a);
        }
        return b;
    }

    @Override
    public <B> B foldRight(B z, Function2<A, B, B> f) {
        Object array = this.unsafeArray();
        B b = z;
        int i = Array.getLength(array);
        while (i > 0) {
            Object a = ScalaRunTime$.MODULE$.array_apply(array, --i);
            b = f.apply(a, b);
        }
        return b;
    }

    @Override
    public ArraySeq<A> tail() {
        return ArraySeq$.MODULE$.unsafeWrapArray(ArrayOps$.MODULE$.tail$extension(this.unsafeArray()));
    }

    @Override
    public ArraySeq<A> reverse() {
        Object var3_3;
        Object reverse$extension_$this = this.unsafeArray();
        int reverse$extension_len = Array.getLength(reverse$extension_$this);
        Object reverse$extension_res = ClassTag$.MODULE$.apply(reverse$extension_$this.getClass().getComponentType()).newArray(reverse$extension_len);
        for (int reverse$extension_i = 0; reverse$extension_i < reverse$extension_len; ++reverse$extension_i) {
            ScalaRunTime$.MODULE$.array_update(reverse$extension_res, reverse$extension_len - reverse$extension_i - 1, ScalaRunTime$.MODULE$.array_apply(reverse$extension_$this, reverse$extension_i));
        }
        void v0 = var3_3;
        Object var1_1 = null;
        var3_3 = null;
        return ArraySeq$.MODULE$.unsafeWrapArray(v0);
    }

    @Override
    public String className() {
        return "ArraySeq";
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
            Array$.MODULE$.copy(this.unsafeArray(), 0, xs, start2, copied);
        }
        return copied;
    }

    @Override
    public final int applyPreferredMaxLength() {
        return Integer.MAX_VALUE;
    }

    @Override
    public <B> ArraySeq<A> sorted(Ordering<B> ord) {
        if (Array.getLength(this.unsafeArray()) <= 1) {
            return this;
        }
        Object[] a = (Object[])Array$.MODULE$.copyAs(this.unsafeArray(), this.length(), ClassTag$.MODULE$.AnyRef());
        Arrays.sort(a, ord);
        return new ofRef<Object>(a);
    }

    private final ArraySeq genericResult$1(IterableOnce suffix$1) {
        ArrayBuilder arrayBuilder;
        int k = suffix$1.knownSize();
        if (k == 0) {
            return this;
        }
        ClassTag<Object> make_evidence$1 = ClassTag$.MODULE$.Any();
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
                                        arrayBuilder = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef<Object>(make_evidence$1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Object var4_3 = null;
        clazz = null;
        ArrayBuilder b = arrayBuilder;
        if (k >= 0) {
            b.sizeHint(k + Array.getLength(this.unsafeArray()));
        }
        b.addAll(this.unsafeArray());
        b.addAll(suffix$1);
        return ArraySeq$.MODULE$.unsafeWrapArray(b.result());
    }

    private final ArraySeq genericResult$2(IterableOnce prefix$1) {
        ArrayBuilder arrayBuilder;
        int k = prefix$1.knownSize();
        if (k == 0) {
            return this;
        }
        ClassTag<Object> make_evidence$1 = ClassTag$.MODULE$.Any();
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
                                        arrayBuilder = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef<Object>(make_evidence$1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Object var4_3 = null;
        clazz = null;
        ArrayBuilder b = arrayBuilder;
        if (k >= 0) {
            b.sizeHint(k + Array.getLength(this.unsafeArray()));
        }
        b.addAll(prefix$1);
        if (k < 0) {
            b.sizeHint(b.length() + Array.getLength(this.unsafeArray()));
        }
        b.addAll(this.unsafeArray());
        return ArraySeq$.MODULE$.unsafeWrapArray(b.result());
    }

    public static final /* synthetic */ Tuple2 $anonfun$zip$1(ArraySeq $this, ArraySeq x2$1, int i) {
        return new Tuple2($this.apply(i), x2$1.apply(i));
    }

    public static final /* synthetic */ Tuple2 $anonfun$zip$1$adapted(ArraySeq $this, ArraySeq x2$1, Object i) {
        return ArraySeq.$anonfun$zip$1($this, x2$1, BoxesRunTime.unboxToInt(i));
    }

    public static final class ofBoolean
    extends ArraySeq<Object> {
        private static final long serialVersionUID = 3L;
        private final boolean[] unsafeArray;

        public boolean[] unsafeArray() {
            return this.unsafeArray;
        }

        public ManifestFactory.BooleanManifest elemTag() {
            return ClassTag$.MODULE$.Boolean();
        }

        @Override
        public int length() {
            return this.unsafeArray().length;
        }

        @Override
        public boolean apply(int i) throws ArrayIndexOutOfBoundsException {
            return this.unsafeArray()[i];
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash$mZc$sp(this.unsafeArray());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofBoolean) {
                ofBoolean ofBoolean2 = (ofBoolean)that;
                return Arrays.equals(this.unsafeArray(), ofBoolean2.unsafeArray());
            }
            return Seq.equals$(this, that);
        }

        @Override
        public <B> ArraySeq<Object> sorted(Ordering<B> ord) {
            if (this.unsafeArray().length <= 1) {
                return this;
            }
            if (ord == Ordering$Boolean$.MODULE$) {
                boolean[] a = (boolean[])this.unsafeArray().clone();
                Ordering$Boolean$ stableSort_evidence$3 = Ordering$Boolean$.MODULE$;
                Sorting$.MODULE$.stableSort((Object)a, 0, a.length, stableSort_evidence$3);
                Object var3_3 = null;
                return new ofBoolean(a);
            }
            return super.sorted((Ordering)ord);
        }

        @Override
        public Iterator<Object> iterator() {
            return new ArrayOps$ArrayIterator$mcZ$sp(this.unsafeArray());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
            return (S)new BoxedBooleanArrayStepper(this.unsafeArray(), 0, this.unsafeArray().length);
        }

        @Override
        public <B> ArraySeq<B> updated(int index, B elem) {
            if (elem instanceof Boolean) {
                boolean bl = BoxesRunTime.unboxToBoolean(elem);
                return new ofBoolean((boolean[])ArrayOps$.MODULE$.updated$extension(this.unsafeArray(), index, bl, ClassTag$.MODULE$.Boolean()));
            }
            return super.updated(index, (Object)elem);
        }

        @Override
        public <B> ArraySeq<B> appended(B elem) {
            if (elem instanceof Boolean) {
                boolean bl = BoxesRunTime.unboxToBoolean(elem);
                return new ofBoolean((boolean[])ArrayOps$.MODULE$.appended$extension(this.unsafeArray(), bl, ClassTag$.MODULE$.Boolean()));
            }
            return super.appended((Object)elem);
        }

        @Override
        public <B> ArraySeq<B> prepended(B elem) {
            if (elem instanceof Boolean) {
                boolean bl = BoxesRunTime.unboxToBoolean(elem);
                return new ofBoolean((boolean[])ArrayOps$.MODULE$.prepended$extension(this.unsafeArray(), bl, ClassTag$.MODULE$.Boolean()));
            }
            return super.prepended((Object)elem);
        }

        @Override
        public boolean apply$mcZI$sp(int i) {
            return this.unsafeArray()[i];
        }

        public ofBoolean(boolean[] unsafeArray) {
            this.unsafeArray = unsafeArray;
        }
    }

    public static final class ofByte
    extends ArraySeq<Object> {
        private static final long serialVersionUID = 3L;
        private final byte[] unsafeArray;

        public byte[] unsafeArray() {
            return this.unsafeArray;
        }

        public ManifestFactory.ByteManifest elemTag() {
            return ClassTag$.MODULE$.Byte();
        }

        @Override
        public int length() {
            return this.unsafeArray().length;
        }

        @Override
        public byte apply(int i) throws ArrayIndexOutOfBoundsException {
            return this.unsafeArray()[i];
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash$mBc$sp(this.unsafeArray());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofByte) {
                ofByte ofByte2 = (ofByte)that;
                return Arrays.equals(this.unsafeArray(), ofByte2.unsafeArray());
            }
            return Seq.equals$(this, that);
        }

        @Override
        public <B> ArraySeq<Object> sorted(Ordering<B> ord) {
            if (this.unsafeArray().length <= 1) {
                return this;
            }
            if (ord == Ordering$Byte$.MODULE$) {
                byte[] a = (byte[])this.unsafeArray().clone();
                Arrays.sort(a);
                return new ofByte(a);
            }
            return super.sorted((Ordering)ord);
        }

        @Override
        public Iterator<Object> iterator() {
            return new ArrayOps$ArrayIterator$mcB$sp(this.unsafeArray());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
            Stepper.EfficientSplit efficientSplit;
            if (shape.shape() == StepperShape$.MODULE$.ReferenceShape()) {
                WidenedByteArrayStepper ofParIntStepper_st = new WidenedByteArrayStepper(this.unsafeArray(), 0, this.unsafeArray().length);
                efficientSplit = new Stepper.EfficientSplit(ofParIntStepper_st){};
                Object var2_2 = null;
            } else {
                efficientSplit = new WidenedByteArrayStepper(this.unsafeArray(), 0, this.unsafeArray().length);
            }
            return (S)efficientSplit;
        }

        @Override
        public <B> ArraySeq<B> updated(int index, B elem) {
            if (elem instanceof Byte) {
                byte by = BoxesRunTime.unboxToByte(elem);
                return new ofByte((byte[])ArrayOps$.MODULE$.updated$extension(this.unsafeArray(), index, by, ClassTag$.MODULE$.Byte()));
            }
            return super.updated(index, (Object)elem);
        }

        @Override
        public <B> ArraySeq<B> appended(B elem) {
            if (elem instanceof Byte) {
                byte by = BoxesRunTime.unboxToByte(elem);
                return new ofByte((byte[])ArrayOps$.MODULE$.appended$extension(this.unsafeArray(), by, ClassTag$.MODULE$.Byte()));
            }
            return super.appended((Object)elem);
        }

        @Override
        public <B> ArraySeq<B> prepended(B elem) {
            if (elem instanceof Byte) {
                byte by = BoxesRunTime.unboxToByte(elem);
                return new ofByte((byte[])ArrayOps$.MODULE$.prepended$extension(this.unsafeArray(), by, ClassTag$.MODULE$.Byte()));
            }
            return super.prepended((Object)elem);
        }

        public ofByte(byte[] unsafeArray) {
            this.unsafeArray = unsafeArray;
        }
    }

    public static final class ofChar
    extends ArraySeq<Object> {
        private static final long serialVersionUID = 3L;
        private final char[] unsafeArray;

        public char[] unsafeArray() {
            return this.unsafeArray;
        }

        public ManifestFactory.CharManifest elemTag() {
            return ClassTag$.MODULE$.Char();
        }

        @Override
        public int length() {
            return this.unsafeArray().length;
        }

        @Override
        public char apply(int i) throws ArrayIndexOutOfBoundsException {
            return this.unsafeArray()[i];
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash$mCc$sp(this.unsafeArray());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofChar) {
                ofChar ofChar2 = (ofChar)that;
                return Arrays.equals(this.unsafeArray(), ofChar2.unsafeArray());
            }
            return Seq.equals$(this, that);
        }

        @Override
        public <B> ArraySeq<Object> sorted(Ordering<B> ord) {
            if (this.unsafeArray().length <= 1) {
                return this;
            }
            if (ord == Ordering$Char$.MODULE$) {
                char[] a = (char[])this.unsafeArray().clone();
                Arrays.sort(a);
                return new ofChar(a);
            }
            return super.sorted((Ordering)ord);
        }

        @Override
        public Iterator<Object> iterator() {
            return new ArrayOps$ArrayIterator$mcC$sp(this.unsafeArray());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
            Stepper.EfficientSplit efficientSplit;
            if (shape.shape() == StepperShape$.MODULE$.ReferenceShape()) {
                WidenedCharArrayStepper ofParIntStepper_st = new WidenedCharArrayStepper(this.unsafeArray(), 0, this.unsafeArray().length);
                efficientSplit = new /* invalid duplicate definition of identical inner class */;
                Object var2_2 = null;
            } else {
                efficientSplit = new WidenedCharArrayStepper(this.unsafeArray(), 0, this.unsafeArray().length);
            }
            return (S)efficientSplit;
        }

        @Override
        public <B> ArraySeq<B> updated(int index, B elem) {
            if (elem instanceof Character) {
                char c = BoxesRunTime.unboxToChar(elem);
                return new ofChar((char[])ArrayOps$.MODULE$.updated$extension(this.unsafeArray(), index, Character.valueOf(c), ClassTag$.MODULE$.Char()));
            }
            return super.updated(index, (Object)elem);
        }

        @Override
        public <B> ArraySeq<B> appended(B elem) {
            if (elem instanceof Character) {
                char c = BoxesRunTime.unboxToChar(elem);
                return new ofChar((char[])ArrayOps$.MODULE$.appended$extension(this.unsafeArray(), Character.valueOf(c), ClassTag$.MODULE$.Char()));
            }
            return super.appended((Object)elem);
        }

        @Override
        public <B> ArraySeq<B> prepended(B elem) {
            if (elem instanceof Character) {
                char c = BoxesRunTime.unboxToChar(elem);
                return new ofChar((char[])ArrayOps$.MODULE$.prepended$extension(this.unsafeArray(), Character.valueOf(c), ClassTag$.MODULE$.Char()));
            }
            return super.prepended((Object)elem);
        }

        @Override
        public StringBuilder addString(StringBuilder sb, String start2, String sep, String end) {
            return new ArraySeq.ofChar(this.unsafeArray()).addString(sb, start2, sep, end);
        }

        public ofChar(char[] unsafeArray) {
            this.unsafeArray = unsafeArray;
        }
    }

    public static final class ofDouble
    extends ArraySeq<Object> {
        private static final long serialVersionUID = 3L;
        private final double[] unsafeArray;

        public double[] unsafeArray() {
            return this.unsafeArray;
        }

        public ManifestFactory.DoubleManifest elemTag() {
            return ClassTag$.MODULE$.Double();
        }

        @Override
        public int length() {
            return this.unsafeArray().length;
        }

        @Override
        public double apply(int i) throws ArrayIndexOutOfBoundsException {
            return this.unsafeArray()[i];
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash$mDc$sp(this.unsafeArray());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofDouble) {
                ofDouble ofDouble2 = (ofDouble)that;
                return Arrays.equals(this.unsafeArray(), ofDouble2.unsafeArray());
            }
            return Seq.equals$(this, that);
        }

        @Override
        public Iterator<Object> iterator() {
            return new ArrayOps$ArrayIterator$mcD$sp(this.unsafeArray());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
            Stepper.EfficientSplit efficientSplit;
            if (shape.shape() == StepperShape$.MODULE$.ReferenceShape()) {
                DoubleArrayStepper ofParDoubleStepper_st = new DoubleArrayStepper(this.unsafeArray(), 0, this.unsafeArray().length);
                efficientSplit = new Stepper.EfficientSplit(ofParDoubleStepper_st){};
                Object var2_2 = null;
            } else {
                efficientSplit = new DoubleArrayStepper(this.unsafeArray(), 0, this.unsafeArray().length);
            }
            return (S)efficientSplit;
        }

        @Override
        public <B> ArraySeq<B> updated(int index, B elem) {
            if (elem instanceof Double) {
                double d = BoxesRunTime.unboxToDouble(elem);
                return new ofDouble((double[])ArrayOps$.MODULE$.updated$extension(this.unsafeArray(), index, d, ClassTag$.MODULE$.Double()));
            }
            return super.updated(index, (Object)elem);
        }

        @Override
        public <B> ArraySeq<B> appended(B elem) {
            if (elem instanceof Double) {
                double d = BoxesRunTime.unboxToDouble(elem);
                return new ofDouble((double[])ArrayOps$.MODULE$.appended$extension(this.unsafeArray(), d, ClassTag$.MODULE$.Double()));
            }
            return super.appended((Object)elem);
        }

        @Override
        public <B> ArraySeq<B> prepended(B elem) {
            if (elem instanceof Double) {
                double d = BoxesRunTime.unboxToDouble(elem);
                return new ofDouble((double[])ArrayOps$.MODULE$.prepended$extension(this.unsafeArray(), d, ClassTag$.MODULE$.Double()));
            }
            return super.prepended((Object)elem);
        }

        @Override
        public double apply$mcDI$sp(int i) {
            return this.unsafeArray()[i];
        }

        public ofDouble(double[] unsafeArray) {
            this.unsafeArray = unsafeArray;
        }
    }

    public static final class ofFloat
    extends ArraySeq<Object> {
        private static final long serialVersionUID = 3L;
        private final float[] unsafeArray;

        public float[] unsafeArray() {
            return this.unsafeArray;
        }

        public ManifestFactory.FloatManifest elemTag() {
            return ClassTag$.MODULE$.Float();
        }

        @Override
        public int length() {
            return this.unsafeArray().length;
        }

        @Override
        public float apply(int i) throws ArrayIndexOutOfBoundsException {
            return this.unsafeArray()[i];
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash$mFc$sp(this.unsafeArray());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofFloat) {
                ofFloat ofFloat2 = (ofFloat)that;
                return Arrays.equals(this.unsafeArray(), ofFloat2.unsafeArray());
            }
            return Seq.equals$(this, that);
        }

        @Override
        public Iterator<Object> iterator() {
            return new ArrayOps$ArrayIterator$mcF$sp(this.unsafeArray());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
            Stepper.EfficientSplit efficientSplit;
            if (shape.shape() == StepperShape$.MODULE$.ReferenceShape()) {
                WidenedFloatArrayStepper ofParDoubleStepper_st = new WidenedFloatArrayStepper(this.unsafeArray(), 0, this.unsafeArray().length);
                efficientSplit = new /* invalid duplicate definition of identical inner class */;
                Object var2_2 = null;
            } else {
                efficientSplit = new WidenedFloatArrayStepper(this.unsafeArray(), 0, this.unsafeArray().length);
            }
            return (S)efficientSplit;
        }

        @Override
        public <B> ArraySeq<B> updated(int index, B elem) {
            if (elem instanceof Float) {
                float f = BoxesRunTime.unboxToFloat(elem);
                return new ofFloat((float[])ArrayOps$.MODULE$.updated$extension(this.unsafeArray(), index, Float.valueOf(f), ClassTag$.MODULE$.Float()));
            }
            return super.updated(index, (Object)elem);
        }

        @Override
        public <B> ArraySeq<B> appended(B elem) {
            if (elem instanceof Float) {
                float f = BoxesRunTime.unboxToFloat(elem);
                return new ofFloat((float[])ArrayOps$.MODULE$.appended$extension(this.unsafeArray(), Float.valueOf(f), ClassTag$.MODULE$.Float()));
            }
            return super.appended((Object)elem);
        }

        @Override
        public <B> ArraySeq<B> prepended(B elem) {
            if (elem instanceof Float) {
                float f = BoxesRunTime.unboxToFloat(elem);
                return new ofFloat((float[])ArrayOps$.MODULE$.prepended$extension(this.unsafeArray(), Float.valueOf(f), ClassTag$.MODULE$.Float()));
            }
            return super.prepended((Object)elem);
        }

        @Override
        public float apply$mcFI$sp(int i) {
            return this.unsafeArray()[i];
        }

        public ofFloat(float[] unsafeArray) {
            this.unsafeArray = unsafeArray;
        }
    }

    public static final class ofInt
    extends ArraySeq<Object> {
        private static final long serialVersionUID = 3L;
        private final int[] unsafeArray;

        public int[] unsafeArray() {
            return this.unsafeArray;
        }

        public ManifestFactory.IntManifest elemTag() {
            return ClassTag$.MODULE$.Int();
        }

        @Override
        public int length() {
            return this.unsafeArray().length;
        }

        @Override
        public int apply(int i) throws ArrayIndexOutOfBoundsException {
            return this.unsafeArray()[i];
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash$mIc$sp(this.unsafeArray());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofInt) {
                ofInt ofInt2 = (ofInt)that;
                return Arrays.equals(this.unsafeArray(), ofInt2.unsafeArray());
            }
            return Seq.equals$(this, that);
        }

        @Override
        public <B> ArraySeq<Object> sorted(Ordering<B> ord) {
            if (this.unsafeArray().length <= 1) {
                return this;
            }
            if (ord == Ordering$Int$.MODULE$) {
                int[] a = (int[])this.unsafeArray().clone();
                Arrays.sort(a);
                return new ofInt(a);
            }
            return super.sorted((Ordering)ord);
        }

        @Override
        public Iterator<Object> iterator() {
            return new ArrayOps$ArrayIterator$mcI$sp(this.unsafeArray());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
            Stepper.EfficientSplit efficientSplit;
            if (shape.shape() == StepperShape$.MODULE$.ReferenceShape()) {
                IntArrayStepper ofParIntStepper_st = new IntArrayStepper(this.unsafeArray(), 0, this.unsafeArray().length);
                efficientSplit = new /* invalid duplicate definition of identical inner class */;
                Object var2_2 = null;
            } else {
                efficientSplit = new IntArrayStepper(this.unsafeArray(), 0, this.unsafeArray().length);
            }
            return (S)efficientSplit;
        }

        @Override
        public <B> ArraySeq<B> updated(int index, B elem) {
            if (elem instanceof Integer) {
                int n = BoxesRunTime.unboxToInt(elem);
                return new ofInt((int[])ArrayOps$.MODULE$.updated$extension(this.unsafeArray(), index, n, ClassTag$.MODULE$.Int()));
            }
            return super.updated(index, (Object)elem);
        }

        @Override
        public <B> ArraySeq<B> appended(B elem) {
            if (elem instanceof Integer) {
                int n = BoxesRunTime.unboxToInt(elem);
                return new ofInt((int[])ArrayOps$.MODULE$.appended$extension(this.unsafeArray(), n, ClassTag$.MODULE$.Int()));
            }
            return super.appended((Object)elem);
        }

        @Override
        public <B> ArraySeq<B> prepended(B elem) {
            if (elem instanceof Integer) {
                int n = BoxesRunTime.unboxToInt(elem);
                return new ofInt((int[])ArrayOps$.MODULE$.prepended$extension(this.unsafeArray(), n, ClassTag$.MODULE$.Int()));
            }
            return super.prepended((Object)elem);
        }

        @Override
        public int apply$mcII$sp(int i) {
            return this.unsafeArray()[i];
        }

        public ofInt(int[] unsafeArray) {
            this.unsafeArray = unsafeArray;
        }
    }

    public static final class ofLong
    extends ArraySeq<Object> {
        private static final long serialVersionUID = 3L;
        private final long[] unsafeArray;

        public long[] unsafeArray() {
            return this.unsafeArray;
        }

        public ManifestFactory.LongManifest elemTag() {
            return ClassTag$.MODULE$.Long();
        }

        @Override
        public int length() {
            return this.unsafeArray().length;
        }

        @Override
        public long apply(int i) throws ArrayIndexOutOfBoundsException {
            return this.unsafeArray()[i];
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash$mJc$sp(this.unsafeArray());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofLong) {
                ofLong ofLong2 = (ofLong)that;
                return Arrays.equals(this.unsafeArray(), ofLong2.unsafeArray());
            }
            return Seq.equals$(this, that);
        }

        @Override
        public <B> ArraySeq<Object> sorted(Ordering<B> ord) {
            if (this.unsafeArray().length <= 1) {
                return this;
            }
            if (ord == Ordering$Long$.MODULE$) {
                long[] a = (long[])this.unsafeArray().clone();
                Arrays.sort(a);
                return new ofLong(a);
            }
            return super.sorted((Ordering)ord);
        }

        @Override
        public Iterator<Object> iterator() {
            return new ArrayOps$ArrayIterator$mcJ$sp(this.unsafeArray());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
            Stepper.EfficientSplit efficientSplit;
            if (shape.shape() == StepperShape$.MODULE$.ReferenceShape()) {
                LongArrayStepper ofParLongStepper_st = new LongArrayStepper(this.unsafeArray(), 0, this.unsafeArray().length);
                efficientSplit = new Stepper.EfficientSplit(ofParLongStepper_st){};
                Object var2_2 = null;
            } else {
                efficientSplit = new LongArrayStepper(this.unsafeArray(), 0, this.unsafeArray().length);
            }
            return (S)efficientSplit;
        }

        @Override
        public <B> ArraySeq<B> updated(int index, B elem) {
            if (elem instanceof Long) {
                long l = BoxesRunTime.unboxToLong(elem);
                return new ofLong((long[])ArrayOps$.MODULE$.updated$extension(this.unsafeArray(), index, l, ClassTag$.MODULE$.Long()));
            }
            return super.updated(index, (Object)elem);
        }

        @Override
        public <B> ArraySeq<B> appended(B elem) {
            if (elem instanceof Long) {
                long l = BoxesRunTime.unboxToLong(elem);
                return new ofLong((long[])ArrayOps$.MODULE$.appended$extension(this.unsafeArray(), l, ClassTag$.MODULE$.Long()));
            }
            return super.appended((Object)elem);
        }

        @Override
        public <B> ArraySeq<B> prepended(B elem) {
            if (elem instanceof Long) {
                long l = BoxesRunTime.unboxToLong(elem);
                return new ofLong((long[])ArrayOps$.MODULE$.prepended$extension(this.unsafeArray(), l, ClassTag$.MODULE$.Long()));
            }
            return super.prepended((Object)elem);
        }

        @Override
        public long apply$mcJI$sp(int i) {
            return this.unsafeArray()[i];
        }

        public ofLong(long[] unsafeArray) {
            this.unsafeArray = unsafeArray;
        }
    }

    public static final class ofRef<T>
    extends ArraySeq<T> {
        private static final long serialVersionUID = 3L;
        private final T[] unsafeArray;

        public T[] unsafeArray() {
            return this.unsafeArray;
        }

        @Override
        public ClassTag<T> elemTag() {
            return ClassTag$.MODULE$.apply(this.unsafeArray().getClass().getComponentType());
        }

        @Override
        public int length() {
            return this.unsafeArray().length;
        }

        @Override
        public T apply(int i) throws ArrayIndexOutOfBoundsException {
            return this.unsafeArray()[i];
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash(this.unsafeArray());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofRef) {
                ofRef ofRef2 = (ofRef)that;
                return Array$.MODULE$.equals(this.unsafeArray(), ofRef2.unsafeArray());
            }
            return Seq.equals$(this, that);
        }

        @Override
        public <B> ofRef<T> sorted(Ordering<B> ord) {
            if (this.unsafeArray().length <= 1) {
                return this;
            }
            Object[] a = (Object[])this.unsafeArray().clone();
            Arrays.sort(a, ord);
            return new ofRef<Object>(a);
        }

        @Override
        public Iterator<T> iterator() {
            return new ArrayOps.ArrayIterator(this.unsafeArray());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<T, S> shape) {
            return (S)(shape.shape() == StepperShape$.MODULE$.ReferenceShape() ? new ObjectArrayStepper<T>(this.unsafeArray(), 0, this.unsafeArray().length) : shape.parUnbox(new ObjectArrayStepper<T>(this.unsafeArray(), 0, this.unsafeArray().length)));
        }

        public ofRef(T[] unsafeArray) {
            this.unsafeArray = unsafeArray;
        }
    }

    public static final class ofShort
    extends ArraySeq<Object> {
        private static final long serialVersionUID = 3L;
        private final short[] unsafeArray;

        public short[] unsafeArray() {
            return this.unsafeArray;
        }

        public ManifestFactory.ShortManifest elemTag() {
            return ClassTag$.MODULE$.Short();
        }

        @Override
        public int length() {
            return this.unsafeArray().length;
        }

        @Override
        public short apply(int i) throws ArrayIndexOutOfBoundsException {
            return this.unsafeArray()[i];
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash$mSc$sp(this.unsafeArray());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofShort) {
                ofShort ofShort2 = (ofShort)that;
                return Arrays.equals(this.unsafeArray(), ofShort2.unsafeArray());
            }
            return Seq.equals$(this, that);
        }

        @Override
        public <B> ArraySeq<Object> sorted(Ordering<B> ord) {
            if (this.unsafeArray().length <= 1) {
                return this;
            }
            if (ord == Ordering$Short$.MODULE$) {
                short[] a = (short[])this.unsafeArray().clone();
                Arrays.sort(a);
                return new ofShort(a);
            }
            return super.sorted((Ordering)ord);
        }

        @Override
        public Iterator<Object> iterator() {
            return new ArrayOps$ArrayIterator$mcS$sp(this.unsafeArray());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
            Stepper.EfficientSplit efficientSplit;
            if (shape.shape() == StepperShape$.MODULE$.ReferenceShape()) {
                WidenedShortArrayStepper ofParIntStepper_st = new WidenedShortArrayStepper(this.unsafeArray(), 0, this.unsafeArray().length);
                efficientSplit = new /* invalid duplicate definition of identical inner class */;
                Object var2_2 = null;
            } else {
                efficientSplit = new WidenedShortArrayStepper(this.unsafeArray(), 0, this.unsafeArray().length);
            }
            return (S)efficientSplit;
        }

        @Override
        public <B> ArraySeq<B> updated(int index, B elem) {
            if (elem instanceof Short) {
                short s2 = BoxesRunTime.unboxToShort(elem);
                return new ofShort((short[])ArrayOps$.MODULE$.updated$extension(this.unsafeArray(), index, s2, ClassTag$.MODULE$.Short()));
            }
            return super.updated(index, (Object)elem);
        }

        @Override
        public <B> ArraySeq<B> appended(B elem) {
            if (elem instanceof Short) {
                short s2 = BoxesRunTime.unboxToShort(elem);
                return new ofShort((short[])ArrayOps$.MODULE$.appended$extension(this.unsafeArray(), s2, ClassTag$.MODULE$.Short()));
            }
            return super.appended((Object)elem);
        }

        @Override
        public <B> ArraySeq<B> prepended(B elem) {
            if (elem instanceof Short) {
                short s2 = BoxesRunTime.unboxToShort(elem);
                return new ofShort((short[])ArrayOps$.MODULE$.prepended$extension(this.unsafeArray(), s2, ClassTag$.MODULE$.Short()));
            }
            return super.prepended((Object)elem);
        }

        public ofShort(short[] unsafeArray) {
            this.unsafeArray = unsafeArray;
        }
    }

    public static final class ofUnit
    extends ArraySeq<BoxedUnit> {
        private static final long serialVersionUID = 3L;
        private final BoxedUnit[] unsafeArray;

        public BoxedUnit[] unsafeArray() {
            return this.unsafeArray;
        }

        public ManifestFactory.UnitManifest elemTag() {
            return ClassTag$.MODULE$.Unit();
        }

        @Override
        public int length() {
            return this.unsafeArray().length;
        }

        @Override
        public void apply(int i) throws ArrayIndexOutOfBoundsException {
            BoxedUnit cfr_ignored_0 = this.unsafeArray()[i];
        }

        @Override
        public int hashCode() {
            return MurmurHash3$.MODULE$.arraySeqHash$mVc$sp(this.unsafeArray());
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof ofUnit) {
                ofUnit ofUnit2 = (ofUnit)that;
                return this.unsafeArray().length == ofUnit2.unsafeArray().length;
            }
            return Seq.equals$(this, that);
        }

        @Override
        public Iterator<BoxedUnit> iterator() {
            return new ArrayOps$ArrayIterator$mcV$sp(this.unsafeArray());
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<BoxedUnit, S> shape) {
            return (S)new ObjectArrayStepper<BoxedUnit>(this.unsafeArray(), 0, this.unsafeArray().length);
        }

        @Override
        public void apply$mcVI$sp(int i) {
            BoxedUnit cfr_ignored_0 = this.unsafeArray()[i];
        }

        public ofUnit(BoxedUnit[] unsafeArray) {
            this.unsafeArray = unsafeArray;
        }
    }
}

