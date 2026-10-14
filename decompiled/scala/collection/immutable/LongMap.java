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
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterator;
import scala.collection.BuildFrom;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.View;
import scala.collection.generic.BitOperations;
import scala.collection.generic.DefaultSerializationProxy;
import scala.collection.immutable.AbstractMap;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.List;
import scala.collection.immutable.LongMap$;
import scala.collection.immutable.LongMap$Nil$;
import scala.collection.immutable.LongMap$ToBuildFrom$;
import scala.collection.immutable.LongMap$ToFactory$;
import scala.collection.immutable.LongMapEntryIterator;
import scala.collection.immutable.LongMapKeyIterator;
import scala.collection.immutable.LongMapUtils$;
import scala.collection.immutable.LongMapValueIterator;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.collection.immutable.StrictOptimizedMapOps;
import scala.collection.mutable.Builder;
import scala.collection.mutable.ImmutableBuilder;
import scala.collection.mutable.ListBuffer;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.Either;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@ScalaSignature(bytes="\u0006\u0005\u0019\rqAB={\u0011\u0003\t\u0019AB\u0004\u0002\biD\t!!\u0003\t\u000f\u0005\r\u0012\u0001\"\u0001\u0002&!9\u0011qE\u0001\u0005\u0002\u0005%\u0002b\u0002C[\u0003\u0011\u0005Aq\u0017\u0005\b\u0005K\u000bA\u0011\u0001Cc\u0011\u001d!Y.\u0001C\u0001\t;Dq\u0001b<\u0002\t\u0003!\tp\u0002\u0005\u0005\u0000\u0006A\tI\u001fC,\r!!y%\u0001EAu\u0012E\u0003bBA\u0012\u0013\u0011\u0005AQ\u000b\u0005\b\t3JA\u0011\tC.\u0011%!9$CA\u0001\n\u0003\u0012)\u0004C\u0005\u0005:%\t\t\u0011\"\u0001\u0003P!IA1H\u0005\u0002\u0002\u0013\u0005Aq\f\u0005\n\t\u0007J\u0011\u0011!C!\t\u000b2q\u0001b\u0019\u0002\u0001j$)\u0007\u0003\u0006\u0003\u0000A\u0011)\u001a!C\u0001\u0007\u001bB!\u0002b\u001c\u0011\u0005#\u0005\u000b\u0011BA\u001d\u0011)\u0011y\r\u0005BK\u0002\u0013\u0005A\u0011\u000f\u0005\u000b\tg\u0002\"\u0011#Q\u0001\n\u0011-\u0004bBA\u0012!\u0011\u0005AQ\u000f\u0005\b\t{\u0002B\u0011\u0001C@\u0011%!\t\u0001EA\u0001\n\u0003!y\tC\u0005\u0005\u0018A\t\n\u0011\"\u0001\u0005\u001e\"IA\u0011\u0005\t\u0012\u0002\u0013\u0005A\u0011\u0015\u0005\n\to\u0001\u0012\u0011!C!\u0005kA\u0011\u0002\"\u000f\u0011\u0003\u0003%\tAa\u0014\t\u0013\u0011m\u0002#!A\u0005\u0002\u0011%\u0006\"\u0003C\"!\u0005\u0005I\u0011\tC#\u0011%!I\u0005EA\u0001\n\u0003\"ik\u0002\u0006\u0006\u0002\u0005\t\t\u0011#\u0001{\u000b\u00071!\u0002b\u0019\u0002\u0003\u0003E\tA_C\u0003\u0011\u001d\t\u0019\u0003\tC\u0001\u000b\u000fA\u0011\"\"\u0003!\u0003\u0003%)%b\u0003\t\u0013\t\u0015\u0006%!A\u0005\u0002\u00165\u0001\"CC\u000eA\u0005\u0005I\u0011QC\u000f\u0011%\u0019I\fIA\u0001\n\u0013)yCB\u0004\u0004@\u0006\u0001%p!1\t\u0015\rEgE!f\u0001\n\u0003\u0019i\u0005\u0003\u0006\u0004T\u001a\u0012\t\u0012)A\u0005\u0003sA!b!6'\u0005+\u0007I\u0011AB'\u0011)\u00199N\nB\tB\u0003%\u0011\u0011\b\u0005\u000b\u000734#Q3A\u0005\u0002\rm\u0007BCBoM\tE\t\u0015!\u0003\u0004F\"Q1q\u001c\u0014\u0003\u0016\u0004%\taa7\t\u0015\r\u0005hE!E!\u0002\u0013\u0019)\rC\u0004\u0002$\u0019\"\taa9\t\u000f\rEh\u0005\"\u0001\u0004t\"IA\u0011\u0001\u0014\u0002\u0002\u0013\u0005A1\u0001\u0005\n\t/1\u0013\u0013!C\u0001\t3A\u0011\u0002\"\t'#\u0003%\t\u0001b\t\t\u0013\u0011\u001db%%A\u0005\u0002\u0011%\u0002\"\u0003C\u0019ME\u0005I\u0011\u0001C\u001a\u0011%!9DJA\u0001\n\u0003\u0012)\u0004C\u0005\u0005:\u0019\n\t\u0011\"\u0001\u0003P!IA1\b\u0014\u0002\u0002\u0013\u0005AQ\b\u0005\n\t\u00072\u0013\u0011!C!\t\u000bB\u0011\u0002\"\u0013'\u0003\u0003%\t\u0005b\u0013\b\u0015\u0015]\u0012!!A\t\u0002i,ID\u0002\u0006\u0004@\u0006\t\t\u0011#\u0001{\u000bwAq!a\t=\t\u0003)i\u0004C\u0005\u0006\nq\n\t\u0011\"\u0012\u0006\f!I!Q\u0015\u001f\u0002\u0002\u0013\u0005Uq\b\u0005\n\u000b7a\u0014\u0011!CA\u000b'B\u0011b!/=\u0003\u0003%I!b\f\t\u000f\u0015%\u0014\u0001b\u0001\u0006l\u001dAQ\u0011Q\u0001!\u0012\u0013)\u0019I\u0002\u0005\u0006\u0006\u0006\u0001\u000b\u0012BCD\u0011\u001d\t\u0019\u0003\u0012C\u0001\u000b\u001fCq!a#E\t\u0003)\t\nC\u0004\u0005p\u0012#\t!\"'\t\u0013\reF)!A\u0005\n\u0015=\u0002bBCU\u0003\u0011\rQ1V\u0004\t\u000b\u0003\f\u0001\u0015#\u0003\u0006D\u001aAQQY\u0001!\u0012\u0013)9\rC\u0004\u0002$-#\t!b3\t\u000f\u0005-5\n\"\u0001\u0006N\"9Aq^&\u0005\u0002\u0015U\u0007bBCm\u0003\u0011\rQ1\u001c\u0005\b\u000bS\fA1ACv\u0011%\u0019I,AA\u0001\n\u0013)yCB\u0004\u0002\bi\f\t#a\f\t\u000f\u0005\r\"\u000b\"\u0001\u0002\n\"9\u00111\u0012*\u0005R\u00055\u0005bBA[%\u0012E\u0013q\u0017\u0005\b\u0003O\u0011F\u0011IAd\u0011\u001d\tIM\u0015C!\u0003\u0017Dq!a5S\t\u0003\t)\u000eC\u0004\u0002^J#)%a8\t\u000f\u0005e(\u000b\"\u0012\u0002|\"9!1\u0002*\u0005B\t5\u0001b\u0002B\t%\u0012\u0015!1\u0003\u0005\b\u0005?\u0011F\u0011\tB\u0011\u0011\u001d\u0011)C\u0015C\u0003\u0005OA\u0001Ba\rSA\u0013E#Q\u0007\u0005\b\u0005\u0007\u0012F\u0011\tB#\u0011\u001d\u0011iE\u0015C!\u0005\u001fBqAa\u0016S\t\u0003\u0012I\u0006C\u0004\u0003`I#\tE!\u0019\t\u000f\tE$\u000b\"\u0012\u0003P!9!1\u000f*\u0005\u0006\tU\u0004b\u0002BF%\u0012\u0015#Q\u0012\u0005\b\u0005K\u0013FQ\tBT\u0011\u001d\u0011iK\u0015C!\u0005_CqAa0S\t\u0003\u0012\t\rC\u0004\u0003RJ#\tAa5\t\u000f\t\u0015(\u000b\"\u0001\u0003h\"9!1\u001e*\u0005\u0002\t5\bb\u0002B\u007f%\u0012\u0005!q \u0005\b\u0007+\u0011F\u0011AB\f\u0011\u001d\u0019yC\u0015C\u0001\u0007cAqa!\u0010S\t\u0003\u0019y\u0004C\u0004\u0004LI#)a!\u0014\t\u000f\rE#\u000b\"\u0002\u0004N!91Q\u000b*\u0005\u0002\r]\u0003bBB5%\u0012\u000511\u000e\u0005\b\u0007{\u0012F\u0011IB@\u0011\u001d\u0019iD\u0015C!\u0007#Cqa!)S\t\u0003\u0019\u0019\u000b\u0003\u0005\u0004:J\u0003K\u0011CB^\u0003\u001dauN\\4NCBT!a\u001f?\u0002\u0013%lW.\u001e;bE2,'BA?\u007f\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u007f\u0006)1oY1mC\u000e\u0001\u0001cAA\u0003\u00035\t!PA\u0004M_:<W*\u00199\u0014\u000b\u0005\tY!a\u0005\u0011\t\u00055\u0011qB\u0007\u0002}&\u0019\u0011\u0011\u0003@\u0003\r\u0005s\u0017PU3g!\u0011\t)\"a\b\u000e\u0005\u0005]!\u0002BA\r\u00037\t!![8\u000b\u0005\u0005u\u0011\u0001\u00026bm\u0006LA!!\t\u0002\u0018\ta1+\u001a:jC2L'0\u00192mK\u00061A(\u001b8jiz\"\"!a\u0001\u0002\u000b\u0015l\u0007\u000f^=\u0016\t\u0005-B1W\u000b\u0003\u0003[\u0001R!!\u0002S\tc+B!!\r\u0002TM9!+a\r\u0002f\u0005M\u0004\u0003CA\u0003\u0003k\tI$a\u0014\n\u0007\u0005]\"PA\u0006BEN$(/Y2u\u001b\u0006\u0004\b\u0003BA\u001e\u0003\u0003rA!!\u0002\u0002>%\u0019\u0011q\b>\u0002\u00191{gnZ'baV#\u0018\u000e\\:\n\t\u0005\r\u0013Q\t\u0002\u0005\u0019>tw-\u0003\u0003\u0002D\u0005\u001d#\u0002BA%\u0003\u0017\nQBQ5u\u001fB,'/\u0019;j_:\u001c(bAA'y\u00069q-\u001a8fe&\u001c\u0007\u0003BA)\u0003'b\u0001\u0001\u0002\u0005\u0002VI#)\u0019AA,\u0005\u0005!\u0016\u0003BA-\u0003?\u0002B!!\u0004\u0002\\%\u0019\u0011Q\f@\u0003\u000f9{G\u000f[5oOB!\u0011QBA1\u0013\r\t\u0019G \u0002\u0004\u0003:L\b\u0003DA\u0003\u0003O\nI$a\u0014\u0002l\u0005E\u0014bAA5u\n)2\u000b\u001e:jGR|\u0005\u000f^5nSj,G-T1q\u001fB\u001c\b\u0003BA\u0003\u0003[J1!a\u001c{\u0005\ri\u0015\r\u001d\t\u0006\u0003\u000b\u0011\u0016q\n\t\u0005\u0003k\n)I\u0004\u0003\u0002x\u0005\u0005e\u0002BA=\u0003\u007fj!!a\u001f\u000b\t\u0005u\u0014\u0011A\u0001\u0007yI|w\u000e\u001e \n\u0003}L1!a!\u007f\u0003\u001d\u0001\u0018mY6bO\u0016LA!!\t\u0002\b*\u0019\u00111\u0011@\u0015\u0005\u0005E\u0014\u0001\u00044s_6\u001c\u0006/Z2jM&\u001cG\u0003BA9\u0003\u001fCq!!%U\u0001\u0004\t\u0019*\u0001\u0003d_2d'\u0006BAK\u0003G\u0003b!a&\u0002\u001a\u0006uU\"\u0001?\n\u0007\u0005mEP\u0001\u0007Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cW\r\u0005\u0005\u0002\u000e\u0005}\u0015\u0011HA(\u0013\r\t\tK \u0002\u0007)V\u0004H.\u001a\u001a,\u0005\u0005\u0015\u0006\u0003BAT\u0003ck!!!+\u000b\t\u0005-\u0016QV\u0001\nk:\u001c\u0007.Z2lK\u0012T1!a,\u007f\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003g\u000bIKA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\f!C\\3x'B,7-\u001b4jG\n+\u0018\u000e\u001c3feV\u0011\u0011\u0011\u0018\u0016\u0005\u0003w\u000b\u0019\u000b\u0005\u0005\u0002>\u0006\r\u0017QTA9\u001b\t\tyLC\u0002\u0002Br\fq!\\;uC\ndW-\u0003\u0003\u0002F\u0006}&a\u0002\"vS2$WM]\u000b\u0003\u0003c\na\u0001^8MSN$XCAAg!\u0019\t)!a4\u0002\u001e&\u0019\u0011\u0011\u001b>\u0003\t1K7\u000f^\u0001\tSR,'/\u0019;peV\u0011\u0011q\u001b\t\u0007\u0003/\u000bI.!(\n\u0007\u0005mGP\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003\u001d1wN]3bG\",B!!9\u0002vR!\u00111]Au!\u0011\ti!!:\n\u0007\u0005\u001dhP\u0001\u0003V]&$\bbBAv3\u0002\u0007\u0011Q^\u0001\u0002MBA\u0011QBAx\u0003;\u000b\u00190C\u0002\u0002rz\u0014\u0011BR;oGRLwN\\\u0019\u0011\t\u0005E\u0013Q\u001f\u0003\b\u0003oL&\u0019AA,\u0005\u0005)\u0016\u0001\u00044pe\u0016\f7\r[#oiJLX\u0003BA\u007f\u0005\u0013!B!a9\u0002\u0000\"9\u00111\u001e.A\u0002\t\u0005\u0001CCA\u0007\u0005\u0007\tI$a\u0014\u0003\b%\u0019!Q\u0001@\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004\u0003BA)\u0005\u0013!q!a>[\u0005\u0004\t9&\u0001\u0007lKf\u001c\u0018\n^3sCR|'/\u0006\u0002\u0003\u0010A1\u0011qSAm\u0003s\t!BZ8sK\u0006\u001c\u0007nS3z+\u0011\u0011)B!\b\u0015\t\u0005\r(q\u0003\u0005\b\u0003Wd\u0006\u0019\u0001B\r!!\ti!a<\u0002:\tm\u0001\u0003BA)\u0005;!q!a>]\u0005\u0004\t9&\u0001\bwC2,Xm]%uKJ\fGo\u001c:\u0016\u0005\t\r\u0002CBAL\u00033\fy%\u0001\u0007g_J,\u0017m\u00195WC2,X-\u0006\u0003\u0003*\tEB\u0003BAr\u0005WAq!a;_\u0001\u0004\u0011i\u0003\u0005\u0005\u0002\u000e\u0005=\u0018q\nB\u0018!\u0011\t\tF!\r\u0005\u000f\u0005]hL1\u0001\u0002X\u0005I1\r\\1tg:\u000bW.Z\u000b\u0003\u0005o\u0001BA!\u000f\u0003@5\u0011!1\b\u0006\u0005\u0005{\tY\"\u0001\u0003mC:<\u0017\u0002\u0002B!\u0005w\u0011aa\u0015;sS:<\u0017aB5t\u000b6\u0004H/_\u000b\u0003\u0005\u000f\u0002B!!\u0004\u0003J%\u0019!1\n@\u0003\u000f\t{w\u000e\\3b]\u0006I1N\\8x]NK'0Z\u000b\u0003\u0005#\u0002B!!\u0004\u0003T%\u0019!Q\u000b@\u0003\u0007%sG/\u0001\u0004gS2$XM\u001d\u000b\u0005\u0003c\u0012Y\u0006C\u0004\u0002l\n\u0004\rA!\u0018\u0011\u0011\u00055\u0011q^AO\u0005\u000f\n\u0011\u0002\u001e:b]N4wN]7\u0016\t\t\r$\u0011\u000e\u000b\u0005\u0005K\u0012i\u0007E\u0003\u0002\u0006I\u00139\u0007\u0005\u0003\u0002R\t%Da\u0002B6G\n\u0007\u0011q\u000b\u0002\u0002'\"9\u00111^2A\u0002\t=\u0004CCA\u0007\u0005\u0007\tI$a\u0014\u0003h\u0005!1/\u001b>f\u0003\r9W\r\u001e\u000b\u0005\u0005o\u0012i\b\u0005\u0004\u0002\u000e\te\u0014qJ\u0005\u0004\u0005wr(AB(qi&|g\u000eC\u0004\u0003\u0000\u0015\u0004\r!!\u000f\u0002\u0007-,\u0017\u0010K\u0002f\u0005\u0007\u0003BA!\"\u0003\b6\u0011\u0011QV\u0005\u0005\u0005\u0013\u000biKA\u0004uC&d'/Z2\u0002\u0013\u001d,Go\u0014:FYN,W\u0003\u0002BH\u0005'#bA!%\u0003\u0018\ne\u0005\u0003BA)\u0005'#qAa\u001bg\u0005\u0004\u0011)*\u0005\u0003\u0002P\u0005}\u0003b\u0002B@M\u0002\u0007\u0011\u0011\b\u0005\t\u000573G\u00111\u0001\u0003\u001e\u00069A-\u001a4bk2$\bCBA\u0007\u0005?\u0013\t*C\u0002\u0003\"z\u0014\u0001\u0002\u00102z]\u0006lWM\u0010\u0015\u0004M\n\r\u0015!B1qa2LH\u0003BA(\u0005SCqAa h\u0001\u0004\tI\u0004K\u0002h\u0005\u0007\u000bQ\u0001\n9mkN,BA!-\u00038R!!1\u0017B]!\u0015\t)A\u0015B[!\u0011\t\tFa.\u0005\u000f\t-\u0004N1\u0001\u0003\u0016\"9!1\u00185A\u0002\tu\u0016AA6w!!\ti!a(\u0002:\tU\u0016aB;qI\u0006$X\rZ\u000b\u0005\u0005\u0007\u0014I\r\u0006\u0004\u0003F\n-'Q\u001a\t\u0006\u0003\u000b\u0011&q\u0019\t\u0005\u0003#\u0012I\rB\u0004\u0003l%\u0014\rA!&\t\u000f\t}\u0014\u000e1\u0001\u0002:!9!qZ5A\u0002\t\u001d\u0017!\u0002<bYV,\u0017AC;qI\u0006$XmV5uQV!!Q\u001bBn)!\u00119N!8\u0003`\n\u0005\b#BA\u0003%\ne\u0007\u0003BA)\u00057$qAa\u001bk\u0005\u0004\u0011)\nC\u0004\u0003\u0000)\u0004\r!!\u000f\t\u000f\t='\u000e1\u0001\u0003Z\"9\u00111\u001e6A\u0002\t\r\bCCA\u0007\u0005\u0007\tyE!7\u0003Z\u00069!/Z7pm\u0016$G\u0003BA9\u0005SDqAa l\u0001\u0004\tI$\u0001\bn_\u0012Lg-_(s%\u0016lwN^3\u0016\t\t=(Q\u001f\u000b\u0005\u0005c\u00149\u0010E\u0003\u0002\u0006I\u0013\u0019\u0010\u0005\u0003\u0002R\tUHa\u0002B6Y\n\u0007\u0011q\u000b\u0005\b\u0003Wd\u0007\u0019\u0001B}!)\tiAa\u0001\u0002:\u0005=#1 \t\u0007\u0003\u001b\u0011IHa=\u0002\u0013Ut\u0017n\u001c8XSRDW\u0003BB\u0001\u0007\u000f!baa\u0001\u0004\n\r5\u0001#BA\u0003%\u000e\u0015\u0001\u0003BA)\u0007\u000f!qAa\u001bn\u0005\u0004\u0011)\nC\u0004\u0004\f5\u0004\raa\u0001\u0002\tQD\u0017\r\u001e\u0005\b\u0003Wl\u0007\u0019AB\b!1\tia!\u0005\u0002:\r\u00151QAB\u0003\u0013\r\u0019\u0019B \u0002\n\rVt7\r^5p]N\n\u0001#\u001b8uKJ\u001cXm\u0019;j_:<\u0016\u000e\u001e5\u0016\r\re1\u0011FB\u0010)\u0019\u0019Yba\t\u0004,A)\u0011Q\u0001*\u0004\u001eA!\u0011\u0011KB\u0010\t\u001d\u0019\tC\u001cb\u0001\u0003/\u0012\u0011A\u0015\u0005\b\u0007\u0017q\u0007\u0019AB\u0013!\u0015\t)AUB\u0014!\u0011\t\tf!\u000b\u0005\u000f\t-dN1\u0001\u0002X!9\u00111\u001e8A\u0002\r5\u0002\u0003DA\u0007\u0007#\tI$a\u0014\u0004(\ru\u0011\u0001D5oi\u0016\u00148/Z2uS>tW\u0003BB\u001a\u0007w!B!!\u001d\u00046!911B8A\u0002\r]\u0002#BA\u0003%\u000ee\u0002\u0003BA)\u0007w!qa!\tp\u0005\u0004\t9&\u0001\u0006%a2,8\u000f\n9mkN,Ba!\u0011\u0004HQ!11IB%!\u0015\t)AUB#!\u0011\t\tfa\u0012\u0005\u000f\t-\u0004O1\u0001\u0003\u0016\"911\u00029A\u0002\r\r\u0013\u0001\u00034jeN$8*Z=\u0016\u0005\u0005e\u0002fA9\u0003\u0004\u00069A.Y:u\u0017\u0016L\bf\u0001:\u0003\u0004\u0006\u0019Q.\u00199\u0016\t\re3q\f\u000b\u0005\u00077\u001a\u0019\u0007E\u0003\u0002\u0006I\u001bi\u0006\u0005\u0003\u0002R\r}CaBB1g\n\u0007\u0011q\u000b\u0002\u0003-JBq!a;t\u0001\u0004\u0019)\u0007\u0005\u0005\u0002\u000e\u0005=\u0018QTB4!!\ti!a(\u0002:\ru\u0013a\u00024mCRl\u0015\r]\u000b\u0005\u0007[\u001a\u0019\b\u0006\u0003\u0004p\rU\u0004#BA\u0003%\u000eE\u0004\u0003BA)\u0007g\"qa!\u0019u\u0005\u0004\t9\u0006C\u0004\u0002lR\u0004\raa\u001e\u0011\u0011\u00055\u0011q^AO\u0007s\u0002b!a&\u0002\u001a\u000em\u0004\u0003CA\u0007\u0003?\u000bId!\u001d\u0002\r\r|gnY1u+\u0011\u0019\tia\"\u0015\t\r\r51\u0012\t\u0006\u0003\u000b\u00116Q\u0011\t\u0005\u0003#\u001a9\tB\u0004\u0004\nV\u0014\rA!&\u0003\u0005Y\u000b\u0004bBB\u0006k\u0002\u00071Q\u0012\t\u0007\u0003/\u000bIja$\u0011\u0011\u00055\u0011qTA\u001d\u0007\u000b+Baa%\u0004\u001aR!1QSBN!\u0015\t)AUBL!\u0011\t\tf!'\u0005\u000f\r%eO1\u0001\u0003\u0016\"911\u0002<A\u0002\ru\u0005CBAL\u00033\u001by\n\u0005\u0005\u0002\u000e\u0005}\u0015\u0011HBL\u0003\u001d\u0019w\u000e\u001c7fGR,Ba!*\u0004,R!1qUBW!\u0015\t)AUBU!\u0011\t\tfa+\u0005\u000f\r\u0005tO1\u0001\u0002X!91qV<A\u0002\rE\u0016A\u00019g!!\tiaa-\u0002\u001e\u000e]\u0016bAB[}\ny\u0001+\u0019:uS\u0006dg)\u001e8di&|g\u000e\u0005\u0005\u0002\u000e\u0005}\u0015\u0011HBU\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\tY!\u000b\u0003SM%\u0001\"a\u0001\"j]V!11YBe'\u001d13QYBf\u0003g\u0002R!!\u0002S\u0007\u000f\u0004B!!\u0015\u0004J\u0012A\u0011Q\u000b\u0014\u0005\u0006\u0004\t9\u0006\u0005\u0003\u0002\u000e\r5\u0017bABh}\n9\u0001K]8ek\u000e$\u0018A\u00029sK\u001aL\u00070A\u0004qe\u00164\u0017\u000e\u001f\u0011\u0002\t5\f7o[\u0001\u0006[\u0006\u001c8\u000eI\u0001\u0005Y\u00164G/\u0006\u0002\u0004F\u0006)A.\u001a4uA\u0005)!/[4ii\u00061!/[4ii\u0002\"\"b!:\u0004j\u000e-8Q^Bx!\u0015\u00199OJBd\u001b\u0005\t\u0001bBBi_\u0001\u0007\u0011\u0011\b\u0005\b\u0007+|\u0003\u0019AA\u001d\u0011\u001d\u0019In\fa\u0001\u0007\u000bDqaa80\u0001\u0004\u0019)-A\u0002cS:,Ba!>\u0004|R11q_B\u007f\u0007\u007f\u0004R!!\u0002S\u0007s\u0004B!!\u0015\u0004|\u00129!1\u000e\u0019C\u0002\u0005]\u0003bBBma\u0001\u00071q\u001f\u0005\b\u0007?\u0004\u0004\u0019AB|\u0003\u0011\u0019w\u000e]=\u0016\t\u0011\u0015A1\u0002\u000b\u000b\t\u000f!i\u0001b\u0004\u0005\u0012\u0011U\u0001#BBtM\u0011%\u0001\u0003BA)\t\u0017!q!!\u00162\u0005\u0004\t9\u0006C\u0005\u0004RF\u0002\n\u00111\u0001\u0002:!I1Q[\u0019\u0011\u0002\u0003\u0007\u0011\u0011\b\u0005\n\u00073\f\u0004\u0013!a\u0001\t'\u0001R!!\u0002S\t\u0013A\u0011ba82!\u0003\u0005\r\u0001b\u0005\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU!A1\u0004C\u0010+\t!iB\u000b\u0003\u0002:\u0005\rFaBA+e\t\u0007\u0011qK\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\u0011!Y\u0002\"\n\u0005\u000f\u0005U3G1\u0001\u0002X\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\u001aT\u0003\u0002C\u0016\t_)\"\u0001\"\f+\t\r\u0015\u00171\u0015\u0003\b\u0003+\"$\u0019AA,\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIQ*B\u0001b\u000b\u00056\u00119\u0011QK\u001bC\u0002\u0005]\u0013!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\u0005}Cq\b\u0005\n\t\u0003B\u0014\u0011!a\u0001\u0005#\n1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XC\u0001C$!\u0019\t9*!7\u0002`\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\u00119\u0004\"\u0014\t\u0013\u0011\u0005#(!AA\u0002\tE#a\u0001(jYN9\u0011\u0002b\u0015\u0004L\u0006M\u0004#BA\u0003%\u0006eCC\u0001C,!\r\u00199/C\u0001\u0007KF,\u0018\r\\:\u0015\t\t\u001dCQ\f\u0005\b\u0007\u0017Y\u0001\u0019AA0)\u0011\ty\u0006\"\u0019\t\u0013\u0011\u0005c\"!AA\u0002\tE#a\u0001+jaV!Aq\rC7'\u001d\u0001B\u0011NBf\u0003g\u0002R!!\u0002S\tW\u0002B!!\u0015\u0005n\u0011A\u0011Q\u000b\t\u0005\u0006\u0004\t9&\u0001\u0003lKf\u0004SC\u0001C6\u0003\u00191\u0018\r\\;fAQ1Aq\u000fC=\tw\u0002Raa:\u0011\tWBqAa \u0016\u0001\u0004\tI\u0004C\u0004\u0003PV\u0001\r\u0001b\u001b\u0002\u0013]LG\u000f\u001b,bYV,W\u0003\u0002CA\t\u0013#B\u0001b!\u0005\fB)AQ\u0011\t\u0005\b:\u0019\u0011Q\u0001\u0001\u0011\t\u0005EC\u0011\u0012\u0003\b\u0005W2\"\u0019AA,\u0011\u001d!iI\u0006a\u0001\t\u000f\u000b\u0011a]\u000b\u0005\t##9\n\u0006\u0004\u0005\u0014\u0012eE1\u0014\t\u0006\u0007O\u0004BQ\u0013\t\u0005\u0003#\"9\nB\u0004\u0002V]\u0011\r!a\u0016\t\u0013\t}t\u0003%AA\u0002\u0005e\u0002\"\u0003Bh/A\u0005\t\u0019\u0001CK+\u0011!Y\u0002b(\u0005\u000f\u0005U\u0003D1\u0001\u0002XU!A1\u0015CT+\t!)K\u000b\u0003\u0005l\u0005\rFaBA+3\t\u0007\u0011q\u000b\u000b\u0005\u0003?\"Y\u000bC\u0005\u0005Bq\t\t\u00111\u0001\u0003RQ!!q\u0007CX\u0011%!\tEHA\u0001\u0002\u0004\u0011\t\u0006\u0005\u0003\u0002R\u0011MFaBA+\u0007\t\u0007\u0011qK\u0001\ng&tw\r\\3u_:,B\u0001\"/\u0005@R1A1\u0018Ca\t\u0007\u0004R!!\u0002S\t{\u0003B!!\u0015\u0005@\u00129\u0011Q\u000b\u0003C\u0002\u0005]\u0003b\u0002B@\t\u0001\u0007\u0011\u0011\b\u0005\b\u0005\u001f$\u0001\u0019\u0001C_+\u0011!9\r\"4\u0015\t\u0011%Gq\u001a\t\u0006\u0003\u000b\u0011F1\u001a\t\u0005\u0003#\"i\rB\u0004\u0002V\u0015\u0011\r!a\u0016\t\u000f\u0011EW\u00011\u0001\u0005T\u0006)Q\r\\3ngB1\u0011Q\u0002Ck\t3L1\u0001b6\u007f\u0005)a$/\u001a9fCR,GM\u0010\t\t\u0003\u001b\ty*!\u000f\u0005L\u0006!aM]8n+\u0011!y\u000e\":\u0015\t\u0011\u0005H\u0011\u001e\t\u0006\u0003\u000b\u0011F1\u001d\t\u0005\u0003#\")\u000fB\u0004\u0005h\u001a\u0011\r!a\u0016\u0003\u0003YCq!!%\u0007\u0001\u0004!Y\u000f\u0005\u0004\u0002\u0018\u0006eEQ\u001e\t\t\u0003\u001b\ty*!\u000f\u0005d\u0006Qa.Z<Ck&dG-\u001a:\u0016\t\u0011MH1`\u000b\u0003\tk\u0004\u0002\"!0\u0002D\u0012]HQ \t\t\u0003\u001b\ty*!\u000f\u0005zB!\u0011\u0011\u000bC~\t\u001d!9o\u0002b\u0001\u0003/\u0002R!!\u0002S\ts\f1AT5m\u0003\r!\u0016\u000e\u001d\t\u0004\u0007O\u00043#\u0002\u0011\u0002\f\u0005MACAC\u0002\u0003!!xn\u0015;sS:<GC\u0001B\u001c+\u0011)y!\"\u0006\u0015\r\u0015EQqCC\r!\u0015\u00199\u000fEC\n!\u0011\t\t&\"\u0006\u0005\u000f\u0005U3E1\u0001\u0002X!9!qP\u0012A\u0002\u0005e\u0002b\u0002BhG\u0001\u0007Q1C\u0001\bk:\f\u0007\u000f\u001d7z+\u0011)y\"b\n\u0015\t\u0015\u0005R\u0011\u0006\t\u0007\u0003\u001b\u0011I(b\t\u0011\u0011\u00055\u0011qTA\u001d\u000bK\u0001B!!\u0015\u0006(\u00119\u0011Q\u000b\u0013C\u0002\u0005]\u0003\"CC\u0016I\u0005\u0005\t\u0019AC\u0017\u0003\rAH\u0005\r\t\u0006\u0007O\u0004RQ\u0005\u000b\u0003\u000bc\u0001BA!\u000f\u00064%!QQ\u0007B\u001e\u0005\u0019y%M[3di\u0006\u0019!)\u001b8\u0011\u0007\r\u001dHhE\u0003=\u0003\u0017\t\u0019\u0002\u0006\u0002\u0006:U!Q\u0011IC$)))\u0019%\"\u0013\u0006L\u00155S\u0011\u000b\t\u0006\u0007O4SQ\t\t\u0005\u0003#*9\u0005B\u0004\u0002V}\u0012\r!a\u0016\t\u000f\rEw\b1\u0001\u0002:!91Q[ A\u0002\u0005e\u0002bBBm\u007f\u0001\u0007Qq\n\t\u0006\u0003\u000b\u0011VQ\t\u0005\b\u0007?|\u0004\u0019AC(+\u0011))&b\u0019\u0015\t\u0015]SQ\r\t\u0007\u0003\u001b\u0011I(\"\u0017\u0011\u0019\u00055Q1LA\u001d\u0003s)y&b\u0018\n\u0007\u0015ucP\u0001\u0004UkBdW\r\u000e\t\u0006\u0003\u000b\u0011V\u0011\r\t\u0005\u0003#*\u0019\u0007B\u0004\u0002V\u0001\u0013\r!a\u0016\t\u0013\u0015-\u0002)!AA\u0002\u0015\u001d\u0004#BBtM\u0015\u0005\u0014!\u0003;p\r\u0006\u001cGo\u001c:z+\u0011)i'\"\u001f\u0015\t\u0015=TQ\u0010\t\t\u0003/+\t(\"\u001e\u0006|%\u0019Q1\u000f?\u0003\u000f\u0019\u000b7\r^8ssBA\u0011QBAP\u0003s)9\b\u0005\u0003\u0002R\u0015eDa\u0002Ct\u0005\n\u0007\u0011q\u000b\t\u0006\u0003\u000b\u0011Vq\u000f\u0005\b\u000b\u007f\u0012\u0005\u0019\u0001CC\u0003\u0015!W/\\7z\u0003%!vNR1di>\u0014\u0018\u0010E\u0002\u0004h\u0012\u0013\u0011\u0002V8GC\u000e$xN]=\u0014\u000f\u0011\u000bY!\"#\u0002tAA\u0011qSC9\u000b\u0017+i\t\u0005\u0005\u0002\u000e\u0005}\u0015\u0011HA\u0006!\u0015\t)AUA\u0006)\t)\u0019\t\u0006\u0003\u0006\u000e\u0016M\u0005bBCK\r\u0002\u0007QqS\u0001\u0003SR\u0004b!a&\u0002\u001a\u0016-UCACN!!\ti,a1\u0006\f\u00165\u0005f\u0002#\u0006 \n=WQ\u0015\t\u0005\u0003\u001b)\t+C\u0002\u0006$z\u0014\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u001f\u0003\rAsaQCP\u0005\u001f,)+A\u0006u_\n+\u0018\u000e\u001c3Ge>lW\u0003BCW\u000bs#B!b,\u0006>BQ\u0011qSCY\u0003?*),b/\n\u0007\u0015MFPA\u0005Ck&dGM\u0012:p[BA\u0011QBAP\u0003s)9\f\u0005\u0003\u0002R\u0015eFa\u0002Ct\u0013\n\u0007\u0011q\u000b\t\u0006\u0003\u000b\u0011Vq\u0017\u0005\b\u000b\u007fK\u0005\u0019\u0001CC\u0003\u001d1\u0017m\u0019;pef\f1\u0002V8Ck&dGM\u0012:p[B\u00191q]&\u0003\u0017Q{')^5mI\u001a\u0013x.\\\n\u0006\u0017\u0006-Q\u0011\u001a\t\u000b\u0003/+\t,a\u0018\u0006\f\u00165ECACb)\u0011)y-b5\u0015\t\u00155U\u0011\u001b\u0005\b\u000b+k\u0005\u0019ACL\u0011\u001d!Y.\u0014a\u0001\u0003?\"B!b'\u0006X\"9A1\u001c(A\u0002\u0005}\u0013aD5uKJ\f'\r\\3GC\u000e$xN]=\u0016\t\u0015uWQ]\u000b\u0003\u000b?\u0004\u0002\"a&\u0006r\u0015\u0005Xq\u001d\t\t\u0003\u001b\ty*!\u000f\u0006dB!\u0011\u0011KCs\t\u001d!9o\u0014b\u0001\u0003/\u0002R!!\u0002S\u000bG\f\u0001CY;jY\u00124%o\\7M_:<W*\u00199\u0016\t\u00155Xq`\u000b\u0003\u000b_\u0004\"\"a&\u00062\u0016EX1 D\u0001a\u0011)\u00190b>\u0011\u000b\u0005\u0015!+\">\u0011\t\u0005ESq\u001f\u0003\f\u000bs\u0004\u0016\u0011!A\u0001\u0006\u0003\t9FA\u0002`IE\u0002\u0002\"!\u0004\u0002 \u0006eRQ \t\u0005\u0003#*y\u0010B\u0004\u0005hB\u0013\r!a\u0016\u0011\u000b\u0005\u0015!+\"@")
public abstract class LongMap<T>
extends AbstractMap<Object, T>
implements StrictOptimizedMapOps<Object, T, Map, LongMap<T>>,
Serializable {
    public static <V> BuildFrom<LongMap<?>, Tuple2<Object, V>, LongMap<V>> buildFromLongMap() {
        return LongMap$ToBuildFrom$.MODULE$;
    }

    public static <V> BuildFrom<Object, Tuple2<Object, V>, LongMap<V>> toBuildFrom(LongMap$ factory2) {
        return LongMap$ToBuildFrom$.MODULE$;
    }

    public static <V> Factory<Tuple2<Object, V>, LongMap<V>> toFactory(LongMap$ dummy) {
        return LongMap$ToFactory$.MODULE$;
    }

    public static <V> Builder<Tuple2<Object, V>, LongMap<V>> newBuilder() {
        return new ImmutableBuilder<Tuple2<Object, V>, LongMap<V>>(){

            public $anon$1 addOne(Tuple2<Object, V> elem) {
                this.elems_$eq(((LongMap)this.elems()).$plus(elem));
                return this;
            }
        };
    }

    public static <V> LongMap<V> from(IterableOnce<Tuple2<Object, V>> coll) {
        return LongMap$.MODULE$.from(coll);
    }

    public static <T> LongMap<T> singleton(long key, T value) {
        return new Tip<T>(key, value);
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
    public Tuple2<LongMap<T>, LongMap<T>> partition(Function1<Tuple2<Object, T>, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<LongMap<T>, LongMap<T>> span(Function1<Tuple2<Object, T>, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<Iterable<A1>, Iterable<A2>> unzip(Function1<Tuple2<Object, T>, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<Iterable<A1>, Iterable<A2>, Iterable<A3>> unzip3(Function1<Tuple2<Object, T>, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<Tuple2<Object, T>, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<Tuple2<Object, T>, IterableOnce<B>> f) {
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
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<Tuple2<Object, T>, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<Tuple2<Object, T>, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<Tuple2<Object, T>, B>, C2> b) {
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
    public <A1, A2> Tuple2<Iterable<A1>, Iterable<A2>> partitionMap(Function1<Tuple2<Object, T>, Either<A1, A2>> f) {
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
    public LongMap<T> fromSpecific(IterableOnce<Tuple2<Object, T>> coll) {
        Builder<Tuple2<Object, T>, LongMap<T>> b = this.newSpecificBuilder();
        if (b == null) {
            throw null;
        }
        b.sizeHint(coll, 0);
        b.addAll(coll);
        return b.result();
    }

    @Override
    public Builder<Tuple2<Object, T>, LongMap<T>> newSpecificBuilder() {
        return new ImmutableBuilder<Tuple2<Object, T>, LongMap<T>>(this){

            public $anon$2 addOne(Tuple2<Object, T> elem) {
                this.elems_$eq(((LongMap)this.elems()).$plus(elem));
                return this;
            }
        };
    }

    @Override
    public LongMap<T> empty() {
        return LongMap$Nil$.MODULE$;
    }

    @Override
    public List<Tuple2<Object, T>> toList() {
        Tip tip;
        Bin bin;
        ListBuffer buffer = new ListBuffer();
        Function1<Tuple2, ListBuffer> & Serializable foreach_f = (Function1<Tuple2, ListBuffer> & Serializable)x$1 -> (ListBuffer)buffer.$plus$eq(x$1);
        LongMap foreach_this = this;
        while (foreach_this instanceof Bin) {
            bin = (Bin)foreach_this;
            LongMap foreach_left = bin.left();
            LongMap foreach_right = bin.right();
            foreach_left.foreach((Function1<Tuple2<Object, T>, U>)foreach_f);
            foreach_this = foreach_right;
        }
        if (foreach_this instanceof Tip) {
            tip = (Tip)foreach_this;
            long foreach_key = tip.key();
            Object foreach_value = tip.value();
            Tuple2 tuple2 = new Tuple2(foreach_key, foreach_value);
            ListBuffer cfr_ignored_0 = (ListBuffer)buffer.addOne(tuple2);
        } else if (!((Object)LongMap$Nil$.MODULE$).equals(foreach_this)) {
            throw new MatchError(foreach_this);
        }
        Object var2_3 = null;
        Object var3_2 = null;
        bin = null;
        Object var5_5 = null;
        Object var6_6 = null;
        tip = null;
        Object var10_9 = null;
        return buffer.toList();
    }

    @Override
    public Iterator<Tuple2<Object, T>> iterator() {
        if (((Object)LongMap$Nil$.MODULE$).equals(this)) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        return new LongMapEntryIterator(this);
    }

    @Override
    public final <U> void foreach(Function1<Tuple2<Object, T>, U> f) {
        while (this_ instanceof Bin) {
            Bin bin = (Bin)this_;
            LongMap<T> left = bin.left();
            LongMap right = bin.right();
            left.foreach(f);
            LongMap this_ = right;
        }
        if (this_ instanceof Tip) {
            Tip tip = (Tip)this_;
            long key = tip.key();
            Object value = tip.value();
            f.apply(new Tuple2(key, value));
            return;
        }
        if (((Object)LongMap$Nil$.MODULE$).equals(this_)) {
            return;
        }
        throw new MatchError(this_);
    }

    @Override
    public final <U> void foreachEntry(Function2<Object, T, U> f) {
        while (this_ instanceof Bin) {
            Bin bin = (Bin)this_;
            LongMap<T> left = bin.left();
            LongMap right = bin.right();
            left.foreachEntry(f);
            LongMap this_ = right;
        }
        if (this_ instanceof Tip) {
            Tip tip = (Tip)this_;
            long key = tip.key();
            Object value = tip.value();
            f.apply(key, value);
            return;
        }
        if (((Object)LongMap$Nil$.MODULE$).equals(this_)) {
            return;
        }
        throw new MatchError(this_);
    }

    @Override
    public Iterator<Object> keysIterator() {
        if (((Object)LongMap$Nil$.MODULE$).equals(this)) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        return new LongMapKeyIterator(this);
    }

    public final <U> void foreachKey(Function1<Object, U> f) {
        while (this_ instanceof Bin) {
            Bin bin = (Bin)this_;
            LongMap left = bin.left();
            LongMap right = bin.right();
            left.foreachKey(f);
            LongMap this_ = right;
        }
        if (this_ instanceof Tip) {
            long key = ((Tip)this_).key();
            f.apply(key);
            return;
        }
        if (((Object)LongMap$Nil$.MODULE$).equals(this_)) {
            return;
        }
        throw new MatchError(this_);
    }

    @Override
    public Iterator<T> valuesIterator() {
        if (((Object)LongMap$Nil$.MODULE$).equals(this)) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        return new LongMapValueIterator(this);
    }

    public final <U> void foreachValue(Function1<T, U> f) {
        while (this_ instanceof Bin) {
            Bin bin = (Bin)this_;
            LongMap<T> left = bin.left();
            LongMap right = bin.right();
            left.foreachValue(f);
            LongMap this_ = right;
        }
        if (this_ instanceof Tip) {
            Object value = ((Tip)this_).value();
            f.apply(value);
            return;
        }
        if (((Object)LongMap$Nil$.MODULE$).equals(this_)) {
            return;
        }
        throw new MatchError(this_);
    }

    @Override
    public String className() {
        return "LongMap";
    }

    @Override
    public boolean isEmpty() {
        return this == LongMap$Nil$.MODULE$;
    }

    @Override
    public int knownSize() {
        if (this.isEmpty()) {
            return 0;
        }
        return -1;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public LongMap<T> filter(Function1<Tuple2<Object, T>, Object> f) {
        if (this instanceof Bin) {
            void var9_8;
            Bin bin = (Bin)this;
            long prefix = bin.prefix();
            long mask = bin.mask();
            LongMap left = bin.left();
            LongMap right = bin.right();
            Object object = right.filter((Function1)f);
            Object newleft = left.filter((Function1)f);
            if (left == var9_8 && right == object) {
                return this;
            }
            return LongMapUtils$.MODULE$.bin(prefix, mask, var9_8, object);
        }
        if (this instanceof Tip) {
            Tip tip = (Tip)this;
            long key = tip.key();
            Object value = tip.value();
            if (BoxesRunTime.unboxToBoolean(f.apply(new Tuple2(key, value)))) {
                return this;
            }
            return LongMap$Nil$.MODULE$;
        }
        if (((Object)LongMap$Nil$.MODULE$).equals(this)) {
            return LongMap$Nil$.MODULE$;
        }
        throw new MatchError(this);
    }

    @Override
    public <S> LongMap<S> transform(Function2<Object, T, S> f) {
        if (this instanceof Bin) {
            Bin bin = (Bin)this;
            LongMap left = bin.left();
            LongMap right = bin.right();
            return bin.bin(left.transform((Function2)f), right.transform((Function2)f));
        }
        if (this instanceof Tip) {
            Tip tip = (Tip)this;
            long key = tip.key();
            Object value = tip.value();
            return tip.withValue(f.apply(key, value));
        }
        if (((Object)LongMap$Nil$.MODULE$).equals(this)) {
            return LongMap$Nil$.MODULE$;
        }
        throw new MatchError(this);
    }

    @Override
    public final int size() {
        if (((Object)LongMap$Nil$.MODULE$).equals(this)) {
            return 0;
        }
        if (this instanceof Tip) {
            return 1;
        }
        if (this instanceof Bin) {
            Bin bin = (Bin)this;
            LongMap left = bin.left();
            LongMap right = bin.right();
            return left.size() + right.size();
        }
        throw new MatchError(this);
    }

    @Override
    public final Option<T> get(long key) {
        while (this_ instanceof Bin) {
            LongMap this_;
            Bin bin = (Bin)this_;
            long mask = bin.mask();
            LongMap left = bin.left();
            LongMap right = bin.right();
            if (BitOperations.Long.zero$(LongMapUtils$.MODULE$, key, mask)) {
                this_ = left;
                continue;
            }
            this_ = right;
        }
        if (this_ instanceof Tip) {
            Tip tip = (Tip)this_;
            long key2 = tip.key();
            Object value = tip.value();
            if (key == key2) {
                return new Some(value);
            }
            return None$.MODULE$;
        }
        if (((Object)LongMap$Nil$.MODULE$).equals(this_)) {
            return None$.MODULE$;
        }
        throw new MatchError(this_);
    }

    @Override
    public final <S> S getOrElse(long key, Function0<S> function0) {
        while (true) {
            LongMap this_;
            if (((Object)LongMap$Nil$.MODULE$).equals(this_)) {
                return function0.apply();
            }
            if (this_ instanceof Tip) {
                Tip tip = (Tip)this_;
                long key2 = tip.key();
                Object value = tip.value();
                if (key == key2) {
                    return (S)value;
                }
                return function0.apply();
            }
            if (!(this_ instanceof Bin)) break;
            Bin bin = (Bin)this_;
            long mask = bin.mask();
            LongMap left = bin.left();
            LongMap right = bin.right();
            if (BitOperations.Long.zero$(LongMapUtils$.MODULE$, key, mask)) {
                this_ = left;
                continue;
            }
            this_ = right;
        }
        throw new MatchError(this_);
    }

    @Override
    public final T apply(long key) {
        while (this_ instanceof Bin) {
            LongMap this_;
            Bin bin = (Bin)this_;
            long mask = bin.mask();
            LongMap left = bin.left();
            LongMap right = bin.right();
            if (BitOperations.Long.zero$(LongMapUtils$.MODULE$, key, mask)) {
                this_ = left;
                continue;
            }
            this_ = right;
        }
        if (this_ instanceof Tip) {
            Tip tip = (Tip)this_;
            long key2 = tip.key();
            Object value = tip.value();
            if (key == key2) {
                return value;
            }
            throw new IllegalArgumentException("Key not found");
        }
        if (((Object)LongMap$Nil$.MODULE$).equals(this_)) {
            throw new IllegalArgumentException("key not found");
        }
        throw new MatchError(this_);
    }

    @Override
    public <S> LongMap<S> $plus(Tuple2<Object, S> kv) {
        return this.updated(kv._1$mcJ$sp(), kv._2());
    }

    @Override
    public <S> LongMap<S> updated(long key, S value) {
        if (this instanceof Bin) {
            Bin bin = (Bin)this;
            long prefix = bin.prefix();
            long mask = bin.mask();
            LongMap left = bin.left();
            LongMap right = bin.right();
            if (!BitOperations.Long.hasMatch$(LongMapUtils$.MODULE$, key, prefix, mask)) {
                return LongMapUtils$.MODULE$.join(key, new Tip<S>(key, value), prefix, this);
            }
            if (BitOperations.Long.zero$(LongMapUtils$.MODULE$, key, mask)) {
                return new Bin<S>(prefix, mask, left.updated(key, value), right);
            }
            return new Bin(prefix, mask, left, right.updated(key, value));
        }
        if (this instanceof Tip) {
            long key2 = ((Tip)this).key();
            if (key == key2) {
                return new Tip<S>(key, value);
            }
            return LongMapUtils$.MODULE$.join(key, new Tip<S>(key, value), key2, this);
        }
        if (((Object)LongMap$Nil$.MODULE$).equals(this)) {
            return new Tip<S>(key, value);
        }
        throw new MatchError(this);
    }

    public <S> LongMap<S> updateWith(long key, S value, Function2<T, S, S> f) {
        if (this instanceof Bin) {
            Bin bin = (Bin)this;
            long prefix = bin.prefix();
            long mask = bin.mask();
            LongMap<T> left = bin.left();
            LongMap<T> right = bin.right();
            if (!BitOperations.Long.hasMatch$(LongMapUtils$.MODULE$, key, prefix, mask)) {
                return LongMapUtils$.MODULE$.join(key, new Tip<S>(key, value), prefix, this);
            }
            if (BitOperations.Long.zero$(LongMapUtils$.MODULE$, key, mask)) {
                return new Bin<S>(prefix, mask, left.updateWith(key, value, f), right);
            }
            return new Bin(prefix, mask, left, right.updateWith(key, value, f));
        }
        if (this instanceof Tip) {
            Tip tip = (Tip)this;
            long key2 = tip.key();
            Object value2 = tip.value();
            if (key == key2) {
                return new Tip<S>(key, f.apply(value2, value));
            }
            return LongMapUtils$.MODULE$.join(key, new Tip<S>(key, value), key2, this);
        }
        if (((Object)LongMap$Nil$.MODULE$).equals(this)) {
            return new Tip<S>(key, value);
        }
        throw new MatchError(this);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public LongMap<T> removed(long key) {
        if (this instanceof Bin) {
            void $minus_key;
            Bin bin = (Bin)this;
            long prefix = bin.prefix();
            long mask = bin.mask();
            LongMap left = bin.left();
            LongMap right = bin.right();
            if (!BitOperations.Long.hasMatch$(LongMapUtils$.MODULE$, key, prefix, mask)) {
                return this;
            }
            if (BitOperations.Long.zero$(LongMapUtils$.MODULE$, key, mask)) {
                void $minus_key2;
                Long l = key;
                if (left == null) {
                    throw null;
                }
                l = null;
                return LongMapUtils$.MODULE$.bin(prefix, mask, (LongMap)left.removed($minus_key2), right);
            }
            Long l = key;
            if (right == null) {
                throw null;
            }
            l = null;
            return LongMapUtils$.MODULE$.bin(prefix, mask, left, (LongMap)right.removed($minus_key));
        }
        if (this instanceof Tip) {
            long key2 = ((Tip)this).key();
            if (key == key2) {
                return LongMap$Nil$.MODULE$;
            }
            return this;
        }
        if (((Object)LongMap$Nil$.MODULE$).equals(this)) {
            return LongMap$Nil$.MODULE$;
        }
        throw new MatchError(this);
    }

    public <S> LongMap<S> modifyOrRemove(Function2<Object, T, Option<S>> f) {
        if (this instanceof Bin) {
            Bin bin = (Bin)this;
            long prefix = bin.prefix();
            long mask = bin.mask();
            LongMap<T> left = bin.left();
            LongMap<T> right = bin.right();
            LongMap<S> newleft = left.modifyOrRemove(f);
            LongMap<S> newright = right.modifyOrRemove(f);
            if (left == newleft && right == newright) {
                return this;
            }
            return LongMapUtils$.MODULE$.bin(prefix, mask, newleft, newright);
        }
        if (this instanceof Tip) {
            Tip tip = (Tip)this;
            long key = tip.key();
            Object value = tip.value();
            Option<S> option = f.apply(key, value);
            if (None$.MODULE$.equals(option)) {
                return LongMap$Nil$.MODULE$;
            }
            if (option instanceof Some) {
                Object value2 = ((Some)option).value();
                if (value == value2) {
                    return this;
                }
                return new Tip(key, value2);
            }
            throw new MatchError(option);
        }
        if (((Object)LongMap$Nil$.MODULE$).equals(this)) {
            return LongMap$Nil$.MODULE$;
        }
        throw new MatchError(this);
    }

    public <S> LongMap<S> unionWith(LongMap<S> that, Function3<Object, S, S, S> f) {
        Tuple2<LongMap, LongMap<S>> tuple2 = new Tuple2<LongMap, LongMap<S>>(this, that);
        if (this instanceof Bin) {
            Bin bin = (Bin)this;
            long p1 = bin.prefix();
            long m1 = bin.mask();
            LongMap l1 = bin.left();
            LongMap r1 = bin.right();
            if (that instanceof Bin) {
                Bin bin2 = (Bin)that;
                long p2 = bin2.prefix();
                long m22 = bin2.mask();
                LongMap l2 = bin2.left();
                LongMap r2 = bin2.right();
                if (BitOperations.Long.unsignedCompare$(LongMapUtils$.MODULE$, m22, m1)) {
                    if (!BitOperations.Long.hasMatch$(LongMapUtils$.MODULE$, p2, p1, m1)) {
                        return LongMapUtils$.MODULE$.join(p1, this, p2, bin2);
                    }
                    if (BitOperations.Long.zero$(LongMapUtils$.MODULE$, p2, m1)) {
                        return new Bin<S>(p1, m1, l1.unionWith(bin2, f), r1);
                    }
                    return new Bin(p1, m1, l1, r1.unionWith(bin2, f));
                }
                if (BitOperations.Long.unsignedCompare$(LongMapUtils$.MODULE$, m1, m22)) {
                    if (!BitOperations.Long.hasMatch$(LongMapUtils$.MODULE$, p1, p2, m22)) {
                        return LongMapUtils$.MODULE$.join(p1, this, p2, bin2);
                    }
                    if (BitOperations.Long.zero$(LongMapUtils$.MODULE$, p1, m22)) {
                        return new Bin(p2, m22, this.unionWith(l2, f), r2);
                    }
                    return new Bin(p2, m22, l2, this.unionWith(r2, f));
                }
                if (p1 == p2) {
                    return new Bin(p1, m1, l1.unionWith(l2, f), r1.unionWith(r2, f));
                }
                return LongMapUtils$.MODULE$.join(p1, this, p2, bin2);
            }
        }
        if (this instanceof Tip) {
            Tip tip = (Tip)this;
            long key = tip.key();
            Object value = tip.value();
            return that.updateWith(key, value, (Function2<Object, Object, Object> & Serializable)(x, y) -> f.apply(BoxesRunTime.boxToLong(key), y, x));
        }
        if (that instanceof Tip) {
            Tip tip = (Tip)that;
            long key = tip.key();
            Object value = tip.value();
            return this.updateWith(key, value, (Function2<Object, Object, Object> & Serializable)(x, y) -> f.apply(BoxesRunTime.boxToLong(key), x, y));
        }
        if (((Object)LongMap$Nil$.MODULE$).equals(this)) {
            return that;
        }
        if (((Object)LongMap$Nil$.MODULE$).equals(that)) {
            return this;
        }
        throw new MatchError(tuple2);
    }

    public <S, R> LongMap<R> intersectionWith(LongMap<S> that, Function3<Object, T, S, R> f) {
        if (this instanceof Bin) {
            Bin bin = (Bin)this;
            long p1 = bin.prefix();
            long m1 = bin.mask();
            LongMap<T> l1 = bin.left();
            LongMap<T> r1 = bin.right();
            if (that instanceof Bin) {
                Bin bin2 = (Bin)that;
                long p2 = bin2.prefix();
                long m22 = bin2.mask();
                LongMap l2 = bin2.left();
                LongMap r2 = bin2.right();
                if (BitOperations.Long.unsignedCompare$(LongMapUtils$.MODULE$, m22, m1)) {
                    if (!BitOperations.Long.hasMatch$(LongMapUtils$.MODULE$, p2, p1, m1)) {
                        return LongMap$Nil$.MODULE$;
                    }
                    if (BitOperations.Long.zero$(LongMapUtils$.MODULE$, p2, m1)) {
                        return l1.intersectionWith(bin2, f);
                    }
                    return r1.intersectionWith(bin2, f);
                }
                if (m1 == m22) {
                    return LongMapUtils$.MODULE$.bin(p1, m1, l1.intersectionWith(l2, f), r1.intersectionWith(r2, f));
                }
                if (!BitOperations.Long.hasMatch$(LongMapUtils$.MODULE$, p1, p2, m22)) {
                    return LongMap$Nil$.MODULE$;
                }
                if (BitOperations.Long.zero$(LongMapUtils$.MODULE$, p1, m22)) {
                    return this.intersectionWith(l2, f);
                }
                return this.intersectionWith(r2, f);
            }
        }
        if (this instanceof Tip) {
            Tip tip = (Tip)this;
            long key = tip.key();
            Object value = tip.value();
            Option<S> option = that.get(key);
            if (None$.MODULE$.equals(option)) {
                return LongMap$Nil$.MODULE$;
            }
            if (option instanceof Some) {
                Object value2 = ((Some)option).value();
                return new Tip<R>(key, f.apply(key, value, value2));
            }
            throw new MatchError(option);
        }
        if (that instanceof Tip) {
            Tip tip = (Tip)that;
            long key = tip.key();
            Object value = tip.value();
            Option<T> option = this.get(key);
            if (None$.MODULE$.equals(option)) {
                return LongMap$Nil$.MODULE$;
            }
            if (option instanceof Some) {
                Object value2 = ((Some)option).value();
                return new Tip<R>(key, f.apply(key, value2, value));
            }
            throw new MatchError(option);
        }
        return LongMap$Nil$.MODULE$;
    }

    public <R> LongMap<T> intersection(LongMap<R> that) {
        return this.intersectionWith(that, (Function3<Object, Object, Object, Object> & Serializable)(key, value, value2) -> value);
    }

    @Override
    public <S> LongMap<S> $plus$plus(LongMap<S> that) {
        return this.unionWith(that, (Function3<Object, Object, Object, Object> & Serializable)(key, x, y) -> y);
    }

    public final long firstKey() {
        while (this_ instanceof Bin) {
            LongMap this_ = ((Bin)this_).left();
        }
        if (this_ instanceof Tip) {
            return ((Tip)this_).key();
        }
        if (((Object)LongMap$Nil$.MODULE$).equals(this_)) {
            throw new IllegalStateException("Empty set");
        }
        throw new MatchError(this_);
    }

    public final long lastKey() {
        while (this_ instanceof Bin) {
            LongMap this_ = ((Bin)this_).right();
        }
        if (this_ instanceof Tip) {
            return ((Tip)this_).key();
        }
        if (((Object)LongMap$Nil$.MODULE$).equals(this_)) {
            throw new IllegalStateException("Empty set");
        }
        throw new MatchError(this_);
    }

    @Override
    public <V2> LongMap<V2> map(Function1<Tuple2<Object, T>, Tuple2<Object, V2>> f) {
        return LongMap$.MODULE$.from(new View.Map<Tuple2<Object, T>, Tuple2<Object, V2>>(this, f));
    }

    @Override
    public <V2> LongMap<V2> flatMap(Function1<Tuple2<Object, T>, IterableOnce<Tuple2<Object, V2>>> f) {
        return LongMap$.MODULE$.from(new View.FlatMap<Tuple2<Object, T>, Tuple2<Object, V2>>(this, f));
    }

    @Override
    public <V1> LongMap<V1> concat(IterableOnce<Tuple2<Object, V1>> that) {
        return (LongMap)StrictOptimizedMapOps.concat$(this, that);
    }

    @Override
    public <V1> LongMap<V1> $plus$plus(IterableOnce<Tuple2<Object, V1>> that) {
        return this.concat((IterableOnce)that);
    }

    @Override
    public <V2> LongMap<V2> collect(PartialFunction<Tuple2<Object, T>, Tuple2<Object, V2>> pf) {
        ImmutableBuilder strictOptimizedCollect_b = new /* invalid duplicate definition of identical inner class */;
        Object strictOptimizedCollect_marker = Statics.pfMarker;
        Iterator strictOptimizedCollect_it = this.iterator();
        while (strictOptimizedCollect_it.hasNext()) {
            Object strictOptimizedCollect_elem = strictOptimizedCollect_it.next();
            Object strictOptimizedCollect_v = pf.applyOrElse(strictOptimizedCollect_elem, arg_0 -> StrictOptimizedIterableOps.$anonfun$strictOptimizedCollect$1(strictOptimizedCollect_marker, arg_0));
            if (strictOptimizedCollect_marker == strictOptimizedCollect_v) continue;
            strictOptimizedCollect_b.addOne((Tuple2)strictOptimizedCollect_v);
        }
        return (LongMap)strictOptimizedCollect_b.result();
    }

    public Object writeReplace() {
        return new DefaultSerializationProxy<Tuple2<Object, Object>>(LongMap$ToFactory$.MODULE$, this);
    }

    public static class Bin<T>
    extends LongMap<T>
    implements Product {
        private final long prefix;
        private final long mask;
        private final LongMap<T> left;
        private final LongMap<T> right;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public long prefix() {
            return this.prefix;
        }

        public long mask() {
            return this.mask;
        }

        public LongMap<T> left() {
            return this.left;
        }

        public LongMap<T> right() {
            return this.right;
        }

        public <S> LongMap<S> bin(LongMap<S> left, LongMap<S> right) {
            if (this.left() == left && this.right() == right) {
                return this;
            }
            return new Bin<S>(this.prefix(), this.mask(), left, right);
        }

        public <T> Bin<T> copy(long prefix, long mask, LongMap<T> left, LongMap<T> right) {
            return new Bin<T>(prefix, mask, left, right);
        }

        public <T> long copy$default$1() {
            return this.prefix();
        }

        public <T> long copy$default$2() {
            return this.mask();
        }

        public <T> LongMap<T> copy$default$3() {
            return this.left();
        }

        public <T> LongMap<T> copy$default$4() {
            return this.right();
        }

        @Override
        public String productPrefix() {
            return "Bin";
        }

        @Override
        public int productArity() {
            return 4;
        }

        @Override
        public Object productElement(int x$1) {
            switch (x$1) {
                case 0: {
                    return this.prefix();
                }
                case 1: {
                    return this.mask();
                }
                case 2: {
                    return this.left();
                }
                case 3: {
                    return this.right();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return new AbstractIterator<T>(this){
                private int c;
                private final int cmax;
                private final Product x$2;

                public boolean hasNext() {
                    return this.c < this.cmax;
                }

                public T next() {
                    Object result = this.x$2.productElement(this.c);
                    ++this.c;
                    return (T)result;
                }
                {
                    this.x$2 = x$2;
                    this.c = 0;
                    this.cmax = x$2.productArity();
                }
            };
        }

        @Override
        public String productElementName(int x$1) {
            switch (x$1) {
                case 0: {
                    return "prefix";
                }
                case 1: {
                    return "mask";
                }
                case 2: {
                    return "left";
                }
                case 3: {
                    return "right";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public Bin(long prefix, long mask, LongMap<T> left, LongMap<T> right) {
            this.prefix = prefix;
            this.mask = mask;
            this.left = left;
            this.right = right;
        }
    }

    public static class Tip<T>
    extends LongMap<T>
    implements Product {
        private final long key;
        private final T value;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public long key() {
            return this.key;
        }

        public T value() {
            return this.value;
        }

        public <S> Tip<S> withValue(S s2) {
            if (s2 == this.value()) {
                return this;
            }
            return new Tip<S>(this.key(), s2);
        }

        public <T> Tip<T> copy(long key, T value) {
            return new Tip<T>(key, value);
        }

        public <T> long copy$default$1() {
            return this.key();
        }

        public <T> T copy$default$2() {
            return this.value();
        }

        @Override
        public String productPrefix() {
            return "Tip";
        }

        @Override
        public int productArity() {
            return 2;
        }

        @Override
        public Object productElement(int x$1) {
            switch (x$1) {
                case 0: {
                    return this.key();
                }
                case 1: {
                    return this.value();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return new /* invalid duplicate definition of identical inner class */;
        }

        @Override
        public String productElementName(int x$1) {
            switch (x$1) {
                case 0: {
                    return "key";
                }
                case 1: {
                    return "value";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public Tip(long key, T value) {
            this.key = key;
            this.value = value;
        }
    }
}

