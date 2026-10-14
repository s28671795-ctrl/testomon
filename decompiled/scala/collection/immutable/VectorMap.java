/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.NoSuchElementException;
import scala.$less$colon$less;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterator;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableFactoryDefaults;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.LazyZip2;
import scala.collection.MapFactory;
import scala.collection.MapFactoryDefaults;
import scala.collection.MapOps;
import scala.collection.SeqMap;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.View;
import scala.collection.WithFilter;
import scala.collection.immutable.AbstractMap;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.collection.immutable.Stream;
import scala.collection.immutable.StrictOptimizedMapOps;
import scala.collection.immutable.Vector;
import scala.collection.immutable.VectorMap$;
import scala.collection.immutable.VectorMapBuilder;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.StringBuilder;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Either;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@ScalaSignature(bytes="\u0006\u0005\r\rc\u0001\u0002 @\u0005\u0019C\u0011B\u001b\u0001\u0003\u0006\u0004%\taP6\t\u0011=\u0004!\u0011!Q\u0001\n1D\u0011\u0002\u001d\u0001\u0003\u0006\u0004%\taP9\t\u0011m\u0004!\u0011!Q\u0001\nID\u0001\u0002 \u0001\u0003\u0002\u0003\u0006I\u0001\u001f\u0005\u0006{\u0002!IA \u0005\t\u0003\u000b\u0001\u0001\u0015\"\u0015\u0002\b!9Q\u0010\u0001C\u0001\u007f\u0005}\u0001\"CA\u0014\u0001\t\u0007I\u0011IA\u0015\u0011\u001d\tY\u0003\u0001Q\u0001\naDq!!\f\u0001\t\u0003\nI\u0003C\u0004\u00020\u0001!\t%!\r\t\u000f\u0005e\u0002\u0001\"\u0001\u0002<!9\u0011\u0011\u000b\u0001\u0005B\u0005M\u0003bBA4\u0001\u0011\u0005\u0013\u0011\u000e\u0005\b\u0003k\u0002A\u0011AA<\u0011\u001d\t\t\t\u0001C\u0005\u0003\u0007Cq!!'\u0001\t\u0003\tY\nC\u0004\u0002&\u0002!\t%a*\t\u000f\u0005-\u0007\u0001\"\u0011\u0002N\"9\u0011Q\u001d\u0001\u0005B\u0005\u001d\bbBA\u0000\u0001\u0011\u0005!\u0011\u0001\u0005\b\u0005\u000b\u0001A\u0011\tB\u0004\u0011\u001d\u0011y\u0001\u0001C!\u0005#AqA!\u0006\u0001\t\u0003\u00129\u0002C\u0004\u0003\u001a\u0001!\tEa\u0006\t\u000f\tm\u0001\u0001\"\u0011\u0003\u001e!9!\u0011\u0005\u0001\u0005B\t\r\u0002b\u0002B\u0013\u0001\u0011\u0005#1\u0005\u0005\b\u0005O\u0001A\u0011\tB\u0015\u0011\u001d\u0011Y\u0003\u0001C!\u0005[9qA!\r@\u0011\u0003\u0011\u0019D\u0002\u0004?\u007f!\u0005!Q\u0007\u0005\u0007{\u0006\"\tA!\u0010\u0007\u000f\t}\u0012EQ\u0011\u0003B!Q!1L\u0012\u0003\u0016\u0004%\t!!\u000b\t\u0013\tu3E!E!\u0002\u0013A\bBB?$\t\u0003\u0011y\u0006C\u0005\u0003h\r\n\t\u0011\"\u0001\u0003j!I!QN\u0012\u0012\u0002\u0013\u0005!q\u000e\u0005\n\u0005\u0003\u001b\u0013\u0011!C!\u0005\u0007C\u0011Ba%$\u0003\u0003%\t!!\u000b\t\u0013\tU5%!A\u0005\u0002\t]\u0005\"\u0003BOG\u0005\u0005I\u0011\tBP\u0011%\u0011\u0019kIA\u0001\n\u0003\u0011)\u000bC\u0005\u0003*\u000e\n\t\u0011\"\u0011\u0003,\"I!qV\u0012\u0002\u0002\u0013\u0005#\u0011\u0017\u0005\n\u0005g\u001b\u0013\u0011!C!\u0005kC\u0011Ba.$\u0003\u0003%\tE!/\b\u0015\tu\u0016%!A\t\u0002\u0005\u0012yL\u0002\u0006\u0003@\u0005\n\t\u0011#\u0001\"\u0005\u0003Da!`\u001a\u0005\u0002\te\u0007\"\u0003BZg\u0005\u0005IQ\tB[\u0011%\u0011YnMA\u0001\n\u0003\u0013i\u000eC\u0005\u0003bN\n\t\u0011\"!\u0003d\"I!1^\u001a\u0002\u0002\u0013%!Q\u001e\u0005\t\u0005k\f\u0003\u0015!\u0004\u0003x\"9!\u0011`\u0011\u0005\u0002\tm\bbBB\u0005C\u0011\u000511\u0002\u0005\b\u0007K\tC\u0011AB\u0014\u0011%\u0011Y/IA\u0001\n\u0013\u0011iOA\u0005WK\u000e$xN]'ba*\u0011\u0001)Q\u0001\nS6lW\u000f^1cY\u0016T!AQ\"\u0002\u0015\r|G\u000e\\3di&|gNC\u0001E\u0003\u0015\u00198-\u00197b\u0007\u0001)2a\u0012(Z'\u0015\u0001\u0001j\u00170d!\u0011I%\n\u0014-\u000e\u0003}J!aS \u0003\u0017\u0005\u00137\u000f\u001e:bGRl\u0015\r\u001d\t\u0003\u001b:c\u0001\u0001B\u0003P\u0001\t\u0007\u0001KA\u0001L#\t\tV\u000b\u0005\u0002S'6\t1)\u0003\u0002U\u0007\n9aj\u001c;iS:<\u0007C\u0001*W\u0013\t96IA\u0002B]f\u0004\"!T-\u0005\ri\u0003AQ1\u0001Q\u0005\u00051\u0006\u0003B%]\u0019bK!!X \u0003\rM+\u0017/T1q!\u0019Iu\f\u0014-bE&\u0011\u0001m\u0010\u0002\u0016'R\u0014\u0018n\u0019;PaRLW.\u001b>fI6\u000b\u0007o\u00149t!\tI\u0005\u0001\u0005\u0003J\u00011C\u0006C\u00023f\u0019b\u000bw-D\u0001B\u0013\t1\u0017I\u0001\nNCB4\u0015m\u0019;pef$UMZ1vYR\u001c\bCA%i\u0013\tIwH\u0001\u0005Ji\u0016\u0014\u0018M\u00197f\u0003\u00191\u0017.\u001a7egV\tA\u000eE\u0002J[VK!A\\ \u0003\rY+7\r^8s\u0003\u001d1\u0017.\u001a7eg\u0002\n!\"\u001e8eKJd\u00170\u001b8h+\u0005\u0011\b\u0003B%t\u0019VL!\u0001^ \u0003\u00075\u000b\u0007\u000f\u0005\u0003SmbD\u0016BA<D\u0005\u0019!V\u000f\u001d7feA\u0011!+_\u0005\u0003u\u000e\u00131!\u00138u\u0003-)h\u000eZ3sYfLgn\u001a\u0011\u0002\u000f\u0011\u0014x\u000e\u001d9fI\u00061A(\u001b8jiz\"bAY@\u0002\u0002\u0005\r\u0001\"\u00026\u0007\u0001\u0004a\u0007\"\u00029\u0007\u0001\u0004\u0011\b\"\u0002?\u0007\u0001\u0004A\u0018!C2mCN\u001ch*Y7f+\t\tI\u0001\u0005\u0003\u0002\f\u0005ea\u0002BA\u0007\u0003+\u00012!a\u0004D\u001b\t\t\tBC\u0002\u0002\u0014\u0015\u000ba\u0001\u0010:p_Rt\u0014bAA\f\u0007\u00061\u0001K]3eK\u001aLA!a\u0007\u0002\u001e\t11\u000b\u001e:j]\u001eT1!a\u0006D)\u0015\u0011\u0017\u0011EA\u0013\u0011\u0019Q\u0007\u00021\u0001\u0002$A\u0019\u0011*\u001c'\t\u000bAD\u0001\u0019\u0001:\u0002\tML'0Z\u000b\u0002q\u0006)1/\u001b>fA\u0005I1N\\8x]NK'0Z\u0001\bSN,U\u000e\u001d;z+\t\t\u0019\u0004E\u0002S\u0003kI1!a\u000eD\u0005\u001d\u0011un\u001c7fC:\fq!\u001e9eCR,G-\u0006\u0003\u0002>\u0005\rCCBA \u0003\u0013\ni\u0005E\u0003J\u00011\u000b\t\u0005E\u0002N\u0003\u0007\"q!!\u0012\u000e\u0005\u0004\t9E\u0001\u0002WcE\u0011\u0001,\u0016\u0005\u0007\u0003\u0017j\u0001\u0019\u0001'\u0002\u0007-,\u0017\u0010C\u0004\u0002P5\u0001\r!!\u0011\u0002\u000bY\fG.^3\u0002\u0017]LG\u000f\u001b#fM\u0006,H\u000e^\u000b\u0005\u0003+\nY\u0006\u0006\u0003\u0002X\u0005u\u0003#B%t\u0019\u0006e\u0003cA'\u0002\\\u00119\u0011Q\t\bC\u0002\u0005\u001d\u0003bBA0\u001d\u0001\u0007\u0011\u0011M\u0001\u0002IB1!+a\u0019M\u00033J1!!\u001aD\u0005%1UO\\2uS>t\u0017'\u0001\txSRDG)\u001a4bk2$h+\u00197vKV!\u00111NA9)\u0011\ti'a\u001d\u0011\u000b%\u001bH*a\u001c\u0011\u00075\u000b\t\bB\u0004\u0002F=\u0011\r!a\u0012\t\u000f\u0005}s\u00021\u0001\u0002p\u0005\u0019q-\u001a;\u0015\t\u0005e\u0014q\u0010\t\u0005%\u0006m\u0004,C\u0002\u0002~\r\u0013aa\u00149uS>t\u0007BBA&!\u0001\u0007A*\u0001\boKb$h+\u00197jI\u001aKW\r\u001c3\u0015\t\u0005\u0015\u0015q\u0011\t\u0005%ZDH\n\u0003\u0004\u0002\nF\u0001\r\u0001_\u0001\u0005g2|G\u000fK\u0002\u0012\u0003\u001b\u0003B!a$\u0002\u00166\u0011\u0011\u0011\u0013\u0006\u0004\u0003'\u001b\u0015AC1o]>$\u0018\r^5p]&!\u0011qSAI\u0005\u001d!\u0018-\u001b7sK\u000e\f\u0001\"\u001b;fe\u0006$xN]\u000b\u0003\u0003;\u0003R\u0001ZAP\u0003GK1!!)B\u0005!IE/\u001a:bi>\u0014\b\u0003\u0002*w\u0019b\u000bqa\u001d;faB,'/\u0006\u0003\u0002*\u00065F\u0003BAV\u0003\u0003\u00042!TAW\t\u001d\tyk\u0005b\u0001\u0003c\u0013\u0011aU\t\u0004#\u0006M\u0006\u0007BA[\u0003{\u0003R\u0001ZA\\\u0003wK1!!/B\u0005\u001d\u0019F/\u001a9qKJ\u00042!TA_\t-\ty,!,\u0002\u0002\u0003\u0005)\u0011\u0001)\u0003\u0007}#\u0013\u0007C\u0004\u0002DN\u0001\u001d!!2\u0002\u000bMD\u0017\r]3\u0011\u000f\u0011\f9-a)\u0002,&\u0019\u0011\u0011Z!\u0003\u0019M#X\r\u001d9feNC\u0017\r]3\u0002\u0015-,\u0017p\u0015;faB,'/\u0006\u0003\u0002P\u0006MG\u0003BAi\u0003C\u00042!TAj\t\u001d\ty\u000b\u0006b\u0001\u0003+\f2!UAla\u0011\tI.!8\u0011\u000b\u0011\f9,a7\u0011\u00075\u000bi\u000eB\u0006\u0002`\u0006M\u0017\u0011!A\u0001\u0006\u0003\u0001&aA0%e!9\u00111\u0019\u000bA\u0004\u0005\r\bC\u00023\u0002H2\u000b\t.\u0001\u0007wC2,Xm\u0015;faB,'/\u0006\u0003\u0002j\u00065H\u0003BAv\u0003w\u00042!TAw\t\u001d\ty+\u0006b\u0001\u0003_\f2!UAya\u0011\t\u00190a>\u0011\u000b\u0011\f9,!>\u0011\u00075\u000b9\u0010B\u0006\u0002z\u00065\u0018\u0011!A\u0001\u0006\u0003\u0001&aA0%g!9\u00111Y\u000bA\u0004\u0005u\bC\u00023\u0002Hb\u000bY/A\u0004sK6|g/\u001a3\u0015\u0007\t\u0014\u0019\u0001\u0003\u0004\u0002LY\u0001\r\u0001T\u0001\u000b[\u0006\u0004h)Y2u_JLXC\u0001B\u0005!\u0011!'1B1\n\u0007\t5\u0011I\u0001\u0006NCB4\u0015m\u0019;pef\f\u0001bY8oi\u0006Lgn\u001d\u000b\u0005\u0003g\u0011\u0019\u0002\u0003\u0004\u0002La\u0001\r\u0001T\u0001\u0005Q\u0016\fG-\u0006\u0002\u0002$\u0006!A.Y:u\u0003)a\u0017m\u001d;PaRLwN\\\u000b\u0003\u0005?\u0001RAUA>\u0003G\u000bA\u0001^1jYV\t!-\u0001\u0003j]&$\u0018\u0001B6fsN,\"!a\t\u0002\rY\fG.^3t+\t\u0011y\u0003E\u0002JQb\u000b\u0011BV3di>\u0014X*\u00199\u0011\u0005%\u000b3#B\u0011\u00038\t%\u0001c\u0001*\u0003:%\u0019!1H\"\u0003\r\u0005s\u0017PU3g)\t\u0011\u0019DA\u0005U_6\u00147\u000f^8oKN91Ea\u000e\u0003D\t%\u0003c\u0001*\u0003F%\u0019!qI\"\u0003\u000fA\u0013x\u000eZ;diB!!1\nB+\u001d\u0011\u0011iE!\u0015\u000f\t\u0005=!qJ\u0005\u0002\t&\u0019!1K\"\u0002\u000fA\f7m[1hK&!!q\u000bB-\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\r\u0011\u0019fQ\u0001\tI&\u001cH/\u00198dK\u0006IA-[:uC:\u001cW\r\t\u000b\u0005\u0005C\u0012)\u0007E\u0002\u0003d\rj\u0011!\t\u0005\u0007\u000572\u0003\u0019\u0001=\u0002\t\r|\u0007/\u001f\u000b\u0005\u0005C\u0012Y\u0007\u0003\u0005\u0003\\\u001d\u0002\n\u00111\u0001y\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\"A!\u001d+\u0007a\u0014\u0019h\u000b\u0002\u0003vA!!q\u000fB?\u001b\t\u0011IH\u0003\u0003\u0003|\u0005E\u0015!C;oG\",7m[3e\u0013\u0011\u0011yH!\u001f\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0005\u000b\u0003BAa\"\u0003\u00126\u0011!\u0011\u0012\u0006\u0005\u0005\u0017\u0013i)\u0001\u0003mC:<'B\u0001BH\u0003\u0011Q\u0017M^1\n\t\u0005m!\u0011R\u0001\raJ|G-^2u\u0003JLG/_\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\r)&\u0011\u0014\u0005\t\u00057[\u0013\u0011!a\u0001q\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"A!)\u0011\t\u0011\fy*V\u0001\tG\u0006tW)];bYR!\u00111\u0007BT\u0011!\u0011Y*LA\u0001\u0002\u0004)\u0016A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$BA!\"\u0003.\"A!1\u0014\u0018\u0002\u0002\u0003\u0007\u00010\u0001\u0005iCND7i\u001c3f)\u0005A\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\t\u0015\u0015AB3rk\u0006d7\u000f\u0006\u0003\u00024\tm\u0006\u0002\u0003BNc\u0005\u0005\t\u0019A+\u0002\u0013Q{WNY:u_:,\u0007c\u0001B2gM)1Ga1\u0003PB9!Q\u0019Bfq\n\u0005TB\u0001Bd\u0015\r\u0011ImQ\u0001\beVtG/[7f\u0013\u0011\u0011iMa2\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t\u0017\u0007\u0005\u0003\u0003R\n]WB\u0001Bj\u0015\u0011\u0011)N!$\u0002\u0005%|\u0017\u0002\u0002B,\u0005'$\"Aa0\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\t\t\u0005$q\u001c\u0005\u0007\u000572\u0004\u0019\u0001=\u0002\u000fUt\u0017\r\u001d9msR!!Q\u001dBt!\u0011\u0011\u00161\u0010=\t\u0013\t%x'!AA\u0002\t\u0005\u0014a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011!q\u001e\t\u0005\u0005\u000f\u0013\t0\u0003\u0003\u0003t\n%%AB(cU\u0016\u001cG/\u0001\u0005F[B$\u00180T1q!\u0011I\u0005!U)\u0002\u000b\u0015l\u0007\u000f^=\u0016\r\tu81AB\u0004+\t\u0011y\u0010\u0005\u0004J\u0001\r\u00051Q\u0001\t\u0004\u001b\u000e\rA!B(;\u0005\u0004\u0001\u0006cA'\u0004\b\u0011)!L\u000fb\u0001!\u0006!aM]8n+\u0019\u0019iaa\u0005\u0004\u0018Q!1qBB\r!\u0019I\u0005a!\u0005\u0004\u0016A\u0019Qja\u0005\u0005\u000b=[$\u0019\u0001)\u0011\u00075\u001b9\u0002B\u0003[w\t\u0007\u0001\u000bC\u0004\u0004\u001cm\u0002\ra!\b\u0002\u0005%$\b#\u00023\u0004 \r\r\u0012bAB\u0011\u0003\na\u0011\n^3sC\ndWm\u00148dKB1!K^B\t\u0007+\t!B\\3x\u0005VLG\u000eZ3s+\u0019\u0019Ica\u000f\u0004@U\u001111\u0006\t\t\u0007[\u0019\u0019da\u000e\u0004B5\u00111q\u0006\u0006\u0004\u0007c\t\u0015aB7vi\u0006\u0014G.Z\u0005\u0005\u0007k\u0019yCA\u0004Ck&dG-\u001a:\u0011\rI38\u0011HB\u001f!\ri51\b\u0003\u0006\u001fr\u0012\r\u0001\u0015\t\u0004\u001b\u000e}B!\u0002.=\u0005\u0004\u0001\u0006CB%\u0001\u0007s\u0019i\u0004")
public final class VectorMap<K, V>
extends AbstractMap<K, V>
implements scala.collection.immutable.SeqMap<K, V>,
StrictOptimizedMapOps<K, V, VectorMap, VectorMap<K, V>> {
    private final Vector<Object> fields;
    private final Map<K, Tuple2<Object, V>> underlying;
    private final int dropped;
    private final int size;

    public static <K, V> Builder<Tuple2<K, V>, VectorMap<K, V>> newBuilder() {
        return new VectorMapBuilder();
    }

    public static <K, V> VectorMap<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        return VectorMap$.MODULE$.from((IterableOnce)it);
    }

    @Override
    public scala.collection.immutable.MapOps concat(IterableOnce that) {
        return StrictOptimizedMapOps.concat$(this, that);
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
    public Tuple2<VectorMap<K, V>, VectorMap<K, V>> partition(Function1<Tuple2<K, V>, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<VectorMap<K, V>, VectorMap<K, V>> span(Function1<Tuple2<K, V>, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
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
    public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> partitionMap(Function1<Tuple2<K, V>, Either<A1, A2>> f) {
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
    public String stringPrefix() {
        return SeqMap.stringPrefix$(this);
    }

    public Vector<Object> fields() {
        return this.fields;
    }

    public Map<K, Tuple2<Object, V>> underlying() {
        return this.underlying;
    }

    @Override
    public String className() {
        return "VectorMap";
    }

    @Override
    public int size() {
        return this.size;
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
    public <V1> VectorMap<K, V1> updated(K key, V1 value) {
        Tuple2 tuple2;
        Option option = this.underlying().get(key);
        if (option instanceof Some && (tuple2 = (Tuple2)((Some)option).value()) != null) {
            int slot = tuple2._1$mcI$sp();
            return new VectorMap<K, V>(this.fields(), (Map)this.underlying().updated(key, new Tuple2<Integer, V1>(slot, value)), this.dropped);
        }
        if (None$.MODULE$.equals(option)) {
            Vector<Object> vector = this.fields();
            if (vector == null) {
                throw null;
            }
            return new VectorMap<K, V>((Vector)vector.appended(key), (Map)this.underlying().updated(key, new Tuple2<Integer, V1>(this.fields().length() + this.dropped, value)), this.dropped);
        }
        throw new MatchError(option);
    }

    @Override
    public <V1> Map<K, V1> withDefault(Function1<K, V1> d) {
        return new Map.WithDefault<K, V1>(this, d);
    }

    @Override
    public <V1> Map<K, V1> withDefaultValue(V1 d) {
        return new Map.WithDefault(this, (Function1<Object, Object> & Serializable)x$1 -> d);
    }

    @Override
    public Option<V> get(K key) {
        Option option = this.underlying().get(key);
        if (option instanceof Some) {
            Tuple2 v = (Tuple2)((Some)option).value();
            return new Some(v._2());
        }
        if (None$.MODULE$.equals(option)) {
            return None$.MODULE$;
        }
        throw new MatchError(option);
    }

    public Tuple2<Object, K> scala$collection$immutable$VectorMap$$nextValidField(int slot) {
        Object r;
        while (true) {
            Vector<Object> vector = this.fields();
            if (vector == null) {
                throw null;
            }
            if (slot >= vector.length()) {
                return new Tuple2<Integer, Object>(-1, null);
            }
            r = this.fields().apply(slot);
            if (!(r instanceof Tombstone)) break;
            int distance = ((Tombstone)r).distance();
            slot += distance;
        }
        return new Tuple2(slot, r);
    }

    @Override
    public Iterator<Tuple2<K, V>> iterator() {
        return new AbstractIterator<Tuple2<K, V>>(this){
            private final int fieldsLength;
            private int slot;
            private K key;
            private final /* synthetic */ VectorMap $outer;

            private void advance() {
                int n;
                int nextSlot = this.slot + 1;
                if (nextSlot >= this.fieldsLength) {
                    this.slot = this.fieldsLength;
                    this.key = null;
                    return;
                }
                Tuple2<Object, K> tuple2 = this.$outer.scala$collection$immutable$VectorMap$$nextValidField(nextSlot);
                if (tuple2 != null && -1 == (n = tuple2._1$mcI$sp())) {
                    this.slot = this.fieldsLength;
                    this.key = null;
                    return;
                }
                if (tuple2 != null) {
                    int s2 = tuple2._1$mcI$sp();
                    K k = tuple2._2();
                    this.slot = s2;
                    this.key = k;
                    return;
                }
                throw new MatchError((Object)null);
            }

            public boolean hasNext() {
                return this.slot < this.fieldsLength;
            }

            public Tuple2<K, V> next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException("next called on depleted iterator");
                }
                Tuple2<K, T2> result = new Tuple2<K, T2>(this.key, ((Tuple2)this.$outer.underlying().apply(this.key))._2());
                this.advance();
                return result;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.fieldsLength = $outer.fields().length();
                this.slot = -1;
                this.key = null;
                this.advance();
            }
        };
    }

    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<Tuple2<K, V>, S> shape) {
        return (S)IterableOnce.stepper$(this, shape);
    }

    @Override
    public <S extends Stepper<?>> S keyStepper(StepperShape<K, S> shape) {
        return (S)MapOps.keyStepper$(this, shape);
    }

    @Override
    public <S extends Stepper<?>> S valueStepper(StepperShape<V, S> shape) {
        return (S)MapOps.valueStepper$(this, shape);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public VectorMap<K, V> removed(K key) {
        block15: {
            block17: {
                block16: {
                    if (this.isEmpty()) {
                        return (VectorMap)MapFactoryDefaults.empty$(this);
                    }
                    fs = this.fields();
                    if (fs == null) {
                        throw null;
                    }
                    sz = fs.length();
                    var4_4 = false;
                    var5_5 = null;
                    var6_6 = this.underlying().get(key);
                    if (var6_6 instanceof Some) {
                        var4_4 = true;
                        var5_5 = (Some)var6_6;
                        if (this.size() == 1) {
                            return (VectorMap)MapFactoryDefaults.empty$(this);
                        }
                    }
                    if (!var4_4 || (var7_7 = (Tuple2)var5_5.value()) == null) break block15;
                    s = var7_7._1$mcI$sp() - this.dropped;
                    if (s < sz - 1) {
                        var10_9 = fs.apply(s + 1);
                        if (var10_9 instanceof Tombstone) {
                            d = ((Tombstone)var10_9).distance();
                            v0 = s + d + 1;
                        } else {
                            v0 = s + 1;
                        }
                    } else {
                        v0 = s + 1;
                    }
                    next = v0;
                    fs = fs.updated(s, new Tombstone(next - s));
                    if (s <= 0) break block16;
                    var13_12 = false;
                    var14_13 = null;
                    var15_14 = fs.apply(s - 1);
                    if (!(var15_14 instanceof Tombstone)) ** GOTO lbl-1000
                    var13_12 = true;
                    var14_13 = (Tombstone)var15_14;
                    d = var14_13.distance();
                    if (d < 0) {
                        v1 = s + d >= 0 ? s + d : 0;
                    } else if (var13_12 && var14_13.distance() == 1) {
                        v1 = s - 1;
                    } else {
                        if (var13_12) {
                            d = var14_13.distance();
                            throw new IllegalStateException(new java.lang.StringBuilder(35).append("tombstone indicate wrong position: ").append(d).toString());
                        }
                        v1 = s;
                    }
                    break block17;
                }
                v1 = s;
            }
            first = v1;
            fs = fs.updated(first, new Tombstone(next - first));
            last = next - 1;
            if (last != first) {
                fs = fs.updated(last, new Tombstone(first - 1 - last));
            }
            v2 = this.underlying();
            if (v2 == null) {
                throw null;
            }
            return new VectorMap<K, V>(fs, (Map)v2.removed(key), this.dropped);
        }
        return this;
    }

    @Override
    public MapFactory<VectorMap> mapFactory() {
        return VectorMap$.MODULE$;
    }

    @Override
    public boolean contains(K key) {
        return this.underlying().contains(key);
    }

    @Override
    public Tuple2<K, V> head() {
        return new /* invalid duplicate definition of identical inner class */.next();
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public Tuple2<K, V> last() {
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("empty.last");
        }
        lastSlot = this.fields().length() - 1;
        var3_2 = false;
        var4_3 = null;
        var5_4 = this.fields().last();
        if (!(var5_4 instanceof Tombstone)) ** GOTO lbl-1000
        var3_2 = true;
        var4_3 = (Tombstone)var5_4;
        d = var4_3.distance();
        if (d < 0) {
            v0 = this.fields().apply(lastSlot + d);
        } else if (var3_2 && var4_3.distance() == 1) {
            v0 = this.fields().apply(lastSlot - 1);
        } else {
            if (var3_2) {
                d = var4_3.distance();
                throw new IllegalStateException(new java.lang.StringBuilder(35).append("tombstone indicate wrong position: ").append(d).toString());
            }
            v0 = var5_4;
        }
        last = v0;
        return new Tuple2<Object, T2>(last, ((Tuple2)this.underlying().apply(last))._2());
    }

    @Override
    public Option<Tuple2<K, V>> lastOption() {
        if (this.isEmpty()) {
            return None$.MODULE$;
        }
        return new Some<Object>(this.last());
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public VectorMap<K, V> tail() {
        void var3_3;
        void drop_n;
        void var2_2;
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("empty.tail");
        }
        Tuple2<Object, K> tuple2 = this.scala$collection$immutable$VectorMap$$nextValidField(0);
        if (tuple2 == null) {
            throw new MatchError((Object)null);
        }
        int slot = tuple2._1$mcI$sp();
        K key = tuple2._2();
        Vector<Object> vector = this.fields();
        void var5_4 = var2_2 + true;
        if (vector == null) {
            throw null;
        }
        Vector<Object> drop_this = vector;
        Object var4_5 = null;
        Map<void, Tuple2<Object, V>> map = this.underlying();
        if (map == null) {
            throw null;
        }
        return new VectorMap<K, V>((Vector)drop_this.slice((int)drop_n, drop_this.length()), (Map)map.removed(var3_3), this.dropped + var2_2 + 1);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public VectorMap<K, V> init() {
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("empty.init");
        }
        v0 = this.fields();
        if (v0 == null) {
            throw null;
        }
        lastSlot = v0.length() - 1;
        var2_2 = false;
        var3_3 = null;
        var4_4 = this.fields().last();
        if (!(var4_4 instanceof Tombstone)) ** GOTO lbl-1000
        var2_2 = true;
        var3_3 = (Tombstone)var4_4;
        d = var3_3.distance();
        if (d < 0) {
            v1 = lastSlot + d;
            v2 = this.fields().apply(lastSlot + d);
        } else if (var2_2 && var3_3.distance() == 1) {
            v1 = lastSlot - 1;
            v2 = this.fields().apply(lastSlot - 1);
        } else {
            if (var2_2) {
                d = var3_3.distance();
                throw new IllegalStateException(new java.lang.StringBuilder(35).append("tombstone indicate wrong position: ").append(d).toString());
            }
            v1 = lastSlot;
            v2 = var4_4;
        }
        var10_7 = v2;
        slot = v1;
        v3 = this.fields();
        v4 = this.fields();
        if (v4 == null) {
            throw null;
        }
        var9_9 = v4.length() - slot;
        if (v3 == null) {
            throw null;
        }
        dropRight_this = v3;
        var8_10 = null;
        v5 = this.underlying();
        if (v5 == null) {
            throw null;
        }
        return new VectorMap<K, V>((Vector)dropRight_this.slice(0, dropRight_this.length() - Math.max((int)dropRight_n, 0)), (Map)v5.removed(var10_7), this.dropped);
    }

    @Override
    public Vector<K> keys() {
        return new AbstractIterator<K>(this){
            private final Iterator<Tuple2<K, V>> iter;

            private Iterator<Tuple2<K, V>> iter() {
                return this.iter;
            }

            public boolean hasNext() {
                return this.iter().hasNext();
            }

            public K next() {
                return this.iter().next()._1();
            }
            {
                this.iter = $outer.iterator();
            }
        }.toVector();
    }

    @Override
    public scala.collection.immutable.Iterable<V> values() {
        return new scala.collection.immutable.Iterable<V>(this){
            private final /* synthetic */ VectorMap $outer;

            public IterableFactory<scala.collection.immutable.Iterable> iterableFactory() {
                return scala.collection.immutable.Iterable.iterableFactory$(this);
            }

            public final Iterable<V> toIterable() {
                return Iterable.toIterable$(this);
            }

            public final Iterable<V> coll() {
                return Iterable.coll$(this);
            }

            public Iterable<V> seq() {
                return Iterable.seq$(this);
            }

            public String className() {
                return Iterable.className$(this);
            }

            public final String collectionClassName() {
                return Iterable.collectionClassName$(this);
            }

            public String stringPrefix() {
                return Iterable.stringPrefix$(this);
            }

            public String toString() {
                return Iterable.toString$(this);
            }

            public <B> LazyZip2<V, B, Iterable> lazyZip(Iterable<B> that) {
                return Iterable.lazyZip$(this, that);
            }

            public IterableOps fromSpecific(IterableOnce coll) {
                return IterableFactoryDefaults.fromSpecific$(this, coll);
            }

            public Builder<V, scala.collection.immutable.Iterable<V>> newSpecificBuilder() {
                return IterableFactoryDefaults.newSpecificBuilder$(this);
            }

            public IterableOps empty() {
                return IterableFactoryDefaults.empty$(this);
            }

            public final Iterable<V> toTraversable() {
                return IterableOps.toTraversable$(this);
            }

            public boolean isTraversableAgain() {
                return IterableOps.isTraversableAgain$(this);
            }

            public final Object repr() {
                return IterableOps.repr$(this);
            }

            public IterableFactory<?> companion() {
                return IterableOps.companion$(this);
            }

            public V head() {
                return (V)IterableOps.head$(this);
            }

            public Option<V> headOption() {
                return IterableOps.headOption$(this);
            }

            public V last() {
                return (V)IterableOps.last$(this);
            }

            public Option<V> lastOption() {
                return IterableOps.lastOption$(this);
            }

            public View<V> view() {
                return IterableOps.view$(this);
            }

            public int sizeCompare(int otherSize) {
                return IterableOps.sizeCompare$((IterableOps)this, otherSize);
            }

            public final IterableOps sizeIs() {
                return IterableOps.sizeIs$(this);
            }

            public int sizeCompare(Iterable<?> that) {
                return IterableOps.sizeCompare$((IterableOps)this, that);
            }

            public View<V> view(int from, int until) {
                return IterableOps.view$(this, from, until);
            }

            public Object transpose(Function1 asIterable) {
                return IterableOps.transpose$(this, asIterable);
            }

            public Object filter(Function1 pred) {
                return IterableOps.filter$(this, pred);
            }

            public Object filterNot(Function1 pred) {
                return IterableOps.filterNot$(this, pred);
            }

            public WithFilter<V, ?> withFilter(Function1<V, Object> p) {
                return IterableOps.withFilter$(this, p);
            }

            public Tuple2<scala.collection.immutable.Iterable<V>, scala.collection.immutable.Iterable<V>> partition(Function1<V, Object> p) {
                return IterableOps.partition$(this, p);
            }

            public Tuple2<scala.collection.immutable.Iterable<V>, scala.collection.immutable.Iterable<V>> splitAt(int n) {
                return IterableOps.splitAt$(this, n);
            }

            public Object take(int n) {
                return IterableOps.take$(this, n);
            }

            public Object takeRight(int n) {
                return IterableOps.takeRight$(this, n);
            }

            public Object takeWhile(Function1 p) {
                return IterableOps.takeWhile$(this, p);
            }

            public Tuple2<scala.collection.immutable.Iterable<V>, scala.collection.immutable.Iterable<V>> span(Function1<V, Object> p) {
                return IterableOps.span$(this, p);
            }

            public Object drop(int n) {
                return IterableOps.drop$(this, n);
            }

            public Object dropRight(int n) {
                return IterableOps.dropRight$(this, n);
            }

            public Object dropWhile(Function1 p) {
                return IterableOps.dropWhile$(this, p);
            }

            public Iterator<scala.collection.immutable.Iterable<V>> grouped(int size) {
                return IterableOps.grouped$(this, size);
            }

            public Iterator<scala.collection.immutable.Iterable<V>> sliding(int size) {
                return IterableOps.sliding$(this, size);
            }

            public Iterator<scala.collection.immutable.Iterable<V>> sliding(int size, int step) {
                return IterableOps.sliding$(this, size, step);
            }

            public Object tail() {
                return IterableOps.tail$(this);
            }

            public Object init() {
                return IterableOps.init$(this);
            }

            public Object slice(int from, int until) {
                return IterableOps.slice$(this, from, until);
            }

            public <K$> Map<K$, scala.collection.immutable.Iterable<V>> groupBy(Function1<V, K$> f) {
                return IterableOps.groupBy$(this, f);
            }

            public <K$, B> Map<K$, scala.collection.immutable.Iterable<B>> groupMap(Function1<V, K$> key, Function1<V, B> f) {
                return IterableOps.groupMap$(this, key, f);
            }

            public <K$, B> Map<K$, B> groupMapReduce(Function1<V, K$> key, Function1<V, B> f, Function2<B, B, B> reduce) {
                return IterableOps.groupMapReduce$(this, key, f, reduce);
            }

            public Object scan(Object z, Function2 op) {
                return IterableOps.scan$(this, z, op);
            }

            public Object scanLeft(Object z, Function2 op) {
                return IterableOps.scanLeft$(this, z, op);
            }

            public Object scanRight(Object z, Function2 op) {
                return IterableOps.scanRight$(this, z, op);
            }

            public Object map(Function1 f) {
                return IterableOps.map$(this, f);
            }

            public Object flatMap(Function1 f) {
                return IterableOps.flatMap$(this, f);
            }

            public Object flatten(Function1 asIterable) {
                return IterableOps.flatten$(this, asIterable);
            }

            public Object collect(PartialFunction pf) {
                return IterableOps.collect$(this, pf);
            }

            public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> partitionMap(Function1<V, Either<A1, A2>> f) {
                return IterableOps.partitionMap$(this, f);
            }

            public Object concat(IterableOnce suffix) {
                return IterableOps.concat$(this, suffix);
            }

            public final Object $plus$plus(IterableOnce suffix) {
                return IterableOps.$plus$plus$(this, suffix);
            }

            public Object zip(IterableOnce that) {
                return IterableOps.zip$(this, that);
            }

            public Object zipWithIndex() {
                return IterableOps.zipWithIndex$(this);
            }

            public Object zipAll(Iterable that, Object thisElem, Object thatElem) {
                return IterableOps.zipAll$(this, that, thisElem, thatElem);
            }

            public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> unzip(Function1<V, Tuple2<A1, A2>> asPair) {
                return IterableOps.unzip$(this, asPair);
            }

            public <A1, A2, A3> Tuple3<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>, scala.collection.immutable.Iterable<A3>> unzip3(Function1<V, Tuple3<A1, A2, A3>> asTriple) {
                return IterableOps.unzip3$(this, asTriple);
            }

            public Iterator<scala.collection.immutable.Iterable<V>> tails() {
                return IterableOps.tails$(this);
            }

            public Iterator<scala.collection.immutable.Iterable<V>> inits() {
                return IterableOps.inits$(this);
            }

            public Object tapEach(Function1 f) {
                return IterableOps.tapEach$(this, f);
            }

            public Object $plus$plus$colon(IterableOnce that) {
                return IterableOps.$plus$plus$colon$(this, that);
            }

            public boolean hasDefiniteSize() {
                return IterableOnceOps.hasDefiniteSize$(this);
            }

            public <U> void foreach(Function1<V, U> f) {
                IterableOnceOps.foreach$(this, f);
            }

            public boolean forall(Function1<V, Object> p) {
                return IterableOnceOps.forall$(this, p);
            }

            public boolean exists(Function1<V, Object> p) {
                return IterableOnceOps.exists$(this, p);
            }

            public int count(Function1<V, Object> p) {
                return IterableOnceOps.count$(this, p);
            }

            public Option<V> find(Function1<V, Object> p) {
                return IterableOnceOps.find$(this, p);
            }

            public <B> B foldLeft(B z, Function2<B, V, B> op) {
                return (B)IterableOnceOps.foldLeft$(this, z, op);
            }

            public <B> B foldRight(B z, Function2<V, B, B> op) {
                return (B)IterableOnceOps.foldRight$(this, z, op);
            }

            public final <B> B $div$colon(B z, Function2<B, V, B> op) {
                return (B)IterableOnceOps.$div$colon$(this, z, op);
            }

            public final <B> B $colon$bslash(B z, Function2<V, B, B> op) {
                return (B)IterableOnceOps.$colon$bslash$(this, z, op);
            }

            public <A1> A1 fold(A1 z, Function2<A1, A1, A1> op) {
                return (A1)IterableOnceOps.fold$(this, z, op);
            }

            public <B> B reduce(Function2<B, B, B> op) {
                return (B)IterableOnceOps.reduce$(this, op);
            }

            public <B> Option<B> reduceOption(Function2<B, B, B> op) {
                return IterableOnceOps.reduceOption$(this, op);
            }

            public <B> B reduceLeft(Function2<B, V, B> op) {
                return (B)IterableOnceOps.reduceLeft$(this, op);
            }

            public <B> B reduceRight(Function2<V, B, B> op) {
                return (B)IterableOnceOps.reduceRight$(this, op);
            }

            public <B> Option<B> reduceLeftOption(Function2<B, V, B> op) {
                return IterableOnceOps.reduceLeftOption$(this, op);
            }

            public <B> Option<B> reduceRightOption(Function2<V, B, B> op) {
                return IterableOnceOps.reduceRightOption$(this, op);
            }

            public boolean isEmpty() {
                return IterableOnceOps.isEmpty$(this);
            }

            public boolean nonEmpty() {
                return IterableOnceOps.nonEmpty$(this);
            }

            public int size() {
                return IterableOnceOps.size$(this);
            }

            public final <B> void copyToBuffer(Buffer<B> dest) {
                IterableOnceOps.copyToBuffer$(this, dest);
            }

            public <B> int copyToArray(Object xs) {
                return IterableOnceOps.copyToArray$(this, xs);
            }

            public <B> int copyToArray(Object xs, int start2) {
                return IterableOnceOps.copyToArray$(this, xs, start2);
            }

            public <B> int copyToArray(Object xs, int start2, int len) {
                return IterableOnceOps.copyToArray$(this, xs, start2, len);
            }

            public <B> B sum(Numeric<B> num) {
                return (B)IterableOnceOps.sum$(this, num);
            }

            public <B> B product(Numeric<B> num) {
                return (B)IterableOnceOps.product$(this, num);
            }

            public <B> V min(Ordering<B> ord) {
                return (V)IterableOnceOps.min$(this, ord);
            }

            public <B> Option<V> minOption(Ordering<B> ord) {
                return IterableOnceOps.minOption$(this, ord);
            }

            public <B> V max(Ordering<B> ord) {
                return (V)IterableOnceOps.max$(this, ord);
            }

            public <B> Option<V> maxOption(Ordering<B> ord) {
                return IterableOnceOps.maxOption$(this, ord);
            }

            public <B> V maxBy(Function1<V, B> f, Ordering<B> cmp) {
                return (V)IterableOnceOps.maxBy$(this, f, cmp);
            }

            public <B> Option<V> maxByOption(Function1<V, B> f, Ordering<B> cmp) {
                return IterableOnceOps.maxByOption$(this, f, cmp);
            }

            public <B> V minBy(Function1<V, B> f, Ordering<B> cmp) {
                return (V)IterableOnceOps.minBy$(this, f, cmp);
            }

            public <B> Option<V> minByOption(Function1<V, B> f, Ordering<B> cmp) {
                return IterableOnceOps.minByOption$(this, f, cmp);
            }

            public <B> Option<B> collectFirst(PartialFunction<V, B> pf) {
                return IterableOnceOps.collectFirst$(this, pf);
            }

            public <B> B aggregate(Function0<B> z, Function2<B, V, B> seqop, Function2<B, B, B> combop) {
                return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
            }

            public <B> boolean corresponds(IterableOnce<B> that, Function2<V, B, Object> p) {
                return IterableOnceOps.corresponds$(this, that, p);
            }

            public final String mkString(String start2, String sep, String end) {
                return IterableOnceOps.mkString$(this, start2, sep, end);
            }

            public final String mkString(String sep) {
                return IterableOnceOps.mkString$(this, sep);
            }

            public final String mkString() {
                return IterableOnceOps.mkString$(this);
            }

            public StringBuilder addString(StringBuilder b, String start2, String sep, String end) {
                return IterableOnceOps.addString$(this, b, start2, sep, end);
            }

            public final StringBuilder addString(StringBuilder b, String sep) {
                return IterableOnceOps.addString$(this, b, sep);
            }

            public final StringBuilder addString(StringBuilder b) {
                return IterableOnceOps.addString$(this, b);
            }

            public <C1> C1 to(Factory<V, C1> factory2) {
                return (C1)IterableOnceOps.to$(this, factory2);
            }

            public final Iterator<V> toIterator() {
                return IterableOnceOps.toIterator$(this);
            }

            public List<V> toList() {
                return IterableOnceOps.toList$(this);
            }

            public Vector<V> toVector() {
                return IterableOnceOps.toVector$(this);
            }

            public <K$, V$> Map<K$, V$> toMap($less$colon$less<V, Tuple2<K$, V$>> ev) {
                return IterableOnceOps.toMap$(this, ev);
            }

            public <B> Set<B> toSet() {
                return IterableOnceOps.toSet$(this);
            }

            public Seq<V> toSeq() {
                return IterableOnceOps.toSeq$(this);
            }

            public IndexedSeq<V> toIndexedSeq() {
                return IterableOnceOps.toIndexedSeq$(this);
            }

            public final Stream<V> toStream() {
                return IterableOnceOps.toStream$(this);
            }

            public final <B> Buffer<B> toBuffer() {
                return IterableOnceOps.toBuffer$(this);
            }

            public <B> Object toArray(ClassTag<B> evidence$2) {
                return IterableOnceOps.toArray$(this, evidence$2);
            }

            public Iterable<V> reversed() {
                return IterableOnceOps.reversed$(this);
            }

            public <S extends Stepper<?>> S stepper(StepperShape<V, S> shape) {
                return (S)IterableOnce.stepper$(this, shape);
            }

            public int knownSize() {
                return IterableOnce.knownSize$(this);
            }

            public Iterator<V> iterator() {
                VectorMap vectorMap = this.$outer;
                if (vectorMap == null) {
                    throw null;
                }
                VectorMap keysIterator_this = vectorMap;
                Object var1_1 = null;
                return new /* invalid duplicate definition of identical inner class */.map((Function1<Object, Object> & Serializable)(A x$4) -> ((Tuple2)$this.$outer.underlying().apply(x$4))._2());
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$iterator$1(scala.collection.immutable.VectorMap$$anon$2 java.lang.Object )}, serializedLambda);
            }
        };
    }

    private VectorMap(Vector<Object> fields, Map<K, Tuple2<Object, V>> underlying, int dropped) {
        this.fields = fields;
        this.underlying = underlying;
        this.dropped = dropped;
        this.size = underlying.size();
    }

    public VectorMap(Vector<K> fields, Map<K, Tuple2<Object, V>> underlying) {
        this(fields, underlying, 0);
    }

    public static final class Tombstone
    implements Product,
    Serializable {
        private final int distance;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public int distance() {
            return this.distance;
        }

        public Tombstone copy(int distance) {
            return new Tombstone(distance);
        }

        public int copy$default$1() {
            return this.distance();
        }

        @Override
        public String productPrefix() {
            return "Tombstone";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            switch (x$1) {
                case 0: {
                    return this.distance();
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
        public boolean canEqual(Object x$1) {
            return x$1 instanceof Tombstone;
        }

        @Override
        public String productElementName(int x$1) {
            switch (x$1) {
                case 0: {
                    return "distance";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, "Tombstone".hashCode());
            n = Statics.mix(n, this.distance());
            int finalizeHash_length = 1;
            return Statics.avalanche(n ^ finalizeHash_length);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        @Override
        public boolean equals(Object x$1) {
            block3: {
                block2: {
                    if (this == x$1) break block2;
                    if (!(x$1 instanceof Tombstone)) break block3;
                    Tombstone tombstone = (Tombstone)x$1;
                    if (this.distance() != tombstone.distance()) break block3;
                }
                return true;
            }
            return false;
        }

        public Tombstone(int distance) {
            this.distance = distance;
        }
    }
}

