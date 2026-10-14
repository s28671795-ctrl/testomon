/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;
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
import scala.collection.AbstractIterator;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.MapOps;
import scala.collection.SortedMapFactory;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StrictOptimizedMapOps;
import scala.collection.View;
import scala.collection.generic.DefaultSerializationProxy;
import scala.collection.immutable.Seq;
import scala.collection.mutable.AbstractMap;
import scala.collection.mutable.Builder;
import scala.collection.mutable.CollisionProofHashMap$;
import scala.collection.mutable.Growable;
import scala.collection.mutable.GrowableBuilder;
import scala.collection.mutable.Iterable;
import scala.collection.mutable.Map;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.Statics;
import scala.util.Either;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@ScalaSignature(bytes="\u0006\u0005!%eaBA'\u0003\u001f\u0012\u0011Q\f\u0005\u000b\u0003_\u0003!\u0011!Q\u0001\n\u0005E\u0006BCA\\\u0001\t\u0005\t\u0015!\u0003\u0002:\"q\u0011q\u0018\u0001\u0005\u0002\u0003\u0015)\u0011!Q\u0001\f\u0005\u0005\u0007bBAm\u0001\u0011\u0005\u00111\u001c\u0005\t\u0003O\u0004\u0001\u0015\"\u0004\u0002j\"9\u0011\u0011\u001c\u0001\u0005\u0002\u0005MX\u0001CA}\u0001\u0001\u0006I!a?\u0006\u0011\r\u0015\u0001\u0001)A\u0005\u000b[Ba\"b\u001c\u0001\t\u0003\u0005)\u0011!A!B\u0013)\t\b\u0003\b\u0006z\u0001!\t\u0011!B\u0001\u0002\u0003\u0006K!!-\t\u001d\u0015m\u0004\u0001\"A\u0001\u0006\u0003\u0005\t\u0015)\u0003\u00022\"9QQ\u0010\u0001\u0005B\tU\u0005BDC@\u0001\u0011\u0005\tQ!A\u0001B\u00135Q\u0011\u0011\u0005\u000f\u000b\u0013\u0003A\u0011!A\u0003\u0002\u0003\u0005KQBCF\u0011\u001d\u0011i\r\u0001C)\u000b#Cq!b+\u0001\t#*i\u000bC\u0004\u0003>\u0001!\t%b-\t\u000f\u0015U\u0006\u0001\"\u0011\u00068\"9Q1\u0018\u0001\u0005\u0002\u0015u\u0006bBCd\u0001\u0011\u0005S\u0011\u001a\u0005\b\u000b3\u0004A\u0011ICn\u0011!)\u0019\u0010\u0001Q\u0005\n\u0015U\bbBC\u007f\u0001\u0011\u0005Sq \u0005\b\r\u0007\u0001A\u0011\tD\u0003\u0011\u001d1Y\u0001\u0001C!\r\u001bAqAb\u0005\u0001\t\u00031)\u0002\u0003\u0005\u0007\u001c\u0001\u0001K\u0011\u0002D\u000f\u001191y\u0003\u0001C\u0001\u0002\u000b\u0005\t\u0011)C\u0005\rcA\u0001Bb\u0010\u0001A\u0013%a\u0011\t\u0005\b\r\u0017\u0002A\u0011\tD'\u0011!1\u0019\u0006\u0001Q\u0005\n\u0019Uc\u0001\u0003D-\u0001\u0001\u000eIAb\u0017\t\u000f\u0005e\u0007\u0005\"\u0001\u0007h!Aa1\u000e\u0011!\u000e#1i\u0007\u0003\u0005\u0007l\u0001\u0002k\u0011\u0003D9\u0011!19\b\tQ!\n\u0005E\u0006\u0002CB\u0001A\u0001\u0006K!b\u001e\t\u0011\u0019e\u0004\u0005)A\u0005\u0003cCq!b\t!\t\u0003\u00199\u0005C\u0004\u0005\u000e\u0001\"\tAb\u001f\t\u000f\u0019u\u0004\u0001\"\u0011\u0007\u0000!9aq\u0011\u0001\u0005B\u0019%\u0005B\u0004DG\u0001\u0011\u0005\tQ!A\u0001B\u0013%aq\u0012\u0005\t\r+\u0003\u0001\u0015\"\u0003\u0007\u0018\"AaQ\u0014\u0001!\n\u00131y\n\u0003\b\u00072\u0002!\t\u0011!B\u0001\u0002\u0003&IAb-\t\u001d\u0019E\u0006\u0001\"A\u0001\u0006\u0003\u0005\t\u0015\"\u0003\u0007@\"Aa\u0011\u001a\u0001!\n\u00131Y\r\u0003\b\u0007R\u0002!\t\u0011!B\u0001\u0002\u0003&IAb5\t\u000f\u0019]\u0007\u0001\"\u0011\u0007Z\"9a1\u001c\u0001\u0005B\u0019u\u0007b\u0002Dq\u0001\u0011\u0005a1\u001d\u0005\b\rO\u0004A\u0011\tBK\u0011\u001d1I\u000f\u0001C!\u0007\u000fBqa!0\u0001\t\u00032Y\u000fC\u0004\u0004V\u0002!\tEb>\t\u0011\u0015}\u0003\u0001)C\t\u000f\u0007A\u0001b\"\u0002\u0001A\u0013Esq\u0001\u0005\b\u000f\u0013\u0001A\u0011ID\u0006\u0011\u001d9)\u0002\u0001C\u0001\u000f/Aqab\u0010\u0001\t\u00039\t\u0005C\u0004\b`\u0001!\ta\"\u0019\t\u000f\u001d\r\u0005\u0001\"\u0011\b\u0006\"9qq\u0013\u0001\u0005F\u001de\u0005bBDV\u0001\u0011\u0005sQ\u0016\u0005\b\u000fW\u0003A\u0011IDi\u0011!9)\u0010\u0001Q\u0005\n\u001d]\b\u0002CD\u007f\u0001\u0001&Iab@\t\u0011\t\u0015\b\u0001)C\u0005\u0011\u000bA\u0001B!:\u0001A\u0013%\u0001\u0012\u0003\u0005\t\u00117\u0001\u0001\u0015\"\u0004\t\u001e!A\u00012\u0007\u0001!\n\u001bA)\u0004\u0003\u0005\tB\u0001\u0001K\u0011\u0002E\"\u0011!AI\u0005\u0001Q\u0005\n!-\u0003\u0002\u0003E+\u0001\u0001&I\u0001c\u0016\t\u0011!}\u0003\u0001)C\u0005\u0011CB\u0001\u0002#\u001b\u0001A\u0013%\u00012\u000e\u0005\t\u0011g\u0002\u0001\u0015\"\u0003\tv!9\u0001r\u0010\u0001\u0005\u0002!\u0005u\u0001CA\u0000\u0003\u001fB\tA!\u0001\u0007\u0011\u00055\u0013q\nE\u0001\u0005\u0007Aq!!7R\t\u0003\u0011Y\u0001C\u0006\u0003\u000eE\u0013\r\u0011\"\u0002\u0002T\t=\u0001\u0002\u0003B\f#\u0002\u0006iA!\u0005\t\u000f\te\u0011\u000b\"\u0001\u0003\u001c!9!QH)\u0005\u0002\t}\u0002b\u0002B*#\u0012\u0005!Q\u000b\u0005\b\u0005'\nF\u0011\u0001B9\u0011\u001d\u0011y)\u0015C\u0003\u0005#CqAa%R\t\u000b\u0011)J\u0002\u0004\u0003\u0018F3!\u0011\u0014\u0005\u000b\u0005k[&Q1A\u0005\u0002\tU\u0005B\u0003B\\7\n\u0005\t\u0015!\u0003\u00022\"Q\u0011qW.\u0003\u0006\u0004%\tA!%\t\u0015\te6L!A!\u0002\u0013\tI\f\u0003\u0006\u0002bn\u0013)\u0019!C\u0001\u0005wC!Ba0\\\u0005\u0003\u0005\u000b\u0011\u0002B_\u0011\u001d\tIn\u0017C\u0001\u0005\u0003DqA!4\\\t\u0003\u0011y\rC\u0004\u0003Tm#\tA!6\t\u000f\t\u0015\u0018\u000b\"\u0003\u0003h\"9!Q])\u0005\n\u0011=\u0005\"\u0003CV#\n\u0007IQ\u0002CW\u0011!!\u0019,\u0015Q\u0001\u000e\u0011=faBB\u0007#\u0006\u00052q\u0002\u0005\b\u00033LG\u0011AB\t\r\u0019\tI0\u0015\u0002\u0004\u0016!Q!\u0011`6\u0003\u0002\u0004%\ta!\u0007\t\u0015\r}1N!a\u0001\n\u0003\u0019\t\u0003\u0003\u0006\u0004.-\u0014\t\u0011)Q\u0005\u00077A!B!@l\u0005\u0003\u0007I\u0011\u0001BK\u0011)\u0019yc\u001bBA\u0002\u0013\u00051\u0011\u0007\u0005\u000b\u0007kY'\u0011!Q!\n\u0005E\u0006B\u0003BqW\n\u0005\r\u0011\"\u0001\u00048!Q1QH6\u0003\u0002\u0004%\taa\u0010\t\u0015\r\r3N!A!B\u0013\u0019I\u0004\u0003\u0006\u0004F-\u0014\t\u0019!C\u0001\u0007\u000fB!ba\u0014l\u0005\u0003\u0007I\u0011AB)\u0011)\u0019)f\u001bB\u0001B\u0003&1\u0011\n\u0005\u000b\u0007/Z'\u00111A\u0005\u0002\re\u0003BCB/W\n\u0005\r\u0011\"\u0001\u0004`!Q11M6\u0003\u0002\u0003\u0006Kaa\u0017\t\u0015\r\u00154N!a\u0001\n\u0003\u0019I\u0006\u0003\u0006\u0004h-\u0014\t\u0019!C\u0001\u0007SB!b!\u001cl\u0005\u0003\u0005\u000b\u0015BB.\u0011)\u0019yg\u001bBA\u0002\u0013\u00051\u0011\f\u0005\u000b\u0007cZ'\u00111A\u0005\u0002\rM\u0004BCB<W\n\u0005\t\u0015)\u0003\u0004\\!9\u0011\u0011\\6\u0005\u0002\re\u0004bBBEW\u0012\u000531\u0012\u0005\b\u0007;[G\u0011ABP\u0011\u001d\u0019il\u001bC\u0001\u0007\u007fCqa!6l\t\u0003\u00199\u000eC\u0004\u0004h.$\ta!;\t\u000f\u0011U\u0016\u000b\"\u0003\u00058\"9AQ[)\u0005\n\u0011]\u0007b\u0002Cu#\u0012%A1\u001e\u0004\u0007\tw\ff\u0001\"@\t\u0017\u0015E\u0011Q\u0003B\u0001B\u0003%Qq\u0001\u0005\f\u0005_\f)B!A!\u0002\u0017)\u0019\u0002\u0003\u0005\u0002Z\u0006UA\u0011AC\f\u0011%)\t#!\u0006!B\u0013)9\u0001\u0003\u0005\u0006$\u0005UA\u0011AB$\u0011!!i!!\u0006\u0005\u0002\u0015\u0015bABB\u0003#\u001a\u00199\u0001C\u0006\u0003z\u0006\r\"\u00111A\u0005\u0002\rU\bbCB\u0010\u0003G\u0011\t\u0019!C\u0001\u0007wD1b!\f\u0002$\t\u0005\t\u0015)\u0003\u0004x\"Y!Q`A\u0012\u0005\u0003\u0007I\u0011\u0001BK\u0011-\u0019y#a\t\u0003\u0002\u0004%\taa@\t\u0017\rU\u00121\u0005B\u0001B\u0003&\u0011\u0011\u0017\u0005\f\u0005C\f\u0019C!a\u0001\n\u0003!\u0019\u0001C\u0006\u0004>\u0005\r\"\u00111A\u0005\u0002\u0011%\u0001bCB\"\u0003G\u0011\t\u0011)Q\u0005\t\u000bA1\u0002\"\u0004\u0002$\t\u0005\r\u0011\"\u0001\u0005\u0010!YA1CA\u0012\u0005\u0003\u0007I\u0011\u0001C\u000b\u0011-!I\"a\t\u0003\u0002\u0003\u0006K\u0001\"\u0005\t\u0011\u0005e\u00171\u0005C\u0001\t7A\u0001b!#\u0002$\u0011\u0005CQ\u0005\u0005\n\tk\t\u0019\u0003)C\u0005\toA\u0001b!(\u0002$\u0011\u0005A\u0011\t\u0005\t\u0007{\u000b\u0019\u0003\"\u0001\u0005P!A1Q[A\u0012\t\u0003!y\u0006\u0003\u0005\u0004h\u0006\rB\u0011\u0001C7\u0011%)y&UA\u0001\n\u0013)\tGA\u000bD_2d\u0017n]5p]B\u0013xn\u001c4ICNDW*\u00199\u000b\t\u0005E\u00131K\u0001\b[V$\u0018M\u00197f\u0015\u0011\t)&a\u0016\u0002\u0015\r|G\u000e\\3di&|gN\u0003\u0002\u0002Z\u0005)1oY1mC\u000e\u0001QCBA0\u0003[\n\u0019iE\u0005\u0001\u0003C\n9)!&\u0002*BA\u00111MA3\u0003S\n\t)\u0004\u0002\u0002P%!\u0011qMA(\u0005-\t%m\u001d;sC\u000e$X*\u00199\u0011\t\u0005-\u0014Q\u000e\u0007\u0001\t\u001d\ty\u0007\u0001b\u0001\u0003c\u0012\u0011aS\t\u0005\u0003g\nY\b\u0005\u0003\u0002v\u0005]TBAA,\u0013\u0011\tI(a\u0016\u0003\u000f9{G\u000f[5oOB!\u0011QOA?\u0013\u0011\ty(a\u0016\u0003\u0007\u0005s\u0017\u0010\u0005\u0003\u0002l\u0005\rEaBAC\u0001\t\u0007\u0011\u0011\u000f\u0002\u0002-Ba\u00111MAE\u0003S\n\t)!$\u0002\u0014&!\u00111RA(\u0005\u0019i\u0015\r](qgB!\u00111MAH\u0013\u0011\t\t*a\u0014\u0003\u00075\u000b\u0007\u000fE\u0004\u0002d\u0001\tI'!!\u0011\u0015\u0005]\u0015\u0011TAO\u0003G\u000b\u0019*\u0004\u0002\u0002T%!\u00111TA*\u0005i\u0019FO]5di>\u0003H/[7ju\u0016$\u0017\n^3sC\ndWm\u00149t!!\t)(a(\u0002j\u0005\u0005\u0015\u0002BAQ\u0003/\u0012a\u0001V;qY\u0016\u0014\u0004\u0003BA2\u0003KKA!a*\u0002P\tA\u0011\n^3sC\ndW\r\u0005\u0007\u0002\u0018\u0006-\u0016\u0011NAA\u0003\u001b\u000b\u0019*\u0003\u0003\u0002.\u0006M#!F*ue&\u001cGo\u00149uS6L'0\u001a3NCB|\u0005o]\u0001\u0010S:LG/[1m\u0007\u0006\u0004\u0018mY5usB!\u0011QOAZ\u0013\u0011\t),a\u0016\u0003\u0007%sG/\u0001\u0006m_\u0006$g)Y2u_J\u0004B!!\u001e\u0002<&!\u0011QXA,\u0005\u0019!u.\u001e2mK\u0006A4oY1mC\u0012\u001aw\u000e\u001c7fGRLwN\u001c\u0013nkR\f'\r\\3%\u0007>dG.[:j_:\u0004&o\\8g\u0011\u0006\u001c\b.T1qI\u0011z'\u000fZ3sS:<\u0007CBAb\u0003'\fIG\u0004\u0003\u0002F\u0006=g\u0002BAd\u0003\u001bl!!!3\u000b\t\u0005-\u00171L\u0001\u0007yI|w\u000e\u001e \n\u0005\u0005e\u0013\u0002BAi\u0003/\nq\u0001]1dW\u0006<W-\u0003\u0003\u0002V\u0006]'\u0001C(sI\u0016\u0014\u0018N\\4\u000b\t\u0005E\u0017qK\u0001\u0007y%t\u0017\u000e\u001e \u0015\r\u0005u\u00171]As)\u0011\t\u0019*a8\t\u000f\u0005\u0005H\u0001q\u0001\u0002B\u0006AqN\u001d3fe&tw\rC\u0004\u00020\u0012\u0001\r!!-\t\u000f\u0005]F\u00011\u0001\u0002:\u0006\u00012o\u001c:uK\u0012l\u0015\r\u001d$bGR|'/_\u000b\u0003\u0003W\u0004b!a&\u0002n\u0006E\u0018\u0002BAx\u0003'\u0012\u0001cU8si\u0016$W*\u00199GC\u000e$xN]=\u0011\u0007\u0005\r\u0004\u0001\u0006\u0002\u0002vR!\u00111SA|\u0011\u001d\t\tO\u0002a\u0002\u0003\u0003\u0014aA\u0015\"O_\u0012,\u0007cBA\u007fW\u0006%\u0014\u0011\u0011\b\u0004\u0003G\u0002\u0016!F\"pY2L7/[8o!J|wN\u001a%bg\"l\u0015\r\u001d\t\u0004\u0003G\n6#B)\u0003\u0006\u0005-\b\u0003BA;\u0005\u000fIAA!\u0003\u0002X\t1\u0011I\\=SK\u001a$\"A!\u0001\u0002\r=\u0014H-T:h+\t\u0011\tb\u0004\u0002\u0003\u0014\u0005\u0012!QC\u0001\u000269{\u0007%[7qY&\u001c\u0017\u000e\u001e\u0011Pe\u0012,'/\u001b8h7\u0012Z8JM?^A\u0019|WO\u001c3!i>\u0004#-^5mI\u0002\n\u0007eQ8mY&\u001c\u0018n\u001c8Qe>|g\rS1tQ6\u000b\u0007o\u0017\u0013|\u0017JjH\u0006\t\u0013|-JjXL\f\u0011Z_V\u0004S.Y=!o\u0006tG\u000f\t;pAU\u00048-Y:uAQ|\u0007%\u0019\u0011NCB\\Fe_&~Y\u0001\"3PV?^A\u0019L'o\u001d;!Ef\u00043-\u00197mS:<\u0007\u0005Y;og>\u0014H/\u001a3a]\u00059qN\u001d3Ng\u001e\u0004\u0013\u0001\u00024s_6,bA!\b\u0003&\t%B\u0003\u0002B\u0010\u0005c!BA!\t\u0003,A9\u00111\r\u0001\u0003$\t\u001d\u0002\u0003BA6\u0005K!q!a\u001cV\u0005\u0004\t\t\b\u0005\u0003\u0002l\t%BaBAC+\n\u0007\u0011\u0011\u000f\u0005\n\u0005[)\u0016\u0011!a\u0002\u0005_\t!\"\u001a<jI\u0016t7-\u001a\u00132!\u0019\t\u0019-a5\u0003$!9!1G+A\u0002\tU\u0012AA5u!\u0019\t9Ja\u000e\u0003<%!!\u0011HA*\u00051IE/\u001a:bE2,wJ\\2f!!\t)(a(\u0003$\t\u001d\u0012!B3naRLXC\u0002B!\u0005\u000f\u0012Y\u0005\u0006\u0003\u0003D\t5\u0003cBA2\u0001\t\u0015#\u0011\n\t\u0005\u0003W\u00129\u0005B\u0004\u0002pY\u0013\r!!\u001d\u0011\t\u0005-$1\n\u0003\b\u0003\u000b3&\u0019AA9\u0011%\u0011yEVA\u0001\u0002\b\u0011\t&\u0001\u0006fm&$WM\\2fII\u0002b!a1\u0002T\n\u0015\u0013A\u00038fo\n+\u0018\u000e\u001c3feV1!q\u000bB2\u0005O\"BA!\u0017\u0003lAA\u00111\rB.\u0005?\u0012I'\u0003\u0003\u0003^\u0005=#a\u0002\"vS2$WM\u001d\t\t\u0003k\nyJ!\u0019\u0003fA!\u00111\u000eB2\t\u001d\tyg\u0016b\u0001\u0003c\u0002B!a\u001b\u0003h\u00119\u0011QQ,C\u0002\u0005E\u0004cBA2\u0001\t\u0005$Q\r\u0005\n\u0005[:\u0016\u0011!a\u0002\u0005_\n!\"\u001a<jI\u0016t7-\u001a\u00134!\u0019\t\u0019-a5\u0003bU1!1\u000fB?\u0005\u0003#bA!\u001e\u0003\f\n5E\u0003\u0002B<\u0005\u000b\u0003\u0002\"a\u0019\u0003\\\te$1\u0011\t\t\u0003k\nyJa\u001f\u0003\u0000A!\u00111\u000eB?\t\u001d\ty\u0007\u0017b\u0001\u0003c\u0002B!a\u001b\u0003\u0002\u00129\u0011Q\u0011-C\u0002\u0005E\u0004cBA2\u0001\tm$q\u0010\u0005\n\u0005\u000fC\u0016\u0011!a\u0002\u0005\u0013\u000b!\"\u001a<jI\u0016t7-\u001a\u00135!\u0019\t\u0019-a5\u0003|!9\u0011q\u0016-A\u0002\u0005E\u0006bBA\\1\u0002\u0007\u0011\u0011X\u0001\u0012I\u00164\u0017-\u001e7u\u0019>\fGMR1di>\u0014XCAA]\u0003Y!WMZ1vYRLe.\u001b;jC2\u001c\u0015\r]1dSRLXCAAY\u0005Y!Um]3sS\u0006d\u0017N_1uS>tg)Y2u_JLXC\u0002BN\u0005O\u0013YkE\u0004\\\u0005\u000b\u0011iJa,\u0011\u0011\u0005]%q\u0014BR\u0005[KAA!)\u0002T\t9a)Y2u_JL\b\u0003CA;\u0003?\u0013)K!+\u0011\t\u0005-$q\u0015\u0003\b\u0003_Z&\u0019AA9!\u0011\tYGa+\u0005\u000f\u0005\u00155L1\u0001\u0002rA9\u00111\r\u0001\u0003&\n%\u0006\u0003BAb\u0005cKAAa-\u0002X\na1+\u001a:jC2L'0\u00192mK\u0006YA/\u00192mK2+gn\u001a;i\u00031!\u0018M\u00197f\u0019\u0016tw\r\u001e5!\u0003-aw.\u00193GC\u000e$xN\u001d\u0011\u0016\u0005\tu\u0006CBAb\u0003'\u0014)+A\u0005pe\u0012,'/\u001b8hAQA!1\u0019Bd\u0005\u0013\u0014Y\rE\u0004\u0003Fn\u0013)K!+\u000e\u0003ECqA!.c\u0001\u0004\t\t\fC\u0004\u00028\n\u0004\r!!/\t\u000f\u0005\u0005(\r1\u0001\u0003>\u0006aaM]8n'B,7-\u001b4jGR!!Q\u0016Bi\u0011\u001d\u0011\u0019d\u0019a\u0001\u0005'\u0004b!a&\u00038\t\rVC\u0001Bl!!\t\u0019Ga\u0017\u0003$\n5\u0006fB.\u0003\\\n\u0005(1\u001d\t\u0005\u0003k\u0012i.\u0003\u0003\u0003`\u0006]#\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\u0019\u0011aB2p[B\f'/Z\u000b\u0007\u0005S\u0014)\u0010\" \u0015\u0011\t-(q\u001fB~\u0005\u007f$B!!-\u0003n\"9!q^3A\u0004\tE\u0018aA8sIB1\u00111YAj\u0005g\u0004B!a\u001b\u0003v\u00129\u0011qN3C\u0002\u0005E\u0004b\u0002B}K\u0002\u0007!1_\u0001\u0004W\u0016L\bb\u0002B\u007fK\u0002\u0007\u0011\u0011W\u0001\u0005Q\u0006\u001c\b\u000eC\u0004\u0004\u0002\u0015\u0004\raa\u0001\u0002\t9|G-\u001a\t\t\u0005\u000b\f\u0019Ca=\u0005|\t1A\n\u0014(pI\u0016,ba!\u0003\u0004z\u0012\u001d1\u0003BA\u0012\u0007\u0017\u00012A!2j\u0005\u0011qu\u000eZ3\u0014\u0007%\u0014)\u0001\u0006\u0002\u0004\f%\"\u0011.a\tl+\u0019\u00199b!\b\u0004<M\u00191na\u0003\u0016\u0005\rm\u0001\u0003BA6\u0007;!q!a\u001cl\u0005\u0004\t\t(A\u0004lKf|F%Z9\u0015\t\r\r2\u0011\u0006\t\u0005\u0003k\u001a)#\u0003\u0003\u0004(\u0005]#\u0001B+oSRD\u0011ba\u000bn\u0003\u0003\u0005\raa\u0007\u0002\u0007a$\u0013'\u0001\u0003lKf\u0004\u0013\u0001\u00035bg\"|F%Z9\u0015\t\r\r21\u0007\u0005\n\u0007W\u0001\u0018\u0011!a\u0001\u0003c\u000bQ\u0001[1tQ\u0002*\"a!\u000f\u0011\t\u0005-41\b\u0003\b\u0003\u000b['\u0019AA9\u0003%1\u0018\r\\;f?\u0012*\u0017\u000f\u0006\u0003\u0004$\r\u0005\u0003\"CB\u0016g\u0006\u0005\t\u0019AB\u001d\u0003\u00191\u0018\r\\;fA\u0005\u0019!/\u001a3\u0016\u0005\r%\u0003\u0003BA;\u0007\u0017JAa!\u0014\u0002X\t9!i\\8mK\u0006t\u0017a\u0002:fI~#S-\u001d\u000b\u0005\u0007G\u0019\u0019\u0006C\u0005\u0004,Y\f\t\u00111\u0001\u0004J\u0005!!/\u001a3!\u0003\u0011aWM\u001a;\u0016\u0005\rm\u0003c\u0002BcW\u000em1\u0011H\u0001\tY\u00164Go\u0018\u0013fcR!11EB1\u0011%\u0019Y#_A\u0001\u0002\u0004\u0019Y&A\u0003mK\u001a$\b%A\u0003sS\u001eDG/A\u0005sS\u001eDGo\u0018\u0013fcR!11EB6\u0011%\u0019Y\u0003`A\u0001\u0002\u0004\u0019Y&\u0001\u0004sS\u001eDG\u000fI\u0001\u0007a\u0006\u0014XM\u001c;\u0002\u0015A\f'/\u001a8u?\u0012*\u0017\u000f\u0006\u0003\u0004$\rU\u0004\"CB\u0016\u007f\u0006\u0005\t\u0019AB.\u0003\u001d\u0001\u0018M]3oi\u0002\"\u0002ca\u0017\u0004|\ru4qPBA\u0007\u0007\u001b)ia\"\t\u0011\te\u00181\u0001a\u0001\u00077A\u0001B!@\u0002\u0004\u0001\u0007\u0011\u0011\u0017\u0005\t\u0005C\f\u0019\u00011\u0001\u0004:!A1QIA\u0002\u0001\u0004\u0019I\u0005\u0003\u0005\u0004X\u0005\r\u0001\u0019AB.\u0011!\u0019)'a\u0001A\u0002\rm\u0003\u0002CB8\u0003\u0007\u0001\raa\u0017\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"a!$\u0011\t\r=5q\u0013\b\u0005\u0007#\u001b\u0019\n\u0005\u0003\u0002H\u0006]\u0013\u0002BBK\u0003/\na\u0001\u0015:fI\u00164\u0017\u0002BBM\u00077\u0013aa\u0015;sS:<'\u0002BBK\u0003/\nqaZ3u\u001d>$W\r\u0006\u0004\u0004\"\u000e\u001d61\u0016\u000b\u0005\u00077\u001a\u0019\u000b\u0003\u0005\u0003p\u0006\u001d\u00019ABS!\u0019\t\u0019-a5\u0004\u001c!A1\u0011VA\u0004\u0001\u0004\u0019Y\"A\u0001l\u0011!\u0019i+a\u0002A\u0002\u0005E\u0016!\u00015)\t\u0005\u001d1\u0011\u0017\t\u0005\u0007g\u001bI,\u0004\u0002\u00046*!1qWA,\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0007w\u001b)LA\u0004uC&d'/Z2\u0002\u000f\u0019|'/Z1dQV!1\u0011YBi)\u0011\u0019\u0019ca1\t\u0011\r\u0015\u0017\u0011\u0002a\u0001\u0007\u000f\f\u0011A\u001a\t\t\u0003k\u001aIm!4\u0004P&!11ZA,\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0005\u0002v\u0005}51DB\u001d!\u0011\tYg!5\u0005\u0011\rM\u0017\u0011\u0002b\u0001\u0003c\u0012\u0011!V\u0001\rM>\u0014X-Y2i\u000b:$(/_\u000b\u0005\u00073\u001c)\u000f\u0006\u0003\u0004$\rm\u0007\u0002CBc\u0003\u0017\u0001\ra!8\u0011\u0015\u0005U4q\\B\u000e\u0007s\u0019\u0019/\u0003\u0003\u0004b\u0006]#!\u0003$v]\u000e$\u0018n\u001c83!\u0011\tYg!:\u0005\u0011\rM\u00171\u0002b\u0001\u0003c\n1BZ8sK\u0006\u001c\u0007NT8eKV!11^Bz)\u0011\u0019\u0019c!<\t\u0011\r\u0015\u0017Q\u0002a\u0001\u0007_\u0004\u0002\"!\u001e\u0004J\u000em3\u0011\u001f\t\u0005\u0003W\u001a\u0019\u0010\u0002\u0005\u0004T\u00065!\u0019AA9+\t\u00199\u0010\u0005\u0003\u0002l\reH\u0001CA8\u0003G\u0011\r!!\u001d\u0015\t\r\r2Q \u0005\u000b\u0007W\t9#!AA\u0002\r]H\u0003BB\u0012\t\u0003A!ba\u000b\u0002.\u0005\u0005\t\u0019AAY+\t!)\u0001\u0005\u0003\u0002l\u0011\u001dA\u0001CAC\u0003G\u0011\r!!\u001d\u0015\t\r\rB1\u0002\u0005\u000b\u0007W\t\u0019$!AA\u0002\u0011\u0015\u0011\u0001\u00028fqR,\"\u0001\"\u0005\u0011\u0011\t\u0015\u00171EB|\t\u000b\t\u0001B\\3yi~#S-\u001d\u000b\u0005\u0007G!9\u0002\u0003\u0006\u0004,\u0005e\u0012\u0011!a\u0001\t#\tQA\\3yi\u0002\"\"\u0002\"\u0005\u0005\u001e\u0011}A\u0011\u0005C\u0012\u0011!\u0011I0!\u0010A\u0002\r]\b\u0002\u0003B\u007f\u0003{\u0001\r!!-\t\u0011\t\u0005\u0018Q\ba\u0001\t\u000bA\u0001\u0002\"\u0004\u0002>\u0001\u0007A\u0011\u0003\u000b\u0003\tO\u0001B\u0001\"\u000b\u000545\u0011A1\u0006\u0006\u0005\t[!y#\u0001\u0003mC:<'B\u0001C\u0019\u0003\u0011Q\u0017M^1\n\t\reE1F\u0001\u0003KF$ba!\u0013\u0005:\u0011u\u0002\u0002\u0003C\u001e\u0003\u0003\u0002\r!a\u001f\u0002\u0003\u0005D\u0001\u0002b\u0010\u0002B\u0001\u0007\u00111P\u0001\u0002ER1A1\tC%\t\u0017\"B\u0001\"\u0005\u0005F!A!q^A\"\u0001\b!9\u0005\u0005\u0004\u0002D\u0006M7q\u001f\u0005\t\u0007S\u000b\u0019\u00051\u0001\u0004x\"A1QVA\"\u0001\u0004\t\t\f\u000b\u0003\u0002D\rEV\u0003\u0002C)\t7\"Baa\t\u0005T!A1QYA#\u0001\u0004!)\u0006\u0005\u0005\u0002v\r%Gq\u000bC-!!\t)(a(\u0004x\u0012\u0015\u0001\u0003BA6\t7\"\u0001ba5\u0002F\t\u0007\u0011\u0011\u000f\u0015\u0005\u0003\u000b\u001a\t,\u0006\u0003\u0005b\u0011%D\u0003BB\u0012\tGB\u0001b!2\u0002H\u0001\u0007AQ\r\t\u000b\u0003k\u001ayna>\u0005\u0006\u0011\u001d\u0004\u0003BA6\tS\"\u0001ba5\u0002H\t\u0007\u0011\u0011\u000f\u0015\u0005\u0003\u000f\u001a\t,\u0006\u0003\u0005p\u0011]D\u0003BB\u0012\tcB\u0001b!2\u0002J\u0001\u0007A1\u000f\t\t\u0003k\u001aI\r\"\u0005\u0005vA!\u00111\u000eC<\t!\u0019\u0019.!\u0013C\u0002\u0005E\u0004\u0006BA%\u0007c\u0003B!a\u001b\u0005~\u00119\u0011QQ3C\u0002\u0005E\u0004fA3\u0005\u0002B!\u0011Q\u000fCB\u0013\u0011!))a\u0016\u0003\r%tG.\u001b8fQ\r)G\u0011\u0012\t\u0005\u0007g#Y)\u0003\u0003\u0005\u000e\u000eU&AB;okN,G-\u0006\u0004\u0005\u0012\u0012mEq\u0015\u000b\t\t'#i\nb(\u0005\"R!\u0011\u0011\u0017CK\u0011\u001d\u0011yO\u001aa\u0002\t/\u0003b!a1\u0002T\u0012e\u0005\u0003BA6\t7#q!a\u001cg\u0005\u0004\t\t\bC\u0004\u0003z\u001a\u0004\r\u0001\"'\t\u000f\tuh\r1\u0001\u00022\"91\u0011\u00014A\u0002\u0011\r\u0006c\u0002BcW\u0012eEQ\u0015\t\u0005\u0003W\"9\u000bB\u0004\u0002\u0006\u001a\u0014\r!!\u001d)\u0007\u0019$\t)\u0001\tue\u0016,\u0017NZ=UQJ,7\u000f[8mIV\u0011AqV\b\u0003\tck\u0012\u0001C\u0001\u0012iJ,W-\u001b4z)\"\u0014Xm\u001d5pY\u0012\u0004\u0013\u0001\u00027fC\u001a,b\u0001\"/\u0005@\u0012\u0015G\u0003\u0004C^\t\u0013$Y\r\"4\u0005P\u0012E\u0007c\u0002BcW\u0012uF1\u0019\t\u0005\u0003W\"y\f\u0002\u0005\u0005B\u0006=!\u0019AA9\u0005\u0005\t\u0005\u0003BA6\t\u000b$\u0001\u0002b2\u0002\u0010\t\u0007\u0011\u0011\u000f\u0002\u0002\u0005\"A!\u0011`A\b\u0001\u0004!i\f\u0003\u0005\u0003~\u0006=\u0001\u0019AAY\u0011!\u0011\t/a\u0004A\u0002\u0011\r\u0007\u0002CB#\u0003\u001f\u0001\ra!\u0013\t\u0011\r=\u0014q\u0002a\u0001\twCC!a\u0004\u0005\u0002\u0006qQ.\u001b8O_\u0012,gj\u001c8Ok2dWC\u0002Cm\t?$\u0019\u000f\u0006\u0003\u0005\\\u0012\u0015\bc\u0002BcW\u0012uG\u0011\u001d\t\u0005\u0003W\"y\u000e\u0002\u0005\u0005B\u0006E!\u0019AA9!\u0011\tY\u0007b9\u0005\u0011\u0011\u001d\u0017\u0011\u0003b\u0001\u0003cB\u0001b!\u0001\u0002\u0012\u0001\u0007A1\u001c\u0015\u0005\u0003#\u0019\t,A\u0005tk\u000e\u001cWm]:peV1AQ\u001eCz\to$B\u0001b<\u0005zB9!QY6\u0005r\u0012U\b\u0003BA6\tg$\u0001\u0002\"1\u0002\u0014\t\u0007\u0011\u0011\u000f\t\u0005\u0003W\"9\u0010\u0002\u0005\u0005H\u0006M!\u0019AA9\u0011!\u0019\t!a\u0005A\u0002\u0011=(a\u0004*C\u001d>$Wm]%uKJ\fGo\u001c:\u0016\r\u0011}X1BC\b'\u0011\t)\"\"\u0001\u0011\r\u0005]U1AC\u0004\u0013\u0011))!a\u0015\u0003!\u0005\u00137\u000f\u001e:bGRLE/\u001a:bi>\u0014\bc\u0002BcW\u0016%QQ\u0002\t\u0005\u0003W*Y\u0001\u0002\u0005\u0005B\u0006U!\u0019AA9!\u0011\tY'b\u0004\u0005\u0011\u0011\u001d\u0017Q\u0003b\u0001\u0003c\nA\u0001\u001e:fKB1\u00111YAj\u000b\u0013AC!!\u0007\u0005\nR!Q\u0011DC\u0010)\u0011)Y\"\"\b\u0011\u0011\t\u0015\u0017QCC\u0005\u000b\u001bA\u0001Ba<\u0002\u001c\u0001\u000fQ1\u0003\u0005\t\u000b#\tY\u00021\u0001\u0006\b\u0005Aa.\u001a=u\u001d>$W-A\u0004iCNtU\r\u001f;\u0015\u0005\u0015\u001d\u0001FBA\u0011\u000bS))\u0004\u0005\u0004\u0002v\u0015-RqF\u0005\u0005\u000b[\t9F\u0001\u0004uQJ|wo\u001d\t\u0005\u0003\u0007,\t$\u0003\u0003\u00064\u0005]'A\u0006(p'V\u001c\u0007.\u00127f[\u0016tG/\u0012=dKB$\u0018n\u001c82\u000fy\u0019i)b\u000e\u0006^EJ1%\"\u000f\u0006B\u0015MS1I\u000b\u0005\u000bw)i$\u0006\u0002\u0004\u000e\u0012AQqHA.\u0005\u0004)IEA\u0001U\u0013\u0011)\u0019%\"\u0012\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00132\u0015\u0011)9%a\u0016\u0002\rQD'o\\<t#\u0011\t\u0019(b\u0013\u0011\t\u00155Sq\n\b\u0005\u0003k\ny-\u0003\u0003\u0006R\u0005]'!\u0003+ie><\u0018M\u00197fc%\u0019SQKC,\u000b3*9E\u0004\u0003\u0002v\u0015]\u0013\u0002BC$\u0003/\ntAIA;\u0003/*YFA\u0003tG\u0006d\u0017-M\u0002'\u000b_\tAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!b\u0019\u0011\t\u0011%RQM\u0005\u0005\u000bO\"YC\u0001\u0004PE*,7\r\u001e\u0015\b#\nm'\u0011\u001dBrQ\u001d\u0001&1\u001cBq\u0005G\u0004\u0002\"!@\u0002$\u0005%\u0014\u0011Q\u00016g\u000e\fG.\u0019\u0013d_2dWm\u0019;j_:$S.\u001e;bE2,GeQ8mY&\u001c\u0018n\u001c8Qe>|g\rS1tQ6\u000b\u0007\u000f\n\u0013uC\ndW\r\u0005\u0004\u0002v\u0015MTqO\u0005\u0005\u000bk\n9FA\u0003BeJ\f\u0017\u0010E\u0002\u0002~&\f\u0011h]2bY\u0006$3m\u001c7mK\u000e$\u0018n\u001c8%[V$\u0018M\u00197fI\r{G\u000e\\5tS>t\u0007K]8pM\"\u000b7\u000f['ba\u0012\"C\u000f\u001b:fg\"|G\u000eZ\u0001<g\u000e\fG.\u0019\u0013d_2dWm\u0019;j_:$S.\u001e;bE2,GeQ8mY&\u001c\u0018n\u001c8Qe>|g\rS1tQ6\u000b\u0007\u000f\n\u0013d_:$XM\u001c;TSj,\u0017\u0001B:ju\u0016\f1h]2bY\u0006$3m\u001c7mK\u000e$\u0018n\u001c8%[V$\u0018M\u00197fI\r{G\u000e\\5tS>t\u0007K]8pM\"\u000b7\u000f['ba\u0012\"3m\\7qkR,\u0007*Y:i)\u0011\t\t,b!\t\u000f\u0015\u0015U\u00021\u0001\u0002j\u0005\tq\u000eK\u0002\u000e\t\u0003\u000bQg]2bY\u0006$3m\u001c7mK\u000e$\u0018n\u001c8%[V$\u0018M\u00197fI\r{G\u000e\\5tS>t\u0007K]8pM\"\u000b7\u000f['ba\u0012\"\u0013N\u001c3fqR!\u0011\u0011WCG\u0011\u001d\u0011iP\u0004a\u0001\u0003cC3A\u0004CA)\u0011)\u0019*b)+\t\u0005MUQS\u0016\u0003\u000b/\u0003B!\"'\u0006 6\u0011Q1\u0014\u0006\u0005\u000b;\u001b),A\u0005v]\u000eDWmY6fI&!Q\u0011UCN\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0005\b\u000bK{\u0001\u0019ACT\u0003\u0011\u0019w\u000e\u001c7+\t\u0015%VQ\u0013\t\u0007\u0003/\u00139$!(\u0002%9,wo\u00159fG&4\u0017n\u0019\"vS2$WM]\u000b\u0003\u000b_SC!\"-\u0006\u0016BA\u00111\rB.\u0003;\u000b\u0019*\u0006\u0002\u0002\u0014\u0006A1m\u001c8uC&t7\u000f\u0006\u0003\u0004J\u0015e\u0006b\u0002B}%\u0001\u0007\u0011\u0011N\u0001\u0004O\u0016$H\u0003BC`\u000b\u000b\u0004b!!\u001e\u0006B\u0006\u0005\u0015\u0002BCb\u0003/\u0012aa\u00149uS>t\u0007b\u0002B}'\u0001\u0007\u0011\u0011N\u0001\u0006CB\u0004H.\u001f\u000b\u0005\u0003\u0003+Y\rC\u0004\u0003zR\u0001\r!!\u001b)\u000bQ)I#b42\u000fy\u0019i)\"5\u0006XFJ1%\"\u000f\u0006B\u0015MW1I\u0019\nG\u0015USqKCk\u000b\u000f\ntAIA;\u0003/*Y&M\u0002'\u000b_\t\u0011bZ3u\u001fJ,En]3\u0016\t\u0015uW\u0011\u001d\u000b\u0007\u000b?,9/\";\u0011\t\u0005-T\u0011\u001d\u0003\b\u000bG,\"\u0019ACs\u0005\t1\u0016'\u0005\u0003\u0002\u0002\u0006m\u0004b\u0002B}+\u0001\u0007\u0011\u0011\u000e\u0005\t\u000bW,B\u00111\u0001\u0006n\u00069A-\u001a4bk2$\bCBA;\u000b_,y.\u0003\u0003\u0006r\u0006]#\u0001\u0003\u001fcs:\fW.\u001a \u0002\u0011\u0019Lg\u000e\u001a(pI\u0016$B!b\u001e\u0006x\"9Q\u0011 \fA\u0002\u0005%\u0014\u0001B3mK6D3A\u0006CA\u0003!\u0019\u0018N_3IS:$H\u0003BB\u0012\r\u0003Aq!\" \u0018\u0001\u0004\t\t,\u0001\u0004va\u0012\fG/\u001a\u000b\u0007\u0007G19A\"\u0003\t\u000f\te\b\u00041\u0001\u0002j!9!\u0011\u001d\rA\u0002\u0005\u0005\u0015a\u00019viR1Qq\u0018D\b\r#AqA!?\u001a\u0001\u0004\tI\u0007C\u0004\u0003bf\u0001\r!!!\u0002\r\u0005$Gm\u00148f)\u001119B\"\u0007\u000e\u0003\u0001Aq!\"?\u001b\u0001\u0004\ti*\u0001\u0003qkR\u0004D\u0003\u0003D\u0010\rK19C\"\u000b\u0011\r\u0005Ud\u0011EAA\u0013\u00111\u0019#a\u0016\u0003\tM{W.\u001a\u0005\b\u0005s\\\u0002\u0019AA5\u0011\u001d\u0011\to\u0007a\u0001\u0003\u0003CqAb\u000b\u001c\u0001\u0004\u0019I%\u0001\u0004hKR|E\u000e\u001a\u0015\u00047\u0011\u0005\u0015\u0001N:dC2\fGeY8mY\u0016\u001cG/[8oI5,H/\u00192mK\u0012\u001au\u000e\u001c7jg&|g\u000e\u0015:p_\u001aD\u0015m\u001d5NCB$C\u0005];uaQaaq\u0004D\u001a\rk19D\"\u000f\u0007<!9!\u0011 \u000fA\u0002\u0005%\u0004b\u0002Bq9\u0001\u0007\u0011\u0011\u0011\u0005\b\rWa\u0002\u0019AB%\u0011\u001d\u0011i\u0010\ba\u0001\u0003cCqA\"\u0010\u001d\u0001\u0004\t\t,A\u0002jIb\fq\u0001\u001e:fK&4\u0017\u0010\u0006\u0004\u0004$\u0019\rc\u0011\n\u0005\b\r\u000bj\u0002\u0019\u0001D$\u0003\ryG\u000e\u001a\t\u0004\r/A\u0001b\u0002D\u001f;\u0001\u0007\u0011\u0011W\u0001\u0007C\u0012$\u0017\t\u001c7\u0015\t\u0019]aq\n\u0005\b\r#r\u0002\u0019ACU\u0003\tA8/A\u0004sK6|g/\u001a\u0019\u0015\t\u0005mdq\u000b\u0005\b\u000bs|\u0002\u0019AA5\u0005-i\u0015\r]%uKJ\fGo\u001c:\u0016\t\u0019uc1M\n\u0004A\u0019}\u0003CBAL\u000b\u00071\t\u0007\u0005\u0003\u0002l\u0019\rDa\u0002D3A\t\u0007\u0011\u0011\u000f\u0002\u0002%R\u0011a\u0011\u000e\t\u0006\r/\u0001c\u0011M\u0001\bKb$(/Y2u)\u00111\tGb\u001c\t\u000f\r\u0005!\u00051\u0001\u0007HQ!a\u0011\rD:\u0011\u001d\u0019\ta\ta\u0001\rk\u00022Ab\u0006\b\u0003\u0005I\u0017a\u00017f]R\u0011a\u0011M\u0001\rW\u0016L8/\u0013;fe\u0006$xN]\u000b\u0003\r\u0003\u0003b!a&\u0007\u0004\u0006%\u0014\u0002\u0002DC\u0003'\u0012\u0001\"\u0013;fe\u0006$xN]\u0001\tSR,'/\u0019;peV\u0011a1\u0012\t\u0007\u0003/3\u0019)!(\u0002sM\u001c\u0017\r\\1%G>dG.Z2uS>tG%\\;uC\ndW\rJ\"pY2L7/[8o!J|wN\u001a%bg\"l\u0015\r\u001d\u0013%OJ|w\u000fV1cY\u0016$Baa\t\u0007\u0012\"9a1S\u0016A\u0002\u0005E\u0016A\u00028fo2,g.\u0001\u0007sK\u0006dGn\\2UC\ndW\r\u0006\u0003\u0004$\u0019e\u0005b\u0002DJY\u0001\u0007\u0011\u0011\u0017\u0015\u0004Y\u0011\u0005\u0015aC:qY&$()^2lKR$\"ba\t\u0007\"\u001a\rfq\u0015DV\u0011\u001d)\t\"\fa\u0001\u000boBqA\"*.\u0001\u0004\t\t,A\u0005m_^\u0014UoY6fi\"9a\u0011V\u0017A\u0002\u0005E\u0016A\u00035jO\"\u0014UoY6fi\"9aQV\u0017A\u0002\u0005E\u0016\u0001B7bg.D3!\fCA\u0003m\u001a8-\u00197bI\r|G\u000e\\3di&|g\u000eJ7vi\u0006\u0014G.\u001a\u0013D_2d\u0017n]5p]B\u0013xn\u001c4ICNDW*\u00199%IM\u0004H.\u001b;Ck\u000e\\W\r\u001e\u000b\u000b\u0007G1)L\"/\u0007<\u001au\u0006b\u0002D\\]\u0001\u0007aqI\u0001\u0005Y&\u001cH\u000fC\u0004\u0007&:\u0002\r!!-\t\u000f\u0019%f\u00061\u0001\u00022\"9aQ\u0016\u0018A\u0002\u0005EFCCB\u0012\r\u00034\u0019M\"2\u0007H\"9Q\u0011C\u0018A\u0002\u0019U\u0004b\u0002DS_\u0001\u0007\u0011\u0011\u0017\u0005\b\rS{\u0003\u0019AAY\u0011\u001d1ik\fa\u0001\u0003c\u000bA\u0002^1cY\u0016\u001c\u0016N_3G_J$B!!-\u0007N\"9aq\u001a\u0019A\u0002\u0005E\u0016\u0001C2ba\u0006\u001c\u0017\u000e^=\u0002yM\u001c\u0017\r\\1%G>dG.Z2uS>tG%\\;uC\ndW\rJ\"pY2L7/[8o!J|wN\u001a%bg\"l\u0015\r\u001d\u0013%]\u0016<H\u000b\u001b:fg\"|G\u000e\u001a\u000b\u0005\u0003c3)\u000eC\u0004\u0006~E\u0002\r!!-\u0002\u000b\rdW-\u0019:\u0015\u0005\r\r\u0012A\u0002:f[>4X\r\u0006\u0003\u0006@\u001a}\u0007b\u0002B}g\u0001\u0007\u0011\u0011N\u0001\fgV\u0014GO]1di>sW\r\u0006\u0003\u0007\u0018\u0019\u0015\bbBC}i\u0001\u0007\u0011\u0011N\u0001\nW:|wO\\*ju\u0016\fq![:F[B$\u00180\u0006\u0003\u0007n\u001aUH\u0003BB\u0012\r_Dqa!28\u0001\u00041\t\u0010\u0005\u0005\u0002v\r%\u0017Q\u0014Dz!\u0011\tYG\">\u0005\u000f\rMwG1\u0001\u0002rU!a\u0011`D\u0001)\u0011\u0019\u0019Cb?\t\u000f\r\u0015\u0007\b1\u0001\u0007~BQ\u0011QOBp\u0003S\n\tIb@\u0011\t\u0005-t\u0011\u0001\u0003\b\u0007'D$\u0019AA9)\t\u0011)!A\u0005dY\u0006\u001c8OT1nKV\u0011AqE\u0001\u0010O\u0016$xJ]#mg\u0016,\u0006\u000fZ1uKR1\u0011\u0011QD\u0007\u000f\u001fAqA!?<\u0001\u0004\tI\u0007\u0003\u0005\b\u0012m\"\t\u0019AD\n\u00031!WMZ1vYR4\u0016\r\\;f!\u0019\t)(b<\u0002\u0002\u0006\u0019Q.\u00199\u0016\r\u001deq\u0011ED\u0014)\u00119Yb\"\u000f\u0015\t\u001duq1\u0006\t\b\u0003G\u0002qqDD\u0013!\u0011\tYg\"\t\u0005\u000f\u001d\rBH1\u0001\u0002r\t\u00111J\r\t\u0005\u0003W:9\u0003B\u0004\b*q\u0012\r!!\u001d\u0003\u0005Y\u0013\u0004bBAqy\u0001\u000fqQ\u0006\t\u0007\u0003\u0007\f\u0019nb\b)\u0011\u001d-r\u0011GD\u001c\u0005'\u0001Baa-\b4%!qQGB[\u0005AIW\u000e\u001d7jG&$hj\u001c;G_VtG-A\u0002ng\u001eDqa!2=\u0001\u00049Y\u0004\u0005\u0005\u0002v\r%\u0017QTD\u001f!!\t)(a(\b \u001d\u0015\u0012a\u00024mCRl\u0015\r]\u000b\u0007\u000f\u0007:Yeb\u0014\u0015\t\u001d\u0015sq\u000b\u000b\u0005\u000f\u000f:\t\u0006E\u0004\u0002d\u00019Ie\"\u0014\u0011\t\u0005-t1\n\u0003\b\u000fGi$\u0019AA9!\u0011\tYgb\u0014\u0005\u000f\u001d%RH1\u0001\u0002r!9\u0011\u0011]\u001fA\u0004\u001dM\u0003CBAb\u0003'<I\u0005\u000b\u0005\bR\u001dErq\u0007B\n\u0011\u001d\u0019)-\u0010a\u0001\u000f3\u0002\u0002\"!\u001e\u0004J\u0006uu1\f\t\u0007\u0003/\u00139d\"\u0018\u0011\u0011\u0005U\u0014qTD%\u000f\u001b\nqaY8mY\u0016\u001cG/\u0006\u0004\bd\u001d-tq\u000e\u000b\u0005\u000fK:9\b\u0006\u0003\bh\u001dE\u0004cBA2\u0001\u001d%tQ\u000e\t\u0005\u0003W:Y\u0007B\u0004\b$y\u0012\r!!\u001d\u0011\t\u0005-tq\u000e\u0003\b\u000fSq$\u0019AA9\u0011\u001d\t\tO\u0010a\u0002\u000fg\u0002b!a1\u0002T\u001e%\u0004\u0006CD9\u000fc99Da\u0005\t\u000f\u001ded\b1\u0001\b|\u0005\u0011\u0001O\u001a\t\t\u0003k:i(!(\b\u0002&!qqPA,\u0005=\u0001\u0016M\u001d;jC24UO\\2uS>t\u0007\u0003CA;\u0003?;Ig\"\u001c\u0002\r\r|gnY1u+\u001199i\"$\u0015\t\u001d%uq\u0012\t\b\u0003G\u0002\u0011\u0011NDF!\u0011\tYg\"$\u0005\u000f\u001d%rH1\u0001\u0006f\"9q\u0011S A\u0002\u001dM\u0015AB:vM\u001aL\u0007\u0010\u0005\u0004\u0002\u0018\n]rQ\u0013\t\t\u0003k\ny*!\u001b\b\f\u0006QA\u0005\u001d7vg\u0012\u0002H.^:\u0016\t\u001dmu\u0011\u0015\u000b\u0005\u000f;;\u0019\u000bE\u0004\u0002d\u0001\tIgb(\u0011\t\u0005-t\u0011\u0015\u0003\b\u000fS\u0001%\u0019ACs\u0011\u001d1\t\u0006\u0011a\u0001\u000fK\u0003b!a&\u00038\u001d\u001d\u0006\u0003CA;\u0003?\u000bIgb()\u0007\u0001#\t)A\u0003%a2,8/\u0006\u0003\b0\u001eUF\u0003BDY\u000fo\u0003r!a\u0019\u0001\u0003S:\u0019\f\u0005\u0003\u0002l\u001dUFaBCr\u0003\n\u0007QQ\u001d\u0005\b\u000fs\u000b\u0005\u0019AD^\u0003\tYg\u000f\u0005\u0005\u0002v\u0005}\u0015\u0011NDZQ-\tuqXDc\u000f\u000f<Ym\"4\u0011\t\u0005Ut\u0011Y\u0005\u0005\u000f\u0007\f9F\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-\t\u0002\bJ\u0006q4i\u001c8tS\u0012,'\u000f\t:fcVL'/\u001b8hA\u0005t\u0007%[7nkR\f'\r\\3!\u001b\u0006\u0004\be\u001c:!M\u0006dG\u000e\t2bG.\u0004Co\u001c\u0011NCBt3m\u001c8dCR\fQa]5oG\u0016\f#ab4\u0002\rIr\u0013g\r\u00181+\u00119\u0019n\"7\u0015\u0011\u001dUw1\\Dq\u000fK\u0004r!a\u0019\u0001\u0003S:9\u000e\u0005\u0003\u0002l\u001deGaBCr\u0005\n\u0007QQ\u001d\u0005\b\u000f;\u0014\u0005\u0019ADp\u0003\u0015)G.Z72!!\t)(a(\u0002j\u001d]\u0007bBDr\u0005\u0002\u0007qq\\\u0001\u0006K2,WN\r\u0005\b\u000fO\u0014\u0005\u0019ADu\u0003\u0015)G.Z7t!\u0019\t)hb;\b`&!qQ^A,\u0005)a$/\u001a9fCR,GM\u0010\u0015\f\u0005\u001e}vQYDy\u000f\u0017<i-\t\u0002\bt\u0006)Uk]3!W-\u0002s/\u001b;iA\u0005t\u0007%\u001a=qY&\u001c\u0017\u000e\u001e\u0011d_2dWm\u0019;j_:\u0004\u0013M]4v[\u0016tG\u000fI5ogR,\u0017\r\u001a\u0011pM\u0002Z\u0003e^5uQ\u00022\u0018M]1sON\fQ![:SK\u0012$Ba!\u0013\bz\"91\u0011A\"A\u0002\u0019U\u0004fA\"\u0005\u0002\u00069\u0011n\u001d\"mC\u000e\\G\u0003BB%\u0011\u0003Aqa!\u0001E\u0001\u00041)\bK\u0002E\t\u0003#\u0002\"!-\t\b!%\u00012\u0002\u0005\b\u0005s,\u0005\u0019AA5\u0011\u001d\u0011i0\u0012a\u0001\u0003cCqa!\u0001F\u0001\u000419\u0005K\u0002F\t\u0003C3!\u0012CE)!\t\t\fc\u0005\t\u0016!]\u0001b\u0002B}\r\u0002\u0007\u0011\u0011\u000e\u0005\b\u0005{4\u0005\u0019AAY\u0011\u001d\u0019\tA\u0012a\u0001\rkB3A\u0012CA\u0003IIgn]3si&sGo\\#ySN$\u0018N\\4\u0015\u001d\r%\u0003r\u0004E\u0012\u0011OAI\u0003c\u000b\t.!9\u0001\u0012E$A\u0002\u0019U\u0014!B0s_>$\bb\u0002E\u0013\u000f\u0002\u0007\u0011\u0011W\u0001\u0007EV\u001c7.\u001a;\t\u000f\tex\t1\u0001\u0002j!9!Q`$A\u0002\u0005E\u0006b\u0002Bq\u000f\u0002\u0007\u0011\u0011\u0011\u0005\b\u0011_9\u0005\u0019\u0001D;\u0003\u0005A\bfA$\u00042\u00061\u0011N\\:feR$Bb!\u0013\t8!e\u00022\bE\u001f\u0011\u007fAq!\"\u0005I\u0001\u00041)\bC\u0004\t&!\u0003\r!!-\t\u000f\te\b\n1\u0001\u0002j!9!Q %A\u0002\u0005E\u0006b\u0002Bq\u0011\u0002\u0007\u0011\u0011Q\u0001\u000fM&D\u0018I\u001a;fe&s7/\u001a:u)\u00191)\b#\u0012\tH!9\u0001\u0012E%A\u0002\u0019U\u0004bBB\u0001\u0013\u0002\u0007aQO\u0001\u0007I\u0016dW\r^3\u0015\u0015\u0005m\u0004R\nE(\u0011#B\u0019\u0006C\u0004\t\")\u0003\rA\"\u001e\t\u000f!\u0015\"\n1\u0001\u00022\"9!\u0011 &A\u0002\u0005%\u0004b\u0002B\u007f\u0015\u0002\u0007\u0011\u0011W\u0001\u000fM&D\u0018I\u001a;fe\u0012+G.\u001a;f)!1)\b#\u0017\t\\!u\u0003b\u0002E\u0011\u0017\u0002\u0007aQ\u000f\u0005\b\u0007\u0003Y\u0005\u0019\u0001D;\u0011\u001d\u0019yg\u0013a\u0001\rk\n!B]8uCR,G*\u001a4u)\u00191)\bc\u0019\tf!9\u0001\u0012\u0005'A\u0002\u0019U\u0004b\u0002E\u0018\u0019\u0002\u0007aQ\u000f\u0015\u0004\u0019\u0012\u0005\u0015a\u0003:pi\u0006$XMU5hQR$bA\"\u001e\tn!=\u0004b\u0002E\u0011\u001b\u0002\u0007aQ\u000f\u0005\b\u0011_i\u0005\u0019\u0001D;Q\riE\u0011Q\u0001\u000biJ\fgn\u001d9mC:$H\u0003\u0003D;\u0011oBI\b# \t\u000f!\u0005b\n1\u0001\u0007v!9\u00012\u0010(A\u0002\u0019U\u0014A\u0001;p\u0011\u001d\u0011IB\u0014a\u0001\rk\n\u0011B\u001a:p[:{G-Z:\u0015\r\u0019U\u00042\u0011ED\u0011\u001d1\tf\u0014a\u0001\u0011\u000b\u0003b!a&\u0007\u0004\u0016]\u0004bBC?\u001f\u0002\u0007\u0011\u0011\u0017")
public final class CollisionProofHashMap<K, V>
extends AbstractMap<K, V>
implements StrictOptimizedMapOps<K, V, Map, CollisionProofHashMap<K, V>> {
    private final double loadFactor;
    public final Ordering<K> scala$collection$mutable$CollisionProofHashMap$$ordering;
    public Node[] scala$collection$mutable$CollisionProofHashMap$$table;
    public int scala$collection$mutable$CollisionProofHashMap$$threshold;
    public int scala$collection$mutable$CollisionProofHashMap$$contentSize;

    public static int defaultInitialCapacity() {
        return 16;
    }

    public static double defaultLoadFactor() {
        return 0.75;
    }

    public static <K, V> Builder<Tuple2<K, V>, CollisionProofHashMap<K, V>> newBuilder(int initialCapacity, double loadFactor, Ordering<K> evidence$4) {
        return new GrowableBuilder<Tuple2<K, V>, CollisionProofHashMap<K, V>>(initialCapacity, loadFactor, evidence$4){

            public void sizeHint(int size) {
                ((CollisionProofHashMap)this.elems()).sizeHint(size);
            }
        };
    }

    public static <K, V> Builder<Tuple2<K, V>, CollisionProofHashMap<K, V>> newBuilder(Ordering<K> evidence$3) {
        return CollisionProofHashMap$.MODULE$.newBuilder(evidence$3);
    }

    public static <K, V> CollisionProofHashMap<K, V> from(IterableOnce<Tuple2<K, V>> it, Ordering<K> evidence$1) {
        return CollisionProofHashMap$.MODULE$.from((IterableOnce)it, (Ordering)evidence$1);
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
    public Tuple2<CollisionProofHashMap<K, V>, CollisionProofHashMap<K, V>> partition(Function1<Tuple2<K, V>, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<CollisionProofHashMap<K, V>, CollisionProofHashMap<K, V>> span(Function1<Tuple2<K, V>, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
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
    public Object filterImpl(Function1 pred, boolean isFlipped) {
        return StrictOptimizedIterableOps.filterImpl$(this, pred, isFlipped);
    }

    @Override
    public <A1, A2> Tuple2<Iterable<A1>, Iterable<A2>> partitionMap(Function1<Tuple2<K, V>, Either<A1, A2>> f) {
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

    private final SortedMapFactory<CollisionProofHashMap> sortedMapFactory() {
        return CollisionProofHashMap$.MODULE$;
    }

    @Override
    public int size() {
        return this.scala$collection$mutable$CollisionProofHashMap$$contentSize;
    }

    public final int scala$collection$mutable$CollisionProofHashMap$$computeHash(K o) {
        int h2 = o == null ? 0 : o.hashCode();
        return h2 ^ h2 >>> 16;
    }

    public final int scala$collection$mutable$CollisionProofHashMap$$index(int hash) {
        return hash & this.scala$collection$mutable$CollisionProofHashMap$$table.length - 1;
    }

    @Override
    public CollisionProofHashMap<K, V> fromSpecific(IterableOnce<Tuple2<K, V>> coll) {
        return CollisionProofHashMap$.MODULE$.from((IterableOnce)coll, (Ordering)this.scala$collection$mutable$CollisionProofHashMap$$ordering);
    }

    @Override
    public Builder<Tuple2<K, V>, CollisionProofHashMap<K, V>> newSpecificBuilder() {
        return CollisionProofHashMap$.MODULE$.newBuilder(this.scala$collection$mutable$CollisionProofHashMap$$ordering);
    }

    @Override
    public CollisionProofHashMap<K, V> empty() {
        return new CollisionProofHashMap<K, V>(this.scala$collection$mutable$CollisionProofHashMap$$ordering);
    }

    @Override
    public boolean contains(K key) {
        int findNode_scala$collection$mutable$CollisionProofHashMap$$computeHash_h = key == null ? 0 : key.hashCode();
        int findNode_hash = findNode_scala$collection$mutable$CollisionProofHashMap$$computeHash_h ^ findNode_scala$collection$mutable$CollisionProofHashMap$$computeHash_h >>> 16;
        Node node = this.scala$collection$mutable$CollisionProofHashMap$$table[findNode_hash & this.scala$collection$mutable$CollisionProofHashMap$$table.length - 1];
        Node node2 = node == null ? null : (node instanceof LLNode ? ((LLNode)node).getNode(key, findNode_hash, this.scala$collection$mutable$CollisionProofHashMap$$ordering) : ((RBNode)node).getNode(key, findNode_hash, this.scala$collection$mutable$CollisionProofHashMap$$ordering));
        node = null;
        return node2 != null;
    }

    @Override
    public Option<V> get(K key) {
        Object v;
        int findNode_scala$collection$mutable$CollisionProofHashMap$$computeHash_h = key == null ? 0 : key.hashCode();
        int findNode_hash = findNode_scala$collection$mutable$CollisionProofHashMap$$computeHash_h ^ findNode_scala$collection$mutable$CollisionProofHashMap$$computeHash_h >>> 16;
        Node node = this.scala$collection$mutable$CollisionProofHashMap$$table[findNode_hash & this.scala$collection$mutable$CollisionProofHashMap$$table.length - 1];
        Node node2 = node == null ? null : (node instanceof LLNode ? ((LLNode)node).getNode(key, findNode_hash, this.scala$collection$mutable$CollisionProofHashMap$$ordering) : ((RBNode)node).getNode(key, findNode_hash, this.scala$collection$mutable$CollisionProofHashMap$$ordering));
        node = null;
        LLNode lLNode = node2;
        if (lLNode == null) {
            return None$.MODULE$;
        }
        if (lLNode instanceof LLNode) {
            v = lLNode.value();
        } else if (lLNode instanceof RBNode) {
            v = ((RBNode)((Object)lLNode)).value();
        } else {
            throw new MatchError(lLNode);
        }
        return new Some(v);
    }

    @Override
    public V apply(K key) throws NoSuchElementException {
        int findNode_scala$collection$mutable$CollisionProofHashMap$$computeHash_h = key == null ? 0 : key.hashCode();
        int findNode_hash = findNode_scala$collection$mutable$CollisionProofHashMap$$computeHash_h ^ findNode_scala$collection$mutable$CollisionProofHashMap$$computeHash_h >>> 16;
        Node node = this.scala$collection$mutable$CollisionProofHashMap$$table[findNode_hash & this.scala$collection$mutable$CollisionProofHashMap$$table.length - 1];
        Node node2 = node == null ? null : (node instanceof LLNode ? ((LLNode)node).getNode(key, findNode_hash, this.scala$collection$mutable$CollisionProofHashMap$$ordering) : ((RBNode)node).getNode(key, findNode_hash, this.scala$collection$mutable$CollisionProofHashMap$$ordering));
        node = null;
        LLNode lLNode = node2;
        if (lLNode == null) {
            return (V)MapOps.default$(this, key);
        }
        if (lLNode instanceof LLNode) {
            return lLNode.value();
        }
        if (lLNode instanceof RBNode) {
            return ((RBNode)((Object)lLNode)).value();
        }
        throw new MatchError(lLNode);
    }

    @Override
    public <V1> V1 getOrElse(K key, Function0<V1> function0) {
        int findNode_scala$collection$mutable$CollisionProofHashMap$$computeHash_h = key == null ? 0 : key.hashCode();
        int findNode_hash = findNode_scala$collection$mutable$CollisionProofHashMap$$computeHash_h ^ findNode_scala$collection$mutable$CollisionProofHashMap$$computeHash_h >>> 16;
        Node node = this.scala$collection$mutable$CollisionProofHashMap$$table[findNode_hash & this.scala$collection$mutable$CollisionProofHashMap$$table.length - 1];
        Node node2 = node == null ? null : (node instanceof LLNode ? ((LLNode)node).getNode(key, findNode_hash, this.scala$collection$mutable$CollisionProofHashMap$$ordering) : ((RBNode)node).getNode(key, findNode_hash, this.scala$collection$mutable$CollisionProofHashMap$$ordering));
        node = null;
        LLNode nd = node2;
        if (nd == null) {
            return function0.apply();
        }
        if (nd instanceof LLNode) {
            return (V1)nd.value();
        }
        return (V1)((RBNode)((Object)nd)).value();
    }

    private Node findNode(K elem) {
        int scala$collection$mutable$CollisionProofHashMap$$computeHash_h = elem == null ? 0 : elem.hashCode();
        int hash = scala$collection$mutable$CollisionProofHashMap$$computeHash_h ^ scala$collection$mutable$CollisionProofHashMap$$computeHash_h >>> 16;
        Node node = this.scala$collection$mutable$CollisionProofHashMap$$table[hash & this.scala$collection$mutable$CollisionProofHashMap$$table.length - 1];
        if (node == null) {
            return null;
        }
        if (node instanceof LLNode) {
            return ((LLNode)node).getNode(elem, hash, this.scala$collection$mutable$CollisionProofHashMap$$ordering);
        }
        return ((RBNode)node).getNode(elem, hash, this.scala$collection$mutable$CollisionProofHashMap$$ordering);
    }

    @Override
    public void sizeHint(int size) {
        int target = this.tableSizeFor((int)((double)(size + 1) / this.loadFactor));
        if (target > this.scala$collection$mutable$CollisionProofHashMap$$table.length) {
            if (size == 0) {
                this.scala$collection$mutable$CollisionProofHashMap$$table = new Node[target];
                this.scala$collection$mutable$CollisionProofHashMap$$threshold = this.scala$collection$mutable$CollisionProofHashMap$$newThreshold(this.scala$collection$mutable$CollisionProofHashMap$$table.length);
                return;
            }
            this.scala$collection$mutable$CollisionProofHashMap$$growTable(target);
            return;
        }
    }

    @Override
    public void update(K key, V value) {
        boolean put0_getOld = false;
        if (this.scala$collection$mutable$CollisionProofHashMap$$contentSize + 1 >= this.scala$collection$mutable$CollisionProofHashMap$$threshold) {
            this.scala$collection$mutable$CollisionProofHashMap$$growTable(this.scala$collection$mutable$CollisionProofHashMap$$table.length * 2);
        }
        int put0_scala$collection$mutable$CollisionProofHashMap$$computeHash_h = key == null ? 0 : key.hashCode();
        int put0_hash = put0_scala$collection$mutable$CollisionProofHashMap$$computeHash_h ^ put0_scala$collection$mutable$CollisionProofHashMap$$computeHash_h >>> 16;
        int put0_idx = put0_hash & this.scala$collection$mutable$CollisionProofHashMap$$table.length - 1;
        this.scala$collection$mutable$CollisionProofHashMap$$put0(key, value, put0_getOld, put0_hash, put0_idx);
    }

    @Override
    public Option<V> put(K key, V value) {
        int put0_idx;
        int put0_scala$collection$mutable$CollisionProofHashMap$$computeHash_h;
        int put0_hash;
        Some<V> some;
        boolean put0_getOld = true;
        if (this.scala$collection$mutable$CollisionProofHashMap$$contentSize + 1 >= this.scala$collection$mutable$CollisionProofHashMap$$threshold) {
            this.scala$collection$mutable$CollisionProofHashMap$$growTable(this.scala$collection$mutable$CollisionProofHashMap$$table.length * 2);
        }
        if ((some = this.scala$collection$mutable$CollisionProofHashMap$$put0(key, value, put0_getOld, put0_hash = (put0_scala$collection$mutable$CollisionProofHashMap$$computeHash_h = key == null ? 0 : key.hashCode()) ^ put0_scala$collection$mutable$CollisionProofHashMap$$computeHash_h >>> 16, put0_idx = put0_hash & this.scala$collection$mutable$CollisionProofHashMap$$table.length - 1)) == null) {
            return None$.MODULE$;
        }
        return some;
    }

    /*
     * WARNING - void declaration
     */
    public CollisionProofHashMap<K, V> addOne(Tuple2<K, V> elem) {
        void put0_getOld;
        boolean bl = false;
        V put0_value = elem._2();
        K put0_key = elem._1();
        if (this.scala$collection$mutable$CollisionProofHashMap$$contentSize + 1 >= this.scala$collection$mutable$CollisionProofHashMap$$threshold) {
            this.scala$collection$mutable$CollisionProofHashMap$$growTable(this.scala$collection$mutable$CollisionProofHashMap$$table.length * 2);
        }
        int put0_scala$collection$mutable$CollisionProofHashMap$$computeHash_h = put0_key == null ? 0 : put0_key.hashCode();
        int put0_hash = put0_scala$collection$mutable$CollisionProofHashMap$$computeHash_h ^ put0_scala$collection$mutable$CollisionProofHashMap$$computeHash_h >>> 16;
        int put0_idx = put0_hash & this.scala$collection$mutable$CollisionProofHashMap$$table.length - 1;
        this.scala$collection$mutable$CollisionProofHashMap$$put0(put0_key, put0_value, (boolean)put0_getOld, put0_hash, put0_idx);
        return this;
    }

    private Some<V> put0(K key, V value, boolean getOld) {
        if (this.scala$collection$mutable$CollisionProofHashMap$$contentSize + 1 >= this.scala$collection$mutable$CollisionProofHashMap$$threshold) {
            this.scala$collection$mutable$CollisionProofHashMap$$growTable(this.scala$collection$mutable$CollisionProofHashMap$$table.length * 2);
        }
        int scala$collection$mutable$CollisionProofHashMap$$computeHash_h = key == null ? 0 : key.hashCode();
        int hash = scala$collection$mutable$CollisionProofHashMap$$computeHash_h ^ scala$collection$mutable$CollisionProofHashMap$$computeHash_h >>> 16;
        int idx = hash & this.scala$collection$mutable$CollisionProofHashMap$$table.length - 1;
        return this.scala$collection$mutable$CollisionProofHashMap$$put0(key, value, getOld, hash, idx);
    }

    public Some<V> scala$collection$mutable$CollisionProofHashMap$$put0(K key, V value, boolean getOld, int hash, int idx) {
        boolean res;
        boolean bl;
        Node node = this.scala$collection$mutable$CollisionProofHashMap$$table[idx];
        if (node instanceof RBNode) {
            RBNode rBNode = (RBNode)node;
            bl = this.insert(rBNode, idx, key, hash, value);
        } else {
            LLNode<K, V> old = (LLNode<K, V>)node;
            if (old == null) {
                this.scala$collection$mutable$CollisionProofHashMap$$table[idx] = new LLNode<K, V>(key, hash, value, null);
            } else {
                int remaining = 8;
                LLNode<K, V> prev = null;
                for (LLNode<K, V> n = old; n != null && n.hash() <= hash && remaining > 0; n = n.next(), --remaining) {
                    if (n.hash() == hash && BoxesRunTime.equals(key, n.key())) {
                        Object old2 = n.value();
                        n.value_$eq(value);
                        if (getOld) {
                            return new Some(old2);
                        }
                        return null;
                    }
                    prev = n;
                }
                if (remaining == 0) {
                    this.treeify(old, idx);
                    return this.scala$collection$mutable$CollisionProofHashMap$$put0(key, value, getOld, hash, idx);
                }
                if (prev == null) {
                    this.scala$collection$mutable$CollisionProofHashMap$$table[idx] = new LLNode<K, V>(key, hash, value, old);
                } else {
                    prev.next_$eq(new LLNode<K, V>(key, hash, value, prev.next()));
                }
            }
            bl = res = true;
        }
        if (res) {
            ++this.scala$collection$mutable$CollisionProofHashMap$$contentSize;
        }
        if (res) {
            return new Some<Object>(null);
        }
        return null;
    }

    /*
     * WARNING - void declaration
     */
    private void treeify(LLNode<K, V> old, int idx) {
        void scala$collection$mutable$CollisionProofHashMap$$leaf_red;
        void scala$collection$mutable$CollisionProofHashMap$$leaf_value;
        void scala$collection$mutable$CollisionProofHashMap$$leaf_hash;
        boolean bl = false;
        V v = old.value();
        int n = old.hash();
        K scala$collection$mutable$CollisionProofHashMap$$leaf_key = old.key();
        Object var5_6 = null;
        v = null;
        this.scala$collection$mutable$CollisionProofHashMap$$table[idx] = new RBNode<K, void>(scala$collection$mutable$CollisionProofHashMap$$leaf_key, (int)scala$collection$mutable$CollisionProofHashMap$$leaf_hash, scala$collection$mutable$CollisionProofHashMap$$leaf_value, (boolean)scala$collection$mutable$CollisionProofHashMap$$leaf_red, null, null, null);
        for (LLNode<K, V> n2 = old.next(); n2 != null; n2 = n2.next()) {
            RBNode root = (RBNode)this.scala$collection$mutable$CollisionProofHashMap$$table[idx];
            this.insertIntoExisting(root, idx, n2.key(), n2.hash(), n2.value(), root);
        }
    }

    public CollisionProofHashMap<K, V> addAll(IterableOnce<Tuple2<K, V>> xs) {
        int k = xs.knownSize();
        if (k > 0) {
            this.sizeHint(this.scala$collection$mutable$CollisionProofHashMap$$contentSize + k);
        }
        return (CollisionProofHashMap)Growable.addAll$(this, xs);
    }

    private Object remove0(K elem) {
        int scala$collection$mutable$CollisionProofHashMap$$computeHash_h = elem == null ? 0 : elem.hashCode();
        int hash = scala$collection$mutable$CollisionProofHashMap$$computeHash_h ^ scala$collection$mutable$CollisionProofHashMap$$computeHash_h >>> 16;
        int idx = hash & this.scala$collection$mutable$CollisionProofHashMap$$table.length - 1;
        boolean bl = false;
        LLNode lLNode = null;
        Node node = this.scala$collection$mutable$CollisionProofHashMap$$table[idx];
        if (node == null) {
            return Statics.pfMarker;
        }
        if (node instanceof RBNode) {
            RBNode rBNode = (RBNode)node;
            Object v = this.delete(rBNode, idx, elem, hash);
            if (v != Statics.pfMarker) {
                --this.scala$collection$mutable$CollisionProofHashMap$$contentSize;
            }
            return v;
        }
        if (node instanceof LLNode) {
            bl = true;
            lLNode = (LLNode)node;
            if (lLNode.hash() == hash && BoxesRunTime.equals(lLNode.key(), elem)) {
                this.scala$collection$mutable$CollisionProofHashMap$$table[idx] = lLNode.next();
                --this.scala$collection$mutable$CollisionProofHashMap$$contentSize;
                return lLNode.value();
            }
        }
        if (bl) {
            LLNode prev = lLNode;
            for (LLNode next = lLNode.next(); next != null && next.hash() <= hash; next = next.next()) {
                if (next.hash() == hash && BoxesRunTime.equals(next.key(), elem)) {
                    prev.next_$eq(next.next());
                    --this.scala$collection$mutable$CollisionProofHashMap$$contentSize;
                    return next.value();
                }
                prev = next;
            }
            return Statics.pfMarker;
        }
        throw new MatchError(node);
    }

    @Override
    public Iterator<K> keysIterator() {
        if (this.isEmpty()) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        return new MapIterator<K>(this){

            public K extract(LLNode<K, V> node) {
                return node.key();
            }

            public K extract(RBNode<K, V> node) {
                return node.key();
            }
        };
    }

    @Override
    public Iterator<Tuple2<K, V>> iterator() {
        if (this.isEmpty()) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        return new MapIterator<Tuple2<K, V>>(this){

            public Tuple2<K, V> extract(LLNode<K, V> node) {
                return new Tuple2<K, V>(node.key(), node.value());
            }

            public Tuple2<K, V> extract(RBNode<K, V> node) {
                return new Tuple2<K, V>(node.key(), node.value());
            }
        };
    }

    public void scala$collection$mutable$CollisionProofHashMap$$growTable(int newlen) {
        this.scala$collection$mutable$CollisionProofHashMap$$table = Arrays.copyOf(this.scala$collection$mutable$CollisionProofHashMap$$table, newlen);
        this.scala$collection$mutable$CollisionProofHashMap$$threshold = this.scala$collection$mutable$CollisionProofHashMap$$newThreshold(this.scala$collection$mutable$CollisionProofHashMap$$table.length);
        for (int oldlen = this.scala$collection$mutable$CollisionProofHashMap$$table.length; oldlen < newlen; oldlen *= 2) {
            for (int i = 0; i < oldlen; ++i) {
                RBNode rBNode;
                LLNode lLNode;
                Node old = this.scala$collection$mutable$CollisionProofHashMap$$table[i];
                if (old == null) continue;
                int splitBucket_highBucket = i + oldlen;
                if (old instanceof LLNode) {
                    lLNode = (LLNode)old;
                    this.scala$collection$mutable$CollisionProofHashMap$$splitBucket(lLNode, i, splitBucket_highBucket, oldlen);
                } else if (old instanceof RBNode) {
                    rBNode = (RBNode)old;
                    this.scala$collection$mutable$CollisionProofHashMap$$splitBucket(rBNode, i, splitBucket_highBucket, oldlen);
                } else {
                    throw new MatchError(old);
                }
                lLNode = null;
                rBNode = null;
            }
        }
    }

    private void reallocTable(int newlen) {
        this.scala$collection$mutable$CollisionProofHashMap$$table = new Node[newlen];
        this.scala$collection$mutable$CollisionProofHashMap$$threshold = this.scala$collection$mutable$CollisionProofHashMap$$newThreshold(this.scala$collection$mutable$CollisionProofHashMap$$table.length);
    }

    private void splitBucket(Node tree, int lowBucket, int highBucket, int mask) {
        if (tree instanceof LLNode) {
            LLNode lLNode = (LLNode)tree;
            this.scala$collection$mutable$CollisionProofHashMap$$splitBucket(lLNode, lowBucket, highBucket, mask);
            return;
        }
        if (tree instanceof RBNode) {
            RBNode rBNode = (RBNode)tree;
            this.scala$collection$mutable$CollisionProofHashMap$$splitBucket(rBNode, lowBucket, highBucket, mask);
            return;
        }
        throw new MatchError(tree);
    }

    public void scala$collection$mutable$CollisionProofHashMap$$splitBucket(LLNode<K, V> list, int lowBucket, int highBucket, int mask) {
        LLNode<Object, Object> preLow = new LLNode<Object, Object>(null, 0, null, null);
        LLNode<Object, Object> preHigh = new LLNode<Object, Object>(null, 0, null, null);
        LLNode<Object, Object> lastLow = preLow;
        LLNode<Object, Object> lastHigh = preHigh;
        LLNode<K, V> n = list;
        while (n != null) {
            LLNode<K, V> next = n.next();
            if ((n.hash() & mask) == 0) {
                lastLow.next_$eq(n);
                lastLow = n;
            } else {
                lastHigh.next_$eq(n);
                lastHigh = n;
            }
            n = next;
        }
        lastLow.next_$eq(null);
        if (list != preLow.next()) {
            this.scala$collection$mutable$CollisionProofHashMap$$table[lowBucket] = preLow.next();
        }
        if (preHigh.next() != null) {
            this.scala$collection$mutable$CollisionProofHashMap$$table[highBucket] = preHigh.next();
            lastHigh.next_$eq(null);
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    public void scala$collection$mutable$CollisionProofHashMap$$splitBucket(RBNode<K, V> tree, int lowBucket, int highBucket, int mask) {
        int create_e = 0;
        IntRef lowCount = new IntRef(create_e);
        int create_e2 = 0;
        IntRef highCount = new IntRef(create_e2);
        RBNode<K, V> rBNode = tree;
        Function1<RBNode, Object> & Serializable intersect = (Function1<RBNode, Object> & Serializable)n -> {
            CollisionProofHashMap.$anonfun$scala$collection$mutable$CollisionProofHashMap$$splitBucket$1(mask, highCount, lowCount, n);
            return BoxedUnit.UNIT;
        };
        if (rBNode == null) {
            throw null;
        }
        RBNode<K, V> foreachNode_this = rBNode;
        while (true) {
            if (foreachNode_this.left() != null) {
                void foreachNode_f;
                foreachNode_this.left().foreachNode(foreachNode_f);
            }
            if ((foreachNode_this.hash() & mask) != 0) {
                ++highCount.elem;
            } else {
                ++lowCount.elem;
            }
            if (foreachNode_this.right() == null) break;
            foreachNode_this = foreachNode_this.right();
        }
        Object var7_10 = null;
        intersect = null;
        if (highCount.elem != 0) {
            if (lowCount.elem == 0) {
                this.scala$collection$mutable$CollisionProofHashMap$$table[lowBucket] = null;
                this.scala$collection$mutable$CollisionProofHashMap$$table[highBucket] = tree;
                return;
            }
            this.scala$collection$mutable$CollisionProofHashMap$$table[lowBucket] = this.fromNodes(new RBNodesIterator<K, V>(tree, this.scala$collection$mutable$CollisionProofHashMap$$ordering).filterImpl((Function1<RBNode, Object> & Serializable)(A n) -> BoxesRunTime.boxToBoolean(CollisionProofHashMap.$anonfun$scala$collection$mutable$CollisionProofHashMap$$splitBucket$2(mask, n)), false), lowCount.elem);
            this.scala$collection$mutable$CollisionProofHashMap$$table[highBucket] = this.fromNodes(new RBNodesIterator<K, V>(tree, this.scala$collection$mutable$CollisionProofHashMap$$ordering).filterImpl((Function1<RBNode, Object> & Serializable)(A n) -> BoxesRunTime.boxToBoolean(CollisionProofHashMap.$anonfun$scala$collection$mutable$CollisionProofHashMap$$splitBucket$3(mask, n)), false), highCount.elem);
            return;
        }
    }

    private int tableSizeFor(int capacity) {
        int n = capacity - 1;
        int max$extension_that = 4;
        int n2 = Integer.highestOneBit(Math.max(n, max$extension_that)) * 2;
        int min$extension_that = 0x40000000;
        return Math.min(n2, min$extension_that);
    }

    public int scala$collection$mutable$CollisionProofHashMap$$newThreshold(int size) {
        return (int)((double)size * this.loadFactor);
    }

    @Override
    public void clear() {
        Arrays.fill(this.scala$collection$mutable$CollisionProofHashMap$$table, null);
        this.scala$collection$mutable$CollisionProofHashMap$$contentSize = 0;
    }

    @Override
    public Option<V> remove(K key) {
        Object v = this.remove0(key);
        if (v == Statics.pfMarker) {
            return None$.MODULE$;
        }
        return new Some<Object>(v);
    }

    public CollisionProofHashMap<K, V> subtractOne(K elem) {
        this.remove0(elem);
        return this;
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
    public <U> void foreach(Function1<Tuple2<K, V>, U> f) {
        for (Node n : this.scala$collection$mutable$CollisionProofHashMap$$table) {
            if (n == null) continue;
            if (n instanceof LLNode) {
                LLNode foreach_this = (LLNode)n;
                while (true) {
                    f.apply(new Tuple2(foreach_this.key(), foreach_this.value()));
                    if (foreach_this.next() == null) break;
                    foreach_this = foreach_this.next();
                }
                Object var5_5 = null;
                continue;
            }
            if (n instanceof RBNode) {
                RBNode foreach_this = (RBNode)n;
                while (true) {
                    if (foreach_this.left() != null) {
                        foreach_this.left().foreach(f);
                    }
                    f.apply(new Tuple2(foreach_this.key(), foreach_this.value()));
                    if (foreach_this.right() == null) break;
                    foreach_this = foreach_this.right();
                }
                Object var6_6 = null;
                continue;
            }
            throw new MatchError(n);
        }
    }

    @Override
    public <U> void foreachEntry(Function2<K, V, U> f) {
        for (Node n : this.scala$collection$mutable$CollisionProofHashMap$$table) {
            if (n == null) continue;
            if (n instanceof LLNode) {
                LLNode foreachEntry_this = (LLNode)n;
                while (true) {
                    f.apply(foreachEntry_this.key(), foreachEntry_this.value());
                    if (foreachEntry_this.next() == null) break;
                    foreachEntry_this = foreachEntry_this.next();
                }
                Object var5_5 = null;
                continue;
            }
            if (n instanceof RBNode) {
                RBNode foreachEntry_this = (RBNode)n;
                while (true) {
                    if (foreachEntry_this.left() != null) {
                        foreachEntry_this.left().foreachEntry(f);
                    }
                    f.apply(foreachEntry_this.key(), foreachEntry_this.value());
                    if (foreachEntry_this.right() == null) break;
                    foreachEntry_this = foreachEntry_this.right();
                }
                Object var6_6 = null;
                continue;
            }
            throw new MatchError(n);
        }
    }

    public Object writeReplace() {
        return new DefaultSerializationProxy<K>(new DeserializationFactory(this.scala$collection$mutable$CollisionProofHashMap$$table.length, this.loadFactor, this.scala$collection$mutable$CollisionProofHashMap$$ordering), this);
    }

    @Override
    public String className() {
        return "CollisionProofHashMap";
    }

    @Override
    public V getOrElseUpdate(K key, Function0<V> defaultValue) {
        int scala$collection$mutable$CollisionProofHashMap$$computeHash_h = key == null ? 0 : key.hashCode();
        int hash = scala$collection$mutable$CollisionProofHashMap$$computeHash_h ^ scala$collection$mutable$CollisionProofHashMap$$computeHash_h >>> 16;
        int idx = hash & this.scala$collection$mutable$CollisionProofHashMap$$table.length - 1;
        Node node = this.scala$collection$mutable$CollisionProofHashMap$$table[idx];
        if (node != null) {
            if (node instanceof LLNode) {
                LLNode nd = ((LLNode)node).getNode(key, hash, this.scala$collection$mutable$CollisionProofHashMap$$ordering);
                if (nd != null) {
                    return nd.value();
                }
            } else {
                RBNode nd = ((RBNode)node).getNode(key, hash, this.scala$collection$mutable$CollisionProofHashMap$$ordering);
                if (nd != null) {
                    return nd.value();
                }
            }
        }
        Node[] table0 = this.scala$collection$mutable$CollisionProofHashMap$$table;
        V v = defaultValue.apply();
        if (this.scala$collection$mutable$CollisionProofHashMap$$contentSize + 1 >= this.scala$collection$mutable$CollisionProofHashMap$$threshold) {
            this.scala$collection$mutable$CollisionProofHashMap$$growTable(this.scala$collection$mutable$CollisionProofHashMap$$table.length * 2);
        }
        int newIdx = table0 == this.scala$collection$mutable$CollisionProofHashMap$$table ? idx : hash & this.scala$collection$mutable$CollisionProofHashMap$$table.length - 1;
        this.scala$collection$mutable$CollisionProofHashMap$$put0(key, v, false, hash, newIdx);
        return v;
    }

    public <K2, V2> CollisionProofHashMap<K2, V2> map(Function1<Tuple2<K, V>, Tuple2<K2, V2>> f, Ordering<K2> ordering) {
        return CollisionProofHashMap$.MODULE$.from(new View.Map<Tuple2<K, V>, Tuple2<K2, V2>>(this, f), ordering);
    }

    public <K2, V2> CollisionProofHashMap<K2, V2> flatMap(Function1<Tuple2<K, V>, IterableOnce<Tuple2<K2, V2>>> f, Ordering<K2> ordering) {
        return CollisionProofHashMap$.MODULE$.from(new View.FlatMap<Tuple2<K, V>, Tuple2<K2, V2>>(this, f), ordering);
    }

    public <K2, V2> CollisionProofHashMap<K2, V2> collect(PartialFunction<Tuple2<K, V>, Tuple2<K2, V2>> pf, Ordering<K2> ordering) {
        return CollisionProofHashMap$.MODULE$.from(new View.Collect<Tuple2<K, V>, Tuple2<K2, V2>>(this, pf), ordering);
    }

    @Override
    public <V2> CollisionProofHashMap<K, V2> concat(IterableOnce<Tuple2<K, V2>> suffix) {
        IterableOnce<Object> iterableOnce;
        if (suffix instanceof Iterable) {
            Iterable iterable = (Iterable)suffix;
            iterableOnce = new View.Concat(this, iterable);
        } else {
            iterableOnce = this.iterator().concat((Function0<Iterator> & Serializable)() -> suffix.iterator());
        }
        return CollisionProofHashMap$.MODULE$.from(iterableOnce, (Ordering)this.scala$collection$mutable$CollisionProofHashMap$$ordering);
    }

    @Override
    public final <V2> CollisionProofHashMap<K, V2> $plus$plus(IterableOnce<Tuple2<K, V2>> xs) {
        return this.concat((IterableOnce)xs);
    }

    @Override
    public <V1> CollisionProofHashMap<K, V1> $plus(Tuple2<K, V1> kv) {
        return CollisionProofHashMap$.MODULE$.from(new View.Appended<Tuple2<K, V1>>(this, kv), (Ordering)this.scala$collection$mutable$CollisionProofHashMap$$ordering);
    }

    @Override
    public <V1> CollisionProofHashMap<K, V1> $plus(Tuple2<K, V1> elem1, Tuple2<K, V1> elem2, Seq<Tuple2<K, V1>> elems) {
        return CollisionProofHashMap$.MODULE$.from((IterableOnce)new View.Concat(new View.Appended<Tuple2<K, V1>>(new View.Appended<Tuple2<K, V1>>(this, elem1), elem2), elems), (Ordering)this.scala$collection$mutable$CollisionProofHashMap$$ordering);
    }

    private boolean isRed(RBNode<K, V> node) {
        return node != null && node.red();
    }

    private boolean isBlack(RBNode<K, V> node) {
        return node == null || !node.red();
    }

    private int compare(K key, int hash, LLNode<K, V> node) {
        int i = hash - node.hash();
        if (i != 0) {
            return i;
        }
        return this.scala$collection$mutable$CollisionProofHashMap$$ordering.compare(key, node.key());
    }

    private int compare(K key, int hash, RBNode<K, V> node) {
        return this.scala$collection$mutable$CollisionProofHashMap$$ordering.compare(key, node.key());
    }

    private final boolean insertIntoExisting(RBNode<K, V> _root, int bucket, K key, int hash, V value, RBNode<K, V> x) {
        while (true) {
            RBNode<K, V> next;
            int cmp;
            if ((cmp = this.scala$collection$mutable$CollisionProofHashMap$$ordering.compare(key, x.key())) == 0) {
                x.value_$eq(value);
                return false;
            }
            RBNode<K, V> rBNode = next = cmp < 0 ? x.left() : x.right();
            if (next == null) {
                boolean scala$collection$mutable$CollisionProofHashMap$$leaf_red = true;
                RBNode<K, V> z = new RBNode<K, V>(key, hash, value, scala$collection$mutable$CollisionProofHashMap$$leaf_red, null, null, x);
                if (cmp < 0) {
                    x.left_$eq(z);
                } else {
                    x.right_$eq(z);
                }
                this.scala$collection$mutable$CollisionProofHashMap$$table[bucket] = this.fixAfterInsert(_root, z);
                return true;
            }
            x = next;
        }
    }

    private final boolean insert(RBNode<K, V> tree, int bucket, K key, int hash, V value) {
        if (tree == null) {
            boolean scala$collection$mutable$CollisionProofHashMap$$leaf_red = false;
            this.scala$collection$mutable$CollisionProofHashMap$$table[bucket] = new RBNode<K, V>(key, hash, value, scala$collection$mutable$CollisionProofHashMap$$leaf_red, null, null, null);
            return true;
        }
        return this.insertIntoExisting(tree, bucket, key, hash, value, tree);
    }

    private RBNode<K, V> fixAfterInsert(RBNode<K, V> _root, RBNode<K, V> node) {
        RBNode<K, V> root = _root;
        RBNode<K, V> z = node;
        while (true) {
            Object var7_7 = null;
            RBNode<K, V> isRed_node = z.parent();
            if (!(isRed_node != null && isRed_node.red())) break;
            if (z.parent() == z.parent().parent().left()) {
                RBNode<K, V> y = z.parent().parent().right();
                if (y != null && y.red()) {
                    z.parent().red_$eq(false);
                    y.red_$eq(false);
                    z.parent().parent().red_$eq(true);
                    z = z.parent().parent();
                    continue;
                }
                if (z == z.parent().right()) {
                    z = z.parent();
                    RBNode<K, V> rotateLeft_root = root;
                    RBNode<K, V> rotateLeft_y = z.right();
                    z.right_$eq(rotateLeft_y.left());
                    RBNode<K, V> rotateLeft_xp = z.parent();
                    if (rotateLeft_y.left() != null) {
                        rotateLeft_y.left().parent_$eq(z);
                    }
                    rotateLeft_y.parent_$eq(rotateLeft_xp);
                    if (rotateLeft_xp == null) {
                        rotateLeft_root = rotateLeft_y;
                    } else if (z == rotateLeft_xp.left()) {
                        rotateLeft_xp.left_$eq(rotateLeft_y);
                    } else {
                        rotateLeft_xp.right_$eq(rotateLeft_y);
                    }
                    rotateLeft_y.left_$eq(z);
                    z.parent_$eq(rotateLeft_y);
                    Object var8_8 = null;
                    Object var9_9 = null;
                    Object var10_10 = null;
                    root = rotateLeft_root;
                }
                z.parent().red_$eq(false);
                z.parent().parent().red_$eq(true);
                RBNode<K, V> rotateRight_x = z.parent().parent();
                RBNode<K, V> rotateRight_root = root;
                RBNode<K, V> rotateRight_y = rotateRight_x.left();
                rotateRight_x.left_$eq(rotateRight_y.right());
                RBNode<K, V> rotateRight_xp = rotateRight_x.parent();
                if (rotateRight_y.right() != null) {
                    rotateRight_y.right().parent_$eq(rotateRight_x);
                }
                rotateRight_y.parent_$eq(rotateRight_xp);
                if (rotateRight_xp == null) {
                    rotateRight_root = rotateRight_y;
                } else if (rotateRight_x == rotateRight_xp.right()) {
                    rotateRight_xp.right_$eq(rotateRight_y);
                } else {
                    rotateRight_xp.left_$eq(rotateRight_y);
                }
                rotateRight_y.right_$eq(rotateRight_x);
                rotateRight_x.parent_$eq(rotateRight_y);
                Object var11_11 = null;
                Object var12_12 = null;
                Object var13_13 = null;
                Object var14_14 = null;
                root = rotateRight_root;
                continue;
            }
            RBNode<K, V> y = z.parent().parent().left();
            if (y != null && y.red()) {
                z.parent().red_$eq(false);
                y.red_$eq(false);
                z.parent().parent().red_$eq(true);
                z = z.parent().parent();
                continue;
            }
            if (z == z.parent().left()) {
                z = z.parent();
                RBNode<K, V> rotateRight_root = root;
                RBNode<K, V> rotateRight_y = z.left();
                z.left_$eq(rotateRight_y.right());
                RBNode<K, V> rotateRight_xp = z.parent();
                if (rotateRight_y.right() != null) {
                    rotateRight_y.right().parent_$eq(z);
                }
                rotateRight_y.parent_$eq(rotateRight_xp);
                if (rotateRight_xp == null) {
                    rotateRight_root = rotateRight_y;
                } else if (z == rotateRight_xp.right()) {
                    rotateRight_xp.right_$eq(rotateRight_y);
                } else {
                    rotateRight_xp.left_$eq(rotateRight_y);
                }
                rotateRight_y.right_$eq(z);
                z.parent_$eq(rotateRight_y);
                Object var15_15 = null;
                Object var16_16 = null;
                Object var17_17 = null;
                root = rotateRight_root;
            }
            z.parent().red_$eq(false);
            z.parent().parent().red_$eq(true);
            RBNode<K, V> rotateLeft_x = z.parent().parent();
            RBNode<K, V> rotateLeft_root = root;
            RBNode<K, V> rotateLeft_y = rotateLeft_x.right();
            rotateLeft_x.right_$eq(rotateLeft_y.left());
            RBNode<K, V> rotateLeft_xp = rotateLeft_x.parent();
            if (rotateLeft_y.left() != null) {
                rotateLeft_y.left().parent_$eq(rotateLeft_x);
            }
            rotateLeft_y.parent_$eq(rotateLeft_xp);
            if (rotateLeft_xp == null) {
                rotateLeft_root = rotateLeft_y;
            } else if (rotateLeft_x == rotateLeft_xp.left()) {
                rotateLeft_xp.left_$eq(rotateLeft_y);
            } else {
                rotateLeft_xp.right_$eq(rotateLeft_y);
            }
            rotateLeft_y.left_$eq(rotateLeft_x);
            rotateLeft_x.parent_$eq(rotateLeft_y);
            Object var18_18 = null;
            Object var19_19 = null;
            Object var20_20 = null;
            Object var21_21 = null;
            root = rotateLeft_root;
        }
        root.red_$eq(false);
        return root;
    }

    private Object delete(RBNode<K, V> _root, int bucket, K key, int hash) {
        RBNode<K, V> root = _root;
        RBNode<K, V> z = _root.getNode(key, hash, this.scala$collection$mutable$CollisionProofHashMap$$ordering);
        if (z != null) {
            RBNode<K, V> xParent;
            RBNode<K, V> x;
            V oldValue = z.value();
            boolean yIsRed = z.red();
            if (z.left() == null) {
                x = z.right();
                root = this.transplant(_root, z, z.right());
                xParent = z.parent();
            } else if (z.right() == null) {
                x = z.left();
                root = this.transplant(_root, z, z.left());
                xParent = z.parent();
            } else {
                RBNode<K, V> y = CollisionProofHashMap$.MODULE$.scala$collection$mutable$CollisionProofHashMap$$minNodeNonNull(z.right());
                yIsRed = y.red();
                x = y.right();
                if (y.parent() == z) {
                    xParent = y;
                } else {
                    xParent = y.parent();
                    root = this.transplant(_root, y, y.right());
                    y.right_$eq(z.right());
                    y.right().parent_$eq(y);
                }
                root = this.transplant(root, z, y);
                y.left_$eq(z.left());
                y.left().parent_$eq(y);
                y.red_$eq(z.red());
            }
            if (!yIsRed) {
                root = this.fixAfterDelete(root, x, xParent);
            }
            if (root != _root) {
                this.scala$collection$mutable$CollisionProofHashMap$$table[bucket] = root;
            }
            return oldValue;
        }
        return Statics.pfMarker;
    }

    /*
     * Unable to fully structure code
     */
    private RBNode<K, V> fixAfterDelete(RBNode<K, V> _root, RBNode<K, V> node, RBNode<K, V> parent) {
        root = _root;
        x = node;
        xParent = parent;
        while (x != root && (x == null || x.red() == false)) {
            block41: {
                block40: {
                    if (x != xParent.left()) break block40;
                    w = xParent.right();
                    if (w.red()) {
                        w.red_$eq(false);
                        xParent.red_$eq(true);
                        rotateLeft_root = root;
                        rotateLeft_y = xParent.right();
                        xParent.right_$eq(rotateLeft_y.left());
                        rotateLeft_xp = xParent.parent();
                        if (rotateLeft_y.left() != null) {
                            rotateLeft_y.left().parent_$eq(xParent);
                        }
                        rotateLeft_y.parent_$eq(rotateLeft_xp);
                        if (rotateLeft_xp == null) {
                            rotateLeft_root = rotateLeft_y;
                        } else if (xParent == rotateLeft_xp.left()) {
                            rotateLeft_xp.left_$eq(rotateLeft_y);
                        } else {
                            rotateLeft_xp.right_$eq(rotateLeft_y);
                        }
                        rotateLeft_y.left_$eq(xParent);
                        xParent.parent_$eq(rotateLeft_y);
                        var9_9 = null;
                        var10_10 = null;
                        var11_11 = null;
                        root = rotateLeft_root;
                        w = xParent.right();
                    }
                    var12_12 = null;
                    isBlack_node = w.left();
                    if (!(isBlack_node == null || isBlack_node.red() == false)) ** GOTO lbl-1000
                    var13_13 = null;
                    isBlack_node = w.right();
                    if (isBlack_node == null || isBlack_node.red() == false) {
                        w.red_$eq(true);
                        x = xParent;
                    } else lbl-1000:
                    // 2 sources

                    {
                        var14_14 = null;
                        isBlack_node = w.right();
                        if (isBlack_node == null || isBlack_node.red() == false) {
                            w.left().red_$eq(false);
                            w.red_$eq(true);
                            rotateRight_root = root;
                            rotateRight_y = w.left();
                            w.left_$eq(rotateRight_y.right());
                            rotateRight_xp = w.parent();
                            if (rotateRight_y.right() != null) {
                                rotateRight_y.right().parent_$eq(w);
                            }
                            rotateRight_y.parent_$eq(rotateRight_xp);
                            if (rotateRight_xp == null) {
                                rotateRight_root = rotateRight_y;
                            } else if (w == rotateRight_xp.right()) {
                                rotateRight_xp.right_$eq(rotateRight_y);
                            } else {
                                rotateRight_xp.left_$eq(rotateRight_y);
                            }
                            rotateRight_y.right_$eq(w);
                            w.parent_$eq(rotateRight_y);
                            var15_15 = null;
                            var16_16 = null;
                            var17_17 = null;
                            root = rotateRight_root;
                            w = xParent.right();
                        }
                        w.red_$eq(xParent.red());
                        xParent.red_$eq(false);
                        w.right().red_$eq(false);
                        rotateLeft_root = root;
                        rotateLeft_y = xParent.right();
                        xParent.right_$eq(rotateLeft_y.left());
                        rotateLeft_xp = xParent.parent();
                        if (rotateLeft_y.left() != null) {
                            rotateLeft_y.left().parent_$eq(xParent);
                        }
                        rotateLeft_y.parent_$eq(rotateLeft_xp);
                        if (rotateLeft_xp == null) {
                            rotateLeft_root = rotateLeft_y;
                        } else if (xParent == rotateLeft_xp.left()) {
                            rotateLeft_xp.left_$eq(rotateLeft_y);
                        } else {
                            rotateLeft_xp.right_$eq(rotateLeft_y);
                        }
                        rotateLeft_y.left_$eq(xParent);
                        xParent.parent_$eq(rotateLeft_y);
                        var18_18 = null;
                        var19_19 = null;
                        var20_20 = null;
                        root = rotateLeft_root;
                        x = root;
                    }
                    break block41;
                }
                w = xParent.left();
                if (w.red()) {
                    w.red_$eq(false);
                    xParent.red_$eq(true);
                    rotateRight_root = root;
                    rotateRight_y = xParent.left();
                    xParent.left_$eq(rotateRight_y.right());
                    rotateRight_xp = xParent.parent();
                    if (rotateRight_y.right() != null) {
                        rotateRight_y.right().parent_$eq(xParent);
                    }
                    rotateRight_y.parent_$eq(rotateRight_xp);
                    if (rotateRight_xp == null) {
                        rotateRight_root = rotateRight_y;
                    } else if (xParent == rotateRight_xp.right()) {
                        rotateRight_xp.right_$eq(rotateRight_y);
                    } else {
                        rotateRight_xp.left_$eq(rotateRight_y);
                    }
                    rotateRight_y.right_$eq(xParent);
                    xParent.parent_$eq(rotateRight_y);
                    var21_21 = null;
                    var22_22 = null;
                    var23_23 = null;
                    root = rotateRight_root;
                    w = xParent.left();
                }
                var24_24 = null;
                isBlack_node = w.right();
                if (!(isBlack_node == null || isBlack_node.red() == false)) ** GOTO lbl-1000
                var25_25 = null;
                isBlack_node = w.left();
                if (isBlack_node == null || isBlack_node.red() == false) {
                    w.red_$eq(true);
                    x = xParent;
                } else lbl-1000:
                // 2 sources

                {
                    var26_26 = null;
                    isBlack_node = w.left();
                    if (isBlack_node == null || isBlack_node.red() == false) {
                        w.right().red_$eq(false);
                        w.red_$eq(true);
                        rotateLeft_root = root;
                        rotateLeft_y = w.right();
                        w.right_$eq(rotateLeft_y.left());
                        rotateLeft_xp = w.parent();
                        if (rotateLeft_y.left() != null) {
                            rotateLeft_y.left().parent_$eq(w);
                        }
                        rotateLeft_y.parent_$eq(rotateLeft_xp);
                        if (rotateLeft_xp == null) {
                            rotateLeft_root = rotateLeft_y;
                        } else if (w == rotateLeft_xp.left()) {
                            rotateLeft_xp.left_$eq(rotateLeft_y);
                        } else {
                            rotateLeft_xp.right_$eq(rotateLeft_y);
                        }
                        rotateLeft_y.left_$eq(w);
                        w.parent_$eq(rotateLeft_y);
                        var27_27 = null;
                        var28_28 = null;
                        var29_29 = null;
                        root = rotateLeft_root;
                        w = xParent.left();
                    }
                    w.red_$eq(xParent.red());
                    xParent.red_$eq(false);
                    w.left().red_$eq(false);
                    rotateRight_root = root;
                    rotateRight_y = xParent.left();
                    xParent.left_$eq(rotateRight_y.right());
                    rotateRight_xp = xParent.parent();
                    if (rotateRight_y.right() != null) {
                        rotateRight_y.right().parent_$eq(xParent);
                    }
                    rotateRight_y.parent_$eq(rotateRight_xp);
                    if (rotateRight_xp == null) {
                        rotateRight_root = rotateRight_y;
                    } else if (xParent == rotateRight_xp.right()) {
                        rotateRight_xp.right_$eq(rotateRight_y);
                    } else {
                        rotateRight_xp.left_$eq(rotateRight_y);
                    }
                    rotateRight_y.right_$eq(xParent);
                    xParent.parent_$eq(rotateRight_y);
                    var30_30 = null;
                    var31_31 = null;
                    var32_32 = null;
                    root = rotateRight_root;
                    x = root;
                }
            }
            xParent = x.parent();
        }
        if (x != null) {
            x.red_$eq(false);
        }
        return root;
    }

    private RBNode<K, V> rotateLeft(RBNode<K, V> _root, RBNode<K, V> x) {
        RBNode<K, V> root = _root;
        RBNode<K, V> y = x.right();
        x.right_$eq(y.left());
        RBNode<K, V> xp = x.parent();
        if (y.left() != null) {
            y.left().parent_$eq(x);
        }
        y.parent_$eq(xp);
        if (xp == null) {
            root = y;
        } else if (x == xp.left()) {
            xp.left_$eq(y);
        } else {
            xp.right_$eq(y);
        }
        y.left_$eq(x);
        x.parent_$eq(y);
        return root;
    }

    private RBNode<K, V> rotateRight(RBNode<K, V> _root, RBNode<K, V> x) {
        RBNode<K, V> root = _root;
        RBNode<K, V> y = x.left();
        x.left_$eq(y.right());
        RBNode<K, V> xp = x.parent();
        if (y.right() != null) {
            y.right().parent_$eq(x);
        }
        y.parent_$eq(xp);
        if (xp == null) {
            root = y;
        } else if (x == xp.right()) {
            xp.right_$eq(y);
        } else {
            xp.left_$eq(y);
        }
        y.right_$eq(x);
        x.parent_$eq(y);
        return root;
    }

    private RBNode<K, V> transplant(RBNode<K, V> _root, RBNode<K, V> to, RBNode<K, V> from) {
        RBNode<K, V> root = _root;
        if (to.parent() == null) {
            root = from;
        } else if (to == to.parent().left()) {
            to.parent().left_$eq(from);
        } else {
            to.parent().right_$eq(from);
        }
        if (from != null) {
            from.parent_$eq(to.parent());
        }
        return root;
    }

    public RBNode<K, V> fromNodes(Iterator<Node> xs, int size) {
        int maxUsedDepth = 32 - Integer.numberOfLeadingZeros(size);
        return CollisionProofHashMap.f$1(1, size, xs, maxUsedDepth);
    }

    public static final /* synthetic */ void $anonfun$scala$collection$mutable$CollisionProofHashMap$$splitBucket$1(int mask$1, IntRef highCount$1, IntRef lowCount$1, RBNode n) {
        if ((n.hash() & mask$1) != 0) {
            ++highCount$1.elem;
            return;
        }
        ++lowCount$1.elem;
    }

    public static final /* synthetic */ boolean $anonfun$scala$collection$mutable$CollisionProofHashMap$$splitBucket$2(int mask$1, RBNode n) {
        return (n.hash() & mask$1) == 0;
    }

    public static final /* synthetic */ boolean $anonfun$scala$collection$mutable$CollisionProofHashMap$$splitBucket$3(int mask$1, RBNode n) {
        return (n.hash() & mask$1) != 0;
    }

    private static final RBNode f$1(int level, int size, Iterator xs$1, int maxUsedDepth$1) {
        Object v;
        int n;
        Object k;
        switch (size) {
            case 0: {
                return null;
            }
            case 1: {
                Object v2;
                int n2;
                Object k2;
                Node nn = (Node)xs$1.next();
                if (nn instanceof LLNode) {
                    LLNode lLNode = (LLNode)nn;
                    k2 = lLNode.key();
                    n2 = lLNode.hash();
                    v2 = lLNode.value();
                } else if (nn instanceof RBNode) {
                    RBNode rBNode = (RBNode)nn;
                    k2 = rBNode.key();
                    n2 = rBNode.hash();
                    v2 = rBNode.value();
                } else {
                    throw new MatchError(nn);
                }
                Object v3 = v2;
                int n3 = n2;
                Object k3 = k2;
                return new RBNode(k3, n3, v3, level == maxUsedDepth$1 && level != 1, null, null, null);
            }
        }
        int leftSize = (size - 1) / 2;
        RBNode left = CollisionProofHashMap.f$1(level + 1, leftSize, xs$1, maxUsedDepth$1);
        Node nn = (Node)xs$1.next();
        RBNode right = CollisionProofHashMap.f$1(level + 1, size - 1 - leftSize, xs$1, maxUsedDepth$1);
        if (nn instanceof LLNode) {
            LLNode lLNode = (LLNode)nn;
            k = lLNode.key();
            n = lLNode.hash();
            v = lLNode.value();
        } else if (nn instanceof RBNode) {
            RBNode rBNode = (RBNode)nn;
            k = rBNode.key();
            n = rBNode.hash();
            v = rBNode.value();
        } else {
            throw new MatchError(nn);
        }
        Object v4 = v;
        int n4 = n;
        Object k4 = k;
        RBNode n5 = new RBNode(k4, n4, v4, false, left, right, null);
        if (left != null) {
            left.parent_$eq(n5);
        }
        right.parent_$eq(n5);
        return n5;
    }

    public CollisionProofHashMap(int initialCapacity, double loadFactor, Ordering<K> ordering) {
        this.loadFactor = loadFactor;
        this.scala$collection$mutable$CollisionProofHashMap$$ordering = ordering;
        this.scala$collection$mutable$CollisionProofHashMap$$table = new Node[this.tableSizeFor(initialCapacity)];
        this.scala$collection$mutable$CollisionProofHashMap$$threshold = this.scala$collection$mutable$CollisionProofHashMap$$newThreshold(this.scala$collection$mutable$CollisionProofHashMap$$table.length);
        this.scala$collection$mutable$CollisionProofHashMap$$contentSize = 0;
    }

    public CollisionProofHashMap(Ordering<K> ordering) {
        this(16, 0.75, ordering);
    }

    private static final class DeserializationFactory<K, V>
    implements Factory<Tuple2<K, V>, CollisionProofHashMap<K, V>>,
    Serializable {
        private static final long serialVersionUID = 3L;
        private final int tableLength;
        private final double loadFactor;
        private final Ordering<K> ordering;

        public int tableLength() {
            return this.tableLength;
        }

        public double loadFactor() {
            return this.loadFactor;
        }

        public Ordering<K> ordering() {
            return this.ordering;
        }

        @Override
        public CollisionProofHashMap<K, V> fromSpecific(IterableOnce<Tuple2<K, V>> it) {
            return new CollisionProofHashMap(this.tableLength(), this.loadFactor(), this.ordering()).addAll((IterableOnce)it);
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public Builder<Tuple2<K, V>, CollisionProofHashMap<K, V>> newBuilder() {
            void newBuilder_evidence$4;
            Ordering<K> ordering = this.ordering();
            double newBuilder_loadFactor = this.loadFactor();
            int newBuilder_initialCapacity = this.tableLength();
            return new /* invalid duplicate definition of identical inner class */;
        }

        public DeserializationFactory(int tableLength, double loadFactor, Ordering<K> ordering) {
            this.tableLength = tableLength;
            this.loadFactor = loadFactor;
            this.ordering = ordering;
        }
    }

    private static final class LLNode<K, V>
    extends Node {
        private K key;
        private int hash;
        private V value;
        private LLNode<K, V> next;

        public K key() {
            return this.key;
        }

        public void key_$eq(K x$1) {
            this.key = x$1;
        }

        public int hash() {
            return this.hash;
        }

        public void hash_$eq(int x$1) {
            this.hash = x$1;
        }

        public V value() {
            return this.value;
        }

        public void value_$eq(V x$1) {
            this.value = x$1;
        }

        public LLNode<K, V> next() {
            return this.next;
        }

        public void next_$eq(LLNode<K, V> x$1) {
            this.next = x$1;
        }

        public String toString() {
            return new StringBuilder(16).append("LLNode(").append(this.key()).append(", ").append(this.value()).append(", ").append(this.hash()).append(") -> ").append(this.next()).toString();
        }

        private boolean eq(Object a, Object b) {
            if (a == null) {
                return b == null;
            }
            return a.equals(b);
        }

        public LLNode<K, V> getNode(K k, int h2, Ordering<K> ord) {
            while (h2 != this_.hash() || !this_.eq(k, this_.key())) {
                if (this_.next() == null || this_.hash() > h2) {
                    return null;
                }
                LLNode<K, V> this_ = this_.next();
            }
            return this_;
        }

        public <U> void foreach(Function1<Tuple2<K, V>, U> f) {
            while (true) {
                f.apply(new Tuple2<K, V>(this_.key(), this_.value()));
                if (this_.next() == null) break;
                LLNode<K, V> this_ = this_.next();
            }
        }

        public <U> void foreachEntry(Function2<K, V, U> f) {
            while (true) {
                f.apply(this_.key(), this_.value());
                if (this_.next() == null) break;
                LLNode<K, V> this_ = this_.next();
            }
        }

        public <U> void foreachNode(Function1<LLNode<K, V>, U> f) {
            while (true) {
                f.apply(this_);
                if (this_.next() == null) break;
                LLNode<K, V> this_ = this_.next();
            }
        }

        public LLNode(K key, int hash, V value, LLNode<K, V> next) {
            this.key = key;
            this.hash = hash;
            this.value = value;
            this.next = next;
        }
    }

    private abstract class MapIterator<R>
    extends AbstractIterator<R> {
        private int i;
        private Node node;
        private final int len;

        public abstract R extract(LLNode<K, V> var1);

        public abstract R extract(RBNode<K, V> var1);

        @Override
        public boolean hasNext() {
            if (this.node != null) {
                return true;
            }
            while (this.i < this.len) {
                Node n = this.scala$collection$mutable$CollisionProofHashMap$MapIterator$$$outer().scala$collection$mutable$CollisionProofHashMap$$table[this.i];
                ++this.i;
                if (n == null) continue;
                if (n instanceof RBNode) {
                    RBNode rBNode = (RBNode)n;
                    this.node = CollisionProofHashMap$.MODULE$.scala$collection$mutable$CollisionProofHashMap$$minNodeNonNull(rBNode);
                    return true;
                }
                if (n instanceof LLNode) {
                    LLNode lLNode = (LLNode)n;
                    this.node = lLNode;
                    return true;
                }
                throw new MatchError(n);
            }
            return false;
        }

        @Override
        public R next() {
            if (!this.hasNext()) {
                return (R)Iterator$.scala$collection$Iterator$$_empty.next();
            }
            Node node = this.node;
            if (node instanceof RBNode) {
                RBNode rBNode = (RBNode)node;
                R r = this.extract(rBNode);
                this.node = CollisionProofHashMap$.MODULE$.scala$collection$mutable$CollisionProofHashMap$$successor(rBNode);
                return r;
            }
            if (node instanceof LLNode) {
                LLNode lLNode = (LLNode)node;
                R r = this.extract(lLNode);
                this.node = lLNode.next();
                return r;
            }
            throw new MatchError(node);
        }

        public /* synthetic */ CollisionProofHashMap scala$collection$mutable$CollisionProofHashMap$MapIterator$$$outer() {
            return CollisionProofHashMap.this;
        }

        public MapIterator() {
            if (CollisionProofHashMap.this == null) {
                throw null;
            }
            this.i = 0;
            this.node = null;
            this.len = CollisionProofHashMap.this.scala$collection$mutable$CollisionProofHashMap$$table.length;
        }
    }

    public static abstract class Node {
    }

    public static final class RBNode<K, V>
    extends Node {
        private K key;
        private int hash;
        private V value;
        private boolean red;
        private RBNode<K, V> left;
        private RBNode<K, V> right;
        private RBNode<K, V> parent;

        public K key() {
            return this.key;
        }

        public void key_$eq(K x$1) {
            this.key = x$1;
        }

        public int hash() {
            return this.hash;
        }

        public void hash_$eq(int x$1) {
            this.hash = x$1;
        }

        public V value() {
            return this.value;
        }

        public void value_$eq(V x$1) {
            this.value = x$1;
        }

        public boolean red() {
            return this.red;
        }

        public void red_$eq(boolean x$1) {
            this.red = x$1;
        }

        public RBNode<K, V> left() {
            return this.left;
        }

        public void left_$eq(RBNode<K, V> x$1) {
            this.left = x$1;
        }

        public RBNode<K, V> right() {
            return this.right;
        }

        public void right_$eq(RBNode<K, V> x$1) {
            this.right = x$1;
        }

        public RBNode<K, V> parent() {
            return this.parent;
        }

        public void parent_$eq(RBNode<K, V> x$1) {
            this.parent = x$1;
        }

        public String toString() {
            return new StringBuilder(18).append("RBNode(").append(this.key()).append(", ").append(this.hash()).append(", ").append(this.value()).append(", ").append(this.red()).append(", ").append(this.left()).append(", ").append(this.right()).append(")").toString();
        }

        /*
         * Enabled aggressive block sorting
         */
        public RBNode<K, V> getNode(K k, int h2, Ordering<K> ord) {
            while (true) {
                RBNode<K, V> this_;
                int cmp;
                if ((cmp = ord.compare(k, this_.key())) < 0) {
                    if (this_.left() == null) {
                        return null;
                    }
                    this_ = this_.left();
                    continue;
                }
                if (cmp <= 0) {
                    return this_;
                }
                if (this_.right() == null) {
                    return null;
                }
                this_ = this_.right();
            }
        }

        public <U> void foreach(Function1<Tuple2<K, V>, U> f) {
            while (true) {
                if (this_.left() != null) {
                    this_.left().foreach(f);
                }
                f.apply(new Tuple2<K, V>(this_.key(), this_.value()));
                if (this_.right() == null) break;
                RBNode<K, V> this_ = this_.right();
            }
        }

        public <U> void foreachEntry(Function2<K, V, U> f) {
            while (true) {
                if (this_.left() != null) {
                    this_.left().foreachEntry(f);
                }
                f.apply(this_.key(), this_.value());
                if (this_.right() == null) break;
                RBNode<K, V> this_ = this_.right();
            }
        }

        public <U> void foreachNode(Function1<RBNode<K, V>, U> f) {
            while (true) {
                if (this_.left() != null) {
                    this_.left().foreachNode(f);
                }
                f.apply(this_);
                if (this_.right() == null) break;
                RBNode<K, V> this_ = this_.right();
            }
        }

        public RBNode(K key, int hash, V value, boolean red, RBNode<K, V> left, RBNode<K, V> right, RBNode<K, V> parent) {
            this.key = key;
            this.hash = hash;
            this.value = value;
            this.red = red;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    private static final class RBNodesIterator<A, B>
    extends AbstractIterator<RBNode<A, B>> {
        private RBNode<A, B> nextNode;

        @Override
        public boolean hasNext() {
            return this.nextNode != null;
        }

        @Override
        public RBNode<A, B> next() throws NoSuchElementException {
            RBNode<A, B> rBNode = this.nextNode;
            if (rBNode == null) {
                return (RBNode)((Object)Iterator$.scala$collection$Iterator$$_empty.next());
            }
            this.nextNode = CollisionProofHashMap$.MODULE$.scala$collection$mutable$CollisionProofHashMap$$successor(rBNode);
            return rBNode;
        }

        public RBNodesIterator(RBNode<A, B> tree, Ordering<A> ord) {
            this.nextNode = tree == null ? null : CollisionProofHashMap$.MODULE$.scala$collection$mutable$CollisionProofHashMap$$minNodeNonNull(tree);
        }
    }
}

