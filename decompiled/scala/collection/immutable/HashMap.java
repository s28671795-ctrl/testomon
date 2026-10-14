/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.MatchError;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.Hashing$;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.Map;
import scala.collection.MapFactory;
import scala.collection.MapOps;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShape$;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.convert.impl.AnyChampStepper;
import scala.collection.convert.impl.DoubleChampStepper;
import scala.collection.convert.impl.IntChampStepper;
import scala.collection.convert.impl.LongChampStepper;
import scala.collection.generic.DefaultSerializable;
import scala.collection.immutable.AbstractMap;
import scala.collection.immutable.BitmapIndexedMapNode;
import scala.collection.immutable.HashMap;
import scala.collection.immutable.HashMap$;
import scala.collection.immutable.HashMapBuilder;
import scala.collection.immutable.HashSet;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.MapKeyIterator;
import scala.collection.immutable.MapKeyValueTupleHashIterator;
import scala.collection.immutable.MapKeyValueTupleIterator;
import scala.collection.immutable.MapKeyValueTupleReverseIterator;
import scala.collection.immutable.MapNode;
import scala.collection.immutable.MapNode$;
import scala.collection.immutable.MapNodeRemoveAllSetNodeIterator;
import scala.collection.immutable.MapOps;
import scala.collection.immutable.MapValueIterator;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Node;
import scala.collection.immutable.Node$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.collection.immutable.Set$EmptySet$;
import scala.collection.immutable.StrictOptimizedMapOps;
import scala.collection.mutable.AbstractBuffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.HashMap;
import scala.collection.mutable.HashSet;
import scala.collection.mutable.Queue;
import scala.collection.mutable.Queue$;
import scala.collection.mutable.ReusableBuilder;
import scala.reflect.ScalaSignature;
import scala.runtime.AbstractFunction2;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.Either;
import scala.util.hashing.MurmurHash3$;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@ScalaSignature(bytes="\u0006\u0005\u0011-a\u0001B!C\u0005%C\u0011\u0002\u001d\u0001\u0003\u0006\u0004%\tAQ9\t\u0011U\u0004!\u0011!Q\u0001\nIDaA\u001e\u0001\u0005\u0002\t;\b\"\u0002<\u0001\t\u0003I\b\"\u0002>\u0001\t\u0003Z\bBB@\u0001\t\u0003\n\t\u0001C\u0004\u0002\n\u0001!\t%!\u0001\t\u000f\u0005-\u0001\u0001\"\u0011\u0002\u000e!9\u0011Q\u0003\u0001\u0005B\u0005]aABA\u0010\u0001\u0019\t\t\u0003\u0003\u0004w\u0015\u0011\u0005\u0011q\u0006\u0005\t\u0003gQ\u0001\u0015\"\u0003\u00026!A\u00111\u0007\u0006!\n\u0013\t)\u0005C\u0004\u0002V)!\t%a\u0016\t\u000f\u0005u#\u0002\"\u0011\u0002`!9\u00111\r\u0006\u0005B\u0005\u0015\u0004bBA9\u0015\u0011\u0005\u00131\u000f\u0005\b\u0003o\u0002A\u0011AA=\u0011\u001d\t9\t\u0001C!\u0003\u0013Cq!!$\u0001\t\u0003\ny\t\u0003\u0005\u0002\u0014\u0002!\tBQA=\u0011\u001d\t)\n\u0001C!\u0003/Cq!!8\u0001\t\u0003\ny\u000eC\u0004\u0002|\u0002!\t%!@\t\u000f\te\u0001\u0001\"\u0012\u0003\u001c!9!\u0011\u0005\u0001\u0005B\t\r\u0002b\u0002B\u0014\u0001\u0011\u0005!\u0011\u0006\u0005\b\u0005g\u0001A\u0011\tB\u001b\u0011!\u0011i\u0005\u0001Q\u0005\n\t=\u0003b\u0002B3\u0001\u0011\u0005!q\r\u0005\b\u0005o\u0002A\u0011\tB=\u0011\u001d\u0011y\t\u0001C\u0001\u0005#CqA!&\u0001\t\u0003\u00129\nC\u0004\u00036\u0002!\tEa.\t\u000f\te\u0006\u0001\"\u0011\u00038\"9!1\u0018\u0001\u0005B\tu\u0006b\u0002B`\u0001\u0011\u0005#Q\u0018\u0005\b\u0005\u0003\u0004A\u0011\tBb\u0011\u001d\u0011I\u000e\u0001C!\u00057D\u0001Ba;\u0001\t\u0003!%Q\u001e\u0005\b\u0005s\u0004A\u0011\tB~\u0011\u001d\u0011y\u0010\u0001C!\u0007\u0003A\u0001ba\u0001\u0001A\u0013E3Q\u0001\u0005\b\u0007/\u0001A\u0011AB\r\u0011\u001d\u0019y\u0003\u0001C!\u0007cA\u0001b!\u0011\u0001\t#\"51\t\u0005\b\u0007\u001b\u0002A\u0011IB(\u0011\u001d\u0019I\u0006\u0001C!\u00077Bqaa\u0019\u0001\t\u0003\u001a)\u0007C\u0004\u0004l\u0001!\te!\u001c\t\u000f\rE\u0004\u0001\"\u0011\u0004t!91q\u000f\u0001\u0005B\re\u0004bBB?\u0001\u0011\u00053q\u0010\u0005\b\u0007\u0007\u0003A\u0011IBC\u0011\u001d\u0019I\t\u0001C!\u0007\u0017;qaa$C\u0011\u0003\u0019\tJ\u0002\u0004B\u0005\"\u000511\u0013\u0005\u0007mf\"\taa'\t\u0013\ru\u0015H1A\u0005\u000e\r}\u0005\u0002CBRs\u0001\u0006ia!)\t\u000f\r5\u0016\b\"\u0001\u00040\"91QX\u001d\u0005\u0002\r}\u0006bBBls\u0011\u00051\u0011\u001c\u0005\n\u0007kL\u0014\u0011!C\u0005\u0007o\u0014q\u0001S1tQ6\u000b\u0007O\u0003\u0002D\t\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003\u000b\u001a\u000b!bY8mY\u0016\u001cG/[8o\u0015\u00059\u0015!B:dC2\f7\u0001A\u000b\u0004\u0015Fc6#\u0002\u0001L=\u000eT\u0007\u0003\u0002'N\u001fnk\u0011AQ\u0005\u0003\u001d\n\u00131\"\u00112tiJ\f7\r^'baB\u0011\u0001+\u0015\u0007\u0001\t\u0015\u0011\u0006A1\u0001T\u0005\u0005Y\u0015C\u0001+Y!\t)f+D\u0001G\u0013\t9fIA\u0004O_RD\u0017N\\4\u0011\u0005UK\u0016B\u0001.G\u0005\r\te.\u001f\t\u0003!r#a!\u0018\u0001\u0005\u0006\u0004\u0019&!\u0001,\u0011\r1{vjW1c\u0013\t\u0001'IA\u000bTiJL7\r^(qi&l\u0017N_3e\u001b\u0006\u0004x\n]:\u0011\u00051\u0003\u0001\u0003\u0002'\u0001\u001fn\u0003b\u0001Z3P7\u0006<W\"\u0001#\n\u0005\u0019$%AE'ba\u001a\u000b7\r^8ss\u0012+g-Y;miN\u0004\"\u0001\u00145\n\u0005%\u0014%\u0001C%uKJ\f'\r\\3\u0011\u0005-tW\"\u00017\u000b\u00055$\u0015aB4f]\u0016\u0014\u0018nY\u0005\u0003_2\u00141\u0003R3gCVdGoU3sS\u0006d\u0017N_1cY\u0016\f\u0001B]8pi:{G-Z\u000b\u0002eB!Aj](\\\u0013\t!(I\u0001\u000bCSRl\u0017\r]%oI\u0016DX\rZ'ba:{G-Z\u0001\ne>|GOT8eK\u0002\na\u0001P5oSRtDC\u00012y\u0011\u0015\u00018\u00011\u0001s)\u0005\u0011\u0017AC7ba\u001a\u000b7\r^8ssV\tA\u0010E\u0002e{\u0006L!A #\u0003\u00155\u000b\u0007OR1di>\u0014\u00180A\u0005l]><hnU5{KV\u0011\u00111\u0001\t\u0004+\u0006\u0015\u0011bAA\u0004\r\n\u0019\u0011J\u001c;\u0002\tML'0Z\u0001\bSN,U\u000e\u001d;z+\t\ty\u0001E\u0002V\u0003#I1!a\u0005G\u0005\u001d\u0011un\u001c7fC:\faa[3z'\u0016$XCAA\r!\u0011a\u00151D(\n\u0007\u0005u!IA\u0002TKR\u0014!\u0002S1tQ.+\u0017pU3u'\rQ\u00111\u0005\t\u0005\u0003K\t9#D\u0001\u0001\u0013\u0011\tI#a\u000b\u0003\u001f%kW.\u001e;bE2,7*Z=TKRL1!!\fC\u0005\u0019i\u0015\r](qgR\u0011\u0011\u0011\u0007\t\u0004\u0003KQ\u0011a\u00048fo.+\u0017pU3u\u001fJ$\u0006.[:\u0015\t\u0005e\u0011q\u0007\u0005\b\u0003sa\u0001\u0019AA\u001e\u0003)qWm\u001e%bg\"l\u0015\r\u001d\u0019\u0005\u0003{\t\t\u0005E\u0003M\u0001=\u000by\u0004E\u0002Q\u0003\u0003\"1\"a\u0011\u00028\u0005\u0005\t\u0011!B\u0001'\n\u0019q\fJ\u0019\u0015\t\u0005e\u0011q\t\u0005\b\u0003\u0013j\u0001\u0019AA&\u0003-qWm\u001e*p_Rtu\u000eZ31\t\u00055\u0013\u0011\u000b\t\u0006\u0019N|\u0015q\n\t\u0004!\u0006ECaCA*\u0003\u000f\n\t\u0011!A\u0003\u0002M\u00131a\u0018\u00133\u0003\u0011Ign\u00197\u0015\t\u0005e\u0011\u0011\f\u0005\u0007\u00037r\u0001\u0019A(\u0002\t\u0015dW-\\\u0001\u0005Kb\u001cG\u000e\u0006\u0003\u0002\u001a\u0005\u0005\u0004BBA.\u001f\u0001\u0007q*\u0001\u0004gS2$XM\u001d\u000b\u0005\u00033\t9\u0007C\u0004\u0002jA\u0001\r!a\u001b\u0002\tA\u0014X\r\u001a\t\u0007+\u00065t*a\u0004\n\u0007\u0005=dIA\u0005Gk:\u001cG/[8oc\u0005Ia-\u001b7uKJtu\u000e\u001e\u000b\u0005\u00033\t)\bC\u0004\u0002jE\u0001\r!a\u001b\u0002\u0011%$XM]1u_J,\"!a\u001f\u0011\u000b\u0011\fi(!!\n\u0007\u0005}DI\u0001\u0005Ji\u0016\u0014\u0018\r^8s!\u0015)\u00161Q(\\\u0013\r\t)I\u0012\u0002\u0007)V\u0004H.\u001a\u001a\u0002\u0019-,\u0017p]%uKJ\fGo\u001c:\u0016\u0005\u0005-\u0005\u0003\u00023\u0002~=\u000baB^1mk\u0016\u001c\u0018\n^3sCR|'/\u0006\u0002\u0002\u0012B!A-! \\\u0003=\u0011XM^3sg\u0016LE/\u001a:bi>\u0014\u0018aB:uKB\u0004XM]\u000b\u0005\u00033\u000b\u0019\u000b\u0006\u0003\u0002\u001c\u0006M'CBAO\u0003C\u000b9L\u0002\u0004\u0002 \u0002\u0001\u00111\u0014\u0002\ryI,g-\u001b8f[\u0016tGO\u0010\t\u0004!\u0006\rFaBAS-\t\u0007\u0011q\u0015\u0002\u0002'F\u0019A+!+1\t\u0005-\u00161\u0017\t\u0006I\u00065\u0016\u0011W\u0005\u0004\u0003_#%aB*uKB\u0004XM\u001d\t\u0004!\u0006MFaCA[\u0003G\u000b\t\u0011!A\u0003\u0002M\u00131a\u0018\u00134!\u0011\tI,!4\u000f\t\u0005m\u0016\u0011\u001a\b\u0005\u0003{\u000b9M\u0004\u0003\u0002@\u0006\u0015WBAAa\u0015\r\t\u0019\rS\u0001\u0007yI|w\u000e\u001e \n\u0003\u001dK!!\u0012$\n\u0007\u0005-G)A\u0004Ti\u0016\u0004\b/\u001a:\n\t\u0005=\u0017\u0011\u001b\u0002\u000f\u000b\u001a4\u0017nY5f]R\u001c\u0006\u000f\\5u\u0015\r\tY\r\u0012\u0005\b\u0003+4\u00029AAl\u0003\u0015\u0019\b.\u00199f!\u001d!\u0017\u0011\\AA\u0003CK1!a7E\u00051\u0019F/\u001a9qKJ\u001c\u0006.\u00199f\u0003)YW-_*uKB\u0004XM]\u000b\u0005\u0003C\fI\u000f\u0006\u0003\u0002d\u0006](CBAs\u0003O\f9L\u0002\u0004\u0002 \u0002\u0001\u00111\u001d\t\u0004!\u0006%HaBAS/\t\u0007\u00111^\t\u0004)\u00065\b\u0007BAx\u0003g\u0004R\u0001ZAW\u0003c\u00042\u0001UAz\t-\t)0!;\u0002\u0002\u0003\u0005)\u0011A*\u0003\u0007}#C\u0007C\u0004\u0002V^\u0001\u001d!!?\u0011\r\u0011\fInTAt\u000311\u0018\r\\;f'R,\u0007\u000f]3s+\u0011\tyPa\u0002\u0015\t\t\u0005!Q\u0003\n\u0007\u0005\u0007\u0011)!a.\u0007\r\u0005}\u0005\u0001\u0001B\u0001!\r\u0001&q\u0001\u0003\b\u0003KC\"\u0019\u0001B\u0005#\r!&1\u0002\u0019\u0005\u0005\u001b\u0011\t\u0002E\u0003e\u0003[\u0013y\u0001E\u0002Q\u0005#!1Ba\u0005\u0003\b\u0005\u0005\t\u0011!B\u0001'\n\u0019q\fJ\u001b\t\u000f\u0005U\u0007\u0004q\u0001\u0003\u0018A1A-!7\\\u0005\u000b\t\u0001bY8oi\u0006Lgn\u001d\u000b\u0005\u0003\u001f\u0011i\u0002\u0003\u0004\u0003 e\u0001\raT\u0001\u0004W\u0016L\u0018!B1qa2LHcA.\u0003&!1!q\u0004\u000eA\u0002=\u000b1aZ3u)\u0011\u0011YC!\r\u0011\tU\u0013icW\u0005\u0004\u0005_1%AB(qi&|g\u000e\u0003\u0004\u0003 m\u0001\raT\u0001\nO\u0016$xJ]#mg\u0016,BAa\u000e\u0003<Q1!\u0011\bB!\u0005\u0007\u00022\u0001\u0015B\u001e\t\u001d\u0011i\u0004\bb\u0001\u0005\u007f\u0011!AV\u0019\u0012\u0005mC\u0006B\u0002B\u00109\u0001\u0007q\n\u0003\u0005\u0003Fq!\t\u0019\u0001B$\u0003\u001d!WMZ1vYR\u0004R!\u0016B%\u0005sI1Aa\u0013G\u0005!a$-\u001f8b[\u0016t\u0014\u0001\u00058fo\"\u000b7\u000f['ba>\u0013H\u000b[5t+\u0011\u0011\tFa\u0016\u0015\t\tM#\u0011\f\t\u0006\u0019\u0002y%Q\u000b\t\u0004!\n]Ca\u0002B\u001f;\t\u0007!q\b\u0005\b\u0003\u0013j\u0002\u0019\u0001B.!\u0015a5o\u0014B+Q\ri\"q\f\t\u0004+\n\u0005\u0014b\u0001B2\r\n1\u0011N\u001c7j]\u0016\fq!\u001e9eCR,G-\u0006\u0003\u0003j\t=DC\u0002B6\u0005c\u0012\u0019\bE\u0003M\u0001=\u0013i\u0007E\u0002Q\u0005_\"qA!\u0010\u001f\u0005\u0004\u0011y\u0004\u0003\u0004\u0003 y\u0001\ra\u0014\u0005\b\u0005kr\u0002\u0019\u0001B7\u0003\u00151\u0018\r\\;f\u0003-)\b\u000fZ1uK\u0012<\u0016\u000e\u001e5\u0016\t\tm$1\u0011\u000b\u0005\u0005{\u0012i\t\u0006\u0003\u0003\u0000\t\u0015\u0005#\u0002'\u0001\u001f\n\u0005\u0005c\u0001)\u0003\u0004\u00129!QH\u0010C\u0002\t}\u0002b\u0002BD?\u0001\u0007!\u0011R\u0001\u0012e\u0016l\u0017\r\u001d9j]\u001e4UO\\2uS>t\u0007cB+\u0002n\t-\"1\u0012\t\u0006+\n5\"\u0011\u0011\u0005\u0007\u0005?y\u0002\u0019A(\u0002\u000fI,Wn\u001c<fIR\u0019!Ma%\t\r\t}\u0001\u00051\u0001P\u0003\u0019\u0019wN\\2biV!!\u0011\u0014BP)\u0011\u0011YJ!)\u0011\u000b1\u0003qJ!(\u0011\u0007A\u0013y\nB\u0004\u0003>\u0005\u0012\rAa\u0010\t\u000f\t\r\u0016\u00051\u0001\u0003&\u0006!A\u000f[1u!\u0019\u00119K!,\u00034:!\u0011Q\u0018BU\u0013\r\u0011YKR\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\u0011yK!-\u0003\u0019%#XM]1cY\u0016|enY3\u000b\u0007\t-f\t\u0005\u0004V\u0003\u0007{%QT\u0001\u0005i\u0006LG.F\u0001c\u0003\u0011Ig.\u001b;\u0002\t!,\u0017\rZ\u000b\u0003\u0003\u0003\u000bA\u0001\\1ti\u00069am\u001c:fC\u000eDW\u0003\u0002Bc\u0005+$BAa2\u0003NB\u0019QK!3\n\u0007\t-gI\u0001\u0003V]&$\bb\u0002BhM\u0001\u0007!\u0011[\u0001\u0002MB9Q+!\u001c\u0002\u0002\nM\u0007c\u0001)\u0003V\u00121!q\u001b\u0014C\u0002M\u0013\u0011!V\u0001\rM>\u0014X-Y2i\u000b:$(/_\u000b\u0005\u0005;\u0014I\u000f\u0006\u0003\u0003H\n}\u0007b\u0002BhO\u0001\u0007!\u0011\u001d\t\b+\n\rxj\u0017Bt\u0013\r\u0011)O\u0012\u0002\n\rVt7\r^5p]J\u00022\u0001\u0015Bu\t\u0019\u00119n\nb\u0001'\u0006yam\u001c:fC\u000eDw+\u001b;i\u0011\u0006\u001c\b\u000e\u0006\u0003\u0003H\n=\bb\u0002BhQ\u0001\u0007!\u0011\u001f\t\n+\nMxjWA\u0002\u0005\u000fL1A!>G\u0005%1UO\\2uS>t7\u0007K\u0002)\u0005?\na!Z9vC2\u001cH\u0003BA\b\u0005{DaAa)*\u0001\u0004A\u0016\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005\r\u0011!C2mCN\u001ch*Y7f+\t\u00199\u0001\u0005\u0003\u0004\n\rMQBAB\u0006\u0015\u0011\u0019iaa\u0004\u0002\t1\fgn\u001a\u0006\u0003\u0007#\tAA[1wC&!1QCB\u0006\u0005\u0019\u0019FO]5oO\u00061Q.\u001a:hK\u0012,Baa\u0007\u0004$Q!1QDB\u0017)\u0011\u0019yb!\n\u0011\u000b1\u0003qj!\t\u0011\u0007A\u001b\u0019\u0003B\u0004\u0003>1\u0012\rAa\u0010\t\u000f\r\u001dB\u00061\u0001\u0004*\u00051Q.\u001a:hK\u001a\u0004\u0012\"\u0016Br\u0003\u0003\u001bYca\u000b\u0011\rU\u000b\u0019iTB\u0011\u0011\u001d\u0011\u0019\u000b\fa\u0001\u0007?\t\u0011\u0002\u001e:b]N4wN]7\u0016\t\rM2\u0011\b\u000b\u0005\u0007k\u0019i\u0004E\u0003M\u0001=\u001b9\u0004E\u0002Q\u0007s!aaa\u000f.\u0005\u0004\u0019&!A,\t\u000f\t=W\u00061\u0001\u0004@A9QKa9P7\u000e]\u0012A\u00034jYR,'/S7qYR)!m!\u0012\u0004J!9\u0011\u0011\u000e\u0018A\u0002\r\u001d\u0003cB+\u0002n\u0005\u0005\u0015q\u0002\u0005\b\u0007\u0017r\u0003\u0019AA\b\u0003%I7O\u00127jaB,G-\u0001\u0006sK6|g/\u001a3BY2$2AYB)\u0011\u001d\u0019\u0019f\fa\u0001\u0007+\nAa[3zgB)1q\u000bBW\u001f:\u0019QK!+\u0002\u0013A\f'\u000f^5uS>tG\u0003BB/\u0007?\u0002R!VABE\nDqa!\u00191\u0001\u0004\u00199%A\u0001q\u0003\u0011!\u0018m[3\u0015\u0007\t\u001c9\u0007C\u0004\u0004jE\u0002\r!a\u0001\u0002\u00039\f\u0011\u0002^1lKJKw\r\u001b;\u0015\u0007\t\u001cy\u0007C\u0004\u0004jI\u0002\r!a\u0001\u0002\u0013Q\f7.Z,iS2,Gc\u00012\u0004v!91\u0011M\u001aA\u0002\r\u001d\u0013!\u00033s_B<\u0006.\u001b7f)\r\u001171\u0010\u0005\b\u0007C\"\u0004\u0019AB$\u0003%!'o\u001c9SS\u001eDG\u000fF\u0002c\u0007\u0003Cqa!\u001b6\u0001\u0004\t\u0019!\u0001\u0003ee>\u0004Hc\u00012\u0004\b\"91\u0011\u000e\u001cA\u0002\u0005\r\u0011\u0001B:qC:$Ba!\u0018\u0004\u000e\"91\u0011M\u001cA\u0002\r\u001d\u0013a\u0002%bg\"l\u0015\r\u001d\t\u0003\u0019f\u001aB!OBKyB\u0019Qka&\n\u0007\reeI\u0001\u0004B]f\u0014VM\u001a\u000b\u0003\u0007#\u000b\u0001\"R7qifl\u0015\r]\u000b\u0003\u0007C\u0003B\u0001\u0014\u0001U)\u0006IQ)\u001c9us6\u000b\u0007\u000f\t\u0015\u0004y\r\u001d\u0006cA+\u0004*&\u001911\u0016$\u0003\u0013Q\u0014\u0018M\\:jK:$\u0018!B3naRLXCBBY\u0007o\u001bY,\u0006\u0002\u00044B1A\nAB[\u0007s\u00032\u0001UB\\\t\u0015\u0011VH1\u0001T!\r\u000161\u0018\u0003\u0006;v\u0012\raU\u0001\u0005MJ|W.\u0006\u0004\u0004B\u000e\u001d71\u001a\u000b\u0005\u0007\u0007\u001ci\r\u0005\u0004M\u0001\r\u00157\u0011\u001a\t\u0004!\u000e\u001dG!\u0002*?\u0005\u0004\u0019\u0006c\u0001)\u0004L\u0012)QL\u0010b\u0001'\"91q\u001a A\u0002\rE\u0017AB:pkJ\u001cW\rE\u0003e\u0007'\u001c).C\u0002\u00030\u0012\u0003r!VAB\u0007\u000b\u001cI-\u0001\u0006oK^\u0014U/\u001b7eKJ,baa7\u0004n\u000eEXCABo!!\u0019yn!:\u0004j\u000eMXBABq\u0015\r\u0019\u0019\u000fR\u0001\b[V$\u0018M\u00197f\u0013\u0011\u00199o!9\u0003\u001fI+Wo]1cY\u0016\u0014U/\u001b7eKJ\u0004r!VAB\u0007W\u001cy\u000fE\u0002Q\u0007[$QAU C\u0002M\u00032\u0001UBy\t\u0015ivH1\u0001T!\u0019a\u0005aa;\u0004p\u0006aqO]5uKJ+\u0007\u000f\\1dKR\u00111\u0011 \t\u0005\u0007\u0013\u0019Y0\u0003\u0003\u0004~\u000e-!AB(cU\u0016\u001cG\u000fK\u0004:\t\u0003\u0011)\bb\u0002\u0011\u0007U#\u0019!C\u0002\u0005\u0006\u0019\u0013\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u001f\u0003\rAs\u0001\u000fC\u0001\u0005k\"9\u0001")
public final class HashMap<K, V>
extends AbstractMap<K, V>
implements StrictOptimizedMapOps<K, V, HashMap, HashMap<K, V>>,
DefaultSerializable {
    private final BitmapIndexedMapNode<K, V> rootNode;

    public static <K, V> ReusableBuilder<Tuple2<K, V>, HashMap<K, V>> newBuilder() {
        return new HashMapBuilder();
    }

    public static <K, V> HashMap<K, V> from(IterableOnce<Tuple2<K, V>> source2) {
        return HashMap$.MODULE$.from((IterableOnce)source2);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
    }

    @Override
    public IterableOps map(Function1 f) {
        return scala.collection.StrictOptimizedMapOps.map$(this, f);
    }

    @Override
    public IterableOps flatMap(Function1 f) {
        return scala.collection.StrictOptimizedMapOps.flatMap$(this, f);
    }

    @Override
    public IterableOps collect(PartialFunction pf) {
        return scala.collection.StrictOptimizedMapOps.collect$(this, pf);
    }

    @Override
    public IterableOps $plus(Tuple2 elem1, Tuple2 elem2, Seq elems) {
        return scala.collection.StrictOptimizedMapOps.$plus$(this, elem1, elem2, elems);
    }

    @Override
    public <A1, A2> Tuple2<Iterable<A1>, Iterable<A2>> unzip(Function1<Tuple2<K, V>, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<Iterable<A1>, Iterable<A2>, Iterable<A3>> unzip3(Function1<Tuple2<K, V>, Tuple3<A1, A2, A3>> asTriple) {
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
    public Object filter(Function1 pred) {
        return StrictOptimizedIterableOps.filter$(this, pred);
    }

    @Override
    public Object filterNot(Function1 pred) {
        return StrictOptimizedIterableOps.filterNot$(this, pred);
    }

    @Override
    public <A1, A2> Tuple2<Iterable<A1>, Iterable<A2>> partitionMap(Function1<Tuple2<K, V>, Either<A1, A2>> f) {
        return StrictOptimizedIterableOps.partitionMap$(this, f);
    }

    @Override
    public Object tapEach(Function1 f) {
        return StrictOptimizedIterableOps.tapEach$(this, f);
    }

    public BitmapIndexedMapNode<K, V> rootNode() {
        return this.rootNode;
    }

    @Override
    public MapFactory<HashMap> mapFactory() {
        return HashMap$.MODULE$;
    }

    @Override
    public int knownSize() {
        return this.rootNode().size();
    }

    @Override
    public int size() {
        return this.rootNode().size();
    }

    @Override
    public boolean isEmpty() {
        return this.rootNode().size() == 0;
    }

    @Override
    public Set<K> keySet() {
        if (this.size() == 0) {
            return Set$EmptySet$.MODULE$;
        }
        return new HashKeySet(this);
    }

    @Override
    public Iterator<Tuple2<K, V>> iterator() {
        if (this.isEmpty()) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        return new MapKeyValueTupleIterator<K, V>(this.rootNode());
    }

    @Override
    public Iterator<K> keysIterator() {
        if (this.isEmpty()) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        return new MapKeyIterator<K, V>(this.rootNode());
    }

    @Override
    public Iterator<V> valuesIterator() {
        if (this.isEmpty()) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        return new MapValueIterator<K, V>(this.rootNode());
    }

    public Iterator<Tuple2<K, V>> reverseIterator() {
        if (this.isEmpty()) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        return new MapKeyValueTupleReverseIterator<K, V>(this.rootNode());
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<Tuple2<K, V>, S> shape) {
        void from_extract;
        Function2<MapNode, Object, Tuple2> & Serializable intersect = (Function2<MapNode, Object, Tuple2> & Serializable)(node, i) -> node.getPayload(BoxesRunTime.unboxToInt(i));
        BitmapIndexedMapNode<K, V> from_root = this.rootNode();
        int from_maxSize = this.size();
        AnyChampStepper from_ans = new AnyChampStepper(from_maxSize, from_extract);
        from_ans.initRoot(from_root);
        Object var3_3 = null;
        intersect = null;
        Object var5_5 = null;
        return shape.parUnbox(from_ans);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <S extends Stepper<?>> S keyStepper(StepperShape<K, S> shape) {
        void from_root;
        void from_extract;
        int n = shape.shape();
        if (StepperShape$.MODULE$.IntShape() == n) {
            void from_root2;
            void from_extract2;
            Function2<MapNode, Object, Object> & Serializable intersect = (Function2<MapNode, Object, Object> & Serializable)(node, i) -> BoxesRunTime.boxToInteger(HashMap.$anonfun$keyStepper$1(node, BoxesRunTime.unboxToInt(i)));
            BitmapIndexedMapNode<K, V> bitmapIndexedMapNode = this.rootNode();
            int from_maxSize = this.size();
            IntChampStepper<void> from_ans = new IntChampStepper<void>(from_maxSize, (Function2<void, Object, Object>)from_extract2);
            from_ans.initRoot(from_root2);
            return (S)from_ans;
        }
        if (StepperShape$.MODULE$.LongShape() == n) {
            void from_root3;
            void from_extract3;
            Function2<MapNode, Object, Object> & Serializable intersect = (Function2<MapNode, Object, Object> & Serializable)(node, i) -> BoxesRunTime.boxToLong(HashMap.$anonfun$keyStepper$2(node, BoxesRunTime.unboxToInt(i)));
            BitmapIndexedMapNode<K, V> bitmapIndexedMapNode = this.rootNode();
            int from_maxSize = this.size();
            LongChampStepper<void> from_ans = new LongChampStepper<void>(from_maxSize, (Function2<void, Object, Object>)from_extract3);
            from_ans.initRoot(from_root3);
            return (S)from_ans;
        }
        if (StepperShape$.MODULE$.DoubleShape() == n) {
            void from_root4;
            void from_extract4;
            Function2<MapNode, Object, Object> & Serializable intersect = (Function2<MapNode, Object, Object> & Serializable)(node, i) -> BoxesRunTime.boxToDouble(HashMap.$anonfun$keyStepper$3(node, BoxesRunTime.unboxToInt(i)));
            BitmapIndexedMapNode<K, V> bitmapIndexedMapNode = this.rootNode();
            int from_maxSize = this.size();
            DoubleChampStepper<void> from_ans = new DoubleChampStepper<void>(from_maxSize, (Function2<void, Object, Object>)from_extract4);
            from_ans.initRoot(from_root4);
            return (S)from_ans;
        }
        Function2<MapNode, Object, Object> & Serializable intersect = (Function2<MapNode, Object, Object> & Serializable)(node, i) -> node.getKey(BoxesRunTime.unboxToInt(i));
        BitmapIndexedMapNode<K, V> bitmapIndexedMapNode = this.rootNode();
        int from_maxSize = this.size();
        AnyChampStepper from_ans = new AnyChampStepper(from_maxSize, from_extract);
        from_ans.initRoot(from_root);
        bitmapIndexedMapNode = null;
        intersect = null;
        Object var18_18 = null;
        return shape.parUnbox(from_ans);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <S extends Stepper<?>> S valueStepper(StepperShape<V, S> shape) {
        void from_root;
        void from_extract;
        int n = shape.shape();
        if (StepperShape$.MODULE$.IntShape() == n) {
            void from_root2;
            void from_extract2;
            Function2<MapNode, Object, Object> & Serializable intersect = (Function2<MapNode, Object, Object> & Serializable)(node, i) -> BoxesRunTime.boxToInteger(HashMap.$anonfun$valueStepper$1(node, BoxesRunTime.unboxToInt(i)));
            BitmapIndexedMapNode<K, V> bitmapIndexedMapNode = this.rootNode();
            int from_maxSize = this.size();
            IntChampStepper<void> from_ans = new IntChampStepper<void>(from_maxSize, (Function2<void, Object, Object>)from_extract2);
            from_ans.initRoot(from_root2);
            return (S)from_ans;
        }
        if (StepperShape$.MODULE$.LongShape() == n) {
            void from_root3;
            void from_extract3;
            Function2<MapNode, Object, Object> & Serializable intersect = (Function2<MapNode, Object, Object> & Serializable)(node, i) -> BoxesRunTime.boxToLong(HashMap.$anonfun$valueStepper$2(node, BoxesRunTime.unboxToInt(i)));
            BitmapIndexedMapNode<K, V> bitmapIndexedMapNode = this.rootNode();
            int from_maxSize = this.size();
            LongChampStepper<void> from_ans = new LongChampStepper<void>(from_maxSize, (Function2<void, Object, Object>)from_extract3);
            from_ans.initRoot(from_root3);
            return (S)from_ans;
        }
        if (StepperShape$.MODULE$.DoubleShape() == n) {
            void from_root4;
            void from_extract4;
            Function2<MapNode, Object, Object> & Serializable intersect = (Function2<MapNode, Object, Object> & Serializable)(node, i) -> BoxesRunTime.boxToDouble(HashMap.$anonfun$valueStepper$3(node, BoxesRunTime.unboxToInt(i)));
            BitmapIndexedMapNode<K, V> bitmapIndexedMapNode = this.rootNode();
            int from_maxSize = this.size();
            DoubleChampStepper<void> from_ans = new DoubleChampStepper<void>(from_maxSize, (Function2<void, Object, Object>)from_extract4);
            from_ans.initRoot(from_root4);
            return (S)from_ans;
        }
        Function2<MapNode, Object, Object> & Serializable intersect = (Function2<MapNode, Object, Object> & Serializable)(node, i) -> node.getValue(BoxesRunTime.unboxToInt(i));
        BitmapIndexedMapNode<K, V> bitmapIndexedMapNode = this.rootNode();
        int from_maxSize = this.size();
        AnyChampStepper from_ans = new AnyChampStepper(from_maxSize, from_extract);
        from_ans.initRoot(from_root);
        bitmapIndexedMapNode = null;
        intersect = null;
        Object var18_18 = null;
        return shape.parUnbox(from_ans);
    }

    @Override
    public final boolean contains(K key) {
        int keyUnimprovedHash = Statics.anyHash(key);
        int keyHash = Hashing$.MODULE$.improve(keyUnimprovedHash);
        return this.rootNode().containsKey(key, keyUnimprovedHash, keyHash, 0);
    }

    @Override
    public V apply(K key) {
        int keyUnimprovedHash = Statics.anyHash(key);
        int keyHash = Hashing$.MODULE$.improve(keyUnimprovedHash);
        return this.rootNode().apply(key, keyUnimprovedHash, keyHash, 0);
    }

    @Override
    public Option<V> get(K key) {
        int keyUnimprovedHash = Statics.anyHash(key);
        int keyHash = Hashing$.MODULE$.improve(keyUnimprovedHash);
        return this.rootNode().get(key, keyUnimprovedHash, keyHash, 0);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <V1> V1 getOrElse(K key, Function0<V1> function0) {
        void getOrElse_shift;
        int keyUnimprovedHash = Statics.anyHash(key);
        int keyHash = Hashing$.MODULE$.improve(keyUnimprovedHash);
        BitmapIndexedMapNode<K, V> bitmapIndexedMapNode = this.rootNode();
        boolean bl = false;
        if (bitmapIndexedMapNode == null) {
            throw null;
        }
        BitmapIndexedMapNode<K, V> getOrElse_this = bitmapIndexedMapNode;
        int getOrElse_mask = keyHash >>> getOrElse_shift & 0x1F;
        int getOrElse_bitpos = 1 << getOrElse_mask;
        if ((getOrElse_this.dataMap() & getOrElse_bitpos) != 0) {
            int getOrElse_index = Node$.MODULE$.indexFrom(getOrElse_this.dataMap(), getOrElse_mask, getOrElse_bitpos);
            Object getOrElse_key0 = getOrElse_this.content()[2 * getOrElse_index];
            if (BoxesRunTime.equals(key, getOrElse_key0)) {
                return (V1)getOrElse_this.content()[2 * getOrElse_index + 1];
            }
            return function0.apply();
        }
        if ((getOrElse_this.nodeMap() & getOrElse_bitpos) != 0) {
            int getOrElse_index = Node$.MODULE$.indexFrom(getOrElse_this.nodeMap(), getOrElse_mask, getOrElse_bitpos);
            return ((MapNode)getOrElse_this.getNode(getOrElse_index)).getOrElse(key, keyUnimprovedHash, keyHash, (int)(getOrElse_shift + 5), function0);
        }
        return function0.apply();
    }

    private <V1> HashMap<K, V1> newHashMapOrThis(BitmapIndexedMapNode<K, V1> newRootNode) {
        if (newRootNode == this.rootNode()) {
            return this;
        }
        return new HashMap<K, V1>(newRootNode);
    }

    @Override
    public <V1> HashMap<K, V1> updated(K key, V1 value) {
        int keyUnimprovedHash = Statics.anyHash(key);
        MapNode newHashMapOrThis_newRootNode = this.rootNode().updated((Object)key, (Object)value, keyUnimprovedHash, Hashing$.MODULE$.improve(keyUnimprovedHash), 0, true);
        if (newHashMapOrThis_newRootNode == this.rootNode()) {
            return this;
        }
        return new HashMap<K, V>(newHashMapOrThis_newRootNode);
    }

    @Override
    public <V1> HashMap<K, V1> updatedWith(K key, Function1<Option<V>, Option<V1>> remappingFunction) {
        return (HashMap)scala.collection.immutable.MapOps.updatedWith$(this, key, remappingFunction);
    }

    @Override
    public HashMap<K, V> removed(K key) {
        int keyUnimprovedHash = Statics.anyHash(key);
        MapNode newHashMapOrThis_newRootNode = this.rootNode().removed((Object)key, keyUnimprovedHash, Hashing$.MODULE$.improve(keyUnimprovedHash), 0);
        if (newHashMapOrThis_newRootNode == this.rootNode()) {
            return this;
        }
        return new HashMap<K, V>(newHashMapOrThis_newRootNode);
    }

    @Override
    public <V1> HashMap<K, V1> concat(IterableOnce<Tuple2<K, V1>> that) {
        public class Scala_collection_immutable_HashMap$accum$1
        extends AbstractFunction2<K, V1, BoxedUnit>
        implements Function1<Tuple2<K, V1>, BoxedUnit> {
            private boolean changed;
            private int shallowlyMutableNodeMap;
            private BitmapIndexedMapNode<K, V1> current;
            private final /* synthetic */ HashMap $outer;

            public boolean apply$mcZD$sp(double v1) {
                return Function1.apply$mcZD$sp$(this, v1);
            }

            public double apply$mcDD$sp(double v1) {
                return Function1.apply$mcDD$sp$(this, v1);
            }

            public float apply$mcFD$sp(double v1) {
                return Function1.apply$mcFD$sp$(this, v1);
            }

            public int apply$mcID$sp(double v1) {
                return Function1.apply$mcID$sp$(this, v1);
            }

            public long apply$mcJD$sp(double v1) {
                return Function1.apply$mcJD$sp$(this, v1);
            }

            public void apply$mcVD$sp(double v1) {
                Function1.apply$mcVD$sp$(this, v1);
            }

            public boolean apply$mcZF$sp(float v1) {
                return Function1.apply$mcZF$sp$(this, v1);
            }

            public double apply$mcDF$sp(float v1) {
                return Function1.apply$mcDF$sp$(this, v1);
            }

            public float apply$mcFF$sp(float v1) {
                return Function1.apply$mcFF$sp$(this, v1);
            }

            public int apply$mcIF$sp(float v1) {
                return Function1.apply$mcIF$sp$(this, v1);
            }

            public long apply$mcJF$sp(float v1) {
                return Function1.apply$mcJF$sp$(this, v1);
            }

            public void apply$mcVF$sp(float v1) {
                Function1.apply$mcVF$sp$(this, v1);
            }

            public boolean apply$mcZI$sp(int v1) {
                return Function1.apply$mcZI$sp$(this, v1);
            }

            public double apply$mcDI$sp(int v1) {
                return Function1.apply$mcDI$sp$(this, v1);
            }

            public float apply$mcFI$sp(int v1) {
                return Function1.apply$mcFI$sp$(this, v1);
            }

            public int apply$mcII$sp(int v1) {
                return Function1.apply$mcII$sp$(this, v1);
            }

            public long apply$mcJI$sp(int v1) {
                return Function1.apply$mcJI$sp$(this, v1);
            }

            public void apply$mcVI$sp(int v1) {
                Function1.apply$mcVI$sp$(this, v1);
            }

            public boolean apply$mcZJ$sp(long v1) {
                return Function1.apply$mcZJ$sp$(this, v1);
            }

            public double apply$mcDJ$sp(long v1) {
                return Function1.apply$mcDJ$sp$(this, v1);
            }

            public float apply$mcFJ$sp(long v1) {
                return Function1.apply$mcFJ$sp$(this, v1);
            }

            public int apply$mcIJ$sp(long v1) {
                return Function1.apply$mcIJ$sp$(this, v1);
            }

            public long apply$mcJJ$sp(long v1) {
                return Function1.apply$mcJJ$sp$(this, v1);
            }

            public void apply$mcVJ$sp(long v1) {
                Function1.apply$mcVJ$sp$(this, v1);
            }

            public <A$> Function1<A$, BoxedUnit> compose(Function1<A$, Tuple2<K, V1>> g2) {
                return Function1.compose$(this, g2);
            }

            public <A$> Function1<Tuple2<K, V1>, A$> andThen(Function1<BoxedUnit, A$> g2) {
                return Function1.andThen$(this, g2);
            }

            public String toString() {
                return Function1.toString$(this);
            }

            public boolean changed() {
                return this.changed;
            }

            public void changed_$eq(boolean x$1) {
                this.changed = x$1;
            }

            public int shallowlyMutableNodeMap() {
                return this.shallowlyMutableNodeMap;
            }

            public void shallowlyMutableNodeMap_$eq(int x$1) {
                this.shallowlyMutableNodeMap = x$1;
            }

            public BitmapIndexedMapNode<K, V1> current() {
                return this.current;
            }

            public void current_$eq(BitmapIndexedMapNode<K, V1> x$1) {
                this.current = x$1;
            }

            public void apply(Tuple2<K, V1> kv) {
                this.apply(kv._1(), kv._2());
            }

            public void apply(K key, V1 value) {
                int originalHash = Statics.anyHash(key);
                int improved = Hashing$.MODULE$.improve(originalHash);
                if (!this.changed()) {
                    this.current_$eq((BitmapIndexedMapNode<K, V1>)this.current().updated((Object)key, (Object)value, originalHash, improved, 0, true));
                    if (this.current() != this.$outer.rootNode()) {
                        this.changed_$eq(true);
                        int maskFrom_shift = 0;
                        int bitposFrom_mask = improved >>> maskFrom_shift & 0x1F;
                        this.shallowlyMutableNodeMap_$eq(1 << bitposFrom_mask);
                        return;
                    }
                    return;
                }
                this.shallowlyMutableNodeMap_$eq(this.current().updateWithShallowMutations(key, value, originalHash, improved, 0, this.shallowlyMutableNodeMap()));
            }

            public Scala_collection_immutable_HashMap$accum$1(HashMap $outer) {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.changed = false;
                this.shallowlyMutableNodeMap = 0;
                this.current = $outer.rootNode();
            }
        }
        if (that instanceof HashMap) {
            HashMap hashMap = (HashMap)that;
            if (this.isEmpty()) {
                return hashMap;
            }
            if (this.rootNode().concat(hashMap.rootNode(), 0) == hashMap.rootNode()) {
                return hashMap;
            }
            MapNode newHashMapOrThis_newRootNode = this.rootNode().concat(hashMap.rootNode(), 0);
            if (newHashMapOrThis_newRootNode == this.rootNode()) {
                return this;
            }
            return new HashMap<K, V>(newHashMapOrThis_newRootNode);
        }
        if (that instanceof scala.collection.mutable.HashMap) {
            scala.collection.mutable.HashMap hashMap = (scala.collection.mutable.HashMap)that;
            Iterator iter = hashMap.nodeIterator();
            MapNode current = this.rootNode();
            while (iter.hasNext()) {
                HashMap.Node next = iter.next();
                int originalHash = hashMap.unimproveHash(next.hash());
                int improved = Hashing$.MODULE$.improve(originalHash);
                if ((current = ((BitmapIndexedMapNode)current).updated(next.key(), next.value(), originalHash, improved, 0, true)) == this.rootNode()) continue;
                int maskFrom_shift = 0;
                int bitposFrom_mask = improved >>> maskFrom_shift & 0x1F;
                int shallowlyMutableNodeMap = 1 << bitposFrom_mask;
                while (iter.hasNext()) {
                    HashMap.Node next2 = iter.next();
                    int originalHash2 = hashMap.unimproveHash(next2.hash());
                    shallowlyMutableNodeMap = ((BitmapIndexedMapNode)current).updateWithShallowMutations(next2.key(), next2.value(), originalHash2, Hashing$.MODULE$.improve(originalHash2), 0, shallowlyMutableNodeMap);
                }
                return new HashMap<K, V>(current);
            }
            return this;
        }
        if (that instanceof scala.collection.immutable.Map) {
            scala.collection.immutable.Map map = (scala.collection.immutable.Map)that;
            if (map.isEmpty()) {
                return this;
            }
            Scala_collection_immutable_HashMap$accum$1 accum2 = new Scala_collection_immutable_HashMap$accum$1(this);
            map.foreachEntry(accum2);
            BitmapIndexedMapNode newHashMapOrThis_newRootNode = accum2.current();
            if (newHashMapOrThis_newRootNode == this.rootNode()) {
                return this;
            }
            return new HashMap(newHashMapOrThis_newRootNode);
        }
        Iterator<Tuple2<K, V1>> it = that.iterator();
        if (it.isEmpty()) {
            return this;
        }
        Scala_collection_immutable_HashMap$accum$1 accum3 = new Scala_collection_immutable_HashMap$accum$1(this);
        it.foreach(accum3);
        BitmapIndexedMapNode newHashMapOrThis_newRootNode = accum3.current();
        if (newHashMapOrThis_newRootNode == this.rootNode()) {
            return this;
        }
        return new HashMap(newHashMapOrThis_newRootNode);
    }

    @Override
    public HashMap<K, V> tail() {
        Object $minus_key = ((Tuple2)this.head())._1();
        return this.removed($minus_key);
    }

    @Override
    public HashMap<K, V> init() {
        Object $minus_key = ((Tuple2)this.last())._1();
        return this.removed($minus_key);
    }

    @Override
    public Tuple2<K, V> head() {
        return this.iterator().next();
    }

    @Override
    public Tuple2<K, V> last() {
        return this.reverseIterator().next();
    }

    @Override
    public <U> void foreach(Function1<Tuple2<K, V>, U> f) {
        BitmapIndexedMapNode<K, V> bitmapIndexedMapNode = this.rootNode();
        if (bitmapIndexedMapNode == null) {
            throw null;
        }
        BitmapIndexedMapNode<K, V> foreach_this = bitmapIndexedMapNode;
        int foreach_iN = foreach_this.payloadArity();
        for (int foreach_i = 0; foreach_i < foreach_iN; ++foreach_i) {
            f.apply((Tuple2<K, V>)foreach_this.getPayload(foreach_i));
        }
        int foreach_jN = foreach_this.nodeArity();
        for (int foreach_j = 0; foreach_j < foreach_jN; ++foreach_j) {
            ((MapNode)foreach_this.getNode(foreach_j)).foreach(f);
        }
    }

    @Override
    public <U> void foreachEntry(Function2<K, V, U> f) {
        BitmapIndexedMapNode<K, V> bitmapIndexedMapNode = this.rootNode();
        if (bitmapIndexedMapNode == null) {
            throw null;
        }
        BitmapIndexedMapNode<K, V> foreachEntry_this = bitmapIndexedMapNode;
        int foreachEntry_iN = foreachEntry_this.payloadArity();
        for (int foreachEntry_i = 0; foreachEntry_i < foreachEntry_iN; ++foreachEntry_i) {
            f.apply(foreachEntry_this.content()[2 * foreachEntry_i], foreachEntry_this.content()[2 * foreachEntry_i + 1]);
        }
        int foreachEntry_jN = foreachEntry_this.nodeArity();
        for (int foreachEntry_j = 0; foreachEntry_j < foreachEntry_jN; ++foreachEntry_j) {
            ((MapNode)foreachEntry_this.getNode(foreachEntry_j)).foreachEntry(f);
        }
    }

    public void foreachWithHash(Function3<K, V, Object, BoxedUnit> f) {
        BitmapIndexedMapNode<K, V> bitmapIndexedMapNode = this.rootNode();
        if (bitmapIndexedMapNode == null) {
            throw null;
        }
        BitmapIndexedMapNode<K, V> foreachWithHash_this = bitmapIndexedMapNode;
        int foreachWithHash_iN = foreachWithHash_this.payloadArity();
        for (int foreachWithHash_i = 0; foreachWithHash_i < foreachWithHash_iN; ++foreachWithHash_i) {
            f.apply(foreachWithHash_this.content()[2 * foreachWithHash_i], foreachWithHash_this.content()[2 * foreachWithHash_i + 1], foreachWithHash_this.originalHashes()[foreachWithHash_i]);
        }
        int foreachWithHash_jN = foreachWithHash_this.nodeArity();
        for (int foreachWithHash_j = 0; foreachWithHash_j < foreachWithHash_jN; ++foreachWithHash_j) {
            ((MapNode)foreachWithHash_this.getNode(foreachWithHash_j)).foreachWithHash(f);
        }
    }

    @Override
    public boolean equals(Object that) {
        block2: {
            block4: {
                block3: {
                    if (!(that instanceof HashMap)) break block2;
                    HashMap hashMap = (HashMap)that;
                    if (this == hashMap) break block3;
                    BitmapIndexedMapNode<K, V> bitmapIndexedMapNode = this.rootNode();
                    BitmapIndexedMapNode<K, V> bitmapIndexedMapNode2 = hashMap.rootNode();
                    if (bitmapIndexedMapNode != null ? !((Object)bitmapIndexedMapNode).equals(bitmapIndexedMapNode2) : bitmapIndexedMapNode2 != null) break block4;
                }
                return true;
            }
            return false;
        }
        return Map.equals$(this, that);
    }

    @Override
    public int hashCode() {
        if (this.isEmpty()) {
            return MurmurHash3$.MODULE$.emptyMapHash();
        }
        MapKeyValueTupleHashIterator<K, V> hashIterator = new MapKeyValueTupleHashIterator<K, V>(this.rootNode());
        return MurmurHash3$.MODULE$.unorderedHash(hashIterator, MurmurHash3$.MODULE$.mapSeed());
    }

    @Override
    public String className() {
        return "HashMap";
    }

    /*
     * WARNING - void declaration
     */
    public <V1> HashMap<K, V1> merged(HashMap<K, V1> that, Function2<Tuple2<K, V>, Tuple2<K, V1>, Tuple2<K, V1>> mergef) {
        void mergeInto_that;
        if (mergef == null) {
            if (that == null) {
                throw null;
            }
            return (HashMap)MapOps.$plus$plus$(that, (IterableOnce)this);
        }
        if (this.isEmpty()) {
            return that;
        }
        if (that.isEmpty()) {
            return this;
        }
        if (this.size() == 1) {
            void var5_5;
            void var4_4;
            Object object = this.rootNode().getPayload(0);
            if (object == null) {
                throw new MatchError((Object)null);
            }
            Object k = ((Tuple2)object)._1();
            Object v = ((Tuple2)object)._2();
            int originalHash = this.rootNode().getHash(0);
            int improved = Hashing$.MODULE$.improve(originalHash);
            if (that.rootNode().containsKey(var4_4, originalHash, improved, 0)) {
                void var11_11;
                void var10_10;
                Tuple2<void, V1> thatPayload = that.rootNode().getTuple(var4_4, originalHash, improved, 0);
                Tuple2<K, V1> tuple2 = mergef.apply((Tuple2<K, V>)object, (Tuple2<K, V1>)thatPayload);
                if (tuple2 == null) {
                    throw new MatchError((Object)null);
                }
                K mergedK = tuple2._1();
                V1 mergedV = tuple2._2();
                int mergedOriginalHash = Statics.anyHash(var10_10);
                int mergedImprovedHash = Hashing$.MODULE$.improve(mergedOriginalHash);
                return new HashMap<K, V>(((BitmapIndexedMapNode)that.rootNode().removed((Object)thatPayload._1(), originalHash, improved, 0)).updated((Object)var10_10, (Object)var11_11, mergedOriginalHash, mergedImprovedHash, 0, true));
            }
            return new HashMap<K, V>(that.rootNode().updated((Object)var4_4, (Object)var5_5, originalHash, improved, 0, true));
        }
        if (that.size() == 0) {
            void var16_16;
            void var15_15;
            Object object = this.rootNode().getPayload(0);
            if (object == null) {
                throw new MatchError((Object)null);
            }
            Object k = ((Tuple2)object)._1();
            Object v = ((Tuple2)object)._2();
            int thatOriginalHash = this.rootNode().getHash(0);
            int thatImproved = Hashing$.MODULE$.improve(thatOriginalHash);
            if (this.rootNode().containsKey(var15_15, thatOriginalHash, thatImproved, 0)) {
                void var22_22;
                void var21_21;
                Tuple2<void, V> payload = this.rootNode().getTuple(var15_15, thatOriginalHash, thatImproved, 0);
                Tuple2<K, V1> tuple2 = mergef.apply((Tuple2<K, V>)payload, (Tuple2<K, V1>)object);
                if (tuple2 == null) {
                    throw new MatchError((Object)null);
                }
                K mergedK = tuple2._1();
                V1 mergedV = tuple2._2();
                int mergedOriginalHash = Statics.anyHash(var21_21);
                int mergedImprovedHash = Hashing$.MODULE$.improve(mergedOriginalHash);
                return new HashMap<K, V>(this.rootNode().updated((Object)var21_21, (Object)var22_22, mergedOriginalHash, mergedImprovedHash, 0, true));
            }
            return new HashMap<K, V>(this.rootNode().updated((Object)var15_15, (Object)var16_16, thatOriginalHash, thatImproved, 0, true));
        }
        HashMapBuilder<Object, Object> builder = new HashMapBuilder<Object, Object>();
        BitmapIndexedMapNode bitmapIndexedMapNode = this.rootNode();
        boolean bl = false;
        BitmapIndexedMapNode<K, V1> bitmapIndexedMapNode2 = that.rootNode();
        if (bitmapIndexedMapNode == null) {
            throw null;
        }
        BitmapIndexedMapNode mergeInto_this = bitmapIndexedMapNode;
        if (mergeInto_that instanceof BitmapIndexedMapNode) {
            if (mergeInto_this.size() == 0) {
                mergeInto_that.buildTo(builder);
            } else if (mergeInto_that.size() == 0) {
                mergeInto_this.buildTo(builder);
            } else {
                int mergeInto_allMap = mergeInto_this.dataMap() | mergeInto_that.dataMap() | mergeInto_this.nodeMap() | mergeInto_that.nodeMap();
                int mergeInto_minIndex = Integer.numberOfTrailingZeros(mergeInto_allMap);
                int mergeInto_maxIndex = 32 - Integer.numberOfLeadingZeros(mergeInto_allMap);
                int mergeInto_leftIdx = 0;
                int mergeInto_rightIdx = 0;
                for (int mergeInto_index = mergeInto_minIndex; mergeInto_index < mergeInto_maxIndex; ++mergeInto_index) {
                    void mergeInto_shift;
                    int mergeInto_bitpos = 1 << mergeInto_index;
                    if ((mergeInto_bitpos & mergeInto_this.dataMap()) != 0) {
                        Object mergeInto_leftKey = mergeInto_this.content()[2 * mergeInto_leftIdx];
                        Object mergeInto_leftValue = mergeInto_this.content()[2 * mergeInto_leftIdx + 1];
                        int mergeInto_leftOriginalHash = mergeInto_this.originalHashes()[mergeInto_leftIdx];
                        if ((mergeInto_bitpos & mergeInto_that.dataMap()) != 0) {
                            Object mergeInto_rightKey = mergeInto_that.content()[2 * mergeInto_rightIdx];
                            Object mergeInto_rightValue = mergeInto_that.content()[2 * mergeInto_rightIdx + 1];
                            int mergeInto_rightOriginalHash = mergeInto_that.originalHashes()[mergeInto_rightIdx];
                            if (mergeInto_leftOriginalHash == mergeInto_rightOriginalHash && BoxesRunTime.equals(mergeInto_leftKey, mergeInto_rightKey)) {
                                builder.addOne(mergef.apply(new Tuple2<Object, Object>(mergeInto_leftKey, mergeInto_leftValue), new Tuple2<Object, Object>(mergeInto_rightKey, mergeInto_rightValue)));
                            } else {
                                builder.addOne(mergeInto_leftKey, mergeInto_leftValue, mergeInto_leftOriginalHash);
                                builder.addOne(mergeInto_rightKey, mergeInto_rightValue, mergeInto_rightOriginalHash);
                            }
                            ++mergeInto_rightIdx;
                        } else if ((mergeInto_bitpos & mergeInto_that.nodeMap()) != 0) {
                            int mergeInto_leftImprovedHash;
                            Node mergeInto_subNode = mergeInto_that.getNode(mergeInto_that.nodeIndex(mergeInto_bitpos));
                            MapNode mergeInto_removed = ((MapNode)mergeInto_subNode).removed(mergeInto_leftKey, mergeInto_leftOriginalHash, mergeInto_leftImprovedHash = Hashing$.MODULE$.improve(mergeInto_leftOriginalHash), (int)(mergeInto_shift + 5));
                            if (mergeInto_removed == mergeInto_subNode) {
                                ((MapNode)mergeInto_subNode).buildTo(builder);
                                builder.addOne(mergeInto_leftKey, mergeInto_leftValue, mergeInto_leftOriginalHash, mergeInto_leftImprovedHash);
                            } else {
                                mergeInto_removed.buildTo(builder);
                                builder.addOne(mergef.apply(new Tuple2<Object, Object>(mergeInto_leftKey, mergeInto_leftValue), ((MapNode)mergeInto_subNode).getTuple(mergeInto_leftKey, mergeInto_leftOriginalHash, mergeInto_leftImprovedHash, (int)(mergeInto_shift + 5))));
                            }
                        } else {
                            builder.addOne(mergeInto_leftKey, mergeInto_leftValue, mergeInto_leftOriginalHash);
                        }
                        ++mergeInto_leftIdx;
                        continue;
                    }
                    if ((mergeInto_bitpos & mergeInto_this.nodeMap()) != 0) {
                        if ((mergeInto_bitpos & mergeInto_that.dataMap()) != 0) {
                            Object mergeInto_rightKey = mergeInto_that.content()[2 * mergeInto_rightIdx];
                            Object mergeInto_rightValue = mergeInto_that.content()[2 * mergeInto_rightIdx + 1];
                            int mergeInto_rightOriginalHash = mergeInto_that.originalHashes()[mergeInto_rightIdx];
                            int mergeInto_rightImprovedHash = Hashing$.MODULE$.improve(mergeInto_rightOriginalHash);
                            Node mergeInto_subNode = mergeInto_this.getNode(mergeInto_this.nodeIndex(mergeInto_bitpos));
                            MapNode mergeInto_removed = ((MapNode)mergeInto_subNode).removed(mergeInto_rightKey, mergeInto_rightOriginalHash, mergeInto_rightImprovedHash, (int)(mergeInto_shift + 5));
                            if (mergeInto_removed == mergeInto_subNode) {
                                ((MapNode)mergeInto_subNode).buildTo(builder);
                                builder.addOne(mergeInto_rightKey, mergeInto_rightValue, mergeInto_rightOriginalHash, mergeInto_rightImprovedHash);
                            } else {
                                mergeInto_removed.buildTo(builder);
                                builder.addOne(mergef.apply(((MapNode)mergeInto_subNode).getTuple(mergeInto_rightKey, mergeInto_rightOriginalHash, mergeInto_rightImprovedHash, (int)(mergeInto_shift + 5)), new Tuple2<Object, Object>(mergeInto_rightKey, mergeInto_rightValue)));
                            }
                            ++mergeInto_rightIdx;
                            continue;
                        }
                        if ((mergeInto_bitpos & mergeInto_that.nodeMap()) != 0) {
                            ((MapNode)mergeInto_this.getNode(mergeInto_this.nodeIndex(mergeInto_bitpos))).mergeInto(mergeInto_that.getNode(mergeInto_that.nodeIndex(mergeInto_bitpos)), builder, (int)(mergeInto_shift + 5), mergef);
                            continue;
                        }
                        ((MapNode)mergeInto_this.getNode(mergeInto_this.nodeIndex(mergeInto_bitpos))).buildTo(builder);
                        continue;
                    }
                    if ((mergeInto_bitpos & mergeInto_that.dataMap()) != 0) {
                        int mergeInto_dataIndex = mergeInto_that.dataIndex(mergeInto_bitpos);
                        builder.addOne(mergeInto_that.content()[2 * mergeInto_dataIndex], mergeInto_that.content()[2 * mergeInto_dataIndex + 1], mergeInto_that.originalHashes()[mergeInto_dataIndex]);
                        ++mergeInto_rightIdx;
                        continue;
                    }
                    if ((mergeInto_bitpos & mergeInto_that.nodeMap()) == 0) continue;
                    ((MapNode)mergeInto_that.getNode(mergeInto_that.nodeIndex(mergeInto_bitpos))).buildTo(builder);
                }
            }
        } else {
            throw new MatchError(mergeInto_that);
        }
        Object var26_28 = null;
        bitmapIndexedMapNode2 = null;
        Object var36_36 = null;
        Object var37_37 = null;
        Object var39_39 = null;
        Object var40_40 = null;
        Object var42_42 = null;
        Object var44_44 = null;
        Object var45_45 = null;
        Object var46_46 = null;
        Object var49_49 = null;
        Object var50_50 = null;
        return builder.result();
    }

    @Override
    public <W> HashMap<K, W> transform(Function2<K, V, W> f) {
        BitmapIndexedMapNode bitmapIndexedMapNode = this.rootNode();
        if (bitmapIndexedMapNode == null) {
            throw null;
        }
        BitmapIndexedMapNode transform_this = bitmapIndexedMapNode;
        Object[] transform_newContent = null;
        int transform_iN = transform_this.payloadArity();
        int transform_jN = transform_this.nodeArity();
        int transform_newContentLength = transform_this.content().length;
        for (int transform_i = 0; transform_i < transform_iN; ++transform_i) {
            Object transform_key = transform_this.content()[2 * transform_i];
            Object transform_value = transform_this.content()[2 * transform_i + 1];
            W transform_newValue = f.apply(transform_key, transform_value);
            if (transform_newContent == null) {
                if (transform_newValue == transform_value) continue;
                transform_newContent = (Object[])transform_this.content().clone();
                transform_newContent[2 * transform_i + 1] = transform_newValue;
                continue;
            }
            transform_newContent[2 * transform_i + 1] = transform_newValue;
        }
        for (int transform_j = 0; transform_j < transform_jN; ++transform_j) {
            Node transform_node = transform_this.getNode(transform_j);
            MapNode<K, W> transform_newNode = ((MapNode)transform_node).transform(f);
            if (transform_newContent == null) {
                if (transform_newNode == transform_node) continue;
                transform_newContent = (Object[])transform_this.content().clone();
                transform_newContent[transform_newContentLength - transform_j - 1] = transform_newNode;
                continue;
            }
            transform_newContent[transform_newContentLength - transform_j - 1] = transform_newNode;
        }
        Object var3_2 = null;
        Object var4_3 = null;
        Object var9_8 = null;
        Object var10_9 = null;
        Object var11_10 = null;
        Object var13_12 = null;
        Object var14_13 = null;
        BitmapIndexedMapNode newHashMapOrThis_newRootNode = transform_newContent == null ? transform_this : new BitmapIndexedMapNode(transform_this.dataMap(), transform_this.nodeMap(), transform_newContent, transform_this.originalHashes(), transform_this.size(), transform_this.cachedJavaKeySetHashCode());
        if (newHashMapOrThis_newRootNode == this.rootNode()) {
            return this;
        }
        return new HashMap(newHashMapOrThis_newRootNode);
    }

    @Override
    public HashMap<K, V> filterImpl(Function1<Tuple2<K, V>, Object> pred, boolean isFlipped) {
        BitmapIndexedMapNode bitmapIndexedMapNode;
        BitmapIndexedMapNode bitmapIndexedMapNode2 = this.rootNode();
        if (bitmapIndexedMapNode2 == null) {
            throw null;
        }
        BitmapIndexedMapNode filterImpl_this = bitmapIndexedMapNode2;
        if (filterImpl_this.size() == 0) {
            bitmapIndexedMapNode = filterImpl_this;
        } else if (filterImpl_this.size() == 1) {
            bitmapIndexedMapNode = BoxesRunTime.unboxToBoolean(pred.apply((Tuple2<K, V>)filterImpl_this.getPayload(0))) != isFlipped ? filterImpl_this : MapNode$.MODULE$.empty();
        } else if (filterImpl_this.nodeMap() == 0) {
            int filterImpl_minimumIndex = Integer.numberOfTrailingZeros(filterImpl_this.dataMap());
            int filterImpl_maximumIndex = 32 - Integer.numberOfLeadingZeros(filterImpl_this.dataMap());
            int filterImpl_newDataMap = 0;
            int filterImpl_newCachedHashCode = 0;
            int filterImpl_dataIndex = 0;
            for (int filterImpl_i = filterImpl_minimumIndex; filterImpl_i < filterImpl_maximumIndex; ++filterImpl_i) {
                int filterImpl_bitpos = 1 << filterImpl_i;
                if ((filterImpl_bitpos & filterImpl_this.dataMap()) == 0) continue;
                Object filterImpl_payload = filterImpl_this.getPayload(filterImpl_dataIndex);
                if (BoxesRunTime.unboxToBoolean(pred.apply((Tuple2<K, V>)filterImpl_payload)) != isFlipped) {
                    filterImpl_newDataMap |= filterImpl_bitpos;
                    filterImpl_newCachedHashCode += Hashing$.MODULE$.improve(filterImpl_this.originalHashes()[filterImpl_dataIndex]);
                }
                ++filterImpl_dataIndex;
            }
            if (filterImpl_newDataMap == 0) {
                bitmapIndexedMapNode = MapNode$.MODULE$.empty();
            } else if (filterImpl_newDataMap == filterImpl_this.dataMap()) {
                bitmapIndexedMapNode = filterImpl_this;
            } else {
                int filterImpl_newSize = Integer.bitCount(filterImpl_newDataMap);
                Object[] filterImpl_newContent = new Object[filterImpl_newSize * 2];
                int[] filterImpl_newOriginalHashCodes = new int[filterImpl_newSize];
                int filterImpl_newMaximumIndex = 32 - Integer.numberOfLeadingZeros(filterImpl_newDataMap);
                int filterImpl_newDataIndex = 0;
                for (int filterImpl_j = Integer.numberOfTrailingZeros(filterImpl_newDataMap); filterImpl_j < filterImpl_newMaximumIndex; ++filterImpl_j) {
                    int filterImpl_bitpos = 1 << filterImpl_j;
                    if ((filterImpl_bitpos & filterImpl_newDataMap) == 0) continue;
                    int filterImpl_oldIndex = Integer.bitCount(filterImpl_this.dataMap() & filterImpl_bitpos - 1);
                    filterImpl_newContent[filterImpl_newDataIndex * 2] = filterImpl_this.content()[filterImpl_oldIndex * 2];
                    filterImpl_newContent[filterImpl_newDataIndex * 2 + 1] = filterImpl_this.content()[filterImpl_oldIndex * 2 + 1];
                    filterImpl_newOriginalHashCodes[filterImpl_newDataIndex] = filterImpl_this.originalHashes()[filterImpl_oldIndex];
                    ++filterImpl_newDataIndex;
                }
                bitmapIndexedMapNode = new BitmapIndexedMapNode(filterImpl_newDataMap, 0, filterImpl_newContent, filterImpl_newOriginalHashCodes, filterImpl_newSize, filterImpl_newCachedHashCode);
            }
        } else {
            int filterImpl_allMap = filterImpl_this.dataMap() | filterImpl_this.nodeMap();
            int filterImpl_minimumIndex = Integer.numberOfTrailingZeros(filterImpl_allMap);
            int filterImpl_maximumIndex = 32 - Integer.numberOfLeadingZeros(filterImpl_allMap);
            int filterImpl_oldDataPassThrough = 0;
            int filterImpl_nodeMigrateToDataTargetMap = 0;
            AbstractBuffer filterImpl_nodesToMigrateToData = null;
            int filterImpl_nodesToPassThroughMap = 0;
            int filterImpl_mapOfNewNodes = 0;
            Object filterImpl_newNodes = null;
            int filterImpl_newDataMap = 0;
            int filterImpl_newNodeMap = 0;
            int filterImpl_newSize = 0;
            int filterImpl_newCachedHashCode = 0;
            int filterImpl_dataIndex = 0;
            int filterImpl_nodeIndex = 0;
            for (int filterImpl_i = filterImpl_minimumIndex; filterImpl_i < filterImpl_maximumIndex; ++filterImpl_i) {
                int filterImpl_bitpos = 1 << filterImpl_i;
                if ((filterImpl_bitpos & filterImpl_this.dataMap()) != 0) {
                    Object filterImpl_payload = filterImpl_this.getPayload(filterImpl_dataIndex);
                    if (BoxesRunTime.unboxToBoolean(pred.apply((Tuple2<K, V>)filterImpl_payload)) != isFlipped) {
                        filterImpl_newDataMap |= filterImpl_bitpos;
                        filterImpl_oldDataPassThrough |= filterImpl_bitpos;
                        ++filterImpl_newSize;
                        filterImpl_newCachedHashCode += Hashing$.MODULE$.improve(filterImpl_this.originalHashes()[filterImpl_dataIndex]);
                    }
                    ++filterImpl_dataIndex;
                    continue;
                }
                if ((filterImpl_bitpos & filterImpl_this.nodeMap()) == 0) continue;
                Node filterImpl_oldSubNode = filterImpl_this.getNode(filterImpl_nodeIndex);
                MapNode<K, V> filterImpl_newSubNode = ((MapNode)filterImpl_oldSubNode).filterImpl(pred, isFlipped);
                filterImpl_newSize += filterImpl_newSubNode.size();
                filterImpl_newCachedHashCode += filterImpl_newSubNode.cachedJavaKeySetHashCode();
                if (filterImpl_newSubNode.size() > 1) {
                    filterImpl_newNodeMap |= filterImpl_bitpos;
                    if (filterImpl_oldSubNode == filterImpl_newSubNode) {
                        filterImpl_nodesToPassThroughMap |= filterImpl_bitpos;
                    } else {
                        filterImpl_mapOfNewNodes |= filterImpl_bitpos;
                        if (filterImpl_newNodes == null) {
                            filterImpl_newNodes = Queue$.MODULE$.empty();
                        }
                        ((AbstractBuffer)filterImpl_newNodes).$plus$eq(filterImpl_newSubNode);
                    }
                } else if (filterImpl_newSubNode.size() == 1) {
                    filterImpl_newDataMap |= filterImpl_bitpos;
                    filterImpl_nodeMigrateToDataTargetMap |= filterImpl_bitpos;
                    if (filterImpl_nodesToMigrateToData == null) {
                        filterImpl_nodesToMigrateToData = (Queue)IterableFactory.apply$(Queue$.MODULE$, Nil$.MODULE$);
                    }
                    filterImpl_nodesToMigrateToData.$plus$eq(filterImpl_newSubNode);
                }
                ++filterImpl_nodeIndex;
            }
            if (filterImpl_newSize == 0) {
                bitmapIndexedMapNode = MapNode$.MODULE$.empty();
            } else if (filterImpl_newSize == filterImpl_this.size()) {
                bitmapIndexedMapNode = filterImpl_this;
            } else {
                int filterImpl_newDataSize = Integer.bitCount(filterImpl_newDataMap);
                int filterImpl_newContentSize = 2 * filterImpl_newDataSize + Integer.bitCount(filterImpl_newNodeMap);
                Object[] filterImpl_newContent = new Object[filterImpl_newContentSize];
                int[] filterImpl_newOriginalHashes = new int[filterImpl_newDataSize];
                int filterImpl_newAllMap = filterImpl_newDataMap | filterImpl_newNodeMap;
                int filterImpl_maxIndex = 32 - Integer.numberOfLeadingZeros(filterImpl_newAllMap);
                int filterImpl_oldDataIndex = 0;
                int filterImpl_oldNodeIndex = 0;
                int filterImpl_newDataIndex = 0;
                int filterImpl_newNodeIndex = 0;
                for (int filterImpl_i = filterImpl_minimumIndex; filterImpl_i < filterImpl_maxIndex; ++filterImpl_i) {
                    int filterImpl_bitpos = 1 << filterImpl_i;
                    if ((filterImpl_bitpos & filterImpl_oldDataPassThrough) != 0) {
                        filterImpl_newContent[filterImpl_newDataIndex * 2] = filterImpl_this.content()[2 * filterImpl_oldDataIndex];
                        filterImpl_newContent[filterImpl_newDataIndex * 2 + 1] = filterImpl_this.content()[2 * filterImpl_oldDataIndex + 1];
                        filterImpl_newOriginalHashes[filterImpl_newDataIndex] = filterImpl_this.originalHashes()[filterImpl_oldDataIndex];
                        ++filterImpl_newDataIndex;
                        ++filterImpl_oldDataIndex;
                        continue;
                    }
                    if ((filterImpl_bitpos & filterImpl_nodesToPassThroughMap) != 0) {
                        filterImpl_newContent[filterImpl_newContentSize - filterImpl_newNodeIndex - 1] = filterImpl_this.getNode(filterImpl_oldNodeIndex);
                        ++filterImpl_newNodeIndex;
                        ++filterImpl_oldNodeIndex;
                        continue;
                    }
                    if ((filterImpl_bitpos & filterImpl_nodeMigrateToDataTargetMap) != 0) {
                        MapNode filterImpl_node = (MapNode)((Queue)filterImpl_nodesToMigrateToData).dequeue();
                        filterImpl_newContent[2 * filterImpl_newDataIndex] = filterImpl_node.getKey(0);
                        filterImpl_newContent[2 * filterImpl_newDataIndex + 1] = filterImpl_node.getValue(0);
                        filterImpl_newOriginalHashes[filterImpl_newDataIndex] = filterImpl_node.getHash(0);
                        ++filterImpl_newDataIndex;
                        ++filterImpl_oldNodeIndex;
                        continue;
                    }
                    if ((filterImpl_bitpos & filterImpl_mapOfNewNodes) != 0) {
                        filterImpl_newContent[filterImpl_newContentSize - filterImpl_newNodeIndex - 1] = ((Queue)filterImpl_newNodes).dequeue();
                        ++filterImpl_newNodeIndex;
                        ++filterImpl_oldNodeIndex;
                        continue;
                    }
                    if ((filterImpl_bitpos & filterImpl_this.dataMap()) != 0) {
                        ++filterImpl_oldDataIndex;
                        continue;
                    }
                    if ((filterImpl_bitpos & filterImpl_this.nodeMap()) == 0) continue;
                    ++filterImpl_oldNodeIndex;
                }
                bitmapIndexedMapNode = new BitmapIndexedMapNode(filterImpl_newDataMap, filterImpl_newNodeMap, filterImpl_newContent, filterImpl_newOriginalHashes, filterImpl_newSize, filterImpl_newCachedHashCode);
            }
        }
        Object var4_3 = null;
        Object var12_11 = null;
        Object var14_13 = null;
        Object var15_14 = null;
        Object var26_25 = null;
        Object var29_28 = null;
        Object var38_37 = null;
        Object var39_38 = null;
        Object var40_39 = null;
        Object var43_42 = null;
        Object var44_43 = null;
        Object var53_52 = null;
        BitmapIndexedMapNode newRootNode = bitmapIndexedMapNode;
        if (newRootNode == this.rootNode()) {
            return this;
        }
        if (newRootNode.size() == 0) {
            return HashMap$.MODULE$.empty();
        }
        return new HashMap<K, V>(newRootNode);
    }

    @Override
    public HashMap<K, V> removedAll(IterableOnce<K> keys2) {
        if (this.isEmpty()) {
            return this;
        }
        if (keys2 instanceof HashSet) {
            HashSet hashSet = (HashSet)keys2;
            if (hashSet.isEmpty()) {
                return this;
            }
            BitmapIndexedMapNode newRootNode = new MapNodeRemoveAllSetNodeIterator(hashSet.rootNode()).removeAll(this.rootNode());
            if (newRootNode == this.rootNode()) {
                return this;
            }
            if (newRootNode.size() <= 0) {
                return HashMap$.MODULE$.empty();
            }
            return new HashMap(newRootNode);
        }
        if (keys2 instanceof scala.collection.mutable.HashSet) {
            scala.collection.mutable.HashSet hashSet = (scala.collection.mutable.HashSet)keys2;
            if (hashSet.isEmpty()) {
                return this;
            }
            HashSet.HashSetIterator iter = new HashSet.HashSetIterator<HashSet.Node<A>>(hashSet){

                public HashSet.Node<A> extract(HashSet.Node<A> nd) {
                    return nd;
                }
            };
            MapNode curr = this.rootNode();
            while (iter.hasNext()) {
                HashSet.Node next = (HashSet.Node)iter.next();
                int unimproveHash_improvedHash = next.hash();
                int originalHash = unimproveHash_improvedHash ^ unimproveHash_improvedHash >>> 16;
                int improved = Hashing$.MODULE$.improve(originalHash);
                if (((BitmapIndexedMapNode)(curr = ((BitmapIndexedMapNode)curr).removed(next.key(), originalHash, improved, 0))).size() != 0) continue;
                return HashMap$.MODULE$.empty();
            }
            if (curr == this.rootNode()) {
                return this;
            }
            return new HashMap<K, V>(curr);
        }
        Iterator<K> iter = keys2.iterator();
        MapNode curr = this.rootNode();
        while (iter.hasNext()) {
            int improved;
            int originalHash;
            K next = iter.next();
            if (((BitmapIndexedMapNode)(curr = ((BitmapIndexedMapNode)curr).removed((Object)next, originalHash = Statics.anyHash(next), improved = Hashing$.MODULE$.improve(originalHash), 0))).size() != 0) continue;
            return HashMap$.MODULE$.empty();
        }
        if (curr == this.rootNode()) {
            return this;
        }
        return new HashMap<K, V>(curr);
    }

    @Override
    public Tuple2<HashMap<K, V>, HashMap<K, V>> partition(Function1<Tuple2<K, V>, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public HashMap<K, V> take(int n) {
        return (HashMap)IterableOps.take$(this, n);
    }

    @Override
    public HashMap<K, V> takeRight(int n) {
        return (HashMap)StrictOptimizedIterableOps.takeRight$(this, n);
    }

    @Override
    public HashMap<K, V> takeWhile(Function1<Tuple2<K, V>, Object> p) {
        return (HashMap)IterableOps.takeWhile$(this, p);
    }

    @Override
    public HashMap<K, V> dropWhile(Function1<Tuple2<K, V>, Object> p) {
        return (HashMap)IterableOps.dropWhile$(this, p);
    }

    @Override
    public HashMap<K, V> dropRight(int n) {
        return (HashMap)StrictOptimizedIterableOps.dropRight$(this, n);
    }

    @Override
    public HashMap<K, V> drop(int n) {
        return (HashMap)IterableOps.drop$(this, n);
    }

    @Override
    public Tuple2<HashMap<K, V>, HashMap<K, V>> span(Function1<Tuple2<K, V>, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    public static final /* synthetic */ int $anonfun$keyStepper$1(MapNode node, int i) {
        return BoxesRunTime.unboxToInt(node.getKey(i));
    }

    public static final /* synthetic */ long $anonfun$keyStepper$2(MapNode node, int i) {
        return BoxesRunTime.unboxToLong(node.getKey(i));
    }

    public static final /* synthetic */ double $anonfun$keyStepper$3(MapNode node, int i) {
        return BoxesRunTime.unboxToDouble(node.getKey(i));
    }

    public static final /* synthetic */ int $anonfun$valueStepper$1(MapNode node, int i) {
        return BoxesRunTime.unboxToInt(node.getValue(i));
    }

    public static final /* synthetic */ long $anonfun$valueStepper$2(MapNode node, int i) {
        return BoxesRunTime.unboxToLong(node.getValue(i));
    }

    public static final /* synthetic */ double $anonfun$valueStepper$3(MapNode node, int i) {
        return BoxesRunTime.unboxToDouble(node.getValue(i));
    }

    public HashMap(BitmapIndexedMapNode<K, V> rootNode) {
        this.rootNode = rootNode;
        Statics.releaseFence();
    }

    public HashMap() {
        this(MapNode$.MODULE$.empty());
    }

    private final class HashKeySet
    extends MapOps.ImmutableKeySet {
        private Set<K> newKeySetOrThis(HashMap<K, ?> newHashMap) {
            if (newHashMap == this.scala$collection$immutable$HashMap$HashKeySet$$$outer()) {
                return this;
            }
            return newHashMap.keySet();
        }

        private Set<K> newKeySetOrThis(BitmapIndexedMapNode<K, ?> newRootNode) {
            if (newRootNode == this.scala$collection$immutable$HashMap$HashKeySet$$$outer().rootNode()) {
                return this;
            }
            return new HashMap(newRootNode).keySet();
        }

        @Override
        public Set<K> incl(K elem) {
            int originalHash = Statics.anyHash(elem);
            int improvedHash = Hashing$.MODULE$.improve(originalHash);
            MapNode newNode = this.scala$collection$immutable$HashMap$HashKeySet$$$outer().rootNode().updated(elem, (Object)null, originalHash, improvedHash, 0, false);
            return this.newKeySetOrThis((BitmapIndexedMapNode)newNode);
        }

        @Override
        public Set<K> excl(K elem) {
            HashMap hashMap = this.scala$collection$immutable$HashMap$HashKeySet$$$outer();
            if (hashMap == null) {
                throw null;
            }
            return this.newKeySetOrThis((HashMap)hashMap.removed(elem));
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public Set<K> filter(Function1<K, Object> pred) {
            BitmapIndexedMapNode bitmapIndexedMapNode;
            HashMap hashMap = this.scala$collection$immutable$HashMap$HashKeySet$$$outer();
            Function1<Tuple2, Object> & Serializable intersect = (Function1<Tuple2, Object> & Serializable)kv -> BoxesRunTime.boxToBoolean(HashKeySet.$anonfun$filter$1(pred, kv));
            if (hashMap == null) {
                throw null;
            }
            HashMap filter_this = hashMap;
            boolean filterImpl_isFlipped = false;
            BitmapIndexedMapNode bitmapIndexedMapNode2 = filter_this.rootNode();
            if (bitmapIndexedMapNode2 == null) {
                throw null;
            }
            BitmapIndexedMapNode filterImpl_filterImpl_this = bitmapIndexedMapNode2;
            if (filterImpl_filterImpl_this.size() == 0) {
                bitmapIndexedMapNode = filterImpl_filterImpl_this;
            } else if (filterImpl_filterImpl_this.size() == 1) {
                Object object = filterImpl_filterImpl_this.getPayload(0);
                bitmapIndexedMapNode = BoxesRunTime.unboxToBoolean(pred.apply(((Tuple2)object)._1())) != filterImpl_isFlipped ? filterImpl_filterImpl_this : MapNode$.MODULE$.empty();
            } else if (filterImpl_filterImpl_this.nodeMap() == 0) {
                int filterImpl_filterImpl_minimumIndex = Integer.numberOfTrailingZeros(filterImpl_filterImpl_this.dataMap());
                int filterImpl_filterImpl_maximumIndex = 32 - Integer.numberOfLeadingZeros(filterImpl_filterImpl_this.dataMap());
                int filterImpl_filterImpl_newDataMap = 0;
                int filterImpl_filterImpl_newCachedHashCode = 0;
                int filterImpl_filterImpl_dataIndex = 0;
                for (int filterImpl_filterImpl_i = filterImpl_filterImpl_minimumIndex; filterImpl_filterImpl_i < filterImpl_filterImpl_maximumIndex; ++filterImpl_filterImpl_i) {
                    int filterImpl_filterImpl_bitpos = 1 << filterImpl_filterImpl_i;
                    if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_this.dataMap()) == 0) continue;
                    Object object = filterImpl_filterImpl_this.getPayload(filterImpl_filterImpl_dataIndex);
                    if (BoxesRunTime.unboxToBoolean(pred.apply(((Tuple2)object)._1())) != filterImpl_isFlipped) {
                        filterImpl_filterImpl_newDataMap |= filterImpl_filterImpl_bitpos;
                        filterImpl_filterImpl_newCachedHashCode += Hashing$.MODULE$.improve(filterImpl_filterImpl_this.originalHashes()[filterImpl_filterImpl_dataIndex]);
                    }
                    ++filterImpl_filterImpl_dataIndex;
                }
                if (filterImpl_filterImpl_newDataMap == 0) {
                    bitmapIndexedMapNode = MapNode$.MODULE$.empty();
                } else if (filterImpl_filterImpl_newDataMap == filterImpl_filterImpl_this.dataMap()) {
                    bitmapIndexedMapNode = filterImpl_filterImpl_this;
                } else {
                    int filterImpl_filterImpl_newSize = Integer.bitCount(filterImpl_filterImpl_newDataMap);
                    Object[] filterImpl_filterImpl_newContent = new Object[filterImpl_filterImpl_newSize * 2];
                    int[] filterImpl_filterImpl_newOriginalHashCodes = new int[filterImpl_filterImpl_newSize];
                    int filterImpl_filterImpl_newMaximumIndex = 32 - Integer.numberOfLeadingZeros(filterImpl_filterImpl_newDataMap);
                    int filterImpl_filterImpl_newDataIndex = 0;
                    for (int filterImpl_filterImpl_j = Integer.numberOfTrailingZeros(filterImpl_filterImpl_newDataMap); filterImpl_filterImpl_j < filterImpl_filterImpl_newMaximumIndex; ++filterImpl_filterImpl_j) {
                        int filterImpl_filterImpl_bitpos = 1 << filterImpl_filterImpl_j;
                        if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_newDataMap) == 0) continue;
                        int filterImpl_filterImpl_oldIndex = Integer.bitCount(filterImpl_filterImpl_this.dataMap() & filterImpl_filterImpl_bitpos - 1);
                        filterImpl_filterImpl_newContent[filterImpl_filterImpl_newDataIndex * 2] = filterImpl_filterImpl_this.content()[filterImpl_filterImpl_oldIndex * 2];
                        filterImpl_filterImpl_newContent[filterImpl_filterImpl_newDataIndex * 2 + 1] = filterImpl_filterImpl_this.content()[filterImpl_filterImpl_oldIndex * 2 + 1];
                        filterImpl_filterImpl_newOriginalHashCodes[filterImpl_filterImpl_newDataIndex] = filterImpl_filterImpl_this.originalHashes()[filterImpl_filterImpl_oldIndex];
                        ++filterImpl_filterImpl_newDataIndex;
                    }
                    bitmapIndexedMapNode = new BitmapIndexedMapNode(filterImpl_filterImpl_newDataMap, 0, filterImpl_filterImpl_newContent, filterImpl_filterImpl_newOriginalHashCodes, filterImpl_filterImpl_newSize, filterImpl_filterImpl_newCachedHashCode);
                }
            } else {
                int filterImpl_filterImpl_allMap = filterImpl_filterImpl_this.dataMap() | filterImpl_filterImpl_this.nodeMap();
                int filterImpl_filterImpl_minimumIndex = Integer.numberOfTrailingZeros(filterImpl_filterImpl_allMap);
                int filterImpl_filterImpl_maximumIndex = 32 - Integer.numberOfLeadingZeros(filterImpl_filterImpl_allMap);
                int filterImpl_filterImpl_oldDataPassThrough = 0;
                int filterImpl_filterImpl_nodeMigrateToDataTargetMap = 0;
                AbstractBuffer filterImpl_filterImpl_nodesToMigrateToData = null;
                int filterImpl_filterImpl_nodesToPassThroughMap = 0;
                int filterImpl_filterImpl_mapOfNewNodes = 0;
                Object filterImpl_filterImpl_newNodes = null;
                int filterImpl_filterImpl_newDataMap = 0;
                int filterImpl_filterImpl_newNodeMap = 0;
                int filterImpl_filterImpl_newSize = 0;
                int filterImpl_filterImpl_newCachedHashCode = 0;
                int filterImpl_filterImpl_dataIndex = 0;
                int filterImpl_filterImpl_nodeIndex = 0;
                for (int filterImpl_filterImpl_i = filterImpl_filterImpl_minimumIndex; filterImpl_filterImpl_i < filterImpl_filterImpl_maximumIndex; ++filterImpl_filterImpl_i) {
                    void filter_pred;
                    int filterImpl_filterImpl_bitpos = 1 << filterImpl_filterImpl_i;
                    if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_this.dataMap()) != 0) {
                        Object object = filterImpl_filterImpl_this.getPayload(filterImpl_filterImpl_dataIndex);
                        if (BoxesRunTime.unboxToBoolean(pred.apply(((Tuple2)object)._1())) != filterImpl_isFlipped) {
                            filterImpl_filterImpl_newDataMap |= filterImpl_filterImpl_bitpos;
                            filterImpl_filterImpl_oldDataPassThrough |= filterImpl_filterImpl_bitpos;
                            ++filterImpl_filterImpl_newSize;
                            filterImpl_filterImpl_newCachedHashCode += Hashing$.MODULE$.improve(filterImpl_filterImpl_this.originalHashes()[filterImpl_filterImpl_dataIndex]);
                        }
                        ++filterImpl_filterImpl_dataIndex;
                        continue;
                    }
                    if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_this.nodeMap()) == 0) continue;
                    Node filterImpl_filterImpl_oldSubNode = filterImpl_filterImpl_this.getNode(filterImpl_filterImpl_nodeIndex);
                    MapNode filterImpl_filterImpl_newSubNode = ((MapNode)filterImpl_filterImpl_oldSubNode).filterImpl(filter_pred, filterImpl_isFlipped);
                    filterImpl_filterImpl_newSize += filterImpl_filterImpl_newSubNode.size();
                    filterImpl_filterImpl_newCachedHashCode += filterImpl_filterImpl_newSubNode.cachedJavaKeySetHashCode();
                    if (filterImpl_filterImpl_newSubNode.size() > 1) {
                        filterImpl_filterImpl_newNodeMap |= filterImpl_filterImpl_bitpos;
                        if (filterImpl_filterImpl_oldSubNode == filterImpl_filterImpl_newSubNode) {
                            filterImpl_filterImpl_nodesToPassThroughMap |= filterImpl_filterImpl_bitpos;
                        } else {
                            filterImpl_filterImpl_mapOfNewNodes |= filterImpl_filterImpl_bitpos;
                            if (filterImpl_filterImpl_newNodes == null) {
                                filterImpl_filterImpl_newNodes = Queue$.MODULE$.empty();
                            }
                            ((AbstractBuffer)filterImpl_filterImpl_newNodes).$plus$eq(filterImpl_filterImpl_newSubNode);
                        }
                    } else if (filterImpl_filterImpl_newSubNode.size() == 1) {
                        filterImpl_filterImpl_newDataMap |= filterImpl_filterImpl_bitpos;
                        filterImpl_filterImpl_nodeMigrateToDataTargetMap |= filterImpl_filterImpl_bitpos;
                        if (filterImpl_filterImpl_nodesToMigrateToData == null) {
                            filterImpl_filterImpl_nodesToMigrateToData = (Queue)IterableFactory.apply$(Queue$.MODULE$, Nil$.MODULE$);
                        }
                        filterImpl_filterImpl_nodesToMigrateToData.$plus$eq(filterImpl_filterImpl_newSubNode);
                    }
                    ++filterImpl_filterImpl_nodeIndex;
                }
                if (filterImpl_filterImpl_newSize == 0) {
                    bitmapIndexedMapNode = MapNode$.MODULE$.empty();
                } else if (filterImpl_filterImpl_newSize == filterImpl_filterImpl_this.size()) {
                    bitmapIndexedMapNode = filterImpl_filterImpl_this;
                } else {
                    int filterImpl_filterImpl_newDataSize = Integer.bitCount(filterImpl_filterImpl_newDataMap);
                    int filterImpl_filterImpl_newContentSize = 2 * filterImpl_filterImpl_newDataSize + Integer.bitCount(filterImpl_filterImpl_newNodeMap);
                    Object[] filterImpl_filterImpl_newContent = new Object[filterImpl_filterImpl_newContentSize];
                    int[] filterImpl_filterImpl_newOriginalHashes = new int[filterImpl_filterImpl_newDataSize];
                    int filterImpl_filterImpl_newAllMap = filterImpl_filterImpl_newDataMap | filterImpl_filterImpl_newNodeMap;
                    int filterImpl_filterImpl_maxIndex = 32 - Integer.numberOfLeadingZeros(filterImpl_filterImpl_newAllMap);
                    int filterImpl_filterImpl_oldDataIndex = 0;
                    int filterImpl_filterImpl_oldNodeIndex = 0;
                    int filterImpl_filterImpl_newDataIndex = 0;
                    int filterImpl_filterImpl_newNodeIndex = 0;
                    for (int filterImpl_filterImpl_i = filterImpl_filterImpl_minimumIndex; filterImpl_filterImpl_i < filterImpl_filterImpl_maxIndex; ++filterImpl_filterImpl_i) {
                        int filterImpl_filterImpl_bitpos = 1 << filterImpl_filterImpl_i;
                        if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_oldDataPassThrough) != 0) {
                            filterImpl_filterImpl_newContent[filterImpl_filterImpl_newDataIndex * 2] = filterImpl_filterImpl_this.content()[2 * filterImpl_filterImpl_oldDataIndex];
                            filterImpl_filterImpl_newContent[filterImpl_filterImpl_newDataIndex * 2 + 1] = filterImpl_filterImpl_this.content()[2 * filterImpl_filterImpl_oldDataIndex + 1];
                            filterImpl_filterImpl_newOriginalHashes[filterImpl_filterImpl_newDataIndex] = filterImpl_filterImpl_this.originalHashes()[filterImpl_filterImpl_oldDataIndex];
                            ++filterImpl_filterImpl_newDataIndex;
                            ++filterImpl_filterImpl_oldDataIndex;
                            continue;
                        }
                        if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_nodesToPassThroughMap) != 0) {
                            filterImpl_filterImpl_newContent[filterImpl_filterImpl_newContentSize - filterImpl_filterImpl_newNodeIndex - 1] = filterImpl_filterImpl_this.getNode(filterImpl_filterImpl_oldNodeIndex);
                            ++filterImpl_filterImpl_newNodeIndex;
                            ++filterImpl_filterImpl_oldNodeIndex;
                            continue;
                        }
                        if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_nodeMigrateToDataTargetMap) != 0) {
                            MapNode filterImpl_filterImpl_node = (MapNode)((Queue)filterImpl_filterImpl_nodesToMigrateToData).dequeue();
                            filterImpl_filterImpl_newContent[2 * filterImpl_filterImpl_newDataIndex] = filterImpl_filterImpl_node.getKey(0);
                            filterImpl_filterImpl_newContent[2 * filterImpl_filterImpl_newDataIndex + 1] = filterImpl_filterImpl_node.getValue(0);
                            filterImpl_filterImpl_newOriginalHashes[filterImpl_filterImpl_newDataIndex] = filterImpl_filterImpl_node.getHash(0);
                            ++filterImpl_filterImpl_newDataIndex;
                            ++filterImpl_filterImpl_oldNodeIndex;
                            continue;
                        }
                        if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_mapOfNewNodes) != 0) {
                            filterImpl_filterImpl_newContent[filterImpl_filterImpl_newContentSize - filterImpl_filterImpl_newNodeIndex - 1] = ((Queue)filterImpl_filterImpl_newNodes).dequeue();
                            ++filterImpl_filterImpl_newNodeIndex;
                            ++filterImpl_filterImpl_oldNodeIndex;
                            continue;
                        }
                        if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_this.dataMap()) != 0) {
                            ++filterImpl_filterImpl_oldDataIndex;
                            continue;
                        }
                        if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_this.nodeMap()) == 0) continue;
                        ++filterImpl_filterImpl_oldNodeIndex;
                    }
                    bitmapIndexedMapNode = new BitmapIndexedMapNode(filterImpl_filterImpl_newDataMap, filterImpl_filterImpl_newNodeMap, filterImpl_filterImpl_newContent, filterImpl_filterImpl_newOriginalHashes, filterImpl_filterImpl_newSize, filterImpl_filterImpl_newCachedHashCode);
                }
            }
            Object var6_5 = null;
            Object var15_17 = null;
            Object var16_18 = null;
            Object var27_29 = null;
            Object var30_32 = null;
            Object var39_41 = null;
            Object var40_42 = null;
            Object var43_45 = null;
            Object var44_46 = null;
            Object var53_55 = null;
            BitmapIndexedMapNode filterImpl_newRootNode = bitmapIndexedMapNode;
            Object var5_56 = null;
            Object var2_3 = null;
            intersect = null;
            return this.newKeySetOrThis((HashMap)(filterImpl_newRootNode == filter_this.rootNode() ? filter_this : (filterImpl_newRootNode.size() == 0 ? HashMap$.MODULE$.empty() : new HashMap(filterImpl_newRootNode))));
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public Set<K> filterNot(Function1<K, Object> pred) {
            BitmapIndexedMapNode bitmapIndexedMapNode;
            HashMap hashMap = this.scala$collection$immutable$HashMap$HashKeySet$$$outer();
            Function1<Tuple2, Object> & Serializable intersect = (Function1<Tuple2, Object> & Serializable)kv -> BoxesRunTime.boxToBoolean(HashKeySet.$anonfun$filterNot$1(pred, kv));
            if (hashMap == null) {
                throw null;
            }
            HashMap filterNot_this = hashMap;
            boolean filterImpl_isFlipped = true;
            BitmapIndexedMapNode bitmapIndexedMapNode2 = filterNot_this.rootNode();
            if (bitmapIndexedMapNode2 == null) {
                throw null;
            }
            BitmapIndexedMapNode filterImpl_filterImpl_this = bitmapIndexedMapNode2;
            if (filterImpl_filterImpl_this.size() == 0) {
                bitmapIndexedMapNode = filterImpl_filterImpl_this;
            } else if (filterImpl_filterImpl_this.size() == 1) {
                Object object = filterImpl_filterImpl_this.getPayload(0);
                bitmapIndexedMapNode = BoxesRunTime.unboxToBoolean(pred.apply(((Tuple2)object)._1())) != filterImpl_isFlipped ? filterImpl_filterImpl_this : MapNode$.MODULE$.empty();
            } else if (filterImpl_filterImpl_this.nodeMap() == 0) {
                int filterImpl_filterImpl_minimumIndex = Integer.numberOfTrailingZeros(filterImpl_filterImpl_this.dataMap());
                int filterImpl_filterImpl_maximumIndex = 32 - Integer.numberOfLeadingZeros(filterImpl_filterImpl_this.dataMap());
                int filterImpl_filterImpl_newDataMap = 0;
                int filterImpl_filterImpl_newCachedHashCode = 0;
                int filterImpl_filterImpl_dataIndex = 0;
                for (int filterImpl_filterImpl_i = filterImpl_filterImpl_minimumIndex; filterImpl_filterImpl_i < filterImpl_filterImpl_maximumIndex; ++filterImpl_filterImpl_i) {
                    int filterImpl_filterImpl_bitpos = 1 << filterImpl_filterImpl_i;
                    if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_this.dataMap()) == 0) continue;
                    Object object = filterImpl_filterImpl_this.getPayload(filterImpl_filterImpl_dataIndex);
                    if (BoxesRunTime.unboxToBoolean(pred.apply(((Tuple2)object)._1())) != filterImpl_isFlipped) {
                        filterImpl_filterImpl_newDataMap |= filterImpl_filterImpl_bitpos;
                        filterImpl_filterImpl_newCachedHashCode += Hashing$.MODULE$.improve(filterImpl_filterImpl_this.originalHashes()[filterImpl_filterImpl_dataIndex]);
                    }
                    ++filterImpl_filterImpl_dataIndex;
                }
                if (filterImpl_filterImpl_newDataMap == 0) {
                    bitmapIndexedMapNode = MapNode$.MODULE$.empty();
                } else if (filterImpl_filterImpl_newDataMap == filterImpl_filterImpl_this.dataMap()) {
                    bitmapIndexedMapNode = filterImpl_filterImpl_this;
                } else {
                    int filterImpl_filterImpl_newSize = Integer.bitCount(filterImpl_filterImpl_newDataMap);
                    Object[] filterImpl_filterImpl_newContent = new Object[filterImpl_filterImpl_newSize * 2];
                    int[] filterImpl_filterImpl_newOriginalHashCodes = new int[filterImpl_filterImpl_newSize];
                    int filterImpl_filterImpl_newMaximumIndex = 32 - Integer.numberOfLeadingZeros(filterImpl_filterImpl_newDataMap);
                    int filterImpl_filterImpl_newDataIndex = 0;
                    for (int filterImpl_filterImpl_j = Integer.numberOfTrailingZeros(filterImpl_filterImpl_newDataMap); filterImpl_filterImpl_j < filterImpl_filterImpl_newMaximumIndex; ++filterImpl_filterImpl_j) {
                        int filterImpl_filterImpl_bitpos = 1 << filterImpl_filterImpl_j;
                        if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_newDataMap) == 0) continue;
                        int filterImpl_filterImpl_oldIndex = Integer.bitCount(filterImpl_filterImpl_this.dataMap() & filterImpl_filterImpl_bitpos - 1);
                        filterImpl_filterImpl_newContent[filterImpl_filterImpl_newDataIndex * 2] = filterImpl_filterImpl_this.content()[filterImpl_filterImpl_oldIndex * 2];
                        filterImpl_filterImpl_newContent[filterImpl_filterImpl_newDataIndex * 2 + 1] = filterImpl_filterImpl_this.content()[filterImpl_filterImpl_oldIndex * 2 + 1];
                        filterImpl_filterImpl_newOriginalHashCodes[filterImpl_filterImpl_newDataIndex] = filterImpl_filterImpl_this.originalHashes()[filterImpl_filterImpl_oldIndex];
                        ++filterImpl_filterImpl_newDataIndex;
                    }
                    bitmapIndexedMapNode = new BitmapIndexedMapNode(filterImpl_filterImpl_newDataMap, 0, filterImpl_filterImpl_newContent, filterImpl_filterImpl_newOriginalHashCodes, filterImpl_filterImpl_newSize, filterImpl_filterImpl_newCachedHashCode);
                }
            } else {
                int filterImpl_filterImpl_allMap = filterImpl_filterImpl_this.dataMap() | filterImpl_filterImpl_this.nodeMap();
                int filterImpl_filterImpl_minimumIndex = Integer.numberOfTrailingZeros(filterImpl_filterImpl_allMap);
                int filterImpl_filterImpl_maximumIndex = 32 - Integer.numberOfLeadingZeros(filterImpl_filterImpl_allMap);
                int filterImpl_filterImpl_oldDataPassThrough = 0;
                int filterImpl_filterImpl_nodeMigrateToDataTargetMap = 0;
                AbstractBuffer filterImpl_filterImpl_nodesToMigrateToData = null;
                int filterImpl_filterImpl_nodesToPassThroughMap = 0;
                int filterImpl_filterImpl_mapOfNewNodes = 0;
                Object filterImpl_filterImpl_newNodes = null;
                int filterImpl_filterImpl_newDataMap = 0;
                int filterImpl_filterImpl_newNodeMap = 0;
                int filterImpl_filterImpl_newSize = 0;
                int filterImpl_filterImpl_newCachedHashCode = 0;
                int filterImpl_filterImpl_dataIndex = 0;
                int filterImpl_filterImpl_nodeIndex = 0;
                for (int filterImpl_filterImpl_i = filterImpl_filterImpl_minimumIndex; filterImpl_filterImpl_i < filterImpl_filterImpl_maximumIndex; ++filterImpl_filterImpl_i) {
                    void filterNot_pred;
                    int filterImpl_filterImpl_bitpos = 1 << filterImpl_filterImpl_i;
                    if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_this.dataMap()) != 0) {
                        Object object = filterImpl_filterImpl_this.getPayload(filterImpl_filterImpl_dataIndex);
                        if (BoxesRunTime.unboxToBoolean(pred.apply(((Tuple2)object)._1())) != filterImpl_isFlipped) {
                            filterImpl_filterImpl_newDataMap |= filterImpl_filterImpl_bitpos;
                            filterImpl_filterImpl_oldDataPassThrough |= filterImpl_filterImpl_bitpos;
                            ++filterImpl_filterImpl_newSize;
                            filterImpl_filterImpl_newCachedHashCode += Hashing$.MODULE$.improve(filterImpl_filterImpl_this.originalHashes()[filterImpl_filterImpl_dataIndex]);
                        }
                        ++filterImpl_filterImpl_dataIndex;
                        continue;
                    }
                    if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_this.nodeMap()) == 0) continue;
                    Node filterImpl_filterImpl_oldSubNode = filterImpl_filterImpl_this.getNode(filterImpl_filterImpl_nodeIndex);
                    MapNode filterImpl_filterImpl_newSubNode = ((MapNode)filterImpl_filterImpl_oldSubNode).filterImpl(filterNot_pred, filterImpl_isFlipped);
                    filterImpl_filterImpl_newSize += filterImpl_filterImpl_newSubNode.size();
                    filterImpl_filterImpl_newCachedHashCode += filterImpl_filterImpl_newSubNode.cachedJavaKeySetHashCode();
                    if (filterImpl_filterImpl_newSubNode.size() > 1) {
                        filterImpl_filterImpl_newNodeMap |= filterImpl_filterImpl_bitpos;
                        if (filterImpl_filterImpl_oldSubNode == filterImpl_filterImpl_newSubNode) {
                            filterImpl_filterImpl_nodesToPassThroughMap |= filterImpl_filterImpl_bitpos;
                        } else {
                            filterImpl_filterImpl_mapOfNewNodes |= filterImpl_filterImpl_bitpos;
                            if (filterImpl_filterImpl_newNodes == null) {
                                filterImpl_filterImpl_newNodes = Queue$.MODULE$.empty();
                            }
                            ((AbstractBuffer)filterImpl_filterImpl_newNodes).$plus$eq(filterImpl_filterImpl_newSubNode);
                        }
                    } else if (filterImpl_filterImpl_newSubNode.size() == 1) {
                        filterImpl_filterImpl_newDataMap |= filterImpl_filterImpl_bitpos;
                        filterImpl_filterImpl_nodeMigrateToDataTargetMap |= filterImpl_filterImpl_bitpos;
                        if (filterImpl_filterImpl_nodesToMigrateToData == null) {
                            filterImpl_filterImpl_nodesToMigrateToData = (Queue)IterableFactory.apply$(Queue$.MODULE$, Nil$.MODULE$);
                        }
                        filterImpl_filterImpl_nodesToMigrateToData.$plus$eq(filterImpl_filterImpl_newSubNode);
                    }
                    ++filterImpl_filterImpl_nodeIndex;
                }
                if (filterImpl_filterImpl_newSize == 0) {
                    bitmapIndexedMapNode = MapNode$.MODULE$.empty();
                } else if (filterImpl_filterImpl_newSize == filterImpl_filterImpl_this.size()) {
                    bitmapIndexedMapNode = filterImpl_filterImpl_this;
                } else {
                    int filterImpl_filterImpl_newDataSize = Integer.bitCount(filterImpl_filterImpl_newDataMap);
                    int filterImpl_filterImpl_newContentSize = 2 * filterImpl_filterImpl_newDataSize + Integer.bitCount(filterImpl_filterImpl_newNodeMap);
                    Object[] filterImpl_filterImpl_newContent = new Object[filterImpl_filterImpl_newContentSize];
                    int[] filterImpl_filterImpl_newOriginalHashes = new int[filterImpl_filterImpl_newDataSize];
                    int filterImpl_filterImpl_newAllMap = filterImpl_filterImpl_newDataMap | filterImpl_filterImpl_newNodeMap;
                    int filterImpl_filterImpl_maxIndex = 32 - Integer.numberOfLeadingZeros(filterImpl_filterImpl_newAllMap);
                    int filterImpl_filterImpl_oldDataIndex = 0;
                    int filterImpl_filterImpl_oldNodeIndex = 0;
                    int filterImpl_filterImpl_newDataIndex = 0;
                    int filterImpl_filterImpl_newNodeIndex = 0;
                    for (int filterImpl_filterImpl_i = filterImpl_filterImpl_minimumIndex; filterImpl_filterImpl_i < filterImpl_filterImpl_maxIndex; ++filterImpl_filterImpl_i) {
                        int filterImpl_filterImpl_bitpos = 1 << filterImpl_filterImpl_i;
                        if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_oldDataPassThrough) != 0) {
                            filterImpl_filterImpl_newContent[filterImpl_filterImpl_newDataIndex * 2] = filterImpl_filterImpl_this.content()[2 * filterImpl_filterImpl_oldDataIndex];
                            filterImpl_filterImpl_newContent[filterImpl_filterImpl_newDataIndex * 2 + 1] = filterImpl_filterImpl_this.content()[2 * filterImpl_filterImpl_oldDataIndex + 1];
                            filterImpl_filterImpl_newOriginalHashes[filterImpl_filterImpl_newDataIndex] = filterImpl_filterImpl_this.originalHashes()[filterImpl_filterImpl_oldDataIndex];
                            ++filterImpl_filterImpl_newDataIndex;
                            ++filterImpl_filterImpl_oldDataIndex;
                            continue;
                        }
                        if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_nodesToPassThroughMap) != 0) {
                            filterImpl_filterImpl_newContent[filterImpl_filterImpl_newContentSize - filterImpl_filterImpl_newNodeIndex - 1] = filterImpl_filterImpl_this.getNode(filterImpl_filterImpl_oldNodeIndex);
                            ++filterImpl_filterImpl_newNodeIndex;
                            ++filterImpl_filterImpl_oldNodeIndex;
                            continue;
                        }
                        if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_nodeMigrateToDataTargetMap) != 0) {
                            MapNode filterImpl_filterImpl_node = (MapNode)((Queue)filterImpl_filterImpl_nodesToMigrateToData).dequeue();
                            filterImpl_filterImpl_newContent[2 * filterImpl_filterImpl_newDataIndex] = filterImpl_filterImpl_node.getKey(0);
                            filterImpl_filterImpl_newContent[2 * filterImpl_filterImpl_newDataIndex + 1] = filterImpl_filterImpl_node.getValue(0);
                            filterImpl_filterImpl_newOriginalHashes[filterImpl_filterImpl_newDataIndex] = filterImpl_filterImpl_node.getHash(0);
                            ++filterImpl_filterImpl_newDataIndex;
                            ++filterImpl_filterImpl_oldNodeIndex;
                            continue;
                        }
                        if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_mapOfNewNodes) != 0) {
                            filterImpl_filterImpl_newContent[filterImpl_filterImpl_newContentSize - filterImpl_filterImpl_newNodeIndex - 1] = ((Queue)filterImpl_filterImpl_newNodes).dequeue();
                            ++filterImpl_filterImpl_newNodeIndex;
                            ++filterImpl_filterImpl_oldNodeIndex;
                            continue;
                        }
                        if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_this.dataMap()) != 0) {
                            ++filterImpl_filterImpl_oldDataIndex;
                            continue;
                        }
                        if ((filterImpl_filterImpl_bitpos & filterImpl_filterImpl_this.nodeMap()) == 0) continue;
                        ++filterImpl_filterImpl_oldNodeIndex;
                    }
                    bitmapIndexedMapNode = new BitmapIndexedMapNode(filterImpl_filterImpl_newDataMap, filterImpl_filterImpl_newNodeMap, filterImpl_filterImpl_newContent, filterImpl_filterImpl_newOriginalHashes, filterImpl_filterImpl_newSize, filterImpl_filterImpl_newCachedHashCode);
                }
            }
            Object var6_5 = null;
            Object var15_17 = null;
            Object var16_18 = null;
            Object var27_29 = null;
            Object var30_32 = null;
            Object var39_41 = null;
            Object var40_42 = null;
            Object var43_45 = null;
            Object var44_46 = null;
            Object var53_55 = null;
            BitmapIndexedMapNode filterImpl_newRootNode = bitmapIndexedMapNode;
            Object var5_56 = null;
            Object var2_3 = null;
            intersect = null;
            return this.newKeySetOrThis((HashMap)(filterImpl_newRootNode == filterNot_this.rootNode() ? filterNot_this : (filterImpl_newRootNode.size() == 0 ? HashMap$.MODULE$.empty() : new HashMap(filterImpl_newRootNode))));
        }

        public /* synthetic */ HashMap scala$collection$immutable$HashMap$HashKeySet$$$outer() {
            return (HashMap)this.$outer;
        }

        public static final /* synthetic */ boolean $anonfun$filter$1(Function1 pred$1, Tuple2 kv) {
            return BoxesRunTime.unboxToBoolean(pred$1.apply(kv._1()));
        }

        public static final /* synthetic */ boolean $anonfun$filterNot$1(Function1 pred$2, Tuple2 kv) {
            return BoxesRunTime.unboxToBoolean(pred$2.apply(kv._1()));
        }

        public HashKeySet(HashMap $outer) {
            super($outer);
        }
    }
}

