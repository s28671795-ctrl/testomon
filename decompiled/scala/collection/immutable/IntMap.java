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
import scala.collection.immutable.IntMap$;
import scala.collection.immutable.IntMap$Nil$;
import scala.collection.immutable.IntMap$ToBuildFrom$;
import scala.collection.immutable.IntMap$ToFactory$;
import scala.collection.immutable.IntMapEntryIterator;
import scala.collection.immutable.IntMapKeyIterator;
import scala.collection.immutable.IntMapUtils$;
import scala.collection.immutable.IntMapValueIterator;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.List;
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
@ScalaSignature(bytes="\u0006\u0005\u0019UqA\u0002>|\u0011\u0003\t)AB\u0004\u0002\nmD\t!a\u0003\t\u000f\u0005\u0015\u0012\u0001\"\u0001\u0002(!9\u0011\u0011F\u0001\u0005\u0002\u0005-\u0002b\u0002Cd\u0003\u0011\u0005A\u0011\u001a\u0005\b\u0005k\u000bA\u0011\u0001Cl\u0011\u001d!i/\u0001C\u0001\t_<\u0001\"\"\u0001\u0002\u0011\u0003[H\u0011\u000e\u0004\t\tC\n\u0001\u0012Q>\u0005d!9\u0011Q\u0005\u0005\u0005\u0002\u0011\u001d\u0004b\u0002C6\u0011\u0011\u0005CQ\u000e\u0005\n\t\u0007B\u0011\u0011!C!\u0005\u0017B\u0011\u0002\"\u0012\t\u0003\u0003%\t\u0001b\u0012\t\u0013\u00115\u0003\"!A\u0005\u0002\u0011E\u0004\"\u0003C+\u0011\u0005\u0005I\u0011\tC,\r\u001d!)(\u0001!|\toB!Ba$\u0010\u0005+\u0007I\u0011\u0001B3\u0011)!\ti\u0004B\tB\u0003%\u00111\b\u0005\u000b\u0005?|!Q3A\u0005\u0002\u0011\r\u0005B\u0003CC\u001f\tE\t\u0015!\u0003\u0005~!9\u0011QE\b\u0005\u0002\u0011\u001d\u0005b\u0002CH\u001f\u0011\u0005A\u0011\u0013\u0005\n\t\u001by\u0011\u0011!C\u0001\tCC\u0011\u0002b\t\u0010#\u0003%\t\u0001b,\t\u0013\u00115r\"%A\u0005\u0002\u0011M\u0006\"\u0003C\"\u001f\u0005\u0005I\u0011\tB&\u0011%!)eDA\u0001\n\u0003!9\u0005C\u0005\u0005N=\t\t\u0011\"\u0001\u0005<\"IAQK\b\u0002\u0002\u0013\u0005Cq\u000b\u0005\n\t7z\u0011\u0011!C!\t\u007f;!\"b\u0001\u0002\u0003\u0003E\ta_C\u0003\r)!)(AA\u0001\u0012\u0003YXq\u0001\u0005\b\u0003KyB\u0011AC\u0005\u0011%)YaHA\u0001\n\u000b*i\u0001C\u0005\u00036~\t\t\u0011\"!\u0006\u0010!IQQD\u0010\u0002\u0002\u0013\u0005Uq\u0004\u0005\n\u0007\u000b|\u0012\u0011!C\u0005\u000bc1qaa3\u0002\u0001n\u001ci\r\u0003\u0006\u0004^\u0016\u0012)\u001a!C\u0001\u0005KB!ba8&\u0005#\u0005\u000b\u0011BA\u001e\u0011)\u0019\t/\nBK\u0002\u0013\u0005!Q\r\u0005\u000b\u0007G,#\u0011#Q\u0001\n\u0005m\u0002BCBsK\tU\r\u0011\"\u0001\u0004h\"Q1\u0011^\u0013\u0003\u0012\u0003\u0006Ia!5\t\u0015\r-XE!f\u0001\n\u0003\u00199\u000f\u0003\u0006\u0004n\u0016\u0012\t\u0012)A\u0005\u0007#Dq!!\n&\t\u0003\u0019y\u000fC\u0004\u0004~\u0016\"\taa@\t\u0013\u00115Q%!A\u0005\u0002\u0011=\u0001\"\u0003C\u0012KE\u0005I\u0011\u0001C\u0013\u0011%!i#JI\u0001\n\u0003!y\u0003C\u0005\u00054\u0015\n\n\u0011\"\u0001\u00056!IAQH\u0013\u0012\u0002\u0013\u0005Aq\b\u0005\n\t\u0007*\u0013\u0011!C!\u0005\u0017B\u0011\u0002\"\u0012&\u0003\u0003%\t\u0001b\u0012\t\u0013\u00115S%!A\u0005\u0002\u0011=\u0003\"\u0003C+K\u0005\u0005I\u0011\tC,\u0011%!Y&JA\u0001\n\u0003\"if\u0002\u0006\u0006:\u0005\t\t\u0011#\u0001|\u000bw1!ba3\u0002\u0003\u0003E\ta_C\u001f\u0011\u001d\t)c\u000fC\u0001\u000b\u007fA\u0011\"b\u0003<\u0003\u0003%)%\"\u0004\t\u0013\tU6(!A\u0005\u0002\u0016\u0005\u0003\"CC\u000fw\u0005\u0005I\u0011QC+\u0011%\u0019)mOA\u0001\n\u0013)\t\u0004C\u0004\u0006l\u0005!\t!\"\u001c\t\u000f\u0015m\u0014\u0001b\u0001\u0006~\u001dAQ1S\u0001!\u0012\u0013))J\u0002\u0005\u0006\u0018\u0006\u0001\u000b\u0012BCM\u0011\u001d\t)\u0003\u0012C\u0001\u000bCCq!!$E\t\u0003)\u0019\u000bC\u0004\u0006l\u0011#\t!b+\t\u0013\r\u0015G)!A\u0005\n\u0015E\u0002bBC^\u0003\u0011\rQQX\u0004\t\u000b'\f\u0001\u0015#\u0003\u0006V\u001aAQq[\u0001!\u0012\u0013)I\u000eC\u0004\u0002&-#\t!\"8\t\u000f\u000555\n\"\u0001\u0006`\"9Q1N&\u0005\u0002\u0015\u001d\bbBCv\u0003\u0011\rQQ\u001e\u0005\b\u000bw\fA1AC\u007f\u0011%\u0019)-AA\u0001\n\u0013)\tDB\u0004\u0002\nm\f\t#!\r\t\u000f\u0005\u0015\"\u000b\"\u0001\u0002\f\"9\u0011Q\u0012*\u0005R\u0005=\u0005bBA\\%\u0012E\u0011\u0011\u0018\u0005\b\u0003\u0017\u0014F\u0011KAg\u0011\u001d\tIC\u0015C!\u0003;Dq!a8S\t\u0003\n\t\u000fC\u0004\u0002jJ#\t!a;\t\u000f\u0005M(\u000b\"\u0012\u0002v\"9!q\u0002*\u0005B\tE\u0001b\u0002B\u0011%\u0012\u0005#1\u0005\u0005\b\u0005O\u0011FQ\u0001B\u0015\u0011\u001d\u0011)D\u0015C!\u0005oAqAa\u000fS\t\u000b\u0011i\u0004\u0003\u0005\u0003JI\u0003K\u0011\u000bB&\u0011\u001d\u0011IF\u0015C!\u00057BqAa\u0019S\t\u0003\u0012)\u0007C\u0004\u0003hI#\tE!\u001b\t\u000f\t=$\u000b\"\u0011\u0003r!9!\u0011\u0011*\u0005F\t\u0015\u0004b\u0002BB%\u0012\u0015!Q\u0011\u0005\b\u00057\u0013FQ\tBO\u0011\u001d\u0011)L\u0015C#\u0005oCqA!0S\t\u0003\u0012y\fC\u0004\u0003PJ#\tE!5\t\u000f\t\u0005(\u000b\"\u0001\u0003d\"9!1\u001f*\u0005\u0002\tU\bbBB\u0004%\u0012\u00053\u0011\u0002\u0005\b\u0007;\u0011F\u0011IB\u0010\u0011\u001d\u0019yC\u0015C\u0001\u0007cAqaa\u0012S\t\u0003\u0019I\u0005C\u0004\u0004\\I#\ta!\u0018\t\u000f\r\u0005$\u000b\"\u0001\u0004d!911\u000f*\u0005\u0002\rU\u0004bBBE%\u0012\u000511\u0012\u0005\b\u0007G\u0013F\u0011ABS\u0011\u001d\u0019iB\u0015C\u0001\u0007cCqa!0S\t\u000b\u0011)\u0007C\u0004\u0004BJ#)A!\u001a\t\u0011\r\u0015'\u000b)C\t\u0007\u000f\fa!\u00138u\u001b\u0006\u0004(B\u0001?~\u0003%IW.\\;uC\ndWM\u0003\u0002\u007f\u007f\u0006Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0005\u0005\u0005\u0011!B:dC2\f7\u0001\u0001\t\u0004\u0003\u000f\tQ\"A>\u0003\r%sG/T1q'\u0015\t\u0011QBA\u000b!\u0011\ty!!\u0005\u000e\u0003}L1!a\u0005\u0000\u0005\u0019\te.\u001f*fMB!\u0011qCA\u0011\u001b\t\tIB\u0003\u0003\u0002\u001c\u0005u\u0011AA5p\u0015\t\ty\"\u0001\u0003kCZ\f\u0017\u0002BA\u0012\u00033\u0011AbU3sS\u0006d\u0017N_1cY\u0016\fa\u0001P5oSRtDCAA\u0003\u0003\u0015)W\u000e\u001d;z+\u0011\ti\u0003\"2\u0016\u0005\u0005=\u0002#BA\u0004%\u0012\rW\u0003BA\u001a\u0003+\u001arAUA\u001b\u0003O\n)\b\u0005\u0005\u0002\b\u0005]\u00121HA)\u0013\r\tId\u001f\u0002\f\u0003\n\u001cHO]1di6\u000b\u0007\u000f\u0005\u0003\u0002>\u0005\rc\u0002BA\u0004\u0003\u007fI1!!\u0011|\u0003-Ie\u000e^'baV#\u0018\u000e\\:\n\t\u0005\u0015\u0013q\t\u0002\u0004\u0013:$\u0018\u0002BA#\u0003\u0013RA!a\u0013\u0002N\u0005i!)\u001b;Pa\u0016\u0014\u0018\r^5p]NT1!a\u0014~\u0003\u001d9WM\\3sS\u000e\u0004B!a\u0015\u0002V1\u0001A\u0001CA,%\u0012\u0015\r!!\u0017\u0003\u0003Q\u000bB!a\u0017\u0002bA!\u0011qBA/\u0013\r\tyf \u0002\b\u001d>$\b.\u001b8h!\u0011\ty!a\u0019\n\u0007\u0005\u0015tPA\u0002B]f\u0004B\"a\u0002\u0002j\u0005m\u0012\u0011KA7\u0003gJ1!a\u001b|\u0005U\u0019FO]5di>\u0003H/[7ju\u0016$W*\u00199PaN\u0004B!a\u0002\u0002p%\u0019\u0011\u0011O>\u0003\u00075\u000b\u0007\u000fE\u0003\u0002\bI\u000b\t\u0006\u0005\u0003\u0002x\u0005\u001de\u0002BA=\u0003\u0007sA!a\u001f\u0002\u00026\u0011\u0011Q\u0010\u0006\u0005\u0003\u007f\n\u0019!\u0001\u0004=e>|GOP\u0005\u0003\u0003\u0003I1!!\"\u0000\u0003\u001d\u0001\u0018mY6bO\u0016LA!a\t\u0002\n*\u0019\u0011QQ@\u0015\u0005\u0005M\u0014\u0001\u00044s_6\u001c\u0006/Z2jM&\u001cG\u0003BA:\u0003#Cq!a%U\u0001\u0004\t)*\u0001\u0003d_2d\u0007CBAL\u00033\u000bi*D\u0001~\u0013\r\tY* \u0002\r\u0013R,'/\u00192mK>s7-\u001a\u0016\u0005\u0003?\u000b)\u000b\u0005\u0005\u0002\u0010\u0005\u0005\u00161HA)\u0013\r\t\u0019k \u0002\u0007)V\u0004H.\u001a\u001a,\u0005\u0005\u001d\u0006\u0003BAU\u0003gk!!a+\u000b\t\u00055\u0016qV\u0001\nk:\u001c\u0007.Z2lK\u0012T1!!-\u0000\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003k\u000bYKA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\f!\"\u001b8u\u001b\u0006\u0004hI]8n+\u0011\tY,!1\u0015\t\u0005u\u0016Q\u0019\t\u0006\u0003\u000f\u0011\u0016q\u0018\t\u0005\u0003'\n\t\rB\u0004\u0002DV\u0013\r!!\u0017\u0003\u0005Y\u0013\u0004bBAJ+\u0002\u0007\u0011q\u0019\t\u0007\u0003/\u000bI*!3\u0011\u0011\u0005=\u0011\u0011UA\u001e\u0003\u007f\u000b!C\\3x'B,7-\u001b4jG\n+\u0018\u000e\u001c3feV\u0011\u0011q\u001a\u0016\u0005\u0003#\f)\u000b\u0005\u0005\u0002T\u0006e\u0017qTA:\u001b\t\t)NC\u0002\u0002Xv\fq!\\;uC\ndW-\u0003\u0003\u0002\\\u0006U'a\u0002\"vS2$WM]\u000b\u0003\u0003g\na\u0001^8MSN$XCAAr!\u0019\t9!!:\u0002 &\u0019\u0011q]>\u0003\t1K7\u000f^\u0001\tSR,'/\u0019;peV\u0011\u0011Q\u001e\t\u0007\u0003/\u000by/a(\n\u0007\u0005EXP\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003\u001d1wN]3bG\",B!a>\u0003\fQ!\u0011\u0011`A\u0000!\u0011\ty!a?\n\u0007\u0005uxP\u0001\u0003V]&$\bb\u0002B\u00015\u0002\u0007!1A\u0001\u0002MBA\u0011q\u0002B\u0003\u0003?\u0013I!C\u0002\u0003\b}\u0014\u0011BR;oGRLwN\\\u0019\u0011\t\u0005M#1\u0002\u0003\b\u0005\u001bQ&\u0019AA-\u0005\u0005)\u0016\u0001\u00044pe\u0016\f7\r[#oiJLX\u0003\u0002B\n\u0005?!B!!?\u0003\u0016!9!\u0011A.A\u0002\t]\u0001CCA\b\u00053\tY$!\u0015\u0003\u001e%\u0019!1D@\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004\u0003BA*\u0005?!qA!\u0004\\\u0005\u0004\tI&\u0001\u0007lKf\u001c\u0018\n^3sCR|'/\u0006\u0002\u0003&A1\u0011qSAx\u0003w\t!BZ8sK\u0006\u001c\u0007nS3z+\u0011\u0011YCa\r\u0015\t\u0005e(Q\u0006\u0005\b\u0005\u0003i\u0006\u0019\u0001B\u0018!!\tyA!\u0002\u0002<\tE\u0002\u0003BA*\u0005g!qA!\u0004^\u0005\u0004\tI&\u0001\bwC2,Xm]%uKJ\fGo\u001c:\u0016\u0005\te\u0002CBAL\u0003_\f\t&\u0001\u0007g_J,\u0017m\u00195WC2,X-\u0006\u0003\u0003@\t\u001dC\u0003BA}\u0005\u0003BqA!\u0001`\u0001\u0004\u0011\u0019\u0005\u0005\u0005\u0002\u0010\t\u0015\u0011\u0011\u000bB#!\u0011\t\u0019Fa\u0012\u0005\u000f\t5qL1\u0001\u0002Z\u0005I1\r\\1tg:\u000bW.Z\u000b\u0003\u0005\u001b\u0002BAa\u0014\u0003V5\u0011!\u0011\u000b\u0006\u0005\u0005'\ni\"\u0001\u0003mC:<\u0017\u0002\u0002B,\u0005#\u0012aa\u0015;sS:<\u0017aB5t\u000b6\u0004H/_\u000b\u0003\u0005;\u0002B!a\u0004\u0003`%\u0019!\u0011M@\u0003\u000f\t{w\u000e\\3b]\u0006I1N\\8x]NK'0Z\u000b\u0003\u0003w\taAZ5mi\u0016\u0014H\u0003BA:\u0005WBqA!\u0001d\u0001\u0004\u0011i\u0007\u0005\u0005\u0002\u0010\t\u0015\u0011q\u0014B/\u0003%!(/\u00198tM>\u0014X.\u0006\u0003\u0003t\teD\u0003\u0002B;\u0005{\u0002R!a\u0002S\u0005o\u0002B!a\u0015\u0003z\u00119!1\u00103C\u0002\u0005e#!A*\t\u000f\t\u0005A\r1\u0001\u0003\u0000AQ\u0011q\u0002B\r\u0003w\t\tFa\u001e\u0002\tML'0Z\u0001\u0004O\u0016$H\u0003\u0002BD\u0005\u001b\u0003b!a\u0004\u0003\n\u0006E\u0013b\u0001BF\u007f\n1q\n\u001d;j_:DqAa$g\u0001\u0004\tY$A\u0002lKfD3A\u001aBJ!\u0011\u0011)Ja&\u000e\u0005\u0005=\u0016\u0002\u0002BM\u0003_\u0013q\u0001^1jYJ,7-A\u0005hKR|%/\u00127tKV!!q\u0014BR)\u0019\u0011\tKa*\u0003*B!\u00111\u000bBR\t\u001d\u0011Yh\u001ab\u0001\u0005K\u000bB!!\u0015\u0002b!9!qR4A\u0002\u0005m\u0002\u0002\u0003BVO\u0012\u0005\rA!,\u0002\u000f\u0011,g-Y;miB1\u0011q\u0002BX\u0005CK1A!-\u0000\u0005!a$-\u001f8b[\u0016t\u0004fA4\u0003\u0014\u0006)\u0011\r\u001d9msR!\u0011\u0011\u000bB]\u0011\u001d\u0011y\t\u001ba\u0001\u0003wA3\u0001\u001bBJ\u0003\u0015!\u0003\u000f\\;t+\u0011\u0011\tMa2\u0015\t\t\r'\u0011\u001a\t\u0006\u0003\u000f\u0011&Q\u0019\t\u0005\u0003'\u00129\rB\u0004\u0003|%\u0014\rA!*\t\u000f\t-\u0017\u000e1\u0001\u0003N\u0006\u00111N\u001e\t\t\u0003\u001f\t\t+a\u000f\u0003F\u00069Q\u000f\u001d3bi\u0016$W\u0003\u0002Bj\u00053$bA!6\u0003\\\nu\u0007#BA\u0004%\n]\u0007\u0003BA*\u00053$qAa\u001fk\u0005\u0004\u0011)\u000bC\u0004\u0003\u0010*\u0004\r!a\u000f\t\u000f\t}'\u000e1\u0001\u0003X\u0006)a/\u00197vK\u0006\u0019Q.\u00199\u0016\t\t\u0015(1\u001e\u000b\u0005\u0005O\u0014i\u000fE\u0003\u0002\bI\u0013I\u000f\u0005\u0003\u0002T\t-HaBAbW\n\u0007\u0011\u0011\f\u0005\b\u0005\u0003Y\u0007\u0019\u0001Bx!!\tyA!\u0002\u0002 \nE\b\u0003CA\b\u0003C\u000bYD!;\u0002\u000f\u0019d\u0017\r^'baV!!q\u001fB\u007f)\u0011\u0011IPa@\u0011\u000b\u0005\u001d!Ka?\u0011\t\u0005M#Q \u0003\b\u0003\u0007d'\u0019AA-\u0011\u001d\u0011\t\u0001\u001ca\u0001\u0007\u0003\u0001\u0002\"a\u0004\u0003\u0006\u0005}51\u0001\t\u0007\u0003/\u000bIj!\u0002\u0011\u0011\u0005=\u0011\u0011UA\u001e\u0005w\faaY8oG\u0006$X\u0003BB\u0006\u0007#!Ba!\u0004\u0004\u0016A)\u0011q\u0001*\u0004\u0010A!\u00111KB\t\t\u001d\u0019\u0019\"\u001cb\u0001\u0005K\u0013!AV\u0019\t\u000f\r]Q\u000e1\u0001\u0004\u001a\u0005!A\u000f[1u!\u0019\t9*!'\u0004\u001cAA\u0011qBAQ\u0003w\u0019y!\u0001\u0006%a2,8\u000f\n9mkN,Ba!\t\u0004(Q!11EB\u0015!\u0015\t9AUB\u0013!\u0011\t\u0019fa\n\u0005\u000f\rMaN1\u0001\u0003&\"91q\u00038A\u0002\r-\u0002CBAL\u00033\u001bi\u0003\u0005\u0005\u0002\u0010\u0005\u0005\u00161HB\u0013\u0003\u001d\u0019w\u000e\u001c7fGR,Baa\r\u0004:Q!1QGB\u001e!\u0015\t9AUB\u001c!\u0011\t\u0019f!\u000f\u0005\u000f\u0005\rwN1\u0001\u0002Z!91QH8A\u0002\r}\u0012A\u00019g!!\tya!\u0011\u0002 \u000e\u0015\u0013bAB\"\u007f\ny\u0001+\u0019:uS\u0006dg)\u001e8di&|g\u000e\u0005\u0005\u0002\u0010\u0005\u0005\u00161HB\u001c\u0003))\b\u000fZ1uK^KG\u000f[\u000b\u0005\u0007\u0017\u001a\t\u0006\u0006\u0005\u0004N\rM3QKB,!\u0015\t9AUB(!\u0011\t\u0019f!\u0015\u0005\u000f\tm\u0004O1\u0001\u0003&\"9!q\u00129A\u0002\u0005m\u0002b\u0002Bpa\u0002\u00071q\n\u0005\b\u0005\u0003\u0001\b\u0019AB-!)\tyA!\u0007\u0002R\r=3qJ\u0001\be\u0016lwN^3e)\u0011\t\u0019ha\u0018\t\u000f\t=\u0015\u000f1\u0001\u0002<\u0005qQn\u001c3jMf|%OU3n_Z,W\u0003BB3\u0007W\"Baa\u001a\u0004nA)\u0011q\u0001*\u0004jA!\u00111KB6\t\u001d\u0011YH\u001db\u0001\u00033BqA!\u0001s\u0001\u0004\u0019y\u0007\u0005\u0006\u0002\u0010\te\u00111HA)\u0007c\u0002b!a\u0004\u0003\n\u000e%\u0014!C;oS>tw+\u001b;i+\u0011\u00199h! \u0015\r\re4qPBA!\u0015\t9AUB>!\u0011\t\u0019f! \u0005\u000f\tm4O1\u0001\u0003&\"91qC:A\u0002\re\u0004b\u0002B\u0001g\u0002\u000711\u0011\t\r\u0003\u001f\u0019))a\u000f\u0004|\rm41P\u0005\u0004\u0007\u000f{(!\u0003$v]\u000e$\u0018n\u001c84\u0003AIg\u000e^3sg\u0016\u001cG/[8o/&$\b.\u0006\u0004\u0004\u000e\u000eu51\u0013\u000b\u0007\u0007\u001f\u001b9ja(\u0011\u000b\u0005\u001d!k!%\u0011\t\u0005M31\u0013\u0003\b\u0007+#(\u0019AA-\u0005\u0005\u0011\u0006bBB\fi\u0002\u00071\u0011\u0014\t\u0006\u0003\u000f\u001161\u0014\t\u0005\u0003'\u001ai\nB\u0004\u0003|Q\u0014\r!!\u0017\t\u000f\t\u0005A\u000f1\u0001\u0004\"Ba\u0011qBBC\u0003w\t\tfa'\u0004\u0012\u0006a\u0011N\u001c;feN,7\r^5p]V!1qUBX)\u0011\t\u0019h!+\t\u000f\r]Q\u000f1\u0001\u0004,B)\u0011q\u0001*\u0004.B!\u00111KBX\t\u001d\u0019)*\u001eb\u0001\u00033*Baa-\u0004:R!1QWB^!\u0015\t9AUB\\!\u0011\t\u0019f!/\u0005\u000f\tmdO1\u0001\u0003&\"91q\u0003<A\u0002\rU\u0016\u0001\u00034jeN$8*Z=)\u0007]\u0014\u0019*A\u0004mCN$8*Z=)\u0007a\u0014\u0019*\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002\u000e%\"!+\n\u0005\u0010\u0005\r\u0011\u0015N\\\u000b\u0005\u0007\u001f\u001c)nE\u0004&\u0007#\u001c9.!\u001e\u0011\u000b\u0005\u001d!ka5\u0011\t\u0005M3Q\u001b\u0003\t\u0003/*CQ1\u0001\u0002ZA!\u0011qBBm\u0013\r\u0019Yn \u0002\b!J|G-^2u\u0003\u0019\u0001(/\u001a4jq\u00069\u0001O]3gSb\u0004\u0013\u0001B7bg.\fQ!\\1tW\u0002\nA\u0001\\3giV\u00111\u0011[\u0001\u0006Y\u00164G\u000fI\u0001\u0006e&<\u0007\u000e^\u0001\u0007e&<\u0007\u000e\u001e\u0011\u0015\u0015\rE8Q_B|\u0007s\u001cY\u0010E\u0003\u0004t\u0016\u001a\u0019.D\u0001\u0002\u0011\u001d\u0019iN\fa\u0001\u0003wAqa!9/\u0001\u0004\tY\u0004C\u0004\u0004f:\u0002\ra!5\t\u000f\r-h\u00061\u0001\u0004R\u0006\u0019!-\u001b8\u0016\t\u0011\u0005Aq\u0001\u000b\u0007\t\u0007!I\u0001b\u0003\u0011\u000b\u0005\u001d!\u000b\"\u0002\u0011\t\u0005MCq\u0001\u0003\b\u0005wz#\u0019AA-\u0011\u001d\u0019)o\fa\u0001\t\u0007Aqaa;0\u0001\u0004!\u0019!\u0001\u0003d_BLX\u0003\u0002C\t\t/!\"\u0002b\u0005\u0005\u001a\u0011mAQ\u0004C\u0011!\u0015\u0019\u00190\nC\u000b!\u0011\t\u0019\u0006b\u0006\u0005\u000f\u0005]\u0003G1\u0001\u0002Z!I1Q\u001c\u0019\u0011\u0002\u0003\u0007\u00111\b\u0005\n\u0007C\u0004\u0004\u0013!a\u0001\u0003wA\u0011b!:1!\u0003\u0005\r\u0001b\b\u0011\u000b\u0005\u001d!\u000b\"\u0006\t\u0013\r-\b\u0007%AA\u0002\u0011}\u0011AD2paf$C-\u001a4bk2$H%M\u000b\u0005\tO!Y#\u0006\u0002\u0005*)\"\u00111HAS\t\u001d\t9&\rb\u0001\u00033\nabY8qs\u0012\"WMZ1vYR$#'\u0006\u0003\u0005(\u0011EBaBA,e\t\u0007\u0011\u0011L\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00134+\u0011!9\u0004b\u000f\u0016\u0005\u0011e\"\u0006BBi\u0003K#q!a\u00164\u0005\u0004\tI&\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001b\u0016\t\u0011]B\u0011\t\u0003\b\u0003/\"$\u0019AA-\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jq\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011A\u0011\n\t\u0005\u0003\u001f!Y%C\u0002\u0002F}\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0003\u0002b\u0011E\u0003\"\u0003C*o\u0005\u0005\t\u0019\u0001C%\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011A\u0011\f\t\u0007\u0003/\u000by/!\u0019\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0005\u001b\"y\u0006C\u0005\u0005Te\n\t\u00111\u0001\u0005J\t\u0019a*\u001b7\u0014\u000f!!)ga6\u0002vA)\u0011q\u0001*\u0002\\Q\u0011A\u0011\u000e\t\u0004\u0007gD\u0011AB3rk\u0006d7\u000f\u0006\u0003\u0003^\u0011=\u0004bBB\f\u0015\u0001\u0007\u0011\u0011\r\u000b\u0005\u0003C\"\u0019\bC\u0005\u0005T5\t\t\u00111\u0001\u0005J\t\u0019A+\u001b9\u0016\t\u0011eDqP\n\b\u001f\u0011m4q[A;!\u0015\t9A\u0015C?!\u0011\t\u0019\u0006b \u0005\u0011\u0005]s\u0002\"b\u0001\u00033\nAa[3zAU\u0011AQP\u0001\u0007m\u0006dW/\u001a\u0011\u0015\r\u0011%E1\u0012CG!\u0015\u0019\u0019p\u0004C?\u0011\u001d\u0011y\t\u0006a\u0001\u0003wAqAa8\u0015\u0001\u0004!i(A\u0005xSRDg+\u00197vKV!A1\u0013CN)\u0011!)\n\"(\u0011\u000b\u0011]u\u0002\"'\u000f\u0007\u0005\u001d\u0001\u0001\u0005\u0003\u0002T\u0011mEa\u0002B>+\t\u0007\u0011\u0011\f\u0005\b\t?+\u0002\u0019\u0001CM\u0003\u0005\u0019X\u0003\u0002CR\tS#b\u0001\"*\u0005,\u00125\u0006#BBz\u001f\u0011\u001d\u0006\u0003BA*\tS#q!a\u0016\u0017\u0005\u0004\tI\u0006C\u0005\u0003\u0010Z\u0001\n\u00111\u0001\u0002<!I!q\u001c\f\u0011\u0002\u0003\u0007AqU\u000b\u0005\tO!\t\fB\u0004\u0002X]\u0011\r!!\u0017\u0016\t\u0011UF\u0011X\u000b\u0003\toSC\u0001\" \u0002&\u00129\u0011q\u000b\rC\u0002\u0005eC\u0003BA1\t{C\u0011\u0002b\u0015\u001c\u0003\u0003\u0005\r\u0001\"\u0013\u0015\t\t5C\u0011\u0019\u0005\n\t'j\u0012\u0011!a\u0001\t\u0013\u0002B!a\u0015\u0005F\u00129\u0011qK\u0002C\u0002\u0005e\u0013!C:j]\u001edW\r^8o+\u0011!Y\r\"5\u0015\r\u00115G1\u001bCk!\u0015\t9A\u0015Ch!\u0011\t\u0019\u0006\"5\u0005\u000f\u0005]CA1\u0001\u0002Z!9!q\u0012\u0003A\u0002\u0005m\u0002b\u0002Bp\t\u0001\u0007AqZ\u000b\u0005\t3$y\u000e\u0006\u0003\u0005\\\u0012\u0005\b#BA\u0004%\u0012u\u0007\u0003BA*\t?$q!a\u0016\u0006\u0005\u0004\tI\u0006C\u0004\u0005d\u0016\u0001\r\u0001\":\u0002\u000b\u0015dW-\\:\u0011\r\u0005=Aq\u001dCv\u0013\r!Io \u0002\u000byI,\u0007/Z1uK\u0012t\u0004\u0003CA\b\u0003C\u000bY\u0004\"8\u0002\t\u0019\u0014x.\\\u000b\u0005\tc$9\u0010\u0006\u0003\u0005t\u0012m\b#BA\u0004%\u0012U\b\u0003BA*\to$q\u0001\"?\u0007\u0005\u0004\tIFA\u0001W\u0011\u001d\t\u0019J\u0002a\u0001\t{\u0004b!a&\u0002\u001a\u0012}\b\u0003CA\b\u0003C\u000bY\u0004\">\u0002\u00079KG.A\u0002USB\u00042aa= '\u0015y\u0012QBA\u000b)\t))!\u0001\u0005u_N#(/\u001b8h)\t\u0011i%\u0006\u0003\u0006\u0012\u0015]ACBC\n\u000b3)Y\u0002E\u0003\u0004t>))\u0002\u0005\u0003\u0002T\u0015]AaBA,E\t\u0007\u0011\u0011\f\u0005\b\u0005\u001f\u0013\u0003\u0019AA\u001e\u0011\u001d\u0011yN\ta\u0001\u000b+\tq!\u001e8baBd\u00170\u0006\u0003\u0006\"\u0015%B\u0003BC\u0012\u000bW\u0001b!a\u0004\u0003\n\u0016\u0015\u0002\u0003CA\b\u0003C\u000bY$b\n\u0011\t\u0005MS\u0011\u0006\u0003\b\u0003/\u001a#\u0019AA-\u0011%)icIA\u0001\u0002\u0004)y#A\u0002yIA\u0002Raa=\u0010\u000bO!\"!b\r\u0011\t\t=SQG\u0005\u0005\u000bo\u0011\tF\u0001\u0004PE*,7\r^\u0001\u0004\u0005&t\u0007cABzwM)1(!\u0004\u0002\u0016Q\u0011Q1H\u000b\u0005\u000b\u0007*I\u0005\u0006\u0006\u0006F\u0015-SQJC(\u000b'\u0002Raa=&\u000b\u000f\u0002B!a\u0015\u0006J\u00119\u0011q\u000b C\u0002\u0005e\u0003bBBo}\u0001\u0007\u00111\b\u0005\b\u0007Ct\u0004\u0019AA\u001e\u0011\u001d\u0019)O\u0010a\u0001\u000b#\u0002R!a\u0002S\u000b\u000fBqaa;?\u0001\u0004)\t&\u0006\u0003\u0006X\u0015\u0015D\u0003BC-\u000bO\u0002b!a\u0004\u0003\n\u0016m\u0003\u0003DA\b\u000b;\nY$a\u000f\u0006b\u0015\u0005\u0014bAC0\u007f\n1A+\u001e9mKR\u0002R!a\u0002S\u000bG\u0002B!a\u0015\u0006f\u00119\u0011qK C\u0002\u0005e\u0003\"CC\u0017\u007f\u0005\u0005\t\u0019AC5!\u0015\u0019\u00190JC2\u0003)qWm\u001e\"vS2$WM]\u000b\u0005\u000b_*9(\u0006\u0002\u0006rAA\u00111[Am\u000bg*I\b\u0005\u0005\u0002\u0010\u0005\u0005\u00161HC;!\u0011\t\u0019&b\u001e\u0005\u000f\u0011e\u0018I1\u0001\u0002ZA)\u0011q\u0001*\u0006v\u0005IAo\u001c$bGR|'/_\u000b\u0005\u000b\u007f*Y\t\u0006\u0003\u0006\u0002\u0016=\u0005\u0003CAL\u000b\u0007+9)\"$\n\u0007\u0015\u0015UPA\u0004GC\u000e$xN]=\u0011\u0011\u0005=\u0011\u0011UA\u001e\u000b\u0013\u0003B!a\u0015\u0006\f\u00129A\u0011 \"C\u0002\u0005e\u0003#BA\u0004%\u0016%\u0005bBCI\u0005\u0002\u0007AqS\u0001\u0006IVlW._\u0001\n)>4\u0015m\u0019;pef\u00042aa=E\u0005%!vNR1di>\u0014\u0018pE\u0004E\u0003\u001b)Y*!\u001e\u0011\u0011\u0005]U1QCO\u000b?\u0003\u0002\"a\u0004\u0002\"\u0006m\u0012Q\u0002\t\u0006\u0003\u000f\u0011\u0016Q\u0002\u000b\u0003\u000b+#B!b(\u0006&\"9Qq\u0015$A\u0002\u0015%\u0016AA5u!\u0019\t9*!'\u0006\u001eV\u0011QQ\u0016\t\t\u0003'\fI.\"(\u0006 \":A)\"-\u0003`\u0016]\u0006\u0003BA\b\u000bgK1!\".\u0000\u0005A\u0019VM]5bYZ+'o]5p]VKEIH\u0001\u0004Q\u001d\u0019U\u0011\u0017Bp\u000bo\u000b1\u0002^8Ck&dGM\u0012:p[V!QqXCf)\u0011)\t-b4\u0011\u0015\u0005]U1YA1\u000b\u000f,i-C\u0002\u0006Fv\u0014\u0011BQ;jY\u00124%o\\7\u0011\u0011\u0005=\u0011\u0011UA\u001e\u000b\u0013\u0004B!a\u0015\u0006L\u00129A\u0011`%C\u0002\u0005e\u0003#BA\u0004%\u0016%\u0007bBCi\u0013\u0002\u0007AqS\u0001\bM\u0006\u001cGo\u001c:z\u0003-!vNQ;jY\u00124%o\\7\u0011\u0007\rM8JA\u0006U_\n+\u0018\u000e\u001c3Ge>l7#B&\u0002\u000e\u0015m\u0007CCAL\u000b\u0007\f\t'\"(\u0006 R\u0011QQ\u001b\u000b\u0005\u000bC,)\u000f\u0006\u0003\u0006 \u0016\r\bbBCT\u001b\u0002\u0007Q\u0011\u0016\u0005\b\t[l\u0005\u0019AA1)\u0011)i+\";\t\u000f\u00115h\n1\u0001\u0002b\u0005y\u0011\u000e^3sC\ndWMR1di>\u0014\u00180\u0006\u0003\u0006p\u0016]XCACy!!\t9*b!\u0006t\u0016e\b\u0003CA\b\u0003C\u000bY$\">\u0011\t\u0005MSq\u001f\u0003\b\ts|%\u0019AA-!\u0015\t9AUC{\u0003=\u0011W/\u001b7e\rJ|W.\u00138u\u001b\u0006\u0004X\u0003BC\u0000\r#)\"A\"\u0001\u0011\u0015\u0005]U1\u0019D\u0002\r\u001b1\u0019\u0002\r\u0003\u0007\u0006\u0019%\u0001#BA\u0004%\u001a\u001d\u0001\u0003BA*\r\u0013!1Bb\u0003Q\u0003\u0003\u0005\tQ!\u0001\u0002Z\t\u0019q\fJ\u0019\u0011\u0011\u0005=\u0011\u0011UA\u001e\r\u001f\u0001B!a\u0015\u0007\u0012\u00119A\u0011 )C\u0002\u0005e\u0003#BA\u0004%\u001a=\u0001")
public abstract class IntMap<T>
extends AbstractMap<Object, T>
implements StrictOptimizedMapOps<Object, T, Map, IntMap<T>>,
Serializable {
    public static <V> BuildFrom<IntMap<?>, Tuple2<Object, V>, IntMap<V>> buildFromIntMap() {
        return IntMap$ToBuildFrom$.MODULE$;
    }

    public static <V> BuildFrom<Object, Tuple2<Object, V>, IntMap<V>> toBuildFrom(IntMap$ factory2) {
        return IntMap$ToBuildFrom$.MODULE$;
    }

    public static <V> Factory<Tuple2<Object, V>, IntMap<V>> toFactory(IntMap$ dummy) {
        return IntMap$ToFactory$.MODULE$;
    }

    public static <V> Builder<Tuple2<Object, V>, IntMap<V>> newBuilder() {
        return new ImmutableBuilder<Tuple2<Object, V>, IntMap<V>>(){

            public $anon$1 addOne(Tuple2<Object, V> elem) {
                this.elems_$eq(((IntMap)this.elems()).$plus(elem));
                return this;
            }
        };
    }

    public static <V> IntMap<V> from(IterableOnce<Tuple2<Object, V>> coll) {
        return IntMap$.MODULE$.from(coll);
    }

    public static <T> IntMap<T> singleton(int key, T value) {
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
    public Tuple2<IntMap<T>, IntMap<T>> partition(Function1<Tuple2<Object, T>, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<IntMap<T>, IntMap<T>> span(Function1<Tuple2<Object, T>, Object> p) {
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
    public IntMap<T> fromSpecific(IterableOnce<Tuple2<Object, T>> coll) {
        return this.intMapFrom(coll);
    }

    public <V2> IntMap<V2> intMapFrom(IterableOnce<Tuple2<Object, V2>> coll) {
        ImmutableBuilder b = new /* invalid duplicate definition of identical inner class */;
        int sizeHint_delta = 0;
        Builder.sizeHint$(b, coll, sizeHint_delta);
        b.addAll(coll);
        return (IntMap)b.result();
    }

    @Override
    public Builder<Tuple2<Object, T>, IntMap<T>> newSpecificBuilder() {
        return new ImmutableBuilder<Tuple2<Object, T>, IntMap<T>>(this){

            public $anon$2 addOne(Tuple2<Object, T> elem) {
                this.elems_$eq(((IntMap)this.elems()).$plus(elem));
                return this;
            }
        };
    }

    @Override
    public IntMap<T> empty() {
        return IntMap$Nil$.MODULE$;
    }

    @Override
    public List<Tuple2<Object, T>> toList() {
        Tip tip;
        Bin bin;
        ListBuffer buffer = new ListBuffer();
        Function1<Tuple2, ListBuffer> & Serializable foreach_f = (Function1<Tuple2, ListBuffer> & Serializable)x$1 -> (ListBuffer)buffer.$plus$eq(x$1);
        IntMap foreach_this = this;
        while (foreach_this instanceof Bin) {
            bin = (Bin)foreach_this;
            IntMap foreach_left = bin.left();
            IntMap foreach_right = bin.right();
            foreach_left.foreach((Function1<Tuple2<Object, T>, U>)foreach_f);
            foreach_this = foreach_right;
        }
        if (foreach_this instanceof Tip) {
            tip = (Tip)foreach_this;
            int foreach_key = tip.key();
            Object foreach_value = tip.value();
            Tuple2 tuple2 = new Tuple2(foreach_key, foreach_value);
            ListBuffer cfr_ignored_0 = (ListBuffer)buffer.addOne(tuple2);
        } else if (!((Object)IntMap$Nil$.MODULE$).equals(foreach_this)) {
            throw new MatchError(foreach_this);
        }
        Object var2_3 = null;
        Object var3_2 = null;
        bin = null;
        Object var5_5 = null;
        Object var6_6 = null;
        tip = null;
        Object var9_9 = null;
        return buffer.toList();
    }

    @Override
    public Iterator<Tuple2<Object, T>> iterator() {
        if (((Object)IntMap$Nil$.MODULE$).equals(this)) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        return new IntMapEntryIterator(this);
    }

    @Override
    public final <U> void foreach(Function1<Tuple2<Object, T>, U> f) {
        while (this_ instanceof Bin) {
            Bin bin = (Bin)this_;
            IntMap<T> left = bin.left();
            IntMap right = bin.right();
            left.foreach(f);
            IntMap this_ = right;
        }
        if (this_ instanceof Tip) {
            Tip tip = (Tip)this_;
            int key = tip.key();
            Object value = tip.value();
            f.apply(new Tuple2(key, value));
            return;
        }
        if (((Object)IntMap$Nil$.MODULE$).equals(this_)) {
            return;
        }
        throw new MatchError(this_);
    }

    @Override
    public <U> void foreachEntry(Function2<Object, T, U> f) {
        if (this instanceof Bin) {
            Bin bin = (Bin)this;
            IntMap<T> left = bin.left();
            IntMap<T> right = bin.right();
            left.foreachEntry(f);
            right.foreachEntry(f);
            return;
        }
        if (this instanceof Tip) {
            Tip tip = (Tip)this;
            int key = tip.key();
            Object value = tip.value();
            f.apply(key, value);
            return;
        }
        if (((Object)IntMap$Nil$.MODULE$).equals(this)) {
            return;
        }
        throw new MatchError(this);
    }

    @Override
    public Iterator<Object> keysIterator() {
        if (((Object)IntMap$Nil$.MODULE$).equals(this)) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        return new IntMapKeyIterator(this);
    }

    public final <U> void foreachKey(Function1<Object, U> f) {
        while (this_ instanceof Bin) {
            Bin bin = (Bin)this_;
            IntMap left = bin.left();
            IntMap right = bin.right();
            left.foreachKey(f);
            IntMap this_ = right;
        }
        if (this_ instanceof Tip) {
            int key = ((Tip)this_).key();
            f.apply(key);
            return;
        }
        if (((Object)IntMap$Nil$.MODULE$).equals(this_)) {
            return;
        }
        throw new MatchError(this_);
    }

    @Override
    public Iterator<T> valuesIterator() {
        if (((Object)IntMap$Nil$.MODULE$).equals(this)) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        return new IntMapValueIterator(this);
    }

    public final <U> void foreachValue(Function1<T, U> f) {
        while (this_ instanceof Bin) {
            Bin bin = (Bin)this_;
            IntMap<T> left = bin.left();
            IntMap right = bin.right();
            left.foreachValue(f);
            IntMap this_ = right;
        }
        if (this_ instanceof Tip) {
            Object value = ((Tip)this_).value();
            f.apply(value);
            return;
        }
        if (((Object)IntMap$Nil$.MODULE$).equals(this_)) {
            return;
        }
        throw new MatchError(this_);
    }

    @Override
    public String className() {
        return "IntMap";
    }

    @Override
    public boolean isEmpty() {
        return this == IntMap$Nil$.MODULE$;
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
    public IntMap<T> filter(Function1<Tuple2<Object, T>, Object> f) {
        if (this instanceof Bin) {
            void var7_8;
            Bin bin = (Bin)this;
            int prefix = bin.prefix();
            int mask = bin.mask();
            IntMap left = bin.left();
            IntMap right = bin.right();
            Object object = right.filter((Function1)f);
            Object newleft = left.filter((Function1)f);
            if (left == var7_8 && right == object) {
                return this;
            }
            return IntMapUtils$.MODULE$.bin(prefix, mask, var7_8, object);
        }
        if (this instanceof Tip) {
            Tip tip = (Tip)this;
            int key = tip.key();
            Object value = tip.value();
            if (BoxesRunTime.unboxToBoolean(f.apply(new Tuple2(key, value)))) {
                return this;
            }
            return IntMap$Nil$.MODULE$;
        }
        if (((Object)IntMap$Nil$.MODULE$).equals(this)) {
            return IntMap$Nil$.MODULE$;
        }
        throw new MatchError(this);
    }

    @Override
    public <S> IntMap<S> transform(Function2<Object, T, S> f) {
        if (this instanceof Bin) {
            Bin bin = (Bin)this;
            IntMap left = bin.left();
            IntMap right = bin.right();
            return bin.bin(left.transform((Function2)f), right.transform((Function2)f));
        }
        if (this instanceof Tip) {
            Tip tip = (Tip)this;
            int key = tip.key();
            Object value = tip.value();
            return tip.withValue(f.apply(key, value));
        }
        if (((Object)IntMap$Nil$.MODULE$).equals(this)) {
            return IntMap$Nil$.MODULE$;
        }
        throw new MatchError(this);
    }

    @Override
    public final int size() {
        if (((Object)IntMap$Nil$.MODULE$).equals(this)) {
            return 0;
        }
        if (this instanceof Tip) {
            return 1;
        }
        if (this instanceof Bin) {
            Bin bin = (Bin)this;
            IntMap left = bin.left();
            IntMap right = bin.right();
            return left.size() + right.size();
        }
        throw new MatchError(this);
    }

    @Override
    public final Option<T> get(int key) {
        while (this_ instanceof Bin) {
            IntMap this_;
            Bin bin = (Bin)this_;
            int mask = bin.mask();
            IntMap left = bin.left();
            IntMap right = bin.right();
            if (BitOperations.Int.zero$(IntMapUtils$.MODULE$, key, mask)) {
                this_ = left;
                continue;
            }
            this_ = right;
        }
        if (this_ instanceof Tip) {
            Tip tip = (Tip)this_;
            int key2 = tip.key();
            Object value = tip.value();
            if (key == key2) {
                return new Some(value);
            }
            return None$.MODULE$;
        }
        if (((Object)IntMap$Nil$.MODULE$).equals(this_)) {
            return None$.MODULE$;
        }
        throw new MatchError(this_);
    }

    @Override
    public final <S> S getOrElse(int key, Function0<S> function0) {
        while (true) {
            IntMap this_;
            if (((Object)IntMap$Nil$.MODULE$).equals(this_)) {
                return function0.apply();
            }
            if (this_ instanceof Tip) {
                Tip tip = (Tip)this_;
                int key2 = tip.key();
                Object value = tip.value();
                if (key == key2) {
                    return (S)value;
                }
                return function0.apply();
            }
            if (!(this_ instanceof Bin)) break;
            Bin bin = (Bin)this_;
            int mask = bin.mask();
            IntMap left = bin.left();
            IntMap right = bin.right();
            if (BitOperations.Int.zero$(IntMapUtils$.MODULE$, key, mask)) {
                this_ = left;
                continue;
            }
            this_ = right;
        }
        throw new MatchError(this_);
    }

    @Override
    public final T apply(int key) {
        while (this_ instanceof Bin) {
            IntMap this_;
            Bin bin = (Bin)this_;
            int mask = bin.mask();
            IntMap left = bin.left();
            IntMap right = bin.right();
            if (BitOperations.Int.zero$(IntMapUtils$.MODULE$, key, mask)) {
                this_ = left;
                continue;
            }
            this_ = right;
        }
        if (this_ instanceof Tip) {
            Tip tip = (Tip)this_;
            int key2 = tip.key();
            Object value = tip.value();
            if (key == key2) {
                return value;
            }
            throw new IllegalArgumentException("Key not found");
        }
        if (((Object)IntMap$Nil$.MODULE$).equals(this_)) {
            throw new IllegalArgumentException("key not found");
        }
        throw new MatchError(this_);
    }

    @Override
    public <S> IntMap<S> $plus(Tuple2<Object, S> kv) {
        return this.updated(kv._1$mcI$sp(), kv._2());
    }

    @Override
    public <S> IntMap<S> updated(int key, S value) {
        if (this instanceof Bin) {
            Bin bin = (Bin)this;
            int prefix = bin.prefix();
            int mask = bin.mask();
            IntMap left = bin.left();
            IntMap right = bin.right();
            if (!BitOperations.Int.hasMatch$(IntMapUtils$.MODULE$, key, prefix, mask)) {
                return IntMapUtils$.MODULE$.join(key, new Tip<S>(key, value), prefix, this);
            }
            if (BitOperations.Int.zero$(IntMapUtils$.MODULE$, key, mask)) {
                return new Bin<S>(prefix, mask, left.updated(key, value), right);
            }
            return new Bin(prefix, mask, left, right.updated(key, value));
        }
        if (this instanceof Tip) {
            int key2 = ((Tip)this).key();
            if (key == key2) {
                return new Tip<S>(key, value);
            }
            return IntMapUtils$.MODULE$.join(key, new Tip<S>(key, value), key2, this);
        }
        if (((Object)IntMap$Nil$.MODULE$).equals(this)) {
            return new Tip<S>(key, value);
        }
        throw new MatchError(this);
    }

    @Override
    public <V2> IntMap<V2> map(Function1<Tuple2<Object, T>, Tuple2<Object, V2>> f) {
        return this.intMapFrom(new View.Map<Tuple2<Object, T>, Tuple2<Object, V2>>(this, f));
    }

    @Override
    public <V2> IntMap<V2> flatMap(Function1<Tuple2<Object, T>, IterableOnce<Tuple2<Object, V2>>> f) {
        return this.intMapFrom(new View.FlatMap<Tuple2<Object, T>, Tuple2<Object, V2>>(this, f));
    }

    @Override
    public <V1> IntMap<V1> concat(IterableOnce<Tuple2<Object, V1>> that) {
        return (IntMap)StrictOptimizedMapOps.concat$(this, that);
    }

    @Override
    public <V1> IntMap<V1> $plus$plus(IterableOnce<Tuple2<Object, V1>> that) {
        return this.concat((IterableOnce)that);
    }

    @Override
    public <V2> IntMap<V2> collect(PartialFunction<Tuple2<Object, T>, Tuple2<Object, V2>> pf) {
        ImmutableBuilder strictOptimizedCollect_b = new /* invalid duplicate definition of identical inner class */;
        Object strictOptimizedCollect_marker = Statics.pfMarker;
        Iterator strictOptimizedCollect_it = this.iterator();
        while (strictOptimizedCollect_it.hasNext()) {
            Object strictOptimizedCollect_elem = strictOptimizedCollect_it.next();
            Object strictOptimizedCollect_v = pf.applyOrElse(strictOptimizedCollect_elem, arg_0 -> StrictOptimizedIterableOps.$anonfun$strictOptimizedCollect$1(strictOptimizedCollect_marker, arg_0));
            if (strictOptimizedCollect_marker == strictOptimizedCollect_v) continue;
            strictOptimizedCollect_b.addOne((Tuple2)strictOptimizedCollect_v);
        }
        return (IntMap)strictOptimizedCollect_b.result();
    }

    public <S> IntMap<S> updateWith(int key, S value, Function2<T, S, S> f) {
        if (this instanceof Bin) {
            Bin bin = (Bin)this;
            int prefix = bin.prefix();
            int mask = bin.mask();
            IntMap<T> left = bin.left();
            IntMap<T> right = bin.right();
            if (!BitOperations.Int.hasMatch$(IntMapUtils$.MODULE$, key, prefix, mask)) {
                return IntMapUtils$.MODULE$.join(key, new Tip<S>(key, value), prefix, this);
            }
            if (BitOperations.Int.zero$(IntMapUtils$.MODULE$, key, mask)) {
                return new Bin<S>(prefix, mask, left.updateWith(key, value, f), right);
            }
            return new Bin(prefix, mask, left, right.updateWith(key, value, f));
        }
        if (this instanceof Tip) {
            Tip tip = (Tip)this;
            int key2 = tip.key();
            Object value2 = tip.value();
            if (key == key2) {
                return new Tip<S>(key, f.apply(value2, value));
            }
            return IntMapUtils$.MODULE$.join(key, new Tip<S>(key, value), key2, this);
        }
        if (((Object)IntMap$Nil$.MODULE$).equals(this)) {
            return new Tip<S>(key, value);
        }
        throw new MatchError(this);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public IntMap<T> removed(int key) {
        if (this instanceof Bin) {
            void $minus_key;
            Bin bin = (Bin)this;
            int prefix = bin.prefix();
            int mask = bin.mask();
            IntMap left = bin.left();
            IntMap right = bin.right();
            if (!BitOperations.Int.hasMatch$(IntMapUtils$.MODULE$, key, prefix, mask)) {
                return this;
            }
            if (BitOperations.Int.zero$(IntMapUtils$.MODULE$, key, mask)) {
                void $minus_key2;
                Integer n = key;
                if (left == null) {
                    throw null;
                }
                n = null;
                return IntMapUtils$.MODULE$.bin(prefix, mask, (IntMap)left.removed($minus_key2), right);
            }
            Integer n = key;
            if (right == null) {
                throw null;
            }
            n = null;
            return IntMapUtils$.MODULE$.bin(prefix, mask, left, (IntMap)right.removed($minus_key));
        }
        if (this instanceof Tip) {
            int key2 = ((Tip)this).key();
            if (key == key2) {
                return IntMap$Nil$.MODULE$;
            }
            return this;
        }
        if (((Object)IntMap$Nil$.MODULE$).equals(this)) {
            return IntMap$Nil$.MODULE$;
        }
        throw new MatchError(this);
    }

    public <S> IntMap<S> modifyOrRemove(Function2<Object, T, Option<S>> f) {
        if (this instanceof Bin) {
            Bin bin = (Bin)this;
            int prefix = bin.prefix();
            int mask = bin.mask();
            IntMap<T> left = bin.left();
            IntMap<T> right = bin.right();
            IntMap<S> newleft = left.modifyOrRemove(f);
            IntMap<S> newright = right.modifyOrRemove(f);
            if (left == newleft && right == newright) {
                return this;
            }
            return IntMapUtils$.MODULE$.bin(prefix, mask, newleft, newright);
        }
        if (this instanceof Tip) {
            Tip tip = (Tip)this;
            int key = tip.key();
            Object value = tip.value();
            Option<S> option = f.apply(key, value);
            if (None$.MODULE$.equals(option)) {
                return IntMap$Nil$.MODULE$;
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
        if (((Object)IntMap$Nil$.MODULE$).equals(this)) {
            return IntMap$Nil$.MODULE$;
        }
        throw new MatchError(this);
    }

    public <S> IntMap<S> unionWith(IntMap<S> that, Function3<Object, S, S, S> f) {
        Tuple2<IntMap, IntMap<S>> tuple2 = new Tuple2<IntMap, IntMap<S>>(this, that);
        if (this instanceof Bin) {
            Bin bin = (Bin)this;
            int p1 = bin.prefix();
            int m1 = bin.mask();
            IntMap l1 = bin.left();
            IntMap r1 = bin.right();
            if (that instanceof Bin) {
                Bin bin2 = (Bin)that;
                int p2 = bin2.prefix();
                int m22 = bin2.mask();
                IntMap l2 = bin2.left();
                IntMap r2 = bin2.right();
                if (BitOperations.Int.unsignedCompare$(IntMapUtils$.MODULE$, m22, m1)) {
                    if (!BitOperations.Int.hasMatch$(IntMapUtils$.MODULE$, p2, p1, m1)) {
                        return IntMapUtils$.MODULE$.join(p1, this, p2, bin2);
                    }
                    if (BitOperations.Int.zero$(IntMapUtils$.MODULE$, p2, m1)) {
                        return new Bin<S>(p1, m1, l1.unionWith(bin2, f), r1);
                    }
                    return new Bin(p1, m1, l1, r1.unionWith(bin2, f));
                }
                if (BitOperations.Int.unsignedCompare$(IntMapUtils$.MODULE$, m1, m22)) {
                    if (!BitOperations.Int.hasMatch$(IntMapUtils$.MODULE$, p1, p2, m22)) {
                        return IntMapUtils$.MODULE$.join(p1, this, p2, bin2);
                    }
                    if (BitOperations.Int.zero$(IntMapUtils$.MODULE$, p1, m22)) {
                        return new Bin(p2, m22, this.unionWith(l2, f), r2);
                    }
                    return new Bin(p2, m22, l2, this.unionWith(r2, f));
                }
                if (p1 == p2) {
                    return new Bin(p1, m1, l1.unionWith(l2, f), r1.unionWith(r2, f));
                }
                return IntMapUtils$.MODULE$.join(p1, this, p2, bin2);
            }
        }
        if (this instanceof Tip) {
            Tip tip = (Tip)this;
            int key = tip.key();
            Object value = tip.value();
            return that.updateWith(key, value, (Function2<Object, Object, Object> & Serializable)(x, y) -> f.apply(BoxesRunTime.boxToInteger(key), y, x));
        }
        if (that instanceof Tip) {
            Tip tip = (Tip)that;
            int key = tip.key();
            Object value = tip.value();
            return this.updateWith(key, value, (Function2<Object, Object, Object> & Serializable)(x, y) -> f.apply(BoxesRunTime.boxToInteger(key), x, y));
        }
        if (((Object)IntMap$Nil$.MODULE$).equals(this)) {
            return that;
        }
        if (((Object)IntMap$Nil$.MODULE$).equals(that)) {
            return this;
        }
        throw new MatchError(tuple2);
    }

    public <S, R> IntMap<R> intersectionWith(IntMap<S> that, Function3<Object, T, S, R> f) {
        if (this instanceof Bin) {
            Bin bin = (Bin)this;
            int p1 = bin.prefix();
            int m1 = bin.mask();
            IntMap<T> l1 = bin.left();
            IntMap<T> r1 = bin.right();
            if (that instanceof Bin) {
                Bin bin2 = (Bin)that;
                int p2 = bin2.prefix();
                int m22 = bin2.mask();
                IntMap l2 = bin2.left();
                IntMap r2 = bin2.right();
                if (BitOperations.Int.unsignedCompare$(IntMapUtils$.MODULE$, m22, m1)) {
                    if (!BitOperations.Int.hasMatch$(IntMapUtils$.MODULE$, p2, p1, m1)) {
                        return IntMap$Nil$.MODULE$;
                    }
                    if (BitOperations.Int.zero$(IntMapUtils$.MODULE$, p2, m1)) {
                        return l1.intersectionWith(bin2, f);
                    }
                    return r1.intersectionWith(bin2, f);
                }
                if (m1 == m22) {
                    return IntMapUtils$.MODULE$.bin(p1, m1, l1.intersectionWith(l2, f), r1.intersectionWith(r2, f));
                }
                if (!BitOperations.Int.hasMatch$(IntMapUtils$.MODULE$, p1, p2, m22)) {
                    return IntMap$Nil$.MODULE$;
                }
                if (BitOperations.Int.zero$(IntMapUtils$.MODULE$, p1, m22)) {
                    return this.intersectionWith(l2, f);
                }
                return this.intersectionWith(r2, f);
            }
        }
        if (this instanceof Tip) {
            Tip tip = (Tip)this;
            int key = tip.key();
            Object value = tip.value();
            Option<S> option = that.get(key);
            if (None$.MODULE$.equals(option)) {
                return IntMap$Nil$.MODULE$;
            }
            if (option instanceof Some) {
                Object value2 = ((Some)option).value();
                return new Tip<R>(key, f.apply(key, value, value2));
            }
            throw new MatchError(option);
        }
        if (that instanceof Tip) {
            Tip tip = (Tip)that;
            int key = tip.key();
            Object value = tip.value();
            Option<T> option = this.get(key);
            if (None$.MODULE$.equals(option)) {
                return IntMap$Nil$.MODULE$;
            }
            if (option instanceof Some) {
                Object value2 = ((Some)option).value();
                return new Tip<R>(key, f.apply(key, value2, value));
            }
            throw new MatchError(option);
        }
        return IntMap$Nil$.MODULE$;
    }

    public <R> IntMap<T> intersection(IntMap<R> that) {
        return this.intersectionWith(that, (Function3<Object, Object, Object, Object> & Serializable)(key, value, value2) -> value);
    }

    @Override
    public <S> IntMap<S> $plus$plus(IntMap<S> that) {
        return this.unionWith(that, (Function3<Object, Object, Object, Object> & Serializable)(key, x, y) -> y);
    }

    public final int firstKey() {
        while (this_ instanceof Bin) {
            IntMap this_ = ((Bin)this_).left();
        }
        if (this_ instanceof Tip) {
            return ((Tip)this_).key();
        }
        if (((Object)IntMap$Nil$.MODULE$).equals(this_)) {
            throw new IllegalStateException("Empty set");
        }
        throw new MatchError(this_);
    }

    public final int lastKey() {
        while (this_ instanceof Bin) {
            IntMap this_ = ((Bin)this_).right();
        }
        if (this_ instanceof Tip) {
            return ((Tip)this_).key();
        }
        if (((Object)IntMap$Nil$.MODULE$).equals(this_)) {
            throw new IllegalStateException("Empty set");
        }
        throw new MatchError(this_);
    }

    public Object writeReplace() {
        return new DefaultSerializationProxy<Tuple2<Object, Object>>(IntMap$ToFactory$.MODULE$, this);
    }

    public static class Bin<T>
    extends IntMap<T>
    implements Product {
        private final int prefix;
        private final int mask;
        private final IntMap<T> left;
        private final IntMap<T> right;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public int prefix() {
            return this.prefix;
        }

        public int mask() {
            return this.mask;
        }

        public IntMap<T> left() {
            return this.left;
        }

        public IntMap<T> right() {
            return this.right;
        }

        public <S> IntMap<S> bin(IntMap<S> left, IntMap<S> right) {
            if (this.left() == left && this.right() == right) {
                return this;
            }
            return new Bin<S>(this.prefix(), this.mask(), left, right);
        }

        public <T> Bin<T> copy(int prefix, int mask, IntMap<T> left, IntMap<T> right) {
            return new Bin<T>(prefix, mask, left, right);
        }

        public <T> int copy$default$1() {
            return this.prefix();
        }

        public <T> int copy$default$2() {
            return this.mask();
        }

        public <T> IntMap<T> copy$default$3() {
            return this.left();
        }

        public <T> IntMap<T> copy$default$4() {
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

        public Bin(int prefix, int mask, IntMap<T> left, IntMap<T> right) {
            this.prefix = prefix;
            this.mask = mask;
            this.left = left;
            this.right = right;
        }
    }

    public static class Tip<T>
    extends IntMap<T>
    implements Product {
        private final int key;
        private final T value;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public int key() {
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

        public <T> Tip<T> copy(int key, T value) {
            return new Tip<T>(key, value);
        }

        public <T> int copy$default$1() {
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

        public Tip(int key, T value) {
            this.key = key;
            this.value = value;
        }
    }
}

