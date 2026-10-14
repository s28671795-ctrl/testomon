/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.ArrayOps$;
import scala.collection.BitSet;
import scala.collection.BitSetOps;
import scala.collection.BitSetOps$;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.Set;
import scala.collection.SortedIterableFactory;
import scala.collection.SortedOps;
import scala.collection.SortedSetFactoryDefaults;
import scala.collection.SortedSetOps;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.View;
import scala.collection.immutable.AbstractSet;
import scala.collection.immutable.BitSet$;
import scala.collection.immutable.SetOps;
import scala.collection.immutable.SortedSet;
import scala.collection.immutable.StrictOptimizedSetOps;
import scala.collection.immutable.StrictOptimizedSortedSetOps;
import scala.collection.mutable.Builder;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.Either;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@ScalaSignature(bytes="\u0006\u0005\r}a!\u0002!B\u0003CA\u0005\"B5\u0001\t\u0003Q\u0007\"B6\u0001\t\u0003b\u0007\"\u00029\u0001\t#\n\b\"B<\u0001\t#B\bBB@\u0001\t\u0003\n\t\u0001C\u0004\u0002\u0004\u0001!\t!!\u0002\t\u0011\u0005E\u0003\u0001\"\u0005D\u0005'BqAa\u0016\u0001\t\u0003\u0011I\u0006C\u0004\u0003`\u0001!\tA!\u0019\t\u000f\u0005M\u0004A\"\u0005\u0003f!9!1\u000e\u0001\u0005B\t5\u0004b\u0002B6\u0001\u0011\u0005#Q\u000f\u0005\b\u0005_\u0003A\u0011\tBY\u0011\u001d\u0011y\u000b\u0001C!\u0005oCqAa4\u0001\t\u0003\u0012\t\u000eC\u0004\u0003P\u0002!\tE!8\t\u000f\tM\b\u0001\"\u0011\u0003v\"A!\u0011\t\u0001!\n#\u0019IbB\u0004\u0003\u001e\u0005C\t!a\u0002\u0007\r\u0001\u000b\u0005\u0012AA\u0005\u0011\u0019IG\u0003\"\u0001\u0002&!1\u0001\u000f\u0006C\u0001\u0003OA\u0001b \u000bC\u0002\u0013\u0015\u0011\u0011\u0001\u0005\b\u0003[!\u0002\u0015!\u0004Y\u0011\u0019\ty\u0003\u0006C\u0001q\"9\u0011\u0011\u0007\u000b\u0005\n\u0005M\u0002bBA\")\u0011\u0005\u0011Q\t\u0005\b\u0003#\"B\u0011AA*\r\u0019\t9\u0006\u0006\u0001\u0002Z!Q\u0011\u0011J\u000f\u0003\u0006\u0004%\t!a\u0017\t\u0015\u0005uSD!A!\u0002\u0013\tI\u0004\u0003\u0004j;\u0011\u0005\u0011q\f\u0005\t\u0003OjB\u0011C\"\u0002j!A\u00111N\u000f\u0005\u0012\r\u000bi\u0007\u0003\u0005\u0002tu!\tbQA;\u0011\u001d\ti(\bC!\u0003\u007fBq!!#\u001e\t\u0003\nYI\u0002\u0004\u00026R\u0001\u0011q\u0017\u0005\u000b\u0003s3#Q1A\u0005\u0002\u0005m\u0003BCA^M\t\u0005\t\u0015!\u0003\u0002:!Q\u0011Q\u0018\u0014\u0003\u0006\u0004%\t!a\u0017\t\u0015\u0005}fE!A!\u0002\u0013\tI\u0004\u0003\u0004jM\u0011\u0005\u0011\u0011\u0019\u0005\t\u0003O2C\u0011C\"\u0002j!A\u00111\u000e\u0014\u0005\u0012\r\u000bI\r\u0003\u0005\u0002t\u0019\"\tbQAg\u0011\u001d\tiH\nC!\u0003'Dq!!#'\t\u0003\n9N\u0002\u0004\u0002`R\u0001\u0011\u0011\u001d\u0005\u000b\u0003\u0013\n$Q1A\u0005\u0002\u0005\r\bBCA/c\t\u0005\t\u0015!\u0003\u0002L!1\u0011.\rC\u0001\u0003KD\u0001\"a\u001a2\t#\u0019\u0015\u0011\u000e\u0005\t\u0003W\nD\u0011C\"\u0002l\"A\u00111O\u0019\u0005\u0012\r\u000by\u000fC\u0004\u0002~E\"\t%!>\t\u000f\u0005%\u0015\u0007\"\u0011\u0002|\"9!\u0011A\u0019\u0005B\u0005\rhA\u0002B\u0003)\u0019\u00119\u0001\u0003\u0006tw\t\u0005\t\u0015!\u0003Y\u0005GAa![\u001e\u0005\u0002\t\u0015\u0002\u0002\u0003B\u0016w\u0001&\tB!\f\t\u0013\t\u0005C#!A\u0005\n\t\r#A\u0002\"jiN+GO\u0003\u0002C\u0007\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003\t\u0016\u000b!bY8mY\u0016\u001cG/[8o\u0015\u00051\u0015!B:dC2\f7\u0001A\n\t\u0001%\u000bF+\u0017/`EB\u0019!jS'\u000e\u0003\u0005K!\u0001T!\u0003\u0017\u0005\u00137\u000f\u001e:bGR\u001cV\r\u001e\t\u0003\u001d>k\u0011!R\u0005\u0003!\u0016\u00131!\u00138u!\rQ%+T\u0005\u0003'\u0006\u0013\u0011bU8si\u0016$7+\u001a;\u0011\u000b)+Vj\u0016-\n\u0005Y\u000b%\u0001D*peR,GmU3u\u001fB\u001c\bC\u0001&S!\tQ\u0005\u0001E\u0003K56;\u0006,\u0003\u0002\\\u0003\nY2\u000b\u001e:jGR|\u0005\u000f^5nSj,GmU8si\u0016$7+\u001a;PaN\u0004\"!\u00180\u000e\u0003\rK!\u0001Q\"\u0011\u0007u\u0003\u0007,\u0003\u0002b\u0007\nI!)\u001b;TKR|\u0005o\u001d\t\u0003G\u001at!A\u00143\n\u0005\u0015,\u0015a\u00029bG.\fw-Z\u0005\u0003O\"\u0014AbU3sS\u0006d\u0017N_1cY\u0016T!!Z#\u0002\rqJg.\u001b;?)\u0005A\u0016\u0001C;og>\u0014H/\u001a3\u0016\u00035\u00042A\u00138N\u0013\ty\u0017IA\u0002TKR\fAB\u001a:p[N\u0003XmY5gS\u000e$\"\u0001\u0017:\t\u000bM\u001c\u0001\u0019\u0001;\u0002\t\r|G\u000e\u001c\t\u0004;Vl\u0015B\u0001<D\u00051IE/\u001a:bE2,wJ\\2f\u0003IqWm^*qK\u000eLg-[2Ck&dG-\u001a:\u0016\u0003e\u0004BA_?N16\t1P\u0003\u0002}\u0007\u00069Q.\u001e;bE2,\u0017B\u0001@|\u0005\u001d\u0011U/\u001b7eKJ\fQ!Z7qif,\u0012\u0001W\u0001\u000eE&$8+\u001a;GC\u000e$xN]=\u0016\u0005\u0005\u001d\u0001C\u0001&\u0015'\u001d!\u00121BA\t\u0003/\u00012ATA\u0007\u0013\r\ty!\u0012\u0002\u0007\u0003:L(+\u001a4\u0011\u000bu\u000b\u0019\"\u0014-\n\u0007\u0005U1IA\fTa\u0016\u001c\u0017NZ5d\u0013R,'/\u00192mK\u001a\u000b7\r^8ssB!\u0011\u0011DA\u0012\u001b\t\tYB\u0003\u0003\u0002\u001e\u0005}\u0011AA5p\u0015\t\t\t#\u0001\u0003kCZ\f\u0017bA4\u0002\u001cQ\u0011\u0011q\u0001\u000b\u00041\u0006%\u0002BBA\u0016-\u0001\u0007A/\u0001\u0002ji\u00061Q-\u001c9us\u0002\n!B\\3x\u0005VLG\u000eZ3s\u0003-\u0019'/Z1uKNk\u0017\r\u001c7\u0015\u000ba\u000b)$a\u0010\t\u000f\u0005]\"\u00041\u0001\u0002:\u0005\t\u0011\rE\u0002O\u0003wI1!!\u0010F\u0005\u0011auN\\4\t\u000f\u0005\u0005#\u00041\u0001\u0002:\u0005\t!-A\u0006ge>l')\u001b;NCN\\Gc\u0001-\u0002H!9\u0011\u0011J\u000eA\u0002\u0005-\u0013!B3mK6\u001c\b#\u0002(\u0002N\u0005e\u0012bAA(\u000b\n)\u0011I\u001d:bs\u0006\tbM]8n\u0005&$X*Y:l\u001d>\u001cu\u000e]=\u0015\u0007a\u000b)\u0006C\u0004\u0002Jq\u0001\r!a\u0013\u0003\u000f\tKGoU3ucM\u0011Q\u0004W\u000b\u0003\u0003s\ta!\u001a7f[N\u0004C\u0003BA1\u0003K\u00022!a\u0019\u001e\u001b\u0005!\u0002bBA%A\u0001\u0007\u0011\u0011H\u0001\u0007]^|'\u000fZ:\u0016\u00035\u000bAa^8sIR!\u0011\u0011HA8\u0011\u0019\t\tH\ta\u0001\u001b\u0006\u0019\u0011\u000e\u001a=\u0002\u0015U\u0004H-\u0019;f/>\u0014H\rF\u0003Y\u0003o\nI\b\u0003\u0004\u0002r\r\u0002\r!\u0014\u0005\b\u0003w\u001a\u0003\u0019AA\u001d\u0003\u00059\u0018\u0001\u00023jM\u001a$2\u0001WAA\u0011\u001d\t\u0019\t\na\u0001\u0003\u000b\u000bQa\u001c;iKJ\u0004B!XAD\u001b&\u0011qnQ\u0001\u000bM&dG/\u001a:J[BdG#\u0002-\u0002\u000e\u0006u\u0005bBAHK\u0001\u0007\u0011\u0011S\u0001\u0005aJ,G\r\u0005\u0004O\u0003'k\u0015qS\u0005\u0004\u0003++%!\u0003$v]\u000e$\u0018n\u001c82!\rq\u0015\u0011T\u0005\u0004\u00037+%a\u0002\"p_2,\u0017M\u001c\u0005\b\u0003?+\u0003\u0019AAL\u0003%I7O\u00127jaB,G\rK\u0006\u001e\u0003G\u000bI+a+\u00020\u0006E\u0006c\u0001(\u0002&&\u0019\u0011qU#\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u0005\u00055\u0016\u0001Q%na2,W.\u001a8uCRLwN\u001c\u0011dY\u0006\u001c8/Z:!_\u001a\u0004#)\u001b;TKR\u00043\u000f[8vY\u0012\u0004cn\u001c;!E\u0016\u0004\u0013mY2fgN,G\r\t3je\u0016\u001cG\u000f\\=\u0002\u000bMLgnY3\"\u0005\u0005M\u0016A\u0002\u001a/cMr\u0003GA\u0004CSR\u001cV\r\u001e\u001a\u0014\u0005\u0019B\u0016AB3mK6\u001c\b'A\u0004fY\u0016l7\u000f\r\u0011\u0002\r\u0015dW-\\:2\u0003\u001d)G.Z7tc\u0001\"b!a1\u0002F\u0006\u001d\u0007cAA2M!9\u0011\u0011X\u0016A\u0002\u0005e\u0002bBA_W\u0001\u0007\u0011\u0011\b\u000b\u0005\u0003s\tY\r\u0003\u0004\u0002r5\u0002\r!\u0014\u000b\u00061\u0006=\u0017\u0011\u001b\u0005\u0007\u0003cr\u0003\u0019A'\t\u000f\u0005md\u00061\u0001\u0002:Q\u0019\u0001,!6\t\u000f\u0005\ru\u00061\u0001\u0002\u0006R)\u0001,!7\u0002\\\"9\u0011q\u0012\u0019A\u0002\u0005E\u0005bBAPa\u0001\u0007\u0011q\u0013\u0015\fM\u0005\r\u0016\u0011VAV\u0003_\u000b\tLA\u0004CSR\u001cV\r\u001e(\u0014\u0005EBVCAA&)\u0011\t9/!;\u0011\u0007\u0005\r\u0014\u0007C\u0004\u0002JQ\u0002\r!a\u0013\u0015\t\u0005e\u0012Q\u001e\u0005\u0007\u0003c2\u0004\u0019A'\u0015\u000ba\u000b\t0a=\t\r\u0005Et\u00071\u0001N\u0011\u001d\tYh\u000ea\u0001\u0003s!2\u0001WA|\u0011\u001d\tI\u0010\u000fa\u0001\u0003\u000b\u000bA\u0001\u001e5biR)\u0001,!@\u0002\u0000\"9\u0011qR\u001dA\u0002\u0005E\u0005bBAPs\u0001\u0007\u0011qS\u0001\ni>\u0014\u0015\u000e^'bg.D3\"MAR\u0003S\u000bY+a,\u00022\n\u00112+\u001a:jC2L'0\u0019;j_:\u0004&o\u001c=z'\rY$\u0011\u0002\t\u0005\u0005\u0017\u0011yB\u0004\u0003\u0003\u000e\tma\u0002\u0002B\b\u00053qAA!\u0005\u0003\u00185\u0011!1\u0003\u0006\u0004\u0005+9\u0015A\u0002\u001fs_>$h(C\u0001G\u0013\t!U)C\u0002\u0003\u001e\r\u000baAQ5u'\u0016$\u0018\u0002\u0002B\u0003\u0005CQ1A!\bD\u0013\r\u0019(q\u0004\u000b\u0005\u0005O\u0011I\u0003E\u0002\u0002dmBQa]\u001fA\u0002a\u000b1B]3bIJ+7o\u001c7wKR\u0011!q\u0006\t\u0004\u001d\nE\u0012b\u0001B\u001a\u000b\n\u0019\u0011I\\=)\u000fm\u00129D!\u0010\u0003@A\u0019aJ!\u000f\n\u0007\tmRI\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t1!\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0003FA!!q\tB'\u001b\t\u0011IE\u0003\u0003\u0003L\u0005}\u0011\u0001\u00027b]\u001eLAAa\u0014\u0003J\t1qJ\u00196fGRDs\u0001\u0006B\u001c\u0005{\u0011y\u0004F\u0002Y\u0005+Bq!!\u0013\b\u0001\u0004\tY%\u0001\u0003j]\u000edGc\u0001-\u0003\\!1!Q\f\u0005A\u00025\u000bA!\u001a7f[\u0006!Q\r_2m)\rA&1\r\u0005\u0007\u0005;J\u0001\u0019A'\u0015\u000ba\u00139G!\u001b\t\r\u0005E$\u00021\u0001N\u0011\u001d\tYH\u0003a\u0001\u0003s\t1!\\1q)\rA&q\u000e\u0005\b\u0005cZ\u0001\u0019\u0001B:\u0003\u00051\u0007#\u0002(\u0002\u00146kU\u0003\u0002B<\u0005\u0003#BA!\u001f\u0003,R!!1\u0010BG!\u0011Q%K! \u0011\t\t}$\u0011\u0011\u0007\u0001\t\u001d\u0011\u0019\t\u0004b\u0001\u0005\u000b\u0013\u0011AQ\t\u0005\u0005\u000f\u0013y\u0003E\u0002O\u0005\u0013K1Aa#F\u0005\u001dqu\u000e\u001e5j]\u001eDqAa$\r\u0001\b\u0011\t*\u0001\u0002fmB)1Ma%\u0003~%\u0019!Q\u00135\u0003\u0011=\u0013H-\u001a:j]\u001eD\u0003B!$\u0003\u001a\n\u0015&q\u0015\t\u0005\u00057\u0013\t+\u0004\u0002\u0003\u001e*\u0019!qT#\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0003$\nu%\u0001E5na2L7-\u001b;O_R4u.\u001e8e\u0003\ri7oZ\u0011\u0003\u0005S\u000baPT8!S6\u0004H.[2ji\u0002z%\u000fZ3sS:<7\fJ>C{v\u0003cm\\;oI\u0002\"x\u000e\t2vS2$\u0007%\u0019\u0011T_J$X\rZ*fin#3PQ?^]\u0001Jv.\u001e\u0011nCf\u0004s/\u00198uAQ|\u0007%\u001e9dCN$\b\u0005^8!C\u0002\u001aV\r^.J]Rl\u0006EZ5sgR\u0004#-\u001f\u0011dC2d\u0017N\\4!AVt7o\u001c:uK\u0012\u0004g\u0006C\u0004\u0003r1\u0001\rA!,\u0011\r9\u000b\u0019*\u0014B?\u0003\u001d1G.\u0019;NCB$2\u0001\u0017BZ\u0011\u001d\u0011\t(\u0004a\u0001\u0005k\u0003RATAJ\u001bR,BA!/\u0003BR!!1\u0018Be)\u0011\u0011iLa1\u0011\t)\u0013&q\u0018\t\u0005\u0005\u007f\u0012\t\rB\u0004\u0003\u0004:\u0011\rA!\"\t\u000f\t=e\u0002q\u0001\u0003FB)1Ma%\u0003@\"B!1\u0019BM\u0005K\u00139\u000bC\u0004\u0003r9\u0001\rAa3\u0011\r9\u000b\u0019*\u0014Bg!\u0011iVOa0\u0002\u000f\r|G\u000e\\3diR\u0019\u0001La5\t\u000f\tUw\u00021\u0001\u0003X\u0006\u0011\u0001O\u001a\t\u0006\u001d\neW*T\u0005\u0004\u00057,%a\u0004)beRL\u0017\r\u001c$v]\u000e$\u0018n\u001c8\u0016\t\t}'q\u001d\u000b\u0005\u0005C\u0014y\u000f\u0006\u0003\u0003d\n%\b\u0003\u0002&S\u0005K\u0004BAa \u0003h\u00129!1\u0011\tC\u0002\t\u0015\u0005b\u0002BH!\u0001\u000f!1\u001e\t\u0006G\nM%Q\u001d\u0015\t\u0005S\u0014IJ!*\u0003(\"9!Q\u001b\tA\u0002\tE\bC\u0002(\u0003Z6\u0013)/A\u0002{SB,BAa>\u0004\u0006Q!!\u0011`B\t)\u0011\u0011Ypa\u0002\u0011\t)\u0013&Q \t\u0007\u001d\n}Xja\u0001\n\u0007\r\u0005QI\u0001\u0004UkBdWM\r\t\u0005\u0005\u007f\u001a)\u0001B\u0004\u0003\u0004F\u0011\rA!\"\t\u000f\t=\u0015\u0003q\u0001\u0004\nA)1Ma%\u0003~\"B1q\u0001BM\u0005K\u001bi!\t\u0002\u0004\u0010\u0005\tYAT8!S6\u0004H.[2ji\u0002z%\u000fZ3sS:<7\fJ>C{v\u0003cm\\;oI\u0002\"x\u000e\t2vS2$\u0007%\u0019\u0011T_J$X\rZ*finC\u0013J\u001c;-A\u0011Z()`\u0015^]\u0001Jv.\u001e\u0011nCf\u0004s/\u00198uAQ|\u0007%\u001e9dCN$\b\u0005^8!C\u0002\u001aV\r^.J]Rl\u0006EZ5sgR\u0004#-\u001f\u0011dC2d\u0017N\\4!AVt7o\u001c:uK\u0012\u0004g\u0006C\u0004\u0002zF\u0001\raa\u0005\u0011\r\rU1qCB\u0002\u001d\r\u0011y\u0001Z\u0005\u0003m\"$\"!a\u0003*\t\u0001ib%\r\u0015\b'\t]\"Q\bB \u0001")
public abstract class BitSet
extends AbstractSet<Object>
implements SortedSet<Object>,
StrictOptimizedSortedSetOps<Object, SortedSet, BitSet>,
scala.collection.BitSet,
Serializable {
    public static BitSet fromBitMask(long[] elems) {
        return BitSet$.MODULE$.fromBitMask(elems);
    }

    public static Builder<Object, BitSet> newBuilder() {
        return BitSet$.MODULE$.newBuilder();
    }

    public static Factory<Object, BitSet> specificIterableFactory() {
        return BitSet$.MODULE$;
    }

    public static Object fill(int n, Function0 elem) {
        BitSet$ fill_this = BitSet$.MODULE$;
        View.Fill fromSpecific_it = new View.Fill(n, elem);
        return fill_this.fromSpecific((IterableOnce)fromSpecific_it);
    }

    @Override
    public String stringPrefix() {
        return scala.collection.BitSet.stringPrefix$(this);
    }

    @Override
    public /* synthetic */ int scala$collection$BitSetOps$$super$max(Ordering ord) {
        return BoxesRunTime.unboxToInt(SortedSetOps.max$(this, ord));
    }

    @Override
    public /* synthetic */ int scala$collection$BitSetOps$$super$min(Ordering ord) {
        return BoxesRunTime.unboxToInt(SortedSetOps.min$(this, ord));
    }

    @Override
    public /* synthetic */ scala.collection.BitSet scala$collection$BitSetOps$$super$concat(IterableOnce that) {
        return (scala.collection.BitSet)((Object)StrictOptimizedSetOps.concat$(this, that));
    }

    @Override
    public /* synthetic */ scala.collection.BitSet scala$collection$BitSetOps$$super$intersect(Set that) {
        return (scala.collection.BitSet)scala.collection.SetOps.intersect$(this, that);
    }

    @Override
    public /* synthetic */ scala.collection.BitSet scala$collection$BitSetOps$$super$diff(Set that) {
        return (scala.collection.BitSet)((Object)SetOps.diff$(this, that));
    }

    @Override
    public final Ordering<Object> ordering() {
        return BitSetOps.ordering$(this);
    }

    @Override
    public boolean contains(int elem) {
        return BitSetOps.contains$(this, elem);
    }

    @Override
    public Iterator<Object> iterator() {
        return BitSetOps.iterator$(this);
    }

    @Override
    public Iterator<Object> iteratorFrom(int start2) {
        return BitSetOps.iteratorFrom$(this, start2);
    }

    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
        return (S)BitSetOps.stepper$(this, shape);
    }

    @Override
    public int size() {
        return BitSetOps.size$(this);
    }

    @Override
    public boolean isEmpty() {
        return BitSetOps.isEmpty$(this);
    }

    @Override
    public <B> int max(Ordering<B> ord) {
        return BitSetOps.max$(this, ord);
    }

    @Override
    public <B> int min(Ordering<B> ord) {
        return BitSetOps.min$(this, ord);
    }

    @Override
    public <U> void foreach(Function1<Object, U> f) {
        BitSetOps.foreach$(this, f);
    }

    @Override
    public long[] toBitMask() {
        return BitSetOps.toBitMask$(this);
    }

    @Override
    public scala.collection.BitSet rangeImpl(Option from, Option until) {
        return BitSetOps.rangeImpl$(this, from, until);
    }

    @Override
    public scala.collection.BitSet concat(IterableOnce other) {
        return BitSetOps.concat$(this, other);
    }

    @Override
    public scala.collection.BitSet intersect(Set other) {
        return BitSetOps.intersect$(this, other);
    }

    @Override
    public scala.collection.BitSet diff(Set other) {
        return BitSetOps.diff$(this, other);
    }

    @Override
    public scala.collection.BitSet xor(scala.collection.BitSet other) {
        return BitSetOps.xor$(this, other);
    }

    @Override
    public final scala.collection.BitSet $up(scala.collection.BitSet other) {
        return BitSetOps.$up$(this, other);
    }

    @Override
    public Tuple2<BitSet, BitSet> partition(Function1<Object, Object> p) {
        return BitSetOps.partition$(this, p);
    }

    @Override
    public Tuple2<BitSet, BitSet> span(Function1<Object, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<scala.collection.immutable.Set<A1>, scala.collection.immutable.Set<A2>> unzip(Function1<Object, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<scala.collection.immutable.Set<A1>, scala.collection.immutable.Set<A2>, scala.collection.immutable.Set<A3>> unzip3(Function1<Object, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<Object, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<Object, IterableOnce<B>> f) {
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
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<Object, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<Object, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<Object, B>, C2> b) {
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
    public <A1, A2> Tuple2<scala.collection.immutable.Set<A1>, scala.collection.immutable.Set<A2>> partitionMap(Function1<Object, Either<A1, A2>> f) {
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
    public SortedIterableFactory<SortedSet> sortedIterableFactory() {
        return SortedSet.sortedIterableFactory$(this);
    }

    @Override
    public /* synthetic */ boolean scala$collection$SortedSet$$super$equals(Object that) {
        return Set.equals$(this, that);
    }

    @Override
    public boolean equals(Object that) {
        return scala.collection.SortedSet.equals$(this, that);
    }

    @Override
    public SortedSetOps.WithFilter<Object, ?, ?> withFilter(Function1<Object, Object> p) {
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
    public Iterator keysIteratorFrom(Object start2) {
        return SortedSetOps.keysIteratorFrom$(this, start2);
    }

    @Override
    public Object firstKey() {
        return SortedSetOps.firstKey$(this);
    }

    @Override
    public Object lastKey() {
        return SortedSetOps.lastKey$(this);
    }

    @Override
    public Option minAfter(Object key) {
        return SortedSetOps.minAfter$(this, key);
    }

    @Override
    public Option maxBefore(Object key) {
        return SortedSetOps.maxBefore$(this, key);
    }

    @Override
    public SortedSetOps rangeTo(Object to) {
        return SortedSetOps.rangeTo$(this, to);
    }

    @Override
    public int compare(Object k0, Object k1) {
        return SortedOps.compare$(this, k0, k1);
    }

    @Override
    public Object range(Object from, Object until) {
        return SortedOps.range$(this, from, until);
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

    @Override
    public scala.collection.immutable.Set<Object> unsorted() {
        return this;
    }

    @Override
    public BitSet fromSpecific(IterableOnce<Object> coll) {
        return this.bitSetFactory().fromSpecific((IterableOnce)coll);
    }

    @Override
    public Builder<Object, BitSet> newSpecificBuilder() {
        return this.bitSetFactory().newBuilder();
    }

    @Override
    public BitSet empty() {
        return this.bitSetFactory().empty();
    }

    public BitSet$ bitSetFactory() {
        return BitSet$.MODULE$;
    }

    @Override
    public BitSet fromBitMaskNoCopy(long[] elems) {
        return BitSet$.MODULE$.fromBitMaskNoCopy(elems);
    }

    @Override
    public BitSet incl(int elem) {
        if (!(elem >= 0)) {
            throw new IllegalArgumentException(new StringBuilder(20).append("requirement failed: ").append((Object)"bitset element must be >= 0").toString());
        }
        if (this.contains(elem)) {
            return this;
        }
        int idx = elem >> 6;
        return this.updateWord(idx, this.word(idx) | 1L << elem);
    }

    @Override
    public BitSet excl(int elem) {
        if (!(elem >= 0)) {
            throw new IllegalArgumentException(new StringBuilder(20).append("requirement failed: ").append((Object)"bitset element must be >= 0").toString());
        }
        if (this.contains(elem)) {
            int idx = elem >> 6;
            return this.updateWord(idx, this.word(idx) & (1L << elem ^ 0xFFFFFFFFFFFFFFFFL));
        }
        return this;
    }

    public abstract BitSet updateWord(int var1, long var2);

    /*
     * WARNING - void declaration
     */
    @Override
    public BitSet map(Function1<Object, Object> f) {
        Builder<Object, BitSet> strictOptimizedMap_b = this.newSpecificBuilder();
        Iterator strictOptimizedMap_it = this.iterator();
        while (strictOptimizedMap_it.hasNext()) {
            void strictOptimizedMap_$plus$eq_elem;
            Object object = f.apply(strictOptimizedMap_it.next());
            if (strictOptimizedMap_b == null) {
                throw null;
            }
            strictOptimizedMap_b.addOne(strictOptimizedMap_$plus$eq_elem);
            object = null;
        }
        return strictOptimizedMap_b.result();
    }

    @Override
    public <B> SortedSet<B> map(Function1<Object, B> f, Ordering<B> ev) {
        return (SortedSet)scala.collection.StrictOptimizedSortedSetOps.map$(this, f, ev);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public BitSet flatMap(Function1<Object, IterableOnce<Object>> f) {
        Builder<Object, BitSet> strictOptimizedFlatMap_b = this.newSpecificBuilder();
        Iterator strictOptimizedFlatMap_it = this.iterator();
        while (strictOptimizedFlatMap_it.hasNext()) {
            void strictOptimizedFlatMap_$plus$plus$eq_xs;
            IterableOnce<Object> iterableOnce = f.apply(strictOptimizedFlatMap_it.next());
            if (strictOptimizedFlatMap_b == null) {
                throw null;
            }
            strictOptimizedFlatMap_b.addAll((IterableOnce<Object>)strictOptimizedFlatMap_$plus$plus$eq_xs);
            iterableOnce = null;
        }
        return strictOptimizedFlatMap_b.result();
    }

    @Override
    public <B> SortedSet<B> flatMap(Function1<Object, IterableOnce<B>> f, Ordering<B> ev) {
        return (SortedSet)scala.collection.StrictOptimizedSortedSetOps.flatMap$(this, f, ev);
    }

    @Override
    public BitSet collect(PartialFunction<Object, Object> pf) {
        Builder<Object, BitSet> strictOptimizedCollect_b = this.newSpecificBuilder();
        Object strictOptimizedCollect_marker = Statics.pfMarker;
        Iterator strictOptimizedCollect_it = this.iterator();
        while (strictOptimizedCollect_it.hasNext()) {
            Object strictOptimizedCollect_elem = strictOptimizedCollect_it.next();
            Object strictOptimizedCollect_v = pf.applyOrElse(strictOptimizedCollect_elem, arg_0 -> StrictOptimizedIterableOps.$anonfun$strictOptimizedCollect$1(strictOptimizedCollect_marker, arg_0));
            if (strictOptimizedCollect_marker == strictOptimizedCollect_v) continue;
            if (strictOptimizedCollect_b == null) {
                throw null;
            }
            strictOptimizedCollect_b.addOne(strictOptimizedCollect_v);
        }
        return strictOptimizedCollect_b.result();
    }

    @Override
    public <B> SortedSet<B> collect(PartialFunction<Object, B> pf, Ordering<B> ev) {
        return (SortedSet)scala.collection.StrictOptimizedSortedSetOps.collect$(this, pf, ev);
    }

    @Override
    public <B> SortedSet<Tuple2<Object, B>> zip(IterableOnce<B> that, Ordering<Tuple2<Object, B>> ev) {
        return (SortedSet)scala.collection.StrictOptimizedSortedSetOps.zip$(this, that, ev);
    }

    public Object writeReplace() {
        return new SerializationProxy(this);
    }

    public static final /* synthetic */ String $anonfun$incl$1() {
        return "bitset element must be >= 0";
    }

    public static final /* synthetic */ String $anonfun$excl$1() {
        return "bitset element must be >= 0";
    }

    public static class BitSet1
    extends BitSet {
        private final long elems;

        public long elems() {
            return this.elems;
        }

        @Override
        public int nwords() {
            return 1;
        }

        @Override
        public long word(int idx) {
            if (idx == 0) {
                return this.elems();
            }
            return 0L;
        }

        @Override
        public BitSet updateWord(int idx, long w) {
            if (idx == 0) {
                return new BitSet1(w);
            }
            if (idx == 1) {
                return BitSet$.MODULE$.scala$collection$immutable$BitSet$$createSmall(this.elems(), w);
            }
            return this.fromBitMaskNoCopy(BitSetOps$.MODULE$.updateArray(new long[]{this.elems()}, idx, w));
        }

        @Override
        public BitSet diff(Set<Object> other) {
            if (other instanceof scala.collection.BitSet) {
                scala.collection.BitSet bitSet = (scala.collection.BitSet)other;
                switch (bitSet.nwords()) {
                    case 0: {
                        return this;
                    }
                }
                long newElems = this.elems() & (bitSet.word(0) ^ 0xFFFFFFFFFFFFFFFFL);
                if (newElems == 0L) {
                    return this.empty();
                }
                return new BitSet1(newElems);
            }
            return (BitSet)BitSetOps.diff$(this, other);
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public BitSet filterImpl(Function1<Object, Object> pred, boolean isFlipped) {
            long _elems;
            long l;
            boolean bl = false;
            long computeWordForFilter_oldWord = this.elems();
            if (computeWordForFilter_oldWord == 0L) {
                l = 0L;
            } else {
                void computeWordForFilter_wordIndex;
                long computeWordForFilter_w = computeWordForFilter_oldWord;
                int computeWordForFilter_trailingZeroes = Long.numberOfTrailingZeros(computeWordForFilter_oldWord);
                long computeWordForFilter_jmask = 1L << computeWordForFilter_trailingZeroes;
                void computeWordForFilter_maxJ = (computeWordForFilter_wordIndex + true) * 64 - Long.numberOfLeadingZeros(computeWordForFilter_oldWord);
                for (void computeWordForFilter_j = computeWordForFilter_wordIndex * 64 + computeWordForFilter_trailingZeroes; computeWordForFilter_j != computeWordForFilter_maxJ; ++computeWordForFilter_j) {
                    if ((computeWordForFilter_w & computeWordForFilter_jmask) != 0L && pred.apply$mcZI$sp((int)computeWordForFilter_j) == isFlipped) {
                        computeWordForFilter_w &= computeWordForFilter_jmask ^ 0xFFFFFFFFFFFFFFFFL;
                    }
                    computeWordForFilter_jmask <<= 1;
                }
                l = _elems = computeWordForFilter_w;
            }
            if (_elems == 0L) {
                return this.empty();
            }
            return new BitSet1(_elems);
        }

        public BitSet1(long elems) {
            this.elems = elems;
        }
    }

    public static class BitSet2
    extends BitSet {
        private final long elems0;
        private final long elems1;

        public long elems0() {
            return this.elems0;
        }

        public long elems1() {
            return this.elems1;
        }

        @Override
        public int nwords() {
            return 2;
        }

        @Override
        public long word(int idx) {
            if (idx == 0) {
                return this.elems0();
            }
            if (idx == 1) {
                return this.elems1();
            }
            return 0L;
        }

        @Override
        public BitSet updateWord(int idx, long w) {
            if (idx == 0) {
                return new BitSet2(w, this.elems1());
            }
            if (idx == 1) {
                return BitSet$.MODULE$.scala$collection$immutable$BitSet$$createSmall(this.elems0(), w);
            }
            return this.fromBitMaskNoCopy(BitSetOps$.MODULE$.updateArray(new long[]{this.elems0(), this.elems1()}, idx, w));
        }

        @Override
        public BitSet diff(Set<Object> other) {
            if (other instanceof scala.collection.BitSet) {
                scala.collection.BitSet bitSet = (scala.collection.BitSet)other;
                switch (bitSet.nwords()) {
                    case 0: {
                        return this;
                    }
                    case 1: {
                        return new BitSet2(this.elems0() & (bitSet.word(0) ^ 0xFFFFFFFFFFFFFFFFL), this.elems1());
                    }
                }
                long _elems0 = this.elems0() & (bitSet.word(0) ^ 0xFFFFFFFFFFFFFFFFL);
                long _elems1 = this.elems1() & (bitSet.word(1) ^ 0xFFFFFFFFFFFFFFFFL);
                if (_elems1 == 0L) {
                    if (_elems0 == 0L) {
                        return this.empty();
                    }
                    return new BitSet1(_elems0);
                }
                return new BitSet2(_elems0, _elems1);
            }
            return (BitSet)BitSetOps.diff$(this, other);
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public BitSet filterImpl(Function1<Object, Object> pred, boolean isFlipped) {
            long _elems1;
            long l;
            long l2;
            boolean bl = false;
            long computeWordForFilter_oldWord = this.elems0();
            if (computeWordForFilter_oldWord == 0L) {
                l2 = 0L;
            } else {
                void computeWordForFilter_wordIndex;
                long computeWordForFilter_w = computeWordForFilter_oldWord;
                int computeWordForFilter_trailingZeroes = Long.numberOfTrailingZeros(computeWordForFilter_oldWord);
                long computeWordForFilter_jmask = 1L << computeWordForFilter_trailingZeroes;
                void computeWordForFilter_maxJ = (computeWordForFilter_wordIndex + true) * 64 - Long.numberOfLeadingZeros(computeWordForFilter_oldWord);
                for (void computeWordForFilter_j = computeWordForFilter_wordIndex * 64 + computeWordForFilter_trailingZeroes; computeWordForFilter_j != computeWordForFilter_maxJ; ++computeWordForFilter_j) {
                    if ((computeWordForFilter_w & computeWordForFilter_jmask) != 0L && pred.apply$mcZI$sp((int)computeWordForFilter_j) == isFlipped) {
                        computeWordForFilter_w &= computeWordForFilter_jmask ^ 0xFFFFFFFFFFFFFFFFL;
                    }
                    computeWordForFilter_jmask <<= 1;
                }
                l2 = computeWordForFilter_w;
            }
            long _elems0 = l2;
            boolean bl2 = true;
            long computeWordForFilter_oldWord2 = this.elems1();
            if (computeWordForFilter_oldWord2 == 0L) {
                l = 0L;
            } else {
                void computeWordForFilter_wordIndex;
                long computeWordForFilter_w = computeWordForFilter_oldWord2;
                int computeWordForFilter_trailingZeroes = Long.numberOfTrailingZeros(computeWordForFilter_oldWord2);
                long computeWordForFilter_jmask = 1L << computeWordForFilter_trailingZeroes;
                void computeWordForFilter_maxJ = (computeWordForFilter_wordIndex + true) * 64 - Long.numberOfLeadingZeros(computeWordForFilter_oldWord2);
                for (void computeWordForFilter_j = computeWordForFilter_wordIndex * 64 + computeWordForFilter_trailingZeroes; computeWordForFilter_j != computeWordForFilter_maxJ; ++computeWordForFilter_j) {
                    if ((computeWordForFilter_w & computeWordForFilter_jmask) != 0L && pred.apply$mcZI$sp((int)computeWordForFilter_j) == isFlipped) {
                        computeWordForFilter_w &= computeWordForFilter_jmask ^ 0xFFFFFFFFFFFFFFFFL;
                    }
                    computeWordForFilter_jmask <<= 1;
                }
                l = _elems1 = computeWordForFilter_w;
            }
            if (_elems1 == 0L) {
                if (_elems0 == 0L) {
                    return this.empty();
                }
                return new BitSet1(_elems0);
            }
            return new BitSet2(_elems0, _elems1);
        }

        public BitSet2(long elems0, long elems1) {
            this.elems0 = elems0;
            this.elems1 = elems1;
        }
    }

    public static class BitSetN
    extends BitSet {
        private final long[] elems;

        public long[] elems() {
            return this.elems;
        }

        @Override
        public int nwords() {
            return this.elems().length;
        }

        @Override
        public long word(int idx) {
            if (idx < this.nwords()) {
                return this.elems()[idx];
            }
            return 0L;
        }

        @Override
        public BitSet updateWord(int idx, long w) {
            return this.fromBitMaskNoCopy(BitSetOps$.MODULE$.updateArray(this.elems(), idx, w));
        }

        @Override
        public BitSet diff(Set<Object> that) {
            if (that instanceof scala.collection.BitSet) {
                int i;
                int thisnwords;
                scala.collection.BitSet bitSet = (scala.collection.BitSet)that;
                int bsnwords = bitSet.nwords();
                if (bsnwords >= (thisnwords = this.nwords())) {
                    int i2;
                    long currentWord = 0L;
                    boolean anyChanges = false;
                    for (i2 = thisnwords - 1; i2 >= 0 && currentWord == 0L; --i2) {
                        long oldWord = this.word(i2);
                        currentWord = oldWord & (bitSet.word(i2) ^ 0xFFFFFFFFFFFFFFFFL);
                        anyChanges = anyChanges || currentWord != oldWord;
                    }
                    if (i2 < 0) {
                        return this.empty();
                    }
                    int minimumNonZeroIndex = i2 + 1;
                    while (!anyChanges && i2 >= 0) {
                        long oldWord = this.word(i2);
                        currentWord = oldWord & (bitSet.word(i2) ^ 0xFFFFFFFFFFFFFFFFL);
                        anyChanges = anyChanges || currentWord != oldWord;
                        --i2;
                    }
                    if (anyChanges) {
                        if (minimumNonZeroIndex == -1) {
                            return this.empty();
                        }
                        if (minimumNonZeroIndex == 0) {
                            return new BitSet1(currentWord);
                        }
                        if (minimumNonZeroIndex == 1) {
                            return new BitSet2(this.word(0) & (bitSet.word(0) ^ 0xFFFFFFFFFFFFFFFFL), currentWord);
                        }
                        int take$extension_n = minimumNonZeroIndex + 1;
                        long[] newArray = (long[])ArrayOps$.MODULE$.slice$extension(this.elems(), 0, take$extension_n);
                        newArray[i2 + 1] = currentWord;
                        while (i2 >= 0) {
                            newArray[i2] = this.word(i2) & (bitSet.word(i2) ^ 0xFFFFFFFFFFFFFFFFL);
                            --i2;
                        }
                        return this.fromBitMaskNoCopy(newArray);
                    }
                    return this;
                }
                boolean anyChanges = false;
                long currentWord = 0L;
                for (i = bsnwords - 1; i >= 0 && !anyChanges; --i) {
                    long oldWord = this.word(i);
                    currentWord = oldWord & (bitSet.word(i) ^ 0xFFFFFFFFFFFFFFFFL);
                    anyChanges = anyChanges || currentWord != oldWord;
                }
                if (anyChanges) {
                    long[] newElems = (long[])this.elems().clone();
                    newElems[i + 1] = currentWord;
                    while (i >= 0) {
                        newElems[i] = this.word(i) & (bitSet.word(i) ^ 0xFFFFFFFFFFFFFFFFL);
                        --i;
                    }
                    return this.fromBitMaskNoCopy(newElems);
                }
                return this;
            }
            return (BitSet)BitSetOps.diff$(this, that);
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public BitSet filterImpl(Function1<Object, Object> pred, boolean isFlipped) {
            int i;
            long currentWord = 0L;
            boolean anyChanges = false;
            for (i = this.nwords() - 1; i >= 0 && currentWord == 0L; --i) {
                long l;
                long oldWord = this.word(i);
                if (oldWord == 0L) {
                    l = 0L;
                } else {
                    long computeWordForFilter_w = oldWord;
                    int computeWordForFilter_trailingZeroes = Long.numberOfTrailingZeros(oldWord);
                    long computeWordForFilter_jmask = 1L << computeWordForFilter_trailingZeroes;
                    int computeWordForFilter_maxJ = (i + 1) * 64 - Long.numberOfLeadingZeros(oldWord);
                    for (int computeWordForFilter_j = i * 64 + computeWordForFilter_trailingZeroes; computeWordForFilter_j != computeWordForFilter_maxJ; ++computeWordForFilter_j) {
                        if ((computeWordForFilter_w & computeWordForFilter_jmask) != 0L && pred.apply$mcZI$sp(computeWordForFilter_j) == isFlipped) {
                            computeWordForFilter_w &= computeWordForFilter_jmask ^ 0xFFFFFFFFFFFFFFFFL;
                        }
                        computeWordForFilter_jmask <<= 1;
                    }
                    l = computeWordForFilter_w;
                }
                currentWord = l;
                anyChanges = anyChanges || currentWord != oldWord;
            }
            if (i < 0) {
                if (currentWord == 0L) {
                    return this.empty();
                }
                return this.fromBitMaskNoCopy(new long[]{currentWord});
            }
            int minimumNonZeroIndex = i + 1;
            while (!anyChanges && i >= 0) {
                long l;
                long oldWord = this.word(i);
                if (oldWord == 0L) {
                    l = 0L;
                } else {
                    long computeWordForFilter_w = oldWord;
                    int computeWordForFilter_trailingZeroes = Long.numberOfTrailingZeros(oldWord);
                    long computeWordForFilter_jmask = 1L << computeWordForFilter_trailingZeroes;
                    int computeWordForFilter_maxJ = (i + 1) * 64 - Long.numberOfLeadingZeros(oldWord);
                    for (int computeWordForFilter_j = i * 64 + computeWordForFilter_trailingZeroes; computeWordForFilter_j != computeWordForFilter_maxJ; ++computeWordForFilter_j) {
                        if ((computeWordForFilter_w & computeWordForFilter_jmask) != 0L && pred.apply$mcZI$sp(computeWordForFilter_j) == isFlipped) {
                            computeWordForFilter_w &= computeWordForFilter_jmask ^ 0xFFFFFFFFFFFFFFFFL;
                        }
                        computeWordForFilter_jmask <<= 1;
                    }
                    l = computeWordForFilter_w;
                }
                currentWord = l;
                anyChanges = anyChanges || currentWord != oldWord;
                --i;
            }
            if (anyChanges) {
                if (minimumNonZeroIndex == -1) {
                    return this.empty();
                }
                if (minimumNonZeroIndex == 0) {
                    return new BitSet1(currentWord);
                }
                if (minimumNonZeroIndex == 1) {
                    long l;
                    boolean bl = false;
                    long computeWordForFilter_oldWord = this.word(0);
                    if (computeWordForFilter_oldWord == 0L) {
                        l = 0L;
                    } else {
                        void computeWordForFilter_wordIndex;
                        long computeWordForFilter_w = computeWordForFilter_oldWord;
                        int computeWordForFilter_trailingZeroes = Long.numberOfTrailingZeros(computeWordForFilter_oldWord);
                        long computeWordForFilter_jmask = 1L << computeWordForFilter_trailingZeroes;
                        void computeWordForFilter_maxJ = (computeWordForFilter_wordIndex + true) * 64 - Long.numberOfLeadingZeros(computeWordForFilter_oldWord);
                        for (void computeWordForFilter_j = computeWordForFilter_wordIndex * 64 + computeWordForFilter_trailingZeroes; computeWordForFilter_j != computeWordForFilter_maxJ; ++computeWordForFilter_j) {
                            if ((computeWordForFilter_w & computeWordForFilter_jmask) != 0L && pred.apply$mcZI$sp((int)computeWordForFilter_j) == isFlipped) {
                                computeWordForFilter_w &= computeWordForFilter_jmask ^ 0xFFFFFFFFFFFFFFFFL;
                            }
                            computeWordForFilter_jmask <<= 1;
                        }
                        l = computeWordForFilter_w;
                    }
                    return new BitSet2(l, currentWord);
                }
                int take$extension_n = minimumNonZeroIndex + 1;
                long[] newArray = (long[])ArrayOps$.MODULE$.slice$extension(this.elems(), 0, take$extension_n);
                newArray[i + 1] = currentWord;
                while (i >= 0) {
                    long l;
                    long computeWordForFilter_oldWord = this.word(i);
                    if (computeWordForFilter_oldWord == 0L) {
                        l = 0L;
                    } else {
                        long computeWordForFilter_w = computeWordForFilter_oldWord;
                        int computeWordForFilter_trailingZeroes = Long.numberOfTrailingZeros(computeWordForFilter_oldWord);
                        long computeWordForFilter_jmask = 1L << computeWordForFilter_trailingZeroes;
                        int computeWordForFilter_maxJ = (i + 1) * 64 - Long.numberOfLeadingZeros(computeWordForFilter_oldWord);
                        for (int computeWordForFilter_j = i * 64 + computeWordForFilter_trailingZeroes; computeWordForFilter_j != computeWordForFilter_maxJ; ++computeWordForFilter_j) {
                            if ((computeWordForFilter_w & computeWordForFilter_jmask) != 0L && pred.apply$mcZI$sp(computeWordForFilter_j) == isFlipped) {
                                computeWordForFilter_w &= computeWordForFilter_jmask ^ 0xFFFFFFFFFFFFFFFFL;
                            }
                            computeWordForFilter_jmask <<= 1;
                        }
                        l = computeWordForFilter_w;
                    }
                    newArray[i] = l;
                    --i;
                }
                return this.fromBitMaskNoCopy(newArray);
            }
            return this;
        }

        @Override
        public long[] toBitMask() {
            return (long[])this.elems().clone();
        }

        public BitSetN(long[] elems) {
            this.elems = elems;
        }
    }

    private static final class SerializationProxy
    extends BitSet.SerializationProxy {
        private static final long serialVersionUID = 3L;

        @Override
        public Object readResolve() {
            return BitSet$.MODULE$.fromBitMaskNoCopy(this.elems());
        }

        public SerializationProxy(BitSet coll) {
            super(coll);
        }
    }
}

