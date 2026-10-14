/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import java.util.NoSuchElementException;
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
import scala.collection.IndexedSeq;
import scala.collection.IndexedSeqView;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
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
import scala.collection.generic.DefaultSerializable;
import scala.collection.immutable.AbstractSeq;
import scala.collection.immutable.AnyVectorStepper;
import scala.collection.immutable.BigVector;
import scala.collection.immutable.DoubleVectorStepper;
import scala.collection.immutable.IndexedSeq$;
import scala.collection.immutable.IndexedSeqOps;
import scala.collection.immutable.IntVectorStepper;
import scala.collection.immutable.LongVectorStepper;
import scala.collection.immutable.NewVectorIterator;
import scala.collection.immutable.Vector$;
import scala.collection.immutable.Vector0$;
import scala.collection.immutable.Vector1;
import scala.collection.immutable.VectorBuilder;
import scala.collection.immutable.VectorIterator;
import scala.collection.immutable.VectorStatics$;
import scala.collection.mutable.Builder;
import scala.collection.mutable.ReusableBuilder;
import scala.math.Integral;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ObjectRef;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\r}r!B\u00193\u0011\u0003Id!B\u001e3\u0011\u0003a\u0004B\u0002?\u0002\t\u0003\u0011Y\rC\u0004\u0003N\u0006!\tAa4\t\u000f\te\u0017\u0001\"\u0001\u0003\\\"9!Q^\u0001\u0005\u0002\t=\b\u0002CB\u0003\u0003\u0011\u0005Aga\u0002\t\u0013\r]\u0011A1A\u0005\n\u0005-\u0001\u0002CB\r\u0003\u0001\u0006I!!\u0004\t\u0013\rm\u0011A1A\u0005\n\ru\u0001\u0002CB\u0013\u0003\u0001\u0006Iaa\b\t\u0013\r\u001d\u0012!!A\u0005\n\r%b!B\u001e3\u0003C1\u0005\"C5\r\u0005\u000b\u0007IQ\u0001\u001ak\u0011!YHB!A!\u0002\u001bY\u0007B\u0002?\r\t\u0003\u0011T\u0010\u0003\u0004\u0000\u0019\u0011\u0005\u0013\u0011\u0001\u0005\b\u0003\u0013aAQIA\u0006\u0011\u001d\t\u0019\u0002\u0004C#\u0003+A\u0001\"!\b\r\t+\"\u0014q\u0004\u0005\b\u0003kaA\u0011IA\u001c\u0011\u001d\ti\u0005\u0004C!\u0003\u001fBq!a\u0017\r\t\u0003\ni\u0006C\u0004\u0002j1!\t%a\u001b\t\u000f\u0005}D\u0002\"\u0012\u0002\u0002\"A\u0011\u0011\u0013\u0007!\n#\t\u0019\n\u0003\u0005\u0002&2\u0001K\u0011CAT\u0011\u001d\t9\f\u0004C!\u0003sCq!a3\r\t\u000b\ni\rC\u0004\u0002\\2!)%!8\t\u000f\u0005\rH\u0002\"\u0012\u0002f\"9\u00111\u001e\u0007\u0005F\u00055\bbBAz\u0019\u0011\u0005\u0013Q\u001f\u0005\b\u0003odA\u0011IA{\u0011!\tI\u0010\u0004Q\u0007\u0012\u0005m\b\u0002\u0003B\u0003\u0019\u0019E!'a\u0003\t\u0011\t\u001dAB\"\u00053\u0005\u0013A\u0001Ba\b\r\r#\u0011$\u0011\u0005\u0005\b\u0005KaA\u0011\tB\u0014\u0011\u001d\u0011i\u0004\u0004C!\u0003kDqAa\u0010\r\t#\nY\u0001C\u0004\u0003B1!\tEa\u0011\t\u0011\tmD\u0002)C\t\u0005{BqAa$\r\t\u000b\u0012\t\nC\u0004\u0003\u00142!)E!%\t\u000f\tUE\u0002\"\u0012\u0003\u0018\"A!Q\u0016\u0007\u0005\u0002Q\nY\u0001\u0003\u0005\u000302!\t\u0001NA\u0006\u0011!\u0011\t\f\u0004C\u0001i\tM\u0016A\u0002,fGR|'O\u0003\u00024i\u0005I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003kY\n!bY8mY\u0016\u001cG/[8o\u0015\u00059\u0014!B:dC2\f7\u0001\u0001\t\u0003u\u0005i\u0011A\r\u0002\u0007-\u0016\u001cGo\u001c:\u0014\u0007\u0005i\u0014\t\u0005\u0002?\u007f5\ta'\u0003\u0002Am\t1\u0011I\\=SK\u001a\u00042AQ\"F\u001b\u0005!\u0014B\u0001#5\u0005e\u0019FO]5di>\u0003H/[7ju\u0016$7+Z9GC\u000e$xN]=\u0011\u0005ibQCA$N'\u001da\u0001JV-^A\u000e\u00042AO%L\u0013\tQ%GA\u0006BEN$(/Y2u'\u0016\f\bC\u0001'N\u0019\u0001!aA\u0014\u0007\u0005\u0006\u0004y%!A!\u0012\u0005A\u001b\u0006C\u0001 R\u0013\t\u0011fGA\u0004O_RD\u0017N\\4\u0011\u0005y\"\u0016BA+7\u0005\r\te.\u001f\t\u0004u][\u0015B\u0001-3\u0005)Ie\u000eZ3yK\u0012\u001cV-\u001d\t\u0006ui[U\tX\u0005\u00037J\u0012Q\"\u00138eKb,GmU3r\u001fB\u001c\bc\u0001\u001e\r\u0017B)!HX&F9&\u0011qL\r\u0002\u0016'R\u0014\u0018n\u0019;PaRLW.\u001b>fIN+\u0017o\u00149t!\u0011\u0011\u0015mS#\n\u0005\t$$aF%uKJ\f'\r\\3GC\u000e$xN]=EK\u001a\fW\u000f\u001c;t!\t!w-D\u0001f\u0015\t1G'A\u0004hK:,'/[2\n\u0005!,'a\u0005#fM\u0006,H\u000e^*fe&\fG.\u001b>bE2,\u0017a\u00029sK\u001aL\u00070M\u000b\u0002WB\u0011A\u000e\u001f\b\u0003[Zt!A\\;\u000f\u0005=$hB\u00019t\u001b\u0005\t(B\u0001:9\u0003\u0019a$o\\8u}%\tq'\u0003\u00026m%\u00111\u0007N\u0005\u0003oJ\nABV3di>\u0014\u0018J\u001c7j]\u0016L!!\u001f>\u0003\t\u0005\u0013(/\r\u0006\u0003oJ\n\u0001\u0002\u001d:fM&D\u0018\u0007I\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005qs\b\"B5\u0010\u0001\u0004Y\u0017aD5uKJ\f'\r\\3GC\u000e$xN]=\u0016\u0005\u0005\r\u0001\u0003\u0002\"\u0002\u0006\u0015K1!a\u00025\u0005)\u0019V-\u001d$bGR|'/_\u0001\u0007Y\u0016tw\r\u001e5\u0016\u0005\u00055\u0001c\u0001 \u0002\u0010%\u0019\u0011\u0011\u0003\u001c\u0003\u0007%sG/\u0001\u0005ji\u0016\u0014\u0018\r^8s+\t\t9\u0002\u0005\u0003C\u00033Y\u0015bAA\u000ei\tA\u0011\n^3sCR|'/\u0001\u0006gS2$XM]%na2$R\u0001XA\u0011\u0003cAq!a\t\u0014\u0001\u0004\t)#\u0001\u0003qe\u0016$\u0007C\u0002 \u0002(-\u000bY#C\u0002\u0002*Y\u0012\u0011BR;oGRLwN\\\u0019\u0011\u0007y\ni#C\u0002\u00020Y\u0012qAQ8pY\u0016\fg\u000eC\u0004\u00024M\u0001\r!a\u000b\u0002\u0013%\u001ch\t\\5qa\u0016$\u0017aB;qI\u0006$X\rZ\u000b\u0005\u0003s\ty\u0004\u0006\u0004\u0002<\u0005\u0015\u0013\u0011\n\t\u0005u1\ti\u0004E\u0002M\u0003\u007f!q!!\u0011\u0015\u0005\u0004\t\u0019EA\u0001C#\tY5\u000bC\u0004\u0002HQ\u0001\r!!\u0004\u0002\u000b%tG-\u001a=\t\u000f\u0005-C\u00031\u0001\u0002>\u0005!Q\r\\3n\u0003!\t\u0007\u000f]3oI\u0016$W\u0003BA)\u0003/\"B!a\u0015\u0002ZA!!\bDA+!\ra\u0015q\u000b\u0003\b\u0003\u0003*\"\u0019AA\"\u0011\u001d\tY%\u0006a\u0001\u0003+\n\u0011\u0002\u001d:fa\u0016tG-\u001a3\u0016\t\u0005}\u0013Q\r\u000b\u0005\u0003C\n9\u0007\u0005\u0003;\u0019\u0005\r\u0004c\u0001'\u0002f\u00119\u0011\u0011\t\fC\u0002\u0005\r\u0003bBA&-\u0001\u0007\u00111M\u0001\raJ,\u0007/\u001a8eK\u0012\fE\u000e\\\u000b\u0005\u0003[\n\u0019\b\u0006\u0003\u0002p\u0005U\u0004\u0003\u0002\u001e\r\u0003c\u00022\u0001TA:\t\u001d\t\te\u0006b\u0001\u0003\u0007Bq!a\u001e\u0018\u0001\u0004\tI(\u0001\u0004qe\u00164\u0017\u000e\u001f\t\u0006\u0005\u0006m\u0014\u0011O\u0005\u0004\u0003{\"$\u0001D%uKJ\f'\r\\3P]\u000e,\u0017aC1qa\u0016tG-\u001a3BY2,B!a!\u0002\nR!\u0011QQAF!\u0011QD\"a\"\u0011\u00071\u000bI\tB\u0004\u0002Ba\u0011\r!a\u0011\t\u000f\u00055\u0005\u00041\u0001\u0002\u0010\u000611/\u001e4gSb\u0004RAQA>\u0003\u000f\u000bQ\u0002\u001d:fa\u0016tG-\u001a3BY2\u0004T\u0003BAK\u00037#b!a&\u0002\u001e\u0006\u0005\u0006\u0003\u0002\u001e\r\u00033\u00032\u0001TAN\t\u001d\t\t%\u0007b\u0001\u0003\u0007Bq!a\u001e\u001a\u0001\u0004\ty\nE\u0003C\u0003w\nI\nC\u0004\u0002$f\u0001\r!!\u0004\u0002\u0003-\fA\"\u00199qK:$W\rZ!mYB*B!!+\u00020R1\u00111VAY\u0003k\u0003BA\u000f\u0007\u0002.B\u0019A*a,\u0005\u000f\u0005\u0005#D1\u0001\u0002D!9\u0011Q\u0012\u000eA\u0002\u0005M\u0006#\u0002\"\u0002|\u00055\u0006bBAR5\u0001\u0007\u0011QB\u0001\nG2\f7o\u001d(b[\u0016,\"!a/\u0011\t\u0005u\u0016qY\u0007\u0003\u0003\u007fSA!!1\u0002D\u0006!A.\u00198h\u0015\t\t)-\u0001\u0003kCZ\f\u0017\u0002BAe\u0003\u007f\u0013aa\u0015;sS:<\u0017\u0001\u0002;bW\u0016$2\u0001XAh\u0011\u001d\t\t\u000e\ba\u0001\u0003\u001b\t\u0011A\u001c\u0015\u00049\u0005U\u0007c\u0001 \u0002X&\u0019\u0011\u0011\u001c\u001c\u0003\r%tG.\u001b8f\u0003\u0011!'o\u001c9\u0015\u0007q\u000by\u000eC\u0004\u0002Rv\u0001\r!!\u0004)\u0007u\t).A\u0005uC.,'+[4iiR\u0019A,a:\t\u000f\u0005Eg\u00041\u0001\u0002\u000e!\u001aa$!6\u0002\u0013\u0011\u0014x\u000e\u001d*jO\"$Hc\u0001/\u0002p\"9\u0011\u0011[\u0010A\u0002\u00055\u0001fA\u0010\u0002V\u0006!A/Y5m+\u0005a\u0016\u0001B5oSR\faa\u001d7jG\u0016\u0004D#\u0002/\u0002~\n\u0005\u0001bBA\u0000E\u0001\u0007\u0011QB\u0001\u0003Y>DqAa\u0001#\u0001\u0004\ti!\u0001\u0002iS\u0006\u0001b/Z2u_J\u001cF.[2f\u0007>,h\u000e^\u0001\fm\u0016\u001cGo\u001c:TY&\u001cW\r\u0006\u0003\u0003\f\tm\u0001\u0007\u0002B\u0007\u0005+\u0001RA\u0010B\b\u0005'I1A!\u00057\u0005\u0015\t%O]1z!\ra%Q\u0003\u0003\f\u0005/!\u0013\u0011!A\u0001\u0006\u0003\u0011IBA\u0002`I]\n\"\u0001U\u001f\t\u000f\tuA\u00051\u0001\u0002\u000e\u0005\u0019\u0011\u000e\u001a=\u0002/Y,7\r^8s'2L7-\u001a)sK\u001aL\u0007\u0010T3oORDG\u0003BA\u0007\u0005GAqA!\b&\u0001\u0004\ti!A\u0006d_BLHk\\!se\u0006LX\u0003\u0002B\u0015\u0005g!\u0002\"!\u0004\u0003,\tU\"\u0011\b\u0005\b\u0005[1\u0003\u0019\u0001B\u0018\u0003\tA8\u000fE\u0003?\u0005\u001f\u0011\t\u0004E\u0002M\u0005g!q!!\u0011'\u0005\u0004\t\u0019\u0005C\u0004\u00038\u0019\u0002\r!!\u0004\u0002\u000bM$\u0018M\u001d;\t\u000f\tmb\u00051\u0001\u0002\u000e\u0005\u0019A.\u001a8\u0002\u0011Q|g+Z2u_J\fq#\u00199qYf\u0004&/\u001a4feJ,G-T1y\u0019\u0016tw\r\u001e5\u0002\u000fM$X\r\u001d9feV!!Q\tB()\u0011\u00119E!\u001d\u0013\r\t%#Q\nB2\r\u0019\u0011Y\u0005\u0004\u0001\u0003H\taAH]3gS:,W.\u001a8u}A\u0019AJa\u0014\u0005\u000f\tE\u0013F1\u0001\u0003T\t\t1+E\u0002Q\u0005+\u0002DAa\u0016\u0003`A)!I!\u0017\u0003^%\u0019!1\f\u001b\u0003\u000fM#X\r\u001d9feB\u0019AJa\u0018\u0005\u0017\t\u0005$qJA\u0001\u0002\u0003\u0015\ta\u0014\u0002\u0004?\u0012B\u0004\u0003\u0002B3\u0005Wr1A\u001cB4\u0013\r\u0011I\u0007N\u0001\b'R,\u0007\u000f]3s\u0013\u0011\u0011iGa\u001c\u0003\u001d\u00153g-[2jK:$8\u000b\u001d7ji*\u0019!\u0011\u000e\u001b\t\u000f\tM\u0014\u0006q\u0001\u0003v\u0005)1\u000f[1qKB1!Ia\u001eL\u0005\u001bJ1A!\u001f5\u00051\u0019F/\u001a9qKJ\u001c\u0006.\u00199f\u0003\u0011Iwn\u001c2\u0015\t\t}$Q\u0012\t\u0005\u0005\u0003\u00139ID\u0002p\u0005\u0007K1A!\"7\u0003\u001d\u0001\u0018mY6bO\u0016LAA!#\u0003\f\nI\u0012J\u001c3fq>+Ho\u00144C_VtGm]#yG\u0016\u0004H/[8o\u0015\r\u0011)I\u000e\u0005\b\u0003\u000fR\u0003\u0019AA\u0007\u0003\u0011AW-\u00193\u0016\u0003-\u000bA\u0001\\1ti\u00069am\u001c:fC\u000eDW\u0003\u0002BM\u0005S#BAa'\u0003\"B\u0019aH!(\n\u0007\t}eG\u0001\u0003V]&$\bb\u0002BR[\u0001\u0007!QU\u0001\u0002MB1a(a\nL\u0005O\u00032\u0001\u0014BU\t\u0019\u0011Y+\fb\u0001\u001f\n\tQ+\u0001\u0006ti\u0006\u0014H/\u00138eKb\f\u0001\"\u001a8e\u0013:$W\r_\u0001\rS:LG/\u0013;fe\u0006$xN]\u000b\u0005\u0005k\u0013\u0019\r\u0006\u0003\u0003\u001c\n]\u0006b\u0002B]a\u0001\u0007!1X\u0001\u0002gB)!H!0\u0003B&\u0019!q\u0018\u001a\u0003\u001dY+7\r^8s\u0013R,'/\u0019;peB\u0019AJa1\u0005\u000f\u0005\u0005\u0003G1\u0001\u0002D%\u001aABa2\n\u0007\t%'G\u0001\u0006WK\u000e$xN]%na2$\u0012!O\u0001\u0006K6\u0004H/_\u000b\u0005\u0005#\u00149.\u0006\u0002\u0003TB!!\b\u0004Bk!\ra%q\u001b\u0003\u0006\u001d\u000e\u0011\raT\u0001\u0005MJ|W.\u0006\u0003\u0003^\n\rH\u0003\u0002Bp\u0005O\u0004BA\u000f\u0007\u0003bB\u0019AJa9\u0005\r\t\u0015HA1\u0001P\u0005\u0005)\u0005b\u0002Bu\t\u0001\u0007!1^\u0001\u0003SR\u0004RAQA>\u0005C\f!B\\3x\u0005VLG\u000eZ3s+\u0011\u0011\tp!\u0001\u0016\u0005\tM\b\u0003\u0003B{\u0005w\u0014ypa\u0001\u000e\u0005\t](b\u0001B}i\u00059Q.\u001e;bE2,\u0017\u0002\u0002B\u007f\u0005o\u0014qBU3vg\u0006\u0014G.\u001a\"vS2$WM\u001d\t\u0004\u0019\u000e\u0005A!\u0002(\u0006\u0005\u0004y\u0005\u0003\u0002\u001e\r\u0005\u007f\f!BZ5mYN\u0003\u0018M]:f+\u0011\u0019Ia!\u0005\u0015\t\r-1Q\u0003\u000b\u0005\u0007\u001b\u0019\u0019\u0002\u0005\u0003;\u0019\r=\u0001c\u0001'\u0004\u0012\u0011)aJ\u0002b\u0001\u001f\"9\u00111\n\u0004A\u0002\r=\u0001bBAi\r\u0001\u0007\u0011QB\u0001\u001fI\u00164\u0017-\u001e7u\u0003B\u0004H.\u001f)sK\u001a,'O]3e\u001b\u0006DH*\u001a8hi\"\fq\u0004Z3gCVdG/\u00119qYf\u0004&/\u001a4feJ,G-T1y\u0019\u0016tw\r\u001e5!\u00035)W\u000e\u001d;z\u0013R,'/\u0019;peV\u00111q\u0004\t\u0005u\r\u0005\u0002+C\u0002\u0004$I\u0012\u0011CT3x-\u0016\u001cGo\u001c:Ji\u0016\u0014\u0018\r^8s\u00039)W\u000e\u001d;z\u0013R,'/\u0019;pe\u0002\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"aa\u000b\u0011\t\u0005u6QF\u0005\u0005\u0007_\tyL\u0001\u0004PE*,7\r\u001e\u0015\b\u0003\rM2\u0011HB\u001e!\rq4QG\u0005\u0004\u0007o1$\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\u0019\u0001f\u0002\u0001\u00044\re21\b")
public abstract class Vector<A>
extends AbstractSeq<A>
implements scala.collection.immutable.IndexedSeq<A>,
scala.collection.immutable.StrictOptimizedSeqOps<A, Vector, Vector<A>>,
DefaultSerializable {
    private final Object[] prefix1;

    public static <A> ReusableBuilder<A, Vector<A>> newBuilder() {
        return new VectorBuilder();
    }

    public static <E> Vector<E> from(IterableOnce<E> it) {
        return Vector$.MODULE$.from((IterableOnce)it);
    }

    public static SeqOps tabulate(int n, Function1 f) {
        VectorBuilder tabulate_b = new VectorBuilder();
        for (int tabulate_i = 0; tabulate_i < n; ++tabulate_i) {
            Object tabulate_$plus$eq_elem = f.apply(tabulate_i);
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var4_4 = null;
        }
        return tabulate_b.result();
    }

    public static SeqOps fill(int n, Function0 elem) {
        VectorBuilder fill_b = new VectorBuilder();
        for (int fill_i = 0; fill_i < n; ++fill_i) {
            Object fill_$plus$eq_elem = elem.apply();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var4_4 = null;
        }
        return fill_b.result();
    }

    public static SeqOps unapplySeq(SeqOps x) {
        return x;
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        VectorBuilder tabulate_b = new VectorBuilder();
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var11_11;
            VectorBuilder tabulate_b2 = new VectorBuilder();
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var14_14;
                VectorBuilder tabulate_b3 = new VectorBuilder();
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object var17_17;
                    VectorBuilder tabulate_b4 = new VectorBuilder();
                    Builder.sizeHint$(tabulate_b4, n4);
                    for (int tabulate_i4 = 0; tabulate_i4 < n4; ++tabulate_i4) {
                        Object var20_20;
                        VectorBuilder tabulate_b5 = new VectorBuilder();
                        Builder.sizeHint$(tabulate_b5, n5);
                        for (int tabulate_i5 = 0; tabulate_i5 < n5; ++tabulate_i5) {
                            Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3, tabulate_i4, tabulate_i5);
                            tabulate_b5.addOne(tabulate_$plus$eq_elem);
                            var20_20 = null;
                        }
                        Object var18_18 = null;
                        var20_20 = null;
                        Object tabulate_$plus$eq_elem = tabulate_b5.result();
                        tabulate_b4.addOne(tabulate_$plus$eq_elem);
                        var17_17 = null;
                    }
                    Object var15_15 = null;
                    var17_17 = null;
                    Object tabulate_$plus$eq_elem = tabulate_b4.result();
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var14_14 = null;
                }
                Object var12_12 = null;
                var14_14 = null;
                Object tabulate_$plus$eq_elem = tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var11_11 = null;
            }
            Object var9_9 = null;
            var11_11 = null;
            Object tabulate_$plus$eq_elem = tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var8_8 = null;
        }
        return tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        VectorBuilder tabulate_b = new VectorBuilder();
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var10_10;
            VectorBuilder tabulate_b2 = new VectorBuilder();
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var13_13;
                VectorBuilder tabulate_b3 = new VectorBuilder();
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object var16_16;
                    VectorBuilder tabulate_b4 = new VectorBuilder();
                    Builder.sizeHint$(tabulate_b4, n4);
                    for (int tabulate_i4 = 0; tabulate_i4 < n4; ++tabulate_i4) {
                        Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3, tabulate_i4);
                        tabulate_b4.addOne(tabulate_$plus$eq_elem);
                        var16_16 = null;
                    }
                    Object var14_14 = null;
                    var16_16 = null;
                    Object tabulate_$plus$eq_elem = tabulate_b4.result();
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var13_13 = null;
                }
                Object var11_11 = null;
                var13_13 = null;
                Object tabulate_$plus$eq_elem = tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var10_10 = null;
            }
            Object var8_8 = null;
            var10_10 = null;
            Object tabulate_$plus$eq_elem = tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var7_7 = null;
        }
        return tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f) {
        VectorBuilder tabulate_b = new VectorBuilder();
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var9_9;
            VectorBuilder tabulate_b2 = new VectorBuilder();
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var12_12;
                VectorBuilder tabulate_b3 = new VectorBuilder();
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3);
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var12_12 = null;
                }
                Object var10_10 = null;
                var12_12 = null;
                Object tabulate_$plus$eq_elem = tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var9_9 = null;
            }
            Object var7_7 = null;
            var9_9 = null;
            Object tabulate_$plus$eq_elem = tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var6_6 = null;
        }
        return tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, Function2 f) {
        VectorBuilder tabulate_b = new VectorBuilder();
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var8_8;
            VectorBuilder tabulate_b2 = new VectorBuilder();
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2);
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var8_8 = null;
            }
            Object var6_6 = null;
            var8_8 = null;
            Object tabulate_$plus$eq_elem = tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var5_5 = null;
        }
        return tabulate_b.result();
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        VectorBuilder fill_b = new VectorBuilder();
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var11_11;
            VectorBuilder fill_b2 = new VectorBuilder();
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var14_14;
                VectorBuilder fill_b3 = new VectorBuilder();
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object var17_17;
                    VectorBuilder fill_b4 = new VectorBuilder();
                    Builder.sizeHint$(fill_b4, n4);
                    for (int fill_i4 = 0; fill_i4 < n4; ++fill_i4) {
                        Object var20_20;
                        VectorBuilder fill_b5 = new VectorBuilder();
                        Builder.sizeHint$(fill_b5, n5);
                        for (int fill_i5 = 0; fill_i5 < n5; ++fill_i5) {
                            Object fill_$plus$eq_elem = elem.apply();
                            fill_b5.addOne(fill_$plus$eq_elem);
                            var20_20 = null;
                        }
                        Object var18_18 = null;
                        var20_20 = null;
                        Object fill_$plus$eq_elem = fill_b5.result();
                        fill_b4.addOne(fill_$plus$eq_elem);
                        var17_17 = null;
                    }
                    Object var15_15 = null;
                    var17_17 = null;
                    Object fill_$plus$eq_elem = fill_b4.result();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var14_14 = null;
                }
                Object var12_12 = null;
                var14_14 = null;
                Object fill_$plus$eq_elem = fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var11_11 = null;
            }
            Object var9_9 = null;
            var11_11 = null;
            Object fill_$plus$eq_elem = fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var8_8 = null;
        }
        return fill_b.result();
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        VectorBuilder fill_b = new VectorBuilder();
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var10_10;
            VectorBuilder fill_b2 = new VectorBuilder();
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var13_13;
                VectorBuilder fill_b3 = new VectorBuilder();
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object var16_16;
                    VectorBuilder fill_b4 = new VectorBuilder();
                    Builder.sizeHint$(fill_b4, n4);
                    for (int fill_i4 = 0; fill_i4 < n4; ++fill_i4) {
                        Object fill_$plus$eq_elem = elem.apply();
                        fill_b4.addOne(fill_$plus$eq_elem);
                        var16_16 = null;
                    }
                    Object var14_14 = null;
                    var16_16 = null;
                    Object fill_$plus$eq_elem = fill_b4.result();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var13_13 = null;
                }
                Object var11_11 = null;
                var13_13 = null;
                Object fill_$plus$eq_elem = fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var10_10 = null;
            }
            Object var8_8 = null;
            var10_10 = null;
            Object fill_$plus$eq_elem = fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var7_7 = null;
        }
        return fill_b.result();
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem) {
        VectorBuilder fill_b = new VectorBuilder();
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var9_9;
            VectorBuilder fill_b2 = new VectorBuilder();
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var12_12;
                VectorBuilder fill_b3 = new VectorBuilder();
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object fill_$plus$eq_elem = elem.apply();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var12_12 = null;
                }
                Object var10_10 = null;
                var12_12 = null;
                Object fill_$plus$eq_elem = fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var9_9 = null;
            }
            Object var7_7 = null;
            var9_9 = null;
            Object fill_$plus$eq_elem = fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var6_6 = null;
        }
        return fill_b.result();
    }

    public static Object fill(int n1, int n2, Function0 elem) {
        VectorBuilder fill_b = new VectorBuilder();
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var8_8;
            VectorBuilder fill_b2 = new VectorBuilder();
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object fill_$plus$eq_elem = elem.apply();
                fill_b2.addOne(fill_$plus$eq_elem);
                var8_8 = null;
            }
            Object var6_6 = null;
            var8_8 = null;
            Object fill_$plus$eq_elem = fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var5_5 = null;
        }
        return fill_b.result();
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(Vector$.MODULE$, start2, end, step, evidence$4);
    }

    public static Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(Vector$.MODULE$, start2, end, evidence$3);
    }

    public static Object unfold(Object init2, Function1 f) {
        Vector$ unfold_this = Vector$.MODULE$;
        View.Unfold from_source = new View.Unfold(init2, f);
        return unfold_this.from((IterableOnce)from_source);
    }

    public static Object iterate(Object start2, int len, Function1 f) {
        Vector$ iterate_this = Vector$.MODULE$;
        View.Iterate<Object> from_source = new View.Iterate<Object>(start2, len, f);
        return iterate_this.from((IterableOnce)from_source);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
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
    public Object sorted(Ordering ord) {
        return scala.collection.immutable.StrictOptimizedSeqOps.sorted$(this, ord);
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
    public Tuple2<Vector<A>, Vector<A>> partition(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<Vector<A>, Vector<A>> span(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<Vector<A1>, Vector<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<Vector<A1>, Vector<A2>, Vector<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
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
    public Object filter(Function1 pred) {
        return StrictOptimizedIterableOps.filter$(this, pred);
    }

    @Override
    public Object filterNot(Function1 pred) {
        return StrictOptimizedIterableOps.filterNot$(this, pred);
    }

    @Override
    public <A1, A2> Tuple2<Vector<A1>, Vector<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
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
    public Iterator<A> reverseIterator() {
        return scala.collection.IndexedSeqOps.reverseIterator$(this);
    }

    @Override
    public <B$> B$ foldRight(B$ z, Function2<A, B$, B$> op) {
        return (B$)scala.collection.IndexedSeqOps.foldRight$(this, z, op);
    }

    @Override
    public IndexedSeqView<A> view() {
        return scala.collection.IndexedSeqOps.view$(this);
    }

    @Override
    public IndexedSeqView<A> view(int from, int until) {
        return scala.collection.IndexedSeqOps.view$(this, from, until);
    }

    @Override
    public Iterable<A> reversed() {
        return scala.collection.IndexedSeqOps.reversed$(this);
    }

    @Override
    public Object reverse() {
        return scala.collection.IndexedSeqOps.reverse$(this);
    }

    @Override
    public Option<A> headOption() {
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

    public final Object[] prefix1() {
        return this.prefix1;
    }

    @Override
    public SeqFactory<Vector> iterableFactory() {
        return Vector$.MODULE$;
    }

    @Override
    public final int length() {
        if (this instanceof BigVector) {
            return ((BigVector)this).length0();
        }
        return this.prefix1().length;
    }

    @Override
    public final Iterator<A> iterator() {
        if (Vector0$.MODULE$ == this) {
            return Vector$.MODULE$.scala$collection$immutable$Vector$$emptyIterator();
        }
        return new NewVectorIterator(this, this.length(), this.vectorSliceCount());
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public final Vector<A> filterImpl(Function1<A, Object> pred, boolean isFlipped) {
        int len = this.prefix1().length;
        for (int i = 0; i != len; ++i) {
            if (BoxesRunTime.unboxToBoolean(pred.apply(this.prefix1()[i])) != isFlipped) continue;
            int bitmap = 0;
            for (int j = i + 1; j < len; ++j) {
                if (BoxesRunTime.unboxToBoolean(pred.apply(this.prefix1()[j])) == isFlipped) continue;
                bitmap |= 1 << j;
            }
            int newLen = i + Integer.bitCount(bitmap);
            if (this instanceof BigVector) {
                Object var18_15;
                Object[] objectArray;
                int k;
                VectorBuilder b = new VectorBuilder();
                for (k = 0; k < i; ++k) {
                    b.addOne(this.prefix1()[k]);
                }
                k = i + 1;
                while (i != newLen) {
                    if ((1 << k & bitmap) != 0) {
                        b.addOne(this.prefix1()[k]);
                        ++i;
                    }
                    ++k;
                }
                Function1<Object, Object> & Serializable intersect = (Function1<Object, Object> & Serializable)v -> {
                    if (BoxesRunTime.unboxToBoolean(pred.apply(v)) != isFlipped) {
                        return b.addOne(v);
                    }
                    return BoxedUnit.UNIT;
                };
                BigVector foreachRest_this = (BigVector)this;
                int foreachRest_c = foreachRest_this.vectorSliceCount();
                for (int foreachRest_i = 1; foreachRest_i < foreachRest_c; ++foreachRest_i) {
                    Object object;
                    int foreachRest_foreachRec_i;
                    void foreachRest_foreachRec_level;
                    void foreachRest_foreachRec_a;
                    int foreachRest_vectorSliceDim_c = foreachRest_c / 2;
                    objectArray = foreachRest_this.vectorSlice(foreachRest_i);
                    int n = foreachRest_vectorSliceDim_c + 1 - Math.abs(foreachRest_i - foreachRest_vectorSliceDim_c) - 1;
                    VectorStatics$ foreachRest_foreachRec_this = VectorStatics$.MODULE$;
                    int foreachRest_foreachRec_len = ((void)foreachRest_foreachRec_a).length;
                    if (foreachRest_foreachRec_level == false) {
                        for (foreachRest_foreachRec_i = 0; foreachRest_foreachRec_i < foreachRest_foreachRec_len; ++foreachRest_foreachRec_i) {
                            object = foreachRest_foreachRec_a[foreachRest_foreachRec_i];
                            if (BoxesRunTime.unboxToBoolean(pred.apply(object)) == isFlipped) continue;
                            b.addOne(object);
                        }
                    } else {
                        void foreachRest_foreachRec_l = foreachRest_foreachRec_level - true;
                        while (foreachRest_foreachRec_i < foreachRest_foreachRec_len) {
                            int foreachRec_i;
                            Object[] foreachRec_a = (Object[])foreachRest_foreachRec_a[foreachRest_foreachRec_i];
                            int foreachRec_len = foreachRec_a.length;
                            if (foreachRest_foreachRec_l == false) {
                                for (foreachRec_i = 0; foreachRec_i < foreachRec_len; ++foreachRec_i) {
                                    object = foreachRec_a[foreachRec_i];
                                    if (BoxesRunTime.unboxToBoolean(pred.apply(object)) == isFlipped) continue;
                                    b.addOne(object);
                                }
                            } else {
                                void foreachRec_l = foreachRest_foreachRec_l - true;
                                while (foreachRec_i < foreachRec_len) {
                                    void foreachRest_f;
                                    foreachRest_foreachRec_this.foreachRec((int)foreachRec_l, (T[])((Object[])foreachRec_a[foreachRec_i]), foreachRest_f);
                                    ++foreachRec_i;
                                }
                            }
                            Object var35_21 = null;
                            ++foreachRest_foreachRec_i;
                        }
                    }
                    var18_15 = null;
                    objectArray = null;
                }
                intersect = null;
                var18_15 = null;
                objectArray = null;
                return b.result();
            }
            if (newLen == 0) {
                return Vector0$.MODULE$;
            }
            Object[] newData = new Object[newLen];
            System.arraycopy(this.prefix1(), 0, newData, 0, i);
            int k = i + 1;
            while (i != newLen) {
                if ((1 << k & bitmap) != 0) {
                    newData[i] = this.prefix1()[k];
                    ++i;
                }
                ++k;
            }
            return new Vector1(newData);
        }
        if (this instanceof BigVector) {
            Object var29_42;
            Object[] objectArray;
            VectorBuilder b = new VectorBuilder();
            b.initFrom(this.prefix1());
            Function1<Object, Object> & Serializable intersect = (Function1<Object, Object> & Serializable)v -> {
                if (BoxesRunTime.unboxToBoolean(pred.apply(v)) != isFlipped) {
                    return b.addOne(v);
                }
                return BoxedUnit.UNIT;
            };
            BigVector foreachRest_this = (BigVector)this;
            int foreachRest_c = foreachRest_this.vectorSliceCount();
            for (int foreachRest_i = 1; foreachRest_i < foreachRest_c; ++foreachRest_i) {
                Object object;
                int foreachRest_foreachRec_i;
                void foreachRest_foreachRec_level;
                void foreachRest_foreachRec_a;
                int foreachRest_vectorSliceDim_c = foreachRest_c / 2;
                objectArray = foreachRest_this.vectorSlice(foreachRest_i);
                int n = foreachRest_vectorSliceDim_c + 1 - Math.abs(foreachRest_i - foreachRest_vectorSliceDim_c) - 1;
                VectorStatics$ foreachRest_foreachRec_this = VectorStatics$.MODULE$;
                int foreachRest_foreachRec_len = ((void)foreachRest_foreachRec_a).length;
                if (foreachRest_foreachRec_level == false) {
                    for (foreachRest_foreachRec_i = 0; foreachRest_foreachRec_i < foreachRest_foreachRec_len; ++foreachRest_foreachRec_i) {
                        object = foreachRest_foreachRec_a[foreachRest_foreachRec_i];
                        if (BoxesRunTime.unboxToBoolean(pred.apply(object)) == isFlipped) continue;
                        b.addOne(object);
                    }
                } else {
                    void foreachRest_foreachRec_l = foreachRest_foreachRec_level - true;
                    while (foreachRest_foreachRec_i < foreachRest_foreachRec_len) {
                        int foreachRec_i;
                        Object[] foreachRec_a = (Object[])foreachRest_foreachRec_a[foreachRest_foreachRec_i];
                        int foreachRec_len = foreachRec_a.length;
                        if (foreachRest_foreachRec_l == false) {
                            for (foreachRec_i = 0; foreachRec_i < foreachRec_len; ++foreachRec_i) {
                                object = foreachRec_a[foreachRec_i];
                                if (BoxesRunTime.unboxToBoolean(pred.apply(object)) == isFlipped) continue;
                                b.addOne(object);
                            }
                        } else {
                            void foreachRec_l = foreachRest_foreachRec_l - true;
                            while (foreachRec_i < foreachRec_len) {
                                void foreachRest_f;
                                foreachRest_foreachRec_this.foreachRec((int)foreachRec_l, (T[])((Object[])foreachRec_a[foreachRec_i]), foreachRest_f);
                                ++foreachRec_i;
                            }
                        }
                        Object var39_36 = null;
                        ++foreachRest_foreachRec_i;
                    }
                }
                var29_42 = null;
                objectArray = null;
            }
            intersect = null;
            var29_42 = null;
            objectArray = null;
            return b.result();
        }
        return this;
    }

    @Override
    public <B> Vector<B> updated(int index, B elem) {
        return (Vector)scala.collection.immutable.StrictOptimizedSeqOps.updated$(this, index, elem);
    }

    @Override
    public <B> Vector<B> appended(B elem) {
        return (Vector)StrictOptimizedSeqOps.appended$(this, elem);
    }

    @Override
    public <B> Vector<B> prepended(B elem) {
        return (Vector)StrictOptimizedSeqOps.prepended$(this, elem);
    }

    @Override
    public <B> Vector<B> prependedAll(IterableOnce<B> prefix) {
        int k = prefix.knownSize();
        if (k == 0) {
            return this;
        }
        if (k < 0) {
            return (Vector)StrictOptimizedSeqOps.prependedAll$(this, prefix);
        }
        return this.prependedAll0(prefix, k);
    }

    @Override
    public final <B> Vector<B> appendedAll(IterableOnce<B> suffix) {
        int k = suffix.knownSize();
        if (k == 0) {
            return this;
        }
        if (k < 0) {
            return (Vector)StrictOptimizedSeqOps.appendedAll$(this, suffix);
        }
        return this.appendedAll0(suffix, k);
    }

    /*
     * WARNING - void declaration
     */
    public <B> Vector<B> prependedAll0(IterableOnce<B> prefix, int k) {
        int tinyAppendLimit = 4 + this.vectorSliceCount();
        if (k < tinyAppendLimit) {
            Vector v = this;
            Iterator it = IndexedSeq$.MODULE$.from((IterableOnce)prefix).reverseIterator();
            while (it.hasNext()) {
                Object a = it.next();
                if (v == null) {
                    throw null;
                }
                v = (Vector)v.prepended(a);
            }
            return v;
        }
        if (this.length() < k >>> 5 && prefix instanceof Vector) {
            Vector v = (Vector)prefix;
            Iterator<A> it = this.iterator();
            while (it.hasNext()) {
                void $colon$plus_elem;
                A a = it.next();
                if (v == null) {
                    throw null;
                }
                a = null;
                v = (Vector)v.appended($colon$plus_elem);
            }
            return v;
        }
        return (Vector)StrictOptimizedSeqOps.prependedAll$(this, prefix);
    }

    public <B> Vector<B> appendedAll0(IterableOnce<B> suffix, int k) {
        int tinyAppendLimit = 4 + this.vectorSliceCount();
        if (k > 0 && k < tinyAppendLimit) {
            ObjectRef<Vector> v = new ObjectRef<Vector>(this);
            if (suffix instanceof scala.collection.immutable.Iterable) {
                ((scala.collection.immutable.Iterable)suffix).foreach((Function1<Object, Object> & Serializable)x -> {
                    v.elem = ((Vector)v.elem).appended(x);
                    return BoxedUnit.UNIT;
                });
            } else {
                suffix.iterator().foreach((Function1<Object, Object> & Serializable)x -> {
                    v.elem = ((Vector)v.elem).appended(x);
                    return BoxedUnit.UNIT;
                });
            }
            return (Vector)v.elem;
        }
        if (this.length() < k >>> 5 && suffix instanceof Vector) {
            Object v = (Vector)suffix;
            Iterator<A> ri = this.reverseIterator();
            while (ri.hasNext()) {
                v = ((Vector)v).prepended(ri.next());
            }
            return v;
        }
        return ((VectorBuilder)new VectorBuilder().initFrom(this).addAll((IterableOnce)suffix)).result();
    }

    @Override
    public String className() {
        return "Vector";
    }

    @Override
    public final Vector<A> take(int n) {
        return (Vector)this.slice(0, n);
    }

    @Override
    public final Vector<A> drop(int n) {
        return (Vector)this.slice(n, this.length());
    }

    @Override
    public final Vector<A> takeRight(int n) {
        return (Vector)this.slice(this.length() - Math.max(n, 0), this.length());
    }

    @Override
    public final Vector<A> dropRight(int n) {
        return (Vector)this.slice(0, this.length() - Math.max(n, 0));
    }

    @Override
    public Vector<A> tail() {
        return (Vector)this.slice(1, this.length());
    }

    @Override
    public Vector<A> init() {
        return (Vector)this.slice(0, this.length() - 1);
    }

    public abstract Vector<A> slice0(int var1, int var2);

    public abstract int vectorSliceCount();

    public abstract Object[] vectorSlice(int var1);

    public abstract int vectorSlicePrefixLength(int var1);

    @Override
    public <B> int copyToArray(Object xs, int start2, int len) {
        return this.iterator().copyToArray(xs, start2, len);
    }

    @Override
    public Vector<A> toVector() {
        return this;
    }

    @Override
    public int applyPreferredMaxLength() {
        return Vector$.MODULE$.scala$collection$immutable$Vector$$defaultApplyPreferredMaxLength();
    }

    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
        int n = shape.shape();
        if (StepperShape$.MODULE$.IntShape() == n) {
            return (S)new IntVectorStepper((NewVectorIterator)this.iterator());
        }
        if (StepperShape$.MODULE$.LongShape() == n) {
            return (S)new LongVectorStepper((NewVectorIterator)this.iterator());
        }
        if (StepperShape$.MODULE$.DoubleShape() == n) {
            return (S)new DoubleVectorStepper((NewVectorIterator)this.iterator());
        }
        return shape.parUnbox(new AnyVectorStepper((NewVectorIterator)this.iterator()));
    }

    public IndexOutOfBoundsException ioob(int index) {
        return new IndexOutOfBoundsException(new StringBuilder(31).append(index).append(" is out of bounds (min 0, max ").append(this.length() - 1).append(")").toString());
    }

    @Override
    public final A head() {
        if (this.prefix1().length == 0) {
            throw new NoSuchElementException("empty.head");
        }
        return (A)this.prefix1()[0];
    }

    @Override
    public final A last() {
        if (this instanceof BigVector) {
            Object[] suffix = ((BigVector)this).suffix1();
            if (suffix.length == 0) {
                throw new NoSuchElementException("empty.tail");
            }
            return (A)suffix[suffix.length - 1];
        }
        return (A)this.prefix1()[this.prefix1().length - 1];
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public final <U> void foreach(Function1<A, U> f) {
        int c = this.vectorSliceCount();
        for (int i = 0; i < c; ++i) {
            int foreachRec_i;
            void foreachRec_level;
            void foreachRec_a;
            int vectorSliceDim_c = c / 2;
            Object[] objectArray = this.vectorSlice(i);
            int n = vectorSliceDim_c + 1 - Math.abs(i - vectorSliceDim_c) - 1;
            VectorStatics$ foreachRec_this = VectorStatics$.MODULE$;
            int foreachRec_len = ((void)foreachRec_a).length;
            if (foreachRec_level == false) {
                for (foreachRec_i = 0; foreachRec_i < foreachRec_len; ++foreachRec_i) {
                    f.apply(foreachRec_a[foreachRec_i]);
                }
            } else {
                void foreachRec_l = foreachRec_level - true;
                while (foreachRec_i < foreachRec_len) {
                    foreachRec_this.foreachRec((int)foreachRec_l, (Object[])foreachRec_a[foreachRec_i], f);
                    ++foreachRec_i;
                }
            }
            Object var5_5 = null;
            objectArray = null;
        }
    }

    public int startIndex() {
        return 0;
    }

    public int endIndex() {
        return this.length();
    }

    public <B> void initIterator(VectorIterator<B> s2) {
        s2.it_$eq((NewVectorIterator)this.iterator());
    }

    public Vector(Object[] prefix1) {
        this.prefix1 = prefix1;
    }
}

