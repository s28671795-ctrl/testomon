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
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.EvidenceIterableFactory;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Set;
import scala.collection.SortedOps;
import scala.collection.SortedSetFactoryDefaults;
import scala.collection.SortedSetOps;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShape$;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.View;
import scala.collection.convert.impl.AnyBinaryTreeStepper;
import scala.collection.convert.impl.BinaryTreeStepper$;
import scala.collection.convert.impl.DoubleBinaryTreeStepper;
import scala.collection.convert.impl.IntBinaryTreeStepper;
import scala.collection.convert.impl.LongBinaryTreeStepper;
import scala.collection.generic.DefaultSerializable;
import scala.collection.immutable.AbstractSet;
import scala.collection.immutable.RedBlackTree;
import scala.collection.immutable.RedBlackTree$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.SetOps;
import scala.collection.immutable.SortedSet;
import scala.collection.immutable.StrictOptimizedSortedSetOps;
import scala.collection.immutable.TreeMap;
import scala.collection.immutable.TreeSet$;
import scala.collection.immutable.TreeSet$sub$1$;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.ReusableBuilder;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.LazyRef;
import scala.runtime.Nothing$;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\rmc\u0001B\u001f?\u0005\u0015C\u0011b\u001c\u0001\u0003\u0006\u0004%\tA\u00109\t\u0011a\u0004!\u0011!Q\u0001\nED\u0001\"\u001f\u0001\u0003\u0006\u0004%\u0019A\u001f\u0005\n\u0003\u000b\u0001!\u0011!Q\u0001\nmD\u0001\"a\u0002\u0001\t\u0003q\u0014\u0011\u0002\u0005\b\u0003\u000f\u0001A\u0011AA\t\u0011\u001d\t9\u0002\u0001C!\u00033A\u0001\"a9\u0001A\u0013%\u0011Q\u001d\u0005\b\u0003W\u0004A\u0011IAw\u0011\u001d\t)\u0010\u0001C!\u0003oDq!a@\u0001\t\u0003\u0012\t\u0001C\u0004\u0003\u0004\u0001!\tE!\u0001\t\u000f\t\u0015\u0001\u0001\"\u0011\u0003\b!9!\u0011\u0002\u0001\u0005B\t\u001d\u0001b\u0002B\u0006\u0001\u0011\u0005#Q\u0002\u0005\b\u0005?\u0001A\u0011\tB\u0011\u0011\u001d\u0011i\u0003\u0001C!\u0005_AqA!\u000e\u0001\t\u0003\u00129\u0004C\u0004\u0003<\u0001!\tE!\u0010\t\u000f\t\u0015\u0003\u0001\"\u0011\u0003H!9!1\n\u0001\u0005B\t5\u0003\u0002\u0003B)\u0001\u0001&IAa\u0015\t\u000f\t}\u0003\u0001\"\u0011\u0003b!9!Q\r\u0001\u0005B\t\u001d\u0004b\u0002B6\u0001\u0011\u0005#Q\u000e\u0005\b\u0005o\u0002A\u0011\tB=\u0011\u001d\u0011I\t\u0001C!\u0005\u0017CqAa&\u0001\t\u0003\u0012I\nC\u0004\u0003\u001e\u0002!\tAa(\t\u000f\t\u001d\u0006\u0001\"\u0001\u0003*\"9!q\u0016\u0001\u0005B\tE\u0006b\u0002B|\u0001\u0011\u0005!\u0011 \u0005\b\u0005{\u0004A\u0011\tB\u0000\u0011\u001d\u0019)\u0001\u0001C\u0001\u0007\u000fAqa!\u0004\u0001\t\u0003\u0019y\u0001C\u0004\u0004\u0014\u0001!\ta!\u0006\t\u000f\re\u0001\u0001\"\u0011\u0004\u001c!911\u0005\u0001\u0005B\r\u0015\u0002bBB\u0015\u0001\u0011\u000531\u0006\u0005\b\u0007g\u0001A\u0011IB\u001b\u0011\u001d\u0019I\u0004\u0001C!\u0007wAqaa\u0010\u0001\t\u0003\u001a\t\u0005C\u0004\u0004F\u0001!\tea\u0012\t\u0011\r5\u0003\u0001)C)\u0007\u001f:qaa\u0016?\u0011\u0003\tYB\u0002\u0004>}!\u0005\u0011Q\u0004\u0005\b\u0003\u000fqC\u0011AA\u0016\u0011\u001d\tiC\fC\u0001\u0003_Aq!a\u0010/\t\u0003\t\t\u0005C\u0004\u0002^9\"\t!a\u0018\u0007\r\u0005ed\u0006BA>\u0011-I8G!A!\u0002\u0017\ti)a$\t\u000f\u0005\u001d1\u0007\"\u0001\u0002\u0016\u0016)ao\r\u0001\u0002 \"9qn\rQ!\n\u0005}\u0005bBAQg\u0011\u0005\u00131\u0015\u0005\b\u0003W\u001bD\u0011IAW\u0011\u001d\t)l\rC!\u0003oCq!a04\t\u0003\n\t\rC\u0005\u0002D:\n\t\u0011\"\u0003\u0002F\n9AK]3f'\u0016$(BA A\u0003%IW.\\;uC\ndWM\u0003\u0002B\u0005\u0006Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003\r\u000bQa]2bY\u0006\u001c\u0001!\u0006\u0002G\u001bN9\u0001aR,[?\nL\u0007c\u0001%J\u00176\ta(\u0003\u0002K}\tY\u0011IY:ue\u0006\u001cGoU3u!\taU\n\u0004\u0001\u0005\u000b9\u0003!\u0019A(\u0003\u0003\u0005\u000b\"\u0001\u0015+\u0011\u0005E\u0013V\"\u0001\"\n\u0005M\u0013%a\u0002(pi\"Lgn\u001a\t\u0003#VK!A\u0016\"\u0003\u0007\u0005s\u0017\u0010E\u0002I1.K!!\u0017 \u0003\u0013M{'\u000f^3e'\u0016$\b#\u0002%\\\u0017vs\u0016B\u0001/?\u00051\u0019vN\u001d;fIN+Go\u00149t!\tA\u0005\u0001E\u0002I\u0001-\u0003R\u0001\u00131L;zK!!\u0019 \u00037M#(/[2u\u001fB$\u0018.\\5{K\u0012\u001cvN\u001d;fIN+Go\u00149t!\u0015\u0019GmS/g\u001b\u0005\u0001\u0015BA3A\u0005a\u0019vN\u001d;fIN+GOR1di>\u0014\u0018\u0010R3gCVdGo\u001d\t\u0003\u0011\u001eL!\u0001\u001b \u0003\u0007M+G\u000f\u0005\u0002k[6\t1N\u0003\u0002m\u0001\u00069q-\u001a8fe&\u001c\u0017B\u00018l\u0005M!UMZ1vYR\u001cVM]5bY&T\u0018M\u00197f\u0003\u0011!(/Z3\u0016\u0003E\u0004BA];L):\u0011\u0001j]\u0005\u0003iz\nABU3e\u00052\f7m\u001b+sK\u0016L!A^<\u0003\tQ\u0013X-\u001a\u0006\u0003iz\nQ\u0001\u001e:fK\u0002\n\u0001b\u001c:eKJLgnZ\u000b\u0002wB\u0019Ap`&\u000f\u0005Ek\u0018B\u0001@C\u0003\u001d\u0001\u0018mY6bO\u0016LA!!\u0001\u0002\u0004\tAqJ\u001d3fe&twM\u0003\u0002\u007f\u0005\u0006IqN\u001d3fe&tw\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\t\u0005-\u0011q\u0002\u000b\u0004=\u00065\u0001\"B=\u0006\u0001\bY\b\"B8\u0006\u0001\u0004\tHCAA\n)\rq\u0016Q\u0003\u0005\u0006s\u001a\u0001\u001da_\u0001\u0016g>\u0014H/\u001a3Ji\u0016\u0014\u0018M\u00197f\r\u0006\u001cGo\u001c:z+\t\tY\u0002\u0005\u0002I]M)a&a\b\u0002&A\u0019\u0011+!\t\n\u0007\u0005\r\"I\u0001\u0004B]f\u0014VM\u001a\t\u0005G\u0006\u001dR,C\u0002\u0002*\u0001\u0013QcU8si\u0016$\u0017\n^3sC\ndWMR1di>\u0014\u0018\u0010\u0006\u0002\u0002\u001c\u0005)Q-\u001c9usV!\u0011\u0011GA\u001c)\u0011\t\u0019$!\u000f\u0011\t!\u0003\u0011Q\u0007\t\u0004\u0019\u0006]B!\u0002(1\u0005\u0004y\u0005\"CA\u001ea\u0005\u0005\t9AA\u001f\u0003))g/\u001b3f]\u000e,G%\r\t\u0005y~\f)$\u0001\u0003ge>lW\u0003BA\"\u0003\u0017\"B!!\u0012\u0002TQ!\u0011qIA(!\u0011A\u0005!!\u0013\u0011\u00071\u000bY\u0005\u0002\u0004\u0002NE\u0012\ra\u0014\u0002\u0002\u000b\"1\u00110\ra\u0002\u0003#\u0002B\u0001`@\u0002J!9\u0011QK\u0019A\u0002\u0005]\u0013AA5u!\u0015\u0019\u0017\u0011LA%\u0013\r\tY\u0006\u0011\u0002\r\u0013R,'/\u00192mK>s7-Z\u0001\u000b]\u0016<()^5mI\u0016\u0014X\u0003BA1\u0003c\"B!a\u0019\u0002vAA\u0011QMA6\u0003_\n\u0019(\u0004\u0002\u0002h)\u0019\u0011\u0011\u000e!\u0002\u000f5,H/\u00192mK&!\u0011QNA4\u0005=\u0011V-^:bE2,')^5mI\u0016\u0014\bc\u0001'\u0002r\u0011)aJ\rb\u0001\u001fB!\u0001\nAA8\u0011\u0019I(\u0007q\u0001\u0002xA!Ap`A8\u00059!&/Z3TKR\u0014U/\u001b7eKJ,B!! \u0002\bN)1'a \u0002\nB)!/!!\u0002\u0006&\u0019\u00111Q<\u0003\u0013M+G\u000fS3ma\u0016\u0014\bc\u0001'\u0002\b\u0012)aj\rb\u0001\u001fBA\u0011QMA6\u0003\u000b\u000bY\t\u0005\u0003I\u0001\u0005\u0015\u0005\u0003\u0002?\u0000\u0003\u000bK1!_AI\u0013\r\t\u0019j\u001e\u0002\u0007\u0011\u0016d\u0007/\u001a:\u0015\u0005\u0005]E\u0003BAM\u0003;\u0003R!a'4\u0003\u000bk\u0011A\f\u0005\u0007sV\u0002\u001d!!$\u0011\u000bI,\u0018Q\u0011+\u0002\r\u0005$Gm\u00148f)\u0011\t)+a*\u000e\u0003MBq!!+9\u0001\u0004\t))\u0001\u0003fY\u0016l\u0017AB1eI\u0006cG\u000e\u0006\u0003\u0002&\u0006=\u0006bBAYs\u0001\u0007\u00111W\u0001\u0003qN\u0004RaYA-\u0003\u000b\u000bQa\u00197fCJ$\"!!/\u0011\u0007E\u000bY,C\u0002\u0002>\n\u0013A!\u00168ji\u00061!/Z:vYR$\"!a#\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005\u001d\u0007\u0003BAe\u0003'l!!a3\u000b\t\u00055\u0017qZ\u0001\u0005Y\u0006twM\u0003\u0002\u0002R\u0006!!.\u0019<b\u0013\u0011\t).a3\u0003\r=\u0013'.Z2uQ\u001dq\u0013\u0011\\Ap\u0003C\u00042!UAn\u0013\r\tiN\u0011\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012aA\u0001\r]\u0016<8+\u001a;PeN+GN\u001a\u000b\u0004=\u0006\u001d\bBBAu\u0011\u0001\u0007\u0011/A\u0001u\u0003\u0011\u0019\u0018N_3\u0016\u0005\u0005=\bcA)\u0002r&\u0019\u00111\u001f\"\u0003\u0007%sG/A\u0004jg\u0016k\u0007\u000f^=\u0016\u0005\u0005e\bcA)\u0002|&\u0019\u0011Q \"\u0003\u000f\t{w\u000e\\3b]\u0006!\u0001.Z1e+\u0005Y\u0015\u0001\u00027bgR\fA\u0001^1jYV\ta,\u0001\u0003j]&$\u0018aA7j]V!!q\u0002B\r)\rY%\u0011\u0003\u0005\b\u0005'y\u00019\u0001B\u000b\u0003\ry'\u000f\u001a\t\u0005y~\u00149\u0002E\u0002M\u00053!qAa\u0007\u0010\u0005\u0004\u0011iB\u0001\u0002BcE\u00111\nV\u0001\u0004[\u0006DX\u0003\u0002B\u0012\u0005W!2a\u0013B\u0013\u0011\u001d\u0011\u0019\u0002\u0005a\u0002\u0005O\u0001B\u0001`@\u0003*A\u0019AJa\u000b\u0005\u000f\tm\u0001C1\u0001\u0003\u001e\u0005!AM]8q)\rq&\u0011\u0007\u0005\b\u0005g\t\u0002\u0019AAx\u0003\u0005q\u0017\u0001\u0002;bW\u0016$2A\u0018B\u001d\u0011\u001d\u0011\u0019D\u0005a\u0001\u0003_\fQa\u001d7jG\u0016$RA\u0018B \u0005\u0003Bq!a\u0010\u0014\u0001\u0004\ty\u000fC\u0004\u0003DM\u0001\r!a<\u0002\u000bUtG/\u001b7\u0002\u0013\u0011\u0014x\u000e\u001d*jO\"$Hc\u00010\u0003J!9!1\u0007\u000bA\u0002\u0005=\u0018!\u0003;bW\u0016\u0014\u0016n\u001a5u)\rq&q\n\u0005\b\u0005g)\u0002\u0019AAx\u0003)\u0019w.\u001e8u/\"LG.\u001a\u000b\u0005\u0003_\u0014)\u0006C\u0004\u0003XY\u0001\rA!\u0017\u0002\u0003A\u0004b!\u0015B.\u0017\u0006e\u0018b\u0001B/\u0005\nIa)\u001e8di&|g.M\u0001\nIJ|\u0007o\u00165jY\u0016$2A\u0018B2\u0011\u001d\u00119f\u0006a\u0001\u00053\n\u0011\u0002^1lK^C\u0017\u000e\\3\u0015\u0007y\u0013I\u0007C\u0004\u0003Xa\u0001\rA!\u0017\u0002\tM\u0004\u0018M\u001c\u000b\u0005\u0005_\u0012)\bE\u0003R\u0005crf,C\u0002\u0003t\t\u0013a\u0001V;qY\u0016\u0014\u0004b\u0002B,3\u0001\u0007!\u0011L\u0001\bM>\u0014X-Y2i+\u0011\u0011YH!\"\u0015\t\u0005e&Q\u0010\u0005\b\u0005\u007fR\u0002\u0019\u0001BA\u0003\u00051\u0007CB)\u0003\\-\u0013\u0019\tE\u0002M\u0005\u000b#aAa\"\u001b\u0005\u0004y%!A+\u0002\u00115Lg.\u00114uKJ$BA!$\u0003\u0014B!\u0011Ka$L\u0013\r\u0011\tJ\u0011\u0002\u0007\u001fB$\u0018n\u001c8\t\r\tU5\u00041\u0001L\u0003\rYW-_\u0001\n[\u0006D()\u001a4pe\u0016$BA!$\u0003\u001c\"1!Q\u0013\u000fA\u0002-\u000b\u0001\"\u001b;fe\u0006$xN]\u000b\u0003\u0005C\u0003Ba\u0019BR\u0017&\u0019!Q\u0015!\u0003\u0011%#XM]1u_J\fA\"\u001b;fe\u0006$xN\u001d$s_6$BA!)\u0003,\"1!Q\u0016\u0010A\u0002-\u000bQa\u001d;beR\fqa\u001d;faB,'/\u0006\u0003\u00034\nuF\u0003\u0002B[\u0005[\u0014bAa.\u0003<\nEgA\u0002B]\u0001\u0001\u0011)L\u0001\u0007=e\u00164\u0017N\\3nK:$h\bE\u0002M\u0005{#qAa0 \u0005\u0004\u0011\tMA\u0001T#\r\u0001&1\u0019\u0019\u0005\u0005\u000b\u0014i\rE\u0003d\u0005\u000f\u0014Y-C\u0002\u0003J\u0002\u0013qa\u0015;faB,'\u000fE\u0002M\u0005\u001b$1Ba4\u0003>\u0006\u0005\t\u0011!B\u0001\u001f\n\u0019q\fJ\u0019\u0011\t\tM'q\u001d\b\u0005\u0005+\u0014\u0019O\u0004\u0003\u0003X\n\u0005h\u0002\u0002Bm\u0005?l!Aa7\u000b\u0007\tuG)\u0001\u0004=e>|GOP\u0005\u0002\u0007&\u0011\u0011IQ\u0005\u0004\u0005K\u0004\u0015aB*uKB\u0004XM]\u0005\u0005\u0005S\u0014YO\u0001\bFM\u001aL7-[3oiN\u0003H.\u001b;\u000b\u0007\t\u0015\b\tC\u0004\u0003p~\u0001\u001dA!=\u0002\u000bMD\u0017\r]3\u0011\r\r\u0014\u0019p\u0013B^\u0013\r\u0011)\u0010\u0011\u0002\r'R,\u0007\u000f]3s'\"\f\u0007/Z\u0001\tG>tG/Y5ogR!\u0011\u0011 B~\u0011\u0019\tI\u000b\ta\u0001\u0017\u0006)!/\u00198hKR)al!\u0001\u0004\u0004!1\u0011qH\u0011A\u0002-CaAa\u0011\"\u0001\u0004Y\u0015!\u0003:b]\u001e,\u0017*\u001c9m)\u0015q6\u0011BB\u0006\u0011\u001d\tyD\ta\u0001\u0005\u001bCqAa\u0011#\u0001\u0004\u0011i)\u0001\u0003j]\u000edGc\u00010\u0004\u0012!1\u0011\u0011V\u0012A\u0002-\u000bA!\u001a=dYR\u0019ala\u0006\t\r\u0005%F\u00051\u0001L\u0003\u0019\u0019wN\\2biR\u0019al!\b\t\u000f\r}Q\u00051\u0001\u0004\"\u0005!A\u000f[1u!\u0011\u0019\u0017\u0011L&\u0002\u0015I,Wn\u001c<fI\u0006cG\u000eF\u0002_\u0007OAqaa\b'\u0001\u0004\u0019\t#A\u0005j]R,'o]3diR\u0019al!\f\t\u000f\r}q\u00051\u0001\u00040A!1m!\rL\u0013\tA\u0007)\u0001\u0003eS\u001a4Gc\u00010\u00048!91q\u0004\u0015A\u0002\r=\u0012A\u00024jYR,'\u000fF\u0002_\u0007{AqAa *\u0001\u0004\u0011I&A\u0005qCJ$\u0018\u000e^5p]R!!qNB\"\u0011\u001d\u00119F\u000ba\u0001\u00053\na!Z9vC2\u001cH\u0003BA}\u0007\u0013Baaa\u0013,\u0001\u0004!\u0016aA8cU\u0006I1\r\\1tg:\u000bW.Z\u000b\u0003\u0007#\u0002B!!3\u0004T%!1QKAf\u0005\u0019\u0019FO]5oO\u00069AK]3f'\u0016$\bfB\u0017\u0002Z\u0006}\u0017\u0011\u001d")
public final class TreeSet<A>
extends AbstractSet<A>
implements SortedSet<A>,
StrictOptimizedSortedSetOps<A, TreeSet, TreeSet<A>>,
DefaultSerializable {
    private final RedBlackTree.Tree<A, Object> tree;
    private final Ordering<A> ordering;

    public static <A> ReusableBuilder<A, TreeSet<A>> newBuilder(Ordering<A> ordering) {
        return new TreeSetBuilder<A>(ordering);
    }

    public static Factory evidenceIterableFactory(Object evidence$13) {
        return EvidenceIterableFactory.evidenceIterableFactory$(TreeSet$.MODULE$, evidence$13);
    }

    public static Object unfold(Object init2, Function1 f, Object evidence$11) {
        TreeSet$ unfold_this = TreeSet$.MODULE$;
        View.Unfold from_it = new View.Unfold(init2, f);
        return unfold_this.from(from_it, (Ordering)evidence$11);
    }

    public static Object iterate(Object start2, int len, Function1 f, Object evidence$10) {
        TreeSet$ iterate_this = TreeSet$.MODULE$;
        View.Iterate<Object> from_it = new View.Iterate<Object>(start2, len, f);
        return iterate_this.from(from_it, (Ordering)evidence$10);
    }

    public static Object tabulate(int n, Function1 f, Object evidence$9) {
        TreeSet$ tabulate_this = TreeSet$.MODULE$;
        View.Tabulate from_it = new View.Tabulate(n, f);
        return tabulate_this.from(from_it, (Ordering)evidence$9);
    }

    public static Object fill(int n, Function0 elem, Object evidence$8) {
        TreeSet$ fill_this = TreeSet$.MODULE$;
        View.Fill from_it = new View.Fill(n, elem);
        return fill_this.from(from_it, (Ordering)evidence$8);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
    }

    @Override
    public scala.collection.SortedSet map(Function1 f, Ordering ev) {
        return scala.collection.StrictOptimizedSortedSetOps.map$(this, f, ev);
    }

    @Override
    public scala.collection.SortedSet flatMap(Function1 f, Ordering ev) {
        return scala.collection.StrictOptimizedSortedSetOps.flatMap$(this, f, ev);
    }

    @Override
    public scala.collection.SortedSet zip(IterableOnce that, Ordering ev) {
        return scala.collection.StrictOptimizedSortedSetOps.zip$(this, that, ev);
    }

    @Override
    public scala.collection.SortedSet collect(PartialFunction pf, Ordering ev) {
        return scala.collection.StrictOptimizedSortedSetOps.collect$(this, pf, ev);
    }

    @Override
    public <A1, A2> Tuple2<scala.collection.immutable.Set<A1>, scala.collection.immutable.Set<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<scala.collection.immutable.Set<A1>, scala.collection.immutable.Set<A2>, scala.collection.immutable.Set<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
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
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
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
    public Object filterNot(Function1 pred) {
        return StrictOptimizedIterableOps.filterNot$(this, pred);
    }

    @Override
    public Object filterImpl(Function1 pred, boolean isFlipped) {
        return StrictOptimizedIterableOps.filterImpl$(this, pred, isFlipped);
    }

    @Override
    public <A1, A2> Tuple2<scala.collection.immutable.Set<A1>, scala.collection.immutable.Set<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
        return StrictOptimizedIterableOps.partitionMap$(this, f);
    }

    @Override
    public Object tapEach(Function1 f) {
        return StrictOptimizedIterableOps.tapEach$(this, f);
    }

    @Override
    public scala.collection.immutable.Set<A> unsorted() {
        return SortedSet.unsorted$(this);
    }

    @Override
    public /* synthetic */ boolean scala$collection$SortedSet$$super$equals(Object that) {
        return Set.equals$(this, that);
    }

    @Override
    public String stringPrefix() {
        return scala.collection.SortedSet.stringPrefix$(this);
    }

    @Override
    public scala.collection.SortedSet fromSpecific(IterableOnce coll) {
        return SortedSetFactoryDefaults.fromSpecific$(this, coll);
    }

    @Override
    public Builder<A, TreeSet<A>> newSpecificBuilder() {
        return SortedSetFactoryDefaults.newSpecificBuilder$(this);
    }

    @Override
    public scala.collection.SortedSet empty() {
        return SortedSetFactoryDefaults.empty$(this);
    }

    @Override
    public SortedSetOps.WithFilter<A, ?, ?> withFilter(Function1<A, Object> p) {
        return SortedSetFactoryDefaults.withFilter$(this, p);
    }

    @Override
    public /* synthetic */ Object scala$collection$SortedSetOps$$super$min(Ordering ord) {
        return IterableOnceOps.min$(this, ord);
    }

    @Override
    public /* synthetic */ Object scala$collection$SortedSetOps$$super$max(Ordering ord) {
        return IterableOnceOps.max$(this, ord);
    }

    @Override
    public Iterator<A> keysIteratorFrom(A start2) {
        return SortedSetOps.keysIteratorFrom$(this, start2);
    }

    @Override
    public A firstKey() {
        return (A)SortedSetOps.firstKey$(this);
    }

    @Override
    public A lastKey() {
        return (A)SortedSetOps.lastKey$(this);
    }

    @Override
    public SortedSetOps rangeTo(Object to) {
        return SortedSetOps.rangeTo$(this, to);
    }

    @Override
    public int compare(A k0, A k1) {
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

    public RedBlackTree.Tree<A, Object> tree() {
        return this.tree;
    }

    @Override
    public Ordering<A> ordering() {
        return this.ordering;
    }

    public TreeSet$ sortedIterableFactory() {
        return TreeSet$.MODULE$;
    }

    private TreeSet<A> newSetOrSelf(RedBlackTree.Tree<A, Object> t2) {
        if (t2 == this.tree()) {
            return this;
        }
        return new TreeSet<A>(t2, this.ordering());
    }

    @Override
    public int size() {
        return RedBlackTree$.MODULE$.count(this.tree());
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public A head() {
        RedBlackTree.Tree<A, Object> tree = RedBlackTree$.MODULE$.smallest(this.tree());
        if (tree == null) {
            throw null;
        }
        return tree.scala$collection$immutable$RedBlackTree$Tree$$_key;
    }

    @Override
    public A last() {
        RedBlackTree.Tree<A, Object> tree = RedBlackTree$.MODULE$.greatest(this.tree());
        if (tree == null) {
            throw null;
        }
        return tree.scala$collection$immutable$RedBlackTree$Tree$$_key;
    }

    @Override
    public TreeSet<A> tail() {
        return new TreeSet<A>(RedBlackTree$.MODULE$.tail(this.tree()), this.ordering());
    }

    @Override
    public TreeSet<A> init() {
        return new TreeSet<A>(RedBlackTree$.MODULE$.init(this.tree()), this.ordering());
    }

    @Override
    public <A1> A min(Ordering<A1> ord) {
        if (ord == this.ordering() && IterableOnceOps.nonEmpty$(this)) {
            return this.head();
        }
        return (A)SortedSetOps.min$(this, ord);
    }

    @Override
    public <A1> A max(Ordering<A1> ord) {
        if (ord == this.ordering() && IterableOnceOps.nonEmpty$(this)) {
            return this.last();
        }
        return (A)SortedSetOps.max$(this, ord);
    }

    @Override
    public TreeSet<A> drop(int n) {
        if (n <= 0) {
            return this;
        }
        if (n >= this.size()) {
            return (TreeSet)SortedSetFactoryDefaults.empty$(this);
        }
        return new TreeSet<A>(RedBlackTree$.MODULE$.drop(this.tree(), n, this.ordering()), this.ordering());
    }

    @Override
    public TreeSet<A> take(int n) {
        if (n <= 0) {
            return (TreeSet)SortedSetFactoryDefaults.empty$(this);
        }
        if (n >= this.size()) {
            return this;
        }
        return new TreeSet<A>(RedBlackTree$.MODULE$.take(this.tree(), n, this.ordering()), this.ordering());
    }

    @Override
    public TreeSet<A> slice(int from, int until) {
        if (until <= from) {
            return (TreeSet)SortedSetFactoryDefaults.empty$(this);
        }
        if (from <= 0) {
            return this.take(until);
        }
        if (until >= this.size()) {
            return this.drop(from);
        }
        return new TreeSet<A>(RedBlackTree$.MODULE$.slice(this.tree(), from, until, this.ordering()), this.ordering());
    }

    @Override
    public TreeSet<A> dropRight(int n) {
        int max_y = 0;
        return this.take(this.size() - Math.max(n, max_y));
    }

    @Override
    public TreeSet<A> takeRight(int n) {
        int max_y = 0;
        return this.drop(this.size() - Math.max(n, max_y));
    }

    private int countWhile(Function1<A, Object> p) {
        int result = 0;
        Iterator<A> it = this.iterator();
        while (it.hasNext() && BoxesRunTime.unboxToBoolean(p.apply(it.next()))) {
            ++result;
        }
        return result;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public TreeSet<A> dropWhile(Function1<A, Object> p) {
        void var2_2;
        int countWhile_result = 0;
        Iterator<A> countWhile_it = this.iterator();
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
    public TreeSet<A> takeWhile(Function1<A, Object> p) {
        void var2_2;
        int countWhile_result = 0;
        Iterator<A> countWhile_it = this.iterator();
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
    public Tuple2<TreeSet<A>, TreeSet<A>> span(Function1<A, Object> p) {
        void var2_2;
        int countWhile_result = 0;
        Iterator<A> countWhile_it = this.iterator();
        while (countWhile_it.hasNext() && BoxesRunTime.unboxToBoolean(p.apply(countWhile_it.next()))) {
            ++countWhile_result;
        }
        Object var3_3 = null;
        return IterableOps.splitAt$(this, (int)var2_2);
    }

    @Override
    public <U> void foreach(Function1<A, U> f) {
        RedBlackTree$.MODULE$.foreachKey(this.tree(), f);
    }

    @Override
    public Option<A> minAfter(A key) {
        RedBlackTree.Tree<A, Object> v = RedBlackTree$.MODULE$.minAfter(this.tree(), key, this.ordering());
        if (v == null) {
            return None$.MODULE$;
        }
        return new Some(v.scala$collection$immutable$RedBlackTree$Tree$$_key);
    }

    @Override
    public Option<A> maxBefore(A key) {
        RedBlackTree.Tree<A, Object> v = RedBlackTree$.MODULE$.maxBefore(this.tree(), key, this.ordering());
        if (v == null) {
            return None$.MODULE$;
        }
        return new Some(v.scala$collection$immutable$RedBlackTree$Tree$$_key);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Iterator<A> iterator() {
        void keysIterator_evidence$17;
        Ordering<A> ordering = this.ordering();
        None$ keysIterator_start = None$.MODULE$;
        RedBlackTree.Tree<A, Object> keysIterator_tree = this.tree();
        return new RedBlackTree.KeysIterator<Nothing$, Object>((RedBlackTree.Tree<Nothing$, Object>)keysIterator_tree, keysIterator_start, (Ordering<Nothing$>)keysIterator_evidence$17);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Iterator<A> iteratorFrom(A start2) {
        void keysIterator_evidence$17;
        Ordering<A> ordering = this.ordering();
        Some<A> keysIterator_start = new Some<A>(start2);
        RedBlackTree.Tree<A, Object> keysIterator_tree = this.tree();
        return new RedBlackTree.KeysIterator<A, Object>(keysIterator_tree, keysIterator_start, keysIterator_evidence$17);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
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
            Function1<RedBlackTree.Tree, Object> & Serializable intersect = (Function1<RedBlackTree.Tree, Object> & Serializable)x$3 -> BoxesRunTime.boxToInteger(TreeSet.$anonfun$stepper$3(x$3));
            Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect2 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$2 -> x$2.right();
            Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect3 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$1 -> x$1.left();
            RedBlackTree.Tree<A, Object> tree = this.tree();
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
            Function1<RedBlackTree.Tree, Object> & Serializable intersect = (Function1<RedBlackTree.Tree, Object> & Serializable)x$6 -> BoxesRunTime.boxToLong(TreeSet.$anonfun$stepper$6(x$6));
            Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect4 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$5 -> x$5.right();
            Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect5 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$4 -> x$4.left();
            RedBlackTree.Tree<A, Object> tree = this.tree();
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
            Function1<RedBlackTree.Tree, Object> & Serializable intersect = (Function1<RedBlackTree.Tree, Object> & Serializable)x$9 -> BoxesRunTime.boxToDouble(TreeSet.$anonfun$stepper$9(x$9));
            Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect6 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$8 -> x$8.right();
            Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect7 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$7 -> x$7.left();
            RedBlackTree.Tree<A, Object> tree = this.tree();
            int from_maxLength = this.size();
            DoubleBinaryTreeStepper<Object> from_ans = new DoubleBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left4, (Function1<Object, Object>)from_right4, (Function1<Object, Object>)from_extract4);
            from_ans.initialize(from_root4, from_maxLength);
            return (S)from_ans;
        }
        Function1<RedBlackTree.Tree, Object> & Serializable intersect = (Function1<RedBlackTree.Tree, Object> & Serializable)x$12 -> x$12.key();
        Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect8 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$11 -> x$11.right();
        Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable intersect9 = (Function1<RedBlackTree.Tree, RedBlackTree.Tree> & Serializable)x$10 -> x$10.left();
        RedBlackTree.Tree<A, Object> tree = this.tree();
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
    public boolean contains(A elem) {
        return RedBlackTree$.MODULE$.contains(this.tree(), elem, this.ordering());
    }

    @Override
    public TreeSet<A> range(A from, A until) {
        return this.newSetOrSelf(RedBlackTree$.MODULE$.range(this.tree(), from, until, this.ordering()));
    }

    @Override
    public TreeSet<A> rangeImpl(Option<A> from, Option<A> until) {
        return this.newSetOrSelf(RedBlackTree$.MODULE$.rangeImpl(this.tree(), from, until, this.ordering()));
    }

    @Override
    public TreeSet<A> incl(A elem) {
        return this.newSetOrSelf(RedBlackTree$.MODULE$.update(this.tree(), elem, null, false, this.ordering()));
    }

    @Override
    public TreeSet<A> excl(A elem) {
        return this.newSetOrSelf(RedBlackTree$.MODULE$.delete(this.tree(), elem, this.ordering()));
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public TreeSet<A> concat(IterableOnce<A> that) {
        if (!(that instanceof TreeSet)) ** GOTO lbl-1000
        var3_2 = (TreeSet)that;
        v0 = this.ordering();
        var4_3 = var3_2.ordering();
        if (!(v0 != null ? v0.equals(var4_3) == false : var4_3 != null)) {
            v1 = RedBlackTree$.MODULE$.union(this.tree(), var3_2.tree(), this.ordering());
        } else lbl-1000:
        // 2 sources

        {
            it = that.iterator();
            t = this.tree();
            while (it.hasNext()) {
                t = RedBlackTree$.MODULE$.update(t, it.next(), null, false, this.ordering());
            }
            v1 = t;
        }
        t = v1;
        return this.newSetOrSelf(t);
    }

    @Override
    public TreeSet<A> removedAll(IterableOnce<A> that) {
        if (that instanceof TreeSet) {
            TreeSet treeSet = (TreeSet)that;
            Ordering<A> ordering = this.ordering();
            Ordering<A> ordering2 = treeSet.ordering();
            if (!(ordering != null ? !ordering.equals(ordering2) : ordering2 != null)) {
                return this.newSetOrSelf(RedBlackTree$.MODULE$.difference(this.tree(), treeSet.tree(), this.ordering()));
            }
        }
        LazyRef sub$module = new LazyRef();
        that.iterator().foreach(this.sub$2(sub$module));
        return this.newSetOrSelf(this.sub$2(sub$module).currentTree());
    }

    @Override
    public TreeSet<A> intersect(Set<A> that) {
        if (that instanceof TreeSet) {
            TreeSet treeSet = (TreeSet)that;
            Ordering<A> ordering = this.ordering();
            Ordering<A> ordering2 = treeSet.ordering();
            if (!(ordering != null ? !ordering.equals(ordering2) : ordering2 != null)) {
                return this.newSetOrSelf(RedBlackTree$.MODULE$.intersect(this.tree(), treeSet.tree(), this.ordering()));
            }
        }
        return this.newSetOrSelf(RedBlackTree$.MODULE$.filterEntries(this.tree(), (Function2<Object, Object, Object> & Serializable)(k, x$13) -> BoxesRunTime.boxToBoolean(TreeSet.$anonfun$filter$1(that, k, x$13))));
    }

    @Override
    public TreeSet<A> diff(Set<A> that) {
        if (that instanceof TreeSet) {
            TreeSet treeSet = (TreeSet)that;
            Ordering<A> ordering = this.ordering();
            Ordering<A> ordering2 = treeSet.ordering();
            if (!(ordering != null ? !ordering.equals(ordering2) : ordering2 != null)) {
                return this.newSetOrSelf(RedBlackTree$.MODULE$.difference(this.tree(), treeSet.tree(), this.ordering()));
            }
        }
        return (TreeSet)SetOps.diff$(this, that);
    }

    @Override
    public TreeSet<A> filter(Function1<A, Object> f) {
        return this.newSetOrSelf(RedBlackTree$.MODULE$.filterEntries(this.tree(), (Function2<Object, Object, Object> & Serializable)(k, x$13) -> BoxesRunTime.boxToBoolean(TreeSet.$anonfun$filter$1(that, k, x$13))));
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Tuple2<TreeSet<A>, TreeSet<A>> partition(Function1<A, Object> p) {
        void var4_4;
        void var3_3;
        Tuple2<RedBlackTree.Tree<A, Object>, RedBlackTree.Tree<A, Object>> tuple2 = RedBlackTree$.MODULE$.partitionEntries(this.tree(), (Function2<Object, Object, Object> & Serializable)(a, x$14) -> BoxesRunTime.boxToBoolean(TreeSet.$anonfun$partition$1(p, a, x$14)));
        if (tuple2 == null) {
            throw new MatchError((Object)null);
        }
        RedBlackTree.Tree<A, Object> l = tuple2._1();
        RedBlackTree.Tree<A, Object> r = tuple2._2();
        return new Tuple2<TreeSet<A>, TreeSet<A>>(this.newSetOrSelf((RedBlackTree.Tree<A, Object>)var3_3), this.newSetOrSelf((RedBlackTree.Tree<A, Object>)var4_4));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TreeSet) {
            TreeSet treeSet = (TreeSet)obj;
            Ordering<A> ordering = this.ordering();
            Ordering<A> ordering2 = treeSet.ordering();
            if (!(ordering != null ? !ordering.equals(ordering2) : ordering2 != null)) {
                return RedBlackTree$.MODULE$.keysEqual(this.tree(), treeSet.tree(), this.ordering());
            }
        }
        return scala.collection.SortedSet.equals$(this, obj);
    }

    @Override
    public String className() {
        return "TreeSet";
    }

    public static final /* synthetic */ int $anonfun$stepper$3(RedBlackTree.Tree x$3) {
        return BoxesRunTime.unboxToInt(x$3.key());
    }

    public static final /* synthetic */ long $anonfun$stepper$6(RedBlackTree.Tree x$6) {
        return BoxesRunTime.unboxToLong(x$6.key());
    }

    public static final /* synthetic */ double $anonfun$stepper$9(RedBlackTree.Tree x$9) {
        return BoxesRunTime.unboxToDouble(x$9.key());
    }

    private final /* synthetic */ TreeSet$sub$1$ sub$lzycompute$1(LazyRef sub$module$1) {
        synchronized (sub$module$1) {
            TreeSet$sub$1$ treeSet$sub$1$ = sub$module$1.initialized() ? (TreeSet$sub$1$)sub$module$1.value() : sub$module$1.initialize(new TreeSet$sub$1$(this));
            return treeSet$sub$1$;
        }
    }

    /*
     * Ignored method signature, as it can't be verified against descriptor
     */
    private final TreeSet$sub$1$ sub$2(LazyRef sub$module$1) {
        if (sub$module$1.initialized()) {
            return (TreeSet$sub$1$)sub$module$1.value();
        }
        return this.sub$lzycompute$1(sub$module$1);
    }

    public static final /* synthetic */ boolean $anonfun$filter$1(Function1 f$1, Object k, Object x$13) {
        return BoxesRunTime.unboxToBoolean(f$1.apply(k));
    }

    public static final /* synthetic */ boolean $anonfun$partition$1(Function1 p$1, Object a, Object x$14) {
        return BoxesRunTime.unboxToBoolean(p$1.apply(a));
    }

    public TreeSet(RedBlackTree.Tree<A, Object> tree, Ordering<A> ordering) {
        this.tree = tree;
        this.ordering = ordering;
        if (ordering == null) {
            throw new NullPointerException("ordering must not be null");
        }
    }

    public TreeSet(Ordering<A> ordering) {
        this(null, ordering);
    }

    private static class TreeSetBuilder<A>
    extends RedBlackTree.SetHelper<A>
    implements ReusableBuilder<A, TreeSet<A>> {
        private RedBlackTree.Tree<A, Object> tree = null;

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
        public <NewTo> Builder<A, NewTo> mapResult(Function1<TreeSet<A>, NewTo> f) {
            return Builder.mapResult$(this, f);
        }

        @Override
        public final Growable<A> $plus$eq(A elem) {
            return Growable.$plus$eq$(this, elem);
        }

        @Override
        public final Growable<A> $plus$eq(A elem1, A elem2, Seq<A> elems) {
            return Growable.$plus$eq$(this, elem1, elem2, elems);
        }

        @Override
        public final Growable<A> $plus$plus$eq(IterableOnce<A> xs) {
            return Growable.$plus$plus$eq$(this, xs);
        }

        @Override
        public int knownSize() {
            return Growable.knownSize$(this);
        }

        @Override
        public TreeSetBuilder<A> addOne(A elem) {
            this.tree = this.mutableUpd(this.tree, elem);
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public TreeSetBuilder<A> addAll(IterableOnce<A> xs) {
            if (xs instanceof TreeSet) {
                TreeSet treeSet = (TreeSet)xs;
                Ordering ordering = treeSet.ordering();
                Ordering ordering2 = super.ordering();
                if (!(ordering != null ? !ordering.equals(ordering2) : ordering2 != null)) {
                    if (this.tree == null) {
                        this.tree = treeSet.tree();
                        return this;
                    }
                    this.tree = RedBlackTree$.MODULE$.union(this.beforePublish(this.tree), treeSet.tree(), super.ordering());
                    return this;
                }
            }
            if (xs instanceof TreeMap) {
                TreeMap treeMap = (TreeMap)xs;
                Ordering ordering = treeMap.ordering();
                Ordering ordering3 = super.ordering();
                if (!(ordering != null ? !ordering.equals(ordering3) : ordering3 != null)) {
                    if (this.tree == null) {
                        this.tree = treeMap.tree0();
                        return this;
                    }
                    this.tree = RedBlackTree$.MODULE$.union(this.beforePublish(this.tree), treeMap.tree0(), super.ordering());
                    return this;
                }
            }
            Growable.addAll$(this, xs);
            return this;
        }

        @Override
        public void clear() {
            this.tree = null;
        }

        @Override
        public TreeSet<A> result() {
            return new TreeSet<A>(this.beforePublish(this.tree), super.ordering());
        }

        public TreeSetBuilder(Ordering<A> ordering) {
            super(ordering);
        }
    }
}

